package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbStatTerminalChangeTask;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;


@Repository
public class TerminalUpgradeTaskDAO extends HibernateDao<FtbStatTerminalChangeTask, Long> {

	/**
	 * 添加数据提取任务(不保存结束时间)
	 */
	public void saveTask(FtbStatTerminalChangeTask entity) {
	//	String sql = "insert into ftbStatTerminalChangeTask(nmTerminalChangeId,vcTaskName,dtOrderTime,vcTaskOrder,dtTaskStartTime,dtTaskEndTime,bitTaskActiveFlag,intTaskStatus) values (?,?,?,?,?,?,?,?)";
		String sql = "insert into ftbStatTerminalChangeTask(nmTerminalChangeId,vcTaskName,dtOrderTime,vcTaskOrder,dtTaskStartTime,bitTaskActiveFlag,intTaskStatus) values (?,?,?,?,?,?,?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, entity.getNmTerminalChangeId());
		query.setParameter(1, entity.getVcTaskName());

		query.setParameter(2, entity.getDtOrderTime());
		query.setParameter(3, entity.getVcTaskOrder());
		query.setParameter(4, entity.getDtTaskStartTime());
		//query.setParameter(5, entity.getDtTaskEndTime());
		query.setParameter(5, entity.getBitTaskActiveFlag());
		query.setParameter(6, entity.getIntTaskStatus());
	
		query.executeUpdate();
	}

	public Page<CommonSport> query(final Page page, String vcTaskName,
			String vcTaskOrder, int intTaskStatus, String startTime,
			String endTime,long TerminalChangeId) {
		String sql = this.buildSql(vcTaskName, vcTaskOrder, intTaskStatus,
				startTime, endTime, TerminalChangeId,page);

		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());

		List list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonSport> buildBeanList(List list) {
		List<CommonSport> CustomerList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			CustomerList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				 commonSport.setNmTerminalChangeId(Common.StringToLong(bean[1]+""));
				commonSport.setNmTerminalChangeIdTaskId(Common.StringToLong(bean[0]+""));
				commonSport.setVcTaskName(bean[2]+"");
				commonSport.setDtOrderTime(CommonUtils.formatDate(bean[3]));
				commonSport.setVcTaskOrder(bean[4]+"");
				commonSport.setDtStartTime(CommonUtils.formatDate(bean[5]));
				if(bean[6]!=null)
				commonSport.setDtEndTime(CommonUtils.formatDate(bean[6]));
				commonSport.setBitTaskActiveFlag(Common.StringToInt((bean[7]+"")));
				commonSport.setIntTaskStatus((Common.StringToInt(bean[8]+"")));
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	public String buildSql(String vcTaskName, String vcTaskOrder,
			int intTaskStatus, String startTime, String endTime,long TerminalChangeId, Page page) {
		String sql = "";
		String sortSql = "";
		String sortType = " asc ";
		String sortColumn = " dtOrderTime";
		String gropsqlString = "";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select nmTerminalChangeIdTaskId,nmTerminalChangeId,vcTaskName,dtOrderTime,vcTaskOrder,dtTaskStartTime,dtTaskEndTime,bitTaskActiveFlag,intTaskStatus"
				+ " from ftbStatTerminalChangeTask where nmTerminalChangeId="+TerminalChangeId;
		if (vcTaskName != null && !("").equals(vcTaskName))
			sql = sql + " and vcTaskName like '%" + vcTaskName + "%'";
		if (vcTaskOrder != null && !("").equals(vcTaskOrder))
			sql = sql + " and vcTaskOrder like '%" + vcTaskOrder + "%'";
		if (intTaskStatus != -1)
			sql = sql + " and intTaskStatus =" + intTaskStatus;
		if (startTime != null && !("").equals(startTime))
			sql = sql
					+ " and dtOrderTime >="
					+ MantoEyeUtils.formatData(startTime,
							CommonConstants.MANTOEYE_TIME_LEVEL_HOUR);
		if (endTime != null && !("").equals(endTime))
			sql = sql
					+ " and dtOrderTime <="
					+ MantoEyeUtils.formatData(endTime,
							CommonConstants.MANTOEYE_TIME_LEVEL_HOUR);

		sortSql = sortSql + " order by  " + sortColumn + sortType;
				sql = sql + sortSql;
		return sql;
	}

	/**
	 * 删除任务
	 * 
	 * @param taskId
	 */
	public void deleteTask(Long taskId) {
		String deleteSql = "delete from  ftbStatTerminalChangeTask where nmTerminalChangeIdTaskId ="
				+ taskId;
		String sql = "delete from ftbStatFlushTerminalChange where nmTerminalChangeIdTaskId="
				+ taskId;
		String sql2 = "delete from ftbStatBussFlushTerminalChange where nmTerminalChangeIdTaskId="
				+ taskId;
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
		this.getSession().createSQLQuery(sql).executeUpdate();
		this.getSession().createSQLQuery(sql2).executeUpdate();
	}

	public Integer queryByVcTaskName(String vcTaskName) {
		String queryIdSql = "select nmTerminalChangeIdTaskId from ftbStatTerminalChangeTask where vcTaskName='"
				+ vcTaskName + "'";// 获取当前插入任务ID
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
	}


	/*
	 * 
	 * 停止任务
	 */
	public void stopTask(long taskId) {
		String deleteSql = "update ftbStatTerminalChangeTask set intTaskStatus=4 where nmTerminalChangeIdTaskId ="
				+ taskId;

		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}

	/*
	 * 
	 * 开始任务
	 */
	public void startTask(long taskId) {
		String deleteSql = "update ftbStatTerminalChangeTask set intTaskStatus=1 where nmTerminalChangeIdTaskId ="
				+ taskId;

		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}

}
