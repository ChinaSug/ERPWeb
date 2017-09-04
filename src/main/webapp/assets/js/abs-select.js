/**
 * 下拉表格选择框
 * @author Sug 2017-7-31
 */
var SelectGrid = function(option) {
	
	var options = {
		render: '', // 标签id
		url: '', // 服务器地址
		data: '', // 上传数据
		setField: false, // 是否将选择返回值设置到当前表单名称相同的属性的值
		selectHeight: '150px', // 默认选择框高度
		selectWidth: '', // 默认与input宽度一样

		items: [],
		/**
		 * 表格标题栏
		 * itemTitle : [{
		 *	title : '表头1',
		 *	dataIndex :'a',
		 *	width : '10%'
		 *}, {
		 *	title : '表头2',
		 *	dataIndex :'b',
		 *	width :'10%'
		 *}]
		 * @type {Array}
		 */
		itemTitle: [],
		itemKey: '', // 选中后返回的字段值
		itemIndex: 0, // 选中后返回第几个下标的字段值
		/**
		 * fieldReplace: [{
		 * 		itemName: 'value',
		 * 		fieldName: 'oid'
		 * }]
		 * 在setField时，将名为value的值，修改为设置到name为oid的标签中
		 */
		fieldReplace: [],
		setCaretDownIcon : true, // 是否添加下三角图标
		
		mustSelect: true, // 是否必须选择items中的一项 
		notSelectMsg: '请选择已有选项！', // 若没有选择已有items选项的提示信息
	};
	var grid = null;
	var picker = null;
	
	var SelectGrid = function(option) {
		return new SelectGrid.fn.init(option);
	}
	
	SelectGrid.fn = SelectGrid.prototype = {
		constructor: SelectGrid,
		init: function(option) {
			var _self = this;
			_self.options = $.extend(false, {}, options, option);
			var _op = _self.options;
			
			if (!isNullString(_op.url)) {
				_self.ajaxGetItems();
			} else {
				_self.renderUI();
			}
			
			if (_op.setCaretDownIcon) {
				_self.renderCaretDown();
			}
			
		},
		
		updateByUrl : function (url, _data) {
			var _self = this;
			_self.options.url = url;
			_self.options.data = _data;
			$.post(url, _data, function(data) {
				_self.updateByItem(data);
			});
		},
		
		updateByItem : function(items) {
			var _self = this;
			if(typeof items == "string") {
				if(items.indexOf("[") == -1) {
					items = "[" + items + "]";
				}
				items = JSON.parse(items)
			}
			_self.options.items = items;
			var _fRep = _self.options.fieldReplace; 
			var _items = items;
			if (isArray(_fRep) && _fRep.length > 0) {
				$.each(_fRep, function(i, n) {
					if (n.itemName == _self.options.itemKey) {
						_self.options.itemKey = n.fieldName;
					}
					
					$.each(_items, function(j, m) {
						var __val = m[n.itemName];
						delete m[n.itemName];
						m[n.fieldName] = __val;
					});
				});
			}
			_self.grid.set('items', _items);
		},
		
		// 渲染选择器
		renderUI : function() {
			var _self = this;
			var options = this.options;
			var $input = $("#" + options.render);
			var _items = options.items;
			options.itemsKey = $input.attr("name");
			
			// 是否替换属性
			var _fRep = options.fieldReplace; 
			if (isArray(_fRep) && _fRep.length > 0) {
				$.each(_fRep, function(i, n) {
					if (n.itemName == options.itemKey) {
						options.itemKey = n.fieldName;
					}
					
					$.each(_items, function(j, m) {
						var __val = m[n.itemName];
						delete m[n.itemName];
						m[n.fieldName] = __val;
					});
				});
			}
			var _selectWidth = isNullString(options.selectWidth) ? $("#" + options.render)[0].offsetWidth : options.selectWidth; 
			
			BUI.use(['bui/picker', 'bui/grid'], function(Picker, Grid) {
				grid = new Grid.SimpleGrid({
					idField : options.itemKey,
					columns : options.itemTitle,
					textGetter : function(item) {
						return item[options.itemKey];
					},
					elStyle : {
						'max-height': options.selectHeight,
						'overflow': 'auto',
					},
					items : _items
				});
				picker = new Picker.ListPicker({
					trigger : "#" + options.render,
					id : options.render + "_picker",
					width : _selectWidth, //指定宽度
					children : [grid] //配置picker内的列表
				});
				picker.render();
				_self.picker = picker;
				_self.grid = grid;

				grid.on('itemclick', function(e) {
					var _item = e.item;
					if (options.setField) {
						_self.setFormField(_item[options.itemKey]);
					}
				})
				
				if (options.mustSelect) {
					var buiField = new BUI.Form.Field({
						srcNode: "#" + $input.attr("id"),
					}).render();
					
					var ruleName = $input.attr("id") + "-rule";
					
					BUI.Form.Rules.add({
				        name : ruleName,  //规则名称
				        msg : options.notSelectMsg,//默认显示的错误信息
				        validator : function(value, baseValue, formatMsg){
				        	$input.css("border", "");
				        	if (value == '') {
				        		return '';
				        	}
				        	
				        	var flag = false;
				        	$.each(options.items, function(i, n) {
								if (n[options.itemKey] == value) {
									flag = true;
								}
							});
				        	
				        	if (!flag) {
				        		$input.css("border", "1px dotted red")
				        		return formatMsg;
				        	} else {
				        		return '';
				        	}
				        }
					}); 
					
					var rules = buiField.get('rules');
					for (var name in rules) {
						buiField.removeRule(name);
					}
					buiField.addRule(ruleName);
				}
				
				$input.on('keyup', function() {
					var queryLowerCase = $input.val();
					var suggestions = $.grep(_items, function(obj) {
						var _has = false;
						$.each(options.itemTitle, function(j, o) {
							var _str = obj[o.dataIndex];
							if (!isNullString(_str) && _str.indexOf(queryLowerCase) !== -1) {
								_has = true;
							}
						});
						if (_has) {
							return obj;
						}
					});
					grid.set("items", suggestions);
					picker.show();
					
					if (options.setField) {
						_self.setFormField(queryLowerCase);
					}
				})
			});
		},
		
		/**
		 * 通过Ajax方式获取options.url的数据并设置到选择框中
		 */
		ajaxGetItems : function() {
			var _self = this;
			var options = _self.options;
			$.post(options.url, options.data, function(data) {
				if(typeof data == "string") {
					if(data.indexOf("[") == -1) {
						data = "[" + data + "]";
					}
					data = JSON.parse(data)
				}
				options.items = data;
				_self.renderUI();
			});
		},
		
		// 设置下三角图标
		renderCaretDown : function() {
			var options = this.options;
			var $input = $("#" + options.render);
			var caretId = options.render + '_caret'; 
			
			$input.after('<span id="' + caretId + '" style="margin-left: -15px;position: absolute;"><span class="x-caret x-caret-down"></span></span>');
			$("#" + caretId).live('click', function() {
				$input.click();
			});
		},
		
		/**
		 * 设置表单中与items中数据同名的数据值
		 */
		setFormField : function(val) {
			var options = this.options;
			var $input = $("#" + options.render);
			var _find = false;

			$.each(options.items, function(i, n){
				if (val == n[options.itemKey]) {
					BUI.FormHelper.setFields($input.parents("form")[0], n);
					$input.change();
					_find = true;
				}
			});
			
			if (!_find) {
				var obj = $.extend(true, {}, options.items[0]);
				$.each(obj, function(i, n) {
					obj[i] = null;
					if (i == $input.attr("name")) {
						obj[i] = $input.val();
					}
				})
				BUI.FormHelper.setFields($input.parents("form")[0], obj);
			}
		}
	}
	
	function isNullString(variable1) {
		if (typeof variable1 == 'string' && variable1 != '') {
			return false;
		}
		return true;
	}

	var isArray = function(object) {
		return object && typeof object === 'object' &&
			typeof object.length === 'number' &&
			typeof object.splice === 'function' &&
			//判断length属性是否是可枚举的 对于数组 将得到false  
			!(object.propertyIsEnumerable('length'));
	};
	
	SelectGrid.fn.init.prototype = SelectGrid.fn;

	return new SelectGrid(option);
};