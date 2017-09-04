<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.abs.ps.web.dto.UserDto"%>
<%@ page import="com.abs.ps.web.dto.MenuItemDto"%>
<%@ page import="com.abs.ps.util.QueueConstants"%>
<%@ page import="java.util.*"%>
<%
	UserDto userDto = (UserDto) session.getAttribute(QueueConstants.QUEUE_USER_SESSION);
	String userid = "";
	String userName = "";
	if (userDto != null) {
		userid = userDto.getUserId();
		userName = userDto.getUserName();
	}
	 
	//Map<String, List<MenuItemDto>> map = (Map<String, List<MenuItemDto>>) session.getAttribute(QueueConstants.QUEUE_MENU);
	String menuStr = (String) session.getAttribute(QueueConstants.QUEUE_MENU);

%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>立佳欣ERP管理系统</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link href="../<%=request.getContextPath()%>/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
   <link href="../<%=request.getContextPath()%>/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
   <link href="../<%=request.getContextPath()%>/assets/css/main-min.css" rel="stylesheet" type="text/css" />
   <style>
		.workbench-refresh {
			display: block;
			position: absolute;
			text-decoration: none;
			overflow: hidden;
			cursor: pointer;
			width: 13px;
			height: 13px;
			text-indent: -99px;
			z-index: 3;
			top: 4px;
			right: 17px;
		}
		
	</style>
 </head>
 <body>

  <div class="header">
  
      <div class="dl-title">
          <span class="dl-title-text">立佳欣ERP管理系统</span>
      </div>

    <div class="dl-log">欢迎您，<span class="dl-log-user"><%=userName %></span><a href="<%=request.getContextPath()%>/login.html?op_action=LOGOUT&userid=<%=userid%>" target="_top" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
  </div>
   <div class="content">
    <div class="dl-main-nav">
      <div class="dl-inform"><div class="dl-inform-title">贴心小秘书<s class="dl-inform-icon dl-up"></s></div></div>
      <ul id="J_Nav"  class="nav-list ks-clear">
        <li class="nav-item dl-selected"><div class="nav-item-inner nav-home"></div></li>
       <!--   <li class="nav-item"><div class="nav-item-inner nav-order">表单页</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-inventory">搜索页</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-supplier">详情页</div></li>-->
      </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
   </div>
  <script type="text/javascript" src="../<%=request.getContextPath()%>/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="../<%=request.getContextPath()%>/assets/js/bui.js"></script>
  <script type="text/javascript" src="../<%=request.getContextPath()%>/assets/js/config.js"></script>

  <script>
	BUI.use('common/main',function(){
		var config = <%=menuStr%>;
		
		var mainPage = new PageUtil.MainPage({
		  modulesConfig : config
		});
		
		// 添加工作台tab页面
		/*var workbench = {
			id : 'work_bench',
			title : '工作台',
			href : 'mgHandler.html?op_action=WORK_BENCH',
			closeable : false
		};
		mainPage.openPage(workbench);
	    // 添加工作台tab页面刷新按钮
		var wb_ref_button = $(".bui-nav-tab-item[title='工作台'] .tab-item-close"); // 替换工作台关闭按钮为刷新按钮
		wb_ref_button.attr({class: "icon-refresh workbench-refresh", style:"display:block"});
		wb_ref_button.click(function(){
			mainPage.reloadPage("work_bench");
		});*/
	});
    
    
  </script>
 </body>
</html>
