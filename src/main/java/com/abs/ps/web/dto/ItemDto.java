package com.abs.ps.web.dto;

public class ItemDto {
	private String oid;
	private String code; //物品编号
	private String name; //物品名称
	private String typeOid; //类型OID
	private String typeName;//类型名称
	private String model; //型号
	private String color;//颜色
	private String spec;//规格
	private String unit;//单位
	private String unitPrice;//物品单价
	private String damPrice;//报废单价
	private String matPrice;//材料单价
	private String piePrice;//饼料单价
	private String safeAmt;//安全库存量
	private String supplierOid;//供应商oid
	private String supplierName;//供应商名称
	private String customerOid;//客户OID
	private String customerName;//客户名称
	private String status;
	private String createBy;
	private String createDate;
	private String lastModifyBy;
	private String lastModifyDate;
	private String disabled;
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeOid() {
		return typeOid;
	}
	public void setTypeOid(String typeOid) {
		this.typeOid = typeOid;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getDamPrice() {
		return damPrice;
	}
	public void setDamPrice(String damPrice) {
		this.damPrice = damPrice;
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
	public String getSafeAmt() {
		return safeAmt;
	}
	public void setSafeAmt(String safeAmt) {
		this.safeAmt = safeAmt;
	}
	public String getSupplierOid() {
		return supplierOid;
	}
	public void setSupplierOid(String supplierOid) {
		this.supplierOid = supplierOid;
	}
	public String getCustomerOid() {
		return customerOid;
	}
	public void setCustomerOid(String customerOid) {
		this.customerOid = customerOid;
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
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	
}
