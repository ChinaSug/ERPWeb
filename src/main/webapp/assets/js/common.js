// @By Sug 2017-8-15

function onlyNum(event) {
	if(!(event.keyCode == 46) && !(event.keyCode == 8) && !(event.keyCode == 37) && !(event.keyCode == 39)) {
		if(!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105))) {
			event.returnValue = false;
			event.preventDefault();
		}
	}
}

// 只允许按删除键
function onlyDelete(event) {
	if(!(event.keyCode == 8) && !(event.keyCode == 46)) {
		event.returnValue = false;
		event.preventDefault();
	} else {
		event.target.value = "";
		event.target.innerText = "";
	}
}

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


function isChildOf(child, parent) {
    var parentNode;
    if(child && parent) {
        parentNode = child.parentNode;
        while(parentNode) {
            if(parent === parentNode) {
                return true;
            }
            parentNode = parentNode.parentNode;
        }
    }
    return false;
}

function selectImg(node) {
	var imgFile = node.nextElementSibling;
	if ("file" == imgFile.type) {
		imgFile.click();
	}
}


$(".img-file-token").change(function() {
	var $divImg = $(this).parents(".div-img").eq(0);
	var $divClose = $divImg.find(".div-close").eq(0);
	var $imgToken = $(this).siblings(".img-token").eq(0);
	
	$divClose.find("img").eq(0).attr("src", getFileUrl(this.id));
	$divClose.show();
	$imgToken.val("");
})

// 取消图片按钮
$(".close-background").click(function() {
	var $this = $(this);
	
	$this.parent().hide();
	$this.parents("ul").find(":file").eq(0).attr("value", "");
	$this.parents("ul").find(".img-token").eq(0).attr("value", "");
	$this.parents(".div-img").find(".div-img-forward").attr("value", "");
	$this.parents(".div-img").find(".div-img-forward").hide();
	$this.siblings(".img-token").eq(0).val("");
});

var $enterObj = $("input,select");
$enterObj.live('keypress', function (e) {
	// 当按下回车时默认跳转到下一个选项，而不是提交表单
    if (e.which == 13) {
       	$enterObj.eq($enterObj.index(this)+1).focus(); 
        return false;
    }
});

//限制文件上传大小
function fileSizeLimit(target, maxMB) {
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;         
	if (isIE && !target.files) {     
		var filePath = target.value;     
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
		var file = fileSystem.GetFile (filePath);     
		fileSize = file.Size;    
	} else {    
		fileSize = target.files[0].size;     
	}
	
	var size = fileSize;
	var _maxSize = maxMB * 1024 * 1024;
	if(size > _maxSize){
		target.value = "";
		return false;
	}
	return true;
}

/**
 * 返回上一页
 */
function returnPrevPage() {
	// location.href = document.referrer;
	history.go(-1);
}

// 将数字转成带千位分隔符数字
function convertToFinance(num) {
	if (/^[+-]?[0-9]+(\.[0-9]*)?$/.test(num)) {
		return parseFloat(parseFloat(num).toFixed(2)).toLocaleString();
	} else if (/^-?\d+(,\d{3})*(\.\d*)?$/.test(num)) {
		return parseFloat(num).toFixed(2);
	} else {
		return 0;
	}
}

/**
 * 将num转换为保留两位小数的数字，若小数点后两个都是0就转为整数返回
 * 
 * @param num
 * @returns
 */
function keep2Decimal(num) {
	num = $.trim(num);
	if (/^-?\d+(\,\d{3})*(\.\d*)?$/.test(num)) {
		num = num.toString().replace(/,/g, "");
		num = parseFloat(num).toFixed(2);
		return parseFloat(num);
	} else {
		return 0;
	}
}

function keep4Decimal(num) {
	return parseDecimal(num, 4);
}

/**
 * 将num转换为保留小数的数字，若小数点后两个都是0就转为整数返回
 * 
 * @param num
 * @returns
 */
function parseDecimal(num, decimal) {
	num = $.trim(num);
	if (/^-?\d+(\,\d{3})*(\.\d*)?$/.test(num)) {
		var _dec = decimal > 0 ? decimal : 0;
		num = num.toString().replace(/,/g, "");
		num = parseFloat(num).toFixed(_dec);
		
		return parseFloat(num);
	} else {
		return 0;
	}
}

$(".financeFormat").blur(function() {
	$(this).val(convertToFinance($(this).val()));
})
$(".financeFormat").focus(function() {
	var _val = $(this).val();
	if (/^-?\d+(\,\d{3})*(\.\d*)?$/.test(_val)) {
		_val = _val.replace(/,/g, "");
		$(this).val(_val);
	}
})

window.onload = function () {
	$.each($(".financeFormat"), function(i, n) {
		$(this).blur();
	});
}

//下拉选择框下三角图片提示点击事件
$(".caret-down-div").click(function() {
	var $this = $(this);
	$this.siblings(".caret-down-input").focus();
	$this.siblings(".caret-down-input").click();
});

/**
 * 比较两个日期相差天数，接收两个日期字符串
 */
var dayMilli = 24 * 60 * 60 *1000;
var yearMilli = 365 * dayMilli;
function dateDiffDay(start, end){
	try{  
		var dateArr = start.split("-");
		var checkDate = new Date();
			checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);
		var checkTime = checkDate.getTime();
		
		var dateArr2 = end.split("-");
		var checkDate2 = new Date();
			checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);
		var checkTime2 = checkDate2.getTime();

		var cha = (checkTime2 - checkTime) / dayMilli; 
		return cha;
	} catch(e) {
		return false;
	}
}

/**
 * 判断字符是否为空或空字符串
 * 
 * @param variable1
 * @returns {Boolean}
 */
function isNullStr(variable1) {
	if (typeof variable1 == 'string' && variable1 != '') {
		return false;
	}
	return true;
}

/**
 * 判断字符是否为2017-01-01类型日期
 * 
 * @param year
 * @returns {Boolean}
 */
function isDateFormat(year) {
	if (/^\d{4}-\d{2}-\d{2}$/.test(year)) {
		return true;
	}
	return false;
}

/**
 * 判断年份是否为闰年
 * 
 * @param year
 * @returns {Boolean}
 */
function isLeapYear(year) {  
	return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);  
}

/**
 * 合计表格列数值
 * @param tableId 表格Id
 * @param colIndex 第几列
 * @param filterStr 要过滤的字符
 */
function amountTableCol(tableId, colIndex, filterStr) {
	var totalAmount = 0;
	var tab = document.getElementById(tableId);
	$(tab).find("tr").each(function() {
		$(this).find('td:eq(' + (colIndex-1) + ')').each(function() {
			var _text = $(this).text();
			if (filterStr) {
				var re = new RegExp(filterStr, "g");
				_text = _text.replace(re, "");
			}
            totalAmount += keep2Decimal(_text);  
        });
	})
	return totalAmount;
}

/**
 * 合并table相同的列
 *
 * @param tableId 表格Id
 * @param startCol 开始列
 * @param endCol 结束列
 */
function mergeTableTd(tableId, startCol, endCol) {
	var tab = document.getElementById(tableId);
	if (tab == null) {
		return;
	}
	var val, count, start;  
	var maxCol = endCol; //maxCol：合并单元格作用到多少列
	for(var col = maxCol-1; col >= startCol ; col--) {
		count = 1;
		val = "";
		for(var i=0; i<tab.rows.length; i++) {
			if(val == tab.rows[i].cells[col].innerHTML) {
				count++;
			} else {
				if (count > 1) {
				//合并
					start = i - count;
					tab.rows[start].cells[col].rowSpan = count;
					for(var j=start+1; j<i; j++) {
						tab.rows[j].cells[col].style.display = "none";
					}
					count = 1;
				}
				val = tab.rows[i].cells[col].innerHTML;
			}
		}

		if(count > 1 ) {
			//合并，最后几行相同的情况下
			start = i - count;
			tab.rows[start].cells[col].rowSpan = count;
			for(var j=start+1; j<i; j++) {
				tab.rows[j].cells[col].style.display = "none";
			}
		}
	}
}

function getCurDate() {
	var d = new Date();
	var month = d.getMonth() + 1;
	month = month < 10 ? "0" + month : month;
	var day = d.getDate();
	day = day < 10 ? "0" + day : day;
	return d.getFullYear() + "-"  + month + "-" + day;
}

function convertParamToObj(url) {
	var paramObj = {};
	var arr = url.substr(url.indexOf('?') + 1).split('&');
	arr.forEach(function(item) {
		var tmp = item.split('=');
		paramObj[tmp[0]] = tmp[1];
	})
	return paramObj;
}

