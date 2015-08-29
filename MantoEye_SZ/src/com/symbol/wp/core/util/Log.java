package com.symbol.wp.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志记录类<br>
 * 可用于实时控制台输出或文件中<br>
 * <br>
 * 对于输出到文件的情况,每次文件容量大于设置的限值时会重新创建新日志文件记录新日志,<br>
 * 原来的日志文件会按升序编号命名(如: log-1.log, log-2.log...),序号越小文件越旧<br>
 * 注:最新的日志文件没有序号<br>
 * <br>
 * 当旧日志文件(被编号的文件)大于设置的限值时会将最旧的日志文件(log-1.log)删除<br>
 * 
 * @author MaxChou
 * @version 2004-10-15
 */
public class Log

{
	private final static Logger logger = LoggerFactory.getLogger(Log.class);
	// 单个文件大小限制值50k
	public static long LENGTH_LIMITED = 50000;

	// 最大历史日志文件数
	public static int COUNT_LIMITED = 5;

	// 要在日志中记录的目标类
	private Object m_target = null;

	// 日志文件名,不含扩展名
	private String m_fileName = null;

	// 随机文件读写对象
	private RandomAccessFile m_raFile = null;

	// 日期格式
	private static SimpleDateFormat DATA_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");
	// 用于提高字符串处理效率
	private StringBuilder m_sb = null;

	/**
	 * 构造函数
	 * 
	 * @param object
	 *            要记录的对象目标类
	 * @param fileName
	 *            日志文件名
	 */
	public Log(Object object, String fileName) {
		m_target = object;
		m_fileName = fileName;

		m_sb = new StringBuilder();

		try {
			m_raFile = new RandomAccessFile(m_fileName + ".log", "rw");
			m_raFile.seek(m_raFile.length());
		} catch (FileNotFoundException e) {
			logger.error(DATA_FORMAT.format(new java.util.Date())
					+ " [jo.util.Log] Constructor()");
		} catch (IOException e) {
			logger.error(DATA_FORMAT.format(new java.util.Date())
					+ " [jo.util.Log] Constructor()2");
		}
	}

	/**
	 * 在日志文件中追加一条新警告日志
	 * 
	 * @param record
	 *            日志内容
	 */
	public void logWarning(String record) {
		m_sb.setLength(0);
		m_sb.append("!!! ");
		m_sb.append(record);
		m_sb.append(" !!!");
		log(m_sb.toString());
	}

	/**
	 * 在日志文件中追加一条新错误日志
	 * 
	 * @param record
	 *            日志内容
	 */
	public void logError(String record) {
		m_sb.setLength(0);
		m_sb.append("*** ");
		m_sb.append(record);
		m_sb.append(" ***");
		log(m_sb.toString());
	}

	/**
	 * 在日志文件中追加一条新日志
	 * 
	 * @param record
	 *            日志内容
	 */
	public void log(String record) {
		try {
			String className = "";
			if (m_target != null)
				className = m_target.getClass().getName();

			m_sb.setLength(0);
			m_sb.append(DATA_FORMAT.format(new java.util.Date()));
			m_sb.append(" [");
			m_sb.append(className);
			m_sb.append("] ");
			m_sb.append(Utils.toISO_8859_1(record));
			m_sb.append("\r\n");
			m_raFile.writeBytes(m_sb.toString());

			// 当文件超出最大限制后进行新日志文件创建处理
			if (m_raFile.length() > LENGTH_LIMITED) {
				m_raFile.close();

				// 找出可用的日志文件名,最大允许保留最近COUNT_LIMIT个日志文件
				File file = null;
				int i = 1;
				for (i = 1; i <= COUNT_LIMITED; i++) {
					file = new File(m_fileName + "-" + i + ".log");
					if (!file.exists())
						break;
				}

				// 日志数已超过COUNT_LIMIT,则删除最旧的日志文件
				if (i > COUNT_LIMITED) {
					i = COUNT_LIMITED;

					// 先删除最旧的文件
					file = new File(m_fileName + "-1.log");
					if (file.exists())
						file.delete();

					// 将其它所有日志文件名序号减1
					int k = 2;
					for (k = 2; k <= COUNT_LIMITED; k++) {
						file = new File(m_fileName + "-" + k + ".log");
						if (file.exists()) {
							file.renameTo(new File(m_fileName + "-" + (k - 1)
									+ ".log"));
							continue;
						}
					}

					// 删除最后一个日志文件
					file = new File(m_fileName + "-" + (k - 1) + ".log");
					if (file.exists())
						file.delete();
				}

				// 完成限制文件数处理后.重命名当前超过大小限制的日志文件
				file = new File(m_fileName + ".log");
				file.renameTo(new File(m_fileName + "-" + i + ".log"));

				// 重新创建空白日志文件
				m_raFile = new RandomAccessFile(m_fileName + ".log", "rw");
				m_raFile.seek(0);
			}
		} catch (IOException e) {
			logger.error(DATA_FORMAT.format(new java.util.Date())
					+ " [maxjo.util.Log] log(String)");
		}
	}

	/**
	 * 在控制台输出日志内容
	 * 
	 * @param obj
	 *            要记录的对象目标类
	 * @param record
	 *            日志内容
	 */
	public static void log(Object obj, String record) {
		String className = "";
		if (obj != null)
			className = obj.getClass().getName();

		logger.error(DATA_FORMAT.format(new java.util.Date()) + " ["
				+ className + "] " + record);
	}
}