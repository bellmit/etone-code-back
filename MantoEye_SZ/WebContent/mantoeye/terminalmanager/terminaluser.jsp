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
						<%-- <tr valign="top">
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
									
								</table>
							</td>
						</tr>--%>

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
												终端上传任务状态
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
								<div class="dialog_msg_class" style="margin-top:120px;margin-left:275px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>文件上传中,请稍后...</div>
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
	
		{name : 'vcServerIp'      },
		{name : 'vcFilePath'        },
		{name : 'intStatus'      },
		{name : 'dtSTime'      },
		{name : 'dtETime'        }
	]
};



var colsConfig = [
		{ id : 'vcServerIp'      , header : "文件所在服务器" ,sortable:true   },
		{ id : 'vcFilePath'    , header : "文件路径" ,sortable:true  },
		{ id : 'intStatus'      , header : "上传状态" ,sortable:false  ,renderer:renderStatus   },
		{ id : 'dtSTime'    , header : "开始上传时间" ,sortable:true },
		{ id : 'dtETime'    , header : "完成上传时间" ,sortable:true }
];
/*
1.文件已上传完成
2.数据导入中
3.数据导入成功
4.数据导入异常*/
function renderStatus(value ,record,columnObj,grid,colNo,rowNo){
	if(value==1){
		return '文件上传完成,等待程序导入';
	}else if(value==2){
		return '数据导入中';
	}else if(value==3){
		return '数据导入成功';
	}else  if(value==4){
		return '数据导入异常';
	}else{
		return '文件上传出错';
	}
}
var gridConfig={

	id : "datagrid",
	loadURL :'/terminalmanager_findUpFiles.do?1=1',
	exportURL :'' ,
	dataset : dsConfig ,
	columns : colsConfig ,
	width:780,
	height:500,
	container : 'data_container', 
	beforeLoad:checkLogon,
	toolbarContent : 'nav | goto | pagesize | reload | state' ,
	pageSize : getDispalyPageSize(0,1),
	remoteSort : false ,
	remotePaging:false,
	pageSizeList : [10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,45,50,100],
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



$(document).ready(function(){
	 //按钮权限设置	  
	  var permflag = '<%=btnperm%>';
	  
	   /*第一个参数为下面按钮数据库对应按钮标识的部分或全组成的集合
	  	*（如果是部分，该部分必须在下面的所有按钮中能唯一标识）
	  	*顺序和数量应该与下面的按钮一致
	  	*/
	  var bflags = initBtnPerm('BTN_UPLOAD,_BTN_CLEAR',permflag);
	
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '上传',
          bodyStyle : 'new',
          useable : bflags[0],
          handler : function(){
          		var mm= showMMDialog('/mantoeye/terminalmanager/terminal_up.jsp','终端上传','540px','300px');
	         	if(typeof(mm)!="undefined"){
		         	 var flag=window.confirm(mm);
		         	 if(flag){	         	 	
		         	 	var mmwidth = window.screen.availWidth;//屏幕宽度
		         		var ml = 378;
		         		var mt = 200;
		         		if(mmwidth==1024){
		         			ml = 275;
		         		}
		         		$('#showmsg').css({"margin-top":mt,"margin-left":ml,"display":"block"});
		         		savaUpFile();	
		         	 }		         	 	         	         	 
		         }
          }
         	
        },'-',{
          type : 'button',
          text : '查看号码',
          bodyStyle : 'edit',
          useable : bflags[1],
          handler : function(){
          	//alert("查看号码");
        	  var obj=new Object();
				obj.name='终端号码查看';
				obj.id='TERMINAL_NUMBER_VIEW';
				obj.target = '/mantoeye/terminalmanager/terminaluser_list.jsp?1=1';
				parent.newTab(true,obj,'1');
          }
        }]
      });
	  toolbar.render();
    });
function myquery(){
	GT.$grid('datagrid').refresh();
}   
    
function savaUpFile(){
	$.ajax({
			type : "post",
			url : "/terminalmanager_saveUpFile.do",
			data : {
			},
			success : function(xml) {
				$('#showmsg').css({'display':'none'});	
				myquery();
				alert(xml);				
			},
			error : function() {
				alert('上传文件出错!');
				$('#showmsg').css({'display':'none'});	
			}
	});
}

</script>