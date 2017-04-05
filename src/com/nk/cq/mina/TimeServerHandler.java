package com.nk.cq.mina;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.nk.common.CommClassId;
import com.nk.common.Communication;
import com.nk.common.DataItem;
import com.nk.common.Frame;
import com.nk.common.TmFrame;

@SuppressWarnings("rawtypes")
public class TimeServerHandler implements IoHandler{
	
	@Override
	public void exceptionCaught(IoSession arg0,Throwable arg1) throws Exception{
		System.out.println(arg0.toString()+"发生异常，可能由客户端强制关闭连接造成!");
//		arg1.printStackTrace();
	}
	
	private void updateFpcNoTmIdRelationship(String curFpcNo,Set<Long> curTmIdSet,IoSession session) throws Exception{
		boolean bExisted = false;
		Set<String> keySet = Communication.fpcNoTmIdMap.keySet();
		for(String key:keySet){
			if(key.equals(curFpcNo)){
				Communication.fpcNoTmIdMap.get(key).addAll(curTmIdSet);
				bExisted = true;
				break;
			}
		}
		if(!bExisted){
			Communication.fpcNoTmIdMap.put(curFpcNo,curTmIdSet);
		}
	}

	@Override
	public void messageReceived(IoSession session,Object message) throws Exception{
		String str = message.toString();
		System.out.println("TimeServerHandler接收:"+str);
		if(str.trim().equalsIgnoreCase("quit")){
			session.close(true);
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		JSONObject obj = JSONObject.fromObject(str); //处理接收到的数据放入Communication中recvNoAndFrameMap中
		Map<String,Class> classMap = new HashMap<String,Class>();
		classMap.put("dataList",TmFrame.class);
		classMap.put("itemList",DataItem.class);
		Frame frame = (Frame)JSONObject.toBean(obj,Frame.class,classMap);
		String fpcNo = frame.getFrontPCNo();
		if("00".equals(frame.getDataType())){
			if("01".equals(frame.getDldp())){ //前置机登录
//				long curFpcNo = Communication.gFpcConnectTimes; //Long.parseLong(frame.getFrontPCNo()); //此处不能拿前置机报文中的fpcNo
				if(Communication.fpcNoDatabaseSet.contains(fpcNo)){ //判断frame.getFrontPCNo()是否存在于数据库中配置的前置机列表
					System.out.println(fpcNo+"登录成功!");
					Set<String> curFpcNoSet = Communication.fpcNoAndMinaSessionId.keySet();
					if(curFpcNoSet.contains(fpcNo)){ //判断是否已存在此编号的前置机连接，如果已存在则断开之前的连接
						Communication.fpcNoAndMinaSessionId.get(fpcNo).close(false);
					}
					Communication.fpcNoAndMinaSessionId.put(fpcNo,session);
					List<DataItem> itemList = new ArrayList<DataItem>(); //开始组登录确认包
					DataItem di = new DataItem("01","0"); //0确认、1不在线、2数据类型不存在、3数据项不存在、4未知原因
					itemList.add(di);
					List<TmFrame> dataList = new ArrayList<TmFrame>();
					TmFrame tm = new TmFrame("","","","",sdf.format(Calendar.getInstance().getTime()),"0","0",itemList); //3-1
					dataList.add(tm);
					String dataType = "00";
					Frame sendFrame = new Frame(fpcNo,"0",dataType,dataList,frame.getDldp()); //回复登录确认帧
//					JSONObject jObject = JSONObject.fromObject(sendFrame);
//					String confirmStr = jObject.toString();
//					System.out.println("sessionCreated发送登录确认帧:"+confirmStr);
					String reqOrRespNoStr = frame.getReqOrRespNo();
					try{
						byte bReqOrRespNo = (byte)(Short.parseShort(reqOrRespNoStr)&0x00ff);
						CommClassId cid = new CommClassId(fpcNo,(long)bReqOrRespNo); //0~255是主动上报帧变化范围
						sendFrame.setFrontPCNo(cid.getFpcId()+"");
						sendFrame.setReqOrRespNo(cid.getReqNo()+"");
						Communication.sendNoAndFrameMap.put(cid,sendFrame);
					}catch(NumberFormatException ee){
						System.out.println("接收登录帧不包含reqOrRespNo或此值非法!");
					}
				}else{
					System.out.println("此连接在登录时，所用的fpcNo非法(不存在中心库)!");
					session.close(false); //为false等socket发送完毕再关闭socket连接，为true是立即关闭
				}
			}else if("02".equals(frame.getDldp())){ //前置机心跳
				IoSession tempSession = Communication.fpcNoAndMinaSessionId.get(fpcNo);
				if(session.equals(tempSession)){
					List<DataItem> itemList = new ArrayList<DataItem>(); //开始组心跳确认包
					DataItem di = new DataItem("01","0"); //0确认、1不在线、2数据类型不存在、3数据项不存在、4未知原因
					itemList.add(di);
					List<TmFrame> dataList = new ArrayList<TmFrame>();
					TmFrame tm = new TmFrame("","","","",sdf.format(Calendar.getInstance().getTime()),"0","0",itemList);
					dataList.add(tm);
					String dataType = "00";
					Frame sendFrame = new Frame(fpcNo,"0",dataType,dataList,frame.getDldp()); //回复登录确认帧
					String reqOrRespNoStr = frame.getReqOrRespNo();
					try{
						byte bReqOrRespNo = (byte)(Short.parseShort(reqOrRespNoStr)&0x00ff);
						CommClassId cid = new CommClassId(fpcNo,(long)bReqOrRespNo); //0~255是主动上报帧变化范围
						sendFrame.setFrontPCNo(cid.getFpcId()+"");
						sendFrame.setReqOrRespNo(cid.getReqNo()+"");
						Communication.sendNoAndFrameMap.put(cid,sendFrame);
					}catch(NumberFormatException ee){
						System.out.println("接收心跳帧不包含reqOrRespNo或此值非法!");
					}
				}
			}else if("03".equals(frame.getDldp())){ //前置机退出
				Communication.fpcNoAndMinaSessionId.remove(fpcNo);
			}else if(frame.getDldp() == null || "".equals(frame.getDldp())){ //前置机回确认或否认等
				long reqOrRespNo = Long.parseLong(frame.getReqOrRespNo());
				if(reqOrRespNo >= 0 && reqOrRespNo <= 0xff){
					
				}else{ //回请求确认或否认
					CommClassId key = new CommClassId(fpcNo,reqOrRespNo);
					Communication.recvNoAndFrameMap.put(key,frame);
				}
			}
		}else if("0D".equals(frame.getDataType())){ //在线终端列表
			List<TmFrame> tmFrameList = frame.getDataList();
			Set<Long> curTmIdSet = new HashSet<Long>();
			System.out.println("--------------------------------------------在线终端----------------------------------------------");
			for(TmFrame tm:tmFrameList){
				System.out.print(tm.getRtua()+"\t");
				curTmIdSet.add(Long.parseLong(tm.getRtua()));
			}
			System.out.println();
			System.out.println("--------------------------------------------------------------------------------------------------");
			updateFpcNoTmIdRelationship(fpcNo,curTmIdSet,session);
			List<DataItem> itemList = new ArrayList<DataItem>(); //开始组在线终端确认包
			DataItem di = new DataItem("01","0"); //0确认、1不在线、2数据类型不存在、3数据项不存在、4未知原因
			itemList.add(di);
			List<TmFrame> dataList = new ArrayList<TmFrame>();
			TmFrame tm = new TmFrame("","","","",sdf.format(Calendar.getInstance().getTime()),"0","0",itemList);
			dataList.add(tm);
			String dataType = "00";
			Frame sendFrame = new Frame(fpcNo,"0",dataType,dataList,frame.getDldp()); //回复在线终端确认帧
			String reqOrRespNoStr = frame.getReqOrRespNo();
			try{
				byte bReqOrRespNo = (byte)(Short.parseShort(reqOrRespNoStr)&0x00ff);
				CommClassId cid = new CommClassId(fpcNo,(long)bReqOrRespNo); //0~255是主动上报帧变化范围
				sendFrame.setFrontPCNo(cid.getFpcId()+"");
				sendFrame.setReqOrRespNo(cid.getReqNo()+"");
				Communication.sendNoAndFrameMap.put(cid,sendFrame);
			}catch(NumberFormatException ee){
				System.out.println("接收在线终端帧不包含reqOrRespNo或此值非法!");
			}
		}else{
			long reqOrRespNo = Long.parseLong(frame.getReqOrRespNo());
			if(reqOrRespNo >= 0 && reqOrRespNo <= 0xff){ //定义0~255是主动上报帧变化范围
				CommClassId key = new CommClassId(fpcNo,reqOrRespNo);
				Communication.recvNoAndFrameMap.put(key,frame);
			}else{
				CommClassId key = new CommClassId(fpcNo,reqOrRespNo);
				Communication.recvNoAndFrameMap.put(key,frame);
			}
		}
	}

	@Override
	public void messageSent(IoSession session,Object message) throws Exception{
		String str = message.toString();
		System.out.println("TimeServerHandler发送:"+str);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception{
		System.out.println(session.getRemoteAddress().toString()+"已断开!");
		Set<String> keySet = Communication.fpcNoAndMinaSessionId.keySet();
		for(String key:keySet){
			IoSession tempSession = Communication.fpcNoAndMinaSessionId.get(key);
			if(tempSession.equals(session)){
				System.out.println("前置机序号"+key+"从Communication.fpcNoAndMinaSessionId中已移除!");
				Communication.fpcNoAndMinaSessionId.remove(key);
				Set<String> tempKeySet = Communication.fpcNoTmIdMap.keySet();
				for(String tempKey:tempKeySet){
					if(key.equals(tempKey)){
						System.out.println("前置机序号"+tempKey+"从Communication.fpcNoTmIdMap中已移除!");
						Communication.fpcNoTmIdMap.remove(tempKey);
						break;
					}
				}
				break;
			}
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception{
		System.out.println(session.getRemoteAddress().toString()+"已连接!");
		Communication.gFpcConnectTimes ++;
	}

	@Override
	public void sessionIdle(IoSession session,IdleStatus status) throws Exception{
//		System.out.println("IDLE "+session.getIdleCount(status));
	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception{
//		System.out.println("sessionOpened...");
	}
}









