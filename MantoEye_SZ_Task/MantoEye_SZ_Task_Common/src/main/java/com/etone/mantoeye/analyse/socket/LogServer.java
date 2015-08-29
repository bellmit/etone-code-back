package com.etone.mantoeye.analyse.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.domain.CurrentTask;
import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.socket.queue.WriteLogQueue;
import com.etone.mantoeye.analyse.util.FileLocker;
import com.etone.mantoeye.analyse.util.constant.Constant;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * SOCKET 記錄日誌操作
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月16日14:09:54
 */
public class LogServer {

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(LogServer.class);

	/**
	 * SOCKET 服務對象
	 */
	private static ServerSocket server = null;

	/**
	 * SOCKET 服務對象
	 */
	private static Socket socket = null;

	/**
	 * SOCKET記錄日誌線程鎖文件
	 */
	// private static final String LOCKFILE_SOCKET = StringUtils.join("../",
	// Constant.CONFIG_PATH, "socketMain.lock");

	private static final String LOCKFILE_SOCKET = Constant.APP_HOME
			+ Constant.SEPARATOR_CHAR + Constant.CONFIG_PATH
			+ "socketMain.lock";

	/**
	 * SOCKET 記錄日誌操作主方法
	 */
	public static void main(String[] args) {

		// 構造文件鎖
		FileLocker locker = new FileLocker(LOCKFILE_SOCKET);

		if (locker.tryLock()) {

			logger.debug("Server Write log socket server start...");
			Thread socket = new Thread(new Runnable() {
				public void run() {
					logger.debug("Server Read Log Info To Queue thread.");
					LogServer.readLogInfoToQueue();
				}
			});
			socket.setPriority(7);
			socket.start();

			// // 队列写日志
			Thread log = new Thread(new Runnable() {
				public void run() {
					logger.debug("Server Write Log From Queue thread.");
					writeLogFromQueue();
				}
			});
			log.setPriority(9);
			log.start();

		}
	}

	/**
	 * 從隊列中取日誌信息并寫入數據庫中
	 */
	protected static void writeLogFromQueue() {
		logger.debug("1---->Server start write log from queue.");
		IExecTaskManager manager = null;

		while (true) {
			logger.debug(
					"2---->[while (true)] Server WriteLogQueue isEmpty --> [{}].",
					WriteLogQueue.getQueue().isEmpty());

			while (!WriteLogQueue.getQueue().isEmpty()) {

				if (null == manager) {
					manager = new ExecTaskManagerImpl();
				}

				logger.debug(
						"4---->[while (!WriteLogQueue.getQueue().isEmpty())] WriteLogQueue has [{}] and size [{}].",
						new Object[]{WriteLogQueue.getQueue().toArray(),
								WriteLogQueue.getQueue().size()});

				String logInfo = WriteLogQueue.getQueue().remove();

				logger.debug(
						"5---->[while (!WriteLogQueue.getQueue().isEmpty())] Server socket queue remove log is [{}].",
						logInfo);

				logger.debug(
						"5.1---->FINNALLY---[while (!WriteLogQueue.getQueue().isEmpty())] WriteLogQueue has [{}] and size [{}].",
						new Object[]{WriteLogQueue.getQueue().toArray(),
								WriteLogQueue.getQueue().size()});

				if (StringUtils.containsIgnoreCase(
						StringUtils.trimToEmpty(logInfo), "log=")) {

					logger.debug("6---->[while (!WriteLogQueue.getQueue().isEmpty())] start write log.");

					// 將完成任務寫入日誌裱中
					writeLog(StringUtils.substring(logInfo, 4), manager);

					logger.debug(
							"7---->[while (!WriteLogQueue.getQueue().isEmpty())] [SOCKET writeLog [{}] complete and remove it ] WriteLogFromQueue size [{}]",
							logInfo, WriteLogQueue.getQueue().size());

				} else if (StringUtils.containsIgnoreCase(
						StringUtils.trimToEmpty(logInfo), "state=")) {

					logger.debug("8---->[while (!WriteLogQueue.getQueue().isEmpty())] start update log.");

					// 日誌更新任務操作狀態
					String[] param = StringUtils.split(
							StringUtils.substring(logInfo, 6), ';');

					updateFlag(param[0], param[1], manager);

					logger.debug(
							"9---->[while (!WriteLogQueue.getQueue().isEmpty())] [SOCKET updateFlag [{}]  update sucess ] WriteLogFromQueue size [{}]",
							logInfo, WriteLogQueue.getQueue().size());
				} else {
					logger.info(
							"10---->[while (!WriteLogQueue.getQueue().isEmpty())] Log info anaylse error. [{}].",
							logInfo);
				}

			}

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
		}

	}
	/**
	 * 日誌更新任務操作狀態
	 * 
	 * @param currentTaskId
	 * @param intStatus
	 * @param manager
	 */
	private static void updateFlag(String currentTaskId, String intStatus,
			IExecTaskManager manager) {
		logger.debug(
				"[updateFlag] ---- Start update log, task id:[{}] status:[{}]",
				currentTaskId, intStatus);

		// ICommonTaskManager manager = null;

		// try {
		boolean flag = true;
		// 怕LOCK TALBE,如果LOCK TABLE时,就等待循环执行
		int i = 0;
		while (flag) {
			if (i++ > 100) {
				logger.warn("Socket log update currentTask process error , break update process.");
				manager = null;
				break;
			}

			// if (null == manager) {
			// manager = new CommonTaskManagerImpl();
			// }

			try {

				long intTaskId = NumberUtils.toLong(currentTaskId);
				int intState = NumberUtils.toInt(intStatus);

				logger.debug("[updateFlag -- start] ---- UPDATE [{}.{}] TASK.",
						intTaskId, intState);

				// 判斷表是否鎖
				// while (manager.tableIsLocks("tbTask"));

				manager.updateCurrentTaskState(intTaskId, intState);
				logger.debug("[updateFlag -- end] ---- UPDATE [{}.{}] TASK.",
						intTaskId, intState);

				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次:运行完成的任务,更新任务ID[{}]记录,操作SOCKET时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, currentTaskId, e.getMessage(),
									e.getCause(), e.getClass()});

				flag = true;
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
				}
			}
			// finally {
			// manager = null;
			// }
		}

		// } finally {
		manager = null;
		// }
	}

	/**
	 * 將完成任務寫入日誌裱中
	 * 
	 * @param currentTaskId
	 * @param manager
	 */
	private static void writeLog(String currentTaskId, IExecTaskManager manager) {
		logger.debug("[writeLog] ---- Start write log,task id:[{}].",
				currentTaskId);

		// ICommonTaskManager manager = null;

		// try {
		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 100) {
				logger.warn("Socket log complete currentTask process error , break complete process.");
				manager = null;
				break;
			}

			// if (null == manager) {
			// manager = new CommonTaskManagerImpl();
			// }

			try {
				long taskId = NumberUtils.toLong(currentTaskId);

				CurrentTask currentTask = manager.getCurrentTask(taskId);

				if (null != currentTask) {
					logger.debug("[writeLog -- start] ---- COMPLETE {} TASK.",
							currentTask.getIntTaskId());

					manager.completeCurrentTask(currentTask);

					logger.debug("[writeLog -- end] ---- COMPLETE {} TASK.",
							currentTask.getIntTaskId());
				}

				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次:运行完成的任务,删除任务ID[{}]记录,写SOCKET日志表时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, currentTaskId, e.getMessage(),
									e.getCause(), e.getClass()});

				flag = true;
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
				}
			}
			// finally {
			// manager = null;
			// }
		}

		// } finally {
		manager = null;
		// }
	}

	/**
	 * 讀日誌信息到隊列中 <br>
	 * Created on: May 4, 2012 11:13:10 AM
	 * 
	 */
	public static void readLogInfoToQueue() {
		// protected static void readLogInfoToQueue() {

		String logInfo = "";
		DataInputStream dataInputStream = null;
		InputStream inputStream = null;

		try {
			int port = NumberUtils.toInt(Constant.SOCKET_PORT, 8866);

			// 在端口8866注册服务
			server = new ServerSocket(port);

			while (true) {
				socket = server.accept(); // 监听窗口,等待连接
				logger.debug("Server Listen port:[{}],wait connection.", port);

				// 获得对应Socket的输入
				inputStream = socket.getInputStream();
				// 建立数据流
				dataInputStream = new DataInputStream(inputStream);

				// 读入从client传来的字符串
				logInfo = dataInputStream.readLine();
				logger.debug("Socket Server accept:[{}]", logInfo);

				if (StringUtils.isBlank(StringUtils.trimToEmpty(logInfo))) {
					logger.warn("Socket client send message is empty, break listen socket.");
					break;
				}

				// -- 2011年11月16日14:24:53 放入队列中 2011年11月16日14:51:32 完成
				WriteLogQueue.add(logInfo);

				logger.debug(
						"Server Log info [{}] to queue, WriteLogQueue has [{}].",
						new Object[]{logInfo, WriteLogQueue.getQueue().size(),
								WriteLogQueue.getQueue().toArray()});

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}

				dataInputStream.close();
				inputStream.close();
				socket.close();

				// 从队列中读取任务
				// writeLogFromQueue();
			}
		} catch (IOException e) {
			logger.error(
					"\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"單線程SOCKET寫日誌server时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
		} finally {
			logger.debug("Closed stream .");
			try {
				if (null != dataInputStream) {
					dataInputStream.close();
				}
				dataInputStream = null;
			} catch (IOException e) {
			}
			try {
				if (null != inputStream) {
					inputStream.close();
				}
				inputStream = null;
			} catch (IOException e) {
			}
			try {
				if (null != socket) {
					socket.close();
				}
				socket = null;
			} catch (IOException e) {
			}
		}
	}
}
