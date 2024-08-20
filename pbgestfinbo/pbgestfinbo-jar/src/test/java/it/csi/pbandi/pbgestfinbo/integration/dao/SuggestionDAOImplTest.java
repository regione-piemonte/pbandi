/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbgestfinbo.base.PbgestfinboJunitClassRunner;
import it.csi.pbandi.pbgestfinbo.base.TestBaseService;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.SuggestionDAOImpl;

@RunWith(PbgestfinboJunitClassRunner.class)
public class SuggestionDAOImplTest  extends TestBaseService {
	
	protected static Logger LOG = Logger.getLogger(SuggestionDAOImplTest.class);
	
	@Autowired
	SuggestionDAOImpl suggestionDAOImpl;
	
	@Before()
    public void before() {
    	LOG.info("[SuggestionDAOImplTest::before]START");
    }

    @After
    public void after() {
    	LOG.info("[SuggestionDAOImplTest::after]START");
    }
    
    
    @Autowired
    SuggestionDAOImpl suggestionDaoImpl;

    @Test
    public void suggestDenominazioneTest() {
    	LOG.info("[SuggestionDAOImplTest::suggestionIdSoggettoTest]START");
    	
    	/*
    	 * 
    	 */
 
    }
}
