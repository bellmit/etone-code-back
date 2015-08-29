package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbStatTerminalChangeTask;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalUpgradeTaskManager;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class TerminalUpgradeTaskAction extends BaseDispatchAction {
	private static final long serialVersionUID = -7992128821878284128L;
	@Autowired
	private TerminalUpgradeTaskManager terminalUpgradeTaskManager ;
	@Resource(name = "commonManagerImpl")
	private ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>();
	private FtbStatTerminalChangeTask entity;

	private String vcTaskName;
	private String vcTaskOrder;
	private String intTaskStatus="-1";
	// private String startTime= CommonUtils.getSYestoday() + " 00";// 开始时间;
	// private String endTime= CommonUtils.getSYestoday() + " 23";
	private String startTime;
	private String endTime;

	private int taskType = 1;
	private int timeLevel = 2;
private String TerminalChangeId;
	public int getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(int timeLevel) {
		this.timeLevel = timeLevel;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public FtbStatTerminalChangeTask getEntity() {
		return entity;
	}

	public void setEntity(FtbStatTerminalChangeTask entity) {
		this.entity = entity;
	}

	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	public String getVcTaskName() {
		return vcTaskName;
	}

	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
	}

	public String getVcTaskOrder() {
		return vcTaskOrder;
	}

	public void setVcTaskOrder(String vcTaskOrder) {
		this.vcTaskOrder = vcTaskOrder;
	}

	public String getIntTaskStatus() {
		return intTaskStatus;
	}

	public void setIntTaskStatus(String intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTerminalChangeId() {
		return TerminalChangeId;
	}

	public void setTerminalChangeId(String terminalChangeId) {
		TerminalChangeId = terminalChangeId;
	}

	public void stopTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		String msg = "";
		try {
			terminalUpgradeTaskManager.stopTask(taskId);
			msg = "停止终端升级任务[：" + taskId + "]" + "成功!";
			commonManagerImpl.log(request, msg);
		} catch (Exception e) { // 删除失败
			msg = "停止终端升级任务[：" + taskId + "]" + "失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}

	public void startTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		String msg = "";
		try {
			terminalUpgradeTaskManager.startTask(taskId);
			msg = "开始任务[：" + taskId + "]" + "成功!";
			commonManagerImpl.log(request, msg);
		} catch (Exception e) { // 删除失败
			msg = "开始任务[：" + taskId + "]" + "失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}

	/**
	 * 查询原始数据
	 * 
	 */
	public void query() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmTerminalChangeIdTaskId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = terminalUpgradeTaskManager.query(page, Common.OutConvert(vcTaskName),
				Common.OutConvert(vcTaskOrder), Common
						.StringToInt(intTaskStatus), startTime, endTime,Common.StringToLong(TerminalChangeId));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();

		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	private String getSortField(String sort) {
		if (sort.indexOf("dtStartTime") != -1) {
			return "dtTaskStartTime";
		}
		if (sort.indexOf("dtEndTime") != -1) {
			return "dtTaskEndTime";
		}
		return sort;
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
				terminalUpgradeTaskManager.deleteTasks(id);
			}
			msg = "删除任务成功!";
			commonManagerImpl.log(request, "删除终端升级任务[ID：" + taskIds + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除任务失败!";
			logger.error(e.toString());
			logger.error(e.getMessage());
			commonManagerImpl.log(request, "删除终端升级任务[ID：" + taskIds + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	/**
	 * 停止任务
	 */

	/**
	 * 判断该任务是否存在
	 * 
	 * @throws IOException
	 */
	public String checkUnique() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcTaskName = request.getParameter("vcTaskName").trim();
		String oldTaskName = request.getParameter("oldTaskName");
		if ((oldTaskName != null && !vcTaskName.equals(oldTaskName))
				|| oldTaskName == null) {
			int i = terminalUpgradeTaskManager.isVcTaskName(vcTaskName);
			if (i == -1) {// -1表示不存在该任务名称
				Struts2Utils.renderText("true");
			} else {
				Struts2Utils.renderText("false");
			}
		} else
			Struts2Utils.renderText("true");
		return null;
	}


	public String saveTask() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer()
				.getUserInfo();
		String msg = null;
	String TerminalChangeId=request.getParameter("TerminalChangeId");
		try {
			Long taskId = entity.getNmTerminalChangeIdTaskId();
			String taskName = entity.getVcTaskName();
			entity.setVcTaskName(Common.OutConvert(taskName));
			if (taskId != null) {// 编辑
				boolean succ = terminalUpgradeTaskManager.editParseTask(entity);
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
				entity.setNmTerminalChangeId(Common.StringToLong(TerminalChangeId));
				terminalUpgradeTaskManager.saveParseTask(entity);

			}

			msg = "终端升级任务[名称：" + entity.getVcTaskName() + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "终端升级任务[名称：" + entity.getVcTaskName() + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	} 

	/**
	 * 编辑任务
	*/
	public String edit() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		String keyid = request.getParameter("keyid");
		entity = terminalUpgradeTaskManager.get(Common.StringToLong(keyid));
		return EDIT;
	}
 

	public String add() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String TerminalChangeId = request.getParameter("TerminalChangeId");
		request.setAttribute("TerminalChangeId", TerminalChangeId);
		return ADD;
	}


}
