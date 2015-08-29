package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.TerminalBusiness;
import com.symbol.app.mantoeye.dto.flush.TerminalBusinessEntity;
import com.symbol.app.mantoeye.dto.flush.TerminalEntity;
import com.symbol.app.mantoeye.dto.flush.TerminalTaskEntity;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalBusinessAnalyseManager;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalTaskManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.export.ExcelMergeBean;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class TerminalBisAnlyseAction extends BaseDispatchAction {

	HttpServletRequest request = ServletActionContext.getRequest();
	@Autowired
	private TerminalBusinessAnalyseManager terminalBusinessAnalyseManager;

	@Autowired
	private TerminalTaskManager terminalTaskManager;
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	private Page<TerminalEntity> page = new Page<TerminalEntity>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	private String taskId;
	private String business;

	private List<Long[]> flushList = new ArrayList<Long[]>();

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	/**
	 * 查询所有任务
	 */
	public void queryAllTask() {
		List<TerminalTaskEntity> list = terminalTaskManager.queryAllTask();
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml = xml + "<root>";
		for (int i = 0; i < list.size(); i++) {
			TerminalTaskEntity taskEntity = list.get(i);
			xml = xml + "<data>";
			xml = xml + "<id>" + taskEntity.getNmTerminalPolicyId() + "</id>";
			xml = xml + "<name>" + taskEntity.getTaskName() + "</name>";
			xml = xml + "</data>";
		}
		xml = xml + "</root>";
		Struts2Utils.renderXml(xml);

	}

	/**
	 * 根据任务查询统计数据
	 */
	public void queryTerminalBusiness() {

		List<TerminalBusinessEntity> listData = new ArrayList<TerminalBusinessEntity>();
		List<TerminalBusinessEntity> listData2 = new ArrayList<TerminalBusinessEntity>();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		if (taskId != null) {
			List<TerminalBusiness> list = terminalBusinessAnalyseManager
					.query(Common.StringToInt(taskId));
			listData = this.bulidData(list, business,
					Common.StringToInt(taskId));
			SortInfo si = gridServerHandler.getSingleSortInfo();
			String order = "DESC";
			String orderBy = "sumFlush";
			if (si != null) {
				order = si.getSortOrder();
				if (order.equals("defaultsort"))
					order = "DESC";
				orderBy = si.getFieldName();
			}
			request.getSession()
					.setAttribute("orderForTerminalBusiness", order);
			request.getSession().setAttribute("orderByForTerminalBusiness",
					orderBy);
			listData = MantoEyeUtils.orderList(listData, order, orderBy);
			int pageNo = gridServerHandler.getPageNum();
			int pageSize = gridServerHandler.getPageSize();
			gridServerHandler.setTotalRowNum(listData.size());
			int firstRecord = ((pageNo - 1) * pageSize);
			int countRecord = listData.size() - (pageNo - 1) * pageSize > pageSize ? pageNo
					* pageSize
					: listData.size() - 1;

			for (int i = firstRecord; i <= countRecord; i++) {
				listData2.add(listData.get(i));
			}
		}
		gridServerHandler.setData(listData2, TerminalBusinessEntity.class);

		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText(),
				new String[0]);
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		business = (String) request.getSession().getAttribute("business");
		int displayColumn = Common.StringToInt(request
				.getParameter("displayColumn"));
		List<TerminalBusinessEntity> listData = new ArrayList<TerminalBusinessEntity>();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		if (taskId != null) {
			List<TerminalBusiness> list = terminalBusinessAnalyseManager
					.query(Common.StringToInt(taskId));
			listData = this.bulidData(list, business,
					Common.StringToInt(taskId));
			SortInfo si = gridServerHandler.getSingleSortInfo();
			String order = (String) request.getSession().getAttribute(
					"orderForTerminalBusiness");
			String orderBy = (String) request.getSession().getAttribute(
					"orderByForTerminalBusiness");

			if (si != null) {
				order = si.getSortOrder();
				if (order.equals("defaultsort"))
					order = "DESC";
				orderBy = si.getFieldName();

			}
			listData = MantoEyeUtils.orderList(listData, order, orderBy);
		}

		String exportmsg = ExportMsg.EXPORT_TERM_BUSINUSS_ANAY;
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		String[] businesses = business.split("\\,");
		List<ExcelMergeBean> mergeArray = new ArrayList<ExcelMergeBean>();
		mergeArray.add(new ExcelMergeBean(0, 0, 0, 1, "终端名称"));
		mergeArray.add(new ExcelMergeBean(1, 1, 0, 1, "总流量"));
		mergeArray.add(new ExcelMergeBean(2, 2, 0, 1, "总用户数"));
		int first = 0;
		int last = 0;
		for (int i = 0; i < businesses.length; i++) {
			String busi = businesses[i];
			if (i == 0) {
				first = 3;
				last = 3 + displayColumn - 1;
			} else {
				first = last + 1;
				last = first + displayColumn - 1;
			}

			mergeArray.add(new ExcelMergeBean(first, last, busi));
		}
		gridServerHandler.setMergeArray(mergeArray);
		gridServerHandler.exportXLS(listData, TerminalBusinessEntity.class);
	}

	public List<TerminalBusinessEntity> bulidData(List<TerminalBusiness> list,
			String businesses, int taskId) {
		List<TerminalBusinessEntity> listData = new ArrayList<TerminalBusinessEntity>();
		Map<String, List<TerminalBusiness>> map = new HashMap<String, List<TerminalBusiness>>();
		Map<String, Long> nameIdmap = new HashMap<String, Long>();
		// 根据终端品牌，终端类型 分类出对应的业务数据
		for (int i = 0; i < list.size(); i++) {
			TerminalBusiness tb = list.get(i);

			String terminalVsTypeName = tb.getTerminalBrandName() + ":"
					+ tb.getTerminalTypeName();
			if (map.get(terminalVsTypeName) == null) {
				List<TerminalBusiness> terminalList = new ArrayList<TerminalBusiness>();
				terminalList.add(tb);
				map.put(terminalVsTypeName, terminalList);
			} else {
				map.get(terminalVsTypeName).add(tb);

			}
			String ttname = tb.getTerminalTypeName();
			if (!nameIdmap.containsKey(ttname)) {
				nameIdmap.put(ttname, tb.getTerminalTypeId());
			}
		}
		Map<Long, Long> sumUsersMap = terminalBusinessAnalyseManager
				.findSumUsers(taskId);
		Map<Long, Double> sumFlushMap = terminalBusinessAnalyseManager
				.findsumFlush(taskId);
		List<String> listTerminalName = new ArrayList<String>(map.keySet());
		for (int i = 0; i < listTerminalName.size(); i++) {
			String terminalVsTypeName = listTerminalName.get(i);
			TerminalBusinessEntity tbEntity = new TerminalBusinessEntity();
			List<TerminalBusiness> listTerminalBusi = map
					.get(terminalVsTypeName);// 一个终端其对应的业务
			Double sumFlush = 0d;
			Long sumUser = 0l;

			String terminalName = terminalVsTypeName.split(":")[1];

			// 每个终端对应的具体业务
			List<TerminalBusiness> listBusi = this.findBusiness(
					listTerminalBusi, businesses);
			if (sumUsersMap.get(nameIdmap.get(terminalName)) != null) {
				sumUser = sumUsersMap.get(nameIdmap.get(terminalName));
			}
			if (sumFlushMap.get(nameIdmap.get(terminalName)) != null) {
				sumFlush = sumFlushMap.get(nameIdmap.get(terminalName));
			}
			for (int j = 0; j < listBusi.size(); j++) {
				TerminalBusiness tb = listBusi.get(j);
				// sumFlush = sumFlush + tb.getIntFlushKB();
				// sumUser = sumUser + tb.getIntUsers();
				Class c = tbEntity.getClass();
				try {// 通过反射方式赋值
					String methodName1 = "setColumn" + j + 1;
					Method m1 = c.getMethod(methodName1, String.class);
					m1.invoke(tbEntity, String.valueOf(tb.getIntUsers()));
					String methodName2 = "setColumn" + j + 2;
					Method m2 = c.getMethod(methodName2, String.class);
					m2.invoke(tbEntity, getTotalFlushFormat(tb.getIntFlushKB()));
					String methodName3 = "setColumn" + j + 3;
					Method m3 = c.getMethod(methodName3, String.class);
					m3.invoke(tbEntity, getFlushRate(Common.StringTodouble(tb
							.getFlUsersRate())));
					String methodName4 = "setColumn" + j + 4;
					Method m4 = c.getMethod(methodName4, String.class);
					m4.invoke(tbEntity, getFlushRate(Common.StringTodouble(tb
							.getFlFlustRate())));
					String avag = "0.00";
					if (tb.getIntUsers() != 0) {
						avag = getTotalFlushFormat((tb.getIntFlush() / tb
								.getIntUsers()) / 1024.0);
					}
					String methodName5 = "setColumn" + j + 5;
					Method m5 = c.getMethod(methodName5, String.class);
					m5.invoke(tbEntity, String.valueOf(avag));
				} catch (SecurityException e) {
					logger.error(e.getMessage());
				} catch (NoSuchMethodException e) {
					logger.error(e.getMessage());
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage());
				}

			}

			tbEntity.setSumFlush(getTotalFlushFormat(sumFlush));
			tbEntity.setSumUser(sumUser.toString());
			tbEntity.setTerminalName(terminalName);
			listData.add(tbEntity);
		}
		return listData;
	}

	/**
	 * 保留两位小数点方法
	 * 
	 * @param i
	 * @param d
	 * @return
	 */
	public String getTotalFlushFormat(Double d) {
		// 数量级太大时，数据时以科学计数法的形式存在的，不能直接转换为字符串
		BigDecimal b = new BigDecimal(d);
		String rate = b + "";
		String[] rates = rate.split("\\.");
		if (rates.length < 2) {
			return rate + ".00";
		} else if (rates[1].length() < 2) {
			return rate + "0";
		} else {
			return rate.substring(0, rate.indexOf(".") + 3);
		}

	}

	public String getFlushRate(Double d) {
		d = d * 100;
		DecimalFormat df = new DecimalFormat("0.00");
		String rate = df.format(d);
		return rate;

	}

	/**
	 * 根据页面业务顺序排列LIST
	 * 
	 * @param list
	 * @param businessName
	 * @return
	 */
	public List<TerminalBusiness> findBusiness(List<TerminalBusiness> list,
			String businesses) {
		List<TerminalBusiness> listData = new ArrayList<TerminalBusiness>();
		String[] business = businesses.split("\\,");
		for (int i = 0; i < business.length; i++) {
			TerminalBusiness tb = null;
			boolean flag = true;
			for (int j = 0; j < list.size(); j++) {
				tb = list.get(j);
				if (tb.getBusinessName().equals(business[i])) {
					tb = list.get(j);
					listData.add(tb);
					flag = false;
					break;
				}

			}

			if (flag) {
				tb = new TerminalBusiness();
				tb.setBusinessName(business[i]);
				tb.setIntFlush(0l);
				tb.setIntUsers(0l);
				tb.setFlFlustRate("0.00");
				tb.setFlUsersRate("0.00");
				tb.calculateData();
				listData.add(tb);
			}
		}

		return listData;
	}

	/**
	 * 数据导出
	 */
	public void export2() throws ServletException, IOException {
		business = (String) request.getSession().getAttribute("business");
		int displayColumn = Common.StringToInt(request
				.getParameter("displayColumn"));
		List<TerminalBusinessEntity> listData = new ArrayList<TerminalBusinessEntity>();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		if (taskId != null) {
			List<TerminalBusiness> list = terminalBusinessAnalyseManager
					.query(Common.StringToInt(taskId));
			listData = this.bulidBusinessData(list, business);
			SortInfo si = gridServerHandler.getSingleSortInfo();
			String order = (String) request.getSession().getAttribute(
					"orderForBusinessTerminal");
			String orderBy = (String) request.getSession().getAttribute(
					"orderByForBusinessTerminal");
			listData = MantoEyeUtils.orderList(listData, order, orderBy);
		}
		String exportmsg = ExportMsg.EXPORT_BUSINUSS_TERM_ANAY;
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		String[] businesses = business.split("\\,");
		List<ExcelMergeBean> mergeArray = new ArrayList<ExcelMergeBean>();
		mergeArray.add(new ExcelMergeBean(0, 0, 0, 1, "业务名称"));
		mergeArray.add(new ExcelMergeBean(1, 1, 0, 1, "总流量"));
		mergeArray.add(new ExcelMergeBean(2, 2, 0, 1, "总用户数"));
		int first = 0;
		int last = 0;
		for (int i = 0; i < businesses.length; i++) {
			String busi = businesses[i];
			if (i == 0) {
				first = 3;
				last = 3 + displayColumn - 1;
			} else {
				first = last + 1;
				last = first + displayColumn - 1;
			}

			mergeArray.add(new ExcelMergeBean(first, last, busi));
		}
		gridServerHandler.setMergeArray(mergeArray);
		gridServerHandler.exportXLS(listData, TerminalBusinessEntity.class);
	}

	/**
	 * 根据任务查询统计数据
	 */
	public void queryBusinessTerminal() {
		business = (String) request.getSession().getAttribute("business");
		List<TerminalBusinessEntity> listData = new ArrayList<TerminalBusinessEntity>();
		List<TerminalBusinessEntity> listData2 = new ArrayList<TerminalBusinessEntity>();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		if (taskId != null) {
			List<TerminalBusiness> list = terminalBusinessAnalyseManager
					.query(Common.StringToInt(taskId));
			listData = this.bulidBusinessData(list, business);
			SortInfo si = gridServerHandler.getSingleSortInfo();
			String order = "DESC";
			String orderBy = "sumFlush";
			if (si != null) {
				order = si.getSortOrder();
				if (order.equals("defaultsort"))
					order = "DESC";
				orderBy = si.getFieldName();
			}
			request.getSession()
					.setAttribute("orderForBusinessTerminal", order);
			request.getSession().setAttribute("orderByForBusinessTerminal",
					orderBy);
			listData = MantoEyeUtils.orderList(listData, order, orderBy);
			int pageNo = gridServerHandler.getPageNum();
			int pageSize = gridServerHandler.getPageSize();
			gridServerHandler.setTotalRowNum(listData.size());
			int firstRecord = ((pageNo - 1) * pageSize);
			int countRecord = listData.size() - (pageNo - 1) * pageSize > pageSize ? pageNo
					* pageSize
					: listData.size() - 1;

			for (int i = firstRecord; i <= countRecord; i++) {
				listData2.add(listData.get(i));
			}
			// gridServerHandler.setOtherDataInfo(calFlushUnit(firstRecord,
			// countRecord));
		}
		gridServerHandler.setData(listData2, TerminalBusinessEntity.class);

		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText(),
				new String[0]);
	}

	// private String calFlushUnit(int firstRecord, int countRecord) {
	// Long b2kb = 1024 * 10L;
	// Long b2mb = 1024 * 1024 * 10L;
	// Long b2gb = 1024 * 1024 * 1024 * 10L;
	// Long b2tb = 1024 * 1024 * 1024 * 1024 * 10L;
	// String uis = "";
	// if (flushList.size() > 0) {
	// Long[] maxs = flushList.get(firstRecord);
	// Long[] mins = flushList.get(firstRecord);
	// logger.info("**" + firstRecord+"&&&&"+countRecord);
	// for (int i = firstRecord; i < countRecord; i++) {
	// Long[] fsh = flushList.get(i);
	// for (int j = 0; j < fsh.length; j++) {
	// logger.info("**" + fsh[j]);
	// if (fsh[j] > maxs[j]) {
	// maxs[j] = fsh[j];
	// }
	// if (fsh[j] < mins[j]) {
	// mins[j] = fsh[j];
	// }
	// }
	// logger.info("********************************");
	// }
	// for (int k = 0; k < mins.length; k++) {
	// logger.info("$$$$$$" + mins[k]);
	// if (b2tb < mins[k]) {
	// uis = uis + "TB" + ",";
	// } else if (b2gb < mins[k]) {
	// uis = uis + "GB" + ",";
	// } else if (b2mb < mins[k]) {
	// uis = uis + "MB" + ",";
	// } else if (b2kb < mins[k]) {
	// uis = uis + "KB" + ",";
	// } else {
	// uis = uis + "B" + ",";
	// }
	// }
	// uis = uis.substring(0, uis.length() - 1);
	// }
	// logger.info("UNITS:" + uis);
	// return uis;
	// }

	public List<TerminalBusinessEntity> bulidBusinessData(
			List<TerminalBusiness> list, String businesses) {
		List<TerminalBusinessEntity> listData = new ArrayList<TerminalBusinessEntity>();
		Map<String, List<TerminalBusiness>> map = new HashMap<String, List<TerminalBusiness>>();
		// 根据终端品牌，终端类型 分类出对应的业务数据
		for (int i = 0; i < list.size(); i++) {
			TerminalBusiness tb = list.get(i);
			String businessName = tb.getBusinessName();
			if (map.get(businessName) == null) {
				List<TerminalBusiness> businessList = new ArrayList<TerminalBusiness>();
				businessList.add(tb);
				map.put(businessName, businessList);
			} else {
				map.get(businessName).add(tb);
			}
		}
		flushList = new ArrayList<Long[]>();
		List<String> listBusinessName = new ArrayList<String>(map.keySet());

		for (int i = 0; i < listBusinessName.size(); i++) {
			String businessName = listBusinessName.get(i);
			TerminalBusinessEntity tbEntity = new TerminalBusinessEntity();
			List<TerminalBusiness> listTerminalBusi = map.get(businessName);// 一个终端其对应的业务
			Double sumFlush = 0d;
			Long sumUser = 0l;
			Long sumFlushb = 0l;
			Double sumFlusKb = 0d;
			String terminalName = businessName;

			// 每个终端对应的具体业务
			List<TerminalBusiness> listBusi = this.findTerminal(
					listTerminalBusi, businesses);
			Long[] flushs = new Long[listBusi.size() + 1];
			for (int j = 0; j < listBusi.size(); j++) {

				TerminalBusiness tb = listBusi.get(j);
				sumFlush = sumFlush + tb.getIntFlushKB();
				sumUser = sumUser + tb.getIntUsers();
				sumFlusKb = sumFlusKb
						+ Double.parseDouble(getTotalFlushFormat(tb
								.getIntFlushKB()));
				sumFlushb = sumFlushb + tb.getIntFlush();
				flushs[j + 1] = tb.getIntFlush();

				Class c = tbEntity.getClass();
				try {// 通过反射方式赋值
					String methodName1 = "setColumn" + j + 1;
					Method m1 = c.getMethod(methodName1, String.class);
					Object parm = new String(String.valueOf(tb.getIntUsers()));
					m1.invoke(tbEntity, parm);
					String methodName2 = "setColumn" + j + 2;
					Method m2 = c.getMethod(methodName2, String.class);
					m2.invoke(tbEntity, getTotalFlushFormat(tb.getIntFlushKB()));
					// m2.invoke(tbEntity, tb.getIntFlush()+"");
					String methodName3 = "setColumn" + j + 3;
					Method m3 = c.getMethod(methodName3, String.class);
					m3.invoke(tbEntity, getFlushRate(Common.StringTodouble(tb
							.getFlUsersRate())));
					String methodName4 = "setColumn" + j + 4;
					Method m4 = c.getMethod(methodName4, String.class);
					m4.invoke(tbEntity, getFlushRate(Common.StringTodouble(tb
							.getFlFlustRate())));
					String avag = "0.00";
					if (tb.getIntUsers() != 0) {
						avag = getTotalFlushFormat((tb.getIntFlush() / tb
								.getIntUsers()) / 1024.0);
					}
					String methodName5 = "setColumn" + j + 5;
					Method m5 = c.getMethod(methodName5, String.class);
					m5.invoke(tbEntity, String.valueOf(avag));

				} catch (SecurityException e) {
					logger.error(e.getMessage());
				} catch (NoSuchMethodException e) {
					logger.error(e.getMessage());
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage());
				}

			}
			flushs[0] = sumFlushb;
			flushList.add(flushs);
			// tbEntity.setSumFlush(getTotalFlushFormat(sumFlush));
			tbEntity.setSumFlush(getTotalFlushFormat(sumFlusKb));
			tbEntity.setSumUser(sumUser.toString());
			tbEntity.setTerminalName(terminalName);

			listData.add(tbEntity);
		}
		return listData;
	}

	/**
	 * 根据页面业务顺序排列LIST
	 * 
	 * @param list
	 * @param businessName
	 * @return
	 */
	public List<TerminalBusiness> findTerminal(List<TerminalBusiness> list,
			String businesses) {

		List<TerminalBusiness> listData = new ArrayList<TerminalBusiness>();
		String[] business = businesses.split("\\,");
		for (int i = 0; i < business.length; i++) {
			TerminalBusiness tb = null;
			boolean flag = true;
			for (int j = 0; j < list.size(); j++) {
				tb = list.get(j);
				if (tb.getTerminalTypeName().equals(business[i])) {
					tb = list.get(j);
					listData.add(tb);
					flag = false;
					break;
				}

			}

			if (flag) {
				tb = new TerminalBusiness();
				tb.setBusinessName(business[i]);
				tb.setIntFlush(0l);
				tb.setIntUsers(0l);
				tb.setFlFlustRate("0.00");
				tb.setFlUsersRate("0.00");
				tb.calculateData();
				listData.add(tb);
			}
		}

		return listData;

	}

	/**
	 * 保存在业务分析时 页面所选的业务 和 所需要显示的 列 结果
	 */
	public void saveArgs() {

		String business = request.getParameter("business");
		String displayId = request.getParameter("displayId");

		logger.info("business:" + business);
		logger.info("displayId:" + displayId);

		request.getSession().setAttribute("business", business);
		request.getSession().setAttribute("displayId", displayId);
		Struts2Utils.renderText("yes");
	}

}
