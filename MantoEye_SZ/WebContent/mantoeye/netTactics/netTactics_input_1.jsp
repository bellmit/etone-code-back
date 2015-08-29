<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>添加策略</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/modedialog.css" />
			<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
			<script type="text/javascript" src="/js/common_grid.js"></script>
			
<!-- 导入业务选择复选框 -->
		<link rel="stylesheet" href="/mantoeye/dialog/dialog.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
		<!-- 自动补全JS --> 
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript">
	//验证表单信息
	$(document).ready( function() {
		//聚焦第一个输入框
			$("#entity.vcTaskName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate( {
				rules : {
					"entity.vcTaskName": { 
        				required: true, 
        				remote:{
        					url:"/modules/keywordsTactics/keywords-tactics!checkUnique.do",
        					type:"post" ,
        					data:{"vcTaskName":
        							function (){
        								return $('#vcTaskName').attr('value')
        								}
        					}
        					}
    					},
					"entity.dtStartTime" :"required",
					"entity.dtEndTime" :"required",
					"business" :"required"
				},messages: {
					"entity.vcTaskName": {
						remote: "任务名称已存在"
					}
				},
				submitHandler: function(form) {  
					//alert(form);
					var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					var start = $('#intStartHour').attr("value");
					var end = $('#intEndHour').attr("value");
					if(parseInt(end)<parseInt(start)){
							alert("时间段选择错误！")
							return;
						}
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');
					$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
					form.submit();  
				}   
			});
		});
</script>
		<style>
#content_table_id  th {
	height: 22px;
	padding-left: 5px;
	width: 24%;
	text-align: right;
	font: normal 13px arial, tahoma, helvetica, sans-serif;
}
</style>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden">
		<form name="beanView" action="/modules/netTactics/net-tactics!save.do"
			id="beanForm" method="post">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0" width="100%"
							class="header">
							<tr>
								<td width="100%" height="24px">
									<div class="title">
										添加策略
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<div>
							<!----------- 表单信息 ------------------>
							<table width="100%" cellpadding="0" cellspacing="1"
								id="content_table_id"
								style="width: 100%; height: auto; margin: 0px; padding: 0px;">
								<tr>
									<th>
										策略名称：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" name="entity.vcTaskName" 
											style="width: 350px;" id="vcTaskName"
											value="${entity.vcTaskName}" />
									</td>
								</tr>
									
								<tr>
									<th>
										策略执行日期：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" class="Wdate" style="width: 158px;"
											readonly
											onclick="showStartForm()" onchange="changTime()"
											name="entity.dtStartTime" id="entity.dtStartTime"
											value="${entity.dtStartTime}" />
									&nbsp 至 &nbsp
										<input type="text" class="Wdate" style="width: 158px;"
											readonly
											onclick="showEndForm()"
											name="entity.dtEndTime" id="entity.dtEndTime"
											value="${entity.dtEndTime}" />
									</td>
								</tr>
								
								<tr>
									<th>
										策略执行时间段：
										<font color="red">*</font>
									</th>
									<td>
										<select id="intStartHour" name="entity.intStartHour" style="width:162px" onchange="changStartHour()" value="${entity.intStartHour}">
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
						</select>
							&nbsp 至 &nbsp
						<select id="intEndHour" name="entity.intEndHour" style="width:162px" onchange="changEndHour()" value="${entity.intEndHour}">
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
						</select>
									</td>
								</tr>
								<tr>
									<th>
										备注：
									</th>
									<td>
										<input type="text" name="entity.vcContents"
											style="width: 350px;" id="vcContents" value="${entity.vcContents}"
											 />
									</td>
								</tr>
							<tr>
									<th>
										网络内容选择：
										<font color="red">*</font>
									</th>
									<td>
										<input type="text" id="business" name="business" value="${business}" readonly onclick="showBusinessDialog()" style="width: 350px; height: 16px;" />
										<div style="height: 0px; border: 0px; overflow: hidden" id="business_dialog_hidden_id"></div>
										<input id="businessIds" type="hidden" name="businessIds" value="${businessIds}" />
									</td>
								</tr>
						<tr>
									<th>
									</th>
									<td >
										<font color="red">提示：由于慧眼系统负荷原因，初期关键字项目只支持5个自定义内容类别筛选。</font>
									</td>
										
								</tr>
							</table>
						</div>
					</td>
					
					<div style="display: none;">
						<s:submit id="btn_commit"></s:submit>
						<button id="btn_cancel" onclick="window.close()"></button>
					</div>
				</tr>
			</table>
			<div class="dialog_msg_class" style="margin-top:120px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
			<table width="66%" cellpadding="0" cellspacing="0"
				class="formitem_pagestyle" style="padding-top: 40px;">
				<tr>
					<td colspan="2">
						<table align="right">
							<tr>
								<td align="center">
									<div id="mainbtn">
										<ul>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_commit').click();" id="saveBtn" name="saveBtn"><span>保存</span>
												</a>
											</li>
											<li>
												<a href="#"
													onclick="document.getElementById('btn_cancel').click();" id="cancelBtn" name="cancelBtn"><span>关闭</span>
												</a>
											</li>
										</ul>
									</div>
								</td>
								<td width="10px"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script>
var date=new Date();
var today=date.getYear()+'-'+''+((date.getMonth()+1)>9?(date.getMonth()+1):("0"+(date.getMonth()+1)))+'-'+''+(date.getDate()>9?date.getDate():("0"+date.getDate())); 
function changTime(){
	var ssd = document.getElementById('entity.dtStartTime').value;
	/*if(ssd==today){
		$('#intStartHour').val(date.getHours()+1);
		$('#intEndHour').val(date.getHours()+1);
	}*/
}

function changStartHour(){
	/*var ssd = document.getElementById('entity.dtStartTime').value;
	var value = $('#intStartHour').attr("value");
	if(ssd==today){
		if(value<date.getHours()+1){
			alert("不能选择小于今天的时间段,今天已过这个时间段！");
			$('#intStartHour').val(date.getHours()+1);
		}
	}*/
	//$('#intEndHour').val($('#intStartHour').attr("value"));
}

function changEndHour(){
	/*var start = $('#intStartHour').attr("value");
	var end = $('#intEndHour').attr("value");
	if(parseInt(end)<parseInt(start)){
			alert("不能选择小于当前时间段")
			$('#intEndHour').val(start);
		}*/
}


function showStartForm(){
		var eed = document.getElementById('entity.dtEndTime').value;
		if(eed==""){
			return new WdatePicker({minDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd'});
		}else{
			return  new WdatePicker({minDate:'%y-%M-%d %H:%m:00',maxDate:'#F{$dp.$D(\'entity.dtEndTime\')||\'%y-%M-%d %H:%m:%s\'}',dateFmt:'yyyy-MM-dd'});
		}
}
function showEndForm(){
		var ssd = document.getElementById('entity.dtStartTime').value;
		if(ssd==""){
			return new WdatePicker({minDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd'});			
		}else{
			return new WdatePicker({minDate:'#F{$dp.$D(\'entity.dtStartTime\')}',dateFmt:'yyyy-MM-dd'});
		}
}



var businessDialog = null;
	function showBusinessDialog(){
		if(businessDialog == null){
		  businessDialog = new SymbolDialog({
	        renderTo : 'business',//点击触发对话框ID
	        renderTo2:'business_dialog_hidden_id',
	        hiddenTo:'businessIds',//页面与对话框参数传递
			id:'businessDialog',//对话框ID
			hideSelItem:{//解决IE6中select元素掩盖弹出框情况,此处为select元素ID
				select1:'bitTaskActiveFlag'
			},
			showLoading:true,
			typeSearchName:'类型',
			modelSearchName:'内容',
			title:'内容复选对话框',//对话框标题
			url:'/modules/netTactics/net-tactics!initBussnessDialogData.do?1=1'//对话框加载后台数据URL
	      });
		  businessDialog.init();//初始化页面DOM对象
		  businessDialog.loadData($('#businessIds').attr('value'));//加载后台数据并
		  businessDialog.display();//显示
		}else{
		  businessDialog.display();//显示(第N次以后只显示对话框即可,提高效率)
		}
		//隐藏select
		$('#bitTaskActiveFlag').css({'display':'none'});
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