<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="../../skin//Default/css/maincontent.css" />
		<!-- 请根据语言和系统编码,自行选择引入的 gt_msg_...js 文件 (默认的msg文件为UTF-8编码) -->
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript" src="/js/nav.js"></script>
		<script type="text/javascript" src="/js/JsMap.js"></script>
		<style>
.dialog_main_class {
	text-align: left;
	display: none;
	background: #eceef3;
	border: 1px solid #336699;
	z-index: 99;
	position: absolute;
	width: 347px;
	height: 230px;
	display: none;
	overflow-x: hidden;
	overflow-y: hidden;
}

.dialog_head_class {
	padding: 0px;
	position: relative;
	height: 20px;
	width: 100%;
	border-bottom: 1px solid #b0c7d7;
	background-image: url(/mantoeye/dialog/toolbar_bg.gif);
}

.dialog_table_class input {
	margin: 15px 0 0 0px;
	height: 14px;
	width: 180px;
}

.dialog_table_class tr td {
	padding-left: 20px;
}

.dialog_table_class select {
	height: 20px;
	width: 185px;
}

.dialog_head_icon_class {
	float: left;
	display: block;
	margin: 2px;
}

.dialog_head_title_class {
	float: left;
	margin-top: 3px;
}

.dialog_button_class {
	width: 99%;
	text-align: center;
}

.dialog_button_class button {
	margin: 8px;
}

.dialog_button_class .dialog_button {
	vertical-align: middle;
	line-height: 18px;
	height: 21px;
	border: 1px solid #336699;
	background: url(/mantoeye/dialog/input_button_bg.gif) repeat-x 0px -2px;
}

.dialog_msg_class {
	height: 40px;
	width: 220px;
	background: #eceef3;
	border: 1px solid #336699;
	position: absolute;
	z-index: 99999;
	display: none;
}

.dialog_msg_class img {
	margin-left: 10px;
	margin-top: 8px;
}
</style>		
	</head>
	<!-- <script type="text/javascript" src="/js/jquery.corner.js"></script>
<script type="text/javascript">
	 $('#readyTest').corner();   
    $(function(){	
        $('div.inner').wrap('<div class="outer"></div>');
        $('p').wrap("<code></code>");
		$("#query_condition").corner("round 8px").parent().css('padding', '4px').corner("round 10px")
       
    });    	
</script> -->
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
												详细任务定制
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
									<div id="data_container">
										<table id="myHead" style="display: none">
											<tr>
												<td columnId='vcColumnNickName'>
													列名
												</td>
												<td columnId='setOut'>
													<input type="checkbox" id="outchk" alt="全选"
														onclick="selectAllOut(this.checked)" />
													设为输出
												</td>
												<td columnId='detail'>
													条件
												</td>
											</tr>
										</table>
									</div>
								</div>
							</td>
						</tr>

					</table>
				</td>
				<td width="2px"></td>
			</tr>
			<tr>
				<td colspan="2" height="3px"></td>
			</tr>
		</table>
	</body>
</html>

<script type="text/javascript"><!--
var url = window.location.href;
var taskId=url.substring(url.indexOf('=')+1,url.length);

function renderInitSet1(value ,record,columnObj,grid,colNo,rowNo){
	var id = 'out'+rowNo;
	var nmTableMapId = record.nmTableMapId;
	var outType = record.outType;
	if(outType!=null){
		return "<input id=\""+id+"\" type='checkbox' name='outchk' value=\""+nmTableMapId+"\" checked class='set1'  style='height:20px;width:20px;' >";
	}else{
		return "<input id=\""+id+"\" type='checkbox' name='outchk' value=\""+nmTableMapId+"\" class='set1'  style='height:20px;width:20px;' >";
	}	
}

var w="1000px";
var h="600px";
if(width==1024){
	w="815px";
	h="600px";
}

function showDetail(taskId,vcColumnName){
	var obj = new Object();
	obj.taskId = taskId;
	obj.width = w;
	if(vcColumnName=='nmMsisdn'){
		window.showModalDialog("/mantoeye/data_catch/task_number.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
	if(vcColumnName=='vcWapUrl'){
		window.showModalDialog("/mantoeye/data_catch/task_url.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
	if(vcColumnName=='vcApn'){
		showMMDialog("/dataOutput_taskApn.do?taskId="+taskId,obj,'680px','500px');
	}
	if(vcColumnName=='vcCgi'){
		showMMDialog("/dataOutput_taskArea.do?taskId="+taskId,obj,'720px','500px');
		$.ajax({
				type : "post",
				url : 'dataOutput_cleanCgiSession.do',
				data : {},
				success : function() {},
				error : function() {}
			})
	}
	if(vcColumnName=='nmImei'){
		window.showModalDialog("/mantoeye/data_catch/task_imei.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
	if(vcColumnName=='vcBussinessName'){
		showMMDialog("/dataOutput_taskBusiness.do?taskId="+taskId,obj,'680px','500px');
	}
}

function displayorhideImg(id,checkid){
	var check = document.getElementById(checkid).checked;
	if(document.images[id].style.display=="none" && check){
		document.images[id].style.display = "block";
	}else{
		document.images[id].style.display = "none";
	}
}

function renderDetail(value ,record,columnObj,grid,colNo,rowNo){
	var nmTableMapId = record.nmTableMapId;
	var vcColumnName=record.vcColumnName;
	var inType=record.inType;
	var id = 'img'+rowNo;
	var checkboxid = 'in'+rowNo;
	if(vcColumnName=='nmMsisdn' || vcColumnName=='vcWapUrl' || vcColumnName=='vcApn' || vcColumnName=='vcCgi' || vcColumnName=='nmImei' || vcColumnName=='vcBussinessName'){
	if(inType==0){
		return '<table><tr>'
				+'<td><input id=\"'+checkboxid+'\" type="checkbox" name="inchk" value=\''+nmTableMapId+'\' class="set1"  style="height:20px;width:20px;" onclick="displayorhideImg(\''+id+'\',\''+checkboxid+'\')"></td>'
				+'<td><img id=\"'+id+'\" style="cursor: hand;display:none" src="/skin/Default/images/MantoEye/icon/info.png" alt="查看详情" onclick="showDetail(\''+taskId+'\',\''+vcColumnName+'\');" /></td>'
				+'</tr></table>'
	}else{
		return '<table><tr>'
				+'<td><input id=\"'+checkboxid+'\" type="checkbox" checked name="inchk" value=\''+nmTableMapId+'\' checked class="set1"  style="height:20px;width:20px;" onclick="displayorhideImg(\''+id+'\',\''+checkboxid+'\')"></td>'
				+'<td><img id=\"'+id+'\" style="cursor: hand" src="/skin/Default/images/MantoEye/icon/info.png" alt="查看详情" onclick="showDetail(\''+taskId+'\',\''+vcColumnName+'\');" /></td>'
				+'</tr></table>'	}
		
	}else{
		return "";
	}
	
}



var dsConfig= {
	uniqueField : 'null' ,
	fields :[
		{name : 'vcColumnName'},
		{name : 'vcColumnNickName'},
		{name : 'nmTableMapId'},
		{name : 'inType'},
		{name : 'outType'}
	]
};
var colsConfig = [
		{ id : 'vcColumnNickName'    , header : "列名"   },
		{ id : 'vcColumnName'    , header : "列名"   ,hidden:true},
		{ id : 'nmTableMapId'    , header : "nmTableMapId"  ,hidden:true },	
		{ id : 'outType'    , header : "intColumnType"  ,hidden:true },		
		{ id : 'setOut'      ,checkType:'checkbox', header : "设为输出",sortable:false,renderer:renderInitSet1    },
		{ id : 'detail'    , header : "条件",sortable:false,renderer:renderDetail}
		
];
var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/dataOutput_queryColumnMap.do?taskId='+taskId,
	exportURL :'/dataOutput_queryColumnMap.do?1=1',
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	resizable : false,
	showGridMenu : false,
	allowCustomSkin : false,
	allowFreeze : true,
	allowGroup : true,
	allowHide : true,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'state' ,
	pageSize : 60 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

			
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 3 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度


	//设置宽度
	//判断是否查询到数据
	judgeData(datagrid);
	
	//初始化grid
	initGridInfo(obj);
	
}

function saveOutPutColumnMapTask(){
	var count = datagrid.getAllRows().length;
	var nmTableMapIds=[];
	var innmTableMapIds=[];
	for(var i=0;i<count;i++){
		var outId = 'out'+i;
		if($('#'+outId).attr('checked')==true){
			nmTableMapIds.push($('#'+outId).attr('value'));
		}
		var inId = 'in'+i;
		if($('#'+inId).attr('checked')==false){
			innmTableMapIds.push($('#'+inId).attr('value'));
		}	
	}
	$.ajax({
				type : "post",
				url : 'dataOutput_saveOutPutColumnMapTask.do',
				data : {
					taskId:taskId,
					nmTableMapIds:nmTableMapIds,
					innmTableMapIds:innmTableMapIds
				},
				success : function() {
					alert("保存成功！");
					location.reload();
				},
				error : function() {
					alert('服务器出错,请联系管理员!');
					}
				})
}



//初始化grid工具栏
$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '保存',
          bodyStyle : 'save',
          useable : 'T',
          handler : function(){
				saveOutPutColumnMapTask();
          }
        }]
      });
	  toolbar.render();
});
   
	
//选择全部
function selectAllOut(check) {
	var gridid = 'datagrid';
	var sy = document.getElementsByName('outchk');
	if(check){
		for (var i = 0; i < sy.length; i++) {
			sy[i].checked = true;
		}
	}else{
		for (var i = 1; i < sy.length; i++) {
			sy[i].checked = false;
		}
	}
}	
/*//选择全部
function selectAllCnd(check) {
	var gridid = 'datagrid';
	var sy = document.getElementsByName('cndchk');
	if(check){
		for (var i = 0; i < sy.length; i++) {
			if(sy[i].disabled==false)
				sy[i].checked = true;
		}
	}else{
		for (var i = 1; i < sy.length; i++) {
			sy[i].checked = false;
		}
	}
}	
*/
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


