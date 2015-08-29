/****************/
/*****百分比柱图*****/
/****************/
function findSWF(movieName) {
  if (navigator.appName.indexOf("Microsoft")!= -1) {
    return window[movieName];
  } else {
    return document[movieName];
  }
}
function buildPercentChartData(text,labels,xlabels,datas){
	return buildPChart(text,labels,xlabels,datas,100,20);
}
/*
 * 参数：text-标题
 * xlabels:x轴数据数组
 * datas:y值的数组  组成的数组
 */
function buildPChart(text,labels,xlabels,datas,max,step){
	var title =  buildTitle(text);
	var colors=["#1B95D9","#FF0000","#339966","#8822BB","#8C0019"];
	var elements = [];
	for(var i=0;i<datas.length;i++){
		elements[i] = {
		      	"type":      "hbar",
		      	"alpha":     1.0,
		      	"colour":    colors[i%5],
		      	"text":      buildLabel(labels[i]),
		      	"font-size": 10,
		      	"tip":buildTip(labels[i]), 
		      	"values" :   datas[i]
		    }
	}
	var chart = {
		"title" : title,
		"tooltip":{
		    "shadow":false,
		    "stroke":1,
		    "colour":"#333333",
		    "background":"#FFFFFF",
		    "title":"{font-size: 14px; color: #905050;}",
		    "body":"{font-size: 12px; font-weight: bold; color: #333333;}"
		  },
		"elements" :elements,
		"x_axis":{
			    "stroke":      1,
			    "tick_length": 3,
			    "max":   max,
			    "min":   0,
			    "steps": step,
			    "offset": false,
			    "colour":      "#1B95D9",
			    "grid_colour": "#333333"
			 },
		"y_axis":{
				"offset": true,
				"stroke":1,
				"tick_height":3,
				"colour":"#909090",
				"grid_colour":"#333333",
				"labels":xlabels
				}
  }
  	return chart;
}
function buildXLables(xlables){
	return{
		  "align":"center",
	      "labels":xlables    
	      }
}
/*
 * 可以完善此方法优化x轴显示方式
 */
function getXlablesSteps(xlables){
	var step = 1;
	return step;
}
function buildTip(label){
	//如果有单位  则label与单位间用@@分开
	var ls = [];
	var unit = "";
	var l = label;
	if(label.indexOf("@@")!=-1){
		ls = label.split("@@");
		l = ls[0];
		unit = ls[1];
	}
	return l + "：#val#"+unit+" \n";
}
function buildLabel(label){
	if(label.indexOf("@@")!=-1){
		var us = label.split("@@");
		return us[0]+"("+us[1]+")";
	}else{
		return label;
	}
}
function buildTitle(text){
	var  title = {
	    "text" : text,
	    "style" : "{font-size: 14px; color:#333333; font-family: Verdana; text-align: center;padding-top:10px;}"
	  };
	return title;
}