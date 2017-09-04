package com.abs.ps.web.dto.json;

import java.util.List;

public class JPage {
	private List<DepartInfo> rows;
	private String results;
	
	
	public List<DepartInfo> getRows() {
		return rows;
	}
	public void setRows(List<DepartInfo> rows) {
		this.rows = rows;
	}
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	
	
}
