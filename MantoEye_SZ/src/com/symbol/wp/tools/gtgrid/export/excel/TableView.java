package  com.symbol.wp.tools.gtgrid.export.excel;

import java.util.ArrayList;
import java.util.List;

public class TableView {

	private String topic = null; // 主题

	private String titles = null;// 格式用","分开
	
	private List beanList = new ArrayList();

	public List getBeanList() {
		return beanList;
	}

	public void setBeanList(List beanList) {
		this.beanList = beanList;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

}
