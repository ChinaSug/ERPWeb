<%@page import="com.abs.ps.web.dto.DepartmentDto"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.abs.core.paging.IPaging"%>
<%@ page import="com.abs.ps.web.dto.UserDto"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	IPaging myPage = (IPaging) request.getAttribute("QUEUE_PAGING");
	List<UserDto> list = myPage.getThisPageElements();
	pageContext.setAttribute("userList", list);

	boolean isAdmin = (Boolean) request.getAttribute("IS_ADMIN");
	String isAllowAdd = (String) request.getAttribute("IS_ALLOW_ADD");
	
	List<DepartmentDto> scopeList = (List) request.getAttribute("SCOPE_LIST");
	Map<Long, String> scopeMap = new HashMap<Long, String>();
	if (scopeList != null) {
		for (DepartmentDto dto : scopeList) {
			scopeMap.put(dto.getId(), dto.getDepartName());
		}
	}
	pageContext.setAttribute("scopeMap", scopeMap);
	
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
<link href="..<%=request.getContextPath()%>/assets/css/page-min.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="container">

		<div class="row">
			<div class="doc-content">
				<%-- <form class="form-panel" action="<%=request.getContextPath()%>/mgHandler.html?op_action=USER_LIST">
					<ul class="panel-content">
						<li>
							<input name="userName" class="control-text" type="text" placeholder="请输入用户姓名" />
							<button type="submit" class="button button-primary">
								<i class="icon-white icon-search"></i>查询
							</button>
						</li>
					</ul>
				</form> --%>
				<form name="delForm" id="delForm" method="post"
					action="<%=request.getContextPath()%>/mgHandler.html?op_action=USER_DELETE">
					<table cellspacing="0" class="table table-bordered">
						<thead>
							<tr>
								<th width="5%"><label class="checkbox"><input
										type="checkbox" onClick="selectAll(this);">全选</label>
								</th>
								<th width="20%">用户ID</th>
								<th width="20%">姓名</th>
								<th width="20%">用户类型</th>
								<th width="5%">状态</th>
								<th width="5%">权限</th>
								<th width="5%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${userList }" var="user">
								<tr>
									<td>
										<c:if test="${user.deletable}">
										   <input type="checkbox" value="${user.id }" id="delid" name="delid"/>
										</c:if>
									</td>
									<td>${user.userId }</td>
									<td>${user.userName }</td>
									<td>
										<c:choose>
										    <c:when test="${user.isAdmin }">
										    	管理员
										    </c:when>
										    <c:otherwise>
										    	普通用户
										    </c:otherwise>
										</c:choose>
									</td>
									
									<td>
										<c:choose>
										    <c:when test="${user.status == 1 }">
										    	正常
										    </c:when>
										    <c:otherwise>
										    	失效
										    </c:otherwise>
										</c:choose>
									</td>
									<td><a href="${pageContext.request.contextPath}/mgHandler.html?op_action=USER_PRIV_PAGE&oid=${user.id}">查看</a></td>
									<td><a href="${pageContext.request.contextPath}/mgHandler.html?op_action=USER_MODIFY&oid=${user.id}">编辑</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
	        <div>
	          <ul class="toolbar pull-left">
			    <li><button class="button button-success"  onClick="redirectToAddPage();" ><i class="icon-white icon-plus"></i>添加</button></li>
	            <li><button class="button button-danger" onClick="deleteObj();"><i class="icon-white icon-trash"></i>删除</button></li>
	          </ul>
			  <jsp:include page="paging.jsp" flush="false" />
	        </div>
		</div>
	</div>
</div>
	
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/bui-min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/config-min.js"></script>
<script type="text/javascript">
function selectAll(selector){
	var obj = document.delForm.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "delid"){
			if (selector.checked == true){
				obj[i].checked = true;
			} else {
				obj[i].checked = false;
			}
		}
	}

}

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
	 window.location.href = "<%=request.getContextPath()%>/mgHandler.html?op_action=USER_ADD"; 
}
</script>
	
</body>
</html>
