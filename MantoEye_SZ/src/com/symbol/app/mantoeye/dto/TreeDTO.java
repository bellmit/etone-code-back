package com.symbol.app.mantoeye.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONString;

import net.sf.json.JSONArray;

public class TreeDTO implements JSONString{

	private String id;
	private String value;
	
	private List<TreeDTO> rows;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public List<TreeDTO> getRows() {
		return rows;
	}
	public void setRows(List<TreeDTO> rows) {
		this.rows = rows;
	}
	
	public void Add(DimenDto dimenDto){
		if(rows == null)rows = new ArrayList<TreeDTO>();
		rows.add(dimenDto);
	}	
	
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"id\":"+id);
		sb.append(",\"value\":"+value);
		if(rows != null && rows.size() > 0){
			sb.append(",\"rows\":"+JSONArray.fromObject(rows).toString());
		}
		sb.append("}");
		return sb.toString();
	}
}
