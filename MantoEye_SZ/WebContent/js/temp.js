
$.ajax({
	type:"post", 
	url:"spMmsTitleArea_listMmsTitle.do", 
	data:{
		dataType_search:dataType_search, 
		timeLevel_search:$("#timeLevel_search option:selected").val(), 
		time_search:time_search = $("#time_search").attr("value"), 
		spaceLevel_search:$("#spaceLevel_search option:selected").val()
	}, 
	success:function (message) {
		if (message != null && message != "") {
			var ts = message.split("&&&&&");
			if (ts.length > 0) {
				for (var i = 0; i < ts.length; i++) {
					$("#mms_title_select").append("<option value='" + ts[i] + "'>" + ts[i] + "</option>");
				}
			}
		}
	}, error:function () {
	alert("\u670d\u52a1\u5668\u51fa\u9519,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458!");
	}
});

