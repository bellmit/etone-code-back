/**
 *  com.etone.mantoeye.util.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

/**
 * @author Wu Zhenzhen
 * @version Apr 25, 2012 10:05:25 AM
 */
public class FileLocker {

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FileLocker.class);
	/**
	 * A token representing a lock on a region of a file.
	 */
	private FileLock lock = null;

	/**
	 * A channel for reading, writing, mapping, and manipulating a file.
	 */
	private FileChannel channel = null;

	/**
	 * Instances of this class support both reading and writing to a random
	 * access file.
	 */
	private RandomAccessFile randomAccessFile = null;

	/**
	 * 構造文件鎖標誌文件
	 * 
	 * @param fileName
	 */
	public FileLocker(File file) {

		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
			logger.debug("Random Access File --> [{}].", randomAccessFile);
			if (null != randomAccessFile) {
				channel = randomAccessFile.getChannel();
			}
			logger.debug("Channel --> [{}].", channel);
		} catch (FileNotFoundException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"構造FileLocker文件鎖類型时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
		}

	}

	/**
	 * 
	 * @param file
	 */
	public FileLocker(String file) {
		try {
			randomAccessFile = new RandomAccessFile(file, "rw");
			logger.debug("Random Access File --> [{}].", randomAccessFile);
			if (null != randomAccessFile) {
				channel = randomAccessFile.getChannel();
			}
			logger.debug("Channel --> [{}].", channel);
		} catch (FileNotFoundException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"構造FileLocker文件鎖類型时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
		}

	}
	/**
	 * 判斷進程是否存在鎖,如果存在則不進行執行進程，否則執行
	 * 
	 * @return
	 */
	public boolean tryLock() {
		try {
			if (null != channel) {
				lock = channel.tryLock();
			}

			boolean flag = (null != lock && lock.isValid());
			logger.debug("Check Locked --> [{}] by [{}].", flag,
					DateTimeUtils.getCurrentDateStr());

			if (flag)
				return true;
		} catch (IOException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"TryLock文件鎖類型时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
		}
		return false;
	}

}
