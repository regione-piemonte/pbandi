/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao;


import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbworkspace.base.PbworkspaceJunitClassRunner;
import it.csi.pbandi.pbworkspace.base.TestBaseService;
import it.csi.pbandi.pbworkspace.integration.dao.impl.AttivitaDAOImpl;

@RunWith(PbworkspaceJunitClassRunner.class)
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
    	/*
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
		*/
    }
    
    @Test
    public void getBandi() {
    	/*
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
		*/
    }
    
    @Test
    public void startAttivita() {
    	/*
    	String prf = "[AttivitaDAOImplTest::startAttivita] ";
    	LOG.info( prf + "START");
    	
    	boolean eseguoTest = false;
    	
    	Long idUtente = 25846L;
		Long idProgetto = 18026L;
		String identitaDigitale = "bbbb";
		String descBreveTask = "GEST-CHECKLIST";
		
    	try {
    		if(eseguoTest) {
	    		LOG.info( prf + "attivitaDAOImpl="+attivitaDAOImpl);
				String i = attivitaDAOImpl.startAttivita(idUtente, identitaDigitale, descBreveTask, idProgetto);
    		} else {
        		LOG.info( prf + " test skippato");
        	}
		} catch (Exception e) {
			LOG.error( prf + "errore "+e.getMessage());
			e.printStackTrace();
		}
		LOG.info( prf + "END");
		*/
    }
    
}
