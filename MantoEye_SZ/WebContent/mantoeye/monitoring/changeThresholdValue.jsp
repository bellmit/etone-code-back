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
<title>阀值设置</title>
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
			<!-- 
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
										<td class="condition_name">业务类型</td>
										<td><select id="type">
											<option value="BSC">BSC</option>
											<option value="CGI">CGI小区</option>
											<option value="SGSN">SGSN</option>
										</select></td>
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
			 -->
			<tr>
				<td>
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tr>
						<td width="4px" height="24px;"><img
							src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" /></td>
						<td width="100%" id="title"
							style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
						阀值信息</td>
						<td width="4px"><img
							src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr valign="top" style="background-color: #D3E0F2;">
				<td>
				<table
					style="width: 100%; margin: 0px; margin-bottom: 2px; margin-left: 15px;"
					bgcolor="#D3E0F2" border="0">
					<tr style="height: 18px;">
						<td></td>
					</tr>
					<tr>
						<td>今天流量比昨天流量增长率大于<input type="text" id="alarmLimitAdd"
							style="width: 60px;" name="alarmLimitAdd" value="${addBean }"
							onkeyup="clearNoNum(this);" />%</td>
						<td>【计算公式：(今天流量—昨天流量) / 昨天流量  ╳ 100 ％】</td>
					</tr>
					<tr>
						<td>今天流量比昨天流量下降率大于<input type="text" id="alarmLimitMv"
							style="width: 60px;" name="alarmLimitMv" value="${mvBean }"
							onkeyup="clearNoNum(this);" />%</td>
						<td>【计算公式：(今天流量—昨天流量) / 昨天流量  ╳ 100 ％】</td>
					</tr>
					<tr>
						<td>流量异常：流量值小于<input type="text" id="alarmLimitWarm"
							style="width: 109px;" name="alarmLimitWarm" value="${warmBean }"
							onkeyup="clearNoNum(this);" />KB</td>
						<td></td>
					</tr>
					<tr style="height: 20px;">
						<td></td>
					</tr>
					<tr>
						<td colspan="2">
						<div id="mainbtn" style="padding-left: 230px;">
						<ul>
							<li><a href="#" onclick="javascript:changeThresholdValue2();" id="saveBtn"
								name="saveBtn"><span>保存</span> </a></li>
							<li><a href="#" onclick="javascript:window.close();"
								id="cancelBtn" name="cancelBtn"><span>关闭</span> </a></li>
						</ul>
						</div>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="2"></td>
	</tr>
</table>
<iframe id="upload" name="upload" height="0px" width="0px"></iframe>
</body>
</html>
<script type="text/javascript">
function clearNoNum(obj) 
{ 
   obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符 
   obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
   //obj.value = obj.value.replace(/\.$/g,"");  //验证最后一个字符是数字而不是. 
   obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的. 
   obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 

   if('alarmLimitWarm' == obj.id){
		if( obj.value > 976562499){
			alert('最大值不得超过976562499！');
			obj.focus();
			obj.value='';
			return ;
		}
   }
} 

	var obj = window.dialogArguments;
	//var type = obj.type;
	var type = '${type}';

	$(document).ready(function(){
		$('#title').html(type + '阀值信息&nbsp;&nbsp;&nbsp;');
		$('#type').attr('value',type);
	});

	function okUpdate(){
		$.ajax({
			type : "post",
			url : "alarmLimitConfig_initQuery2.do",
			data : {
				type : type
			},
			error : function() {
				alert('服务器出错,请联系管理员!');
			}
		});	
	} 
	
	function changeThresholdValue2(){

		var operatorWarm ='<';
		var operatorFlush ='>';
		var timeLevelFlush = '2';
		var alarmLimitWarm = $('#alarmLimitWarm').val();
		var alarmLimitAdd = $('#alarmLimitAdd').val();
		var alarmLimitMv = $('#alarmLimitMv').val();

		if( '' == alarmLimitWarm || '' == alarmLimitAdd || '' == alarmLimitMv){
			alert('所设阀值不能为空，请重新设置！');
			return ;
		}

		buildCondition();

		var alarmType = '';
		if( 'CGI' == type){
			alarmType = '1,2,3';
		}else if( 'BSC' == type){
			alarmType = '4,5,6';
		}else if( 'TD' == type){
			alarmType = '11,12,13';
		}else if( 'GPRS' == type){
			alarmType = '14,15,16';
		}else{
			alarmType = '8,9,10';
		}
		
		
		var alarmLimit = alarmLimitWarm+','+alarmLimitAdd+','+alarmLimitMv;
		var timeLevel = '天,天,天';
		var operator = '小于,大于,大于';
		var typeNew = type+','+type+','+type;

		/*
		alert(alarmType);
		alert(alarmLimit);
		alert(operator);
		alert(timeLevel);
		alert(typeNew);
		*/

		$.ajax({
			type : "post",
			url : "alarmLimitConfig_updateAlarm2.do",
			data : {
				alarmType : alarmType,
				alarmLimit : alarmLimit,
				timeLevel : timeLevel,
				operator : operator,
				type : typeNew
			},
			success : function(msg) {
				alert(msg); 
				okUpdate(); 
			},
			error : function() {
				alert('服务器出错,请联系管理员!');
			}
		});
		 
	}

	var dsConfig= {
			uniqueField : 'alarmType' ,

			fields :[
				{name : 'alarmType' },
				{name : 'alarmName' },
				{name : 'operator' },
				{name : 'alarmLimit' },
				{name : 'timeLevel' } 
			]
		};

	var colsConfig = [
						{id: 'chk' ,isCheckColumn : true, checkType:'checkbox',hidden:true, checkValue:false,frozen : false , filterable : true, header: "", title: "全选" },
		  				{ id : 'alarmType',header : "告警类型" ,headAlign:'left' ,align:'left',width:80 ,hidden:true},
		  				{ id : 'alarmName',header : "告警类型名称" ,headAlign:'left' ,align:'left',width:166},	
		  				{ id : 'operator',header : "比较操作符" ,headAlign:'left' ,align:'left',width:110,
		  					editor : { type :'select' ,options : {'>': '大于' , '<':'小于', '=':'等于'}  },
		  					renderer : GT.Grid.mappingRenderer(  {'>': '大于' , '<':'小于', '=':'等于'},'--' )},	
		  				{ id : 'timeLevel',header : "时间粒度" ,headAlign:'left' ,align:'left',width:128 ,
			  				//editor : {type :'select' ,options : {'1': '小时' , '2':'天', '3':'周', '4':'月'}},
			  				//renderer : GT.Grid.mappingRenderer(  {'1': '小时' , '2':'天', '3':'周', '4':'月'} , '天' )},	
			  				renderer:function(){return '天';}},
		  				{ id : 'alarmLimit',header : "阀值" ,headAlign:'right' ,align:'right',width:128 ,editor: { type :'text' ,validRule : 'R,float' }}		
		  			   //,{ id : 'detail',header : "操作" ,headAlign:'left' ,align:'left',exportable:false,width:130,renderer:renderFormatAlarm} 
		   ];
	
	function changeThresholdValue(){
		GT.$grid('datagrid').refresh();
  		var alarmType = getColumnValuesByName('datagrid','alarmType');
		var alarmLimit = getColumnValuesByName('datagrid','alarmLimit');
		var timeLevel = getColumnValuesByName('datagrid','timeLevel');
		var operator = getColumnValuesByName('datagrid','operator'); 
		buildCondition();
			
		/*for(var i = 0 ; i , 3 ; i ++){
			if( '--' == alarmType.split(',')[i]){
				alert('阀值格式错误，请重新输入阀值!');
				return ;
			}
			if( '--' == alarmLimit.split(',')[i]){
				alert('阀值格式错误，请重新输入阀值!');
				return ;
			}
			if( '--' == timeLevel.split(',')[i]){
				alert('阀值格式错误，请重新输入阀值!');
				return ;
			}
			if( '--' == operator.split(',')[i]){
				alert('阀值格式错误，请重新输入阀值!');
				return ;
			}
		}*/

		//alert(alarmType+'=t='+alarmLimit+'=l='+timeLevel+'=tl='+operator+'=op='+type+'==B');

		$.ajax({
			type : "post",
			url : "alarmLimitConfig_updateAlarm.do",
			data : {
				alarmType : alarmType,
				alarmLimit : alarmLimit,
				timeLevel : timeLevel,
				operator : operator,
				type : type
			},
			success : function(msg) {
				alert(msg); 
				query(); 
			},
			error : function() {
				alert('服务器出错,请联系管理员!');
			}
		});
	}

	function getColumnValuesByName(gridid, columnName) {
		var sy = document.getElementsByName("gt_" + gridid + "_chk_chk");
		var result = "";
		var vs = "";
		for (var i = 1; i < sy.length; i++) {
			//if (sy[i].checked) {
				vs = sy[i];
				var $o = $(vs);
				var $tr = $o.parent().parent().parent();
				var $colObj = $tr.find(".gt-col-" + gridid + "-" + columnName.toLowerCase()).eq(0).find("div").eq(0);
				result = result + "," + $colObj.html();
			//}
		}
		return result.substring(1, result.length);
	}
	
	var gridConfig={
			id : "datagrid",
			loadURL :'/alarmLimitConfig_initQuery.do?1=1&type='+type,
			dataset : dsConfig ,
			columns : colsConfig ,
			width:590,
			height:180,
			resizable : false,
			showGridMenu : false,
			container : 'data_container', 
			beforeLoad:checkLogon,
			toolbarContent : 'nav | goto | pagesize | state' , 
			pageSize : 100 ,
			remoteSort : false ,
			pageSizeList : [10,20,50,100],
			onComplete:loadComplate
		};

	var datagrid=new GT.Grid( gridConfig );
/*	
    GT.Utils.onLoad( function(){
		datagrid.render();
	} );
*/

	function loadComplate(){
		var obj = new Object (); 
		obj.datagrid = datagrid;
		obj.hideColumn = 2; 
		obj.isCheckbox = true; 
		obj.otherHeight = 0 ;  

		judgeData(datagrid);
		
		//initGridInfo(obj);
	}

	function judgeData() {
		var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
		var tdWidth = "588px";
		 
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

		GT.$grid('datagrid').loadURL='/alarmLimitConfig_initQuery.do?1=1' ;
		GT.$grid('datagrid').query( param );
		$('#title').html(type + '阀值信息 &nbsp;&nbsp;&nbsp;(提示：在要修改的地方单击，改好后点击更新阀值按钮即可!)');
	}

	function buildCondition(){
		//type = $('#type').val();
		var param = {
			type:type
		} 
		return param;
	}

	/*
	$(document).ready(function(){
	      var toolbar = new Toolbar({
	        renderTo : 'toolbar',
	        items : [{
	        	  type : 'button',
		          text : '更新阀值',
		          useable : 'T',
		          handler :changeThresholdValue2
			 }]
	      });
		  toolbar.render();
	}); 
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