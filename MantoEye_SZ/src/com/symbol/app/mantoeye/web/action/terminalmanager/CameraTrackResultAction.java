package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.CameraTrackResultManager;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class CameraTrackResultAction extends BaseDispatchAction {
	private static final long serialVersionUID = -7992128821878284128L;

	HttpServletRequest request = ServletActionContext.getRequest();
	@Autowired
	private CameraTrackResultManager cameraTrackResultManager;
	@Resource(name = "commonManagerImpl")
	private ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private long taskId;	//任务ID
	private String taskStartTime;	//任务开始时间
	private long intTaskStatus;		//任务状态
	private int dataType = 1;	//数据类型,默认查询GPRS
	

	private String queryTaskStartTime;	//任务开始查询时间

	private int areaId;// 区域ID
	private int areaType;// 区域类型ID
	private int terminalId;
	private int bussinessId;

	// ///////////////////////////////////【通用拍照】区域分析流量表/////////////////////////////////////
	/*
	 * 
	 * 對應表ftbStatSpeAreaFlush_
	 */
	public void queryAreaAnalyse() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmStatSpeAreaId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		if (areaType==0) {//默认设置为1
			areaType=1;
		}
		if(!StringUtils.isEmpty(queryTaskStartTime)){
			taskStartTime = queryTaskStartTime;
		}
		page = cameraTrackResultManager.queryAreaAnalyse(page, taskId,areaType, taskStartTime, intTaskStatus, dataType);
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
		if (sort.indexOf("fullDate") != -1) 
			return "dtStatTime";
		
		if (sort.indexOf("upFlush") != -1) {
			return "nmUpFlush";
		}
		if (sort.indexOf("downFlush") != -1) {
			return "nmDownFlush";
		}
		if (sort.indexOf("totalFlush") != -1) {
			return "sumFlush";
		}

		return sort;
	}

	public void exportAreaAnalyse() throws ServletException, IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
//		long taskId = Common.StringToLong(request.getParameter("taskId"));
		List<CommonSport> list = null;
		List<String[]> networkResultList = null;
		if (areaType==0) {//默认设置为1
			areaType=1;
		}
		if(!StringUtils.isEmpty(queryTaskStartTime)){
			taskStartTime = queryTaskStartTime;
		}
		list = cameraTrackResultManager.AreaAnalyselistData(taskId,areaType, taskStartTime, intTaskStatus, dataType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "区域分析查询结果";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonSport.class);

	}

	// ///////////////////////////////////【通用拍照】业务分析流量表/////////////////////////////////////
	/*
	 * 
	 * 對應表ftbStatSpeBussFlush_
	 */
	public void queryBisAnalyse() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("sumFlush");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		if(!StringUtils.isEmpty(queryTaskStartTime)){
			taskStartTime = queryTaskStartTime;
		}
		page = cameraTrackResultManager.queryBisAnalyse(page, taskId, taskStartTime, intTaskStatus, dataType);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void exportBisAnalyse() throws ServletException, IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
//		long taskId = Common.StringToLong(request.getParameter("taskId"));
		List<CommonSport> list = null;
		List<String[]> networkResultList = null;
		if(!StringUtils.isEmpty(queryTaskStartTime)){
			taskStartTime = queryTaskStartTime;
		}
		list = cameraTrackResultManager.bisAnalyselistData(taskId, taskStartTime, intTaskStatus, dataType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "业务查询结果";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonSport.class);

	}

	// ///////////////////////////////////【通用拍照】终端分析流量表/////////////////////////////////////
	/*
	 * 
	 * 對應表ftbStatSpeTerminalFlush_
	 */
	public void queryTerminalAnalyse() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmStatSpeTerminalId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		if(!StringUtils.isEmpty(queryTaskStartTime)){
			taskStartTime = queryTaskStartTime;
		}
		page = cameraTrackResultManager.queryTerminalAnalyse(page, taskId, taskStartTime, intTaskStatus, dataType);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void exportTerminalAnalyse() throws ServletException, IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
//		long taskId = Common.StringToLong(request.getParameter("taskId"));
		List<CommonSport> list = null;
		List<String[]> networkResultList = null;
		if(!StringUtils.isEmpty(queryTaskStartTime)){
			taskStartTime = queryTaskStartTime;
		}
		list = cameraTrackResultManager.TerminalAnalyselistData(taskId, taskStartTime, intTaskStatus, dataType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端分析查询结果";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonSport.class);

	}

	// ///////////////////////////////////【通用拍照】区域-终端分析流量表/////////////////////////////////////
	/*
	 * 
	 * 對應表ftbStatSpeAreaTerminalFlush_
	 */
	public void queryAreaTerminal() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmStatSpeAreaTerminalId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = cameraTrackResultManager.queryAreaTerminal(page, taskId,
				areaType, areaId, terminalId, taskStartTime, dataType);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void exportAreaTerminal() throws ServletException, IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		String taskStartTime = request.getParameter("taskStartTime");
		int areaType = Common.StringToInt(request.getParameter("areaType"));
		int areaId = Common.StringToInt(request.getParameter("areaId"));
		int terminalId = Common.StringToInt(request.getParameter("terminalId"));
		List<CommonSport> list = null;
		List<String[]> networkResultList = null;
		list = cameraTrackResultManager.areaTerminallistData(taskId, areaType,
				areaId, terminalId, taskStartTime, dataType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "区域分析询结果";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonSport.class);

	}

	// ///////////////////////////////////［通用拍照］区域-业务分析流量表///////////////////////////////////
	/*
	 * 
	 * 對應表ftbStatSpeAreaBussFlush_
	 */
	public void queryAreaBis() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmStatSpeAreaBussId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = cameraTrackResultManager.queryAreaBis(page, taskId,
				areaType, areaId, bussinessId, taskStartTime, dataType);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void exportAreaBis() throws ServletException, IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		String taskStartTime = request.getParameter("taskStartTime");
		int areaType = Common.StringToInt(request.getParameter("areaType"));
		int areaId = Common.StringToInt(request.getParameter("areaId"));
		int bussinessId = Common.StringToInt(request.getParameter("bussinessId"));
		List<CommonSport> list = null;
		List<String[]> networkResultList = null;
		list = cameraTrackResultManager.areaBislistData(taskId, areaType,
				areaId, bussinessId, taskStartTime, dataType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "区域业务查询结果";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonSport.class);

	}

	
	

	// ///////////////////////////////////【通用拍照】终端-业务分析流量表///////////////////////////////////
	/*
	 * 
	 * 對應表ftbStatSpeTerminalBussFlush_
	 */
	public void queryTerminalBis() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmStatSpeTerminalBussId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = cameraTrackResultManager.queryTerminalBis(page, taskId,terminalId, bussinessId,taskStartTime, dataType);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void exportTerminalBis() throws ServletException, IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		long taskId = Common.StringToLong(request.getParameter("taskId"));
		String taskStartTime = request.getParameter("taskStartTime");
		int terminalId = Common.StringToInt(request.getParameter("terminalId"));
		int bussinessId = Common.StringToInt(request.getParameter("bussinessId"));
		List<CommonSport> list = null;
		List<String[]> networkResultList = null;
		list = cameraTrackResultManager.terminalBislistData(taskId, terminalId, bussinessId,taskStartTime, dataType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端业务查询结果";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonSport.class);

	}
	public int getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(int bussinessId) {
		this.bussinessId = bussinessId;
	}

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	
	public String getQueryTaskStartTime() {
		return queryTaskStartTime;
	}

	public void setQueryTaskStartTime(String queryTaskStartTime) {
		this.queryTaskStartTime = queryTaskStartTime;
	}

	public long getIntTaskStatus() {
		return intTaskStatus;
	}

	public void setIntTaskStatus(long intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}

	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
}
