package com.symbol.app.mantoeye.dao.sports;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/*
 * 区域导入
 */

@Repository
public class AreaMapCellDAO extends HibernateDao{	
	
	public Page<CommonSport> query(Page page,String vcAreaName,String vcCgiName,Integer intType,String vcCGI) {
		String sql = this.buildSql(vcAreaName, vcCgiName,page,intType,vcCGI);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG)
				.addScalar("dtUpdateTime",Hibernate.DATE)
				.addScalar("vcAreaName",Hibernate.STRING)
				.setResultTransformer(
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
	
	public Page<CommonSport> queryCGI(Page page,Long id) {
		String sql = this.buildCGISql(id,page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG)
				.addScalar("vcCgiName",Hibernate.STRING)
				.addScalar("vcCGI",Hibernate.STRING)
				.setResultTransformer(
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
	
	public String buildCGISql(Long id,Page page) {
		String sql = "select a.nmAreaMapCellId as id,c.vcCgiChName as vcCgiName,a.vcCgi as vcCGI from ftbAreaMapCell a,ftbCgi c where a.vcCgi=c.vcCGI ";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "id";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = sql + " and a.nmAreaId="+id;
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	public String buildSql(String vcAreaName,String vcCgiName,Page page,Integer intType,String vcCGI) {
		String sql = "select nmAreaId as id,dtUpdateTime,vcAreaName from ftbArea where 1=1 ";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "dtUpdateTime";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		if(vcAreaName!=null && !vcAreaName.equals("")){
			sql = sql +" and vcAreaName like '%"+ vcAreaName+"%'";
		}
		sql = sql + " and intType="+intType;
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	public String buildExtportSql(String vcAreaName,String vcCgiName,Page page,Integer intType,String vcCGI) {
		String sql = "select a.nmAreaId as id,a.dtUpdateTime,a.vcAreaName,c.vcCgiChName as vcCgiName,c.vcCGI as vcCGI from ftbArea a "
						+" left join ftbAreaMapCell b on a.nmAreaId=b.nmAreaId "
						+" left join ftbCgi c on b.vcCGI = c.vcCGI where 1=1";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "dtUpdateTime";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		if(vcAreaName!=null && !vcAreaName.equals("")){
			sql = sql +" and a.vcAreaName like '%"+ vcAreaName+"%'";
		}
		if(vcCgiName!=null && !vcCgiName.equals("")){
			sql = sql +" and (c.vcCgiChName like '%"+ vcCgiName.toLowerCase()+"%' or c.vcCgiChName like '%"+vcCgiName.toUpperCase()+"%')";
		}
		if(vcCGI!=null && !vcCGI.equals("")){
			sql = sql +" and c.vcCGI like '%"+ vcCGI+"%'";
		}
		sql = sql + " and a.intType="+intType;
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(String vcAreaName,String vcCgiName,Integer intType,String vcCGI) {
		String sql = this.buildExtportSql(vcAreaName, vcCgiName, null,intType,vcCGI);
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG)
				.addScalar("dtUpdateTime",Hibernate.DATE)
				.addScalar("vcAreaName",Hibernate.STRING)
				.addScalar("vcCgiName",Hibernate.STRING)
				.addScalar("vcCGI",Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class)).list();
		return list;
	}
	
	public void insertArea(String vcAreaName, int intType,String dtUpdateTime) {
		String sql = "insert into ftbArea(vcAreaName,intType,dtUpdateTime) values('"+vcAreaName+"',"+intType+",'"+dtUpdateTime+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void updateArea(Long id,String vcAreaName, String dtUpdateTime) {
		String sql = "update ftbArea set vcAreaName="+vcAreaName+",dtUpdateTime='"+dtUpdateTime +"'where nmAreaId="+id;
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void deleteById(String ids,int intType) {
		String sql = "delete from ftbArea where intType="+intType+" and nmAreaId in ("+ids+")";
		String sql2 = "delete from ftbAreaMapCell where nmAreaId in ("+ids+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
		this.getSession().createSQLQuery(sql2).executeUpdate();
	}
	
	public void deleteAreaMapCell(String ids) {
		String sql = "delete from ftbAreaMapCell where nmAreaMapCellId in ("+ids+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public List queryCgi(String vcCgiChName) {
		String sql = "select vcCGI from ftbCgi where lcase(vcCgiChName) ="+ vcCgiChName +" or ucase(vcCgiChName)="+vcCgiChName;
		return this.getSession().createSQLQuery(sql).list();		
	}
	
	public List queryByVcCgi(String vcCGI) {
		String sql = "select vcCGI from ftbCgi where vcCGI ='"+ vcCGI+"'";
		return this.getSession().createSQLQuery(sql).list();		
	}
	
	
	public List queryArea(String vcAreaName,int intType) {
		String sql = "select nmAreaId from ftbArea where intType="+intType +" and vcAreaName ='"+ vcAreaName+"'";
		return this.getSession().createSQLQuery(sql).list();		
	}
	
	public boolean queryAreaMapCell(Long nmAreaId,String vcCGI) {
		String sql = "select count(1) as num from ftbAreaMapCell where nmAreaId="+nmAreaId+" and vcCGI ='"+ vcCGI+"'";
		Object totalObj = this.getSession().createSQLQuery(sql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if(total==0){
			return true;
		}else{
			return false;
		}		
	}
	
	public void insertAreaMapCell(Long nmAreaId,String vcCGI) {
		String sql = "insert into ftbAreaMapCell(nmAreaId,vcCGI) values("+nmAreaId+",'"+ vcCGI+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();		
	}
	
	
}
