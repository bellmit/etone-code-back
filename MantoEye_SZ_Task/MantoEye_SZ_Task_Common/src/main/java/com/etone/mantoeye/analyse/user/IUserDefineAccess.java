/**
 * 用戶自定義實現功能接口
 */
package com.etone.mantoeye.analyse.user;

import java.sql.SQLException;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.domain.AnalyseParam;

/**
 * 用戶自定義實現功能接口
 * 
 * @author Wu Zhenzhen
 * @version 4.0 2011年12月22日10:02:30
 */
public interface IUserDefineAccess {

	static final Logger logger = LoggerFactory
			.getLogger(IUserDefineAccess.class);

	/**
	 * 用戶自定義功能執行方法
	 * 
	 * @param param
	 * @throws SQLException
	 */
	public void execute(AnalyseParam analyseParam) throws SQLException;

}
