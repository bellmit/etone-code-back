package com.symbol.wp.core.web.action.sysdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseEventLog;
import com.symbol.wp.core.entity.TbBaseOpLog;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.service.impl.BaseOpLogManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@SuppressWarnings("unchecked")
public class LogInfoAction extends BaseDispatchAction implements IAction {

	@Autowired
	private BaseOpLogManager baseOpLogManager;

	@Autowired
	private ICommonManager commonManagerImpl;

	private Page<TbBaseOpLog> page = new Page<TbBaseOpLog>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	HttpServletRequest request = ServletActionContext.getRequest();

	public void query() throws ServletException, IOException {
		List<TbBaseOpLog> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		// 通过用户的登陆名，查询用户昵称
		String userName = Struts2Utils.getRequest()
				.getParameter("s_vcUserName");
		if (userName != null && !"".equals(userName)) {
			filters.add(new PropertyFilter("vcLoginUser", userName,
					MatchType.LIKE));
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		page.setOrderBy("dtRecordTime");
		page.setOrder("desc");
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = baseOpLogManager.search(page, filters);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<TbBaseOpLog> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		// 通过用户的登陆名，查询用户昵称
		String userName = Struts2Utils.getRequest()
				.getParameter("s_vcUserName");
		if (userName != null && !"".equals(userName)) {
			filters.add(new PropertyFilter("vcLoginUser", userName,
					MatchType.LIKE));
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		list = baseOpLogManager.find(filters);
		String exportmsg = "导出数据：系统日志";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		logger.info("SIZE:" + list.size());
		try {
			gridServerHandler.exportXLSfromMaps(this.formatViewData(list));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	private List<TbBaseOpLog> buildVLog(List<TbBaseOpLog> list) {
		String names = "'";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String n = list.get(i).getVcLoginUser();
				if (names.indexOf("'" + n + "'") == -1) {
					names = names + n + "','";
				}
			}
			if (names.length() > 2) {
				names = names.substring(0, names.length() - 2);
			}
			Map<String, String> map = baseOpLogManager.getUserNames(names);
			for (int i = 0; i < list.size(); i++) {
				String ln = list.get(i).getVcLoginUser();
				if (map.get(ln) != null && map.get(ln) != "") {
					list.get(i).setVcLoginUser(map.get(ln));
				}
			}
		}
		return list;
	}

	/**
	 * 页面视图数据
	 */
	private List<Map> formatViewData(List<TbBaseOpLog> list) {
		List maplist = new ArrayList();
		Map map;
		TbBaseOpLog bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("id", bean.getId());
				map.put("dtRecordTime", CommonUtils.formatFullDate(bean
						.getDtRecordTime()));
				map.put("vcLoginUser", CommonUtils.killNull(bean
						.getVcLoginUser()));
				map.put("vcLogContent", CommonUtils.killNull(bean
						.getVcLogContent()));
				map.put("vcLoginIp", CommonUtils.killNull(bean.getVcLoginIp()));
				maplist.add(map);
			}
		}
		return maplist;
	}

	/**
	 * 删除日志
	 */
	public void deleteLogs() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String logIds = request.getParameter("logIds");
		String[] sids = logIds.split(",");
		String msg = "";
		try {// 删除成功
			baseOpLogManager.deleteByKeys(sids);
			msg = "删除成功!";
			commonManagerImpl.log(request, "删除日志[主键：" + logIds + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除失败!";
			commonManagerImpl.log(request, "删除日志[主键：" + logIds + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public BaseOpLogManager getBaseOpLogManager() {
		return baseOpLogManager;
	}

	public void setBaseOpLogManager(BaseOpLogManager baseOpLogManager) {
		this.baseOpLogManager = baseOpLogManager;
	}

	public ICommonManager getCommonManagerImpl() {
		return commonManagerImpl;
	}

	public void setCommonManagerImpl(ICommonManager commonManagerImpl) {
		this.commonManagerImpl = commonManagerImpl;
	}

	public Page<TbBaseOpLog> getPage() {
		return page;
	}

	public void setPage(Page<TbBaseOpLog> page) {
		this.page = page;
	}

	public String delete() throws Exception {
		return null;
	}

	public String edit() throws Exception {
		return null;
	}

	public String list() throws Exception {
		return null;
	}

	public String save() throws Exception {
		return null;
	}

	public String update() throws Exception {
		return null;
	}

}
