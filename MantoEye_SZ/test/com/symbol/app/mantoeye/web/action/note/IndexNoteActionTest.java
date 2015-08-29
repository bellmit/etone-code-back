package com.symbol.app.mantoeye.web.action.note;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class IndexNoteActionTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	private DataSource dataSource;
	private IndexNoteAction indexNoteAction;

	// add
	@Test
	public void testAdd() {
		String res = indexNoteAction.add();
		Assert.assertThat(res, Matchers.is("success"));
	}

	// edit
	@Test
	public void testEdit() {
		String res = indexNoteAction.edit();
		Assert.assertThat(res, Matchers.is("success"));
	}

	// del
	@Test
	public void testDelete() {
		indexNoteAction.delete();
	}

	// query
	@Test
	public void testQuery() {
		indexNoteAction.query();
	}

	// findbyid
	@Test
	public void testFindById() {
		indexNoteAction.findById();
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public IndexNoteAction getIndexNoteAction() {
		return indexNoteAction;
	}

	@Resource
	public void setIndexNoteAction(IndexNoteAction indexNoteAction) {
		this.indexNoteAction = indexNoteAction;
	}

}
