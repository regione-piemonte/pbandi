/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.business.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;		// vale solo con poi-5.0.0
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbwebcert.util.BeanUtil;
import it.csi.pbandi.pbwebcert.util.Constants;

@Component
public class ReportManager {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;

	
	public byte[] getMergedDocumentFromTemplate(String templateKey, Map<String, byte[]> singlePageTables,
			Map<String, Map<String, Object>> singleCellVars) throws IOException {
		String prf = "[ReportManager::getMergedDocumentFromTemplate]";
		LOG.info(prf + " BEGIN");
		InputStream resourceAsStream = this.getClass().getClassLoader()
				.getResourceAsStream("xls_templates/" + templateKey + ".xls");
		Workbook templateWb = new HSSFWorkbook(resourceAsStream);
       
		if (singlePageTables != null && singlePageTables.size() > 0) {
			// Fase di riempimento di pagine con fogli singoli
			for (String key : singlePageTables.keySet()) {
				Workbook singlePageTable = new HSSFWorkbook(
						new ByteArrayInputStream(singlePageTables.get(key)));
				Sheet sheet = singlePageTable.getSheetAt(0);
				Sheet templateSheet = templateWb.getSheet(key);
				LOG.info(prf + " sheet: "+ sheet.toString());
				LOG.info(prf + " templateSheet: "+ templateSheet.toString());
				populateSheet(sheet, templateSheet);
			}
		}

		if (singleCellVars != null && !singleCellVars.isEmpty()) {
			// Fase di replace dei segnaposto $variabile
			for (String key : singleCellVars.keySet()) {
				replaceCellValues(singleCellVars.get(key),
						templateWb.getSheet(key));
			}
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		templateWb.write(out);
		LOG.info(prf + " END");
		return out.toByteArray();
	}

	private void populateSheet(Sheet dataSheet, Sheet sheet) {
		String prf = "[ReportManager::populateSheet]";
		LOG.info(prf + " BEGIN");
		for (int i = dataSheet.getFirstRowNum(); i <= dataSheet.getLastRowNum(); i++) {
			Row dataRow = dataSheet.getRow(i);
			if (dataRow != null) {
				Row row = sheet.getRow(i);
				if (row == null) {
					row = sheet.createRow(i);
				}
				for (int j = dataRow.getFirstCellNum(); j <= dataRow
						.getLastCellNum(); j++) {
					Cell from = dataRow.getCell(j);
					Cell to = row.getCell(j);
					if (from != null) {
						copyCellValue(from, to != null ? to : row.createCell(j));
					}
				}
			}
		}
		LOG.info(prf + " END");
	}
	
	private void replaceCellValues(Map<String, Object> additionalVars,
			Sheet headerSheet) {
		/*   vale solo con poi-5.0.0
		String prf = "[ReportManager::replaceCellValues]";
		LOG.info(prf + " BEGIN");
		for (int i = headerSheet.getFirstRowNum(); i <= headerSheet
				.getLastRowNum(); i++) {
			Row dataRow = headerSheet.getRow(i);
			if (dataRow != null) {
				for (int j = dataRow.getFirstCellNum(); j <= dataRow
						.getLastCellNum(); j++) {
					Cell cell = dataRow.getCell(j);
					if (cell != null
							&& cell.getCellType() == CellType.STRING) {
						String cellValue = cell.getStringCellValue();
						if (cellValue.contains("$")) {
							String token = cellValue.split("\\$")[1];
							if (additionalVars.containsKey(token)) {
								setCellValue(cell, additionalVars.get(token));
							}
						}
					}
				}
			}
		}
		LOG.info(prf + " END");
		*/
	}
	
	private void setCellValue(Cell cell, Object value) {
		try {
			if (value == null) {
				cell.setCellValue("");
//				cell.setCellType(CellType.BLANK);
			} else if (value.getClass().equals(java.math.BigDecimal.class)
					|| value.getClass().equals(java.lang.Double.class)
					|| value.getClass().equals(java.lang.Long.class)) {
				cell.setCellValue(BeanUtil.convert(Double.class, value));
//				cell.setCellType(CellType.NUMERIC);
			} else {
				// Data o testo
				throw new Exception();
			}
		} catch (Exception e) {
			// In mancanza d'altro...
//			cell.setCellType(CellType.STRING);
			cell.setCellValue(value.toString());
		}

	}
	
	private static void copyCellValue(Cell from, Cell to) {
		/*  vale solo con poi-5.0.0
		switch (from.getCellType()) {
		case BLANK:
			to.setCellType(CellType.BLANK);
			break;
		case BOOLEAN:
			to.setCellValue(from.getBooleanCellValue());
			break;
		case ERROR:
			to.setCellErrorValue(from.getErrorCellValue());
			break;
		case FORMULA:
			to.setCellFormula(from.getCellFormula());
			break;
		case NUMERIC:
			to.setCellValue(from.getNumericCellValue());
			break;
		case STRING:
			to.setCellValue(from.getStringCellValue());
			break;
		default:
			break;
		}
		*/
	}

	
	
}
