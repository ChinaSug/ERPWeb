package com.abs.ps.domain;

import java.io.Serializable;
import java.util.Date;

public class AppDebugLog implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3122925613479977449L;
	
	private Long oid;
	private String userId;
	private String function;
	private String input;
	private String output;
	private Date createDate;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	
	
}
