String.prototype.trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

var modelIds = "";//型号ID集合
var modelNames ="";//型号名称集合
	
	
/**
*终端复选框定义
*/
SymbolDialog = function (config) {
	//复选框ID
	this.id = config.id;
	//复选框名称
	this.title = config.title;
	//承载容器
	this.renderTo = config.renderTo;
	this.renderTo2 = config.renderTo2;
	//控制选择记录数
	this.count=config.count;
	//是否有添加所有按钮
	this.imgall=config.imgall;
	//是否具选择维度名称
	this.hasDisplayName=config.hasDisplayName;
	//是否有添加所选大项按钮
	this.imgtypeall=config.imgtypeall;
	//是否有添加有添加单个按钮
	this.imgone=config.imgone;
	//页面交互传值
	this.hiddenTo = config.hiddenTo;
	//服务器端加载数据的URL
	this.url = config.url;
	//查询条件终端ID
	this.tId = config.id + "brand_search";
	//查询类型名称
	this.typeSearchName = config.typeSearchName;
	//查询条件型号ID
	this.mId = config.id + "model_search";
	//查询型号名称
	this.modelSearchName = config.modelSearchName;
	// 查询图片ID
	this.imgSearchId = config.id + "img_search";
	//增加删除图片ID
	this.addAllId = config.id + "img_add_all";
	this.addTypeId=config.id + "img_add_type";
	this.delAllId = config.id + "img_delete_all";
	this.delAllTypeId = config.id + "img_delete_type";
	this.addId = config.id + "img_add";
	this.delId = config.id + "img_delete";
	this.treesId = config.id + "top_div";
	this.leftId = config.id + "left_div";
	this.rightId = config.id + "right_div";
	this.rightIdForSearch = config.id + "right_div_search";
	this.labelId = config.id + "msg_label";
	//url传参
	this.params = config.params;
	//是否再次加载数据标识
	this.hasInit = config.hasInit;
	//最多选择的项数
	this.maxSize = config.maxSize;
	//解决IE6中div与select冲突的问题(关闭或确定弹出框时显示)
	this.hideSelItem = config.hideSelItem;
	//是否显示操作进度条
	this.loadingId = config.id+"loading_msg";
	this.showLoading = config.showLoading;
	//变量 用于判断所选项是否发生变化
	this.isChangeItemData = false;
	this.itemDataArray = new Array();
};
SymbolDialog.prototype = {init:function () {
	this.dialog = $("<div id=\"" + this.id + "\" class=\"dialog_main_class\" style=\"display:block\"></div>");
	this.initHeadContent();
	this.initBodyContent();
	this.initButtonContent();
	if (typeof (this.renderTo2) != "undefined") {
		this.dialog.insertAfter("#" + this.renderTo2);
	}else{
		this.dialog.insertAfter("#" + this.renderTo);
	}
	this.initLoadingContent();
	this.confirm(this.id, this.renderTo, this.hiddenTo);
	this.close(this.id, this.renderTo, this.hiddenTo);
	this.imgClick();//增加删除事件
	//this.searchClick();//查询事件
	this.searchClickAll();//新增全业务范围查询
	//this.addAllDataForSearch();//复选框生成的时候就将全业务初始化出来
}, initHeadContent:function () {//复选框头信息
	var headContextDiv = "<div class=\"dialog_head_class\">" + "<div class=\"dialog_head_icon_class\">" + "</div><div class=\"dialog_head_title_class\">" + this.title + "</div></div>";
	$(headContextDiv).appendTo(this.dialog);
}, initBodyContent:function () {//复选框主题信息
	var queryContext = "<table class=\"dialog_table_class\"><tr>" + "<td style=\"color: black;\">" + this.typeSearchName + ":</td>" + "<td><select  id=\"" + this.tId + "\" ></select></td>" + "<td class=\"dialog_query_space\"></td>" + "<td style=\"color: black;\">" + this.modelSearchName + ":</td>" + "<td><input type=\"text\" id=\"" + this.mId + "\" /></td>" + "<td><img src=\"/skin/Default/images/form/16/search.gif\" alt=\"\u70b9\u51fb\u67e5\u8be2\" id=\"" + this.imgSearchId + "\"></img></td>" + "</tr></table>";
	var leftListContext = "<table>" + "<tr><td><div  class=\"dialog_top_div_class\"><ul id=\"" + this.treesId + "\" class=\"filetree\"></ul></div></td></tr>" + "<tr><td height=\"18px\"><label id=\"" + this.labelId + "\"></label></td></tr>" + "<tr><td><select id=\"" + this.leftId + "\" class=\"dialog_left_list_class\" multiple=\"multiple\"></select></td></tr>" + "</table>";
	var imgAllContext ='';
		if(typeof this.imgall=='undefined'||this.imgall==true){
			imgAllContext= "<img id=\"" + this.addAllId + "\" src=\"/skin/Default/images/icon/16/aall.png\" alt=\"添加所有\"></img> " + "<br /><br />" + "<img id=\"" + this.delAllId + "\" src=\"/skin/Default/images/icon/16/rall.png\" alt=\"删除所有\"></img><br/><br/><br/><br/>";
		}
		if(typeof this.imgtypeall=='undefined'||this.imgtypeall==true){
		 	imgAllContext=imgAllContext+"<img id=\"" + this.addAllTypeId + "\" src=\"/skin/Default/images/icon/16/atype.png\" alt=\"添加所选大类\"></img> " + "<br /><br />" + "<img id=\"" + this.delAllTypeId + "\" src=\"/skin/Default/images/icon/16/rtype.png\" alt=\"删除所选大类\"></img><br/><br/><br/>";
		}
	 		imgAllContext=imgAllContext+"<img id=\"" + this.addId + "\" src=\"/skin/Default/images/icon/16/aselect.png\" alt=\"添加所选子项\"></img> " + "<br /><br />" + "<img id=\"" + this.delId + "\" src=\"/skin/Default/images/icon/16/rselect.png\" alt=\"删除所选子项\"></img>";
	var rightListContext = "<select id=\"" + this.rightId + "\" class=\"dialog_right_list_class\" multiple=\"multiple\"></select>";
	var rightListSearchContext = "<input type='hidden' id=\"" + this.rightIdForSearch + "\" />";
	var $queryTD = $("<td colspan=\"3\"></td>");
	$(queryContext).appendTo($queryTD);
	var $leftListTD = $("<td width=\"10px\"></td>");
	$(leftListContext).appendTo($leftListTD);
	var $imgTD = $("<td width=\"50px\" style=\"text-align: center;\">");
	$(imgAllContext).appendTo($imgTD);
	var $rightListTD = $("<td width=\"50px\" style=\"text-align: center;\"></td>");
	$(rightListContext).appendTo($rightListTD);
	$(rightListSearchContext).appendTo($rightListTD);
	var $queryTR = $("<tr></tr");
	$queryTD.appendTo($queryTR);
	var $bodyTR = $("<tr></tr");
	$leftListTD.appendTo($bodyTR);
	$imgTD.appendTo($bodyTR);
	$rightListTD.appendTo($bodyTR);
	var $tbodyObj = $("<tbody></tbody>");
	var $tableObj = $("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"></table");
	var $divObj = $("<div></div");
	$queryTR.appendTo($tbodyObj);
	$bodyTR.appendTo($tbodyObj);
	$tbodyObj.appendTo($tableObj);
	$tableObj.appendTo($divObj);
	this.initButtonContent($divObj);
	$divObj.appendTo(this.dialog);
}, initButtonContent:function (divObj) {//复选框按钮
	var $buttonObj = $("<div class=\"dialog_button_class\">" + "<button class=\"dialog_button\" id=\"" + this.id + "_confirm_button\">\u786e\u5b9a</button>" + "<button class=\"dialog_button\" id=\"" + this.id + "_close_button\">\u5173\u95ed</button>" + "</div>");
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
/*}, searchClick:function (divObj) {//查询事件
	var o = this;
	$("#" + o.imgSearchId).click(function () {
		$("#" + o.leftId + " option").each(function () {
			$(this).remove();
		});
		var searchModel = $("#" + o.mId).attr("value");
		var brandId = $("#" + o.tId + " option:selected").val();//终端ID
		o.changeLeftListData(brandId, searchModel);
	});
	*/
}, searchClickAll:function (divObj) {//新增全业务范围查询
	var o = this;
	$("#" + o.imgSearchId).click(function () {
		$("#" + o.leftId + " option").each(function () {
			$(this).remove();
		});
		var searchModel = $("#" + o.mId).attr("value");

		//alert(searchModel+'===searchModel');
		o.addAllDataForSearch();
		o.changeItemData();
		//=====================================================================================
		o.getItemDataArrayAll(searchModel.trim());
	});
},getItemDataArrayAll:function (search_value) {//新增全业务范围值方法
    var o = this;
	var valueTempArr = $('#'+o.rightIdForSearch).val().split(',');

	$("#" + o.leftId + " option").each(function () {
		$(this).remove();//移除之前项
	});
 

	for(var i = 0 ; i < valueTempArr.length ; i ++){
		var tempArr = valueTempArr[i].split(':');
		var idsv = tempArr[0].trim()+":"+tempArr[1].trim();
		var mnsv = tempArr[2].trim();
		modelIds += idsv +',';
		modelNames += mnsv+',';
	}

	modelIds = modelIds.substring(0,modelIds.length-1);
	modelNames = modelNames.substring(0,modelNames.length-1);
	var mids = modelIds.split(",");//型号ID集合
	var mnas = modelNames.split(",");//型号名称集合
		
	var total = 0;

	for (var i = 0; i < mids.length; i++) {
		if (search_value == null || search_value == '' || search_value == 'null') {//非查询事件改变
			$("#" + o.leftId).append("<option value='" + mids[i] + "'>" + mnas[i] + "</option>");
			total = total + 1;
		} else {
			search_value = search_value.trim().toLowerCase();
			if (mnas[i].toLowerCase().indexOf(search_value) != -1) {
				$("#" + o.leftId).append("<option value='" + mids[i].trim() + "'>" + mnas[i].trim() + "</option>");
				total = total + 1;
			}
		}
	}
	
	$("#" + o.labelId).html("\u5171\u6709" + total + "\u6761\u8bb0\u5f55");
	
}, addAllDataForSearch:function () {//增加DOM对象
	var o = this;
	var $treeObj = $('#'+o.treesId).find("input[id$='_modelids']");
	var valueTemp = '';
	$treeObj.each(function (j){
		var modelIds = $(this).attr("value");
		var modelNames = $(this).next("input").attr("value");
		var mids = modelIds.split(",");//型号ID集合
		var mnas = modelNames.split(",");//型号名称集合
		for (var i = 0; i < mids.length; i++) {
			valueTemp += mids[i].trim()+':'+mnas[i].trim()+',';
		}
	});
	valueTemp = valueTemp.substring(0,valueTemp.length-1);
	$("#" + o.rightIdForSearch).attr('value',valueTemp);
}, imgClick:function () {//增加删除事件
	var o = this;
	//全选事件
	$("#" + this.addAllId).click(function () {
		o.addAllData();
		o.changeItemData();
	});
	$("#" + this.delAllId).click(function () {
		o.delAllData();
		o.changeItemData();
	});
	
	$("#"+this.addAllTypeId).click(function(){
			var mids=modelIds.split(",");
			var mnas=modelNames.split(",");
			for(var i=0;i<mids.length;i++){
						if ($("#" + o.rightId + " option[value='" + mids[i].trim()+ "']").length == 0) {//如果右边不存在所选的项,append上去(length判断获取到对象的大小,0代表没有此对象)
							$("#" + o.rightId).append("<option value='" + mids[i].trim() + "'>" + mnas[i].trim() + "</option");
							o.changeItemData();
						}
			}
	})
	
		$("#"+this.delAllTypeId).click(function(){
			var mids=modelIds.split(",");
			var mnas=modelNames.split(",");
			for(var i=0;i<mids.length;i++){
						if ($("#" + o.rightId + " option[value='" + mids[i].trim()+ "']").length > 0) {//如果右边不存在所选的项,append上去(length判断获取到对象的大小,0代表没有此对象)
							$("#" + o.rightId + " option[value='" + mids[i].trim()+ "']").remove();
							o.changeItemData();
						}
			}
	})
	
	$("#" + this.addId).click(function () {
		if ($("#" + o.leftId + " option:selected").length > 0) {
			if(o.countItem($("#" + o.rightId + " option"))){
				$("#" + o.leftId + " option:selected").each(function () {//获取左边所选择的项
					if ($("#" + o.rightId + " option[value='" + $(this).val().trim() + "']").length == 0) {//如果右边不存在所选的项,append上去(length判断获取到对象的大小,0代表没有此对象)
						$("#" + o.rightId).append("<option value='" + $(this).val().trim() + "'>" + $(this).text() + "</option");
						o.changeItemData();
					}
				});
			}
		}
	});
	$("#" + this.delId).click(function () {
		if ($("#" + o.rightId + " option:selected").length > 0) {
			$("#" + o.rightId + " option:selected").each(function () {
				$(this).remove();
				o.changeItemData();
			});
		}
	});
	//双击增加事件
	$("#" + o.leftId).dblclick(function () {
		if(o.countItem($("#" + o.rightId + " option"))){
			if ($("#" + o.rightId + " option[value='" + $("#" + o.leftId + " option:selected").get(0).value + "']").length == 0) {//如果右边不存在所选的项,append上去(length判断获取到对象的大小,0代表没有此对象)
				$("#" + o.rightId).append("<option value='" + $("#" + o.leftId + " option:selected").get(0).value + "'>" + $("#" + o.leftId + " option:selected").get(0).text + "</option");
				o.changeItemData();
			}
		}
	});
	//双击删除事件
	$("#" + o.rightId).dblclick(function () {
		$("#" + o.rightId + " option:selected").remove();
		o.changeItemData();
	});
}, changeItemData:function () {//控制数组变量
   this.isChangeItemData = true;
},countItem:function(args){
	if(typeof this.count!='undefined'){
		if(args.length>=this.count){
			alert('您最多只能选择'+this.count+'条记录!');
			return false;
		}
	}
	return true;
 },confirmItemDataArray:function (id,value,flag) {//点击确定数据改变
    var o = this;
	if(o.isChangeItemData){
		if(flag){
			o.itemDataArray = new Array();
		}
		o.itemDataArray.push(id+'&&&'+value);
	}
}, resetItemDataArray:function () {//数组数组信息
   var o  = this;
   if(o.isChangeItemData){
   	  $("#" + o.rightId + " option").remove();//如果选项已改变,删除所有
   	  for(var i in o.itemDataArray){//然后重新赋值
		var data = o.itemDataArray[i].split('&&&');
		$("#" + o.rightId).append("<option value='" + data[0].trim() + "'>" + data[1].trim() + "</option");
 	  }
   }
}, confirm:function (dialogId, renderTo, hiddenTo) {//确认事件
	var o = this;
	$("#" + this.id + "_confirm_button").bind("click", function (i) {
		//获取选中值
		var ids = "";
		var names = "";
		if ($("#" + o.rightId + " option").length > 0) {
			$("#" + o.rightId + " option").each(function (j) {
				ids = ids.trim();
				names= names.trim();
				
				if(typeof this.hasDisplayName!=undefined &&this.hasDisplayName!=false){
					ids = ids + $(this).val()+":"+$(this).text() + ",";
				}else{
					ids = ids + $(this).val() + ",";
				}
				names = names + $(this).text() + ",";
				o.confirmItemDataArray($(this).val().trim(),$(this).text().trim(),(j==0?true:false));
			});
		}
		if (ids != "") {
			ids = ids.substring(0, ids.lastIndexOf(","));
		}
		if (names != "") {
			names = names.substring(0, names.lastIndexOf(","));
		}
		$("#" + renderTo).attr("value", names);
		$("#" + hiddenTo).attr("value", ids);
		//显示select元素
		o.hideSel();
		$("#" + dialogId).css({display:"none"});//隐藏dialog
	});
}, close:function (dialogId, renderTo, hiddenTo) {//关闭按钮
	var o = this;
	$("#" + this.id + "_close_button").bind("click", function (i) {
		o.resetItemDataArray();
		o.hideSel();//显示页面select元素
		$("#" + dialogId).css({display:"none"});//隐藏dialog
	});
}, display:function () {//显示复选框
	$("#" + this.id).css({display:"block"});
}, block:function () {//隐藏复选框
	$("#" + this.id).css({display:"none"});
}, managerMaxSize:function () {//控制选择的项数
	//alert(this.maxSize);
	//alert($("#" + this.rightId + " option").length);
}, hideSel:function () {//显示select选项
	if (typeof (this.hideSelItem) != "undefined") {
		for (var s in this.hideSelItem) {
			var id = (this.hideSelItem)[s];
			$('#'+id).css({'display':'block'});
		}
	}
}, loadData:function (defaultValue) {//加载服务器数据
	var i = this.id;
	var o = this;
	//URL带上参数
	var url = this.url + "?";
	if (typeof (this.params) != "undefined") {
		for (var k in this.params) {
			url = url + k + "=" + ((this.params)[k]) + "&";
		}
	}
	$.ajax({type:"post", dataType:"xml", url:url, data:{}, success:function (xml) {
		if($(xml).find("data").length==0){
			o.displayLoading(false);
			alert('没有数据显示!');
			return ;
		}
		o.removeData();
		$(xml).find("data").each(function (i) {
			var brandId = $(this).children("brandId").text();
			var brandName = $(this).children("brandName").text();
			var modelId = $(this).children("modelId").text();
			var modelName = $(this).children("modelName").text();
			var src = "<li><img src=\"/tools/jquery/images/folder-closed.gif\" />";
			var span = "<span  id=\"" + brandId + "\">" + brandName + "</span>";
			var modelid_hide = "<input type=\"hidden\" id=\"" + o.id + brandId + "_modelids\" value=\"" + modelId + "\" />";
			var modelname_hide = "<input type=\"hidden\" id=\"" + o.id + brandId + "_modelnames\" value=\"" + modelName + "\" />";
			src = src + span + modelid_hide + modelname_hide;
			src = src + "<ul></ul></li>";
			var $src = $(src);
						//初始化树
			$src.appendTo("#" + o.treesId);
						//初始化查询条件select
			$("#" + o.tId).append("<option value='" + brandId + "'>" + brandName + "</option>");
		});
		$("#" + o.treesId).addClass("filetree");
		$("#" + o.treesId).treeview();
		$("#" + o.treesId + " li span").click(function () {//树单击事件
			var selIndex = 0;
			var v = this.id;
			$("#" + o.tId + " option").each(function (i) {
				if ($(this).val() == v) {
					selIndex = i;
				}
			});
			$("#" + o.tId).get(0).selectedIndex = selIndex;//设置选择项
			o.changeLeftListData(this.id, null);
		});
		o.setDedaultValue(defaultValue);//初始化数据
	}, error:function () {
		o.displayLoading(false);
		alert("\u670d\u52a1\u5668\u51fa\u9519,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458!");
	}});
}, setDedaultValue:function (defaultValue) {//初始化选择项
	var o = this;
	if (typeof (defaultValue) == "undefined") {
		o.displayLoading(false);
		return ;
	}
	$("#" + o.rightId + " option").remove();
	if (defaultValue == "" || defaultValue == null) {
		o.displayLoading(false);
		return;
	}
	var data = defaultValue.split(",");
	for (var i = 0; i < data.length; i++) {
		var ids = data[i].split(":");
		var brandId = ids[0];
		var modelId = ids[1];
		var modelIds = $("#" + o.id + brandId + "_modelids").attr("value");
		var modelNames = $("#" + o.id + brandId + "_modelnames").attr("value");
		var mids = modelIds.split(",");//型号ID集合
		var mnas = modelNames.split(",");//型号名称集合
		for (var j = 0; j < mids.length; j++) {
			if (mids[j].trim() == data[i].trim()) {
				$("#" + o.rightId).append("<option value='" + mids[j].trim() + "'>" + mnas[j].trim() + "</option");
				o.itemDataArray.push(mids[j].trim()+'&&&'+mnas[j].trim());
			}
		}
	}
	o.displayLoading(false);
}, removeData:function () {//删除DOM对象
	var o = this;
	if (typeof (o.hasInit) != "undefined") {
		if (o.hasInit) {
			$("#" + o.treesId).children().remove();//删除树
			$("#" + o.tId + " option").remove();
			$("#" + o.mId).attr("value", "");
			$("#" + o.leftId + " option").each(function () {
				$(this).remove();//移除之前项
			});
			$("#" + o.rightId + " option").each(function () {
				$(this).remove();
			});
			$("#" + o.labelId).attr("value", "");
		}
	}
}, changeLeftListData:function (id, search_value) {//改变左边复选框
	var o = this;
	 modelIds = $("#" + o.id + id + "_modelids").attr("value");
	modelNames = $("#" + o.id + id + "_modelnames").attr("value");
	var mids = modelIds.split(",");//型号ID集合
	var mnas = modelNames.split(",");//型号名称集合

	/*
	alert(modelIds+'==modelIds');
	alert(modelNames+'==modelNames');
	alert(mids+'==mids');
	alert(mnas+'====mnas');
	*/
	
	$("#" + o.leftId + " option").each(function () {
		$(this).remove();//移除之前项
	});
	var total = 0;
	for (var i = 0; i < mids.length; i++) {
		if (search_value == null) {//非查询事件改变
			$("#" + o.leftId).append("<option value='" + mids[i].trim() + "'>" + mnas[i].trim() + "</option>");
			total = total + 1;
		} else {
			search_value = search_value.trim().toLowerCase();
			if (mnas[i].toLowerCase().indexOf(search_value) != -1) {
				$("#" + o.leftId).append("<option value='" + mids[i].trim() + "'>" + mnas[i].trim() + "</option>");
				total = total + 1;
			}
		}
	}
	$("#" + o.labelId).html("\u5171\u6709" + total + "\u6761\u8bb0\u5f55");
}, addAllData:function () {//增加DOM对象
	var o = this;
	var $treeObj = $('#'+o.treesId).find("input[id$='_modelids']");
	if($treeObj.attr('value')!=''){
		$("#" + o.rightId + " option").each(function () {
			$(this).remove();
		});
	}
	$treeObj.each(function (j){
		var modelIds = $(this).attr("value");
		var modelNames = $(this).next("input").attr("value");
		var mids = modelIds.split(",");//型号ID集合
		var mnas = modelNames.split(",");//型号名称集合
		for (var i = 0; i < mids.length; i++) {
			$("#" + o.rightId).append("<option value='" + mids[i].trim() + "'>" + mnas[i] + "</option");
		}
	});
},delAllData:function () {//删除DOM对象
	var o = this;
	$("#" + o.rightId + " option").each(function () {
		$(this).remove();
	});
}};



