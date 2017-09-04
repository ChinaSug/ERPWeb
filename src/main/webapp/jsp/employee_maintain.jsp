<%@page import="com.abs.ps.dao.impl.DepartmentDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.ps.web.dto.AMEmployeeDto"%>
<%@ page import="com.abs.ps.web.dto.DepartmentDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.abs.ps.util.QueueConstants"%>
<%
	AMEmployeeDto empDto = (AMEmployeeDto) request.getAttribute(QueueConstants.AM_EMPLOYEE);
	List<DepartmentDto> depDto = (List) request.getAttribute(QueueConstants.AM_DEPARTMENT_LIST);
	Long id = 0L; 
	String empName = "";
	String empType = "";
	String status = "";
	Boolean isSupport = false;
	Boolean isWorking = false;
	String positionCode = "";
	String departOid = "";
	String departName = "";

	if (empDto != null) { 
		id = empDto.getId();
		empName = empDto.getEmpName();
		empType = empDto.getEmpType();
		status = empDto.getStatus();
		isSupport = empDto.isSupport();
		isWorking = empDto.isWorking();
		positionCode = empDto.getPositionCode();
		departOid = empDto.getDepartOid();
		departName = empDto.getDepartName();

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
    <link href="..<%=request.getContextPath()%>/assets/css/page-min.css" rel="stylesheet" type="text/css" />  

 </head>
 <body>
<div class="container">
  <div class="doc-content">
  <form class="form-horizontal well" id="J_Form" action="<%=request.getContextPath()%>/mgHandler.html?op_action=EMP_SAVE"  method="post">
   <input name="oid" id="oid" value="<%=id%>" type="hidden"/>
    <h3>员工信息</h3>
    <div class="row">
      <div class="control-group span8">
        <label class="control-label">职员名称：</label>
        <div class="controls">
          <input type="text" name="empName" id="empName" data-rules="{required:true}" class="input-normal control-text" value="<%=empName==null?"":empName %>">
        </div>
      </div>
    </div>
    
    <div class="row">
      <div class="control-group span8">
        <label class="control-label">职位编号：</label>
        <div class="controls">
			<input type="text" name="positionCode" id="positionCode" data-rules="{maxlength:10}" class="input-normal control-text" value="<%=positionCode==null?"":positionCode %>">
		</div>
      </div>
    </div>
	
	<div class="row">
      <div class="control-group span8">
        <label class="control-label">部门：</label>
        <div class="controls">
			<select name="depart_oid" id="depart_oid" class="span4 span-width" data-rules="{required:true}">
				<option value="" >请选择</option>
<% for(DepartmentDto dep : depDto){ %>
				<option value="<%=dep.getId() %>" <% if(empDto !=null && departOid.equals(String.valueOf(dep.getId()))){out.print("selected");} %>><%=dep.getDepartName() %></option>
<% } %>
			</select>
        </div>
      </div>
    </div>
    
   	<div class="row">
      <div class="control-group span8">
        <label class="control-label">状态：</label>
        <div class="controls">
           <select name="status" class="span2 span-width" data-rules="{required:true}">
			  <option value="1" <% if (empDto == null){out.print("selected");} else if("1".equals(status)){out.print("selected");} %>>正常</option>
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
		  <button type="button" class="button" onclick="javaScript:history.back()">返回</button>
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

BUI.use('bui/form',function (Form) {
   var form = new Form.HForm({
     srcNode : '#J_Form'
   });

   form.render();
 });

</script>

<body>
</html>  
