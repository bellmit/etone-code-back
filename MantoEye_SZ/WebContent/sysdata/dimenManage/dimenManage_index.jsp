<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=utf-8"%>
	<%@ include file="/include/session.jsp"%> 
	<%@ include file="/include/setcache.jsp"%> 
	<head>
		<title>维度管理</title>
		<link rel="stylesheet" type="text/css"
			href="/skin/Default/css/maincontent.css" />

		<style type="text/css">
#tabMenu {
	margin: 0;
	padding: 0 0 0 15px;
	list-style: none;
}

#tabMenu li {
	float: left;
	height: 32px;
	width: 139px;
	cursor: pointer;
	cursor: hand;
}

li.selected {
	background-position: 0 0;
	color: #CCC;
}
</style>

		<script type="text/javascript" src="/js/jquery.js">
</script>
		<script type="text/javascript" src="/js/jquery.ui.js">
</script>

		<script type="text/javascript">
$(function() {
	$("#tabMenu").children().bind("click", function() {
		var showId = $("#tabMenu").children().index(this);
		var showDiv = $("#data_container").children().eq(showId);
		$("#tabMenu").children().addClass("selected");
		$(this).removeClass("selected");
		$.ajax( {
			type : "POST",
			url : "/sysdata/dimenManage/dimen-manage!dimenAjax.do",
			data : "typeId=" + showId,
			beforeSend : function() {
				$("#data_container").children().slideUp("1500");
			},
			success : function(msg) {
				showDiv.html(msg);
				showDiv.slideDown("1500");
			}
		});
	});
	$("#tabMenu").children().eq(0).trigger("click");
});
</script>

	</head>
	<body style="overflow: hidden">
		<table id="maincontent" cellspacing="0" cellpadding="0" border="0"
			bgcolor="#D3E0F2" width="100%" height="100%">
			<tr>
				<td height="5px"></td>
			</tr>

			<tr>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"
						style="width: 100%;">

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
												<ul id="tabMenu">
													<li>
														产品维度管理
													</li>
													<li class="selected">
														产品维组度管理
													</li>
												</ul>
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
								<div class="gt-panel"
									style="width: 100%; margin: 0px; margin-bottom: 2px;">
									<div id="data_container"
										style="width: 100%; height: 570px; overflow-x: auto; overflow-y: auto">
										<div></div>
										<div style="display: none"></div>
									</div>
								</div>
							</td>
						</tr>

					</table>
				</td>
			</tr>

		</table>
	</body>
</html>
