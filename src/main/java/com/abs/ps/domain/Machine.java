package com.abs.ps.domain;

import java.io.Serializable;

public class Machine implements Serializable {
	
	private static final long serialVersionUID = -2302379219331388906L;
	
	private Long oid;
	private String name;
	private String brand;
	private String type;
	private String tieBar;
	private String fpSize;
	private String openQuan;
	private String thick;
	private String screwDia;
	private String maxInjPrs;
	private String injWgt;
	private String remark;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTieBar() {
		return tieBar;
	}

	public void setTieBar(String tieBar) {
		this.tieBar = tieBar;
	}

	public String getFpSize() {
		return fpSize;
	}

	public void setFpSize(String fpSize) {
		this.fpSize = fpSize;
	}

	public String getOpenQuan() {
		return openQuan;
	}

	public void setOpenQuan(String openQuan) {
		this.openQuan = openQuan;
	}

	public String getThick() {
		return thick;
	}

	public void setThick(String thick) {
		this.thick = thick;
	}

	public String getScrewDia() {
		return screwDia;
	}

	public void setScrewDia(String screwDia) {
		this.screwDia = screwDia;
	}

	public String getMaxInjPrs() {
		return maxInjPrs;
	}

	public void setMaxInjPrs(String maxInjPrs) {
		this.maxInjPrs = maxInjPrs;
	}

	public String getInjWgt() {
		return injWgt;
	}

	public void setInjWgt(String injWgt) {
		this.injWgt = injWgt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
