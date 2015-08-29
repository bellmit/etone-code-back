<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript">
	//验证表单信息
	$(document).ready( function() {
		//聚焦第一个输入框
			$("#entity.strFirstTime").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate( {
				rules : {
					"entity.strFirstTime" :"required",
					"entity.strLastTime" :"required",
					"flush_view" :{
						"required":true,
						"number":true
						},
					"entity.overDay" :"required",
					"entity.preMonth" :"required"
				},
				submitHandler: function(form) {   
					var unit1 = $('#unit1').attr('value');
					var lflush = $('#flush_view').attr('value');
					var fflush = lflush;
					switch(unit1){
						case '4':fflush = lflush*1024*1024*1024;break;
						case '3':fflush = lflush*1024*1024;break;
						case '2':fflush = lflush*1024;break;
						case '1':fflush = lflush;break;
					}					
					document.getElementById('entity.lastFlush').value = fflush;	
					
					var unit2 = $('#unit2').attr('value');
					var lflush2 = $('#per_view').attr('value');
					var fflush2 = lflush2;
					switch(unit2){
						case '4':fflush2 = lflush2*1024*1024*1024;break;
						case '3':fflush2 = lflush2*1024*1024;break;
						case '2':fflush2 = lflush2*1024;break;
						case '1':fflush2 = lflush2;break;
					}				
					document.getElementById('entity.preMonth').value = fflush2;		
					var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');
									
					form.submit();  
				}   
			});
		});
var baseunit1 = 1;	
var baseunit2 = 1;	
function changeUnit(flag){
	if(flag=='1'){
		var unit1 = $('#unit1').attr('value');
		var lflush = $('#flush_view').attr('value');
		var step = unit1 - baseunit1;	
		var afterf = changeViewData(lflush,step+"");
		var lflush = $('#flush_view').attr('value',afterf);		
		baseunit1 = unit1;
	}else if(flag=='2'){
		var unit2 = $('#unit2').attr('value');
		var lflush = $('#per_view').attr('value');
		var step = unit2 - baseunit2;	
		var afterf = changeViewData(lflush,step+"");
		var lflush = $('#per_view').attr('value',afterf);		
		baseunit2 = unit2;
	}
}
function changeViewData(baseflush,step){
	var afterflush = baseflush;
	switch(step){
		case '3':afterflush = baseflush/(1024*1024*1024);break;
		case '2':afterflush = baseflush/(1024*1024);break;
		case '1':afterflush = baseflush/1024;break;
		case '-3':afterflush = baseflush*(1024*1024*1024);break;
		case '-2':afterflush = baseflush*(1024*1024);break;
		case '-1':afterflush = baseflush*1024;break;
	}
	return afterflush;
}		
</script>
<style>
.myth {
	background-color: #FFFFFF;
	height: 22px;
	padding-left: 5px;
	width: 24%;
	text-align: right;
}

.mytd {
	padding-left: 5px;
	background-color: #FFFFFF;
	width: 76%;
}
</style>
		<base target="_self" />
	</head>
	<body style="overflow-x: hidden"
		onload="if(window!=top)parnet.location.reload()" bgcolor="">
		<form name="beanView" action="/blackuser_update.do?permId=${permId}"
			id="beanForm" method="post">
			<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
				bgcolor="" width="100%" height="100%">
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" border="0"
							bgcolor="#ffffff" width="100%" class="inputmenubar">
							<tr>
								<td width="100%" height="24px">
									<div class="middletitle">
										编辑用户
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<div class="inputmain">
							<!----------- 表单信息 ------------------>
							<table width="100%" cellpadding="0" cellspacing="1">
								<tr>
									<th class="myth" >
										<span>号码：<font color="red">*</font></span>
									</th>
									<td class="mytd">
									<span>${entity.msisdn }</span>
										<input type="hidden" name="entity.msisdn" readonly
											style="width: 270px;" id="entity.msisdn"
											value="${entity.msisdn }" />
									</td>
								</tr>
								<tr>
									<th class="myth">
										初定时间：
										<font color="red">*</font>
									</th>
									<td class="mytd">
										<!-- <input type="text" class="Wdate" style="width: 271px;"
											onclick="new WdatePicker({dateFmt:'yyyy-MM-dd'});"
											name="entity.strFirstTime" id="entity.strFirstTime"
											value="${entity.strFirstTime}" readonly/> -->
										<span>${entity.strFirstTime}</span>	
										<input type="hidden" class="Wdate" style="width: 271px;"
											name="entity.strFirstTime" id="entity.strFirstTime"
											value="${entity.strFirstTime}" readonly />
										<input type="hidden" name="entity.id" id="entity.id"
											value="${entity.id }" />
										<input type="hidden" name="entity.imsi" id="entity.imsi"
											value="${entity.imsi }" />
									</td>
								</tr>
								<tr>
									<th class="myth">
										描述：
									</th>
									<td class="mytd">
										<input type="text" name="entity.describe" id="entity.describe"
											value="${entity.describe }" style="width: 270px;" />
									</td>
								</tr>
								<tr>
									<th class="myth">
										最后确定时间：
										<font color="red">*</font>
									</th>
									<td class="mytd">
										<input type="text" class="Wdate" style="width: 271px;"
											onclick="new WdatePicker({minDate:'#F{$dp.$D(\'entity.strFirstTime\')}',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});"
											name="entity.strLastTime" id="entity.strLastTime"
											value="${entity.strLastTime}" />
									</td>
								</tr>
								<tr>
									<th class="myth">
										核定时流量：
										<font color="red">*</font>
									</th>
									<td class="mytd">
										<select style="width: 46px;" id="unit1" name="unit1" onchange="changeUnit('1')">
										<option value="4">GB</option>
										<option value="3">MB</option>
										<option value="2">KB</option>
										<option value="1" selected>B</option>
										</select>
										<input type="text" name="flush_view"
											id="flush_view" value="${entity.lastFlush}"
											style="width: 220px;" onchange=""/>											
										<input type="hidden" name="entity.lastFlush"
											id="entity.lastFlush" value="${entity.lastFlush}"/>
									</td>
								</tr>
								
								<tr>
									<th class="myth">
										上月总流量：
									</th>
									<td class="mytd">
									<select style="width: 46px;" id="unit2" name="unit2" onchange="changeUnit('2')">
										<option value="4">GB</option>
										<option value="3">MB</option>
										<option value="2">KB</option>
										<option value="1" selected>B</option>
										</select>
										<input type="text" name="per_view"
											id="per_view" value="${entity.preMonth}"
											style="width: 220px;" onchange=""/>
											
										<input type="hidden" name="entity.preMonth"
											id="entity.preMonth" value="${entity.preMonth}"/>	
									</td>
								</tr>
								<tr>
									<th class="myth">
										超标天数：
										<font color="red">*</font>
									</th>
									<td class="mytd">
										<input type="text" name="entity.overDay" id="entity.overDay"
											value="${entity.overDay}" style="width: 270px;" />
									</td>

								</tr>
							</table>
							<div class="dialog_msg_class" style="margin-top:120px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="20px">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="padding-left: 180px;">
						<jsp:include page="/include/submit.jsp"></jsp:include>
						<div style="display: none;">
							<s:submit id="btn_commit"></s:submit>
							<button id="btn_cancel" onclick="window.close();"></button>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

