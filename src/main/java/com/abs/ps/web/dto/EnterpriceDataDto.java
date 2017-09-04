package com.abs.ps.web.dto;

public class EnterpriceDataDto {
	
	private long oid;
	private long entOid;
	private String period;
	private String type;
	private double outputValue;
	private double income;
	private double benefit;
	private double powerComsume;
	private double taxValue;
	
	private boolean deletable;
	
	private double waterConsume;
	private double outputAcc;
	private double outputLastYear;
	private double outputAccLastYear;
	private double changeRate;
	
	private String entName;
	private String entCode;
	private String specType;
	
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public long getEntOid() {
		return entOid;
	}
	public void setEntOid(long entOid) {
		this.entOid = entOid;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getOutputValue() {
		return outputValue;
	}
	public void setOutputValue(double outputValue) {
		this.outputValue = outputValue;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public double getBenefit() {
		return benefit;
	}
	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}
	public double getPowerComsume() {
		return powerComsume;
	}
	public void setPowerComsume(double powerComsume) {
		this.powerComsume = powerComsume;
	}
	public double getTaxValue() {
		return taxValue;
	}
	public void setTaxValue(double taxValue) {
		this.taxValue = taxValue;
	}
	public boolean isDeletable() {
		return deletable;
	}
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
	public double getWaterConsume() {
		return waterConsume;
	}
	public void setWaterConsume(double waterConsume) {
		this.waterConsume = waterConsume;
	}
	public double getOutputAcc() {
		return outputAcc;
	}
	public void setOutputAcc(double outputAcc) {
		this.outputAcc = outputAcc;
	}
	public double getOutputLastYear() {
		return outputLastYear;
	}
	public void setOutputLastYear(double outputLastYear) {
		this.outputLastYear = outputLastYear;
	}
	public double getOutputAccLastYear() {
		return outputAccLastYear;
	}
	public void setOutputAccLastYear(double outputAccLastYear) {
		this.outputAccLastYear = outputAccLastYear;
	}
	public double getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(double changeRate) {
		this.changeRate = changeRate;
	}
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	public String getEntCode() {
		return entCode;
	}
	public void setEntCode(String entCode) {
		this.entCode = entCode;
	}
	public String getSpecType() {
		return specType;
	}
	public void setSpecType(String specType) {
		this.specType = specType;
	}
	@Override
	public String toString() {
		return "EnterpriceDataDto [oid=" + oid + ", entOid=" + entOid + ", period=" + period + ", type=" + type
				+ ", outputValue=" + outputValue + ", income=" + income + ", benefit=" + benefit + ", powerComsume="
				+ powerComsume + ", taxValue=" + taxValue + ", deletable=" + deletable + ", waterConsume="
				+ waterConsume + ", outputAcc=" + outputAcc + ", outputLastYear=" + outputLastYear
				+ ", outputAccLastYear=" + outputAccLastYear + ", changeRate=" + changeRate + ", entName=" + entName
				+ ", entCode=" + entCode + ", specType=" + specType + "]";
	}
	
	
}
