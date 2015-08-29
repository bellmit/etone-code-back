package com.symbol.wp.core.dao.common;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SqlBaseDao extends HibernateDaoSupport {
	
	
	
    public Connection getConnection() {
		//return this.getSessionFactory().openSession().connection();
		return this.getSession().connection();
	}
	public Statement getStatement() throws SQLException {
		return this.getConnection().createStatement();
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return this.getStatement().executeQuery(sql);
	}

}
