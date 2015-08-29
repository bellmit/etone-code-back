package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.dto.AppServer;
import com.symbol.app.mantoeye.dto.TermDataLoadTask;
import com.symbol.app.mantoeye.dto.flush.TerminalEntity;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalManager;
import com.symbol.app.mantoeye.web.action.FtpClientOperator;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class TerminalManagerAction extends BaseDispatchAction implements
		IAction {
	@Autowired
	private TerminalManager baseTerminalManager;
	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<TerminalEntity> page = new Page<TerminalEntity>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void query() {
		String terminal = request.getParameter("terminal");
		TerminalEntity tEntity = new TerminalEntity();
		if (terminal != null && terminal.trim() != "") {
			tEntity.setTerminalType(terminal);
		}
		List<TerminalEntity> list = new ArrayList<TerminalEntity>();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		if (terminal != null) {
			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			page = baseTerminalManager.queryTerminal(page, tEntity);
			logger.info("***************************"+page.getPageNo());
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = page.getTotalCount();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			list = page.getResult();
		}
		gridServerHandler.setData(list, TerminalEntity.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText(),
				new String[0]);

	}

	public void saveUpdate() {

	}

	public String upFile() {
		String msg = "";

		List<TerminalEntity> list = new ArrayList<TerminalEntity>();
		BufferedReader gbkbr = null;
		BufferedReader utf8br = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		int error = 0;
		int total = 0;
		String path = PropertiesUtil.getInstance().getProperty(
				"system.terminal.upload.localDir");
		if (!path.endsWith("/"))
			path = path + "/";
		String fileName = new Date().getTime() + "terminal.dat";
		String file = path + fileName;
		String gbkstr = "";
		String utf8str = "";
		int msindex = 0;
		int terindex = 1;
		int brandindex = 3;
		String[] titles;
		int index = 0;
		try {
			gbkbr = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.getFile()), "GBK"));
			utf8br = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.getFile()), "UTF-8"));
			gbkstr = gbkbr.readLine();
			utf8str = utf8br.readLine();
			if (gbkstr.indexOf("号码") != -1) {
				logger.info("$$$$$--GBK--$$$$$");
				titles = gbkstr.split("\t");
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(this.getFile()), "GBK"));
			} else if (utf8str.indexOf("号码") != -1) {
				logger.info("$$$$$--UTF-8--$$$$$");
				titles = utf8str.split("\t");
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(this.getFile()), "UTF-8"));
			} else {
				logger.info("$$$$$--Other--$$$$$");
				titles = gbkstr.split("\t");
				msg = "文件上传失败,文件必须包含标题!";
				request.setAttribute(VarConstants.ERROR_CODE,
						MsgConstants.ERROR_CODE_00013);
				return ERROR;
			}
			for (int i = 0; i < titles.length; i++) {
				if (titles[i].equals("号码")) {
					msindex = i;
				}
				if (titles[i].equals("终端")) {
					terindex = i;
				}
				if (titles[i].equals("品牌")) {
					brandindex = i;
				}
			}
			pw = new PrintWriter(new FileWriter(file));
			while ((str = br.readLine()) != null) {
				if (index++ == 0) {
					continue;
				}
				total++;
				TerminalEntity terminalEntity = new TerminalEntity();
				String[] strs = str.split("\t");
				if (strs.length != 3) {
					error++;
					continue;
				}
				if (total < 5) {
					logger.info(str);
				}
				String msisdn = strs[0];
				if (msisdn.length() == 11 || msisdn.length() == 13) {

					if (msisdn.length() == 11) {
						msisdn = "86" + msisdn;
					}
					if (!this.isNumeric(msisdn)) {
						error++;
						continue;
					}
					pw.println(str);
					pw.flush();

				} else {
					error++;
					continue;
				}
			}

			// 通过ftp上传到服务器
			// saveUpFile();

			msg = "文件上传服务器,总记录数 :" + total + ", 无效记录数:" + error + " 确定上传数据生效吗?";
			request.setAttribute(VarConstants.SUCC_CODE, "0000110");
			request.setAttribute("message", msg);
			request.getSession().setAttribute("filePath", fileName);
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
			if(pw!=null){
				pw.flush();
				pw.close();
			}		
			try {
				utf8br.close();
			} catch (Exception e) {
				//logger.error(e.getMessage());
			}
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

	public void setPage(Page<TerminalEntity> page) {
		this.page = page;
	}

	public void saveUpFile() {
		String filePath = (String) request.getSession()
				.getAttribute("filePath");
		String serverIp = PropertiesUtil.getInstance().getProperty(
				"terminal_upload_server_ftp_ip");
		String localDir = PropertiesUtil.getInstance().getProperty(
				"system.terminal.upload.localDir");
		String remoteDir = PropertiesUtil.getInstance().getProperty(
				"terminal_upload_remoteDir");
		String iptype = PropertiesUtil.getInstance().getProperty("file.server.ip.type");
		// 根据对应的IP查找服务器信息
		AppServer appServer =new AppServer();
		String dbserverip = serverIp;
		if(iptype.equals("in")){
			appServer = baseTerminalManager
			.getAppServerByServerIp(serverIp);
		}else{
			appServer = baseTerminalManager
				.getAppServerByOutIp(serverIp);
			dbserverip = appServer.getServerIP();
		}
		int serverPort = 21;
		String user = appServer.getUserName();
		String passwd = appServer.getPassWd();

		String msg = null;
		try {
			FtpClientOperator ftp = new FtpClientOperator(serverIp, serverPort,
					user, passwd);
			ftp.connectFtpServer();
			ftp.upload(localDir, filePath, remoteDir);
			ftp.closeConnect();

			if (!remoteDir.endsWith("/"))
				remoteDir = remoteDir + "/";
			filePath = remoteDir + filePath;
			msg = "文件上传成功,请耐心等待几份钟后台处理!";

			// 插入上传终端信息 等待后台处理
			baseTerminalManager.saveUpFile(dbserverip, filePath);

			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			Struts2Utils.renderText(msg);

		}
	}

	public String delete() throws Exception {
		/*
		 * try{ msg = "文件上传成功!";
		 * 
		 * baseTerminalManager.insertTerminal(list);
		 * commonManagerImpl.log(request, msg);
		 * request.setAttribute(VarConstants.SUCC_CODE,
		 * MsgConstants.SUCC_CODE_00006); return SUCCESS; }catch(Exception e){
		 * logger.error(e.getMessage()); msg = "文件上传失败!";
		 * request.setAttribute(VarConstants.SUCC_CODE,
		 * MsgConstants.ERROR_CODE_00011); return ERROR;
		 * 
		 * }
		 */
		return null;
	}

	public String edit() throws Exception {
		return null;
	}

	public String save() throws Exception {
		return null;
	}

	public String update() throws Exception {
		return null;
	}

	/**
	 * 列表显示页面 分页显示总行时有问题，分页还需要测试
	 */
	public String list() throws Exception {
		// this.setPaginationdataList();
		return INDEX;
	}

	public boolean isNumeric(String str) {// 判断是否是数字类型
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public List<TerminalEntity> readFile() {// 读取上传文件
		List<TerminalEntity> list = new ArrayList<TerminalEntity>();
		BufferedReader br = null;
		String str = null;
		try {
			try {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(this.getFile()), "utf-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			try {
				while ((str = br.readLine()) != null) {

					TerminalEntity terminalEntity = new TerminalEntity();
					String[] strs = str.split("\t");
					String msisdn = strs[0];
					if (msisdn.length() != 11)
						continue;
					msisdn = "86" + msisdn;
					if (this.isNumeric(msisdn)) {
						terminalEntity.setMsisdn(Common.StringToLong(msisdn));
						terminalEntity.setTerminalBrand(strs[1]);
						terminalEntity.setTerminalType(strs[2]);
						list.add(terminalEntity);
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return list;
	}

	public void clearTable() {

		String msg = "";

		try {
			msg = "清除终端成功!";
			baseTerminalManager.clearTable();
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "清除终端失败!";
			Struts2Utils.renderText(msg);

		}
	}

	/**
	 * 查看所有的上传任务
	 * 
	 * @return
	 */
	public void findUpFiles() {
		List<TermDataLoadTask> list = baseTerminalManager.findUpFiles();

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		gridServerHandler.setData(list, TermDataLoadTask.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText(),
				new String[0]);
	}
}
