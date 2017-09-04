package com.abs.ps.domain;

import java.io.Serializable;

public class ItemType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1725422809067322589L;
	private Long oid;
	private String name;
	private String code;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
