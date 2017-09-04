<%@page import="com.abs.ps.util.StringHelper"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.ps.web.dto.UserDto"%>
<%@ page import="com.abs.ps.web.dto.NameCodeDto"%>
<%@ page import="java.util.List" %>
<%@ page import="com.abs.ps.util.QueueConstants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	Long imgMaxSize = (Long) request.getAttribute("IMG_MAX_SIZE");
	if(imgMaxSize == null){
		imgMaxSize = Long.parseLong("10485760"); //默认最大10M图片大小
	}

	List<NameCodeDto> nameCodeList = (List<NameCodeDto>) request.getAttribute(QueueConstants.QUEUE_CENTER_LIST);	
	UserDto userDto = (UserDto) request.getAttribute(QueueConstants.QUEUE_USER);	
	Long oid = -1L; 
	String sex = "";
	String userid = "";
	String username = "";
	String password = "";
	String nickName = "";
	String status = "";
	String defaultAdmin = (String)request.getAttribute("DEFAULT_ADMIN");
	Boolean isAdmin = "Y".equals(defaultAdmin)?true:false;
	String email = "";
	String mobileNum = "";
	String entOid = "";
	String imgUrl = "";
	boolean hasImg = false;
	
	String orgOid = (String) request.getAttribute("DEFAULT_ORG");
	if (userDto != null) { 
		oid = userDto.getId();
		sex = userDto.getSex();
		userid = userDto.getUserId();
		username = userDto.getUserName();
		password = userDto.getPassword();
		nickName = userDto.getNickName();
		status = userDto.getStatus();
		isAdmin = userDto.isAdmin();
		email = userDto.getEmail();
		mobileNum = userDto.getMobileNum();
		orgOid = String.valueOf(userDto.getOrgOid());
		entOid = String.valueOf(userDto.getEntOid());
		
		imgUrl = userDto.getImgUrl();
		if(!StringHelper.isEmpty(imgUrl)){
			hasImg = true;
		}
	}
	
	String showCompany = "N";
	if (!isAdmin) {
		showCompany = "Y";
	}
	
	String errorCode = (String) request.getAttribute("ERROR_CODE");
	String userTypeSelected = (String) request.getAttribute("USER_TYPE_SELECTED");
	
	List<NameCodeDto> entList = (List<NameCodeDto>) request.getAttribute("ENT_LIST");
	

%>
<!DOCTYPE HTML>
<html>
 <head>
  <title></title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="..<%=request.getContextPath()%>/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/page-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/close-img.css" rel="stylesheet" type="text/css" />    

<style type="text/css">

.input-imgButton {
	margin-right: 10px;
	margin-bottom: 5px;
}

</style>

 </head>
 <body>
<div class="container">
  	<div class="doc-content">
	<form class="form-horizontal well" id="J_Form" enctype="multipart/form-data" action="<%=request.getContextPath()%>/mgHandler.html?op_action=USER_SAVE"  method="post">
	<div class="div-content div-row">
   	<input name="oid" id="oid" value="<%=oid%>" type="hidden"/>
    <h3>用户信息</h3>
    <div class="row">
    	<div class="control-group span8">
        <label class="control-label"><%if (userDto == null ) {%><font color='red'>*</font><%} %>用户类型：</label>
        <div class="controls">
           <%
           	  if (userDto != null&&oid>1 ) {
           			if (userDto.isAdmin()) {out.print("管理员");} else {out.print("普通用户");}
           	  } else {
           %>
			<%if (isAdmin) { %><input onChange="userTypeChange('1');" name="role" id="role" <% if(userTypeSelected != null && userTypeSelected.equals("1")){out.print("checked");} %> value="1" class="text" type="radio">管理员 <% } %>
			<input onChange="userTypeChange('0');"
			<%if(!isAdmin && userTypeSelected == null){out.print("checked");} %> name="role" id="role" <% if(userTypeSelected != null && userTypeSelected.equals("0")){out.print("checked");} %> value="0" class="text" type="radio">普通用户
		<% } %>
		</div>
      </div>
    </div>
    
    <div class="row">
      <div class="control-group span15">
        <label class="control-label">头&nbsp;&nbsp;像：</label>
        <div class="input-append" style="position: relative;float: left;margin-bottom: 10px;">
          	<ul id="warp" style="margin-left: 10px;">
				<li>
					<input type='button' id="imgButton" class="input-imgButton" value='选择图片'/>
					<input type="file" id="imgUrl" name="imgUrl" accept=".jpg,.jpeg,.png,.bmp"/>
					<input type="hidden" name="imgToken" class="img-token" value="<%=imgUrl %>" />
				</li>
				<li>
					<div class="div-close">
						<img id="showImg" src="<%if(hasImg){out.print(imgUrl);} %>" style="height:150px; width:220px;<% if(!hasImg){out.print("display:none;");} %>">
						<span class="close-background">
							<span class="close cancel-button"></span>
						</span>
					</div>
				</li>
			</ul>
        </div>
      </div>
     
    </div>
    
    <div class="row">
      <div class="control-group span12">
        <label class="control-label"><font color='red'>*</font>用户&nbsp;ID：</label>
        <div class="controls">
          <input type="text" name="userid" id="userid" data-rules="{required:true}" class="input-normal control-text" value="<%=userid %>">
        </div>
      </div>
     
    </div>
    <div class="row">
     <div class="control-group span12">
        <label class="control-label"><font color='red'>*</font>用户名称：</label>
        <div class="controls">
          <input type="text"  name="username" id="username" data-rules="{required:true}" class="input-normal control-text" value="<%=username %>">
        </div>
      </div>
    </div>
    
    <div class="row">
      <div class="control-group span12">
        <label class="control-label"><font color='red'>*</font>用户密码：</label>
        <div class="controls">
			<input type="text"  name="password" id="password" data-rules="{required:true}" class="input-normal control-text" value="<%=password %>">
		</div>
      </div>
    </div>
    
   	<div class="row">
	 <div class="control-group span12">
        <label class="control-label"><font color='red'>*</font>联系电话：</label>
        <div class="controls">
			<input type="text" name="mobileNum" id="mobileNum" data-rules="" class="input-normal control-text" value="<%=mobileNum %>">
        </div>
      </div>  
	</div>
    
    <div class="row">
      <div class="control-group span12">
        <label class="control-label">昵&nbsp;&nbsp;称：</label>
        <div class="controls">
			<input type="text"  name="nickName" id="nickName"  class="input-normal control-text" value="<%=nickName %>">
		</div>
      </div>
    </div>
    
      <div class="row">
      <div class="control-group span12">
        <label class="control-label">性&nbsp;&nbsp;别：</label>
        <div class="controls">
			<input name="sex"  id="sex" <% if(sex != null && sex.equals("1")){out.print("checked");} %> value="1" class="text" type="radio" >男
			<input name="sex"  id="sex" <% if(sex != null && sex.equals("0")){out.print("checked");} %> value="0" class="text" type="radio" >女
		</div>
      </div>
    </div>

	
    
  
   	<div class="row">
      <div class="control-group span12">
        <label class="control-label">邮&nbsp;&nbsp;箱：</label>
        <div class="controls">
			<input type="text" name="email" id="email"  class="input-normal control-text" value="<%=email %>">
        </div>
      </div>
	</div>
	
	<div class="row">
      <div class="control-group span12">
        <label class="control-label">状&nbsp;&nbsp;态：</label>
        <div class="controls">
           <select name="status"  class="span2 span-width" data-rules="{required:true}">
			  <option value="1" <% if (userDto == null){out.print("selected");} else if("1".equals(status)){out.print("selected");} %>>正常</option>
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


function userTypeChange(type){

	var target=document.getElementById("ent_layer");
	if ("1" == type){
		target.style.display="none";
	} else {
		target.style.display="block";      		
	}
}

function checkUserType() {
	var chkObjs = document.getElementsByName("role"); 
	var isChecked = false;
	for(var i=0;i<chkObjs.length;i++){ 
		if(chkObjs[i].checked){ 
			isChecked = true;
		} 
	}
	if (!isChecked) {
		alert("请先选择用户类型！");
	}
	return;
}

function isEmptyObject() {
	 var objList = document.getElementById("centerCode");
	 if(objList.length<="1"){
		 alert("区域信息为空，请先选择用户类型！");
	 }
	 return;
}  

<%-- function onload() {
	var target=document.getElementById("ent_layer");
	var showCompany = '<%=showCompany%>';
	if (showCompany == 'Y') {
		target.style.display="block";
	} else {
		target.style.display="none";
		 
	}
} --%>

$(function(){
	var _maxSize = <%=imgMaxSize %>; //图片最大大小

	var imgUrl = $("#imgUrl");
	imgUrl.hide();
	
	var imgButton = $("#imgButton");
	var tempSize = _maxSize/1048576; // 除以1024*1024
	imgButton.after("<font style='color:grey'>图片大小不超过"+tempSize+"MB，支持jpg/jpeg/png/bmp图片格式</font>");
	imgButton.click(function(){
		imgUrl.click();
	});
	
	imgUrl.change(function(){
		var imgHint = $("#img_hint");
		var showImg = $("#showImg");
		var imgFile = $(this)[0].files[0];
		
		var fileName = imgFile.name;
		imgButton.next("font").remove();
		if(/(.jpg$)|(.bmp$)|(.png$)|(.jpeg$)/.test(fileName)){
			if(typeof(imgFile) != "undefined"){
				imgHint.hide();
				var size = imgFile.size;
				if(size > _maxSize){
					imgButton.after("<font style='color:red;'>图片太大，请重新选择</font>");
					$(this).attr("value","");
					showImg.hide();
				}else{
					showImg.attr("src",getFileUrl("imgUrl"));
					showImg.show();
					$(".div-close").show();
				}
			}else{
				$(this).attr("value","");
				imgHint.show();
				showImg.hide();
			}
			$("input[name='imgToken']").val(""); //表示保存在数据库原来图片被更改
		}else{
			imgButton.after("<font style='color:red;'>文件类型不正确，请重新选择</font>");
			$(this).attr("value","");
			showImg.hide();
		}
	});
	
	function getFileUrl(sourceId) {
		var url;
		if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
			url = document.getElementById(sourceId).value;
		} else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
			url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
		} else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
			url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
		}
		return url;
	}
	
	// 取消图片按钮
	$(".close-background").click(function() {
		$(this).parent().hide();
		$(this).parents("ul").find(":file").eq(0).attr("value", "");
		$(this).parents("ul").find(".img-token").eq(0).attr("value", "");
	});
	
});


</script>

<body>
</html>  
