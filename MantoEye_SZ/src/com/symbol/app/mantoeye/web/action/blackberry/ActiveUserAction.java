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
import com.symbol.app.mantoeye.dto.blackberry.ActiveUserBean;
import com.symbol.app.mantoeye.service.blackberry.BlackBerryActiveUserManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class ActiveUserAction  extends BaseDispatchAction{

	@Autowired
	private BlackBerryActiveUserManager blackBerryActiveUserManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;
	
	private String exportFileName;
	
	private String stattype = "4";//粒度 月
	
	private String searchdate = CommonUtils.getLastMonth().substring(0, 7);//时间点
	
	Page page = new Page();
	
	private String conditionType="1";//过滤类型   运营商(1)or国家(2)
	
	private String countryId="101";//国家ID
	
	private String ldcId;//运营商ID
	
	private String condition="1";//活跃度操作符 >(1) or =(3) or <(2)
	
	private String conditionVal="20";//活跃度过滤值
	
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
	
//	/**
//	 * 查询数据
//	 */
//	public void query() throws ServletException, IOException {
//		logger.info("stattype:"+stattype+"--searchdate:"+searchdate);
//		List<ActiveUserBean> list = null;
//		if(this.searchdate==null){
//			this.searchdate =  CommonUtils.getLastMonth();
//		}
//		GridServerHandler gridServerHandler = new GridServerHandler(
//				Struts2Utils.getRequest(), Struts2Utils.getResponse());
//		try{
//	
//		list = blackBerryActiveUserManager.findActiveUser(searchdate);
//		
//		}catch(Exception e){
//			logger.error(e.getMessage());
//		}
//		gridServerHandler.setData(this.formatViewData(list));
//		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
//		
//		
//	}
	
	/**
	 * 查询数据(分页排序)
	 */
	public void query() throws ServletException, IOException {
		
		List<ActiveUserBean> list = null;
		if(this.searchdate==null){
			this.searchdate =  CommonUtils.getLastMonth();
		}
		logger.info("stattype:"+stattype+"--searchdate:"+searchdate);
		logger.info("conditionType:"+conditionType+"   countryId:"+countryId+"  ldcId:"+ldcId);
		logger.info("condition:"+condition+"   conditionVal:"+conditionVal);
		int condVal = Common.StringToInt(conditionVal);
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
		GridServerHandler gridServerHandler=new GridServerHandler(Struts2Utils.getRequest(),Struts2Utils.getResponse());
			
		int totalRowNum=gridServerHandler.getTotalRowNum();
		if (totalRowNum<1){
			totalRowNum=blackBerryActiveUserManager.findActiveUserCount(searchdate,conditionType,cid,lid,condition,condVal);
			gridServerHandler.setTotalRowNum(totalRowNum);			
		}
		logger.info(totalRowNum+"-----");
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		
		try{
	
		list = blackBerryActiveUserManager.findActiveUserPage(searchdate,page,conditionType,cid,lid,condition,condVal);
		
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

		List<ActiveUserBean> list = null;
		if(this.searchdate==null){
			this.searchdate =  CommonUtils.getLastMonth();
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
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
		try{
			list = blackBerryActiveUserManager.findAllActiveUser(searchdate,conditionType,cid,lid,condition,condVal);		
			String exportmsg = ExportMsg.EXPORT_BLACKBREEY_ACTIVE+"（"+searchdate+"）";
			commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );
			gridServerHandler.exportXLSfromMaps(this.formatViewData(list));
			//gridServerHandler.exportXLS(list,ActiveUserBean.class);
		}catch(Exception e){
			logger.error(e.getMessage());
		}

	}
	/**
	 * 格式化页面GRID所需列表数据
	 * @param list
	 * @return
	 */
		private List formatViewData(List list) {
			List maplist = new ArrayList();
			Map map=null;
			ActiveUserBean bean;
//			int iii=0;
			try{
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (ActiveUserBean)list.get(i);
					map = new LinkedHashMap();
					map.put("fulldate", bean.getFullDate());
					map.put("cgi", bean.getCgi());
					
//					map.put("apnName", bean.getVcApnName());
					map.put("idc", bean.getVcIdc());
					map.put("countryName", bean.getVcCountryName());
					map.put("vcBrandName", bean.getVcBrandName());

					map.put("days", bean.getDays());
					map.put("totalFlushMb", bean.getTotalFlushMb());
					map.put("fullDate",bean.getFullDate());
					map.put("count",bean.getCount());
					map.put("imsi",bean.getImsi());
					map.put("msisdn",bean.getMsisdn());
//					if(iii++%10==0){
//						logger.info(map+"&&&&&&&&&&&&&");
//					}
					maplist.add(map);
				}
				
			}
			
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			logger.info("maplist&&&&"+maplist.size());
			return maplist;
		}
}
