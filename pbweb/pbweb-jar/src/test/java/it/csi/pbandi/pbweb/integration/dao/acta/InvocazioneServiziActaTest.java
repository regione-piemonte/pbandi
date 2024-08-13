/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.acta;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.business.manager.ActaManager;

@RunWith(PbwebJunitClassRunner.class)
public class InvocazioneServiziActaTest  extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(InvocazioneServiziActaTest.class);
	
	@Autowired
	ActaManager actaManager;
	
	@Before()
	public void before() {
		LOG.info("[InvocazioneServiziActaTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[InvocazioneServiziActaTest::after]START-STOP");
	}

	@Test
	public void inizializzazioneTest() {
		String prf = "[InvocazioneServiziActaTest::inizializzazioneTest] ";
		LOG.info( "\n\n\n" );
		LOG.info(  prf + "START");
		
		try {
//			actaManager.inizalizzaActa("ice", "descSettore");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		LOG.info(  prf + "END \n\n\n");
	}

}
