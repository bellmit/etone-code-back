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
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript" src="/js/nav.js"></script>
		<script type="text/javascript" src="/js/JsMap.js"></script>
		<style>
.dialog_main_class {
	text-align: left;
	display: none;
	background: #eceef3;
	border: 1px solid #336699;
	z-index: 99;
	position: absolute;
	width: 347px;
	height: 230px;
	display: none;
	overflow-x: hidden;
	overflow-y: hidden;
}

.dialog_head_class {
	padding: 0px;
	position: relative;
	height: 20px;
	width: 100%;
	border-bottom: 1px solid #b0c7d7;
	background-image: url(/mantoeye/dialog/toolbar_bg.gif);
}

.dialog_table_class input {
	margin: 15px 0 0 0px;
	height: 14px;
	width: 180px;
}

.dialog_table_class tr td {
	padding-left: 20px;
}

.dialog_table_class select {
	height: 20px;
	width: 185px;
}

.dialog_head_icon_class {
	float: left;
	display: block;
	margin: 2px;
}

.dialog_head_title_class {
	float: left;
	margin-top: 3px;
}

.dialog_button_class {
	width: 99%;
	text-align: center;
}

.dialog_button_class button {
	margin: 8px;
}

.dialog_button_class .dialog_button {
	vertical-align: middle;
	line-height: 18px;
	height: 21px;
	border: 1px solid #336699;
	background: url(/mantoeye/dialog/input_button_bg.gif) repeat-x 0px -2px;
}

.dialog_msg_class {
	height: 40px;
	width: 220px;
	background: #eceef3;
	border: 1px solid #336699;
	position: absolute;
	z-index: 99999;
	display: none;
}

.dialog_msg_class img {
	margin-left: 10px;
	margin-top: 8px;
}
</style>		
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
						<tr valign="top">
							<td>
								<table cellspacing="0" cellpadding="0" border="0" width="100%">
									<tr valign="top" style="font-size: 1px;">
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
													<td class="condition_name" style="width: 100px;">
														请选择业务类型:
													</td>
													<td class="condition_value">
														<select name="businessType" id="businessType"
															onchange="changeBusinessType(this);" style="height: 21px">
															<option value="1">
																数据业务分析
															</option>
															<option value="2">
																大流量用户
															</option>
															<option value="3">
																彩信业务
															</option>
															<option value="4">
																黑莓业务
															</option>
															<option value="5">
																终端分析业务
															</option>
															<!-- <option value="0">
																不属于任何业务
															</option> -->
														</select>
													</td>
													<td class="condition_name">
														请选择表类型:
													</td>
													<td class="condition_value">

														<select name="tableType" id="tableType"
															onchange="getAllTable(this);" style="height: 21px">


														</select>
													</td>
													<td class="condition_name">
														维度:
													</td>
													<td class="condition_value" style="width: 120px">
														<select name="dimension1" id="dimension1"
															onchange="changeDimension(this.value,1)"
															style="height: 21px">
														</select>
													</td>
													<td style="width: 120px">
														<select name="dimension2" id="dimension2"
															onchange="changeDimension(this.value,2)"
															style="height: 21px; display: none">
														</select>
													</td>
													<td>
														<select name="dimension3" id="dimension3"
															onchange="changeDimension(this.value,3)"
															style="height: 21px; display: none">
														</select>
													</td>
												</tr>
												<div class="dialog_msg_class" style="margin-top:0px;margin-left:300px;" id="showmsg" name="showmsg" style="display:none">
												<img src="/skin/Default/images/icon/16/loading.gif">
												</img>查询条件初始化中,请稍后...</div>
												<tr>
													<td class="condition_name">
														请选择表:
													</td>
													<td class="condition_value" style="width: 568px;"
														colspan="5">
														<select name="tableSelect" id="tableSelect"
															onchange="query(this);"
															style="height: 21px; width: 571px;">
														</select>
														<input type="hidden" value="" id="tablename" />
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
												即席查询&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:openHelp()">帮助</a>
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
									<div id="data_container">
										<table id="myHead" style="display: none">
											<tr>
												<td columnId='vcColumnNickName'>
													列名
												</td>
												<td columnId='strColumnType'>
													字段类型
												</td>
												<td columnId='setOut'>
													<input type="checkbox" id="outchk" alt="全选"
														onclick="selectAllOut(this.checked)" />
													设为输出
												</td>
												<td columnId='setCondition'>
													<input type="checkbox" id="cndchk" alt="全选"
														onclick="selectAllCnd(this.checked)" />
													设为条件
												</td>
												<td columnId='operator1'>
													操作符一
												</td>
												<td columnId='conditionValue1'>
													条件值一
												</td>
												<td columnId='operator2'>
													操作符二
												</td>
												<td columnId='conditionValue2'>
													条件值二
												</td>
											</tr>
										</table>
									</div>
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
var businessType='1';//1-业务分析：2-大流量用户：3彩信分析：4-黑莓分析：5-终端业务分析：0-不属于人为业务
var tableType='1';//1-小时表:4-月表：2-天表：3-周表：5-原始表
var tableId;
var operator1=-1;
var operator2=-1;
var value1=-1;
var value2=-1;

var mymap = new Map();//维度-表(多个表之间是逗号) 
var btmap = new Map();//业务类型表类型-维度(多个维度之间是逗号) 
var tdmap = new Map();//表名-维度(多个维度之间是逗号) 


var tableTimeType='1';

function renderInitOper1(value ,record,columnObj,grid,colNo,rowNo){
	operator1++;
	var id="operator1_"+operator1;
	var intColumnType=record.intColumnType.toString();
	var fieldName = record.vcColumnName.toString();
	if(intColumnType=="2"){
		return "<select id=\""+id+"\" onchange=\'setVisiable(this);\' disabled  class='operator1' style='height:20px;font-size:10px;width:100px;' >"
					+"<option value=''>请选择操作符</option>"
					+"<option value='='>=</option>"
					+"<option value='like'>like</option>"
					+"</select>";
					
	}else{
		var shtml = "<select id=\""+id+"\" onchange=\'setVisiable(this);\' disabled  class='operator1' style='height:20px;font-size:10px;width:100px;' >"
					+"<option value=''>请选择操作符</option>";
			shtml = shtml +"<option value='='>=</option>"
			if(fieldName!='intRaitype'){
				shtml = shtml +"<option value='>'>></option>"
					shtml = shtml +"<option value='>='>>=</option>"
					shtml = shtml +"<option value='<'><</option>"
					shtml = shtml +"<option value='<='><=</option>"
			}
					
			shtml = shtml+"</select>";
		return shtml;
	}
}
function renderInitOper2(value ,record,columnObj,grid,colNo,rowNo){
	var intColumnType=record.intColumnType.toString();
	var fieldName = record.vcColumnName.toString();
	var shtml="<input type='hidden' class = 'columntype' value='"+intColumnType+"'>"
	operator2++;
	var id="operator2_"+operator2;
	if(intColumnType=="2"){
		shtml=shtml+ "<select id=\""+id+"\" onchange=\'setVisiable(this);\' disabled class='operator2' style='height:20px;font-size:10px;width:100px;'>"
					+"<option value=''>请选择操作符</option>"
					+"<option value='='>=</option>"
					+"<option value='like'>like</option>"
					+"</select>";
	}else{
		shtml = shtml+"<select id=\""+id+"\" onchange=\'setVisiable(this);\' disabled  class='operator2' style='height:20px;font-size:10px;width:100px;' >"
					+"<option value=''>请选择操作符</option>";
			shtml = shtml +"<option value='='>=</option>"
			if(fieldName!='intRaitype'){
				shtml = shtml +"<option value='>'>></option>"
					shtml = shtml +"<option value='>='>>=</option>"
					shtml = shtml +"<option value='<'><</option>"
					shtml = shtml +"<option value='<='><=</option>"
			}
					
					shtml = shtml+"</select>";
		
	}
	return shtml;
}

	function filterChar(args){
		
			var dd=args.value.replace(/[^\d]/g,'');
			args.value=dd;
	}
	
	function filterClip(){
		return clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''));
	}
function buildConditionHtml(fieldName,intColumnType,clazz){
	if(fieldName=='dtStatTime'){
		return buildTimeSelecter(clazz);
	}else if(fieldName=='intMonth'){
		//return buildSelectHtml([1,2,3,4,5,6,7,8,9,10,11,12],[1,2,3,4,5,6,7,8,9,10,11,12],clazz,1);
		return buildDateValue(clazz,'m');
	}else if(fieldName=='intDay'){
		//return buildSelectHtml([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],clazz,1);
		return buildDateValue(clazz,'d');
	}else if(fieldName=='intHour'){
		//return buildSelectHtml([0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23],clazz,0);
		return buildDateValue(clazz,'h');
	}else if(fieldName=='intYear'){
		//return buildSelectHtml([2009,2010,2011,2012,2013,2014,2015],[2009,2010,2011,2012,2013,2014,2015],clazz,2010);
		return buildDateValue(clazz,'y');
	}else if(fieldName=='intWeek'){
		return buildDateValue(clazz,'w');
	}else if(fieldName=='intRaitype'){
		return buildSelectHtml([1,2],['GPRS','TD'],clazz,1);
	}else{
		if(intColumnType=='1'){
			return '<input onkeyup=\"filterChar(this)\" style=\"display:none;\" onbeforepaste=\"filterClip();\" class=\"'+clazz+'\" style=\"height:18px;\">';
		}else{
			return "<input  type='text' class='"+clazz+"' style=\"display:none;\" style='height:18px;' >";
		
		}
	}	
}
function buildTimeSelecter(clazz){
	if(tableTimeType=='1'){//onpicked:function(){this.value = $dp.cal.getP(\'W\');},
		return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({dateFmt:\'yyyy-MM-dd HH:00:00\'});" />';
	}else if(tableTimeType=='2'){
		return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({dateFmt:\'yyyy-MM-dd 00:00:00\'});" />';
	}else if(tableTimeType=='4'){
		return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({dateFmt:\'yyyy-MM-01 00:00:00\'});" />';
	}else{
		return "<input  type='text' class='"+clazz+"' style=\"display:none;\" style='height:18px;' >";
	}
}
function buildDateValue(clazz,flag){
	if(flag=='y'){
		return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({dateFmt:\'yyyy\'});" />';
	}else if(flag=='m'){
		return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({dateFmt:\'M\'});" />';		
	}else if(flag=='d'){
		return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({dateFmt:\'d\'});" />';		
	}else if(flag=='h'){
		return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({dateFmt:\'HH\'});" />';
	}else if(flag=='w'){
		//return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({onpicked:function(){this.value = $dp.cal.getP(\'W\');},dateFmt:\'yyyy-MM-dd\'});" />';
		return '<input type="text" readonly class="'+clazz+'" style="display:none;height:17px;" onclick="new WdatePicker({dateFmt:\'yyyy-MM-dd\'});" />';
	}
}
function buildSelectHtml(values,fields,clazz,selectedval){
	var html = "<select class='"+clazz+"' style=\"display:none;\" style='height:19px;' >";
	for(var i =0 ; i<values.length ;i++){
		html = html + "<option value='"+values[i]+"'   ";
		if(values[i]==selectedval){
			html = html + ' selected ';
		}
		html = html + ">"+fields[i]+"</option>"
	}
	html = html + "</select>";
	return html;
}
function renderInitValue1(value ,record,columnObj,grid,colNo,rowNo){
	var intColumnType=record.intColumnType.toString();
	var fieldName = record.vcColumnName.toString();
	return buildConditionHtml(fieldName,intColumnType,'value1');	
}
function renderInitValue2(value ,record,columnObj,grid,colNo,rowNo){
	var intColumnType=record.intColumnType.toString();	
	var fieldName = record.vcColumnName.toString();
	return buildConditionHtml(fieldName,intColumnType,'value2');	
}
function renderInitSet1(value ,record,columnObj,grid,colNo,rowNo){
	var vcColumnName=record.vcColumnName.toString();
	var vcColumnNickName=record.vcColumnNickName.toString();
	var intColumnType=record.intColumnType.toString();
	var nameAndNickName=vcColumnName+":"+vcColumnNickName+":"+intColumnType;
	return "<input  type='checkbox' name='outchk' value=\""+nameAndNickName+"\" class='set1'  style='height:20px;width:20px;' >";
}

function renderInitSet2(value ,record,columnObj,grid,colNo,rowNo){
	value1++;
	var vcColumnName=record.vcColumnName.toString();
	var intColumnType=record.intColumnType.toString();
	var vcColumnNickName=record.vcColumnNickName.toString();
	var nameAndType=vcColumnName+":"+intColumnType+":"+vcColumnNickName;
	if(vcColumnName=='nmImei'){
		return "<input id=\""+value1+"\" name='cndchk' onclick=\'setHidden(this);\'  type='checkbox' disabled value=\""+nameAndType+"\" class='set2'  style='height:20px;width:20px;' >";
	}else{
		return "<input id=\""+value1+"\" name='cndchk' onclick=\'setHidden(this);\'  type='checkbox' value=\""+nameAndType+"\" class='set2'  style='height:20px;width:20px;' >";
	}
}
/**
*根据选择条件来隐藏显示操作符
*
*/
function setHidden(args){
	var id=args.id;
	$oper1=$('.operator1');
	$oper2=$('.operator2');
	$value1=$('.value1');
	$value2=$('.value2');
	$columntype = $('.columntype');
	var operobj1=$oper1.get(id);
	var operobj2=$oper2.get(id);
	var valueobj1=$value1.get(id);
	var valueobj2=$value2.get(id);
	var ccolumntype =$columntype.get(id); 
	//alert(ccolumntype.value);
	if(args.checked==true){
		operobj1.disabled=false;
		if(ccolumntype.value!='2'){
			operobj2.disabled=false;
		}
		operobj1.selectedIndex=1;
		valueobj1.style["display"]="block";
	}else{
		operobj1.disabled=true;
		operobj2.disabled=true;
		operobj1.selectedIndex=0;
		operobj2.selectedIndex=0;
		valueobj1.value="";
		valueobj1.style["display"]="none";
		valueobj2.value="";
		valueobj2.style["display"]="none";
	}
	
}
function setVisiable(args){
	var id=args.id;
	var ids=id.split("_");
	$value1=$('.value1');
	$value2=$('.value2');
	var valueobj1=$value1.get(ids[1]);
	var valueobj2=$value2.get(ids[1]);
	if(ids[0]=="operator1"){
		if(args.value==''){
			valueobj1.value="";
			valueobj1.style["display"]="none"
		}else{
			valueobj1.style["display"]="block";
		}
	}else{
		if(args.value==''){
			valueobj2.value="";
			valueobj2.style["display"]="none"
		}else{
			valueobj2.style["display"]="block";
		}
	
	}
}
var dsConfig= {

	uniqueField : 'null' ,
	fields :[
		{name : 'vcColumnNickName'      },
		{name : 'strColumnType'      }
	]
};
var colsConfig = [
		{ id : 'vcColumnNickName'    , header : "列名"   },
		{ id : 'strColumnType'    , header : "字段类型"   },
		{ id : 'setOut'      ,checkType:'checkbox', header : "设为输出",sortable:false,renderer:renderInitSet1    },
		{ id : 'setCondition'      ,checkType:'checkbox', header : "设为条件",sortable:false,renderer:renderInitSet2    },
		{ id : 'operator1'    , header : "操作符一" ,sortable:false,renderer:renderInitOper1  },
		{ id : 'conditionValue1'      , header : "条件值一",sortable:false,renderer:renderInitValue1   },
		{ id : 'operator2'    , header : "操作符二" ,sortable:false,renderer:renderInitOper2 },
		{ id : 'conditionValue2'    , header : "条件值二",sortable:false,renderer:renderInitValue2 }
		
];
var gridConfig={
	id : "datagrid",
	/* loadURL 支持函数, 该函数返回值是response对象 */
	loadURL :'/immediatelyquery_query.do?1=1',
	exportURL :'/immediatelyquery_export.do?1=1',
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
	toolbarContent : 'state' ,
	pageSize : 60 ,
	remoteSort : false ,
	pageSizeList : [10,20,50,100],
	customHead : 'myHead',
	onComplete:loadComplate
};

var datagrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
	datagrid.render();
});

			
function loadComplate(){
	var  obj = new Object ();//使用对象传参,以防以后修改
	obj.datagrid = datagrid;
	obj.hideColumn = 0 ;//隐藏的列数目
	obj.isCheckbox = false;//是否可选择
	obj.otherHeight = 0 ; //必须定义,因为有些页面不同，所以此处指定预留高度

	operator1=-1;
	operator2=-1;
	value1=-1;
	value2=-1;
	//设置宽度
	//判断是否查询到数据
	judgeData(datagrid);
	
	//初始化grid
	initGridInfo(obj);
	
	document.getElementById('outchk').checked = false;
	document.getElementById('cndchk').checked = false;
}

/**
*根据所选表名称  去查询相应的字段名
*/
function query(args) {
	tableId=$(args).val().split(":")[0];
	tableTimeType = $('#tableType').val();
	var param={
		 tableId:tableId
	};
	GT.$grid('datagrid').query( param );
}

/**
*选择业务类型调用此方法
*/
function changeBusinessType(args){
	businessType=$(args).val();
	$('#tableSelect option').each(function(){
		if($(this).val()!='-1'){
			$(this).remove();
		}
	});
	var xml=document.getElementById('tablename').value;
	var $tableName=$(changeXMLObj(xml));
	var stabletype1 = ',';
	$tableName.find('da').each(function(){
		var btype=$(this).children('businesstype').text();
		var ttype=$(this).children('tabletype').text();
		if(btype==businessType){
				if(stabletype1.indexOf(','+ttype+',')==-1){
					stabletype1 = stabletype1 + ttype + ',';
				}
			}	
	});
	changeTableTypeSel(stabletype1);
	$('#tableType').get(0).selectedIndex=0;
	initDimension(businessType,"_");
}
var thistypetables = "";
/**
*根据选择表类型 来获取对应所有表
*/
function getAllTable(args){
	tableType=$(args).val();
	var xml=document.getElementById('tablename').value;
	var $tableName=$(changeXMLObj(xml));
	$('#tableSelect option').each(function(){
		if($(this).val()!=-1){
			$(this).remove();
		}
	});
	var dims=",";
	thistypetables = "";
	$tableName.find('da').each(function(){
		var btype=$(this).children('businesstype').text();
		var ttype=$(this).children('tabletype').text();
		if(businessType==btype&&tableType==ttype){
				var id=$(this).children('id').text();
				var name=$(this).children('name').text();
				var nickname=$(this).children('nickname').text();
				var idname=id+":"+name;
				thistypetables = thistypetables + "<option value='"+idname+"'>"+nickname+"</option>";								
		}		
	});
	$('#tableSelect').append(thistypetables);	
	//alert(stabletype);
	query(document.getElementById("tableSelect"));
	initDimension(businessType,tableType);
}

//数字类型 根据选择的操作符合  所输入的值 来生成相应的SQL语句
function isOperator(oper1,oper2,value1,value2,columnName){
	var oper11=oper1.substring(0,1);
	var oper12=oper2.substring(0,1);
	
	if((oper11==oper12)||(oper11=="="||oper12=="=")){
		return "("+columnName+""+oper1+""+value1+" or "+columnName+""+oper2+""+value2+")";
	}else{
		if(oper11==">"&&oper12=="<"){
			if(parseInt(value1)>=parseInt(value2)){
				
				return "("+columnName+""+oper1+""+value1+" or "+columnName+""+oper2+""+value2+")";
			}else{
				return "("+columnName+""+oper1+""+value1+" and "+columnName+""+oper2+""+value2+")";
			}
		}else if(oper11=="<"&&oper12==">"){
			if(parseInt(value1)>=parseInt(value2)){
				return "("+columnName+""+oper1+""+value1+" and "+columnName+""+oper2+""+value2+")";
			}else{
				return "("+columnName+""+oper1+""+value1+" or "+columnName+""+oper2+""+value2+")";
			}
		}
	}
}
/**
*组装SQL语句
*/
function publishSQL(){

	var tableName=$('#tableSelect').val().split(":")[1];
	var $set1=$('.set1');
	var checkCount=0;
	var outName='';//所有选择输出项列名
	var outNickName='';//所有选择输出项列别名
	var outDataTypes='';
	$set1.each(function(){
		if($(this).get(0).checked==true){
			checkCount++;
			var nameAndNickName=$(this).val().split(":");
			outName=outName+nameAndNickName[0]+",";
			outNickName=outNickName+nameAndNickName[1]+",";
			outDataTypes = outDataTypes + nameAndNickName[2] +',';
		}
	})
	outName=outName.substring(0,outName.length-1);
	outNickName=outNickName.substring(0,outNickName.length-1);
	outDataTypes=outDataTypes.substring(0,outName.length-1);
	var outNames=outName.split(',');
	var sql="select ";
	if(checkCount==0){
		alert("请选择最少一项输出字段!");
		return ;
	}else{
		for(var i=0;i<outNames.length;i++){
			if(i==outNames.length-1){
				sql=sql+outNames[i]
			}else{
				sql=sql+outNames[i]+","
			}
		}
	}
	sql=sql+" from "+tableName+" where 1=1 "
	
	var $set2=$('.set2');//设为输出所选项
	var $values1=$('.value1');//所输入条件值一
	var $values2=$('.value2');//所输入条件值二
	var $operator1=$('.operator1');//所选操作符一
	var $operator2=$('.operator2');//所选操作符二
	
	var conditionCount = 0;
	
	$set2.each(function(i){
		if($(this).get(0).checked==true){
			conditionCount++;
			var nameAndColumnType=$(this).val();
			var nameAndType=nameAndColumnType.split(":");
			var flag1=false;
			var flag2=false;
			var inValue1=$values1.get(i).value;
			var inValue2=$values2.get(i).value;
			var operatorValue1=$operator1.get(i).value;
			var operatorValue2=$operator2.get(i).value;
			var columnName=nameAndType[0];
			if(operatorValue1!=''){
				
				flag1=true;
			}
			if(operatorValue2!=''){
				flag2=true;
			}
			
			//当为数字类型时 输入值没空  默认为0
			if(nameAndType[1]=='1'){
				if(inValue1==null||inValue1==''){
					inValue1=0;
				}
				
				if(inValue2==null||inValue2==''){
					inValue2=0;
				}
			}
			
			//操作符一  和 操作二都被选择的	情况
			if(flag1==true&&flag2==true){
				if(nameAndType[1]=='1'){//当为数字型  并且选择了两个操作符情况  存在多种值
					sql=sql+" and "+isOperator(operatorValue1,operatorValue2,inValue1,inValue2,columnName)+" ";
				}else if(nameAndType[1]=='2'){
					if(operatorValue1=="like"){
						inValue1="$"+inValue1+"$";
					}
					if(operatorValue1=="like"){
						inValue2="$"+inValue1+"$";
					}
					sql=sql+" and "+"("+columnName+" "+operatorValue1+" '"+inValue1+"' or "+columnName+" "+operatorValue2+" '"+inValue2+"') ";
				}else{
					sql=sql+" and "+"("+columnName+" "+operatorValue1+" '"+inValue1+"' and "+columnName+" "+operatorValue2+" '"+inValue2+"') ";
				}
			}else if(flag1==true&&flag2==false){//选择第二个操作符情况
				if(nameAndType[1]=='1'){
					sql=sql+" and "+columnName+operatorValue1+inValue1+" ";
				}else if(nameAndType[1]=='2'){
					if(operatorValue1=="like"){
						inValue1="$"+inValue1+"$";
					}
					sql=sql+" and "+columnName+" "+operatorValue1+" '"+inValue1+"' ";
				}else{
					sql=sql+" and "+columnName+" "+operatorValue1+"'"+inValue1+"' ";
				}
			}else if(flag1==false&&flag2==true) {//选择第二个操作符情况
				if(nameAndType[1]=='1'){
					sql=sql+" and "+columnName+operatorValue2+inValue2+" ";
				}else if(nameAndType[1]=='2'){
					if(operatorValue2=="like"){
						inValue2="$"+inValue2+"$";
					}
					sql=sql+" and "+columnName+" "+operatorValue2+" '"+inValue2+"' ";
				}else{
					sql=sql+" and "+columnName+" "+operatorValue2+"'"+inValue2+"' ";
				}
			}
			
			if('intWeek' == columnName){
				flagColumnNameWeek = '@'+columnName+'~'+operatorValue1+'~'+inValue1+'~'+operatorValue2+'~'+inValue2+'~'+flag1+'~'+flag2;
				flagColumnNameValue = true;
			} 
		}
		
	});	
	
	if(flagColumnNameValue){
		sql = sql + flagColumnNameWeek;
		flagColumnNameValue = false;
	}
	
	openDisplayPage(sql,outNickName,outName,outDataTypes);

}

var flagColumnNameWeek = '';
var flagColumnNameValue = false;

function openDisplayPage(sql,ninkName,names,outDataTypes){
	var obj = new Object();
		obj.name='即席查询显示';
		obj.id='immediatelydisplay';
		obj.target = '/mantoeye/immediately/immediatelyQueryDisplay.jsp';
		parent.immediately_sql=sql;
		parent.immediately_ninkname=ninkName;
		parent.immediately_name=names;
		parent.immediately_types=outDataTypes;
		parent.newTab(true,obj,sql);
}

function changeXMLObj(text){//文本类型转换成xml对象
	var xmlDoc = null;  
	try //Internet Explorer  
	{  
	  xmlDoc=new ActiveXObject("Microsoft.XMLDOM");  
	  xmlDoc.async="false";  
	  xmlDoc.loadXML(text);  
	}  
	catch(e)  
	{  
	  try //Firefox, Mozilla, Opera, etc.  
	    {  
	    parser=new DOMParser();  
	    xmlDoc=parser.parseFromString(text,"text/xml");  
	    }  
	  catch(e) {}  
	}  
	return xmlDoc
}


//初始化grid工具栏
$(document).ready(function(){
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '查询',
          bodyStyle : 'search',
          useable : 'T',
          handler : function(){
           publishSQL();
          }
        }]
      });
	  toolbar.render();
});
       
    /**
    *初时化表
    */
    $(document).ready(function(){
		$.ajax({
			type : "post",
			url : "/immediatelyquery_queryAllTable.do",
			data : {
			},
			success : function(xml) {
				document.getElementById("tablename").value=xml;
				var stabletype1=",";
				$('#tableSelect').append("<option value='-1'>------------------------------------------------------------请选择表名------------------------------------------------------------</option>");
				thistypetables = "";
				$(changeXMLObj(xml)).find('da').each(function(){
					var btype=$(this).children('businesstype').text();
					var ttype=$(this).children('tabletype').text();
					var name=$(this).children('name').text();
					
					if(btype==businessType){
					if(stabletype1.indexOf(','+ttype+',')==-1){
						stabletype1 = stabletype1 + ttype + ',';
					}
					}
					if(businessType==btype&&tableType==ttype){
						var id=$(this).children('id').text();						
						var nickname=$(this).children('nickname').text();
						var idname=id+":"+name;
						thistypetables = thistypetables+"<option value='"+idname+"'>"+nickname+"</option>";
						
					}
					var tabledims = ',';
					/*维度处理*/
					$(this).children('dimensions').find('dim').each(function(){
						var dim=$(this).text();
						var busitt = btype+"_"+ttype;
						if(btmap.containsKey(busitt)){
							var dims = btmap.get(busitt);
							if(dims.indexOf(","+dim+",")==-1){
								dims = dims +dim+ ',';
								btmap.remove(busitt); 
								btmap.put(busitt,dims);
							}
						}else{
							btmap.put(busitt,","+dim+",");
						}
						//
						if(mymap.containsKey(dim)){
							var dims = mymap.get(dim);
							dims = dims +name + ',';
							mymap.remove(dim); 
							mymap.put(dim,dims);
						}else{
							mymap.put(dim,","+name+",");
						}
						//						
						tabledims = tabledims +dim + ',';
					});
					tdmap.put(name,tabledims);
				});
				$('#tableSelect').append(thistypetables);
				/*
				alert(mymap.size()+"-size");
					for(var i = 0;i<mymap.size();i++){
						var element = mymap.element(i);
						alert(element.key+":"+element.value);
						if(i>4) break;
				}
				alert(btmap.size()+"-size");
					for(var i = 0;i<btmap.size();i++){
						var element = btmap.element(i);
						alert(element.key+":"+element.value);
						if(i>8) break;
				}
				alert(tdmap.size()+"-size");
					for(var i = 0;i<tdmap.size();i++){
						var element = tdmap.element(i);
						alert(element.key+":"+element.value);
						if(i>8) break;
				}*/
				changeTableTypeSel(stabletype1);
				initDimension(businessType,tableType);
			},
			error : function() {
				alert('服务器出错,请联系管理员!');
			}
		});
	});	
var thistypetables1 = "";
var thistypetables2 = "";
var selectval1 = "";
//维度选择事件	
function changeDimension(val,flag){
	var dtables = mymap.get(val);
	$('#tableSelect option').each(function(){
		$(this).remove();
	});
	if(flag==1){//第一维
		$('#tableSelect').append("<option value='-1'>------------------------------------------------------------请选择表名------------------------------------------------------------</option>");
		$('#tableSelect').append(thistypetables);
	}else if(flag==2){//第二维
		$('#tableSelect').append(thistypetables1);
	}else{//第三维
		$('#tableSelect').append(thistypetables2);
	}
	if(val != '-1'){
	$('#tableSelect option').each(function(){
			var tablename  = $(this).attr('value');
			if(tablename!='-1'){			
			var tns = tablename.split(':')[1];
			if(dtables.indexOf(','+tns+',')==-1){
				$(this).remove();
			}
			}
	});
	}else{
		
	}
	if(flag==1){
		selectval1 = val;
		$('#dimension3').css('display','none');
		thistypetables1 = $('#tableSelect > option');
		initDimension2ByDim1(val);
	}else if(flag==2){
		thistypetables2 = $('#tableSelect > option');
		initDimension3ByDim2(val);
	}
	$('#tableSelect').attr("value",'-1');
	query(document.getElementById("tableSelect"));
}
//初始化第三维度选择框
function initDimension3ByDim2(val){
	if(val=='-1'){
		$('#dimension3').css('display','none');
	}else{
		$('#dimension3').css('display','block');
	}
	$('#dimension3 option').each(function(){		
		$(this).remove();		
	});
	var hasele = false;
	var dim3names = ',';
	$('#dimension3').append('<option value="-1" >--选择第三维度--</option>');
	$('#tableSelect option').each(function(){
			var tablename = $(this).attr('value').split(':')[1];
			var dimss = tdmap.get(tablename);
			if(dimss!=null){
				var dadim = dimss.split(',');
				for(var i=0;i<dadim.length;i++){	
					var thisd = dadim[i];		
					if(thisd!=null&&thisd!=''&&thisd!=val&&thisd!=selectval1){
						if(dim3names.indexOf(','+thisd+',')==-1){
							$('#dimension3').append('<option value="'+thisd+'" >'+thisd+'</option>');
							hasele = true;
						}
						dim3names = dim3names +thisd +',';
					}	
				}				
			}
	});
	if(!hasele){
		$('#dimension3').css('display','none');
	}
}		
//初始化第二维度选择框
function initDimension2ByDim1(val){
	if(val=='-1'){
		$('#dimension2').css('display','none');
	}else{
		$('#dimension2').css('display','block');
	}
	$('#dimension2 option').each(function(){		
		$(this).remove();		
	});
	var hasele = false;
	var dim2names = ',';
	$('#dimension2').append('<option value="-1" >--选择第二维度--</option>');
	$('#tableSelect option').each(function(){
			var tablename = $(this).attr('value').split(':')[1];
			var dimss = tdmap.get(tablename);
			if(dimss!=null){
				var dadim = dimss.split(',');
				for(var i=0;i<dadim.length;i++){	
					var thisd = dadim[i];		
					if(thisd!=null&&thisd!=''&&thisd!=val){
						if(dim2names.indexOf(','+thisd+',')==-1){
							$('#dimension2').append('<option value="'+thisd+'" >'+thisd+'</option>');
							hasele = true;
						}
						dim2names = dim2names +thisd +',';
					}	
				}				
			}
	});
	if(!hasele){
		$('#dimension2').css('display','none');
	}
}	
//初始化第一维度选择框	
function initDimension(busitype,tabletype){
	$('#dimension1 option').each(function(){
			$(this).remove();
	});
	var tt = busitype+"_"+tabletype;
	var dims = btmap.get(tt);
	if(dims!=null){
		var ss = dims.split(',');
		$('#dimension1').append('<option value="-1" >--请选择维度--</option>');
		for(var s=0;s<ss.length;s++){
		if(ss[s]!=null&&ss[s]!='')
		$('#dimension1').append('<option value="'+ss[s]+'" >'+ss[s]+'</option>');
		}
	}else{
		$('#dimension1').append('<option value="-1" >--无相关维度--</option>');
	}
	$('#dimension2').css('display','none');
	
	$('#showmsg').css('display','none');
	
}	
function changeTableTypeSel(val){
	$('#tableType option').each(function(){
		if($(this).val()!=-1){
			$(this).remove();
		}
	});
	$('#tableType').append('<option value="">--请选择表类型--</option>');
	if(val.indexOf(',1,')!=-1){
		$('#tableType').append('<option value="1" >小时表</option>');
	}
	if(val.indexOf(',2,')!=-1){
		$('#tableType').append('<option value="2" >天表</option>');
	}
	if(val.indexOf(',3,')!=-1){
		$('#tableType').append('<option value="3" >周表</option>');
	}
	if(val.indexOf(',4,')!=-1){
		$('#tableType').append('<option value="4" >月表</option>');
	}
	if(val.indexOf(',5,')!=-1){
		$('#tableType').append('<option value="5" >原始表</option>');
	}
	$('#tableType').attr('value',tableType);
}	
//选择全部
function selectAllOut(check) {
	var gridid = 'datagrid';
	var sy = document.getElementsByName('outchk');
	if(check){
		for (var i = 0; i < sy.length; i++) {
			sy[i].checked = true;
		}
	}else{
		for (var i = 1; i < sy.length; i++) {
			sy[i].checked = false;
		}
	}
}	
//选择全部
function selectAllCnd(check) {
	var gridid = 'datagrid';
	var sy = document.getElementsByName('cndchk');
	$oper1=$('.operator1');
	$oper2=$('.operator2');
	$value1=$('.value1');
	$value2=$('.value2');
	$columntype = $('.columntype');
	var imidx = -1;
	if(check){
		$(sy).each(function(){
			
		});
		for(var  i=0;i<sy.length;i++){
			if(sy[i].value.indexOf('nmImei')!=-1){
				sy[i].checked = false; 
				imidx = i-1;
			}else{
				sy[i].checked = true; 
			}
		}
		$('.operator1').each(function(){
			$(this).attr("disabled",false);
			$(this).attr("selectedIndex",1);
		});
		for(var  i=0;i<$columntype.length;i++){
			if($columntype[i].value!=2){
				$oper2[i].disabled = false;
			}
		}
		/*	$('.operator2').each(function(){
			$(this).attr("disabled",false);
			});
		*/	
		$('.value1').each(function(){
			$(this).css("display","block");
		});
		$('.value2').each(function(){
			$(this).attr("disabled",false);
		});
		if(imidx!=-1){
			$oper1[imidx].disabled = true;
			$oper2[imidx].disabled = true;
			$oper1[imidx].value= "";
			$value1[imidx].style["display"] = "none";
		}
	}else{
		$(sy).each(function(){
			$(this).attr("checked",false); 
		});
		$('.operator1').each(function(){
			$(this).attr("disabled",true);
			$(this).attr("selectedIndex",0);
		});
		$('.operator2').each(function(){
			$(this).attr("disabled",true);
			$(this).attr("selectedIndex",0);
		});
		$('.value1').each(function(){
			$(this).attr("value","");
			$(this).css("display","none");
		});
		$('.value2').each(function(){
			$(this).attr("value","");
			$(this).css("display","none");
		});
	}
}	
function openHelp(){
	showMMDialog('/mantoeye/immediately/help.jsp?1=1','','720px','540px');
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


