package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class StockInfo implements Serializable {
	private static final long serialVersionUID = 3067933822953341311L;
	
	private Long oid;
	private String stockNum;
	private Long stockTypeOid;
	private Long warehouseOid;
	private String stockPerson;
	private Date stockDate;
	private Long itemOid;
	private Double stockAmt;
	private Double unitPrice;
	private Double totalPrice;
	private Long customerOid;
	private String remark;
	private String status;
	private String createBy;
	private Date createDate;
	private String lastModifyBy;
	private Date lastModifyDate;

	private String stockItemType;
	private Long bomDetailOid;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public Long getStockTypeOid() {
		return stockTypeOid;
	}

	public void setStockTypeOid(Long stockTypeOid) {
		this.stockTypeOid = stockTypeOid;
	}

	public Long getWarehouseOid() {
		return warehouseOid;
	}

	public void setWarehouseOid(Long warehouseOid) {
		this.warehouseOid = warehouseOid;
	}

	public String getStockPerson() {
		return stockPerson;
	}

	public void setStockPerson(String stockPerson) {
		this.stockPerson = stockPerson;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public Long getItemOid() {
		return itemOid;
	}

	public void setItemOid(Long itemOid) {
		this.itemOid = itemOid;
	}

	public Double getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(Double stockAmt) {
		this.stockAmt = stockAmt;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getCustomerOid() {
		return customerOid;
	}

	public void setCustomerOid(Long customerOid) {
		this.customerOid = customerOid;
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

	public String getStockItemType() {
		return stockItemType;
	}

	public void setStockItemType(String stockItemType) {
		this.stockItemType = stockItemType;
	}

	public Long getBomDetailOid() {
		return bomDetailOid;
	}

	public void setBomDetailOid(Long bomDetailOid) {
		this.bomDetailOid = bomDetailOid;
	}

}
