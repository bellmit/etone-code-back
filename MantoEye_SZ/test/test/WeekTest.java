package test;

import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;

public class WeekTest {
	public static void main(String[] args) {

		// 2011-1-2 

		System.out.println(CommonUtils.getWeek(Common.getDate("2011-01-02")));
	}

}
