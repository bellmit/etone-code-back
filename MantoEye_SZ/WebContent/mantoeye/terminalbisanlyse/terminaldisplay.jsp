<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端分析数据呈现</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript"
			src="/tools/gt-grid/gt_grid_all_source.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<!-- 导入业务选择复选框 -->
		<link rel="stylesheet" href="/mantoeye/dialog/dialog.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
		<script type="text/javascript" src="SymbolDialog.js"></script>
		<!-- 自动补全JS -->
<script type='text/javascript'
	src='/tools/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript'
	src='/tools/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript'
	src='/tools/autocomplete/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css"
	href="/tools/autocomplete/jquery.autocomplete.css" />

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
													<td class="condition_name" style="width: 100px;">
														请选择分析指标:
													</td>
													<%
														String displayId = (String)request.getSession().getAttribute("displayId");
														String[] displayIds = displayId.split("\\,");
														for (int i = 0; i < displayIds.length; i++) {
															String displayName = "用户数";
															if (displayIds[i].equals("2")) {
																displayName = "流量";
															} else if (displayIds[i].equals("3")) {
																displayName = "用户占比";
															} else if (displayIds[i].equals("4")) {
																displayName = "流量占比";
															} else if (displayIds[i].equals("5")) {
																displayName = "平均流量";
															}
													%>
													<td style="width: 80px;">
														<input type="checkbox" onclick="hiddenColumn(this);"
															class="dataType" value="<%=i + 1%>" name="dataType"
															checked
															style="width: 20px; border: 0px; background-color: transparent;" />
														<%=displayName %>
													</td>

													<%
														}
													%>
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
												终端业务分析数据
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
								<!-- 自定义列表头. -->
								<!-- -->

								<div id="toolbar" style="height: 25px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container">
										<table id="myHead" style="display: none">
											<tr id="tablehead" valign="middle">
												<td rowspan="2" columnId='terminalName'>
													终端名称
												</td>
												<td rowspan="2" columnId='sumFlush'>
													总流量(KB)
												</td>
												<td rowspan="2" columnId='sumUser'>
													总用户数
												</td>
												<%
													String business = (String)request.getSession().getAttribute("business");
													business = java.net.URLDecoder.decode(business, "UTF-8");;
													String[] businesses = business.split("\\,");
													for (int i = 0; i < businesses.length; i++) {
												%>
												<td colspan="<%=displayIds.length%>" style="padding-top:2px;" class="busihead"
													align="center"><%=businesses[i]%></td>

												<%
													}
												%>

											</tr>
											<tr>
												<%
													for (int i = 0; i < businesses.length; i++) {
														for (int j = 0; j < displayIds.length; j++) {
															String str1 = "column" + i + displayIds[j];
															String headName = "用户数";
															if (displayIds[j].equals("2")) {
																headName = "流量(KB)";
															} else if (displayIds[j].equals("3")) {
																headName = "用户占比(%)";
															} else if (displayIds[j].equals("4")) {
																headName = "流量占比(%)";
															} else if (displayIds[j].equals("5")) {
																headName = "平均流量(KB/人)";
															}
												%>
												<td class="smallheand" columnId='<%=str1%>'><%=headName%>
												</td>

												<%
													}
													}
												%>
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
				<td colspan="2" height="5px"></td>
			</tr>
		</table>
	</body>
</html>
<script type="text/javascript">
var obj =parent.terminalObj;
var business=obj.business;
var taskId2=obj.taskId
var displayId=obj.displayId;
var displayIds=displayId.split(",");
var businesses=business.split(",");
var field=[];
for(var i=0;i<businesses.length;i++){
	for(var j=1;j<displayIds.length;j++){
		var record={ name : 'column'+i+displayIds[j] }
				field.push(record);
	}
	
}
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :field
};



/*
function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var date = record.fullDate.toString();
	return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/space.png" alt="查看空间分布" onclick="openSapceDistribute(\''+date+'\');" />';
}
*/
var colsConfig=[];
var record={ id : 'terminalName'      , header : "终端名称" , width:80   };
	colsConfig.push(record);
var record={ id : 'sumFlush'      , header : "总流量(	KB)" , width:80,headAlign:'right' ,align:'right',exportnumber:true     };
	colsConfig.push(record);
var record={ id : 'sumUser'      , header : "总用户数" , width:80,headAlign:'right' ,align:'right' ,exportnumber:true    };
	colsConfig.push(record);
for(var i=0;i<businesses.length;i++){
	for(var j=0;j<displayIds.length;j++){
		var headName="用户数";
		if(displayIds[j]=="2"){
			headName="流量(KB)";
		}else if(displayIds[j]=="3"){
			headName="用户占比(%)";
		}else if(displayIds[j]=="4"){
			headName="流量占比(%)";
		}else if(displayIds[j]=="5"){
			headName="平均流量(KB/人)";
		}
		record={ id : 'column'+i+displayIds[j]     , header : headName  , width:100,headAlign:'right' ,align:'right' ,exportnumber:true  };
		colsConfig.push(record);
		
	}
}

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/terminalbisanlyse_queryTerminalBusiness.do?1=1&taskId='+taskId2+'&business='+business,
	exportURL :'/terminalbisanlyse_export.do?1=1',
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:"100%",
	height:420,
	resizable : false,
	container : 'data_container',
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	display:displayIds.length,
	pageSize : getDispalyPageSize(0,2) ,
	remoteSort : true ,
	pageSizeList : [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

var firstInit=true;

function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.otherHeight=0;
	obj.otherWidth=0;
	initMutriHeandGridInfo(obj);
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

//// 查询表单的查询函数 ////
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);	
	taskId2=$('#d_taskName').val();
	if(taskId2==''){
		alert('请选择要分析的任务!');
		return ;
	}
	 business=$('#business').val();
	if(business=='-------------------------------------请选择业务-------------------------------------'||business==''){
		alert('请选择业务!');
		return;
	}
	
	
	var param={	
		taskId:taskId2,
		business:business	
	};
	GT.$grid('datagrid').query( param );
}


//初始化grid工具栏
$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
          	var displayColumn=0;
          	$('.dataType').each(function(){
			if(this.checked==true){
				displayColumn++;
			}
			})
           //参数
			var params={
				taskId:taskId2,
				displayColumn:displayColumn,
				business:business	
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='终端业务分析数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
});
    
function showWaiting(){

}

/**
	根据选择要显示的列
*/
function hiddenColumn(args){
	for(var i=0;i<businesses.length;i++){
		var columnName="column"+i+args.value;
		if(args.checked==true){
			datagrid.getColumn(columnName).show();
		}else{
			datagrid.getColumn(columnName).hide();
		}
	}
	
	var i=0;//一种业务显示列数
	
	$('.dataType').each(function(){
		if(this.checked==true){
			i++;
		}
	})
	$('.busihead').each(function(){
		$(this).attr("colSpan",i);
	})
	
}

</script>




