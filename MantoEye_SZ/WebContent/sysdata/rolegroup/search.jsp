<%@ page contentType="text/html;charset=GBK" language="java"%>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<div id="query_area">
	<div id="query_upimg">
		<div class="upleftcircle"></div>
		<div class="upmiddlecircle"></div>
		<div class="uprightcircle"></div>
	</div>
	<div id="query_condition">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="ico">
				<td class="form">
					<table cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<th>
								��ɫ������
							</th>
							<td>
								<input type="text" name="filter_LIKE_vcName"
									value="${param['filter_LIKE_vcName']}" />
							</td>
							<th>
								�ϼ��飺
							</th>
							<td>
								<input type="text" name="filter_LIKE_vcParentId"
									value="${param['filter_LIKE_vcParentId']}" />
							</td>
							<th>
							</th>
							<td class="button">
								<div id="mainbtn">
									<ul>
										<li>
											<a href="#" onclick="searchForm.submit();" ��title="��ѯ"><span>��ѯ</span>
											</a>
										</li>
										<!--  
										<li>
											<a href="#" onclick="searchForm.reset();" title="����"><span>����</span>
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
	<div id="query_downimg">
		<div class="downleftcircle"></div>
		<div class="downmiddlecircle"></div>
		<div class="downrightcircle"></div>
	</div>
</div>
