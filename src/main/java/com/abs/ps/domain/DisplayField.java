package com.abs.ps.domain;

import java.io.Serializable;

public class DisplayField implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2397226599171054811L;
	private Long oid;
	private Long centerOid;
	private String fieldCode;
	private String fieldName;
	private String fieldValue;
	private Integer sequence;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Long getCenterOid() {
		return centerOid;
	}
	public void setCenterOid(Long centerOid) {
		this.centerOid = centerOid;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	
}
