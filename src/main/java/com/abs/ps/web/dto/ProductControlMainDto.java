package com.abs.ps.web.dto;

import java.util.List;

public class ProductControlMainDto {
	private String oid;
	private String pcNum;
	private String departOid;
	private String departName;
	private String publishPerson;
	private String publishDate;
	private String confirmPerson;
	private String machineOid;
	private String machineName;
	private String mouldOid;
	private String mouldName;
	private String prodNum;
	private String stubBar;
	private String weight;
	private String period;
	private String caveNum;
	private String dailyTargetAmt;
	private String pcDate;
	private String prodAmt;
	private String unitConsume;
	private String plasticType;
	private String plasticWeight;
	private String targetStartDate;
	private String targetEndDate;
	private String planTime;
	private String bomDetailOid;
	private String status;
	private String createBy;
	private String createDate;
	private String lastModifyBy;
	private String lastModifyDate;
	private List<ProductControlDetailDto> detailDtos;
	
	private String materialName;
	private String materialNum;
	private String loss;
	private long itemOid;
	
	private String itemCode;
	private String itemColor;
	
	private String bomNum;
	private String prodId; 
	private String remark;
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getPcNum() {
		return pcNum;
	}
	public void setPcNum(String pcNum) {
		this.pcNum = pcNum;
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
	public String getPublishPerson() {
		return publishPerson;
	}
	public void setPublishPerson(String publishPerson) {
		this.publishPerson = publishPerson;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getConfirmPerson() {
		return confirmPerson;
	}
	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}
	public String getMachineOid() {
		return machineOid;
	}
	public void setMachineOid(String machineOid) {
		this.machineOid = machineOid;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getMouldOid() {
		return mouldOid;
	}
	public void setMouldOid(String mouldOid) {
		this.mouldOid = mouldOid;
	}
	public String getMouldName() {
		return mouldName;
	}
	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}
	public String getProdNum() {
		return prodNum;
	}
	public void setProdNum(String prodNum) {
		this.prodNum = prodNum;
	}
	public String getStubBar() {
		return stubBar;
	}
	public void setStubBar(String stubBar) {
		this.stubBar = stubBar;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getCaveNum() {
		return caveNum;
	}
	public void setCaveNum(String caveNum) {
		this.caveNum = caveNum;
	}
	public String getDailyTargetAmt() {
		return dailyTargetAmt;
	}
	public void setDailyTargetAmt(String dailyTargetAmt) {
		this.dailyTargetAmt = dailyTargetAmt;
	}
	public String getPcDate() {
		return pcDate;
	}
	public void setPcDate(String pcDate) {
		this.pcDate = pcDate;
	}
	public String getProdAmt() {
		return prodAmt;
	}
	public void setProdAmt(String prodAmt) {
		this.prodAmt = prodAmt;
	}
	public String getUnitConsume() {
		return unitConsume;
	}
	public void setUnitConsume(String unitConsume) {
		this.unitConsume = unitConsume;
	}
	public String getPlasticType() {
		return plasticType;
	}
	public void setPlasticType(String plasticType) {
		this.plasticType = plasticType;
	}
	public String getPlasticWeight() {
		return plasticWeight;
	}
	public void setPlasticWeight(String plasticWeight) {
		this.plasticWeight = plasticWeight;
	}
	public String getTargetStartDate() {
		return targetStartDate;
	}
	public void setTargetStartDate(String targetStartDate) {
		this.targetStartDate = targetStartDate;
	}
	public String getTargetEndDate() {
		return targetEndDate;
	}
	public void setTargetEndDate(String targetEndDate) {
		this.targetEndDate = targetEndDate;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
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
	public List<ProductControlDetailDto> getDetailDtos() {
		return detailDtos;
	}
	public void setDetailDtos(List<ProductControlDetailDto> detailDtos) {
		this.detailDtos = detailDtos;
	}
	public String getBomDetailOid() {
		return bomDetailOid;
	}
	public void setBomDetailOid(String bomDetailOid) {
		this.bomDetailOid = bomDetailOid;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialNum() {
		return materialNum;
	}
	public void setMaterialNum(String materialNum) {
		this.materialNum = materialNum;
	}
	public String getLoss() {
		return loss;
	}
	public void setLoss(String loss) {
		this.loss = loss;
	}
	public long getItemOid() {
		return itemOid;
	}
	public void setItemOid(long itemOid) {
		this.itemOid = itemOid;
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
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getBomNum() {
		return bomNum;
	}
	public void setBomNum(String bomNum) {
		this.bomNum = bomNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
		ProductControlMainDto other = (ProductControlMainDto) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ProductControlMainDto [oid=" + oid + ", pcNum=" + pcNum
				+ ", departOid=" + departOid + ", departName=" + departName
				+ ", publishPerson=" + publishPerson + ", publishDate="
				+ publishDate + ", confirmPerson=" + confirmPerson
				+ ", machineOid=" + machineOid + ", machineName=" + machineName
				+ ", mouldOid=" + mouldOid + ", mouldName=" + mouldName
				+ ", prodNum=" + prodNum + ", stubBar=" + stubBar + ", weight="
				+ weight + ", period=" + period + ", caveNum=" + caveNum
				+ ", dailyTargetAmt=" + dailyTargetAmt + ", pcDate=" + pcDate
				+ ", prodAmt=" + prodAmt + ", unitConsume=" + unitConsume
				+ ", plasticType=" + plasticType + ", plasticWeight="
				+ plasticWeight + ", targetStartDate=" + targetStartDate
				+ ", targetEndDate=" + targetEndDate + ", planTime=" + planTime
				+ ", bomDetailOid=" + bomDetailOid + ", status=" + status
				+ ", createBy=" + createBy + ", createDate=" + createDate
				+ ", lastModifyBy=" + lastModifyBy + ", lastModifyDate="
				+ lastModifyDate + ", detailDtos=" + detailDtos
				+ ", materialName=" + materialName + ", materialNum="
				+ materialNum + ", loss=" + loss + ", itemOid=" + itemOid
				+ ", itemCode=" + itemCode + ", itemColor=" + itemColor
				+ ", bomNum=" + bomNum + ", prodId=" + prodId + ", remark="
				+ remark + "]";
	}
	
}
