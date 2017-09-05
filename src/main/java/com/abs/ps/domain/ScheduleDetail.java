package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class ScheduleDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2848392728275532011L;
	private Long oid;
	private Long scheduleOid;
	private String tryNum;
	private Date tryDate;
	private Date actualDate;
	private String result;
	private String tryCallback;
	private String endCallback;
	private Date createDate;
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEndCallback() {
		return endCallback;
	}
	public void setEndCallback(String endCallback) {
		this.endCallback = endCallback;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Long getScheduleOid() {
		return scheduleOid;
	}
	public void setScheduleOid(Long scheduleOid) {
		this.scheduleOid = scheduleOid;
	}
	public String getTryNum() {
		return tryNum;
	}
	public void setTryNum(String tryNum) {
		this.tryNum = tryNum;
	}
	public Date getTryDate() {
		return tryDate;
	}
	public void setTryDate(Date tryDate) {
		this.tryDate = tryDate;
	}
	public Date getActualDate() {
		return actualDate;
	}
	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTryCallback() {
		return tryCallback;
	}
	public void setTryCallback(String tryCallback) {
		this.tryCallback = tryCallback;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
