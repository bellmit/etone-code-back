<%@ page contentType="text/html;charset=GBK" %>
<html>
<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<script language="JavaScript">
    function openMain()
    {
      	 mapWin = window.open('main.jsp', '_blank', 'left=0,top=0,width=screen.availWidth,height=screen.availHeight,menubar=no,location=no,scrollbars=no,toolbar=no,status=yes,titlebar=0,resizable=yes,tabbar=no,AddressBar=no,copyhistory=no');
	
	
        if (mapWin == null)
        {
            alert("注意: 要正常使用本系统,请关闭或删除您的浏览中拦截弹出窗口的功能或插件!");
            return;
        }

        var larg = 0;
        var altez = 0;
        if (document.layers)
        {
            larg = screen.availWidth - 0;
            altez = screen.availHeight - 0;
        }
        else
        {
            larg = screen.availWidth + 8;
            altez = screen.availHeight + 8;
        }
        mapWin.resizeTo(larg, altez);
        mapWin.moveTo(-4, -4);
        shutwin();
    }

    function shutwin()
    {
       	window.opener = null; 
		window.open("","_self"); //fix ie7 
        window.close();
        return;
    }
    openMain();
</script>
</html>