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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import z.z.w.project.test.SuperClass;
import z.z.w.project.test.model.TbResourceRote;
import z.z.w.project.test.model.TbReveExpen;
import z.z.w.project.test.model.TbSpending;
import z.z.w.project.test.model.TbSubSpending;
import z.z.w.project.test.model.VeiwRSS;
import z.z.w.project.util.common.DataTools;

/**
 * z.z.w.project.test.service.SpendingServiceTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:etc/spring.xml" })
public class AllTest extends SuperClass {
	private ResourceRoteService resourceRoteService;
	private ReveExpenService reveExpenService;
	private SpendingService spendingService;

	private SubSpendingService subSpendingService;

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:56:11
	 * 
	 * @return
	 */
	private List<VeiwRSS> getList() {

		List<VeiwRSS> list = new ArrayList<VeiwRSS>();
		VeiwRSS v = new VeiwRSS();
		v.setVcRNote("其他");
		v.setVcSNote("其他");
		v.setVcSSNote("其他");
		list.add(v);

		v = new VeiwRSS();
		v.setVcRNote("支出");
		v.setVcSNote("其他");
		v.setVcSSNote("其他");
		list.add(v);

		v = new VeiwRSS();
		v.setVcRNote("支出");
		v.setVcSNote("衣");
		v.setVcSSNote("其他");
		list.add(v);

		v = new VeiwRSS();
		v.setVcRNote("支出");
		v.setVcSNote("衣");
		v.setVcSSNote("服飾");
		list.add(v);

		v = new VeiwRSS();
		v.setVcRNote("支出");
		v.setVcSNote("食");
		v.setVcSSNote("其他");
		list.add(v);

		// v = new VeiwRSS();
		// v.setVcRNote("收入");
		// v.setVcSNote("其他");
		// list.add(v);
		//
		// v = new VeiwRSS();
		// v.setVcRNote("收入");
		// v.setVcSNote("工資");
		// list.add(v);

		return list;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:32:18
	 * 
	 * @return the resourceRoteService
	 */
	public ResourceRoteService getResourceRoteService() {
		return resourceRoteService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:40:56
	 * 
	 * @return the reveExpenService
	 */
	public ReveExpenService getReveExpenService() {
		return reveExpenService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:32:53
	 * 
	 * @return the spendingService
	 */
	public SpendingService getSpendingService() {
		return spendingService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:32:53
	 * 
	 * @return the subSpendingService
	 */
	public SubSpendingService getSubSpendingService() {
		return subSpendingService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午11:32:18
	 * 
	 * @param resourceRoteService
	 *            the resourceRoteService to set
	 */
	@Resource
	public void setResourceRoteService(ResourceRoteService resourceRoteService) {
		this.resourceRoteService = resourceRoteService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:40:56
	 * 
	 * @param reveExpenService
	 *            the reveExpenService to set
	 */
	@Resource
	public void setReveExpenService(ReveExpenService reveExpenService) {
		this.reveExpenService = reveExpenService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:32:53
	 * 
	 * @param spendingService
	 *            the spendingService to set
	 */
	@Resource
	public void setSpendingService(SpendingService spendingService) {
		this.spendingService = spendingService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:32:53
	 * 
	 * @param subSpendingService
	 *            the subSpendingService to set
	 */
	@Resource
	public void setSubSpendingService(SubSpendingService subSpendingService) {
		this.subSpendingService = subSpendingService;
	}

	@Test
	public void testAddOthers() {

		VeiwRSS v = new VeiwRSS();
		v.setVcRNote("收入");
		v.setVcSNote("其他");

		String r = v.getVcRNote();
		String s = v.getVcSNote();
		String ss = v.getVcSSNote();

		TbSubSpending tss = subSpendingService.getByJoin(v);

		if (DataTools.isEmpty(tss)) {
			tss = new TbSubSpending();
			tss.setVcNote(ss);

			TbSpending ts = spendingService.getByJoin(v);
			if (DataTools.isEmpty(ts)) {
				ts = new TbSpending();
				ts.setVcNote(s);
			}

			tss.setTbSpending(ts);

			TbReveExpen tr = reveExpenService.getByNote(r);
			if (DataTools.isEmpty(tr)) {
				tr = new TbReveExpen();
				tr.setVcNote(r);
			}

			ts.setTbReveExpen(tr);

			if (!DataTools.isEmpty(ss))
				subSpendingService.save(tss);
			else
				spendingService.save(ts);

		}

	}

	@Test
	public void testAll() {

		List<VeiwRSS> list = getList();

		// 支出
		for (VeiwRSS v : list) {

			String r = v.getVcRNote();
			String s = v.getVcSNote();
			String ss = v.getVcSSNote();

			TbSubSpending tss = subSpendingService.getByJoin(v);

			if (DataTools.isEmpty(tss)) {
				tss = new TbSubSpending();
				tss.setVcNote(ss);

				TbSpending ts = spendingService.getByJoin(v);
				if (DataTools.isEmpty(ts)) {
					ts = new TbSpending();
					ts.setVcNote(s);
				}

				tss.setTbSpending(ts);

				TbReveExpen tr = reveExpenService.getByNote(r);
				if (DataTools.isEmpty(tr)) {
					tr = new TbReveExpen();
					tr.setVcNote(r);
				}

				ts.setTbReveExpen(tr);

				if (!DataTools.isEmpty(ss))
					subSpendingService.save(tss);
				else
					spendingService.save(ts);

			}

		}

	}

	@Test
	public void testCreateTable() {

	}

	@Test
	public void testAddResourceRote() {

		TbResourceRote rote = new TbResourceRote();
		rote.setVcNote("收支列表");

		TbResourceRote rote2 = new TbResourceRote();
		rote2.setVcNote("系統管理");

		TbResourceRote rote3 = new TbResourceRote();
		rote3.setVcNote("賬單");
		rote3.setTbResourceRote(rote);

		resourceRoteService.addResourceRote(rote3);

		TbResourceRote rote4 = new TbResourceRote();
		rote4.setVcNote("收支項管理");
		rote4.setTbResourceRote(rote2);
		resourceRoteService.addResourceRote(rote4);

		TbResourceRote rote5 = new TbResourceRote();
		rote5.setVcNote("錢包管理");
		rote5.setTbResourceRote(rote2);
		resourceRoteService.addResourceRote(rote5);

	}

}
