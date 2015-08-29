<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>拨测结果按号码查看流量</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin//Default/css/maincontent.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
			<script type="text/javascript" src="/js/common_grid.js"></script>
			<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<base target=”_self”>
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
							<td height="5px" bgcolor="#D3E0F2" width="100%">
							<input type="hidden" value="${taskid }" name ="taskid"/>
							</td>
						</tr>
						
						<tr>
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr>
										<td width="4px" height="24px;">
											<img
												src="/skin/Default/images/MantoEye/maincontent/lefttitle.gif" />
										</td>
										<td width="100%"
											style="background: url(/skin/Default/images/MantoEye/maincontent/middletitle.gif) repeat-x; padding-left: 10px; font-size: 12px; font-weight: bold; color: #FFFFFF; vertical-align: middle;">
											拨测流量查看
										</td>
										<td width="4px">
											<img
												src="/skin/Default/images/MantoEye/maincontent/righttitle.gif" />
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr>
							<td>
							<div id="toolbar" style="height: 25px;display:none;"></div>
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

<script>
/**
脚本不出错
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
var taskid = document.getElementById("taskid").value;
//重置查询条件
function reset(){
	//$('#d_searchDateStart').attr("value","");
	//$("#d_timeLevel").get(0).selectedIndex=1;
	//$('#d_raittype').attr("checked","true");//设置radio默认值
}

var dsConfig= {
	//data : data1 ,
	uniqueField : 'nmMsisdn' ,
	fields :[
		{name : 'nmMsisdn'        },
		{name : 'taskid'        },
		{name : 'nmUpFlush'        },
		{name : 'nmDownFlush'        },
		{name : 'nmTotalFlush'      }
	]
};
var colsConfig = [
		{ id : 'nmMsisdn'      , header : "号码"    ,width:100 },
		{ id : 'nmUpFlush'      , header : "上行流量(KB)"   ,width:120  ,renderer:renderFormatDataInit2 },
		{ id : 'nmDownFlush'      , header : "下行流量(KB)"   ,width:120  ,renderer:renderFormatDataInit2  },
		{ id : 'nmTotalFlush'      , header : "总流量(KB)"   ,width:120 ,renderer:renderFormatDataInit2 },
		{ id : 'detail'   , header : "查看" , sortable:false ,width:55 , 
			renderer:renderManager
		}
];
function renderManager(value ,record,columnObj,grid,colNo,rowNo){
	var msisdn = record.nmMsisdn.toString();
	var taskid = record.taskid.toString();
	return '<table><tr><td><img style="cursor: hand"  src="/skin/Default/images/MantoEye/icon/business.gif" alt="分业务查看" onclick="openBusiFlush(\''+taskid+'\',\''+msisdn+'\');" /></td>'
			+'</tr></table>';
}
function openBusiFlush(taskid,msisdn){	
	//alert(taskid+"--"+msisdn);
	showMDialog('/dataGetterBusiness_openbusiflush.do?taskid='+taskid+'&msisdn='+msisdn,'','540px','420px');		
	//document.location.href='/dataGetterBusiness_openbusiflush.do?taskid='+taskid+'&msisdn='+msisdn;
}
function showMDialog(url,title,width,height){
    var v = window.showModalDialog(url,title,'dialogHeight:'+height+';dialogWidth:'+width+';scrollbars:no;resizable:yes;help:no;status:no;center:yes;');     
	return v;
}
var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/dataGetterBusiness_queryMsisdnFlush.do?taskid='+taskid,
	exportURL :'/dataGetterBusiness__exportMsisdnFlush.do?taskid='+taskid ,
	dataset : dsConfig ,
	columns : colsConfig ,
	resizable : false,
	showGridMenu : false,
	width:520,
	height:360,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom',
	toolbarContent : '| state' ,
	pageSize : 10 ,
	remoteSort : false ,
//	pageSizeList : [10,20,50,100],
//	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var hasinit = false;
function loadComplate(){
	//grid列表初始化信息
	var obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//alert("----");
	//判断是否查询到数据
	judgeThisData(datagrid);
}
//判断grid查询是否有数据，没有则提示
function judgeThisData(datagrid){
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var tdWidth="520px"
	if(typeof(datagrid.getRecord(0))=="undefined"){
		var $o = $('#'+datagrid.id+'_bodyDiv').find('table').eq(0).find('tbody').eq(0);//获取对象
		$o.find('tr').eq(0).remove();//由于控件存在bug，所以需要删除第一行
		var $tr = $('<tr><td height="20px" width='+tdWidth+' style="font-size:14px" align="center" bgcolor="#EEEEEE">没有数据显示!</td></tr>');
		$tr.appendTo($o);
	}
}
//// 查询表单的查询函数 ////
function query() {
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
//初始化toolbar
$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : exportToXls
        }]
      });
	  toolbar.render();
    });
 function exportToXls(){
	//导出
	var fileObj = new Object();
	fileObj.fileName='拨测流量列表';//文件名称
	fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
	fileObj.gridObj = datagrid;//当前grid对象
	fileObj.params = params;
	exportFile(fileObj);
 }

</script>


