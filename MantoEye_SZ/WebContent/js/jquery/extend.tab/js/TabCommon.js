//alert('new tab');
var a = 2;
var tabpanel = null;
tabpanel= new TabPanel({
		//renderTo:'tab',
		//width:'100%', //如果设置了宽度,就不会自动改变宽度
		//height:400,
		//border:'none',
		active : 0,
		items : [{
			id:'system_main_id',
			title:'系统欢迎页面',
			html:'<iframe src="/welcome.jsp" width="100%" height="100%" frameborder="0"></iframe>',
			closable: false
		}]
	});
function showTab(id){
	//alert(tabpanel);
	//alert('dd');
	if(id==1){
		tabpanel.addTab({title:'APN分布',id:''+id, html:'<iframe src="/apnDistribute_list.do" width="100%" height="100%" frameborder="0"></iframe>'});
	}else if(id==2){
		tabpanel.addTab({title:'应用协议分布',id:''+id, html:'<iframe src="/wapDistribute_list.do" width="100%" height="100%" frameborder="0"></iframe>'});
	}else {
		tabpanel.addTab({title:'传输协议分布',id:''+id, html:'<iframe src="/tcpDistribute_list.do" width="100%" height="100%" frameborder="0"></iframe>'});
	}
}