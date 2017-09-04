<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>脚本调试 </title>
<link rel="stylesheet" rev="stylesheet" href="..<%=request.getContextPath()%>/css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="..<%=request.getContextPath()%>/js/validate.js"></script> 

<script language="JavaScript" type="text/javascript">

var XMLHttpReq;  
function createXMLHttpRequest() {  
    if(window.XMLHttpRequest) { //Mozilla
        XMLHttpReq = new XMLHttpRequest();  
    }  
    else if (window.ActiveXObject) { // IE
        try {  
            XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");  
        } catch (e) {  
            try {  
                XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");  
            } catch (e) {}  
        }  
    }  
}  

function fsubmit() {
	
	var inputParam = document.getElementById("input_param").value;
	if (inputParam == '') {
		alert('请选择借用人');
		return false;
	}
	
	createXMLHttpRequest(); 
	
	var url = document.getElementById("addr").value + inputParam;
	XMLHttpReq.open("GET", url, true);  
    XMLHttpReq.onreadystatechange = callbackFn;
    XMLHttpReq.send(null); 
}

function callbackFn() {
	if (XMLHttpReq.readyState == 4) { 
        if (XMLHttpReq.status == 200) { 
        	var response = XMLHttpReq.responseText; 
        	document.getElementById("output_result").innerText=response;
			
        } else { //
            window.alert("您所请求的页面有异常！");  
        }  
    } 
}

</script>
<style type="text/css">
<!--
.atten {font-size:12px;font-weight:normal;color:#F00;}
-->
</style>
</head>

<body class="ContentBody" >

<div class="MainDiv">
<table width="99%" border="0" cellpadding="0" cellspacing="0" class="CContent">
  <tr>
      <th class="tablestyle_title" >脚本调试</th>
  </tr>
  <tr>
    <td class="CPanel">
		
		<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
		<TR>
			<TD width="100%">
				<fieldset style="height:100%;">
				<legend></legend>
					  <table border="0" cellpadding="2" cellspacing="1" style="width:100%">
					 
					 <tr>
					     <td nowrap align="right" width="20%">选择调试地址:</td>
					     
					     <td width="80%">
							<select name="addr" id="addr">					    
                          		<option value="<%=request.getContextPath()%>/mgHandler.html?">mgHandler</option>
							</select>
						</td>
					  </tr>
					 
					  <tr>
					     <td nowrap align="right" width="20%">输入参数:</td>
					     
					     <td width="80%"><input name="input_param" size="80" id="input_param"  type="text" class="text"/></td>
					  </tr>
					  
					   <tr>
					     <td nowrap align="right" width="20%">输出:</td>
					     
					     <td width="80%"><span id="output_result"></span></td>
					  </tr>

					  </table>

				</fieldset>			</TD>
		</TR>
		
		</TABLE>
	
	
	 </td>
  </tr>

		<TR>
			<TD colspan="2" align="center" height="50px">
			<input type="button" name="Submit" value=" 提交 " class="button" onclick="fsubmit();"/>　
</TD>
		</TR>
		</TABLE>
	
	
	 </td>
  </tr>
  
  
  
  
  </table>

</div>
</form>

</body>
</html>