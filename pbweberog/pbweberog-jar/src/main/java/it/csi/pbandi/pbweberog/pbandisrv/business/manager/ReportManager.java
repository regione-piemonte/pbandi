/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandisrv.business.manager;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweberog.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweberog.pbandiutil.common.LoggerUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


/**
 * 
 */
public class ReportManager {
	
	@Autowired
	private LoggerUtil logger;


	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}





	private static HashMap<String, JasperReport> cacheTemplate = new HashMap<String, JasperReport>();


	public JasperReport getReportNonCompilato(String nomeReport)
			throws JRException {
		logger.info("carico il report non compilato: " + nomeReport);
		JasperReport report = null;

		long begin = System.currentTimeMillis();
		try {
			logger.debug("cerco il report nella cache");
			report = (JasperReport) cacheTemplate.get(nomeReport);
			if (report == null) {
				logger.info("report non trovato in cache");
				JasperDesign jasperDesign = JRXmlLoader.load(nomeReport);
				logger.info("compilo il report");
				report = JasperCompileManager.compileReport(jasperDesign);
				logger.info("cacho il report " + nomeReport);
				cacheTemplate.put(nomeReport, report);
			} else {
				logger.info("report " + nomeReport + " trovato nella cache");
			}
			logger.info("Report non compilato caricato in "
					+ (System.currentTimeMillis() - begin) + " millisec");
		} catch (Exception ex) {
			logger.error("Errore : " + ex.getMessage(), ex);
		}  
		return report;
	}

	/**
	 * Crea, partendo da un template, un file .xls contenente i dati ottenuti
	 * dalle mappe e dai fogli di calcolo dati come parametri. Utilizza Apache
	 * POI 3.7
	 * 
	 * @param templateKey
	 *            il nome del file di template da usare come base
	 * @param singlePageTables
	 *            file .xls a singola pagina da usare per ricoprire la pagina
	 *            del template col nome dell chiave indicata
	 * @param singleCellVars
	 *            mappa contenente come chiave il nome della pagina e come
	 *            valore una mappa <segnaposto, valore>
	 * @return il file .xls risultate dal merge delle informazioni
	 * @throws IOException
	 *             in caso di errore nell'ottenimento del template, di un file
	 *             .xls dato come parametro, in scrittura del file risultante
	 */
	public byte[] getMergedDocumentFromTemplate(String templateKey,
			Map<String, byte[]> singlePageTables,
			Map<String, Map<String, Object>> singleCellVars) throws IOException {
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
		return out.toByteArray();
	}

	private void populateSheet(Sheet dataSheet, Sheet sheet) {
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
	}

	private static void copyCellValue(Cell from, Cell to) {
		switch (from.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			to.setCellType(Cell.CELL_TYPE_BLANK);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			to.setCellValue(from.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			to.setCellErrorValue(from.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			to.setCellFormula(from.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			to.setCellValue(from.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			to.setCellValue(from.getStringCellValue());
			break;
		default:
			break;
		}
	}

	private void replaceCellValues(Map<String, Object> additionalVars,
			Sheet headerSheet) {
		for (int i = headerSheet.getFirstRowNum(); i <= headerSheet
				.getLastRowNum(); i++) {
			Row dataRow = headerSheet.getRow(i);
			if (dataRow != null) {
				for (int j = dataRow.getFirstCellNum(); j <= dataRow
						.getLastCellNum(); j++) {
					Cell cell = dataRow.getCell(j);
					if (cell != null
							&& cell.getCellType() == Cell.CELL_TYPE_STRING) {
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
	}

	private void setCellValue(Cell cell, Object value) {
		try {
			if (value == null) {
				cell.setCellValue("");
				cell.setCellType(Cell.CELL_TYPE_BLANK);
			} else if (value.getClass().equals(java.math.BigDecimal.class)
					|| value.getClass().equals(java.lang.Double.class)
					|| value.getClass().equals(java.lang.Long.class)) {
				cell.setCellValue(BeanUtil.convert(Double.class, value));
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			} else {
				// Data o testo
				throw new Exception();
			}
		} catch (Exception e) {
			// In mancanza d'altro...
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(value.toString());
		}

	}
	
	
	
	
	
	
	
	 
	
}