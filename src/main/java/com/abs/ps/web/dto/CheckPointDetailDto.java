package com.abs.ps.web.dto;

public class CheckPointDetailDto {
	private String oid;
	private String cpMainOid;
	private String itemOid;
	private String itemCode;
	private String itemName;
	private String itemTypeName;
	private String model; // 型号
	private String color;// 颜色
	private String spec;// 规格
	private String unit;// 单位
	private String safeAmt;// 安全库存量
	private String currentStockAmt;
	private String actualStockAmt;
	private String remark;
	private String disabled;

	private String stockItemType;
	private String bomDetailOid;
	private String bomNum;
	private String prodId;
	private String prodName;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getCpMainOid() {
		return cpMainOid;
	}

	public void setCpMainOid(String cpMainOid) {
		this.cpMainOid = cpMainOid;
	}

	public String getItemOid() {
		return itemOid;
	}

	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSafeAmt() {
		return safeAmt;
	}

	public void setSafeAmt(String safeAmt) {
		this.safeAmt = safeAmt;
	}

	public String getCurrentStockAmt() {
		return currentStockAmt;
	}

	public void setCurrentStockAmt(String currentStockAmt) {
		this.currentStockAmt = currentStockAmt;
	}

	public String getActualStockAmt() {
		return actualStockAmt;
	}

	public void setActualStockAmt(String actualStockAmt) {
		this.actualStockAmt = actualStockAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getStockItemType() {
		return stockItemType;
	}

	public void setStockItemType(String stockItemType) {
		this.stockItemType = stockItemType;
	}

	public String getBomDetailOid() {
		return bomDetailOid;
	}

	public void setBomDetailOid(String bomDetailOid) {
		this.bomDetailOid = bomDetailOid;
	}

	public String getBomNum() {
		return bomNum;
	}

	public void setBomNum(String bomNum) {
		this.bomNum = bomNum;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckPointDetailDto other = (CheckPointDetailDto) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CheckPointDetailDto [oid=" + oid + ", cpMainOid=" + cpMainOid
				+ ", itemOid=" + itemOid + ", itemCode=" + itemCode
				+ ", itemName=" + itemName + ", itemTypeName=" + itemTypeName
				+ ", model=" + model + ", color=" + color + ", spec=" + spec
				+ ", unit=" + unit + ", safeAmt=" + safeAmt
				+ ", currentStockAmt=" + currentStockAmt + ", actualStockAmt="
				+ actualStockAmt + ", remark=" + remark + ", disabled="
				+ disabled + ", stockItemType=" + stockItemType
				+ ", bomDetailOid=" + bomDetailOid + ", bomNum=" + bomNum
				+ ", prodId=" + prodId + ", prodName=" + prodName + "]";
	}
	
}
