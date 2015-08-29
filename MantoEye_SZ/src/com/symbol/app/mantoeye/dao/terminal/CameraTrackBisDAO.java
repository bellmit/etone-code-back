package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class CameraTrackBisDAO extends HibernateDao {

	public Page<CommonSport> query(final Page page, String terminal) {
		String sql = this.buildSql(terminal, page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql).setFirstResult(page.getFirst())
		.setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(String terminal) {
		String sql = this.buildSql(terminal, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list);
	}

	
	public List<CommonSport> buildBeanList(List list) {
		List<CommonSport> CustomerList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			CustomerList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);		
			commonSport.setStatdate(bean[0].toString());
			commonSport.setId(Common.StringToLong(bean[1].toString()));
			commonSport.setBusiness(bean[2].toString());
			commonSport.setNmUsers(Common.StringToLong(bean[4].toString()));
	commonSport.setIntUpFlush(Common.StringToLong(bean[5].toString()));
	commonSport.setIntDownFlush(Common.StringToLong(bean[6].toString()));
	commonSport.setTotalFlush(Common.StringToLong(bean[3].toString()));
commonSport.setTask(bean[7].toString());
	commonSport.calculateData1();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	public String buildSql(String terminal,Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " desc ";
		String sortType = " asc ";
		 String sortColumn = " statdate";
		 String gropsqlString="";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
	
			sql = "select statdate,id,bisname,nmFlush,nmUsers,nmUpflush,nmDownflush,task"
					+ " from Cameratrackbis where 1=1 ";
			if (terminal!=null&&!("").equals(terminal)) 
				sql=sql+" and task ='"+terminal+"'";
		
			defaultSortSql = " order by statdate " + defaultSortType;				
		sql=sql+gropsqlString;
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

}
