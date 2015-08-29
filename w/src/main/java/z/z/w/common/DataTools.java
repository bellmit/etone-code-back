/**
 * z.z.w.common.DataTools.java
 */
package z.z.w.common;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Data tools
 * 
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 10:11:11 AM
 */
public final class DataTools {

	public static final char UNIT_B = 'B';
	public static final char UNIT_KB = 'K';
	public static final char UNIT_MB = 'M';
	public static final char UNIT_GB = 'G';
	public static final char UNIT_TB = 'T';

	public static final char UNIT_B_L = 'b';
	public static final char UNIT_KB_L = 'k';
	public static final char UNIT_MB_L = 'm';
	public static final char UNIT_GB_L = 'g';
	public static final char UNIT_TB_L = 't';

	/**
	 * private constructor <br>
	 * Created on: Nov 21, 2012 2:50:22 PM
	 */
	private DataTools() {
	}

	/**
	 * Sources string content sub string times <br>
	 * Created on: Nov 21, 2012 2:49:15 PM
	 * 
	 * @param sourcesString
	 *            sources string
	 * @param subString
	 *            sub string
	 * @return times
	 */
	public final static int getSubstringCount(String sourcesString,
			String subString) {
		int idx = -1;
		int count = 0;

		while ((idx = sourcesString.indexOf(subString, idx + 1)) != -1)
			count++;

		return count;
	}

	/**
	 * String has chinese <br>
	 * Created on: Nov 21, 2012 2:47:51 PM
	 * 
	 * @param value
	 * @return
	 */
	public static boolean hasChinese(String value) {
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
		Matcher m = p.matcher(value);
		while (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * format number <br>
	 * Created on: Nov 21, 2012 2:56:19 PM
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
	 * 
	 * formate integer number by %04d <br>
	 * Created on: Nov 21, 2012 2:45:25 PM
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
	 * Created on: Nov 21, 2012 2:44:27 PM
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
	 * Created on: Nov 21, 2012 2:41:59 PM
	 * 
	 * @param formatByte
	 *            total byte num
	 * @param formatBit
	 *            bit num
	 * @param number
	 * @return
	 */
	public static String nmFormatToFloat(int formatByte, int formatBit,
			Object number) {
		String format = "%0" + formatByte + "." + formatBit + "f";
		return nmFormatToFloat(format, number);
	}

	/**
	 * format float number by 07.2f <br>
	 * Created on: Nov 21, 2012 2:39:20 PM
	 * 
	 * @param number
	 * @return 0234.67
	 */
	public static String nmFormatToFloat(Object number) {
		return nmFormatToFloat("%07.2f", number);
	}

	/**
	 * format number <br>
	 * Created on: Nov 21, 2012 2:36:41 PM
	 * 
	 * @param format
	 *            "%08.3f"
	 * @param number
	 *            0234.678 ####.###
	 * 
	 * @return
	 */
	public static String nmFormatToFloat(String format, Object number) {
		return String.format(format,
				NumberUtils.toFloat(String.valueOf(number)));
	}

	/**
	 * String is null or is empty <br>
	 * Created on: Nov 21, 2012 2:35:38 PM
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return (("null".equalsIgnoreCase(trimToEmpty(string))) || (StringUtils
				.isBlank(trimToEmpty(string))));
	}

	/**
	 * String trim to empty and change null to empty
	 * 
	 * <br>
	 * Created on: Nov 21, 2012 2:34:53 PM
	 * 
	 * @param string
	 * @return
	 */
	public static String trimToEmpty(String string) {
		return StringUtils.trimToEmpty(string);
	}

	/**
	 * File is null or is not exists <br>
	 * Created on: Nov 21, 2012 2:33:58 PM
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isEmpty(File file) {
		return ((null == file) || !(file.exists()));
	}

	/**
	 * Array Object is null or is empty <br>
	 * Created on: Nov 21, 2012 2:33:24 PM
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object[] obj) {
		return ((null == obj) || (obj.length == 0));
	}

	/**
	 * Object is null <br>
	 * Created on: Nov 21, 2012 2:33:05 PM
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return ((null == obj));
	}

	/**
	 * Map is null or isEmpty <br>
	 * Created on: Nov 21, 2012 2:30:33 PM
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (null == map || map.isEmpty());
	}

	/**
	 * Collection is null or is empty <br>
	 * Created on: Nov 21, 2012 2:32:32 PM
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
	 * Created on: 2013-8-10 下午02:55:59
	 * 
	 * @param hexStr
	 * @return
	 */
	public static char getCharByHexStr(String hexStr) {
		if (hexStr.startsWith("\\x"))
			hexStr = hexStr.replace("\\x", "");

		return ((char) Integer.parseInt(hexStr, 16));
	}

	/**
	 * <br>
	 * Created on: Mar 21, 2013 10:10:15 AM
	 * 
	 * @param splitStr
	 * @return
	 */
	public static String parseSplit(String splitStr) {

		String split = "\\n";

		if (splitStr.equals(split))
			return ("\n");

		split = "\\t";
		if (splitStr.equals(split))
			return ("\t");

		split = "\\r\\n";
		if (splitStr.equals(split))
			return ("\r\n");

		return splitStr;
	}

	/**
	 * <br>
	 * Created on: 2013-8-26 下午04:38:33
	 * 
	 * @param dataSize
	 * @return #.##B/K/M/G/T
	 */
	public static String formatDataSize(long dataSize) {
		DecimalFormat df = new DecimalFormat("#.##");
		String fileSizeString = "";
		if (dataSize < 1024) {
			fileSizeString = df.format((double) dataSize) + "B";
		} else if (dataSize < 1048576) {
			fileSizeString = df.format((double) dataSize / 1024) + "K";
		} else if (dataSize < 1073741824) {
			fileSizeString = df.format((double) dataSize / 1048576) + "M";
		} else if (dataSize < 1099511627776l) {
			fileSizeString = df.format((double) dataSize / 1073741824) + "G";
		} else {
			fileSizeString = df.format((double) dataSize / 1099511627776l)
					+ "T";
		}
		return fileSizeString;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-8-26 下午04:56:45
	 * 
	 * @param dataSize
	 * @param unit
	 * @return #.##unit(B/K/M/G/T)
	 */
	public static String formatDataSize(long dataSize, char unit) {
		DecimalFormat df = new DecimalFormat("#.##");
		String fileSizeString = "";

		switch (unit) {
		case UNIT_B:
		case UNIT_B_L:
			fileSizeString = df.format((double) dataSize) + "B";
			break;
		case UNIT_KB:
		case UNIT_KB_L:
			fileSizeString = df.format((double) dataSize / 1024) + "K";
			break;
		case UNIT_MB:
		case UNIT_MB_L:
			fileSizeString = df.format((double) dataSize / 1048576) + "M";
			break;
		case UNIT_GB:
		case UNIT_GB_L:
			fileSizeString = df.format((double) dataSize / 1073741824) + "G";
			break;

		default:
			fileSizeString = df.format((double) dataSize / 1099511627776l)
					+ "T";
			break;
		}

		return fileSizeString;
	}

}
