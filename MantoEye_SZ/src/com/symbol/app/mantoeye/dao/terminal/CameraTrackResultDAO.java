package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDaoOld;


@Repository
public class CameraTrackResultDAO extends HibernateDaoOld {
	
	
	// ///////////////////////////////////【通用拍照】区域分析流量表/////////////////////////////////////
	public Page<CommonSport> queryAreaAnalyse(final Page page, long taskId,int areaType, String taskStartTime, long intTaskStatus, int dataType) {
		long preId = findTaskPreId(taskId, taskStartTime, intTaskStatus);
		String tableName="ftbStatSpeAreaFlush_";
		Integer total   ;
		List list =null;
		String tableSql="select 1 from sys.systable where table_name='"+tableName+preId+"'";
		List tableList=this.getSession().createSQLQuery(tableSql).list();
		if(tableList==null||tableList.isEmpty())
			total=0;
		else{
		String sql = this.buildAreaAnalyseSql(preId,areaType, page, dataType);		
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		 total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		 list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		}
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildAreaAnalyseList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	
	
	/**	V3，通过查找中间表ftbDataOutPutTaskPre找到preId
	 * @param taskId
	 * @param taskStartTime
	 * @return
	 */
	public long findTaskPreId(long taskId, String taskStartTime, long intTaskStatus){
		StringBuilder sql = new StringBuilder("select nmDataOutPutTaskPreId from ftbDataOutPutTaskPre where 1=1 ");
		sql.append(" and nmDataOutPutTaskId = " + taskId);
		sql.append(" and intTaskStatus = 2");
		sql.append(" and dtDataTime = '" + Common.getFormateHour(taskStartTime) + "'");
		
		long preId = 0L;
		Object taskPre = this.getSession().createSQLQuery(sql.toString()).uniqueResult();
		if(taskPre != null){
			preId = Long.parseLong(null == taskPre.toString()
					|| "".equals(taskPre.toString()) ? "0" : taskPre.toString());
		}
		return preId;
	}
	
	public boolean isExistTable(String tableName, long preId){
		String tableSql="select 1 from sys.systable where table_name='"+tableName+preId+"'";
		List tableList=this.getSession().createSQLQuery(tableSql).list();
		if(tableList != null && !tableList.isEmpty()){
			return true;
		}
		return false;
	}
	

	public String buildAreaAnalyseSql(long taskId,int areaType, Page page, int dataType) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmStatSpeAreaId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select intAreaId,intAreaType,nmUpFlush,nmDownFlush,(nmUpFlush+nmDownFlush) AS sumFlush,nmUsers,dtStatTime from ftbStatSpeAreaFlush_"+ taskId+" where intAreaType="+areaType;
		sql += " and intRaitype = " + dataType;
		sortSql = " order by  " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> buildAreaAnalyseList(List list) {
		List<CommonSport> CustomerList = new ArrayList<CommonSport>();
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				int areaId = Common.StringToInt(bean[0] + "");
				int areaType = Common.StringToInt(bean[1] + "");
				String areaName = findArea(areaType, areaId);
				commonSport.setVcAreaName(areaName);
				commonSport.setNmAreaId(Common.StringToLong(bean[0] + ""));// 区域ID
				commonSport.setDataType(Common.StringToInt(bean[1] + ""));// 区域类型ID
				commonSport.setIntUpFlush(Common.StringToLong(bean[2] + ""));
				commonSport.setIntDownFlush(Common.StringToLong(bean[3] + ""));
				commonSport.setTotalFlush(Common.StringToLong(bean[4] + ""));
				commonSport.setNmUsers(Common.StringToLong(bean[5] + ""));
				commonSport.setFullDate(bean[6] + "");
				commonSport.calculateData1();
				commonSport.convertFullDate();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	/*
	 * 根據區域類型ID以及區域ID查找区域名称
	 */
	public String findArea(int areaType, int areaId) {
		String sql = "";
		// 0：全网 1：bsc类型 2：街道类型 3：分公司类型 4：营销片区 5：sgsn类型
		switch (areaType) {
		case 1:
			sql = "select  vcName from dtbBsc where intBscId=" + areaId;
			break;
		case 5:
			sql = "select  vcName from dtbGsn where intSgsnId=" + areaId;
			break;
		case 2:
			sql = "select  vcName from dtbStreet where intStreetId=" + areaId;
			break;
		case 4:
			sql = "select vcSaleAreaName from dtbSaleArea where intSaleAreaId="
					+ areaId;
			break;
		case 3:
			sql = "select vcBranchName from dtbSubsidiaryCompany where intBranchId="
					+ areaId;
			break;
		}
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null)
			return list.get(0).toString();
		else
			return null;

	}

	public List<CommonSport> AreaAnalyselistData(long taskId,int areaType, String taskStartTime, long intTaskStatus, int dataType) {
		long preId = findTaskPreId(taskId, taskStartTime, intTaskStatus);
		String sql = "";
		sql = this.buildAreaAnalyseSql(preId,areaType, null, dataType);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildAreaAnalyseList(list);
	}

	// ///////////////////////////////////【通用拍照】业务分析流量表/////////////////////////////////////
	public Page<CommonSport> queryBisAnalyse(final Page page, long taskId, String taskStartTime, long intTaskStatus, int dataType) {
		long preId = findTaskPreId(taskId, taskStartTime, intTaskStatus);
		String tableName="ftbStatSpeBussFlush_";
		Integer total   ;
		List list =null;
		String tableSql="select 1 from sys.systable where table_name='"+tableName+preId+"'";
		List tableList=this.getSession().createSQLQuery(tableSql).list();
		if(tableList==null||tableList.isEmpty())
			total=0;
		else{
		String sql = this.buildBisAnalyseSql(preId, page, dataType);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		 total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		 list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		}
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBisAnalyseList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public String buildBisAnalyseSql(long taskId, Page page, int dataType) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " sumFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select s.nmBussinessId,dtbGroupTree.vcDimensName,nmUpFlush,nmDownFlush,(nmUpFlush+nmDownFlush) AS sumFlush,nmUsers,dtStatTime from ftbStatSpeBussFlush_"+ taskId
				+" s left join dtbGroupTree on dtbGroupTree.nmDimensId=s.nmBussinessId and (dtbGroupTree.nmTypeId=1 or dtbGroupTree.nmTypeId is null)";
		sql += " where s.intRaitype=" + dataType;

		sortSql = " order by  " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> buildBisAnalyseList(List list) {
		List<CommonSport> CustomerList = new ArrayList<CommonSport>();
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				commonSport.setBusinessId(Common.StringToLong(bean[0] + ""));
				commonSport.setBusiness(bean[1]+ "");// 业务名称
				commonSport.setIntUpFlush(Common.StringToLong(bean[2] + ""));
				commonSport.setIntDownFlush(Common.StringToLong(bean[3] + ""));
				commonSport.setTotalFlush(Common.StringToLong(bean[4] + ""));
				commonSport.setNmUsers(Common.StringToLong(bean[5] + ""));
				commonSport.setFullDate(bean[6] + "");
				commonSport.calculateData1();
				commonSport.convertFullDate();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	public String FindBisById(long bisid) {
		String sql = "select vcDimensName from dtbGroupTree where nmTypeId=1 and nmDimensId="
				+ bisid;
		List list = this.getSession().createSQLQuery(sql).list();
		if (list == null || list.isEmpty()) {
			return null;
		} else {

			return list.get(0).toString();
		}
	}

	public List<CommonSport> bisAnalyselistData(long taskId, String taskStartTime, long intTaskStatus, int dataType) {
		long preId = findTaskPreId(taskId, taskStartTime, intTaskStatus);
		String sql = "";
		sql = this.buildBisAnalyseSql(preId, null, dataType);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBisAnalyseList(list);
	}

	// ///////////////////////////////////【通用拍照】终端分析流量表/////////////////////////////////////
	public Page<CommonSport> queryTerminalAnalyse(final Page page, long taskId, String taskStartTime, long intTaskStatus, int dataType) {
		long preId = findTaskPreId(taskId, taskStartTime, intTaskStatus);
		String tableName="ftbStatSpeTerminalFlush_";
		Integer total   ;
		List list =null;
		String tableSql="select 1 from sys.systable where table_name='"+tableName+preId+"'";
		List tableList=this.getSession().createSQLQuery(tableSql).list();
		if(tableList==null||tableList.isEmpty())
			total=0;
		else{
		String sql = this.buildTerminalAnalyseSql(preId, page, dataType);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		 total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		 list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		}
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildTerminalAnalyseList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public String buildTerminalAnalyseSql(long taskId, Page page, int dataType) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmStatSpeTerminalId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select nmTerminalId,nmUpFlush,nmDownFlush,(nmUpFlush+nmDownFlush) AS sumFlush,nmUsers,dtStatTime from ftbStatSpeTerminalFlush_"+ taskId;
		sql += " where intRaitype=" + dataType;
		sortSql = " order by  " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> buildTerminalAnalyseList(List list) {
		List<CommonSport> CustomerList = new ArrayList<CommonSport>();
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				commonSport.setNmTerminalId(Common.StringToLong(bean[0] + ""));
				commonSport.setVcTerminalName(findTerminalById(Common
						.StringToLong(bean[0] + "")));// 终端名称
				commonSport.setIntUpFlush(Common.StringToLong(bean[1] + ""));
				commonSport.setIntDownFlush(Common.StringToLong(bean[2] + ""));
				commonSport.setTotalFlush(Common.StringToLong(bean[3] + ""));
				commonSport.setNmUsers(Common.StringToLong(bean[4] + ""));
				commonSport.setFullDate(bean[5] + "");
				commonSport.calculateData1();
				commonSport.convertFullDate();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	public List<CommonSport> terminalAnalyselistData(long taskId, String taskStartTime, long intTaskStatus, int dataType) {
		long preId = findTaskPreId(taskId, taskStartTime, intTaskStatus);
		String sql = "";
		sql = this.buildTerminalAnalyseSql(preId, null, dataType);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildTerminalAnalyseList(list);
	}

	public String findTerminalById(long id) {
		String sql = "select vcTerminalBrand,vcTerminalName from dtbTerminal where nmTerminalId="
				+ id;
		List list = this.getSession().createSQLQuery(sql).list();
		if (list == null || list.isEmpty()) {
			return "未知";
		} else {
			Object[] objects = (Object[]) list.get(0);
			String TerminalBrand = objects[0] == null ? "未知" : objects[0] + "";
			String TerminalName = objects[1] == null ? "未知" : objects[1] + "";
			return TerminalBrand + "/" + TerminalName;
		}
	}

	// //////////////////////////////////【通用拍照】区域-终端分析流量表///////////////////////////////////
	public Page<CommonSport> queryAreaTerminal(final Page page, long taskId,
			int areaType, int areaId, int terminalId, String taskStartTime, int dataType) {
		String tableName = "ftbStatSpeAreaTerminalFlush_";
		long preId = findTaskPreId(taskId, taskStartTime, 0);
		List list = null;
		Integer total;
		if(isExistTable(tableName, preId)){
			String sql = this.buildAreaTerminalSql(preId, areaType, areaId,
					terminalId, page, dataType);
			String sqls = sql.split("order by")[0];
			String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
			Object totalObj = this.getSession().createSQLQuery(totalSql)
					.uniqueResult();
			total = Integer.parseInt(null == totalObj.toString()
					|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
			list = this.getSession().createSQLQuery(sql).setFirstResult(
					page.getFirst()).setMaxResults(page.getPageSize()).list();
		}else{
			total = 0;
		}
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildAreaTerminalList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public String buildAreaTerminalSql(long taskId, int areaType, int areaId,
			int terminalId, Page page, int dataType) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmStatSpeAreaTerminalId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select intAreaId,intAreaType,nmUpFlush,nmDownFlush,(nmUpFlush+nmDownFlush) AS sumFlush,nmUsers,dtStatTime,nmTerminalId from ftbStatSpeAreaTerminalFlush_"
				+ taskId+" where 1=1";
		sql += " and intRaitype=" + dataType;
		if (areaType != -1 && areaId != -1)
			sql = sql + " and intAreaType=" + areaType + " and intAreaId="
					+ areaId;
		if (terminalId != -1)
			sql = sql + " and nmTerminalId=" + terminalId;
		sortSql = " order by  " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> buildAreaTerminalList(List list) {
		List<CommonSport> CustomerList = new ArrayList<CommonSport>();
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				int areaId = Common.StringToInt(bean[0] + "");
				int areaType = Common.StringToInt(bean[1] + "");
				String areaName = findArea(areaType, areaId);
				commonSport.setVcAreaName(areaName);
				commonSport.setIntUpFlush(Common.StringToLong(bean[2] + ""));
				commonSport.setIntDownFlush(Common.StringToLong(bean[3] + ""));
				commonSport.setTotalFlush(Common.StringToLong(bean[4] + ""));
				commonSport.setNmUsers(Common.StringToLong(bean[5] + ""));
				commonSport.setFullDate(bean[6] + "");
				commonSport.setVcTerminalName(findTerminalById(Common
						.StringToLong(bean[7] + "")));// 终端名称
				commonSport.calculateData1();
				commonSport.convertFullDate();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	public List<CommonSport> areaTerminallistData(long taskId, int areaType,
			int areaId, int terminalId, String taskStartTime, int dataType) {
		String sql = "";
		long preId = findTaskPreId(taskId, taskStartTime, 0);
		sql = this.buildAreaTerminalSql(preId, areaType, areaId, terminalId,
				null, dataType);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildAreaTerminalList(list);
	}

	
	
	
	
	// /////////////////////////////////［通用拍照］区域-业务分析流量表/////////////////////////////////
	public Page<CommonSport> queryAreaBis(final Page page, long taskId,
			int areaType, int areaId, int bussinessId, String taskStartTime, int dataType) {
		long preId = findTaskPreId(taskId, taskStartTime, 0);
		String tableName = "ftbStatSpeAreaBussFlush_";
		Integer total;
		List list = null;
		if(isExistTable(tableName, preId)){
			String sql = this.buildAreaBisSql(preId, areaType, areaId,
					bussinessId, page, dataType);
			String sqls = sql.split("order by")[0];
			String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
			Object totalObj = this.getSession().createSQLQuery(totalSql)
					.uniqueResult();
			total = Integer.parseInt(null == totalObj.toString()
					|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
			list = this.getSession().createSQLQuery(sql).setFirstResult(
					page.getFirst()).setMaxResults(page.getPageSize()).list();
		}else{
			total = 0;
		}
		
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildAreaBisList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public String buildAreaBisSql(long taskId, int areaType, int areaId,
			int bussinessId, Page page, int dataType) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmStatSpeAreaBussId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select intAreaId,intAreaType,nmUpFlush,nmDownFlush,(nmUpFlush+nmDownFlush) AS sumFlush,nmUsers,dtStatTime,nmBussinessId from ftbStatSpeAreaBussFlush_"
				+ taskId+" where 1=1";
		sql += " and intRaitype=" + dataType;
		if (areaType != -1 && areaId != -1)
			sql = sql + " and intAreaType=" + areaType + " and intAreaId="
					+ areaId;
		if (bussinessId != -1)
			sql = sql + " and nmBussinessId=" + bussinessId;
		sortSql = " order by  " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> buildAreaBisList(List list) {
		List<CommonSport> CustomerList = new ArrayList<CommonSport>();
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				int areaId = Common.StringToInt(bean[0] + "");
				int areaType = Common.StringToInt(bean[1] + "");
				String areaName = findArea(areaType, areaId);
				commonSport.setVcAreaName(areaName);
				commonSport.setIntUpFlush(Common.StringToLong(bean[2] + ""));
				commonSport.setIntDownFlush(Common.StringToLong(bean[3] + ""));
				commonSport.setTotalFlush(Common.StringToLong(bean[4] + ""));
				commonSport.setNmUsers(Common.StringToLong(bean[5] + ""));
				commonSport.setFullDate(bean[6] + "");
				commonSport.setBusiness(FindBisById(Common.StringToLong(bean[7]+ "")));// 业务名称
				commonSport.calculateData1();
				commonSport.convertFullDate();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	public List<CommonSport> areaBislistData(long taskId, int areaType,
			int areaId, int bussinessId, String taskStartTime, int dataType) {
		String sql = "";
		long preId = findTaskPreId(taskId, taskStartTime, 0);
		sql = this.buildAreaBisSql(preId, areaType, areaId, bussinessId,
				null, dataType);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildAreaBisList(list);
	}

	
	// ///////////////////////////////////【通用拍照】终端-业务分析流量表/////////////////////////////////////
	public Page<CommonSport> queryTerminalBis(final Page page, long taskId,int terminalId,int bussinessId,String taskStartTime, int dataType) {
		long preId = findTaskPreId(taskId, taskStartTime, 0);
		String tableName = "ftbStatSpeTerminalBussFlush_";
		Integer total;
		List list = null;
		if(isExistTable(tableName, preId)){
			String sql = this.buildTerminalBisSql(preId,terminalId,bussinessId, page, dataType);
			String sqls = sql.split("order by")[0];
			String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
			Object totalObj = this.getSession().createSQLQuery(totalSql)
					.uniqueResult();
			total = Integer.parseInt(null == totalObj.toString()
					|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
			list = this.getSession().createSQLQuery(sql).setFirstResult(
					page.getFirst()).setMaxResults(page.getPageSize()).list();
		}else{
			total = 0;
		}
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildTerminalBisList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public String buildTerminalBisSql(long taskId,int terminalId,int bussinessId, Page page, int dataType) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmStatSpeTerminalBussId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select nmTerminalId,nmUpFlush,nmDownFlush,(nmUpFlush+nmDownFlush) AS sumFlush,nmUsers,dtStatTime,nmBussinessId from ftbStatSpeTerminalBussFlush_"
				+ taskId+" where 1=1";
		sql += " and intRaitype=" + dataType;
if(terminalId!=-1)
	sql=sql+" and nmTerminalId="+terminalId;
if(bussinessId!=-1)
	sql=sql+" and nmBussinessId="+bussinessId;
		sortSql = " order by  " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}

	public List<CommonSport> buildTerminalBisList(List list) {
		List<CommonSport> CustomerList = new ArrayList<CommonSport>();
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				commonSport.setVcTerminalName(findTerminalById(Common
						.StringToLong(bean[0] + "")));// 终端名称
				commonSport.setIntUpFlush(Common.StringToLong(bean[1] + ""));
				commonSport.setIntDownFlush(Common.StringToLong(bean[2] + ""));
				commonSport.setTotalFlush(Common.StringToLong(bean[3] + ""));
				commonSport.setNmUsers(Common.StringToLong(bean[4] + ""));
				commonSport.setFullDate(bean[5] + "");
				commonSport.setBusiness(FindBisById(Common.StringToLong(bean[6]+ "")));// 业务名称
				commonSport.calculateData1();
				commonSport.convertFullDate();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	public List<CommonSport> terminalBislistData(long taskId,int terminalId,int bussinessId,String taskStartTime, int dataType) {
		String sql = "";
		long preId = findTaskPreId(taskId, taskStartTime, 0);
		sql = this.buildTerminalBisSql(preId,terminalId,bussinessId, null, dataType);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildTerminalBisList(list);
	}

	
	
	
	
}
