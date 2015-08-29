/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.writefile.TestWriterFile.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-5 上午09:50:04 
 *   LastChange: 2013-9-5 上午09:50:04 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.writefile;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import z.z.w.common.DataTools;
import z.z.w.common.DateTools;
import z.z.w.common.ThreadTools;
import z.z.w.project.test.writefile.config.TestWriterFileConfig;
import z.z.w.project.test.writefile.vo.FileWritor;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.writefile.TestWriterFile.java
 */
public final class TestWriterFile implements Runnable {

	/** Current server class simple name : server name */
	private final static String SERVER_NAME = TestWriterFile.class.getName();

	/** Server config */
	private final static String XML_CONF_NAME = "//server/adapter/type[@class='"
			+ SERVER_NAME + "']";

	private WriterOperator writerOpertor = null;

	private FileWritor fileWritor = null;

	private final static char[] CHARS = { '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };

	protected char fieldSplit = ((char) 44);
	protected char rowSplit = ((char) 13);

	/**
	 * <br>
	 * Created on: 2013-9-5 上午09:50:04
	 */
	public TestWriterFile() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:02:51
	 */
	private void init() {
		fileWritor = TestWriterFileConfig.getFileWritor(XML_CONF_NAME);
		if (DataTools.isEmpty(fileWritor)) {
			LogTools.getLogger(getClass()).warn(
					"File writor config is null, return.");
			return;
		}
		setSpilt();
		writerOpertor = new WriterOperator(fileWritor);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:38
	 */
	private void setSpilt() {
		String fs = fileWritor.getFieldSplit();
		String rs = fileWritor.getRowSplit();

		/**************************************************************/
		try {
			fieldSplit = DataTools.getCharByHexStr(fs);
		} catch (Exception e) {
			fieldSplit = 44;
		}
		try {
			rowSplit = DataTools.getCharByHexStr(rs);
		} catch (Exception e) {
			rowSplit = 10;
		}
		/**************************************************************/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (true) {
			try {

				if (DataTools.isEmpty(writerOpertor))
					continue;

				int intHour = DateTools.getHour();
				intHour = getIntHour(intHour);
				String finData = getFinData(intHour);

				String intNetType = getNetType();
				writerOpertor.setIntNetType(intNetType);
				writerOpertor.setFileKeyData(getFileKeyDate(intHour));
				writerOpertor.writeDataToFile(finData);

				if (intNetType.equals("1"))
					ThreadTools.sleep(2);

			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "TestWriterFile", "run()",
										e.getMessage(), e.getCause(),
										e.getClass() });
			} finally {
			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 下午02:27:58
	 * 
	 * @param intHour
	 * @return
	 */
	private int getIntHour(int intHour) {
		int min = DateTools.getMin();

		if (min < getRandomValue(6)) {
			if (intHour != 0)
				intHour = intHour - 1;
			else
				intHour = 23;
		}

		return intHour;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:46:00
	 * 
	 * @return
	 */
	private String getNetType() {
		return getRandomValue(2) + "";
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:36:54
	 * 
	 * @return
	 */
	private String getFileKeyDate(int intHour) {
		StringBuffer sb = new StringBuffer(10);
		sb.append(DateTools.getYear());
		sb.append(getValue(DateTools.getMonth() + ""));
		sb.append(getValue(DateTools.getDay() + ""));
		sb.append(getValue(intHour + ""));
		return sb.toString();
	}

	/**
	 * <br>
	 * Created on: 2013-8-27 下午03:20:23
	 * 
	 * @param value
	 * @return
	 */
	private String getValue(String value) {
		StringBuffer sb = new StringBuffer(2);
		if (value.length() != 2)
			sb.append("0");
		sb.append(value);
		return sb.toString();
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:29:28
	 * 
	 * @return
	 */
	private String getFinData(int intHour) {
		StringBuffer sb = new StringBuffer();
		// FILE_TITLE_2G =
		// "小时,IMSI,IMEI,LAC,CI,业务类型,服务器域名,端口,User_Agent_Main,时长(ms),流量(KB)";
		// FILE_TITLE_3G =
		// "小时,IMSI,IMEI,LAC,SAC,业务类型,服务器域名,端口,User_Agent_Main,时长(ms),流量(KB)";
		// hour
		sb.append(intHour).append(fieldSplit);

		/**
		 * IMSI共有15位，其结构如下： MCC+MNC+MIN MCC：Mobile Country
		 * Code，行動裝置國家代碼，共3位，中国为460; MNC:Mobile Network
		 * Code，移动设备网络代码，2位(欧洲标准)或3位(北美标准)，中国电信CDMA系统使用03，
		 * 一个典型的IMSI号码为460030912121001;
		 * 
		 * MIN共有10位，其结构如下： 09+M0M1M2M3+ABCD
		 * 其中的M0M1M2M3和MDN号码中的H0H1H2H3可存在对应关系，ABCD四位为自由分配。
		 */
		sb.append(4600309).append(RandomStringUtils.randomNumeric(8))
				.append(fieldSplit);

		/**
		 * IMEI序列号共有15位数字，前6位（TAC）是型號核准號碼，代表手機類型。接著2位（FAC）是最後裝配號，代表產地。 後6位（
		 * SNR）是串號，代表生產順序號。最後1位（SP）一般為0，是檢驗碼，備用。
		 */
		sb.append(RandomStringUtils.random(1, CHARS))
				.append(RandomStringUtils.randomNumeric(14)).append(fieldSplit);

		// LAC
		sb.append(getLac()).append(fieldSplit);

		// CI/SAC
		sb.append(getCIorSAC()).append(fieldSplit);

		// 业务类型
		sb.append(getBussid()).append(fieldSplit);

		// 服务器域名
		sb.append(getServerName()).append(fieldSplit);
		// 端口
		sb.append(80).append(fieldSplit);
		// User_Agent_Main
		sb.append(getUserAgent()).append(fieldSplit);
		// 时长(ms)
		sb.append(getOnlineTime()).append(fieldSplit);
		// 流量(KB)
		sb.append(getFlush()).append(fieldSplit).append(rowSplit);

		return sb.toString();
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:21:16
	 * 
	 * @return
	 */
	private String getUserAgent() {
		return getRandomStringValue(8);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:22:04
	 * 
	 * @param i
	 * @return
	 */
	private String getRandomStringValue(int count) {
		return RandomStringUtils.randomAlphanumeric(3) + "."
				+ RandomStringUtils.randomAlphanumeric(count - 2) + "."
				+ RandomStringUtils.randomAlphanumeric(count) + "."
				+ RandomStringUtils.randomAlphanumeric(3);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:21:13
	 * 
	 * @return
	 */
	private String getServerName() {
		return getRandomStringValue(3);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:20:37
	 * 
	 * @return
	 */
	private Object getOnlineTime() {
		return getRandomValue(999999999);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:20:19
	 * 
	 * @return
	 */
	private int getFlush() {
		return getRandomValue(99999999);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:19:04
	 * 
	 * @return
	 */
	private int getBussid() {
		return getRandomValue(99999);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:18:47
	 * 
	 * @return
	 */
	private int getCIorSAC() {
		return getRandomValue(5535);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:17:30
	 * 
	 * @return
	 */
	private int getLac() {
		return getRandomValue(65535);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:17:57
	 * 
	 * @param max
	 * @return
	 */
	private int getRandomValue(int max) {
		Random rand = new Random(DateTools.getCurrentDateToLong());
		// random.nextInt(max)%(max-min+1) + min;
		int temp = rand.nextInt(max) % (max - 1 + 1) + 1;
		return temp;
	}
}
