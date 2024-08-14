/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ReportManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.PropostaCertificazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ImportiAnticipoPropostaCertificazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ReportPropostaCertificazionePorFesrVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDLineaDiInterventoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPropostaCertificazVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbwebcert.base.PbwebcertJunitClassRunner;
import it.csi.pbandi.pbwebcert.base.TestBaseService;
import it.csi.pbandi.pbwebcert.integration.dao.impl.CertificazioneDaoImpl;
import it.csi.pbandi.pbwebcert.integration.vo.AllegatoVExcel20212027VO;
import it.csi.pbandi.pbwebcert.integration.vo.MailVO;
import it.csi.pbandi.pbwebcert.util.FileSqlUtil;

@RunWith(PbwebcertJunitClassRunner.class)
public class CertificazioneExcellTest  extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(CertificazioneExcellTest.class);

	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	protected DecodificheManager decodificheManager;
	@Autowired
	private ReportManager reportManager;
	@Autowired
	protected BeanUtil beanUtil;
	@Autowired
	protected FileSqlUtil fileSqlUtil;
	@Deprecated
	@Autowired
	private it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.dao.PbandiCertificazioneDAOImpl pbandiCertificazioneDAO;	
	
	CertificazioneDaoImpl certificazioneDaoImpl;
	
	@Before()
	public void before() {
		LOG.info("[CertificazioneExcellTest::before]START");
	}

	@After
	public void after() {
		LOG.info("[CertificazioneExcellTest::after]START");
	}

	

	@Test
	public void generaExllTest() throws Exception {
		String prf = "[CertificazioneExcellTest :: generaExllTest] ";
		LOG.debug(prf + "START");
		
		BigDecimal idPropostaCertificaz = new BigDecimal(1475);
		BigDecimal idStatoPropostaCertif = new BigDecimal(3);  //1 --> 00coda
		
		Long idUtente = null;
		PropostaCertificazioneDTO propostaCertificazione = new PropostaCertificazioneDTO();
		propostaCertificazione.setIdPropostaCertificaz(idPropostaCertificaz.longValue());
		
		MailVO mailVO = new MailVO();
		Long[] idLineeDiInterventoArr = new Long[] {891L};
		
		//CertificazioneBusinessImpl -> lanciaCreazionePropostaCertificazione
		
		try {
			
			String lineaDiIntervento = decodificheManager.findDescBreve(PbandiDLineaDiInterventoVO.class, new BigDecimal(891));
			LOG.debug(prf + "lineaDiIntervento=["+lineaDiIntervento+"]");
			
			PbandiTPropostaCertificazVO propostaCertificazVO = new PbandiTPropostaCertificazVO();
			propostaCertificazVO.setIdPropostaCertificaz(idPropostaCertificaz);
			propostaCertificazVO.setIdStatoPropostaCertif(idStatoPropostaCertif);
			
			propostaCertificazVO = genericDAO.findSingleWhere(propostaCertificazVO);
			LOG.debug(prf + "propostaCertificazVO="+propostaCertificazVO);
			
			creaESalvaReportPropostaCertificazione(idUtente,
					idPropostaCertificaz, propostaCertificazVO, mailVO,
					idLineeDiInterventoArr, false);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void creaESalvaReportPropostaCertificazione(Long idUtente,
			BigDecimal idPropostaCertificaz,
			PbandiTPropostaCertificazVO propostaCertificazVO, MailVO mailVO,
			Long[] idLineeDiInterventoArr, boolean isPostGestione) throws Exception {
		
		LOG.info(" BEGIN");
		LOG.info(" idPropostaCertificaz="+idPropostaCertificaz);
		LOG.info(" idLineeDiInterventoArr="+idLineeDiInterventoArr);
		
//		List<AttachmentVO> attachments = new ArrayList<AttachmentVO>();

		if (mailVO.getContent() == null) {
			mailVO.setContent("");
		}

		for (Long idLineaDiIntervento : idLineeDiInterventoArr) {
			
			/*
			 * carico il report dal database
			 */
			LOG.info(" idLineaDiIntervento =["+idLineaDiIntervento+"]");
			
			ReportPropostaCertificazionePorFesrVO filtroReport = new ReportPropostaCertificazionePorFesrVO();
			filtroReport.setIdPropostaCertificaz(idPropostaCertificaz);
			filtroReport.setIdLineaDiIntervento(new BigDecimal(idLineaDiIntervento));
			
			FilterCondition filter = new FilterCondition<ReportPropostaCertificazionePorFesrVO>(filtroReport);
			NullCondition<ReportPropostaCertificazionePorFesrVO> nullCond = new NullCondition<ReportPropostaCertificazionePorFesrVO>(ReportPropostaCertificazionePorFesrVO.class,"idProgettoSif");
			AndCondition andCond = new AndCondition<ReportPropostaCertificazionePorFesrVO>(filter, nullCond);
			List<ReportPropostaCertificazionePorFesrVO> elementiReport = genericDAO.findListWhere(andCond);
			
			if(elementiReport!=null) {
				LOG.info(" >>>>>>>>>>>> elementiReport.size ="+elementiReport.size());
			}
			
			// Jira PBANDI-2882 - fine.
			
			// Jira PBANDI-2882: dati con cui popolare il foglio Percettori.
			ReportPropostaCertificazionePorFesrVO filtroPercettori = new ReportPropostaCertificazionePorFesrVO();		
			filtroPercettori.setIdPropostaCertificaz(idPropostaCertificaz);
			filtroPercettori.setIdLineaDiIntervento(new BigDecimal(idLineaDiIntervento));
			// aggiungo la condizione "idProgettoSif is not null".
			FilterCondition filterCondPercettori = new FilterCondition<ReportPropostaCertificazionePorFesrVO>(filtroPercettori);
			NullCondition<ReportPropostaCertificazionePorFesrVO> nullCondPercettori = new NullCondition<ReportPropostaCertificazionePorFesrVO>(ReportPropostaCertificazionePorFesrVO.class,"idProgettoSif");
			NotCondition<ReportPropostaCertificazionePorFesrVO> notCondPrecettori = new NotCondition<ReportPropostaCertificazionePorFesrVO>(nullCondPercettori); 
			AndCondition andCondPercettori = new AndCondition<ReportPropostaCertificazionePorFesrVO>(filterCondPercettori, notCondPrecettori);
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori = genericDAO.findListWhere(andCondPercettori);
			
			
			if(elementiPercettori!=null) {
				LOG.info(" >>>>>>>>>>>> elementiPercettori.size ="+elementiPercettori.size());
			}
			
			
			//lineaDiIntervento=POR-FESR-2021-2027
			String lineaDiIntervento = decodificheManager.findDescBreve(
					PbandiDLineaDiInterventoVO.class, new BigDecimal(idLineaDiIntervento));
			
			LOG.info(" lineaDiIntervento=["+lineaDiIntervento+"]");
			
//			mailVO.setContent(mailVO.getContent()
//					+ creaTestoMail(
//							lineaDiIntervento,
//							!isEmpty(elementiReport),
//							propostaCertificazVO.getDescProposta(),
//							caricaScostamenti(idPropostaCertificaz),
//							isScostamentoCompensato(idPropostaCertificaz),
//							caricaProgettiPerCompensazione(idPropostaCertificaz))
//					+ "\n\n");

			LOG.info(" mailVO.setContent OK");

			if (!isEmpty(elementiReport)) {
               
				byte[] reportPropostaCertificazioneFileData = generaReport(
						propostaCertificazVO, 
						elementiReport, 
						lineaDiIntervento,
						elementiPercettori);
				
//				FileUtil.scriviFile("propostaCertificazione1420.xls", reportPropostaCertificazioneFileData);	
				
				if(reportPropostaCertificazioneFileData == null)
					throw new Exception("Il report per la certificazione è null");
				
				String postGestione = "";
				if(isPostGestione){
					postGestione = "PostGestione";
				}
				String nomeFile = "report" 
						+ postGestione
						+ propostaCertificazVO.getIdPropostaCertificaz()
						+ lineaDiIntervento + ".xls";
				LOG.info( " nomeFile="+nomeFile);
//				LOG.info( " elementiReport !=null , creo attachment");
//				AttachmentVO attach = new AttachmentVO();
//				attach.setData(reportPropostaCertificazioneFileData);
//				attach.setName(nomeFile);
//				attach.setContentType(MIME_APPLICATION_XSL);
//
//				attachments.add(attach);
				LOG.info(" attachment added to list");
			}

//			mailVO.setAttachments(attachments);

//			for (AttachmentVO attachment : attachments) {
//				// deve essere fatta per ultima, perché non è transazionale
//				persistiDocumento(idUtente, attachment.getName(),
//						attachment.getData(), propostaCertificazVO);
//			}
           LOG.info("settati attachments");

		}
	 LOG.info("END");
	}

	private byte[] generaReport(
			PbandiTPropostaCertificazVO propostaCertificazVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport,
			String descBreveLinea,
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori) throws Exception {
		
		LOG.debug( "descBreveLinea: " + descBreveLinea);
		
		byte[] reportBytes = null;
		if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR.equals(descBreveLinea)) {
//			reportBytes = generaReportPorFesr(propostaCertificazVO, elementiReport, descBreveLinea);
			LOG.debug( "genero Report LINEA_DI_INTERVENTO_RADICE_POR_FESR " );
			
		} else if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.LINEA_DI_INTERVENTO_RADICE_PAR_FAS
				.equals(descBreveLinea)) {
			LOG.debug( "genero Report LINEA_DI_INTERVENTO_RADICE_PAR_FAS");
//			reportBytes = generaReportParFas(propostaCertificazVO, elementiReport, descBreveLinea);
			
		}else if (it.csi.pbandi.pbservizit.pbandisrv.util.Constants.LINEA_DI_INTERVENTO_RADICE_POR_FESR_2014_20.equals(descBreveLinea)) {
			LOG.debug( " di generaReportPorFesr2014_20");
			LOG.debug( "genero Report  LINEA_DI_INTERVENTO_RADICE_POR_FESR_2014_20");
//			reportBytes = generaReportPorFesr2014_20(
//					propostaCertificazVO, elementiReport, descBreveLinea, elementiPercettori);
		}
		else if ("POR-FESR-2021-2027".equals(descBreveLinea)) {
			LOG.debug( " genero Report POR-FESR-2021-2027");
			reportBytes = generaReportPorFesr2021_27(
					propostaCertificazVO, elementiReport, descBreveLinea, elementiPercettori);
		}

		if(reportBytes!=null) {
			LOG.debug( "reportPropostaCertificazioneFileData : "+reportBytes);
			
			FileUtils.writeByteArrayToFile(new File("C:\\tmp\\report1475POR-FESR-2021-2027.xls"), reportBytes);
			
		} else {
			LOG.debug( "reportPropostaCertificazioneFileData NULL ");
		}
		return reportBytes;
	}
	
	
	
	private byte[] generaReportPorFesr2021_27(
			PbandiTPropostaCertificazVO propostaCertificazVO,
			List<ReportPropostaCertificazionePorFesrVO> elementiReport,
			String descBreveLinea,
			List<ReportPropostaCertificazionePorFesrVO> elementiPercettori) throws Exception, IOException {

		LOG.info(" BEGIN");

		byte[] reportPropostaCertificazioneFileData;
		// conf/report/xls_templates/nReportPropostaCertificazionePorFesr2021_27.xls
		String templateKey = "nReportPropostaCertificazionePorFesr2021_27";
		LOG.info("Popolo il template " + templateKey);

		ArrayList<Long> linesToJump = new ArrayList<Long>();
		linesToJump.add(2L);
		reportPropostaCertificazioneFileData = TableWriter
				.writeTableToByteArray(templateKey, new ExcelDataWriter(
						propostaCertificazVO.getIdPropostaCertificaz().toString()), elementiReport, linesToJump);
		ReportPropostaCertificazionePorFesrVO elemento = elementiReport.get(0);
		Map<String, Object> testataVars = beanUtil.transformToMap(elemento);
		LOG.info("testataVars="+testataVars);
		
		// PK testataVars.get("tableNameForValueObject") contiene una query 
				
		
		// Jira PBANDI-2882: foglio Percettoti.
		LOG.info(" foglio Percettori ");
		String templateKeyPercettori = it.csi.pbandi.pbservizit.pbandisrv.util.Constants.TEMPLATE_PROPOSTA_CERTIFICAZIONE_POR_FESR_PERCETTORI;
		byte[] reportPropostaCertificazioneFileDataPercettori;
		reportPropostaCertificazioneFileDataPercettori = TableWriter
				.writeTableToByteArray(templateKeyPercettori, new ExcelDataWriter(
						propostaCertificazVO.getIdPropostaCertificaz().toString()+"Percettori"), elementiPercettori, linesToJump);
		ReportPropostaCertificazionePorFesrVO elementoPercettori = new ReportPropostaCertificazionePorFesrVO();
		if (elementiPercettori.size() > 0) {
			elementoPercettori = elementiPercettori.get(0);
		}
		Map<String, Object> testataVarsPercettori = beanUtil.transformToMap(elementoPercettori);
		LOG.info("testataVarsPercettori="+testataVarsPercettori);
		// Jira PBANDI-2882: foglio Percettoti - fine.

		PbandiDLineaDiInterventoVO porFesrVO = new PbandiDLineaDiInterventoVO();
		porFesrVO.setDescBreveLinea(descBreveLinea);

		//Importo Anticipo
		ImportiAnticipoPropostaCertificazioneVO importiVO = new ImportiAnticipoPropostaCertificazioneVO();
		importiVO.setIdPropostaCertificaz(propostaCertificazVO.getIdPropostaCertificaz());

		//******* PREFIXES **************************************
		final String IMPORTO_ANTICIPO_PREFIX = "importoAnticipo";
		final String IMPORTO_NONCOPERTO_PREFIX = "importoNonCoperto";
		final String DIFF_IMPORTI_ANTICIPO_PREFIX = "diffAnticipoImpNonCop";
		final String IMPORTI_PAGVALIDATINETTI_PREFIX = "importoPagValidatiNetti";
		final String IMPORTO_CERTNETTO_PREFIX = "importoCertNetto";
		final String IMPORTO_CONTRIBUTIVERSATI_PREFIX = "importoContributiVersati";
		final String IMPORTO_CONTRIBUTOPUBBLICO_PREFIX = "importoContributoPubblico";

		String queryImportoNettoAndImportoPubblicoConc = fileSqlUtil.getQuery("GenerazioneExcelCertificazione20212027.sql");
		//help? TODO
		List<AllegatoVExcel20212027VO> resultQuery20212017 = new ArrayList<>();// = getJdbcTemplate().query(queryImportoNettoAndImportoPubblicoConc, new AllegatoVExcel20212027RowMapper());

		//Inserire gli assi in una costante
		Map<String, BigDecimal> asseLineaInterventoMap = new HashMap<String, BigDecimal>();
		asseLineaInterventoMap.put("I" , 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_I);
		asseLineaInterventoMap.put("II", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_II);
		asseLineaInterventoMap.put("III", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_III);
		asseLineaInterventoMap.put("IV", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_IV);
		asseLineaInterventoMap.put("V", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_V);
		asseLineaInterventoMap.put("VI", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_VI);
		asseLineaInterventoMap.put("VII", 	it.csi.pbandi.pbservizit.pbandisrv.util.Constants.ID_LINEA_DI_INTERVENTO_ASSE_VII);

		Map<String, Object> allegatoVVars = new HashMap<String, Object>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> anticipoMap = new HashMap<String, BigDecimal>();
		List<ImportiAnticipoPropostaCertificazioneVO> anticipi = genericDAO.where(importiVO).select();

		Map<String, BigDecimal> importoCertificazioneNettoMap = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> contributoPubblicoConcessoMap = new HashMap<String, BigDecimal>();

		for(AllegatoVExcel20212027VO allegatoVExcel20212027VO : resultQuery20212017) {
			String asse = allegatoVExcel20212027VO.getDescAsse();
			importoCertificazioneNettoMap.put(asse, allegatoVExcel20212027VO.getImportoCertificazioneNetto());
			contributoPubblicoConcessoMap.put(asse, allegatoVExcel20212027VO.getImportoCertificazioneNetto());
		}

		for (String key : asseLineaInterventoMap.keySet()) {

			BigDecimal idLineaDiIntervento = asseLineaInterventoMap.get(key);
			BigDecimal value = null;

			for(ImportiAnticipoPropostaCertificazioneVO anticipo : anticipi){
				if(anticipo.getIdLineaDiIntervento().compareTo(idLineaDiIntervento) == 0){
					value = anticipo.getImportoAnticipo();
				}
			}

			anticipoMap.put(key, value);
		}

		// cerco i dettagli delle proposte di certificazioni appartenenti a progetti SIFP
		List<Map> proposteSIFList = pbandiCertificazioneDAO.findProposteSIF(propostaCertificazVO.getIdPropostaCertificaz());
		LOG.info(" proposteSIFList="+proposteSIFList);
		List<BigDecimal> listaII = null;

		// popolo una lista con gli id dei progetti SIF per l'id certificazione dato
		if(proposteSIFList!=null && !proposteSIFList.isEmpty()){

			listaII = new ArrayList<BigDecimal>();
			for (Map mappa : proposteSIFList) {
				LOG.info(" ID_PROGETTO="+mappa.get("ID_PROGETTO"));
				listaII.add((BigDecimal)mappa.get("ID_PROGETTO"));
			}
		}

		//impCertNettiMap = sommo colonna AW (ImportiCertificabiliNetti) per Asse
		Map<String, BigDecimal> impCertNettiMap = new HashMap<String, BigDecimal>();

		// impPagValidazMap = somma colonna AE (ImportoPagamentiValidati) per Asse
		// TODO : dubbio : questo valore è corretto o devo prendere ImportoPagValidatiOrig?
		Map<String, BigDecimal> impPagValidazMap = new HashMap<String, BigDecimal>();

		// impEccedValidazMap = somma colonna AF (ImportoEccendenzeValidazione) per Asse
		Map<String, BigDecimal> impEccedValidazMap = new HashMap<String, BigDecimal>();

		// sommaValoriNMap = somma valori sommaValoriN per Asse
		Map<String, BigDecimal> sommaValoriNMap = new HashMap<String, BigDecimal>();

		for (ReportPropostaCertificazionePorFesrVO el : elementiReport) {

			String asse = el.getDescAsse();

			if(listaII!=null && listaII.contains( el.getIdProgetto())){

				BigDecimal imp = el.getImpCertificabileNettoSoppr();

				LOG.info(" SIF: Asse Progetto IdLineaDiIntervento= ["+asse+"] ["+el.getIdProgetto()+"] ["+el.getIdLineaDiIntervento()+"] ");

//				BigDecimal tmpA = impCertNettiMap.get(asse);
//				if(tmpA!=null){
//					BigDecimal somA = imp.add(tmpA);
//					impCertNettiMap.put(asse, somA);
//				}else{
//					impCertNettiMap.put(asse, imp);
//				}

				BigDecimal impB = el.getImportoPagamentiValidati();
				BigDecimal tmpB = impPagValidazMap.get(asse);
				if(tmpB!=null){
					BigDecimal somB = impB.add(tmpB);
					impPagValidazMap.put(asse, somB);
				}else{
					impPagValidazMap.put(asse, impB);
				}

				BigDecimal impC = el.getImportoEccendenzeValidazione();
				BigDecimal tmpC = impEccedValidazMap.get(asse);
				if(tmpB!=null){
					BigDecimal somC = impC.add(tmpC);
					impEccedValidazMap.put(asse, somC);
				}else{
					impEccedValidazMap.put(asse, impC);
				}
			}else{
				LOG.info(" NON SIF: Asse Progetto IdLineaDiIntervento= ["+asse+"] ["+el.getIdProgetto()+"] ["+el.getIdLineaDiIntervento()+"] ");
			}

			// Jira PBANDI-2882: al posto di SommaValoriN ora si usa ValoreN.
			//BigDecimal impD = el.getSommaValoriN();
			BigDecimal impD = el.getValoreN();
			BigDecimal tmpD = sommaValoriNMap.get(asse);
			if(tmpD!=null){
				BigDecimal somD = impD.add(tmpD);
				sommaValoriNMap.put(asse, somD);
				LOG.info(" somD="+somD);
			}else{
				sommaValoriNMap.put(asse, impD);
			}

		}
		LOG.info(" impCertNettiMap="+impCertNettiMap);
		LOG.info(" impPagValidazMap="+impPagValidazMap);
		LOG.info(" impEccedValidazMap="+impEccedValidazMap);
		LOG.info(" sommaValoriNMap="+sommaValoriNMap);

		for (String key : new String[] { "I", "II", "III", "IV" , "V" , "VI" , "VII"}) {

			BigDecimal valueSomm = new BigDecimal(0);
			BigDecimal valueAnt = new BigDecimal(0);

			if(sommaValoriNMap!=null && sommaValoriNMap.containsKey(key) && sommaValoriNMap.get(key)!=null){
				LOG.info(" asse ="+key+" , sommaValoriNMap.get(key)="+sommaValoriNMap.get(key));
				valueSomm = valueSomm.add(sommaValoriNMap.get(key));
			}

			if(anticipoMap!=null && anticipoMap.containsKey(key) && anticipoMap.get(key)!=null){
				LOG.info(" asse ="+key+" , anticipoMap.get(key)="+anticipoMap.get(key));
				valueAnt = valueAnt.add(anticipoMap.get(key));
			}

			LOG.info(" asse ="+key+" , valueSomm ="+valueSomm+" , valueAnt="+valueAnt);

			//Sostituzione placeholders con nuovi valori certificazione 2021 2027
			if (importoCertificazioneNettoMap.get(key) != null) {
				allegatoVVars.put(IMPORTO_CONTRIBUTIVERSATI_PREFIX + key, importoCertificazioneNettoMap.get(key));
				allegatoVVars.put(IMPORTO_CONTRIBUTOPUBBLICO_PREFIX + key, importoCertificazioneNettoMap.get(key));
			} else {
				allegatoVVars.put(IMPORTO_CONTRIBUTIVERSATI_PREFIX + key, new BigDecimal(0));
				allegatoVVars.put(IMPORTO_CONTRIBUTOPUBBLICO_PREFIX  + key, new BigDecimal(0));
			}


			// valorizzo la colonna "C" = "Importo complessivo versato come anticipo...."
			allegatoVVars.put(IMPORTO_ANTICIPO_PREFIX + key, valueSomm);

			// valorizzo la colonna "E" = "Importo che non e' stato coperto dalle spese....."
			if(valueAnt.compareTo(valueSomm)<=0){
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueAnt);
			}else{
				allegatoVVars.put(IMPORTO_NONCOPERTO_PREFIX + key, valueSomm);
			}

			// valorizzo la colonna "D" = "C" - "E"
			// calcolo la differenza tra IMPORTO_ANTICIPO_PREFIX e IMPORTO_NONCOPERTO_PREFIX
			BigDecimal diffA = new BigDecimal(0);
			BigDecimal diffB = new BigDecimal(0);

			if(allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key)!=null)
				diffA = diffA.add((BigDecimal)allegatoVVars.get(IMPORTO_ANTICIPO_PREFIX + key));

			if(allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key)!=null)
				diffB = diffB.add((BigDecimal)allegatoVVars.get(IMPORTO_NONCOPERTO_PREFIX + key));

			BigDecimal diffe = diffA.subtract(diffB);
			LOG.info(" asse ="+key+" , diffA="+diffA+" ,diffB ="+diffB+" , diffe=" +diffe);
			allegatoVVars.put(DIFF_IMPORTI_ANTICIPO_PREFIX + key, diffe);

			if(impCertNettiMap.get(key)!=null){
				allegatoVVars.put(IMPORTO_CERTNETTO_PREFIX + key, impCertNettiMap.get(key));
			}else{
				allegatoVVars.put(IMPORTO_CERTNETTO_PREFIX + key, new BigDecimal(0));
			}

			// impPagValidazMap = somma colonna AE per Asse
			// impEccedValidazMap = somma colonna AF per Asse

			BigDecimal AE = impPagValidazMap.get(key);
			LOG.info(" AE="+AE);

			if(AE!=null){
				BigDecimal AF ;
				if(impEccedValidazMap.get(key)!=null){
					AF = impEccedValidazMap.get(key);
				}else{
					AF = new BigDecimal(0);
				}
				BigDecimal diff = AE.subtract(AF);
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, diff);
			}else{
				allegatoVVars.put(IMPORTI_PAGVALIDATINETTI_PREFIX + key, new BigDecimal(0));
			}
		}

		Map<String, byte[]> singlePageTables = new HashMap<String, byte[]>();
		singlePageTables.put("Dettaglio", reportPropostaCertificazioneFileData);
		Map<String, Map<String, Object>> singleCellVars = new HashMap<String, Map<String, Object>>();
		singleCellVars.put("Testata", testataVars);
		singleCellVars.put("Allegato V", allegatoVVars);
		// Jira PBANDI-2882.
		singlePageTables.put("Percettori", reportPropostaCertificazioneFileDataPercettori);

		reportPropostaCertificazioneFileData = reportManager
				.getMergedDocumentFromTemplate(templateKey, singlePageTables,
						singleCellVars);

//		FileUtil.scriviFile("report1420.xls", reportPropostaCertificazioneFileData);

		LOG.info(" END");
		return reportPropostaCertificazioneFileData;
	}
	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}
	
	protected boolean isEmpty(java.util.List<?> o){
		return ObjectUtil.isEmpty(o);
				
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}
}
