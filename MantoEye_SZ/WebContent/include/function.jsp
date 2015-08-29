<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ page import="java.io.File"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Calendar"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<%!public static String convert(String str) {
		try {
			if (str == null)
				return "";
			if (str.equals("null"))
				return "";

			byte[] bys = str.getBytes("ISO8859_1");
			return new String(bys, "GBK");
		} catch (Exception e) {
			return str;
		}
	}

	public static String disconvert(String str) {
		if (str == null)
			return "";
		try {

			byte[] bys = str.getBytes("GBK");
			return new String(bys, "ISO8859_1");
		} catch (Exception e) {
			return str;
		}
	}

	public static String long2short(String str, int len) {
		if (str == null)
			return "";
		if (str.length() > len) {
			str = str.substring(0, len - 1) + "...";
			return str;
		} else
			return str;
	}

	public static String addBr(String Content) {
		int aaa = 0;
		String makeContent = new String();
		Content = Content.replaceAll("\n", "<br>");
		;
		aaa = 0;
		makeContent = new String();
		makeContent = Content.replaceAll(" ", "&nbsp;");
		return makeContent;
	}

	public String addSingleQuotes(String Content) {
		return Content.replaceAll("'", "''");
	}

	public static String replaceStr(String s, String org, String ob) {
		String newString = "";
		int first = 0;
		while (s.indexOf(org) != -1) {
			first = s.indexOf(org);
			if (first != s.length()) {
				newString = newString + s.substring(0, first) + ob;
				s = s.substring(first + org.length(), s.length());
			}
		}

		newString = newString + s;
		return newString;
	}

	public static String clearNull(String str) {
		if (str == null)
			return null;
		str = replaceStr(str, "'null'", "null");
		str = replaceStr(str, "''", "null");
		str = replaceStr(str, ",,", ",null,");
		str = replaceStr(str, ",,", ",null,");
		str = replaceStr(str, ",)", ",null)");
		str = replaceStr(str, "(,", "(null,");
		return str;
	}

	public static String getDateFormat(java.util.Date d) {
		return getDateTimeFormatBy(d, "yyyy-MM-dd");
	}

	public static String getTimeFormat(java.util.Date d) {
		return getDateTimeFormatBy(d, "HH:mm");
	}

	public static String getDateTimeFormat(java.util.Date d) {
		return getDateTimeFormatBy(d, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getShortDTF(java.util.Date d) {
		return getDateTimeFormatBy(d, "yyyy-MM-dd HH:mm");
	}

	public static String getDateTimeFormatBy(java.util.Date d, String p) {
		if (d == null)
			return "-";
		SimpleDateFormat sdf = new SimpleDateFormat(p);

		return sdf.format(d);
	}

	public static String numberConvert(int num) {
		String s = String.valueOf(num);
		if (num < 10)
			s = "0" + s;
		return s;
	}

	// -- IsNullOrEmpty code by SIYUAN at 2006-4-27
	public static boolean IsNullOrEmpty(String s) {
		return (s == null || s.trim().length() == 0);
	}

	//
	public static java.util.Date getOffsetDate(java.util.Date chkDate,
			java.lang.Integer offSet) throws Exception {
		int month = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(chkDate);
		calendar.add(Calendar.MONTH, offSet);
		return calendar.getTime();
	}

	public static java.util.Date getOffsetMinute(java.util.Date chkDate,
			java.lang.Integer offSet) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(chkDate);
		calendar.add(Calendar.MINUTE, offSet);
		return calendar.getTime();
	}

	public ArrayList<String> stringToList(String s) {
		ArrayList<String> slist = new ArrayList<String>();
		String[] a = null;
		if (s != null && !s.equals("")) {
			a = s.split(",");
			for (int i = 0; i < a.length; i++) {
				slist.add(a[i].trim());
			}
		}
		return slist;
	}

	/**
	 * 弹出信息后跳转到指定页面
	 * @param msg  提示信息
	 * @param url  跳转到指定页面
	 */
	public String jspAlert(String msg, String url) {
		String makeContent = new String();
		makeContent = "<scr" + "ipt type=\"text/javascript\">\n";
		makeContent += "    alert(\"" + msg + "\");\n";
		makeContent += "    self.location.href = \"" + url + "\";\n";
		makeContent += "</scr" + "ipt>\n";
		return makeContent;
	}

	/**
	 * 弹出信息后跳转到指定页面
	 * @param msg  提示信息
	 * @param url  之前的页面
	 */
	public String jspAlert_history(String msg) {
		String makeContent = new String();
		makeContent = "<scr" + "ipt type=\"text/javascript\">\n";
		makeContent += "    alert(\"" + msg + "\");\n";
		makeContent += "    history.go(-1);\n";
		makeContent += "</scr" + "ipt>\n";
		return makeContent;
	}

	/**
	 * 弹出信息后跳转到指定页面
	 * @param msg  提示信息
	 * @param action  调用js命令
	 * @param url  跳转到指定页面
	 */
	public String jspAlert(String msg, String action, String url) {
		String makeContent = new String();
		makeContent = "<scr" + "ipt type=\"text/javascript\">\n";
		makeContent += "    alert(\"" + msg + "\");\n";
		makeContent += "    " + action + "\n";
		makeContent += "    self.location.href = \"" + url + "\";\n";
		makeContent += "</scr" + "ipt>\n";
		return makeContent;
	}%>
<%!public static String path(String filePath) {
		return filePath.replaceAll("\\\\|/", "\\" + File.separator);
	}%>