package com.abs.ps.web.dto.json;

import java.util.List;

public class JMenuContentDto {
	private String text;
	private boolean collapsed;
	private List<JMenuItemDto> items;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<JMenuItemDto> getItems() {
		return items;
	}
	public void setItems(List<JMenuItemDto> items) {
		this.items = items;
	}
	public boolean isCollapsed() {
		return collapsed;
	}
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	
	
	
}
