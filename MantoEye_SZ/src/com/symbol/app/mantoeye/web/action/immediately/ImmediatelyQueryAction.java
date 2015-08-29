package com.symbol.app.mantoeye.web.action.immediately;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.ImmediatelyQueryEntity;
import com.symbol.app.mantoeye.dto.flush.TableColumnMapEntity;
import com.symbol.app.mantoeye.dto.flush.TableMapEntity;
import com.symbol.app.mantoeye.service.immediately.ImmediatelyQueryManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class ImmediatelyQueryAction extends BaseDispatchAction {

	HttpServletRequest request = ServletActionContext.getRequest();
	@Autowired
	private ImmediatelyQueryManager immediatelyQueryManager;
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;
	private String tableId = "-1";
	private String sql = "";

	private Page<ImmediatelyQueryEntity> page = new Page<ImmediatelyQueryEntity>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	/**
	 * 查询所有表
	 */
	public void queryAllTable() {
		try {
			List<String> dl = null;
			List<TableMapEntity> list = immediatelyQueryManager.queryAllTabke();
			String str = "<?str version=\"1.0\" encoding=\"gb2312\"?>";
			str = str + "<root>";
			str = str + "<data>";
			for (int i = 0; i < list.size(); i++) {
				TableMapEntity tableMap = list.get(i);
				dl = tableMap.getDimensions();
				str = str + "<da>";
				str = str + "<id>" + tableMap.getNmTableMapId() + "</id>";
				str = str + "<name>" + tableMap.getVcTableName() + "</name>";
				str = str + "<nickname>" + tableMap.getVcTableNickName()
						+ "</nickname>";
				str = str + "<tabletype>" + tableMap.getIntTableType()
						+ "</tabletype>";
				str = str + "<businesstype>" + tableMap.getIntBusinessType()
						+ "</businesstype>";
				str = str + "<dimensions>";
				if (dl != null) {
					for (String s : dl) {
						str = str + "<dim>" + s + "</dim>";
					}
				}
				str = str + "</dimensions>";
				str = str + "</da>";
			}
			str = str + "</data>";
			str = str + "</root>";

			Struts2Utils.renderText(str);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
		}

	}

	/**
	 * 查询所有表
	 */
	/*
	 * public void queryColumnMap() { List<TableColumnMapEntity> list
	 * =immediatelyQueryManager.queryColumnMap();
	 * Map<String,List<TableColumnMapEntity>> map=this.changeData(list);
	 * List<String> keys=new ArrayList<String>(map.keySet());
	 * List<TableColumnMapEntity> tableColumnList=null; String xml =
	 * "<?xml version=\"1.0\" encoding=\"gb2312\"?>"; xml = xml + "<root>";
	 * 
	 * if(keys!=null&&!keys.isEmpty()){ for (int i = 0; i < keys.size(); i++) {
	 * String id=keys.get(i); tableColumnList=map.get(id); xml = xml + "<data>";
	 * xml = xml + "<id>" + id + "</id>"; String columnName=""; String
	 * columnType=""; String nickName=""; for(int
	 * j=0;j<tableColumnList.size();j++){ TableColumnMapEntity tableMap =
	 * tableColumnList.get(j);
	 * columnName=columnName+tableMap.getVcColumnName()+":";
	 * columnType=columnType+tableMap.getIntColumnType()+":";
	 * nickName=nickName+tableMap.getVcColumnNickName()+":"; }
	 * if(columnName!=""){ columnName=columnName.substring(0,
	 * columnName.length()-1); columnType=columnType.substring(0,
	 * columnType.length()-1); nickName=nickName.substring(0,
	 * nickName.length()-1); } xml = xml + "<name>" + columnName + "</name>";
	 * xml = xml + "<columntype>" + columnType + "</columntype>"; xml = xml +
	 * "<nickname>" + nickName + "</nickname>"; xml = xml + "</data>"; } } xml =
	 * xml + "</root>"; Struts2Utils.renderText(xml);
	 * 
	 * }
	 */

	/**
	 * 根据表ID 查询表里所有的列字段
	 */
	public void query() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<TableColumnMapEntity> list = new ArrayList<TableColumnMapEntity>();
		if (!tableId.equals("-1")) {
			list = immediatelyQueryManager.queryColumnMapByTableId(Common
					.StringToInt(tableId));
		}
		gridServerHandler.setData(list, TableColumnMapEntity.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/*
	 * public Map<String,List<TableColumnMapEntity>>
	 * changeData(List<TableColumnMapEntity> list){ Map<String
	 * ,List<TableColumnMapEntity>> map=new
	 * HashMap<String,List<TableColumnMapEntity>>();
	 * if(list!=null&&!list.isEmpty()){ for(int i=0;i<list.size();i++){
	 * TableColumnMapEntity columnMap=list.get(i);
	 * if(map.get(columnMap.getNmTableMapId())!=null){
	 * map.get(columnMap.getNmTableMapId()).add(columnMap); }else{
	 * List<TableColumnMapEntity> listData=new
	 * ArrayList<TableColumnMapEntity>(); listData.add(columnMap);
	 * map.put(columnMap.getNmTableMapId(), listData); } } } return map; }
	 */

	/**
	 * 根据页面组装的SQL语句 查询相应的数据
	 */
	public void immediatelyQuery() {
		try {
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			this.page.setPageSize(gridServerHandler.getPageSize());
			this.page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			sql = sql.replace("$", "%");

			String[] sqlArr = sql.split("@");

			if (sqlArr.length > 1) {
				sql = sqlArr[0];
				String sqlll = sqlArr[1];
				String sqlTest = sqlll;
				String[] weekArr = sqlTest.split("~");
				String weekName = weekArr[0];
				String opr1 = weekArr[1];
				String va1 = weekArr[2];
				String opr2 = weekArr[3];
				String va2 = weekArr[4];
				String flag1 = weekArr[5];
				String flag2 = weekArr[6];
				if ("true".equalsIgnoreCase(flag1.trim())
						&& "true".equalsIgnoreCase(flag2.trim())) {
					int v1 = CommonUtils.getWeek(Common.getDate(va1));
					int v2 = CommonUtils.getWeek(Common.getDate(va2));
					String con1 = weekName + opr1 + va1;
					String con2 = weekName + opr2 + va2;
					sql = sql.replace(con1, weekName + opr1 + v1);
					sql = sql.replace(con2, weekName + opr2 + v2);
				} else if ("true".equalsIgnoreCase(flag1.trim())
						&& "false".equalsIgnoreCase(flag2.trim())) {
					int v1 = CommonUtils.getWeek(Common.getDate(va1));
					String con1 = weekName + opr1 + va1;
					sql = sql.replace(con1, weekName + opr1 + v1);
				} else if ("false".equalsIgnoreCase(flag1.trim())
						&& "true".equalsIgnoreCase(flag2.trim())) {
					int v2 = CommonUtils.getWeek(Common.getDate(va2));
					String con2 = weekName + opr2 + va2;
					sql = sql.replace(con2, weekName + opr2 + v2);
				}
			}

			request.getSession().setAttribute("immedsql4export", sql);
			this.page = this.immediatelyQueryManager.queryBySql(page, sql,
					false);
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = this.page.getTotalCount();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}

			List<ImmediatelyQueryEntity> listData = this.page.getResult();
			gridServerHandler.setData(listData, ImmediatelyQueryEntity.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText(),
					new String[0]);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private String ninkName;

	/**
	 * 根据页面组装的SQL语句 查询相应的数据 导出
	 */
	public void export() throws ServletException, IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// sql=sql.replace("$", "%");
		sql = request.getSession().getAttribute("immedsql4export") + "";
		String[] sqla = sql.split(" ");
		String table = "--";
		try {
			for (int i = 0; i < sqla.length - 1; i++) {
				if ("FROM".equals(sqla[i].trim().toUpperCase())) {
					table = sqla[i + 1];
					break;
				}
			}
		} catch (Exception e) {
			// logger.error(e.getMessage());
		}
		String exportmsg = ExportMsg.EXPORT_OFFHAND_DATA + "（table:" + table
				+ "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		long totalCount = immediatelyQueryManager.getTotalCount(sql);
		if (totalCount > 60000) {
			// gridServerHandler.exportCSV(listData,
			// ImmediatelyQueryEntity.class);

			String localDir = "";
			String iqServerPath = "";
			String queryTime = "";

			ninkName = new String(ninkName.getBytes("ISO-8859-1"), "UTF-8");

			try {
				localDir = PropertiesUtil.getInstance().getProperty(
						"data_catch_localDir");
				iqServerPath = PropertiesUtil.getInstance().getProperty(
						"database_server_temp_path");
				queryTime = String.valueOf(System.currentTimeMillis());
				immediatelyQueryManager.queryBySql(sql, localDir, iqServerPath,
						queryTime, ninkName);
				downloadFileLoacl(localDir, queryTime);
			} catch (RuntimeException e) {
				logger.error(e.getMessage());
			} finally {
				immediatelyQueryManager.deleteTempFile(localDir, iqServerPath,
						queryTime);
			}

		} else {
			this.page = this.immediatelyQueryManager
					.queryBySql(page, sql, true);
			List<ImmediatelyQueryEntity> listData = this.page.getResult();
			gridServerHandler.exportXLS(listData, ImmediatelyQueryEntity.class);
		}
	}

	private final int BUFFER_INPUT = 16 * 1024;

	private void downloadFileLoacl(String localDir, String queryTime) {
		InputStream in = null;
		OutputStream out = null;
		String filePath = "";
		String name = "大数据导出-" + queryTime + ".csv";
		filePath = localDir + name;

		try {
			out = getServletResponse().getOutputStream();
			getServletResponse().setContentType("application/octet-stream");
			in = new BufferedInputStream(new FileInputStream(filePath),
					BUFFER_INPUT);

			getServletResponse().setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(name, "utf-8"));
			byte[] buffer = new byte[BUFFER_INPUT];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

			out.flush();
		} catch (Exception e) {
			logger.error("错误产生类:[" + e.getClass().getName() + "] - 错误原因:["
					+ e.getLocalizedMessage() + "]");
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("错误产生类:["
							+ e.getStackTrace().getClass().getName()
							+ "] - 错误原因:[" + e.getCause().toString() + "]");
				}
			}

			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("错误产生类:["
							+ e.getStackTrace().getClass().getName()
							+ "] - 错误原因:[" + e.getMessage() + "]");
				}
			}
		}

	}

	/**
	 * 根据页面组装的SQL语句 查询相应的数据 导出
	 */
	public void exportCsv() throws ServletException, IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// sql=sql.replace("$", "%");
		sql = request.getSession().getAttribute("immedsql4export") + "";
		this.page = this.immediatelyQueryManager.queryBySql(page, sql, true);
		List<ImmediatelyQueryEntity> listData = this.page.getResult();

		gridServerHandler.exportCSV(listData, ImmediatelyQueryEntity.class);
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getNinkName() {
		return ninkName;
	}

	public void setNinkName(String ninkName) {
		this.ninkName = ninkName;
	}

}
