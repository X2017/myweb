package com.nk.pojo;

import java.io.Serializable;

public class INF_TERMINAL_ACCOUNT  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long terminal_ormeterid;
	private String equipment_name;
	private	Float rated_voltage;
	private	Float rated_current;
	private	Float rated_power;
	private	Long mp_id;
	private	String url;
	private	String remarks;
	private Integer dev_type;
	
	public Integer getDev_type() {
		return dev_type;
	}
	public void setDev_type(Integer dev_type) {
		this.dev_type = dev_type;
	}
	public Long getTerminal_ormeterid() {
		return terminal_ormeterid;
	}
	public void setTerminal_ormeterid(Long terminal_ormeterid) {
		this.terminal_ormeterid = terminal_ormeterid;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	public Float getRated_voltage() {
		return rated_voltage;
	}
	public void setRated_voltage(Float rated_voltage) {
		this.rated_voltage = rated_voltage;
	}
	public Float getRated_current() {
		return rated_current;
	}
	public void setRated_current(Float rated_current) {
		this.rated_current = rated_current;
	}
	public Float getRated_power() {
		return rated_power;
	}
	public void setRated_power(Float rated_power) {
		this.rated_power = rated_power;
	}
	public Long getMp_id() {
		return mp_id;
	}
	public void setMp_id(Long mp_id) {
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
	
	
}
