package com.symbol.app.mantoeye.web.action.dataCatch;

import javax.servlet.http.HttpServletRequest;

import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;

public class HistoryRecordCatchAction extends BaseDispatchAction {
	public String list() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		String[] wapArray = { "VIP-1", "张三", "是", "8615913140007",
				"2009-10-20", "2009-10-22", "2009-10-22", "1",
				"http://localhost:7000/admin/VIP-1.txt", "1024" };
		String[] netArray = { "VIP-2", "李四", "是", "8615913143434",
				"2009-10-20", "2009-10-22", "--", "0", "--", "--" };
		String[] companyArray = { "VIP-3", "王五", "是", "8615913146666",
				"2009-10-20", "2009-10-25", "--", "2", "--", "--" };
		String[] otherArray = { "VIP-4", "刘六", "是", "8615913167678",
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
