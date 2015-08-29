package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.TopStat;
import com.symbol.app.mantoeye.service.businessRule.TopFlushAndImsiManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.export.ExcelMergeBean;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class TopFlushAction extends BaseDispatchAction {

	@Autowired
	private TopFlushAndImsiManager topFlushAndImsiManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	private String stattype = "2";// 粒度

	private String searchdate = CommonUtils.getSYestoday();// 时间点

	private int raittype = 1;// 0－gprs 1－TD

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getStattype() {
		return stattype;
	}

	public void setStattype(String stattype) {
		this.stattype = stattype;
	}

	public String getSearchdate() {
		return searchdate;
	}

	public void setSearchdate(String searchdate) {
		this.searchdate = searchdate;
	}

	public int getRaittype() {
		return raittype;
	}

	public void setRaittype(int raittype) {
		this.raittype = raittype;
	}

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		logger.info("stattype:" + stattype + "--searchdate:" + searchdate
				+ "--raittype:" + raittype);
		List<TopStat> list = null;
		if (this.searchdate == null) {
			this.searchdate = CommonUtils.getSYestoday();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {

			list = topFlushAndImsiManager.findTopStatFlush(raittype, stattype,
					searchdate);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		gridServerHandler.setData(list, TopStat.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		logger.info("stattype:" + stattype + "--searchdate:" + searchdate
				+ "--raittype:" + raittype + "--exportFileName"
				+ exportFileName);
		if (this.searchdate == null) {
			this.searchdate = CommonUtils.getSYestoday();
		}
		List<TopStat> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		list = topFlushAndImsiManager.findTopStatFlush(raittype, stattype,
				searchdate);
		try {
			String exportmsg = ExportMsg.EXPORT_FLUSH_TOPFLUSH + "（" + searchdate + "）";
			commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

			gridServerHandler.exportXLS(list, TopStat.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

}
