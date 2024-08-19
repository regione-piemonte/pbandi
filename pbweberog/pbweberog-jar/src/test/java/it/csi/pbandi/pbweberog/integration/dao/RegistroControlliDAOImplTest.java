/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

//package it.csi.pbandi.pbweberog.integration.dao;
//
//import org.apache.log4j.Logger;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ChecklistRettificaForfettariaDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.IrregolaritaDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.RettificaForfettariaDTO;
//import it.csi.pbandi.pbservizit.security.UserInfoSec;
//import it.csi.pbandi.pbweberog.base.PbweberogJunitClassRunner;
//import it.csi.pbandi.pbweberog.base.TestBaseService;
//import it.csi.pbandi.pbweberog.dto.EsitoGenerazioneReportDTO;
//import it.csi.pbandi.pbweberog.integration.dao.impl.RegistroControlliDAOImpl;
//
//@RunWith(PbweberogJunitClassRunner.class)
//public class RegistroControlliDAOImplTest extends TestBaseService{
//	protected static Logger LOG = Logger.getLogger(RegistroControlliDAOImplTest.class);
//	protected static UserInfoSec userInfo = getCurrentUserInfo();
//	
//	@Autowired
//	RegistroControlliDAOImpl registroControlliDAOImpl;
//	@Before()
//	public void before() {
//		LOG.info("[RegistroControlliDAOImplTest::before]START");
//	}
//	@After()
//	public void after() {
//		LOG.info("[RegistroControlliDAOImplTest::after]START");
//	}
//	private static UserInfoSec getCurrentUserInfo() {
//		UserInfoSec userInfo = new UserInfoSec();
//		userInfo.setIdUtente(26300L);
//		userInfo.setIdIride("AAAAAA00A11O000W/CSI PIEMONTE/DEMO 34/ACTALIS_EU/20210209102032/16");
//		userInfo.setCodiceRuolo("BEN-MASTER");
//		userInfo.setIdSoggetto(2130090L);
//		userInfo.setRuolo("Utente operatore per tutti i Beneficiari");
//		userInfo.setNome("CSI PIEMONTE");
//		userInfo.setCognome("DEMO 34");	
//		return userInfo;
//	}
//	
//	@Test
//	public void  findIrregolaritaTest() throws Exception{
//		String prf = "[RegistroControlliDAOImplTest :: findIrregolaritaTest] ";
//		LOG.info(prf + "START");
//		//Long idProgetto = 14896L;
//		Long idProgetto = 19233L;
//		Long idUtente = getCurrentUserInfo().getIdUtente();
//		String idIride = getCurrentUserInfo().getIdIride();
//		IrregolaritaDTO filtro = new IrregolaritaDTO();
//		filtro.setIdProgetto(idProgetto);
//		LOG.info(prf + " ********************************* IRREGOLARI ********************************\n");
//		IrregolaritaDTO[] list = registroControlliDAOImpl.findIrregolarita(idUtente, idIride, filtro);
//		for(IrregolaritaDTO i: list) {
//			//LOG.info(prf + "Data campione: " + i.getDataCampione());
//			LOG.info(prf + "DtComunicazione: " + i.getDtComunicazione());
//			LOG.info(prf + "DataInizioControlli: " + i.getDataInizioControlli());
//			LOG.info(prf + "DataFineControlli: " + i.getDataFineControlli());
//			LOG.info(prf + "Tipo Controlli: " + i.getTipoControlliProvv());
//			LOG.info(prf + "ImportoAgevolazioneIrreg: " + i.getImportoAgevolazioneIrreg());
//			
//		}
////		
//		LOG.info(prf + "END");
//	}
//	
//	
//	@Test
//	public void  findEsitiRegolariTest() throws Exception{
//		String prf = "[RegistroControlliDAOImplTest :: findEsitiRegolariTest] ";
//		LOG.info(prf + "START");
//		//Long idProgetto = 14896L;
//		Long idProgetto = 19233L;
//		Long idUtente = getCurrentUserInfo().getIdUtente();
//		String idIride = getCurrentUserInfo().getIdIride();
//		IrregolaritaDTO filtro = new IrregolaritaDTO();
//		filtro.setIdProgetto(idProgetto);
//		IrregolaritaDTO[] list = registroControlliDAOImpl.findEsitiRegolari(idUtente, idIride, filtro);
//		LOG.info(prf + " ********************************* ESITI REGOLARI ********************************\n");
//		for(IrregolaritaDTO i: list) {
//			//LOG.info(prf + "Data campione: " + i.getDataCampione());
//			LOG.info(prf + "DtComunicazione: " + i.getDtComunicazione());
//			LOG.info(prf + "DataInizioControlli: " + i.getDataInizioControlli());
//			LOG.info(prf + "DataFineControlli: " + i.getDataFineControlli());
//			LOG.info(prf + "Tipo Controlli: " + i.getTipoControlliProvv());
//			LOG.info(prf + "ImportoAgevolazioneIrreg: " + i.getImportoAgevolazioneIrreg());
//			LOG.info(prf + "Motivo Irregolarita: " + i.getDescMotivoRevoca());
//			LOG.info(prf + "ImportoIrregolarita: " + i.getImportoIrregolarita());
//			
//			
//		}
////		
//		LOG.info(prf + "END");
//	}
//	
//	@Test
//	public void  findRettificheForfettarieTest() throws Exception{
//		String prf = "[RegistroControlliDAOImplTest :: findRettificheForfettarieTest] ";
//		LOG.info(prf + "START");
//		//Long idProgetto = 14896L;
//		Long idProgetto = 19233L;
//		Long idUtente = getCurrentUserInfo().getIdUtente();
//		String idIride = getCurrentUserInfo().getIdIride();
//		IrregolaritaDTO filtro = new IrregolaritaDTO();
//		filtro.setIdProgetto(idProgetto);
//		RettificaForfettariaDTO[] list = registroControlliDAOImpl.findRettificheForfettarie(idUtente, idIride, filtro);
//		LOG.info(prf + " ********************************* Rettifiche Forfettarie ********************************\n");
//		for(RettificaForfettariaDTO i: list) {
//			//LOG.info(prf + "Data campione: " + i.getDataCampione());
//			LOG.info(prf + "Nome File: " + i.getNomeFile());
//			
//		}
//		LOG.info(prf + "END");
//	}
//	
//	@Test
//	public void  findDettaglioEsitoRegolareTest() throws Exception{
//		String prf = "[RegistroControlliDAOImplTest :: findDettaglioEsitoRegolareTest] ";
//		LOG.info(prf + "START");
//		Long idUtente = getCurrentUserInfo().getIdUtente();
//		String idIride = getCurrentUserInfo().getIdIride();
//		Long idEsitoRegolare = 210L;
//		IrregolaritaDTO dettaglio = registroControlliDAOImpl.findDettaglioEsitoRegolare(idUtente, idIride, idEsitoRegolare);
//		LOG.info(prf + " ********************************* Dettaglio Esito Regolare ********************************\n");
//		
//		LOG.info(prf + "Id Progetto: " + dettaglio.getIdProgetto());
//		LOG.info(prf + "Tipo Controlli: " + dettaglio.getTipoControlli());
//		LOG.info(prf + "Codice Visualizzato : " + dettaglio.getCodiceVisualizzato());
//		LOG.info(prf + "Desc Breve Tipo Irregolarita : " + dettaglio.getDescBreveTipoIrregolarita());
//		
//		LOG.info(prf + "END");
//	}
//	
//	
//	
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	/////////////////////////////////////////// RETTIFICHE FORFEITTARIE SU AFFIDAMENTI ////////////////////////////////////////////////////
//	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	
//	@Test
//	public void  findChecklistRettificheForfettarieTest() throws Exception{
//		String prf = "[RegistroControlliDAOImplTest :: findChecklistRettificheForfettarieTest] ";
//		LOG.info(prf + "START");
//		//Long idProgetto = 14896L;
//		Long idProgetto = 19233L;
//		String idIride = getCurrentUserInfo().getIdIride();
//		Long idUtente = getCurrentUserInfo().getIdUtente();
//		ChecklistRettificaForfettariaDTO[] list = registroControlliDAOImpl.findChecklistRettificheForfettarie(idUtente, idIride, idProgetto);
//		LOG.info(prf + " ********************************* Checklist Rettifiche Forfettarie ********************************\n");
//		for(ChecklistRettificaForfettariaDTO c: list) {
//			LOG.info(prf + "Desc Tipologia Appalto: " + c.getDescTipologiaAppalto());
//			LOG.info(prf + "Oggetto Appalto: " + c.getOggettoAppalto());
//			LOG.info(prf + "Esito Intermedio: " + c.getEsitoIntermedio());
//			LOG.info(prf + "Nome File: " + c.getNomeFile());
//			LOG.info(prf + "Id Documento Index: " + c.getIdDocumentoIndex());
//						
//		}
//		LOG.info(prf + "END");
//	}
//	
//	@Test
//	public void  getContenutoDocumentoByIdTest() throws Exception{
//		String prf = "[RegistroControlliDAOImplTest :: getContenutoDocumentoByIdTest] ";
//		LOG.info(prf + "START");
//		//Long idProgetto = 14896L;
//		Long idProgetto = 19233L;
//		Long idDocIndex = 229917L;
//		String idIride = getCurrentUserInfo().getIdIride();
//		Long idUtente = getCurrentUserInfo().getIdUtente();
////		EsitoGenerazioneReportDTO esito = registroControlliDAOImpl.getContenutoDocumentoById(idDocIndex);
////		LOG.info(prf + " ********************************* GET CONTENUTO DOCUMENTO ********************************\n");
////		
////		LOG.info(prf + "Esito: " + esito.getEsito());
////		LOG.info(prf + "Nome documento: " + esito.getNomeDocumento());
//		
//		LOG.info(prf + "END");
//	}
//	
//	
//}
