/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.action.SystemDataAction.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-4 下午04:13:03 
 *   LastChange: 2013-11-4 下午04:13:03 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import z.z.w.project.test.common.BeanUtils;
import z.z.w.project.test.model.SystemData;
import z.z.w.project.test.model.view.DataGrid;
import z.z.w.project.test.model.view.TreeGridSystemData;
import z.z.w.project.test.service.SystemDataService;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.action.SystemDataAction.java
 */
@Action
public class SystemDataAction extends BaseAction<TreeGridSystemData> {

	/**
	 * <br>
	 * Created on: 2013-11-4 下午04:16:35
	 */
	private static final long serialVersionUID = 3014902049180323954L;

	private SystemDataService systemDataService;

	private TreeGridSystemData treeGridSystemData = new TreeGridSystemData();

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-4 下午04:16:52
	 */
	public void query() {
		List<SystemData> list = systemDataService.queryList();
		List<TreeGridSystemData> tList = new ArrayList<TreeGridSystemData>();

		if (!DataTools.isEmpty(list))
			for (SystemData sd : list) {
				TreeGridSystemData tsd = new TreeGridSystemData();
				BeanUtils.copyNotNullProperties(sd, tsd);
				tList.add(tsd);
			}

		LogTools.getLogger(getClass()).debug("--{}....{}--",
				treeGridSystemData.getRows(), treeGridSystemData.getPage());
		int size = treeGridSystemData.getRows() * treeGridSystemData.getPage();
		if (size > tList.size())
			size = tList.size();

		DataGrid<TreeGridSystemData> dg = new DataGrid<TreeGridSystemData>();
		dg.setTotal(tList.size());
		dg.setRows(tList.subList(treeGridSystemData.getPage(), size));
		super.writeJson(dg);

	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午04:21:48
	 * 
	 * @return the systemDataService
	 */
	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午04:21:48
	 * 
	 * @param systemDataService
	 *            the systemDataService to set
	 */
	@Resource
	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.project.test.action.BaseAction#getModel()
	 */
	@Override
	public TreeGridSystemData getModel() {
		return treeGridSystemData;
	}

}
