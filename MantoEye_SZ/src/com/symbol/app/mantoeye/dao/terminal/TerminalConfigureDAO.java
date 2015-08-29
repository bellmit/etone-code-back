package com.symbol.app.mantoeye.dao.terminal;



import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;


@Repository
public class TerminalConfigureDAO extends HibernateDao{	
	
	public Page<CommonSport> query(Page page,String vcTerminalTac,String vcTerminalBrand,String vcTerminalName) {
		String sql = this.buildSql(vcTerminalTac, vcTerminalBrand, vcTerminalName,page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("nmTerminalId", Hibernate.LONG)
				.addScalar("vcTerminalTac",Hibernate.STRING)
				.addScalar("vcTerminalBrand",Hibernate.STRING)
				.addScalar("vcTerminalName",Hibernate.STRING).setResultTransformer(
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
	
	public String buildSql(String vcTerminalTac, String vcTerminalBrand,String vcTerminalName,Page page) {
		String sql = "select nmTerminalId,vcTerminalTac,vcTerminalBrand,vcTerminalName from dtbTerminal where 1=1";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "nmTerminalId";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		if(vcTerminalTac!=null && !vcTerminalTac.equals("")){
			sql = sql +" and vcTerminalTac like '%"+ vcTerminalTac+"%'";
		}
		if(vcTerminalBrand!=null && !vcTerminalBrand.equals("")){
			sql = sql +" and vcTerminalBrand like '%"+ vcTerminalBrand+"%'";
		}
		if(vcTerminalName!=null && !vcTerminalName.equals("")){
			sql = sql +" and vcTerminalName like '%"+ vcTerminalName+"%'";
		}
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(String vcTerminalTac,
			String vcTerminalBrand, String vcTerminalName) {
		String sql = this.buildSql(vcTerminalTac, vcTerminalBrand,
				vcTerminalName, null);
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("nmTerminalId", Hibernate.LONG).addScalar(
						"vcTerminalTac", Hibernate.STRING).addScalar(
						"vcTerminalBrand", Hibernate.STRING).addScalar(
						"vcTerminalName", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class)).list();
		return list;
	}
	
	public boolean terminalUnique(Long nmTerminalId,String vcTerminalTac) {
		String sql = "select count(1) as num from dtbTerminal where vcTerminalTac='"
				+ vcTerminalTac+"'";
		if (nmTerminalId!=null) {
			sql+="and nmTerminalId!="+nmTerminalId;
		}
		Object totalObj = this.getSession().createSQLQuery(sql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if(total==0){
			return true;
		}else{
			return false;
		}
	}
	
	public void saveTerminal(String vcTerminalTac, String vcTerminalBrand,String vcTerminalName) {
		String sql = "insert into dtbTerminal(vcTerminalTac,vcTerminalBrand,vcTerminalName) values('"
				+ vcTerminalTac + "','" + vcTerminalBrand + "','" + vcTerminalName + "')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void updateTerminal(String vcTerminalTac, String vcTerminalBrand,String vcTerminalName,Long nmTerminalId) {
		String sql = "update dtbTerminal set vcTerminalTac='"+vcTerminalTac+"',vcTerminalBrand='"+vcTerminalBrand+"',vcTerminalName='"+vcTerminalName +"' where nmTerminalId="+nmTerminalId;
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void deleteById(String ids) {
		String sql = "delete from dtbTerminal where nmTerminalId in ("+ids+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
}
