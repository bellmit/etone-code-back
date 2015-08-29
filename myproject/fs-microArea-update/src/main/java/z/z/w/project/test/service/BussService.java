/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.BussService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2014-1-24 下午04:56:59 
 *   LastChange: 2014-1-24 下午04:56:59 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.IBussDao;
import z.z.w.project.test.vo.TbCgiInfo;

/**
 * z.z.w.project.test.service.BussService.java
 */
@Service
public class BussService {
	private IBussDao iBussDao;

	/**
	 * <br>
	 * Created on: 2014-1-24 下午04:56:59
	 */
	public BussService() {
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:18:59
	 * 
	 * @return the iBussDao
	 */
	public IBussDao getiBussDao() {
		return iBussDao;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:18:59
	 * 
	 * @param iBussDao
	 *            the iBussDao to set
	 */
	@Resource
	public void setiBussDao(IBussDao iBussDao) {
		this.iBussDao = iBussDao;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:18:03
	 * 
	 * @return
	 */
	public List<TbCgiInfo> getCgiInfoList() {
		return iBussDao.getCgiInfoList();
	}

}
