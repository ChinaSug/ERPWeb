package com.abs.ps.web.dto;

public class EmployeeDto {
	// for criteria
	private String centerCode;
	private String serviceCode;
	private String windowCode;
	private String userId;
	
	// for data
	private String userName;
	private String windowName;
	private String serviceName;
	private String centerName;
	private String employeeStatus;
	private String windowStatus;
	private String currentNum;
	private int numOfQueue;
	public String getCenterCode() {
		return centerCode;
	}
	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getWindowCode() {
		return windowCode;
	}
	public void setWindowCode(String windowCode) {
		this.windowCode = windowCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWindowName() {
		return windowName;
	}
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getCenterName() {
		return centerName;
	}
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	public String getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	public String getWindowStatus() {
		return windowStatus;
	}
	public void setWindowStatus(String windowStatus) {
		this.windowStatus = windowStatus;
	}
	public String getCurrentNum() {
		return currentNum;
	}
	public void setCurrentNum(String currentNum) {
		this.currentNum = currentNum;
	}
	public int getNumOfQueue() {
		return numOfQueue;
	}
	public void setNumOfQueue(int numOfQueue) {
		this.numOfQueue = numOfQueue;
	}

}
