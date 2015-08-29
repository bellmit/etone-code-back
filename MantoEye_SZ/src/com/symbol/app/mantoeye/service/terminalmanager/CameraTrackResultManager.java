package com.symbol.app.mantoeye.service.terminalmanager;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.terminal.CameraTrackResultDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;


@Service
@Transactional
public class CameraTrackResultManager  {
	@Autowired
	private CameraTrackResultDAO cameraTrackResultDAO;
	/////////////////////////////////////【通用拍照】区域分析流量表/////////////////////////////////////
	public Page<CommonSport> queryAreaAnalyse(final Page page,long taskId,int areaType, String taskStartTime, long intTaskStatus, int dataType){
		return cameraTrackResultDAO.queryAreaAnalyse(page, taskId,areaType, taskStartTime, intTaskStatus, dataType);
	}
	public List<CommonSport> AreaAnalyselistData( long taskId,int areaType, String taskStartTime, long intTaskStatus, int dataType) {
		return cameraTrackResultDAO.AreaAnalyselistData( taskId,areaType,taskStartTime, intTaskStatus, dataType);
	}
	
	
	
	/////////////////////////////////////【通用拍照】业务分析流量表/////////////////////////////////////
	public Page<CommonSport> queryBisAnalyse(final Page page,long taskId, String taskStartTime, long intTaskStatus, int dataType){
		return cameraTrackResultDAO.queryBisAnalyse(page, taskId, taskStartTime, intTaskStatus, dataType);
	}
	public List<CommonSport> bisAnalyselistData( long taskId, String taskStartTime, long intTaskStatus, int dataType) {
		return cameraTrackResultDAO.bisAnalyselistData( taskId, taskStartTime, intTaskStatus, dataType);
	}
	
	
	/////////////////////////////////////【通用拍照】终端分析流量表/////////////////////////////////////
	public Page<CommonSport> queryTerminalAnalyse(final Page page,long taskId, String taskStartTime, long intTaskStatus, int dataType){
		return cameraTrackResultDAO.queryTerminalAnalyse(page, taskId, taskStartTime, intTaskStatus, dataType);
	}
	public List<CommonSport> TerminalAnalyselistData( long taskId, String taskStartTime, long intTaskStatus, int dataType) {
		return cameraTrackResultDAO.terminalAnalyselistData( taskId, taskStartTime, intTaskStatus, dataType);
	}
	
	////////////////////////////////////【通用拍照】区域-终端分析流量表///////////////////////////////////
	public Page<CommonSport> queryAreaTerminal(final Page page,long taskId,int areaType,int areaId,int terminalId,String taskStartTime, int dataType){
		return cameraTrackResultDAO.queryAreaTerminal(page, taskId,areaType,areaId,terminalId,taskStartTime,dataType);
	}
	public List<CommonSport> areaTerminallistData( long taskId,int areaType,int areaId,int terminalId,String taskStartTime, int dataType) {
		return cameraTrackResultDAO.areaTerminallistData( taskId,areaType,areaId,terminalId, taskStartTime, dataType);
	}
	
	///////////////////////////////////［通用拍照］区域-业务分析流量表//////////////////////////////////
	public Page<CommonSport> queryAreaBis(final Page page,long taskId,int areaType,int areaId,int bussinessId, String taskStartTime, int dataType){
		return cameraTrackResultDAO.queryAreaBis(page, taskId,areaType,areaId,bussinessId,taskStartTime, dataType);
	}
	public List<CommonSport> areaBislistData( long taskId,int areaType,int areaId,int bussinessId, String taskStartTime, int dataType) {
		return cameraTrackResultDAO.areaBislistData( taskId,areaType,areaId,bussinessId,taskStartTime, dataType);
	}
	//////////////////////////////////【通用拍照】终端-业务分析流量表//////////////////////////////
	public Page<CommonSport> queryTerminalBis(final Page page,long taskId,int terminalId,int bussinessId, String taskStartTime, int dataType){
		return cameraTrackResultDAO.queryTerminalBis(page, taskId,terminalId,bussinessId,taskStartTime, dataType);
	}
	public List<CommonSport> terminalBislistData( long taskId,int terminalId,int bussinessId, String taskStartTime, int dataType) {
		return cameraTrackResultDAO.terminalBislistData( taskId,terminalId,bussinessId,taskStartTime, dataType);
	}
}
