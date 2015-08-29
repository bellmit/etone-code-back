package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.business.VStatDayAssistRule;
import com.symbol.app.mantoeye.entity.business.FtbStatDayAssistRule;
import com.symbol.app.mantoeye.service.businessRule.AssistRuleStatManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.export.ExcelMergeBean;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 辅规则不匹配统计
 * 
 * @author rankin
 * 
 */
public class AssistRuleStatAction extends BaseDispatchAction {

	@Autowired
	private AssistRuleStatManager assistRuleStatManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	private String startDate;// 开始时间

	private String endDate;// 结束时间

	// private Integer businuss ;//业务

	private int filtercount;// 次数过滤

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	//
	// public Integer getBusinuss() {
	// return businuss;
	// }
	//
	// public void setBusinuss(Integer businuss) {
	// this.businuss = businuss;
	// }

	public int getFiltercount() {
		return filtercount;
	}

	public void setFiltercount(int filtercount) {
		this.filtercount = filtercount;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbStatDayAssistRule> page = new Page<FtbStatDayAssistRule>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbStatDayAssistRule> getPage() {
		return page;
	}

	public void setPage(Page<FtbStatDayAssistRule> page) {
		this.page = page;
	}

	private String getStartTime(String date) {
		return date.split(" ")[0];
	}

	private String getEndTime(String date) {
		return date.split(" ")[0];
	}

	public void list() {
		try {
			if (startDate == null || startDate == "") {
				startDate = getStartTime(CommonUtils.getYestodayDate());
			}
			if (endDate == null || endDate == "") {
				endDate = getEndTime(CommonUtils.getYestodayDate());
			}
			String busiName = request.getParameter("busiName");
			List<VStatDayAssistRule> list = null;

			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());

			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = assistRuleStatManager.getCount(busiName,
						startDate, endDate);
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

			SortInfo si = gridServerHandler.getSingleSortInfo();
			if (si != null) {
				page.setOrderBy(si.getFieldName());
				page.setOrder(si.getSortOrder());
			} else {
				page.setOrderBy("count");
				page.setOrder("desc");
			}

			list = assistRuleStatManager.findPageData(page, busiName,
					startDate, endDate);

			logger.info("----list:" + list.size());
			gridServerHandler.setData(this.formatViewData(list));
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		if (startDate == null || startDate == "") {
			startDate = getStartTime(Common.getNow());
		}
		if (endDate == null || startDate == "") {
			endDate = getEndTime(Common.getNow());
		}
		List<VStatDayAssistRule> list = null;

		String busiName = request.getParameter("busiName");

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// list = buildJsonData();
		list = assistRuleStatManager.findAllData(busiName, startDate, endDate);

		// List<ExcelMergeBean> mergeArray = new ArrayList<ExcelMergeBean>();
		// mergeArray.add(new ExcelMergeBean(0,0,0,1,"业务名称"));
		// mergeArray.add(new ExcelMergeBean(1,1,0,1,"不匹配次数"));
		// mergeArray.add(new ExcelMergeBean(2,2,"翻译规则"));
		// mergeArray.add(new ExcelMergeBean(3,6,"检验/自发现规则"));
		// mergeArray.add(new ExcelMergeBean(0,0,0,1,"业务名称"));
		//		
		// mergeArray.add(new ExcelMergeBean(0,0,"翻译规则"));
		// mergeArray.add(new ExcelMergeBean(1,1,0,1,"不匹配次数"));
		// mergeArray.add(new ExcelMergeBean(2,2,0,1,"所属业务"));
		// gridServerHandler.setMergeArray(mergeArray);
		String exportmsg = ExportMsg.EXPORT_BUSINESS_ASSISTSTAT + "("
				+ startDate + "~" + endDate + ")";
		commonManagerImpl.log(request, exportmsg);

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
		Map<Integer, Map> resultmap = new LinkedHashMap<Integer, Map>();
		VStatDayAssistRule bean;
		logger.info((list != null && list.size() > 0) + "---size---");

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = (VStatDayAssistRule) list.get(i);

				map = new LinkedHashMap();
				// map.put("nmAssistRuleId", bean.getNmAssistRuleId());
				map.put("mainid", bean.getNmBusinessMainVetorId());
				map.put("vcBussinessName", CommonUtils.killNull(bean
						.getMainVetor().getDtbBusinessSite()
						.getVcBussinessName()));

				map.put("mainruleIp", CommonUtils.killNull(bean.getMainVetor()
						.getVcServerIp()));
				map.put("mainrulePort", CommonUtils.killNull(bean
						.getMainVetor().getIntPort()));
				map.put("mainruleApn", CommonUtils.killNull(bean.getMainVetor()
						.getVcApn()));
				map.put("mainruleUserAgent", CommonUtils.killNull(bean
						.getMainVetor().getVcUserAgent()));
				map.put("mainruleUrl", CommonUtils.killNull(bean.getMainVetor()
						.getVcUrl()));

				// map.put("assistruleIp", CommonUtils.killNull(bean
				// .getAssistVetor()
				// .getVcServerIp()));
				// map.put("assistrulePort", CommonUtils.killNull(bean
				// .getAssistVetor()
				// .getIntPort()));
				// map.put("assistruleApn", CommonUtils.killNull(bean
				// .getAssistVetor()
				// .getVcApn()));
				// map.put("assistruleUserAgent", CommonUtils.killNull(bean
				// .getAssistVetor()
				// .getVcUserAgent()));
				// map.put("assistruleUrl", CommonUtils.killNull(bean
				// .getAssistVetor()
				// .getVcUrl()));

				map.put("count", bean.getTotalCount());
				maplist.add(map);

			}
			logger.info(map.toString());
		}

		return maplist;

	}
}
