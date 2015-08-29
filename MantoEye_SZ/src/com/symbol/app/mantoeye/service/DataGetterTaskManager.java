package com.symbol.app.mantoeye.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.bean.DataFilterBean;
import com.symbol.app.mantoeye.dao.DataGetterTaskDAO;
import com.symbol.app.mantoeye.dao.business.BusinessSiteDAO;
import com.symbol.app.mantoeye.dto.VFtbDataGetterTask;
import com.symbol.app.mantoeye.entity.FtbDataGetterDecTask;
import com.symbol.app.mantoeye.entity.FtbDataGetterFilter;
import com.symbol.app.mantoeye.entity.FtbDataGetterTask;
import com.symbol.app.mantoeye.entity.FtbFilterColumnMapTask;
import com.symbol.app.mantoeye.entity.FtbTableColumnMap;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManagerOld;

/**
 * 全量业务自发现
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class DataGetterTaskManager extends
		EntityManagerOld<FtbDataGetterTask, Long> {

	@Autowired
	private DataGetterTaskDAO dataGetterTaskDAO;

	@Autowired
	private BusinessSiteDAO businessSiteDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected DataGetterTaskDAO getEntityDao() {
		return dataGetterTaskDAO;
	}

	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbDataGetterTask> search(final Page<FtbDataGetterTask> page,
			final List<PropertyFilter> filters) {
		System.out.println("<------------------connect DataGetterTaskManager start ------------------------>");
		Page<FtbDataGetterTask> apage = dataGetterTaskDAO.find(page, filters);
		System.out.println("<------------------connect DataGetterTaskManager end ------------------------>");
		List<FtbDataGetterTask> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}

	// 实体到视图的转换
	public List<VFtbDataGetterTask> convertBeanToView(
			List<FtbDataGetterTask> list) {
		List<VFtbDataGetterTask> viewList = new ArrayList<VFtbDataGetterTask>();
		if (list != null && !list.isEmpty()) {
			String ids = ",";
			
			for (FtbDataGetterTask bean : list) {
				VFtbDataGetterTask view = new VFtbDataGetterTask();
				Long id = bean.getNmDataGetterTaskId();
				if(ids.indexOf(","+id+",")==-1){
					ids = ids + id + ",";
				}
				int tasktype = bean.getIntTaskType();
				view.setNmDataGetterTaskId(bean.getNmDataGetterTaskId());// 任务ID
				view.setVcTaskName(bean.getVcTaskName());// 任务名
				view.setNmTaskOrder(bean.getNmTaskOrder());// 定制人
				view.setDtOrderTime(CommonUtils.formatFullDate(bean
						.getDtOrderTime()));// 定制时间
				view.setDtStartTime(CommonUtils.formatFullDate(bean
						.getDtStartTime()));// 开始时间
				view.setDtEndTime(CommonUtils.formatFullDate(bean
						.getDtEndTime()));// 结束时间
				view.setBitTaskActiveFlag(bean.getBitTaskActiveFlag());// 是否激活
				view.setIntTaskStatus(bean.getIntTaskStatus());// 任务状态
				view.setIntTaskType(tasktype);// 任务类型
				
				if (tasktype == 5 || tasktype == 6
						|| tasktype == 7
						|| tasktype == 8) {// 拨测维护
					List<FtbDataGetterDecTask> desFileList = dataGetterTaskDAO
							.getDecFileInfo(bean.getNmDataGetterTaskId());
					if (desFileList != null && !desFileList.isEmpty()) {
						view.setFileInfo(desFileList.get(0).getVcFilePath());
					}
					DtbBusinessSite dbs = findBusinessFilter(bean
							.getNmDataGetterTaskId());
					if (dbs != null) {
						view.setBusinessName(dbs.getVcBussinessName());// 业务名称
						view.setBusinessId(dbs.getNmBussinessId());
					}
				}
				viewList.add(view);
			}
			if(ids.length()>1){
				ids = ids.substring(1,ids.length()-1);
			}
			// 用户号码
			Map<Long,List<FtbFilterColumnMapTask>>  hmap = dataGetterTaskDAO.getColumnMapByIds(ids);
			Map<Long,List<FtbDataGetterFilter>> cmap = dataGetterTaskDAO.getFilterInfoByIds(ids);
			
			for(int i=0;i<viewList.size();i++){
				VFtbDataGetterTask v = viewList.get(i);
				int tasktype = v.getIntTaskType();
				Long id = v.getNmDataGetterTaskId();
				if(tasktype==3){
					viewList.get(i).setMsisdn(formatHMsisdn(hmap.get(id)));// 用户号码
				}else{
					viewList.get(i).setMsisdn(formatCMsisdn(cmap.get(id)));// 用户号码
				}				
			}
		}
		return viewList;
	}
//	/**
//	 * key taskid,value businessname
//	 * @param filterMap
//	 * @return
//	 */
//	public Map<Long, List<DtbBusinessSite>> getBusinessNameMap(
//			Map<Long, List<FtbDataGetterFilter>> filterMap) {
//		String busiIds = ",";
//		Map<Long, List<DtbBusinessSite>> bnameMap = new HashMap<Long, List<DtbBusinessSite>>();
//		if (filterMap != null && !filterMap.isEmpty()) {
//			Set<Long> set = filterMap.keySet();
//			Iterator it = set.iterator();
//			//组装id
//			while(it.hasNext()) {
//				List<FtbDataGetterFilter> flist = filterMap.get(it.next());
//				for (int i = 0; i < flist.size(); i++) {
//					FtbDataGetterFilter filter = flist.get(i);
//					if (filter.getIntFilterType() == 1) {
//						if (busiIds.indexOf("," + filter.getVcFilterValue()
//								+ ",") == -1) {
//							busiIds = busiIds + filter.getVcFilterValue() + ",";
//						}
//					}
//				}
//			}
//			if (busiIds.length() > 1) {
//				busiIds = busiIds.substring(1, busiIds.length() - 1);
//			}
//			logger.info("busiIds:" + busiIds);
//			Map<Long, DtbBusinessSite> busiNameMap = businessSiteDAO
//					.getMapByIds(busiIds);
//			Iterator<Long> it1 = set.iterator();
//			//放入业务名称
//			while(it1.hasNext()){
//				Long taskid = it1.next();
//				List<FtbDataGetterFilter> flist = filterMap.get(taskid);
//				List<DtbBusinessSite> nameLists = new ArrayList<DtbBusinessSite> ();
//				for (int i = 0; i < flist.size(); i++) {
//					FtbDataGetterFilter filter = flist.get(i);				
//					if (filter.getIntFilterType() == 1) {
//						nameLists .add( busiNameMap.get(Common.StringToLong(filter.getVcFilterValue())));
//					}
//				}
//				logger.info("业务名称>>>>>>>>>>>:"+nameLists.size());
//				bnameMap.put(taskid, nameLists);
//			}
//		}
//		return bnameMap;
//	}

	/**
	 * 去掉提取号码86字段 历史解析数据
	 */
	public String formatHMsisdn(List<FtbFilterColumnMapTask> filterInfoList) {
		String msis = "";
		// 历史解析数据
		if (filterInfoList != null && !filterInfoList.isEmpty()) {
			for (int i = 0; i < filterInfoList.size(); i++) {
				FtbFilterColumnMapTask filter = filterInfoList.get(i);
				String m = filter.getVcFilterValue();
				// 判断是否是手机号码
				if (m.startsWith("86") && m.length() == 13
						&& NumberUtils.isNumber(m)) {
					m = m.substring(2, filter.getVcFilterValue().length());
					msis = msis + m + ",";
				} else if (m.length() == 11 && NumberUtils.isNumber(m)) {
					msis = msis + m + ",";
				}
			}
			if (msis != "" && msis.length() > 0) {
				msis = msis.substring(0, msis.length() - 1);
			}
		}
		return msis;
	}

	/**
	 * 去掉提取号码86字段 原始数据
	 */
	public String formatCMsisdn(List<FtbDataGetterFilter> filterInfoList) {
		String msis = "";
		if (filterInfoList != null && !filterInfoList.isEmpty()) {
			for (int i = 0; i < filterInfoList.size(); i++) {
				FtbDataGetterFilter filter = filterInfoList.get(i);
				if (filter.getIntFilterType() == 1) {
					String m = filter.getVcFilterValue().substring(2,
							filter.getVcFilterValue().length());
					if (i != filterInfoList.size() - 1) {
						msis = msis + m + ",";
					} else {
						msis = msis + m;
					}
				}
			}
		}
		return msis;
	}

	/**
	 * 去掉提取号码86字段
	 * 
	 */
	public String formatMsisdn(Long taskId, int taskType) {
		String msis = "";
		if (taskType == 3) {// 历史解析数据
			List<FtbFilterColumnMapTask> filterInfoList = dataGetterTaskDAO
					.getColumnMap(taskId);
			if (filterInfoList != null && !filterInfoList.isEmpty()) {
				for (int i = 0; i < filterInfoList.size(); i++) {
					FtbFilterColumnMapTask filter = filterInfoList.get(i);
					String m = filter.getVcFilterValue();
					// 判断是否是手机号码
					if (m.startsWith("86") && m.length() == 13
							&& NumberUtils.isNumber(m)) {
						m = m.substring(2, filter.getVcFilterValue().length());
						msis = msis + m + ",";
					} else if (m.length() == 11 && NumberUtils.isNumber(m)) {
						msis = msis + m + ",";
					}
				}
				if (msis != "" && msis.length() > 0) {
					msis = msis.substring(0, msis.length() - 1);
				}
			}
		} else if (taskType != 4) {// 原始数据
			List<FtbDataGetterFilter> filterInfoList = dataGetterTaskDAO
					.getFilterInfo(taskId);
			if (filterInfoList != null && !filterInfoList.isEmpty()) {
				for (int i = 0; i < filterInfoList.size(); i++) {
					FtbDataGetterFilter filter = filterInfoList.get(i);
					if (filter.getIntFilterType() == 1) {
						String m = filter.getVcFilterValue().substring(2,
								filter.getVcFilterValue().length());
						if (i != filterInfoList.size() - 1) {
							msis = msis + m + ",";
						} else {
							msis = msis + m;
						}
					}
				}
			}
		}
		return msis;
	}

	// 保存业务id的过滤条件
	private void saveBusiFilter(Long taskId, String busiid) {
		FtbDataGetterFilter filter = null;
		logger.info("busiid:------>" + busiid);
		// 业务id过滤
		filter = new FtbDataGetterFilter();
		filter.setNmDataGetterTaskId(taskId);
		filter.setIntFilterType(3);
		filter.setVcFilterValue(busiid);
		dataGetterTaskDAO.saveTaskFilter(filter);
	}

	// 保存号码的过滤条件
	private void saveMsisdnFilter(Long taskId, String msisdn) {
		FtbDataGetterFilter filter = null;
		logger.info("busiid:------>" + msisdn);
		filter = new FtbDataGetterFilter();
		filter.setNmDataGetterTaskId(taskId);
		filter.setIntFilterType(1);
		filter.setVcFilterValue("86" + msisdn);
		dataGetterTaskDAO.saveTaskFilter(filter);
	}

	// 返回一个实体的副本
	private FtbDataGetterTask cloneEntity(FtbDataGetterTask entity) {
		FtbDataGetterTask bean = new FtbDataGetterTask();
		bean.setDtStartTime(entity.getDtStartTime());
		bean.setDtEndTime(entity.getDtEndTime());
		bean.setDtOrderTime(entity.getDtOrderTime());
		bean.setIntTaskDelong(entity.getIntTaskDelong());
		bean.setIntTaskStatus(entity.getIntTaskStatus());
		if (entity.getIntTaskType() == 5) {// 已有业务
			bean.setIntTaskType(7);
		} else {// 新业务
			bean.setIntTaskType(8);
		}
		bean.setNmTaskOrder(entity.getNmTaskOrder());
		bean.setBitTaskActiveFlag(entity.getBitTaskActiveFlag());
		bean.setVcTaskName(entity.getVcTaskName() + "[解码数据]");
		return bean;
	}

	/**
	 * 添加数据提取任务
	 */
	public void saveTask(FtbDataGetterTask entity, TbBaseUserInfo userInfo,
			String busiid, String msisdns) {

		Integer taskType = entity.getIntTaskType();

		// 原始信令Id
		Long taskId = dataGetterTaskDAO.saveTask(entity, userInfo);

		if (msisdns != null && !"".equals(msisdns)) {
			String[] mds = msisdns.split(",");
			for (String m : mds) {
				saveMsisdnFilter(taskId, m);
			}
		}
	}

	/**
	 * 添加数据提取任务
	 */
	public void saveBusiTask(FtbDataGetterTask entity, TbBaseUserInfo userInfo,
			String busiid, String msisdns) {

		Integer taskType = entity.getIntTaskType();
		// 解码数据任务
		FtbDataGetterTask entity1 = cloneEntity(entity);

		// 任务名称后面加上提取数据的类型
		entity.setVcTaskName(entity.getVcTaskName() + "[原始数据]");
		// 原始信令Id
		Long taskId = dataGetterTaskDAO.saveTask(entity, userInfo);
		// 解码数据Id
		Long taskId1 = dataGetterTaskDAO.saveTask(entity1, userInfo);

		// 已有业务拨测
		if (taskType == 5) {
			// 原始信令任务
			saveBusiFilter(taskId, busiid);
			// 解码数据任务
			saveBusiFilter(taskId1, busiid);
		}
		if (msisdns != null && !"".equals(msisdns)) {
			String[] mds = msisdns.split(",");
			for (String m : mds) {
				saveMsisdnFilter(taskId, m);
				saveMsisdnFilter(taskId1, m);
			}
		}
	}

	/**
	 * 保存解析记录
	 * hasgroup:分组标识
	 */
	public void saveParseTask(FtbDataGetterTask entity,
			TbBaseUserInfo userInfo, List<Integer> outColumns,
			List<DataFilterBean> filters) {
		Integer taskType = entity.getIntTaskType();
		// Id
		Long taskId = dataGetterTaskDAO.saveTask(entity, userInfo);
		// 保存输出列
//		if(hasgroup){
//			for (int i = 0; i < outColumns.size(); i++) {
//				dataGetterTaskDAO.saveOutColumn(taskId, outColumns.get(i),"distinct");//用户号码提取时必须过滤掉重复记录
//			}
//		}else{
			for (int i = 0; i < outColumns.size(); i++) {
				dataGetterTaskDAO.saveOutColumn(taskId, outColumns.get(i));
			}
//		}
		
		logger.info("****" + filters.size());
		// 保存条件
		for (int j = 0; j < filters.size(); j++) {

			DataFilterBean filter = filters.get(j);
			if (filter != null && filter.getColumnId() != null) {
				dataGetterTaskDAO.saveTaskColumnFilter(taskId, filter
						.getColumnId(), filter.getValue(), filter
						.getCondition());
			}
		}
	}

	/**
	 * 编辑解析记录
	 */
	public boolean editParseTask(FtbDataGetterTask entity,
			TbBaseUserInfo userInfo, List<DataFilterBean> filters) {

		boolean succ = true;
		FtbDataGetterTask entity2 = dataGetterTaskDAO.get(entity
				.getNmDataGetterTaskId());
		if (entity2.getIntTaskStatus() != 0) {
			succ = false;
		} else {
			entity2.setDtStartTime(entity.getDtStartTime());
			entity2.setDtEndTime(entity.getDtEndTime());
			entity2.setBitTaskActiveFlag(entity.getBitTaskActiveFlag());
			// Id
			// Long taskId = dataGetterTaskDAO.saveTask(entity, userInfo);
			Long taskId = entity.getNmDataGetterTaskId();
			dataGetterTaskDAO.save(entity2);
			// 删除原有条件
			dataGetterTaskDAO.deleteColumnMapFilter(taskId);
			// 保存条件
			for (int j = 0; j < filters.size(); j++) {
				DataFilterBean filter = filters.get(j);
				if (filter != null) {
					dataGetterTaskDAO.saveTaskColumnFilter(taskId, filter
							.getColumnId(), filter.getValue(), filter
							.getCondition());
				}
			}
		}
		return succ;
	}

	/**
	 * 编辑数据提取任务
	 */
	public boolean editTask(FtbDataGetterTask entity, String busiid,
			String msisdns) {

		boolean succ = true;
		FtbDataGetterTask entity2 = dataGetterTaskDAO.get(entity
				.getNmDataGetterTaskId());
		if (entity2.getIntTaskStatus() != 0) {
			succ = false;
		} else {
			entity2.setDtStartTime(entity.getDtStartTime());
			entity2.setDtEndTime(entity.getDtEndTime());
			entity2.setBitTaskActiveFlag(entity.getBitTaskActiveFlag());
			
			Integer taskType = entity.getIntTaskType();
			Long taskId = entity.getNmDataGetterTaskId();
			// 修改任务信息
			dataGetterTaskDAO.save(entity2);
			// 删除原有过滤条件
			dataGetterTaskDAO.deleteTaskFilter(taskId);

			// 已有业务拨测
			if (taskType == 5 || taskType == 7) {
				// 业务id过滤
				saveBusiFilter(taskId, busiid);
			}
			if (msisdns != null && !"".equals(msisdns)) {
				String[] mds = msisdns.split(",");
				for (String m : mds) {
					saveMsisdnFilter(taskId, m);
				}
			}
		}
		return succ;
	}

	public void deleteTasks(Long id) {
		// 删除过滤条件
		dataGetterTaskDAO.deleteTaskFilter(id);// 任务关联过滤条件
		dataGetterTaskDAO.deleteColumnMapOut(id);// 任务表字段信息
		dataGetterTaskDAO.deleteColumnMapFilter(id);// 任务关联过滤条件
		dataGetterTaskDAO.deleteTaskDecFile(id);// 删除解码后文件信息
		dataGetterTaskDAO.deleteDataGetterBusiness(id);// 删除拨测记录信息(ftbDataGetterBusiness)
		List maplist = dataGetterTaskDAO.getServerMapTask(id);
		if (maplist != null && maplist.size() > 0) {
			for (int i = 0; i < maplist.size(); i++) {
				Long mid = Common.StringToLong(maplist.get(i) + "");
				dataGetterTaskDAO.deleteTaskFile(mid);// 删除原始信令文件信息
			}
		}
		dataGetterTaskDAO.deleteServerMap(id);// 删除服务器任务对照表
		// 删除任务
		dataGetterTaskDAO.deleteTask(id);
	}

	public void deleteServerList(short serverListId) {
		dataGetterTaskDAO.deleteServerList(serverListId);
	}

	public void deleteTaskFile(Long serverMapId) {
		dataGetterTaskDAO.deleteTaskFile(serverMapId);
	}

	public void deleteServerMap(Long taskId) {
		dataGetterTaskDAO.deleteServerMap(taskId);
	}

	/**
	 * 根据号码获取任务ID
	 */
	public Long[] getTaskIdsByMsisdn(String msisdn) {
		return dataGetterTaskDAO.getTaskIdsByMsisdn(msisdn);
	}
	public Long[] getTaskIdsByMsisdn2(String msisdn) {
		return dataGetterTaskDAO.getTaskIdsByMsisdn2(msisdn);
	}
	/**
	 * 根据号码获取任务ID
	 */
	public Long[] getTaskIdsByMsisdn1(String msisdn,int columnId) {
		return dataGetterTaskDAO.getTaskIdsByMsisdn1(msisdn,columnId);
	}
	/**
	 * 获取过滤条件 去掉提取号码86字段
	 * 
	 */
	public String findMsisdnFilter(Long taskId) {
		String msis = "";
		List<FtbDataGetterFilter> filterInfoList = dataGetterTaskDAO
				.getFilterInfo(taskId);
		if (filterInfoList != null && !filterInfoList.isEmpty()) {
			for (int i = 0; i < filterInfoList.size(); i++) {
				FtbDataGetterFilter filter = filterInfoList.get(i);
				if (filter.getIntFilterType() == 1) {// 号码
					String m = filter.getVcFilterValue().substring(2,
							filter.getVcFilterValue().length());
					if (i != filterInfoList.size() - 1) {
						msis = msis + m + ",";
					} else {
						msis = msis + m;
					}
				}
			}
		}
		return msis;
	}

	/**
	 * 获取过滤条件 业务对象
	 * 
	 */
	public DtbBusinessSite findBusinessFilter(Long taskId) {
		DtbBusinessSite dbs = null;
		List<FtbDataGetterFilter> filterInfoList = dataGetterTaskDAO
				.getFilterInfo(taskId);
		String busiid = "";
		// logger.info(taskId+"");
		if (filterInfoList != null && !filterInfoList.isEmpty()) {
			for (int i = 0; i < filterInfoList.size(); i++) {
				FtbDataGetterFilter filter = filterInfoList.get(i);
				if (filter.getIntFilterType() == 3) {// 业务id
					busiid = filter.getVcFilterValue();
					break;
				}
			}
			Integer bid = Common.StringToInteger(busiid);
			if (bid > 0) {
				dbs = businessSiteDAO.get(bid);
			}
		}
		return dbs;
	}

	/**
	 * 获取表字段集合信息
	 */
	public List<FtbTableColumnMap> getTableColumnMap() {
		return dataGetterTaskDAO.getTableColumnMap();
	}

	// /**
	// * 获取任务服务器对应表信息
	// */
	// public List<FtbDataGetterServerMapTask> getServerMapTask(Long taskId) {
	// return dataGetterTaskDAO.getServerMapTask(taskId);
	// }

	/**
	 * 获取表字段集合信息
	 */
	public List<FtbFilterColumnMapTask> getColumnMap(Long taskId) {
		return dataGetterTaskDAO.getColumnMap(taskId);
	}

	/**
	 * 获取表字段集合信息Map
	 */
	public Map<Long, List<FtbFilterColumnMapTask>> getColumnMapByMap(String ids) {
		return dataGetterTaskDAO.getColumnMapByIds(ids);
	}

}
