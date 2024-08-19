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
//import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
//import it.csi.pbandi.pbservizit.security.UserInfoSec;
//import it.csi.pbandi.pbweberog.base.PbweberogJunitClassRunner;
//import it.csi.pbandi.pbweberog.base.TestBaseService;
//import it.csi.pbandi.pbweberog.dto.disimpegni.RevocaDTO;
//import it.csi.pbandi.pbweberog.integration.dao.impl.GestioneDisimpegniDAOImpl;
//
//@RunWith(PbweberogJunitClassRunner.class)
//public class GestioneDisimpegniDAOImplTest extends TestBaseService  {
//	protected static Logger LOG = Logger.getLogger(GestioneDisimpegniDAOImplTest.class);
//	protected static UserInfoSec userInfo = getCurrentUserInfo();
//	
//	@Autowired
//	GestioneDatiGeneraliBusinessImpl datiGeneraliBusinessImpl;
//	@Autowired
//	GestioneDisimpegniDAOImpl gestioneDisimpegniDAOImpl;
//	
//	
//	@Before()
//	public void before() {
//		LOG.info("[GestioneDisimpegniDAOImplTest::before]START");
//	}
//	@After()
//	public void after() {
//		LOG.info("[GestioneDisimpegniDAOImplTest::after]START");
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
//	
//	@Test
//	public void findRiepilogoRevocheTest() throws Exception {
//		String prf = "[GestioneDisimpegniDAOImplTest :: findRiepilogoRevocheTest] ";
//		LOG.info(prf + "START");
//		Long idUtente = getCurrentUserInfo().getIdUtente();
//		String idIride = getCurrentUserInfo().getIdIride();
//		Long idProgetto = 19233L;
//		RevocaDTO filtro = new RevocaDTO();
//		filtro.setIdProgetto(idProgetto);
//		it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] modalita =  gestioneDisimpegniDAOImpl.findRiepilogoRevoche(idUtente, idIride, filtro);
//		for(it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO m: modalita) {
//			LOG.info(prf + "descBreve: "+ m.getDescBreveModalitaAgevolaz());
//		}
//		LOG.info(prf + "END");
//	}
//	
//	
//	
//	
//}
