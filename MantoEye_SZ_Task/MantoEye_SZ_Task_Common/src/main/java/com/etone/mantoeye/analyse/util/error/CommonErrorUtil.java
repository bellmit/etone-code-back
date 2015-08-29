/**
 *  com.etone.mantoeye.analyse.util.error.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.util.error;

import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

/**
 * @author Wu Zhenzhen
 * @version May 21, 2012 5:03:07 PM
 */
public class CommonErrorUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(CommonErrorUtil.class);

	public static boolean isTableLockError(Exception e) {
		logger.debug("錯誤信息:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
				new Object[]{e.getMessage(), e.getCause(), e.getClass()});

		if (StringUtils.containsOnly("created by transaction", e.getCause()
				.getMessage())) {
			return true;
		} else if (StringUtils
				.containsOnly("locked", e.getCause().getMessage())) {
			return true;
		}
		return false;
	}
}
