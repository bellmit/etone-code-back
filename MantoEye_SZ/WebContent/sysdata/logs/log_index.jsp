<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>系统日志</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
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
														操作者：
													</td>
													<td class="condition_value">
														<input type="text" id="user_search" value="" style="height: 17px;"/>
													</td>
													<td class="condition_name">
														操作IP：
													</td>
													<td class="condition_value">
														<input type="text" id="ip_search" value="" style="height: 18px;" />
													</td>
													<td class="condition_name">
														操作内容：
													</td>
													<td class="condition_value">
														<input type="text" id="content_search" value="" style="height: 17px;"/>
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
												<tr valign="middle">
													<td class="condition_name">
														开始时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 17px;"
															onclick="new WdatePicker({maxDate:'#F{$dp.$D(\'end\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
															id="start" />
													</td>
													<td class="condition_name">
														结束时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 17px;"
															onclick="new WdatePicker({minDate:'#F{$dp.$D(\'start\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
															id="end" />
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
												系统日志数据
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
		{name : 'dtRecordTime'      },
		{name : 'vcLoginUser'    },
		{name : 'vcLogContent'    },
		{name : 'vcLoginIp' }
	]
};
var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" ,exportable:false},
		{ id : 'vcLoginUser'     , header : "操作者",sortable:false,width:100  },
		{ id : 'vcLoginIp' , header : "操作者IP" ,sortable:false ,width:100},	
		{ id : 'dtRecordTime'    , header : "操作时间"  ,sortable:false,width:130},
		{ id : 'vcLogContent'  , header : "日志内容",sortable:false ,width:680 }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/log_query.do?1=1',
	exportURL : '/log_export.do?1=1',
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
	pageSize : getDispalyPageSize(0,2) ,
	remoteSort : true ,
	pageSizeList : [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
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
	columninit=true;
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
var user_search = '';
var content_search = '';
var ip_search = '';
var start = '';
var end = '';
//grid查询
function query() {
		user_search =  GT.U.getValue(GT.$('user_search')),
		content_search= GT.U.getValue(GT.$('content_search')),
		ip_search=GT.U.getValue(GT.$('ip_search')),
		start= GT.U.getValue(GT.$('start')),
		end= GT.U.getValue(GT.$('end'))
	var param={
		s_vcUserName :user_search,
		filter_LIKE_vcLogContent : content_search,
		filter_LIKE_vcLoginIp : ip_search,
		'filter_GE|DATE_dtRecordTime' :start,
		'filter_LE|DATE_dtRecordTime' : end
	};
	GT.$grid('datagrid').query( param );
}
//重置查询条件
function reset(){
	$('#user_search').attr("value","");
	$('#content_search').attr("value","");
	$('#ip_search').attr("value","");
	$('#start').attr("value","");
	$('#end').attr("value","");
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
	  var bflags = initBtnPerm('_BTN_DEL',permflag);
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '删除日志',
          bodyStyle : 'delete',
          useable : bflags[0],
          handler : deleteLogs
        } ,'-',{
         type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
			//导出
			var fileObj = new Object();
			fileObj.fileName='系统日志信息';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = {
		 		s_vcUserName :user_search,
				filter_LIKE_vcLogContent : content_search,
				filter_LIKE_vcLoginIp : ip_search,
				'filter_GE|DATE_dtRecordTime' :start,
				'filter_LE|DATE_dtRecordTime' : end
			};
			exportFile(fileObj);
        }}]
      });
	  toolbar.render();
    });
    
//删除事件
function deleteLogs(){
    if(getSelectedCount('datagrid')<1){
       alert('请选择需要删除的日志!');
       return;
    }
	var logIds = getSelectedKeys('datagrid');
	var flag = window.confirm("确定删除吗?");
	if(flag){
		$.ajax({
			type : "post",
			url : "log_deleteLogs.do",
			data : {
				logIds:logIds
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



