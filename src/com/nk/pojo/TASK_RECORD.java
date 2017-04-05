package com.nk.pojo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class TASK_RECORD implements Serializable{
	private Integer ddp_id;
	private String comm_no;
	private String jn;
	private String tt;
	private Date ts;
	private Short ts_unit;
	private String ti;
	private Date rs;
	private Short rs_unit;
	private String ri;
	private Short task_template_id;
	private Short exe_status;
	public Integer getDdp_id(){
		return ddp_id;
	}
	public void setDdp_id(Integer ddp_id){
		this.ddp_id = ddp_id;
	}
	public String getComm_no(){
		return comm_no;
	}
	public void setComm_no(String comm_no){
		this.comm_no = comm_no;
	}
	public String getJn(){
		return jn;
	}
	public void setJn(String jn){
		this.jn = jn;
	}
	public String getTt(){
		return tt;
	}
	public void setTt(String tt){
		this.tt = tt;
	}
	public Date getTs(){
		return ts;
	}
	public void setTs(Date ts){
		this.ts = ts;
	}
	public Short getTs_unit(){
		return ts_unit;
	}
	public void setTs_unit(Short ts_unit){
		this.ts_unit = ts_unit;
	}
	public String getTi(){
		return ti;
	}
	public void setTi(String ti){
		this.ti = ti;
	}
	public Date getRs(){
		return rs;
	}
	public void setRs(Date rs){
		this.rs = rs;
	}
	public Short getRs_unit(){
		return rs_unit;
	}
	public void setRs_unit(Short rs_unit){
		this.rs_unit = rs_unit;
	}
	public String getRi(){
		return ri;
	}
	public void setRi(String ri){
		this.ri = ri;
	}
	public Short getTask_template_id(){
		return task_template_id;
	}
	public void setTask_template_id(Short task_template_id){
		this.task_template_id = task_template_id;
	}
	public Short getExe_status(){
		return exe_status;
	}
	public void setExe_status(Short exe_status){
		this.exe_status = exe_status;
	}
}
