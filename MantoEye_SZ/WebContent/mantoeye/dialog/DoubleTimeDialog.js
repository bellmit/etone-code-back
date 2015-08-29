String.prototype.trim = function()
	{
	    return   this.replace(/(^\s*)|(\s*$)/g,"");
	}
/**
*时间跨度选择框
*/
DoubleTimeDialog = function (config) {
	//复选框ID
	this.id = config.id;
	//复选框名称
	this.title = config.title;
	//承载容器
	this.renderTo = config.renderTo;
	//页面交互传值
	this.hiddenTo = config.hiddenTo;
	//服务器端加载数据的URL
	this.url = config.url;
	//查询条件终端ID
	this.tId = config.id+"brand_search";
	//查询类型名称
	this.typeSearchName = config.typeSearchName;
	//查询条件型号ID
	this.mId = config.id+"model_search";
	//查询型号名称
	this.modelSearchName = config.modelSearchName;
	// 查询图片ID
	this.imgSearchId= config.id+"img_search";
	//增加删除图片ID
	this.addId = config.id+"img_add";
	this.delId = config.id+"img_delete";
	this.treesId = config.id+"top_div";
	this.leftId = config.id+"left_div";
	this.rightId = config.id+"right_div";
	this.labelId = config.id+"msg_label";
	//url传参
	this.params = config.params;
	//是否再次加载数据标识
	this.hasInit = config.hasInit;
};
DoubleTimeDialog.prototype = {init:function () {
	this.dialog = $("<div id=\"" + this.id + "\" class=\"dialog_main_class\" style=\"display:block\"></div>");
	this.initHeadContent();
	this.initBodyContent();
	this.initButtonContent();
	this.dialog.insertAfter('#'+this.renderTo);
	this.confirm(this.id,this.renderTo,this.hiddenTo);
	this.close(this.id,this.renderTo,this.hiddenTo);
	
	this.timeTypeChange();
	this.startTimeClick();//开始时间选择
	this.endTimeClick();//结束时间选择
}, initHeadContent:function () {//复选框头信息
	var headContextDiv = "<div class=\"dialog_head_class\">" + "<div class=\"dialog_head_icon_class\">" + "</div><div class=\"dialog_head_title_class\">" + this.title + "</div></div>";
	$(headContextDiv).appendTo(this.dialog);
}, initBodyContent:function () {//复选框主题信息
	var queryContext = '<table class="dialog_table_class">'+
						'<tr><td>时间粒度:'+
						'<select id=\"timeType\" >'+
						'<option value=\"1\">时</option>'+
						'<option value=\"2\">天</option>'+
						'<option value=\"3\">周</option>'+
						'<option value=\"4\">月</option>'+
						'</select>'+
						'</td>'+
					  '</tr><tr><td>&nbsp;</td></tr></table>';
	var leftListContext = '<table>'+
						'<tr><td>开始时间:<input type=\"text\" class=\"Wdate\"	style=\"display: block; height: 17px;\" '+
						' id=\"start_time\" name=\"start_time\" /></td></tr>'+
						'<tr><td height="18px" style="display:none"><label id="'+this.labelId+'"></label></td></tr>'+
						'<tr><td>结束时间:<input type=\"text\" class=\"Wdate\"	style=\"display: block; height: 17px;\" '+
						' id=\"end_time\" name=\"end_time\" /></td></tr>'+
					  '</table>';
	
	var $queryTD = $('<td colspan="1"></td>');
    $(queryContext).appendTo($queryTD);
    
    var $leftListTD = $('<td width="10px"></td>');
    $(leftListContext).appendTo($leftListTD);
    
    var $queryTR = $('<tr></tr');
    $queryTD.appendTo($queryTR);
    
    var $bodyTR = $('<tr></tr');
    $leftListTD.appendTo($bodyTR);
    
    var $tbodyObj = $('<tbody></tbody>');
    var $tableObj = $('<table border="0" cellpadding="0" cellspacing="0"></table');
    var $divObj = $('<div></div');
	
    $queryTR.appendTo($tbodyObj);
    $bodyTR.appendTo($tbodyObj);
    
    $tbodyObj.appendTo($tableObj);
    $tableObj.appendTo($divObj);
    this.initButtonContent($divObj);
    $divObj.appendTo(this.dialog);
},initButtonContent:function (divObj) {//复选框按钮
	var $buttonObj = $('<div class="dialog_button_class">'+
						'<button class="dialog_button" id=\"'+this.id+'_confirm_button\">确定</button>'+
						'<button class="dialog_button" id=\"'+this.id+'_close_button\">关闭</button>'+
						'</div>');
	$buttonObj.appendTo(divObj);
},startTimeClick:function (divObj) {//查询事件
	$("#start_time").click(function(){
		var ts = $("#timeType option:selected").val();
		switch(ts)
		   {
		   case '1':
		   	 new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd HH:00'});
		     break;
		   case '2':
		   	 new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
		     break;
		   case '3':
		     new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM-dd'});
		     break;
		   case '4':
		   	  new WdatePicker({maxDate:'#F{$dp.$D(\'endTime_search\')}',dateFmt:'yyyy-MM'});
		     break;
		 }
	});
},endTimeClick:function (divObj) {//查询事件
	$("#end_time").click(function(){
	var ts = $("#timeLevel_search option:selected").val();
	switch(ts)
	   {
	   case '1':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd HH:00:00'});
	     break;
	   case '2':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '3':
	     new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM-dd'});
	     break;
	   case '4':
	   	 new WdatePicker({minDate:'#F{$dp.$D(\'startTime_search\')}',dateFmt:'yyyy-MM'});
	     break;
	 }
	});
},timeTypeChange:function (divObj) {//时间粒度改变
	$("#timeType").change(function(){
		$("#start_time").attr("value","");
		$("#end_time").attr("value","");
	});
},confirm:function (dialogId,renderTo,hiddenTo) {//确认事件
	var o = this;
	$('#'+this.id+'_confirm_button').bind("click", function (i) {
			$('#'+dialogId).css({
				display : "none"
			});
			//获取选中值
			var timeType =  $("#timeType").val();
			var start = $("#start_time").text();
			var end = $("#end_time").text();
			$('#'+renderTo).attr('value',start+"~"+end);
			$('#'+hiddenTo).attr('value',timeType+"~"+start+"~"+end);
	});
}, close:function (dialogId,renderTo,hiddenTo) {//关闭按钮
	var o = this;
	$('#'+this.id+'_close_button').bind("click", function (i) {
			$('#'+dialogId).css({
				display : "none"
			});
			$("#"+o.rightId+" option").remove();
	});
},display:function () {//显示复选框
	$('#'+this.id).css({
		display : "block"
	});
},block:function () {//隐藏复选框
	$('#'+this.id).css({
		display : "none"
	});
}};

