package com.symbol.app.mantoeye.web.action.dataCatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.bean.DataFilterBean;
import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.VFtbDataGetterTask;
import com.symbol.app.mantoeye.dto.common.CommonDimension;
import com.symbol.app.mantoeye.entity.FtbDataGetterTask;
import com.symbol.app.mantoeye.entity.FtbFilterColumnMapTask;
import com.symbol.app.mantoeye.entity.FtbTableColumnMap;
import com.symbol.app.mantoeye.service.CommonDimensionManager;
import com.symbol.app.mantoeye.service.DataGetterTaskManager;
import com.symbol.app.mantoeye.util.CommonUtils;
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

public class DataGetterTaskAction extends BaseDispatchAction{
	private static final long serialVersionUID = -7992128821878284128L;
	private static final String DETAIL_USER_NUMBER="detailUserNumber";
	@Autowired
	private DataGetterTaskManager dataGetterTaskManager;
	@Autowired
	private CommonDimensionManager commonDimensionManager;
	@Resource(name="commonManagerImpl")
	private ICommonManager commonManagerImpl;

	private FtbDataGetterTask entity;
	private String keys;
	private List<CommonDimension> businessList;
	private Map<Integer, Map<Long, CommonDimension>> areaMap;
	private Page<FtbDataGetterTask> page = new Page<FtbDataGetterTask>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	/**
	 * 原始表对应的所有字段
	 */
	private Map<String, Integer> tableColumnMap = null;
	/**
	 * 解析数据需要提取字段 1:号码 2:APN ......
	 */
	private Map<Integer, String> outColumnMap = null;

	/**
	 * 用户号码提取区域字段
	 */
	private Map<Integer, String> userNumberAreaColumnMap = null;

	/**
	 * 用户号码提取业务字段
	 */
	private Map<Integer, String> userNumberBusinessColumnMap = null;

	/**
	 * 查询原始数据
	 * 
	 */
	public void query() throws ServletException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		String userName = (String) request.getSession().getAttribute(
				"baseUserName");
		String file_Down_Flag = (String) request.getSession().getAttribute(
				"file_Down_Flag");
		// nmTaskOrder
		// 任务类型1.历史原始信令提取2.实时原始信令提取3.历史解析信令提取4.用户号码提取
		String taskType = request.getParameter("taskType");
		//logger.info(">>>>>>>$$$$$$$$$$>>>>>>taskType:"+taskType);
		filters.add(new PropertyFilter("intTaskType", Integer// 任务类型
				.parseInt(taskType), MatchType.EQ));

		String taskStatus_search = request.getParameter("taskStatus_search");
		if (!"-1".equals(taskStatus_search) && taskStatus_search != null) {// 状态
			filters.add(new PropertyFilter("intTaskStatus", Integer
					.parseInt(taskStatus_search), MatchType.EQ));
		}

		if (!"DATA_CATCH_USER_PERM".equals(file_Down_Flag)) {
			filters.add(new PropertyFilter("nmTaskOrder", userName,
					MatchType.EQ));
		}
		String msis_search = request.getParameter("msis_search");
		if (msis_search != null && !"".equals(msis_search)) {// 号码
			if (taskType.equals("3")) {//3.历史解析信令提取ftbFilterColumnMapTask
				logger.info(">>>>>>>>>>>>>msis_search:"+msis_search);
				init();
				int columnId = tableColumnMap.get(outColumnMap.get(1));// 获取字段关联表ID
				logger.info(">>>>>>>>>>>>>columnId:"+columnId);
				Long[] taskIds = dataGetterTaskManager
						.getTaskIdsByMsisdn1(msis_search,columnId);
				filters.add(new PropertyFilter("nmDataGetterTaskId", taskIds,
						MatchType.IN));
			} else {
				Long[] taskIds = dataGetterTaskManager
						.getTaskIdsByMsisdn(msis_search);
				filters.add(new PropertyFilter("nmDataGetterTaskId", taskIds,
						MatchType.IN));
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
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("dtOrderTime");
			page.setOrder("desc");
		}

		page = dataGetterTaskManager.search(page, filters);
		List<FtbDataGetterTask> list = page.getResult();
		List<VFtbDataGetterTask> viewList = dataGetterTaskManager
				.convertBeanToView(list);
		if ("4".equals(taskType)) {// 如果是用户号码提取
			viewList = this.packUserNumberTask(viewList);
		}
		gridServerHandler.setData(viewList, VFtbDataGetterTask.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 保存原始记录任务
	 * 
	 */
	public String saveTask()throws ServletException, IOException{
		String msg = null;
		try {
		logger.info("************************saveTask...****");
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer()
				.getUserInfo();
		
		String vcMsisdns = request.getParameter("vcMsisdns");// 用户号码
		
			Long taskId = entity.getNmDataGetterTaskId();
			logger.info("-taskId-:" + taskId);
			if (taskId != null) {// 编辑
				dataGetterTaskManager.editTask(entity, "", vcMsisdns);
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00102);
			} else {// 新建
				entity.setIntTaskStatus(0);// 新建时的状态
				entity.setDtOrderTime(new Date());// 新建时的定制时间
				entity.setIntTaskType(Common.StringToInt(request
						.getParameter("taskType")));
				dataGetterTaskManager.saveTask(entity, userInfo, "", vcMsisdns);
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00101);
			}
			msg = "保存数据提取任务[名称：" + entity.getVcTaskName() + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			Struts2Utils.getRequest().setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存数据提取任务[名称：" + entity.getVcTaskName() + "]失败!";
			commonManagerImpl.log(Struts2Utils.getRequest(), msg);
			return ERROR;
		}
	}

	/**
	 * 保存历史解析任务
	 */
	public String saveHistoryParseTask()throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer()
				.getUserInfo();
		String msg = null;
		String vcMsisdns = request.getParameter("vcMsisdns");
		// 过滤条件（除开时间），全部用等于匹配
		List<DataFilterBean> filters = new ArrayList<DataFilterBean>();
		DataFilterBean filter;

		try {
			// 初始化相关信息
			init();

			// 设置过滤条件(即号码)
			if (vcMsisdns != null && !"".equals(vcMsisdns)) {
				String[] msisdns = vcMsisdns.split(",");
				for (String m : msisdns) {
					filter = new DataFilterBean();
					if (tableColumnMap.containsKey(outColumnMap.get(1))) {
						int columnId = tableColumnMap.get(outColumnMap.get(1));// 获取字段关联表ID
						String value = "86" + m;
						filter.setColumnId(columnId);
						filter.setValue(value);
						filter.setCondition("=");
						filters.add(filter);
					}
				}
			}
			// 保存解析数据开始时间
			filters.add(getStartColumnCondition(entity));

			// 保存解析数据结束时间
			filters.add(getEndColumnCondition(entity));

			Long taskId = entity.getNmDataGetterTaskId();
			if (taskId != null) {// 编辑
				boolean succ = dataGetterTaskManager.editParseTask(entity,
						userInfo, filters);
				if (succ) {
					request.setAttribute(VarConstants.SUCC_CODE,
							MsgConstants.SUCC_CODE_00102);
				} else {
					request.setAttribute(VarConstants.SUCC_CODE,
							MsgConstants.SUCC_CODE_00202);
				}
			} else {// 新建
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00101);
				entity.setIntTaskStatus(0);// 新建时的状态
				entity.setDtOrderTime(new Date());// 新建时的定制时间
				entity.setIntTaskType(Common.StringToInt(request
						.getParameter("taskType")));

				// 保存需要显示的值(对应表ftbOutColumnMapTask)
				String column = null;
				List<Integer> outColumns = new ArrayList<Integer>();
				for (int key : outColumnMap.keySet()) {
					column = outColumnMap.get(key);
					if (tableColumnMap.containsKey(column)) {
						outColumns.add(tableColumnMap.get(column));
					}
				}
				dataGetterTaskManager.saveParseTask(entity, userInfo,
						outColumns, filters);
			}

			msg = "保存数据提取任务[名称：" + entity.getVcTaskName()+ "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());

			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存数据提取任务[名称：" + entity.getVcTaskName() + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		} 
	}

	/**
	 * 保存任务提取时间条件: >(会话流程开始时间) 或 =(其它)
	 */
	private DataFilterBean getStartColumnCondition(FtbDataGetterTask task) {
		DataFilterBean bean = new DataFilterBean();
		if (tableColumnMap.containsKey("dtBeginTime")) {// 会话流程开始时间
			int columnId = tableColumnMap.get("dtBeginTime");// 获取字段关联表ID
			bean.setColumnId(columnId);
			bean.setCondition(">");
			bean.setValue(CommonUtils.formatFullDate(task.getDtStartTime()));
		}
		return bean;
	}

	/**
	 * 保存任务提取时间条件: <(会话流程结束时间) 或 =(其它)
	 */
	private DataFilterBean getEndColumnCondition(FtbDataGetterTask task) {
		DataFilterBean bean = new DataFilterBean();
		if (tableColumnMap.containsKey("dtEndTime")) {// 会话流程结束时间
			int columnId = tableColumnMap.get("dtEndTime");
			bean.setColumnId(columnId);
			bean.setCondition("<");
			bean.setValue(CommonUtils.formatFullDate(task.getDtEndTime()));
		}
		return bean;
	}

	/**
	 * 保存用户号码提取任务
	 */
	public String saveUserNumberTask() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer()
				.getUserInfo();
		String msg = null;
		// 过滤条件（除开时间），全部用等于匹配
		List<DataFilterBean> filters = new ArrayList<DataFilterBean>();
		DataFilterBean filter;
		try {
			// 初始化相关信息
			init();
			String[] id = null;
			String i = null;
			String v = null;
			int key = 0;
			// 设置区域过滤值
			String areaIds = request.getParameter("areaIds");
			
			if (areaIds != null && !"".equals(areaIds)) {
				String[] datas = areaIds.split(",");
				for (String ids : datas) {
					id = ids.split(":");
					if (id.length < 2)
						continue;
					filter = new DataFilterBean();
					i = id[0].trim();
					v = id[1].trim();
					key = Common.StringToInt(i);

					//logger.info("*****userNumberAreaColumnMap.containsKey(key):"+userNumberAreaColumnMap.containsKey(key));
					// logger.info("*****tableColumnMap.containsKey(userNukey)));mberAreaColumnMap.get(key)):"
					// +tableColumnMap.containsKey(userNumberAreaColumnMap.get(
					if (userNumberAreaColumnMap.containsKey(key)) {// 判断是否匹配ID
						List listsds = new ArrayList(tableColumnMap.keySet());
						if (tableColumnMap.containsKey(userNumberAreaColumnMap
								.get(key))) {// 判断是否含有此字段
							int columnId = tableColumnMap
									.get(userNumberAreaColumnMap.get(key));// 获取字段
							// 增加过滤条件关联
							filter.setColumnId(columnId);
							filter.setCondition("=");
							filter.setValue(v);

							logger.info(columnId + "-VVVV-" + v);

							filters.add(filter);
						}
					}
				}
			}

			// 保存业务过滤值
			String businessIds = request.getParameter("businessIds");
			// logger.info("*****businessIds:"+businessIds);
			if (businessIds != null && !"".equals(businessIds)) {
				String[] datas = businessIds.split(",");
				for (String ids : datas) {
					id = ids.split(":");
					v = id[1].trim();
					filter = new DataFilterBean();
					if (tableColumnMap.containsKey(userNumberBusinessColumnMap
							.get(1))) {// 判断是否含有业务字段
						int columnId = tableColumnMap
								.get(userNumberBusinessColumnMap.get(1));// 获取业务字段
						// 增加过滤条件关联
						filter.setColumnId(columnId);
						filter.setCondition("=");
						filter.setValue(v);
						filters.add(filter);
					}
				}
			}
			// 保存解析数据开始时间
			filters.add(getStartColumnCondition(entity));

			// 保存解析数据结束时间
			filters.add(getEndColumnCondition(entity));

			Long taskId = entity.getNmDataGetterTaskId();
			if (taskId != null) {// 编辑
				boolean succ = dataGetterTaskManager.editParseTask(entity,
						userInfo, filters);
				if (succ) {
					request.setAttribute(VarConstants.SUCC_CODE,
							MsgConstants.SUCC_CODE_00102);
				} else {
					request.setAttribute(VarConstants.SUCC_CODE,
							MsgConstants.SUCC_CODE_00202);
				}
			} else {// 新建
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00101);
				entity.setIntTaskStatus(0);// 新建时的状态
				entity.setDtOrderTime(new Date());// 新建时的定制时间
				entity.setIntTaskType(Common.StringToInt(request
						.getParameter("taskType")));
				// 保存需要显示的值(用户号码提取保存显示的只是号码)
				String column = outColumnMap.get(1);// 号码标识
				String imei = outColumnMap.get(42);// IMEI
				List<Integer> outColumns = new ArrayList<Integer>();
				if (tableColumnMap.containsKey(column)) {
					outColumns.add(tableColumnMap.get(column));
				}
				if (tableColumnMap.containsKey(imei)) {
					outColumns.add(tableColumnMap.get(imei));
				}
				logger.info("*****filters:" + filters.size());
				dataGetterTaskManager.saveParseTask(entity, userInfo,
						outColumns, filters);
			}

			msg = "保存数据提取任务[名称：" + entity.getVcTaskName() + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存数据提取任务[名称：" + entity.getVcTaskName() + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}
	public String add() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskType = request.getParameter("taskType");
		if ("4".equals(taskType)) {// 用户号码提取
			return "addUserNumber";
		} else {
			return ADD;
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
		try {

			for (Long id : ids) {
				dataGetterTaskManager.deleteTasks(id);
			}
			msg = "删除任务成功!";
			commonManagerImpl.log(request, "删除数据提取任务[ID：" + taskIds + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除任务失败!";
			logger.error(e.toString());
			logger.error(e.getMessage());
			commonManagerImpl.log(request, "删除数据提取任务[ID：" + taskIds + "]失败!");
		}
		Struts2Utils.renderText(msg);
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
			msg = "停止任务[：" + taskId + "]"+"成功!";
			commonManagerImpl.log(request, msg );
		} catch (Exception e) { // 删除失败
			msg = "停止任务[：" + taskId + "]"+"失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}

	/**
	 * 编辑任务
	 */
	public String edit() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String keyid = request.getParameter("keyid");
		entity = dataGetterTaskManager.get(Common.StringToLong(keyid));
		String taskType = request.getParameter("taskType");
		// 用户号码
		request.setAttribute("vcMsisdns", dataGetterTaskManager.formatMsisdn(
				entity.getNmDataGetterTaskId(), Common.StringToInt(taskType)));
		if ("4".equals(taskType)) {// 用户号码提取
			// 初始化相关信息
			init();
			List<FtbFilterColumnMapTask> columnFilterList = dataGetterTaskManager
					.getColumnMap(entity.getNmDataGetterTaskId());
			String[] businessInfo = packBusinessInfo(columnFilterList);
			String[] areaInfo = packAreaInfo(columnFilterList);
			request.setAttribute("business", businessInfo[0]);
			request.setAttribute("businessIds", businessInfo[1]);
			request.setAttribute("area", areaInfo[0]);
			request.setAttribute("areaIds", areaInfo[1]);
			return "editUserNumber";
		} else {
			return EDIT;
		}
	}
	/**
	 * 编辑任务
	 */
	public String show() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String keyid = request.getParameter("keyid");
		entity = dataGetterTaskManager.get(Common.StringToLong(keyid));
		String taskType = request.getParameter("taskType");
		// 用户号码
		request.setAttribute("vcMsisdns", dataGetterTaskManager.formatMsisdn(
				entity.getNmDataGetterTaskId(), Common.StringToInt(taskType)));
		if ("4".equals(taskType)) {// 用户号码提取
			// 初始化相关信息
			init();
			List<FtbFilterColumnMapTask> columnFilterList = dataGetterTaskManager
					.getColumnMap(entity.getNmDataGetterTaskId());
			String[] businessInfo = packBusinessInfo(columnFilterList);
			String[] areaInfo = packAreaInfo(columnFilterList);
			request.setAttribute("business", businessInfo[0]);
			request.setAttribute("businessIds", businessInfo[1]);
			request.setAttribute("area", areaInfo[0]);
			request.setAttribute("areaIds", areaInfo[1]);
			return DETAIL_USER_NUMBER;
		} else {
			return DETAIL;
		}
	}

	/**
	 * 
	 * 如果是用户号码提取任务,则组装用户号码提取任务相关信息
	 */
	public List<VFtbDataGetterTask> packUserNumberTask(
			List<VFtbDataGetterTask> list) {
		List<VFtbDataGetterTask> viewList = new ArrayList<VFtbDataGetterTask>();
		if (list != null && !list.isEmpty()) {
			// 初始化相关信息
			init();
			// for (VFtbDataGetterTask view : list) {
			// // 初始化相关信息
			// init();
			// List<FtbFilterColumnMapTask> columnFilterList =
			// dataGetterTaskManager
			// .getColumnMap(view.getNmDataGetterTaskId());
			// String[] businessInfo = packBusinessInfo(columnFilterList);
			// String[] areaInfo = packAreaInfo(columnFilterList);
			// view.setAreaName(areaInfo[0]);
			// view.setBusinessName(businessInfo[0]);
			// viewList.add(view);
			// }
			// 因为查询数据库的时间问题，尽量少查询数据库
			Map<Long, List<FtbFilterColumnMapTask>> columnFlmap = new HashMap<Long, List<FtbFilterColumnMapTask>>();
			String filterss = ",";

			// 1.组装数据
			for (VFtbDataGetterTask view : list) {
				if (filterss.indexOf("," + view.getNmDataGetterTaskId() + ",") == -1) {
					filterss = filterss + view.getNmDataGetterTaskId() + ",";
				}
			}
			filterss = filterss.substring(1, filterss.length() - 1);
			columnFlmap = dataGetterTaskManager.getColumnMapByMap(filterss);

			for (VFtbDataGetterTask view : list) {
				List<FtbFilterColumnMapTask> columnFilterList = columnFlmap
						.get(view.getNmDataGetterTaskId());
				String[] businessInfo = packBusinessInfo(columnFilterList);
				String[] areaInfo = packAreaInfo(columnFilterList);
				view.setAreaName(areaInfo[0]);
				view.setBusinessName(businessInfo[0]);
				viewList.add(view);
			}
		}
		return viewList;
	}

	/**
	 * 组装用户号码提取中区域信息
	 */
	public String[] packAreaInfo(List<FtbFilterColumnMapTask> columnFilterList) {
		if (areaMap == null) {
			areaMap = initAreaMap();
		}
		String[] areaInfo = new String[2];
		String area = "";
		String areaIds = "";
		if (columnFilterList != null && !columnFilterList.isEmpty()) {
			for (FtbFilterColumnMapTask filter : columnFilterList) {
				FtbTableColumnMap tableColumn = filter.getFtbTableColumnMap();
				// 如果是区域
				if (!userNumberBusinessColumnMap.get(1).equals(
						tableColumn.getVcColumnName())) {
					for (int i : userNumberAreaColumnMap.keySet()) {
						if (userNumberAreaColumnMap.get(i).equals(
								tableColumn.getVcColumnName())) {

							Map<Long, CommonDimension> map = areaMap.get(i);
							CommonDimension c = map.get(Common
									.StringToLong(filter.getVcFilterValue()));
							if (c != null) {
								area = area + c.getName() + ",";
							}
							areaIds = areaIds + i + ":"
									+ filter.getVcFilterValue() + ",";
							break;
						}
					}
				}

			}
		}
		if (!"".equals(area)) {
			area = area.substring(0, area.lastIndexOf(","));
			areaIds = areaIds.substring(0, areaIds.lastIndexOf(","));
		}
		areaInfo[0] = area;
		areaInfo[1] = areaIds;
		return areaInfo;
	}

	/**
	 * 组装用户号码提取中业务信息
	 */
	public String[] packBusinessInfo(
			List<FtbFilterColumnMapTask> columnFilterList) {
		Map<Long, CommonDimension> businessMap = null;
		if (businessList == null) {
			businessList = commonDimensionManager.findAllBussiness();
		}
		if (businessList != null && !businessList.isEmpty()) {
			businessMap = new HashMap<Long, CommonDimension>();
			for (CommonDimension c : businessList) {
				businessMap.put(c.getId(), c);
			}
		}
		String[] businessInfo = new String[2];
		String business = "";
		String businessIds = "";
		if (columnFilterList != null && !columnFilterList.isEmpty()) {
			for (FtbFilterColumnMapTask filter : columnFilterList) {
				FtbTableColumnMap tableColumn = filter.getFtbTableColumnMap();
				// 如果是业务
				if (userNumberBusinessColumnMap.get(1).equals(
						tableColumn.getVcColumnName())) {
					CommonDimension c = businessMap.get(Common
							.StringToLong(filter.getVcFilterValue()));
					if(c!=null && c.getName()!=null){
						business = business + c.getName() + ",";
						businessIds = businessIds + c.getType() + ":"
								+ filter.getVcFilterValue() + ",";
					}
					
				}
			}
		}
		if (!"".equals(business)) {
			business = business.substring(0, business.lastIndexOf(","));
			businessIds = businessIds
					.substring(0, businessIds.lastIndexOf(","));
		}
		businessInfo[0] = business;
		businessInfo[1] = businessIds;
		return businessInfo;
	}

	/**
	 * 初始化信息
	 */
	public void init() {
		initTableColumnMap();
		initOutColumnMap();
		initUserNumberAreaColumnMap();
		initUserNumberBusinessColumnMap();
		initAreaMap();
	}

	/**
	 * 初始化map集合
	 */
	public void initTableColumnMap() {
		if (tableColumnMap == null) {
			tableColumnMap = new HashMap<String, Integer>();
			List<FtbTableColumnMap> columnList = dataGetterTaskManager
					.getTableColumnMap();
			if (columnList != null && !columnList.isEmpty()) {
				for (FtbTableColumnMap column : columnList) {
					tableColumnMap.put(column.getVcColumnName(), column
							.getNmTableColumnMapId());
				}
			}
		}
		// logger.info(tableColumnMap.toString());
	}

	/**
	 * 初始化解析数据需要呈现的字段信息
	 */
	public void initOutColumnMap() {
		if (outColumnMap == null) {
			outColumnMap = new LinkedHashMap<Integer, String>();
			outColumnMap.put(9, "nmImsi");// nmImsi
			outColumnMap.put(1, "nmMsisdn");// 号码
			outColumnMap.put(42, "nmImei");// IMEI
			outColumnMap.put(39, "intAppUpFlush");// 上行数据流量
			outColumnMap.put(40, "intAppDownFlush");// 下行数据流量
			outColumnMap.put(37, "intAppUpPacketCnt");// 上行数据包数量
			outColumnMap.put(38, "intAppDownPacketCnt");// 下行数据包数量
			outColumnMap.put(2, "vcApn");// APN
			outColumnMap.put(3, "vcWapType");// wap类型
			outColumnMap.put(5, "vcContentType");// 内容类型
			outColumnMap.put(6, "intLac");// LAC
			outColumnMap.put(8, "vcCgi");// CGI			
			outColumnMap.put(10, "dtBeginTime");// 会话流程开始时间
			outColumnMap.put(11, "dtEndTime");// 会话流程结束时间
			outColumnMap.put(12, "nmSessionTime");// 会话时长
			outColumnMap.put(13, "vcBearerSrcAddress");// 承载层源地址
			outColumnMap.put(14, "vcBearerDestAddress");// 承载层目的地址
			outColumnMap.put(15, "intIpLen");// IP长度
			outColumnMap.put(16, "vcIpType");// IP协议类型
			outColumnMap.put(17, "ipClientAddress");// 客户端地址
			outColumnMap.put(18, "ipServerAddress");// 服务器地址
			outColumnMap.put(19, "ipClientPort");// 客户端端口
			outColumnMap.put(20, "ipServerPort");// 服务器端口
			outColumnMap.put(21, "intAbortType");// 错误码
			outColumnMap.put(22, "vcWtpAbortReason");// WTP Abort Reason
			outColumnMap.put(23, "vcWapType");// WAP类型
			outColumnMap.put(24, "vcWapStatus");// WAP状态码
			outColumnMap.put(25, "vcWapUrl");// WSP URL/HTTP URL
			outColumnMap.put(26, "vcWapUserAgent");// WSP UserAgent/Http
			// UserAgent
			outColumnMap.put(27, "intMmsLen");// 彩信内容长度
			outColumnMap.put(28, "vcMmsMetho");// 彩信方法
			outColumnMap.put(29, "vcMmsFrom");// 彩信发送方号码
			outColumnMap.put(30, "vcMmsTo");// 彩信接收方号码
			outColumnMap.put(31, "vcMmsSubject");// 彩信主题
			outColumnMap.put(32, "vcMmsTranId");// MMS Transaction ID
			outColumnMap.put(33, "vcAppMt");// 应用层会话类型
			outColumnMap.put(34, "intAppSuccessful");// 会话成功标志
			outColumnMap.put(35, "vcAppRetranType");// 重传类型
			outColumnMap.put(36, "vcAppRetranTimes");// 重传次数
						
			outColumnMap.put(41, "intAppAckTime");// 响应时间			
			outColumnMap.put(43, "intRaitype");// TD

		}
	}

	/**
	 * 初始化用户号码提取需要获取的区域字段信息
	 */
	public void initUserNumberAreaColumnMap() {
		if (userNumberAreaColumnMap == null) {
			userNumberAreaColumnMap = new HashMap<Integer, String>();
			userNumberAreaColumnMap.put(
					CommonConstants.MANTOEYE_SPACE_LEVEL_BSC, "intBscId");// 1:BSC
			userNumberAreaColumnMap.put(
					CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN, "intDescSgsnId");
			userNumberAreaColumnMap.put(
					CommonConstants.MANTOEYE_SPACE_LEVEL_STREET, "intStreetId");
			userNumberAreaColumnMap.put(
					CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA,
					"intSaleAreaId");
			userNumberAreaColumnMap.put(
					CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH, "intBranchId");
		}
	}

	/**
	 * 初始化用户号码提取需要获取的业务字段信息
	 */
	public void initUserNumberBusinessColumnMap() {
		if (userNumberBusinessColumnMap == null) {
			userNumberBusinessColumnMap = new HashMap<Integer, String>();
			userNumberBusinessColumnMap.put(1, "nmBussinessId");// 业务
			userNumberBusinessColumnMap.put(2, "nmBussinessTypeId");// 业务类型
		}
	}

	/**
	 * 初始化区域维表MAP集合
	 */
	public Map<Integer, Map<Long, CommonDimension>> initAreaMap() {
		List<CommonDimension> bscList = commonDimensionManager.findAllBsc();
		List<CommonDimension> streetList = commonDimensionManager
				.findAllStreet();
		List<CommonDimension> saleAreaList = commonDimensionManager
				.findAllSaleArea();
		List<CommonDimension> sgsnList = commonDimensionManager.findAllSgsn();
		List<CommonDimension> branchList = commonDimensionManager
				.findAllBranch();
		Map<Integer, Map<Long, CommonDimension>> areaMap = new HashMap<Integer, Map<Long, CommonDimension>>();

		areaMap.put(CommonConstants.MANTOEYE_SPACE_LEVEL_BSC,
				packMapData(bscList));
		areaMap.put(CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN,
				packMapData(sgsnList));
		areaMap.put(CommonConstants.MANTOEYE_SPACE_LEVEL_STREET,
				packMapData(streetList));
		areaMap.put(CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA,
				packMapData(saleAreaList));
		areaMap.put(CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH,
				packMapData(branchList));
		return areaMap;
	}

	public Map<Long, CommonDimension> packMapData(List<CommonDimension> list) {
		Map<Long, CommonDimension> map = new HashMap<Long, CommonDimension>();
		if (list != null && !list.isEmpty()) {
			for (CommonDimension c : list) {
				map.put(c.getId(), c);
			}
		}
		return map;
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
	
}
