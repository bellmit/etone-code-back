package com.symbol.wp.tools.gtgrid.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import jxl.write.WritableCellFormat;

import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.tools.gtgrid.common.Const;
import com.symbol.wp.tools.gtgrid.export.AbstractXlsWriter;
import com.symbol.wp.tools.gtgrid.export.CsvWriter;
import com.symbol.wp.tools.gtgrid.export.ExcelMergeBean;
import com.symbol.wp.tools.gtgrid.export.ExcelStyle;
import com.symbol.wp.tools.gtgrid.export.JxlXlsWriter;
import com.symbol.wp.tools.gtgrid.model.ColumnInfo;
import com.symbol.wp.tools.gtgrid.model.FilterInfo;
import com.symbol.wp.tools.gtgrid.model.GridInfo;
import com.symbol.wp.tools.gtgrid.model.PageInfo;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.org.json.JSONException;
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.util.BeanUtils;
import com.symbol.wp.tools.gtgrid.util.BytesUtils;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;
import com.symbol.wp.tools.gtgrid.util.LogHandler;
import com.symbol.wp.tools.gtgrid.util.ModelUtils;
import com.symbol.wp.tools.gtgrid.util.StringUtils;

/**
 * @author fins
 * 
 */
public class GridServerHandler {

	public static String CONTENTTYPE = "text/html; charset=UTF-8";
	public static String GT_JSON_NAME = "_gt_json";
	public static String DATA_ROOT = "data";

	private String action;
	private String exception;

	private List data;
	private String recordType;
	private String encoding = null;
	private boolean encodeFileName = true;

	private List fieldsName;

	private boolean success;

	private HttpServletRequest request;
	private HttpServletResponse response;

	private JSONObject jsonObject;

	private Class dataBeanClass = null;
	private JSONArray jsonData = null;

	private GridInfo gridInfo = new GridInfo();

	private PageInfo pageInfo = new PageInfo();

	private boolean remotePaging = false;

	private List sortInfo = new ArrayList();
	private List filterInfo = new ArrayList();
	private List columnInfo = new ArrayList();

	private Map parameters = new HashMap();

	private Map parameterMap;

	private Map writers = new HashMap();

	private List<ExcelMergeBean> mergeArray;

	private boolean[] isNumber;// 是否是数字
	
	private String otherDataInfo;

	/**
	 * 设置需要合并的表头列
	 * 
	 * @param mergeArray
	 */
	public void setMergeArray(List<ExcelMergeBean> mergeArray) {
		this.mergeArray = mergeArray;
	}

	public GridServerHandler() {
	}

	public GridServerHandler(String gtJson) {
		init(gtJson);
	}

	public GridServerHandler(Map parameterMap) {
		setParameterMap(parameterMap);
		init();
	}

	public GridServerHandler(HttpServletRequest request,
			HttpServletResponse response) {
		setRequest(request);
		setResponse(response);
		init();
	}

	public void init(String gtJson) {
		if (StringUtils.isNotEmpty(gtJson)) {
			try {
				// LogHandler.debug(" AJAX IN : "+gtJson);
				jsonObject = new JSONObject(gtJson);
				action = jsonObject.getString("action");
				recordType = jsonObject.has("remotePaging") ? jsonObject
						.getString("recordType") : null;
				remotePaging = jsonObject.has("remotePaging") ? jsonObject
						.getBoolean("remotePaging") : false;

				initGridInfo();

				if ("load".equalsIgnoreCase(action)) {
					initPageInfo();
					initSortInfo();
					initFilterInfo();
				} else if ("save".equalsIgnoreCase(action)) {

				} else if ("export".equalsIgnoreCase(action)) {
					initColumnInfo();
				}
			} catch (JSONException e) {
				LogHandler.error(this, e);
			}
		}
	}

	public boolean isRemotePaging() {
		return remotePaging;
	}

	public void init() {
		init(getParameter(GT_JSON_NAME));
	}

	public void initGridInfo() {
		JSONObject modelJS;
		try {
			modelJS = jsonObject.getJSONObject("gridInfo");
			if (modelJS != null) {
				setGridInfo(ModelUtils.createGridInfo(modelJS));
			}
		} catch (JSONException e) {
			// LogHandler.error(this,e);
		}
	}

	public void initPageInfo() {
		JSONObject modelJS;
		try {
			modelJS = jsonObject.getJSONObject("pageInfo");
			if (modelJS != null) {
				setPageInfo(ModelUtils.createPageInfo(modelJS));
			}
		} catch (JSONException e) {
			LogHandler.error(this, e);
		}
	}

	public void initSortInfo() {
		JSONArray modelJS;
		try {
			modelJS = jsonObject.getJSONArray("sortInfo");
			if (modelJS != null) {
				for (int i = 0; i < modelJS.length(); i++) {
					JSONObject si = modelJS.getJSONObject(i);
					sortInfo.add(ModelUtils.createSortInfo(si));
				}
			}
		} catch (JSONException e) {
			LogHandler.error(this, e);
		}
	}

	public void initFilterInfo() {
		JSONArray modelJS;
		try {
			modelJS = jsonObject.getJSONArray("filterInfo");
			if (modelJS != null) {
				for (int i = 0; i < modelJS.length(); i++) {
					JSONObject si = modelJS.getJSONObject(i);
					filterInfo.add(ModelUtils.createFilterInfo(si));
				}
			}
		} catch (JSONException e) {
			LogHandler.error(this, e);
		}
	}

	public void initColumnInfo() {
		JSONArray modelJS;
		try {
			modelJS = jsonObject.getJSONArray("columnInfo");
			if (modelJS != null) {
				for (int i = 0; i < modelJS.length(); i++) {
					JSONObject si = modelJS.getJSONObject(i);
					columnInfo.add(ModelUtils.createColumnInfo(si));
				}
			}
		} catch (JSONException e) {
			LogHandler.error(this, e);
		}
	}

	public void setXlsWriter(AbstractXlsWriter writer) {
		writers.put("xls", writer);
	}

	public AbstractXlsWriter getXlsWriter() {
		AbstractXlsWriter writer = null;
		try {
			writer = (AbstractXlsWriter) writers.get("xls");
		} catch (Exception e) {
			LogHandler.warn(this, e);
		}
		if (writer == null) {
			writer = new JxlXlsWriter();
		}
		return writer;
	}

	public List getDisplayColumnInfo() {
		List disColumnInfo = new ArrayList();
		if (columnInfo != null) {
			for (int i = 0, len = columnInfo.size(); i < len; i++) {
				ColumnInfo col = (ColumnInfo) columnInfo.get(i);
				if (!col.isHidden() && col.isExportable()) {
					disColumnInfo.add(col);
				}
			}
		}
		return disColumnInfo;
	}

	public String getSaveResponseText() {
		JSONObject json = new JSONObject();
		try {
			json.put("success", success);
			json.put("exception", exception);
		} catch (JSONException e) {
			LogHandler.error(this, e);
		}
		return json.toString();
	}

	public JSONObject getLoadResponseJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put(DATA_ROOT, jsonData);
			json
					.put("pageInfo", ModelUtils
							.generatePageInfoJSON(getPageInfo()));
			json.put("otherDataInfo", otherDataInfo);
			json.put("exception", exception);
		} catch (JSONException e) {
			LogHandler.error(this, e);
		}
		return json;
	}

	public String getLoadResponseText() {
		JSONObject json = getLoadResponseJSON();
		String jstr = json == null ? "" : json.toString();
		// LogHandler.debug(" AJAX OUT : "+jstr);
		return jstr;
	}
	public void setOtherDataInfo(String info) {
		this.otherDataInfo = info;
	}
	public void setData(List data) {
		this.data = data;
		this.dataBeanClass = null;
		setJsonData(new JSONArray(data));

	}

	public void setData(List data, Class beanClass) {
		this.data = data;
		this.dataBeanClass = beanClass;
		setJsonData(JSONUtils.BeanList2JSONArray(data, beanClass));
	}

	public List getUpdatedRecords() {
		return getRecordsList("updatedRecords");
	}

	public List getUpdatedRecords(Class beanClass) {
		return getRecordsList("updatedRecords", beanClass);
	}

	public List getInsertedRecords() {
		return getRecordsList("insertedRecords");
	}

	public List getInsertedRecords(Class beanClass) {
		return getRecordsList("insertedRecords", beanClass);
	}

	public List getDeletedRecords() {
		return getRecordsList("deletedRecords");
	}

	public List getDeletedRecords(Class beanClass) {
		return getRecordsList("deletedRecords", beanClass);
	}

	public List getRecordsList(String rname, Class beanClass) {
		List recordsList = null;
		JSONArray records_JS;
		try {
			records_JS = jsonObject.getJSONArray(rname);
			if (records_JS != null) {
				recordsList = new ArrayList();
				Object[] methodsInfo = BeanUtils
						.getCacheSetterMethodInfo(beanClass);
				for (int i = 0; i < records_JS.length(); i++) {
					JSONObject obj_JS = records_JS.getJSONObject(i);
					recordsList.add(JSONUtils.JSONObject2Bean(obj_JS,
							beanClass, methodsInfo));
				}
			}
		} catch (JSONException e) {
			// LogHandler.error(this,e);
		}
		return recordsList;
	}

	public List getRecordsList(String rname) {
		List recordsList = null;
		JSONArray records_JS;
		try {
			records_JS = jsonObject.getJSONArray(rname);
			if (records_JS != null) {
				recordsList = new ArrayList();
				for (int i = 0; i < records_JS.length(); i++) {
					JSONObject obj_JS = records_JS.getJSONObject(i);
					recordsList.add(JSONUtils.JSONObject2Map(obj_JS));
				}
			}
		} catch (JSONException e) {
			// LogHandler.error(this,e);
		}
		return recordsList;
	}

	public String[] getParameterValues(String name) {
		return (String[]) parameterMap.get(name);
	}

	public String getParameter(String name) {
		String[] pv = getParameterValues(name);
		if (pv != null && pv.length > 0) {
			return pv[0];
		}
		return null;
	}

	public void printResponseText(String text) {
		try {
			response.setContentType(CONTENTTYPE);
			PrintWriter out = response.getWriter();
			out.println(text);
			out.flush();
			out.close();
		} catch (IOException e) {
			LogHandler.error(this, e);
		}
	}

	public void initAttachmentHeader() {
		getResponse().setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		getResponse().setHeader("Content-Type", "application/force-download");
		getResponse().setHeader("Content-Type", "application/octet-stream");
		getResponse().setHeader("Content-Type", "application/download");
		getResponse().setHeader("Cache-Control",
				"private, max-age=0, must-revalidate");
		getResponse().setHeader("Pragma", "public");
	}

	public void downloadFile(String fileName) {
		downloadFile(fileName, Const.nullInt);
	}

	public String encodeFileName(String fileName) {
		String agent = getRequest().getHeader("USER-AGENT");
		fileName = fileName.replaceAll("\n|\r", " ").trim();
		if (null != agent) {
			try {
				agent = agent.toUpperCase();
				if (agent.indexOf("MSIE") != -1) {
					fileName = URLEncoder.encode(fileName, "UTF8");
				} else if (agent.indexOf("MOZILLA") != -1) {
					fileName = "=?UTF-8?B?"
							+ (new String(BytesUtils.toBase64Chars(fileName
									.getBytes("UTF-8")))) + "?=";
				}
			} catch (UnsupportedEncodingException e) {
				// do nothing
			}
		}
		return fileName;
	}

	public void downloadFile(String fileName, long length) {
		initAttachmentHeader();
		if (isEncodeFileName()) {
			fileName = encodeFileName(fileName);
		}
		getResponse().setHeader("Content-Disposition",
				"attachment; filename=\"" + fileName + "\"");
		if (length != Const.nullInt) {
			getResponse().setHeader("Content-Length", String.valueOf(length));
		}
	}

	public void exportXLS(List data, Class beanClass) throws IOException {
		List cols = this.getDisplayColumnInfo();
		int len = cols.size();

		String[] properiesName = new String[len];
		String[] headsName = new String[len];
		isNumber = new boolean[len];

		for (int i = 0; i < len; i++) {
			ColumnInfo colInfo = (ColumnInfo) cols.get(i);
			properiesName[i] = colInfo.getFieldIndex();
			headsName[i] = colInfo.getHeader();
			isNumber[i] = colInfo.isExportnumber();
			// System.out.println("isNumber[i]:"+isNumber[i]);
		}
		exportXLS(data, properiesName, headsName, beanClass);
	}

	public void exportXLSfromMaps(List data) throws IOException {
		exportXLS(data, Map.class);
	}

	public void exportXLS(List data, String[] properiesName,
			String[] headsName, Class beanClass) throws IOException {
		String fileName = getParameter("exportFileName");
		downloadFile(fileName + ".xls");

		ExcelStyle styles = new ExcelStyle();
		WritableCellFormat titlestyle = styles.getTableYellow();
		WritableCellFormat fieldstyle = styles.getTable();

		OutputStream out = getResponse().getOutputStream();
		AbstractXlsWriter xlsw = getXlsWriter();
		xlsw.init();
		xlsw.setOut(out);
		xlsw.setEncoding(getEncoding());
		xlsw.start();

		String mergeRows = "";

		// 需要合并的列
		if (mergeArray != null && mergeArray.size() > 0) {
			mergeRows = xlsw.addMergeTitle(mergeArray, titlestyle);
		}
		// System.out.println(mergeRows+"-----------");
		xlsw.addTitle(headsName, titlestyle, mergeRows);
		if (beanClass == null || Map.class.isAssignableFrom(beanClass)) {
			for (int i = 0, len = data.size(); i < len; i++) {
				Map record = (Map) data.get(i);
				if (isNumber != null && isNumber.length == properiesName.length) {
					xlsw.addRow(BeanUtils.map2Array(record, properiesName,
							isNumber), fieldstyle);
				} else {
					xlsw.addRow(BeanUtils.map2Array(record, properiesName),
							fieldstyle);
				}
			}
		} else {
			for (int i = 0, len = data.size(); i < len; i++) {
				Object record = data.get(i);
				if (isNumber != null && isNumber.length == properiesName.length) {
					xlsw.addRow(BeanUtils.bean2Array(record, properiesName,
							beanClass, isNumber), fieldstyle);
				} else {
					xlsw.addRow(BeanUtils.bean2Array(record, properiesName,
							beanClass), fieldstyle);
				}
			}
		}

		xlsw.end();
		xlsw.close();
	}

	public void exportXLSfromMaps(List data, String[] properiesName,
			String[] headsName) throws IOException {
		exportXLS(data, properiesName, headsName, Map.class);
	}

	// ///////////CSV Export//////////////
	private String[] objectArray2StringArray(Object[] objects) {
		String[] strs = new String[objects.length];
		for (int i = 0; i < objects.length; i++) {
			strs[i] = objects[i] + "";
		}
		return strs;
	}

	public void exportCSV(List data, Class beanClass) throws IOException {
		List cols = this.getDisplayColumnInfo();
		int len = cols.size();

		String[] properiesName = new String[len];
		String[] headsName = new String[len];
		isNumber = new boolean[len];

		for (int i = 0; i < len; i++) {
			ColumnInfo colInfo = (ColumnInfo) cols.get(i);
			properiesName[i] = colInfo.getFieldIndex();
			headsName[i] = colInfo.getHeader();
			isNumber[i] = colInfo.isExportnumber();
		}
		exportCSV(data, properiesName, headsName, beanClass);
	}

	public void exportCSVfromMaps(List data) throws IOException {
		exportCSV(data, Map.class);
	}

	public void exportCSV(List data, String[] properiesName,
			String[] headsName, Class beanClass) throws IOException {
		
		String fileName = getParameter("exportFileName");
		downloadFile(fileName + ".csv");
		OutputStream out = getResponse().getOutputStream();
		
		CsvWriter csvw = new CsvWriter(out,',',Charset.forName("GBK"));
		csvw.writeRecord(headsName);
		if (beanClass == null || Map.class.isAssignableFrom(beanClass)) {
			for (int i = 0, len = data.size(); i < len; i++) {
				Map record = (Map) data.get(i);
				csvw.writeRecord(objectArray2StringArray(BeanUtils.map2Array(
						record, properiesName, isNumber)));
			}
		} else {
			for (int i = 0, len = data.size(); i < len; i++) {
				Object record = data.get(i);
				csvw.writeRecord(objectArray2StringArray(BeanUtils.bean2Array(
						record, properiesName, beanClass)));
			}
		}
		csvw.endRecord();
		csvw.flush();
		csvw.close();
		
		out.flush();
		out.close();
	}

	// ////////////CSV Export END///////////////
	public void printSaveResponseText() {
		printResponseText(getSaveResponseText());
	}

	public void printLoadResponseText() {
		printResponseText(getLoadResponseText());
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public List getSortInfo() {
		return sortInfo;
	}

	public void setSortInfo(List sortInfo) {
		this.sortInfo = sortInfo;
	}

	public SortInfo getSingleSortInfo() {
		return sortInfo == null || sortInfo.size() < 1 ? null
				: (SortInfo) sortInfo.get(0);
	}

	public List getFilterInfo() {
		return filterInfo;
	}

	public void setFilterInfo(List filterInfo) {
		this.filterInfo = filterInfo;
	}

	public FilterInfo getSingleFilterInfo() {
		return filterInfo == null || filterInfo.size() < 1 ? null
				: (FilterInfo) filterInfo.get(0);
	}

	public List getData() {
		return data;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public List getFieldsName() {
		return fieldsName;
	}

	public void setFieldsName(List fieldsName) {
		this.fieldsName = fieldsName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getPageSize() {
		return getPageInfo().getPageSize();
	}

	public void setPageSize(int pageSize) {
		getPageInfo().setPageSize(pageSize);
	}

	public int getPageNum() {
		return getPageInfo().getPageNum();

	}

	public void setPageNum(int pageNum) {
		getPageInfo().setPageNum(pageNum);
	}

	public int getTotalRowNum() {
		return getPageInfo().getTotalRowNum();
	}

	public void setTotalRowNum(int totalRowNum) {
		getPageInfo().setTotalRowNum(totalRowNum);
	}

	public int getTotalPageNum() {
		return getPageInfo().getTotalPageNum();
	}

	public void setTotalPageNum(int totalPageNum) {
		getPageInfo().setTotalPageNum(totalPageNum);
	}

	public int getStartRowNum() {
		return getPageInfo().getStartRowNum();
	}

	public void setStartRowNum(int startRowNum) {
		getPageInfo().setStartRowNum(startRowNum);
	}

	public int getEndRowNum() {
		return getPageInfo().getEndRowNum();
	}

	public void setEndRowNum(int endRowNum) {
		getPageInfo().setEndRowNum(endRowNum);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
		setParameterMap(request.getParameterMap());
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public Class getDataBeanClass() {
		return dataBeanClass;
	}

	public void setDataBeanClass(Class dataBeanClass) {
		this.dataBeanClass = dataBeanClass;
	}

	public JSONArray getJsonData() {
		return jsonData;
	}

	public void setJsonData(JSONArray jsonData) {
		this.jsonData = jsonData;
	}

	public Map getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map parameterMap) {
		this.parameterMap = parameterMap;
	}

	public static int getInt(Object i) {
		return getInt(i, -1);
	}

	public static int getInt(Object i, int defaultI) {
		try {
			if (i != null) {
				return Integer.parseInt(String.valueOf(i));
			}
		} catch (Exception e) {
		}
		return defaultI;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public GridInfo getGridInfo() {
		return gridInfo;
	}

	public void setGridInfo(GridInfo gridInfo) {
		this.gridInfo = gridInfo;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public List getColumnInfo() {
		return columnInfo;
	}

	public void setColumnInfo(List columnInfo) {
		this.columnInfo = columnInfo;
	}

	public boolean isEncodeFileName() {
		return encodeFileName;
	}

	public void setEncodeFileName(boolean encodeFileName) {
		this.encodeFileName = encodeFileName;
	}

}
