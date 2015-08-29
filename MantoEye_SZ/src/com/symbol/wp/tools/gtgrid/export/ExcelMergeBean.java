package com.symbol.wp.tools.gtgrid.export;

public class ExcelMergeBean {
	
	//开始列 （0开始）
	private int startCol;
	
	//结束列 （0开始）
	private int endCol;
	
	private int startRow = -1;
	
	private int endRow = -1;
	
	//内容
	private String field;
	
	public ExcelMergeBean(){
		
	}
	public ExcelMergeBean(int startCol,int endCol,String field){
		this.startCol = startCol;
		this.endCol = endCol;
		this.field = field;
	}
	public ExcelMergeBean(int startCol,int endCol,int startRow,int endRow,String field){
		this.startCol = startCol;
		this.endCol = endCol;
		this.field = field;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	
}
