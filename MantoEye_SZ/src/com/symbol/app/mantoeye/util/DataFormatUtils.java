package com.symbol.app.mantoeye.util;

import java.util.List;

import com.symbol.app.mantoeye.dto.blackberry.ActiveUserSpaceBean;
import com.symbol.app.mantoeye.dto.blackberry.BlackBerryCountryBean;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.dto.mms.PeerMmsBean;

/**
 * 
 * 格式化数据
 * 
 * @author rankin
 * 
 */
public class DataFormatUtils {

	/**
	 * 获取流量的最大值
	 * 
	 * @param list
	 *            flag 1-流量 2-用户数 3-激活次数
	 * @return
	 */
	private static Long getMaxData(List<CommonFlush> list, int flag) {
		Long max = 0L;
		CommonFlush bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if (flag == 1) {//流量
					if (bean.getTotalFlush() > max) {
						max = bean.getTotalFlush();
					}
				} else if (flag == 2) {//用户数
					if (bean.getIntImsis() > max) {
						max = bean.getIntImsis();
					}
				}else if (flag == 3) {//访问次数
					if (bean.getVisit() > max) {
						max = bean.getVisit();
					}
				} else {//彩信发送量
					if (bean.getTotalSendFlush() > max) {
						max = bean.getTotalSendFlush();
					}
				}

			}
		}
		return max;
	}

	/**
	 * 根据数量级获取流量的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getFlushUnit(List<CommonFlush> list) {

		// 当最大的流量大于10TB时，所有的数据使用TB单位
		//Long mintb = 10 * 1024 * 1024 * 1024 * 1024L;
		Long mingb = 10 * 1024 * 1024 * 1024L;
		Long minmb = 10 * 1024 * 1024L;
		Long minkb = 10 * 1024L;
		Long max = getMaxData(list, 1);
//		System.out.print("--------****"+max);
//		if (max >= mintb) {
//			return "TB";
//		} else 
		if (max >= mingb) {
			return "GB";
		} else if (max >= minmb) {
			return "MB";
		} else if (max >= minkb) {
			return "KB";
		} else {
			return "B";
		}
	}
	/**
	 * 根据数量级获取流量的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getABBFlushUnit(List<ActiveUserSpaceBean> list) {

		// 当最大的流量大于10TB时，所有的数据使用TB单位
		//Long mintb = 10 * 1024 * 1024 * 1024 * 1024L;
		Long mingb = 10 * 1024 * 1024 * 1024L;
		Long minmb = 10 * 1024 * 1024L;
		Long minkb = 10 * 1024L;
		Long max = 0L;
		ActiveUserSpaceBean bean;
		for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if (bean.getTotalFlush() > max) {
					max = bean.getTotalFlush();
				}
		}
		if (max >= mingb) {
			return "GB";
		} else if (max >= minmb) {
			return "MB";
		} else if (max >= minkb) {
			return "KB";
		} else {
			return "B";
		}
	}
	/**
	 * 根据数量级获取流量的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getCBBFlushUnit(List<BlackBerryCountryBean> list) {

		// 当最大的流量大于10TB时，所有的数据使用TB单位
		//Long mintb = 10 * 1024 * 1024 * 1024 * 1024L;
		Long mingb = 10 * 1024 * 1024 * 1024L;
		Long minmb = 10 * 1024 * 1024L;
		Long minkb = 10 * 1024L;
		Long max = 0L;
		BlackBerryCountryBean bean;
		for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if (bean.getTotalFlush() > max) {
					max = bean.getTotalFlush();
				}
		}
		if (max >= mingb) {
			return "GB";
		} else if (max >= minmb) {
			return "MB";
		} else if (max >= minkb) {
			return "KB";
		} else {
			return "B";
		}
	}


	/**
	 * 根据数量级获取用户数的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getImsisUnit(List<CommonFlush> list) {

		Long minYi = 10 * 100000000L;
		Long minWan = 10 * 10000L;
		Long max = getMaxData(list, 2);
		if (max >= minYi) {
			return "亿";
		} else if (max >= minWan) {
			return "万";
		} else {
			return " ";
		}
	}
	/**
	 * 根据数量级获取用户数的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getABBImsisUnit(List<ActiveUserSpaceBean> list) {

		Long minYi = 10 * 100000000L;
		Long minWan = 10 * 10000L;
		Long max =0L;
		ActiveUserSpaceBean bean;
		for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if (bean.getImsis() > max) {
					max = bean.getImsis();
				}
		}
		if (max >= minYi) {
			return "亿";
		} else if (max >= minWan) {
			return "万";
		} else {
			return " ";
		}
	}
	/**
	 * 根据数量级获取用户数的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getCBBImsisUnit(List<BlackBerryCountryBean> list) {

		Long minYi = 10 * 100000000L;
		Long minWan = 10 * 10000L;
		Long max =0L;
		BlackBerryCountryBean bean;
		for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if (bean.getImsis() > max) {
					max = bean.getImsis();
				}
		}
		if (max >= minYi) {
			return "亿";
		} else if (max >= minWan) {
			return "万";
		} else {
			return " ";
		}
	}
	/**
	 * 根据数量级获取彩信发送量的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getSendUnit(List<CommonFlush> list) {

		Long minYi = 10 * 100000000L;
		Long minWan = 10 * 10000L;
		Long max = getMaxData(list, 4);
		if (max >= minYi) {
			return "亿";
		} else if (max >= minWan) {
			return "万";
		} else {
			return " ";
		}
	}
	/**
	 * 根据数量级获取彩信发送量的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getSendUnit1(List<PeerMmsBean> list) {

		Long minYi = 10 * 100000000L;
		Long minWan = 10 * 10000L;
		Long max = 0L;
		PeerMmsBean bean;
		for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if (bean.getTotalCount() > max) {
					max = bean.getTotalCount();
				}
		}
		if (max >= minYi) {
			return "亿";
		} else if (max >= minWan) {
			return "万";
		} else {
			return " ";
		}
	}

	/**
	 * 根据数量级获取访问次数的单位， 防止数量太大
	 * 
	 * @param list
	 * @return
	 */
	public static String getVisitUnit(List<CommonFlush> list) {

		Long minYi = 10 * 100000000L;
		Long minWan = 10 * 10000L;
		Long max = getMaxData(list, 3);
		if (max >= minYi) {
			return "亿";
		} else if (max >= minWan) {
			return "万";
		} else {
			return " ";
		}
	}

	/**
	 * 通过单位获取该单位对应的流量
	 * 
	 * @param c
	 * @param unit
	 *            单位
	 * @param type
	 *            数据，up down total
	 * @return
	 */
	public static Object getFlushByUnit(CommonFlush c, String unit, String type) {
		if (type.equals("up")) {
			if (unit.equals("TB")) {
				return c.getUpFlushTB();
			} else if (unit.equals("GB")) {
				return c.getUpFlushGB();
			} else if (unit.equals("MB")) {
				return c.getUpFlushMB();
			} else if (unit.equals("KB")) {
				return c.getUpFlushKB();
			} else {
				return c.getIntUpFlush();
			}
		} else if (type.equals("down")) {
			if (unit.equals("TB")) {
				return c.getDownFlushTB();
			} else if (unit.equals("GB")) {
				return c.getDownFlushGB();
			} else if (unit.equals("MB")) {
				return c.getDownFlushMB();
			} else if (unit.equals("KB")) {
				return c.getDownFlushKB();
			} else {
				return c.getIntDownFlush();
			}
		} else {
			if (unit.equals("TB")) {
				return c.getTotalFlushTB();
			} else if (unit.equals("GB")) {
				return c.getTotalFlushGB();
			} else if (unit.equals("MB")) {
				return c.getTotalFlushMB();
			} else if (unit.equals("KB")) {
				return c.getTotalFlushKB();
			} else {
				return c.getTotalFlush();
			}
		}
	}
	/**
	 * 通过单位获取该单位对应的流量
	 * 
	 * @param c
	 * @param unit
	 *            单位
	 * @param type
	 *            数据，up down total
	 *            flag 1-ActiveUserSpaceBean
	 *            2-BlackBerryCountryBean
	 * @return
	 */
	public static Object getFlushByUnit1(Object cc,int flag, String unit, String type) {
		if(flag==1){
			ActiveUserSpaceBean c = (ActiveUserSpaceBean)cc;
			if (type.equals("up")) {
				if (unit.equals("TB")) {
					return c.getUpFlushTb();
				} else if (unit.equals("GB")) {
					return c.getUpFlushGb();
				} else if (unit.equals("MB")) {
					return c.getUpFlushMb();
				} else if (unit.equals("KB")) {
					return c.getUpFlushKb();
				} else {
					return c.getUpFlush();
				}
			} else if (type.equals("down")) {
				if (unit.equals("TB")) {
					return c.getDownFlushTb();
				} else if (unit.equals("GB")) {
					return c.getDownFlushGb();
				} else if (unit.equals("MB")) {
					return c.getDownFlushMb();
				} else if (unit.equals("KB")) {
					return c.getDownFlushKb();
				} else {
					return c.getDownFlush();
				}
			} else {
				if (unit.equals("TB")) {
					return c.getTotalFlushTb();
				} else if (unit.equals("GB")) {
					return c.getTotalFlushGb();
				} else if (unit.equals("MB")) {
					return c.getTotalFlushMb();
				} else if (unit.equals("KB")) {
					return c.getTotalFlushKb();
				} else {
					return c.getTotalFlush();
				}
			}
		}else{
			BlackBerryCountryBean c = (BlackBerryCountryBean)cc;
			if (type.equals("up")) {
				if (unit.equals("TB")) {
					return c.getUpFlushTb();
				} else if (unit.equals("GB")) {
					return c.getUpFlushGb();
				} else if (unit.equals("MB")) {
					return c.getUpFlushMb();
				} else if (unit.equals("KB")) {
					return c.getUpFlushKb();
				} else {
					return c.getUpFlush();
				}
			} else if (type.equals("down")) {
				if (unit.equals("TB")) {
					return c.getDownFlushTb();
				} else if (unit.equals("GB")) {
					return c.getDownFlushGb();
				} else if (unit.equals("MB")) {
					return c.getDownFlushMb();
				} else if (unit.equals("KB")) {
					return c.getDownFlushKb();
				} else {
					return c.getDownFlush();
				}
			} else {
				if (unit.equals("TB")) {
					return c.getTotalFlushTb();
				} else if (unit.equals("GB")) {
					return c.getTotalFlushGb();
				} else if (unit.equals("MB")) {
					return c.getTotalFlushMb();
				} else if (unit.equals("KB")) {
					return c.getTotalFlushKb();
				} else {
					return c.getTotalFlush();
				}
			}
		}
		
	}
	/**
	 * 通过单位获取该单位对应的流量
	 * 
	 * @param c
	 * @param unit
	 *            单位
	 * @param type
	 *            数据，imsis visit
	 * @return
	 */
	public static Object getIVByUnit(CommonFlush c, String unit, String type) {
		if (type.equals("imsis")) {
			if (unit.equals("万")) {
				return c.getImsisWan();
			} else {
				return c.getIntImsis();
			}
		}else if(type.equals("send")){
			if (unit.equals("亿")) {
				return c.getTotalSendYi();
			} else if (unit.equals("万")) {
				return c.getTotalSendWan();
			} else {
				return c.getTotalSendFlush();
			}
		}
		else {
			if (unit.equals("亿")) {
				return c.getVisitYi();
			} else if (unit.equals("万")) {
				return c.getVisitWan();
			} else {
				return c.getVisit();
			}
		}
	}
	/**
	 * 通过单位获取该单位对应的流量
	 * 
	 * @param c
	 * @param unit
	 *            单位
	 * @param type
	 *            数据，imsis visit
	 *    flag        0-PeerMmsBean
	 *    1-ActiveUserSpaceBean
	 *    2-BlackBerryCountryBean
	 * @return
	 */
	public static Object getIVByUnit1(Object cc,int flag, String unit, String type) {
		if(flag==0){
			PeerMmsBean c = (PeerMmsBean)cc;
			if(type.equals("send")){
				if (unit.equals("亿")) {
					return c.getTotalSendYi();
				} else if (unit.equals("万")) {
					return c.getTotalSendWan();
				} else {
					return c.getTotalCount();
				}
			}
			else {
				return c.getTotalCount();
			}
		}else if(flag==1){
			ActiveUserSpaceBean c = (ActiveUserSpaceBean)cc;
			if(type.equals("imsis")){
				if (unit.equals("亿")) {
					return c.getImsisYi();
				} else if (unit.equals("万")) {
					return c.getImsisWan();
				} else {
					return c.getImsis();
				}
			}
			else {
				return c.getImsis();
			}
		}else{
			BlackBerryCountryBean c = (BlackBerryCountryBean)cc;
			if(type.equals("imsis")){
				if (unit.equals("亿")) {
					return c.getImsisYi();
				} else if (unit.equals("万")) {
					return c.getImsisWan();
				} else {
					return c.getImsis();
				}
			}
			else {
				return c.getImsis();
			}
		}
		
	}
	/**
	 *通过名称去掉List内某数据
	 * 
	 * @param list
	 * @return
	 */
	public static List<CommonFlush> exceptCommonFlush(List<CommonFlush> list,String except) {

		CommonFlush bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if(bean.getBusinessName().equals(except)){
					list.remove(bean);
					return list;
				}
			}
		}
		return list;
	}
	/**
	 *通过名称去掉List内某数据
	 * 
	 * @param list
	 * @return
	 */
	public static List<PeerMmsBean> exceptMmsFlush(List<PeerMmsBean> list,String except) {

		PeerMmsBean bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if(bean.getDimensionName().equals(except)){
					list.remove(bean);
					return list;
				}
			}
		}
		return list;
	}
	/**
	 *通过名称去掉List内某数据
	 * 
	 * @param list
	 * @return
	 */
	public static List<ActiveUserSpaceBean> exceptABBFlush(List<ActiveUserSpaceBean> list,String except) {

		ActiveUserSpaceBean bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if(bean.getDimensionName().equals(except)){
					list.remove(bean);
					return list;
				}
			}
		}
		return list;
	}
	/**
	 *通过名称去掉List内某数据
	 * 
	 * @param list
	 * @return
	 */
	public static List<BlackBerryCountryBean> exceptCBBFlush(List<BlackBerryCountryBean> list,String except) {

		BlackBerryCountryBean bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				if(bean.getDimensionName().equals(except)){
					list.remove(bean);
					return list;
				}
			}
		}
		return list;
	}
}
