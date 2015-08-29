<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>表字段信息管理</title>
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
														字段名：
													</td>
													<td class="condition_value">
														<input type="text" name="columnname_sarch" id="columnname_sarch"
															style="height: 16px" />
													</td>
													<td class="condition_name">
														字段别名：
													</td>
													<td class="condition_value">
													<input type="text" name="nicename_sarch" id="nicename_sarch"
															style="height: 16px" />
														
													</td>
													</tr>
												<tr valign="middle">
													<td class="condition_name">
														数据类型：
													</td>
													<td class="condition_value">
														<select name="columntype_search" id="columntype_search"
															style="height: 20px; width: 125px" >
															<option value="">
																全部
															</option>
															
															<option value="1">
																数字型
															</option>
															<option value="2">
																字符型
															</option>
															<option value="3">
																日期型
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
												表字段信息列表
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
		{name : 'tableName'        },
		{name : 'vcColumnName'        },
		{name : 'vcColumnNickName'        },
		{ name : 'intColumnType'       }
	]
};
function renderColumnType(value ,record,columnObj,grid,colNo,rowNo){
	if(value=='1'){
		return '数字型';
	}else if(value=='2'){
		return '字符型';
	}else{
		return '日期型';
	}
}

var colsConfig = [
		// isCheckColumn 来指定是不是"checkbox选择列" ,通过 grid.checkedRows 能取得所有被选中的行
		// checkbox的value 通过 fieldName来指定(数据记录的某一列)
		// grid.checkedRows 是一个{}, key为 checkbox的value, value为true/false. 所以要求这一列的值必须是唯一的.
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" },
		{ id : 'tableName'      , header : '表名' },		
		{ id : 'vcColumnName'      , header : '字段名' },
		{ id : 'vcColumnNickName'    , header : '字段别名'  },
		{ id : 'intColumnType'      , header : '数据类型'  ,render:renderColumnType }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/tableColumnMap_query.do?1=1',
	exportURL :'/tableColumnMap_export.do?1=1' ,             
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
	var columntype_search = $("#columntype_search option:selected").val();
	var param={
		'filter_LIKE_ftbTableMap.vcTableName' : $('#tablename_search').attr('value'),
		'filter_LIKE_vcColumnName' : $('#columnname_sarch').attr('value'),
		'filter_LIKE_vcColumnNickName' : $('#nicename_sarch').attr('value'),
		'filter_EQ|INTEGER_intColumnType':columntype_search
	};
	GT.$grid('datagrid').query( param );
}

function reset(){
	$('#tablename_search').attr('value','');
	$('#nicename_sarch').attr('value','');
	$('#columnname_sarch').attr('value','');
	$("#columntype_search").get(0).selectedIndex=0;
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
          }
        }
        /*{
          type : 'button',
          text : '添加表信息',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
          		var v = showMMDialog('/tableColumnMap_add.do?1=1','','640px','400px');
          		reloadData(v);
          }
        },'-',{
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
          		var v = showMMDialog('/tableColumnMap_edit.do?id='+id,'','640px','400px');
          		reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '删除表信息',
          bodyStyle : 'delete',
          useable : 'T',
          handler : deleteRecords
        }*/]
      });
     
	  toolbar.render();
    });
function testButton(){
	var item = {
          type : 'button',
          text : '添加表信息',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
          		var v = showMMDialog('/tableColumnMap_add.do?1=1','','640px','400px');
          		reloadData(v);
          }

}
//重新加f载数据
function reloadData(v){
	if(v=='add' || v=='edit'){
       query();
    }
}
//删除任务 
function deleteRecords(){
	var count = getSelectedCount('datagrid');
		var keys = getSelectedKeys('datagrid');
		if(count<1){
          		alert("请选择要删除的记录！");
         }else{
	if(window.confirm("你确定要删除所选的数据吗?")){
	$.ajax({
		type : "post",
		url : "tableColumnMap_delete.do",
		data : {
			keys:keys
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



