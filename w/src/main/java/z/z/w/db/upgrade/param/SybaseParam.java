/**
 * z.z.w.db.upgrade.param.SybaseParam.java
 */
package z.z.w.db.upgrade.param;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-15 上午11:56:17
 */
public class SybaseParam extends ConnParam {

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:56:52
	 */
	private static final long serialVersionUID = -8652010682834694904L;

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:56:17
	 */
	public SybaseParam() {
		setPort(5000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.param.ConnParam#getDriver()
	 */
	@Override
	public String getDriver() {
		return "com.sybase.jdbc3.jdbc.SybDriver";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.param.ConnParam#getUrl()
	 */
	@Override
	public String getUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:sybase:Tds:");
		sb.append(getServer());
		sb.append(":");
		sb.append(getPort());
		sb.append("/");
		sb.append(getDatabase());
		sb.append("?jconnect_version=6");

		return sb.toString();
	}

}
