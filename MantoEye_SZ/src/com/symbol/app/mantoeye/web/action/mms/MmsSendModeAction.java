package com.symbol.app.mantoeye.web.action.mms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.mms.MmsSendModeManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class MmsSendModeAction extends BaseDispatchAction {
	@Autowired
	private MmsSendModeManager mmsSendModeManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String dataType_search = "1";
	private String timeLevel_search = "2";
	private String time_search = CommonUtils.getSYestoday();

	/**
	 * 按彩信发送方式统计
	 */
	public void query() throws ServletException, IOException {
		List<CommonFlush> list = mmsSendModeManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		if (list == null) {
			list = new ArrayList<CommonFlush>();
		}

		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonFlush> list = mmsSendModeManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_MMS_SENDTYPE
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search
				+ "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public MmsSendModeManager getMmsDistributeManager() {
		return mmsSendModeManager;
	}

	public void setMmsDistributeManager(MmsSendModeManager mmsSendModeManager) {
		this.mmsSendModeManager = mmsSendModeManager;
	}

	public String getDataType_search() {
		return dataType_search;
	}

	public void setDataType_search(String dataType_search) {
		this.dataType_search = dataType_search;
	}

	public String getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(String timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
	}

	public String getTime_search() {
		return time_search;
	}

	public void setTime_search(String time_search) {
		this.time_search = time_search;
	}

}
