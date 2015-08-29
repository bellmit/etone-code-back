package com.symbol.app.mantoeye.web.action.bigFlushUser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.dto.flush.BigFlushUserValue;
import com.symbol.app.mantoeye.entity.business.BigFlushUserLimitValue;
import com.symbol.app.mantoeye.service.bigflushuser.BigFlushUserLimitValueManager;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseOpLog;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class ValueSetAction extends BaseDispatchAction implements IAction{

	@Autowired
	private BigFlushUserLimitValueManager bigFlushUserLimitValueManager;
	private Page<TbBaseOpLog> page = new Page<TbBaseOpLog>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	
	public String delete() throws Exception {
		return null;
	}

	public String edit() throws Exception {
		return EDIT;
	}
	

	public String list() throws Exception {
		List<BigFlushUserValue> list=new ArrayList<BigFlushUserValue>();
		BigFlushUserValue bigFlush=new BigFlushUserValue();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		
		return INDEX;
	}
	
	public void query(){
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<BigFlushUserLimitValue> list=bigFlushUserLimitValueManager.query();
		gridServerHandler.setData(list, BigFlushUserLimitValue.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		
	}
	
	public void saveone(){
		HttpServletRequest request = Struts2Utils.getRequest();
		String id=request.getParameter("id");
		
		String value=request.getParameter("value");
		boolean flag=bigFlushUserLimitValueManager.save(id, value);
		Struts2Utils.renderText("");
	}
	public String save() throws Exception {
		return null;
	}
	public String update() throws Exception {
		return null;
	}
}
