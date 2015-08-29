package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.CameraTrackBisDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class CameraTrackBisManager {
	@Autowired
	private CameraTrackBisDAO cameraTrackBisDAO;

	public Page<CommonSport> query(final Page page, String terminal) {
		return cameraTrackBisDAO.query(page, terminal);
	}

	public List<CommonSport> listData(String terminal) {
		return cameraTrackBisDAO.listData(terminal);
	}


}
