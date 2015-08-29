<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib prefix="tree"  uri="/WEB-INF/tag/tree.tld" %>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<div id="query_area">
	<div id="query_upimg"><div class="upleftcircle"></div><div class="upmiddlecircle"></div><div class="uprightcircle"></div></div>
	<div id="query_condition">
		   <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td class="form">
                <table cellpadding="0" cellspacing="0" width="100%">                  
                    <tr>
                        <th>父模块：</th>
                        <td>
                        <tree:ModuleTreeTag title="--系统管理--" name="filter_LIKE_vcParentId"
									selectID="${param['filter_LIKE_vcParentId']}" action="" searchFlag="1"/>
                        </td>
                        <th>菜单名称：</th>
                        <td>
                            <input type="text" name="filter_LIKE_vcMenuName"
                                   value="${param['filter_LIKE_vcMenuName']}" />
                        </td>
                        <th>映射地址：</th>
                        <td>
                            <input type="text" name="filter_LIKE_vcRedirectUrl"
                                   value="${param['filter_LIKE_vcRedirectUrl']}" />
                        </td>                       
                        <td class="button">
							<div id="mainbtn">
								<ul>
									<li>
										<a href="#" onclick="searchForm.submit();" 　title="查询"><span>查询</span>
										</a>
									</li>
									<!--  
									<li>
										<a href="#" onclick="searchForm.reset();" title="重置"><span>重置</span>
										</a>
									</li>
									-->
								</ul>
							</div>
						</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
	<div id="query_downimg"><div class="downleftcircle"></div><div class="downmiddlecircle"></div><div class="downrightcircle"></div></div>
</div>