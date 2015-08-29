<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<head>
<title>本地号码筛选</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/maincontent.css" />

<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
<script language="javascript" type="text/javascript"
	src="/tools/datepicker/WdatePicker.js"></script>
<!-- 列表工具栏 -->
<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
	type="text/css"></link>
<script type="text/javascript" src="/js/jquery/extend.tab/js/Toolbar.js"></script>
<script type="text/javascript" src="/js/common_grid.js"></script>
<script src="/js/nav.js"></script>
</head>
<body>
	<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
		bgcolor="#D3E0F2" width="100%" height="100%">
		<tr>
			<td colspan="2" height="5px"></td>
		</tr>

		<tr>
			<td>
				<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
					style="width: 100%;">
					<tr>
						<td>
							<table cellspacing="0" cellpadding="0" border="0" width="100%"
								class="menubar">
								<tr valign="top">
									<td width="4px" height="24px">
										<div class="lefttitle"></div>
									</td>
									<td width="100%" height="24px">
										<div class="middletitle">本地号码数据</div>
									</td>
									<td width="4px">
										<div class="righttitle"></div>
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td>
							<div id="toolbar" style="height: 25px"></div>
							<div class="gt-panel"
								style="width: 100%; margin: 0px; margin-bottom: 2px;">
								<div id="data_container"></div>
							</div>
						</td>
					</tr>

				</table>
			</td>
			<td width="2px"></td>
		</tr>
		<tr>
			<td colspan="2" height="5px"></td>
		</tr>
	</table>
</body>
</html>
<script type="text/javascript">
	var nmMsisdn = '';

	var dsConfig = {
		//data : data1 ,
		uniqueField : 'id',
		fields : [ {
			name : 'nmMsisdn'
		} ]
	};

	var colsConfig = [ {
		id : 'chk',
		isCheckColumn : true,
		checkValue : false,
		frozen : false,
		filterable : true,
		header : "",
		exportable : false
	}, {
		id : 'nmMsisdn',
		header : "深圳本地号码",
		headAlign : 'center',
		sortable : false,
		align : 'right'
	// 		exportnumber:true
	}

	];

	var gridConfig = {
		id : "datagrid",
		loadURL : '/localUserMsisdnCheck_query.do?1=1',
		exportURL : '/localUserMsisdnCheck_export.do?1=1',
		dataset : dsConfig,
		columns : colsConfig,
		width : 780,
		height : 500,
		resizable : false,
		showGridMenu : false,
		allowCustomSkin : false,
		allowFreeze : false,
		allowGroup : false,
		allowHide : false,
		container : 'data_container',
		beforeLoad : checkLogon,
		pageSize : getDispalyPageSize(0, 1),
		remoteSort : true,
		pageSizeList : [ 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
				23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
				39, 40, 45, 50, 100 ],
		toolbarContent : 'nav | goto | pagesize | state',
		onComplete : loadComplate
	};

	function getLongPageSize() {
		var size = getDispalyPageSize(1, 1);
		if (size < 25) {
			size = 25;
		}
		return size;
	}
	var datagrid = new GT.Grid(gridConfig);
	GT.Utils.onLoad(function() {
		datagrid.render();
	});

	var firstInit = true;
	//grid回调函数
	function loadComplate() {
		//grid列表初始化信息
		var obj = new Object();//使用对象传参,以防以后修改
		obj.datagrid = datagrid;
		obj.hideColumn = 0;//隐藏的列数目
		obj.isCheckbox = true;//是否可选择
		obj.otherHeight = 0; //必须定义,因为有些页面不同，所以此处指定预留高度

		//判断是否查询到数据
		judgeData(datagrid);
		//初始化grid
		initGridInfo(obj);

		if (firstInit == true) {
			var pageSize = getDispalyPageSize(0, 1);
			var index = 0;
			$(".gt-pagesize-select option").each(function(i) {
				if (this.text == pageSize) {
					index = i;
				}
			})
			firstInit = false;
			$(".gt-pagesize-select").get(0).selectedIndex = index;
		}

	}

	//grid查询
	function query() {
		nmMsisdn = 'test';
		// 	if(startTime_search=='' || endTime_search=='' || startTime_search==null || endTime_search==null){
		// 		alert('请选择时间跨度!');
		// 		return ;
		// 	}
		var param = {
			'test' : nmMsisdn
		};
		GT.$grid('datagrid').query(param);
	}
	
	$(document).ready(function() {
		clearSession();
	});

	/***************初始化toolbar***************************/
	$(document)
			.ready(
					function() {
						//按钮权限设置	  
						var toolbar = new Toolbar(
								{
									renderTo : 'toolbar',
									//border: 'top',
									items : [
											{
												type : 'button',
												text : '批量过滤本地号码',
												bodyStyle : 'new',
												useable : 'T',
												handler : function() {
													var mm = showMMDialog(
															'/sysdata/userBelong/number_up.jsp',
															'号码上传', '540px',
															'300px');
													if (typeof (mm) != "undefined") {
														var flag = window
																.confirm(mm);
														if (flag) {
															// 											alert("正在更新号码……，请稍后一段时间再查看！")
															query();
														} else {//清空session的数据
															// 															clearSession();
														}
													}

												}
											}, '-', {
												type : 'button',
												text : '导出本地号码',
												bodyStyle : 'xls',
												useable : 'T',
												handler : function() {
													//参数
													var params = {
														nmMsisdn : 'test'
													//结束时间
													};
													//导出
													var fileObj = new Object();
													fileObj.fileName = '本地号码';//('+startTime_search+'至'+endTime_search+')';//文件名称
													fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
													fileObj.gridObj = datagrid;//当前grid对象
													fileObj.params = params;
													exportFile(fileObj);
												}
											} ]
								});
						toolbar.render();
					});

	function clearSession() {
		$.ajax({
			type : "post",
			url : "/localUserMsisdnCheck_clearSession.do",
			data : {},
			success : function() {

			},
			error : function() {

			}
		});
	}

	/**
	 脚本不出错
	 */
	$(document).ready(function() {
		window.onerror = killErrors;
	})
	function killErrors() {
		return true;
	}
</script>



