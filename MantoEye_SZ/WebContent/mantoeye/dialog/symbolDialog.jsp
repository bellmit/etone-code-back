<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务分布</title>
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
		<script type="text/javascript" src="/js/common.js"></script>
		<script src="/tools/jquery/jquery.treeview.js" type="text/javascript"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<!-- 导入业务选择复选框 -->
		<link rel="stylesheet" href="/mantoeye/dialog/dialog.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
		<script type="text/javascript" src="SymbolDialog.js"></script>
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
									<tr height="4px;" valign="top">
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
												style="margin-left: 1px" cellpadding="0px;boder="0">
												<tr valign="middle">
													<td style="width: 72px">
														<input type="radio" name="dataType" id="d_dataType1"
															value="0" checked
															style="width: 20px; border: 0px; background-color: transparent;" />
														GPRS
													</td>
													<td style="width: 72px">
														<input type="radio" name="dataType" id="d_dataType2"
															style="width: 20px; margin-left: 2px; border: none; background-color: transparent;" />
														TD
													</td>
													<td class="condition_name">
														时间粒度：
													</td>
													<td class="condition_value">
														<div class="select">
															<div>
																<select name="timeLevel" id="d_timeLevel"
																	style="height: 21px" onchange="selChange();">
																	<option value="1">
																		小时
																	</option>
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
															</div>
														</div>
													</td>
													<td class="condition_name">
														时间：
													</td>
													<td class="condition_value">
														<input type="text" class="Wdate" style="height: 16px;"
															onclick="preStartDate();" name="searchDateStart"
															value="${searchDateStart}" id="d_searchDateStart" />
														<input type="hidden" id="d_searchDateEnd" />
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
											<table style="margin-left: -1px">
												<tr valign="top">
													<td
														style="width: 72px; height: 22px; cursor: default; padding: 0px 1px 0px 10px; color: #ffffff; font-size: 11px;">
														<input type="radio" value="业务类型" checked
															name="businessType" id="d_businessType"
															onclick="selectBusiness(this);"
															style="width: 20px; border: 0px; background-color: transparent;" />
														测试测试
													</td>
													<td
														style="width: 72px; cursor: default; padding: 0px 1px 0px 10px; color: #ffffff; font-size: 11px;">
														<input type="radio" value="业务" name="businessType"
															onclick="selectBusiness(this);"
															style="width: 20px; border: none; background-color: transparent;" />
														业务
													</td>
													<td style="width: 10px"></td>
													<td height="22px;">
														<input type="text" value="------------请选择业务------------"
															id="business" onclick="showDialog()"
															style="width: 200px; height: 16px; display: block; border: 1px solid #FFDDEE; font-size: 11px; color: #163877;" />
														<input id="businessIds" type="text" value="" />
													</td>
													<td height="22px;">
														<select style="height: 21px" id="dialog_change_id" onchange="changeDialog();">
															<option value="1" selected>
																弹出框11
															</option>
															<option value="2">
																弹出框22
															</option>
														</select>
													</td>
													<!-- 
													<td height="22px;">
														<input type="text" value="------------请选择业务2------------"
															id="business22" onclick="showDialog2()"
															style="width: 200px; height: 16px; display: block; border: 1px solid #FFDDEE; font-size: 11px; color: #163877;" />
														<input id="businessIds22" type="text" value="" />
													</td>
													 -->
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
												业务分布图形
												<font color="red">( GPRS 小时 2009-08-20 20:00:00)</font>
											</div>
											<input type="hidden" value="" id="chartxmldata" />
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td height="230px" width="100%"
								style="padding: 15px 20px; background: #ffffff">
								<div id="imgarea1" style="width: 100%; height: 100%">
									<iframe name="frm1"
										src="/flash/ThreePieChart.html?getdata=js&canclick=true&dataflag=2"
										scrolling="no" id="frm1"
										style="HEIGHT: 230px; VISIBILITY: inherit; width: 100%; Z-INDEX: 1"
										marginwidth="1" marginheight="1" frameborder="0"
										align="middle"></iframe>
								</div>
								<div style="display: none" id="imgarea2"
									style="width: 100%; height: 100%">
									<iframe name="frm2"
										src="/flash/LineColumnChart.html?getdata=js&canclick=true&dataflag=1"
										scrolling="no" id="frm2"
										style="HEIGHT: 230px; VISIBILITY: inherit; width: 100%; Z-INDEX: 1"
										marginwidth="1" marginheight="1" frameborder="0"
										frameborder="0" align="middle">
									</iframe>
								</div>
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
												业务分布数据
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
var dataTypeValue=0;//数据类型标识 0GPRS业务
var timeLevel_search=2;//时间类型标示
var searchDateStart=new Date();//时间
searchDateStart=searchDateStart.valueOf();
searchDateStart=searchDateStart-24*60*60*1000;
searchDateStart=new Date(searchDateStart); 
var mm=searchDateStart.getMonth()+1;
searchDateStart=searchDateStart.getYear()+"-"+mm+"-"+searchDateStart.getDate();
var business;//业务
var businessType="业务类型";//业务类型

var dsConfig= {
	//data : data1 ,
	uniqueField : 'no' ,
	fields :[
		{name : 'fullDate'        },
		{ name : 'business'       },
		{ name : 'businessType'   },
		{name : 'upFlushMB'      },
		{name : 'downFlushMB'    },
		{name : 'totalFlushMB'    },
		{name : 'flushRate'    },
		{name : 'intImsis' },
		{name : 'visit' },
		{name : 'detail'    }
	]
};

function renderInit(value ,record,columnObj,grid,colNo,rowNo){
		var businessTypeOne = record.businessType.toString();
		var businessOne=record.business.toString();
		return '<table><tr><td><img class="handImg1" style="cursor: hand" src="/skin/Default/images/MantoEye/icon/space.png" alt="查看空间分布" onclick="openSapceDistribute(1,\''+businessOne+'\');" /></td>'
					+'</td><td></div><img class="handImg2" style="cursor: hand" src="/skin/Default/images/MantoEye/icon/space.png" alt="查看具体业务分布情况" onclick="openSapceDistribute(2,\''+businessTypeOne+'\');" /></td>'
					+'</tr></table>';
}
var colsConfig = [
		{ id : 'fullDate'      , header : "时间"    },
		{ id : 'business'      , header : "业务"    },
		{ id : 'businessType'      , header : "业务类型"    },
		{ id : 'upFlushMB'    , header : "上行流量(MB)"   },
		{ id : 'downFlushMB'     , header : "下行流量(MB)"    },
		{ id : 'totalFlushMB'  , header : "总流量(MB)"},
		{ id : 'flushRate'  , header : "流量占比(%)"},
		{ id : 'intImsis' , header : "用户数"   },
		{ id : 'visit' , header : "访问次数"   },
		{ id : 'detail'   , header : "操作" , sortable:false, 
			renderer:renderInit
		}
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/businessDistribute_query.do?1=1',
	exportURL :'/businessDistribute_export.do?1=1' ,
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
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
	toolbarContent : 'state' ,
	beforeSave : function(reqParam){
		//alert(GT.$json(reqParam) ) ;
		//GT.$grid('grid1').reload(true);
		//return false;
	},
	pageSize : 20 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
//	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

var hasinit = false;
var flag=0;//标示饼图还是柱状图
function loadComplate(){

	//设置宽度
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	if(width==1024){
		//设置列表的宽高
		datagrid.setSize(780,(totalRecords+3) * 22);
		$('#toolbar').css("width","780px");
	}else{
		datagrid.setSize(1055,(totalRecords+4) * 22);
		$('#toolbar').css("width","1045px");
	}
	
	//根据分辨率初始化grid列宽
	if(!columninit){
		if(width==1024){
			initColumnWidth(datagrid,780,1);
		}else{
			initColumnWidth(datagrid,1045,1);
		}
	}
	if(totalRecords==1){
		if(businessType=='业务类型'){
			datagrid.getColumn('business').hide();
			datagrid.getColumn('businessType').show();
		}else{
			datagrid.getColumn('business').show();
			datagrid.getColumn('businessType').hide();
		}
	}
	var date=$("#d_searchDateStart").val();
	var ld=$("#d_timeLevel option:selected").text();
	
	var title;
	var strdate;
	if(date==''){
		date=new Date();
		date=date.valueOf();
		date=date-24*60*60*1000;
		date=new Date(date); 
		var month=date.getMonth()+1;
		strdate=date.getYear()+'年'+''+month+'月'+''+date.getDate()+'日';
		$('#d_searchDateStart').val(date.getYear()+"-"+month+"-"+date.getDate());
	}else{
		strdate=date.toString();
	}	
	
	if(totalRecords>1){
			var chartxmldata;
				if(businessType=='业务类型'){
					title=strdate+' 各业务类型#FIELD#占比图';
					chartxmldata='<root><chart id="1" name="'+title+'" fields="流量|用户数|访问次数">';
					for(var i = 0 ; i<totalRecords;i++){
						var record = datagrid.getRecord(i);
						chartxmldata=chartxmldata+'<data label="'+record.businessType+'" total="'+record.totalFlush+'"  imsis ="'+record.intImsis+'" count="'+record.visit+'"/>';
						
					}
					document.getElementById("imgarea1").style["display"] = "block";
					document.getElementById("imgarea2").style["display"] = "none";
					$('.handImg1').each(function(){
						this.style["display"] = "none";
					});
					
					$('.handImg2').each(function(){
						this.style["display"] = "block";
					});
					datagrid.getColumn('business').hide();
					datagrid.getColumn('businessType').show();
					flag=0;
				}else{
					title=date+' '+ld+'各业务流量用户对比图';
					chartxmldata='<root><chart id="2" name="'+title+'" fields="流量|用户数">';
					for(var i = 0 ; i<totalRecords;i++){
						var record = datagrid.getRecord(i);
						chartxmldata=chartxmldata+'<data shortlabel="'+record.business+'" label="'+record.business+'" total="'+record.totalFlush+'"  imsis ="'+record.intImsis+'" count="'+record.visit+'"/>';
					}
					
					document.getElementById("imgarea1").style["display"] = "none";
					document.getElementById("imgarea2").style["display"] = "block";
					$('.handImg1').each(function(){
						this.style["display"] = "block";
					});
					
					$('.handImg2').each(function(){
						this.style["display"] = "none";
					});
					
					flag=1;
					datagrid.getColumn('businessType').hide();
					datagrid.getColumn('business').show();
				}
				chartxmldata=chartxmldata+'</chart></root>';
				document.getElementById("chartxmldata").value=chartxmldata;
				if(hasinit){
					if(businessType=='业务类型'){
						changPieData();
					}else{
						changLineData();
					}
				}
			}
}
//// 查询表单的查询函数 ////
function query() {
	timeLevel_search = $("#d_timeLevel option:selected").val();
	if(document.getElementById("d_dataType1").checked==true){
		dataTypeValue=0;
	}else{
		dataTypeValue=1;
	}
	business=$("#business").val().trim();
	if(businessType=='业务'){
		if(business==''){
			alert('请最少选择一个业务作为查询条件!');
			return;
		}
	}
	searchDateStart=$("#d_searchDateStart").val().trim();
	if(searchDateStart==''){
		alert('请选择查询时间!');
			return;
	}
	var param={
		searchDateStart : searchDateStart,
		timeLevel :timeLevel_search,
		business : business,
		businessType : businessType,
		dataType :dataTypeValue
	};
	GT.$grid('datagrid').query( param );
}


$(document).ready(function(){
	var $inputObj=$("input[type='text']").not( $(".Wdate")[0] );
	$inputObj.each(function(){
		$obj=$(this);
		$obj.bind("focus",function(){
			$(this).css("background","#F5E5E1");
		});
		$obj.bind("blur",function(){
			$(this).css("background","#ffffff");
		});
	})
})



function initChartData(param){
	hasinit=true;
	var xml =document.getElementById("chartxmldata").value;			
	return xml;
}
function changPieData(){
	var xml = document.getElementById("chartxmldata").value;
	document.frm1.ThreePieChart.jsChangeData(xml);
}

function changLineData(){
	var xml = document.getElementById("chartxmldata").value;
	//document.frm2.LineColumnChart.jsChangeData(xml);
}

function initChartWidth(){
 	var width=window.screen.availWidth;
	var availW=1014;
	var availH=230;
	if(width==1024){
		availW=765;
	}else{
		availW=1014;
	}
 	return availW+"|230";
}
function chartItemClick(label){
	var obj =new Object();
	if(flag==0){
		obj.dataTypeValue = dataTypeValue;
		obj.searchDateStart = searchDateStart ;
		obj.timeLevel_search = timeLevel_search ;
		obj.businessTypeName = label;
	 	var value = window.showModalDialog("/mantoeye/data_analyse/business_in_businesstype.jsp",obj,"dialogWidth=1000px;dialogHeight=600px;location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}else{
		obj.dataTypeValue = dataTypeValue;
		obj.searchDateStart = searchDateStart ;
		obj.timeLevel_search = timeLevel_search ;
		obj.businessName = label;
		var value = window.showModalDialog("/mantoeye/data_analyse/business_in_space.jsp",obj,"dialogWidth=1000px;dialogHeight=600px;location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
}

function openSapceDistribute(num,bis){
	var obj =new Object();
	if(num==2){
		obj.dataTypeValue = dataTypeValue;
		obj.searchDateStart = searchDateStart ;
		obj.timeLevel_search = timeLevel_search ;
		obj.businessTypeName = bis;
	 	var value = window.showModalDialog("/mantoeye/data_analyse/business_in_businesstype.jsp",obj,"dialogWidth=1000px;dialogHeight=600px;location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}else{
		obj.dataTypeValue = dataTypeValue;
		obj.searchDateStart = searchDateStart ;
		obj.timeLevel_search = timeLevel_search ;
		obj.businessName = bis;
		var value = window.showModalDialog("/mantoeye/data_analyse/business_in_space.jsp",obj,"dialogWidth=1000px;dialogHeight=600px;location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
}
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
            //alert('导出');
          }
        }]
      });
	  toolbar.render();
    });
    
    function selectBusiness(bis){
    	businessType=$(bis).val();
    	if(businessType=='业务'){
    		document.getElementById("business").style["display"]="block";
    	}else{
    		document.getElementById("business").style["display"]="none";
    	}
    }
    
    	//重置查询条件
function reset(){
	$('#d_searchDateStart').attr("value","");
	$("#d_timeLevel").get(0).selectedIndex=0;
	$('#d_dataType1').attr("checked","true");//设置radio默认值
	$("#d_businessType").get(0).selectedIndex=0;//业务类型
	document.getElementById("business").style["display"]="none";
	$("#business").attr("value","");//区域维度
}
    
</script>

<!-- 复选框JS -->
<script>
	var dialog = null;
	function showDialog(){
		if(dialog == null){
		  dialog = new SymbolDialog({
	        renderTo : 'business',//点击触发对话框ID
	        hiddenTo:'businessIds',//页面与对话框参数传递
			id:'businessDialog',//对话框ID
			title:'终端选择对话框',//对话框标题
			typeSearchName:'品牌',
			modelSearchName:'型号',
			url:'terminalDialog_initTerminalDialogData.do',
			hasInit:false,//再次加载服务器端数据标识
			params: {//URL传参
				p1:1,
				p2:2
			}
	      });
		  dialog.init();//初始化页面DOM对象
		  dialog.loadData();//加载后台数据
		  dialog.display();//显示
		}else{
		  dialog.display();//显示(第N次以后只显示对话框即可,提高效率)
		}
	}
	
	function changeDialog(){
		//var v = $("#dialog_change_id option:selected").val();
		//dialog.url='terminalDialog_initTerminalDialogData.do';
		//dialog.params.p1=v;//改变参数
		//dialog.hasInit=true;//重新加载后台数据标识
		//dialog.loadData();//加载后台数据
		//dialog.display();
		//dialog.block();
		$('#businessDialog').remove();
		dialog = null;
	}
	/*
	var dialog2 = null;
	function showDialog2(){
		if(dialog2 == null){
		  dialog2 = new SymbolDialog({
	        renderTo : 'business22',
	        hiddenTo:'businessIds22',
			id:'businessDialog222',
			title:'终端选择对话框',
			typeSearchName:'品牌',
			modelSearchName:'型号',
			url:'terminalDialog_initTerminalDialogData.do'
	      });
		  dialog2.init();//初始化页面DOM对象
		  dialog2.loadData();//加载后台数据
		  dialog2.display();//显示
		}else{
		  dialog2.display();
		}
	}
	*/
</script>