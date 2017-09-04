<!DOCTYPE HTML>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../assets/css/dpl-min.css"
	rel="stylesheet" type="text/css" />
<link href="../assets/css/bui-min.css"
	rel="stylesheet" type="text/css" />
<link href="../assets/css/page-min.css"
	rel="stylesheet" type="text/css" />
	
<style type="text/css">
	.control-text{
		width: 300px !importment; 
	}
</style>
	
</head>
<body>
<div class="container">
	<div class="row">
		<div class="doc-content">
			<form name="delForm" id="delForm" method="post"
				action="/mgHandler.html?op_action=HOUSE_DELETE">
				<ul class="panel-content form-info-ul">
					<li>
						<label class="control-label">物业编号：</label>
						<input class="control-text" type="text" name="searchCode"/>
						<button type="button" class="button button-primary" id="searchBtn">
							<i class="icon-white icon-search"></i>查询
						</button>
					</li>
				</ul>
				<table cellspacing="0" class="table table-bordered">
					<thead>
						<tr>
							<th width="50px"><label class="checkbox"><input type="checkbox" onClick="selectAll(this);">全选</label></th>
							<th>物业编号</th>
							<th>物业名称</th>
							<th>所属公司</th>
							<th>区域</th>
							<th>面积（平方米）</th>
							<th>建造日期</th>
							<th>产权归属</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</form>
			<div>
				<ul class="toolbar pull-left">
					<li>
						<button class="button button-success" onClick="redirectToAddPage();">
							<i class="icon-white icon-plus"></i>添加
						</button>
					</li>
					<li>
						<button class="button button-danger" onClick="deleteObj();">
							<i class="icon-white icon-trash"></i>删除
						</button>
					</li>
				</ul>
				<jsp:include page="paging.jsp" flush="false" />
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="../assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="../assets/js/bui-min.js"></script>
<script type="text/javascript" src="../assets/js/sea.js"></script>
<script type="text/javascript" src="../assets/js/config-min.js"></script>
<script type="text/javascript" src="../assets/js/abs-paging.js"></script>
<script type="text/javascript">

$("#searchBtn").click(function() {
	document.getElementById("delForm").action = '/mgHandler.html?op_action=HOUSE_LIST';
	document.getElementById("delForm").submit();
});

function redirectToAddPage() {
	 window.location.href = '/mgHandler.html?op_action=HOUSE_ADD';
}


</script>

</body>
</html>
