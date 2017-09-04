package com.abs.ps.web.dto;

import java.io.Serializable;
import java.util.Date;

public class BomDetailDto implements Serializable{
	/**
	 * 
	 */
	private String oid;
	private String bomOid;
	
	private String prodId;
	private String mouldOid;
	private String name;
	private String prodPicUrl;
	private String itemOid;
	
	private String pinkOid;
	private String alloyOid;
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
	private double unitPrice;
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
	private String createDate;
	
	private String bomNum;
	private String model;
	private String itemName;
	private String mouldName;
	private String pinkName;
	private String alloyName;
	private String itemColor;
	private String itemCode;
	
	private double materialUpc;
	private double pieUpc;
	private double damageUpc;
	private long materialType;
	private double safeAmt;
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getBomOid() {
		return bomOid;
	}
	public void setBomOid(String bomOid) {
		this.bomOid = bomOid;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getMouldOid() {
		return mouldOid;
	}
	public void setMouldOid(String mouldOid) {
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
	public String getItemOid() {
		return itemOid;
	}
	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}
	public String getPinkOid() {
		return pinkOid;
	}
	public void setPinkOid(String pinkOid) {
		this.pinkOid = pinkOid;
	}
	public String getAlloyOid() {
		return alloyOid;
	}
	public void setAlloyOid(String alloyOid) {
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
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getBomNum() {
		return bomNum;
	}
	public void setBomNum(String bomNum) {
		this.bomNum = bomNum;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getMouldName() {
		return mouldName;
	}
	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}
	public String getPinkName() {
		return pinkName;
	}
	public void setPinkName(String pinkName) {
		this.pinkName = pinkName;
	}
	public String getAlloyName() {
		return alloyName;
	}
	public void setAlloyName(String alloyName) {
		this.alloyName = alloyName;
	}
	public double getMaterialUpc() {
		return materialUpc;
	}
	public void setMaterialUpc(double materialUpc) {
		this.materialUpc = materialUpc;
	}
	public double getPieUpc() {
		return pieUpc;
	}
	public void setPieUpc(double pieUpc) {
		this.pieUpc = pieUpc;
	}
	public double getDamageUpc() {
		return damageUpc;
	}
	public void setDamageUpc(double damageUpc) {
		this.damageUpc = damageUpc;
	}
	public long getMaterialType() {
		return materialType;
	}
	public void setMaterialType(long materialType) {
		this.materialType = materialType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemColor() {
		return itemColor;
	}
	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}
	public double getSafeAmt() {
		return safeAmt;
	}
	public void setSafeAmt(double safeAmt) {
		this.safeAmt = safeAmt;
	}
	

}
