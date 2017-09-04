package com.abs.ps.domain;

import java.util.Date;

/**
 * @author 苏建生 2017-4-21
 */
public class Contract implements java.io.Serializable {

	private static final long serialVersionUID = -8574159106173895930L;
	
	private Long oid;
	private Long houseOid;
	private String tenantName;
	private String tenantPhone;
	private String idNum;
	private String sex;
	private String contCode;
	private String status;
	private Double rent;
	private Double deposit;
	private String tollPeriod;
	private Date startDate;
	private Date endDate;
	private String remark;
	private String createBy;
	private Date createDate;
	private String lastModifyBy;
	private Date lastModifyDate;

	/** default constructor */
	public Contract() {
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getHouseOid() {
		return houseOid;
	}

	public void setHouseOid(Long houseOid) {
		this.houseOid = houseOid;
	}

	public String getTenantName() {
		return this.tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantPhone() {
		return this.tenantPhone;
	}

	public void setTenantPhone(String tenantPhone) {
		this.tenantPhone = tenantPhone;
	}

	public String getIdNum() {
		return this.idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getContCode() {
		return this.contCode;
	}

	public void setContCode(String contCode) {
		this.contCode = contCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getRent() {
		return this.rent;
	}

	public void setRent(Double rent) {
		this.rent = rent;
	}

	public Double getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public String getTollPeriod() {
		return this.tollPeriod;
	}

	public void setTollPeriod(String tollPeriod) {
		this.tollPeriod = tollPeriod;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastModifyBy() {
		return this.lastModifyBy;
	}

	public void setLastModifyBy(String lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	

}