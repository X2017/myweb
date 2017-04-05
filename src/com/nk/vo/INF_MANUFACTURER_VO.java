package com.nk.vo;

import java.io.Serializable;

public class INF_MANUFACTURER_VO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String manufacturer_id;
	private String manufacturer_no;
	private String manufacturer_name;
	private String address;
	private String start_time;
	private String man;
	private String phoneno;
	private String terminal_number;
	public String getManufacturer_id() {
		return manufacturer_id;
	}
	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}
	public String getManufacturer_no() {
		return manufacturer_no;
	}
	public void setManufacturer_no(String manufacturer_no) {
		this.manufacturer_no = manufacturer_no;
	}
	public String getManufacturer_name() {
		return manufacturer_name;
	}
	public void setManufacturer_name(String manufacturer_name) {
		this.manufacturer_name = manufacturer_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getMan() {
		return man;
	}
	public void setMan(String man) {
		this.man = man;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getTerminal_number() {
		return terminal_number;
	}
	public void setTerminal_number(String terminal_number) {
		this.terminal_number = terminal_number;
	}
	@Override
	public String toString() {
		return "INF_MANUFACTURER_VO [manufacturer_id=" + manufacturer_id
				+ ", manufacturer_no=" + manufacturer_no
				+ ", manufacturer_name=" + manufacturer_name + ", address="
				+ address + ", start_time=" + start_time + ", man=" + man
				+ ", phoneno=" + phoneno + ", terminal_number="
				+ terminal_number + "]";
	}
	
	
}
