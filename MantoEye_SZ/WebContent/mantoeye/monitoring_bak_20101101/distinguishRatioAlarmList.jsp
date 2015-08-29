<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.opensymphony.xwork2.ActionContext"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>历史告警信息</title>
<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
<link type="text/css" rel="stylesheet"
	href="/skin/Default/css/maincontent.css" />
<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
	type="text/css"></link>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
<script language="javascript" type="text/javascript"
	src="/tools/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/common_grid.js"></script>
<!-- 列表工具栏 -->
<script type="text/javascript" src="/js/jquery/extend.tab/js/Toolbar.js"></script>
</head>
<body>
<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
	bgcolor="#D3E0F2" width="100%" height="100%">
	<tr>
		<td colspan="2" height="5px"></td>
	</tr>
	<tr valign="top">
		<td>
		<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
			style="width: 100%;">
			<tr valign="top">
				<td>
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tr valign="top" style="font-size: 1px;">
						<td>
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
						</td>
					</tr>
					<tr valign="top">
						<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<tr valign="top">
								<td width="100%" class="condition_down">
								<table id="query_condition" cellspacing="0px" cellpadding="0px;"
									border="0">
									<tr valign="middle">
										<td class="condition_name">查询时间：</td>
										<td><input type="text" class="Wdate"
											onclick="getStartWdate()" style="height: 17px;"
											id="startDate" name="startDate" /></td>
										<td style="width: 5px;">到</td>
										<td><input type="text" class="Wdate"
											onclick="getEndWdate()" style="height: 17px;" id="endDate"
											name="endDate" /></td>
										<td width="*"></td>
										<td width="40px">
										<div id="mainbtn">
										<ul>
											<li><a href="#" onclick="javascript:query();" title="查询"><span>查询</span>
											</a></li>
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
				</table>
				</td>
			</tr>
			<tr>
				<td height="5px" bgcolor="#D3E0F2" width="100%"></td>
			</tr>
			<tr>
				<td>
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tr>
						<td width="4px" height="24px;"><img
							src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" /></td>
						<td width="100%" id="title"
							style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
						分布数据</td>
						<td width="4px"><img
							src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" /></td>
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
	var type = obj.type;
	var typeName = obj.typeName;
	var date = new Date();
	var year = date.getYear();
	var m = date.getMonth();
	var month = m > 9 ? m + '' : '0' + m;
	var d = date.getDate();
	var day = d > 9 ? d + '' : '0' + d;

	var startDate = year + '-' + month + '-' + day;

	month = (m + 1) > 9 ? (m + 1) + '' : '0' + (m + 1);
	day = (d - 1) > 9 ? (d - 1) + '' : '0' + (d - 1);
	var endDate = year + '-' + month + '-' + day;

	$(document).ready(function(){
		$('#title').html(typeName + '历史识别率告警数据');
		$('#startDate').attr('value',startDate);
		$('#endDate').attr('value',endDate);
	});

	var dsConfig= {
			//data : data1 ,
			uniqueField : 'alarmId' ,
			fields :[
				{name : 'alarmRatio' },
				{name : 'reAlarmRatio' },
				{name : 'fullDate' }
			]
		};

	var colsConfig = [
		  				{ id : 'fullDate',header : "时间" ,headAlign:'left' ,align:'left',width:195 },
		  				{ id : 'alarmRatio',header : "识别率(%)" ,headAlign:'right' ,align:'right',width:195,renderer:renderFormatAlarm },	
		  				{ id : 'reAlarmRatio',header : "未识别率(%)" ,headAlign:'right' ,align:'right',width:195,renderer:renderFormatReAlarm} 
		   ]; 

	function renderFormatAlarm(value ,record,columnObj,grid,colNo,rowNo){
		return renderFormat(record.alarmRatio);
	}	
	
	function renderFormatReAlarm(value ,record,columnObj,grid,colNo,rowNo){
		return renderFormat(record.reAlarmRatio);
	}	
		
	function renderFormat(value){
		return value;//+'%';
	}	

	var gridConfig={
			id : "datagrid",
			loadURL :'/distinguishRatioAlarm_queryHistory.do?1=1&startDate='+startDate+'&endDate='+endDate+'&type='+type,
			exportURL :'/distinguishRatioAlarm_export.do?1=1' ,
			dataset : dsConfig ,
			columns : colsConfig ,
			width:590,
			height:380,
			resizable : false,
			showGridMenu : false,
			container : 'data_container', 
			beforeLoad:checkLogon,
			toolbarContent : 'nav | goto | pagesize | state' ,
			pageSize : 15 ,
			remoteSort : false ,
			pageSizeList : [15,20,50,100],
			onComplete:loadComplate
		};

	var datagrid=new GT.Grid( gridConfig );
	GT.Utils.onLoad( function(){
		datagrid.render();
	} );


	function loadComplate(){
		var obj = new Object (); 
		obj.datagrid = datagrid;
		obj.hideColumn = 0 ; 
		obj.isCheckbox = false; 
		obj.otherHeight = 0 ;  

		judgeData(datagrid);
		
		//initGridInfo(obj);
	}

	function judgeData() {
		var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
		var tdWidth = "586px";
		 
		if (typeof (datagrid.getRecord(0)) == "undefined") {
			var $o = $("#" + datagrid.id + "_bodyDiv").find("table").eq(0).find("tbody").eq(0);//获取对象
			$o.find("tr").eq(0).remove();//由于控件存在bug，所以需要删除第一行
			var $tr = $("<tr><td height=\"20px\" width=" + tdWidth + " style=\"font-size:14px\" align=\"center\" bgcolor=\"#EEEEEE\">\u6ca1\u6709\u6570\u636e\u663e\u793a!</td></tr>");
			$tr.appendTo($o);
			initHasData = false;
		}else{
			initHasData = true;
		}
	}

	function query(){
		var param = buildCondition();

		if( '' == startDate){
			alert('起始查询时间不能为空!');
			return;
		}
		if( '' == endDate){
			alert('截止查询时间不能为空!');
			return;
		}

		GT.$grid('datagrid').loadURL='/distinguishRatioAlarm_queryHistory.do?' ;
		GT.$grid('datagrid').query( param );
	}

	function buildCondition(){
		startDate = $('#startDate').val();
		endDate = $('#endDate').val();
		
		var param = {
			startDate : startDate,
			endDate : endDate,
			type:type
		}

		return param;
	}

	function getStartWdate(){
		return new WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'}) ;
	}
	function getEndWdate(){
		return new WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd'}) ;
	}

	$(document).ready(function(){
	      var toolbar = new Toolbar({
	        renderTo : 'toolbar',
	        items : [{
	          type : 'button',
	          text : '导出',
	          bodyStyle : 'xls',
	          useable : 'T',
	          handler :exportToXls
	        }]
	      });
		  toolbar.render();
	});

	function exportToXls(){
		
		var params=buildCondition();
		
		var fileObj = new Object();
		fileObj.fileName=typeName + '历史告警信息('+startDate+'至'+endDate+')'; 
		fileObj.fileFormat = 'xls'; 
		fileObj.gridObj = datagrid; 
		fileObj.params = params;
		exportFile(fileObj);
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