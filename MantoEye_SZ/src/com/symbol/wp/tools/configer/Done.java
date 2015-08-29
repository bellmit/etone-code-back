package com.symbol.wp.tools.configer;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.tools.file.FileUtils;

public class Done {
	private Logger logger = LoggerFactory.getLogger(Done.class);

	// 需要拷贝的文件
	private static final String[] beans = new String[] { "TbBaseDepartment",
			"tbBaseOpLog", "TbBasePermissions", "TbBaseRoleGroup",
			"TbBaseRoles", "TbBaseUserInfo", "TbBaseUserRole",
			"TbBaseRolePermis" };

	private static String basepath = "F:/Workspace7/wcmp/";

	public static void main(String[] args) {
		Done done = new Done();
		done.domain(basepath);
	}

	public boolean domain(String wcmppath) {
		if (wcmppath != null) {
			if (!wcmppath.trim().endsWith("/")) {
				wcmppath = wcmppath.trim() + "/";
			}
			String topath = wcmppath + "src/com/symbol/wp/core/entity/";
			String frompath = wcmppath
					+ "src/com/symbol/wp/tools/configer/sybase/";
			for (int i = 0; i < beans.length; i++) {
				copyfile(frompath, topath, beans[i], beans[i] + ".java");
			}
		}
		return true;
	}

	public boolean copyfile(String fromfolder, String tofolder,
			String fromename, String toname) {
		File file = new File(fromfolder + fromename);
		File file1 = new File(tofolder + toname);
		if (!file.exists()) {
			logger.info("发生错误！");
			return false;
		}
		if (file1.exists()) {
			file1.delete();
		}
		try {
			FileUtils.copyFileToDir(tofolder, file, toname);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

}
