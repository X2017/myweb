package com.nk.common;

import java.util.List;

public class TmFrame implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String frameId;
	private String tmUUID;
	private String proto;
	private String rtua;
	private String getTime;
	private String fn;
	private String pn;
	private List<DataItem> itemList;
	
	public TmFrame(){
		
	}
	public TmFrame(String frameId,String tmUUID,String proto,String rtua,String getTime,String fn,String pn,List<DataItem> itemList){
		this.frameId = frameId;
		this.tmUUID = tmUUID;
		this.proto = proto;
		this.rtua = rtua;
		this.getTime = getTime;
		this.fn = fn;
		this.pn = pn;
		this.itemList = itemList;
	}
	
	public List<DataItem> getItemList(){
		return itemList;
	}
	public void setItemList(List<DataItem> itemList){
		this.itemList = itemList;
	}
	public String getFrameId(){
		return frameId;
	}
	public void setFrameId(String frameId){
		this.frameId = frameId;
	}
	public String getTmUUID(){
		return tmUUID;
	}
	public void setTmUUID(String tmUUID){
		this.tmUUID = tmUUID;
	}
	public String getProto(){
		return proto;
	}
	public void setProto(String proto){
		this.proto = proto;
	}
	public String getRtua(){
		return rtua;
	}
	public void setRtua(String rtua){
		this.rtua = rtua;
	}
	public String getGetTime(){
		return getTime;
	}
	public void setGetTime(String getTime){
		this.getTime = getTime;
	}
	public String getFn(){
		return fn;
	}
	public void setFn(String fn){
		this.fn = fn;
	}
	public String getPn(){
		return pn;
	}
	public void setPn(String pn){
		this.pn = pn;
	}
}
