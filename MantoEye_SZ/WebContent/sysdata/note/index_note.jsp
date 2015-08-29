<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<head>
<title>首页公告栏信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/maincontent.css" />
<script src="/js/nav.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
<!-- 列表工具栏 -->
<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
	type="text/css"></link>
<script type="text/javascript" src="/js/jquery/extend.tab/js/Toolbar.js"></script>
<script type="text/javascript" src="/js/common_grid.js"></script>
<style type="text/css">
html,body {
	padding: 0px;
	margin: 0px;
	background-color: #D3E0F2;
}

#mainContent {
	width: 100%;
	height: 100%;
}

#mainTitle {
	margin-top: 5px;
	width: 100%;
	height: 27px;
}

#titleLeft {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/lefttitle.gif);
	background-repeat: no-repeat;
	height: 27px;
	width: 3px;
	float: left;
}

#titleCenter {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/middletitle.gif);
	background-repeat: repeat-x;
	float: left;
	width: 99.3%;
	height: 27px;
	color: white;
}

#titleRight {
	background-image:
		url(/skin/Default/images/MantoEye/maincontent/righttitle.gif);
	background-repeat: no-repeat;
	height: 27px;
	width: 3px;
	float: right;
}

.title_content {
	padding-left: 6px;
	line-height: 2.2em;
	font-weight: bold;
}
</style>
</head>
<body>
<div id="mainContent">
<div id="mainTitle">
<div id="titleLeft"></div>
<div id="titleCenter"><span class="title_content">首页公告栏信息</span></div>
<div id="titleRight"></div>
<div id="toolbar" style="height: 25px"></div>
<div class="gt-panel" id="panel_data"
	style="width: 100%; margin: 0px; margin-bottom: 2px;">
<div id="data_container"></div>
</div>
</div>
</div>
</body>
</html>

<script type="text/javascript">
var dsConfig = {
		//data : data1 ,
	    uniqueField: 'nmNoteId',
	    fields: [{
	        name: 'vcTitle'
	    }, {
	        name: 'vcContent'
	    }, {
	        name: 'dtDate'
	    }]
	};

	var colsConfig = [{
	    id: 'chk',
	    isCheckColumn: true,
	    checkType: 'checkbox',
	    checkValue: false,
	    frozen: false,
	    filterable: true,
	    header: "",
	    title: "全选",
	    exportable: false
	}, {
	    id: 'nmNoteId',
	    header: "公告信息ID",
	    hidden: true
	}, {
	    id: 'vcTitle',
	    header: "公告信息标题",
	    sortable: true,
	    renderer: renderTitle
	}, {
	    id: 'vcContent',
	    header: "公告信息内容",
	    hidden: true
	}, {
	    id: 'dtDate',
	    header: "记录时间",
	    sortable: true
	}, {
	    id: 'detail',
	    header: "操作",
	    sortable: false,
	    exportable:false,
	    renderer: renderDetail
	}];

	function renderDetail(value, record, columnObj, grid, colNo, rowNo){
	    return '<div id="mainbtn" style="padding-left: 30px;"><ul>' 
			+'<li><a href="javascript:deleteNote_('+record.nmNoteId+');"><span>删除</span></a></li>'
			+'<li><a href="javascript:edit('+record.nmNoteId+');"><span>编辑</span></a></li></ul></div>';
	}

	function renderTitle(value, record, columnObj, grid, colNo, rowNo){
	    return '<span title="' + record.vcContent + '">' + record.vcTitle +
	    '</span>';
	}

	var gridConfig = {
	    id: "datagrid",
	    loadURL : '/indexNote_query.do?1=1',
	    exportURL: '/indexNote_export.do?1=1',
	    dataset: dsConfig,
	    columns: colsConfig,
	    width: 780,
	    height: 500,
	    resizable: false,
	    container: 'data_container',
	    beforeLoad: checkLogon,
	    pageSize: getDispalyPageSize(0, 2),
	    remoteSort : true ,
	    pageSizeList: [10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 45, 50, 100],
	    toolbarContent: 'nav | goto | pagesize | state',
	    onComplete: loadComplate
	};

	var datagrid = new GT.Grid(gridConfig);
	GT.Utils.onLoad(function(){
	    datagrid.render();
	});

	function loadComplate(){
	    var obj = new Object();
	    obj.datagrid = datagrid;
	    obj.isCheckbox = true;
	    obj.otherHeight = 0;
	    obj.hideColumn = 2;
	    
	    judgeData(datagrid);

	    //initGridInfo(obj);
	    initGridInfo2(datagrid);
	}

	function initGridInfo2(datagrid) {
		var width = window.screen.availWidth-224; 
		width = width - 10;
		//var cs = datagrid.columns.length;
		var cs = 2;
		
		var totalRecords = datagrid.getAllRows().length;
		var dWidth = (totalRecords) * 22 + 70;
		var parentHeight = parent.document.getElementById("container_content").offsetHeight;//整个编辑区的高度
		var yy = window.screen.availHeight;
		var yyy = window.screenTop;
		var otherHeight = 0;
		var parentHeight = yy - yyy;
		var $toolbarObj = $("#toolbar");//toolbar对象
		var tHeight = $toolbarObj.offset().top + 25;//toolbar相对位置
		var IEHeight = 32;
		if (otherHeight != undefined) {
			IEHeight = IEHeight - otherHeight;
		}
		var totalRecords = datagrid.getAllRows().length;
		var dWidth = (totalRecords) * 22 + 55;
		if (checkIE() == "IE8") {
			dWidth = (totalRecords) * 22 + 70;
		}
		if (width == 1024) {
			var width_1024 = parentHeight - tHeight - IEHeight - 3;
			datagrid.setSize(800, dWidth > width_1024 ? dWidth : width_1024);//设置列表的宽高
			$toolbarObj.css("width", "800px");//设置列表toolbar的宽高
			realWidth = 800;
		} else {
			var width_1280 = parentHeight - tHeight - IEHeight;//满屏高度
			datagrid.setSize(1056, dWidth > width_1280 ? dWidth : width_1280);
			$toolbarObj.css("width", "1056px");
			realWidth = 1056;
		}

		width = width - 380;
		cs = 1;
		var w = parseInt(width / cs);
		for (var j in datagrid.columnMap) {
			//nmNoteId,vcTitle,dtDate,vcContent
			 if( 'chk' == j){
				// datagrid.getColumn(j).setWidth(3);
			 }else if( 'detail' == j){
				 datagrid.getColumn(j).setWidth(220);
			 }else if( 'dtDate' == j){
				 datagrid.getColumn(j).setWidth(120);
			 }else{
				 datagrid.getColumn(j).setWidth(w);
			 }	
		}
	}

	function query(){
		var param={};
		GT.$grid('datagrid').query( param );
	}
	
	$(document).ready(function(){
	    var toolbar = new Toolbar({
	        renderTo: 'toolbar',
	        items: [{
	            type: 'button',
	            text: '添加',
	            bodyStyle: 'new',
	            useable: 'T',
	            handler: function(){
	                var v = showMMDialog('edit.jsp', '', '600px', '380px');
	                reloadData(v);
	            }
	        }, '-', {
	            type: 'button',
	            text: '编辑',
	            bodyStyle: 'edit',
	            useable: 'T',
	            handler: function(){
	                var count = getSelectedCount('datagrid');
	                if (count > 1) {
	                    alert("只能选择一条记录！");
	                }else{
	                	if (count < 1) {
	                        alert("请选择一条记录！");
	                    }else {
	                        var id = getSelectedId('datagrid');
	                        var v = showMMDialog('/indexNote_findById.do?ids=' + id, '', '600px', '380px');
	                        reloadData(v);
	                    }
		            } 
	            }
	        }, '-', {
	            type: 'button',
	            text: '删除',
	            bodyStyle: 'delete',
	            useable: 'T',
	            handler: deleteNote
	        }, '-', {
	            type: 'button',
	            text: '导出',
	            bodyStyle: 'xls',
	            useable: 'T',
	            handler: function(){
	                var fileObj = new Object();
	                fileObj.fileName = '首页公告栏信息';
	                fileObj.fileFormat = 'xls';
	                datagrid.getColumn("vcContent").show();
	                fileObj.gridObj = datagrid;
	                exportFile(fileObj);
	                datagrid.getColumn("vcContent").hide();
	            }
	        }]
	    });
	    toolbar.render();
	});

	function edit(id){
		var v = showMMDialog('/indexNote_findById.do?ids=' + id, '', '600px', '380px');
        reloadData(v);
	}

	function reloadData(v){
	    if (v == 'add' || v == 'edit') {
	        query();
	    }
	}

	function deleteNote(){
	    if (getSelectedCount('datagrid') < 1) {
	        alert('请选择需要删除的数据!');
	        return;
	    }
	    
	    var ids = getSelectedKeys('datagrid');
	    deleteNote_(ids);
	}

	function deleteNote_(ids){
		 var flag = window.confirm("确定删除吗?");
		    if (flag) {
		        $.ajax({
		            type: "post",
		            url: "indexNote_delete.do",
		            data: {
		                ids: ids
		            },
		            success: function(msg){
		                alert(msg);//打印删除是否成功信息
		                query();//刷新grid数据
		            },
		            error: function(){
		                alert('服务器出错,请联系管理员!');
		            }
		        });
		    }
	}

	/**
	$(document).ready(function(){
	    window.onerror = killErrors;
	})

	function killErrors(){
	    return true;
	}
	*/

</script>