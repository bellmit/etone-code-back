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
		<script type="text/javascript" src="/js/common.js"></script>
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
												<td width= "4px">
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
													<td class="condition_name" style='width:100px;'>
														请选择用户类型:
													</td>
													<td class="condition_value">
														<div class="select">
															<div>
																<select name="isBlackUser" id="d_isBlackUser"
																	style="height: 21px">
																	<option value="1">
																		全部
																	</option>
																	<option value="2" >
																		非黑名单用户
																	</option>

																	<option value="3">
																		黑名单用户
																	</option>
																</select>
															</div>
														</div>
													</td>
													<td class="condition_name">
														时间:
													</td>
													<td class="condition_value" id="time_space_start">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectStartTime()" id="startTime_search"
															name="startTime_search" />
													</td>
													<td style="width: 5px;" style="display:none">
														&nbsp;到&nbsp;
													</td>
													<td class="condition_value" id="time_space_end" style="display:none">
														<input type="text" class="Wdate"
															style="display: block; height: 17px;"
															onclick="selectEndTime()" id="endTime_search"
															name="endTime_search" />
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
																	<a href="javascript:reset();" title="重置"><span>重置</span> </a>
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
								<table cellspacing="0" cellpadding="0" border="0" width="100%" class="menubar">
									<tr valign="top">
												<td width="4px" height="24px">
													<div class="lefttitle"></div>
												</td>
												<td width="100%" height="24px" >
													<div class="middletitle">大流量用户数据
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
								<div id="descriFrame" style="display:none;position:absolute;left:300px;top:200px;z-index:100;width:300px;height:100px;border:solid 0px #76B1F0;">
									<div style="margin:0px;padding0px;width:300px;height:20px;background: url(/skin/Default/images/MantoEye/topbar.gif) no-repeat">
									</div>
									<div style="margin:0px;padding0px;width:298px;height:80px;border:solid 1px #99ABAF;background:#E2ECF9;">
										<table cellpadding="0" cellspacing="0px" border="0" style="width:100%;height:100%">
										<tr>
											<td height="20px"></td><td></td>
										</tr>
										<tr>
											<td style="font-size:11px;width:100px;color:blue;" align="center">请输入描述:</td>
											<td><input type="text" id="descInput" style="width:190px;height20px;border:solid 1px #99ABAF;font-size:11px;"/></td>
										</tr>
										<tr>
										<td></td>
										<td align="center">
											<div id="mainbtn">
															<ul>
																<li>
																	<a href="javascript:closeFrame();" 　title="确定"><span>确定</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:cancleFrame();" title="取消"><span>取消</span> </a>
																</li>
															</ul>

														</div>
										</td>
										</tr>
									</table>
									</div>
								</div>
								<div id="toolbar" style="height: 25px"></div>
								<div class="gt-panel" style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container"></div>
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

<script type="text/javascript">
var blackUserId="";
var keys='';
var descrite="黑名单用户";
var dsConfig= {

	uniqueField : 'id' ,
	fields :[
		{name : 'time'        },
		{name : 'imsi'      },
		{name : 'imei'      },
		{name : 'vcUserBelong'      },
		{name : 'cellName'      },
		{name : 'branchName'      },
		{name : 'strMissdn'        },
		{name : 'isBlackUser'      },
		{name : 'upFlushMB'      },
		{name : 'downFlushMB'      },
		{name : 'totalFlushMB'      }
	]
};

function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var date = record.time.toString();
	var msisdn = record.msisdn;
	var isBlackUser = record.isBlackUser.toString();
	var tflush = record.totalFlushMB;
	var uflush = record.upFlushMB;
	var dflush = record.downFlushMB;
	//if(isBlackUser!='是'){
		return '<table><tr><td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/address.png" alt="TOP5地址分布" onclick="openDistribute(1,\''+date+'\',\''+msisdn+'\','+tflush+','+uflush+','+dflush+');" /></td>'
					+'</td><td></div><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/useragent.png" alt="UserAgen分布" onclick="openDistribute(2,\''+date+'\',\''+msisdn+'\','+tflush+','+uflush+','+dflush+');" /></td>'
					+'</td><td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/apn.png" alt="APN分布" onclick="openDistribute(3,\''+date+'\',\''+msisdn+'\','+tflush+','+uflush+','+dflush+');" /></td>'
					+'</td><td></div><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/contenttype.png" alt="ContentType分布" onclick="openDistribute(4,\''+date+'\',\''+msisdn+'\','+tflush+','+uflush+','+dflush+');" /></td>'
					+'</td><td></div><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/street.png" alt="TOP5街道办分布" onclick="openDistribute(5,\''+date+'\',\''+msisdn+'\','+tflush+','+uflush+','+dflush+');" /></td>'
					+'</td><td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/time.png" alt="TOP5小时点分布" onclick="openDistribute(6,\''+date+'\',\''+msisdn+'\','+tflush+','+uflush+','+dflush+');" /></td></tr></table>'
	/*}else{
		return '';
	}*/
}

function renderInitTwo(value ,record,columnObj,grid,colNo,rowNo){
	var isBlackUser = record.isBlackUser.toString();
	var msisdn = record.msisdn;
	var time=record.time;
	if(isBlackUser=='是'){
		return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/yes.gif" alt="点击查看黑名单详情" onclick="openBlackUser(\''+msisdn+'\',\''+time+'\');" />'
				
	}else{
		return '<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/no.gif" alt="非黑名单用户"  />'
}
	}
function rendererDate(value ,record,columnObj,grid,colNo,rowNo){
	return (value+"").split(" ")[0];
}	
function rendererTrend(value ,record,columnObj,grid,colNo,rowNo){
	return '<a href ="javascript:openFlushTrend('+value+')"  alt="查看流量走势">'+value+'</a>';
}	
var searchDateStart="";
var searchDateEnd="";
function openFlushTrend(msisdn){
	var obj =new Object();
	obj.msisdn = msisdn;
	obj.searchDateStart = "";
	obj.searchDateEnd = "";
	var value = window.showModalDialog("/mantoeye/valueset/big_flush_trend.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	
}
var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, _checkType:'radio', 	frozen : false , filterable : false, header: "id", title: "全选" ,exportable:false, fieldName : 'id'},
		{ id : 'time'      , header : "时间"  ,renderer:rendererDate ,  sortable:false},
		{ id : 'imsi'    , header : "IMSI"  ,  sortable:false },
		{ id : 'strMissdn'      , header : "号码",  sortable:false ,renderer:rendererTrend},
		{ id : 'imei'    , header : "IMEI" ,  sortable:false },
		{ id : 'vcUserBelong', header : "用户归属" ,  sortable:false },
		{ id : 'cellName'    , header : "最常用小区"  ,sortable:false },
		{ id : 'branchName'    , header : "最常用区域"  ,sortable:false },	
		{ id : 'isBlackUser'    , header : "是否黑名单用户" ,sortable:false,renderer:renderInitTwo  },
		{ id : 'upFlushMB'      , header : "上行流量(MB)",headAlign:'right' ,align:'right'  ,exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'downFlushMB'    , header : "下行流量(MB)" ,headAlign:'right' ,align:'right',exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'totalFlushMB'    , header : "总流量(MB)" ,headAlign:'right' ,align:'right',exportnumber:true ,renderer:renderFormatDataInit2 },
		{ id : 'detail'   , header : "操作" ,sortable:false,width:160,exportable:false,
			renderer:renderInit
		}
];


var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/bigflushdisplay_query.do?1=1',
	exportURL :'/bigflushdisplay_export.do?1=1',
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
	toolbarContent : 'nav | goto | pagesize | state' ,
	beforeLoad:checkLogon,
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
function loadComplate(){
		var  obj = new Object ();//使用对象传参,以防以后修改
		obj.datagrid = datagrid;
		obj.hideColumn = 0 ;//隐藏的列数目
		obj.isCheckbox = true;//是否可选择
		obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
		obj.otherWidth =160;
		
	//设置宽度
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
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
	
	var date=$("#startTime_search").val();
	if(date==''){
		date=new Date();
		date=date.valueOf();
		date=date-24*60*60*1000;
		date=new Date(date); 
		var month=date.getMonth()+1;
		$('#startTime_search').val(date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())));
		$('#endTime_search').val(date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())));
	}
	searchDateStart = $('#startTime_search').val();
	searchDateEnd = $('#endTime_search').val();
	if(totalRecords>1){
		var $ck=$("input[type='checkbox']");
		for(var i = 0 ; i<totalRecords;i++){
				var record = datagrid.getRecord(i);
				if(record.isBlackUser.toString()=="是"){
					$($ck.get(i+1)).attr("checked",false);
					$($ck.get(i+1)).attr("disabled",true);
					$($ck.get(i+1)).css("display","none");
				}
		}
	}
	
}

var isBlackUser="1";

var permflag = '<%=btnperm%>';
var bflags = initBtnPerm('_BTN_BLACK',permflag);
var addBtn =  bflags[0];

//// 查询表单的查询函数 ////
function query() {
		//重置分页数据
	datagrid.setTotalRowNum(-1);
	isBlackUser=$("#d_isBlackUser").val();
	searchDateStart=$('#startTime_search').val();
	//searchDateEnd = $('#endTime_search').val();
	searchDateEnd=$('#startTime_search').val();
	if(searchDateStart==''){
		alert('请选择查询时间');
		return;
	}
	var param={
		 searchDateStart: searchDateStart,
		  searchDateEnd: searchDateEnd,
		 isBlackUser:isBlackUser
	};
	
	if('3' == isBlackUser){
		datagrid.getColumn("chk").hide();
        addBtn = 'F';
	}else{
		datagrid.getColumn("chk").show();
		addBtn =  bflags[0];
	}
	
	getTools();
	
	GT.$grid('datagrid').query( param );
}

$(document).ready(function(){
	var $inputObj=$("input[class!='Wdate']");
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


function reset(){
	$('#startTime_search').val("");
	$('#endTime_search').val("");
	$('#d_isBlackUser').get(0).selectedIndex=0;
}

function closeFrame(){
	document.getElementById("descriFrame").style["display"]= "none";
	if(document.getElementById("descInput").value!=""){
		descrite=document.getElementById("descInput").value;
	}
	addBlackUser();
}


function cancleFrame(){
	document.getElementById("descriFrame").style["display"] = "none";
}

var w="1000px";
var h="600px";
if(width==1024){
	w="815px";
}

//列表链接操作
function openDistribute(flag,date,msisdn,tflush,uflush,dflush){
var obj =new Object();
obj.totalflush = tflush;
obj.upflush = uflush;
obj.downflush = dflush;
	if(flag==1){
		obj.name='url';
		obj.msisdn=msisdn;
		obj.date=date;
		var value = window.showModalDialog("/mantoeye/valueset/url.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}else if(flag == 2){
		obj.name='userAgent';
		obj.msisdn=msisdn;
		obj.date=date;
		var value = window.showModalDialog("/mantoeye/valueset/userAgent.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}else if(flag==3){
		obj.name='apn';
		obj.msisdn=msisdn;
		obj.date=date;
		var value = window.showModalDialog("/mantoeye/valueset/top5Apn.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}else if(flag==4){
		obj.name='contentType';
		obj.msisdn=msisdn;
		obj.date=date;
		var value = window.showModalDialog("/mantoeye/valueset/contentType.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}else if(flag == 5){
		obj.name='street';
		obj.msisdn=msisdn;
		obj.date=date;
		var value = window.showModalDialog("/mantoeye/valueset/street.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}else{
		obj.name='hour';
		obj.msisdn=msisdn;
		obj.date=date;
		var value = window.showModalDialog("/mantoeye/valueset/hour.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
	}
}

function openBlackUser(msisdn,time){
	var obj = new Object();
		obj.name='黑名单管理';
		obj.id='BIG_BLACK_MANA';
		obj.target = '/mantoeye/valueset/blackuser.jsp?permId=BIG_BLACK_MANA&date='+msisdn;
		parent.newTab(true,obj,msisdn);
}

//时间选择事件
function selectStartTime(){
	new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
}
function selectEndTime(){
	new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
}

$(document).ready(function(){
	getTools();
    });
    
    function getTools(){
    	$('#toolbar').html('');
        var toolbar = new Toolbar({
          renderTo : 'toolbar',
  		//border: 'top',
          items : [
          {
            type : 'button',
            text : '添加为黑名单用户',
            bodyStyle : 'new',
            useable : addBtn,
            handler : function(){
           	 keys = getSelectedKeys('datagrid');
            	var count = getSelectedCount('datagrid');
            	if(count<1){
            		alert("请选择一条记录！");
            	}else{
            		
            		if(window.confirm("你确定要将所选择用户添加为黑名单用户吗?")){
            			document.getElementById("descriFrame").style["display"] = "block";
            		}
            	}
            }
          },'-',
          {
            type : 'button',
            text : '导出',
            bodyStyle : 'xls',
            useable : 'T',
             handler : function(){
             	if(searchDateStart==''){
  				alert('请选择查询时间');
  				return;
  			}
             //参数
  			var params={
  				searchDateStart: searchDateStart,
  				searchDateEnd: searchDateStart,
  				isBlackUser:isBlackUser
  			};
  			//exportURL :'/bigflushdisplay_exportCsv.do?1=1',
  			var ttc = datagrid.getTotalRowNum()
             	if(ttc>65500){
             		alert('结果数据太多，请重新设置查询条件!');
             		return;
             	}		
  			//导出
  			var fileObj = new Object();
  			fileObj.fileName='大流量用户数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
  			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
  			fileObj.gridObj = datagrid;//当前grid对象
  			fileObj.params = params;
  			exportFile(fileObj);
            }
          }]
        });
  	  toolbar.render();
    }
    
    function addBlackUser(){
    		$.ajax({
						type : "post",
						url : "/bigflushdisplay_saveFromBigFlushUser.do",
						data : {
							keys:keys,
							descrite:descrite
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


