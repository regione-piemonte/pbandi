/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.base.PbservizitJunitClassRunner;
import it.csi.pbandi.pbservizit.base.TestBaseService;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.integration.dao.impl.ProfilazioneDAOImpl;

//import javax.ws.rs.core.Response;

@RunWith(PbservizitJunitClassRunner.class)
public class UserApiDAOImplTest extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(UserApiDAOImplTest.class);
	
	@Before()
    public void before() {
    	LOG.info("START");
    }

    @After
    public void after() {
    	LOG.info("END");
    }

    @Autowired
    ProfilazioneDAOImpl profilazioneDAOimpl;
    
    @Test
    public void findSoggettoTest() {
    	LOG.info("[UserApiDAOImplTest::findSoggettoTest] BEGIN");
    	String codFiscale = "PCHMRC72A05H355F";
//    	try {
//    		LOG.info("[UserApiDAOImplTest::findSoggettoTest] soggetto="+
//    					profilazioneDAOimpl.findSoggetto(codFiscale));
//			
//		} catch (DaoException e) {
//			e.printStackTrace();
//		}
    }
    
}
