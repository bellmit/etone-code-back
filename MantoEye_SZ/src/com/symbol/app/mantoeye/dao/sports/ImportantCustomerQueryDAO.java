package com.symbol.app.mantoeye.dao.sports;



import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbArea;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.modules.orm.Page;

/*
 * 重要客户
 */

@Repository
public class ImportantCustomerQueryDAO extends HibernateQueryDAO{

	/**
	 * 分页查询
	 * 
	 * @param page
	 * 
	 *            TD标识
	 * @param timeLevel
	 *            时间粒度 1:小时 2:天 3:周 4:月
	 * @return
	 */
	public Page<CommonSport> query(final Page page, String common_search, int timeLevel,
			String sTime, String eTime,Integer intType,Integer dataType) {
		String sql = this.buildSql(common_search, timeLevel, sTime, eTime, page,intType,dataType);
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
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(String common_search, int timeLevel, String sTime,
			String eTime,Integer intType) {
		int dataType=2;
		String sql = this.buildSql(common_search, timeLevel, sTime, eTime, null,intType,dataType);
		List<CommonSport> list = this.getSession().createSQLQuery(sql).addScalar("statdate", Hibernate.STRING)
									.addScalar("context1",Hibernate.STRING)
									.addScalar("context2",Hibernate.STRING)
									.setResultTransformer(Transformers.aliasToBean(CommonSport.class)).list();
		return buildList(list);
	}

	public List<CommonSport> listData1(String common_search, int timeLevel, String sTime,
			String eTime,Integer intType) {
		int dataType=2;
		String sql = this.buildSql1(common_search, timeLevel, sTime, eTime, null,intType,dataType);
		List<CommonSport> list = this.getSession().createSQLQuery(sql).addScalar("statdate", Hibernate.STRING)
									.addScalar("context",Hibernate.STRING)
									.addScalar("detail",Hibernate.STRING)
									.setResultTransformer(Transformers.aliasToBean(CommonSport.class)).list();
		return buildList1(list,intType,dataType);
	}
	
	public List<CommonSport> listDataTerminalByBusiness(String terminalProtectedArea,String terminalTimeSearch,Integer intType) {
		int dataType=2;
		String sql = this.buildTerminalByBusinessSql(terminalProtectedArea, terminalTimeSearch, null,intType,dataType);
		List<CommonSport> list = this.getSession().createSQLQuery(sql).addScalar("statdate", Hibernate.STRING)
									.addScalar("context",Hibernate.STRING)
									.addScalar("detail",Hibernate.STRING)
									.setResultTransformer(Transformers.aliasToBean(CommonSport.class)).list();
		return buildList1(list,intType,dataType);
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
	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String buildSql(String common_search, int timeLevel, String sTime, String eTime,
			Page page,Integer intType,Integer dataType) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "statdate";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (timeLevel) {
		case 1:
			if(intType!=11){
				sql = "select statdate,right(left(right(context,length(context)-charindex( '|',context)-5),charindex( '|',right(context,length(context)-charindex( '|',context)-5))-1),11) as context1, "
					+ "convert(decimal(5,2),left(right(right(context,length(context)-charindex( '|',context)-5),length(right(context,length(context)-charindex( '|',context)-5))-charindex( '|',right(context,length(context)-charindex( '|',context)-5))),charindex('%',right(right(context,length(context)-charindex( '|',context)-5),length(right(context,length(context)-charindex( '|',context)-5))-charindex( '|',right(context,length(context)-charindex( '|',context)-5))))-1)) as context2"
					+ " from ftbOverView_sport "
					+ " where intType="+intType;
			}
			else{
				if (dataType==1) {
					sql = "select statdate,left(right(context,length(context)-charindex( '|',context)),charindex( '|',right(context,length(context)-charindex( '|',context)))-1) as context1,"
						+ "right(right(context,length(context)-charindex( '|',context)),length(right(context,length(context)-charindex( '|',context)))-charindex( '|',right(context,length(context)-charindex( '|',context)))) as context2 " 
						+ " from ftbOverView_sport"
						+ " where intType="
						+ intType;
				}else {
					sql = "select statdate,left(right(context,length(context)-charindex( '|',context)),charindex( '|',right(context,length(context)-charindex( '|',context)))-1) as context1,"
					+"convert(varchar,detail) as context2"//注意字符串转换，linux系统不支持longvarchar类型，不然查询出错。
						+ " from ftbOverView_sport"
						+ " where intType="
						+ intType;
				}
				
			}
			
			sortSql = " order by " + sortColumn +" "+sortType;
			if(sTime!=null && !sTime.equals("")){
				sql = sql+ " and  statdate>="+ MantoEyeUtils.formatData(sTime, timeLevel);
			}
			if(eTime!=null && !eTime.equals("")){
				sql = sql+" and  statdate<="+ MantoEyeUtils.formatData(eTime, timeLevel);
			}
			break;
		}
		if(common_search!=null && !common_search.equals("")){
			sql = sql +" and context1 like '%"+common_search+"%'";
		}
		sql = sql + sortSql;
		return sql;
	}
	/**
	 * 查询所有
	 */
	public List getAllMsisdn() {
		String sql = "select nmMsisdn from ftbMsisdnList where intType=1";
		List msisdnList = this.getSession().createSQLQuery(sql).list();
		return msisdnList;
	}
	
	/**
	 * 查询所有区域
	 */
	public List<FtbArea> queryAllArea(int intType) {
		String sql = "select * from ftbArea where intType="+intType;
		List<FtbArea> aList = this.getSession().createSQLQuery(sql).addEntity(FtbArea.class).list();
		return aList;
	}
	

	
	/**
	 * 查找刚插入保障区域
	 */
	public List<FtbArea> queryArea(String vcAreaName) {
		String sql = "select * from  ftbArea where intType=1 and vcAreaName='"+vcAreaName+"'";
		List<FtbArea> aList=this.getSession().createSQLQuery(sql).addEntity(FtbArea.class).list();
		return aList;
	}
	
	/**
	 * 查询所有小区
	 */
	public List queryCgi() {
		String sql = "select vcCGI,vcCgiChName from ftbCgi";
		List aList = this.getSession().createSQLQuery(sql).list();
		return aList;
	}
	
	/**
	 * 查询ftbAreaMapCell 是否已存在
	 */
	public boolean cgiUniqe(String cgi,String cgiName) {
		String sql = "select count(1) from ftbCgi where vcCGI='"+cgi+"' and vcCgiChName='"+cgiName+"'";
		Object totalObj = this.getSession().createSQLQuery(sql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if (total==0) {
			return true;
		}else{
			return false;
		}		
	}
	
	
	
	/**
	 * 查询ftbAreaMapCell 是否已存在
	 */
	public boolean areaMapCellUniqe(Long nmAreaId,String vcCGI) {
		String sql = "select count(1) from ftbAreaMapCell where nmAreaId="+nmAreaId+" and vcCGI='"+vcCGI+"'";
		Object totalObj = this.getSession().createSQLQuery(sql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if (total==0) {
			return true;
		}else{
			return false;
		}		
	}
	

	
	/**
	 * 查询所有的ftbAreaMapCell
	 */
	public List queryAreaMapCell() {
		String sql = "select nmAreaId,vcCGI from ftbAreaMapCell";
		List list = this.getSession().createSQLQuery(sql).list();	
		return list;
	}
	
	
	
	
	
	
	
	public Page<CommonSport> query1(final Page page, String common_search, int timeLevel,
			String sTime, String eTime,Integer intType,Integer dataType) {
		String sql = this.buildSql1(common_search, timeLevel, sTime, eTime, page,intType,dataType);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("statdate", Hibernate.STRING)
				.addScalar("context1",Hibernate.STRING)
				.addScalar("context",Hibernate.STRING)
				.addScalar("detail",Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildList1(list,intType,dataType));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	
	public Page<CommonSport> queryTerminal(final Page page, String common_search, int timeLevel,
			String sTime, String eTime,Integer intType,Integer dataType) {
		String sql = this.buildTerminalSql(common_search, timeLevel, sTime, eTime, page,intType,dataType);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("statdate", Hibernate.STRING)
				.addScalar("context",Hibernate.STRING)
				.addScalar("detail",Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildList1(list,intType,dataType));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	
	public List<CommonSport> listDataTerminal(String common_search, int timeLevel, String sTime,
			String eTime,Integer intType) {
		int dataType=2;
		String sql = this.buildTerminalSql(common_search, timeLevel, sTime, eTime, null,intType,dataType);
		List<CommonSport> list = this.getSession().createSQLQuery(sql).addScalar("statdate", Hibernate.STRING)
									.addScalar("context",Hibernate.STRING)
									.addScalar("detail",Hibernate.STRING)
									.setResultTransformer(Transformers.aliasToBean(CommonSport.class)).list();
		return buildList1(list,intType,dataType);
	}
	
	public Page<CommonSport> queryTerminalByBusiness(final Page page, String terminalProtectedArea, String terminalTimeSearch, Integer intType,Integer dataType) {
		String sql = this.buildTerminalByBusinessSql(terminalProtectedArea, terminalTimeSearch, page,intType,dataType);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("statdate", Hibernate.STRING)
				.addScalar("context1",Hibernate.STRING)
				.addScalar("context",Hibernate.STRING)
				.addScalar("detail",Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(CommonSport.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildList1(list,intType,dataType));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	
	
	public String buildSql1(String common_search, int timeLevel, String sTime, String eTime,
			Page page,Integer intType,Integer dataType) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "statdate";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (timeLevel) {
		case 1:
				sql = "select statdate,right(left(right(context,length(context)-charindex( '|',context)-5),charindex( '|',right(context,length(context)-charindex( '|',context)-5))-1),11) as context1,context,detail "
					+ " from ftbOverView_sport "
					+ " where intType="+intType;
			
			sortSql = " order by " + sortColumn +" "+sortType;
			if(sTime!=null && !sTime.equals("")){
				sql = sql+ " and  statdate>="+ MantoEyeUtils.formatData(sTime, timeLevel);
			}
			if(eTime!=null && !eTime.equals("")){
				sql = sql+" and  statdate<="+ MantoEyeUtils.formatData(eTime, timeLevel);
			}
			break;
		}
		if(common_search!=null && !common_search.equals("")){
			sql = sql +" and context like '%"+common_search+"%'";
		}
		sql = sql + sortSql;
		return sql;
	}
	
	public String buildTerminalSql(String common_search, int timeLevel, String sTime, String eTime,
			Page page,Integer intType,Integer dataType) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "statdate";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (timeLevel) {
		case 1:
				sql = "select statdate,context,detail "
					+ " from ftbOverView_sport "
					+ " where intType="+intType;
			
			sortSql = " order by " + sortColumn +" "+sortType;
			if(sTime!=null && !sTime.equals("")){
				sql = sql+ " and  statdate>="+ MantoEyeUtils.formatData(sTime, timeLevel);
			}
			if(eTime!=null && !eTime.equals("")){
				sql = sql+" and  statdate<="+ MantoEyeUtils.formatData(eTime, timeLevel);
			}
			break;
		}
		if(common_search!=null && !common_search.equals("")){
			sql = sql +" and context like '%"+common_search+"%'";
		}
		sql = sql + sortSql;
		return sql;
	}
	
	public String buildTerminalByBusinessSql(String terminalProtectedArea, String terminalTimeSearch,
			Page page,Integer intType,Integer dataType) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "statdate";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select statdate,right(left(right(context,length(context)-charindex( '|',context)-5),charindex( '|',right(context,length(context)-charindex( '|',context)-5))-1),11) as context1,context,detail "
					+ " from ftbOverView_sport "
					+ " where intType="+intType;
			
			sortSql = " order by " + sortColumn +" "+sortType;
			if(terminalTimeSearch!=null && !terminalTimeSearch.equals("")){
				sql = sql+ " and  statdate ="+ MantoEyeUtils.formatData(terminalTimeSearch, 1);
			}
		if(terminalProtectedArea!=null && !terminalProtectedArea.equals("")){
			sql = sql +" and context like '%"+terminalProtectedArea+"%'";
		}
		sql = sql + sortSql;
		return sql;
	}
	
	//时间处理
	public List<CommonSport> buildList1(List<CommonSport> list,Integer intType,Integer dataType) {
		List<CommonSport> csList = new ArrayList<CommonSport>();
		for (int i = 0; i < list.size(); i++) {
			CommonSport commonSport = list.get(i);
			String statdate = commonSport.getStatdate();
			statdate = statdate.substring(0, statdate.indexOf(":"));
			commonSport.setStatdate(statdate+":00");
			String context = commonSport.getContext();
			if (intType==12) {
				if (context!=null && !context.equals("")) {
					String[] c = context.split("\\|");
					//commonSport.setContext1(c[2]);
					commonSport.setContext2(c[4]);
				}
			}else {
				if (context!=null && !context.equals("")) {
					String[] c = context.split("\\|");
					commonSport.setContext1(c[1]);
					commonSport.setContext2(commonSport.getDetail());
				}
			}
			csList.add(commonSport);
		}
		return csList;
	}
}
