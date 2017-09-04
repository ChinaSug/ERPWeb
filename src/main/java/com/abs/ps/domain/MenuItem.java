package com.abs.ps.domain;

import java.io.Serializable;

public class MenuItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2717053328678218257L;
	private Long oid;
	private String menuCode;
	private String menuName;
	private Boolean isAdmin;
	private String className;
	private String url;
	private Long mainMenuOid;
	private Boolean isShow;
	private Boolean isSingle;
	private Integer seq;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getMainMenuOid() {
		return mainMenuOid;
	}
	public void setMainMenuOid(Long mainMenuOid) {
		this.mainMenuOid = mainMenuOid;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	public Boolean getIsSingle() {
		return isSingle;
	}
	public void setIsSingle(Boolean isSingle) {
		this.isSingle = isSingle;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	
}
