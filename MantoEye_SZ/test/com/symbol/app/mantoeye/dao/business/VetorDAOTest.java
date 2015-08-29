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

import com.symbol.app.mantoeye.entity.business.FtbMainAssistVetor;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class VetorDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;

	private VetorDAO vetorDAO;

	@Test
	public void testFindForQuery() {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();

		// final String propertyName, final Object value, final MatchType
		// matchType
		PropertyFilter pf = new PropertyFilter();
		pf.setMatchType(MatchType.LIKE);
		pf.setPropertyName("test.vcApn");
		pf.setValue("test");
		PropertyFilter pf2 = new PropertyFilter();
		pf2.setMatchType(MatchType.LIKE);
		pf2.setPropertyName("fsd.test.vcUserAgent");
		pf2.setValue("test");
		PropertyFilter pf3 = new PropertyFilter();
		pf3.setMatchType(MatchType.LIKE);
		pf3.setPropertyName("fdsal.test.serverIp");
		pf3.setValue("test");
		PropertyFilter pf4 = new PropertyFilter();
		pf4.setMatchType(MatchType.LIKE);
		pf4.setPropertyName("test.vcUrl");
		pf4.setValue("test");
		PropertyFilter pf5 = new PropertyFilter();
		pf5.setMatchType(MatchType.LIKE);
		pf5.setPropertyName("test.businessName");
		pf5.setValue("test");
		filters.add(pf);
		filters.add(pf2);
		filters.add(pf3);
		filters.add(pf4);
		filters.add(pf5);

		Page<FtbMainAssistVetor> page = new Page<FtbMainAssistVetor>();

		Page<FtbMainAssistVetor> npage = vetorDAO.findForQuery(page, filters);

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

	public VetorDAO getVetorDAO() {
		return vetorDAO;
	}

	@Resource
	public void setVetorDAO(VetorDAO vetorDAO) {
		this.vetorDAO = vetorDAO;
	}

}
