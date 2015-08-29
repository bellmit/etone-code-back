<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://"
   + request.getServerName() + ":" + request.getServerPort()
   + path + "/";
%>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  <title>部门管理</title>
  <link rel="stylesheet" type="text/css"
   href="<%=basePath%>/tools/gt-grid/gt_grid.css" />
  <script type="text/javascript"
   src="<%=basePath%>/tools/gt-grid/gt_msg_cn_gbk.js"></script>
  <script type="text/javascript"
   src="<%=basePath%>/tools/gt-grid/gt_grid_all.js"></script>
  <script type="text/javascript">
var dsConfig= {
 fields :[
  {name : 'depart_id',type:'int' },
  {name : 'depart_name'      },
  {name : 'order_id',type:'int' },
  {name:'if_valid',type:'int'}
 ]
};
var colsConfig = [
{id:'depart_id',header:'部门ID',editor:{type:'text',validRule:'R,integer'}},
{id:'depart_name',header:'部门名称',editor:{type:'text',validRule:'R,text'}},
{id:'order_id',header:'说明',editor:{type:'text',validRule:'R,integer'}},
{id:'if_valid',header:'启用',editor:{type:'select',options:{'1':'有效','0':'失效'}},renderer:GT.Grid.mappingRenderer({'1':'有效','0':'失效'},'未知')}
];

var gridConfig={

 id : "grid1",
 dataset : dsConfig ,
 columns : colsConfig ,
 container : 'grid1_container', 
 toolbarPosition : 'top',
 saveURL :'showDepart.action',
 toolbarContent : ' reload | add | del | save | state' ,
 loadURL : '/gtGrid_load.do?1=1',   
    remotePaging : false 
};

var mygrid=new GT.Grid( gridConfig );
GT.Utils.onLoad( function(){
 mygrid.render();
} );
</script>
</head>
<body>
<!-- grid的容器. -->
<div id="grid1_container" style="width:500px;height:600px">
</div>

 </body>
</html>