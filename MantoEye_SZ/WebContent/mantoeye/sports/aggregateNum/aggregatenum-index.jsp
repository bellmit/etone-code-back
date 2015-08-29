<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>集团号码</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
				<script src="/js/nav.js">
</script>
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
									<tr valign="top" style="font-size: 1px;">
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
													<!-- 	<td class="condition_value" id="dataType_search_td_id">
														<input type="radio" name='radio'
															style="width: 20px; border: 0px;"
															onclick='showDataType(1)' checked />
														GPRS
														<input type="radio" name='radio'
															style="width: 20px; border: 0px;"
															onclick='showDataType(2)' />
														TD
													</td> -->
													<td class="condition_name" style="display: none">
														时间粒度：
													</td>
													<td class="condition_value" style="display: none">
														<select name="timeLevel_search" id="timeLevel_search"
															onchange="changeTimeLevel()" style="height: 21px">
															<option value="1" selected>
																小时
															</option>
															<option value="2" >
																天
															</option>

														</select>
													</td>
													<td class="condition_name">
														时间跨度：
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectStartTime()" id="startTime_search"
															name="startTime_search" />
													</td>
													<td style="width: 5px;">
														&nbsp;到&nbsp;
													</td>
													<td class="condition_value" id="time_space_end">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectEndTime()" id="endTime_search"
															name="endTime_search" />
													</td>
	<td class="condition_name">
														集团名称：
													</td>
													<td class="condition_value">
														<input type="text" name="CustomerName" id="CustomerName"
															style="height: 17px; width: 115px" />
													</td>
													<!-- 号码 -->
													<td class="condition_name">
														集团号码：
													</td>
													<td class="condition_value" id="number">
														<input type="text" style="display: block; height: 17px;"
															id="number_search" name="number_search" />
													</td>


													<td width="200px">
														<div id="mainbtn" style="margin-left: -6px">
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
												集团客户列表
											</div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- ------------------>
						<tr>
							<td>
								<div id="toolbar" style="height: 25px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container"></div>
								</div>
							</td>
						</tr>
						<!-- ------------>
						<tr>
							<td>
								<div id="toolbar" style="height: 25px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container"></div>
								</div>
							</td>
						</tr>
						<!----------- -->
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
//初始化查询时间
var nmMsisdn='';
var date=new Date();
var CustomerName="";
var timeLevel_search ='1';//查询时间粒度
date = date.valueOf();
date = date;
date1 = date - 24 * 60 * 60 * 1000 * 30;
date = new Date(date);
date1 = new Date(date1);

var today = date.getYear()
		+ '-'
		+ ''
		+ ((date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : ("0" + (date
				.getMonth() + 1))) + '-' + ''
		+ (date.getDate() > 9 ? date.getDate() : ("0" + date.getDate())); //查询开始时间

var last30today = date1.getYear()
		+ '-'
		+ ''
		+ ((date1.getMonth() + 1) > 9 ? (date1.getMonth() + 1) : ("0" + (date1
				.getMonth() + 1))) + '-' + ''
		+ (date1.getDate() > 9 ? date1.getDate() : ("0" + date1.getDate())); //查询开始时间		
var endTime_search = today;
var startTime_search = last30today;

var dsConfig= {
	//data : data1 ,
	uniqueField : 'id' ,
	fields :[
		{name : 'id'        },
		{name : 'dtUpdateTime'    },
		{name : 'vcNote'    },
		{name : 'nmMsisdn'      }
		
	]
};

function renderDataInit(value ,record,columnObj,grid,colNo,rowNo){
    var nmMsisdn = record.nmMsisdn+'';
	var s = nmMsisdn.substring(2,13);
	return s;
}

var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选",exportable:false },
		{ id : 'dtUpdateTime'     , header : "更新时间",exportnumber:true},
			{ id : 'vcNote'     , header : "集团名称",exportnumber:true},
		{ id : 'nmMsisdn' , header : "客户号码" ,exportnumber:true,headAlign:'right' ,align:'right'}
		
];

var gridConfig={
	id : "datagrid",
	loadURL : '/aggregatenum_query.do?1=1',
	exportURL : '/aggregatenum_export.do?1=1',
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
	obj.isCheckbox = true;//是否可选择
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
	startTime_search = $('#startTime_search').attr("value");
	endTime_search = $('#endTime_search').attr("value");
	CustomerName=$('#CustomerName').attr("value");
	if(startTime_search=='' || endTime_search=='' || startTime_search==null || endTime_search==null){
		alert('请选择时间跨度!');
		return ;
		
	}
	nmMsisdn = $('#number_search').attr("value");
	var param={
		nmMsisdn : nmMsisdn,
		startTime_search:startTime_search,//开始时间
		endTime_search:endTime_search,//结束时间
		CustomerName:CustomerName
	};

	GT.$grid('datagrid').query( param );
}
//重置查询条件
function reset(){
	$('#number_search').attr("value","");
	$('#startTime_search').attr("value","");
	$('#endTime_search').attr("value","");
	
}

/***************初始化toolbar***************************/
$(document).ready(function(){
  	  //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	  
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，该部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和数量应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('_BTN_ADD,_BTN_EDIT,_BTN_DEL',permflag);

      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '添加号码',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          			var v = showMMDialog('/aggregatenum_input.do','','650px','460px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '编辑号码',
          bodyStyle : 'edit',
          useable :  bflags[1],
          handler : function(){
          	var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		var id=getSelectedId('datagrid');
          		var obj = new Object();
          		var record = getRightRecord('datagrid',datagrid);
          		obj.value = record.nmMsisdn+'';
          		obj.CustomerName=record.vcNote+'';
				obj.id = record.id;
          			var v = showMMDialog('/aggregatenum_edit.do?id='+id,obj,'650px','460px');
          		reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '删除号码',
          bodyStyle : 'delete',
          useable : bflags[2],
          handler : deleteNmMsisdn
        },'-',
						{
							type : 'button',
							text : '导入号码',
							bodyStyle : 'new',
						    useable : bflags[0],
							handler : function() {
								var mm = showMMDialog(
										'/mantoeye/sports/Number_import.jsp', '号码上传',
										'540px', '300px');
								if (typeof (mm) != "undefined") {
									var flag = window.confirm(mm);
									if (flag) {
										var mmwidth = window.screen.availWidth;//屏幕宽度
										var ml = 378;
										var mt = 200;
										if (mmwidth == 1024) {
											ml = 275;
										}
										alert("正在上传号码……，请稍后一段时间再查看！");						
										savaUpFile();
						
									}
								}
							}

						},'-',{
          type : 'button',
          text : '导出号码',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
           //参数
			var params={		
				startTime_search:startTime_search,//开始时间
				endTime_search:endTime_search,//结束时间
					nmMsisdn : nmMsisdn,
					CustomerName:CustomerName
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='集团客户号码';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
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
//删除事件
function deleteNmMsisdn(){
    if(getSelectedCount('datagrid')<1){
       alert('请选择需要删除的号码!');
       return;
    }
	var ids = getSelectedKeys('datagrid');
	var flag = window.confirm("确定删除吗?");
	if(flag){
		$.ajax({
			type : "post",
						url : "aggregatenum_deleteMenu.do",
			data : {
				ids:ids
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


//时间选择事件
function selectStartTime(){
	   new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
}
function selectEndTime(){
	   new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
}

//初始化查询时间
$(document).ready(function() {
	$('#startTime_search').attr('value', startTime_search);
	$('#endTime_search').attr('value', endTime_search);
});

function savaUpFile() {
	$.ajax( {
		type : "post",
		url : "/sportcustomer_saveUpFile.do",
		data : {},
		success : function(xml) {
			query();
		},
		error : function() {
			alert('上传文件出错!');
		}
	});
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


