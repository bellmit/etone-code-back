package com.symbol.wp.tools.gtgrid.export.excel;

import java.util.ArrayList;
import java.util.Calendar;

import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateExcel {

	public ArrayList publictitle = new ArrayList();

	public Label l;

	public WritableSheet sheet;

	public WritableFont whiteFont = new WritableFont(WritableFont.ARIAL, 10,
			WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
			jxl.format.Colour.WHITE);
	public WritableFont blackFont = new WritableFont(WritableFont.ARIAL, 10,
			WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
			jxl.format.Colour.BLACK);
	public WritableFont tableFont = new WritableFont(WritableFont.ARIAL, 10,
			WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
			jxl.format.Colour.BLACK);
	public WritableFont tableFontYellow = new WritableFont(WritableFont.ARIAL,
			10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
			jxl.format.Colour.BLACK);
	public WritableFont bigtableFont = new WritableFont(WritableFont.ARIAL, 10,
			WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
			jxl.format.Colour.BLACK);
	public WritableFont whiteFont_Bold = new WritableFont(WritableFont.ARIAL,
			12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
			jxl.format.Colour.WHITE);
	public WritableFont blackFont_Bold = new WritableFont(WritableFont.ARIAL,
			12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
			jxl.format.Colour.BLACK);

	public WritableCellFormat title = new WritableCellFormat(blackFont_Bold);

	public WritableCellFormat table = new WritableCellFormat(tableFont);

	public WritableCellFormat tableYellow = new WritableCellFormat(
			tableFontYellow);

	public WritableCellFormat bigtable = new WritableCellFormat(bigtableFont);

	public WritableCellFormat detFormat = new WritableCellFormat(blackFont);

	public WritableCellFormat numDetFormat = new WritableCellFormat(blackFont);

	public WritableCellFormat totalFormat = new WritableCellFormat(blackFont);

	public WritableCellFormat allTotalFormat = new WritableCellFormat(whiteFont);

	public TemplateExcel() {
		publictitle = new ArrayList();
		l = null;
		sheet = null;

		try {
			title.setAlignment(jxl.format.Alignment.CENTRE);
			title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			title.setWrap(false);
			title.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, Colour.BLACK);
			title.setBackground(Colour.GRAY_25);

			table.setAlignment(jxl.format.Alignment.RIGHT);
			table.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			table.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, Colour.BLACK);
			table.setWrap(false);

			tableYellow.setAlignment(jxl.format.Alignment.LEFT);
			tableYellow
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			tableYellow.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, Colour.BLACK);
			tableYellow.setBackground(Colour.YELLOW);

			bigtable.setAlignment(jxl.format.Alignment.CENTRE);
			bigtable.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			bigtable.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, Colour.BLACK);
			bigtable.setWrap(false);

			detFormat.setAlignment(jxl.format.Alignment.CENTRE);
			detFormat.setWrap(false);
			detFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			detFormat.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, Colour.BLACK);
			detFormat.setBackground(Colour.WHITE);

			numDetFormat.setAlignment(jxl.format.Alignment.RIGHT);
			numDetFormat.setWrap(false);
			numDetFormat
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			numDetFormat.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, Colour.BLACK);
			numDetFormat.setBackground(Colour.WHITE);

			totalFormat.setAlignment(jxl.format.Alignment.CENTRE);
			totalFormat.setWrap(false);
			totalFormat.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, Colour.BLACK);
			totalFormat.setBackground(Colour.GRAY_25);

			allTotalFormat.setAlignment(jxl.format.Alignment.RIGHT);
			allTotalFormat.setWrap(false);
			allTotalFormat.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, Colour.BLACK);
			allTotalFormat.setBackground(Colour.LIGHT_ORANGE);
		} catch (WriteException e) {
			logger.error(e.getMessage());
		}
	}

	private final Logger logger = LoggerFactory.getLogger(TemplateExcel.class);

	public String FileName() {
		String filename = null;
		Calendar today = Calendar.getInstance();
		int year = today.get(1);
		int month = today.get(2);
		month++;
		String strMonth = null;
		if (month < 10)
			strMonth = "0" + String.valueOf(month);
		else
			strMonth = String.valueOf(month);
		int day = today.get(5);
		String strDay = null;
		if (day < 10)
			strDay = "0" + String.valueOf(day);
		else
			strDay = String.valueOf(day);
		int hour = today.get(11);
		int minutes = today.get(12);
		int second = today.get(13);
		filename = String.valueOf(year) + strMonth + strDay
				+ String.valueOf(hour) + String.valueOf(minutes)
				+ String.valueOf(second)
				+ String.valueOf((int) (100D * Math.random()));
		return filename;
	}

	public void TitleInput(String titleinput) {
		if (publictitle == null)
			publictitle = new ArrayList();
		publictitle.add(titleinput);
	}

	public void PasteSQL(String s, String s1, Colour colour, int i, int j,
			int k, int i1) {
	}

	public void PasteString(String str, WritableFont font, Colour bg,
			int startrow, int startcol, int endrow, int endcol)
			throws RowsExceededException, WriteException {
		font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD,
				false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat fontFormat = new WritableCellFormat(font);
		fontFormat.setBackground(bg);
		fontFormat.setAlignment(jxl.format.Alignment.CENTRE);
		sheet.mergeCells(startrow, startcol, endrow, endcol);
		l = new Label(startrow, startcol, str, fontFormat);
		sheet.addCell(l);
	}

	public void MakeDataBoder(int beginRow) throws WriteException {
		WritableFont columnNameFont = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.WHITE);
		WritableCellFormat columnNameFormat = new WritableCellFormat(
				columnNameFont);
		columnNameFormat.setAlignment(jxl.format.Alignment.CENTRE);
		columnNameFormat.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THIN, Colour.BLACK);
		columnNameFormat.setBackground(Colour.GREEN);
		int column = 0;
		for (int i = 0; i < publictitle.size(); i++) {
			l = new Label(column++, beginRow, publictitle.get(i).toString(),
					columnNameFormat);
			sheet.addCell(l);
		}

	}

	public void addNullCells(WritableSheet sheet, int row, int startCell,
			int cellNum, WritableCellFormat format) throws WriteException {
		for (int i = startCell; i < startCell + cellNum; i++) {
			sheet.addCell(new Label(i, row, "", format));
		}
	}

	/**
	 * 打印标题
	 * 
	 * @param beginRow
	 * @param title
	 * @throws WriteException
	 */
	public void printTitle(int beginRow, WritableCellFormat title)
			throws WriteException {
		int column = 0;
		for (int i = 0; i < publictitle.size(); i++) {
			sheet.setColumnView(column, String.valueOf(publictitle.get(i))
					.length() < 25 ? 25 : String.valueOf(publictitle.get(i))
					.length());
			l = new Label(column++, beginRow, String
					.valueOf(publictitle.get(i)), title);
			sheet.addCell(l);
		}

	}

	public void printTitle(int beginCol, int beginRow, WritableCellFormat title)
			throws WriteException {
		for (int i = 0; i < publictitle.size(); i++, beginCol++) {
			sheet.setColumnView(beginCol, String.valueOf(publictitle.get(i))
					.length() < 25 ? 25 : String.valueOf(publictitle.get(i))
					.length());
			l = new Label(beginCol, beginRow, publictitle.get(i).toString(),
					title);
			sheet.addCell(l);
		}

	}

	public void printTitle(int beginCol, int beginRow, String title,
			WritableCellFormat style) throws WriteException {
		sheet
				.setColumnView(beginCol, title.length() < 25 ? 25 : title
						.length());
		l = new Label(beginCol, beginRow, title, style);
		sheet.addCell(l);
	}

	public void printTitle(int beginCol, int beginRow, int width, String title,
			WritableCellFormat style) throws WriteException {
		System.out.println("width:" + width);
		sheet.setColumnView(beginCol, width);
		l = new Label(beginCol, beginRow, title, style);
		sheet.addCell(l);
	}
}
