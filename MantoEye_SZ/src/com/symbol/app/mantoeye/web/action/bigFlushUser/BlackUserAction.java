package com.symbol.app.mantoeye.web.action.bigFlushUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.BigFlushUser;
import com.symbol.app.mantoeye.entity.business.BlackUser;
import com.symbol.app.mantoeye.service.bigflushuser.BlackUserManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class BlackUserAction extends BaseDispatchAction implements IAction {
	HttpServletRequest request = ServletActionContext.getRequest();
	
	private Page<BlackUser> page = new Page<BlackUser>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	
	@Autowired
	private BlackUserManager blackUserManager;
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;
	
	private BlackUser entity;
	private Long id;
	private String keys; 
	private int isFile;
	private String msisdn;
	private File myFile;
	private String descrite;
	private String msn;
	private String dmsisdn;
	
	public String delete() throws Exception {
		
		return null;
	}
	
	public void deleteUser() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String msg = "";
		try {// 删除成功
			blackUserManager.deleteUser(keys);
			msg = "删除黑名单用户成功!";
			commonManagerImpl.log(request, "删除黑名单[主键：" + keys + "]用户成功!");
		} catch (Exception e) { // 删除失败
			logger.error(e.getMessage());
			msg = "删除黑名单用户失败!";
			commonManagerImpl.log(request, "删除黑名单[主键：" + keys + "]删除黑名单失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public String edit() throws Exception {
		entity=blackUserManager.queryEntiryById(id);
		return EDIT;
	}

	public String list() throws Exception {
	
		return INDEX;
	}
	
	public String input() throws Exception {
		return "input";
	}
	
	public void query(){
		try{
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			/**
			 * grid控件排序有3种情况：asc desc 和 第一次加载数据
			 * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
			 */
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("intLastFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortFieldForBigFlushUser(si.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("intLastFlush");
			page.setOrder("desc");
		}
		String dflag = request.getParameter("dflag");
		String dmsn = request.getParameter("dmsn");
		if(dflag!=null){
			msn = dmsn;
		}
		logger.info("msisdn:"+msn);
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page=blackUserManager.query(page, msn);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		List<BlackUser> list = page.getResult();
		logger.info("SIZE:"+list.size());
		gridServerHandler.setData(list, BlackUser.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}catch(Exception e){
		logger.error(e.getMessage());
	}
	}
	
	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			/**
			 * grid控件排序有3种情况：asc desc 和 第一次加载数据
			 * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
			 */
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("intLastFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortFieldForBigFlushUser(si.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("intLastFlush");
			page.setOrder("desc");
		}
		String dflag = request.getParameter("dflag");
		String dmsn = request.getParameter("dmsn");
		if(dflag!=null){
			msn = dmsn;
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
	
		List<BlackUser> list = blackUserManager.queryAllBlackUser(page,msn);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		String exportmsg = ExportMsg.EXPORT_BIGFLUSH_BLACKLIST;
		commonManagerImpl.log(Struts2Utils.getRequest(),exportmsg );
		gridServerHandler.exportXLS(list, BlackUser.class);
	}
	

	public String save() throws Exception {
		List<BigFlushUser> list=new ArrayList<BigFlushUser>();
		descrite= new String(descrite.getBytes("gbk"),
		"UTF-8");
		if(isFile==0){
			list=this.getUpMsisdn();
		}else{
			list=this.readFile();
		}
		logger.info("黑名单数量-size:"+list.size());
		logger.info("msisdn:"+msisdn);
		String msg="";
		try{
			msg = "添加黑名单成功!";
			blackUserManager.insertBlackUser(list,descrite);
			commonManagerImpl.log(request, msg);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加黑名单失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	public String update() throws Exception {
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String msg = null;
		logger.info("dentity.getDescribe()"+entity.getDescribe());
		logger.info("entity.getLastFlush()"+entity.getLastFlush());
		logger.info("entity.getPreMonth()"+entity.getPreMonth());
		
		
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			BlackUser blackUser = blackUserManager.queryEntiryById(entity.getId());
			blackUser.setFirstTime( Common.getYMDDate(entity.getStrFirstTime()));
			blackUser.setDescribe(entity.getDescribe());
			blackUser.setLastTime( Common.getYMDDate(Common.getNow()));
			blackUser.setLastFlush(entity.getLastFlush());
			blackUser.setOverDay(entity.getOverDay());
			blackUser.setPreMonth(entity.getPreMonth());
			blackUserManager.save(blackUser);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			msg = "编辑成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;
		} catch (Exception e) {
			msg = "编辑失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			logger.error(e.getMessage());
			return ERROR;
		}
	}
	

	public List<BigFlushUser> getUpMsisdn(){
		List<BigFlushUser> list=new ArrayList<BigFlushUser>();
		String[] str=dmsisdn.split(",");
		for(int i=0;i<str.length;i++){
			BigFlushUser bigFlushUser=new BigFlushUser();
			String strMsisdn=str[i];
			if(strMsisdn.length()!=13)
				continue;
			if(!strMsisdn.startsWith("86"))
				continue;
			if(this.isNumeric(strMsisdn)){
				bigFlushUser.setMsisdn(Common.StringToLong(strMsisdn));
				list.add(bigFlushUser);
			}
			
		}
		return list;
	}
	public List<BigFlushUser> readFile(){//读取上传文件
		List<BigFlushUser> list=new ArrayList<BigFlushUser>();
		BufferedReader br=null;
		String strMsisdn=null;
		try {
			br=new BufferedReader(new FileReader(this.getMyFile()));
			try {
				while((strMsisdn=br.readLine())!=null){
					BigFlushUser bigFlushUser=new BigFlushUser();
					if(strMsisdn.length()!=13)
						continue;
					if(!strMsisdn.startsWith("86"))
						continue;
					if(this.isNumeric(strMsisdn)){
						bigFlushUser.setMsisdn(Common.StringToLong(strMsisdn));
						list.add(bigFlushUser);
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	public  boolean isNumeric(String str){//判断是否是数字类型
	     Pattern pattern = Pattern.compile("[0-9]*");
	     return pattern.matcher(str).matches();  
	}

	public BlackUser getEntity() {
		return entity;
	}

	public void setEntity(BlackUser entity) {
		this.entity = entity;
	}

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

	public int getIsFile() {
		return isFile;
	}

	public void setIsFile(int isFile) {
		this.isFile = isFile;
	}

	

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getDescrite() {
		return descrite;
	}

	public void setDescrite(String descrite) {
		this.descrite = descrite;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getDmsisdn() {
		return dmsisdn;
	}

	public void setDmsisdn(String dmsisdn) {
		this.dmsisdn = dmsisdn;
	}
	
}
