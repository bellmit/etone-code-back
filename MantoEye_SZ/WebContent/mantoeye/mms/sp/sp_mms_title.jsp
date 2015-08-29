<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>SP彩信按主题统计</title>
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
	<iframe id="download" name="download" height="0px" width="0px"></iframe>
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
													<td class="condition_value" id="dataType_search_td_id" style="display: none">
														<input type="radio" name="dataTypeRadio"
															style="width: 20px; border: 0px;"
															onclick='showDataType(0)' value="1" checked />
														GPRS
														<input type="radio" name="dataTypeRadio" value="2"
															style="width: 20px; border: 0px;"
															onclick='showDataType(1)' />
														TD
													</td>
													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="2" selected>
																天
															</option>
															<option value="3">
																周（周日~周六）
															</option>
															<option value="4">
																月
															</option>
														</select>
													</td>
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectTime()" id="time_search" />
													</td>
													<td width="*"></td>
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
												SP彩信按主题统计列表
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
							<div id="dataLoading" class="dataLoading" style="display: none;">
				<img src="/skin/Default/images/icon/16/loading.gif"></img>
				操作中，请稍后...</div>
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
var time_search =date.getYear()+'-'+((date.getMonth()+1)>10?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+(date.getDate()>10?date.getDate():("0"+date.getDate())) ;//查询开始时间

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'       },
		{name : 'port'      },
		{name : 'title'      },
		{name : 'totalSendFlush'      },
		{name : 'flushRate' },
		{name : 'allFlushRate' }
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间" ,sortable:false     },
		{ id : 'port'      , header : "端口"     ,sortable:false  },
		{ id : 'title'      , header : "主题"   ,sortable:false    },
		{ id : 'totalSendFlush'    , header : "发送量(条)"  ,headAlign:'right' ,align:'right',exportnumber:true },
		{ id : 'flushRate'  , header : "SP彩信占比(%)"  ,headAlign:'right' ,align:'right',renderer:renderFormatDataInit4  },
		{ id : 'allFlushRate'  , header : "全网占比(%)"  ,headAlign:'right' ,align:'right',renderer:renderFormatDataInit4  }
];

var gridConfig={
	id : "datagrid",
	loadURL :'/spMmsTitle_query.do',
	exportURL :'/spMmsTitle_export.do?1=1' ,
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
	pageSize : getDispalyPageSize(0,1) ,
	remoteSort : true ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	toolbarContent : 'nav | goto | pagesize | state' ,
//	customHead : 'myHead',
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
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(obj);
	
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

//grid查询
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);

	initChartAgain = true;
	time_search = $('#time_search').attr("value");
	timeLevel_search = $("#timeLevel_search option:selected").val();
	if(time_search=='' ){
		alert('请选择时间!');
		return ;
	}
	var param={
		hasinit:hasinit,
		dataType_search:dataType_search,
		timeLevel_search:timeLevel_search,
		time_search:time_search
	};
	GT.$grid('datagrid').query( param );
}

/*****************查询条件**********************/
//设置页面显示的数据类型
function showDataType(type){
	dataType_search = type ;
}
//时间改变事件
function changeTimeLevel(){
	$('#time_search').attr("value","");
}
//时间选择事件
function selectTime(){
	var tls = $("#timeLevel_search option:selected").val();
	switch(tls)
	   {
	   case '1':
	   	 new WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00'});
	     break;
	   case '2':
	   	 new WdatePicker({dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	   	 new WdatePicker({dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	 new WdatePicker({dateFmt:'yyyy-MM'});
	     break;
	 }
}
//重置查询条件
function reset(){
	$('#dataType_search_td_id input:first').attr("checked","true");//设置radio默认值
	$("#timeLevel_search").get(0).selectedIndex=0;
	$('#time_search').attr("value","");
}

//初始化查询时间
$(document).ready(function(){
	$('#time_search').attr('value',time_search);
});
/*****************查询条件结束**********************/



/***************初始化toolbar***************************/
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
          	 //var ttc = datagrid.getTotalRowNum()
           /*	if(ttc>65500){
           		alert('结果数据太多，请重新设置查询条件!');
           		return;
           	}*/
           //参数
          	var ttc = datagrid.getTotalRowNum();
           	if(ttc>60000){
               //	alert('>60000');
           		if(confirm('结果数据太多，确定要导出？')){
                   	$('#dataLoading').css('display', 'none');
               		openUrl('/spMmsTitle_export.do?1=1&dataType_search='+dataType_search+'&timeLevel_search='+timeLevel_search+'&time_search='+time_search);
               		setTimeout("showDataDailog()",8000);
               	}
           	}
           	else{
			var params={
				dataType_search:dataType_search,
				timeLevel_search:timeLevel_search,
				time_search:time_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='SP彩信主题统计数据';
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }}
        }]
      });
	  toolbar.render();
    });
/***************初始化toolbar结束***************************/

function showDataDailog(msg){
	$('#dataLoading').css('display','none'); 
}

function openUrl(urlstr)
{
   	var obj=document.getElementById('download');
   	obj.contentWindow.location.href=urlstr;
   	   	
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



