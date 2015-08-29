package com.etone.mantoeye.analyse.domain.network;

import java.io.Serializable;

/**
 * 業務健康度統計結果所需參數DOMAIN
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月17日14:36:42
 * 
 */
public class NetWorkParam  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2249457828721922162L;

	/**
	 * SELECT SQL
	 */
	private String selectSql;

	/**
	 * FROM TABLENAME
	 */
	private String tableName;

	/**
	 * WHERE SQL
	 */
	private String whereSql;

	/**
	 * 業務健康度任務ID
	 */
	private Long nmNetworkTaskId;

	/**
	 * 指標類型
	 */
	private int intType;

	/**
	 * 權重
	 */
	private double intSale;

	/**
	 * 計算出的結構值
	 */
	private double intCount;

	/**
	 * TO值
	 */
	private double markValue;

	/**
	 * @return the selectSql
	 */
	public String getSelectSql() {
		return selectSql;
	}

	/**
	 * @param selectSql
	 *            the selectSql to set
	 */
	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
	 * @return the nmNetworkTaskId
	 */
	public Long getNmNetworkTaskId() {
		return nmNetworkTaskId;
	}

	/**
	 * @param nmNetworkTaskId
	 *            the nmNetworkTaskId to set
	 */
	public void setNmNetworkTaskId(Long nmNetworkTaskId) {
		this.nmNetworkTaskId = nmNetworkTaskId;
	}

	/**
	 * @return the intType
	 */
	public int getIntType() {
		return intType;
	}

	/**
	 * @param intType
	 *            the intType to set
	 */
	public void setIntType(int intType) {
		this.intType = intType;
	}

	/**
	 * @return the intSale
	 */
	public double getIntSale() {
		return intSale;
	}

	/**
	 * @param intSale
	 *            the intSale to set
	 */
	public void setIntSale(double intSale) {
		this.intSale = intSale;
	}

	/**
	 * @return the intCount
	 */
	public double getIntCount() {
		return intCount;
	}

	/**
	 * @param intCount
	 *            the intCount to set
	 */
	public void setIntCount(double intCount) {
		this.intCount = intCount;
	}

	/**
	 * @return the markValue
	 */
	public double getMarkValue() {
		return markValue;
	}

	/**
	 * @param markValue
	 *            the markValue to set
	 */
	public void setMarkValue(double markValue) {
		this.markValue = markValue;
	}

	/**
	 * @param selectSql
	 * @param tableName
	 * @param whereSql
	 * @param nmNetworkTaskId
	 * @param intType
	 * @param intSale
	 * @param intCount
	 * @param markValue
	 */
	public NetWorkParam(String selectSql, String tableName, String whereSql,
			Long nmNetworkTaskId, int intType, double intSale, double intCount,
			double markValue) {
		this.selectSql = selectSql;
		this.tableName = tableName;
		this.whereSql = whereSql;
		this.nmNetworkTaskId = nmNetworkTaskId;
		this.intType = intType;
		this.intSale = intSale;
		this.intCount = intCount;
		this.markValue = markValue;
	}

	/**
	 * 無慘構造方法
	 */
	public NetWorkParam() {
	}

}
