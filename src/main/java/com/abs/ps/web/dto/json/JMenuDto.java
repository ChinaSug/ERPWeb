package com.abs.ps.web.dto.json;

import java.util.List;

public class JMenuDto {
	private String id;
	private List<JMenuContentDto> menu;
	private String homePage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<JMenuContentDto> getMenu() {
		return menu;
	}
	public void setMenu(List<JMenuContentDto> menu) {
		this.menu = menu;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	
	
}
