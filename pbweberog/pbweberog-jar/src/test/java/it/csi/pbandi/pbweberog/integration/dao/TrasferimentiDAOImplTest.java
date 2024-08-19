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
//import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.CausaleTrasferimentoDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.SoggettoTrasferimentoDTO;
//import it.csi.pbandi.pbservizit.pbandisrv.dto.trasferimenti.TrasferimentoDTO;
//import it.csi.pbandi.pbservizit.security.UserInfoSec;
//import it.csi.pbandi.pbweberog.base.PbweberogJunitClassRunner;
//import it.csi.pbandi.pbweberog.base.TestBaseService;
//import it.csi.pbandi.pbweberog.dto.trasferimenti.FiltroTrasferimento;
//import it.csi.pbandi.pbweberog.integration.dao.impl.TrasferimentiDAOImpl;
//
//
//@RunWith(PbweberogJunitClassRunner.class)
//public class TrasferimentiDAOImplTest extends TestBaseService{
//	protected static Logger LOG = Logger.getLogger(TrasferimentiDAOImplTest.class);
//	protected static UserInfoSec userInfo = getCurrentUserInfo();
//	
//	@Before()
//	public void before() {
//		LOG.info("[TrasferimentiDAOImplTest::before]START");
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
//		LOG.info("[TrasferimentiDAOImplTest::after]START");
//	}
//	
//	
//	@Autowired
//	TrasferimentiDAOImpl trasferimentiDAOImpl;
//	
//	
////	@Test
////	public void findSoggettiTrasferimentoTest() throws Exception {
////		String prf = "[TrasferimentiDAOImplTest :: findSoggettiTrasferimentoTest] ";
////		LOG.info(prf + "START");
////		SoggettoTrasferimentoDTO[] soggetti = trasferimentiDAOImpl.findSoggettiTrasferimento(getCurrentUserInfo().getIdUtente(),
////															getCurrentUserInfo().getIdIride());
////		for(SoggettoTrasferimentoDTO s: soggetti) {
////			LOG.info(prf + " soggetto trasferimento: " + s.getDenominazioneBeneficiario() + " " + s.getCfBeneficiario());
////		}
////		LOG.info(prf + "END");
////	}
////	
////	
//////	
//	@Test
//	public void findElencoCausaliTrasferimentiTest() throws Exception {
//		String prf = "[TrasferimentiDAOImplTest :: findElencoCausaliTrasferimentiTest] ";
//		LOG.info(prf + "START");
////		CausaleTrasferimentoDTO[] causali = trasferimentiDAOImpl.findElencoCausaliTrasferimenti(getCurrentUserInfo().getIdUtente(),
////															getCurrentUserInfo().getIdIride());
////		for(CausaleTrasferimentoDTO c: causali) {
////			LOG.info(prf + " causale trasferimento: " + c.getDescCausaleTrasferimento());
////		}
//		LOG.info(prf + "END");
//	}
////	
////	
////	@Test
////	public void ricercaTrasferimentiTest() throws Exception {
////		String prf = "[TrasferimentiDAOImplTest :: ricercaTrasferimentiTest] ";
////		LOG.info(prf + "START");
////		FiltroTrasferimento filtro = new FiltroTrasferimento();
////		filtro.setIdCausaleTrasferimento(1L);
////		filtro.setIdSoggettoBeneficiario(2117898L);
////		TrasferimentoDTO[] trasferimenti = trasferimentiDAOImpl.ricercaTrasferimenti(getCurrentUserInfo().getIdUtente(),getCurrentUserInfo().getIdIride(), filtro);
////		for(TrasferimentoDTO t: trasferimenti) {
////			LOG.info(prf + "  IdTrasferimento: " + t.getIdTrasferimento() + " Beneficiario: " + t.getFlagPubblicoPrivato());
////		}
////		LOG.info(prf + "END");
////	}
//	
//}
