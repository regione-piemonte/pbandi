/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.servizi;

import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbgestfinbo.base.TestBaseService;
import it.csi.pbandi.pbgestfinbo.business.manager.ActaManager;
import it.csi.pbandi.pbgestfinbo.dto.DatiXActaDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.IterAutorizzativiDAOImpl;
import it.csi.pbandi.pbgestfinbo.util.AcarisUtils;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
import it.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.doqui.acta.acaris.backofficeservice.BackOfficeServicePort;
import it.doqui.acta.acaris.objectservice.ObjectService;
import it.doqui.acta.acaris.objectservice.ObjectServicePort;
import it.doqui.acta.acaris.repositoryservice.AcarisException;
import it.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.IdFormaDocumentariaType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.PrincipalExtResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.CodiceFiscaleType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdAOOType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdNodoType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdStrutturaType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.csi.pbandi.pbgestfinbo.base.PbgestfinboJunitClassRunner;

@RunWith(PbgestfinboJunitClassRunner.class)
public class InvocazioneServiziActaTest extends TestBaseService {
	
	protected static Logger LOG = Logger.getLogger(InvocazioneServiziActaTest.class);
	
	private static final String CONSUMER_KEY = "eccM1dwlfgBLae05KFnPFzf0lV4a";
	private static final String CONSUMER_SECRET = "OgJacFR0CV7MiDTZMBOEAYPQXrQa";
	private static final String OAUTH_URL = "http://tst-<VH_API_MANAGER>/api/token";
	String endpointObjectService = "http://tst-<VH_API_MANAGER>/api/DOC_ACTA_object-T/1.0";
	String endpointRepositoryService = "http://tst-<VH_API_MANAGER>/api/DOC_ACTA_repository-T/1.0";
	String endpointBackOfficeService = "http://tst-<VH_API_MANAGER>/api/DOC_ACTA_backoffice-T/1.0";
	
	// trippletta letta dal db
	private long idAoo;
	private long idStruttura;
	private long idNodo;
	
	private String codiceFiscale = null ;
	private String actaUtenteAppkey = null;
	private String actaRepositoryName = null;
	private ObjectIdType repositoryId;
	private PrincipalIdType userPrincipalId = null;
	
	@Autowired
	ActaManager actaManager;
	
	@Autowired
	IterAutorizzativiDAOImpl iterAutorizzativiDAOImpl;
	
	private ObjectServicePort objectServicePort;
	private RepositoryServicePort repositoryServicePort;
	private BackOfficeServicePort backofficeServicePort;
	
	@Before()
    public void before() {
		String prf = "[InvocazioneServiziActaTest::before]";
    	LOG.info(prf + " BEGIN");
    	try {
    		
    		actaRepositoryName = "FINPIEMONTETEST FINPIEMONTE";
    		codiceFiscale = "FNDRGP16A01L219V";
    		actaUtenteAppkey = "47/-68/-90/54/101/-62/108/86/-86/49/-31/110/-98/-87/9/-12";
    		LOG.info(prf + " actaRepositoryName="+actaRepositoryName);
    		LOG.info(prf + " codiceFiscale="+codiceFiscale);
    		LOG.info(prf + " actaUtenteAppkey="+actaUtenteAppkey);
    		
    		idAoo = 301L;
    		idStruttura = 1178L;
    		idNodo = 1364L;
    		LOG.info(prf + " idAoo="+idAoo);
    		LOG.info(prf + " idStruttura="+idStruttura);
    		LOG.info(prf + " idNodo="+idNodo);
    		
    		inizializzaRepositoryService();
			inizializzaObjectService();
			inizializzaBackOfficeService();
			PrincipalExtResponseType principalMaster = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale,
					idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
			
			LOG.info(prf + " principalMaster="+principalMaster);
			
			if(principalMaster!=null ) {
				userPrincipalId = principalMaster.getPrincipalId();
			}
			LOG.info(prf + " userPrincipalId="+userPrincipalId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			LOG.info(prf + " END");
		}
    }

    @After
    public void after() {
    	LOG.info("[InvocazioneServiziActaTest::after] BEGIN-END");
    }

    
    @Test
    public void recuperoFormaDocumentariaTest() {
		String prf = "[InvocazioneServiziActaTest::recuperoFormaDocumentariaTest]";
		LOG.info(prf + "BEGIN \n\n\n");
		
		LOG.info(prf + " objectServicePort="+objectServicePort);
	    IdFormaDocumentariaType idFormaDoc = null;
		try {
			String DESCRIZIONE_FORMA_DOCUMENTARIA = "Controlli";
			idFormaDoc = AcarisUtils.queryForFormaDocumentaria(DESCRIZIONE_FORMA_DOCUMENTARIA, 
					objectServicePort, getRepositoryId(), userPrincipalId );
			
			LOG.info(prf + " idFormaDoc="+idFormaDoc.getValue());
			
		} catch (Exception e) {
			LOG.error("Error = "+e.getMessage());
		}
    }
    
	@Test
    public void popolaDatiXActaTest() {
		String prf = "[InvocazioneServiziActaTest::popolaDatiXActaTest]";
		LOG.info(prf + "BEGIN \n\n\n");
		
		Long idWorkFlow = 381L;
		Integer idTipoDocIndex = 32;
		UserInfoSec userInfoSec = new UserInfoSec();
		userInfoSec.setCodFisc("PCHMRC72A05H355F");
		userInfoSec.setNome("Marco");
		userInfoSec.setCognome("Pochettino");
		
		BeneficiarioSec beneficiario = new BeneficiarioSec();
		beneficiario.setCodiceFiscale("PRMLVI33A07H456P");
		beneficiario.setDenominazione("Primo LEvi");
		beneficiario.setIdBeneficiario(999L);
		userInfoSec.setBeneficiarioSelezionato(beneficiario);

		DatiXActaDTO dati = null;
		try {
			dati = iterAutorizzativiDAOImpl.popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LOG.info(prf + " dati="+dati);
			LOG.info(prf + " END \n\n\n");
		}
	}
    
    @Test
    public void creaDocumentoTest() {
    	String prf = "[InvocazioneServiziActaTest::creaDocumentoTest] ";
    	LOG.info( "\n\n\n" + prf + " BEGIN");
    	
    	try {
    		
    		Long idWorkFlow = 273L; //381L;
    		Integer idTipoDocIndex = 32;
    		UserInfoSec userInfoSec = new UserInfoSec();
    		userInfoSec.setCodFisc("PCHMRC72A05H355F");
    		userInfoSec.setNome("Marco");
    		userInfoSec.setCognome("Pochettino");
    		userInfoSec.setIdUtente(33L);
    		BeneficiarioSec beneficiario = new BeneficiarioSec();
    		beneficiario.setCodiceFiscale("PRMLVI33A07H456P");
    		beneficiario.setDenominazione("Primo LEvi");
    		beneficiario.setIdBeneficiario(999L);
    		userInfoSec.setBeneficiarioSelezionato(beneficiario);

    		DatiXActaDTO datiActa = null;
    		try {
    			datiActa = iterAutorizzativiDAOImpl.popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
    			
    			String codClassificazione = actaManager.classificaDocumento(datiActa);
				LOG.info(prf + "codClassificazione= " + codClassificazione);
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			LOG.info(prf + " datiActa="+datiActa);
    			LOG.info(prf + " END \n\n\n");
    		}
    		
    		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LOG.info(prf + " END \n\n\n");
		}
 
    }
    
    
    @Test
    public void creaActaDirTest() {
    	String prf = "[InvocazioneServiziActaTest::creaActaDirTest] ";
    	LOG.info( "\n\n\n" + prf + " BEGIN");
    	
    	try {
    		
    		
//			actaManager.creaActaDirTest();
			LOG.info(prf + "ok");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LOG.info(prf + " END \n\n\n");
		}
 
    }
    
    private void inizializzaObjectService() throws Exception {
		String prf = "[InvocazioneServiziActaTest::inizializzaObjectService] ";
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
//			Map<String, Object> map = bp.getRequestContext();
//			String endpointObjectService = RESOURCE_BUNDLE.getString("acta.endpointObjectService");
//			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endpointObjectService);
			
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
    
    private void inizializzaRepositoryService() throws Exception {
		String prf = "[InvocazioneServiziActaTest::inizializzaRepositoryService] ";
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
    
    private void inizializzaBackOfficeService() throws Exception {

		String prf = "[InvocazioneServiziActaTest::inizializzaBackOfficeService] ";
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
    private ObjectIdType getRepositoryId() throws Exception {
		if(repositoryId==null)
			repositoryId = getRepositoryId(actaRepositoryName, repositoryServicePort);
		return repositoryId;
    }
    private ObjectIdType getRepositoryId(String actaRepositoryName, RepositoryServicePort repositoryServicePort) throws Exception  {
		String prf = "[InvocazioneServiziActaTest::getRepositoryId] ";
		LOG.info(prf + " BEGIN");
		
		ObjectIdType repositoryId = null;
		try {
			AcarisRepositoryEntryType[] reps = repositoryServicePort.getRepositories();

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
		
		} catch (AcarisException e) {
			LOG.error(prf + " Error in repositoryServicePort.getRepositories()"+e.getMessage(),e);
			e.printStackTrace();
			throw new Exception("ERROR_ACTA_GENERIC");
		}finally {
			LOG.info(prf + " END"); 
		}
		return repositoryId;
	}
    
    private PrincipalExtResponseType getPrincipalExt(String idRepository, String codiceFiscale, long idAoo, long idStruttura,
			long idNodo, String actaUtenteAppkey, BackOfficeServicePort backofficeServicePort) throws Exception  {
		String prf = "[InvocazioneServiziActaTest::getPrincipalExt] ";
		LOG.debug(prf + " BEGIN ");
		
		LOG.debug(prf + " idAoo= "+idAoo);
		LOG.debug(prf + " idStruttura= "+idStruttura);
		LOG.debug(prf + " idNodo= "+idNodo);
		LOG.debug(prf + " actaUtenteAppkey= "+actaUtenteAppkey);
		LOG.debug(prf + " codiceFiscale= "+codiceFiscale);
		LOG.debug(prf + " idRepository= "+idRepository);
		
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
		try {
			
			arrPrincipalExt = backofficeServicePort.getPrincipalExt(objectIdType, codFiscale, _idAOO, _idStruttura, _idNodo, clientApplicationInfo);
			if (arrPrincipalExt!=null) {
				LOG.debug(prf + " >>>>>>> arrPrincipalExt lenght= "+arrPrincipalExt.length);
				LOG.debug(prf + " >>>>>>> Time = "+ start);
			}
			
		} catch (Exception e) {
			LOG.error(prf + " e.getMessage(): " + e.getMessage());		
			e.printStackTrace();
			throw new Exception("ERROR_ACTA_PRINCIPAL");
		} finally {
			long end = System.currentTimeMillis();
			LOG.debug(prf + " END, durata " + (end-start)/1000);
		}
		return arrPrincipalExt[0];
	}
    
}
