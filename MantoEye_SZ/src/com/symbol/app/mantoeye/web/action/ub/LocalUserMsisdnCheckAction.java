/**
 * com.symbol.app.mantoeye.web.action.ub.LocalUserMsisdnCheckAction.java
 */
package com.symbol.app.mantoeye.web.action.ub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.symbol.app.mantoeye.entity.UserBelongMsisdn;
import com.symbol.app.mantoeye.service.ud.LocalUserMsisdnCheckManager;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * @author Wu Zhenzhen
 * @version 2013-10-16 下午02:38:14
 */
@Controller
@Scope("prototype")
public class LocalUserMsisdnCheckAction extends BaseAction {

	private LocalUserMsisdnCheckManager localUserMsisdnCheckManager = null;

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:05:34
	 * 
	 * @return the localUserMsisdnCheckManager
	 */
	public LocalUserMsisdnCheckManager getLocalUserMsisdnCheckManager() {
		return localUserMsisdnCheckManager;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:05:34
	 * 
	 * @param localUserMsisdnCheckManager
	 *            the localUserMsisdnCheckManager to set
	 */
	@Resource
	public void setLocalUserMsisdnCheckManager(
			LocalUserMsisdnCheckManager localUserMsisdnCheckManager) {
		this.localUserMsisdnCheckManager = localUserMsisdnCheckManager;
	}

	private HttpServletRequest request = ServletActionContext.getRequest();
	private File file = null;

	/**
	 * <br>
	 * Created on: 2013-10-16 下午02:45:46
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * <br>
	 * Created on: 2013-10-16 下午02:45:46
	 * 
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * <br>
	 * Created on: 2013-10-16 下午02:38:19
	 */
	private static final long serialVersionUID = 808124603895122653L;

	/**
	 * 
	 * <br>
	 * Created on: 2013-10-16 下午02:41:35
	 */
	public String upFile() {
		clearSession();
		String msg = "";
		BufferedReader br = null;
		String str = null;
		int error = 0;
		List<UserBelongMsisdn> msisList = new ArrayList<UserBelongMsisdn>();
		List<Integer> errorNum = new ArrayList<Integer>();
		int total = 0;
		int repeat = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					this.getFile()), "GBK"));
			while ((str = br.readLine()) != null) {
				total++;
				String msisdn = str;
				if (msisdn.length() == 11 || msisdn.length() == 13) {

					if (msisdn.length() == 13) {
						msisdn = msisdn.substring(2);
					}

					if (!this.isNumeric(msisdn)) {
						errorNum.add(total);
						error++;
						continue;
					}
					UserBelongMsisdn um = new UserBelongMsisdn();
					um.setNmMsisdn(msisdn);
					if (!msisList.contains(um)) {
						msisList.add(um);
					} else {
						repeat++;
					}

				} else {
					errorNum.add(total);
					error++;
					continue;
				}
			}

			msg = "批量用户号码,总记录数 :" + total + "， 无效记录数:" + error + "，重复记录："
					+ repeat + "， 确定过滤出本地用户吗?";
			request.setAttribute(VarConstants.SUCC_CODE, "0000110");
			request.setAttribute("message", msg);
			request.getSession().setAttribute("msisList", msisList);
			return SUCCESS;

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			msg = "过滤出本地用户失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.SUCC_CODE_00006);
			return ERROR;
		} catch (IOException e) {
			logger.error(e.getMessage());
			msg = "过滤出本地用户失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00011);
			return ERROR;
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				// logger.error(e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void query() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<UserBelongMsisdn> ml = (List<UserBelongMsisdn>) request
				.getSession().getAttribute("msisList");

		Page<UserBelongMsisdn> page = new Page<UserBelongMsisdn>(20);
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		if (null == ml || ml.isEmpty()) {
			page.setTotalCount(0);
			page.setResult(new ArrayList<UserBelongMsisdn>());
		} else {
			List<UserBelongMsisdn> aml = localUserMsisdnCheckManager
					.checkLocalMsisdnAll(ml);
			// logger.info("********ml**[{}]",ml);
			page.setTotalCount(aml.size());
			// logger.info("**********[{}]",aml);
			// logger.info("*********aml.size()[{}]",aml.size());
			int size = page.getPageNo() * page.getPageSize();
			if (size > aml.size())
				size = aml.size();
			page.setResult(aml.subList(page.getFirst(), size));

			request.getSession().setAttribute("msisListDownl", aml);
		}

		gridServerHandler.setTotalRowNum(page.getTotalCount());
		gridServerHandler.setData(page.getResult(), UserBelongMsisdn.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void clearSession() {
		logger.info("清空session");
		request.getSession().removeAttribute("msisList");
	}

	public boolean isNumeric(String str) {// 判断是否是数字类型
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-10-16 下午02:41:28
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void export() throws IOException {

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<UserBelongMsisdn> ml = (List<UserBelongMsisdn>) request
				.getSession().getAttribute("msisListDownl");

		gridServerHandler.exportXLS(ml, UserBelongMsisdn.class);
		request.getSession().removeAttribute("msisListDownl");

	}
}
