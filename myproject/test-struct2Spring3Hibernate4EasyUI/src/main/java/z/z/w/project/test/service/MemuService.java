/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.MemuService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 上午11:54:36 
 *   LastChange: 2013-11-3 上午11:54:36 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.common.BeanUtils;
import z.z.w.project.test.dao.MemuDao;
import z.z.w.project.test.model.TbMemu;
import z.z.w.project.test.model.view.TreeMemu;
import z.z.w.project.util.common.DataTools;

/**
 * z.z.w.project.test.service.MemuService.java
 */
@Service
public class MemuService {

	private MemuDao<TbMemu> memuDao;

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-3 下午04:20:33
	 * 
	 * @return
	 */
	public List<TreeMemu> getTreeNode(String id) {
		String hql = "from TbMemu where tbMemu is null";
		Map<String, Object> params = new HashMap<String, Object>();
		if (!DataTools.isEmpty(id)) {
			hql = "from TbMemu where tbMemu.id = :id ";
			params.put("id", id);
		}

		List<TbMemu> list = memuDao.findData(hql, params);
		List<TreeMemu> vList = new ArrayList<TreeMemu>();
		if (!DataTools.isEmpty(list))
			for (TbMemu t : list) {
				TreeMemu vm = new TreeMemu();
				BeanUtils.copyProperties(t, vm);

				if (!DataTools.isEmpty(t.getTbMemus()))
					vm.setState("closed");// 节点以文件夹的形式体现
				else
					vm.setState("open");// 节点以文件的形式体现

				setAttributes(t, vm);

				vList.add(vm);
			}

		return vList;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:57:21
	 * 
	 * @param t
	 * @param vm
	 */
	private void setAttributes(TbMemu t, TreeMemu vm) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("intMonth", t.getIntMonth());
		attributes.put("intYear", t.getIntYear());
		attributes.put("vcUrl", t.getVcUrl());

		vm.setAttributes(attributes);
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午04:22:34
	 * 
	 * @return the memuDao
	 */
	public MemuDao<TbMemu> getMemuDao() {
		return memuDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午04:22:34
	 * 
	 * @param memuDao
	 *            the memuDao to set
	 */
	@Resource
	public void setMemuDao(MemuDao<TbMemu> memuDao) {
		this.memuDao = memuDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午09:56:20
	 * 
	 * @return
	 */
	public List<TreeMemu> getAllTreeNodes() {
		String hql = "from TbMemu";
		List<TbMemu> list = memuDao.findData(hql, null);
		List<TreeMemu> vList = new ArrayList<TreeMemu>();
		if (!DataTools.isEmpty(list))
			for (TbMemu t : list) {
				TreeMemu vm = new TreeMemu();
				BeanUtils.copyProperties(t, vm);
				TbMemu st = t.getTbMemu();
				if (!DataTools.isEmpty(st))
					vm.setPid(st.getId());

				setAttributes(t, vm);
				vList.add(vm);
			}

		return vList;
	}

}
