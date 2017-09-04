package com.abs.ps.web.helper;

import java.io.IOException;

import javax.servlet.ServletException;

public interface IControllerHelper {
	public void doDelete() throws ServletException, IOException;
	public void doSave() throws ServletException, IOException;
	public void doModify() throws ServletException, IOException;
	public void doAdd() throws ServletException, IOException;
	public void doQuery() throws ServletException, IOException;
}
