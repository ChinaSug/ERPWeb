package com.abs.ps.web.dto;

import java.util.Date;
import java.util.List;

public class OrganizationDto {
	private long id;
	private String orgName;
	private String status;
	private boolean deletable;
	private String orgCode;
	private boolean needConfirm;
	private String createBy;
	private Date createDate;
	private String lastModifyBy;
	private Date lastModifyDate;
	private int assetsExpireAdvanceDateAmt;
	private int returnExpireAdvanceDateAmt;
	private Date expireDate;
	private int maxAppointDay;
	private String isWorkingDay;
	private List<String> periods;
	private String logoUrl;
	private String descr;
	private int activityAmt;
	private int hotNoticeAmt;
	private int hotActivityAmt;
	private int hotEnrollAmt;
	
	private String rotateImgUrl1;
	private String rotateImgUrl2;
	private String rotateImgUrl3;
	private String rotateImgUrl4;
	private String rotateImgUrl5;
	
	public long getId() {
		return id;
	} 
	public void setId(long id) {
		this.id = id; 
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isDeletable() {
		return deletable;
	}
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public boolean isNeedConfirm() {
		return needConfirm;
	}
	public void setNeedConfirm(boolean needConfirm) {
		this.needConfirm = needConfirm;
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
	public int getAssetsExpireAdvanceDateAmt() {
		return assetsExpireAdvanceDateAmt;
	}
	public void setAssetsExpireAdvanceDateAmt(int assetsExpireAdvanceDateAmt) {
		this.assetsExpireAdvanceDateAmt = assetsExpireAdvanceDateAmt;
	}
	public int getReturnExpireAdvanceDateAmt() {
		return returnExpireAdvanceDateAmt;
	}
	public void setReturnExpireAdvanceDateAmt(int returnExpireAdvanceDateAmt) {
		this.returnExpireAdvanceDateAmt = returnExpireAdvanceDateAmt;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public List<String> getPeriods() {
		return periods;
	}
	public void setPeriods(List<String> periods) {
		this.periods = periods;
	}
	public int getMaxAppointDay() {
		return maxAppointDay;
	}
	public void setMaxAppointDay(int maxAppointDay) {
		this.maxAppointDay = maxAppointDay;
	}
	public String getIsWorkingDay() {
		return isWorkingDay;
	}
	public void setIsWorkingDay(String isWorkingDay) {
		this.isWorkingDay = isWorkingDay;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public int getActivityAmt() {
		return activityAmt;
	}
	public void setActivityAmt(int activityAmt) {
		this.activityAmt = activityAmt;
	}
	public int getHotNoticeAmt() {
		return hotNoticeAmt;
	}
	public void setHotNoticeAmt(int hotNoticeAmt) {
		this.hotNoticeAmt = hotNoticeAmt;
	}
	public int getHotActivityAmt() {
		return hotActivityAmt;
	}
	public void setHotActivityAmt(int hotActivityAmt) {
		this.hotActivityAmt = hotActivityAmt;
	}
	public int getHotEnrollAmt() {
		return hotEnrollAmt;
	}
	public void setHotEnrollAmt(int hotEnrollAmt) {
		this.hotEnrollAmt = hotEnrollAmt;
	}
	public String getRotateImgUrl1() {
		return rotateImgUrl1;
	}
	public void setRotateImgUrl1(String rotateImgUrl1) {
		this.rotateImgUrl1 = rotateImgUrl1;
	}
	public String getRotateImgUrl2() {
		return rotateImgUrl2;
	}
	public void setRotateImgUrl2(String rotateImgUrl2) {
		this.rotateImgUrl2 = rotateImgUrl2;
	}
	public String getRotateImgUrl3() {
		return rotateImgUrl3;
	}
	public void setRotateImgUrl3(String rotateImgUrl3) {
		this.rotateImgUrl3 = rotateImgUrl3;
	}
	public String getRotateImgUrl4() {
		return rotateImgUrl4;
	}
	public void setRotateImgUrl4(String rotateImgUrl4) {
		this.rotateImgUrl4 = rotateImgUrl4;
	}
	public String getRotateImgUrl5() {
		return rotateImgUrl5;
	}
	public void setRotateImgUrl5(String rotateImgUrl5) {
		this.rotateImgUrl5 = rotateImgUrl5;
	}
	

}
