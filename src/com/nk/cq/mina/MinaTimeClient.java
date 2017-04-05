package com.nk.cq.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.nk.common.Configuration;

public class MinaTimeClient{

	public static void main(String[] args){
		IoConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("logger",new LoggingFilter());
		connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
		connector.setHandler(new TimeClientHander());
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1",Integer.parseInt(Configuration.propMap.get("mina.port"))));
		connectFuture.awaitUninterruptibly(); //等待建立连接
		System.out.println("连接成功");

		IoSession session = connectFuture.getSession();
		Scanner sc = new Scanner(System.in);
		boolean quit = false;
		while(!quit){
			String str = sc.next();
			if(str.equalsIgnoreCase("quit")){
				quit = true;
			}
			session.write(str);
		}
		sc.close();
		if(session!=null){ //关闭
			if(session.isConnected()){
				session.getCloseFuture().awaitUninterruptibly();
			}
			connector.dispose(true);
		}
	}
}
