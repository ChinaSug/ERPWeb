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
		    
		    .redWord {
		    	color: red;
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
		
		<div id="divItem" class="hide">
			<div class="x-collapsed x-accordion-body">
				<table class="table table-bordered">
					<tr>
						<th>物品编号</th>
						<th>物品名称</th>
						<th>类型</th>
						<th>型号</th>
						<th>颜色</th>
						<th>规格</th>
						<th>单位</th>
						<th>供应商</th>
						<th>仓库</th>
						<th>安全库存量</th>
						<th>当前库存量</th>
					</tr>
					<c:forEach items="${requestScope.LOW_STOCK }" var="d">
						<tr>
							<td>${d.itemCode }</td>
							<td>${d.itemName }</td>
							<td>${d.typeName }</td>
							<td>${d.model }</td>
							<td>${d.color }</td>
							<td>${d.spec }</td>
							<td>${d.unit }</td>
							<td>${d.supplierName }</td>
							<td>${d.warehouseName }</td>
							<td>${d.safeAmt }</td>
							<td class="redWord">${d.stockAmt }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div id="divOrder" class="hide">
			<div class="x-collapsed x-accordion-body">
				<table class="table table-bordered">
					<tr>
						<th>接单日期</th>
						<th>客户</th>
						<th>订单号</th>
						<th>部番</th>
						<th>规格名称</th>
						<th>订单量</th>
						<th>完成量</th>
						<th>未完成量</th>
						<th>纳期</th>
						<th>开工日期</th>
						<th>完工日期</th>
						<th>状态</th>
					</tr>
					<c:forEach items="${requestScope.LOW_STOCK1 }" var="d">
						<tr>
							<td>${d.itemCode }</td>
							<td>${d.itemName }</td>
							<td>${d.typeName }</td>
							<td>${d.model }</td>
							<td>${d.color }</td>
							<td>${d.spec }</td>
							<td>${d.unit }</td>
							<td>${d.supplierName }</td>
							<td>${d.warehouseName }</td>
							<td>${d.safeAmt }</td>
							<td class="redWord">${d.stockAmt }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<script src="assets/js/jquery-1.8.1.min.js"></script>
		<script src="assets/js/bui-min.js"></script>
		<script src="assets/js/sea.js"></script>
		<script src="assets/js/config-min.js"></script>
		<script type="text/javascript">
			BUI.use(['bui/layout', 'bui/grid', 'bui/data'], function(Layout, Grid, Data) {
			
				var divItem = $("#divItem").html();
				var divOrder = $("#divOrder").html();
				
				var downArrows = "<div style='float:right'><span class='x-caret x-caret-down'></span></div>";
				var control = new BUI.Component.Controller({
					width : '100%',
					height: 500,
					render: '#J_Layout',
					defaultChildClass: 'controller',
					children: [
						{
							layout: {
								title: '少于安全库存量的物料'+downArrows,
							},
							content : divItem,
						},{
							layout: {
								title: '异常状态订单'+downArrows,
							},
							content : divOrder,
						},{
							layout: {
							}
						}
					],
					plugins: [Layout.Accordion]
				});
				
				control.render();
			});
		</script>
	</body>

</html>
