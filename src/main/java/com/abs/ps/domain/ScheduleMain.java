package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class ScheduleMain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 226628828768839439L;
	private Long oid;
	private Long customerOid;
	private String prodId;
	private Date scheduleDate;
	private String respPerson;
	private String name;
	private String prodAmt;
	private Long itemOid;
	private String prodNum;
	private String unitConsume;
	private String productAmt;
	private String projectId;
	private Date projectDate;
	private String prodPicUrl;
	private String fetchStandardNum;
	private String fetchActualNum;
	private String shotStandardWgt;
	private String shotActualWgt;
	private String prodStandardWgt;
	private String prodActualWgt;
	private String pwStandardWgt;
	private String pwActualWgt;
	private Date mouldTgCompleteDate;
	private Date mouldActCompleteDate;
	private String tryAmt;
	private String actAmt;
	private String prodQc;
	private String prodStandardJob;
	private String prodFqc;
	private String prodPkg;
	private String prodShape;
	private String prodReport;
	private Date prodSignDate;
	private String prodVerify;
	private String prodGo;
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
	public Long getCustomerOid() {
		return customerOid;
	}
	public void setCustomerOid(Long customerOid) {
		this.customerOid = customerOid;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getRespPerson() {
		return respPerson;
	}
	public void setRespPerson(String respPerson) {
		this.respPerson = respPerson;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProdAmt() {
		return prodAmt;
	}
	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}
	public Long getItemOid() {
		return itemOid;
	}
	public void setItemOid(Long itemOid) {
		this.itemOid = itemOid;
	}
	public String getProdNum() {
		return prodNum;
	}
	public void setProdNum(String prodNum) {
		this.prodNum = prodNum;
	}
	public String getUnitConsume() {
		return unitConsume;
	}
	public void setUnitConsume(String unitConsume) {
		this.unitConsume = unitConsume;
	}
	public String getProductAmt() {
		return productAmt;
	}
	public void setProductAmt(String productAmt) {
		this.productAmt = productAmt;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Date getProjectDate() {
		return projectDate;
	}
	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}
	public String getProdPicUrl() {
		return prodPicUrl;
	}
	public void setProdPicUrl(String prodPicUrl) {
		this.prodPicUrl = prodPicUrl;
	}
	public String getFetchStandardNum() {
		return fetchStandardNum;
	}
	public void setFetchStandardNum(String fetchStandardNum) {
		this.fetchStandardNum = fetchStandardNum;
	}
	public String getFetchActualNum() {
		return fetchActualNum;
	}
	public void setFetchActualNum(String fetchActualNum) {
		this.fetchActualNum = fetchActualNum;
	}
	public String getShotStandardWgt() {
		return shotStandardWgt;
	}
	public void setShotStandardWgt(String shotStandardWgt) {
		this.shotStandardWgt = shotStandardWgt;
	}
	public String getShotActualWgt() {
		return shotActualWgt;
	}
	public void setShotActualWgt(String shotActualWgt) {
		this.shotActualWgt = shotActualWgt;
	}
	public String getProdStandardWgt() {
		return prodStandardWgt;
	}
	public void setProdStandardWgt(String prodStandardWgt) {
		this.prodStandardWgt = prodStandardWgt;
	}
	public String getProdActualWgt() {
		return prodActualWgt;
	}
	public void setProdActualWgt(String prodActualWgt) {
		this.prodActualWgt = prodActualWgt;
	}
	public String getPwStandardWgt() {
		return pwStandardWgt;
	}
	public void setPwStandardWgt(String pwStandardWgt) {
		this.pwStandardWgt = pwStandardWgt;
	}
	public String getPwActualWgt() {
		return pwActualWgt;
	}
	public void setPwActualWgt(String pwActualWgt) {
		this.pwActualWgt = pwActualWgt;
	}
	public Date getMouldTgCompleteDate() {
		return mouldTgCompleteDate;
	}
	public void setMouldTgCompleteDate(Date mouldTgCompleteDate) {
		this.mouldTgCompleteDate = mouldTgCompleteDate;
	}
	public Date getMouldActCompleteDate() {
		return mouldActCompleteDate;
	}
	public void setMouldActCompleteDate(Date mouldActCompleteDate) {
		this.mouldActCompleteDate = mouldActCompleteDate;
	}
	public String getTryAmt() {
		return tryAmt;
	}
	public void setTryAmt(String tryAmt) {
		this.tryAmt = tryAmt;
	}
	public String getActAmt() {
		return actAmt;
	}
	public void setActAmt(String actAmt) {
		this.actAmt = actAmt;
	}
	public String getProdQc() {
		return prodQc;
	}
	public void setProdQc(String prodQc) {
		this.prodQc = prodQc;
	}
	public String getProdStandardJob() {
		return prodStandardJob;
	}
	public void setProdStandardJob(String prodStandardJob) {
		this.prodStandardJob = prodStandardJob;
	}
	public String getProdFqc() {
		return prodFqc;
	}
	public void setProdFqc(String prodFqc) {
		this.prodFqc = prodFqc;
	}
	public String getProdPkg() {
		return prodPkg;
	}
	public void setProdPkg(String prodPkg) {
		this.prodPkg = prodPkg;
	}
	public String getProdShape() {
		return prodShape;
	}
	public void setProdShape(String prodShape) {
		this.prodShape = prodShape;
	}
	public String getProdReport() {
		return prodReport;
	}
	public void setProdReport(String prodReport) {
		this.prodReport = prodReport;
	}
	public Date getProdSignDate() {
		return prodSignDate;
	}
	public void setProdSignDate(Date prodSignDate) {
		this.prodSignDate = prodSignDate;
	}
	public String getProdVerify() {
		return prodVerify;
	}
	public void setProdVerify(String prodVerify) {
		this.prodVerify = prodVerify;
	}
	public String getProdGo() {
		return prodGo;
	}
	public void setProdGo(String prodGo) {
		this.prodGo = prodGo;
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
	@Override
	public String toString() {
		return "ScheduleMain [oid=" + oid + ", name=" + name + "]";
	}
	
}
