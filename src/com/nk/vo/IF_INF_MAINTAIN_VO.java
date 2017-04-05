package com.nk.vo;

import java.io.Serializable;

public class IF_INF_MAINTAIN_VO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String terminal_ormeterid;
	private String equipment_name;
	private String maintain_date;
	private String maintain_man;
	private String maintain_record;
	private String next_maintain_date;
	private String next_maintain_man;
	private String maintain_content;
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
	public String getMaintain_date() {
		return maintain_date;
	}
	public void setMaintain_date(String maintain_date) {
		this.maintain_date = maintain_date;
	}
	public String getMaintain_man() {
		return maintain_man;
	}
	public void setMaintain_man(String maintain_man) {
		this.maintain_man = maintain_man;
	}
	public String getMaintain_record() {
		return maintain_record;
	}
	public void setMaintain_record(String maintain_record) {
		this.maintain_record = maintain_record;
	}
	public String getNext_maintain_date() {
		return next_maintain_date;
	}
	public void setNext_maintain_date(String next_maintain_date) {
		this.next_maintain_date = next_maintain_date;
	}
	public String getNext_maintain_man() {
		return next_maintain_man;
	}
	public void setNext_maintain_man(String next_maintain_man) {
		this.next_maintain_man = next_maintain_man;
	}
	public String getMaintain_content() {
		return maintain_content;
	}
	public void setMaintain_content(String maintain_content) {
		this.maintain_content = maintain_content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
