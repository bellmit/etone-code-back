/**
 * z.z.w.env.Config.java
 */
package z.z.w.env;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.math.NumberUtils;

import z.z.w.common.DataTools;
import z.z.w.common.FileTools;

/**
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 3:50:54 PM
 */
public final class Config {

	/**
	 * CONFIG PROPERTIES
	 */
	private Properties prop = null;
	private StringBuilder rootKey = null;
	private String fileName = "$PRO_HOME/etc/global.cfg";

	/**
	 * DEFAULT CONFIG FILE : "$PRO_HOME/etc/global.cfg" <br>
	 * Created on: Nov 21, 2012 3:57:54 PM
	 */
	public Config() {
		init();
	}

	/**
	 * GIVE CONFIG FILE : fileName <br>
	 * Created on: Nov 21, 2012 3:58:47 PM
	 * 
	 * @param fileName
	 */
	public Config(String fileName) {
		this.fileName = fileName;
		init();
	}

	/**
	 * init <br>
	 * Created on: Nov 21, 2012 3:55:47 PM
	 */
	private void init() {

		prop = new Properties();
		rootKey = new StringBuilder();

		fileName = FileTools.replaceProHome(fileName);
		File file = new File(fileName);

		if (DataTools.isEmpty(file)) {
			System.out.println(fileName + " NOT EXISTS ! PORGRAMME EXIT.");
			System.exit(1);
		}

		try {
			prop.load(new FileInputStream(file));

			setRootKey("common.cfg");

		} catch (FileNotFoundException e) {
			System.out.println("    Properties load file [" + fileName
					+ "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		} catch (IOException e) {
			System.out.println("    Properties load file [" + fileName
					+ "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		}

	}

	/**
	 * Set root key <br>
	 * Created on: Nov 21, 2012 4:19:39 PM
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
	 * Created on: Nov 21, 2012 4:22:09 PM
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
	 * Get config property string value <br>
	 * Created on: Nov 21, 2012 4:28:29 PM
	 * 
	 * @param subKey
	 * @return
	 */
	public String getString(String subKey) {
		return getValue(subKey);
	}

	/**
	 * Get config property long value <br>
	 * Created on: Nov 21, 2012 4:28:16 PM
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
	 * Get config property int value <br>
	 * Created on: Nov 21, 2012 4:26:51 PM
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
	 * Get config property float value <br>
	 * Created on: Nov 21, 2012 4:26:13 PM
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
	 * Get config property double value <br>
	 * Created on: Nov 21, 2012 4:25:26 PM
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
	 * Get config property boolean value <br>
	 * Created on: Nov 21, 2012 4:23:33 PM
	 * 
	 * @param subKey
	 * @return
	 */
	public boolean getBoolean(String subKey) {
		String value = getValue(subKey);
		if (!DataTools.isEmpty(value)) {
			value = (value.equals("0")) ? "false" : "true";
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Config [fileName=" + fileName + "]";
	}
}
