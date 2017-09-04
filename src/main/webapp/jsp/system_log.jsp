<%@page import="com.abs.ps.util.StringHelper"%>
<%@page import="com.abs.ps.web.dto.ActionLogDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="com.abs.core.paging.IPaging"%>

<%
	IPaging myPage = (IPaging) request.getAttribute("QUEUE_PAGING");
	String logDate = (String) request.getAttribute("log_date");
	String userId = (String) request.getAttribute("user_id");
	String action_Type = (String)request.getAttribute("actionType");
	
	List<ActionLogDto> list = null;
	if(myPage != null){
		list = myPage.getThisPageElements();
	}
	
%>
<!DOCTYPE HTML>
<html>
<head>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="..<%=request.getContextPath()%>/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/page-min.css" rel="stylesheet" type="text/css" />  
   
<style type="text/css">

    .park-info-ul{
	    padding: 7px 12px;
		background-color: rgb(238, 238, 238);
		margin-bottom: 18px;
		border: 1px solid rgb(221, 221, 221);
		border-radius: 3px;
	}
	
	/** 表格中文字不超出边框   **/
	.table-fix{
		table-layout:fixed;
	}
	
	.table-fix td{
		word-wrap:break-word;
	}

</style>
   
</head>
<body>

<div class="container">
	<div class="row">
		<div class="span32 doc-content">
			<form id="" method="post" action="<%=request.getContextPath()%>/mgHandler.html?op_action=SYSTEM_LOG&search_type=1">
				<ul class="panel-content park-info-ul">
					<li>
						<label class="control-label">日期：</label>
						<input type="text" name="log_date" value="<%=logDate %>" class="calendar" >
						<label class="control-label">用户ID：</label>
						<input type="text" name="user_id" style="width: 150px;" value="<%=StringHelper.filterNullStr(userId) %>" class="input-normal control-text">
						<%-- <label class="control-label">操作类型ID：</label>
						<input type="text" name="actionType" style="width: 150px;" value="<%=StringHelper.filterNullStr(action_Type) %>" class="input-normal control-text"> --%>
						<select id = "selector" name="actionType"  class="span2 span-width" data-rules="{required:true}" style= "height:25px;width:140px">
							<option value="LOGIN" >登陆</option>
							<option value="LOGOUT">退出</option>
							<option value="ADD">添加</option>
							<option value="MODIFY">修改</option>
							<option value="DELETE">删除</option>
						</select>
						<button type="submit" class="button button-primary">
							<i class="icon-white icon-search"></i>查询
						</button>
					</li>
				</ul>
				<table cellspacing="0" class="table table-bordered table-fix">
					<thead>
						<tr>
							<th width="10%">用户ID</th>
							<th width="10%">用户姓名</th>
							<th width="10%">IP地址</th>
							<th width="10%">操作类型</th>
							<th width="10%">模块</th>
							<th width="10%">原值</th>
							<th width="10%">改为</th>
							<th width="10%">操作时间</th>
						</tr>
					</thead>
					<tbody>
					
					<% if(list != null){
							for(ActionLogDto dto : list){ %>
						<tr>
							<td><%=dto.getUserId() %></td>
							<td><%=StringHelper.filterNullStr(dto.getUserName()) %></td>
							<td><%=dto.getIpAddr() %></td>
							<td><%
								String actionType = StringHelper.filterNullStr(dto.getActionType());
								if(actionType != null){
									if("LOGIN".equals(actionType)){
										out.print("登陆");
									}else if("LOGOUT".equals(actionType)){
										out.print("退出");
									}else if("ADD".equals(actionType)){
										out.print("添加");
									}else if("DELETE".equals(actionType)){
										out.print("删除");
									}else if("MODIFY".equals(actionType)){
										out.print("修改");
									}
								}
							%>
							</td>
							<td><%
								String field = StringHelper.filterNullStr(dto.getFieldName());
								if("AM_EMPLOYEE".equals(field)){
									out.print("员工");
								}else if("COMMENT_INFO".equals(field)){
									out.print("资讯评论");
								}else if("AM_DEPARTMENT".equals(field)){
									out.print("部门信息");
								}else if("ENT_DATA".equals(field)){
									out.print("企业数据");
								}else if("AM_ENT_INFO".equals(field)){
									out.print("企业信息");
								}else if("ORG_SAVE".equals(field) || "ORG_ADD".equals(field) || "ORG_DELETE".equals(field)){
									out.print("园区");
								}else if("PRAK_INFO".equals(field)){
									out.print("资讯信息");
								}else if("PI_REG".equals(field)){
									out.print("资讯报名");
								}else if("QUEUE_USER".equals(field)){
									out.print("用户");
								}else if("AM_CONTRACT".equals(field)){
									out.print("合约信息");
								}else if("AM_HOUSE".equals(field)){
									out.print("房屋信息");
								}else if("AM_CUSTOMER".equals(field)){
									out.print("客户信息");
								}else if("AM_INSPEC".equals(field)){
									out.print("生产检查信息");
								}else if("AM_WHS".equals(field)){
									out.print("仓库信息");
								}else if("AM_MOULD".equals(field)){
									out.print("模具信息");
								}else if("AM_BOMINFO".equals(field)){
									out.print("BOM管理信息");
								}else if("AM_BOM_DETAIL".equals(field)){
									out.print("BOM管理详细信息");
								}else if("AM_MACHINE".equals(field)){
									out.print("机台信息");
								}else if("AM_ITEMTYPE".equals(field)){
									out.print("物品类型信息");
								}else if("AM_SUPPLIER".equals(field)){
									out.print("供应商信息");
								}else if("AM_ITEM".equals(field)){
									out.print("物品信息");
								}else if("AM_SCHD_MAIN".equals(field)){
									out.print("工程管理信息");
								}else if("AM_SCHD_DETAIL".equals(field)){
									out.print("工程管理详细信息");
								}else if("AM_SCP".equals(field)){
									out.print("库存盘点信息");
								}else if("AM_STOCKINFO".equals(field)){
									out.print("出入库管理信息");
								}else if("AM_STOCKTYPE".equals(field)){
									out.print("库存类型管理");
								}else if("AM_PC".equals(field)){
									out.print("生产控制信息");
								}else if("AM_SUB_PC".equals(field)){
									out.print("生产控制详细信息");
								}
								
								/* if("LOGIN".equals(field)){
									out.print("登陆");
								}else{
									out.print(field);
								} */
							%></td>
							<td><%=StringHelper.filterNullStr(dto.getFromValue()) %></td>
							<td><%=StringHelper.filterNullStr(dto.getToValue()) %></td>
							<td><%=dto.getCreateTime() %></td>
						</tr>
					<% }} %>
					</tbody>
				</table>
			</form>
			<div>
			  <jsp:include page="paging.jsp" flush="false" />
	        </div>
		</div>
	</div>
</div>


<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/bui-min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/config-min.js"></script>

<script type="text/javascript">
BUI.use('bui/calendar',function(Calendar){
    var datepicker = new Calendar.DatePicker({
      trigger:'.calendar',
      dateMask : 'yyyy-mm-dd',
      autoRender : true,
    });
});


</script>
</body>
</html>
  