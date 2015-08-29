<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>彩信端口管理</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script src="/js/nav.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
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
						<tr valign="top">
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr valign="top" style="font-size: 1px;">
										<td>
											<table cellspacing="0" cellpadding="0" border="0"
												width="100%">
												<tr>
													<td style="font-size: 1px; width: 4px;">
														<div class="leftcircle"></div>
													</td>
													<td width="100%">
														<div class="middlecircle"></div>
													</td>
													<td width="4px">
														<div class="rightcircle"></div>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr valign="top">
										<td width="100%" class="condition_down">
											<table id="query_condition" cellspacing="0px"
												cellpadding="0px;" border="0">
												<tr valign="middle" style="height: 30px;">
													<td class="condition_name">
														彩信端口号：
													</td>
													<td class="condition_value">
														<input type="text" name="spPort" id="spPort" />
													</td>
													<td class="condition_name">
														彩信所属：
													</td>
													<td class="condition_value">
														<input type="text" name="beLong" id="beLong" />
													</td>
													<td class="condition_name">
														公司名称：
													</td>
													<td class="condition_value">
														<input type="text" name="companyName" id="companyName" />
													</td>
													<td width="200px">
														<div id="mainbtn">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<!--  
																<li>
																	<a href="javascript:reset();" title="重置"><span>重置</span>
																	</a>
																</li>
																-->
															</ul>
														</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
						</tr>
						<tr>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												彩信端口信息数据
											</div>
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
<!--

var spPort='';
var beLong='';
var companyName='';

var dsConfig= {
	uniqueField : 'spPort' ,
	fields :[
		{name : 'spPortMapId'},
		{name : 'spPort'},
		{name : 'beLong'},
		{name : 'companyName'} 
	]
};

var colsConfig = [
		{ id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" ,exportable:false },
		{ id : 'spPort', header : "彩信端口号" ,sortable:false},
		{ id : 'beLong', header : "彩信所属",sortable:false },
		{ id : 'companyName', header : "公司名称",sortable:false}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/spPortControl_query.do?1=1',
	exportURL : '/spPortControl_export.do?1=1',
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	showGridMenu : false,
	allowCustomSkin : false,
	allowFreeze : false,
	allowGroup : false,
	allowHide : false,
	container : 'data_container', 
	beforeLoad:checkLogon,
	pageSize : getDispalyPageSize(0,2),
	remoteSort : false ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	toolbarContent : 'nav | goto | pagesize | state' ,
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var initCondition = true;
var firstInit=true;
//grid回调函数
function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.isCheckbox = true;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	
	
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	
	initColumnWidth(datagrid,1050,0,1);
	initGridInfo(obj);
	
	if(firstInit==true){
		var pageSize=getDispalyPageSize(0,2);
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}
	
}

var w="700px";
var h="500px";
if(width==1024){
	w="600px";
	h="400px";
}

function query(){
	//重置分页数据
	datagrid.setTotalRowNum(-1);

	var param = buildCondition();
	
	GT.$grid('datagrid').query( param );
}

function reset(){
	$('#spPort').attr('value','');
	$('#beLong').attr('value','');
	$('#companyName').attr('value','');
}

function buildCondition(){

	var spPort = $('#spPort').val();
	var beLong = $('#beLong').val();
	var companyName = $('#companyName').val();

	var param={
		 spPort:spPort,
		 beLong:beLong,
		 companyName:companyName
	};
	
	return param;

}

$(document).ready(function(){
    var toolbar = new Toolbar({
        renderTo : 'toolbar',
        items : [{
          type : 'button',
          text : '添加',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
          		var v = showMMDialog('sp_port_manager_add.jsp','','600px','380px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '编辑',
          bodyStyle : 'edit',
          useable : 'T',
          handler : function(){
          	var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          			var id=getSelectedId('datagrid');
          			var v = showMMDialog('/spPortControl_editPrePare.do?spPortBean.spPort='+id,'','600px','380px');
          			reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '删除',
          bodyStyle : 'delete',
          useable : 'T',
          handler : deleteSpPort
        } ,'-',{
          type : 'button',
          text : '导入',
          bodyStyle : 'xls',
          useable : 'T',
          handler :function(){
          		var v = showMMDialog('sp_port_manager_xls_upload.jsp','','600px','380px');
          		reloadData(v);
          }
        } ,'-',{
         type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
            //参数
			var params= buildCondition();
			//导出
			var fileObj = new Object();
			fileObj.fileName='彩信端口信息';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
        }}
        ]
    }); 
	toolbar.render();
});

function reloadData(v){
	if(v=='add' || v=='edit' || v == 'upload'){
	//alert('OK');
       query();
    }
}

function deleteSpPort(){
    if(getSelectedCount('datagrid')<1){
       alert('请选择需要删除的彩信端口信息!');
       return;
    }
     
	var ids = getSelectedKeys('datagrid');
	var flag = window.confirm("确定删除吗?");
	if(flag){
		$.ajax({
			type : "post",
			url : "spPortControl_remove.do",
			data : {
				ids:ids
			},
			success : function(msg) {
				alert(msg);//打印删除是否成功信息
				query();//刷新grid数据
			},
			error : function() {
				alert('服务器出错,请联系管理员!');
			}
		});
	}
}
/*
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
*/
//-->
</script>
