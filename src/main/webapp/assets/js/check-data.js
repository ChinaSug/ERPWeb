// @Sug 2017-8-22 in ERPWeb project

var erpHVersion = getVersion();

function getVersion() {
	var d = new Date();
	return d.getFullYear() + "" + (d.getMonth()+1)  + ""
	+ d.getDate() + "" + d.getHours() + "" + d.getMinutes()
	+ "" + d.getSeconds();
}

function getLikaVersion(model) {
	var d = new Date();
	var year = patchZone((d.getFullYear() + "").slice(2, 4));
	var month = patchZone(d.getMonth() + 1);
	var dat = patchZone(d.getDate());
	var hours = patchZone(d.getHours());
	var minu = patchZone(d.getMinutes());
	var sec = patchZone(d.getSeconds());
	
	model = model != null ? model : "";
	return "LIKA-" + model + year + "" + month + "" + dat
		+ "" + hours + "" + minu + "" + sec + ""
		+ getRandom();
}

function patchZone(val) {
	return val < 10 ? "0" + val : val;
}

function getRandom() {
	var word = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	return word.charAt(Math.random()*word.length);
}

var $enterObj = $("input,select");
$enterObj.live('keypress', function (e) {
	// 当按下回车时默认跳转到下一个选项，而不是提交表单
    if (e.which == 13) {
       	$enterObj.eq($enterObj.index(this)+1).focus(); 
        return false;
    }
});

//清除验证缓存
function clearCheckCache(buiField) {
	if (buiField != null) {
		buiField.render();
		buiField.fire("clearCache");
	   	buiField.set('cacheMap',{});
	}
}

// 设置editor位置和清除buiField验证缓存
function setEditorAndClearCache(editing, buiField) {
	if (editing != null) {
		editing.on('editorshow', function(ev) {
			var edt = editing.get('editor').render();
			edt.set("y", 140);
			clearCheckCache(buiField);
		});
	}
}

// 在form表提交前先检查数据是否正确
function checkBeforeSubmit($form) {
	if ($form != null && "FORM" == $form[0].tagName) {
		var xFieldError = $form.find(".x-field-error");
		var validText = $form.find(".valid-text");
		
		if (xFieldError.length == 0 && validText.length == 0) {
			return true;
		}
	}
	return false;
}

// 设置字段远程验证
function setFieldRemote(buiForm, fieldName, className) {
	var nameField = buiForm.getField(fieldName);
	
	nameField.set('remote', {
		url: "/ERPWeb/mgHandler.html?op_action=IS_EXIST",
		dataType: 'json', //默认为字符串
		callback: function(data) {
			if(data != null) {
				if(data.success) {
					return '';
				} else {
					return data.errors[fieldName];
				}
			}
		}
	});
	
	$(nameField.get("srcNode")[0]).on('blur', function(ev) {
		clearCheckCache(nameField);
	})
	
	nameField.on('remotestart', function(ev) {
		var data = ev.data;
		var oidField = buiForm.getField("oid");
		nameField = buiForm.getField(fieldName);
		
		if (oidField != null) {
			var oidTag = oidField.get("srcNode")[0];
			if (oidTag != null) {
				data.oid = oidTag.value;
			}
		}
		data.class = className;
		data.field = fieldName;
		data.value = nameField.get('value');
	});
}

// 设置editing提交验证与提交数据后清除验证缓存
function setEditingAcceptMethod(editing) {
	var editor = editing.get('editor');
	editor.set('success', function() {
		var _form = editing.get('form').render();
		if (_form.isValid() && checkBeforeSubmit($("#" + _form.get("id")))) {
			editor.accept();
		}
	});
}

