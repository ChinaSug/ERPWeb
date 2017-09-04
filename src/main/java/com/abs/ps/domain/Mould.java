package com.abs.ps.domain;

import java.io.Serializable;

public class Mould implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1202551235288249912L;
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
