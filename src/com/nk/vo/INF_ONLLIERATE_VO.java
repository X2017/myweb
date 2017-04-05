package com.nk.vo;

import java.io.Serializable;

public class INF_ONLLIERATE_VO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String front_name;//前置机名称
	private String equipment_name;//设备名称
	private String customer_name;//客户名称
	private String dev_type;//设备类型
	private String onlineLength;//在线时长
	private String onlinerate;//在线率
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
	public String getDev_type() {
		//'设备类型 1前置机 2 集中器 3计量点表 4变压器',
		if(dev_type.equals("1")){
			return "前置机";
		} else if(dev_type.equals("2")){
			return "集中器";
		}else if(dev_type.equals("3")){
			return "计量点表";
		}else if(dev_type.equals("4")){
			return "变压器";
		}else{
			return "其他类型";
		}
	}
	public void setDev_type(String dev_type) {
		this.dev_type = dev_type;
	}
	public String getOnlineLength() {
		return onlineLength;
	}
	public void setOnlineLength(String onlineLength) {
		this.onlineLength = onlineLength;
	}
	public String getOnlinerate() {
		return onlinerate;
	}
	public void setOnlinerate(String onlinerate) {
		this.onlinerate = onlinerate;
	}
	public String getCustomer_name(){
		return customer_name;
	}
	public void setCustomer_name(String customer_name){
		this.customer_name = customer_name;
	}
}
