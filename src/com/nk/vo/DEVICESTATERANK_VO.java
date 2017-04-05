package com.nk.vo;

import java.io.Serializable;

public class DEVICESTATERANK_VO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String devices;//厂家名称
	private String rank;//厂家排名
	private String time; // 设备在线时长
	private String rate;//相对在线率
	
	public String getRate() {
		/*DecimalFormat    df   = new DecimalFormat("######0.00"); 
		return df.format(Double.valueOf(rate)+"%");*/
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public String getTime() {
		/*Long v = Long.valueOf(this.time);
		long day = v/(24*60*60*1000);
		long hour = (v-day*24*60*60*1000)/(60*60*1000); 
		long minute = (v -day*24*60*60*1000- hour*60*60*1000)/(60*1000);
		long second = (v -day*24*60*60*1000- hour*60*60*1000 - minute*60*1000)/1000;
		String s =day+"天"+ hour+ "小时" + minute + "分 " + second+"秒";
		return s;*/
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	
}
