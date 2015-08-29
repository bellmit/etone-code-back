package com.symbol.app.mantoeye.web.action.mms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.mms.PeerMmsBean;
import com.symbol.app.mantoeye.service.mms.PeerMmsManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 点对点彩信号段分布
 */
public class PeerMmsSegAction extends BaseDispatchAction {

	@Autowired
	private PeerMmsManager peerMmsManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	private String stattype = "2";// 粒度

	private String searchdate = CommonUtils.getSYestoday();// 时间点

	private int raittype = 1;// 1－gprs 2－TD

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
		List<PeerMmsBean> list = null;
		if (this.searchdate == null) {
			this.searchdate = CommonUtils.getSYestoday();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {

			list = peerMmsManager
					.findPeerMmsSeg(raittype, stattype, searchdate);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		gridServerHandler.setData(this.formatViewData(list));
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
		List<PeerMmsBean> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		list = peerMmsManager.findPeerMmsSeg(raittype, stattype, searchdate);
		String exportmsg = ExportMsg.EXPORT_MMS_PEERSEG
				+ Common.getTimeLevelCH(stattype) + "（" + searchdate + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatViewData(List list) {
		List maplist = new ArrayList();
		Map map = null;
		PeerMmsBean bean;
		try {

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (PeerMmsBean) list.get(i);
					map = new LinkedHashMap();
					map.put("upCount", bean.getUpCount());
					map.put("downCount", bean.getDownCount());
					map.put("totalCount", bean.getTotalCount());
					map.put("fullDate", bean.getFullDate());
					map.put("segName", bean.getDimensionName());
					map.put("percentUpCount", bean.getPercentUpCount());
					map.put("percentDownCount", bean.getPercentDownCount());
					map.put("percentTotalCount", bean.getPercentTotalCount());
					maplist.add(map);
				}
				// logger.info(map.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return maplist;
	}

}
