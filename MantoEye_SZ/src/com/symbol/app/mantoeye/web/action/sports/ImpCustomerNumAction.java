package com.symbol.app.mantoeye.web.action.sports;



import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbMsisdnList;
import com.symbol.app.mantoeye.service.sports.ImpCustomerNumManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
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
 * 大运保障
 * 重要客户
 * 
 */

public class ImpCustomerNumAction extends BaseDispatchAction {
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;
	@Autowired
	private ImpCustomerNumManager impCustomerNumManager;
	private String nmMsisdn;
	private String startTime_search=CommonUtils.getPerThityDay();// 开始时间
	private String endTime_search=CommonUtils.getCurrentDate();// 结束时间
	private FtbMsisdnList entity;
	private Long id;
	public Long getId() {
		return id;
	}

	public FtbMsisdnList getEntity() {
		return entity;
	}

	public void setEntity(FtbMsisdnList entity) {
		this.entity = entity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNmMsisdn() {
		return nmMsisdn;
	}

	public void setNmMsisdn(String nmMsisdn) {
		this.nmMsisdn = nmMsisdn;
	}

	public String getStartTime_search() {
		return startTime_search;
	}

	public void setStartTime_search(String startTimeSearch) {
		startTime_search = startTimeSearch;
	}

	public String getEndTime_search() {
		return endTime_search;
	}

	public void setEndTime_search(String endTimeSearch) {
		endTime_search = endTimeSearch;
	}

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<PropertyFilter> filters = HibernateWebUtils
		.buildPropertyFilters(Struts2Utils.getRequest());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("dtUpdateTime");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page =impCustomerNumManager.query(page, startTime_search, endTime_search, Common.OutConvert(nmMsisdn));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		logger.info("---------------导出action---------------------");
		List<CommonSport> list = impCustomerNumManager.listData(startTime_search, endTime_search, Common.OutConvert(nmMsisdn));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "重要客户号码" + "（"+ startTime_search + "~" + endTime_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	@Override
	public String input() throws Exception {
		return INPUT;
	}
	
	public String edit() throws Exception {
		//entity = impCustomerNumManager.get(id);
		return EDIT;
	}
	
	public String saveNmMsisdn() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			impCustomerNumManager.saveNmMsisdn(nmMsisdn);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加号码成功!";
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加号码失败!";
			return ERROR;
		}finally {
			commonManagerImpl.log(request, msg);
		}
	}
	
	public String updateNmMsisdn() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			boolean flag = impCustomerNumManager.nmMsisdnUnique("86"+nmMsisdn);
			if (flag) {
				msg = "编辑号码成功!";
				impCustomerNumManager.updateNmMsisdn(id, Long.parseLong(nmMsisdn));
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00102);
				return SUCCESS;
			}else{
				msg = "编辑号码已存在!";
				request.setAttribute(VarConstants.ERROR_CODE,
						msg);
				return ERROR;
			}

		} catch (Exception e) {
			msg = "编辑号码失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					msg);
			return ERROR;
		}finally {
			commonManagerImpl.log(request, msg);
		}
	}
	
	/**
	 * 删除号码
	 */
	public void deleteNmMsisdn() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String msg = "";
		try {// 删除成功
			impCustomerNumManager.deleteById(ids);
			msg = "删除号码成功!";
			commonManagerImpl.log(request, "删除号码成功[主键：" + ids + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除号码失败!";
			commonManagerImpl.log(request, "删除号码失败[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}
}
