/**
 * z.z.w.db.upgrade.param.MicroParam.java
 */
package z.z.w.db.upgrade.param;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-15 上午11:54:28
 */
public class MicroParam extends ConnParam {

	/**
	 * <br> Created on: 2013-7-15 上午11:55:08 
	 */
	private static final long serialVersionUID = -1789859000182600104L;

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:54:28
	 */
	public MicroParam() {
		setPort(1433);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.param.ConnParam#getDriver()
	 */
	@Override
	public String getDriver() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.param.ConnParam#getUrl()
	 */
	@Override
	public String getUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:sqlserver://");
		sb.append(getServer());
		sb.append(":");
		sb.append(getPort());
		sb.append(";");
		sb.append("DatabaseName=");
		sb.append(getDatabase());

		return sb.toString();
	}

}
