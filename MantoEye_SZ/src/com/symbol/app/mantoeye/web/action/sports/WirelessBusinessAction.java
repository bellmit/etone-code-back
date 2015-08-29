package com.symbol.app.mantoeye.web.action.sports;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.sports.WirelessBusinessrManager;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class WirelessBusinessAction extends BaseDispatchAction {

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	// private String dataType_search = "1";// GPRS
	private String timeLevel_search = "1";// 天
	private String startTime_search = returnDate();// 开始时间
	private String endTime_search = returnDate();// 结束时间
	private Integer intType;
	private String vcCGI;

	@Autowired
	private WirelessBusinessrManager wirelessBusinessrManager;
	HttpServletRequest request = ServletActionContext.getRequest();
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
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("intYear");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = wirelessBusinessrManager.query(page, Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search, intType, Common.OutConvert(vcCGI));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();

		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	private String getSortField(String sort) {
		System.out.println(sort);
		if (sort.indexOf("nmFlush") != -1) {
			return "nmFlush";
		}
		return sort;
	}


	
	public  String returnDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String HourDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.HOUR_OF_DAY, -1);
			HourDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return HourDate;
	}
	
	

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonSport> list = null;
		list = wirelessBusinessrManager.listData(Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search, intType, Common.OutConvert(vcCGI));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "无线业务"
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ startTime_search + "~" + endTime_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	public String getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(String timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
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

	public Integer getintType() {
		return intType;
	}

	public void setintType(Integer intType) {
		this.intType = intType;
	}

	public String getVcCGI() {
		return vcCGI;
	}

	public void setVcCGI(String vcCGI) {
		this.vcCGI = vcCGI;
	}

}
