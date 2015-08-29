package com.symbol.app.mantoeye.dao.sports;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class CustomerDAO extends HibernateDao {

	public Page<CommonSport> query(final Page page, int timeLevel,
			String sTime, String eTime, String phoneNumber,String CustomerName) {
		String sql = this.buildSql1(phoneNumber, timeLevel, sTime, eTime,CustomerName, page);

		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("statdate", Hibernate.STRING)
				.addScalar("context1",Hibernate.STRING)
				.addScalar("context2",Hibernate.STRING)
				.addScalar("vcNote",Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildList1(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	
	//时间处理
	public List<CommonSport> buildList(List<CommonSport> list) {
		List<CommonSport> csList = new ArrayList<CommonSport>();
		for (int i = 0; i < list.size(); i++) {
			CommonSport commonSport = list.get(i);
			String statdate = commonSport.getStatdate();
			statdate = statdate.substring(0, statdate.indexOf(":"));
			commonSport.setStatdate(statdate+":00");
			csList.add(commonSport);
		}
		return csList;
	}
	
	//时间处理
	public List<CommonSport> buildList1(List<CommonSport> list) {
		List<CommonSport> csList = new ArrayList<CommonSport>();
		for (int i = 0; i < list.size(); i++) {
			CommonSport commonSport = list.get(i);
			String statdate = commonSport.getStatdate();
			statdate = statdate.substring(0, statdate.indexOf(":"));
			commonSport.setStatdate(statdate+":00");
			String context = commonSport.getContext2();
			if (context!=null && !context.equals("")) {
					String[] c = context.split("\\|");
					//commonSport.setContext1(c[2]);
					commonSport.setContext2(c[4]);
				}
			csList.add(commonSport);
		}
		return csList;
	}

	public void saveUpFile(String vcNote,String number) {
		System.out.println("执行入库");
		// String sql = buildInsertSql();
		String sql = " insert into ftbMsisdnList (vcNote,nmMsisdn,intType,dtUpdateTime) values('"
			+vcNote+"','"+ number + "',2,'" + CommonUtils.getCurrentDate() + "')";
		System.out.println(sql);
		this.getSession().createSQLQuery(sql).executeUpdate();
	}


	public List<String> getAllMsisdn() {
		String sqlString = "select nmMsisdn from ftbMsisdnList where intType=2";
		List<String> msisdns = this.getSession().createSQLQuery(sqlString)
				.list();
		// List<FtbMsisdnList> t = getAll();
		// List msisdns=null;
		return msisdns;
	}


	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime.
	 * @param endTime
	 * @return
	 */
	public String buildSql(String phoneNumber, int timeLevel, String sTime,
			String eTime,String CustomerName, Page page) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "statdate";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			 sortColumn = " " + page.getOrderBy() + " ";
		}
//		switch (timeLevel) {
//		case 1:
			sql = "select statdate,right(left(right(context,length(context)-charindex( '|',context)-5),charindex( '|',right(context,length(context)-charindex( '|',context)-5))-1),11) as context1, "
				+ "convert(decimal(5,2),left(right(right(context,length(context)-charindex( '|',context)-5),length(right(context,length(context)-charindex( '|',context)-5))-charindex( '|',right(context,length(context)-charindex( '|',context)-5))),charindex('%',right(right(context,length(context)-charindex( '|',context)-5),length(right(context,length(context)-charindex( '|',context)-5))-charindex( '|',right(context,length(context)-charindex( '|',context)-5))))-1)) as context2"
					+ ",vcNote  from ftbOverView_sport as a left join  ftbMsisdnList as b on context1=right(convert(varchar,b.nmMsisdn),11) and b.intType= (a.intType-11) where a.intType=13"
					+ " and  a.statdate>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  a.statdate<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			sortSql = " order by " + sortColumn +" "+sortType;
//			break;
//		}
		 if (phoneNumber!=null&&!("").equals(phoneNumber)) {
		 sql = sql + " and context1 like '%" + phoneNumber+"%'";
		 }
		 if (CustomerName!=null&&!("").equals(CustomerName))
			 sql = sql + " and b.vcNote like '%" + CustomerName+"%'";
		 sql = sql + sortSql;
		return sql;
	}
	
	public String buildSql1(String phoneNumber, int timeLevel, String sTime,
			String eTime,String CustomerName, Page page) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "statdate";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			 sortColumn = " " + page.getOrderBy() + " ";
		}
//		switch (timeLevel) {
//		case 1:
			sql = "select statdate,right(left(right(context,length(context)-charindex( '|',context)-5),charindex( '|',right(context,length(context)-charindex( '|',context)-5))-1),11) as context1,context as context2"
					+ ",vcNote  from ftbOverView_sport as a left join  ftbMsisdnList as b on context1=right(convert(varchar,b.nmMsisdn),11) and b.intType= (a.intType-11) where a.intType=13"
					+ " and  a.statdate>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  a.statdate<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			sortSql = " order by " + sortColumn +" "+sortType;
//			break;
//		}
		 if (phoneNumber!=null&&!("").equals(phoneNumber)) {
		 sql = sql + " and context1 like '%" + phoneNumber+"%'";
		 }
		 if (CustomerName!=null&&!("").equals(CustomerName))
			 sql = sql + " and b.vcNote like '%" + CustomerName+"%'";
		 sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> listData(int timeLevel, String sTime,
			String eTime, String phoneNumber,String CustomerName) {
		String sql = this.buildSql1(phoneNumber, timeLevel, sTime, eTime,CustomerName, null);
		List<CommonSport> list = this.getSession().createSQLQuery(sql).addScalar("statdate", Hibernate.STRING)
		.addScalar("context1",Hibernate.STRING)
		.addScalar("context2",Hibernate.STRING)
				.addScalar("vcNote",Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(CommonSport.class)).list();
		return buildList1(list);
	}

}
