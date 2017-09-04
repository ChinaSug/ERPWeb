package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class CheckPointMain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7854875501098625830L;
	private Long oid;
	private String checkNum;
	private String peroidType;
	private String peroidYear;
	private String peroidTime;
	private String checkPerson;
	private Long warehouseOid;
	private String remark;
	private String status;
	private String createBy;
	private Date createDate;
	private String lastModifyBy;
	private Date lastModifyDate;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}
	
	public String getPeroidType() {
		return peroidType;
	}
	public void setPeroidType(String peroidType) {
		this.peroidType = peroidType;
	}
	public String getPeroidYear() {
		return peroidYear;
	}
	public void setPeroidYear(String peroidYear) {
		this.peroidYear = peroidYear;
	}
	public String getPeroidTime() {
		return peroidTime;
	}
	public void setPeroidTime(String peroidTime) {
		this.peroidTime = peroidTime;
	}
	public String getCheckPerson() {
		return checkPerson;
	}
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}
	public Long getWarehouseOid() {
		return warehouseOid;
	}
	public void setWarehouseOid(Long warehouseOid) {
		this.warehouseOid = warehouseOid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

}
