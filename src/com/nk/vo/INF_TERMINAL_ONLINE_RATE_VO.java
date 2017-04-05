package com.nk.vo;

import java.io.Serializable;

public class INF_TERMINAL_ONLINE_RATE_VO implements Serializable{
	private static final long serialVersionUID = 1L;
	//前置机名称
		private String front_name;
		//终端名称
		private String equipment_name;
		//在线时长
		private String onlineLength;
		//在线率
		private String online_rate;//在线率，默认是 终端在线时长/前置机在线时长
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
		public String getOnlineLength() {
			return onlineLength;
		}
		public void setOnlineLength(String onlineLength) {
			this.onlineLength = onlineLength;
		}
		public String getOnline_rate() {
			return online_rate;
		}
		public void setOnline_rate(String online_rate) {
			this.online_rate = online_rate;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
		
}
