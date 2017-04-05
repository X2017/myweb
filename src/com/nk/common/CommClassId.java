package com.nk.common;

public class CommClassId{
	private String fpcId;
	private Long reqNo;
	
	public CommClassId(String fpcId,Long reqNo){
		this.fpcId = fpcId;
		this.reqNo = reqNo;
	}
	
	public String getFpcId(){
		return fpcId;
	}
	public void setFpcId(String fpcId){
		this.fpcId = fpcId;
	}
	public Long getReqNo(){
		return reqNo;
	}
	public void setReqNo(Long reqNo){
		this.reqNo = reqNo;
	}

	public boolean equals(Object other){
		if((this==other))
			return true;
		if((other==null))
			return false;
		if(!(other instanceof CommClassId))
			return false;
		CommClassId castOther = (CommClassId)other;
		return ((this.getFpcId()==castOther.getFpcId())||(this.getFpcId()!=null&&castOther.getFpcId()!=null&&this.getFpcId().equals(castOther.getFpcId())))&&((this.getReqNo()==castOther.getReqNo())||(this.getReqNo()!=null&&castOther.getReqNo()!=null&&this.getReqNo().equals(castOther.getReqNo())));
	}

	public int hashCode(){
		int result = 17;
		result = 37*result+(getFpcId()==null?0:this.getFpcId().hashCode());
		result = 37*result+(getReqNo()==null?0:this.getReqNo().hashCode());
		return result;
	}
}
