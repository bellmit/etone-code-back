package com.symbol.app.mantoeye.web.action.sports;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.sports.ImportantCustomerManager;
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

/**
 * 大运保障
 * 重要客户
 * 
 */

public class ImportantCustomerAction extends BaseDispatchAction {
	@Autowired
	@Qualifier("commonManagerImpl")
	public ICommonManager commonManagerImpl;
	HttpServletRequest request = ServletActionContext.getRequest();
	
	@Autowired
	private ImportantCustomerManager ImportantCustomerManager;
	
	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String common_search;
	private String timeLevel_search = "1";// 天
	private String startTime_search = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_search = CommonUtils.getSYestoday() + " 23";// 结束时间
	private Integer intType=12;
	public String getCommon_search() {
		return common_search;
	}

	public void setCommon_search(String commonSearch) {
		common_search = commonSearch;
	}

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
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
		page = ImportantCustomerManager.query(page, Common.OutConvert(common_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search,intType,null);
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
		List<CommonSport> list = null;
		list = ImportantCustomerManager.listData(Common.OutConvert(common_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search,intType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "重要客户"
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ startTime_search + "~" + endTime_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	
	/**
	 * 上传号码
	 */
	public String upFile() {
		String msg = "";
		List<String> msisList=new ArrayList<String>();
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		int error = 0;
		List<Integer> errorNum = new ArrayList<Integer>();	
		int total = 0;
		int repeat = 0;
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.getFile()), "GBK"));
			while ((str = br.readLine()) != null) {
				total++;
				String msisdn = str;
				if (msisdn.length() == 11 || msisdn.length() == 13) {

					if (msisdn.length() == 11) {
						msisdn = "86" + msisdn;
					}
					if (!this.isNumeric(msisdn)) {
						errorNum.add(total);
						error++;
						continue;
					}
					if(!msisList.contains(msisdn)){
						msisList.add(msisdn);
					}else{
						repeat++;
					}				
				} else {
					errorNum.add(total);
					error++;
					continue;
				}
			}

			msg = "文件上传服务器,总记录数 :" + total + "， 无效记录数:" + error +"，重复记录："+repeat+ "， 确定上传数据生效吗?";
			request.setAttribute(VarConstants.SUCC_CODE,"0000110");
			request.setAttribute("message", msg);
			request.getSession().setAttribute("msisList", msisList);
			return SUCCESS;

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.SUCC_CODE_00006);
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
				br.close();
			} catch (Exception e) {
				//logger.error(e.getMessage());
			}
		}

	}
	
	public void saveUpFile() {
		List<String> msisList = (List<String>) request.getSession().getAttribute("msisList");
		if(msisList!=null && msisList.size()>0){
			ImportantCustomerManager.saveMsis(msisList);
			clearSession();
		}
	}
	
	public void clearSession() {
		logger.info("清空session");
		request.getSession().removeAttribute("msisList");
	}

	public Page<CommonSport> getPage() {
		return page;
	}

	public boolean isNumeric(String str) {// 判断是否是数字类型
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
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

}
