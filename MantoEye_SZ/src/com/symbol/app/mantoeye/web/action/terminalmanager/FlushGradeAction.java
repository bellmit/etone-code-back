package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.IOException;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.FlushGradeManager;
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

public class FlushGradeAction extends BaseDispatchAction {

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private Long nmFlushConfigureId;
	private int bitType=0;
	private int intDownFlush;
	private int intUpFlush;

	
	@Autowired
	private FlushGradeManager flushGradeManager;

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
			page.setOrderBy("nmFlushConfigureId");
			page.setOrder("asc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = flushGradeManager.query(page,bitType);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();

		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	
	public void deleteFlushConfigure() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");

		String msg = "";
		try {
			flushGradeManager.deleteByKeys(ids);
			msg = "删除成功!";
			commonManagerImpl.log(request, msg);
		} catch (Exception e) {
			msg = "删除失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}
	
	public void changeFlushConfigure() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		List list = flushGradeManager.findByBitType(bitType);
		if (list!=null && list.size()>0) {
			Object[] obj = (Object[]) list.get(0);
			if (Common.StringToInt(obj[3]+"")!=-1) {
				intDownFlush =Common.StringToInt(obj[3]+"")/1024;
			}else {
				intDownFlush =Common.StringToInt(obj[3]+"");
			}
		}else{
			intDownFlush=0;
		}
		Struts2Utils.renderText(intDownFlush+"");
	}
	
	
	public String saveFlushConfigure() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		try {
			intDownFlush = intDownFlush*1024;
			if (intUpFlush!=-1) {
				intUpFlush = intUpFlush*1024;
			}
			flushGradeManager.saveFlushConfigure(bitType,intDownFlush,intUpFlush);
			msg = "添加成功!";
			request.setAttribute(VarConstants.SUCC_CODE,MsgConstants.SUCC_CODE_00101);
			return SUCCESS;
		} catch (Exception e) {
			msg = "添加失败!";
			request.setAttribute(VarConstants.ERROR_CODE, msg);

			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}

	}
	
	public String input() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		List list = flushGradeManager.findByBitType(bitType);
		if (list!=null && list.size()>0) {
			Object[] obj = (Object[]) list.get(0);
			if (Common.StringToInt(obj[3]+"")!=-1) {
				intDownFlush =Common.StringToInt(obj[3]+"")/1024;
			}else {
				intDownFlush =Common.StringToInt(obj[3]+"");
			}
		}else {
			intDownFlush=0;
		}
		return INPUT;
	}

	public Long getNmFlushConfigureId() {
		return nmFlushConfigureId;
	}

	public void setNmFlushConfigureId(Long nmFlushConfigureId) {
		this.nmFlushConfigureId = nmFlushConfigureId;
	}

	public int getBitType() {
		return bitType;
	}

	public void setBitType(int bitType) {
		this.bitType = bitType;
	}

	public int getIntDownFlush() {
		return intDownFlush;
	}

	public void setIntDownFlush(int intDownFlush) {
		this.intDownFlush = intDownFlush;
	}

	public int getIntUpFlush() {
		return intUpFlush;
	}

	public void setIntUpFlush(int intUpFlush) {
		this.intUpFlush = intUpFlush;
	}

	
}
