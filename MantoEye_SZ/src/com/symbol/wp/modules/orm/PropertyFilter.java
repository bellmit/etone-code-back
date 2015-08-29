package com.symbol.wp.modules.orm;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;



/**
 * 与具体ORM实现无关的属性过滤条件封装类.
 * 
 * PropertyFilter主要记录页面中简单的搜索过滤条件,比Hibernate的Criterion要简单很多.
 * 可按项目扩展其他对比方式如大于、小于及其他数据类型如数字和日期.
 * 
 */
public class PropertyFilter {

	/**
	 * 多个属性间OR关系的分隔符.
	 */
	public static final String OR_SEPARATOR = "__";

	/**
	 * 属性比较类型.
	 * EQ 表示等于,LIKE 表示类似于,IN表示属于其中的一个
	 * GT 表示大于,LT表示小于,GE表示大于或者等于
	 * LE 表示小于或者等于
	 * NE 不等于
	 */
	public enum MatchType {
		EQ,LIKE,GT,LT,GE,LE,NE,IN,NOTIN,ISNULL,NOTNULL;
	}
	/**
	 * 查询条件数据类型
	 * 默认获取的页面查询条件为String类型
	 * 如果是其他类型
	 * 需显示指明并在HibernateWebUtils内部转换
	 * 以下枚举类型分别对应的数据类型为
	 * String,Long,Integer,Date,Boolean,Double,Float,Byte
	 * @author rankin
	 *
	 */
	public enum DataType {
		STRING,LONG,INTEGER,DATE,BOOLEAN,DOUBLE,FLOAT,BYTE;
	}
	
	private String propertyName;
	private Object value;
	
	private MatchType matchType = MatchType.EQ;

	public PropertyFilter() {
	}

	public PropertyFilter(final String propertyName, final Object value, final MatchType matchType) {
		this.propertyName = propertyName;
		this.value = value;
		this.matchType = matchType;
	}
//	
//	public PropertyFilter(final String propertyName, final Object value, final MatchType matchTypeGE,final MatchType matchTypeLE) {
//		this.propertyName = propertyName;
//		this.value = value;
//		this.matchType = matchType;
//	}

	/**
	 * 获取属性名称,可用'__'分隔多个属性,此时属性间是or的关系.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * 设置属性名称,可用'__'分隔多个属性,此时属性间是or的关系.
	 */
	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(final MatchType matchType) {
		this.matchType = matchType;
	}
//	/**
//	 * 返回该条件对应的SQL片段，
//	 * 待完成
//	 * 暂未完善，应用时应注意测试。
//	 * @return
//	 */
//	public String buildSql(){
//		//操作符
//		String o = "=";
//		String sql = "";
//		if (MatchType.EQ.equals(matchType)) {
//			sql = " and "+propertyName + " = "+ value;
//		}
//		if (MatchType.LIKE.equals(matchType)) {
//			sql =  " and "+propertyName + " like "+ value;
//		}
//		if(MatchType.IN.equals(matchType)) {
//			//String [] values=((String)value).split(",");
//			Object [] values=(Object [])value;
//			sql=Restrictions.in(propertyName, values);
//		}
//		if (MatchType.GT.equals(matchType)) {
//			sql = Restrictions.gt(propertyName, value);
//		}
//		if (MatchType.LT.equals(matchType)) {
//			sql = Restrictions.lt(propertyName, value);
//		}
//		if (MatchType.GE.equals(matchType)) {
//			sql = Restrictions.ge(propertyName, value);
//		}
//		if (MatchType.LE.equals(matchType)) {
//			sql = Restrictions.le(propertyName, value);
//		}
//		if(MatchType.NE.equals(matchType)){
//			sql = Restrictions.ne(propertyName, value);
//		}
//		if(MatchType.NOTIN.equals(matchType)){
//			//String [] values=((String)value).split(",");
//			Object [] values=(Object [])value;
//			sql=Restrictions.in(propertyName, values);
//			sql = Restrictions.not(Restrictions.in(propertyName, values));
//		}
//		if(MatchType.ISNULL.equals(matchType)){
//			sql = Restrictions.isNull(propertyName);
//		}
//		if(MatchType.NOTNULL.equals(matchType)){
//			sql = Restrictions.isNotNull(propertyName);
//		}
//		
//	}
	
}


