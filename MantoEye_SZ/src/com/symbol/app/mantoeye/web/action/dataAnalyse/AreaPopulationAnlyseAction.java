package com.symbol.app.mantoeye.web.action.dataAnalyse;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.businessAnalyse.AreaPopulationAnlyseManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;


/**
 * 常驻人口分析
 * 
 * 
 */
public class AreaPopulationAnlyseAction extends BaseDispatchAction {
	@Autowired
	private AreaPopulationAnlyseManager areaPopulationAnlyseManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search = "1";// 默认GPRS
	private String timeLevel = "3";// 默认周
	private String startTime = getPerThityDay();// 获取前7日的日期
	private String vcAreaName;

	public String getVcAreaName() {
		return vcAreaName;
	}
	public void setVcAreaName(String vcAreaName) {
		this.vcAreaName = vcAreaName;
	}
	/**
	 * 
	 * 获取前7天日期
	 * 
	 * @param todayDataStr
	 * @return
	 */
	public String getPerThityDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String yestodayDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, -7);
			yestodayDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return yestodayDate;
	}
	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<CommonSport> list = null;
		int week = CommonUtils.getWeek(new Date());
		logger.info("当前周"+week);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {
			SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("nmUsers");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			} // 默认排序方式
		} else {
			page.setOrderBy("nmUsers");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = areaPopulationAnlyseManager.query(page, Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel), startTime,Common.OutConvert(vcAreaName));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
			gridServerHandler.setData(list, CommonSport.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}


	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonSport> list = areaPopulationAnlyseManager.listData(Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel), startTime,Common.OutConvert(vcAreaName));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "常驻人口分析"
				+ Common.getTimeLevelCH(timeLevel) + "（" + startTime+"）";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	public String getDataType_search() {
		return dataType_search;
	}

	public void setDataType_search(String dataType_search) {
		this.dataType_search = dataType_search;
	}
	public String getTimeLevel() {
		return timeLevel;
	}
	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}



}
