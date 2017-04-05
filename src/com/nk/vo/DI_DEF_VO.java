package com.nk.vo;

import java.io.Serializable;

public class DI_DEF_VO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String di_item_no;
	private String di_no;
	private String di_name;
	private String di_group_name;
	private String di_item_name;
	private String di_item_format;
	private String di_item_length;
	private String use_unit;
	public String getDi_item_no(){
		return di_item_no;
	}
	public void setDi_item_no(String di_item_no){
		this.di_item_no = di_item_no;
	}
	public String getDi_no(){
		return di_no;
	}
	public void setDi_no(String di_no){
		this.di_no = di_no;
	}
	public String getDi_name(){
		return di_name;
	}
	public void setDi_name(String di_name){
		this.di_name = di_name;
	}
	public String getDi_group_name(){
		return di_group_name;
	}
	public void setDi_group_name(String di_group_name){
		this.di_group_name = di_group_name;
	}
	public String getDi_item_name(){
		return di_item_name;
	}
	public void setDi_item_name(String di_item_name){
		this.di_item_name = di_item_name;
	}
	public String getDi_item_format(){
		return di_item_format;
	}
	public void setDi_item_format(String di_item_format){
		this.di_item_format = di_item_format;
	}
	public String getDi_item_length(){
		return di_item_length;
	}
	public void setDi_item_length(String di_item_length){
		this.di_item_length = di_item_length;
	}
	public String getUse_unit(){
		return use_unit;
	}
	public void setUse_unit(String use_unit){
		this.use_unit = use_unit;
	}
}
