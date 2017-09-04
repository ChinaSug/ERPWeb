package com.abs.ps.web.dto;

public class EnterpriceRequirementDto {
	private long oid;
	private long entOid;
	private long reqOid;
	
	private String reqName;
	
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public long getEntOid() {
		return entOid;
	}
	public void setEntOid(long entOid) {
		this.entOid = entOid;
	}
	public long getReqOid() {
		return reqOid;
	}
	public void setReqOid(long reqOid) {
		this.reqOid = reqOid;
	}
	public String getReqName() {
		return reqName;
	}
	public void setReqName(String reqName) {
		this.reqName = reqName;
	}
	
}
