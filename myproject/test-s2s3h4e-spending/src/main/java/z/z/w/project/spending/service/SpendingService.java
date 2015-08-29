/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.service.SpendingService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午04:35:02 
 *   LastChange: 2013-11-12 下午04:35:02 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.spending.common.BeanUtils;
import z.z.w.project.spending.common.HqlFilter;
import z.z.w.project.spending.dao.DielectricDao;
import z.z.w.project.spending.dao.RSSSDao;
import z.z.w.project.spending.dao.ReveExpenDao;
import z.z.w.project.spending.dao.SpendingDao;
import z.z.w.project.spending.dao.SubSpendingDao;
import z.z.w.project.spending.model.RSSSVo;
import z.z.w.project.spending.model.TbDielectric;
import z.z.w.project.spending.model.TbReveExpen;
import z.z.w.project.spending.model.TbSpending;
import z.z.w.project.spending.model.TbSubSpending;
import z.z.w.project.spending.model.view.ViewDataGridSpending;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.spending.service.SpendingService.java
 */
@Service
public class SpendingService {

	private DielectricDao<TbDielectric> dielectricDao;
	private ReveExpenDao<TbReveExpen> reveExpenDao;
	private SpendingDao<TbSpending> spendingDao;
	private SubSpendingDao<TbSubSpending> subSpendingDao;
	private RSSSDao<RSSSVo> rSSSDao;

	/**
	 * <br>
	 * Created on: 2013-11-17 下午10:07:56
	 * 
	 * @return the rSSSDao
	 */
	public RSSSDao<RSSSVo> getrSSSDao() {
		return rSSSDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午10:07:56
	 * 
	 * @param rSSSDao
	 *            the rSSSDao to set
	 */
	@Resource
	public void setrSSSDao(RSSSDao<RSSSVo> rSSSDao) {
		this.rSSSDao = rSSSDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:43:27
	 * 
	 * @return the dielectricDao
	 */
	public DielectricDao<TbDielectric> getDielectricDao() {
		return dielectricDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:35:59
	 * 
	 * @return the reveExpenDao
	 */
	public ReveExpenDao<TbReveExpen> getReveExpenDao() {
		return reveExpenDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:35:59
	 * 
	 * @return the spendingDao
	 */
	public SpendingDao<TbSpending> getSpendingDao() {
		return spendingDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:35:59
	 * 
	 * @return the subSpendingDao
	 */
	public SubSpendingDao<TbSubSpending> getSubSpendingDao() {
		return subSpendingDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:43:27
	 * 
	 * @param dielectricDao
	 *            the dielectricDao to set
	 */
	@Resource
	public void setDielectricDao(DielectricDao<TbDielectric> dielectricDao) {
		this.dielectricDao = dielectricDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:35:59
	 * 
	 * @param reveExpenDao
	 *            the reveExpenDao to set
	 */
	@Resource
	public void setReveExpenDao(ReveExpenDao<TbReveExpen> reveExpenDao) {
		this.reveExpenDao = reveExpenDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:35:59
	 * 
	 * @param spendingDao
	 *            the spendingDao to set
	 */
	@Resource
	public void setSpendingDao(SpendingDao<TbSpending> spendingDao) {
		this.spendingDao = spendingDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:35:59
	 * 
	 * @param subSpendingDao
	 *            the subSpendingDao to set
	 */
	@Resource
	public void setSubSpendingDao(SubSpendingDao<TbSubSpending> subSpendingDao) {
		this.subSpendingDao = subSpendingDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:14:59
	 * 
	 * @param hqlFilter
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<TbDielectric> queryDielectricListByPage(HqlFilter hqlFilter, int page, int rows) {
		String hql = "from TbDielectric t" + hqlFilter.getWhereAndOrderHql();
		return dielectricDao.findByPage(hql, hqlFilter.getParams(), page, rows);
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:16:33
	 * 
	 * @param hqlFilter
	 * @return
	 */
	public long getDielectricListCount(HqlFilter hqlFilter) {
		String hql = "select count(*) from TbDielectric t" + hqlFilter.getWhereHql();
		Long count = dielectricDao.count(hql, hqlFilter.getParams());
		if (DataTools.isEmpty(count))
			return 0l;

		return count.longValue();
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 下午03:58:09
	 * 
	 * @param hqlFilter
	 */
	public void deleteDataByIds(HqlFilter hqlFilter) {
		String hql = "delete from TbDielectric t" + hqlFilter.getWhereHql();
		dielectricDao.executeUpdate(hql, hqlFilter.getParams());
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午08:51:00
	 * 
	 * @param viewDataGridSpending
	 */
	public void addBalances(ViewDataGridSpending viewDataGridSpending) {
		TbDielectric td = new TbDielectric();
		BeanUtils.copyNotNullProperties(viewDataGridSpending, td);

		dielectricDao.save(td);
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:00:03
	 * 
	 * @param viewDataGridSpending
	 */
	public void editBalances(ViewDataGridSpending viewDataGridSpending) {
		TbDielectric td = new TbDielectric();
		BeanUtils.copyNotNullProperties(viewDataGridSpending, td);

		dielectricDao.saveOrUpdate(td);
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:25:34
	 * 
	 * @param v
	 */
	public void addRSSS(ViewDataGridSpending v) {

		String r = v.getVcRNote();
		String s = v.getVcSNote();
		String ss = v.getVcSSNote();

		if (DataTools.isEmpty(r))
			throw new NullPointerException("The ReveExpen Note is not NULL!");

		if (DataTools.isEmpty(s))
			throw new NullPointerException("The Spending Note is not NULL!");

		TbSubSpending tss = getByJoinSubSpend(v);

		if (DataTools.isEmpty(tss)) {
			tss = new TbSubSpending();
			tss.setVcNote(ss);

			TbSpending ts = getByJoinSpend(v);
			if (DataTools.isEmpty(ts)) {
				ts = new TbSpending();
				ts.setVcNote(s);
			}

			tss.setTbSpending(ts);

			TbReveExpen tr = getByNoteExpen(r);
			if (DataTools.isEmpty(tr))
				throw new NullPointerException("The ReveExpen info is not modify!");

			ts.setTbReveExpen(tr);

			if (!DataTools.isEmpty(ss))
				subSpendingDao.saveOrUpdate(tss);
			else
				spendingDao.saveOrUpdate(ts);

		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:29:24
	 * 
	 * @param note
	 * @return
	 */
	private TbReveExpen getByNoteExpen(String note) {
		String hql = "from TbReveExpen where vcNote = :vcNote";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vcNote", note);
		return reveExpenDao.get(hql, paramMap);
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:28:28
	 * 
	 * @param v
	 * @return
	 */
	private TbSpending getByJoinSpend(ViewDataGridSpending v) {
		String hql = "from TbSpending tr where tr.vcNote = :tsNote and tr.tbReveExpen.vcNote = :trNote order by tr.vcNote";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tsNote", v.getVcSNote());
		paramMap.put("trNote", v.getVcRNote());
		List<TbSpending> list = spendingDao.find(hql, paramMap);
		if (!DataTools.isEmpty(list))
			return list.get(0);
		return null;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:27:40
	 * 
	 * @param v
	 * @return
	 */
	private TbSubSpending getByJoinSubSpend(ViewDataGridSpending v) {
		String hql = "from TbSubSpending tss where tss.vcNote = :tssNote and tss.tbSpending.vcNote = :tsNote and tss.tbSpending.tbReveExpen.vcNote = :trNote";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tssNote", v.getVcSSNote());
		paramMap.put("tsNote", v.getVcSNote());
		paramMap.put("trNote", v.getVcRNote());
		List<TbSubSpending> list = subSpendingDao.find(hql, paramMap);
		if (!DataTools.isEmpty(list))
			return list.get(0);
		return null;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 上午10:18:24
	 * 
	 * @param hqlFilter
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<RSSSVo> queryRsssListByPage(HqlFilter hqlFilter, int page, int rows) {
		String hql = "select new z.z.w.project.spending.model.RSSSVo(tr.vcNote,ts.vcNote,tss.vcNote,tr.chReveExpenId,(case when ts.nmSpendId is null then -1l else ts.nmSpendId end) as nmSpendId,(case when tss.nmSubSpendId is null then -1l else tss.nmSubSpendId end) as nmSubSpendId,tr.positiveOrNegative) from TbSubSpending tss right join tss.tbSpending ts right join ts.tbReveExpen tr " + hqlFilter.getWhereAndOrderHql();
		return rSSSDao.findByPage(hql, hqlFilter.getParams(), page, rows);
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 上午10:18:29
	 * 
	 * @param hqlFilter
	 * @return
	 */
	public long getRssListCount(HqlFilter hqlFilter) {
		String hql = "select count(*) from TbSubSpending tss right join tss.tbSpending ts right join ts.tbReveExpen tr " + hqlFilter.getWhereHql();
		Long count = rSSSDao.count(hql, hqlFilter.getParams());
		if (DataTools.isEmpty(count))
			return 0l;

		return count.longValue();
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:35:17
	 * 
	 * @param hqlFilter
	 */
	public void deleteRsssByIds(HqlFilter hqlFilter) {
		// 'QUERY_ts#nmSpendId_A|L_IN' : sids.join(','),
		// 'QUERY_tss#nmSubSpendId_A|L_IN' : ssids.join(','),

		String hql = "";
		Map<String, Object> paramMap = hqlFilter.getRequestParams();
		if (!DataTools.isEmpty(paramMap)) {
			String trObj = String.valueOf(paramMap.get("tr#chReveExpenId_A|I_IN"));
			if (!DataTools.isEmpty(trObj)) {
				hqlFilter.addFilter("QUERY_tr#chReveExpenId_A|I_IN", trObj);
				hql = "delete from TbReveExpen tr " + hqlFilter.getWhereHql();
				reveExpenDao.executeUpdate(hql, hqlFilter.getParams());
			}

			hqlFilter.clearParams();

			String tsObj = String.valueOf(paramMap.get("ts#nmSpendId_A|L_IN"));
			if (!DataTools.isEmpty(tsObj)) {
				hqlFilter.addFilter("QUERY_ts#nmSpendId_A|L_IN", tsObj);
				hql = "delete from TbSpending ts " + hqlFilter.getWhereHql();
				spendingDao.executeUpdate(hql, hqlFilter.getParams());
			}

			hqlFilter.clearParams();

			String tssObj = String.valueOf(paramMap.get("tss#nmSubSpendId_A|L_IN"));
			if (!DataTools.isEmpty(tssObj)) {
				hqlFilter.addFilter("QUERY_tss#nmSubSpendId_A|L_IN", tssObj);
				hql = "delete from TbSubSpending tss " + hqlFilter.getWhereHql();
				subSpendingDao.executeUpdate(hql, hqlFilter.getParams());
			}
		}

	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午04:03:06
	 * 
	 * @param viewDataGridSpending
	 */
	public void editRsss(ViewDataGridSpending viewDataGridSpending) {

		String r = viewDataGridSpending.getVcRNote();
		String s = viewDataGridSpending.getVcSNote();
		String ss = viewDataGridSpending.getVcSSNote();

		if (DataTools.isEmpty(r))
			throw new NullPointerException("The ReveExpen Note is not NULL!");

		if (DataTools.isEmpty(s))
			throw new NullPointerException("The Spending Note is not NULL!");

		/***************************************************************************************
		 * update TbReveExpen
		 ***************************************************************************************/
		TbReveExpen tr = getByNoteExpen(r);
		if (DataTools.isEmpty(tr)) {
			tr = reveExpenDao.get(TbReveExpen.class, viewDataGridSpending.getChReveExpenId());
			if (DataTools.isEmpty(tr))
				throw new NullPointerException("The ReveExpen info is not modify!");

			tr.setVcNote(r);
			reveExpenDao.saveOrUpdate(tr);
		}

		/***************************************************************************************
		 * update TbSpending
		 ***************************************************************************************/
		TbSpending ts = getByJoinSpend(viewDataGridSpending);
		if (DataTools.isEmpty(ts)) {
			ts = spendingDao.get(TbSpending.class, viewDataGridSpending.getNmSpendId());
			if (DataTools.isEmpty(ts))
				ts = new TbSpending();
			ts.setVcNote(s);
			ts.setTbReveExpen(tr);
			// spendingDao.saveOrUpdate(ts);
		}

		/***************************************************************************************
		 * update TbSubSpending
		 ***************************************************************************************/
		if (DataTools.isEmpty(ss)) {
			spendingDao.saveOrUpdate(ts);
			deleteOldSubSpending(viewDataGridSpending);
		} else {
			TbSubSpending tss = getByJoinSubSpend(viewDataGridSpending);

			if (!DataTools.isEmpty(tss)) {
				spendingDao.saveOrUpdate(ts);
				deleteOldSubSpending(viewDataGridSpending);
			} else {
				tss = subSpendingDao.get(TbSubSpending.class, viewDataGridSpending.getNmSubSpendId());
				if (!DataTools.isEmpty(tss)) {
					String hql = "delete from TbSubSpending tss where nmSubSpendId = " + tss.getNmSubSpendId();
					subSpendingDao.executeUpdate(hql, null);
				} else
					deleteOldSubSpending(viewDataGridSpending);

				tss = new TbSubSpending();
				tss.setVcNote(ss);
				tss.setTbSpending(ts);
				subSpendingDao.saveOrUpdate(tss);
			}
		}

	}

	/**
	 * <br>
	 * Created on: 2013-11-19 下午04:12:35
	 * 
	 * @param viewDataGridSpending
	 */
	private void deleteOldSubSpending(ViewDataGridSpending viewDataGridSpending) {
		long sid = viewDataGridSpending.getNmSpendId();
		long ssid = viewDataGridSpending.getNmSubSpendId();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		String hql = "";
		if (sid != -1 && ssid != -1) {
			hql = "delete TbSubSpending ts where ts.nmSubSpendId = :nmSubSpendId";
			paramMap.put("nmSubSpendId", ssid);
			subSpendingDao.executeUpdate(hql, paramMap);
		} else if (sid != -1 && ssid == -1) {
			hql = "delete TbSpending ts where ts.nmSpendId = :nmSpendId ";
			paramMap.put("nmSpendId", sid);
			spendingDao.executeUpdate(hql, paramMap);
		} else {
			hql = "delete TbReveExpen ts where ts.chReveExpenId = :chReveExpenId ";
			paramMap.put("chReveExpenId", viewDataGridSpending.getChReveExpenId());
			reveExpenDao.executeUpdate(hql, paramMap);
		}

	}

	/**
	 * <br>
	 * Created on: 2013-11-20 下午03:20:33
	 * 
	 * @return
	 */
	public List<TbReveExpen> queryReveExpenList() {
		return reveExpenDao.find("from TbReveExpen group by vcNote", null);
	}

	/**
	 * <br>
	 * Created on: 2013-11-20 下午04:11:02
	 * 
	 * @return
	 */
	public List<TbSpending> querySpendingList() {
		return spendingDao.find("from TbSpending group by vcNote", null);
	}

	/**
	 * <br>
	 * Created on: 2013-11-20 下午04:12:33
	 * 
	 * @return
	 */
	public List<TbSubSpending> querySubSpendingList() {
		return subSpendingDao.find("from TbSubSpending group by vcNote", null);
	}

	/**
	 * <br>
	 * Created on: 2013-11-22 下午03:43:40
	 */
	public void initReveExpen() {
		LogTools.getLogger(getClass()).info("Init reveExpen data ...");

		TbReveExpen tr = new TbReveExpen();
		tr.setChReveExpenId('1');
		tr.setPositiveOrNegative('+');
		tr.setVcNote("收入");
		reveExpenDao.saveOrUpdate(tr);

		tr = new TbReveExpen();
		tr.setChReveExpenId('2');
		tr.setPositiveOrNegative('-');
		tr.setVcNote("支出");
		reveExpenDao.saveOrUpdate(tr);

		tr = new TbReveExpen();
		tr.setChReveExpenId('3');
		tr.setPositiveOrNegative('=');
		tr.setVcNote("轉賬");
		reveExpenDao.saveOrUpdate(tr);

	}

	/**
	 * <br>
	 * Created on: 2013-11-22 下午04:41:19
	 * 
	 * @return
	 */
	public long getSpendingCount() {
		String hql = "select count(*) from TbSpending t";
		Long count = spendingDao.count(hql, null);
		if (DataTools.isEmpty(count))
			return 0l;

		return count.longValue();
	}
}
