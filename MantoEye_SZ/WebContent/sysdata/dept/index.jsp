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
		<form action="/dept_list.do?1=1&permId=${permId}" name="searchForm"
			id="mainForm" method="post">
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
										部门管理
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
						</legend>
						<!----------- 数据列表 ------------------>
						<page:table titles="部门名称,上级部门,部门描述"
							titleMethods="getId,getVcDeptName,getNmParentDeptName,getVcDeptMemo"
							viewName="com.symbol.wp.core.dto.VBaseDepartment"
							sorts="vcDeptName,*,*"
							checkboxName="ck" widths="*,*,*,*"
							trClassName="dg_alternatingitemstyle"
							tbClassName="dg_borderstyle" trClassName1="dg_itemstyle"
							dataListName="dataList" showCheckBox="Y" />
						<page:pageButton checkboxName="ck" tbClassName="dg_pagestyle"
							tdClassName="pageinfo" tdClassName1="pagebutton" onsubmit="1"
							url="/dept_list.do?1=1&permId=${permId}" />

					</fieldset>
				</div>
			</div>
			<input name="permId" type="hidden" value="${permId}">
			<input name="id" type="hidden" value="" id="eid">
		</form>
	</body>
</html>

<script language="javascript">
	//新建部门
    function newRecord(id){
    	 url = window.location.href;
	     document.searchForm.action= "/dept_input.do?1=1&permId=${permId}";
	     searchForm.submit();
    }
	//删除部门
    function deleteSelectedRecord(id){
        var countNum = getSelectCount(id);
        if(countNum<1){
            alert("请选择需要删除的部门！");
        }else{
            if(confirm("你确实要删除所选择的部门吗？")){
                var arrayId = getSelectValue(id);
                document.searchForm.action= "/dept_delete.do?keys=" + arrayId;
                searchForm.submit();
                return true;
            }
        }
    }
    //编辑部门
    function editRecord(id){
        var countNum = getSelectCount(id);//判断选择的数目
        if(countNum<1){
            alert("请选择一项");
        }else if(countNum>1){
            alert("只能选择一项");
        }
        else{
            //var Id = getSelectValue(id); //获取选择的主键
            //openWindow('/dept_openEditInfo.do?1=1&permId=${permId}&id='+Id,'frmmain')
             var Id = getSelectValue(id); 
            document.getElementById("eid").value=Id; 
            document.searchForm.action= 'dept_edit.do?1=1&permId=${permId}';
    		searchForm.submit();
        }
    }
</script>
