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
package z.z.w.project.spending.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.spending.common.BeanUtils;
import z.z.w.project.spending.dao.ResourceRoteDao;
import z.z.w.project.spending.model.TbResourceRote;
import z.z.w.project.spending.model.view.ViewTreeResourceRote;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;

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
	public void setResourceRoteDao(ResourceRoteDao<TbResourceRote> resourceRoteDao) {
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

	/**
	 * <br>
	 * Created on: 2013-11-12 下午02:29:18
	 */
	public void initResourceRote() {

		LogTools.getLogger(getClass()).info("Init resource data ...");

		setSpendingList();

		setSpendingSystem();
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午03:39:40
	 */
	private void setSpendingSystem() {
		String vcNote = "系統管理";
		TbResourceRote rote = getRoteByNote(vcNote, true);

		if (DataTools.isEmpty(rote)) {
			rote = new TbResourceRote();
			rote.setVcNote(vcNote);
		}

		vcNote = "收支項目";
		TbResourceRote subRote = getRoteByNote(vcNote, false);
		if (DataTools.isEmpty(subRote)) {
			subRote = new TbResourceRote();
			subRote.setVcNote(vcNote);
		}

		String vcRotePage = "/system/spending.jsp";
		subRote.setVcNote(vcNote);
		subRote.setVcRotePage(vcRotePage);
		subRote.setTbResourceRote(rote);

		addResourceRote(subRote);

		vcNote = "錢包管理";
		subRote = getRoteByNote(vcNote, false);
		if (DataTools.isEmpty(subRote)) {
			subRote = new TbResourceRote();
			subRote.setVcNote(vcNote);
		}

		vcRotePage = "/system/dielectric.jsp";
		subRote.setVcNote(vcNote);
		subRote.setVcRotePage(vcRotePage);
		subRote.setTbResourceRote(rote);

		addResourceRote(subRote);
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午03:39:25
	 */
	private void setSpendingList() {
		String vcNote = "賬單列表";
		TbResourceRote rote = getRoteByNote(vcNote, true);

		if (DataTools.isEmpty(rote)) {
			rote = new TbResourceRote();
			rote.setVcNote(vcNote);
		}

		vcNote = DateTools.getParseDateToStr(DateTools.getCurrentDate(), DateTools.YYYY_MM);

		TbResourceRote subRote = getRoteByNote(vcNote, false);
		if (DataTools.isEmpty(subRote)) {
			subRote = new TbResourceRote();
			subRote.setVcNote(vcNote);
		}

		String vcRotePage = getVcRotePage();
		subRote.setVcNote(vcNote);
		subRote.setVcRotePage(vcRotePage);
		subRote.setTbResourceRote(rote);

		addResourceRote(subRote);
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午03:28:17
	 * 
	 * @param vcNote
	 * @param pidIsNull
	 * @return
	 */
	private TbResourceRote getRoteByNote(String vcNote, boolean pidIsNull) {
		String hql = "from TbResourceRote where vcNote =:vcNote and tbResourceRote ";
		hql += (pidIsNull ? " is null" : " is not null");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vcNote", vcNote);
		return resourceRoteDao.get(hql, paramMap);
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午02:58:56
	 * 
	 * @return
	 */
	private String getVcRotePage() {
		return "/system/spendingList.jsp?intYear=" + DateTools.getYear() + "&intMonth=" + DateTools.getMonth();
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午03:53:46
	 * 
	 * @return
	 */
	public List<ViewTreeResourceRote> getAllTreeNodes() {
		String hql = "from TbResourceRote";
		List<TbResourceRote> list = resourceRoteDao.find(hql, null);
		List<ViewTreeResourceRote> vList = new ArrayList<ViewTreeResourceRote>();
		if (!DataTools.isEmpty(list))
			for (TbResourceRote t : list) {
				ViewTreeResourceRote vm = new ViewTreeResourceRote();
				BeanUtils.copyProperties(t, vm);
				vm.setId(t.getNmResourceRoteid());
				TbResourceRote st = t.getTbResourceRote();
				if (!DataTools.isEmpty(st))
					vm.setIntPRRId(st.getNmResourceRoteid());

				setAttributes(t, vm);
				vList.add(vm);
			}

		return vList;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午03:57:16
	 * 
	 * @param t
	 * @param vm
	 */
	private void setAttributes(TbResourceRote t, ViewTreeResourceRote vm) {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("vcUrl", t.getVcRotePage());

		vm.setAttributes(attributes);
	}

}
