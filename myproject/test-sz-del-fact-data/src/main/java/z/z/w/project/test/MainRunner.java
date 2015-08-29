/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.space.moniter.MainRunner.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-22 下午03:53:00 
 *   LastChange: 2013-9-22 下午03:53:00 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import z.z.w.project.common.config.Global;
import z.z.w.project.common.config.InitPro;
import z.z.w.project.test.service.CheckFactTableServer;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;

/**
 * 
 * z.z.w.project.test.MainRunner.java
 */
public class MainRunner extends InitPro {

	private CheckFactTableServer checkFactTableServer = null;

	// private final Config config = new Config("$PRO_HOME/etc/config.properties");

	// private final String backupDataPath = FileTools.addPathSeparator(config.getString("backupDataPath"));
	// private final String tarBackupDataPath = FileTools.addPathSeparator(config.getString("tarBackupDataPath"));

	/***
	 * 
	 * <br>
	 * Created on: 2013-11-7 下午12:30:25
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainRunner mainRunner = new MainRunner();
		mainRunner.startBackupDataSerice();
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:21:02
	 */
	public MainRunner() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:21:23
	 */
	private void init() {
		try {
			ApplicationContext context = new FileSystemXmlApplicationContext(Global.SPRING_XML);
			checkFactTableServer = (CheckFactTableServer) context.getBean("checkFactTableServer");
		} catch (BeansException e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "backupDataSerice", "init()", e.getMessage(), e.getCause(), e.getClass() });
			System.exit(0);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午12:31:37
	 */
	private void startBackupDataSerice() {
		try {

			List<String> tableNameList = checkFactTableServer.getNewTableNameList();

			if (DataTools.isEmpty(tableNameList)) {
				LogTools.getLogger(getClass()).warn("New table is null,return .");
				return;
			}

			LogTools.getLogger(getClass()).info("New table size : [{}].", tableNameList.size());

			checkTableDataCount(tableNameList);

		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "startBackupDataSerice", "getAllBackupTableNames()", e.getMessage(), e.getCause(), e.getClass() });
			System.exit(1);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:49:35
	 * 
	 * @param tableNameList
	 */
	private void checkTableDataCount(List<String> tableNameList) {
		for (String tableName : tableNameList) {

			long startTime = DateTools.getCurrentDateToLong();
			tableName = DataTools.trimToEmpty(tableName);

			long count = checkFactTableServer.getCurrentTableDataCount(tableName);

			LogTools.getLogger(getClass()).info("Check table :[{}], has data [{}]t.", tableName, count);

			if (count == 0) {
				LogTools.getLogger(getClass()).warn("Table :[{}] hasn't data, update fact table status 5.");
				checkFactTableServer.updateCurrentFactTableDataStatus(tableName);
			}

			long useTime = DateTools.getCurrentDateToLong() - startTime;
			LogTools.getLogger(getClass()).info("Check fact table data use :[{}]s.", (useTime / 1000));
		}
	}

}
