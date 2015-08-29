<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="../../skin//Default/css/maincontent.css" />
		<!-- 请根据语言和系统编码,自行选择引入的 gt_msg_...js 文件 (默认的msg文件为UTF-8编码) -->
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
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
						<table cellspacing="0" cellpadding="0" border="0"
							bgcolor="#ffffff" style="width: 100%;">
							<tr>
								<td>
								
									<table cellspacing="0" cellpadding="0" border="0" width="100%" class="menubar">
									<tr valign="top">
												<td width="4px" height="24px">
													<div class="lefttitle"></div>
												</td>
												<td width="100%" height="24px" >
													<div class="middletitle">大流量用户阀值</div>
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
									<div class="gt-panel"
										style="width: 100%; margin: 0px; margin-bottom: 2px;">
										<div id="grid1_container"></div>
									</div>
								</td>
							</tr>

						</table>
					</td>
					<td width="2"></td>
				</tr>
				<tr>
				<td colspan="2" height="3px"></td>
			</tr>
			</table>
	</body>
</html>

<script type="text/javascript">
var dsConfig= {

	//data : data1 ,

uniqueField : 'no' ,

	fields :[
		{name : 'bigFlush'        },
		{name : 'bigFlushValue'      },
	]
};
var colsConfig = [
		{ id : 'bigFlush'      , header : "描述" , width : 400   },
		{ id : 'bigFlushValue'    , header : "大流量阀值(MB)" , width : 400 ,editable:true ,  editor: { type :'text' ,validRule : 'R,integer' } }
		
];

var gridConfig={

	id : "mygrid",
	
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/valueset_getList.do?1=1',
	exportURL :'/valueset_export.do?1=1' ,

	saveURL : '/valueset_saveValue.do?1=1' ,
	
	dataset : dsConfig ,

	columns : colsConfig ,
	
	width:1025,
	height:500,
	resizable : true,
	showGridMenu : true,
	allowCustomSkin : true,
	allowFreeze : true,
	allowGroup : true,
	allowHide : true,
	toolbarPosition : 'top',
	toolbarContent :  'reload |  save' ,
	container : 'grid1_container', 

	beforeSave : function(reqParam){
		alert(GT.$json(reqParam) ) ;
		
	},
	pageSize : 20 ,
	
	
	pageSizeList : [10,20,50,100],
	defaultRecord :  { no : 111 , name :'', age : '', gender : 'U' , english : 50 , math :50 },

//	customHead : 'myHead',
	onComplete:loadComplate


};

var mygrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	mygrid.render();
} );
function loadComplate(){
	var totalRecords = mygrid.getAllRows().length;//获取加载到的数据记录数目
	var width=window.screen.availWidth;
	if(width==1024){
		//设置列表的宽高
		mygrid.setSize(780,(totalRecords+3) * 22);
	}else{
		mygrid.setSize(1035,(totalRecords+3) * 22);
	}
}


//// 查询表单的查询函数 ////
function query() {
	var param={
		filter_LIKE_searchDateStart : GT.U.getValue(GT.$('d_searchDateStart')),
		filter_LIKE_timeLevel : GT.U.getValue(GT.$('d_timeLevel')),
		filter_LIKE_searchDateEnd : GT.U.getValue(GT.$('d_searchDateEnd'))
	};
	GT.$grid('mygrid').query( param );
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

function openWindow(i){
	if(i==1){
		var $dataimg=$("#dataimg");
		if(document.getElementById("imgarea").style["display"] == "none"){
			document.getElementById("imgarea").style["display"] = "block";
			document.getElementById("jx").style["display"] = "block";
			$dataimg.height(231);
			$dataimg.width(1025);
			return;
		}
		if($dataimg.height()==231){
			$dataimg.height(500);
			$dataimg.width(1025);
		}else{
			$dataimg.height(231);
			$dataimg.width(1025);
		}
	}else{
		if(document.getElementById("imgarea").style["display"] == "block"){
			document.getElementById("imgarea").style["display"] = "none";
			document.getElementById("jx").style["display"] = "none";
		}else{
			document.getElementById("imgarea").style["display"] = "block";
			document.getElementById("jx").style["display"] = "block";
		}
	}
}

function closeWindow(j){
	
	if(j==1){
		document.getElementById("imgarea").style["display"] = "none";
		document.getElementById("jx").style["display"] = "none";
	}else{
		var $dataimg=$("#dataimg");
		$dataimg.height(500);
		$dataimg.width(1025);
	}
}

function initChartWidth(){
// alert("**initChartWidth**");
 return "1025|230";
}
function charItemClick(label){
	alert("**success**"+label);
}

</script>


