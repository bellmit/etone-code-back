<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title>工具栏组件</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/core.css"
			type="text/css"></link>
		<link rel="stylesheet" href="/js/jquery/extend.tab/css/Toolbar.css"
			type="text/css"></link>
		<script type="text/javascript"
			src="/js/jquery/extend.tab/js/Toolbar.js"></script>
	</head>
	<script type="text/javascript">
    //js的注释与html的注释放开,再看一下效果
    $(document).ready(function(){

      var toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : '新建',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
			//top.tabpanel.addTab({id:'baidu', title:'百度一下！', html:'<iframe src="http://www.baidu.com" width="100%" height="100%" frameborder="0"></iframe>'});
          }
        },'-',{
          type : 'button',
          text : '修改',
          bodyStyle : 'edit',
          useable : 'T',
          handler : function(){
			top.tabpanel.addTab({id:'google', title:'Google！', html:'<iframe src="http://www.google.cn" width="100%" height="100%" frameborder="0"></iframe>'});
          }
        },'-',{
          type : 'button',
          text : '权限设置',
          bodyStyle : 'role-setup',
          useable : 'T',
          handler : function(){
			top.tabpanel.addTab({title:'无限添加', html:'<iframe src="" width="100%" height="100%" frameborder="0"></iframe>'});
          }
        },'-',{
          type : 'button',
          text : '关联用户',
          bodyStyle : 'linked',
          useable : 'F'
        },'-',{
          type : 'button',
          text : '删除',
          bodyStyle : 'delete',
          useable : 'T',
          handler : function(){
            
          }
        },'-',{
          type:'az',
          text:'A-Z',
          bodyStyle:'search'
        }],
        azable : true,
        azparam : 'azparam',
        filters : [{
          id : 'ALL',
          title : '全部',
          bodyStyle : 'btn-all',
          handler : function(){
            
          }
        },{
          id : 'F',
          title : '未读',
          bodyStyle : 'filter-read-n',
          handler : function(){
            
          }
        },{
          id : 'T',
          title : '已读',
          bodyStyle : 'filter-read-y',
          handler : function(){
            
          }
        },{
          id : 'S',
          title : '已发送',
          bodyStyle : 'filter-sended',
          handler : function(){
            
          }
        }],
        active : 'ALL'//激活哪个
      });

	  toolbar.render();

	  toolbar.genAZ();
    });

	
    </script>
	<body>
		<!--div style="height:424px;"></div-->
		<div id="toolbar"></div>
		<input type="hidden" name="azparam" id="azparam" />
	</body>
</html>