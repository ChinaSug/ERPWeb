package com.abs.ps.domain;

import java.io.Serializable;

public class SupportingData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7164945649060653328L;
	private Long oid;
	private String dataCode;
	private String dataName;
	private String dataType;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	
}
