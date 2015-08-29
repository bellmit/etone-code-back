package com.symbol.app.mantoeye.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 公用的静态常量
 * 
 * @author rankin
 * 
 */
public class CommonConstants {
	
	public static void setSegMap()
		{
		CommonConstants.segMap = new HashMap<Integer,String>();
		CommonConstants.segMap.put(0, "其它");
		CommonConstants.segMap.put(1, "130");
		CommonConstants.segMap.put(2, "131");
		CommonConstants.segMap.put(3, "132");
		CommonConstants.segMap.put(4, "133");
		CommonConstants.segMap.put(5, "134");
		CommonConstants.segMap.put(6, "135");
		CommonConstants.segMap.put(7, "136");
		CommonConstants.segMap.put(8, "137");
		CommonConstants.segMap.put(9, "138");
		CommonConstants.segMap.put(10, "139");
		CommonConstants.segMap.put(11, "159");		
		}

	public static final String MANTOEYE_BUSINESS_OTHER_NAME = "其他";// 为识别业务的名称

	// 时间粒度设置1:小时 2:天 3:周 4:月
	public static final int MANTOEYE_TIME_LEVEL_HOUR = 1;
	public static final int MANTOEYE_TIME_LEVEL_DAY = 2;
	public static final int MANTOEYE_TIME_LEVEL_WEEK = 3;
	public static final int MANTOEYE_TIME_LEVEL_MONTH = 4;

	// 空间区域维度 1:BSC 2:SGSN 3:街道 4:营销片区 5:分公司
	public static final int MANTOEYE_SPACE_LEVEL_BSC = 1;
	public static final int MANTOEYE_SPACE_LEVEL_SGSN = 2;
	public static final int MANTOEYE_SPACE_LEVEL_STREET = 3;
	public static final int MANTOEYE_SPACE_LEVEL_SALEAREA = 4;
	public static final int MANTOEYE_SPACE_LEVEL_BRANCH = 5;
//	public static final int MANTOEYE_SPACE_LEVEL_CGI = 6;
//	public static final int MANTOEYE_SPACE_LEVEL_BUSOFFICE = 7;//营业厅
//	public static final int MANTOEYE_SPACE_LEVEL_CVILLAGE = 8;//城村
//	public static final int MANTOEYE_SPACE_LEVEL_APN = 9;
	
	// APN空间区域维度 1:BSC 2:SGSN 3:街道 4:营销片区 5:分公司
	public static final int MANTOEYE_APN_SPACE_LEVEL_BSC = 1;
	public static final int MANTOEYE_APN_SPACE_LEVEL_SGSN = 2;
	public static final int MANTOEYE_APN_SPACE_LEVEL_STREET = 3;
	public static final int MANTOEYE_APN_SPACE_LEVEL_SALEAREA = 4;
	public static final int MANTOEYE_APN_SPACE_LEVEL_BRANCH = 5;
	
	
	//KPI指标类型
	public static final int MANTOEYE_KPI_PDP = 2;
	public static final int MANTOEYE_KPI_RAU = 3;
	public static final int MANTOEYE_KPI_ATTACH = 1;
	
	public static Map<Integer,String> segMap;
	
	public static Map<Integer,String> getSegMap(){
		if(segMap==null){
			setSegMap();
		}
		return segMap;
	}
	

}
