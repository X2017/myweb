package com.nk.common;

import java.util.List;

/**
 * 用户后台向前台返回的JSON对象
 */
@SuppressWarnings("serial")
public class Json implements java.io.Serializable{

	private boolean success = false;

	private String msg = "";

	private Object obj = null;

	private Integer id;

	private List<String> gidlist;
	
	private List<Integer> idlist;
	
	public List<String> getGidlist() {
		return gidlist;
	}

	public void setGidlist(List<String> gidlist) {
		this.gidlist = gidlist;
	}

	public List<Integer> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<Integer> idlist) {
		this.idlist = idlist;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean isSuccess(){
		return success;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public String getMsg(){
		return msg;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public Object getObj(){
		return obj;
	}

	public void setObj(Object obj){
		this.obj = obj;
	}
}







