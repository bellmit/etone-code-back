package com.symbol.wp.tools.gtgrid.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WritableCellFormat;

public abstract class AbstractXlsWriter {

	private OutputStream out;
	private String encoding=null;
	
	public AbstractXlsWriter() { }
	

	public abstract void start();
	public abstract void end();
	public abstract void writeCell(int row, short col, Object value,WritableCellFormat style) throws Exception ;
	public abstract void writeTitle(int row, short col, Object value,WritableCellFormat style) throws Exception ;
	public abstract String writeMergeTitle(int row, short col, ExcelMergeBean value,WritableCellFormat style) throws Exception ;
	
	private int row=0;
	
	private List errRows=new ArrayList();
	
	
	public List getErrRows() {
		return errRows;
	}


	public void init(){
		setRow(0);
		errRows.clear();
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	public void addTitle(Object[] record,WritableCellFormat style,String mergeRows) {
		boolean haveMergeRow = false;
		System.out.println(mergeRows+"----");
		if(mergeRows!=""){
			List<Integer> mergeRowList = new ArrayList<Integer>();
			String [] mergeRowArray = mergeRows.split(",");
			if(mergeRowArray!=null&&mergeRowArray.length>0){
				for(int i=0;i<mergeRowArray.length;i++){
					int r = -1;
					try{
						r = Integer.parseInt(mergeRowArray[i]);
						mergeRowList.add(r);
					}catch(Exception e){
					}
				}
				for (short i = 0; i < record.length; i++) {
					try {
						if(!mergeRowList.contains(new Integer(i))){
							writeTitle(this.row, i, record[i],style);
						}					
					} catch (Exception e) {
						errRows.add(Integer.valueOf(this.row));
					}
				}
				haveMergeRow = true;
				
			}
		}
		if(!haveMergeRow){
			for (short i = 0; i < record.length; i++) {
				try {
					writeTitle(this.row, i, record[i],style);
				} catch (Exception e) {
					errRows.add(Integer.valueOf(this.row));
				}
			}
		}	
		this.row++;
	}
	public String addMergeTitle(List<ExcelMergeBean> mergeBean,WritableCellFormat style) {
		String mergeRows = "";
		for (short i = 0; i < mergeBean.size(); i++) {
			try {
				mergeRows =mergeRows+writeMergeTitle(this.row, i, mergeBean.get(i),style);
			} catch (Exception e) {
				errRows.add(Integer.valueOf(this.row));
			}
		}
		this.row++;
		if(mergeRows!=""&&mergeRows.endsWith(",")){
			//去掉最后的,号
			mergeRows = mergeRows.substring(0, mergeRows.length()-1);
		}
		return mergeRows;
	}
	public void addRow(Object[] record,WritableCellFormat style) {
		for (short i = 0; i < record.length; i++) {
			try {
				writeCell(this.row, i, record[i],style);
			} catch (Exception e) {
				errRows.add(Integer.valueOf(this.row));
			}
		}
		this.row++;
	}
	
	public void close() {
		try {
			getOut().close();
		} catch (IOException e) {
			// do nothing;
		}		
	}
	

	public OutputStream getOut() {
		return out;
	}
	public void setOut(OutputStream out) {
		this.out = out;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public static boolean isNumber(Object value){
		return value instanceof Number;
	}
	
}
