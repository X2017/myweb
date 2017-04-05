package com.nk.protocol_cfg;

@SuppressWarnings("serial")
public class NecessaryProtocolCfg implements java.io.Serializable{
	private String dataType; //相当于afn
	private String dir; //0为web发往前置机，1为前置机发过来
	private String fn;
	private String diid;
	private String desc;
	public String getDataType(){
		return dataType;
	}
	public void setDataType(String dataType){
		this.dataType = dataType;
	}
	public String getDir(){
		return dir;
	}
	public void setDir(String dir){
		this.dir = dir;
	}
	public String getFn(){
		return fn;
	}
	public void setFn(String fn){
		this.fn = fn;
	}
	public String getDiid(){
		return diid;
	}
	public void setDiid(String diid){
		this.diid = diid;
	}
	public String getDesc(){
		return desc;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
}


