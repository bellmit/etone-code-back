/**
 * z.z.w.process.handler.StreamHandler.java
 */
package z.z.w.process.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import z.z.w.log.LogUtil;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-19 上午11:46:46
 */
public class StreamHandler extends Thread {

	/**
	 * 程序正確執行結果碼
	 */
	private static final String ERROR_CODE = "ERROR";
	/**
	 * stream流
	 */
	private BufferedReader bufferedReader = null;

	/**
	 * 消息類型：<br />
	 * 0:正常結束 <br />
	 * 1:異常結束
	 */
	private String messageType = "";

	/**
	 * 流結尾
	 */
	private boolean endOfStream = false;

	/**
	 * <br>
	 * Created on: 2013-8-19 上午11:47:34
	 * 
	 * @param bufferedReader
	 * @param messageType
	 */
	public StreamHandler(InputStream inputStream, String messageType) {
		super();
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
				if (messageType.equals(ERROR_CODE)) {
					LogUtil.getLogger(getClass()).error(
							"StreamHandler-->[{}].", line);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
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
