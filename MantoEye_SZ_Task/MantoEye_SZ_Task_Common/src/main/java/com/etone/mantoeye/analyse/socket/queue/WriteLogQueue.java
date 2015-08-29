/**
 *  com.etone.mantoeye.analyse.socket.queue.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.socket.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * SOCKET寫任務日誌隊列
 * 
 * @author Wu Zhenzhen
 * @version May 4, 2012 10:40:46 AM
 */
public class WriteLogQueue {
	/**
	 * 私有構造方法
	 */
	private WriteLogQueue() {
	}

	/**
	 * 靜態私有化Queue對象
	 */
	private static Queue<String> currentLogInfoQueue = null;

	/**
	 * 隊列中的任務數
	 */
	private static int currentQueueTaskCount = 0;

	/**
	 * 初始化任務隊列
	 * 
	 * @return
	 */
	public static synchronized Queue<String> getQueue() {
		if (null == currentLogInfoQueue) {
			currentLogInfoQueue = new ConcurrentLinkedQueue<String>();
		}
		return currentLogInfoQueue;
	}

	/**
	 * 任務隊列數減一
	 */
	public static synchronized void reduce() {
		if (0 == currentQueueTaskCount)
			return;
		currentQueueTaskCount--;
	}

	/**
	 * 任務隊列數加一
	 */
	public static synchronized void plusQueueTaskCount() {
		currentQueueTaskCount++;
	}

	/**
	 * 當前任務移出隊列
	 * 
	 * @return
	 */
	public static synchronized String remove() {
		return (String) WriteLogQueue.getQueue().poll();
	}

	/**
	 * 當前任務加入隊列
	 * 
	 * @param String
	 */
	public static synchronized void add(String logInfo) {
		WriteLogQueue.getQueue().add(logInfo);
	}

	/**
	 * 返回當前任務隊列總數
	 * 
	 * @return
	 */
	public static int getCurrentQueueTaskCount() {
		return currentQueueTaskCount;
	}

}
