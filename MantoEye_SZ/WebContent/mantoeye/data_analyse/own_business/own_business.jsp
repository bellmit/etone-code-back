<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%>
	<%@ include file="/include/setcache.jsp"%>
	<head>
		<title>竞争对手业务</title>
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
		<script type="text/javascript" src="/mantoeye/dialog/SymbolDialog1.js"></script>
		<script type="text/javascript" src="/tools/jquery/jquery.treeview.js"></script>
		<!-- 自动补全JS --> 
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript">
	//验证表单信息
	$(document).ready( function() {
		//聚焦第一个输入框
			$("#business").focus();
			var obj = window.dialogArguments;
			$('#time_search').attr('value',obj.time_search);
			$('#dataType_search').attr('value',obj.dataType_search);
			$('#timeLevel_search').attr('value',obj.timeLevel_search);
			$('#businessName').attr('value',obj.businessName);
			$('#spaceLevel_search').attr('value',obj.spaceLevel_search);
			$('#vcName').attr('value',obj.vcName);
			//为beanForm注册validate函数
			$("#beanForm").validate( {
				rules : {
					"business" :"required"
				},
				submitHandler: function(form) {   
					//alert(form);
					var ml = (parseInt(window.dialogWidth)-250)/2;
					var mt = (parseInt(window.dialogHeight)-55)/2;
					$('#showmsg').css({"margin-top":0,"margin-left":ml,"display":"block"});
					$('#saveBtn').attr('disabled','disabled');	
					$('#cancelBtn').attr('disabled','disabled');	
					form.submit();  
				}   
			});
		});
		
		function competitorBusiness(){
			var business = $('#business').attr('value');
			if(business==null || business==''){
				alert("请选择竞争对手业务！");
				return false;
			}
			var busiName = $('#businessName').attr('value')+","+business;
			var bName = busiName.split(",");		
			var businessName = '';
			for(var i=0;i<bName.length;i++){
				if(businessName!=''){
					businessName = businessName +",'"+bName[i]+"'";
				}else{
					businessName = "'"+bName[i]+"'";
				}				
			}
			var vcName = $('#vcName').attr('value');
			var time_search = $('#time_search').attr('value');
			var dataType_search = $('#dataType_search').attr('value');
			var timeLevel_search = $('#timeLevel_search').attr('value');
			var spaceLevel_search = $('#spaceLevel_search').attr('value');
			var obj =new Object();
			obj.time_search = time_search ;
			obj.dataType_search = dataType_search ;
			obj.timeLevel_search = timeLevel_search;
			obj.businessName = businessName;
			obj.spaceLevel_search=spaceLevel_search;
			obj.vcName=vcName;
			var w="1000px";
			var h="600px";
			if(width==1024){
				w="815px";
				h="600px";
			}
			window.showModalDialog("/mantoeye/data_analyse/own_business/competitor_business.jsp",obj,"dialogWidth="+w+";dialogHeight="+h+";location=no;resizable=no;status=no;toolbar:no;scroll=yes;center=yes");
			window.close();
		}
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
		<form name="beanView" action="/dataOutput_saveOutPutBussinessMap.do"
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
										选择竞争对手业务
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
									
									<td>
										<input type="text" id="business" name="business"
											value="${business}" readonly onclick="showBusinessDialog()"
											style="width: 300px; height: 16px; display: none;" />
										<div style="height: 0px; border: 0px; overflow: hidden"
											id="business_dialog_hidden_id"></div>
										<input id="businessIds" type="hidden" name="businessIds"
											value="${businessIds}" />
									</td>
									<input type="hidden" id="businessName" name="businessName" />
									<input type="hidden" id="time_search" name="time_search" />
									<input type="hidden" id="dataType_search" name="dataType_search" />
									<input type="hidden" id="timeLevel_search" name="timeLevel_search" />
									<input type="hidden" id="spaceLevel_search" name="spaceLevel_search" />
									<input type="hidden" id="vcName" name="vcName" />
								</tr>
							</table>
						</div>
					</td>
					
				</tr>
			</table>
		
		</form>
	</body>
</html>
<script>

/**
脚本不出错
*/
$(document).ready(function(){
	showBusinessDialog()
	window.onerror = killErrors;
})
function killErrors() {
	return true;
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
			modelSearchName:'业务',
			title:'业务复选对话框',//对话框标题
			url:'terminalDialog_initBussnessDialogData.do'//对话框加载后台数据URL
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
	


</script>