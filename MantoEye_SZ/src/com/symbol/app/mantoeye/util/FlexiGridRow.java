package com.symbol.app.mantoeye.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

public class FlexiGridRow implements JSONString {
	private int id;
	private Object cell;
	private List<Object> children = null;
	
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Object getCell() {
		return cell;
	}


	public void setCell(Object cell) {
		this.cell = cell;
	}

	

	public List<Object> getChildren() {
		return children;
	}


	public void setChildren(List<Object> children) {
		this.children = children;
	}

	public void addChild(FlexiGridRow row){
		if(this.children == null) this.children = new ArrayList<Object>();
		this.children.add(row);
	}

	//@Override
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"id\":"+id+",");
		sb.append("\"cell\":"+JSONObject.fromObject(cell).toString());
		if(this.getChildren() != null && this.getChildren().size() > 0)
			sb.append(",\"children\":"+JSONArray.fromObject(this.getChildren()).toString());
		sb.append("}");
		
		return sb.toString();
	}
}
