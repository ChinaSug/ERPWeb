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