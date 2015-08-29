<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/style.css" />
		<link rel="stylesheet" href="/tools/jquery/jquery.treeview.css" />
		<script src="/tools/jquery/jquery.treeview.js" type="text/javascript"></script>
		<style type="text/css">
body {
	background: #F2F6F7;
}

#condition_div {
	padding-top: 10px;
	padding-left: 16px;
	padding-bottom: 12px;
}

#condition_div select {
	height: 18px;
	width: 140px;
}

#condition_div input {
	height: 18px;
	width: 140px;
}

#top_div {
	width: 100%;
	height: 150px;
	overflow-x: hidden;
	overflow-y: scroll;
	border: 1px groove;
	overflow-x: hidden;
	overflow-y: scroll;
	overflow-y: scroll;
	background: #FFFFFF;
}

#left_list {
	text-align: center;
	width: 300px;
	height: 160px;
}

#right_list {
	text-align: center;
	width: 300px;
	height: 330px;
}

#btn_div {
	padding-top: 5px;
	padding-left: 75%;
}

#btn_div input {
	width: 60px;
}
</style>
	</head>
	<body>
		<div>
			<div id="condition_div">
				&nbsp; 业务类型:
				<select name="phoneType" id="phoneType" onchange="changeType()">
					<c:forEach items="${phoneInfoList}" var="phone">
						<option value="${phone.type}">
							${phone.name}
						</option>
					</c:forEach>
				</select>
				业务:
				<input type="text" name="phoneModel" id="phoneModel">
				<img src="/skin/Default/images/form/16/search.png" id="img_search"></img>
			</div>
			<div id="content_div">
				<table width="95%" align="center" border="0" cellpadding="0"
					cellspacing="0">
					<tbody>
						<tr>
							<td width="30%" align="center" height="150">
								<table>
									<tr>
										<td>
											<div id="top_div">
												<ul id="top_list" class="filetree">
													<c:forEach items="${phoneInfoList}" var="phone">
														<li>
															<img src="/tools/jquery/images/folder-closed.gif" />
															<span id="${phone.type}">${phone.name}</span>
															<input type="hidden" value="${phone.detailModel}" />
															<ul>
															</ul>
														</li>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td height="18px">
											<label id="msg_label"></label>
										</td>
									</tr>
									<tr>
										<td>
											<select id="left_list" size="10" multiple="multiple">
											</select>
										</td>
									</tr>
								</table>
							</td>
							<td width="5%" align="center">
								<img id="img_add" src="/skin/Default/images/icon/16/arrow_r.gif"></img>
								<br>
								<br>
								<img id="img_delete"
									src="/skin/Default/images/icon/16/arrow_l.gif"></img>
							</td>
							<td width="30%" align="center">
								<select id="right_list" multiple="multiple">
								</select>
							</td>
						</tr>
					</tbody>
				</table>
				<div id="btn_div">
					<input type="button" id="confirm_btn" value="确&nbsp&nbsp认" />
					<input type="button" id="cancel_btn" value="取&nbsp&nbsp消" />
				</div>
			</div>
		</div>
	</body>
</html>
<script>
	//去掉字符串前后空格
	String.prototype.trim = function()
	{
	    return   this.replace(/(^\s*)|(\s*$)/g,"");
	}
	//初始化对话框标题
	$(function() {
		document.title="业务选择对话框";
	});
	
	//初始化左边list数据
	$(document).ready(function(){
		changeType();
	})
	
	//初始化左边树
	$(function() {
			$("#top_list").treeview();
			$('#top_list li span').click(function(){//树单击事件
				var $optionsObj = $("#phoneType option");
				var id = this.id;
				var index = 0 ; 
				$optionsObj.each(function(i){
					if(this.value==id){
						index = i ;
					}
				});
				$("#phoneType").get(0).selectedIndex=index;//确定选择项 
				changeType();
			});
	});
	
	//手机品牌选择事件
	function changeType(){
		 $("#left_list option").each(function(){
	　　　　　　　$(this).remove();　
	　　　})
	 	 $('#phoneModel').attr("value","");
	　　　var typeId = $("#phoneType option:selected").val();
		 var values=$('#'+typeId).next().attr("value");
		 var vs = values.split(":");
		 var total = 0 ; 
		 for(var i = 0 ; i<vs.length;i++){
			$("#left_list").append("<option value='"+vs[i]+"'>"+vs[i]+"</option>");
			total = total + 1;
		 }
		 $("#msg_label").html("共有"+total+"条记录");
	}
	
	//查询事件
	$(function(){
		$("#img_search").click(function(){
			$("#left_list option").each(function(){
		　　　　　　　$(this).remove();　
		　　　})
			var searchModel = $('#phoneModel').attr("value");
			searchModel = searchModel.trim().toLowerCase();
		　　 var typeId = $("#phoneType option:selected").val();
			var values=$('#'+typeId).next().attr("value");
			var vs = values.split(":");
			var total = 0 ; 
			for(var i = 0 ; i<vs.length;i++){
			   var value = vs[i].toLowerCase();
			   if(value.indexOf(searchModel)!=-1){
			   		$("#left_list").append("<option value='"+vs[i]+"'>"+vs[i]+"</option>");
			   		total = total + 1 ; 
			   }
			}
			$("#msg_label").html("共有"+total+"条记录");
		});
	})
	
	//增加 删除
	$(function(){
	　　  $("#img_add").click(function(){
	　　　　　　　if($("#left_list option:selected").length>0)
	　　　　　　　{
	　　　　　　　　　　　$("#left_list option:selected").each(function(){//获取左边所选择的项
						  if($("#right_list option[value='"+$(this).val()+"']").length==0){//如果右边不存在所选的项,append上去(length判断获取到对象的大小,0代表没有此对象)
						  	$("#right_list").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option");
						  }
	　　　　　　　　　　　})
	　　　　　　　}
	　　　})
	     $("#img_delete").click(function(){
		　　　　　　　　　　　if($("#right_list option:selected").length>0)
		　　　　　　　　　　　{
		　　　　　　　　　　　　　　　$("#right_list option:selected").each(function(){
		　　　　　　　　　　　　　　　　　　　　　$(this).remove();　
		　　　　　　　　　　　　　　　})
		　　　　　　　　　　　}
		　})
	})
	
	//确定 取消按钮
	$(function() {
			$("#confirm_btn").click(function(){
				var $selObjes = $("#right_list option") ; 
				var value = '';
				if ($selObjes.length > 0) {
					$selObjes.each(function () {
						value = value + this.value + ',';
					});
				}
				if(value!=''){
					value = value.substring(0,value.lastIndexOf(','));
				}
				window.returnValue = value;//设定模式窗口返回值,调用页面获取
				window.close();
			})
			
			$("#cancel_btn").click(function(){
				window.close();
			})
	});  
	
</script>
