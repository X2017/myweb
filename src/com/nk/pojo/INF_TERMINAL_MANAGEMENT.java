package com.nk.pojo;

import java.io.Serializable;
import java.util.Date;

public class INF_TERMINAL_MANAGEMENT implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer terminal_ormeterid;
	private	String  equipment_name;
	private	Integer	terminal_asset_no;
	private	String	terminal_model;
	private	String  terminal_manufacturer;
	
	private Date 	install_date;
	private	String  install_man;
	private	String	install_add;
	private Date    operation_date;
	private String 	operation_man;
	
	private String 	operation_explain;
	private	Integer operation_time;
	private Date 	fade_date;
	private String  fade_man;
	private String  fade_reason;
 	private	Integer terminal_state;
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
	public Integer getTerminal_asset_no() {
		return terminal_asset_no;
	}
	public void setTerminal_asset_no(Integer terminal_asset_no) {
		this.terminal_asset_no = terminal_asset_no;
	}
	public String getTerminal_model() {
		return terminal_model;
	}
	public void setTerminal_model(String terminal_model) {
		this.terminal_model = terminal_model;
	}
	public String getTerminal_manufacturer() {
		return terminal_manufacturer;
	}
	public void setTerminal_manufacturer(String terminal_manufacturer) {
		this.terminal_manufacturer = terminal_manufacturer;
	}
	public Date getInstall_date() {
		return install_date;
	}
	public void setInstall_date(Date install_date) {
		this.install_date = install_date;
	}
	public String getInstall_man() {
		return install_man;
	}
	public void setInstall_man(String install_man) {
		this.install_man = install_man;
	}
	public String getInstall_add() {
		return install_add;
	}
	public void setInstall_add(String install_add) {
		this.install_add = install_add;
	}
	public Date getOperation_date() {
		return operation_date;
	}
	public void setOperation_date(Date operation_date) {
		this.operation_date = operation_date;
	}
	public String getOperation_man() {
		return operation_man;
	}
	public void setOperation_man(String operation_man) {
		this.operation_man = operation_man;
	}
	public String getOperation_explain() {
		return operation_explain;
	}
	public void setOperation_explain(String operation_explain) {
		this.operation_explain = operation_explain;
	}
	public Integer getOperation_time() {
		return operation_time;
	}
	public void setOperation_time(Integer operation_time) {
		this.operation_time = operation_time;
	}
	public Date getFade_date() {
		return fade_date;
	}
	public void setFade_date(Date fade_date) {
		this.fade_date = fade_date;
	}
	public String getFade_man() {
		return fade_man;
	}
	public void setFade_man(String fade_man) {
		this.fade_man = fade_man;
	}
	public String getFade_reason() {
		return fade_reason;
	}
	public void setFade_reason(String fade_reason) {
		this.fade_reason = fade_reason;
	}
	public Integer getTerminal_state() {
		return terminal_state;
	}
	public void setTerminal_state(Integer terminal_state) {
		this.terminal_state = terminal_state;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 	
 	
}
