/**
 * 表单操作工具
 * 
 * @type {Object}
 */
var etone = {
	/**
	 * 页面视图，用于存储页面临时信息，并统一入口
	 * 
	 * @type {Object}
	 */
	view : {},
	/**
	 * 默认mainForm作为提交操作表单
	 * 
	 * @type {String}
	 */
	defaultForm : '#mainForm',
	/**
	 * 默认编辑或修改表单名称
	 * 
	 * @type String
	 */
	inputForm : '#inputForm',
	/**
	 * 分页转向
	 * 
	 * @param {Number}
	 *            pageNo
	 */
	jumpPage : function(pageNo) {
		$("#pageNo").val(pageNo);
		//alert($("#pageNo").attr('id'));
		$(this.defaultForm).submit();
	},
	/**
	 * 设置表格排序
	 * 
	 * @param {String}
	 *            orderBy
	 * @param {String}
	 *            defaultOrder
	 */
	sort : function(orderBy, defaultOrder) {
		if ($("#orderBy").val() == orderBy) {
			if ($("#order").val() == "") {
				$("#order").val(defaultOrder);
			} else if ($("#order").val() == "desc") {
				$("#order").val("asc");
			} else if ($("#order").val() == "asc") {
				$("#order").val("desc");
			}
		} else {
			$("#orderBy").val(orderBy);
			$("#order").val(defaultOrder);
		}

		$(this.defaultForm).submit();
	},
	/**
	 * 查询函数，并初始化页面变量
	 */
	search : function() {
		$("#order").val("");
		$("#orderBy").val("");
		$("#pageNo").val("1");

		$(this.defaultForm).submit();
	},
	modify : function(url) {
		if (url == null)
			return;
		var chkGroup = $("input[@name='nmIds[]']:checked");
		if (chkGroup.length == 1) {
			var str  = $(chkGroup[0]).val();
			var ulindex = str.indexOf('_');
			if(ulindex==-1)
				return;
			var id = str.substring(0,ulindex);
			var redir = url + '?id=' + id;
			location.href = redir;
		} else if (chkGroup.length == 0) {
			alert('请选择要编辑的记录!');
		} else {
			alert('每次只能编辑一条记录!');
		}
	},
	/**
	 * 删除选中记录
	 */
	remove : function(id,url) {
		var chkGroup = $("input[@name='nmIds[]']:checked");
		if(chkGroup.length>0){
		 	if (confirm('确实要删除选中记录吗？')) {
				var dForm = $(this.defaultForm);
				//alert(url);
				dForm.attr('action', url);
				dForm.submit();
			}
		}else{
			alert('请选择要删除的记录');
		}
		
	},
	
		/**
	 * 删除选中记录
	 */
	removeRow : function(id,url) {
		var chkGroup = $("input[@name='nmIds[]']:checked");
		if(chkGroup.length>0){
		 	if (confirm('确实要删除选中记录吗？')) {
				var dForm = $(this.defaultForm);
				//alert(url);
				dForm.attr('action', url);
				dForm.submit();
			}
		}else{
			alert('请选择要删除的记录');
		}
		
	},
	
	/**
	 * Ajax请求
	 * 
	 * @param {String}
	 *            url 请求地址
	 * @param {String}
	 *            param 查询参数
	 * @param {Object/Function}
	 *            callback 回调函数，由调用者传入
	 */
	postRequest : function(url, param, callback) {
		$.ajax({
					type : "POST",
					url : url,
					data : param,
					dataType : 'json',
					success : callback
				});
	},
	/**
	 * Ajax请求同步方式
	 * 
	 * @param {String}
	 *            url 请求地址
	 * @param {String}
	 *            param 查询参数
	 * @param {Object/Function}
	 *            callback 回调函数，由调用者传入
	 */
	postSyncRequest : function(url, param, callback) {
		$.ajax({
					type : "POST",
					url : url,
					data : param,
					async: false,
					dataType : 'json',
					success : callback
				});
	},
	/**
	 * 打开对话框
	 * 
	 * @param {String}
	 *            url 转向网页的地址
	 * @param {String}
	 *            name 网页名称，可为空
	 * @param {Number}
	 *            iWidth 弹出窗口的宽度
	 * @param {Number}
	 *            iHeight 弹出窗口的高度
	 */
	openDialog : function(url, name, iWidth, iHeight) {
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
		window.open(url, name, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft+ ',scrollbars=yes,resizeable=yes,location=no,status=no' );
	},
	/**
	
	openDialog : function(url, name, iWidth, iHeight) {
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
		window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft+ ',toolbar=yes,menubar=yes,scrollbars=yes,resizeable=yes,location=no,status=no' );
	},
	**/
	/**
	 * 渲染表格样式，有时间可以再简化一下
	 * 
	 * @param {String}
	 *            id #targetTable
	 */
	renderTable : function(id) {
		var selecttionColor = '#FFCC33';
		var highlightColor = '#c1ebff';
		var recordCheck = $(id + ' tr:gt(0) :checkbox');
		var rowCheck = $(id + ' tr:gt(0) td');
		// 处理审鼠标高亮事件
		$(id + ' tr:gt(0)').hover(function(e) {
					$(this).children('td').css('background-color', highlightColor);
				}, function(e) {
					var restoreColor = $(this).data('restoreColor') || '';
					$(this).children('td').css('background-color', restoreColor);
				});
		// 表头的check事件
		$(id + ' tr:first :checkbox').click(function(e) {
					recordCheck.attr('checked', $(e.target).attr('checked'));
					var restoreColor = $(e.target).attr('checked') ? selecttionColor : '';
					var rowTr = recordCheck.closest('tr');
					rowTr.children('td').css('background-color', restoreColor);
					rowTr.data('restoreColor', restoreColor);
				});
		// 行单击事件
		$(id + ' tr:gt(0)').click(function(e) {
					var rowTr = $(this);
					var rowTds = rowTr.children('td');
					var firstTd = rowTds.eq(0);
					var singleCheck = firstTd.find(':checkbox');
					if ($(e.target).attr('type') != 'checkbox') {
						singleCheck.attr('checked', !singleCheck.attr('checked'));
					}
					rowTds.css('background-color', singleCheck.attr('checked') ? selecttionColor : '');
					rowTr.data('restoreColor', rowTds.css('background-color'));
				});
		// 附加checkbox
		$('#checkRecords').click(function(e) {
					$(id + ' tr:first :checkbox').attr('checked', $(e.target).attr('checked'));
					recordCheck.attr('checked', $(e.target).attr('checked'));
					var restoreColor = $(e.target).attr('checked') ? selecttionColor : '';
					var rowTr = recordCheck.closest('tr');
					rowTr.children('td').css('background-color', restoreColor);
					rowTr.data('restoreColor', restoreColor);
				});
	}
};
