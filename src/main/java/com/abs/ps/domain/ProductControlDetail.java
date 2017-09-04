package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class ProductControlDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9154878501743485706L;
	private Long oid;
	private Long pcOid;
	private String pcType;
	private Date produceDate;
	private String dutyId;
	private Double planProduceAmt;
	private Double actualProduceAmt;
	private Double goodProductAmt;
	private Double badProductAmt;
	private String efficiencyRate;
	private String badRate;
	private String remark;
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
	public Long getPcOid() {
		return pcOid;
	}
	public void setPcOid(Long pcOid) {
		this.pcOid = pcOid;
	}
	public String getPcType() {
		return pcType;
	}
	public void setPcType(String pcType) {
		this.pcType = pcType;
	}
	public Date getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}
	public String getDutyId() {
		return dutyId;
	}
	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}
	public Double getPlanProduceAmt() {
		return planProduceAmt;
	}
	public void setPlanProduceAmt(Double planProduceAmt) {
		this.planProduceAmt = planProduceAmt;
	}
	public Double getActualProduceAmt() {
		return actualProduceAmt;
	}
	public void setActualProduceAmt(Double actualProduceAmt) {
		this.actualProduceAmt = actualProduceAmt;
	}
	public Double getGoodProductAmt() {
		return goodProductAmt;
	}
	public void setGoodProductAmt(Double goodProductAmt) {
		this.goodProductAmt = goodProductAmt;
	}
	public Double getBadProductAmt() {
		return badProductAmt;
	}
	public void setBadProductAmt(Double badProductAmt) {
		this.badProductAmt = badProductAmt;
	}
	public String getEfficiencyRate() {
		return efficiencyRate;
	}
	public void setEfficiencyRate(String efficiencyRate) {
		this.efficiencyRate = efficiencyRate;
	}
	public String getBadRate() {
		return badRate;
	}
	public void setBadRate(String badRate) {
		this.badRate = badRate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
