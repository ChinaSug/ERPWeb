package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class DamageInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4597267907378126713L;
	
	private Long oid;
	private String number;
	private Long itemOid;
	private Long customerOid;
	private Double badAmt;
	private Double pieAmt;
	private Double unitPrice;
	private Double cost;
	private Double unitConsume;
	private Double itemUnitPrice;
	private Double itemFee;
	private Double pieUnitPrice;
	private Double pieFee;
	private String regSource;
	private Long departOid;
	private String status;
	private Date reportDate;
	private String createBy;
	private Date createDate;
	private String lastModifyBy;
	private Date lastModifyDate;

	private Long bomDetailOid;
	private String damageType;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getItemOid() {
		return itemOid;
	}

	public void setItemOid(Long itemOid) {
		this.itemOid = itemOid;
	}

	public Long getCustomerOid() {
		return customerOid;
	}

	public void setCustomerOid(Long customerOid) {
		this.customerOid = customerOid;
	}

	public Double getBadAmt() {
		return badAmt;
	}

	public void setBadAmt(Double badAmt) {
		this.badAmt = badAmt;
	}

	public Double getPieAmt() {
		return pieAmt;
	}

	public void setPieAmt(Double pieAmt) {
		this.pieAmt = pieAmt;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getUnitConsume() {
		return unitConsume;
	}

	public void setUnitConsume(Double unitConsume) {
		this.unitConsume = unitConsume;
	}

	public Double getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(Double itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public Double getItemFee() {
		return itemFee;
	}

	public void setItemFee(Double itemFee) {
		this.itemFee = itemFee;
	}

	public Double getPieUnitPrice() {
		return pieUnitPrice;
	}

	public void setPieUnitPrice(Double pieUnitPrice) {
		this.pieUnitPrice = pieUnitPrice;
	}

	public Double getPieFee() {
		return pieFee;
	}

	public void setPieFee(Double pieFee) {
		this.pieFee = pieFee;
	}

	public String getRegSource() {
		return regSource;
	}

	public void setRegSource(String regSource) {
		this.regSource = regSource;
	}

	public Long getDepartOid() {
		return departOid;
	}

	public void setDepartOid(Long departOid) {
		this.departOid = departOid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
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

	public Long getBomDetailOid() {
		return bomDetailOid;
	}

	public void setBomDetailOid(Long bomDetailOid) {
		this.bomDetailOid = bomDetailOid;
	}

	public String getDamageType() {
		return damageType;
	}

	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}

}
