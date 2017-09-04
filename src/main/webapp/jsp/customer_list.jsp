<%@page import="com.abs.ps.util.QueueConstants"%>
<%@page import="com.abs.ps.util.Constants"%>
<%@page import="com.abs.ps.util.StringHelper"%>
<%@page import="com.abs.ps.util.JsonUtils"%>
<%@page import="java.util.Map"%>
<%@page import="com.abs.ps.web.dto.CustomerDto"%>
<%@page import="java.util.List"%>
<%@page import="com.abs.core.paging.IPaging"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	IPaging myPage = (IPaging) request.getAttribute("QUEUE_PAGING");
	List<CustomerDto> custList = null;
	if (myPage != null) {
		custList = myPage.getThisPageElements();
	}
	pageContext.setAttribute("custList", custList);
	
	Map<String, String> valueMap = (Map) request.getAttribute("SEARCH_MAP");
	String valueMapJson = "";
	if (valueMap != null) {
		valueMapJson = JsonUtils.toJSONString(valueMap);
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="..<%=request.getContextPath()%>/assets/css/dpl-min.css"
	rel="stylesheet" type="text/css" />
<link href="..<%=request.getContextPath()%>/assets/css/bui-min.css"
	rel="stylesheet" type="text/css" />
<link href="..<%=request.getContextPath()%>/assets/css/page.css?v=20176911147"
	rel="stylesheet" type="text/css" />

<style type="text/css">
.control-text{
	width: 300px !importment; 
}
	
</style>
	
</head>
<body>
<div class="container">
	<div class="row">
		<div class="doc-content">
			<form name="delForm" id="delForm" method="post"
				action="<%=request.getContextPath()%>/mgHandler.html?op_action=CUSTOMER_DELETE">
				<ul class="panel-content form-info-ul search-bar">
					<li>
						<label class="control-label">客户名称：</label>
						<input class="control-text" type="text" name="<%=Constants.SEARCH_CUSTOMER_NAME %>" onkeydown='if(event.keyCode==13)return false' />
					</li>
					<li>
						<button type="button" class="button button-primary" id="searchBtn">
							<i class="icon-white icon-search"></i>查询
						</button>
					</li>
				</ul>
				<table cellspacing="0" class="table table-bordered">
					<thead>
						<tr>
							<th width="50px"><label class="checkbox"><input type="checkbox" onClick="selectAll(this);">全选</label></th>
							<th>客户名称</th>
							<th>联系人</th>
							<th>客户联系电话</th>
							<th>客户邮箱</th>
							<!-- <th>所属单位</th> -->
							<th>创建人</th>
							<th>创建时间</th>
							<th width="30px">状态</th>
							<th width="30px">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${custList}" var="cust">
							<tr>
								<td>
									<%-- <c:if test="${cust.deletable }"> --%>
										<input type="checkbox" value="${cust.oid }" name="delid"/>
								<%-- 	</c:if> --%>
								</td>
								<td>${cust.name}</td>
								<td>${cust.contactPerson}</td>
								<td>${cust.mobileNum}</td>
								<td>${cust.email}</td>
								<%-- <td>${cust.departOid }</td> --%>
								<td>${cust.createBy}</td>
								<td>${cust.createDate}</td>
								<td>
									<c:choose>
										<c:when test="${cust.status == 1 }">
											正常
										</c:when>
										<c:when test="${cust.status == 0 }">
											失效
										</c:when>
									</c:choose>
								</td>
								<td><a href="<%=request.getContextPath()%>/mgHandler.html?op_action=CUSTOMER_MODIFY&oid=${cust.oid}">编辑</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
			<div>
				<ul class="toolbar pull-left">
					<li>
						<button class="button button-success" onClick="redirectToAddPage();">
							<i class="icon-white icon-plus"></i>添加
						</button>
					</li>
					<li>
						<button class="button button-danger" onClick="deleteObj();">
							<i class="icon-white icon-trash"></i>删除
						</button>
					</li>
				</ul>
				<jsp:include page="paging.jsp" flush="false" />
			</div>
		</div>
	</div>
</div>

<div class="over"></div>
<div class="layout">
	<img src="<%=request.getContextPath()%>/assets/img/load-16-16.gif" />
</div>

<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/bui-min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/sea.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/config-min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/abs-paging.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/common.js"></script>
<script type="text/javascript">

$("#searchBtn").click(function() {
	document.getElementById("delForm").action = '<%=request.getContextPath()%>/mgHandler.html?op_action=CUSTOMER_LIST';
	$(".over").show();
  	$(".layout").show();
	document.getElementById("delForm").submit();
});
function deleteObj() {
	var obj = document.delForm.elements;
	var isChecked = false;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "delid" && obj[i].checked==true){
			isChecked = true;
		}
	}

	if (!isChecked) {
		BUI.Message.Alert('请先选择记录！');
	} else {
		BUI.Message.Confirm('确定要删除记录吗？',function(){
			document.getElementById("delForm").submit();
		},'question');
	}

	
}
function redirectToAddPage() {
	 window.location.href = '<%=request.getContextPath()%>/mgHandler.html?op_action=CUSTOMER_ADD';
}


 <% if (!StringHelper.isEmpty(valueMapJson)) { %>
	var formContent = <%=valueMapJson %>;
	BUI.FormHelper.setFields($("#delForm")[0], formContent[0]);
<% } %> 

</script>

</body>
</html>