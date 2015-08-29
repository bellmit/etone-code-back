<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端配置表</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<script src="/js/nav.js"></script>
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
												cellpadding="0px;boder="0">
												<tr valign="middle">											
													<td class="condition_name">
														终端识别前缀：
													</td>
													<td class="condition_value">
														<input type="text" id="vcTerminalTac" value="" />
													</td>
													<td class="condition_name">
														终端品牌：
													</td>
													<td class="condition_value">
														<input type="text" id="vcTerminalBrand" value="" />
													</td>
													<td class="condition_name">
														终端型号：
													</td>
													<td class="condition_value">
														<input type="text" id="vcTerminalName" value="" />
													</td>
												
													<td width="200px">
														<div id="mainbtn">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
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
												终端配置表数据
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
var vcTerminalTac='';
var vcTerminalBrand='';
var vcTerminalName='';

var dsConfig= {
	//data : data1 ,
	uniqueField : 'nmTerminalId' ,
	fields :[
		{name : 'nmTerminalId'        },
		{name : 'vcTerminalTac'    },
		{name : 'vcTerminalBrand'    },
		{name : 'vcTerminalName'      }
		
	]
};

function renderDataInit(value ,record,columnObj,grid,colNo,rowNo){
    var nmMsisdn = record.nmMsisdn+'';
	var s = nmMsisdn.substring(2,13);
	return s;
}

var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选",exportable:false },
		{ id : 'vcTerminalTac'     , header : "终端识别前缀",exportnumber:true},
		{ id : 'vcTerminalBrand' , header : "终端品牌" ,exportnumber:true},
		{ id : 'vcTerminalName' , header : "终端型号" ,exportnumber:true}
		
];

var gridConfig={
	id : "datagrid",
	loadURL :'/terminalConfigure_query.do?1=1',
	exportURL :'/terminalConfigure_export.do?1=1' ,
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

function getLongPageSize() {
	var size = getDispalyPageSize(1, 1);
	if (size < 25) {
		size = 25;
	}
	return size;
}
var datagrid = new GT.Grid(gridConfig);
GT.Utils.onLoad(function() {
	datagrid.render();
});

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
	vcTerminalTac = $('#vcTerminalTac').attr("value");
	vcTerminalBrand = $('#vcTerminalBrand').attr("value");
	vcTerminalName = $('#vcTerminalName').attr("value");
	var param={
		vcTerminalTac:vcTerminalTac,
		vcTerminalBrand:vcTerminalBrand,//开始时间
		vcTerminalName:vcTerminalName//结束时间
	};
	GT.$grid('datagrid').query( param );
}

function saveUpFile(){
	$.ajax({
			type : "post",
			url : "/terminalConfigure_saveUpFile.do",
			data : {
			},
			success : function(xml) {
			$('#showmsg').css({'display':'none'});
				query();//刷新grid数据
				//alert(xml);				
			},
			error : function() {	
			$('#showmsg').css({'display':'none'});
				alert("上传文件出错！")
			}
	});
}

function clearSession(){
	$.ajax({
			type : "post",
			url : "/terminalConfigure_clearSession.do?",
			data : {},
			success : function() {
					
			},
			error : function() {	
				
			}
	});
}

//重新加载数据
function reloadData(v){
	if(v=='add' || v=='edit'){
       query();
    }
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
          text : '添加',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          		var v = showMMDialog('/mantoeye/terminalmanager/terminal_configure_input.jsp','','600px','380px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '编辑',
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
          		var record = getRightRecord('datagrid',datagrid);;
          		obj.nmTerminalId = record.nmTerminalId;
				obj.vcTerminalTac = record.vcTerminalTac;
				obj.vcTerminalBrand = record.vcTerminalBrand;
				obj.vcTerminalName = record.vcTerminalName;
          		var v = showMMDialog('/mantoeye/terminalmanager/terminal_configure_edit.jsp',obj,'600px','380px');
          		reloadData(v);
          	}
          }
        },'-',{
          type : 'button',
          text : '删除',
          bodyStyle : 'delete',
          useable : bflags[2],
          handler : deleteTerminal
        },'-',{
          type : 'button',
          text : '上传',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          var mm= showMMDialog('/mantoeye/terminalmanager/terminal_configure_up.jsp','终端配置','540px','300px');
          		if (typeof (mm) != "undefined") {
					var flag=window.confirm(mm);
		         	 if(flag){    	 	
		         		saveUpFile();
		         		alert("文件上传成功,请耐心等待几份钟后台处理!")	
		         	 }else{//清空session的数据
		         		clearSession();
		         	}
								}	    		
		         		         	 	         	         	 
		      
          }
        },'-',{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
           //参数
			var params={
				vcTerminalTac:vcTerminalTac,
				vcTerminalBrand:vcTerminalBrand,//开始时间
				vcTerminalName:vcTerminalName//结束时间
			};
			//导出
			var fileObj = new Object();
			fileObj.fileName='终端配置';//文件名称
			fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
			fileObj.gridObj = datagrid;//当前grid对象
			fileObj.params = params;
			exportFile(fileObj);
          }
        }]
      }); 
	  toolbar.render();
    });



//删除事件
function deleteTerminal(){
    if(getSelectedCount('datagrid')<1){
       alert('请选择需要删除的号码!');
       return;
    }
	var ids = getSelectedKeys('datagrid');
	var flag = window.confirm("确定删除吗?");
	if(flag){
		$.ajax({
			type : "post",
			url : "terminalConfigure_deleteTerminal.do",
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



