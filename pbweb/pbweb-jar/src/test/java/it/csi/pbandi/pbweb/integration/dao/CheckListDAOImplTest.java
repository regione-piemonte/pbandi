/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.integration.dao.impl.CheckListDAOImpl;

@RunWith(PbwebJunitClassRunner.class)
public class CheckListDAOImplTest extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(DecodificheDAOImplTest.class);

	@Autowired
	CheckListDAOImpl checkListDao;

	private Long idUtente;
	private String identitaDigitale;
	
	@Before()
	public void before() {
		LOG.info("[CheckListDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[CheckListDAOImplTest::after]START-STOP");
	}

	@Test
	public void getModuloCheckListTest() {
		LOG.info("[CheckListDAOImplTest::getModuloCheckListTest]START");
		
		String soggettoControllore = "";
		Long idProgetto = new Long(18664);
		
//		checkListDao.getModuloCheckList(idUtente, identitaDigitale, idProgetto, soggettoControllore);
		
		LOG.info("[CheckListDAOImplTest::getModuloCheckListTest]STOP");
	}
}
