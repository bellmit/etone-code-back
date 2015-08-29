package com.symbol.app.mantoeye.dto;

import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;

public class TopFlushBean {
	
		private String fulldate;
		private String name;
		private Long flush;
		private Long imsis;
		private int top;
		private Double flushRate;
		private Double imsisRate;
		private String spaceName;
		private Double flushKB;
		private Double flushMB;
		private Double flushGB;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Long getFlush() {
			return flush;
		}
		public void setFlush(Long flush) {
			this.flush = flush;
		}
		public int getTop() {
			return top;
		}
		public void setTop(int top) {
			this.top = top;
		}
		public Double getFlushRate() {
			return flushRate;
		}
		public void setFlushRate(Double flushRate) {
			this.flushRate = flushRate;
		}		
		public Long getImsis() {
			return imsis;
		}
		public void setImsis(Long imsis) {
			this.imsis = imsis;
		}
		public Double getImsisRate() {
			return imsisRate;
		}
		public void setImsisRate(Double imsisRate) {
			this.imsisRate = imsisRate;
		}		
		public Double getFlushKB() {
			return flushKB;
		}
		public void setFlushKB(Double flushKB) {
			this.flushKB = flushKB;
		}
		public Double getFlushMB() {
			return flushMB;
		}
		public void setFlushMB(Double flushMB) {
			this.flushMB = flushMB;
		}
		public Double getFlushGB() {
			return flushGB;
		}
		public void setFlushGB(Double flushGB) {
			this.flushGB = flushGB;
		}
		/**
		 * 计算流量用户数的全网占比
		 */
		public void calculateFlushRate(double sumTotalFlush,double sumImsis){
			flushRate = Common.StringTodouble(CommonUtils.formatPercent(flush, sumTotalFlush));
			imsisRate = Common.StringTodouble(CommonUtils.formatPercent(imsis, sumImsis));
		}
		/**
		 * 计算流量用户数的全网占比
		 */
		public void calculate(){
			flushKB = CommonUtils.formatBToKb(flush);
			flushMB = CommonUtils.formatBToMb(flush);
			flushGB = CommonUtils.formatBToGb(flush);
		}
		public String getFulldate() {
			return fulldate;
		}
		public void setFulldate(String fulldate) {
			this.fulldate = fulldate;
		}
		public String getSpaceName() {
			return spaceName;
		}
		public void setSpaceName(String spaceName) {
			this.spaceName = spaceName;
		}
		
}
