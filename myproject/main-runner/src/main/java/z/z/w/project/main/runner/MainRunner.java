/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.main.runner.MainRunner.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午07:33:15 
 *   LastChange: 2013-8-20 下午07:33:15 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.main.runner;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import z.z.w.common.DataTools;
import z.z.w.common.FileTools;
import z.z.w.common.ThreadTools;
import z.z.w.project.main.config.Global;
import z.z.w.project.util.common.LogTools;
import z.z.w.vo.AdapterType;
import z.z.w.vo.ServerAdapter;

/**
 * z.z.w.project.main.runner.MainRunner.java
 */
public final class MainRunner {

	static {
		// load log4j config xml
		if (DataTools.isEmpty(Global.LOG_CFG_FILE) || !(new File(Global.LOG_CFG_FILE).exists())) {

			System.out.println("LOG4J CONFIG [" + Global.LOG_CFG_FILE + "] NOT EXIST. PROGREAM EXIT!");
			System.exit(0);
		}

		LogTools.setLogBackDomConfig(Global.LOG_CFG_FILE);
	}

	// static {
	//
	// boolean status = CertificateCoder.verifyCertificate(new Date(),
	// TestMacAddress.keyStorePath, TestMacAddress.keyStorePassword,
	// TestMacAddress.alias);
	//
	// if (!status) {
	// LogTools.getLogger(MainRunner.class)
	// .warn("The project of certificate is overtime. project is system.exit(0).");
	// System.exit(0);
	// } else {
	// status = CertificateCoder
	// .verifyCertificate(Global.CERTIFICATE_FILE);
	// if (!status) {
	// LogTools.getLogger(MainRunner.class)
	// .warn("The project of certificate is overtime. project is system.exit(0).");
	// System.exit(0);
	// } else
	// LogTools.getLogger(MainRunner.class).info(
	// "The project of certificate is ok.");
	// }
	//
	// }

	static {
		// check Only one instance exec.
		if (!FileTools.tryLock(Global.LOCK_FILE)) {
			LogTools.getLogger(MainRunner.class).warn("Another instance is running!");
			System.exit(0);
		}
	}

	/** Server config */
	private final static String XML_SERVER_ADAPTER = "//server/adapter";
	private Map<String, Runnable> serverMap = null;
	private ExecutorService service = Executors.newCachedThreadPool();

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:33:15
	 */
	private MainRunner() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:42:13
	 */
	private void init() {
		serverMap = new LinkedHashMap<String, Runnable>();

		List<ServerAdapter> list = Global.getServerAdapterList(XML_SERVER_ADAPTER);

		if (DataTools.isEmpty(list)) {
			LogTools.getLogger(getClass()).warn("Server class info is null,System.exit(0);");
			System.exit(0);
		}

		constructClassInfos(list);

		if (DataTools.isEmpty(serverMap)) {
			LogTools.getLogger(getClass()).warn("Server class instance is null,System.exit(0);");
			System.exit(0);
		}

		// TODO -- 2013-8-23 15:36:20 自定義ThreadFactory
		// service = Executors.newCachedThreadPool(new ThreadFactory() {
		//
		// public Thread newThread(Runnable r) {
		// LogTools.getLogger(getClass()).info("---------{}", r.getClass());
		// String threadName = r.getClass().getSimpleName();
		// return new ThreadMoniter(threadName, r);
		// }
		// });

	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:52:42
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void constructClassInfos(List<ServerAdapter> list) {
		Iterator<ServerAdapter> it = list.iterator();
		while (it.hasNext()) {
			final ServerAdapter savo = it.next();

			List<AdapterType> adapterTypeList = savo.getAdapterTypeList();
			if (DataTools.isEmpty(adapterTypeList))
				continue;

			Iterator<AdapterType> itt = adapterTypeList.iterator();
			while (itt.hasNext()) {
				final AdapterType at = itt.next();
				final Class<Runnable> clazz = (Class<Runnable>) Global.getServerClass(at.getClassName());
				if (DataTools.isEmpty(clazz))
					continue;

				final Runnable iServer = (Runnable) Global.getInterfaceInstance(clazz);
				if (DataTools.isEmpty(iServer))
					continue;

				int threadNum = savo.getThreadNum();

				if (threadNum == 1)
					serverMap.put(at.getName(), iServer);
				else
					while (threadNum-- > 0) {
						serverMap.put(at.getName() + "_" + threadNum, iServer);
					}
			}

		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:33:15
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainRunner mainRunner = new MainRunner();
		mainRunner.startServer();
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:53:59
	 */
	private void startServer() {
		try {
			if (DataTools.isEmpty(service)) {
				LogTools.getLogger(getClass()).warn("ExecutorService init faild,System.exit(0);");
				System.exit(0);
			}

			for (Map.Entry<String, Runnable> entry : serverMap.entrySet()) {
				String key = entry.getKey();
				LogTools.getLogger(getClass()).info("Server thread : [{}] started ...", key);
				Runnable iServer = entry.getValue();
				service.execute(iServer);
				ThreadTools.sleepSeconds(2);
			}

			// service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (Exception e) {
			LogTools.getLogger(getClass()).warn("Server runnable execute error [{}].", e.getMessage());
		} finally {
			service.shutdown();
		}
	}

}
