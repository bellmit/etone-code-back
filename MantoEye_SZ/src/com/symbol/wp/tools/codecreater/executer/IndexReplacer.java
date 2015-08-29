package com.symbol.wp.tools.codecreater.executer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.symbol.wp.tools.codecreater.info.MemoInfo;

public class IndexReplacer {
	/**
	 * 取得INDEX一串get的字符串
	 * 
	 * @param info
	 * @param itemMemoMap
	 * @return
	 */
	public static String getItemString(HashMap<String, MemoInfo> itemMemoMap) {
		// HashMap<String,String> test = new HashMap<String,String>();
		// test.put("name", "test");
		// test.put("feng", "fengTest");
		// test.put("xian", "xianTest");

		Set<String> set = itemMemoMap.keySet();
		Iterator it = set.iterator();
		String item = "";
		StringBuffer itemStr = new StringBuffer();
		char[] chars = null;
		while (it.hasNext()) {
			item = (String) it.next();
			String firstStr = item.substring(0, 1).toUpperCase();
			firstStr = "get" + firstStr;

			itemStr = itemStr.append(firstStr
					+ item.substring(1, item.length()).trim() + ",");
		}
		itemStr.setLength(itemStr.length() - 1);
		return itemStr.toString().trim();
	}

	/**
	 * index的说明字符串
	 * 
	 * @param itemMemoMap
	 * @return
	 */
	public static String getMemoStr(HashMap<String, MemoInfo> itemMemoMap) {
		// HashMap<String,String> test = new HashMap<String,String>();
		// test.put("name", "美国");
		// test.put("feng", "欧盟");
		// test.put("xian", "中国");

		Set set = itemMemoMap.keySet();
		Iterator it = set.iterator();
		String itemMemo = "";

		StringBuffer itemStr = new StringBuffer();
		while (it.hasNext()) {
			String key = (String) it.next();
			MemoInfo info = itemMemoMap.get(key);
			itemStr = itemStr.append(info.getMemo().trim() + ",");

		}
		itemStr.setLength(itemStr.length() - 1);
		return itemStr.toString().trim();
	}

	public static void main(String[] args) {
		IndexReplacer f = new IndexReplacer();
		// logger.info(f.getMemoStr(null)+"");
	}
}
