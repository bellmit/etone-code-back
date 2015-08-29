package com.symbol.app.mantoeye.web.action.dataCatch;

import javax.servlet.http.HttpServletRequest;

import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;

public class RealtimeRecordCatchAction extends BaseDispatchAction {
	public String list() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		String[] wapArray = { "REL-1", "张三", "是", "8615913140007",
				"2009-10-20", "2009-10-22", "2009-10-22", "1",
				"http://localhost:7000/admin/VIP-1.txt", "1024" };
		String[] netArray = { "REL-2", "张三", "是", "8615913143434",
				"2009-10-20", "2009-10-22", "--", "0", "--", "--" };
		String[] companyArray = { "REL-3", "张三", "是", "8615913146666",
				"2009-10-20", "2009-10-25", "--", "2", "--", "--" };
		String[] otherArray = { "REL-4", "张三", "是", "8615913167678",
				"2009-10-18", "2009-10-19", "--", "3", "--", "--" };
		dataList.add(wapArray);
		dataList.add(netArray);
		dataList.add(companyArray);
		dataList.add(otherArray);
		return INDEX;
	}

	/**
	 * 添加
	 */
	public String input() throws Exception {
		logger.info("-----------ddd------.");
		return INPUT;
	}

	/**
	 * 编辑
	 */
	public String edit() throws Exception {
		return EDIT;
	}

	/**
	 * 保存
	 */
	public String save() throws ServiceStartupException {
		return SUCCESS;
	}

	/**
	 * 更新
	 */
	public String update() throws ServiceStartupException {
		return SUCCESS;
	}
}
