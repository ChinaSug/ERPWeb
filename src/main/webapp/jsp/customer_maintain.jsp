<%@page import="com.abs.ps.util.QueueConstants"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.abs.ps.util.StringHelper"%>
<%@page import="com.abs.ps.util.JsonUtils"%>
<%@page import="com.abs.ps.web.dto.CustomerDto"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	CustomerDto custDto = (CustomerDto) request.getAttribute("CUSTOMER_DTO");
	String dtoJson = JsonUtils.toJSONString(custDto);
	
	
	pageContext.setAttribute("custDto", custDto);

%>
<!DOCTYPE HTML>
<html>
<head>
	<title>客户信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="..<%=request.getContextPath()%>/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
	<link href="..<%=request.getContextPath()%>/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
	<link href="..<%=request.getContextPath()%>/assets/css/close-img.css" rel="stylesheet" type="text/css" />    
	<link href="..<%=request.getContextPath()%>/assets/css/page.css?v=201705242" rel="stylesheet" type="text/css" />
<style type="text/css">
	.clear-div {
		clear: both;
		margin-left: 10px;
	}
	
	.controls .width-div {
		width: 300px;
	}
	
	input:disabled {
	    border: 1px solid #DDD;
	    background-color: #F5F5F5;
	    color:#ACA899;
	}
	
	#addForm .width80 {
		width: 80px;
		
	}
	
	.bdsug li {
	    width: 292px;
	    color: #000;
	    line-height: 22px;
	    padding: 0 8px;
	    position: relative;
	    cursor: default;
	}
	
	.bdsug li:hover {
		background-color: #d8ded4;
	}
	
	.bdsug {
		display: none;
	    position: absolute;
	    z-index: 1;
	    width: 308px;
	    background: #fff;
	    border: 1px solid #ccc;
	    _overflow: hidden;
	    box-shadow: 1px 1px 3px #ededed;
	    -webkit-box-shadow: 1px 1px 3px #ededed;
	    -moz-box-shadow: 1px 1px 3px #ededed;
	    -o-box-shadow: 1px 1px 3px #ededed;
	}
	
</style>

</head>
<body>
<div class="container">
	<div class="doc-content">
		<form class="form-horizontal well" id="addForm"  action="<%=request.getContextPath()%>/mgHandler.html?op_action=CUSTOMER_SAVE" method="post" >
			<div class="div-content content600" >
				<input name="oid" id="oid" type="hidden"/>
				<h3>客户信息</h3>
				
				<div class="div-row" style="padding-left:300px;">
					
					<div class="control-group span12;"style="padding-top:30px;">
						<label class="control-label"><s>*</s>客户名称：</label>
						<div class="controls">
							<input type="text" name="name" data-rules="{required:true,maxlength:20}" class="control-text width-div">
						</div>
					</div>
					
					<div class="control-group span12">
						<label class="control-label"><s>*</s>联系人：</label>
						<div class="controls">
							<input type="text" name="contactPerson" data-rules="{required:true,maxlength:20}" class="control-text width-div">
						</div>
					</div>
					
					<div class="control-group span12">
						<label class="control-label"><s>*</s>联系号码：</label>
						<div class="controls">
							<input type="text" name="mobileNum" data-rules="{required:true,maxlength:20}" class="control-text width-div">
						</div>
					</div>
					
					<div class="control-group span12">
						<label class="control-label">邮箱地址：</label>
						<div class="controls">
							<input type="text" name="email" data-rules="{maxlength:50}" class="control-text width-div">
						</div>
					</div>
					
					<div class="control-group span12" >
					<label class="control-label">状&nbsp;&nbsp;态：</label>
						<div class="controls">
							<select name="status"  class="span2 span-width" data-rules="{required:true}">
								<option value="1">正常</option>
								<option value="0">失效</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class="actions-bar" style="padding-left:580px;padding-top:250px;">
				<div class="row">
					<div class="op-div op-width400">
						<button type="submit" class="button button-primary">保存</button>
						<button type="button" class="button button-return" onclick="javascript:history.back()" >返回</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>


<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/bui-min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/config-min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/sea.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/common.js"></script>
<script type="text/javascript">

BUI.use('bui/form',function (Form) {
   	var form = new Form.HForm({
     	srcNode : '#addForm',
   	});
   	form.render();
   	
   	var fieldName = "name",
	classN = "Customer",
	nameField = form.getField(fieldName);
	
	nameField.set('remote', {
		url: "/ERPWeb/mgHandler.html?op_action=IS_EXIST",
		dataType: 'json', //默认为字符串
		callback: function(data) {
			if(data != null) {
				if(data.success) {
					return '';
				} else {
					return data.errors[fieldName];
				}
			}
		}
	});
	
	nameField.on('remotestart', function(ev) {
		var data = ev.data;
		var oidField = form.getField("oid");
		nameField = form.getField(fieldName);
		
		data.oid = oidField.get("value");
		data.class = classN;
		data.field = fieldName;
		data.value = nameField.get('value');
	});
});


<% if (!StringHelper.isEmpty(dtoJson)) { %>
	var formContent = <%=dtoJson %>;
	BUI.FormHelper.setFields($("#addForm")[0], formContent[0]);
<% } %>



$("body").not("input").click(function() {
	$("input").siblings(".bdsug").hide();
});


</script>

<body>
</html>  
