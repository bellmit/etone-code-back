package com.symbol.app.mantoeye.dao.keywordsTactics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.keywordsTactics.FtbKeyValueGetterFilter;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsDetail;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsTactics;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;



@Component
public class KeywordsTacticsDAO extends HibernateDao{
	/**查询关键字策略任务表*/
	public List<?> queryTacticsList(){
		StringBuffer sql = new StringBuffer();
		sql.append("select nmDataGetterTaskId,vcTaskName,vcTaskOrder,dtOrderTime,dtStartTime,dtEndTime,"
				+ "intStartHour,intEndHour,intTaskStatus from ftbDataGetterTask_KW where intTaskType = 1");
		return getBySql(sql.toString());
	}
	
	/**向策略任务表插入数据*/
	public void insertTactics(String vcTaskName,String dtOrderTime,String vcTaskOrder,String dtStartTime,String dtEndTime,
			String intStartHour,String intEndHour,String vcContents){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ftbDataGetterTask_KW(vcTaskName,intTaskType,dtOrderTime,vcTaskOrder,dtStartTime,dtEndTime,"
				+"intStartHour,intEndHour,bitTaskActiveFlag,intTaskStatus,vcContents) values('"
				+ vcTaskName+"',"+1+",'"+dtOrderTime+"','"+vcTaskOrder+"','"+dtStartTime+"','"
				+dtEndTime+"',"+intStartHour+","+intEndHour+","+0+","+0+",'"+vcContents+"')");
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	public void save(KeywordsTactics entity){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ftbDataGetterTask_KW(vcTaskName,intTaskType,dtOrderTime,vcTaskOrder,dtStartTime,dtEndTime,"
				+"intStartHour,intEndHour,bitTaskActiveFlag,intTaskStatus,vcContents) values('"
				+ entity.getVcTaskName()+"',"+entity.getIntTaskType()+",'"+entity.getDtOrderTime()+"','"+entity.getVcTaskOrder()+"','"+entity.getDtStartTime()+"','"
				+entity.getDtEndTime()+"',"+entity.getIntStartHour()+","+entity.getIntEndHour()+","+entity.getBitTaskActiveFlag()+","+entity.getIntTaskStatus()+",'"+entity.getVcContents()+"')");
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	
	public void update(KeywordsTactics entity){
		StringBuffer sql = new StringBuffer();
		sql.append("update ftbDataGetterTask_KW set dtOrderTime='"+entity.getDtOrderTime()+"',vcTaskOrder='"+entity.getVcTaskOrder()+"',dtStartTime='"+entity.getDtStartTime()+"',dtEndTime='"+entity.getDtEndTime()+"',"
				+"intStartHour="+entity.getIntStartHour()+",intEndHour="+entity.getIntEndHour()+",intTaskStatus="+entity.getIntTaskStatus()+",vcContents='"+entity.getVcContents()+"' where nmDataGetterTaskId="+entity.getNmDataGetterTaskId());
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
	public long getTaskId(String dtOrderTime){
		StringBuffer sql = new StringBuffer();
		sql.append("select nmDataGetterTaskId from ftbDataGetterTask_KW where dtOrderTime='"+dtOrderTime+"'");
		List list = getBySql(sql.toString());
		return Long.parseLong(list.get(0).toString());
	}
	
	/**向策略提取过滤表插入精确匹配数据*/
	public long insertIsExactMark(long nmDataGetterTaskId,String vcFilterKeyValue,int isExactMark){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ftbKeyValueGetterFilter(vcFilterKeyValue,nmDataGetterTaskId,isExactMark) " 
				+ "values('"+vcFilterKeyValue+"',"+nmDataGetterTaskId+","+isExactMark+")");
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
		String idSql = "select @@identity";
		return Long.parseLong(this.getSession().createSQLQuery(idSql).list().get(0).toString());
	}
	
	/**向策略提取过滤表插入模糊匹配数据*/
	public void insertKeyValueSoTypeId(long nmSoTypeId,long nmDataGetterFilterId){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ftbKeyValueSoTypeIdFilter(nmSoTypeId,nmDataGetterFilterId) " 
				+ "values("+nmSoTypeId+","+nmDataGetterFilterId+")");
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**向策略提取过滤表插入模糊匹配数据*/
	public void insertUnExactMark(String nmDataGetterTaskId,String nmSoTypeId,String vcFilterKeyValue){
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ftbKeyValueGetterFilter(vcFilterKeyValue,nmDataGetterTaskId,nmSoTypeId,isExactMark) " 
				+ "values('"+vcFilterKeyValue+"',"+nmDataGetterTaskId+","+nmSoTypeId+","+0+")");
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**停止策略*/
	public void stopTatctics(int nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("update ftbDataGetterTask_KW set intTaskStatus = " + 3);
		sql.append(" where nmDataGetterTaskId in (" + nmDataGetterTaskId+")");
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	/**删除策略提取过滤表的数据*/
	public void deleteKeyValueGetterFilter(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("delete from ftbKeyValueGetterFilter where nmDataGetterTaskId in(" + nmDataGetterTaskId+")");
		sql1.append("delete from tbTacticsLog where nmDataGetterTaskId in(" + nmDataGetterTaskId+")");
		sql2.append("delete from ftbKeyTacticsData where nmDataGetterTaskId in(" + nmDataGetterTaskId+")");
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
		this.getSession().createSQLQuery(sql1.toString()).executeUpdate();
		this.getSession().createSQLQuery(sql2.toString()).executeUpdate();
	}
	
	public void deleteKeyValueGetterFilter(long nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ftbKeyValueGetterFilter where nmDataGetterTaskId in(" + nmDataGetterTaskId+")");
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	public void deleteKeyValueSoTypeIdFilter(String nmDataGetterFilterId){
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ftbKeyValueSoTypeIdFilter where nmDataGetterFilterId in(" + nmDataGetterFilterId+")");
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	
	
	public List findDataGetterFilterId(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("select nmDataGetterFilterId from ftbKeyValueGetterFilter where nmDataGetterTaskId in (" + nmDataGetterTaskId+")");
		return this.getSession().createSQLQuery(sql.toString()).list();
	}
	
	/**删除策略*/
	public void deleteTactics(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ftbDataGetterTask_KW where nmDataGetterTaskId in (" + nmDataGetterTaskId+")");
		deleteKeyValueGetterFilter(nmDataGetterTaskId);
//		getBySql(sql.toString());
		this.getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	

	public KeywordsTactics findTaskById(long id){
		StringBuffer sql = new StringBuffer();
		sql.append("select nmDataGetterTaskId,vcTaskName,intTaskType,dtOrderTime,vcTaskOrder,dtStartTime,dtEndTime,intStartHour,intEndHour,bitTaskActiveFlag,intTaskStatus,vcContents  from ftbDataGetterTask_KW where nmDataGetterTaskId = " + id);
		List<KeywordsTactics> ktList = this.getSession().createSQLQuery(sql.toString())
										.addScalar("nmDataGetterTaskId", Hibernate.LONG)
										.addScalar("vcTaskName",Hibernate.STRING)
										.addScalar("intTaskType",Hibernate.INTEGER)
										.addScalar("dtOrderTime",Hibernate.STRING)
										.addScalar("vcTaskOrder",Hibernate.STRING)
										.addScalar("dtStartTime",Hibernate.STRING)
										.addScalar("dtEndTime",Hibernate.STRING)
										.addScalar("intStartHour",Hibernate.STRING)
										.addScalar("intEndHour",Hibernate.STRING)
										.addScalar("bitTaskActiveFlag",Hibernate.INTEGER)
										.addScalar("intTaskStatus",Hibernate.INTEGER)
										.addScalar("vcContents",Hibernate.STRING)
										.setResultTransformer(Transformers.aliasToBean(KeywordsTactics.class)).list();
		return ktList.get(0);
	}
	
	public List<FtbKeyValueGetterFilter> findKeyById(long id){
		StringBuffer sql = new StringBuffer();
		sql.append("select vcFilterKeyValue,nmDataGetterTaskId,ftbKeyValueSoTypeIdFilter.nmSoTypeId as nmSoTypeId,vcSoName,isExactMark from ftbKeyValueGetterFilter,dtbSoType,ftbKeyValueSoTypeIdFilter where ftbKeyValueGetterFilter.nmDataGetterFilterId = ftbKeyValueGetterFilter.nmDataGetterFilterId and dtbSoType.nmSoTypeId=ftbKeyValueSoTypeIdFilter.nmSoTypeId and  nmDataGetterTaskId = " + id);
		List<FtbKeyValueGetterFilter> keyList = this.getSession().createSQLQuery(sql.toString())
										.addScalar("vcFilterKeyValue",Hibernate.STRING)
										.addScalar("nmDataGetterTaskId",Hibernate.LONG)
										.addScalar("nmSoTypeId",Hibernate.LONG)
										.addScalar("vcSoName",Hibernate.STRING)
										.addScalar("isExactMark",Hibernate.BOOLEAN)
										.setResultTransformer(Transformers.aliasToBean(FtbKeyValueGetterFilter.class)).list();
		return keyList;
	}
	
	
	/**查询详细信息*/
	public List<?> getShowMessage(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.vcTaskName,a.dtStartTime,a.dtEndTime,a.intStartHour,a.intEndHour,");
		sql.append("a.vcContents,c.vcSoName,b1.vcFilterKeyValue isExactMark,b2.vcFilterKeyValue unExactMark" +
				" from ftbDataGetterTask_KW a,ftbKeyValueGetterFilter b1,ftbKeyValueGetterFilter b2,dtbSoType c");
		sql.append(" where a.nmDataGetterTaskId=b1.nmDataGetterTaskId and a.nmDataGetterTaskId="+nmDataGetterTaskId);
		sql.append(" and b1.nmSoTypeId=c.nmSoTypeId and b1.isExactMark=1 and b2.isExactMark=0");
		sql.append(" and a.nmDataGetterTaskId=b2.nmDataGetterTaskId and b2.nmSoTypeId=c.nmSoTypeId");
		return getBySql(sql.toString());
	}
	
	/**查询结果列表*/
	public List<?> showDialect(String nmDataGetterTaskId){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.nmId,a.nmMsisdn,b.vcSoName,a.vcKeyValues,a.isExactMark,c.userTime");
		sql.append(" from ftbKeyTacticsData a,dtbSoType b,ftbDataGetterTask_KW d,");
		sql.append("(select CONCAT(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')");
		sql.append(" as userTime,CONCAT(intYear,'-',intMonth,'-',intDay)");
		sql.append(" as selectTime from ftbKeyTacticsData) as c");
		sql.append(" where c.selectTime between d.dtStartTime and d.dtEndTime");
		sql.append(" and a.intHour between d.intStartHour and d.intEndHour");
		sql.append(" and d.nmDataGetterTaskId ="+nmDataGetterTaskId);
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
	
	
	
	// 业务类型业务关联查询
	public List<BussAndBussType> queryBussAndBussType() {
		String sql = "select nmSoTypeId as nmSoTypeId,0 as nmBussinessTypeId,vcSoName as vcSoName,'搜索引擎' as vcBussinessTypeName from dtbSoType";
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
	
	public Integer queryByVcTaskName(String vcTaskName) {
		String queryIdSql = "select nmDataGetterTaskId from ftbDataGetterTask_KW where vcTaskName='"
				+ vcTaskName +"'";// 获取当前插入任务ID
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
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
				keywordsDetal.setVcKeyValues(objs[2]+"");
				if (objs[3].toString().equals("0")) {
					keywordsDetal.setIsExactMark("否");
				}else {
					keywordsDetal.setIsExactMark("是");
				}
				keywordsDetal.setFullDate(objs[4]+"");
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
		sql= "select nmMsisdn,vcSoName,vcKeyValues,isExactMark,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00')  as fullDate " +
			 " from ftbKeyTacticsData left join dtbSoType on dtbSoType.nmSoTypeId = ftbKeyTacticsData.nmSoTypeId " +
			 " where intyear!=0 and intmonth!=0 and intmonth<=12 and intday !=0 and intday<=31 and inthour<=23 and nmDataGetterTaskId = "+id+" and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00'))>='"+startTime+"' and convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',intHour,':00:00'))<='"+endTime+"' and intHour>="+startHour+" and intHour<="+endHour;
		sortSql = sortSql + "  order by " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}
}
