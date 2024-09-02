/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.servizi;

import it.csi.pbandi.pbgestfinbo.base.PbgestfinboJunitClassRunner;
import it.csi.pbandi.pbgestfinbo.base.TestBaseService;
import it.csi.pbandi.pbgestfinbo.integration.dao.CreazioneFascicoliFPDao;
import it.csi.pbandi.pbgestfinbo.integration.vo.MetadatiActaVO;
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
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumFolderObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.FascicoloRealeLiberoPropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.IdFascicoloStandardType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.PrincipalExtResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.*;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.BindingProvider;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(PbgestfinboJunitClassRunner.class)
public class CreazioneFascicoliFPTest extends TestBaseService {
	
	protected static Logger LOG = Logger.getLogger(CreazioneFascicoliFPTest.class);
	
	// acta input params
	static ObjectIdType initialRepositoryObjectIdType= new ObjectIdType(); 
	static CodiceFiscaleType initialCodiceFiscaleType=new CodiceFiscaleType();
	static ClientApplicationInfo clientApplicationInfo= new ClientApplicationInfo();
	static NavigationConditionInfoType navigationConditionInfoType = new NavigationConditionInfoType(); 
	static ObjectIdType repositoryId = new ObjectIdType();
	
	private static RepositoryServicePort repositoryServicePort;
	private static BackOfficeServicePort backofficeServicePort;
	private static ObjectServicePort objectServicePort;
	
	private static String CONSUMER_KEY;
	private static String CONSUMER_SECRET;
	private static String OAUTH_URL;
	private static String endpointBackOfficeService;
	private static String endpointRepositoryService;
	private static String endpointObjectService;
	
	private static Long IdAoo;
	private static Long IdStruttura;
	private static Long IdNodo;
	private static String actaRepositoryName;
	private static String UtenteCodfiscale;
	private static String AppKey;
	
	private static Long ActaVitalRecordCodeTypeAlto;
	private static String PAROLA_CHIAVE_FINDOM;
	
	private static Integer maxItems = null;
	private static Integer skipCount = 0;
	
	private static final String SERIE_FASCICOLI_PROPERTIES_TYPE = "SerieFascicoliPropertiesType";
	private static final String DOSSIER_PROPERTIES_TYPE ="DossierPropertiesType";
	
	private static List<MetadatiActaVO> metadatiActa;
	private static HashMap<Integer, String> INFO_BANDO = new HashMap<>();

	private static IdFascicoloStandardType ID_FASCICOLO_STD = null;

	private static PrincipalIdType userIdPrincipalType = null;

	@Autowired
	CreazioneFascicoliFPDao creazioneFascicoliFPDao;

	/**
	 * Pochettino
	 * 
	 *  Classe di test che genera l'alberatura su ACTA Finpiemonte di TEST
	 *
	 *  Deve esistere l'alberatura sino al BANDO:
	 *  
	 *  FINPIEMONTE > Titolario Finpiemonte > 105 MISURE DI AGECOLAZIONE > 10 FONDI DI AGEVOLAZIONE 
	 *
     *	> bando xxx    (il bando - Serie di dossier )
	 *		> Documenti generali del fondo		(dossier - da creare fisso)
	 *		> Domande di agevolazione			(dossier - da creare fisso - assegno parola chiave)
	 *
	 *
	 * Il metodo "creaFascicoliFPTest" crea l'alberatura seguente dentro il dossier "Domande di agevolazione"
	 * 
	 *	> Num Domanda					(Fascicolo - da creare con la domanda - assegnare parola chiave)
	 *		> Domanda e allegati		(SottoFascicolo  - da creare in automatico alla creazione della domanda)
	 *		> Procedimento concessione	(SottoFascicolo  - da creare in automatico alla creazione della domanda) 
	 *		> ..
	 * 		> Legale 
	 *  
	 * 
	 * 
	 * 
	 */
	
	
	@Before()
    public void before() {
    	LOG.info("[CreazioneFascicoliFPTest::before] BEGIN");
    	
    	/////////////////////////////////////////////////////////////////
    	// TODO valori dipendenti dall' ACTA da utilizzare
    	CONSUMER_KEY = <CONSUMER_KEY>;
    	CONSUMER_SECRET = <CONSUMER_SECRET>;
    	OAUTH_URL = "http://tst-<VH_API_MANAGER>/api/token";
    	endpointBackOfficeService = "http://tst-<VH_API_MANAGER>/api/DOC_ACTA_backoffice-T/1.0";
    	endpointRepositoryService = "http://tst-<VH_API_MANAGER>/api/DOC_ACTA_repository-T/1.0";
    	endpointObjectService = "http://tst-<VH_API_MANAGER>/api/DOC_ACTA_object-T/1.0";
    	
    	IdAoo = 301L;
    	IdStruttura = 1178L;
    	IdNodo = 1364L;
    	actaRepositoryName = "FINPIEMONTETEST FINPIEMONTE";
    	UtenteCodfiscale = "FNDRGP16A01L219V";
    	AppKey = "47/-68/-90/54/101/-62/108/86/-86/49/-31/110/-98/-87/9/-12";
    	
    	ActaVitalRecordCodeTypeAlto = 735L;

    	/////////////////////////////////////////////////////////////////
    	PAROLA_CHIAVE_FINDOM = "FD"; //FD+numero domanda se arriva da mondo FINDOM, PB+numero progetto se arriva da scheda progetto

		//TODO bandi da configurare su doqui
		INFO_BANDO.put(1637, "FINDOMLR3404-SOSTINV-LINEAAARIGIANATO");

		initialCodiceFiscaleType.setValue(UtenteCodfiscale);
    	clientApplicationInfo.setAppKey(AppKey);
    	
    	try {
    		
    		inizializzaBackOfficeService();
			LOG.info("[CreazioneFascicoliFPTest::before] backofficeServicePort inizializzato");
			
			inizializzaRepositoryService();
			LOG.info("[CreazioneFascicoliFPTest::before] repositoryServicePort inizializzato");
			
			inizializzaObjectService();
			LOG.info("[CreazioneFascicoliFPTest::before] objectServicePort inizializzato");
			
			repositoryId = getRepositoryId(actaRepositoryName, repositoryServicePort);

			LOG.info("[CreazioneFascicoliFPTest::before] repositoryId="+repositoryId);
			LOG.info("[CreazioneFascicoliFPTest::before] repositoryId.value="+repositoryId.getValue());
			
			initialRepositoryObjectIdType.setValue(repositoryId.getValue());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOG.info("[CreazioneFascicoliFPTest::before] END");
    }

    @After
    public void after() {
    	LOG.info("[CreazioneFascicoliFPTest::after] BEGIN-END");
    }
	
    
    @Test
    public void creaFascicoliFPTest() {
		String prf = "[CreazioneFascicoliFPTest::creaFascicoliFPTest] ";
		LOG.info(prf + "BEGIN");

		for(Map.Entry<Integer, String> infoBando : INFO_BANDO.entrySet()) {

			metadatiActa = creazioneFascicoliFPDao.getMetadatiActaList(infoBando.getKey());

			// uso la logica scritta da Durando in FINDOM/findomprtfp/ProtocolManager -> classificaDocumenti

			//Collocazione specifica dello user legato al progetto
			IdAOOType userIdAOOType = new IdAOOType();
			userIdAOOType.setValue(IdAoo); //

			IdStrutturaType userIdStrutturaType = new IdStrutturaType();
			userIdStrutturaType.setValue(IdStruttura); //

			IdNodoType userIdNodoType = new IdNodoType();
			userIdNodoType.setValue(IdNodo); //

			userIdPrincipalType = getUserPrincipalId(userIdAOOType, userIdStrutturaType, userIdNodoType);
			LOG.info(prf + "userPrincipalId=" + userIdPrincipalType.getValue());

			try {
				PropertyFilterType propertyFilterType = new PropertyFilterType();
				propertyFilterType.setFilterType(EnumPropertyFilter.ALL); //costante

				QueryableObjectType target = new QueryableObjectType();
				target.setObject(SERIE_FASCICOLI_PROPERTIES_TYPE);

				ObjectIdType bandoObjectID = getObjectID(infoBando.getValue(), propertyFilterType, maxItems, skipCount, userIdPrincipalType, target, "paroleChiave", 2);

				if (bandoObjectID == null) {
					//Cerco la root del bando con target DOSSIER_PROPERTIES_TYPE
					LOG.error(prf + "Non esiste ancora il fascicolo del bando per QueryableObjectType target: " + SERIE_FASCICOLI_PROPERTIES_TYPE);
					target.setObject(DOSSIER_PROPERTIES_TYPE);
					bandoObjectID = getObjectID(infoBando.getValue(), propertyFilterType, maxItems, skipCount, userIdPrincipalType, target, "paroleChiave", 1);
				}

				if (bandoObjectID == null) {
					LOG.info(prf + "Struttura aggregativa padre non trovata ne per fascicolo ne per dossier");
					// i regionali devono creare il fascicolo del bando e la parola chiave

				} else {
					for (MetadatiActaVO metadatiActaVO : metadatiActa) {
						setIdFascicoloStd(userIdPrincipalType);
						ObjectIdType objectIdFolderDomanda = creaFascicoloPerDomanda(userIdPrincipalType, bandoObjectID, userIdAOOType, userIdStrutturaType,
								userIdNodoType, metadatiActaVO);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		LOG.info(prf + "END");
    }

    private static ObjectIdType creaFascicoloPerDomanda(
			PrincipalIdType userPrincipalId, 
			ObjectIdType bandoObjectID,
			IdAOOType userIdAOOType,
			IdStrutturaType userIdStrutturaType,
			IdNodoType userIdNodoType,
			MetadatiActaVO metadatiActa) {
		
    	String prf = "[CreazioneFascicoliFPTest::creaFascicoloPerDomanda] ";
		LOG.info(prf + "BEGIN");
		
		ObjectIdType folderProgettoId = null;
		LOG.info(prf + " metadatiActa = " + metadatiActa);
		
		long start=System.currentTimeMillis();
		try {
	         // crea il fascicolo
	          FascicoloRealeLiberoPropertiesType properties = new FascicoloRealeLiberoPropertiesType();
	          properties.setConservazioneCorrente(5); // N.B. deve essere compreso  tra 0 e 99 e <= cons gen
	          
	          properties.setConservazioneGenerale(metadatiActa.getConservazioneGeneraleFascicolo()); // N.B. deve essere compreso  tra 1 e 99 e >= cons cor
	            
	          IdVitalRecordCodeType idVrc = new IdVitalRecordCodeType(); // codice di vitalita del fascicolo;
	          idVrc.setValue(ActaVitalRecordCodeTypeAlto);
	          properties.setIdVitalRecordCode(idVrc); // obbligatorio
	       
	          properties.setOggetto(metadatiActa.getOggettoFascicolo());
	          properties.setSoggetto(metadatiActa.getSoggettoFascicolo());  
	          
	          properties.setParoleChiave(PAROLA_CHIAVE_FINDOM + metadatiActa.getIdDomanda());
	          properties.setIdAOORespMat(userIdAOOType);
	          properties.setIdStrutturaRespMat(userIdStrutturaType);
	          properties.setIdNodoRespMat(userIdNodoType);
	          properties.setDatiPersonali(true);
	          properties.setDatiRiservati(false);
	          properties.setDatiSensibili(false);
	          properties.setArchivioCorrente(true);

	          properties.setIdFascicoloStdRiferimento(ID_FASCICOLO_STD);
	          
	          properties.setNumero(metadatiActa.getIdDomanda()); //queryForCodiceStd("fs100", userPrincipalId));

			  try {
				  //EnumFolderObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE,
				  folderProgettoId = objectServicePort.createFolder(initialRepositoryObjectIdType,
						  EnumFolderObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE,
						  userPrincipalId,
						  properties,
						  bandoObjectID);
			  }catch (Exception e){
				  folderProgettoId = objectServicePort.createFolder(initialRepositoryObjectIdType,
						  EnumFolderObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE,
						  userPrincipalId,
						  properties,
						  bandoObjectID);
			  }

			LOG.info(prf + " objectServicePort.createFolder executed in ms: "+(System.currentTimeMillis()-start));

			if(folderProgettoId != null){
				  LOG.info(prf + " OK folder progetto creato su ACTA : "+folderProgettoId.getValue());
			}
		} catch (it.doqui.acta.acaris.objectservice.AcarisException e) {
			if(e.getFaultInfo().getErrorCode().equals(EnumErrorCodeType.SERGEN_E_001)){ //userIdPrincipalType scaduto
				//rigenero l'userIdPrincipalType
				userIdPrincipalType = getUserPrincipalId(userIdAOOType, userIdStrutturaType, userIdNodoType);
				LOG.info(prf + "userPrincipalId="+ userIdPrincipalType.getValue());
				LOG.info(prf + " END, createFolderForProgetto executed in ms: "+(System.currentTimeMillis()-start));
				return creaFascicoloPerDomanda(userIdPrincipalType, bandoObjectID, userIdAOOType, userIdStrutturaType, userIdNodoType, metadatiActa);
			}
			if(!e.getFaultInfo().getErrorCode().equals(EnumErrorCodeType.SER_E_110)) { //fascicolo già presente
				LOG.info(e.getFaultInfo().getErrorCode());
				LOG.error(prf + " acEx.getMessage(): " + e.getMessage() +
						"\nacEx.getFaultInfo().getErrorCode(): " + e.getFaultInfo().getErrorCode() +
						"\nacEx.getFaultInfo().getPropertyName(): " + e.getFaultInfo().getPropertyName() +
						"\nacEx.getFaultInfo().getObjectId(): " + e.getFaultInfo().getObjectId() +
						"\nacEx.getFaultInfo().getExceptionType(): " + e.getFaultInfo().getExceptionType() +
						"\nacEx.getFaultInfo().getClassName(): " + e.getFaultInfo().getClassName() +
						"\nErrore objectServicePort.createFolder: " + e.getMessage() + "\n\n", e);
			}else {
				LOG.info(prf + " OK folder progetto non creato su ACTA perchè già presente");
			}
		}finally{
			LOG.info(prf + " END, createFolderForProgetto executed in ms: "+(System.currentTimeMillis()-start));
		}
		return folderProgettoId;
	}

	private static void setIdFascicoloStd(PrincipalIdType userPrincipalId) {
		String prf = "[CreazioneFascicoliFPTest::setIdFascicoloStd] ";
		LOG.info(prf + "BEGIN");

		try {
			long idFascicoloStd = queryForFascicoloStd("fs100", userPrincipalId);
			LOG.info(prf + "idFascicoloStd = " + idFascicoloStd);
			ID_FASCICOLO_STD = new IdFascicoloStandardType();
			ID_FASCICOLO_STD.setValue(idFascicoloStd);
		}catch (Exception e){
			LOG.error("Exception while trying to setIdFascicoloStd", e);
		}finally {
			LOG.info(prf + "END");
		}
	}

	private static long queryForFascicoloStd(String codiceFascicoloStd, PrincipalIdType userPrincipalId) throws Exception {
    	String prf = "[CreazioneFascicoliFPTest::queryForFascicoloStd] ";
		LOG.info(prf + " BEGIN");
		
	    QueryableObjectType target = new QueryableObjectType();
	    target.setObject(it.doqui.acta.actasrv.dto.acaris.type.common.EnumObjectType.FASCICOLO_STD_PROPERTIES_TYPE.value());

	    PropertyFilterType filter = new PropertyFilterType();
	    filter.setFilterType(EnumPropertyFilter.ALL);

	    List<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
	    QueryConditionType qct = new QueryConditionType();
	    qct.setPropertyName("codice");
	    qct.setOperator(EnumQueryOperator.EQUALS);
	    qct.setValue(codiceFascicoloStd); // valore codice da ricercare, ad esempio "DOCGEN"
	    criteria.add(qct);

	    qct = new QueryConditionType();
	    qct.setPropertyName("idStato");
	    qct.setOperator(EnumQueryOperator.EQUALS);
	    qct.setValue("3"); // fascicolo standard attivo
	    criteria.add(qct);

	    long fstdKeyValue = 0;
	    try {
	    	 
	        PagingResponseType result = objectServicePort.query(initialRepositoryObjectIdType, userPrincipalId,  target, filter,
	            criteria.toArray(new QueryConditionType[0]), null, null, new Integer(0));

	        LOG.info(prf + " result="+result);
	        
	        if (result != null) {
	            boolean trovato = false;
	            for (ObjectResponseType obj : result.getObjects()) {
	                if (obj != null && obj.getPropertiesLength() > 0) {
	                    for (PropertyType prop : obj.getProperties()) {
	                        if ("dbKey".equals(prop.getQueryName().getPropertyName()) && prop.getValue() != null
	                                && prop.getValue().getContentLength() > 0) {
	                            fstdKeyValue = Long.parseLong(prop.getValue().getContent(0));
	                            trovato = true;
	                            break;
	                        }
	                    }
	                }
	                if (trovato)
	                    break;
	            }
	        }
	        
	        LOG.info(prf + " fstdKeyValue="+fstdKeyValue);

	    } catch (it.doqui.acta.acaris.objectservice.AcarisException acEx) {
	       LOG.info(prf + "errore nella ricerca del fascicolo standard");
	       LOG.info(prf + "acEx.getMessage(): " + acEx.getMessage());
	       LOG.info(prf + "acEx.getFaultInfo().getErrorCode(): " + acEx.getFaultInfo().getErrorCode());
	       LOG.info(prf + "acEx.getFaultInfo().getPropertyName(): " + acEx.getFaultInfo().getPropertyName());
	       LOG.info(prf + "acEx.getFaultInfo().getObjectId(): " + acEx.getFaultInfo().getObjectId());
	       LOG.info(prf + "acEx.getFaultInfo().getExceptionType(): " + acEx.getFaultInfo().getExceptionType());
	       LOG.info(prf + "acEx.getFaultInfo().getClassName(): " + acEx.getFaultInfo().getClassName());
	        throw acEx;
	    } catch (Exception ex) {
	       LOG.info(prf + "errore nella ricerca dello stato di efficacia");
	       LOG.info(prf + "ex.getMessage() " + ex.getMessage());
	        throw ex;
	    }

	    return fstdKeyValue;
	}

	// MEtodi ACTA
    
	private ObjectIdType getRepositoryId(String actaRepositoryName, RepositoryServicePort repositoryServicePort) {//throws Exception  {
		String prf = "[CreazioneFascicoliFPTest::getRepositoryId] ";
		LOG.info(prf + " BEGIN");
		
		ObjectIdType repositoryId = null;

		AcarisRepositoryEntryType[] reps = null;
		try {
			reps = repositoryServicePort.getRepositories();
			if(reps == null){
				LOG.error(prf + " repositoryServicePort.getRepositories() non ha resituito resultati");
				return null;
			}
			for (AcarisRepositoryEntryType acarisRepositoryEntryType : reps) {
				LOG.info(prf + " RepositoryName(): "+acarisRepositoryEntryType.getRepositoryName() );
				String repositoryName = acarisRepositoryEntryType.getRepositoryName();
				
				if(actaRepositoryName.equalsIgnoreCase(repositoryName)){
					repositoryId = acarisRepositoryEntryType.getRepositoryId();
					LOG.info(prf + "selezionato RepositoryId del repositoryName = ["+repositoryName+"]");
					break;
				}
			}
		} catch (it.doqui.acta.acaris.repositoryservice.AcarisException e) {
			e.printStackTrace();
		}finally {
			LOG.info(prf + " END"); 	
		}
		return repositoryId;
	}
   
	
    private static PrincipalIdType getUserPrincipalId( IdAOOType idAOOType,  IdStrutturaType idStrutturaType, IdNodoType idNodoType)
	{
    	LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] BEGIN");

		long start = System.currentTimeMillis();
		PrincipalIdType userPrincipalId = null;

		try
		{
			LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] clientApplicationInfo:" + clientApplicationInfo.getAppKey());
			LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] initialRepositoryObjectIdType:" + initialRepositoryObjectIdType.getValue());
			LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] initialCodiceFiscaleType:" + initialCodiceFiscaleType.getValue());
			LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] idAOOType:" +idAOOType.getValue());
			LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] idNodoType:" + idNodoType.getValue() );
			LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] idStrutturaType:" + idStrutturaType.getValue());

			PrincipalExtResponseType[] principalExt = backofficeServicePort.getPrincipalExt(initialRepositoryObjectIdType, initialCodiceFiscaleType, idAOOType, idStrutturaType, idNodoType, 
					clientApplicationInfo);

			if (principalExt != null && principalExt.length != 0)
			{
				userPrincipalId = principalExt[0].getPrincipalId();
				LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] found user principal: " + userPrincipalId.getValue());
			}
			else
			{
				String msg = "\nbackOfficeServicePort.getPrincipalExt : non ho trovato lo user principal, contattare supporto_acaris  \n\n";
				LOG.error("[CreazioneFascicoliFPTest::getUserPrincipalId] " + msg);
				return null;
			}
		}
		catch (AcarisException e)
		{
			String msg="(GET USER PRINCIPAL ID) Errore backOfficeServicePort.getPrincipalExt : "+ e.getMessage();
			LOG.error("[CreazioneFascicoliFPTest::getUserPrincipalId] KO : "+msg, e);
			return null;
		}
		finally
		{
			LOG.info("[CreazioneFascicoliFPTest::getUserPrincipalId] END, executed in ms: "+(System.currentTimeMillis()-start));
		}

		return userPrincipalId;
	}
    
    
    private static ObjectIdType getObjectID(String valueProp,
			PropertyFilterType propertyFilterType, Integer maxItems,
			Integer skipCount, PrincipalIdType userPrincipalId,
			QueryableObjectType target,String nomeProp, Integer stato ) {
    	String prf = "[CreazioneFascicoliFPTest::getObjectID] ";
		LOG.info(prf + " BEGIN");
		
		ObjectIdType objectId = null;
		long start=System.currentTimeMillis();
		try
		{
	        QueryConditionType c1 = new QueryConditionType();
	        c1.setOperator(EnumQueryOperator.EQUALS);
	        c1.setPropertyName(nomeProp);
	        c1.setValue(valueProp);

	        LOG.info(prf + "  (GET OBJECT BY nomeProp=[" + nomeProp + "], valueProp=[" + valueProp + "])");

	        //Le condizioni potrebbero essere 1 o 2, uso un arraylist
	        ArrayList<QueryConditionType> lista = new ArrayList<QueryConditionType>();
	        lista.add(c1);

	        /** PK : modifica in modo da ricercare le sole aggregazioni attive/aperte (a seconda del tipo di aggregazione)
	            propertyName = "stato", operator = EQUALS
				value
					2 in caso di serie (serie di fascicoli, serie di dossier, serie tipologiche di documenti)
					1 per tutte le altre tipologie di aggregazione (tra cui i fascicoli)
	         */
	        LOG.info(prf + " stato="+stato);
	        if (stato != null)
	        {
		        QueryConditionType c2 = new QueryConditionType();
		        c2.setOperator(EnumQueryOperator.EQUALS);
		        c2.setPropertyName("stato");
		        c2.setValue(stato+"");
		        LOG.info(prf + " aggiunta seconda QueryConditionType per stato="+stato);

		        lista.add(c2);
	        }

	        LOG.info(prf + " lista.size()="+lista.size());
	        QueryConditionType[] queryConditionTypes = new QueryConditionType[lista.size()];

	        int i = 0;
	        for (Object item : lista)
	        {
	        	queryConditionTypes[i] = (QueryConditionType)item;
	        	i++;
	        }
	        

			LOG.info(prf + " calling  objectServicePort.query by parola chiave to find  object id " );
			PagingResponseType query = objectServicePort.query(initialRepositoryObjectIdType, userPrincipalId, 
				target, propertyFilterType, queryConditionTypes,
				navigationConditionInfoType, maxItems,
				skipCount);

			if (query.getObjects() != null)
			{
				if (query.getObjects().length > 1)
				{
					LOG.warn(prf + " Attenzione! mi aspetto un solo elemento invece objectServicePort.query ne ha restituiti: "+query.getObjects());
				}
				for (ObjectResponseType objectResponseType : query.getObjects())
				{
					objectId = objectResponseType.getObjectId();
					LOG.info(prf + " objectId   : " + objectId);
				}
			}
		}
		catch (it.doqui.acta.acaris.objectservice.AcarisException e)
		{
			String msg="\n Errore objectServicePort.query : " + e.getMessage();
			LOG.error(prf + " " + msg, e);
		}
		catch (Exception e)
		{
			LOG.error(prf + " EXCEPTION " + e.getMessage(), e);
			e.printStackTrace();
		}
		finally
		{
			LOG.info(prf + " END, executed in ms: " + (System.currentTimeMillis() - start));
		}

		return objectId;
	}
    
    
    // metodi inizializzazione WS 
    private void inizializzaBackOfficeService() throws Exception {

		String prf = "[CreazioneFascicoliFPTest::inizializzaBackOfficeService] ";
		LOG.info(prf + " BEGIN");
		
		try {
			//TODO PK : riga qui sotto solo per TEST JUNIT, commentarla e scommentare la seguente
			URL url = new URL("http://tst-<VH_ESPOSIZIONE_SERVIZI>/actasrv/backofficeWS?wsdl");
			//URL url = this.getClass().getClassLoader().getResource("/wsdl/ACARISWS-BackOfficeService.wsdl");
			LOG.info(prf + ">><< url="+url);
			
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

			Object o = gwfb.create();
			backofficeServicePort = (BackOfficeServicePort) o;

			LOG.debug(prf + " backofficeServicePort= "+backofficeServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception("ERROR_ACTA_SERVICE");
		}finally {
			LOG.info(prf + " END");
		}
	}
    
    private void inizializzaRepositoryService() throws Exception {
		String prf = "[CreazioneFascicoliFPTest::inizializzaRepositoryService] ";
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
			
			Object o = gwfb.create();
			repositoryServicePort = (RepositoryServicePort) o;
			LOG.debug(prf + " repositoryServicePort= "+repositoryServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception("ERROR_ACTA_SERVICE");
		}finally {
			LOG.info(prf + " END");
		}
	}
    
    private void inizializzaObjectService() throws Exception {
		String prf = "[CreazioneFascicoliFPTest::inizializzaObjectService] ";
		LOG.info(prf + " BEGIN");
		
		try {
//			URL url = new URL("http://tst-<VH_ESPOSIZIONE_SERVIZI>/actasrv/objectWS?wsdl");
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-ObjectService.wsdl");
			LOG.info(prf + ">><< url="+url);
 
			ObjectService os = new  ObjectService(url);
			LOG.info(prf + " ok os");
			
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
			
			Object o = gwfb.create();
			objectServicePort = (ObjectServicePort) o;
			LOG.debug(prf + " objectServicePort= "+objectServicePort);
		}catch(Exception e) {
			LOG.error(prf + "Exception e="+e.getMessage());
			e.printStackTrace();
			throw new Exception("ERROR_ACTA_SERVICE");
		}finally {
			LOG.info(prf + " END");
		}
	}
}
