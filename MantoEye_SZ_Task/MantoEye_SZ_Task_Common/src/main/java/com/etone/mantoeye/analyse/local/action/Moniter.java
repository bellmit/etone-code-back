/**
 * com.etone.mantoeye.analyse.local.action.Moniter.java
 */
package com.etone.mantoeye.analyse.local.action;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

/**
 * @author Wu Zhenzhen
 * @version Apr 18, 2013 9:32:38 AM
 */
public class Moniter extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(Moniter.class);

	/**
	 * <br>
	 * Created on: Apr 18, 2013 9:32:47 AM
	 * 
	 * Created by: Wu Zhenzhen
	 * 
	 */
	public Moniter() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			int maxMemory = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
			int totalMemory = (int) (Runtime.getRuntime().totalMemory() / (1024 * 1024));
			int freeMemory = (int) (Runtime.getRuntime().freeMemory() / (1024 * 1024));

			logger.info("Current system maxMemory :[{}]M.", maxMemory);
			logger.info("Current system totalMemory :[{}]M.", totalMemory);
			logger.info("Current system freeMemory :[{}]M.", freeMemory);
			logger.info("Useing system memory :[{}]M.",
					(totalMemory - freeMemory));

			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
			}
		}
	}

}
