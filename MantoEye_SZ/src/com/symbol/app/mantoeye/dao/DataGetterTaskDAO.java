package com.symbol.app.mantoeye.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.FtbDataGetterDecTask;
import com.symbol.app.mantoeye.entity.FtbDataGetterFilter;
import com.symbol.app.mantoeye.entity.FtbDataGetterTask;
import com.symbol.app.mantoeye.entity.FtbFilterColumnMapTask;
import com.symbol.app.mantoeye.entity.FtbTableColumnMap;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.hibernate.HibernateDaoOld;

/**
 * 数据定制提取
 * 
 * @author rankin
 * 
 */
@Repository
public class DataGetterTaskDAO extends HibernateDaoOld<FtbDataGetterTask, Long> {
	
	// /**
	// * 添加数据提取任务
	// */
	// public Long saveTask(FtbDataGetterTask entity, TbBaseUserInfo userInfo) {
	// Integer taskDelong;
	//
	// if (entity.getIntTaskDelong() != 2) {// 数据提取模块
	// taskDelong = 1;
	// } else {// 拨测模块
	// taskDelong = 2;
	// }
	// //String taskName = entity.getVcTaskName();
	// Date orderTime = entity.getDtOrderTime();
	// int taskType = entity.getIntTaskType();
	// String sql =
	// "insert into ftbDataGetterTask(intTaskDelong ,vcTaskName,intTaskType,dtOrderTime ,nmTaskOrder,dtStartTime ,dtEndTime ,bitTaskActiveFlag , intTaskStatus ) values (?,?,?,?,?,?,?,?,?) ";
	// Query query = this.getSession().createSQLQuery(sql);
	//
	// query.setParameter(0, taskDelong);
	// query.setParameter(1, entity.getVcTaskName());// 任务名
	// query.setParameter(2, entity.getIntTaskType());// 任务类型
	// query.setParameter(3, new Date());// 定制时间
	// query.setParameter(4, userInfo.getVcUserName());// 定制人
	// query.setParameter(5, entity.getDtStartTime());// 开始时间
	// query.setParameter(6, entity.getDtEndTime());// 结束时间
	// query.setParameter(7, entity.getBitTaskActiveFlag());// 是否激活
	// query.setParameter(8, 0);// 状态 0:未开始
	// query.executeUpdate();
	// // 返回刚插入的主键ID
	// String sql2 =
	// "select nmDataGetterTaskId from  ftbDataGetterTask where dtOrderTime = ? and intTaskType = ? order by nmDataGetterTaskId desc ";
	// Query query2 = this.getSession().createSQLQuery(sql2);
	// query2.setParameter(0, orderTime);
	// query2.setParameter(1, taskType);
	// List list2 = query2.list();
	// Long taskId = 0L;
	// if (list2 != null && !list2.isEmpty()) {
	// taskId = ((BigDecimal) list2.get(0)).longValue();
	// }
	// logger.info("DAO*TaskId:"+taskId);
	// return taskId;
	// }
	/**
	 * 添加数据提取任务
	 */
	public Long saveTask(FtbDataGetterTask entity, TbBaseUserInfo userInfo) {
		Integer taskDelong;
		Long taskId = 0L;

		if (entity.getIntTaskDelong() != 2) {// 数据提取模块
			taskDelong = 1;
		} else {// 拨测模块
			taskDelong = 2;
		}
		// -- 输入参数： @intTaskDelong , @vcTaskName, @intTaskType, @dtOrderTime ,
		// @nmTaskOrder, @dtStartTime , @dtEndTime ,
		// -- @bitTaskActiveFlag , @intTaskStatus
		// try{
		SessionFactory s = this.getSessionFactory();
		Session session = s.openSession();
		// 客户信息
		Connection con = session.connection();
		Transaction ts = session.beginTransaction();
		String procedure = "{Call proc_create_datagettertask_byweb(?,?,?,?,?,?,?,?,?,?) }";
		CallableStatement cstmt;
		try {
			cstmt = con.prepareCall(procedure);

			cstmt.registerOutParameter(1, java.sql.Types.NUMERIC);
			cstmt.setString(2, entity.getVcTaskName());
			cstmt.setInt(3, entity.getIntTaskType());
			cstmt.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
			cstmt.setString(5, userInfo.getVcUserName());
			cstmt.setTimestamp(6, new java.sql.Timestamp(entity
					.getDtStartTime().getTime()));
			cstmt.setTimestamp(7, new java.sql.Timestamp(entity.getDtEndTime()
					.getTime()));
			cstmt.setInt(8, entity.getBitTaskActiveFlag() ? 1 : 0);
			cstmt.setInt(9, 0);// 状态 0:未开始
			cstmt.setInt(10, taskDelong);
			cstmt.executeUpdate();
			ts.commit();
			taskId = cstmt.getLong(1) + 0L;

			logger.info("-----***------" + taskId + "----****----");
			return taskId;
		} catch (Exception e) {
			ts.rollback();
			logger.error(e.getMessage());
			return 0L;
		}

		// String
		// sql="{Call proc_create_datagettertask_byweb(?,?,?,?,?,?,?,?,?,?) }";
		// SQLQuery query = this.getSession().createSQLQuery(sql);
		// query.setInteger(0, taskDelong);
		// query.setString(1, entity.getVcTaskName());
		// query.setInteger(2,entity.getIntTaskType());
		// query.setDate(3, new Date());
		// query.setString(4,userInfo.getVcUserName());
		// query.setDate(5, entity.getDtStartTime());
		// query.setDate(6, entity.getDtEndTime());
		// query.setInteger(7, entity.getBitTaskActiveFlag()?1:0);
		// query.setInteger(8, 0);// 状态 0:未开始
		//
		//
		// query.executeUpdate();
		//
		// }catch(Exception e){
		// logger.error(e.getMessage());
		// logger.error(e.toString());
		// }finally{
		// }
	}

	/**
	 * 添加数据提取任务关联过滤条件
	 */
	public void saveTaskFilter(FtbDataGetterFilter filter) {
		String sql = "insert into ftbDataGetterFilter(nmDataGetterTaskId,intFilterType,vcFilterValue ) values (?,?,?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, filter.getNmDataGetterTaskId());// 数据提取ID
		query.setParameter(1, filter.getIntFilterType());// 类型
		query.setParameter(2, filter.getVcFilterValue());// 号码
		query.executeUpdate();
	}

	/**
	 * 根据任务ID获取解析文件表信息
	 * 
	 * @param taskId
	 * @return
	 */
	public List<FtbDataGetterDecTask> getDecFileInfo(Long taskId) {
		String hql = "from FtbDataGetterDecTask f where f.nmDataGetterTaskId = ?";
		List list = this.find(hql, new Long[] { taskId });
		return list;
	}

	/**
	 * 根据任务ID获取过滤条件表信息
	 * 
	 * @param taskId
	 * @return
	 */
	public List<FtbDataGetterFilter> getFilterInfo(Long taskId) {
		String hql = "from FtbDataGetterFilter f where f.nmDataGetterTaskId = ?";
		List list = this.find(hql, new Long[] { taskId });
		return list;
	}

	/**
	 * 根据任务ID获取过滤条件表信息
	 * 
	 * @param taskId
	 * @return
	 */
	public Map<Long, List<FtbDataGetterFilter>> getFilterInfoByIds(
			String taskIds) {
		Map<Long, List<FtbDataGetterFilter>> rmap = new HashMap<Long, List<FtbDataGetterFilter>>();
		String hql = "from FtbDataGetterFilter f where f.nmDataGetterTaskId in ("
				+ taskIds + ")";
		List list = this.find(hql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				FtbDataGetterFilter f = (FtbDataGetterFilter) list.get(i);
				Long id = f.getNmDataGetterTaskId();
				if (rmap.get(id) != null) {
					rmap.get(id).add(f);
				} else {
					List<FtbDataGetterFilter> l = new ArrayList<FtbDataGetterFilter>();
					l.add(f);
					rmap.put(id, l);
				}

			}
		}
		return rmap;
	}

	/**
	 * 根据任务ID获取服务器 任务对应表信息
	 * 
	 * @param taskId
	 * @return
	 */
	public List getServerMapTask(Long taskId) {
		String sql = "select nmServerMapTaskId from ftbDataGetterServerMapTask  where nmDataGetterTaskId = ?";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, taskId);
		List list = query.list();
		return list;
	}

	/**
	 * 根据号码获取任务ID
	 */
	public Long[] getTaskIdsByMsisdn(String msisdn) {
		Long[] taskIds = null;
		msisdn = "%" + msisdn + "%";
		String hql = "from FtbDataGetterFilter f where f.intFilterType = 1 and f.vcFilterValue like ?";
		List list = this.find(hql, new String[] { msisdn });
		if (list != null && !list.isEmpty()) {
			taskIds = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				taskIds[i] = ((FtbDataGetterFilter) list.get(i))
						.getNmDataGetterTaskId();
			}
		} else {
			taskIds = new Long[1];
			taskIds[0] = -1L;
		}
		return taskIds;
	}

	public Long[] getTaskIdsByMsisdn2(String msisdn) {
		Long[] taskIds = null;
		String[] marr = msisdn.split(",");
		String hql = "select f.nmDataGetterFilterId from FtbDataGetterFilter f where f.intFilterType = 1";
		if (marr.length == 1) {
			msisdn = "'%" + msisdn + "%'";
			hql += " and f.vcFilterValue like " + msisdn;
		} else if (marr.length > 1) {
			hql += " and f.vcFilterValue like '%" + marr[0] + "%'";
			for (int i = 1; i <= marr.length - 1; i++) {
				hql += " or (f.vcFilterValue like '%" + marr[i] + "%')";
			}
		}
		// List list = this.find(hql, new String[] { msisdn });
		List list = this.getSession().createSQLQuery(hql).list();
		if (list != null && !list.isEmpty()) {
			taskIds = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				taskIds[i] = Common.StringToLong(list.get(i) + "");
			}
		} else {
			taskIds = new Long[1];
			taskIds[0] = -1L;
		}

		return taskIds;
	}

	/**
	 * 根据号码获取任务ID
	 */
	public Long[] getTaskIdsByMsisdn1(String msisdn, int columnId) {
		Long[] taskIds = null;
		String sql = "select nmDataGetterTaskId from ftbFilterColumnMapTask f where f.nmTableColumnMapId = "
				+ columnId + " and f.vcFilterValue like '%" + msisdn + "%' ";
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && !list.isEmpty()) {
			taskIds = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				taskIds[i] = Common.StringToLong(list.get(i) + "");
			}
		} else {
			taskIds = new Long[1];
			taskIds[0] = -1L;
		}
		return taskIds;
	}

	/**
	 * 获取表字段集合信息
	 */
	public List<FtbTableColumnMap> getTableColumnMap() {
		// 通过过原始表类型(intTableType=5)查
		// String hql =
		// " from FtbTableColumnMap c where c.ftbTableMap.nmTableMapId = (select m.nmTableMapId  from  FtbTableMap m where m.intTableType=5 )";
		// 通过原始表名称查
		String hql = " from FtbTableColumnMap c where c.ftbTableMap.nmTableMapId = (select m.nmTableMapId  from  FtbTableMap m where m.vcTableName='v_ftbGnAppData2TQ' )";
		List list = this.find(hql);
		return list;
	}

	/**
	 * 添加数据提取任务关联字段过滤条件
	 */
	public void saveTaskColumnFilter(Long taskId, int columnId, String value,
			String condition) {
		String sql = "insert into ftbFilterColumnMapTask(nmDataGetterTaskId,nmTableColumnMapId,vcFilterValue,vcCondition ) values (?,?,?,?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, taskId);// 数据提取ID
		query.setParameter(1, columnId);// 字段ID
		query.setParameter(2, value);// 值
		query.setParameter(3, condition);// 条件
		query.executeUpdate();
	}

	/**
	 * 添加数据提取任务需要呈现字段
	 */
	public void saveOutColumn(Long taskId, int columnId) {
		// String sql =
		// "insert into ftbOutColumnMapTask(nmDataGetterTaskId,nmTableColumnMapId,bitGourpBy,vcCondition ) values (?,?,?,?)";
		String sql = "insert into ftbOutColumnMapTask(nmDataGetterTaskId,nmTableColumnMapId) values (?,?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, taskId);// 数据提取ID
		query.setParameter(1, columnId);// 呈现字段ID
		// if(Common.judgeString(gourpBy)){
		// query.setParameter(2, 1);// 是否分组
		// query.setParameter(3, gourpBy);// 分组标识
		// }else{
		// query.setParameter(2, 0);// 是否分组
		// query.setParameter(3, gourpBy);// 分组标识
		// }
		query.executeUpdate();
	}

	/**
	 * 删除数据提取任务关联过滤条件
	 */
	public void deleteTaskFilter(Long taskId) {
		String deleteSql = "delete from  ftbDataGetterFilter where nmDataGetterTaskId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, taskId);
		deleteQuery.executeUpdate();
	}

	public void deleteDataGetterBusiness(Long taskId) {
		String deleteSql = "delete from  ftbDataGetterBusiness where nmDataGetterTaskId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, taskId);
		deleteQuery.executeUpdate();
	}

	public void deleteServerList(short serverListId) {
		String deleteSql = "delete from  ftbServerList where nmServerListId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, serverListId);
		deleteQuery.executeUpdate();
	}

	public void deleteTaskFile(Long serverMapId) {
		String deleteSql = "delete from  ftbDataGetterTaskFileInfo where nmServerMapTaskId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, serverMapId);
		int i = deleteQuery.executeUpdate();
		logger.info("serverMapId:" + serverMapId + "    delete size:" + i);
	}

	public void deleteTaskDecFile(Long taskId) {
		String deleteSql = "delete from  ftbDataGetterDecTask where nmDataGetterTaskId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, taskId);
		deleteQuery.executeUpdate();
	}

	public void deleteServerMap(Long tastId) {
		String deleteSql = "delete from  ftbDataGetterServerMapTask where nmDataGetterTaskId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, tastId);
		deleteQuery.executeUpdate();
	}

	/**
	 * 删除数据提取任务关联字段过滤条件(表ftbFilterColumnMapTask)
	 */
	public void deleteColumnMapFilter(Long taskId) {
		String deleteSql = "delete from  ftbFilterColumnMapTask where nmDataGetterTaskId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, taskId);
		deleteQuery.executeUpdate();
	}

	/**
	 * 删除数据提取任务关联过滤条件(表ftbOutColumnMapTask)
	 */
	public void deleteColumnMapOut(Long taskId) {
		String deleteSql = "delete from  ftbOutColumnMapTask where nmDataGetterTaskId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, taskId);
		deleteQuery.executeUpdate();
	}

	/**
	 * 获取表字段集合信息
	 */
	public List<FtbFilterColumnMapTask> getColumnMap(Long taskId) {
		String hql = " from FtbFilterColumnMapTask  where nmDataGetterTaskId = ?";
		List list = this.find(hql, new Object[] { taskId });
		return list;
	}

	/**
	 * 获取表字段集合信息
	 */
	public Map<Long, List<FtbFilterColumnMapTask>> getColumnMapByIds(String ids) {
		Map<Long, List<FtbFilterColumnMapTask>> rmap = new HashMap<Long, List<FtbFilterColumnMapTask>>();
		String hql = " from FtbFilterColumnMapTask  where nmDataGetterTaskId in ("
				+ ids + ")";
		List list = this.find(hql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				FtbFilterColumnMapTask r = (FtbFilterColumnMapTask) list.get(i);
				Long id = r.getFtbDataGetterTask().getNmDataGetterTaskId();
				if (rmap.get(id) != null) {
					rmap.get(id).add(r);
				} else {
					List<FtbFilterColumnMapTask> l = new ArrayList<FtbFilterColumnMapTask>();
					l.add(r);
					rmap.put(id, l);
				}

			}
		}
		return rmap;
	}

	/**
	 * 删除任务
	 * 
	 * @param taskId
	 */
	public void deleteTask(Long taskId) {
		String deleteSql = "delete from  ftbDataGetterTask where nmDataGetterTaskId = ?";
		Query deleteQuery = this.getSession().createSQLQuery(deleteSql);
		deleteQuery.setParameter(0, taskId);
		deleteQuery.executeUpdate();
	}

}
