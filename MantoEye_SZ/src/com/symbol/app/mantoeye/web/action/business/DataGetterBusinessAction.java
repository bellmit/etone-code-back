package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.dto.flush.TestFlush;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.entity.business.FtbDataGetterBusiness;
import com.symbol.app.mantoeye.service.businessRule.BusinessSiteManager;
import com.symbol.app.mantoeye.service.businessRule.DataGetterBusinessManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 拨测维护
 * 
 * @author rankin
 * 
 */
public class DataGetterBusinessAction extends BaseDispatchAction {

	public static final String DETAIL_NEW = "detail_new";
	public static final String FILE_LIST = "file_list";
	public static final String FLUSH_LIST = "flush_list";
	public static final String BUSI_FLUSH_LIST = "busi_flush_list";

	@Autowired
	private DataGetterBusinessManager dataGetterBusinessManager;

	@Autowired
	private BusinessSiteManager businessSiteManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbDataGetterBusiness> page = new Page<FtbDataGetterBusiness>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbDataGetterBusiness> getPage() {
		return page;
	}

	public void setPage(Page<FtbDataGetterBusiness> page) {
		this.page = page;
	}

	/**
	 * 拨测结果列表查看
	 */
	public void query() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {
			List<FtbDataGetterBusiness> list = null;
			List<PropertyFilter> filters = HibernateWebUtils
					.buildPropertyFilters(Struts2Utils.getRequest());
			String taskid = request.getParameter("taskid");
			filters.add(new PropertyFilter(
					"ftbDataGetterTask.nmDataGetterTaskId", Common
							.StringToLong(taskid), MatchType.EQ));
			logger.info(taskid + "====" + Common.StringToLong(taskid));

			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

			page = dataGetterBusinessManager.search(page, filters);
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = page.getTotalCount();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			list = page.getResult();
			// list = dataGetterBusinessManager.search(page,
			// filters).getResult();
			// logger.info("----list:" + list.size());
			gridServerHandler.setData(this.formatViewData(list));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 拨测结果 分号码统计流量
	 */
	public void queryMsisdnFlush() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {
			String taskid = request.getParameter("taskid");

			logger.info(taskid + "****" + Common.StringToLong(taskid));
			Long tid = Common.StringToLong(taskid);

			List<TestFlush> result = dataGetterBusinessManager
					.getMsisdnFlush(tid);
			gridServerHandler.setData(this.formatFlushViewData(result));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 拨测结果 某号码分业务统计 统计流量
	 */
	public void queryMsisdnBusiFlush() {
		try{
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {
			String taskid = request.getParameter("taskid");
			String msisdn = request.getParameter("msisdn");

			logger.info(msisdn + "****" + Common.StringToLong(taskid));
			Long tid = Common.StringToLong(taskid);

			List<TestFlush> result = dataGetterBusinessManager
					.getMsisdnBusiFlush(tid, msisdn);
			gridServerHandler.setData(this.formatFlushViewData(result));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<FtbDataGetterBusiness> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		String taskid = request.getParameter("taskid");
		filters.add(new PropertyFilter("ftbDataGetterTask.nmDataGetterTaskId",
				Common.StringToLong(taskid), MatchType.EQ));

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// list = buildJsonData();
		list = dataGetterBusinessManager.find(filters);

		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatViewData(List list) {

		logger.info(list.size() + "---size---");
		List maplist = new ArrayList();
		Map map = null;
		FtbDataGetterBusiness bean;
		// logger.info((list != null && list.size() > 0) + "---size---");
		//DtbBusinessSite dbs = null;
		String businessName = "--未知业务--";
//		Integer businessId = 0;
		String bussids = "-1,";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = (FtbDataGetterBusiness) list.get(i);
				String busiid = bean.getNmBussinessId()+"";
				if(bussids.indexOf(","+busiid+",")==-1){
					bussids = bussids +busiid+",";
				}
			}
			bussids = bussids.substring(0,bussids.length()-1);
			logger.info("^^^^^^^^^^^^^^^^busi ids"+bussids);
			Map<Long,DtbBusinessSite>  busiMap = businessSiteManager.getNameMapByIds(bussids);
			for (int i = 0; i < list.size(); i++) {
				bean = (FtbDataGetterBusiness) list.get(i);
				Long busiid = bean.getNmBussinessId()*1L;
//				try {
//					dbs = businessSiteManager.get(busiid);
//					businessId = busiid;
//					if (dbs != null) {
//						businessName = dbs.getVcBussinessName();
//					}
//				} catch (Exception e) {
//					// org.hibernate.ObjectNotFoundException: No row with the
//					// given identifier exists:
//					// [com.symbol.app.mantoeye.entity.business.DtbBusinessSite#0]
//					// 如果table1里有自身的主键id1,还有table2的主键id2,这两个字段.
//					// 如果hibenrate设置的单项关联,即使table1中的id2为null值,table2中id2中有值,查询都不会出错.
//					// 但是如果table1中的id2字段有值,但是这个值在table2中主键值里并没有,就会报上面的错!
//				}
				if(busiMap.get(busiid)!=null){
					businessName = busiMap.get(busiid).getVcBussinessName()!=null?busiMap.get(busiid).getVcBussinessName():"--未知业务--";
				}							
				map = new LinkedHashMap();
				// map.put("nmAssistRuleId", bean.getNmAssistRuleId());
				map.put("nmDataGetterBusinessId", bean
						.getNmDataGetterBusinessId());
				map.put("nmImsi", CommonUtils.killNull(bean.getNmImsi()));
				map.put("nmMsisdn", CommonUtils.killNull(bean.getNmMsisdn()));
				map.put("vcUrl", CommonUtils.killNull(bean.getVcUrl()));
				map.put("vcServerIp", CommonUtils
						.killNull(bean.getVcServerIp()));
				map.put("intPort", CommonUtils.killNull(bean.getIntPort()));
				map.put("vcApn", CommonUtils.killNull(bean.getVcApn()));
				map.put("businessName", businessName);
				map.put("businessId", busiid);

				map.put("vcUserAgent", CommonUtils.killNull(bean
						.getVcUserAgent()));

				map.put("nmUpFlush", CommonUtils.formatBToKb(bean
						.getNmUpFlush()));
				map.put("nmDownFlush", CommonUtils.formatBToKb(bean
						.getNmDownFlush()));
				map.put("nmTotalFlush", CommonUtils.formatBToKb(bean
						.getNmUpFlush()
						+ bean.getNmDownFlush()));

				// private Long nmUpFlush;
				// private Long nmDownFlush;

				map.put("dtTime", CommonUtils.killNull(Common
						.getDateTimeFormat(bean.getDtTime())));
				maplist.add(map);

			}
			logger.info(map.toString());
		}
		return maplist;
	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatFlushViewData(List<TestFlush> list) {

		logger.info(list.size() + "---size---");
		List maplist = new ArrayList();
		Map map = null;
		TestFlush bean;

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("nmMsisdn", bean.getMsisdn());
				map.put("taskid", bean.getTaskid());
				map
						.put("nmUpFlush", CommonUtils.formatBToKb(bean
								.getUpFlush()));
				map.put("nmDownFlush", CommonUtils.formatBToKb(bean
						.getDownFlush()));
				map.put("nmTotalFlush", CommonUtils.formatBToKb(bean
						.getTotalFlush()));
				map.put("businessName", bean.getBusinessName());

				maplist.add(map);
			}
		}
		return maplist;
	}

	/**
	 * 打开列表弹出页面
	 * 
	 * @return
	 */
	public String openlist() {
		String taskid = request.getParameter("taskid");
		String busiid = request.getParameter("busiid");
		String tasktype = request.getParameter("tasktype");
		logger.info(taskid + "----" + busiid + "----" + tasktype);
		request.setAttribute("busiid", busiid);
		request.setAttribute("taskid", taskid);
		if (tasktype != null && tasktype.equals("5")) {// 已有业务
			return DETAIL;
		} else {// 新业务
			return DETAIL_NEW;
		}

	}
	/**
	 * 打开文件弹出页面
	 * 
	 * @return
	 */
	public String filelist() {
		String taskid = request.getParameter("taskid");
		String busiid = request.getParameter("busiid");
		String tasktype = request.getParameter("tasktype");
		logger.info(taskid + "----" + busiid + "----" + tasktype);
		request.setAttribute("busiid", busiid);
		request.setAttribute("taskid", taskid);
		// if(tasktype!=null&&tasktype.equals("5")){//已有业务
		//			
		// }
		return FILE_LIST;

	}
	/**
	 * 打开手机/流量列表弹出页面
	 * 
	 * @return
	 */
	public String openflush() {
		String taskid = request.getParameter("taskid");
//		String busiid = request.getParameter("busiid");
//		String tasktype = request.getParameter("tasktype");
//		logger.info(taskid + "----" + busiid + "----" + tasktype);
//		request.setAttribute("busiid", busiid);
		request.setAttribute("taskid", taskid);
		return FLUSH_LIST;

	}
	/**
	 * 打开手机/业务/流量弹出页面
	 * 
	 * @return
	 */
	public String openbusiflush() {
		String taskid = request.getParameter("taskid");
		String msisdn = request.getParameter("msisdn");
		request.setAttribute("taskid", taskid);
		request.setAttribute("msisdn", msisdn);
		return BUSI_FLUSH_LIST;

	}
}