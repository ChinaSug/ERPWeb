package com.abs.ps.domain;

import java.io.Serializable;

public class Warehouse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4942141177979700209L;
	private Long oid;
	private String name;
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
	
	
}
