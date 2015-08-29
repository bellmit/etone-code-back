package com.symbol.app.mantoeye.web.action.mms;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.mms.SpMmsTitleManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class SpMmsTitleAction extends BaseDispatchAction {
	@Autowired
	private SpMmsTitleManager spMmsTitleManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search = "1";
	private String timeLevel_search = "2";
	private String time_search = CommonUtils.getSYestoday();// 获取昨天时间
	private String startTime_search = CommonUtils.getSYestoday();// 获取昨天时间
	private String endTime_search = CommonUtils.getSYestoday() + " 23:00:00";// 
	private String isHour;// 是否小时统计
	private String mms_title_search = "";// 默认彩信主题

	public void query() throws ServletException, IOException {
		try {
			// 小时跨度统计
			if (isHour != null && !"".equals(isHour)) {
				timeLevel_search = "1";
				time_search = startTime_search;
			}
			List<CommonFlush> list = null;
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			SortInfo si = gridServerHandler.getSingleSortInfo();
			if (si != null) {
				String order = si.getSortOrder();
				if ("defaultsort".equals(order)) {
					page.setOrder("desc");
					page.setOrderBy("nmCounts");
				} else {
					page.setOrder(order);
					page.setOrderBy(MantoEyeUtils.getSpMmsSortField(si
							.getFieldName()));
				}
				// 默认排序方式
			} else {
				page.setOrder("desc");
				page.setOrderBy("nmCounts");
			}
			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			page = spMmsTitleManager.query(page, Common
					.StringToInt(dataType_search), Common
					.StringToInt(timeLevel_search), time_search,
					endTime_search, mms_title_search);
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = page.getTotalCount();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			list = page.getResult();
			gridServerHandler.setData(list, CommonFlush.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		String exportmsg = ExportMsg.EXPORT_MMS_SPTITLE
				+ Common.getTimeLevelCH(timeLevel_search)+ "（" + time_search
				+ "）";
		// 小时跨度统计
		if (isHour != null && !"".equals(isHour)) {
			timeLevel_search = "1";
			time_search = startTime_search;
			exportmsg = ExportMsg.EXPORT_MMS_SPTITLEHOUR
					+ Common.getTimeLevelCH(timeLevel_search) + "（"
					+ time_search + "）";
		}
		mms_title_search = new String(mms_title_search.getBytes("ISO-8859-1"),
				"UTF-8");

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		// if(list.size()>65500){
		// logger.info("export csv -size:"+list.size());
		// gridServerHandler.exportCSV(list, CommonFlush.class);
		long totalCount = spMmsTitleManager.getTotalCount(Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), time_search, endTime_search,
				mms_title_search);
		
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("nmCounts");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSpMmsSortField(si
						.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrder("desc");
			page.setOrderBy("nmCounts");
		}
		
		if (totalCount > 60000) {
			String localDir = "";
			String iqServerPath = "";
			String queryTime = "";
			try {
				localDir = PropertiesUtil.getInstance().getProperty(
						"data_catch_localDir");
				iqServerPath = PropertiesUtil.getInstance().getProperty(
						"database_server_temp_path");
				queryTime = String.valueOf(System.currentTimeMillis());
				spMmsTitleManager.queryBySql(Integer.parseInt(dataType_search),
						Common.StringToInt(timeLevel_search), time_search,
						endTime_search, mms_title_search, localDir,
						iqServerPath, queryTime);
				downloadFileLoacl(localDir, queryTime);
			} catch (RuntimeException e) {
				logger.error(e.getMessage());
			} finally {
				spMmsTitleManager.deleteTempFile(localDir, iqServerPath,
						queryTime);
			}

		} else {
			List<CommonFlush> list = spMmsTitleManager.listData(Integer
					.parseInt(dataType_search), Common
					.StringToInt(timeLevel_search), time_search,
					endTime_search, mms_title_search,page);
			gridServerHandler.exportXLS(list, CommonFlush.class);
		}

	}

	private final int BUFFER_INPUT = 16 * 1024;

	private void downloadFileLoacl(String localDir, String queryTime) {
		InputStream in = null;
		OutputStream out = null;
		String filePath = "";
		String name = "大数据导出-" + queryTime + ".csv";
		filePath = localDir + name;

		try {
			out = getServletResponse().getOutputStream();
			getServletResponse().setContentType("application/octet-stream");
			in = new BufferedInputStream(new FileInputStream(filePath),
					BUFFER_INPUT);

			getServletResponse().setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(name, "utf-8"));
			byte[] buffer = new byte[BUFFER_INPUT];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

			out.flush();
		} catch (Exception e) {
			logger.error("错误产生类:[" + e.getClass().getName() + "] - 错误原因:["
					+ e.getLocalizedMessage() + "]");
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("错误产生类:["
							+ e.getStackTrace().getClass().getName()
							+ "] - 错误原因:[" + e.getCause().toString() + "]");
				}
			}

			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("错误产生类:["
							+ e.getStackTrace().getClass().getName()
							+ "] - 错误原因:[" + e.getMessage() + "]");
				}
			}
		}

	}

	/**
	 * 获取SP彩信主题集合(按主题时段统计)
	 */
	public void listMmsTitle() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		String like_mms_title_search = request
				.getParameter("like_mms_title_search");
		String message = "";
		List<String> list = spMmsTitleManager.listMmsTitle(Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search, like_mms_title_search);
		if (list != null && !list.isEmpty()) {
			for (String t : list) {
				message = message + t + "&&&&&";
			}
		}
		if (!"".equals(message)) {
			message = message.substring(0, message.lastIndexOf("&&&&&"));
		}
		Struts2Utils.renderText(message);
	}

	public SpMmsTitleManager getSpMmsTitleManager() {
		return spMmsTitleManager;
	}

	public void setSpMmsTitleManager(SpMmsTitleManager spMmsTitleManager) {
		this.spMmsTitleManager = spMmsTitleManager;
	}

	public Page<CommonFlush> getPage() {
		return page;
	}

	public void setPage(Page<CommonFlush> page) {
		this.page = page;
	}

	public String getDataType_search() {
		return dataType_search;
	}

	public void setDataType_search(String dataType_search) {
		this.dataType_search = dataType_search;
	}

	public String getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(String timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
	}

	public String getTime_search() {
		return time_search;
	}

	public void setTime_search(String time_search) {
		this.time_search = time_search;
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

	public String getIsHour() {
		return isHour;
	}

	public void setIsHour(String isHour) {
		this.isHour = isHour;
	}

	public String getMms_title_search() {
		return mms_title_search;
	}

	public void setMms_title_search(String mms_title_search) {
		this.mms_title_search = mms_title_search;
	}

}
