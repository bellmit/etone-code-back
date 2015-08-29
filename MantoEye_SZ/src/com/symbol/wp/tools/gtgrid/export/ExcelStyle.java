package com.symbol.wp.tools.gtgrid.export;

import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelStyle {

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

	public ExcelStyle() {

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

	private final Logger logger = LoggerFactory.getLogger(ExcelStyle.class);

	public WritableCellFormat getTitle() {
		return title;
	}

	public WritableCellFormat getTable() {
		return table;
	}

	public WritableCellFormat getTableYellow() {
		return tableYellow;
	}

	public WritableCellFormat getBigtable() {
		return bigtable;
	}

	public WritableCellFormat getDetFormat() {
		return detFormat;
	}

	public WritableCellFormat getNumDetFormat() {
		return numDetFormat;
	}

	public WritableCellFormat getTotalFormat() {
		return totalFormat;
	}

	public WritableCellFormat getAllTotalFormat() {
		return allTotalFormat;
	}
}
