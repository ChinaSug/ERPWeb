package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class Organization implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1549211048792516810L;

	private Long oid;
	private String orgCode;
	private String orgName;
	private String status;
	private String createBy;
	private Date createDate;
	private String lastModifyBy;
	private Date lastModifyDate;
	private Boolean needConfirm;
	private Integer assetsExpireAdvanceDateAmt;
	private Integer returnExpireAdvanceDateAmt;
	private Date expireDate;
	private Boolean isInternal;
	private Integer maxAppointDay;
	private Boolean isWorkingDay;
	private String logoUrl;
	private String descr;
	private Integer activityAmt;
	private Integer hotNoticeAmt;
	private Integer hotActivityAmt;
	private Integer hotEnrollAmt;
	
	private String rotateImgUrl1;
	private String rotateImgUrl2;
	private String rotateImgUrl3;
	private String rotateImgUrl4;
	private String rotateImgUrl5;
	
	private String imgUrl1;
	private String imgUrl2;
	private String imgUrl3;
	private String imgUrl4;
	private String imgUrl5;

	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Boolean getNeedConfirm() {
		return needConfirm;
	}
	public void setNeedConfirm(Boolean needConfirm) {
		this.needConfirm = needConfirm;
	}
	public Integer getAssetsExpireAdvanceDateAmt() {
		return assetsExpireAdvanceDateAmt;
	}
	public void setAssetsExpireAdvanceDateAmt(Integer assetsExpireAdvanceDateAmt) {
		this.assetsExpireAdvanceDateAmt = assetsExpireAdvanceDateAmt;
	}
	public Integer getReturnExpireAdvanceDateAmt() {
		return returnExpireAdvanceDateAmt;
	}
	public void setReturnExpireAdvanceDateAmt(Integer returnExpireAdvanceDateAmt) {
		this.returnExpireAdvanceDateAmt = returnExpireAdvanceDateAmt;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Integer getMaxAppointDay() {
		return maxAppointDay;
	}
	public void setMaxAppointDay(Integer maxAppointDay) {
		this.maxAppointDay = maxAppointDay;
	}
	public Boolean getIsWorkingDay() {
		return isWorkingDay;
	}
	public void setIsWorkingDay(Boolean isWorkingDay) {
		this.isWorkingDay = isWorkingDay;
	}
	public Boolean getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(Boolean isInternal) {
		this.isInternal = isInternal;
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
	public Integer getActivityAmt() {
		return activityAmt;
	}
	public void setActivityAmt(Integer activityAmt) {
		this.activityAmt = activityAmt;
	}
	public Integer getHotNoticeAmt() {
		return hotNoticeAmt;
	}
	public void setHotNoticeAmt(Integer hotNoticeAmt) {
		this.hotNoticeAmt = hotNoticeAmt;
	}
	public Integer getHotActivityAmt() {
		return hotActivityAmt;
	}
	public void setHotActivityAmt(Integer hotActivityAmt) {
		this.hotActivityAmt = hotActivityAmt;
	}
	public Integer getHotEnrollAmt() {
		return hotEnrollAmt;
	}
	public void setHotEnrollAmt(Integer hotEnrollAmt) {
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
	public String getImgUrl1() {
		return imgUrl1;
	}
	public void setImgUrl1(String imgUrl1) {
		this.imgUrl1 = imgUrl1;
	}
	public String getImgUrl2() {
		return imgUrl2;
	}
	public void setImgUrl2(String imgUrl2) {
		this.imgUrl2 = imgUrl2;
	}
	public String getImgUrl3() {
		return imgUrl3;
	}
	public void setImgUrl3(String imgUrl3) {
		this.imgUrl3 = imgUrl3;
	}
	public String getImgUrl4() {
		return imgUrl4;
	}
	public void setImgUrl4(String imgUrl4) {
		this.imgUrl4 = imgUrl4;
	}
	public String getImgUrl5() {
		return imgUrl5;
	}
	public void setImgUrl5(String imgUrl5) {
		this.imgUrl5 = imgUrl5;
	}
	
	
	
}
