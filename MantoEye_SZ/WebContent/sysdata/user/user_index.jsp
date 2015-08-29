<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>用户管理</title>
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
														用户昵称：
													</td>
													<td class="condition_value">
														<input type="text" id="userName" value="" style="height: 17px;" />
													</td>
													<td class="condition_name">
														登录名：
													</td>
													<td class="condition_value">
														<input type="text" id="loginName" value="" style="height: 17px;"/>
													</td>
													
													<!-- 
													<td class="condition_name">
														部门：
													</td>
													<td class="condition_value">
														<select name="depart" id="depart"
															style="height: 22px; width: 125px">

														</select>
													</td>
													 -->
													<td class="condition_name">
														角色：
													</td>
													<td class="condition_value">
														<select name="role" id="role"
															style="height: 22px; width: 125px">

														</select>
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
												<tr valign="middle" style="display:none">
													<td class="condition_name">
														手机：
													</td>
													<td class="condition_value">
														<input type="text" id="mobel" value="" style="height: 17px;"/>
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
												系统用户数据
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
		{name : 'vcUserName'      },
		{name : 'vcLoginName'    },
		{name : 'deptName'    },
		{name : 'vcTelephone' },
		{name : 'vcMobel'    },
		{name : 'roleName' },
		{name : 'tiUserType' }
	]
};
var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" },
		{ id : 'vcUserName' , header : "用户昵称" ,sortable:false  },
		{ id : 'vcLoginName'     , header : "登录名" ,sortable:false },
		{ id : 'deptName'    , header : "部门" ,hidden:true },
		{ id : 'tiUserType'    , header : "用户类型" ,renderer:renderUserType ,hidden:true },
		{ id : 'vcTelephone'  , header : "电话" ,sortable:false ,hidden:true},
		{ id : 'vcMobel'    , header : "手机"  ,sortable:false ,hidden:true},
		{ id : 'roleName'  , header : "角色",sortable:false  }
];
function renderUserType(value ,record,columnObj,grid,colNo,rowNo){
	if(value=="1"){
		return "统一网管平台用户"
	}else{
		return "本地测试用户"
	}
}

var gridConfig={
	id : "datagrid",
	loadURL :'/user_query.do?1=1',
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
	remoteSort : true ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
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
	obj.hideColumn = 4 ;//隐藏的列数目
	obj.isCheckbox = true;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
		
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
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
	//初始化查询条件
	if(!initCondition){
		initQueryCondition();
	}
}

//初始化查询条件
var initCondition = false;
function initQueryCondition(){
	$.ajax({
		type : "post",
		url : "user_getAjaxXmlData.do?1=1",
		data : {
			p1:1
		},
		success : function(xml) {
			var data = xml.split('&&&');
			initData(data[0],true);
			initData(data[1],false);
			initCondition = true;
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}

//组装部门和角色信息
function initData(text,flag){
	var xmlDoc = null;  
	try //Internet Explorer  
	{  
	  xmlDoc=new ActiveXObject("Microsoft.XMLDOM");  
	  xmlDoc.async="false";  
	  xmlDoc.loadXML(text);  
	}  
	catch(e)  
	{  
	  try //Firefox, Mozilla, Opera, etc.  
	    {  
	    parser=new DOMParser();  
	    xmlDoc=parser.parseFromString(text,"text/xml");  
	    }  
	  catch(e) {}  
	}  
	
	var objId = null;
	if(flag){
		objId = 'depart' ;
	}else{
		objId = 'role' ;
	}
	$("#"+objId).append("<option value=''>全部</option>");
	$(xmlDoc).find("data").each(function(i){  
		var id=$(this).children("id").text(); 
		var name=$(this).children("name").text();
		$("#"+objId).append("<option value='"+id+"'>"+name+"</option>");
	});
}


//grid查询
function query() {
	var param={
		filter_LIKE_vcUserName : GT.U.getValue(GT.$('userName')),
		filter_LIKE_vcLoginName : GT.U.getValue(GT.$('loginName')),
		filter_LIKE_vcMobel : GT.U.getValue(GT.$('mobel')),
		//filter_LIKE_vcDeptId : GT.U.getValue(GT.$('depart')),
		role_search : GT.U.getValue(GT.$('role'))
	};
	GT.$grid('datagrid').query( param );
}
//重置查询条件
function reset(){
	$('#userName').attr("value","");
	$('#loginName').attr("value","");
	$('#mobel').attr("value","");
	//$("#depart").get(0).selectedIndex=0;
	$("#role").get(0).selectedIndex=0;
}

/***************初始化toolbar***************************/
$(document).ready(function(){
		displayOrHiddenTd();
	  //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，改部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('_BTN_ADD,_BTN_EDIT,_BTN_DEL,_BTN_ROLE,_BTN_UNMP',permflag);
	  
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '添加用户',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          		var v = showMMDialog('/user_input.do','','600px','380px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '编辑用户',
          bodyStyle : 'edit',
          useable : bflags[1],
          handler : function(){
          	var count = getSelectedCount('datagrid');
          	var utype = getColumnValueByName('datagrid','tiUserType');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		if(utype.indexOf("统一网管平台用户")!=-1){
          			alert("不能编辑‘统一网管平台用户’类型的用户");
          		}else{
          			var id=getSelectedId('datagrid');
          			var v = showMMDialog('/user_edit.do?id='+id,'','600px','380px');
          			reloadData(v);
          		}
          	}
          }
        },'-',{
          type : 'button',
          text : '删除用户',
          bodyStyle : 'delete',
          useable : bflags[2],
          handler : deleteUser
        }
        ,'-',{
          type : 'button',
          text : '权限设置',
          bodyStyle : 'role-setup',
          useable : bflags[3],
          handler :function(){
          	var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		var id=getSelectedId('datagrid');
          		var v = showMMDialog('/user_openPermis.do?id='+id,'','600px','380px');
          		reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '同步统一网管平台用户',
          bodyStyle : 'unmpuser',
          useable : bflags[4],
          handler : getAllUnmpUser
        }
        ]
      }); 
	  toolbar.render();
    });

//重新加载数据
function reloadData(v){
	if(v=='add' || v=='edit' || v == 'role'){
       query();
    }
}
//删除事件
function deleteUser(){
    if(getSelectedCount('datagrid')<1){
       alert('请选择需要删除的用户!');
       return;
    }
    var utype = getColumnValuesByName('datagrid','tiUserType');
    if(utype.indexOf("统一网管平台用户")!=-1){
    	alert("不能删除‘统一网管平台用户’类型的用户");
    	return;
    }
	var ids = getSelectedKeys('datagrid');
	var flag = window.confirm("确定删除吗?");
	if(flag){
		$.ajax({
			type : "post",
			url : "user_deleteUsers.do",
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

function getAllUnmpUser(){
	var flag = window.confirm("确定要同步用户?");
	if(flag){
		$.ajax({
			type : "post",
			url : "user_getAllUnmpUserInfo.do",
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



