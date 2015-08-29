package com.symbol.app.mantoeye.service.note;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dto.note.IndexNote;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class IndexNoteManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	private DataSource dataSource;
	private IndexNoteManager indexNoteManager;

	// add
	@Test
	public void testAdd() {
		String vcTitle = "MantoEye v23.0版本发布";
		String vcContent = "MantoEye v3.0版本发布MantoEye v3.0版本发布";
		IndexNote in = new IndexNote();
		in.setVcContent(vcContent);
		in.setVcTitle(vcTitle);
		indexNoteManager.add(in);
	}

	// delete
	@Test
	public void testDelete() {
		String ids = "9";
		indexNoteManager.delete(ids);
	}

	// edit
	@Test
	public void testEdit() {
		int id = 5;
		String vcTitle = "MantoEye v34343.0版本发布";
		String vcContent = "MantoEye v3.0版本发布MantoEye v3.0版本发布";
		IndexNote in = new IndexNote();
		in.setVcContent(vcContent);
		in.setVcTitle(vcTitle);
		in.setNmNoteId(id);
		indexNoteManager.edit(in);
	}

	// query
	@Test
	public void testQuery() {
		GridServerHandler gridServerHandler = new GridServerHandler();
		Page<IndexNote> list = indexNoteManager.query(gridServerHandler);
		Assert.assertThat(list, Matchers.notNullValue());

		for (IndexNote in : list.getResult()) {
			System.out.println(in.getDtDate());
			System.out.println(in.getVcContent());
			System.out.println(in.getVcTitle());
			System.out.println(in.getNmNoteId());
			System.out.println("----------------------");
		}
	}

	// findbyid
	@Test
	public void testFindById() {
		String id = "34";
		IndexNote in = indexNoteManager.findById(id);
		Assert.assertThat(in, Matchers.notNullValue());
		System.out.println(in.getDtDate());
		System.out.println(in.getVcContent());
		System.out.println(in.getVcTitle());
		System.out.println(in.getNmNoteId());
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public IndexNoteManager getIndexNoteManager() {
		return indexNoteManager;
	}

	@Resource
	public void setIndexNoteManager(IndexNoteManager indexNoteManager) {
		this.indexNoteManager = indexNoteManager;
	}

}
