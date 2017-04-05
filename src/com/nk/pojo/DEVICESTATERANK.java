package com.nk.pojo;

import java.io.Serializable;

public class DEVICESTATERANK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String devices;//厂家名称
	private Integer rank;//厂家排名
	private Long time; // 设备在线时长
	private Double rate;//相对在线率
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		
		this.rate = rate;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	
}
