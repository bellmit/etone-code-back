package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.entity.FtbResidentConfig;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class PopulationConfigureDAO extends HibernateDao {

	/**
	 * 
	 * @param 插入常驻人口配置
	 */
	public void saveConfigure(Integer intType,Integer intDay,String vcNote) {
		String saveSql = "insert into ftbResidentConfig (intType,intDay,vcNote) values "
				+ "("
				+ intType
				+ ","
				+ intDay
				+ ",'"
				+ vcNote
				+ "')";
		this.getSession().createSQLQuery(saveSql).executeUpdate();

	}

	public void updateConfigure(Long nmResidentId,Integer intDay,String vcNote) {
		String updateSql = "update ftbResidentConfig set intDay="+intDay+",vcNote='"+vcNote+"' where nmResidentId="+nmResidentId;
		this.getSession().createSQLQuery(updateSql).executeUpdate();
	}
	
	/**
	 * 
	 * @param taskName
	 * @return Integer
	 * 
	 *         通过任务名判断任务是否存在 不存在返回-1 存在返回当前ID名称
	 */

	public Integer queryByIntType(Integer intType) {
		String queryIdSql = "select nmResidentId from ftbResidentConfig where intType="
				+ intType ;// 获取当前插入任务ID
		List list = this.getSession().createSQLQuery(queryIdSql).list();
		if (list == null || list.isEmpty()) {
			return -1;
		} else {
			return Common.StringToInt(list.get(0).toString());
		}
	}

	

	public Page<FtbResidentConfig> queryTask(final Page page) {
		List<FtbResidentConfig> list = new ArrayList<FtbResidentConfig>();
		StringBuffer sql = new StringBuffer(
				"select nmResidentId,intType,intDay,vcNote from ftbResidentConfig where 1=1");
		sql.append(" order by nmResidentId desc ");
		Page newPage = new Page();
		String sqls = sql.toString().split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<Object[]> listObj = this.getSession().createSQLQuery(
				sql.toString()).setFirstResult(page.getFirst()).setMaxResults(
				page.getPageSize()).list();

		newPage.setTotalCount(total);
		newPage.setPageNo(page.getPageNo());
		newPage.setPageSize(page.getPageSize());

		FtbResidentConfig ftbResidentConfig = null;
		if (listObj != null && !listObj.isEmpty()) {
			for (int i = 0; i < listObj.size(); i++) {
				ftbResidentConfig = new FtbResidentConfig();
				Object[] obj = listObj.get(i);
				ftbResidentConfig.setNmResidentId(Common
						.StringToLong(obj[0] + ""));
				int intType = Common.StringToInt(obj[1] + "");
				if (intType==1) {
					ftbResidentConfig.setIntType("周");
				}
				if (intType==2) {
					ftbResidentConfig.setIntType("月");
				}
				ftbResidentConfig.setIntDay(Common
						.StringToInt(obj[2] + ""));
				ftbResidentConfig.setVcNote(obj[3] + "");
				list.add(ftbResidentConfig);
			}
		}
		newPage.setResult(list);
		return newPage;
	}


	public void deleteConfigure(String nmResidentIds) {
		String delSql = "delete from ftbResidentConfig where nmResidentId in ("
				+ nmResidentIds + ")";
	
		this.getSession().createSQLQuery(delSql).executeUpdate();
	}

}
