package com.symbol.app.mantoeye.service.quartz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.common.DimensionDAO;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class DimensionService {

	private static final Log logger = LogFactory.getLog(DimensionService.class);

	@Autowired
	private DimensionDAO dimensionDAO;

	/**
	 * 初始化维表数据，每次启动Tomcat时运行，同时通过Spring配置的定时器定时运行此方法。
	 */
	@Transactional(readOnly = true)
	public void initDimensions() {
		logger.info("**开始查询维表数据！**");
		try {
			logger.info(new Date());

			logger.info("**查询维表数据成功！**");
		} catch (Exception e) {
			logger.warn("**查询维表数据失败！**");
			logger.error(e.getMessage());
		}
	}

}
