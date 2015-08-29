package com.symbol.app.mantoeye.web.action.sports;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbMsisdnList;
import com.symbol.app.mantoeye.service.sports.aggregatenum.AggregateNumManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class AggregateNumAction extends BaseDispatchAction {

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String startTime_search = CommonUtils.getPerThityDay();// 开始时间
	private String endTime_search = CommonUtils.getCurrentDate();// 结束时间
	private String number_search = "";
	private FtbMsisdnList entity;
	private Long id;
	private String nmMsisdn;
	private String OldnmMsisdn;
	private String CustomerName;
	@Autowired
	private AggregateNumManager aggregateNumManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
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
		page = aggregateNumManager.query(page, startTime_search,
				endTime_search, Common.OutConvert(nmMsisdn), Common
						.OutConvert(CustomerName));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();

		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void export() throws ServletException, IOException {
		List<CommonSport> list = null;
		if (CustomerName != null) {
			CustomerName = new String(CustomerName.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		list = aggregateNumManager.listData(startTime_search, endTime_search,
				Common.OutConvert(nmMsisdn), Common.OutConvert(CustomerName));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "集团客户号码" + "（"
				+ startTime_search + "~" + endTime_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	public void deleteMenu() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");

		String msg = "";
		try {
			aggregateNumManager.deleteByKeys(ids);
			msg = "删除成功!";
			commonManagerImpl.log(request, "删除号码成功[主键：" + ids + "]成功!!");
		} catch (Exception e) {
			msg = "删除失败!";
			commonManagerImpl.log(request, "删除号码失败[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public String saveTask() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		
		String vcMsisdns = request.getParameter("nmMsisdn");// 用户号码
		String vcNote = request.getParameter("vcNote");// 用户号码
		int count = aggregateNumManager.FindByMsisdn(vcMsisdns);
		try {
			if (count==0) {
				aggregateNumManager.saveTask(vcNote, vcMsisdns);
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00101);
				msg = "添加号码成功!";
				return SUCCESS;
			}else {
				msg = "号码重复!";
				request.setAttribute(VarConstants.ERROR_CODE,msg);
				
				return ERROR;
			}
		
		} catch (Exception e) {
			msg = "添加号码失败!";
			request.setAttribute(VarConstants.ERROR_CODE, msg);

			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	
	}

	public String saveModify() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		int count = 0;
		OldnmMsisdn = Common.OutConvert(OldnmMsisdn);
		nmMsisdn = Common.OutConvert(nmMsisdn);
		if (!OldnmMsisdn.equals(nmMsisdn)) {
			count = aggregateNumManager.FindByMsisdn(nmMsisdn);
		}
		try {
			if (count == 0) {
				// aggregateNumManager.saveModify(Integer.parseInt(id.toString()),"86"
				// + nmMsisdn,CustomerName);
				aggregateNumManager.saveModify(Integer.parseInt(id.toString()),
						"86" + nmMsisdn, Common.OutConvert(CustomerName));
				msg = "编辑号码成功!";
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00102);

				return SUCCESS;
			} else {
				msg = "号码重复!";
				request.setAttribute(VarConstants.ERROR_CODE, msg);

				return ERROR;
			}

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "编辑号码失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}

	public String getOldnmMsisdn() {
		return OldnmMsisdn;
	}

	public void setOldnmMsisdn(String oldnmMsisdn) {
		OldnmMsisdn = oldnmMsisdn;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String edit() throws Exception {

		return EDIT;
	}

	public String getNmMsisdn() {
		return nmMsisdn;
	}

	public void setNmMsisdn(String nmMsisdn) {
		this.nmMsisdn = nmMsisdn;
	}

	public FtbMsisdnList getEntity() {
		return entity;
	}

	public void setEntity(FtbMsisdnList entity) {
		this.entity = entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	public String getStartTime_search() {
		return startTime_search;
	}

	public void setStartTime_search(String startTime_search) {
		this.startTime_search = startTime_search;
	}

	public String getEndTime_search() {
		return endTime_search;
	}

	public void setEndTime_search(String endTime_search) {
		this.endTime_search = endTime_search;
	}

	public String getNumber_search() {
		return number_search;
	}

	public void setNumber_search(String numberSearch) {
		number_search = numberSearch;
	}
}
