package com.symbol.app.mantoeye.web.action.business;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.businessRule.BusinessSearchManager;
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
 * 业务规则搜索功能对照
 * 
 * 
 */
public class BusinessSearchAction extends BaseDispatchAction {
	@Autowired
	private BusinessSearchManager businessSearchManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;
	private File file;
	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String vcTitleType;
	private String vcTitle;
	private String ids;
	
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<CommonSport> list = null;

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {
			SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
		} else {
			page.setOrder("desc");
			page.setOrderBy("nmBussinessSearchId");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = businessSearchManager.query(page,vcTitleType,vcTitle);
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

	
	public void deleteBusiness() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String msg = "";
		try {
			businessSearchManager.deleteBusinessSearch(ids);
			msg = "删除搜索网站成功!";
			commonManagerImpl.log(request, "删除搜索网站成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除搜索网站失败!";
			logger.error(e.toString());
			logger.error(e.getMessage());
			commonManagerImpl.log(request, "删除搜索网站失败!");
		}
		Struts2Utils.renderText(msg);
	}
	
	/**
	 * 保存搜索网站
	 */
	public String saveSearchWebSite() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		vcTitleType = Common.OutConvert(vcTitleType);
		vcTitle = Common.OutConvert(vcTitle);
		try {
				List<CommonSport> list = businessSearchManager.queryAllBussList();
					boolean flag = true;
					if (list!=null && list.size()>0) {
						for (CommonSport commonSport : list) {
							if (vcTitleType.equals(commonSport.getVcTitleType())&&vcTitle.equals(commonSport.getVcTitle())) {
								flag = false;
								break;
							}
						}
					}
					if (flag && !vcTitleType.equals("") && !vcTitle.equals("")) {
						String vcKeyword = URLEncoder.encode(vcTitle,"utf-8");
						businessSearchManager.saveBusinessSearch(vcTitleType,vcTitle,vcKeyword);
					}				
				
			msg = "添加业务规则搜索功能对照成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加业务规则搜索功能对照失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}
	
	public String upFile() {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		jxl.Workbook rwb = null;
		InputStream is=null;
		String vcTitleType=null;
		String vcTitle=null;
		try {
			List<CommonSport> list = businessSearchManager.queryAllBussList();
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
					if (!"栏目类型".equalsIgnoreCase(c.getContents())) {
						logger.info("上传文件标题出错");
					}
				} else if (j == 1) {
					if (!"类型".equalsIgnoreCase(c.getContents())) {
						logger.info("上传文件标题出错");
					}
				} 
			}
			int count = 0;
			for (int i = 1; i < rsRows; i++) {
				for (int j = 0; j < rsColumns; j++) {				
					Cell cell = rs.getCell(j, i);
					if (j == 0) {
						if(cell.getContents()!=null && !cell.getContents().equals("")){
							vcTitleType = cell.getContents().trim();
						}	
					} else if (j == 1) {
						if(cell.getContents()!=null && !cell.getContents().equals("")){
							vcTitle = cell.getContents().trim();
						}		
					} 
				}				
				//确定没有重复的
				boolean flag = true;
				
				if (vcTitleType!=null && vcTitle!=null ) {
					if (list!=null && list.size()>0) {
						for (CommonSport commonSport : list) {
							if (vcTitleType.equals(commonSport.getVcTitleType())&&vcTitle.equals(commonSport.getVcTitle())) {
								flag = false;
								break;
							}
						}
					}
					if (flag) {
						String vcKeyword = URLEncoder.encode(vcTitle,"utf-8");
						businessSearchManager.saveBusinessSearch(vcTitleType,vcTitle,vcKeyword);
					}
				}								
				
			}	
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00006);
			return SUCCESS;		
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
	
	public Page<CommonSport> getPage() {
		return page;
	}


	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}


	public String getVcTitleType() {
		return vcTitleType;
	}


	public void setVcTitleType(String vcTitleType) {
		this.vcTitleType = vcTitleType;
	}


	public String getVcTitle() {
		return vcTitle;
	}


	public void setVcTitle(String vcTitle) {
		this.vcTitle = vcTitle;
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		if (vcTitleType != null) {
			vcTitleType = new String(vcTitleType.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		if (vcTitle != null) {
			vcTitle = new String(vcTitle.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		List<CommonSport> list = businessSearchManager.listData(Common.OutConvert(vcTitleType),Common.OutConvert(vcTitle));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "业务规则搜索功能对照";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	
}
