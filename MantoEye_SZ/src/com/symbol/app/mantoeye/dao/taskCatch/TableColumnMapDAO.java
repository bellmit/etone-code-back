package com.symbol.app.mantoeye.dao.taskCatch;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.FtbTableColumnMap;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;
@Repository
public class TableColumnMapDAO extends HibernateDao<FtbTableColumnMap, Integer>{
	public void saveEntity(FtbTableColumnMap entity,Short tableId){
		String sql = " insert into ftbTableColumnMap(nmTableMapId,vcColumnName,vcColumnNickName,intColumnType) " +
					 " values(?,?,?,?)";
		SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter(0, tableId);
        query.setString(1, entity.getVcColumnName());
        query.setString(2,entity.getVcColumnNickName()); 
        query.setInteger(3, entity.getIntColumnType());
        query.executeUpdate();
	}
}
