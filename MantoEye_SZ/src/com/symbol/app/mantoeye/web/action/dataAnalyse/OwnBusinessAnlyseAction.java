package com.symbol.app.mantoeye.web.action.dataAnalyse;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.businessAnalyse.OwnBusinessAnlyseManager;
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
 * 终端业务分析
 * 
 * 
 */
public class OwnBusinessAnlyseAction extends BaseDispatchAction {
	@Autowired
	private OwnBusinessAnlyseManager ownBusinessAnlyseManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search = "1";// 默认GPRS
	private String timeLevel_search = "2";// 默认天
	private String time_search = CommonUtils.getSYestoday();// 开始时间
	private int spaceLevel_search = 1;
	private String vcBussinessName;
	private long bussinessId;

	public long getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(long bussinessId) {
		this.bussinessId = bussinessId;
	}

	public String getVcBussinessName() {
		return vcBussinessName;
	}

	public void setVcBussinessName(String vcBussinessName) {
		this.vcBussinessName = vcBussinessName;
	}

	public int getSpaceLevel_search() {
		return spaceLevel_search;
	}

	public void setSpaceLevel_search(int spaceLevelSearch) {
		spaceLevel_search = spaceLevelSearch;
	}

	public void initBussnessDialogData() throws Exception {
		Struts2Utils.renderXml(packBussnessInfo());
	}

	/**
	 * 组装业务信息
	 */
	public String packBussnessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = ownBusinessAnlyseManager
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
//					modle = setType.toString().replace("[", "")
//							.replace("]", "");
//					typeIds = con.toString().replace("[", "").replace("]", "");// 型号IDs
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
			for(Iterator it=setType.iterator();it.hasNext();){
				str = str+","+it.next();
			}
			if(str.indexOf(",")!=-1){
				str = str.substring(1);
			}		
		}
		return str;
	}

	private String getMapValueStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for(Iterator<String> it=setType.iterator();it.hasNext();){
				str = str+","+map.get(it.next());
			}
			if(str.indexOf(",")!=-1){
				str = str.substring(1);
			}		
		}
		return str;
	}


	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<CommonSport> list = null;

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {
			SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("nmFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			} // 默认排序方式
		} else {
			page.setOrderBy("nmFlush");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = ownBusinessAnlyseManager.query(page, Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), time_search,spaceLevel_search,bussinessId);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
			gridServerHandler.setData(list, CommonSport.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}


	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		if (vcBussinessName != null) {
			vcBussinessName = new String(vcBussinessName.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		List<CommonSport> list = ownBusinessAnlyseManager.listData(Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), time_search,spaceLevel_search,bussinessId);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端区域分析"
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search+"）";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	/**
	 * 初始化重要业务
	 */
	public void initAreaDialogData() throws Exception {
		Struts2Utils.renderXml(packAreaInfo2());
	}

	private String packAreaInfo2() {
		List<Phone> list = new ArrayList<Phone>();
		logger.info("action----------11111111111111111111");
		Map<String, Map<String, String>> map = ownBusinessAnlyseManager
				.bulidAreaAndType();
		logger.info("action----------test22222222222222222222");
		List<String> areaList = new ArrayList<String>(map.keySet());
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml = xml + "<root>";
		if (areaList != null && !areaList.isEmpty()) {
			for (int i = 0; i < areaList.size(); i++) {
				String areanAndId = areaList.get(i);
				Map<String, String> mapType = map.get(areanAndId);
				String[] str = areanAndId.split(":");// 分离品牌名称和Id

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
	



	public String getTime_search() {
		return time_search;
	}


	public void setTime_search(String timeSearch) {
		time_search = timeSearch;
	}


	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	public String getDataType_search() {
		return dataType_search;
	}

	public void setDataType_search(String dataType_search) {
		this.dataType_search = dataType_search;
	}

	public String getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(String timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
	}

}
