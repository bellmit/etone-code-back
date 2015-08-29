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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import z.z.w.project.common.config.Global;
import z.z.w.project.common.config.InitPro;
import z.z.w.project.test.service.BackupDataService;
import z.z.w.project.test.vo.ExportData;
import z.z.w.project.test.vo.LoadData;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.env.Config;
import z.z.w.project.util.file.FileTools;

/**
 * 
 * z.z.w.project.test.MainRunner.java
 */
public class MainRunner extends InitPro {

	private BackupDataService backupDataService;
	private final Config config = new Config("$PRO_HOME/etc/config.properties");

	private final String backupDataPath = FileTools.addPathSeparator(config
			.getString("backupDataPath"));
	private final String tarBackupDataPath = FileTools.addPathSeparator(config
			.getString("tarBackupDataPath"));

	private final String EXPROT_OPTION;

	private List<ExportData> exportList = null;
	private List<LoadData> loadList = null;

	private final ExecutorService executer = Executors.newFixedThreadPool(4);

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
	 * Created on: 2013-11-7 下午12:31:37
	 */
	private void startBackupDataSerice() {
		try {
			List<String> backupTableNameList = backupDataService
					.getAllBackupTableNames();
			if (DataTools.isEmpty(backupTableNameList)) {
				LogTools.getLogger(getClass()).warn(
						"Backup table name is null,return .");
				return;
			}

			LogTools.getLogger(getClass()).debug("Backup table size : [{}].",
					backupTableNameList.size());

			prepareDataList(backupTableNameList);

			loadData();

			exportData();

			tarExportData();

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "startBackupDataSerice",
									"getAllBackupTableNames()", e.getMessage(),
									e.getCause(), e.getClass() });
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 上午09:45:04
	 */
	private void tarExportData() {

		String cmd = buildCMD();
		Process process = null;
		long startTime = DateTools.getCurrentDateToLong();
		try {
			// process = Runtime.getRuntime().exec(cmd);
			// process.waitFor();
			// process.getInputStream().close();
			// process.getOutputStream().close();
			// process.getErrorStream().close();
			// process.destroy();
			process = null;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "startBackupDataSerice",
									"tarExportData", e.getMessage(),
									e.getCause(), e.getClass() });
		} finally {
			try {
				if (!DataTools.isEmpty(process)) {
					// process.getInputStream().close();
					// process.getOutputStream().close();
					// process.getErrorStream().close();
					// process.destroy();
					process = null;
				}
			} catch (Exception e) {
			}
			long useTime = DateTools.getCurrentDateToLong() - startTime;
			LogTools.getLogger(getClass()).info(
					"Execute command [{}] complete, use time : [{}]s.",
					new Object[] { cmd, (useTime / 1000) });
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 上午09:51:58
	 * 
	 * @return
	 */
	private String buildCMD() {
		String date = DateTools.getParseDateToStr(DateTools.getCurrentDate(),
				DateTools.YYYYMMDD);

		StringBuffer sb = new StringBuffer();

		sb.append("tar -C ").append(tarBackupDataPath);
		sb.append(" -zcvf ").append(tarBackupDataPath);
		sb.append("iqbackupbasedata_").append(date);
		sb.append(".tgz ").append(backupDataPath);

		return sb.toString();
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:53:33
	 */
	private void loadData() {
		if (DataTools.isEmpty(loadList))
			return;

		for (LoadData ed : loadList) {
			// long startTime = DateTools.getCurrentDateToLong();
			try {
				// backupDataService.loadDataFromFile(ed);

				StringBuffer sb = new StringBuffer();
				sb.append("COMMIT; LOAD TABLE ").append(ed.getTableName());
				sb.append(" (").append(ed.getColumns());
				sb.append(") FROM '").append(ed.getFilePath());
				sb.append("' DELIMITED BY '|' ");
				// sb.append(" row delimited by '\n' ");
				sb.append("ESCAPES OFF QUOTES OFF WITH CHECKPOINT OFF;");

				LogTools.getLogger(getClass()).info("{}", sb.toString());

			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "startBackupDataSerice",
										"loadData", e.getMessage(),
										e.getCause(), e.getClass() });
			} finally {
				// long useTime = DateTools.getCurrentDateToLong() - startTime;
				// LogTools.getLogger(getClass()).info(
				// "Load data to table [{}] complete, use time : [{}]s.",
				// new Object[] { ed.getTableName(), (useTime / 1000) });
			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:53:30
	 */
	private void exportData() {

		if (DataTools.isEmpty(exportList))
			return;

		try {
			for (final ExportData ed : exportList) {

				Runnable runnable = new Runnable() {

					public void run() {
						long startTime = DateTools.getCurrentDateToLong();
						try {
							backupDataService.exportDataToFile(ed);
						} catch (Exception e) {
							LogTools.getLogger(getClass())
									.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
											new Object[] {
													"startBackupDataSerice",
													"exprortData-Runnable",
													e.getMessage(),
													e.getCause(), e.getClass() });
						} finally {
							long useTime = DateTools.getCurrentDateToLong()
									- startTime;
							LogTools.getLogger(getClass())
									.info("Export table :[{}] data to file complete, use time : [{}]s.",
											new Object[] { ed.getTableName(),
													(useTime / 1000) });
						}
					}
				};

				executer.execute(runnable);

			}
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "startBackupDataSerice",
									"exprortData", e.getMessage(),
									e.getCause(), e.getClass() });
		} finally {
			executer.shutdown();
			// service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午02:50:53
	 * 
	 * @param backupTableNameList
	 */
	private void prepareDataList(List<String> backupTableNameList) {

		try {

			for (String tableName : backupTableNameList) {
				try {

					tableName = DataTools.trimToEmpty(tableName);

					List<String> tableColumnList = backupDataService
							.getTableColumns(tableName);

					if (DataTools.isEmpty(tableColumnList))
						continue;

					StringBuffer sb = new StringBuffer();
					for (String column : tableColumnList) {
						sb.append(DataTools.trimToEmpty(column)).append(",");
					}

					String columns = sb.substring(0, sb.length() - 1)
							.toString();

					String filePath = backupDataPath + tableName + ".out";

					String exportOption = EXPROT_OPTION
							+ "set temporary option TEMP_EXTRACT_NAME1='"
							+ filePath + "' ; ";

					ExportData ed = new ExportData();
					ed.setColumns(columns);
					ed.setTableName(tableName);
					ed.setExportOption(exportOption);
					exportList.add(ed);

					LoadData ld = new LoadData();
					ld.setColumns(columns);
					ld.setFilePath(filePath);
					ld.setTableName(tableName);
					loadList.add(ld);

					// LogTools.getLogger(getClass()).debug("{}.",
					// ed.toString());
					// LogTools.getLogger(getClass()).debug("{}.",
					// ld.toString());

				} catch (Exception e) {
					LogTools.getLogger(getClass())
							.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
									new Object[] { "startBackupDataSerice",
											"backupData(for)", e.getMessage(),
											e.getCause(), e.getClass() });
				} finally {
				}
			}

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "startBackupDataSerice",
									"backupData()", e.getMessage(),
									e.getCause(), e.getClass() });
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午12:31:02
	 */
	private MainRunner() {
		super();
		EXPROT_OPTION = getExportOption();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午02:47:51
	 * 
	 * @return
	 */
	private String getExportOption() {
		StringBuffer sb = new StringBuffer();
		sb.append("set temporary option TEMP_EXTRACT_COLUMN_DELIMITER='|';\n");
		sb.append("set temporary option TEMP_EXTRACT_BINARY='OFF';\n");
		sb.append("set temporary option Temp_Extract_Null_As_Empty='ON';\n");
		return sb.toString();
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午01:59:27
	 */
	private void init() {
		try {
			ApplicationContext context = new FileSystemXmlApplicationContext(
					Global.SPRING_XML);

			backupDataService = (BackupDataService) context
					.getBean("backupDataService");
			exportList = new ArrayList<ExportData>();
			loadList = new ArrayList<LoadData>();
		} catch (BeansException e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "backupDataSerice", "init()",
									e.getMessage(), e.getCause(), e.getClass() });
			System.exit(0);
		}

	}

}
