/**
 * com.etone.mantoeye.analyse.service.common.IHiTechFairElexconDataMananger.java
 */
package com.etone.mantoeye.analyse.service.common;

import java.sql.SQLException;
import java.util.List;

import com.etone.mantoeye.analyse.domain.AnalyseParam;

/**
 * @author Wu Zhenzhen
 * @version Nov 15, 2012 4:48:44 PM
 */
public interface IHiTechFairElexconDataMananger {

	public List<String> getHiTechFairElexconDataList(AnalyseParam param)
			throws SQLException;
}
