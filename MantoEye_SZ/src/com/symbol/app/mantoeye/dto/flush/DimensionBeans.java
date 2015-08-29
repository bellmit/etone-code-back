package com.symbol.app.mantoeye.dto.flush;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.symbol.app.mantoeye.constants.DimensionEnum;
import com.symbol.wp.core.util.Common;

/**
 * 
 * 保存所有的维表的数据，在系统启动时初始化此对象，保存在容器中， 定时与数据库同步数据
 * 
 * @author rankin
 * 
 */
public class DimensionBeans {

	private Map<DimensionEnum, Map<Long, String>> dimensions;
	private Map<DimensionEnum, Map<Long, List<Long>>> dimensionsList;

	public Map<DimensionEnum, Map<Long, String>> getDimensions() {
		return dimensions;
	}

	public void setDimensions(Map<DimensionEnum, Map<Long, String>> dimensions) {
		this.dimensions = dimensions;
	}

	public Map<DimensionEnum, Map<Long, List<Long>>> getDimensionsList() {
		return dimensionsList;
	}

	public void setDimensionsList(
			Map<DimensionEnum, Map<Long, List<Long>>> dimensionsList) {
		this.dimensionsList = dimensionsList;
	}

	public void refreshBeans(DimensionEnum de, Map<Long, String> beans) {
		if (dimensions == null) {
			dimensions = new HashMap<DimensionEnum, Map<Long, String>>();
		}
		dimensions.put(de, beans);
	}

	public void refreshBusinessBeans(DimensionEnum de,
			Map<Long, List<Long>> beans) {
		if (dimensionsList == null) {
			dimensionsList = new HashMap<DimensionEnum, Map<Long, List<Long>>>();
		}
		dimensionsList.put(de, beans);
	}

	/**
	 * 根据业务ID获取业务名称
	 */
	public String getBusinessName(Map<Long, String> businessMap, String id) {
		String businessName = "";
		Iterator it = businessMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			if (((Long) entry.getKey()).equals(Common.StringToLong(id))) {
				businessName = entry.getValue() + "";
			}
		}
		return businessName;
	}

	/**
	 * 根据业务类型ID和业务ID获取业务名称
	 */
	public String getBusinessName(Long businessTypeId, Long businessId) {
		String businessName = "";
		Map<Long, List<Long>> businessRuleMap = this.getDimensionsList().get(
				DimensionEnum.业务规则);
		List<Long> businessIdsList = businessRuleMap.get(businessTypeId);// 从业务类型中获取业务集合
		Map<Long, String> businessMap = this.getDimensions().get(
				DimensionEnum.业务);// 获取业务维表
		if (businessIdsList != null && !businessIdsList.isEmpty()) {
			if (businessIdsList.contains(businessId)) {// 判断业务集合中是否含有该业务
				businessName = businessMap.get(businessId);// 如果有,则从业务维表获取业务名称
			}
		}
		return businessName;
	}
	/**
	 * 根据维表类型和该类型的id获取名称
	 */
	public String getDimensionNameById(DimensionEnum de, Long id) {
		String businessName = "";
		Map<Long, String> businessMap = this.getDimensions().get(de);// 获取业务维表
		if (businessMap != null && !businessMap.isEmpty()) {
			businessName=businessMap.get(id);
		}
		if(businessName==null||businessName==""){
			businessName = "--";
		}
		return businessName;
	}
}
