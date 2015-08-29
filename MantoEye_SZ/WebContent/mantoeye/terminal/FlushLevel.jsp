<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>流量分层</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js">
</script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js">
</script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js">
</script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js">
</script>
		<script type="text/javascript" src="/js/common_grid.js">
</script>
		<!-- OFC图表 -->
		<script type="text/javascript" src="/flash/js/swfobject.js">
</script>
		<script type="text/javascript" src="/flash/js/json/json2.js">
</script>
		<script type="text/javascript" src="/flash/js/BarLineChartUtil.js">
</script>
		<script type="text/javascript" src="/flash/js/ParseUtils.js">
</script>


		<script src="/js/nav.js">
</script>

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
												cellpadding="0px" boder="0">
												<tr valign="middle">
													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<select name="timeLevel_search" id="timeLevel_search" disabled="disabled"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="0" selected>
																天
															</option>
															<option value="1">
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
												<td width="200px">
														<div id="mainbtn" style="width:300px">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:parent.openTab1('/mantoeye/terminal/FlushGrade.jsp?permId=FLUSHGRADE','FLUSHGRADE','配置流量档次');" 
																		style="width:80px;"  title="配置流量档次"><span>流量档次</span>
																	</a>
																</li>
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
												流量分层统计
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
var timeLevel_search ='0';//查询时间粒度
var time_search =date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); //查询开始时间

	
var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{name : 'nmStatFlushLayeringId'        },
		{name : 'flushlevel'        },
		{name : 'intImsis'        }
	]
};

var w="1000px";
var h="600px";
if(width==1024){
	w="815px";
	h="600px";
}

function openDistributeBusiness(flag, nmStatFlushLayeringId,flushlevel) {
	var obj = new Object();
	obj.nmStatFlushLayeringId = nmStatFlushLayeringId;
	obj.flushlevel = flushlevel;
	obj.timeLevel_search = timeLevel_search;
	obj.time_search = time_search;
	if (flag == 1) {
		window.showModalDialog("/mantoeye/terminal/FlushLevel_Analyse.jsp",obj,"dialogWidth="+ w+ ";dialogHeight="+ h+ ";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
	if (flag == 2) {
		window.showModalDialog("/mantoeye/terminal/FlushLevel_Business.jsp",obj,"dialogWidth="+ w+ ";dialogHeight="+ h+ ";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
	if (flag == 3) {
		window.showModalDialog("/mantoeye/terminal/flushLevel_area_analyse.jsp",obj,"dialogWidth="+ w+ ";dialogHeight="+ h+ ";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
	
}

function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var nmStatFlushLayeringId = record.nmStatFlushLayeringId;
	var flushlevel = record.flushlevel;
	var thtml = "<table><tr>";
	thtml = thtml +'<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/wap.gif" alt="查看终端品牌" onclick="openDistributeBusiness(1,\''+nmStatFlushLayeringId+'\',\''+flushlevel+'\');" /></td>';
	thtml = thtml +'</td><td></div><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/business.gif" alt="查看业务使用情况" onclick="openDistributeBusiness(2,\''+nmStatFlushLayeringId+'\',\''+flushlevel+'\');" /></td>';
	thtml = thtml +'</td><td></div><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/map.gif" alt="查看区域分析" onclick="openDistributeBusiness(3,\''+nmStatFlushLayeringId+'\',\''+flushlevel+'\');" /></td>';
	thtml = thtml +  '</tr></table>';
	return thtml;
}
	
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"    ,exportnumber:true, sortable:false},
		{ id : 'nmStatFlushLayeringId'      , header : "nmFlushConfigureId",hidden:true},
		{ id : 'flushlevel'      , header : "流量档次（KB）",exportnumber:true},
		{ id : 'intImsis'    , header : "用户数" ,exportnumber:true,headAlign:'right' ,align:'right'},
		{ id : 'detail'   , header : "操作" , sortable:false, exportable:false,
			renderer:renderInit
		}
];

var gridConfig={
	id : "datagrid",
	loadURL :'/flushlevel_query.do?1=1',
	exportURL :'/flushlevel_export.do?1=1' ,
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
	pageSize :  getLongPageSize(),
	remoteSort : true ,
	remotePaging:true,
	pageSizeList :   [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	onComplete:loadComplate
};
function getLongPageSize(){
	var size = getDispalyPageSize(1,1);
	if(size<25){
		size = 25;
	}
	return size;
}
var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

var firstInit=true;
//gird回调函数
function loadComplate(){
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 1 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	
	//判断是否查询到数据
	judgeData(datagrid);
	
	
	//初始化grid
	initGridInfo(obj);

	if(firstInit==true){
		//var pageSize=getDispalyPageSize(1,1);
		var pageSize=25;
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
	var pageinfo = datagrid.getPageInfo();
	pageinfo.pageNum = 1;
	datagrid.setPageInfo(pageinfo);
	//查询条件
	timeLevel_search = $("#timeLevel_search option:selected").val();
	time_search = $('#time_search').attr("value");
	//传递给后台参数
	var param={
		timeLevel_search:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
		time_search:time_search
	};
	 
	GT.$grid('datagrid').query( param );
}



//初始化查询时间
$(document).ready(function(){
	$('#time_search').attr('value',time_search);
});

//时间改变事件
function changeTimeLevel(){
	$('#time_search').attr("value","");
}
//时间选择事件
function selectTime(){
	var tls = $("#timeLevel_search option:selected").val();
	switch(tls)
	   {
	   case '0':
	   	 new WdatePicker({dateFmt:'yyyy-MM-dd'});
	     break;
	   case '1':
	   	 new WdatePicker({dateFmt:'yyyy-MM'});
	     break;
	 }
}



//初始化grid工具栏
$(document).ready(function(){
	 //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	  
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，该部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和数量应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('BTN_IMPORT',permflag);
	
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
           //参数
			var params={		
				timeLevel_search:timeLevel_search,//时间粒度  1:小时 2:天 3:周 4:月
				time_search:time_search
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='流量分层';//
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
});



$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}


</script>


