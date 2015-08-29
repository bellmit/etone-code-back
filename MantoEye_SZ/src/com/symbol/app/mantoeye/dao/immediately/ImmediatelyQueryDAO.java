package com.symbol.app.mantoeye.dao.immediately;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.ImmediatelyQueryEntity;
import com.symbol.app.mantoeye.dto.flush.TableColumnMapEntity;
import com.symbol.app.mantoeye.dto.flush.TableMapEntity;
import com.symbol.app.mantoeye.entity.FtbTableMap;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class ImmediatelyQueryDAO extends HibernateQueryDAO {

	/**
	 * 查询所有表
	 * 
	 * @return
	 */
	public List<TableMapEntity> queryAllTabke() {
		TableMapEntity tableMap;
		String sql = "from FtbTableMap where intIsShow=? order by vcTableNickName asc";
		Query query = this.getSession().createQuery(sql);
		query.setParameter(0, 1);
		List list = query.list();
		logger.info(list.size() + "-----");
		List<TableMapEntity> listData = new ArrayList<TableMapEntity>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				FtbTableMap obj = (FtbTableMap) list.get(i);
				tableMap = new TableMapEntity();
				tableMap.setNmTableMapId(obj.getNmTableMapId() + "");
				tableMap.setVcTableName(obj.getVcTableName());
				tableMap.setVcTableNickName(obj.getVcTableNickName());
				tableMap.setIntTableType(obj.getIntTableType() + "");
				tableMap.setIntBusinessType(obj.getIntBusinessType() + "");
				if (obj.getVcDimension() != null && obj.getVcDimension() != "") {
					String[] dis = obj.getVcDimension().split(",");
					List<String> l = new ArrayList<String>();
					for (String s : dis) {
						if (s != null && s.trim() != "")
							l.add(s);
					}
					tableMap.setDimensions(l);
				}
				listData.add(tableMap);
			}
		}
		return listData;
	}

	/**
	 * 查询所有表字段
	 * 
	 * @return
	 */
	public List<TableColumnMapEntity> queryColumnMap() {
		String sql = "select nmTableMapId,vcColumnName,intColumnType,vcColumnNickName from ftbTableColumnMap ";
		List list = this.getSession().createSQLQuery(sql).list();
		List<TableColumnMapEntity> listData = new ArrayList<TableColumnMapEntity>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				TableColumnMapEntity columnMap = new TableColumnMapEntity();
				columnMap.setNmTableMapId(obj[0].toString());
				columnMap.setVcColumnName(obj[1].toString());
				columnMap.setIntColumnType(obj[2].toString());
				columnMap.setVcColumnNickName(obj[3].toString());
				listData.add(columnMap);
			}
		}
		return listData;
	}

	/**
	 * 根据表名查询所有表字段
	 * 
	 * @return
	 */
	public List<TableColumnMapEntity> queryColumnMapByTableId(int tableId) {
		String sql = "select nmTableMapId,vcColumnName,intColumnType,vcColumnNickName from ftbTableColumnMap where nmTableMapId="
				+ tableId;
		List list = this.getSession().createSQLQuery(sql).list();
		List<TableColumnMapEntity> listData = new ArrayList<TableColumnMapEntity>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				TableColumnMapEntity columnMap = new TableColumnMapEntity();
				columnMap.setNmTableMapId(obj[0].toString());
				columnMap.setVcColumnName(obj[1].toString());
				columnMap.setIntColumnType(obj[2].toString());
				columnMap.setVcColumnNickName(obj[3].toString());
				listData.add(columnMap);
			}
		}
		return listData;
	}

	/**
	 * 根据生成的SQL来查询数据 分导出和分页
	 * 
	 * @param page
	 * @param sql
	 * @param export
	 * @return
	 */
	public Page<ImmediatelyQueryEntity> queryBySql(final Page page, String sql,
			boolean export) {
		// nmImei的查询因为包含了函数 ，用hibernate时会报错 因此用jdbc查询
		if (sql.indexOf("nmImei") != -1) {
			logger.info("--contains nmImei--");
			return queryBySql1(page, sql, export);
		} else {
			List<Object[]> list = new ArrayList<Object[]>();
			Page newPage = new Page();
			if (export) {
				list = this.getSession().createSQLQuery(sql).list();
			} else {
				String[] countSqls = sql.split("from");
				String countSql = "select count(1) from " + countSqls[1];
				BigInteger dcount = (BigInteger) this.getSession()
						.createSQLQuery(countSql).list().get(0);
				int count = dcount.intValue();
				newPage.setTotalCount(count);
				newPage.setPageSize(page.getPageSize());
				newPage.setPageNo(page.getPageNo());
				SQLQuery query = this.getSession().createSQLQuery(sql);
				query.setFirstResult(page.getFirst());
				query.setMaxResults(page.getPageSize());
				list = query.list();
			}
			newPage.setResult(this.bulidData(list));
			return newPage;
		}
	}

	// 获取IMEI
	private Map<Long, String> getImeis(String imsis, int year, int month,
			int day) {
		Map<Long, String> imeimap = new HashMap<Long, String>();
		Object[] objs;
		String imeis;
		String sql = " select  nmImsi,nmImei from ftbStatImeiOverFlush where 1=1 ";
		if (imsis != "") {
			sql = sql + " and nmImsi in (" + imsis + ") ";
		}
		if (year > 0) {// 获取全部时无此过滤条件
			sql = sql + " and intYear =" + year + " and intMonth =" + month
					+ " and intDay =" + day;
		}
		sql = sql + " group by  nmImsi,nmImei ";
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				objs = (Object[]) list.get(i);
				Long imsi = Common.StringToLong(objs[0] + "");
				String imei = objs[1] + "";
				if (imeimap.containsKey(imsi)) {
					imeimap.put(imsi, imeimap.get(imsi) + "," + imei);
				} else {
					imeimap.put(imsi, imei);
				}
			}
		}
		return imeimap;
	}

	public Page<ImmediatelyQueryEntity> queryBySql1(final Page page,
			String sql, boolean export) {
		List<Object[]> list = new ArrayList<Object[]>();
		String[] bsesql = sql.split("from");
		String fields = bsesql[0].replaceAll("select", "").trim();
		logger.info("fields:" + fields);
		String[] arrfield = fields.split(",");
		int imeiIndex = -1;
		for (int i = 0; i < arrfield.length; i++) {
			if (arrfield[i].trim().equals("nmImei")) {
				imeiIndex = i;
			}
		}
		fields = fields.replaceAll("nmImei", "nmImsi");
		sql = "select " + fields + " from " + bsesql[1];
		logger.info("*basesql*" + sql);
		logger.info("*imeiIndex:" + imeiIndex);
		Page newPage = new Page();
		if (export) {
			list = this.getSession().createSQLQuery(sql).list();
		} else {
			String[] countSqls = sql.split("from");
			String countSql = "select count(1) from " + countSqls[1];
			BigInteger dcount = (BigInteger) this.getSession().createSQLQuery(
					countSql).list().get(0);
			int count = dcount.intValue();
			newPage.setTotalCount(count);
			newPage.setPageSize(page.getPageSize());
			newPage.setPageNo(page.getPageNo());
			SQLQuery query = this.getSession().createSQLQuery(sql);
			query.setFirstResult(page.getFirst());
			query.setMaxResults(page.getPageSize());
			list = query.list();
		}
		newPage.setResult(this.bulidData1(list, imeiIndex));
		return newPage;
	}

	/**
	 * 组装实体Bean
	 * 
	 * @param list
	 * @return
	 */
	public List<ImmediatelyQueryEntity> bulidData1(List<Object[]> list,
			int imeiIndex) {
		List<ImmediatelyQueryEntity> listData = new ArrayList<ImmediatelyQueryEntity>();
		Map<Long, String> map = this.getImeis("", -1, -1, -1);
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj;
				if (list.get(i) instanceof Object[]) {
					obj = list.get(i);
				} else {
					obj = new Object[] { list.get(i) };
				}
				ImmediatelyQueryEntity immediatelyEntity = new ImmediatelyQueryEntity();
				Class c = immediatelyEntity.getClass();
				for (int j = 0; j < obj.length; j++) {
					String methodName = "setColumn" + j;
					Method m1;
					try {
						if (j == imeiIndex) {
							Long imsi = Common.StringToLong(obj[j] + "");
							String imei = map.get(imsi);
							m1 = c.getMethod(methodName, String.class);
							m1.invoke(immediatelyEntity, imei == null ? "--"
									: imei + "");
						} else {
							m1 = c.getMethod(methodName, String.class);
							m1.invoke(immediatelyEntity, obj[j] == null ? "--"
									: obj[j] + "");
						}

					} catch (SecurityException e) {
						logger.error(e.getMessage());
					} catch (NoSuchMethodException e) {
						logger.error(e.getMessage());
					} catch (IllegalArgumentException e) {
						logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage());
					}
				}
				listData.add(immediatelyEntity);
			}
		}
		return listData;
	}

	/**
	 * 组装实体Bean
	 * 
	 * @param list
	 * @return
	 */
	public List<ImmediatelyQueryEntity> bulidData(List<Object[]> list) {
		List<ImmediatelyQueryEntity> listData = new ArrayList<ImmediatelyQueryEntity>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj;
				if (list.get(i) instanceof Object[]) {
					obj = list.get(i);
				} else {
					obj = new Object[] { list.get(i) };
				}
				ImmediatelyQueryEntity immediatelyEntity = new ImmediatelyQueryEntity();
				Class c = immediatelyEntity.getClass();
				for (int j = 0; j < obj.length; j++) {
					String methodName = "setColumn" + j;
					Method m1;
					try {
						m1 = c.getMethod(methodName, String.class);
						m1.invoke(immediatelyEntity, obj[j] == null ? "--"
								: obj[j] + "");

					} catch (SecurityException e) {
						logger.error(e.getMessage());
					} catch (NoSuchMethodException e) {
						logger.error(e.getMessage());
					} catch (IllegalArgumentException e) {
						logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage());
					}
				}
				listData.add(immediatelyEntity);
			}
		}
		return listData;
	}

	/**
	 * 大数据导出到文件SQL
	 * 
	 * @param sql
	 * @param queryTime
	 * @param iqServerPath
	 */
	public void queryBySql(String sql, String queryTime, String iqServerPath) {
		Connection con = getSession().connection();
		// @sql ,@immediateDate ,@db_temp_dir
		String procedure = "{Call  proc_web_export_big_data_to_file(?,?,?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = con.prepareCall(procedure);
			cstmt.setString(1, sql);
			cstmt.setString(2, queryTime);
			cstmt.setString(3, iqServerPath);
			cstmt.execute();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public long getTotalCount(String sql) {
		sql = " select count(1) from (" + sql + " ) as totalCount";
		Object count = this.getSession().createSQLQuery(sql).uniqueResult();
		return Long.parseLong(count.toString());
	}
}
