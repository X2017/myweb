package com.nk.pojo;

public class MeasureDevCustomer{
	private Long measureDevId;
	private Short type; //1为集中器，2为计量点
	private Integer customerId;
	private String customerName;
	public Long getMeasureDevId(){
		return measureDevId;
	}
	public void setMeasureDevId(Long measureDevId){
		this.measureDevId = measureDevId;
	}
	public Short getType(){
		return type;
	}
	public void setType(Short type){
		this.type = type;
	}
	public Integer getCustomerId(){
		return customerId;
	}
	public void setCustomerId(Integer customerId){
		this.customerId = customerId;
	}
	public String getCustomerName(){
		return customerName;
	}
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
}
