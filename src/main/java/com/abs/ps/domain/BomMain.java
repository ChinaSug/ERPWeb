package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class BomMain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4630600717987389993L;
	private Long oid;
	private long customerOid;
	private String bomNum;
	private String confirmPerson;
	private String respPerson;
	private String status;
	private String createBy;
	private Date createDate;
	private String lastModifyBy;
	private Date lastModifyDate;
	private String disabled;
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	
	
	public long getCustomerOid() {
		return customerOid;
	}
	public void setCustomerOid(long customerOid) {
		this.customerOid = customerOid;
	}
	public String getBomNum() {
		return bomNum;
	}
	public void setBomNum(String bomNum) {
		this.bomNum = bomNum;
	}
	public String getConfirmPerson() {
		return confirmPerson;
	}
	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}
	public String getRespPerson() {
		return respPerson;
	}
	public void setRespPerson(String respPerson) {
		this.respPerson = respPerson;
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
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
}
