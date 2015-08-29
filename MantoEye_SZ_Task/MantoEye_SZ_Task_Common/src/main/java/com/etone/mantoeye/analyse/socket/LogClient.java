package com.etone.mantoeye.analyse.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * SOCKET 記錄日誌操作
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月16日11:44:21
 * 
 */
public class LogClient {

	/**
	 * 日誌
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(LogClient.class);

	/**
	 * 客戶端接收日誌信息
	 * 
	 * @param logInfo
	 */
	public static void writeLog(String logInfo) {
		logger.debug("Enter Client writeLog send msg:[{}].", logInfo);

		Socket socket = null;
		OutputStream outputStream = null;
		PrintStream printStream = null;

		try {
			int port = NumberUtils.toInt(Constant.SOCKET_PORT, 8866);

			// 注意端口号要与服务器保持一致:8866
			socket = new Socket("127.0.0.1", port);
			logger.debug("Client connect socket :[127.0.0.1:{}]", port);
			// 获得对应socket的输入
			outputStream = socket.getOutputStream();

			// 建立数据流
			printStream = new PrintStream(outputStream);
			logInfo = StringUtils.trimToEmpty(logInfo);
			printStream.println(logInfo); // 将读取得字符串传给server

			logger.debug("Client PrintStream loginfo -- > [{}].", logInfo);

			// 关闭连接
			printStream.close();
			outputStream.close();
			socket.close();

		} catch (UnknownHostException e) {
			logger.error(
					"\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"Client單線程SOCKET寫日誌writeLog时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
		} catch (IOException e) {
			logger.error(
					"\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"Client單線程SOCKET寫日誌writeLog时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
		} finally {
			if (null != printStream) {
				printStream.close(); // 关闭数据输出流
			}
			printStream = null;
			try {
				if (null != outputStream) {
					outputStream.close(); // 关闭输出流
				}
				outputStream = null;
			} catch (IOException e) {
			}
			try {
				if (null != socket) {
					socket.close(); // 关闭socket
				}
				socket = null;
			} catch (IOException e) {
			}
		}
	}

}
