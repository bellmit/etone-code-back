/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.ReveExpenService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-9 下午09:03:43 
 *   LastChange: 2013-11-9 下午09:03:43 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.ReveExpenDao;
import z.z.w.project.test.model.TbReveExpen;

/**
 * z.z.w.project.test.service.ReveExpenService.java
 */
@Service
public class ReveExpenService {

	private ReveExpenDao<TbReveExpen> reveExpenDao;

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:04:08
	 * 
	 * @return the reveExpenDao
	 */
	public ReveExpenDao<TbReveExpen> getReveExpenDao() {
		return reveExpenDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:04:08
	 * 
	 * @param reveExpenDao
	 *            the reveExpenDao to set
	 */
	@Resource
	public void setReveExpenDao(ReveExpenDao<TbReveExpen> reveExpenDao) {
		this.reveExpenDao = reveExpenDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:42:21
	 * 
	 * @param r
	 * @return
	 */
	public TbReveExpen getByNote(String note) {
		String hql = "from TbReveExpen where vcNote = :vcNote";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vcNote", note);
		return reveExpenDao.get(hql, paramMap);
	}

}
