package com.symbol.app.mantoeye.service.mms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.mms.SpPortControlDao;
import com.symbol.app.mantoeye.dto.mms.SpPortControlBean;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 彩信端口管理MANAGER
 * 
 * @author Jane Wu
 * 
 */
@Service
@Transactional(readOnly = false)
public class SpPortControlManager {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(SpPortControlManager.class);

	private SpPortControlDao spPortControlDao;

	private Page page = new Page();

	public SpPortControlDao getSpPortControlDao() {
		return spPortControlDao;
	}

	@Resource
	public void setSpPortControlDao(SpPortControlDao spPortControlDao) {
		this.spPortControlDao = spPortControlDao;
	}

	public void add(SpPortControlBean bean) {
		if (null != bean) {
			spPortControlDao.insert(bean);
		}
	}

	public void edit(SpPortControlBean bean) {
		if (null != bean) {
			spPortControlDao.update(bean);
		}
	}

	public void remove(Long spPortMapId) {

		if (null != spPortMapId) {
			spPortControlDao.delete(spPortMapId);
		}

	}

	public void remove(String spPort) {

		if (null != spPort) {
			spPortControlDao.delete(spPort);
		}

	}

	public List<SpPortControlBean> findByPage(
			GridServerHandler gridServerHandler, SpPortControlBean bean) {

		Map<String, String> paremeters = buildParemeters(bean);

		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page.setTotalCount(spPortControlDao.getTotalCount(paremeters));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}

		List<SpPortControlBean> list = spPortControlDao.selectByPage(page,
				paremeters);

		return list;
	}

	private Map<String, String> buildParemeters(SpPortControlBean bean) {
		Map<String, String> paremeters = new HashMap<String, String>();

		if (null != bean.getBeLong() && !"".equals(bean.getBeLong().trim())) {
			paremeters.put("vcBelong", bean.getBeLong().trim());
		}
		if (null != bean.getCompanyName()
				&& !"".equals(bean.getCompanyName().trim())) {
			paremeters.put("vcCompanyName", bean.getCompanyName().trim());
		}
		if (null != bean.getSpPort() && !"".equals(bean.getSpPort().trim())) {
			paremeters.put("nmSpPort", bean.getSpPort().trim());
		}

		return paremeters;
	}

	public List<SpPortControlBean> findAll(SpPortControlBean bean) {

		Map<String, String> paremeters = buildParemeters(bean);

		List<SpPortControlBean> list = spPortControlDao.selectAll(paremeters);

		return list;
	}

	/**
	 * 导入EXCEL文件操作
	 * 
	 * @param beanMap
	 */
	public void importElsFile(Map<String, SpPortControlBean> beanMap) {
		if (null != beanMap && !beanMap.isEmpty()) {
			Map<String, String> paremeters = null;
			List<SpPortControlBean> list = null;
			Set<String> keySet = beanMap.keySet();
			SpPortControlBean bean = null;
			for (String key : keySet) {
				bean = new SpPortControlBean();
				bean.setSpPort(key);
				// 检查上传的文件中的数据是否与DB中的重复
				paremeters = buildParemeters(bean);
				list = spPortControlDao.selectAllForImport(paremeters);

				if (null != list && !list.isEmpty()) {
					spPortControlDao.updateBySpPort(beanMap.get(key));
				} else {
					spPortControlDao.insert(beanMap.get(key));
				}
			}
		}
	}

	/**
	 * 将上传的XLS文件内容保存到MAP中
	 * 
	 * @return
	 */
	public Map<String, SpPortControlBean> loadXlsFile(File file) {
		Map<String, SpPortControlBean> map = new HashMap<String, SpPortControlBean>();
		Workbook rwb = null;
		try {
			InputStream is = new FileInputStream(file);
			rwb = Workbook.getWorkbook(is);

			Sheet[] sheetArr = rwb.getSheets();

			if (null != sheetArr && 0 != sheetArr.length) {
				for (Sheet sheet : sheetArr) {
					Map<String, SpPortControlBean> mapTemp = buildXlsDataMap(sheet);
					map.putAll(mapTemp);
				}
			}

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (BiffException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return map;
	}

	private Map<String, SpPortControlBean> buildXlsDataMap(Sheet sheet) {
		Map<String, SpPortControlBean> map = new HashMap<String, SpPortControlBean>();
		int columns = sheet.getColumns();
		int rows = sheet.getRows();

		SpPortControlBean bean = null;
		// 实际中xls文件的第一行是表头，说明每列的值名
		for (int row = 1; row < rows; row++) {
			bean = new SpPortControlBean();
			Cell cell = sheet.getCell(0, row);// 判断端口号是否为空
			if ("".equals(cell.getContents())) {
				continue;
			} else {
				for (int column = 0; column < columns; column++) {
					cell = sheet.getCell(column, row);
					if (0 == column) {
						bean.setSpPort(cell.getContents());
					} else if (1 == column) {
						bean.setBeLong(cell.getContents());
					} else if (2 == column) {
						bean.setCompanyName(cell.getContents());
					}
				}
			}
			map.put(bean.getSpPort(), bean);
		}

		return map;
	}

	public SpPortControlBean findById(String spPort) {
		SpPortControlBean bean = null;
		if (null != spPort) {
			bean = spPortControlDao.selectBySpPort(spPort);
		}

		return bean;
	}

}
