/**
 * z.z.w.db.upgrade.param.MysqlParam.java
 */
package z.z.w.db.upgrade.param;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-20 下午11:09:33
 */
public class MysqlParam extends ConnParam {

	/**
	 * <br>
	 * Created on: 2013-8-20 下午11:09:37
	 */
	private static final long serialVersionUID = 3788558052611933880L;

	/**
	 * <br>
	 * Created on: 2013-8-20 下午11:09:48
	 */
	public MysqlParam() {
		setPort(3306);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.param.ConnParam#getDriver()
	 */
	@Override
	public String getDriver() {
		// return "com.mysql.jdbc.Driver";
		return "org.gjt.mm.mysql.Driver";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.param.ConnParam#getUrl()
	 */
	@Override
	public String getUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:mysql://");
		sb.append(getServer());
		sb.append("/");
		sb.append(getDatabase());
		sb.append("?user=");
		sb.append(getUser());
		sb.append("&password=");
		sb.append(getPassword());
		sb.append("&useUnicode=true&characterEncoding=utf-8");

		return sb.toString();
	}
}
