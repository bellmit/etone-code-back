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
		<td>
									<div id="flash" style="display:block">
										<iframe name="frm2" src="/flash/MantoEye_Main.html?1=1"
											scrolling="yes" id="frm2"
											style="HEIGHT: 350px; VISIBILITY: inherit; width: 1025px; Z-INDEX: 1"
											marginwidth="1" marginheight="1" border="0" frameborder="0"
											align="center"></iframe>
									<br>
									</td>
		</table>
	</body>
</html>
<script>
function flashAreaDbClick(areaName){
alert("----"+areaName)
}
//调用改变数据的方法
function changeData(){
	alert('changeData call');
	var s = frm2.MantoEye_Main.changeData();
}
//调用改变数据的方法
function initSize(){
	return "962|350";
}
//返回数据
var tmd = 'tmd';
function loadDataString(){
	//alert('parent-->'+parent.dataType);
	var rs = '<?xml version="1.0" encoding="UTF-8"?>'+
'<Data>'+
'<Stat id="BSC" title="2009年10月1日BSC流量分布X">'+
'<Statdata name="BSC01" totalFlush="452" upFlush="377" downFlush="75" imsis="3896"/>'+
'<Statdata name="BSC02" totalFlush="704" upFlush="396" downFlush="308" imsis="3905"/>'+
'<Statdata name="BSC03" totalFlush="521" upFlush="263" downFlush="258" imsis="4567"/>'+
'<Statdata name="BSC04" totalFlush="872" upFlush="530" downFlush="342" imsis="5405"/>'+
'<Statdata name="BSC05" totalFlush="108" upFlush="56" downFlush="52" imsis="4959"/>'+
'<Statdata name="BSC06" totalFlush="430" upFlush="63" downFlush="367" imsis="3953"/>'+
'<Statdata name="BSC07" totalFlush="916" upFlush="406" downFlush="510" imsis="2604"/>'+
'<Statdata name="BSC08" totalFlush="663" upFlush="161" downFlush="502" imsis="4083"/>'+
'<Statdata name="BSC09" totalFlush="656" upFlush="453" downFlush="203" imsis="1986"/>'+
'<Statdata name="BSC10" totalFlush="454" upFlush="336" downFlush="118" imsis="756"/>'+
'<Statdata name="BSC11" totalFlush="399" upFlush="89" downFlush="310" imsis="1571"/>'+
'<Statdata name="BSC12" totalFlush="795" upFlush="338" downFlush="457" imsis="4590"/>'+
'<Statdata name="BSC13" totalFlush="668" upFlush="453" downFlush="215" imsis="2888"/>'+
'<Statdata name="BSC14" totalFlush="367" upFlush="203" downFlush="164" imsis="1053"/>'+
'<Statdata name="BSC15" totalFlush="568" upFlush="54" downFlush="514" imsis="3944"/>'+
'<Statdata name="BSC16" totalFlush="533" upFlush="294" downFlush="239" imsis="4761"/>'+
'<Statdata name="BSC17" totalFlush="416" upFlush="276" downFlush="140" imsis="9692"/>'+
'<Statdata name="BSC18" totalFlush="694" upFlush="262" downFlush="432" imsis="5301"/>'+
'<Statdata name="BSC19" totalFlush="608" upFlush="308" downFlush="300" imsis="2432"/>'+
'<Statdata name="BSC20" totalFlush="870" upFlush="417" downFlush="453" imsis="3976"/>'+
'<Statdata name="BSC21" totalFlush="1014" upFlush="470" downFlush="544" imsis="6369"/>'+
'<Statdata name="BSC22" totalFlush="492" upFlush="175" downFlush="317" imsis="10055"/>'+
'<Statdata name="BSC23" totalFlush="701" upFlush="211" downFlush="490" imsis="4339"/>'+
'<Statdata name="BSC24" totalFlush="431" upFlush="263" downFlush="168" imsis="2804"/>'+
'<Statdata name="BSC25" totalFlush="578" upFlush="340" downFlush="238" imsis="8139"/>'+
'<Statdata name="BSC26" totalFlush="657" upFlush="228" downFlush="429" imsis="3267"/>'+
'<Statdata name="BSC27" totalFlush="416" upFlush="62" downFlush="354" imsis="2557"/>'+
'<Statdata name="BSC28" totalFlush="375" upFlush="180" downFlush="195" imsis="4825"/>'+
'<Statdata name="BSC29" totalFlush="574" upFlush="280" downFlush="294" imsis="4417"/>'+
'<Statdata name="BSC30" totalFlush="781" upFlush="411" downFlush="370" imsis="5156"/>'+
'<Statdata name="BSC31" totalFlush="437" upFlush="123" downFlush="314" imsis="2423"/>'+
'<Statdata name="BSC32" totalFlush="753" upFlush="245" downFlush="508" imsis="2827"/>'+
'<Statdata name="BSC33" totalFlush="910" upFlush="428" downFlush="482" imsis="6182"/>'+
'<Statdata name="BSC34" totalFlush="754" upFlush="228" downFlush="526" imsis="4936"/>'+
'<Statdata name="BSC35" totalFlush="400" upFlush="122" downFlush="278" imsis="8571"/>'+
'<Statdata name="BSC36" totalFlush="287" upFlush="111" downFlush="176" imsis="2969"/>'+
'<Statdata name="BSC37" totalFlush="581" upFlush="333" downFlush="248" imsis="1295"/>'+
'<Statdata name="BSC38" totalFlush="323" upFlush="84" downFlush="239" imsis="2468"/>'+
'<Statdata name="BSC39" totalFlush="255" upFlush="169" downFlush="86" imsis="1660"/>'+
'<Statdata name="BSC40" totalFlush="646" upFlush="426" downFlush="220" imsis="8547"/>'+
'<Statdata name="BSC41" totalFlush="916" upFlush="372" downFlush="544" imsis="8294"/>'+
'<Statdata name="BSC42" totalFlush="427" upFlush="144" downFlush="283" imsis="1082"/>'+
'<Statdata name="BSC43" totalFlush="737" upFlush="454" downFlush="283" imsis="2540"/>'+
'<Statdata name="BSC44" totalFlush="695" upFlush="519" downFlush="176" imsis="3735"/>'+
'<Statdata name="BSC45" totalFlush="379" upFlush="290" downFlush="89" imsis="436"/>'+
'<Statdata name="BSC46" totalFlush="486" upFlush="140" downFlush="346" imsis="9920"/>'+
'<Statdata name="BSC47" totalFlush="576" upFlush="259" downFlush="317" imsis="2800"/>'+
'<Statdata name="BSC48" totalFlush="527" upFlush="339" downFlush="188" imsis="107"/>'+
'<Statdata name="BSC49" totalFlush="593" upFlush="509" downFlush="84" imsis="7349"/>'+
'<Statdata name="BSC50" totalFlush="964" upFlush="446" downFlush="518" imsis="5718"/>'+
'<Statdata name="BSC51" totalFlush="309" upFlush="58" downFlush="251" imsis="407"/>'+
'<Statdata name="BSC52" totalFlush="839" upFlush="333" downFlush="506" imsis="5551"/>'+
'<Statdata name="BSC53" totalFlush="305" upFlush="80" downFlush="225" imsis="141"/>'+
'<Statdata name="BSC54" totalFlush="743" upFlush="255" downFlush="488" imsis="7180"/>'+
'<Statdata name="BSC55" totalFlush="591" upFlush="418" downFlush="173" imsis="4161"/>'+
'<Statdata name="BSC56" totalFlush="657" upFlush="207" downFlush="450" imsis="6265"/>'+
'<Statdata name="BSC57" totalFlush="714" upFlush="275" downFlush="439" imsis="9088"/>'+
'<Statdata name="BSC58" totalFlush="596" upFlush="348" downFlush="248" imsis="9237"/>'+
'<Statdata name="BSC59" totalFlush="528" upFlush="352" downFlush="176" imsis="3459"/>'+
'<Statdata name="BSC60" totalFlush="662" upFlush="344" downFlush="318" imsis="8565"/>'+
'<Statdata name="BSC61" totalFlush="491" upFlush="352" downFlush="139" imsis="2391"/>'+
'<Statdata name="BSC62" totalFlush="558" upFlush="461" downFlush="97" imsis="9044"/>'+
'<Statdata name="BSC63" totalFlush="550" upFlush="423" downFlush="127" imsis="4238"/>'+
'<Statdata name="BSC64" totalFlush="607" upFlush="157" downFlush="450" imsis="1008"/>'+
'<Statdata name="BSC65" totalFlush="515" upFlush="142" downFlush="373" imsis="1622"/>'+
'<Statdata name="BSC66" totalFlush="531" upFlush="380" downFlush="151" imsis="1882"/>'+
'<Statdata name="BSC67" totalFlush="1022" upFlush="478" downFlush="544" imsis="3335"/>'+
'<Statdata name="BSC68" totalFlush="631" upFlush="470" downFlush="161" imsis="793"/>'+
'<Statdata name="BSC69" totalFlush="704" upFlush="396" downFlush="308" imsis="113"/>'+
'<Statdata name="BSC70" totalFlush="530" upFlush="117" downFlush="413" imsis="4529"/>'+
'<Statdata name="BSC71" totalFlush="815" upFlush="318" downFlush="497" imsis="9098"/>'+
'<Statdata name="BSC72" totalFlush="513" upFlush="288" downFlush="225" imsis="9181"/>'+
'<Statdata name="BSC73" totalFlush="598" upFlush="150" downFlush="448" imsis="4310"/>'+
'<Statdata name="BSC74" totalFlush="819" upFlush="536" downFlush="283" imsis="6353"/>'+
'<Statdata name="BSC75" totalFlush="888" upFlush="998" downFlush="327" imsis="9681"/>'+
'<Statdata name="BSC76" totalFlush="605" upFlush="361" downFlush="244" imsis="4308"/>'+
'<Statdata name="BSC77" totalFlush="749" upFlush="496" downFlush="253" imsis="3198"/>'+
'<Statdata name="BSC78" totalFlush="667" upFlush="143" downFlush="524" imsis="4404"/>'+
'<Statdata name="BSC79" totalFlush="466" upFlush="79" downFlush="387" imsis="5146"/>'+
'<Statdata name="BSC80" totalFlush="767" upFlush="300" downFlush="467" imsis="1716"/>'+
'</Stat>'+
'</Data>';
	return rs;
}

</script>