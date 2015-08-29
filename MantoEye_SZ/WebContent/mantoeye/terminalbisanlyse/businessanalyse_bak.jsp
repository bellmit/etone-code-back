<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>业务分析</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" type="text/css"
			href="/tools/gt-grid/gt_grid.css" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/maincontent.css" />
		<script type="text/javascript" src="/tools/gt-grid/gt_msg_cn.js"></script>
		<script type="text/javascript" src="/tools/gt-grid/gt_grid_all_source.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<!-- 列表工具栏 -->
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
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
													<td class="condition_name">
														请选择任务:
													</td>
													<td class="condition_value">
														<div class="select">
															<div>
																<select name="taskName" id="d_taskName" onclick="changeDialog(this);"
																	style="height: 21px" >
																	
																</select>
															</div>
														</div>
													</td>
													<td class="condition_name" style="width:100px;">
														请选择分析指标:
													</td>
													<td style="width:80px;">
														<input type="checkbox" onclick="hiddenColumn();" class="dataType" value="1" name="dataType" checked style="width: 20px; border: 0px; background-color: transparent;"/>用户数
													</td>
													<td style="width:80px;">
														<input type="checkbox" onclick="hiddenColumn();" class="dataType" value="2" name="dataType" checked style="width: 20px; border: 0px; background-color: transparent;"/>总流量
													</td>
													<td style="width:80px;">
														<input type="checkbox" onclick="hiddenColumn();" class="dataType" value="3" name="dataType" checked style="width: 20px; border: 0px; background-color: transparent;"/>用户占比
													</td>
													<td style="width:80px;">
														<input type="checkbox" onclick="hiddenColumn();" class="dataType" value="4" name="dataType" checked style="width: 20px; border: 0px; background-color: transparent;"/>流量占比
													</td>
													<td style="width:80px;">
														<input type="checkbox" onclick="hiddenColumn();" class="dataType" value="5" name="dataType" checked style="width: 20px; border: 0px; background-color: transparent;"/>人均流量
													</td>
													
												</tr>
											</table>
											<table style="margin-left:5px">
												<tr valign="middle">
													<td 
														style="width: 72px; cursor: default; padding: 0px 1px 0px 10px; color: #ffffff; font-size: 11px;">
															请选择终端:
														</td>
														<td height="22px;" style="text_align: left;padding-left:7px;">
															<input value="-------------------------------------请选择终端-------------------------------------"
															type="text" value="" id="business" readonly
																onclick="showDialog()"
																style="width: 350px; height: 16px; display: block; border: 1px solid #7F9DB9; font-size: 11px; color: #163877;" />
															<input id="businessIds" type="hidden" value=""
																name="businessIds" />
														</td>
														<td width="100px"></td>
														<td width="120px">
														<div id="mainbtn">
															<ul>
																<li>
																	<a href="javascript:query();" 　title="查询"><span>查询</span>
																	</a>
																</li>
																<li>
																	<a href="javascript:reset();" title="重置"><span>重置</span>
																	</a>
																</li>
															</ul>
														</div>
													</td>
												</tr>
											</table>
											
											<table style="margin-left:5px;margin-top:10px;">
												<tr>
													<td colspan="2" height="30px">
														<span style="color:#0000FF;">温馨提示: 终端业务分析,为了高效显示分析结果,分析显示列最好控制<span style="color:red;">200</span>列以内,(例如:<span style="color:red;">40个终端类型*5个分析指标</span>,或 <span style="color:red;">100个终端类型*2个分析指标</span>,依此类推).</span>
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
						
					</table>
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

$(document).ready(function(){
	$.ajax({
		type : "post",
		dataType:"xml",
		url : "terminalbisanlyse_queryAllTask.do",
		data : {
			
		},
		success : function(xml) {
			$("#d_taskName").append("<option value=''>----请选择任务----</option>");
			$(xml).find("data").each(function(i){
				var id=$(this).children("id").text(); 
				var name=$(this).children("name").text();
				$("#d_taskName").append("<option value='"+id+"'>"+name+"</option>");
			})
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});

})

function reset(){
	$('#d_taskName').get(0).selectedIndex=0;
	$('#business').val('-------------------------------------请选择终端-------------------------------------');
	$('.dataType').each(function(){
		this.checked=true;
	})
}

var business='';
var taskId2='';
var displayId='';
function query(){
	displayId='';
	business='';
	taskId2='';
	taskId2=$('#d_taskName').val();
	if(taskId2==''){
		alert('请选择要分析的任务!');
		return ;
	}
	 business=$('#business').val();
	if(business=='-------------------------------------请选择终端-------------------------------------'||business==''){
		alert('请选择终端!');
		return;
	}
	
	var $dataType=$('.dataType');
	$dataType.each(function(){
		if(this.checked==true){
			displayId=displayId+this.value+","
		}
	});
	displayId=displayId.substring(0,displayId.length-1);
	//alert(displayId+"-");
	$.ajax({
		type : "post",
		url : "terminalbisanlyse_saveArgs.do",
		data : {
			business:business,
			displayId:displayId
		},
		success : function(xml) {
			if(xml=="yes"){
				var obj=new Object();
				obj.business=business;
				obj.taskId=taskId2;
				obj.displayId=displayId;
				obj.name='业务终端分析呈现';
				obj.id='TERMINAL_BUSINESS_ANALYSE';
				obj.target = '/mantoeye/terminalbisanlyse/businessdisplay.jsp?1=1';
				parent.newTab(true,obj,'1');
			}else{
				alert('后台出错,请重试!');
			}
		},
		error : function() {
			alert('服务器出错,请联系管理员!');
		}
	});
}
</script>

<!-- 复选框JS -->
<script>
	var dialog = null;
	var taskId;
	function showDialog(){
		if(typeof(taskId)=='undefined')
			return;
		if(dialog == null){
		  dialog = new SymbolDialog({
	        renderTo : 'business',//点击触发对话框ID
	        hiddenTo:'businessIds',//页面与对话框参数传递
			id:'businessDialog',//对话框ID
			title:'终端选择对话框',//对话框标题
			showLoading:true,
			typeSearchName:'品牌',
			modelSearchName:'型号',
			url:'terminalDialog_packTerminalInfoByTaskId.do',
			params:{
				taskId:taskId
			}
	      });
		  dialog.init();//初始化页面DOM对象
		  dialog.loadData();//加载后台数据
		  dialog.display();//显示
		}else{
		  dialog.display();//显示(第N次以后只显示对话框即可,提高效率)
		}
	}
	
	function changeDialog(parm){
		//var v = $("#dialog_change_id option:selected").val();
		//dialog.url='terminalDialog_initTerminalDialogData.do';
		//dialog.params.p1=v;//改变参数
		//dialog.hasInit=true;//重新加载后台数据标识
		//dialog.loadData();//加载后台数据
		//dialog.display();
		//dialog.block();
		$('#business').attr('value','');
		$('#businessIds').attr('value','');//改变时设置相关值为空
		$('#businessDialog').remove();//移除dialog
		dialog = null;//
		taskId=$(parm).val();
		if(taskId=='')
			taskId=-1;
	}
	
</script>
 


	



