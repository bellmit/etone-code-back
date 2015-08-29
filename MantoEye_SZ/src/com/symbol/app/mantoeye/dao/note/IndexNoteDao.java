package com.symbol.app.mantoeye.dao.note;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.note.IndexNote;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class IndexNoteDao extends HibernateDao<IndexNote, Integer> {

	@SuppressWarnings("unchecked")
	public List<IndexNote> query(Page<IndexNote> page) {

		String sql = " select nmNoteId,vcTitle,convert(varchar(10),string(dtDate)) dtDate,vcContent from tbNote ";

		sql += " order by " + page.getOrderBy() + " " + page.getOrder();

		List<IndexNote> list = this.getSession().createSQLQuery(sql).addScalar(
				"nmNoteId", Hibernate.INTEGER).addScalar("vcTitle",
				Hibernate.STRING).addScalar("dtDate", Hibernate.STRING)
				.addScalar("vcContent", Hibernate.STRING).setResultTransformer(
						Transformers.aliasToBean(IndexNote.class))
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<IndexNote> list() {

		String sql = " select nmNoteId,vcTitle,convert(varchar(10),string(dtDate)) dtDate,vcContent from tbNote ";

		sql += " order by dtDate desc";

		List<IndexNote> list = this.getSession().createSQLQuery(sql).addScalar(
				"nmNoteId", Hibernate.INTEGER).addScalar("vcTitle",
				Hibernate.STRING).addScalar("dtDate", Hibernate.STRING)
				.addScalar("vcContent", Hibernate.STRING).setResultTransformer(
						Transformers.aliasToBean(IndexNote.class)).list();

		return list;
	}

	public Integer getTotalCount() {
		String sql = " select count(1) from tbNote ";
		Object count = this.getSession().createSQLQuery(sql).uniqueResult();
		return Integer
				.parseInt(null == count || "".equals(count.toString()) ? "0"
						: count.toString());
	}

	public void insert(IndexNote in) {
		Date date = new Date();
		String currentDate = CommonUtils.formatFullDate(date);
		String sql = " insert into tbNote(vcTitle,dtDate,vcContent) values('"
				+ in.getVcTitle() + "','" + currentDate + "','"
				+ in.getVcContent() + "')";

		this.getSession().createSQLQuery(sql).executeUpdate();

	}

	public void delete(String ids) {
		String sql = "delete from tbNote where nmNoteId in (" + ids + ")";

		this.getSession().createSQLQuery(sql).executeUpdate();

	}

	public void edit(IndexNote in) {
		Date date = new Date();
		String currentDate = CommonUtils.formatFullDate(date);
		String sql = " update tbNote set vcContent='" + in.getVcContent()
				+ "',dtDate='" + currentDate + "',vcTitle='" + in.getVcTitle()
				+ "' where nmNoteId=" + in.getNmNoteId();
		this.getSession().createSQLQuery(sql).executeUpdate();
	}

	public IndexNote findById(String id) {
		String sql = " select nmNoteId,vcTitle,convert(varchar(10),"
				+ "string(dtDate)) dtDate,vcContent from tbNote where nmNoteId="
				+ id;
		IndexNote in = (IndexNote) this.getSession().createSQLQuery(sql)
				.addScalar("nmNoteId", Hibernate.INTEGER).addScalar("vcTitle",
						Hibernate.STRING).addScalar("dtDate", Hibernate.STRING)
				.addScalar("vcContent", Hibernate.STRING).setResultTransformer(
						Transformers.aliasToBean(IndexNote.class))
				.uniqueResult();

		return in;
	}
}
