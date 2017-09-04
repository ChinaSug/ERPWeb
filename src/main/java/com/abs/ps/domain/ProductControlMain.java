package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class ProductControlMain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6743668679202551692L;
	private Long oid;
	private String pcNum;
	private Long departOid;
	private String publishPerson;
	private Date publishDate;
	private String confirmPerson;
	private Long machineOid;
	private Long mouldOid;
	private String prodNum;
	private String stubBar;
	private String weight;
	private String period;
	private String caveNum;
	private String dailyTargetAmt;
	private Date pcDate;
	private String prodAmt;
	private String unitConsume;
	private String plasticType;
	private String plasticWeight;
	private Date targetStartDate;
	private Date targetEndDate;
	private String planTime;
	private Long bomDetailOid;
	private String status;
	private String createBy;
	private Date createDate;
	private String lastModifyBy;
	private Date lastModifyDate;
	
	private String materialName;
	private String materialNum;
	private String loss;
	private Long itemOid;
	
	private String remark;
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getPcNum() {
		return pcNum;
	}
	public void setPcNum(String pcNum) {
		this.pcNum = pcNum;
	}
	
	public Long getDepartOid() {
		return departOid;
	}
	public void setDepartOid(Long departOid) {
		this.departOid = departOid;
	}
	public String getPublishPerson() {
		return publishPerson;
	}
	public void setPublishPerson(String publishPerson) {
		this.publishPerson = publishPerson;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getConfirmPerson() {
		return confirmPerson;
	}
	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}
	public Long getMachineOid() {
		return machineOid;
	}
	public void setMachineOid(Long machineOid) {
		this.machineOid = machineOid;
	}
	public Long getMouldOid() {
		return mouldOid;
	}
	public void setMouldOid(Long mouldOid) {
		this.mouldOid = mouldOid;
	}
	public String getProdNum() {
		return prodNum;
	}
	public void setProdNum(String prodNum) {
		this.prodNum = prodNum;
	}
	public String getStubBar() {
		return stubBar;
	}
	public void setStubBar(String stubBar) {
		this.stubBar = stubBar;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getCaveNum() {
		return caveNum;
	}
	public void setCaveNum(String caveNum) {
		this.caveNum = caveNum;
	}
	public String getDailyTargetAmt() {
		return dailyTargetAmt;
	}
	public void setDailyTargetAmt(String dailyTargetAmt) {
		this.dailyTargetAmt = dailyTargetAmt;
	}
	public Date getPcDate() {
		return pcDate;
	}
	public void setPcDate(Date pcDate) {
		this.pcDate = pcDate;
	}
	public String getProdAmt() {
		return prodAmt;
	}
	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}
	public String getUnitConsume() {
		return unitConsume;
	}
	public void setUnitConsume(String unitConsume) {
		this.unitConsume = unitConsume;
	}
	public String getPlasticType() {
		return plasticType;
	}
	public void setPlasticType(String plasticType) {
		this.plasticType = plasticType;
	}
	public String getPlasticWeight() {
		return plasticWeight;
	}
	public void setPlasticWeight(String plasticWeight) {
		this.plasticWeight = plasticWeight;
	}
	public Date getTargetStartDate() {
		return targetStartDate;
	}
	public void setTargetStartDate(Date targetStartDate) {
		this.targetStartDate = targetStartDate;
	}
	public Date getTargetEndDate() {
		return targetEndDate;
	}
	public void setTargetEndDate(Date targetEndDate) {
		this.targetEndDate = targetEndDate;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	
	public Long getBomDetailOid() {
		return bomDetailOid;
	}
	public void setBomDetailOid(Long bomDetailOid) {
		this.bomDetailOid = bomDetailOid;
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
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialNum() {
		return materialNum;
	}
	public void setMaterialNum(String materialNum) {
		this.materialNum = materialNum;
	}
	public String getLoss() {
		return loss;
	}
	public void setLoss(String loss) {
		this.loss = loss;
	}
	public Long getItemOid() {
		return itemOid;
	}
	public void setItemOid(Long itemOid) {
		this.itemOid = itemOid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
