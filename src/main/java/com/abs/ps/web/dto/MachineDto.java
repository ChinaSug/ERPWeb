package com.abs.ps.web.dto;

public class MachineDto {
	private String oid;
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
	
	private String disabled;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
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

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
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
		MachineDto other = (MachineDto) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MachineDto [oid=" + oid + ", name=" + name + ", brand=" + brand
				+ ", type=" + type + ", tieBar=" + tieBar + ", fpSize="
				+ fpSize + ", openQuan=" + openQuan + ", thick=" + thick
				+ ", screwDia=" + screwDia + ", maxInjPrs=" + maxInjPrs
				+ ", injWgt=" + injWgt + ", remark=" + remark + ", disabled="
				+ disabled + "]";
	}
	
}
