package com.symbol.wp.tools.gtgrid.export;

import java.io.IOException;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.symbol.wp.tools.gtgrid.util.LogHandler;

public class JxlXlsWriter extends AbstractXlsWriter {

	private WritableWorkbook workbook;
	private WritableSheet sheet;
	private WorkbookSettings workbookSettings=new WorkbookSettings();
		
	public void writeNumCell(int row, short col, double value,WritableCellFormat style) throws RowsExceededException, WriteException {
		Number number = new Number(col,row,value,style);		
		sheet.addCell(number);
	}

  
	public void writeStringCell(int row, short col, String value,WritableCellFormat style) throws RowsExceededException, WriteException {
		
        Label label=new Label(col,row,value,style); 
        sheet.addCell(label);
	}

	public void writeCell(int row, short col, Object value,WritableCellFormat style) throws Exception {
		if (isNumber(value)){
			double dNum= Double.parseDouble(String.valueOf(value));
			writeNumCell(row, col, dNum,style);
		}else{
			writeStringCell(row, col, String.valueOf(value),style);
		}
	}
	
	public void writeTitle(int row, short col, Object value,WritableCellFormat style) throws Exception {
		sheet.setColumnView(col, String.valueOf(value).length()<15?15:String.valueOf(value).length());
		Label label=new Label(col,row,String.valueOf(value),style); 
        sheet.addCell(label);
	}
	public String writeMergeTitle(int row, short col, ExcelMergeBean value,WritableCellFormat style) throws Exception {
		int start = value.getStartCol();
		int end = value.getEndCol();	
		int rstart = value.getStartRow();
		int rend = value.getEndRow();
		String  field= value.getField();
		String mergeRows = "";
		if(rstart!=-1&&rend!=-1&&rend>rstart){//有行和列合并
			if(end>start){
				sheet.mergeCells(start,rstart, end, rend);
				sheet.setColumnView(col,(end-start+1)*15 );
				for(int i = start;i<=end;i++){
					mergeRows = mergeRows + i + ",";
				}
				
			}else{
				sheet.mergeCells(col,rstart, col, rend);
				sheet.setColumnView(col,field.length()<15?15:field.length());
				mergeRows = mergeRows + col + ",";
			}
		}else{
			if(end>start){
				sheet.mergeCells(start,row, end, row);
				sheet.setColumnView(col,(end-start+1)*15 );
			}else{
				sheet.setColumnView(col,field.length()<15?15:field.length());
			}
			 
		}
		Label label=new Label(start,row,field,style);			
        sheet.addCell(label);
        return mergeRows;
	}

	public void end() {
		
		try {
			workbook.write();
			workbook.close();
		} catch (WriteException e) {
			LogHandler.error(this,e);
		} catch (IOException e) {
			LogHandler.error(this,e);
		}
	}

	public void start() {
		try {
			
			workbookSettings.setUseTemporaryFileDuringWrite(true);
			if (getEncoding()!=null){
				workbookSettings.setEncoding(getEncoding());
			}			
			
			workbook =Workbook.createWorkbook(getOut(),workbookSettings);
			
			sheet = workbook.createSheet("Sheet 1",0);
		} catch (IOException e) {
			LogHandler.error(this,e);
		}		
	}

}
