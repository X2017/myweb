package com.nk.pojo;

import java.io.Serializable;
import java.util.Date;

public class INF_MANUFACTURER implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer manufacturer_id;
	private Integer manufacturer_no;
	private String manufacturer_name;
	private String address;
	private Date start_time;
	private String man;
	private String phoneno;
	private Integer terminal_number;
	public Integer getManufacturer_id() {
		return manufacturer_id;
	}
	public void setManufacturer_id(Integer manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}
	public Integer getManufacturer_no() {
		return manufacturer_no;
	}
	public void setManufacturer_no(Integer manufacturer_no) {
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
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
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
	public Integer getTerminal_number() {
		return terminal_number;
	}
	public void setTerminal_number(Integer terminal_number) {
		this.terminal_number = terminal_number;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
