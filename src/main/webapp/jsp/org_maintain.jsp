<%@ page import="com.abs.ps.util.Constants"%>
<%@ page import="com.abs.ps.web.dto.NameCodeDto"%>
<%@ page import="java.util.List"%>
<%@ page import="com.abs.ps.util.StringHelper"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.ps.web.dto.OrgDto"%>
<%@ page import="com.abs.ps.util.QueueConstants"%>
<%
	Long imgMaxSize = (Long) request.getAttribute("IMG_MAX_SIZE");
	if(imgMaxSize == null){
		imgMaxSize = Long.parseLong("10485760"); //默认最大10M图片大小
	}
	
	List<NameCodeDto> entDataTypeList = (List) request.getAttribute(Constants.ENT_DATA_TYPE_LIST);
	List<NameCodeDto> orgDefineDataList = (List) request.getAttribute("ORG_DEFINE_ENT_DATA");
	OrgDto centerDto = (OrgDto) request.getAttribute(QueueConstants.QUEUE_CENTER);
	String id = "";
	String centerName = "";
	String centerCode = "";
	String status = "";
	String maxAppointDay = "";
	String isWorkingDay = "";
	String periods = "";
	String appointType = "";
    boolean needConfirm = false;
	
	 String logoUrl = "";
	 String descr = "";
	 int activityAmt = 0;
	 int hotNoticeAmt = 0;
	 int hotActivityAmt = 0;
	 int hotEnrollAmt = 0;
	
	 String rotateImgUrl1 = "";
	 String rotateImgUrl2 = "";
	 String rotateImgUrl3 = "";
	 String rotateImgUrl4 = "";
	 String rotateImgUrl5 = "";
	 
	 String imgForwardUrl1 = "";
	 String imgForwardUrl2 = "";
	 String imgForwardUrl3 = "";
	 String imgForwardUrl4 = "";
	 String imgForwardUrl5 = "";
	 
	 boolean hasLogo = false;
	 boolean hasRotateImg1 = false;
	 boolean hasRotateImg2 = false;
	 boolean hasRotateImg3 = false;
	 boolean hasRotateImg4 = false;
	 boolean hasRotateImg5 = false;
	if (centerDto != null) { 
		id = centerDto.getOid();
		centerName = centerDto.getOrgName();
		centerCode = centerDto.getOrgCode();
		status = centerDto.getStatus();
		maxAppointDay = centerDto.getMaxAppointDay();
		isWorkingDay = centerDto.getIsWorkingDay();
		periods = centerDto.getPeriods();
		appointType = centerDto.getAppointTypes();
		
		needConfirm = centerDto.isNeedConfirm();
		logoUrl = request.getContextPath() + "/" + centerDto.getLogoUrl();

		descr = centerDto.getDescr();

		activityAmt = centerDto.getActivityAmt();
		hotNoticeAmt = centerDto.getHotActivityAmt();
		hotActivityAmt = centerDto.getHotActivityAmt();
		hotEnrollAmt = centerDto.getHotEnrollAmt();
		
		rotateImgUrl1 = request.getContextPath() + "/" + centerDto.getRotateImgUrl1();
		rotateImgUrl2 = request.getContextPath() + "/" + centerDto.getRotateImgUrl2();
		rotateImgUrl3 = request.getContextPath() + "/" +centerDto.getRotateImgUrl3();
		rotateImgUrl4 = request.getContextPath() + "/" +centerDto.getRotateImgUrl4();
		rotateImgUrl5 = request.getContextPath() + "/" +centerDto.getRotateImgUrl5();
		
		hasLogo = !StringHelper.isEmpty(centerDto.getLogoUrl());
		hasRotateImg1 = !StringHelper.isEmpty(centerDto.getRotateImgUrl1());
		hasRotateImg2 = !StringHelper.isEmpty(centerDto.getRotateImgUrl2());
		hasRotateImg3 = !StringHelper.isEmpty(centerDto.getRotateImgUrl3());
		hasRotateImg4 = !StringHelper.isEmpty(centerDto.getRotateImgUrl4());
		hasRotateImg5 = !StringHelper.isEmpty(centerDto.getRotateImgUrl5());
		
		imgForwardUrl1 = StringHelper.filterNullStr(centerDto.getImgForwardUrl1());
		imgForwardUrl2 = StringHelper.filterNullStr(centerDto.getImgForwardUrl2());
		imgForwardUrl3 = StringHelper.filterNullStr(centerDto.getImgForwardUrl3());
		imgForwardUrl4 = StringHelper.filterNullStr(centerDto.getImgForwardUrl4());
		imgForwardUrl5 = StringHelper.filterNullStr(centerDto.getImgForwardUrl5());
	} 
	
	String errorCode = (String) request.getAttribute("ERROR_CODE");
	
	

%>
<!DOCTYPE HTML>
<html>
 <head>
  <title></title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="..<%=request.getContextPath()%>/ueditor/themes/default/css/umeditor.min.css" />
    <link href="..<%=request.getContextPath()%>/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="..<%=request.getContextPath()%>/assets/css/page.css" rel="stylesheet" type="text/css" />  
    <link href="..<%=request.getContextPath()%>/assets/css/close-img.css" rel="stylesheet" type="text/css" />  
	
	<style type="text/css">
	.controls ul{
		margin-left: 0px;
	}
	
	.input-imgButton {
		margin-right: 10px;
		margin-bottom: 5px;
	}
	
	.div-img-forward {
		float: right;
		display: none;
	}
	
	.div-img-forward input {
		width: 400px;
	}
	
	.div-row .control-group{
		padding-left: 30px;
	}
	
	p {
		line-height: initial;
		font-size: initial;
	}
	
	span {
	}
	
	
	</style>
	
 </head>
 <body>
<div class="container">
  <div class="doc-content">
  <form class="form-horizontal well" id="J_Form"  enctype="multipart/form-data" action="<%=request.getContextPath()%>/mgHandler.html?op_action=ORG_SAVE"  method="post">
  	<div class="div-row div-content">
   <input name="oid" id="oid" value="<%=id%>" type="hidden"/>
    <h3>单位信息</h3>
    <div class="row">
      <div class="control-group span15">
        <label class="control-label"><s>*</s>单位名称：</label>
        <div class="controls">
          <input type="text" name="orgName" id="centerName" data-rules="{required:true,maxlength:50}" class="input-large control-text" value="<%=centerName %>">
        </div>
      </div>
    </div>
	<div class="row">
		<div class="control-group span15">
			<label class="control-label"><s>*</s>单位代码：</label>
			<div class="controls">
			  <input type="text" name="orgCode" id="centerCode" data-rules="{required:true,maxlength:20}" class="input-large control-text" value="<%=centerCode %>">
			</div>
		</div>
	</div>
	<div class="row">
	  <div class="control-group span15" style="width: 600px;">
			<label class="control-label" style="">单位LOGO：</label>
			<div class="div-img" id="div_ImgShow" style="margin-left:10px;">
				<ul id="warp">
					<li>
						<input type='button' value='选择图片' id="logoButton" class="input-imgButton" />
						<input type="file" id="logoUrl" name="logoUrl" accept=".jpg,.jpeg,.png,.bmp"/>
						<input type="hidden" class="img-token" name="logoToken" value="<%if(hasLogo){out.print(centerDto.getLogoUrl());} %>">
					</li>
					<li>
						<div class="div-close" style="<% if(!hasLogo){out.print("display:none;");} %>">
							<img id="logo" src="<%if(hasLogo){out.print(logoUrl);} %>">
							<span class="close-background">
								<span class="close cancel-button"></span>
							</span>
						</div>
					</li>
				</ul>
			</div>

		</div>
    </div>

	<div class="row editor-div" style="height: 570px;">
		<div class="control-group span17">
				<label class="control-label">单位描述：</label>
				<div class="controls  control-row4">
					<textarea name="descr" id="descr"><%=descr==null?"":descr%></textarea>
				</div>
		</div>
	</div>

    <div class="row">
      <div class="control-group span8">
        <label class="control-label"><s>*</s>状态：</label>
        <div class="controls">
           <select name="status" class="span2 span-width" data-rules="{required:true}">
			  <option value="1" <% if (centerDto == null){out.print("selected");} else if("1".equals(status)){out.print("selected");} %>>正常</option>
			  <option value="0" <% if("0".equals(status)){out.print("selected");} %>>失效</option>
			</select>
        </div>
      </div>
	 </div>	
     
    <hr>

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
<script type="text/javascript" src="..<%=request.getContextPath()%>/ueditor/umeditor.config.js"></script>  
<script type="text/javascript" src="..<%=request.getContextPath()%>/ueditor/umeditor.js"></script>  
<script type="text/javascript">

var editor = UM.getEditor('descr',{
	initialFrameWidth:400,  //初始化编辑器宽度
	initialFrameHeight:480,  //初始化编辑器高度
	autoHeightEnabled:false, //是否自动长高
});


editor.addListener("keyup", function(type, event) {
	// var count = editor.getContentLength(true);
	var count = $(".edui-body-container").html().length;
	if(count>3500){
		var contentText = editor.getContentTxt();
		editor.setContent(contentText.substring(0, 3500));
		alert("超出字数限制");
	}
});

$(function(){
	var _maxSize = <%=imgMaxSize %>; //图片最大大小

	var logoUrl = $("#logoUrl");
	
	var imgButton = $("#logoButton");
	var tempSize = _maxSize/1048576; // 除以1024*1024
	imgButton.after("<font style='color:grey'>图片大小不超过"+tempSize+"MB，支持jpg/jpeg/png/bmp图片格式</font>");
	imgButton.click(function(){
		logoUrl.click();
	})

	logoUrl.change(function(){
		var logo = $("#logo");
		var logoDiv = logo.parent();
		var imgFile = $(this)[0].files[0];
		
		var fileName = imgFile.name;
		imgButton.next("font").remove();
		if(/(.jpg$)|(.bmp$)|(.png$)|(.jpeg$)/.test(fileName)){
			if(typeof(imgFile) != "undefined"){
				var size = imgFile.size;
				if(size > _maxSize){
					imgButton.after("<font style='color:red;'>图片太大，请重新选择</font>");
					$(this).attr("value","");
					logoDiv.hide();
				}else{
					logo.attr("src",getFileUrl("logoUrl"));
					logoDiv.show();
				}
			}else{
				$(this).attr("value","");
				logoDiv.hide();
			}
			$("input[name='logoToken']").val(""); //表示保存在数据库原来图片被更改
		}else{
			imgButton.after("<font style='color:red;'>文件类型不正确，请重新选择</font>");
			$(this).attr("value","");
			logoDiv.hide();
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
	
	var rotateInput = $("#div_RotateImg input[type='file']");
	var rotateToken = $("#div_RotateImg input[type='hidden']");
	var rotateButtons = $("#div_RotateImg input[type='button']");
	var rotateImg = $("#div_RotateImg img");
	
	rotateInput.hide();
	
	rotateButtons.click(function(){
		var imgId = $(this).attr("id");
		var num = imgId.substring(imgId.length-1, imgId.length);
		rotateInput.eq(num-1).click();
	});
	
	rotateInput.change(function(){
		var imgId = $(this).attr("id");
		var num = imgId.substring(imgId.length-1, imgId.length);
		var rotateImgSize = $(this)[0].files[0].size;
		var currentImg = rotateImg.eq(num-1);
		var currentImgDiv = currentImg.parent();
		
		var fileName = $(this)[0].files[0].name;
		$(this).next("font").remove();
		// 判断文件类型是否为指定图片
		if(/(.jpg$)|(.bmp$)|(.png$)|(.jpeg$)/.test(fileName)){
			// 判断图片大小是否在指定大小内
			if(rotateImgSize > _maxSize){
				$(this).after("<font style='color:red;'>图片太大，请重新选择！</font>");
				$(this).attr("value","");
				currentImgDiv.hide();
			}else{
				currentImg.attr("src",getFileUrl(imgId));
				currentImgDiv.show();
				$(this).parents(".div-img").find(".div-img-forward").show();
				rotateToken.eq(num-1).attr("value",""); //表示图片发生改变
			}
		}else{
			$(this).after("<font style='color:red;'>文件类型不正确，请重新选择</font>");
			$(this).attr("value","");
			currentImgDiv.hide();
		}
	});
	
	// 取消图片按钮
	$(".close-background").click(function() {
		$(this).parent().hide();
		$(this).parents("ul").find(":file").eq(0).attr("value", "");
		$(this).parents("ul").find(".img-token").eq(0).attr("value", "");
		$(this).parents(".div-img").find(".div-img-forward").attr("value", "");
		$(this).parents(".div-img").find(".div-img-forward").hide();
	});
	
});

BUI.use('bui/form',function (Form) {
   var form = new Form.HForm({
     srcNode : '#J_Form'
   });
   form.render();
});



</script>

<body>
</html>  
