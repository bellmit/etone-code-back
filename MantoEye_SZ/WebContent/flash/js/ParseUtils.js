/*
 * String类型转化为Float类型
 */
function StringToFloat(val){
	val = val+"";
	return parseFloat(val.replace(/,/g,''));
}
/*
 * 格式化字符类型的x轴数据
 * 去掉（） () [] 内的字符
 */
function formatXDateStrLables(lables){
	var formatLables=[];
	for(var i=0;i<lables.length;i++){
		var st = lables[i].replace(/（\S*）/,"");
		st = st.replace(/\[\S*\]/,"");
		st = st.replace(/\(\S*\)/,"");
		formatLables[i] = st;				
	}
	return formatLables;
}
/*
 * 格式化时间类型的x轴数据
 * lables 原始数据
 * maxsize x轴最多显示标签数
 * type时间类型 （hour,day,month,week）
 */
function formatXDateLables(lables,maxsize,type){
	var formatLables=[];
	var sp=1;//每少个标签显示一个
	var t = lables.length/maxsize;
	if(parseInt(t)<1){//标签数没有达到最大显示数
		sp = 1 ;
	}else if(t == parseInt(t)){//整除
		sp = parseInt(t);
	}else{
		sp = parseInt(t) + 1;
	}
	if(type=="hour"){
		for(var i=0;i<lables.length;i++){
			var sd = lables[i].split(" ");
			var ld = sd[0].split("-");
			var rd = sd[1].split(":");
			if(i%sp==0){
				formatLables[i] = rd[0]+"/"+ld[2];				
			}else{
				formatLables[i] = "";
			}
		}
	}else if(type=="day"){
		for(var i=0;i<lables.length;i++){
			var sd = lables[i].split(" ");
			var ld = sd[0].split("-");
			if(i%sp==0){
				formatLables[i] = ld[1]+"-"+ld[2];				
			}else{
				formatLables[i] = "";
			}
		}
	}
	else if(type=="week"){
		for(var i=0;i<lables.length;i++){
			var sd = lables[i].split("~");
			var ld = sd[0].split("-");
			var rd = sd[1].split("-");
			if(i%sp==0){
				formatLables[i] = sd[0];				
			}else{
				formatLables[i] = "";
			}
		}
	}else if(type=="month"){
		for(var i=0;i<lables.length;i++){
			if(i%sp==0){
				formatLables[i] =  lables[i];				
			}else{
				formatLables[i] = "";
			}
		}
	}else{
		return lables;
	}
	return formatLables;
}
/*
 * 获取数组最大值
 * @param datas
 * @return
 */
function getMaxData(datas){
	var max =0;
	if(datas.length>0){
		max = datas[0];
		for(var i=1;i<datas.length;i++){
			if(datas[i]>max){
				max = datas[i];
			}
		}
	}
	return max;
}
function getMaxPercentData(datas){
	var max =0;
	var ndatas = [];
	if(datas.length>0){
		for(var s=0; s<datas.length;s++){
			var n = datas[s].split('%')[0];
			ndatas[s]=n;
		}
		
		var nndatas = parseFloat(ndatas);
	
		max = nndatas[0]+1;
		for(var i=1;i<nndatas.length;i++){
			if(nndatas[i]>max){
				max = nndatas[i]+1;
			}
		}
	}
	return max;
}
/*
 * 以万de方式格式化数据
 */
function formatNumberByWan(datas){
	var obj = new Object();
	var unit = "";
	var max = getMaxData(datas);
	var formatdates = [];
	if(max>100000){//十万以上
		for(var i=0;i<datas.length;i++){
			formatdates[i] = (parseInt((datas[i]/10000)*100+0.5))/100;
			unit = "万";
		}
	}else if(max>1000000000){//十亿以上
		for(var i=0;i<datas.length;i++){
			formatdates[i] = (parseInt((datas[i]/100000000)*100+0.5))/100;
			unit = "亿";
		}
	}else{
		formatdates = datas;
	}
	obj.datas = formatdates;
	obj.unit = unit;
	return obj;
}
/*
 *提升单位
 */
function upUnitBy1024(values,len){
	var results = [];
	var sr;
	if(len>4||len<0){
		alert("转化参数非法，转化失败！");
	}else if(len==1){
		for(var i=0;i<values.length;i++){
			sr = values[i]/1024;
			results[i] = (parseInt(sr*100+0.5))/100;//四舍五入
		}
	}else if(len==2){
		for(var i=0;i<values.length;i++){
			sr = values[i]/(1024*1024);
			results[i] = (parseInt(sr*100+0.5))/100;
		}
	}else if(len==3){
		for(var i=0;i<values.length;i++){
			sr = values[i]/(1024*1024*1024);
			results[i] = (parseInt(sr*100+0.5))/100;
		}
	}else if(len==4){
		for(var i=0;i<values.length;i++){
			sr = values[i]/(1024*1024*1024*1024);
			results[i] = (parseInt(sr*100+0.5))/100;
		}
	}else{
		results = values;
	}
	return results;
}
/*
 *检查数据 应该怎么转换
 *maxdata 最大的数值
 *maxs 最大支持的转换次数（即转换到TB需转换的次数 如初始单位为B时此值为4）
 */
function checkDataBy1024(maxdata,maxs){
	var s = 0 ;
	if(maxdata>10*1024*1024*1024*1024){
		s=4;
	}else if(maxdata>10*1024*1024*1024){
		s=3;
	}else if(maxdata>10*1024*1024){
		s=2;
	}else if(maxdata>10*1024){
		s=1;
	}else{
		s=0;
	}
	return s>maxs?maxs:s;
}

function formatPercentDate(datas){
	var obj = new Object();
	var formatdates = [];
	var unit = "";
	 if(datas.length>0){
		for(var s=0; s<datas.length;s++){
			var n = datas[s].split('%');
			formatdates[s] = parseFloat(n[0]);
		}
	}
	obj.datas=formatdates;
	obj.unit="%";
	
	return obj;
}

/*
 * 以1024de方式格式化数据
 * inunit 初始单位(KB,MB,GB,TB,默认或为空时为B)
 * 本函数暂时只支持inunit（MB,）,需要其他应修改此函数
 */
function formatDataBy1024(datas,inunit){
	var obj = new Object();
	var formatdates = [];
	var unit = "";
	var max = getMaxData(datas);
	
	//只支持MB要支持其他在此添加分支
	if(inunit=="MB"){
		var to = checkDataBy1024(max,2);
		if(to==0){
			unit = "MB";
			formatdates = datas;
		}else if(to==1){
			unit = "GB";
			formatdates=upUnitBy1024(datas,1);
		}else{
			unit = "TB";
			formatdates=upUnitBy1024(datas,2);
		}
		obj.datas = formatdates;
		obj.unit = unit;
	}else if(inunit=="KB"){
		var to = checkDataBy1024(max,3);
		if(to==0){
			unit = "KB";
			formatdates = datas;
		}else if(to==1){
			unit = "MB";
			formatdates=upUnitBy1024(datas,1);
		}else if(to==2){
			unit = "GB";
			formatdates=upUnitBy1024(datas,2);
		}else{
			unit = "TB";
			formatdates=upUnitBy1024(datas,3);
		}
		obj.datas = formatdates;
		obj.unit = unit;
	}else{
		obj.datas = datas;
		obj.unit = inunit;
	}
	return obj;
}
/*
 * 组装包含回调事件的柱状图数据
 * label:回调时，传回的能够标识所点柱的数据
 * value:数据y值
 */
function addCallFunction(label,value){
	return {"top":value, "on-click": "callback('"+label+"')"};
}
/*
 * 以1024de方式格式化数据
 * objs 为Object类型对象
 * inunit 初始单位(KB,MB,GB,TB,默认或为空时为B)
 * 本函数暂时只支持inunit（MB,）,需要其他应修改此函数
 */
function formatObjDataBy1024(objs,inunit){
	var datas = [];
	for(var i=0;i<objs.length;i++){
		datas[i] = objs[i].top;
	}
	var fobj= formatDataBy1024(datas,inunit);
	datas = fobj.datas;
	for(var i=0;i<objs.length;i++){
		objs[i].top = datas[i] ;
	}
	fobj.datas = objs;
	return fobj;
}
function formatObjNumberByWan(objs){
	var datas = [];
	for(var i=0;i<objs.length;i++){
		datas[i] = objs[i].top;
	}
	var fobj= formatNumberByWan(datas);
	datas = fobj.datas;
	for(var i=0;i<objs.length;i++){
		objs[i].top = datas[i] ;
	}
	fobj.datas = objs;
	return fobj;
}
function buildLineChartTip(datas,showlbs,funit){
	if(datas==null||datas.size==0){
			return datas;
	}
	var rsdatas = [];
	var label = "";
	var unit = "";
	if(funit!=""){
		label = funit.split("@@")[0]+":";
		if(typeof(funit.split("@@")[1])!="undefined")
		unit = funit.split("@@")[1];
	}
	if(typeof(datas[0].value)!="undefined"){
		for(var i=0;i<datas.length;i++){
			rsdatas[i] = datas[i];
			rsdatas[i].tip=showlbs[i]+"\n"+label+"#val#"+unit;
		}
	}else{
		for(var i=0;i<datas.length;i++){
			rsdatas[i] = {"value":datas[i],"tip":showlbs[i]+"\n"+label+"#val#"+unit}
		}
	}
	return rsdatas;
}
function buildBarChartTip(datas,showlbs,funit){
	if(datas==null||datas.size==0){
		return datas;
	}
	var rsdatas = [];
	var label = "";
	var unit = "";
	if(funit!=""){
		label = funit.split("@@")[0]+":";
		if(typeof(funit.split("@@")[1])!="undefined")
		unit = funit.split("@@")[1];
	}
	if(typeof(datas[0].top)!="undefined"){
		for(var i=0;i<datas.length;i++){
			rsdatas[i] = datas[i];
			rsdatas[i].tip=showlbs[i]+"\n"+label+"#val#"+unit;
		}
	}else{
		for(var i=0;i<datas.length;i++){
			rsdatas[i] = {"top":datas[i],"tip":showlbs[i]+"\n"+label+"#val#"+unit};
		}
	}
	return rsdatas;
}
function buildHBarChartTip(datas,showlbs,funit,showdts,lab2){
	if(datas==null||datas.size==0){
		return datas;
	}
	var rsdatas = [];
	var label = "";
	var unit = "";
	if(funit!=""){
		label = funit.split("@@")[0]+"：";
		if(typeof(funit.split("@@")[1])!="undefined")
		unit = funit.split("@@")[1];
	}
	if(typeof(datas[0].right)!="undefined"){
		for(var i=0;i<datas.length;i++){
			rsdatas[i] = datas[i];
			rsdatas[i].tip=showlbs[i]+"\n"+lab2+"："+showdts[i]+"\n"+label+"#val#"+unit;
		}
	}else{
		for(var i=0;i<datas.length;i++){
			rsdatas[i] = {"right":datas[i],"tip":showlbs[i]+"\n"+lab2+"："+showdts[i]+"\n"+label+"#val#"+unit};
		}
	}
	//ofc图表问题，必须对数组进行倒置
	return conversData(rsdatas);
}
//倒置数组
function conversData(data){
	/*
	 * 修改了open flash chart的bug，不用再倒置数组了
	 */
	/*var cdlist = [];
	var cnt = data.length;
	if(cnt>1){
		for(var i=cnt-1;i>=0;i--){
			cdlist[cnt-1-i] = data[i];
		}
	}else{
		cdlist = data;
	}
	return cdlist;
	*/
	return data;
}
function buildEmptyAxisChart(){
	var title = {
	    "text" : "没有数据显示",
	    "style" : "{font-size: 14px; color:#333333; font-family: Verdana; text-align: center;padding-top:10px;}"
	  };
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
		      	"type":      "bar",
		      	"alpha":     1.0,
		      	"colour":    "#1B95D9",
		      	"text":      "",
		      	"font-size": 10,
		      	"values" :   []
		    }],
		"x_axis":{
				"stroke":1,
				"tick_height":3,
				"colour":"#909090",
				"grid_colour":"#333333",
				"labels": {
		      		"labels": []
		  			}
				},
		"y_axis":{
			    "stroke":      1,
			    "tick_length": 3,
			    "colour":      "#1B95D9",
			    "grid_colour": "#333333"
			 } 
  }
  	return chart;
}