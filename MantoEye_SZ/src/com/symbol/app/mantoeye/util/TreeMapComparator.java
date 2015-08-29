package com.symbol.app.mantoeye.util;

import java.util.Comparator;


public class TreeMapComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		return (Integer)o1-(Integer)o2;
	}

}
