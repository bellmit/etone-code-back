<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>问题反馈</title>
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
									<tr valign="top" style="font-size:1px;">
										<table cellspacing="0" cellpadding="0" border="0" width="100%">
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

									</tr>
									<tr valign="top">
										<td width="100%" class="condition_down">
											<table id="query_condition" cellspacing="0px"
												cellpadding="0px;boder="0">
												<tr valign="middle">
													<td class="condition_name">
														主题：
													</td>
													<td class="condition_value">
														<input type="text" id="vcTitle" value="" />
													</td>
													<td class="condition_name">
														发起人：
													</td>
													<td class="condition_value">
														<input type="text" id="vcCreater" value="" />
													</td>
													<td class="condition_name">
														内容：
													</td>
													<td class="condition_value">
														<input type="text" id="clContent" value="" />
													</td>
													<td width="*"></td>
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
												问题反馈数据
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
var dsConfig= {
	//data : data1 ,
	uniqueField : 'id' ,
	fields :[
		{name : 'id'        },
		{name : 'vcTitle'      },
		{name : 'vcCreater'    },
		{name : 'clContent'    },
		{name : 'dtCreateDate'    },
		{name : 'dtLastUpdate' },
		{name : 'intReplys' }
	]
};
var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" },
		{ id : 'vcTitle' , header : "标题"  },
		{ id : 'vcCreater'     , header : "创建人"  },
		{ id : 'clContent'    , header : "内容" },
		{ id : 'dtCreateDate'    , header : "创建时间" },
		{ id : 'dtLastUpdate'  , header : "最后回复时间" },
		{ id : 'intReplys'  , header : "回复量" }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/feedback_query.do?1=1',
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
	pageSize : 20 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
	toolbarContent : 'nav | goto | pagesize | state' ,
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

//grid回调函数
function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = true;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
		
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(obj);
	
}

//grid查询
function query() {
	var param={
		filter_LIKE_vcTitle : GT.U.getValue(GT.$('vcTitle')),
		filter_LIKE_vcCreater : GT.U.getValue(GT.$('vcCreater')),
		filter_LIKE_clContent : GT.U.getValue(GT.$('clContent'))
	};
	GT.$grid('datagrid').query( param );
}
//重置查询条件
function reset(){
	$('#vcTitle').attr("value","");
	$('#clContent').attr("value","");
	$('#vcCreater').attr("value","");
}

/***************初始化toolbar***************************/
$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '发起反馈',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
          		var v = showMMDialog('/feedback_input.do','','650px','450px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '回复反馈',
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
          		var v = showMMDialog('/feedback_edit.do?id='+id,'','650px','450px');
          		reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '查看反馈',
          bodyStyle : 'view',
          useable : 'T',
          handler :function(){
          	var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		var id=getSelectedId('datagrid');
          		var v = showMMDialog('/feedback_view.do?id='+id,'','650px','450px');
          		reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '删除反馈',
          bodyStyle : 'delete',
          useable : 'T',
          handler : deleteFeedback
        }
        ]
      }); 
	  toolbar.render();
    });

//重新加载数据
function reloadData(v){
	if(v=='add' || v=='edit' || v == 'reply'){
       query();
    }
}
//删除事件
function deleteFeedback(){
    if(getSelectedCount('datagrid')<1){
       alert("请选择需要删除的反馈！");
       return;
    }
	var ids = getSelectedKeys('datagrid');
	var flag = window.confirm("确定删除吗?");
	if(flag){
		$.ajax({
			type : "post",
			url : "feedback_deleteFeedback.do",
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


/**
脚本不出错
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}

</script>



