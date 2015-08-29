/**************************************************************************
 * <pre>
 *     FileName: test.test.db.BonecpDbTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-9 下午04:01:06 
 *   LastChange: 2013-9-9 下午04:01:06 
 *      History:
 * </pre>
 **************************************************************************/
package test.test.db;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import z.z.w.log.LogUtil;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

/**
 * test.test.db.BonecpDbTest.java
 */
public class BonecpDbTest {
	@Before
	public void before() {
		LogUtil.setLogBackDomConfig("etc/logback.xml");
	}

	@Test
	public void testBonecpJDBC() {

		/**
		 * <pre>
		 * <?xml version="1.0" encoding="UTF-8"?>
		 * <bonecp-config>  
		 *   <default-config>  
		 *     <property name="jdbcUrl">jdbc:oracle:thin:@127.0.0.1:1521:orcl</property>  
		 *     <property name="username">scott</property>  
		 *     <property name="password">tiger</property>  
		 *     <property name="partitionCount">3</property>  
		 *     <property name="maxConnectionsPerPartition">30</property>  
		 *     <property name="minConnectionsPerPartition">10</property>  
		 *     <property name="acquireIncrement">3</property>  
		 *     
		 *     
		 *       <prop key="bonecp.idleMaxAge">240</prop> 
		 *             <prop key="bonecp.idleConnectionTestPeriod">60</prop> 
		 *             <prop key="bonecp.partitionCount">3</prop> 
		 *             <prop key="bonecp.acquireIncrement">10</prop> 
		 *             <prop key="bonecp.maxConnectionsPerPartition">60</prop> 
		 *             <prop key="bonecp.minConnectionsPerPartition">20</prop> 
		 *             <prop key="bonecp.statementsCacheSize">50</prop> 
		 *             <prop key="bonecp.releaseHelperThreads">3</prop> 
		 *   </default-config>   
		 * </bonecp-config>
		 * </pre>
		 */

		try {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver"); // load the DB
																// driver
			BoneCPConfig config = new BoneCPConfig(); // create a new
														// configuration
														// object
			config.setJdbcUrl("jdbc:sybase:Tds:192.168.8.239:2688/asiqssmp");
			config.setUsername("DBA"); // set the username
			config.setPassword("sql"); // set the password
			// 设置每60秒检查数据库中的空闲连接数
			config.setIdleConnectionTestPeriodInMinutes(1);
			// 设置连接空闲时间
			config.setIdleMaxAge(240, TimeUnit.MINUTES);
			// 设置每个分区中的最大连接数 30
			config.setMaxConnectionsPerPartition(6);
			// 设置每个分区中的最小连接数 10
			config.setMinConnectionsPerPartition(3);
			// 当连接池中的连接耗尽的时候 BoneCP一次同时获取的连接数
			config.setAcquireIncrement(3);
			// 连接释放处理
			config.setReleaseHelperThreads(3);
			// 设置分区 分区数为3
			config.setPartitionCount(3);
			// 设置配置参数
			BoneCP connectionPool = new BoneCP(config); // setup the
														// connection
			Connection connection = null;
			// 创建100个连接
			for (int i = 0; i < 10; i++) {
				long startTime = System.currentTimeMillis();
				try {
					connection = connectionPool.getConnection();

					if (connection != null) {
						System.out.println("Connection successful!");
						Statement stmt = connection.createStatement();
						// do something with the connection.
						ResultSet rs = stmt
								.executeQuery(" select * from tbServiceRule_2013090915");

						while (rs.next()) {
							System.out.println(rs.getString(2));
						}
					}
					connection.close(); // close the connection
				} catch (SQLException e) {
					e.printStackTrace();
					connection.close();
				} // fetch a
				long endtTime = System.currentTimeMillis();

				System.out.println("-------->total seconds :"
						+ (endtTime - startTime));
			}

			connectionPool.close();
			connectionPool.shutdown(); // shutdown connection pool.
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	@Ignore
	@Test
	public void testBonecpDataSource() {

		try {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver"); // load the DB
			BoneCPDataSource dataSource = new BoneCPDataSource();
			dataSource.setUsername("DBA");
			dataSource.setPassword("sql");
			dataSource
					.setJdbcUrl("jdbc:sybase:Tds:192.168.8.239:2688/asiqssmp");
			dataSource.setMaxConnectionsPerPartition(10);
			dataSource.setMinConnectionsPerPartition(5);
			dataSource.setIdleConnectionTestPeriodInMinutes(1);
			dataSource.setIdleMaxAge(240, TimeUnit.MINUTES);
			dataSource.setAcquireIncrement(5);
			dataSource.setReleaseHelperThreads(3);
			// pool
			Connection connection;
			connection = dataSource.getConnection();

			if (connection != null) {
				System.out.println("Connection successful!");
				Statement stmt = connection.createStatement();
				// do something with the connection.
				ResultSet rs = stmt
						.executeQuery(" select * from tbServiceRule_2013090915");

				while (rs.next()) {
					System.out.println(rs.getString(2));
					System.out.println(rs.getString(3));
				}
			}
			connection.close(); // close the connection
			dataSource.close();
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
