package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable{
	private Long oid;
	private String name;
	private Long typeOid;
	private String model;
	private String color;
	private String unit;
	private Double unitPrice;
	private Integer safeAmt;
	private Long supplierOid;
	private Long customerOid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTypeOid() {
		return typeOid;
	}
	public void setTypeOid(Long typeOid) {
		this.typeOid = typeOid;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getSafeAmt() {
		return safeAmt;
	}
	public void setSafeAmt(Integer safeAmt) {
		this.safeAmt = safeAmt;
	}
	public Long getSupplierOid() {
		return supplierOid;
	}
	public void setSupplierOid(Long supplierOid) {
		this.supplierOid = supplierOid;
	}
	public Long getCustomerOid() {
		return customerOid;
	}
	public void setCustomerOid(Long customerOid) {
		this.customerOid = customerOid;
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
