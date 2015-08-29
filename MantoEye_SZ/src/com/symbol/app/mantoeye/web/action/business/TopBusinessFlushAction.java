package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.entity.business.FtbStatDayBusFlushOrder;
import com.symbol.app.mantoeye.service.businessRule.BusinessAssistVetorManager;
import com.symbol.app.mantoeye.service.businessRule.BusinessMainVetorManager;
import com.symbol.app.mantoeye.service.businessRule.TopBusinessFlushManager;
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
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 业务流量排行
 * 
 * @author rankin
 * 
 */
public class TopBusinessFlushAction extends BaseDispatchAction {

	@Autowired
	private TopBusinessFlushManager topBusinessFlushManager;
	@Autowired
	private BusinessMainVetorManager businessMainVetorManager;
	@Autowired
	private BusinessAssistVetorManager businessAssistVetorManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	private String searchdate = CommonUtils.getSYestoday();// 时间点

	private String busitype;// 业务类型 all/find/nofind

	public String getBusitype() {
		return busitype;
	}

	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getSearchdate() {
		return searchdate;
	}

	public void setSearchdate(String searchdate) {
		this.searchdate = searchdate;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbStatDayBusFlushOrder> page = new Page<FtbStatDayBusFlushOrder>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbStatDayBusFlushOrder> getPage() {
		return page;
	}

	public void setPage(Page<FtbStatDayBusFlushOrder> page) {
		this.page = page;
	}

	private String getStartTime(String date) {
		return date.split(" ")[0] + " 00:00:00";
	}

	private String getEndTime(String date) {
		return date.split(" ")[0] + " 23:59:59";
	}

	public void list() {
		// setPaginationdataList();
		try {
			if (searchdate == null) {
				searchdate = CommonUtils.getSYestoday();
			}
			List<FtbStatDayBusFlushOrder> list = null;
			List<PropertyFilter> filters = HibernateWebUtils
					.buildPropertyFilters(Struts2Utils.getRequest());

			filters.add(new PropertyFilter("dtStatTime", Common
					.getDate(getStartTime(searchdate)), MatchType.GE));
			filters.add(new PropertyFilter("dtStatTime", Common
					.getDate(getEndTime(searchdate)), MatchType.LE));

			logger
					.info("searchdate:" + searchdate + "    busitype:"
							+ busitype);
			logger.info("Start:" + Common.getDate(getStartTime(searchdate))
					+ "    End:" + Common.getDate(getEndTime(searchdate)));

			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			SortInfo si = gridServerHandler.getSingleSortInfo();
			if (si != null) {
				if (si.getFieldName().equals("flushOrder")) {
					page.setOrderBy("intOrder");
					page.setOrder(si.getSortOrder());
				} else if (si.getFieldName().equals("flushMb")) {
					page.setOrderBy("nmFlush");
					page.setOrder(si.getSortOrder());
				}

			} else {
				page.setOrderBy("intOrder");
				page.setOrder("asc");
			}
			// list = businessSiteManager.getAll();
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = topBusinessFlushManager.find(filters).size();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

			page = topBusinessFlushManager.search(page, filters);

			list = page.getResult();
			logger.info("----list:" + list.size());
			gridServerHandler.setData(this.formatViewData(list));
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// /**
	// * 导出
	// */
	// public void export() throws ServletException, IOException {
	//
	// try{
	// if (searchdate == null) {
	// searchdate = Common.getNow();
	// }
	// List<FtbStatDayBusFlushOrder> list = null;
	// // List<PropertyFilter> filters = HibernateWebUtils
	// // .buildPropertyFilters(Struts2Utils.getRequest());
	// //
	// // filters.add(new PropertyFilter("dtStatTime", Common
	// // .getDate(getStartTime(searchdate)), MatchType.GE));
	// // filters.add(new PropertyFilter("dtStatTime", Common
	// // .getDate(getEndTime(searchdate)), MatchType.LE));
	// String findtype = request.getParameter("findType");
	// logger.info("--searchdate:" + searchdate + "-exportFileName"
	// + exportFileName);
	// if (this.searchdate == null) {
	// this.searchdate = Common.getNow();
	// }
	// GridServerHandler gridServerHandler = new GridServerHandler(
	// Struts2Utils.getRequest(), Struts2Utils.getResponse());
	// // list = buildJsonData();
	// list = topBusinessFlushManager.findAll(findtype,getStartTime(searchdate),
	// getEndTime(searchdate));
	// String exportmsg = ExportMsg.EXPORT_BUSINESS_TOPFLUSH;
	// commonManagerImpl.log(request,exportmsg );
	// gridServerHandler.exportXLSfromMaps(this.formatViewData(list));
	// }catch(Exception e){
	// logger.error(e.getMessage());
	// }
	// }
	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		try {
			if (searchdate == null) {
				searchdate = CommonUtils.getSYestoday();
			}
			List<FtbStatDayBusFlushOrder> list = null;
			List<PropertyFilter> filters = HibernateWebUtils
					.buildPropertyFilters(Struts2Utils.getRequest());

			filters.add(new PropertyFilter("dtStatTime", Common
					.getDate(getStartTime(searchdate)), MatchType.GE));
			filters.add(new PropertyFilter("dtStatTime", Common
					.getDate(getEndTime(searchdate)), MatchType.LE));
			String findtype = request.getParameter("findType");
			if (findtype != null && findtype != "") {
				filters
						.add(new PropertyFilter("isFind", findtype,
								MatchType.EQ));
			}

			// logger.info("--searchdate:" + searchdate + "-exportFileName"
			// + exportFileName);
			// if (this.searchdate == null) {
			// this.searchdate = Common.getNow();
			// }
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			list = topBusinessFlushManager.find(filters);
			String exportmsg = ExportMsg.EXPORT_BUSINESS_TOPFLUSH + "("
					+ searchdate + ")";
			commonManagerImpl.log(request, exportmsg);
			gridServerHandler.exportXLSfromMaps(this.formatViewData(list));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatViewData(List list) {
		// logger.info(list.size() + "---size---");
		List maplist = new ArrayList();
		Map map = null;
		FtbStatDayBusFlushOrder bean;
		// logger.info((list != null && list.size() > 0) + "---size---");

		// 业务数据的主键，1000条数据如果分别查会很慢，先组合在一起一次查出
		String ids = ",";
		try {
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (FtbStatDayBusFlushOrder) list.get(i);
					map = new LinkedHashMap();
					map.put("nmBusFlushOrderId", bean.getNmBusFlushOrderId());
					map.put("idFind", CommonUtils.killNull(bean.getIsFind()));
					if (bean.getIsFind().equals("1")
							&& Common.judgeString(bean.getVcMainBussiness())) {
						if (ids.indexOf("," + bean.getVcMainBussiness() + ",") == -1) {
							ids = ids + bean.getVcMainBussiness() + ",";
						}
						map.put("bussinessId", bean.getVcMainBussiness());
					} else if (bean.getIsFind().equals("2")
							&& Common.judgeString(bean.getVcGuessBusiness())) {
						if (ids.indexOf("," + bean.getVcGuessBusiness() + ",") == -1) {
							ids = ids + bean.getVcGuessBusiness() + ",";
						}
						map.put("bussinessId", bean.getVcGuessBusiness());
					} else {
						map.put("bussinessNames", "--");
						map.put("bussinessId", "-1");
					}
					map.put("ip", CommonUtils.killNull(bean.getVcServerIp()));
					map.put("port", CommonUtils.killNull(bean.getIntPort()));
					map.put("flushOrder", CommonUtils.killNull(bean
							.getIntOrder()));
					map.put("flush", CommonUtils.killNull(bean.getNmFlush()));
					map.put("flushMb", CommonUtils.killNull(CommonUtils
							.formatBToMb(bean.getNmFlush() + 0L)));
					map.put("flushGb", CommonUtils.killNull(CommonUtils
							.formatBToGb(bean.getNmFlush() + 0L)));
					maplist.add(map);
				}
				if (ids.startsWith(",") && ids.endsWith(",")) {
					ids = ids.substring(1, ids.length() - 1);
				}
				// logger.info("ids:" + ids);
				Map<String, String> busnNames = businessMainVetorManager
						.getBusiNameByIds(ids);
				// logger.info("ids:" + busnNames);
				// 添加业务名
				for (int i = 0; i < maplist.size(); i++) {
					bean = (FtbStatDayBusFlushOrder) list.get(i);
					LinkedHashMap lmap = (LinkedHashMap) maplist.get(i);
					String busiid = lmap.get("bussinessId") + "";
					// logger.info(busiid);
					((LinkedHashMap) maplist.get(i)).put("bussinessNames",
							CommonUtils.killNull(busnNames.get(busiid)));
				}
				// logger.info("success export!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return maplist;
	}
}
