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
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

/**
 * 点对点彩信时段分布
 * @author rankin
 *
 */
public class PeerMmsHourAction extends BaseDispatchAction {

	@Autowired
	private PeerMmsManager peerMmsManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;
	
	private String exportFileName;
	
	private Page<PeerMmsBean> page = new Page<PeerMmsBean>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	
	private String startdate = CommonUtils.getYestodayDate()+" 00:00:00";//开始时间
	
	private String enddate = CommonUtils.getYestodayDate()+" 23:00:00";//结束时间
	
	private int raittype = 1;//1－gprs 2－TD
	
	public String getExportFileName() {
		return exportFileName;
	}
	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
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
		logger.info("startdate:"+startdate+"--enddate:"+enddate+"--raittype:"+raittype);
		List<PeerMmsBean> list = null;
		if(this.startdate==null){
			this.startdate =   CommonUtils.getYestodayDate()+" 01:00:00";
		}
		if(this.enddate==null){
			this.enddate =   CommonUtils.getYestodayDate()+" 23:00:00";
		}
		
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getMmsSortField(si.getFieldName()));
		} else {
			page.setOrderBy("");
			page.setOrder("");
		}
		
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = peerMmsManager.findHourPeerMms(raittype, startdate, enddate).size();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		
		
		page =  peerMmsManager.findHourPeerMmsPage(page,raittype, startdate, enddate);
		list = page.getResult();
		

		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}
	/**
	 * 获取图形数据
	 */
	public void getAjaxChartData() throws Exception {

		try {
			List<PeerMmsBean> list = peerMmsManager.findHourPeerMms(raittype, startdate, enddate);
			if (list != null && list.size() > 0) {			
			} else {
				list = new ArrayList<PeerMmsBean>();
			}
			JSONArray ja = JSONUtils.BeanList2JSONArray(list,
					PeerMmsBean.class);
			Struts2Utils.renderJson(ja.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		logger.info("startdate:"+startdate+"--enddate:"+enddate+"--raittype:"+raittype);
		if(this.startdate==null){
			this.startdate =   CommonUtils.getYestodayDate()+" 01:00:00";
		}
		if(this.enddate==null){
			this.enddate =   CommonUtils.getYestodayDate()+" 24:00:00";
		}
		List<PeerMmsBean> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		list = peerMmsManager.findHourPeerMms(raittype, startdate, enddate);
		 String exportmsg = ExportMsg.EXPORT_MMS_PEERHOUR+"（"+startdate+"~"+enddate+"）";
			commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}
	
	/**
	 * 格式化页面GRID所需列表数据
	 * @param list
	 * @return
	 */
		private List formatViewData(List list) {
			logger.info(list.size()+"---size---");
			List maplist = new ArrayList();
			Map map=null;
			PeerMmsBean bean;
			//logger.info((list != null && list.size() > 0)+"---size---");
			try{
			
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (PeerMmsBean)list.get(i);
					map = new LinkedHashMap();
					map.put("upCount", bean.getUpCount());
					map.put("downCount", bean.getDownCount());
					map.put("totalCount", bean.getTotalCount());
					map.put("fullDate",bean.getFullDate());
					map.put("hourName",bean.getDimensionName());
					map.put("percentUpCount",bean.getPercentUpCount());
					map.put("percentDownCount",bean.getPercentDownCount());
					map.put("percentTotalCount",bean.getPercentTotalCount());
					map.put("hour",bean.getHour());
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

