<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端管理</title>
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
		<!-- 导入业务选择复选框 -->
		<link rel="stylesheet" href="/mantoeye/dialog/dialog.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
		<script type="text/javascript" src="SymbolDialog.js"></script>
		<!-- 自动补全JS -->
<script type='text/javascript'
	src='/tools/autocomplete/lib/jquery.bgiframe.min.js'></script>
<script type='text/javascript'
	src='/tools/autocomplete/lib/thickbox-compressed.js'></script>
<script type='text/javascript'
	src='/tools/autocomplete/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css"
	href="/tools/autocomplete/jquery.autocomplete.css" />
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
										<td><table cellspacing="0" cellpadding="0" border="0" width="100%">
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
</td>
									</tr>
									<tr valign="top">
										<td width="100%" class="condition_down">
											<table cellspacing="0px" cellpadding="0px;boder="0">
												<tr valign="middle">
													<td
														style="width: 72px; cursor: default; padding: 0px 1px 0px 10px; color: #ffffff; font-size: 11px;">
														请选择终端：
													</td>
													<td>

														<input type="text"
															value="-------------------------------------请选择终端-------------------------------------"
															id="terminal" onclick="showDialog2()"
															style="width: 350px; height: 16px; display: block; border: 1px solid #FFDDEE; font-size: 11px; color: #163877;" />
														<input id="terminalIds" type="hidden" value=""
															name="terminalIds" />
													</td>
													<td width="100px"></td>
													<td width="200px">
														<div id="mainbtn">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:query();" title="重置"><span>重置</span>
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
												终端用户数据
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
var terminal;
var dsConfig= {

	//data : data1 ,

uniqueField : 'null' ,

	fields :[
	
		{name : 'msisdn'      },
		{name : 'terminalBrand'        },
		{name : 'terminalType'      },
		{name : 'date'      }
	]
};



var colsConfig = [
		{ id : 'msisdn'      , header : "号码" ,sortable:true   },
		{ id : 'terminalBrand'    , header : "终端品牌" ,sortable:true  },
		{ id : 'terminalType'      , header : "终端型号" ,sortable:false  },
		{ id : 'date'    , header : "上传时间" ,sortable:true }
];

var gridConfig={

	id : "datagrid",
	
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/terminalmanager_query.do?1=1',
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
	pageSize : getDispalyPageSize(0,1),
	pageSizeList : [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
	defaultRecord :  { no : 111 , name :'', age : '', gender : 'U' , english : 50 , math :50 },
	onComplete:loadComplate

};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

var firstInit=true;

function loadComplate(){
	var  objone = new Object ();//使用对象传参,以防以后修改
	objone.datagrid = datagrid;
	objone.hideColumn = 0 ;//隐藏的列数目
	objone.isCheckbox = false;//是否可选择
	objone.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
		//判断是否查询到数据
	judgeData(datagrid);
	//初始化grid
	initGridInfo(objone);
	
	if(terminal!=''){
		$('#terminal').val(terminal);
	}
}

//// 查询表单的查询函数 ////
function query() {
	terminal=$('#terminal').val();
	var param={
		terminal:terminal
	};
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


$(document).ready(function(){
	 //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	  
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，该部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和数量应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('BTN_UPLOAD','TERMINAL_DATA_MANA_BTN_CLEAR',permflag);
	
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [/*{
          type : 'button',
          text : '上传',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          		var mm= showMMDialog('/mantoeye/terminalmanager/terminal_up.jsp','终端上传','540px','300px');
	         	if(typeof(mm)!="undefined"){
		         	 var flag=window.confirm(mm);
		         	 if(flag){
		         	 	savaUpFile();
		         	 }
		         }
          }
         	
        },'-',*/{
          type : 'button',
          text : '清除终端',
          bodyStyle : 'edit',
          useable : bflags[1],
          handler : function(){
          	if(window.confirm("你确定要清除终端表吗?")){
	          	$.ajax({
							type : "post",
							url : "/terminalmanager_clearTable.do",
							data : {
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
        }]
      });
	  toolbar.render();
    });
    
    
function savaUpFile(){
	$.ajax({
			type : "post",
			url : "/terminalmanager_saveUpFile.do",
			data : {
			},
			success : function(xml) {
				alert(xml);
			},
			error : function() {
				alert('服务器出错,请联系管理员!');
			}
	});
}

</script>


<!-- 复选框JS -->
<script>
	var dialog2 = null;
	function showDialog2(){
		if(dialog2 == null){
		  dialog2 = new SymbolDialog({
	        renderTo : 'terminal',
	        hiddenTo:'terminalIds',
			id:'terminalDialog',
			showLoading:true,
			typeSearchName:'品牌',
			modelSearchName:'型号',
			title:'终端复选对话框',
			url:'terminalDialog_initTerminalDialogData.do'
	      });
		  dialog2.init();//初始化页面DOM对象
		  dialog2.loadData();//加载后台数据
		  dialog2.display();//显示
		}else{
		  dialog2.display();
		}
	}
</script>
