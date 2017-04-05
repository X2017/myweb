package com.nk.cq.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.nk.common.Configuration;

@SuppressWarnings("unused")
public class MinaTimeServer extends TimerTask{
	
	private static final int MAX_FRAME_ITEM_LEN = 4096;
	private static boolean bThreadStarted = false;
	private ServletContext sc = null;
	
	public MinaTimeServer(ServletContext sc){
		this.sc = sc;
	}
	
	public void serverStart(){
		try{
			IoAcceptor acceptor = new NioSocketAcceptor();
//			acceptor.getFilterChain().addLast("logger",new LoggingFilter()); //日志过滤器
//			acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8")))); //PrefixedStringCodecFactory TextLineCodecFactory
			acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new MyCodecFcatory(new MyEncoder(Charset.forName("UTF-8")),new MyDecoder(Charset.forName("UTF-8")))));
			acceptor.setHandler(new TimeServerHandler());
			acceptor.getSessionConfig().setReadBufferSize(MAX_FRAME_ITEM_LEN);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);
			acceptor.bind(new InetSocketAddress(Integer.parseInt(Configuration.propMap.get("mina.port"))));
			sc.setAttribute("acceptor",acceptor);
			bThreadStarted = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		try{
			if(!bThreadStarted){
				serverStart();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}








