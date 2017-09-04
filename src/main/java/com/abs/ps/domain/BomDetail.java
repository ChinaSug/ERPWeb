package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class BomDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7292908809958924710L;
	private Long oid;
	private Long bomOid;
	private String prodId;
	private Long mouldOid;
	private String name;
	private String prodPicUrl;
	private Long itemOid;
	private Long pinkOid;
	private Long alloyOid;
	private String projectId;
	private String fetchStandardNum;
	private String fetchActualNum;
	private String rawItemStdPrice;
	private String rawItemActPrice;
	private String shapeStdPd;
	private String shapeActPd;
	private String prodStandardWgt;
	private String prodActualWgt;
	private String pwStandardWgt;
	private String pwActualWgt;
	private String shotStandardWgt;
	private String shotActualWgt;
	private String stardardMachine;
	private String actualMachine;
	private String dailyPc;
	private Double unitPrice;
	private String avgProdAmt;
	private String reqAmt;
	private String pkgBoxMd;
	private String pkgSuckMd;
	private String pkgBagMd;
	private String pkgFilm;
	private String pkgRemark;
	private String remark;
	private String status;
	private String createBy;
	private Date createDate;
	
	private Double materialUpc;
	private Double pieUpc;
	private Double damageUpc;
	private Long materialType;
	private Double safeAmt;
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Long getBomOid() {
		return bomOid;
	}
	public void setBomOid(Long bomOid) {
		this.bomOid = bomOid;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public Long getMouldOid() {
		return mouldOid;
	}
	public void setMouldOid(Long mouldOid) {
		this.mouldOid = mouldOid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProdPicUrl() {
		return prodPicUrl;
	}
	public void setProdPicUrl(String prodPicUrl) {
		this.prodPicUrl = prodPicUrl;
	}
	public Long getItemOid() {
		return itemOid;
	}
	public void setItemOid(Long itemOid) {
		this.itemOid = itemOid;
	}
	public Long getPinkOid() {
		return pinkOid;
	}
	public void setPinkOid(Long pinkOid) {
		this.pinkOid = pinkOid;
	}
	public Long getAlloyOid() {
		return alloyOid;
	}
	public void setAlloyOid(Long alloyOid) {
		this.alloyOid = alloyOid;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public String getRawItemStdPrice() {
		return rawItemStdPrice;
	}
	public void setRawItemStdPrice(String rawItemStdPrice) {
		this.rawItemStdPrice = rawItemStdPrice;
	}
	public String getRawItemActPrice() {
		return rawItemActPrice;
	}
	public void setRawItemActPrice(String rawItemActPrice) {
		this.rawItemActPrice = rawItemActPrice;
	}
	public String getShapeStdPd() {
		return shapeStdPd;
	}
	public void setShapeStdPd(String shapeStdPd) {
		this.shapeStdPd = shapeStdPd;
	}
	public String getShapeActPd() {
		return shapeActPd;
	}
	public void setShapeActPd(String shapeActPd) {
		this.shapeActPd = shapeActPd;
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
	public String getStardardMachine() {
		return stardardMachine;
	}
	public void setStardardMachine(String stardardMachine) {
		this.stardardMachine = stardardMachine;
	}
	public String getActualMachine() {
		return actualMachine;
	}
	public void setActualMachine(String actualMachine) {
		this.actualMachine = actualMachine;
	}
	public String getDailyPc() {
		return dailyPc;
	}
	public void setDailyPc(String dailyPc) {
		this.dailyPc = dailyPc;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getAvgProdAmt() {
		return avgProdAmt;
	}
	public void setAvgProdAmt(String avgProdAmt) {
		this.avgProdAmt = avgProdAmt;
	}
	public String getReqAmt() {
		return reqAmt;
	}
	public void setReqAmt(String reqAmt) {
		this.reqAmt = reqAmt;
	}
	public String getPkgBoxMd() {
		return pkgBoxMd;
	}
	public void setPkgBoxMd(String pkgBoxMd) {
		this.pkgBoxMd = pkgBoxMd;
	}
	public String getPkgSuckMd() {
		return pkgSuckMd;
	}
	public void setPkgSuckMd(String pkgSuckMd) {
		this.pkgSuckMd = pkgSuckMd;
	}
	public String getPkgBagMd() {
		return pkgBagMd;
	}
	public void setPkgBagMd(String pkgBagMd) {
		this.pkgBagMd = pkgBagMd;
	}
	public String getPkgFilm() {
		return pkgFilm;
	}
	public void setPkgFilm(String pkgFilm) {
		this.pkgFilm = pkgFilm;
	}
	public String getPkgRemark() {
		return pkgRemark;
	}
	public void setPkgRemark(String pkgRemark) {
		this.pkgRemark = pkgRemark;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Double getMaterialUpc() {
		return materialUpc;
	}
	public void setMaterialUpc(Double materialUpc) {
		this.materialUpc = materialUpc;
	}
	public Double getPieUpc() {
		return pieUpc;
	}
	public void setPieUpc(Double pieUpc) {
		this.pieUpc = pieUpc;
	}
	public Double getDamageUpc() {
		return damageUpc;
	}
	public void setDamageUpc(Double damageUpc) {
		this.damageUpc = damageUpc;
	}
	public Long getMaterialType() {
		return materialType;
	}
	public void setMaterialType(Long materialType) {
		this.materialType = materialType;
	}
	public Double getSafeAmt() {
		return safeAmt;
	}
	public void setSafeAmt(Double safeAmt) {
		this.safeAmt = safeAmt;
	}
	

}
