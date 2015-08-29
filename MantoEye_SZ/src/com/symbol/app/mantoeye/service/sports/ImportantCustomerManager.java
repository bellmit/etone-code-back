package com.symbol.app.mantoeye.service.sports;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.sports.ImportantCustomerDAO;
import com.symbol.app.mantoeye.dao.sports.ImportantCustomerQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbArea;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class ImportantCustomerManager {

	@Autowired
	private ImportantCustomerDAO importantCustomerDAO;
	@Autowired
	private ImportantCustomerQueryDAO importantCustomerQueryDAO;
	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page, String common_search, int timeLevel,
			String startTime, String endTime,Integer intType,Integer dataType) {
		return importantCustomerQueryDAO.query1(page, common_search, timeLevel, startTime, endTime,intType,dataType);
	}

	public List<CommonSport> listData(String common_search, int timeLevel,
			String startTime, String endTime,Integer intType) {
		return importantCustomerQueryDAO.listData1(common_search, timeLevel, startTime, endTime,intType);
	}
	
	/**	查询业务排名的二级页面数据
	 */
	public Page<CommonSport> queryTerminalByBusiness(final Page page, String terminalProtectedArea, String terminalTimeSearch,
			Integer intType,Integer dataType) {
		return importantCustomerQueryDAO.queryTerminalByBusiness(page, terminalProtectedArea, terminalTimeSearch,intType,dataType);
	}

	public List<CommonSport> listDataTerminalByBusiness(String terminalProtectedArea, String terminalTimeSearch,Integer intType) {
		return importantCustomerQueryDAO.listDataTerminalByBusiness(terminalProtectedArea, terminalTimeSearch,intType);
	}
	
	/**	查询终端排名列表数据
	 * @return
	 */
	public Page<CommonSport> queryTerminal(final Page page, String common_search, int timeLevel,
			String startTime, String endTime,Integer intType,Integer dataType) {
		return importantCustomerQueryDAO.queryTerminal(page, common_search, timeLevel, startTime, endTime,intType,dataType);
	}

	public List<CommonSport> listDataTerminal(String common_search, int timeLevel,
			String startTime, String endTime,Integer intType) {
		return importantCustomerQueryDAO.listDataTerminal(common_search, timeLevel, startTime, endTime,intType);
	}
	
	public void saveMsis(List<String> msisList) {
		List list = importantCustomerQueryDAO.getAllMsisdn();
		int intType=1;//1重要客户，2重要客户
		String dtUpdateTime=CommonUtils.getCurrentDate();
		boolean flag=false;
		for (int i = 0; i < msisList.size(); i++) {
			String nmMsisdn = msisList.get(i);
			for (int j = 0; j < list.size(); j++) {
				Object[] obj = list.toArray();
				String msisdn = obj[j].toString();
				if(msisdn.equals(nmMsisdn)){
					flag=true;
					break;
				}	
			}
			if(!flag){
				importantCustomerDAO.insertMsisdn(Long.parseLong(nmMsisdn),intType,dtUpdateTime);
			}
		}
	}

	
	public void importArea(List<String> aList,int intType) {
		List<FtbArea> arealist = importantCustomerQueryDAO.queryAllArea(intType);
		//area不插入已存在的
		for (int i = 0; i < aList.size(); i++) {
			String vcAreaName = aList.get(i);
			boolean flag = true;
			String dtUpdateTime = CommonUtils.getCurrentDate();
			for (int j = 0; j < arealist.size(); j++) {
				if (vcAreaName.equals(arealist.get(j).getVcAreaName())) {
					flag = false;
					break;
				}
				
			}
			if (flag) {
				importantCustomerDAO.insertArea(vcAreaName, intType, dtUpdateTime);
			}
		
		}

	}
	
	public int importAreaMapCell(List<CommonSport> csList,int intType) {
		int count=0;
		List<CommonSport> commonSportList =new ArrayList<CommonSport>();		
		List cgiQueryList = importantCustomerQueryDAO.queryCgi();//查询所有的cgi
	
		List<FtbArea> list = importantCustomerQueryDAO.queryAllArea(intType);
	/*	//保障区域（以中文名匹配）
		if (intType==1) {
			for (int i = 0; i < csList.size(); i++) {		
				String vcAreaName = csList.get(i).getVcAreaName();
				String vcCgiName = csList.get(i).getVcCgiName();
				CommonSport commonSport= new CommonSport();
				if(vcAreaName!=null && !vcAreaName.equals("") && vcCgiName!=null && !vcCgiName.equals("")){
					//匹配cgi
					boolean flagCGI = true;
					List<String> cgiList = new ArrayList<String>();
					for (int j = 0; j < cgiQueryList.size(); j++) {
						Object[] objs = (Object[]) cgiQueryList.get(j);
						String vcCgiChName = objs[1].toString();
						if(vcCgiChName.equals(vcCgiName) || vcCgiChName.equals(vcCgiName.toLowerCase()) || vcCgiChName.equals(vcCgiName.toUpperCase())){						
							cgiList.add(objs[0].toString());
							flagCGI=false;
							//break;存在一个小区名对应几个cgi
						}	
					}
					if (cgiList!=null && cgiList.size()>0) {
						commonSport.setCgiList(cgiList);
					}
					if (flagCGI) {
						count++;//匹配不了cgi表
					}
					//匹配area				
					Long nmAreaId = null;
					for (int j = 0; j < list.size(); j++) {
						if (vcAreaName.equals(list.get(j).getVcAreaName())) {
							nmAreaId = list.get(j).getNmAreaId();
							break;
						}
						
					}
					if (nmAreaId==null) {
						System.out.println("系统出错!");
					}
					commonSport.setNmAreaId(nmAreaId); 
					if (commonSport!=null && commonSport.getCgiList()!=null && commonSport.getCgiList().size()>0) {
						commonSportList.add(commonSport);
					}	
				}
			}
		}
		*/
		
		//自定义小区集
		//if (intType==2) {
			for (int i = 0; i < csList.size(); i++) {		
				String vcAreaName = csList.get(i).getVcAreaName();
				String vcCgiName = csList.get(i).getVcCgiName();
				String vcCGI = csList.get(i).getVcCGI();
				CommonSport commonSport= new CommonSport();
				if(vcAreaName!=null && !vcAreaName.equals("") && vcCGI!=null && !vcCGI.equals("")){
					//匹配cgi
					boolean flagCGI = true;
					List<String> cgiList = new ArrayList<String>();
					for (int j = 0; j < cgiQueryList.size(); j++) {
						Object[] objs = (Object[]) cgiQueryList.get(j);
						String cgi = objs[0].toString();
						if(cgi.equals(vcCGI)){						
							cgiList.add(vcCGI);
							flagCGI=false;
							break;//找到匹配就跳出
						}	
					}
					if (cgiList!=null && cgiList.size()>0) {
						commonSport.setCgiList(cgiList);
					}
					if (flagCGI) {
						count++;//匹配不了cgi表
					}
					//匹配area				
					Long nmAreaId = null;
					for (int j = 0; j < list.size(); j++) {
						if (vcAreaName.equals(list.get(j).getVcAreaName()) && intType==list.get(j).getIntType()) {
							nmAreaId = list.get(j).getNmAreaId();
							break;
						}
						
					}
					if (nmAreaId==null) {
						System.out.println("系统出错!");
					}
					commonSport.setNmAreaId(nmAreaId); 
					if (commonSport!=null && commonSport.getCgiList()!=null && commonSport.getCgiList().size()>0) {
						commonSportList.add(commonSport);
					}	
				}
			}
		//}
		
		
		//更新完对应关系后录入数据库,
		if (commonSportList!=null && commonSportList.size()>0) {
			List amclist = importantCustomerQueryDAO.queryAreaMapCell();
			for (int i = 0; i < commonSportList.size(); i++) {
				Long nmAreaId = commonSportList.get(i).getNmAreaId();
				List<String> cgiList = commonSportList.get(i).getCgiList();
				if (nmAreaId!=null && cgiList!=null && cgiList.size()>0) {
						for (int k = 0; k < cgiList.size(); k++) {
							String cgi = cgiList.get(k).toString();
							boolean flag = true;
							for (int j = 0; j < amclist.size(); j++) {
								Object[] objs = (Object[]) amclist.get(j);
								if (nmAreaId.toString().equals(objs[0].toString()) && cgi.equals(objs[1].toString())) {
									flag = false;
									break;
								}
							}
							if (flag) {
								importantCustomerDAO.insertAreaMapCell(nmAreaId, cgi);
							}
						}						
				}

			}
		}
		return count;
	}
	
}
