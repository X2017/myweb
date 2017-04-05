package com.nk.common;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.nk.protocol_cfg.Di0di1Config;
import com.nk.protocol_cfg.NecessaryProtocolCfg;

public class StaticVariable{
	public static final String DEF_DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static final String DEF_URL = "jdbc:mysql://localhost:3306/NEEM_KT";
	public static final String DEF_USER = "root";
	public static final String DEF_PASS = "123456";
	
	public static final byte PROTOCOL_PREFIX_SIGN[] = {0x4E,0x4B}; //NK
	public static final byte PROTOCOL_PREFIX_CONTENTLEN[] = {0x00,0x00,0x00,0x00};
	public static final int maxTimeoutSeconds = Integer.parseInt(Configuration.propMap.get("system.maxTimeoutSeconds"));
	public static final String CHARSET_NAME = "UTF-8";
	public static final Charset CHARSET = Charset.forName(CHARSET_NAME);
	public static final int RECBUF_LEN = 100*1024;
	
	public static List<Di0di1Config> gProtocolCfgList = new ArrayList<Di0di1Config>();
	public static List<NecessaryProtocolCfg> getNecessaryProtocolCfgFromDbCfg(String dir,String dataType,String fn){
//		System.out.println(dir+"\t"+dataType+"\t"+fn);
		List<NecessaryProtocolCfg> reqList = new ArrayList<NecessaryProtocolCfg>();
		int size = gProtocolCfgList.size();
		for(int i = 0; i < size; i ++){
			Di0di1Config d = gProtocolCfgList.get(i);
//			System.out.println(d.getDir()+"\t"+d.getAfn()+"\t"+d.getFn());
			if(d.getDir().equals(dir) && d.getAfn().equals(dataType) && d.getFn().equals(fn)){
				NecessaryProtocolCfg n = new NecessaryProtocolCfg();
				n.setDataType(dataType);
				n.setDir(dir);
				n.setFn(fn);
				n.setDiid(d.getDi0di1());
				n.setDesc(d.getDesc());
				reqList.add(n);
			}
		}
		return reqList;
	}
	public static String getDiNameByDiId(String dir,String diId,List<NecessaryProtocolCfg> subCfg){
		int size = subCfg.size();
		for(int i = 0; i < size; i ++){
			NecessaryProtocolCfg n = subCfg.get(i);
			if(n.getDir().equals(dir) && n.getDiid().equals(diId)){
				return n.getDesc();
			}
		}
		return null;
	}
}
