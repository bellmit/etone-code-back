package com.symbol.app.mantoeye.dao.businessRules;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class BusinessSearchDAO extends HibernateDao{
	
	
	public Page<CommonSport> query(final Page page, String vcTitleType,String vcTitle) {
		String sql = this.buildSql(vcTitleType,vcTitle,page);
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
	
	public List<CommonSport> listData(String vcTitleType,String vcTitle) {
		String sql = this.buildSql(vcTitleType,vcTitle,null);
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		return buildBeanList(list);
	}
	
	
	public void deleteBusinessSearch(String ids) {
		String deleteSql = "delete from  ftbBusinessSearchVetor where nmBussinessSearchId in ("+ids+")";
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	public void saveBusinessSearch(String vcTitleType,String vcTitle,String vcKeyword) {
		String sql = "insert into ftbBusinessSearchVetor(vcTitleType,vcTitle,vcKeyword) values('"+vcTitleType+"','"+vcTitle+"','"+vcKeyword+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public String buildSql(String vcTitleType,String vcTitle,
			Page page) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmBussinessSearchId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select nmBussinessSearchId,vcTitleType,vcTitle,vcKeyword from ftbBusinessSearchVetor where 1=1 ";
		if (vcTitleType!=null && !vcTitleType.equals("")) {
			sql = sql + " and vcTitleType like '%"+vcTitleType.trim()+"%'";
		}
		if (vcTitle!=null && !vcTitle.equals("")) {
			sql = sql + " and vcTitle like '%"+vcTitle.trim()+"%'";
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
				commonSport.setVcTitleType(objs[1] + "");
				commonSport.setVcTitle(objs[2] + "");
				commonSport.setVcKeyword(objs[3] + "");
				commonSportList.add(commonSport);
			}
		}
		return commonSportList;
	}


}
