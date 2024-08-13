/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.integration.dao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.business.gestionebackoffice.GestioneBackofficeBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico;
import it.csi.pbandi.pbwebbo.base.PbwebboJunitClassRunner;
import it.csi.pbandi.pbwebbo.base.TestBaseService;
import it.csi.pbandi.pbwebbo.business.impl.GestioneBackofficeBusinessWebboImpl;
import javax.wsdl.xml.WSDLReader; // NON deve essere axis-wsdl4j-1.5.1.jar ma  wsdl4j-1.6.2.jar come in pbweb
import it.csi.bilsrvrp.cmpsrvrp.dto.bilancio.attiliquid.ConsultaAttoLiquidazioneDTO;


@RunWith(PbwebboJunitClassRunner.class)
public class ConfigurazioneBandoLineaTest  extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(ConfigurazioneBandoLineaTest.class);
	
//	@Autowired
//	private GestioneBackofficeBusinessImpl gestioneBackofficeSrv;
	
	@Autowired
	private GestioneBackofficeBusinessWebboImpl gestioneBackofficeBusinessWebboImpl;
	
	@Before()
   public void before() {
   	LOG.info("[ConfigurazioneBandoLineaTest::before]START");
   }

   @After
   public void after() {
   	LOG.info("[ConfigurazioneBandoLineaTest::after]START");
   }
   
   @Test
   public void verificaParolaChiaveActaTest() {
   	String prf = "[ConfigurazioneBandoLineaTest::verificaParolaChiaveActaTest] ";
   	LOG.info( prf + "START");
   	
   	GestioneBackOfficeEsitoGenerico esito = null;
   	
   	try {
		
		Long progrBandoLineaEnteCompetenza = 1275L;
		Long idU = 27345L; //DEMO34
		String idIride = "AAAAAA00A11O000W/CSI PIEMONTE/DEMO 34/ACTALIS_EU/20210209102032/16";
		
		//esito = gestioneBackofficeSrv.verificaParolaChiaveActa(idU, idIride, progrBandoLineaEnteCompetenza);
		esito = gestioneBackofficeBusinessWebboImpl.verificaParolaChiaveActa(idU, idIride, progrBandoLineaEnteCompetenza);
		
		LOG.debug(prf + " esito="+esito);
	}catch (Exception e) {	
		
		e.printStackTrace();
		LOG.error(prf + ": "+e);
		e.getStackTrace();
		esito = new GestioneBackOfficeEsitoGenerico();
		esito.setEsito(false);
		esito.setMessage(e.getMessage());
	}
	LOG.debug(prf + " END");
   	
   }
	   
}
