package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.dto.VFtbDataGetterTask;
import com.symbol.app.mantoeye.entity.FtbDataGetterTask;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.service.DataGetterTaskManager;
import com.symbol.app.mantoeye.service.businessRule.BusinessSiteManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 规则拨测维护
 * 
 * @author rankin
 */
public class BusinessRuleTestAction extends BaseDispatchAction {

	@Autowired
	private DataGetterTaskManager dataGetterTaskManager;

	@Autowired
	private BusinessSiteManager businessSiteManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	private FtbDataGetterTask entity;
	private Long id;
	private String keys;
	private Page<FtbDataGetterTask> page = new Page<FtbDataGetterTask>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private final String OLD_ADD = "old_add";
	private final String NEW_ADD = "new_add";
	private final String OLD_EDIT = "old_edit";
	private final String NEW_EDIT = "new_edit";

	public ICommonManager getCommonManagerImpl() {
		return commonManagerImpl;
	}

	public void setCommonManagerImpl(ICommonManager commonManagerImpl) {
		this.commonManagerImpl = commonManagerImpl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 查询原始数据
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void query() throws ServletException, IOException {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			List<PropertyFilter> filters = HibernateWebUtils
					.buildPropertyFilters(Struts2Utils.getRequest());
			// 任务类型5.已有规则拨测任务(原始信令)
			// 6.新规则拨测任务(原始信令)7.已有规则拨测任务(解码数据)8.新规则拨测任务(解码数据)
			Integer[] taskTypes = new Integer[] { 5, 6, 7, 8 };
			filters.add(new PropertyFilter("intTaskType", taskTypes,
					MatchType.IN));

			String taskStatus_search = request
					.getParameter("taskStatus_search");
			if (!"-1".equals(taskStatus_search) && taskStatus_search != null) {// 状态
				filters.add(new PropertyFilter("intTaskStatus", Integer
						.parseInt(taskStatus_search), MatchType.EQ));
			}

			String msis_search = request.getParameter("msis_search");
			// System.out.println(msis_search+"----------------------------");
			if (msis_search != null && !"".equals(msis_search)) {// 号码
				Long[] taskIds = dataGetterTaskManager
						.getTaskIdsByMsisdn(msis_search);
				// Long[] taskIds = dataGetterTaskManager
				// .getTaskIdsByMsisdn2(msis_search);
				// for (Long id : taskIds) {
				// filters.add(new PropertyFilter("nmDataGetterTaskId", id,
				// MatchType.EQ));
				// }
				filters.add(new PropertyFilter("nmDataGetterTaskId", taskIds,
						MatchType.IN));
			}
			for (PropertyFilter f : filters) {
				if (f.getValue() instanceof String) {
					String s = f.getValue().toString();
					if (s.contains("[")) {
						String[] sa = s.split("\\[");
						String ss = sa[0];
						for (int i = 1; i < sa.length; i++) {
							ss += "[[]" + sa[i];
						}
						f.setValue(ss);
					}
				}

			}

			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = dataGetterTaskManager.find(filters).size();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			SortInfo si = gridServerHandler.getSingleSortInfo();
			if (si != null) {
				String order = si.getSortOrder();
				/**
				 * grid控件排序有3种情况：asc desc 和 第一次加载数据
				 * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
				 */
				if ("defaultsort".equals(order)) {
					page.setOrder("desc");
					page.setOrderBy("dtOrderTime");
				} else {
					page.setOrder(order);
					page.setOrderBy(MantoEyeUtils.getSortField(si
							.getFieldName()));
				}
				// 默认排序方式
			} else {
				page.setOrderBy("dtOrderTime");
				page.setOrder("desc");
			}
			page = dataGetterTaskManager.search(page, filters);
			List<FtbDataGetterTask> list = page.getResult();
			gridServerHandler.setData(
					dataGetterTaskManager.convertBeanToView(list),
					VFtbDataGetterTask.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public String add() throws Exception {

		HttpServletRequest request = Struts2Utils.getRequest();
		String taskType = request.getParameter("taskType");

		if (taskType.equals("5") || taskType.equals("7")) {// 已有规则拨测 ，
			String bid = request.getParameter("bid");
			Integer busiid = Common.StringToInteger(bid);
			if (busiid > 0) {
				DtbBusinessSite dbs = businessSiteManager.get(busiid);
				if (dbs != null) {
					request.setAttribute("busiid", busiid);
					request.setAttribute("businame", dbs.getVcBussinessName());
				}
			}
			return OLD_ADD;
		} else {// 新规则拨测
			return NEW_ADD;
		}
	}

	/**
	 * 添加任务提取
	 * 
	 */
	public String saveTask() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer()
				.getUserInfo();
		String msg = null;
		String opt = "添加";
		try {
			Long taskId = entity.getNmDataGetterTaskId();
			Integer taskType = entity.getIntTaskType();
			String busiid = "";
			String msisdns = request.getParameter("vcMsisdns");// 用户号码
			// 已有业务拨测
			if (taskType == 5) {
				busiid = request.getParameter("businessIds");
			}
			if (taskId != null) {// 编辑
				opt = "编辑";
				boolean succ = dataGetterTaskManager.editTask(entity, busiid,
						msisdns);
				if (succ) {
					request.setAttribute(VarConstants.SUCC_CODE,
							MsgConstants.SUCC_CODE_00102);
				} else {
					request.setAttribute(VarConstants.SUCC_CODE,
							MsgConstants.SUCC_CODE_00202);
				}
			} else {// 新建
				entity.setIntTaskStatus(0);// 新建时的状态
				entity.setDtOrderTime(new Date());// 新建时的定制时间
				dataGetterTaskManager.saveBusiTask(entity, userInfo, busiid,
						msisdns);
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00101);
			}

			msg = opt + "数据提取任务[任务名称：" + entity.getVcTaskName() + "]成功!";
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = opt + "数据提取任务[任务名称：" + entity.getVcTaskName() + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	/**
	 * 编辑任务
	 */
	public String update() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			// dataGetterTaskManager.save(entity);
			// request.setAttribute(VarConstants.SUCC_CODE,
			// MsgConstants.SUCC_CODE_00102);
			// msg = "编辑数据提取任务[任务名称：" + entity.getVcTaskName() + "]成功!";
			// if (Common.judgeString(msg)) {
			// commonManagerImpl.log(request, msg);
			// }
			return SUCCESS;
		} catch (Exception e) {
			// msg = "编辑数据提取任务[任务名称：" + entity.getVcTaskName() + "]失败!";
			// if (Common.judgeString(msg)) {
			// commonManagerImpl.log(request, msg);
			// }
			// logger.error(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 删除任务
	 * 
	 * @throws Exception
	 */
	public void deleteTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskIds = request.getParameter("taskIds");
		String[] sids = taskIds.split(",");
		Long[] ids = new Long[sids.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = Common.StringToLong(sids[i]);
		}
		String msg = "";
		try {// 删除成功
			for (Long id : ids) {
				dataGetterTaskManager.deleteTasks(id);
			}
			msg = "删除任务成功!";
			commonManagerImpl.log(request, "删除数据提取任务[主键：" + taskIds + "]成功!");
		} catch (Exception e) { // 删除失败
			logger.error(e.getMessage());
			msg = "删除任务失败!";
			commonManagerImpl.log(request, "删除数据提取任务[主键：" + taskIds + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public String show() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		entity = dataGetterTaskManager.get(id);

		Integer taskType = entity.getIntTaskType();

		request.setAttribute("taskType", taskType);
		// 用户号码
		request.setAttribute("vcMsisdns", dataGetterTaskManager
				.findMsisdnFilter(entity.getNmDataGetterTaskId()));

		logger.info((taskType == 5) + "=====");
		if (taskType == 5 || taskType == 7) {//
			DtbBusinessSite dbs = dataGetterTaskManager
					.findBusinessFilter(entity.getNmDataGetterTaskId());
			request.setAttribute("busiid", dbs.getNmBussinessId());
			request.setAttribute("businame", dbs.getVcBussinessName());
		}
		return DETAIL;
	}

	public String edit() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		entity = dataGetterTaskManager.get(id);

		Integer taskType = entity.getIntTaskType();

		request.setAttribute("taskType", taskType);
		// 用户号码
		request.setAttribute("vcMsisdns", dataGetterTaskManager
				.findMsisdnFilter(entity.getNmDataGetterTaskId()));

		logger.info((taskType == 5) + "=====");
		if (taskType == 5 || taskType == 7) {//
			DtbBusinessSite dbs = dataGetterTaskManager
					.findBusinessFilter(entity.getNmDataGetterTaskId());
			request.setAttribute("busiid", dbs.getNmBussinessId());
			request.setAttribute("businame", dbs.getVcBussinessName());
			return OLD_EDIT;
		} else {
			return NEW_EDIT;
		}
	}

	public String delete() throws Exception {
		return null;
	}

	public String list() throws Exception {
		return null;
	}

	public DataGetterTaskManager getDataGetterTaskManager() {
		return dataGetterTaskManager;
	}

	public void setDataGetterTaskManager(
			DataGetterTaskManager dataGetterTaskManager) {
		this.dataGetterTaskManager = dataGetterTaskManager;
	}

	public Page<FtbDataGetterTask> getPage() {
		return page;
	}

	public void setPage(Page<FtbDataGetterTask> page) {
		this.page = page;
	}

	public FtbDataGetterTask getEntity() {
		return entity;
	}

	public void setEntity(FtbDataGetterTask entity) {
		this.entity = entity;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String save() throws Exception {
		return null;
	}

	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskName = request.getParameter("entity.vcTaskName");
		try {
			taskName = new String(taskName.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String oldName = request.getParameter("oldName");

		try {
			oldName = java.net.URLDecoder.decode(oldName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		logger.info(taskName + "-----" + oldName);
		if (dataGetterTaskManager.isPropertyUnique("vcTaskName", taskName,
				oldName)) {
			logger.info("---true");
			Struts2Utils.renderText("true");
		} else {
			logger.info("---false");
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 停止任务
	 */
	public void stopTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String msg = "";
		try {
			FtbDataGetterTask entity = dataGetterTaskManager.get(Long
					.parseLong(taskId));
			entity.setIntTaskStatus(4);// 停止中
			dataGetterTaskManager.save(entity);
			msg = "停止任务成功!";
		} catch (Exception e) { // 删除失败
			msg = "停止任务失败!";
		} finally {
			commonManagerImpl.log(request, msg + "[主键：" + taskId + "]!");
		}
		Struts2Utils.renderText(msg);
	}

}
