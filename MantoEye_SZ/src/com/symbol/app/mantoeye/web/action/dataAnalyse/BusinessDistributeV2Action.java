package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.BusinessDistributeV2Manager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.app.mantoeye.web.action.Phone;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 全网数据流量分布
 * 
 * @author rankin
 * 
 */
public class BusinessDistributeV2Action extends BaseDispatchAction {

	@Autowired
	private BusinessDistributeV2Manager businessDistributeV2Manager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;
	HttpServletRequest request = Struts2Utils.getRequest();
	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private String timeLevel = "2";// 时间周期 2:天
	private String total_time_search;// 总体分析过来的时间
	private String total_time_level;// 总体分析过来的时间周期
	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private String business;// 业务
	private String businessType = "businesstype";// 业务类型
	private int dataType = 1;	//默认是GPRS数据类型

	/**
	 * <br>
	 * Created on: 2013-10-16 上午11:40:12
	 * 
	 * @return the dataType
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * <br>
	 * Created on: 2013-10-16 上午11:40:12
	 * 
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	private Long nmGroupId;

	public Long getNmGroupId() {
		return nmGroupId;
	}

	public void setNmGroupId(Long nmGroupId) {
		this.nmGroupId = nmGroupId;
	}

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		if (!"null".equals(total_time_search)) {
			timeLevel = total_time_level;
			searchDateStart = total_time_search;
		}
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("nmFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("nmFlush");
			page.setOrder("desc");
		}

		int totalRowNum = gridServerHandler.getTotalRowNum();

		totalRowNum = businessDistributeV2Manager.queryBussDistribute(
				Common.StringToInt(timeLevel), searchDateStart, dataType,
				businessType, business, page.getOrderBy(), page.getOrder())
				.size();
		gridServerHandler.setTotalRowNum(totalRowNum);
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = businessDistributeV2Manager.queryBussDistributePage(page,
				Common.StringToInt(timeLevel), searchDateStart, dataType,
				businessType, business);

		// business数据太长 导出时无法通过url传过来
		request.getSession().setAttribute("business4export", business);

		// logger.info("====[{}]",page.getResult());

		gridServerHandler.setData(page.getVresult(), CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 查询数据
	 */
	public void queryAll() throws ServletException, IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		if (!"null".equals(total_time_search)) {
			timeLevel = total_time_level;
			searchDateStart = total_time_search;
		}
		String orderBy = "nmFlush";
		String order = "desc";
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			order = si.getSortOrder();
			orderBy = MantoEyeUtils.getSortField(si.getFieldName());
			// 默认排序方式
		}
		request.getSession().setAttribute("business4export", business);

		List<CommonFlush> list = businessDistributeV2Manager
				.queryBussDistribute(Common.StringToInt(timeLevel),
						searchDateStart, dataType, businessType, business,
						orderBy, order);
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 判断是否是叶子 oldVersion:旧业务，true：有第二层跳转，false：没有第二层跳转
	 */
	public void isExistLeaf() throws ServletException, IOException {
		String flag = businessDistributeV2Manager.isExistLeaf(nmGroupId);
		Struts2Utils.renderText(flag);
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		String orderBy = "nmFlush";
		String order = "desc";
		if (si != null) {
			order = si.getSortOrder();
			orderBy = MantoEyeUtils.getSortField(si.getFieldName());
			// 默认排序方式
		}
		business = request.getSession().getAttribute("business4export") + "";
		// logger.info("before:" + business);
		// business = new String(business.getBytes("ISO-8859-1"), "UTF-8");
		logger.info("after:" + business);
		List<CommonFlush> list = businessDistributeV2Manager
				.queryBussDistribute(Common.StringToInt(timeLevel),
						searchDateStart, dataType, businessType, business,
						orderBy, order);
		logger.info("size:" + list.size());
		String exportmsg = ExportMsg.EXPORT_FLUSH_BUSINESSTYPE
				+ Common.getTimeLevelCH(timeLevel) + "（" + searchDateStart
				+ "）";
		if (businessType.equals("business")) {
			exportmsg = ExportMsg.EXPORT_FLUSH_BUSINESS
					+ Common.getTimeLevelCH(timeLevel) + "（" + searchDateStart
					+ "）";
		}
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public void initBussnessDialogData() throws Exception {
		Struts2Utils.renderXml(packBussnessInfo());
	}

	/**
	 * 组装业务信息
	 */
	public String packBussnessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = businessDistributeV2Manager
				.queryBussAndBussType();
		List<String> brandList = new ArrayList<String>(map.keySet());
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml = xml + "<root>";
		if (brandList != null && !brandList.isEmpty()) {
			for (int i = 0; i < brandList.size(); i++) {
				String brandAnadId = brandList.get(i);
				Map<String, String> mapType = map.get(brandAnadId);
				String[] str = brandAnadId.split(":");// 分离品牌名称和Id

				Set setType = mapType.keySet();
				Collection con = mapType.values();
				String modle = null;
				String typeIds = null;
				if (setType != null) {
					// modle = setType.toString().replace("[", "")
					// .replace("]", "");
					// typeIds = con.toString().replace("[", "").replace("]",
					// "");// 型号IDs
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
		// logger.info("----------------------------------------------------------------");
		// logger.info(xml);
		// logger.info("----------------------------------------------------------------");
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

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public String getTotal_time_search() {
		return total_time_search;
	}

	public void setTotal_time_search(String total_time_search) {
		this.total_time_search = total_time_search;
	}

	public String getTotal_time_level() {
		return total_time_level;
	}

	public void setTotal_time_level(String total_time_level) {
		this.total_time_level = total_time_level;
	}

	public String getSearchDateStart() {
		return searchDateStart;
	}

	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}
