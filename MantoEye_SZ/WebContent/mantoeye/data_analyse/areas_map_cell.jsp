<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>自定义小区集</title>
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
		<script src="/js/nav.js"></script>
	</head>
	<body>
	<iframe id="download" name="download" height="0px" width="0px"></iframe><!--用iframe模拟文件下载-->
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
														小区集名：
													</td>
													<td class="condition_value">
														<input type="text" id="vcAreaName" value="" />
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
												小区集数据
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
var vcAreaName='';
var vcCgiName='';
var vcCGI='';
var dsConfig= {
	//data : data1 ,
	uniqueField : 'id' ,
	fields :[
		{name : 'id'        },
		{name : 'vcAreaName'    },
		{name : 'dtUpdateTime'      },
		{name : 'vcCgiName'      },
		{name : 'vcCGI'      }
	]
};


var w="1000px";
var h="600px";
if(width==1024){
	w="815px";
	h="600px";
}
function showDetail(id,vcAreaName){
	var obj = new Object ();
	obj.id = id;
	obj.width = w;
	obj.vcAreaName = vcAreaName;
	window.showModalDialog("/mantoeye/data_analyse/area_map_cell_detail.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
}

function renderManager(value ,record,columnObj,grid,colNo,rowNo){
	var id = record.id;
	var vcAreaName = record.vcAreaName;
	return '<table><tr>'
				+'<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/info.png" alt="关联的CGI" onclick="showDetail('+id+',\''+vcAreaName+'\');" /></td>'
				+'</tr></table>'
}



var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选",exportable:false },
		{ id : 'dtUpdateTime' , header : "更新时间" ,exportable:false},
		{ id : 'vcAreaName'     , header : "自定义小区集名",exportnumber:true},
		{ id : 'vcCgiName'     , header : "小区名",exportnumber:true,exportable:true,hidden:true},
		{ id : 'vcCGI'     , header : "CGI",exportnumber:true,exportable:true,hidden:true},
		{ id : 'detail'   , header : "操作" , sortable:false, width:50,exportable:false,
			renderer:renderManager
		}	
];

var gridConfig={
	id : "datagrid",
	loadURL :'/areaMapCell_query.do?intType=2',
	exportURL :'/areaMapCell_export.do?1=1' ,
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
	obj.hideColumn = 2 ;//隐藏的列数目
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
	vcAreaName = $('#vcAreaName').attr("value");
	//vcCgiName = $('#vcCgiName').attr("value");
	//vcCGI = $('#vcCGI').attr("value");
	var param={
		vcAreaName : vcAreaName//,
		//vcCgiName:vcCgiName,
		//vcCGI:vcCGI
	};
	GT.$grid('datagrid').query( param );
}
//重置查询条件
function reset(){
	$('#vcAreaName').attr("value","");
	$('#vcCgiName').attr("value","");
}

/***************初始化toolbar***************************/
$(document).ready(function(){
  	  //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	  
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，该部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和数量应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('_BTN_ADD',permflag);

      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '添加',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
          		var obj = new Object();
          		obj.intType = 2;
          		var v = showMMDialog('/mantoeye/data_analyse/area_map_cell_input.jsp',obj,'600px','380px');
          		if(v=='add' || v=='edit'){
       				query();
    			}
          }
        },'-',{
          type : 'button',
          text : '删除',
          bodyStyle : 'delete',
          useable : 'T',
          handler : deleteAreaMapCell
        },'-',{
          type : 'button',
          text : '更新小区集',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          			var mm= showMMDialog('/mantoeye/data_analyse/areas_up.jsp','更新小区集','540px','300px');
						if (typeof (mm) != "undefined") {
									//alert("正在更新小区集，请稍等一段时间再查看数据！");
									reloadData();
						}else{
								reloadData();
						}
						
							}
						}, '-', {
							type : 'button',
							text : '模板下载',
							bodyStyle : 'new',
							useable : 'T',
							handler : downloadTemplate
						}, '-', {
							type : 'button',
							text : '导出',
							bodyStyle : 'xls',
							useable : 'T',
							handler : function() {
								//参数
							var params = {
								vcAreaName : vcAreaName,
								//vcCgiName : vcCgiName,
								intType:2//,
								//vcCGI:vcCGI
							};
							//导出
							var fileObj = new Object();
							fileObj.fileName = '小区集';
							fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
							fileObj.gridObj = datagrid;//当前grid对象
							fileObj.params = params;
							exportFile(fileObj);
						}
						} ]
			});
			toolbar.render();
		});

	//重新加载数据
	function reloadData() {
		query();
	}

	//删除任务 
function deleteAreaMapCell(){
	if( getSelectedCount('datagrid')<1){
		alert('请选择需要删除的小区集!');
		return ;
	}
	var ids = getSelectedKeys('datagrid');
	var flag = window.confirm("你确定要删除所选的数据?");
	if(flag){
		$.ajax({
			type : "post",
			url : "areaMapCell_delete.do",
			data : {
				ids:ids,
				intType:2
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
	function downloadTemplate() {
		var obj = document.getElementById('download');
		obj.contentWindow.location.href = '/topBusiness_downloadTemplate.do?intType=2';
	}
	/**
	 脚本不出错
	 */
	$(document).ready(function() {
		window.onerror = killErrors;
	})
	function killErrors() {
		return true;
	}
</script>



