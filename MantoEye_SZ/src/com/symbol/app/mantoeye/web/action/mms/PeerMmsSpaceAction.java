package com.symbol.app.mantoeye.web.action.mms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.dto.mms.PeerMmsBean;
import com.symbol.app.mantoeye.service.mms.PeerMmsManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.DataFormatUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 点对点彩信空间分布
 * 
 * @author rankin
 * 
 */
public class PeerMmsSpaceAction extends BaseDispatchAction {

	@Autowired
	private PeerMmsManager peerMmsManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	private String stattype = "2";// 粒度

	private String searchdate = CommonUtils.getSYestoday();// 时间点

	private int raittype = 1;// 1－gprs 2－TD

	private int spaceLevel = 1;//

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

	public int getSpaceLevel() {
		return spaceLevel;
	}

	public void setSpaceLevel(int spaceLevel) {
		this.spaceLevel = spaceLevel;
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

			list = peerMmsManager.findPeerMmsSpace(raittype, spaceLevel,
					stattype, searchdate);

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
		list = peerMmsManager.findPeerMmsSpace(raittype, spaceLevel, stattype,
				searchdate);
		String exportmsg = ExportMsg.EXPORT_MMS_PEERAREA
				+ Common.getTimeLevelCH(stattype) + "（" + searchdate + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxChartXmlData() throws Exception {

		logger.info("stattype:" + stattype + "--searchdate:" + searchdate
				+ "--raittype:" + raittype + "--exportFileName"
				+ exportFileName);
		if (this.searchdate == null) {
			this.searchdate = CommonUtils.getSYestoday();
		}
		String spaceName;
		List<PeerMmsBean> list = null;
		list = peerMmsManager.findPeerMmsSpace(raittype, spaceLevel, stattype,
				searchdate);
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			spaceName = "BSC";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN) {
			spaceName = "SGSN";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			spaceName = "街道办";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			spaceName = "营销片区";
		} else {
			spaceName = "分公司";
		}
		String titletime = searchdate;
		if (Common.StringToInt(stattype) == 3) {
			titletime = CommonUtils.getDayInnerWeek(searchdate);
		}
		String chartTitle = "\"" + titletime + " " + spaceName
				+ "{dataname}分布\"";

		// 去掉名称为其他的数据
		list = DataFormatUtils.exceptMmsFlush(list, "其他");
		// 根据数量级获取流量的单位，防止数量太大
		String sendUnit = DataFormatUtils.getSendUnit1(list);

		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + "<Data>"
				+ "<Stat id=\"\" unit=\"" + sendUnit + "\" tipunit=\"\" title="
				+ chartTitle + " trip=\"彩信发送量{br}" + spaceName
				+ "：{name}{br}发送量：{total}" + sendUnit + "\">";
		if (list != null && !list.isEmpty()) {
			for (PeerMmsBean c : list) {
				xml = xml + "<Statdata name=\"" + c.getDimensionName()
						+ "\" total=\""
						+ DataFormatUtils.getIVByUnit1(c, 0, sendUnit, "send")
						+ "\"/>";
			}
		}

		// String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
		// + "<Data>"
		// + "<Stat id=\"\" unit=\"\" title="
		// + chartTitle
		// +
		// " trip=\"点对点彩信发送：{br}"+spaceName+"：{name}{br}总量：{total}{br}上行量：{up}{br}下流量：{down}\">";
		// if (list != null && !list.isEmpty()) {
		// for (PeerMmsBean c : list) {
		// xml = xml + "<Statdata name=\"" + c.getDimensionName()
		// + "\" total=\"" + c.getTotalCount() + "\" up=\""
		// + c.getUpCount() + "\" down=\"" + c.getDownCount()
		// + "\"/>";
		// }
		// }
		xml = xml + "</Stat>" + "</Data>";
		// logger.info("---------xml-------" + xml);
		Struts2Utils.renderText(xml);
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
					map.put("spaceName", bean.getDimensionName());
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
