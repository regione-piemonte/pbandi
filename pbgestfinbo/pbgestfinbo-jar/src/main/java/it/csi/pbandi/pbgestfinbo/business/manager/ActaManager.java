/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.business.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.sql.DataSource;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.pbgestfinbo.dto.DatiXActaDTO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbgestfinbo.util.AcarisUtils;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbgestfinbo.util.DateUtil;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
import it.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.doqui.acta.acaris.backofficeservice.BackOfficeServicePort;
import it.doqui.acta.acaris.documentservice.ContenutoFisicoIRC;
import it.doqui.acta.acaris.documentservice.DocumentService;
import it.doqui.acta.acaris.documentservice.DocumentServicePort;
import it.doqui.acta.acaris.documentservice.DocumentoArchivisticoIRC;
import it.doqui.acta.acaris.documentservice.DocumentoFisicoIRC;
import it.doqui.acta.acaris.documentservice.EnumTipoDocumentoArchivistico;
import it.doqui.acta.acaris.documentservice.EnumTipoOperazione;
import it.doqui.acta.acaris.documentservice.IdentificatoreDocumento;
import it.doqui.acta.acaris.objectservice.ObjectService;
import it.doqui.acta.acaris.objectservice.ObjectServicePort;
import it.doqui.acta.acaris.officialbookservice.OfficialBookService;
import it.doqui.acta.acaris.officialbookservice.OfficialBookServicePort;
import it.doqui.acta.acaris.repositoryservice.AcarisException;
import it.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.ClassificazionePropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.ContenutoFisicoPropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.DocumentoFisicoPropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.DocumentoSemplicePropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumDocPrimarioType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumFolderObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumTipoDocumentoType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.IdFormaDocumentariaType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.IdStatoDiEfficaciaType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.PrincipalExtResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.AcarisContentStreamType;
import it.doqui.acta.actasrv.dto.acaris.type.common.CodiceFiscaleType;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumMimeTypeType;
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
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.DestinatarioInterno;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.EnumTipoAPI;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.EnumTipoRegistrazioneDaCreare;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.EnumTipologiaSoggettoAssociato;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.IdentificazioneProtocollante;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.IdentificazioneRegistrazione;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.InfoCreazioneCorrispondente;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.InfoCreazioneRegistrazione;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.MittenteEsterno;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.ProtocollazioneDocumentoEsistente;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.RegistrazioneArrivo;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.RiferimentoSoggettoEsistente;

public class ActaManager extends JdbcDaoSupport{
	private Logger logger = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public ActaManager(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Autowired
	private DecodificheDAOImpl decodificheDAOImpl;
	
	@Autowired
	private  it.csi.pbandi.pbgestfinbo.integration.dao.GestioneLog logMonitoraggioService;
	
	private BackOfficeServicePort backofficeServicePort;
	private ObjectServicePort objectServicePort;
	private OfficialBookServicePort officialBookServicePort;
	private RepositoryServicePort repositoryServicePort; 
	private static DocumentServicePort documentServicePort;
	
	// trippletta letta dal db
	private long idAoo;
	private long idStruttura;
	private long idNodo;
	
	private String codiceFiscale = null ;
	private String actaUtenteAppkey = null;
	private String actaRepositoryName = null;
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("conf.acta");
	private static final String CONSUMER_KEY = RESOURCE_BUNDLE.getString("acta.CONSUMER_KEY");
	private static final String CONSUMER_SECRET = RESOURCE_BUNDLE.getString("acta.CONSUMER_SECRET");
	private static final String OAUTH_URL = RESOURCE_BUNDLE.getString("acta.OAUTH_URL");
	private static final String endpointBackOfficeService = RESOURCE_BUNDLE.getString("acta.endpointBackOfficeService");
	private static final String endpointRepositoryService = RESOURCE_BUNDLE.getString("acta.endpointRepositoryService");
	private static final String endpointObjectService = RESOURCE_BUNDLE.getString("acta.endpointObjectService");
//	private static final String endpointNavigationService = RESOURCE_BUNDLE.getString("acta.endpointNavigationService");
//	private static final String endpointRelationshipsService = RESOURCE_BUNDLE.getString("acta.endpointRelationshipsService");
	private static final String endpointOfficialBookService = RESOURCE_BUNDLE.getString("acta.endpointOfficialBookService");
	private static final String endpointDocumentService = RESOURCE_BUNDLE.getString("acta.endpointDocumentService");
	
//	private static String ERROR_ICE_NOT_VALID = "Codice di classificazione esteso non valido.";
	private static String ERROR_ACTA_SERVICE = "Impossibile contattare i servizi di Acta.";
	private static String ERROR_ACTA_PRINCIPAL = "Impossibile individuare il principal su Acta.";
//	private static String ERROR_ACTA_COLLOCATION = "Collocazione del file non trovata su Acta.";
//	private static String ERROR_ACTA_ICE = "Codice di classificazione esteso non trovato.";
//	private static String ERROR_ACTA_CLASSIFICAZIONE = "Classificazione non trovata su Acta.";
//	private static String ERROR_ACTA_ICE_PROJ = "Il codice di classificazione esteso non appartiene al settore del progetto corrente.";
	private static String ERROR_ACTA_GENERIC = "Acta ha restituito un errore generico.";
	
	private static final Long PERFETTO_ED_EFFICACE = 2L; // perfetto ed efficace
	private static final Long PERFETTO_ED_EFFICACE_NON_FIRMATO = 8L; // perfetto ed efficace ma non firmato
	
	private static final String DESCRIZIONE_FORMA_DOCUMENTARIA = "Controlli";
	private static final String APPLICATIVO_ALIMENTANTE = "PBANFP";
	
	private ObjectIdType repositoryId;
//	private PrincipalExtResponseType principal;
		
	private static final String DESCTIPODOC_DS = "DS";			// DICHIARAZIONE DI SPESA
	private static final String DESCTIPODOC_RE = "RE";			// RICHIESTA DI EROGAZIONE
	private static final String DESCTIPODOC_PR = "PR";			// PROPOSTA DI RIMODULAZIONE
	private static final String DESCTIPODOC_RIN = "RIN";		// COMUNICAZIONE DI RINUNCIA
	private static final String DESCTIPODOC_CFP = "CFP";		// COMUNICAZIONE DI FINE PROGETTO
	private static final String DESCTIPODOC_DGE = "DGE";		// DOCUMENTO GENERICO ESCUSSIONE

	//Da finpiemonte verso beneficiario
	private static final String DESCTIPODOC_LARIR = "LARIR";	//LA richiesta integrazione rendicontazione- firmati digitalmente
	private static final String DESCTIPODOC_LADS = "LADS";		//LA esito validazione- non firmati digitalmente
	private static final String DESCTIPODOC_LACL = "LACL";		//LA richiesta di integrazione al controllo in loco - non firmati digitalmente
	private static final String DESCTIPODOC_AVCL = "AVCL";		//Lettere di avvio controllo in loco - non firmati digitalmente
	private static final String DESCTIPODOC_ESCL = "ESCL";		//Lettera esito controllo in loco - non firmati digitalmente
	private static final String DESCTIPODOC_VCV = "VCV";		//Verbale di controllo in loco - non firmato digitalmente
	private static final String DESCTIPODOC_LRIPC = "LRIPC";	//LA richiesta di integrazione al procedimento di revoca - non firmati digitalmente
	private static final String DESCTIPODOC_LAVPC = "LAVPC";	//LA avvio procedimento di revoca - non firmati digitalmente
	private static final String DESCTIPODOC_LARPC = "LARPC";	//LA richiesta di archiviazione procedimento di revoca - non firmati digitalmente
	private static final String DESCTIPODOC_LEMPV = "LEMPV";	//LA emissione provvedimento di emissione revoca -  non firmati digitalmente
	private static final String DESCTIPODOC_LRAPV = "LRAPV";	//LA ritiro in autotutela provvedimento di revoca - non firmati digitalmente
	private static final String DESCTIPODOC_LADE = "LADE";		//LA creazione distinta di erogazione - non firmati digitalmente
	private static final String DESCTIPODOC_RIB = "RIB";		//LA richiesta di integrazione all'escussione - non firmati digitalmente
	private static final String DESCTIPODOC_IEB = "IEB";		//LA esito escussione- non firmati digitalmente
	private static final String DESCTIPODOC_LCFPV = "LCFPV";	//LA conferma del provvedimento di revoca - non firmati digitalmente
	private static final String DESCTIPODOC_REPVAL = "REPVAL";	//Report validazione excel
	private static final String DESCTIPODOC_LACIS = "LACIS";	//LA communicazione intervento sostitutivo

	//Methods
	private DocumentoArchivisticoIRC popolaMetadati(String objectIdFolderDomandaStr, PrincipalIdType userPrincipalId, DatiXActaDTO datiActa) throws Exception {
		String prf = "[ActaManager::popolaMetadati]";
		logger.info(prf + " BEGIN");
		logger.info(prf + " INPUT PARAM datiActa = " + datiActa);
		
		DocumentoArchivisticoIRC datiCreazione = new DocumentoArchivisticoIRC();

		// Parent Folder
		ObjectIdType objectIdFolderDomanda = new ObjectIdType();
		objectIdFolderDomanda.setValue(objectIdFolderDomandaStr);
		datiCreazione.setParentFolderId(objectIdFolderDomanda);
		
		// Dati di Classificazione
		ClassificazionePropertiesType associativeObjectProperties = new ClassificazionePropertiesType();
		associativeObjectProperties.setCartaceo(false);			// valore identico per tutte le tipologiaDocumento
		associativeObjectProperties.setCopiaCartacea(false);	// valore identico per tutte le tipologiaDocumento
		datiCreazione.setPropertiesClassificazione(associativeObjectProperties);

		// Dati Principali
		datiCreazione.setTipoDocumento(EnumTipoDocumentoArchivistico.DOCUMENTO_SEMPLICE); // valore identico per tutte le tipologiaDocumento

		// campi dipendenti dalla tipologia di documento
		String oggetto; 		//////// metadati - Dati Principali -> Oggetto // max 1000 char
		String autoreGiur;   	//////// metadati - Dati di Identita' -> Autore giuridico // max 400 char
		String autoreFis; 		//////// metadati - Dati di Identita' -> Utente collegato // max 400 char
		String destGiuridico; 	//////// metadati - Dati di Identita' -> Destinatario giuridico // max 400 char
		String destFisico;		//////// metadati - Dati di Identita' -> Destinatario fisico - denominazione // max 400 char

		String beneficiario;
		try{
			beneficiario = getJdbcTemplate().queryForObject(
					"SELECT CASE\n" +
							"WHEN pteg.ID_ENTE_GIURIDICO IS NOT NULL THEN pteg.DENOMINAZIONE_ENTE_GIURIDICO \n" +
							"WHEN ptpf.ID_PERSONA_FISICA IS NOT NULL THEN ptpf.COGNOME || ' ' || ptpf.NOME \n" +
							"ELSE '' END AS denominazione\n" +
							"FROM PBANDI_R_SOGGETTO_DOMANDA prsd\n" +
							"LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.id_domanda = prsd.ID_DOMANDA\n" +
							"LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg on pteg.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\n" +
							"LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf on ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA \n" +
							"WHERE prsd.ID_TIPO_BENEFICIARIO <> 4\n" +
							"AND prsd.ID_TIPO_ANAGRAFICA = 1\n" +
							"AND ptd.ID_DOMANDA = ?",
					String.class,
					datiActa.getIdDomanda()
			);
		} catch (IncorrectResultSizeDataAccessException e){
			beneficiario = "";
		}
		logger.info(prf + " beneficiario="+beneficiario);
		String legaleBenef;
		try{
			legaleBenef = getJdbcTemplate().queryForObject(
					"SELECT CASE\n" +
							"WHEN enteGiuridico.ID_ENTE_GIURIDICO IS NOT NULL THEN enteGiuridico.DENOMINAZIONE_ENTE_GIURIDICO \n" +
							"WHEN personaFisica.ID_PERSONA_FISICA IS NOT NULL THEN personaFisica.COGNOME || ' ' || personaFisica.NOME  \n" +
							"WHEN enteGiuridico2.ID_ENTE_GIURIDICO IS NOT NULL THEN enteGiuridico2.DENOMINAZIONE_ENTE_GIURIDICO \n" +
							"WHEN personaFisica2.ID_PERSONA_FISICA IS NOT NULL THEN personaFisica2.COGNOME || ' ' || personaFisica2.NOME  \n" +
							"ELSE '' END AS denominazione\n" +
							"FROM PBANDI_T_DOCUMENTO_INDEX ptdi \n" +
							"LEFT JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.ID_SOGGETTO = ptdi.ID_SOGG_DELEGATO \n" +
							"LEFT JOIN (SELECT DISTINCT \n" +
							"\tsoggetto.id_soggetto,\n" +
							"\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
							"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
							"\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
							"\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
							"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
							"LEFT JOIN (SELECT DISTINCT \n" +
							"\tsoggetto.id_soggetto,\n" +
							"\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
							"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
							"\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
							"\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
							"LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
							"LEFT JOIN PBANDI_T_SOGGETTO soggetto2 ON soggetto2.ID_SOGGETTO = ptdi.ID_SOGG_RAPPR_LEGALE  \n" +
							"LEFT JOIN (SELECT DISTINCT \n" +
							"\tsoggetto.id_soggetto,\n" +
							"\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
							"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
							"\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
							"\tGROUP BY soggetto.id_soggetto) enteUnivoco2 ON enteUnivoco2.id_soggetto = soggetto2.id_soggetto \n" +
							"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico2 ON enteGiuridico2.id_ente_giuridico = enteUnivoco2.id_ente_giuridico\n" +
							"LEFT JOIN (SELECT DISTINCT \n" +
							"\tsoggetto.id_soggetto,\n" +
							"\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
							"\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
							"\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
							"\tGROUP BY soggetto.id_soggetto) personaUnivoca2 ON personaUnivoca2.id_soggetto = soggetto2.id_soggetto \n" +
							"LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica2 ON personaFisica2.id_persona_fisica = personaUnivoca2.id_persona_fisica\n" +
							"WHERE ptdi.ID_DOCUMENTO_INDEX = ?",
					String.class,
					datiActa.getIdDocIndex()
			);
		} catch (IncorrectResultSizeDataAccessException e){
			legaleBenef = "";
		}
		logger.info(prf + " legaleBenef="+legaleBenef);
		if(legaleBenef==null || "".equals(legaleBenef)){
			legaleBenef = beneficiario;
		}
		switch (datiActa.getDescrBreveTipoDoc()) {
			//DOCUMENTI INVIATI DAL BENEFICIARIO
			case DESCTIPODOC_DS:		// [colonna J e K] DS Dichiarazione di spesa
				oggetto = "Dichiarazione di spesa [DS-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				break;
			case DESCTIPODOC_CFP:		// [colonna L e M] DS Finale Comunicazione fine progetto
				oggetto = "Ds Finale di Comunicazione [CFP-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				break;
			case "X":					// [N e O] Ds Integrativa , forse ricade sotto DS ma ID_TIPO_DICH_SPESA=4 ???
				oggetto = "DS Integrativa [DS-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				break;
			case "XX":					// [P e Q] Documenti integrativi (rendicontazioni controlli)
				oggetto = "Documenti Integrativi [DI-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				break;
			case DESCTIPODOC_RIN:		// [R e S] Comunicazione di rinuncia
				oggetto = "Comunicazione rinuncia [RIN-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				break;
			case DESCTIPODOC_PR:		// [T e U] Proposta di rimodulazione
				oggetto = "Proposta rimodulazione [PRIM-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				break;
			case DESCTIPODOC_RE:		// [V e W] Richiesta erogazione
				oggetto = "Richiesta erogazione [RICEROG-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				break;
			case "XXX":				// [AE e AF] Contestazione
				oggetto = "Contestazione [CONT-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				//PK non utilizzata da iter Autorizzativo
				break;
			case "XXXX":			// [AG] Controdeduzioni
				oggetto = "Controdeduzioni [CONTR-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				//PK non utilizzata da iter Autorizzativo
				break;
			case "XXXXX":			// [AH e AI] Documenti integrativi (Revoche)
				oggetto = "Documenti Integrativi [DOCINT-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				//PK non utilizzata da iter Autorizzativo
				break;
			case "XXXXXX":			// [AN e AO]  Documenti integrativi (Controllo in loco)
				oggetto = "Documenti Integrativi [DOCINT-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = beneficiario;
				autoreFis = legaleBenef;
				destGiuridico = "FINPIEMONTE";
				destFisico = "";
				//PK non utilizzata da iter Autorizzativo
				break;
			case "XXXXXXXX":		// [AS e AT]  Richiesta di integrazione all'escussione
				oggetto = "Richiesta di integrazione [RESCINT-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				//PK non utilizzata da iter Autorizzativo
				break;

				//DOCUMENTI INVIATI DA FINPIEMONTE
			case DESCTIPODOC_LARIR:
				oggetto = "Lettera accompagnatoria [LAINTRENDICONTAZIONE-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LADS:
				oggetto = "Lettera accompagnatoria [LAESITOVAL-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LACL:
				oggetto = "Lettera accompagnatoria [LAINTCL-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_AVCL:
				oggetto = "Lettera accompagnatoria [LAAVCL-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_ESCL:
				oggetto = "Lettera accompagnatoria [ESITOCL-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_VCV:
				oggetto = "Lettera accompagnatoria [VERBCONTRLOCO-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LRIPC:
				oggetto = "Lettera accompagnatoria [LAREV-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LAVPC:
				oggetto = "Lettera accompagnatoria [LAAPR-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LARPC:
				oggetto = "Lettera accompagnatoria [LAARCHREV-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LEMPV:
				oggetto = "Lettera accompagnatoria [LAEMISREVOCA-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LRAPV:
				oggetto = "Lettera accompagnatoria [LAAUTOTUTELA-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LADE:
				oggetto = "Lettera accompagnatoria [LADISTEROG-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_RIB:
				oggetto = "Lettera accompagnatoria [LAINTESCUS-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_IEB:
				oggetto = "Lettera accompagnatoria [LAESITOESCUS-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LCFPV:
				oggetto = "Lettera accompagnatoria [LAPROVREVOCA-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_REPVAL:
				oggetto = "Report validazione excel [REPVAL-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			case DESCTIPODOC_LACIS:
				oggetto = "Lettera accompagnatoria communicazione intervento sostitutivo [LACIS-" + datiActa.getIdDomanda() + datiActa.getNomeFile() + "]";
				autoreGiur = "FINPIEMONTE";
				autoreFis = "";
				destGiuridico = beneficiario;
				destFisico = legaleBenef;
				break;
			default:
				logger.error("Descrizione tipo documento non gestita su Acta: " + datiActa.getDescrBreveTipoDoc());
				throw new Exception("Descrizione tipo documento non gestita su Acta: " + datiActa.getDescrBreveTipoDoc());
		}
		
		logger.info(prf + " oggetto = " + oggetto);
		DocumentoSemplicePropertiesType properties = new DocumentoSemplicePropertiesType();
		properties.setOggetto(oggetto);
		
		properties.setOrigineInterna(false); // valore identico per tutte le tipologiaDocumento

		// chiesto ACARIS - Presenza file
		// TRUE se si passa un file da archiviare
		properties.setRappresentazioneDigitale(true);
		
		properties.setDatiPersonali(true);  // valore identico per tutte le tipologiaDocumento
		properties.setDatiRiservati(false); // valore identico per tutte le tipologiaDocumento
		properties.setDatiSensibili(false); // valore identico per tutte le tipologiaDocumento
		
		// "Annotazione formale" - valore indefinito nell'analisi
		// 		per inserire un annotazione ad un documento o una aggregazione, vedere operazione managementService.addAnnotazione
		// "Applica annotazione a intero documento" - valore indefinito nell'analoso
		// "Applica annotazione a classificazione corrente" - valore indefinito nell'analoso

		properties.setRegistrato(true); // valore identico per tutte le tipologiaDocumento
		properties.setModificabile(false); // valore identico per tutte le tipologiaDocumento
		properties.setDefinitivo(true); // valore identico per tutte le tipologiaDocumento
		
		properties.setDataCreazione(getDataOdiernaSenzaOra());

		// Dati di Identita'
		
		// Autore giuridico // max 400 char
		logger.info(prf + " autoreGiur = " + autoreGiur+ ", size "+ autoreGiur.length());
		if(autoreGiur.length()>400) {
			autoreGiur = autoreGiur.substring(0,399);
		}
		
		String[] autoreGiurArr = {autoreGiur}; 
		properties.setAutoreGiuridico(autoreGiurArr);
		
		//Autore fisico // max 400 char
		logger.info(prf + " autoreFis = " + autoreFis + ", size "+ autoreFis.length());
		if(autoreFis.length()>400) {
			autoreFis = autoreFis.substring(0,399);
		}
		String[] autoreFisArr = {autoreFis}; 
		properties.setAutoreFisico(autoreFisArr);
		
		//Originatore
		//<identita digitale dell'utente che si collega a PBANFP> (Cognome + Nome +  " - " + Codice fiscale)
		String originatore = datiActa.getUtenteCollegatoCognome() + " " + datiActa.getUtenteCollegatoNome() + " - " + datiActa.getUtenteCollegatoCF();
		
		logger.info(prf + " originatore = " + originatore+ ", size "+ originatore.length());
		if(originatore.length()>400) {
			originatore = originatore.substring(0,399);
		}
		String[] originatoreArr = {originatore}; 
		properties.setOriginatore(originatoreArr);

		//Destinatario giuridico  // max 400 char
		logger.info(prf + " destGiuridico = " + destGiuridico+ ", size "+ destGiuridico.length());
		if(destGiuridico.length()>400) {
			destGiuridico = destGiuridico.substring(0,399);
		}
		String[] destGiuridicoArr = {destGiuridico}; 
		properties.setDestinatarioGiuridico(destGiuridicoArr);
		
		
		//Destinatario fisico - denominazione  // max 400 char
		logger.info(prf + " destFisico = " + destFisico + ", size "+ destFisico.length());
		if(destFisico.length()>400) {
			destFisico = destFisico.substring(0,399);
		}
		String[] destFisicoArr = {destFisico}; 
		properties.setDestinatarioFisico(destFisicoArr);
		
		properties.setDataDocCronica(getDataOdiernaSenzaOra());
		properties.setDocConAllegati(false);
		properties.setDocAutenticato(false);
		properties.setDocAutenticatoFirmaAutenticata(false);
		properties.setDocAutenticatoCopiaAutentica(false);

		//Forma documentaria
		//E' la forma documentaria associata al documento. La si deve scegliere tra le forme documentaria valide definite dall'Ente
		// chiesto ACARIS  - Forma documentaria vale "CONTROLLI"
		IdFormaDocumentariaType idFormaDoc;
		try {
			idFormaDoc = AcarisUtils.queryForFormaDocumentaria(DESCRIZIONE_FORMA_DOCUMENTARIA, objectServicePort, getRepositoryId(), userPrincipalId );
			logger.info(prf + " idFormaDoc="+idFormaDoc.getValue());
		} catch (Exception e) {
			logger.error("Error = "+e.getMessage());
			//e.printStackTrace();
			throw new Exception ("Errore nel reperimento dell'id della forma documentaria");
		}
		properties.setIdFormaDocumentaria(idFormaDoc);
		 
		properties.setFirmaElettronica(datiActa.isFirmaDigitale());
		properties.setFirmaElettronicaDigitale(datiActa.isFirmaDigitale());
		//properties.setDataDocChiusuraRifTempUTC(getDataOdiernaSenzaOra());
		
		// chiesto ACARIS  - Riferimento temporale (UTC)
		// legato al valore di Data Chiusura 
		
		// chiesto ACARIS  - Data Chiusura
		// valorizzabile solo per documenti di tipo REGISTRO e non per documenti di tipo SEMPLICE
        // valorizzare DocumentoRegistroPropertiesType.dataChiusura	

		// Dati di Integrita'
		
		// chiesto ACARIS  - AOO responsabile affare
		// -- valore EREDITATO
		
		// chiesto ACARIS  - Struttura responsabile affare
		// -- valore EREDITATO
		
		properties.setApplicativoAlimentante(APPLICATIVO_ALIMENTANTE);
		
//		IdVitalRecordCodeType idVitalRecordCode = new IdVitalRecordCodeType();
//		idVitalRecordCode.setValue(10L); //TODO verificare con ACARIS , in doc c'e' ALTO
//		properties.setIdVitalRecordCode(idVitalRecordCode);
	

		// Conservazione sostitutiva
		// nessuno
		
		// Protocollo
		// nessuno
		
		// Documento elettronico
		IdStatoDiEfficaciaType idStatoE = new IdStatoDiEfficaciaType();
		ContenutoFisicoPropertiesType contenutoFisicoPropertiesType = new ContenutoFisicoPropertiesType();
		
		EnumMimeTypeType mimeType;
				
		// in base al fatto che sia un file firmato o meno
		if(datiActa.isFirmaDigitale()) {
			idStatoE.setValue(PERFETTO_ED_EFFICACE);
			properties.setTipoDocFisico(EnumTipoDocumentoType.FIRMATO);
			contenutoFisicoPropertiesType.setSbustamento(true);
			mimeType = EnumMimeTypeType.APPLICATION_TIMESTAMPED_DATA;
		}else {
			idStatoE.setValue(PERFETTO_ED_EFFICACE_NON_FIRMATO);
			properties.setTipoDocFisico(EnumTipoDocumentoType.SEMPLICE);
			contenutoFisicoPropertiesType.setSbustamento(false); //valido solo per i doc firmati
			mimeType = EnumMimeTypeType.APPLICATION_PDF;
		}
	
		properties.setComposizione(EnumDocPrimarioType.DOCUMENTO_SINGOLO);
		
		// Molteplicita della composizione
		// chiesto ACARIS  - Molteplicita della composizione
		properties.setMultiplo(false);
		
		properties.setContentStreamFilename(datiActa.getNomeFile());
		
		// chiesto ACARIS - Rimanda l’operazione di sbustamento == SI
//		in base al valore di ContenutoFisicoPropertiesType.sbustamento
//			-- sbustamento = false -> rimanda operazione (quindi non sbusta il documento firmato)
//			-- sbustamento = true -> non rimanda operazione (quindi sbusta il documento firmato)

		// Attributi creazione/modifica
		
		// chiesto ACARIS - Archivio corrente = SI
		// e' un valore impostato dal sistema
		properties.setDataCreazione(getDataOdiernaSenzaOra());

		// chiesto ACARIS - Utente di creazione
		// e' un valore impostato dal sistema

		datiCreazione.setPropertiesDocumento(properties);

		// setto nelle properties il documento fisico
		AcarisContentStreamType acarisContentStreamType = new AcarisContentStreamType();
		
		ContenutoFisicoIRC[] contenuti = new ContenutoFisicoIRC[1];
		DocumentoFisicoIRC[] documenti = new DocumentoFisicoIRC[1];

		contenuti[0] = new ContenutoFisicoIRC();
		contenuti[0].setPropertiesContenutoFisico(contenutoFisicoPropertiesType);
		 
		documenti[0] = new DocumentoFisicoIRC();
		
		DocumentoFisicoPropertiesType documentoFisicoProperty = new DocumentoFisicoPropertiesType();
		documentoFisicoProperty.setDescrizione("documento fisico");
		documentoFisicoProperty.setDataMemorizzazione(new Date());
		documentoFisicoProperty.setProgressivoPerDocumento(1);
		
		documenti[0].setPropertiesDocumentoFisico(documentoFisicoProperty);
		
		final OutputStream oS = new ByteArrayOutputStream(datiActa.getTsdFile().length);
	    final InputStream iS = new ByteArrayInputStream(datiActa.getTsdFile());
	    javax.activation.DataSource a = new javax.activation.DataSource()
	    {
			public OutputStream getOutputStream() {
				return oS;
			}
			public String getName()
			{
				return "";
			}
			public InputStream getInputStream() {
				return iS;
			}
			public String getContentType()
			{
				return "";
			}
	    };
	    
	    acarisContentStreamType.setStreamMTOM(new DataHandler(a));
	    acarisContentStreamType.setStream(datiActa.getTsdFile());
	    acarisContentStreamType.setMimeType(mimeType);
	    acarisContentStreamType.setFilename(datiActa.getNomeFile());
	    
	    contenuti[0].setStream(acarisContentStreamType); 
	    
	    contenuti[0].setTipo(EnumStreamId.PRIMARY);
		documenti[0].setContenutiFisici(contenuti);

		datiCreazione.setDocumentiFisici(documenti);
		
		logger.info(prf + " END");
		return datiCreazione;
	}


	//Util
	private static Date getDataOdiernaSenzaOra() {
		Calendar adesso = new GregorianCalendar(Locale.ITALY);

		adesso.setTime(adesso.getTime());
		adesso.set(Calendar.AM_PM, Calendar.AM);
		adesso.set(Calendar.HOUR, 0);
		adesso.set(Calendar.MINUTE, 0);
		adesso.set(Calendar.SECOND, 0);

		return adesso.getTime();
	}


	private IdentificatoreDocumento creaDocumentoActa(PrincipalIdType userPrincipalId, DocumentoArchivisticoIRC datiCreazione) {
		String prf = "[ActaManager::creaDocumentoActa] ";
		logger.info(prf + " BEGIN");
		long start=System.currentTimeMillis();
		Long idChiamata = null;
		IdentificatoreDocumento identificatoreDocumento = null;
		try {
			
			 ObjectIdType initialRepositoryObjectIdType= new ObjectIdType(); 
		     initialRepositoryObjectIdType.setValue(getRepositoryId().getValue());
		     
		     idChiamata = logMonitoraggioService.trackCallPre(23L,17812L, "W", 53L, 106188L,
		    		 EnumTipoOperazione.ELETTRONICO);	
			identificatoreDocumento = documentServicePort.creaDocumento(initialRepositoryObjectIdType , userPrincipalId, 
					EnumTipoOperazione.ELETTRONICO, datiCreazione);
			
			logMonitoraggioService.trackCallPost(idChiamata, "OK" , "200","", identificatoreDocumento);
			
			logger.info(prf + " identificatoreDocumento="+identificatoreDocumento);
			
			logger.info(prf + " IdClassificazione="+	identificatoreDocumento.getObjectIdClassificazione().getValue());
			
		} catch (it.doqui.acta.acaris.objectservice.AcarisException e) {
			logger.error( prf + "AcarisException = " + e.getMessage(), e);
			logMonitoraggioService.trackCallPost(idChiamata, "KO" ,e.getMessage(), "500", null);
		} catch (Exception e) {
			logMonitoraggioService.trackCallPost(idChiamata, "KO" ,e.getMessage(), "500", null);
			logger.error( prf + "Exception = " + e.getMessage(), e);
			
		}finally{
			logger.info(prf + " END, executed in ms: "+(System.currentTimeMillis()-start));
		}
		logger.info(prf + " END");
		return identificatoreDocumento;
	}


	private ObjectIdType getObjectIByDescrAndParentNode(String valueProp,
			PropertyFilterType propertyFilterType, Integer maxItems,
			Integer skipCount, PrincipalIdType userPrincipalId,
			QueryableObjectType target,String nomeProp, ObjectIdType parentNode) {

		String prf = "[ActaManager::getObjectIByDescrAndParentNode] ";
		logger.info(prf + " BEGIN");
		
		ObjectIdType objectId = null;
		long start=System.currentTimeMillis();
		Long idChiamata = null;
		try {
			QueryConditionType[] queryConditionTypes = new QueryConditionType[1];
	        QueryConditionType c1 = new QueryConditionType();
	        c1.setOperator(EnumQueryOperator.EQUALS);
	        c1.setPropertyName(nomeProp);
	        c1.setValue(valueProp );
	        
	        //imposto un punto di partenza nella ricerca del sottofascicolo (passare un parametro in piu al metodo con l'id del fasciolo
	        NavigationConditionInfoType myNavigationConditionInfoType = new NavigationConditionInfoType();
	        myNavigationConditionInfoType.setParentNodeId(parentNode);   

	        logger.info(prf + "  GET OBJECT BY nomeProp: "+nomeProp+" ,valueProp  "+valueProp+")");
	        
	        queryConditionTypes[0] = c1;
			
	        ObjectIdType initialRepositoryObjectIdType= new ObjectIdType(); 
	       	initialRepositoryObjectIdType.setValue(getRepositoryId().getValue());
	       	
			logger.info(prf + "  calling  objectServicePort.query by parola chiave to find  object id " );
			idChiamata = logMonitoraggioService.trackCallPre(26L,17812L, "R",53L, 106188L,userPrincipalId, queryConditionTypes);
			PagingResponseType result = objectServicePort.query(initialRepositoryObjectIdType, userPrincipalId, 
					target, propertyFilterType, queryConditionTypes,
					myNavigationConditionInfoType, maxItems,
					skipCount);
		 
			logMonitoraggioService.trackCallPost(idChiamata, "OK" , "200","","", result);
			if (result.getObjects() != null) {
				if(result.getObjects().length>1){
					logger.warn(prf + "  Attenzione! mi aspetto un solo elemento invece objectServicePort.query ne ha restituiti: "+result.getObjects().length);
				}
				for (ObjectResponseType objectResponseType : result.getObjects()) {
					objectId = objectResponseType.getObjectId();
					logger.info(prf + "  objectId   : " + objectId);
				}
			}
			
			printObjectResponseType(result);
			
		} catch (it.doqui.acta.acaris.objectservice.AcarisException e) {
			logMonitoraggioService.trackCallPost(idChiamata, "KO" ,e.getMessage(), "500", null);
			logger.error( prf + "AcarisException = " + e.getMessage(), e);
		} catch (Exception e) {
			logger.error( prf + "Exception = " + e.getMessage(), e);
		}finally{
			logger.info(prf + " END, executed in ms: "+(System.currentTimeMillis()-start));
		}
		return objectId;
	}


	private ObjectIdType getObjectID(String valueProp,
			PropertyFilterType propertyFilterType, Integer maxItems,
			Integer skipCount, PrincipalIdType userPrincipalId,
			QueryableObjectType target, String nomeProp) {
		
		String prf = "[ProtocolManager::getObjectID] ";
		logger.info( prf + "BEGIN");
		
		long start=System.currentTimeMillis();
		ObjectIdType objectId = null;
	
		try	{
	        QueryConditionType c1 = new QueryConditionType();
	        c1.setOperator(EnumQueryOperator.EQUALS);
	        c1.setPropertyName(nomeProp);
	        c1.setValue(valueProp);

	        logger.info( prf + " (GET OBJECT BY nomeProp=[" + nomeProp + "], valueProp=[" + valueProp + "])");

	        //Le condizioni potrebbero essere 1 o 2, uso un arraylist
	        ArrayList<QueryConditionType> lista = new ArrayList<QueryConditionType>();
	        lista.add(c1);

//	        logger.info( prf + "objectIdNodo="+objectIdNodo);
//	        if (objectIdNodo != null)
//	        {
//		        QueryConditionType c2 = new QueryConditionType();
//		        c2.setOperator(EnumQueryOperator.EQUALS);
//		        c2.setPropertyName("objectId");
//		        c2.setValue(objectIdNodo);
//		        logger.info( prf + "aggiunta seconda QueryConditionType per objectIdNodo");
//
//		        lista.add(c2);
//	        }

	        QueryConditionType[] queryConditionTypes = new QueryConditionType[lista.size()];
	        int i = 0;
	        for (Object item : lista)
	        {
	        	queryConditionTypes[i] = (QueryConditionType)item;
	        	i++;
	        }
		     
	        String reposiString = getRepositoryId().getValue();
	        
	        ObjectIdType initialRepositoryObjectIdType= new ObjectIdType(); 
	       	initialRepositoryObjectIdType.setValue(getRepositoryId().getValue());

	        
	        logger.info( prf + "userPrincipalId = "+userPrincipalId.getValue());
	        logger.info( prf + "repositoryId = "+repositoryId.getValue());
	        logger.info( prf + "reposiString = "+reposiString);
	        
	        NavigationConditionInfoType navigationConditionInfoType = new NavigationConditionInfoType();
	        
			logger.info( prf + "calling  objectServicePort.query by parola chiave to find  object id " );
			PagingResponseType result = objectServicePort.query(initialRepositoryObjectIdType, userPrincipalId, 
				target, propertyFilterType, queryConditionTypes,
				navigationConditionInfoType, maxItems,	skipCount);

			if (result.getObjects() != null)	{
				
				logger.warn( prf + "result.getObjects().length = " + result.getObjects().length);
				
				if (result.getObjects().length > 1) {
					logger.warn( prf + "Attenzione! mi aspetto un solo elemento invece objectServicePort.query ne ha restituiti: "+result.getObjects().length);
				}
				
				printObjectResponseType(result);
				
				for (ObjectResponseType obj : result.getObjects())
				{
					objectId = obj.getObjectId();
					logger.info( prf + "objectId   : " + objectId + ", valore="+objectId.getValue());
				}
			}else {
				logger.info( prf + "query.getObjects() NULL");

			}
		}
		catch (it.doqui.acta.acaris.objectservice.AcarisException e)
		{
			logger.error( prf + "AcarisException = " + e.getMessage(), e);
		}
		catch (Exception e)
		{
			logger.error( prf + "EXCEPTION " + e.getMessage(), e);
			e.printStackTrace();
		}
		finally
		{
			logger.info( prf + "END, executed in ms: " + (System.currentTimeMillis() - start));
		}

		return objectId;
	}


	private void printObjectResponseType(PagingResponseType result) {

		String prf = "[ActaManager::printObjectResponseType] ";
		logger.info(prf + " BEGIN");
		
		if(result!=null && result.getObjects().length > 0) {
			logger.info(prf + " result.getObjects().length = "+result.getObjects().length);
			for (ObjectResponseType obj : result.getObjects())
			{
				if (obj.getPropertiesLength() > 0) {
                    for (PropertyType property : obj.getProperties())  {
                    	logger.info(prf + "property.getQueryName().getPropertyName()="+property.getQueryName().getPropertyName());
                        if (property.getQueryName().getPropertyName().equals("descrizione")) {
                        	logger.info(prf + " val =" + property.getValue().getContent()[0]);
                        }
                        if (property.getQueryName().getPropertyName().equals("objectId")) {
                        	logger.info(prf + " val =" + property.getValue().getContent()[0]);
                        }
                    }
                }
			}
		}else {
			logger.info(prf + " PagingResponseType result NULL o vuoto");
		}
		logger.info(prf + " END");
	}

	
	
	
	// metodi di ricerca/inizializzazione su acta
	public void inizalizzaActa() throws Exception {
		String prf = "[ActaManager::inizalizzaActa] ";
		logger.info(prf + " BEGIN");
		
		actaRepositoryName = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_FINP_REPOSITORYNAME, true);
		codiceFiscale = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_FINP_UTENTECF, true);
		actaUtenteAppkey = decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_FINP_UTENTEAPPKEY, true);
		logger.info(prf + " actaRepositoryName="+actaRepositoryName);
		logger.info(prf + " codiceFiscale="+codiceFiscale);
		logger.info(prf + " actaUtenteAppkey="+actaUtenteAppkey);
		
		idAoo = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_FINP_IDAOO, true));
		idStruttura = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_FINP_IDSTRUTTURA, true));
		idNodo = new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ACTA_FINP_IDNODO, true));
		logger.info(prf + " idAoo="+idAoo);
		logger.info(prf + " idStruttura="+idStruttura);
		logger.info(prf + " idNodo="+idNodo);
		
        long start = System.currentTimeMillis();
        
        inizializzaBackOfficeService();
		inizializzaRepositoryService();
		inizializzaObjectService();
//		inizializzaNavigationService();
//		inizializzaRelationshipsService();
		inizializzaOfficialBookService();
		inizializzaDocumentServiceService();
		
        logger.info(prf + " inizializzazione avvenuta");
        
//        PrincipalExtResponseType principalMaster = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
//        logger.info( prf + "principalMaster="+principalMaster + ", time="+start);

        long d = System.currentTimeMillis() - start;
        
        logger.info(prf + "durata = [" + d + "] ms");
        logger.info(prf + "END");
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
		Long idChiamata = null;
		try {
			 idChiamata = logMonitoraggioService.trackCallPre(28L,17812L, "R", 53L, 17812L);	
			AcarisRepositoryEntryType[] reps = repositoryServicePort.getRepositories();

			if(reps == null){
				logMonitoraggioService.trackCallPost(idChiamata, "KO" ,"404","", reps);
				logger.error(prf + " repositoryServicePort.getRepositories() non ha resituito resultati");
				return null;
			}
			
				logMonitoraggioService.trackCallPost(idChiamata, "OK" ,"200","", reps);
			
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
			logMonitoraggioService.trackCallPost(idChiamata, "KO" ,e.getMessage(), "500", null);
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
		logger.debug(prf + " actaUtenteAppkey= "+actaUtenteAppkey);
		logger.debug(prf + " codiceFiscale= "+codiceFiscale);
		logger.debug(prf + " idRepository= "+idRepository);
		
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
		Long idChiamata= null;
		try {

			 idChiamata = logMonitoraggioService.trackCallPre(25L,17812L, "R", 53L, 106188L, codFiscale, _idAOO, _idStruttura, _idNodo);
			arrPrincipalExt = backofficeServicePort.getPrincipalExt(objectIdType, codFiscale, _idAOO, _idStruttura, _idNodo, clientApplicationInfo);
			if (arrPrincipalExt!=null) {
				logMonitoraggioService.trackCallPost(idChiamata, "OK" , "200","", arrPrincipalExt);
				logger.debug(prf + " >>>>>>> arrPrincipalExt lenght= "+arrPrincipalExt.length);
				logger.debug(prf + " >>>>>>> Time = "+ start);
			}
			
		} catch (Exception e) {
			logMonitoraggioService.trackCallPost(idChiamata, "KO" ,e.getMessage(), "500", null);
			logger.error(prf + " e.getMessage(): " + e.getMessage());		
			e.printStackTrace();
			throw new Exception(ERROR_ACTA_PRINCIPAL);
		} finally {
			long end = System.currentTimeMillis();
			logger.debug(prf + " END, durata " + (end-start)/1000);
		}
		return arrPrincipalExt != null ? arrPrincipalExt[0] : null;
	}
    
    private void inizializzaBackOfficeService() throws Exception {

		String prf = "[ActaManager::inizializzaBackOfficeService] ";
		logger.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("/wsdl/ACARISWS-BackOfficeService.wsdl");
			logger.info(prf + ">><< url="+url);
			
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
    
    private void inizializzaObjectService() throws Exception {
		String prf = "[ActaManager::inizializzaObjectService] ";
		logger.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-ObjectService.wsdl");
			logger.info(prf + ">><< url="+url);
 
			ObjectService os = new  ObjectService(url);
			logger.info(prf + " ok os");
			
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
    
    private void inizializzaDocumentServiceService() throws Exception {

		String prf = "[ActaManager::inizializzaDocumentServiceService] ";
		logger.info(prf + " BEGIN");
		
		try {
			URL url = this.getClass().getClassLoader().getResource("wsdl/ACARISWS-DocumentService.wsdl");
			logger.info(prf + ">><< url="+url);
			
			DocumentService ds = new  DocumentService(url);
			DocumentServicePort port = ds.getDocumentServicePort();
			logger.info(prf + " port="+port);
		
			BindingProvider bp = (BindingProvider) port;
			Map<String, Object> map = bp.getRequestContext();

			map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointDocumentService);
			
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
			gwfb.setEndPoint(endpointDocumentService);
			gwfb.setWrappedInterface(DocumentServicePort.class);
			gwfb.setPort(port);
			port = null;
			gwfb.setTokenRetryManager(trm);

			Object o = gwfb.create();
			documentServicePort = (DocumentServicePort) o;

			logger.debug(prf + " DocumentServicePort= "+documentServicePort);
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


	/**
	 * PK : metodo FAKE per evitare che la classificazione di rompa
	 * perche' non c'e' l'alberatura su Doqui
	 * 
	 * @param datiActa
	 * @return
	 * @throws Exception
	 */
	public String classificaDocumento_test(DatiXActaDTO datiActa) throws Exception {
		String prf = "[ActaManager::classificaDocumento] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + " datiActa="+datiActa);
		String codClassificazione = "FAKE";
		logger.info(prf + " END");
		return codClassificazione;
	}
	
	public String classificaDocumento(DatiXActaDTO datiActa) throws Exception {
		String prf = "[ActaManager::classificaDocumento] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + " datiActa="+datiActa);
		
		String codClassificazione = null;
		Long start=System.currentTimeMillis();
		try {
			
			// STEP 1) inizializzazione connessione ad Acta
			inizalizzaActa();
			
			PropertyFilterType propertyFilterType=new PropertyFilterType();
			propertyFilterType.setFilterType(EnumPropertyFilter.ALL);
			Integer maxItems = null;
			Integer skipCount = 0;
			
			
			// STEP 2) ricerca dati "Nodo root"
			logger.info(prf + "cerco il PrincipalExt ");	
			PrincipalExtResponseType principalMaster = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
			
			logger.info(prf + "cerco il PrincipalId ");
			PrincipalIdType userPrincipalId = null;
			if(principalMaster!=null ) {
				userPrincipalId = principalMaster.getPrincipalId();
				logger.info(prf + "userPrincipalId="+userPrincipalId);
			} else {
				logger.warn(prf + "principalMaster nullo ");
				throw new Exception("principalMaster nullo");
			}

			
			// STEP 3) ricerca del fascicolo per parola chiave
			QueryableObjectType target = new QueryableObjectType();
			target.setObject(EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE.value());
//			target.setObject(EnumObjectType.FASCICOLO_REALE_LIBERO_PROPERTIES_TYPE.value());
			logger.info(prf + "target = " + EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE.value());
			
			ObjectIdType objectIDFascicolo = getObjectID( datiActa.getParolaChiave() , propertyFilterType, maxItems,
					skipCount, userPrincipalId, target, "paroleChiave");
			if(objectIDFascicolo!=null) {
				// trovato fascicolo della domanda
				logger.info(prf + "objectIDFascicolo="+objectIDFascicolo.getValue());
			}else {
				logger.error("Impossibile trovare fascicolo della domanda numero ("+datiActa.getIdDomanda()+") con parola chiave ["
						+datiActa.getParolaChiave()+"]");
				throw new Exception("fascicolo della domanda non trovato");
			}

			
			// STEP 4) ricerca del sotto-fascicolo appartenente al fascicolo trovato ed avente una data descrizione
			QueryableObjectType target2 = new QueryableObjectType();
			target2.setObject(EnumFolderObjectType.SOTTOFASCICOLO_PROPERTIES_TYPE.value());
			
					
			ObjectIdType objectIDSottoFascicolo = getObjectIByDescrAndParentNode( datiActa.getDescrFascicolo(), propertyFilterType, maxItems,
				    skipCount, userPrincipalId, target2, "descrizione", objectIDFascicolo);
			if(objectIDSottoFascicolo!=null) {
				// trovato sotto-fascicolo della domanda
				logger.info(prf + "objectIDSottoFascicolo="+objectIDSottoFascicolo.getValue());
			}else {
				logger.error("Impossibile trovare il sotto-fascicolo ("+datiActa.getDescrFascicolo()+") della domanda numero ("+datiActa.getIdDomanda()+")");
				throw new Exception("sottofascicolo della domanda non trovato");
			}
			
			
			// STEP 5) popolo i metadati del documento da caricare 
			DocumentoArchivisticoIRC datiCreazione = popolaMetadati(objectIDSottoFascicolo.getValue(), userPrincipalId, datiActa);
			logger.info(prf + " datiCreazione="+datiCreazione);
		
			
			// STEP 6) carico documento e metadati nel sotto-fascicolo trovato
			IdentificatoreDocumento idDoc = creaDocumentoActa(userPrincipalId, datiCreazione);
		
			if(idDoc!=null && idDoc.getObjectIdClassificazione()!=null)
				codClassificazione = idDoc.getObjectIdClassificazione().getValue();
				
		} catch (Exception e) {
			logger.error(" Exception " + e.getMessage());
			e.printStackTrace();
			
			String msg = e.getMessage();
			if(msg != null && msg.length()>4000)
				msg = msg.substring(0, 4000);
			
			creaLogProtocollazione(msg, "CLASSIFICAZIONE", datiActa.getIdDocIndex(), datiActa.getUtenteCollegatoId(), (System.currentTimeMillis()-start), "N");
			throw e;
		}
		
		creaLogProtocollazione("OK", "CLASSIFICAZIONE", datiActa.getIdDocIndex(), datiActa.getUtenteCollegatoId(), (System.currentTimeMillis()-start), "S");
		logger.info(prf + " END");
		return codClassificazione;
	}


    private void creaLogProtocollazione(String msg, String metodo, Long idDocIndex, Long idUtente, Long durata, String esito) {
    	String prf = "[ActaManager::creaLogProtocollazione] ";
		logger.info(prf + " BEGIN");
		
		logger.info(prf + " msg=["+msg+"], metodo="+metodo+", idDocIndex="+idDocIndex + ", idUtente="+idUtente+", durata="+durata+", esito="+esito);
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO PBANDI.PBANDI_T_LOG_PROTOCOLLAZIONE ");
			sql.append("(ID_LOG, MESSAGGIO, DT_LOG, METODO, ID_DOCUMENTO_INDEX, ID_UTENTE, DURATION, FLAG_ESITO) ");
			sql.append( "VALUES ( ");
			sql.append( " SEQ_PBANDI_T_LOG_PROTOCOLL.nextval, ?, sysdate, ?, ?, ?, ?, ?");
			sql.append( " )");
		
			Object[] params = new Object[6];
			params[0] = msg;
			params[1] = metodo;
			params[2] = idDocIndex;
			params[3] = idUtente;
			params[4] = durata;
			params[5] = esito; //S o N
			
			getJdbcTemplate().update(sql.toString(), params);
			
			logger.info(prf+"inserito record PBANDI_T_LOG_PROTOCOLLAZIONE ");
			
		} catch (Exception e) {
			logger.error(prf+" ERRORE: ", e);
		}
    }

	private ObjectIdType getRegistroId(
			DatiXActaDTO datiActa,
			PrincipalIdType userPrincipalId,
			ObjectIdType aooIdCyp) throws Exception {

		String pr = "[ProtocolManager::getRegistroId] ";
		logger.info(pr + " BEGIN");
		ObjectIdType registroId=null;
		Long idChiamata= null;
		try{
			
			logger.info(pr + " search for year : "+DateUtil.getAnno());
			
			PropertyFilterType propertyFilterType= new PropertyFilterType();
			propertyFilterType.setFilterType(EnumPropertyFilter.ALL);
			
			logger.info(pr + " parametri passati a officialBookServicePort.getRegistries()");
			logger.info(pr + " initialRepositoryObjectIdType.getValue() vale: " + getRepositoryId().getValue());
			logger.info(pr + " userPrincipalId vale: " + userPrincipalId.getValue());
			logger.info(pr + " aooIdCyp vale: " + aooIdCyp.getValue());
			logger.info(pr + " anno vale: " + DateUtil.getAnno());
			idChiamata = logMonitoraggioService.trackCallPre(27L,datiActa.getUtenteCollegatoId(), "R", 53L, 106188L,getRepositoryId(), userPrincipalId, aooIdCyp,
					DateUtil.getAnno());
			PagingResponseType registries = officialBookServicePort.getRegistries(getRepositoryId(), userPrincipalId, aooIdCyp,
				DateUtil.getAnno(), propertyFilterType); // calcolare il registro arg4 pu? essere null
			
			logMonitoraggioService.trackCallPost(idChiamata, "OK" , "200","", registries);
			if (registries!=null && registries.getObjectsLength()>0){
				 logger.info(pr + "Numero registri=" + registries.getObjectsLength());
				 registroId=registries .getObjects(0).getObjectId();
			}else{
				String msg="Errore bloccante per idDocumentoIndex: "+datiActa.getIdDocIndex()+": officialBookServicePort.getRegistries() non ha restituito nulla, non posso protocollare " ;
				logger.info(pr + msg);
//				manager.getFindomDAO().logProtocollazione(UTENTE, documentoIndexVO.getIdDocumentoIndex().longValue(), "N", PROTOCOLLAZIONE, 0, msg  );
			} 
		}catch (it.doqui.acta.acaris.officialbookservice.AcarisException e) {
			logMonitoraggioService.trackCallPost(idChiamata, "KO" ,e.getMessage(), "500", null);
			String msg="Errore per idDocumentoIndex: "+datiActa.getIdDocIndex()+" ,non posso protocollare :officialBookServicePort.getRegistries: "+ e.getMessage();
			logger.error( pr + msg +" :"+e.getMessage()+"\n\n", e);
//			manager.getFindomDAO().logProtocollazione(UTENTE, documentoIndexVO.getIdDocumentoIndex().longValue(), "N", PROTOCOLLAZIONE, 0, msg  );
		}
		logger.info(pr + " END");
		return registroId;
	}
    
	private static final String AOO_PROPERTIES_TYPE = "AOOPropertiesType_";
	private static final String STRUTTURA_PROPERTIES_TYPE = "StrutturaPropertiesType_";
	private static final String NODO_PROPERTIES_TYPE = "NodoPropertiesType_";
	private static final int ID_RUOLO_CORRISPONDENTE = 1; // per competenza
	
	/**
	 * PK : Metodo FAKE per evitare che la protocollazione si rompa
	 * perche' non esiste l'alberatura su Doqui
	 * 
	 * @param datiActa
	 * @return
	 * @throws Exception
	 */
	public String protocollaDocumento_test(DatiXActaDTO datiActa) throws Exception {
		String prf = "[ActaManager::protocollaDocumento] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + " datiActa="+datiActa);
		Long start=System.currentTimeMillis();
		String codiceProtocollo = "FAKE";
		logger.info(prf + " END");
		return codiceProtocollo;
	}
	
	public String protocollaDocumento(DatiXActaDTO datiActa) throws Exception {
		String prf = "[ActaManager::protocollaDocumento] ";
		logger.info(prf + " BEGIN");
		logger.info(prf + " datiActa="+datiActa);
		Long start=System.currentTimeMillis();
		String codiceProtocollo = null;
		Long idChiamata = null;
		try {
			// STEP 1) inizializzazione connessione ad Acta
			inizalizzaActa();
			
			// STEP 2) getGeneralPrincipal
			//PrincipalIdType generalPrincipalId =null;
			
			// STEP 2) ricerca dati "Nodo root"
			logger.info(prf + "cerco il PrincipalExt ");	
			PrincipalExtResponseType principalMaster = getPrincipalExt(getRepositoryId().getValue(), codiceFiscale, idAoo, idStruttura, idNodo, actaUtenteAppkey, backofficeServicePort);
			
			logger.info(prf + "cerco il PrincipalId ");
			PrincipalIdType userPrincipalId = null;
			if(principalMaster!=null ) {
				userPrincipalId = principalMaster.getPrincipalId();
				logger.info(prf + "userPrincipalId="+userPrincipalId);
			} else {
				logger.warn(prf + "principalMaster nullo ");
				throw new Exception("principalMaster nullo");
			}
						
			logger.info(prf + " protocollo il documento avente idClassificazione: "+datiActa.getCodiceClassificazione());
			
			// controllo che il documento non sia gia' protocollato
			
			ProtocollazioneDocumentoEsistente protocollazioneDocumentoEsistente = new ProtocollazioneDocumentoEsistente();
			
			ObjectIdType aooIdCyp = new ObjectIdType();
			aooIdCyp.setValue(AcarisUtils.pseudoCipher(AOO_PROPERTIES_TYPE+idAoo));
			
			ObjectIdType registroId = getRegistroId( datiActa, userPrincipalId, aooIdCyp);
			if(registroId==null) {
				logger.warn(prf+"Impossibile protocollare documento " + datiActa.getIdDocIndex() + ", nessun registro trovato.");
				throw new Exception("Impossibile protocollare documento " + datiActa.getIdDocIndex() + ", nessun registro trovato.");
			}
			protocollazioneDocumentoEsistente.setRegistroId(registroId);
			
			ObjectIdType idStrutturaCyp = new ObjectIdType();
			idStrutturaCyp.setValue(AcarisUtils.pseudoCipher(STRUTTURA_PROPERTIES_TYPE + idStruttura));
			
			ObjectIdType idNodoCyp = new ObjectIdType();
			idNodoCyp.setValue(AcarisUtils.pseudoCipher(NODO_PROPERTIES_TYPE + idNodo));
			
			RiferimentoSoggettoEsistente infoSoggetto = new RiferimentoSoggettoEsistente();
			infoSoggetto.setSoggettoId( idNodoCyp);
			logger.info(prf+"infoSoggetto.setSoggettoId( idNodoCyp) : "+idNodoCyp.getValue());
			infoSoggetto.setTipologia(EnumTipologiaSoggettoAssociato.NODO);
			
			ObjectIdType idNodoProtocollanteCyp = new ObjectIdType();
			idNodoProtocollanteCyp.setValue(AcarisUtils.pseudoCipher(NODO_PROPERTIES_TYPE + idNodo));
			
			InfoCreazioneCorrispondente infoCreazioneCorrispondenteMittente = new InfoCreazioneCorrispondente();
//				logger.info(prf+" infoCreazioneCorrispondenteMittente.setDenominazione(documentoIndexVO.getDenominazioneBeneficiario())" +
//				 documentoIndexVO.getDenominazioneBeneficiario());
			infoCreazioneCorrispondenteMittente.setDenominazione(datiActa.getDenominazioneBeneficiario());
     
			InfoCreazioneCorrispondente infoCreazioneCorrispondenteDestinatario = new InfoCreazioneCorrispondente();
			infoCreazioneCorrispondenteDestinatario.setDenominazione("");
			infoCreazioneCorrispondenteDestinatario.setInfoSoggettoAssociato(infoSoggetto);
		
			DestinatarioInterno[] dInterno = new DestinatarioInterno[1];
			DestinatarioInterno destInterno = new DestinatarioInterno();
			destInterno.setCorrispondente(infoCreazioneCorrispondenteDestinatario);
			destInterno.setIdRuoloCorrispondente(ID_RUOLO_CORRISPONDENTE); //
			dInterno[0] = destInterno;
			
			MittenteEsterno[] mEsterno = new MittenteEsterno[1];
			MittenteEsterno mittEsterno = new MittenteEsterno();
			mittEsterno.setCorrispondente(infoCreazioneCorrispondenteMittente);
			mEsterno[0] = mittEsterno;
			
			IdentificazioneProtocollante idProtocollante = new IdentificazioneProtocollante();
			InfoCreazioneRegistrazione infoCreazioneRegistrazione = new InfoCreazioneRegistrazione();
			infoCreazioneRegistrazione.setForzareSeRegistrazioneSimile(true); 
			
			RegistrazioneArrivo regArrivo = new RegistrazioneArrivo();
			regArrivo.setMittenteEsterno(mEsterno);
			regArrivo.setTipoRegistrazione(EnumTipoAPI.ARRIVO);

			idProtocollante.setStrutturaId(idStrutturaCyp);
			idProtocollante.setNodoId(idNodoProtocollanteCyp);
			logger.info(prf+" idProtocollante.setStrutturaId(idStrutturaCyp) : "+idStrutturaCyp.getValue());
			infoCreazioneRegistrazione.setProtocollante(idProtocollante);
			infoCreazioneRegistrazione.setDataDocumento(new Date());
			infoCreazioneRegistrazione.setDestinatarioInterno(dInterno);
			infoCreazioneRegistrazione.setDocumentoRiservato(false);
			infoCreazioneRegistrazione.setOggetto(datiActa.getNomeFile());
			infoCreazioneRegistrazione.setRegistrazioneRiservata(false);

			regArrivo.setInfoCreazione(infoCreazioneRegistrazione);

			protocollazioneDocumentoEsistente = new ProtocollazioneDocumentoEsistente();
			protocollazioneDocumentoEsistente.setAooProtocollanteId(aooIdCyp);
			protocollazioneDocumentoEsistente.setRegistrazioneAPI(regArrivo);
			protocollazioneDocumentoEsistente.setSenzaCreazioneSoggettiEsterni(true);
            ObjectIdType objectIdClassificazioneType= new ObjectIdType();
            objectIdClassificazioneType.setValue(datiActa.getCodiceClassificazione());
			protocollazioneDocumentoEsistente.setClassificazioneId(objectIdClassificazioneType); 
			logger.info(prf + " protocollazioneDocumentoEsistente.setClassificazioneId(objectIdClassificazioneType): "+datiActa.getCodiceClassificazione());
			
			
//				XStream xstream = new XStream();
//	    		String xml = xstream.toXML(initialRepositoryObjectIdType);
//	    		logger.info("[ProtocolManager::protocolDocument] REPOSITORY ID: " + xml);
    		
//	    		xml = xstream.toXML(userPrincipalId);
//	    		logger.info("[ProtocolManager::protocolDocument] USER PRINCIPAL ID: " + xml);
			
//	    		xml = xstream.toXML(protocollazioneDocumentoEsistente);
//	    		logger.info("[ProtocolManager::protocolDocument] DOCUMENTO: " + xml);
			 idChiamata = logMonitoraggioService.trackCallPre(24L,datiActa.getUtenteCollegatoId(), "W", 0L, 0L,getRepositoryId(),userPrincipalId, EnumTipoRegistrazioneDaCreare.PROTOCOLLAZIONE_DOCUMENTO_ESISTENTE);
			IdentificazioneRegistrazione identificazioneRegistrazione = officialBookServicePort.creaRegistrazione(getRepositoryId(),
						userPrincipalId, EnumTipoRegistrazioneDaCreare.PROTOCOLLAZIONE_DOCUMENTO_ESISTENTE, 
						protocollazioneDocumentoEsistente); // registraz in arrivo
			codiceProtocollo = identificazioneRegistrazione.getNumero();
			
			logMonitoraggioService.trackCallPost(idChiamata, "OK" , ""+"","", identificazioneRegistrazione.getNumero());
			
			//MB2019_01_25 ini jira 1155 recupero la data restituita da ACTA in identificazioneRegistrazione che e' senza ore minuti e secondi
//				java.util.Date dataProtocollo = new Date();
//				String annoPerNumProt = ""; //dalla data protocollo che recupero da Acta estraggo l'anno e lo concateno al numero protocollo
//				if (identificazioneRegistrazione!=null && identificazioneRegistrazione.getDataUltimoAggiornamento() !=null){
//					it.doqui.acta.actasrv.dto.acaris.type.common.ChangeTokenType changeToken = identificazioneRegistrazione.getDataUltimoAggiornamento();
//					String dataUltimoAggiornamento = changeToken.getValue();
//					logger.info("[ProtocolManager::protocolDocument]dataUltimoAggiornamento=["+dataUltimoAggiornamento+"]");
//					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");				
//					try {
//						dataProtocollo = formatter.parse(dataUltimoAggiornamento);
//						annoPerNumProt = dataUltimoAggiornamento.substring(6);
//						logger.info(prf + "  ho parsificato la data restituita da ACTA : " + dataProtocollo);
//					} catch (ParseException e) {					
//						String msg="prf + \"  non e' stato possibile parsificare la data protocollo restituita da ACTA" + e.getMessage();
//						logger.error(msg+"\n ");
////						manager.getFindomDAO().logProtocollazione(UTENTE, documentoIndexVO.getIdDocumentoIndex().longValue(), "N", PROTOCOLLAZIONE, (System.currentTimeMillis()-start),msg);
//						throw new Exception(msg);
//					}
//				}
			//MB2019_01_25 fine	
			
			logger.info(prf + " protocollazione su ACTA eseguita in ms: "+(System.currentTimeMillis()-start)+" [protocollo il documento] OK , num protocollo restituito da ACTA: "+codiceProtocollo);
			//MB2019_01_25 numProtocollo=numProtocollo+"_"+documentoIndexVO.getSettore()+DateUtil.getAnno();
//				numProtocollo=numProtocollo+"_"+documentoIndexVO.getSettore()+annoPerNumProt; //MB2019_01_25
			
			//MB2019_01_25 updateDBForProtocollation(documentoIndexVO, numProtocollo,DateUtil.utilToSqlDate(new Date()));
//				updateDBForProtocollation(documentoIndexVO, numProtocollo,DateUtil.utilToSqlTimeStamp(dataProtocollo));//MB2018_01_25
			
			logger.info("[ProtocolManager::protocolDocument] aggiornamento del DB con i dati di protocollazione eseguita; num protocollo usato: " + codiceProtocollo);
				
				
//				manager.getFindomDAO().logProtocollazione(UTENTE, documentoIndexVO.getIdDocumentoIndex().longValue(), "S", PROTOCOLLAZIONE, (System.currentTimeMillis()-start)," Num protocollo inserito: "+numProtocollo);
//	        } catch (it.doqui.acta.acaris.officialbookservice.AcarisException e) {
//				String msg="[ProtocolManager::protocolDocument]"+datiDocumento+" , errore AcarisException in officialBookServicePort.creaRegistrazione: "+e.getMessage();
//				logger.error("[ProtocolManager::protocolDocument] KO "+msg+"\n");
//				manager.getFindomDAO().logProtocollazione(UTENTE, documentoIndexVO.getIdDocumentoIndex().longValue(), "N", PROTOCOLLAZIONE, (System.currentTimeMillis()-start),msg);
//			} catch (SQLException e) {
//				String msg="[ProtocolManager::protocolDocument]"+datiDocumento+" , errore SQLException in officialBookServicePort.creaRegistrazione:  "+e.getMessage();
//				logger.error("[ProtocolManager::protocolDocument] KO "+msg+"\n");
//				manager.getFindomDAO().logProtocollazione(UTENTE, documentoIndexVO.getIdDocumentoIndex().longValue(), "N", PROTOCOLLAZIONE, (System.currentTimeMillis()-start),msg);
//			}
			
		} catch (Exception e) {
			logMonitoraggioService.trackCallPost(idChiamata, "KO" ,e.getMessage(), "500", null);
			logger.error(" Exception " + e.getMessage());
			e.printStackTrace();
			String msg = e.getMessage();
			if(msg.length()>4000)
				msg = msg.substring(0, 4000);
			creaLogProtocollazione(msg, "PROTOCOLLAZIONE", datiActa.getIdDocIndex(), datiActa.getUtenteCollegatoId(), (System.currentTimeMillis()-start), "N");
			throw e;
		}
		creaLogProtocollazione("OK", "PROTOCOLLAZIONE", datiActa.getIdDocIndex(), datiActa.getUtenteCollegatoId(), (System.currentTimeMillis()-start), "S");
		return codiceProtocollo;
	}
}
