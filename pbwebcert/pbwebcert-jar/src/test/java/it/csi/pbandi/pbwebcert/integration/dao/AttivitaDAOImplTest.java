/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.dao;


import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.integration.dao.impl.AttivitaDAOImpl;
import it.csi.pbandi.pbwebcert.base.PbwebcertJunitClassRunner;
import it.csi.pbandi.pbwebcert.base.TestBaseService;

@RunWith(PbwebcertJunitClassRunner.class)
public class AttivitaDAOImplTest  extends TestBaseService {
	
	protected static Logger LOG = Logger.getLogger(AttivitaDAOImplTest.class);
	
	 @Before()
    public void before() {
    	LOG.info("[AttivitaDAOImplTest::before]START");
    }

    @After
    public void after() {
    	LOG.info("[AttivitaDAOImplTest::after]START");
    }

    @Autowired
    AttivitaDAOImpl attivitaDAOImpl;
    
    
    @Test
    public void getProgettiWithProceduresTest() {
    	String prf = "[AttivitaDAOImplTest::getProgettiWithProceduresTest] ";
    	LOG.info( prf + "START");
    	
    	Long progrBandoLineaIntervento = 271L;
		Long idSoggettoBen = 3499L;
		Long idUtente = 25846L;
		
//		try {
//			attivitaDAOImpl.getProgettiWithProcedures(progrBandoLineaIntervento, idSoggettoBen, idUtente);
//		} catch (Exception e) {
//			LOG.error( prf + "errore "+e.getMessage());
//			e.printStackTrace();
//		}
		LOG.info( prf + "END");
    }
    
    @Test
    public void getBandi() {
    	String prf = "[AttivitaDAOImplTest::getBandi] ";
    	LOG.info( prf + "START");
    	
    	Long idSoggetto = 2139302L;
		Long idBeneficiario = 3499L;
		String codiceRuolo = "BEN-MASTER";
		
//    	try {
//			
//			List<BandoVO> lisBandi = attivitaDAOImpl.getBandi(codiceRuolo, idSoggetto, idBeneficiario);
//			
//			LOG.info(prf + "bandi trovati: " + (lisBandi != null ? lisBandi.size() : 0));
//			
//		} catch (Exception e) {
//			LOG.error( prf + "errore "+e.getMessage());
//			e.printStackTrace();
//		}
		LOG.info( prf + "END");
		
    }
    
}
