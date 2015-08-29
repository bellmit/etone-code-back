/**
 *  com.etone.mantoeye.analyse.service.queue.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.etone.mantoeye.analyse.domain.CurrentTask;

/**
 * 任務隊列
 * 
 * @author Wu Zhenzhen
 * @version Apr 27, 2012 4:14:29 PM
 */
public class CurrentTaskQueue {

	/**
	 * 私有構造方法
	 */
	private CurrentTaskQueue() {
	}

	/**
	 * 靜態私有化Queue對象
	 */
	private static Queue<CurrentTask> currentTaskQueue = null;

	/**
	 * 隊列中的任務數
	 */
	private static int currentQueueTaskCount = 0;

	/**
	 * 初始化任務隊列
	 * 
	 * @return
	 */
	public static synchronized Queue<CurrentTask> getQueue() {
		if (null == currentTaskQueue) {
			currentTaskQueue = new ConcurrentLinkedQueue<CurrentTask>();
		}
		return currentTaskQueue;
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
	public static synchronized CurrentTask remove() {
		return (CurrentTask) CurrentTaskQueue.getQueue().poll();
	}

	/**
	 * 當前任務加入隊列
	 * 
	 * @param currentTask
	 */
	public static synchronized void add(CurrentTask currentTask) {
		CurrentTaskQueue.getQueue().add(currentTask);
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
