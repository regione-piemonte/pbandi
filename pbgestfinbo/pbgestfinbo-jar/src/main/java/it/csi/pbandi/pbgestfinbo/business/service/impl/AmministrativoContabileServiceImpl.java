/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.pbandi.ammvoservrest.dto.*;
import it.csi.pbandi.pbgestfinbo.business.service.AmministrativoContabileService;
import it.csi.pbandi.pbgestfinbo.integration.dao.AmministrativoContabileDAO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;


@Service
public class AmministrativoContabileServiceImpl implements AmministrativoContabileService {

    @Autowired
    AmministrativoContabileDAO amministrativoContabileDAO;

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    private final long ID_SERVIZIO_IMPORTI_REVOCHE = 6;
    //private final long ID_SERVIZIO_IBAN =  8;
    private final long ID_SERVIZIO_ESTRATTO_CONTO = 11;
    private final long ID_SERVIZIO_PIANO_AMMORTAMENTO = 12;
    private final long ID_SERVIZIO_DISTINTA_EROGAZIONE_REVOCHE = 10;
    private final long ID_SERVIZIO_DISTINTA_PASSAGGIO_PERDITA = 13;
    private final long ID_SERVIZIO_DISTINTA_EROGAZIONE_ESCUSSIONE = 9;
    private final long ID_SERVIZIO_DISTINTA_EROGAZIONE_CONTRIBUTO = 2;
    private final long ID_SERVIZIO_DISTINTA_EROGAZIONE_FINANZIAMENTO = 3;
    private final long ID_SERVIZIO_DISTINTA_EROGAZIONE_GARANZIA = 4;
    private final long ID_SERVIZIO_DISTINTA_SALDO_STRALCIO = 14;
    private final long ID_SERVIZIO_DISTINTA_CESSIONE_QUOTA = 15;
    private final long ID_SERVIZIO_DEBITO_RESIDUO = 5;
    private final long ID_SERVIZIO_RECUPERI_REVOCHE = 20;
    private final long ID_SERVIZIO_STATO_DISTINTE = 1;
    private final long ID_SERVIZIO_CONTROLLI_ISCRIZIONE_RUOLO = 18;
    private final long ID_SERVIZIO_CONTROLLI_SEGNALAZIONE_CORTE_CONTI = 19;
    private final long ID_SERVIZIO_DISTINTA_EROGAZIONE_REVOCA_BANCARIA = 21;
    private final long ID_SERVIZIO_STATO_CREDITO = 22;
    
    private final String MOD_CHIAMATA_INSERT = "I";
    private final String MOD_CHIAMATA_UPDATE = "U";
    private final String MOD_CHIAMATA_READ = "R";

    //	public static final String CLASS_NAME = "RestAPICallTest";
    //	private static final String ENDPOINT_BASE = "http://127.0.0.1:80/PBandi/api";

    //	private static final String USERNAME = "gWcp1JVE_DB3czrUf";
    //	private static final String  PASSWORD = "hk2uNw9V_5vBhbMSU";
    private String authHeader;

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.ammvo");
    private static final String AMMVO_ENDPOINT_BASE = RESOURCE_BUNDLE.getString("ammvo.endpointService");
    //private static final String AMMVO_SERV_IBAN = "/api/iban";
    private static final String AMMVO_SERV_STATO_DISTINTE = "/api/statoDistinte";
    private static final String AMMVO_SERV_IMPORTI_REVOCHE = "/api/importiRevoche";
    private static final String AMMVO_SERV_DISTINTA_EROGAZIONE = "/api/distinta";
    private static final String AMMVO_SERV_ESTRATTO_CONTO = "/api/estrattoConto";
    private static final String AMMVO_SERV_PIANO_AMMORTAMENTO = "/api/pianoAmmortamento";
    private static final String AMMVO_SERV_DEBITO_RESIDUO = "/api/debitoResiduo";
    private static final String AMMVO_SERV_RECUPERI_REVOCHE = "/api/recuperiRevoche";
    private static final String AMMVO_SERV_CONTROLLI = "/api/controlli";

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

        Long idMonitAmmCont = amministrativoContabileDAO.trackCallToAmministrativoContabilePre(idServizio, idUtente, modalitaChiamata, parametriInput, parametriOutput, idEntita, idTarget);
                
        LOG.info(prf + " END");

        return idMonitAmmCont;
    }

    // 7.2 Algoritmo Tracciamento chiamata al servizio esposto da amministrativo contabile post
    public boolean trackCallToAmministrativoContabilePost(Long idMonitAmmCont, String esito, String codErrore, String msgErr, String parametriOutput) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        amministrativoContabileDAO.trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codErrore, msgErr, parametriOutput);

        LOG.info(prf + " END");

        return true;
    }

    // get record tracciamneto amministratico contabile
//    @Override
//    public MonitoringAmministrativoContabileDTO getTrackCallToAmministartivoContabile(Long idAmmCont) throws Exception {
//        String className = Thread.currentThread().getStackTrace()[1].getClassName();
//        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//        String prf = "[" + className + "::" + methodName + "]";
//        LOG.info(prf + " BEGIN");
//
//        MonitoringAmministrativoContabileDTO result = amministrativoContabileDAO.getTrackCallToAmministartivoContabile(idAmmCont);
//
//        LOG.info(prf + " END");
//
//        return result;
//    }


    //Setta utente e password per la chiamata
    private void setAtorizationParam() {
        String auth = USERNAME + ":" + PASSWORD;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        authHeader = "Basic " + new String(encodedAuth);

    }

    private class ResponseCodeAmmCont {
        private int codiceHttp;
        private String esito; // OK-KO
        private String codiceErrore;
        private String messaggioErrore;
        private String parametriOutput;

        private ResponseCodeAmmCont(Response resp) {

        	codiceHttp = resp.getStatus();

        	if (codiceHttp == 201 || codiceHttp == 204) { // Il codice 204 è successo senza risposta - Topservice usa entrambi
        		esito = "OK";
        		parametriOutput = resp.readEntity(String.class);

        	} else {
        		esito = "KO";
        		codiceErrore = Integer.toString(codiceHttp);
        		messaggioErrore = resp.readEntity(String.class);
        		parametriOutput = messaggioErrore;
        	}
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


    //Chiamata ad amministrativo contabile anagrafica
//	@Override
//	public Long callToAnagraficaFondoIbanFondo (long idBando, String iban, int idModalitaAgev, String tipologiaConto, int moltiplicatore, String note, String fondoInps, long idUtente)  throws Exception {
//
//		String className = Thread.currentThread().getStackTrace()[1].getClassName();
//		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//		String prf = "[" + className + "::" + methodName + "]";
//		LOG.info(prf + " BEGIN");
//
//
//		Long idServizio = ID_SERVIZIO_IBAN; //e' id di riferimento per AnagraficaFondo.IbanFondo
//		String parametriInput;  // Concatenzaione chiave-valore
//		String parametriOutput =  ""; // Concatenzaione chiave-valore
//		Long idMonitAmmCont;
//
//
//
//
//		ResponseCodeAmmCont responseCodeAmmCont;
//
//		try {
//
//			//////////////// PARAMETRI //////////////
//
//			//10.1.2 Richiamo di API REST tramite JAX-RS client standard
//			Client client = ClientBuilder.newBuilder().build();
//			LOG.info(prf + " client OK");
//
//			String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_IBAN;
//			LOG.info(prf + " servUrl="+servUrl);
//
//			WebTarget target = client.target(servUrl);
//			LOG.info(prf + " target OK");
//
//			setAtorizationParam ();
//			LOG.info(prf  + " authHeader =" + authHeader);
//
//			Iban ibanAmmCont = new Iban();
//			ibanAmmCont.setIdFondo(idBando);
//			ibanAmmCont.setIban(iban);
//			ibanAmmCont.setIdAgevolazione(idModalitaAgev);
//			ibanAmmCont.setTipoConto(tipologiaConto);
//			ibanAmmCont.setMoltiplicatore(moltiplicatore);
//			ibanAmmCont.setNote(note);
//			ibanAmmCont.setFondoInpis(fondoInps);
//
//			Entity<Iban> jsonIbanAmmCont = Entity.json(ibanAmmCont);
//			LOG.info(prf + "eIban="+jsonIbanAmmCont);
//
//
//
//			//////////////// CHIAMATA //////////////
//			parametriInput = jsonIbanAmmCont.toString();
//			idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, ID_ENTITA_ANAGRAFICA, idBando);
//
//
//			Response  resp =  target.request().header("authorization", authHeader).post(jsonIbanAmmCont);
//			responseCodeAmmCont = new ResponseCodeAmmCont (resp);
//			LOG.info(prf + responseCodeAmmCont);
//
//
//			trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(),
//					responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
//
//
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//
//		LOG.info(prf + " END");
//
//		return idMonitAmmCont;
//
//
//	}

    //Chiamata ad amministrativo contabile stato distinte
    @Override
    public StatoDistinte calltoStatoDistinte(int idDistinta, int rigaDistinta, long idUtente) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_STATO_DISTINTE; //e' id di riferimento per StatoDistinte.StatoDistinte
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont;
        StatoDistinte statoDistinta = null;

        try {
            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_STATO_DISTINTE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            parametriInput =
                    "idDistinta: " + idDistinta + "\n" +
                            "rigaDistinta: " + rigaDistinta + "\n";
            LOG.info(prf + "Parametri input = " + parametriInput);

            //////////////// CHIAMATA //////////////
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_READ, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);


            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            distintaMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            Response resp = target
                    .queryParam("idDistinta", idDistinta)
                    .queryParam("rigaDistinta", rigaDistinta)
                    .request().header("authorization", authHeader).get();

            String output = "";
            if (resp.getStatus() == 200) {
                StatoDistinte[] statoDistinte = distintaMapper.readValue(resp.readEntity(String.class), StatoDistinte[].class);
                //Converto stato distinte di ammvo in stato distinte di pbandi
                output = Arrays.toString(statoDistinte);

                if(statoDistinte != null && statoDistinte.length > 0)
                    statoDistinta = statoDistinte[0];
            }

            trackCallToAmministrativoContabilePost(
                    idMonitAmmCont,
                    (resp.getStatus() == 200 ? "OK" : "KO"),
                    (resp.getStatus() != 200 ? Integer.toString(resp.getStatus()) : null),
                    (resp.getStatus() != 200 ? resp.readEntity(String.class) : null),
                    output);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        LOG.info(prf + " END");

        return statoDistinta;
    }

    //Chiamata ad amministrativo contabile importi revoche
    @Override
    public ImportiRevoche[] callToImportiRevocheImporti(int idProgetto, int idBando, Date dataErogazione, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idGestioneRevoca, long idUtente) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_IMPORTI_REVOCHE; //e' id di riferimento per ImportiRevoche.Importi
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont;
        ImportiRevoche[] importiRevoche = null;

        try {
            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_IMPORTI_REVOCHE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            //set idFondo
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            //set idBeneficiario
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            //set ibanFondo
            String ibanFondo = amministrativoContabileDAO.getIbanFondo(idBando, idModalitaAgevolazione);
            //set codiceProgetto
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String dataRiferimento = formatter.format(dataErogazione);

            parametriInput =
                    "idFondo: " + idFondo + "\n" +
                            "idBeneficiario: " + idBeneficiario + "\n" +
                            "ibanFondo: " + ibanFondo + "\n" +
                            "codiceProgetto: " + codiceProgetto + "\n" +
                            "dataRiferimento: " + dataRiferimento + "\n" +
                            "idAgevolazione: " + idModalitaAgevolazioneRif + "\n";
            LOG.info(prf + "Parametri input = " + parametriInput);

            //////////////// CHIAMATA //////////////
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_READ, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaGestioneRevoche(), idGestioneRevoca);

            Response resp = target
                    .queryParam("idFondo", idFondo)
                    .queryParam("idBeneficiario", idBeneficiario)
                    .queryParam("ibanFondo", ibanFondo)
                    .queryParam("codiceProgetto", codiceProgetto)
                    .queryParam("dataRiferimento", dataRiferimento)
                    .queryParam("idAgevolazione", idModalitaAgevolazioneRif)
                    .request().header("authorization", authHeader).get();

            String output = "";
            if (resp.getStatus() == 200) {
                importiRevoche = resp.readEntity(ImportiRevoche[].class);
                output = Arrays.toString(importiRevoche);
            }

            trackCallToAmministrativoContabilePost(
                    idMonitAmmCont,
                    (resp.getStatus() == 200 ? "OK" : "KO"),
                    (resp.getStatus() != 200 ? Integer.toString(resp.getStatus()) : null),
                    (resp.getStatus() != 200 ? resp.readEntity(String.class) : null),
                    output);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        LOG.info(prf + " END");

        return importiRevoche;
    }


    //Chiamata ad amministrativo contabile distinta erogazione
    @Override
    public Long callToDistintaErogazioneRevoca(int idDistinta, int rigaDistinta, Date dataErogazione, double oneri, double importoRevocaCapitale, int numeroRevoca, int tipoRevoca, int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_DISTINTA_EROGAZIONE_REVOCHE; //e' id di riferimento per DistintaErogazione.Revoche
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont = null;

        ResponseCodeAmmCont responseCodeAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            //set idFondo
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            //set idBeneficiario
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            //set codiceProgetto
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            //set codiceDomanda
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            //set tipologiaDistinta
            Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("REVOCA");

            Distinta distintaAmmCont = new Distinta();
            distintaAmmCont.setIdDistinta(idDistinta);
            distintaAmmCont.setRigaDistinta(rigaDistinta);
            distintaAmmCont.setIdFondo(idFondo);
            distintaAmmCont.setCodiceProgetto(codiceProgetto);
            distintaAmmCont.setIdTipoDomanda(idTipoDistinta);
            distintaAmmCont.setIdBeneficiario(idBeneficiario);
            distintaAmmCont.setDataErogazione(dataErogazione);
            distintaAmmCont.setLivello(0);
            distintaAmmCont.setStato(0);
            distintaAmmCont.setOneri(oneri);
            distintaAmmCont.setImportoRevocaCapitale(importoRevocaCapitale);
            distintaAmmCont.setIdRevoca(numeroRevoca);
            distintaAmmCont.setCodiceDomanda(codiceDomanda);
            distintaAmmCont.setIdAgevolazione(idModalitaAgevolazioneRif);
            distintaAmmCont.setTipoRevoca(tipoRevoca);

            Entity<Distinta> jsonDistintaAmmCont = Entity.json(distintaAmmCont);
            LOG.info(prf + "eDistinta=" + jsonDistintaAmmCont);

            //////////////// CHIAMATA //////////////
            parametriInput = jsonDistintaAmmCont.toString();
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);

            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            //distintaMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
            Response resp = target.request(MediaType.APPLICATION_JSON).header("authorization", authHeader).post(Entity.json(distintaMapper.writeValueAsString(distintaAmmCont)));

            responseCodeAmmCont = new ResponseCodeAmmCont(resp);
            LOG.info(prf + responseCodeAmmCont);

            trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info(prf + " END");

        return idMonitAmmCont;
    }

    @Override
    public Long callToDistintaErogazioneEscussione(int idDistinta, int rigaDistinta, int idProgetto, int idBando, String ibanBeneficiario, Date dtInizioValidita, double importoApprovato, int idTipoEscussione, String causaleBonifico, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");
        
        LOG.info(prf + " idTipoEscussione: " + idTipoEscussione);

        Long idServizio = ID_SERVIZIO_DISTINTA_EROGAZIONE_ESCUSSIONE; //e' id di riferimento per DistintaErogazione.Escussione
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont = null;

        ResponseCodeAmmCont responseCodeAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            //set idFondo
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            //set ibanFondo
            String ibanFondo = amministrativoContabileDAO.getIbanFondo(idBando, idModalitaAgevolazione);
            //set idBeneficiario
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            //set codiceProgetto
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            //set codiceDomanda
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            //set tipologiaDistinta
            Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("ESCUSSIONE");
            
            // 1 se idTipoEscussione == 3
            // 4 se idTipoEscussione == 1 or 2
            int causaleErogazione = 4;
            if(idTipoEscussione == 3) {
            	causaleErogazione = 1;
            }

            Distinta distintaAmmCont = new Distinta();
            distintaAmmCont.setIdDistinta(idDistinta);
            distintaAmmCont.setRigaDistinta(rigaDistinta);
            distintaAmmCont.setIdFondo(idFondo);
            distintaAmmCont.setCodiceProgetto(codiceProgetto);
            distintaAmmCont.setIdTipoDomanda(idTipoDistinta);
            distintaAmmCont.setIbanFondo(ibanFondo);
            distintaAmmCont.setIdBeneficiario(idBeneficiario);
            distintaAmmCont.setIbanBeneficiario(ibanBeneficiario);
            distintaAmmCont.setDataErogazione(dtInizioValidita);
            distintaAmmCont.setImporto(importoApprovato);
            distintaAmmCont.setLivello(0);
            distintaAmmCont.setStato(0);
            distintaAmmCont.setTipoEscussione(idTipoEscussione);
            distintaAmmCont.setCausaleEscussione(causaleBonifico);
            distintaAmmCont.setCodiceDomanda(codiceDomanda);
            distintaAmmCont.setIdAgevolazione(idModalitaAgevolazioneRif);
            distintaAmmCont.setCausaleErogazione(causaleErogazione);

            Entity<Distinta> jsonDistintaAmmCont = Entity.json(distintaAmmCont);
            LOG.info(prf + "eDistinta=" + jsonDistintaAmmCont);

            //////////////// CHIAMATA //////////////
            parametriInput = jsonDistintaAmmCont.toString();
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);

            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            //distintaMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
            Response resp = target.request(MediaType.APPLICATION_JSON).header("authorization", authHeader).post(Entity.json(distintaMapper.writeValueAsString(distintaAmmCont)));

            responseCodeAmmCont = new ResponseCodeAmmCont(resp);
            LOG.info(prf + responseCodeAmmCont);

            trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info(prf + " END");

        return idMonitAmmCont;
    }

    @Override
    public Long callToDistintaErogazioneContributo(int idDistinta, int rigaDistinta, int idDistintaDett, int idProgetto, int idBando, Date dataErogazione, double importo, double importoIres, int idCausaleErogazione, String iban, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_DISTINTA_EROGAZIONE_CONTRIBUTO; //e' id di riferimento per DistintaErogazione.Contributo
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont = null;

        ResponseCodeAmmCont responseCodeAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            //calcolo percentuale ires
            importoIres = importoIres / importo;

            //set idBeneficiario
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            //set idFondo
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            //set ibanFondo
            String ibanFondo = amministrativoContabileDAO.getIbanFondo(idBando, idModalitaAgevolazione);
            //set ibanBeneficiario
            String ibanBeneficiario = amministrativoContabileDAO.getIbanInterventoSostitutivo(idDistintaDett);
            if (ibanBeneficiario == null) {
                ibanBeneficiario = amministrativoContabileDAO.getIbanBeneficiario(idProgetto);
            }
            //set codiceProgetto
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            //set codiceDomanda
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            //set tipologiaDistinta
            Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("CONTRIBUTO");

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            Distinta distintaAmmCont = new Distinta();
            distintaAmmCont.setIdDistinta(idDistinta);
            distintaAmmCont.setRigaDistinta(rigaDistinta);
            distintaAmmCont.setIdFondo(idFondo);
            distintaAmmCont.setCodiceProgetto(codiceProgetto);
            distintaAmmCont.setIdTipoDomanda(idTipoDistinta);
            distintaAmmCont.setIbanFondo(ibanFondo);
            distintaAmmCont.setIdBeneficiario(idBeneficiario);
            distintaAmmCont.setIbanBeneficiario(ibanBeneficiario);
            distintaAmmCont.setDataErogazione(dataErogazione);
            distintaAmmCont.setImporto(importo);
            distintaAmmCont.setImportoIres(importoIres);
            distintaAmmCont.setLivello(0);
            distintaAmmCont.setStato(0);
            distintaAmmCont.setCodiceDomanda(codiceDomanda);
            distintaAmmCont.setIdAgevolazione(idModalitaAgevolazioneRif);
            distintaAmmCont.setCausaleErogazione(idCausaleErogazione);  // Nuova specifica (del 29 dic 2023)

            Entity<Distinta> jsonDistintaAmmCont = Entity.json(distintaAmmCont);
            LOG.info(prf + "eDistinta=" + jsonDistintaAmmCont);

            //////////////// CHIAMATA //////////////
            parametriInput = jsonDistintaAmmCont.toString();
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);

            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            //distintaMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
            Response resp = target.request(MediaType.APPLICATION_JSON).header("authorization", authHeader).post(Entity.json(distintaMapper.writeValueAsString(distintaAmmCont)));

            responseCodeAmmCont = new ResponseCodeAmmCont(resp);
            LOG.info(prf + responseCodeAmmCont);

            trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info(prf + " END");

        return idMonitAmmCont;
    }

    @Override
    public Long callToDistintaErogazioneFinanziamento(int idDistinta, int rigaDistinta, int idProgetto, int idBando, Date dataErogazione, double importo, int numRate, int freqRate, String tipoPeriodo, double percentualeInteressi, double importoIres, int idCausaleErogazione, int preammortamento, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_DISTINTA_EROGAZIONE_FINANZIAMENTO; //e' id di riferimento per DistintaErogazione.Finanziamento
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont = null;

        ResponseCodeAmmCont responseCodeAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            //calcolo percentuale ires
            importoIres = importoIres / importo;

            //set idBeneficiario
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            //set idFondo
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            //set ibanFondo
            String ibanFondo = amministrativoContabileDAO.getIbanFondo(idBando, idModalitaAgevolazione);
            //set ibanBeneficiario
            String ibanBeneficiario = amministrativoContabileDAO.getIbanBeneficiario(idProgetto);
            //set codiceProgetto
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            //set codiceDomanda
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            //set tipologiaDistinta
            Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("FINANZIAMENTO AGEVOLATO");

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            Distinta distintaAmmCont = new Distinta();
            distintaAmmCont.setIdDistinta(idDistinta);
            distintaAmmCont.setRigaDistinta(rigaDistinta);
            distintaAmmCont.setIdFondo(idFondo);
            distintaAmmCont.setCodiceProgetto(codiceProgetto);
            distintaAmmCont.setIdTipoDomanda(idTipoDistinta);
            distintaAmmCont.setIbanFondo(ibanFondo);
            distintaAmmCont.setIdBeneficiario(idBeneficiario);
            distintaAmmCont.setIbanBeneficiario(ibanBeneficiario);
            distintaAmmCont.setDataErogazione(dataErogazione);
            distintaAmmCont.setImporto(importo);
            distintaAmmCont.setNumeroRate(numRate);
            distintaAmmCont.setFrequenzaRate(freqRate);
            distintaAmmCont.setTipoPeriodo(tipoPeriodo);
            distintaAmmCont.setPercentualeInteressi(percentualeInteressi);
            distintaAmmCont.setImportoIres(importoIres);
            distintaAmmCont.setPreammortamento(preammortamento);
            distintaAmmCont.setLivello(0);
            distintaAmmCont.setStato(0);
            distintaAmmCont.setCodiceDomanda(codiceDomanda);
            distintaAmmCont.setIdAgevolazione(idModalitaAgevolazioneRif);
            distintaAmmCont.setCausaleErogazione(idCausaleErogazione); // Nuova specifica (del 29 dic 2023)

            distintaAmmCont.setImportoAmmesso(amministrativoContabileDAO.getImportoAmmessoTotale(idProgetto));

            Entity<Distinta> jsonDistintaAmmCont = Entity.json(distintaAmmCont);
            LOG.info(prf + "eDistinta=" + jsonDistintaAmmCont);

            //////////////// CHIAMATA //////////////
            parametriInput = jsonDistintaAmmCont.toString();
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);

            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            //distintaMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
            Response resp = target.request(MediaType.APPLICATION_JSON).header("authorization", authHeader).post(Entity.json(distintaMapper.writeValueAsString(distintaAmmCont)));

            responseCodeAmmCont = new ResponseCodeAmmCont(resp);
            LOG.info(prf + responseCodeAmmCont);

            trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info(prf + " END");

        return idMonitAmmCont;
    }

    @Override
    public Long callToDistintaErogazioneGaranzia(int idDistinta, int rigaDistinta, int idProgetto, int idBando, Date dataErogazione, double importo, int idCausaleErogazione, int numRate, int freqRate, String tipoPeriodo, double percentualeInteressi, int preammortamento, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_DISTINTA_EROGAZIONE_GARANZIA; //e' id di riferimento per DistintaErogazione.Garanzia
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont = null;

        ResponseCodeAmmCont responseCodeAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            //set idBeneficiario
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            //set idFondo
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            //set ibanFondo
            String ibanFondo = amministrativoContabileDAO.getIbanFondo(idBando, idModalitaAgevolazione);
            //set ibanBeneficiario
            String ibanBeneficiario = amministrativoContabileDAO.getIbanBeneficiario(idProgetto);
            //set codiceProgetto
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            //set codiceDomanda
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            //set tipologiaDistinta
            Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("GARANZIA");

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            Distinta distintaAmmCont = new Distinta();
            distintaAmmCont.setIdDistinta(idDistinta);
            distintaAmmCont.setRigaDistinta(rigaDistinta);
            distintaAmmCont.setIdFondo(idFondo);
            distintaAmmCont.setCodiceProgetto(codiceProgetto);
            distintaAmmCont.setIdTipoDomanda(idTipoDistinta);
            distintaAmmCont.setIbanFondo(ibanFondo);
            distintaAmmCont.setIdBeneficiario(idBeneficiario);
            distintaAmmCont.setIbanBeneficiario(ibanBeneficiario);
            distintaAmmCont.setDataErogazione(dataErogazione);
            distintaAmmCont.setImporto(importo);
            distintaAmmCont.setNumeroRate(numRate);
            distintaAmmCont.setFrequenzaRate(freqRate);
            distintaAmmCont.setTipoPeriodo(tipoPeriodo);
            distintaAmmCont.setPercentualeInteressi(percentualeInteressi);
            distintaAmmCont.setPreammortamento(preammortamento);
            distintaAmmCont.setLivello(0);
            distintaAmmCont.setStato(0);
            distintaAmmCont.setCodiceDomanda(codiceDomanda);
            distintaAmmCont.setIdAgevolazione(idModalitaAgevolazioneRif);
            distintaAmmCont.setCausaleErogazione(idCausaleErogazione); // Nuova specifica (del 29 dic 2023)

            distintaAmmCont.setImportoAmmesso(amministrativoContabileDAO.getImportoAmmessoBanca(idProgetto));

            Entity<Distinta> jsonDistintaAmmCont = Entity.json(distintaAmmCont);
            LOG.info(prf + "eDistinta=" + jsonDistintaAmmCont);

            //////////////// CHIAMATA //////////////
            parametriInput = jsonDistintaAmmCont.toString();
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);

            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            //distintaMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
            Response resp = target.request(MediaType.APPLICATION_JSON).header("authorization", authHeader).post(Entity.json(distintaMapper.writeValueAsString(distintaAmmCont)));

            responseCodeAmmCont = new ResponseCodeAmmCont(resp);
            LOG.info(prf + responseCodeAmmCont);

            trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info(prf + " END");

        return idMonitAmmCont;
    }

    @Override
    public Boolean callToDistintaErogazionePerdita(int idDistinta, int rigaDistinta, int idProgetto, Date dataPassaggioPerdita, double importoPerdita, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        boolean res = false;

        Long idServizio = ID_SERVIZIO_DISTINTA_PASSAGGIO_PERDITA; //e' id di riferimento per DistintaErogazione.Perdita
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont;

        ResponseCodeAmmCont responseCodeAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            // set idFondo
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            // set codiceProgetto
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            // set idBeneficiario
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            // set codiceDomanda
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            //set tipologiaDistinta
            Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("PASSAGGIO A PERDITA");


            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            Distinta distintaAmmCont = new Distinta();
            distintaAmmCont.setIdDistinta(idDistinta);
            distintaAmmCont.setRigaDistinta(1);
            distintaAmmCont.setIdFondo(idFondo);
            distintaAmmCont.setCodiceProgetto(codiceProgetto);
            distintaAmmCont.setIdTipoDomanda(idTipoDistinta);
            distintaAmmCont.setIdBeneficiario(idBeneficiario);
            distintaAmmCont.setDataErogazione(dataPassaggioPerdita);
            distintaAmmCont.setLivello(0);
            distintaAmmCont.setStato(0);
            //distintaAmmCont.setImportoRevocaCapitale(importoPerdita);
            distintaAmmCont.setImporto(importoPerdita);
            distintaAmmCont.setCodiceDomanda(codiceDomanda);
            distintaAmmCont.setIdAgevolazione(idModalitaAgevolazioneRif);
            distintaAmmCont.setIbanFondo("001"); // TODO: da mandare quest'altra chiave

            Entity<Distinta> jsonDistintaAmmCont = Entity.json(distintaAmmCont);
            LOG.info(prf + "eDistinta=" + jsonDistintaAmmCont);

            //////////////// CHIAMATA //////////////
            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            //distintaMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
            Response resp = target.request(MediaType.APPLICATION_JSON).header("authorization", authHeader).post(Entity.json(distintaMapper.writeValueAsString(distintaAmmCont)));

            parametriInput = jsonDistintaAmmCont.toString();
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);

            responseCodeAmmCont = new ResponseCodeAmmCont(resp);
            LOG.info(prf + responseCodeAmmCont);

            if ("OK".equals(responseCodeAmmCont.esito)) {
                res = true;
            }

            trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info(prf + " END");

        return res;
    }


    /*@Override
    public Long callToDistintaErogazioneCessione(int idDistinta, int rigaDistinta, int idProgetto, Date dataCessione, double impTotCess, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_DISTINTA_CESSIONE_QUOTA; //e' id di riferimento per DistintaErogazione.Perdita
        String parametriInput;  // Concatenzaione chiave-valore
        String parametriOutput = ""; // Concatenzaione chiave-valore
        Long idMonitAmmCont = null;

        ResponseCodeAmmCont responseCodeAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            // set idFondo
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            // set codiceProgetto
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            // set idBeneficiario
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            // set codiceDomanda
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            //set tipologiaDistinta
            Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("CESSIONE QUOTE");

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl=" + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader =" + authHeader);

            Distinta distintaAmmCont = new Distinta();
            distintaAmmCont.setIdDistinta(idDistinta);
            distintaAmmCont.setRigaDistinta(rigaDistinta);
            distintaAmmCont.setIdFondo(idFondo);
            distintaAmmCont.setCodiceProgetto(codiceProgetto);
            distintaAmmCont.setIdTipoDomanda(idTipoDistinta);
            distintaAmmCont.setIdBeneficiario(idBeneficiario);
            distintaAmmCont.setDataErogazione(dataCessione);
            distintaAmmCont.setLivello(0);
            distintaAmmCont.setStato(0);
            distintaAmmCont.setImporto(impTotCess);
            distintaAmmCont.setCodiceDomanda(codiceDomanda);
            distintaAmmCont.setIdAgevolazione(idModalitaAgevolazioneRif);
            Entity<Distinta> jsonDistintaAmmCont = Entity.json(distintaAmmCont);
            LOG.info(prf + "eDistinta=" + jsonDistintaAmmCont);

            //////////////// CHIAMATA //////////////
            parametriInput = jsonDistintaAmmCont.toString();
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);

            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            //distintaMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
            Response resp = target.request(MediaType.APPLICATION_JSON).header("authorization", authHeader).post(Entity.json(distintaMapper.writeValueAsString(distintaAmmCont)));

            responseCodeAmmCont = new ResponseCodeAmmCont(resp);
            LOG.info(prf + responseCodeAmmCont);

            trackCallToAmministrativoContabilePost(idMonitAmmCont, responseCodeAmmCont.getEsito(), responseCodeAmmCont.getCodiceErrore(), responseCodeAmmCont.messaggioErrore, responseCodeAmmCont.getParametriOutput());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOG.info(prf + " END");

        return idMonitAmmCont;
    }*/


    // AREA CREDITI - AMM.VO CONTABILE

    @Override
    public EstrattoConto callToEstrattoConto(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_ESTRATTO_CONTO; //e' id di riferimento per StatoDistinte.StatoDistinte
        
        // Per tracciamento chiamata a DB
        Long idMonitAmmCont = -1L;
        String esito = "";
        String codiceErrore = "";
        String messaggioErrore = "";
        String parametriInput = "";
        String parametriOutput = "";
        
        //StatoDistinte statoDistinte = null;

        EstrattoConto[] conto = null;
        //List<EstrattoConto> conto = new ArrayList<EstrattoConto>();

        try {
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);

            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_ESTRATTO_CONTO;
            LOG.info(prf + " servUrl = " + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader = " + authHeader);
            
            parametriInput = "idFondo: " + idFondo + "\n" +
                             "idBeneficiario: " + idBeneficiario + "\n" +
                             "codiceProgetto: " + codiceProgetto + "\n" +
                             "idAgevolazione: " + idModalitaAgevolazioneRif;
            
            LOG.info(prf + " Parametri input = \n" + parametriInput);


            //////////////// CHIAMATA //////////////
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_READ, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaConto(), (long) idProgetto);

            Response resp = target
                    .queryParam("idFondo", idFondo)
                    .queryParam("idBeneficiario", idBeneficiario)
                    .queryParam("codiceProgetto", codiceProgetto)
                    .queryParam("idAgevolazione", idModalitaAgevolazioneRif)
                    .request().header("authorization", authHeader).get();
            String response = resp.readEntity(String.class);

            if (resp.getStatus() == 200) {
            	// Serve per evitare errori con le date
            	ObjectMapper estrattoContoMapper = new ObjectMapper();
                estrattoContoMapper.setSerializationInclusion(Include.NON_NULL);
                estrattoContoMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
                conto = estrattoContoMapper.readValue(response, EstrattoConto[].class);

            	esito = "OK";
            	codiceErrore = null;
            	messaggioErrore = null;
                parametriOutput = response;
            } else {
            	esito = "KO";
            	codiceErrore = Integer.toString(resp.getStatus());
            	messaggioErrore = response;
				parametriOutput = response;
			}
            
            
            //trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);

        } catch (Exception e) {
            e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
        } finally {
        	if(idMonitAmmCont != -1) {
        		try {
        			trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        	
        	LOG.info(prf + " END");
        }

        //return conto.isEmpty() ? null : conto.get(0);
        return (conto != null && conto.length > 0) ? conto[0] : null;
    }

    @Override
    public PianoAmmortamento callToPianoAmmortamento(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_PIANO_AMMORTAMENTO; //e' id di riferimento per StatoDistinte.StatoDistinte
        
        // Per tracciamento chiamata a DB
        Long idMonitAmmCont = -1L;
        String esito = "";
        String codiceErrore = "";
        String messaggioErrore = "";
        String parametriInput = "";
        String parametriOutput = "";

        PianoAmmortamento[] piano = null;

        try {
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            //String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);

            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_PIANO_AMMORTAMENTO;
            LOG.info(prf + " servUrl = " + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader = " + authHeader);


            parametriInput = "idFondo: " + idFondo + "\n" +
                             "idBeneficiario: " + idBeneficiario + "\n" +
                             "codiceProgetto: " + codiceProgetto + "\n" +
                             "idAgevolazione: " + idModalitaAgevolazioneRif;
            LOG.info(prf + " Parametri input = \n" + parametriInput);


            //////////////// CHIAMATA //////////////
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_READ, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaConto(), (long) idProgetto);

            Response resp = target
                    .queryParam("idFondo", idFondo)
                    .queryParam("idBeneficiario", idBeneficiario)
                    .queryParam("codiceProgetto", codiceProgetto)
                    .queryParam("idAgevolazione", idModalitaAgevolazioneRif)
                    .request().header("authorization", authHeader).get();
            String response = resp.readEntity(String.class);

            if (resp.getStatus() == 200) {
            	// Serve per evitare errori con le date
                ObjectMapper pianoAmmortamentoMapper = new ObjectMapper();
                pianoAmmortamentoMapper.setSerializationInclusion(Include.NON_NULL);
                pianoAmmortamentoMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
                piano = pianoAmmortamentoMapper.readValue(response, PianoAmmortamento[].class);
                
                esito = "OK";
            	codiceErrore = null;
            	messaggioErrore = null;
                parametriOutput = response;
            } else {
            	esito = "KO";
            	codiceErrore = Integer.toString(resp.getStatus());
            	messaggioErrore = response;
				parametriOutput = response;
            }

            //trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);

        } catch (Exception e) {
            e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
        } finally {
        	if(idMonitAmmCont != -1) {
        		try {
        			trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        	
        	LOG.info(prf + " END");
        }

        return (piano != null && piano.length > 0) ? piano[0] : null;
    }

    @Override
    public DebitoResiduo callToDebitoResiduo(int idProgetto, int idBando, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_DEBITO_RESIDUO;
        
        // Per tracciamento chiamata a DB
        Long idMonitAmmCont = -1L;
        String esito = "";
        String codiceErrore = "";
        String messaggioErrore = "";
        String parametriInput = "";
        String parametriOutput = "";

        DebitoResiduo[] debito = null;

        try {
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            //String ibanFondo = amministrativoContabileDAO.getIbanFondo(idBando, idModalitaAgevolazione);
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);

            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DEBITO_RESIDUO;
            LOG.info(prf + " servUrl = " + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader = " + authHeader);

            parametriInput = "idFondo: " + idFondo + "\n" +
                             "idBeneficiario: " + idBeneficiario + "\n" +
                             "codiceProgetto: " + codiceProgetto + "\n" +
                             "idAgevolazione: " + idModalitaAgevolazioneRif;
            LOG.info(prf + " Parametri input = \n" + parametriInput);


            //////////////// CHIAMATA //////////////
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_READ, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaConto(), (long) idProgetto);

            Response resp = target
                    .queryParam("idFondo", idFondo)
                    .queryParam("idBeneficiario", idBeneficiario)
                    .queryParam("codiceProgetto", codiceProgetto)
                    .queryParam("idAgevolazione", idModalitaAgevolazioneRif)
                    .request().header("authorization", authHeader).get();
            String response = resp.readEntity(String.class);

            //String output = "";
            if (resp.getStatus() == 200) {
                ObjectMapper debitoResiduoMapper = new ObjectMapper();
                debito = debitoResiduoMapper.readValue(response, DebitoResiduo[].class);
                
                esito = "OK";
            	codiceErrore = null;
            	messaggioErrore = null;
                parametriOutput = response;
            } else {
                esito = "KO";
            	codiceErrore = Integer.toString(resp.getStatus());
            	messaggioErrore = response;
				parametriOutput = response;
            }
            
		} catch (Exception e) {
            e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
            
		} finally {
        	if(idMonitAmmCont != -1) {
        		try {
        			trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        	
        	LOG.info(prf + " END");
        }

        return (debito != null && debito.length > 0) ? debito[0] : null;
    }
    
    
    @Override
    public RecuperiRevoche callToRecuperiRevoche(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente, Long numeroRevoca, Long idGestioneRevoca) throws Exception {
    	String className = Thread.currentThread().getStackTrace()[1].getClassName();
    	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
    	String prf = "[" + className + "::" + methodName + "]";
    	LOG.info(prf + " BEGIN");

    	Long idServizio = ID_SERVIZIO_RECUPERI_REVOCHE;

    	// Per tracciamento chiamata a DB
    	Long idMonitAmmCont = -1L;
    	String esito = "";
    	String codiceErrore = "";
    	String messaggioErrore = "";
    	String parametriInput = "";
    	String parametriOutput = "";

    	RecuperiRevoche[] recupero = null;
    	//List<RecuperiRevoche> recupero = new ArrayList<RecuperiRevoche>();

    	try {
    		Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
    		//String ibanFondo = amministrativoContabileDAO.getIbanFondo(idBando, idModalitaAgevolazione);
    		String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
    		Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);

    		//////////////// PARAMETRI //////////////

    		//10.1.2 Richiamo di API REST tramite JAX-RS client standard
    		Client client = ClientBuilder.newBuilder().build();
    		LOG.info(prf + " client OK");

    		String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_RECUPERI_REVOCHE;
    		LOG.info(prf + " servUrl = " + servUrl);

    		WebTarget target = client.target(servUrl);
    		LOG.info(prf + " target OK");

    		setAtorizationParam();
    		LOG.info(prf + " authHeader = " + authHeader);

    		parametriInput = "idFondo: " + idFondo + "\n" +
    						 "idBeneficiario: " + idBeneficiario + "\n" +
    						 "codiceProgetto: " + codiceProgetto + "\n" +
    						 "idAgevolazione: " + idModalitaAgevolazioneRif + "\n" +
    						 "idRevoca: " + numeroRevoca;
    		LOG.info(prf + " Parametri input = \n" + parametriInput);

    		idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_READ, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaGestioneRevoche(), idGestioneRevoca);


    		//////////////// CHIAMATA //////////////

    		Response resp = target
    				.queryParam("idFondo", idFondo)
    				.queryParam("idBeneficiario", idBeneficiario)
    				.queryParam("codiceProgetto", codiceProgetto)
    				.queryParam("idAgevolazione", idModalitaAgevolazioneRif)
    				.queryParam("idRevoca", numeroRevoca)
    				.request().header("authorization", authHeader).get();
    		String response = resp.readEntity(String.class);
    		
    		if (resp.getStatus() == 200) {
                ObjectMapper recuperiRevocheMapper = new ObjectMapper();
                recupero = recuperiRevocheMapper.readValue(response, RecuperiRevoche[].class);
                
                esito = "OK";
            	codiceErrore = null;
            	messaggioErrore = null;
                parametriOutput = response;
            } else {
            	esito = "KO";
            	codiceErrore = Integer.toString(resp.getStatus());
            	messaggioErrore = response;
				parametriOutput = response;
            }

            //trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);

    	} catch (Exception e) {
            e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
        } finally {
        	if(idMonitAmmCont != -1) {
        		try {
        			trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        	
        	LOG.info(prf + " END");
        }

    	return (recupero != null && recupero.length > 0) ? recupero[0] : null;
    }
    
    
    @Override
    public Boolean callToDistintaErogazioneSaldoStralcio(int idDistinta, int rigaDistinta, int idProgetto, Date dataEsito, double impConfidi, double impDebitore, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        boolean res = false;

        Long idServizio = ID_SERVIZIO_DISTINTA_SALDO_STRALCIO; //e' id di riferimento per DistintaErogazione.Perdita
        
        // Per tracciamento chiamata a DB
    	Long idMonitAmmCont = -1L;
    	String esito = "";
    	String codiceErrore = "";
    	String messaggioErrore = "";
    	String parametriInput = "";
    	String parametriOutput = "";

        ResponseCodeAmmCont responseAmmCont;
        try {
            //////////////// PARAMETRI //////////////
            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("SALDO E STRALCIO");

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl = " + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            //Date data = new Date(dataEsito.getTime());
				/*SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				String data = null;
				if(dataEsito!=null) {
					data = dt2.format(dataEsito);
				}
				Date data2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(data);*/

            setAtorizationParam();
            LOG.info(prf + " authHeader = " + authHeader);
            
            Distinta distintaAmmCont = new Distinta();
            distintaAmmCont.setIdDistinta(idDistinta);
            distintaAmmCont.setRigaDistinta(1);
            distintaAmmCont.setIdFondo(idFondo);
            distintaAmmCont.setCodiceProgetto(codiceProgetto);
            distintaAmmCont.setIdTipoDomanda(7);
            distintaAmmCont.setIdBeneficiario(idBeneficiario);
            distintaAmmCont.setDataErogazione(dataEsito);
            distintaAmmCont.setLivello(0); // ?
            distintaAmmCont.setStato(0); // ? // Stato della Riga (Importata=1,Non  Importata=0) ??
            distintaAmmCont.setImporto(impConfidi + impDebitore); // importo debitore + importo confidi se sono valorizzati.
            // Tipologia Distinta ??
            distintaAmmCont.setCodiceDomanda(codiceDomanda);
            distintaAmmCont.setIdAgevolazione(idModalitaAgevolazioneRif);
            
            
            Entity<Distinta> jsonDistintaAmmCont = Entity.json(distintaAmmCont);
            LOG.info(prf + " jsonDistinta = \n" + jsonDistintaAmmCont);
            //parametriInput = jsonDistintaAmmCont.toString();


            //////////////// CHIAMATA //////////////
            
            ObjectMapper distintaMapper = new ObjectMapper();
            distintaMapper.setSerializationInclusion(Include.NON_NULL);
            distintaMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            parametriInput = distintaMapper.writeValueAsString(distintaAmmCont);
            
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, amministrativoContabileDAO.getIdEntitaDistinta(), (long) idDistinta);
            
            
            Response resp = target.request(MediaType.APPLICATION_JSON)
            		.header("authorization", authHeader)
            		.post(Entity.json(distintaMapper.writeValueAsString(distintaAmmCont)));
            responseAmmCont = new ResponseCodeAmmCont(resp);
            
            
        	esito = responseAmmCont.getEsito();
        	codiceErrore = responseAmmCont.getCodiceErrore();
        	messaggioErrore = responseAmmCont.getMessaggioErrore();
        	parametriOutput = responseAmmCont.getParametriOutput();
            
        	
            if ("OK".equals(responseAmmCont.getEsito())) {
                res = true;
            }

        } catch (Exception e) {
        	e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
        } finally {

        	if(idMonitAmmCont != -1L) {
        		try {
        			trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}

        	LOG.info(prf + " END");
        }

        return res;
    }

    
    @Override
    public Boolean setSegnalazioneCorteConti(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, Date dataSegnalazione, Long idUtente, Long idSegnalazioneCorteConti) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_CONTROLLI_SEGNALAZIONE_CORTE_CONTI;
        
        Boolean res = false;
        
        // Per tracciamento chiamata a DB
    	Long idMonitAmmCont = -1L;
    	String esito = "";
    	String codiceErrore = "";
    	String messaggioErrore = "";
    	String parametriInput = "";
    	String parametriOutput = "";

        ResponseCodeAmmCont responseAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + "/api/segnalazioneCorte";
            LOG.info(prf + " servUrl = " + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader = " + authHeader);

            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);

            SegnalazioneCorte segnalazione = new SegnalazioneCorte();
            segnalazione.setIdFondo(idFondo); // Codice Bando/Fondo
            segnalazione.setCodiceProgetto(codiceProgetto); // Codice Progetto
            segnalazione.setIdBeneficiario(idBeneficiario); // Codice Beneficiario
            segnalazione.setDataSegnalazione(dataSegnalazione); // Data segnalazione
            segnalazione.setCodiceDomanda(codiceDomanda); // Codice Domanda
            segnalazione.setIdAgevolazione(idModalitaAgevolazioneRif); // Tipologia Agevolazione
            segnalazione.setFlagSegnalazione(1); // Flag segnalazione Corte dei conti
            // Tipologia di invio ???

            Entity<SegnalazioneCorte> jsonSegnalazioneAmmCont = Entity.json(segnalazione);
            LOG.info(prf + "jsonSegnalazione = \n" + jsonSegnalazioneAmmCont);

            
            //////////////// CHIAMATA //////////////
            
            ObjectMapper segnalazioneMapper = new ObjectMapper();
            segnalazioneMapper.setSerializationInclusion(Include.NON_NULL);
            segnalazioneMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            parametriInput = segnalazioneMapper.writeValueAsString(segnalazione);
            
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, (long) 515, idSegnalazioneCorteConti);

            
            Response resp = target.request(MediaType.APPLICATION_JSON)
            		.header("authorization", authHeader)
            		.put(Entity.json(segnalazioneMapper.writeValueAsString(segnalazione)));
            responseAmmCont = new ResponseCodeAmmCont(resp);
            
            
            esito = responseAmmCont.getEsito();
        	codiceErrore = responseAmmCont.getCodiceErrore();
        	messaggioErrore = responseAmmCont.getMessaggioErrore();
        	parametriOutput = responseAmmCont.getParametriOutput();
            
        	
            if ("OK".equals(responseAmmCont.getEsito())) {
                res = true;
            }

        } catch (Exception e) {
        	e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
        } finally {
        	if(idMonitAmmCont != 1L) {
        		try {
					trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
		}

        LOG.info(prf + " END");


        return res;
    }
    

    @Override
    public Boolean setIscrizioneRuolo(int idProgetto, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, Date dataIscrizione, Long idUtente, Long idIscrizioneRuolo) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_CONTROLLI_ISCRIZIONE_RUOLO; // id 18

        Boolean res = false;
        
        // Per tracciamento chiamata a DB
    	Long idMonitAmmCont = -1L;
    	String esito = "";
    	String codiceErrore = "";
    	String messaggioErrore = "";
    	String parametriInput = "";
    	String parametriOutput = "";

        ResponseCodeAmmCont responseAmmCont;
        
        try {
            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + "/api/iscrizioneRuolo";
            LOG.info(prf + " servUrl = " + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader = " + authHeader);

            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);

            IscrizioneRuolo iscrizione = new IscrizioneRuolo();
            iscrizione.setIdFondo(idFondo);
            iscrizione.setCodiceProgetto(codiceProgetto);
            iscrizione.setIdBeneficiario(idBeneficiario);
            iscrizione.setDataIscrizione(dataIscrizione);
            iscrizione.setCodiceDomanda(codiceDomanda);
            iscrizione.setIdAgevolazione(idModalitaAgevolazioneRif);
            iscrizione.setFlagIscrizione(1); // ???
            // Tipologia di invio ???

            Entity<IscrizioneRuolo> jsonControlliAmmCont = Entity.json(iscrizione);
            LOG.info(prf + "jsonControlli = \n" + jsonControlliAmmCont);

            //////////////// CHIAMATA //////////////
            
            ObjectMapper iscrizioneMapper = new ObjectMapper();
            iscrizioneMapper.setSerializationInclusion(Include.NON_NULL);
            iscrizioneMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            parametriInput = iscrizioneMapper.writeValueAsString(iscrizione);
            
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, (long) 515, idIscrizioneRuolo);

            
            Response resp = target.request(MediaType.APPLICATION_JSON)
            		.header("authorization", authHeader)
            		.put(Entity.json(iscrizioneMapper.writeValueAsString(iscrizione)));
            responseAmmCont = new ResponseCodeAmmCont(resp);

            
            esito = responseAmmCont.getEsito();
        	codiceErrore = responseAmmCont.getCodiceErrore();
        	messaggioErrore = responseAmmCont.getMessaggioErrore();
        	parametriOutput = responseAmmCont.getParametriOutput();
            
        	
            if ("OK".equals(responseAmmCont.getEsito())) {
                res = true;
            }

        } catch (Exception e) {
        	e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
        } finally {
        	if(idMonitAmmCont != 1L) {
        		try {
					trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
		}

        LOG.info(prf + " END");


        return res;
    }


    
    @Override
    public Boolean setRevocaBancaria(Long idRevocaBancaria, int idDistinta, int rigaDistinta, int idProgetto, Date dataErogazione, BigDecimal importo, BigDecimal importoIres, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_DISTINTA_EROGAZIONE_REVOCA_BANCARIA; // id 21
        
        Boolean res = false;
        
        // Per tracciamento chiamata a DB
    	Long idMonitAmmCont = -1L;
    	String esito = "";
    	String codiceErrore = "";
    	String messaggioErrore = "";
    	String parametriInput = "";
    	String parametriOutput = "";

        ResponseCodeAmmCont responseAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + AMMVO_SERV_DISTINTA_EROGAZIONE;
            LOG.info(prf + " servUrl = " + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader = " + authHeader);

            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);
            //Integer idTipoDistinta = amministrativoContabileDAO.getIdTipoDistinta("FINANZIAMENTO AGEVOLATO"); // ???
            //String ibanFondo = amministrativoContabileDAO.getIbanFondo(idBando, idModalitaAgevolazione);
            //String ibanBeneficiario = amministrativoContabileDAO.getIbanBeneficiario(idProgetto);

            Distinta revocaBancaria = new Distinta();
            revocaBancaria.setIdDistinta(idDistinta); // ID Distinta
            revocaBancaria.setRigaDistinta(rigaDistinta); // Riga Distinta
            revocaBancaria.setIdFondo(idFondo); // Codice Bando/Fondo
            revocaBancaria.setCodiceProgetto(codiceProgetto); // Codice Progetto
            revocaBancaria.setIdTipoDomanda(5); // Codice Tipo Domanda // Perché dio bono scrivete nel documento che non è obbligatorio se poi il servizio lo richiede???!!!
            revocaBancaria.setIdBeneficiario(idBeneficiario); // Codice Beneficiario
            revocaBancaria.setDataErogazione(dataErogazione); // Data Erogazione
            //revocaBancaria.setLivello(0); // ???
            revocaBancaria.setStato(0); // Stato della Riga (Importata=1, Non  Importata=0)
            revocaBancaria.setCodiceDomanda(codiceDomanda); // Codice Domanda
            revocaBancaria.setIdAgevolazione(idModalitaAgevolazioneRif); // Tipologia Agevolazione
            // Tipologia Distinta ???
            // Tipologia Invio ???
            revocaBancaria.setTipoRevoca(1); // Tipologia Revoca
            
            Entity<Distinta> jsonRevocaBancariaAmmCont = Entity.json(revocaBancaria);
            LOG.info(prf + " jsonDistinta = \n" + jsonRevocaBancariaAmmCont);

            //////////////// CHIAMATA //////////////
            
            ObjectMapper controlliMapper = new ObjectMapper();
            controlliMapper.setSerializationInclusion(Include.NON_NULL);
            controlliMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            parametriInput = controlliMapper.writeValueAsString(revocaBancaria);
            
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, (long) 515, idRevocaBancaria);

            
            Response resp = target.request(MediaType.APPLICATION_JSON)
            		.header("authorization", authHeader)
            		.post(Entity.json(controlliMapper.writeValueAsString(revocaBancaria)));
            responseAmmCont = new ResponseCodeAmmCont(resp);
            
            
            esito = responseAmmCont.getEsito();
        	codiceErrore = responseAmmCont.getCodiceErrore();
        	messaggioErrore = responseAmmCont.getMessaggioErrore();
        	parametriOutput = responseAmmCont.getParametriOutput();
            
        	
            if ("OK".equals(responseAmmCont.getEsito())) {
                res = true;
            }

        } catch (Exception e) {
        	e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
        } finally {
        	if(idMonitAmmCont != 1L) {
        		try {
					trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
		}

        LOG.info(prf + " END");


        return res;
    }
    
    
	
    @Override
    public Boolean setStatoCredito(Long idStatoCredito, int idStato, int idProgetto, Date dataCambioStato, int idModalitaAgevolazione, int idModalitaAgevolazioneRif, Long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        Long idServizio = ID_SERVIZIO_STATO_CREDITO; // id 22
        
        Boolean res = false;
        
        // Per tracciamento chiamata a DB
    	Long idMonitAmmCont = -1L;
    	String esito = "";
    	String codiceErrore = "";
    	String messaggioErrore = "";
    	String parametriInput = "";
    	String parametriOutput = "";

        ResponseCodeAmmCont responseAmmCont;
        try {
            //////////////// PARAMETRI //////////////

            //10.1.2 Richiamo di API REST tramite JAX-RS client standard
            Client client = ClientBuilder.newBuilder().build();
            LOG.info(prf + " client OK");

            String servUrl = AMMVO_ENDPOINT_BASE + "/api/statoCredito";
            LOG.info(prf + " servUrl = " + servUrl);

            WebTarget target = client.target(servUrl);
            LOG.info(prf + " target OK");

            setAtorizationParam();
            LOG.info(prf + " authHeader = " + authHeader);

            Long idFondo = amministrativoContabileDAO.getIdFondo(idProgetto);
            Long idBeneficiario = amministrativoContabileDAO.getNdgBeneficiario(idProgetto);
            String codiceProgetto = amministrativoContabileDAO.getCodiceProgetto(idProgetto, idModalitaAgevolazione);
            String codiceDomanda = amministrativoContabileDAO.getCodiceDomanda(idProgetto);

            StatoCredito statoCredito = new StatoCredito();
            statoCredito.setIdFondo(idFondo); // Codice Bando/Fondo
            statoCredito.setCodiceProgetto(codiceProgetto); // Codice Progetto
            statoCredito.setIdBeneficiario(idBeneficiario); // Codice Beneficiario
            statoCredito.setDataCambioStato(dataCambioStato); // Data cambio stato
            statoCredito.setCodiceDomanda(codiceDomanda); // Codice Domanda
            // Tipologia Invio ???
            statoCredito.setIdAgevolazione(idModalitaAgevolazioneRif); // Tipologia Agevolazione
            statoCredito.setStato(idStato); // id stato credito selezionato
            
            
            Entity<StatoCredito> jsonStatoCreditoAmmCont = Entity.json(statoCredito);
            LOG.info(prf + " jsonStatoCredito = \n" + jsonStatoCreditoAmmCont);

            //////////////// CHIAMATA //////////////
            
            ObjectMapper statoCreditoMapper = new ObjectMapper();
            statoCreditoMapper.setSerializationInclusion(Include.NON_NULL);
            statoCreditoMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            parametriInput = statoCreditoMapper.writeValueAsString(statoCredito);
            
            idMonitAmmCont = trackCallToAmministrativoContabilePre(idServizio, idUtente, MOD_CHIAMATA_INSERT, parametriInput, parametriOutput, (long) 553, idStatoCredito);

            
            Response resp = target.request(MediaType.APPLICATION_JSON)
            		.header("authorization", authHeader)
            		.put(Entity.json(statoCreditoMapper.writeValueAsString(statoCredito)));
            responseAmmCont = new ResponseCodeAmmCont(resp);
            
            
            esito = responseAmmCont.getEsito();
        	codiceErrore = responseAmmCont.getCodiceErrore();
        	messaggioErrore = responseAmmCont.getMessaggioErrore();
        	parametriOutput = responseAmmCont.getParametriOutput();
            
        	
            if ("OK".equals(responseAmmCont.getEsito())) {
                res = true;
            }

        } catch (Exception e) {
        	e.printStackTrace();
            esito = "KO";
            codiceErrore = "Server err";
            messaggioErrore = e.getMessage();
            parametriOutput = e.toString();
        } finally {
        	if(idMonitAmmCont != 1L) {
        		try {
					trackCallToAmministrativoContabilePost(idMonitAmmCont, esito, codiceErrore, messaggioErrore, parametriOutput);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
		}

        LOG.info(prf + " END");


        return res;
    }
    
    
    
}



