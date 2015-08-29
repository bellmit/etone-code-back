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
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.app.mantoeye.service.businessRule.BusinessAssistVetorManager;
import com.symbol.app.mantoeye.service.businessRule.BusinessSiteManager;
import com.symbol.app.mantoeye.service.businessRule.VetorManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
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
 * @author rankin 规则配置
 */
public class BusinessAssistVetorAction extends BaseDispatchAction {

	private FtbBusinessAssistVetor entity;

	private Integer id;
	private String keys;

	@Autowired
	private BusinessAssistVetorManager businessAssistVetorManager;

	@Autowired
	private BusinessSiteManager businessSiteManager;

	@Autowired
	private VetorManager vetorManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbBusinessAssistVetor> page = new Page<FtbBusinessAssistVetor>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbBusinessAssistVetor> getPage() {
		return page;
	}

	public void setPage(Page<FtbBusinessAssistVetor> page) {
		this.page = page;
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

	public FtbBusinessAssistVetor getEntity() {
		return entity;
	}

	public void setEntity(FtbBusinessAssistVetor entity) {
		this.entity = entity;
	}

	public Integer getId() {
		return id;
	}

	public void delete() throws Exception {
		String msg = null;
		String smsg = "";
		String[] sids = keys.split(",");
		Integer[] isids = Common.StringArrayToIntegerArray(sids);

		try {// 删除成功
			businessAssistVetorManager.deleteByKeys(isids);
			msg = "删除自发现规则[规则Id:" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			smsg = "删除数据成功!";
		} catch (Exception e) { // 删除失败
			msg = "删除自发现规则[规则Id:" + keys + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			smsg = "该规则正在被使用，不能被删除!";
		}
		Struts2Utils.renderText(smsg);
	}

	public String add() throws Exception {
		// logger.info("----:----");
		try {
			String bid = request.getParameter("bid");
			// logger.info("----:" + bid);
			Integer busiid = Common.StringToInteger(bid);
			if (busiid > 0) {
				DtbBusinessSite dbs = businessSiteManager.get(busiid);
				request.setAttribute("busiid", dbs.getNmBussinessId());
				request.setAttribute("businame", dbs.getVcBussinessName());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ADD;
	}

	public void list() {
		// setPaginationdataList();

		List<FtbBusinessAssistVetor> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmBusinessAssistVetorId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		logger.info(page.getOrderBy() + "--" + page.getOrder() + "--"
				+ page.getPageNo() + "--" + page.getPageSize());
		// list = vetorManager.getAll();
		try {

			page = businessAssistVetorManager.search(page, filters);
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

	public String save() throws Exception {
		String msg = null;

		HttpServletRequest request = Struts2Utils.getRequest();
		String bbid = request.getParameter("businessIds");
		Integer busiid = Common.StringToInteger(bbid);
		logger.info(bbid);
		String bussiname = "--";
		DtbBusinessSite dtbb = businessSiteManager.get(busiid);
		if (dtbb != null) {
			bussiname = dtbb.getVcBussinessName();
		}
		try {
			msg = "对业务[业务名：" + bussiname + "]添加自发现规则" + buildLogComment(entity)
					+ "成功!";
			businessAssistVetorManager.saveEntity(entity, busiid);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;

		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存自发现规则失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	private String buildLogComment(FtbBusinessAssistVetor bn) {
		String cm = "";
		if (Common.judgeString(bn.getVcServerIp())) {
			cm = cm + "[IP：" + bn.getVcServerIp() + "]";
		}
		if (bn.getIntPort() != null) {
			cm = cm + "[PORT：" + bn.getIntPort() + "]";
		}
		if (Common.judgeString(bn.getVcUrl())) {
			cm = cm + "[URL：" + bn.getVcUrl() + "]";
		}
		if (Common.judgeString(bn.getVcApn())) {
			cm = cm + "[APN：" + bn.getVcApn() + "]";
		}
		if (Common.judgeString(bn.getVcUserAgent())) {
			cm = cm + "[UserAgent：" + bn.getVcUserAgent() + "]";
		}
		return cm;
	}

	public String update() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();

		String bbid = request.getParameter("businessIds");
		Integer busiid = Common.StringToInteger(bbid);
		DtbBusinessSite bs = businessSiteManager.get(busiid);
		entity.setDtbBusinessSite(bs);
		String bussiname = "--";
		if (bs != null) {
			bussiname = bs.getVcBussinessName();
		}
		try {
			msg = "编辑业务[业务名：" + bussiname + "]的自发现规则["
					+ buildLogComment(entity) + "]成功!";
			businessAssistVetorManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;
		} catch (Exception e) {
			msg = "编辑业务[业务名：" + bussiname + "]的自发现规则["
					+ buildLogComment(entity) + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}

	}

	public String checkUnique() {
		HttpServletRequest request = Struts2Utils.getRequest();
		String bbid = request.getParameter("businessIds");
		Integer busiid = Common.StringToInteger(bbid);

		String oldskey = request.getParameter("keyid");
		Integer oldkey = Common.StringToInteger(oldskey);

		FtbBusinessAssistVetor qbean = new FtbBusinessAssistVetor();
		qbean.setIntPort(Common.StringToInteger(request.getParameter("port")));
		qbean.setVcApn(request.getParameter("apn"));
		qbean.setVcServerIp(request.getParameter("ip"));
		qbean.setVcUrl(request.getParameter("url"));
		qbean.setVcUserAgent(request.getParameter("useragent"));

		logger.info(qbean.getVcApn() + "|" + qbean.getVcServerIp() + "|"
				+ qbean.getVcUrl() + "|" + qbean.getIntPort() + ""
				+ qbean.getVcUserAgent() + "|" + busiid);

		if (businessAssistVetorManager.isAllPropertyUnique(qbean, busiid,
				oldkey)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}

		return null;
	}

	public String edit() throws Exception {
		entity = businessAssistVetorManager.get(id);
		request.setAttribute("busiid", entity.getDtbBusinessSite()
				.getNmBussinessId());
		request.setAttribute("businame", entity.getDtbBusinessSite()
				.getVcBussinessName());

		return EDIT;
	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatViewData(List<FtbBusinessAssistVetor> list) {
		List maplist = new ArrayList();
		Map map;
		FtbBusinessAssistVetor bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				String bn = "--";
				if (bean.getDtbBusinessSite() != null) {
					bn = bean.getDtbBusinessSite().getVcBussinessName();
				}
				map.put("nmBusinessAssistVetorId",
						bean.getNmBusinessAssistVetorId());
				map.put("businessName", CommonUtils.killNull(bn));

				map.put("assistruleIp",
						CommonUtils.killNull(bean.getVcServerIp()));
				map.put("assistrulePort",
						CommonUtils.killNull(bean.getIntPort()));
				map.put("assistruleApn", CommonUtils.killNull(bean.getVcApn()));
				map.put("assistruleUserAgent",
						CommonUtils.killNull(bean.getVcUserAgent()));
				map.put("assistruleUrl", CommonUtils.killNull(bean.getVcUrl()));

				maplist.add(map);
			}
		}
		return maplist;
	}

	/**
	 * 未关联的自发现规则
	 */
	public void listNoassociate() {
		// setPaginationdataList();

		List<FtbBusinessAssistVetor> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		String busiid = request.getParameter("busiid");
		Integer ibusiid = Common.StringToInteger(busiid);
		String kid = request.getParameter("kid");
		Integer ikid = Common.StringToInteger(kid);

		// filters.add(new PropertyFilter("vcUrl","",MatchType.EQ));
		// 自发现规则必须和主规则属于相同的业务
		filters.add(new PropertyFilter("dtbBusinessSite.nmBussinessId",
				ibusiid, MatchType.EQ));

		String m_ip = request.getParameter("m_ip");
		String m_port = request.getParameter("m_port");
		String m_url = request.getParameter("m_url");
		String m_apn = request.getParameter("m_apn");
		String m_useragent = request.getParameter("m_useragent");
		logger.info("-ikid:" + ikid + "-m_ip:" + m_ip + "-m_port:" + m_port
				+ "-m_url:" + m_url + "-m_apn:" + m_apn + "-m_useragent:"
				+ m_useragent + "-ibusiid:" + ibusiid);

		// 主规则和自发现规则在相同字段上不能同时都有值
		if (null != m_ip && !"".equals(m_ip.trim())) {
			filters.add(new PropertyFilter("vcServerIp", "", MatchType.EQ));
		}
		if (null != m_port && !"".equals(m_port.trim())
				&& Common.StringToInt(m_port) > 0) {
			filters.add(new PropertyFilter("intPort", "", MatchType.ISNULL));
		}
		if (null != m_url && !"".equals(m_url.trim())) {
			filters.add(new PropertyFilter("vcUrl", "", MatchType.EQ));
		}
		if (null != m_apn && !"".equals(m_apn.trim())) {
			filters.add(new PropertyFilter("vcApn", "", MatchType.EQ));
		}
		if (null != m_useragent && !"".equals(m_useragent.trim())) {
			filters.add(new PropertyFilter("vcUserAgent", "", MatchType.EQ));
		}
		Integer[] assistIds = vetorManager.findAssistIdByMainId(ikid);

		logger.info("assistIds:" + assistIds.length);
		filters.add(new PropertyFilter("nmBusinessAssistVetorId", assistIds,
				MatchType.NOTIN));

		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmBusinessAssistVetorId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		logger.info(ikid + "----" + page.getOrderBy() + "--" + page.getOrder()
				+ "--" + page.getPageNo() + "--" + page.getPageSize());
		try {

			page = businessAssistVetorManager.search(page, filters);
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
	 * 已经关联的自发现规则
	 */
	public void listAssociate() {
		// setPaginationdataList();

		List<FtbBusinessAssistVetor> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();

		String busiid = request.getParameter("busiid");
		Integer ibusiid = Common.StringToInteger(busiid);
		String kid = request.getParameter("kid");
		Integer ikid = Common.StringToInteger(kid);

		// 自发现规则必须和主规则属于相同的业务
		filters.add(new PropertyFilter("dtbBusinessSite.nmBussinessId",
				ibusiid, MatchType.EQ));
		Integer[] assistIds = vetorManager.findAssistIdByMainId(ikid);
		logger.info("Y---assistIds:" + assistIds.length);
		filters.add(new PropertyFilter("nmBusinessAssistVetorId", assistIds,
				MatchType.IN));

		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmBusinessAssistVetorId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		// logger.info(id+"----"+page.getOrderBy() + "--" + page.getOrder() +
		// "--"
		// + page.getPageNo() + "--" + page.getPageSize());
		try {

			page = businessAssistVetorManager.search(page, filters);
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

		List<FtbBusinessAssistVetor> list = null;
		// 页面url参数需要转码
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFiltersWithConvert(Struts2Utils.getRequest());

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// list = buildJsonData();
		list = businessAssistVetorManager.find(filters);
		String exportmsg = ExportMsg.EXPORT_BUSINESS_ASSISTRULE;
		commonManagerImpl.log(request, exportmsg);
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}
}
