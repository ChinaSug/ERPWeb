<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.ps.web.dto.UserDto"%>
<%@ page import="com.abs.ps.web.dto.MenuItemDto"%>
<%@ page import="java.util.List" %>
<%@ page import="com.abs.ps.util.QueueConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	List<MenuItemDto> menuDtoList = (List<MenuItemDto>)request.getAttribute(QueueConstants.QUEUE_USER_PRIV_LIST);
	List<Long> privOids = (List)request.getAttribute(QueueConstants.QUEUE_USER_PRIV);
	String userOid = (String)request.getAttribute("USER_OID");
%>
<!DOCTYPE HTML>
<html>
 <head>
  <title></title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="..<%=request.getContextPath()%>/assets/css/dpl-min.css"	rel="stylesheet" type="text/css" />
<link href="..<%=request.getContextPath()%>/assets/css/bui-min.css"	rel="stylesheet" type="text/css" />
<link href="..<%=request.getContextPath()%>/assets/css/page-min.css" rel="stylesheet" type="text/css" />

<style type="text/css">

@keyframes mymove {
	0%{ opacity: 1; }
	50% { opacity: 1; }
	100%{ opacity: 0; }
}


</style>

 </head>
<body>
<c:if test="${requestScope.MSG != null }">
	<div id="tipDiv" class="tips tips-small tips-success " style="position: fixed;left: 50%;top: 20px;opacity:0;margin-left: -100px;animation: mymove 4s;">
		<span class="x-icon x-icon-small x-icon-success"><i class="icon icon-white icon-ok"></i></span>
		<div class="tips-content">${requestScope.MSG}</div>
	</div>      
</c:if>
<div class="container">
	<div class="row">
		<div class="span24 doc-content">
		<form name="delForm" id="delForm" method="post" action="<%=request.getContextPath()%>/mgHandler.html?op_action=USER_PRIV_SAVE">
			<h3>用户权限设置</h3>
			<input type="hidden" name="userOid" id="userOid" value=<%=userOid %>>
			<table class="table table-bordered" cellspacing="0">
				<thead>
					<tr>
						<th width="5%">
							<label class="checkbox"><input type="checkbox" onClick="selectAll(this);">全选</label>
						</th>
						<th width="10%">权限名称</td>
				        <th width="10%">使用类型</td>
				    </tr>
			    </thead>
			    <input type="hidden" value="0" id="privid" />
			    <tbody>
					<% for(MenuItemDto menuDto : menuDtoList){ 
						if (menuDto.getUrl() == null) continue; 
					%>
						<tr>
							<td>
								<input value="<%=menuDto.getId() %>" id="privid" name="privid"  <% if(privOids.contains(menuDto.getId())){out.print("checked");} %> type="checkbox">
							</td>
							<td>
								<%=menuDto.getMenuName() %>
							</td>
							<td>
								<% if(menuDto.isAdmin()){out.print("管理模块");}else{out.print("一般用户");} %>
							</td>
						</tr>
					<% } %>
				</tbody>
			</table>
			
			<div class="actions-bar">
			  <div class="row ">
				<div class="span13 offset3 ">
				  <button type="submit" class="button button-primary">保存</button>
				  <button type="submit" class="button" onclick="javaScript:history.back()">返回</button>
				  
				</div>
			  </div>
			</div>
		</form>
		</div>
	</div>
</div>

<script type="text/javascript">
function selectAll(selector){
	var obj = document.delForm.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "privid"){
			if (selector.checked == true){
				obj[i].checked = true;
			} else {
				obj[i].checked = false;
			}
		}
	}

}

function unselectAll(){
	var obj = document.delForm.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "privid"){
			if (obj[i].checked==true) obj[i].checked = false;
			else obj[i].checked = true;
		}
	}
}


function savePriv() {
	if (confirm("确定要保存吗？")) {
		var obj = document.delForm.elements;
		var isChecked = false;
		for (var i=0;i<obj.length;i++){
			if (obj[i].name == "privid" && obj[i].checked==true){
				isChecked = true;
			}
		}
		if (isChecked) {
			document.getElementById("delForm").submit();
		} else {
			alert("请先选择记录！");
		}
	}
}

</script>

<body>
</html>  
