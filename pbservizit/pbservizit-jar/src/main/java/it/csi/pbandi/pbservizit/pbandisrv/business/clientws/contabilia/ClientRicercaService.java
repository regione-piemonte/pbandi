/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia;

import java.net.MalformedURLException;
import java.net.URL;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestione;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaCapitoloUscitaGestioneResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggetti;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDettaglioSoggettiResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaDocumentoSpesaResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioni;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaLiquidazioniResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesa;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaEstesaOrdinativiSpesaResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegno;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaImpegnoResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaService_PortType;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaService_ServiceLocator;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggetti;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaSinteticaSoggettiResponse;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
//import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
//import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
//import it.csi.wso2.apiman.oauth2.helper.WsProvider;
//import it.csi.wso2.apiman.oauth2.helper.extra.axis1.Axis1Impl;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.apache.axis.transport.http.HTTPConstants;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientRicercaService {

	public static final String LOGGER_PREFIX = "pbandisrv";
	
	@Autowired
	protected LoggerUtil logger;
		
	public LoggerUtil getLogger() {
		return logger;
	}
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}
	
	private static final URL url = RicercaService_ServiceLocator.class.getResource("/bilancio-cre--BILANCIO_contabilia_ricercaService1.0.wsdl");	
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("clientws");
	private static final String OAUTH_URL = RESOURCE_BUNDLE.getString("contabilia.oauthURL");
	private static final String CONSUMER_KEY = RESOURCE_BUNDLE.getString("contabilia.consumerKey");
	private static final String CONSUMER_SECRET = RESOURCE_BUNDLE.getString("contabilia.consumerSecret");
	private static final String ENDPOINT = RESOURCE_BUNDLE.getString("contabilia.ricercaService.endpoint");
	
	private static final QName SERVICE_NAME = new QName("http://siac.csi.it/ricerche/svc/1.0", "RicercaService");
	
//	private static RicercaService_PortType wrappedPort = null;
	private static RicercaService_PortType ricServPort = null;
	
	private void inizializza() {
	
		logger.debug("[ClientRicercaService::inizializza] BEGIN");
		logger.debug("[ClientRicercaService::inizializza] URL = " +url);
//		logger.debug("[ClientRicercaService::inizializza] OAUTH_URL = " +OAUTH_URL);
//		logger.debug("[ClientRicercaService::inizializza] URL = " +url.toString());
//		logger.debug("[ClientRicercaService::inizializza] OAUTH_URL = " +OAUTH_URL);
//		logger.debug("[ClientRicercaService::inizializza] CONSUMER_KEY = " +CONSUMER_KEY);
//		logger.debug("[ClientRicercaService::inizializza] CONSUMER_SECRET = " +CONSUMER_SECRET);
		logger.debug("[ClientRicercaService::inizializza] ENDPOINT = " +ENDPOINT);
		
		RicercaService_ServiceLocator ss = null;
		RicercaService_PortType port = null;
		try {
			ss = new RicercaService_ServiceLocator(url.toString(), SERVICE_NAME);
			port = ss.getRicercaServicePort();
			
		} catch (ServiceException e) {
			logger.error("[ClientRicercaService::inizializza] ERRORE ServiceException: "+e);
		}
				
		OauthHelper oauthHelper = new OauthHelper(OAUTH_URL,CONSUMER_KEY,CONSUMER_SECRET,10000); // time out in ms
		logger.debug("[ClientRicercaService::inizializza] oauthHelper= "+oauthHelper);
		
//		TokenRetryManager trm = new TokenRetryManager();
//		trm.setOauthHelper(oauthHelper);			
//		WsProvider wsp = new Axis1Impl();		
//		trm.setWsProvider(wsp);
//		if (wsp == null)
//			logger.debug("[ClientRicercaService::inizializza] ERRORE: WsProvider NON ISTANZIATO");
//		
//		GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
//		gwfb.setEndPoint(ENDPOINT);	
//		gwfb.setWrappedInterface(RicercaService_PortType.class);
//		gwfb.setPort(port);
//		gwfb.setTokenRetryManager(trm);

		it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.ricercaService.RicercaService_ServiceLocator ricServLoc;
		
		URL ecmMngmentUrl;
		
		try {
			ecmMngmentUrl = new URL(ENDPOINT);
			
			ricServLoc = new RicercaService_ServiceLocator();
			ricServPort = ricServLoc.getRicercaServicePort(ecmMngmentUrl);
			
			org.apache.axis.client.Stub s = (Stub)ricServPort;
			Hashtable<String, String> headers = new Hashtable<String, String>();
			headers.put("Authorization", "Bearer "+	oauthHelper.getToken() );
			
			s._setProperty(HTTPConstants.REQUEST_HEADERS, headers);

			logger.debug("[ClientRicercaService::inizializza] bearer iniettato negli headers");
			
		} catch (MalformedURLException e) {
			logger.error("[ClientRicercaService::inizializza] ERRORE MalformedURLException: "+e);
		} catch (ServiceException e) {
			logger.error("[ClientRicercaService::inizializza] ERRORE 2 ServiceException: "+e);
		}
		
//		try {

//			Object o = gwfb.create();
//			if (o == null) 
//				logger.debug("[ClientRicercaService::inizializza] ERRORE: GenericWrapperFactoryBean create() ha restituito NULL");

//			wrappedPort = (RicercaService_PortType) o;

/*
 * se metto questo log ottengo :
 *  ERROR [io.undertow.request] (default task-1) UT005023: Exception handling request to /finanziamenti/bandi/pbwebfin/restfacade/bilancio/acquisisciimpegno: org.jboss.resteasy.spi.UnhandledException: java.lang.AbstractMethodError: it.csi.wso2.apiman.oauth2.helper.extra.axis1.Axis1Impl.iniectTokenFirst(Ljava/lang/Object;Ljava/lang/String;Ljava/util/Map;)V
 */
//			logger.debug("[ClientRicercaService::inizializza] Inizializzazione terminata, wrappedPort="+wrappedPort);
			
//		} catch (Exception e) {
//			logger.debug("[ClientRicercaService::inizializza] ERRORE in ClientRicercaService: " + e);
//		}
	}
	
	
	public RicercaImpegnoResponse ricercaImpegno(RicercaImpegno input) throws Exception {
		String metodo = "ClientRicercaService.ricercaImpegno()";
		logger.begin();
		/* PK : vecchia logica su pbandisrv che utilizzava la token-retry per fare le invocazioni axis 1.4
		if (wrappedPort == null) {
			System.out.println(metodo+" wrappedPort null. INIZIALIZZO");
			inizializza();
		}
		RicercaImpegnoResponse output = null;
		if (wrappedPort == null) {
			String msg = metodo+": ERRORE: wrappedPort NULLO";
			System.out.println(msg);
			throw new Exception(msg);
		} else {
			System.out.println(metodo+" pre wrappedPort.ricercaImpegno");
			output = wrappedPort.ricercaImpegno(input);
		}
		*/
		inizializza();
		if (ricServPort == null) {
			logger.debug( metodo + " ricServPort null. INIZIALIZZO...");
			inizializza();
		}
		RicercaImpegnoResponse output = null;
		if (ricServPort == null) {
			String msg = metodo+": ERRORE: ricServPort NULLO";
			logger.error(  " messaggio =" +msg);
			throw new Exception(msg);
		} else {
			output = ricServPort.ricercaImpegno(input);
			logger.debug(metodo+" ricercaImpegno invocato");
		}
		logger.end();
		return output;
	}
	
	public RicercaCapitoloUscitaGestioneResponse ricercaCapitoloUscitaGestione(RicercaCapitoloUscitaGestione input) throws Exception {
		String metodo = "ClientRicercaService.ricercaCapitoloUscitaGestione()";
		RicercaCapitoloUscitaGestioneResponse output = null;
		if (ricServPort == null) {
			String msg = metodo+": ERRORE: wrappedPort NULLO";
			logger.error(  " messaggio =" +msg);
			throw new Exception(msg);			
		} else {
			output = ricServPort.ricercaCapitoloUscitaGestione(input);		
		}
		return output;
	}
	
	public RicercaSinteticaSoggettiResponse ricercaSinteticaSoggetti(RicercaSinteticaSoggetti input) throws Exception {
		String metodo = "ClientRicercaService.ricercaSinteticaSoggetti()";
		RicercaSinteticaSoggettiResponse output = null;
		if (ricServPort == null) {
			String msg = metodo+": ERRORE: ricServPort NULLO";
			logger.error(  " messaggio =" +msg);
			throw new Exception(msg);				
		} else {
			output = ricServPort.ricercaSinteticaSoggetti(input);			
		}
		return output;
	}
	
	public RicercaDettaglioSoggettiResponse ricercaDettaglioSoggetto(RicercaDettaglioSoggetti input) throws Exception {
		String metodo = "ClientRicercaService.ricercaDettaglioSoggetto()";
		RicercaDettaglioSoggettiResponse output = null;
		if (ricServPort == null) {
			inizializza();
//			String msg = metodo+": ERRORE: ricServPort NULLO";
//			logger.error(  " messaggio =" +msg);
//			throw new Exception(msg);				
		} 
			
		output = ricServPort.ricercaDettaglioSoggetto(input);	
		
		return output;
	}
	
	public RicercaEstesaLiquidazioniResponse ricercaEstesaLiquidazioni(RicercaEstesaLiquidazioni input) throws Exception {
		String metodo = "ClientRicercaService.ricercaEstesaLiquidazioni()";
		RicercaEstesaLiquidazioniResponse output = null;
		if (ricServPort == null) {
			logger.debug( metodo + " ricServPort null. INIZIALIZZO...");
			inizializza();
			
//			String msg = metodo+": ERRORE: ricServPort NULLO";
//			logger.error(  " messaggio =" +msg);
//			throw new Exception(msg);				
		} 
			output = ricServPort.ricercaEstesaLiquidazioni(input);
		
		return output;
	}
	
	public RicercaEstesaOrdinativiSpesaResponse ricercaEstesaOrdinativiSpesa(RicercaEstesaOrdinativiSpesa input) throws Exception {
		String metodo = "ClientRicercaService.ricercaEstesaOrdinativiSpesa()";
		RicercaEstesaOrdinativiSpesaResponse output = null;
		if (ricServPort == null) {
			String msg = metodo+": ERRORE: ricServPort NULLO";
			logger.error(  " messaggio =" +msg);
			throw new Exception(msg);				
		} else {
			output = ricServPort.ricercaEstesaOrdinativiSpesa(input);
		}
		return output;
	}
	
	public RicercaDocumentoSpesaResponse ricercaDocumentoSpesa(RicercaDocumentoSpesa input) throws Exception {
		String metodo = "ClientRicercaService.ricercaDocumentoSpesa()";
		RicercaDocumentoSpesaResponse output = null;
		if (ricServPort == null) {
			String msg = metodo+": ERRORE: ricServPort NULLO";
			logger.error(  " messaggio =" +msg);
			throw new Exception(msg);				
		} else {
			output = ricServPort.ricercaDocumentoSpesa(input);
		}
		return output;
	}
	
}