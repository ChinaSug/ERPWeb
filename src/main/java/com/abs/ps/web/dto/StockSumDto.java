package com.abs.ps.web.dto;

public class StockSumDto {
	private long cpOid;
	private String itemOid;
	private String whsOid;
	private String dimension;
	private double sumAmt;
	
	public long getCpOid() {
		return cpOid;
	}
	public void setCpOid(long cpOid) {
		this.cpOid = cpOid;
	}
	public String getItemOid() {
		return itemOid;
	}
	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}
	
	
	public String getWhsOid() {
		return whsOid;
	}
	public void setWhsOid(String whsOid) {
		this.whsOid = whsOid;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public double getSumAmt() {
		return sumAmt;
	}
	public void setSumAmt(double sumAmt) {
		this.sumAmt = sumAmt;
	}
}
