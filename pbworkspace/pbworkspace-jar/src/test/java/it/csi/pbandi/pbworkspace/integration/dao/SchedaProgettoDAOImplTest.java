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
import it.csi.pbandi.pbworkspace.integration.dao.impl.SchedaProgettoDAOImpl;

@RunWith(PbworkspaceJunitClassRunner.class)
public class SchedaProgettoDAOImplTest  extends TestBaseService {
	
	protected static Logger LOG = Logger.getLogger(SchedaProgettoDAOImplTest.class);
	
	Long idUtente = new Long(21957);
	String idIride = "idIrideFinto";
	Long idSoggetto = new Long(2130091);
	
	 @Before()
    public void before() {
    	LOG.info("[SchedaProgettoDAOImplTest::before]START");
    }

    @After
    public void after() {
    	LOG.info("[SchedaProgettoDAOImplTest::after]START");
    }

    @Autowired
    SchedaProgettoDAOImpl schedaProgettoDAOImpl;
    
    
    @Test
    public void inizializzaSchedaProgetto() {
    	/*
    	String prf = "[SchedaProgettoDAOImplTest::inizializzaSchedaProgetto] ";
    	LOG.info( prf + "START");
    	
    	Long progrBandoLineaIntervento = 258L;
		Long idSoggetto = 2130090L;
		String codiceRuolo = "CREATOR";

		try {
			schedaProgettoDAOImpl.inizializzaSchedaProgetto(progrBandoLineaIntervento, idSoggetto, codiceRuolo, idUtente, idIride);
		} catch (Exception e) {
			LOG.error( prf + "errore "+e.getMessage());
			e.printStackTrace();
		}
		LOG.info( prf + "END");
		*/
    }
    
}
