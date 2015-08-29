package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.bean.FlushRoleBean;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.app.mantoeye.entity.business.VstatDayMainRuleFlush;
import com.symbol.app.mantoeye.service.businessRule.BusinessMainFlushManager;
import com.symbol.app.mantoeye.service.businessRule.BusinessMainVetorManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 主规则流量碰撞率统计
 * 
 * @author rankin
 * 
 */
public class BusinessMainFlushAction extends BaseDispatchAction {

	@Autowired
	private BusinessMainFlushManager businessMainFlushManager;

	@Autowired
	private BusinessMainVetorManager businessMainVetorManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	private String searchdate = CommonUtils.getSYestoday();

	private String businame;

	private String sip;

	private String flushRate;// 流量环比阀值

	private String business;// 流量环比阀值

	private String conflictCountRate;// 碰撞率环比阀值

	public String getBusiname() {
		return businame;
	}

	public void setBusiname(String businame) {
		this.businame = businame;
	}

	public String getSip() {
		return sip;
	}

	public void setSip(String sip) {
		this.sip = sip;
	}

	public String getFlushRate() {
		return flushRate;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public void setFlushRate(String flushRate) {
		this.flushRate = flushRate;
	}

	public String getConflictCountRate() {
		return conflictCountRate;
	}

	public void setConflictCountRate(String conflictCountRate) {
		this.conflictCountRate = conflictCountRate;
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
	private Page<VstatDayMainRuleFlush> page = new Page<VstatDayMainRuleFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<VstatDayMainRuleFlush> getPage() {
		return page;
	}

	public void setPage(Page<VstatDayMainRuleFlush> page) {
		this.page = page;
	}

	public void list() {
		// setPaginationdataList();
		Date sd = Common.getYMDDate(Common.getToday());
		try {
			sd = Common.getOffsetDate(sd, 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (searchdate != null) {
			sd = Common.getYMDDate(searchdate);
		}
		logger.info("Action date..." + searchdate);
		try {
			List<VstatDayMainRuleFlush> list = null;
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			SortInfo si = gridServerHandler.getSingleSortInfo();
			if (si != null) {
				page.setOrderBy(si.getFieldName());
				page.setOrder(si.getSortOrder());
			} else {
				page.setOrderBy("id.flushRate");
				page.setOrder("desc");
			}
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = businessMainFlushManager.getCount(sd, businame,
						sip);
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			// logger.info("Action date..."+totalRowNum);
			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			buildSortInfo(gridServerHandler);
			// logger.info("Action date..."+gridServerHandler.getPageSize()+"--"+gridServerHandler.getPageInfo().getPageNum());

			// list = businessSiteManager.getAll();
			page = businessMainFlushManager.searchFormView(page, sd, businame,
					sip);

			list = page.getResult();
			logger.info("----list:" + list.size());
			gridServerHandler.setData(this.formatViewData(list));
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.info(e.getMessage());
		}
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<FlushRoleBean> list = businessMainFlushManager.findAllFromView(
				searchdate, businame, sip);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_BUSINESS_MANIFLUSH + "("
				+ searchdate + ")";
		commonManagerImpl.log(request, exportmsg);

		gridServerHandler.exportXLS(list, FlushRoleBean.class);

	}

	private void buildSortInfo(GridServerHandler gridServerHandler) {
		Map<String, String> sortmap = new HashMap<String, String>();
		sortmap.put("lastflush", "lnmFlush");
		sortmap.put("lastconflictCount", "lnmFlush");
		sortmap.put("flush", "nmFlush");
		sortmap.put("conflictCount", "nmConflictCount");
		sortmap.put("flushRate", "flushRate");
		sortmap.put("conflictCountRate", "conflictCountRate");
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			if (sortmap.get(si.getFieldName()) != null) {
				page.setOrderBy(sortmap.get(si.getFieldName()));
			} else {
				page.setOrderBy("");
			}
		} else {
			page.setOrderBy("");
			page.setOrder("asc");
		}

	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatViewData(List list) {
		logger.info(list.size() + "---size---");
		Map map = null;
		List maplist = new ArrayList();
		VstatDayMainRuleFlush bean;
		FtbBusinessMainVetor mainVetor;
		logger.info((list != null && list.size() > 0) + "---size---");
		try {
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (VstatDayMainRuleFlush) list.get(i);

					mainVetor = businessMainVetorManager.get(bean.getId()
							.getNmBusinessMainVetorId());
					map = new LinkedHashMap();
					map.put("nmBusinessMainVetorId", bean.getId()
							.getNmBusinessMainVetorId());
					map.put("fulldate", Common.getDateFormat(bean.getId()
							.getDtStatTime()));
					if (mainVetor != null) {
						map.put("vcBussinessName", CommonUtils
								.killNull(mainVetor.getDtbBusinessSite()
										.getVcBussinessName()));
						map.put("ip", CommonUtils.killNull(mainVetor
								.getVcServerIp()));
					} else {
						map.put("vcBussinessName", "--");
						map.put("ip", CommonUtils.killNull(mainVetor
								.getVcServerIp()));
					}

					map.put("lastflush", CommonUtils.formatBToMb(bean.getId()
							.getLnmFlush().longValue()));// 上周流量
					map.put("lastconflictCount", bean.getId()
							.getLnmConflictCount());// 上周碰撞率
					map.put("flush", CommonUtils.formatBToMb(bean.getId()
							.getNmFlush().longValue()));
					map.put("conflictCount", bean.getId().getNmConflictCount());
					map.put("flushRate", bean.getId().getFlushRate() * 100);
					map.put("conflictCountRate", bean.getId()
							.getConflictCountRate() * 100);

					maplist.add(map);
				}
				logger.info(map.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return maplist;
	}
}
