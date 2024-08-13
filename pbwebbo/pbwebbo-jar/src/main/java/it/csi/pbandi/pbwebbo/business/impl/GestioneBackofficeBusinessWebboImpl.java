/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.impl;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.ws.BindingProvider;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionebackoffice.GestioneBackOfficeEsitoGenerico;
import it.csi.pbandi.pbservizit.pbandisrv.exception.gestionebackoffice.GestioneBackOfficeException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaEnteCompAttiviVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRBandoLineaEnteCompVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTLogProtocollazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
import it.doqui.acta.acaris.backofficeservice.AcarisException;
import it.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.doqui.acta.acaris.backofficeservice.BackOfficeServicePort;
import it.doqui.acta.acaris.objectservice.ObjectService;
import it.doqui.acta.acaris.objectservice.ObjectServicePort;
import it.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.PrincipalExtResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.CodiceFiscaleType;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumPropertyFilter;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumQueryOperator;
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

public class GestioneBackofficeBusinessWebboImpl {

	@Autowired
	protected LoggerUtil logger;
	
	@Autowired
	private GenericDAO genericDAO;
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.acta"); // PK, file della componente che include questa lib
	private static final String endpointRepositoryService = RESOURCE_BUNDLE.getString("acta.endpointRepositoryService");
	private static final String endpointBackOfficeService = RESOURCE_BUNDLE.getString("acta.endpointBackOfficeService");
	private static final String endpointObjectService = RESOURCE_BUNDLE.getString("acta.endpointObjectService");
	
	private static final String CONSUMER_KEY = RESOURCE_BUNDLE.getString("acta.CONSUMER_KEY");
	private static final String CONSUMER_SECRET = RESOURCE_BUNDLE.getString("acta.CONSUMER_SECRET");
	private static final String OAUTH_URL = RESOURCE_BUNDLE.getString("acta.OAUTH_URL");
	
	static String ERRORE_SERVER = "E.E002"; // Non e' stato possibile portare a termine l'operazione.
	static String METODO_VERIFICA_LOG_PROTOCOLLAZIONE = "VERIFICA";
	static String ACTA_QUERY_TARGET_AOOSTRNODOVIEW = "AooStrNodoView";
	static String ACTA_QUERY_TARGET_FASCICOLI = "SerieFascicoliPropertiesType";
	static String ACTA_QUERY_TARGET_DOSSIER = "DossierPropertiesType";
	static String ACTA_QUERY_NAME_CODICE_STRUTTURA = "codiceStruttura";
	static String ACTA_QUERY_NAME_PAROLACHIAVE = "paroleChiave";
	static NavigationConditionInfoType navigationConditionInfoType = new NavigationConditionInfoType(); // lasciarlo cosi per ora, volendo si può far partire la query da un nodo preciso
	private static Integer maxItems = null;
	private static Integer skipCount = 0;
	
	static BackOfficeServicePort backOfficeServicePort;
	static RepositoryServicePort repositoryServicePort ;
	static ObjectServicePort objectServicePort;
	
	
	public GestioneBackOfficeEsitoGenerico verificaParolaChiaveActa(Long idUtente, String identitaDigitale, Long progrBandoLineaEnteComp)
			throws CSIException, SystemException, UnrecoverableException, GestioneBackOfficeException {
			
			String prf = "[GestioneBackofficeBusinessImpl::verificaParolaChiaveActa] ";
			logger.info(prf + " BEGIN");
			logger.info(prf + " progrBandoLineaEnteComp = "+progrBandoLineaEnteComp);
			
			String[] nameParameter = { "idUtente", "identitaDigitale", "progrBandoLineaEnteComp" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progrBandoLineaEnteComp);
			
			GestioneBackOfficeEsitoGenerico esito = new GestioneBackOfficeEsitoGenerico();
			String enteSettore="", parolaChiaveActa="";		
			try {						
				
				// Recupera alcuni dati da PBANDI_V_BANDO_LINEA_ENTE_COMP.
				BandoLineaEnteCompAttiviVO ente = new BandoLineaEnteCompAttiviVO();
				ente.setProgrBandoLineaEnteComp(new BigDecimal(progrBandoLineaEnteComp));
				List<BandoLineaEnteCompAttiviVO> lista = genericDAO.findListWhere(ente);
				if (lista == null || lista.size() == 0 || lista.get(0) == null) {
					throw new Exception("Record in PBANDI_V_BANDO_LINEA_ENTE_COMP con progrBandoLineaEnteComp = "+progrBandoLineaEnteComp+" NON trovato");
				} else {
					enteSettore = lista.get(0).getDescEnteSettore();
					parolaChiaveActa = lista.get(0).getParolaChiave();				
					if (StringUtil.isEmpty(enteSettore))
						throw new Exception("EnteSettore non valorizzato");
					if (StringUtil.isEmpty(parolaChiaveActa))
						throw new Exception("parolaChiaveActa non valorizzata");
				}
				
				//    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				//				VALORI DA USARE PER I  TEST
				//enteSettore = "A11000";
				//parolaChiaveActa = "A11_bando1_PM";
				//    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
				logger.info(prf + " enteSettore = "+enteSettore+"; parolaChiaveActa = "+parolaChiaveActa);
				
				// Recupera alcune costanti da db.
				String actaRepositoryName = this.leggiCostanteActa("conf.actaRepositoryName",idUtente);
				if (StringUtil.isEmpty(actaRepositoryName))
					throw new Exception("Costante conf.actaRepositoryName non valorizzata.");
				String actaUtenteCodfiscale = this.leggiCostanteActa("conf.actaUtenteCodfiscale",idUtente);
				if (StringUtil.isEmpty(actaUtenteCodfiscale))
					throw new Exception("Costante conf.actaUtenteCodfiscale non valorizzata.");
				String actaUtenteAppkey = this.leggiCostanteActa("conf.actaUtenteAppkey",idUtente);
				if (StringUtil.isEmpty(actaUtenteAppkey))
					throw new Exception("Costante conf.actaUtenteAppkey non valorizzata.");				
				String actaIdAOO = this.leggiCostanteActa("conf.actaIdAOO",idUtente);
				if (StringUtil.isEmpty(actaIdAOO))
					throw new Exception("Costante conf.actaIdAOO non valorizzata.");
				String actaIdStruttura = this.leggiCostanteActa("conf.actaIdStruttura",idUtente);
				if (StringUtil.isEmpty(actaIdStruttura))
					throw new Exception("Costante conf.actaIdStruttura non valorizzata.");	
				String actaIdNodo = this.leggiCostanteActa("conf.actaIdNodo",idUtente);
				if (StringUtil.isEmpty(actaIdNodo))
					throw new Exception("Costante conf.actaIdNodo non valorizzata.");
				
				logger.info(prf + "Inizio inizializzazione servizi Acta.");
				this.initializeBackOfficeService();
				this.inizializzaRepositoryService();
				this.initializeObjectService();
				logger.info(prf + "Fine inizializzazione servizi Acta.");
				
				String idRepository = this.getRepositoryId(actaRepositoryName, idUtente);
				if (StringUtil.isEmpty(idRepository))
					throw new Exception("Identificativo repository non individuato.");	
				
				
				IdAOOType idAOOType = new IdAOOType();
				idAOOType.setValue(new Long(actaIdAOO));			
				IdStrutturaType idStrutturaType = new IdStrutturaType();
				idStrutturaType.setValue(new Long(actaIdStruttura));			
				IdNodoType idNodoType = new IdNodoType();
				idNodoType.setValue(new Long(actaIdNodo));
				
				PrincipalIdType principalIdType = this.getPrincipalIdType(idRepository,actaUtenteCodfiscale,idAOOType,idStrutturaType,idNodoType,actaUtenteAppkey,idUtente);
				if (principalIdType == null || StringUtil.isEmpty(principalIdType.getValue()))
					throw new Exception("Nessun principalIdType trovato.");
				
				PagingResponseType pagingResponseType = this.getObjectIDActa(idRepository,principalIdType,ACTA_QUERY_TARGET_AOOSTRNODOVIEW,ACTA_QUERY_NAME_CODICE_STRUTTURA,enteSettore,idUtente);
				if (pagingResponseType == null || pagingResponseType.getObjects() == null)
					throw new Exception("Nessun pagingResponseType trovato.");
				
				// collocazione specifica dello user legato al progetto
			    IdAOOType userIdAOOType = new IdAOOType();
			    IdStrutturaType userIdStrutturaType= new  IdStrutturaType();
			    IdNodoType userIdNodoType = new  IdNodoType();
			    mapActaUserCollocation(userIdAOOType, userIdStrutturaType, userIdNodoType, pagingResponseType);
				
				PrincipalIdType userPrincipalIdType = this.getPrincipalIdType(idRepository,actaUtenteCodfiscale,userIdAOOType,userIdStrutturaType,userIdNodoType,actaUtenteAppkey,idUtente);
				if (userPrincipalIdType == null || StringUtil.isEmpty(userPrincipalIdType.getValue()))
					throw new Exception("Nessun userPrincipalIdType trovato.");
				
				// Primo tentativo di ricerca della parola chiave.
				boolean parolaChiaveVerificata = false;
				logger.info(prf + "Prima ricerca con target = "+ACTA_QUERY_TARGET_FASCICOLI);
				PagingResponseType userPagingResponseType1 = this.getObjectID(idRepository,userPrincipalIdType,ACTA_QUERY_TARGET_FASCICOLI,ACTA_QUERY_NAME_PAROLACHIAVE,parolaChiaveActa,idUtente);
				if (userPagingResponseType1 == null || userPagingResponseType1.getObjects() == null || userPagingResponseType1.getObjects().length == 0) {
					// Secondo tentativo di ricerca della parola chiave.
					logger.info(prf + "La prima ricerca NON ha trovato la parola chiave");
					logger.info(prf + "Seconda ricerca con target = "+ACTA_QUERY_TARGET_DOSSIER);
					PagingResponseType userPagingResponseType2 = this.getObjectID(idRepository,userPrincipalIdType,ACTA_QUERY_TARGET_DOSSIER,ACTA_QUERY_NAME_PAROLACHIAVE,parolaChiaveActa,idUtente);
					if (userPagingResponseType2 == null || userPagingResponseType2.getObjects() == null || userPagingResponseType2.getObjects().length == 0) {
						logger.info(prf + "La seconda ricerca NON ha trovato la parola chiave");
						parolaChiaveVerificata = false;
					} else {
						logger.info(prf + "La seconda ricerca ha trovato la parola chiave");
						parolaChiaveVerificata = true;
					}
				} else {
					logger.info(prf + "La prima ricerca ha trovato la parola chiave");
					parolaChiaveVerificata = true;
				}
				
				// Se verificata, lo memorizzo in PBANDI_R_BANDO_LINEA_ENTE_COMP.FEEDBACK_ACTA
				if (parolaChiaveVerificata) {
					try {
						PbandiRBandoLineaEnteCompVO vo = new PbandiRBandoLineaEnteCompVO();
						vo.setProgrBandoLineaEnteComp(new BigDecimal(progrBandoLineaEnteComp));
						vo.setFeedbackActa(it.csi.pbandi.pbservizit.pbandisrv.util.Constants.FEEDBACK_ACTA_VERIFICATA);
						vo.setIdUtenteAgg(new BigDecimal(idUtente));
						genericDAO.update(vo);
					} catch (Exception e) {
						throw new Exception("Errore nell'update di PbandiRBandoLineaEnteComp.");
					}
				}
				
				esito.setEsito(parolaChiaveVerificata);
				if (parolaChiaveVerificata)
					esito.setMessage("Parola chiave verificata.");			
				else
					esito.setMessage("Parola chiave non verificata.");
				logger.info(prf + " esito restituito:"+esito.getEsito());
				
			} catch (Exception e) {
				logger.error(prf + "Errore durante la verifica della parola chiave Acta "+parolaChiaveActa, e);
				esito.setEsito(false);
				esito.setMessage(ERRORE_SERVER);
				logger.info(prf + " esito restituito:"+esito.getEsito());
			}  
			logger.info(prf + "END");
			return esito;
		}
	
	private void initializeObjectService() {
		String prf = "[GestioneBackofficeBusinessImpl::initializeObjectService] ";
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
			
			objectServicePort = null;
			try {
				Object o = gwfb.create();
				objectServicePort = (ObjectServicePort) o;
			} catch (Exception ex) {
				logger.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			logger.debug(prf + " objectServicePort= "+objectServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		logger.info(prf + " END");
	}

	private void initializeBackOfficeService() {
		String prf = "[GestioneBackofficeBusinessImpl::initializeBackOfficeService] ";
		logger.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-BackOfficeService.wsdl");
			logger.info(prf + " url="+url);
			
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
			
			backOfficeServicePort = null;
			try {
				Object o = gwfb.create();
	//			inspect(o);
				backOfficeServicePort = (BackOfficeServicePort) o;
			} catch (Exception ex) {
				logger.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			logger.debug(prf + " backOfficeServicePort= "+backOfficeServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		logger.info(prf + " END");
	}
	
	private  void mapActaUserCollocation(IdAOOType userIdAOOType,
			IdStrutturaType userIdStrutturaType, IdNodoType userIdNodoType,
			PagingResponseType query) {
		ObjectResponseType[] objectResponseTypes = query.getObjects();
		logger.info("mapActaUserCollocation: num elementi in objectResponseTypes = " + objectResponseTypes.length);
		for (ObjectResponseType objectResponseType : objectResponseTypes) {
			PropertyType[] properties = objectResponseType.getProperties();
			for (PropertyType propertyType : properties) {
				String propertyName = propertyType.getQueryName().getPropertyName();
				if (propertyName.equalsIgnoreCase("idAoo")) {
					 userIdAOOType.setValue(Long.valueOf( propertyType.getValue().getContent()[0]));
//					userIdAOOType.setValue(Long.valueOf( propertyType.getValue()[0]));
				} else if (propertyName.equalsIgnoreCase("idNodo")) {
					userIdNodoType.setValue(Long.valueOf(propertyType.getValue().getContent()[0] ));
//					userIdNodoType.setValue(Long.valueOf(propertyType.getValue()[0]));
				} else if (propertyName.equalsIgnoreCase("idStruttura")) {
					userIdStrutturaType.setValue(Long.valueOf(propertyType.getValue().getContent()[0] ));
//					userIdStrutturaType.setValue(Long.valueOf(propertyType.getValue()[0] ));
				}
			}
		}
		// collocazione dell'utente specifico
		logger.info("user specific idAoo: "+userIdAOOType.getValue()			  
			  +"; idStruttura: "+userIdStrutturaType.getValue()
			  +"; idNodo : "+userIdNodoType.getValue());
	}
	
	private void inizializzaRepositoryService() {
		String prf = "[GestioneBackofficeBusinessImpl::inizializzaRepositoryService] ";
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
			
			repositoryServicePort = null;
			try {
				Object o = gwfb.create();
				repositoryServicePort = (RepositoryServicePort) o;
			} catch (Exception ex) {
				logger.debug(prf + " Exception " + ex.getMessage());
				ex.printStackTrace();
			}
			logger.debug(prf + " repositoryServicePort= "+repositoryServicePort);
		}catch(Exception e) {
			logger.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
		}
		
		logger.info(prf + " END");
		
	}

	private String getRepositoryId(
			String actaRepositoryName, Long idUtente) {
		logger.info("Chiamo repositoryServicePort.getRepositories().");
		String repositoryIdOutput = null;
		boolean repositoryFound=false;
		try {
			AcarisRepositoryEntryType[] reps = null;
				reps = repositoryServicePort.getRepositories();
			if(ObjectUtil.isEmpty(reps)){
				logger.error("repositoryServicePort.getRepositories() non ha resituito resultati");
				return null;
			}
			for (AcarisRepositoryEntryType acarisRepositoryEntryType : reps) {
				logger.info("acarisRepositoryEntryType.getRepositoryName(): "+acarisRepositoryEntryType.getRepositoryName() + 
						" ,acarisRepositoryEntryType.getRepositoryId().getValue():" + acarisRepositoryEntryType.getRepositoryId().getValue());
				String repositoryName = acarisRepositoryEntryType.getRepositoryName();
				
				if(actaRepositoryName.equalsIgnoreCase(repositoryName)){
					ObjectIdType repositoryId = acarisRepositoryEntryType.getRepositoryId();
					repositoryIdOutput = repositoryId.getValue();
					repositoryFound=true;
					break;
				}
			}
		} catch (it.doqui.acta.acaris.repositoryservice.AcarisException e) {
			logger.error("Error in repositoryServicePort.getRepositories()"+e.getMessage(),e);
			return null;
		}
		if(repositoryFound){
			logger.info("initial repositoryId found: "+repositoryIdOutput);
		
		}else{
			logger.error("initial repositoryId not found!");
			return null;
		}
		return repositoryIdOutput;
	}
	
	private PagingResponseType getObjectIDActa(
			String idRepository, PrincipalIdType principalIdType, 
			String target, String name, String enteSettore, Long idUtente) {
		
		logger.info("Chiamo backOfficeServicePort.query.");
		
		ObjectIdType objectIdType = new ObjectIdType();
		objectIdType.setValue(idRepository);
		
		QueryableObjectType queryableObjectType = new QueryableObjectType();
		queryableObjectType.setObject(target);
		
		PropertyFilterType propertyFilterType = new PropertyFilterType();
		propertyFilterType.setFilterType(EnumPropertyFilter.ALL);
	
		QueryConditionType queryConditionTypeUserCollocation[] = new QueryConditionType[2];
		queryConditionTypeUserCollocation[0] = new QueryConditionType();
		queryConditionTypeUserCollocation[0].setOperator(EnumQueryOperator.EQUALS); 
		queryConditionTypeUserCollocation[0].setPropertyName(name);
		queryConditionTypeUserCollocation[0].setValue(enteSettore); 
		queryConditionTypeUserCollocation[1] = new QueryConditionType();
		queryConditionTypeUserCollocation[1].setOperator(EnumQueryOperator.EQUALS);
		queryConditionTypeUserCollocation[1].setPropertyName("flagResponsabile");
		queryConditionTypeUserCollocation[1].setValue("S"); 
		
        logger.shallowDump(objectIdType,"info");
        logger.shallowDump(principalIdType,"info");
		logger.shallowDump(queryableObjectType,"info");
		logger.shallowDump(propertyFilterType,"info");
		logger.shallowDump(queryConditionTypeUserCollocation[0],"info");
		logger.shallowDump(queryConditionTypeUserCollocation[1],"info");
		
		PagingResponseType query = null;
		try {
			query = backOfficeServicePort.query(objectIdType, principalIdType,
						queryableObjectType, propertyFilterType, queryConditionTypeUserCollocation,
						navigationConditionInfoType, maxItems, skipCount);		
		} catch (it.doqui.acta.acaris.backofficeservice.AcarisException e) {
			String msgErrore = "Errore sollevato da backOfficeServicePort.query(): "+e.getMessage();
			logger.error(msgErrore);
			this.inserisciLogProtocollazione(msgErrore, METODO_VERIFICA_LOG_PROTOCOLLAZIONE, "N", idUtente);
			return null;
		}
		return query;
	}
	
	private PagingResponseType getObjectID(
			String idRepository, PrincipalIdType principalIdType, 
			String target, String propertyName, String propertyValue, Long idUtente) {
		
		logger.info("Chiamo backOfficeServicePort.query.");
		
		ObjectIdType objectIdType = new ObjectIdType();
		objectIdType.setValue(idRepository);
		
		QueryableObjectType queryableObjectType = new QueryableObjectType();
		queryableObjectType.setObject(target);
		
		PropertyFilterType propertyFilterType = new PropertyFilterType();
		propertyFilterType.setFilterType(EnumPropertyFilter.ALL);
		
        QueryConditionType c1 = new QueryConditionType();
        c1.setOperator(EnumQueryOperator.EQUALS);
        c1.setPropertyName(propertyName);
        c1.setValue(propertyValue);
        
        QueryConditionType[] queryConditionTypes = new QueryConditionType[1];
        queryConditionTypes[0] = c1; 		
		
        logger.shallowDump(objectIdType,"info");
        logger.shallowDump(principalIdType,"info");
		logger.shallowDump(queryableObjectType,"info");
		logger.shallowDump(propertyFilterType,"info");
		logger.shallowDump(queryConditionTypes[0],"info");
		
		PagingResponseType query = null;
		try {
			query = objectServicePort.query(objectIdType, principalIdType,
						queryableObjectType, propertyFilterType, queryConditionTypes,
						navigationConditionInfoType, maxItems, skipCount);		
		} catch (it.doqui.acta.acaris.objectservice.AcarisException e) {
			String msgErrore = "Errore sollevato da backOfficeServicePort.query(): "+e.getMessage();
			logger.error(msgErrore);
			this.inserisciLogProtocollazione(msgErrore, METODO_VERIFICA_LOG_PROTOCOLLAZIONE, "N", idUtente);
			return null;
		}
		return query;
	}
	
	private PrincipalIdType getPrincipalIdType(String idRepository,String actaUtenteCodfiscale,IdAOOType idAOOType,
			IdStrutturaType idStrutturaType,IdNodoType idNodoType,String actaUtenteAppkey,Long idUtente) {
		
		logger.info("Chiamo backOfficeServicePort.getPrincipalExt");
		
		ObjectIdType objectIdType = new ObjectIdType();
		objectIdType.setValue(idRepository);
		
		CodiceFiscaleType codiceFiscaleType = new CodiceFiscaleType();
		codiceFiscaleType.setValue(actaUtenteCodfiscale);				
		
		ClientApplicationInfo clientApplicationInfo = new ClientApplicationInfo();
		clientApplicationInfo.setAppKey(actaUtenteAppkey);
		
		logger.shallowDump(objectIdType,"info");
	    logger.shallowDump(codiceFiscaleType,"info");
		logger.shallowDump(idAOOType,"info");
		logger.shallowDump(idStrutturaType,"info");
		logger.shallowDump(idNodoType,"info");
		logger.shallowDump(clientApplicationInfo,"info");
		
		PrincipalIdType principalIdType = null;
		String msgErrore = null;
		try {			
			PrincipalExtResponseType[] principalExt = backOfficeServicePort
					.getPrincipalExt(objectIdType,codiceFiscaleType,idAOOType,idStrutturaType,idNodoType,clientApplicationInfo);
			if (!ObjectUtil.isEmpty(principalExt)) {
				principalIdType = principalExt[0].getPrincipalId();
				logger.info("Trovato principalIdType = " + principalIdType.getValue());
			} else {				
				msgErrore = "Nessun principalIdType restituito da backOfficeServicePort.getPrincipalExt().";				
			}
		} catch (AcarisException e) {
			msgErrore = "Errore sollevato da backOfficeServicePort.getPrincipalExt(): "+e.getMessage();
		}
		
		// Gestisco caso di errore.
		if (!StringUtil.isEmpty(msgErrore)) {
			logger.error(msgErrore);
			this.inserisciLogProtocollazione(msgErrore, METODO_VERIFICA_LOG_PROTOCOLLAZIONE, "N", idUtente);
			return null;
		}
				
		return principalIdType;
	}
	
	private String leggiCostanteActa(String attributo, Long idUtente) {
		String valore = null;
		try {
			PbandiCCostantiVO c = new PbandiCCostantiVO();
			c.setAttributo(attributo);
			c = genericDAO.findSingleWhere(c);
			valore = c.getValore();
			logger.info("Valore della costante "+attributo+" = "+valore);
		} catch (Exception e) {
			valore = null;
			this.inserisciLogProtocollazione("ATTENZIONE! Non è stato possibile completare l'operazione", METODO_VERIFICA_LOG_PROTOCOLLAZIONE, "N", idUtente);
		}  		
		return valore;
	}
	
	private void inserisciLogProtocollazione(String msg, String metodo, String flagEsito, Long idUtente) {
		PbandiTLogProtocollazioneVO l = new PbandiTLogProtocollazioneVO();
		l.setMessaggio(msg);
		l.setMetodo(metodo);
		l.setFlagEsito(flagEsito);
		l.setIdUtente(new BigDecimal(idUtente));
		java.util.Date d = new java.util.Date();
		java.sql.Date oggi = new java.sql.Date(d.getTime());
		l.setDtLog(oggi);
		logger.info("Inserisco il seguente record in PBANDI_T_LOG_PROTOCOLLAZIONE: "+l.toString());
		try {
			genericDAO.insert(l);
		} catch (Exception e1) {
			logger.error("Errore nella insert in PBANDI_T_LOG_PROTOCOLLAZIONE.");
		}
	}
}
