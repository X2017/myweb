package com.nk.pojo;

import java.io.Serializable;
import java.util.Date;

public class INF_TERM implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer term_id;
	private String term_addr;
	private String term_name;
	private String phoneNo;
	private String simNo;
	private String ip;
	private String term_status;
	private String create_man;
	private Date create_date;
	private Short is_valid;
	private String term_asset_no;
	private String term_manufacturer;
	private String phase;
	private String remarks;
	private Integer customer_id;
	private Short term_pro;
	private String term_model;
	
	public INF_TERM() {
		super();
	}
	public INF_TERM(Integer term_id, String term_addr, String term_name,
			String phoneNo, String simNo, String ip, String term_status,
			String create_man, Date create_date, Short is_valid,
			String term_asset_no, String term_manufacturer, String phase,
			String remarks, Integer customer_id, Short term_pro,
			String term_model) {
		super();
		this.term_id = term_id;
		this.term_addr = term_addr;
		this.term_name = term_name;
		this.phoneNo = phoneNo;
		this.simNo = simNo;
		this.ip = ip;
		this.term_status = term_status;
		this.create_man = create_man;
		this.create_date = create_date;
		this.is_valid = is_valid;
		this.term_asset_no = term_asset_no;
		this.term_manufacturer = term_manufacturer;
		this.phase = phase;
		this.remarks = remarks;
		this.customer_id = customer_id;
		this.term_pro = term_pro;
		this.term_model = term_model;
	}
	public String getTerm_model() {
		return term_model;
	}
	public void setTerm_model(String term_model) {
		this.term_model = term_model;
	}
	public Integer getTerm_id(){
		return term_id;
	}
	public void setTerm_id(Integer term_id){
		this.term_id = term_id;
	}
	public String getTerm_addr(){
		return term_addr;
	}
	public void setTerm_addr(String term_addr){
		this.term_addr = term_addr;
	}
	public String getTerm_name(){
		return term_name;
	}
	public void setTerm_name(String term_name){
		this.term_name = term_name;
	}
	public String getPhoneNo(){
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
	}
	public String getSimNo(){
		return simNo;
	}
	public void setSimNo(String simNo){
		this.simNo = simNo;
	}
	public String getIp(){
		return ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	public String getTerm_status(){
		return term_status;
	}
	public void setTerm_status(String term_status){
		this.term_status = term_status;
	}
	public String getCreate_man(){
		return create_man;
	}
	public void setCreate_man(String create_man){
		this.create_man = create_man;
	}
	public Date getCreate_date(){
		return create_date;
	}
	public void setCreate_date(Date create_date){
		this.create_date = create_date;
	}
	public Short getIs_valid(){
		return is_valid;
	}
	public void setIs_valid(Short is_valid){
		this.is_valid = is_valid;
	}
	public String getTerm_asset_no(){
		return term_asset_no;
	}
	public void setTerm_asset_no(String term_asset_no){
		this.term_asset_no = term_asset_no;
	}
	public String getTerm_manufacturer(){
		return term_manufacturer;
	}
	public void setTerm_manufacturer(String term_manufacturer){
		this.term_manufacturer = term_manufacturer;
	}
	public String getPhase(){
		return phase;
	}
	public void setPhase(String phase){
		this.phase = phase;
	}
	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	public Integer getCustomer_id(){
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id){
		this.customer_id = customer_id;
	}
	public Short getTerm_pro(){
		return term_pro;
	}
	public void setTerm_pro(Short term_pro){
		this.term_pro = term_pro;
	}
}







