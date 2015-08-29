//全部变量,设置弹出窗口在IE不同版本中的大小
var dialogWidth = "680px";
var dialogHeight = "420px";
//检查IE版本信息
function checkIE() {
	var explorName, explorVersion, flag;
	explorVersion = window.navigator.appVersion;
	explorName = window.navigator.appName;
	if (explorName == "Microsoft Internet Explorer") {
		flag = parseFloat(explorVersion.substring(explorVersion.indexOf("MSIE") + 5, explorVersion.lastIndexOf("Windows")));
	} else {
		flag = parseFloat(explorVersion);
	}
	return flag;
}
//根据IE版本设置弹出模式窗口的大小
$(function () {
	if (checkIE() == 6) {
		dialogHeight = "480px";
	}
});

