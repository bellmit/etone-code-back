package com.symbol.app.mantoeye.web.action.blocAnalyse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;

public class BlocSpaceDistributeAction  extends BaseDispatchAction {

	private String timeLevel;// 时间粒度
	private String searchDate;// 时间点
	private String spaceLevel;// 空间粒度
	private String blocApn;// 集团APN

	public String getBlocApn() {
		return blocApn;
	}

	public void setBlocApn(String blocApn) {
		this.blocApn = blocApn;
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

	public String getSpaceLevel() {
		return spaceLevel;
	}

	public void setSpaceLevel(String spaceLevel) {
		this.spaceLevel = spaceLevel;
	}

	public String list() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		logger.info(timeLevel + "-" + searchDate + "-" + spaceLevel);
		String lab = spaceLevel;
		if(spaceLevel!=null){
			if(spaceLevel.equals("sgsn")){
				lab = "SGSN";
			}
			if(spaceLevel.equals("street")){
				lab = "街道办";
			}
			if(spaceLevel.equals("marea")){
				lab = "营销片区";
			}
			if(spaceLevel.equals("branch")){
				lab = "分公司";
			}
			
		}else{
			lab = "BSC";
		}
		
		buildTestData(request,lab);
		return INDEX;
	}

	// XML格式
	// <root>
	// <chart id="1" name="总流量">
	// <data label="10-1" total="2400" up="1200" down="1200" imsis="1200"/>
	// </chart>
	// </root>
	private void buildTestData(HttpServletRequest request,String lab) {
		List<String[]> list = new ArrayList<String[]>();
		String[] data = null;
		Random random = new Random();
		String xml = "<root><chart id='1' name='"+lab+"流量'>";
		String date = "2009-10-20";
		String basedate = lab;
		for (int i = 1; i < 25; i++) {
			int up = random.nextInt(400) + 100;
			int down = random.nextInt(600) + 50;
			int total = up + down;
			int imsis = random.nextInt(900) + 50;
			int count = random.nextInt(700) + 50;
			data = new String[] {date, basedate + (i < 10 ? "0" + i : "" + i), imsis + "", total + "" ,
					up + "", down + "",count+""};
			xml = xml + "<data label='" +lab+ (i < 10 ? "0" + i : "" + i)
					+ "' total='" + total + "'  up='" + up + "'  down='" + down
					+ "'  imsis='" + imsis + "'/>";
			list.add(data);
		}
		xml = xml + "</chart></root>";
		request.setAttribute("dataList", list);
		request.setAttribute("xml", xml);
	}
}
