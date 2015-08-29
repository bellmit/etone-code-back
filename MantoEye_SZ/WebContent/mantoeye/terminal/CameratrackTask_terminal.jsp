<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>终端管理</title>
		<%@ include file="/include/session.jsp"%>
		<%@ include file="/include/setcache.jsp"%>
		<script src="/js/jquery/etone.js" type="text/javascript"></script>
		<link href="/css/style.css" rel="stylesheet" type="text/css" />
	    <script language="javascript">
		   function load(){
		   		var taskId = window.parent.document.getElementById("taskId").value;
		   		$('#taskId').attr('value',taskId);
		   }
		</script>
		<script language="javascript" src="/css/com/script.js"></script>
	</head>
	<body onload='<s:if test="page.totalCount==-1">etone.jumpPage(1)</s:if><s:if test="page.totalCount!=-1">load()</s:if>' style="margin-top:-4px;">
		<s:form id="mainForm" name="mainForm" action="/cameratrack_queryTerminal.do">
			<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}" />
			<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}" />
			<input type="hidden" name="page.order" id="order" value="${page.order}" />	
			<input type="hidden" name="taskId" id="taskId"/>		
			<!-- 查询：开始 -->
			<table width="717"  border="0"   frame="border" rules="rows" style="border-collapse:collapse;">
				<tr>				
				</tr>
				<tr>
					<td align="left" >
						&nbsp;
						&nbsp;
						终端类型：&nbsp;<input id="vcTerminalName" name="vcTerminalName" type="text" value="${terminalType}" />	
						
							
						&nbsp;

	
							
							终端品牌：
						&nbsp;
						<input id="vcTerminalBrand" name="vcTerminalBrand" type="text" value="${terminalName}" />&nbsp;
						<input type="image" src="/css/images/btnquery.png"
							class="img" style="width:54;height:18;" />
					</td>

				</tr>
				<tr>
					<td height="328" colspan="3" >
						<table width="710">
							<tr>
								<td width="44%" height="205" align="left" valign="top"
									class="dg_alternatingitemstyle">
									<select name="unUnion" multiple="multiple" id="unUnion" ondblclick="addDblclick()"
										size="15" style="width: 90%;">


										<s:iterator value="page.result">

											<option value="${vcTerminalName}_${vcTerminalBrand}_${nmTerminalId}">${vcTerminalName}-${vcTerminalBrand}</option>
										
										</s:iterator>
									</select>
								</td>
								<td width="12%" align="center" class="dg_alternatingitemstyle">
									<input id="alladd" type="image" src="/css/images/many_to_right.png" onclick="return addall()"/>
                                    <br />                                      
									<input id="add" type="image" src="/css/images/one_to_right.png" onclick="return add();"/>
                                    <br />                                      
									<input id="del" type="image" src="/css/images/one_to_left.png"   onclick="return remove()"/>
                                    <br />                                      
									<input id="alldel" type="image" src="/css/images/many_to_left.png"  onclick="return removeall()"/>
								</td>
								<td width="44%" class="dg_alternatingitemstyle">
								     
									<select name="union" multiple="multiple" id="union" ondblclick="removeDblclick()"
													size="15" style="width: 90%;">	
									<s:iterator value="ftbTerminalList">

											<option value="${vcTerminalName}_${vcTerminalBrand}_${nmTerminalId}">${vcTerminalName}-${vcTerminalBrand}</option>
										
										</s:iterator>														
														
								</select>
								</td>
							</tr>
							<tr>
								<td height="50px" align="center" valign="top"
									class="dg_alternatingitemstyle">
									<table class="pagintionBar" border="0" align="right"
										cellpadding="0" cellspacing="0">
                                        <tr>
                                        	<td colspan="6" align="center">
                                            
										共有 ${page.totalCount} 条记录，当前第
									${page.pageNo}/${page.totalPages} 页
                                            </td>
                                        </tr>
										<tr>
											<td height="50">
												<img src="/css/images/first.gif" width="37"
													height="15" onclick="javascript:etone.jumpPage(1);" />
											</td>
											<td>
												<img src="/css/images/back.gif" width="43" height="15"
													<s:if test="page.hasPre">onclick="javascript:etone.jumpPage(${page.prePage});"</s:if> />
											</td>
											<td>
												<img src="/css/images/next.gif" width="43" height="15"
													<s:if test="page.hasNext">onclick="javascript:etone.jumpPage(${page.nextPage});"</s:if> />
											</td>
											<td>
												<img src="/css/images/last.gif" width="37" height="15"
													onclick="javascript:etone.jumpPage(${page.totalPages});" />
											</td>
											<td width="100">
												<div align="center">
													<span class="font12">转到第 <input id="gotoPage"
															name="textfield" type="text" value="${page.pageNo}"
															size="4"
															style="height: 12px; width: 20px; border: 1px solid #999999;" />
														页 </span>
												</div>
											</td>
											<td width="40">
												<img src="/css/images/go.gif" width="37" height="15"
													onclick="javascript:etone.jumpPage($('#gotoPage').val());" />
											</td>
										</tr>
									</table>
								</td>
								<td align="center" >&nbsp;
									
								</td>
								<td align="center">
								    
							  	    <s:if test="#request.ftbTerminalList.size()>0">
									共有<s:property value="#request.ftbTerminalList.size()"/>条记录.
									</s:if>
									<s:else>
										 共有0条记录.
									</s:else>
							   </td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>				
				</tr>
			</table>

		</s:form>

	</body>
</html>


<script type="text/javascript">

	function findChecked(){
		var unUnion=document.getElementById("unUnion");
		var union=document.getElementById("union");
		var vcTerminal=[]
		var cnt = 0;
		for(var i=0;i<unUnion.options.length;i++){
			if(unUnion.options[i].selected){
				var Terminal = unUnion.options[i].value;
				vcTerminal.push(Terminal);
				cnt=cnt+1;
			}
		}	
	}
	
	function addDblclick(){
		var unUnion=document.getElementById("unUnion");
		var vcTerminal=[]
		for(var i=0;i<unUnion.options.length;i++){
			if(unUnion.options[i].selected){
				var Terminal = unUnion.options[i].value;
				vcTerminal.push(Terminal);
			}
		}		
		document.forms[0].action="/cameratrack_addTerminal.do?vcTerminal="+vcTerminal;
		document.forms[0].submit();
	}
	
	function add() {
		var unUnion=document.getElementById("unUnion");
		var vcTerminal=[]
		var cnt = 0;
		for(var i=0;i<unUnion.options.length;i++){
			if(unUnion.options[i].selected){
				var Terminal = unUnion.options[i].value;
				vcTerminal.push(Terminal);
				cnt=cnt+1;
			}
		}
		if(cnt<=0){
			alert("请选择要添加区域.");
			return false;
		}
		document.forms[0].action="/cameratrack_addTerminal.do?vcTerminal="+vcTerminal;
		return true;
	}
			
	function remove() {
		var union=document.getElementById("union");
		var vcTerminal=[]
		var cnt = 0;
		for(var i=0;i<union.options.length;i++){
			if(union.options[i].selected){
				var Terminal = union.options[i].value;
				vcTerminal.push(Terminal);
				cnt=cnt+1;
			}
		}
		if(cnt<=0){
			alert("请选择要移出的区域.");
			return false;
		}
		document.forms[0].action="/cameratrack_removeTerminal.do?vcTerminal="+vcTerminal;
		return true;
	}
		
	function removeDblclick() {
		var union=document.getElementById("union");
		var vcTerminal=[]
		var cnt = 0;
		for(var i=0;i<union.options.length;i++){
			if(union.options[i].selected){
				var Terminal = union.options[i].value;
				vcTerminal.push(Terminal);
			}
		}
		document.forms[0].action="/cameratrack_removeTerminal.do?vcTerminal="+vcTerminal;
		document.forms[0].submit();
	}	
			
			
			function addall(){
		   		var unUnion=document.getElementById("unUnion");
				var union=document.getElementById("union");
				var vcTerminal=[]
				for(var i=0;i<unUnion.options.length;i++){
					var Terminal = unUnion.options[i].value;
					vcTerminal.push(Terminal);
				}
		   		document.forms[0].action="/cameratrack_addTerminal.do?vcTerminal="+vcTerminal;
				return true;
		   }
		   
		     function removeall(){
		   		document.forms[0].action="/cameratrack_removeAllTerminal.do";
				return true;
		   }
		   
</script>
