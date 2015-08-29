/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.SpendingServiceTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-9 下午02:24:58 
 *   LastChange: 2013-11-9 下午02:24:58 
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
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.service.SpendingServiceTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:etc/spring.xml" })
public class SpendingServiceTest extends SuperClass {

	private SpendingService spendingService;

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.SpendingService#addSpending(z.z.w.project.test.model.TbSpending)}
	 * .
	 */
	@Test
	public void testAddSpending() {
		setOther();

		setShouru();

	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:21:29
	 */
	private void setShouru() {
		TbReveExpen tr = new TbReveExpen();
		tr.setVcNote("收入");

		TbSpending ts = new TbSpending();
		ts.setVcNote("其他");
		ts.setTbReveExpen(tr);
		spendingService.addSpending(ts);

		ts = new TbSpending();
		ts.setVcNote("工資");
		ts.setTbReveExpen(tr);
		spendingService.addSpending(ts);

		ts = new TbSpending();
		ts.setVcNote("差旅報銷");
		ts.setTbReveExpen(tr);
		spendingService.addSpending(ts);

		ts = new TbSpending();
		ts.setVcNote("意外收入");
		ts.setTbReveExpen(tr);
		spendingService.addSpending(ts);
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:20:23
	 */
	private void setOther() {
		TbReveExpen tr = new TbReveExpen();
		tr.setVcNote("其他");

		TbSpending ts = new TbSpending();
		ts.setVcNote("其他");
		ts.setTbReveExpen(tr);
		spendingService.addSpending(ts);
	}

	@Test
	public void testGet() {

		TbSpending ts = spendingService.get(1L);
		if (!DataTools.isEmpty(ts)) {
			LogTools.getLogger(getClass()).info("{}", ts.getNmSpendId());
			LogTools.getLogger(getClass()).info("{}", ts.getVcNote());
			/**
			 * 
			 * EAGER的時候，才能取到set中的值。LAZY的時候不取值
			 * 
			 * <pre>
			 * OneToMany(mappedBy = tbSpending, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
			 * public Set&lt;TbSubSpending&gt; getTbSubSpendings() {
			 * 	return tbSubSpendings;
			 * }
			 * </pre>
			 */
			// Set<TbSubSpending> set = ts.getTbSubSpendings();
			// if (DataTools.isEmpty(set))
			// return;
			// LogTools.getLogger(getClass()).info(
			// "============================================");
			// for (TbSubSpending tss : set) {
			// LogTools.getLogger(getClass())
			// .info("{}", tss.getNmSubSpendId());
			// LogTools.getLogger(getClass()).info("{}", tss.getVcNote());
			// // LogTools.getLogger(getClass()).info("{}",
			// // tss.getTbSpending());
			// }
		}
	}

	@Test
	public void testDelete() {
		TbSpending ts = spendingService.get(5L);
		if (!DataTools.isEmpty(ts)) {
			spendingService.delete(ts);
		}
	}

	@Test
	public void testUpdate() {
		TbSpending ts = spendingService.get(6L);
		if (!DataTools.isEmpty(ts)) {
			ts.setVcNote("TEST NOTE");
			spendingService.update(ts);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午02:25:33
	 * 
	 * @return the spendingService
	 */
	public SpendingService getSpendingService() {
		return spendingService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午02:25:33
	 * 
	 * @param spendingService
	 *            the spendingService to set
	 */
	@Resource
	public void setSpendingService(SpendingService spendingService) {
		this.spendingService = spendingService;
	}

}
