package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.entity.business.FtbBusinessAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.app.mantoeye.service.businessRule.BusinessMainVetorManager;
import com.symbol.app.mantoeye.service.businessRule.BusinessSiteManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * @author rankin 规则配置
 */
public class BusinessMainVetorAction extends BaseDispatchAction {

	private FtbBusinessMainVetor entity;

	private Integer id;
	private String keys;

	@Autowired
	private BusinessMainVetorManager businessMainVetorManager;

	@Autowired
	private BusinessSiteManager businessSiteManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbBusinessMainVetor> page = new Page<FtbBusinessMainVetor>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbBusinessMainVetor> getPage() {
		return page;
	}

	public void setPage(Page<FtbBusinessMainVetor> page) {
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

	public FtbBusinessMainVetor getEntity() {
		return entity;
	}

	public void setEntity(FtbBusinessMainVetor entity) {
		this.entity = entity;
	}

	public Integer getId() {
		return id;
	}

	public void delete() throws Exception {
		String msg = null;
		String[] sids = keys.split(",");
		String smsg = "";
		Integer[] isids = Common.StringArrayToIntegerArray(sids);
		try {// 删除成功
			businessMainVetorManager.deleteByKeys(isids);
			msg = "删除翻译规则[规则Id：" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			smsg = "删除数据成功!";
		} catch (Exception e) { // 删除失败
			msg = "删除翻译规则[规则Id：" + keys + "]失败!";
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

	public void query() throws ServletException, IOException {
		// setPaginationdataList();

		List<FtbBusinessMainVetor> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmBusinessMainVetorId");
			page.setOrder("desc");
		}

		String flag = request.getParameter("flag");
		logger.info("falg========" + flag);
		// 拨测维护时的查询
		if (flag != null && flag.equals("1")) {
			// 取得业务id
			// String busiid = request.getAttribute("busiid").toString();
			String busiid = request.getParameter("busiid");
			Integer ibid = Common.StringToInteger(busiid);
			logger.info("ibid" + ibid);
			if (ibid > 0) {// 有匹配的业务
				filters.add(new PropertyFilter("dtbBusinessSite.nmBussinessId",
						ibid, MatchType.EQ));
			} else {// 没有匹配的业务
				filters.add(new PropertyFilter("dtbBusinessSite.nmBussinessId",
						0, MatchType.EQ));
			}
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		logger.info(page.getOrderBy() + "--" + page.getOrder() + "--"
				+ page.getPageNo() + "--" + page.getPageSize());
		// list = vetorManager.getAll();
		try {

			page = businessMainVetorManager.search(page, filters);
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
		logger.info(bbid + ">>>" + busiid);
		try {
			businessMainVetorManager.saveEntity(entity, busiid);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加翻译规则[IP：" + entity.getVcServerIp() + "]成功!";

			request.setAttribute(VarConstants.URL, "");
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存翻译规则[IP：" + entity.getVcServerIp() + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	public String mutilSave() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		String bbid = request.getParameter("businessIds");
		Integer busiid = Common.StringToInteger(bbid);
		String bussiname = "--";
		DtbBusinessSite dtbb = businessSiteManager.get(busiid);
		if (dtbb != null) {
			bussiname = dtbb.getVcBussinessName();
		}
		String ips = request.getParameter("ips");
		if (ips != null && ips.trim() != "") {
			String[] ipArray = ips.split("\n");
			logger.info("翻译规则数量：" + ipArray.length);
			try {
				businessMainVetorManager.mutilSaveEntity(ipArray, busiid);
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00101);
				msg = "对业务[业务名：" + bussiname + "]添加" + ipArray.length
						+ "条翻译规则成功!";

				request.setAttribute(VarConstants.URL, "");
				if (Common.judgeString(msg)) {
					commonManagerImpl.log(request, msg);
				}
				return SUCCESS;

			} catch (Exception e) {
				request.setAttribute(VarConstants.ERROR_CODE,
						MsgConstants.ERROR_CODE_00004);
				msg = "添加翻译规则失败!";
				if (Common.judgeString(msg)) {
					commonManagerImpl.log(request, msg);
				}
				return ERROR;
			}
		}
		request.setAttribute(VarConstants.ERROR_CODE,
				MsgConstants.ERROR_CODE_00004);
		logger.info("IP为空！");
		return ERROR;
	}

	public String update() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		Date now = new Date();
		String bbid = request.getParameter("businessIds");
		Integer busiid = Common.StringToInteger(bbid);
		logger.info(bbid + ">>>" + busiid);
		DtbBusinessSite bs = businessSiteManager.get(busiid);
		entity.setDtbBusinessSite(bs);
		try {
			businessMainVetorManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			msg = "编辑翻译规则[IP：" + entity.getVcServerIp() + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;
		} catch (Exception e) {
			msg = "编辑翻译规则[IP：" + entity.getVcServerIp() + "]失败!";
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
		// qbean.setIntPort(Common.StringToInteger(request.getParameter("port")));
		// qbean.setVcApn(request.getParameter("apn"));
		qbean.setVcServerIp(request.getParameter("ip"));
		// qbean.setVcUrl(request.getParameter("url"));
		// qbean.setVcUserAgent(request.getParameter("useragent"));

		logger.info(qbean.getVcApn() + "|" + qbean.getVcServerIp() + "|"
				+ qbean.getVcUrl() + "|" + qbean.getIntPort() + ""
				+ qbean.getVcUserAgent() + "|" + busiid);

		if (businessMainVetorManager.isAllPropertyUnique(qbean, busiid, oldkey)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}

		return null;
	}

	public String edit() throws Exception {
		entity = businessMainVetorManager.get(id);
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
	private List formatViewData(List<FtbBusinessMainVetor> list) {
		List maplist = new ArrayList();
		Map map;
		FtbBusinessMainVetor bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("nmBusinessMainVetorId",
						bean.getNmBusinessMainVetorId());
				map.put("businessName", CommonUtils.killNull(bean
						.getDtbBusinessSite().getVcBussinessName()));

				map.put("mainruleIp",
						CommonUtils.killNull(bean.getVcServerIp()));
				map.put("mainrulePort", CommonUtils.killNull(bean.getIntPort()));
				map.put("mainruleApn", CommonUtils.killNull(bean.getVcApn()));
				map.put("mainruleUserAgent",
						CommonUtils.killNull(bean.getVcUserAgent()));
				map.put("mainruleUrl", CommonUtils.killNull(bean.getVcUrl()));

				maplist.add(map);
			}
		}
		return maplist;
	}

	/**
	 * 关联辅规则
	 */
	public String associate() throws Exception {
		entity = businessMainVetorManager.get(id);
		request.setAttribute("busiid", entity.getDtbBusinessSite()
				.getNmBussinessId());
		String title = "【" + entity.getDtbBusinessSite().getVcBussinessName()
				+ "】翻译规则【";
		// if(entity.getVcUrl()!=null){
		// title = title +"URL:" + entity.getVcUrl();
		// }
		if (entity.getVcServerIp() != null) {
			title = title + "  IP:" + entity.getVcServerIp();
		}
		// if(entity.getIntPort()!=null){
		// title = title +"  Port:"+ entity.getIntPort();
		// }
		// if(entity.getVcApn()!=null){
		// title = title +"  APN:"+ entity.getVcApn();
		// }
		// if(entity.getVcUserAgent()!=null){
		// title = title +"  UserAgent:"+ entity.getVcUserAgent();
		// }
		title = title + "】";
		request.setAttribute("maintitle", title);
		return ASSOCIATE;
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<FtbBusinessMainVetor> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFiltersWithConvert(Struts2Utils.getRequest());

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// list = buildJsonData();
		String flag = request.getParameter("flag");
		logger.info("falg========" + flag);
		// 拨测维护时的查询
		if (flag != null && flag.equals("1")) {
			// 取得业务id
			String busiid = request.getParameter("busiid");
			Integer ibid = Common.StringToInteger(busiid);
			logger.info("ibid" + ibid);
			if (ibid > 0) {// 有匹配的业务
				filters.add(new PropertyFilter("dtbBusinessSite.nmBussinessId",
						ibid, MatchType.EQ));
			} else {// 没有匹配的业务
				filters.add(new PropertyFilter("dtbBusinessSite.nmBussinessId",
						0, MatchType.EQ));
			}
		}
		list = businessMainVetorManager.find(filters);
		String exportmsg = ExportMsg.EXPORT_BUSINESS_MAINRULE;
		commonManagerImpl.log(request, exportmsg);
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}
}
