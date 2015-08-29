String.prototype.trim = function()
	{
	    return   this.replace(/(^\s*)|(\s*$)/g,"");
	}
/**
*终端复选框定义
*/
SingleDialog = function (config) {
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
	//是否显示操作进度条
	this.loadingId = config.id+"loading_msg";
	this.showLoading = config.showLoading;
	//是否再次加载数据标识
	this.hasInit = config.hasInit;
};
SingleDialog.prototype = {init:function () {
	
	this.dialog = $("<div id=\"" + this.id + "\" class=\"dialog_main_class\" style=\"display:block\"></div>");
	this.initHeadContent();
	this.initBodyContent();
	this.initButtonContent();
	this.dialog.insertAfter('#'+this.renderTo);
	this.confirm(this.id,this.renderTo,this.hiddenTo);
	this.close(this.id,this.renderTo,this.hiddenTo);
	this.imgClick();//增加删除事件
	this.searchClick();//查询事件
	this.searchTextClick();//查询事件
}, initHeadContent:function () {//复选框头信息
	var headContextDiv = "<div class=\"dialog_head_class\">" + "<div class=\"dialog_head_icon_class\">" + "</div><div class=\"dialog_head_title_class\">" + this.title + "</div></div>";
	$(headContextDiv).appendTo(this.dialog);
}, initBodyContent:function () {//复选框主题信息
	var queryContext = '<table class="dialog_table_class"><tr  style="display:none">'+
						'<td>'+this.typeSearchName+':'+
						'<select id=\"'+this.tId+'\" ></select></td></tr>'+
						'<tr><td>'+this.modelSearchName+':'+
						'<input type="text"  id="'+this.mId+'"/>'+
						'<img src="/skin/Default/images/form/16/search.gif" alt="点击查询" id="'+this.imgSearchId+'"></img></td>'+
						'</tr><tr><td>&nbsp;</td></tr>'+
						'<tr><td>'+this.typeSearchName+':<select id="'+this.treesId+'" class="dialog_left_list_class" ></select></td></tr>'+
						'<tr><td height="18px" style="display:none"><label id="'+this.labelId+'"></label></td></tr>'+
						'<tr><td>'+this.modelSearchName+':<select id="'+this.leftId+'" class="dialog_left_list_class" ></select></td></tr>'+
					  '</table>';
    var $divObj = $('<div></div');
    $(queryContext).appendTo(this.dialog);
    this.initButtonContent(this.dialog);
},initButtonContent:function (divObj) {//复选框按钮
	var $buttonObj = $('<div class="dialog_button_class">'+
						'<button class="dialog_button" id=\"'+this.id+'_confirm_button\">确定</button>'+
						'<button class="dialog_button" id=\"'+this.id+'_close_button\">关闭</button>'+
						'</div>');
	$buttonObj.appendTo(divObj);
}, initLoadingContent:function () {//进度条信息
	if (typeof (this.showLoading) != "undefined") {
		if(this.showLoading){
			var $loadingObj = $("<div class=\"dialog_msg_class\" id="+this.loadingId+"><img src=\"/skin/Default/images/icon/16/loading.gif\"></img>操作进行中,请稍后...</div>");
			$loadingObj.insertAfter(this.dialog);
		}
	}
}, displayLoading:function (flag) {//控制进度条
	if (typeof (this.showLoading) != "undefined") {
		if(this.showLoading){
			var $msgObj = $('#'+this.loadingId);
			if(flag){
				$msgObj.css({'display':'block'});
			}else{
				$msgObj.css({'display':'none'});
			}
		}
	}
},searchTextClick:function (divObj) {//查询事件
	/*var o =  this;
	$("#"+o.mId).click(function(){
		var searchModel = $('#'+o.mId).attr("value");
		if(searchModel=='---请输入查询条件---'){
			$('#'+o.mId).attr("value") = '';
		}
	});*/
},searchClick:function (divObj) {//查询事件
	var o =  this;
	$("#"+o.imgSearchId).click(function(){
			$("#"+o.leftId+" option").each(function(){
		　　　　　　　$(this).remove();　
		　　　})
			var searchModel = $('#'+o.mId).attr("value");
		　　 var brandId = $("#"+o.tId+" option:selected").val();//终端ID
			o.changeLeftListData(brandId,searchModel);
	});
},imgClick:function () {//增加删除事件
	var o = this;
},confirm:function (dialogId,renderTo,hiddenTo) {//确认事件
	var o = this;
	$('#'+this.id+'_confirm_button').bind("click", function (i) {
			$('#'+dialogId).css({
				display : "none"
			});
			//获取选中值
			var ids =  $("#"+o.leftId+" option:selected").val();
			var names = $("#"+o.leftId+" option:selected").text();
			//alert("ids:"+ids+"names:"+names);
			$('#'+renderTo).attr('value',names);
			$('#'+hiddenTo).attr('value',ids.split(":")[1]);
			//清空
			//$("#"+this.leftId+" option").remove();
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
},loadData:function () {//加载服务器数据
	var i = this.id;
	var o = this;
	//URL带上参数
	var url = this.url+'?';
	if(typeof(this.params)!='undefined'){
		for( var k in this.params){
			url = url + k +'='+((this.params)[k])+'&';
		}
	}
	$.ajax({
		type : "post",
		dataType:"xml",
		url : url,
		data : {
			
		},
		success : function(xml) {	
			o.displayLoading(false);
			o.removeData();
			$(xml).find("data").each(function(i){ 
						var brandId=$(this).children("brandId").text(); 
						var brandName=$(this).children("brandName").text(); 
						var modelId=$(this).children("modelId").text(); 
						var modelName=$(this).children("modelName").text(); 					
						$("#"+o.treesId).append("<option value='"+brandId+"'>"+brandName+"</option>");
						
						//初始化查询条件select
						$("#"+o.tId).append("<option value='"+modelId+"'>"+modelName+"</option>");
						
					});
					$('#'+o.treesId).addClass("filetree");
					$("#"+o.treesId).treeview();
					
					$('#'+o.treesId).change(function(){//树单击事件
						$("#"+o.tId).get(0).selectedIndex=$("#"+o.treesId).get(0).selectedIndex;//设置选择项
						o.changeLeftListData(null,null);
					});
			$("#"+o.tId).get(0).selectedIndex=0;//设置选择项
			o.changeLeftListData(null,null);		
		},
		error : function() {
			o.displayLoading(false);
			alert('初始化业务数据失败!');
		}
	});
	function changeType(){
		//alert("o.id--"+o.tId);
		o.changeLeftListData($("#"+o.tId+" option:selected").val(),null);
	}
},removeData:function () {//删除DOM对象
	var o = this;
	if(typeof(o.hasInit)!='undefined'){
		if(o.hasInit){
			//$('#'+o.treesId).children().remove();//删除树
			$("#"+o.treesId+" option").each(function(){
	　　　　　　　$(this).remove();　//移除之前项
	　　　	})
			$('#'+o.tId+' option').remove();
			$('#'+o.mId).attr('value','');
			$("#"+o.leftId+" option").each(function(){
	　　　　　　　$(this).remove();　//移除之前项
	　　　	});
			
			$('#'+o.labelId).attr('value','');
		}
	}
},changeLeftListData:function (type_value,search_value) {//改变左边复选框
		var o = this;
		var modelIds="";
		var modelNames="";
		var mids;
		var mnas;
		if(search_value==null){//非查询事件改变
				modelIds =  $("#"+o.tId+" option:selected").val();
				modelNames = $("#"+o.tId+" option:selected").text();
				mids = modelIds.split(',');//型号ID集合
				mnas = modelNames.split(',');//型号名称集合
		}else{
				$("#"+o.tId+" option").each(function(){
				  modelIds = modelIds +　$(this).val()+',';　
				  modelNames = modelNames +　$(this).text()+',';　
			　　})
				mids = modelIds.substring(0,modelIds.lastIndexOf(",")).split(',');//型号ID集合
				mnas = modelNames.substring(0,modelNames.lastIndexOf(",")).split(',');//型号名称集合	
			
		}
		//alert("id--name:"+modelIds+"--"+modelNames);
		$("#"+o.leftId+" option").each(function(){
	　　　　　　　$(this).remove();　//移除之前项
	　　　})
		var total = 0 ; 
		for(var i = 0 ; i<mids.length;i++){
			if(search_value==null){//非查询事件改变				
				$("#"+o.leftId).append("<option value='"+mids[i]+"'>"+mnas[i]+"</option>");
				total = total + 1;
			}else{							
				search_value = search_value.trim().toLowerCase();
				if(mnas[i].toLowerCase().indexOf(search_value)!=-1){
					$("#"+o.leftId).append("<option value='"+mids[i]+"'>"+mnas[i]+"</option>");
					total = total + 1 ; 
				}
			}			
		}
		$("#"+o.labelId).html("共有"+total+"条记录");
}};

