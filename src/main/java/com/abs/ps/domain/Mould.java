package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class Mould implements Serializable {
	private static final long serialVersionUID = -1202551235288249912L;
	
	private Long oid;
	private String name;
	private Long customerOid;
	private String scale;
	private String hole;
	private String made;
	private String manu;
	private Date date;
	private Date outDate;
	private String place;
	private String remark;

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

	public Long getCustomerOid() {
		return customerOid;
	}

	public void setCustomerOid(Long customerOid) {
		this.customerOid = customerOid;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getHole() {
		return hole;
	}

	public void setHole(String hole) {
		this.hole = hole;
	}

	public String getMade() {
		return made;
	}

	public void setMade(String made) {
		this.made = made;
	}

	public String getManu() {
		return manu;
	}

	public void setManu(String manu) {
		this.manu = manu;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
