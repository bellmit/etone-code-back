<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务${maintitle }关联检验规则</title>	
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
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="780px" height="100%">
			<tr>
				<td colspan="2" height="5px"></td>
			</tr>

			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
						style="width: 780px;">
						<tr valign="top">
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="780px">
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
												cellpadding="0px;boder="0">
												<tr valign="middle">
													<td class="condition_name">
														URL：
													</td>
													<td class="condition_value">
													<input type="hidden" name="d_busiName" value="" id="d_busiName"/>
														<input type="text"
															name="d_url" value=""
															id="d_url" style="width:160px"/>
													</td>
													 <td class="condition_name" style="display:none">
														IP：
													</td>
													<td class="condition_value" style="display:none">
														<input type="text"
															name="d_ip" value=""
															id="d_ip" style="width:160px"/>
													</td>
													<td class="condition_name">
														Port：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_port" value=""
															id="d_port" style="width:160px"/>
													</td>	
													<td class="condition_name">&nbsp;</td><td class="condition_value">&nbsp;</td>
													</tr><tr>												
													<td class="condition_name">
														APN：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_apn" value=""
															id="d_apn" style="width:160px"/>
													</td>
													
													<td class="condition_name">
														UserAgent：
													</td>
													<td class="condition_value">
														<input type="text"
															name="d_useragent" value=""
															id="d_useragent" style="width:160px"/>
													</td>
													
													<td class="condition_name">
														&nbsp;
													</td>
													<td >
														<div id="mainbtn" style="width:160px;text-align: right;">
															<ul>
																<li>
																	<a href="javascript:query('3');" 　title="查询"><span>查询</span></a>
																</li>
																<!--  
																<li>
																	<a href="javascript:reset();" title="重置"><span>重置</span></a>
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
							<td height="5px" bgcolor="#D3E0F2" width="780px">
							<input id="busiid" type="hidden" value="${busiid }"></input>
							<input id="kid" type="hidden" value="${entity.nmBusinessMainVetorId }"></input>
							<input id="m_ip" type="hidden" value="${entity.vcServerIp }"></input>
							<input id="m_port" type="hidden" value="${entity.intPort }"></input>
							<input id="m_url" type="hidden" value="${entity.vcUrl}"></input>
							<input id="m_apn" type="hidden" value="${entity.vcApn }"></input>
							<input id="m_useragent" type="hidden" value="${entity.vcUserAgent }"></input>
							</td>
						</tr>

						

						<tr>
							<td>							
								<table cellspacing="0" cellpadding="0" border="0" width="780px"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												未关联检验规则
											</div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>	
						<!-- 未关联的数据 -->
						<tr >
							<td style="display:block" id="maincontent_w">
								<div id="toolbar_w" style="height: 25px;width:780px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container_w">
									</div>
								</div>
							</td>
						</tr>	
						<tr>
							<td>							
								<table cellspacing="0" cellpadding="0" border="0" width="780px"
									class="menubar">
									<tr valign="top">
										<td width="4px" height="24px">
											<div class="lefttitle"></div>
										</td>
										<td width="100%" height="24px">
											<div class="middletitle">
												已关联检验规则
												<!--  <input type="button" value="test activeCell" onclick="alert( datagrid_w.activeCell.innerHTML )" />
											--></div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>	
						<!-- 已关联的数据 -->
						<tr >
							<td style="display:block" id="maincontent_y">
								<div id="toolbar_y" style="height: 25px;width:780px"></div>
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container_y">
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
var init_w =false;
var init_y =false;
var dsConfig= {
	//data : data1 ,
	uniqueField : 'nmBusinessAssistVetorId' ,
	fields :[
		{name : 'nmBusinessAssistVetorId'},
		{name : 'businessName'       },
		{name : 'assistruleIp'       },
		{name : 'assistrulePort'      },
		{name : 'assistruleApn'    },
		{name : 'assistruleUserAgent'    },
		{name : 'assistruleUrl'    }
	]
};
var colsConfig = [
		{id: 'chk' ,isCheckColumn : true, checkType:'checkbox', checkValue:false,frozen : false , filterable : true, header: "", title: "全选"},
	//	{ id : 'nmBusinessAssistVetorId'      , header : "ID" ,width:60 },
		{ id : 'assistruleUrl'     , header : "URL"  ,width:240, sortable:false},
	//	{ id : 'assistruleIp'      , header : "IP"  ,width:180 , sortable:false},
		{ id : 'assistrulePort'    , header : "Port" ,width:100 , sortable:false },
		{ id : 'assistruleApn'     , header : "APN"   ,width:200 , sortable:false},
		{ id : 'assistruleUserAgent'     , header : "UserAgent"  ,width:200 , sortable:false}
	//	{ id : 'detail'   , header : "操作" , sortable:false, 
	//		renderer:renderInit
	//	}
];
var gridConfig_w={
	id : "datagrid_w",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/businessAssistVetor_listNoassociate.do?1=1',
	exportURL :'/businessAssistVetor_exportNoassociate.do?1=1',
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:200,
	resizable : false,
	container : 'data_container_w',
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	pageSize : 10 ,
	remoteSort : true ,
	pageSizeList : [10,20,50,100],
	onComplete:loadComplate_w
};
var gridConfig_y={
	id : "datagrid_y",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/businessAssistVetor_listAssociate.do?1=1',
	exportURL :'/businessAssistVetor_exportAssociate.do?1=1',
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:200,
	resizable : false,
	container : 'data_container_y',
	toolbarPosition : 'bottom', 
	toolbarContent : '| nav | goto | pagesize | state',
	pageSize : 10 ,
	remoteSort : true ,
	pageSizeList : [10,20,50,100],
	onComplete:loadComplate_y
};

var datagrid_w=new GT.Grid( gridConfig_w );
var datagrid_y=new GT.Grid( gridConfig_y );
GT.Utils.onLoad( function(){
	datagrid_w.render();
	datagrid_y.render();
});

function loadComplate_y(){
	if(!init_y){
		query(2);
		init_y = true;
	}else{
		//判断是否查询到数据
		judgeMyData(datagrid_y);
	}

	
}
function loadComplate_w(){
	if(!init_w){
		query(1);
		init_w = true;
	}else{
		//判断是否查询到数据
		judgeMyData(datagrid_w);
	}
	
}
//判断grid查询是否有数据，没有则提示
function judgeMyData(datagrid){
	var totalRecords = datagrid.getAllRows().length;//获取加载到的数据记录数目
	var tdWidth="780px"
	if(typeof(datagrid.getRecord(0))=="undefined"){
		var $o = $('#'+datagrid.id+'_bodyDiv').find('table').eq(0).find('tbody').eq(0);//获取对象
		$o.find('tr').eq(0).remove();//由于控件存在bug，所以需要删除第一行
		var $tr = $('<tr><td height="20px" width='+tdWidth+' style="font-size:14px" align="center" bgcolor="#EEEEEE">没有数据显示!</td></tr>');
		$tr.appendTo($o);
	}
}

//// 查询表单的查询函数 ////
function query(flag) {
	var param={
		'filter_LIKE_dtbBusinessSite.vcBussinessName' : GT.U.getValue(GT.$('d_busiName')),
		'filter_LIKE_vcServerIp' : GT.U.getValue(GT.$('d_ip')),
		'filter_LIKE_vcUrl' : GT.U.getValue(GT.$('d_url')),
		'filter_LIKE_vcApn' : GT.U.getValue(GT.$('d_apn')),
		'filter_EQ|INTEGER_intPort' : GT.U.getValue(GT.$('d_port')),
		'filter_LIKE_vcUserAgent' : GT.U.getValue(GT.$('d_useragent')),
		'busiid' : GT.U.getValue(GT.$('busiid')),
		'kid' : GT.U.getValue(GT.$('kid')),
		'm_ip' : GT.U.getValue(GT.$('m_ip')),
		'm_url' : GT.U.getValue(GT.$('m_url')),
		'm_apn' : GT.U.getValue(GT.$('m_apn')),
		'm_port' : GT.U.getValue(GT.$('m_port')),
		'm_useragent' : GT.U.getValue(GT.$('m_useragent'))
	};
	if(flag=='1'){
	GT.$grid('datagrid_w').query( param );
	}else if(flag=='2'){
	GT.$grid('datagrid_y').query( param );
	}else{
	GT.$grid('datagrid_w').query( param );
	GT.$grid('datagrid_y').query( param );
	}
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
	document.getElementById('d_ip').value='';
	document.getElementById('d_url').value='';
	document.getElementById('d_apn').value='';
	document.getElementById('d_port').value='';
	document.getElementById('d_useragent').value='';
}
$(document).ready(function(){
      var toolbar_w = new Toolbar({
        renderTo : 'toolbar_w',
		//border: 'top',
        items : [{
          type : 'button',
          text : '添加关联',
          bodyStyle : 'addassiciate',
          useable : 'T',
          handler : function(){
        		addassociate();
        }
        }
        ,'-'/*,{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
           //GT.$grid('datagrid').exportGrid();
          }
        }*/
        ]
      });    
	  toolbar_w.render();
	   var toolbar_y = new Toolbar({
        renderTo : 'toolbar_y',
		//border: 'top',
        items : [{
          type : 'button',
          text : '删除关联',
          bodyStyle : 'deleteassiciate',
          useable : 'T',
          handler : function(){
       			deleteassociate();
        }
        }
        ,'-'/*,{
          type : 'button',
          text : '导出',
          bodyStyle : 'xls',
          useable : 'T',
          handler : function(){
           //GT.$grid('datagrid').exportGrid();
          }
        }*/]
      });
	  toolbar_y.render();
    });

function addassociate() {
		var count = getSelectedCount('datagrid_w');
		var keys = getSelectedKeys('datagrid_w');
		var kid = document.getElementById("kid").value;
		if(count<1){
          		alert("请选择要关联的记录！");
         }else{
         		if(window.confirm("为了防止误判断翻译规则，请确保检验规则信息填写得足够完全，你确定要关联？"))	{
          			$.ajax({
						type : "post",
						url : "/businessVetor_addAssociate.do",
						data : {
							keys:keys,
							kid:kid
						},
						success : function(msg) {
							alert(msg);//打印删除是否成功信息
							query('3');//刷新grid数据
						},
						error : function() {
							alert('服务器出错,请联系管理员!');
						}
					});
					}
          	}
}    
function deleteassociate() {
		var count = getSelectedCount('datagrid_y');
		var keys = getSelectedKeys('datagrid_y');
		var kid = document.getElementById("kid").value;
		if(count<1){
          		alert("请选择要删除关联的记录！");
         }else{ 
         if(window.confirm("你确定要删除所选的关联信息吗?")){
          			$.ajax({
						type : "post",
						url : "/businessVetor_deleteAssociate.do",
						data : {
							keys:keys,
							kid:kid
						},
						success : function(msg) {
							alert(msg);//打印删除是否成功信息
							query('3');//刷新grid数据
						},
						error : function() {
							alert('服务器出错,请联系管理员!');
						}
					});
					}
          	}
}     
    
/**
*
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}
</script>



