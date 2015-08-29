package com.etone.mantoeye.analyse.domain.terminal;

import java.io.Serializable;

/**
 * 終端升級日誌參數DOMAIN
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月27日1:04:46
 * 
 */
public class TerminalChangeLogParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3446142296692832217L;

	/**
	 * select查詢sql
	 */
	private String sourcesSql;

	/**
	 * where條件sql
	 */
	private String whereSql;

	/**
	 * 終端升級統計表Id
	 */
	private Long nmTerminalChangeId;

	/**
	 * @return the sourcesSql
	 */
	public String getSourcesSql() {
		return sourcesSql;
	}

	/**
	 * @param sourcesSql
	 *            the sourcesSql to set
	 */
	public void setSourcesSql(String sourcesSql) {
		this.sourcesSql = sourcesSql;
	}

	/**
	 * @return the whereSql
	 */
	public String getWhereSql() {
		return whereSql;
	}

	/**
	 * @param whereSql
	 *            the whereSql to set
	 */
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	/**
	 * @return the nmTerminalChangeId
	 */
	public Long getNmTerminalChangeId() {
		return nmTerminalChangeId;
	}

	/**
	 * @param nmTerminalChangeId
	 *            the nmTerminalChangeId to set
	 */
	public void setNmTerminalChangeId(Long nmTerminalChangeId) {
		this.nmTerminalChangeId = nmTerminalChangeId;
	}

	/**
	 * @param sourcesSql
	 * @param whereSql
	 * @param nmTerminalChangeId
	 */
	public TerminalChangeLogParam(String sourcesSql, String whereSql,
			Long nmTerminalChangeId) {
		this.sourcesSql = sourcesSql;
		this.whereSql = whereSql;
		this.nmTerminalChangeId = nmTerminalChangeId;
	}

	/**
	 * 
	 */
	public TerminalChangeLogParam() {
	}

}
