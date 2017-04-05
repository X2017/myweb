package com.nk.vo;

public class TASK_TEMPLATE_VO{
	private String task_template_id;
	private String task_template_name;
	private String di_items;
	public String getTask_template_id(){
		return task_template_id;
	}
	public void setTask_template_id(String task_template_id){
		this.task_template_id = task_template_id;
	}
	public String getTask_template_name(){
		return task_template_name;
	}
	public void setTask_template_name(String task_template_name){
		this.task_template_name = task_template_name;
	}
	public String getDi_items(){
		return di_items;
	}
	public void setDi_items(String di_items){
		this.di_items = di_items;
	}
}
