package com.abs.ps.domain;

import java.io.Serializable;

public class CheckPointDetail implements Serializable {
	private static final long serialVersionUID = 2021840352806338975L;
	
	private Long oid;
	private Long cpMainOid;
	private Long itemOid;
	private Double currentStockAmt;
	private Double actualStockAmt;
	private String remark;
	private String stockItemType;
	private Long bomDetailOid;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getCpMainOid() {
		return cpMainOid;
	}

	public void setCpMainOid(Long cpMainOid) {
		this.cpMainOid = cpMainOid;
	}

	public Long getItemOid() {
		return itemOid;
	}

	public void setItemOid(Long itemOid) {
		this.itemOid = itemOid;
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
