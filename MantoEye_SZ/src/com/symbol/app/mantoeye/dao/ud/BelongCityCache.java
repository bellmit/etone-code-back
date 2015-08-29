/**
 * com.symbol.app.mantoeye.dao.ud.BelongCityCache.java
 */
package com.symbol.app.mantoeye.dao.ud;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wu Zhenzhen
 * @version 2013-10-17 下午12:39:07
 */
public abstract class BelongCityCache {

	private static Map<Integer, Boolean> cache = new HashMap<Integer, Boolean>();

	/**
	 * 通过IMSI号段取得是否外地
	 * 
	 * @param
	 * @return
	 */
	public static Boolean get(int msisdnSegment) {
		synchronized (cache) {
			return cache.get(msisdnSegment);
		}
	}

	/**
	 * 对缓存进行更新
	 * 
	 * @param sgsnTmpMap
	 */
	public static void updateCache(Map<Integer, Boolean> tmpMap) {
		synchronized (cache) {
			cache.clear();
			cache.putAll(tmpMap);
		}
	}

	/**
	 * 取得该cache的大小
	 * 
	 * @return
	 */
	public static int size() {
		return cache.size();
	}
}
