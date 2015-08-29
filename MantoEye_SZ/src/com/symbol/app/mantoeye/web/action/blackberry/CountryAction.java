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
import com.symbol.app.mantoeye.dto.blackberry.LdcUsersBean;
import com.symbol.app.mantoeye.service.blackberry.BlackBerryCountryManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
/**
 * 黑莓用户归属地分布
 * @author rankin
 *
 */
public class CountryAction extends BaseDispatchAction{
	@Autowired
	private BlackBerryCountryManager blackBerryCountryManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;
	
	private String exportFileName;
	
	private String stattype = "2";//粒度
	
	private String searchdate = CommonUtils.getSYestoday();//时间点
	
	
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
	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		logger.info("stattype:"+stattype+"--searchdate:"+searchdate);
		List<BlackBerryCountryBean> list = null;
		if(this.searchdate==null){
			this.searchdate =  CommonUtils.getSYestoday();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try{
	
		list = blackBerryCountryManager.findCountryDistribute(stattype, searchdate);
		

		String sxml = buildChartStr(list);
		this.getServletRequest().getSession().setAttribute(
				SessionConstants.SESSION_DATA_BB_COUNTRY, sxml);
		
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	public void getAjaxChartXmlData() throws Exception {
		String xml = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_BB_COUNTRY)
				+ "";
		Struts2Utils.renderText(xml);
	}
	public String buildChartStr(List<BlackBerryCountryBean> list ){
//		List<BlackBerryCountryBean> list = null;
//		if(this.searchdate==null){
//			this.searchdate =  CommonUtils.getSYestoday();
//		}
		int timeLevel=2;
			timeLevel=Common.StringToInt(stattype);
			
//			GridServerHandler gridServerHandler = new GridServerHandler(
//					Struts2Utils.getRequest(), Struts2Utils.getResponse());
//			SortInfo si = gridServerHandler.getSingleSortInfo();
//		
//			
//		list=blackBerryCountryManager.findCountryDistribute(stattype, searchdate);
		Long sumFlush=0l;
		Long sumImsi=0l;
		if (list != null && !list.isEmpty()) {
			for (int i=0;i< list.size();i++) {
				if(i>9){
					BlackBerryCountryBean c=list.get(i);
					sumFlush=sumFlush+c.getTotalFlush();
					sumImsi=sumImsi+c.getImsis();
				}
			}
		}
		String time_Level_Name="";
		time_Level_Name = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "小时"
				: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "天"
						: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "周"
								: "月";
		String xml ="<root><chart id=\"1\" name=\""+searchdate+"运营商#FIELD#"+time_Level_Name+"分布图"+"\"fields=\"流量|用户数\">";
		int i=0;
		if (list != null && !list.isEmpty()) {
			for (BlackBerryCountryBean c : list) {
				i++;
				if(i>10)
					break;
				xml = xml + "<data label=\"" + formatName(c.getCountryName())
				+ "\" shortlabel = \"" + formatName(c.getCountryName())
				+ "\" total=\"" + c.getTotalFlush()
				+ "\" imsis=\"" + c.getImsis() +  "\"/>";
			}
			xml = xml + "<data label=\"" + "其他"
			+ "\" shortlabel = \"" + "其他"
			+ "\" total=\"" + sumFlush
			+ "\" imsis=\"" + sumImsi +  "\"/>";
		}
		xml = xml + "</chart></root>";
		return xml;
	}
	/**
	 * 去掉括号内部的文字
	 * @param name
	 * @return
	 */
	private String formatName(String name){
		String rex = "\\(.*\\)";
		if(name!=null&&name.trim().length()>0){
			return name.replaceAll(rex, "");
		}else{
			return name;
		}
	}
	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		logger.info("stattype:"+stattype+"--searchdate:"+searchdate+"--exportFileName"+exportFileName);
		List<BlackBerryCountryBean> list = null;
		if(this.searchdate==null){
			this.searchdate =  CommonUtils.getSYestoday();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try{
	
			list = blackBerryCountryManager.findCountryDistribute(stattype, searchdate);
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		String exportmsg = ExportMsg.EXPORT_BLACKBREEY_COUNTRY+"（"+searchdate+"）";
		commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );	
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	/**
	 * 获取图形数据
	 */
	//public void getAjaxChartXmlData() throws Exception {
		
//		logger.info("stattype:"+stattype+"--searchdate:"+searchdate+"countryId:"+countryId);
//		if(this.searchdate==null){
//			this.searchdate =  CommonUtils.getSYestoday();
//		}
//		String spaceName;
//		List<BlackBerryCountryBean> list = null;
//		list =blackBerryCountryManager.findCountrySpace(spaceLevel, stattype, countryId, searchdate);
//		if(spaceLevel==CommonConstants.MANTOEYE_SPACE_LEVEL_BSC){
//			spaceName = "BSC";
//		}else if(spaceLevel==CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN){
//			spaceName = "SGSN";
//		}else if(spaceLevel==CommonConstants.MANTOEYE_SPACE_LEVEL_STREET){
//			spaceName = "街道办";
//		}else if(spaceLevel==CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA){
//			spaceName = "营销片区";
//		}else{
//			spaceName = "分公司";
//		}
//		String chartTitle = "\"" + searchdate+" " +spaceName+ "{dataname}分布\"";
//		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
//				+ "<Data>"
//				+ "<Stat id=\"\" unit=\"\" title="
//				+ chartTitle
//				+ " trip=\"用户归属地分布：{br}"+spaceName+"：{name}{br}总流量：{total}MB{br}上行流量：{up}MB{br}下行流量：{down}MB{br}用户数：{imsis}\">";
//		if (list != null && !list.isEmpty()) {
//			for (BlackBerryCountryBean c : list) {
//				xml = xml + "<Statdata name=\"" + c.getDimensionName()
//						+ "\" total=\"" + c.getTotalFlushMb() + "\" up=\""
//						+ c.getUpFlushMb() + "\" down=\"" + c.getDownFlushMb()
//						+ "\"  imsis=\"" + c.getImsis()+"\"/>";
//			}
//		}
//		xml = xml + "</Stat>" + "</Data>";
//		Struts2Utils.renderText(xml);
//	}
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
					map.put("averageFlushKb",bean.getAverageFlushKb());
					map.put("countryName",bean.getDimensionName());
					map.put("imsis",bean.getImsis());
					map.put("totalFlushRate",bean.getTotalFlushRate());
					map.put("imsisRate",bean.getImsisRate());
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
