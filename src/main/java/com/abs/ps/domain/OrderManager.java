package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6580915796687361577L;
	private Long oid;
	private Date acceptDate;
	private Long customerOid;
	private String orderNum;
	private String projectNum; 
	private String productNum;
	private String specificationName;
	private String orderCount;
	private String completeCount;
	private String unCompleteCount;
	private Date delivery;
	private Date startDate;
	private Date finishDate;
	private String status;
	private String remark;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Date getAcceptDate() {
		return acceptDate;
	}
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	public Long getCustomerOid() {
		return customerOid;
	}
	public void setCustomerOid(Long customerOid) {
		this.customerOid = customerOid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getProjectNum() {
		return projectNum;
	}
	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getSpecificationName() {
		return specificationName;
	}
	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}
	public String getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}
	public String getCompleteCount() {
		return completeCount;
	}
	public void setCompleteCount(String completeCount) {
		this.completeCount = completeCount;
	}
	public String getUnCompleteCount() {
		return unCompleteCount;
	}
	public void setUnCompleteCount(String unCompleteCount) {
		this.unCompleteCount = unCompleteCount;
	}
	public Date getDelivery() {
		return delivery;
	}
	public void setDelivery(Date delivery) {
		this.delivery = delivery;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
