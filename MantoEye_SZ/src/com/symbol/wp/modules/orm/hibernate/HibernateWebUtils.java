package com.symbol.wp.modules.orm.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.DataType;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.utils.ReflectionUtils;


/**
 * Hibernate针对Web应用的Utils函数集合.
 */
public class HibernateWebUtils {

	private HibernateWebUtils() {
	}

	/**
	 * 根据对象ID集合,整理合并集合.
	 * 
	 * 默认对象主键的名称名为"id".
	 * @see #mergeByCheckedIds(Collection, Collection, Class, String)
	 */
	public static <T, ID> void mergeByCheckedIds(final Collection<T> srcObjects, final Collection<ID> checkedIds,
			final Class<T> clazz) {
		mergeByCheckedIds(srcObjects, checkedIds, clazz, "id");
	}

	/**
	 * 根据对象ID集合,整理合并集合.
	 * 
	 * 页面发送变更后的子对象id列表时,删除原来的子对象集合再根据页面id列表创建一个全新的集合这种看似最简单的做法是不行的.
	 * 因此需采用如此的整合算法：在源集合中删除id不在ID集合中的对象,根据ID集合中的id创建对象并添加到源集合中.
	 * 
	 * @param srcObjects 源对象集合
	 * @param checkedIds  目标ID集合
	 * @param clazz  集合中对象的类型
	 * @param idName 对象主键的名称
	 */
	public static <T, ID> void mergeByCheckedIds(final Collection<T> srcObjects, final Collection<ID> checkedIds,
			final Class<T> clazz, final String idName) {

		//参数校验
		Assert.notNull(srcObjects, "scrObjects不能为空");
		Assert.hasText(idName, "idName不能为空");
		Assert.notNull(clazz, "clazz不能为空");

		//目标ID集合为空,删除源集合中所有对象后直接返回.
		if (checkedIds == null) {
			srcObjects.clear();
			return;
		}

		//遍历源集合,如果其id不在目标ID集合中的对象,进行删除.
		//同时,在目标ID集合中删除已在源集合中的id,使得目标ID集合中剩下的id均为源集合中没有的ID.
		Iterator<T> srcIterator = srcObjects.iterator();
		try {

			while (srcIterator.hasNext()) {
				T element = srcIterator.next();
				Object id;
				id = PropertyUtils.getProperty(element, idName);

				if (!checkedIds.contains(id)) {
					srcIterator.remove();
				} else {
					checkedIds.remove(id);
				}
			}

			//ID集合目前剩余的id均不在源集合中,创建对象,为id属性赋值并添加到源集合中.
			for (ID id : checkedIds) {
				T obj = clazz.newInstance();
				PropertyUtils.setProperty(obj, idName, id);
				srcObjects.add(obj);
			}

		} catch (Exception e) {
			ReflectionUtils.handleException(e);
		}
	}

	/**
	 * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
	 * 默认Filter属性名前缀为filter_.
	 * 
	 * @see #buildPropertyFilters(HttpServletRequest, String)
	 */
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request) {
		return buildPropertyFilters(request, "filter_");
	}

	/**
	 * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
	 * 默认Filter属性名前缀为filter_.
	 * 
	 * @see #buildPropertyFilters(HttpServletRequest, String)
	 */
	public static List<PropertyFilter> buildPropertyFiltersWithConvert(final HttpServletRequest request) {
		String filterPrefix = "filter_";
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

		//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, String> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix);
		// logger.info("---filterParamMap---"+filterParamMap);

		//分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, String> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = entry.getValue();			
			//如果value值为空,则忽略此filter.
			boolean omit = StringUtils.isBlank(value);
			if (!omit) {
				//去除前后空格
				value = value.trim();
				//分析filterName,获取matchType与propertyName
				MatchType matchType;
				String allTypeCode = StringUtils.substringBefore(filterName, "_");
				String dataTypeCode="STRING";
				String matchTypeCode = "EQ";
				DataType dataType;
				//类型转换
				if(allTypeCode.contains("|")){
					matchTypeCode = StringUtils.substringBefore(allTypeCode, "|");
					dataTypeCode = StringUtils.substringAfter(allTypeCode, "|");					
					try {
						dataType = Enum.valueOf(DataType.class, dataTypeCode);
					} catch (RuntimeException e) {
						throw new IllegalArgumentException("数据类型没有按规则编写,无法进行数据类型转换.", e);
					}
				}else{
					//字符串类型
					dataType = DataType.STRING;
					matchTypeCode = allTypeCode;
				}
				
				try {
					matchType = Enum.valueOf(MatchType.class, matchTypeCode);
				} catch (RuntimeException e) {
					throw new IllegalArgumentException("filter名称没有按规则编写,无法得到属性比较类型.", e);
				}
				
				String propertyName = StringUtils.substringAfter(filterName, "_");
				
				Object objvalue = value;
				//数据类型转换
				if(dataType!=null&&dataType!=DataType.STRING){
					objvalue = formatValue(dataType,value);
				}else if(dataType==DataType.STRING){
					objvalue = convertToUTF8(value+"");
					
				}
//				logger.info("objvalue||||--->>>"+objvalue);
				if(objvalue!=null){
					//logger.info(objvalue+"----Type--"+objvalue.getClass());
					//logger.info("----propertyName--"+propertyName);
					PropertyFilter filter = new PropertyFilter(propertyName, objvalue, matchType);
					filterList.add(filter);
				}		
			}
		}
		
		return filterList;
	}
	/**
	 * 转换编码 iso-8859-1 转换为utf-8
	 * 
	 * @param s
	 * @return
	 */
	private static String convertToUTF8(String s) {
		if (s == null) {
			return s;
		}
		try {
			byte abyte0[] = s.getBytes("iso-8859-1");
			return new String(abyte0, "utf-8");
		} catch (Exception exception) {
			//log.error(exception);
		}
		return s;
	}
	/**
	 * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
	 * PropertyFilter命名规则为Filter属性前缀_比较类型_属性名.
	 * 
	 * eg.
	 * filter_EQUAL_name
	 * filter_LIKE_name|email
	 */
	@SuppressWarnings("unchecked")
	public static<T> List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request, final String filterPrefix) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

		//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, String> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix);
		// logger.info("---filterParamMap---"+filterParamMap);

		//分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, String> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = entry.getValue();			
			//如果value值为空,则忽略此filter.
			boolean omit = StringUtils.isBlank(value);
			if (!omit) {
				//去除前后空格
				value = value.trim();
				//分析filterName,获取matchType与propertyName
				MatchType matchType;
				String allTypeCode = StringUtils.substringBefore(filterName, "_");
				String dataTypeCode="STRING";
				String matchTypeCode = "EQ";
				DataType dataType;
				//类型转换
				if(allTypeCode.contains("|")){
					matchTypeCode = StringUtils.substringBefore(allTypeCode, "|");
					dataTypeCode = StringUtils.substringAfter(allTypeCode, "|");					
					try {
						dataType = Enum.valueOf(DataType.class, dataTypeCode);
					} catch (RuntimeException e) {
						throw new IllegalArgumentException("数据类型没有按规则编写,无法进行数据类型转换.", e);
					}
				}else{
					//字符串类型
					dataType = DataType.STRING;
					matchTypeCode = allTypeCode;
				}
				
				try {
					matchType = Enum.valueOf(MatchType.class, matchTypeCode);
				} catch (RuntimeException e) {
					throw new IllegalArgumentException("filter名称没有按规则编写,无法得到属性比较类型.", e);
				}
				
				String propertyName = StringUtils.substringAfter(filterName, "_");
				
				Object objvalue = value;
				//数据类型转换
				if(dataType!=null&&dataType!=DataType.STRING){
					objvalue = formatValue(dataType,value);
				}
				if(objvalue!=null){
					// logger.info(objvalue+"----Type--"+objvalue.getClass());
					// logger.info("----propertyName--"+propertyName);
					PropertyFilter filter = new PropertyFilter(propertyName, objvalue, matchType);
					filterList.add(filter);
				}		
			}
		}
		
		return filterList;
	}
	
//	/**
//	 * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
//	 * PropertyFilter命名规则为Filter属性前缀_比较类型_属性名.
//	 * 
//	 * eg.
//	 * filter_EQUAL_name
//	 * filter_LIKE_name|email
//	 */
//	@SuppressWarnings("unchecked")
//	public static<T> List<PropertyFilter> buildAjaxPropertyFilters(Map<String, String> filterParamMap) {
//		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
//
//		 logger.info("---filterParamMap---"+filterParamMap);
//
//		//分析参数Map,构造PropertyFilter列表
//		for (Map.Entry<String, String> entry : filterParamMap.entrySet()) {
//			String filterName = entry.getKey();
//			String value = entry.getValue();			
//			//如果value值为空,则忽略此filter.
//			boolean omit = StringUtils.isBlank(value);
//			if (!omit) {
//				//去除前后空格
//				value = value.trim();
//				//分析filterName,获取matchType与propertyName
//				MatchType matchType;
//				String allTypeCode = StringUtils.substringBefore(filterName, "_");
//				String dataTypeCode="STRING";
//				String matchTypeCode = "EQ";
//				DataType dataType;
//				//类型转换
//				if(allTypeCode.contains("|")){
//					matchTypeCode = StringUtils.substringBefore(allTypeCode, "|");
//					dataTypeCode = StringUtils.substringAfter(allTypeCode, "|");					
//					try {
//						dataType = Enum.valueOf(DataType.class, dataTypeCode);
//					} catch (RuntimeException e) {
//						throw new IllegalArgumentException("数据类型没有按规则编写,无法进行数据类型转换.", e);
//					}
//				}else{
//					//字符串类型
//					dataType = DataType.STRING;
//					matchTypeCode = allTypeCode;
//				}
//				
//				try {
//					matchType = Enum.valueOf(MatchType.class, matchTypeCode);
//				} catch (RuntimeException e) {
//					throw new IllegalArgumentException("filter名称没有按规则编写,无法得到属性比较类型.", e);
//				}
//				
//				String propertyName = StringUtils.substringAfter(filterName, "_");
//				
//				Object objvalue = value;
//				//数据类型转换
//				if(dataType!=null&&dataType!=DataType.STRING){
//					objvalue = formatValue(dataType,value);
//				}
//				if(objvalue!=null){
//					logger.info(objvalue+"----Type--"+objvalue.getClass());
//					logger.info("----propertyName--"+propertyName);
//					PropertyFilter filter = new PropertyFilter(propertyName, objvalue, matchType);
//					filterList.add(filter);
//				}		
//			}
//		}
//		
//		return filterList;
//	}
	
	protected static Object  formatValue(DataType dataType,String value){
		 if(DataType.STRING.equals(dataType)){
				return value;
			}else if(DataType.INTEGER.equals(dataType)){
			try {
				return Integer.parseInt(value);
			} catch (Exception _ex) {
				return null;
			}
		}
		else if(DataType.DOUBLE.equals(dataType)){
				try {
					return Double.parseDouble(value);
				} catch (Exception _ex) {
					return null;
				}
		}
		else if(DataType.FLOAT.equals(dataType)){
			try {
				return Float.parseFloat(value);
			} catch (Exception _ex) {
				return null;
			}
		}
		else if(DataType.BYTE.equals(dataType)){
			try {
				return new Byte(value);
			} catch (Exception _ex) {
				return null;
			}
		}
		else if(DataType.LONG.equals(dataType)){
			try {
				return Long.parseLong(value);
			} catch (Exception _ex) {
				return null;
			}
		}
		else if(DataType.DATE.equals(dataType)){
			if (value == null)
				return null;
			if (value.length() < 11)
				value = value + " 01:01:01";
			SimpleDateFormat simpledateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			try {
				Date date = simpledateformat.parse(value);
				return date;
			} catch (Exception _ex) {			
				return null;
			}
		}else if(DataType.BOOLEAN.equals(dataType)){
			if(value.equalsIgnoreCase("true")){
				return true;
			}else if(value.equalsIgnoreCase("false")){
				return false;
			}else{
				return null;
			}
		}
		else{
			throw new IllegalArgumentException("查询条件类型错误，暂时只支持Long，Date，Intefer，Boolean类型的转换.");
		}
		
	}
}
