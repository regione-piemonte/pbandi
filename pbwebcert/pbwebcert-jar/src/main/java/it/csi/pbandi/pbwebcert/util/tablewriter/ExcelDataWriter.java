/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util.tablewriter;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbwebcert.util.BeanUtil;

public class ExcelDataWriter extends DataWriter {
	@Autowired
	protected BeanUtil beanUtil;
	private HSSFWorkbook wb;
	private HSSFSheet currentSheet;
	private HSSFRow currentRow;
	private HSSFCellStyle generalStyle;
	private HSSFCellStyle amountStyle;
	private HSSFCellStyle dateAndTimeStyle;
	private HSSFCellStyle dateStyle;

	private String tablename;
	private int sheetNum = -1;
	private int rowNum = 0;
	private int cellNum = 0;
	private int maxCellNum = 0;

	public ExcelDataWriter(final String tablename) {
		this(tablename, new HashSet<String>(){{add(tablename);}});
	}

	public ExcelDataWriter(String tablename, Set<String> sheetNames) {
		this.tablename = tablename;

		wb = new HSSFWorkbook();

		// REFERENCE:
		// http://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/BuiltinFormats.html

		amountStyle = this.wb.createCellStyle();
		amountStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));

		generalStyle = this.wb.createCellStyle();
		generalStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("General"));

		dateAndTimeStyle = this.wb.createCellStyle();
		dateAndTimeStyle.setDataFormat(HSSFDataFormat
				.getBuiltinFormat("m/d/yy h:mm"));

		dateStyle = this.wb.createCellStyle();
		dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

		for (String sheetName : sheetNames) {
			this.addSheet(sheetName);
		}
	}

	public void addSheet(String sheetName) {
		this.rowNum = 0;
		this.sheetNum++;
		currentSheet = wb.createSheet();
		this.currentRow = currentSheet.createRow(this.rowNum);
		wb.setSheetName(this.sheetNum, sheetName);
	}

	public void close() throws Exception {
		super.close();

		for (int i = 0; i < maxCellNum; i++) {
			currentSheet.autoSizeColumn((short) i);
		}

		OutputStream out = null;
		if (this.os == null) {
			out = new FileOutputStream(tablename + ".xls");
		} else {
			out = this.os;
		}
		wb.write(out);
		out.close();
	}

	public void nextRecord() throws IOException {
		this.cellNum = 0;
		this.rowNum++;
		this.currentRow = currentSheet.createRow(this.rowNum);
	}

	public <T extends Object> void writeField(T data) throws IOException {
		if (data != null) {
			HSSFCell c = this.currentRow.createCell(this.cellNum);
			try {
				if (BeanUtil.isTypeInteger(data.getClass())) {
					populateCellWithData(c, BeanUtil
							.convert(String.class, data));
				} else if (BeanUtil.isTypeNumeric(data.getClass())) {
					populateCellWithData(c, BeanUtil
							.convert(Double.class, data));
				} else if (BeanUtil.isTypeTime(data.getClass())) {
					populateCellWithData(c, BeanUtil.convert(Date.class, data));
				} else {
					populateCellWithData(c, BeanUtil
							.convert(String.class, data));
				}
			} catch (Exception e) {
				populateCellWithData(c, data.toString());
			}
		}

		if (cellNum == maxCellNum) {
			maxCellNum++;
		}
		this.cellNum++;
	}

	private void populateCellWithData(HSSFCell c, double data) {
		c.setCellValue(data);
		c.setCellStyle(amountStyle);
	}

	private void populateCellWithData(HSSFCell c, Date data) {
		c.setCellValue(data);
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		if (cal.get(Calendar.HOUR) == 0 && cal.get(Calendar.MINUTE) == 0
				&& cal.get(Calendar.SECOND) == 0) {
			c.setCellStyle(dateStyle);
		} else {
			c.setCellStyle(dateAndTimeStyle);
		}
	}

	private void populateCellWithData(HSSFCell c, String data) {
		c.setCellValue(new HSSFRichTextString(data));
		c.setCellStyle(generalStyle);
	}

}
