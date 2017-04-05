package com.nk.protocol_cfg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimerTask;

import com.nk.common.Configuration;
import com.nk.common.StaticVariable;

public class ProtocolInit extends TimerTask{
	
	private static boolean bThreadStarted = false;
	
	public void init(){
		try{
			Class.forName("org.sqlite.JDBC");
			String classpath = ProtocolInit.class.getResource("/").toURI().getPath();
			String WebFPCProtocolFilePathName = classpath + Configuration.propMap.get("system.sqliteDBFileName");
			System.out.println("正在初始化web与前置机通信协议库,协议配置sqlite文件路径:"+WebFPCProtocolFilePathName);
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+WebFPCProtocolFilePathName);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select *from di0di1config where useflag='1' order by di0di1");
			while(rs.next()){
//				System.out.print("afn:" + rs.getString("afn") + "\t");
//				System.out.print("gp:" + rs.getString("gp") + "\t");
//				System.out.print("di0di1:" + rs.getString("di0di1") + "\t");
//				System.out.print("len:" + rs.getString("len") + "\t");
//				System.out.print("format:" + rs.getString("format") + "\t");
//				System.out.print("desc:" + rs.getString("desc"));
//				System.out.println();
				Di0di1Config d = new Di0di1Config();
				d.setDir(rs.getString("dir"));
				d.setAfn(rs.getString("afn"));
				d.setGp(rs.getString("gp"));
				d.setFn(rs.getString("fn"));
				d.setDi0di1(rs.getString("di0di1"));
				d.setLen(rs.getString("len"));
				d.setFormat(rs.getString("format"));
				d.setDesc(rs.getString("desc"));
				d.setUnit(rs.getString("unit"));
				d.setSeq("null");
				d.setUseflag(rs.getString("useflag"));
				StaticVariable.gProtocolCfgList.add(d);
			}
			stmt.close();
			conn.close();
//			System.out.println("protocolCfgList.size():"+protocolCfgList.size());
//			JSONArray jsonArray = JSONArray.fromObject(protocolCfgList);
//			String jsonArrayStr = jsonArray.toString();
//			System.out.println("jsonArrayStr:\r\n"+jsonArrayStr);
			bThreadStarted = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		try{
			if(!bThreadStarted){
				init();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}









