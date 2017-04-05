package com.nk.common;

import java.util.List;

public class Frame implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String frontPCNo;
	private String reqOrRespNo;
	private String dataType;
	private List<TmFrame> dataList;
	private String dldp;
	
	public Frame(){
		
	}
	public Frame(String frontPCNo,String reqOrRespNo,String dataType,List<TmFrame> dataList,String dldp){
		this.frontPCNo = frontPCNo;
		this.reqOrRespNo = reqOrRespNo;
		this.dataType = dataType;
		this.dataList = dataList;
		this.dldp = dldp;
	}
	
	public String getFrontPCNo(){
		return frontPCNo;
	}
	public void setFrontPCNo(String frontPCNo){
		this.frontPCNo = frontPCNo;
	}
	public String getReqOrRespNo(){
		return reqOrRespNo;
	}
	public void setReqOrRespNo(String reqOrRespNo){
		this.reqOrRespNo = reqOrRespNo;
	}
	public String getDataType(){
		return dataType;
	}
	public void setDataType(String dataType){
		this.dataType = dataType;
	}
	public List<TmFrame> getDataList(){
		return dataList;
	}
	public void setDataList(List<TmFrame> dataList){
		this.dataList = dataList;
	}
	public String getDldp(){
		return dldp;
	}
	public void setDldp(String dldp){
		this.dldp = dldp;
	}
}

