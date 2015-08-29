<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>翻译规则配置</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet" href="/skin/Default/css/maincontent.css" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css" type="text/css"></link>
		<script type="text/javascript" src="/js/nav.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script type="text/javascript" src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<script type="text/javascript" src="/js/jquery/extend.tab/js/Toolbar.js"></script>
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
														业务名称：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_busiName" value=""
															id="d_busiName" />
													</td>
													
													<td class="condition_name">
														IP：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_ip" value=""
															id="d_ip" />
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
													</tr><tr style="display:none">
													<td class="condition_name">
														APN：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_apn" value=""
															id="d_apn" />
													</td>
													<td class="condition_name">
														UserAgent：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_useragent" value=""
															id="d_useragent" />
													</td>
													<td class="condition_name">
														URL：
													</td>
													<td class="condition_value" colspan="3" style="width:250px">
														<input type="text"
															name="d_url" value=""
															id="d_url" style="width:160px"/>
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
												翻译规则数据列表
											</div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
<!-- 自定义列表头. -->
<!--<table id="myHead" style="display:none">
<tr>
	<td rowspan="2" columnId='businessName'>业务名称</td>
	<td colspan="5" >主规则</td>
	<td colspan="5">辅规则</td>
</tr>
<tr>
	<td columnId='mainruleUrl'>URL</td>
	<td columnId='mainruleIp'>IP</td>
	<td columnId='mainrulePort'>Port</td>	
	<td columnId='mainruleApn'>APN</td>
	<td columnId='mainruleUserAgent'>UserAgent</td>
	<td columnId='assistruleUrl'>URL</td>
	<td columnId='assistruleIp'>IP</td>
	<td columnId='assistrulePort'>Port</td>	
	<td columnId='assistruleApn'>APN</td>
	<td columnId='assistruleUserAgent'>UserAgent</td>
</tr>
</table>	 -->		
						<tr>
							<td>
								<div id="toolbar" style="height: 25px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container">

									</div>
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
//关联检验规则权限标识
var map_perm = 'F';
var dsConfig= {
	//data : data1 ,
	uniqueField : 'nmBusinessMainVetorId' ,
	fields :[
		{name : 'nmBusinessMainVetorId'        },
		{ name : 'businessName'       },
		{ name : 'mainruleIp'       },
		{name : 'mainrulePort'      },
		{name : 'mainruleApn'    },
		{name : 'mainruleUserAgent'    },
		{name : 'mainruleUrl'    }
	]
};

function renderManager(value ,record,columnObj,grid,colNo,rowNo){
	var date = record.nmBusinessMainVetorId.toString();
	var rhtml = ' ';
	if(map_perm=='T'){
		rhtml =  '<img style="cursor: hand"  src="/skin/Default/images/MantoEye/icon/associate.gif" alt="关联检验规则" onclick="associateById(\''+date+'\');" />';
	}
	return rhtml;
}
/**/
var colsConfig = [
		//{ id : 'nmMainAssistVetorId'      , header : "ID"   ,hidden:true,exportable:false },
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选",exportable:false},		
		{ id : 'businessName'      , header : "业务名称" , sortable:false },
		{ id : 'mainruleIp'      , header : "IP"  , sortable:false},
		{ id : 'detail'   , header : "操作" , sortable:false, exportable:false,
			renderer:renderManager
		}
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/businessMainVetor_query.do?1=1',
	exportURL :'/businessMainVetor_export.do?1=1' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	resizable : false,
	transparentMask:true,
    selectRowByCheck: true,
	container : 'data_container',
	beforeLoad:checkLogon,
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	beforeSave : function(reqParam){
	},
	pageSize : getDispalyPageSize(0,1) ,
	remoteSort : true ,
	pageSizeList :  [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	//customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

var firstInit=true;

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

//// 查询表单的查询函数 ////
function query() {
	var param={
		'filter_LIKE_dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn')),
		'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_useragent'))
	};
	GT.$grid('datagrid').query( param );
}
function reset(){
	$('#d_busiName').attr('value','');
	$('#d_ip').attr('value','');
	$('#d_url').attr('value','');
	$('#d_apn').attr('value','');
	$('#d_useragent').attr('value','');
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
	  var bflags = initBtnPerm('_BTN_ADD,_BTN_EDIT,_BTN_DEL,_BTN_MAP',permflag);
	  map_perm = bflags[3];
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '添加翻译规则',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          var mm = showMMDialog('/businessMainVetor_add.do?flag=0','添加主规则','540px','450px');
          if(mm=='add'){
         	 		query();
         	}         
          }
        },'-',{
          type : 'button',
          text : '编辑翻译规则',
          bodyStyle : 'edit',
          useable : bflags[1],
          handler : function(){
         var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		var id=getSelectedId('datagrid');
          		var mm= showMMDialog('/businessMainVetor_edit.do?id='+id,'编辑业务','540px','450px');
         	 	if(mm=='edit'){
         	 		query();
         	 }	
          }
          }
        },'-',{
          type : 'button',
          text : '删除翻译规则',
          bodyStyle : 'delete',
          useable : bflags[2],
          handler : deleteData
        }/*,'-',{
          type : 'button',
          text : '关联检验规则',
          bodyStyle : 'linked',
          useable : 'T',
          handler : associate
        }*/,'-',{
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
 	//参数
	var params={
		'filter_LIKE_dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn')),
		'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_useragent'))
	};
			//导出
	var fileObj = new Object();
	fileObj.fileName='翻译规则数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
	fileObj.fileFormat = 'xls';//文件格式,后台暂支持xls格式
	fileObj.gridObj = datagrid;//当前grid对象
	fileObj.params = params;
	exportFile(fileObj);
 }     
//删除数据
function deleteData(){
		var count = getSelectedCount('datagrid');
		var keys = getSelectedKeys('datagrid');
		if(count<1){
          		alert("请选择要删除的记录！");
         }else{
          	if(window.confirm("你确定要删除所选的数据吗?")){
          			$.ajax({
						type : "post",
						url : "/businessMainVetor_delete.do",
						data : {
							keys:keys
						},
						success : function(msg) {
							alert(msg);//打印删除是否成功信息
							query();//刷新grid数据
							parent.refrushTab("RULE_MA_MAMAP_MANA");//刷新关联页面
						},
						error : function() {
							alert('该规则正在被使用，不能被删除!');
						}
					});
          		}
          	}
}
/*
function associate(){
	 var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          	//alert("associate");         	
          		var id=getSelectedId('datagrid');
          		var mm= showMMDialog('/businessMainVetor_associate.do?id='+id,'关联辅规则','800px','575px');
          		parent.refrushTab("RULE_MA_MAMAP_MANA");//刷新关联页面
         	 	//if(mm=='associate'){
         	 		//query();
         	// }	
     }
}*/
function associateById(id){

	var mm= showMMDialog('/businessMainVetor_associate.do?id='+id,'关联辅规则','800px','575px');
	parent.refrushTab("RULE_MA_MAMAP_MANA");//刷新关联页面
         	 	
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



