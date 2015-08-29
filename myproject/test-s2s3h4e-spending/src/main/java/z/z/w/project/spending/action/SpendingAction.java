/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.action.SpendingAction.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午04:39:04 
 *   LastChange: 2013-11-12 下午04:39:04 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.action;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import z.z.w.project.spending.common.BeanUtils;
import z.z.w.project.spending.common.HqlFilter;
import z.z.w.project.spending.common.Json;
import z.z.w.project.spending.model.RSSSVo;
import z.z.w.project.spending.model.TbDielectric;
import z.z.w.project.spending.model.TbReveExpen;
import z.z.w.project.spending.model.TbSpending;
import z.z.w.project.spending.model.TbSubSpending;
import z.z.w.project.spending.model.view.BasicDataGrid;
import z.z.w.project.spending.model.view.ViewDataGridSpending;
import z.z.w.project.spending.service.SpendingService;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.spending.action.SpendingAction.java
 */
public class SpendingAction extends BaseAction<ViewDataGridSpending> {

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:56:33
	 */
	private static final long serialVersionUID = 8654744342899521474L;

	private SpendingService spendingService;
	private ViewDataGridSpending viewDataGridSpending = new ViewDataGridSpending();

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-20 下午04:12:29
	 */
	public void subSpendList() {
		LogTools.getLogger(getClass()).info("Enter subSpend list ...");

		try {
			List<TbSubSpending> list = spendingService.querySubSpendingList();
			// List<ViewDataGridSpending> tList = new ArrayList<ViewDataGridSpending>();
			// if (!DataTools.isEmpty(list)) {
			// for (TbSubSpending tss : list) {
			// ViewDataGridSpending vdgs = new ViewDataGridSpending();
			// BeanUtils.copyNotNullProperties(tss, vdgs);
			// tList.add(vdgs);
			// }
			// }

			writeJson(list);
		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "rssslList()", e.getMessage(), e.getCause(), e.getClass() });
			Json json = new Json();
			json.setMsg("加載失敗！" + e.getMessage());
			writeJson(json);
		}

	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-20 下午04:11:46
	 */
	public void spendList() {
		LogTools.getLogger(getClass()).info("Enter spend list ...");

		try {
			List<TbSpending> list = spendingService.querySpendingList();
			// List<ViewDataGridSpending> tList = new ArrayList<ViewDataGridSpending>();
			// if (!DataTools.isEmpty(list)) {
			// for (TbSpending tss : list) {
			// ViewDataGridSpending vdgs = new ViewDataGridSpending();
			// BeanUtils.copyNotNullProperties(tss, vdgs);
			// tList.add(vdgs);
			// }
			// }

			writeJson(list);
		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "rssslList()", e.getMessage(), e.getCause(), e.getClass() });
			Json json = new Json();
			json.setMsg("加載失敗！" + e.getMessage());
			writeJson(json);
		}

	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-20 下午04:10:16
	 */
	public void reveExpenList() {
		LogTools.getLogger(getClass()).info("Enter reveExpen list ...");

		try {

			List<TbReveExpen> list = spendingService.queryReveExpenList();

			// List<ViewDataGridSpending> tList = new ArrayList<ViewDataGridSpending>();
			// if (!DataTools.isEmpty(list)) {
			// for (TbReveExpen tss : list) {
			// ViewDataGridSpending vdgs = new ViewDataGridSpending();
			// BeanUtils.copyNotNullProperties(tss, vdgs);
			// tList.add(vdgs);
			// }
			// }

			writeJson(list);

		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "rssslList()", e.getMessage(), e.getCause(), e.getClass() });
			Json json = new Json();
			json.setMsg("加載失敗！" + e.getMessage());
			writeJson(json);
		}

	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-18 下午04:02:54
	 */
	public void editRSSS() {
		Json json = new Json();

		try {
			spendingService.editRsss(viewDataGridSpending);

			json.setSuccess(true);
			json.setMsg("修改成功！");
		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "editRSSS()", e.getMessage(), e.getCause(), e.getClass() });
			json.setMsg("修改失敗！" + e.getMessage());
		}
		writeJson(json);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-18 下午04:02:33
	 */
	public void delRSSS() {
		Json json = new Json();

		try {

			HqlFilter hqlFilter = new HqlFilter(getRequest());
			spendingService.deleteRsssByIds(hqlFilter);

			json.setSuccess(true);
			json.setMsg("删除成功！");
		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "delRSSS()", e.getMessage(), e.getCause(), e.getClass() });
			json.setMsg("删除失敗！" + e.getMessage());
		}

		writeJson(json);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-17 下午10:14:30
	 */
	public void rssslList() {
		LogTools.getLogger(getClass()).info("Enter rsss list ...");
		BasicDataGrid<ViewDataGridSpending> dg = new BasicDataGrid<ViewDataGridSpending>();
		try {

			long spendLen = spendingService.getSpendingCount();
			if (spendLen != 0) {
				HqlFilter hqlFilter = new HqlFilter();
				setOrderSort(hqlFilter);

				List<RSSSVo> list = spendingService.queryRsssListByPage(hqlFilter, viewDataGridSpending.getPage(), viewDataGridSpending.getRows());

				List<ViewDataGridSpending> tList = new ArrayList<ViewDataGridSpending>();
				if (!DataTools.isEmpty(list)) {
					for (RSSSVo tss : list) {

						if (DataTools.isEmpty(tss.getVcSNote()) && DataTools.isEmpty(tss.getVcSSNote()))
							continue;
						if (DataTools.isEmpty(tss.getPositiveOrNegative()))
							throw new NullPointerException("The ReveExpen info is not NULL!");

						ViewDataGridSpending vdgs = new ViewDataGridSpending();
						BeanUtils.copyNotNullProperties(tss, vdgs);
						vdgs.setRsssId(UUID.randomUUID().toString());

						tList.add(vdgs);
					}
				}

				dg.setTotal(spendingService.getRssListCount(hqlFilter));
				dg.setRows(tList);
			}
			dg.setSuccess(true);

		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "rssslList()", e.getMessage(), e.getCause(), e.getClass() });
			dg.setMsg("加載數據錯誤！" + e.getMessage());
		}
		super.writeJson(dg);
	}

	/**
	 * <br>
	 * Created on: 2013-11-21 上午10:48:51
	 * 
	 * @param hqlFilter
	 */
	private void setOrderSort(HqlFilter hqlFilter) {
		/**
		 * order asc,desc page 1 rows 100 sort vcRNote,vcSNote
		 */
		String sort = viewDataGridSpending.getSort();
		String order = viewDataGridSpending.getOrder();
		if (DataTools.isEmpty(sort))
			return;
		String[] sortArr = sort.split(",", -1);
		int len = sortArr.length;
		if (!DataTools.isEmpty(sortArr) && len != 1) {
			String[] orderArr = order.split(",", -1);
			String orderSort = "";
			for (int i = 0; i < len; i++) {
				orderSort += getSort(sortArr[i]) + " " + orderArr[i] + ",";
			}
			orderSort = orderSort.substring(0, orderSort.length() - 1);
			hqlFilter.addSort("");
			hqlFilter.addOrder(orderSort);
		} else {
			hqlFilter.addSort(getSort(sort));
			hqlFilter.addOrder(order);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 上午11:19:21
	 * 
	 * @param sort
	 * @return
	 */
	private String getSort(String sort) {
		if (sort.equalsIgnoreCase("vcRNote"))
			return "tr.vcNote";
		else if (sort.equalsIgnoreCase("vcSNote"))
			return "ts.vcNote";
		else
			return "tss.vcNote";
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-17 下午09:25:20
	 */
	public void addRSSS() {
		Json json = new Json();

		try {

			spendingService.addRSSS(viewDataGridSpending);

			json.setSuccess(true);
			json.setMsg("保存成功！");
		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "addRSSS()", e.getMessage(), e.getCause(), e.getClass() });
			json.setMsg("保存失敗！" + e.getMessage());
		}
		writeJson(json);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-17 下午09:24:19
	 */
	public void editBalances() {
		Json json = new Json();

		try {

			spendingService.editBalances(viewDataGridSpending);

			json.setSuccess(true);
			json.setMsg("修改成功！");
		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "editBalances()", e.getMessage(), e.getCause(), e.getClass() });
			json.setMsg("修改失敗！" + e.getMessage());
		}
		writeJson(json);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-17 下午08:43:35
	 */
	public void addBalances() {
		Json json = new Json();

		try {

			spendingService.addBalances(viewDataGridSpending);

			json.setSuccess(true);
			json.setMsg("保存成功！");
		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "addBalances()", e.getMessage(), e.getCause(), e.getClass() });
			json.setMsg("保存失敗！" + e.getMessage());
		}
		writeJson(json);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-17 下午08:42:30
	 */
	public void delBalances() {
		Json json = new Json();

		try {

			HqlFilter hqlFilter = new HqlFilter(getRequest());
			spendingService.deleteDataByIds(hqlFilter);

			json.setSuccess(true);
			json.setMsg("删除成功！");
		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "delBalances()", e.getMessage(), e.getCause(), e.getClass() });
			json.setMsg("删除失敗！" + e.getMessage());
		}

		writeJson(json);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-12 下午05:06:44
	 */
	public void dielList() {
		LogTools.getLogger(getClass()).info("Enter dielectric list ...");
		BasicDataGrid<ViewDataGridSpending> dg = new BasicDataGrid<ViewDataGridSpending>();
		try {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addOrder(viewDataGridSpending.getOrder());
			hqlFilter.addSort(viewDataGridSpending.getSort());

			List<TbDielectric> list = spendingService.queryDielectricListByPage(hqlFilter, viewDataGridSpending.getPage(), viewDataGridSpending.getRows());

			List<ViewDataGridSpending> tList = new ArrayList<ViewDataGridSpending>();
			if (!DataTools.isEmpty(list))
				for (TbDielectric sd : list) {
					ViewDataGridSpending tsd = new ViewDataGridSpending();
					BeanUtils.copyNotNullProperties(sd, tsd);
					tList.add(tsd);
				}

			dg.setTotal(spendingService.getDielectricListCount(hqlFilter));
			dg.setRows(tList);
			dg.setSuccess(true);

		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "SpendingAction", "dielList()", e.getMessage(), e.getCause(), e.getClass() });
			dg.setMsg("加載數據錯誤！" + e.getMessage());
		}
		super.writeJson(dg);
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午05:02:16
	 * 
	 * @return the spendingService
	 */
	public SpendingService getSpendingService() {
		return spendingService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午05:02:16
	 * 
	 * @param spendingService
	 *            the spendingService to set
	 */
	@Resource
	public void setSpendingService(SpendingService spendingService) {
		this.spendingService = spendingService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.project.spending.action.BaseAction#getModel()
	 */
	@Override
	public ViewDataGridSpending getModel() {
		return viewDataGridSpending;
	}

}
