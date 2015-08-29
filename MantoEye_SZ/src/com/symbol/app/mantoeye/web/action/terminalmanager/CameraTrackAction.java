package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbDataOutPutTask;
import com.symbol.app.mantoeye.service.terminalmanager.CameraTrackManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.app.mantoeye.web.action.Phone;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class CameraTrackAction extends BaseDispatchAction {
	private static final long serialVersionUID = -7992128821878284128L;
	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private int taskStatus = -1;
	private String orderTime_search;
	private String orderTime_end;
	private String taskName;
	private String taskMan;
	private FtbDataOutPutTask entity;
	private String oldTaskType;
	private Page<CommonSport> pageNmMsisdn = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private Long nmId;
	private static final String Task_VcArea = "Task_VcArea";
	
	private static final String Task_Terminal = "Task_Terminal";
	
	@Autowired
	private CameraTrackManager cameraTrackManager;
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	public void query() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmDataOutPutTaskId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = cameraTrackManager.query(page, taskStatus, orderTime_search,
				orderTime_end, Common.OutConvert(taskName), Common.OutConvert(taskMan));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public String add() throws Exception {
		return ADD;
	}

	/**
	 * 判断该任务是否存在
	 * 
	 * @throws IOException
	 */
	public String checkUnique() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcTaskName = request.getParameter("vcTaskName").trim();
		int i = cameraTrackManager.existVcTaskName(vcTaskName);
		if (i == -1) {// -1表示不存在该任务名称
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 保存数据 (包括添加以及编辑)
	 */
	public String saveDataOutput() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer()
				.getUserInfo();
		System.out.println(entity.getNmDataOutPutTaskId());
		String msg = null;
		try {
			Long taskId = entity.getNmDataOutPutTaskId();
			if (taskId != null) {// 编辑
				if (!oldTaskType.equals(entity.getIntTaskType().toString()))
					cameraTrackManager.deleteTasks(taskId, Common
							.StringToInt(oldTaskType), 1);

				boolean succ = cameraTrackManager.editParseTask(entity,
						userInfo);
				if (succ) {
					request.setAttribute(VarConstants.SUCC_CODE,
							MsgConstants.SUCC_CODE_00102);
				} else {
					request.setAttribute(VarConstants.SUCC_CODE,
							MsgConstants.SUCC_CODE_00202);
				}
			} else {// 新建
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00101);
				entity.setIntTaskStatus(0);// 新建时的状态
				entity.setDtOrderTime(new Date());// 新建时的定制时间
				entity.setVcTaskOrder(userInfo.getVcUserName());
				cameraTrackManager.saveParseTask(entity);
			}

			msg = "通用拍照任务[名称：" + entity.getVcTaskName() + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "通用拍照任务[名称：" + entity.getVcTaskName() + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 编辑任务
	 */
	public String edit() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		String keyid = request.getParameter("keyid");
		entity = cameraTrackManager.get(Common.StringToLong(keyid));
		return EDIT;
	}

	/**
	 * 激活任务
	 */
	public void activeTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String intTaskType = request.getParameter("intTaskType");
		String msg = "";
		try {
			boolean flag = cameraTrackManager.isExistOutPutColumnMap(Long
					.parseLong(taskId), conventTaskType(intTaskType));
			if (flag) {
				FtbDataOutPutTask entity = cameraTrackManager.get(Long
						.parseLong(taskId));
				entity.setBitTaskActiveFlag(true);// 激活
				cameraTrackManager.save(entity);
				msg = "激活任务成功!";
				commonManagerImpl.log(request, msg);
			} else {
				msg = "不能激活该任务，请输入任务条件!";
				commonManagerImpl.log(request, msg);
			}
		} catch (Exception e) { // 删除失败
			msg = "激活任务失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}
	public int conventTaskType(String value) {
		int result=0;
		if (("通用拍照指定号码").equals(value)) {
			result= 1;
		} else if(("通用拍照指定终端").equals(value)) {
			result= 2;
		}
		 else if(("通用拍照指定区域").equals(value)) {
			 result= 4;
		}
		 else if(("通用拍照指定业务").equals(value)) {
			 result= 5;
		}
		 else if(("通用拍照指定APN").equals(value)) {
			 result= 6;
		}
		 else if(("通用拍照用户归属").equals(value)) {
			 result= 7;
		}
		return result;
	}
	/**
	 * 删除任务
	 * 
	 * @throws Exception
	 */
	public void deleteTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskIds = request.getParameter("taskIds");
		String TaskTypes = request.getParameter("TaskTypes");
		String[] sids = taskIds.split(",");
		String[] sTypes = TaskTypes.split(",");
		Long[] ids = new Long[sids.length];
		int[] types = new int[sTypes.length];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = Common.StringToLong(sids[i]);
			System.out.println(sTypes[i]);
			types[i] = conventTaskType(sTypes[i]);
		}
		String msg = "";
		try {
			//
			// for (Long id : ids) {
			// cameraTrackManager.deleteTasks(id);
			// }
			for (int i = 0; i < ids.length; i++) {
				cameraTrackManager.deleteTasks(ids[i], types[i], 2);
			}

			msg = "删除任务成功!";
			commonManagerImpl.log(request, "删除数据提取任务[ID：" + taskIds + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除任务失败!";
			logger.error(e.toString());
			logger.error(e.getMessage());
			commonManagerImpl.log(request, "删除数据提取任务[ID：" + taskIds + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	/**
	 * 停止任务
	 */
	public void stopTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String msg = "";
		try {
			FtbDataOutPutTask entity = cameraTrackManager.get(Long
					.parseLong(taskId));
			entity.setIntTaskStatus(4);// 停止中
			cameraTrackManager.save(entity);
			msg = "停止任务[：" + taskId + "]" + "成功!";
			commonManagerImpl.log(request, msg);
		} catch (Exception e) { // 删除失败
			msg = "停止任务[：" + taskId + "]" + "失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}

	// /////////////////////////////////////////////////APN///////////////////////////////////////////////////////////
	/**
	 * 初始化apn
	 */
	public void initApnDialogData() throws Exception {
		Struts2Utils.renderXml(packBussnessInfo());
	}

	public String packBussnessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = cameraTrackManager
				.queryApnAndApnType();
		List<String> apnList = new ArrayList<String>(map.keySet());
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml = xml + "<root>";
		if (apnList != null && !apnList.isEmpty()) {
			for (int i = 0; i < apnList.size(); i++) {
				String apnAnadId = apnList.get(i);
				Map<String, String> mapType = map.get(apnAnadId);
				String[] str = apnAnadId.split(":");// 分离品牌名称和Id

				Set setType = mapType.keySet();
				Collection con = mapType.values();
				String modle = null;
				String typeIds = null;
				if (setType != null) {
					modle = getMapKeyStr(mapType);
					typeIds = getMapValueStr(mapType);
				}

				xml = xml + "<data>" + "<brandId>" + str[1] + "</brandId>"
						+ "<brandName>" + str[0] + "</brandName>" + "<modelId>"
						+ typeIds + "</modelId>" + "<modelName>" + modle
						+ "</modelName>" + "</data>";
			}
		}
		xml = xml + "</root>";
		return xml;
	}

	private String getMapKeyStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for (Iterator it = setType.iterator(); it.hasNext();) {
				str = str + "," + it.next();
			}
			if (str.indexOf(",") != -1) {
				str = str.substring(1);
			}
		}
		return str;
	}

	private String getMapValueStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for (Iterator<String> it = setType.iterator(); it.hasNext();) {
				str = str + "," + map.get(it.next());
			}
			if (str.indexOf(",") != -1) {
				str = str.substring(1);
			}
		}
		return str;
	}

	/**
	 * 保存APN
	 */
	public String saveCameratrackApn() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		String[] id = null;
		String vcApn = null;
		try {
			String taskId = request.getParameter("taskId");
			String apn = request.getParameter("apn");

			cameraTrackManager
					.deleteCameratrackApn(Common.StringToLong(taskId));
			if (apn != null && !"".equals(apn)) {
				String[] datas = apn.split(",");
				for (int i = 0; i < datas.length; i++) {
					vcApn = datas[i];
					cameraTrackManager.saveCameratrackApn(Common
							.StringToLong(taskId), vcApn);
				}
			}
			msg = "通用拍照APN任务[保存APN成功!";
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
			msg = "通用拍照APN任务[保存APN失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	public String taskApn() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String apn = cameraTrackManager.queryApnIdAndApnIdTypeByTaskId(Common
				.StringToInt(taskId));
		if (apn != null) {
			String[] apns = apn.split("@");
			request.setAttribute("apn", apns[1]);
			request.setAttribute("apnIds", apns[0]);
		}

		return "task_apn";
	}

	// //////////////////////////////////////////////////////业务///////////////////////////////////////////////////////////////////

	/**
	 * 保存业务
	 */
	public String saveCameratrackBis() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		String[] id = null;
		String businessId = null;
		try {
			String taskId = request.getParameter("taskId");

			String businessIds = request.getParameter("businessIds");
			cameraTrackManager
					.deleteCameratrackBis(Common.StringToLong(taskId));
			if (businessIds != null && !"".equals(businessIds)) {

				String[] datas = businessIds.split(",");
				for (String ids : datas) {
					id = ids.split(":");
					businessId = id[1].trim();
					cameraTrackManager.saveCameratrackBis(Common
							.StringToLong(taskId), Common
							.StringToLong(businessId));
				}
			}
			msg = "通用拍照业务[保存业务成功!";
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
			msg = "通用拍照业务[保存：业务失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	public String taskBusiness() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String business = cameraTrackManager
				.queryBussIdAndBussIdTypeByTaskId(Common.StringToInt(taskId));
		if (business != null) {
			String[] businesses = business.split("@");
			request.setAttribute("business", businesses[1]);
			request.setAttribute("businessIds", businesses[0]);
		}

		return "task_business";
	}

	// //////////////////////////////////////////////////号码//////////////////////////////////////////////////////////
	/**
	 * 查询号码
	 */

	public void queryNmMsisdn() throws ServletException, IOException {
		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String nmMsisdn = request.getParameter("nmMsisdn");
		if (nmMsisdn != null) {
			nmMsisdn = nmMsisdn.trim();
		}
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			pageNmMsisdn.setOrder(order);
			pageNmMsisdn.setOrderBy(MantoEyeUtils.getSortField(si
					.getFieldName()));

		} else {
			pageNmMsisdn.setOrderBy("nmId");
			pageNmMsisdn.setOrder("desc");
		}
		pageNmMsisdn.setPageSize(gridServerHandler.getPageSize());
		pageNmMsisdn.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		pageNmMsisdn = cameraTrackManager.queryNmMsisdn(pageNmMsisdn, Common
				.StringToLong(taskId), nmMsisdn);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = pageNmMsisdn.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = pageNmMsisdn.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 号码导出
	 */
	public void exportNmMsisdn() throws ServletException, IOException {
		List<CommonSport> list = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String nmMsisdn = request.getParameter("nmMsisdn");
		if (nmMsisdn != null) {
			nmMsisdn = nmMsisdn.trim();
		}
		list = cameraTrackManager.exportNmMsisdn(Common.StringToLong(taskId),
				nmMsisdn);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "（用户号码导出）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	/**
	 * 删除号码
	 * 
	 * @throws Exception
	 */
	public void deleteNmMsisdn() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String nmIds = request.getParameter("nmIds");
		String taskId = request.getParameter("taskId");
		String msg = "";
		try {
			cameraTrackManager.deleteNmMsisdn(nmIds);
			msg = "删除号码成功!";
			commonManagerImpl.log(request, "删除通用拍照号码[ID：" + nmIds + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除号码失败!";
			logger.error(e.toString());
			logger.error(e.getMessage());
			commonManagerImpl.log(request, "删除通用拍照号码[ID：" + nmIds + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	/**
	 * 判断该号码是否存在
	 * 
	 * @throws IOException
	 */
	public String checkUniqueNmMsisdn() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String nmMsisdn = request.getParameter("nmMsisdn");
		String taskId = request.getParameter("taskId");
		int i = cameraTrackManager.isNmMsisdn(Common.StringToLong(taskId),
				Common.StringToLong("86" + nmMsisdn), nmId);
		;
		if (i == -1) {// -1表示不存在该任务名称
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 保存号码
	 */
	public String saveNmMsisdn() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String nmMsisdn = request.getParameter("nmMsisdn").trim();
		String intMsisdnStatus = request.getParameter("intMsisdnStatus");
		String msg = null;
		try {
			int status = 0;
			if (intMsisdnStatus != null && !intMsisdnStatus.equals("0")) {
				status = Integer.parseInt(intMsisdnStatus);
			}

			List<String> msisList = new ArrayList<String>();
			String[] msisdn = nmMsisdn.split(",");
			for (int i = 0; i < msisdn.length; i++) {
				if (!msisList.contains("86" + msisdn[i])) {
					msisList.add("86" + msisdn[i]);
				}
			}
			cameraTrackManager
					.saveMsisdn(Common.StringToLong(taskId), msisList);
			msg = "通用拍照任务[保存号码成功!";
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
			msg = "通用拍照任务[保存：" + nmMsisdn + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 修改号码
	 */
	public String updateNmMsisdn() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		String nmMsisdn = request.getParameter("nmMsisdn");
		String msg = null;
		try {
			cameraTrackManager.updateMsisdn(Common.StringToLong(taskId), Common
					.StringToLong("86" + nmMsisdn), nmId);
			msg ="通用拍照输出任务[修改：" + nmMsisdn + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "通用拍照任务[修改：" + nmMsisdn + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 上传号码
	 */

	public String upNumberFile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String intMsisdnStatus = request.getParameter("intMsisdnStatus");
		String taskId = request.getParameter("taskId");
		int status = 0;
		if (intMsisdnStatus != null && !intMsisdnStatus.equals("0")) {
			status = Integer.parseInt(intMsisdnStatus);
		}
		String msg = "";
		List<String> msisList = new ArrayList<String>();
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		int error = 0;
		List<Integer> errorNum = new ArrayList<Integer>();
		int total = 0;
		int repeat = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					this.getFile()), "GBK"));
			while ((str = br.readLine()) != null) {
				System.out.println(str);
				total++;
				String msisdn = str.trim();
				  String startMsisdn = msisdn.substring(0, 2);
					if (!startMsisdn.equals("86")) {
						msisdn = "86" + msisdn;
					}
					if (!this.isNumeric(msisdn)) {
						errorNum.add(total);
						error++;
						continue;
					}
					if (!msisdn.substring(2, 3).equals("1")) {
						error++;
						continue;
					}
					if (!msisList.contains(msisdn)) {
						msisList.add(msisdn);
					} else {
						repeat++;
					}
			}

			msg = "文件上传总记录数 :" + total + "， 无效记录数:" + error + "，重复记录：" + repeat;
			request.setAttribute(VarConstants.SUCC_CODE, "0000110");
			request.setAttribute("message", msg);
			cameraTrackManager
					.saveMsisdn(Common.StringToLong(taskId), msisList);
			return SUCCESS;

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			msg = "文件上传失败!";
			request.setAttribute(VarConstants.ERROR_CODE, "上传文件为空");
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
			}
		}

	}

	public boolean isNumeric(String str) {// 判断是否是数字类型
		if (str!=null&&!"".equals(str)) {
			Pattern pattern = Pattern.compile("[0-9]*");
			return pattern.matcher(str).matches();
		}else {
			return false;
		}

	}

	// /////////////////////////////////////////////////终端类型/////////////////////////////////////////////////////////////
//	/**
//	 * 初始化apn
//	 */
//	public void iniTerminalDialogData() throws Exception {
//		Struts2Utils.renderXml(packTerminalInfo());
//	}
//
//	public String packTerminalInfo() {
//		List<Phone> list = new ArrayList<Phone>();
//		Map<String, Map<String, String>> map = cameraTrackManager
//				.queryTerminalAndType();
//		List<String> apnList = new ArrayList<String>(map.keySet());
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//		xml = xml + "<root>";
//		if (apnList != null && !apnList.isEmpty()) {
//			for (int i = 0; i < apnList.size(); i++) {
//				String apnAnadId = apnList.get(i);
//				Map<String, String> mapType = map.get(apnAnadId);
//				String[] str = apnAnadId.split(":");// 分离品牌名称和Id
//
//				Set setType = mapType.keySet();
//				Collection con = mapType.values();
//				String modle = null;
//				String typeIds = null;
//				if (setType != null) {
//					modle = getMapKeyStr(mapType);
//					typeIds = getMapValueStr(mapType);
//				}
//
//				xml = xml + "<data>" + "<brandId>" + str[1] + "</brandId>"
//						+ "<brandName>" + str[0] + "</brandName>" + "<modelId>"
//						+ typeIds + "</modelId>" + "<modelName>" + modle
//						+ "</modelName>" + "</data>";
//			}
//		}
//		xml = xml + "</root>";
//		return xml;
//	}
//
//	/**
//	 * 保存终端
//	 */
//	public String saveCameratrackTerminal() throws ServletException,
//			IOException {
//		HttpServletRequest request = Struts2Utils.getRequest();
//		String msg = null;
//		String[] id = null;
//		String vcApn = null;
//		try {
//			String taskId = request.getParameter("taskId");
//			String apn = request.getParameter("apnIds");
//
//			cameraTrackManager.deleteCameratrackTerminal(Common
//					.StringToLong(taskId));
//			if (apn != null && !"".equals(apn)) {
//				String[] datas = apn.split(",");
//				for (int i = 0; i < datas.length; i++) {
//					vcApn = datas[i];
//					String terminalid = vcApn.split(":")[1];
//					cameraTrackManager.saveCameratrackTerminal(Common
//							.StringToLong(taskId), Common
//							.StringToLong(terminalid));
//				}
//			}
//			msg = "通用拍照终端任务[保存业务成功!";
//			request.setAttribute(VarConstants.SUCC_CODE,
//					msg);
//			commonManagerImpl.log(request, msg);
//			return SUCCESS;
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			logger.error(e.toString());
//			logger.error(e.getMessage());
//			request.setAttribute(VarConstants.ERROR_CODE,
//					MsgConstants.ERROR_CODE_00004);
//			msg = "通用拍照终端任务[保存：业务失败!";
//			commonManagerImpl.log(request, msg);
//			return ERROR;
//		}
//	}
//
//	public String taskTerminal() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String taskId = request.getParameter("taskId");
//		String apn = cameraTrackManager.queryTerminalIdAndIdTypeByTaskId(Common
//				.StringToInt(taskId));
//		if (apn != null) {
//			String[] apns = apn.split("@");
//			request.setAttribute("apn", apns[1]);
//			request.setAttribute("apnIds", apns[0]);
//		}
//
//		return "task_terminal";
//	}

	
	
	public String taskTerminalIndex() throws Exception {

		return "task_terminalindex";
	}

	public String addTerminal() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<CommonSport> cList = new ArrayList<CommonSport>();
		cList = (List<CommonSport>) request.getSession().getAttribute(
				Task_Terminal);
		if (cList != null && cList.size() > 0) {
			ftbTerminalList.addAll(cList);
		}
		if (vcTerminal != null && !vcTerminal.equals("")) {
			String[] vcTerminals = vcTerminal.split(",");
			for (int j = 0; j < vcTerminals.length; j++) {
				CommonSport ftbTerminal = new CommonSport();
				logger.info(vcTerminals[j]);
				String[] s = vcTerminals[j].split("_");
				String vcTerminalName = new String(s[0].getBytes("ISO-8859-1"),"UTF-8");
				String vcTerminalBrand= new String(s[1].getBytes("ISO-8859-1"),"UTF-8");
				
				long terminalId = Common.StringToLong(s[2]);
				ftbTerminal.setVcTerminalName(vcTerminalName);
				ftbTerminal.setVcTerminalBrand(vcTerminalBrand);
				ftbTerminal.setNmTerminalId(Common.StringToLong(s[2]));
				boolean flag = true;
				for (int i = 0; i < ftbTerminalList.size(); i++) {
					CommonSport ftbTerminalTemp = ftbTerminalList.get(i);
					if ((ftbTerminalTemp.getNmTerminalId()+"").equals(terminalId + "")) {
						flag = false;
						break;
					}
				}
				if (flag) {
					ftbTerminalList.add(ftbTerminal);
				}
			}
		}
		request.getSession().setAttribute(Task_Terminal, ftbTerminalList);
		return queryTerminal();
	}

	public String removeTerminal() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<CommonSport> cList = new ArrayList<CommonSport>();
		cList = (List<CommonSport>) request.getSession().getAttribute(
				Task_Terminal);
		if (cList != null && cList.size() > 0) {
			ftbTerminalList.addAll(cList);
		}
		if (vcTerminal != null && !vcTerminal.equals("")) {
			String[] vcTerminals = vcTerminal.split(",");
			for (int j = 0; j < vcTerminals.length; j++) {
				CommonSport ftbTerminal = new CommonSport();
				logger.info(vcTerminals[j]);
				String[] s = vcTerminals[j].split("_");
				String vcTerminalName = new String(s[0].getBytes("ISO-8859-1"),
						"UTF-8");
				String vcTerminalBrand=  new String(s[1].getBytes("ISO-8859-1"),"UTF-8");
			
				long terminalId = Common.StringToLong(s[2]);
				ftbTerminal.setVcTerminalName(vcTerminalName);
				ftbTerminal.setVcTerminalBrand(vcTerminalBrand);
				ftbTerminal.setNmTerminalId(Common.StringToLong(s[2]));
					for (int i = 0; i < ftbTerminalList.size(); i++) {
						CommonSport ftbTerminalTemp = ftbTerminalList.get(i);
						if ((ftbTerminalTemp.getNmTerminalId()+"").equals(terminalId + "")) {
							ftbTerminalList.remove(ftbTerminalTemp);
							break;
						}
					}
			
				
			}
		}
		request.getSession().setAttribute(Task_Terminal, ftbTerminalList);
		return queryTerminal();
	}

	public String queryTerminal() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		page = cameraTrackManager.queryTerminal(page, Common.OutConvert(vcTerminalBrand),Common.OutConvert(vcTerminalName));
		List<CommonSport> cList = cameraTrackManager.queryTerminalMap(Common
				.StringToLong(taskId));
		ftbTerminalList = (List<CommonSport>) request.getSession().getAttribute(
				Task_Terminal);
		if (ftbTerminalList == null&&cList!=null) {
			ftbTerminalList = new ArrayList<CommonSport>();
			ftbTerminalList.addAll(cList);
			request.getSession().setAttribute(Task_Terminal, ftbTerminalList);
		}
		return "task_terminal";
	}

	public String removeAllTerminal() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		cleanTerminalSession();
		page = cameraTrackManager.queryTerminal(page, vcTerminalBrand,vcTerminalName);
		ftbTerminalList = null;
		return "task_terminal";
	}

	public void cleanTerminalSession() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		request.getSession().removeAttribute(Task_Terminal);
		logger.info("清空Terminal的session");
	}

	/**
	 * 保存Terminal
	 */
	public String saveTerminalMap() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		try {
			List<CommonSport> cList = (List<CommonSport>) request.getSession()
					.getAttribute(Task_Terminal);
			request.getSession().removeAttribute(Task_VcArea);
			String taskId = request.getParameter("taskId");

			cameraTrackManager.deleteTerminalMap(Common.StringToLong(taskId));
			if (cList != null && cList.size() > 0) {
				for (CommonSport ftbTerminal : cList) {
					long nmTerminalId = ftbTerminal.getNmTerminalId();
					
					cameraTrackManager.saveTerminalMap(Common
							.StringToLong(taskId),nmTerminalId);
				}
			}
			msg = "通用拍照任务[保存终端成功!";
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
			msg = "通用拍照任务[保存终端失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}
	
	
	
//	private String vcTerminal;//页面传递过来的value
//	private String vcTerminalName;//终端类型
//	private String vcTerminalBrand;//终端品牌
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// /////////////////////////////////////////////////////用户归属//////////////////////////////////////////////////////////
	public String taskUser() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String UserBelongId = cameraTrackManager.queryUserBelongByTaskId(Common
				.StringToInt(taskId));
		int intUserBelongId = 0;
		int exists = 0;// 0不存在，1存在
		if (UserBelongId != null) {
			intUserBelongId = Common.StringToInt(UserBelongId);
			exists = 1;
		}
		request.setAttribute("intUserBelongId", intUserBelongId);
		request.setAttribute("exists", exists);
		return "task_user";
	}

	/**
	 * 保存用户配置
	 */
	public String saveCameratrackUser() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		String[] id = null;
		String vcApn = null;
		try {
			String taskId = request.getParameter("taskId");
			String intUserBelongId = request.getParameter("intUserBelongId");
			String exists = request.getParameter("exists");

			if ("1".equals(exists))
				cameraTrackManager.UpdateCameratrackUser(Common
						.StringToLong(taskId), Common
						.StringToInt(intUserBelongId));
			else
				cameraTrackManager.saveCameratrackUser(Common
						.StringToLong(taskId), Common
						.StringToInt(intUserBelongId));

			msg = "通用拍照任务[保存用户配置成功!";
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
			msg = "通用拍照任务[保存用户配置失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	// //////////////////////////////////////////////////////区域类型///////////////////////////////////////////////////////////////
	public String taskAreaIndex() throws Exception {

		return "task_areaindex";
	}

	public String addCgi() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<CommonSport> cList = new ArrayList<CommonSport>();
		cList = (List<CommonSport>) request.getSession().getAttribute(
				Task_VcArea);
		if (cList != null && cList.size() > 0) {
			ftbCgiList.addAll(cList);
		}
		if (vcCgi != null && !vcCgi.equals("")) {
			String[] vcCgis = vcCgi.split(",");
			for (int j = 0; j < vcCgis.length; j++) {
				CommonSport ftbCgi = new CommonSport();
				logger.info(vcCgis[j]);
				String[] s = vcCgis[j].split("_");
				String vcCgiChName = new String(s[0].getBytes("ISO-8859-1"),
						"UTF-8");
				String cgi = s[1];
		
				if (cgi.equals("0")) {
					ftbCgiList.clear();
					ftbCgi.setDataType(0);
					ftbCgi.setVcAreaName("全网");
					ftbCgiList.add(ftbCgi);
					request.getSession().setAttribute(Task_VcArea, ftbCgiList);
					return queryCgi();
				}		
				long areaId = Common.StringToLong(s[2]);
				ftbCgi.setVcAreaName(vcCgiChName);
				ftbCgi.setDataType(Common.StringToInt(cgi));
				ftbCgi.setNmAreaId(Common.StringToLong(s[2]));
				boolean flag = true;
				for (int i = 0; i < ftbCgiList.size(); i++) {
					CommonSport ftbCgiTemp = ftbCgiList.get(i);
					if(!(ftbCgiTemp.getDataType()+"").equals(cgi)){
						ftbCgiList.clear();
						break;
					}
					if ((ftbCgiTemp.getNmAreaId()+"").equals(areaId + "")) {
						flag = false;
						break;
					}
				}
				if (flag) {
					ftbCgiList.add(ftbCgi);
				}
			}
		}
		request.getSession().setAttribute(Task_VcArea, ftbCgiList);
		return queryCgi();
	}

	public String removeCgi() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<CommonSport> cList = new ArrayList<CommonSport>();
		cList = (List<CommonSport>) request.getSession().getAttribute(
				Task_VcArea);
		if (cList != null && cList.size() > 0) {
			ftbCgiList.addAll(cList);
		}
		if (vcCgi != null && !vcCgi.equals("")) {
			String[] vcCgis = vcCgi.split(",");
			for (int j = 0; j < vcCgis.length; j++) {
				CommonSport ftbCgi = new CommonSport();
				logger.info(vcCgis[j]);
				String[] s = vcCgis[j].split("_");
				String vcCgiChName = new String(s[0].getBytes("ISO-8859-1"),
						"UTF-8");
				String cgi = s[1];
				if (!"0".equals(cgi)&&cgi!="0") {
					long areaId = Common.StringToLong(s[2]);
					ftbCgi.setVcAreaName(vcCgiChName);
					ftbCgi.setDataType(Common.StringToInt(cgi));
					ftbCgi.setNmAreaId(areaId);
					for (int i = 0; i < ftbCgiList.size(); i++) {
						CommonSport ftbCgiTemp = ftbCgiList.get(i);
						if (ftbCgiTemp.getNmAreaId().toString().equals(areaId + "")) {
							ftbCgiList.remove(ftbCgiTemp);
							break;
						}
					}
				}else
					ftbCgiList.clear();
				
			}
		}
		request.getSession().setAttribute(Task_VcArea, ftbCgiList);
		return queryCgi();
	}

	public String queryCgi() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
		HttpServletRequest request = Struts2Utils.getRequest();
		String taskId = request.getParameter("taskId");
		page = cameraTrackManager.queryArea(page, areaType,Common.OutConvert(vcCgiChName));
		List<CommonSport> cList = cameraTrackManager.queryAreaMap(Common
				.StringToLong(taskId));
		ftbCgiList = (List<CommonSport>) request.getSession().getAttribute(
				Task_VcArea);
		if (ftbCgiList == null&&cList!=null) {
			ftbCgiList = new ArrayList<CommonSport>();
			ftbCgiList.addAll(cList);
			request.getSession().setAttribute(Task_VcArea, ftbCgiList);
		}
		request.getSession().setAttribute("areaType", areaType);
		return "task_area";
	}

	public String removeAllCgi() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		cleanCgiSession();
		page = cameraTrackManager.queryArea(page, areaType,vcCgiChName);
		ftbCgiList = null;
		return "task_area";
	}

	public void cleanCgiSession() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		request.getSession().removeAttribute(Task_VcArea);
		logger.info("清空cgi的session");
	}

	/**
	 * 保存CGI
	 */
	public String saveOutPutCgiMap() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		try {
			List<CommonSport> cList = (List<CommonSport>) request.getSession()
					.getAttribute(Task_VcArea);
			request.getSession().removeAttribute(Task_VcArea);
			String taskId = request.getParameter("taskId");

			cameraTrackManager.deleteOutPutAreaMap(Common.StringToLong(taskId));
			if (cList != null && cList.size() > 0) {
				for (CommonSport ftbCgi : cList) {
					int areaType = ftbCgi.getDataType();
					String areaId =areaType==0?null: ftbCgi.getNmAreaId()+"";
					cameraTrackManager.saveOutPutAreaMap(Common
							.StringToLong(taskId), areaType, areaId);
				}
			}
			msg = "通用拍照任务[保存区域成功!";
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
			msg = "通用拍照任务[添保存gi失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}
	
	
	
	private String vcTerminal;
	private String vcTerminalName;
	private String vcTerminalBrand;
	private List<CommonSport> ftbTerminalList = new ArrayList<CommonSport>();
	
	public List<CommonSport> getFtbTerminalList() {
		return ftbTerminalList;
	}

	public void setFtbTerminalList(List<CommonSport> ftbTerminalList) {
		this.ftbTerminalList = ftbTerminalList;
	}

	public String getVcTerminal() {
		return vcTerminal;
	}

	public void setVcTerminal(String vcTerminal) {
		this.vcTerminal = vcTerminal;
	}






	public String getVcTerminalName() {
		return vcTerminalName;
	}

	public void setVcTerminalName(String vcTerminalName) {
		this.vcTerminalName = vcTerminalName;
	}

	public String getVcTerminalBrand() {
		return vcTerminalBrand;
	}

	public void setVcTerminalBrand(String vcTerminalBrand) {
		this.vcTerminalBrand = vcTerminalBrand;
	}



	private String vcCgiChName;

	public String getVcCgiChName() {
		return vcCgiChName;
	}

	public void setVcCgiChName(String vcCgiChName) {
		this.vcCgiChName = vcCgiChName;
	}

	private int areaType = 1;

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	private String vcCgi;

	public String getVcCgi() {
		return vcCgi;
	}

	public void setVcCgi(String vcCgi) {
		this.vcCgi = vcCgi;
	}

	private Page<CommonSport> cgiPage = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	public Page<CommonSport> getCgiPage() {
		return cgiPage;
	}

	public void setCgiPage(Page<CommonSport> cgiPage) {
		this.cgiPage = cgiPage;
	}

	private List<CommonSport> ftbCgiList = new ArrayList<CommonSport>();

	public List<CommonSport> getFtbCgiList() {
		return ftbCgiList;
	}

	public void setFtbCgiList(List<CommonSport> ftbCgiList) {
		this.ftbCgiList = ftbCgiList;
	}

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Long getNmId() {
		return nmId;
	}

	public void setNmId(Long nmId) {
		this.nmId = nmId;
	}

	public Page<CommonSport> getPageNmMsisdn() {
		return pageNmMsisdn;
	}

	public void setPageNmMsisdn(Page<CommonSport> pageNmMsisdn) {
		this.pageNmMsisdn = pageNmMsisdn;
	}

	public FtbDataOutPutTask getEntity() {
		return entity;
	}

	public void setEntity(FtbDataOutPutTask entity) {
		this.entity = entity;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getOrderTime_search() {
		return orderTime_search;
	}

	public void setOrderTime_search(String orderTimeSearch) {
		orderTime_search = orderTimeSearch;
	}

	public String getOrderTime_end() {
		return orderTime_end;
	}

	public void setOrderTime_end(String orderTimeEnd) {
		orderTime_end = orderTimeEnd;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskMan() {
		return taskMan;
	}

	public void setTaskMan(String taskMan) {
		this.taskMan = taskMan;
	}

	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	public String getOldTaskType() {
		return oldTaskType;
	}

	public void setOldTaskType(String oldTaskType) {
		this.oldTaskType = oldTaskType;
	}

}
