<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>阀值设置</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="../../skin//Default/css/maincontent.css" />
		<!-- 请根据语言和系统编码,自行选择引入的 gt_msg_...js 文件 (默认的msg文件为UTF-8编码) -->
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
	</head>

	<!-- <script type="text/javascript" src="/js/jquery.corner.js"></script>
<script type="text/javascript">
	 $('#readyTest').corner();
    
    $(function(){
	
        $('div.inner').wrap('<div class="outer"></div>');
        $('p').wrap("<code></code>");
		$("#query_condition").corner("round 8px").parent().css('padding', '4px').corner("round 10px")
       
    });
    
	
</script> -->
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
												大流量阀值设置
											</div>
										</td>
										<td width="4px">
											<div class="righttitle"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>

						<tr valign="top">
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
				<td colspan="2" height="2px"></td>
			</tr>
		</table>
	</body>
</html>

<script type="text/javascript">
/**
脚本不出错
*/
$(document).ready(function(){
	window.onerror = killErrors;
})
function killErrors() {
	return true;
}

var initValue;
var dsConfig= {

	//data : data1 ,

uniqueField : 'no' ,

	fields :[
		{name : 'Id'        },
		{name : 'strTimeType'        },
		{name : 'intLimitValue'        },
		{name : 'descrite'      }
	]
};
var colsConfig = [
		{ id : 'Id'  , header : "ID" , width : 50 , 	editable:false ,  
		hidden:true
		},
		{ id : 'strTimeType'      , header : "时间粒度"  },
		{ id : 'intLimitValue'    , header : "大流量阀值(MB)",headAlign:'right' ,align:'right' , editable:true ,  editor: { type :'text' ,validRule : 'R,integer' }  },
		{ id : 'descrite'      , header : "描述"  }
];

var gridConfig={

	id : "datagrid",
	
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/valueset_query.do?1=1',
	exportURL :'/valueset_export.do?1=1' ,
	saveURL : '/valueset_saveValue.do?1=1' ,
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
	toolbarContent : 'state' ,
	beforeSave : function(reqParam){
		//alert(GT.$json(reqParam) ) ;
		//GT.$grid('grid1').reload(true);
		//return false;
	},
	pageSize : 10 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
//	customHead : 'myHead',
	onComplete:loadComplate


};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
} );

function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度
	//初始化grid
	initGridInfo(obj);
	
	var width=window.screen.availWidth;
	var totalwidth;
	if (width == 1024) {
		totalwidth = 800;
	} else {	
		totalwidth = 1056;
	}	
	datagrid.getColumn('strTimeType').setWidth(totalwidth*0.3);
	datagrid.getColumn('intLimitValue').setWidth(totalwidth*0.2);
	datagrid.getColumn('descrite').setWidth(totalwidth*0.5);
	
	var record = datagrid.getRecord(0);
	initValue=record.intLimitValue;
	
}



function save(){
      var record = datagrid.getRecord(0);
      var value=record.intLimitValue;
      var id=record.id;
      if(value!=initValue){
      	$.ajax({
		type : "post",
		url : "/valueset_saveone.do?1=1",
		data : {
			id:id,//TD标识
			value:value//开始时间
		},
		success : function(xml) {
			alert("修改阀值成功!");
			GT.$grid('datagrid').reload(true);
		},
		error : function() {
			alert('修改阀值成功!');
		}
	});
      }
}

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
		//border: 'top',
        items : [{
          type : 'button',
          text : '保存',
          bodyStyle : 'save',
          useable :  bflags[0],
          handler : save
        }]
      });
	  toolbar.render();
    });

</script>


