/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.action.ResourceRoteAction.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午03:47:45 
 *   LastChange: 2013-11-12 下午03:47:45 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import z.z.w.project.spending.model.view.ViewTreeResourceRote;
import z.z.w.project.spending.service.ResourceRoteService;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.spending.action.ResourceRoteAction.java
 */
@Action
public class ResourceRoteAction extends BaseAction<ViewTreeResourceRote> {

	/**
	 * <br>
	 * Created on: 2013-11-12 下午03:51:06
	 */
	private static final long serialVersionUID = -1926501562486778971L;
	private ResourceRoteService resourceRoteService;

	/**
	 * 平布加載返回，適用於數據較少的情況 <br>
	 * Created on: 2013-11-3 下午09:55:08
	 */
	public void getAllTreeNodes() {
		LogTools.getLogger(getClass()).info("Get memu tree all list ...");
		writeJson(resourceRoteService.getAllTreeNodes());
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午03:50:49
	 * 
	 * @return the resourceRoteService
	 */
	public ResourceRoteService getResourceRoteService() {
		return resourceRoteService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午03:50:49
	 * 
	 * @param resourceRoteService
	 *            the resourceRoteService to set
	 */
	@Resource
	public void setResourceRoteService(ResourceRoteService resourceRoteService) {
		this.resourceRoteService = resourceRoteService;
	}

}
