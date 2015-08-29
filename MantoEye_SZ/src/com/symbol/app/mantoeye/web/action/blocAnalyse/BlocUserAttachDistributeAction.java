package com.symbol.app.mantoeye.web.action.blocAnalyse;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;

public class BlocUserAttachDistributeAction extends BaseDispatchAction {
	public String list() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		buildDistributeData(request);
		return INDEX;
	}

	public void buildDistributeData(HttpServletRequest request) {
		Random random = new Random();
		String xml = "<root><chart id='1' name='总流量'>";
		for (int i = 1; i < 4; i++) {
			String businessName = "";
			String date = "2009-10-22";
			String up = random.nextInt(400) + 100 + "";
			String down = random.nextInt(600) + 50 + "";
			String count = random.nextInt(600) + 50 + "";
			String total = up + down;
			String imsis = random.nextInt(900) + 50 + "";
			if (i == 1) {
				businessName = "本地";
			} else if (i == 2) {
				businessName = "外地";
			} else {
				businessName = "外国";
			}
			String[] data = { date, businessName, imsis, total, up, down, count };
			dataList.add(data);
			xml = xml + "<data label='" + businessName + "' total='" + total
					+ "'  count='" + count + "'  imsis='" + imsis + "'/>";
		}
		xml = xml + "</chart></root>";
		request.setAttribute("dataList", dataList);
		request.setAttribute("xml", xml);
	}
}
