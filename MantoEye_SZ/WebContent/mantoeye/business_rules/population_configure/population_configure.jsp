<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>常驻人口配置</title>
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
												常驻人口配置
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
	uniqueField : 'nmResidentId' ,
	fields :[
		{name : 'nmResidentId'        },
		{name : 'intType'    },
		{name : 'intDay'      },
		{name : 'vcNote'      }
		
	]
};


var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选" },
		{ id : 'intType'     , header : "配置类型",exportnumber:true,sortable:false },
		{ id : 'intDay' , header : "常驻频次天数" ,exportnumber:true,headAlign:'right' ,align:'right',sortable:false },
		{ id : 'vcNote' , header : "备注" ,exportnumber:true,sortable:false }
		
		
];

var gridConfig={
	id : "datagrid",
	loadURL :'/populationConfigure_query.do?intType=2',
	exportURL :'' ,
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
	remoteSort : false ,
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
	GT.$grid('datagrid').query();
}

/***************初始化toolbar***************************/
$(document).ready(function(){
  	  	  //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	  
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，该部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和数量应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('_BTN_EDIT',permflag);
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
        items : [/*{
          type : 'button',
          text : '添加',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
			var width = '720px';
          	var height = (checkIE()=='IE6')?'500px':'450px';
          	
          	var v = showMMDialog('/mantoeye/business_rules/population_configure/input.jsp','添加常驻人口配置',width,height);
          		if (typeof (v) != "undefined") {
								reloadData();
						}else{
								reloadData();
						}
          }
        },'-',*/{
          type : 'button',
          text : '编辑',
          bodyStyle : 'edit',
          useable : bflags[0],
          handler : function(){
          	
          	var count = getSelectedCount('datagrid');
          	if(count<1){
          		alert("请选择一条记录!");
          	}else if(count>1){
          		alert("只能选择一条记录!");
          	}else {
          		var width = '720px';
          		var height = (checkIE()=='IE6')?'500px':'450px';
          		var obj = new Object();
          		var record = getRightRecord('datagrid',datagrid);
				obj.nmResidentId = record.nmResidentId+'';
				obj.intType = record.intType;
				obj.intDay = record.intDay;
				obj.vcNote = record.vcNote;
				var id = getSelectedId('datagrid');
          		var v = showMMDialog('/populationConfigure_edit.do',obj,width,height);
          		reloadData(v);
          	}
          }
        }/*,'-',{
            type : 'button',
            text : '删除',
            bodyStyle : 'delete',
            useable : bflags[2],
            handler : deleteConfigure
          }*/]
      });
	  toolbar.render();
    });
 

//删除任务 
function deleteConfigure(){
	var nmResidentIds = getSelectedKeys('datagrid');
	var count =getSelectedCount('datagrid');
	if(count<1){
		alert('请选择要删除的项!');
		return;
	}
	
	if(window.confirm("删除该常驻人口配置,确定删除吗?")){
		$.ajax({
			type : "post",
			url : "populationConfigure_deleteConfigure.do",
			data : {
				nmResidentIds:nmResidentIds
			},
			success : function(msg) {
				alert(msg);//打印删除是否成功信息
				query();//刷新grid数据
			},
			error : function() {
				alert('服务器出错,请联系管理员!');
			}
		})
	}
}
	//重新加载数据
	function reloadData(v) {
		if(v=='add' || v=='edit'){
       		query();
    	}
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



