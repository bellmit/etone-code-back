/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.common.DataTools.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-9 下午05:22:14 
 *   LastChange: 2013-9-9 下午05:22:14 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.common;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * z.z.w.project.util.common.DataTools.java
 */
public abstract class DataTools {

	@Deprecated
	public static final char UNIT_B = 'B';
	@Deprecated
	public static final char UNIT_KB = 'K';
	@Deprecated
	public static final char UNIT_MB = 'M';
	@Deprecated
	public static final char UNIT_GB = 'G';
	@Deprecated
	public static final char UNIT_TB = 'T';
	@Deprecated
	public static final char UNIT_B_L = 'b';
	@Deprecated
	public static final char UNIT_KB_L = 'k';
	@Deprecated
	public static final char UNIT_MB_L = 'm';
	@Deprecated
	public static final char UNIT_GB_L = 'g';
	@Deprecated
	public static final char UNIT_TB_L = 't';

	/**
	 * String has chinese <br>
	 * Created on: 2013-9-9 下午05:22:59
	 * 
	 * @param string
	 * @return true or false
	 */
	public static boolean hasChinese(String string) {
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
		Matcher m = p.matcher(string);
		while (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * format number <br>
	 * Created on: 2013-9-9 下午05:26:48
	 * 
	 * @param formatByte
	 *            total byte number
	 * @param number
	 * @return
	 */
	public static String nmFormatToInt(int formatByte, Object number) {
		String format = "%0" + formatByte + "d";
		return nmFormatToInt(format, number);
	}

	/**
	 * formate integer number by %04d <br>
	 * Created on: 2013-9-9 下午05:27:18
	 * 
	 * @param number
	 *            %04d
	 * @return 0123 ####
	 */
	public static String nmFormatToInt(Object number) {
		return nmFormatToInt("%04d", number);
	}

	/**
	 * format integer number <br>
	 * Created on: 2013-9-9 下午05:27:32
	 * 
	 * @param format
	 *            %04d
	 * @param number
	 *            0123 ####
	 * @return
	 */
	public static String nmFormatToInt(String format, Object number) {
		return String.format(format, NumberUtils.toInt(String.valueOf(number)));
	}

	/**
	 * formate float number <br>
	 * Created on: 2013-9-9 下午05:27:47
	 * 
	 * @param formatBytetotal
	 *            byte num
	 * @param formatBit
	 *            bit num
	 * @param number
	 * @return
	 */
	public static String nmFormatToFloat(int formatByte, int formatBit, Object number) {
		String format = "%0" + formatByte + "." + formatBit + "f";
		return nmFormatToFloat(format, number);
	}

	/**
	 * format float number by 07.2f <br>
	 * Created on: 2013-9-9 下午05:28:02
	 * 
	 * @param number
	 * @return 0234.67
	 */
	public static String nmFormatToFloat(Object number) {
		return nmFormatToFloat("%07.2f", number);
	}

	/**
	 * format number <br>
	 * Created on: 2013-9-9 下午05:28:12
	 * 
	 * @param format
	 *            "%08.3f"
	 * @param number
	 *            0234.678 ####.###
	 * @return
	 */
	public static String nmFormatToFloat(String format, Object number) {
		return String.format(format, NumberUtils.toFloat(String.valueOf(number)));
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午09:40:28
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return (("null".equalsIgnoreCase(trimToEmpty(string))) || (StringUtils.isBlank(trimToEmpty(string))));
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午09:40:33
	 * 
	 * @param string
	 * @return
	 */
	public static String trimToEmpty(String string) {
		return StringUtils.trimToEmpty(string);
	}

	/**
	 * (null == file) || !(file.exists()) <br>
	 * Created on: 2013-9-10 上午09:40:53
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isEmpty(File file) {
		return ((null == file) || !(file.exists()));
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午09:41:47
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object[] obj) {
		return ((null == obj) || (obj.length == 0));
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午09:41:44
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return ((null == obj));
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-22 下午02:54:28
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(char c) {
		return isEmpty(new String(new char[] { c }));
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午09:41:39
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (null == map || map.isEmpty());
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午09:41:31
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return ((null == collection || collection.isEmpty()));
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午09:48:56
	 * 
	 * @param hexStr
	 *            startsWith "\\x"
	 * @return
	 */
	public static char getCharByHexStr(String hexStr) {
		if (hexStr.startsWith("\\x"))
			hexStr = hexStr.replace("\\x", "");

		return ((char) Integer.parseInt(hexStr, 16));
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午09:43:20
	 * 
	 * @param dataSize
	 * @return #.##B/K/M/G/T
	 * @deprecated use <code>z.z.w.project.util.file.FileTools.freeSpace(path)</code>
	 */
	public static String formatDataSize(long dataSize) {
		String fileSizeString = "";
		if (dataSize < 1024) {
			fileSizeString = getFormatValue(dataSize) + UNIT_B;
		} else if (dataSize < 1048576) {
			fileSizeString = getFormatValue(((double) dataSize / 1024)) + UNIT_KB;
		} else if (dataSize < 1073741824) {
			fileSizeString = getFormatValue(((double) dataSize / 1048576)) + UNIT_MB;
		} else if (dataSize < 1099511627776l) {
			fileSizeString = getFormatValue(((double) dataSize / 1073741824)) + UNIT_GB;
		} else {
			fileSizeString = getFormatValue(((double) dataSize / 1099511627776l)) + UNIT_TB;
		}
		return fileSizeString;
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午03:37:21
	 * 
	 * @param dataSize
	 * @param unitB
	 * @return
	 * @deprecated use <code>z.z.w.project.util.file.FileTools.freeSpace(path)</code>
	 */
	private static String getFormatValue(double dataSize) {
		return nmFormatToFloat("%1.2f", dataSize);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午09:43:06
	 * 
	 * @param dataSize
	 * @param unit
	 * @return #.##unit(B/K/M/G/T)
	 * @deprecated use <code>z.z.w.project.util.file.FileTools.freeSpace(path)</code>
	 * 
	 */
	public static String formatDataSize(long dataSize, char unit) {

		String fileSizeString = "";

		switch (unit) {
		case UNIT_B:
		case UNIT_B_L:
			fileSizeString = getFormatValue(((double) dataSize)) + UNIT_B;
			break;
		case UNIT_KB:
		case UNIT_KB_L:
			fileSizeString = getFormatValue(((double) dataSize / 1024)) + UNIT_KB;
			break;
		case UNIT_MB:
		case UNIT_MB_L:
			fileSizeString = getFormatValue(((double) dataSize / 1048576)) + UNIT_MB;
			break;
		case UNIT_GB:
		case UNIT_GB_L:
			fileSizeString = getFormatValue(((double) dataSize / 1073741824)) + UNIT_GB;
			break;

		default:
			fileSizeString = getFormatValue(((double) dataSize / 1099511627776l)) + UNIT_TB;
			break;
		}

		return fileSizeString;
	}

	/**
	 * 二進制轉16進制 <br>
	 * Created on: 2013-9-11 下午04:05:16
	 * 
	 * @param value
	 * @return
	 */
	public static String parseByteToHex(byte value) {
		return parseByte2HexStr(new byte[] { value });
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
}
