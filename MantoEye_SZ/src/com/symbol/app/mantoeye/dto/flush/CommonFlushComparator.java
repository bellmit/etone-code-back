package com.symbol.app.mantoeye.dto.flush;

import java.util.Comparator;

public class CommonFlushComparator implements Comparator {

	/**
	 * 排序字段 1:总流量 2:用户数 3:激活次数
	 */
	private int sortFlag;
	/**
	 * 排序类型 1:升序 2:降序
	 */
	private int sortType;

	public CommonFlushComparator(int sortFlag, int sortType) {
		this.sortFlag = sortFlag;
		this.sortType = sortType;
	}

	public int getSortType() {
		return sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	public int compare(Object o1, Object o2) {
		int i = 0;
		CommonFlush bf1 = (CommonFlush) o1;
		CommonFlush bf2 = (CommonFlush) o2;
		if (sortFlag == 1) {// 总流量排序
			if (bf1.getTotalFlush().compareTo(bf2.getTotalFlush()) == 1) {
				i = (sortType == 1) ? 1 : -1;
			} else if (bf1.getTotalFlush().compareTo(bf2.getTotalFlush()) == 0) {
				i = 0;
			} else {
				i = (sortType == 1) ? -1 : 1;
			}
		} else if (sortFlag == 2) {// 用户数排序
			if (bf1.getIntImsis().compareTo(bf2.getIntImsis()) == 1) {
				i = (sortType == 1) ? 1 : -1;
			} else if (bf1.getIntImsis().compareTo(bf2.getIntImsis()) == 0) {
				i = 0;
			} else {
				i = (sortType == 1) ? -1 : 1;
			}
		} else if (sortFlag == 3) {// 激活次数排序
			if (bf1.getActiveCount().compareTo(bf2.getActiveCount()) == 1) {
				i = (sortType == 1) ? 1 : -1;
			} else if (bf1.getActiveCount().compareTo(bf2.getActiveCount()) == 0) {
				i = 0;
			} else {
				i = (sortType == 1) ? -1 : 1;
			}
		}
		return i;
	}

	public int getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(int sortFlag) {
		this.sortFlag = sortFlag;
	}

}
