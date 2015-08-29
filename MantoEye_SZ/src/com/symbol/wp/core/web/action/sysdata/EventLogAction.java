package com.symbol.wp.core.web.action.sysdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseEventLog;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.service.impl.BaseEventLogManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class EventLogAction extends BaseDispatchAction {

	private TbBaseEventLog entity;
	private String id;
	private String keys;

	@Autowired
	private BaseEventLogManager baseEventLogManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<TbBaseEventLog> page = new Page<TbBaseEventLog>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<TbBaseEventLog> getPage() {
		return page;
	}

	public void setPage(Page<TbBaseEventLog> page) {
		this.page = page;
	}

	public TbBaseEventLog getEntity() {
		return entity;
	}

	public void setEntity(TbBaseEventLog entity) {
		this.entity = entity;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
	public void queryForJq() throws ServletException, IOException {		
		logger.info("-----------------in-->");		
		String mypage = request.getParameter("page"); // 取得当前页数,注意这是jqgrid自身的参数   
        String myrows = request.getParameter("rows"); // 取得每页显示行数，,注意这是jqgrid自身的参数   
        int totalRowNum = page.getTotalCount();
		List<TbBaseEventLog> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		list = baseEventLogManager.find(filters);
						
		if (totalRowNum < 1) {
			totalRowNum =list.size();
			page.setTotalCount(totalRowNum);
		}
		page.setOrderBy("id");
		page.setOrder("desc");
		page.setPageSize(Common.StringToInt(myrows));
		page.setPageNo(Common.StringToInt(mypage));
		
		page = baseEventLogManager.search(page, filters);
		list = page.getResult();
		JSONObject json = new JSONObject();
		json.put("total",page.getTotalPages());
		json.put("page",mypage);
		json.put("records",totalRowNum);
		json.put("rows",JSONUtils.BeanList2JSONArray(list, TbBaseEventLog.class));	
		logger.info(json.toString());	
		logger.info("-----------------before out-->");
		Struts2Utils.renderJson(json.toString());
	}
	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<TbBaseEventLog> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		page.setOrderBy("id");
		page.setOrder("desc");
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = baseEventLogManager.search(page, filters);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<TbBaseEventLog> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
		.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		list = baseEventLogManager.find(filters);
		 String exportmsg = "导出数据：系统事件日志";
			commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}
	/**
	 * 页面视图数据
	 */
	private List formatViewData(List<TbBaseEventLog> list) {
		List maplist = new ArrayList();
		Map map;
		TbBaseEventLog bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("id", bean.getId());
				map.put("dtRecordTime", CommonUtils.killNull(bean
						.getDtRecordTime()));
				map.put("vcRecorder", CommonUtils
						.killNull(bean.getVcRecorder()));
				map.put("vcEventContent", CommonUtils.killNull(bean
						.getVcEventContent()));
				map.put("vcEventAffect", CommonUtils.killNull(bean
						.getVcEventAffect()));
				map.put("vcEventType", CommonUtils.killNull(bean
						.getVcEventType()));
				maplist.add(map);
			}
		}
		return maplist;
	}

	public void delete() throws Exception {
		String msg = null;
		String[] sids = keys.split(",");
		try {// 删除成功
			baseEventLogManager.deleteByKeys(sids);
			msg = "删除事件日志信息成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
		} catch (Exception e) { // 删除失败
			msg = "删除事件日志信息失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
		}
		Struts2Utils.renderText(msg);
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	public String list() throws Exception {
		return INDEX;
	}

	public String save() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			LoginListener loginListener = (LoginListener) request.getSession()
					.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
			String loginName = loginListener.getSessionContainer()
					.getUserInfo().getVcUserName();

			entity.setVcRecorder(loginName);
			entity.setDtRecordTime(new Date());
			baseEventLogManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00001);
			msg = "添加事件信息成功!";

			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加事件信息失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}
	
	public String update() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			baseEventLogManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			msg = "编辑事件信息成功!";
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "编辑事件信息失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}

	}

	public TbBaseEventLog getModel() {

		return entity;
	}

	public String edit() throws Exception {
		entity = baseEventLogManager.get(id);
		return EDIT;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
