<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String errMsg = (String) request.getAttribute("QUEUE_MESSAGE");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>来访管理系统</title>
</head>
<SCRIPT language="JavaScript">

function newWindow() {
	<% if ("LOGIN_SESSION_TIMEOUT".equals(errMsg)) { %>
	alert("超时，请重新登陆！");
	top.location.href = "<%=request.getContextPath()%>/login.html";
	<% } else if ("INVALID_WORDING".equals(errMsg)){ %>
	alert("发送请求中的参数中含有非法字符！");
	window.history.go(-1);
	<%}%>
}

</SCRIPT>

<body onload="newWindow();">

</body>
</html>