<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务终端分析</title>
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
		<!-- 导入业务选择复选框 -->
		<link rel="stylesheet" href="/mantoeye/dialog/dialog.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
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
												即席查询数据
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

var sql=parent.immediately_sql;
var ninkName=parent.immediately_ninkname;
var fieldName=parent.immediately_name;
var dataTypes=parent.immediately_types;
var names=ninkName.split(',');
var fnames = fieldName.split(',');
var dtypes = dataTypes.split(',');

var field=[];
for(var i=0;i<names.length;i++){
	
		var record={ name : 'column'+i}
		field.push(record);
	
}
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :field
};




var colsConfig=[];

for(var i=0;i<names.length;i++){
	var ffn = fnames[i];
	var ttt = dtypes [i]; 
	record={ id : 'column'+i    , header : names[i],sortable:false  };
	if(ffn=='intRaitype'){
		record={ id : 'column'+i    , header : names[i] ,sortable:false  ,renderer:rendererNetType};
	}
	/*if(ttt =='1'){
		record.exportnumber = true;
	}*/
	colsConfig.push(record);
}
function rendererNetType(value ,record,columnObj,grid,colNo,rowNo){
	if(value=='1'){
		return 'GPRS'
	}else{
		return 'TD'
	}
}
var gridConfig={
	id : "datagrid",
	loadURL :'/immediatelyquery_immediatelyQuery.do?1=1&sql='+sql,
	exportURL :'/immediatelyquery_export.do?1=1',
	dataset : dsConfig ,
	columns : colsConfig ,
	width:"100%",
	height:420,
	resizable : false,
	container : 'data_container',
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	pageSize : getDispalyPageSize(0,1) ,
	remoteSort : true ,
	pageSizeList : [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
//	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

var firstInit=true;
var  objone = new Object ();//使用对象传参,以防以后修改
		objone.datagrid = datagrid;
		objone.hideColumn = 0 ;//隐藏的列数目
		objone.isCheckbox = false;//是否可选择
		objone.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
function loadComplate(){
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(objone);
	
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

var business='';

//// 查询表单的查询函数 ////
function query() {
	
	var taskId=$('#d_taskName').val();
	if(taskId==''){
		alert('请选择要分析的任务!');
		return ;
	}
	 business=$('#business').val();
	if(business=='-------------------------------------请选择终端-------------------------------------'||business==''){
		alert('请选择业务!');
		return;
	}
	
	
	var param={	
		taskId:taskId,
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
          	//alert(datagrid.getTotalRowNum()+"--");
           	var ttc = datagrid.getTotalRowNum()
           	if(ttc>565500){
           		alert('结果数据太多，请重新设置查询条件!');
           		return;
           	}
           //参数
			var params={
				sql:sql
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='即席查询数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
});


/**
	根据选择要显示的列
*/
function hiddenColumn(){
	var flag1=true;
	var flag2=true;
	var flag3=true;
	var flag4=true;
	var flag5=true;
	var i=0;
	var dataType=document.all("dataType");
	
		if(dataType[0].checked="true"){
			flag1=true;
			i=i+1;
		}else{
			flag1=false;
		}
		
		if(dataType[1].checked==true){
			flag2=true;
			i=i+1;
		}else{
			flag2=false;
		}
		
		if(dataType[2].checked==true){
			flag3=true;
			i=i+1;
		}else{
			flag3=false;
		}
		
		if(dataType[3].checked==true){
			flag4=true;
			i=i+1;
		}else{
			flag4=false;
		}
		
		if(dataType[4].checked==true){
			flag5=true;
			i=i+1;
		}else{
			flag5=false;
		}
		
	$('.busihead').each(function(){
		$(this).attr("colSpan",i);
	})
	for(var i=0;i<20;i++){
		var column1='column'+i+'1';
		var column2='column'+i+'2';
		var column3='column'+i+'3';
		var column4='column'+i+'4';
		var column5='column'+i+'5';
		
		if(flag1==true){
			datagrid.getColumn(column1).show();
		}else{
			datagrid.getColumn(column1).hide();
		}
		
		if(flag2==true){
			datagrid.getColumn(column2).show();
		}else{
			datagrid.getColumn(column2).hide();
		}
		
		if(flag3==true){
			datagrid.getColumn(column3).show();
		}else{
			datagrid.getColumn(column3).hide();
		}
		
		if(flag4==true){
			datagrid.getColumn(column4).show();
		}else{
			datagrid.getColumn(column4).hide();
		}
		
		if(flag5==true){
			datagrid.getColumn(column5).show();
		}else{
			datagrid.getColumn(column5).hide();
		}
	}
	

	
	
}

</script>






