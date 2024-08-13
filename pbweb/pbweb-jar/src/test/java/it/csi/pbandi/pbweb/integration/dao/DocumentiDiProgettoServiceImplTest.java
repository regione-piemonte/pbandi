/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.business.service.impl.DocumentiDiProgettoServiceImpl;

@RunWith(PbwebJunitClassRunner.class)
public class DocumentiDiProgettoServiceImplTest extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(DocumentiDiProgettoServiceImplTest.class);

	@Autowired
	private DocumentiDiProgettoServiceImpl documentiDiProgettoServiceImpl;

	@Test
	public void leggiFileActaTest() {
		String prf = "[DocumentiDiProgettoServiceImplTest::leggiFileActaTest] ";
		LOG.info(prf + " BEGIN");
		
		Long idDocumentoIndex = 353391L;
				
		try {
			Response oo = documentiDiProgettoServiceImpl.leggiFileActa(idDocumentoIndex, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
