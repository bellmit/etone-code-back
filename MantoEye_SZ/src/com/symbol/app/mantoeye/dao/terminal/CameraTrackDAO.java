package com.symbol.app.mantoeye.dao.terminal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbDataOutPutTask;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDaoOld;

@Repository
public class CameraTrackDAO extends HibernateDaoOld<FtbDataOutPutTask, Long> {

	public Page<CommonSport> query(final Page page, int taskStatus,
			String orderTime_search, String orderTime_end, String taskName,
			String taskMan) {
		String sql = this.buildSql(taskStatus, orderTime_search, orderTime_end,
				taskName, taskMan, page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonSport> buildBeanList(List list) {
		List<CommonSport> CustomerList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			CustomerList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
			commonSport.setNmDataOutPutTaskId(Common.StringToLong(bean[0]+""));
			commonSport.setVcTaskName(bean[2]+"");
			commonSport.setIntTaskType(Common.StringToInt(bean[3]+""));
			commonSport.setDtOrderTime(CommonUtils.formatDate(bean[4]));
			commonSport.setVcTaskOrder(bean[5]+"");
			commonSport.setDtStartTime(CommonUtils.formatDate(bean[6]));
			commonSport.setDtEndTime(CommonUtils.formatDate(bean[7]));
			commonSport.setBitTaskActiveFlag(Common.StringToInt(bean[8]+""));
			commonSport.setIntTaskStatus(Common.StringToInt(bean[9]+""));
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
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
	public String buildSql(int taskStatus, String orderTime_search,
			String orderTime_end, String taskName, String taskMan, Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmDataOutPutTaskId ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		sql = "select * from ftbDataOutPutTask where intTaskType<>3 ";
		if (taskStatus != -1)
			sql = sql + " and intTaskStatus =" + taskStatus + "";
		if (orderTime_search != null &&!("").equals(orderTime_search))
			sql = sql + " and dtOrderTime>='" + orderTime_search+"' ";
		if (orderTime_end != null &&! ("").equals(orderTime_end))
			sql = sql + " and dtOrderTime<='" + orderTime_end+"' ";;
		if (taskName != null &&! ("").equals(taskName))
			sql = sql + " and vcTaskName like '%" + taskName + "%'";
		if (taskMan != null &&! ("").equals(taskMan))
			sql = sql + " and vcTaskOrder like '%" + taskMan + "%'";
		sortSql = " order by  " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	
	
	public Integer queryByVcTaskName(String vcTaskName) {
		String queryIdSql = "select nmDataOutPutTaskId from ftbDataOutPutTask where vcTaskName='"
				+ vcTaskName +"'"; 
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
	}

	public void saveTask(FtbDataOutPutTask entity) {
		String sql = "insert into ftbDataOutPutTask(intTaskDelong,vcTaskName,intTaskType,dtOrderTime,vcTaskOrder,dtStartTime,dtEndTime,bitTaskActiveFlag,intTaskStatus) values (?,?,?,?,?,?,?,?,?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, entity.getIntTaskDelong());
		query.setParameter(1, entity.getVcTaskName());
		query.setParameter(2, entity.getIntTaskType());
		query.setParameter(3, entity.getDtOrderTime());
		query.setParameter(4, entity.getVcTaskOrder());
		query.setParameter(5, entity.getDtStartTime());
		query.setParameter(6, entity.getDtEndTime());
		query.setParameter(7, entity.getBitTaskActiveFlag());
		query.setParameter(8, entity.getIntTaskStatus());
		query.executeUpdate();
	}
	
	/**
	 * 删除任务
	 * 
	 * @param taskId
	 */
	public void deleteTask(Long taskId) {	
			String deleteSql2 = "delete from  ftbDataOutPutTask where nmDataOutPutTaskId ="+taskId;
			this.getSession().createSQLQuery(deleteSql2).executeUpdate();
	}
	
	/**
	 * 删除中间表任务
	 * 
	 * @param taskId
	 */
	public void deleteTaskPre(Long taskId) {	
			String deleteSql2 = "delete from  ftbDataOutPutTaskPre where nmDataOutPutTaskId ="+taskId;
			this.getSession().createSQLQuery(deleteSql2).executeUpdate();
	}
	
	public void delContrast(Long taskId,String tableName){
		String deleteSql = "delete from  "+tableName+" where nmDataOutPutTaskId ="+taskId;
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	public void delResultTable(long taskId) {
		String sql="if exists(select 1 from sys.systable where table_name='ftbStatSpeAreaTerminalFlush_"+taskId+"') then truncate table ftbStatSpeTerminalFlush_"+taskId+"; drop table ftbStatSpeAreaTerminalFlush_"+taskId+" end if;";
		String sql2="if exists(select 1 from sys.systable where table_name='ftbStatSpeAreaBussFlush_"+taskId+"') then truncate table ftbStatSpeAreaBussFlush_"+taskId+"; drop table ftbStatSpeAreaBussFlush_"+taskId+" end if;";
		String sql3="if exists(select 1 from sys.systable where table_name='ftbStatSpeTerminalBussFlush_"+taskId+"') then truncate table ftbStatSpeTerminalBussFlush_"+taskId+"; drop table ftbStatSpeTerminalBussFlush_"+taskId+" end if;";
		String sql4="if exists(select 1 from sys.systable where table_name='ftbStatSpeAreaFlush_"+taskId+"') then truncate table ftbStatSpeAreaFlush_"+taskId+"; drop table ftbStatSpeAreaFlush_"+taskId+" end if;";
		String sql5="if exists(select 1 from sys.systable where table_name='ftbStatSpeBussFlush_"+taskId+"') then truncate table ftbStatSpeBussFlush_"+taskId+"; drop table ftbStatSpeBussFlush_"+taskId+" end if;";
		String sql6="if exists(select 1 from sys.systable where table_name='ftbStatSpeTerminalFlush_"+taskId+"') then truncate table ftbStatSpeTerminalFlush_"+taskId+"; drop table ftbStatSpeTerminalFlush_"+taskId+" end if;";
		   this.getSession().createSQLQuery(sql).executeUpdate();
		   this.getSession().createSQLQuery(sql2).executeUpdate();
		   this.getSession().createSQLQuery(sql3).executeUpdate();
		   this.getSession().createSQLQuery(sql4).executeUpdate();
		   this.getSession().createSQLQuery(sql5).executeUpdate();
		   this.getSession().createSQLQuery(sql6).executeUpdate();
		
	}
	
	public List<Long> findPreIdByTaskId(long taskId){
		StringBuilder sql = new StringBuilder();
		sql.append("select nmDataOutPutTaskPreId from ftbDataOutPutTaskPre where 1=1");
		sql.append(" and nmDataOutPutTaskId = " + taskId);
		List<Object> preIdObj = this.getSession().createSQLQuery(sql.toString()).list();
		List<Long> preIds = new ArrayList<Long>();
		for(Object id : preIdObj){
			if(id != null){
				long preId = Long.parseLong(null == id.toString()
						|| "".equals(id.toString()) ? "0" : id.toString());
				preIds.add(preId);
			}
		}
		return preIds;
	}
	
	public boolean isExistOutPutColumnMap(Long taskId,String tableName) {
		//1.通用拍照指定号码	2.通用拍照指定终端	4.通用拍照指定区域	5.通用拍照指定业务	6.通用拍照指定APN 7.通用拍照用户归属
		String queryIdSql = "select count(*) as totalCount from "+tableName+" where nmDataOutPutTaskId="
				+ taskId;
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if (total>0) {
			return true;
		}else{
			return false;
		}
	}
	
	//////////////////////////////////////////////////APN/////////////////////////////////////////////////////
	// APN类型APN关联查询
	public List<BussAndBussType> queryApnAndApnType() {
		String sql = "select b.intApnId,b.intApnTypeId,b.vcApnName,t.vcApnTypeName from dtbApn b join dtbApnType t on b.intApnTypeId=t.intApnTypeId";
		List list = this.getSession().createSQLQuery(sql).list();
		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
		BussAndBussType bt = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bt = new BussAndBussType();
				Object[] obj = (Object[]) list.get(i);
				bt.setBusinessId(obj[0] + "");
				bt.setBusinessName(obj[2] + "");
				bt.setBusinessTypeId(obj[1] + "");
				bt.setBusinessTypeName(obj[3] + "");
				listEntity.add(bt);
			}
		}
		return listEntity;
	}
	
	public void deleteCameratrackApn(Long taskId) {
		String deleteSql = "delete from  ftbOutPutApnMap where nmDataOutPutTaskId ="+taskId;
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	public void saveCameratrackApn(Long taskId,String vcApn) {
		String sql = "insert into ftbOutPutApnMap(nmDataOutPutTaskId,vcApn) values("+taskId+",'"+vcApn+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	// 根据任务ID 来关联业务类型业务查询 用于终端业务分析
	public List<BussAndBussType> queryApnIdAndApnIdTypeByTaskId(int taskId) {

		String sql = "select b.intApnId,b.intApnTypeId,b.vcApnName,c.vcApnTypeName from ftbOutPutApnMap a,dtbApn b,dtbApnType c where a.vcApn=b.vcApnName and b.intApnTypeId = c.intApnTypeId and a.nmDataOutPutTaskId="
				+ taskId;
		List list = this.getSession().createSQLQuery(sql).list();
		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
		BussAndBussType bt = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bt = new BussAndBussType();
				Object[] obj = (Object[]) list.get(i);
				bt.setBusinessId(obj[0] + "");
				bt.setBusinessName(obj[2] + "");
				bt.setBusinessTypeId(obj[1] + "");
				bt.setBusinessTypeName(obj[3] + "");
				listEntity.add(bt);
			}
		}
		return listEntity;
	}
	
	///////////////////////////////////////////////业务//////////////////////////////////////////////////////////////////
	public void saveCameratrackBis(Long taskId ,Long businessId) {
		String sql = "insert into ftbOutPutBussinessMap(nmDataOutPutTaskId,nmBussinessId) values ("+taskId+","+businessId+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	public void deleteCameratrackBis(Long taskId) {
		String deleteSql = "delete from  ftbOutPutBussinessMap where nmDataOutPutTaskId ="+taskId;
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	public List<BussAndBussType> queryBussAndBussTypeByTaskId(int taskId) {

		String sql = "select nmDimensId as nmBussinessId,nmGroupId as nmBussinessTypeId,vcDimensName as vcBussinessName,vcGroupName as vcBussinessTypeName from ftbOutPutBussinessMap a,dtbGroupTree d where a.nmBussinessId=d.nmDimensId and nmTypeId = 1 and nmDataOutPutTaskId="
			+ taskId+" group by nmDimensId,nmGroupId,vcDimensName,vcGroupName order by nmDimensId";
		List list = this.getSession().createSQLQuery(sql).list();
		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
		BussAndBussType bt = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bt = new BussAndBussType();
				Object[] obj = (Object[]) list.get(i);
				bt.setBusinessId(obj[0] + "");
				bt.setBusinessName(obj[2] + "");
				bt.setBusinessTypeId(obj[1] + "");
				bt.setBusinessTypeName(obj[3] + "");
				listEntity.add(bt);
			}
		}
		return listEntity;
	}
	
////////////////////////////////////////////////////号码//////////////////////////////////////////////////////////
	public Page<CommonSport> queryNmMsisdn(Page page,Long taskId,String nmMsisdn) {
		String sql = this.buildNmMsisdnSql(taskId, nmMsisdn,page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("nmMsisdn",
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
	
	public List<CommonSport> exportNmMsisdn(Long taskId,String nmMsisdn) {
		String sql = this.buildNmMsisdnSql(taskId, nmMsisdn,null);
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
		.addScalar("id", Hibernate.LONG).addScalar("nmMsisdn",
				Hibernate.STRING).setResultTransformer(
				Transformers.aliasToBean(CommonSport.class)).list();
		return list;
	}
	public void deleteNmMsisdn(String nmIds) {
		String deleteSql = "delete from  ftbOutPutMsisdnMap where nmId in ("+nmIds+")";
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	public String buildNmMsisdnSql(Long taskId,String nmMsisdn,Page page) {
		String sql = "select nmId as id,right(convert(varchar,nmMsisdn),length(convert(varchar,nmMsisdn))-2) as nmMsisdn from ftbOutPutMsisdnMap where nmDataOutPutTaskId="+taskId;
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "nmId";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		if(nmMsisdn!=null && !nmMsisdn.equals("")){
			sql = sql +" and convert(varchar,nmMsisdn) like '%"+ nmMsisdn+"%'";
		}
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	public Integer queryNmMsisdn(Long taskId,Long nmMsisdn,Long nmId) {
		String queryIdSql = "select * from ftbOutPutMsisdnMap where nmDataOutPutTaskId="
				+ taskId+" and nmMsisdn="+ nmMsisdn;// 获取当前插入任务ID
		if (nmId!=null) {
			queryIdSql=queryIdSql+" and nmId!="+nmId;
		}
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
	}
	public List queryAllNmMsisdn(Long taskId) {
		String sql = "select nmMsisdn from ftbOutPutMsisdnMap where nmDataOutPutTaskId="+taskId;
		List list = this.getSession().createSQLQuery(sql).list();
		return list;
	}
	
	public void saveMsisdn(Long taskId,Long msis) {
		String sql = "insert into ftbOutPutMsisdnMap(nmDataOutPutTaskId,nmMsisdn) values("+taskId+","+msis+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void updateMsisdn(Long taskId,Long nmMsisdn,Long nmId) {
		String sql = "update ftbOutPutMsisdnMap set nmMsisdn=? where nmId=? and nmDataOutPutTaskId=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, nmMsisdn);
		query.setParameter(1, nmId);
		query.setParameter(2, taskId);
		query.executeUpdate();
	}
	
	
	//////////////////////////////////////////////////////////终端类型////////////////////////////////////////////////////////////////
	// APN类型APN关联查询
//	public List<BussAndBussType> queryTerminalAndType() {
//		String sql = "SELECT nmTerminalId,vcTerminalName,vcTerminalBrand FROM dtbTerminal WHERE vcTerminalBrand !='N/A'";
//		List list = this.getSession().createSQLQuery(sql).list();
//		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
//		BussAndBussType bt = null;
//		if (list != null && !list.isEmpty()) {
//			for (int i = 0; i < list.size(); i++) {
//				bt = new BussAndBussType();
//				Object[] obj = (Object[]) list.get(i);
//				bt.setBusinessId(obj[0] + "");
//				bt.setBusinessName(obj[1] + "");
//				bt.setBusinessTypeId(obj[2] + "");
//				bt.setBusinessTypeName(obj[2] + "");
//				listEntity.add(bt);
//			}
//		}
//		return listEntity;
//	}
//	
//	public List<BussAndBussType> queryTerminalIdAndIdTypeByTaskId(int taskId) {
//
//		String sql = "select b.nmTerminalId,b.vcTerminalName,b.vcTerminalBrand from ftbOutPutTerminalMap a,dtbTerminal b where a.nmTerminalId=b.nmTerminalId  and a.nmDataOutPutTaskId="
//				+ taskId;
//
//		List list = this.getSession().createSQLQuery(sql).list();
//		List<BussAndBussType> listEntity = new ArrayList<BussAndBussType>();
//		BussAndBussType bt = null;
//		if (list != null && !list.isEmpty()) {
//			for (int i = 0; i < list.size(); i++) {
//				bt = new BussAndBussType();
//				Object[] obj = (Object[]) list.get(i);
//				bt.setBusinessId(obj[0] + "");
//				bt.setBusinessName(obj[1] + "");
//				bt.setBusinessTypeId(obj[2] + "");
//				bt.setBusinessTypeName(obj[2] + "");
//				listEntity.add(bt);
//			}
//		}
//		return listEntity;
//	}
//	
//	public void deleteCameratrackTerminal(Long taskId) {
//		String deleteSql = "delete from  ftbOutPutTerminalMap where nmDataOutPutTaskId ="+taskId;
//		this.getSession().createSQLQuery(deleteSql).executeUpdate();
//	}
//	
//	public void saveCameratrackTerminal(Long taskId,long vcApn) {
//		String sql = "insert into ftbOutPutTerminalMap(nmDataOutPutTaskId,nmTerminalId) values("+taskId+","+vcApn+")";
//		this.getSession().createSQLQuery(sql).executeUpdate();
//	}
	
	public List<CommonSport> queryTerminalMap(Long taskId) {
		String sql = "SELECT b.nmTerminalId,b.vcTerminalBrand,b.vcTerminalName FROM ftbOutPutTerminalMap as a LEFT JOIN dtbTerminal as b ON a.nmTerminalId=b.nmTerminalId where nmDataOutPutTaskId="+taskId;
		List list = this.getSession().createSQLQuery(sql).list();
	List<CommonSport> resultList=buildTerminalMap(list);
		
		return resultList;
	}
	 	public List<CommonSport> buildTerminalMap(List list) {
		List<CommonSport> List = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			List = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
			
				commonSport.setNmTerminalId(Common.StringToLong(bean[0]+""));
				commonSport.setVcTerminalBrand(bean[1]+"");
				commonSport.setVcTerminalName(bean[2]+"");
			
				List.add(commonSport);
			}
		}
		return List;
	}
    
	
	
	
	
	public Page<CommonSport> queryTerminal(Page page,String vcTerminalBrand,String vcTerminalName) {

		
		String sql = this.buildTerminalSql(vcTerminalBrand,vcTerminalName);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildTerminalList(list));
		newPage.setPageNo(page.getPageNo());
		return newPage;
		
	
	}
    
	public String buildTerminalSql(String vcTerminalBrand,String vcTerminalName) {
		String sql = "select nmTerminalId,vcTerminalBrand,vcTerminalName from dtbTerminal where 1=1";
	if(vcTerminalBrand!=null&&!("").equals(vcTerminalBrand))
		sql=sql+" and vcTerminalBrand like '"+vcTerminalBrand+"%'";
	if(vcTerminalName!=null&&!("").equals(vcTerminalName))
		sql=sql+" and vcTerminalName like '"+vcTerminalName+"%'";
	sql=sql+" order by vcTerminalBrand desc";
		return sql;
	}
    
	public List<CommonSport> buildTerminalList(List list) {
		List<CommonSport> List = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			List = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				commonSport.setNmTerminalId(Common.StringToLong(bean[0]+""));
				commonSport.setVcTerminalBrand(bean[1]+"");
				commonSport.setVcTerminalName(bean[2]+"");
				List.add(commonSport);
			}
		}
		return List;
	}
    public void deleteTerminalMap(long taskId){
    	String sql="delete from ftbOutPutTerminalMap where nmDataOutPutTaskId="+taskId;
    	this.getSession().createSQLQuery(sql).executeUpdate();
    }
    public void saveTerminalMap(long taskId,long nmTerminalId){
    	String sql="";
    	
    	sql=" insert into ftbOutPutTerminalMap(nmDataOutPutTaskId,nmTerminalId) values("+taskId+","+nmTerminalId+")";

    	this.getSession().createSQLQuery(sql).executeUpdate();
    }
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////用户归属//////////////////////////////////////////////////
    public String queryUserBelongByTaskId(long taskId){
    	String sql="select intUserBelongId from ftbOutPutAttribMap where nmDataOutPutTaskId="+taskId;
    	List list=this.getSession().createSQLQuery(sql).list();
    	if(list.size()!=0)
    		return list.get(0).toString();
    	else
    		return null;
    	
    }
    public void saveCameratrackUser(long taskId,int intintUserBelongId){
    	String sql="insert into ftbOutPutAttribMap(nmDataOutPutTaskId,intUserBelongId) values("+taskId+","+intintUserBelongId+")";
    	this.getSession().createSQLQuery(sql).executeUpdate();
    }
	
    public void UpdateCameratrackUser(long taskId,int intintUserBelongId){
    	String sql = "update ftbOutPutAttribMap set intUserBelongId=? where  nmDataOutPutTaskId=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, intintUserBelongId);
		query.setParameter(1, taskId);
		query.executeUpdate();
    }
    
    ////////////////////////////////////////////////////////区域配置//////////////////////////////////////////////////////
	public List<CommonSport> queryAreaMap(Long taskId) {
		String sql = "select intAreaType,intAreaId from ftbOutPutAreaMap where nmDataOutPutTaskId="+taskId;
		List list = this.getSession().createSQLQuery(sql).list();
	List<CommonSport> resultList=buildAreaMap(list);
		
		return resultList;
	}
	 	public List<CommonSport> buildAreaMap(List list) {
		List<CommonSport> List = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			List = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				int areaType=Common.StringToInt(bean[0]+"");
				if(areaType!=0){
				commonSport.setVcAreaName(findArea(Common.StringToInt(bean[0]+""), Common.StringToInt(bean[1]+"")));
				commonSport.setNmAreaId(Common.StringToLong(bean[1]+""));
				commonSport.setDataType(Common.StringToInt(bean[0]+""));
				}else {
					commonSport.setVcAreaName("全网");
					commonSport.setNmAreaId(Common.StringToLong(0+""));
					commonSport.setDataType(0);
				}
				List.add(commonSport);
			}
		}
		return List;
	}
    
	 	public String findArea(int areaType,int areaId){
	 		String sql="";
	 		//0：全网  1：bsc类型 	2：街道类型	3：分公司类型 4：营销片区	5：sgsn类型
			switch (areaType) {
			case 1:
				sql = "select  vcName from dtbBsc where intBscId="+areaId;
				break;
			case 5:
				sql = "select  vcName from dtbGsn where intSgsnId="+areaId;
				break;
			case 2:
				sql = "select  vcName from dtbStreet where intStreetId="+areaId;
				break;
			case 4:
				sql = "select vcSaleAreaName from dtbSaleArea where intSaleAreaId="+areaId;
				break;
			case 3:
				sql = "select vcBranchName from dtbSubsidiaryCompany where intBranchId="+areaId;
				break;
			case 6:
				sql = "select vcCgiChName,vcCGI from ftbCgi where intId="+areaId;
				break;
			}
	 		List list=this.getSession().createSQLQuery(sql).list();
	 		if (list!=null){ 
	 			if (areaType==6) {
	 				List cgiList = new ArrayList();
					for (int i = 0; i < list.size(); i++) {
						Object[] bean = (Object[]) list.get(i);
						String cgi =bean[0]+"--"+bean[1];
						cgiList.add(cgi);
					}
					list.clear();
		 			list.addAll(cgiList);
				}  			
				return list.get(0).toString();
				
	 		}else{
	 			return null;
	 		}
	 	}
	
	
	
	
	public Page<CommonSport> queryArea(Page page,int areaType,String areaName) {
		if (areaType!=0) {
		
		String sql = this.buildAreaSql(areaType,areaName);
		String totalSql = "select count(1) from ( " + sql + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildAreaList(list,areaType));
		newPage.setPageNo(page.getPageNo());
		return newPage;
		
		}else {
			Page newPage = new Page();
			newPage.setPageSize(page.getPageSize());
			newPage.setTotalCount(1);
			newPage.setResult(buildAreaList(null,areaType));
			newPage.setPageNo(page.getPageNo());
			return newPage;
		}
	}
    
	public String buildAreaSql(int areaType,String areaName) {
		String sql = "";
		//0：全网  1：bsc类型 	2：街道类型	3：分公司类型 4：营销片区	5：sgsn类型
		if("null".equals(areaName)||areaName==null)
			areaName="";
		switch (areaType) {
		case 1:
			sql = "select intBscId, vcName from dtbBsc where vcName like '%"+areaName+"%'";
			break;
		case 5:
			sql = "select intSgsnId, vcName from dtbGsn  where vcName like '%"+areaName+"%'";
			break;
		case 2:
			sql = "select intStreetId, vcName from dtbStreet  where vcName like '%"+areaName+"%'";
			break;
		case 4:
			sql = "select intSaleAreaId, vcSaleAreaName from dtbSaleArea  where vcSaleAreaName like '%"+areaName+"%'";
			break;
		case 3:
			sql = "select intBranchId, vcBranchName from dtbSubsidiaryCompany  where vcBranchName like '%"+areaName+"%'";
			break;
		case 6:
			sql = "select intId,vcCGI, vcCgiChName from ftbCGI  where vcCgiChName like '%"+areaName+"%'";
			break;
		}
		return sql;
	}
    
	public List<CommonSport> buildAreaList(List list,int areaType) {
		List<CommonSport> List = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			List = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);
				commonSport.setNmAreaId(Common.StringToLong(bean[0]+""));
				if (areaType==6) {
					commonSport.setVcAreaName(bean[2]+"--"+bean[1]);
				}else {
					commonSport.setVcAreaName(bean[1]+"");
				}
				commonSport.setDataType(areaType);
				List.add(commonSport);
			}
		}else {
			List = new ArrayList<CommonSport>();
			CommonSport commonSport1 = new CommonSport();
			commonSport1.setVcAreaName("全网");
			commonSport1.setDataType(areaType);
			List.add(commonSport1);
		}
		return List;
	}
    public void deleteOutPutAreaMap(long taskId){
    	String sql="delete from ftbOutPutAreaMap where nmDataOutPutTaskId="+taskId;
    	this.getSession().createSQLQuery(sql).executeUpdate();
    }
    public void saveOutPutAreaMap(long taskId,int areaType,String areaId){
    	String sql="";
    	if(areaId!=null&&!("").equals(areaId))
    	sql=" insert into ftbOutPutAreaMap(nmDataOutPutTaskId,intAreaType,intAreaId) values("+taskId+","+areaType+","+areaId+")";
    	else
    		sql=" insert into ftbOutPutAreaMap(nmDataOutPutTaskId,intAreaType) values("+taskId+","+areaType+")";
    	this.getSession().createSQLQuery(sql).executeUpdate();
    }
    
	
}
