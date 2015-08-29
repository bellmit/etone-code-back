/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.SubSpendingService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-8 下午09:34:03 
 *   LastChange: 2013-11-8 下午09:34:03 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.SubSpendingDao;
import z.z.w.project.test.model.TbSubSpending;
import z.z.w.project.test.model.VeiwRSS;

/**
 * z.z.w.project.test.service.SubSpendingService.java
 */
@Service
public class SubSpendingService {
	private SubSpendingDao<TbSubSpending> subSpendingDao;

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-8 下午09:35:35
	 * 
	 * @param tbSubSpending
	 */
	public void addSubSpending(TbSubSpending tbSubSpending) {
		subSpendingDao.saveOrUpdate(tbSubSpending);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-8 下午09:54:19
	 * 
	 * @param id
	 * @return
	 */
	public TbSubSpending get(Serializable id) {
		return subSpendingDao.get(TbSubSpending.class, id);
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:34:31
	 * 
	 * @return the subSpendingDao
	 */
	public SubSpendingDao<TbSubSpending> getSubSpendingDao() {
		return subSpendingDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:34:31
	 * 
	 * @param subSpendingDao
	 *            the subSpendingDao to set
	 */
	@Resource
	public void setSubSpendingDao(SubSpendingDao<TbSubSpending> subSpendingDao) {
		this.subSpendingDao = subSpendingDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午07:39:27
	 * 
	 * @param tss
	 */
	public void delete(TbSubSpending tss) {
		subSpendingDao.delete(tss);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午07:50:53
	 * 
	 * @param tss
	 */
	public void update(TbSubSpending tss) {
		subSpendingDao.update(tss);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:47:59
	 * 
	 * @param ss
	 * @return
	 */
	public TbSubSpending getByNote(String note) {
		String hql = "from TbSubSpending where vcNote = :vcNote";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vcNote", note);
		return subSpendingDao.get(hql, paramMap);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:48:52
	 * 
	 * @param tss
	 */
	public void save(TbSubSpending tss) {
		subSpendingDao.saveOrUpdate(tss);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午10:10:56
	 * 
	 * @param v
	 * @return
	 */
	public TbSubSpending getByJoin(VeiwRSS v) {
		String hql = "from TbSubSpending tss where tss.vcNote = :tssNote and tss.tbSpending.vcNote = :tsNote and tss.tbSpending.tbReveExpen.vcNote = :trNote";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tssNote", v.getVcSSNote());
		paramMap.put("tsNote", v.getVcSNote());
		paramMap.put("trNote", v.getVcRNote());
		return subSpendingDao.get(hql, paramMap);
	}

}
