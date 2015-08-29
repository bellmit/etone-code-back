/**
 * z.z.w.others.TimeOutTest.java
 */
package z.z.w.others.timeout.test;

import java.util.Random;

import z.z.w.common.DateTools;
import z.z.w.common.ThreadTools;

/**
 * @author Wu Zhenzhen
 * @version Mar 21, 2013 11:37:38 AM
 */
public class TimeOutTest {

	/**
	 * <br>
	 * Created on: Mar 21, 2013 11:37:38 AM
	 */
	public TimeOutTest() {
	}

	/**
	 * <br>
	 * Created on: Mar 21, 2013 11:37:38 AM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// try {
		// System.out.println(DateTools.getDateToLong(DateTools
		// .getDate("1970-01-01 00:00:00")));
		// System.out.println(DateTools.getCurrentDateToLong());
		// } catch (ParseException e) {
		// }

		// int handleDelayTime = 10*1000;//10s
		int handleDelayTime = 1000 * 60 * 30;// 1s*60=1m*30=半小時
		long appCurrentDate = 0;
		long delayEndDate = 0;

		while (true) {

			/**
			 * 
			 1363838764870
			 */
			Random rand = new Random(System.currentTimeMillis());
			// random.nextInt(max)%(max-min+1) + min;
			// int temp = rand.nextLong(1363838764870l)
			long temp = rand.nextLong() % (1363838764870l - 288000001 + 1)
					- 288000001;

			TestData gnAppData = new TestData();
			gnAppData.setEndTime(temp);

			if (CurrentDateContext.APP_CURRENT_DATE == 0)
				CurrentDateContext.APP_CURRENT_DATE = gnAppData.getEndTime();
			else {

				System.out
						.println("-- CurrentDateContext.APP_CURRENT_DATE : "
								+ DateTools.getParseDateToStr(DateTools
										.getDateByLongTime(CurrentDateContext.APP_CURRENT_DATE)));

				appCurrentDate = CurrentDateContext.APP_CURRENT_DATE;

				delayEndDate = appCurrentDate - handleDelayTime;

				System.out.println("-- gnAppData TIME : "
						+ DateTools.getParseDateToStr(DateTools
								.getDateByLongTime(gnAppData.getEndTime())));

				System.out.println("-- delayEndDate : "
						+ DateTools.getParseDateToStr(DateTools
								.getDateByLongTime(delayEndDate)));

				if (gnAppData.getEndTime() > delayEndDate) {

					if (gnAppData.getEndTime() > appCurrentDate)
						CurrentDateContext.APP_CURRENT_DATE = gnAppData
								.getEndTime();

					System.out.println("----OK-----");

				} else {

					System.out.println("-----------TIME OUT-----------");
					System.out
							.println("-- TIME  OUTgnAppData.getEndTime() : "
									+ DateTools.getParseDateToStr(DateTools
											.getDateByLongTime(gnAppData
													.getEndTime())));

					System.out.println("-- TIME OUTappCurrentDate : "
							+ DateTools.getParseDateToStr(DateTools
									.getDateByLongTime(appCurrentDate)));

					System.out.println("-----------TIME OUT-----------");
				}
			}

			ThreadTools.sleep(200);
		}

	}

}
