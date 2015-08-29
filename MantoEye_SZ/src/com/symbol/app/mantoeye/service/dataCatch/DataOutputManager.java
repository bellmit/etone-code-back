package com.symbol.app.mantoeye.service.dataCatch;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.dataCatch.DataOutputDAO;
import com.symbol.app.mantoeye.dto.VFtbDataOutPutTask;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.flush.OutPutTableColumnEntity;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbCgi;
import com.symbol.app.mantoeye.entity.FtbDataOutPutDecTask;
import com.symbol.app.mantoeye.entity.FtbDataOutPutTask;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * 全量业务自发现
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class DataOutputManager extends
		EntityManager<FtbDataOutPutTask, Long> {

	@Autowired
	private DataOutputDAO dataOutputDAO;
	public static Map<String, Integer> sortMap;
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected DataOutputDAO getEntityDao(){
		return dataOutputDAO;
	}

	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbDataOutPutTask> search(final Page<FtbDataOutPutTask> page,
			final List<PropertyFilter> filters) {
		Page<FtbDataOutPutTask> apage = dataOutputDAO.find(page, filters);
		List<FtbDataOutPutTask> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}

	// 实体到视图的转换
	public List<VFtbDataOutPutTask> convertBeanToView(
			List<FtbDataOutPutTask> list) {
		List<VFtbDataOutPutTask> viewList = new ArrayList<VFtbDataOutPutTask>();
		if (list != null && !list.isEmpty()) {		
			for (FtbDataOutPutTask bean : list) {
				VFtbDataOutPutTask view = new VFtbDataOutPutTask();
				int tasktype = bean.getIntTaskType();
				view.setNmDataOutPutTaskId(bean.getNmDataOutPutTaskId());// 任务ID
				view.setIntTaskDelong(bean.getIntTaskDelong());
				view.setVcTaskName(bean.getVcTaskName());// 任务名
				view.setVcTaskOrder(bean.getVcTaskOrder());// 定制人
				view.setDtOrderTime(CommonUtils.formatFullDate(bean
						.getDtOrderTime()));// 定制时间
				view.setDtStartTime(CommonUtils.formatFullDate(bean
						.getDtStartTime()));// 开始时间
				view.setDtEndTime(CommonUtils.formatFullDate(bean
						.getDtEndTime()));// 结束时间
				view.setBitTaskActiveFlag(bean.getBitTaskActiveFlag());// 是否激活
				view.setIntTaskStatus(bean.getIntTaskStatus());// 任务状态
				view.setIntTaskType(tasktype);// 任务类型
				viewList.add(view);
			}
		}
		return viewList;
	}

	/**
	 * 保存解析记录
	 * hasgroup:分组标识
	 */
	public void saveParseTask(FtbDataOutPutTask entity) {
		dataOutputDAO.saveTask(entity);
	}

	public Long findTaskByName(String vcTaskName) {
		return dataOutputDAO.findTaskByName(vcTaskName);
	}
	
	
	public FtbDataOutPutDecTask getByTaskId(Long taskId) {
		return dataOutputDAO.getByTaskId(taskId);
	}
	
	public void saveDataOutPutDecTask(FtbDataOutPutDecTask entity) {
		dataOutputDAO.saveDataOutPutDecTask(entity);
	}
	
	public void saveOutPutBussinessMap(Long taskId,Long businessId) {
		dataOutputDAO.saveOutPutBussinessMap(taskId,businessId);
	}
	
	
	/**
	 *根据选择的任务ID 组装业务对应的业务ID和业务类型ID
	 */
	public String queryBussIdAndBussIdTypeByTaskId(int taskId) {
		List<BussAndBussType> list = dataOutputDAO
				.queryBussAndBussTypeByTaskId(taskId);
		String bisIdAndBisTypeId = "";
		String businessName = "";
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				bisIdAndBisTypeId = bisIdAndBisTypeId + bat.getBusinessTypeId()
						+ ":" + bat.getBusinessId() + ",";
				businessName = businessName
						+ SymbolUtils.getSaftDialogStr(bat.getBusinessName())
						+ ",";
			}
			bisIdAndBisTypeId = bisIdAndBisTypeId.substring(0, bisIdAndBisTypeId
					.length() - 1);
			businessName = businessName.substring(0, businessName.length() - 1);
			return bisIdAndBisTypeId + "@" + businessName;
		}else{
			return null;
		}
		

	}
	
	
	/**
	 *根据选择的任务ID 组装apn
	 */
	public String queryApnIdAndApnIdTypeByTaskId(int taskId) {
		List<BussAndBussType> list = dataOutputDAO
				.queryApnIdAndApnIdTypeByTaskId(taskId);
		String bisIdAndBisTypeId = "";
		String businessName = "";
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				bisIdAndBisTypeId = bisIdAndBisTypeId + bat.getBusinessTypeId()
						+ ":" + bat.getBusinessId() + ",";
				businessName = businessName
						+ SymbolUtils.getSaftDialogStr(bat.getBusinessName())
						+ ",";
			}
			bisIdAndBisTypeId = bisIdAndBisTypeId.substring(0, bisIdAndBisTypeId
					.length() - 1);
			businessName = businessName.substring(0, businessName.length() - 1);
			return bisIdAndBisTypeId + "@" + businessName;
		}else{
			return null;
		}
	}
	
	public String queryAreaIdAndCgiByTaskId(int taskId) {
		List<BussAndBussType> list = dataOutputDAO
				.queryAreaIdAndCgiByTaskId(taskId);
		String bisIdAndBisTypeId = "";
		String businessName = "";
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				bisIdAndBisTypeId = bisIdAndBisTypeId + bat.getBusinessTypeId()
						+ ":" + bat.getBusinessId() + ",";
				businessName = businessName
						+ SymbolUtils.getSaftDialogStr(bat.getBusinessName())
						+ ",";
			}
			bisIdAndBisTypeId = bisIdAndBisTypeId.substring(0, bisIdAndBisTypeId
					.length() - 1);
			businessName = businessName.substring(0, businessName.length() - 1);
			return bisIdAndBisTypeId + "@" + businessName;
		}else{
			return null;
		}
	}
	
	
	public void deleteOutPutColumnMapTask(Long taskId) {
		dataOutputDAO.deleteOutPutColumnMapTask(taskId);
	}
	
	public void deleteOutPutBussinessMap(Long taskId) {
		dataOutputDAO.deleteOutPutBussinessMap(taskId);
	}
	
	
	public void saveOutPutColumnMapTask(Long taskId,Long nmTableMapId) {
		dataOutputDAO.saveOutPutColumnMapTask(taskId,nmTableMapId);
	}
	
	
	public void deleteOutPutApnMap(Long taskId) {
		dataOutputDAO.deleteOutPutApnMap(taskId);
	}
	
	
	public void saveOutPutApnMap(Long taskId,String vcApn) {
		dataOutputDAO.saveOutPutApnMap(taskId,vcApn);
	}
	
	
	public void saveOutPutCgiMap(Long taskId,String cgi) {
		dataOutputDAO.saveOutPutCgiMap(taskId,cgi);
	}
	
	/**
	 * 编辑解析记录
	 */
	public boolean editParseTask(FtbDataOutPutTask entity,
			TbBaseUserInfo userInfo) {

		boolean succ = true;
		FtbDataOutPutTask entity2 = dataOutputDAO.get(entity
				.getNmDataOutPutTaskId());
		if (entity2.getIntTaskStatus() != 0) {
			succ = false;
		} else {
			entity2.setDtStartTime(entity.getDtStartTime());
			entity2.setDtEndTime(entity.getDtEndTime());
			entity2.setBitTaskActiveFlag(entity.getBitTaskActiveFlag());
			Long taskId = entity.getNmDataOutPutTaskId();
			dataOutputDAO.save(entity2);			
		}
		return succ;
	}
	
	/**
	 * 编辑解析记录
	 */
	public void editDataOutPutDecTask(FtbDataOutPutDecTask entity) {
		dataOutputDAO.updateDataOutPutDecTask(entity);
	}

	public void deleteTasks(Long taskId) {
		// 删除任务
		dataOutputDAO.deleteTask(taskId);
	}
	
	public void deleteNmMsisdn(String nmIds) {
		// 删除号码
		dataOutputDAO.deleteNmMsisdn(nmIds);
	}
	
	
	
	public void deleteOutPutCgiMap(Long taskId) {
		dataOutputDAO.deleteOutPutCgiMap(taskId);
	}
	
	public boolean isExistNmMsisdn(Long taskId) {
		// 删除号码
		return dataOutputDAO.isExistNmMsisdn(taskId);
	}
	
	public boolean isExistIMEI(Long taskId) {
		// 删除号码
		return dataOutputDAO.isExistIMEI(taskId);
	}
	
	public boolean isExistUrl(Long taskId) {
		// 删除号码
		return dataOutputDAO.isExistUrl(taskId);
	}
	
	public int isVcTaskName(String vcTaskName){
		return dataOutputDAO.queryByVcTaskName(vcTaskName);
	}

	
	public int isVcFileName(String vcFileName){
		return dataOutputDAO.queryByVcFileName(vcFileName);
	}
	
	public int isNmMsisdn(Long taskId,Long nmMsisdn,Long nmId){
		return dataOutputDAO.queryNmMsisdn(taskId,nmMsisdn,nmId);
	}
	
	/**
	 * 根据表ID查询所有表字段
	 * 
	 * @return
	 */
	public List<OutPutTableColumnEntity> queryColumnMapByTableId(Long taskId) {
		String compfield;
		OutPutTableColumnEntity entity;
		List<OutPutTableColumnEntity> compList;
		List<OutPutTableColumnEntity> columnList = dataOutputDAO
				.queryColumnMapByTableId(taskId);
		
		return columnList;
	}
	
	public List isOutPutColumnMap(Long taskId) {	
		return dataOutputDAO.isOutPutColumnMap(taskId);
	}
	
	public boolean isExistOutPutColumnMap(Long taskId) {	
		return dataOutputDAO.isExistOutPutColumnMap(taskId);
	}
	
	public boolean isExistOutPutCgiMap(Long taskId) {	
		return dataOutputDAO.isExistOutPutCgiMap(taskId);
	}
	
	public int isOutPutMsisdnMap(Long taskId) {	
		return dataOutputDAO.isOutPutMsisdnMap(taskId);
	}
	
	public Page<CommonSport> queryNmMsisdn(final Page page,
			Long taskId,String nmMsisdn) {
		return dataOutputDAO.queryNmMsisdn(page, taskId, nmMsisdn);
	}
	
	
	public List<FtbCgi> queryOutPutCgiMap(Long taskId) {
		return dataOutputDAO.queryOutPutCgiMap(taskId);
	}
	
	public Page<FtbCgi> queryCgi(final Page page,
			String vcCgiChName) {
		return dataOutputDAO.queryCgi(page,vcCgiChName);
	}
	
	
	public List<CommonSport> exportNmMsisdn(Long taskId,String nmMsisdn) {
		return dataOutputDAO.exportNmMsisdn(taskId,nmMsisdn);
	}
	
	public void saveOutPutColumnMap(Long taskId,String column,int value) {	
		dataOutputDAO.saveOutPutColumnMap(taskId,column,value);
	}
	
	public void updateOutPutColumnMap(Long taskId,String column,int value) {	
		dataOutputDAO.updateOutPutColumnMap(taskId,column,value);
	}
	
	
	public void saveOutPutMsisdnMap(Long taskId,List msisList) {
		List list = dataOutputDAO.queryAllNmMsisdn(taskId);
		if (msisList!=null && msisList.size()>0) {
			for (int i = 0; i < msisList.size(); i++) {
				String msis = msisList.get(i).toString();
				boolean flag = true;
				if (list!=null && list.size()>0) {
					for (int j = 0; j < list.size(); j++) {
						String nmMsisdn = list.get(j).toString();
						if (msis.equals(nmMsisdn)) {
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					dataOutputDAO.saveOutPutMsisdnMap(taskId,Common.StringToLong(msis));
				}
			}
		}
	}
	public void updateOutPutMsisdnMap(Long taskId,Long nmMsisdn,Long nmId) {
		dataOutputDAO.updateOutPutMsisdnMap(taskId,nmMsisdn,nmId);
	}
	
	
	/*
	 * 改动
	 * 导入URL数据，先判断数据表内是否已存在
	 * 
	 * */
	public void saveOutPutUrlMap(Long taskId,List urlList) {
		if (urlList!=null && urlList.size()>0) {
			List<String> urlDataList=dataOutputDAO.findUrlByCondition(taskId);//查处表内已存在该任务ID的URL
			for (int i = 0; i < urlList.size(); i++) {
//				int count=queryUrlByCondition(taskId,urlList.get(i).toString(),null);
//				if(count==-1)
				if(!urlDataList.contains(urlList.get(i)))
				dataOutputDAO.saveOutPutUrlMap(taskId,urlList.get(i).toString());
			}
		}
	}
	/*
	 * 
	 * 改动
	 * 导入IMEI的数据，先判断数据表内是否已存在
	 * 
	 * */
	public void saveOutPutImeiMap(Long taskId,List IMIEList) {
		if (IMIEList!=null && IMIEList.size()>0) {
			List<String> imeiDataList=dataOutputDAO.findImeiByCondition(taskId);
			for (int i = 0; i < IMIEList.size(); i++) {
//				int count=queryvcImeiByCondition(taskId,IMIEList.get(i).toString(),null);
//				if(count==-1)
				if(!imeiDataList.contains(IMIEList.get(i)))
				dataOutputDAO.saveOutPutImeiMap(taskId,IMIEList.get(i).toString());
			}
		}
	}
	
	
	public void getComparatorMap() {
		// 设置排序方式
		sortMap = new HashMap<String, Integer>();
		// 时间排最前
		sortMap.put("intYear", 1);
		sortMap.put("intMonth", 2);
		sortMap.put("intDay", 3);
		sortMap.put("intHour", 4);
		sortMap.put("intWeek", 5);
		sortMap.put("intRaitype", 6);

		// 流量
		sortMap.put("nmAppUpFlush", 10);
		sortMap.put("nmAppDownFlush", 11);
		sortMap.put("nmFlush", 12);
		sortMap.put("nmAveFlush", 13);

		// 用户数
		sortMap.put("nmUsers", 20);

		sortMap.put("nmUpCounts", 30);
		sortMap.put("nmDownCounts", 31);
		sortMap.put("nmTotalCounts", 32);
		sortMap.put("nmCounts", 33);

		// 访问次数
		sortMap.put("nmActiveCounts", 40);
		sortMap.put("nmVisitCounts", 41);

		sortMap.put("dtStatTime", 999);
	}
	
	
	/**
	 * 组装APN
	 */
	public Map<String, Map<String, String>> queryApnAndApnType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		List<BussAndBussType> list = dataOutputDAO
				.queryApnAndApnType();
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				String bussTypeNameAndId = SymbolUtils.getSaftDialogStr(bat
						.getBusinessTypeName())
						+ ":" + bat.getBusinessTypeId();// 业务类型名，业务ID组装成主键一起传上应用层！
				if (mapAll.get(bussTypeNameAndId) == null) {
					bussMap = new HashMap<String, String>();
					bussMap.put(bat.getBusinessName(), bat.getBusinessTypeId()
							+ ":" + bat.getBusinessId());
					mapAll.put(bussTypeNameAndId, bussMap);
				} else {
					mapAll.get(bussTypeNameAndId)
							.put(
									SymbolUtils.getSaftDialogStr(bat
											.getBusinessName()),
									bat.getBusinessTypeId() + ":"
											+ bat.getBusinessId());
				}
			}
		}
		return mapAll;
	}
	
	/*
	 * 任务定制-Url
	 * */
	public Page<CommonSport> queryvcUrl(Page page,Long taskId,String vcUrl){
		return dataOutputDAO.queryvcUrl(page, taskId, vcUrl);
	}
	
	public void DelUrlById(String ids){
		 dataOutputDAO.DelUrlByID(ids);
	}
	public List<CommonSport> urlListData(Long taskId,String vcUrl){
		return dataOutputDAO.urlListData(taskId, vcUrl);
	}
	public Integer queryUrlByCondition(Long taskId,String vcUrl,Long nmId) {
		return dataOutputDAO.queryUrlByCondition(taskId, vcUrl, nmId);
	}
	public int isExistsUrl(Long taskId) {
		return dataOutputDAO.isExistsUrl(taskId);
	}

	public void savevcUrl(Long taskId,List vcUrlList) {
		if (vcUrlList!=null && vcUrlList.size()>0) {
			for (int i = 0; i < vcUrlList.size(); i++) {
				String vcUrl =vcUrlList.get(i).toString();
				dataOutputDAO.savevcUrl(taskId,vcUrl);
			}
		}
	}
	public void modifyUrl(Long taskId,String vcUrl,Long nmId) {
		dataOutputDAO.modifyUrl(taskId, vcUrl, nmId);
	}
	
	
	/*
	 * 任务定制-Imei
	 * */
	public Page<CommonSport> queryvcImei(Page page,Long taskId,String vcImei){
		return dataOutputDAO.queryvcImei(page, taskId, vcImei);
	}
	
	public void DelvcImeiById(String ids){
		 dataOutputDAO.DelvcImeiByID(ids);
	}
	public List<CommonSport> vcImeiListData(Long taskId,String vcImei){
		return dataOutputDAO.vcImeiListData(taskId, vcImei);
	}
	public Integer queryvcImeiByCondition(Long taskId,String vcImei,Long nmId) {
		return dataOutputDAO.queryvcImeiByCondition(taskId, vcImei, nmId);
	}
	public int isExistsvcImei(Long taskId) {
		return dataOutputDAO.isExistsvcImei(taskId);
	}

	public void savevcvcImei(Long taskId,List vcUrlList) {
		if (vcUrlList!=null && vcUrlList.size()>0) {
			List list = dataOutputDAO.queryAllImei(taskId);
			for (int i = 0; i < vcUrlList.size(); i++) {
				String vcImei =vcUrlList.get(i).toString();
				boolean flag = true;
				if (list!=null && list.size()>0) {
					for (int j = 0; j < list.size(); j++) {
						String imei = list.get(j).toString();
						if (vcImei.equals(imei)) {
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					dataOutputDAO.savevcImei(taskId,vcImei);
				}
			}
		}
	}
	public void modifyvcImei(Long taskId,String vcImei,Long nmId) {
		dataOutputDAO.modifyvcImei(taskId, vcImei, nmId);
	}
}
