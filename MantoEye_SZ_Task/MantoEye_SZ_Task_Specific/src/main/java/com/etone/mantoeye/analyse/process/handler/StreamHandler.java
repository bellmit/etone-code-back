/**
 *  com.etone.mantoeye.analyse.process.handler.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.process.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * Process調用外部程序時對輸入、輸出的操作
 * 
 * @author Wu Zhenzhen
 * @version Apr 28, 2012 2:30:43 PM
 */
public class StreamHandler extends Thread {

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(StreamHandler.class);
	/**
	 * stream流
	 */
	private BufferedReader bufferedReader;

	/**
	 * 消息類型：<br />
	 * 0:正常結束 <br />
	 * 1:異常結束
	 */
	private String messageType;

	/**
	 * 流結尾
	 */
	private boolean endOfStream = false;

	/**
	 * 構造方法
	 * 
	 * @param inputStream
	 * @param messageType
	 */
	public StreamHandler(InputStream inputStream, String messageType) {
		this.bufferedReader = new BufferedReader(new InputStreamReader(
				inputStream));
		this.messageType = messageType;
	}

	/**
	 * 流處理
	 * 
	 * @throws IOException
	 */
	public void pumpStream() throws IOException {
		if (!endOfStream) {
			String line = bufferedReader.readLine();
			if (null != line) {
				if (messageType.equals(Constant.ERROR_CODE)) {
					logger.error("StreamHandler-->[{}].", line);
				} else {
					// ignore
					// if (line.contains("taskFinish")) {
					// queue.reduce();
					// }
				}
			} else {
				endOfStream = true;
			}
		}
	}

	/**
	 * 線程執行
	 */
	public void run() {
		try {
			while (!endOfStream) {
				// 流處理
				pumpStream();
			}
			bufferedReader.close();
		} catch (IOException ioe) {
			// ignore
		}
	}
}
