package com.symbol.app.mantoeye.dao.sports;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/*
 * 重要客户号码
 */

@Repository
public class ImpCustomerNumDAO extends HibernateDao{	
	
	public Page<CommonSport> query(Page page,String sTime,String eTime,String nmMsisdn) {
		String sql = this.buildSql(sTime, eTime, nmMsisdn,page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("dtUpdateTime",
						Hibernate.DATE).addScalar("nmMsisdn",
						Hibernate.STRING).setResultTransformer(
						Transformers.aliasToBean(CommonSport.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(list);
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	
	public String buildSql(String sTime, String eTime,String nmMsisdn,Page page) {
		String sql = "select nmMsisdnListId as id,dtUpdateTime,right(convert(varchar,nmMsisdn),11) as nmMsisdn,intType from ftbMsisdnList where intType=1";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "dtUpdateTime";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		if(sTime!=null && !sTime.equals("")){
			sql = sql +" and convert(date,dtUpdateTime) >= "+ MantoEyeUtils.formatData(sTime, 2);
		}
		if(eTime!=null && !eTime.equals("")){
			sql = sql +" and convert(date,dtUpdateTime) <="+ MantoEyeUtils.formatData(eTime, 2);
		}
		if(nmMsisdn!=null && !nmMsisdn.equals("")){
			sql = sql +" and convert(varchar,nmMsisdn) like '%"+ nmMsisdn+"%'";
		}
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(String sTime, String eTime,
			String nmMsisdn) {
		String sql = this.buildSql(sTime, eTime, nmMsisdn, null);
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("dtUpdateTime",
						Hibernate.DATE).addScalar("nmMsisdn", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class)).list();
		return list;
	}
	
	public boolean nmMsisdnUnique(String nmMsisdn) {
		String sql = "select count(1) as num from ftbMsisdnList where intType=1 and nmMsisdn="
				+ nmMsisdn;
		Object totalObj = this.getSession().createSQLQuery(sql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if(total==0){
			return true;
		}else{
			return false;
		}
	}
	
	public void saveNmMsisdn(Long nmMsisdn, String dtUpdateTime, Integer intType) {
		String sql = "insert into ftbMsisdnList(dtUpdateTime,nmMsisdn,intType) values('"
				+ dtUpdateTime + "'," + nmMsisdn + "," + intType + ")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void updateNmMsisdn(Long id,Long nmMsisdn, String dtUpdateTime) {
		String sql = "update ftbMsisdnList set nmMsisdn="+nmMsisdn+",dtUpdateTime='"+dtUpdateTime +"'where nmMsisdnListId="+id;
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void deleteById(String ids) {
		String sql = "delete from ftbMsisdnList where nmMsisdnListId in ("+ids+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
}
