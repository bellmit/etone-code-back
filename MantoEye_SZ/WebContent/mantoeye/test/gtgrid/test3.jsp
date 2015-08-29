<%@ page contentType="text/html;charset=utf-8"%>
<html>
<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="Cache-Control" content="no-cache" />
<title>GT-Grid 教程示例</title>

<link rel="stylesheet" type="text/css" href="/tools/gt-grid/gt_grid.css" />

<!-- 请根据语言和系统编码,自行选择引入的 gt_msg_...js 文件 (默认的msg文件为UTF-8编码) -->
<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>


<script type="text/javascript" >
var dsConfig= {

	//data : data1 ,

	uniqueField : 'no' ,

	fields :[
		{name : 'id'        },
		{name : 'dtRecordTime'      },
		{name : 'vcLoginUser'    },
		{name : 'vcLogContent'    },
		{name : 'vcLoginIp' }
	]
};
var colsConfig = [
		{ id : 'id'      , header : "ID" , width : 50 ,hidden:true  },
		{ id : 'vcLoginIp' , header : "操作者IP" , width : 60 , align :'right'  },
		{ id : 'vcLoginUser'     , header : "操作者" , width : 50   },
		{ id : 'dtRecordTime'    , header : "操作时间" , width : 120  },
		{ id : 'vcLogContent'  , header : "日志内容" , width : 150 
		},
		{ id : 'detail'   , header : "详细信息" , width : 120,sortable:false,
			renderer : '<a href=".?no=@{no}" style="margin-left:3px;" >&#160;@{vcLoginUser} 的详细信息&#160;</a>' 
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
	loadURL :'/loginfogt_getSortedList.do?1=1',
	exportURL :'/loginfogt_exportList.do?1=1' ,

	//saveURL : '/flexiGridTest_list1.do?1=1' ,
	
	dataset : dsConfig ,

	columns : colsConfig ,
	
	width: 800,
	
	height:400,
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

	customHead : 'myHead',
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
		s_vcUserName : GT.U.getValue(GT.$('d_vcLoginUser')),
		filter_LIKE_vcLogContent : GT.U.getValue(GT.$('d_vcLogContent')),
		filter_LIKE_vcLoginIp : GT.U.getValue(GT.$('d_vcLoginIp'))
	};
	alert(mygrid);
	GT.$grid('mygrid').query( param );
}
</script>
</head>

<body>

<!-- 自定义列表头. -->
<!-- --><table id="myHead" style="display:none">
<tr>
	<td rowspan="2" columnId='id'>ID</td>
	<td colspan="2" >操作者</td>
	<td colspan="2">信息</td>
	<td rowspan="2"  columnId='detail'>操作</td>
</tr>
<tr>
	<td columnId='vcLoginIp'>IP</td>
	<td columnId='vcLoginUser'>名称</td>
	<td columnId='dtRecordTime'>时间</td>
	<td columnId='vcLogContent'>内容</td>
</tr>
</table>
<div class="gt-panel" style="width:500px;margin:5px;margin-bottom:15px;background-color:#ecf6ff" >
	<div class="gt-panel-head"><span>查询</span></div>
	<div class="gt-panel-body">
		<table>
			<tr>
				<td width="15%">操作者</td>
				<td width="35%">
					<input type="text"  id="d_vcLoginUser" value="" />
				</td>
				<td width="15%">操作内容</td>
				<td width="35%">
					<input type="text" id="d_vcLogContent" value="" />
				</td>	
			</tr>
			<tr>
				<td width="15%">操作者IP</td>
				<td width="35%">
					<input type="text"  id="d_vcLoginIp" value="" />
				</td>
				<td width="15%">起至时间</td>
				<td width="35%">
					<input type="text" id="d_vcLoginIp" value="" />
				</td>	
			</tr>
		</table>
	</div>
	<div class="gt-button-area">
		<input type="button" class="gt-input-button" value="查询" onclick="query()" />
		<input type="reset" class="gt-input-button" />
	</div>
</div>
<!-- grid的容器. -->
<div id="grid1_container" style="width:700px;height:300px">
</div>

<div style="width:500px;height:30px;background: url(/tools/gt-grid/skin/default/images/hd_row_bg.gif) repeat-x;"> <input id="dd" type="button" value="测试" onclick="test();"></div>

 </body>
</html>

<script type="text/javascript">

$(document).ready(function (){
	
})
	function test(){
		var $test=$("select");
		$($test.get(0).parentElement).bind("change",dd());
	}
	
	function dd(){
	 alert(1);
	}
</script>
