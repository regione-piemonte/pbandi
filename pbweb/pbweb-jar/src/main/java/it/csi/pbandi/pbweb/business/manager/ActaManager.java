/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.business.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.integration.dao.GestioneLog;
import it.csi.pbandi.pbweb.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbweb.util.AcarisUtils;
import it.csi.pbandi.pbweb.util.Constants;
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
import it.doqui.acta.acaris.officialbookservice.OfficialBookService;
import it.doqui.acta.acaris.officialbookservice.OfficialBookServicePort;
import it.doqui.acta.acaris.relationshipsservice.RelationshipsService;
import it.doqui.acta.acaris.relationshipsservice.RelationshipsServicePort;
import it.doqui.acta.acaris.repositoryservice.AcarisException;
import it.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumArchiveObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumRelationshipDirectionType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumRelationshipObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.RelationshipPropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
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
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryableObjectType;


public class ActaManager {

	private Logger logger = Logger.getLogger(Constants.COMPONENT_NAME);
	
	@Autowired
	private DecodificheDAOImpl decodificheDAOImpl;
	
	@Autowired
	private GestioneLog logMonitoraggioService;
	
	private BackOfficeServicePort backofficeServicePort; 
	private RepositoryServicePort repositoryServicePort; 
	private ObjectServicePort objectServicePort; 
	private NavigationServicePort navigationServicePort;
	private RelationshipsServicePort relationshipsServicePort;
	private OfficialBookServicePort officialBookServicePort;
	
	private ObjectIdType repositoryId;
	private PrincipalExtResponseType principal; 

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.acta");
	private static final String CONSUMER_KEY = RESOURCE_BUNDLE.getString("acta.CONSUMER_KEY");
	private static final String CONSUMER_SECRET = RESOURCE_BUNDLE.getString("acta.CONSUMER_SECRET");
	private static final String OAUTH_URL = RESOURCE_BUNDLE.getString("acta.OAUTH_URL");
	private static final String endpointBackOfficeService = RESOURCE_BUNDLE.getString("acta.endpointBackOfficeService");
	private static final String endpointRepositoryService = RESOURCE_BUNDLE.getString("acta.endpointRepositoryService");
	private static final String endpointObjectService = RESOURCE_BUNDLE.getString("acta.endpointObjectService");
	private static final String endpointNavigationService = RESOURCE_BUNDLE.getString("acta.endpointNavigationService");
	private static final String endpointRelationshipsService = RESOURCE_BUNDLE.getString("acta.endpointRelationshipsService");
	private static final String endpointOfficialBookService = RESOURCE_BUNDLE.getString("acta.endpointOfficialBookService");
	
	
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
    //objectServicePort.getContentStream DA MAPPARE
	
    
	
	private boolean verboseOutput = true;

	// trippletta letta dal db
	private long idAoo;
	private long idStruttura;
	private long idNodo;
	
	// trippletta ricavata della collocazione del settore del progetto su cui si vuole inserire il doc ICE
	private long idAooICE;
	private long idStrutturaICE;
	private long idNodoICE;
	
	private String codiceFiscale = null ;
	private String actaUtenteAppkey = null;
	private String actaRepositoryName = null;
	
	private static String ERROR_ICE_NOT_VALID = "Codice di classificazione esteso non valido.";
	private static String ERROR_ACTA_SERVICE = "Impossibile contattare i servizi di Acta.";
	private static String ERROR_ACTA_PRINCIPAL = "Impossibile individuare il principal su Acta.";
	private static String ERROR_ACTA_COLLOCATION = "Collocazione del file non trovata su Acta.";
	private static String ERROR_ACTA_ICE = "Codice di classificazione esteso non trovato.";
	private static String ERROR_ACTA_CLASSIFICAZIONE = "Classificazione non trovata su Acta.";
	private static String ERROR_ACTA_ICE_PROJ = "Il codice di classificazione esteso non appartiene al settore del progetto corrente.";
	private static String ERROR_ACTA_GENERIC = "Acta ha restituito un errore generico.";
	
	private String[] iceSplit  = new String[3];
	private boolean isAllegato = false;
	private String dbKeyAggreagazione = "";
	
	public AcarisContentStreamType ricercaClassificazioneByIndiceClassEstesoCXF(String ice, String descSettore) throws Exception {
		String prf = "[ActaManager::ricercaClassificazioneByIndiceClassEstesoCXF] ";
		logger.info(prf + " BEGIN");
		
		long tInizio = System.currentTimeMillis();
		AcarisContentStreamType docActa = null;
		
		try {
			
//			actaRepositoryName = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_REPOSITORYNAME, true);
//			codiceFiscale = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_UTENTECF, true);
//			actaUtenteAppkey = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_UTENTEAPPKEY, true);
//			logger.info(prf + " actaRepositoryName="+actaRepositoryName);
//			logger.info(prf + " codiceFiscale="+codiceFiscale);
//			logger.info(prf + " actaUtenteAppkey="+actaUtenteAppkey);
//			
//			idAoo = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_IDAOO, true));
//			idStruttura = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_IDSTRUTTURA, true));
//			idNodo = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_IDNODO, true));
//			
//			String[] iceSplit = splitAggregazioneIce(ice);
//			for (int i = 0; i < iceSplit.length; i++) {
//				logger.info(prf + " iceSplit["+i+"]={"+iceSplit[i]+"}");
//			}
//			
//			 if (iceSplit[0] == null) {
//				 logger.error(prf + "ERRORE scomposizione ice classificazione: ice aggregazione non trovato!");
//				 throw new Exception(ERROR_ICE_NOT_VALID);
//	        }
//	        if (iceSplit[1] == null) {
//	        	logger.error(prf + "ERRORE scomposizione ice classificazione: codice documento (principale) non trovato!");
//	        	throw new Exception(ERROR_ICE_NOT_VALID);
//	        }
	
//	        boolean isAllegato = iceSplit[2] != null; 
//	        logger.info(prf + "isAllegato="+isAllegato);
//	        if (!isAllegato) {
//	        	logger.info(prf + " Indice Classificazione di un documento PRINCIPALE");
//	        } else {
//	        	logger.info(prf + " Indice Classificazione di un documento ALLEGATO");
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
//	        logger.info(prf + " inizializzazione avvenuta");
//	        
//	        PrincipalExtResponseType principalMaster = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
//	        logger.info( prf + "principalMaster="+principalMaster + ", time="+start);
//	        
//	        // ricavo idAoo, idStruttura, idNodo della collocazione del settore del progetto su cui si vuole inserire il doc ICE
//        	String AOO_STR_NODO_VIEW = "AooStrNodoView";
//        	
//        	QueryableObjectType target = new QueryableObjectType();
//		    target.setObject(AOO_STR_NODO_VIEW);
//		    PropertyFilterType filter = AcarisUtils.getPropertyFilterAll();
//			
//			QueryConditionType[] criteria = { AcarisUtils.buildQueryCondition("codiceStruttura", EnumQueryOperator.EQUALS, descSettore), 
//												AcarisUtils.buildQueryCondition("flagResponsabile", EnumQueryOperator.EQUALS, "S")};
//
//			NavigationConditionInfoType navigationLimits = new NavigationConditionInfoType();
//			Integer maxItems = null;
//	        Integer skipCount = 0;
//	        PagingResponseType pagingResponse = null;
//	        try {
//	        	pagingResponse = backofficeServicePort.query(getRepositoryId(), principalMaster.getPrincipalId(), target, filter, criteria, navigationLimits, maxItems, skipCount);
//	        	logger.debug(prf + "pagingResponse= " + pagingResponse);
//				if (pagingResponse == null || pagingResponse.getObjects() == null) {
//					logger.error("(GET USER SPECIFIC COLLOCATION) Errore : backofficeServicePort.query non ha restituito nulla ");
//					throw new Exception(ERROR_ACTA_COLLOCATION);
//				}
//	        }catch(Exception e) {
//	        	logger.error("pagingResponse exception= "+e.getMessage());
//				throw new Exception(ERROR_ACTA_COLLOCATION);
//	        }
//			
//			IdAOOType userIdAOOType = new IdAOOType();
//			IdNodoType userIdNodoType = new  IdNodoType();
//			IdStrutturaType userIdStrutturaType= new  IdStrutturaType();
//			mapActaUserCollocation(userIdAOOType, userIdStrutturaType, userIdNodoType, pagingResponse);
//			
//			idAooICE = userIdAOOType.getValue();
//			idStrutturaICE = userIdStrutturaType.getValue();
//			idNodoICE = userIdNodoType.getValue();
//					
//			logger.info(prf + "******************** Trovata TRIPPLETTA da usare ");
//			logger.info(prf + "idAooICE: "+idAooICE);
//			logger.info(prf + "idStrutturaICE : "+idStrutturaICE);
//			logger.info(prf + "idNodoICE: "+idNodoICE);
//			logger.info(prf + "********************");
//			
//	        String dbKeyAggreagazione = ricercaIdAggregazioneByIce(iceSplit[0]); 
//	        logger.info(prf + " dbKeyAggreagazione="+dbKeyAggreagazione);
//	        
//	        logger.info(prf + " T ricerca aggr by ice: " + (System.currentTimeMillis() - start) + " ms");
//	        
//	        if(dbKeyAggreagazione == null) {
//	        	throw new Exception(ERROR_ACTA_ICE);
//	        }
        
			inizalizzaActa( ice,  descSettore);
			
	        long start = System.currentTimeMillis();
	        
	        String objectIdClassificazione = ricercaObjectIdClassificazionePrincipale(iceSplit[1], dbKeyAggreagazione);
	        logger.info(prf + " objectIdClassificazione="+objectIdClassificazione);
	        
	        logger.info(prf + " T ricerca classificazione (principale): " + (System.currentTimeMillis() - start)+ " ms");
	        
	        if (objectIdClassificazione == null) {
	        	logger.error(prf +"ERRORE ricerca classificazione da codice documento (principale)!");
	        	throw new Exception(ERROR_ACTA_CLASSIFICAZIONE);
	        }
	        
	        docActa = estrai(objectIdClassificazione);

	        String objectIdClassificazioneAllegata = null;
	        if (isAllegato) {
	            start = System.currentTimeMillis();
	            objectIdClassificazioneAllegata = recuperaAllegatoDaPrincipale(objectIdClassificazione, iceSplit[2]);
	            logger.info(prf + " T ricerca classificazione (allegata da principale): " + (System.currentTimeMillis() - start));
	            if (objectIdClassificazioneAllegata != null) {
	            	logger.info(prf + " trovata classificazione allegata: " + AcarisUtils.pseudoDecipher(objectIdClassificazioneAllegata));
	            } else {
	            	logger.info(prf + " NON trovata classificazione allegata by indice classificazione esteso");
	            }
	        }
	        
	        logger.info(prf + "RicercaClassificazioneByIce fine, T totale: "
	            + (System.currentTimeMillis() - tInizio));

		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			throw new Exception(e);
		}
		logger.info(prf + " END");
		return docActa;
	}
	

	private void inizalizzaActa(String ice, String descSettore) throws Exception {
		String prf = "[ActaManager::inizalizzaActa] ";
		logger.info(prf + " BEGIN");
		
		actaRepositoryName = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_REPOSITORYNAME, true);
		codiceFiscale = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_UTENTECF, true);
		actaUtenteAppkey = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_UTENTEAPPKEY, true);
		logger.info(prf + " actaRepositoryName="+actaRepositoryName);
		logger.info(prf + " codiceFiscale="+codiceFiscale);
		logger.info(prf + " actaUtenteAppkey="+actaUtenteAppkey);
		
		idAoo = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_IDAOO, true));
		idStruttura = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_IDSTRUTTURA, true));
		idNodo = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_IDNODO, true));
		
		iceSplit = splitAggregazioneIce(ice);
		for (int i = 0; i < iceSplit.length; i++) {
			logger.info(prf + " iceSplit["+i+"]={"+iceSplit[i]+"}");
		}
		
		 if (iceSplit[0] == null) {
			 logger.error(prf + "ERRORE scomposizione ice classificazione: ice aggregazione non trovato!");
			 throw new Exception(ERROR_ICE_NOT_VALID);
        }
        if (iceSplit[1] == null) {
        	logger.error(prf + "ERRORE scomposizione ice classificazione: codice documento (principale) non trovato!");
        	throw new Exception(ERROR_ICE_NOT_VALID);
        }

        isAllegato = iceSplit[2] != null; 
        logger.info(prf + "isAllegato="+isAllegato);
        if (!isAllegato) {
        	logger.info(prf + " Indice Classificazione di un documento PRINCIPALE");
        } else {
        	logger.info(prf + " Indice Classificazione di un documento ALLEGATO");
        }
	    
        long start = System.currentTimeMillis();
        
        inizializzaBackOfficeService();
		inizializzaRepositoryService();
		inizializzaObjectService();
		inizializzaNavigationService();
		inizializzaRelationshipsService();
		inizializzaOfficialBookService();
        logger.info(prf + " inizializzazione avvenuta");
        
        PrincipalExtResponseType principalMaster = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
        logger.info( prf + "principalMaster="+principalMaster + ", time="+start);
        
        // ricavo idAoo, idStruttura, idNodo della collocazione del settore del progetto su cui si vuole inserire il doc ICE
    	String AOO_STR_NODO_VIEW = "AooStrNodoView";
    	
    	QueryableObjectType target = new QueryableObjectType();
	    target.setObject(AOO_STR_NODO_VIEW);
	    PropertyFilterType filter = AcarisUtils.getPropertyFilterAll();
		
		QueryConditionType[] criteria = { AcarisUtils.buildQueryCondition("codiceStruttura", EnumQueryOperator.EQUALS, descSettore), 
											AcarisUtils.buildQueryCondition("flagResponsabile", EnumQueryOperator.EQUALS, "S")};

		NavigationConditionInfoType navigationLimits = new NavigationConditionInfoType();
		Integer maxItems = null;
        Integer skipCount = 0;
        PagingResponseType pagingResponse = null;
        Long rispostaLogMonitoraggio = null;
        try {
        	rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE_QUERY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(), principalMaster.getPrincipalId(), target, filter, criteria, navigationLimits, maxItems, skipCount);
        	pagingResponse = backofficeServicePort.query(getRepositoryId(), principalMaster.getPrincipalId(), target, filter, criteria, navigationLimits, maxItems, skipCount);
        	logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",pagingResponse);
        	logger.debug(prf + "pagingResponse= " + pagingResponse);
			if (pagingResponse == null || pagingResponse.getObjects() == null) {
				logger.error("(GET USER SPECIFIC COLLOCATION) Errore : backofficeServicePort.query non ha restituito nulla ");
				throw new Exception(ERROR_ACTA_COLLOCATION);
			}
        }catch(Exception e) {
        	logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
        	logger.error("pagingResponse exception= "+e.getMessage());
			throw new Exception(ERROR_ACTA_COLLOCATION);
        }
		
		IdAOOType userIdAOOType = new IdAOOType();
		IdNodoType userIdNodoType = new  IdNodoType();
		IdStrutturaType userIdStrutturaType= new  IdStrutturaType();
		mapActaUserCollocation(userIdAOOType, userIdStrutturaType, userIdNodoType, pagingResponse);
		
		idAooICE = userIdAOOType.getValue();
		idStrutturaICE = userIdStrutturaType.getValue();
		idNodoICE = userIdNodoType.getValue();
				
		logger.info(prf + "******************** Trovata TRIPPLETTA da usare ");
		logger.info(prf + "idAooICE: "+idAooICE);
		logger.info(prf + "idStrutturaICE : "+idStrutturaICE);
		logger.info(prf + "idNodoICE: "+idNodoICE);
		logger.info(prf + "********************");
		
        dbKeyAggreagazione = ricercaIdAggregazioneByIce(iceSplit[0]); 
        logger.info(prf + " dbKeyAggreagazione="+dbKeyAggreagazione);
        
        logger.info(prf + " T ricerca aggr by ice: " + (System.currentTimeMillis() - start) + " ms");
        
        if(dbKeyAggreagazione == null) {
        	throw new Exception(ERROR_ACTA_ICE);
        }
        logger.info(prf + "END");
	}


	private String[] splitAggregazioneIce(String classificazioneIce) {
		String prf = "[ActaManager::splitAggregazioneIce] ";
		logger.info(prf + " BEGIN");
		
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
        
        logger.info(prf + " indice classificazione esteso: " + classificazioneIce);
        logger.info(prf +" indice classificazione esteso AGGREGAZIONE: " + aggrIce);
        // nel caso in cui l'indice class esteso preso fa riferimento ad un allegato, penultimo elemento indica il suo principale
        // nel caso in cui l'ice classificazione è un principale, allora ultimo elemento indica il suo principale
        logger.info(prf +" codice classificazione: " + (codiceClassificazione != null ? codiceClassificazione : "non trovato"));
        // nel caso in cui l'indice class esteso preso fa riferimento ad un allegato, ultimo elemento è allegato
        logger.info(prf +" codice classificazione (allegato): " + (codiceClassificazioneAllegato != null ? codiceClassificazioneAllegato : "non trovato"));
        
        String[] result = {aggrIce.toString(), codiceClassificazione, codiceClassificazioneAllegato};
        
        return result;
	}
	
	private void inizializzaRelationshipsService() throws Exception {
		String prf = "[ActaManager::inizializzaRelationshipsService] ";
		logger.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-RelationshipsService.wsdl");
			RelationshipsService rls = new  RelationshipsService(url);
			RelationshipsServicePort port = rls.getRelationshipsServicePort();
			logger.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointRelationshipsService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			logger.debug(prf + " oauth= "+oauth);
			
			logger.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			logger.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			logger.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointRelationshipsService);
			gwfb.setWrappedInterface(RelationshipsServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			Object o = gwfb.create();
			relationshipsServicePort = (RelationshipsServicePort) o;
			logger.debug(prf + " relationshipsServicePort= "+relationshipsServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_SERVICE);
		}finally {
			logger.info(prf + " END");
		}
	}

	private void inizializzaNavigationService() throws Exception {
		String prf = "[ActaManager::inizializzaNavigationService] ";
		logger.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-NavigationService.wsdl");
			NavigationService os = new  NavigationService(url);
			NavigationServicePort port = os.getNavigationServicePort();
			logger.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointNavigationService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			logger.debug(prf + " oauth= "+oauth);
			
			logger.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			logger.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			logger.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointNavigationService);
			gwfb.setWrappedInterface(NavigationServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			Object o = gwfb.create();
			navigationServicePort = (NavigationServicePort) o;
			logger.debug(prf + " navigationServicePort= "+navigationServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_SERVICE);
		}finally {
			logger.info(prf + " END");
		}
	}

	private void inizializzaObjectService() throws Exception {
		String prf = "[ActaManager::inizializzaObjectService] ";
		logger.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-ObjectService.wsdl");
			ObjectService os = new  ObjectService(url);
			ObjectServicePort port = os.getObjectServicePort();
			logger.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointObjectService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			logger.debug(prf + " oauth= "+oauth);
			
			logger.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			logger.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			logger.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointObjectService);
			gwfb.setWrappedInterface(ObjectServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			Object o = gwfb.create();
			objectServicePort = (ObjectServicePort) o;
			logger.debug(prf + " objectServicePort= "+objectServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_SERVICE);
		}finally {
			logger.info(prf + " END");
		}
	}

	private void inizializzaRepositoryService() throws Exception {
		String prf = "[ActaManager::inizializzaRepositoryService] ";
		logger.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-RepositoryService.wsdl");
			RepositoryService rs = new  RepositoryService(url);
			RepositoryServicePort port = rs.getRepositoryServicePort();
			logger.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointRepositoryService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			logger.debug(prf + " oauth= "+oauth);
			
			logger.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			logger.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			logger.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointRepositoryService);
			gwfb.setWrappedInterface(RepositoryServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			Object o = gwfb.create();
			repositoryServicePort = (RepositoryServicePort) o;
			logger.debug(prf + " repositoryServicePort= "+repositoryServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_SERVICE);
		}finally {
			logger.info(prf + " END");
		}
	}

	private void inizializzaBackOfficeService() throws Exception {

		String prf = "[ActaManager::inizializzaBackOfficeService] ";
		logger.info(prf + " BEGIN");
		
		try {
//			URL url = new URL("http://localhost/actasrv/backofficeWS?wsdl");
			URL url = this.getClass().getClassLoader().getResource("/wsdl/ACARISWS-BackOfficeService.wsdl");
			BackOfficeService bs = new  BackOfficeService(url);
			BackOfficeServicePort port = bs.getBackOfficeServicePort();
			logger.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointBackOfficeService);
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			logger.debug(prf + " oauth= "+oauth);
			
			logger.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			logger.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			logger.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointBackOfficeService);
			gwfb.setWrappedInterface(BackOfficeServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);

			Object o = gwfb.create();
			backofficeServicePort = (BackOfficeServicePort) o;

			logger.debug(prf + " backofficeServicePort= "+backofficeServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_SERVICE);
		}finally {
			logger.info(prf + " END");
		}
	}
	
	private void inizializzaOfficialBookService() throws Exception {
		String prf ="[ActaManager::inizializzaOfficialBookService] ";
		logger.info(prf + " BEGIN");
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-OfficialBookService.wsdl");
			OfficialBookService obs = new  OfficialBookService(url);
			OfficialBookServicePort port = obs.getOfficialBookServicePort();
			logger.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();
			
			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointOfficialBookService);
			
			OauthHelper oauth = new OauthHelper(OAUTH_URL,CONSUMER_KEY, CONSUMER_SECRET,10000); // time out in ms
			logger.debug(prf + " oauth= "+oauth);
			
			logger.debug(prf + " TokenTetry version : " + OauthHelper.getVersion());
			
			TokenRetryManager trm = new TokenRetryManager();
			logger.debug(prf + " trm.version="+trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			logger.debug(prf + " WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint(endpointOfficialBookService);
			gwfb.setWrappedInterface(OfficialBookServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);
			
			Object o = gwfb.create();
			officialBookServicePort = (OfficialBookServicePort) o;
			
			logger.debug(prf + " officialBookServicePort= "+officialBookServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_SERVICE);
		}finally {
			logger.info(prf + " END");
		}
	}
	
	
	private String ricercaIdAggregazioneByIce(String iceAggregazione) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImplTest::ricercaIdAggregazioneByIce] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + " ricerco aggregazione con ice: " + iceAggregazione);
		
		 String dbKeyAggreagazione = null;
		 
		try {
	        PropertyFilterType filter = AcarisUtils.getPropertyFilterAll();
	        logger.info(prf + " filter = "+filter);
	        
	        QueryConditionType[] criteria = { AcarisUtils.buildQueryCondition("indiceClassificazioneEstesa", EnumQueryOperator.EQUALS, iceAggregazione) };
	        logger.info(prf + " criteria = "+criteria);
	        
	        PagingResponseType result = objectServiceQuery("AggregazioneICEView", filter, criteria, null);
	        logger.info(prf + "PagingResponseType result = "+result);
	        
	        if (result.getObjectsLength() > 0) {
	            for (ObjectResponseType obj : result.getObjects()) {
	                if (obj.getPropertiesLength() > 0) {
	                    for (PropertyType property : obj.getProperties())  {
	                    	// PK : TODO mi serve la property "dataFine".....
	                    	// se dataFine!=null l'indice di classificazione NON e' valido 
	                    	// (il documento e' stato spostato dalla sua posizione)
	                    	logger.info(prf + "property.getQueryName().getPropertyName()="+property.getQueryName().getPropertyName());
	                        if (property.getQueryName().getPropertyName().equals("dbKey")) {
	                        	dbKeyAggreagazione = property.getValue().getContent()[0];
	                        }
	                    }
	                }
	            }
	        }
		}catch(Exception e) {
			throw new Exception(ERROR_ACTA_GENERIC);
		}finally {
			logger.info(prf + " END");
		}
        return dbKeyAggreagazione;
	}
	
	// ricerca la classificazione principale attiva con codice = codiceClassificazione
    // all'interno dell'aggregazione identificata da dbKeyAggreagazione
    // il codice della classificazione e' univoco all'interno di una aggregazione
	private String ricercaObjectIdClassificazionePrincipale(String codiceClassificazione, String dbKeyAggreagazione) throws Exception {
      
    	String prf = "[DocumentiDiProgettoDAOImplTest::ricercaObjectIdClassificazionePrincipale] ";
		logger.info(prf + " BEGIN");
		
		logger.info(prf + " ricerco classificazione con codice: " + codiceClassificazione + " e idAggregazionePadre: " + dbKeyAggreagazione);
        QueryableObjectType target = new QueryableObjectType();
        target.setObject(EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE.value());
        PropertyFilterType filter = AcarisUtils.getPropertyFilterAll();
        
        ArrayList<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
        criteria.add(AcarisUtils.buildQueryCondition("codice", EnumQueryOperator.EQUALS, codiceClassificazione));
        criteria.add(AcarisUtils.buildQueryCondition("dbKeyAggregazione", EnumQueryOperator.EQUALS, dbKeyAggreagazione));
        
        logger.info(prf + " criteria valorizzato");
        String objectId = null;
  
		try {
        
			PagingResponseType result = objectServiceQuery(EnumObjectType.CLASSIFICAZIONE_PROPERTIES_TYPE.value(), filter,
			            criteria.toArray(new QueryConditionType[criteria.size()]), null);
			 
	        logger.info(prf + " objectServiceQuery invocata");
	        
	        ObjectResponseType principaleRicercata = null;
	        if (result != null && result.getObjectsLength() > 0) {
	            ArrayList<ObjectResponseType> classificazioniPrincipali = new ArrayList<ObjectResponseType>();
	            int i = 0;
	            for (ObjectResponseType obj : result.getObjects()) {
	                if (verboseOutput) {
//	                	logger.info(prf + (++i) + " objectId: " + AcarisUtils.pseudoDecipher(obj.getObjectId().getValue()));
	                }
	                if (obj.getPropertiesLength() > 0) {
	                    for (PropertyType property : obj.getProperties())  {
	                        if (verboseOutput && property.getValue().getContentLength() > 0) {
	                        	logger.info(prf + " \t" + property.getQueryName().getPropertyName() + ":");
	                        
	                            for (String content: property.getValue().getContent()) {
	                            	logger.info(prf + " \t\t" + content);
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
	                    	 if ("attiva".equalsIgnoreCase(property.getValue().getContent()[0])) {
	                    		 logger.info(prf + " ATTIVA");
	                            principaleRicercata = classificazione;
	                        }
	                    }
	                }
	            }
	        }
	        logger.info(prf + " principaleRicercata="+principaleRicercata);
	        
	        if (principaleRicercata != null) {
	            objectId = principaleRicercata.getObjectId().getValue();
	        }
	        logger.info(prf + " >>>>>>>>>>>> objectId="+objectId);
        
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_CLASSIFICAZIONE);
		}
		
        logger.info(prf + " END");
        return objectId;
    }
    
    private AcarisContentStreamType estrai(String objectIdClassificazione) throws  Exception {
		String prf = "[ActaManager::estrai] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + " objectIdClassificazione = "+objectIdClassificazione);
		
		AcarisContentStreamType acstt = null;
		// 2.5 Recupero del content-stream di un Document a partire da una classificazione
		
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
		Long rispostaLogMonitoraggioRelationships = null;
		Long rispostaLogMonitoraggioObject = null;
		try {
			rispostaLogMonitoraggioNavigation = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_NAVIGATION, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,repositoryId, folderId, principal.getPrincipalId(), filter);
			children = navigationServicePort.getChildren(repositoryId, folderId, principal.getPrincipalId(), filter, null, 0);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioNavigation, "OK","","",children);
			logger.info(prf + " children ="+children);
			
			// 2. Invocazione del servizio getObjectRelationships (vd 11.1) di Relationships Services per ottenere l'identificativo del Folder 
			//    (sottoclasse Documento Fisico)
			///
			String objectIdStr = getObjectIdStr(children);
			logger.info(prf + " objectIdStr ="+objectIdStr);
			
			ObjectIdType objectId = new ObjectIdType();
			objectId.setValue(objectIdStr);
	
			EnumRelationshipDirectionType direction = EnumRelationshipDirectionType.SOURCE;
			EnumRelationshipObjectType typeId = EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE;
			
			rispostaLogMonitoraggioRelationships = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_RELATIONSHIPS, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,repositoryId, principal.getPrincipalId(), objectId, typeId, direction, filter);
			RelationshipPropertiesType[] rlsprt = relationshipsServicePort.getObjectRelationships(repositoryId, principal.getPrincipalId(), objectId, typeId, direction, filter);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioRelationships, "OK","","",rlsprt);
			logger.info(prf + " RelationshipPropertiesType ="+rlsprt);

			// 3. Per ottenere il content stream ricercato, utilizzare il servizio getContentStream (vd 8.7) di Object Service utilizzando come 
			//    parametro di ingresso l'identificativo di tipo Documento Fisico.
			// 
			String objectDocFisico =  getObjectDocFisico(rlsprt);
			logger.info(prf + " objectDocFisico ="+objectDocFisico);
			
			ObjectIdType objectIdDocFisico = new ObjectIdType();
			objectIdDocFisico.setValue(objectDocFisico); 
			
			EnumStreamId streamId = EnumStreamId.PRIMARY ;
			rispostaLogMonitoraggioObject = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_OBJECT_QUERY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,repositoryId, objectIdDocFisico, principal.getPrincipalId(), streamId );
			AcarisContentStreamType[] arrayACST = objectServicePort.getContentStream(repositoryId, objectIdDocFisico, principal.getPrincipalId(), streamId );
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioObject, "OK","","",arrayACST);
			logger.info(prf + " AcarisContentStreamType = LETTO");
			if(arrayACST!=null) {
				logger.info(prf + " arrayACST.length = "+arrayACST.length);
				logger.info(prf + " Filename = "+arrayACST[0].getFilename());
//				logger.info(prf + " length = "+arrayACST[0].getLength());
//				logger.info(prf + " STREAM = "+arrayACST[0].getStream());
//				DataHandler dha = arrayACST[0].getStreamMTOM();
//				logger.info(prf + " DataHandler = "+ dha);
				acstt = arrayACST[0];
			}
				
		} catch (it.doqui.acta.acaris.navigationservice.AcarisException e2) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioNavigation, "KO",CODICE_ERRORE,"messaggioerrore",e2.getMessage());
			logger.error(prf + "navigationservice.AcarisException e2="+e2);
			e2.printStackTrace();
			
			if(e2.getMessage()!=null && e2.getMessage().contains("accessibile dal principal")) {
				// se l'errore e' "ACARIS - Il Folder specificato non è accessibile dal principal specificato"
				// l'utente sta cercando di linkare un ICE di un settore diverso da quello del progetto	
				throw new Exception(ERROR_ACTA_ICE_PROJ);
			}
			throw new Exception(ERROR_ACTA_GENERIC);
		
		} catch (it.doqui.acta.acaris.relationshipsservice.AcarisException e1) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioRelationships, "KO",CODICE_ERRORE,"messaggioerrore",e1.getMessage());
			logger.error(prf + "relationshipsservice.AcarisException e1="+e1);
			e1.printStackTrace();
			throw new Exception(ERROR_ACTA_GENERIC);
			
		} catch (it.doqui.acta.acaris.objectservice.AcarisException e) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioObject, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			logger.error(prf + "objectservice.AcarisException e="+e);
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_GENERIC);
		}finally {
			logger.info(prf + "END");
		}
		return acstt;
	}
    
 // cerca tra gli allegati della classificazione principale (identificata da objectIdClassPrincipale) l'unico attivo con codice = codiceDocumentoAllegato
    // puo' esistere una sola classificazione allegata attiva con un dato codice
    private String recuperaAllegatoDaPrincipale(String objectIdClassPrincipale, String codiceDocumentoAllegato) throws Exception {
    	String prf = "[DocumentiDiProgettoDAOImplTest::recuperaAllegatoDaPrincipale] ";
		logger.info(prf + " BEGIN");
		
		logger.info(prf + " ricerco classificazione con codice: " + codiceDocumentoAllegato + ", allegata a classificazione "
            + AcarisUtils.pseudoDecipher(objectIdClassPrincipale));
		
        ObjectIdType rootNodeId = new ObjectIdType();
        rootNodeId.setValue(objectIdClassPrincipale);

        PropertyFilterType filter = AcarisUtils.getPropertyFilterAll();

        String objectIdAllegatoByCodice = null;
        Long rispostaLogMonitoraggio = null;
        Long rispostaLogMonitoraggioNavigationInterno = null;
        Boolean IdentificareSecodoNavigation =false;
        try {
        	rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_NAVIGATION, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(), rootNodeId, getPrincipalId(), filter);
            PagingResponseType result = navigationServicePort.getChildren(getRepositoryId(), rootNodeId, getPrincipalId(), filter, null, 0);
            logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",result);
            logger.info(prf + " navigationServicePort.getChildren invocata");
            
            if (result.getObjects() != null && result.getObjects().length > 0) {
                ArrayList<ObjectResponseType> allegatiAttivi = new ArrayList<ObjectResponseType>();
                for (int i = 0; i < result.getObjects().length; ++i) {
                    ObjectResponseType obj = result.getObjects()[i];
                    
                    if (EnumObjectType.GRUPPO_ALLEGATI_PROPERTIES_TYPE.value().equals(AcarisUtils.decodeTipoTargetIdFromObjIdDeChiper(obj.getObjectId().getValue()))) {
                    	logger.info(prf + " ricerco figli di " + AcarisUtils.pseudoDecipher(obj.getObjectId().getValue()));
                    	IdentificareSecodoNavigation = true;
                    	rispostaLogMonitoraggioNavigationInterno = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_NAVIGATION, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(),obj.getObjectId(), getPrincipalId(), filter);
                    	PagingResponseType allegati = navigationServicePort.getChildren(getRepositoryId(),
                            obj.getObjectId(), getPrincipalId(), filter, null, 0);
                    	logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioNavigationInterno, "OK","","",allegati);
                    	if (allegati.getObjects() != null && allegati.getObjects().length > 0) {
                            boolean trovato = false;
                            for (int j = 0; !trovato && j < allegati.getObjects().length; j++) {
                                ObjectResponseType allegato = allegati.getObjects()[j];
                                if (verboseOutput) {
                                	 logger.info(prf  + (j+1) + " objectId child: " + AcarisUtils.pseudoDecipher(allegato.getObjectId().getValue()));
                                }
                                if (allegato.getProperties()!=null && allegato.getProperties().length>0) {
                                    for (PropertyType property : allegato.getProperties())  {
                                    	if (verboseOutput && property.getValue()!=null && property.getValue().getContent().length > 0) {
                                        	logger.info(prf + " \t" + property.getQueryName().getPropertyName() + ":");
                                        	for (String content: property.getValue().getContent()) {
                                            	 logger.info(prf + " \t\t" + content);
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
                	 logger.info(prf + " trovati allegatiAttivi.size = "+allegatiAttivi.size());
                }
                for (ObjectResponseType allegato: allegatiAttivi) {
                    for (PropertyType property : allegato.getProperties())  {
                        if (property.getQueryName().getPropertyName().equals("codice")) {
                            //String codice = property.getValue().getContent()[0];
                        	String codice = property.getValue().getContent()[0];
                            logger.info(prf + " allegato attivo: " + AcarisUtils.pseudoDecipher(allegato.getObjectId().getValue()) + " - codice: " + codice);
                            if (codice.equals(codiceDocumentoAllegato)) {
                                objectIdAllegatoByCodice = allegato.getObjectId().getValue();
                                break;
                            }
                        }
                    }
                }
            }
            

        } catch (it.doqui.acta.acaris.navigationservice.AcarisException acEx) {
        	if(IdentificareSecodoNavigation) {
        		logMonitoraggioService.trackCallPost(rispostaLogMonitoraggioNavigationInterno, "KO",CODICE_ERRORE,"messaggioerrore",acEx.getMessage());
        	}else {
        		logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",acEx.getMessage());
        	}
        	logger.error(prf + " AcarisException sollevata dal servizio getChildren, "+acEx.getMessage());
        	acEx.printStackTrace();
        	throw new Exception(ERROR_ACTA_GENERIC);
        } catch (Exception e) {
        	logger.error(prf + " Exception sollevata dal servizio getChildren");
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_GENERIC);
		}finally {
        	logger.info(prf + " END");
		}
        
        return objectIdAllegatoByCodice;
    }
    
    private PagingResponseType objectServiceQuery(String entitaTarget, PropertyFilterType filter,  QueryConditionType[] criteria,
	        NavigationConditionInfoType navigationLimits) throws Exception {
		
		String prf = "[DocumentiDiProgettoDAOImplTest::objectServiceQuery] ";
		logger.info(prf + " BEGIN");
		
	    
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
        	logger.error(prf + " errore sollevato objectService.query() per " + entitaTarget);
        	logger.error(prf + " acEx.getMessage(): " + acEx.getMessage());
        	acEx.printStackTrace();
        	throw new Exception(ERROR_ACTA_GENERIC);
        }
	    return pagingResponse;
	}
    
    private String getObjectIdStr(PagingResponseType children) {
		String prf = "[DocumentiDiProgettoDAOImplTest::getObjectIdStr] ";
		logger.info(prf +"BEGIN");
		
		String objectIdStr = null;
		
		if (children == null) {
			logger.info(prf +"ATTENZIONE: recordset null");
		} else 	{
			int max = children.getObjects().length;
			for (int i = 0; i < max; i++) {
				logger.info(prf +"--------------" + (i+1) + "--------------");
				ObjectResponseType ort = children.getObjects()[i];
				
				for(int j = 0; j < ort.getProperties().length; j++) {
					PropertyType pt =  ort.getProperties()[j];
					logger.info(prf + pt.getQueryName().getClassName()	+ "." + pt.getQueryName().getPropertyName() + ": ");
					
					for(int k = 0; k < pt.getValue().getContent().length; k++) {
						logger.info(prf +"("+k+")    " + pt.getValue().getContent()[k]);
						if("objectId".equals(pt.getQueryName().getPropertyName())) {
							objectIdStr = pt.getValue().getContent()[k];
						}
					}
				}
			}	
		}
		logger.info(prf +"END, objectIdStr="+objectIdStr);
		return objectIdStr;
	}
    
    private String getObjectDocFisico(RelationshipPropertiesType[] y) {
		String prf = "[DocumentiDiProgettoDAOImplTest::getObjectDocFisico] ";
		logger.info(prf +"BEGIN");
		
		/**
		 * Devi prendere in considerazione gli elementi dell'array con  
		 * RelationshipPropertiesType.relationType = EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE 
		 * che hanno come RelationshipPropertiesType.targetType = EnumArchiveObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE. 
		 * Per questi elementi, l'identificativo che ti serve è il valore di RelationshipPropertiesType.targetId
		 *	Utilizza il valore di questo identificativo come input del servizio 
		 *	objectService.getContentStream(repositoryId, objectIdDocFisico, principalId, tipoContenuto)
		 *	, con tipoContenuto = EnumStreamId.PRIMARY
		 */
		String objectDocFis = null;
		if (y == null || y.length==0) {
			logger.info(prf +"ATTENZIONE: recordset null");
		} else 	{
			
			for (int i = 0; i < y.length; i++) {
				RelationshipPropertiesType rel = y[i];
				
				logger.info(prf +"rel.getRelationType()="+rel.getRelationType());
				logger.info(prf +"rel.getTargetType()="+rel.getTargetType()); //DocumentoFisicoPropertiesType
				logger.info(prf +"rel.getTargetId()="+rel.getTargetId()); 
				
				if(EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE.equals(rel.getRelationType()) && 
					EnumArchiveObjectType.DOCUMENTO_FISICO_PROPERTIES_TYPE.equals(rel.getTargetType())) {
//					logger.info(prf +"ATTENZIONE:SONO DENTRO");
					ObjectIdType ww = rel.getTargetId();
					if(ww!=null) {
						logger.info(prf +"ObjectIdType ww="+ww.getValue());
						objectDocFis = ww.getValue();
					}
				}
				
//				ObjectIdType ey = rel.getObjectId();
//				if(ey!=null)
//					logger.info(prf +"ObjectIdType ey="+ey.getValue());
//				
//				ObjectIdType sr = rel.getSourceId();
//				if(sr!=null) {
//					logger.info(prf +"ObjectIdType sr="+sr.getValue());
//					objectDocFis = sr.getValue();
//				}
//				ObjectIdType ww = rel.getTargetId();
//				if(ww!=null)
//					logger.info(prf +"ObjectIdType ww="+ww.getValue());
				
			}
		}
		logger.info(prf +"END, objectDocFis="+objectDocFis);
		return objectDocFis;
	}
		
    private PrincipalIdType getPrincipalId() throws Exception  {

    	// TODO 
//		if(principal==null)
			principal = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAooICE, idStrutturaICE, idNodoICE, actaUtenteAppkey, backofficeServicePort);
		logger.info( " principal="+principal);
		return principal.getPrincipalId();
	}

	private ObjectIdType getRepositoryId() throws Exception {
		if(repositoryId==null)
			repositoryId = getRepositoryId(actaRepositoryName, repositoryServicePort);
		return repositoryId;
    }
	
	private ObjectIdType getRepositoryId(String actaRepositoryName, RepositoryServicePort repositoryServicePort) throws Exception  {
		String prf = "[ActaManager::getRepositoryId] ";
		logger.info(prf + " BEGIN");
		
		ObjectIdType repositoryId = null;
		Long rispostaLogMonitoraggio = null;
		try {
			rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_REPOSITORY_GET_REPOSITORY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,null);
			AcarisRepositoryEntryType[] reps = repositoryServicePort.getRepositories();
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",reps);
			if(reps == null){
				logger.error(prf + " repositoryServicePort.getRepositories() non ha resituito resultati");
				return null;
			}
			for (AcarisRepositoryEntryType acarisRepositoryEntryType : reps) {
				logger.info(prf + " RepositoryName(): "+acarisRepositoryEntryType.getRepositoryName() );
				String repositoryName = acarisRepositoryEntryType.getRepositoryName();
				
				if(actaRepositoryName.equalsIgnoreCase(repositoryName)){
					repositoryId = acarisRepositoryEntryType.getRepositoryId();
					logger.info(prf + "selezionato RepositoryId del repositoryName = ["+repositoryName+"]");
					break;
				}
			}
		
		} catch (AcarisException e) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			logger.error(prf + " Error in repositoryServicePort.getRepositories()"+e.getMessage(),e);
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_GENERIC);
		}finally {
			logger.info(prf + " END"); 
		}
		return repositoryId;
	}
	
	private PrincipalExtResponseType getPrincipalExt(String idRepository, String codiceFiscale, long idAoo, long idStruttura,
			long idNodo, String actaUtenteAppkey, BackOfficeServicePort backofficeServicePort) throws Exception  {
		String prf = "[ActaManager::getPrincipalExt] ";
		logger.debug(prf + " BEGIN ");
		
		logger.debug(prf + " idAoo= "+idAoo);
		logger.debug(prf + " idStruttura= "+idStruttura);
		logger.debug(prf + " idNodo= "+idNodo);
		
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
		Long rispostaLogMonitoraggio = null;
		try {
//			DettaglioAOOType auo = backofficeServicePort.getDettaglioAOO(objectIdType, _idAOO);
//			logger.debug(prf + " auo.denominazione= "+auo.getDenominazione());
//			logger.debug(prf + " auo.codice= "+auo.getCodice());
			
			rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_BACK_OFFICE_GET_PRINCIPAL_EXT, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,objectIdType, codFiscale, _idAOO, _idStruttura, _idNodo, clientApplicationInfo);
			arrPrincipalExt = backofficeServicePort.getPrincipalExt(objectIdType, codFiscale, _idAOO, _idStruttura, _idNodo, clientApplicationInfo);
			
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","",arrPrincipalExt);
			
			if (arrPrincipalExt!=null) {
				logger.debug(prf + " >>>>>>> arrPrincipalExt lenght= "+arrPrincipalExt.length);
				logger.debug(prf + " >>>>>>> Time = "+ start);
			}
			
		} catch (Exception e) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			logger.error(prf + " e.getMessage(): " + e.getMessage());		
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_PRINCIPAL);
		} finally {
			long end = System.currentTimeMillis();
			logger.debug(prf + " END, durata " + (end-start)/1000);
		}
		return arrPrincipalExt[0];
	}
	
	private void mapActaUserCollocation(IdAOOType userIdAOOType, IdStrutturaType userIdStrutturaType,
			IdNodoType userIdNodoType, PagingResponseType pagingResponse) {
		
		ObjectResponseType[] objectResponseTypes = pagingResponse.getObjects();
		logger.info("mapActaUser (searchUSerCollocation)objectResponseTypes " + objectResponseTypes.length);
		
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
		logger.info("user specific idAoo: "+userIdAOOType.getValue()
			  +"idNodo : "+userIdNodoType.getValue()
			  +"idStruttura: "+userIdStrutturaType.getValue());
	}
	
	public DecodificheDAOImpl getDecodificheDAOImpl() {
		return decodificheDAOImpl;
	}

	public void setDecodificheDAOImpl(DecodificheDAOImpl decodificheDAOImpl) {
		this.decodificheDAOImpl = decodificheDAOImpl;
	}


	public PropertyType[] ricercaProtocolloDocumentoByIndiceClassEstesoCXF(String ice,
			String descSettore) throws Exception {
		
		String prf = "[ActaManager::ricercaProtocolloDocumentoByIndiceClassEstesoCXF] ";
		logger.debug(prf + " BEGIN ");
		PropertyType[] props = null;
		try {
			inizalizzaActa( ice,  descSettore);
			long start = System.currentTimeMillis();
		        
	        String objectIdClassificazione = ricercaObjectIdClassificazionePrincipale(iceSplit[1], dbKeyAggreagazione);
	        logger.info(prf + " objectIdClassificazione="+objectIdClassificazione);
	        
	        if (objectIdClassificazione == null) {
	        	logger.error(prf +"ERRORE ricerca classificazione da codice documento (principale)!");
	        	throw new Exception(ERROR_ACTA_CLASSIFICAZIONE);
	        }
	        
	        props = ricercaProtocolloOfficial(objectIdClassificazione);
	        logger.info(prf + " props ="+props);
	        logger.info(prf + " T ricerca protocollo: " + (System.currentTimeMillis() - start)+ " ms");
		
		} catch (Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			throw new Exception(e);

		}finally {
			logger.debug(prf + " END ");
		}
		return props;
	}


	private PropertyType[] ricercaProtocolloOfficial(String objectIdClassificazione) throws Exception {
		String prf = "[ActaManager::ricercaProtocolloOfficial] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + " ricerco protocollo con objectIdClassificazione: " + objectIdClassificazione );
        
//		b)
//		se partite da una classificazione principale e avete l'objectIdClassificazione, dovete impostare il target RegistrazioneClassificazioniView con criterio di ricerca:
//		property name = " objectIdClassificazione"
//		operator = EQUALS
//		value = <objectIdClassificazione>
		
		PropertyType[] props = null;
        PropertyFilterType filter = AcarisUtils.getPropertyFilterAll();
        
        ArrayList<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
        criteria.add(AcarisUtils.buildQueryCondition("objectIdClassificazione", EnumQueryOperator.EQUALS, objectIdClassificazione));
        logger.info(prf + " criteria valorizzato");
        
        NavigationConditionInfoType navigationLimits = null;
        QueryableObjectType target = new QueryableObjectType();
        target.setObject("RegistrazioneClassificazioniView");

        Integer maxItems = null;
        Integer skipCount = 0;
        Long rispostaLogMonitoraggio = null; 
		try {
			
			rispostaLogMonitoraggio = logMonitoraggioService.trackCallPre(ID_URL_SERVICE_OFFICIAL_BOOK_QUERY, ID_LOG_MONITORAGGIO_UTENTE, MOD_CHIAMATA_READ, ID_LOG_ENTITA, ID_LOG_TARGHET,getRepositoryId(), getPrincipalId(), target, filter, 
						criteria.toArray(new QueryConditionType[criteria.size()]), 
						navigationLimits, maxItems, skipCount);
			PagingResponseType pagingResponse = officialBookServicePort.query(getRepositoryId(), getPrincipalId(), target, filter, 
		  																criteria.toArray(new QueryConditionType[criteria.size()]), 
		  																navigationLimits, maxItems, skipCount);
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "OK","","serie dossier bando linea created",pagingResponse);
			logger.info(prf + " pagingResponse="+pagingResponse);
			if(pagingResponse!=null) {
				logger.info(prf + " >>>>>>>>>>>> pagingResponse.getObjectsLength()="+pagingResponse.getObjectsLength());
				ObjectResponseType[] objs = pagingResponse.getObjects();
				for (ObjectResponseType objectResponseType : objs) {
					if (objectResponseType.getPropertiesLength() > 0) {
						logger.info(prf + " >>>>>>>>>>>> objectResponseType.getPropertiesLength()="+objectResponseType.getPropertiesLength());
						props = objectResponseType.getProperties();
					}
				}
			}
		} catch (Exception e) {
			logMonitoraggioService.trackCallPost(rispostaLogMonitoraggio, "KO",CODICE_ERRORE,"messaggioerrore",e.getMessage());
			e.printStackTrace();
			throw new Exception(e);
		}finally {
			logger.info(prf + " END");
		}
        return props;
	}
}
