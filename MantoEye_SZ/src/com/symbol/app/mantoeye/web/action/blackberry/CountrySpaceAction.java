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
import com.symbol.app.mantoeye.dto.blackberry.BlackBerryCountryBean;
import com.symbol.app.mantoeye.service.blackberry.BlackBerryCountryManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.DataFormatUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
/**
 * 黑莓用户归属地分布 空间分布
 * @author rankin
 *
 */
public class CountrySpaceAction extends BaseDispatchAction {
	@Autowired
	private BlackBerryCountryManager blackBerryCountryManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;
	
	private String exportFileName;
	
	private String stattype = "2";//粒度 
	
	private String searchdate = CommonUtils.getSYestoday();//时间点
	
	private Long countryId = 101L;//
	
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
	public int getSpaceLevel() {
		return spaceLevel;
	}
	public void setSpaceLevel(int spaceLevel) {
		this.spaceLevel = spaceLevel;
	}
	
	
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		logger.info("stattype:"+stattype+"--searchdate:"+searchdate+"countryId:"+countryId);
		List<BlackBerryCountryBean> list = null;
		if(this.searchdate==null){
			this.searchdate =  CommonUtils.getSYestoday();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try{
	
		list = blackBerryCountryManager.findCountrySpace(spaceLevel, stattype, countryId, searchdate);
		String sxml = buildChartStr(list);
		this.getServletRequest().getSession().setAttribute(
				SessionConstants.SESSION_DATA_BB_COUNTRY_SPACE, sxml);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	public void getAjaxChartXmlData() throws Exception {
		String xml = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_BB_COUNTRY_SPACE)
				+ "";
		Struts2Utils.renderText(xml);
	}
	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		logger.info("stattype:"+stattype+"--searchdate:"+searchdate+"--exportFileName"+exportFileName+"countryId:"+countryId);
		List<BlackBerryCountryBean> list = null;
		if(this.searchdate==null){
			this.searchdate =  CommonUtils.getSYestoday();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try{
	
		list = blackBerryCountryManager.findCountrySpace(spaceLevel, stattype, countryId, searchdate);

		}catch(Exception e){
			logger.error(e.getMessage());
		}
		String exportmsg = ExportMsg.EXPORT_BLACKBREEY_COUNTRYSPACE+"（"+searchdate+"）";
		commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	/**
	 * 获取图形数据
	 */
	public String buildChartStr(List<BlackBerryCountryBean> list) {
		
//		logger.info("stattype:"+stattype+"--searchdate:"+searchdate+"countryId:"+countryId);
//		if(this.searchdate==null){
//			this.searchdate =  CommonUtils.getSYestoday();
//		}
		String spaceName;
//		List<BlackBerryCountryBean> list = null;
//		list =blackBerryCountryManager.findCountrySpace(spaceLevel, stattype, countryId, searchdate);
		if(spaceLevel==CommonConstants.MANTOEYE_SPACE_LEVEL_BSC){
			spaceName = "BSC";
		}else if(spaceLevel==CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN){
			spaceName = "SGSN";
		}else if(spaceLevel==CommonConstants.MANTOEYE_SPACE_LEVEL_STREET){
			spaceName = "街道办";
		}else if(spaceLevel==CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA){
			spaceName = "营销片区";
		}else{
			spaceName = "分公司";
		}
		String titletime = searchdate;
		if (Common.StringToInt(stattype) == 3) {
			titletime = CommonUtils.getDayInnerWeek(searchdate);
		}
		String chartTitle = "\"" + titletime+" " +spaceName+ "{dataname}分布\"";
		
		// 去掉名称为其他的数据
		list = DataFormatUtils.exceptCBBFlush(list, "其他");
		// 根据数量级获取流量的单位，防止数量太大
		String flushUnit = DataFormatUtils.getCBBFlushUnit(list);
		String imsisUnit = DataFormatUtils.getCBBImsisUnit(list);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<Data>"
				+ "<Stat id=\"\" unit=\"" + flushUnit + "|" + imsisUnit 
				+ "\" tipunit=\"\" title=" + chartTitle
				+ " trip=\"用户归属地分布{br}" + spaceName + "：{name}{br}总流量：{total}"
				+ flushUnit + "{br}上行流量：{up}" + flushUnit + "{br}下行流量：{down}"
				+ flushUnit + "{br}用户数：{imsis}" + imsisUnit
				+ "\">";
		if (list != null && !list.isEmpty()) {
			for (BlackBerryCountryBean c : list) {
				xml = xml + "<Statdata name=\"" + c.getDimensionName()
						+ "\" total=\""
						+ DataFormatUtils.getFlushByUnit1(c, 2,flushUnit, "total")
						+ "\" up=\""
						+ DataFormatUtils.getFlushByUnit1(c, 2,flushUnit, "up")
						+ "\" down=\""
						+ DataFormatUtils.getFlushByUnit1(c,2, flushUnit, "down")
						+ "\" imsis =\""
						+ DataFormatUtils.getIVByUnit1(c, 2,imsisUnit, "imsis")
						+ "\"/>";
			}
		}
		
		xml = xml + "</Stat>" + "</Data>";
		return xml;
	}
	/**
	 * 格式化页面GRID所需列表数据
	 * @param list
	 * @return
	 */
		private List formatViewData(List list) {
			List maplist = new ArrayList();
			Map map=null;
			BlackBerryCountryBean bean;
			try{
			
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (BlackBerryCountryBean)list.get(i);
					map = new LinkedHashMap();
					map.put("upFlushMb", bean.getUpFlushMb());
					map.put("downFlushMb", bean.getDownFlushMb());
					map.put("totalFlushMb", bean.getTotalFlushMb());
					map.put("countryName", bean.getCountryName());
					map.put("fullDate",bean.getFullDate());
					map.put("averageFlushMb",bean.getAverageFlushMb());
					map.put("averageFlushKb",bean.getAverageFlushKb());
					map.put("spaceName",bean.getDimensionName());
					map.put("imsis",bean.getImsis());
					maplist.add(map);
				}
				//logger.info(map.toString());
			}
			
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			return maplist;
		}
}
