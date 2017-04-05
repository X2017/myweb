package com.nk.pojo;

import java.io.Serializable;
import java.util.Date;

public class INF_ALARM_RECORD  implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer terminal_ormeterid;
	private String	equipment_name;
	private Integer customer_id;
	private String customer_name;
	private Date	record_time;
	private String  alarm_code;
	private String  alarm_name;
	private String  alarm_way;
	private Integer alarm_state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTerminal_ormeterid() {
		return terminal_ormeterid;
	}
	public void setTerminal_ormeterid(Integer terminal_ormeterid) {
		this.terminal_ormeterid = terminal_ormeterid;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public Date getRecord_time() {
		return record_time;
	}
	public void setRecord_time(Date record_time) {
		this.record_time = record_time;
	}
	public String getAlarm_code() {
		return alarm_code;
	}
	public void setAlarm_code(String alarm_code) {
		this.alarm_code = alarm_code;
	}
	public String getAlarm_name() {
		return alarm_name;
	}
	public void setAlarm_name(String alarm_name) {
		this.alarm_name = alarm_name;
	}
	public String getAlarm_way() {
		return alarm_way;
	}
	public void setAlarm_way(String alarm_way) {
		this.alarm_way = alarm_way;
	}
	public Integer getAlarm_state() {
		return alarm_state;
	}
	public void setAlarm_state(Integer alarm_state) {
		this.alarm_state = alarm_state;
	}
	public String getCustomer_name(){
		return customer_name;
	}
	public void setCustomer_name(String customer_name){
		this.customer_name = customer_name;
	}
}
