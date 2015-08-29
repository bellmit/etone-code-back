<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title>测试</title>
	</head>
	<body>
		<br>
		<table>
			<tr>
				<td>
					<div id="flash" style="display: block">
					<input type="button" name="changedata" value="改变数据" onclick="changData()">
							<div id="flash" style="display: block">
						<!-- --><iframe name="frm1"
							src="/flash/DoublePieChart.html?getdata=js&canclick=true&dataflag=3" scrolling="no"
							id="frm1"
							style="HEIGHT: 322px; VISIBILITY: inherit; width: 922px; Z-INDEX: 1"
							marginwidth="1" marginheight="1" border="0" frameborder="0"
							align="center"></iframe>
						<br><br>
					 	<iframe name="frm2"
							src="/flash/ThreePieChart.html?getdata=js&canclick=true&dataflag=2" scrolling="no"
							id="frm2"
							style="HEIGHT: 242px; VISIBILITY: inherit; width: 922px; Z-INDEX: 1"
							marginwidth="1" marginheight="1" border="0" frameborder="0"
							align="center"></iframe>
						<br>
						<iframe name="frm3"
							src="/flash/LineColumnChart.html?getdata=xml&canclick=true&dataflag=1"
							scrolling="no" id="frm3"
							style="HEIGHT: 322px; VISIBILITY: inherit; width: 922px; Z-INDEX: 1"
							marginwidth="1" marginheight="1" frameborder="0" frameborder="0"
							align="center"></iframe>
					</div> 
					<!--<input type="button" name="changedata" value="改变数据" onclick="changData()">
							<div id="flash" style="display: block">
						<iframe name="frm4"
							src="/flash/Test.html?getdata=xml&dataflag=1" scrolling="yes"
							id="frm4"
							style="HEIGHT: 420px; VISIBILITY: inherit; width: 920px; Z-INDEX: 1"
							marginwidth="1" marginheight="1" border="0" frameborder="0"
							align="center"></iframe>
						<br>
				<iframe name="frm5"
							src="/flash/BaseChart.html?configtype=js&configid=2&subcharts=1_2_3_4_5" scrolling="yes"
							id="frm5"
							style="HEIGHT: 420px; VISIBILITY: inherit; width: 620px; Z-INDEX: 1"
							marginwidth="1" marginheight="1" border="0" frameborder="0"
							align="center"></iframe>
						<br>
						<iframe name="frm6"
							src="/flash/BaseChart.html?configtype=js&configid=3&subcharts=1_4"
							scrolling="yes" id="frm6"
							style="HEIGHT: 420px; VISIBILITY: inherit; width: 620px; Z-INDEX: 1"
							marginwidth="1" marginheight="1" border="0" frameborder="0"
							align="center"></iframe> -->
					</div>
					<br>
				</td>
			</tr>
		</table>
	</body>
</html>
<script language="JavaScript" type="text/javascript">
/*
function initChart(){
	//alert("----");
	return "TEST_WEB_DISTRIBUTE_AREA_DAY|1|2008-1-1 00:00:00|总流量";
}*/
function initChartData(flag){
	//alert("----"+flag);
	if(flag=='1'){//曲线柱状图
		return '<root><chart id="1" name="2009年10月流量用户数天走势图" fields="流量|用户数">'+
'<data label="2009-10-1" shortlabel = "1" total="1200" up="898" down="221" imsis ="331"/>'+
'<data label="2009-10-2" shortlabel = "2" total="1400" up="118" down="445" imsis ="666"/>'+
'<data label="2009-10-3" shortlabel = "3" total="1800" up="777" down="135" imsis ="712"/>'+
'<data label="2009-10-4" shortlabel = "4" total="7300" up="68" down="89" imsis ="509"/>'+
'<data label="2009-10-5" shortlabel = "5" total="5200" up="818" down="412" imsis ="665"/>'+
'<data label="2009-10-6" shortlabel = "6" total="4700" up="898" down="16" imsis ="212"/>'+
'<data label="2009-10-7" shortlabel = "3" total="1800" up="777" down="135" imsis ="712"/>'+
'<data label="2009-10-8" shortlabel = "4" total="7300" up="68" down="89" imsis ="509"/>'+
'<data label="2009-10-9" shortlabel = "5" total="5200" up="818" down="412" imsis ="665"/>'+
'<data label="2009-10-10" shortlabel = "6" total="4700" up="898" down="16" imsis ="212"/>'+
'<data label="2009-10-11" shortlabel = "1" total="1200" up="898" down="221" imsis ="331"/>'+
'<data label="2009-10-12" shortlabel = "2" total="1400" up="118" down="445" imsis ="666"/>'+
'<data label="2009-10-13" shortlabel = "3" total="1800" up="777" down="135" imsis ="712"/>'+
'<data label="2009-10-14" shortlabel = "4" total="7300" up="68" down="89" imsis ="509"/>'+
'<data label="2009-10-15" shortlabel = "5" total="5200" up="818" down="412" imsis ="665"/>'+
'<data label="2009-10-16" shortlabel = "6" total="4700" up="898" down="16" imsis ="212"/>'+
'<data label="2009-10-17" shortlabel = "3" total="1800" up="777" down="135" imsis ="712"/>'+
'<data label="2009-10-18" shortlabel = "4" total="7300" up="68" down="89" imsis ="509"/>'+
'<data label="2009-10-19" shortlabel = "5" total="5200" up="818" down="412" imsis ="665"/>'+
'<data label="2009-10-20" shortlabel = "6" total="4700" up="898" down="16" imsis ="212"/>'+
'<data label="2009-10-21" shortlabel = "1" total="1200" up="898" down="221" imsis ="331"/>'+
'<data label="2009-10-22" shortlabel = "2" total="1400" up="118" down="445" imsis ="666"/>'+
'<data label="2009-10-23" shortlabel = "3" total="1800" up="777" down="135" imsis ="712"/>'+
'<data label="2009-10-24" shortlabel = "4" total="7300" up="68" down="89" imsis ="509"/>'+
'<data label="2009-10-25" shortlabel = "5" total="5200" up="818" down="412" imsis ="665"/>'+
'<data label="2009-10-26" shortlabel = "6" total="4700" up="898" down="16" imsis ="212"/>'+
'<data label="2009-10-27" shortlabel = "3" total="1800" up="777" down="135" imsis ="712"/>'+
'<data label="2009-10-28" shortlabel = "4" total="7300" up="68" down="89" imsis ="509"/>'+
'<data label="2009-10-29" shortlabel = "5" total="5200" up="818" down="412" imsis ="665"/>'+
'<data label="2009-10-30" shortlabel = "6" total="4700" up="898" down="16" imsis ="212"/>'+

'</chart></root>';
	
	}else if(flag=='2'){	//三个饼的图表
		return '<root><chart id="1" name="2009年10月1日#FIELD#APN分布图" fields="流量|用户数|激活次数">'+
'<data label="CMNET" total="1200" up="898" down="221" imsis ="331" count="777"/>'+
'<data label="CMWAP" total="1400" up="118" down="445" imsis ="666"  count="987"/>'+
'<data label="企业APN" total="1800" up="777" down="135" imsis ="712"  count="567"/>'+
'<data label="其他" total="7300" up="68" down="89" imsis ="509"  count="888"/></chart></root>';
	}else if(flag=='3'){//两个饼的图表
	return '<root><chart id="1" name="2009年10月1日#FIELD#APN分布图" fields="流量|用户数">'+
'<data label="CMNET" total="1200" up="898" down="221" imsis ="331"/>'+
'<data label="CMWAP" total="1400" up="118" down="445" imsis ="666"/>'+
'<data label="企业APN" total="1800" up="777" down="135" imsis ="712"/>'+
'<data label="其他" total="7300" up="68" down="89" imsis ="509"/></chart></root>';
	}else{
	alert("参数错误");
	}
	
}

//标识通过url参数传入，初始化配置有两种方式
//1.通过XML,必须参数style=base_pie_test，使用这种方法，flash读取/MantoEye_SZ/webapp/flash/config/config.xml下的配置文件“base_pie_test”替换为配置文件的ID(例如：frm1,frm2,frm3)
//2.通过js,必须参数configtype=js以及configid=?,configid参数标识图表，会以参数的形式回调js函数chartConfig，在chartConfig函数内返回配置文件的xml字符串(例如：frm4,frm5,frm6)
//返回xml的格式见上面,另外两个url参数说明：subcharts表示图表内包含的子图表id（在/MantoEye_SZ/webapp/flash/config/config.xml内配置）;dataflag 通过js函数initChartData设置数据时的标识
//返回值说明-- 图表宽度|图表高度|图表类型|图表是否有点击事件|图表获取数据方式
/*function chartConfig(configid){
if(configid=="1"){//当标识为1时，图表为piechart
 return "575|348|pie|true|js";
}else if(configid=="2"){//标识为2时，图表为linechart
 return "575|348|line|true|js";
}else{
  return "575|348|column|true|js";
}
}*/
function initChartWidth(flag){
// alert("**initChartWidth**");
if(flag=='2'){
 return "920|240";
}else{
 return "920|320";
}

}
function chartItemClick(label){
	alert("**success**"+label);
}
function changData(){
var xml='<root><chart id="1" name="2009-10-1">'+
'<data label="2009-10-1" shortlabel="1" total="6200" up="898" down="221" imsis ="337"/>'+
'<data label="2009-10-2" shortlabel="2" total="6400" up="118" down="445" imsis ="676"/>'+
'<data label="2009-10-3" shortlabel="3" total="6800" up="777" down="135" imsis ="912"/>'+
'<data label="2009-10-4" shortlabel="4" total="3300" up="68" down="89" imsis ="119"/>'+
'<data label="2009-10-5" shortlabel="5" total="3200" up="818" down="412" imsis ="165"/>'+
'<data label="2009-10-6" shortlabel="6" total="3700" up="898" down="16" imsis ="216"/></chart></root>';

document.frm3.LineColumnChart.jsChangeData(xml);
}
</script>
