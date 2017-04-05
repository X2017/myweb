package com.nk.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import net.sf.json.JSONObject;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoSession;

public class Communication extends TimerTask{
	
	private static final String fpcDbTableName = "if_inf_front_machine"; //inf_front_machine if_inf_front_machine
	public static long gFpcConnectTimes = 0;
	
	private ServletContext sc = null;
	private static int g_seq = 0;
	private static long g_reqNo = 10000; //请求号从10000开始，防止与登录、心跳、前置机主动上报的reqOrRespNo冲突
	
	public static Set<String> fpcNoDatabaseSet = new HashSet<String>();
	public static Map<CommClassId,Frame> recvNoAndFrameMap = new HashMap<CommClassId,Frame>();
	public static Map<CommClassId,Frame> sendNoAndFrameMap = new HashMap<CommClassId,Frame>();
	public static Map<String,Set<Long>> fpcNoTmIdMap = new HashMap<String,Set<Long>>();
	public static Map<String,IoSession> fpcNoAndMinaSessionId = new HashMap<String,IoSession>();
	
	static{
		try{
			Connection con = DBUtil.getConnection();
			Statement stmt = con.createStatement();
			String sql = "select distinct front_code from "+fpcDbTableName; 
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String fpcNo = rs.getString(1);
				fpcNoDatabaseSet.add(fpcNo);
			}
//			System.out.println("fpcNoDatabaseSet.size():"+fpcNoDatabaseSet.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Communication(ServletContext sc){
		this.sc = sc;
	}
	
	public CommClassId getCommClassId(Long tmId){
		IoAcceptor acceptor = (IoAcceptor)sc.getAttribute("acceptor");
		if(acceptor != null){
			Map<Long,IoSession> sessions = acceptor.getManagedSessions();
			Set<Long> keySet = sessions.keySet();
			Set<String> pcNoAndSessionIdKeySet = fpcNoAndMinaSessionId.keySet();
			Set<String> pcNoKeySet = fpcNoTmIdMap.keySet();
			for(Long key:keySet){
				IoSession session = sessions.get(key);
				for(String pcNoAndSessionIdKey:pcNoAndSessionIdKeySet){
					IoSession tempSession = fpcNoAndMinaSessionId.get(pcNoAndSessionIdKey);
					if(session.equals(tempSession)){
						for(String pcNoKey:pcNoKeySet){
							if(pcNoAndSessionIdKey.equals(pcNoKey)){
								Set<Long> tmList = fpcNoTmIdMap.get(pcNoKey);
								if(tmList.contains(tmId)){
									g_reqNo ++;
									return new CommClassId(pcNoKey,g_reqNo);
								}
								return null;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	public Frame getRespMsgFromFPC(String fpcId,long reqId){
		Set<CommClassId> keySet = recvNoAndFrameMap.keySet();
//		System.out.println("getRespMsgFromFPC->keySet.size():"+keySet.size());
//		System.out.println("fpcId:"+fpcId+"\treqId:"+reqId);
		for(CommClassId key:keySet){
//			System.out.println(key.getFpcId()+"\t"+key.getReqNo());
			if(key.getFpcId().equals(fpcId) && key.getReqNo().longValue() == reqId){
				Frame f = recvNoAndFrameMap.get(key);
				long frameReqOrRespNo = Long.parseLong(f.getReqOrRespNo());
				if(frameReqOrRespNo >= 0 && frameReqOrRespNo <= 0xff){ //除了前置机主动上报的帧需要从map中移除
					
				}else{
					recvNoAndFrameMap.remove(key);
				}
				return f;
			}
		}
		return null;
	}
	
	private void handleFpcInitiativeReportData(){
		Set<CommClassId> keySet = recvNoAndFrameMap.keySet();
		for(CommClassId key:keySet){
			Frame f = recvNoAndFrameMap.get(key);
			long frameReqOrRespNo = Long.parseLong(f.getReqOrRespNo());
			if(frameReqOrRespNo >= 0 && frameReqOrRespNo <= 0xff){ //读配置，前置机主动上报的帧写数据库、使用websocket推送到网页
				JSONObject jobj = JSONObject.fromObject(f);
				String initiativeReportFrame = jobj.toString();
				System.out.println("主动上报帧:"+initiativeReportFrame);
				
				recvNoAndFrameMap.remove(key);
			}
		}
	}
	
	@Override
	public void run(){
		try{
			handleFpcInitiativeReportData();
			
			IoAcceptor acceptor = (IoAcceptor)sc.getAttribute("acceptor");
			if(acceptor != null){
				Map<Long,IoSession> sessions = acceptor.getManagedSessions();
				Set<Long> keySet = sessions.keySet();
				boolean bFind = false;
				for(Long key:keySet){
					IoSession session = sessions.get(key);
					Set<CommClassId> sendKeySet = sendNoAndFrameMap.keySet();
					for(CommClassId cid:sendKeySet){
						IoSession tempSession = fpcNoAndMinaSessionId.get(cid.getFpcId());
						if(tempSession.equals(session)){
							Frame sendFrame = sendNoAndFrameMap.get(cid);
							JSONObject jobj = JSONObject.fromObject(sendFrame);
							String sendStr = jobj.toString();
//							System.out.println("run->sendStr:"+sendStr);
							session.write(sendStr);
							sendNoAndFrameMap.remove(cid);
							bFind = true;
							break;
						}
					}
					if(g_seq >= 255){
						g_seq = 0;
					}else{
						g_seq ++;
					}
					if(bFind){
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}





