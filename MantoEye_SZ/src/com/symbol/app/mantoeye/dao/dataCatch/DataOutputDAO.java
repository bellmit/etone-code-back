package com.symbol.app.mantoeye.dao.dataCatch;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.flush.OutPutTableColumnEntity;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbCgi;
import com.symbol.app.mantoeye.entity.FtbDataOutPutDecTask;
import com.symbol.app.mantoeye.entity.FtbDataOutPutTask;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 自定义数据输出
 * 
 * @author chenchengle
 * 
 */
@Repository
public class DataOutputDAO extends HibernateDao<FtbDataOutPutTask, Long> {
	
	/**
	 * 添加数据提取任务
	 */
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
	
	
	public void saveDataOutPutDecTask(FtbDataOutPutDecTask entity) {
		String sql = "insert into ftbDataOutPutDecTask(nmDataOutPutTaskId,vcFileName,vcFileServerIp,vcFilePath,nmFileSize) values (?,?,?,?,?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, entity.getNmDataOutPutTaskId());
		query.setParameter(1, entity.getVcFileName());
		query.setParameter(2, entity.getVcFileServerIp());
		query.setParameter(3, entity.getVcFilePath());
		query.setParameter(4, entity.getNmFileSize());
		query.executeUpdate();
	}
	
	public void saveOutPutBussinessMap(Long taskId ,Long businessId) {
		String sql = "insert into ftbOutPutBussinessMap(nmDataOutPutTaskId,nmBussinessId) values ("+taskId+","+businessId+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	// 根据任务ID 来关联业务类型业务查询 用于终端业务分析
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
	

	public List<BussAndBussType> queryAreaIdAndCgiByTaskId(int taskId) {

		String sql = "select b.intId,b.intId,b.vcCGI,b.vcCgiChName from ftbOutPutCgiMap a,ftbCgi b where a.vcCgi=b.vcCGI and a.nmDataOutPutTaskId="
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
	
	public void updateDataOutPutDecTask(FtbDataOutPutDecTask entity) {
		String sql = "update ftbDataOutPutDecTask set vcFileServerIp=?,vcFilePath=?,nmFileSize=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, entity.getVcFileServerIp());
		query.setParameter(1, entity.getVcFilePath());
		query.setParameter(2, entity.getNmFileSize());
		query.executeUpdate();
	}
	
	public Long findTaskByName(String vcTaskName) {
		String sql = "select nmDataOutPutTaskId from ftbDataOutPutTask where vcTaskName='"+vcTaskName+"'";
		List list = this.getSession().createSQLQuery(sql).list();
		if (list == null || list.isEmpty()) {
			return 0l;
		} else {
			return Common.StringToLong(list.get(0).toString());
		}
	}
	
	public FtbDataOutPutDecTask getByTaskId(Long taskId) {
		String sql = "select * from ftbDataOutPutDecTask where nmDataOutPutTaskId="+taskId;
		List<FtbDataOutPutDecTask> list = this.getSession().createSQLQuery(sql).addEntity(FtbDataOutPutDecTask.class).list();
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	

	/**
	 * 删除任务
	 * 
	 * @param taskId
	 */
	public void deleteTask(Long taskId) {
		String deleteSql = "delete from  ftbDataOutPutTask where nmDataOutPutTaskId ="+taskId;
		//String deleteSql1 = "delete from  ftbDataOutPutDecTask where nmDataOutPutTaskId ="+taskId;
		String deleteSql2 = "delete from  ftbOutPutUrlMap where nmDataOutPutTaskId ="+taskId;
		String deleteSql3 = "delete from  ftbOutPutCgiMap where nmDataOutPutTaskId ="+taskId;
		String deleteSql4 = "delete from  ftbOutPutApnMap where nmDataOutPutTaskId ="+taskId;
		String deleteSql5 = "delete from  ftbOutPutMsisdnMap where nmDataOutPutTaskId ="+taskId;
		String deleteSql6 = "delete from  ftbOutPutImeiMap where nmDataOutPutTaskId ="+taskId;
		String deleteSql7 = "delete from  ftbOutPutBussinessMap where nmDataOutPutTaskId ="+taskId;
		String deleteSql8 = "delete from  ftbOutPutColumnMap where nmDataOutPutTaskId ="+taskId;
		String deleteSql9 = "delete from  ftbOutPutColumnMapTask where nmDataOutPutTaskId ="+taskId;		
		//this.getSession().createSQLQuery(deleteSql1).executeUpdate();
		this.getSession().createSQLQuery(deleteSql2).executeUpdate();
		this.getSession().createSQLQuery(deleteSql3).executeUpdate();
		this.getSession().createSQLQuery(deleteSql4).executeUpdate();
		this.getSession().createSQLQuery(deleteSql5).executeUpdate();
		this.getSession().createSQLQuery(deleteSql6).executeUpdate();
		this.getSession().createSQLQuery(deleteSql7).executeUpdate();
		this.getSession().createSQLQuery(deleteSql8).executeUpdate();
		this.getSession().createSQLQuery(deleteSql9).executeUpdate();
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	/**
	 * 删除号码
	 * 
	 * @param taskId
	 */
	public void deleteNmMsisdn(String nmIds) {
		String deleteSql = "delete from  ftbOutPutMsisdnMap where nmId in ("+nmIds+")";
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	public void deleteOutPutColumnMapTask(Long taskId) {
		String deleteSql = "delete from  ftbOutPutColumnMapTask where nmDataOutPutTaskId ="+taskId;
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	
	public void deleteOutPutBussinessMap(Long taskId) {
		String deleteSql = "delete from  ftbOutPutBussinessMap where nmDataOutPutTaskId ="+taskId;
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	public void saveOutPutColumnMapTask(Long taskId,Long nmTableMapId) {
		String sql = "insert into ftbOutPutColumnMapTask(nmDataOutPutTaskId,nmOutPutTableColumnId) values("+taskId+","+nmTableMapId+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void deleteOutPutApnMap(Long taskId) {
		String deleteSql = "delete from  ftbOutPutApnMap where nmDataOutPutTaskId ="+taskId;
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	public void saveOutPutApnMap(Long taskId,String vcApn) {
		String sql = "insert into ftbOutPutApnMap(nmDataOutPutTaskId,vcApn) values("+taskId+",'"+vcApn+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void deleteOutPutCgiMap(Long taskId) {
		String deleteSql = "delete from  ftbOutPutCgiMap where nmDataOutPutTaskId ="+taskId;
		this.getSession().createSQLQuery(deleteSql).executeUpdate();
	}
	
	public void saveOutPutCgiMap(Long taskId,String cgi) {
		String sql = "insert into ftbOutPutCgiMap(nmDataOutPutTaskId,vcCgi) values("+taskId+",'"+cgi+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	
	public Integer queryByVcTaskName(String vcTaskName) {
		String queryIdSql = "select nmDataOutPutTaskId from ftbDataOutPutTask where vcTaskName='"
				+ vcTaskName +"'";// 获取当前插入任务ID
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
	}

	
	public Integer queryByVcFileName(String vcFileName) {
		String queryIdSql = "select nmDataOutPutDecTaskId from ftbDataOutPutDecTask where vcFileName='"
				+ vcFileName +"'";// 获取当前插入任务ID
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
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
	
	
	/**
	 * 根据表名查询所有表字段
	 * 
	 * @return
	 */
	public List<OutPutTableColumnEntity> queryColumnMapByTableId(Long taskId) {
		String sql = "select a.vcColumnName,a.vcColumnNickName,a.nmOutPutTableColumnId,b.nmId from ftbOutPutTableColumn a left join ftbOutPutColumnMapTask b on a.nmOutPutTableColumnId=b.nmOutPutTableColumnId and b.nmDataOutPutTaskId="+taskId+" order by vcColumnName asc";
		List list = this.getSession().createSQLQuery(sql).list();
		List outPutColumnMapList= isOutPutColumnMap(taskId);
		Object[] outPutColumnMapobj=null;
		if (outPutColumnMapList != null && outPutColumnMapList.size() > 0) {
			outPutColumnMapobj = (Object[]) outPutColumnMapList.get(0);
		}
		List<OutPutTableColumnEntity> listData = new ArrayList<OutPutTableColumnEntity>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				OutPutTableColumnEntity columnMap = new OutPutTableColumnEntity();
				if (obj[0]!=null && !obj[0].equals("")) {
					columnMap.setVcColumnName(obj[0].toString());
				}
				if (obj[1]!=null && !obj[1].equals("")) {
					columnMap.setVcColumnNickName(obj[1].toString());
				}
				if (obj[2]!=null) {
					columnMap.setNmTableMapId(obj[2].toString());
				}
				if (obj[3]!=null) {
					columnMap.setOutType(obj[3].toString());
				}
				columnMap.setInType("1");
				if (outPutColumnMapobj!=null) {
					if (columnMap.getVcColumnName().equals("nmImei")&& (outPutColumnMapobj[6]==null || outPutColumnMapobj[6].toString().equals("0")) ) {
						columnMap.setInType("0");
					}
					if (columnMap.getVcColumnName().equals("nmMsisdn")&& (outPutColumnMapobj[2]==null || outPutColumnMapobj[2].toString().equals("0"))) {
						columnMap.setInType("0");
					}
					if (columnMap.getVcColumnName().equals("vcBussinessName")&& (outPutColumnMapobj[7]==null || outPutColumnMapobj[7].toString().equals("0"))) {
						columnMap.setInType("0");
					}
					if (columnMap.getVcColumnName().equals("vcCgi")&& (outPutColumnMapobj[5]==null || outPutColumnMapobj[5].toString().equals("0"))) {
						columnMap.setInType("0");
					}
					if (columnMap.getVcColumnName().equals("vcWapUrl")&& (outPutColumnMapobj[3]==null || outPutColumnMapobj[3].toString().equals("0"))) {
						columnMap.setInType("0");
					}
					if (columnMap.getVcColumnName().equals("vcApn")&& (outPutColumnMapobj[4]==null || outPutColumnMapobj[4].toString().equals("0"))) {
						columnMap.setInType("0");
					}
					
				}else{
					columnMap.setInType("0");
				}
				listData.add(columnMap);
			}
		}
		return listData;
	}
	
	
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

	public List isOutPutColumnMap(Long taskId) {
		String queryIdSql = "select nmOutPutColumnMapId,nmDataOutPutTaskId,intMsisdnStatus,intURLStatus,intApnStatus,intCgiStatus,intImeiStatus,intbussinessStatus from ftbOutPutColumnMap where nmDataOutPutTaskId="
				+ taskId;// 获取当前插入任务ID
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		return list;
	}
	
	public boolean isExistOutPutColumnMap(Long taskId) {
		String queryIdSql = "select count(*) as totalCount from ftbOutPutColumnMap where nmDataOutPutTaskId="
				+ taskId;// 获取当前插入任务ID
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if (total>0) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isExistOutPutCgiMap(Long taskId) {
		String queryIdSql = "select count(*) as totalCount from ftbOutPutCgiMap where nmDataOutPutTaskId="
				+ taskId;// 获取当前插入任务ID
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if (total>0) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isExistNmMsisdn(Long taskId) {
		String queryIdSql = "select count(*) as totalCount from ftbOutPutMsisdnMap where nmDataOutPutTaskId="+taskId;
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if (total>0) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isExistIMEI(Long taskId) {
		String queryIdSql = "select count(*) as totalCount from ftbOutPutImeiMap where nmDataOutPutTaskId="+taskId;
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if (total>0) {
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isExistUrl(Long taskId) {
		String queryIdSql = "select count(*) as totalCount from ftbOutPutUrlMap where nmDataOutPutTaskId="+taskId;
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		if (total>0) {
			return true;
		}else{
			return false;
		}
	}
	
	public int isOutPutMsisdnMap(Long taskId) {
		String queryIdSql = "select count(*) as totalCount from ftbOutPutMsisdnMap where nmDataOutPutTaskId="
				+ taskId;// 获取当前插入任务ID
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		return total;
	}
	
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
	
	
	public List<FtbCgi> queryOutPutCgiMap(Long taskId) {
		String sql = "select a.vcCgi vcCGI,c.vcCgiChName from ftbOutPutCgiMap a,ftbCgi c  where a.vcCgi = c. vcCGI and nmDataOutPutTaskId="+taskId;
		List<FtbCgi> list = this.getSession().createSQLQuery(sql)
				.addScalar("vcCGI", Hibernate.STRING)
				.addScalar("vcCgiChName", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(FtbCgi.class)).list();
		return list;
	}
	
	public Page<FtbCgi> queryCgi(Page page,String vcCgiChName) {
		String sql = this.buildCgiSql(vcCgiChName);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("intId", Hibernate.INTEGER)
				.addScalar("vcCGI", Hibernate.STRING)
				.addScalar("vcCgiEnName", Hibernate.STRING)
				.addScalar("vcCgiChName", Hibernate.STRING)
				.setResultTransformer(
						Transformers.aliasToBean(FtbCgi.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(list);
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	
	public String buildCgiSql(String vcCgiChName) {
		String sql = "select * from ftbCgi where 1=1";
		String sortSql = "";
		String sortType = " asc ";
		String sortColumn = "vcCgiChName";
		if(vcCgiChName!=null && !vcCgiChName.equals("")){
			sql = sql +" and (vcCgiChName like '%"+ vcCgiChName+"%' or vcCgi like '%"+vcCgiChName+"%')";
		}
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	public List<CommonSport> exportNmMsisdn(Long taskId,String nmMsisdn) {
		String sql = this.buildNmMsisdnSql(taskId, nmMsisdn,null);
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
		.addScalar("id", Hibernate.LONG).addScalar("nmMsisdn",
				Hibernate.STRING).setResultTransformer(
				Transformers.aliasToBean(CommonSport.class)).list();
		return list;
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

	
	public void saveOutPutColumnMap(Long taskId,String column,int value) {
		String sql = "insert into ftbOutPutColumnMap(nmDataOutPutTaskId,"+column+") values("+taskId+","+value+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void updateOutPutColumnMap(Long taskId,String column,int value) {
		String sql = "update ftbOutPutColumnMap set "+column+"="+value +" where nmDataOutPutTaskId="+ taskId;
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void saveOutPutMsisdnMap(Long taskId,Long msis) {
		String sql = "insert into ftbOutPutMsisdnMap(nmDataOutPutTaskId,nmMsisdn) values("+taskId+","+msis+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public List queryAllNmMsisdn(Long taskId) {
		String sql = "select nmMsisdn from ftbOutPutMsisdnMap where nmDataOutPutTaskId="+taskId;
		List list = this.getSession().createSQLQuery(sql).list();
		return list;
	}
	
	public List queryAllImei(Long taskId) {
		String sql = "select vcImei from ftbOutPutImeiMap where nmDataOutPutTaskId="+taskId;
		List list = this.getSession().createSQLQuery(sql).list();
		return list;
	}
	
	public void updateOutPutMsisdnMap(Long taskId,Long nmMsisdn,Long nmId) {
		String sql = "update ftbOutPutMsisdnMap set nmMsisdn=? where nmId=? and nmDataOutPutTaskId=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, nmMsisdn);
		query.setParameter(1, nmId);
		query.setParameter(2, taskId);
		query.executeUpdate();
	}
	
	
	public void saveOutPutUrlMap(Long taskId,String url) {
		String sql = "insert into ftbOutPutUrlMap(nmDataOutPutTaskId,vcUrl) values("+taskId+",'"+url+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void saveOutPutImeiMap(Long taskId,String IMIE) {
		String sql = "insert into ftbOutPutImeiMap(nmDataOutPutTaskId,vcImei) values("+taskId+",'"+IMIE+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	/*
	 * 
	 * 任务定制-url
	 * */
	
	public Page<CommonSport> queryvcUrl(Page page,Long taskId,String vcUrl) {
		String sql = this.buildvcUrlSql(taskId, vcUrl,page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("vcUrl",
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
	
	public String buildvcUrlSql(Long taskId,String vcUrl,Page page) {
		String sql = "select nmId as id,vcUrl from ftbOutPutUrlMap where nmDataOutPutTaskId="+taskId;
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "nmId";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		if(vcUrl!=null && !vcUrl.equals("")){
			sql = sql +" and vcUrl like '%"+ vcUrl+"%'";
		}
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	
	public void DelUrlByID(String ids){
		String sql="delete from ftbOutPutUrlMap where nmId in ("+ids+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public List<CommonSport> urlListData(Long taskId,String vcUrl) {
		String sql = this.buildvcUrlSql(taskId,vcUrl, null);
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("vcUrl",
						Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(CommonSport.class)).list();
		return list;
	}
	public Integer queryUrlByCondition(Long taskId,String vcUrl,Long nmId) {
		String queryIdSql = "select * from ftbOutPutUrlMap where nmDataOutPutTaskId="
				+ taskId+" and vcUrl='"+ vcUrl+"'";// 获取当前插入任务ID
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
	
	public int isExistsUrl(Long taskId) {
		String queryIdSql = "select count(*) as totalCount from ftbOutPutUrlMap where nmDataOutPutTaskId="
				+ taskId;// 获取当前插入任务ID
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		return total;
	}
	


	public void savevcUrl(Long taskId,String vcUrl) {
		String sql = "insert into ftbOutPutUrlMap(nmDataOutPutTaskId,vcUrl) values("+taskId+",'"+vcUrl+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void modifyUrl(Long taskId,String vcUrl,Long nmId) {
		String sql = "update ftbOutPutUrlMap set vcUrl=? where nmId=? and nmDataOutPutTaskId=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, vcUrl);
		query.setParameter(1, nmId);
		query.setParameter(2, taskId);
		query.executeUpdate();
	}
	
	//2011-12-8  按条件查询表内已存在url
	public List<String> findUrlByCondition(long taskId){
		String Sql = "select vcUrl from ftbOutPutUrlMap where nmDataOutPutTaskId="
			+ taskId;
		return this.getSession().createSQLQuery(Sql).list();
	}
	
	
	/*
	 * 
	 * 任务定制-IMEI
	 * */
	
	public Page<CommonSport> queryvcImei(Page page,Long taskId,String vcImei) {
		String sql = this.buildvcImeiSql(taskId, vcImei,page);
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("vcImei",
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
	
	public String buildvcImeiSql(Long taskId,String vcImei,Page page) {
		String sql = "select nmId as id,vcImei from ftbOutPutImeiMap where nmDataOutPutTaskId="+taskId;
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = "nmId";
		if (page != null && page.getOrder()!=null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		if(vcImei!=null && !vcImei.equals("")){
			sql = sql +" and vcImei like '%"+ vcImei+"%'";
		}
		sortSql = " order by " + sortColumn +" "+sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	
	public void DelvcImeiByID(String ids){
		String sql="delete from ftbOutPutImeiMap where nmId in ("+ids+")";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public List<CommonSport> vcImeiListData(Long taskId,String vcImei) {
		String sql = this.buildvcImeiSql(taskId,vcImei, null);
		List<CommonSport> list = this.getSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.LONG).addScalar("vcImei",
						Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(CommonSport.class)).list();
		return list;
	}
	public Integer queryvcImeiByCondition(Long taskId,String vcImei,Long nmId) {
		String queryIdSql = "select * from ftbOutPutImeiMap where nmDataOutPutTaskId="
				+ taskId+" and vcImei='"+ vcImei+"'";// 获取当前插入任务ID
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
	
	public int isExistsvcImei(Long taskId) {
		String queryIdSql = "select count(*) as totalCount from ftbOutPutImeiMap where nmDataOutPutTaskId="
				+ taskId;// 获取当前插入任务ID
		Object totalObj = this.getSession().createSQLQuery(queryIdSql).uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		return total;
	}
	


	public void savevcImei(Long taskId,String vcImei) {
		String sql = "insert into ftbOutPutImeiMap(nmDataOutPutTaskId,vcImei) values("+taskId+",'"+vcImei+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void modifyvcImei(Long taskId,String vcImei,Long nmId) {
		String sql = "update ftbOutPutImeiMap set vcImei=? where nmId=? and nmDataOutPutTaskId=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, vcImei);
		query.setParameter(1, nmId);
		query.setParameter(2, taskId);
		query.executeUpdate();
	}
	
	public List<String> findImeiByCondition(long taskId){
		String Sql = "select vcImei from ftbOutPutImeiMap where nmDataOutPutTaskId="
			+ taskId;
		return this.getSession().createSQLQuery(Sql).list();
	}
	
	
}
