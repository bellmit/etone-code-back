package com.symbol.app.mantoeye.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.common.CommonDimension;
import com.symbol.app.mantoeye.service.CommonDimensionManager;
import com.symbol.app.mantoeye.service.businessAnalyse.BusinessDistributeManager;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;

public class TerminalDialogAction extends BaseDispatchAction {

	/**
	 * 初始化复选框数据
	 * 
	 * @throws Exception
	 */

	@Autowired
	private BusinessDistributeManager businessDistributeManager;

	@Autowired
	private TerminalManager baseTerminalManager;

	@Autowired
	private CommonDimensionManager commonDimensionManager;

	public void initTerminalDialogData() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Struts2Utils.renderXml(packTerminalInfo());
	}

	public void initBussnessDialogData() throws Exception {
		Struts2Utils.renderXml(packBussnessInfo());
	}

	/**
	 * 组装业务信息
	 */
	public String packBussnessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = businessDistributeManager
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
	 * 初始化区域弹出框
	 */
	public void initAreaDialogData() throws Exception {
		// Struts2Utils.renderXml(packAreaInfo());
		Struts2Utils.renderXml(packAreaInfo2());
	}

	private String packAreaInfo2() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = baseTerminalManager
				.bulidAreaAndType();
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
		return xml;
	}

	/**
	 * 组装区域弹出框数据
	 */
	public String packAreaInfo() {
		List<CommonDimension> bscList = commonDimensionManager.findAllBsc();
		List<CommonDimension> streetList = commonDimensionManager
				.findAllStreet();
		List<CommonDimension> saleAreaList = commonDimensionManager
				.findAllSaleArea();
		List<CommonDimension> sgsnList = commonDimensionManager.findAllSgsn();
		List<CommonDimension> branchList = commonDimensionManager
				.findAllBranch();
		Map<Integer, List<CommonDimension>> map = new HashMap<Integer, List<CommonDimension>>();
		map.put(CommonConstants.MANTOEYE_SPACE_LEVEL_BSC, bscList);
		map.put(CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN, sgsnList);
		map.put(CommonConstants.MANTOEYE_SPACE_LEVEL_STREET, streetList);
		map.put(CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA, saleAreaList);
		map.put(CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH, branchList);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml = xml + "<root>";
		for (int key : map.keySet()) {
			String name = "";
			switch (key) {
			case CommonConstants.MANTOEYE_SPACE_LEVEL_BSC:
				name = "BSC";
				break;
			case CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN:
				name = "SGSN";
				break;
			case CommonConstants.MANTOEYE_SPACE_LEVEL_STREET:
				name = "街道办";
				break;
			case CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA:
				name = "营销片区";
				break;
			case CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH:
				name = "分公司";
				break;
			}
			List<CommonDimension> list = map.get(key);
			if (list != null && !list.isEmpty()) {
				String modleNames = "";
				String modleIds = "";
				for (CommonDimension c : list) {
					modleIds = modleIds + key + ":" + c.getId() + ",";
					modleNames = modleNames + c.getName() + ",";
				}
				if ("".equals(modleIds)) {
					modleIds = modleIds.substring(0, modleIds.lastIndexOf(","));
					modleNames = modleNames.substring(0, modleNames
							.lastIndexOf(","));
				}
				xml = xml + "<data>" + "<brandId>" + key + "</brandId>"
						+ "<brandName>" + name + "</brandName>" + "<modelId>"
						+ modleIds + "</modelId>" + "<modelName>" + modleNames
						+ "</modelName>" + "</data>";
			}

		}
		xml = xml + "</root>";
		return xml;
	}

	/**
	 * 根据任务ID 组装业务信息
	 */
	public String packBussnessInfoByTaskId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = businessDistributeManager
				.queryBussAndBussTypeByTaskId(Common.StringToInt(taskId));
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
		Struts2Utils.renderXml(xml);
		return null;
	}

	/**
	 * 组装业务信息
	 */
	public String packTerminalInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = baseTerminalManager
				.bulidBrandAndType();
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
		return xml;
	}

	/**
	 * 根据任务ID 组装业务信息
	 */
	public String packTerminalInfoByTaskId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = baseTerminalManager
				.bulidBrandAndType(Common.StringToInt(taskId));
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
		Struts2Utils.renderXml(xml);
		return null;
	}
}
