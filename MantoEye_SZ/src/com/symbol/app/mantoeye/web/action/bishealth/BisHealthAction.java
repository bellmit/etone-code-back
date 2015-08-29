package com.symbol.app.mantoeye.web.action.bishealth;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import jxl.write.WritableCellFormat;

import org.apache.derby.client.net.Request;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbNetworkTask1;
import com.symbol.app.mantoeye.service.bishealth.BisHealthManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.export.AbstractXlsWriter;
import com.symbol.wp.tools.gtgrid.export.ExcelStyle;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class BisHealthAction extends BaseDispatchAction {
	private static final long serialVersionUID = -7992128821878284128L;
	@Autowired
	private BisHealthManager bisHealthManager;
	@Resource(name = "commonManagerImpl")
	private ICommonManager commonManagerImpl;

	// ///////////////////////////////////////////以下業務健康度////////////////////////////////////////////////
	private Page<CommonSport> page = new Page<CommonSport>();
	private FtbNetworkTask1 entity;

	private String vcTaskName;
	private String vcTaskOrder;
	private String intTaskStatus = "-1";
	// private String startTime= CommonUtils.getSYestoday() + " 00";// 开始时间;
	// private String endTime= CommonUtils.getSYestoday() + " 23";
	private String startTime;
	private String endTime;

	private int taskType = 6;// 默认首先查询统计结果
	private int timeLevel = 2;

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

	public FtbNetworkTask1 getEntity() {
		return entity;
	}

	public void setEntity(FtbNetworkTask1 entity) {
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

	public void stopTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		String msg = "";
		try {
			bisHealthManager.stopTask(taskId);
			msg = "停止任务[：" + taskId + "]" + "成功!";
			commonManagerImpl.log(request, msg);
		} catch (Exception e) { // 删除失败
			msg = "停止任务[：" + taskId + "]" + "失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}

	public void startTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		String msg = "";
		try {
			bisHealthManager.startTask(taskId);
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
			page.setOrderBy(si.getFieldName());

		} else {
			page.setOrderBy("intYear");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = bisHealthManager.query(page, Common.OutConvert(vcTaskName),
				Common.OutConvert(vcTaskOrder), Common
						.StringToInt(intTaskStatus), startTime, endTime);
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
		if (sort.indexOf("nmFlush") != -1) {
			return "nmFlush";
		}
		if (sort.indexOf("nmOFlush") != -1) {
			return "nmAllFlush";
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
				bisHealthManager.deleteTasks(id);
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
			int i = bisHealthManager.isVcTaskName(vcTaskName);
			if (i == -1) {// -1表示不存在该任务名称
				Struts2Utils.renderText("true");
			} else {
				Struts2Utils.renderText("false");
			}
		} else
			Struts2Utils.renderText("true");
		return null;
	}

	/**
	 * 保存自定义数据输出
	 */
	public String saveTask() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer()
				.getUserInfo();
		String msg = null;
		String[] id = null;
		String businessIds = request.getParameter("businessIds");
		String oldbusinessid = request.getParameter("oldbusiness");
		String businessId = "";
		if (businessIds != null && !"".equals(businessIds)
				&& !businessIds.equals(oldbusinessid)) {

			String[] datas = businessIds.split(",");
			for (String ids : datas) {
				id = ids.split(":");
				businessId += id[1].trim();
			}
			entity.setNmBussinessId(Common.StringToLong(businessId));
		}
		try {
			Long taskId = entity.getNmNetworkTaskId();
			String taskName = entity.getVcTaskName();
			entity.setVcTaskName(Common.OutConvert(taskName));
			if (taskId != null) {// 编辑
				boolean succ = bisHealthManager.editParseTask(entity);
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
				entity.setIntTaskDelong(1);
				bisHealthManager.saveParseTask(entity);

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
	 * 编辑任务
	 */
	public String edit() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		String keyid = request.getParameter("keyid");
		entity = bisHealthManager.get(Common.StringToLong(keyid));
		String bisname = null;
		long bussinessid = 0;
		int bisTypeId = 0;
		if (entity.getNmBussinessId() != null) {
			bussinessid = entity.getNmBussinessId();
			String business = bisHealthManager.FindBisById(bussinessid);
			if (business != null) {
				String[] businesses = business.split("@");
				request.setAttribute("business", businesses[1]);
				request.setAttribute("businessIds", businesses[0]);
			}
		}

		return EDIT;
	}

	public void queryDetail() throws ServletException, IOException {
		if(taskType==6)
	return;
		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("intYear");
			page.setOrder("desc");

		}

		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		page = bisHealthManager.queryDetail(page, taskType, taskId);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	public String queryNetWorkResult() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		List<CommonSport> list = bisHealthManager.queryNetworkResult(taskId);
		CommonSport data = list.get(list.size() - 1);
		list.remove(list.size() - 1);
		request.setAttribute("networkResultList", list);
		request.setAttribute("networkResultSum",data.getSum());
		return "NETWORKTESULT";
	}

	public void export() throws ServletException, IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		List<CommonSport> list = null;
		List<String[]> networkResultList = null;
		list = bisHealthManager.listData(taskType, taskId);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "任务健康查询结果";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonSport.class);

	}

	public void exportNetworkResult() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String[] headsName = { "业务", "时间", "KQI", "计算值", "权重", "TO值" };
		String fileName = "";

		fileName = "任务健康结果统计";
		List<CommonSport> list = bisHealthManager.queryNetworkResult(taskId);

		gridServerHandler.downloadFile(fileName + ".xls");

		ExcelStyle styles = new ExcelStyle();
		WritableCellFormat titlestyle = styles.getTableYellow();

		OutputStream out = this.getServletResponse().getOutputStream();
		AbstractXlsWriter xlsw = gridServerHandler.getXlsWriter();
		xlsw.init();
		xlsw.setOut(out);
		xlsw.setEncoding(gridServerHandler.getEncoding());
		xlsw.start();
		xlsw.addTitle(headsName, titlestyle, "");
		for (int i = 0; i < list.size()-1; i++) {
			CommonSport commonSport = list.get(i);
			String[] data = { commonSport.getBusinessName(),
					commonSport.getStatdate(), commonSport.getVcName(),
					commonSport.getIntCount(), commonSport.getIntScale(),
					commonSport.getIntMark()  };
			xlsw.addRow(data, styles.getTable());
		}
		String sum=list.get(list.size()-1).getSum()+"";
String[] lastData= {"业务健康度","", "","","",sum};
xlsw.addRow(lastData,  styles.getTable());
		xlsw.end();
		xlsw.close();
	}

	public String add() throws Exception {
		return ADD;
	}

	// ///////////////////////////////////////////////以上任务健康度////////////////////////////////////////////////////////////

}
