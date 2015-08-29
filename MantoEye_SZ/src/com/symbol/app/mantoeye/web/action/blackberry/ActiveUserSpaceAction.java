package com.symbol.app.mantoeye.web.action.blackberry;

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
import com.symbol.app.mantoeye.constants.SessionConstants;
import com.symbol.app.mantoeye.dto.blackberry.ActiveUserSpaceBean;
import com.symbol.app.mantoeye.service.blackberry.BlackBerryActiveUserManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.DataFormatUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class ActiveUserSpaceAction extends BaseDispatchAction {

	@Autowired
	private BlackBerryActiveUserManager blackBerryActiveUserManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	private String stattype = "4";// 粒度 月

	private String searchdate = CommonUtils.getLastMonth();// 时间点

	private int spaceLevel = 1;//
	
	private String conditionType;//过滤类型   运营商(2)or国家(1)
	
	private String countryId;//国家ID
	
	private String ldcId;//运营商ID
	
	private String condition;//活跃度操作符 >(1) or =(3) or <(2)
	
	private String conditionVal;//活跃度过滤值
	
	

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getLdcId() {
		return ldcId;
	}

	public void setLdcId(String ldcId) {
		this.ldcId = ldcId;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConditionVal() {
		return conditionVal;
	}

	public void setConditionVal(String conditionVal) {
		this.conditionVal = conditionVal;
	}

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
		try{
		List<ActiveUserSpaceBean> list = null;
		if (this.searchdate == null) {
			this.searchdate = CommonUtils.getLastMonth();
		}
		logger.info("stattype:"+stattype+"--searchdate:"+searchdate);
		logger.info("conditionType:"+conditionType+"   countryId:"+countryId+"  ldcId:"+ldcId);
		logger.info("condition:"+condition+"   conditionVal:"+conditionVal);
		int condVal = Common.StringToInt2(conditionVal);
		Long cid = Common.StringToLong(countryId);
		Long lid = Common.StringToLong(ldcId);
		if(condVal<0||condVal>31){//当数据非法时，取值为20
			condVal = 20;
		}
		if(!Common.judgeString(conditionType)){
			conditionType = "1";
			cid = 101L;
		}
		if(!Common.judgeString(condition)){
			condition = "1";
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		
		try {

			list = blackBerryActiveUserManager.findActiveUserSpace(spaceLevel,
					searchdate,conditionType,cid,lid,condition,condVal);
			

			String sxml = buildChartStr(list);
			this.getServletRequest().getSession().setAttribute(
					SessionConstants.SESSION_DATA_BB_ACTIVE_SPACE, sxml);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	public void getAjaxChartXmlData() throws Exception {
		String xml = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_BB_ACTIVE_SPACE)
				+ "";
		Struts2Utils.renderText(xml);
	}
	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		logger.info("stattype:" + stattype + "--searchdate:" + searchdate
				+ "--exportFileName" + exportFileName);
		List<ActiveUserSpaceBean> list = null;
		if (this.searchdate == null) {
			this.searchdate = CommonUtils.getLastMonth();
		}
		int condVal = Common.StringToInt2(conditionVal);
		Long cid = Common.StringToLong(countryId);
		Long lid = Common.StringToLong(ldcId);
		if(condVal<0||condVal>31){//当数据非法时，取值为20
			condVal = 20;
		}
		if(!Common.judgeString(conditionType)){
			conditionType = "1";
		}
		if(!Common.judgeString(condition)){
			condition = "1";
		}
		logger.info("stattype:"+stattype+"--searchdate:"+searchdate);
		logger.info("conditionType:"+conditionType+"   countryId:"+countryId+"  ldcId:"+ldcId);
		logger.info("condition:"+condition+"   conditionVal:"+conditionVal);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {

			list = blackBerryActiveUserManager.findActiveUserSpace(spaceLevel,
					searchdate,conditionType,cid,lid,condition,condVal);
			String exportmsg = ExportMsg.EXPORT_BLACKBREEY_ACTIVESPACE+"（"+searchdate+"）";
			commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	/**
	 * 获取图形数据
	 */
	public String buildChartStr(List<ActiveUserSpaceBean> list) {

		logger.info("stattype:" + stattype + "--searchdate:" + searchdate);
//		if (this.searchdate == null) {
//			this.searchdate = CommonUtils.getLastMonth();
//		}
		String spaceName;
//		List<ActiveUserSpaceBean> list = null;
//		int condVal = Common.StringToInt2(conditionVal);
//		Long cid = Common.StringToLong(countryId);
//		Long lid = Common.StringToLong(ldcId);
//		if(condVal<0||condVal>31){//当数据非法时，取值为20
//			condVal = 20;
//		}
//		if(!Common.judgeString(conditionType)){
//			conditionType = "1";
//		}
//		if(!Common.judgeString(condition)){
//			condition = "1";
//		}
//		logger.info("stattype:"+stattype+"--searchdate:"+searchdate);
//		logger.info("conditionType:"+conditionType+"   countryId:"+countryId+"  ldcId:"+ldcId);
//		logger.info("condition:"+condition+"   conditionVal:"+conditionVal);
//		list = blackBerryActiveUserManager.findActiveUserSpace(spaceLevel,
//				searchdate,conditionType,cid,lid,condition,condVal);
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
		list = DataFormatUtils.exceptABBFlush(list, "其他");
		// 根据数量级获取流量的单位，防止数量太大
		String flushUnit = DataFormatUtils.getABBFlushUnit(list);
		String imsisUnit = DataFormatUtils.getABBImsisUnit(list);
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + "<Data>"
				+ "<Stat id=\"\" unit=\"" + flushUnit + "|" + imsisUnit 
				+ "\" tipunit=\"\" title=" + chartTitle
				+ " trip=\"流量用户数统计{br}" + spaceName + "：{name}{br}总流量：{total}"
				+ flushUnit + "{br}上行流量：{up}" + flushUnit + "{br}下行流量：{down}"
				+ flushUnit + "{br}用户数：{imsis}" + imsisUnit
				+ "\">";
		if (list != null && !list.isEmpty()) {
			for (ActiveUserSpaceBean c : list) {
				xml = xml + "<Statdata name=\"" + c.getDimensionName()
						+ "\" total=\""
						+ DataFormatUtils.getFlushByUnit1(c, 1,flushUnit, "total")
						+ "\" up=\""
						+ DataFormatUtils.getFlushByUnit1(c, 1,flushUnit, "up")
						+ "\" down=\""
						+ DataFormatUtils.getFlushByUnit1(c,1, flushUnit, "down")
						+ "\" imsis =\""
						+ DataFormatUtils.getIVByUnit1(c, 1,imsisUnit, "imsis")
						+ "\"/>";
			}
		}
		xml = xml + "</Stat>" + "</Data>";
		return xml;
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
		ActiveUserSpaceBean bean;
		try {

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (ActiveUserSpaceBean) list.get(i);
					map = new LinkedHashMap();
					map.put("upFlushMb", bean.getUpFlushMb());
					map.put("downFlushMb", bean.getDownFlushMb());
					map.put("totalFlushMb", bean.getTotalFlushMb());
					map.put("fullDate", bean.getFullDate());
					map.put("spaceName", bean.getDimensionName());
					map.put("imsis", bean.getImsis());
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
