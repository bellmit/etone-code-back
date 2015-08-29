/**
 * z.z.w.quartz.upgrade.job.Moniter.java
 */
package z.z.w.quartz.upgrade.job;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Node;

import z.z.w.common.FileTools;
import z.z.w.common.ThreadTools;
import z.z.w.common.XmlTools;
import z.z.w.env.Config;
import z.z.w.quartz.upgrade.ServerTest;

/**
 * @author Wu Zhenzhen
 * @version Dec 24, 2012 11:56:47 AM
 */
public class Moniter implements Runnable {
	public static boolean isStop = false;
	private Config conf = new Config();
	private static ServerTest test = null;

	private static final List<Runnable> runningThreadList = new ArrayList<Runnable>();

	public static void add(Runnable r) {
		synchronized (runningThreadList) {
			runningThreadList.add(r);
		}
	}

	public static void remove(Runnable r) {
		synchronized (runningThreadList) {
			runningThreadList.remove(r);
		}
	}

	public static int size() {
		// synchronized (runningThreadList) {
		return runningThreadList.size();
		// }
	}

	private void open() {
		try {
			XmlTools.openXml(FileTools.replaceProHome(conf
					.getString("xml-file")));
		} catch (DocumentException e) {

		}
	}

	/**
	 * <br>
	 * Created on: Dec 9, 2012 5:22:07 PM
	 */
	private void closeXml() {
		XmlTools.closeXml();
	}

	/**
	 * <br>
	 * Created on: Dec 24, 2012 11:56:47 AM
	 */
	public Moniter() {
		conf.setRootKey("common.util");
	}

	/**
	 * <br>
	 * Created on: Dec 23, 2012 10:21:52 PM
	 * 
	 * @param test
	 */
	public static void setTest(ServerTest test) {
		Moniter.test = test;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		boolean isStoped = false;

		while (true) {

			// System.out.println(" BEFORE -- list size : " + size());

			open();
			Node node = XmlTools.getSingleNode("//server");

			isStop = ((XmlTools.getSingleAttrValue(node, "stop")
					.equalsIgnoreCase("1")) ? true : false);
			closeXml();

			// System.out.println("isStop -->" + isStop + " -- list size : "
			// + size());

			if (isStop && !isStoped) {
				// System.out.println(DateTools.getCurrentDateStr()
				// + " --- stop quartz thread ---"
				// + Thread.currentThread().getName() + " -- list size : "
				// + size());
				// // break;
				test.stop();

				isStoped = true;

			}
			// System.out.println(" -- list size : " + size());

			// update quartz config
			updateQuartzConf();

			if (isStop && (Moniter.size() == 0)) {
				// System.out.println(DateTools.getCurrentDateStr()
				// + " --- stop Moniter thread---"
				// + Thread.currentThread().getName());
				break;
			}

			// System.out.println("----------------------------------"
			// + " -- list size : " + size());

			ThreadTools.sleepSeconds(7);
		}

		// System.out
		// .println("&&&&&&&&&&&&&MONITER break while(true)&&&&&&&&&&&&&&&&&&&&&");
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 8:42:02 PM
	 */
	private void updateQuartzConf() {
		System.out.println();
		ThreadTools.sleepSeconds(11);
		System.out.println("--------------------------------------");
		// try {
		// List<String> list = test.getJobGroupNames();
		//
		// for (String job : list) {
		// System.out.println("*****Job detail name :" + job + "--"
		// + DateTools.getCurrentDateStr());
		// if (job.equals("JobTest_GROUP")) {
		// // scheduler.rescheduleJob(triggerName, jobGroups[i],
		// // trigger);
		// System.out.println("--change---");
		// // try {
		// // test.callReSchedulerJob("JobTest", "0/7 * * * * ?");
		// // } catch (ParseException e) {
		// // // TODO Auto-generated catch block
		// // }
		//
		// test.callReSchedulerJob("JobTest", 1);
		// }
		// }
		// } catch (SchedulerException e) {
		// e.printStackTrace();
		// }
		System.out.println("--------------------------------------");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}
}
