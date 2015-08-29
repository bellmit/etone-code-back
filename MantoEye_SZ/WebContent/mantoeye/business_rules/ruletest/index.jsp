<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务规则拨测维护</title>
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
													<td class="condition_name" style="width:70px;">
														任务名称:
													</td>
													<td class="condition_value">
														<input type="text" name="taskName_search"
															id="taskName_search" style="height: 18px" />
													</td>
													<td class="condition_name" style="width:40px;">
														定制人:
													</td>
													<td class="condition_value">
														<input type="text" name="taskMan_sarch" id="taskMan_sarch"
															style="height: 18px" />
													</td>
													<td class="condition_name" style="width:70px;">
														号码:
													</td>
													<td class="condition_value">
														<input type="text" name="msis_search" id="msis_search"
															style="height: 18px" />
													</td>
													<td style="width:70px;"></td>
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
														定制时间:
													</td>
													<td class="condition_value" >
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="new WdatePicker({maxDate:'#F{$dp.$D(\'orderTime_end\')}',dateFmt:'yyyy-MM-dd 00:00:00'});"
															name="orderTime_start" id="orderTime_start" /></td>
															<td class="condition_name">到</td>
														<td class="condition_value" ><input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="new WdatePicker({minDate:'#F{$dp.$D(\'orderTime_start\')}',dateFmt:'yyyy-MM-dd 00:00:00'});"
															name="orderTime_end" id="orderTime_end" />	
													</td>
													<td class="condition_name">
														任务状态:
													</td>
													<td class="condition_value">
														<select name="taskStatus_search" id="taskStatus_search"
															style="height: 21px; width: 125px">
															<option value="-1">
																全部
															</option>
															<option value="0">
																未开始
															</option>
															<option value="1">
																进行中
															</option>
															<option value="2">
																已结束
															</option>
															<option value="3">
																已终止
															</option>
														</select>
													</td>
													<td class="condition_name">
														任务类型:
													</td>
													<td class="condition_value">
														<select name="taskType_search" id="taskType_search"
															style="height: 21px; width: 125px" >
															<option value="">
																全部
															</option>
															<option value="5">
																已有业务拨测
															</option>
															<option value="6">
																新业务拨测
															</option>
														</select>
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
												业务拨测任务列表
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
	uniqueField : 'nmDataGetterTaskId' ,
	fields :[
		{name : 'nmDataGetterTaskId'        },
		{name : 'intTaskType'        },
		{name : 'vcTaskName'        },
		{name : 'nmTaskOrder'        },
		{ name : 'msisdn'       },
		{ name : 'dtOrderTime'       },
		{ name : 'dtStartTime'       },
		{name : 'dtEndTime'      },
		{name : 'bitTaskActiveFlag'    },
		{name : 'intTaskStatus'      },
		{name : 'fileInfo'      },
		{name : 'businessId'      },
		{name : 'businessName'      }
	]
};
//function renderManager(value ,record,columnObj,grid,colNo,rowNo){
	//var id = record.nmDataGetterTaskId;
	//var state = record.intTaskStatus;
	//var fileInfo = record.fileInfo;
	//return fileInfo;
//}
/**/
function openDownload(taskid,busiid,state,tasktype,taskName){
 if(state!='2'){
  alert('任务未结束');
  return ;
 }else{
 	 var obj = new Object();
		obj.taskName =taskName;
 	 showMMDialog('/dataGetterBusiness_filelist.do?tasktype='+tasktype+'&taskid='+taskid+'&busiid='+busiid,obj,'540px','420px');
	// openWindow('/dataGetterBusiness_filelist.do?tasktype='+tasktype+'&taskid='+taskid+'&busiid='+busiid,'','540px','420px');	 	
 }
}
function openListPage(taskid,busiid,state,tasktype){
 if(state!='2'){
  alert('任务未结束');
  return ;
 }else{
	 //alert('tasktype='+tasktype+'&taskid='+taskid+'&busiid='+busiid);
 	 showMMDialog('/dataGetterBusiness_openlist.do?tasktype='+tasktype+'&taskid='+taskid+'&busiid='+busiid,'','800px','575px');
 }
}
function openMsisdnFlush(taskid,busiid,state,tasktype){
	 if(state!='2'){
	  alert('任务未结束');
	  return ;
	 }else{
	 	 showMMDialog('/dataGetterBusiness_openflush.do?taskid='+taskid,'','540px','420px');
	 }
	}
function renderManager(value ,record,columnObj,grid,colNo,rowNo){
	var taskid = record.nmDataGetterTaskId.toString();
	var state = record.intTaskStatus.toString();
	var busiid = record.businessId.toString();
	var tasktype =record.intTaskType.toString();
	var taskName = record.vcTaskName.toString();
	//alert(tasktype);
	if(state=='2'){
		//原始数据
		if(tasktype=='5'||tasktype=='6'){
			return '<table><tr>'
			+'<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/info.png" alt="查看任务" onclick="showDetail(\''+taskid+'\');" /></td>'			
			+'<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/files.gif" alt="原始信令文件列表" onclick="openDownload(\''+taskid+'\',\''+busiid+'\',\''+state+'\',\''+tasktype+'\',\''+taskName+'\');" /></td>'
			+'</tr></table>';
		}else{
			return '<table><tr>'
			+'<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/info.png" alt="查看任务" onclick="showDetail(\''+taskid+'\');" /></td>'			
			+'<td><img style="cursor: hand"  src="/skin/Default/images/MantoEye/icon/grid.gif" alt="拨测结果列表" onclick="openListPage(\''+taskid+'\',\''+busiid+'\',\''+state+'\',\''+tasktype+'\');" /></td>'
			+'<td>&nbsp;</td><td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/flush.gif" alt="流量分号码查看" onclick="openMsisdnFlush(\''+taskid+'\',\''+busiid+'\',\''+state+'\',\''+tasktype+'\');" /></td>'
			+'</tr></table>';
		}
	}else{
	 	return 	'<table><tr><td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/info.png" alt="查看任务" onclick="showDetail(\''+taskid+'\');" /></td></tr></table>'			
;
	}
}
function showDetail(id){
	var width = '640px';
    var height = '360px';
	showMMDialog('/businessRuleTest_show.do?id='+id,id,width,height);
}
function renderTaskActiveFlag(value ,record,columnObj,grid,colNo,rowNo){
	if(value==1){
		return '是';
	}else{
		return '否';
	}
}
function renderTaskType(value ,record,columnObj,grid,colNo,rowNo){
	var tasktype =record.intTaskType.toString();
	if(tasktype==5||tasktype==7){
	var businessName = record.businessName.toString();
		return businessName;
	}else{
		return '--未知业务--';
	}
}
function renderTaskStatus(value ,record,columnObj,grid,colNo,rowNo){
	if(value==0){
		return '未开始';
	}else if(value==1){
		return '进行中';
	}else if(value==2){
		return '已结束';
	}else{
		return '已终止';
	}
}

var colsConfig = [
	// isCheckColumn 来指定是不是"checkbox选择列" ,通过 grid.checkedRows 能取得所有被选中的行
		// checkbox的value 通过 fieldName来指定(数据记录的某一列)
		// grid.checkedRows 是一个{}, key为 checkbox的value, value为true/false. 所以要求这一列的值必须是唯一的.
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" },
			{ id : 'intTaskType'      , header : '任务类型',hidden:true  , sortable:false },
		{ id : 'businessName'      , header : '拨测业务',renderer:renderTaskType  , sortable:false },
		{ id : 'vcTaskName'      , header : '任务名称'  , sortable:false },
		{ id : 'nmTaskOrder'      , header : '定制人'  , sortable:false },
		{ id : 'msisdn'    , header : '号码', sortable:false   },
		{ id : 'dtOrderTime'      , header : '定制时间'  , sortable:false },
		{ id : 'dtStartTime'    , header : '开始时间' , sortable:false },
		{ id : 'dtEndTime'     , header : '结束时间'  , sortable:false},
		{ id : 'bitTaskActiveFlag'  , header : '是否激活' ,renderer:renderTaskActiveFlag , sortable:false},
		{ id : 'intTaskStatus'  , header : '状态',renderer:renderTaskStatus  , sortable:false},
		{ id : 'detail'   , header : "操作" , sortable:false, 
			renderer:renderManager
		}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/businessRuleTest_query.do?1=1',
	exportURL :'/businessRuleTest_export.do?1=1' ,             
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
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize :getDispalyPageSize(0,2) ,
	remoteSort : false ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

var firstInit=true;

//grid回调方法
function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 1 ;//隐藏的列数目
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
}

//查询
function query() {
	var taskStatus_search = $("#taskStatus_search option:selected").val();
	var taskType_search = $("#taskType_search option:selected").val();
	var orderTime_start = $('#orderTime_start').attr('value');
	var orderTime_end = $('#orderTime_end').attr('value');
	var param={
		'filter_LIKE_vcTaskName' : $('#taskName_search').attr('value'),
		'filter_LIKE_nmTaskOrder' : $('#taskMan_sarch').attr('value'),
		'taskStatus_search' : taskStatus_search,
		'msis_search' : $('#msis_search').attr('value'),
		'filter_GE|DATE_dtOrderTime':orderTime_start,
		'filter_LE|DATE_dtOrderTime':orderTime_end,
		'filter_EQ|INTEGER_intTaskType' : taskType_search
	};
	
	//alert($('#msis_search').attr('value'));
	GT.$grid('datagrid').query( param );
}

function reset(){
	$('#taskName_search').attr('value','');
	$('#taskMan_sarch').attr('value','');
	$("#taskStatus_search").get(0).selectedIndex=0;
	$("#taskType_search").get(0).selectedIndex=0;
	$('#orderTime_start').attr('value','');
	$('#orderTime_end').attr('value','');
	$('#msis_search').attr('value','');
}

//任务状态更改
function configTask(id,state){
	if(state==1){
		alert('进行中的状态可以终止!');
	}else if(state==3){
		return ;
	}else{
		alert('只有进行中的任务才可以终止!');
	}
}
//文件下载
function downloadFile(state,filePath){
	if(state!=2){
		alert('任务未完成,不能下载!');
	}else{
	
	}
}
var toolbar;
//grid工具栏
$(document).ready(function(){
	//displayOrHiddenTd();
	  //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	  var bflags = initBtnPerm('_BTN_BIZ_ADD,_BTN_NEW_ADD,_BTN_EDIT,_BTN_DEL',permflag);
	    
   toolbar = new Toolbar({
        renderTo : 'toolbar',
        items : [{
          type : 'button',
          text : '添加已有业务拨测任务',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          		var v = showMMDialog('/businessRuleTest_add.do?taskType=5','','640px','560px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '添加新业务拨测任务',
          bodyStyle : 'new',
          useable : bflags[1],
          handler : function(){
          		var v = showMMDialog('/businessRuleTest_add.do?taskType=6','','640px','560px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '编辑任务',
          bodyStyle : 'edit',
          useable : bflags[2],
          handler : function(){
          	var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		var state = getColumnValueByName('datagrid','intTaskStatus');
          		if(state!='未开始'){
          		 	alert("只有状态为'未开始'的任务才能编辑！");
          		}else{
          			var id=getSelectedId('datagrid');
          			var v = showMMDialog('/businessRuleTest_edit.do?id='+id,'','640px','560px');
          		reloadData(v);
          		}
          	}
          }
        },'-',{
            type : 'button',
            text : '停止任务',
            bodyStyle : 'stop',
            useable : bflags[2],
            handler : stopTask
          },'-',{
          type : 'button',
          text : '删除任务',
          bodyStyle : 'delete',
          useable : bflags[3],
          handler : deleteTask
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
function stopTask(){
	var count = getSelectedCount('datagrid');
	if(count>1){
		alert("只能选择一条记录！");
	}else if(count<1){
		alert("请选择一条记录！");
	}else{
		var taskId=getSelectedId('datagrid');
		var state = getColumnValueByName('datagrid','intTaskStatus');
		var tty = getColumnValueByName('datagrid','intTaskType');
		if(state==null){
			return ;
		}
		//alert(tty);
		if(tty=='7'||tty=='8'){
			alert('解码数据提取任务不可以手动停止!');
			return ;
		}
		if(state!='进行中'){
			alert('只有进行中状态的任务才可以停止!');
			return ;
		}
		$.ajax({
			type : "post",
			url : "businessRuleTest_stopTask.do",
			data : {
				taskId:taskId
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

//删除任务 
function deleteTask(){
	var taskIds = getSelectedKeys('datagrid');
	var count = getSelectedCount('datagrid');
	if(count<1){
	alert('请选择要删除的记录！');
	}else{	
		var states = getColumnValuesByName('datagrid','intTaskStatus');
  		if(states.indexOf('进行中')!=-1){
  		 	alert("状态为'进行中'的任务不能删除!");
  		}else{
	if(window.confirm("你确定要删除所选的数据?")){
	$.ajax({
		type : "post",
		url : "businessRuleTest_deleteTask.do",
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
	});}}
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



