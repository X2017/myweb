package com.nk.pojo;

import java.io.Serializable;
import java.util.Date;

public class INF_CUSTOMER implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer customer_id;
	private String customer_no;
	private String customer_name;
	private Short customer_type;
	private String industry_type;
	private Short customer_capacity;
	private Short customer_status;
	private String man;
	private String tel;
	private String emailAddr;
	private String address;
	private Short is_valid;
	private String create_man;
	private Date created_date;
	private String area;
	private String power_org;
	private String remarks;
	private String customer_status_name;
	private String customer_type_name;
	private String industry_name;
	private Integer company_id;
	private Integer para_id;
	
	public Short getCustomer_status(){
		return customer_status;
	}
	public void setCustomer_status(Short customer_status){
		this.customer_status = customer_status;
	}
	public Integer getCustomer_id(){
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id){
		this.customer_id = customer_id;
	}
	public String getCustomer_no(){
		return customer_no;
	}
	public void setCustomer_no(String customer_no){
		this.customer_no = customer_no;
	}
	public String getCustomer_name(){
		return customer_name;
	}
	public void setCustomer_name(String customer_name){
		this.customer_name = customer_name;
	}
	public Short getCustomer_type(){
		return customer_type;
	}
	public void setCustomer_type(Short customer_type){
		this.customer_type = customer_type;
	}
	public String getIndustry_type(){
		return industry_type;
	}
	public void setIndustry_type(String industry_type){
		this.industry_type = industry_type;
	}
	public Short getCustomer_capacity(){
		return customer_capacity;
	}
	public void setCustomer_capacity(Short customer_capacity){
		this.customer_capacity = customer_capacity;
	}
	public String getMan(){
		return man;
	}
	public void setMan(String man){
		this.man = man;
	}
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel = tel;
	}
	public String getEmailAddr(){
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr){
		this.emailAddr = emailAddr;
	}
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public Short getIs_valid(){
		return is_valid;
	}
	public void setIs_valid(Short is_valid){
		this.is_valid = is_valid;
	}
	public String getCreate_man(){
		return create_man;
	}
	public void setCreate_man(String create_man){
		this.create_man = create_man;
	}
	public Date getCreated_date(){
		return created_date;
	}
	public void setCreated_date(Date created_date){
		this.created_date = created_date;
	}
	public String getArea(){
		return area;
	}
	public void setArea(String area){
		this.area = area;
	}
	public String getPower_org(){
		return power_org;
	}
	public void setPower_org(String power_org){
		this.power_org = power_org;
	}
	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	public String getCustomer_status_name(){
		return customer_status_name;
	}
	public void setCustomer_status_name(String customer_status_name){
		this.customer_status_name = customer_status_name;
	}
	public String getCustomer_type_name(){
		return customer_type_name;
	}
	public void setCustomer_type_name(String customer_type_name){
		this.customer_type_name = customer_type_name;
	}
	public String getIndustry_name(){
		return industry_name;
	}
	public void setIndustry_name(String industry_name){
		this.industry_name = industry_name;
	}
	public Integer getCompany_id(){
		return company_id;
	}
	public void setCompany_id(Integer company_id){
		this.company_id = company_id;
	}
	public Integer getPara_id(){
		return para_id;
	}
	public void setPara_id(Integer para_id){
		this.para_id = para_id;
	}
	@Override
	public boolean equals(Object other){
		if((this == other))
			return true;
		if((other == null))
			return false;
		if(!(other instanceof INF_CUSTOMER))
			return false;
		INF_CUSTOMER castOther = (INF_CUSTOMER)other;
		if(this.getCustomer_id() == null || castOther.getCustomer_id() == null)
			return false;
		else if(this.getCustomer_id().equals(castOther.getCustomer_id()))
			return true;
		return false;
	}

	@Override
	public int hashCode(){
		int result = 17;
		result = 37 * result + (getCustomer_id() == null ? 0 : this.getCustomer_id().hashCode());
		result = 37 * result + (getCustomer_id() == null ? 0 : this.getCustomer_id().hashCode());
		return result;
	}
}


