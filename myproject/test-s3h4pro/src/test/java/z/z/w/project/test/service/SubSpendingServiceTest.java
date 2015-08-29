/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.SubSpendingServiceTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-8 下午09:35:56 
 *   LastChange: 2013-11-8 下午09:35:56 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import z.z.w.project.test.SuperClass;
import z.z.w.project.test.model.TbReveExpen;
import z.z.w.project.test.model.TbSpending;
import z.z.w.project.test.model.TbSubSpending;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.service.SubSpendingServiceTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:etc/spring.xml" })
public class SubSpendingServiceTest extends SuperClass {

	private SubSpendingService subSpendingService;

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.SubSpendingService#addSubSpending(z.z.w.project.test.model.TbSubSpending)}
	 * .
	 */
	@Test
	public void testAddSubSpending() {

		TbReveExpen tr = new TbReveExpen();
		tr.setVcNote("支出");

		setOther(tr);

		setYi(tr);

		TbSpending ts = new TbSpending();
		ts.setVcNote("食");
		ts.setTbReveExpen(tr);
		TbSubSpending tss = new TbSubSpending();
		tss.setTbSpending(ts);
		tss.setVcNote("其他");
		subSpendingService.addSubSpending(tss);

		ts = new TbSpending();
		ts.setVcNote("住");
		ts.setTbReveExpen(tr);
		tss = new TbSubSpending();
		tss.setTbSpending(ts);
		tss.setVcNote("其他");
		subSpendingService.addSubSpending(tss);

		ts = new TbSpending();
		ts.setVcNote("行");
		ts.setTbReveExpen(tr);
		tss = new TbSubSpending();
		tss.setTbSpending(ts);
		tss.setVcNote("其他");
		subSpendingService.addSubSpending(tss);

		ts = new TbSpending();
		ts.setVcNote("學習");
		ts.setTbReveExpen(tr);
		tss = new TbSubSpending();
		tss.setTbSpending(ts);
		tss.setVcNote("其他");
		subSpendingService.addSubSpending(tss);

		ts = new TbSpending();
		ts.setVcNote("交際");
		ts.setTbReveExpen(tr);
		tss = new TbSubSpending();
		tss.setTbSpending(ts);
		tss.setVcNote("其他");
		subSpendingService.addSubSpending(tss);

	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:27:19
	 * 
	 * @param tr
	 */
	private void setYi(TbReveExpen tr) {
		TbSpending ts = new TbSpending();
		ts.setVcNote("衣");
		ts.setTbReveExpen(tr);
		TbSubSpending tss = new TbSubSpending();
		tss.setVcNote("其他");
		tss.setTbSpending(ts);
		subSpendingService.addSubSpending(tss);

		tss = new TbSubSpending();
		tss.setTbSpending(ts);
		tss.setVcNote("服飾");
		subSpendingService.addSubSpending(tss);

		tss = new TbSubSpending();
		tss.setTbSpending(ts);
		tss.setVcNote("理髮");
		subSpendingService.addSubSpending(tss);

	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:24:25
	 * 
	 * @param tr
	 */
	private void setOther(TbReveExpen tr) {
		TbSpending ts = new TbSpending();
		ts.setVcNote("其他");
		ts.setTbReveExpen(tr);

		TbSubSpending tss = new TbSubSpending();
		tss.setVcNote("其他");
		tss.setTbSpending(ts);
		subSpendingService.addSubSpending(tss);
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.SubSpendingService#get(java.lang.Integer)}
	 * .
	 */
	@Test
	public void testGet() {

		TbSubSpending tss = subSpendingService.get(1L);
		if (!DataTools.isEmpty(tss)) {
			LogTools.getLogger(getClass()).info("{}", tss.getNmSubSpendId());
			LogTools.getLogger(getClass()).info("{}", tss.getVcNote());
			TbSpending ts = tss.getTbSpending();
			if (DataTools.isEmpty(ts))
				return;
			LogTools.getLogger(getClass()).info(
					"============================================");
			LogTools.getLogger(getClass()).info("{}", ts.getNmSpendId());
			LogTools.getLogger(getClass()).info("{}", ts.getVcNote());
		}
	}

	@Test
	public void testDelete() {

		TbSubSpending tss = subSpendingService.get(2L);
		if (!DataTools.isEmpty(tss)) {
			// 只有至空，才不會刪除多的一方數據的時候，級聯的把一的一方也刪除，這樣是不對的。
			// 或是直接使用HQL,SQL語句刪除一條多的一方的數據
			tss.setTbSpending(null);
			subSpendingService.delete(tss);
		}
	}

	@Test
	public void testUpdate() {
		TbSubSpending tss = subSpendingService.get(6L);
		if (!DataTools.isEmpty(tss)) {
			tss.setVcNote("TEST -== NOTE");
			tss.getTbSpending().setVcNote("+++");
			subSpendingService.update(tss);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:36:40
	 * 
	 * @return the subSpendingService
	 */
	public SubSpendingService getSubSpendingService() {
		return subSpendingService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:36:40
	 * 
	 * @param subSpendingService
	 *            the subSpendingService to set
	 */
	@Resource
	public void setSubSpendingService(SubSpendingService subSpendingService) {
		this.subSpendingService = subSpendingService;
	}

}
