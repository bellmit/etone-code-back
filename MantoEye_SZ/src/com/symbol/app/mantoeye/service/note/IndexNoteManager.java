package com.symbol.app.mantoeye.service.note;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.note.IndexNoteDao;
import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.app.mantoeye.dto.note.IndexNote;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@Transactional
@Service
public class IndexNoteManager {
	private IndexNoteDao indexNoteDao;

	public IndexNoteDao getIndexNoteDao() {
		return indexNoteDao;
	}

	@Resource
	public void setIndexNoteDao(IndexNoteDao indexNoteDao) {
		this.indexNoteDao = indexNoteDao;
	}

	public void add(IndexNote in) {
		indexNoteDao.insert(in);

	}

	public void delete(String ids) {
		indexNoteDao.delete(ids);
	}

	public void edit(IndexNote in) {
		indexNoteDao.edit(in);
	}

	public Page<IndexNote> query(GridServerHandler gridServerHandler) {
		Page<IndexNote> page = new Page<IndexNote>(20);
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		page.setTotalCount(indexNoteDao.getTotalCount());

		SortInfo si = gridServerHandler.getSingleSortInfo();

		if (null != si) {
			String fieldName = si.getFieldName();
			String sortAttr = si.getSortOrder();
			page.setOrder(sortAttr);
			page.setOrderBy(fieldName);
		} else {
			page.setOrder("desc");
			page.setOrderBy("dtDate");
		}

		List<IndexNote> list = indexNoteDao.query(page);

		page.setResult(list);

		return page;
	}

	public IndexNote findById(String id) {
		IndexNote in = indexNoteDao.findById(id);
		return in;
	}

	public List<IndexNote> export() {
		List<IndexNote> list = indexNoteDao.list();
		return list;
	}

}
