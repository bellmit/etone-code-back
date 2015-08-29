package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
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

import com.symbol.app.mantoeye.dto.business.FtbMainAssistVetorNew;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "dataSourceTransactionManager", defaultRollback = false)
@Transactional
public class NextVetorDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;

	private NextVetorDao nextVetorDao;

	@Test
	public void testFindForQuery() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();

		// final String propertyName, final Object value, final MatchType
		// matchType
		PropertyFilter pf = new PropertyFilter();
		pf.setMatchType(MatchType.LIKE);
		pf.setPropertyName("test.vcApn");
		pf.setValue("test");
		// filters.add(pf);

		Page<FtbMainAssistVetorNew> page = new Page<FtbMainAssistVetorNew>();

		Page<FtbMainAssistVetorNew> npage = nextVetorDao.findForQuery(page,
				filters);

		Assert.assertThat(npage, Matchers.notNullValue());
		Assert.assertThat(npage.getResult(), Matchers.notNullValue());
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public NextVetorDao getNextVetorDao() {
		return nextVetorDao;
	}

	@Resource
	public void setNextVetorDao(NextVetorDao nextVetorDao) {
		this.nextVetorDao = nextVetorDao;
	}

}
