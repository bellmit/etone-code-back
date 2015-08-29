<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务配置</title>
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
														公司：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_compName" value=""
															id="d_compName" />
													</td>
													<td class="condition_name">
														业务类型：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_busiType" value=""
															id="d_busiType" />
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
												业务数据列表
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
								<iframe   name="subpage"   width="0"   height="0"></iframe>
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
var dsConfig= {
	//data : data1 ,
	uniqueField : 'intBussinessId' ,
	fields :[
		{name : 'intBussinessId'        },
		{ name : 'vcBussinessName'       },
		{ name : 'companyName'       },
		{ name : 'typeName'       },
		{name : 'vcBussinessNote'      },
		{name : 'vcBussinessPicPath'    }
	]
};
function openAddMainRule(id){
	var mm = showMMDialog('/businessMainVetor_add.do?bid='+id,'添加主规则','540px','450px');
	parent.refrushTab("RULE_MA_MAIN_MANA");//刷新关联页面
}
function openAddAssistRule(id){
	var mm = showMMDialog('/businessAssistVetor_add.do?bid='+id,'添加辅规则','540px','450px');
	parent.refrushTab("RULE_MA_ASSIST_MANA");//刷新关联页面
}
function openAddTestRule(id){
	var mm = showMMDialog('/businessRuleTest_add.do?taskType=5&bid='+id,'添加拨测任务','640px','560px');
	parent.refrushTab("RULE_TEST_MANA");//刷新拨测任务
}
/**/
function renderInit(value ,record,columnObj,grid,colNo,rowNo){
	var bid = record.intBussinessId.toString();
	return '<table><tr>'
			//+'<td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/main.gif" alt="添加翻译规则" onclick="openAddMainRule(\''+bid+'\');" /></td>'
			//+'<td>&nbsp;</td><td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/assist.gif" alt="添加检验/自发现规则" onclick="openAddAssistRule(\''+bid+'\');" /></td>'
			+'<td>&nbsp;</td><td><img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/test.gif" alt="添加拨测任务" onclick="openAddTestRule(\''+bid+'\');" /></td>'
			+'</tr></table>';
}

var colsConfig = [
	// isCheckColumn 来指定是不是"checkbox选择列" ,通过 grid.checkedRows 能取得所有被选中的行
		// checkbox的value 通过 fieldName来指定(数据记录的某一列)
		// grid.checkedRows 是一个{}, key为 checkbox的value, value为true/false. 所以要求这一列的值必须是唯一的.
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选",exportable:false},
		{ id : 'intBussinessId'      , header : "ID"  ,hidden:true,exportable:false },
		{ id : 'vcBussinessName'      , header : '业务名称' , sortable:false  },
		{ id : 'companyName'    , header : '公司名称'  ,sortable:false  },
		{ id : 'typeName'      , header : '业务类型'   ,sortable:false  },
		{ id : 'vcBussinessNote'    , header : '描述' ,sortable:false   },
		{ id : 'vcBussinessPicPath'     , header : 'LOGO' ,sortable:false ,hidden:true,exportable:false  },
		{ id : 'detail'   , header : "操作" , sortable:false, exportable:false,
			renderer:renderInit
		}
];

var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/businessSite_list.do?1=1',
	exportURL :'/businessSite_export.do?1=1' ,
	//saveURL : '/flexiGridTest_list1.do?1=1' ,
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
	pageSize : getDispalyPageSize(0,1),
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

//// 查询表单的查询函数 ////
function query() {
	var param={
		'filter_LIKE_vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		'filter_LIKE_dtbBusinessCompany.vcName' : GT.U.getValue(GT.$('d_compName')),
		'filter_LIKE_dtbBusinessType.vcBussinessTypeName' : GT.U.getValue(GT.$('d_busiType'))
	};
	GT.$grid('datagrid').query( param );
}
function reset(){
	$('#d_busiName').attr('value','')
	$('#d_compName').attr('value','')
	$('#d_busiType').attr('value','')
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
	displayOrHiddenTd();
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '添加业务',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
          var mm = showMMDialog('/businessSite_input.do?flag=0','添加业务','500px','300px');
          if(mm=='add'){
         	 		query();
         	}
          }
        },'-',{
          type : 'button',
          text : '编辑业务',
          bodyStyle : 'edit',
          useable : 'T',
          handler : function(){
          	var count = getSelectedCount('datagrid');
          	if(count>1){
          		alert("只能选择一条记录！");
          	}else if(count<1){
          		alert("请选择一条记录！");
          	}else{
          		var id=getSelectedId('datagrid');
          		var mm= showMMDialog('/businessSite_edit.do?id='+id,'编辑业务','500px','300px');
         	 	if(mm=='edit'){
         	 		query();
         	 	}
          	}
          }
        },'-',{
          type : 'button',
          text : '删除业务',
          bodyStyle : 'delete',
          useable : 'T',
          handler :deleteData
        },'-',{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler :exportToXls
        }]
      });
	  toolbar.render();
    });
function reloadData(){
//Alert("----");
	 //query();
}
function exportToXls(){
 //参数
	var params={
		'filter_LIKE_vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		'filter_LIKE_dtbBusinessCompany.vcName' : GT.U.getValue(GT.$('d_compName')),
		'filter_LIKE_dtbBusinessType.vcBussinessTypeName' : GT.U.getValue(GT.$('d_busiType'))
	};
			//导出
	var fileObj = new Object();
	fileObj.fileName='业务数据列表';//('+startTime_search+'至'+endTime_search+')';//文件名称
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
						url : "/businessSite_delete.do",
						data : {
							keys:keys
						},
						success : function(msg) {
							alert(msg);//打印删除是否成功信息
							query();//刷新grid数据
						},
						error : function() {
							alert('该业务已经被使用，不能被删除!');
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



