package com.abs.ps.domain;

import java.io.Serializable;

public class StockType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5786076591391100384L;
	private Long oid;
	private String name;
	private String dimension;
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
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
