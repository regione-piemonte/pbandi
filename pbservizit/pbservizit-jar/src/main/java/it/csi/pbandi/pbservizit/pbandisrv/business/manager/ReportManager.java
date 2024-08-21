/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.PropostaRimodulazioneReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.RimodulazioneReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.DocumentoContabileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.dichiarazionedispesa.VoceDiCostoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.erogazione.DichiarazioneDiRinunciaReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoItemDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.EstremiBancariDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.FonteFinanziaria;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ModalitaAgevolazione;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistAffidamentoHtmlFasiDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistHtmlAnagraficaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistHtmlIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.ChecklistItemDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneReportDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.TipoAllegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.FaseMonitoraggioProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IndicatoreDomandaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.dichiarazionedispesa.DocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.erogazione.ErogazioneReportVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;


/**
 * 
 */
public class ReportManager {
	
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private BeanUtil beanUtil;

	private RegolaManager regolaManager;
	@Autowired
	private ProgettoManager progettoManager;

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setRegolaManager(RegolaManager regolaManager) {
		this.regolaManager = regolaManager;
	}

	public RegolaManager getRegolaManager() {
		return regolaManager;
	}

	public ProgettoManager getProgettoManager() {
		return progettoManager;
	}

	public void setProgettoManager(ProgettoManager progettoManager) {
		this.progettoManager = progettoManager;
	}



	private static HashMap<String, JasperReport> cacheTemplate = new HashMap<String, JasperReport>();

//	JasperReport report = null;
//	private Resource[] reports;

	// PK : rimuovo perche senno non compila.... No qualifying bean of type 'org.springframework.core.io.Resource[]' available:
//	public ReportManager(Resource... nomeReports) {
//		this.reports = nomeReports;
//	}

//	public void init() throws JRException {
//		try {
//			long begin = System.currentTimeMillis();
//			for (Resource r : reports) {
//				logger.debug("carico il nomeReport  compilato: " + r);
//				report = cacheTemplate.get(r.toString());
//
//				if (report == null) {
//					logger.debug("report non trovato in cache");
//
//					/*
//					 * //in caso di path assoluto: File f=new File(nomeReport);
//					 * report=(JasperReport) JRLoader.loadObject(f);
//					 */
//
//					// inserito dentro l'ear
//					report = (JasperReport) JRLoader.loadObject(r
//							.getInputStream());
//					logger.debug("cacho il report");
//					cacheTemplate.put(r.getFilename(), report);
//				}
//				logger.debug("Report compilato caricato in "
//						+ (System.currentTimeMillis() - begin) + " millisec");
//			}
//			
//			/*this.createDichiarazioneDiSpesaPDF(new DichiarazioneDiSpesaDTO(),
//					true, true, true, true,
//					new DichiarazioneDiSpesaReportDTO(),
//					new VoceDiCostoDTO[] {}, new DocumentoContabileDTO[] {});
//			*/
//		} catch (Exception ex) {
//			logger.error("Errore : " + ex.getMessage(), ex);
//		} finally {
//			reports = null;
//		}
//	}

	/**
	 * USATO SOLO PER TEST
	 * 
	 * @param nomeReport
	 * @return
	 */
//	public byte[] getReport(String nomeReport) {
//		logger.debug("report : " + report);
//		byte[] pdfBytes = null;
//		JasperPrint jasperPrint = null;
//
//		try {
//
//			/*
//			 * SOLO PER PROVA
//			 * 
//			 * if(report==null){ logger.debug("report null,lo carico"); report
//			 * =getReportCompilato(nomeReport); }else{
//			 * logger.debug("report gi� cachato"); }
//			 */
//
//			Map parameters = new HashMap();
//			riempiMappaDeiParametri(parameters);
//			// parameters.put("field2","DICHIARAZIONE DI SPESA");
//			// parameters.put("field3","DICHIARAZIONE DI SPESA");
//			// parameters.put("","");
//			DocumentoDiSpesaVO[] documentiDiSpesaVO = getDocumentiDiSpesaFake();
//
//			JRDataSource dataSource = new JRBeanArrayDataSource(
//					documentiDiSpesaVO);
//
//			logger.info("popolo il report con i dati");
//			jasperPrint = JasperFillManager.fillReport(report, parameters,
//					dataSource);
//
//			// jasperPrint =JasperFillManager.fillReport(report,parameters);
//			logger.info("esporto il report in formato pdf (byte[])");
//
//			pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
//
//			logger.info("il report contiene " + pdfBytes.length + " byte");
//
//			// TODO per test
//			// JasperExportManager.exportReportToPdfFile(jasperPrint,
//			// "d:\\report.pdf");
//			// JasperExportManager.exportReportToHtmlFile(jasperPrint,
//			// "d:\\prova2.html");
//			// JasperViewer.viewReport(jasperPrint);
//
//		} catch (JRException ex) {
//			logger.error("Errore: " + ex.getMessage(), ex);
//			RuntimeException re = new RuntimeException();
//			re.initCause(ex);
//			throw re;
//		} 
//		if (pdfBytes == null) {
//			RuntimeException re = new RuntimeException(
//					"Errore: il report generato � null");
//			throw re;
//		}
//		return pdfBytes;
//	}

	// parameters.put("ReportTitle", "SCR Report");
	// parameters.put("BaseDir", reportFile.getParentFile());

//	private void riempiMappaDeiParametri(Map parameters) {
//		parameters.put("destinatario",
//				"Regione Piemonte, Direzione delle attivit� produttive");
//		parameters.put("destinatarioIndirizzo",
//				"Piazza Castello 12 - 10100  - Torino");
//		Calendar calendar = new GregorianCalendar(2008, 0, 13);
//		Date d1 = calendar.getTime();
//		calendar.set(2009, 4, 12);
//		Date d2 = calendar.getTime();
//		String dataInizio = DateUtil.formatDate(d1);
//		String dataFine = DateUtil.formatDate(d1);
//		parameters.put("dataInizioRendicontazione", dataInizio);
//		parameters.put("dataFineRendicontazione", dataFine);
//		parameters.put("determinazione", "PROVA DETERMINAZIONE");
//		parameters.put("numeroContributo", "189456");
//		parameters.put("dataContributo", "01/12/1999");
//		parameters.put("denominazioneIntervento",
//				" prova denominazione intervento");
//		parameters.put("codiceProgetto", " prova codiceProgetto 123");
//		parameters.put("sottoscritto", " xxx ");
//		parameters.put("comuneNascita", " TORINO");
//		parameters.put("dataNascita", "01/01/2000");
//		parameters.put("comuneResidenza", " TORINO");
//		parameters.put("provinciaResidenza", "(TO)");
//		parameters.put("societa", " Societa di prova");
//
//	}

//	private DocumentoDiSpesaVO[] getDocumentiDiSpesaFake() {
//		List<DocumentoDiSpesaVO> list = new ArrayList<DocumentoDiSpesaVO>();
//		DocumentoDiSpesaVO doc1 = new DocumentoDiSpesaVO();
//		doc1.setCodiceFiscaleFornitore("ABCDEFGDSFSDG123");
//		doc1.setIdDocDiRiferimento(2224353L);
//		DocumentoDiSpesaVO doc2 = new DocumentoDiSpesaVO();
//		doc2.setCodiceFiscaleFornitore("KHJQY213Z4545X");
//		doc2.setIdDocDiRiferimento(687432345L);
//		DocumentoDiSpesaVO doc3 = new DocumentoDiSpesaVO();
//		doc3.setCodiceFiscaleFornitore("123rt33KHJQYZX");
//		doc3.setIdDocDiRiferimento(123687345L);
//		list.add(doc1);
//		list.add(doc2);
//		list.add(doc3);
//		return (DocumentoDiSpesaVO[]) list.toArray(new DocumentoDiSpesaVO[list
//				.size()]);
//
//	}

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
				logger.debug("report " + nomeReport + " trovato nella cache");
			}
			logger.debug("Report non compilato caricato in "
					+ (System.currentTimeMillis() - begin) + " millisec");
		} catch (Exception ex) {
			logger.error("Errore : " + ex.getMessage(), ex);
		}  
		return report;
	}

	public JasperDesign getReportJRXML(String nomeReport) throws Exception {
		logger.info("carico il report non compilato: " + nomeReport);
		JasperDesign jasperDesign = null;
		long begin = System.currentTimeMillis();
		try {
			jasperDesign = JRXmlLoader.load(this.getClass().getClassLoader()
					.getResourceAsStream("report/"+nomeReport));
			logger.info("Report non compilato caricato in "
					+ (System.currentTimeMillis() - begin) + " millisec");
		} catch (Exception ex) {
			logger.error("Errore : " + ex.getMessage(), ex);
			throw ex;
		}
		return jasperDesign;
	}

	public JasperReport getReportCompilato(String nomeReport)
			throws JRException {
		logger.begin();
		logger.debug("nomeReport compilato: " + nomeReport);
		JasperReport report = null;

		try {
			long begin = System.currentTimeMillis();
			logger.info("cerco il report compilato "+nomeReport+" nella cache");
			report = (JasperReport) cacheTemplate.get(nomeReport);
			if (report == null) {
				logger.debug("report [" + nomeReport + "] non trovato in cache");

				// in caso di path assoluto:
				// File f=new File(nomeReport);
				// report=(JasperReport) JRLoader.loadObject(f);

				// inserito dentro l'ear // OLD \pbandisrv\conf\report
//				InputStream resourceAsStream2 = (InputStream)this.getClass().getClassLoader().getResourceAsStream(it.csi.pbandi.pbservizit.util.Constants.PATH_FILE_JASPER + nomeReport);
//				logger.debug("resourceAsStream2= "+resourceAsStream2);

				InputStream resourceAsStream = ReportManager.class.getClassLoader().getResourceAsStream(it.csi.pbandi.pbservizit.util.Constants.PATH_FILE_JASPER  + nomeReport); 
				logger.debug("resourceAsStream= "+resourceAsStream);
				
				if(resourceAsStream==null) {
					logger.error("file [" + nomeReport + "] non trovato dentro l'ear");
				}
				report = (JasperReport) JRLoader.loadObject(resourceAsStream);
				
				logger.debug("cacho il report " + nomeReport);
				cacheTemplate.put(nomeReport, report);
			}
			logger.info("Report "+nomeReport+" compilato caricato in "
					+ (System.currentTimeMillis() - begin) + " millisec");
		} catch (Exception ex) {
			logger.error("Errore : " + nomeReport + "\n" + ex.getMessage(), ex);
		} 
		logger.end();
		return report;
	}

	public JasperReport getReportProva(String nomeReport) {
		JasperReport report = (JasperReport) cacheTemplate.get(nomeReport);
		return report;
	}

	public byte[] createDichiarazioneDiSpesaPDF(
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, boolean isBozza,
			boolean isBr02, boolean isBr05, boolean isBR16,
			DichiarazioneDiSpesaReportDTO dichiarazione,
			VoceDiCostoDTO[] vociDiCosto,
			DocumentoContabileDTO[] documentiContabili) throws Exception {
		byte[] result = null;
		JasperReport reportMaster;
		JasperReport reportVociCosto;
		JasperReport reportDocumentiContabili;
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		watcher.start();
		try {
			long startCaricaReport = System.currentTimeMillis();
			reportMaster = getReportCompilato("DichiarazioneDiSpesa.jasper");
			reportVociCosto = getReportCompilato("DichiarazioneDiSpesa_VociDiCosto.jasper");
			reportDocumentiContabili = getReportCompilato("DichiarazioneDiSpesa_DocumentiContabili.jasper");
			logger.info("+++++++++++++++ report caricati in : "
					+ (System.currentTimeMillis() - startCaricaReport) + " ms");

			Map<String, Object> parameters = new HashMap<String, Object>();
			/**
			 * Dati per il subreport delle voci di costo
			 */
			JRBeanCollectionDataSource vociDS = new JRBeanCollectionDataSource(
					Arrays.asList(vociDiCosto));
			parameters.put("VOCI_SUB", reportVociCosto);
			parameters.put("VOCI_DS", vociDS);

			/**
			 * Dati per il subreport dei documenti contabili
			 */
			JRBeanCollectionDataSource documentiContabiliDS = new JRBeanCollectionDataSource(
					Arrays.asList(documentiContabili));
			parameters.put("DOC_CONT_SUB", reportDocumentiContabili);
			parameters.put("DOC_CONT_DS", documentiContabiliDS);

			parameters.put("dichiarazione", dichiarazione);
			parameters.put("IS_BR02", isBr02);
			parameters.put("IS_BR05", isBr05);
			parameters.put("IS_BOZZA", new Boolean(isBozza));
			parameters.put("IS_BR16", isBR16);
			caricaImmagine(parameters);
			long startFillReport = System.currentTimeMillis();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportMaster, parameters, new JREmptyDataSource());
			logger.info("+++++++++++++++ fillReport  in : "
					+ (System.currentTimeMillis() - startFillReport) + " ms");

			long exportReportToPdf = System.currentTimeMillis();
			result = JasperExportManager.exportReportToPdf(jasperPrint);
			logger.info("+++++++++++++++ exportReportToPdf in : "
					+ (System.currentTimeMillis() - exportReportToPdf) + " ms");
		} catch (Exception e) {
			watcher.stop();
			watcher.dumpElapsed(this.getClass().getName(), "createReport",
					"ReportManager::createReport", "id progetto "
							+ dichiarazioneDiSpesaDTO.getIdProgetto());
			logger.error(e.getMessage(), e);
			throw e;
		}

		return result;
	}

	public byte[] createRichiestaErogazionePDF(
			RichiestaErogazioneReportDTO richiestaErogazioneReportDTO)
			throws Exception {
		/*
		 * CREAZIONE REPORT
		 */
		byte[] result = null;
		JasperReport erogazioneReport = getReportCompilato("RichiestaErogazione.jasper");
		JasperReport fideiussioniReport = getReportCompilato("RichiestaErogazione_Fideiussioni.jasper");
		JasperReport allegatiReport = getReportCompilato("RichiestaErogazione_Allegati.jasper");
		Map parameters = new HashMap<String, RichiestaErogazioneReportDTO>();
		parameters.put("RICHIESTA_EROGAZIONE", richiestaErogazioneReportDTO);

		/*
		 * Creazione del sub_report FIDEIUSSIONI
		 */
		Collection<FideiussioneDTO> fideiussioni = null;
		if (richiestaErogazioneReportDTO.getFideiussioni() != null
				&& richiestaErogazioneReportDTO.getFideiussioni().length > 0) {
			fideiussioni = Arrays.asList(richiestaErogazioneReportDTO
					.getFideiussioni());
		} else {
			fideiussioni = new ArrayList();
		}
		JRBeanCollectionDataSource fideiussioniDS = new JRBeanCollectionDataSource(
				fideiussioni);
		parameters.put("FIDEIUSSIONI_SUB", fideiussioniReport);
		parameters.put("FIDEIUSSIONI_DS", fideiussioniDS);

		/*
		 * Creazione del sub_report ALLEGATI
		 */
		Collection<TipoAllegatoDTO> allegati = null;
		if (richiestaErogazioneReportDTO.getAllegati() != null
				&& richiestaErogazioneReportDTO.getAllegati().length > 0) {
			allegati = Arrays
					.asList(richiestaErogazioneReportDTO.getAllegati());
		} else {
			allegati = new ArrayList();
		}

		JRBeanCollectionDataSource allegatiDS = new JRBeanCollectionDataSource(
				allegati);
		parameters.put("ALLEGATI_SUB", allegatiReport);
		parameters.put("ALLEGATI_DS", allegatiDS);

		/*
		 * Creazione pdf
		 */
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				erogazioneReport, parameters, new JREmptyDataSource());

		result = JasperExportManager.exportReportToPdf(jasperPrint);

		return result;

	}

	public byte[] createPropostaDiRimodulazionePDF(
			PropostaRimodulazioneReportDTO propostaRimodulazioneDTO,
			List<ContoEconomicoItemDTO> contoEconomicoItem,
			List<ModalitaAgevolazione> modalitaAgevolazione) throws Exception {
		
		logger.begin();
		byte[] result = null;
		JasperReport propostaDiRimodulazioneReport = getReportCompilato("PropostaDiRimodulazione.jasper");

		JasperReport contoEconomicoReport = getReportCompilato("PropostaDiRimodulazione_ContoEconomico.jasper");
		
		JasperReport modalitaAgevolazioneReport = getReportCompilato("PropostaDiRimodulazione_ModalitaAgevolazione.jasper");

		Map parameters = new HashMap<String, PropostaRimodulazioneReportDTO>();

		parameters.put("proposta", propostaRimodulazioneDTO);

		/*
		 * Creazione del sub_report contoEconomico
		 */

		JRBeanCollectionDataSource listContoEconomicoItemDS = new JRBeanCollectionDataSource(
				contoEconomicoItem);

		parameters.put("CONTO_ECONOMICO_SUB", contoEconomicoReport);
		parameters.put("CONTO_ECONOMICO_DS", listContoEconomicoItemDS);

		JRBeanCollectionDataSource listModalitaDS = new JRBeanCollectionDataSource(
				modalitaAgevolazione);

		parameters.put("MODALITA_AGEVOLAZIONE_SUB", modalitaAgevolazioneReport);

		parameters.put("MODALITA_AGEVOLAZIONE_DS", listModalitaDS);

		/*
		 * Creazione pdf
		 */
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				propostaDiRimodulazioneReport, parameters,
				new JREmptyDataSource());

		result = JasperExportManager.exportReportToPdf(jasperPrint);
		logger.end();
		
		return result;
	}

	public byte[] createRimodulazionePDF(
			RimodulazioneReportDTO rimodulazioneReportDTO,
			List<ContoEconomicoItemDTO> contoEconomico,
			List<ModalitaAgevolazione> modalitaAgevolazione,
			List<FonteFinanziaria> fontiFinanziarie) throws Exception {
		logger.begin();
		byte[] result = null;
		JasperReport rimodulazioneReport = getReportCompilato("Rimodulazione.jasper");


		// PK in fase di "rimodulazione del conto economico" non sembra passare da qui
		JasperReport contoEconomicoReport = getReportCompilato("Rimodulazione_ContoEconomico.jasper");

		// risalire alla normativa?? BR58A welfae abitativo
		
		logger.debug("IdContoEconomico = " + rimodulazioneReportDTO.getIdContoEconomico()); 
		logger.debug("IdProgetto = " +rimodulazioneReportDTO.getIdProgetto());

		
		JasperReport modalitaAgevolazioneReport = getReportCompilato("Rimodulazione_ModalitaAgevolazione.jasper");

		JasperReport fontiFinanziarieReport = getReportCompilato("Rimodulazione_FontiFinanziarie.jasper");

		Map parameters = new HashMap<String, PropostaRimodulazioneReportDTO>();

		parameters.put("rimodulazione", rimodulazioneReportDTO);

		/*
		 * Creazione dei sub_report
		 */

		JRBeanCollectionDataSource contoEconomicoItemDS = new JRBeanCollectionDataSource(contoEconomico);

		parameters.put("CONTO_ECONOMICO_RIM_SUB", contoEconomicoReport);
		parameters.put("CONTO_ECONOMICO_RIM_DS", contoEconomicoItemDS);

		JRBeanCollectionDataSource modalitaDS = new JRBeanCollectionDataSource(	modalitaAgevolazione);

		parameters.put("MODALITA_AGEVOLAZIONE_RIM_SUB",	modalitaAgevolazioneReport);
		parameters.put("MODALITA_AGEVOLAZIONE_RIM_DS", modalitaDS);

		JRBeanCollectionDataSource fontiFinanziarieDS = new JRBeanCollectionDataSource(
				fontiFinanziarie);

		parameters.put("FONTI_FINANZIARIE_RIM_SUB", fontiFinanziarieReport);
		parameters.put("FONTI_FINANZIARIE_RIM_DS", fontiFinanziarieDS);

		/*
		 * Creazione pdf
		 */
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				rimodulazioneReport, parameters, new JREmptyDataSource());

		result = JasperExportManager.exportReportToPdf(jasperPrint);
		logger.end();
		return result;
	}

	// TODO }L{
	public byte[] createDichiarazioneDiRinunciaPDF(
			DichiarazioneDiRinunciaReportDTO dichiarazione) throws Exception {
		byte[] result = null;
		JasperReport report = getReportCompilato("DichiarazioneDiRinuncia.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("dichiarazione", dichiarazione);

		JasperPrint jasperPrint = JasperFillManager.fillReport(report,
				parameters, new JREmptyDataSource());

		result = JasperExportManager.exportReportToPdf(jasperPrint);
		return result;
	}

	public byte[] createComunicazioneFineProgettoPDF(
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.DichiarazioneDiSpesaDTO dichiarazione,
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.VoceDiCostoDTO[] vociDiCosto,
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO[] documentiContabiliDTO,
			List<ErogazioneReportVO> erogazioni,
			List<ContoEconomicoItemDTO> contoEconomico,
			List<IndicatoreDomandaVO> indicatori,
			List<FaseMonitoraggioProgettoVO> fasiMonitoraggio,
			List<CodiceDescrizioneDTO> documentazioneAllegata, boolean isBozza,
			EstremiBancariDTO estremiBancariDTO, Double importoRichiestaSaldo,
			BigDecimal percentualeErogazione) throws Exception {

		byte[] result = null;
		JasperReport reportMaster;

		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		watcher.start();
		try {
			long startCaricaReport = System.currentTimeMillis();
			reportMaster = getReportCompilato("FineProgetto.jasper");
			logger.info("+++++++++++++++ report FineProgetto.jasper caricato in : "
					+ (System.currentTimeMillis() - startCaricaReport) + " ms");

			Map<String, Object> parameters = new HashMap<String, Object>();
			/**
			 * Dati per il subreport delle voci di costo
			 */
			parameters.put("dichiarazione", dichiarazione);

			parameters.put("IS_BR02", regolaManager
					.isRegolaApplicabileForBandoLinea(
							dichiarazione.getIdBandoLinea(),
							RegoleConstants.BR02_VISUALIZZA_VOCI_DI_SPESA));

			parameters.put("IS_BR05", regolaManager
					.isRegolaApplicabileForBandoLinea(
							dichiarazione.getIdBandoLinea(),
							RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA));
			parameters.put("IS_BR16", regolaManager
					.isRegolaApplicabileForBandoLinea(
							dichiarazione.getIdBandoLinea(),
							RegoleConstants.BR16_GESTIONE_CAMPO_TASK));

			parameters.put("IS_BOZZA", isBozza);
			parameters.put("bando", dichiarazione.getDescBando());
			List<Object> listVuotaPerSubreport = new ArrayList<Object>();
			listVuotaPerSubreport.add("");

			if (importoRichiestaSaldo != null) {

				JasperReport subErogazioneSaldo = getReportCompilato("SubErogazioneSaldo.jasper");
				JRBeanCollectionDataSource DS_vuoto = new JRBeanCollectionDataSource(
						listVuotaPerSubreport);
				parameters.put("SUB_EROGAZIONE_SALDO", subErogazioneSaldo);
				parameters.put("DS_EROGAZIONE_SALDO", DS_vuoto);
				if (percentualeErogazione == null)
					percentualeErogazione = new BigDecimal(0);
				parameters.put("percentualeErogazione", NumberUtil
						.getStringValueItalianFormat(percentualeErogazione
								.doubleValue()));
				parameters.put("importoRichiestaSaldo", NumberUtil
						.getStringValueItalianFormat(importoRichiestaSaldo));
				parameters.put("estremiBancari", estremiBancariDTO);
				JasperReport estremiBancari = getReportCompilato("EstremiBancari.jasper");
				JRBeanCollectionDataSource DS_vuoto2 = new JRBeanCollectionDataSource(
						listVuotaPerSubreport);
				parameters.put("ESTREMI_BANCARI", estremiBancari);
				parameters.put("DS_ESTREMI_BANCARI", DS_vuoto2);
			}

			String note = "";
			if (!ObjectUtil.isEmpty(dichiarazione.getNoteFineProgetto())) {
				note = "Note: \n" + dichiarazione.getNoteFineProgetto();
			}
			parameters.put("note", note);

			setFineProgettoSubReport1(parameters, listVuotaPerSubreport);

			setSubReportErogazioni(erogazioni, parameters);

			setFineProgettoSubReport2(parameters, listVuotaPerSubreport);

			if (!ObjectUtil.isEmpty(vociDiCosto))
				setSubReportVociDiCosto(vociDiCosto, parameters);

			setSubReportContoEconomico(parameters, contoEconomico);

			if (!ObjectUtil.isEmpty(documentiContabiliDTO))
				setSubReportDocumentiContabili(documentiContabiliDTO,
						parameters);

			if (regolaManager.isRegolaApplicabileForBandoLinea(
					dichiarazione.getIdBandoLinea(),
					RegoleConstants.BR35_CONTROLLO_ATTIVITA_INDICATORI_VALIDA)) {
				if (!ObjectUtil.isEmpty(indicatori)) {
					parameters.put("IS_INDICATORI", Boolean.TRUE);
					setSubReportIndicatori(indicatori, parameters);
				}
			}

			if (regolaManager
					.isRegolaApplicabileForBandoLinea(
							dichiarazione.getIdBandoLinea(),
							RegoleConstants.BR34_CONTROLLO_ATTIVITA_CRONOPROGRAMMA_VALIDA)) {
				if (!ObjectUtil.isEmpty(fasiMonitoraggio)) {
					parameters.put("IS_CRONOPROGRAMMA", Boolean.TRUE);
					setSubReportFasiMonitoraggio(fasiMonitoraggio, parameters);
				}
			}

			setSubReportDocumentazioneAllegata(documentazioneAllegata,
					parameters);

			caricaImmagine(parameters);

			long startFillReport = System.currentTimeMillis();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportMaster, parameters, new JREmptyDataSource());

			logger.info("+++++++++++++++ fillReport ComunicazioneFineProgetto in : "
					+ (System.currentTimeMillis() - startFillReport) + " ms");

			long exportReportToPdf = System.currentTimeMillis();
			result = JasperExportManager.exportReportToPdf(jasperPrint);
			logger.info("+++++++++++++++ exportReportToPdf ComunicazioneFineProgetto in : "
					+ (System.currentTimeMillis() - exportReportToPdf) + " ms");
		} catch (Exception e) {
			watcher.stop();
			watcher.dumpElapsed(this.getClass().getName(), "createReport",
					"ReportManager::createComunicazioneFineProgettoPDF",
					"id progetto ");
			logger.error(e.getMessage(), e);
			throw e;
		}

		return result;
	}

	private JasperReport setSubReportDocumentazioneAllegata(
			List<CodiceDescrizioneDTO> documentazioneAllegata,
			Map<String, Object> parameters) throws JRException {
		JasperReport report = getReportCompilato("DocumentazioneAllegata.jasper");

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
				(documentazioneAllegata));
		parameters.put("SUB_DOCUMENTAZIONE_ALLEGATA", report);
		parameters.put("DS_DOCUMENTAZIONE_ALLEGATA", ds);
		return report;

	}

	private JasperReport setSubReportFasiMonitoraggio(
			List<FaseMonitoraggioProgettoVO> fasiMonitoraggio,
			Map<String, Object> parameters) throws JRException {
		JasperReport report = getReportCompilato("FasiMonitoraggio.jasper");

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
				(fasiMonitoraggio));
		parameters.put("SUB_FASI_MONITORAGGIO", report);
		parameters.put("DS_FASI_MONITORAGGIO", ds);
		return report;

	}

	private JasperReport setSubReportIndicatori(
			List<IndicatoreDomandaVO> indicatori, Map<String, Object> parameters)
			throws JRException {
		JasperReport report = getReportCompilato("Indicatori.jasper");

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
				indicatori);
		parameters.put("SUB_INDICATORI", report);
		parameters.put("DS_INDICATORI", ds);
		return report;

	}

	private JasperReport setSubReportContoEconomico(
			Map<String, Object> parameters,
			List<ContoEconomicoItemDTO> contoEconomico) throws JRException {
		JasperReport contoEconomicoReport = getReportCompilato("ContoEconomico.jasper");

		JRBeanCollectionDataSource contoEconomicoItemDS = new JRBeanCollectionDataSource(
				contoEconomico);

		parameters.put("SUB_CONTO_ECONOMICO", contoEconomicoReport);
		parameters.put("CONTO_ECONOMICO_DS", contoEconomicoItemDS);
		return contoEconomicoReport;
	}

	private JasperReport setSubReportDocumentiContabili(
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO[] documentiContabiliDTO,
			Map<String, Object> parameters) throws JRException {
		JasperReport reportDocumentiContabili = getReportCompilato("DocumentiContabili.jasper");
		logger.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n +++++++++++++++++++++++++++++++++++++++");
		logger.info("documenti contabili modificati");
		JRBeanCollectionDataSource documentiContabiliDS = new JRBeanCollectionDataSource(
				Arrays.asList(documentiContabiliDTO));
		parameters.put("SUB_DOC_CONTABILI", reportDocumentiContabili);
		parameters.put("DS_DOC_CONTABILI", documentiContabiliDS);
		return reportDocumentiContabili;
	}

	private void setSubReportVociDiCosto(
			it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report.VoceDiCostoDTO[] vociDiCosto,
			Map<String, Object> parameters) throws JRException {
		JasperReport reportVociCosto = getReportCompilato("VociDiCosto.jasper");
		JRBeanCollectionDataSource vociDS = new JRBeanCollectionDataSource(
				Arrays.asList(vociDiCosto));
		parameters.put("VOCI_SUB", reportVociCosto);
		parameters.put("VOCI_DS", vociDS);
	}

	private void setFineProgettoSubReport2(Map<String, Object> parameters,
			List<Object> listVuotaPerSubreport) throws JRException {
		JasperReport subReportFineProgetto2 = getReportCompilato("SubFineProgetto2.jasper");
		parameters.put("SUB_FINE_PROGETTO_2", subReportFineProgetto2);
		JRBeanCollectionDataSource DS_vuoto2 = new JRBeanCollectionDataSource(
				listVuotaPerSubreport);
		parameters.put("DS_SUB_REPORT_2", DS_vuoto2);
	}

	private void setFineProgettoSubReport1(Map<String, Object> parameters,
			List<Object> listVuotaPerSubreport) throws JRException {
		JasperReport subReportFineProgetto1 = getReportCompilato("SubFineProgetto1.jasper");
		JRBeanCollectionDataSource DS_vuoto = new JRBeanCollectionDataSource(
				listVuotaPerSubreport);
		parameters.put("SUB_FINE_PROGETTO_1", subReportFineProgetto1);
		parameters.put("DS_SUB_REPORT_1", DS_vuoto);
	}

	private void setSubReportErogazioni(List<ErogazioneReportVO> erogazioni,
			Map<String, Object> parameters) throws JRException {

		if (erogazioni == null || erogazioni.isEmpty()) {
			erogazioni = new ArrayList<ErogazioneReportVO>();
			ErogazioneReportVO erogazione = new ErogazioneReportVO();
			erogazione.setDescModalitaAgevolazione("");
			erogazioni.add(erogazione);
		}

		JRBeanCollectionDataSource erogazioniDS = new JRBeanCollectionDataSource(
				erogazioni);

		JasperReport subReportErogazioni;
		subReportErogazioni = getReportCompilato("Erogazioni.jasper");
		parameters.put("SUB_REPORT_EROGAZIONI", subReportErogazioni);
		parameters.put("DS_EROGAZIONI", erogazioniDS);
		BigDecimal totaleImportoErogazione = new BigDecimal("0");
		ErogazioneReportVO totale = new ErogazioneReportVO();
		if (!ObjectUtil.isEmpty(erogazioni)) {
			totale = NumberUtil.accumulate(erogazioni, "importoErogazione");
			totaleImportoErogazione = totale.getImportoErogazione();
		}

		parameters.put("totaleImportoErogazione", totaleImportoErogazione);
	}

	public byte[] createDichiarazioneDiSpesaSenzaDocumentiPDF(
			DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO, boolean isBozza,
			boolean isBr02, boolean isBr05, boolean isBR16,
			DichiarazioneDiSpesaReportDTO dichiarazione) throws Exception {

		byte[] result = null;
		JasperReport reportMaster;
		it.csi.util.performance.StopWatch watcher = new it.csi.util.performance.StopWatch(
				Constants.STOPWATCH_LOGGER);
		watcher.start();
		try {
			long startCaricaReport = System.currentTimeMillis();
			reportMaster = getReportCompilato("DichiarazioneDiSpesaSenzaDocumenti.jasper");
			logger.info("+++++++++++++++ report caricati in : "
					+ (System.currentTimeMillis() - startCaricaReport) + " ms");

			Map<String, Object> parameters = new HashMap<String, Object>();
			/**
			 * Dati per il subreport delle voci di costo
			 */

			/**
			 * Dati per il subreport dei documenti contabili
			 */

			parameters.put("dichiarazione", dichiarazione);
			parameters.put("IS_BR02", isBr02);
			parameters.put("IS_BR05", isBr05);
			parameters.put("IS_BOZZA", new Boolean(isBozza));
			parameters.put("IS_BR16", isBR16);
			caricaImmagine(parameters);
			long startFillReport = System.currentTimeMillis();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportMaster, parameters, new JREmptyDataSource());
			logger.info("+++++++++++++++ fillReport  in : "
					+ (System.currentTimeMillis() - startFillReport) + " ms");

			long exportReportToPdf = System.currentTimeMillis();
			result = JasperExportManager.exportReportToPdf(jasperPrint);
			logger.info("+++++++++++++++ exportReportToPdf in : "
					+ (System.currentTimeMillis() - exportReportToPdf) + " ms");
		} catch (Exception e) {
			watcher.stop();
			watcher.dumpElapsed(this.getClass().getName(), "createReport",
					"ReportManager::createReport", "id progetto "
							+ dichiarazioneDiSpesaDTO.getIdProgetto());
			logger.error(e.getMessage(), e);
			throw e;
		}

		return result;
	}

	private void caricaImmagine(Map<String, Object> parameters) {
		long caricaGif = System.currentTimeMillis();
		InputStream imageBozza = this.getClass().getClassLoader()
				.getResourceAsStream("bozza.gif");
		logger.info("immagine  bozza caricata in  : "
				+ (System.currentTimeMillis() - caricaGif) + " ms");
		parameters.put("imgBozza", imageBozza);
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
	
	
	
	
	
	public byte[] createChecklist(String codTipoChecklist,
			ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO,
			List<ChecklistItemDTO> items, 
			ChecklistHtmlIrregolaritaDTO checklistHtmlIrregolaritaDTO,
			Boolean isSif,
			byte[] jasperModel,
			Map<String, String> paramentersExtra
			 ) throws Exception {
		
		logger.begin();
		logger.info("checkListHtmlAnagraficaDTO="+checkListHtmlAnagraficaDTO);
		logger.info("checklistHtmlIrregolaritaDTO="+checklistHtmlIrregolaritaDTO);
		logger.info("items="+items);
		logger.info("checklistHtmlIrregolaritaDTO="+checklistHtmlIrregolaritaDTO);
		logger.info("isSif="+isSif);
		logger.info("jasperModel="+jasperModel);
		logger.info("paramentersExtra="+paramentersExtra);
		
		byte[] pdfBytes = null;
		JasperReport checklistMasterReport = null;
		
//		if(checkListHtmlAnagraficaDTO.getIdProcesso() != null && checkListHtmlAnagraficaDTO.getIdProcesso().compareTo(Constants.ID_PROCESSO_VECCHIA_PROGRAMMAZIONE) == 0){
		if(jasperModel != null){
			
			checklistMasterReport = (JasperReport) JRLoader.loadObject(new ByteArrayInputStream(jasperModel));
//			else
//				throw new NotFoundException("Nessun modello Jasper � stato assegnato per questo bando linea");

		}else{
			if(codTipoChecklist.equals(GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE)){
				
				  checklistMasterReport = !isSif ? getReportCompilato("checklistAiutiDocumentale_14_20.jasper") 
						  : getReportCompilato("checklistSF_DocumentaleV1_14_20.jasper");
			}else if(codTipoChecklist.equals(GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO)){
				 checklistMasterReport = !isSif ? getReportCompilato("checklistAiuti_InLoco_14_20.jasper") 
						 : getReportCompilato("checklistSF_inLocoV1_14_20.jasper");
			}
		}
		 
		if(checklistMasterReport != null){
			JasperReport checklistItemReport = getReportCompilato("checklistItemDocumentale.jasper");
			logger.info("checklistItemReport="+checklistItemReport);
			
			Map parameters = new HashMap<String, ChecklistHtmlAnagraficaDTO>();
			String relativeResoursesPath = ResourceBundle.getBundle("conf.server").getString("resourcesPath");
			
			logger.info("relativeResoursesPath="+relativeResoursesPath);
			//resourcesPath=http://www.sistemapiemonte.it scritto nel file di properties e poi messo nel build.xml
			
			JRBeanCollectionDataSource itemsDS = new JRBeanCollectionDataSource(items);
			parameters.put("CHECKLIST_ITEM_SUB", checklistItemReport);
			parameters.put("CHECKLIST_ITEM_DS", itemsDS);
			parameters.put("anagrafica", checkListHtmlAnagraficaDTO);
			parameters.put("irregolarita", checklistHtmlIrregolaritaDTO);
			parameters.put("IMG_PATH", relativeResoursesPath);
			parameters.putAll(paramentersExtra);
			
			logger.info("checklistMasterReport="+checklistMasterReport);
		
			logger.info("calling JasperFillManager.fillReport");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					checklistMasterReport, parameters,
					new JREmptyDataSource());
			
			logger.info("JasperExportManager.exportReportToPdf");
			pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
			
			logger.info("pdfBytes "+pdfBytes);
			
//			writeFile(null, pdfBytes);
			logger.end();
			
			return pdfBytes;
		}else
			throw new Exception("Nessun modello � stato configurato per il bando " + checkListHtmlAnagraficaDTO.getBandoRiferimento());
	}
	
	
	public byte[] createChecklistAffidamento(String codTipoChecklist,
			ChecklistHtmlAnagraficaDTO checkListHtmlAnagraficaDTO,
			List<ChecklistItemDTO> items, 
			ChecklistAffidamentoHtmlFasiDTO fasi, boolean isBandoPSC
			 ) throws Exception {
		logger.begin();
		logger.debug("checkListHtmlAnagraficaDTO="+checkListHtmlAnagraficaDTO);
		
		byte[] pdfBytes = null;
		JasperReport checklistMasterReport = null;

		// PK : per i bandi PSC carico altro template
		// se isBandoPSC == true , allora e' bando PSC

		if(codTipoChecklist.equals(Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_DOCUMENTALE)){				// Jira Pbandi-2859
	
			if(!isBandoPSC)
				checklistMasterReport = getReportCompilato("checklistAffidamentoDocumentale.jasper");
			else
				checklistMasterReport = getReportCompilato("checklistAffidamentoDocumentalePSC.jasper");

		}else if(codTipoChecklist.equals(Constants.COD_TIPO_CHEKCLIST_AFFIDAMENTO_IN_LOCO)){			// Jira Pbandi-2859
			
			if(!isBandoPSC)
				checklistMasterReport = getReportCompilato("checklistAffidamentoInLoco.jasper");
			else
				checklistMasterReport = getReportCompilato("checklistAffidamentoInLocoPSC.jasper");
		}

		JasperReport checklistItemReport = getReportCompilato("checklistItemAffidamento.jasper");

		Map parameters = new HashMap<String, ChecklistHtmlAnagraficaDTO>();
		String relativeResoursesPath = ResourceBundle.getBundle("server").getString("resourcesPath");
		logger.debug("relativeResoursesPath="+relativeResoursesPath);
		
		JRBeanCollectionDataSource itemsDS = new JRBeanCollectionDataSource(items);
		parameters.put("CHECKLIST_ITEM_SUB", checklistItemReport);
		parameters.put("CHECKLIST_ITEM_DS", itemsDS);
		parameters.put("anagrafica", checkListHtmlAnagraficaDTO);
		parameters.put("fasiAffidamento", fasi);
		parameters.put("IMG_PATH", relativeResoursesPath);

		logger.info("calling JasperFillManager.fillReport");
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				checklistMasterReport, parameters,
				new JREmptyDataSource());
		
		logger.info("JasperExportManager.exportReportToPdf");
		pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		
		logger.info("pdfBytes "+pdfBytes);
		
		//writeFile(null, pdfBytes);
		logger.end();
		return pdfBytes;
	}
	
	/*
	 * ONLY FOR JUNIT TESTING
	 */
//	private void writeFile(String filename,byte[] bytesPdf) throws FileNotFoundException,
//	IOException {
//		if(filename==null)filename="checkList_"+System.currentTimeMillis()+".pdf";
//		FileOutputStream fos = new FileOutputStream(filename);
//		fos.write(bytesPdf);
//		fos.close();
//	}
	
	 
	
}