package com.symbol.app.mantoeye.dao.taskCatch;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.FtbTableMap;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 统计表管理（即席查询和数据提取用）
 * @author rankin
 *
 */
@Repository
public class TableMapDAO extends HibernateDao<FtbTableMap, Short>{
	public void saveEntity(FtbTableMap entity){
		String sql = " insert into ftbTableMap(vcTableName,vcTableNickName,intViewFlag,intTableType,intBusinessType,intIsShow) " +
					 " values(?,?,?,?,?,?)";
		SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setString(0, entity.getVcTableName());
        query.setString(1, entity.getVcTableNickName());
        query.setInteger(2,entity.getIntViewFlag()); 
        query.setInteger(3, entity.getIntTableType());
        query.setInteger(4, entity.getIntBusinessType());
        query.setInteger(5, entity.getIntIsShow());

        query.executeUpdate();
	}
}
