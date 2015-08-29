package com.symbol.app.mantoeye.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

public class FlexiGridDataContainer {

	private int page;
	private List rows;
	private int index;
	private int total;
	
	
	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public FlexiGridDataContainer(){
		index = 1;
		rows = new ArrayList();

	}

	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public void addData(Object obj){
		FlexiGridRow row = new FlexiGridRow();
		row.setId(index++);
		row.setCell(obj);
		addRow(row);
	}
	
	public void addRow(Object row){
		rows.add(row);
	}
	
	public void addData(List list){
		for(Object obj : list){
			addData(obj);
		}
	}
	
	public String toJSONString(){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"total\":"+getTotal()+", ");
		sb.append("\"page\":"+getPage()+", ");
		sb.append("\"rows\":"+JSONArray.fromObject(rows).toString());
		sb.append("}");
		return sb.toString();
	}
	
	
}

