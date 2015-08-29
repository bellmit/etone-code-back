/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.InitDbService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 下午12:10:48 
 *   LastChange: 2013-11-3 下午12:10:48 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.InitDbDao;
import z.z.w.project.test.model.TbMemu;
import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.service.InitDbService.java
 */
@Service
public class InitDbService {

	private InitDbDao<TbMemu> initDbDao;

	private static final Date date = DateTools.getCurrentDate();

	/**
	 * <br>
	 * Created on: 2013-11-3 下午12:11:03
	 */
	public synchronized void initCheck() {
		TbMemu tbMemu = initCheckRoot();
		tbMemu = initCheckYear(tbMemu);
		updateDbData(tbMemu);
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午04:04:29
	 * 
	 * @param rootTbMemu
	 * @return
	 */
	private TbMemu initCheckYear(TbMemu rootTbMemu) {

		Map<String, Object> paramMap = setParamMap(date);

		String dataDate = DateTools.getParseDateToStr(date, DateTools.YYYY);

		TbMemu tbMemu = new TbMemu();
		tbMemu.setId(dataDate);
		tbMemu.setTbMemu(rootTbMemu);
		tbMemu.setVcTitle(dataDate + "年賬單");
		tbMemu.setIntYear(NumberUtils.toInt(
				String.valueOf(paramMap.get("intYear")), DateTools.getMonth()));

		initDbDao.saveOrUpdateDB(tbMemu);

		return tbMemu;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午03:43:35
	 */
	private TbMemu initCheckRoot() {
		TbMemu tbMemu = new TbMemu();
		tbMemu.setId("root");
		tbMemu.setVcTitle("賬單");
		tbMemu.setVcUrl("");

		initDbDao.saveOrUpdateDB(tbMemu);

		return tbMemu;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:03:01
	 * 
	 * @param paramMap
	 */
	private void updateDbData(TbMemu rootTbMemu) {

		Date date = DateTools.getCurrentDate();

		Map<String, Object> paramMap = setParamMap(date);

		String dataDate = DateTools.getParseDateToStr(date, DateTools.YYYYMM);

		LogTools.getLogger(getClass()).info(
				"Current db hasn't [{}] data, update db ...", dataDate);

		TbMemu tbMemu = new TbMemu();
		tbMemu.setId(dataDate);
		tbMemu.setTbMemu(rootTbMemu);
		tbMemu.setVcTitle(dataDate + "月賬單");
		tbMemu.setIntMonth(NumberUtils.toInt(
				String.valueOf(paramMap.get("intMonth")), DateTools.getMonth()));
		tbMemu.setIntYear(NumberUtils.toInt(
				String.valueOf(paramMap.get("intYear")), DateTools.getMonth()));
		tbMemu.setVcUrl("/system/systemlist.jsp");

		initDbDao.saveOrUpdateDB(tbMemu);
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:02:10
	 * 
	 * @return
	 */
	private Map<String, Object> setParamMap(Date date) {
		int intYear = DateTools.getYear(date);
		int intMonth = DateTools.getMonth(date);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("intYear", intYear);
		paramMap.put("intMonth", intMonth);
		return paramMap;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:25:08
	 * 
	 * @return the initDbDao
	 */
	public InitDbDao<TbMemu> getInitDbDao() {
		return initDbDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:25:08
	 * 
	 * @param initDbDao
	 *            the initDbDao to set
	 */
	@Resource
	public void setInitDbDao(InitDbDao<TbMemu> initDbDao) {
		this.initDbDao = initDbDao;
	}

}
