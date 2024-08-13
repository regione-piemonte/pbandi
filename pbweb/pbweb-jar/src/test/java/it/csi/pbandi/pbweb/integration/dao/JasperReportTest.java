/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.ReportManager;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.ChecklistHtmlAnagraficaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.ChecklistHtmlIrregolaritaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.manager.report.ChecklistItemDTO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@RunWith(PbwebJunitClassRunner.class)
public class JasperReportTest  extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(JasperReportTest.class);

	@Before()
	public void before() {
		LOG.info("[JasperReportTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[JasperReportTest::after]START-STOP");
	}

	@Test
	public void getReportCompilato() { //it.csi.pbandi.pbweb.pbandisrv.business.manager.ReportManager -> getReportCompilato
		/*
		String prf = "[JasperReportTest::getReportCompilato] ";
		LOG.info( "\n \n \n");
    	LOG.info( prf + " START");

    	boolean eseguoTest = false;
    	
    	if(eseguoTest) {
	    	JasperReport report = null;
	    	String nomeReport = "checklistItemDocumentale.jasper";
	    	
	    	try {
				long begin = System.currentTimeMillis();
				LOG.info("cerco il report compilato "+nomeReport+" nella cache");
	//			report = (JasperReport) cacheTemplate.get(nomeReport);
				if (report == null) {
					LOG.debug("report [" + nomeReport + "] non trovato in cache");
	
					// in caso di path assoluto:
					// File f=new File(nomeReport);
					// report=(JasperReport) JRLoader.loadObject(f);
	
					// inserito dentro l'ear
	//				report = (JasperReport) JRLoader.loadObject(this.getClass()
	//						.getClassLoader().getResourceAsStream(nomeReport));
					
					
					
					InputStream resourceAsStream = ReportManager.class.getClassLoader().getResourceAsStream(it.csi.pbandi.pbweb.util.Constants.PATH_FILE_JASPER + nomeReport);
					LOG.debug("resourceAsStream= "+resourceAsStream);
					//pbweb-jar-1.0.0.jar\it\csi\pbandi\pbweb\pbandisrv\business\manager\
					
					
					report = (JasperReport) JRLoader.loadObject(resourceAsStream);
					LOG.debug(">>> report= "+report);
					
					
					JasperReport checklistMasterReport = null;
					List<ChecklistItemDTO> items = new ArrayList<ChecklistItemDTO>();
					ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO = new ChecklistHtmlAnagraficaDTO();
					ChecklistHtmlIrregolaritaDTO checklistHtmlIrregolaritaDTO = new ChecklistHtmlIrregolaritaDTO();
					Map<String, String> paramentersExtra = new HashMap<String, String>();
					
					Map parameters = new HashMap<String, ChecklistHtmlAnagraficaDTO>();
//					String relativeResoursesPath = ResourceBundle.getBundle("conf.server").getString("resourcesPath");
					String relativeResoursesPath = "http://www.sistemapiemonte.it";
					
					JRBeanCollectionDataSource itemsDS = new JRBeanCollectionDataSource(items);
					parameters.put("CHECKLIST_ITEM_SUB", report);
					parameters.put("CHECKLIST_ITEM_DS", itemsDS);
					parameters.put("anagrafica", checkListHtmlAnagraficaDTO);
					parameters.put("irregolarita", checklistHtmlIrregolaritaDTO);
					parameters.put("IMG_PATH", relativeResoursesPath);
					parameters.putAll(paramentersExtra);
					
					LOG.info("checklistMasterReport="+checklistMasterReport);
				
					LOG.info("calling JasperFillManager.fillReport");
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							checklistMasterReport, parameters,
							new JREmptyDataSource());
					
					LOG.debug( prf + "END \n\n\n");
	//				cacheTemplate.put(nomeReport, report);
				}
				LOG.info("Report "+nomeReport+" compilato caricato in "	+ (System.currentTimeMillis() - begin) + " millisec");
			} catch (Exception ex) {
				LOG.error("Errore : " + nomeReport + "\n" + ex.getMessage(), ex);
			} 
	    	
    	}else {
    		LOG.info( prf + " test skippato");
    	}
    	LOG.info( prf + " END");
    	*/
	}
	
}
