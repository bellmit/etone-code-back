<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>任务定制文件列表</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin//Default/css/maincontent.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
			<script src="/js/nav.js"></script>
			<base target="download">
	</head>
	<body>
	<iframe id="download" name="download" height="0px" width="0px"></iframe><!--用iframe模拟文件下载-->
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
							<td height="5px" bgcolor="#D3E0F2" width="100%">
								<input type="hidden"  name="taskid" />
							</td>
						</tr>
						<tr>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr>
										<td width="4px" height="24px;">
											<img
												src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" />
										</td>
										<td width="100%"
											style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
											任务定制文件列表
										</td>
										<td width="4px">
											<img
												src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" />
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
				<td width="2"></td>
			</tr>
		</table>
	</body>
</html>
<script type="text/javascript">
var obj = window.dialogArguments;
var taskId = obj.taskId ;
var taskType = obj.taskType;
var taskName = obj.taskName;
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'vcFileName'},
		{name : 'nmFileSize'},
		{name : 'vcFileFormat'},
		{name : 'detail'}
	]
};
function renderManager(value ,record,columnObj,grid,colNo,rowNo){
	var fileid = record.nmDataOutPutDecTaskId.toString();
	return '<img style="cursor: hand;border:0" onclick="downloadFile(\''+fileid+'\');" src="/skin/Default/images/MantoEye/icon/download.gif" alt="下载文件" />';
}
function downloadFile(fileid){	
	var tn = encodeURIComponent(taskName);
 	openUrl("/dataOutPutDecTask_downloadForRomote.do?fileid="+fileid+"&taskType="+taskType+"&taskName="+tn);
}
var colsConfig = [
		{ id : 'vcFileName' , header : "文件名称"   ,width:250 },
		{ id : 'nmFileSize' , header : "文件大小(KB)"    ,width:100  },
		{ id : 'vcFileFormat'  , header : " 文件类型"    ,width:100   },
		{ id : 'detail' , header : "下载",sortable:false ,width:60 , 
			renderer:renderManager}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/dataOutPutDecTask_query.do?taskid='+taskId+"&tasktype="+taskType,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:520,
	height:360,
	resizable : false,
	showGridMenu : false,
	allowCustomSkin : false,
	allowFreeze : false,
	allowGroup : false,
	allowHide : false,
	container : 'data_container', 
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize : 10 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var flag = false; //判断初始化IE版本
function loadComplate(){
	//判断是否查询到数据
	judgeMyData(datagrid);
	if(!flag){
		if (checkIE() == "IE6") {
			datagrid.setSize(520,318);
		}
		flag = true;
	}
}

//判断grid查询是否有数据，没有则提示
function judgeMyData(datagrid,k){
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var tdWidth="520px"
	if(typeof(datagrid.getRecord(0))=="undefined"){
		var $o = $('#'+datagrid.id+'_bodyDiv').find('table').eq(0).find('tbody').eq(0);//获取对象
		$o.find('tr').eq(0).remove();//由于控件存在bug，所以需要删除第一行
		var $tr = $('<tr><td height="20px" width='+tdWidth+' style="font-size:14px" align="center" bgcolor="#EEEEEE">没有数据显示!</td></tr>');
		$tr.appendTo($o);
	}
}

/***************初始化toolbar***************************/
$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
        items : [{
          type : 'button',
          text : '',
          bodyStyle : '',
          useable : 'F',
          handler : function(){
            
          }
        }]
      });
	  toolbar.render();
    });
/***************初始化toolbar结束***************************/


/**
脚本不出错
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
//showModalDialog session 丢失
function openUrl(urlstr)
{
   	var obj=document.getElementById('download');
   	obj.contentWindow.location.href=urlstr;
   	   	
}
</script>
