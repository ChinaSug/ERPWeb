package com.abs.ps.app.pojo;

import java.util.List;

public class GlobalMessage {
	private String id;
	private String name;
	private String token;
	private String msgCode;
	private String opType;
	private List<String> objStr;
	private String url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	
	public List<String> getObjStr() {
		return objStr;
	}
	public void setObjStr(List<String> objStr) {
		this.objStr = objStr;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
