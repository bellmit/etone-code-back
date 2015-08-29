package com.symbol.app.mantoeye.dao.bishealth;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbNetworkTask1;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

import examples.newsgroups;

/**
 * 自定义数据输出
 * 
 * @author chenchengle
 * 
 */
@Repository
public class BisHealthDAO extends HibernateDao<FtbNetworkTask1, Long> {

	/**
	 * 添加数据提取任务
	 */
	public void saveTask(FtbNetworkTask1 entity) {
		String sql = "insert into ftbNetworkTask(intTaskDelong,vcTaskName,intTaskType,dtOrderTime,vcTaskOrder,dtStartTime,dtEndTime,bitTaskActiveFlag,intTaskStatus,nmBussinessId,intActiveDay) values (?,?,?,?,?,?,?,?,?,?,?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, entity.getIntTaskDelong());
		query.setParameter(1, entity.getVcTaskName());
		query.setParameter(2, entity.getIntTaskType());
		query.setParameter(3, entity.getDtOrderTime());
		query.setParameter(4, entity.getVcTaskOrder());
		query.setParameter(5, entity.getDtStartTime());
		query.setParameter(6, entity.getDtEndTime());
		query.setParameter(7, entity.getBitTaskActiveFlag());
		query.setParameter(8, entity.getIntTaskStatus());
		query.setParameter(9, entity.getNmBussinessId());
		query.setParameter(10, 2);
		query.executeUpdate();
	}

	// ////////////////////////////////业务健康度/////////////////////////////////////////////////////
	public Page<CommonSport> query(final Page page, String vcTaskName,
			String vcTaskOrder, int intTaskStatus, String startTime,
			String endTime) {
		String sql = this.buildSql(vcTaskName, vcTaskOrder, intTaskStatus,
				startTime, endTime, page);

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
				// commonSport.setIntTaskDelong(Common.StringToInt(
				// bean[1].toString()));
				commonSport.setNmNetworkTaskId(Common.StringToLong(bean[0]
						.toString()));
				commonSport.setVcTaskName(bean[2].toString());
				commonSport.setDtOrderTime(CommonUtils.formatDate(bean[4]));
				commonSport.setVcTaskOrder(bean[5].toString());
				commonSport.setDtStartTime(CommonUtils.formatDate(bean[6])
						.split(" ")[0]);
				commonSport.setIntTaskType(Common.StringToInt(bean[3]
						.toString()));
				commonSport.setDtEndTime(CommonUtils.formatDate(bean[7]).split(
						" ")[0]);

				commonSport.setBitTaskActiveFlag(Common.StringToInt((bean[8]
						.toString())));
				commonSport.setIntTaskStatus((Common.StringToInt(bean[9]
						.toString())));
				commonSport.setNmBussinessId(Common
						.StringToLong((bean[10] + "")));
				commonSport
						.setIntActiveDay(Common.StringToInt((bean[11] + "")));
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	public String buildSql(String vcTaskName, String vcTaskOrder,
			int intTaskStatus, String startTime, String endTime, Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " desc ";
		String sortType = " asc ";
		String sortColumn = " dtOrderTime";
		String gropsqlString = "";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}

		sql = "select nmNetworkTaskId,intTaskDelong,vcTaskName,intTaskType,dtOrderTime,vcTaskOrder,dtStartTime,dtEndTime,bitTaskActiveFlag,intTaskStatus,nmBussinessId,intActiveDay"
				+ " from ftbNetworkTask where 1=1 ";
		if (vcTaskName != null && !("").equals(vcTaskName))
			sql = sql + " and vcTaskName like '" + vcTaskName + "%'";
		if (vcTaskOrder != null && !("").equals(vcTaskOrder))
			sql = sql + " and vcTaskOrder like'" + vcTaskOrder + "%'";
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

		defaultSortSql = " order by dtOrderTime " + defaultSortType;
		sql = sql + gropsqlString;
		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("statdate".equals(page.getOrderBy())) {// 按时间顺序查询
				defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
						sortType);
				sql = sql + defaultSortSql;
			} else {// 其他查询
				sql = sql + sortSql;
			}
		} else {// 图形查询按时间先后顺序
			sql = sql + defaultSortSql;
		}
		return sql;
	}

	/**
	 * 删除任务
	 * 
	 * @param taskId
	 */
	public void deleteTask(Long taskId) {
		String deleteSql = "delete from  ftbNetworkTask where nmNetworkTaskId ="
				+ taskId;
		String sql = "delete from ftbStatConnect where nmNetworkTaskId="
				+ taskId;
		String sql2 = "delete from ftbStatConnectRequest where nmNetworkTaskId="
				+ taskId;
		String sql3 = "delete from ftbStatAppAckTime where nmNetworkTaskId="
				+ taskId;
		String sql4 = "delete from ftbStatBusinessActivity where nmNetworkTaskId="
				+ taskId;
		String sql5 = "delete from ftbStatBusinessdevelopment where nmNetworkTaskId="
				+ taskId;
		String sql6 = "delete from ftbNetworkResult where nmNetworkTaskId="
			+ taskId;
	
		this.getSession().createSQLQuery(sql).executeUpdate();
		this.getSession().createSQLQuery(sql2).executeUpdate();
		this.getSession().createSQLQuery(sql3).executeUpdate();
		this.getSession().createSQLQuery(sql4).executeUpdate();
		this.getSession().createSQLQuery(sql5).executeUpdate();
		this.getSession().createSQLQuery(sql6).executeUpdate();
		this.getSession().createSQLQuery(deleteSql).executeUpdate();

	}

	public Integer queryByVcTaskName(String vcTaskName) {
		String queryIdSql = "select nmNetworkTaskId from ftbNetworkTask where vcTaskName='"
				+ vcTaskName + "'";// 获取当前插入任务ID
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
	}

	public List<BussAndBussType> FindBisById(long bisid) {
		//String sql = "select nmBussinessTypeId,vcBussinessName from dtbBusinessSite where nmBussinessId=" + bisid;
		String sql = "select nmDimensId as nmBussinessId,nmGroupId as nmBussinessTypeId,vcDimensName as vcBussinessName,vcGroupName as vcBussinessTypeName from dtbGroupTree where nmTypeId=1 and nmDimensId ="+bisid+" group by nmDimensId,nmGroupId,vcDimensName,vcGroupName order by nmDimensId";
		List list = this.getSession().createSQLQuery(sql).list();
		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
		BussAndBussType bt = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bt = new BussAndBussType();
				Object[] obj = (Object[]) list.get(i);
				bt.setBusinessId(obj[0] + "");
				bt.setBusinessName(obj[2] + "");
				bt.setBusinessTypeId(obj[1] + "");
				bt.setBusinessTypeName(obj[3] + "");
				listEntity.add(bt);
			}
		}
		return listEntity;
	}

	/*
	 * 
	 * 停止任务
	 */
	public void stopTask(long taskId) {
		String deleteSql = "update ftbNetworkTask set intTaskStatus=4 where nmNetworkTaskId ="
				+ taskId;

		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}

	/*
	 * 
	 * 开始任务
	 */
	public void startTask(long taskId) {
		String deleteSql = "update ftbNetworkTask set intTaskStatus=1 where nmNetworkTaskId ="
				+ taskId;

		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}

	// ///////////////////////////////////以下为业务健康度子页///////////////////////////////////////////////////////////////

	public Page<CommonSport> queryDetail(final Page page, int intTaskType, long taskId) {
		Page newPage = new Page();
		
		String sql = "";
		List netWorkList = FindNetWorkById(taskId);
		Object[] obj = (Object[]) netWorkList.get(0);
		String sTime=obj[1]+"";
		String eTime=obj[2]+"";
		if (intTaskType == 1)
			sql = this.buildStatConnectSql( sTime, eTime, taskId,
					page);
		if (intTaskType == 2)
			sql = this.buildStatConnectRequestSql( sTime, eTime,
					taskId, page);
		if (intTaskType == 3)
			sql = this.buildStatAppAckTimeSql( sTime, eTime, taskId,
					page);
		if (intTaskType == 4)
			sql = this.buildBusinessActivitySql( sTime, eTime,
					taskId, page);
		if (intTaskType == 5)
			sql = this.buildBusinessdevelopmentSql( sTime, eTime,
					taskId, page);
		
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());

		List list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
	
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list, intTaskType, taskId));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonSport> buildBeanList(List list,
			int taskType, long taskId) {
		int timeLevel=taskType==1||taskType==2||taskType==3?2:3;
		List<CommonSport> CustomerList = null;
		CommonSport commonSport = null;
			CustomerList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);

				if (null != bean[1] && !bean[1].equals(""))
					commonSport.setIntYear(Common.StringToInt(bean[1] + ""));

				switch (timeLevel) {

				case 2:
					commonSport.setIntMonth(Common.StringToInt(bean[2] + ""));// 月
					commonSport.setIntDay(Common.StringToInt(bean[3] + ""));// 日

					break;
				case 3:

					commonSport.setIntWeek(Common.StringToInt(bean[2] + ""));// 周
					break;
//				case 4:
//					commonSport.setIntMonth(Common.StringToInt(bean[2] + ""));// 月
//					break;
				}
				commonSport.setSpanDate(timeLevel);
				switch (taskType) {
				case 1:
					commonSport.setIntSuccess(Common.StringToInt(bean[0] + ""));
					commonSport.setIntTimes(Common.StringToInt(bean[4] + ""));
					break;
				case 2:
					commonSport.setIntAppSuccessful(Common.StringToInt(bean[0]
							+ ""));
					commonSport
							.setIntAppTimes(Common.StringToInt(bean[4] + ""));
					break;
				case 3:
					commonSport.setIntAppAckTime(Common.StringToLong(bean[0]
							+ ""));
					commonSport.setIntAppAckTimes(Common.StringToInt(bean[4]
							+ ""));
					break;
				case 4:
					commonSport.setNmActiveUsers(Common.StringToLong(bean[0]
							+ ""));
					commonSport.setNmAllUsers(Common.StringToInt(bean[3] + ""));
					break;
				case 5:
					commonSport.setNmUsers(Common.StringToLong(bean[0] + ""));
					commonSport.setNmFlush(Common.StringToLong(bean[3] + ""));
					commonSport.setNmOFlush(Common.StringToLong(bean[4] + ""));
					commonSport.setIntImsis(Common.StringToLong(bean[5]+""));//上周人数
					commonSport.calculateData();
					break;
				}

				CustomerList.add(commonSport);
			}
		return CustomerList;
	}
/*
 * 组合业务健康度中结果统计数据
 * */
	public List<CommonSport> buildStringList(List list, long taskId){
double sum=0;
		List<CommonSport> datasList=new ArrayList<CommonSport>();
			String bis=findBisByTaskId(taskId);
			for (int i = 0; i < list.size(); i++) {
				CommonSport commonSport=new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				List netWorkList = FindNetWorkById(taskId);
				Object[] obj = (Object[]) netWorkList.get(0);
				sum=sum+Common.StringTodouble(bean[1] + "")*Common.StringTodouble( bean[3] + "");
			//	String[] datas=new String[]{bis,(obj[1] + "").split(" ")[0] + "~"+ (obj[2] + "").split(" ")[0],findNetworkConfigureByTypeId(bean[0] + ""),bean[2] + "",bean[1] + "",bean[3] + ""};
				commonSport.setStatdate((obj[1] + "").split(" ")[0] + "~"+ (obj[2] + "").split(" ")[0]);
				commonSport.setBusinessName(bis);
				commonSport.setVcName(findNetworkConfigureByTypeId(bean[0] + ""));
				commonSport.setIntScale(bean[1] + "");
				commonSport.setIntCount(bean[2] + "");
				commonSport.setIntMark(bean[3]+"");
				datasList.add(commonSport);
			}
		//	String[] data=new String[]{sum+""};
		DecimalFormat df=new DecimalFormat("#.##");
		sum=Common.StringTodouble(df.format(sum));
			CommonSport commonSport=new CommonSport();
			commonSport.setSum(sum);

			datasList.add(commonSport);
			 return datasList;
	}
	
	/*
	 * 结果统计查询
	 * */
	public List<CommonSport> queryNetworkResult(  long taskId) {
		String sql=buildNetworkResultSql(taskId);
		List list=this.getSession().createSQLQuery(sql).list();
		return buildStringList(list,taskId);
		
	}
	
	
	/**
	 * 组装查询语句 连接成功率统计表
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 *            .
	 * @param endTime
	 * @return
	 */
	public String buildStatConnectSql(  String sTime,
			String eTime, Long taskId, Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " desc ";
		String sortType = " asc ";
		String sortColumn = " nmConnectId";

		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		

			sql = "select intSuccess,intYear,intMonth,intDay,intTimes"
					+ " from ftbStatConnect where nmNetworkTaskId =" + taskId;
			if (sTime != null && !"".equals(sTime))
				sql += " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))>="
						+ MantoEyeUtils.formatData(sTime, 2);
			if (eTime != null && !"".equals(eTime))
				sql += " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))<="
						+ MantoEyeUtils.formatData(eTime, 2);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay "
					+ defaultSortType;


	
		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("statdate".equals(page.getOrderBy())) {
				defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
						sortType);
				sql = sql + defaultSortSql;
			} else {// 其他查询
				sql = sql + sortSql;
			}
		} else {// 图形查询按时间先后顺序
			sql = sql + defaultSortSql;
		}
		return sql;
	}

	/*
	 * 业务请求成功率统计表 2
	 */
	public String buildStatConnectRequestSql(  String sTime,
			String eTime, Long taskId, Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " desc ";
		String sortType = " asc ";
		String sortColumn = " nmConnectRequestId";

		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		
			sql = "select intAppSuccessful,intYear,intMonth,intDay,intAppTimes"
					+ " from ftbStatConnectRequest where nmNetworkTaskId ="
					+ taskId;
			if (sTime != null && !"".equals(sTime))
				sql += " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))>="
						+ MantoEyeUtils.formatData(sTime, 2);
			if (eTime != null && !"".equals(eTime))
				sql += " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))<="
						+ MantoEyeUtils.formatData(eTime, 2);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay "
					+ defaultSortType;

	

		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("statdate".equals(page.getOrderBy())) {
				defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
						sortType);
				sql = sql + defaultSortSql;
			} else {// 其他查询
				sql = sql + sortSql;
			}
		} else {// 图形查询按时间先后顺序
			sql = sql + defaultSortSql;
		}
		return sql;
	}

	/*
	 * 业务连接响应统计表 3
	 */
	public String buildStatAppAckTimeSql( String sTime,
			String eTime, Long taskId, Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " desc ";
		String sortType = " asc ";
		String sortColumn = " nmConnectRequestId";

		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
	
			sql = "select intAppAckTime,intYear,intMonth,intDay,intAppAckTimes"
					+ " from ftbStatAppAckTime where nmNetworkTaskId ="
					+ taskId;
			if (sTime != null && !"".equals(sTime))
				sql += " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))>="
						+ MantoEyeUtils.formatData(sTime, 2);
			if (eTime != null && !"".equals(eTime))
				sql += " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))<="
						+ MantoEyeUtils.formatData(eTime, 2);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay "
					+ defaultSortType;

		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("statdate".equals(page.getOrderBy())) {// 按时间顺序查询
				defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
						sortType);
				sql = sql + defaultSortSql;
			} else {// 其他查询
				sql = sql + sortSql;
			}
		} else {// 图形查询按时间先后顺序
			sql = sql + defaultSortSql;
		}
		return sql;
	}

	/*
	 * 业务活跃用户指标统计表 4
	 */
	public String buildBusinessActivitySql( String sTime,
			String eTime, Long taskId, Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " asc ";
		String sortType = " desc ";
		String sortColumn = " nmBusinessActivityId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
	
			Date sDate = CommonUtils.getDate(sTime);
			int sYear = CommonUtils.getYear(sDate);
			int sMonth = CommonUtils.getMonth(sDate);
			int sWeek = CommonUtils.getWeek(sDate);

			Date eDate = CommonUtils.getDate(eTime);
			int eYear = CommonUtils.getYear(eDate);
			int eMonth = CommonUtils.getMonth(eDate);
			int eWeek = CommonUtils.getWeek(eDate);

			if (sMonth == 12 && sWeek == 1) {
				sYear = sYear + 1;
			}
			if (eMonth == 12 && eWeek == 1) {
				eYear = eYear + 1;
			}

			String bSql = "";
			for (int i = 1; i < eYear - sYear; i++) {
				bSql = bSql + " or (intYear = " + (sYear + i) + ") ";
			}

			sql = "select nmUsers,intYear,intWeek,nmAllUsers"
					+ " from ftbStatBusinessActivity  "
					+ " where nmNetworkTaskId= " + taskId;
			if (sYear != eYear) {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek < 100 )" + " or ( intYear = "
						+ eYear + " and intWeek>0 and intWeek <= " + eWeek
						+ " ))  " + bSql;
			} else {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek <= " + eWeek + " ))  " + bSql;
			}
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intWeek  " + defaultSortType;
	

		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("statdate".equals(page.getOrderBy())) {// 按时间顺序查询
				defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
						sortType);
				sql = sql + defaultSortSql;
			} else {// 其他查询
				sql = sql + sortSql;
			}
		} else {// 图形查询按时间先后顺序
			sql = sql + defaultSortSql;
		}
		return sql;
	}

	/*
	 * 业务持续发展指标统计表 5
	 */
	public String buildBusinessdevelopmentSql(  String sTime,
			String eTime, Long taskId, Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " asc ";
		String sortType = " desc ";
		String sortColumn = " dtStatTime ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
	 
			Date sDate = CommonUtils.getDate(sTime);
			int sYear = CommonUtils.getYear(sDate);
			int sMonth = CommonUtils.getMonth(sDate);
			int sWeek = CommonUtils.getWeek(sDate);

			Date eDate = CommonUtils.getDate(eTime);
			int eYear = CommonUtils.getYear(eDate);
			int eMonth = CommonUtils.getMonth(eDate);
			int eWeek = CommonUtils.getWeek(eDate);

			if (sMonth == 12 && sWeek == 1) {
				sYear = sYear + 1;
			}
			if (eMonth == 12 && eWeek == 1) {
				eYear = eYear + 1;
			}

			String bSql = "";
			for (int i = 1; i < eYear - sYear; i++) {
				bSql = bSql + " or (intYear = " + (sYear + i) + ") ";
			}

			sql = "select nmUsers,intYear,intWeek,nmFlush,nmAllFlush,nmPrevUsers"
					+ " from ftbStatBusinessdevelopment  "
					+ " where nmNetworkTaskId=" + taskId;
			if (sYear != eYear) {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek < 100 )" + " or ( intYear = "
						+ eYear + " and intWeek>0 and intWeek <= " + eWeek
						+ " ))  " + bSql;
			} else {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek <= " + eWeek + " ))  " + bSql;
			}
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intWeek  " + defaultSortType;
		 
		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("statdate".equals(page.getOrderBy())) {// 按时间顺序查询
				defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
						sortType);
				sql = sql + defaultSortSql;
			} else {// 其他查询
				sql = sql + sortSql;
			}
		} else {// 图形查询按时间先后顺序
			sql = sql + defaultSortSql;
		}
		return sql;
	}

	public List<CommonSport> listData(  int intTaskType, long taskId) {
		String sql = "";
		List netWorkList = FindNetWorkById(taskId);
		Object[] obj = (Object[]) netWorkList.get(0);
		String sTime=obj[1]+"";
		String eTime=obj[2]+"";
		if (intTaskType == 1)
			sql = this.buildStatConnectSql( sTime, eTime, taskId,
					null);
		if (intTaskType == 2)
			sql = this.buildStatConnectRequestSql(  sTime, eTime,
					taskId, null);
		if (intTaskType == 3)
			sql = this.buildStatAppAckTimeSql(  sTime, eTime, taskId,
					null);
		if (intTaskType == 4)
			sql = this.buildBusinessActivitySql(  sTime, eTime,
					taskId, null);
		if (intTaskType == 5)
			sql = this.buildBusinessdevelopmentSql( sTime, eTime,
					taskId, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list,intTaskType, taskId);
	}
	

	
	

	/*
	 * 
	 * 统计结果
	 */
	public String buildNetworkResultSql(Long taskId) {

		String sortSql = "";
		String sortType = " asc ";
		String sortColumn = " nmNetworkResultId";
		String sql = "select intType ,intScale,intCount,intMark from ftbNetworkResult"
				+ " where nmNetworkTaskId=" + taskId;

		sortSql = " order by " + sortColumn + sortType;
		// sql = sql + sortSql;
		return sql;

	}

	/*
	 * 统计结果中，根据任务ID查找任务表中的业务以及任务结束时间开始时间
	 */
	private List FindNetWorkById(long taskId) {
		String sql = "SELECT vcBussinessName,dtStartTime,dtEndTime FROM ftbNetworkTask as a LEFT JOIN dtbBusinessSite as b on a.nmBussinessId=b.nmBussinessId where  a.nmNetworkTaskId="
				+ taskId;
		return this.getSession().createSQLQuery(sql).list();
	}

	/*
	 * 根据统计结果表中的指标ID查处对应指标名
	 */
	private String findNetworkConfigureByTypeId(String intType) {
		String sql = "select vcName from ftbNetworkConfigure where intType="
				+ intType;
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && !list.isEmpty())
			return list.get(0).toString();
		else
			return "未知";

	}
	
	
	/*
	 * 根據任务ID查处业务名称
	 * */
private String findBisByTaskId(long taskId){
	String sql="SELECT b.vcBussinessName FROM ftbNetworkTask as a LEFT JOIN  dtbBusinessSite as b on a.nmBussinessId=b.nmBussinessId where a.nmNetworkTaskId="+taskId;
	List list=this.getSession().createSQLQuery(sql).list();
	if(list.get(0)!=null&&!("null").equals(list.get(0)+""))
		return list.get(0)+"";
	else
		return "未知";
}
	
	
	
	
}
