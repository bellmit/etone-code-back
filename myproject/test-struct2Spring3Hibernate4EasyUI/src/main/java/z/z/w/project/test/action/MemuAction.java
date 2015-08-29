/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.action.MemuAction.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 上午11:53:55 
 *   LastChange: 2013-11-3 上午11:53:55 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import z.z.w.project.test.model.view.TreeMemu;
import z.z.w.project.test.service.MemuService;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.action.MemuAction.java
 */
@Action
public class MemuAction extends BaseAction<TreeMemu> {

	/**
	 * <br>
	 * Created on: 2013-11-3 下午03:30:05
	 */
	private static final long serialVersionUID = -7432003942919640696L;
	private MemuService memuService;
	private TreeMemu treeMemu = new TreeMemu();

	/**
	 * 異步加載返回，適用於菜單較大的情況 <br>
	 * Created on: 2013-11-3 下午04:20:11
	 */
	public void getTreeNode() {
		LogTools.getLogger(getClass()).info("Get memu tree list -- {}...",
				treeMemu);
		writeJson(memuService.getTreeNode(treeMemu.getId()));
	}

	/**
	 * 平布加載返回，適用於數據較少的情況 <br>
	 * Created on: 2013-11-3 下午09:55:08
	 */
	public void getAllTreeNodes() {
		LogTools.getLogger(getClass()).info("Get memu tree all list ...");
		writeJson(memuService.getAllTreeNodes());
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 上午11:54:55
	 * 
	 * @return the memuService
	 */
	public MemuService getMemuService() {
		return memuService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 上午11:54:56
	 * 
	 * @param memuService
	 *            the memuService to set
	 */
	@Resource
	public void setMemuService(MemuService memuService) {
		this.memuService = memuService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.project.test.action.BaseAction#getModel()
	 */
	@Override
	public TreeMemu getModel() {
		return treeMemu;
	}
}
