/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.integration.dao.acta.TestUtilsCXF;
import it.csi.pbandi.pbweb.integration.monitoraggio.GestioneLog;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
import it.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.doqui.acta.acaris.backofficeservice.BackOfficeServicePort;
import it.doqui.acta.acaris.navigationservice.NavigationService;
import it.doqui.acta.acaris.navigationservice.NavigationServicePort;
import it.doqui.acta.acaris.objectservice.ObjectService;
import it.doqui.acta.acaris.objectservice.ObjectServicePort;
import it.doqui.acta.acaris.relationshipsservice.RelationshipsService;
import it.doqui.acta.acaris.relationshipsservice.RelationshipsServicePort;
import it.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.DettaglioAOOType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.PrincipalExtResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.CodiceFiscaleType;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumQueryOperator;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdAOOType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdNodoType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdStrutturaType;
import it.doqui.acta.actasrv.dto.acaris.type.common.NavigationConditionInfoType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PagingResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyFilterType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryConditionType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryableObjectType;

@RunWith(PbwebJunitClassRunner.class)
public class DocumentiDiProgettoCXFDAOImplTest  extends TestBaseService {
	
	protected static Logger LOG = Logger.getLogger(DocumentiDiProgettoCXFDAOImplTest.class);

	@Autowired
	private GestioneLog logMonitoraggioService;
	
	
	// PK : servono i seguenti 5 servizi ACTA
	private BackOfficeServicePort backofficeServicePort; 
	private RepositoryServicePort repositoryServicePort; 
	private ObjectServicePort objectServicePort; 
	private NavigationServicePort navigationServicePort;
	private RelationshipsServicePort relationshipsServicePort;
	
	private ObjectIdType repositoryId;
	private PrincipalExtResponseType principal; 
	
	static String actaRepositoryName = "RP201209 Regione Piemonte - Agg. 09/2012";
	
	private final static Long ID_LOG_MONITORAGGIO_UTENTE = -1L; 
	private final static Long ID_LOG_TARGHET = 21L; 
	private final static Long ID_LOG_ENTITA = 3L; 
	private final static Long ID_URL_SERVICE_BACK_OFFICE = 21L; //http://localhost/actasrv/backofficeWS?wsdl
	private final static Long ID_URL_SERVICE_DOCUMENT = 21L;  //http://localhost/actasrv/documentWS?wsdl
    private final static Long ID_URL_SERVICE_OBJECT = 21L; //http://localhost/actasrv/objectWS?wsdl
    private final static Long ID_URL_SERVICE_OFFICIAL_BOOK = 21L; //http://localhost/actasrv/officialbookWS?wsdl
    private final static Long ID_URL_SERVICE_REPOSITORY = 21L; //http://localhost/actasrv/repositoryWS?wsdl
    private final static Long ID_URL_SERVICE_MANAGEMENT= 21L; //http://localhost/actasrv/managementWS?wsdl
    private final static Long ID_URL_SERVICE_NAVIGATION= 21L; //http://localhost/actasrv/navigationWS?wsdl
    private final static Long ID_URL_SERVICE_RELATIONSHIPS= 21L; //http://localhost/actasrv/relationshipsWS?wsdl
    private final static String MOD_CHIAMATA_INSERT = "I";
    private final static String MOD_CHIAMATA_UPDATE = "U";
    private final static String MOD_CHIAMATA_READ = "R";
    private final static String CODICE_ERRORE = "XXXXX";
	
	// presenti sul DB (master)
//	static long idAoo = 237L; 			// (DB1600 Attività produttive)
//	static long idStruttura = 795L; 	//(DB1600 DB1600-ATTIVITA' PRODUTTIVE)
//	static long idNodo = 810L;
	// AcarisException: ACARIS - Il Folder specificato non è accessibile dal principal specificato
	
//	static long idAoo = 278L;			//  (A19000 COMPETITIVITA' DEL SISTEMA REGIONALE)
//	static long idStruttura = 1047L; 	//(DB1600 DB1600-ATTIVITA' PRODUTTIVE)
//	static long idNodo = 1130L; 			// (A19000-R Responsabile)
	
	
	static String codiceFiscale = "BNDRGP15R10L219Q";
	static String actaUtenteAppkey = "-68/-37/17/-98/89/-124/-38/21/-6/-118/-52/-21/74/-42/1/0";
	
	
	//applications: PBANDI-REGP --> TODO vanno lette da configurazione
	static String CONSUMER_KEY = "eccM1dwlfgBLae05KFnPFzf0lV4a";
	static String CONSUMER_SECRET = "OgJacFR0CV7MiDTZMBOEAYPQXrQa";
	static String OAUTH_URL = "http://tst-<da sostiure con il VH di esposizione API>/api/token";
	static String endpointBackOfficeService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_backoffice-T/1.0";
	static String endpointRepositoryService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_repository-T/1.0";
	static String endpointObjectService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_object-T/1.0";
	static String endpointNavigationService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_navigation-T/1.0";
	static String endpointRelationshipsService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_relationships-T/1.0";
	
	private boolean verboseOutput = true;
	 
	long idAoo;
	long idStruttura;
	long idNodo;
			
	
	@Before()
	public void before() {
		LOG.info("[DocumentiDiProgettoCXFDAOImplTest::before]START-STOP");
	}

	@After
	public void after() {
		LOG.info("[DocumentiDiProgettoCXFDAOImplTest::after]START-STOP");
	}
	

	@Test
	public void inizializzaCXF3() {
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaCXF3] ";
		LOG.info(prf + " BEGIN");
		
		try {
			
			inizializzaBackOfficeService();
			inizializzaRepositoryService();
			inizializzaObjectService();
			inizializzaNavigationService();
			inizializzaRelationshipsService();
			
			
			PrincipalIdType pr = getPrincipalId();
			LOG.debug(prf + ">>>>>>>>>>>>>>>>>>>>pr= " + pr.getValue());
			
			long idAoo = 237L; 			// (DB1600 Attività produttive)
			long idStruttura = 795L; 	//(DB1600 DB1600-ATTIVITA' PRODUTTIVE)
			long idNodo = 810L;
			
			PrincipalExtResponseType principale = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
//			PrincipalExtResponseType principale2 = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, null, null, actaUtenteAppkey, backofficeServicePort);
			
			PrincipalIdType pr2 = getPrincipalId();
			LOG.debug(prf + ">>>>>>>>>>>>>>>>>>>>p2r= " + pr2.getValue());
			
			
			if(pr.getValue().equals(pr2.getValue())){
				LOG.debug(prf + ">>>>>>>>>>>>>>>>>>>> UGUALI");
			}else {
				LOG.debug(prf + ">>>>>>>>>>>>>>>>>>>> DIVERSI");
			}

	        PagingResponseType pagingResponse = null;
	        try {
	        	QueryableObjectType target = new QueryableObjectType();
			    target.setObject("AooStrNodoView");
			    PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
				
				QueryConditionType[] criteria = { TestUtilsCXF.buildQueryCondition("codiceStruttura", EnumQueryOperator.EQUALS, "A299000"), 
												   TestUtilsCXF.buildQueryCondition("flagResponsabile", EnumQueryOperator.EQUALS, "S")};
				 
				NavigationConditionInfoType navigationLimits = new NavigationConditionInfoType();
				Integer maxItems = null;
		        Integer skipCount = 0;
		        
				pagingResponse = objectServicePort.query(getRepositoryId(), pr, target, filter, criteria, navigationLimits, maxItems, skipCount);
				LOG.debug(prf + "pagingResponse= " + pagingResponse);
	        } catch (Exception acEx) {
	        	
	        	// Il tipo di oggetto richiesto non è stato inserito tra gli oggetti interrogabili per l’Ente di appartenenza.
	        	acEx.printStackTrace();
	        	throw new Exception(acEx);
	        }
		        
		        
			/* PK FORSE serve per iniettare il bearer
			   
		    @SuppressWarnings("unchecked")
			Map<String,Object> hs0 = (Map<String,Object>) map.get(MessageContext.HTTP_REQUEST_HEADERS);

		    System.out.println("hs0 " + hs0);

		    Map<String,Object> hs = null;
		    if (hs0 != null)
		    	hs = hs0;
		    else
		    	hs = new HashMap<String,Object>();
		    
		    hs.put("Authorization", Arrays.asList("Bearer 1b6d47572990b484977695945977389b"));
		    map.put(MessageContext.HTTP_REQUEST_HEADERS, hs);	
		    
		    */
			
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}
	
	private void inizializzaRelationshipsService() {
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaRelationshipsService] ";
		LOG.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-RelationshipsService.wsdl");
			RelationshipsService rls = new  RelationshipsService(url);
			RelationshipsServicePort port = rls.getRelationshipsServicePort();
			LOG.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointRelationshipsService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			LOG.debug(prf + " oauth= "+oauth);
			
			LOG.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			LOG.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			LOG.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointRelationshipsService);
			gwfb.setWrappedInterface(RelationshipsServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			relationshipsServicePort = null;
			try {
				Object o = gwfb.create();
				relationshipsServicePort = (RelationshipsServicePort) o;
			} catch (Exception ex) {
				LOG.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			LOG.debug(prf + " relationshipsServicePort= "+relationshipsServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}

	private void inizializzaNavigationService() {
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaNavigationService] ";
		LOG.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-NavigationService.wsdl");
			NavigationService os = new  NavigationService(url);
			NavigationServicePort port = os.getNavigationServicePort();
			LOG.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointNavigationService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			LOG.debug(prf + " oauth= "+oauth);
			
			LOG.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			LOG.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			LOG.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointNavigationService);
			gwfb.setWrappedInterface(NavigationServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			navigationServicePort = null;
			try {
				Object o = gwfb.create();
				navigationServicePort = (NavigationServicePort) o;
			} catch (Exception ex) {
				LOG.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			LOG.debug(prf + " navigationServicePort= "+navigationServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}

	private void inizializzaObjectService() {
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaObjectService] ";
		LOG.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-ObjectService.wsdl");
			ObjectService os = new  ObjectService(url);
			ObjectServicePort port = os.getObjectServicePort();
			LOG.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointObjectService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			LOG.debug(prf + " oauth= "+oauth);
			
			LOG.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			LOG.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			LOG.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointObjectService);
			gwfb.setWrappedInterface(ObjectServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			objectServicePort = null;
			try {
				Object o = gwfb.create();
				objectServicePort = (ObjectServicePort) o;
			} catch (Exception ex) {
				LOG.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			LOG.debug(prf + " objectServicePort= "+objectServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}

	private void inizializzaRepositoryService() {
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaRepositoryService] ";
		LOG.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-RepositoryService.wsdl");
			RepositoryService rs = new  RepositoryService(url);
			RepositoryServicePort port = rs.getRepositoryServicePort();
			LOG.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointRepositoryService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			LOG.debug(prf + " oauth= "+oauth);
			
			LOG.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			LOG.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			LOG.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointRepositoryService);
			gwfb.setWrappedInterface(RepositoryServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			repositoryServicePort = null;
			try {
				Object o = gwfb.create();
				repositoryServicePort = (RepositoryServicePort) o;
			} catch (Exception ex) {
				LOG.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			LOG.debug(prf + " repositoryServicePort= "+repositoryServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}

	private void inizializzaBackOfficeService() {

		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaBackOfficeService] ";
		LOG.info(prf + " BEGIN");
		
		try {
			//URL url = new URL("http://localhost/actasrv/backofficeWS?wsdl");
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-BackOfficeService.wsdl");
			BackOfficeService bs = new  BackOfficeService(url);
			BackOfficeServicePort port = bs.getBackOfficeServicePort();
			LOG.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointBackOfficeService);
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			LOG.debug(prf + " oauth= "+oauth);
			
			LOG.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			LOG.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			LOG.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointBackOfficeService);
			gwfb.setWrappedInterface(BackOfficeServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			backofficeServicePort = null;
			try {
				Object o = gwfb.create();
	//			inspect(o);
				backofficeServicePort = (BackOfficeServicePort) o;
			} catch (Exception ex) {
				LOG.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			LOG.debug(prf + " backofficeServicePort= "+backofficeServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}

	@Test
	public void inizializzaCXF2() {
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaCXF2] ";
		LOG.info(prf + " BEGIN");
		
		try {
			URL url = new URL("http://localhost/actasrv/backofficeWS?wsdl");
			BackOfficeService bs = new  BackOfficeService(url);
			backofficeServicePort = bs.getBackOfficeServicePort();
			LOG.info(prf + " backofficeServicePort="+backofficeServicePort);
//			// con lib actasrv-client.5.0.0.jar  --> client cxf-2.2.3
//			//ERRORE : Exception e=class it.doqui.acta.actasrv.dto.acaris.type.backoffice.GetProperties do not have a property of the name {backoffice.acaris.acta.doqui.it}repositoryId
//			
//			
			URL url2 = new URL("http://localhost/actasrv/repositoryWS?wsdl");
			RepositoryService rs = new RepositoryService(url2);
			repositoryServicePort = rs.getRepositoryServicePort();
			LOG.info(prf + " repositoryServicePort="+repositoryServicePort);
//			
			URL url3 = new URL("http://localhost/actasrv/objectWS?wsdl");
			ObjectService os = new ObjectService(url3);
			objectServicePort = os.getObjectServicePort();
			LOG.info(prf + " objectServicePort="+objectServicePort);
//			// con lib actasrv-client.5.0.0.jar
//			//ERRORE :Exception e=class it.doqui.acta.actasrv.dto.acaris.type.common.Query do not have a property of the name {common.acaris.acta.doqui.it}repositoryId
			
			URL url4 = new URL("http://localhost/actasrv/navigationWS?wsdl");
			NavigationService ns = new NavigationService(url4);
			navigationServicePort = ns.getNavigationServicePort();
			LOG.info(prf + " navigationServicePort="+navigationServicePort);
			
			URL url5 = new URL("http://localhost/actasrv/relationshipsWS?wsdl");
			RelationshipsService rls = new RelationshipsService(url5);
			relationshipsServicePort = rls.getRelationshipsServicePort();
			LOG.info(prf + " relationshipsServicePort="+relationshipsServicePort);
			
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}
	
	@Test
	public void inizializzaCXF() {
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializzaCXF] ";
		LOG.info(prf + " BEGIN");
		
//        URL url = RicercaService.class.getResource("/bilancio-cre--BILANCIO_contabilia_ricercaService1.0.wsdl");
////      OauthHelper.setjks(vars.getVar("trustStore"),true);
//      System.out.println(url);
//      
//      RicercaService_Service ss = new RicercaService_Service(url, SERVICE_NAME);
//      RicercaService port = ss.getRicercaServicePort();  
//		BindingProvider bp = (BindingProvider) port;
//		Map<String, Object> map = bp.getRequestContext();
		
		
		try {
//			URL url = new URL("http://localhost/actasrv/backofficeWS?wsdl");
//			BackOfficeService bs = new  BackOfficeService(url);
//			BackOfficeServicePort port = bs.getBackOfficeServicePort();
			
			
//			URL url4 = new URL("http://localhost/actasrv/navigationWS?wsdl");
//			NavigationService ns = new NavigationService(url4);
//			NavigationServicePort navigationServicePort = ns.getNavigationServicePort();
//			LOG.info(prf + " navigationServicePort="+navigationServicePort);
			
//			QName SERVICE = new QName("navigationservice.acaris.acta.doqui.it", "NavigationService");
//			NavigationService ns2 = new NavigationService(url4, SERVICE);
//			NavigationServicePort t2 = ns2.getNavigationServicePort();
			
//			URL url5 = new URL("http://localhost/actasrv/relationshipsWS?wsdl");
//			RelationshipsService rls = new RelationshipsService(url5);
//			relationshipsServicePort = rls.getRelationshipsServicePort();
//			LOG.info(prf + " relationshipsServicePort="+relationshipsServicePort);
			
//			File wsdlFile1 = new File(DocumentiDiProgettoCXFDAOImplTest.class.getResource("/conf/ACARISWS-RelationshipsService.wsdl").getFile());
//			URL url5 = wsdlFile1.toURL();
//			URL url5 = new URL("http://localhost/actasrv/relationshipsWS?wsdl");
//			RelationshipsService rls = new RelationshipsService(url5);
//			relationshipsServicePort = rls.getRelationshipsServicePort();
//			LOG.info(prf + " relationshipsServicePort="+relationshipsServicePort);
			
			//////////////////
			
//			File wsdlFile = new File(DocumentiDiProgettoCXFDAOImplTest.class.getResource("/conf/ACARISWS-BackOfficeService.wsdl").getFile());
//			URL url = wsdlFile.toURL();
			
							   
//			URL url = new URL("http://localhost/actasrv/backofficeWS?wsdl");
//			QName BOSERVICE = new QName("backofficeservice.acaris.acta.doqui.it", "BackOfficeService");
////			BackOfficeService bs = new  BackOfficeService(url);
//			BackOfficeService bs = new  BackOfficeService(url,BOSERVICE);
//			backofficeServicePort = bs.getBackOfficeServicePort(new WebServiceFeature[] { new MTOMFeature(true) });
//			LOG.info(prf + " backofficeServicePort="+backofficeServicePort);

/*
			  String PART228_SERVER = "localhost";
		      String PART228_CONTEXT = "actasrv";
		      int PART228_PORT = 80;
		      long ID_AOO = 278L;
		      long ID_NODO = 1130L; 
		      long ID_STRUTTURA = 1047L;
		      String COD_FISCALE = "BNDRGP15R10L219Q";
		      String APP_KEY = "-68/-37/17/-98/89/-124/-38/21/-6/-118/-52/-21/74/-42/1/0";
		      String REP_NAME = "RP201209 Regione Piemonte - Agg. 09/2012";
		      boolean MTOM_ENABLED = true; 
		  	
		      ServiziAcaris servAc = new ServiziAcaris(PART228_SERVER, PART228_CONTEXT, PART228_PORT, MTOM_ENABLED);
			
		      LOG.info(prf + " backofficeServicePort="+servAc.getBackOfficeServicePort());
		      
		      //javax.xml.ws.WebServiceException: Metadati WSDL non disponibili per la creazione del proxy. L'istanza di servizio o ServiceEndpointInterface it.doqui.acta.acaris.backofficeservice.BackOfficeServicePort devono contenere le informazioni WSDL
		      
		*/      
		      
		      /*
		      ConfigurazioneFruitore cfg = new ConfigurazioneFruitore(PART228_SERVER, PART228_CONTEXT, PART228_PORT, REP_NAME, COD_FISCALE, ID_AOO, ID_NODO, ID_STRUTTURA,
		            APP_KEY, MTOM_ENABLED);
			LOG.info(prf + " cfg OK");
			
			ConnessioneAcaris testConnect = new ConnessioneAcaris();
			LOG.info(prf + " testConnect OK");
			
			AcarisRepositoryEntryType[] reps = null;
	        try {
	            reps = cfg.getServiziAcaris().getRepositoryServicePort().getRepositories();
	            LOG.info(prf + " reps OK");
	        } catch (it.doqui.acta.acaris.repositoryservice.AcarisException acEx) {
	            System.out.println("acEx.getMessage(): " + acEx.getMessage());
	            System.out.println("acEx.getFaultInfo().getErrorCode(): " + acEx.getFaultInfo().getErrorCode());
	            System.out.println("acEx.getFaultInfo().getPropertyName(): " + acEx.getFaultInfo().getPropertyName());
	            System.out.println("acEx.getFaultInfo().getObjectId(): " + acEx.getFaultInfo().getObjectId());
	            System.out.println("acEx.getFaultInfo().getExceptionType(): " + acEx.getFaultInfo().getExceptionType());
	            System.out.println("acEx.getFaultInfo().getClassName(): " + acEx.getFaultInfo().getClassName());
	            System.out.println("acEx.getFaultInfo().getTechnicalInfo: " + acEx.getFaultInfo().getTechnicalInfo());
	            return;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return;
	        }
			*/
			
//			ParametriAcaris parametri = new ParametriAcaris(server, context, port);
//	        parametri.setRepositoryName(repositoryName);
//	        parametri.setCodiceFiscale(codiceFiscale);
//	        parametri.setIdAoo(idAoo);
//	        parametri.setIdNodo(idNodo);
//	        parametri.setIdStruttura(idStruttura);
//	        parametri.setAppKey(appKey);
//			ServiziAcaris serviziAcaris = new ServiziAcaris(parametri.getServer(), parametri.getContext(), parametri.getPort(), mtomEnabled);
//			backofficeServicePort = AcarisServiceClient.getBackofficeServiceAPI("http://localhost/actasrv/backofficeWS?wsdl");
//			backofficeServicePort = AcarisServiceClient.getBackofficeServiceAPI(server, context, port);;
//			LOG.info(prf + " backofficeServicePort="+backofficeServicePort);
			
								
			
			
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}
	
	
	private PrincipalIdType getPrincipalId() throws Exception {

		if(principal==null)
			principal = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
		LOG.info( " principal="+principal);
		return principal.getPrincipalId();
	}

	private ObjectIdType getRepositoryId() throws Exception {
		if(repositoryId==null)
			repositoryId = getRepositoryId(actaRepositoryName, repositoryServicePort);
		return repositoryId;
    }
	
	private ObjectIdType getRepositoryId(String actaRepositoryName, RepositoryServicePort repositoryServicePort) throws Exception {
		LOG.info("[DocumentiDiProgettoDAOImplTest::getRepositoryId] BEGIN Chiamo repositoryServicePort.getRepositories() repositoryServicePort="+repositoryServicePort);
		
		ObjectIdType repositoryId = null;
		try {
			AcarisRepositoryEntryType[] reps = repositoryServicePort.getRepositories();

			if(reps == null){
				LOG.error("[DocumentiDiProgettoDAOImplTest::getRepositoryId] repositoryServicePort.getRepositories() non ha resituito resultati");
				return null;
			}
			for (AcarisRepositoryEntryType acarisRepositoryEntryType : reps) {
				LOG.info("[DocumentiDiProgettoDAOImplTest::getRepositoryId] RepositoryName(): "+acarisRepositoryEntryType.getRepositoryName() + 
						" ,RepositoryId().getValue():" + acarisRepositoryEntryType.getRepositoryId().getValue());
				String repositoryName = acarisRepositoryEntryType.getRepositoryName();
				
				if(actaRepositoryName.equalsIgnoreCase(repositoryName)){
					repositoryId = acarisRepositoryEntryType.getRepositoryId();
					
					break;
				}
			}
		
		} catch (Exception e) {
			LOG.error("[DocumentiDiProgettoDAOImplTest::getRepositoryId] Error in repositoryServicePort.getRepositories()"+e.getMessage(),e);
			e.printStackTrace();
			throw new Exception (e);
		}
//		} catch (AcarisException e) {
//			LOG.error("Error in repositoryServicePort.getRepositories()"+e.getMessage(),e);
//			return null;
//		}
		LOG.info("[DocumentiDiProgettoDAOImplTest::getRepositoryId] END"); 
		return repositoryId;
	}
	
	private PrincipalExtResponseType getPrincipalExt(String idRepository, String codiceFiscale, long idAoo, long idStruttura,
			long idNodo, String actaUtenteAppkey, BackOfficeServicePort backofficeServicePort) throws Exception {

		String prf = "[DocumentiDiProgettoDAOImplTest::getPrincipalExt] ";
		LOG.debug(prf + " BEGIN ");
		long start = System.currentTimeMillis();
		
		ObjectIdType objectIdType = new ObjectIdType();
		objectIdType.setValue(idRepository);
		
		CodiceFiscaleType codFiscale = new CodiceFiscaleType();
		codFiscale.setValue(codiceFiscale);
		
		IdAOOType _idAOO = new IdAOOType();
		_idAOO.setValue(idAoo);
		  
		IdStrutturaType _idStruttura = new IdStrutturaType();
		_idStruttura.setValue(idStruttura);
		  
		IdNodoType _idNodo = new IdNodoType();
		_idNodo.setValue(idNodo);
		
		ClientApplicationInfo clientApplicationInfo = new ClientApplicationInfo();
		clientApplicationInfo.setAppKey(actaUtenteAppkey);
		
		DettaglioAOOType auo = null;
		PrincipalExtResponseType[] arrPrincipalExt = null;
		Long rispostaLogMonitoraggio = null;
		Long rispostaLogMonitoraggio2 = null;
		try { 
        	rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,objectIdType, _idAOO);
			auo = backofficeServicePort.getDettaglioAOO(objectIdType, _idAOO);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",auo);
			LOG.debug(prf + " auo.denominazione= "+auo.getDenominazione());
			LOG.debug(prf + " auo.codice= "+auo.getCodice());
			
			rispostaLogMonitoraggio2 = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,objectIdType, codFiscale, _idAOO, _idStruttura, _idNodo, clientApplicationInfo);
			arrPrincipalExt = backofficeServicePort.getPrincipalExt(objectIdType, codFiscale, _idAOO, _idStruttura, _idNodo, clientApplicationInfo);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio2, "OK","","",arrPrincipalExt);
			
			LOG.debug(prf + " arrPrincipalExt= "+arrPrincipalExt);
			
			LOG.debug(prf + " arrPrincipalExt[0].getPrincipalId().getValue()= "+arrPrincipalExt[0].getPrincipalId().getValue());
		} catch (Exception e) {
			if(auo == null) {
				logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			}
			if(arrPrincipalExt == null) {
				logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio2, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			}
			LOG.error(prf + " e.getMessage(): " + e.getMessage());		
			e.printStackTrace();
			throw new Exception(e);
		}
		finally
		{
			long end = System.currentTimeMillis();
			LOG.debug(prf + " END, durata " + (end-start)/1000);
		}
		return arrPrincipalExt[0];
	}
	
	public void inizializza(){
		String prf = "[DocumentiDiProgettoCXFDAOImplTest::inizializza] ";
		LOG.info(prf + " BEGIN");
		
//		String wsdlLocation = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_backoffice-T/1.0";
//		String wsdlLocation2 = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_repository-T/1.0";
//		String wsdlLocation4 = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_object-T/1.0";
//		String wsdlLocation5 = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_navigation-T/1.0";
//		String wsdlLocation6 = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_relationships-T/1.0";
		
		//applications: PBANDI-REGP
//		String CONSUMER_KEY = "********************";
//		String CONSUMER_SECRET = "**********************";
//		String OAUTH_URL = "http://tst-<da sostiure con il VH di esposizione API>/api/token";
//		
//		OauthHelper oauthHelper = new OauthHelper(OAUTH_URL,CONSUMER_KEY,CONSUMER_SECRET,10000); // time out in ms
//		LOG.debug(prf + " oauthHelper= "+oauthHelper);
//		
//		Hashtable<String, String> headers = new Hashtable<String, String>();
//		headers.put("Authorization", "Bearer "+	oauthHelper.getToken() );
		
		try {

			URL url = new URL("http://localhost/actasrv/backofficeWS?wsdl");
			BackOfficeService bs = new  BackOfficeService(url);
			backofficeServicePort = bs.getBackOfficeServicePort();
			
			
			URL url2 = new URL("http://localhost/actasrv/repositoryWS?wsdl");
			RepositoryService rs = new RepositoryService(url2);
			repositoryServicePort = rs.getRepositoryServicePort();
			
			
			URL url3 = new URL("http://localhost/actasrv/objectWS?wsdl");
			ObjectService os = new ObjectService(url3);
			objectServicePort = os.getObjectServicePort();
			
			URL url5 = new URL("http://localhost/actasrv/relationshipsWS?wsdl");
			RelationshipsService rls = new RelationshipsService(url5);
			relationshipsServicePort = rls.getRelationshipsServicePort();
			
			URL url4 = new URL("http://localhost/actasrv/navigationWS?wsdl");
			NavigationService ns = new NavigationService(url4);
			navigationServicePort = ns.getNavigationServicePort();
			
		}catch(Exception e) {
			LOG.error(prf + " Exception "+e.getMessage());
			e.printStackTrace();
		}
		LOG.info(prf + " END");
	}

	
}
