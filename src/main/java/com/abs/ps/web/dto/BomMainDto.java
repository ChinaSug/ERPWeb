package com.abs.ps.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BomMainDto implements Serializable{
	/**
	 * 
	 */
	private String oid;
	private String bomNum;
	private String customerOid;
	private String confirmPerson;
	private String respPerson;
	private String status;
	private String createBy;
	private String createDate;
	private String lastModifyBy;
	private String lastModifyDate;
	
	private String disabled;
	
	private String customerName;
	
	private List<BomDetailDto> details;
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getCustomerOid() {
		return customerOid;
	}
	public void setCustomerOid(String customerOid) {
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public List<BomDetailDto> getDetails() {
		return details;
	}
	public void setDetails(List<BomDetailDto> details) {
		this.details = details;
	}
	
	
	
	
	
}