package com.nk.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class DBUtil{
//	private static ComboPooledDataSource ds_pooled = new ComboPooledDataSource();
	private static DataSource ds_pooled = null;
	
	static{
		String driverName = Configuration.propMap.get("driverName");
		if(driverName == null)
			driverName = StaticVariable.DEF_DRIVER_NAME;
		String url = Configuration.propMap.get("jdbc.url");
		if(url == null)
			url = StaticVariable.DEF_URL;
		String user = Configuration.propMap.get("jdbc.username");
		if(user == null)
			user = StaticVariable.DEF_USER;
		String password = Configuration.propMap.get("jdbc.password");
		if(password == null)
			password = StaticVariable.DEF_PASS;
		try{
			Class.forName(driverName);
			
			DataSource ds_unpooled = DataSources.unpooledDataSource(url,user,password); //设置连接数据库的配置信息
			Map<String,Object> pool_conf = new HashMap<String,Object>();
			pool_conf.put("maxPoolSize",30); //设置最大连接数
			pool_conf.put("minPoolSize",3);
			pool_conf.put("initialPoolSize",3);
			pool_conf.put("maxIdleTime",60);
			pool_conf.put("checkoutTimeout",30000);
			pool_conf.put("acquireIncrement",10);
			pool_conf.put("acquireRetryAttempts",30);
			pool_conf.put("acquireRetryDelay",1000);
			pool_conf.put("breakAfterAcquireFailure","false");
			pool_conf.put("idleConnectionTestPeriod",60);
			pool_conf.put("numHelperThreads",6);
			ds_pooled = DataSources.pooledDataSource(ds_unpooled,pool_conf);
			
//			ds_pooled.setMaxPoolSize(300);
//			ds_pooled.setMinPoolSize(30);
//			ds_pooled.setInitialPoolSize(10);
//			ds_pooled.setAutoCommitOnClose(true);
//			ds_pooled.setPreferredTestQuery("select 1 from dual");
//			ds_pooled.setTestConnectionOnCheckout(false);
//			ds_pooled.setTestConnectionOnCheckin(false);
//			ds_pooled.setAcquireIncrement(10);
//			ds_pooled.setAcquireRetryAttempts(30);
//			ds_pooled.setAcquireRetryDelay(1000);
//			ds_pooled.setBreakAfterAcquireFailure(true);
//			ds_pooled.setMaxIdleTime(25000);
//			ds_pooled.setNumHelperThreads(6);
//			ds_pooled.setMaxStatements(0);
//			ds_pooled.setMaxStatementsPerConnection(0);
//			ds_pooled.setCheckoutTimeout(60000);
			
		}catch(ClassNotFoundException e){
			System.out.println(driverName+"驱动没有找到！");
		}catch(Exception e){
			System.out.println("没有得到连接！");
			e.printStackTrace();
		}
	}

	public static Connection getConnection(){
		try{
			return ds_pooled.getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn){
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public static void close(Statement stmt){
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			stmt = null;
		}
	}

	public static void close(ResultSet rs){
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			rs = null;
		}
	}

	public static void release(ResultSet rs,Statement stmt,Connection con){
		try{
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(con != null)
				con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private static Integer counter = 0;
	public static void main(String[] args){
		for(int i = 1; i <= 20; i++){
			new Thread(new Runnable(){
				public void run(){
					Connection conn = null;
					try{
						conn = DBUtil.getConnection();
						synchronized(counter){
							System.out.print(Thread.currentThread().getName());
							System.out.print("    counter = "+counter+++"  conn = "+conn);
							System.out.println();
							conn.prepareStatement("select * from t_user");
							conn.close();
						}
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}






