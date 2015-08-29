package com.symbol.app.mantoeye.web.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.AppServer;
import com.symbol.app.mantoeye.entity.FtbDataGetterDecTask;
import com.symbol.app.mantoeye.entity.FtbDataGetterTaskFileInfo;
import com.symbol.app.mantoeye.service.DataGetterTaskDecFileInfoManager;
import com.symbol.app.mantoeye.service.DataGetterTaskFileInfoManager;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class DataGetterTaskFileInfoAction extends BaseDispatchAction {

	@Autowired
	private DataGetterTaskFileInfoManager dataGetterTaskFileInfoManager;
	
	@Autowired
	private DataGetterTaskDecFileInfoManager dataGetterTaskDecFileInfoManager;
	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;
	
	@Autowired
	private TerminalManager baseTerminalManager;
	
	private static final int BUFFER_SIZE = 16 * 1024;

	private Long id;

	private String keys;

	HttpServletRequest request = ServletActionContext.getRequest();

	private Page<FtbDataGetterTaskFileInfo> page = new Page<FtbDataGetterTaskFileInfo>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);
	private Page<FtbDataGetterDecTask> decPage = new Page<FtbDataGetterDecTask>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public Page<FtbDataGetterTaskFileInfo> getPage() {
		return page;
	}

	public void setPage(Page<FtbDataGetterTaskFileInfo> page) {
		this.page = page;
	}

	public void query() throws ServletException, IOException {
//		try{
		HttpServletRequest request = Struts2Utils.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		Long taskid = Common.StringToLong(request.getParameter("taskid"));
		String tasktype = request.getParameter("tasktype");
		
		logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+taskid);
		
		if ("3".equals(tasktype) || "4".equals(tasktype)) {// 解析数据
			filters.add(new PropertyFilter("nmDataGetterTaskId", taskid,
					MatchType.EQ));
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			decPage.setOrderBy("dtEtime");
			decPage.setOrder("desc");
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = dataGetterTaskDecFileInfoManager.find(filters)
						.size();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			decPage.setPageSize(gridServerHandler.getPageSize());
			decPage.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			decPage = dataGetterTaskDecFileInfoManager.search(decPage, filters);
			List<FtbDataGetterDecTask> list = decPage.getResult();
			gridServerHandler.setData(this.formatViewData(list, true));
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} else {
			filters
					.add(new PropertyFilter(
							"ftbDataGetterServerMapTask.ftbDataGetterTask.nmDataGetterTaskId",
							taskid, MatchType.EQ));
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			page.setOrderBy("dtEtime");
			page.setOrder("desc");

			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = dataGetterTaskFileInfoManager.find(filters)
						.size();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			page = dataGetterTaskFileInfoManager.search(page, filters);
			List<FtbDataGetterTaskFileInfo> list = page.getResult();
			logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$list$"+list.size());
			gridServerHandler.setData(this.formatViewData(list, false));
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		}
//		}catch(Exception e){
//			logger.error(e.getMessage());
//		}

	}

	/**
	 * 页面视图数据
	 */
	private List formatViewData(List list, boolean isDes) {
		List maplist = new ArrayList();
		Map map;
		if (isDes) {// 解析数据
			FtbDataGetterDecTask bean;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (FtbDataGetterDecTask) list.get(i);
					map = new LinkedHashMap();
					map
							.put("nmTaskFileInfoId", bean
									.getNmDataGetterDecTaskId());
					map.put("serverName", CommonUtils.killNull(bean
							.getVcFileServerIp()));// 文件存储服务器IP
					map.put("serverIP", CommonUtils.killNull(bean
							.getVcFileServerIp()));// 文件存储服务器Name
					map.put("dtETime", CommonUtils.formatFullDate(bean
							.getDtEtime()));// 时间
					map.put("nmFileSize",CommonUtils.formatBToKb(bean.getNmFileSize()));// 文件大小
					map.put("vcFilePath", CommonUtils.killNull(bean
							.getVcFilePath()));// 文件路径
					map.put("vcFileFormat", CommonUtils.killNull(bean
							.getVcFileFormat()));
					map.put("isDec", "true");// 是否解析数据
					maplist.add(map);
				}
			}
		} else {
			FtbDataGetterTaskFileInfo bean;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (FtbDataGetterTaskFileInfo) list.get(i);
					map = new LinkedHashMap();
					map.put("nmTaskFileInfoId", bean.getNmTaskFileInfoId());
					map.put("serverName", CommonUtils.killNull(bean
							.getVcFileServerIp()));// 文件存储服务器IP
					map.put("serverIP", CommonUtils.killNull(bean
							.getFtbDataGetterServerMapTask().getFtbServerList()
							.getVcServerIp()));// 文件存储服务器Name
					map.put("dtETime", CommonUtils.formatFullDate(bean
							.getDtEtime()));// 时间
					map.put("nmFileSize", CommonUtils.formatBToKb(bean.getNmFileSize()));// 文件大小
					map.put("vcFilePath", CommonUtils.killNull(bean
							.getVcFilePath()));// 文件路径
					map.put("vcFileFormat", CommonUtils.killNull(bean
							.getVcFileFormat()));
					map.put("isDec", "false");// 是否解析数据
					maplist.add(map);
				}
			}
		}
		return maplist;
	}

	public void download() throws Exception {
		String fileid = request.getParameter("fileid");
		String isDec = request.getParameter("isDec");// 是否解析数据
		InputStream in = null;
		OutputStream out = getServletResponse().getOutputStream();
		String msg = "无法从文件服务器获取数据文件，文件可能已经被删除，请联系管理员！";
		Long kid = Common.StringToLong(fileid);
		String name = "unknown";
		String path = "";
		try {
			boolean isExit = false;
			if ("true".equals(isDec)) {// 解析数据
				FtbDataGetterDecTask bean = dataGetterTaskDecFileInfoManager
						.get(kid);
				if (bean != null) {
					isExit = true;
					path = bean.getVcFilePath();
				}
			} else {
				FtbDataGetterTaskFileInfo bean = dataGetterTaskFileInfoManager
						.get(kid);
				if (bean != null) {
					isExit = true;
					path = bean.getVcFilePath();
				}
			}
			
			logger.info("*****************isExit:"+isExit);
			
			if (isExit) {
				getServletResponse().setContentType("application/octet-stream");
				in = new BufferedInputStream(new FileInputStream(path),
						BUFFER_SIZE);
				if (path.indexOf("/") > 0) {
					name = path.substring(path.lastIndexOf("/"), path.length());
				} else if (path.indexOf("\\\\") > 0) {
					name = path.substring(path.lastIndexOf("\\\\"), path
							.length());
				}
				getServletResponse().setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode(name, "utf-8"));
				byte[] buffer = new byte[BUFFER_SIZE];
				int len = 0;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			} else {
				logger.info("--无法获取文件信息---");
				getServletResponse().setContentType("text/html;charset=gb2312");
				out.write(msg.getBytes());
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			getServletResponse().setContentType("text/html;charset=gb2312");
			out.write(msg.getBytes());
		} finally {
			if (null != in) {
				in.close();
			}
		}
	}
	
	public void downloadForRomote() throws Exception {
		logger.info("*****************download in********");
		String fileid = request.getParameter("fileid");
		String isDec = request.getParameter("isDec");// 是否解析数据
		InputStream in = null;
		OutputStream out = getServletResponse().getOutputStream();
		String msg = "无法从文件服务器获取数据文件，文件可能已经被删除，请联系管理员！";
		Long kid = Common.StringToLong(fileid);
		String localDir=PropertiesUtil.getInstance().getProperty(
		"data_catch_localDir");;
		String name = "unknown";
		String fileName = "";
		String user="";
		String passwd="";
		String serverIp="";
		int port=21;
		FtbDataGetterDecTask bean=null;
		FtbDataGetterTaskFileInfo bean2=null;
		String remoteDir=null;
		try {
			boolean isExit = false;
			if ("true".equals(isDec)) {// 解析数据
				bean = dataGetterTaskDecFileInfoManager
						.get(kid);
				logger.info(bean+"-v-");
				if (bean != null) {
					isExit = true;
					String path=bean.getVcFilePath();
					serverIp=bean.getVcFileServerIp();
					logger.info(path+"-v-"+serverIp);
					if(path.indexOf("/")>=0){
						fileName=path.substring(path.lastIndexOf("/")+1, path.length());
						remoteDir=path.substring(0, path.lastIndexOf("/"));
					}else{
						fileName=path.substring(path.lastIndexOf("\\")+1, path.length());
						remoteDir=path.substring(0, path.lastIndexOf("\\"));
					}
					logger.info(fileName+"---"+remoteDir);
				}
			} else {
				 bean2= dataGetterTaskFileInfoManager
						.get(kid);
				 logger.info(bean2+"-vv-");
				if (bean2 != null) {
					isExit = true;
					 String path=bean2.getVcFilePath();
					 serverIp=bean2.getVcFileServerIp();
					if(path.indexOf("/")>=0){
						fileName=path.substring(path.lastIndexOf("/")+1, path.length());
						remoteDir=path.substring(0, path.lastIndexOf("/"));
					}else{
						fileName=path.substring(path.lastIndexOf("\\")+1, path.length());
						remoteDir=path.substring(0, path.lastIndexOf("\\"));
					}
				}
			}
			logger.info("*****************fileName********"+fileName);
			logger.info("*****************remoteDir********"+remoteDir);
			AppServer appServer=baseTerminalManager.getAppServerByServerIp(serverIp);
			
			String serverIpOut= appServer.getServerIpOut();//外网ip
			//因为部署了几个tomcat，通过参数判断是使用外网ip还是内网ip
			String iptype = PropertiesUtil.getInstance().getProperty(
				"file.server.ip.type");
			
			logger.info("---------------iptype-------------"+iptype+"------------------------");
			
			logger.info("*****************serverIp---------------"+serverIp);
			logger.info("*****************serverIpOut---------------"+serverIpOut);
			if(iptype.equals("in")){
				serverIpOut= serverIp;
			}
			
			logger.info("---------------serverIpOut-------------"+serverIpOut+"------------------------");
			user=appServer.getUserName();
			passwd=appServer.getPassWd();
			String filePath=null;
			if(localDir.endsWith("/")){
				filePath=localDir+fileName;
			}else{
				filePath=localDir+"/"+fileName;
			}
			//logger.info("*****************appServer********"+appServer);
			if(!new File(filePath).exists()){//如果该文件在本服务器中不存在  就FTP去拿
				if("true".equals(isDec)){
					serverIp=bean.getVcFileServerIp();
					//filePath=bean.getVcFilePath();
				}else{
					serverIp=bean2.getVcFileServerIp();
					//filePath=bean2.getVcFilePath();
				}
				//FtpClientOperator ftp=new FtpClientOperator(serverIp,port,user,passwd);
				logger.info("***||**************serverIpOut********"+serverIpOut);
				logger.info("****||************port********"+port);
				logger.info("*****||************user********"+user);
				logger.info("******||***********passwd********"+passwd);
				
				logger.info("***||**************remoteDir********"+remoteDir);
				logger.info("****||************fileName********"+fileName);
				logger.info("*****||************localDir********"+localDir);
				logger.info("******||***********fileName********"+fileName);
				
				FtpClientOperator ftp=new FtpClientOperator(serverIpOut,port,user,passwd);
				ftp.connectFtpServer();
				ftp.download(remoteDir, fileName, localDir, fileName);
				ftp.closeConnect();
			}
			 
			if (isExit) {
				getServletResponse().setContentType("application/octet-stream");
				in = new BufferedInputStream(new FileInputStream(filePath),
						BUFFER_SIZE);
				if (filePath.indexOf("/") >= 0) {
					name = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
				} else if (filePath.indexOf("\\\\") >= 0) {
					name = filePath.substring(filePath.lastIndexOf("\\\\")+1, filePath
							.length());
				}else{
					logger.info("-test-"+filePath.indexOf("/"));
				}
				getServletResponse().setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode(name, "utf-8"));
				byte[] buffer = new byte[BUFFER_SIZE];
				int len = 0;
				while ((len = in.read(buffer))!= -1) {
					out.write(buffer, 0, len);
				}
				
				/*写日志*/
				String taskType = request.getParameter("taskType");
				String taskName = request.getParameter("taskName"); 
				String tan = "";
				if(taskName!=null&&!taskName.equals("")){
					tan = new String(taskName.getBytes("ISO-8859-1"), "UTF-8");
				}
				logger.info(taskType+"--"+taskName+"--"+tan);
				String exportmsg = ExportMsg.DOWNLOAD_BUSINESS_TEST+"（任务："+tan+"）";
				if(taskType.equals("2")){
					exportmsg = ExportMsg.DOWNLOAD_DATACATCH_CURRENTCAP+"（任务："+tan+"）";	
				}else if(taskType.equals("3")){
					exportmsg = ExportMsg.DOWNLOAD_DATACATCH_HISTORYANALY+"（任务："+tan+"）";	
				}else if(taskType.equals("4")){
					exportmsg = ExportMsg.DOWNLOAD_DATACATCH_MSISDN+"（任务："+tan+"）";	
				}else if(taskType.equals("1")){
					exportmsg = ExportMsg.DOWNLOAD_DATACATCH_HISTORYCAP+"（任务："+tan+"）";	
				}
				commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );				
				
			} else {
				logger.info("--无法获取文件信息---");
				getServletResponse().setContentType("text/html;charset=gb2312");
				out.write(msg.getBytes());
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			logger.info(ex.toString());
			logger.info(ex.getMessage());
			getServletResponse().setContentType("text/html;charset=gb2312");
			out.write(msg.getBytes());
		} finally {
			if (null != in) {
				in.close();
			}
		}
	}

	public DataGetterTaskFileInfoManager getDataGetterTaskFileInfoManager() {
		return dataGetterTaskFileInfoManager;
	}

	public void setDataGetterTaskFileInfoManager(
			DataGetterTaskFileInfoManager dataGetterTaskFileInfoManager) {
		this.dataGetterTaskFileInfoManager = dataGetterTaskFileInfoManager;
	}

	public DataGetterTaskDecFileInfoManager getDataGetterTaskDecFileInfoManager() {
		return dataGetterTaskDecFileInfoManager;
	}

	public void setDataGetterTaskDecFileInfoManager(
			DataGetterTaskDecFileInfoManager dataGetterTaskDecFileInfoManager) {
		this.dataGetterTaskDecFileInfoManager = dataGetterTaskDecFileInfoManager;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Page<FtbDataGetterDecTask> getDecPage() {
		return decPage;
	}

	public void setDecPage(Page<FtbDataGetterDecTask> decPage) {
		this.decPage = decPage;
	}

}
