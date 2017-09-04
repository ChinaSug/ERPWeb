package com.abs.ps.domain;

import java.util.Date;

/**
 * @author 苏建生 2017-4-21
 */

public class HouseInfo implements java.io.Serializable {
	private static final long serialVersionUID = -3823059065736827967L;
	
	private Long oid;
	private String estateCode;
	private String estateName;
	private Long orgOid;
	private Long scopeOid;
	private String landCode;
	private String houseCode;
	private String status;
	private Integer count;
	private Double area;
	private Double price;
	private String address;
	private Date buildDate;
	private Long purpose;
	private Long assetFrom;
	private Long ownership;
	private String remark;
	private String img1Url;
	private String img2Url;
	private String img3Url;
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
	public String getEstateCode() {
		return estateCode;
	}
	public void setEstateCode(String estateCode) {
		this.estateCode = estateCode;
	}
	public String getEstateName() {
		return estateName;
	}
	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}
	public Long getOrgOid() {
		return orgOid;
	}
	public void setOrgOid(Long orgOid) {
		this.orgOid = orgOid;
	}
	public Long getScopeOid() {
		return scopeOid;
	}
	public void setScopeOid(Long scopeOid) {
		this.scopeOid = scopeOid;
	}
	public String getLandCode() {
		return landCode;
	}
	public void setLandCode(String landCode) {
		this.landCode = landCode;
	}
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImg1Url() {
		return img1Url;
	}
	public void setImg1Url(String img1Url) {
		this.img1Url = img1Url;
	}
	public String getImg2Url() {
		return img2Url;
	}
	public void setImg2Url(String img2Url) {
		this.img2Url = img2Url;
	}
	public String getImg3Url() {
		return img3Url;
	}
	public void setImg3Url(String img3Url) {
		this.img3Url = img3Url;
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
	public Long getPurpose() {
		return purpose;
	}
	public void setPurpose(Long purpose) {
		this.purpose = purpose;
	}
	public Long getAssetFrom() {
		return assetFrom;
	}
	public void setAssetFrom(Long assetFrom) {
		this.assetFrom = assetFrom;
	}
	public Long getOwnership() {
		return ownership;
	}
	public void setOwnership(Long ownership) {
		this.ownership = ownership;
	}

}