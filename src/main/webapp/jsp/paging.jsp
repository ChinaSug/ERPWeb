<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.core.paging.IPaging"%>
<%
IPaging myPage = (IPaging) request.getAttribute("QUEUE_PAGING");
int lastPageNumber = 0;//总页数
int nextPageNumber = 0;//下页
int pageSize = 0;//每页记录数
int previoutPageNumber = 0;//上页
int firstElement = 0;//当前第一条
int lastElement = 0;//当前最后一条
int pageNum = 0;//当前第几页
int totalElement = 0;//总记录数
String pageURL = "";

if (myPage != null){
	 lastPageNumber = myPage.getLastPageNumber();//总页数
	 nextPageNumber = myPage.getNextPageNumber();//下页
	 pageSize = myPage.getPageSize();//每页记录数
	 previoutPageNumber = myPage.getPreviousPageNumber();//上页
	
	 firstElement = myPage.getThisPageFirstElementNumber();//当前第一条
	 lastElement = myPage.getThisPageLastElementNumber();//当前最后一条
	 pageNum = myPage.getThisPageNumber();//当前第几页
	 totalElement = myPage.getTotalNumberOfElements();//总记录数
	 pageURL = myPage.getURL();
}
%>
<div class="pagination pull-right">
<table width="100%" border="0" align="center">
	<tr align="center" width="60%">
		<td><span class="font">
				共<%=totalElement%>条 | 第<%=pageNum%>页
				转第 <select class="span2 span-width"
					name="jump" onchange="jumpPage(this)">
					<%for (int i = 1; i <= lastPageNumber; i++) {
        if (i == pageNum) {%>
					<option selected value="<%=i%>"><%=i%></option>
					<%}
          else{%>
					<option value="<%=i%>"><%=i%></option>
					<%}
        }%>
				</select> 页

		</span></td>
		<td >
			<span class="font">
				<a href="javascript:gotoPage(1)">首页</a><a
					href="javascript:gotoPage(<%=previoutPageNumber%>)">上一页</a><a
					href="javascript:gotoPage(<%=nextPageNumber%>)">下一页</a><a
					href="javascript:gotoPage(<%=lastPageNumber%>)">末页</a> 
			</span>
		</td>
	</tr>
</table> 
</div>
<SCRIPT LANGUAGE="JavaScript" type="">
function jumpPage(selObj){
  window.location.href="<%=pageURL%>&pageNumber="+selObj.options[selObj.selectedIndex].value;
}
function gotoPage(pageNumber){
  window.location.href="<%=pageURL%>&pageNumber="+pageNumber;
}
</SCRIPT>

