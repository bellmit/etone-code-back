package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.entity.business.DtbBusinessCompany;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.entity.business.DtbBusinessType;
import com.symbol.app.mantoeye.service.businessRule.BusinessCompanyManager;
import com.symbol.app.mantoeye.service.businessRule.BusinessSiteManager;
import com.symbol.app.mantoeye.service.businessRule.BusinessTypeManager;
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
 * @author rankin 业务配置
 */
public class BusinessSiteAction extends BaseDispatchAction {
	
	//用于保存业务的类型ID，当转到编辑页面时，把编辑前的业务类型ID放入Session中，
	//保存编辑后数据时需要判断业务类型有没有变化，
	//如果业务类型发生变化，需要进行提别处理，不然统计数据会出现重复记录。
	private String OLD_BUSINESS_TYPE = "OLD_BUSINESS_TYPE_13579";

	private DtbBusinessSite entity;
	private Integer id;
	private String keys;
	@Autowired
	private BusinessSiteManager businessSiteManager;
	@Autowired
	private BusinessTypeManager businessTypeManager;
	@Autowired
	private BusinessCompanyManager businessCompanyManager;

	public Integer getId() {
		return id;
	}

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<DtbBusinessSite> page = new Page<DtbBusinessSite>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<DtbBusinessSite> getPage() {
		return page;
	}

	public void setPage(Page<DtbBusinessSite> page) {
		this.page = page;
	}

	public DtbBusinessSite getEntity() {
		return entity;
	}

	public void setEntity(DtbBusinessSite entity) {
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

	private String getBusiNames(String ids) {
		String names = "";
		Map<Long, DtbBusinessSite> namesmap = businessSiteManager
				.getNameMapByIds(ids);
		if (namesmap != null && namesmap.size() > 0) {
			Set<Long> set = namesmap.keySet();
			Iterator<Long> it = set.iterator();
			while (it.hasNext()) {
				names =names + namesmap.get(it.next()).getVcBussinessName() + ",";
			}
		}
		return names;
	}

	public void delete() throws Exception {
		String msg = null;
		String smsg = "";
		String[] sids = keys.split(",");
		Integer[] isids = Common.StringArrayToIntegerArray(sids);
		String names = getBusiNames(keys);
		try {// 删除成功
			businessSiteManager.deleteByKeys(isids);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00008);
			request.setAttribute(VarConstants.URL, "/feedback_list.do?1=1");
			msg = "删除业务信息[业务名：" + names + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			smsg = "删除数据成功!";
		} catch (Exception e) { // 删除失败
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00005);
			addActionMessage("删除业务信息" + keys + "失败!");
			msg = "删除业务信息[业务名：" + names + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			smsg = "该业务已经被使用，不能被删除!";
		}
		Struts2Utils.renderText(smsg);
	}

	@Override
	public String input() throws Exception {
		List<DtbBusinessType> busiTypeList = businessTypeManager.getAll();
		List<DtbBusinessCompany> busiCompanyList = businessCompanyManager
				.getAll();

		request.setAttribute("busiTypeList", busiTypeList);
		request.setAttribute("busiCompanyList", busiCompanyList);

		return ADD;
	}

	public void list() {
		// setPaginationdataList();

		List<DtbBusinessSite> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			// logger.info("si.getFieldName():"+si.getFieldName()+"   getSortField(si.getFieldName()):"+getSortField(si.getFieldName()));
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmBussinessId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = businessSiteManager.search(page, filters);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		// logger.info("----list:"+list.size());
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public String save() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();

		String busiid = request.getParameter("busiTypeId");
		String busiTypeName = request.getParameter("busiTypeName");
		String compid = request.getParameter("busiCompanyId");
		String compName = request.getParameter("companyName");
		logger.info("busiid:" + busiid + " compid:" + compid + " busiName:"
				+ busiTypeName + " compName" + compName);
		DtbBusinessType typeBean = null;
		DtbBusinessCompany companyBean = null;
		Integer bid = Common.StringToInt2(busiid);
		Integer cid = Common.StringToInt2(compid);
		typeBean = new DtbBusinessType();
		companyBean = new DtbBusinessCompany();
		boolean saveType = false;
		boolean saveComp = false;
		if (bid >= 0) {
			typeBean.setNmBussinessTypeId(bid);
		} else {
			saveType = true;
			typeBean.setVcBussinessTypeName(busiTypeName);
		}
		if (cid >= 0) {
			companyBean.setNmCompanyId(cid);
		} else {
			saveType = true;
			companyBean.setVcName(compName);
		}

		try {
			businessSiteManager.saveEntity(entity, typeBean, companyBean);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加业务信息[业务名：" + entity.getVcBussinessName() + "]成功!";

			request.setAttribute(VarConstants.URL,
					"/businessSite_input.do?flag=1");
			if (saveType) {
				commonManagerImpl.log(request, "保存业务类型[类型名称：" + busiTypeName
						+ "成功!]");
			}
			if (saveComp) {
				commonManagerImpl
						.log(request, "保存公司[公司名称：" + compName + "成功!]");
			}
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}

			return SUCCESS;

		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存业务信息[业务名：" + entity.getVcBussinessName() + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	public String update() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();

		String busiid = request.getParameter("busiTypeId");
		String busiTypeName = request.getParameter("busiTypeName");
		String compid = request.getParameter("busiCompanyId");
		String compName = request.getParameter("companyName");
		logger.info("busiid:" + busiid + " compid:" + compid + " busiName:"
				+ busiTypeName + " compName" + compName);
		DtbBusinessType typeBean = null;
		DtbBusinessCompany companyBean = null;
		Integer bid = Common.StringToInt2(busiid);
		Integer cid = Common.StringToInt2(compid);
		Integer oldtypeid = Common.StringToInteger(this.getServletRequest().getSession().getAttribute(OLD_BUSINESS_TYPE)+"");
		boolean saveType = false;
		boolean saveComp = false;
		boolean typeChanged = false;
		logger.info("***********"+bid+"$$$$$$$$$$$$$$$$"+oldtypeid);
		if (bid >= 0) {
			typeBean = businessTypeManager.get(bid);
			if(oldtypeid>=0&&!oldtypeid.equals(bid)){//业务类型发生了变化
				typeChanged = true;
			}
		} else {
			saveType = true;
			typeBean = new DtbBusinessType();
			typeBean.setVcBussinessTypeName(busiTypeName);
		}
		if (cid >= 0) {
			companyBean = businessCompanyManager.get(cid);
		} else {
			saveComp = true;
			companyBean = new DtbBusinessCompany();
			companyBean.setVcName(compName);
		}
		entity.setDtbBusinessCompany(companyBean);
		entity.setDtbBusinessType(typeBean);

		try {
			businessSiteManager.updateEntity(entity, typeBean, companyBean,typeChanged);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			msg = "编辑业务信息[业务名：" + entity.getVcBussinessName() + "]成功!";
			if (saveType) {
				commonManagerImpl.log(request, "保存业务类型[类型名称：" + busiTypeName
						+ "成功!]");
			}
			if (saveComp) {
				commonManagerImpl
						.log(request, "保存公司[公司名称：" + compName + "成功!]");
			}
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			request.setAttribute(VarConstants.URL, "");
			return SUCCESS;
		} catch (Exception e) {
			msg = "编辑业务信息[业务名：" + entity.getVcBussinessName() + "]失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}

	}

	public DtbBusinessSite getModel() {

		return entity;
	}

	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("entity.vcBussinessName");
		try {
			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String oldName = request.getParameter("vcBussinessName");
		logger.info("oldname:" + oldName);
		try {
			oldName = java.net.URLDecoder.decode(oldName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		logger.info("oldname:" + oldName + "  newname:" + name);
		if (businessSiteManager.isPropertyUnique("vcBussinessName", name,
				oldName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	public String edit() throws Exception {
		entity = businessSiteManager.get(id);
		
		Integer typeid = entity.getDtbBusinessType().getNmBussinessTypeId();
		this.getServletRequest().getSession().setAttribute(OLD_BUSINESS_TYPE, typeid);
		logger.info("ID:"+id+" TypeId:"+typeid);

		List<DtbBusinessType> busiTypeList = businessTypeManager.getAll();
		List<DtbBusinessCompany> busiCompanyList = businessCompanyManager
				.getAll();

		request.setAttribute("busiTypeList", busiTypeList);
		request.setAttribute("busiCompanyList", busiCompanyList);

		return EDIT;
	}

	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatViewData(List list) {
		// logger.info(list.size()+"---size---");
		List maplist = new ArrayList();
		Map map = null;
		DtbBusinessSite bean;
		// logger.info((list != null && list.size() > 0)+"---size---");
		try {

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (DtbBusinessSite) list.get(i);
					map = new LinkedHashMap();
					map.put("intBussinessId", bean.getNmBussinessId());
					map.put("vcBussinessName", CommonUtils.killNull(bean
							.getVcBussinessName()));
					map.put("vcBussinessNote", CommonUtils.killNull(bean
							.getVcBussinessNote()));
					map.put("companyName", CommonUtils.killNull(bean
							.getDtbBusinessCompany().getVcName()));
					map.put("typeName", CommonUtils.killNull(bean
							.getDtbBusinessType().getVcBussinessTypeName()));
					map.put("vcBussinessPicPath", CommonUtils.killNull(bean
							.getVcBussinessPicPath()));
					maplist.add(map);
				}
				// logger.info(map.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return maplist;
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<DtbBusinessSite> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFiltersWithConvert(Struts2Utils.getRequest());

		String bn = request.getParameter("filter_LIKE_vcBussinessName");
		logger.info("-----" + Common.convertToUTF8(bn));
		// String abn =

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// list = buildJsonData();
		list = businessSiteManager.find(filters);
		String exportmsg = ExportMsg.EXPORT_BUSINESS_MANA;
		commonManagerImpl.log(request, exportmsg);
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

}
