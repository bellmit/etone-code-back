package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.flush.TerminalTaskEntity;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class OwnBusinessTaskDAO extends HibernateDao {

	/**
	 * 
	 * @param 插入终端测试任务
	 */
	public void saveTerminalTask(TerminalTaskEntity terminalTask) {
		if ("".equals(terminalTask.getTaskDescribe())
				|| terminalTask.getTaskDescribe() == null) {
			terminalTask.setTaskDescribe("  ");

		}
		String taskSql = "insert into ftbTerminalPolicy (vcPolicyName,vcPolicyNote,dtSTime,dtETime,intStatus) values "// 插入终端定制任务语句
				+ "('"
				+ terminalTask.getTaskName()
				+ "','"
				+ terminalTask.getTaskDescribe()
				+ "','"
				+ terminalTask.getBeginTime()
				+ "','"
				+ terminalTask.getEndTime()
				+ "',"
				+ terminalTask.getIntTaskStatus() + ")";
		this.getSession().createSQLQuery(taskSql).executeUpdate();

		Integer taskId = this.queryIdByTaskName(terminalTask.getTaskName());

		logger.info("**" + taskId);

		// 在wcmp_config.properties配置文件路径
		String terminalPath = PropertiesUtil.getInstance().getProperty(
				"termina.filePath");

		wirterAndLoadTableBuss(terminalPath, terminalTask, taskId);

	}

	/**
	 * 
	 * @param taskName
	 * @return Integer
	 * 
	 *         通过任务名判断任务是否存在 不存在返回-1 存在返回当前ID名称
	 */

	public Integer queryIdByTaskName(String taskName) {
		String queryIdSql = "select nmTerminalPolicyId from ftbTerminalPolicy where vcPolicyName='"
				+ taskName + "'";// 获取当前插入任务ID
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
	}

	/**
	 * 
	 * @param bussPath
	 * @param terminalTask
	 * @param taskId
	 *            批量insert into 到业务关系表中
	 */
	public void wirterAndLoadTableBuss(String bussPath,
			TerminalTaskEntity terminalTask, Integer taskId) {
		Map<String, String> map = terminalTask.getListBuss();
		String buss = "";
		String bussType = "";
		String sql = "insert into ftbTerminalPolicyMapBusiness(nmTerminalPolicyId,nmBussinessTypeId,nmBussinessId) select tp.nmTerminalPolicyId,bt.nmBussinessTypeId,bs.nmBussinessId  from dtbBusinessType bt,dtbBusinessSite bs,ftbTerminalPolicy tp"
				+ " where 1=1 ";

		logger.info("**map**" + map);
		List<String> businessTypeList = new ArrayList<String>(map.keySet());
		sql = sql + " and tp.nmTerminalPolicyId=" + taskId;
		if (businessTypeList!=null && businessTypeList.size()>0) {
			for (int i = 0; i < businessTypeList.size(); i++) {
				bussType = businessTypeList.get(i);
				buss = map.get(bussType);
				if (i == 0) {
					sql = sql + " and ((bt.nmBussinessTypeId=" + bussType
							+ " and bs.nmBussinessId in(" + buss + ")) ";
				} else {
					sql = sql + " or (bt.nmBussinessTypeId=" + bussType
							+ " and bs.nmBussinessId in(" + buss + ")) ";
				}
			}
			sql = sql +")";
		}

		int num = this.getSession().createSQLQuery(sql).executeUpdate();
		logger.info("****************insert busi number:"+num);
	}

	/**
	 * 
	 * @param tableName
	 * @param columns
	 * @param fileName
	 * @return String
	 * 
	 *         组装load table 语句
	 */

	public String bulidString(String tableName, String columns, String fileName) {
		StringBuffer sb = new StringBuffer();
		sb.append("load table ");
		sb.append(tableName);
		sb.append(" (");
		// vcMsisdn,vcBrand,vcType
		sb.append(columns);
		sb.append(") from '");
		sb.append(fileName);
		sb.append("' delimited by '\t' row delimited by '\r\n' "
				+ "escapes off quotes off with checkpoint on");
		return sb.toString();
	}

	/**
	 * 
	 * @param page
	 * @param terminalTask
	 * @return List 根据条件查询所有任务
	 */

	public Page<TerminalTaskEntity> queryTask(final Page page,
			TerminalTaskEntity terminalTask) {
		List<TerminalTaskEntity> list = new ArrayList<TerminalTaskEntity>();
		StringBuffer sql = new StringBuffer(
				"select nmTerminalPolicyId,vcPolicyName,vcPolicyNote,dtSTime,dtETime,intStatus from ftbTerminalPolicy where 1=1");
		if (terminalTask.getTaskName() != null
				&& !"null".equals(terminalTask.getTaskName())) {
			sql.append(" and vcPolicyName like '%");
			sql.append(terminalTask.getTaskName());
			sql.append("%'");
		}

		if (terminalTask.getBeginTime() != null
				&& !"null".equals(terminalTask.getBeginTime())) {
			sql.append(" and( (dtSTime>='");
			sql.append(terminalTask.getBeginTime());
			sql.append("'");
			sql.append(" and dtSTime<='");
			sql.append(terminalTask.getEndTime());
			sql.append("')");

			sql.append(" or (dtETime>='");
			sql.append(terminalTask.getBeginTime());
			sql.append("'");
			sql.append(" and dtETime<='");
			sql.append(terminalTask.getEndTime());
			sql.append("') )");
		}

		if (!"-1".equals(terminalTask.getTaskStatus())) {
			sql.append(" and intStatus=");
			sql.append(terminalTask.getTaskStatus());
		}
		sql.append(" order by nmTerminalPolicyId desc ");
		// List listAll=query.list();
		Page newPage = new Page();
		String sqls = sql.toString().split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<Object[]> listObj = this.getSession().createSQLQuery(
				sql.toString()).setFirstResult(page.getFirst()).setMaxResults(
				page.getPageSize()).list();

		newPage.setTotalCount(total);
		newPage.setPageNo(page.getPageNo());
		newPage.setPageSize(page.getPageSize());

		TerminalTaskEntity taskEntity = null;
		if (listObj != null && !listObj.isEmpty()) {
			for (int i = 0; i < listObj.size(); i++) {
				taskEntity = new TerminalTaskEntity();
				Object[] obj = listObj.get(i);
				taskEntity.setNmTerminalPolicyId(Common
						.StringToInt(obj[0] + ""));
				taskEntity.setTaskName(obj[1] + "");
				taskEntity.setTaskDescribe(obj[2] + "");
				taskEntity.setBeginTime(CommonUtils.formatDate(obj[3]));
				taskEntity.setEndTime(CommonUtils.formatDate(obj[4]));
				taskEntity.setTaskStatus(this.changeStatus(Common
						.StringToInt(obj[5] + "")));
				list.add(taskEntity);
			}
		}
		newPage.setResult(list);
		return newPage;
	}

	public String changeStatus(int i) {
		String status = "";
		switch (i) {
		case -1:
			status = "全部";
			break;
		case 0:
			status = "异常";
			break;
		case 1:
			status = "未处理";
			break;
		case 2:
			status = "进行中";
			break;
		case 3:
			status = "已完成";
			break;
		}
		return status;
	}

	public void deleteTask(String keys) {
		String sqlTerminal = "delete from ftbTerminalPolicyMapTerm where nmTerminalPolicyId in ("
				+ keys + ")";
		String sqlBuss = "delete from ftbTerminalPolicyMapBusiness where nmTerminalPolicyId in ("
				+ keys + ")";
		
		String sqlTask = "delete from ftbTerminalPolicy where nmTerminalPolicyId in ("
				+ keys + ")";

		this.getSession().createSQLQuery(sqlTerminal).executeUpdate();
		this.getSession().createSQLQuery(sqlBuss).executeUpdate();
		this.getSession().createSQLQuery(sqlTask).executeUpdate();
	}

	/**
	 * 查询已完成的任务
	 * 
	 * @return
	 */
	public List<TerminalTaskEntity> queryAllTask() {
		String sql = "select nmTerminalPolicyId,vcPolicyName from ftbTerminalPolicy where intStatus=3";
		List list = this.getSession().createSQLQuery(sql).list();
		List<TerminalTaskEntity> listData = new ArrayList<TerminalTaskEntity>();
		TerminalTaskEntity taskEntity = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				taskEntity = new TerminalTaskEntity();
				Object[] obj = (Object[]) list.get(i);
				taskEntity.setNmTerminalPolicyId(Common
						.StringToInt(obj[0] + ""));
				taskEntity.setTaskName(obj[1] + "");
				listData.add(taskEntity);
			}
		}
		return listData;
	}

	/**
	 * 根据ID 查询 对应任务
	 * 
	 * @param id
	 * @return
	 */
	public TerminalTaskEntity queryTaskByTaskId(int id) {
		String sql = "select nmTerminalPolicyId,vcPolicyName,vcPolicyNote,dtSTime,dtETime,intStatus from ftbTerminalPolicy where nmTerminalPolicyId="
				+ id;
		List list = this.getSession().createSQLQuery(sql).list();
		TerminalTaskEntity taskEntity = new TerminalTaskEntity();
		if (list != null && !list.isEmpty()) {
			Object[] obj = (Object[]) list.get(0);
			taskEntity.setNmTerminalPolicyId(Common.StringToInt(obj[0] + ""));
			taskEntity.setTaskName(obj[1] + "");
			taskEntity.setTaskDescribe(obj[2] + "");
			taskEntity.setBeginTime(CommonUtils.formatDate(obj[3]));
			taskEntity.setEndTime(CommonUtils.formatDate(obj[4]));
			taskEntity.setIntTaskStatus(Common.StringToInt(obj[5] + ""));

		}
		return taskEntity;
	}

}
