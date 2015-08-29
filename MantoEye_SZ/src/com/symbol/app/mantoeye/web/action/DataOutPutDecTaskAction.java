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
import com.symbol.app.mantoeye.entity.FtbDataOutPutDecTask;
import com.symbol.app.mantoeye.service.DataOutPutDecTaskManager;
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

public class DataOutPutDecTaskAction extends BaseDispatchAction {

	@Autowired
	private DataOutPutDecTaskManager dataOutPutDecTaskManager;
	

	@Resource(name="commonManagerImpl")
	public ICommonManager commonManagerImpl;
	
	@Autowired
	private TerminalManager baseTerminalManager;
	private static final int BUFFER_SIZE = 16 * 1024;

	private Long id;

	private String keys;

	HttpServletRequest request = ServletActionContext.getRequest();

	private Page<FtbDataOutPutDecTask> page = new Page<FtbDataOutPutDecTask>(
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



	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		Long taskid = Common.StringToLong(request.getParameter("taskid"));
		//String tasktype = request.getParameter("tasktype");
		logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+taskid);	
			filters.add(new PropertyFilter("nmDataOutPutTaskId", taskid,
					MatchType.EQ));
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			page.setOrderBy("nmDataOutPutDecTaskId");
			page.setOrder("desc");
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = dataOutPutDecTaskManager.find(filters).size();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			page.setPageSize(gridServerHandler.getPageSize());
			page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			page = dataOutPutDecTaskManager.search(page, filters);
			List<FtbDataOutPutDecTask> list = page.getResult();
			gridServerHandler.setData(this.formatViewData(list));
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());


	}

	/**
	 * 页面视图数据
	 */
	private List formatViewData(List list) {
		List maplist = new ArrayList();
		Map map;
			FtbDataOutPutDecTask bean;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (FtbDataOutPutDecTask) list.get(i);
					map = new LinkedHashMap();
					map
							.put("nmDataOutPutDecTaskId", bean
									.getNmDataOutPutDecTaskId());
					map.put("serverName", CommonUtils.killNull(bean
							.getVcFileServerIp()));// 文件存储服务器IP
					map.put("serverIP", CommonUtils.killNull(bean
							.getVcFileServerIp()));// 文件存储服务器Name
					map.put("nmFileSize",CommonUtils.formatBToKb(bean.getNmFileSize()));// 文件大小
					map.put("vcFilePath", CommonUtils.killNull(bean
							.getVcFilePath()));// 文件路径
					map.put("vcFileName", CommonUtils.killNull(bean
							.getVcFileName()));// 文件名称
					map.put("vcFileFormat", CommonUtils.killNull(bean
							.getVcFileFormat()));
					map.put("isDec", "true");// 是否解析数据
					maplist.add(map);
				}
			}
		return maplist;
	}


	
	public void downloadForRomote() throws Exception {
		logger.info("*****************download in********");
		String fileid = request.getParameter("fileid");
		InputStream in = null;
		OutputStream out = getServletResponse().getOutputStream();
		String msg = "无法从文件服务器获取数据文件，文件可能已经被删除，请联系管理员！";
		Long kid = Common.StringToLong(fileid);
		String localDir=PropertiesUtil.getInstance().getProperty("data_catch_localDir");;
		String name = "unknown";
		String fileName = "";
		String user="";
		String passwd="";
		String serverIp="";
		int port=21;
		FtbDataOutPutDecTask bean=null;
		String remoteDir=null;
		try {
			boolean isExit = false;
				bean = dataOutPutDecTaskManager.get(kid);
				logger.info(bean+"-v-");
				if (bean != null) {
					isExit = true;
					String path=bean.getVcFilePath().trim();
					serverIp=bean.getVcFileServerIp();
					fileName = bean.getVcFileName().trim();
					remoteDir = path;
					logger.info(fileName+"---"+remoteDir);
				}
			logger.info("*****************fileName********"+fileName);
			logger.info("*****************remoteDir********"+remoteDir);
			AppServer appServer=baseTerminalManager.getAppServerByServerIp(serverIp);
			
			String serverIpOut= appServer.getServerIpOut();//外网ip
			//因为部署了几个tomcat，通过参数判断是使用外网ip还是内网ip
			String iptype = PropertiesUtil.getInstance().getProperty("file.server.ip.type");
			
			if(iptype.equals("in")){
				serverIpOut= serverIp;
			}
			
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
				serverIp=bean.getVcFileServerIp();
				FtpClientOperator ftp=new FtpClientOperator(serverIpOut,port,user,passwd);
				ftp.connectFtpServer();
				ftp.download(remoteDir, fileName, localDir, fileName);
				ftp.deleteFile(remoteDir, fileName);
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
	

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}



}
