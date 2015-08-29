package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.AreasBusinessTrendManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.web.action.Phone;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class AreasBusinessTrendAction extends BaseAction {

	private static final long serialVersionUID = 264970860388207518L;
	@Autowired
	private AreasBusinessTrendManager areasBusinessTrendManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String timeLevel = "1";
	private String areaType = "1";
	private String areaValue;
	private String startTime_trend = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_trend = CommonUtils.getSYestoday() + " 23";// 结束时间

	public String getStartTime_trend() {
		return startTime_trend;
	}

	public void setStartTime_trend(String startTimeTrend) {
		startTime_trend = startTimeTrend;
	}

	public String getEndTime_trend() {
		return endTime_trend;
	}

	public void setEndTime_trend(String endTimeTrend) {
		endTime_trend = endTimeTrend;
	}

	public void query() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<CommonFlush> list = areasBusinessTrendManager.queryByPage(
				gridServerHandler, timeLevel, areaType, Common
				.OutConvert(areaValue),
				startTime_trend, endTime_trend).getResult();

		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void export() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		areaValue = new String(areaValue.getBytes("ISO-8859-1"), "UTF-8");
		logger.info(areaValue);
		List<CommonFlush> list = areasBusinessTrendManager.queryAll(
				timeLevel, areaType, Common
				.OutConvert(areaValue), startTime_trend, endTime_trend);
		String exportmsg = ExportMsg.EXPORT_TREND_SPACE
				+ Common.getTimeLevelCH(timeLevel) + "（" + startTime_trend
				+ "~" + endTime_trend + "）" + areaValue;
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public void getAjaxChartXmlData() throws Exception {
		logger.info("getAjaxChartXmlData------------------");
		List<CommonFlush> list = areasBusinessTrendManager.queryAll(
				timeLevel, areaType, areaValue, startTime_trend, endTime_trend);

		JSONArray ja = JSONUtils.BeanList2JSONArray(list, CommonFlush.class);
		Struts2Utils.renderJson(ja.toString());

	}

	/**
	 * 初始化区域弹出框
	 */
	public void initAreaDialogData() throws Exception {
		Struts2Utils.renderXml(packAreaInfo2());
	}

	private String packAreaInfo2() {
		List<Phone> list = new ArrayList<Phone>();
		logger.info("action----------11111111111111111111");
		Map<String, Map<String, String>> map = areasBusinessTrendManager
				.bulidAreaAndType();
		logger.info("action----------test22222222222222222222");
		List<String> areaList = new ArrayList<String>(map.keySet());
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml = xml + "<root>";
		if (areaList != null && !areaList.isEmpty()) {
			for (int i = 0; i < areaList.size(); i++) {
				String areanAndId = areaList.get(i);
				Map<String, String> mapType = map.get(areanAndId);
				String[] str = areanAndId.split(":");// 分离品牌名称和Id

				Set setType = mapType.keySet();
				Collection con = mapType.values();
				String modle = null;
				String typeIds = null;
				if (setType != null) {
					modle = getMapKeyStr(mapType);
					typeIds = getMapValueStr(mapType);
				}
				xml = xml + "<data>" + "<brandId>" + str[1] + "</brandId>"
						+ "<brandName>" + str[0] + "</brandName>" + "<modelId>"
						+ typeIds + "</modelId>" + "<modelName>" + modle
						+ "</modelName>" + "</data>";
			}
		}
		xml = xml + "</root>";
		return xml;
	}
	
	private String getMapKeyStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for(Iterator it=setType.iterator();it.hasNext();){
				str = str+","+it.next();
			}
			if(str.indexOf(",")!=-1){
				str = str.substring(1);
			}		
		}
		return str;
	}

	private String getMapValueStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for(Iterator<String> it=setType.iterator();it.hasNext();){
				str = str+","+map.get(it.next());
			}
			if(str.indexOf(",")!=-1){
				str = str.substring(1);
			}		
		}
		return str;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getAreaValue() {
		return areaValue;
	}

	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}
}
