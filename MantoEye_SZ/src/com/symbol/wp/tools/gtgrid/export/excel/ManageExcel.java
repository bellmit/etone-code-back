package com.symbol.wp.tools.gtgrid.export.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.core.util.Common;

public class ManageExcel {

	private final static Logger logger = LoggerFactory
			.getLogger(ManageExcel.class);
	public static String excelTitle = null;

	public static String viewName = null;

	public static String getBeanList = "getBeanList";

	public static String beanName = null;

	public static String beanSetAndGetMehtodName = null;// 格式用","分开

	public static List viewList = new ArrayList();

	/**
	 * @param args
	 *            参数设定:() 功能描述: 逻辑判断: 返回值:
	 */
	public static boolean exportstatExcel(HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException,
			InvocationTargetException {

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ Common.convertGBKToISO(excelTitle) + ".xls");
		WritableWorkbook wb = null;
		TemplateExcel te = new TemplateExcel();

		try {
			Class V_C = Class.forName(viewName);
			Class B_C = Class.forName(beanName);
			OutputStream out = response.getOutputStream();
			wb = Workbook.createWorkbook(out);
			te.sheet = wb.createSheet(excelTitle, 0);
			String titlesArray[] = null;
			Method V_M = null;
			Method B_M = null;
			Object viewObject = null;
			Object beanObject = null;
			List beanList = null;
			String titles = null;
			String topic = null; // 主题
			String topicArray[] = null;
			int beginRow = 0;
			// 主体

			if (viewList != null && !viewList.isEmpty()) {
				for (int i = 0; i < viewList.size(); i++) {

					viewObject = (Object) viewList.get(i);
					V_M = V_C.getMethod(getBeanList, null);
					beanList = (List) V_M.invoke(viewObject, null);

					V_M = V_C.getMethod("getTitles", null);
					titles = (String) V_M.invoke(viewObject, null);

					if (Common.judgeString(titles)) {
						titlesArray = titles.split(",");
					}
					// 主题
					V_M = V_C.getMethod("getTopic", null);
					topic = (String) V_M.invoke(viewObject, null);
					topicArray = topic.split("###");
					// 最顶层的标题
					// if(topicArray!=null){
					// for(int t = 0;t<topicArray.length;t++){
					// //列,行,列,行
					// te.sheet.mergeCells(0,beginRow, titlesArray.length-1, 0);
					// //te.printTitle(0, beginRow,
					// topicArray[t].length()*2,topicArray[t], te.title);
					// te.printTitle(0, beginRow,
					// topicArray[t].length()*2,topicArray[t], te.tableYellow);
					// beginRow++;//开始行指针向下移
					// }
					// }

					// 标题
					if (titlesArray != null) {
						te.publictitle.clear();
						for (int j = 0; j < titlesArray.length; j++) {
							te.TitleInput(titlesArray[j]);
						}
					}
					// te.printTitle(beginRow, te.title);
					te.printTitle(beginRow, te.tableYellow);
					beginRow++;// 开始行指针向下移
					String array_beanSetAndGetMehtodName[] = null;
					if (Common.judgeString(beanSetAndGetMehtodName)) {
						array_beanSetAndGetMehtodName = beanSetAndGetMehtodName
								.split(",");
					}

					// 内容
					if (beanList != null && !beanList.isEmpty()) {
						for (int j = 0; j < beanList.size(); j++) {
							beanObject = (Object) beanList.get(j);
							if (array_beanSetAndGetMehtodName != null) {
								for (int k = 0; k < array_beanSetAndGetMehtodName.length; k++) {
									if (array_beanSetAndGetMehtodName[k] != null)
										B_M = B_C
												.getMethod(
														array_beanSetAndGetMehtodName[k],
														null);
									if (k == 0) {
										// te.printTitle(k, beginRow,
										// String.valueOf(B_M.invoke(beanObject,
										// null)), te.tableYellow);
										te.printTitle(k, beginRow, String
												.valueOf(B_M.invoke(beanObject,
														null)), te.table);
									} else {
										te.printTitle(k, beginRow, String
												.valueOf(B_M.invoke(beanObject,
														null)), te.table);
									}
								}
							}
							beginRow++;
						}
					}
				}
			}
			wb.write();
			wb.close();
			return true;
		} catch (Exception ex) {
			try {
				wb.close();
			} catch (WriteException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			logger.error(ex.getMessage());
			return true;
		}
	}
}
