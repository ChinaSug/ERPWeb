<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.core.paging.IPaging"%>
<%@ page import="com.abs.ps.domain.AppDebugLog"%>
<%@ page import="com.abs.ps.util.DateHelper"%>
<%@ page import="java.util.*"%>
<%
IPaging myPage = (IPaging) request.getAttribute("QUEUE_PAGING");
List<AppDebugLog> list = myPage.getThisPageElements();


%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>APP调试日志</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.tabfont01 {	
	font-family: "宋体";
	font-size: 9px;
	color: #555555;
	text-decoration: none;
	text-align: center;
}
.font051 {font-family: "宋体";
	font-size: 12px;
	color: #333333;
	text-decoration: none;
	line-height: 20px;
}
.font201 {font-family: "宋体";
	font-size: 12px;
	color: #FF0000;
	text-decoration: none;
}
.button {
	font-family: "宋体";
	font-size: 14px;
	height: 37px;
}
html { overflow-x: auto; overflow-y: auto; border:0;} 
-->
</style>

<link href="..<%=request.getContextPath()%>/css/css.css" rel="stylesheet" type="text/css" />
<link href="..<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
  <tr>
    <td>
    <form name="delForm" id="delForm" method="post" action="">
    <table id="subtree1" style="DISPLAY: " width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">

          <tr><td>&nbsp;</td></tr>
              <tr>
                <td height="40" class="font42"><table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#464646" class="newfont03">
		
                  <tr>

                   <td width="10%" align="center" bgcolor="#EEEEEE">请求用户</td>
                    <td width="10%" align="center" bgcolor="#EEEEEE">请求功能</td>
                    <td width="10%" align="center" bgcolor="#EEEEEE">请求时间</td>
                    <td width="15%" align="center" bgcolor="#EEEEEE">输入参数</td>
                    <td width="55%" align="center" bgcolor="#EEEEEE">输出参数</td>

                  </tr>
<% 
	for (AppDebugLog dto : list) {
%>     
                  <tr> 
					<td height="20" bgcolor="#FFFFFF"><%=dto.getUserId() %></td>
                    <td height="20" bgcolor="#FFFFFF"><%=dto.getFunction()%></td>
					<td height="20" bgcolor="#FFFFFF"><%=DateHelper.convert2String(dto.getCreateDate(),DateHelper.DATETIME_FORMATE) %></td>
					<td height="20" bgcolor="#FFFFFF"><%=dto.getInput() %></td>
					<td height="20" bgcolor="#FFFFFF"><%=dto.getOutput() %></td>

                  </tr>
<% 
	}
%>  

                </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      </form>
    <jsp:include page="paging.jsp" flush="false" />
				
				</td>
              </tr>
          </table>
		  
		  </td>
        </tr>
      </table></td>
  </tr>
</table>

</body>
</html>