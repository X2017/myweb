package com.nk.pojo;

import java.io.Serializable;

public class INF_ONLLIERATE implements Serializable{
	private static final long serialVersionUID = 1L;
	private String front_name;//前置机名称
	private String equipment_name;//设备名称
	private String customer_name;//客户名称
	private Integer dev_type;//设备类型
	private Long onlineLength;//在线时长
	private Double onlinerate;//在线率
	private Long terminal_ormeterid;//设备表ID
	private Long time;
	public String getFront_name() {
		return front_name;
	}
	public void setFront_name(String front_name) {
		this.front_name = front_name;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	public Integer getDev_type() {
		return dev_type;
	}
	public void setDev_type(Integer dev_type) {
		this.dev_type = dev_type;
	}
	public Long getOnlineLength() {
		return onlineLength;
	}
	public void setOnlineLength(Long onlineLength) {
		this.onlineLength = onlineLength;
	}
	public Double getOnlinerate() {
		return onlinerate;
	}
	public void setOnlinerate(Double onlinerate) {
		this.onlinerate = onlinerate;
	}
	public Long getTerminal_ormeterid() {
		return terminal_ormeterid;
	}
	public void setTerminal_ormeterid(Long terminal_ormeterid) {
		this.terminal_ormeterid = terminal_ormeterid;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getCustomer_name(){
		return customer_name;
	}
	public void setCustomer_name(String customer_name){
		this.customer_name = customer_name;
	}
}




