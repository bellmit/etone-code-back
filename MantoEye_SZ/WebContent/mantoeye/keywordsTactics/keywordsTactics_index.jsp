<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>关键字搜索策略</title>
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script src="/js/nav.js"></script>
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
	
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="100%">

			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
						style="width: 100%;">
						<tr valign="top">
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr valign="top" style="font-size:1px;">

									</tr>
									<tr valign="top">
										<td width="100%" class="condition_down">
											<table id="query_condition" cellspacing="0px"
												cellpadding="0px;boder=0">
												<tr valign="middle">
													<td id="spaceTd" width="*"></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
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
												系统用户数据
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
								<div id="toolbar" style="height: 25px;width:100%"></div>
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

var addflag='';

var dsConfig= {
	//data : data1 ,
	uniqueField : 'nmDataGetterTaskId' ,
	fields :[
		{name : 'nmDataGetterTaskId'},
		{name : 'tacticsName'},
		{name : 'userName'},
		{name : 'createTime'},
		{name : 'startTime'},
		{name : 'endTime'},
		{name : 'missionTime'},
		{name : 'missionHour'},
		{name : 'status'},
		{name : 'detail'}
	]
};
var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选",width : 10 },
		{ id : 'tacticsName' , header : "策略名称",sortable: true,width : 150 },
		{ id : 'userName', header : "定制人",sortable: true,width : 150},
		{ id : 'createTime', header : "定制时间",sortable: true,width : 150},
		{ id : 'startTime', header : "开始时间",sortable: true,width : 10,hidden : true},
		{ id : 'endTime', header : "结束时间",sortable: true,width : 10,hidden : true},
		{ id : 'missionTime', header : "执行日期",sortable: true,width : 150},
		{ id : 'missionHour', header : "执行时间段",sortable: true,width : 70},
		{ id : 'status', header : "状态",width:100 },
		{ id : 'detail', header : "操作",sortable: false,renderer: renderDetail,width : 200 }
];

function renderDetail(value, record, columnObj, grid, colNo, rowNo){
	if(record.status!='未开始'){
		return '<div id="mainbtn" style="padding-left: 5px;"><ul>' 
		+'<li><a href="javascript:showMsg('+record.nmDataGetterTaskId+');"style="width: 71px;" title="详细信息"><span>详细信息</span></a></li>'
		+'<li><a href="javascript:showDetail('+record.nmDataGetterTaskId+',\''+record.missionHour+'\',\''+record.missionTime+'\');"style="width: 71px;" title="结果列表"><span>结果列表</span></a></li></ul></div>';
	}else{
		 return '<div id="mainbtn" style="padding-left: 5px;"><ul>' 
		+'<li><a href="javascript:showMsg('+record.nmDataGetterTaskId+');"style="width: 71px;" title="详细信息"><span>详细信息</span></a></li></ul></div>';
	}
}

function showMsg(id){
	showMMDialog('/modules/keywordsTactics/keywords-tactics!edit.do?id='+id+'&readOnly=1','','600px','380px');
}


var w="1000px";
var h="600px";
if(width==1024){
	w="815px";
	h="600px";
}
function showDetail(id,missionHour,missionTime){
	var obj = new Object();
	obj.id = id;
	var startHour = missionHour.split("~")[0];
	var endHour = missionHour.split("~")[1];
	var startTime = missionTime.split("~")[0];
	var endTime = missionTime.split("~")[1];
	obj.startTime = startTime+" "+startHour+":00:00";
	obj.startTime1 = startTime+" "+endHour+":00:00";
	obj.endTime = endTime+" "+endHour+":00:00";
	obj.width = w;
	obj.height = h;
	window.showModalDialog("/mantoeye/keywordsTactics/keywordsTactics_detail.jsp",obj,"dialogWidth="+ w+ ";dialogHeight="+ h+ ";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
}
	

var gridConfig={
	id : "datagrid",
	loadURL :'/modules/keywordsTactics/keywords-tactics!query.do?1=1',
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
	pageSize : getDispalyPageSize(0,2),
	remoteSort : false ,
	pageSizeList :  [10,2050,100],
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
	obj.hideColumn = 1 ;//隐藏的列数目
	obj.isCheckbox = true;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	columninit=true;
		
	//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(obj);
	
		if(firstInit==true){
		
		var pageSize=getDispalyPageSize(0,2);
		var index=0;
		$(".gt-pagesize-select option").each(function(i){
			if(this.text==pageSize){
				index=i;
			}
		})
		firstInit=false;
		$(".gt-pagesize-select").get(0).selectedIndex=index;
	}
	
	//初始化查询条件
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	for(var i=0;i<totalRecords;i++){
		if(datagrid.getRecord(i) && datagrid.getRecord(i).status=="进行中"){
			addflag = "F";
			break;
		}
	}
	addToolbar(addflag);
}




//grid查询
function query() {
	var param={
	
	};
	GT.$grid('datagrid').query( param );
}


/***************初始化toolbar***************************/
//$(document).ready(function(){
function addToolbar(addflag){
		displayOrHiddenTd();
	  //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，改部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('_BTN_ADD,_BTN_EDIT,_BTN_DEL,_BTN_ROLE,_BTN_UNMP',permflag);
	  bflags[0] = addflag;
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '新建策略',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          		var v = showMMDialog('keywordsTactics_input_1.jsp','','600px','380px');
          		reloadData(v);
          }
        },'-',{
          type : 'button',
          text : '编辑策略',
          bodyStyle : 'edit',
          useable : bflags[1],
          handler : function(){
          	var count = getSelectedCount('datagrid');
          	var utype = getColumnValueByName('datagrid','tiUserType');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		var state = getColumnValueByName('datagrid','status');
          		if(state==null){
          			return ;
          		}
          		if(state!='未开始' && state!='停止'){//非未开始状态不能编辑
          			alert('只有未开始或停止状态任务才可以编辑!');
          			return ;
          		}
          			var id=getSelectedId('datagrid');
          			var v = showMMDialog('/modules/keywordsTactics/keywords-tactics!edit.do?id='+id+'&readOnly=0','','600px','380px');
          			reloadData(v);
          		//}
          	}
          }
        },'-',{
          type : 'button',
          text : '删除策略',
          bodyStyle : 'delete',
          useable : bflags[2],
          handler : deleteTactics
        }
        ,'-',{
          type : 'button',
          text : '停止策略',
          bodyStyle : 'stop',
          useable : bflags[3],
          handler :stopTactics
        }
        ]
      }); 
	  toolbar.render();
	}
    //});

//重新加载数据
function reloadData(v){
	if(v=='add' || v=='edit'){
      query()
    }
}
//删除事件
function deleteTactics(){
    if(getSelectedCount('datagrid')<1){
       alert('请选择需要删除的策略!');
       return;
    }
    var states = getColumnValueByName('datagrid','status');
  	if(states.indexOf('进行中')!=-1){
	 	alert("状态为'进行中'的任务不能删除!");
	 	return;
	}
	var ids = getSelectedKeys('datagrid');
	var flag = window.confirm("确定删除吗?");
	if(flag){
		$.ajax({
			type : "post",
			url : "/modules/keywordsTactics/keywords-tactics!deleteTactics.do",
			data : {
				ids:ids
			},
			success : function(msg) {
				alert(msg);//打印删除是否成功信息
				query();
			},
			error : function() {
				alert('服务器出错,请联系管理员!');
			}
		});
	}
}

//停止策略
function stopTactics(){
    if(getSelectedCount('datagrid')<1){
       alert('请选择需要停止的策略!');
       return;
    }else if(getSelectedCount('datagrid')>1){
    	alert('只能选择一条策略！');
    }else{
	    var utype = getColumnValuesByName('datagrid','createTime');
	  	var state = getColumnValueByName('datagrid','status');
        if(state==null){
			return ;
		}
		if(state!='进行中'){
			alert('只有进行中状态的任务才可以停止!');
			return ;
		}	
		var ids = getSelectedKeys('datagrid');
		var flag = window.confirm("确定停止吗?");
		if(flag){
			$.ajax({
				type : "post",
				url : "/modules/keywordsTactics/keywords-tactics!stopTactics.do",
				data : {
					ids:ids
				},
				success : function(msg) {
					alert(msg);//打印删除是否成功信息
					window.location.reload();
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


