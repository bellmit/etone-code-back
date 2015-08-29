//////////////////////////////////////////////////////////////////////
// ��ҳ��¼��ʾ���Ӳ������ܽű�
//
// MaxChou(2004-9-9)
// EtoneTech.
//////////////////////////////////////////////////////////////////////
/**
 * ������м�¼
 *
 * @param group ������,���Ϊnull�����.����һ��ҳ����ж����ҳ���ʱ.
 */
function selectAll(group) {
	if (group == null) {
		group = "";
	}

	// �õ���ǰҳ�ļ�¼��
	var markCount = document.getElementById("MarkCountHandle" + group);
	if (markCount == null) {
		return;
	}
	var i = 0;
	var markObj = null;
	for (i = 1; i <= markCount.value; i++) {
		markObj = document.getElementById("ChkMark" + i + "_Handle" + group);
		if (markObj != null && markObj.disabled != true) {
			markObj.checked = true;
			markOneRecord(i, group);
		}
	}
}
function selectAllById(id) {
	var checkbox = document.getElementsByName(id);
	for (var i = 0; i < checkbox.length; i++) {
		checkbox[i].checked = true;
		n_markOnemarkOneRecord(checkbox[i], i + 1, "");
	}
}
function unselectById(id) {
	var checkbox = document.getElementsByName(id);
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked) {
			checkbox[i].checked = false;
			n_markOnemarkOneRecord(checkbox[i], i + 1, "");
		} else {
			checkbox[i].checked = true;
			n_markOnemarkOneRecord(checkbox[i], i + 1, "");
		}
	}
}
function n_markOnemarkOneRecord(obj, index, group) {
	if (group == null) {
		group = "";
	}
	var tt = document.getElementById("TblTr" + index + "_Handle" + group);
	var cmh = obj;
	if (cmh != null && tt != null) {
		if (cmh.checked == true) {
			tt.bgColor = "#FFCC00";
		} else {
			tt.bgColor = "#FFFFFF";
		}
	}
}
function selectOneRecord(obj, index, group) {
	if (group == null) {
		group = "";
	}
	var tt = document.getElementById("TblTr" + index + "_Handle" + group);
	var cmh = obj;
	if (cmh != null && tt != null) {
		if (cmh.checked == true) {
			tt.bgColor = "#FFCC00";
		} else {
			tt.bgColor = "#FFFFFF";
		}
	}
}
function getSelectCount(id) {
	var checkbox = document.getElementsByName(id);
	var num = 0;
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked == true) {
			num++;
		}
	}
	return num;
}
function getSelectValue(id) {
	var arrayId = "";
	var checkbox = document.getElementsByName(id);
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked == true) {
			arrayId = arrayId + checkbox[i].value + ",";
		}
	}
	arrayId = arrayId.substring(0, arrayId.length - 1);
	return arrayId;
}
var gOldBgColor = "";
function overTr(src, index, checkboxid) {
	var checkbox = document.getElementsByName(checkboxid);
	if (checkbox[index - 1] != null && checkbox[index - 1].checked != true) {
		gOldBgColor = src.bgColor;
		src.bgColor = "#FFCC00";
	}
}
function outTr(src, index, checkboxid) {
	var checkbox = document.getElementsByName(checkboxid);
	if (checkbox[index - 1] != null && checkbox[index - 1].checked != true) {
		src.bgColor = gOldBgColor;
	}
}
var ctrlkey = null;
function keyDown(e) {
	ctrlkey = event.ctrlKey
}
document.onkeydown = keyDown;

function clickCheckbox(checkboxid, index) {
	var checkbox = document.getElementsByName(checkboxid);
	if(ctrlkey==null){
		for (var i = 0; i < checkbox.length; i++) {
			checkbox[i].checked = false;
			n_markOnemarkOneRecord(checkbox[i], i + 1, "");
		}
		checkbox[index - 1].checked = true;
		n_markOnemarkOneRecord(checkbox[index - 1], index, "");
	}else{
		checkbox[index - 1].checked = true;
		n_markOnemarkOneRecord(checkbox[index - 1], index, "");
	}
	ctrlkey= null;
}


