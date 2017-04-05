package com.nk.vo;

import java.io.Serializable;

public class INF_FRONT_MACHINE_VO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String front_id;
	private	String front_code;
	private	String front_name;
	private	String common_log_ip;
	private	String webserver_ip;
	private	String webserver_port;
	private	String last_date;
	private	String state;
	public String getFront_id() {
		return front_id;
	}
	public void setFront_id(String front_id) {
		this.front_id = front_id;
	}
	public String getFront_code() {
		return front_code;
	}
	public void setFront_code(String front_code) {
		this.front_code = front_code;
	}
	public String getFront_name() {
		return front_name;
	}
	public void setFront_name(String front_name) {
		this.front_name = front_name;
	}
	public String getCommon_log_ip() {
		return common_log_ip;
	}
	public void setCommon_log_ip(String common_log_ip) {
		this.common_log_ip = common_log_ip;
	}
	public String getWebserver_ip() {
		return webserver_ip;
	}
	public void setWebserver_ip(String webserver_ip) {
		this.webserver_ip = webserver_ip;
	}
	public String getWebserver_port() {
		return webserver_port;
	}
	public void setWebserver_port(String webserver_port) {
		this.webserver_port = webserver_port;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "INF_FRONT_MACHINE_VO [front_id=" + front_id + ", front_code="
				+ front_code + ", front_name=" + front_name
				+ ", common_log_ip=" + common_log_ip + ", webserver_ip="
				+ webserver_ip + ", webserver_port=" + webserver_port
				+ ", last_date=" + last_date + ", state=" + state + "]";
	}
	
}
