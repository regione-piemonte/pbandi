/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia;

import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.apache.axis.transport.http.HTTPConstants;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiService_PortType;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiService_ServiceLocator;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumento;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoAsyncResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.ElaboraDocumentoResponse;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumento;
import it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.LeggiStatoElaborazioneDocumentoResponse;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;

public class ClientDocumentiService {
	
	public static final String LOGGER_PREFIX = "pbandisrv";
	
	@Autowired
	protected LoggerUtil logger;
		
	public LoggerUtil getLogger() {
		return logger;
	}
	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}
		
	
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("clientws");
	private static final String OAUTH_URL = RESOURCE_BUNDLE.getString("contabilia.oauthURL");
	private static final String CONSUMER_KEY = RESOURCE_BUNDLE.getString("contabilia.consumerKey");
	private static final String CONSUMER_SECRET = RESOURCE_BUNDLE.getString("contabilia.consumerSecret");
	private static final String ENDPOINT = RESOURCE_BUNDLE.getString("contabilia.documentiService.endpoint");
	
	private static final QName SERVICE_NAME = new QName("http://siac.csi.it/documenti/svc/1.0", "DocumentiService");
	
//	private static DocumentiService_PortType wrappedPort = null;
	private static DocumentiService_PortType docServPort = null;

	
	private void inizializza() {
		//RicercaService_ServiceLocator

		final URL url = DocumentiService_ServiceLocator.class.getResource("/bilancio-cre--BILANCIO_contabilia_documentiService1.0.wsdl");	
		logger.info("ClientDocumentiService: URL = " +url);
		logger.info("ClientDocumentiService: OAUTH_URL = " +OAUTH_URL);
		logger.info("ClientDocumentiService: CONSUMER_KEY = " +CONSUMER_KEY);
		logger.info("ClientDocumentiService: CONSUMER_SECRET = " +CONSUMER_SECRET);
		logger.info("ClientDocumentiService: ENDPOINT = " +ENDPOINT);
		
		DocumentiService_ServiceLocator ss = null;
		DocumentiService_PortType port = null;
		try {
			ss = new DocumentiService_ServiceLocator(url.toString(), SERVICE_NAME);
			port = ss.getDocumentiServicePort();
		} catch (ServiceException e) {
			logger.error("ClientDocumentiService: ERRORE: "+e);
		}
		if (port == null)
			logger.error("ClientDocumentiService: ERRORE: DocumentiService_PortType NON ISTANZIATO");
		
		OauthHelper oauthHelper = new OauthHelper(OAUTH_URL,CONSUMER_KEY,CONSUMER_SECRET,10000); // time out in ms

//		TokenRetryManager trm = new TokenRetryManager();
//		trm.setOauthHelper(oauthHelper);			
//		WsProvider wsp = new Axis1Impl();		
//		trm.setWsProvider(wsp);
//		if (wsp == null)
//			logger.error("ClientDocumentiService: ERRORE: WsProvider NON ISTANZIATO");
//		
//		GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
//		gwfb.setEndPoint(ENDPOINT);	
//		gwfb.setWrappedInterface(DocumentiService_PortType.class);
//		gwfb.setPort(port);
//		gwfb.setTokenRetryManager(trm);
		
		it.csi.pbandi.pbservizit.pbandisrv.business.clientws.contabilia.documentiService.DocumentiService_ServiceLocator docServLoc;
		
		URL ecmMngmentUrl;
		
		try {
						
//			Object o = gwfb.create();
//			if (o == null) 
//				logger.error("ClientDocumentiService: ERRORE: GenericWrapperFactoryBean create() ha restituito NULL");
						
			ecmMngmentUrl = new URL(ENDPOINT);
			
			docServLoc = new DocumentiService_ServiceLocator();
			docServPort = docServLoc.getDocumentiServicePort(ecmMngmentUrl);
			
			org.apache.axis.client.Stub s = (Stub)docServPort;
			Hashtable<String, String> headers = new Hashtable<String, String>();
			headers.put("Authorization", "Bearer "+	oauthHelper.getToken() );
			
			s._setProperty(HTTPConstants.REQUEST_HEADERS, headers);

			logger.debug("[ClientRicercaService::inizializza] bearer iniettato negli headers");
			
		} catch (Exception e) {
			logger.error("ClientDocumentiService: ERRORE in ClientDocumentiService: " + e);
		}
	}
	
	public ElaboraDocumentoResponse elaboraDocumento(ElaboraDocumento input) throws Exception {
		String metodo = "ClientDocumentiService.elaboraDocumento()";
		ElaboraDocumentoResponse output = null;
//		if (wrappedPort == null) {
//			String msg = metodo+": ERRORE: wrappedPort NULLO";
//			logger.error(msg);
//			throw new Exception(msg);
//		} else {
//			output = wrappedPort.elaboraDocumento(input);
//		}
		if (docServPort == null) {
			String msg = metodo+": wrappedPort NULLO";
			logger.debug(msg+". Chiamo metodo inizializza");
			inizializza();
		} 
		output = docServPort.elaboraDocumento(input);
		
		return output;
	}
	
	public ElaboraDocumentoAsyncResponse elaboraDocumentoAsync(ElaboraDocumento input) throws Exception {
		String metodo = "ClientDocumentiService.elaboraDocumentoAsync()";
		ElaboraDocumentoAsyncResponse output = null;
//		if (wrappedPort == null) {
//			String msg = metodo+": ERRORE: wrappedPort NULLO";
//			logger.error(msg);
//			throw new Exception(msg);
//		} else {
//			output = wrappedPort.elaboraDocumentoAsync(input);
//		}
		if (docServPort == null) {
			String msg = metodo+": wrappedPort NULLO";
			logger.debug(msg+". Chiamo metodo inizializza");
			inizializza();
		} 
		output = docServPort.elaboraDocumentoAsync(input);
		
		return output;
	}
	
	public LeggiStatoElaborazioneDocumentoResponse leggiStatoElaborazioneDocumento(LeggiStatoElaborazioneDocumento input) throws Exception {
		String metodo = "ClientDocumentiService.LeggiStatoElaborazioneDocumento()";
		LeggiStatoElaborazioneDocumentoResponse output = null;
//		if (wrappedPort == null) {
//			String msg = metodo+": ERRORE: wrappedPort NULLO";
//			logger.error(msg);
//			throw new Exception(msg);
//		} else {
//			output = wrappedPort.leggiStatoElaborazioneDocumento(input);
//		}
		if (docServPort == null) {
			String msg = metodo+": wrappedPort NULLO";
			logger.debug(msg+". Chiamo metodo inizializza");
			inizializza();
		} 
		output = docServPort.leggiStatoElaborazioneDocumento(input);
		
		return output;
	}

}
