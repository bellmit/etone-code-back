/****************/
/***曲线图****/
/** ************* */
function findSWF(movieName) {
  if (navigator.appName.indexOf("Microsoft")!= -1) {
    return window[movieName];
  } else {
    return document[movieName];
  }
}
/*
 * 参数：text-标题 xlabels:x轴数据数组 datas:y值的数组 组成的数组
 */
function buildSingleChartDataPre(text,labels,xlabels,datas){
	var title =  buildTitle(text);	
	var righty = getYAxisUtilData(datas[0]);
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
		"elements" :[{	  		
			  	"type":      "line",
			    "colour":    "#FF0000",
			    "text":      buildLabel(labels[0]),
			  	"font-size": 10,
			    "width":     1,
			  	"dot-style": buildDotStyle(labels[0]),
			  	"values" :   datas[0]
			  	// ,"on-show": {"type": "shrink-in","delay":1, "cascade":1}
			 }],
		"x_axis":{
				"stroke":1,
				"tick_height":3,
				"colour":"#909090",
				"grid_colour":"#333333",
				"labels": buildXLables(xlabels)
				},
		"y_axis":{  
				"stroke":      1,
				"max":   righty.max,
			    "min":   righty.min,
			    "steps": righty.step,
				// "grid-visible": true,
			    "colour":      "#1B95D9",
			    "grid_colour": "#333333"
			}	 
  }
  	return chart;
}
function buildDotStyle(label){
	return {
  		"type":"anchor", 
  		"sides":4,
  		"alpha":1,
  		"hollow":true,
  		"background-colour":"#a44a80",
  		"background-alpha": 0.4,
  		"width":1,
  		"tip":buildTip(label) 
  		};
}
function buildXLables(xlables){
	return{
		  "align":"center",
	      "labels":xlables
	      }
}
function buildTip(label){
	// 如果有单位 则label与单位间用@@分开
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
/*
 * 处理数据，返回最大值，最小值，步长
 */
function getYAxisUtilData(datas){
	var obj = new Object();
	var max =0;
	var min =0;
	var step =1;
	if(datas.length>0){	
		 if(typeof (datas[0].value)!="undefined"){
				max = datas[0].value;
				min = datas[0].value;
				for(var i=1;i<datas.length;i++){
					if(datas[i].value>max){
						max = datas[i].value;
					}
					if(datas[i].value<min){
						min = datas[i].value;
					}
				}
			}else{
		max = datas[0];
		min = datas[0];
		for(var i=1;i<datas.length;i++){
			if(datas[i]>max){
				max = datas[i];
			}
			if(datas[i]<min){
				min = datas[i];
			}
		}
			}
	}
	obj.max = max;
	// 图形y轴从最小值开始
	// obj.min = min;
	// 图形y轴从0开始
	obj.min = 0;
	// 默认y轴刻度为10左右
	var fobj = getSteps(obj,10);
	return fobj;
}
/*
 * 处理数据，根据最大值，最小值和分段数，步长
 */
function getSteps(obj,size){
	var max = obj.max;
	var min = obj.min;
	var k = max-min==0?max:max-min;
	
	// 字符串(如:2346.9)
	var ttstep = k/size+"";
	var zs = ttstep.split(".")[0];
	var ststep =zs.substr(0,1)+ zs.substr(1).replace(/\d/g,'0');
	var step = parseInt(ststep);
	
	var realMin = parseInt(min/step);
	var realMax = parseInt(max/step)==(min/step)?parseInt(max/step):(parseInt(max/step)+1)
	realMin = realMin*step;
	realMax = realMax*step;
	var realSize = (realMax-realMin)/step;
	// alert("realMin:"+realMin);
	// alert("realMax:"+realMax);
	obj.max = realMax;
	obj.min = realMin;
	obj.step = step;
	obj.size = realSize;
	return obj;
}