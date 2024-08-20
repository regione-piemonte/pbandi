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
//import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.TipoIndicatoreDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
//import it.csi.pbandi.pbservizit.security.UserInfoSec;
//import it.csi.pbandi.pbwebrce.base.PbwebrceJunitClassRunner;
//import it.csi.pbandi.pbwebrce.integration.dao.impl.IndicatoriDAOImpl;
//
//@RunWith(PbwebrceJunitClassRunner.class)
//public class IndicatoriDAOImplTest {
//	
//	protected static Logger LOG = Logger.getLogger(CronoProgrammaDAOImplTest.class);
//	protected static UserInfoSec userInfo = getCurrentUserInfo();
//	
//	@Before()
//	public void before() {
//		LOG.info("[IndicatoriDAOImplTest::before]START");
//	}
//
//	@After
//	public void after() {
//		LOG.info("[IndicatoriDAOImplTest::after]START");
//	}
//
//	@Autowired
//	IndicatoriDAOImpl indicatoriDAOImpl;
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
//	@Test
//	public void findIndicatoriGestioneTest() throws FormalParameterException, UnrecoverableException {
//		String prf = "[IndicatoriDAOImplTest::findIndicatoriGestione]";
//		LOG.info(prf + " START");
//		Long idProgetto = 19233L;
//		TipoIndicatoreDTO[] indicatori  = indicatoriDAOImpl.findIndicatoriGestione(getCurrentUserInfo().getIdUtente(), getCurrentUserInfo().getIdIride(), idProgetto, false);
//		for(TipoIndicatoreDTO i: indicatori) {
//			LOG.info(prf + " indicatore: "+ i.getDescTipoIndicatore());
//		}
//		LOG.info(prf + " END");
//	}
//	
//}
