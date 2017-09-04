<%@ page import="com.abs.ps.web.dto.UserDto"%>
<%@ page import="com.abs.ps.web.dto.EnterpriceInfoDto"%>
<%@ page import="com.abs.ps.web.dto.ParkInfoDto"%>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="com.abs.ps.util.StringHelper"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	Boolean isAdmin = (Boolean) request.getAttribute("IS_ADMIN");
	if(isAdmin == null){
		isAdmin = false;
	}
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>工作台</title>
		<link type="text/css" href="assets/css/dpl-min.css" rel="stylesheet"/>
		<link type="text/css" href="assets/css/bui-min.css" rel="stylesheet"/>
		
		<style type="text/css">
		   .x-accordion-title{
		      	background: #dfeaf2;
		      	border-top: 2px solid white;
		      	padding: 8px 10px;
		    }
		    
		    .x-accordion-body{
		    	overflow-x: auto;
		    }
		    
		    .x-collapsed .x-accordion-body{
		    	height: 0;
		      	overflow: hidden;
		    }
		    
		</style>

	</head>

	<body>
	
		<div class="detail-section" style="position: absolute;top:10px;left: 10px;right: 10px;">
			<div class="">
				<div id="J_Layout" class="">
				</div>
			</div>
		</div>
		
	<!--	<div id="div_contList" style="display:none;">
			<div class="x-collapsed x-accordion-body">
				<table cellspacing="0" style="width: 100%;" class="table table-bordered">
					<tr>
						<th>合约编号</th>
						<th>租户姓名</th>
						<th>租户电话</th>
						<th>租期开始时间</th>
						<th>租期结束时间</th>
					</tr>
					<c:forEach items="${requestScope.DUE_CONT_LIST }" var="cont">
						<tr>
							<td>${cont.contCode }</td>
							<td>${cont.tenantName }</td>
							<td>${cont.tenantPhone }</td>
							<td>${cont.startDate }</td>
							<td>${cont.endDate }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>-->
		
		<script src="assets/js/jquery-1.8.1.min.js"></script>
		<script src="assets/js/bui-min.js"></script>
		<script src="assets/js/sea.js"></script>
		<script src="assets/js/config-min.js"></script>
		<script type="text/javascript">
	
		</script>
	</body>

</html>
