package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.business.FtbMainAssistVetorNew;
import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.app.mantoeye.entity.business.FtbMainAssistVetor;
import com.symbol.app.mantoeye.service.businessRule.BusinessAssistVetorManager;
import com.symbol.app.mantoeye.service.businessRule.BusinessMainVetorManager;
import com.symbol.app.mantoeye.service.businessRule.VetorManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.export.ExcelMergeBean;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * @author rankin 规则配置
 */
public class BusinessVetorAction extends BaseDispatchAction {

	private FtbMainAssistVetor entity;

	private FtbBusinessMainVetor mentity;
	private FtbBusinessAssistVetor aentity;

	private Integer id;
	private String keys;
	@Autowired
	private VetorManager vetorManager;

	@Autowired
	private BusinessMainVetorManager businessMainVetorManager;

	@Autowired
	private BusinessAssistVetorManager businessAssistVetorManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbMainAssistVetor> page = new Page<FtbMainAssistVetor>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbMainAssistVetor> getPage() {
		return page;
	}

	public void setPage(Page<FtbMainAssistVetor> page) {
		this.page = page;
	}

	public FtbMainAssistVetor getEntity() {
		return entity;
	}

	public void setEntity(FtbMainAssistVetor entity) {
		this.entity = entity;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public FtbBusinessMainVetor getMentity() {
		return mentity;
	}

	public void setMentity(FtbBusinessMainVetor mentity) {
		this.mentity = mentity;
	}

	public FtbBusinessAssistVetor getAentity() {
		return aentity;
	}

	public void setAentity(FtbBusinessAssistVetor aentity) {
		this.aentity = aentity;
	}

	public Integer getId() {
		return id;
	}

	public FtbMainAssistVetor getModel() {

		return entity;
	}

	public void delete() throws Exception {
		String msg = null;
		String rmsg = "";
		String[] sids = keys.split(",");
		Integer[] isids = Common.StringArrayToIntegerArray(sids);
		try {// 删除成功
			vetorManager.deleteByKeys(isids);
			msg = "删除检验规则关联信息[关联Id：" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			rmsg = "删除检验规则关联成功";
		} catch (Exception e) { // 删除失败
			msg = "删除检验规则关联信息[关联Id：" + keys + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			rmsg = "删除检验规则关联失败";
		}
		Struts2Utils.renderText(rmsg);
	}

	public void query() {
		List<FtbMainAssistVetor> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmMainAssistVetorId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		logger.info(page.getOrderBy() + "--" + page.getOrder() + "--"
				+ page.getPageNo() + "--" + page.getPageSize());
		// list = vetorManager.getAll();

		try {

			// page = vetorManager.search(page, filters);
			page = vetorManager.searchForQuery(page, filters);
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = page.getTotalCount();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			list = page.getResult();
			logger.info(list.size() + "----list");

			// TODO
			gridServerHandler.setData(this.formatViewData(list));

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	private List formatViewData(List<FtbMainAssistVetor> list) {
		List maplist = new ArrayList();
		Map map;
		FtbMainAssistVetor bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("nmMainAssistVetorId", bean.getNmMainAssistVetorId());
				if (bean.getFtbBusinessMainVetor() != null
						&& bean.getFtbBusinessMainVetor().getDtbBusinessSite() != null) {
					map.put("businessName",
							CommonUtils.killNull(bean.getFtbBusinessMainVetor()
									.getDtbBusinessSite().getVcBussinessName()));
				} else {
					map.put("businessName", "--");
				}

				// map.put("url",
				// CommonUtils.killNull(bean.getFtbBusinessAssistVetor().getVcUrl()!=null?bean.getFtbBusinessAssistVetor().getVcUrl():bean.getFtbBusinessMainVetor().getVcUrl()));
				// map.put("port",
				// CommonUtils.killNull(bean.getFtbBusinessAssistVetor().getIntPort()>0?bean.getFtbBusinessAssistVetor().getIntPort():bean.getFtbBusinessMainVetor().getIntPort()));
				// map.put("apn",
				// CommonUtils.killNull(bean.getFtbBusinessAssistVetor().getVcApn()!=null?bean.getFtbBusinessAssistVetor().getVcApn():bean.getFtbBusinessMainVetor().getVcApn()));
				// map.put("userAgent",
				// CommonUtils.killNull(bean.getFtbBusinessAssistVetor().getVcUserAgent()!=null?bean.getFtbBusinessAssistVetor().getVcUserAgent():bean.getFtbBusinessMainVetor().getVcUserAgent()));
				// map.put("serverIp",CommonUtils.killNull(
				// bean.getFtbBusinessAssistVetor().getVcServerIp()!=null?bean.getFtbBusinessAssistVetor().getVcServerIp():bean.getFtbBusinessMainVetor().getVcServerIp()));
				if (bean.getFtbBusinessMainVetor() != null) {
					map.put("mainruleIp", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getVcServerIp()));
					map.put("mainrulePort", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getIntPort()));
					map.put("mainruleApn", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getVcApn()));
					map.put("mainruleUserAgent", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getVcUserAgent()));
					map.put("mainruleUrl", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getVcUrl()));
				} else {
					map.put("mainruleIp", "--");
				}
				if (bean.getFtbBusinessAssistVetor() != null) {
					map.put("assistruleIp", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getVcServerIp()));
					map.put("assistrulePort", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getIntPort()));
					map.put("assistruleApn", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getVcApn()));
					map.put("assistruleUserAgent", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getVcUserAgent()));
					map.put("assistruleUrl", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getVcUrl()));
				} else {
					map.put("assistruleIp", "--");
					map.put("assistrulePort", "--");
					map.put("assistruleApn", "--");
					map.put("assistruleUserAgent", "--");
					map.put("assistruleUrl", "--");
				}

				maplist.add(map);
			}
		}
		return maplist;
	}

	public void list() {
		// setPaginationdataList();

		List<FtbMainAssistVetor> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmMainAssistVetorId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		logger.info(page.getOrderBy() + "--" + page.getOrder() + "--"
				+ page.getPageNo() + "--" + page.getPageSize());
		// list = vetorManager.getAll();

		try {

			page = vetorManager.search(page, filters);
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = page.getTotalCount();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			list = page.getResult();
			logger.info(list.size() + "----list");
			gridServerHandler.setData(this.formatViewData(list));

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<FtbMainAssistVetor> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFiltersWithConvert(Struts2Utils.getRequest());

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// list = buildJsonData();
		list = vetorManager.find(filters);

		List<ExcelMergeBean> mergeArray = new ArrayList<ExcelMergeBean>();
		mergeArray.add(new ExcelMergeBean(0, 0, 0, 1, "业务名称"));
		mergeArray.add(new ExcelMergeBean(1, 1, "翻译规则"));
		mergeArray.add(new ExcelMergeBean(2, 5, "检验规则"));
		gridServerHandler.setMergeArray(mergeArray);
		String exportmsg = ExportMsg.EXPORT_BUSINESS_ASSOCIATE;
		commonManagerImpl.log(request, exportmsg);
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List formatViewDataForQuery(List<FtbMainAssistVetorNew> list) {
		List maplist = new ArrayList();
		Map map;
		FtbMainAssistVetorNew bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("nmMainAssistVetorId", bean.getNmMainAssistVetorId());
				if (bean.getFtbBusinessMainVetor() != null
						&& bean.getFtbBusinessMainVetor().getDtbBusinessSite() != null) {
					map.put("businessName",
							CommonUtils.killNull(bean.getFtbBusinessMainVetor()
									.getDtbBusinessSite().getVcBussinessName()));
				} else {
					map.put("businessName", "--");
				}

				// map.put("url",
				// CommonUtils.killNull(bean.getFtbBusinessAssistVetor().getVcUrl()!=null?bean.getFtbBusinessAssistVetor().getVcUrl():bean.getFtbBusinessMainVetor().getVcUrl()));
				// map.put("port",
				// CommonUtils.killNull(bean.getFtbBusinessAssistVetor().getIntPort()>0?bean.getFtbBusinessAssistVetor().getIntPort():bean.getFtbBusinessMainVetor().getIntPort()));
				// map.put("apn",
				// CommonUtils.killNull(bean.getFtbBusinessAssistVetor().getVcApn()!=null?bean.getFtbBusinessAssistVetor().getVcApn():bean.getFtbBusinessMainVetor().getVcApn()));
				// map.put("userAgent",
				// CommonUtils.killNull(bean.getFtbBusinessAssistVetor().getVcUserAgent()!=null?bean.getFtbBusinessAssistVetor().getVcUserAgent():bean.getFtbBusinessMainVetor().getVcUserAgent()));
				// map.put("serverIp",CommonUtils.killNull(
				// bean.getFtbBusinessAssistVetor().getVcServerIp()!=null?bean.getFtbBusinessAssistVetor().getVcServerIp():bean.getFtbBusinessMainVetor().getVcServerIp()));
				if (bean.getFtbBusinessMainVetor() != null) {
					map.put("mainruleIp", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getVcServerIp()));
					map.put("mainrulePort", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getIntPort()));
					map.put("mainruleApn", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getVcApn()));
					map.put("mainruleUserAgent", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getVcUserAgent()));
					map.put("mainruleUrl", CommonUtils.killNull(bean
							.getFtbBusinessMainVetor().getVcUrl()));
				} else {
					map.put("mainruleIp", "--");
				}
				if (bean.getFtbBusinessAssistVetor() != null) {
					map.put("assistruleIp", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getVcServerIp()));
					map.put("assistrulePort", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getIntPort()));
					map.put("assistruleApn", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getVcApn()));
					map.put("assistruleUserAgent", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getVcUserAgent()));
					map.put("assistruleUrl", CommonUtils.killNull(bean
							.getFtbBusinessAssistVetor().getVcUrl()));
				} else {
					map.put("assistruleIp", "--");
					map.put("assistrulePort", "--");
					map.put("assistruleApn", "--");
					map.put("assistruleUserAgent", "--");
					map.put("assistruleUrl", "--");
				}

				maplist.add(map);
			}
		}
		return maplist;
	}

	/**
	 * 添加关联
	 */
	public void addAssociate() {
		String msg = "";
		String rmsg = null;
		String kid = request.getParameter("kid");

		logger.info(kid);
		logger.info(keys);
		Integer ikid = Common.StringToInteger(kid);// 主规则ID
		Integer[] asids = Common.StringArrayToIntegerArray(keys.split(","));
		try {
			vetorManager.addAssociate(ikid, asids);
			rmsg = "关联检验规则成功";
			msg = "主规则[id:" + kid + "]关联检验规则[id:" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
		} catch (Exception e) {
			rmsg = "关联检验规则失败";
			msg = "主规则[id:" + kid + "]关联检验规则[id:" + keys + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
		}
		Struts2Utils.renderText(rmsg);
	}

	/**
	 * 删除关联
	 */
	public void deleteAssociate() {
		String msg = "";
		String rmsg = null;
		String kid = request.getParameter("kid");

		logger.info(kid);
		logger.info(keys);
		Integer ikid = Common.StringToInteger(kid);// 主规则ID
		Integer[] asids = Common.StringArrayToIntegerArray(keys.split(","));
		try {
			vetorManager.deleteAssociate(ikid, asids);
			rmsg = "删除检验规则关联成功";
			msg = "主规则[id:" + kid + "]删除检验规则关联[id:" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
		} catch (Exception e) {
			rmsg = "删除检验规则关联失败";
			msg = "主规则[id:" + kid + "]删除检验规则关联[id:" + keys + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
		}
		Struts2Utils.renderText(rmsg);
	}
}
