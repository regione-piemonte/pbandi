/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.base.PbwebJunitClassRunner;
import it.csi.pbandi.pbweb.base.TestBaseService;
import it.csi.pbandi.pbweb.business.manager.ActaManager;
import it.csi.pbandi.pbweb.integration.dao.acta.AcarisUtils;
import it.csi.pbandi.pbweb.integration.dao.acta.TestUtilsCXF;
import it.csi.pbandi.pbweb.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbweb.integration.monitoraggio.GestioneLog;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
import it.doqui.acta.acaris.backofficeservice.AcarisException;
import it.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.doqui.acta.acaris.backofficeservice.BackOfficeServicePort;
import it.doqui.acta.acaris.navigationservice.NavigationService;
import it.doqui.acta.acaris.navigationservice.NavigationServicePort;
import it.doqui.acta.acaris.objectservice.ObjectService;
import it.doqui.acta.acaris.objectservice.ObjectServicePort;
import it.doqui.acta.acaris.officialbookservice.OfficialBookService;
import it.doqui.acta.acaris.officialbookservice.OfficialBookServicePort;
import it.doqui.acta.acaris.relationshipsservice.RelationshipsService;
import it.doqui.acta.acaris.relationshipsservice.RelationshipsServicePort;
import it.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumArchiveObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumRelationshipDirectionType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumRelationshipObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.IdStatoDiEfficaciaType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.RelationshipPropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.DettaglioAOOType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.DettaglioStrutturaType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.PrincipalExtResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.AcarisContentStreamType;
import it.doqui.acta.actasrv.dto.acaris.type.common.CodiceFiscaleType;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumPropertyFilter;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumQueryOperator;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumStreamId;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdAOOType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdNodoType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdStrutturaType;
import it.doqui.acta.actasrv.dto.acaris.type.common.NavigationConditionInfoType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PagingResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyFilterType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryConditionType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryNameType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryableObjectType;
//import javax.wsdl.xml.WSDLReader; 

@RunWith(PbwebJunitClassRunner.class)
public class DocumentiDiProgettoCXFDAOImpNewlTest  extends TestBaseService {

	protected static Logger LOG = Logger.getLogger(DocumentiDiProgettoCXFDAOImpNewlTest.class);

	@Autowired
	private DecodificheDAOImpl decodificheDAOImpl;
	
	@Autowired
	private GestioneLog logMonitoraggioService;
	
	
	//  servono i seguenti 6 servizi ACTA
	private BackOfficeServicePort backofficeServicePort; 
	private RepositoryServicePort repositoryServicePort; 
	private ObjectServicePort objectServicePort; 
	private NavigationServicePort navigationServicePort;
	private RelationshipsServicePort relationshipsServicePort;
	private OfficialBookServicePort officialBookServicePort;
	
	private ObjectIdType repositoryId;
	private PrincipalExtResponseType principal; 
	
	private String codiceFiscale = null;
	private String actaUtenteAppkey = null;
	private String actaRepositoryName = null;
	
	static String CONSUMER_KEY = <CONSUMER_KEY>;
	static String CONSUMER_SECRET = <CONSUMER_SECRET>;
	static String OAUTH_URL = "http://tst-<da sostiure con il VH di esposizione API>/api/token";
	static String endpointBackOfficeService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_backoffice-T/1.0";
	static String endpointRepositoryService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_repository-T/1.0";
	static String endpointObjectService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_object-T/1.0";
	static String endpointNavigationService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_navigation-T/1.0";
	static String endpointRelationshipsService = "http://tst-<da sostiure con il VH di esposizione API>/api/DOC_ACTA_relationships-T/1.0";
	static String endpointOfficialBookService = "http://tst-api-ent.ecosis.csi.it/api/DOC_ACTA_officialbook-T/1.0";
	
	private boolean verboseOutput = true;
	
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
    
	
	// trippletta letta dal db
	private long idAoo;
	private long idStruttura;
	private long idNodo;
	
	// trippletta ricavata della collocazione del settore del progetto su cui si vuole inserire il doc ICE
	private long idAooICE;
	private long idStrutturaICE;
	private long idNodoICE;
	
	private  String[] iceSplit  = new String[3];
	private  boolean isAllegato = false;
	private  String dbKeyAggreagazione = "";
	
	@Test
	public void cercaICE() {
		
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::cercaICE] ";
		LOG.info(prf + " BEGIN");
		
		//DATI Siringo -> OK
		// Trovato: Filename = PropostaDiRimodulazione_50244_16032016.pdf, length = 14460
//		idAoo = 278L;			//  (A19000 COMPETITIVITA' DEL SISTEMA REGIONALE)
//		idStruttura = 1047L; 	//(DB1600 DB1600-ATTIVITA' PRODUTTIVE)
//		idNodo = 1130L; 
//		String ice = "C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 8.v, 50.v, 10.v,  ASSE III.1.1 - id 24/A19000.sfa, A19000.arm, 3/2016A/A19000.fra, A19000.arm, 1.nd";
		
		try {
			actaRepositoryName = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_REPOSITORYNAME, true);
			codiceFiscale = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_UTENTECF, true);
			actaUtenteAppkey = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_UTENTEAPPKEY, true);
			LOG.info(prf + " actaRepositoryName="+actaRepositoryName);
			LOG.info(prf + " codiceFiscale="+codiceFiscale);
			LOG.info(prf + " actaUtenteAppkey="+actaUtenteAppkey);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// presenti sul DB (master)
		idAoo = 237L; 			// (DB1600 Attività produttive)
		idStruttura = 795L; 	//(DB1600 DB1600-ATTIVITA' PRODUTTIVE)
		idNodo = 810L;
		//CARLA
		
		// dati funzionanti DichiarazioneDiSpesa_18454_17032016.pdf
//		String descSettore = "A19000";
//		String ice = "C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 8.v, 50.v, 10.v,  ASSE III.1.1 - id 24/A19000.sfa, A19000.arm, 2/2016A/A19000.fra, A19000.arm, 2.nd";
		
		// dati funzionanti  PropostaDiRimodulazione_49615_17032016.pdf
//		String descSettore = "A19000";
//		String ice ="C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 8.v, 50.v, 10.v,  ASSE III.1.1 - id 24/A19000.sfa, A19000.arm, 2/2016A/A19000.fra, A19000.arm, 1.nd";
		
		// dati funzionanti : 
//		String descSettore = "A11000"; // settore corretto
//		String descSettore = "A19000"; // settore errato
//		String ice = "C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 8.v, 50.v, 10.v, ASSE III.1.1 - id 24/A19000/A11000.sfa, A11000.arm, 1/2020A/A11000.fra, A11000.arm, 2.nd";
		
		// dati funzionanti : PTE_A17_6.17.12 - Sistema gestionale dell’organismo pagatore - attività 2019_DEF.pdf
//		String descSettore = "A19000";
//		String ice = "C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 8.v, 50.v, 10.v,  ASSE III.1.1 - id 24/A19000.sfa, A19000.arm, 2/2016A/A19000.fra, A19000.arm, 6.nd";
		
		// dati funzionanti : DichiarazioneDiSpesa_21351_19022020.pdf"
		// NON ha protocollo
//		String descSettore = "A19000";
//		String ice = "C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 8.v, 50.v, 10.v, 999/A19000.sfa, A19000.arm, 2/2020A/A19000.fra, A19000.arm, 1.nd";
		
		// dati funzionanti : DichiarazioneDiSpesa_18207_01032016.pdf
		// NON ha protocollo
//		String descSettore = "A15000";
//		String ice = "C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 8.v, 50.v, 10.v, PBANDIPBL170A1500/A15000.sfa, A15000.arm, 1/2016A/A15000.fra, A15000.arm, 1.nd";
		           
		//DichiarazioneDiSpesa_27316_16032022.pdf
		String descSettore = "A19000";
		String ice = "C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 8.v, 50.v, 10.v, 6/A19000.sfa, A19000.arm, 2/2022A/A19000.fra, A19000.arm, 1.nd";
		
		
		ricercaClassificazioneByIndiceClassEstesoCXF(ice,descSettore);
		
		LOG.info(prf + " END");
	}
	
	/**
	 * PK : classe per trovare il value dell'oggetto "IdStatoDiEfficaciaType" dato un suo stato
	 */
	@Test
	public void cercaIdStatoDiEfficacia() {
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::cercaIdStatoDiEfficacia] ";
		LOG.info(prf + " BEGIN");
		
		
		try {
			actaRepositoryName = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_REPOSITORYNAME, true);
			codiceFiscale = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_UTENTECF, true);
			actaUtenteAppkey = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_UTENTEAPPKEY, true);
			LOG.info(prf + " actaRepositoryName="+actaRepositoryName);
			LOG.info(prf + " codiceFiscale="+codiceFiscale);
			LOG.info(prf + " actaUtenteAppkey="+actaUtenteAppkey);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// presenti sul DB (master)
		idAoo = 237L; 			// (DB1600 Attività produttive)
		idStruttura = 795L; 	//(DB1600 DB1600-ATTIVITA' PRODUTTIVE)
		idNodo = 810L;
		//CARLA
		
		
		inizializzaObjectService();
		
		PrincipalIdType principalId = new PrincipalIdType();
		principalId.setValue("05d83cc436c325cb39e331f56d9360f5649c609f619d669d6d9e659f6cf5789c6d8578996285649d7a876c927a926c85789b679e7a8766927a986485789c7a87649b6d85789f678578986485629e7a8761987a9b7a9a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a");
				
		// RepositoryName(): RP201209 Regione Piemonte - Agg. 09/2012 ,
		ObjectIdType repositoryId = new ObjectIdType();
		repositoryId.setValue("07cf25c526c321c527d301d325cf0a9b668a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a758a");
		
		String stato1="PERFETTO ED EFFICACE";
		String stato2="PERFETTO ED EFFICACE MA NON FIRMATO";
		try {
//			IdStatoDiEfficaciaType id = queryForStatoEfficacia(repositoryId, principalId, stato2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}
	
	
	private IdStatoDiEfficaciaType queryForStatoEfficacia(ObjectIdType repositoryId, PrincipalIdType principalId, 
			String descrizioneStatoEfficacia) throws Exception {
	    QueryableObjectType target = new QueryableObjectType();
	    target.setObject("StatoDiEfficaciaDecodifica");

	    PropertyFilterType filter = new PropertyFilterType();
	    filter.setFilterType(EnumPropertyFilter.LIST); // in alternativa, EnumPropertyFilter.ALL

	    List<QueryNameType> richieste = new ArrayList<QueryNameType>();
	    QueryNameType richiesta = new QueryNameType();
	    richiesta.setClassName(target.getObject());
	    richiesta.setPropertyName("dbKey");
	    richieste.add(richiesta);
	    richiesta = new QueryNameType();
	    richiesta.setClassName(target.getObject());
	    richiesta.setPropertyName("descrizione");
	    richieste.add(richiesta);
	    filter.setPropertyList(richieste.toArray(new QueryNameType[richieste.size()]));

	    List<QueryConditionType> criteria = new ArrayList<QueryConditionType>(); // possibile non indicare alcun criterio per ottenere tutti gli stati efficacia disponibili
	    QueryConditionType qct = new QueryConditionType();
	    qct.setPropertyName("descrizione");
	    qct.setOperator(EnumQueryOperator.EQUALS);
	    qct.setValue(descrizioneStatoEfficacia);
	    criteria.add(qct);

	    IdStatoDiEfficaciaType idStatoEfficacia = null;
	    Long rispostaLogMonitoraggio = null;
	    try {
	    	rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_OBJECT_QUERY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,repositoryId, principalId, target, filter,
		            criteria.toArray(new QueryConditionType[criteria.size()]));
	        PagingResponseType result = objectServicePort.query(repositoryId, principalId, target, filter,
	            criteria.toArray(new QueryConditionType[criteria.size()]), null, null, new Integer(0));
	        logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",result);
	        
	        if (result != null && result.getObjectsLength() == 1 && result.getObjects(0) != null && result.getObjects(0).getPropertiesLength() > 0) {
	            for (PropertyType current : result.getObjects(0).getProperties()) {
	                if ("dbKey".equals(current.getQueryName().getPropertyName()) && current.getValue() != null && current.getValue().getContentLength() == 1) {
	                    idStatoEfficacia = new IdStatoDiEfficaciaType();
	                    idStatoEfficacia.setValue(Long.parseLong(current.getValue().getContent(0)));
	                    System.out.println("Trovato stato di efficacia " + descrizioneStatoEfficacia + ", value " + idStatoEfficacia.getValue());
	                    break;
	                }
	            }
	        }

	    } catch (it.doqui.acta.acaris.objectservice.AcarisException acEx) {
	    	logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",acEx.getMessage());
			
	        System.err.println("errore nella ricerca dello stato di efficacia");
	        System.err.println("acEx.getMessage(): " + acEx.getMessage());
	        System.err.println("acEx.getFaultInfo().getErrorCode(): " + acEx.getFaultInfo().getErrorCode());
	        System.err.println("acEx.getFaultInfo().getPropertyName(): " + acEx.getFaultInfo().getPropertyName());
	        System.err.println("acEx.getFaultInfo().getObjectId(): " + acEx.getFaultInfo().getObjectId());
	        System.err.println("acEx.getFaultInfo().getExceptionType(): " + acEx.getFaultInfo().getExceptionType());
	        System.err.println("acEx.getFaultInfo().getClassName(): " + acEx.getFaultInfo().getClassName());
	        throw acEx;
	    } catch (Exception ex) {
	        System.err.println("errore nella ricerca dello stato di efficacia");
	        System.err.println("ex.getMessage() " + ex.getMessage());
	        throw ex;
	    }

	    return idStatoEfficacia;
	}
	
	public void ricercaClassificazioneByIndiceClassEstesoCXF(String ice, String descSettore) {
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::ricercaClassificazioneByIndiceClassEstesoCXF] ";
		LOG.info(prf + " BEGIN");
		
		long tInizio = System.currentTimeMillis();

		try {
//			String[] iceSplit = splitAggregazioneIce(ice);
//			for (int i = 0; i < iceSplit.length; i++) {
//				LOG.info(prf + " iceSplit["+i+"]={"+iceSplit[i]+"}");
//			}
//
//			if (iceSplit[0] == null) {
//				 LOG.error(prf + "ERRORE scomposizione ice classificazione: ice aggregazione non trovato!");
//	        }
//	        if (iceSplit[1] == null) {
//	        	LOG.error(prf + "ERRORE scomposizione ice classificazione: codice documento (principale) non trovato!");
//	        }
//
//	        boolean isAllegato = iceSplit[2] != null; 
//	        LOG.info(prf + "isAllegato="+isAllegato);
//	        if (!isAllegato) {
//	        	LOG.info(prf + " Indice Classificazione di un documento PRINCIPALE");
//	        } else {
//	        	LOG.info(prf + " Indice Classificazione di un documento ALLEGATO");
//	        }
//
//	        long start = System.currentTimeMillis();
//
//	        inizializzaBackOfficeService();
//			inizializzaRepositoryService();
//			inizializzaObjectService();
//			inizializzaNavigationService();
//			inizializzaRelationshipsService();
//			inizializzaOfficialBookService();
//			LOG.info(prf + " inizializzazione avvenuta");
//
//	        PrincipalExtResponseType principalMaster = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
//			LOG.info( " >>> principalMaster="+principalMaster);
//
//			
////			int idProgetto = 17445;
//			
////			select blec.DESC_BREVE_ENTE || NVL (blec.DESC_SETTORE, NVL(blec.DESC_BREVE_SETTORE,'000'))SETTORE, blec.*
////			from pbandi.PBANDI_V_BANDO_LINEA_ENTE_COMP blec, PBANDI_T_PROGETTO prog, PBANDI_T_DOMANDA do
////			where blec.DESC_BREVE_RUOLO_ENTE ='DESTINATARIO'
////			AND blec.PROGR_BANDO_LINEA_INTERVENTO = do.PROGR_BANDO_LINEA_INTERVENTO
////			AND do.id_domanda = prog.id_domanda
////			AND prog.ID_PROGETTO = 17445;
//			
//			
//	      
//	        	
//        	// PK : ricavo idAoo, idStruttura, idNodo della collocazione del settore del progetto su cui si vuole inserire il doc ICE
//        	
//        	String AOO_STR_NODO_VIEW = "AooStrNodoView";
//        	
//        	QueryableObjectType target = new QueryableObjectType();
//		    target.setObject(AOO_STR_NODO_VIEW);
//		    PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
//			
//			QueryConditionType[] criteria = { TestUtilsCXF.buildQueryCondition("codiceStruttura", EnumQueryOperator.EQUALS, descSettore), //"A299000" 
//											   TestUtilsCXF.buildQueryCondition("flagResponsabile", EnumQueryOperator.EQUALS, "S")};
//
//			NavigationConditionInfoType navigationLimits = new NavigationConditionInfoType();
//			Integer maxItems = null;
//	        Integer skipCount = 0;
////
//	        PagingResponseType pagingResponse = backofficeServicePort.query(getRepositoryId(), principalMaster.getPrincipalId(), target, filter, criteria, navigationLimits, maxItems, skipCount);
//			LOG.debug(prf + "pagingResponse= " + pagingResponse);
//			
//			
//			if (pagingResponse == null || pagingResponse.getObjects() == null) {
//				String msg = " (GET USER SPECIFIC COLLOCATION) Errore : objectServicePort.query non ha restituito nulla \n\n";
//				LOG.error("STEP 3 KO : "+msg);
//			}
//			
//			IdAOOType userIdAOOType = new IdAOOType();
//			IdNodoType userIdNodoType = new  IdNodoType();
//			IdStrutturaType userIdStrutturaType= new  IdStrutturaType();
//			mapActaUserCollocation(userIdAOOType, userIdStrutturaType, userIdNodoType, pagingResponse);
//			
//			
//			idAooICE = userIdAOOType.getValue();
//			idStrutturaICE = userIdStrutturaType.getValue();
//			idNodoICE = userIdNodoType.getValue();
//			
//			LOG.info(prf + "******************** Trovata TRIPPLETTA da usare ");
//			LOG.info(prf + "idAooICE: "+idAooICE);
//			LOG.info(prf + "idStrutturaICE : "+idStrutturaICE);
//			LOG.info(prf + "idNodoICE: "+idNodoICE);
//			LOG.info(prf + "********************");
//			
//
//	        String dbKeyAggreagazione = ricercaIdAggregazioneByIce(iceSplit[0]); 
//	        LOG.info(prf + " dbKeyAggreagazione="+dbKeyAggreagazione);
//	        
////	        String protocollo = ricercaProtocolloOfficialA(dbKeyAggreagazione);
////	        LOG.info(prf + " protocollo A)="+protocollo);
//	        
//	        LOG.info(prf + " T ricerca aggr by ice: " + (System.currentTimeMillis() - start) + " ms");
			
			inizializzaActa( ice, descSettore);
	        
        /**RICERCA NUM PROTOCOLLO
         * 1.
potete ottenere il numero di protocollo richiamando l'operazione officialBookService.query() impostando il target in modo diverso a seconda dei casi:

a)
se partite da una classificazione principale e avete l'id_classificazione (dbKey), dovete impostare il target registrazioneView con criterio di ricerca:
property name = " idClassificazione"
operator = EQUALS
value = <id_classificazione>
b)
se partite da una classificazione principale e avete l'objectIdClassificazione, dovete impostare il target RegistrazioneClassificazioniView con criterio di ricerca:
property name = " objectIdClassificazione"
operator = EQUALS
value = <objectIdClassificazione>
c)
se partite da una classificazione allegata dovete prima risalire a quella principale richiamando l'operazione getFolderParent
(classificazione allegata -> gruppo allegato -> classificazione principale).
A quel punto, conoscendo l'objectIdClassificazione, potete procedere con 1b (o 1a indifferente).
Il numero della registrazione di protocollo è presente come valore della property " codice".
         */
	        Long start = System.currentTimeMillis();
//	        ricercaProtocolloAA("dbKeyAggreagazione");
	        
	        String objectIdClassificazione = ricercaObjectIdClassificazionePrincipale(iceSplit[1], dbKeyAggreagazione);
	        LOG.info(prf + " objectIdClassificazione="+objectIdClassificazione);
	        
	        LOG.info(prf + " T ricerca classificazione (principale): " + (System.currentTimeMillis() - start)+ " ms");
	        
	        if (objectIdClassificazione == null) {
	        	LOG.error(prf +"ERRORE ricerca classificazione da codice documento (principale)!");
	        }
//	        LOG.info(prf + " trovata classificazione: " + AcarisUtils.pseudoDecipher(objectIdClassificazione));
	        
	        
	        //  String protocollo = ricercaProtocolloA(objectIdClassificazione);
	        String protocolloB = ricercaProtocolloOfficial(objectIdClassificazione);
	        LOG.info(prf + " protocollo B)="+protocolloB);
	        
	       estrai(objectIdClassificazione);

	        String objectIdClassificazioneAllegata = null;
	        if (isAllegato) {
	            start = System.currentTimeMillis();
	            objectIdClassificazioneAllegata = recuperaAllegatoDaPrincipale(objectIdClassificazione, iceSplit[2]);
	            LOG.info(prf + " T ricerca classificazione (allegata da principale): " + (System.currentTimeMillis() - start));
	            if (objectIdClassificazioneAllegata != null) {
	            	 LOG.info(prf + " trovata classificazione allegata: " + AcarisUtils.pseudoDecipher(objectIdClassificazioneAllegata));
	            } else {
	            	 LOG.info(prf + " NON trovata classificazione allegata by indice classificazione esteso");
	            }
	        }
	        
	        LOG.info(prf + " ********************** RicercaClassificazioneByIce fine, T totale: "
	            + (System.currentTimeMillis() - tInizio));

		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
		}

		LOG.info(prf + " END");
	}
	
	private void inizializzaActa(String ice, String descSettore) throws AcarisException, Exception {
		
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::inizializzaActa] ";
		LOG.info(prf + " BEGIN");
		
		iceSplit = splitAggregazioneIce(ice);
		for (int i = 0; i < iceSplit.length; i++) {
			LOG.info(prf + " iceSplit["+i+"]={"+iceSplit[i]+"}");
		}

		if (iceSplit[0] == null) {
			 LOG.error(prf + "ERRORE scomposizione ice classificazione: ice aggregazione non trovato!");
        }
        if (iceSplit[1] == null) {
        	LOG.error(prf + "ERRORE scomposizione ice classificazione: codice documento (principale) non trovato!");
        }

        isAllegato = iceSplit[2] != null; 
        LOG.info(prf + "isAllegato="+isAllegato);
        if (!isAllegato) {
        	LOG.info(prf + " Indice Classificazione di un documento PRINCIPALE");
        } else {
        	LOG.info(prf + " Indice Classificazione di un documento ALLEGATO");
        }

        long start = System.currentTimeMillis();

        inizializzaBackOfficeService();
		inizializzaRepositoryService();
		inizializzaObjectService();
		inizializzaNavigationService();
		inizializzaRelationshipsService();
		inizializzaOfficialBookService();
		LOG.info(prf + " inizializzazione avvenuta");

        PrincipalExtResponseType principalMaster = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
		LOG.info( " >>> principalMaster="+principalMaster);
		
    	// PK : ricavo idAoo, idStruttura, idNodo della collocazione del settore del progetto su cui si vuole inserire il doc ICE
    	
    	String AOO_STR_NODO_VIEW = "AooStrNodoView";
    	
    	QueryableObjectType target = new QueryableObjectType();
	    target.setObject(AOO_STR_NODO_VIEW);
	    PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
		
		QueryConditionType[] criteria = { TestUtilsCXF.buildQueryCondition("codiceStruttura", EnumQueryOperator.EQUALS, descSettore), //"A299000" 
										   TestUtilsCXF.buildQueryCondition("flagResponsabile", EnumQueryOperator.EQUALS, "S")};

		NavigationConditionInfoType navigationLimits = new NavigationConditionInfoType();
		Integer maxItems = null;
        Integer skipCount = 0;
        
        Long rispostaLogMonitoraggio = null;
        rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE_QUERY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(), principalMaster.getPrincipalId(), target, filter, criteria, navigationLimits, maxItems, skipCount);
        PagingResponseType pagingResponse = backofficeServicePort.query(getRepositoryId(), principalMaster.getPrincipalId(), target, filter, criteria, navigationLimits, maxItems, skipCount);
		LOG.debug(prf + "pagingResponse= " + pagingResponse);
		logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",pagingResponse);
		
		if (pagingResponse == null || pagingResponse.getObjects() == null) {
			String msg = " (GET USER SPECIFIC COLLOCATION) Errore : objectServicePort.query non ha restituito nulla \n\n";
			LOG.error("STEP 3 KO : "+msg);
		}
		
		IdAOOType userIdAOOType = new IdAOOType();
		IdNodoType userIdNodoType = new  IdNodoType();
		IdStrutturaType userIdStrutturaType= new  IdStrutturaType();
		mapActaUserCollocation(userIdAOOType, userIdStrutturaType, userIdNodoType, pagingResponse);
		
		
		idAooICE = userIdAOOType.getValue();
		idStrutturaICE = userIdStrutturaType.getValue();
		idNodoICE = userIdNodoType.getValue();
		
		LOG.info(prf + "******************** Trovata TRIPPLETTA da usare ");
		LOG.info(prf + "idAooICE: "+idAooICE);
		LOG.info(prf + "idStrutturaICE : "+idStrutturaICE);
		LOG.info(prf + "idNodoICE: "+idNodoICE);
		LOG.info(prf + "********************");
		

        dbKeyAggreagazione = ricercaIdAggregazioneByIce(iceSplit[0]); 
        LOG.info(prf + " dbKeyAggreagazione="+dbKeyAggreagazione);
        
//        String protocollo = ricercaProtocolloOfficialA(dbKeyAggreagazione);
//        LOG.info(prf + " protocollo A)="+protocollo);
        
        LOG.info(prf + " T ricerca aggr by ice: " + (System.currentTimeMillis() - start) + " ms");
      	        
        if(dbKeyAggreagazione == null) {
        	throw new Exception("ERROR_ACTA_ICE");
        }
        
		LOG.info(prf + " END");
	}


	private void mapActaUserCollocation(IdAOOType userIdAOOType, IdStrutturaType userIdStrutturaType,
			IdNodoType userIdNodoType, PagingResponseType pagingResponse) {
		
		ObjectResponseType[] objectResponseTypes = pagingResponse.getObjects();
		LOG.info("mapActaUser (searchUSerCollocation)objectResponseTypes " + objectResponseTypes.length);
		
		for (ObjectResponseType objectResponseType : objectResponseTypes) {
			PropertyType[] properties = objectResponseType.getProperties();
			for (PropertyType propertyType : properties) {
				String propertyName = propertyType.getQueryName().getPropertyName();
				if (propertyName.equalsIgnoreCase("idAoo")) {
					 userIdAOOType.setValue(Long.valueOf( propertyType.getValue().getContent()[0]));
				} else if (propertyName.equalsIgnoreCase("idNodo")) {
					userIdNodoType.setValue(Long.valueOf(propertyType.getValue().getContent()[0] ));
				} else if (propertyName.equalsIgnoreCase("idStruttura")) {
					userIdStrutturaType.setValue(Long.valueOf(propertyType.getValue().getContent()[0] ));
				}
			}
		}
				// collocazione dell'utente specifico
		LOG.info("user specific idAoo: "+userIdAOOType.getValue()
			  +"idNodo : "+userIdNodoType.getValue()
			  +"idStruttura: "+userIdStrutturaType.getValue());
	}

	private PrincipalExtResponseType getPrincipalExt(String idRepository, String codiceFiscale, long idAoo, long idStruttura,
			long idNodo, String actaUtenteAppkey, BackOfficeServicePort backofficeServicePort) throws Exception {

		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::getPrincipalExt] ";
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
		
		PrincipalExtResponseType[] arrPrincipalExt = null;
		DettaglioStrutturaType dt = null;
		DettaglioAOOType auo = null; 
		Long rispostaLogMonitoraggio1 = null;
		Long rispostaLogMonitoraggio2 = null;
		Long rispostaLogMonitoraggio3 = null;
		try {
			rispostaLogMonitoraggio1 = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE_GET_PRINCIPAL_EXT, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,objectIdType, _idAOO);
			auo = backofficeServicePort.getDettaglioAOO(objectIdType, _idAOO);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio1, "OK","","",auo);
			LOG.debug(prf + " DettaglioAOOType.denominazione= "+auo.getDenominazione());
			LOG.debug(prf + " DettaglioAOOType.codice= "+auo.getCodice());
			
			rispostaLogMonitoraggio2 = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE_GET_PRINCIPAL_EXT, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,objectIdType, _idStruttura);
			dt = backofficeServicePort.getDettaglioStruttura(objectIdType, _idStruttura);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio2, "OK","","",dt);
			
			LOG.debug(prf + " DettaglioStrutturaType.denominazione= "+dt.getDenominazione());
			LOG.debug(prf + " DettaglioStrutturaType.codice= "+dt.getCodice());
			
			rispostaLogMonitoraggio3 = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE_GET_PRINCIPAL_EXT, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,objectIdType, codFiscale, _idAOO, _idStruttura, _idNodo, clientApplicationInfo);
			arrPrincipalExt = backofficeServicePort.getPrincipalExt(objectIdType, codFiscale, _idAOO, _idStruttura, _idNodo, clientApplicationInfo);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio3, "OK","","",arrPrincipalExt);
			
			LOG.debug(prf + " arrPrincipalExt= "+arrPrincipalExt);
			
			LOG.debug(prf + " arrPrincipalExt[0].getPrincipalId().getValue()= "+arrPrincipalExt[0].getPrincipalId().getValue());
		} catch (Exception e) {
			if(auo == null) {
				logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio1, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			}
			if(dt == null) {
				logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio2, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			}
			if(arrPrincipalExt == null) {
				logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio3, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
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
	
	private ObjectIdType getRepositoryId() throws Exception {
		if(repositoryId==null)
			repositoryId = getRepositoryId(actaRepositoryName, repositoryServicePort);
		else
			LOG.debug("getRepositoryId():: else repositoryId=["+repositoryId+"]");
		return repositoryId;
    }
	
	private ObjectIdType getRepositoryId(String actaRepositoryName, RepositoryServicePort repositoryServicePort) throws Exception {
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::getRepositoryId] ";
		LOG.info(prf + " BEGIN");
		ObjectIdType repositoryId = null;
		Long rispostaLogMonitoraggio = null;
		try {
			
			rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_REPOSITORY_GET_REPOSITORY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,null);
			AcarisRepositoryEntryType[] reps = repositoryServicePort.getRepositories();
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",reps);
			if(reps == null){
				LOG.error(prf + " repositoryServicePort.getRepositories() non ha resituito resultati");
				return null;
			}
			for (AcarisRepositoryEntryType acarisRepositoryEntryType : reps) {
				LOG.info(prf + " RepositoryName(): "+acarisRepositoryEntryType.getRepositoryName() + 
						" ,RepositoryId().getValue():" + acarisRepositoryEntryType.getRepositoryId().getValue());
				String repositoryName = acarisRepositoryEntryType.getRepositoryName();
				if(actaRepositoryName.equalsIgnoreCase(repositoryName)){
					LOG.info(prf + "selezionato RepositoryId del repositoryName = ["+repositoryName+"]");
					repositoryId = acarisRepositoryEntryType.getRepositoryId();
					break;
				}
			}
		} catch (Exception e) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			LOG.error(prf + " Error in repositoryServicePort.getRepositories()"+e.getMessage(),e);
			e.printStackTrace();
			throw new Exception (e);
		}
		LOG.info(prf + " END"); 
		return repositoryId;
	}
	
	private void inizializzaOfficialBookService() {
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::inizializzaOfficialBookService] ";
		LOG.info(prf + " BEGIN");
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-OfficialBookService.wsdl");
			OfficialBookService obs = new  OfficialBookService(url);
			OfficialBookServicePort port = obs.getOfficialBookServicePort();
			LOG.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointOfficialBookService);
			
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
			gwfb.setEndPoint(endpointOfficialBookService);
			gwfb.setWrappedInterface(OfficialBookServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			officialBookServicePort = null;
			try {
				Object o = gwfb.create();
				officialBookServicePort = (OfficialBookServicePort) o;
			} catch (Exception ex) {
				LOG.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			LOG.debug(prf + " officialBookServicePort= "+officialBookServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
	}
	
	private void inizializzaRelationshipsService() {
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::inizializzaRelationshipsService] ";
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
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::inizializzaNavigationService] ";
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
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::inizializzaObjectService] ";
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
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::inizializzaRepositoryService] ";
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

		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::inizializzaBackOfficeService] ";
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
	
	private String[] splitAggregazioneIce(String classificazioneIce) {
		String prf = "[ActaManager::splitAggregazioneIce] ";
		LOG.info(prf + " BEGIN");
		
		String[] splitted = classificazioneIce.split(",");
        StringBuffer aggrIce = new StringBuffer();
        String codiceClassificazione = null;
        String codiceClassificazioneAllegato = null;
        boolean first = true;
        for (String split : splitted) {
            String trimmed = split.trim();
            if (!trimmed.matches(".+((\\.nd)|(\\.a))")) {
                if (!first) {
                    aggrIce.append(",");
                }
                aggrIce.append(split);
                first = false;
            } else {
                if (trimmed.endsWith(".nd")) {
                    codiceClassificazione = trimmed.split(".nd")[0];
                } else {
                    codiceClassificazioneAllegato = trimmed.split(".a")[0];
                }
            }
        }
        
        LOG.info(prf + " indice classificazione esteso: " + classificazioneIce);
        LOG.info(prf +" indice classificazione esteso AGGREGAZIONE: " + aggrIce);
        // nel caso in cui l'indice class esteso preso fa riferimento ad un allegato, penultimo elemento indica il suo principale
        // nel caso in cui l'ice classificazione è un principale, allora ultimo elemento indica il suo principale
        LOG.info(prf +" codice classificazione: " + (codiceClassificazione != null ? codiceClassificazione : "non trovato"));
        // nel caso in cui l'indice class esteso preso fa riferimento ad un allegato, ultimo elemento è allegato
        LOG.info(prf +" codice classificazione (allegato): " + (codiceClassificazioneAllegato != null ? codiceClassificazioneAllegato : "non trovato"));
        
        String[] result = {aggrIce.toString(), codiceClassificazione, codiceClassificazioneAllegato};
        
        return result;
	}
	
	private String ricercaIdAggregazioneByIce(String iceAggregazione) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImplTest::ricercaIdAggregazioneByIce] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " ricerco aggregazione con ice: " + iceAggregazione);
		
		 String dbKeyAggreagazione = null;
		 
		try {
	        PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
	        LOG.info(prf + " filter = "+filter);
	        
	        QueryConditionType[] criteria = { TestUtilsCXF.buildQueryCondition("indiceClassificazioneEstesa", EnumQueryOperator.EQUALS, iceAggregazione) };
	        LOG.info(prf + " criteria = "+criteria);
	        
	        PagingResponseType result = objectServiceQuery("AggregazioneICEView", filter, criteria, null);
	        LOG.info(prf + "PagingResponseType result = "+result);
	        
	        if (result.getObjectsLength() > 0) {
	            for (ObjectResponseType obj : result.getObjects()) {
	                if (obj.getPropertiesLength() > 0) {
	                    for (PropertyType property : obj.getProperties())  {
	                        if (property.getQueryName().getPropertyName().equals("dbKey")) {
	                        	dbKeyAggreagazione = property.getValue().getContent()[0];
	                        }
	                    }
	                }
	            }
	        }
	        LOG.info(prf + "dbKeyAggreagazione = "+dbKeyAggreagazione);
		}catch(Exception e) {
			throw new Exception(e);
		}
        LOG.info(prf + " END");
        return dbKeyAggreagazione;
	}
	
	private PagingResponseType officialBookServiceQuery(String entitaTarget, PropertyFilterType filter,  QueryConditionType[] criteria,
	        NavigationConditionInfoType navigationLimits) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImplTest::officialBookServiceQuery] ";
		LOG.info(prf + " BEGIN");
	    
        QueryableObjectType target = new QueryableObjectType();
        target.setObject(entitaTarget);

        Integer maxItems = null;
        Integer skipCount = 0;

        PagingResponseType pagingResponse = null;
        Long rispostaLogMonitoraggio = null;
        try {
        	rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_OFFICIAL_BOOK_QUERY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(), getPrincipalId(), target, filter, criteria, navigationLimits, maxItems, skipCount);
        	pagingResponse = officialBookServicePort.query(getRepositoryId(), getPrincipalId(), target, filter, criteria, navigationLimits, maxItems, skipCount);
        	logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",pagingResponse);
        } catch (Exception acEx) {
        	logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",acEx.getMessage());
        	LOG.info(prf + " errore sollevato  per " + entitaTarget);
        	LOG.info(prf + " acEx.getMessage(): " + acEx.getMessage());
        	acEx.printStackTrace();
        	throw new Exception(acEx);
        }
        LOG.info(prf + " END");
        return pagingResponse;
	}
	
	private PagingResponseType objectServiceQuery(String entitaTarget, PropertyFilterType filter,  QueryConditionType[] criteria,
	        NavigationConditionInfoType navigationLimits) throws Exception {
		
		String prf = "[DocumentiDiProgettoDAOImplTest::objectServiceQuery] ";
		LOG.info(prf + " BEGIN");
	    
        QueryableObjectType target = new QueryableObjectType();
        target.setObject(entitaTarget);

        Integer maxItems = null;
        Integer skipCount = 0;

        PagingResponseType pagingResponse = null;
        Long rispostaLogMonitoraggio = null;
        try {
        	rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_OBJECT_QUERY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(), getPrincipalId(), target, filter, criteria, navigationLimits,
 	                maxItems, skipCount);
			pagingResponse = objectServicePort.query(getRepositoryId(), getPrincipalId(), target, filter, criteria, navigationLimits,
 	                maxItems, skipCount);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",pagingResponse);
        } catch (Exception acEx) {
        	logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",acEx.getMessage());
        	LOG.info(prf + " errore sollevato objectService.query() per " + entitaTarget);
        	LOG.info(prf + " acEx.getMessage(): " + acEx.getMessage());
        	LOG.info(prf + " errore sollevato da objectService.query()");
        	acEx.printStackTrace();
        	throw new Exception(acEx);
        }
        LOG.info(prf + " END");
        return pagingResponse;
	}
	
	private PrincipalIdType getPrincipalId() throws Exception {
		principal = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAooICE, idStrutturaICE, idNodoICE, actaUtenteAppkey, backofficeServicePort);
		LOG.info( " principal="+principal);
		return principal.getPrincipalId();
	}
	
//	public String ricercaProtocolloOfficialA(String dbKeyAggreagazione) throws Exception {
//	      
//		 // NON FUNZIONA : Il tipo di oggetto richiesto non è stato inserito tra gli oggetti interrogabili per l’Ente di appartenenza.
//		 
//	    	String prf = "[DocumentiDiProgettoDAOImplTest::ricercaProtocolloOfficialA] ";
//			LOG.info(prf + " BEGIN");
//			
////	        QueryableObjectType target = new QueryableObjectType();
////	        target.setObject("registrazioneView");
//	        
//	        PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
//	        
//	        ArrayList<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
//	        criteria.add(TestUtilsCXF.buildQueryCondition("idClassificazione", EnumQueryOperator.EQUALS, dbKeyAggreagazione));
//	        LOG.info(prf + " criteria valorizzato");
//	      
//	        NavigationConditionInfoType navigationLimits = null;
//	        QueryableObjectType target = new QueryableObjectType();
//	        target.setObject("registrazioneView");
//
//	        Integer maxItems = null;
//	        Integer skipCount = 0;
//		      
//			try {
//
//			      PagingResponseType pagingResponse = officialBookServicePort.query(getRepositoryId(), getPrincipalId(), target, filter, 
//			    		  																criteria.toArray(new QueryConditionType[criteria.size()]), 
//			    		  																navigationLimits, maxItems, skipCount);
//
//			      
//				  LOG.info(prf + " >>>>>>>>>>>> pagingResponse="+pagingResponse);
//				 
//			 } catch (Exception acEx) {
//	        	// TODO Auto-generated catch block
//	        	acEx.printStackTrace();
//				throw new Exception(acEx);
//			}
//			
//	        LOG.info(prf + " END");
//	        return "";
//	}
	
	
//	 public String ricercaProtocolloAA(String dbKeyAggreagazione) throws Exception {
//	      
//		 // NON FUNZIONA : Il tipo di oggetto richiesto non è stato inserito tra gli oggetti interrogabili per l’Ente di appartenenza.
//		 
//	    	String prf = "[DocumentiDiProgettoDAOImplTest::ricercaProtocolloAA] ";
//			LOG.info(prf + " BEGIN");
//			
////	        QueryableObjectType target = new QueryableObjectType();
////	        target.setObject("registrazioneView");
//	        
//	        PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
//	        
//	        ArrayList<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
//	        criteria.add(TestUtilsCXF.buildQueryCondition("idClassificazione", EnumQueryOperator.EQUALS, dbKeyAggreagazione));
//	        
//	        LOG.info(prf + " criteria valorizzato");
//	        
//			try {
//				
//				//officialBookService.query()
//				PagingResponseType result = objectServiceQuery("registrazioneView", filter,
//				            criteria.toArray(new QueryConditionType[criteria.size()]), null);
//				 
//		        LOG.info(prf + " objectServiceQuery invocata");
//			 } catch (Exception acEx) {
//	        	// TODO Auto-generated catch block
//	        	acEx.printStackTrace();
//				throw new Exception(acEx);
//			}
//			
//	        LOG.info(prf + " END");
//	        return "";
//	}
			
//	public String ricercaProtocolloA(String idClassificazione) throws Exception {
//		String prf = "[DocumentiDiProgettoDAOImplTest::ricercaProtocolloA] ";
//		LOG.info(prf + " BEGIN");
////		private PagingResponseType objectServiceQuery(String entitaTarget, PropertyFilterType filter,  QueryConditionType[] criteria,
////		        NavigationConditionInfoType navigationLimits) throws Exception {
//			
//		// NON FUNZIONA : Il tipo di oggetto richiesto non è stato inserito tra gli oggetti interrogabili per l’Ente di appartenenza.
//		
//	        QueryableObjectType target = new QueryableObjectType();
//	        target.setObject("RegistrazioneClassificazioniView");
//	        PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
//	        ArrayList<QueryConditionType> criteri = new ArrayList<QueryConditionType>();
//	        criteri.add(TestUtilsCXF.buildQueryCondition("objectIdClassificazione", EnumQueryOperator.EQUALS, idClassificazione));
//	        NavigationConditionInfoType navigationLimits = null;
//	        Integer maxItems = null;
//	        Integer skipCount = 0;
//	        try {
//	        	PagingResponseType pagingResponse = objectServicePort.query(getRepositoryId(), getPrincipalId(), target, filter, 
//						 criteri.toArray(new QueryConditionType[criteri.size()]), navigationLimits,  maxItems, skipCount);
//
//	        } catch (Exception acEx) {
//	        	acEx.printStackTrace();
//				throw new Exception(acEx);
//			}
//	        LOG.info(prf + " END");
//	        return "";
//	}
	
	public String ricercaProtocolloOfficial(String objectIdClassificazione) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImplTest::ricercaProtocolloOfficial] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " ricerco protocollo con objectIdClassificazione: " + objectIdClassificazione );
        
//		b)
//		se partite da una classificazione principale e avete l'objectIdClassificazione, dovete impostare il target RegistrazioneClassificazioniView con criterio di ricerca:
//		property name = " objectIdClassificazione"
//		operator = EQUALS
//		value = <objectIdClassificazione>
		
	        
        PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
        
        ArrayList<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
        criteria.add(TestUtilsCXF.buildQueryCondition("objectIdClassificazione", EnumQueryOperator.EQUALS, objectIdClassificazione));
        LOG.info(prf + " criteria valorizzato");
        
        NavigationConditionInfoType navigationLimits = null;
        QueryableObjectType target = new QueryableObjectType();
        target.setObject("RegistrazioneClassificazioniView");

        Integer maxItems = null;
        Integer skipCount = 0;
	      
        String objectId = null;
        Long rispostaLogMonitoraggio = null;
		try {
	
//			PagingResponseType result = officialBookServiceQuery("RegistrazioneClassificazioniView", filter,
//		            criteria.toArray(new QueryConditionType[criteria.size()]), null);
			
			rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_OFFICIAL_BOOK_QUERY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(), getPrincipalId(), target, filter, 
						criteria.toArray(new QueryConditionType[criteria.size()]), 
						navigationLimits, maxItems, skipCount);
		      PagingResponseType pagingResponse = officialBookServicePort.query(getRepositoryId(), getPrincipalId(), target, filter, 
		    		  																criteria.toArray(new QueryConditionType[criteria.size()]), 
		    		  																navigationLimits, maxItems, skipCount);
		      logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",pagingResponse);
		      
			  LOG.info(prf + " >>>>>>>>>>>> pagingResponse="+pagingResponse);
		        
			  String codice = "";
			  String anno = "";
			  String dataProtocollo = "";
			  
			  if(pagingResponse!=null) {
				  
				  LOG.info(prf + " >>>>>>>>>>>> pagingResponse.getObjectsLength()="+pagingResponse.getObjectsLength());
				  
				  ObjectResponseType[] objs = pagingResponse.getObjects();
				  
				  for (ObjectResponseType objectResponseType : objs) {
				 
					  if (objectResponseType.getPropertiesLength() > 0) {
						  
						  LOG.info(prf + " >>>>>>>>>>>> objectResponseType.getPropertiesLength()="+objectResponseType.getPropertiesLength());
						  
		                    for (PropertyType property : objectResponseType.getProperties())  {
		                        if ( property.getValue().getContentLength() > 0) {
		                        	LOG.info(prf + ">>>>>>>>>>>> " + property.getQueryName().getPropertyName() + ":");
		                        
		                            for (String content: property.getValue().getContent()) {
		                            	LOG.info(prf + " - " + content);
		                            }
		                            
		                            if (property.getQueryName().getPropertyName().equals("codice")) {
		                            	codice = property.getValue().getContent()[0];
		                            }
		                            if (property.getQueryName().getPropertyName().equals("anno")) {
		                            	anno = property.getValue().getContent()[0];
		                            }
		                            if (property.getQueryName().getPropertyName().equals("dataProtocollo")) {
		                            	dataProtocollo = property.getValue().getContent()[0];
		                            }
		                        }
		                  }
					  }
				  }
			  }
				  
			  LOG.info(prf + " >>>>>>>>>>>> codice=["+codice+"], anno=["+anno+"], dataProtocollo=["+ dataProtocollo+"]");
			  objectId = codice + "/" + anno;
			  
		} catch (Exception e) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		}
		
        LOG.info(prf + " END");
        return objectId;
	}
	
	
//	public String ricercaProtocollo(String objectIdClassificazione) throws Exception {
//		String prf = "[DocumentiDiProgettoDAOImplTest::ricercaProtocollo] ";
//		LOG.info(prf + " BEGIN");
//		LOG.info(prf + " ricerco protocollo con objectIdClassificazione: " + objectIdClassificazione );
//        
//        PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
//        
//        ArrayList<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
//        criteria.add(TestUtilsCXF.buildQueryCondition("objectIdClassificazione", EnumQueryOperator.EQUALS, objectIdClassificazione));
//        LOG.info(prf + " criteria valorizzato");
//        
//        String objectId = null;
//		try {
//			
//			PagingResponseType result = objectServiceQuery("RegistrazioneClassificazioniView", filter,
//			            criteria.toArray(new QueryConditionType[criteria.size()]), null);
//			  LOG.info(prf + " >>>>>>>>>>>> objectId="+objectId);
//		        
//		} catch (Exception e) {
//			LOG.info(prf + " acEx.getMessage(): " + e.getMessage());
//			e.printStackTrace();
//			throw new Exception(e);
//		}
//		
//        LOG.info(prf + " END");
//        return objectId;
//	}
	
	// ricerca la classificazione principale attiva con codice = codiceClassificazione
    // all'interno dell'aggregazione identificata da dbKeyAggreagazione
    // il codice della classificazione e' univoco all'interno di una aggregazione
    public String ricercaObjectIdClassificazionePrincipale(String codiceClassificazione, String dbKeyAggreagazione) throws Exception {
      
    	String prf = "[DocumentiDiProgettoDAOImplTest::ricercaObjectIdClassificazionePrincipale] ";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " ricerco classificazione con codice: " + codiceClassificazione + " e idAggregazionePadre: " + dbKeyAggreagazione);
        QueryableObjectType target = new QueryableObjectType();
        target.setObject(EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE.value());
        LOG.info(prf + " >>>>>>>>>>>>>>>>>>>>EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE.value()= " +EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE.value());
        PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();
        
        ArrayList<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
        criteria.add(TestUtilsCXF.buildQueryCondition("codice", EnumQueryOperator.EQUALS, codiceClassificazione));
        criteria.add(TestUtilsCXF.buildQueryCondition("dbKeyAggregazione", EnumQueryOperator.EQUALS, dbKeyAggreagazione));
        
        LOG.info(prf + " criteria valorizzato");
        
        String objectId = null;

  
		try {
			
			PagingResponseType result = objectServiceQuery(EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE.value(), filter,
			            criteria.toArray(new QueryConditionType[criteria.size()]), null);
			 
	        LOG.info(prf + " objectServiceQuery invocata");
	        
	        ObjectResponseType principaleRicercata = null;
	        if (result != null && result.getObjectsLength() > 0) {
	            ArrayList<ObjectResponseType> classificazioniPrincipali = new ArrayList<ObjectResponseType>();
	            int i = 0;
	            for (ObjectResponseType obj : result.getObjects()) {
	                if (verboseOutput) {
//	                	LOG.info(prf + (++i) + " objectId: " + AcarisUtils.pseudoDecipher(obj.getObjectId().getValue()));
	                }
	                if (obj.getPropertiesLength() > 0) {
	                    for (PropertyType property : obj.getProperties())  {
	                        if (verboseOutput && property.getValue().getContentLength() > 0) {
	                        	LOG.info(prf + " \t" + property.getQueryName().getPropertyName() + ":");
	                        
	                            for (String content: property.getValue().getContent()) {
	                            	LOG.info(prf + " \t\t" + content);
	                            }
	                        }
	                        if (property.getQueryName().getPropertyName().equals("docAllegato")) {
	                            boolean isPrincipale = false;
	                            boolean isAllegato = false;
	                            if ("S".equalsIgnoreCase(property.getValue().getContent()[0])) {
	                                isAllegato = true;
	                            } else if ("N".equalsIgnoreCase(property.getValue().getContent()[0])) {
	                                isPrincipale = true;
	                            }
	                            if (isPrincipale && !isAllegato) {
	                                classificazioniPrincipali.add(obj);
	                            }
	                        }
	                    }
	                }
	            }
	            for (ObjectResponseType classificazione: classificazioniPrincipali) {
	                for (PropertyType property : classificazione.getProperties())  {
	                    if (property.getQueryName().getPropertyName().equals("stato")) {
	                    // LOG.info(prf + " principale: " + AcarisUtils.pseudoDecipher(classificazione.getObjectId().getValue()) + " - stato: " + property.getValue().getContent()[0]);
//	                    	LOG.info(prf + " principale: " + AcarisUtils.pseudoDecipher(classificazione.getObjectId().getValue()) + " - stato: " + property.getValue().getContent()[0]);
	                        
	                    	// if ("attiva".equalsIgnoreCase(property.getValue().getContent()[0])) {
	                    	 if ("attiva".equalsIgnoreCase(property.getValue().getContent()[0])) {
	                    		 LOG.info(prf + " ATTIVA");
	                            principaleRicercata = classificazione;
	                        }
	                    }
	                }
	            }
	        }
	        LOG.info(prf + " principaleRicercata="+principaleRicercata);
	        
	        if (principaleRicercata != null) {
	            objectId = principaleRicercata.getObjectId().getValue();
	        }
	        LOG.info(prf + " >>>>>>>>>>>> objectId="+objectId);
        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e);
		}
		
        LOG.info(prf + " END");
        return objectId;
    }
	
	

	private void estrai(String objectIdClassificazione) throws  RemoteException {
		String prf = "[DocumentiDiProgettoCXFDAOImpNewlTest::estrai] ";
		LOG.info(prf + " BEGIN");

		
		// 2.5 Recupero del content-stream di un Document a partire da una classificazione
		
//		String objectIdClassificazione = getObjectIdClassificazione(pagingResponse.getObjects());
		LOG.info(prf + " objectIdClassificazione = "+objectIdClassificazione);
		
		ObjectIdType folderId = new ObjectIdType();
		folderId.setValue(objectIdClassificazione);

		PropertyFilterType filter = new PropertyFilterType();
		filter.setFilterType(EnumPropertyFilter.ALL);
		//
		// 1. A partire dall'identificativo di un oggetto Folder (sottoclasse Classificazione), invocazione del servizio getChildren (vd 10.1) 
		//    di Navigation Services per ottenere l'identificativo del Document (sottoclasse Documento archivistico)
		//
		PagingResponseType children;
		Long rispostaLogMonitoraggioNavigation = null;
		Long rispostaLogMonitoraggioRelation = null;
		Long rispostaLogMonitoraggioObject = null;
		try {
			rispostaLogMonitoraggioNavigation = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_NAVIGATION, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,repositoryId, folderId, principal.getPrincipalId(), filter );
			 
			children = navigationServicePort.getChildren(repositoryId, folderId, principal.getPrincipalId(), filter, null, 0);
			LOG.info(prf + " children ="+children);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioNavigation, "OK","","",children);
			//
			// 2. Invocazione del servizio getObjectRelationships (vd 11.1) di Relationships Services per ottenere l'identificativo del Folder 
			//    (sottoclasse Documento Fisico)
			///
	//		String direction = "source"; //source, target, either
			String objectIdStr = getObjectIdStr(children);
			LOG.info(prf + " objectIdStr ="+objectIdStr);
			
			ObjectIdType objectId = new ObjectIdType();
			objectId.setValue(objectIdStr);
	
	//		String typeId = "DocumentCompositionPropertiesType";
	
			EnumRelationshipDirectionType direction = EnumRelationshipDirectionType.SOURCE;
			EnumRelationshipObjectType typeId = EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE;
			
			rispostaLogMonitoraggioRelation = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_RELATIONSHIPS, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,repositoryId, principal.getPrincipalId(), objectId, typeId, direction, filter);
			RelationshipPropertiesType[] y = relationshipsServicePort.getObjectRelationships(repositoryId, principal.getPrincipalId(), objectId, typeId, direction, filter);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioRelation, "OK","","",y);
		
			LOG.info(prf + " RelationshipPropertiesType ="+y);

			/**
			 * 	ID repositoryId
				ID objectId: Identificativo dell'oggetto con cui la relazione e' associata 
				ID typeId: Tipo Relazione
				direction: Tipo di direzione source o target
				ID principal: Identificativo dell' attore che esegue l’operazione
				filter: Elenco delle properties.
			 */

			//
			// 3. Per ottenere il content stream ricercato, utilizzare il servizio getContentStream (vd 8.7) di Object Service utilizzando come 
			//    parametro di ingresso l'identificativo di tipo Documento Fisico.
			// 
			String objectDocFisico =  getObjectDocFisico(y);
			LOG.info(prf + " objectDocFisico ="+objectDocFisico);
			
			ObjectIdType objectIdDocFisico = new ObjectIdType();
			objectIdDocFisico.setValue(objectDocFisico); // quello [[identificativo di tipo Documento Fisico]] 
			
//			String streamId = "primary";
			EnumStreamId streamId = EnumStreamId.PRIMARY ;
			
			rispostaLogMonitoraggioObject = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_OBJECT, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,repositoryId, objectIdDocFisico, principal.getPrincipalId(), streamId);
			AcarisContentStreamType[] u = objectServicePort.getContentStream(repositoryId, objectIdDocFisico, principal.getPrincipalId(), streamId );
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioObject, "OK","","",u );
			LOG.info(prf + " AcarisContentStreamType = LETTO");
			if(u!=null) {
				LOG.info(prf + " Filename = "+u[0].getFilename());
				LOG.info(prf + " length = "+u[0].getLength());
				byte[] stream = u[0].getStream();
				
			}
				
			
			//ACARIS - Il Folder specificato non è accessibile dal principal specificato
		} catch (it.doqui.acta.acaris.navigationservice.AcarisException e2) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioNavigation, "KO",CODICE_ERRORE,"messaggioerrore",e2.getMessage());
			LOG.error(prf + "navigationservice.AcarisException e2="+e2);
			e2.printStackTrace();
			
			// se l'errore e' "ACARIS - Il Folder specificato non è accessibile dal principal specificato"
			// l'utente sta cercando di linkare un ICE di un settore diverso da quello del progetto
		
		} catch (it.doqui.acta.acaris.relationshipsservice.AcarisException e1) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioRelation, "KO",CODICE_ERRORE,"messaggioerrore",e1.getMessage());
			LOG.error(prf + "relationshipsservice.AcarisException e1="+e1);
			e1.printStackTrace();
			
		} catch (it.doqui.acta.acaris.objectservice.AcarisException e) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioObject, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			LOG.error(prf + "objectservice.AcarisException e="+e);
			e.printStackTrace();
		}

		LOG.info(prf + "END");

	}
	


	// metodi
	private String getObjectIdStr(PagingResponseType children) {
		String prf = "[DocumentiDiProgettoDAOImplTest::getObjectIdStr] ";
		LOG.info(prf +"BEGIN");
		
		String objectIdStr = null;
		
		if (children == null) {
			LOG.info(prf +"ATTENZIONE: recordset null");
		} else 	{
			int max = children.getObjects().length;
			for (int i = 0; i < max; i++) {
				LOG.info(prf +"--------------" + (i+1) + "--------------");
				ObjectResponseType ort = children.getObjects()[i];
				
				for(int j = 0; j < ort.getProperties().length; j++) {
					PropertyType pt =  ort.getProperties()[j];
					LOG.info(prf + pt.getQueryName().getClassName()	+ "." + pt.getQueryName().getPropertyName() + ": ");
					
					for(int k = 0; k < pt.getValue().getContent().length; k++) {
						LOG.info(prf +"("+k+")    " + pt.getValue().getContent()[k]);
						if("objectId".equals(pt.getQueryName().getPropertyName())) {
							objectIdStr = pt.getValue().getContent()[k];
						}
					}
				}
			}	
		}
		LOG.info(prf +"END, objectIdStr="+objectIdStr);
		return objectIdStr;
	}
	
	
	private String getObjectDocFisico(RelationshipPropertiesType[] y) {
		String prf = "[DocumentiDiProgettoDAOImplTest::getObjectDocFisico] ";
		LOG.info(prf +"BEGIN");
		
		
		/**
		 * Devi prendere in considerazione gli elementi dell'array con  
		 * RelationshipPropertiesType.relationType = EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE 
		 * che hanno come RelationshipPropertiesType.targetType = EnumArchiveObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE. 
		 * Per questi elementi, l'identificativo che ti serve è il valore di RelationshipPropertiesType.targetId

Utilizza il valore di questo identificativo come input del servizio 
objectService.getContentStream(repositoryId, objectIdDocFisico, principalId, tipoContenuto)
, con tipoContenuto = EnumStreamId.PRIMARY

		 */
		String objectDocFis = null;
		if (y == null || y.length==0) {
			LOG.info(prf +"ATTENZIONE: recordset null");
		} else 	{
			
			for (int i = 0; i < y.length; i++) {
				RelationshipPropertiesType rel = y[i];
				
				LOG.info(prf +"rel.getRelationType()="+rel.getRelationType());
				LOG.info(prf +"rel.getTargetType()="+rel.getTargetType()); //DocumentoFisicoPropertiesType
				LOG.info(prf +"rel.getTargetId()="+rel.getTargetId()); 
				
			//	if("DocumentCompositionPropertiesType".equals(rel.getRelationType()) && "DocumentoFisicoPropertiesType".equals(rel.getTargetType())){
				if(EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE.equals(rel.getRelationType()) && 
					EnumArchiveObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE.equals(rel.getTargetType())) {
					LOG.info(prf +"ATTENZIONE:SONO DENTRO");
					ObjectIdType ww = rel.getTargetId();
					if(ww!=null) {
						LOG.info(prf +"ObjectIdType ww="+ww.getValue());
						objectDocFis = ww.getValue();
					}
				}
				
//				ObjectIdType ey = rel.getObjectId();
//				if(ey!=null)
//					LOG.info(prf +"ObjectIdType ey="+ey.getValue());
//				
//				ObjectIdType sr = rel.getSourceId();
//				if(sr!=null) {
//					LOG.info(prf +"ObjectIdType sr="+sr.getValue());
//					objectDocFis = sr.getValue();
//				}
//				ObjectIdType ww = rel.getTargetId();
//				if(ww!=null)
//					LOG.info(prf +"ObjectIdType ww="+ww.getValue());
				
			}
		}
		
		LOG.info(prf +"END, objectDocFis="+objectDocFis);
		return objectDocFis;
	}
	
	// cerca tra gli allegati della classificazione principale (identificata da objectIdClassPrincipale) l'unico attivo con codice = codiceDocumentoAllegato
    // puo' esistere una sola classificazione allegata attiva con un dato codice
    public String recuperaAllegatoDaPrincipale(String objectIdClassPrincipale, String codiceDocumentoAllegato) {
    	String prf = "[DocumentiDiProgettoDAOImplTest::recuperaAllegatoDaPrincipale] ";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " ricerco classificazione con codice: " + codiceDocumentoAllegato + ", allegata a classificazione "
            + AcarisUtils.pseudoDecipher(objectIdClassPrincipale));
		
        ObjectIdType rootNodeId = new ObjectIdType();
        rootNodeId.setValue(objectIdClassPrincipale);

        PropertyFilterType filter = TestUtilsCXF.getPropertyFilterAll();

        String objectIdAllegatoByCodice = null;
        Long rispostaLogMonitoraggio = null;
        try {
        	rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(), rootNodeId, getPrincipalId(), filter);
            PagingResponseType result = navigationServicePort.getChildren(getRepositoryId(), rootNodeId, getPrincipalId(), filter, null, 0);
            logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",result);
            LOG.info(prf + " navigationServicePort.getChildren invocata");
            
            if (result.getObjects() != null && result.getObjects().length > 0) {
                ArrayList<ObjectResponseType> allegatiAttivi = new ArrayList<ObjectResponseType>();
                for (int i = 0; i < result.getObjects().length; ++i) {
                    ObjectResponseType obj = result.getObjects()[i];
                    
                    if (EnumObjectType.GRUPPO_ALLEGATI_PROPERTIES_TYPE.value().equals(AcarisUtils.decodeTipoTargetIdFromObjIdDeChiper(obj.getObjectId().getValue()))) {
                    	LOG.info(prf + " ricerco figli di " + AcarisUtils.pseudoDecipher(obj.getObjectId().getValue()));
                        
                    	PagingResponseType allegati = navigationServicePort.getChildren(getRepositoryId(),
                            obj.getObjectId(), getPrincipalId(), filter, null, 0);
                        
                    	if (allegati.getObjects() != null && allegati.getObjects().length > 0) {
                            boolean trovato = false;
                            for (int j = 0; !trovato && j < allegati.getObjects().length; j++) {
                                ObjectResponseType allegato = allegati.getObjects()[j];
                                if (verboseOutput) {
                                	 LOG.info(prf  + (j+1) + " objectId child: " + AcarisUtils.pseudoDecipher(allegato.getObjectId().getValue()));
                                }
                                if (allegato.getProperties()!=null && allegato.getProperties().length>0) {
//                                if (allegato.getPropertiesLength() > 0) {
                                    for (PropertyType property : allegato.getProperties())  {
                                    	if (verboseOutput && property.getValue()!=null && property.getValue().getContent().length > 0) {
//                                    	if (verboseOutput && property.getValue().getContentLength() > 0) {
                                        	LOG.info(prf + " \t" + property.getQueryName().getPropertyName() + ":");
//                                            for (String content: property.getValue().getContent()) {
                                        	for (String content: property.getValue().getContent()) {
                                            	 LOG.info(prf + " \t\t" + content);
                                            }
                                        }
                                        if (property.getQueryName().getPropertyName().equals("stato")) {
                                        	 if ("attiva".equalsIgnoreCase(property.getValue().getContent()[0])) {
                                                allegatiAttivi.add(allegato);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                
                if(allegatiAttivi!=null) {
                	 LOG.info(prf + " trovati allegatiAttivi.size = "+allegatiAttivi.size());
                }
                for (ObjectResponseType allegato: allegatiAttivi) {
                    for (PropertyType property : allegato.getProperties())  {
                        if (property.getQueryName().getPropertyName().equals("codice")) {
                            //String codice = property.getValue().getContent()[0];
                        	String codice = property.getValue().getContent()[0];
                            LOG.info(prf + " allegato attivo: " + AcarisUtils.pseudoDecipher(allegato.getObjectId().getValue()) + " - codice: " + codice);
                            if (codice.equals(codiceDocumentoAllegato)) {
                                objectIdAllegatoByCodice = allegato.getObjectId().getValue();
                                break;
                            }
                        }
                    }
                }
            }
            
        } catch (it.doqui.acta.acaris.navigationservice.AcarisException acEx) {
        	logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",acEx.getMessage());
        	LOG.error(prf + " errore sollevato dal servizio getChildren");
        	LOG.error(prf + " acEx.getMessage(): " + acEx.getMessage());
        	LOG.error(prf + " acEx.getFaultInfo().getErrorCode(): " + acEx.getFaultInfo().getErrorCode());
        	LOG.error(prf + " acEx.getFaultInfo().getPropertyName(): " + acEx.getFaultInfo().getPropertyName());
        	LOG.error(prf + " acEx.getFaultInfo().getObjectId(): " + acEx.getFaultInfo().getObjectId());
        	LOG.error(prf + " acEx.getFaultInfo().getExceptionType(): " + acEx.getFaultInfo().getExceptionType());
        	LOG.error(prf + " acEx.getFaultInfo().getClassName(): " + acEx.getFaultInfo().getClassName());
        	LOG.error(prf + " acEx.getFaultInfo().getTechnicalInfo: " + acEx.getFaultInfo().getTechnicalInfo());
        	acEx.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
        }finally {
        	LOG.info(prf + " END");
		}
        
        return objectIdAllegatoByCodice;
    }


	public DecodificheDAOImpl getDecodificheDAOImpl() {
		return decodificheDAOImpl;
	}


	public void setDecodificheDAOImpl(DecodificheDAOImpl decodificheDAOImpl) {
		this.decodificheDAOImpl = decodificheDAOImpl;
	}
}
