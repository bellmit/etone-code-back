package com.symbol.app.mantoeye.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;

public class MultiDialogAction extends BaseDispatchAction {
	/**
	 * 手机终端对话框
	 */
	public String initPhone() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		request.setAttribute("phoneInfoList", packPhoneInfo());
		logger.info("phone----------->>>>>");
		return "phone";
	}

	public List<Phone> packPhoneInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Phone p1 = new Phone();
		p1.setType("nokia");
		p1.setName("诺基亚");
		p1
				.setDetailModel("Nokia5700:NokiaE51:NokiaN78:Nokia6300:Nokia6030:Nokia7100s:Nokia6110:NokiaN95:Nokia6681:NokiaN91:Nokia6280");
		Phone p2 = new Phone();
		p2.setType("sony");
		p2.setName("索尼爱立信");
		p2
				.setDetailModel("SonyEricssonW810i:SonyEricssonK660i:SonyEricssonK800i:SonyEricssonW595c");
		Phone p3 = new Phone();
		p3.setType("sanxing");
		p3.setName("三星");
		p3
				.setDetailModel("SAMSUNGSGH-i900:SAMSUNGSGH-D880:SAMSUNGSGH-i450:SAMSUNGSGH-E958");
		Phone p4 = new Phone();
		p4.setType("duopuda");
		p4.setName("多普达");
		p4.setDetailModel("Dopod838:HTCP4550");
		Phone p5 = new Phone();
		p5.setType("moto");
		p5.setName("摩托罗拉");
		p5.setDetailModel("MotoA1200r:MotoA1200:MotoE680G");
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		return list;
	}

	/**
	 * 业务对话框
	 */
	public String initBusiness() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		request.setAttribute("phoneInfoList", packBusinessInfo());
		return "business";
	}

	public List<Phone> packBusinessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Phone p = new Phone();
		p.setType("websize");
		p.setName("网站");
		p.setDetailModel("腾讯:易查:3G门户:乐讯:新浪:空中网:动感论坛:百度:移动梦网:友度:其他");

		Phone p1 = new Phone();
		p1.setType("appType");
		p1.setName("应用软件");
		p1.setDetailModel("IM软件:视频软件:音频软件:证券软件");

		Phone p2 = new Phone();
		p2.setType("IM");
		p2.setName("IM");
		p2.setDetailModel("IM_QQ:IM_Fetion:IM_PICA:IM_MSN");

		Phone p3 = new Phone();
		p3.setType("video");
		p3.setName("视频");
		p3.setDetailModel("GGLIVE:万花筒");

		Phone p4 = new Phone();
		p4.setType("audio");
		p4.setName("音频");
		p4.setDetailModel("GGMUSIC:POP音乐:音乐随身听:百灵鸟");

		Phone p5 = new Phone();
		p5.setType("bond");
		p5.setName("证券");
		p5.setDetailModel("3G财神通:大智慧:同花顺");

		list.add(p);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		return list;
	}

	/**
	 * 区域对话框
	 */
	public String initArea() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		request.setAttribute("phoneInfoList", packAreaInfo());
		return "area";
	}

	public List<Phone> packAreaInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Phone p = new Phone();
		p.setType("BSC");
		p.setName("BSC");
		p.setDetailModel("BSC1:BSC2:BSC3:BSC4:BSC5:BSC6");

		Phone p1 = new Phone();
		p1.setType("SGSN");
		p1.setName("SGSN");
		p1.setDetailModel("SGSN1:SGSN2:SGSN3:SGSN4");

		Phone p2 = new Phone();
		p2.setType("行政划分");
		p2.setName("行政划分");
		// p2.setDetailModel("QQ:FETION:PICA:MSN");

		Phone p3 = new Phone();
		p3.setType("街道办");
		p3.setName("街道办");
		// p3.setDetailModel("GGLIVE:万花筒");

		Phone p4 = new Phone();
		p4.setType("营销片区");
		p4.setName("营销片区");
		// p4.setDetailModel("GGMUSIC:POP音乐:音乐随身听:百灵鸟");

		Phone p5 = new Phone();
		p5.setType("分公司");
		p5.setName("分公司");
		// p5.setDetailModel("3G财神通:大智慧:同花顺");

		list.add(p);
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		return list;
	}

}
