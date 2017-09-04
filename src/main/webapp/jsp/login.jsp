<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.abs.ps.util.QueueConstants"%>
<%@ page import="com.abs.ps.util.CookieHelper"%>
<%
	String myMsg = (String) request.getAttribute("QUEUE_MESSAGE");
	if (myMsg != null) {
		if (myMsg.equals(QueueConstants.LOGIN_INVALID_USERID)) {
			myMsg = "用户不存在！";
		} else if (myMsg.equals(QueueConstants.LOGIN_INVALID_PASSWORD)) {
			myMsg = "密码错误！";
		} else if (myMsg.equals(QueueConstants.LOGIN_DUPLICATE_LOGIN)) {
			myMsg = "该账号在别处已经登录!";
		} else if (myMsg.equals(QueueConstants.USER_EXPIRE)) {
			myMsg = "试用到期，请办理正式使用权限。";
		}  else if (myMsg.equals(QueueConstants.USER_UNAVAILABLE)) {
			myMsg = "账号未启用或正等待审核";
		} 
	} else {
		myMsg = "";
	}//
	
	
	String userId = CookieHelper.getCookieValue(request, response, CookieHelper.ABS_USERID_COO);
	
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>立佳欣ERP管理系统</title>  
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/pintuer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/admin.css">
    <style type="text/css">
    .reset-button {
    	background: url(<%=request.getContextPath()%>/assets/img/sprite-469-300.png) no-repeat -312px 0px;
		height: 16px;
		width: 16px;
    	position: absolute;
    	cursor: pointer;
		display: block;
		right: 35px;
		top: 18px;
    }
    
 	#footer {
		background-color: unset;
		opacity: 0.7;
	}
    
   	#footer a{
   		color: white;
   	}
   	
   	#footer a:hover {
   		color:red;
   	}

	.del-btn {
		right: 25px !important;
		opacity: 0.6;
		cursor:pointer;
	}
	
	.login {
		background: #fefefe;
		border-top: 1px;
		-webkit-border-radius:5px;
	    -moz-border-radius:5px;
	    border-radius:5px;
		color: #444;
		width: 420px;
		position: absolute;
		left: 50%;
		top: 50%;
		margin: -220px 0 0 -210px;
	}
	
	.login-scroll {
		top: 0;
		margin-top: 15px;
	}
	
	.fill {
		height:575px;
	}
	
	.input-help {
	    float: right;
	    line-height: 30px;
	}
	
    </style>
    <script src="<%=request.getContextPath()%>/assets/js/jquery-1.8.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/pintuer.js"></script>
</head>
<script language="JavaScript" type="text/javascript" defer=true>
history.go(1);

var userid = '<%=userId%>';

function fsubmit() {
	var userid = document.getElementById("userid").value.trim();
	if (userid == ''){
		alert("请输入用户名！");
		return;
	}
	var password = document.getElementById("password").value.trim();
	if (password == ''){
		alert("请输入密码！");
		return;
	}
	document.getElementById("login-form").submit();
}

document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];          
     if(e && e.keyCode==13){ 
    	 fsubmit();
    }
}; 

function fonload() {

var _msg = '<%=myMsg%>';

if (_msg != '' && _msg.length > 0) {
	alert(_msg);
	return;
}

if (userid != '' && userid != 'null' && userid != null) {
	document.getElementById('userid').value=userid;
}

}

$(function(){

	$(".reset-button").click(function(){
		$(this).siblings("input")[0].value = "";
	});

});


</script>
<body onload="fonload();" style="overflow: auto;">
		<div class="fill"></div>
        <form class="login" id="login-form" action="<%=request.getContextPath()%>/login.html" method="post">
            <input type="hidden" name="op_action" value="LOGIN"/>
            <div class="panel loginbox" style="background: white;"> 
            	<!-- <img src="assets/img/icon.jpg" style="margin: 0 auto; display: block;"> -->
                <div class="text-center margin-big padding-big-top" style="margin-top: 0;"></div>
                <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                    <div class="form-group" style="margin-bottom: 20px;">
                        <div class="field field-icon-right">
                            <input type="text" class="input input-big" name="userid" id="userid" placeholder="登录账号" data-validate="required:请填写账号" style="border-width: 2px;" />
							<span class="icon icon-times margin-small del-btn"></span>
                            <span class="icon icon-user margin-small"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input input-big" name="password" id="password" placeholder="登录密码" data-validate="required:请填写密码" style="border-width: 2px;"/>
							<span class="icon icon-times margin-small del-btn"></span>
                            <span class="icon icon-key margin-small"></span>
                        </div>
                    </div> 
                </div>
                <div style="padding:30px;padding-top:0px;">
                    <div style="padding-bottom: 10px;padding-left:30px;">
	                	<input type="checkbox" name="remember" value="Y">记住账号</input>
	                </div>
                	<input type="submit" class="button button-block bg-main text-big input-big" value="登录">
                </div>
            </div>
        </form>          

		<p id="footer">
			主办方：中山立佳欣塑胶制品有限公司
			<br>
			技术支持：
			<a href="http://www.zhabs.cn/AnBang/index.html" target="view_window">
				<u>珠海市安邦软件有限公司</u>
			</a>
			&nbsp;&nbsp;粤ICP备17017084号
		</p>
		<img src="assets/img/bg_login.png" style="position: fixed; bottom: 0;z-index: -1;height:100%;width:100%;min-height: 720px;min-width:1260px;">
<script>

window.onresize = function(){
	toggleFormScroll();
}
toggleFormScroll();
function toggleFormScroll() {
	var $fm = $("#login-form");
	if (window.innerHeight < 668) {
		if (!$fm.hasClass("login-scroll")) {
			$fm.addClass("login-scroll");
		}
	} else {
		$fm.removeClass("login-scroll");
	}
}

$(".del-btn").css("opacity", "0");
$(".del-btn").click(function() {
	var $input = $(this).siblings("input");

    $input.val("");
	$input.focus();
})

$(".input").on('focus keyup', function() {
	if (this.value.length > 0) {
		$(this).siblings(".del-btn").css("opacity", "1");
	}
});
$(".input").blur(function() {
	$(this).siblings(".del-btn").css("opacity", "0.5");
});

</script>
</body>
</html>