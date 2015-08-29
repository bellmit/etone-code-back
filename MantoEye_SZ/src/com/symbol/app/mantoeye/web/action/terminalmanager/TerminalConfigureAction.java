package com.symbol.app.mantoeye.web.action.terminalmanager;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalConfigureManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;




public class TerminalConfigureAction extends BaseDispatchAction {
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;
	@Autowired
	private TerminalConfigureManager terminalConfigureManager;
	private String vcTerminalTac;
	private String vcTerminalBrand;// 开始时间
	private String vcTerminalName;// 结束时间
	private Long nmTerminalId;
	HttpServletRequest request = ServletActionContext.getRequest();
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<PropertyFilter> filters = HibernateWebUtils
		.buildPropertyFilters(Struts2Utils.getRequest());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmTerminalId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page =terminalConfigureManager.query(page, Common.OutConvert(vcTerminalTac), Common.OutConvert(vcTerminalBrand), Common.OutConvert(vcTerminalName));
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
		if (vcTerminalTac!=null && !vcTerminalTac.equals("")) {
			vcTerminalTac = new String(vcTerminalTac.getBytes("ISO-8859-1"),"UTF-8");
		}
		if (vcTerminalBrand!=null && !vcTerminalBrand.equals("")) {
			vcTerminalBrand = new String(vcTerminalBrand.getBytes("ISO-8859-1"),"UTF-8");
		}
		if (vcTerminalName!=null && !vcTerminalName.equals("")) {
			vcTerminalName = new String(vcTerminalName.getBytes("ISO-8859-1"),"UTF-8");
		}
		List<CommonSport> list = terminalConfigureManager.listData(Common.OutConvert(vcTerminalTac), Common.OutConvert(vcTerminalBrand), Common.OutConvert(vcTerminalName));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端配置" ;
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	@Override
	public String input() throws Exception {
		return INPUT;
	}
	
	public String edit() throws Exception {
		//entity = impCustomerNumManager.get(id);
		return EDIT;
	}
	
	public String saveTerminal() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			terminalConfigureManager.saveTerminal(Common.OutConvert(vcTerminalTac), Common.OutConvert(vcTerminalBrand), Common.OutConvert(vcTerminalName));
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加终端成功!";
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加终端失败!";
			return ERROR;
		}finally {
			commonManagerImpl.log(request, msg);
		}
	}
	
	public String updateTerminal() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			boolean flag = terminalConfigureManager.terminalUnique(nmTerminalId,Common.OutConvert(vcTerminalTac));
			if (flag) {
				msg = "编辑终端成功!";
				terminalConfigureManager.updateTerminal(Common.OutConvert(vcTerminalTac), Common.OutConvert(vcTerminalBrand), Common.OutConvert(vcTerminalName),nmTerminalId);
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00102);
				return SUCCESS;
			}else{
				msg = "终端识别前缀已存在!";
				request.setAttribute(VarConstants.ERROR_CODE,
						msg);
				return ERROR;
			}

		} catch (Exception e) {
			msg = "编辑终端失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					msg);
			return ERROR;
		}finally {
			commonManagerImpl.log(request, msg);
		}
	}
	
	/**
	 * 删除号码
	 */
	public void deleteTerminal() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String msg = "";
		try {// 删除成功
			terminalConfigureManager.deleteById(ids);
			msg = "删除终端成功!";
			commonManagerImpl.log(request, "删除终端成功[主键：" + ids + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除终端失败!";
			commonManagerImpl.log(request, "删除终端失败[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public String getVcTerminalTac() {
		return vcTerminalTac;
	}

	public void setVcTerminalTac(String vcTerminalTac) {
		this.vcTerminalTac = vcTerminalTac;
	}

	public String getVcTerminalBrand() {
		return vcTerminalBrand;
	}

	public void setVcTerminalBrand(String vcTerminalBrand) {
		this.vcTerminalBrand = vcTerminalBrand;
	}

	public String getVcTerminalName() {
		return vcTerminalName;
	}

	public void setVcTerminalName(String vcTerminalName) {
		this.vcTerminalName = vcTerminalName;
	}

	public Long getNmTerminalId() {
		return nmTerminalId;
	}

	public void setNmTerminalId(Long nmTerminalId) {
		this.nmTerminalId = nmTerminalId;
	}
	
	/**
	 * 判断该任务是否存在
	 */
	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcTerminalTac = request.getParameter("vcTerminalTac");
			try {
				vcTerminalTac = java.net.URLDecoder
						.decode(vcTerminalTac, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			}
			Boolean flag=terminalConfigureManager.terminalUnique(nmTerminalId,vcTerminalTac);
			if(flag){//-1表示不存在该任务名称
				Struts2Utils.renderText("true");
			}else{
				Struts2Utils.renderText("false");
			}
		return null;
	}
	
	public String upFile() {
		String msg = "";

		List<CommonSport> list = new ArrayList<CommonSport>();
		List<String> taclist = new ArrayList<String>();
		BufferedReader gbkbr = null;
		BufferedReader br = null;
		BufferedReader utf8br = null;
		String str = null;
		int error = 0;
		int total = 0;
		String gbkstr = "";
		String utf8str = "";
		String[] titles;
		int index = 0;
		int repeat = 0;
		try {
			gbkbr = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.getFile()), "GBK"));
			gbkstr = gbkbr.readLine();
			utf8br = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.getFile()), "UTF-8"));
			utf8str = utf8br.readLine();
			if (gbkstr.indexOf("终端识别前缀") != -1) {
				logger.info("$$$$$--GBK--$$$$$");
				titles = gbkstr.split("\\|");
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(this.getFile()), "GBK"));
			} else if (utf8str.indexOf("终端识别前缀") != -1) {
				logger.info("$$$$$--UTF-8--$$$$$");
				titles = utf8str.split("\\|");
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(this.getFile()), "UTF-8"));
			} else {
				logger.info("$$$$$--Other--$$$$$");
				titles = gbkstr.split("\\|");
				msg = "文件上传失败,文件必须包含标题!";
				request.setAttribute(VarConstants.ERROR_CODE,
						MsgConstants.ERROR_CODE_00013);
				return ERROR;
			}
			while ((str = br.readLine()) != null) {
				if (index++ == 0) {
					continue;
				}
				total++;
				CommonSport commonSport = new CommonSport();
				String[] strs = str.split("\\|");
				if (strs.length != 3) {
					error++;
					continue;
				}
				if (total < 5) {
					logger.info(str);
				}
				String vcTerminalTac = strs[0];
				String vcTerminalBrand = strs[1];
				String vcTerminalName = strs[2];
				commonSport.setVcTerminalTac(vcTerminalTac);
				commonSport.setVcTerminalBrand(vcTerminalBrand);
				commonSport.setVcTerminalName(vcTerminalName);
				if (!taclist.contains(vcTerminalTac)) {
					taclist.add(vcTerminalTac);
					list.add(commonSport);
				}else{
					repeat++;
				}
			}
			msg = "文件上传服务器,总记录数 :" + total + ", 无效记录数:" + error + "，终端识别前缀重复记录数："+repeat+"， 确定上传数据生效吗?";
			request.setAttribute(VarConstants.SUCC_CODE, "0000110");
			request.setAttribute("message", msg);
			request.getSession().setAttribute("terminalList", list);
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
			try {
				gbkbr.close();
			} catch (Exception e) {
				//logger.error(e.getMessage());
			}
			try {
				br.close();
			} catch (Exception e) {
				//logger.error(e.getMessage());
			}
		}

	}
	
	public void saveUpFile() {
		String msg = null;
		try {
			List<CommonSport> list = (List<CommonSport>) request.getSession().getAttribute("terminalList");
			if (list!=null && list.size()>0) {
				List<CommonSport> tList = terminalConfigureManager.queryAllTerminal();
				for (int i = 0; i < list.size(); i++) {
					CommonSport commonSport = list.get(i);
					boolean flag = true;
					for (int j = 0; j < tList.size(); j++) {
						CommonSport cs =  tList.get(j);
						if (cs.getVcTerminalTac().equals(commonSport.getVcTerminalTac())) {
							flag = false;
							break;
						}
					}
					if (flag) {
						terminalConfigureManager.saveTerminals(commonSport.getVcTerminalTac(), commonSport.getVcTerminalBrand(),commonSport.getVcTerminalName());
					}					
				}
			}
			msg = "文件上传成功,请耐心等待几份钟后台处理!";
			clearSession();
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			Struts2Utils.renderText(msg);

		}
	}
	
	
	public void clearSession() {
		logger.info("清空session");
		request.getSession().removeAttribute("terminalList");
	}
}
