<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>�ޱ����ĵ�</title>
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="../../skin//Default/css/maincontent.css" />
		<!-- 请根据语言和系统编码,自行选择引入的 gt_msg_...js 文件 (默认的msg文件为UTF-8编码) -->
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript" src="/tools/datepicker/WdatePicker.js"></script>
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
		<div id="maincontent">
			<div style="width: 100%; height: 5px; background-color: #D3E0F2;"></div>
			<div id="query_condition">
				<div
					style="background: url('/skin/Default/images/MantoEye/maincontent/query_up.gif') no-repeat; width: 100%; height: 4px; "></div>
				<div
					style="background: url('/skin/Default/images/MantoEye/maincontent/query_down.gif') repeat-y; width: 100%; height: auto;  margin-top: 0px; padding-top: 0px;">
					<table cellspacing="0px" cellpadding="0px;boder="0">
						<tr valign="middle">
							<td class="condition_name">
								时间粒度：
							</td>
							<td>
								<div class="select">
									<div>
									<select name="timeLevel" id="d_timeLevel">
										<option value="montd"
											<c:if test="${fn:contains(timeLevel,'month') }">selected</c:if>>
											月
										</option>
										<option value="week"
											<c:if test="${fn:contains(timeLevel,'week') }">selected</c:if>>
											周（周日~周六）
										</option>
										<option value="day"
											<c:if test="${fn:contains(timeLevel,'day') }">selected</c:if>>
											天
										</option>
										<option value="hour"
											<c:if test="${fn:contains(timeLevel,'hour') }">selected</c:if>>
											小时
										</option>
									</select>
									</div>
								</div>
							</td>
							<td class="condition_name">
								时间跨度：
							</td>
							<td>
								<input type="text" class="Wdate" style="height:15px;"
									onclick="new WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})"
									name="searchDateStart" value="${searchDateStart}"
									id="d_searchDateStart"/>
							<td style="width:5px;">
								&nbsp;到&nbsp; 
							</td>
							
							<td>		
								<input type="text" class="Wdate" style="height:15px;"
									onclick="new WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})"
									name="searchDateEnd" value="${searchDateEnd}"
									id="d_searchDateEnd"/>
							<td>
							</td>
							<td width="310px"></td>
							<td>
								<div id="mainbtn">
									<ul>
										<li>
											<a href="javascript:query();"  　title="查询"><span>查询</span>
											</a>
										</li>
										<!--  
										<li>
											<a href="#"  title="重置"><span>重置</span>
											</a>
										</li>
										-->
									</ul>

								</div>
							</td>
						</tr>
					</table>
				</div>
				<!--  <div
					style="background: url('/skin/Default/images/MantoEye/maincontent/query_right.gif') no-repeat; width: 4px; height: 24px; float: left;"></div>-->
			</div>
			<div style="height: 5px; width: 100%; background-color: #D3E0F2;"></div>
			<div id="imgtitle" style="height: 25px; width: 100%;">
				<div class="lefttitle"></div>
				<div class="middletitle">
					<div class="title">
						全网流量图形
					</div>
					<div class="tools">
						<!--<img src="../../skin/Default/images/MantoEye/maincontent/open.gif" alt="调整视窗" onclick="openWindow(1);"></img>&nbsp;&nbsp;-->
						<!--<img src="../../skin/Default/images/MantoEye/maincontent/close.gif" alt="关闭视窗" onclick="closeWindow(1);"></img>-->
					</div>
				</div>
				<div class="righttitle"></div>
			</div>
			<div id="jx" style="width: 100%; height: 20px;"></div>

			<div id="imgarea" class="imgarea">
				<iframe name="frm3"
					src="/flash/LineColumnChart.html?getdata=xml&dataflag=1"
					scrolling="no" id="frm3"
					style="HEIGHT: 230px; VISIBILITY: inherit; width: 1025px; Z-INDEX: 1"
					marginwidth="1" marginheight="1" frameborder="0" frameborder="0"
					align="middle">
				</iframe>
			</div>
			<div style="width: 100%; height: 5px; background-color: #D3E0F2;"></div>
			<div id="datatitle"
				style="height: 25px; width: 100%; margin-top: 0px;">
				<div class="lefttitle"></div>
				<div class="middletitle">
					<div class="title">
						全网流量数据
					</div>
					<div class="tools">
						<!--  <img src="../../skin/Default/images/MantoEye/maincontent/open.gif" alt="调整视窗" onclick="openWindow(2)"></img>&nbsp;&nbsp;-->
						<!--<img src="../../skin/Default/images/MantoEye/maincontent/close.gif" alt="关闭视窗" onclick="closeWindow(2)"></img>-->
					</div>
				</div>
				<div class="righttitle"></div>
				<div style="width: 100%; height: 2px;"></div>
				<div class="gt-panel"
					style="width: 100%; margin: 0px; margin-bottom: 2px;">
					<div id="grid1_container">
					</div>
				</div>
			</div>
	</body>
</html>

<script type="text/javascript">
var dsConfig= {

	//data : data1 ,

uniqueField : 'no' ,

	fields :[
		{name : 'fullDate'        },
		{name : 'intUpFlush'      },
		{name : 'intDownFlush'    },
		{name : 'totalFlush'    },
		{name : 'intImsis' }
	]
};
var colsConfig = [
		{ id : 'fullDate'      , header : "时间" , width : 170   },
		{ id : 'intUpFlush'    , header : "上行流量" , width : 170  },
		{ id : 'intDownFlush'     , header : "下行流量" , width : 170   },
		{ id : 'totalFlush'  , header : "总流量" , width : 170
		},
		{ id : 'intImsis' , header : "用户数" , width : 170   },

		{ id : 'detail'   , header : "操作" , width : 200,sortable:false, 
			//renderer : '<a href=".?no=@{no}" style="margin-left:3px;" >空间分布</a>' 
			renderer:'<img style="cursor: hand" src="/skin/Default/images/MantoEye/icon/space.png" alt="空间分布" onclick="distribute(\'1\',\'@fullDate\')" />'
															
			/*
			renderer : function(value ,record,columnObj,grid,colNo,rowNo){
				return '&#160;<a href=".?no='+record['no']+'" style="margin-left:3px;" >&#160;'+ record['name'] +' 的详细信息&#160;</a>';
			}
			*/
		}
];

var gridConfig={

	id : "mygrid",
	
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/totalDistribute_getSortedList.do?1=1',
	exportURL :'/totalDistribute_export.do?1=1' ,

	//saveURL : '/flexiGridTest_list1.do?1=1' ,
	
	dataset : dsConfig ,

	columns : colsConfig ,
	
	width:1055,
	height:500,
	resizable : true,
	showGridMenu : true,
	allowCustomSkin : true,
	allowFreeze : true,
	allowGroup : true,
	allowHide : true,

	container : 'grid1_container', 

	toolbarPosition : 'bottom',

	toolbarContent : 'skin | nav | goto | pagesize | reload | xls print | state' ,

	beforeSave : function(reqParam){
		//alert(GT.$json(reqParam) ) ;
		//GT.$grid('grid1').reload(true);
		//return false;
	},
	pageSize : 20 ,
	
	remoteSort : true ,
	
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
	//alert( GT.toJsonString(this.getInsertedRecords()) )
//alert("****"+mygrid.columnMap[1]);
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


