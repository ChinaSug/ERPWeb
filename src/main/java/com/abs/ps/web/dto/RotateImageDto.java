package com.abs.ps.web.dto;

public class RotateImageDto {
	private long oid;
	private long orgOid;
	private String descr;
	private String imgUrl;
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public long getOrgOid() {
		return orgOid;
	}
	public void setOrgOid(long orgOid) {
		this.orgOid = orgOid;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
