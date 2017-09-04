<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.core.paging.IPaging"%>
<%@ page import="com.abs.ps.web.dto.OrganizationDto"%>
<%@ page import="java.util.*"%>
<%
IPaging myPage = (IPaging) request.getAttribute("QUEUE_PAGING");
List<OrganizationDto> list = myPage.getThisPageElements();

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
      
     
		<form name="delForm" id="delForm" method="post" action="<%=request.getContextPath()%>/mgHandler.html?op_action=ORG_DELETE">
        <table cellspacing="0" class="table table-bordered">
          <thead>
            <tr>
           
              <th>单位名称</th>
              <th width="20%">状态</th>
              <th width="20%">操作</th>
            </tr>
          </thead>
          <tbody>
<% 
	for (OrganizationDto centerDto : list) {
%>     
                  <tr>
					<td><%=centerDto.getOrgName() %></td>
                    <td><%if("1".equals(centerDto.getStatus())){out.print("正常");}else{out.print("失效");} %></td>
					<td><a href="<%=request.getContextPath()%>/mgHandler.html?op_action=ORG_MODIFY&oid=<%=centerDto.getId()%>">编辑</a></td>
                  </tr>
<% 
	}
%>  
          </tbody>
        </table>
		</form>

      

      </div>
    </div> 
  
</div>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/bui-min.js"></script>
<script type="text/javascript" src="..<%=request.getContextPath()%>/assets/js/config-min.js"></script>
<script type="text/javascript">


</script>

<body>
</html>  
