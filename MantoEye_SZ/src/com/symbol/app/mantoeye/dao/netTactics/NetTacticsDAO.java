package com.symbol.app.mantoeye.dao.netTactics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsDetail;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;


@Component
public class NetTacticsDAO extends HibernateDao{
	/**查询网络内容策略任务表*/
	public List<?> queryTacticsList(){
		StringBuffer sql = new StringBuffer();
		sql.append("select nmDataGetterTaskId,vcTaskName,vcTaskOrder,dtOrderTime,dtStartTime,dtEndTime,"
				+ "intStartHour,intEndHour,intTaskStatus from ftbDataGetterTask_KW where intTaskType = 2  order by dtOrderTime desc");
		return getBySql(sql.toString());
	}
	
	/**向网络内容任务表插入数据*/
	public void insertTactics(String vcTaskName,String dtOrderTime,String vcTaskOrder,String dtStartTime,String dtEndTime,
			String intStartHour,String intEndHour,String vcContents){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ftbDataGetterTask_KW(vcTaskName,intTaskType,dtOrderTime,vcTaskOrder,dtStartTime,dtEndTime,"
				+"intStartHour,intEndHour,bitTaskActiveFlag,intTaskStatus,vcContents) values('"
				+ vcTaskName+"',"+2+",'"+dtOrderTime+"','"+vcTaskOrder+"','"+dtStartTime+"','"
				+dtEndTime+"',"+intStartHour+","+intEndHour+","+0+","+0+",'"+vcContents+"')");
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**保存策略*/
	public void updateTactics(String nmDataGetterTaskId,String vcTaskOrder,String dtStartTime,String dtEndTime,
			String intStartHour,String intEndHour,String vcContents){
		StringBuffer sql = new StringBuffer();
		sql.append("update ftbDataGetterTask_KW set vcTaskOrder='"+vcTaskOrder+"',dtStartTime='"+dtStartTime+
				"',dtEndTime='"+dtEndTime+"',intStartHour='"+intStartHour+"',intEndHour='"+intEndHour+"',vcContents='"
				+vcContents+"'");
		sql.append(" where nmDataGetterTaskId = "+nmDataGetterTaskId);
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**获取任务ID*/
	public String getTaskId(String dtOrderTime){
		StringBuffer sql = new StringBuffer();
		sql.append("select nmDataGetterTaskId from ftbDataGetterTask_KW where dtOrderTime='"+dtOrderTime+"'");
		List list = getBySql(sql.toString());
		return Utils.getString(list.get(0));
	}
	
	/**获取treeID*/
	public List<?> getTreeId(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct a.nmTreeId from(" +
				"select nmTreeId from dtbGroupTree where nmGroupId in("+id+")");
		sql.append(" union all");
		sql.append(" select nmTreeId from dtbGroupTree where nmParentId in("+id+")) a");
		return getBySql(sql.toString());
	}
	
	public List<Object> findKeyById(long id){
		StringBuffer sql = new StringBuffer();
		sql.append("select nmGroupId,vcGroupName,nmParentId from ftbNetContentGetterFilter left join dtbGroupTree on ftbNetContentGetterFilter.nmTreeId = dtbGroupTree.nmGroupId and nmTypeId=2 where nmDataGetterTaskId="+id+" group by nmGroupId,vcGroupName,nmParentId");
		return getBySql(sql.toString());
	}
	
	
	/**向网络内容提取过滤表插入数据*/
	public void insertNetContentGetterFilter(long nmDataGetterTaskId,long nmTreeId){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ftbNetContentGetterFilter(nmTreeId,nmDataGetterTaskId) " 
				+ "values("+nmTreeId+","+nmDataGetterTaskId+")");
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**停止策略*/
	public void stopTatctics(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("update ftbDataGetterTask_KW set intTaskStatus = " + 3);
		sql.append(" where nmDataGetterTaskId = " + nmDataGetterTaskId);
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**删除网络内容提取过滤表的数据*/
	public void deleteNetContentGetterFilter(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("delete from ftbNetContentGetterFilter where nmDataGetterTaskId in (" + nmDataGetterTaskId+")");
		sql1.append("delete from tbTacticsLog where nmDataGetterTaskId in (" + nmDataGetterTaskId+")");
		sql2.append("delete from ftbNetTacticsData where nmDataGetterTaskId in (" + nmDataGetterTaskId+")");
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
		this.getSession().createSQLQuery(sql1.toString()).executeUpdate();
		this.getSession().createSQLQuery(sql2.toString()).executeUpdate();
	}
	
	public void deleteNetContentGetterFilter(long nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ftbNetContentGetterFilter where nmDataGetterTaskId =" + nmDataGetterTaskId);
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**删除策略*/
	public void deleteTactics(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ftbDataGetterTask_KW where nmDataGetterTaskId in(" + nmDataGetterTaskId+")");
		deleteNetContentGetterFilter(nmDataGetterTaskId);
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**查询详细信息*/
	public List<?> getShowMessage(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct * from (");
		sql.append("select a.vcTaskName,a.dtStartTime,a.dtEndTime,a.intStartHour,a.intEndHour,");
		sql.append("a.vcContents,c.vcParentGroupName,c.vcGroupName" +
				" from ftbDataGetterTask_KW a,ftbNetContentGetterFilter b,dtbGroupTree c");
		sql.append(" where a.nmDataGetterTaskId=b.nmDataGetterTaskId and a.nmDataGetterTaskId="+nmDataGetterTaskId);
		sql.append(" and b.nmTreeId=c.nmTreeId) a");
		return getBySql(sql.toString());
	}
	
	/**查询结果列表*/
	public List<?> showDialect(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.nmId,a.nmMsisdn,b.vcParentGroupName,b.vcGroupName,c.userTime");
		sql.append(" from ftbNetTacticsData a,dtbGroupTree b,ftbDataGetterTask_KW d,");
		sql.append("(select CONCAT(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')");
		sql.append(" as userTime,date_format(str_to_date(CONCAT(intYear,'-',intMonth,'-',intDay),'%Y-%m-%d'),'%Y-%m-%d')");
		sql.append(" as selectTime from ftbNetTacticsData) as c");
		sql.append(" where c.selectTime between d.dtStartTime and d.dtEndTime");
		sql.append(" and a.intHour between d.intStartHour and d.intEndHour");
		sql.append(" and d.nmDataGetterTaskId ="+nmDataGetterTaskId);
		sql.append(" and b.nmTreeId = a.nmTreeId");
		sql.append(" limit 10");
		return getBySql(sql.toString());
	}
	
	/**查询导出列表*/
	public List<?> exportDialect(String nmDataGetterTaskId,String startDate,String endDate,String startHour,String endHour){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.nmId,a.nmMsisdn,b.vcParentGroupName,b.vcGroupName,c.userTime");
		sql.append(" from ftbNetTacticsData a,dtbGroupTree b,ftbDataGetterTask_KW d,");
		sql.append("(select CONCAT(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')");
		sql.append(" as userTime,date_format(str_to_date(CONCAT(intYear,'-',intMonth,'-',intDay),'%Y-%m-%d'),'%Y-%m-%d')");
		sql.append(" as selectTime from ftbNetTacticsData) as c");
		sql.append(" where c.selectTime between '" + startDate +"' and '" + endDate +"'");
		sql.append(" and a.intHour between " + startHour +" and " +endHour);
		sql.append(" and d.nmDataGetterTaskId ="+nmDataGetterTaskId);
		sql.append(" and b.nmTreeId = a.nmTreeId");
		sql.append(" limit 10");
		return getBySql(sql.toString());
	}
	
	public List getBySql(String sql) {
		try {
			return this.getSession().createSQLQuery(sql).list();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public List findTreeId(String businessIds){
		StringBuffer sql = new StringBuffer();
		sql.append("select nmTreeId from dtbGroupTree where nmTypeId=2 and  nmGroupId in (" + businessIds+")");
		return this.getSession().createSQLQuery(sql.toString()).list();
	}
	
	// 业务类型业务关联查询
	public List<BussAndBussType> queryBussAndBussType() {
		String sql = "select nmGroupId ,nmParentId ,vcGroupName ,vcParentGroupName  from dtbGroupTree where nmTypeId=2  group by nmParentId,vcParentGroupName,nmGroupId,vcGroupName order by nmParentId";
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
	
	
	public Page<KeywordsDetail> queryKey(final Page page, long id,String startTime,String endTime) {
		Date date1 = CommonUtils.getDate(startTime);
		Date date2 = CommonUtils.getDate(endTime);
		int startHour = CommonUtils.getHour(date1);
		int endHour = CommonUtils.getHour(date2);
		String sql = this.buildSql(id,startTime,endTime,startHour,endHour, page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<Object[]> list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	
	
	public List<KeywordsDetail> listData(Long id,String startTime,String endTime) {
		Date date1 = CommonUtils.getDate(startTime);
		Date date2 = CommonUtils.getDate(endTime);
		int startHour = CommonUtils.getHour(date1);
		int endHour = CommonUtils.getHour(date2);
		String sql = this.buildSql(id,startTime,endTime,startHour,endHour, null);
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		return buildBeanList(list);
	}
	
	public List<KeywordsDetail> buildBeanList(List list) {
		List<KeywordsDetail> kList = null;
		if (list!=null && list.size()>0) {
			kList = new ArrayList<KeywordsDetail>();
			for (int i = 0; i < list.size(); i++) {
				KeywordsDetail keywordsDetal = new KeywordsDetail();
				Object[] objs = (Object[]) list.get(i);
				keywordsDetal.setNmMsisdn(objs[0]+"");
				keywordsDetal.setVcSoName(objs[1]+"");
				keywordsDetal.setFullDate(objs[2]+"");
				keywordsDetal.setVcParentGroupName(objs[3]+"");
				kList.add(keywordsDetal);
			}
		}
		
		return kList;
	}
	
	
	public String buildSql(long id,String startTime,String endTime,int startHour,int endHour,Page page) {
		String sql = "";
		String fields = "";
		String table = "";
		String name = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " fullDate ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql= "select nmMsisdn,vcGroupName as vcsoname,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')  as fullDate,vcParentGroupName " +
			 " from ftbNetTacticsData left join dtbGroupTree on dtbGroupTree.nmTreeId = ftbNetTacticsData.nmTreeId and nmTypeId=2 " +
			 " where intyear!=0 and intmonth!=0 and intmonth<=12 and intday !=0 and intday<=31 and inthour<=23 and  nmDataGetterTaskId = "+id+" and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00'))>='"+startTime+"' and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00'))<='"+endTime+"' and intHour>="+startHour+" and intHour<="+endHour+" group by vcsoname,nmMsisdn,intYear,intMonth,intDay,intHour,vcParentGroupName";
		sortSql = sortSql + "  order by " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}
}
