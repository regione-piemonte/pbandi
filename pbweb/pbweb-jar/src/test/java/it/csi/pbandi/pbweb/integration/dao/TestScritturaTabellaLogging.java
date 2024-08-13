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
import it.csi.pbandi.pbweb.base.TestBaseMonitoraggioService;
import it.csi.pbandi.pbweb.integration.monitoraggio.GestioneLog;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.PrincipalExtResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;

@RunWith(PbwebJunitClassRunner.class)
public class TestScritturaTabellaLogging  extends TestBaseMonitoraggioService {
	
	protected static Logger LOG = Logger.getLogger(TestScritturaTabellaLogging.class);

	@Autowired
	private GestioneLog logMonitoraggioService;
		
	private ObjectIdType repositoryId;
	private PrincipalExtResponseType principal; 
	
	private final static Long ID_LOG_MONITORAGGIO_UTENTE = -1L; 
	private final static Long ID_LOG_TARGHET = 21L; 
	private final static Long ID_LOG_ENTITA = 3L; 
	private final static Long ID_URL_SERVICE_BACK_OFFICE_GET_PRINCIPAL_EXT = 25L; //backOfficeServicePort.getPrincipalExt
	private final static Long ID_URL_SERVICE_BACK_OFFICE_QUERY = 29L;//backOfficeServicePort.query,
	private final static Long ID_URL_SERVICE_DOCUMENT_CREA_DOCUMENTO = 23L;  //documentServicePort.creaDocumento
    private final static Long ID_URL_SERVICE_OBJECT_CREATE_FOLDER = 32L; //objectServicePort.createFolder
    private final static Long ID_URL_SERVICE_OBJECT_QUERY = 26L; //objectServicePort.query
    private final static Long ID_URL_SERVICE_OFFICIAL_BOOK_CREA_REGISTRAZIONE = 24L; //officialBookServicePort.creaRegistrazione
    private final static Long ID_URL_SERVICE_OFFICIAL_BOOK_GET_REGISTRIES = 27L; //officialBookServicePort.getRegistries
    private final static Long ID_URL_SERVICE_OFFICIAL_BOOK_QUERY = 34L; //officialBookServicePort.query
    private final static Long ID_URL_SERVICE_REPOSITORY_GET_REPOSITORY = 28L; //repositoryServicePort.getRepositories
    private final static Long ID_URL_SERVICE_MANAGEMENT= 21L; //http://localhost/actasrv/managementWS?wsdl
    private final static Long ID_URL_SERVICE_NAVIGATION= 21L; //http://localhost/actasrv/navigationWS?wsdl
    private final static Long ID_URL_SERVICE_RELATIONSHIPS= 21L; //http://localhost/actasrv/relationshipsWS?wsdl
    private final static String MOD_CHIAMATA_INSERT = "W";
    private final static String MOD_CHIAMATA_UPDATE = "U";
    private final static String MOD_CHIAMATA_READ = "R";
    private final static String CODICE_ERRORE = "XXXXX";
	
				
	@Before()
	public void before() {
		LOG.info("[DocumentiDiProgettoCXFDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[DocumentiDiProgettoCXFDAOImplTest::after]START-STOP");
	}
	
	@Test
	public void testTracciamento() {
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaCXF] ";
		LOG.info(prf + " BEGIN");
		
		
		Long rispostaLogMonitoraggioNavigation = null;
		try {
			rispostaLogMonitoraggioNavigation = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_DOCUMENT_CREA_DOCUMENTO, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_INSERT, ID_LOG_ENTITA, ID_LOG_TARGHET, "TEST INPUT");
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioNavigation, "OK","","","Risposta OK");
			LOG.info(prf + " END");
			
		}catch(Exception e) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioNavigation, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}
	
	
}
