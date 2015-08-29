
/**
*Textarea 自动适应宽高 
*/
function setRows(obj) {
	var textarea = obj;
	var cols = textarea.cols;
	var str = textarea.value;
	str = str.replace(/ ?/, " ");
	var lines = textarea.rows;
	var chars = 0;
	for (i = 0; i < str.length; i++) {
		var c = str.charAt(i);
		chars++;
		if (c == " ") {
			lines++;
		} else {
			if (i > lines * (cols / 2)) {      //这里是中文模式除2 如果是输入英文字符修改为1 即可
			}
			lines++;
		}
	}
	textarea.setAttribute("rows", lines);
	textarea.style.height = lines * 13 + "px";
}
/**
*内容换行显示
*/
function replayContent($obj) {
	var v = $obj.html();
	var data = v.split("\\n");
	v = "";
	for (var i = 0; i < data.length; i++) {
		v = v + data[i] + "<br/>";
	}
	$obj.html(v);
}

