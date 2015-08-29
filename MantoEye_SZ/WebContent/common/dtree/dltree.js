//////////////////////////////////////////////////////////////////////
// 下拉树, DropDownList Tree
// version: 1.0
//
// 功能:
// 实现下拉列表的树型结构
//
// SiYuan(2006-5-30)
// EtoneTech.
//////////////////////////////////////////////////////////////////////
function dlItem(id, pid, text, selected)
{
	this.id = id;
	this.pid = pid;
	this.text = text;
	this.selected = selected;
	this._islast = false;
	this._hasChildren = false;
	this._parent;
	this._preIcon;
	this._prefix = '&nbsp;&nbsp;';
	this._depth = -1;
}

function dlTree(objName, selectId, action, size)
{
	this.optionItems = [];
	this.prefix = [];
	this.obj = objName;
	this.size = size;
	this.action = action;
	this.selectId = selectId;
	this.root = new dlItem(-1);
	this.Icon = {
		hasNext : '├',
		hasChildren : '│',
		IsEnd : '└',
		Fix : '&nbsp;&nbsp;'
	};
}

dlTree.prototype.toString = function(){
	var sHtml = '<select name="'+this.obj+'" id="'+this.obj+'" onchange="'+this.action+'" size="">\n';
	sHtml += this.processOption(this.root);
	sHtml += '</select>\n';
	return sHtml;
};

dlTree.prototype.add = function(id, pid, text, selected){
	this.optionItems[this.optionItems.length] = new dlItem(id, pid, text, selected);
};

dlTree.prototype.processOption = function(pItem){
	var sHtml = '';
	var i = 0;
	for(i=0;i<this.optionItems.length;i++){
		if(this.optionItems[i].pid==pItem.id){
			var Item = this.optionItems[i];
			this.setItem(Item);
			Item._parent = pItem;
			Item._depth = Item._parent._depth + 1;
			sHtml += this.option(Item);
			if(Item._islast) break;
		}
	}
	return sHtml;
};

dlTree.prototype.option = function(Item){
	var strSelected;
	strSelected = this.selectId!=null?(Item.id==this.selectId?'selected':''):(Item.selected?'selected':'')
	var shtml = '<option value="'+Item.id+'" '+strSelected+'>'+this.indent(Item)+Item.text+'</option>\n';
	if(Item._hasChildren) shtml += this.processOption(Item); // 本行进行递归
	return shtml;
};

dlTree.prototype.indent = function(Item){
	var sHtml = '';
	var tmp;
	var prefix = [];
	if(Item._depth > 0){
		//*
		tmp = Item._parent;
		for(var i=1;i<Item._depth;i++){
			prefix[prefix.length] = tmp._islast?this.Icon.Fix:this.Icon.hasChildren; 
			tmp = tmp._parent;
		}
		//*/
		sHtml += Item._prefix + (prefix.reverse()).join("") + Item._preIcon;
	}
	return sHtml;
};

dlTree.prototype.setItem = function(Item){
	var lastId;
	var i = 0;
	for(i=0;i<this.optionItems.length;i++){
		if(this.optionItems[i].pid==Item.id) Item._hasChildren = true;
		if(this.optionItems[i].pid==Item.pid) lastId = this.optionItems[i].id;
	}
	if(lastId==Item.id) Item._islast = true;
	Item._preIcon = Item._islast?this.Icon.IsEnd:this.Icon.hasNext;
};