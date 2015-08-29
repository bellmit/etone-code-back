/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.dao.IBussDao.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2014-1-24 下午05:18:40 
 *   LastChange: 2014-1-24 下午05:18:40 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.dao;

import java.util.List;

import z.z.w.project.test.vo.TbCgiInfo;

/**
 * z.z.w.project.test.dao.IBussDao.java
 */
public interface IBussDao {

	/**
	 *<br> Created on: 2014-1-24 下午05:19:28 
	 * @return
	 */
	List<TbCgiInfo> getCgiInfoList();

}
