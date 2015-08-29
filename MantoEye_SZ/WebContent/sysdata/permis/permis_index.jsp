<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>模块配置</title>
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
														父模块：
													</td>
													<td class="condition_value">
														<tree:ModuleTreeTag title="--系统管理--" name="parentId"
															selectID="${param['filter_LIKE_vcParentId']}" action=""
															searchFlag="1" />
													</td>
													<td class="condition_name">
														菜单名称：
													</td>
													<td class="condition_value">
														<input type="text" id="menuName" style="height: 17px;"/>
													</td>

													<td class="condition_name">
														映射地址：
													</td>
													<td class="condition_value">
														<input type="text" id="redirectUrl" style="height: 17px;" />
													</td>

													<td id="spaceTd" width="*"></td>
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
												模块配置数据
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
var date=new Date();
date=date.valueOf();
date=date-24*60*60*1000;
date=new Date(date); 
var hasinit=false;//第一次是否初始化图形
var initChartAgain = false;//判断是否再次初始化图形
var dataType_search = 1 ; //页面显示数据类型
var timeLevel_search ='2';//查询时间粒度
var time_search =date.getYear()+'-'+''+(date.getMonth()+1)+'-'+''+date.getDate(); ;//查询开始时间

var dsConfig= {
	//data : data1 ,
	uniqueField : 'id' ,
	fields :[
		{name : 'id'        },
		{name : 'parentMenuName'      },
		{name : 'vcMenuName'    },
		{name : 'vcRedirectUrl'    }
	]
};

var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" },
		{ id : 'parentMenuName' , header : "父菜单名称"  },
		{ id : 'vcMenuName'     , header : "菜单名称"  },
		{ id : 'vcRedirectUrl'    , header : "映射地址"  }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/menu_query.do?1=1',
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
	pageSize :  getDispalyPageSize(0,1)  ,
	remoteSort : false ,
	pageSizeList :[10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	toolbarContent : 'nav | goto | pagesize | state' ,
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var firstInit=true;
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
	
		if(firstInit==true){
		var pageSize=getDispalyPageSize(0,1);
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

//查询条件样式(注意:只有这样才可以操作)
$(document).ready(function(){
	$(document.getElementById("parentId")).attr("style","height:22px;width:130px");
});
//grid查询
function query() {
	//父模块
	var obj = document.getElementById("parentId");
	var index = obj.selectedIndex;
	var value = obj.options[index].value;
	
	var param={
		'filter_LIKE_vcMenuName' : GT.U.getValue(GT.$('menuName')),
		'filter_LIKE_vcRedirectUrl' : GT.U.getValue(GT.$('redirectUrl')),
		'filter_LIKE_vcParentId':value
	};
	GT.$grid('datagrid').query( param );
}
//重置查询条件
function reset(){
	$('#menuName').attr("value","");
	$('#redirectUrl').attr("value","");
	document.getElementById("parentId").selectedIndex=0;
}

/***************初始化toolbar***************************/
$(document).ready(function(){
	displayOrHiddenTd();
 		//按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，该部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和数量应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('_BTN_ADD,_BTN_EDIT,_BTN_DEL',permflag);
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '添加模块',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          		var v = showMMDialog('/menu_input.do','','650px','460px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '编辑模块',
          bodyStyle : 'edit',
          useable :  bflags[1],
          handler : function(){
          	var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		var id=getSelectedId('datagrid');
          		var v = showMMDialog('/menu_edit.do?id='+id,'','650px','460px');
          		reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '删除模块',
          bodyStyle : 'delete',
          useable :  bflags[2],
          handler : deleteMenu
        }]
      }); 
	  toolbar.render();
    });

//重新加载数据
function reloadData(v){
	if(v=='add' || v=='edit'){
       query();
    }
}
//删除事件
function deleteMenu(){
    if(getSelectedCount('datagrid')<1){
       alert('请选择需要删除的模块!');
       return;
    }
	var ids = getSelectedKeys('datagrid');
	var flag = window.confirm("确定删除吗?");
	if(flag){
		$.ajax({
			type : "post",
			url : "menu_deleteMenu.do",
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



