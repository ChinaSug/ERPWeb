package com.abs.ps.domain;

public class DocumentNum {
	private Long oid;
	private Long orgOid;
	private String typeCode;
	private Integer docNum;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Long getOrgOid() {
		return orgOid;
	}
	public void setOrgOid(Long orgOid) {
		this.orgOid = orgOid;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Integer getDocNum() {
		return docNum;
	}
	public void setDocNum(Integer docNum) {
		this.docNum = docNum;
	}
	
}
