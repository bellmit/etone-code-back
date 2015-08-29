package com.symbol.app.mantoeye.dao.note;

import java.util.List;

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

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class IndexNoteDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	private DataSource dataSource;
	private IndexNoteDao indexNoteDao;

	@Test
	public void testQuery() {
		Page<IndexNote> page = new Page<IndexNote>(20);

		List<IndexNote> list = indexNoteDao.query(page);

		Assert.assertThat(list, Matchers.notNullValue());

		for (IndexNote in : list) {
			System.out.println(in.getDtDate());
			System.out.println(in.getVcContent());
			System.out.println(in.getVcTitle());
			System.out.println(in.getNmNoteId());
			System.out.println("----------------------");
		}

	}

	@Test
	public void testTotalCount() {
		Integer count = indexNoteDao.getTotalCount();
		Assert.assertThat(count, Matchers.is(2));
	}

	// add
	@Test
	public void testInsert() {
		String vcTitle = "MantoEye v3.0版本发布";
		String vcContent = "MantoEye v3.0版本发布MantoEye v3.0版本发布";

		IndexNote in = new IndexNote();
		in.setVcContent(vcContent);
		in.setVcTitle(vcTitle);
		indexNoteDao.insert(in);
	}

	// del
	@Test
	public void testDelete() {
		String ids = "3,4";
		indexNoteDao.delete(ids);
	}

	// edit
	@Test
	public void testEdit() {
		int id = 4;
		String vcTitle = "MantoEye v3.0版本发布";
		String vcContent = "MantoEye v3.0版本发布MantoEye v3.0版本发布";
		IndexNote in = new IndexNote();
		in.setVcContent(vcContent);
		in.setVcTitle(vcTitle);
		in.setNmNoteId(id);
		indexNoteDao.edit(in);
	}

	@Test
	public void testFindById() {
		String id = "4";
		IndexNote in = indexNoteDao.findById(id);
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

	public IndexNoteDao getIndexNoteDao() {
		return indexNoteDao;
	}

	@Resource
	public void setIndexNoteDao(IndexNoteDao indexNoteDao) {
		this.indexNoteDao = indexNoteDao;
	}

}
