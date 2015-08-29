package com.symbol.wp.tools.codecreater.info;

import java.util.ArrayList;

public class CreaterInfo {

	private int codeType;
	private String tableName;
	private String rows;
	private String savePath;
	private ArrayList<String> textType;
	private ArrayList <String> textAreaType;
	private ArrayList <String> selectType;
	private ArrayList<String>  unEditAbleType;
	
	
	
	
	public CreaterInfo()
	{
		textType = new ArrayList<String> ();
		textAreaType = new ArrayList<String> ();
		selectType = new ArrayList<String> ();
		unEditAbleType = new ArrayList<String> ();
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public ArrayList<String> getTextType() {
		return textType;
	}

	public void setTextType(ArrayList<String> textType) {
		this.textType = textType;
	}

	public ArrayList<String> getTextAreaType() {
		return textAreaType;
	}

	public void setTextAreaType(ArrayList <String>textAreaType) {
		this.textAreaType = textAreaType;
	}

	public ArrayList <String> getSelectType() {
		return selectType;
	}

	public void setSelectType(ArrayList <String>selectType) {
		this.selectType = selectType;
	}

	public ArrayList<String> getUnEditAbleType() {
		return unEditAbleType;
	}

	public void setUnEditAbleType(ArrayList<String> unEditAbleType) {
		this.unEditAbleType = unEditAbleType;
	}
}
