<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>黑名单管理</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<link type="text/css" rel="stylesheet"
			href="../../skin//Default/css/maincontent.css" />
		<!-- 请根据语言和系统编码,自行选择引入的 gt_msg_...js 文件 (默认的msg文件为UTF-8编码) -->
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script src="/js/nav.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
	</head>

	<%-- <script type="text/javascript" src="/js/jquery.corner.js"></script>
<script type="text/javascript">
	 $('#readyTest').corner();
    
    $(function(){
	
        $('div.inner').wrap('<div class="outer"></div>');
        $('p').wrap("<code></code>");
		$("#query_condition").corner("round 8px").parent().css('padding', '4px').corner("round 10px")
       
    });
    
	
</script> --%>
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
													<td class="condition_name">
														号码：
													</td>
													<td class="condition_value">
														<input name="msisdn" type="text" id="d_msisdn"
															style="height: 16px;" />
													</td>
													<td>
														<span style="font-size: 14px; color: red;">*</span><span
															style="font-size: 11px; color: #163877">【号码支持模糊匹配】</span>
													</td>
													<td id="spaceTd" width="*"></td>
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
												黑名单用户数据
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
				<td colspan="2" height="3px"></td>
			</tr>
		</table>
	</body>
</html>

<script type="text/javascript">
var msisdn='';
var str=window.location.href; 
var args=str.split("date=");
if(args[1]!=undefined){
	msisdn=args[1];
}

var dsConfig= {

	//data : data1 ,

uniqueField : 'id' ,

	fields :[
		{name : 'id'        },
		{name : 'msisdn'      },
		{name : 'imsi'      },
		{name : 'imei'      },
		{name : 'strFirstTime'        },
		{name : 'describe'      },
		{name : 'strLastTime'      },
		{name : 'lastFlushMB'      },
		{name : 'overDay'      },
		{name : 'preMonthMB'      }
	]
};

function renderKillNull(value ,record,columnObj,grid,colNo,rowNo){
	if(value== null||value== ''||value=='0'){
		return "--";
	}else{
		return value;
	}
}

var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, _checkType:'radio',exportable:false, 	frozen : false , filterable : false, header: "id", title: "全选" , fieldName : 'id'},
		{ id : 'msisdn'      , header : "号码" ,sortable:false ,renderer:renderKillNull  },
		{ id : 'imsi'    , header : "IMSI"  ,sortable:false , renderer:renderKillNull},
		{ id : 'imei'    , header : "IMEI"  ,sortable:false, renderer:renderKillNull },
		{ id : 'describe'      , header : "描述" ,sortable:false  },
		{ id : 'strFirstTime'    , header : "初定时间" ,sortable:true  },		
		{ id : 'strLastTime'    , header : "最后核定时间" ,sortable:true },
		{ id : 'lastFlushMB'      , header : "最后核定流量(MB)" ,sortable:true ,headAlign:'right' ,align:'right' ,renderer:renderFormatDataInit2  },
		{ id : 'overDay'    , header : "超阀值天数" ,sortable:true ,headAlign:'right' ,align:'right' },
		{ id : 'preMonthMB'    , header : "上月流量(MB)" ,sortable:true ,headAlign:'right' ,align:'right' ,renderer:renderFormatDataInit2 }
		
];

var gridConfig={

	id : "datagrid",
	
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/blackuser_query.do?msn='+msisdn,
	exportURL :'/blackuser_export.do?1=1' ,
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
	beforeLoad:checkLogon,
	toolbarContent : 'nav | goto | pagesize | state' ,
	pageSize :getDispalyPageSize(0,1) ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	remoteSort : true ,
	defaultRecord :  { no : 111 , name :'', age : '', gender : 'U' , english : 50 , math :50 },
	onComplete:loadComplate

};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var firstInit=true;
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = true;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度

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
	
	if(msisdn!='1&permId'){
		$('#d_msisdn').val(msisdn);
	}
}


//// 查询表单的查询函数 ////
function query() {
	//重置分页数据
	datagrid.setTotalRowNum(-1);
//	msisdn = '';
	msisdn=$('#d_msisdn').val();
//	alert(msisdn+"");
	var param={
		dmsn:msisdn,
		dflag:1
	};
	GT.$grid('datagrid').query( param );
}

function reset(){
	$('#d_msisdn').val("");
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
          text : '添加',
          bodyStyle : 'new',
          useable :  bflags[0],
          handler : function(){
         	var mm= showNRMDialog('/blackuser_input.do?flag=0','添加黑名单用户','500px','320px');
         	 if(mm=='add'){
         	 	query();
         	 }
          }
        },'-',{
          type : 'button',
          text : '修改',
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
          		var mmm=showNRMDialog('/blackuser_edit.do?id='+id,'编辑用户','500px','350px');
          		if(mmm=='edit'){
         	 		query();
         	 	}
          	}
          }
        },'-',{
          type : 'button',
          text : '删除',
          bodyStyle : 'delete',
           useable :  bflags[2],
          handler :deleteUser
        },'-',{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
           handler : function(){
           //参数
			var params={
					dmsn:msisdn,
					dflag:1
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='黑名单数据';//('+startTime_search+'至'+endTime_search+')';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      });
	  toolbar.render();
    });
    
    function deleteUser(){
		var count = getSelectedCount('datagrid');
		var keys = getSelectedKeys('datagrid');
		if(count<1){
          		alert("请选择要删除的记录！");
         }else{
          	if(window.confirm("你确定要删除所选择数据吗?")){
          			$.ajax({
						type : "post",
						url : "/blackuser_deleteUser.do",
						data : {
							keys:keys
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


