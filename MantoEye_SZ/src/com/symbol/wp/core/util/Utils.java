package com.symbol.wp.core.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具类,提供各种在JAVA开发中的工具函数
 * 
 * @author MaxChou
 * @version 2004-7-8
 */
public class Utils {
	public static final int OS_WINDOWS = 0;
	public static final int OS_MACOS = 1;
	public static final int OS_UNIX = 2;

	private static int m_os = -1;
	private static ArrayList<Pattern> m_osPatterns = new ArrayList<Pattern>();

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	/**
	 * 初始化OS分类
	 */
	static {
		m_osPatterns.add(Pattern.compile("windows|os/2|netware"));
		m_osPatterns.add(Pattern.compile("mac os|macos"));
		m_osPatterns.add(Pattern
				.compile("unix|linux|solaris|mpe/ix|hp-ux|aix|freebsd|irix"));
	}

	/**
	 * 转换为GB2312编码
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的字符串,如果转换失败则返回原字符串
	 */
	public static String toGB2312(String str) {
		try {
			if (str == null)
				return null;

			byte[] bys = str.getBytes("ISO8859_1");
			return new String(bys, "GB2312");
		} catch (UnsupportedEncodingException e) {
			Log.log(null, "toGB2312(): " + e);
			return str;
		}
	}

	/**
	 * 转换为GBK编码
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的字符串,如果转换失败则返回原字符串
	 */
	public static String toGBK(String str) {
		try {
			if (str == null)
				return null;

			byte[] bys = str.getBytes("ISO8859_1");
			return new String(bys, "GBK");
		} catch (UnsupportedEncodingException e) {
			Log.log(null, "toGBK(): " + e);
			return str;
		}
	}

	public static String toGB18030(String str) {
		try {
			if (str == null)
				return null;

			byte[] bys = str.getBytes("ISO8859_1");
			return new String(bys, "GB18030");
		} catch (UnsupportedEncodingException e) {
			Log.log(null, "toGB18030(): " + e);
			return str;
		}
	}

	/**
	 * 转换为iso-8859-1编码<br>
	 * 一般用于把硬编码的中文字符串内容写入Sybase数据库中或得到在JAVA中的原始编码格式.
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的字符串,如果转换失败则返回原字符串
	 */
	public static String toISO_8859_1(String str) {
		try {
			if (str == null)
				return null;

			byte[] bys = str.getBytes("GBK");
			return new String(bys, "ISO8859_1");
		} catch (UnsupportedEncodingException e) {
			Log.log(null, "toISO8859_1(): " + e);
			return str;
		}
	}

	public static String convert(String str, String charset) {
		try {
			if (str == null)
				return null;

			byte[] bys = str.getBytes("ISO8859_1");
			return new String(bys, charset);
		} catch (UnsupportedEncodingException e) {
			Log.log(null, "convert(string,string): " + e);
			return str;
		}
	}

	/**
	 * 转换为GBK编码
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的字符串,如果转换失败则返回原字符串
	 */
	public static String convert(String str) {
		return toGBK(str);
	}

	/**
	 * 转换为iso-8859-1编码<br>
	 * 一般用于把硬编码的中文字符串内容写入Sybase数据库中或得到在JAVA中的原始编码格式.
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的字符串,如果转换失败则返回原字符串
	 */
	public static String disconvert(String str) {
		return toISO_8859_1(str);
	}

	/**
	 * 转换指定精度的浮点数
	 * 
	 * @param d
	 *            原始浮点数
	 * @param precision
	 *            精度(指小数点后的位数).如果指定为-1,则取整.
	 * @return 转换后的浮点数字符串
	 */
	public static String getDoubleString(double d, int precision) {
		String value = String.valueOf(d);
		return getDoubleString(value, precision);
	}

	/**
	 * 转换指定精度的浮点数
	 * 
	 * @param str
	 *            浮点数字符串
	 * @param precision
	 *            精度(指小数点后的位数).如果指定为-1,则取整.
	 * @return 转换后的浮点数字符串
	 */
	public static String getDoubleString(String str, int precision) {
		if (str.substring(str.indexOf("."), str.length()).length() > precision + 1)
			str = str.substring(0, str.indexOf(".") + precision + 1);
		return str;
	}

	/**
	 * 返回date所在的一周内的第一天(星期一)的日期
	 * 
	 * @param date
	 *            指定日期的字符串,格式为: yyyy-MM-dd
	 * @return 返回第一天的日期字符串,格式为: yyyy-MM-dd
	 */
	public static String getDateOfWeekBegin(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(date));

			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			// 因为Calendar默认星期日是一个星期的第一天,对于目前本函数应用需进行修正处理
			if (--dayOfWeek == 0)
				dayOfWeek = 7;

			cal.add(Calendar.DATE, -(dayOfWeek - 1)); // 减去天数

			String result = cal.get(Calendar.YEAR) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.DATE);
			return result;
		} catch (ParseException e) {
			Log.log(null, "getDateOfWeekBegin(): " + e);
		}
		return null;
	}

	/**
	 * 返回date所在的一周内的最后一天(星期日)的日期
	 * 
	 * @param date
	 *            指定日期的字符串,格式为: yyyy-MM-dd
	 * @return 返回最后一天的日期字符串,格式为: yyyy-MM-dd
	 */
	public static String getDateOfWeekEnd(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(date));

			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			// 因为Calendar默认星期日是一个星期的第一天,对于目前本函数应用需进行修正处理
			if (--dayOfWeek == 0)
				dayOfWeek = 7;

			cal.add(Calendar.DATE, (7 - dayOfWeek)); // 增加天数

			String result = cal.get(Calendar.YEAR) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.DATE);
			return result;
		} catch (ParseException e) {
			Log.log(null, "getDateOfWeekBegin(): " + e);
		}
		return null;
	}

	/**
	 * 清空表格行
	 * 
	 * @param table
	 *            表格对象
	 */
	public static void clearTableModel(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int i = table.getRowCount() - 1; i >= 0; i--)
			model.removeRow(i);
	}

	/**
	 * 转换Image数据为byte数组
	 * 
	 * @param image
	 *            Image对象
	 * @param format
	 *            image格式字符串.如"jpeg","png"
	 * @return byte数组
	 */
	public static byte[] imageToBytes(Image image, String format) {
		BufferedImage bImage = new BufferedImage(image.getWidth(null), image
				.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics bg = bImage.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bImage, format, out);
		} catch (IOException e) {
			Log.log(null, "imageToBytes(): " + e);
		}
		return out.toByteArray();
	}

	/**
	 * 转换byte数组为Image
	 * 
	 * @param bytes
	 *            Image的bytes数据数组
	 * @return Image
	 */
	public static Image bytesToImage(byte[] bytes) {
		Image image = Toolkit.getDefaultToolkit().createImage(bytes);

		try {
			MediaTracker mt = new MediaTracker(new Label());
			mt.addImage(image, 0);
			mt.waitForAll();
		} catch (InterruptedException e) {
			Log.log(null, "preloadResource(): " + e);
		}

		return image;
	}

	/**
	 * 判断是否为闰年
	 * 
	 * @param year
	 *            年份
	 * @return 是否为闰年.true:是,false:不是.
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 返回指定对象在对象数组中的索引号
	 * 
	 * @param objects
	 *            对象数组
	 * @param object
	 *            对象
	 * @return 对象在对象数组中的索引号,没有匹配时返回-1
	 */
	public static int indexOfArray(Object[] objects, Object object) {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].equals(object))
				return i;
		}
		return -1;
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param value
	 *            要判断的内容
	 * @return true:是,false:不是
	 */
	public static boolean isInteger(String value) {
		return Pattern.matches("-?[0-9]+", value);
	}

	/**
	 * 判断是否为浮点数
	 * 
	 * @param value
	 *            要判断的内容
	 * @return true:是,false:不是
	 */
	public static boolean isFloat(String value) {
		return Pattern.matches("-?[0-9]+.[0-9]+", value);
	}

	/**
	 * 判断是否为十六进制数
	 * 
	 * @param value
	 *            要判断的内容
	 * @return true:是,false:不是
	 */
	public static boolean isHex(String value) {
		return Pattern.matches("0?[x||X]?[a-fA-F[0-9]]+", value);
	}

	/**
	 * 判断是否为八进制数
	 * 
	 * @param value
	 *            要判断的内容
	 * @return true:是,false:不是
	 */
	public static boolean isOct(String value) {
		return Pattern.matches("0?[0-7]+", value);
	}

	/**
	 * 判断是否为合法的Email地址
	 * 
	 * @param value
	 *            要判断的内容
	 * @return true:是,false:不是
	 */
	public static boolean isEmailAddress(String value) {
		return Pattern.matches(
				"\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", value);
	}

	/**
	 * 判断是否为合法的URL
	 * 
	 * @param value
	 *            要判断的内容
	 * @return true:是,false:不是
	 */
	public static boolean isURL(String value) {
		return Pattern.matches(
				"http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", value);
	}

	/**
	 * 判断是否为中文字符
	 * 
	 * @param value
	 *            UNICODE语法的内容,如"\u4e00"
	 * @return true:是,false:不是
	 */
	public static boolean isChinese(String value) {
		return Pattern.matches("[\\u4e00-\\u9fa5]", value);
	}

	/**
	 * 判断是否为双字节内容,如汉字
	 * 
	 * @param value
	 *            要判断的内容,如"你好"
	 * @return true:是,false:不是
	 */
	public static boolean isDoubleByte(String value) {
		return Pattern.matches("[\\u4e00-\\u9fa5]", value);
	}

	/**
	 * 判断是否为HTML标签格式
	 * 
	 * @param value
	 *            要判断的内容
	 * @return true:是,false:不是
	 */
	public static boolean isHtmlTag(String value) {
		return Pattern.matches("/<(.*)>.*<\\/\\1>|<(.*) \\/>/", value);
	}

	/**
	 * 判断是否为IP地址
	 * 
	 * @param value
	 *            要判断的内容
	 * @return true:是,false:不是
	 */
	public static boolean isIpAddress(String value) {
		return Pattern.matches("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)", value);
	}

	/**
	 * 输出列表内容
	 * 
	 * @param list
	 *            列表
	 */
	public static void printValue(String prefix, ArrayList list, String suffix) {
		for (int i = 0; i < list.size(); i++) {
		}
	}

	/**
	 * 输出对象数组内容
	 * 
	 * @param objects
	 *            数组
	 */
	public static void printValue(String prefix, Object[] objects, String suffix) {
		for (int i = 0; i < objects.length; i++) {
		}
	}

	/**
	 * 输出字节数组内容
	 * 
	 * @param prefix
	 *            输入每单位内容的前缀
	 * @param bytes
	 *            要出的字节数组
	 * @param suffix
	 *            输入每单位内容的后缀
	 */
	public static void printValue(String prefix, byte[] bytes, String suffix) {
		for (int i = 0; i < bytes.length; i++) {
		}
	}

	/**
	 * 输出日历时间
	 * 
	 * @param cal
	 *            日历对象
	 */
	public static void printValue(String prefix, Calendar cal, String suffix) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss.S");
	}

	/**
	 * 转换字符中列表内容为以split分隔的字符串
	 * 
	 * @param al
	 *            列表
	 * @param split
	 *            分隔符
	 * @return 转换后的字符串,如果列表为空则返回空字符串""
	 */
	public static String transArrayList(ArrayList al, String split) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < al.size(); i++) {
			sb.append(al.get(i));
			sb.append(split);
		}

		return (sb.length() > 0) ? sb.deleteCharAt(sb.length() - 1).toString()
				: "";
	}

	/**
	 * 字符串 左 对齐
	 * 
	 * @param source
	 *            要处理的源字符串
	 * @param len
	 *            限定对齐长度
	 * @param fill
	 *            不足长度填充的字符
	 * @return 对齐后的字符串
	 */
	public static String alignLeft(String source, int len, byte fill) {
		if (source.length() > len)
			return source.substring(0, len);

		if (source.length() < len) {
			StringBuilder sb = new StringBuilder();
			sb.append(source);
			for (int i = source.length(); i < len; i++)
				sb.append((char) fill);
			return sb.toString();
		}

		return source;
	}

	/**
	 * 字符串 右 对齐
	 * 
	 * @param source
	 *            要处理的源字符串
	 * @param len
	 *            限定对齐长度
	 * @param fill
	 *            不足长度填充的字符
	 * @return 对齐后的字符串
	 */
	public static String alignRight(String source, int len, byte fill) {
		if (source.length() > len)
			return source.substring(0, len);

		if (source.length() < len) {
			String prefix = "";
			for (int i = 0; i < len - source.length(); i++)
				prefix += (char) fill;
			return prefix + source;
		}

		return source;
	}

	/**
	 * 比较两个字节数组是否相同
	 * 
	 * @param src
	 *            比较源
	 * @param dest
	 *            比较目标
	 * @return true:相同,false:不相同
	 */
	public static boolean compareBytes(byte[] src, byte[] dest) {
		if (src.length != dest.length)
			return false;

		for (int i = 0; i < src.length; i++)
			if (src[i] != dest[i])
				return false;

		return true;
	}

	/**
	 * 将日期类型格式化为yyyy-mm-dd hh:mm:ss格式的字符串
	 * 
	 * @param d
	 *            要格式化的日期
	 * @return 格式化后的日期字符串,如果d==null则返回"1900-00-00 00:00:00"
	 */
	public static String getDateTimeFormat(java.util.Date d) {
		if (d == null)
			return "1900-00-00 00:00:00";

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
	}

	/**
	 * 返回指定字节数组的MD5数字签名
	 * 
	 * @param data
	 *            要签名的字符串
	 * @return MD5签名的字节数组
	 */
	public static byte[] getMD5(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(data.getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			Log.log(null, "getMessageDigest(): " + e);
		}
		return null;
	}

	/**
	 * 返回指定字符串的MD5数字签名
	 * 
	 * @param data
	 *            要签名的字符串
	 * @return MD5签名的字符串
	 */
	public static String getMD5String(String data) {
		return new BigInteger(1, getMD5(data)).toString(16);
	}

	/**
	 * 判断文件是否能被锁定
	 * 
	 * @param filename
	 *            要锁定的文件.如果文件不存在则自动创建空白文件
	 * @return true:锁定成功,false:锁定失败
	 */
	public static boolean canLockFile(String filename) {
		FileLock lock = lockFile(filename);
		if (lock == null)
			return false;

		try {
			lock.release();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 锁定指定文件
	 * 
	 * @param filename
	 *            要锁定的文件.如果文件不存在则自动创建空白文件
	 * @return 文件锁对象,如果不能锁定文件则返回null
	 */
	public static FileLock lockFile(String filename) {
		FileChannel m_channel = null;
		try {
			// if (new File(filename).exists() == false)
			// new FileOutputStream(filename).close();
			//
			// m_channel = new RandomAccessFile(filename, "rw").getChannel();
			m_channel = new FileOutputStream(new File(filename)).getChannel();
			return m_channel.tryLock();

			// fileLock = m_channel.tryLock(); if(fileLock==null ||
			// fileLock.isValid==false) return null;
		} catch (FileNotFoundException e) {
			return null;
		} catch (OverlappingFileLockException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 返回所在的平台标识
	 * 
	 * @return 值为"OS_"开头常量,如果没有匹配的则返回OS_UNIX
	 */
	public static int getPlatform() {
		if (m_os == -1) {
			String osName = System.getProperty("os.name").toLowerCase();

			for (int i = 0; i < m_osPatterns.size(); i++) {
				Matcher m = m_osPatterns.get(i).matcher(osName);
				if (m.lookingAt() == true) {
					m_os = i;
					return m_os;
				}
			}

			m_os = OS_UNIX;
			return m_os;
		}
		return m_os;
	}

	/**
	 * 返回子字符串在原始字符串中出现的次数
	 * 
	 * @param org
	 *            原始字符
	 * @param sub
	 *            要统计的子字符串
	 * @return 要统计的子字符串出现的次数
	 */
	public final static int getSubstringCount(String org, String sub) {
		int idx = -1;
		int count = 0;

		while ((idx = org.indexOf(sub, idx + 1)) != -1)
			count++;

		return count;
	}
	
	
	
	
	//关键字系统
	public final static int getInt(Object o){
		int value=0;
		if(o!=null){
			try{
				value = new BigInteger(getString(o)).intValue();
			}catch(Exception e){	
			}			
		}
		return value;
		
	}
	
	public final static String getString(Object o){
		String value ="";
		if(o!=null) return String.valueOf(o);
		return value;
	}
}