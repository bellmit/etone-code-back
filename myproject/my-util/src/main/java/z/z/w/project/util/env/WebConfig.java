/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.env.WebConfig.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-8 上午08:49:20 
 *   LastChange: 2013-10-8 上午08:49:20 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.env;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;

import z.z.w.project.util.common.DataTools;

/**
 * z.z.w.project.util.env.WebConfig.java
 */
public class WebConfig {

	/**
	 * CONFIG PROPERTIES
	 */
	private Properties prop = null;
	private StringBuilder rootKey = null;
	protected String fileName = "global.cfg";

	/**
	 * <br>
	 * Created on: 2013-9-12 上午10:56:12
	 */
	public WebConfig() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-9-12 上午10:56:17
	 * 
	 * @param fileName
	 */
	public WebConfig(String fileName) {
		super();
		this.fileName = fileName;
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-9-12 上午10:56:30
	 */
	protected void init() {

		fileName = WebConfig.class.getClassLoader().getResource(fileName)
				.getPath();
		File file = new File(fileName);

		if (DataTools.isEmpty(file)) {
			System.out.println(fileName + " NOT EXISTS ! PORGRAMME EXIT.");
			System.exit(1);
		}

		try {
			prop = new Properties();
			rootKey = new StringBuilder();
			prop.load(new FileInputStream(file));
			setRootKey("common.cfg");
		} catch (Exception e) {
			System.out.println("    Properties load file [" + fileName
					+ "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-12 上午10:57:20
	 * 
	 * @param value
	 */
	public void setRootKey(String value) {
		rootKey.setLength(0);
		rootKey.append(value);
		rootKey.append(".");
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-12 上午10:57:47
	 * 
	 * @param subKey
	 * @return
	 */
	private String getValue(String subKey) {
		rootKey.append(subKey);
		String result = String.valueOf(prop.get(rootKey.toString()));
		rootKey.setLength(rootKey.length() - subKey.length());
		return DataTools.trimToEmpty(result);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-12 上午10:59:37
	 * 
	 * @param subKey
	 * @return
	 */
	public String getString(String subKey) {
		return getValue(subKey);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-12 上午10:59:37
	 * 
	 * @param subKey
	 * @return
	 */
	public long getLong(String subKey) {
		String value = getValue(subKey);
		if (!DataTools.isEmpty(value)) {
			return NumberUtils.toLong(value, -1);
		}
		return -1;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-12 上午10:59:37
	 * 
	 * @param subKey
	 * @return
	 */
	public int getInt(String subKey) {
		String value = getValue(subKey);
		if (!DataTools.isEmpty(value)) {
			return NumberUtils.toInt(value, -1);
		}
		return -1;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-12 上午10:59:37
	 * 
	 * @param subKey
	 * @return
	 */
	public float getFloat(String subKey) {
		String value = getValue(subKey);
		if (!DataTools.isEmpty(value)) {
			return NumberUtils.toFloat(value, -1);
		}
		return -1;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-12 上午10:59:37
	 * 
	 * @param subKey
	 * @return
	 */
	public double getDouble(String subKey) {
		String value = getValue(subKey);
		if (!DataTools.isEmpty(value)) {
			return NumberUtils.toDouble(value, -1);
		}
		return -1;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-12 上午10:59:33
	 * 
	 * @param subKey
	 * @return
	 */
	public boolean getBoolean(String subKey) {
		String value = getValue(subKey);
		if (!DataTools.isEmpty(value)) {
			value = ((value.equals("0")) || (value.equals("off"))) ? "false"
					: "true";
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

}
