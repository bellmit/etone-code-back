package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.UserAttachDistibuteManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class UserAttachDistributeAction extends BaseDispatchAction {

	@Autowired
	private UserAttachDistibuteManager userAttachDistibuteManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String timeLevel = "2";// 时间周期 2:天
	private String total_time_search;// 总体分析过来的时间
	private String total_time_level;// 总体分析过来的时间周期
	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private int dataType = 1;

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		if (!"null".equals(total_time_search)) {
			timeLevel = total_time_level;
			searchDateStart = total_time_search;
		}
		/*
		 * String dataTypeStr=request.getParameter("dataType");
		 * if(dataTypeStr!=null){ dataType=Common.StringToInt(dataTypeStr); }
		 */
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<CommonFlush> list = userAttachDistibuteManager
				.queryUserAttachDistribute(Common.StringToInt(timeLevel),
						searchDateStart, dataType);
		/*
		 * CommonFlush c=new CommonFlush(); c.setUserBelong("省内");
		 * c.setStrUpFlush("4,233"); c.setStrDownFlush("4.222");
		 * c.setStrTotalFlush("8.983"); c.setStrImsi("3.232");
		 * c.setStrVisit("333.34"); list.add(c);
		 */
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		try {
			/*
			 * String dataTypeStr = request.getParameter("dataType"); if
			 * (dataTypeStr != null) { dataType =
			 * Common.StringToInt(dataTypeStr); }
			 */
			// logger.info("----------------"+total_time_search+"--");
			// if(!"null".equals(total_time_search)){
			// timeLevel = total_time_level;
			// searchDateStart=total_time_search;
			// }
			// logger.info("----------------"+searchDateStart+"--"+timeLevel);
			List<CommonFlush> list = userAttachDistibuteManager
					.queryUserAttachDistribute(Common.StringToInt(timeLevel),
							searchDateStart, dataType);
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			String exportmsg = ExportMsg.EXPORT_FLUSH_BELONG
					+ Common.getTimeLevelCH(timeLevel) + "（" + searchDateStart
					+ "）";
			commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
			gridServerHandler.exportXLS(list, CommonFlush.class);
		} catch (Exception e) {
			// logger.error(e.getMessage());
		}
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
