/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.dao.InitDbDao.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 下午12:11:29 
 *   LastChange: 2013-11-3 下午12:11:29 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.dao;

import org.springframework.stereotype.Repository;

/**
 * z.z.w.project.test.dao.InitDbDao.java
 */
@Repository
public class InitDbDao<TbMemu> extends BaseDao<TbMemu> {

	/**
	 * <br>
	 * Created on: 2013-11-3 下午02:36:52
	 */
	private static final long serialVersionUID = -7092922667878648638L;

	/**
	 * <br>
	 * Created on: 2013-11-3 下午12:50:45
	 * 
	 * @param paramMap
	 */
	public void saveOrUpdateDB(TbMemu t) {
		saveOrUpdate(t);
	}

}
