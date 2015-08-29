/****************/
/******饼图******/
/****************/
function findSWF(movieName) {
  if (navigator.appName.indexOf("Microsoft")!= -1) {
    return window[movieName];
  } else {
    return document[movieName];
  }
}
/*
 * 参数：text-标题
 * label:是什么的分布
 * datas:值的数组(里面为{label:"xxxx",value:111}类型的对象)
 */
function buildPieChartData(text,label,datas){
	var title =  buildTitle(text);
	var colours =   buildColor();
	var tip = buildPieTip(label);
	var values = [];
	for(var i=0;i<datas.length;i++){
		values[i] = buildValue(datas[i]);
	}
	
	var chart = {
		"title" : title,
		"elements" :[{
		"type":"pie",
		"tip" : tip,
		"colours" : colours,
		"alpha" : 1.0,
		"start_angle" : 135,
		"radius":60,
		"no-labels":false,
		"ani--mate" : true,
		//"label-colour":0, 
		"values" :values ,
		"bg_colour" : "#FAFAFA"  	
  	}]
  }
  	return chart;
  //alert(JSON.stringify(chart));
}

function buildValue(data){
	var callfunction ="callback('"+data.label+"')" ;
	var element = {
		"value" : data.value,
		"label" : data.label,
		"on-click":callfunction,
		"animate":[{"type":"bounce","distance":5},{"type":"fade"}]
	}
	return element;
}
function buildPieTip(label){
	//如果有单位  则label与单位间用@@分开
	var ls = [];
	var unit = "";
	var l = label;
	if(label.indexOf("@@")!=-1){
		ls = label.split("@@");
		l = ls[0];
		unit = ls[1];
	}
	return "#label#\n"+l + "：#val#"+unit+" \n占比：#percent#";
}

function buildTitle(text){
	var  title = {
	    "text" : text,
	    "style" : "{font-size: 14px; color:#333333; font-family: Verdana; text-align: center;padding-top:10px;}"
	  };
	return title;
}
function buildColor(){
	var colors = ["0x336699", "0x88AACC", "0x999933", "0x666699",
	              "0xCC9933", "0x006666", "0x3399FF", "0x993300",
	              "0xAAAA77", "0x666666", "0x663366", "0x6699CC",
	              "0xFFCC66", "0x9999CC", "0xAAAAAA", "0x669999",
	              "0xBBBB55", "0xCC6600", "0x9999FF", "0x0066CC",
	              "0x99CCCC", "0x999999", "0xFFCC00", "0x009999",
	              "0x99CC33", "0xFF9900", "0x999966", "0x66CCCC",
	              "0x339966", "0xCCCC33" ];
	return colors;
}
