package com.symbol.wp.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private static PropertiesUtil pu = null;
	private InputStream inputStream = this.getClass().getClassLoader()
			.getResourceAsStream("wcmp_config.properties");
	private static Properties p = null;

	private PropertiesUtil() {
		if (p == null) {
			p = new Properties();
		}
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static PropertiesUtil getInstance() {
		if (PropertiesUtil.pu == null) {
			pu = new PropertiesUtil();
		}
		return pu;
	}

	public String getProperty(String key) {
		return p.getProperty(key);
	}

	public static void main(String args[]) {
		// logger.info(PropertiesUtil.getInstance().getProperty("system.role.look.all"));
	}
}
