package com.symbol.app.mantoeye.web.action.blackberry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.blackberry.CountryUserTopBean;
import com.symbol.app.mantoeye.dto.common.CommonDimension;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.CommonDimensionManager;
import com.symbol.app.mantoeye.service.blackberry.BlackBerryCountryManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

/**
 * Top100小区 流量用户数
 * @author rankin
 *
 */
public class CountryTopUsersAction extends BaseDispatchAction{
	@Autowired
	private BlackBerryCountryManager blackBerryCountryManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;
	
	@Autowired
	private CommonDimensionManager commonDimensionManager;
	
	private String exportFileName;
	
	private String stattype = "2";//粒度 月
	
	private String searchdate = CommonUtils.getSYestoday();//时间点
	
	private Long countryId = 101L;//
	
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
		List<CommonFlush> list = null;
		if(this.searchdate==null){
			this.searchdate =  CommonUtils.getSYestoday();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try{
		list = blackBerryCountryManager.findCountryTopUser(countryId, searchdate,Integer.parseInt(stattype));
		
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		logger.info("stattype:"+stattype+"--searchdate:"+searchdate+"--exportFileName"+exportFileName+"countryId:"+countryId);
		List<CommonFlush> list = null;
		if(this.searchdate==null){
			this.searchdate =  CommonUtils.getSYestoday();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try{
			list = blackBerryCountryManager.findCountryTopUser(countryId, searchdate,Integer.parseInt(stattype));
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		String exportmsg = ExportMsg.EXPORT_BLACKBREEY_USER_TOP100+"（"+searchdate+"）";
		commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}
	/**
	 * 格式化页面GRID所需列表数据
	 * @param list
	 * @return
	 */
		private List formatViewData(List list) {
			List maplist = new ArrayList();
			Map map=null;
			CommonFlush bean;
			try{
			
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (CommonFlush)list.get(i);
					map = new LinkedHashMap();
					map.put("cgi", bean.getCgi());
					map.put("countryName", bean.getUserBelong());
					map.put("cellName", bean.getCgiName());
					map.put("flushMb", bean.getTotalFlushMB());
					map.put("flushGb", bean.getTotalFlushGB());
					map.put("fullDate",bean.getFullDate());
					map.put("street", bean.getStreet());
					map.put("subsidiaryCompany",bean.getSubsidiaryCompany());
					map.put("bsc",bean.getBsc());
					map.put("sgsn",bean.getSgsn());
					map.put("saleArea",bean.getSaleArea());
					map.put("imsis",bean.getIntImsis());
					map.put("nmAveFlushKB",bean.getNmAveFlushKB());
					maplist.add(map);
				}
				//logger.info(map.toString());
			}
			
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			return maplist;
		}
		/**
		 * 获取所有的国家信息
		 */
		public void findAllCountry(){
			try{
			List<CommonDimension> list = commonDimensionManager.findAllCountry();
			//logger.info(list.size()+"");
			JSONObject json=new JSONObject();
			json.put("datalist",JSONUtils.BeanList2JSONArray(list, CommonDimension.class));
			//logger.info("country:"+json.toString());
			Struts2Utils.renderJson(json.toString());
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
		/**
		 * 获取所有运营商信息
		 */
		public void findAllLdc(){
			try{
			List<CommonDimension> list = commonDimensionManager.findAllLdc();
			//logger.info(list.size()+"");
			JSONObject json=new JSONObject();
			json.put("datalist",JSONUtils.BeanList2JSONArray(list, CommonDimension.class));
			//logger.info("country:"+json.toString());
			Struts2Utils.renderJson(json.toString());
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
}
