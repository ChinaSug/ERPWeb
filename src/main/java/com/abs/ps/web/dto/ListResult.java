package com.abs.ps.web.dto;

import java.util.List;

public class ListResult<T> {
	private List<T> rows;
	private String results;
	private String hasError;
	private String error;
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	public String getHasError() {
		return hasError;
	}
	public void setHasError(String hasError) {
		this.hasError = hasError;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	public static void main(String[] argu) {
		
	}
}
