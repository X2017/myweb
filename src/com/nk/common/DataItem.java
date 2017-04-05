package com.nk.common;

public class DataItem implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String no;
	private String name;
	private String val;
	
	public DataItem(){
		
	}
	public DataItem(String no,String val){
		this.no = no;
		this.val = val;
	}
	public DataItem(String no,String name,String val){
		this.no = no;
		this.name = name;
		this.val = val;
	}
	
	public String getNo(){
		return no;
	}
	public void setNo(String no){
		this.no = no;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getVal(){
		return val;
	}
	public void setVal(String val){
		this.val = val;
	}
}
