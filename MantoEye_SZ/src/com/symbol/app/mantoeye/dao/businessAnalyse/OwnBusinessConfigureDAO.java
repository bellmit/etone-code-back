package com.symbol.app.mantoeye.dao.businessAnalyse;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class OwnBusinessConfigureDAO extends HibernateDao{
	
	
	public Page<CommonSport> query(final Page page, String businessName) {
		String sql = this.buildSql(businessName,page);
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
	
	public List<CommonSport> listData(String businessName) {
		String sql = this.buildSql(businessName,null);
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		return buildBeanList(list);
	}
	
	
	public void deleteBusiness(String ids) {
		String deleteSql = "delete from  ftbBussList where nmBussListId in ("+ids+")";
		String deleteSql1 = "delete from ftbBussRivalList where nmBussinessId in ("+ids+")";
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
		this.getSession().createSQLQuery(deleteSql1).executeUpdate();
	}
	
	public void saveBussList(Long businessId,String vcNote) {
		String sql = "insert into ftbBussList(nmBussinessId,vcNote) values("+businessId+",'"+vcNote+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public String buildSql(String businessName,
			Page page) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmBussListId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select b.nmBussListId,a.vcDimensName as businessName,b.vcNote,a.nmDimensId from dtbGroupTree a,ftbBussList b where a.nmDimensId=b.nmBussinessId and nmTypeId=1";
		if (businessName!=null && !businessName.equals("")) {
			sql = sql + " and a.vcDimensName like '%"+businessName.trim()+"%'";
		}
		sortSql = sortSql + " order by  " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}
	

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonSport> buildBeanList(List list) {
		List<CommonSport> commonSportList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			commonSportList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] objs = (Object[]) list.get(i);
				commonSport.setId(Common.StringToLong(objs[0] + ""));
				commonSport.setBusinessName(objs[1] + "");
				commonSport.setVcNote(objs[2] + "");
				commonSport.setBusinessId(Common.StringToLong(objs[3] + ""));
				commonSportList.add(commonSport);
			}
		}
		return commonSportList;
	}


}
