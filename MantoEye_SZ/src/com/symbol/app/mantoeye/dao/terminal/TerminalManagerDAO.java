package com.symbol.app.mantoeye.dao.terminal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.AppServer;
import com.symbol.app.mantoeye.dto.TermDataLoadTask;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.flush.TerminalEntity;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class TerminalManagerDAO extends HibernateDao {

	public void insertTerminal(List<TerminalEntity> list) {// 批量增加终端数据
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		this.clearTerminalTable();
		if (list != null && !list.isEmpty()) {
			String sqlBrand = "select max(intTerminalId) from dtbTerminalBrand ";// 获取终端品牌最大ID
			String sqlType = "select max(intTerminalId) from dtbTerminalType ";// 获取终端型号最大ID
			List brandId = this.getSession().createSQLQuery(sqlBrand).list();
			List typeId = this.getSession().createSQLQuery(sqlBrand).list();
			Object oBId = brandId.get(0);
			Object oTId = typeId.get(0);
			int identyBrand = 0;
			int identyType = 0;
			boolean flagB = oBId == null ? true : false;
			boolean flagT = oBId == null ? true : false;
			if (flagB) {
				identyType = this.addOne(identyType);
			} else {
				identyType = this.addOne(Common.StringToInt(oBId.toString()));
			}

			if (flagT) {
				identyBrand = this.addOne(identyBrand);
			} else {
				identyBrand = this.addOne(Common.StringToInt(oBId.toString()));
			}
			for (int i = 0; i < list.size(); i++) {
				TerminalEntity terminalEntity = list.get(i);

				// 查询是否有此类型终端
				String sql = "select count(*),max(b.intTerminalId),max(tt.intTmerTypeId) from  dtbTerminalBrand b join dtbTerminalType tt  on b.intTerminalId=tt.intTerminalId where b.vcBrand='"
						+ terminalEntity.getTerminalBrand()
						+ "'"
						+ " and tt.vcTmerTypeName='"
						+ terminalEntity.getTerminalType() + "'";
				int count = -1;
				Object[] obj = (Object[]) this.getSession().createSQLQuery(sql)
						.list().get(0);
				count = Common.StringToInt(obj[0] + "");
				if (count < 1) {// 如果没有这类型的品牌和终端就插入到品牌表和终端表
					identyBrand = this.addOne(identyBrand);
					identyType = this.addOne(identyType);
					String sqlInsertBrand = "insert into dtbTerminalBrand(intTerminalId,vcBrand) values ("
							+ identyBrand
							+ ",'"
							+ terminalEntity.getTerminalBrand() + "')";
					this.getSession().createSQLQuery(sqlInsertBrand)
							.executeUpdate();
					String sqlInsertType = "insert into dtbTerminalType(intTmerTypeId,intTerminalId,vcTmerTypeName) values ("
							+ identyType
							+ ","
							+ identyType
							+ ","
							+ terminalEntity.getTerminalType() + "')";
					this.getSession().createSQLQuery(sqlInsertType)
							.executeUpdate();

				} else {
					identyBrand = Common.StringToInt(obj[1] + "");
					identyType = Common.StringToInt(obj[2] + "");
				}

				String sqlMapTable = "insert into ftbTerminalType (intTerminalId,intTmerTypeId,nmMsisdn,dtTime) values ("
						+ identyBrand
						+ ","
						+ identyType
						+ ","
						+ terminalEntity.getMsisdn() + ",'" + date + "')";
				this.getSession().createSQLQuery(sqlMapTable).executeUpdate();

			}
		}
	}

	public void clearTerminalTable() {// 清除终端对照表
		String sqlClear = "truncate table ftbTerminalType";
		this.getSession().createSQLQuery(sqlClear).executeUpdate();
	}

	public int addOne(int l) {
		return l + 1;
	}

	/**
	 * 查询已上传存在的终端
	 * 
	 * @return
	 */
	public List<TerminalEntity> queryAll() {
		/*
		 * String sql="select tt.nmTerminalTypeId,
		 * tt.intTerminalId,tt.intTmerTypeId,b.vcBrand,t.vcTmerTypeName from
		 * ftbTerminalType tt,dtbTerminalBrand b,dtbTerminalType t" +" where
		 * tt.intTerminalId=b.intTerminalId and
		 * tt.intTmerTypeId=t.intTmerTypeId";
		 */
		String sql = "select intTerminalId,vcBrand ,intTmerTypeId,vcTmerTypeName from(select tt.nmTerminalTypeId, tt.intTerminalId,tt.intTmerTypeId,b.vcBrand,t.vcTmerTypeName from ftbTerminalType tt,dtbTerminalBrand b,dtbTerminalType t where tt.intTerminalId=b.intTerminalId and tt.intTmerTypeId=t.intTmerTypeId) tttt group by tttt.intTerminalId,tttt.intTmerTypeId,tttt.vcBrand,tttt.vcTmerTypeName";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List<Object[]> list = query.list();
		List<TerminalEntity> pageList = new ArrayList<TerminalEntity>();
		TerminalEntity tEntity = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = list.get(i);
				tEntity = new TerminalEntity();
				tEntity.setNmTerminalTypeId(Common.StringToLong(obj[0] + ""));
				tEntity.setBradId(Common.StringToInt(obj[0] + ""));
				tEntity.setTypeId(Common.StringToInt(obj[2] + ""));
				tEntity.setTerminalBrand(obj[1] + "");
				tEntity.setTerminalType(obj[3] + "");
				pageList.add(tEntity);
			}
		}

		return pageList;

	}

	/**
	 * 根据任务ID查询已上传存在的终端
	 * 
	 * @return
	 */
	public List<TerminalEntity> queryAllByTaskId(int taskId) {
		String sql = "select tt.nmTerminalPolicyMapTermI, tt.intTerminalId,tt.intTmerTypeId,b.vcBrand,t.vcTmerTypeName from ftbTerminalPolicyMapTerm tt,dtbTerminalBrand b,dtbTerminalType t"
				+ " where tt.intTerminalId=b.intTerminalId and tt.intTmerTypeId=t.intTmerTypeId and tt.nmTerminalPolicyId="
				+ taskId;
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List<Object[]> list = query.list();
		List<TerminalEntity> pageList = new ArrayList<TerminalEntity>();
		TerminalEntity tEntity = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = list.get(i);
				tEntity = new TerminalEntity();
				tEntity.setNmTerminalTypeId(Common.StringToLong(obj[0] + ""));
				tEntity.setBradId(Common.StringToInt(obj[1] + ""));
				tEntity.setTypeId(Common.StringToInt(obj[2] + ""));
				tEntity.setTerminalBrand(obj[3] + "");
				tEntity.setTerminalType(obj[4] + "");
				pageList.add(tEntity);
			}
		}

		return pageList;

	}

	/**
	 * 分页查询终端和品牌对应关系
	 * 
	 * @param page
	 * @param terminalEntity
	 * @return
	 */
	public Page<TerminalEntity> queryTerminalEntity(final Page page,
			TerminalEntity terminalEntity) {
		String sql = "select  tt.nmTerminalTypeId, tt.intTerminalId,tt.intTmerTypeId,b.vcBrand,t.vcTmerTypeName,tt.nmMsisdn,tt.dtTime from ftbTerminalType tt,dtbTerminalBrand b,dtbTerminalType t"
				+ " where tt.intTerminalId=b.intTerminalId and tt.intTmerTypeId=t.intTmerTypeId";

		String countSql = "select count(*) from ftbTerminalType tt,dtbTerminalBrand b,dtbTerminalType t"
				+ " where tt.intTerminalId=b.intTerminalId and tt.intTmerTypeId=t.intTmerTypeId";
		;
		/*
		 * if(terminalEntity.getTerminalBrand()==null||terminalEntity.getTerminalBrand
		 * ()!=""){
		 * sql=sql+" and b.vcBrand='"+terminalEntity.getTerminalBrand()+"'"; }
		 */

		if (terminalEntity.getTerminalType() != null
				|| "null".equals(terminalEntity.getTerminalType())) {
			terminalEntity.setTerminalType(terminalEntity.getTerminalType()
					.replaceAll(",", "','"));
			sql = sql + " and t.vcTmerTypeName in ('"
					+ terminalEntity.getTerminalType() + "' )";
			countSql = countSql + " and t.vcTmerTypeName in ('"
					+ terminalEntity.getTerminalType() + "' )";
		}
		Page newPage = new Page();
		newPage.setTotalCount(Common.StringToInt(this.getSession()
				.createSQLQuery(countSql).list().get(0).toString()));
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setMaxResults(page.getPageSize());
		query.setFirstResult(page.getFirst());
		List<Object[]> list = query.list();
		newPage.setPageSize(page.getPageSize());
		newPage.setPageNo(page.getPageNo());
		List<TerminalEntity> pageList = new ArrayList<TerminalEntity>();
		TerminalEntity tEntity = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = list.get(i);
				tEntity = new TerminalEntity();
				tEntity.setNmTerminalTypeId(Common.StringToLong(obj[0] + ""));
				tEntity.setBradId(Common.StringToInt(obj[1] + ""));
				tEntity.setTypeId(Common.StringToInt(obj[2] + ""));
				tEntity.setTerminalBrand(obj[3] + "");
				tEntity.setTerminalType(obj[4] + "");
				tEntity.setMsisdn(Common.StringToLong(obj[5] + ""));
				tEntity.setDate(CommonUtils.formatDate(obj[6]));
				pageList.add(tEntity);
			}
		}
		newPage.setResult(pageList);
		return newPage;
	}

	/**
	 * 保存上传终端信息
	 * 
	 * @param filePath
	 */
	public void saveUpFile(String serverIp, String filePath) {
		String sql = "insert into ftbStatTermDataLoadTask(vcServerIp,vcFilePath,intStatus,dtSTime) values ('"
				+ serverIp
				+ "','"
				+ filePath
				+ "',"
				+ 1
				+ ",'"
				+ CommonUtils.getCurrentDate() + "')";

		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	/**
	 * 保存上传终端信息
	 * 
	 */
	public List<TermDataLoadTask> findUpFiles() {
		String sql = "select  vcServerIp,vcFilePath,intStatus,dtSTime,dtETime from ftbStatTermDataLoadTask order by dtSTime desc ";
		List list = this.getSession().createSQLQuery(sql).list();
		List<TermDataLoadTask> result = new ArrayList<TermDataLoadTask>();
		TermDataLoadTask bean = null;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				bean = new TermDataLoadTask();
				bean
						.setVcServerIp(Common.judgeString(objs[0] + "") ? (objs[0] + "")
								: "--");
				bean
						.setVcFilePath(Common.judgeString(objs[1] + "") ? (objs[1] + "")
								: "--");
				bean.setIntStatus(Common.StringToInt(objs[2] + ""));
				bean
						.setDtSTime(Common.judgeString(objs[3] + "") ? (objs[3] + "")
								: "--");
				bean
						.setDtETime(Common.judgeString(objs[4] + "") ? (objs[4] + "")
								: "--");

				result.add(bean);
			}
		}
		logger.info("size" + result.size());
		return result;
	}

	/**
	 * 通过服务器IP 获取应用服务器信息
	 * 
	 * @param ip
	 * @return
	 */
	public AppServer getAppServerByServerIp(String ip) {
		String sql = "select vcFtpName,vcFtpPwd,vcServerIpOut from ftbServerMap where vcServerIp='"
				+ ip + "'";
		AppServer appServer = new AppServer();
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			if (obj != null) {
				appServer.setUserName(obj[0] + "");
				appServer.setPassWd(obj[1] + "");
				if (Common.judgeString(obj[2] + "" + "")) {
					appServer.setServerIpOut(obj[2] + "");
				} else {
					appServer.setServerIP(ip);
				}
			}
		}
		return appServer;
	}
	/**
	 * 通过服务器IP 获取应用服务器信息
	 * 
	 * @param ip
	 * @return
	 */
	public AppServer getAppServerByOutIp(String ip) {
		String sql = "select vcFtpName,vcFtpPwd,vcServerIp from ftbServerMap where vcServerIpOut='"
				+ ip + "'";
		AppServer appServer = new AppServer();
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			if (obj != null) {
				appServer.setUserName(obj[0] + "");
				appServer.setPassWd(obj[1] + "");
				if (Common.judgeString(obj[2] + "" + "")) {
					appServer.setServerIP(obj[2] + "");
				} else {
					appServer.setServerIpOut(ip);
				}
			}
		}
		return appServer;
	}

	public List<BussAndBussType> queryBussAndBussType() {
		String sql = " select a.intBscId as nmBussinessId, 1 as nmBussinessTypeId, a.vcName as vcBussinessName ,'BSC' as vcBussinessTypeName from dtbBsc a";
		sql += " union ";
		sql += " select b.intSgsnId as nmBussinessId, 2 as nmBussinessTypeId, b.vcName as vcBussinessName ,'SGSN' as vcBussinessTypeName from dtbGsn b";
		sql += " union";
		sql += " select c.intStreetId as nmBussinessId, 3 as nmBussinessTypeId, c.vcName as vcBussinessName ,'街道办' as vcBussinessTypeName from dtbStreet c";
		sql += " union";
		sql += " select d.intSaleAreaId as nmBussinessId, 4 as nmBussinessTypeId, d.vcSaleAreaName as vcBussinessName ,'营销片区' as vcBussinessTypeName from dtbSaleArea d";
		sql += " union";
		sql += " select e.intBranchId as nmBussinessId, 5 as nmBussinessTypeId, e.vcBranchName as vcBussinessName ,'分公司' as vcBussinessTypeName from dtbSubsidiaryCompany e ";
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

}
