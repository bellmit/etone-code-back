package com.symbol.app.mantoeye.exception;

/**
 * 在查询节点进行插入操作
 * @author rankin
 *
 */
public class QueryNodeInsertException extends Exception {
	
	/**
	 * 
	 */
	public QueryNodeInsertException() {
	}

	/**
	 * @param arg0
	 */
	public QueryNodeInsertException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public QueryNodeInsertException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public QueryNodeInsertException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
