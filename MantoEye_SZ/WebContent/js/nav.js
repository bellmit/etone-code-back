/*
function openURL(url)
{
	window.location.href = url;
}
function openURL2(url, target)
{
	window.parent.open(url, target, "");
}
function openWindow(url, target)
{
	var w = (screen.width - 11) / 2;
	var h = screen.height - 60;
	var l = w / 2;
	var t = 0;
	var newWindow = window.open(url, target, "left=" + l + ",top=" + t + ",width=" + w + ",height=" + h + ",menubar=no,location=no,scrollbars=yes,toolbar=no,status=no,resizable=no");
	newWindow.focus();
}
function openDialog(url, target, w, h)
{
	var l = (screen.width - 11 - w) / 2;
	var t = (screen.height - 60 - h) / 2;
	var newWindow = window.open(url, target, "'left=" + l  + ",top=" + t + ",width=" + w + ",height=" + h + ",menubar=no,location=no,scrollbars=no,toolbar=no,status=no,resizable=no'");
	newWindow.focus();
}
function openDialogWithParam(url, param, target, w, h)
{
	var l = (screen.width - 11 - w) / 2;
	var t = (screen.height - 60 - h) / 2;
	var newWindow = window.open(url + "?url=" + param.replace(/\&/g, "^^^"), target, "left=" + l  + ",top=" + t + ",width=" + w + ",height=" + h + ",menubar=no,location=no,scrollbars=no,toolbar=no,status=no,resizable=no");
	newWindow.focus();
}

function openDialog_old(url, target, w, h, scrolling)
{
	var l = (screen.width - 11 - w) / 2;
	var t = (screen.height - 60 - h) / 2;
	var newWindow = window.open(url, target, "left=" + l  + ",top=" + t + ",width=" + w + ",height=" + h + ",menubar=no,location=no,scrollbars=" + scrolling + ",toolbar=no,status=no,resizable=no");
	newWindow.focus();
}

function openDialog(url,w,h)
{
	var l = (screen.width - 11 - w) / 2;
	var t = (screen.height - 60 - h) / 2;
	//loadDialogContent(url,w,h);
	var newWindow = window.open(url, target, "left=" + l  + ",top=" + t + ",width=" + w + ",height=" + h + ",menubar=no,location=no,scrollbars=" + scrolling + ",toolbar=no,status=no,resizable=no");
	newWindow.focus();
}
*  */
function showMMDialog(url,title,width,height){
    var v = window.showModalDialog(url,title,'dialogHeight:'+height+';dialogWidth:'+width+';scrollbars:no;resizable:yes;help:no;status:no;center:yes;');     
	//openDialog("/include/frame.jsp?" + url,width, height);
	return v;
}
function showNRMDialog(url,title,width,height){
    var v = window.showModalDialog(url,title,'dialogHeight:'+height+';dialogWidth:'+width+';scrollbars:no;resizable:no;help:no;status:no;center:yes;');     
	//openDialog("/include/frame.jsp?" + url,width, height);
	return v;
}
function openWindow(url, target,w,h)
{
	var l =(screen.width - w) / 2;;
	var t =(screen.height - h) / 2;
	var newWindow = window.open(url, target, "left=" + l + ",top=" + t + ",width=" + w + ",height=" + h + ",menubar=no,location=no,scrollbars=yes,toolbar=no,status=no,resizable=no");
	newWindow.focus();
}
