package com.symbol.app.mantoeye.web.action.blackberry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.blackberry.Top100AreaFlushManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class Top100AreaFlushAction extends BaseDispatchAction {

	@Autowired
	private Top100AreaFlushManager top100AreaFlushManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;
	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private int timeLevel_search = 2;

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {

		List<CommonFlush> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		logger.info("searchDateStart:" + searchDateStart);
		try {
			list = top100AreaFlushManager.query(searchDateStart,
					timeLevel_search);
			gridServerHandler.setData(this.formatViewData(list));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<CommonFlush> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		list = top100AreaFlushManager.query(searchDateStart, timeLevel_search);
		String exportmsg = ExportMsg.EXPORT_BLACKBREEY_FLUSH_TOP100
				+ Common.getTimeLevelCH(timeLevel_search + "") + "（"
				+ searchDateStart + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatViewData(List<CommonFlush> list) {

		List maplist = new ArrayList();
		Map map = null;
		CommonFlush bean;
		try {
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (CommonFlush) list.get(i);
					map = new LinkedHashMap();
					map.put("top", bean.getTop());
					map.put("fullDate", bean.getFullDate());
					map.put("cgiName", bean.getCgiName());
					map.put("cgi", bean.getCgi());
					map.put("fullDate", bean.getFullDate());
					map.put("totalFlushMB", bean.getTotalFlushMB());
					map.put("intImsis", bean.getIntImsis());
					map.put("area", bean.getArea());
					map.put("street", bean.getStreet());
					map.put("subsidiaryCompany", bean.getSubsidiaryCompany());
					map.put("bsc", bean.getBsc());
					map.put("sgsn", bean.getSgsn());
					map.put("saleArea", bean.getSaleArea());
					map.put("nmAveFlushKB", bean.getNmAveFlushKB());
					maplist.add(map);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return maplist;
	}

	public int getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(int timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
	}

	public String getSearchDateStart() {
		return searchDateStart;
	}

	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}

}
