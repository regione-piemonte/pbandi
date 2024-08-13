/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.service.impl;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.csi.pbandi.pbwebbo.business.service.AmministrativoContabileService;
import it.csi.pbandi.pbwebbo.dto.amministrativoContabile.Anagrafica;
import it.csi.pbandi.pbwebbo.dto.amministrativoContabile.Iban;
import it.csi.pbandi.pbwebbo.dto.amministrativoContabile.MonitoringAmministrativoContabileDTO;
import it.csi.pbandi.pbwebbo.integration.dao.impl.AmministrativoContabileDAOImpl;
import it.csi.pbandi.pbwebbo.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

 // TODO DA CAMBIARE


//import jakarta.ws.rs.client.Client;
//import jakarta.ws.rs.client.ClientBuilder;
//import jakarta.ws.rs.client.Entity;
//import jakarta.ws.rs.client.WebTarget;
//import jakarta.ws.rs.core.Response;
//import org.glassfish.jersey.jackson.internal.DefaultJacksonJaxbJsonProvider;
//import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationIntrospector;


@Service
public class AmministrativoContabileServiceImpl implements AmministrativoContabileService {
	
	@Autowired
	AmministrativoContabileDAOImpl amministrativoContabileDAOImpl;
	
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	private final long ID_SERVIZIO_ANAGRAFICA =  7;
	private final long ID_SERVIZIO_IBAN =  8;
	private final String MOD_CHIAMATA_INSERT =  "I";
	private final String MOD_CHIAMATA_UPDATE =  "U";
	private final long ID_ENTITA_ANAGRAFICA =  5;
	private final long ID_ENTITA_IBAN =  128;
	
	//	public static final String CLASS_NAME = "RestAPICallTest";
	//	private static final String ENDPOINT_BASE = "http://127.0.0.1:80/PBandi/api";
		
	//	private static final String USERNAME = "**********************";
	//	private static final String  PASSWORD = "********************";
	private String authHeader;
	
	private  static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.ammvo");
	private static final String AMMVO_ENDPOINT_BASE = RESOURCE_BUNDLE.getString("ammvo.endpointService");
	private static final String AMMVO_SERV_ANAGRAFICA = "/api/anagrafica";
	private static final String AMMVO_SERV_IBAN = "/api/iban";
	
	private static final String USERNAME = RESOURCE_BUNDLE.getString("ammvo.endpointServUsr");
	private static final String PASSWORD = RESOURCE_BUNDLE.getString("ammvo.endpointServPwd");
	
	
	// 7.1	Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile pre	
	public Long trackCallToAmministrativoContabilePre(
														Long idServizio, // 7 = AnagraficaFondo.Anagrafica, 8 = AnagraficaFondo.IbanFondo
														Long idUtente,
														String modalitaChiamata, // I = insert, U = update
														String parametriInput, // Concatenzaione chiave-valore
														String parametriOutput,
														Long idEntita, // Valorizzato a seconda del servizio chiamato, es: 5 se è stato chiamato il servizio AnagraficaFondo.Anagrafica, 128 se è stato chiamato il servizio AnagraficaFondo.IbanFondo
														Long idTarget // Valorizzato con l’identificativo relative all’ID_ENTITA, es: Coincide con l’ID_BANDO se è stato chiamato il servizio AnagraficaFondo.Anagrafica, Coincide con ID_ESTREMI_BANCARI se è stato chiamato il servizio AnagraficaFondo.IbanFondo

	) throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");
		
		
		Long idMonitAmmCont = amministrativoContabileDAOImpl.trackCallToAmministrativoContabilePre(idServizio, idUtente, modalitaChiamata, parametriInput, parametriOutput, idEntita, idTarget);
		
		
		
		LOG.info(prf + " END");
		
		return idMonitAmmCont;
	}

	// 7.2 Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile post
	
	public boolean trackCallToAmministrativoContabilePost(Long idMonitAmmCont, String esito, String codErrore, String msgErr,  String parametriOutput) throws Exception {
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");
		
		amministrativoContabileDAOImpl.trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codErrore, msgErr, parametriOutput);
		
		
		
		LOG.info(prf + " END");
		
		return true;
			
		}
		
	// get record tracciamneto amministratico contabile
	@Override
	public MonitoringAmministrativoContabileDTO getTrackCallToAmministartivoContabile( Long idAmmCont) throws Exception{
			
			String className = Thread.currentThread().getStackTrace()[1].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String prf = "[" + className + "::" + methodName + "]";
			LOG.info(prf + " BEGIN");
			
			
			MonitoringAmministrativoContabileDTO result =  amministrativoContabileDAOImpl.getTrackCallToAmministartivoContabile( idAmmCont);
			
			
			LOG.info(prf + " END");
			
			return result;
			
		}
		
	
		
	//Chiamata ad amministrativo contabile anagrafica	
	@Override
	public Long callToAnagraficaFondoAnagrafica (Long idBando, String titoloBando, String contropartita, Integer idNatura, 
			Long idEnteCompetenza, String note, long idUtente)  throws Exception {
		
		
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");
		
		
		Long idServizio = ID_SERVIZIO_ANAGRAFICA; //e' id di riferimento per AnagraficaFondo.Anagrafica
		String parametriInput =  "";  // Concatenzaione chiave-valore
		String parametriOutput =  ""; // Concatenzaione chiave-valore
		Long idMonitAmmCont = null;
		
		
		idMonitAmmCont = trackCallToAmministrativoContabilePre(ID_SERVIZIO_ANAGRAFICA, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, ID_ENTITA_ANAGRAFICA, idBando);
		
		
		ResponseCodeAmmCont responseCodeAmmCont = null;
				
		try {
			
			//////////////// PARAMETRI //////////////
			
			
			//10.1.2 Richiamo di API REST tramite JAX-RS client standard
			Client client = ClientBuilder.newBuilder().build();
			LOG.info(prf + " client OK");
			
			String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_ANAGRAFICA;
			LOG.info(prf + " servUrl="+servUrl);
			
			//WebTarget target = client.target(ENDPOINT_BASE+"/anagrafica");
			WebTarget target = client.target(servUrl);
					
			LOG.info(prf + " target OK");
			
			setAtorizationParam ();
			LOG.info(prf  + " authHeader =" + authHeader);	        
			//target.request().header("authorization", authHeader);
			
			Anagrafica anagraficaAmmCont = new Anagrafica();			
	        anagraficaAmmCont.setIdFondo(idBando); 
			anagraficaAmmCont.setDescrizione(titoloBando);
			anagraficaAmmCont.setContropartita(contropartita);
			anagraficaAmmCont.setIdNatura(idNatura);
			anagraficaAmmCont.setIdDirezione(idEnteCompetenza.intValue());			
			anagraficaAmmCont.setNote(note);			
			
			Entity<Anagrafica> jsonAnagraficaAmmCont = Entity.json(anagraficaAmmCont);			
			LOG.info(prf + "Result anagrafica: \n " + jsonAnagraficaAmmCont);
			
			
			//////////////// CHIAMATA //////////////
			
			//Response resp = (Response) target.request().header("authorization", authHeader).post(jsonAnagraficaAmmCont);	
			//LOG.info(prf + "response.getStatus="+resp.getStatus());
			//LOG.info(prf + ".getEntity="+ resp.readEntity(String.class));			
			
			//responseCodeAmmCont = new ResponseCodeAmmCont (resp);
			
			//trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), 
			//		responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
			
			
			parametriInput = jsonAnagraficaAmmCont.toString();
			idMonitAmmCont = trackCallToAmministrativoContabilePre(ID_SERVIZIO_ANAGRAFICA, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, ID_ENTITA_ANAGRAFICA, idBando);
			
			
			Response  resp =  target.request().header("authorization", authHeader).post(jsonAnagraficaAmmCont);	
			responseCodeAmmCont = new ResponseCodeAmmCont (resp);
			LOG.info(prf + responseCodeAmmCont.toString());		
			
			trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), 
					responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
			
			if(responseCodeAmmCont.getEsito().equals("KO")) {
				if(responseCodeAmmCont.getMessaggioErrore() == null) {
					throw new Exception("Errore generico");
				}
				
				throw new Exception(responseCodeAmmCont.getMessaggioErrore());
			}
			
			
			
			
		
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		LOG.info(prf + " END");
		
	
		return idMonitAmmCont;
		
		
	}
	
	//Chiamata ad amministrativo contabile anagrafica
	@Override
	public Long callToAnagraficaFondoIbanFondo (long idBando, String iban, int idModalitaAgev, String tipologiaConto, int moltiplicatore, String note, String fondoInps, long idUtente)  throws Exception {
			
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");
		
		
		Long idServizio = ID_SERVIZIO_IBAN; //e' id di riferimento per AnagraficaFondo.IbanFondo
		String parametriInput =  "";  // Concatenzaione chiave-valore
		String parametriOutput =  ""; // Concatenzaione chiave-valore
		Long idMonitAmmCont = null;
		
		
		
		
		ResponseCodeAmmCont responseCodeAmmCont = null;
		
		try {
			
			//////////////// PARAMETRI //////////////
			
			//10.1.2 Richiamo di API REST tramite JAX-RS client standard
			Client client = ClientBuilder.newBuilder().build();
			LOG.info(prf + " client OK");
			
			String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_IBAN;
			LOG.info(prf + " servUrl="+servUrl);
			
			WebTarget target = client.target(servUrl);
			LOG.info(prf + " target OK");

			setAtorizationParam ();
			LOG.info(prf  + " authHeader =" + authHeader);	
			
			Iban ibanAmmCont = new Iban();			
			ibanAmmCont.setIdFondo(idBando); 
			ibanAmmCont.setIban(iban); 
			ibanAmmCont.setIdAgevolazione(idModalitaAgev); 
			ibanAmmCont.setTipoConto(tipologiaConto); 			
			ibanAmmCont.setMoltiplicatore(moltiplicatore);
			ibanAmmCont.setNote(note);
			ibanAmmCont.setFondoInpis(fondoInps);
			
			Entity<Iban> jsonIbanAmmCont = Entity.json(ibanAmmCont);			
			LOG.info(prf + "eIban="+jsonIbanAmmCont);
			
			
			
			//////////////// CHIAMATA //////////////
			
			parametriInput = jsonIbanAmmCont.toString();
			idMonitAmmCont = trackCallToAmministrativoContabilePre(ID_SERVIZIO_ANAGRAFICA, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, ID_ENTITA_ANAGRAFICA, idBando);
			
			
			Response  resp =  target.request().header("authorization", authHeader).post(jsonIbanAmmCont);	
			responseCodeAmmCont = new ResponseCodeAmmCont (resp);
			LOG.info(prf + responseCodeAmmCont.toString());		
			
			trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), 
					responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
			
			if(responseCodeAmmCont.getEsito().equals("KO")) {
				if(responseCodeAmmCont.getMessaggioErrore() == null) {
					throw new Exception("Errore generico");
				}
				
				throw new Exception(responseCodeAmmCont.getMessaggioErrore());
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		LOG.info(prf + " END");
		
		return idMonitAmmCont;
		
		
	}
	
	
	
	//Setta utente e password per la chiamata
	private void setAtorizationParam () {
		
		// PK cosi' setto la parte di authorization a mano
		// verificare se esiste un metodo piu' furbo
		
		String auth = USERNAME + ":" + PASSWORD;
        byte[] encodedAuth = Base64.encodeBase64( auth.getBytes(Charset.forName("US-ASCII")) );
        authHeader = "Basic " + new String( encodedAuth );
       
	}

	
	
	private class ResponseCodeAmmCont {
		
		private int codiceHttp;
		private String esito; // OK-KO 
		private String codiceErrore;
		private String messaggioErrore;
		private String parametriOutput;
		
		private ResponseCodeAmmCont(Response resp) {
			
			codiceHttp = resp.getStatus();	
			
			if(codiceHttp == 201) 			
				esito = "OK";
			
			else {			
				esito = "KO";
				codiceErrore = Integer.toString(codiceHttp);
				messaggioErrore =  resp.readEntity(String.class);
			}
			
			parametriOutput = messaggioErrore;
			
		}
		

		public int getCodiceHttp() {
			return codiceHttp;
		}

		public void setCodiceHttp(int codiceHttp) {
			this.codiceHttp = codiceHttp;
		}

		public String getEsito() {
			return esito;
		}

		public void setEsito(String esito) {
			this.esito = esito;
		}

		public String getCodiceErrore() {
			return codiceErrore;
		}

		public void setCodiceErrore(String codiceErrore) {
			this.codiceErrore = codiceErrore;
		}

		public String getMessaggioErrore() {
			return messaggioErrore;
		}

		public void setMessaggioErrore(String messaggioErroore) {
			this.messaggioErrore = messaggioErroore;
		}

		public String getParametriOutput() {
			return parametriOutput;
		}

		public void setParametriOutput(String parametriOutput) {
			this.parametriOutput = parametriOutput;
		}


		@Override
		public String toString() {
			return "ResponseCodeAmmCont [codiceHttp=" + codiceHttp + ", messaggioErrore=" + messaggioErrore + "]";
		}
		
		
		
		
	}



	
	
	
	
	
	// Fine classe
	
}



