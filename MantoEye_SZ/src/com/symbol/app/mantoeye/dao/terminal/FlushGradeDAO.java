package com.symbol.app.mantoeye.dao.terminal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class FlushGradeDAO extends HibernateDao {

	public Page<CommonSport> query(final Page page,int bitType) {
		String sql = this.buildSql( page,bitType);
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


	public List<CommonSport> buildBeanList(List list) {
		List<CommonSport> fcList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			fcList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);		
				commonSport.setId(Common.StringToLong(bean[0]+""));
				if (Common.StringToInt(bean[1]+"")==0) {
					commonSport.setBitType("天");
				}else {
					commonSport.setBitType("月");
				}
				commonSport.setIntDownFlush(Common.StringToLong(bean[2]+"")/1024);
				if (Common.StringToLong(bean[3]+"")==-1) {
					commonSport.setIntUpFlush(-1l);
				}else {
					commonSport.setIntUpFlush(Common.StringToLong(bean[3]+"")/1024);
				}
				fcList.add(commonSport);
			}
		}
		return fcList;
	}
	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 *            .
	 * @param endTime
	 * @return
	 */
	public String buildSql( Page page,int bitType) {
		String sql = "";
		String sortSql = "";
		String sortType = " asc ";
		String sortColumn = "nmFlushConfigureId";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select nmFlushConfigureId,bitType,intDownFlush,intUpFlush from dtbFlushConfigure where bitType="+bitType;
		sortSql = " order by " + sortColumn + " " + sortType;
		sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> listData() {
		String sql =  null;
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list);
	}

	public int findByMsisdn(String nmFlushLevel) {
		String sqlString = "select count(1) from  TerminalFlushLevel where  nmFlushLevel='"
				+ nmFlushLevel+"'";
		Object objcount = this.getSession().createSQLQuery(sqlString)
				.uniqueResult();
		Integer count = Integer.parseInt(null == objcount.toString()
				|| "".equals(objcount.toString()) ? "0" : objcount.toString());
		return count;
	}

	
	
	public List findByBitType(int bitType){
		String sql="select nmFlushConfigureId,bitType,intDownFlush,intUpFlush from dtbFlushConfigure where bitType="+bitType+" order by intDownFlush desc";
		List<String> list=this.getSession().createSQLQuery(sql).list();
		return list;
	}
	
	public void deleteByKeys(String ids) {
		String sql = "delete from dtbFlushConfigure where nmFlushConfigureId in ("+ ids + ")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void saveFlushConfigure(int bitType, int intDownFlush, int intUpFlush) {

		String sql = "insert into dtbFlushConfigure(bitType,intDownFlush,intUpFlush) values("+bitType+","+intDownFlush+","+intUpFlush+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	



}
