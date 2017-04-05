package com.nk.vo;

import java.io.Serializable;

public class INF_TERMINAL_ACCOUNT_VO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String  terminal_ormeterid;
	private String 	equipment_name;
	private	String 	rated_voltage;
	private	String	rated_current;
	private	String 	rated_power;
	private	String 	mp_id;
	private	String 	url;
	private	String 	remarks;
	private String dev_type;
	
	public String getDev_type() {
			return dev_type;
	}
	public void setDev_type(String dev_type) {
		this.dev_type = dev_type;
	}
	public String getTerminal_ormeterid() {
		return terminal_ormeterid;
	}
	public void setTerminal_ormeterid(String terminal_ormeterid) {
		this.terminal_ormeterid = terminal_ormeterid;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	public String getRated_voltage() {
		return rated_voltage;
	}
	public void setRated_voltage(String rated_voltage) {
		this.rated_voltage = rated_voltage;
	}
	public String getRated_current() {
		return rated_current;
	}
	public void setRated_current(String rated_current) {
		this.rated_current = rated_current;
	}
	public String getRated_power() {
		return rated_power;
	}
	public void setRated_power(String rated_power) {
		this.rated_power = rated_power;
	}
	public String getMp_id() {
		return mp_id;
	}
	public void setMp_id(String mp_id) {
		this.mp_id = mp_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "INF_TERMINAL_ACCOUNT_VO [terminal_ormeterid="
				+ terminal_ormeterid + ", equipment_name=" + equipment_name
				+ ", rated_voltage=" + rated_voltage + ", rated_current="
				+ rated_current + ", rated_power=" + rated_power + ", mp_id="
				+ mp_id + ", url=" + url + ", remarks=" + remarks + "]";
	}
	
}
