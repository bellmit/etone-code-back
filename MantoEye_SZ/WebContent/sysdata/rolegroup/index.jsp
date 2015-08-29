<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/include/session.jsp"%>
<%@ include file="/include/setcache.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link type="text/css" rel="stylesheet"
			href="/skin/Default/css/style.css" />

		<script src="/js/paging.js"></script>
		<script src="/js/nav.js"></script>
		<script language="javascript" src="/js/script.js"></script>
		<script language="javascript" type="text/javascript"
			src="/tools/datepicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/common/dtree/dltree.js"></script>
	</head>
	<body id="master">
		<!-- <body id="master"> -->
		<form action="/rolegroup_list.do?1=1&permId=${permId}"
			name="searchForm" id="mainForm" method="post">
			<div class="header">
				<div class="hand"></div>
				<div class="local">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<th>
								<ul>
									<li>
										您的位置: 系统管理&nbsp;&gt;&nbsp;
									</li>
									<li>
										角色组管理
									</li>
								</ul>
							</th>
						</tr>
					</table>
				</div>
			</div>
			<div id="mainareacontent">

				<jsp:include page="search.jsp"></jsp:include>
				<div class="mainarea">
					<!------------ 按钮列表 ------------------>
					<fieldset>
						<legend align="left">
							<b:button className="mainbtn" />
							<%--   <div id="mainbtn">
								<ul>
									<li>
										<a href="#" onclick="newRecord('ck');"><span>添加</span> </a>
									</li>
									<li>
										<a href="#" onclick="editRecord('ck');"><span>编辑</span> </a>
									</li>
									<li>
										<a href="#" onclick="deleteSelectedRecord('ck');"><span>删除</span>
										</a>
									</li>
								</ul>
							</div>--%>

						</legend>
						<!----------- 数据列表 ------------------>
						<page:table titles="角色组名,上级组,说明"
							titleMethods="getId,getVcName,getVcParentId,getVcGroupMemo"
							viewName="com.symbol.wp.core.dto.VBaseRoleGroup"
							checkboxName="ck" sorts="vcName,*,vcGroupMemo" widths="*,*,*,*"
							trClassName="dg_alternatingitemstyle"
							tbClassName="dg_borderstyle" trClassName1="dg_itemstyle"
							dataListName="dataList" showCheckBox="Y" />
						<page:pageButton checkboxName="ck" tbClassName="dg_pagestyle"
							tdClassName="pageinfo" tdClassName1="pagebutton" onsubmit="1"
							url="/rolegroup_list.do?1=1&permId=${permId}" />
					</fieldset>

				</div>
				<input name="permId" type="hidden" value="${permId}">
				<input name="id" type="hidden" value="" id="eid">
		</form>
	</body>
</html>

<script language="javascript">

 	function newRecord(id){
         url = window.location.href;
	     document.searchForm.action= "/rolegroup_input.do?1=1&permId=${permId}";
	     searchForm.submit();
    }

    function editRecord(id){
        var countNum = getSelectCount(id);
        if(countNum<1){
            alert("请选择一项");
        }else if(countNum>1){
            alert("只能选择一项");
        }
        else{
            var Id = getSelectValue(id); 
            document.getElementById("eid").value=Id; 
            document.searchForm.action= 'rolegroup_edit.do?1=1&permId=${permId}';
    		searchForm.submit();
        }
    }
    
     function deleteSelectedRecord(id){
        var countNum = getSelectCount(id);
        if(countNum<1){
            alert("请选择项！");
        }else{
            if(confirm("删除该角色组会关联删除该组下的所有角色,确定删除吗？")){
                var arrayId = getSelectValue(id);
                document.searchForm.action= "/rolegroup_delete.do?permId=${permId}&keys=" + arrayId;
                mainForm.submit();
                return true;
            }
        }
    }
    
    
</script>
