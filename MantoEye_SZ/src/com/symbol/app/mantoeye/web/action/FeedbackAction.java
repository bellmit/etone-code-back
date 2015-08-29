package com.symbol.app.mantoeye.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.symbol.app.mantoeye.entity.TbFeedback;
import com.symbol.app.mantoeye.service.FeedbackManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class FeedbackAction extends BaseDispatchAction {

	private TbFeedback entity;
	private String id;
	private String keys;
	@Autowired
	private FeedbackManager feedbackManager;

	private String relpayer;
	private String relpayContent;

	public String getRelpayer() {
		return relpayer;
	}

	public void setRelpayer(String relpayer) {
		this.relpayer = relpayer;
	}

	public String getRelpayContent() {
		return relpayContent;
	}

	public void setRelpayContent(String relpayContent) {
		this.relpayContent = relpayContent;
	}

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<TbFeedback> page = new Page<TbFeedback>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<TbFeedback> getPage() {
		return page;
	}

	public void setPage(Page<TbFeedback> page) {
		this.page = page;
	}

	public TbFeedback getEntity() {
		return entity;
	}

	public void setEntity(TbFeedback entity) {
		this.entity = entity;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	/**
	 * 删除反馈
	 */
	public void deleteFeedback() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String[] sids = ids.split(",");
		String msg = "";
		try {// 删除成功
			feedbackManager.deleteByKeys(sids);
			// 删除回复
			for (String i : sids) {
				TbFeedback parentEntity = feedbackManager.get(i);
				List<PropertyFilter> filters = HibernateWebUtils
						.buildPropertyFilters(Struts2Utils.getRequest());
				filters.add(new PropertyFilter("vcParentId", parentEntity
						.getId(), MatchType.EQ));
				List<TbFeedback> viewList = feedbackManager.find(filters);
				for (TbFeedback tf : viewList) {
					feedbackManager.delete(tf.getId());
				}
			}

			msg = "删除成功!";
			commonManagerImpl.log(request, "删除反馈[主键：" + ids + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除失败!";
			commonManagerImpl.log(request, "删除反馈[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public String delete() throws Exception {
		String msg = null;
		String[] sids = keys.split(",");
		try {// 删除成功
			feedbackManager.deleteByKeys(sids);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00008);
			request.setAttribute(VarConstants.URL, "/feedback_list.do?1=1");
			msg = "删除反馈信息信息[反馈信息Id：" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;
		} catch (Exception e) { // 删除失败
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00005);
			addActionMessage("删除反馈信息信息" + keys + "失败!");
			msg = "删除反馈信息信息[反馈信息Id：" + keys + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	public String list() throws Exception {
		setPaginationdataList();
		return INDEX;
	}

	public void setPaginationdataList() throws ServiceStartupException {
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(request);
		if (page.getOrderBy() == null) {
			page.setOrderBy("id");
		}
		page = feedbackManager.search(page, filters);
		List<TbFeedback> dataList = page.getVresult();
		List list = new ArrayList();
		request.setAttribute("dataList", dataList);
	}

	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<TbFeedback> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		filters.add(new PropertyFilter("vcParentId", null, MatchType.EQ));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		page.setOrderBy("id");
		page.setOrder("desc");
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = feedbackManager.search(page, filters);
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
	 * 页面视图数据
	 */
	private List formatViewData(List<TbFeedback> list) {
		List maplist = new ArrayList();
		Map map;
		TbFeedback bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("id", bean.getId());
				map.put("vcTitle", CommonUtils.killNull(bean.getVcTitle()));
				map.put("vcCreater", CommonUtils.killNull(bean.getVcCreater()));// 创建人
				map.put("clContent", CommonUtils.killNull(bean.getClContent()));// 内容
				map.put("dtCreateDate", CommonUtils.formatFullDate(bean
						.getDtCreateDate()));// 创建时间
				map.put("dtLastUpdate", CommonUtils.formatFullDate(bean
						.getDtLastUpdate()));// 最后回复时间
				map.put("intReplys", CommonUtils.killNull(bean.getIntReplys()));// 回复量
				maplist.add(map);
			}
		}
		return maplist;
	}

	public String replyFeedback() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		entity.setDtCreateDate(new Date());
		entity.setIntReplys(0L);
		try {
			entity
					.setClContent(entity.getClContent().replace("\n",
							"\\" + "n"));
			feedbackManager.save(entity);

			feedbackManager.save(entity);
			// 更新父问题反馈
			TbFeedback parentEntity = feedbackManager.get(entity
					.getVcParentId());
			parentEntity.setDtLastUpdate(entity.getDtCreateDate());
			parentEntity.setIntReplys(parentEntity.getIntReplys() + 1);// 回复量增1
			feedbackManager.save(parentEntity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00105);
			msg = "回复反馈信息信息[反馈信息名：" + entity.getVcTitle() + "]成功!";
			request.setAttribute(VarConstants.URL, "/feedback_list.do?1=1");
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "回复反馈信息信息[反馈信息名：" + entity.getVcTitle() + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	public String save() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		entity.setDtCreateDate(new Date());
		entity.setIntReplys(0L);
		try {
			entity
					.setClContent(entity.getClContent().replace("\n",
							"\\" + "n"));
			feedbackManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加反馈信息信息[反馈信息名：" + entity.getVcTitle() + "]成功!";
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存反馈信息信息[反馈信息名：" + entity.getVcTitle() + "]失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}

	public String update() throws Exception {
		return null;

	}

	public TbFeedback getModel() {

		return entity;
	}

	public String permis() throws Exception {
		String id = request.getParameter("id");
		TbFeedback tbRoles = feedbackManager.get(id);
		request.setAttribute("tbRoles", tbRoles);
		return PERMIS;
	}

	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcRolesName = request.getParameter("entity.vcRolesName");
		try {
			vcRolesName = new String(vcRolesName.getBytes("ISO-8859-1"),
					"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String vcOldRolesName = request.getParameter("vcOldRolesName");

		try {
			vcOldRolesName = java.net.URLDecoder
					.decode(vcOldRolesName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}

		if (feedbackManager.isPropertyUnique("vcRolesName", vcRolesName,
				vcOldRolesName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	public String edit() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		TbFeedback parentEntity = feedbackManager.get(id);
		request.setAttribute("parentEntity", parentEntity);
		return EDIT;
	}

	public String view() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		TbFeedback parentEntity = feedbackManager.get(id);
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		filters.add(new PropertyFilter("vcParentId", parentEntity.getId(),
				MatchType.EQ));
		List<TbFeedback> viewList = feedbackManager.find(filters);
		request.setAttribute("parentEntity", parentEntity);
		request.setAttribute("viewList", viewList);
		return "view";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
