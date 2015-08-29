package com.symbol.app.mantoeye.web.action.mms;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jxl.write.WritableCellFormat;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.symbol.app.mantoeye.dto.mms.SpPortControlBean;
import com.symbol.app.mantoeye.service.mms.SpPortControlManager;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.export.AbstractXlsWriter;
import com.symbol.wp.tools.gtgrid.export.ExcelStyle;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.BeanUtils;

/**
 * 彩信端口管理ACTION
 * 
 * @author Jane Wu
 * 
 */
@Controller("spPortControlAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class SpPortControlAction extends BaseAction {

	private static final long serialVersionUID = 7490159581664997533L;
	private File file;

	private SpPortControlManager spPortControlManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String spPort;
	private String beLong;
	private String companyName;
	private String spPortMapId;

	private SpPortControlBean spPortBean;

	private String msg;

	public String add() {
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			spPortControlManager.add(spPortBean);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加彩信端口["+spPortBean.getSpPort()+"]信息成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加彩信端口["+spPortBean.getSpPort()+"]信息失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
		
	}

	public void spPortIsExist() {
		buildSpPortBean();
		spPortBean = spPortControlManager.findById(spPortBean.getSpPort());

		if (null == spPortBean || null == spPortBean.getSpPort()
				|| "".equals(spPortBean.getSpPort())) {
			msg = "noExist";
		} else {
			msg = "exist";
		}

		Struts2Utils.renderText(msg);

	}

	private void buildSpPortBean() {
		spPortBean = new SpPortControlBean();

		spPortBean.setBeLong(this.beLong);
		spPortBean.setCompanyName(this.companyName);
		spPortBean.setSpPort(this.spPort);
		spPortBean.setSpPortMapId(Common.StringToLong(this.spPortMapId));

	}

	public void remove() {
		// buildSpPortBean();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String[] sids = ids.split(",");
		try {
			for (String is : sids) {
				spPortControlManager.remove(is);
			}
			msg = "删除彩信端口["+ids+"]信息成功！";
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			msg = "删除彩信端口["+ids+"]信息失败！";
		}
		commonManagerImpl.log(request, msg);
		Struts2Utils.renderText(msg);

	}

	public String editPrePare() {
		// buildSpPortBean();
		try {
			spPortBean = spPortControlManager.findById(spPortBean.getSpPort());
			return "editPrePare";
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			msg = "加载数据失败！";
			return ERROR;
		}

	}

	public String edit() {
		// buildSpPortBean();
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			spPortControlManager.edit(spPortBean);
			msg = "更新彩信端口["+spPortBean.getSpPort()+"]信息成功！";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "更新彩信端口["+spPortBean.getSpPort()+"]信息失败！";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	public void query() {
		buildSpPortBean();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<SpPortControlBean> list = spPortControlManager.findByPage(
				gridServerHandler, spPortBean);

		gridServerHandler.setData(list, SpPortControlBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	public void export() throws IOException {
		buildSpPortBean();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<SpPortControlBean> list = spPortControlManager.findAll(spPortBean);
		String emsg = "导出端口信息数据！";
		commonManagerImpl.log(Struts2Utils.getRequest(), emsg);
		gridServerHandler.exportXLS(list, SpPortControlBean.class);
	}

	public String importXlsFile() {
		HttpServletRequest request = Struts2Utils.getRequest();

		try {
			Map<String, SpPortControlBean> map = spPortControlManager
					.loadXlsFile(file);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00107);
			spPortControlManager.importElsFile(map);
			msg = "导入彩信端口信息成功！";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "导入彩信端口信息失败！";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}

	}
	public void downloadTemplate() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String fileName = "端口导入模板";
		String [] headsName = {"彩信端口号","彩信所属","公司名称"};
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public SpPortControlBean getSpPortBean() {
		return spPortBean;
	}

	public void setSpPortBean(SpPortControlBean spPortBean) {
		this.spPortBean = spPortBean;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public SpPortControlManager getSpPortControlManager() {
		return spPortControlManager;
	}

	@Resource
	public void setSpPortControlManager(
			SpPortControlManager spPortControlManager) {
		this.spPortControlManager = spPortControlManager;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSpPort() {
		return spPort;
	}

	public void setSpPort(String spPort) {
		this.spPort = spPort;
	}

	public String getBeLong() {
		return beLong;
	}

	public void setBeLong(String beLong) {
		this.beLong = beLong;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSpPortMapId() {
		return spPortMapId;
	}

	public void setSpPortMapId(String spPortMapId) {
		this.spPortMapId = spPortMapId;
	}

}
