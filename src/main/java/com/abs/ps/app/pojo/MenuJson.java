package com.abs.ps.app.pojo;

import java.util.List;

public class MenuJson {
	private String menuCode;
	private String menuName;
	private String url;
	private boolean isMainMenu;
	private List<MenuJson> subMenus;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isMainMenu() {
		return isMainMenu;
	}
	public void setMainMenu(boolean isMainMenu) {
		this.isMainMenu = isMainMenu;
	}
	public List<MenuJson> getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(List<MenuJson> subMenus) {
		this.subMenus = subMenus;
	} 
	
	
}
