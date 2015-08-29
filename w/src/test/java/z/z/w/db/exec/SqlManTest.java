/**
 * z.z.w.db.exec.SqlManTest.java
 */
package z.z.w.db.exec;

import static org.junit.Assert.fail;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import z.z.w.common.DateTools;
import z.z.w.db.JdbcInfo;
import z.z.w.env.Config;

/**
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 11:44:03 AM
 */
public class SqlManTest {

	protected SqlMan sqlMan = null;

	/**
	 * <br>
	 * Created on: Nov 22, 2012 11:23:32 AM
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		Config conf = new Config("etc/jdbc.cfg");
		conf.setRootKey("localhost.sybase");
		// conf.setRootKey("db.sybase");
		JdbcInfo info = JdbcInfo.getInstance("sybase");
		info.loadFrom(conf);
		// System.out.println(info.toString());
		// System.out.println("--------------------------------");
		// JdbcInfo info = JdbcInfo.getInstance("mysql");
		// conf.setRootKey("db.mysql");
		// info.loadFrom(conf);

		try {
			sqlMan = new SqlMan();
			sqlMan.createConnection(info);

			if (!sqlMan.isConnected()) {
				System.out.println("CONNECTION NOT CONNECTED.");
				System.exit(1);
			}
		} catch (SQLException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 11:23:32 AM
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		disConnectionIq();
	}

	protected final void disConnectionIq() {
		if (null != sqlMan) {
			sqlMan.close();
			sqlMan = null;
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.db.exec.SqlMan#prepareStatement(java.lang.String)}.
	 */
	@Test
	public void testPrepareStatement() {
		try {

			if (!sqlMan.isConnected())
				return;
			PreparedStatement pstmt = sqlMan
					.prepareStatement("select * from ftbDataGetterTask");
			pstmt.execute();
		} catch (SQLException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.db.exec.SqlMan#prepareCall(java.lang.String)}.
	 */
	@Test
	public void testPrepareCall() {
		try {
			if (!sqlMan.isConnected())
				return;
			CallableStatement cstmt = sqlMan.prepareCall("call test_test()");
			cstmt.execute();
		} catch (SQLException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.db.exec.SqlMan#executeUpdate(java.lang.String)}.
	 */
	@Test
	public void testExecuteUpdate() {

		try {

			List<String> list = new ArrayList<String>();
			int i = 0;
			while (i++ < 1) {
				// Random rand = new Random(System.currentTimeMillis());
				String sql = "";

				// sql = "insert into  ftbGbAppData_2012121810"
				// +
				// "(vcBsc,nmFlush,nmDimensId,nmGroupCgiId,vcMsisdn,vcImsi,intYear,intMonth,intDay,intWeek,intHour)";

				// sql = "insert into  tbmCellvsCellGroup" +
				// "(nmCellId,nmCellGroupId)";
				// sql = "insert into  tbCellGroup" + "(vcName)";

				// sql =
				// "insert into tbTaskInfo(vcTaskName,intTaskPriority,vcFactTableName,intGranularity,btFlag,vcSqlId) ";

				sql = "insert into tbFactTableOperation(vcTableName,dtFactTableCreateTime,dtBLoadTime,dtELoadTime,"
						+ "dtBIndexTime,dtEIndexTime,biIndexComplete,biTaskCreated) ";

				// +
				// "biTaskCreated,intTaskNum,dtTaskCreateTime,biTaskComplete,intTaskCompleteNum,dtTaskCompleteTime)";
				// sql = "insert into  tbCell" + "(vcCgi,vcBsc,vcCell)";
				// sql = "insert into  dtbGroupTree"
				// +
				// "(nmDimensId,vcDimensName,nmGroupId,vcGroupName,nmParentId,vcParentGroupName,nmTypeId, nmLevelsId)";

				// String nmsdn = "8613"
				// + String.format("%09d", (rand.nextInt(999999999)
				// % (999999999 - 1 + 1) + 1));
				//
				// String imsi = "40013"
				// + String.format("%09d", (rand.nextInt(9999999)
				// % (9999999 - 1 + 1) + 1));
				//
				// int bsc = rand.nextInt(9) % (9 - 1 + 1) + 1;
				// int did = rand.nextInt(12) % (12 - 1 + 1) + 1;
				// int gid = rand.nextInt(22) % (22 - 1 + 1) + 1;
				// int pid = rand.nextInt(32) % (32 - 1 + 1) + 1;
				// int flush = rand.nextInt(934399) % (943299 - 1 + 1) + 1;
				// int hour = rand.nextInt(23) % (23 - 0 + 0) + 0;

				// sql = sql
				// + "\n values ('bsc"
				// + bsc
				// + "',"
				// + flush
				// + ","
				// + did
				// + ","
				// + gid
				// + ",'"
				// + nmsdn
				// + "','"
				// + imsi
				// + "',2012,12,18,"
				// + DateTools.getWeek(DateTools.getDate(2012, 12, 18,
				// hour, 0, 0)) + "," + hour + "); commit;";

				// "(nmDimensId,vcDimensName,nmGroupId,vcGroupName,nmParentId,vcParentGroupName,nmTypeId, nmLevelsId)";
				// sql = sql + "\n values (" + did + ",'vcDimensName" + did +
				// "',"
				// + gid + ",'vcGroupName" + gid + "'," + pid + ",'pid"
				// + pid + "',1, 2); commit;";

				/**
				 * (vcTableName,dtFactTableCreateTime,dtBLoadTime,dtELoadTime,"
				 * + "dtBIndexTime,dtEIndexTime,biIndexComplete,";
				 * 
				 * vcTaskName,intTaskPriority,vcFactTableName,intGranularity,
				 * btFlag,vcSqlId)
				 */

				// sql = sql
				// +
				// "\n values ('流量經營分析_PROC_區域內容分析_時用戶統計',0,'ftbGbAppData_2012121810',0,1,'call_proc_area_addup_hour')";
				// sql = sql
				// +
				// " values ('流量經營分析_PROC_區域網站二級分析_時用戶統計',0,'ftbGbAppData_2012121810',0,1,'call_proc_area_addup_hour')";

				sql = sql
						+ " values ('ftbGbAppData_2012121810','"
						+ DateTools.getParseDateToStr(DateTools.getDate(
								"2012121810", DateTools.YYYYMMDDHH))
						+ "',getDate(),getDate(),getDate(),getDate(),1,0)";

				System.out.println(sql);

				list.add(sql);

			}

			if (!sqlMan.isConnected())
				return;
			System.out.println(sqlMan.submitBatch(list));

		} catch (SQLException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		} catch (ParseException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.db.exec.SqlMan#submitBatch(java.util.Collection)}.
	 */
	@Test
	public void testSubmitBatchCollectionOfString() {

		List<String> list = new ArrayList<String>();
		list.add("insert into test (intCityId) values (100)");
		list.add("insert into test (intCityId) values (101)");
		list.add("insert into test (intCityId) values (102)");

		try {
			if (!sqlMan.isConnected())
				return;
			sqlMan.initBatch();
			System.out.println(sqlMan.submitBatch(list));
		} catch (SQLException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.db.exec.SqlMan#executeQueryReadOnly(java.lang.String)}.
	 */
	@Test
	public void testExecuteQueryReadOnlyString() {
		try {
			if (!sqlMan.isConnected())
				return;
			ResultSet set = sqlMan.executeQueryReadOnly("select * from test");
			if (null != set) {
				while (set.next()) {
					System.out.println(set.getInt(1));
				}
			}
		} catch (SQLException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.db.exec.SqlMan#executeQuery(java.lang.String)}.
	 */
	@Test
	public void testExecuteQueryString() {
		try {
			if (!sqlMan.isConnected())
				return;
			ResultSet set = sqlMan.executeQuery("select * from test");
			if (null != set) {
				while (set.next()) {
					System.out.println(set.getInt(1));
				}
			}
		} catch (SQLException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 15, 2012 5:25:38 PM
	 */
	@Test
	public void testExecuteLoadTable() {
		try {
			if (!sqlMan.isConnected())
				return;
			PreparedStatement pstmt = null;
			createTable(pstmt);

			pstmt = null;
			queryDataToFile(pstmt);

			pstmt = null;
			loadDataFromFile(pstmt);

		} catch (SQLException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * <br>
	 * Created on: Dec 15, 2012 5:41:18 PM
	 * 
	 * @param pstmt
	 * @throws SQLException
	 */
	private void loadDataFromFile(PreparedStatement pstmt) throws SQLException {

		/**
		 * load table tbStrategy_FullGn_30474011 (
		 * dtBtime,vcMsisdn,vcImsi,vcImei
		 * ,vcUrl,vcHost,vcIp,vcSgsnIp,vcGgsnIp,vcCgi,vcApn,nmDimensId ) from
		 * 'E:\workspace_foshan\gnNew\4_30474011_CMNET_19760518_092114.txt'
		 * delimited by '\t' escapes off quotes off with checkpoint on; commit;
		 */

		StringBuffer option = new StringBuffer();
		option.append("load table tbStrategy_FullGn_30474011 ( dtBtime ,vcMsisdn,vcImsi,");
		option.append("vcImei,vcUrl,vcHost,vcIp,vcSgsnIp,vcGgsnIp,vcCgi,vcApn,nmDimensId ) ");
		option.append("from 'E:\\workspace_foshan\\gnNew\\4_30474011_CMNET_19760518_092114.txt' ");
		option.append("delimited by '\t' escapes off quotes off with checkpoint on; commit;");

		// option.append("LOAD TABLE wzz_test (userName,dtBtime,userPassword,intBscId) ");
		// option.append("from 'E:\\workspace_util\\w\\etc\\test' ");
		// option.append("delimited by '\t' ");
		// option.append("escapes off quotes off with checkpoint on; commit;");

		pstmt = sqlMan.prepareStatement(option.toString());
		pstmt.execute();
	}

	/**
	 * <br>
	 * Created on: Dec 15, 2012 5:39:44 PM
	 * 
	 * @param pstmt
	 * @throws SQLException
	 */
	private void queryDataToFile(PreparedStatement pstmt) throws SQLException {
		// userName,dtBtime,userPassword,intBscId
		StringBuffer option = new StringBuffer();
		option.append("set temporary option TEMP_EXTRACT_NAME1='E:\\workspace_util\\w\\etc\\wzz_test.txt';");
		option.append("set temporary option TEMP_EXTRACT_COLUMN_DELIMITER='\t';");
		option.append("set temporary option TEMP_EXTRACT_BINARY='OFF';");
		option.append("set temporary option TEMP_EXTRACT_SWAP='OFF';");
		option.append("select userName,dtBtime,userPassword,intBscId from wzz_test");

		pstmt = sqlMan.prepareStatement(option.toString());
		pstmt.execute();
	}

	/**
	 * <br>
	 * Created on: Dec 15, 2012 5:38:59 PM
	 * 
	 * @param pstmt
	 * @throws SQLException
	 */
	private void createTable(PreparedStatement pstmt) throws SQLException {
		pstmt = sqlMan
				.prepareStatement("create table wzz_test ( userId numeric(10,0) not null default autoincrement, "
						+ "userName varchar(20) null, dtBtime datetime null,userPassword varchar(20) null, "
						+ "intBscId numeric(10,0) null,constraint PK_WZZ_TEST primary key (userId) );");
		pstmt.executeUpdate();
	}
}
