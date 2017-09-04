<%@page import="com.abs.ps.web.dto.DepartmentDto"%>
<%@page import="com.abs.ps.web.dto.OrganizationDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.ps.web.dto.OrgDto"%>
<%@ page import="com.abs.ps.util.QueueConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

	List<OrganizationDto> orgDto=(List)request.getAttribute(QueueConstants.AM_ORG_LIST);
	DepartmentDto departDto=(DepartmentDto)request.getAttribute(QueueConstants.AM_DEPARTMENT);
	String userOrgOid = (String)request.getAttribute("ORG_OID");
	long id=0;
	String org_oid="";
	String status="";
	String departName="";
	String orgName="";
	if(departDto!=null){
		//id=departDto.getId();
		org_oid=departDto.getOrgOid();
		status=departDto.getStatus();
		departName=departDto.getDepartName();
		orgName=departDto.getOrgName();
	}

	String errorCode = (String) request.getAttribute("ERROR_CODE");

%>
<!DOCTYPE HTML>
<html>
 <head>
  <title></title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="..<%=request.getContextPath()%>/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/page-min.css?1" rel="stylesheet" type="text/css" />  

 </head>
 <body>
<div class="container">
  <div class="doc-content">
  <form class="form-horizontal well" id="J_Form" action="<%=request.getContextPath()%>/mgHandler.html?op_action=DEPART_SAVE"  method="post">
   <div class="div-content div-row">
   <h3>区域信息</h3>
   <input name="oid" id="oid" value="<%=id%>" type="hidden"/>
    <div class="row">
      <div class="control-group span12">
        <label class="control-label"><s>*</s>区域名称：</label>
        <div class="controls">
          <input type="text" name=departName id="departName" data-rules="{required:true}" class="input-normal control-text" value="<%=departName %>">
        </div>
      </div>
    </div>
    <div class="row">
    	<div class="control-group span12">
        <label class="control-label"><s>*</s>状&nbsp;&nbsp;态：</label>
        <div class="controls">
        	<select name="status" class="span2 span-width" data-rules="{required:true}" style= "height:25px;width:140px">
				<option value="1" <% if (departDto == null){out.print("selected");} else if("1".equals(status)){out.print("selected");} %>>正常</option>
				<option value="0" <% if("0".equals(status)){out.print("selected");} %>>失效</option>
			</select>
        </div>
      </div>
    </div>
	</div>
    <div class="actions-bar">
	  <div class="row ">
		<div class="span13 offset3 ">
		  <button type="submit" class="button button-primary">保存</button>
		  <button type="button" class="button"  onclick="javascript:history.back()" >返回</button>
		</div>
	  </div>
	</div>
  </form>
</div>
</div>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/bui-min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/config-min.js"></script>
<script type="text/javascript">


</script>

<body>
</html>  
