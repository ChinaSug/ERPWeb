package com.abs.ps.web.dto;

/**
 * @author 苏建生 2017-8-14
 */
public class DamageInfoDto {
	
	private String oid;
	private String number;
	private String itemOid;
	private String itemCode;
	private String itemName;
	private String iteyTypeOid;
	private String itemTypeName;
	private String customerOid;
	private String customerName;
	private String model;
	private String color;
	private String spec;
	private String badAmt;
	private String pieAmt;
	private String unitPrice;
	private String cost;
	private String unitConsume;
	private String itemUnitPrice;
	private String damagePrice; // 报废单价
	private String matPrice; // 材料单价
	private String piePrice; // 饼料单价
	private String itemFee;
	private String pieUnitPrice;
	private String pieFee;
	private String regSource;
	private String departOid;
	private String departName;
	private String status;
	private String reportDate;
	private String createBy;
	private String createDate;
	private String lastModifyBy;
	private String lastModifyDate;

	private long bomDetailOid;
	private String damageType;
	private String prodId;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String getIteyTypeOid() {
		return iteyTypeOid;
	}

	public void setIteyTypeOid(String iteyTypeOid) {
		this.iteyTypeOid = iteyTypeOid;
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

	public String getPieAmt() {
		return pieAmt;
	}

	public void setPieAmt(String pieAmt) {
		this.pieAmt = pieAmt;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDamagePrice() {
		return damagePrice;
	}

	public void setDamagePrice(String damPrice) {
		this.damagePrice = damPrice;
	}

	public String getMatPrice() {
		return matPrice;
	}

	public void setMatPrice(String matPrice) {
		this.matPrice = matPrice;
	}

	public String getPiePrice() {
		return piePrice;
	}

	public void setPiePrice(String piePrice) {
		this.piePrice = piePrice;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getUnitConsume() {
		return unitConsume;
	}

	public void setUnitConsume(String unitConsume) {
		this.unitConsume = unitConsume;
	}

	public String getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(String itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public String getItemFee() {
		return itemFee;
	}

	public void setItemFee(String itemFee) {
		this.itemFee = itemFee;
	}

	public String getPieUnitPrice() {
		return pieUnitPrice;
	}

	public void setPieUnitPrice(String pieUnitPrice) {
		this.pieUnitPrice = pieUnitPrice;
	}

	public String getPieFee() {
		return pieFee;
	}

	public void setPieFee(String pieFee) {
		this.pieFee = pieFee;
	}

	public String getRegSource() {
		return regSource;
	}

	public void setRegSource(String regSource) {
		this.regSource = regSource;
	}

	public String getBadAmt() {
		return badAmt;
	}

	public void setBadAmt(String badAmt) {
		this.badAmt = badAmt;
	}

	public String getDepartOid() {
		return departOid;
	}

	public void setDepartOid(String departOid) {
		this.departOid = departOid;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
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

	public String getLastModifyBy() {
		return lastModifyBy;
	}

	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getCustomerOid() {
		return customerOid;
	}

	public void setCustomerOid(String customerOid) {
		this.customerOid = customerOid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getBomDetailOid() {
		return bomDetailOid;
	}

	public void setBomDetailOid(long bomDetailOid) {
		this.bomDetailOid = bomDetailOid;
	}

	public String getDamageType() {
		return damageType;
	}

	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}
	
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
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
		DamageInfoDto other = (DamageInfoDto) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DamageInfoDto [oid=" + oid + ", number=" + number
				+ ", itemOid=" + itemOid + ", itemCode=" + itemCode
				+ ", itemName=" + itemName + ", iteyTypeOid=" + iteyTypeOid
				+ ", itemTypeName=" + itemTypeName + ", customerOid="
				+ customerOid + ", customerName=" + customerName + ", model="
				+ model + ", color=" + color + ", spec=" + spec + ", badAmt="
				+ badAmt + ", pieAmt=" + pieAmt + ", unitPrice=" + unitPrice
				+ ", cost=" + cost + ", unitConsume=" + unitConsume
				+ ", itemUnitPrice=" + itemUnitPrice + ", damagePrice=" + damagePrice
				+ ", matPrice=" + matPrice + ", piePrice=" + piePrice
				+ ", itemFee=" + itemFee + ", pieUnitPrice=" + pieUnitPrice
				+ ", pieFee=" + pieFee + ", regSource=" + regSource
				+ ", departOid=" + departOid + ", departName=" + departName
				+ ", status=" + status + ", reportDate=" + reportDate
				+ ", createBy=" + createBy + ", createDate=" + createDate
				+ ", lastModifyBy=" + lastModifyBy + ", lastModifyDate="
				+ lastModifyDate + ", bomDetailOid=" + bomDetailOid
				+ ", damageType=" + damageType + ", prodId=" + prodId + "]";
	}

}
