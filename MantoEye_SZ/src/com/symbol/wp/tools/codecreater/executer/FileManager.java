package com.symbol.wp.tools.codecreater.executer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.tools.codecreater.info.CreaterInfo;
import com.symbol.wp.tools.codecreater.info.FileType;
import com.symbol.wp.tools.codecreater.info.MemoInfo;

/**
 * 用于保存代码生成模板输入框的信息，以方便下次自动显示重复了的字段说明
 * 
 * @author taky
 * 
 */
public class FileManager {

	private Logger logger = LoggerFactory.getLogger(FileManager.class);

	private StringBuffer bStr = null;
	private RandomAccessFile raf = null;

	public FileManager() {

	}

	/**
	 * 把输入框信息写入文本，方便下次自动匹配备注信息
	 * 
	 * @param info
	 */
	public void saveToFile(CreaterInfo info) {
		try {

			raf = new RandomAccessFile("f:\\test\\test.txt", "rwd");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		bStr = new StringBuffer();
		bStr.setLength(0);
		bStr.append("#" + info.getTableName() + "\r\n");
		bStr.append(info.getCodeType() + "|");
		bStr.append(info.getTableName() + "|");
		bStr.append(info.getRows() + "|");
		bStr.append(info.getSavePath() + "\r\n\r\n");

		String formStr = bStr.toString();
		try {
			logger.info(">>>>>  " + formStr);
			// logger.info("raf leng : " + raf.length());
			raf.seek(raf.length());
			raf.write(formStr.getBytes());

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		try {
			if (raf != null)
				raf.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * 模板的路径
	 * 
	 * @return
	 */
	public String getModelPath() {
		// URL url = this.getClass().getResource("");
		// logger.info("url *** "+url);
		// String path = new File(url.getFile()).getParent();
		// logger.info("path *** "+path);
		// return path+"\\model";
		HttpServletRequest httpRequest = Struts2Utils.getRequest();
		String default_path = httpRequest.getSession().getServletContext()
				.getRealPath("/")
				+ "tools\\codecreater\\model";
		return default_path;
	}

	public String readModleFile(int fileType) throws IOException {

		logger.info("***************fileType ****************"
				+ fileType);
		String modelPath = getModelPath();
		logger.info("model PATH  **********" + modelPath);
		File file22 = new File(modelPath, "Input.jsp");
		logger.info("file exist  **********" + file22.exists());

		FileReader fReader = null;
		BufferedReader bReader = null;
		try {
			// fReader = new FileReader(new
			// File("../src/com/symbol/wp/tools/codecreater/info/CreaterInfo.java"));
			switch (fileType) {
			case 1:
				fReader = new FileReader(new File(modelPath, "index.jsp"));
				break;
			case 2:
				fReader = new FileReader(new File(modelPath, "edit.jsp"));
				break;
			case 3:
				fReader = new FileReader(new File(modelPath, "search.jsp"));
				break;
			case 4:
				fReader = new FileReader(new File(modelPath, "show.jsp"));
				break;
			case 5:
				File file = new File(modelPath, "Input.jsp");
				// logger.info("input ***(((((((((((((((()))))))))))*** "+file.getAbsolutePath());
				fReader = new FileReader(file);
				break;

			default:
				fReader = new FileReader(new File(modelPath, "index.jsp"));
			}

			bReader = new BufferedReader(fReader);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		String tmp = "";
		StringBuffer fileBuffer = new StringBuffer();

		if (bReader == null)
			System.out
					.println("***************breader is null ****************");

		while ((tmp = bReader.readLine()) != null) {

			fileBuffer = fileBuffer.append(new String(tmp.getBytes(), "utf-8"));
			fileBuffer = fileBuffer.append("\r\n");
		}
		if (fReader != null)
			fReader.close();
		if (bReader != null)
			bReader.close();
		return fileBuffer.toString().trim();

	}

	/**
	 * 返回新的index文件
	 * 
	 * @param indexFileString
	 * @return
	 */
	public String getNewFileString(String fileString, CreaterInfo info,
			HashMap<String, MemoInfo> itemMemoMap) {
		return executeBuild(fileString.trim(), info, itemMemoMap);
	}

	/**
	 * 具体生成方法
	 * 
	 * @param indexFileString
	 * @return
	 */
	private String executeBuild(String fileString, CreaterInfo info,
			HashMap<String, MemoInfo> itemMemoMap) {
		int fileType = info.getCodeType();
		if (fileType == FileType.INDEX) {
			return createIndex(fileString, info, itemMemoMap);
		} else if (fileType == FileType.EDIT) {
			return createEdit(fileString, info, itemMemoMap);
		} else if (fileType == FileType.EDIT) {
			return createEdit(fileString, info, itemMemoMap);
		} else if (fileType == FileType.SHOW) {
			return createShow(fileString, info, itemMemoMap);
		} else if (fileType == FileType.INPUT) {
			try {
				return createInput(fileString, info, itemMemoMap);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			}
		} else if (fileType == FileType.SEARCH) {
			return createSearch(fileString, info, itemMemoMap);
		}
		return createIndex(fileString, info, itemMemoMap);
	}

	/**
	 * 生成index文件
	 * 
	 * @param fileString
	 * @return
	 */
	private String createIndex(String fileString, CreaterInfo info,
			HashMap<String, MemoInfo> itemMemoMap) {

		fileString = fileString.trim();
		// 格式 ： getStartDate,getEndDate
		String itemStr = IndexReplacer.getItemString(itemMemoMap);
		// 格式 ： 开始时间 时间段查询专用,结束时间 时间段查询专用
		String itemMemoStr = IndexReplacer.getMemoStr(itemMemoMap);
		// 替换"/","//"
		itemMemoStr = itemMemoStr.replaceAll("//", "");
		itemMemoStr = itemMemoStr.replaceAll("/", "");
		logger.info("itemStr : [" + itemStr + "]");
		logger.info("itemMemoStr : [" + itemMemoStr + "]");

		// 动态后成表格
		ArrayList<String> sel = info.getSelectType();
		ArrayList<String> textType = info.getTextType();
		ArrayList<String> textAreaType = info.getTextAreaType();
		ArrayList<String> uneditableType = info.getUnEditAbleType();

		for (int i = 0; i < sel.size(); i++) {
			logger.info("[" + sel.get(i) + "]");
		}
		if (sel.contains("1")) {
			logger.info("sel contain .....");
		}
		if (textType.contains("2")) {
			logger.info("textType contain .....");
		}
		String resultStr = fileString.replaceAll("@@MEMO@@", itemMemoStr);
		resultStr = resultStr.replaceAll("@@GET_ITEMS@@", itemStr);
		resultStr = resultStr.replaceAll("@@TABLE_NAME@@", info.getTableName());

		return resultStr;
	}

	/**
	 * 生成input文件
	 * 
	 * @param fileString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String createInput(String fileString, CreaterInfo info,
			HashMap<String, MemoInfo> itemMemoMap)
			throws UnsupportedEncodingException {
		// logger.info("**************************************");
		// logger.info(fileString);
		// logger.info("**************************************");
		logger.info(itemMemoMap+"");

		StringBuffer table = new StringBuffer();
		// 字段行数
		int lines = info.getRows().trim().split("\n").length;
		Set set = itemMemoMap.keySet();
		Iterator it = set.iterator();
		MemoInfo memoInfo = new MemoInfo();
		String header = "";
		String tableStr = "";
		while (it.hasNext()) {
			String key = (String) it.next();
			memoInfo = itemMemoMap.get(key);

			logger.info("******* " + memoInfo.getMemo() + "*********"
					+ memoInfo.getItemName() + "****type**"
					+ memoInfo.getType());

			// 表头
			header = "<tr><th>" + memoInfo.getMemo().trim()
					+ ":<font color=\"red\">&nbsp;&nbsp;</font></th>\n";
			// header = new String(header.getBytes("utf-8"),"GBK");
			table = table.append(header);

			// 输入框
			switch (memoInfo.getType()) {
			// text
			case 1:
				tableStr = "<td><input type=\"text\" name=\""
						+ memoInfo.getItemName() + "\" id=\""
						+ memoInfo.getItemName() + "\"></td></tr>\n";
				// tableStr = new String(tableStr.getBytes("utf-8"),"GBK");
				table = table.append(tableStr);
				break;
			// textArea
			case 2:
				tableStr = "<td><textarea name=\"" + memoInfo.getItemName()
						+ "\" id =\"" + memoInfo.getItemName()
						+ "\"  rows=\"4\" cols=\"48\"></textarea></td></tr>\n";
				// tableStr = new String(tableStr.getBytes("utf-8"),"GBK");
				table = table.append(tableStr);
				break;
			// select
			case 3:
				tableStr = "<td><select  name=\"" + memoInfo.getItemName()
						+ "\" id=\"" + memoInfo.getItemName()
						+ "\"></td></tr>\n";
				// tableStr = new String(tableStr.getBytes("utf-8"),"GBK");

				table = table.append(tableStr);
				break;
			// redio
			case 4:
				tableStr = "";
				// tableStr = new String(tableStr.getBytes("utf-8"),"GBK");
				table = table.append("");
				break;
			// checkbox
			case 5:
				tableStr = "";
				// tableStr = new String(tableStr.getBytes("utf-8"),"GBK");
				table = table.append("");
				break;
			// text uneditable
			case 6:
				tableStr = "<td><span>readonly</span></td></tr>\n";
				tableStr = new String(tableStr.getBytes("utf-8"), "GBK");
				table = table.append(tableStr);
				break;
			// textArea uneditable
			case 7:
				tableStr = "";
				tableStr = new String(tableStr.getBytes("utf-8"), "GBK");
				table = table.append("");
				break;

			default:
				tableStr = "";
				tableStr = new String(tableStr.getBytes("utf-8"), "GBK");
				table = table.append("");

			}
		}

		logger.info(table.toString());

		String result = fileString.trim().replaceAll("@@TABLE_INFO@@",
				table.toString());
		return result;
	}

	/**
	 * 生成search文件
	 * 
	 * @param fileString
	 * @return
	 */
	private String createSearch(String fileString, CreaterInfo info,
			HashMap<String, MemoInfo> itemMemoMap) {
		String resultStr = fileString.replaceAll("", "");
		return resultStr;
	}

	/**
	 * 生成show文件
	 * 
	 * @param fileString
	 * @return
	 */
	private String createShow(String fileString, CreaterInfo info,
			HashMap<String, MemoInfo> itemMemoMap) {
		String resultStr = fileString.replaceAll("", "");
		return resultStr;
	}

	/**
	 * 生成edit文件
	 * 
	 * @param fileString
	 * @return
	 */
	private String createEdit(String fileString, CreaterInfo info,
			HashMap<String, MemoInfo> itemMemoMap) {
		String resultStr = fileString.replaceAll("", "");
		return resultStr;
	}

	/**
	 * 写入文件
	 * 
	 * @param fileString
	 * @param path
	 * @param fileName
	 * @throws IOException
	 */
	public void writeToFile(String fileString, String file) throws IOException {
		// FileWriter fwriter = null;
		OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(
				new File(file)), "UTF-8");
		BufferedWriter writer = new BufferedWriter(write);
		writer.write(fileString);
		writer.flush();

		if (write != null)
			write.close();
		if (writer != null)
			writer.close();
	}

}
