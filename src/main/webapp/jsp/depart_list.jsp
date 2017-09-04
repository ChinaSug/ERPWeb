<%@page import="com.abs.ps.web.dto.OrganizationDto"%>
<%@page import="com.abs.ps.web.dto.DepartmentDto"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.core.paging.IPaging"%>
<%@ page import="java.util.*"%>
<%
IPaging myPage = (IPaging) request.getAttribute("QUEUE_PAGING");
List<DepartmentDto> list = myPage.getThisPageElements();


String isAllowAdd = (String) request.getAttribute("IS_ALLOW_ADD");

%>
<!DOCTYPE HTML>
<html>
 <head>
  <title></title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
     <link href="..<%=request.getContextPath()%>/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/page-min.css" rel="stylesheet" type="text/css" />  
   <style type="text/css">
    code {
      padding: 0px 4px;
      color: #d14;
      background-color: #f7f7f9;
      border: 1px solid #e1e1e8;
    }
   </style>
 </head>
 <body>
  
<div class="container">
  
   <div class="row">
      <div class="doc-content">
        <form class="form-panel" action="post">
          
          <ul class="panel-content">
            <li>
              <input class="control-text" type="text" placeholder="请输入区域名称"/>
              <button type="submit" class="button button-primary"><i class="icon-white icon-search"></i>查询</button>
            </li>
          </ul>
        </form>
		<form name="delForm" id="delForm" method="post" action="<%=request.getContextPath()%>/mgHandler.html?op_action=DEPART_DELETE">
        <table cellspacing="0" class="table table-bordered">
          <thead>
            <tr>
              <th width="5%"><label class="checkbox"><input type="checkbox" onClick="selectAll(this);">全选</label></th>
              <th width="20%">区域名称</th>
              <th width="10%">状态</th>
              <th width="5%">操作</th>
            </tr>
          </thead>
          <tbody>
	<% 
	for (DepartmentDto centerDto : list) {
	%>     
           <tr>
			 <td><%if(centerDto.isDeletable()){%><input type="checkbox" value="<%=centerDto.getId()%>" id="delid" name="delid"/><%} %></td>
			 <td><%=centerDto.getDepartName() %></td>
             <td><%if("1".equals(centerDto.getStatus())){out.print("正常");}else{out.print("失效");} %></td>
			 <td><a href="<%=request.getContextPath()%>/mgHandler.html?op_action=DEPART_MODIFY&oid=<%=centerDto.getId()%>">编辑</a></td>
           </tr>
	<% 
	}
	%>  
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
	 window.location.href = "<%=request.getContextPath()%>/mgHandler.html?op_action=DEPART_ADD"; 
}
</script>

<body>
</html>  
