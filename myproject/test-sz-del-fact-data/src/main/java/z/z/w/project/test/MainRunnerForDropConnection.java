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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import z.z.w.project.common.config.Global;
import z.z.w.project.common.config.InitPro;
import z.z.w.project.test.service.CheckFactTableServer;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.env.Config;
import z.z.w.project.util.file.FileTools;

/**
 * 
 * z.z.w.project.test.MainRunnerForDropConnection.java
 */
public class MainRunnerForDropConnection extends InitPro {

	private CheckFactTableServer checkFactTableServer = null;

	private static final long idx = 1000 * 60 * 60 * 4;

	private final Config config = new Config("$PRO_HOME/etc/config.properties");

	private final String innerIp = FileTools.addPathSeparator(config.getString("innerIp"));
	private final String outterIp = FileTools.addPathSeparator(config.getString("outterIp"));

	private final List<String> innerIpArr = Arrays.asList(innerIp.split(","));
	private final List<String> outterIpArr = Arrays.asList(outterIp.split(","));

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
		MainRunnerForDropConnection mainRunner = new MainRunnerForDropConnection();
		mainRunner.startBackupDataSerice();
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:21:02
	 */
	public MainRunnerForDropConnection() {
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

			List<Map<String, Object>> tableNameList = checkFactTableServer.getIQConnectionList();

			if (DataTools.isEmpty(tableNameList)) {
				LogTools.getLogger(getClass()).warn("Connection is null,return .");
				return;
			}

			LogTools.getLogger(getClass()).info("Connection size : [{}].", tableNameList.size());

			checkTableDataCount(tableNameList);

			LogTools.getLogger(getClass()).info("Check complete.");

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
	private void checkTableDataCount(List<Map<String, Object>> tableNameList) {
		for (Map<String, Object> connMap : tableNameList) {

			try {
				// LastReqTime=2014-01-12 15:02:16.447,ConnHandle=79,
				long startTime = DateTools.getCurrentDateToLong();
				long currentTime = startTime;

				Object connId = connMap.get("ConnHandle");
				Object lastReqTime = connMap.get("LastReqTime");
				Object nodeAddr = connMap.get("NodeAddr");

				LogTools.getLogger(getClass()).info("LastReqTime : [{}]=[{}].", lastReqTime, DateTools.getCurrentDateStr());

				long lastTime = DateTools.getDateToLong(DateTools.getDate(String.valueOf(lastReqTime), DateTools.DEFAUL_PARSE + ".SSS"));

				// common.cfg.outterIp=10.201.106.43,192.168.1.81
				if (outterIpArr.contains(nodeAddr)) {
					LogTools.getLogger(getClass()).info("Drop this time connection : [({}){}==={}].", new Object[] { connId, nodeAddr, lastReqTime });
					checkFactTableServer.dropConnection(connId);
				}

				if (currentTime - idx >= lastTime) {
					// common.cfg.innerIp=192.168.1.22,192.168.1.12
					if (!innerIpArr.contains(nodeAddr) && !DataTools.isEmpty(nodeAddr)) {
						LogTools.getLogger(getClass()).info("Drop this time connection : [({}){}==={}].", new Object[] { connId, nodeAddr, lastReqTime });
						checkFactTableServer.dropConnection(connId);
					} else
						LogTools.getLogger(getClass()).info("Don't drop this time connection : [({}){}==={}].", new Object[] { connId, nodeAddr, lastReqTime });
				}

				long useTime = DateTools.getCurrentDateToLong() - startTime;
				LogTools.getLogger(getClass()).info("Check fact table data use :[{}]s.", (useTime / 1000));
			} catch (Exception e) {
				LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "startBackupDataSerice", "checkTableDataCount()", e.getMessage(), e.getCause(), e.getClass() });
			}
		}
	}
}
