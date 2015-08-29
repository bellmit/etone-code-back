<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>终端任务定制</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script src="/js/nav.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
		<script type="text/javascript" src="/js/common_grid.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
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
					<form name="beanView" action="/terminaltask_saveTask.do"
						id="beanForm" method="post" >
						<table cellspacing="0" cellpadding="0" border="0"
							bgcolor="#ffffff" style="width: 100%;">
							<tr valign="top">
								<td>
									<table cellspacing="0" cellpadding="0" border="0" width="100%">
										<tr>
											<td>
												<table cellspacing="0" cellpadding="0" border="0"
													width="100%" class="menubar">
													<tr valign="top">
														<td width="4px" height="24px">
															<div class="lefttitle"></div>
														</td>
														<td width="100%" height="24px">
															<div class="middletitle">
																终端任务定制
															</div>
															<input type="hidden" value="" id="chartxmldata" />
														</td>
														<td width="4px">
															<div class="righttitle"></div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr valign="top">
											<td width="100%">
												<table style="margin-left: -1px">
													<tr valign="middle">
														<td class="condition_name" style="width: 350px;"
															align="right">
															任务名称:
														</td>
														<td style="text_align: left;">
															<input type="text" name="taskName" id="taskName"
																style="height: 16px; width: 350px;" />
														</td>

													</tr>
													<tr valign="middle">
														<td class="condition_name" style="width: 350px;"
															align="right">
															请选择业务:
														</td>
														<td height="22px;" style="text_align: left;">
															<input type="text" value="" id="business" readonly
																onclick="showDialog()"
																style="width: 350px; height: 16px; display: block; border: 1px solid #7F9DB9; font-size: 11px; color: #163877;" />
															<input id="businessIds" type="hidden" value=""
																name="businessIds" />
														</td>
													</tr>
													<tr>
														<td class="condition_name" style="width: 350px;"
															align="right">
															请选择终端:
														</td>
														<td height="22px;" style="text_align: left;">
															<input type="text" value="" id="terminal" readonly
																onclick="showDialog2()"
																style="width: 350px; height: 16px; display: block; border: 1px solid #7F9DB9; font-size: 11px; color: #163877;" />
															<input id="terminalIds" type="hidden" value=""
																name="terminalIds" />
														</td>
													</tr>

													<tr>
														<td class="condition_name" style="width: 350px;"
															align="right">
															开始时间:
														</td>
														<td style="text_align: left;">
															<input type="text" class="Wdate" style="width: 350px;"
																readonly
																onclick="new WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd HH:mm:00'});"
																name="beginTime" id="beginTime" value="${beginTime}" />

														</td>
													</tr>
													<tr>
														<td class="condition_name" style="width: 350px;"
															align="right">
															结束时间:
														</td>
														<td style="text_align: left;">
															<input type="text" class="Wdate" style="width: 350px;"
																readonly
																onclick="new WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:00'});"
																name="endTime" id="endTime" value="${endTime}" />

														</td>

													</tr>
													<tr>
														<td class="condition_name" style="width: 350px;"
															align="right">
															任务描述:
														</td>
														<td style="text_align: left;">
															<textarea rows="6" cols="30" name="taskDescribe"
																id="taskDescribe"
																style="border: 1px solid #999999; height: 120px; width: 350px;"></textarea>
														</td>
													</tr>

													<tr>
														<td class="condition_name" style="width: 350px;"
															align="right">

														</td>
														<td align="center" style="padding-left: 130px;">
														<div class="dialog_msg_class" style="margin-top:120px;margin-left:70px;" id="showmsg" name="showmsg" style="display:none">
										<img src="/skin/Default/images/icon/16/loading.gif">
										</img>操作进行中,请稍后...</div>
															<div id="mainbtn">
																<ul>
																	<li>
																		<a href="javascript:taskSubmit();" 　title="定制" id="saveBtn" name="saveBtn" ><span>定制</span>
																		</a>
																	</li>
																	<li>
																		<a href="javascript:reset();" title="重置" id="cancelBtn" name="cancelBtn"><span>重置</span>
																		</a>
																	</li>
																</ul>
															</div>
														</td>
													</tr>
												</table>
												</from>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
				</form>
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

//验证表单信息
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#taskName").focus();
			//为beanForm注册validate函数
			$("#beanForm").validate({
				rules: { 
					"taskName": { 
        				required: true, 
        				remote: "terminaltask_checkUnique.do?oldTaskName="+encodeURIComponent('${oldTaskName}')
    			},
          		"beginTime": "required",
    			"endTime":"required",
    			"business":"required",
    			"terminal":"required"
				},
				messages: {
					"taskName": {
						remote: "任务名已存在"
					}
				}
			});
		});

function taskSubmit(){
	var taskName=$('#taskName').val();
	if(taskName.trim()==''){
		alert('任务名称不能为空!');
		return ;
	}
	var benginTime=$('#beginTime').val();
	if(benginTime.trim()==''){
		alert('开始时间不能为空!');
		return;
	}
	var endTime=$('#endTime').val();
	if(endTime.trim()==''){
		alert('结束时间不能为空!');
		return;
	}
	var bTime=new Date(benginTime.replace("-","/")).valueOf();
	var eTime=new Date(endTime.replace("-","/")).valueOf();
	if((eTime-7*24*60*60*1000)-bTime>0){
		alert('抱歉!任务定制时间跨度必须在一周时间内,请重新调整时间!');
		$('#endTime').focus();
		return ;
	}
	var taskDescribe=$('#taskDescribe').val();
	var business=$('#businessIds').val();
	if(business.trim()==''){
		alert('业务不能为空!');
		return;
	}
	var terminal=$('#terminalIds').val();
	if(terminal.trim()==''){
		alert('终端不能为空!');
		return;
	}
	var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');
	$.ajax({
						type : "post",
						url : "/terminaltask_saveTask.do",
						data : {
							taskName:taskName,
							benginTime:benginTime,
							endTime:endTime,
							taskDescribe:taskDescribe,
							business:business,
							terminal:terminal
							
						},
						success : function(msg) {
							alert(msg);//打印删除是否成功信息
							if(msg=='添加终端任务成功!'){
								var obj = new Object();
								obj.name='终端任务定制';
								obj.id='TERMINAL_TASK_MANA';
								obj.target = '/mantoeye/terminalmanager/terminal_task.jsp';
								parent.newTab(true,obj);
								parent.tabpanel.kill('taskinput');
							}
						},
						error : function() {
							alert('服务器出错,请联系管理员!');
						}
					});
	
}

function reset(){
	$('#taskName').attr('value','');
	$('#beginTime').attr('value','');
	$('#endTime').attr('value','');
	$('#business').attr('value','');
	$('#businessIds').attr('value','');
	$('#terminal').attr('value','');
	$('#terminalIds').attr('value','');
	$('#taskDescribe').attr('value','');
}

</script>



<!-- 复选框JS -->
<script>
	var dialog = null;
	function showDialog(){
		if(dialog == null){
		  dialog = new SymbolDialog({
	        renderTo : 'business',//点击触发对话框ID
	        hiddenTo:'businessIds',//页面与对话框参数传递
			id:'businessDialog',//对话框ID
			typeSearchName:'类型',
			modelSearchName:'业务',
			title:'业务复选对话框',//对话框标题
			url:'terminalDialog_initBussnessDialogData.do'//对话框加载后台数据URL
	      });
		  dialog.init();//初始化页面DOM对象
		  dialog.loadData();//加载后台数据
		  dialog.display();//显示
		}else{
		  dialog.display();//显示(第N次以后只显示对话框即可,提高效率)
		}
	}
	
	var dialog2 = null;
	function showDialog2(){
		if(dialog2 == null){
		  dialog2 = new SymbolDialog({
	        renderTo : 'terminal',
	        hiddenTo:'terminalIds',
			id:'terminalDialog',
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
