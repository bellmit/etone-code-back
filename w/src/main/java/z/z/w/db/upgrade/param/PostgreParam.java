/**
 * z.z.w.db.upgrade.param.PostgreParam.java
 */
package z.z.w.db.upgrade.param;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-15 上午11:55:26
 */
public class PostgreParam extends ConnParam {

	/**
	 * <br> Created on: 2013-7-15 上午11:56:03 
	 */
	private static final long serialVersionUID = 5362910207864242074L;

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:55:26
	 */
	public PostgreParam() {
		setPort(5432);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.param.ConnParam#getDriver()
	 */
	@Override
	public String getDriver() {
		return "org.postgresql.Driver";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.param.ConnParam#getUrl()
	 */
	@Override
	public String getUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append("jdbc:postgresql://");
		sb.append(getServer());
		sb.append(":");
		sb.append(getPort());
		sb.append("/");
		sb.append(getDatabase());

		return sb.toString();
	}

}
