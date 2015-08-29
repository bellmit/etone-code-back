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
<title>告警详细信息</title>
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
	var yestoday = obj.yestoday;
	var date = new Date();
	var year = date.getYear();
	var m = date.getMonth();
	var month = (m+1) > 9 ? (m+1) + '' : '0' + (m+1);
	var d = date.getDate();
	var day = d > 9 ? d + '' : '0' + d;

	day -= 1;

	//var startDate = year + '-' + month + '-' + day;
	var startDate = yestoday;

	$(document).ready(function(){
		$('#title').html(typeName + '历史流量告警数据');
		$('#startDate').attr('value',startDate);
	});

	var dsConfig= {
			//data : data1 ,
			uniqueField : 'alarmId' ,

			fields :[
				{name : 'alarmId' },
				{name : 'typeName' },
				{name : 'changeFlush' },
				{name : 'isIncrease' },
				{name : 'isDecrease' },
				{name : 'isLow' },
				{name : 'processor' },
				{name : 'changeRatio' },
				{name : 'disposeTime' },
				{name : 'fullDate' }
			]
		};

	var colsConfig = [
		  				{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" ,exportable:false},
		  				{ id : 'fullDate',header : "时间" ,headAlign:'left' ,align:'left',width:68 },
		  				{ id : 'typeName',header : "区域名称" ,headAlign:'left' ,align:'left',width:126},	
		  				{ id : 'typeId',header : "区域Id",hidden:true ,headAlign:'left' ,align:'left',width:108},	
		  			//{ id : 'changeFlush',header : "流量(B)" ,headAlign:'right' ,align:'right',width:120 },	
		  				{ id : 'changeFlushKB',header : "流量(KB)" ,headAlign:'right' ,align:'right',width:120,hidden:true,sortable:true },	
		  				{ id : 'changeFlushMB',header : "流量(MB)" ,headAlign:'right' ,align:'right',width:120 ,sortable:true},	
		  				{ id : 'changeFlushGB',header : "流量(GB)" ,headAlign:'right' ,align:'right',width:120 ,hidden:true ,sortable:true},		
		  				{ id : 'changeRatio',header : "变化率(%)" ,headAlign:'right' ,align:'right',width:68 },	
		  				//{ id : 'isIncrease',header : "是否增长率过大" ,headAlign:'left' ,align:'left',width:108 },	
		  				//{ id : 'isDecrease',header : "是否下降率过大" ,headAlign:'left' ,align:'left',width:108 },	
		  				//{ id : 'isLow',header : "是否偏低" ,headAlign:'left' ,align:'left',width:60 },	
		  				//{ id : 'processor',header : "处理者" ,headAlign:'left' ,align:'left',width:76 }	
		  			  // ,{ id : 'disposeTime',header : "消除时间" ,headAlign:'left' ,align:'left',width:126}
		  			    { id : 'detail',header : "操作" ,headAlign:'left' ,align:'center',sortable:false,exportable:false,width:90,renderer:renderFormatAlarm} 
		   ]; 

	function renderFormatAlarm(value ,record,columnObj,grid,colNo,rowNo){
		var typeName = record.typeId;
		var warmDate = record.fullDate;

		return '<div id="mainbtn" style="padding-left:5px;">'
		+'<ul><li><a href="#" onclick="javascript:doCancelWarn(\''+typeName+'\',\''+warmDate+'\');"' 
		+'style="width: 68px;" 　title="消除告警"><span>消告警</span> </a></li>'
		+'</ul></div>';
	}	
	
	var gridConfig={
			id : "datagrid",
			loadURL :'/flushAlarm_queryHistoryDayMv.do?1=1&startDate='+startDate+'&type='+type,
			exportURL :'/flushAlarm_exportDay.do?1=1' ,
			dataset : dsConfig ,
			columns : colsConfig ,
			width:546,
			height:380,
			resizable : false,
			showGridMenu : false,
			container : 'data_container', 
			beforeLoad:checkLogon,
			toolbarContent : 'nav | goto | pagesize | state' ,
			pageSize : 15 ,
			remoteSort : true ,
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
		obj.hideColumn = 3 ; 
		obj.isCheckbox = true; 
		obj.otherHeight = 0 ;  

		 if(!initHasData){
				sortInit = true;
			} 
			
			//选择呈现单位 
			 if(sortInit && initHasData){
				 buildUnit1();
			 	sortInit = false;
			 }
			 clearCHK('datagrid');
		judgeData(datagrid);
		//initGridInfo(obj);
	}
	function getViewLevel2(max) {
		var level = 0;
		if (max <= 1024 * 10) {//KB
			level = 1;
		} else  if (max > 1024 * 1024 * 10) {//MB
				level = 3;
		} else {//GB
				level = 2;
		}
		return level;
	}
	function buildUnit1(){
		var totalRecords = datagrid.getAllRows().length;
		var max = 0;
		if (typeof (datagrid.getRecord(0)) != "undefined") {
			for (var i = 0; i < totalRecords; i++) {
				var record = datagrid.getRecord(i);
				var fl = record.changeFlushKB;
				if (fl > max) {
					max = fl;
				}
			}
			var level = getViewLevel2(max);
			if (level == 1) {//KB
				datagrid.getColumn("changeFlushKB").show();
				datagrid.getColumn("changeFlushMB").hide();
				datagrid.getColumn("changeFlushGB").hide();
				showUnit = "KB";
			} else {
				if (level == 3) {//GB
					datagrid.getColumn("changeFlushKB").hide();
					datagrid.getColumn("changeFlushMB").hide();
					datagrid.getColumn("changeFlushGB").show();
					showUnit = "GB";
				} else {//MB
					datagrid.getColumn("changeFlushKB").hide();
					datagrid.getColumn("changeFlushMB").show();
					datagrid.getColumn("changeFlushGB").hide();
					showUnit = "MB";
				}
			}
		}
		return showUnit;
	}

	function judgeData() {
		var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
		var tdWidth = "540px";
		 
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

		/*
		if( '2' != warmOK){
			clearWarm = 'T';
		}else{
			clearWarm = 'F';
		}

		buildTools();
		*/

		GT.$grid('datagrid').loadURL='/flushAlarm_queryHistoryDayMv.do?1=1' ;
		GT.$grid('datagrid').query( param );
	}

	function buildCondition(){
		startDate = $('#startDate').val();
		
		var param = {
			startDate : startDate,
			type:type
		}

		return param;
	}

	function getStartWdate(){
		return new WdatePicker({dateFmt:'yyyy-MM-dd'}) ;
	}

	$(document).ready(function(){
	      buildTools();
	});

	function buildTools(){

		$('#toolbar').html('');

		var toolbar = new Toolbar({
	        renderTo : 'toolbar',
	        items : [{
	        	  type : 'button',
		          text : '导出',
		          bodyStyle : 'xls',
		          useable : 'T',
		          handler :exportToXls	        
	        },{
	        	  type : 'button',
		          text : '设置阀值',
		          useable : 'T',
		          handler :changeThresholdValue
			 },{
		          type : 'button',
		          text : '消告警',
		          useable : clearWarm,
		          handler :cancelWarn 
		     }]
	      });
		  toolbar.render();

	}

	 var clearWarm = 'T';

	var w = '600px';
	var h = '500px';
	
	function changeThresholdValue(){

		var obj = new Object();
		if('CGI' == type){
			obj.typeName=type;
		}else if('BSC' == type){
			obj.typeName=type;
		}else if('SGSN' == type){
			obj.typeName=type;
		}else{
			obj.typeName=type;
		}
			obj.type = type;

		w = '600px';
		h = '300px';
		var value = window.showModalDialog("alarmLimitConfig_initQuery2.do?1=1&type="+type,obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	 	//var value = window.showModalDialog("/mantoeye/monitoring/changeThresholdValue.jsp?1=1",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
		
	}

	function cancelWarn(){
		if(getSelectedCount('datagrid')<1){
		       alert('请选择需要消告警的记录!');
		       return;
		}
		
		var typeNames = getColumnValuesByName('datagrid','typeId');
		var warmDate = getColumnValuesByName('datagrid','fullDate');
		doCancelWarn(typeNames,warmDate);
	}

	function doCancelWarn(typeName,warmDate){
		var obj = new Object();
		obj.typeName = typeName;
		obj.type = type;
		obj.warmDate = warmDate;
		w = '360px';
		h = '260px';
	 	var value = window.showModalDialog("/mantoeye/monitoring/cancelWarn.jsp?1=1",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	 	if( 'SUCC_CODE_10000' == value){
			query();
	 	}
	}

	function exportToXls(){
		
		var params=buildCondition();
		
		var fileObj = new Object();
		fileObj.fileName=typeName + '详细告警信息('+startDate+')'; 
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