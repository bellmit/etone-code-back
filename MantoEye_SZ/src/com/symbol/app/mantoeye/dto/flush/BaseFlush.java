package com.symbol.app.mantoeye.dto.flush;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 公用的保存流量用户数据的实体
 * 
 * @author rankin
 * 
 */
public class BaseFlush {

	public static final Log log = LogFactory.getLog(BaseFlush.class);
	/*
	 * 由数据库相应表提供的数据
	 */
	private Long intUpFlush;// 上行流量(单位KB)
	private Long intDownFlush;// 下行流量(单位KB)
	private Long intUpPackets;// 上行数据包数
	private Long intDownPackets;// 下行数据包数
	private Long intImsis;// 用户数
	private int intYear;// 年
	private int intMonth;// 月
	private int intDay;// 日
	private int intHour;// 时段
	private BaseDimension dimension1;// 第一个维度的信息，如'业务类型'
	private BaseDimension dimension2;// 第二个维度的信息，如'APN'

	private final BigDecimal mbformat = new BigDecimal("1024.0");
	private final BigDecimal gbformat = new BigDecimal("1048576.0");

	private String objectId;// 业务ID
	private String objectName;// 业务名称
	private String sequence; // 排序号
	private BigDecimal flushPercent; // 流量占比

	/*
	 * 通过计算得出的数据
	 */
	private BigDecimal totalFlush;// 总流量(单位KB)
	private BigDecimal totalPackets;// 总数据包
	private BigDecimal totalFlushMb;// 总流量(单位MB)
	private BigDecimal totalFlushGb;// 总流量(单位GB)
	private BigDecimal upFlushMb;// 上行流量(单位MB)
	private BigDecimal upFlushGb;// 上行流量(单位GB)
	private BigDecimal downFlushMb;// 下行流量(单位MB)
	private BigDecimal downFlushGb;// 下行流量(单位GB)
	private BigDecimal averageFlush;// 平均流量(单位KB)
	private BigDecimal averageFlushMb;// 平均流量(单位MB)
	private BigDecimal averageFlushGb;// 平均流量(单位GB)

	private String fullDate;// 时间
	private String groupDate;// 统计分组时间 例：(按天统计:'2009-9' ;按小时统计: '2009-9-1' )
	private String sectionDate;// 分段时间 （按天统计即为天的值、按小时统计即为小时的值）

	/*
	 * 
	 * Getter and Setter
	 */
	public Long getIntUpFlush() {
		return intUpFlush;
	}

	public Long getIntDownFlush() {
		return intDownFlush;
	}

	public void setIntUpFlush(Long intUpFlush) {
		this.intUpFlush = intUpFlush;
	}

	public void setIntDownFlush(Long intDownFlush) {
		this.intDownFlush = intDownFlush;
	}

	public Long getIntUpPackets() {
		return intUpPackets;
	}

	public void setIntUpPackets(Long intUpPackets) {
		this.intUpPackets = intUpPackets;
	}

	public Long getIntDownPackets() {
		return intDownPackets;
	}

	public void setIntDownPackets(Long intDownPackets) {
		this.intDownPackets = intDownPackets;
	}

	public Long getIntImsis() {
		return intImsis;
	}

	public void setIntImsis(Long intImsis) {
		this.intImsis = intImsis;
	}

	public int getIntYear() {
		return intYear;
	}

	public void setIntYear(int intYear) {
		this.intYear = intYear;
	}

	public int getIntMonth() {
		return intMonth;
	}

	public void setIntMonth(int intMonth) {
		this.intMonth = intMonth;
	}

	public int getIntDay() {
		return intDay;
	}

	public void setIntDay(int intDay) {
		this.intDay = intDay;
	}

	public int getIntHour() {
		return intHour;
	}

	public void setIntHour(int intHour) {
		this.intHour = intHour;
	}

	public BaseDimension getDimension1() {
		return dimension1;
	}

	public void setDimension1(BaseDimension dimension1) {
		this.dimension1 = dimension1;
	}

	public BaseDimension getDimension2() {
		return dimension2;
	}

	public void setDimension2(BaseDimension dimension2) {
		this.dimension2 = dimension2;
	}

	/*
	 * Getter
	 */
	/**
	 * 总流量(单位KB)
	 * 
	 * @return
	 */
	public BigDecimal getTotalFlush() {
		return totalFlush;
	}

	/**
	 * 总用户数
	 * 
	 * @return
	 */
	public BigDecimal getTotalPackets() {
		return totalPackets;
	}

	/**
	 * 总流量(单位MB)
	 * 
	 * @return
	 */
	public BigDecimal getTotalFlushMb() {
		return totalFlushMb;
	}

	public void setTotalFlushMb(BigDecimal totalFlushMb) {
		this.totalFlushMb = totalFlushMb;
	}

	/**
	 * 总流量(单位GB)
	 * 
	 * @return
	 */
	public BigDecimal getTotalFlushGb() {
		return totalFlushGb;
	}

	public BigDecimal getUpFlushMb() {
		return upFlushMb;
	}

	public BigDecimal getUpFlushGb() {
		return upFlushGb;
	}

	public BigDecimal getDownFlushMb() {
		return downFlushMb;
	}

	public BigDecimal getDownFlushGb() {
		return downFlushGb;
	}

	public BigDecimal getAverageFlush() {
		return averageFlush;
	}

	public BigDecimal getAverageFlushMb() {
		return averageFlushMb;
	}

	public BigDecimal getAverageFlushGb() {
		return averageFlushGb;
	}

	public String getFullDate() {
		return fullDate;
	}

	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}

	public String getGroupDate() {
		return groupDate;
	}

	public String getSectionDate() {
		return sectionDate;
	}

	/**
	 * 通过计算获取统计值 当流量、用户等重数据库查询所得数据设置完成后 调用一次此方法计算统计值
	 * 
	 * @return ishour 是否是按小时统计
	 */
	public boolean calculate(boolean ishour) {
		try {
			// 总数据包数
			this.totalPackets = new BigDecimal(intUpPackets + intDownPackets);
			this.upFlushMb = new BigDecimal(intUpFlush).divide(mbformat,
					RoundingMode.DOWN).setScale(2, BigDecimal.ROUND_HALF_UP);
			this.upFlushGb = new BigDecimal(intUpFlush).divide(gbformat,
					RoundingMode.DOWN).setScale(2, BigDecimal.ROUND_HALF_UP);
			this.downFlushMb = new BigDecimal(intDownFlush).divide(mbformat,
					RoundingMode.DOWN).setScale(2, BigDecimal.ROUND_HALF_UP);
			this.downFlushGb = new BigDecimal(intDownFlush).divide(gbformat,
					RoundingMode.DOWN).setScale(2, BigDecimal.ROUND_HALF_UP);
			// 总流量
			this.totalFlush = new BigDecimal(intUpFlush + intDownFlush);
			this.totalFlushMb = this.totalFlush.divide(mbformat,
					RoundingMode.DOWN).setScale(2, BigDecimal.ROUND_HALF_UP);
			this.totalFlushGb = this.totalFlush.divide(gbformat,
					RoundingMode.DOWN).setScale(2, BigDecimal.ROUND_HALF_UP);
			if (intImsis > 0) {// 平均流量
				BigDecimal dim = new BigDecimal(intImsis).setScale(2,
						BigDecimal.ROUND_HALF_UP);

				this.averageFlush = totalFlush.divide(dim, RoundingMode.DOWN);
				this.averageFlushMb = totalFlush.divide(dim, RoundingMode.DOWN)
						.divide(mbformat, RoundingMode.DOWN).setScale(2,
								BigDecimal.ROUND_HALF_UP);
				this.averageFlushGb = totalFlush.divide(dim, RoundingMode.DOWN)
						.divide(gbformat, RoundingMode.DOWN).setScale(2,
								BigDecimal.ROUND_HALF_UP);
			}
			if (ishour) {
				fullDate = intYear + "-" + intMonth + "-" + intDay + " "
						+ intHour;
				groupDate = intYear + "-" + intMonth + "-" + intDay;
				sectionDate = "" + intHour;
			} else {
				fullDate = intYear + "-" + intMonth + "-" + intDay;
				groupDate = intYear + "-" + intMonth;
				sectionDate = "" + intDay;
			}
			return true;
		} catch (Exception e) {
			log.error("计算统计值发生未知错误！");
			return false;
		}
	}

	// private void createFlushXml() {
	// StringBuffer sb = new StringBuffer("<flush>");
	// /*
	// * 时间
	// */
	//		
	// sb.append("<section>" + sectionDate + "</section>");
	// sb.append("<group>" + groupDate + "</group>");
	//		
	// /*
	// * 业务类型
	// */
	// sb.append("<dimensions>" );
	// if(dimension1!=null){
	// sb.append("<dimension>" );
	// sb.append("<type>" + dimension1.getType() + "</type>");
	// sb.append("<id>" + dimension1.getId() + "</id>");
	// sb.append("<name>" + dimension1.getName() + "</name>");
	// sb.append("</dimension>");
	// }
	// if(dimension2!=null){
	// sb.append("<dimension>" );
	// sb.append("<type>" + dimension2.getType() + "</type>");
	// sb.append("<id>" + dimension2.getId() + "</id>");
	// sb.append("<name>" + dimension2.getName() + "</name>");
	// sb.append("</dimension>");
	// }
	// sb.append("</dimensions>");
	// /*
	// * 上下行流量
	// */
	// sb.append("<upflush>" + intUpFlush + "</upflush>");
	// sb.append("<downflush>" + intDownFlush + "</downflush>");
	// sb.append("<upflushmb>" + upFlushMb + "</upflushmb>");
	// sb.append("<downflushmb>" + downFlushMb + "</downflushmb>");
	// sb.append("<upflushgb>" + upFlushGb + "</upflushgb>");
	// sb.append("<downflushgb>" + downFlushGb + "</downflushgb>");
	// /*
	// * 包数
	// */
	// sb.append("<uppacket>" + intUpPackets + "</uppacket>");
	// sb.append("<downpacket>" + intDownPackets + "</downpacket>");
	// sb.append("<totalpacket>" + totalPackets + "</totalpacket>");
	//
	// /*
	// * 总流量
	// */
	// sb.append("<totalflush>" + totalFlush + "</totalflush>");
	// sb.append("<totalflushmb>" + totalFlushMb + "</totalflushmb>");
	// sb.append("<totalflushgb>" + totalFlushGb + "</totalflushgb>");
	// /*
	// * 平均流量
	// */
	// sb.append("<averageflush>" + averageFlush + "</averageflush>");
	// sb.append("<averageflushmb>" + averageFlushMb + "</averageflushmb>");
	// sb.append("<averageflushgb>" + averageFlushGb + "</averageflushgb>");
	// /*
	// * 用户数
	// */
	// sb.append("<imsis>" + intImsis + "</imsis>");
	// sb.append("</flush>");
	// this.flushXml = sb.toString();
	// }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public BigDecimal getFlushPercent() {
		return flushPercent;
	}

	public void setFlushPercent(BigDecimal flushPercent) {
		this.flushPercent = flushPercent;
	}

	public void setAverageFlush(BigDecimal averageFlush) {
		this.averageFlush = averageFlush;
	}

	public void setAverageFlushMb(BigDecimal averageFlushMb) {
		this.averageFlushMb = averageFlushMb;
	}

	public void setAverageFlushGb(BigDecimal averageFlushGb) {
		this.averageFlushGb = averageFlushGb;
	}

	public void setTotalFlush(BigDecimal totalFlush) {
		this.totalFlush = totalFlush;
	}

}
