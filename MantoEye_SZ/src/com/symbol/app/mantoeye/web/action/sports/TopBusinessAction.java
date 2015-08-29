package com.symbol.app.mantoeye.web.action.sports;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableCellFormat;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.symbol.wp.tools.gtgrid.export.AbstractXlsWriter;
import com.symbol.wp.tools.gtgrid.export.ExcelStyle;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 大运保障
 * 业务排名
 * 
 */

public class TopBusinessAction extends BaseDispatchAction {
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;
	HttpServletRequest request = ServletActionContext.getRequest();
	
	@Autowired
	private ImportantCustomerManager importantCustomerManager;
	
	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String common_search;
	private String timeLevel_search = "1";// 天
	private String startTime_search = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_search = CommonUtils.getSYestoday() + " 23";// 结束时间
	private Integer intType=11;
	private File file;
	private Integer dataType=1;
	
	private String terminalTimeSearch = null;	//当前业务记录连接进来的时间点
	private String terminalProtectedArea = null;	//当前保障区域内容

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	public String getCommon_search() {
		return common_search;
	}

	public void setCommon_search(String commonSearch) {
		common_search = commonSearch;
	}

	public String getTerminalTimeSearch() {
		return terminalTimeSearch;
	}

	public void setTerminalTimeSearch(String terminalTimeSearch) {
		this.terminalTimeSearch = terminalTimeSearch;
	}

	public String getTerminalProtectedArea() {
		return terminalProtectedArea;
	}

	public void setTerminalProtectedArea(String terminalProtectedArea) {
		this.terminalProtectedArea = terminalProtectedArea;
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
		page = importantCustomerManager.query(page, Common.OutConvert(common_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search,intType,dataType);
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
		if (common_search!=null) {
			common_search = new String(common_search.getBytes("ISO-8859-1"),"UTF-8");
		}		
		list = importantCustomerManager.listData(Common.OutConvert(common_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search,intType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "业务排名"
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ startTime_search + "~" + endTime_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	/**
	 * 查询终端前十数据
	 */
	public void queryTerminalByBusiness() throws ServletException, IOException {
		logger.info("---------------进入终端action---------------------");
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
		page = importantCustomerManager.queryTerminalByBusiness(page, terminalProtectedArea, terminalTimeSearch, intType,dataType);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	
	public void queryTerminal() throws ServletException, IOException {
		logger.info("---------------进入终端action---------------------");
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
		page = importantCustomerManager.queryTerminal(page, Common.OutConvert(common_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search,intType,dataType);
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
	 * 终端前十数据导出
	 */
	public void exportTerminalByBusiness() throws ServletException, IOException {
		logger.info("---------------导出终端action---------------------");
		List<CommonSport> list = null;
		if (terminalProtectedArea!=null) {
			terminalProtectedArea = new String(terminalProtectedArea.getBytes("ISO-8859-1"),"UTF-8");
		}		
		list = importantCustomerManager.listDataTerminalByBusiness(terminalProtectedArea, terminalTimeSearch, intType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端排名"+terminalProtectedArea+"（" + terminalTimeSearch + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	public void exportTerminal() throws ServletException, IOException {
		logger.info("---------------导出终端action---------------------");
		List<CommonSport> list = null;
		if (common_search!=null) {
			common_search = new String(common_search.getBytes("ISO-8859-1"),"UTF-8");
		}	
		list = importantCustomerManager.listDataTerminal(Common.OutConvert(common_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search,intType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端排名"+terminalProtectedArea+"（" + terminalTimeSearch + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	
	public String readXLSFile() {
		List<CommonSport> csList = new ArrayList<CommonSport>();
		List<String> aList = new ArrayList<String>();
		jxl.Workbook rwb = null;
		InputStream is=null;
		String msg=null;
		try {
			// 构建Workbook对象 只读Workbook对象
			// 直接从本地文件创建Workbook
			// 从输入流创建Workbook
			is = new FileInputStream(file);
			rwb = Workbook.getWorkbook(is);
			// Sheet(术语：工作表)就是Excel表格左下角的Sheet1,Sheet2,Sheet3但在程序中
			// Sheet的下标是从0开始的
			// 获取第一张Sheet表
			Sheet rs = rwb.getSheet(0);
			// 获取Sheet表中所包含的总列数
			int rsColumns = rs.getColumns();

			/*if (rsColumns > 2) {
				request.setAttribute(VarConstants.ERROR_CODE,
						"上传格式错误");
				return ERROR;
			}*/
			// 获取Sheet表中所包含的总行数
			int rsRows = rs.getRows();
			// 获取指这下单元格的对象引用

			for (int j = 0; j < rsColumns; j++) {
				Cell c = rs.getCell(j, 0);

				if (j == 0) {
					if (!"区域名称".equalsIgnoreCase(c.getContents())) {
						//request.setAttribute(VarConstants.ERROR_CODE,
						//"上传格式错误");
						//return ERROR;
						logger.info("上传文件标题出错");
					}
				} else if (j == 1) {
					if (!"小区名".equalsIgnoreCase(c.getContents())) {
						//request.setAttribute(VarConstants.ERROR_CODE,
						//"上传格式错误");
						//return ERROR;
						logger.info("");
						logger.info("上传文件标题出错");
					}
				} 
			}
			int count = 0;
			for (int i = 1; i < rsRows; i++) {
				CommonSport commonSport= new CommonSport();
				for (int j = 0; j < rsColumns; j++) {				
					Cell cell = rs.getCell(j, i);
					if (j == 0) {
						if(cell.getContents()!=null && !cell.getContents().equals("")){
							commonSport.setVcAreaName(cell.getContents().trim());
						}	
					} else if (j == 1) {
						if(cell.getContents()!=null && !cell.getContents().equals("")){
							commonSport.setVcCgiName(cell.getContents().trim());
						}		
					} else if (j == 2) {
							if(cell.getContents()!=null && !cell.getContents().equals("")){
								commonSport.setVcCGI(cell.getContents().trim());
							}		
						}
				}				
				//确定没有重复的
				boolean flag = true;
				/*//保障区域
				if (intType==1 && commonSport.getVcAreaName()!=null && commonSport.getVcCgiName()!=null) {
					if (csList!=null && csList.size()>0) {				
						for (int k = 0; k < csList.size(); k++) {
							CommonSport cSport = csList.get(k);
							if (commonSport.getVcAreaName().equals(cSport.getVcAreaName()) && commonSport.getVcCgiName().equals(cSport.getVcCgiName())) {
								count++;
								flag = false;
								break;
							}
						}					
					}
					if (flag) {
						csList.add(commonSport);
					}
				}*/
				//小区集
				if (commonSport.getVcAreaName()!=null && commonSport.getVcCGI()!=null ) {
					if (csList!=null && csList.size()>0) {				
						for (int k = 0; k < csList.size(); k++) {
							CommonSport cSport = csList.get(k);
							if (commonSport.getVcAreaName().equals(cSport.getVcAreaName()) && commonSport.getVcCGI().equals(cSport.getVcCGI())) {
								count++;
								flag = false;
								break;
							}
						}					
					}
					if (flag) {
						csList.add(commonSport);
					}
				}								
				//去掉Areas相同的				
				if (!aList.contains(commonSport.getVcAreaName())) {
					aList.add(commonSport.getVcAreaName());
				}
				
			}	
			logger.info("重复记录："+count);
			logger.info(csList.size()+"");
			logger.info(aList.size()+"");
			importantCustomerManager.importArea(aList,intType);
			int errorCgi=importantCustomerManager.importAreaMapCell(csList,intType);
			if (errorCgi>0) {
				msg = "上传文件中重复记录："+count+"条，存在："+errorCgi+" 条记录在维表中缺少小区信息";
				request.setAttribute(VarConstants.ERROR_CODE,msg);
				return ERROR;
			}else{
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00006);
				return SUCCESS;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00011);
			return ERROR;
		}finally {		
			try {
				is.close();
			} catch (Exception e) {
				//logger.error(e.getMessage());
			}
		}
	}
	
	public void downloadTemplate() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String[] headsName = {"区域名称（*）","小区名","cgi（*）"};
		String fileName="";	
		if (intType==1) {
			fileName = "保障区域导入模板";			
		}
		if (intType==2) {
			fileName = "小区集导入模板";		
		}
		
		gridServerHandler.downloadFile(fileName + ".xls");

		ExcelStyle styles = new ExcelStyle();
		WritableCellFormat titlestyle = styles.getTableYellow();

		OutputStream out = this.getServletResponse().getOutputStream();
		AbstractXlsWriter xlsw = gridServerHandler.getXlsWriter();
		xlsw.init();
		xlsw.setOut(out);
		xlsw.setEncoding(gridServerHandler.getEncoding());
		xlsw.start();
		xlsw.addTitle(headsName, titlestyle, "");	
		xlsw.end();
		xlsw.close();
	}


	public Integer getIntType() {
		return intType;
	}

	public void setIntType(Integer intType) {
		this.intType = intType;
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
