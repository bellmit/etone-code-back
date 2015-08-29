/* login.htm */ 
function goLogin()
{
//	window.showModalDialog("/login.jsp", "",  "dialogWidth:800px; dialogHeight:500px;status:No; ");
		var W = screen.availWidth;
		var H = screen.availHeight;
		var L = (W-800)/2;
		var T = (H-500)/2;
		var newWindow = window.open("/login.jsp","","left="+L+",top="+T+",toolbar=no,status=no,width=800,height=500,resizable=no,scrollbars=no");

}

/*commom*/
function Confirm()
{
	var ReturnValue = confirm("你确定要删除该记录吗?");
	if (ReturnValue == true)
		{alert("记录已删除!")}
	else
		{return null}
}

function OpenWin( sURL , sFeatures )
{
	window.open( sURL , null , sFeatures , null)
	//window.open("Sample.htm",null,"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no");
	//window.open( [sURL] [, sName] [, sFeatures] [, bReplace])
	//sName{_blank; _media; _parent; _search; _self; _top}
	//sFeatures{channelmode; directories; fullscreen; height; left; location; menubar; resizable; scrollbars; status; titlebar; toolbar; top; width} 
}

function ShowWin( sURL , sFeatures )
{
	window.showModalDialog( sURL , null , sFeatures)
	//window.showModalDialog("Sample.htm",null,"dialogHeight:591px;dialogWidth:650px;")
	//window.showModalDialog([sURL] [, vArguments] [, sFeatures])
	//sFeatures{dialogHeight; dialogLeft; dialogTop; dialogWidth; center; dialogHide; edge; help; resizable; scroll; status; unadorned}
}
