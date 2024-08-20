/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

//package it.csi.pbandi.pbwebrce.integration.dao;
//
//import org.apache.log4j.Logger;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import it.csi.csi.wrapper.UnrecoverableException;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.EsitoFindFasiMonitoraggio;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.IterDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.MotivoScostamentoDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionecronoprogramma.TipoOperazioneDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
//import it.csi.pbandi.pbservizit.security.UserInfoSec;
//import it.csi.pbandi.pbwebrce.base.PbwebrceJunitClassRunner;
//import it.csi.pbandi.pbwebrce.base.TestBaseService;
//import it.csi.pbandi.pbwebrce.integration.dao.impl.CronoProgrammaDAOImpl;
//
//@RunWith(PbwebrceJunitClassRunner.class)
//public class CronoProgrammaDAOImplTest  extends TestBaseService {
//	protected static Logger LOG = Logger.getLogger(CronoProgrammaDAOImplTest.class);
//	protected static UserInfoSec userInfo = getCurrentUserInfo();
//	
//	@Before()
//	public void before() {
//		LOG.info("[AttivitaDAOImplTest::before]START");
//	}
//
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
//	@After
//	public void after() {
//		LOG.info("[AttivitaDAOImplTest::after]START");
//	}
//
//	@Autowired
//	CronoProgrammaDAOImpl cronoProgrammaDAOImpl;
//	
//	
//	@Test
//	public void getProgrammazioneByIdProgettoTest() throws FormalParameterException {
//		String prf = "[CronoProgrammaDAOImplTest::getProgrammazioneByIdProgettoTest]";
//		LOG.info(prf + " START");
//		Long idProgetto = 19233L;
//		String programmazione  = cronoProgrammaDAOImpl.findProgrammazioneByIdProgetto(getCurrentUserInfo().getIdUtente(), getCurrentUserInfo().getIdIride(), idProgetto);
//		LOG.info(prf + " programmazione: "+ programmazione);
//		LOG.info(prf + " END");
//	}
//	
//
//	@Test
//	public void findTipoOperazioneTest() throws FormalParameterException, UnrecoverableException {
//		String prf = "[CronoProgrammaDAOImplTest::findTipoOperazioneTest]";
//		LOG.info(prf + " START");
//		Long idProgetto = 19233L;
//		String programmazione = "2016";
//		TipoOperazioneDTO[] tipiOperazione = cronoProgrammaDAOImpl.findTipoOperazione(getCurrentUserInfo().getIdUtente(), getCurrentUserInfo().getIdIride(), idProgetto, programmazione);
//		
//		for(TipoOperazioneDTO t: tipiOperazione) {
//			LOG.info(prf + " tipoOperazione: "+ t.getDescTipoOperazione());
//		}
//	
//		LOG.info(prf + " END");
//	}
//	
//
//	@Test
//	public void findFasiMonitoraggioGestioneTest() throws FormalParameterException, UnrecoverableException {
//		String prf = "[CronoProgrammaDAOImplTest::findFasiMonitoraggioGestioneTest]";
//		boolean eseguoTest = false;
//		if(eseguoTest) {
//			LOG.info(prf + " START");
//			Long idProgetto = 19233L;
//			String programmazione = "2016";
//			EsitoFindFasiMonitoraggio esito = cronoProgrammaDAOImpl
//								.findFasiMonitoraggioGestione(getCurrentUserInfo().getIdUtente(), getCurrentUserInfo().getIdIride(), idProgetto, programmazione);
//			
//			LOG.info(prf + " success: "+ esito.getSuccesso() + " desc tipo operazione "+ esito.getDescTipoOperazione());
//			LOG.info(prf + " END");
//		}
//	}
//	
//	
//	@Test
//	public void findMotivoScostamentoTest() throws FormalParameterException, UnrecoverableException {
//		String prf = "[CronoProgrammaDAOImplTest::findMotivoScostamentoTest]";
//		LOG.info(prf + " START");
//		MotivoScostamentoDTO[] dto = cronoProgrammaDAOImpl.findMotivoScostamento(getCurrentUserInfo().getIdUtente(), getCurrentUserInfo().getIdIride());
//		
//		for(MotivoScostamentoDTO t: dto) {
//			LOG.info(prf + " DescMotivoScostamento: "+ t.getDescMotivoScostamento() + " CodIgrueT37T49T53" + t.getCodIgrueT37T49T53());
//		}
//	
//		LOG.info(prf + " END");
//	}
//	
//	
//	@Test
//	public void findIterTest() throws FormalParameterException, UnrecoverableException {
//		String prf = "[CronoProgrammaDAOImplTest::findIterTest]";
//		LOG.info(prf + " START");
//		Long idUtente = getCurrentUserInfo().getIdUtente();
//		String idIride = getCurrentUserInfo().getIdIride();
//		String programmazione = "2016";
//		IterDTO[] dto = cronoProgrammaDAOImpl.findIter(idUtente, idIride,  idUtente, programmazione);
//		
//		for(IterDTO t: dto) {
//			LOG.info(prf + " DescIter: "+ t.getDescIter() + " CodIgrueT35" + t.getCodIgrueT35());
//		}
//	
//		LOG.info(prf + " END");
//	}
//	
////	@Test
////	public void salvaFasiMonitoraggioGestioneTest() throws FormalParameterException, UnrecoverableException {
////		String prf = "[CronoProgrammaDAOImplTest::salvaFasiMonitoraggioGestioneTest]";
////		LOG.info(prf + " START");
////		Long idUtente = getCurrentUserInfo().getIdUtente();
////		String idIride = getCurrentUserInfo().getIdIride();
////		String programmazione = "2016";
////		Long idProgetto = 19233L;
////		
////		FaseMonitoraggioDTO dto = new FaseMonitoraggioDTO();
////		dto.setIdIter(3L);
////		dto.setIdFaseMonit(31L);
////		
////		FaseMonitoraggioDTO[] fasiDto = {dto};
////		
////		
////		EsitoSaveFasiMonitoraggio esito = cronoProgrammaDAOImpl.salvaFasiMonitoraggioGestione(idUtente, idIride, idProgetto, fasiDto );
////		
////		LOG.info(prf + " Messagi: "+ esito.getMessaggi() + " successo" + esito.getSuccesso());
////		
////	
////		LOG.info(prf + " END");
////	}
//	
//	
//
//}
