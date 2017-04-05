package com.nk.common;

import java.util.Date;
import java.util.Timer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.nk.cq.mina.MinaTimeServer;
import com.nk.protocol_cfg.ProtocolInit;

public class UserFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain){
		try{
			chain.doFilter(request,response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void destroy(){
		
	}
	@Override
	public void init(FilterConfig fonfig) throws ServletException{
		if(fonfig != null && fonfig.getServletContext() != null){
			Timer time = new Timer();
			ServletContext sc = fonfig.getServletContext();
			Communication gCommunicationInstance = new Communication(sc);
			sc.setAttribute("gCommunicationInstance",gCommunicationInstance); //StaticVariable.gCommunicationInstance
			time.schedule(gCommunicationInstance,new Date(),500); //0.5秒执行一次
			new MinaTimeServer(sc).serverStart(); //time.schedule(new MinaTimeServer(sc),new Date(),10000); //10秒执行一次，实质只调用了serverStart一次
			new ProtocolInit().init(); //time.schedule(new ProtocolInit(),new Date(),10000); //10秒执行一次，实质只调用了init一次
		}else{
			System.out.println("定时服务初始化失败!");
		}
	}
}
