package com.nk.cq.mina;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PackageProtocol implements Serializable{
	private byte packLen[] = new byte[4];
	private String jsonStr;
	
	public byte[] getPackLen(){
		return packLen;
	}
	public void setPackLen(byte[] packLen){
		this.packLen = packLen;
	}
	public String getJsonStr(){
		return jsonStr;
	}
	public void setJsonStr(String jsonStr){
		this.jsonStr = jsonStr;
	}
}



