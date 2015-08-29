package com.symbol.wp.core.util;

import javax.servlet.http.HttpServletRequest;

import com.symbol.wp.tools.gtgrid.util.StringUtils;

public class ReqUtils {
	
	/**
	 * 取得request对应string类型的值
	 * @param request
	 * @param name
	 * @return  request 对应的值
	 */
	public static String getReqString(HttpServletRequest request,String name){
		String value = request.getParameter(name);
		if(value==null) return "";		
		return value;
	}
	
	/**
	 * 取得request对应string类型的值
	 * @param request
	 * @param name
	 * @param dfvalue
	 * @return request 对应的值 如果为空，刚返回dfvalue
	 */
	public static String gerReqString(HttpServletRequest request,String name,String dfvalue){
		String value =getReqString(request,name);
		if("".equals(value)) return dfvalue;
		return value;
	}
	
	/**
	 * 取得request对应int类型的值
	 * @param request
	 * @param name
	 * @return request 对应的值
	 */
	public static int getReqInt(HttpServletRequest request,String name){
		int value = 0;
		String strvalue = getReqString(request,name);
		if(!"".equals(strvalue)){
			
			try {
				value = Integer.valueOf(strvalue);
			} catch (Exception e) {
			}
		} 
		return value;		
	}
	
	/**
	 * 取得request对应int类型的值
	 * @param request
	 * @param name
	 * @param dfvalue
	 * @return request对应int类型的值
	 */
	public static int getReqInt(HttpServletRequest request,String name,int dfvalue){
		int value = getReqInt(request,name);
		if(value==0) return dfvalue;
		return value;
	}
	
	public static String[] getReqStringArray(HttpServletRequest request,String name){
		String[] stringArray = new String[0];
		
		String strvalue = getReqString(request,name);
		if(!"".equals(strvalue)){
			stringArray = strvalue.split(",");
		}
		return stringArray;
				
	}
	
	public static int[] getReqIntArray(HttpServletRequest request,String name){
		int[] intArray = new int[0] ;
		String[] stringArray = getReqStringArray(request,name);
		 
		if(stringArray.length>0){
			intArray = new int[stringArray.length];
			for(int i=0; i<stringArray.length;i++)
			{
				intArray[i] = StringUtils.StringToInteger(stringArray[i], 0);
			}
			
		}			
		return intArray;
	}
}
