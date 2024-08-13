/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.dto.EsitoOperazioni;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.certificazione.CertificazioneException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbweb.business.MailUtil;
import it.csi.pbandi.pbweb.business.manager.InizializzazioneManager;
import it.csi.pbandi.pbweb.dto.AffidamentoValidazione;
import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoPagamentoDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.RigaNotaDiCreditoItemDTO;
import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.dto.appalti.AppaltoProgetto;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.custom.EsitoValidazioneRendicontazionePiuGreen;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.DocumentoDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.MensilitaDichiarazioneSpesaDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.dto.utils.FileUtil;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.AttributoEsitoDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.DocumentoDiSpesaDematDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.DocumentoDiSpesaDematOldDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.EsitoDichiarazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.EsitoVerificaOperazioneMassivaDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.IdDescCausaleErogDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.IdDescModalitaAgevolazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.InitIntegrazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.InizializzaPopupChiudiValidazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.InizializzaValidazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.MensilitaDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.RigaRicercaDocumentiDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.initDropDownCL_DTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.ValidazioneRendicontazioneDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.EmailRowMapper;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiDiSpesaValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.OperazioneMassivaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ProseguiChiudiValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.RichiediIntegrazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaMensilitaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaParzialmenteDocumentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaOperazioneMassivaRequest;
import it.csi.pbandi.pbweb.integration.vo.Email;
import it.csi.pbandi.pbweb.integration.vo.ModalitaAgevolazioneVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiPiuGreenDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoValidazioneRendicontazione;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IstanzaAttivitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.archivio.ArchivioException;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestioneaffidamenti.GestioneAffidamentiException;
import it.csi.pbandi.pbweb.pbandisrv.exception.validazionerendicontazione.ValidazioneRendicontazioneException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.MailDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.ErrorMessages;
import it.csi.pbandi.pbweb.util.NumberUtil;
import it.csi.pbandi.pbweb.util.StringUtil;
import it.csi.pbandi.pbweb.util.TraduttoreMessaggiPbandisrv;

@Service
public class ValidazioneRendicontazioneDAOImpl extends JdbcDaoSupport implements ValidazioneRendicontazioneDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public ValidazioneRendicontazioneDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	private static ResourceBundle rbMessages = ResourceBundle.getBundle("conf/messages");
	private static ResourceBundle restServices = ResourceBundle.getBundle("conf.restServices");

	@Autowired
	protected it.csi.pbandi.pbweb.business.manager.DocumentoManager documentoManager;

	@Autowired
	DecodificheDAOImpl decodificheDAOImpl;

	@Autowired
	DocumentoDiSpesaDAOImpl documentoDiSpesaDAOImpl;

	@Autowired
	InizializzazioneManager inizializzazioneManager;

	@Autowired
	protected ProfilazioneDAO profilazioneDao;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl profilazioneBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione.ValidazioneRendicontazioneBusinessImpl validazioneRendicontazioneBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.gestionepagamenti.GestionePagamentiBusinessImpl gestionePagamentiBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.gestionevocidispesa.GestioneVociDiSpesaBusinessImpl gestioneVociDiSpesaBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.dichiarazionedispesa.DichiarazioneDiSpesaBusinessImpl dichiarazioneDiSpesaBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.gestioneappalti.GestioneAppaltiBusinessImpl gestioneAppaltiBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.checklist.CheckListBusinessImpl checkListBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.digitalsign.DigitalSignBusinessImpl digitalSignBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.checklisthtml.ChecklistHtmlBusinessImpl checklistHtmlBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.gestioneaffidamenti.GestioneAffidamentiBusinessImpl gestioneAffidamentiBusinessImpl;

	@Autowired
	it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil beanUtil;

	@Autowired
	MailDAO mailDAO;

	@Autowired
	protected MailUtil mailUtil;

	@Autowired
	private GenericDAO genericDAO;

	/*
	 * @Override public String getSvilConstant() throws Exception { String prf =
	 * "[ValidazioneRendicontazioneDAOImpl::getSvilConstant] "; LOG.info(prf +
	 * " BEGIN");
	 * 
	 * StringBuilder sql = new StringBuilder();
	 * 
	 * String res = "None";
	 * 
	 * try {
	 * 
	 * sql.append("SELECT pcc.VALORE \r\n" + "FROM PBANDI_C_COSTANTI pcc \r\n" +
	 * "WHERE pcc.ATTRIBUTO = 'SVILUPPO_FINP'");
	 * 
	 * res = getJdbcTemplate().queryForObject(sql.toString(), String.class);
	 * 
	 * 
	 * } catch (Exception e) { LOG.error(prf+" ERRORE durante getSvilConstant: ",
	 * e); throw new DaoException("Errore durante getSvilConstant.", e); } finally {
	 * LOG.info(prf+" END"); }
	 * 
	 * return res; }
	 */

	@Override
	public InizializzaValidazioneDTO inizializzaValidazione(Long idDichiarazioneDiSpesa, Long idProgetto,
			Long idBandoLinea, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::inizializzaValidazione] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idProgetto = " + idProgetto
				+ "; idBandoLinea = " + idBandoLinea + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo
				+ "; idUtente = " + idUtente);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}

		InizializzaValidazioneDTO result = new InizializzaValidazioneDTO();
		try {

			DatiProgettoInizializzazioneDTO datiProgetto = inizializzazioneManager.completaDatiProgetto(idProgetto);
			if (datiProgetto != null) {
				result.setCodiceVisualizzatoProgetto(datiProgetto.getCodiceVisualizzato());
			}

			// La combo del task la visualizzo solo se e' abilitata la regola BR16.
			Boolean taskVisibile = profilazioneBusinessImpl.isRegolaApplicabileForProgetto(idUtente, idIride,
					idProgetto, RegoleConstants.BR16_GESTIONE_CAMPO_TASK);
			result.setTaskVisibile(taskVisibile);
			if (taskVisibile) {
				result.setElencoTask(decodificheDAOImpl.elencoTask(idProgetto, idUtente, null));
			}

			boolean isInvioDigitale = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
					idBandoLinea, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE)
					|| profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride, idBandoLinea,
							RegoleConstants.BR51_UPLOAD_ALLEGATI_SPESA)
					|| profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride, idBandoLinea,
							RegoleConstants.BR52_UPLOAD_ALLEGATI_QUIETANZA)
					|| profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride, idBandoLinea,
							RegoleConstants.BR53_UPLOAD_ALLEGATI_GENERICI);
			result.setRegolaInvioDigitale(isInvioDigitale ? "S" : "N");

			result.setTipiDocumentoSpesa(decodificheDAOImpl.tipiDocumentoSpesa());

			result.setTipiFornitore(decodificheDAOImpl.ottieniTipologieFornitore());

			result.setStatiDocumentoSpesa(decodificheDAOImpl.statiDocumentoDiSpesaValidazioneRendicontazione());

			// Indica se mostrare o no l'icona di modifica per i documenti di spesa.
			result.setDocumentoSpesaModificabile(
					profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, UseCaseConstants.UC_OPEREN012));

			if (decodificheDAOImpl.dichiarazioneSpesaFinaleConCFP(idDichiarazioneDiSpesa)) {
				// E' una DS finale: trova l'id della CFP.
				Long idCFP = decodificheDAOImpl.idComunicazioneFineProgetto(idProgetto);
				if (idCFP != null) {
					String sqlDt = "SELECT DT_COMUNICAZIONE FROM PBANDI_T_COMUNICAZ_FINE_PROG WHERE ID_COMUNICAZ_FINE_PROG = "
							+ idCFP;
					LOG.info("\n" + sqlDt);
					Date dtCFP = queryForObject(sqlDt, Date.class);

					result.setIdComunicazFineProg(idCFP);
					if (dtCFP != null) {
						try {
							result.setDtComunicazFineProg(DateUtil.getDate(dtCFP));
						} catch (Exception e) {
							LOG.error(prf + "Errore nella trasformazione della data CFP " + dtCFP);
						}
					}
				}

			}

			// Per le DS finali è l'id doc index della CFP, altrimenti è l'id doc index
			// della DS.
			Long idDocumentoIndex = this.trovaIdDocumentoIndex(idDichiarazioneDiSpesa, idProgetto);
			result.setIdDocumentoIndex(idDocumentoIndex);

			if (idDocumentoIndex != null) {

				PbandiTDocumentoIndexVO vo = documentoManager.trovaTDocumentoIndex(idDocumentoIndex);

				if (vo != null) {

					result.setNomeFile(vo.getNomeFile());

					// S = Documento cartaceo, N\null = Digitale.
					result.setFlagFirmaCartacea(vo.getFlagFirmaCartacea());

					// firmata \ non firmata.
					BigDecimal idStatoDocumento = vo.getIdStatoDocumento();
					if (idStatoDocumento != null) {

						String descStatoDoc = decodificheDAOImpl.descrizioneDaId("PBANDI_D_STATO_DOCUMENTO_INDEX",
								"ID_STATO_DOCUMENTO", "DESC_BREVE", idStatoDocumento.longValue());

						if (Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_INVIATO.equalsIgnoreCase(descStatoDoc)
								|| Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_CLASSIFICATO
										.equalsIgnoreCase(descStatoDoc)
								|| Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_PROTOCOLLATO
										.equalsIgnoreCase(descStatoDoc)) {
							result.setStatoDichiarazione("firmata");
						} else {
							result.setStatoDichiarazione("non firmata");
						}

					}

					// Trova il protocollo.
					String sqlDocProt = "SELECT NUM_PROTOCOLLO FROM PBANDI_T_DOC_PROTOCOLLO WHERE ID_DOCUMENTO_INDEX = "
							+ idDocumentoIndex;
					LOG.info("\n" + sqlDocProt);
					String protocollo = queryForObject(sqlDocProt, String.class);
					result.setProtocollo(protocollo);

				}
			}

			// Se esiste già una richiesta di integrazione aperta (non evasa dal
			// Beneficiario) :
			// - si nasconde il bottone 'richiedi integrazione'.
			// - si mostra il msg "ATTENZIONE! è presente una richiesta di integrazione."
			result.setEsisteRichiestaIntegrazioneAperta(
					this.esisteRichiestaIntegrazioneAperta(idDichiarazioneDiSpesa, idUtente, idIride));

			/*
			 * Se l'integrazione esiste, restituisce la lista delle date di invio. Allo
			 * stato attuale dello svilupo, non so se una dichiarazione di spesa corrisponde
			 * a più richieste di integrazione; accedendo alla tabella attraverso la chiave
			 * esterna e non primaria (solo questo dato ho disponibile al momento), non so
			 * se mi tiri fuori più record, dandomi più date.
			 */
			List<String> dateInvio = new ArrayList<String>();
			if (result.getEsisteRichiestaIntegrazioneAperta()) {

				String findDate = "SELECT ptis.DATA_INVIO \r\n" + "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis\r\n"
						+ "WHERE ptis.ID_DICHIARAZIONE_SPESA = " + idDichiarazioneDiSpesa;
				dateInvio = getJdbcTemplate().queryForList(findDate, String.class);
			}
			result.setDateInvio(dateInvio);

			// EnteDestinatarioComunicazioni.
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EnteDestinatarioComunicazioniDTO ente;
			ente = validazioneRendicontazioneBusinessImpl.findEnteDestinatarioComunicazioniByIdProgetto(idUtente,
					idIride, idProgetto);
			if (ente != null) {
				result.setIdEnteCompetenza(ente.getIdEnteCompetenza());
				result.setDescBreveEnte(ente.getDescBreveEnte());
			}

			// +Green
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO datiGeneraliDTO;
			datiGeneraliDTO = gestioneDatiGeneraliBusinessImpl.integraDatiPiuGreenPerNuovaVersione(idUtente, idIride,
					idProgetto, idDichiarazioneDiSpesa);
			if (datiGeneraliDTO != null) {
				result.setIdComunicazFineProgPiuGreen(datiGeneraliDTO.getIdComunicazFineProgPiuGreen());
				result.setDtComunicazFineProgPiuGreen(datiGeneraliDTO.getDtComunicazionePiuGreen());
				result.setIdDocumentoIndexPiuGreen(datiGeneraliDTO.getIdDocIndexComunicFineProgettoPiuGreen());
				result.setStatoDichiarazionePiuGreen(datiGeneraliDTO.getStatoDichiarazionePiuGreen());
				result.setFlagFirmaCartaceaPiuGreen(datiGeneraliDTO.getFlagFirmaCartaceaPiuGreen());
				result.setProtocolloPiuGreen(datiGeneraliDTO.getProtocolloPiuGreen());

				// Trova il nome del file.
				if (datiGeneraliDTO.getIdDocIndexComunicFineProgettoPiuGreen() != null) {
					String sqlFile = "SELECT NOME_FILE FROM PBANDI_T_DOCUMENTO_INDEX WHERE ID_DOCUMENTO_INDEX = "
							+ datiGeneraliDTO.getIdDocIndexComunicFineProgettoPiuGreen();
					LOG.info("\n" + sqlFile);
					String nomeFile = queryForObject(sqlFile, String.class);
					result.setNomeFilePiuGreen(nomeFile);
				}
			}

			// check VALIDA, INVALIDA, RESPINGI e TUTTO sono visibili nella validazione
			// massiva.
			result.setValidazioneMassivaAbilitata(
					profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, "OPEREN040"));

			// Indice se mostrare o no il bottone "Richiedi Integrazione".
			boolean b = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, "OPEREN039");
			result.setRichiestaIntegrazioneAbilitata(b);
			result.setChiusuraValidazioneAbilitata(b);

			// Determina se la ds è finale.
			String sqlDs = "SELECT ID_TIPO_DICHIARAZ_SPESA FROM PBANDI_T_DICHIARAZIONE_SPESA WHERE ID_DICHIARAZIONE_SPESA = "
					+ idDichiarazioneDiSpesa;
			LOG.info("\n" + sqlDs);
			Long idStato = queryForObject(sqlDs, Long.class);
			result.setDichiarazioneDiSpesaFinale(idStato.intValue() == 3);

			LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la inizializzazione della validazione della rendicontazione: ", e);
			throw new DaoException("Errore durante la inizializzazione della validazione della rendicontazione.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public InitIntegrazioneDTO initIntegrazione(Long idDichiarazioneSpesa) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::initIntegrazione] ";
		LOG.info(prf + " BEGIN");
		// LOG.info(prf + "idDichiarazioneDiSpesa = "+idDichiarazioneDiSpesa+";
		// idProgetto = "+idProgetto+"; idBandoLinea = "+idBandoLinea+"; idSoggetto =
		// "+idSoggetto+"; codiceRuolo = "+codiceRuolo+"; idUtente = "+idUtente);

		InitIntegrazioneDTO integra = new InitIntegrazioneDTO();

		try {
			String queryInt = "SELECT \n" + "ptis.DT_NOTIFICA,\n" + "ptis.DATA_RICHIESTA,\n"
					+ "ptis.ID_INTEGRAZIONE_SPESA,\n" + "pdsri.ID_STATO_RICHIESTA,\n" + "pdsri.DESC_STATO_RICHIESTA,\n"
					+ "ptis.DATA_INVIO\n" + "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis\n"
					+ "LEFT JOIN PBANDI_D_STATO_RICH_INTEGRAZ pdsri ON ptis.ID_STATO_RICHIESTA = pdsri.ID_STATO_RICHIESTA \n"
					+ "WHERE ptis.ID_DICHIARAZIONE_SPESA = ?\n" + "ORDER BY DATA_RICHIESTA DESC";
			InitIntegrazioneDTO temp = getJdbcTemplate().query(queryInt, rs -> {
				InitIntegrazioneDTO elencoDati = new InitIntegrazioneDTO();

				if (rs.next()) {
					Date date = rs.getDate("DT_NOTIFICA");
					if (date != null) {
						elencoDati.setDataNotifica(date.toString());
					}

					date = rs.getDate("DATA_RICHIESTA");
					if (date != null) {
						elencoDati.setDataRichiesta(date.toString());
					}

					elencoDati.setIdIntegrazioneSpesa(rs.getLong("ID_INTEGRAZIONE_SPESA"));
					elencoDati.setIdStatoRichiesta(rs.getLong("ID_STATO_RICHIESTA"));
					elencoDati.setStatoRichiesta(rs.getString("DESC_STATO_RICHIESTA"));

					date = rs.getDate("DATA_INVIO");
					if (date != null) {
						elencoDati.setDataInvio(date.toString());
					}
				}
				return elencoDati;
			}, idDichiarazioneSpesa);

			integra.setDataRichiesta(temp.getDataRichiesta());
			integra.setDataNotifica(temp.getDataNotifica());
			integra.setIdIntegrazioneSpesa(temp.getIdIntegrazioneSpesa());
			integra.setIdStatoRichiesta(temp.getIdStatoRichiesta());
			integra.setStatoRichiesta(temp.getStatoRichiesta());
			integra.setDataInvio(temp.getDataInvio());

		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}

		integra.setEsito(true);
		return integra;
	}

	@Override
	// Ex:
	// CPBEChiudiValidazioneDocumentoDiSpesa.inizializzaCPChiudiValidazioneDocumentoDiSpesa()
	public InizializzaPopupChiudiValidazioneDTO inizializzaPopupChiudiValidazione(Long idDichiarazioneDiSpesa,
			Long idProgetto, Long idBandoLinea, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::InizializzaPopupChiudiValidazioneDTO] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idProgetto = " + idProgetto
				+ "; idBandoLinea = " + idBandoLinea + "; idUtente = " + idUtente);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}

		InizializzaPopupChiudiValidazioneDTO result = new InizializzaPopupChiudiValidazioneDTO();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.InfoDichiarazioneSpesaDTO infoDichiarazioneSpesaDTO;
			infoDichiarazioneSpesaDTO = dichiarazioneDiSpesaBusinessImpl.getInfoDichiarazioneDiSpesa(idUtente, idIride,
					idDichiarazioneDiSpesa, idProgetto);

			Boolean isProgettoAperto = !infoDichiarazioneSpesaDTO.getIsProgettoChiuso();

			String noteChiusuraValidazione = dichiarazioneDiSpesaBusinessImpl.findNoteChiusuraValidazione(idUtente,
					idIride, idDichiarazioneDiSpesa);
			if (noteChiusuraValidazione == null) {
				noteChiusuraValidazione = "";
			}
			result.setNote(noteChiusuraValidazione);

			result.setCheckDsIntegrativaVisibile(false); // è solo una inizializzazione; potrebbe diventare true in
															// seguito.
			result.setCheckDsIntegrativaSelezionatoENonModificabile(false); // come sopra.

			Boolean isRegolaDichIntegrativaDisponibile = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(
					idUtente, idIride, idBandoLinea, RegoleConstants.BR36_DICHIARAZIONE_INTEGRATIVA_DISPONIBILE);

			ArrayList<String> messaggi = new ArrayList<String>();

			if (ObjectUtil.isTrue(infoDichiarazioneSpesaDTO.getIsIntermedia())) {

				if (ObjectUtil.isTrue(infoDichiarazioneSpesaDTO.getDichiarazioneFinalePresente())
						&& ObjectUtil.isTrue(infoDichiarazioneSpesaDTO.getPagamentiDaRespingere())) {
					messaggi.add(
							"Per la validazione della spesa corrente esistono pagamenti con lo stato da respingere e il beneficiario ha gi\u00E0 inviato una dichiarazione di spesa finale.");
					if (isRegolaDichIntegrativaDisponibile)
						messaggi.add("Sar\u00E0 necessaria una dichiarazione di spesa integrativa.");
				}

			} else if (ObjectUtil.isTrue(infoDichiarazioneSpesaDTO.getIsFinaleOIntegrativa())) {

				if (isRegolaDichIntegrativaDisponibile && isProgettoAperto) {
					result.setCheckDsIntegrativaVisibile(true);
				}

				if (ObjectUtil.isTrue(infoDichiarazioneSpesaDTO.getPagamentiDaRespingere())) {

					messaggi.add(
							"Per la validazione della dichiarazione corrente esistono pagamenti con lo stato <b>da respingere</b>.");

					if (isRegolaDichIntegrativaDisponibile && isProgettoAperto) {

						result.setCheckDsIntegrativaSelezionatoENonModificabile(true);
						messaggi.add("Sar\u00E0 necessaria una dichiarazione di spesa integrativa.");

					}

				} else if (ObjectUtil.isTrue(infoDichiarazioneSpesaDTO.getPagamentiRespinti())) {

					messaggi.add("Per il progetto esistono pagamenti con lo stato <b>respinto</b>.");

					if (isRegolaDichIntegrativaDisponibile && isProgettoAperto) {

						result.setCheckDsIntegrativaSelezionatoENonModificabile(true);
						messaggi.add("Sar\u00E0 necessaria una dichiarazione di spesa integrativa.");

					}

				} else if ((infoDichiarazioneSpesaDTO.getAltreDichiarazioniDiSpesaIntermedieNonValidate())) {

					messaggi.add(
							"Per il progetto corrente, esiste almeno una dichiarazione di spesa intermedia la cui validazione non \u00E8 stata ancora chiusa.");

					if (isRegolaDichIntegrativaDisponibile && isProgettoAperto) {

						result.setCheckDsIntegrativaSelezionatoENonModificabile(true);
						messaggi.add("Sar\u00E0 necessaria una dichiarazione di spesa integrativa.");

					}
				}

			}

			result.setMessaggi(messaggi);

			// Jira PBANDI-2768
			result.setWarningImportoMaggioreAmmesso(this.validatoMaggioreAmmesso(idProgetto));
			// testo del warning: "Attenzione!<br />Confermando l&rsquo;operazione, almeno
			// una voce di spesa del conto economico risulter&agrave; validata per un
			// importo complessivo maggiore del totale ammesso per la voce di spesa. <br
			// />Pur essendo possibile procedere, si consiglia di visionare il conto
			// economico."

			Long idProcesso = this.idProcesso(idBandoLinea);
			if (idProcesso != null && idProcesso.intValue() == 1) {
				String tipoChecklist = Constants.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_VALIDAZIONE;
				result.setAppalti(this.findAppaltiChecklist(idProgetto, tipoChecklist, idUtente, idIride));
			}

			LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la inizializzazione della popup chiudi validazione: ", e);
			throw new DaoException("Errore durante la inizializzazione della popup chiudi validazione.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public initDropDownCL_DTO initDropDownCL(Long idProgetto) throws Exception {

		String prf = "[ValidazioneRendicontazioneDAOImpl::initDropDownCL] ";
		LOG.info(prf + " BEGIN");

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}

		initDropDownCL_DTO dropdown = new initDropDownCL_DTO();

		try {

			String queryAtt = "SELECT pdavs.ID_ATTRIB_VALID_SPESA, pdavs.DESC_ATTRIB_VALID_SPESA, pdavs.DESC_BREVE_ATTRIB_VALID_SPESA \r\n"
					+ "FROM PBANDI_D_ATTRIBUTO_VALID_SPESA pdavs ";
			dropdown.setAttributi(
					getJdbcTemplate().query(queryAtt, new ResultSetExtractor<ArrayList<AttributoEsitoDTO>>() {
						@Override
						public ArrayList<AttributoEsitoDTO> extractData(ResultSet rs) throws SQLException {
							ArrayList<AttributoEsitoDTO> elencoDati = new ArrayList<>();

							while (rs.next()) {
								AttributoEsitoDTO item = new AttributoEsitoDTO();

								item.setId(rs.getInt("ID_ATTRIB_VALID_SPESA"));
								item.setAttrib(rs.getString("DESC_ATTRIB_VALID_SPESA"));
								item.setAttribBreve(rs.getString("DESC_BREVE_ATTRIB_VALID_SPESA"));
								elencoDati.add(item);
							}
							return elencoDati;
						}
					}));

			String queryEsito = "SELECT pdevs.ID_ESITO_VALID_SPESA, pdevs.DESC_ESITO_VALID_SPESA, pdevs.DESC_BREVE_ESITO_VALID_SPESA \r\n"
					+ "FROM PBANDI_D_ESITO_VALID_SPESA pdevs ";
			dropdown.setEsiti(
					getJdbcTemplate().query(queryEsito, new ResultSetExtractor<ArrayList<EsitoDichiarazioneDTO>>() {
						@Override
						public ArrayList<EsitoDichiarazioneDTO> extractData(ResultSet rs) throws SQLException {
							ArrayList<EsitoDichiarazioneDTO> elencoDati = new ArrayList<>();

							while (rs.next()) {
								EsitoDichiarazioneDTO item = new EsitoDichiarazioneDTO();

								item.setId(rs.getInt("ID_ESITO_VALID_SPESA"));
								item.setEsito(rs.getString("DESC_ESITO_VALID_SPESA"));
								item.setEsitoBreve(rs.getString("DESC_BREVE_ESITO_VALID_SPESA"));
								elencoDati.add(item);
							}
							return elencoDati;
						}
					}));

			String queryModAgev = "SELECT DISTINCT pdma.ID_MODALITA_AGEVOLAZIONE, ptb.ID_LINEA_DI_INTERVENTO, prbli.ID_BANDO,ptd.PROGR_BANDO_LINEA_INTERVENTO, pdma2.DESC_BREVE_MODALITA_AGEVOLAZ, pdma2.DESC_MODALITA_AGEVOLAZIONE\r\n"
					+ "FROM PBANDI_T_PROGETTO ptp\r\n"
					+ "JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
					+ "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "JOIN PBANDI_T_BANDO ptb ON ptb.ID_BANDO = prbli.ID_BANDO \r\n"
					+ "JOIN PBANDI_R_BANDO_MODALITA_AGEVOL prbma ON prbma.ID_BANDO = prbli.ID_BANDO\r\n"
					+ "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = prbma.ID_MODALITA_AGEVOLAZIONE\r\n"
					+ "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma2 ON pdma2.ID_MODALITA_AGEVOLAZIONE = pdma.ID_MODALITA_AGEVOLAZIONE_RIF\r\n"
					+ "JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.id_domanda = ptd.id_domanda\r\n"
					+ "JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.id_conto_economico = ptce.id_conto_economico AND pdma.id_modalita_agevolazione = prcema.id_modalita_agevolazione\r\n"
					+ "WHERE ptp.ID_PROGETTO = ?";

			Object[] args = new Object[] { idProgetto };

			List<IdDescModalitaAgevolazioneDTO> modalita = getJdbcTemplate().query(queryModAgev, args,
					new ResultSetExtractor<ArrayList<IdDescModalitaAgevolazioneDTO>>() {
						@Override
						public ArrayList<IdDescModalitaAgevolazioneDTO> extractData(ResultSet rs) throws SQLException {
							ArrayList<IdDescModalitaAgevolazioneDTO> elencoDati = new ArrayList<>();

							while (rs.next()) {
								IdDescModalitaAgevolazioneDTO item = new IdDescModalitaAgevolazioneDTO();

								item.setIdModalitaAgevolazione(rs.getInt("ID_MODALITA_AGEVOLAZIONE"));
								item.setDescModalitaAgevolazione(rs.getString("DESC_MODALITA_AGEVOLAZIONE"));
								item.setDescBreveModalitaAgevolazione(rs.getString("DESC_BREVE_MODALITA_AGEVOLAZ"));
								elencoDati.add(item);
							}
							return elencoDati;
						}
					});
			dropdown.setModalitaAgevolazione(modalita);

			String queryErogPA = "SELECT COUNT(*)\r\n" + "	FROM PBANDI_T_EROGAZIONE e\r\n"
					+ "	JOIN PBANDI_D_CAUSALE_EROGAZIONE ce2 ON ce2.ID_CAUSALE_EROGAZIONE = e.ID_CAUSALE_EROGAZIONE\r\n"
					+ "	WHERE E.ID_PROGETTO = ? AND DESC_BREVE_CAUSALE = ?";

			args = new Object[] { idProgetto, Constants.DESC_BREVE_CAUSALE_EROGAZIONE_PRIMO_ACCONTO };

			int count = getJdbcTemplate().queryForObject(queryErogPA, args, Integer.class);

			String queryCausaliErog = "SELECT  ID_CAUSALE_EROGAZIONE, DESC_BREVE_CAUSALE, DESC_CAUSALE\r\n"
					+ "FROM PBANDI_D_CAUSALE_EROGAZIONE \r\n" + "WHERE DESC_BREVE_CAUSALE IN (?, ?, ?)\r\n"
					+ "	AND TRUNC(DT_INIZIO_VALIDITA)<= TRUNC(SYSDATE) \r\n"
					+ "	AND  (DT_FINE_VALIDITA IS NULL OR TRUNC(DT_FINE_VALIDITA) >= TRUNC(SYSDATE)) ";

			args = new Object[] { count == 0 ? Constants.DESC_BREVE_CAUSALE_EROGAZIONE_PRIMO_ACCONTO : "",
					Constants.DESC_BREVE_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO,
					Constants.DESC_BREVE_CAUSALE_EROGAZIONE_SALDO };

			List<IdDescCausaleErogDTO> causali = getJdbcTemplate().query(queryCausaliErog, args,
					new ResultSetExtractor<ArrayList<IdDescCausaleErogDTO>>() {
						@Override
						public ArrayList<IdDescCausaleErogDTO> extractData(ResultSet rs) throws SQLException {
							ArrayList<IdDescCausaleErogDTO> elencoDati = new ArrayList<>();

							while (rs.next()) {
								IdDescCausaleErogDTO item = new IdDescCausaleErogDTO();

								item.setIdCausaleErogazione(rs.getInt("ID_CAUSALE_EROGAZIONE"));
								item.setDescCausaleErogazione(rs.getString("DESC_CAUSALE"));
								item.setDescBreveCausaleErogazione(rs.getString("DESC_BREVE_CAUSALE"));
								elencoDati.add(item);
							}
							return elencoDati;
						}
					});
			dropdown.setCausaliErogazione(causali);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la lettura dei dati: ", e);
			throw new DaoException("Errore durante la lettura dei dati.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return dropdown;
	}

	@Override
	public Integer updateCriteri(BigDecimal idAttribValidSpesa, BigDecimal idEsitoValidSpesa,
			BigDecimal idDichiarazioneSpesa) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::updateCriteri]";
		logger.info(prf + "BEGIN");

		int result = 0;
		try {

			String sql = "UPDATE PBANDI.PBANDI_T_DICHIARAZIONE_SPESA\n"
					+ "SET ID_ATTRIB_VALID_SPESA = ?, ID_ESITO_VALID_SPESA = ? WHERE ID_DICHIARAZIONE_SPESA = ?";

			Object[] args = new Object[] { idAttribValidSpesa, idEsitoValidSpesa, idDichiarazioneSpesa };
			result = getJdbcTemplate().update(sql, args);

		} catch (Exception e) {
			logger.error(prf + e);
		}

		logger.info(prf + "END");
		return result;
	}

	@Override
	public String salvaLetteraAccompagnatoria(File filePart, String nomeFile, Boolean visibilitaFile,
			BigDecimal idProgetto, int entita, Long idUtente, BigDecimal idTarget) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::salvaAllegato]";
		logger.info(prf + "BEGIN");

		BigDecimal idTipoDocumentoIndex;
		BigDecimal idEntita;

		switch (entita) {

			case 1: // Avvia iter

				idTipoDocumentoIndex = BigDecimal.valueOf(60);
				idEntita = BigDecimal.valueOf(242);

				break;
			case 2: // lettera accompagnatoria + proposta di erogazione

				idTipoDocumentoIndex = BigDecimal.valueOf(42);
				idEntita = BigDecimal.valueOf(602);

				break;
			case 3: // lettera accompagnatoria + proposta di revoca

				idTipoDocumentoIndex = BigDecimal.valueOf(44);
				idEntita = BigDecimal.valueOf(516);

				break;
			case 4: // lettera accompagnatoria e proposta di revoca + proposta di erogazione
				idTipoDocumentoIndex = BigDecimal.valueOf(44);
				idEntita = BigDecimal.valueOf(516);

				break;

			// idTipoDocuIndex = 33
			// ENTITA pbandi_t_dich_spesa = 63
			// TARGET = idDichiarazioneSpesa

			default:
				return "Indice 'entita' fuori range";
		}

		FileDTO file = new FileDTO();
		String descBreveTipoDocIndex;

		try {
			file.setBytes(FileUtil.getBytesFromFile(filePart));
		} catch (Exception e) {
			return "Errore lettura file";
		}

		descBreveTipoDocIndex = getJdbcTemplate().queryForObject("SELECT DESC_BREVE_TIPO_DOC_INDEX \n"
				+ "FROM PBANDI_C_TIPO_DOCUMENTO_INDEX \n" + "WHERE ID_TIPO_DOCUMENTO_INDEX = ?", String.class,
				idTipoDocumentoIndex);

		Date currentDate = new Date(System.currentTimeMillis());

		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
		if (!visibilitaFile) {
			documentoIndexVO.setFlagVisibileBen("N");
		} else {
			documentoIndexVO.setFlagVisibileBen("S");
		}
		documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		documentoIndexVO.setNomeFile(nomeFile);
		documentoIndexVO.setIdTarget(idTarget);
		documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
		documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
		documentoIndexVO.setIdEntita(idEntita);
		documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
		// PBANDI_T_PROGETTO
		documentoIndexVO.setIdProgetto(idProgetto); // id della PBANDI_T_PROGETTO
		documentoIndexVO.setRepository(descBreveTipoDocIndex);
		documentoIndexVO.setUuidNodo("UUID");

		// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		documentoManager.salvaFile(file.getBytes(), documentoIndexVO);

		// aggiornamentoFlag

		return "OK";
	}

	@Override
	public String salvaChecklistInterna(File filePart, String nomeFile, Boolean visibilitaFile, BigDecimal idProgetto,
			Long idUtente, BigDecimal idTarget, Boolean integrazione) throws Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::salvaCheckListInterna]";
		logger.info(prf + "BEGIN");

		BigDecimal idTipoDocumentoIndex;
		BigDecimal idEntita;

		if (nomeFile == null || nomeFile.isEmpty()) {
			return "OK";
		}

		idTipoDocumentoIndex = BigDecimal.valueOf(33);
		idEntita = BigDecimal.valueOf(integrazione ? 453 : 242);

		FileDTO file = new FileDTO();
		try {
			file.setBytes(FileUtil.getBytesFromFile(filePart));
		} catch (Exception e) {
			LOG.error(prf + "Errore durante la lettura del file",e);
			throw e;
		}
		
		try {
			String descBreveTipoDocIndex = getJdbcTemplate().queryForObject("SELECT DESC_BREVE_TIPO_DOC_INDEX \n"
					+ "FROM PBANDI_C_TIPO_DOCUMENTO_INDEX \n" + "WHERE ID_TIPO_DOCUMENTO_INDEX = ?", String.class,
					idTipoDocumentoIndex);
	
			Date currentDate = new Date(System.currentTimeMillis());
	
			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
			if (!visibilitaFile) {
				documentoIndexVO.setFlagVisibileBen("N");
			} else {
				documentoIndexVO.setFlagVisibileBen("S");
			}
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO.setIdEntita(idEntita);
			documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
			// PBANDI_T_PROGETTO
			documentoIndexVO.setIdProgetto(idProgetto); // id della PBANDI_T_PROGETTO
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");
	
			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
			documentoManager.salvaFile(file.getBytes(), documentoIndexVO);
		} catch (Exception e) {
			LOG.error(prf + "Errore durante il salvataggio della checklist interna",e);
			throw e;
		}
		return "OK";
	}

	@Override
	public String salvaReportValidazione(File filePart, String nomeFile, Boolean visibilitaFile, BigDecimal idProgetto,
			Long idUtente, BigDecimal idTarget, Boolean integrazione) throws Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::salvaReportValidazione]";
		logger.info(prf + "BEGIN");

		BigDecimal idTipoDocumentoIndex;
		BigDecimal idEntita;

		if (nomeFile == null || nomeFile.isEmpty()) {
			return "OK";
		}

		idTipoDocumentoIndex = BigDecimal.valueOf(63);
		idEntita = BigDecimal.valueOf(integrazione ? 453 : 242);

		FileDTO file = new FileDTO();
		try {
			file.setBytes(FileUtil.getBytesFromFile(filePart));
		} catch (Exception e) {
			LOG.error(prf + "Errore durante la lettura del file",e);
			throw e;
		}

		try {
		String descBreveTipoDocIndex = getJdbcTemplate().queryForObject("SELECT DESC_BREVE_TIPO_DOC_INDEX \n"
				+ "FROM PBANDI_C_TIPO_DOCUMENTO_INDEX \n" + "WHERE ID_TIPO_DOCUMENTO_INDEX = ?", String.class,
				idTipoDocumentoIndex);

		Date currentDate = new Date(System.currentTimeMillis());

		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
		if (!visibilitaFile) {
			documentoIndexVO.setFlagVisibileBen("N");
		} else {
			documentoIndexVO.setFlagVisibileBen("S");
		}
		documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
		documentoIndexVO.setNomeFile(nomeFile);
		documentoIndexVO.setIdTarget(idTarget);
		documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
		documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
		documentoIndexVO.setIdEntita(idEntita);
		documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
		// PBANDI_T_PROGETTO
		documentoIndexVO.setIdProgetto(idProgetto); // id della PBANDI_T_PROGETTO
		documentoIndexVO.setRepository(descBreveTipoDocIndex);
		documentoIndexVO.setUuidNodo("UUID");

		// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
		documentoManager.salvaFile(file.getBytes(), documentoIndexVO);
		} catch (Exception e) {
			LOG.error(prf + "Errore durante il salvataggio del report di validazione",e);
			throw e;
		}
		return "OK";
	}

	@Override
	@Transactional
	public String verificaInserimentoPropostaRevoca(Date dataPropostaRevoca, Long idProgetto) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query =
				"SELECT pdma.ID_MODALITA_AGEVOLAZIONE, pdma.ID_MODALITA_AGEVOLAZIONE_RIF " +
						"FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema\n" +
						"JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = prcema.ID_MODALITA_AGEVOLAZIONE \n" +
						"WHERE prcema.ID_CONTO_ECONOMICO IN (\n" +
						"SELECT ID_CONTO_ECONOMICO FROM PBANDI_T_CONTO_ECONOMICO ptce \n" +
						"WHERE ID_DOMANDA IN (\n" +
						"SELECT ID_DOMANDA FROM PBANDI_T_PROGETTO prpsf\n" +
						"WHERE ID_PROGETTO = ?)\n" +
						"AND DT_FINE_VALIDITA IS NULL)\n" +
						"AND pdma.ID_MODALITA_AGEVOLAZIONE_RIF IN (1, 5, 10)";
		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
		List<ModalitaAgevolazioneVO> modalitaAgevolazione = getJdbcTemplate().query(
				query,
				ps -> ps.setLong(1, idProgetto),
				(rs, rowNum) -> {
					ModalitaAgevolazioneVO modalitaAgevolazioneVO = new ModalitaAgevolazioneVO();

					modalitaAgevolazioneVO.setIdModalitaAgevolazione(rs.getLong("ID_MODALITA_AGEVOLAZIONE"));
					modalitaAgevolazioneVO.setIdModalitaAgevolazioneRif(rs.getLong("ID_MODALITA_AGEVOLAZIONE_RIF"));

					return modalitaAgevolazioneVO;
				}
		);

		//Verifica 1, 2 e 3
		for(ModalitaAgevolazioneVO modalitaAgevolaz : modalitaAgevolazione){
			String getDtFrom;
			java.sql.Date dtFrom;
			if(modalitaAgevolaz.getIdModalitaAgevolazioneRif()==1){
				getDtFrom = "SELECT DT_CONTABILE FROM PBANDI_T_EROGAZIONE pte WHERE id_progetto = ? AND ID_MODALITA_AGEVOLAZIONE =? AND ID_CAUSALE_EROGAZIONE ='4'";
				try {
					LOG.info(prf + "\nQuery: \n\n" + getDtFrom + "\n\n");
					dtFrom = getJdbcTemplate().queryForObject(getDtFrom, new Object[]{idProgetto, modalitaAgevolaz.getIdModalitaAgevolazione()}, java.sql.Date.class);
				}catch (EmptyResultDataAccessException e){
					dtFrom = null;
				}
			}else{
				getDtFrom =
						"SELECT DISTINCT DT_DICHIARAZIONE\n" +
								"FROM PBANDI_T_DICHIARAZIONE_SPESA ptds \n" +
								"WHERE ID_PROGETTO = ? \n" +
								"AND ID_TIPO_DICHIARAZ_SPESA IN (\n" +
								"\tSELECT DISTINCT ID_TIPO_DICHIARAZ_SPESA \n" +
								"\tFROM PBANDI_T_DICHIARAZIONE_SPESA \n" +
								"\tWHERE ID_PROGETTO = ?\n" +
								"\tAND ID_TIPO_DICHIARAZ_SPESA = (\n" +
								"\t\tSELECT max(ID_TIPO_DICHIARAZ_SPESA) \n" +
								"\t\tFROM PBANDI_T_DICHIARAZIONE_SPESA \n" +
								"\t\tWHERE ID_PROGETTO = ?\n" +
								"\t)\n" +
								") \n" +
								"AND ID_TIPO_DICHIARAZ_SPESA IN ('2','3','4') \n" +
								"AND DT_DICHIARAZIONE IN (\n" +
								"\tSELECT DISTINCT DT_DICHIARAZIONE \n" +
								"\tFROM PBANDI_T_DICHIARAZIONE_SPESA pdtds \n" +
								"\tWHERE DT_DICHIARAZIONE = (\n" +
								"\t\tSELECT max(DT_DICHIARAZIONE) \n" +
								"\t\tFROM PBANDI_T_DICHIARAZIONE_SPESA \n" +
								"\t\tWHERE ID_PROGETTO = ?\n" +
								"\t)\n" +
								") \n";
				try{
					LOG.info(prf + "\nQuery: \n\n" + getDtFrom + "\n\n");
					dtFrom = getJdbcTemplate().queryForObject(getDtFrom, new Object[]{idProgetto, idProgetto, idProgetto, idProgetto}, java.sql.Date.class);
				}catch (EmptyResultDataAccessException e){
					dtFrom = null;
				}
			}

			String getPeriodoStabilita =
					"SELECT PERIODO_STABILITA FROM PBANDI_R_BANDO_MODALITA_AGEVOL prbma  WHERE ID_MODALITA_AGEVOLAZIONE = ? \n" +
							"AND ID_BANDO IN(SELECT ID_BANDO FROM PBANDI_R_BANDO_LINEA_INTERVENT prbli \n" +
							"WHERE PROGR_BANDO_LINEA_INTERVENTO IN(SELECT PROGR_BANDO_LINEA_INTERVENTO FROM PBANDI_T_DOMANDA ptd \n" +
							"WHERE ID_DOMANDA IN (SELECT ID_DOMANDA FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?))) \n";
			Integer periodoStabilita;
			try{
				LOG.info(prf + "\nQuery: \n\n" + getPeriodoStabilita + "\n\n");
				periodoStabilita = getJdbcTemplate().queryForObject(getPeriodoStabilita, new Object[]{modalitaAgevolaz.getIdModalitaAgevolazione(), idProgetto}, Integer.class);
			}catch (EmptyResultDataAccessException e) {
				periodoStabilita = null;
			}

			if(dtFrom != null && periodoStabilita != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dtFrom);
				cal.add(Calendar.YEAR, periodoStabilita);

				if(modalitaAgevolazione.size()==1 && dataPropostaRevoca.after(cal.getTime())){
					if(modalitaAgevolaz.getIdModalitaAgevolazioneRif()==1){
						return "Non è possibile creare una nuova proposta di revoca per la modalità di agevolazione contributo perché sono trascorsi "+ periodoStabilita +" anni dalla data di erogazione del saldo";
					}else{
						return "Non è possibile creare una nuova proposta di revoca per la modalità di agevolazione "+ (modalitaAgevolaz.getIdModalitaAgevolazioneRif()==5 ? "finanziamento" : "garanzia") +" perché sono trascorsi "+ periodoStabilita +" anni dalla data di rendicontazione finale";
					}
				}
			}
		}
		if(modalitaAgevolazione.size() == 0){
			return "Non è possibile definire la tipologia di agevolazione";
		}
		//verifica 4
		boolean checkErr1 = true;
		boolean checkErr5 = true;
		Long modalitaAgevolazErr = null;
		for(ModalitaAgevolazioneVO modalitaAgevolaz : modalitaAgevolazione){
			String getImportoConcesso =
					"SELECT QUOTA_IMPORTO_AGEVOLATO \n" +
							"FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV a, PBANDI_T_CONTO_ECONOMICO b, PBANDI_T_PROGETTO c \n" +
							"WHERE a.ID_CONTO_ECONOMICO = b.ID_CONTO_ECONOMICO \n" +
							"AND b.ID_DOMANDA = c.ID_DOMANDA \n" +
							"AND c.ID_PROGETTO = ? \n" +
							"AND ID_MODALITA_AGEVOLAZIONE = ? \n" +
							"AND b.DT_FINE_VALIDITA IS NULL\n";
			BigDecimal importoConcesso;
			try {
				LOG.info(prf + "\nQuery: \n\n" + getImportoConcesso + "\n\n");
				importoConcesso = getJdbcTemplate().queryForObject(getImportoConcesso, new Object[]{idProgetto, modalitaAgevolaz.getIdModalitaAgevolazione()}, BigDecimal.class);
			}catch(EmptyResultDataAccessException e){
				importoConcesso = null;
			}
			String getImportoRevocato =
					"SELECT sum(importo) AS IMPORTO FROM PBANDI_T_REVOCA \n" +
							"WHERE ID_PROGETTO = ? \n" +
							"AND ID_MODALITA_AGEVOLAZIONE = ? \n";
			BigDecimal importoRevocato;
			try {
				LOG.info(prf + "\nQuery: \n\n" + getImportoRevocato + "\n\n");
				importoRevocato = getJdbcTemplate().queryForObject(getImportoRevocato, new Object[]{idProgetto, modalitaAgevolaz.getIdModalitaAgevolazione()}, BigDecimal.class);
			}catch(EmptyResultDataAccessException e){
				importoRevocato = null;
			}
			if(importoConcesso != null){
				if(importoRevocato == null || importoRevocato.compareTo(importoConcesso) < 0){
					checkErr5 = false;
					checkErr1 = false;
					break;
				}
				checkErr1 = importoRevocato.compareTo(importoConcesso) == 0;
				checkErr5 = importoRevocato.compareTo(importoConcesso) > 0;
			}
			if(checkErr5 || checkErr1){
				modalitaAgevolazErr = modalitaAgevolaz.getIdModalitaAgevolazione();
			}
		}
		if(checkErr5 || checkErr1){
			String descModAgev = getJdbcTemplate().queryForObject(
					"SELECT modalitaAgevolazione.desc_modalita_agevolazione\n" +
							"FROM PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione\n" +
							"WHERE modalitaAgevolazione.id_modalita_agevolazione = ?",
					String.class,
					modalitaAgevolazErr
			);

			return (checkErr1)
					? "Non è possibile creare una nuova proposta di revoca perché la somma degli importi già revocati è pari all’importo concesso per la modalità di agevolazione " + descModAgev
					: "Non è possibile creare una nuova proposta di revoca perché la somma degli importi già revocati è superiore all’importo concesso per la modalità di agevolazione " + descModAgev
			;
		}

		LOG.info(prf + "END");
		return "OK";
	}

	@Override
	public String creaPropostaRevoca(BigDecimal idProgetto, BigDecimal idDichiarazioneSpesa, Long idUtente, File filePart, String nomeFile,
			Boolean visibilitaFile, int entita) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String query = "INSERT INTO PBANDI_T_GESTIONE_REVOCA \n"
				+ "(ID_GESTIONE_REVOCA, NUMERO_REVOCA, ID_TIPOLOGIA_REVOCA, "
				+ "ID_PROGETTO, ID_DICHIARAZIONE_SPESA, ID_CAUSALE_BLOCCO, ID_STATO_REVOCA, DT_STATO_REVOCA, "
				+ "DT_INIZIO_VALIDITA, DT_GESTIONE, ID_UTENTE_INS, DT_INSERIMENTO) \n" + "VALUES \n"
				+ "(?, SEQ_PBANDI_T_GEST_REVOC_NUMERO.nextval, 1, ?, ?, ?, 1, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, ?, CURRENT_DATE)";

		String getIdGestioneRevoca = "select SEQ_PBANDI_T_GESTIONE_REVOCA.nextval from dual";
		LOG.info(prf + "\nQuery: \n\n" + getIdGestioneRevoca + "\n\n");
		Long idGestioneRevoca = getJdbcTemplate().queryForObject(getIdGestioneRevoca, Long.class);

		try {
			ArrayList<Object> args = new ArrayList<>();
			args.add(idGestioneRevoca); // ID_GESTIONE_REVOCA
			args.add(idProgetto); // ID_PROGETTO
			args.add(idDichiarazioneSpesa); // ID_DICHIARAZIONE_SPESA
			args.add(20); // ID_CAUSALE_BLOCCO
			args.add(idUtente); // idUtente

			LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
			getJdbcTemplate().update(query, ps -> {
				for (int i = 0; i < args.size(); i++) {
					ps.setObject(i + 1, args.get(i));
				}
			});

		} catch (Exception e) {
			LOG.info(prf + "END");
			return "Errore durante il salvataggio della revoca";
		}

		if(filePart != null) {
			// Inserisco la lettera collegata alla revoca per id_target
			String res = salvaLetteraAccompagnatoria(filePart, nomeFile, visibilitaFile, idProgetto, entita, idUtente,
					BigDecimal.valueOf(idGestioneRevoca));
			if (!Objects.equals(res, "OK")) {
				return "Errore durante il salvataggio della lettera accompagnatoria: " + res;
			}
		}

		LOG.info(prf + "END");
		return "OK";
	}

	@Override
	public String creaPropostaRevocaEdErogazione(BigDecimal importoDaErogare, BigDecimal importoIres,
			BigDecimal idProgetto, Long idDichiarazioneSpesa, Long idUtente, Double premialita,
			BigDecimal idCausaleErogContributo, BigDecimal idCausaleErogFinan) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		String getIdGestioneRevoca = "select SEQ_PBANDI_T_GESTIONE_REVOCA.nextval from dual";
		LOG.info(prf + "\nQuery: \n\n" + getIdGestioneRevoca + "\n\n");
		Long idGestioneRevoca = getJdbcTemplate().queryForObject(getIdGestioneRevoca, Long.class);

		String query = "INSERT INTO PBANDI.PBANDI_T_GESTIONE_REVOCA\r\n"
				+ "(ID_GESTIONE_REVOCA, NUMERO_REVOCA, ID_TIPOLOGIA_REVOCA, ID_PROGETTO,\r\n"
				+ "ID_CAUSALE_BLOCCO, DT_GESTIONE, ID_STATO_REVOCA, DT_STATO_REVOCA, \r\n"
				+ "IMP_IRES, IMP_DA_EROGARE_CONTRIBUTO, IMP_DA_EROGARE_FINANZIAMENTO, ID_DICHIARAZIONE_SPESA, DT_INIZIO_VALIDITA, \r\n"
				+ "ID_UTENTE_INS, DT_INSERIMENTO, ID_CAUSALE_EROG_FIN, ID_CAUSALE_EROG_CONTR)\r\n"
				+ "VALUES(?, SEQ_PBANDI_T_GEST_REVOC_NUMERO.nextval, 1, ?, 24, CURRENT_DATE, 1, CURRENT_DATE, ?, ?, ?, ?, CURRENT_DATE, ?, CURRENT_DATE, ?, ?)";
		LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

		try { // Inserisco la revoca
			getJdbcTemplate().update(query, idGestioneRevoca, idProgetto, importoIres, premialita, importoDaErogare,
					idDichiarazioneSpesa, idUtente, idCausaleErogFinan, idCausaleErogContributo);
		} catch (Exception e) {
			return "Errore durante il salvataggio della revoca";
		}

//		CR214
//		// Inserisco la lettera collegata alla revoca per id_target
//		String res = salvaLetteraAccompagnatoria(filePart, nomeFile, idProgetto, entita, idUtente, BigDecimal.valueOf(idGestioneRevoca));
//		if (!Objects.equals(res, "OK")) {
//			return "Errore durante il salvataggio della lettera accompagnatoria: " + res;
//		}

		LOG.info(prf + "END");
		return "OK";
	}

	@Override
	public String verificaPropostaErogazione(BigDecimal idProgetto) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		List<BigInteger> listIdAgevolazioni;
		try {
			// Trovo le agevolazioni per quel progetto
			String query = "SELECT DISTINCT modAgev.ID_MODALITA_AGEVOLAZIONE\n"
					+ "FROM PBANDI_D_MODALITA_AGEVOLAZIONE modAgev\n"
					+ "JOIN PBANDI_R_BANDO_MODALITA_AGEVOL bandoModAgev ON bandoModAgev.ID_MODALITA_AGEVOLAZIONE = modAgev.ID_MODALITA_AGEVOLAZIONE\n"
					+ "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bando ON bando.ID_BANDO = bandoModAgev.ID_BANDO\n"
					+ "JOIN PBANDI_T_DOMANDA domanda ON domanda.PROGR_BANDO_LINEA_INTERVENTO = bando.PROGR_BANDO_LINEA_INTERVENTO \n"
					+ "JOIN PBANDI_T_PROGETTO progetto ON progetto.ID_DOMANDA = domanda.ID_DOMANDA \n"
					+ "WHERE progetto.ID_PROGETTO = ?\n" + "AND modAgev.ID_MODALITA_AGEVOLAZIONE_RIF IN (1, 5, 10)";
			LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

			listIdAgevolazioni = getJdbcTemplate().queryForList(query, BigInteger.class, idProgetto);
		} catch (Exception e) {
			LOG.info(prf + "END");
			return "Errore nella ricerca delle modalità di agevolazione per il progetto";
		}

		if (listIdAgevolazioni.size() == 0) {
			return "Nessuna modalita di agevolazione trovata per il progetto";
		}

		LOG.info(prf + "END");
		return "OK";
	}

	@Override
	public String creaPropostaErogazione(List<Long> listIdModalitaAgevolazione, BigDecimal importoDaErogare,
			BigDecimal importoIres, BigDecimal idProgetto, Long idUtente, Long idDichiarazioneSpesa, Double premialita,
			BigDecimal idCausaleErogContributo, BigDecimal idCausaleErogFinan, HttpServletRequest req) throws Exception{
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + "BEGIN");

		try {
			// Inserisco le proposte per quante agevolazioni ho trovato
			for (Long idAgevolazioni : listIdModalitaAgevolazione) {
				// Trovo la data chiusura validazione
				String query = "SELECT ptds.DT_CHIUSURA_VALIDAZIONE \n"
						+ "FROM PBANDI_T_DICHIARAZIONE_SPESA ptds \n"
						+ "WHERE ptds.ID_DICHIARAZIONE_SPESA = ?";
				LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
				java.sql.Date dataChiusuraValidazione = getJdbcTemplate().queryForObject(query, java.sql.Date.class,
						idDichiarazioneSpesa);

				// Trovo l'id_proposta da collegare alla lettera
				String getIdProposta = "select SEQ_PBANDI_T_PROP_EROGAZIONE.nextval from dual";
				LOG.info(prf + "\nQuery: \n\n" + getIdProposta + "\n\n");
				Long idProposta = getJdbcTemplate().queryForObject(getIdProposta, Long.class);

				// Trovo l'id_modalita_agevolazione per capire logica inserimento
				String getIdModalitaAgevolazione = "SELECT pdma.ID_MODALITA_AGEVOLAZIONE_RIF \n" +
						"FROM PBANDI_D_MODALITA_AGEVOLAZIONE pdma \n" +
						"WHERE pdma.ID_MODALITA_AGEVOLAZIONE = ?";
				LOG.info(prf + "\nQuery: \n\n" + getIdModalitaAgevolazione + "\n\n");
				Long idModalitaAgevolazione = getJdbcTemplate().queryForObject(getIdModalitaAgevolazione, Long.class, idAgevolazioni);

				String query2 = "INSERT INTO PBANDI.PBANDI_T_PROPOSTA_EROGAZIONE\n"
						+ "(ID_PROPOSTA, ID_PROGETTO, \n"
						+ "IMP_LORDO, IMP_IRES, IMP_DA_EROGARE, \n"
						+ "ID_MODALITA_AGEVOLAZIONE, DT_VALIDAZIONE_SPESA,\n"
						+ "DT_INIZIO_VALIDITA, ID_UTENTE_INS, DT_INSERIMENTO, ID_CAUSALE_EROGAZIONE)\n"
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, CURRENT_DATE, ?, CURRENT_DATE, ?)";
				LOG.info(prf + "\nQuery2: \n\n" + query2 + "\n\n");

				if (importoIres == null) {
					importoIres = BigDecimal.ZERO;
				}

				Object[] args;
				if (idModalitaAgevolazione.equals(1L)) { // contributo
					if (premialita == null) {
						premialita = 0D;
					}
					args = new Object[] { idProposta, idProgetto, premialita, importoIres,
							(BigDecimal.valueOf(premialita)).subtract(importoIres), idAgevolazioni,
							dataChiusuraValidazione, idUtente, idCausaleErogContributo};
				} else {
					args = new Object[] { idProposta, idProgetto, importoDaErogare, new BigDecimal(0), importoDaErogare,
							idAgevolazioni,dataChiusuraValidazione, idUtente, idCausaleErogFinan};
				}
				int[] types1 = new int[] { Types.NUMERIC,Types.NUMERIC,Types.DECIMAL,Types.DECIMAL,Types.DECIMAL,Types.NUMERIC,
						Types.DATE, Types.NUMERIC,Types.NUMERIC};

				// Insertions la proposta
				getJdbcTemplate().update(query2, args,types1);
				checkControlliPreErogazione(idProposta, req);

//				CR214
//        		// Inserisco la lettera collegata alla proposta per id_target
//        		String res2 = salvaLetteraAccompagnatoria(filePart, nomeFile, idProgetto, entita, idUtente, BigDecimal.valueOf(idProposta));
//				if (!Objects.equals(res2, "OK")) {
//					return "Errore durante il salvataggio della lettera accompagnatoria";
//				}
			}

		} catch (Exception e) {
			LOG.error("Errore durante il salvataggio della proposta di erogazione ",e);
			throw e;
		}

		LOG.info(prf + "END");
		return "OK";
	}

	public Boolean checkControlliPreErogazione(Long idPropostaErogazione, HttpServletRequest req) {
		String prf = "[ControlloPreErogazioneDAOImpl::checkControlliPreErogazione]";
		LOG.info(prf + " BEGIN");

		Boolean status = false;
		try {
			String sql = "SELECT propostaErogazione.id_progetto\n"
					+ "FROM PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione\n"
					+ "WHERE propostaErogazione.id_proposta = ?";
			Long idProgetto = getJdbcTemplate().queryForObject(sql, Long.class, idPropostaErogazione);

			sql = "SELECT progetto.id_domanda\n" + "FROM PBANDI_T_PROGETTO progetto\n"
					+ "WHERE progetto.id_progetto = ?";
			Long idDomanda = getJdbcTemplate().queryForObject(sql, Long.class, idProgetto);

			sql = "SELECT soggettoProgetto.id_soggetto\n" + "FROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto\n"
					+ "WHERE soggettoProgetto.id_progetto = ?\n" + "AND soggettoProgetto.id_tipo_anagrafica = 1\n"
					+ "AND soggettoProgetto.id_tipo_beneficiario <> 4";
			Long idSoggetto = getJdbcTemplate().queryForObject(sql, Long.class, idProgetto);

			boolean controlliPreErogazione = true;

			{
				LOG.info(prf + " Verifico la proposta " + idPropostaErogazione);
				// 1. Blocco anagrafico
				if (getJdbcTemplate().query("SELECT COUNT(*) as results\n" + "FROM \n"
						+ "PBANDI_T_SOGG_DOMANDA_BLOCCO soggettoDomandaBlocco \n"
						+ "JOIN PBANDI_R_SOGGETTO_DOMANDA soggettoDomanda ON soggettoDomanda.progr_soggetto_domanda = soggettoDomandaBlocco.progr_soggetto_domanda \n"
						+ "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = soggettoDomanda.id_domanda \n"
						+ "WHERE \n" + "soggettoDomanda.id_soggetto = ? \n" + "AND \n" + "progetto.id_progetto = ? \n"
						+ "AND \n" + "soggettoDomandaBlocco.dt_inserimento_sblocco IS NULL", ps -> {
							ps.setLong(1, idSoggetto);
							ps.setLong(2, idProgetto);
						}, rs -> {
							rs.next();
							return rs.getLong("results");
						}) > 0) {
					// ho trovato almeno un blocco per questa domanda/soggetto senza data sblocco
					LOG.info("La proposta con codice: " + idPropostaErogazione
							+ "presenta un blocco soggetto/domanda senza data di sblocco!");
					controlliPreErogazione = false;
				}
				// 2. Forzatura controllo non applicabile per Antimafia
				if (controlliPreErogazione && getJdbcTemplate().query(
						"SELECT COUNT(*) as results \n" + "FROM \n"
								+ "PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" + "WHERE \n"
								+ "controlloNonApplicabile.id_tipo_richiesta = 3 \n" + "AND \n"
								+ "controlloNonApplicabile.id_proposta = ? " + "AND \n"
								+ "controlloNonApplicabile.dt_fine_validita IS NULL",
						ps -> ps.setLong(1, idPropostaErogazione), rs -> {
							rs.next();
							return rs.getLong("results");
						}) < 1) {
					// non ho trovato alcun controllo non applicabile di tipo antimafia per questa
					// proposta di erogazione
					// 3. Scaduto un controllo antimafia
					if (getJdbcTemplate().query(
							"SELECT COUNT(*) as results \n" +
									"FROM PBANDI_T_SOGGETTO_ANTIMAFIA antimafia \n" +
									"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = antimafia.id_domanda \n" +
									"AND progetto.id_progetto = ?\n" +
									"AND ( \n" +
									"\tantimafia.dt_scadenza_antimafia > CURRENT_DATE \n" +
									"\tOR ( \n" +
									"\t\tantimafia.dt_scadenza_antimafia IS NULL\n" +
									"\t\tAND antimafia.DT_RICEZIONE_BDNA IS NOT NULL\n" +
									"\t)\n" +
									")",
							ps -> ps.setLong(1, idProgetto), rs -> {
								rs.next();
								return rs.getLong("results");
							}) < 1) {
						// non ho trovato alcun controllo antimafia valido per questa proposta
						LOG.info("La proposta con codice: " + idPropostaErogazione
								+ " non presenta controlli antimafia validi!");
						controlliPreErogazione = false;
					}
				}
				// 4. Forzatura controllo non applicabile per DURC
				if (controlliPreErogazione && getJdbcTemplate().query(
						"SELECT COUNT(*) as results \n" + "FROM \n"
								+ "PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" + "WHERE \n"
								+ "controlloNonApplicabile.id_tipo_richiesta = 1 \n" + "AND \n"
								+ "controlloNonApplicabile.id_proposta = ? " + "AND \n"
								+ "controlloNonApplicabile.dt_fine_validita IS NULL",
						ps -> ps.setLong(1, idPropostaErogazione), rs -> {
							rs.next();
							return rs.getLong("results");
						}) < 1) {
					// non ho trovato alcun controllo non applicabile di tipo DURC per questa
					// proposta di erogazione
					// 5. Scaduto un controllo DURC
					if (getJdbcTemplate().query(
							"SELECT COUNT(*) AS results\n" + "FROM PBANDI_T_SOGGETTO_DURC durc\n"
									+ "WHERE durc.id_soggetto = ?\n" + "AND durc.dt_scadenza > CURRENT_DATE",
							ps -> ps.setLong(1, idSoggetto), rs -> {
								rs.next();
								return rs.getLong("results");
							}) < 1) {
						if (getJdbcTemplate().query(
								"SELECT COUNT(*) AS results\n" + "FROM PBANDI_T_SOGGETTO_DSAN dsan\n"
										+ "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = dsan.id_domanda\n"
										+ "WHERE progetto.id_progetto = ?\n" + "AND dsan.dt_scadenza > CURRENT_DATE",
								ps -> ps.setLong(1, idProgetto), rs -> {
									rs.next();
									return rs.getLong("results");
								}) < 1) {
							// non ho trovato alcun controllo DURC valido per questa proposta
							LOG.info("La proposta con codice: " + idPropostaErogazione
									+ " non presenta controlli ne DURC ne DSAN validi!");
							controlliPreErogazione = false;
						}
					}
				}
				Long privato = getJdbcTemplate().query(
						"SELECT \n" + "enteGiuridico.flag_pubblico_privato AS flagPubblicoPrivato\n" + "FROM \n"
								+ "PBANDI_T_ENTE_GIURIDICO enteGiuridico\n" + "WHERE \n"
								+ "enteGiuridico.id_soggetto = ? " + "AND " + "ROWNUM = 1",
						ps -> ps.setLong(1, idSoggetto), rs -> {
							rs.next();
							return rs.getLong("flagPubblicoPrivato");
						});
				if (controlliPreErogazione && privato == 1) {
					// 6. Forzatura controllo non applicabile per Deggendorf
					if (getJdbcTemplate().query(
							"SELECT COUNT(*) as results \n" + "FROM \n"
									+ "PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" + "WHERE \n"
									+ "controlloNonApplicabile.id_tipo_richiesta = 4 \n" + "AND \n"
									+ "controlloNonApplicabile.id_proposta = ? ",
							ps -> ps.setLong(1, idPropostaErogazione), rs -> {
								rs.next();
								return rs.getLong("results");
							}) < 1) {
						// non ho trovato alcun controllo non applicabile di tipo Deggendorf per questa
						// proposta di erogazione
						// 7. Scaduto un controllo Deggendorf (con esito 1)
						if (getJdbcTemplate().query("SELECT COUNT(*) AS results \n" + "FROM \n"
								+ "PBANDI_T_PROP_EROG_CTRL_RNA deggendorf \n" + "WHERE \n"
								+ "deggendorf.id_soggetto = ? \n" + "AND \n"
								+ "deggendorf.dt_scadenza > CURRENT_DATE \n" + "AND \n" + "deggendorf.esito = 1 ",
								ps -> ps.setLong(1, idSoggetto), rs -> {
									rs.next();
									return rs.getLong("results");
								}) < 1) {
							// non ho trovato alcun controllo DURC valido per questa proposta
							LOG.info("La proposta con codice: " + idPropostaErogazione
									+ " non presenta controlli Deggendrof validi!");
							controlliPreErogazione = false;
						}
					}
				}
				UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
				// Aggiornamento flag ctrl pre erogazione
				sql = "UPDATE PBANDI_T_PROPOSTA_EROGAZIONE \n" + "SET FLAG_CTRL_PRE_EROGAZIONE  = ?,"
						+ "DT_AGGIORNAMENTO = CURRENT_DATE," + "ID_UTENTE_AGG = ?\n" + "WHERE ID_PROPOSTA = ?";
				getJdbcTemplate().update(sql, controlliPreErogazione ? "S" : "N", userInfoSec.getIdUtente(),
						idPropostaErogazione);
			}

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to checkControlliPreErogazione", e);
		} finally {
			LOG.info(prf + " Status operazione: " + status);
			LOG.info(prf + " END");
		}
		return status;
	}

	private Long idProcesso(Long idBandoLinea) throws Exception {
		String sql = "SELECT ID_PROCESSO FROM PBANDI_R_BANDO_LINEA_INTERVENT WHERE PROGR_BANDO_LINEA_INTERVENTO = "
				+ idBandoLinea;
		LOG.info("\n" + sql);
		Long id = (Long) getJdbcTemplate().queryForObject(sql, Long.class);
		return id;
	}

	// Ex GestioneAppaltiBusinessImpl.findAppaltiChecklist().
	private ArrayList<AppaltoProgetto> findAppaltiChecklist(Long idProgetto, String tipoChecklist, Long idUtente,
			String idIride) throws Exception {

		it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO[] appaltiDto;
		appaltiDto = gestioneAppaltiBusinessImpl.findAppaltiChecklist(idUtente, idIride, idProgetto, tipoChecklist);

		ArrayList<AppaltoProgetto> appalti = new ArrayList<AppaltoProgetto>();

		if (appaltiDto != null) {
			for (it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO dto : appaltiDto) {
				AppaltoProgetto appalto = new AppaltoProgetto();
				appalto.setDescrizione(dto.getDescrizione());
				appalto.setIdAppalto(dto.getIdAppalto());
				appalto.setIdProgetto(dto.getIdProgetto());
				if (dto.getIsAssociated() != null)
					appalto.setAssociato(dto.getIsAssociated());
				appalti.add(appalto);
			}
		}

		return appalti;
	}

	// Ex PbandiValidazioneRendicontazioneDAOImpl.validatoMaggioreAmmesso()
	private Boolean validatoMaggioreAmmesso(Long idProgetto) {

		StringBuilder sqlSelect = new StringBuilder("select count(*) from  ");
		sqlSelect.append(
				"(select qpds.id_rigo_conto_economico, sum(pqpds.importo_validato) validato from pbandi_t_quota_parte_doc_spesa qpds, pbandi_r_pag_quot_parte_doc_sp pqpds ");
		sqlSelect.append("where qpds.id_progetto = " + idProgetto + " ");
		sqlSelect.append("and qpds.id_quota_parte_doc_spesa = pqpds.id_quota_parte_doc_spesa ");
		sqlSelect.append("group by qpds.id_rigo_conto_economico) valid,  ");
		sqlSelect.append(
				"(select rce.id_rigo_conto_economico, rce.importo_ammesso_finanziamento ammesso from pbandi_t_progetto p, pbandi_t_conto_economico ce, pbandi_t_rigo_conto_economico rce ");
		sqlSelect.append("where p.id_domanda = ce.id_domanda  ");
		sqlSelect.append("and ce.id_conto_economico = rce.id_conto_economico ");
		sqlSelect.append("and p.id_progetto = " + idProgetto + " ");
		sqlSelect.append("and ce.dt_fine_validita is null ");
		sqlSelect.append("and ce.id_stato_conto_economico in (2,3,8,9)) ammes ");
		sqlSelect.append("where valid.id_rigo_conto_economico = ammes.id_rigo_conto_economico ");
		sqlSelect.append("and valid.validato > ammes.ammesso ");

		LOG.info("\n" + sqlSelect);
		Integer numRecord = (Integer) getJdbcTemplate().queryForObject(sqlSelect.toString(), Integer.class);

		return (numRecord > 0);
	}

	private <T> T queryForObject(String sql, Class<T> requiredType) {
		try {
			T obj = (T) getJdbcTemplate().queryForObject(sql, requiredType);
			return obj;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	// Ex CPBEValidazioneDocumentiDiSpesa.ricercaDocumentiDiSpesa() +
	// ValidazioneDichiarazioneDiSpesaBusinessImpl.ricercaElencoDocumentiDiSpesa().
	public ArrayList<RigaRicercaDocumentiDiSpesaDTO> cercaDocumentiDiSpesaValidazione(
			CercaDocumentiDiSpesaValidazioneRequest cercaDocumentiDiSpesaValidazioneRequest, Long idUtente,
			String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::cercaDocumentiDiSpesaValidazione] ";
		LOG.info(prf + " BEGIN");

		if (cercaDocumentiDiSpesaValidazioneRequest == null) {
			throw new InvalidParameterException("cercaDocumentiDiSpesaValidazioneRequest non valorizzato.");
		}
		if (cercaDocumentiDiSpesaValidazioneRequest.getIdDichiarazioneSpesa() == null) {
			throw new InvalidParameterException("IdDichiarazioneSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);
		LOG.info(prf + "request = " + cercaDocumentiDiSpesaValidazioneRequest.toString());

		ArrayList<RigaRicercaDocumentiDiSpesaDTO> result = new ArrayList<RigaRicercaDocumentiDiSpesaDTO>();
		try {

			// Popola l'oggetto da passare al metodo di pbandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO documentosrv = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO();

			// Credo siano parti non più usate neanche nel vecchio.
			documentosrv.setIsRicercaPerCapofila(true);
			documentosrv.setIsRicercaPerTutti(false);
			documentosrv.setIsRicercaPerPartner(false);
			documentosrv.setIsGestitiNelProgetto(false);

			documentosrv.setIdTipoDocumentoDiSpesa(cercaDocumentiDiSpesaValidazioneRequest.getIdTipoDocumentoSpesa());
			documentosrv.setNumeroDocumento(cercaDocumentiDiSpesaValidazioneRequest.getNumeroDocumento());
			documentosrv.setDataDocumentoDiSpesa(cercaDocumentiDiSpesaValidazioneRequest.getDataDocumento());
			documentosrv.setIdTipoFornitore(cercaDocumentiDiSpesaValidazioneRequest.getIdTipoFornitore());
			documentosrv.setCodiceFiscaleFornitore(cercaDocumentiDiSpesaValidazioneRequest.getCodiceFiscaleFornitore());
			documentosrv.setPartitaIvaFornitore(cercaDocumentiDiSpesaValidazioneRequest.getPartitaIvaFornitore());
			documentosrv.setDenominazioneFornitore(cercaDocumentiDiSpesaValidazioneRequest.getDenominazioneFornitore());
			documentosrv.setCognomeFornitore(cercaDocumentiDiSpesaValidazioneRequest.getCognomeFornitore());
			documentosrv.setNomeFornitore(cercaDocumentiDiSpesaValidazioneRequest.getNomeFornitore());
			// documentosrv.setIdProgetto(cercaDocumentiDiSpesaValidazioneRequest.getIdProgetto());
			// documentosrv.setIdSoggetto(cercaDocumentiDiSpesaValidazioneRequest.getIdSoggettoBeneficiario());
			documentosrv.setTask(cercaDocumentiDiSpesaValidazioneRequest.getTask());

			ArrayList<String> stati = cercaDocumentiDiSpesaValidazioneRequest.getStatiDocumento();
			if (stati != null && !stati.isEmpty() && !stati.get(0).equals(""))
				documentosrv.setStatiDocumento(stati.toArray(new String[] {}));

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO[] resultDocumentoDiSpesaSrv;
			resultDocumentoDiSpesaSrv = validazioneRendicontazioneBusinessImpl.findDocumentiDiSpesa(idUtente, idIride,
					cercaDocumentiDiSpesaValidazioneRequest.getIdDichiarazioneSpesa(), documentosrv);

			for (int i = 0; i < resultDocumentoDiSpesaSrv.length; i++) {
				RigaRicercaDocumentiDiSpesaDTO doc = new RigaRicercaDocumentiDiSpesaDTO();
				doc.setIdDocumentoDiSpesa(resultDocumentoDiSpesaSrv[i].getIdDocumentoDiSpesa());
				doc.setIdTipoDocumentoDiSpesa(resultDocumentoDiSpesaSrv[i].getIdTipoDocumentoDiSpesa());
				doc.setDescBreveTipoDocumentoDiSpesa(resultDocumentoDiSpesaSrv[i].getDescBreveTipoDocumentoDiSpesa());
				doc.setDescrizioneTipologiaDocumento(resultDocumentoDiSpesaSrv[i].getDescTipoDocumentoDiSpesa());
				doc.setIdStatoDocumentoSpesa(resultDocumentoDiSpesaSrv[i].getIdStatoDocumentoSpesa());
				doc.setDescrizioneStatoDocumentoSpesa(resultDocumentoDiSpesaSrv[i].getDescStatoDocumentoSpesa());
				doc.setTask(resultDocumentoDiSpesaSrv[i].getTask());
				doc.setTipoInvio(resultDocumentoDiSpesaSrv[i].getTipoInvio());
				doc.setNumeroDocumento(resultDocumentoDiSpesaSrv[i].getNumeroDocumento());
				doc.setDenominazioneFornitore(resultDocumentoDiSpesaSrv[i].getDenominazioneFornitore()); // PBANDI-358
				doc.setImportoTotaleDocumento(resultDocumentoDiSpesaSrv[i].getImportoTotaleDocumentoIvato());
				doc.setImportoTotaleValidato(resultDocumentoDiSpesaSrv[i].getImportoTotaleValidato()); // PBandi-1174
				doc.setDataDocumento(resultDocumentoDiSpesaSrv[i].getDataDocumentoDiSpesa());

				result.add(doc);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca dei documenti di spesa: ", e);
			throw new DaoException("Errore durantela ricerca dei documenti di spesa.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public ArrayList<MensilitaDichiarazioneSpesaDTO> recuperaMensilitaDichiarazioneSpesa(Long idDichiarazioneSpesa,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::recuperaMensilitaDichiarazioneSpesa] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneSpesa + "; idUtente = " + idUtente);

		if (idDichiarazioneSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		List<MensilitaDichiarazioneSpesaDTO> listaMensilita = new ArrayList<MensilitaDichiarazioneSpesaDTO>();
		try {

			// Recupero mensilità
			String query = "SELECT ANNO,\n" + "       MESE,\n" + "       ESITO,\n" + "       FLAG_SABBATICO,\n"
					+ "       ID_PROGETTO,\n" + "       ID_DICHIARAZIONE_SPESA,\n" + "       NOTE\n"
					+ "FROM PBANDI_T_DICH_MENS_WS\n" + "WHERE ID_DICHIARAZIONE_SPESA= ? \n"
					+ "  AND DT_FINE_VALIDITA IS NULL";
			LOG.info(prf + "\nQuery mensilità: \n\n" + query + "\n\n");

			listaMensilita = getJdbcTemplate().query(query, rs -> {
				List<MensilitaDichiarazioneSpesaDTO> lista = new ArrayList<MensilitaDichiarazioneSpesaDTO>();

				while (rs.next()) {
					MensilitaDichiarazioneSpesaDTO mensilita = new MensilitaDichiarazioneSpesaDTO();
					mensilita.setAnno(rs.getLong("ANNO"));
					mensilita.setMese(rs.getString("MESE"));
					mensilita.setEsitoValidMesi(rs.getString("ESITO"));
					mensilita.setSabbatico(rs.getString("FLAG_SABBATICO"));
					mensilita.setIdPrg(rs.getLong("ID_PROGETTO"));
					mensilita.setIdDichSpesa(rs.getLong("ID_DICHIARAZIONE_SPESA"));
					Clob clob = rs.getClob("NOTE");
					if (clob != null) {
						mensilita.setNote(clob.getSubString(1, (int) clob.length()));
					}
					lista.add(mensilita);
				}

				return lista;
			}, idDichiarazioneSpesa);

			// Verifico che il mese non sia già rendicontato in altre dichiarazioni di spesa
			// per lo stesso progetto
			for (MensilitaDichiarazioneSpesaDTO mensilita : listaMensilita) {
				String queryMeseRipetuto = "SELECT ID_DICHIARAZIONE_SPESA\n" + "FROM PBANDI_T_DICH_MENS_WS\n"
						+ "WHERE ID_PROGETTO= ? \n" + "  AND MESE= ? \n" + "  AND ANNO= ? \n"
						+ "  AND ID_DICHIARAZIONE_SPESA <> ? \n" + "  AND DT_FINE_VALIDITA IS NULL\n"
						+ "  AND ESITO = 'OK'";
				LOG.info(prf + "\nQuery mese ripetuto: \n\n" + queryMeseRipetuto + "\n\n");
				Long idDichMeseRipetuto = getJdbcTemplate().query(queryMeseRipetuto, rs -> {
					Long id = null;

					while (rs.next()) {
						id = rs.getLong("ID_DICHIARAZIONE_SPESA");
					}

					return id;
				}, mensilita.getIdPrg(), mensilita.getMese(), mensilita.getAnno(), mensilita.getIdDichSpesa());
				mensilita.setIdDichMeseRipetuto(idDichMeseRipetuto);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca delle mensilità per le dichiarazioni di spesa: ", e);
			throw new DaoException("Errore durante la ricerca delle mensilità per le dichiarazioni di spesa.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return new ArrayList<MensilitaDichiarazioneSpesaDTO>(listaMensilita);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EsitoDTO validaMensilita(List<ValidaMensilitaRequest> validaMensilitaRequest, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::validaMensilita] ";
		LOG.info(prf + " BEGIN");

		if (validaMensilitaRequest.isEmpty()) {
			throw new InvalidParameterException("validaMensilitaRequest vuota.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);

		EsitoDTO esito = new EsitoDTO();

		try {

			// Verifico che il mese non sia stato rendicontato in altre dichiarazioni di
			// spesa
			for (ValidaMensilitaRequest request : validaMensilitaRequest) {
				if ("OK".equals(request.getEsitoValidMesi())) {
					String query = "SELECT COUNT(*) \n" + "FROM PBANDI_T_DICH_MENS_WS \n" + "WHERE ID_PROGETTO= ? \n"
							+ "  AND MESE= ? \n" + "  AND ANNO= ? \n" + "  AND ID_DICHIARAZIONE_SPESA <> ? \n"
							+ "  AND DT_FINE_VALIDITA IS NULL \n" + "  AND ESITO = 'OK'";
					LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
					Integer numRecord = getJdbcTemplate().queryForObject(query, new Object[] { request.getIdPrg(),
							request.getMese(), request.getAnno(), request.getIdDichSpesa() }, Integer.class);
					if (numRecord > 0) {
						esito.setEsito(false);
						esito.setMessaggio(
								"Non è possibile validare perché uno dei mesi approvati è già stato rendicontato in un’altra dichiarazione di spesa");
						return esito;
					}
				}
			}

			int cntMensApprov = 0;
			for (ValidaMensilitaRequest request : validaMensilitaRequest) {
				if ("OK".equals(request.getEsitoValidMesi())) {
					cntMensApprov++;
				}
			}

			// Aggiornamento record
			for (ValidaMensilitaRequest request : validaMensilitaRequest) {
				String queryUpdate = "UPDATE PBANDI_T_DICH_MENS_WS \n" + "SET ESITO= ?, \n" + "ID_UTENTE_AGG= ?, \n"
						+ "DT_AGGIORNAMENTO = SYSDATE, \n" + "NOTE = ? \n" + "WHERE ANNO= ? \n" + "  AND MESE= ? \n"
						+ "  AND ID_DICHIARAZIONE_SPESA= ?";
				getJdbcTemplate().update(queryUpdate, request.getEsitoValidMesi(), idUtente, request.getNote(),
						request.getAnno(), request.getMese(), request.getIdDichSpesa());

				//
				ArrayList<DocumentoDTO> documenti = new ArrayList<>();
				String query2 = "SELECT A.ID_PAGAMENTO, \n" + "       B.ID_DOCUMENTO_DI_SPESA, \n"
						+ "       C.ID_STATO_DOCUMENTO_SPESA_VALID \n" + "FROM PBANDI_R_PAGAMENTO_DICH_SPESA A \n"
						+ "JOIN PBANDI_R_PAGAMENTO_DOC_SPESA B ON A.ID_PAGAMENTO = B.ID_PAGAMENTO \n"
						+ "JOIN PBANDI_R_DOC_SPESA_PROGETTO C ON B.ID_DOCUMENTO_DI_SPESA = C.ID_DOCUMENTO_DI_SPESA \n"
						+ "JOIN PBANDI_T_QUOTA_PARTE_DOC_SPESA D ON D.ID_DOCUMENTO_DI_SPESA = C.ID_DOCUMENTO_DI_SPESA \n"
						+ "JOIN PBANDI_R_PAG_QUOT_PARTE_DOC_SP E ON E.ID_QUOTA_PARTE_DOC_SPESA = D.ID_QUOTA_PARTE_DOC_SPESA \n"
						+ "AND E.ID_PAGAMENTO = A.ID_PAGAMENTO \n" + "WHERE A.ID_DICHIARAZIONE_SPESA = ? \n"
						+ "ORDER BY B.ID_DOCUMENTO_DI_SPESA, \n" + "         A.ID_PAGAMENTO";
				LOG.info(prf + "\nQuery2: \n\n" + query2 + "\n\n");

				documenti = getJdbcTemplate().query(query2, rs -> {
					ArrayList<DocumentoDTO> lista = new ArrayList<>();

					while (rs.next()) {
						DocumentoDTO doc = new DocumentoDTO();
						doc.setIdPagamento(rs.getInt("ID_PAGAMENTO"));
						doc.setIdDocumentoSpesa(rs.getInt("ID_DOCUMENTO_DI_SPESA"));
						Integer statoDoc = rs.getInt("ID_STATO_DOCUMENTO_SPESA_VALID");
						if (statoDoc != null && (statoDoc == 6 || statoDoc == 7)) {
							doc.setStatoDocumento("Non approvato");
						} else {
							doc.setStatoDocumento("Approvato");
						}
						lista.add(doc);
					}

					return lista;
				}, request.getIdDichSpesa());

				// Pagamenti
				int cntPagamenti = 0;
				for (DocumentoDTO doc : documenti) {
					if ("Approvato".equals(doc.getStatoDocumento())) {
						cntPagamenti++;
					}
				}
				BigDecimal importoValidatoTotale = BigDecimal.valueOf(600).multiply(BigDecimal.valueOf(cntMensApprov));
				for (DocumentoDTO doc : documenti) {
					BigDecimal importoValidatoPagamento = null;
					if ("Approvato".equals(doc.getStatoDocumento()) && cntPagamenti > 0) {
						importoValidatoPagamento = importoValidatoTotale.divide(BigDecimal.valueOf(cntPagamenti), 2,
								RoundingMode.HALF_UP);
					} else {
						importoValidatoPagamento = BigDecimal.valueOf(0);
					}
					String queryUpdatePagamento = "UPDATE PBANDI_R_PAG_QUOT_PARTE_DOC_SP "
							+ "SET IMPORTO_VALIDATO = ?, " + "IMPORTO_QUIETANZATO = ? " + "WHERE ID_PAGAMENTO = ?";
					getJdbcTemplate().update(queryUpdatePagamento, importoValidatoPagamento, importoValidatoPagamento,
							doc.getIdPagamento());
				}

				// Documenti di spesa
				int cntDocSpesa = 0;
				for (int i = 0; i < documenti.size(); i++) {
					if ("Approvato".equals(documenti.get(i).getStatoDocumento())) {
						if (i == 0 || !documenti.get(i).getIdDocumentoSpesa()
								.equals(documenti.get(i - 1).getIdDocumentoSpesa())) {
							cntDocSpesa++;
						}
					}
				}
				BigDecimal importoRendTotale = BigDecimal.valueOf(600).multiply(BigDecimal.valueOf(cntMensApprov));
				for (DocumentoDTO doc : documenti) {
					BigDecimal importoRendDocumento = null;
					if ("Approvato".equals(doc.getStatoDocumento()) && cntDocSpesa > 0) {
						importoRendDocumento = importoRendTotale.divide(BigDecimal.valueOf(cntDocSpesa), 2,
								RoundingMode.HALF_UP);
					} else {
						importoRendDocumento = BigDecimal.valueOf(0);
					}
					String queryUpdateDocumenti = "UPDATE PBANDI_R_DOC_SPESA_PROGETTO \n"
							+ "SET IMPORTO_RENDICONTAZIONE = ? \n" + "WHERE ID_DOCUMENTO_DI_SPESA = ?";
					getJdbcTemplate().update(queryUpdateDocumenti, importoRendDocumento, doc.getIdDocumentoSpesa());

					String queryUpdateQuotaParte = "UPDATE PBANDI_T_QUOTA_PARTE_DOC_SPESA \n"
							+ "SET IMPORTO_QUOTA_PARTE_DOC_SPESA = ? \n" + "WHERE ID_DOCUMENTO_DI_SPESA = ?";
					getJdbcTemplate().update(queryUpdateQuotaParte, importoRendDocumento, doc.getIdDocumentoSpesa());
				}

			}

			esito.setEsito(true);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la validazione delle mensilità: ", e);
			throw new DaoException("Errore durante la validazione delle mensilità.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;

	}

	private Long trovaIdDocumentoIndex(Long idDichiarazioneDiSpesa, Long idProgetto) throws DaoException {

		Long idTarget = null;
		Long idEntita = null;
		Long idTipoDocIndex = null; // aggiunto poichè con solo target e entità vengono prese anche le checklist.

		if (decodificheDAOImpl.dichiarazioneSpesaFinaleConCFP(idDichiarazioneDiSpesa)) {
			// E' una DS finale: va restituito l'id doc index della CFP.

			// Trova l'id della CFP.
			idTarget = decodificheDAOImpl.idComunicazioneFineProgetto(idProgetto);

			BigDecimal bdIdEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_T_COMUNICAZ_FINE_PROG);
			idEntita = bdIdEntita.longValue();

			BigDecimal bdIdTipoDocIndex = decodificheDAOImpl.idDaDescrizione("PBANDI_C_TIPO_DOCUMENTO_INDEX",
					"ID_TIPO_DOCUMENTO_INDEX", "DESC_BREVE_TIPO_DOC_INDEX", Constants.DESC_BREVE_TIPO_DOC_INDEX_CFP);
			idTipoDocIndex = bdIdTipoDocIndex.longValue();

		} else {
			// E' una DS non finale: va restituito l'id doc index della DS.

			idTarget = idDichiarazioneDiSpesa;

			BigDecimal bdIdEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
					Constants.ENTITA_T_DICH_SPESA);
			idEntita = bdIdEntita.longValue();

			BigDecimal bdIdTipoDocIndex = decodificheDAOImpl.idDaDescrizione("PBANDI_C_TIPO_DOCUMENTO_INDEX",
					"ID_TIPO_DOCUMENTO_INDEX", "DESC_BREVE_TIPO_DOC_INDEX", Constants.DESC_BREVE_TIPO_DOC_INDEX_DS);
			idTipoDocIndex = bdIdTipoDocIndex.longValue();

		}

		// Trova l'id doc index.
		String sqlDocIndex = "SELECT ID_DOCUMENTO_INDEX FROM PBANDI_T_DOCUMENTO_INDEX WHERE ID_TARGET = " + idTarget
				+ " AND ID_ENTITA = " + idEntita + " AND ID_TIPO_DOCUMENTO_INDEX = " + idTipoDocIndex;
		LOG.info("\n" + sqlDocIndex);
		Long idDocIndex = (Long) getJdbcTemplate().queryForObject(sqlDocIndex, Long.class);

		return idDocIndex;
	}

	// Ex
	// ValidazioneDichiarazioneDiSpesaBusinessImpl.esisteRichiestaIntegrazioneAperta().
	// Verifica se per la dichiarazione in input esiste una richiesta di
	// integrazione aperta (non evasa dal Beneficiario).
	private boolean esisteRichiestaIntegrazioneAperta(Long idDichiarazioneDiSpesa, Long idUtente, String idIride)
			throws DaoException, ValidazioneRendicontazioneException, SystemException, UnrecoverableException,
			CSIException {

		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO[] elenco;
		elenco = validazioneRendicontazioneBusinessImpl.findIntegrazioniSpesa(idUtente, idIride,
				idDichiarazioneDiSpesa);

		boolean esiste = false;
		for (it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO dto : elenco) {
			if (dto.getDataInvio() == null)
				esiste = true;
		}

		return esiste;
	}

	@Override
	// Ex ValidazioneDematAction.detailDocSpesa() +
	// GestioneArchivioFileBusinessImpl.detailDocSpesa()
	public DocumentoDiSpesaDematDTO dettaglioDocumentoDiSpesaPerValidazione(Long idDichiarazioneDiSpesa,
			Long idDocumentoDiSpesa, Long idProgetto, Long idBandoLinea, Long idUtente, String idIride)
			throws Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::dettaglioDocumentoDiSpesaPerValidazione] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idBandoLinea = " + idBandoLinea
				+ "; idUtente = " + idUtente);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		DocumentoDiSpesaDematDTO docSpesaDemat;
		try {
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoDettaglioDocumento esito;
			esito = validazioneRendicontazioneBusinessImpl.findDettaglioDocumentoDiSpesa(idUtente, idIride,
					idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa);

			DocumentoDiSpesa documentoDiSpesa = this
					.rimappaDettaglioDocumentoDiSpesaPerValidazione(esito.getDocumentoDiSpesa());
			docSpesaDemat = it.csi.pbandi.pbweb.util.BeanUtil.convert(DocumentoDiSpesaDematDTO.class, documentoDiSpesa);

			// Trova lo stato documento validazione che il servizio sopra non recupera.
			String sql = "SELECT ID_STATO_DOCUMENTO_SPESA_VALID FROM PBANDI_R_DOC_SPESA_PROGETTO WHERE ID_PROGETTO="
					+ idProgetto + " AND ID_DOCUMENTO_DI_SPESA=" + idDocumentoDiSpesa;
			LOG.info("\n" + sql.toString());
			Long idStatoDocValid = (Long) getJdbcTemplate().queryForObject(sql.toString(), Long.class);
			if (idStatoDocValid != null) {
				String desc = decodificheDAOImpl.descrizioneDaId("PBANDI_D_STATO_DOCUMENTO_SPESA",
						"ID_STATO_DOCUMENTO_SPESA", "DESC_STATO_DOCUMENTO_SPESA", idStatoDocValid);
				docSpesaDemat.setDescrizioneStatoValidazione(desc);
			}

			// Jira PBANDI-2768
			Double importo = validazioneRendicontazioneBusinessImpl.findImportoValidatoSuVoceDiSpesa(idUtente, idIride,
					idDocumentoDiSpesa, idProgetto);
			docSpesaDemat.setImportoValidatoSuVoceDiSpesa(importo);

			String[] msgNonValidabile = esito.getMessaggi();
			if (msgNonValidabile != null) {
				// DOC NON VALIDABILE.
				StringBuilder temp = new StringBuilder("Documento non validabile: ");
				for (String msg : msgNonValidabile) {
					logger.info(" msg:  " + msg);
					temp.append(TraduttoreMessaggiPbandisrv.traduci(msg));
				}
				docSpesaDemat.setMessaggiErrore(temp.toString());

			} else {
				// DOC VALIDABILE.

				// documentiAllegati.
				ArrayList<DocumentoAllegatoDTO> documentiAllegati = this.getAllegatiDocumento(idDocumentoDiSpesa,
						idProgetto, idUtente, idIride);
				docSpesaDemat.setDocumentoAllegato(separateByIntegrazione1(documentiAllegati));
				docSpesaDemat.setDocumentoAllegatoGenerico(getAllegatiSenzaIntegrazione1(documentiAllegati));

				// documentiAllegatiPagamento.
				it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO[] pagamenti = gestionePagamentiBusinessImpl
						.findPagamentiAssociatiPerValidazione(idUtente, idIride, idDocumentoDiSpesa, idBandoLinea,
								idProgetto);
				List<DocumentoAllegatoPagamentoDTO> pagamentiAllegati = new ArrayList<>();
				for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO documentoPagamento : pagamenti) {
					// ArrayList<DocumentoAllegatoPagamentoDTO> pagamentiAllegati =
					// this.getAllegatiPagamento(idDocumentoDiSpesa, idProgetto, idBandoLinea,
					// idUtente, idIride);
					pagamentiAllegati.addAll(getJdbcTemplate().query("SELECT ptp.ID_PAGAMENTO,\n"
							+ "ptp.DT_PAGAMENTO,\n" + "pdmp.DESC_MODALITA_PAGAMENTO,\n" + "ptp.IMPORTO_PAGAMENTO,\n"
							+ "ptdi.ID_DOCUMENTO_INDEX,\n" + "ptf.NOME_FILE,\n" + "ptfe.ID_INTEGRAZIONE_SPESA\n"
							+ "FROM PBANDI_T_PAGAMENTO ptp \n"
							+ "JOIN PBANDI_D_MODALITA_PAGAMENTO pdmp ON pdmp.ID_MODALITA_PAGAMENTO = ptp.ID_MODALITA_PAGAMENTO\n"
							+ "JOIN PBANDI_C_ENTITA pce ON pce.NOME_ENTITA='PBANDI_T_PAGAMENTO'\n"
							+ "LEFT JOIN PBANDI_T_FILE_ENTITA ptfe ON ptfe.ID_TARGET = ptp.ID_PAGAMENTO AND ptfe.ID_ENTITA = pce.ID_ENTITA\n"
							+ "LEFT JOIN PBANDI_T_FILE ptf ON ptf.ID_FILE = ptfe.ID_FILE\n"
							+ "LEFT JOIN PBANDI_T_DOCUMENTO_INDEX ptdi ON ptdi.ID_DOCUMENTO_INDEX = ptf.ID_DOCUMENTO_INDEX \n"
							+ "WHERE ptp.ID_PAGAMENTO = ?\n" + "ORDER BY ptdi.ID_DOCUMENTO_INDEX",
							ps -> ps.setLong(1, documentoPagamento.getIdPagamento()), (rs, rownum) -> {
								DocumentoAllegatoPagamentoDTO dto = new DocumentoAllegatoPagamentoDTO();

								dto.setIdPagamento(rs.getLong("ID_PAGAMENTO"));
								dto.setDtPagamento(rs.getString("DT_PAGAMENTO"));
								dto.setDescrizioneModalitaPagamento(rs.getString("DESC_MODALITA_PAGAMENTO"));
								dto.setImportoPagamento(rs.getDouble("IMPORTO_PAGAMENTO"));
								dto.setId(rs.getLong("ID_DOCUMENTO_INDEX"));
								if (rs.wasNull())
									dto.setId(null);
								dto.setNomeFile(rs.getString("NOME_FILE"));
								dto.setIdIntegrazioneSpesa(rs.getString("ID_INTEGRAZIONE_SPESA"));

								return dto;
							}));
				}
				docSpesaDemat.setDocumentoAllegatoPagamento(separateByPagamentoIntegrazione2(pagamentiAllegati));

				// documentiAllegatiFornitore.
				DocumentoAllegatoDTO[] allegatiFornitore = this.getAllegatiFornitore(documentoDiSpesa, idUtente,
						idIride);
				docSpesaDemat.setDocumentoAllegatoFornitore(allegatiFornitore);

				// documentiAllegatiQualificaFornitore.
				DocumentoAllegatoDTO[] allegatiFornitoreQualifica = this.getAllegatiFornitoreQualifica(documentoDiSpesa,
						idUtente, idIride);
				docSpesaDemat.setDocumentoAllegatoQualificaFornitore(allegatiFornitoreQualifica);

				// RigaValidazioneItem
				ArrayList<RigaValidazioneItemDTO> righeValidazioneItem = this.getPagamentiConVdsDemat(idBandoLinea,
						idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa, idUtente, idIride);

				// se non trovo voci di spesa da validare per questa DS, cerco quelle associate
				// al documento da mostrare in sola visualizzazione
				Boolean voceTrovata = Boolean.FALSE;
				if (righeValidazioneItem != null) {
					for (RigaValidazioneItemDTO riga : righeValidazioneItem) {
						if ((Boolean.FALSE).equals(riga.getRigaPagamento())) {
							voceTrovata = Boolean.TRUE;
						}
					}
				}
				if (voceTrovata.equals(Boolean.FALSE)) {
					ArrayList<RigaValidazioneItemDTO> righeValidazioneItemVDS = this.getVdsDocumento(idBandoLinea,
							idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa, idUtente, idIride);
					if (righeValidazioneItemVDS != null) {
						for (RigaValidazioneItemDTO riga : righeValidazioneItemVDS) {
							righeValidazioneItem.add(riga);
						}
					}
				}

				docSpesaDemat.setRigaValidazioneItem(righeValidazioneItem.toArray(new RigaValidazioneItemDTO[] {}));

				// Note di credito
				ArrayList<RigaNotaDiCreditoItemDTO> noteDiCredito = this.getNoteDiCredito(idDocumentoDiSpesa,
						idProgetto, idUtente, idIride);
				for (RigaNotaDiCreditoItemDTO rigaNotaDiCreditoItem : noteDiCredito) {
					if (rigaNotaDiCreditoItem.getRigaNotaDiCredito()) {
						ArrayList<DocumentoAllegatoDTO> documentiAllegatiNota = getAllegatiDocumento(
								rigaNotaDiCreditoItem.getId(), idProgetto, idUtente, idIride);
						rigaNotaDiCreditoItem.setDocumentoAllegato(documentiAllegatiNota);
					}
				}

				docSpesaDemat.setNoteDiCredito(noteDiCredito.toArray(new RigaNotaDiCreditoItemDTO[] {}));

				if (docSpesaDemat.getImportoRendicontabile() != null && docSpesaDemat.getCostoOrario() != null)
					docSpesaDemat.setOreLavorate(NumberUtil.getDoubleValue(
							NumberUtil.divide(docSpesaDemat.getImportoRendicontabile(), docSpesaDemat.getCostoOrario())
									.doubleValue()));
			}

			// parte presente in ValidazioneDematAction.detailDocSpesa()
			RigaValidazioneItemDTO[] rigaValidazioneItems = docSpesaDemat.getRigaValidazioneItem();
			for (RigaValidazioneItemDTO rigaValidazioneItem : rigaValidazioneItems) {
				if (rigaValidazioneItem.getImportoValidatoVoceDiSpesa() != null)
					rigaValidazioneItem.setImportoValidato(NumberUtil
							.getStringValueItalianFormat(rigaValidazioneItem.getImportoValidatoVoceDiSpesa()));
			}

			docSpesaDemat.setAffidamento(this.trovaAffidamento(docSpesaDemat.getIdAppalto(),
					docSpesaDemat.getIdProgetto(), idUtente, idIride));
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca dei dati del documento di spesa: ", e);
			throw new DaoException("Errore durante la ricerca dei dati del documento di spesa.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return docSpesaDemat;
	}

	@Override
	// Ex ValidazioneDematAction.detailDocSpesa() +
	// GestioneArchivioFileBusinessImpl.detailDocSpesa()
	public DocumentoDiSpesaDematOldDTO dettaglioDocumentoDiSpesaPerValidazioneOld(Long idDichiarazioneDiSpesa,
			Long idDocumentoDiSpesa, Long idProgetto, Long idBandoLinea, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::dettaglioDocumentoDiSpesaPerValidazione] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idBandoLinea = " + idBandoLinea
				+ "; idUtente = " + idUtente);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		DocumentoDiSpesaDematOldDTO docSpesaDemat = new DocumentoDiSpesaDematOldDTO();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoDettaglioDocumento esito;
			esito = validazioneRendicontazioneBusinessImpl.findDettaglioDocumentoDiSpesa(idUtente, idIride,
					idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa);

			DocumentoDiSpesa documentoDiSpesa = this
					.rimappaDettaglioDocumentoDiSpesaPerValidazione(esito.getDocumentoDiSpesa());
			docSpesaDemat = it.csi.pbandi.pbweb.util.BeanUtil.convert(DocumentoDiSpesaDematOldDTO.class,
					documentoDiSpesa);

			// Trova lo stato documento validazione che il servizio sopra non recupera.
			String sql = "SELECT ID_STATO_DOCUMENTO_SPESA_VALID FROM PBANDI_R_DOC_SPESA_PROGETTO WHERE ID_PROGETTO="
					+ idProgetto + " AND ID_DOCUMENTO_DI_SPESA=" + idDocumentoDiSpesa;
			LOG.info("\n" + sql.toString());
			Long idStatoDocValid = (Long) getJdbcTemplate().queryForObject(sql.toString(), Long.class);
			if (idStatoDocValid != null) {
				String desc = decodificheDAOImpl.descrizioneDaId("PBANDI_D_STATO_DOCUMENTO_SPESA",
						"ID_STATO_DOCUMENTO_SPESA", "DESC_STATO_DOCUMENTO_SPESA", idStatoDocValid);
				docSpesaDemat.setDescrizioneStatoValidazione(desc);
			}

			// Jira PBANDI-2768
			Double importo = validazioneRendicontazioneBusinessImpl.findImportoValidatoSuVoceDiSpesa(idUtente, idIride,
					idDocumentoDiSpesa, idProgetto);
			docSpesaDemat.setImportoValidatoSuVoceDiSpesa(importo);

			String[] msgNonValidabile = esito.getMessaggi();
			if (msgNonValidabile != null) {
				// DOC NON VALIDABILE.

				String temp = "Documento non validabile: ";
				for (String msg : msgNonValidabile) {
					logger.info(" msg:  " + msg);
					temp += TraduttoreMessaggiPbandisrv.traduci(msg);
				}
				docSpesaDemat.setMessaggiErrore(temp);

			} else {
				// DOC VALIDABILE.

				// documentiAllegati.
				ArrayList<DocumentoAllegatoDTO> documentiAllegati = this.getAllegatiDocumento(idDocumentoDiSpesa,
						idProgetto, idUtente, idIride);
				docSpesaDemat.setDocumentoAllegato(documentiAllegati.toArray(new DocumentoAllegatoDTO[] {}));

				// documentiAllegatiPagamento.
				ArrayList<DocumentoAllegatoPagamentoDTO> pagamentiAllegati = this
						.getAllegatiPagamento(idDocumentoDiSpesa, idProgetto, idBandoLinea, idUtente, idIride);
				docSpesaDemat.setDocumentoAllegatoPagamento(
						pagamentiAllegati.toArray(new DocumentoAllegatoPagamentoDTO[] {}));

				// documentiAllegatiFornitore.
				DocumentoAllegatoDTO[] allegatiFornitore = this.getAllegatiFornitore(documentoDiSpesa, idUtente,
						idIride);
				docSpesaDemat.setDocumentoAllegatoFornitore(allegatiFornitore);

				// documentiAllegatiQualificaFornitore.
				DocumentoAllegatoDTO[] allegatiFornitoreQualifica = this.getAllegatiFornitoreQualifica(documentoDiSpesa,
						idUtente, idIride);
				docSpesaDemat.setDocumentoAllegatoQualificaFornitore(allegatiFornitoreQualifica);

				// RigaValidazioneItem
				ArrayList<RigaValidazioneItemDTO> righeValidazioneItem = this.getPagamentiConVdsDemat(idBandoLinea,
						idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa, idUtente, idIride);
				docSpesaDemat.setRigaValidazioneItem(righeValidazioneItem.toArray(new RigaValidazioneItemDTO[] {}));

				// Note di credito
				ArrayList<RigaNotaDiCreditoItemDTO> noteDiCredito = this.getNoteDiCredito(idDocumentoDiSpesa,
						idProgetto, idUtente, idIride);
				for (RigaNotaDiCreditoItemDTO rigaNotaDiCreditoItem : noteDiCredito) {
					if (rigaNotaDiCreditoItem.getRigaNotaDiCredito()) {
						ArrayList<DocumentoAllegatoDTO> documentiAllegatiNota = getAllegatiDocumento(
								rigaNotaDiCreditoItem.getId(), idProgetto, idUtente, idIride);
						rigaNotaDiCreditoItem.setDocumentoAllegato(documentiAllegatiNota);
					}
				}

				docSpesaDemat.setNoteDiCredito(noteDiCredito.toArray(new RigaNotaDiCreditoItemDTO[] {}));

				if (docSpesaDemat.getImportoRendicontabile() != null && docSpesaDemat.getCostoOrario() != null)
					docSpesaDemat.setOreLavorate(NumberUtil.getDoubleValue(
							NumberUtil.divide(docSpesaDemat.getImportoRendicontabile(), docSpesaDemat.getCostoOrario())
									.doubleValue()));

			}

			// parte presente in ValidazioneDematAction.detailDocSpesa()
			RigaValidazioneItemDTO[] rigaValidazioneItems = docSpesaDemat.getRigaValidazioneItem();
			for (RigaValidazioneItemDTO rigaValidazioneItem : rigaValidazioneItems) {
				if (rigaValidazioneItem.getImportoValidatoVoceDiSpesa() != null)
					rigaValidazioneItem.setImportoValidato(NumberUtil
							.getStringValueItalianFormat(rigaValidazioneItem.getImportoValidatoVoceDiSpesa()));
			}

			docSpesaDemat.setAffidamento(this.trovaAffidamento(docSpesaDemat.getIdAppalto(),
					docSpesaDemat.getIdProgetto(), idUtente, idIride));

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca dei dati del documento di spesa: ", e);
			throw new DaoException("Errore durante la ricerca dei dati del documento di spesa.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return docSpesaDemat;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex ValidazioneDematAction.invalidaSingolo() +
	// GestioneArchivioFileBusinessImpl.invalidaSingolo()
	public void setDataNotifica(Long idIntegrazioneSpesa, Date dataNotifica)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::setDataNotifica] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idIntegrazioneSpesa + "; dataNotifica = " + dataNotifica);

		if (idIntegrazioneSpesa == null) {
			throw new InvalidParameterException("idIntegrazioneSpesa non valorizzato.");
		}
		if (dataNotifica == null) {
			throw new InvalidParameterException("dataNotifica non valorizzato.");
		}

		try {

			String queryUp = "UPDATE PBANDI.PBANDI_T_INTEGRAZIONE_SPESA\r\n" + "SET  DT_NOTIFICA=?\r\n"
					+ "WHERE ID_INTEGRAZIONE_SPESA = " + idIntegrazioneSpesa;

			Object[] args1 = new Object[] { dataNotifica };

			int[] types1 = new int[] { Types.DATE };

			getJdbcTemplate().update(queryUp, args1, types1);

			/*
			 * it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.
			 * EsitoOperazioneAutomatica esitoSrv; esitoSrv =
			 * validazioneRendicontazioneBusinessImpl.invalidaMultiplo(idUtente, idIride,
			 * listaIdDocSpesa, idDichiarazioneDiSpesa);
			 * 
			 * if (esitoSrv == null || !esitoSrv.getEsito()) { throw new
			 * Exception("Invalidamento fallito."); }
			 */

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante l'invalidamento: ", e);
			throw new DaoException("Errore durante l'invalidamento.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	// Ex ValidazioneDematAction.findAffidamento()
	private AffidamentoValidazione trovaAffidamento(Long idAppalto, Long idProgetto, Long idUtente, String idIride)
			throws GestioneAffidamentiException, SystemException, UnrecoverableException, CSIException {
		String prf = "[ValidazioneRendicontazioneDAOImpl::trovaAffidamento] ";
		LOG.info(prf + "idAppalto = " + idAppalto);

		AffidamentoValidazione affidamento = null;

		if (idAppalto == null)
			return affidamento;

		it.csi.pbandi.pbweb.pbandisrv.dto.gestioneaffidamenti.AffidamentoDTO dto;
		dto = gestioneAffidamentiBusinessImpl.findAffidamento(idUtente, idIride, idAppalto);

		if (dto != null) {
			affidamento = new AffidamentoValidazione();
			affidamento.setIdAppalto(dto.getIdAppalto());
			affidamento.setOggetto(dto.getOggettoAppalto());
			if (dto.getProceduraAggiudicazioneDTO() != null) {
				affidamento.setCodProcAgg(dto.getProceduraAggiudicazioneDTO().getCodProcAgg());
				affidamento.setCigProcAgg(dto.getProceduraAggiudicazioneDTO().getCigProcAgg());
			}

			// Jira PBANDI-2775
			affidamento.setIdStatoAffidamento(dto.getIdStatoAffidamento());
			affidamento.setDescStatoAffidamento(dto.getDescStatoAffidamento());

			// Jira PBANDI-2829: restituisco gli eventuali esiti dell'affidamento.
			try {
				it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO checklist;
				checklist = checklistHtmlBusinessImpl.getModuloCheckListAffidamentoHtml(idUtente, idIride,
						dto.getIdAppalto(), idProgetto, "stringa finta", "CLA");
				checklist.getIdDocumentoIndex();
				if (checklist.getEsitoIntermedio() != null) {
					affidamento.setEsitoIntermedio(checklist.getEsitoIntermedio().getEsito());
					affidamento.setFlagRettificaIntermedio(checklist.getEsitoIntermedio().getFlagRettifica());
				}
				if (checklist.getEsitoDefinitivo() != null) {
					affidamento.setEsitoDefinitivo(checklist.getEsitoDefinitivo().getEsito());
					affidamento.setFlagRettificaDefinitivo(checklist.getEsitoDefinitivo().getFlagRettifica());
				}

				// Jira PBANDI-2849.
				affidamento.setNomeFile(checklist.getNomeFile());
				affidamento.setIdDocIndex(checklist.getIdDocumentoIndex());

			} catch (Exception e) {
				logger.error(prf + "ERRORE nella ricerca degli esiti", e);
			}
		}

		return affidamento;
	}

	public DocumentoDiSpesa rimappaDettaglioDocumentoDiSpesaPerValidazione(
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO docSrv) throws Exception {

		if (docSrv == null)
			return null;

		DocumentoDiSpesa doc = new DocumentoDiSpesa();

		doc.setCodiceFiscaleFornitore(docSrv.getCodiceFiscaleFornitore());
		doc.setCodOggettoAttivita(NumberUtil.getStringValue(docSrv.getIdTipoOggettoAttivita()));
		doc.setCodTipologiaFornitore(NumberUtil.getStringValue(docSrv.getIdTipoFornitore()));
		doc.setCodTipologiaDocumento(NumberUtil.getStringValue(docSrv.getIdTipoDocumentoDiSpesa()));
		doc.setCognomeFornitore(docSrv.getCognomeFornitore());
		doc.setCostoOrario(docSrv.getCostoOrario());
		doc.setDataDocumento(DateUtil.getDate(docSrv.getDataDocumentoDiSpesa()));
		doc.setDataDocumentoRiferimento(DateUtil.getDate(docSrv.getDataDocumentoDiSpesaDiRiferimento()));
		doc.setDataDocumentoForfettaria(DateUtil.getDate(docSrv.getDataDocumentoDiSpesa()));
		doc.setDenominazioneFornitore(docSrv.getDenominazioneFornitore());
		doc.setDescrizioneDocumento(docSrv.getDescrizioneDocumentoDiSpesa());
		doc.setDescBreveTipoDocumentoDiSpesa(docSrv.getDescBreveTipoDocumentoDiSpesa());
		doc.setDescrizioneTipologiaDocumento(docSrv.getDescTipoDocumentoDiSpesa());
		doc.setDescrizioneTipologiaFornitore(docSrv.getDescTipologiaFornitore());
		doc.setDescrizioneStato(docSrv.getDescStatoDocumentoSpesa());
		doc.setDescrizioneTipologiaFornitore(docSrv.getDescTipologiaFornitore());
		doc.setDestinazioneTrasferta(docSrv.getDestinazioneTrasferta());
		doc.setDurataTrasferta(docSrv.getDurataTrasferta());
		doc.setId(docSrv.getIdDocumentoDiSpesa());

		doc.setIdDocRiferimento(docSrv.getIdDocRiferimento());
		doc.setIdFornitore(docSrv.getIdFornitore());
		doc.setIdProgetto(docSrv.getIdProgetto());
		doc.setIdTipoDocumentoDiSpesa(docSrv.getIdTipoDocumentoDiSpesa());
		doc.setImponibile(docSrv.getImponibile());
		doc.setImportoIva(docSrv.getImportoIva());
		doc.setImportoIvaACosto(docSrv.getImportoIvaACosto());
		doc.setImportoRitenutaAcconto(docSrv.getImportoRitenutaDAcconto());
		doc.setImportoSpesaForfettaria(docSrv.getImportoTotaleDocumentoIvato());
		doc.setImportoTotaleDocumento(docSrv.getImportoTotaleDocumentoIvato());
		doc.setImportoRendicontabile(docSrv.getImportoRendicontabile());
		doc.setNomeFornitore(docSrv.getNomeFornitore());
		doc.setNoteValidazione(docSrv.getNoteValidazione());
		doc.setNumeroDocumento(docSrv.getNumeroDocumento());
		doc.setNumeroDocumentoRiferimento(docSrv.getNumeroDocumentoDiSpesaDiRiferimento());
		doc.setPartitaIvaFornitore(docSrv.getPartitaIvaFornitore());
		doc.setTask(docSrv.getTask());
		doc.setTipoInvio(docSrv.getTipoInvio());
		doc.setProgrFornitoreQualifica(NumberUtil.getStringValue(docSrv.getProgrFornitoreQualifica()));
		doc.setIdAppalto(docSrv.getIdAppalto());
		return doc;
	}

	public ArrayList<DocumentoAllegatoDTO> getAllegatiDocumento(Long idDocumentoDiSpesa, Long idProgetto, Long idUtente,
			String idIride) throws ArchivioException, SystemException, UnrecoverableException, CSIException {

		it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] files;
		files = archivioBusinessImpl.getFilesAssociatedDocDiSpesa(idUtente, idIride, idDocumentoDiSpesa, idProgetto);

		ArrayList<DocumentoAllegatoDTO> documentiAllegati = new ArrayList<DocumentoAllegatoDTO>();

		if (!ObjectUtil.isEmpty(files)) {
			for (it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO file : files) {
				Long idEntita = file.getIdEntita();
				DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
				allegato.setId(file.getIdDocumentoIndex());
				allegato.setNome(file.getNomeFile());
				allegato.setProtocollo(file.getNumProtocollo());
				// Un allegato è dissociabile se il documento-progetto ha stato a DICHIARABILE o
				// RESPINTO e
				// l'attivita' corrente e' quella della dichiarazione: qui sono sempre in
				// validazione.
				// if (activityProcessManager.isCurrentActivityRendicontazione(session) &&
				// !file.getIsLocked() ){
				// allegato.setIsDisassociable(true);
				// }
				allegato.setDisassociabile(false);
				allegato.setIdIntegrazioneSpesa(file.getIdIntegrazioneSpesa());
				;
				documentiAllegati.add(allegato);
			}
		}

		return documentiAllegati;
	}

	public ArrayList<DocumentoAllegatoPagamentoDTO> getAllegatiPagamento(Long idDocumentoDiSpesa, Long idProgetto,
			Long idBandoLinea, Long idUtente, String idIride)
			throws ArchivioException, SystemException, UnrecoverableException, CSIException {

		ArrayList<DocumentoAllegatoPagamentoDTO> pagamentiAllegati = new ArrayList<DocumentoAllegatoPagamentoDTO>();

		it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO[] pagamenti;
		pagamenti = gestionePagamentiBusinessImpl.findPagamentiAssociatiPerValidazione(idUtente, idIride,
				idDocumentoDiSpesa, idBandoLinea, idProgetto);

		for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO documentoPagamento : pagamenti) {

			it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] documentiAllegatiPagamento;
			documentiAllegatiPagamento = archivioBusinessImpl.getFilesAssociatedPagamento(idUtente, idIride,
					documentoPagamento.getIdPagamento());

			for (it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO fileDTO : documentiAllegatiPagamento) {

				DocumentoAllegatoPagamentoDTO documentoAllegatoPagamento = new DocumentoAllegatoPagamentoDTO();
				documentoAllegatoPagamento.setId(fileDTO.getIdDocumentoIndex());
				documentoAllegatoPagamento.setNomeFile(fileDTO.getNomeFile());
				documentoAllegatoPagamento.setImportoPagamento(documentoPagamento.getImportoPagamento());
				documentoAllegatoPagamento
						.setDescrizioneModalitaPagamento(documentoPagamento.getDescrizioneModalitaPagamento());
				documentoAllegatoPagamento.setDtPagamento(DateUtil.getDate(documentoPagamento.getDtPagamento()));
				documentoAllegatoPagamento.setIdPagamento(documentoPagamento.getIdPagamento());
				documentoAllegatoPagamento.setIdIntegrazioneSpesa(fileDTO.getIdIntegrazioneSpesa());
				pagamentiAllegati.add(documentoAllegatoPagamento);

			}

		}
		return pagamentiAllegati;
	}

	public DocumentoAllegatoDTO[] getAllegatiFornitore(DocumentoDiSpesa documento, Long idUtente, String idIride)
			throws Exception {

		Long idFornitore = documento.getIdFornitore();
		Long idProgetto = documento.getIdProgetto();

		if (idFornitore != null) {
			List<DocumentoAllegatoDTO> lista = documentoDiSpesaDAOImpl.allegatiFornitore(idFornitore, idProgetto,
					idUtente, idIride);
			return lista.toArray(new DocumentoAllegatoDTO[] {});
		}

		return null;
	}

	private DocumentoAllegatoDTO[] getAllegatiFornitoreQualifica(DocumentoDiSpesa documento, Long idUtente,
			String idIride) throws Exception {

		String idQualifica = documento.getProgrFornitoreQualifica();

		if (!StringUtil.isEmpty(idQualifica)) {
			Long progrFornitoreQualifica = new Long(idQualifica);
			List<DocumentoAllegatoDTO> lista = documentoDiSpesaDAOImpl.allegatiQualifica(progrFornitoreQualifica,
					idUtente, idIride);
			return lista.toArray(new DocumentoAllegatoDTO[] {});
		}

		return null;
	}

	// Ex ValidazioneDichiarazioneDiSpesaBusinessImpl.getPagamentiConVdsDemat() in
	// pbandiweb.
	public ArrayList<RigaValidazioneItemDTO> getPagamentiConVdsDemat(Long idBando, Long idDocumentoDiSpesa,
			Long idProgetto, Long idDichiarazioneDiSpesa, Long idUtente, String identita) throws Exception {
		return getPagamentiVDS(idBando, idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa, idUtente, identita,
				Boolean.FALSE);
	}

	public ArrayList<RigaValidazioneItemDTO> getVdsDocumento(Long idBando, Long idDocumentoDiSpesa, Long idProgetto,
			Long idDichiarazioneDiSpesa, Long idUtente, String identita) throws Exception {
		return getPagamentiVDS(idBando, idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa, idUtente, identita,
				Boolean.TRUE);
	}

	private ArrayList<RigaValidazioneItemDTO> getPagamentiVDS(Long idBando, Long idDocumentoDiSpesa, Long idProgetto,
			Long idDichiarazioneDiSpesa, Long idUtente, String identita, Boolean onlyVDSDoc) throws Exception {
		// onlyVDSDoc settato a TRUE se sto cercando le voci di spesa associate al
		// documento anche di DS precedenti, senza le quietanze
		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO validazioneDTO;
		validazioneDTO = validazioneRendicontazioneBusinessImpl.findPagamentiEVociDiSpesa(idUtente, identita,
				idDocumentoDiSpesa, idProgetto);

		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO[] pagamenti = validazioneDTO
				.getPagamenti();

		ArrayList<RigaValidazioneItemDTO> listDocumentiDaValidare = new ArrayList<RigaValidazioneItemDTO>();
		Map<Long, RigaValidazioneItemDTO> mapVds = new LinkedHashMap<Long, RigaValidazioneItemDTO>();
		for (int i = 0; i < pagamenti.length; i++) {
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO pagamento = pagamenti[i];

			RigaValidazioneItemDTO rigaPagamento = new RigaValidazioneItemDTO();
			rigaPagamento.setRigaPagamento(true);
			rigaPagamento.setIdPagamento(pagamento.getIdPagamento());
			rigaPagamento.setModalitaPagamento(pagamento.getDescModalitaPagamento());

			rigaPagamento.setIdDichiarazioneDiSpesa(pagamento.getIdDichiarazioneDiSpesa());

			// BR04
			rigaPagamento.setDataValutaVisible(false);
			if (profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, identita, idBando,
					RegoleConstants.BR04_VISUALIZZA_DATA_VALUTA)) {
				rigaPagamento.setDataValutaVisible(true);
				rigaPagamento.setDataValuta(DateUtil.getDate(pagamento.getDtValuta()));
			}

			// BR05
			rigaPagamento.setDataPagamentoVisible(false);
			if (profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, identita, idBando,
					RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA)) {
				rigaPagamento.setDataPagamentoVisible(true);
				rigaPagamento.setDataPagamento(DateUtil.getDate(pagamento.getDtPagamento()));
			}

			rigaPagamento.setImportoTotalePagamento(pagamento.getImportoTotale());

			// FLAG PER DECIDERE SE CREARE I CHECKBOX PER IL PAGAMENTO
			if (idDichiarazioneDiSpesa.equals(pagamento.getIdDichiarazioneDiSpesa())) {
				rigaPagamento.setRigaModificabile(true);
			} else {
				rigaPagamento.setRigaModificabile(false);
			}

			if (onlyVDSDoc.equals(Boolean.FALSE)) {
				listDocumentiDaValidare.add(rigaPagamento);
			}

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] vociDiSpesa = pagamento
					.getVociDiSpesa();
			if (vociDiSpesa != null && vociDiSpesa.length > 0) {
				for (int y = 0; y < vociDiSpesa.length; y++) {

					if (mapVds.containsKey(vociDiSpesa[y].getIdRigoContoEconomico())) {
						RigaValidazioneItemDTO rigaVoceDiSpesa = mapVds.get(vociDiSpesa[y].getIdRigoContoEconomico());

						Double importoValidatoVoceDiSpesa = rigaVoceDiSpesa.getImportoValidatoVoceDiSpesa();

						Double importoAssociatoVoceDiSpesa = rigaVoceDiSpesa.getImportoAssociatoVoceDiSpesa() != null
								? rigaVoceDiSpesa.getImportoAssociatoVoceDiSpesa()
								: 0d;

						Double oreLavorate = rigaVoceDiSpesa.getOreLavorate();

						if (vociDiSpesa[y].getImportoAssociato() != null) {
							rigaVoceDiSpesa.setImportoAssociatoVoceDiSpesa(
									NumberUtil.sum(importoAssociatoVoceDiSpesa, vociDiSpesa[y].getImportoAssociato()));
						}
						if (vociDiSpesa[y].getImportoValidato() != null) {
							if (importoValidatoVoceDiSpesa != null)
								rigaVoceDiSpesa.setImportoValidatoVoceDiSpesa(NumberUtil.sum(importoValidatoVoceDiSpesa,
										vociDiSpesa[y].getImportoValidato()));
							else
								rigaVoceDiSpesa.setImportoValidatoVoceDiSpesa(vociDiSpesa[y].getImportoValidato());
						}

						if (vociDiSpesa[y].getOreLavorate() != null)
							rigaVoceDiSpesa
									.setOreLavorate(NumberUtil.sum(oreLavorate, vociDiSpesa[y].getOreLavorate()));

						if (rigaVoceDiSpesa.getImportoAssociatoVoceDiSpesa() == null)
							rigaVoceDiSpesa.setImportoAssociatoVoceDiSpesa(0d);
						if (rigaVoceDiSpesa.getOreLavorate() == null)
							rigaVoceDiSpesa.setOreLavorate(0d);

						mapVds.put(vociDiSpesa[y].getIdRigoContoEconomico(), rigaVoceDiSpesa);
					} else {

						RigaValidazioneItemDTO rigaVoceDiSpesa = new RigaValidazioneItemDTO();

						rigaVoceDiSpesa.setRigaPagamento(false);
						rigaVoceDiSpesa.setDescrizioneVoceDiSpesa(vociDiSpesa[y].getDescVoceSpesa());
						rigaVoceDiSpesa.setIdDichiarazioneDiSpesa(vociDiSpesa[y].getIdDichiarazioneSpesa());
						rigaVoceDiSpesa.setIdDocumentoDiSpesa(vociDiSpesa[y].getIdDocumentoDiSpesa());
						rigaVoceDiSpesa.setIdPagamento(pagamento.getIdPagamento());
						rigaVoceDiSpesa.setIdQuotaParte(vociDiSpesa[y].getIdQuotaParte());
						rigaVoceDiSpesa.setIdRigoContoEconomico(vociDiSpesa[y].getIdRigoContoEconomico());

						rigaVoceDiSpesa.setImportoAssociatoVoceDiSpesa(vociDiSpesa[y].getImportoAssociato());

						rigaVoceDiSpesa.setImportoValidatoVoceDiSpesa(vociDiSpesa[y].getImportoValidato());

						rigaVoceDiSpesa.setDataPagamentoVisible(rigaPagamento.getDataPagamentoVisible());
						rigaVoceDiSpesa.setDataValutaVisible(rigaPagamento.getDataValutaVisible());

						if (rigaVoceDiSpesa.getRigaModificabile()) {
							if (vociDiSpesa[y].getImportoValidato() != null)
								rigaVoceDiSpesa.setImportoValidatoVoceDiSpesa(vociDiSpesa[y].getImportoValidato());
						} else {
							rigaVoceDiSpesa.setImportoValidatoVoceDiSpesa(vociDiSpesa[y].getImportoValidato());

						}
						if (onlyVDSDoc.equals(Boolean.TRUE)) {
							rigaVoceDiSpesa.setRigaModificabile(false);
							mapVds.put(vociDiSpesa[y].getIdRigoContoEconomico(), rigaVoceDiSpesa);
						} else {
							if (idDichiarazioneDiSpesa.equals(vociDiSpesa[y].getIdDichiarazioneSpesa())) {
								rigaVoceDiSpesa.setRigaModificabile(true);
								mapVds.put(vociDiSpesa[y].getIdRigoContoEconomico(), rigaVoceDiSpesa);
							} else {
								rigaVoceDiSpesa.setRigaModificabile(false);
							}
						}
					}
				}
			}
		}

		// importo residuo ammesso
		Collection<RigaValidazioneItemDTO> righe = mapVds.values();
		it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO[] elencoVociSpesa;
		elencoVociSpesa = gestioneVociDiSpesaBusinessImpl.findVociDiSpesaAssociateSemplificazione(idUtente, identita,
				idDocumentoDiSpesa, idProgetto);

		for (RigaValidazioneItemDTO rigaVoceDiSpesa : righe) {
			for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO voce : elencoVociSpesa) {
				if (voce.getIdRigoContoEconomico().longValue() == rigaVoceDiSpesa.getIdRigoContoEconomico()
						.longValue()) {
					Double importoResiduoAmmesso = voce.getImportoResiduoAmmesso();
					Double importoResiduoAmmessoOld = rigaVoceDiSpesa.getImportoResiduoAmmesso();
					if (importoResiduoAmmessoOld == null)
						importoResiduoAmmessoOld = 0d;
					if (importoResiduoAmmesso != null)
						rigaVoceDiSpesa.setImportoResiduoAmmesso(
								NumberUtil.sum(importoResiduoAmmessoOld, importoResiduoAmmesso));

					Double importoAmmesso = voce.getImportoFinanziamento();
					Double importoAmmessoOld = rigaVoceDiSpesa.getImportoAmmesso();
					if (importoAmmessoOld == null)
						importoAmmessoOld = 0d;
					if (importoAmmesso != null)
						rigaVoceDiSpesa.setImportoAmmesso(NumberUtil.sum(importoAmmessoOld, importoAmmesso));

				}
			}

		}
		// fine importo residuo ammesso

		listDocumentiDaValidare.addAll(righe);

		return listDocumentiDaValidare;
	}

	public ArrayList<RigaNotaDiCreditoItemDTO> getNoteDiCredito(Long idDocumentoDiSpesa, Long idProgetto, Long idUtente,
			String identitaIride) throws Exception {

		ArrayList<RigaNotaDiCreditoItemDTO> list = new ArrayList<RigaNotaDiCreditoItemDTO>();
		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneNoteDiCreditoDTO dto;
		dto = validazioneRendicontazioneBusinessImpl.findNoteDiCredito(idUtente, identitaIride, idDocumentoDiSpesa,
				idProgetto);

		if (dto != null) {
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.NotaDiCreditoDTO[] note = dto
					.getNoteDiCredito();

			if (note != null && note.length > 0) {

				for (int i = 0; i < note.length; i++) {
					it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.NotaDiCreditoDTO notaDTO = note[i];
					RigaNotaDiCreditoItemDTO riga = new RigaNotaDiCreditoItemDTO();
					riga.setRigaNotaDiCredito(true);
					riga.setDataDocumento(DateUtil.getDate(notaDTO.getDtDocumento()));
					riga.setStatoDocumento(notaDTO.getStatoDocumento());
					riga.setNumeroDocumento(notaDTO.getNumeroDocumento());
					riga.setImportoDocumento(notaDTO.getImportoDocumento());
					riga.setId(notaDTO.getIdNotaDiCredito());
					list.add(riga);
					it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] vociDiSpesa = notaDTO
							.getVociDiSpesa();
					for (int y = 0; y < vociDiSpesa.length; y++) {
						RigaNotaDiCreditoItemDTO rigaVoce = new RigaNotaDiCreditoItemDTO();
						it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO voce = vociDiSpesa[y];
						rigaVoce.setDescrizioneVoceDiSpesa(voce.getDescVoceSpesa());
						rigaVoce.setImportoVoceDiSpesa(voce.getImportoAssociato());
						list.add(rigaVoce);
					}

				}
			}
		}
		return list;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex ValidazioneDematAction.validaSingolo() +
	// GestioneArchivioFileBusinessImpl.validaSingolo()
	public void sospendiDocumento(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, Long idUtente, String idIride, Boolean fromAttivitaValidazione)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::sospendiDocumento] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		LOG.info(prf + "noteValidazione = " + noteValidazione);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		try {

			validazioneRendicontazioneBusinessImpl.saveNoteValidazioneDoc(idUtente, idIride, idDocumentoDiSpesa,
					idProgetto, noteValidazione);

			Long[] listaIdDocSpesa = new Long[] { idDocumentoDiSpesa };
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneAutomatica esitoSrv;
			esitoSrv = validazioneRendicontazioneBusinessImpl.sospendiMultiplo(idUtente, idIride, listaIdDocSpesa,
					idDichiarazioneDiSpesa, fromAttivitaValidazione);

			if (esitoSrv == null || !esitoSrv.getEsito()) {
				throw new Exception("sospensione fallita.");
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la sospensione: ", e);
			throw new DaoException("Errore durante la sospensione.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex ValidazioneDematAction.respingiSingolo() +
	// GestioneArchivioFileBusinessImpl.respingiDocumento()
	public void respingiDocumento(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::respingiDocumento] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		LOG.info(prf + "noteValidazione = " + noteValidazione);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		try {

			validazioneRendicontazioneBusinessImpl.saveNoteValidazioneDoc(idUtente, idIride, idDocumentoDiSpesa,
					idProgetto, noteValidazione);

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneDocumentoDiSpesa esitoSrv;
			esitoSrv = validazioneRendicontazioneBusinessImpl.respingiDocumento(idUtente, idIride, idDocumentoDiSpesa,
					idDichiarazioneDiSpesa);

			if (esitoSrv == null || !esitoSrv.getEsito()) {
				throw new Exception("Respingimento fallito.");
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante il respingimento: ", e);
			throw new DaoException("Errore durante il respingimento.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex ValidazioneDematAction.invalidaSingolo() +
	// GestioneArchivioFileBusinessImpl.invalidaSingolo()
	public void invalidaDocumento(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::invalidaDocumento] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		LOG.info(prf + "noteValidazione = " + noteValidazione);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		try {

			validazioneRendicontazioneBusinessImpl.saveNoteValidazioneDoc(idUtente, idIride, idDocumentoDiSpesa,
					idProgetto, noteValidazione);

			Long[] listaIdDocSpesa = new Long[] { idDocumentoDiSpesa };
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneAutomatica esitoSrv;
			esitoSrv = validazioneRendicontazioneBusinessImpl.invalidaMultiplo(idUtente, idIride, listaIdDocSpesa,
					idDichiarazioneDiSpesa);

			if (esitoSrv == null || !esitoSrv.getEsito()) {
				throw new Exception("Invalidamento fallito.");
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante l'invalidamento: ", e);
			throw new DaoException("Errore durante l'invalidamento.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex ValidazioneDematAction.validaSingolo() +
	// GestioneArchivioFileBusinessImpl.validaSingolo()
	public void validaDocumento(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			String noteValidazione, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::validaDocumento] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		LOG.info(prf + "noteValidazione = " + noteValidazione);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		try {

			validazioneRendicontazioneBusinessImpl.saveNoteValidazioneDoc(idUtente, idIride, idDocumentoDiSpesa,
					idProgetto, noteValidazione);

			Long[] listaIdDocSpesa = new Long[] { idDocumentoDiSpesa };
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneAutomatica esitoSrv;
			esitoSrv = validazioneRendicontazioneBusinessImpl.validaMultiplo(idUtente, idIride, listaIdDocSpesa,
					idDichiarazioneDiSpesa);

			/**
			 * Buono Domiciliarità/Residenzialità - Chiamata al servizio di Validazione
			 * integrazione
			 * 
			 */
			// TODO: Riattivare quando sarà pronto il servizio
//			String response = invioNotificaDomRes(noteValidazione, idDichiarazioneDiSpesa, null, "validaDocumento",
//					"BR69", idDocumentoDiSpesa, esitoSrv.getEsito());

			if (esitoSrv == null || !esitoSrv.getEsito()) {
				throw new Exception("Validazione fallita.");
			}
//			if (response != null && !"OK".equals(response)) {
//				LOG.error(Constants.ERRORE_SERVIZIO_SANITA);
//				throw new Exception(Constants.ERRORE_SERVIZIO_SANITA);
//			}
//		} catch (HttpClientErrorException e) {
//			LOG.error(prf+" ERRORE nella chiamata al servizio: ", e);
//			throw new DaoException("Errore nella chiamata al servizio.", e);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la validazione: ", e);
			throw new DaoException("Errore durante la validazione.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex ValidazioneDematAction.salvaValidaParziale() +
	// GestioneArchivioFileBusinessImpl.salvaValidaParziale()
	// NOTA: se fallisce il check, restituisce esito = false e msg di errore.
	// se fallisce il salvataggio solleva una eccezione per il rollback: tale
	// eccezione viene poi gestita nel servizio rest.
	public EsitoDTO validaParzialmenteDocumento(ValidaParzialmenteDocumentoRequest validaParzialmenteDocumentoRequest,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::validaParzialmenteDocumento] ";
		LOG.info(prf + " BEGIN");

		if (validaParzialmenteDocumentoRequest == null) {
			throw new InvalidParameterException("validaParzialmenteDocumentoRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);
		LOG.info(prf + "validaParzialmenteDocumentoRequest = " + validaParzialmenteDocumentoRequest.toString());

		Long idDichiarazioneDiSpesa = validaParzialmenteDocumentoRequest.getIdDichiarazioneDiSpesa();
		Long idDocumentoDiSpesa = validaParzialmenteDocumentoRequest.getIdDocumentoDiSpesa();
		Long idProgetto = validaParzialmenteDocumentoRequest.getIdProgetto();
		Long idBandoLinea = validaParzialmenteDocumentoRequest.getIdBandoLinea();
		String noteValidazione = validaParzialmenteDocumentoRequest.getNoteValidazione();
		ArrayList<RigaValidazioneItemDTO> righeValidazioneItemModificate = validaParzialmenteDocumentoRequest
				.getRigheValidazioneItem();

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (righeValidazioneItemModificate == null) {
			throw new InvalidParameterException("righeValidazioneItem non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		try {

			// Recupera l'elenco di RigaValidazioneItemDTO passato in input alla popup di
			// validazione.
			ArrayList<RigaValidazioneItemDTO> righeValidazioneItemDB = this.getPagamentiConVdsDemat(idBandoLinea,
					idDocumentoDiSpesa, idProgetto, idDichiarazioneDiSpesa, idUtente, idIride);

			// Copia gli importi modificati a video, nell'array letto da DB.
			this.aggiornaRigheValidazioneItems(righeValidazioneItemDB, righeValidazioneItemModificate);

			// Ex ValidazioneDichiarazioneDiSpesaBusinessImpl.checkDocumentiDaValidare()
			// Il metodo su pbandisrv, in caso di controllo fallito restituisce una
			// eccezione (l'oggetto esito in output contiene sempre esito = true).
			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoValidazioneRendicontazione esitoCheck;
			try {
				esitoCheck = this.checkDocumentiDaValidare(idUtente, idIride, idDocumentoDiSpesa,
						idDichiarazioneDiSpesa, idProgetto, righeValidazioneItemDB);
			} catch (ValidazioneRendicontazioneException e) {
				esito.setEsito(false);
				esito.setMessaggio("Inserire un valore valido nel campo importo validato.");
				return esito;
			}

			// Salvataggio delle modifiche su DB.

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoValidazioneRendicontazione esitoSalvataggio;
			esitoSalvataggio = this.salvaValidaParziale(idUtente, idIride, idDocumentoDiSpesa, idDichiarazioneDiSpesa,
					idProgetto, righeValidazioneItemDB, noteValidazione);

			if (esitoSalvataggio == null || !esitoSalvataggio.getEsito()) {
				// Innalzo eccezione per rollback.
				throw new Exception("Validazione parziale fallita.");
			}

			esito.setEsito(true);
			esito.setMessaggio("Documento salvato correttamente.");

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la validazione parziale: ", e);
			throw new DaoException("Errore durante la validazione parziale.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;

	}

	private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoValidazioneRendicontazione checkDocumentiDaValidare(
			Long idUser, String identitaIride, Long idDocumentoDiSpesa, Long idDichiarazioneDiSpesa, Long idProgetto,
			ArrayList<RigaValidazioneItemDTO> listRigheTabella) throws Exception {

		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO dto;
		dto = creaValidazioneRendicontazioneDTO(idDocumentoDiSpesa, idDichiarazioneDiSpesa, idProgetto,
				listRigheTabella);

		return validazioneRendicontazioneBusinessImpl.checkUpdateDocumentoEVociDiSpesa(idUser, identitaIride, dto);
	}

	// Ex ValidazioneDematAction.populateRigheValidazioneItems() rimaneggiata.
	private void aggiornaRigheValidazioneItems(ArrayList<RigaValidazioneItemDTO> listaDB,
			ArrayList<RigaValidazioneItemDTO> listaModificata) {

		for (RigaValidazioneItemDTO itemModificato : listaModificata) {

			Long idRiga = itemModificato.getIdRigoContoEconomico();
			Double importoModificato = itemModificato.getImportoValidatoVoceDiSpesa();

			for (RigaValidazioneItemDTO itemDB : listaDB) {
				if (!itemDB.getRigaPagamento() && itemDB.getRigaModificabile()) {
					if (idRiga.intValue() == itemDB.getIdRigoContoEconomico().intValue()) {
						itemDB.setImportoValidatoVoceDiSpesa(importoModificato);
					}
				}
			}

		}

	}

	// Ex
	// ValidazioneDichiarazioneDiSpesaBusinessImpl,creaValidazioneRendicontazioneDTO()
	private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO creaValidazioneRendicontazioneDTO(
			Long idDocumentoDiSpesa, Long idDichiarazioneDiSpesa, Long idProgetto,
			ArrayList<RigaValidazioneItemDTO> listRigheTabella) {
		Iterator<RigaValidazioneItemDTO> iterListRow = listRigheTabella.iterator();
		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO pagamento = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO();
		ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO> listPagamenti = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO>();
		ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO> listVociSpesa = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO>();
		while (iterListRow.hasNext()) {

			RigaValidazioneItemDTO row = iterListRow.next();

			if (row.getRigaPagamento()) {
				// carico la lista dei pagamenti
				pagamento = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO();
				pagamento.setIdDichiarazioneDiSpesa(row.getIdDichiarazioneDiSpesa());
				pagamento.setIdPagamento(row.getIdPagamento());
				pagamento.setDescModalitaPagamento(row.getModalitaPagamento());
				if (row.getDataPagamentoVisible())
					pagamento.setDtPagamento(DateUtil.getDate(row.getDataPagamento()));
				if (row.getDataValutaVisible())
					pagamento.setDtValuta(DateUtil.getDate(row.getDataValuta()));

				listPagamenti.add(pagamento);
			} else {
				// carico la lista delle voci di spesa
				if (row.getDescrizioneVoceDiSpesa() != null && row.getRigaModificabile()) {
					it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO voce = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO();
					voce.setIdPagamento(row.getIdPagamento());
					voce.setDescVoceSpesa(row.getDescrizioneVoceDiSpesa());
					voce.setIdQuotaParte(row.getIdQuotaParte());
					voce.setIdDocumentoDiSpesa(row.getIdDocumentoDiSpesa());
					voce.setImportoAssociato(row.getImportoAssociatoVoceDiSpesa());
					if (row.getImportoValidatoVoceDiSpesa() == null) {
						voce.setImportoValidato(0d);
					} else {
						voce.setImportoValidato(row.getImportoValidatoVoceDiSpesa());
					}
					voce.setIdDichiarazioneSpesa(row.getIdDichiarazioneDiSpesa());
					listVociSpesa.add(voce);
				}
			}

		}

		if (listVociSpesa != null && !listVociSpesa.isEmpty()) {
			for (int i = 0; i < listPagamenti.size(); i++) {
				it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO pagamentoDTO = listPagamenti
						.get(i);
				ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO> listVociDiSpesaDelPagamento = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO>();

				for (int y = 0; y < listVociSpesa.size(); y++) {
					it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO voce = listVociSpesa
							.get(y);
					if (voce.getIdPagamento().equals(pagamentoDTO.getIdPagamento())) {
						listVociDiSpesaDelPagamento.add(voce);
					}
				}
				pagamentoDTO.setVociDiSpesa(listVociDiSpesaDelPagamento
						.toArray(new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.VoceDiSpesaDTO[] {}));

			}
		}

		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO dto = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO();
		dto.setIdDocumentoDiSpesa(idDocumentoDiSpesa);
		dto.setIdDichiarazioneDiSpesa(idDichiarazioneDiSpesa);
		dto.setIdProgetto(idProgetto);
		dto.setPagamenti(listPagamenti
				.toArray(new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.PagamentoDTO[] {}));
		return dto;
	}

	// Ex ValidazioneDichiarazioneDiSpesaBusinessImpl.salvaValidaParziale()
	private EsitoValidazioneRendicontazione salvaValidaParziale(Long idUser, String identitaIride, Long idDocumento,
			Long idDichiarazioneDiSpesa, Long idProgetto, ArrayList<RigaValidazioneItemDTO> listRigheTabella,
			String noteValidazione) throws Exception {

		validazioneRendicontazioneBusinessImpl.saveNoteValidazioneDoc(idUser, identitaIride, idDocumento, idProgetto,
				noteValidazione);

		it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO dto;
		dto = creaValidazioneRendicontazioneDTO(idDocumento, idDichiarazioneDiSpesa, idProgetto, listRigheTabella);

		return validazioneRendicontazioneBusinessImpl.updateDocumentoEVociDiSpesa(idUser, identitaIride, dto);
	}

	@Override
	// Ex ReportExcelAction.generaReportDettaglioDocumentoDiSpesa().
	public EsitoLeggiFile reportDettaglioDocumentoDiSpesa(Long idDichiarazioneDiSpesa, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::reportDettaglioDocumentoDiSpesa] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idUtente = " + idUtente);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoReportDettaglioDocumentiDiSpesaDTO esitoSrv;
			esitoSrv = validazioneRendicontazioneBusinessImpl.generaReportDettaglioDocumentoDiSpesa(idUtente, idIride,
					idDichiarazioneDiSpesa);

			if (esitoSrv == null || esitoSrv.getExcelBytes() == null || esitoSrv.getExcelBytes().length == 0) {
				throw new Exception("Report non generato.");
			}

			EsitoLeggiFile esito = new EsitoLeggiFile();
			esito.setNomeFile(esitoSrv.getNomeFile());
			esito.setBytes(esitoSrv.getExcelBytes());

			return esito;

			// return esito.getExcelBytes();
			// String nomeFile = esito.getNomeFile();

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la generazione del report: ", e);
			throw new DaoException("Errore durante la generazione del report.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	// Ex CPBEValidazioneDocumentiDiSpesa.prepareOperazioneAutomatica().
	public EsitoVerificaOperazioneMassivaDTO verificaOperazioneMassiva(
			VerificaOperazioneMassivaRequest verificaOperazioneMassivaRequest, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::verificaOperazioneMassiva] ";
		LOG.info(prf + " BEGIN");

		if (verificaOperazioneMassivaRequest == null) {
			throw new InvalidParameterException("verificaOperazioneMassivaRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);
		LOG.info(prf + "verificaOperazioneMassivaRequest = " + verificaOperazioneMassivaRequest.toString());

		String operazione = verificaOperazioneMassivaRequest.getOperazione();
		Boolean tutti = verificaOperazioneMassivaRequest.getTutti();
		ArrayList<Long> idDocumentiDiSpesaDaElaborare = verificaOperazioneMassivaRequest
				.getIdDocumentiDiSpesaDaElaborare();
		Long idDichiarazioneDiSpesa = verificaOperazioneMassivaRequest.getIdDichiarazioneDiSpesa();
		Long idBandoLinea = verificaOperazioneMassivaRequest.getIdBandoLinea();

		if (StringUtils.isBlank(operazione)) {
			throw new InvalidParameterException("operazione non valorizzato.");
		}
		if (!ObjectUtil.in(operazione, Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_VALIDARE,
				Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_INVALIDARE,
				Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_RESPINGERE)) {
			throw new InvalidParameterException("operazione non valorizzato correttamente.");
		}
		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (tutti == null) {
			throw new InvalidParameterException("tutti non valorizzato.");
		}

		EsitoVerificaOperazioneMassivaDTO esito = new EsitoVerificaOperazioneMassivaDTO();

		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoCheckOperazioneAutomatica esitoCheck;
			if (tutti) {
				esitoCheck = validazioneRendicontazioneBusinessImpl.checkPagamentiPerOperazioneAutomaticaTutti(idUtente,
						idIride, idBandoLinea, idDichiarazioneDiSpesa);
			} else {
				esitoCheck = validazioneRendicontazioneBusinessImpl.checkPagamentiPerOperazioneAutomaticaSelettiva(
						idUtente, idIride, idBandoLinea, idDichiarazioneDiSpesa,
						idDocumentiDiSpesaDaElaborare.toArray(new Long[idDocumentiDiSpesaDaElaborare.size()]));
			}

			LOG.info(prf + "esitoCheck: esito = " + esitoCheck.getEsito() + "; message = " + esitoCheck.getMessage());

			if (!esitoCheck.getEsito()) {
				esito.setEsito(false);
				esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitoCheck.getMessage()));
				return esito;
			}

			ArrayList<Long> idDocumenti = new ArrayList<Long>();
			for (Long id : esitoCheck.getIdDocumenti()) {
				idDocumenti.add(id);
			}

			String target = tutti ? "TUTTI" : idDocumenti.size() + "";
			String messaggio = "L\u2019operazione richiesta provveder\u00E0 a " + operazione
					+ " automaticamente i documenti della dichiarazione corrente al momento non ancora elaborati e in base alla scelta effettuata ("
					+ target + "). Continuare?";

			String messaggioImportoAmmissibileSuperato = null;
			String ERRORE_IMPORTO_AMMISSIBILE_SUPERATO = "W.MX2";
			if (Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_VALIDARE.equals(operazione)
					&& ERRORE_IMPORTO_AMMISSIBILE_SUPERATO.equals(esitoCheck.getMessage())) {
				messaggioImportoAmmissibileSuperato = "Confermando l\u2019operazione, almeno una voce di spesa del conto economico risulter\u00E0 validata per un importo complessivo maggiore del totale ammesso per la voce (si consiglia di visionare il conto economico).";
			}

			esito.setEsito(true);
			esito.setMessaggio(messaggio);
			esito.setMessaggioImportoAmmissibileSuperato(messaggioImportoAmmissibileSuperato);
			esito.setIdDocumenti(idDocumenti);

			return esito;

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la verifica della operazione massiva: ", e);
			throw new DaoException("Errore durante la verifica della operazione massiva.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex CPBEValidazioneDocumentiDiSpesa.confermaOperazioneAutomaticaValidazione().
	public EsitoDTO operazioneMassiva(OperazioneMassivaRequest operazioneMassivaRequest, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::operazioneMassiva] ";
		LOG.info(prf + " BEGIN");

		if (operazioneMassivaRequest == null) {
			throw new InvalidParameterException("operazioneMassivaRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);
		LOG.info(prf + "operazioneMassivaRequest = " + operazioneMassivaRequest.toString());

		String operazione = operazioneMassivaRequest.getOperazione();
		ArrayList<Long> idDocumentiDiSpesaDaElaborare = operazioneMassivaRequest.getIdDocumentiDiSpesaDaElaborare();
		Long idDichiarazioneDiSpesa = operazioneMassivaRequest.getIdDichiarazioneDiSpesa();

		if (StringUtils.isBlank(operazione)) {
			throw new InvalidParameterException("operazione non valorizzato.");
		}
		if (!ObjectUtil.in(operazione, Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_VALIDARE,
				Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_INVALIDARE,
				Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_RESPINGERE)) {
			throw new InvalidParameterException("operazione non valorizzato correttamente.");
		}
		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentiDiSpesaDaElaborare == null || idDocumentiDiSpesaDaElaborare.size() == 0) {
			throw new InvalidParameterException("idDocumentiDiSpesaDaElaborare non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();

		try {

			Long[] idDocumenti = new Long[idDocumentiDiSpesaDaElaborare.size()];
			idDocumenti = idDocumentiDiSpesaDaElaborare.toArray(idDocumenti);

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneAutomatica esitoSrv = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoOperazioneAutomatica();
			if (Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_VALIDARE.equalsIgnoreCase(operazione)) {
				esitoSrv = validazioneRendicontazioneBusinessImpl.validaMultiplo(idUtente, idIride, idDocumenti,
						idDichiarazioneDiSpesa);
			} else if (Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_INVALIDARE
					.equalsIgnoreCase(operazione)) {
				esitoSrv = validazioneRendicontazioneBusinessImpl.invalidaMultiplo(idUtente, idIride, idDocumenti,
						idDichiarazioneDiSpesa);
			} else if (Constants.VALUE_RADIO_OPERAZIONE_AUTOMATICA_VALIDAZIONE_RESPINGERE
					.equalsIgnoreCase(operazione)) {
				esitoSrv = validazioneRendicontazioneBusinessImpl.respingiMultiplo(idUtente, idIride, idDocumenti,
						idDichiarazioneDiSpesa);
			}

			esito.setEsito(esitoSrv.getEsito());
			if (esito.getEsito()) {
				esito.setMessaggio("Operazione conclusa con successo.");
			} else {
				esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esito.getMessaggio()));
			}

			return esito;

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante l'operazione massiva: ", e);
			throw new DaoException("Errore durante l'operazione massiva.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex ValidazioneDematAction.salvaRichiestaIntegrazioneDS().
	public EsitoDTO richiediIntegrazione(RichiediIntegrazioneRequest richiediIntegrazioneRequest, Long idUtente,
			String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::richiediIntegrazione] ";
		LOG.info(prf + " BEGIN");

		if (richiediIntegrazioneRequest == null) {
			throw new InvalidParameterException("richiediIntegrazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);
		LOG.info(prf + richiediIntegrazioneRequest.toString());

		String noteIntegrazione = richiediIntegrazioneRequest.getNoteIntegrazione();
		Long idDichiarazioneDiSpesa = richiediIntegrazioneRequest.getIdDichiarazioneDiSpesa();

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		try {
			java.sql.Date oggi = DateUtil.getSysdate();

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO dto = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO();
			dto.setIdDichiarazioneSpesa(idDichiarazioneDiSpesa);
			dto.setDescrizione(noteIntegrazione);
			dto.setIdIntegrazioneSpesa(null);
			dto.setDataRichiesta(oggi);
			dto.setIdUtenteRichiesta(idUtente);

			Long idIntegrazioneSpesa = validazioneRendicontazioneBusinessImpl.salvaIntegrazioneSpesa(idUtente, idIride,
					dto);

			/**
			 * Buono Domiciliarità/Residenzialità - Chiamata al servizio di Validazione
			 * integrazione
			 * 
			 */
			String response = invioNotificaDomRes(noteIntegrazione, idDichiarazioneDiSpesa, idIntegrazioneSpesa,
					"richiediIntegrazione", "BR79", null, false);

			if (response == null || (response != null && "OK".equals(response))) {
				esito.setEsito(true);
				esito.setMessaggio("Inserimento della richiesta avvenuto con successo.");
			} else {
				esito.setEsito(false);
				esito.setMessaggio(Constants.ERRORE_SERVIZIO_SANITA + response);
			}

			return esito;
		} catch (HttpClientErrorException e) {
			LOG.error(prf + " ERRORE nella chiamata al servizio: ", e);
			throw new DaoException("Errore nella chiamata al servizio.", e);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la richiesta di integrazione: ", e);
			throw new DaoException("Errore durante la richiesta di integrazione.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EsitoDTO newRichiediIntegrazione(MultipartFormDataInput form) throws Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::newRichiediIntegrazione] ";
		LOG.info(prf + " BEGIN");

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(Boolean.FALSE);

		// Form data
		Long idUtente;
		BigDecimal idStatoRichiesta;
		Long idDichiarazioneSpesa;
		Long numGgScadenza;
		String motivazione;

		try {
			idUtente = form.getFormDataPart("idUtente", Long.class, null);
			motivazione = form.getFormDataPart("motivazione", String.class, null);
			numGgScadenza = form.getFormDataPart("numGgScadenza", Long.class, null);
			idStatoRichiesta = form.getFormDataPart("idStatoRichiesta", BigDecimal.class, null);
			idDichiarazioneSpesa = form.getFormDataPart("idDichiarazioneSpesa", Long.class, null);

		} catch (Exception e) {
			LOG.error(prf + " Errore lettura parametri FormData: ",e);
			throw e;
		}

		Long idIntegrazioneSpesa;

		try {

			// Trovo l'id_proposta da collegare alla lettera
			String getIdIntegrazione = "select SEQ_PBANDI_T_INTEGRAZ_SPESA.nextval from dual";
			LOG.info(prf + "\nQuery: \n\n" + getIdIntegrazione + "\n\n");
			idIntegrazioneSpesa = getJdbcTemplate().queryForObject(getIdIntegrazione, Long.class);

			String queryNew = "INSERT INTO PBANDI.PBANDI_T_INTEGRAZIONE_SPESA (\r\n"
					+ "ID_INTEGRAZIONE_SPESA, ID_DICHIARAZIONE_SPESA, DESCRIZIONE,\r\n"
					+ "DATA_RICHIESTA, ID_UTENTE_RICHIESTA, NUM_GIORNI_SCADENZA, ID_STATO_RICHIESTA)\r\n"
					+ "VALUES (?, ?, ?, sysdate, ?, ?, ?)";
			
			Object[] args = {idIntegrazioneSpesa,idDichiarazioneSpesa,motivazione,idUtente,numGgScadenza,idStatoRichiesta};
			getJdbcTemplate().update(queryNew, args);

		} catch (Exception e) {
			LOG.error(prf + " Errore durante l'inserimento della richiesta di integrazione: ", e);
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		esito.setEsito(true);
		esito.setId(idIntegrazioneSpesa);
		esito.setMessaggio("Inserimento della richiesta avvenuto con successo.");

		return esito;

	}

	@Override
	public EsitoDTO chiudiRichiestaIntegrazione(Long idIntegrazione, HttpServletRequest req) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::chiudiRichiestaIntegrazione]";
		LOG.info(prf + " BEGIN");

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(false);
		esito.setId(idIntegrazione);
		esito.setMessaggio("Errore durante la chiusura della richiesta.");
		int rowsUpdated = 0;

		try {
			UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
			Object[] args = new Object[] { userInfoSec.getIdUtente(), idIntegrazione };
			int[] types = new int[] { Types.INTEGER, Types.INTEGER };

			String query = "UPDATE PBANDI_T_INTEGRAZIONE_SPESA \n" + "SET DT_CHIUSURA_UFFICIO = CURRENT_DATE,\n"
					+ "DATA_INVIO = NULL,\n" + "ID_UTENTE_INVIO = ?,\n" + "ID_STATO_RICHIESTA = 3 \n"
					+ "WHERE ID_INTEGRAZIONE_SPESA = ?";
			LOG.debug(query);
			rowsUpdated = getJdbcTemplate().update(query, args, types);

			query = "UPDATE PBANDI_T_PROROGA SET\n" + "ID_STATO_PROROGA = 4,\n" + "ID_UTENTE_AGG = ?,\n"
					+ "DT_AGGIORNAMENTO = CURRENT_DATE\n" + "WHERE ID_RICHIESTA_PROROGA IN (\n"
					+ "\tSELECT proroga.id_richiesta_proroga\n" + "\tFROM PBANDI_T_PROROGA proroga \n"
					+ "\tJOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n"
					+ "\tJOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = proroga.id_target\n"
					+ "\tWHERE proroga.id_stato_proroga = 1\n"
					+ "\tAND entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n"
					+ "\tAND integrazione.id_integrazione_spesa = ?\n" + ")";
			LOG.debug(query);
			getJdbcTemplate().update(query, args, types);

			query = "DELETE FROM PBANDI_T_FILE_ENTITA\n" + "WHERE ID_INTEGRAZIONE_SPESA = ?";
			getJdbcTemplate().update(query, idIntegrazione);
		} catch (Exception ignored) {
		}
		if (rowsUpdated > 0) {
			esito.setEsito(true);
			esito.setId(idIntegrazione);
			esito.setMessaggio("Chiusura della richiesta avvenuta con successo.");
		}

		return esito;
	}

	@Override
	@Transactional
	public String salvaAllegatoIntegrazione(MultipartFormDataInput multipartFormData, Long idWhatever) throws Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::salvaAllegatoIntegrazione]";
		logger.info(prf + "BEGIN");

		BigDecimal idTipoDocumentoIndex;
		BigDecimal idEntita;

		// Form data
		File filePart = null;
		String nomeFile = null;
		Boolean visibilitaFile = Boolean.FALSE;
		BigDecimal idTarget = null;
		BigDecimal idProgetto = null;
		Long idUtente = null;

		try {
			filePart = multipartFormData.getFormDataPart("file", File.class, null);
			
			if (filePart != null) {
				idUtente = multipartFormData.getFormDataPart("idUtente", Long.class, null);
				nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class, null);
				visibilitaFile = multipartFormData.getFormDataPart("visibilitaLettera", String.class, null).equals("1");
				idProgetto = multipartFormData.getFormDataPart("idProgetto", BigDecimal.class, null);
			}

		} catch (Exception e) {
			LOG.error(prf + "Errore nei parametri del FormData", e);
			throw e;
		}
		if (filePart != null) {
			idTipoDocumentoIndex = BigDecimal.valueOf(35);
			idEntita = BigDecimal.valueOf(453);
			idTarget = BigDecimal.valueOf(idWhatever);
	
			FileDTO file = new FileDTO();
			String descBreveTipoDocIndex;
	
			try {
	
				// Legge il file firmato dal multipart.
				Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
				List<InputPart> listInputPart = map.get("file");
	
				if (listInputPart == null) {
					logger.info("listInputPart NULLO");
				} else {
					logger.info("listInputPart SIZE = " + listInputPart.size());
				}
	
				for (InputPart i : listInputPart) {
					MultivaluedMap<String, String> m = i.getHeaders();
					Set<String> s = m.keySet();
					for (String x : s) {
						logger.info("SET = " + x);
					}
				}
	
				file.setBytes(FileUtil.getBytesFromFile(filePart));
				// FileUtil.leggiFileDaMultipart(listInputPart, null, nomeFile);
	
				// Long idtipoindex= (long);
	
				descBreveTipoDocIndex = getJdbcTemplate()
						.queryForObject("select DESC_BREVE_TIPO_DOC_INDEX " + "from PBANDI_C_TIPO_DOCUMENTO_INDEX \r\n"
								+ " where ID_TIPO_DOCUMENTO_INDEX=" + idTipoDocumentoIndex, String.class);
				if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
					throw new Exception("Tipo documento index non trovato.");
	
			} catch (Exception e) {
				LOG.error(prf + "Errore nella lettura del file", e);
				throw e;
			}
	
			try {
	
				Date currentDate = new Date(System.currentTimeMillis());
	
				PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
				if (!visibilitaFile) {
					documentoIndexVO.setFlagVisibileBen("N");
				} else {
					documentoIndexVO.setFlagVisibileBen("S");
				}
				documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
				documentoIndexVO.setNomeFile(nomeFile);
				documentoIndexVO.setIdTarget(idTarget);
				documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
				documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
				documentoIndexVO.setIdEntita(idEntita);
				documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
				// PBANDI_T_PROGETTO
				documentoIndexVO.setIdProgetto(idProgetto); // id della PBANDI_T_PROGETTO
				documentoIndexVO.setRepository(descBreveTipoDocIndex);
				documentoIndexVO.setUuidNodo("UUID");
	
				// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
				Boolean result = documentoManager.salvaFile(file.getBytes(), documentoIndexVO);
	
				if (!result) {
					return "Errore in documentoManager.salvaFile(), il metodo ha restituito false";
				}
	
			} catch (Exception e) {
				LOG.error(prf + " errore inserimento pbandi_t_documento_index " + e);
				throw e;
			}
		} else {
			LOG.info(prf+" Lettera accompagnatoria non presente per questa richiesta integrazione.");
		}

		return "OK";
	}

	// salvaReportValidazione
	// salvaChecklistInterna

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex CPBEValidazioneDocumentiDiSpesa.vaiChiudiValidazione().
	// Il bottone a video si chiama 'Chiudi Validazione', ma in realtà non chiude
	// nulla:
	// verifica i documenti e salva su db il flag cartaceo.
	// In caso di buon esito, angular apre una popup in cui inserire eventuali note.
	// Tale popup apre poi la checlkist.
	public EsitoDTO chiudiValidazione(Long idDichiarazioneDiSpesa, Long idDocumentoIndex, Long idBandoLinea,
			Boolean invioExtraProcedura, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::chiudiValidazione] ";
		LOG.info(prf + " BEGIN");

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idBandoLinea == null) {
			throw new InvalidParameterException("idBandoLinea non valorizzato.");
		}
		if (invioExtraProcedura == null) {
			throw new InvalidParameterException("invioExtraProcedura non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoIndex = " + idDocumentoIndex
				+ "; idBandoLinea = " + idBandoLinea + "; invioExtraProcedura = " + invioExtraProcedura
				+ "; idUtente = " + idUtente);

		EsitoDTO esito = new EsitoDTO();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoControlloDocumenti esitoCheckDoc;
			esitoCheckDoc = validazioneRendicontazioneBusinessImpl.checkStatoDocumenti(idUtente, idIride,
					idDichiarazioneDiSpesa);

			if (esitoCheckDoc != null && esitoCheckDoc.getEsito()) {

				esito.setEsito(true);

				// salva FlagInvioCartaceo
				boolean b = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride, idBandoLinea,
						RegoleConstants.BR38_RENDICONTAZIONE_AUTOMATIZZATA);
				LOG.info(prf + "Abilitazione BR38_RENDICONTAZIONE_AUTOMATIZZATA = " + b);

				if (b) {

					String flag = (invioExtraProcedura ? "S" : "N");

					String sql = new String();
					sql = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET FLAG_FIRMA_CARTACEA = '" + flag
							+ "' WHERE ID_DOCUMENTO_INDEX = " + idDocumentoIndex;
					LOG.info(prf + sql);
					getJdbcTemplate().update(sql.toString());

				}

			} else {
				esito.setEsito(false);
				esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitoCheckDoc.getMessage()));
			}

			return esito;

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la chiusura validazione: ", e);
			throw new DaoException("Errore durante la chiusura validazione.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex CPBEChiudiValidazioneDocumentoDiSpesa.verificaLockEstatoChecklist().
	public EsitoDTO proseguiChiudiValidazione(ProseguiChiudiValidazioneRequest proseguiChiudiValidazioneRequest,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::proseguiChiudiValidazione] ";
		LOG.info(prf + " BEGIN");

		if (proseguiChiudiValidazioneRequest == null) {
			throw new InvalidParameterException("proseguiChiudiValidazioneRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);
		LOG.info(prf + proseguiChiudiValidazioneRequest.toString());

		Long idProgetto = proseguiChiudiValidazioneRequest.getIdProgetto();
		Long idDichiarazioneDiSpesa = proseguiChiudiValidazioneRequest.getIdDichiarazioneDiSpesa();
		Boolean dsIntegrativaConsentita = proseguiChiudiValidazioneRequest.getDsIntegrativaConsentita();
		ArrayList<Long> idAppaltiSelezionati = proseguiChiudiValidazioneRequest.getIdAppaltiSelezionati();
		String noteChiusura = proseguiChiudiValidazioneRequest.getNoteChiusura();

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		try {

			if (dsIntegrativaConsentita == null) {
				dsIntegrativaConsentita = false;
			}

			List<it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO> appalti = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO>();
			if (idAppaltiSelezionati != null) {
				for (Long idAppaltoSelezionato : idAppaltiSelezionati) {
					// String idAppFormatted = replaceChar(idAppaltoSelezionato, "[.]", "");
					// long idAppalto = NumberUtil.toLong(idAppFormatted);
					it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO dto = new it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO();
					dto.setIdProgetto(idProgetto);
					dto.setIdAppalto(idAppaltoSelezionato);
					appalti.add(dto);
				}
			}

			LOG.info(prf + "Chiamo gestioneAppaltiBusinessImpl.insertAppaltiChecklist()");
			it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.EsitoGestioneAppalti esitoSrv;
			esitoSrv = gestioneAppaltiBusinessImpl.insertAppaltiChecklist(idUtente, idIride, idProgetto,
					appalti.toArray(new it.csi.pbandi.pbweb.pbandisrv.dto.gestioneappalti.AppaltoProgettoDTO[0]),
					Constants.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_VALIDAZIONE);

			if (!esitoSrv.getEsito()) {
				esito.setEsito(false);
				esito.setMessaggio(ErrorMessages.ERRORE_GENERICO);
				LOG.info(prf + "gestioneAppaltiBusinessImpl.insertAppaltiChecklist() fallita: " + esito.getMessaggio());
				return esito;
			} else {

				LOG.info(prf + "Chiamo checkListBusinessImpl.salvaLockCheckListValidazione()");
				it.csi.pbandi.pbweb.pbandisrv.dto.checklist.EsitoLockCheckListDTO esitoLockCheckList;
				esitoLockCheckList = checkListBusinessImpl.salvaLockCheckListValidazione(idUtente, idIride, idProgetto,
						idDichiarazioneDiSpesa);

				if (esitoLockCheckList.getEsito()) {

					// Nel vecchio statoChiudiValidazioneCheckList è salvato in sessione: qui che ne
					// faccio?
					String statoChiudiValidazioneCheckList;
					if (StringUtil.isEmpty(esitoLockCheckList.getDescBreveStato())) {
						statoChiudiValidazioneCheckList = "ALL";
					} else if (esitoLockCheckList.getDescBreveStato().equals(Constants.STATO_TIPO_DOC_INDEX_BOZZA)) {
						statoChiudiValidazioneCheckList = "ALL";
					} else if (esitoLockCheckList.getDescBreveStato()
							.equals(Constants.STATO_TIPO_DOC_INDEX_DEFINITIVO)) {
						statoChiudiValidazioneCheckList = "";
					} else {
						statoChiudiValidazioneCheckList = "";
					}

					if (noteChiusura != null) {
						validazioneRendicontazioneBusinessImpl.salvaNoteChiusuraValidazione(idUtente, idIride,
								idDichiarazioneDiSpesa, noteChiusura);
					}

				} else {
					esito.setEsito(false);
					esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitoLockCheckList.getCodiceMessaggio()));
					LOG.info(prf + "checkListBusinessImpl.salvaLockCheckListValidazione() fallita: "
							+ esito.getMessaggio());
					return esito;
				}
			}

			esito.setEsito(true);

			return esito;

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la chiusura della validazione: ", e);
			throw new DaoException("Errore durante la chiusura della validazione.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	private String replaceChar(String source, String oldChar, String newChar) {
		if (source == null)
			return null;
		return source.replaceAll(oldChar, newChar);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex DigitalSignAction.saveProtocollo()().
	public Boolean salvaProtocollo(Long idDocumentoIndex, String protocollo, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::salvaProtocollo] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; protocollo = +protocollo"
				+ "; idUtente = +idUtente");

		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (StringUtils.isBlank(protocollo)) {
			throw new InvalidParameterException("protocollo non valorizzato.");
		}

		try {

			return digitalSignBusinessImpl.saveProtocollo(idUtente, idIride, idDocumentoIndex, protocollo);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante il salvataggio del protocollo: ", e);
			throw new DaoException("Errore durante il salvataggio del protocollo.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

	@Override
	public Object getModuloCheckListValidazioneHtml(Long idUtente, String identitaDigitale, Long idProgetto,
			Long idDichiarazioneDiSpesa, String soggettoControllore) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::getModuloCheckListValidazioneHtml]";
		LOG.info(prf + " BEGIN");

		if (idProgetto == null) {
			LOG.error(prf + " InvalidParameterException, idProgetto null");
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idDichiarazioneDiSpesa == null) {
			LOG.error(prf + " InvalidParameterException, idDichiarazioneDiSpesa null");
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato");
		}
		if (idUtente == null) {
			LOG.error(prf + " InvalidParameterException, idUtente null");
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		LOG.info(prf + " idUtente=" + idUtente);
		LOG.info(prf + " identitaDigitale=" + identitaDigitale);
		LOG.info(prf + " soggettoControllore=" + soggettoControllore);
		LOG.info(prf + " idProgetto=" + idProgetto);
		LOG.info(prf + " idDichiarazioneDiSpesa=" + idDichiarazioneDiSpesa);

		ChecklistHtmlDTO checklistHtmlDTO = null;
		try {

			checklistHtmlDTO = checklistHtmlBusinessImpl.getModuloCheckListValidazioneHtml(idUtente, identitaDigitale,
					idProgetto, soggettoControllore, idDichiarazioneDiSpesa);

			if (checklistHtmlDTO == null
					|| (checklistHtmlDTO != null && StringUtils.isBlank(checklistHtmlDTO.getContentHtml()))) {
				LOG.warn(prf + "Attenzione! Modulo html non configurato per progetto: " + idProgetto);
			} else {
				LOG.info(prf + "prime 100 righe xx chklist validazione : \n"
						+ checklistHtmlDTO.getContentHtml().substring(0, 100));
			}

		} catch (CSIException e) {
			LOG.error(prf + " errore " + e.getMessage());

		} finally {
			LOG.info(prf + " END");
		}
		return checklistHtmlDTO;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO saveCheckListDocumentaleHtml(
			Long idUtente, String idIride, Long idProgetto, String statoChecklist, ChecklistHtmlDTO checkListHtmlDTO,
			Long idDichiarazioneDiSpesa) throws InvalidParameterException, Exception {

		String prf = "[ValidazioneRendicontazioneDAOImpl::saveCheckListDocumentaleHtml]";
		LOG.info(prf + " BEGIN");

		if (idProgetto == null) {
			LOG.error(prf + " InvalidParameterException, idProgetto null");
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idDichiarazioneDiSpesa == null) {
			LOG.error(prf + " InvalidParameterException, idDichiarazioneDiSpesa null");
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato");
		}
		if (idUtente == null) {
			LOG.error(prf + " InvalidParameterException, idUtente null");
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		LOG.info(prf + " idUtente=" + idUtente);
		LOG.info(prf + " idIride=" + idIride);
		LOG.info(prf + " idProgetto=" + idProgetto);
		LOG.info(prf + " idDichiarazioneDiSpesa=" + idDichiarazioneDiSpesa);

		it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO esito = checklistHtmlBusinessImpl
				.saveCheckListDocumentaleHtml(idUtente, idIride, idProgetto, statoChecklist, checkListHtmlDTO,
						idDichiarazioneDiSpesa);

		LOG.info(prf + " END");
		return esito;
	}

	// PK vecchio con 4 parametri IN
	public EsitoValidazioneRendicontazionePiuGreen chiudiValidazioneChecklistHtml(Long idUtente, String idIride,
			IstanzaAttivitaDTO istanza, ValidazioneRendicontazioneDTO validazioneDTO) throws Exception {

		String prf = "[ValidazioneRendicontazioneDAOImpl::chiudiValidazioneChecklistHtml]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " validazioneDTO=" + validazioneDTO.toString());

		EsitoValidazioneRendicontazionePiuGreen esitoRendicontazionePiuGreen = null;

		try {
			EsitoValidazioneRendicontazione esito = validazioneRendicontazioneBusinessImpl
					.chiudiValidazioneChecklistHtml(idUtente, idIride, istanza, validazioneDTO);
			LOG.info(prf + " esito=" + esito);

			esitoRendicontazionePiuGreen = beanUtil.transform(esito, EsitoValidazioneRendicontazionePiuGreen.class);
			LOG.info(prf + " esitoRendicontazionePiuGreen=" + esitoRendicontazionePiuGreen);

			DatiPiuGreenDTO piuGreen = gestioneDatiGeneraliBusinessImpl.findDatiPiuGreen(idUtente, idIride,
					validazioneDTO.getIdProgetto());
			LOG.info(prf + " piuGreen=" + piuGreen);

			if (piuGreen != null && piuGreen.getIdProgettoContributo() != null) {

				ValidazioneRendicontazioneDTO validazionePiuGreenDTO = beanUtil.transform(validazioneDTO,
						ValidazioneRendicontazioneDTO.class);
				validazionePiuGreenDTO.setIdProgetto(piuGreen.getIdProgettoContributo());

				// +Green: nel caso di DS Integrativa su db ci sono 2 DS: l'id della DS
				// contributo sta in DatiPiuGreen.IdDichSpesaIntegrativa
				// nel caso di DS Finale su db c'è 1 sola DS e
				// DatiPiuGreen.IdDichSpesaIntegrativa è nullo.
				if (piuGreen.getIdDichSpesaIntegrativa() != null)
					validazionePiuGreenDTO.setIdDichiarazioneDiSpesa(piuGreen.getIdDichSpesaIntegrativa());

				LOG.info(prf + "ID PROGETTO ----> " + validazioneDTO.getIdProgetto() + " ID PROGETTO CONTRIBUTO ---> "
						+ piuGreen.getIdProgettoContributo() + " ID DS INTEGRATIVA CONTRIBUTO ---> "
						+ piuGreen.getIdDichSpesaIntegrativa());
				EsitoValidazioneRendicontazione esitoPiuGreen = validazioneRendicontazioneBusinessImpl
						.chiudiValidazioneChecklistHtml(idUtente, idIride, istanza, validazionePiuGreenDTO);
				LOG.info(prf + "esitoPiuGreen=" + esitoPiuGreen);
				esitoRendicontazionePiuGreen
						.setIdDocIndexDichiarazionePiuGreen(esitoPiuGreen.getIdDocIndexDichiarazione());
			}

		} catch (Exception e) {
			LOG.error(prf + "ERRORE validazioneRendicontazioneSrv.chiudiValidazioneChecklistHtml " + e.getMessage());
			checkListBusinessImpl.eliminaLockCheckListValidazione(idUtente, idIride, validazioneDTO.getIdProgetto(),
					validazioneDTO.getIdDichiarazioneDiSpesa());
			throw e;
		}

		LOG.info(prf + "esito=" + esitoRendicontazionePiuGreen.getEsito());

		String message = beanUtil.getPropertyValueByName(esitoRendicontazionePiuGreen, "message", String.class);
		LOG.info(prf + "message=" + message);

		if (!beanUtil.getPropertyValueByName(esitoRendicontazionePiuGreen, "esito", Boolean.class)) {

			// MessageManager.setGlobalError(result, message,
			// getBeanUtil().getPropertyValueByName(esito, "params", String[].class));
//			String[] arrayY = beanUtil.getPropertyValueByName(esitoRendicontazionePiuGreen, "params", String[].class);
//			LOG.info(prf + "arrayY="+arrayY);
//			
//			for (String str : arrayY) {
//				message = message + ", " + str;
//			}

			Collection<String> errori = setGlobalError(message,
					beanUtil.getPropertyValueByName(esitoRendicontazionePiuGreen, "params", String[].class));

			for (String str : errori) {
				message = message + ", " + str;
			}
		}

		esitoRendicontazionePiuGreen.setMessage(message);

		LOG.info(prf + " END");
		return esitoRendicontazionePiuGreen;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EsitoValidazioneRendicontazionePiuGreen chiudiValidazioneChecklistHtml(Long idUtente, String idIride,
			IstanzaAttivitaDTO istanza, ValidazioneRendicontazioneDTO validazioneDTO, Long checklistSelezionata,
			FileDTO[] verbali) throws Exception {

		String prf = "[ValidazioneRendicontazioneDAOImpl::chiudiValidazioneChecklistHtml]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " validazioneDTO=" + validazioneDTO.toString());

		EsitoValidazioneRendicontazionePiuGreen esitoRendicontazionePiuGreen = null;
		EsitoValidazioneRendicontazione esito = null;

		try {
			// #####->

			/**
			 * Buono Domiciliarità/Residenzialità - Chiamata al servizio di Validazione
			 * integrazione
			 * 
			 */
			String response = invioNotificaDomRes("", validazioneDTO.getIdDichiarazioneDiSpesa(), null,
					"chiudiValidazioneChecklistHtml", "BR69", null, false);

			if (response == null || (response != null && "OK".equals(response))) {

				esito = validazioneRendicontazioneBusinessImpl.chiudiValidazioneChecklistHtml(idUtente, idIride,
						istanza, validazioneDTO, checklistSelezionata, verbali);
				LOG.info(prf + " esito=" + esito);

				esitoRendicontazionePiuGreen = beanUtil.transform(esito, EsitoValidazioneRendicontazionePiuGreen.class);
				LOG.info(prf + " esitoRendicontazionePiuGreen=" + esitoRendicontazionePiuGreen);

				DatiPiuGreenDTO piuGreen = gestioneDatiGeneraliBusinessImpl.findDatiPiuGreen(idUtente, idIride,
						validazioneDTO.getIdProgetto());
				LOG.info(prf + " piuGreen=" + piuGreen);

				if (piuGreen != null && piuGreen.getIdProgettoContributo() != null) {

					ValidazioneRendicontazioneDTO validazionePiuGreenDTO = beanUtil.transform(validazioneDTO,
							ValidazioneRendicontazioneDTO.class);
					validazionePiuGreenDTO.setIdProgetto(piuGreen.getIdProgettoContributo());

					// +Green: nel caso di DS Integrativa su db ci sono 2 DS: l'id della DS
					// contributo sta in DatiPiuGreen.IdDichSpesaIntegrativa
					// nel caso di DS Finale su db c'è 1 sola DS e
					// DatiPiuGreen.IdDichSpesaIntegrativa è nullo.
					if (piuGreen.getIdDichSpesaIntegrativa() != null)
						validazionePiuGreenDTO.setIdDichiarazioneDiSpesa(piuGreen.getIdDichSpesaIntegrativa());

					LOG.info(prf + "ID PROGETTO ----> " + validazioneDTO.getIdProgetto()
							+ " ID PROGETTO CONTRIBUTO ---> " + piuGreen.getIdProgettoContributo()
							+ " ID DS INTEGRATIVA CONTRIBUTO ---> " + piuGreen.getIdDichSpesaIntegrativa());
					EsitoValidazioneRendicontazione esitoPiuGreen = validazioneRendicontazioneBusinessImpl
							.chiudiValidazioneChecklistHtml(idUtente, idIride, istanza, validazionePiuGreenDTO);
					LOG.info(prf + "esitoPiuGreen=" + esitoPiuGreen);
					esitoRendicontazionePiuGreen
							.setIdDocIndexDichiarazionePiuGreen(esitoPiuGreen.getIdDocIndexDichiarazione());
				}
			} else {
				LOG.error(Constants.ERRORE_SERVIZIO_SANITA + response);
				esito = new EsitoValidazioneRendicontazione();
				esito.setEsito(Boolean.FALSE);
				esito.setMessage(Constants.ERRORE_SERVIZIO_SANITA);
				esitoRendicontazionePiuGreen = beanUtil.transform(esito, EsitoValidazioneRendicontazionePiuGreen.class);
			}

		} catch (Exception e) {
			LOG.error(prf + "ERRORE validazioneRendicontazioneSrv.chiudiValidazioneChecklistHtml " + e.getMessage());
			checkListBusinessImpl.eliminaLockCheckListValidazione(idUtente, idIride, validazioneDTO.getIdProgetto(),
					validazioneDTO.getIdDichiarazioneDiSpesa());
			throw e;
		}

		LOG.info(prf + "esito=" + esitoRendicontazionePiuGreen.getEsito());

		String message = beanUtil.getPropertyValueByName(esitoRendicontazionePiuGreen, "message", String.class);
		LOG.info(prf + "message=" + message);

		if (!beanUtil.getPropertyValueByName(esitoRendicontazionePiuGreen, "esito", Boolean.class)) {

			// MessageManager.setGlobalError(result, message,
			// getBeanUtil().getPropertyValueByName(esito, "params", String[].class));
//			String[] arrayY = beanUtil.getPropertyValueByName(esitoRendicontazionePiuGreen, "params", String[].class);
//			LOG.info(prf + "arrayY="+arrayY);
//			
//			for (String str : arrayY) {
//				message = message + ", " + str;
//			}

			Collection<String> errori = setGlobalError(message,
					beanUtil.getPropertyValueByName(esitoRendicontazionePiuGreen, "params", String[].class));

			for (String str : errori) {
				message = message + ", " + str;
			}
		}

		esitoRendicontazionePiuGreen.setMessage(message);

		LOG.info(prf + " END");
		return esitoRendicontazionePiuGreen;
	}

	private Collection<String> setGlobalError(String codError, Object... msgArgs) {
		Collection<String> errors = new ArrayList<String>();
		LOG.info("codError=" + codError);

		if (ObjectUtil.isEmpty(msgArgs)) {
			errors.add(codError);
		} else {
			String pattern = extractMessage(codError);
			errors.add(MessageFormat.format(pattern, msgArgs));
		}
		return errors;
	}

	private String extractMessage(String key) {
		key = key == null ? it.csi.pbandi.pbservizit.pbandiutil.commonweb.MessageKey.KEY_MSG_ERRORE_GENERICO : key;

		String result;
		try {
			result = rbMessages.getString(key);
		} catch (MissingResourceException e) {
			try {
				result = rbMessages
						.getString(it.csi.pbandi.pbservizit.pbandiutil.commonweb.MessageKey.KEY_MSG_ERRORE_GENERICO);
			} catch (MissingResourceException e1) {
				result = "Generic error.";
			}
		}
		LOG.info("result=" + result);
		return result;
	}

	@Override
	public String getNomeFile(Long idDocumentoIndex) {
		String nomeFile = null;
		if (idDocumentoIndex != null) {
			PbandiTDocumentoIndexVO docInd = documentoManager.trovaTDocumentoIndex(idDocumentoIndex);
			nomeFile = docInd.getNomeFile();
		}
		return nomeFile;
	}

	@Override
	public PbandiCCostantiVO getCostantiAllegati(Long idUtente, String idIride) throws Exception {

		String prf = "[ValidazioneRendicontazioneDAOImpl::getCostanti]";
		LOG.info(prf + " BEGIN");

		String query = "SELECT * 													"
				+ "FROM pbandi_c_costanti pcc 								"
				+ "WHERE pcc.attributo = 'conf.archivioAllowedFileExtensions' ";

		PbandiCCostantiVO estensioniAllegati = this.queryForObject(query, PbandiCCostantiVO.class);
		// this.queryForList

		// List <PbandiCCostantiVO> costntiList = genericDAO.findListWhere();

		LOG.info(prf + " END");

		return estensioniAllegati;

	}

	private List<DocumentoAllegatoDTO> getAllegatiSenzaIntegrazione1(List<DocumentoAllegatoDTO> documentoAllegatoDTOS) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::getAllegatiSenzaIntegrazione1]";
		LOG.info(prf + " BEGIN");

		List<DocumentoAllegatoDTO> output = new ArrayList<>();

		for (DocumentoAllegatoDTO documentoAllegatoDTO : documentoAllegatoDTOS) {
			if (documentoAllegatoDTO.getIdIntegrazioneSpesa() == null) {
				output.add(documentoAllegatoDTO);
			}
		}

		LOG.info(prf + " END");
		return output;
	}

	private List<List<DocumentoAllegatoDTO>> separateByIntegrazione1(List<DocumentoAllegatoDTO> documentoAllegatoDTOS) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::separateByIntegrazione1]";
		LOG.info(prf + " BEGIN");

		List<List<DocumentoAllegatoDTO>> output = new ArrayList<>();

		for (DocumentoAllegatoDTO documentoAllegatoDTO : documentoAllegatoDTOS) {
			if (documentoAllegatoDTO.getIdIntegrazioneSpesa() != null) {
				boolean newList = true;
				for (List<DocumentoAllegatoDTO> list : output) {
					if (Objects.equals(list.get(0).getIdIntegrazioneSpesa(),
							documentoAllegatoDTO.getIdIntegrazioneSpesa())) {
						list.add(documentoAllegatoDTO);
						newList = false;
					}
				}
				if (newList) {
					List<DocumentoAllegatoDTO> list = new ArrayList<>();
					try {
						documentoAllegatoDTO.setDtInvioIntegrazione(getJdbcTemplate().queryForObject(
								"SELECT DATA_INVIO \n" + "FROM PBANDI_T_INTEGRAZIONE_SPESA\n"
										+ "WHERE ID_INTEGRAZIONE_SPESA = ?",
								Date.class, documentoAllegatoDTO.getIdIntegrazioneSpesa()).toString());
					} catch (Exception ignored) {
					}
					if (documentoAllegatoDTO.getDtInvioIntegrazione() != null) {
						list.add(documentoAllegatoDTO);
						output.add(list);
					}
				}
			}
		}

		LOG.info("N. integrazioni:\n" + output.size());
		LOG.info(prf + " END");
		return output;
	}

	private List<DocumentoAllegatoPagamentoDTO> separateByPagamentoIntegrazione2(
			List<DocumentoAllegatoPagamentoDTO> documentoAllegatoPagamentoDTOS) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::separateByPagamentoIntegrazione2]";
		LOG.info(prf + " BEGIN");

		List<DocumentoAllegatoPagamentoDTO> output = new ArrayList<>();

		for (DocumentoAllegatoPagamentoDTO documentoAllegatoPagamentoDTO : documentoAllegatoPagamentoDTOS) {
			boolean newList = true;

			DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
			allegato.setId(documentoAllegatoPagamentoDTO.getId());
			allegato.setNome(documentoAllegatoPagamentoDTO.getNomeFile());
			allegato.setSizeFile(documentoAllegatoPagamentoDTO.getSizeFile());
			allegato.setIdIntegrazioneSpesa(documentoAllegatoPagamentoDTO.getIdIntegrazioneSpesa());

			for (DocumentoAllegatoPagamentoDTO pagamento : output) {
				if (Objects.equals(pagamento.getIdPagamento(), documentoAllegatoPagamentoDTO.getIdPagamento())) {
					newList = false;

					if (documentoAllegatoPagamentoDTO.getIdIntegrazioneSpesa() == null) {
						if (documentoAllegatoPagamentoDTO.getId() != null) {
							pagamento.getDocumentoAllegatoGenerico().add(allegato);
						}
					} else {
						boolean nuovaIntegrazione = true;
						for (List<DocumentoAllegatoDTO> allegatiPagamento : pagamento
								.getDocumentoAllegatoIntegrazioni()) {
							if (Objects.equals(allegatiPagamento.get(0).getIdIntegrazioneSpesa(),
									allegato.getIdIntegrazioneSpesa())) {
								allegatiPagamento.add(allegato);
								nuovaIntegrazione = false;
							}
						}
						if (nuovaIntegrazione) {
							List<DocumentoAllegatoDTO> list = new ArrayList<>();
							try {
								allegato.setDtInvioIntegrazione(getJdbcTemplate().queryForObject(
										"SELECT DATA_INVIO \n" + "FROM PBANDI_T_INTEGRAZIONE_SPESA\n"
												+ "WHERE ID_INTEGRAZIONE_SPESA = ?",
										Date.class, allegato.getIdIntegrazioneSpesa()).toString());
							} catch (Exception ignored) {
							}
							if (allegato.getDtInvioIntegrazione() != null) {
								list.add(allegato);
								pagamento.getDocumentoAllegatoIntegrazioni().add(list);
							}
						}
					}
				}
			}
			if (newList) {
				DocumentoAllegatoPagamentoDTO pagamento = new DocumentoAllegatoPagamentoDTO();
				pagamento.setDocumentoAllegatoGenerico(new ArrayList<>());
				pagamento.setDocumentoAllegatoIntegrazioni(new ArrayList<>());

				pagamento.setIdPagamento(documentoAllegatoPagamentoDTO.getIdPagamento());
				pagamento.setDtPagamento(documentoAllegatoPagamentoDTO.getDtPagamento());
				pagamento.setDescrizioneModalitaPagamento(
						documentoAllegatoPagamentoDTO.getDescrizioneModalitaPagamento());
				pagamento.setImportoPagamento(documentoAllegatoPagamentoDTO.getImportoPagamento());

				if (documentoAllegatoPagamentoDTO.getIdIntegrazioneSpesa() == null) {
					if (documentoAllegatoPagamentoDTO.getId() != null) {
						pagamento.getDocumentoAllegatoGenerico().add(allegato);
					}
				} else {
					try {
						allegato.setDtInvioIntegrazione(getJdbcTemplate().queryForObject(
								"SELECT DATA_INVIO \n" + "FROM PBANDI_T_INTEGRAZIONE_SPESA\n"
										+ "WHERE ID_INTEGRAZIONE_SPESA = ?",
								Date.class, allegato.getIdIntegrazioneSpesa()).toString());
					} catch (Exception ignored) {
					}

					if (allegato.getDtInvioIntegrazione() != null) {
						pagamento.getDocumentoAllegatoIntegrazioni().add(Collections.singletonList(allegato));
					}
				}

				output.add(pagamento);
			}
		}

		LOG.info("N. integrazioni:\n" + output.size());
		LOG.info(prf + " END");
		return output;
	}

	@Override
	public Long getIdChecklist(Long idDichiarazioneSpesa, Long idDocumentoIndex) {
		String prf = "[ValidazioneRendicontazioneDAOImpl::getIdChecklist]";
		LOG.info(prf + " BEGIN");

		Long idChecklist;

		try {
			idChecklist = getJdbcTemplate().queryForObject(
					"SELECT ID_CHECKLIST FROM PBANDI_T_CHECKLIST WHERE ID_DICHIARAZIONE_SPESA = ? AND ID_DOCUMENTO_INDEX = ?",
					Long.class, idDichiarazioneSpesa, idDocumentoIndex);
		} catch (IncorrectResultSizeDataAccessException ignored) {
			idChecklist = null;
			LOG.info("ID checklist non trovato");
		} catch (Exception e) {
			idChecklist = null;
			LOG.info("Error while trying to getIdChecklist");
		}

		LOG.info(prf + " END");
		return idChecklist;
	}

	@Override
	public Double getSumImportoErogProgettiPercettori(Long idUtente, String idIride, Long progrBandoLineaIntervento,
			Long idDichiarazioneDiSpesa) throws Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::getSumImportoErogProgettiPercettori] ";
		LOG.info(prf + " BEGIN");

		if (idUtente == null) {
			LOG.error(prf + "idUtente non valorizzato");
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idIride == null || idIride.isEmpty()) {
			LOG.error(prf + "idIride non valorizzato");
			throw new InvalidParameterException("idIride non valorizzato");
		}
		if (progrBandoLineaIntervento == null) {
			LOG.error(prf + "progrBandoLineaIntervento non valorizzato");
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato");
		}
		if (idDichiarazioneDiSpesa == null) {
			LOG.error(prf + "idDichiarazioneDiSpesa non valorizzato");
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		Double somma = 0D;

		try {
			String query = "WITH SIFFINI (PROGR_BANDO_LINEA_INTERVENTO) AS \r\n"
					+ "	(SELECT PROGR_BANDO_LINEA_INTERVENTO\r\n" + "	FROM PBANDI_R_BANDO_LINEA_INTERVENT \r\n"
					+ "	WHERE PROGR_BANDO_LINEA_INTERV_SIF = ?),\r\n" + "	DICHSPESA (DT_DICHIARAZIONE) AS \r\n"
					+ "	(SELECT DT_DICHIARAZIONE\r\n" + "	FROM PBANDI_T_DICHIARAZIONE_SPESA  \r\n"
					+ "	WHERE ID_DICHIARAZIONE_SPESA = ?)\r\n"
					+ "SELECT SUM(pte.IMPORTO_EROGAZIONE) AS SUM_IMPORTO_EROGAZIONE\r\n"
					+ "FROM PBANDI_T_EROGAZIONE pte,PBANDI_T_PROGETTO ptp, PBANDI_T_DOMANDA ptd, SIFFINI sif, DICHSPESA dich\r\n"
					+ "WHERE ptp.ID_PROGETTO = pte.ID_PROGETTO AND ptd.ID_DOMANDA = ptp.ID_DOMANDA \r\n"
					+ "AND ptd.PROGR_BANDO_LINEA_INTERVENTO IN sif.PROGR_BANDO_LINEA_INTERVENTO\r\n"
					+ "AND pte.DT_CONTABILE <= dich.DT_DICHIARAZIONE";

			LOG.info(query);
			Object[] params = new Object[] { progrBandoLineaIntervento, idDichiarazioneDiSpesa };
			LOG.info(prf + " <progrBandoLineaIntervento>: " + progrBandoLineaIntervento + ", <idDichiarazioneDiSpesa>: "
					+ idDichiarazioneDiSpesa);

			List<Double> result = getJdbcTemplate().queryForList(query, params, Double.class);

			if (result != null && result.size() > 0 && result.get(0) != null) {
				somma = result.get(0);
			}
		} catch (Exception e) {
			LOG.error(prf + "Errore nel calcolo della somma importi erogazione percettori.");
			throw new Exception("Errore nel calcolo della somma importi erogazione percettori: " + e);
		}

		return somma;
	}

	/**
	 * Chiamata al servizio di Domiciliarità/Residenzialità
	 * 
	 * @param note
	 * @param idDichiarazioneDiSpesa
	 * @param idIntegrazioneSpesa
	 * @param metodo
	 * @param regola
	 * @param idDocumentoDiSpesa
	 * @param esitoSrv
	 * @return
	 * @throws Exception
	 */
	private String invioNotificaDomRes(String note, Long idDichiarazioneDiSpesa, Long idIntegrazioneSpesa,
			String metodo, String regola, Long idDocumentoDiSpesa, Boolean esitoSrv) throws Exception {
		LOG.info("[ValidazioneRendicontazioneDAOImpl::invioNotificaDomRes] BEGIN");
		HashMap<String, String> result = getTitoloBandoENumeroDomandaValidazione(idDichiarazioneDiSpesa, regola);
		String response = null;
		if (result != null && !result.isEmpty()) {
			String numeroDomanda = result.get("numeroDomanda");
			String titoloBando = result.get("titoloBando");
			LOG.info("numeroDomanda: " + numeroDomanda + ", titoloBando: " + titoloBando);
			String host = "";
			String user = "";
			String pass = "";
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_REQUEST);
			String dataAzione = sdf.format(new Date());
			if (titoloBando.equals(Constants.BUONO_DOMICILIARITA)) {
				host = restServices.getString(Constants.BUONO_DOMICILIARITA_ENDPOINT);
				user = restServices.getString(Constants.USER_DOMICILIARITA);
				pass = restServices.getString(Constants.PASS_DOMICILIARITA);
			} else if (titoloBando.equals(Constants.BUONO_RESIDENZIALITA)) {
				host = restServices.getString(Constants.BUONO_RESIDENZIALITA_ENDPOINT);
				user = restServices.getString(Constants.USER_RESIDENZIALITA);
				pass = restServices.getString(Constants.PASS_RESIDENZIALITA);
			}
			LOG.info("host: " + host);
			RestTemplate template = new RestTemplate();
			UUID uuid4 = UUID.randomUUID();
			logger.info("X-Request-Id = " + uuid4);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			headers.add("Authorization", getBasicAuthenticationHeader(user, pass));
			headers.add("X-Request-Id", uuid4.toString());
			ObjectMapper requestMapper = new ObjectMapper();
			requestMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			requestMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_FORMAT_REQUEST));
			if ("richiediIntegrazione".equals(metodo)) {
				Integer numGiorni = 5;
				String noteSenzaFormattazione = note.replaceAll("<[^>]*>", "");
				String jsonRequest = "{\"integrazione_spesa\": {" + "\"id_dichiarazione_spesa_bandi\": "
						+ requestMapper.writeValueAsString(idDichiarazioneDiSpesa.toString())
						+ ",\"identificativo_richiesta_integrazione\": "
						+ requestMapper.writeValueAsString(idIntegrazioneSpesa.toString()) + ",\"data_azione\": "
						+ requestMapper.writeValueAsString(dataAzione) + ",\"note\": "
						+ requestMapper.writeValueAsString(noteSenzaFormattazione) + ",\"numero_giorni\": "
						+ requestMapper.writeValueAsString(numGiorni) + "}}";
				LOG.info("Request: " + jsonRequest);
				HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);

				response = template.postForObject(host + numeroDomanda + "/dichiarazione/integrazione", entity,
						String.class);

				if ("OK".equals(response)) {
					inviaMail(numeroDomanda, idIntegrazioneSpesa, titoloBando, idDichiarazioneDiSpesa);
				}

			} else if ("validaDocumento".equals(metodo)) {
				String esito = esitoSrv ? "VALIDATO" : "RESPINTO";
				String jsonRequest = "{\"integrazione_spesa\": {" + "\"id_documento_bandi\": "
						+ requestMapper.writeValueAsString(idDocumentoDiSpesa.toString()) + ",\"esito\": "
						+ requestMapper.writeValueAsString(esito) + ",\"data_azione\": "
						+ requestMapper.writeValueAsString(dataAzione) + ",\"note\": "
						+ requestMapper.writeValueAsString(note != null ? note : "") + "}}";
				LOG.info("Request: " + jsonRequest);
				HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);

				response = template.postForObject(host + numeroDomanda + "/documento-spesa/validazione", entity,
						String.class);
			} else if ("chiudiValidazioneChecklistHtml".equals(metodo)) {
				List<MensilitaDTO> meseAnnoEsitoNote = getMeseAnnoEsitoNote(idDichiarazioneDiSpesa);
				String jsonRequest = "{\"validazione_dichiarazione\": {" + "\"id_dichiarazione_spesa_bandi\": "
						+ requestMapper.writeValueAsString(idDichiarazioneDiSpesa.toString()) + ",\"mensilita\": "
						+ requestMapper.writeValueAsString(meseAnnoEsitoNote) + "}}";
				LOG.info("Request: " + jsonRequest);
				HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);

				response = template.postForObject(host + numeroDomanda + "/dichiarazione/validazione", entity,
						String.class);
			}
		}
		LOG.info("response: " + response);
		return response;
	}

	/**
	 * Recupera titolo bando e numero domanda per la dichiarazione di spesa
	 * 
	 * @param idDichiarazioneSpesa
	 * @return
	 */
	public HashMap<String, String> getTitoloBandoENumeroDomandaValidazione(Long idDichiarazioneSpesa, String regola) {
		LOG.info("[ValidazioneRendicontazioneDAOImpl::getTitoloBandoENumeroDomandaValidazione] BEGIN");
		LOG.info("idDichirazioneSpesa: " + idDichiarazioneSpesa + ", regola = " + regola);
		HashMap<String, String> info = null;
		String query = "SELECT ptb.TITOLO_BANDO, ptd.NUMERO_DOMANDA \r\n"
				+ "FROM PBANDI_T_DICHIARAZIONE_SPESA ptds \r\n"
				+ "LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptds.ID_PROGETTO \r\n"
				+ "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\r\n"
				+ "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "LEFT JOIN PBANDI_T_BANDO ptb ON ptb.ID_BANDO = prbli.ID_BANDO\r\n"
				+ "LEFT JOIN PBANDI_R_REGOLA_BANDO_LINEA prrbl ON prrbl.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "LEFT JOIN PBANDI_C_REGOLA pcr ON pcr.ID_REGOLA = prrbl.ID_REGOLA\r\n"
				+ "WHERE ptds.ID_DICHIARAZIONE_SPESA = :idDichiarazioneSpesa AND pcr.DESC_BREVE_REGOLA = :regola";
		LOG.info("Query: " + query);

		info = getJdbcTemplate().query(query, rs -> {
			HashMap<String, String> map = null;
			while (rs.next()) {
				map = new HashMap<String, String>();
				map.put("titoloBando", rs.getString("TITOLO_BANDO"));
				map.put("numeroDomanda", rs.getString("NUMERO_DOMANDA"));
			}
			return map;
		}, idDichiarazioneSpesa, regola);

		LOG.info("[ValidazioneRendicontazioneDAOImpl::getTitoloBandoENumeroDomandaValidazione] END");

		return info;
	}

	/**
	 * Recupera la lista di mensilità
	 * 
	 * @param idDichiarazioneSpesa
	 * @return
	 */
	public List<MensilitaDTO> getMeseAnnoEsitoNote(Long idDichiarazioneSpesa) {
		LOG.info("[ValidazioneRendicontazioneDAOImpl::getMeseAnnoEsitoNote] BEGIN");
		LOG.info("idDichiarazioneSpesa: " + idDichiarazioneSpesa);

		List<MensilitaDTO> listaMensilita = new ArrayList<MensilitaDTO>();
		String query = "SELECT ptdmw.ANNO, ptdmw.MESE, ptdmw.ESITO, ptdmw.NOTE, ptdmw.FLAG_SABBATICO \r\n"
				+ "FROM PBANDI_T_DICH_MENS_WS ptdmw \r\n"
				+ "WHERE ptdmw.ID_DICHIARAZIONE_SPESA = :idDichiarazioneSpesa";
		LOG.info("Query: " + query);

		listaMensilita = getJdbcTemplate().query(query, rs -> {
			List<MensilitaDTO> lista = new ArrayList<MensilitaDTO>();

			while (rs.next()) {
				String sabbatico = rs.getString("FLAG_SABBATICO");
				if (!"S".equals(sabbatico)) {
					MensilitaDTO mensilita = new MensilitaDTO();
					mensilita.setAnno(rs.getLong("ANNO"));
					mensilita.setMese(rs.getString("MESE"));
					String esito = rs.getString("ESITO");
					if ("OK".equals(esito)) {
						mensilita.setEsito("VALIDATO");
					} else if ("KO".equals(esito)) {
						mensilita.setEsito("RESPINTO");
					} else if ("NV".equals(esito)) {
						mensilita.setEsito("INVALIDATO");
					}
					Clob clob = rs.getClob("NOTE");
					if (clob != null) {
						mensilita.setNote(clob.getSubString(1, (int) clob.length()));
					} else {
						mensilita.setNote("");
					}
					lista.add(mensilita);
				}
			}

			return lista;
		}, idDichiarazioneSpesa);
		LOG.info("[ValidazioneRendicontazioneDAOImpl::getMeseAnnoEsitoNote] END");

		return listaMensilita;
	}

	/**
	 * Recupera i numero giorni scadenza per la richiesta di integrazione
	 * 
	 * @param idIntegrazioneSpesa
	 * @return
	 */
	public Integer getNumGiorniScadenza(Long idIntegrazioneSpesa) {
		LOG.info("[ValidazioneRendicontazioneDAOImpl::getNumGiorniScadenza] BEGIN");
		LOG.info("idIntegrazioneSpesa: " + idIntegrazioneSpesa);
		String query = "SELECT PTIS.NUM_GIORNI_SCADENZA  \n" + "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis \n"
				+ "WHERE PTIS.ID_INTEGRAZIONE_SPESA = ?";
		LOG.info("Query: " + query);
		Integer numGiorni = getJdbcTemplate().queryForObject(query, new Object[] { idIntegrazioneSpesa },
				Integer.class);

		LOG.info("[ValidazioneRendicontazioneDAOImpl::getNumGiorniScadenza] END");

		return numGiorni;
	}

	/**
	 * Genera la stringa per la basic auth
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public String getBasicAuthenticationHeader(String username, String password) {
		String valueToEncode = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
	}

	@Transactional
	@Override
	public EsitoOperazioni chiudiValidazioneUfficio(Long idDichiarazioneSpesa, HttpServletRequest req)
			throws Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::chiudiValidazioneUfficio]";
		LOG.info(prf + " BEGIN");
		UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		try {
			// Aggiorno la dichiarazione di spesa
			String sql = "UPDATE PBANDI_T_DICHIARAZIONE_SPESA SET \n" + "ID_ATTRIB_VALID_SPESA = 7,\n"
					+ "ID_ESITO_VALID_SPESA = 3,\n" + "DT_CHIUSURA_VALIDAZIONE = CURRENT_DATE,\n"
					+ "ID_UTENTE_AGG = ?\n" + "WHERE ID_DICHIARAZIONE_SPESA = ?";

			if (getJdbcTemplate().update(sql, userInfoSec.getIdUtente(), idDichiarazioneSpesa) < 1) {
				throw new Exception("Nessuna dichiarazione di spesa aggiornata!");
			}

			// Aggiorno i documenti di spesa della dichiarazione di spesa
			sql = "UPDATE PBANDI_R_DOC_SPESA_PROGETTO SET \n" + "ID_STATO_DOCUMENTO_SPESA = 7,\n"
					+ "ID_STATO_DOCUMENTO_SPESA_VALID = 7,\n" + "ID_UTENTE_AGG = ?\n"
					+ "WHERE ID_DOCUMENTO_DI_SPESA IN (\n" + "\tSELECT ID_DOCUMENTO_DI_SPESA \n"
					+ "\tFROM PBANDI_S_DICH_DOC_SPESA\n" + "\tWHERE ID_DICHIARAZIONE_SPESA = ?\n" + ")";

			getJdbcTemplate().update(sql, userInfoSec.getIdUtente(), idDichiarazioneSpesa);
		} finally {
			LOG.info(prf + " END");
		}

		EsitoOperazioni esito = new EsitoOperazioni();
		esito.setEsito(true);
		esito.setMsg("Validazione chiusa d'ufficio");
		return esito;
	}

	/**
	 * Invio mail al beneficiario
	 * 
	 * @param numeroDomanda
	 * @param idIntegrazioneSpesa
	 * @param titoloBando
	 * @param idDichiarazioneDiSpesa
	 * @throws Exception
	 */
	private void inviaMail(String numeroDomanda, Long idIntegrazioneSpesa, String titoloBando,
			Long idDichiarazioneDiSpesa) throws Exception {
		LOG.info("[ValidazioneRendicontazioneDAOImpl::inviaMail] BEGIN");
		LOG.info("numeroDomanda: " + numeroDomanda + ", idIntegrazioneSpesa: " + idIntegrazioneSpesa + ", titoloBando: "
				+ titoloBando + ", idDichiarazioneDiSpesa: " + idDichiarazioneDiSpesa);

		/**
		 * 1. Recupero destinatari
		 */
		String queryDestinatari = "";
		String destinatari = "";
		String attributo = "";
		if (Constants.BUONO_DOMICILIARITA.equals(titoloBando)) {
			attributo = "ws_email_testo_b.dom_richieste_integrazioni";
			queryDestinatari = "SELECT ptr.EMAIL \n" + "FROM PBANDI_T_DOMANDA ptd \n"
					+ "JOIN PBANDI_T_PROGETTO ptp ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n"
					+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO \n"
					+ "AND prsp.ID_TIPO_ANAGRAFICA = 1 \n"
					+ "JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsp.ID_RECAPITI_PERSONA_FISICA \n"
					+ "WHERE NUMERO_DOMANDA = '" + numeroDomanda + "'";
		} else if (Constants.BUONO_RESIDENZIALITA.equals(titoloBando)) {
			attributo = "ws_email_testo_b.res_richieste_integrazioni";
			queryDestinatari = "SELECT ptfs.EMAIL	\n" + 
					"FROM PBANDI_T_FORNITORE_STRUTTURA ptfs	\n" + 
					"JOIN PBANDI_R_DICH_SP_FORN_STRUT prdsfs ON PTFS.COD_STRUTTURA = prdsfs.COD_STRUTTURA	\n" + 
					"WHERE prdsfs.ID_DICHIARAZIONE_SPESA = " + idDichiarazioneDiSpesa +
					" AND ptfs.NUMERO_DOMANDA = '" + numeroDomanda + "'";
		}
		LOG.info("Query destinatari: " + queryDestinatari);
		try {
			destinatari = queryForObject(queryDestinatari, String.class);
		} catch (Exception e) {
			LOG.info("Non è stato trovato nessun destinatario");
		}
		LOG.info("Destinatari: " + destinatari);

		if (!StringUtil.isEmpty(destinatari)) {
			/**
			 * 2. Recupero destinatari per CCN
			 */
			String queryDestCCN = "SELECT VALORE \n" + "FROM PBANDI_C_COSTANTI \n"
					+ "WHERE ATTRIBUTO = 'ws_email_dest_ccn_richieste_integrazioni'";
			LOG.info("Query destCCN: " + queryDestCCN);
			String destCCN = getJdbcTemplate().queryForObject(queryDestCCN, String.class);
			LOG.info("Destinatari CCN: " + destCCN);

			/**
			 * 3. Recupero info da mandare via mail
			 */
			HashMap<String, String> info = null;
			String queryInfo = "SELECT ptis.ID_DICHIARAZIONE_SPESA, \n" + "		ptis.DESCRIZIONE, \n"
					+ "		ptis.DATA_RICHIESTA, \n" + "		ptb.TITOLO_BANDO, \n" + "		ptd.NUMERO_DOMANDA, \n"
					+ "		ptpf.NOME, \n" + "		ptpf.COGNOME \n" + "FROM PBANDI_T_INTEGRAZIONE_SPESA ptis \n"
					+ "JOIN PBANDI_T_DICHIARAZIONE_SPESA ptds ON ptds.ID_DICHIARAZIONE_SPESA = ptis.ID_DICHIARAZIONE_SPESA \n"
					+ "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptds.ID_PROGETTO \n"
					+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO \n"
					+ "JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA \n"
					+ "JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n"
					+ "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO \n"
					+ "JOIN PBANDI_T_BANDO ptb ON ptb.ID_BANDO = prbli.ID_BANDO \n"
					+ "WHERE prsp.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_BENEFICIARIO <> 4 \n"
					+ "AND ptis.ID_INTEGRAZIONE_SPESA = :idIntegrazioneSpesa";
			LOG.info("Query info: " + queryInfo);

			info = getJdbcTemplate().query(queryInfo, rs -> {
				HashMap<String, String> map = null;
				while (rs.next()) {
					SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
					map = new HashMap<String, String>();
					map.put("idDichiarazioneSpesa", String.valueOf(rs.getInt("ID_DICHIARAZIONE_SPESA")));
					map.put("descrizione", rs.getString("DESCRIZIONE"));
					if (rs.getDate("DATA_RICHIESTA") != null) {
						map.put("dataInvio", sdf.format(rs.getDate("DATA_RICHIESTA")));
					} else {
						map.put("dataInvio", "");
					}
					map.put("titoloBando", rs.getString("TITOLO_BANDO"));
					map.put("numeroDomanda", rs.getString("NUMERO_DOMANDA"));
					map.put("nome", rs.getString("NOME"));
					map.put("cognome", rs.getString("COGNOME"));
				}
				return map;
			}, idIntegrazioneSpesa);

			/**
			 * 4. Recupero template mail
			 */
			String queryTemplate = "SELECT VALORE \n" + "FROM PBANDI_C_COSTANTI \n" + "WHERE ATTRIBUTO = '" + attributo
					+ "'";
			LOG.info("Query template: " + queryTemplate);
			String template = getJdbcTemplate().queryForObject(queryTemplate, String.class);
			String descSenzaFormattazione = StringUtil.isEmpty(info.get("descrizione")) ? ""
					: info.get("descrizione").replaceAll("<[^>]*>", "");
			String testo = template.replace("${numeroDomanda}", info.get("numeroDomanda"))
					.replace("${titoloBando}", info.get("titoloBando"))
					.replace("${testoRichiesta}", descSenzaFormattazione).replace("${dataInvio}", info.get("dataInvio"))
					.replace("${nome}", info.get("nome")).replace("${cognome}", info.get("cognome"));
			LOG.info("Testo: " + testo);

			String oggetto = "Richiesta di integrazione - " + info.get("titoloBando");

			mailDAO.sendEmail("no-reply@csi.it", "", destinatari, oggetto, testo, null, destCCN);
		}

		LOG.info("[ValidazioneRendicontazioneDAOImpl::inviaMail] END");
	}

	@Override
	public String inviaNotificaChiudiValidRagioneriaDelegata(Long idDichiarazioneDiSpesa, Long idProgetto,
			Long idUtente, String identitaDigitale) throws Exception {

		LOG.info("\n\n\n INVIO NOTIFICA CHIUDI VALIDAZIONE A RAGIONERIA DELEGATA \n\n\n");

		String[] valueToCheck = { "idUtente", "identitaDigitale", "idProgetto", "idDichiarazioneDiSpesa" };
		ValidatorInput.verifyNullValue(valueToCheck, idUtente, identitaDigitale, idProgetto, idDichiarazioneDiSpesa);

		try {
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			MailVO mailVO = new MailVO();

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dtDichiarazione = dateFormat.format(pbandiTDichiarazioneSpesaVO.getDtDichiarazione());

			mailVO.setSubject("Chiusura della Validazione della Dichiarazione di Spesa " + idDichiarazioneDiSpesa
					+ " del " + dtDichiarazione);
			mailVO.setFromAddress(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);
			List<String> mailRagioneriaDelegata = leggiIndirizzoMailRuoloEnteProgetto(idProgetto,
					Constants.DESC_BREVE_RUOLO_ENTE_COMP_RAGIONERIA_DELEGATA);
			mailVO.setToAddresses(mailRagioneriaDelegata);

			mailVO.setContent("Si avvisa che in data " + dateFormat.format(new Date())
					+ " l'istruttore competente ha chiuso la validazione della dichiarazione di spesa numero "
					+ idDichiarazioneDiSpesa + " del " + dtDichiarazione + ".");

			LOG.info("\n\n\nbefore mailDAO.send\n\n");
			LOG.info("----- MAIL CONTENT ------ \n " + mailVO.getContent());

			mailUtil.send(mailVO);
			LOG.info("\n\n\nafter mailDAO.send\n\n");
		} catch (RecordNotFoundException e) {
			LOG.error("RecordNotFoundException ", e);
			throw e;
		} catch (Exception e) {
			LOG.error(e);
			throw e;
		} catch (Throwable t) {
			LOG.error("catching Throwable");
			LOG.error("throwable error message: " + t.getMessage(), t);
		}

		return null;
	}

	@Override
	public List<String> leggiIndirizzoMailRuoloEnteProgetto(Long idProgetto, String descrBreveRuoloEnte)
			throws Exception {
		String prf = "[VaziodazioneRendicontazioneDAOImpl::leggiIndirizzoMailRuoloEnteProgetto]";
		LOG.info(prf + " BEGIN");
		if (idProgetto == null)
			throw new Exception("IdProgetto non valorizzato");
		if (descrBreveRuoloEnte == null)
			throw new Exception("DescBreveRuoloEnte non valorizzata");

		List<String> mails = new ArrayList<String>();

		StringBuilder query = new StringBuilder();
		query.append("SELECT prblec.indirizzo_mail \r\n" + "FROM PBANDI_V_BANDO_LINEA_ENTE_COMP pvblec \r\n"
				+ "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec \r\n"
				+ "	ON  pvblec.PROGR_BANDO_LINEA_ENTE_COMP = prblec.PROGR_BANDO_LINEA_ENTE_COMP \r\n"
				+ "JOIN PBANDI_D_RUOLO_ENTE_COMPETENZA pdrec \r\n"
				+ "	ON pvblec.ID_RUOLO_ENTE_COMPETENZA = pdrec.ID_RUOLO_ENTE_COMPETENZA \r\n"
				+ "WHERE pvblec.DESC_BREVE_RUOLO_ENTE = ? \r\n" + "AND prblec.PROGR_BANDO_LINEA_INTERVENTO = (\r\n"
				+ "											SELECT progr_bando_linea_intervento \r\n"
				+ "											FROM PBANDI_V_PROGETTI_BANDO pvpb \r\n"
				+ "											WHERE id_progetto= ? \r\n"
				+ "										  ) ");

		Object[] param = new Object[] { descrBreveRuoloEnte, idProgetto };
		LOG.info(prf + " <descrBreveRuoloEnte>: " + descrBreveRuoloEnte + ", <idProgetto>: " + idProgetto);
		LOG.info(query.toString());
		List<Email> tipoDocumentoVOs = getJdbcTemplate().query(query.toString(), param, new EmailRowMapper());

		if (tipoDocumentoVOs != null && tipoDocumentoVOs.size() > 0 && tipoDocumentoVOs.get(0) != null
				&& tipoDocumentoVOs.get(0).getMail() != null) {
			mails.add(tipoDocumentoVOs.get(0).getMail());
			LOG.info(prf + "mail trovata: " + tipoDocumentoVOs.get(0).getMail());
		} else {
			LOG.info(prf + "Nessuna mail trovata.");
		}

		LOG.info(prf + " END");
		return mails;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void annullaSospendiDocumentoSpesaValid(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa,
			Long idProgetto, String noteValidazione, Long idUtente, String idIride, Boolean fromAttivitaValidazione)
			throws InvalidParameterException, Exception {
		String prf = "[ValidazioneRendicontazioneDAOImpl::annullaSospendiDocumentoSpesaValid] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);
		LOG.info(prf + "noteValidazione = " + noteValidazione);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		try {

			validazioneRendicontazioneBusinessImpl.saveNoteValidazioneDoc(idUtente, idIride, idDocumentoDiSpesa,
					idProgetto, noteValidazione);

			PbandiRDocSpesaProgettoVO pbandiRDocSpesaProgettoVO = new PbandiRDocSpesaProgettoVO();
			pbandiRDocSpesaProgettoVO.setIdProgetto(new BigDecimal(idProgetto.longValue()));
			pbandiRDocSpesaProgettoVO.setIdDocumentoDiSpesa(new BigDecimal(idDocumentoDiSpesa.longValue()));
			pbandiRDocSpesaProgettoVO = genericDAO.findSingleWhere(pbandiRDocSpesaProgettoVO);

			// ripristino stato precedente alla sospensione
			pbandiRDocSpesaProgettoVO
					.setIdStatoDocumentoSpesaValid(pbandiRDocSpesaProgettoVO.getIdStatoDocumentoSpesa());

			genericDAO.update(pbandiRDocSpesaProgettoVO);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante l'annullamento della sospensione: ", e);
			throw new DaoException("Errore durante l'annullamento della sospensione.", e);
		} finally {
			LOG.info(prf + " END");
		}

	}

}
