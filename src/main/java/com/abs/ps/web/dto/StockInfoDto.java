package com.abs.ps.web.dto;

/**
 * @update 苏建生 2017-8-21
 */
public class StockInfoDto {
	private String oid;
	private String stockNum; // 库存编号
	private String stockTypeOid; // 库存类型OID
	private String stockTypeName;
	private String warehouseOid; // 仓库oid
	private String stockPerson; // 入库人、出库人
	private String stockDate; // 入库日期、出库日期
	private String itemOid; // 物品OID
	private String stockAmt; // 入库数量、出库数量
	private String unitPrice; // 物品单价
	private String totalPrice;// 金额
	private String customerOid;// 客户OID(只出库用）
	private String supplierOid; // 供应商OID（只入库用）
	private String remark;// 备注
	private String status;
	private String createBy;
	private String createDate;
	private String lastModifyBy;
	private String lastModifyDate;
	private String disabled;
	private String isAdmin;
	// for list display
	private String warehouseName; // 仓库名称
	private String itemCode;// 物品编号
	private String itemName;// 物品名称
	private String supplierName;// 供应商名称
	private String customerName;

	private String itemTypeOid; // 物料类型Oid 
	private String stockInfoType;

	// @add Sug 2017-8-21
	private String stockItemType; // 报废类型(1：产品 2：物料)
	private String bomDetailOid; // 报废产品OID
	private String stockItemTypeName; // 报废类型名称(产品、 物料子类(五金、油墨等))

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public String getStockTypeOid() {
		return stockTypeOid;
	}

	public void setStockTypeOid(String stockTypeOid) {
		this.stockTypeOid = stockTypeOid;
	}

	public String getWarehouseOid() {
		return warehouseOid;
	}

	public void setWarehouseOid(String warehouseOid) {
		this.warehouseOid = warehouseOid;
	}

	public String getStockPerson() {
		return stockPerson;
	}

	public void setStockPerson(String stockPerson) {
		this.stockPerson = stockPerson;
	}

	public String getStockDate() {
		return stockDate;
	}

	public void setStockDate(String stockDate) {
		this.stockDate = stockDate;
	}

	public String getItemOid() {
		return itemOid;
	}

	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}

	public String getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(String stockAmt) {
		this.stockAmt = stockAmt;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCustomerOid() {
		return customerOid;
	}

	public void setCustomerOid(String customerOid) {
		this.customerOid = customerOid;
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

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStockTypeName() {
		return stockTypeName;
	}

	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}

	public String getSupplierOid() {
		return supplierOid;
	}

	public void setSupplierOid(String supplierOid) {
		this.supplierOid = supplierOid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStockInfoType() {
		return stockInfoType;
	}

	public void setStockInfoType(String stockInfoType) {
		this.stockInfoType = stockInfoType;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
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
	
	public String getStockItemTypeName() {
		return stockItemTypeName;
	}

	public void setStockItemTypeName(String stockItemTypeName) {
		this.stockItemTypeName = stockItemTypeName;
	}

	public String getItemTypeOid() {
		return itemTypeOid;
	}

	public void setItemTypeOid(String itemTypeOid) {
		this.itemTypeOid = itemTypeOid;
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
		StockInfoDto other = (StockInfoDto) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StockInfoDto [oid=" + oid + ", stockNum=" + stockNum + ", stockTypeOid=" + stockTypeOid
				+ ", stockTypeName=" + stockTypeName + ", warehouseOid=" + warehouseOid + ", stockPerson="
				+ stockPerson + ", stockDate=" + stockDate + ", itemOid=" + itemOid + ", stockAmt=" + stockAmt
				+ ", unitPrice=" + unitPrice + ", totalPrice=" + totalPrice + ", customerOid=" + customerOid
				+ ", supplierOid=" + supplierOid + ", remark=" + remark + ", status=" + status + ", createBy="
				+ createBy + ", createDate=" + createDate + ", lastModifyBy=" + lastModifyBy + ", lastModifyDate="
				+ lastModifyDate + ", disabled=" + disabled + ", isAdmin=" + isAdmin + ", warehouseName="
				+ warehouseName + ", itemCode=" + itemCode + ", itemName=" + itemName + ", supplierName="
				+ supplierName + ", customerName=" + customerName + ", itemTypeOid=" + itemTypeOid + ", stockInfoType="
				+ stockInfoType + ", stockItemType=" + stockItemType + ", bomDetailOid=" + bomDetailOid
				+ ", stockItemTypeName=" + stockItemTypeName + "]";
	}

}
