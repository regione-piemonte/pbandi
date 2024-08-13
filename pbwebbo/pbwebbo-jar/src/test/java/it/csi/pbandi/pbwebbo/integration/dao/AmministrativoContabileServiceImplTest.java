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

import it.csi.pbandi.pbwebbo.base.PbwebboJunitClassRunner;
import it.csi.pbandi.pbwebbo.base.TestBaseService;
import it.csi.pbandi.pbwebbo.business.service.impl.AmministrativoContabileServiceImpl;

@RunWith(PbwebboJunitClassRunner.class)
public class AmministrativoContabileServiceImplTest extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(AmministrativoContabileServiceImplTest.class);
	
	@Autowired
	private AmministrativoContabileServiceImpl amministrativoContabileServiceImpl;
	
	@Before()
	public void before() {
		LOG.info("[AmministrativoContabileServiceImplTest::before]START");
	}

   @After
   public void after() {
   		LOG.info("[AmministrativoContabileServiceImplTest::after]START");
   }
	   
   @Test
   public void callAmmContTest() {
	   String prf = "[AmministrativoContabileServiceImplTest::callAmmContTest] ";
   	   LOG.info( prf + "START");
   		
		Long idBando = 0L;
		Long idUtente = 0L; 
		String iban = "xx";
		String titoloBando = "yy";
		Long idEnteCompetenza = 0L; 
		Long idModalitaAgev = 0L;
		String moltiplicatore = "zz";
		String tipologiaConto = "kk";
   		
//   	   try {
//   		   amministrativoContabileServiceImpl.callAmmCont(idBando, idUtente, iban, titoloBando, idEnteCompetenza, idModalitaAgev, moltiplicatore, tipologiaConto);
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
   	
   	   LOG.info( prf + "END");
   }
   
}
