package com.nk.pojo;

import java.io.Serializable;
import java.util.Date;

public class INF_TERMINAL_ONLINE_RATE  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String equipment_name;
	private Long terminal_ormeterid;
	private Date happen_time;
	private Integer state;
	private Integer dev_type;
	private Integer action_type;
	private Date time_tag;
	//前置机名称
	//终端名称
	//上线时间
	//下线
	//在线率
	
	
	private Integer online_rate;//在线率，默认是 下线时间-上线时间/当前时间-上线时间


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getEquipment_name() {
		return equipment_name;
	}


	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}


	public Long getTerminal_ormeterid() {
		return terminal_ormeterid;
	}


	public void setTerminal_ormeterid(Long terminal_ormeterid) {
		this.terminal_ormeterid = terminal_ormeterid;
	}


	public Date getHappen_time() {
		return happen_time;
	}


	public void setHappen_time(Date happen_time) {
		this.happen_time = happen_time;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public Integer getDev_type() {
		return dev_type;
	}


	public void setDev_type(Integer dev_type) {
		this.dev_type = dev_type;
	}


	public Integer getAction_type() {
		return action_type;
	}


	public void setAction_type(Integer action_type) {
		this.action_type = action_type;
	}


	public Date getTime_tag() {
		return time_tag;
	}


	public void setTime_tag(Date time_tag) {
		this.time_tag = time_tag;
	}


	public Integer getOnline_rate() {
		return online_rate;
	}


	public void setOnline_rate(Integer online_rate) {
		this.online_rate = online_rate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
}
