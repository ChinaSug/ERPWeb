package com.abs.ps.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class PageDispatcher {
	private PageDispatcher() {}
	
	public static String HTML_VERSION = "_v=2017811114754";
	
	public static void forward(HttpServletRequest request, HttpServletResponse response, String pageName) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/" + pageName);
		dispatcher.forward(request, response);
	}
}
