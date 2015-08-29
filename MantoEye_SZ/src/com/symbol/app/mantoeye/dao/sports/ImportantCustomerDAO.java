package com.symbol.app.mantoeye.dao.sports;

import org.springframework.stereotype.Repository;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/*
 * 全网数据业务总体统计
 */

@Repository
public class ImportantCustomerDAO extends HibernateDao{	
	/**
	 * 插入数据
	 */
	public void insertMsisdn(Long nmMsisdn,Integer intType,String dtUpdateTime) {
		String sql = "insert into ftbMsisdnList(nmMsisdn,intType,dtUpdateTime) values("+nmMsisdn+","+intType+",'"+dtUpdateTime+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	/**
	 * 插入保障区域
	 */
	public void insertArea(String vcAreaName,Integer intType,String dtUpdateTime) {
		String sql = "insert into ftbArea(vcAreaName,intType,dtUpdateTime) values('"+vcAreaName+"',"+intType+",'"+dtUpdateTime+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	/**
	 * 插入小区
	 */
	public void insertCGI(String cgi,String cgiName) {
		String sql = "insert into ftbCgi(vcCGI,vcCgiChName) values('"+cgi+"','"+cgiName+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	/**
	 * 插入ftbAreaMapCell
	 */
	public void insertAreaMapCell(Long nmAreaId,String vcCGI) {
		String sql = "insert into ftbAreaMapCell(nmAreaId,vcCGI) values("+nmAreaId+",'"+vcCGI+"')";
		this.getSession().createSQLQuery(sql).executeUpdate();	
	}
}
