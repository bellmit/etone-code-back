<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>表信息管理</title>
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
									<tr height="4px;" valign="top">
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
														表名：
													</td>
													<td class="condition_value">
														<input type="text" name="tablename_search"
															id="tablename_search" style="height: 16px" />
													</td>
													<td class="condition_name">
														表别名：
													</td>
													<td class="condition_value">
														<input type="text" name="nicename_sarch" id="nicename_sarch"
															style="height: 16px" />
													</td>
													<td class="condition_name">
														表类型：
													</td>
													<td class="condition_value">
														<select name="tabletype_search" id="tabletype_search"
															style="height: 20px; width: 125px">
															<option value="">
																全部
															</option>
															<option value="1">
																小时表
															</option>
															<option value="2">
																月表
															</option>
															<option value="3">
																天表
															</option>
															<option value="4">
																周表
															</option>
															<option value="5">
																原始表
															</option>
														</select>
													</td>
													</tr>
												<tr valign="middle">
													<td class="condition_name">
														业务类型：
													</td>
													<td class="condition_value">
														<select name="busitype_search" id="busitype_search"
															style="height: 20px; width: 125px" >
															<option value="">
																全部
															</option>
															<option value="0">
																不属于任何业务
															</option>
															<option value="1">
																数据业务分析
															</option>
															<option value="2">
																大流量用户
															</option>
															<option value="3">
																彩信业务
															</option>
															<option value="4">
																黑莓业务
															</option>
															<option value="5">
																终端分析业务
															</option>
														</select>
													</td>												
													<td>&nbsp;	
													</td>
													<td class="condition_name">
														&nbsp;
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
												表信息列表
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
								<div id="toolbar" style="height: 25px">
						<%-- 		<DIV class=toolbar style="BORDER-BOTTOM: #8db2e3 1px solid">
<TABLE>
<TBODY>
<TR>
<TD style="WIDTH: 2px"></TD>
<TD>
<TABLE cellSpacing=0 cellPadding=0>
<TBODY>
<TR class="over" jQuery1261636358625="2">
<TD class=b_left></TD>
<TD class=b_center><BUTTON class=new style="PADDING-TOP: 4px">提示</BUTTON></TD>
<TD class=b_right></TD></TR></TBODY></TABLE></TD>
<TD><SPAN class=spacer></SPAN></TD>
<TD>
<TABLE cellSpacing=0 cellPadding=0>
<TBODY>
<TR class="" jQuery1261636358625="3">
<TD class=b_left></TD>
<TD class=b_center><BUTTON class=new style="PADDING-TOP: 4px">添加表信息</BUTTON></TD>
<TD class=b_right></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></DIV>--%>
								</div>
								
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
		{name : 'vcTableName'        },
		{name : 'vcTableNickName'        },
		{name : 'intTableType'        },
		{ name : 'intBusinessType'       },
		{ name : 'intIsShow'       },
		{ name : 'intViewFlag'       }
	]
};
function renderTableType(value ,record,columnObj,grid,colNo,rowNo){
	if(value=='1'){
		return '小时表';
	}else if(value=='2'){
		return '月表';
	}else if(value=='3'){
		return '天表';
	}else if(value=='4'){
		return '周表';
	}else {
		return '原始表';
	}
}
function renderBusinessType(value ,record,columnObj,grid,colNo,rowNo){
	if(value=='0'){
		return '不属于任何业务';
	}else if(value=='1'){
		return '数据业务分析';
	}else if(value=='2'){
		return '大流量用户';
	}else if(value=='3'){
		return '彩信业务';
	}else if(value=='4'){
		return '黑莓业务';
	}else {
		return '终端分析业务';
	}
}
function renderViewFlag(value ,record,columnObj,grid,colNo,rowNo){
	if(value=='1'){
		return '表';
	}else{
		return '视图';
	}
}
function renderIsShow(value ,record,columnObj,grid,colNo,rowNo){
	if(value=='0'){
		return '不呈现';
	}else{
		return '呈现';
	}
}
var colsConfig = [
		// isCheckColumn 来指定是不是"checkbox选择列" ,通过 grid.checkedRows 能取得所有被选中的行
		// checkbox的value 通过 fieldName来指定(数据记录的某一列)
		// grid.checkedRows 是一个{}, key为 checkbox的value, value为true/false. 所以要求这一列的值必须是唯一的.
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" },
		{ id : 'vcTableNickName'      , header : '表别名'   },
		{ id : 'vcTableName'      , header : '表名' },		
		{ id : 'intTableType'      , header : '表类型' ,renderer:renderTableType  },
		{ id : 'intBusinessType'    , header : '业务类型'  ,renderer:renderBusinessType },
		{ id : 'intIsShow'      , header : '是否即席查询呈现'  ,renderer:renderIsShow },
		{ id : 'intViewFlag'    , header : '是否视图' ,renderer: renderViewFlag}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/tableMap_query.do?1=1',
	exportURL :'/tableMap_export.do?1=1' ,             
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
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize : 20 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});


//grid回调方法
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

//查询
function query() {
	var tabletype_search = $("#tabletype_search option:selected").val();
	var busitype_search = $("#busitype_search option:selected").val();
	var param={
		'filter_LIKE_vcTableName' : $('#tablename_search').attr('value'),
		'filter_LIKE_vcTableNickName' : $('#nicename_sarch').attr('value'),
		'filter_EQ|INTEGER_intTableType':tabletype_search,
		'filter_EQ|INTEGER_intBusinessType':busitype_search
	};
	GT.$grid('datagrid').query( param );
}

function reset(){
	$('#tablename_search').attr('value','');
	$('#nicename_sarch').attr('value','');
	$("#tabletype_search").get(0).selectedIndex=0;
	$("#busitype_search").get(0).selectedIndex=0;
}
function addbutton(){
//ttt.innerText = document.getElementById("toolbar").innerHTML;
	alert(document.getElementById("toolbar").innerHTML);
}
//grid工具栏

$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
        items : [
        {
          type : 'button',
          text : '提示',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
          		alert("数据直接从数据库维护，如需要从页面维护，请修改jsp页面。");
          		//addbutton();
          }
        }
        /*,'-',{
          type : 'button',
          text : '添加表信息',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
          		var v = showMMDialog('/tableMap_add.do?1=1','','640px','400px');
          		reloadData(v);
          }
        }/*,'-',{
          type : 'button',
          text : '编辑表信息',
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
          		var v = showMMDialog('/tableMap_edit.do?id='+id,'','640px','400px');
          		reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '删除表信息',
          bodyStyle : 'delete',
          useable : 'T',
          handler : deleteTask
        } */]
      });
	  toolbar.render();
    });

//重新加载数据
function reloadData(v){
	if(v=='add' || v=='edit'){
       query();
    }
}
//删除任务 
function deleteTask(){
	var taskIds = getSelectedKeys('datagrid');
	if(window.confirm("你确定要删除所选的数据吗?")){
	$.ajax({
		type : "post",
		url : "tableMap_delete.do",
		data : {
			taskIds:taskIds
		},
		success : function(msg) {
			alert(msg);//打印删除是否成功信息
			query();//刷新grid数据
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});}
}
/**
*
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
</script>



