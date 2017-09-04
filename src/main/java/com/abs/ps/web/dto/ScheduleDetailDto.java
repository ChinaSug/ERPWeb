package com.abs.ps.web.dto;

public class ScheduleDetailDto {
	private String oid;
	private String scheduleOid;
	private String tryNum;
	private String tryDate;
	private String actualDate;
	private String result;
	private String tryCallback;
	private String createDate;
	private String disabled;
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getScheduleOid() {
		return scheduleOid;
	}
	public void setScheduleOid(String scheduleOid) {
		this.scheduleOid = scheduleOid;
	}
	public String getTryNum() {
		return tryNum;
	}
	public void setTryNum(String tryNum) {
		this.tryNum = tryNum;
	}
	public String getTryDate() {
		return tryDate;
	}
	public void setTryDate(String tryDate) {
		this.tryDate = tryDate;
	}
	public String getActualDate() {
		return actualDate;
	}
	public void setActualDate(String actualDate) {
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	
}
