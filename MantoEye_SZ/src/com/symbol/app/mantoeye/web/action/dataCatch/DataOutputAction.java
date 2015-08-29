package com.symbol.app.mantoeye.web.action.dataCatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.VFtbDataOutPutTask;
import com.symbol.app.mantoeye.dto.flush.OutPutTableColumnEntity;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbCgi;
import com.symbol.app.mantoeye.entity.FtbDataOutPutTask;
import com.symbol.app.mantoeye.service.dataCatch.DataOutputManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.app.mantoeye.web.action.Phone;
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


public class DataOutputAction extends BaseDispatchAction {
	private static final long serialVersionUID = -7992128821878284128L;
	private static final String Task_VcCgi = "task_vccgi";
	@Autowired
	private DataOutputManager dataOutputManager;
	@Resource(name = "commonManagerImpl")
	private ICommonManager commonManagerImpl;
	private FtbDataOutPutTask entity;
	private String status;
	private String vcCgiChName;
	private String vcCgi;
	private List<FtbCgi> ftbCgiList = new ArrayList<FtbCgi>();

	public List<FtbCgi> getFtbCgiList() {
		return ftbCgiList;
	}

	public void setFtbCgiList(List<FtbCgi> ftbCgiList) {
		this.ftbCgiList = ftbCgiList;
	}

	public String getVcCgi() {
		return vcCgi;
	}

	public void setVcCgi(String vcCgi) {
		this.vcCgi = vcCgi;
	}

	public String getVcCgiChName() {
		return vcCgiChName;
	}

	public void setVcCgiChName(String vcCgiChName) {
		this.vcCgiChName = vcCgiChName;
	}

	private Long nmId;
	private String[] nmTableMapIds;
	private String[] innmTableMapIds;

	public String[] getInnmTableMapIds() {
		return innmTableMapIds;
	}

	public void setInnmTableMapIds(String[] innmTableMapIds) {
		this.innmTableMapIds = innmTableMapIds;
	}

	public String[] getNmTableMapIds() {
		return nmTableMapIds;
	}

	public void setNmTableMapIds(String[] nmTableMapIds) {
		this.nmTableMapIds = nmTableMapIds;
	}

	public Long getNmId() {
		return nmId;
	}

	public void setNmId(Long nmId) {
		this.nmId = nmId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*private FtbDataOutPutDecTask entity1;

	public FtbDataOutPutDecTask getEntity1() {
		return entity1;
	}

	public void setEntity1(FtbDataOutPutDecTask entity1) {
		this.entity1 = entity1;
	}*/

	private String keys;
	private Page<FtbDataOutPutTask> pageTask = new Page<FtbDataOutPutTask>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private Page<FtbCgi> page = new Page<FtbCgi>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	public Page<FtbDataOutPutTask> getPageTask() {
		return pageTask;
	}

	public void setPageTask(Page<FtbDataOutPutTask> pageTask) {
		this.pageTask = pageTask;
	}

	public Page<FtbCgi> getPage() {
		return page;
	}

	public void setPage(Page<FtbCgi> page) {
		this.page = page;
	}

	private Page<CommonSport> pageNmMsisdn = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 查询原始数据
	 * 
	 */
	public void query() throws ServletException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String taskStatus_search = request.getParameter("taskStatus_search");
		if (!"-1".equals(taskStatus_search) && taskStatus_search != null) {// 状态
			filters.add(new PropertyFilter("intTaskStatus", Integer
					.parseInt(taskStatus_search), MatchType.EQ));
		}
		filters.add(new PropertyFilter("intTaskType", 3, MatchType.EQ));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = dataOutputManager.find(filters).size();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		pageTask.setPageSize(gridServerHandler.getPageSize());
		pageTask.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			/**
			 * grid控件排序有3种情况：asc desc 和 第一次加载数据
			 * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
			 */
			if ("defaultsort".equals(order)) {
				pageTask.setOrder("desc");
				pageTask.setOrderBy("dtOrderTime");
			} else {
				pageTask.setOrder(order);
				pageTask.setOrderBy(MantoEyeUtils.getSortField(si
						.getFieldName()));
			}
			// 默认排序方式
		} else {
			pageTask.setOrderBy("dtOrderTime");
			pageTask.setOrder("desc");
		}

		pageTask = dataOutputManager.search(pageTask, filters);
		List<FtbDataOutPutTask> list = pageTask.getResult();
		List<VFtbDataOutPutTask> viewList = dataOutputManager
				.convertBeanToView(list);
		gridServerHandler.setData(viewList, VFtbDataOutPutTask.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 保存自定义数据输出
	 */
	public String saveDataOutput() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer()
				.getUserInfo();
		String msg = null;
		try {
			Long taskId = entity.getNmDataOutPutTaskId();
			if (taskId != null) {// 编辑
				//dataOutputManager.editDataOutPutDecTask(entity1);
				boolean succ = dataOutputManager
						.editParseTask(entity, userInfo);
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
				entity.setVcTaskOrder(userInfo.getVcUserName());
				entity.setBitTaskActiveFlag(false);
				dataOutputManager.saveParseTask(entity);
				Long nmDataOutPutTaskId = dataOutputManager
						.findTaskByName(entity.getVcTaskName());
				/*if (nmDataOutPutTaskId != 0) {
					entity1.setNmDataOutPutTaskId(nmDataOutPutTaskId);
					dataOutputManager.saveDataOutPutDecTask(entity1);
				}*/
			}

			msg = "自定义数据输出任务[名称：" + entity.getVcTaskName() + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[名称：" + entity.getVcTaskName() + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 保存输出字段与任务对照表
	 */
	public String saveOutPutColumnMapTask() throws ServletException,
			IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String msg = null;
		try {
			dataOutputManager.deleteOutPutColumnMapTask(Common
					.StringToLong(taskId));
			if (nmTableMapIds!=null && !nmTableMapIds.equals("")) {
				for (int i = 0; i < nmTableMapIds.length; i++) {
					dataOutputManager.saveOutPutColumnMapTask(Common
							.StringToLong(taskId), Common
							.StringToLong(nmTableMapIds[i]));
				}
			}
			if (innmTableMapIds!=null && !innmTableMapIds.equals("")) {
				List<OutPutTableColumnEntity> columnList = dataOutputManager.queryColumnMapByTableId(Common.StringToLong(taskId));
				for (int j = 0; j < innmTableMapIds.length; j++) {
					String nmTableMapId = innmTableMapIds[j];
					for (int i = 0; i < columnList.size(); i++) {
						OutPutTableColumnEntity  outPutTableColumnEntity= columnList.get(i);
						if (nmTableMapId.equals(outPutTableColumnEntity.getNmTableMapId())) {
							String vcColumnName = outPutTableColumnEntity.getVcColumnName();
							if (vcColumnName.equals("nmImei")) {
								dataOutputManager.updateOutPutColumnMap(Common.StringToLong(taskId),"intImeiStatus",0);
							}
							if (vcColumnName.equals("nmMsisdn")) {
								dataOutputManager.updateOutPutColumnMap(Common.StringToLong(taskId),"intMsisdnStatus",0);
							}
							if (vcColumnName.equals("vcBussinessName")) {
								dataOutputManager.updateOutPutColumnMap(Common.StringToLong(taskId),"intbussinessStatus",0);
							}
							if (vcColumnName.equals("vcCgi")) {
								dataOutputManager.updateOutPutColumnMap(Common.StringToLong(taskId),"intCgiStatus",0);
							}
							if (vcColumnName.equals("vcWapUrl")) {
								dataOutputManager.updateOutPutColumnMap(Common.StringToLong(taskId),"intURLStatus",0);
							}
							if (vcColumnName.equals("vcApn")) {
								dataOutputManager.updateOutPutColumnMap(Common.StringToLong(taskId),"intApnStatus",0);
							}
						}
					}
				}
			}
			msg = "保存成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	public Page<CommonSport> getPageNmMsisdn() {
		return pageNmMsisdn;
	}

	public void setPageNmMsisdn(Page<CommonSport> pageNmMsisdn) {
		this.pageNmMsisdn = pageNmMsisdn;
	}

	public String add() throws Exception {
		return ADD;
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
				dataOutputManager.deleteTasks(id);
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
			FtbDataOutPutTask entity = dataOutputManager.get(Long
					.parseLong(taskId));
			entity.setIntTaskStatus(4);// 停止中
			dataOutputManager.save(entity);
			msg = "停止任务[：" + taskId + "]" + "成功!";
			commonManagerImpl.log(request, msg);
		} catch (Exception e) { // 删除失败
			msg = "停止任务[：" + taskId + "]" + "失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}
	
	
	/**
	 * 激活任务
	 */
	public void activeTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String msg = "";
		try {
			boolean flag = dataOutputManager.isExistOutPutColumnMap(Long.parseLong(taskId));
			if (flag) {
				FtbDataOutPutTask entity = dataOutputManager.get(Long
						.parseLong(taskId));
				entity.setBitTaskActiveFlag(true);//激活
				dataOutputManager.save(entity);
				msg = "激活任务成功!";
				commonManagerImpl.log(request, msg);
			}else{
				msg = "不能激活该任务，请输入任务条件!";
				commonManagerImpl.log(request, msg);
			}			
		} catch (Exception e) { // 删除失败
			msg = "激活任务失败!";
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
		entity = dataOutputManager.get(Common.StringToLong(keyid));
		//entity1 = dataOutputManager.getByTaskId(Common.StringToLong(keyid));
		return EDIT;
	}
	

	/**
	 * 判断该任务是否存在
	 * 
	 * @throws IOException
	 */
	public String checkUnique() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcTaskName = request.getParameter("vcTaskName").trim();
		int i = dataOutputManager.isVcTaskName(vcTaskName);
		if (i == -1) {// -1表示不存在该任务名称
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 判断该文件名是否存在
	 * 
	 * @throws IOException
	 */
	public String checkUnique1() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcFileName = request.getParameter("vcFileName").trim();
		int i = dataOutputManager.isVcFileName(vcFileName);
		if (i == -1) {// -1表示不存在该任务名称
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 根据表ID 查询表里所有的列字段
	 */
	public void queryColumnMap() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		HttpServletRequest request = ServletActionContext.getRequest();
		List<OutPutTableColumnEntity> list = new ArrayList<OutPutTableColumnEntity>();
		String taskId = request.getParameter("taskId");
		list = dataOutputManager.queryColumnMapByTableId(Common
				.StringToLong(taskId));
		gridServerHandler.setData(list, OutPutTableColumnEntity.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 查询号码
	 */

	public void queryNmMsisdn() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String nmMsisdn = request.getParameter("nmMsisdn");
		if (nmMsisdn != null) {
			nmMsisdn = nmMsisdn.trim();
		}
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			pageNmMsisdn.setOrder(order);
			pageNmMsisdn.setOrderBy(MantoEyeUtils.getSortField(si
					.getFieldName()));

		} else {
			pageNmMsisdn.setOrderBy("nmId");
			pageNmMsisdn.setOrder("desc");
		}
		pageNmMsisdn.setPageSize(gridServerHandler.getPageSize());
		pageNmMsisdn.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		pageNmMsisdn = dataOutputManager.queryNmMsisdn(pageNmMsisdn, Common
				.StringToLong(taskId), nmMsisdn);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = pageNmMsisdn.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = pageNmMsisdn.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public String queryCgi() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		page = dataOutputManager.queryCgi(page, vcCgiChName);
		List<FtbCgi> cList = dataOutputManager.queryOutPutCgiMap(Common
				.StringToLong(taskId));
		ftbCgiList = (List<FtbCgi>) request.getSession().getAttribute(
				Task_VcCgi);
		if (ftbCgiList == null) {
			ftbCgiList = new ArrayList<FtbCgi>();
			ftbCgiList.addAll(cList);
			request.getSession().setAttribute(Task_VcCgi, ftbCgiList);
		}
		return "task_cgi";
	}

	public String addCgi() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<FtbCgi> cList = new ArrayList<FtbCgi>();
		cList = (List<FtbCgi>) request.getSession().getAttribute(Task_VcCgi);
		if (cList != null && cList.size() > 0) {
			ftbCgiList.addAll(cList);
		}
		if (vcCgi != null && !vcCgi.equals("")) {
			String[] vcCgis = vcCgi.split(",");
			for (int j = 0; j < vcCgis.length; j++) {
				FtbCgi ftbCgi = new FtbCgi();
				logger.info(vcCgis[j]);
				String[] s = vcCgis[j].split("_");
				String vcCgiChName = new String(s[0].getBytes("ISO-8859-1"),
						"UTF-8");
				String cgi = s[1];
				ftbCgi.setVcCgiChName(vcCgiChName);
				ftbCgi.setVcCGI(cgi);
				boolean flag = true;
				for (int i = 0; i < ftbCgiList.size(); i++) {
					FtbCgi ftbCgiTemp = ftbCgiList.get(i);
					if (ftbCgiTemp.getVcCGI().equals(cgi)) {
						flag = false;
						break;
					}
				}
				if (flag) {
					ftbCgiList.add(ftbCgi);
				}
			}
		}
		request.getSession().setAttribute(Task_VcCgi, ftbCgiList);
		return queryCgi();
	}

	public String removeCgi() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<FtbCgi> cList = new ArrayList<FtbCgi>();
		cList = (List<FtbCgi>) request.getSession().getAttribute(Task_VcCgi);
		if (cList != null && cList.size() > 0) {
			ftbCgiList.addAll(cList);
		}
		if (vcCgi != null && !vcCgi.equals("")) {
			String[] vcCgis = vcCgi.split(",");
			for (int j = 0; j < vcCgis.length; j++) {
				FtbCgi ftbCgi = new FtbCgi();
				logger.info(vcCgis[j]);
				String[] s = vcCgis[j].split("_");
				String vcCgiChName = new String(s[0].getBytes("ISO-8859-1"),
						"UTF-8");
				String cgi = s[1];
				ftbCgi.setVcCgiChName(vcCgiChName);
				ftbCgi.setVcCGI(cgi);
				for (int i = 0; i < ftbCgiList.size(); i++) {
					FtbCgi ftbCgiTemp = ftbCgiList.get(i);
					if (ftbCgiTemp.getVcCGI().equals(cgi)) {
						ftbCgiList.remove(ftbCgiTemp);
						break;
					}
				}
			}
		}
		request.getSession().setAttribute(Task_VcCgi, ftbCgiList);
		return queryCgi();
	}

	public String removeAllCgi() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		cleanCgiSession();
		page = dataOutputManager.queryCgi(page, vcCgiChName);
		ftbCgiList = null;
		return "task_cgi";
	}

	public void cleanCgiSession() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		request.getSession().removeAttribute(Task_VcCgi);
		logger.info("清空cgi的session");
	}

	/**
	 * 号码导出
	 */
	public void exportNmMsisdn() throws ServletException, IOException {
		List<CommonSport> list = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String nmMsisdn = request.getParameter("nmMsisdn");
		if (nmMsisdn != null) {
			nmMsisdn = nmMsisdn.trim();
		}
		list = dataOutputManager.exportNmMsisdn(Common.StringToLong(taskId),
				nmMsisdn);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "（用户号码导出）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	/**
	 * 保存号码
	 */
	public String saveNmMsisdn() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String nmMsisdn = request.getParameter("nmMsisdn").trim();
		String intMsisdnStatus = request.getParameter("intMsisdnStatus");
		String msg = null;
		try {
			int status = 0;
			if (intMsisdnStatus != null && !intMsisdnStatus.equals("0")) {
				status = Integer.parseInt(intMsisdnStatus);
			}
			// int num = dataOutputManager.isOutPutMsisdnMap(Common
			// .StringToLong(taskId));
			// 判断是否已上传过号码
			// if (num == 0) {
			// 先查询过滤条件与任务对照表是否以存在改任务的记录
			boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
					.StringToLong(taskId));
			int value = status;
			if (!flag) {
				dataOutputManager.saveOutPutColumnMap(Common
						.StringToLong(taskId), "intMsisdnStatus", value);
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intMsisdnStatus", value);
			}
			// }
			List<String> msisList = new ArrayList<String>();
			String[] msisdn = nmMsisdn.split(",");
			for (int i = 0; i < msisdn.length; i++) {
				if (!msisList.contains("86" + msisdn[i])) {
					msisList.add("86" + msisdn[i]);
				}			
			}
			dataOutputManager.saveOutPutMsisdnMap(Common.StringToLong(taskId),
					msisList);
			msg = "自定义数据输出任务[添加：" + nmMsisdn + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[添加：" + nmMsisdn + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 判断该号码是否存在
	 * 
	 * @throws IOException
	 */
	public String checkUniqueNmMsisdn() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String nmMsisdn = request.getParameter("nmMsisdn");
		String taskId = request.getParameter("taskId");
		int i = dataOutputManager.isNmMsisdn(Common.StringToLong(taskId),
				Common.StringToLong("86" + nmMsisdn), nmId);
		;
		if (i == -1) {// -1表示不存在该任务名称
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 修改号码
	 */
	public String updateNmMsisdn() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String nmMsisdn = request.getParameter("nmMsisdn");
		String msg = null;
		try {
			dataOutputManager.updateOutPutMsisdnMap(
					Common.StringToLong(taskId), Common.StringToLong("86"
							+ nmMsisdn), nmId);
			msg = "自定义数据输出任务[修改：" + nmMsisdn + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[修改：" + nmMsisdn + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 删除号码
	 * 
	 * @throws Exception
	 */
	public void deleteNmMsisdn() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String nmIds = request.getParameter("nmIds");
		String taskId = request.getParameter("taskId");
		/*
		 * String[] sids = nmIds.split(","); Long[] ids = new Long[sids.length];
		 * for (int i = 0; i < ids.length; i++) { ids[i] =
		 * Common.StringToLong(sids[i]); }
		 */
		String msg = "";
		try {

			/*
			 * for (Long id : ids) { dataOutputManager.deleteNmMsisdn(id); }
			 */
			dataOutputManager.deleteNmMsisdn(nmIds);
			boolean flag = dataOutputManager.isExistNmMsisdn(Common.StringToLong(taskId));
			if (!flag) {
				int value = 0;// 状态为0未设置
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intMsisdnStatus", value);
			}
			msg = "删除号码成功!";
			commonManagerImpl.log(request, "删除自定义数据输出号码[ID：" + nmIds + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除号码失败!";
			logger.error(e.toString());
			logger.error(e.getMessage());
			commonManagerImpl.log(request, "删除自定义数据输出号码[ID：" + nmIds + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public void checkMsisdnStatus() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		status = "";
		int num = dataOutputManager.isOutPutMsisdnMap(Common
				.StringToLong(taskId));
		if (num != 0) {
			List outPutColumnMapList = dataOutputManager
					.isOutPutColumnMap(Common.StringToLong(taskId));
			if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
				Object[] obj = (Object[]) outPutColumnMapList.get(0);
				if (obj[2] != null) {
					status = obj[2].toString();
				}
			}
		}
		Struts2Utils.renderText(status);
	}

	/**
	 * 上传号码
	 */

	public String upNumberFile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String intMsisdnStatus = request.getParameter("intMsisdnStatus");
		String taskId = request.getParameter("taskId");
		int status = 0;
		if (intMsisdnStatus != null && !intMsisdnStatus.equals("0")) {
			status = Integer.parseInt(intMsisdnStatus);
		}
		// int num = dataOutputManager.isOutPutMsisdnMap(Common
		// .StringToLong(taskId));
		// 判断是否已上传过号码
		// if (num == 0) {
		// 先查询过滤条件与任务对照表是否以存在改任务的记录
		boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
				.StringToLong(taskId));
		int value = status;
		if (!flag) {
			dataOutputManager.saveOutPutColumnMap(Common.StringToLong(taskId),
					"intMsisdnStatus", value);
		} else {
			dataOutputManager.updateOutPutColumnMap(
					Common.StringToLong(taskId), "intMsisdnStatus", value);
		}
		// }
		String msg = "";
		List<String> msisList = new ArrayList<String>();
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		int error = 0;
		List<Integer> errorNum = new ArrayList<Integer>();
		int total = 0;
		int repeat = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					this.getFile()), "GBK"));
			while ((str = br.readLine()) != null) {
				total++;
				String msisdn = str.trim();
				    String startMsisdn = msisdn.substring(0, 2);
					if (!startMsisdn.equals("86")) {
						msisdn = "86" + msisdn;
					}
					if (!this.isNumeric(msisdn)) {
						errorNum.add(total);
						error++;
						continue;
					}
					if (!msisdn.substring(2, 3).equals("1")) {
						error++;
						continue;
					}
					if (!msisList.contains(msisdn)) {
						msisList.add(msisdn);
					} else {
						repeat++;
					}
			}

			msg = "文件上传总记录数 :" + total + "， 无效记录数:" + error + "，重复记录：" + repeat;
			request.setAttribute(VarConstants.SUCC_CODE, "0000110");
			request.setAttribute("message", msg);
			dataOutputManager.saveOutPutMsisdnMap(Common.StringToLong(taskId),
					msisList);
			return SUCCESS;

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE, "上传文件为空");
			return ERROR;
		} catch (IOException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00011);
			return ERROR;
		} finally {
			commonManagerImpl.log(Struts2Utils.getRequest(), msg);
			try {
				br.close();
			} catch (Exception e) {
				// logger.error(e.getMessage());
			}
		}

	}

	/**
	 * 上传url
	 */

	public String upURLFile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String intURLStatus = request.getParameter("intURLStatus");
		String taskId = request.getParameter("taskId");
		int status = 0;
		if (intURLStatus != null && !intURLStatus.equals("0")) {
			status = Integer.parseInt(intURLStatus);
		}

		// 先查询过滤条件与任务对照表是否以存在改任务的记录
		// int num = dataOutputManager.isExistsUrl(Common.StringToLong(taskId));
		// if (num == 0) {
		boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
				.StringToLong(taskId));
		int value = status;
		if (!flag) {
			dataOutputManager.saveOutPutColumnMap(Common.StringToLong(taskId),
					"intURLStatus", value);
		} else {
			dataOutputManager.updateOutPutColumnMap(
					Common.StringToLong(taskId), "intURLStatus", value);
		}
		// }
		String msg = "";
		List<String> urlList = new ArrayList<String>();
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		List<Integer> errorNum = new ArrayList<Integer>();
		int total = 0;
		int repeat = 0;
		int errorCont = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					this.getFile()), "GBK"));
			while ((str = br.readLine()) != null) {
				total++;
				String url = str.trim();
				if (url != null && !"".equals(url)) {
					if (!urlList.contains(url)) {
						urlList.add(url);
					} else {
						repeat++;
					}
				} else {
					errorCont++;
					continue;
				}
			}

			msg = "文件上传总记录数 :" + total + "，重复记录：" + repeat + ",错误记录："
					+ errorCont;
			request.setAttribute(VarConstants.SUCC_CODE, "0000110");
			request.setAttribute("message", msg);
			dataOutputManager.saveOutPutUrlMap(Common.StringToLong(taskId),
					urlList);
			return SUCCESS;

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.SUCC_CODE_00006);
			return ERROR;
		} catch (IOException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE, "上传文件为空");
			return ERROR;
		} finally {
			commonManagerImpl.log(Struts2Utils.getRequest(), msg);
			try {
				br.close();
			} catch (Exception e) {
				// logger.error(e.getMessage());
			}
		}

	}

	/**
	 * 上传IMEI
	 */

	public String upIMEIFile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String intImeiStatus = request.getParameter("intImeiStatus");
		String taskId = request.getParameter("taskId");
		int status = 0;
		if (intImeiStatus != null && !intImeiStatus.equals("0")) {
			status = Integer.parseInt(intImeiStatus);
		}

		// int num =
		// dataOutputManager.isExistsvcImei(Common.StringToLong(taskId));
		// if (num == 0) {
		boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
				.StringToLong(taskId));
		int value = status;
		if (!flag) {
			dataOutputManager.saveOutPutColumnMap(Common.StringToLong(taskId),
					"intImeiStatus", value);
		} else {
			dataOutputManager.updateOutPutColumnMap(
					Common.StringToLong(taskId), "intImeiStatus", value);
		}
		// }
		String msg = "";
		List<String> IMEIList = new ArrayList<String>();
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		int total = 0;
		int repeat = 0;
		int errorCont = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					this.getFile()), "GBK"));
			while ((str = br.readLine()) != null) {
				total++;
				String IMEI = str.trim();
				if (IMEI != null && !"".equals(IMEI)) {
					if (!this.isNumeric(IMEI)) {
						errorCont++;
						continue;
					}
					if (!IMEIList.contains(IMEI)) {
						IMEIList.add(IMEI);
					} else {
						repeat++;
					}
				} else {
					errorCont++;
					continue;
				}
			}

			msg = "文件上传总记录数 :" + total + "，重复记录：" + repeat+",错误记录："+errorCont;
			request.setAttribute(VarConstants.SUCC_CODE, "0000110");
			request.setAttribute("message", msg);
			dataOutputManager.saveOutPutImeiMap(Common.StringToLong(taskId),
					IMEIList);
			return SUCCESS;

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE, "上传文件为空");
			return ERROR;
		} catch (IOException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00011);
			return ERROR;
		} finally {
			commonManagerImpl.log(Struts2Utils.getRequest(), msg);
			try {
				br.close();
			} catch (Exception e) {
				// logger.error(e.getMessage());
			}
		}

	}

	public boolean isNumeric(String str) {// 判断是否是数字类型
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 保存CGI
	 */
	public String saveOutPutCgiMap() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		try {
			List<FtbCgi> cList = (List<FtbCgi>) request.getSession()
					.getAttribute(Task_VcCgi);
			request.getSession().removeAttribute(Task_VcCgi);
			String taskId = request.getParameter("taskId");
			boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
					.StringToLong(taskId));
			int value = 1;
			if (!flag) {
				dataOutputManager.saveOutPutColumnMap(Common
						.StringToLong(taskId), "intCgiStatus", value);
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intCgiStatus", value);
			}
			dataOutputManager.deleteOutPutCgiMap(Common.StringToLong(taskId));
			if (cList != null && cList.size() > 0) {
				for (FtbCgi ftbCgi : cList) {
					String vcCgi = ftbCgi.getVcCGI();
					dataOutputManager.saveOutPutCgiMap(Common
							.StringToLong(taskId), vcCgi);
				}
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intCgiStatus", 0);
			}
			msg = "自定义数据输出任务[添加cgi成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[添加cgi失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	private String getMapKeyStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for (Iterator it = setType.iterator(); it.hasNext();) {
				str = str + "," + it.next();
			}
			if (str.indexOf(",") != -1) {
				str = str.substring(1);
			}
		}
		return str;
	}

	private String getMapValueStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for (Iterator<String> it = setType.iterator(); it.hasNext();) {
				str = str + "," + map.get(it.next());
			}
			if (str.indexOf(",") != -1) {
				str = str.substring(1);
			}
		}
		return str;
	}

	/**
	 * 初始化apn
	 */
	public void initApnDialogData() throws Exception {
		Struts2Utils.renderXml(packBussnessInfo());
	}

	public String packBussnessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = dataOutputManager
				.queryApnAndApnType();
		List<String> apnList = new ArrayList<String>(map.keySet());
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml = xml + "<root>";
		if (apnList != null && !apnList.isEmpty()) {
			for (int i = 0; i < apnList.size(); i++) {
				String apnAnadId = apnList.get(i);
				Map<String, String> mapType = map.get(apnAnadId);
				String[] str = apnAnadId.split(":");// 分离品牌名称和Id

				Set setType = mapType.keySet();
				Collection con = mapType.values();
				String modle = null;
				String typeIds = null;
				if (setType != null) {
					modle = getMapKeyStr(mapType);
					typeIds = getMapValueStr(mapType);
				}

				xml = xml + "<data>" + "<brandId>" + str[1] + "</brandId>"
						+ "<brandName>" + str[0] + "</brandName>" + "<modelId>"
						+ typeIds + "</modelId>" + "<modelName>" + modle
						+ "</modelName>" + "</data>";
			}
		}
		xml = xml + "</root>";
		return xml;
	}

	public String taskApn() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String apn = dataOutputManager.queryApnIdAndApnIdTypeByTaskId(Common
				.StringToInt(taskId));
		if (apn != null) {
			String[] apns = apn.split("@");
			request.setAttribute("apn", apns[1]);
			request.setAttribute("apnIds", apns[0]);
		}
		status = "";
		List outPutColumnMapList = dataOutputManager.isOutPutColumnMap(Common
				.StringToLong(taskId));
		if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
			Object[] obj = (Object[]) outPutColumnMapList.get(0);
			if (obj[4] != null) {
				status = obj[4].toString();
			}
		}

		return "task_apn";
	}

	public String taskArea() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		status = "";
		List outPutColumnMapList = dataOutputManager.isOutPutColumnMap(Common
				.StringToLong(taskId));
		if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
			Object[] obj = (Object[]) outPutColumnMapList.get(0);
			if (obj[5] != null) {
				status = obj[5].toString();
			}
		}
		return "task_area";
	}

	/**
	 * 保存APN
	 */
	public String saveOutPutApnMap() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		String[] id = null;
		String vcApn = null;
		try {
			String taskId = request.getParameter("taskId");
			String apn = request.getParameter("apn");
			boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
					.StringToLong(taskId));
			int value = 1;
			if (!flag) {
				dataOutputManager.saveOutPutColumnMap(Common
						.StringToLong(taskId), "intApnStatus", value);
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intApnStatus", value);
			}
			dataOutputManager.deleteOutPutApnMap(Common.StringToLong(taskId));
			if (apn != null && !"".equals(apn)) {
				String[] datas = apn.split(",");
				for (int i=0; i<datas.length;i++) {
					vcApn = datas[i];
					dataOutputManager.saveOutPutApnMap(Common
							.StringToLong(taskId), vcApn);
				}
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intApnStatus", 0);
			}
			msg = "自定义数据输出任务[添加业务成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[添加：业务失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 保存业务
	 */
	public String saveOutPutBussinessMap() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		String[] id = null;
		String businessId = null;
		try {
			String taskId = request.getParameter("taskId");
			boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
					.StringToLong(taskId));
			int value = 1;
			if (!flag) {
				dataOutputManager.saveOutPutColumnMap(Common
						.StringToLong(taskId), "intbussinessStatus", value);
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intbussinessStatus", value);
			}
			// 保存业务过滤值
			String businessIds = request.getParameter("businessIds");
			dataOutputManager.deleteOutPutBussinessMap(Common
					.StringToLong(taskId));
			if (businessIds != null && !"".equals(businessIds)) {

				String[] datas = businessIds.split(",");
				for (String ids : datas) {
					id = ids.split(":");
					businessId = id[1].trim();
					dataOutputManager.saveOutPutBussinessMap(Common
							.StringToLong(taskId), Common
							.StringToLong(businessId));
				}
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intbussinessStatus", 0);
			}
			msg = "自定义数据输出任务[添加业务成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[添加：业务失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	public String taskBusiness() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String business = dataOutputManager
				.queryBussIdAndBussIdTypeByTaskId(Common.StringToInt(taskId));
		if (business != null) {
			String[] businesses = business.split("@");
			request.setAttribute("business", businesses[1]);
			request.setAttribute("businessIds", businesses[0]);
		}
		status = "";
		List outPutColumnMapList = dataOutputManager.isOutPutColumnMap(Common
				.StringToLong(taskId));
		if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
			Object[] obj = (Object[]) outPutColumnMapList.get(0);
			if (obj[7] != null) {
				status = obj[7].toString();
			}
		}
		return "task_business";
	}

	public FtbDataOutPutTask getEntity() {
		return entity;
	}

	public void setEntity(FtbDataOutPutTask entity) {
		this.entity = entity;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	/*
	 * 任务定制-URL
	 */

	// 查询url
	public void queryvcUrl() throws ServletException, IOException {
		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String vcUrl = request.getParameter("vcUrl");
		if (vcUrl != null)
			vcUrl = vcUrl.trim();
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			pageNmMsisdn.setOrder(order);
			pageNmMsisdn.setOrderBy(MantoEyeUtils.getSortField(si
					.getFieldName()));

		} else {
			pageNmMsisdn.setOrderBy("nmId");
			pageNmMsisdn.setOrder("desc");
		}
		pageNmMsisdn.setPageSize(gridServerHandler.getPageSize());
		pageNmMsisdn.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		pageNmMsisdn = dataOutputManager.queryvcUrl(pageNmMsisdn, Common
				.StringToLong(taskId), vcUrl);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = pageNmMsisdn.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = pageNmMsisdn.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	// 删除URL
	public void DelUrl() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String taskId = request.getParameter("taskId");
		String msg = "";
		try {
			dataOutputManager.DelUrlById(ids);
			boolean flag = dataOutputManager.isExistUrl(Common.StringToLong(taskId));
			if (!flag) {
				int value = 0;// 状态为0未设置
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intURLStatus", value);
			}
			msg = "删除成功!";
			commonManagerImpl.log(request, "删除URL成功[主键：" + ids + "]成功!!");
		} catch (Exception e) {
			msg = "删除失败!";
			commonManagerImpl.log(request, "删除URL失败[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public void urlexport() throws ServletException, IOException {
		List<CommonSport> list = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String vcUrl = request.getParameter("vcUrl");
		if (vcUrl != null){
			vcUrl = new String(vcUrl.trim().getBytes("ISO-8859-1"),"UTF-8");
		}
		list = dataOutputManager
				.urlListData(Common.StringToLong(taskId), vcUrl);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "任务定制URL";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	public void checkUrlStatus() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		status = "";
		int num = dataOutputManager.isExistsUrl(Common.StringToLong(taskId));
		// 判断是否已上传过号码
		if (num != 0) {
			List outPutColumnMapList = dataOutputManager
					.isOutPutColumnMap(Common.StringToLong(taskId));

			if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
				Object[] obj = (Object[]) outPutColumnMapList.get(0);
				if (obj[3] != null && !"".equals(obj[3].toString()))
					status = obj[3].toString();
			}
		}
		Struts2Utils.renderText(status);
	}

	/**
	 * 判断该URL是否存在(新增，编辑)
	 * 
	 * @throws IOException
	 */
	public String checkUniqueUrl() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcUrl = request.getParameter("vcUrl");
		String taskId = request.getParameter("taskId");
		if (vcUrl != null)
			vcUrl = vcUrl.trim();
		int i = dataOutputManager.queryUrlByCondition(Common
				.StringToLong(taskId), vcUrl, nmId);

		if (i == -1) {// -1表示不存在该任务名称
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/*
	 * 保存URL
	 */
	public String saveUrl() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String vcUrl = request.getParameter("vcUrl");
		String intUrlStatus = request.getParameter("intUrlStatus");
		String msg = null;
		if (vcUrl != null)
			vcUrl = vcUrl.trim();
		try {
			int status = 0;
			if (intUrlStatus != null && !intUrlStatus.equals("0")) {
				status = Integer.parseInt(intUrlStatus);
			}
			// int num = dataOutputManager
			// .isExistsUrl(Common.StringToLong(taskId));
			// 判断是否已上传过号码
			// if (num == 0) {
			boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
					.StringToLong(taskId));
			int value = status;
			if (!flag) {
				dataOutputManager.saveOutPutColumnMap(Common
						.StringToLong(taskId), "intURLStatus", value);
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intURLStatus", value);
			}
			// }
			List<String> vcUrlList = new ArrayList<String>();
			vcUrlList.add(vcUrl);
			dataOutputManager.savevcUrl(Common.StringToLong(taskId), vcUrlList);
			msg = "自定义数据输出任务[添加URL：" + vcUrl + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[添加URL：" + vcUrl + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 修改URL
	 */
	public String modifyUrl() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String vcUrl = request.getParameter("vcUrl");
		if (vcUrl != null)
			vcUrl = vcUrl.trim();
		String msg = null;
		try {
			dataOutputManager.modifyUrl(Common.StringToLong(taskId), vcUrl,
					nmId);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			msg = "自定义数据输出任务[修改URL：" + vcUrl + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[修改URL：" + vcUrl + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/*
	 * 任务定制-vcImei
	 */

	// 查询vcImei
	public void queryvcImei() throws ServletException, IOException {
		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String vcImei = request.getParameter("vcImei");
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (vcImei != null)
			vcImei = vcImei.trim();
		if (si != null) {
			String order = si.getSortOrder();
			pageNmMsisdn.setOrder(order);
			pageNmMsisdn.setOrderBy(MantoEyeUtils.getSortField(si
					.getFieldName()));

		} else {
			pageNmMsisdn.setOrderBy("nmId");
			pageNmMsisdn.setOrder("desc");
		}
		pageNmMsisdn.setPageSize(gridServerHandler.getPageSize());
		pageNmMsisdn.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		pageNmMsisdn = dataOutputManager.queryvcImei(pageNmMsisdn, Common
				.StringToLong(taskId), vcImei);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = pageNmMsisdn.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = pageNmMsisdn.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	// 删除vcImei
	public void DelvcImei() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String taskId = request.getParameter("taskId");
		String msg = "";
		try {
			dataOutputManager.DelvcImeiById(ids);
			boolean flag = dataOutputManager.isExistIMEI(Common.StringToLong(taskId));
			if (!flag) {
				int value = 0;// 状态为0未设置
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intImeiStatus", value);
			}
			msg = "删除成功!";
			commonManagerImpl.log(request, "删除IMEI成功[主键：" + ids + "]成功!!");
		} catch (Exception e) {
			msg = "删除失败!";
			commonManagerImpl.log(request, "删除IMEI失败[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public void vcImeiexport() throws ServletException, IOException {
		List<CommonSport> list = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String vcImei = request.getParameter("vcImei");
		if (vcImei != null)
			vcImei = vcImei.trim();
		list = dataOutputManager.vcImeiListData(Common.StringToLong(taskId),
				vcImei);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "任务定制IMEI";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	public void checkvcImeiStatus() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		status = "";
		int num = dataOutputManager.isExistsvcImei(Common.StringToLong(taskId));
		// 判断是否已上传过号码
		if (num != 0) {
			List outPutColumnMapList = dataOutputManager
					.isOutPutColumnMap(Common.StringToLong(taskId));
			if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
				Object[] obj = (Object[]) outPutColumnMapList.get(0);
				if (obj[6] != null && !"".equals(obj[6].toString()))
					status = obj[6].toString();
			}
		}
		Struts2Utils.renderText(status);
	}

	/**
	 * 判断该vcImei是否存在(新增，编辑)
	 * 
	 * @throws IOException
	 */
	public String checkUniquevcImei() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcImei = request.getParameter("vcImei");
		String taskId = request.getParameter("taskId");
		if (vcImei != null)
			vcImei = vcImei.trim();
		int i = dataOutputManager.queryvcImeiByCondition(Common
				.StringToLong(taskId), vcImei, nmId);

		if (i == -1) {// -1表示不存在该任务名称
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/*
	 * 保存新增vcImei
	 */
	public String savevcImei() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String vcImei = request.getParameter("vcImei");
		String intImeiStatus = request.getParameter("intImeiStatus");
		String msg = null;
		if (vcImei != null)
			vcImei = vcImei.trim();
		try {
			int status = 0;
			if (intImeiStatus != null && !intImeiStatus.equals("0")) {
				status = Integer.parseInt(intImeiStatus);
			}
			// int num = dataOutputManager.isExistsvcImei(Common
			// .StringToLong(taskId));
			// 判断是否已上传过号码
			// if (num == 0) {
			boolean flag = dataOutputManager.isExistOutPutColumnMap(Common
					.StringToLong(taskId));
			int value = status;
			if (!flag) {
				dataOutputManager.saveOutPutColumnMap(Common
						.StringToLong(taskId), "intImeiStatus", value);
			} else {
				dataOutputManager.updateOutPutColumnMap(Common
						.StringToLong(taskId), "intImeiStatus", value);
			}
			// }
			List<String> vcUrlList = new ArrayList<String>();
			String[] vcImeiStrings = vcImei.split(",");
			for (int i = 0; i < vcImeiStrings.length; i++) {
				vcUrlList.add(vcImeiStrings[i]);
			}
			dataOutputManager.savevcvcImei(Common.StringToLong(taskId),
					vcUrlList);
			msg = "自定义数据输出任务[添加vcImei：" + vcImei + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[添加vcImei：" + vcImei + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 修改vcImei
	 */
	public String modifyvcImei() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String vcImei = request.getParameter("vcImei");
		String msg = null;
		if (vcImei != null)
			vcImei = vcImei.trim();
		try {
			dataOutputManager.modifyvcImei(Common.StringToLong(taskId), vcImei,
					nmId);
			msg = "自定义数据输出任务[修改vcImei：" + vcImei + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "自定义数据输出任务[修改vcImei：" + vcImei + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}
	
	
	public void checkImeiStatus() throws ServletException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		status = "";
		int num = dataOutputManager.isExistsvcImei(Common.StringToLong(taskId));
		// 判断是否已上传过号码
		if (num != 0) {
			List outPutColumnMapList = dataOutputManager
					.isOutPutColumnMap(Common.StringToLong(taskId));
			if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
				Object[] obj = (Object[]) outPutColumnMapList.get(0);
				if (obj[6] != null && !"".equals(obj[6].toString()))
					status = obj[6].toString();
			}
		}
	}
	
	public String addImei() throws ServletException, IOException {
		checkImeiStatus();
		return "task_imei_add";
	}
	
	public String updateImei() throws ServletException, IOException {
		checkImeiStatus();
		return "task_imei_up";
	}
	
	public void checkNumberStatus() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		status = "";
		int num = dataOutputManager.isOutPutMsisdnMap(Common
				.StringToLong(taskId));
		if (num != 0) {
			List outPutColumnMapList = dataOutputManager
					.isOutPutColumnMap(Common.StringToLong(taskId));
			if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
				Object[] obj = (Object[]) outPutColumnMapList.get(0);
				if (obj[2] != null) {
					status = obj[2].toString();
				}
			}
		}
	}
	
	public String addNumber() throws Exception {
		checkNumberStatus();
		return "task_number_add";
	}
	
	public String updateNumber() throws Exception {
		checkNumberStatus();
		return "task_number_up";
	}
	
	public void checkurlStatus() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		status = "";
		int num = dataOutputManager.isExistsUrl(Common.StringToLong(taskId));
		// 判断是否已上传过号码
		if (num != 0) {
			List outPutColumnMapList = dataOutputManager
					.isOutPutColumnMap(Common.StringToLong(taskId));

			if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
				Object[] obj = (Object[]) outPutColumnMapList.get(0);
				if (obj[3] != null && !"".equals(obj[3].toString()))
					status = obj[3].toString();
			}
		}
	}

	public String updateUrl() throws Exception {
		checkurlStatus();
		return "task_url_up";
	}
	
	public String addUrl() throws Exception {
		checkurlStatus();
		return "task_url_add";
	}
}
