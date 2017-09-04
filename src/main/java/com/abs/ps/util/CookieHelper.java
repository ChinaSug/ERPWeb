package com.abs.ps.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CookieHelper {
	public final static String ABS_USERID_COO = "ABS_USERID_COO";
	public final static String ABS_PWD_COO = "ABS_PWD_COO";
	public final static String ABS_REMEMBER = "ABS_REMEMBER";
	public final static int MAX_AGE = 60*60*24*30;
	private CookieHelper(){}
	
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	public static String getCookieValue(HttpServletRequest request, HttpServletResponse response, String coName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (coName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
