package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.ApnDistributeManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseOpLog;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class ApnDistributeAction extends BaseDispatchAction {
	@Autowired
	private ApnDistributeManager apnDistributeManager;

	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<TbBaseOpLog> page = new Page<TbBaseOpLog>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	HttpServletRequest request = Struts2Utils.getRequest();
	private String timeLevel = "2";// 时间周期 2:天
	private String total_time_search;// 总体分析过来的时间
	private String total_time_level;// 总体分析过来的时间周期
	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private int dataType = 1;

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		logger.info("APN total_time_search:" + total_time_search);
		if (!"null".equals(total_time_search)) {
			timeLevel = total_time_level;
			searchDateStart = total_time_search;
		}
		List<CommonFlush> list = apnDistributeManager.queryApnDistribute(Common
				.StringToInt(timeLevel), searchDateStart, dataType);
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {

		List<CommonFlush> list = apnDistributeManager.queryApnDistribute(Common
				.StringToInt(timeLevel), searchDateStart, dataType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_FLUSH_APN
				+ Common.getTimeLevelCH(timeLevel) + "（" + searchDateStart
				+ "）";
		commonManagerImpl.log(request, exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public String getTotal_time_search() {
		return total_time_search;
	}

	public void setTotal_time_search(String total_time_search) {
		this.total_time_search = total_time_search;
	}

	public String getTotal_time_level() {
		return total_time_level;
	}

	public void setTotal_time_level(String total_time_level) {
		this.total_time_level = total_time_level;
	}

	public String getSearchDateStart() {
		return searchDateStart;
	}

	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}

}
