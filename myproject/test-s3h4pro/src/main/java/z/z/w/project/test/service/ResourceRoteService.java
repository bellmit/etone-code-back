/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.ResourceRoteService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 上午11:30:57 
 *   LastChange: 2013-11-12 上午11:30:57 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.ResourceRoteDao;
import z.z.w.project.test.model.TbResourceRote;

/**
 * z.z.w.project.test.service.ResourceRoteService.java
 */
@Service
public class ResourceRoteService {

	private ResourceRoteDao<TbResourceRote> resourceRoteDao;

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:31:42
	 * 
	 * @return the resourceRoteDao
	 */
	public ResourceRoteDao<TbResourceRote> getResourceRoteDao() {
		return resourceRoteDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:31:42
	 * 
	 * @param resourceRoteDao
	 *            the resourceRoteDao to set
	 */
	@Resource
	public void setResourceRoteDao(
			ResourceRoteDao<TbResourceRote> resourceRoteDao) {
		this.resourceRoteDao = resourceRoteDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:59:27
	 * 
	 * @param rote
	 */
	public void addResourceRote(TbResourceRote rote) {
		resourceRoteDao.saveOrUpdate(rote);
	}

}
