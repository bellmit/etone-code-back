/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.SpendingService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-9 下午02:23:02 
 *   LastChange: 2013-11-9 下午02:23:02 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.SpendingDao;
import z.z.w.project.test.model.TbSpending;
import z.z.w.project.test.model.VeiwRSS;

/**
 * z.z.w.project.test.service.SpendingService.java
 */
@Service
public class SpendingService {

	private SpendingDao<TbSpending> spendingDao;

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-9 下午02:24:46
	 * 
	 * @param ts
	 */
	public void addSpending(TbSpending ts) {
		spendingDao.saveOrUpdate(ts);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午02:24:01
	 * 
	 * @return the spendingDao
	 */
	public SpendingDao<TbSpending> getSpendingDao() {
		return spendingDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午02:24:01
	 * 
	 * @param spendingDao
	 *            the spendingDao to set
	 */
	@Resource
	public void setSpendingDao(SpendingDao<TbSpending> spendingDao) {
		this.spendingDao = spendingDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午03:00:08
	 * 
	 * @param l
	 * @return
	 */
	public TbSpending get(Serializable id) {
		return spendingDao.get(TbSpending.class, id);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午07:45:55
	 * 
	 * @param ts
	 */
	public void delete(TbSpending ts) {
		spendingDao.delete(ts);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午07:47:38
	 * 
	 * @param ts
	 */
	public void update(TbSpending ts) {
		spendingDao.update(ts);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:45:49
	 * 
	 * @param s
	 * @return
	 */
	public TbSpending getByNote(String note) {
		String hql = "from TbSpending where vcNote = :vcNote";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vcNote", note);
		return spendingDao.get(hql, paramMap);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午10:02:06
	 * 
	 * @param ts
	 */
	public void save(TbSpending ts) {
		spendingDao.saveOrUpdate(ts);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午10:18:19
	 * 
	 * @param v
	 * @return
	 */
	public TbSpending getByJoin(VeiwRSS v) {
		String hql = "from TbSpending tr where tr.vcNote = :tsNote and tr.tbReveExpen.vcNote = :trNote";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tsNote", v.getVcSNote());
		paramMap.put("trNote", v.getVcRNote());
		return spendingDao.get(hql, paramMap);
	}
}
