package com.symbol.app.mantoeye.web.action.mms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.mms.SpMmsFlushLevelManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class SpMmsFlushLevelAction extends BaseDispatchAction {
	@Autowired
	private SpMmsFlushLevelManager spMmsFlushLevelManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String dataType_search = "1";// GPRS
	private String time_search = CommonUtils.getSYestoday();// 时间

	public SpMmsFlushLevelManager getSpMmsFlushLevelManager() {
		return spMmsFlushLevelManager;
	}

	public void setSpMmsFlushLevelManager(
			SpMmsFlushLevelManager spMmsFlushLevelManager) {
		this.spMmsFlushLevelManager = spMmsFlushLevelManager;
	}

	public String getDataType_search() {
		return dataType_search;
	}

	public void setDataType_search(String dataType_search) {
		this.dataType_search = dataType_search;
	}

	public String getTime_search() {
		return time_search;
	}

	public void setTime_search(String time_search) {
		this.time_search = time_search;
	}

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		List<CommonFlush> list = spMmsFlushLevelManager.listData(Integer
				.parseInt(dataType_search), time_search);
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
		List<CommonFlush> list = spMmsFlushLevelManager.listData(Integer
				.parseInt(dataType_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_MMS_SPFLUSHLEVEL+"（"+time_search+"）";
		commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}
}
