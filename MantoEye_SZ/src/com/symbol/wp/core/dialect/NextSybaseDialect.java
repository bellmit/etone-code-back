package com.symbol.wp.core.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SybaseDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NextSybaseDialect extends SybaseDialect {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public NextSybaseDialect() {
		super();
		// ---新增
		// registerHibernateType(Types.DECIMAL,
		// Hibernate.BIG_DECIMAL.getName());
		logger.info("加載新的Sybase方言");
		// ---原來
		registerHibernateType(Types.LONGVARCHAR, Hibernate.STRING.getName());
	}
}
