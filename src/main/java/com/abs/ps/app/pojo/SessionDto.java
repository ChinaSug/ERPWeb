package com.abs.ps.app.pojo;

public class SessionDto {
	private String userId;
	private String userName;
	private String menuObj;
	private String token;
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
	public String getMenuObj() {
		return menuObj;
	}
	public void setMenuObj(String menuObj) {
		this.menuObj = menuObj;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
