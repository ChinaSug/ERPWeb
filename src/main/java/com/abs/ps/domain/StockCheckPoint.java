package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class StockCheckPoint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1961702703203736457L;
	private Long oid;
	private String checkNum;
	private String checkPeriod;
	private String checkPerson;
	private Long itemOid;
	private Long warehouseOid;
	private Double currentStockAmt;
	private Double actualStockAmt;
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
	public String getCheckPeriod() {
		return checkPeriod;
	}
	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}
	public String getCheckPerson() {
		return checkPerson;
	}
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}
	public Long getItemOid() {
		return itemOid;
	}
	public void setItemOid(Long itemOid) {
		this.itemOid = itemOid;
	}
	public Long getWarehouseOid() {
		return warehouseOid;
	}
	public void setWarehouseOid(Long warehouseOid) {
		this.warehouseOid = warehouseOid;
	}
	public Double getCurrentStockAmt() {
		return currentStockAmt;
	}
	public void setCurrentStockAmt(Double currentStockAmt) {
		this.currentStockAmt = currentStockAmt;
	}
	public Double getActualStockAmt() {
		return actualStockAmt;
	}
	public void setActualStockAmt(Double actualStockAmt) {
		this.actualStockAmt = actualStockAmt;
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
