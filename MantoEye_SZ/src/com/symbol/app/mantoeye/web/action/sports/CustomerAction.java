package com.symbol.app.mantoeye.web.action.sports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.constants.SessionConstants;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.sports.CustomerManager;
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
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class CustomerAction extends BaseDispatchAction {

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	// private String dataType_search = "1";// GPRS
	private String timeLevel_search = "1";// 天
	private String startTime_search = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_search = CommonUtils.getSYestoday() + " 23";// 结束时间
	private String number_search = "";
	private String CustomerName = "";
	@Autowired
	private CustomerManager customerManager;
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
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("statdate");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = customerManager.query(page,
				Common.StringToInt(timeLevel_search), startTime_search,
				endTime_search, Common.OutConvert(number_search), Common.OutConvert(CustomerName));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();

		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	private void buildChartJson(List<CommonFlush> list) {
		String chartJson = " ";
		try {
			if (list != null && list.size() > 0) {
			} else {
				list = new ArrayList<CommonFlush>();
			}
			JSONArray ja = JSONUtils
					.BeanList2JSONArray(list, CommonFlush.class);
			chartJson = ja.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		this.getServletRequest().getSession().setAttribute(
				SessionConstants.SESSION_DATA_FLUSH_TOTAL, chartJson);
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxChartData() throws Exception {
		String chartJson = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_FLUSH_TOTAL)
				+ "";
		Struts2Utils.renderJson(chartJson);
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonSport> list = null;
		if (CustomerName!=null) {
			CustomerName = new String(CustomerName.getBytes("ISO-8859-1"),"UTF-8");
		}	
		list = customerManager.listData(Common.StringToInt(timeLevel_search),
				startTime_search, endTime_search, Common
						.OutConvert(number_search), Common.OutConvert(CustomerName));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "集团客户"
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ startTime_search + "~" + endTime_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	/*
	 * 导入
	 */

	public String upFile() {
		String msg = "";
		System.out.println("执行导入");
		List<String> errornumbers = new ArrayList<String>();// 记录错误号码
		List<String> errorvcNodes=new ArrayList<String>();//记录错误号码相对名称
		List<String> numbers = new ArrayList<String>();
		Map<String, String> mapdata =new HashMap<String, String>();//key为号码，value为名称
		BufferedReader gbkbr = null;
		BufferedReader utf8br = null;
		BufferedReader br = null;

		String str = null;
		int error = 0;
		int total = 0;

		String gbkstr = "";
		String utf8str = "";
		int msindex = 0;
		int terindex = 1;
		int brandindex = 3;
		String[] titles;
		int index = 0;
		int repeat = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					this.getFile()), "GBK"));
			gbkbr = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.getFile()), "GBK"));
			while ((str = br.readLine()) != null) {

				total++;
				str=str.replace("，", ",");
			
				int strCount = str.indexOf(",");
				if (strCount == -1||str.split(",").length!=2) {
					error++;
					continue;
				}
				System.out.println(str.split(",").length);
				String vcNode= Common.OutConvert( str.split(",")[0]);//名称
				String msisdn =  Common.OutConvert( str.split(",")[1]);//号码
				if (msisdn.length() == 11 || msisdn.length() == 13) {

					if (msisdn.length() == 11) {
						msisdn = "86" + msisdn;
					}
					if (!this.isNumeric(msisdn)) {
						error++;
						errornumbers.add(msisdn);
						errorvcNodes.add(vcNode);
						continue;
					}
					if (!numbers.contains(msisdn)) {
						numbers.add(msisdn);
						mapdata.put(msisdn, vcNode);
					} else {
						repeat++;
					}

				} else {
					error++;
					continue;
				}
			}

			// 通过ftp上传到服务器
			// saveUpFile();
			msg = "文件上传服务器,总记录数 :" + total + "， 无效记录数:" + error + "，重复记录："
					+ repeat + "， 确定上传数据生效吗?";
			// msg = "总记录数 :" + total + ", 无效记录数:" + error + " 确定数据生效吗?";
			request.setAttribute(VarConstants.SUCC_CODE, "0000110");
			request.setAttribute("message", msg);
			request.getSession().setAttribute("mapdata", mapdata);
			return SUCCESS;

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00011);
			return ERROR;
		} catch (IOException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00011);
			return ERROR;
		} finally {
			commonManagerImpl.log(Struts2Utils.getRequest(), msg);
			try {
				utf8br.close();
			} catch (Exception e) {

			}
			try {
				gbkbr.close();
			} catch (Exception e) {

			}
			try {
				br.close();
			} catch (Exception e) {

			}
		}

	}

	public void saveUpFile() {
	
		Map<String,String> mapdata=(Map<String, String>) request.getSession()
		.getAttribute("mapdata");
		customerManager.saveUpFile(mapdata);

		request.getSession().removeAttribute("mapdata");
	}

	public boolean isNumeric(String str) {// 判断是否是数字类型
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
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

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getNumber_search() {
		return number_search;
	}

	public void setNumber_search(String numberSearch) {
		number_search = numberSearch;
	}
}
