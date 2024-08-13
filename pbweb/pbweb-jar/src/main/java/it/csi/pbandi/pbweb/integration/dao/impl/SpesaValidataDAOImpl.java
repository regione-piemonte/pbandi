/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweb.business.MailUtil;
import it.csi.pbandi.pbweb.business.manager.InizializzazioneManager;
import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoPagamentoDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.RettificaVoceItem;
import it.csi.pbandi.pbweb.dto.RigaNotaDiCreditoItemDTO;
import it.csi.pbandi.pbweb.dto.RigaValidazioneItemDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.DocumentoDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.DichiarazioneSpesaValidataComboDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.DichiarazioneSpesaValidataDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.EsitoSalvaSpesaValidataDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.InizializzaModificaSpesaValidataDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.InizializzaSpesaValidataDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.MensilitaProgettoDTO;
import it.csi.pbandi.pbweb.dto.spesaValidata.RilievoDocSpesaDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.MensilitaDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.SpesaValidataDAO;
import it.csi.pbandi.pbweb.integration.dao.ValidazioneRendicontazioneDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiSpesaValidataRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ConfermaSalvaSpesaValidataRequest;
import it.csi.pbandi.pbweb.integration.dao.request.SalvaSpesaValidataRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DichiarazioneDiSpesaRettificaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.MessaggioDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.rettifica.RettificaException;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRDocSpesaProgettoVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.services.mail.vo.MailVO;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.ErrorMessages;
import it.csi.pbandi.pbweb.util.NumberUtil;
import it.csi.pbandi.pbweb.util.TraduttoreMessaggiPbandisrv;

@Service
public class SpesaValidataDAOImpl extends JdbcDaoSupport implements SpesaValidataDAO {

	// NOTA : la "Spesa Validata" nella vecchia versione corrisponde alla "Rettifica
	// Documenti Di Spesa" nei nomi di finestre e servizi.

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	private static ResourceBundle restServices = ResourceBundle.getBundle("conf.restServices");

	@Autowired
	public SpesaValidataDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Autowired
	InizializzazioneManager inizializzazioneManager;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl profilazioneBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.contoeconomico.ContoeconomicoBusinessImpl contoeconomicoBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.rettifica.RettificaBusinessImpl rettificaBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione.ValidazioneRendicontazioneBusinessImpl validazioneRendicontazioneBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.gestionevocidispesa.GestioneVociDiSpesaBusinessImpl gestioneVociDiSpesaBusinessImpl;

	@Autowired
	DecodificheDAOImpl decodificheDAOImpl;

	@Autowired
	ProfilazioneDAO profilazioneDao;

	@Autowired
	ValidazioneRendicontazioneDAOImpl validazioneRendicontazioneDAOImpl;

	@Autowired
	BeanUtil beanUtil;

	@Autowired
	GenericDAO genericDAO;

	@Autowired
	MailUtil mailUtil;

	@Autowired
	ValidazioneRendicontazioneDAO validazioneRendicontazioneDAO;

	@Override
	public InizializzaSpesaValidataDTO inizializzaSpesaValidata(Long idProgetto, Boolean isFP, Long idUtente,
			String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::inizializzaSpesaValidata] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		InizializzaSpesaValidataDTO result = new InizializzaSpesaValidataDTO();
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

			List<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DichiarazioneDiSpesaRettificaDTO> dichiarazioni;
			if (isFP) {
				dichiarazioni = getJdbcTemplate().query("SELECT ptds.ID_DICHIARAZIONE_SPESA,\n" + "ptds.ID_PROGETTO,\n"
						+ "ptds.ID_TIPO_DICHIARAZ_SPESA,\n" + "ptds.DT_DICHIARAZIONE,\n" + "ptds.PERIODO_DAL,\n"
						+ "ptds.PERIODO_AL,\n" + "ptds.DT_CHIUSURA_VALIDAZIONE,\n"
						+ "ptds.NOTE_CHIUSURA_VALIDAZIONE, \n" + "ptds.RILIEVO_CONTABILE, \n"
						+ "ptds.DT_RILIEVO_CONTABILE, \n" + "ptds.DT_CHIUSURA_RILIEVO, \n"
						+ "ptds.DT_CONFERMA_VALIDITA \n" + "FROM PBANDI_T_DICHIARAZIONE_SPESA ptds \n"
						+ "WHERE ptds.ID_PROGETTO = ?\n" +
						// "AND ptds.ID_ATTRIB_VALID_SPESA <> 7\n" + non esiste id_attrib_valid_spesa =
						// 7
						"ORDER BY DT_CHIUSURA_VALIDAZIONE DESC ", ps -> ps.setLong(1, idProgetto), (rs, rownum) -> {
							DichiarazioneDiSpesaRettificaDTO dto = new DichiarazioneDiSpesaRettificaDTO();

							dto.setIdDichiarazioneSpesa(rs.getLong("ID_DICHIARAZIONE_SPESA"));
							dto.setIdProgetto(rs.getLong("ID_PROGETTO"));
							dto.setIdTipoDichiarazSpesa(rs.getLong("ID_TIPO_DICHIARAZ_SPESA"));
							dto.setDtDichiarazione(rs.getDate("DT_DICHIARAZIONE"));
							dto.setPeriodoDal(rs.getDate("PERIODO_DAL"));
							dto.setPeriodoAl(rs.getDate("PERIODO_AL"));
							dto.setDtChiusuraValidazione(rs.getDate("DT_CHIUSURA_VALIDAZIONE"));
							dto.setNoteChiusuraValidazione(rs.getString("NOTE_CHIUSURA_VALIDAZIONE"));
							if (rs.getString("RILIEVO_CONTABILE") != null) {
								dto.setRilievoContabile(rs.getString("RILIEVO_CONTABILE"));
							}
							if (rs.getString("DT_RILIEVO_CONTABILE") != null) {
								dto.setDtRilievoContabile(rs.getDate("DT_RILIEVO_CONTABILE"));
							}
							if (rs.getString("DT_CHIUSURA_RILIEVO") != null) {
								dto.setDtChiusuraRilievo(rs.getDate("DT_CHIUSURA_RILIEVO"));
							}
							if (rs.getString("DT_CONFERMA_VALIDITA") != null) {
								dto.setDtConfermaValidita(rs.getDate("DT_CONFERMA_VALIDITA"));
							}
							return dto;
						});
			} else {
				dichiarazioni = Arrays
						.asList(rettificaBusinessImpl.findDichiarazioniPerRettifica(idUtente, idIride, idProgetto));
			}
			List<CodiceDescrizioneDTO> tipiDS = getJdbcTemplate()
					.query("SELECT ID_TIPO_DICHIARAZ_SPESA, DESC_BREVE_TIPO_DICHIARA_SPESA \r\n"
							+ "FROM PBANDI_D_TIPO_DICHIARAZ_SPESA ", (rs, rownum) -> {
								CodiceDescrizioneDTO dto = new CodiceDescrizioneDTO();

								dto.setCodice(rs.getString("ID_TIPO_DICHIARAZ_SPESA"));
								dto.setDescrizione(rs.getString("DESC_BREVE_TIPO_DICHIARA_SPESA"));

								return dto;
							});
			ArrayList<DichiarazioneSpesaValidataComboDTO> ds = new ArrayList<DichiarazioneSpesaValidataComboDTO>();
			if (dichiarazioni != null) {
				for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DichiarazioneDiSpesaRettificaDTO dto : dichiarazioni) {
					DichiarazioneSpesaValidataComboDTO item = new DichiarazioneSpesaValidataComboDTO();
					item.setCodice(NumberUtil.getStringValue(dto.getIdDichiarazioneSpesa()));	
					for (CodiceDescrizioneDTO tipo : tipiDS) {
						if (tipo.getCodice().equals(dto.getIdTipoDichiarazSpesa().toString())) {
							item.setDescBreveTipoDichiarazioneSpesa(tipo.getDescrizione());
							if(item.getDescBreveTipoDichiarazioneSpesa().equals(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE_CON_COMUNICAZIONE)) {
								item.setDescBreveTipoDichiarazioneSpesa(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE);
							}
						}
					}
					String descrizione = "Dichiarazione di spesa"
					+(item.getDescBreveTipoDichiarazioneSpesa().equals(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE) ? " finale con comunicazione di fine progetto" : "")
					+" nr." + dto.getIdDichiarazioneSpesa() + " inviata il "
							+ DateUtil.formatDate(dto.getDtDichiarazione()) + " chiusa in data "
							+ DateUtil.formatDate(dto.getDtChiusuraValidazione());
					item.setDescrizione(descrizione);
					item.setNoteRilievoContabile(dto.getRilievoContabile());
					item.setDtRilievoContabile(dto.getDtRilievoContabile());
					item.setDtChiusuraRilievi(dto.getDtChiusuraRilievo());
					item.setDtConfermaValiditaRilievi(dto.getDtConfermaValidita());
					ds.add(item);
				}
			}
			result.setComboDichiarazioniDiSpesa(ds);

			// Combo voci di spesa.
			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoDTO c;
			c = contoeconomicoBusinessImpl.findDatiContoEconomico(idUtente, idIride, idProgetto);
			ArrayList<CodiceDescrizioneDTO> vds = new ArrayList<CodiceDescrizioneDTO>();
			estraiVociDiSpesa(c, vds, null);
			result.setComboVociDiSpesa(vds);

			// Combo tipi documenti di spesa.
			result.setComboTipiDocumentoDiSpesa(new ArrayList(this.comboTipiDocumentoSpesa()));

			// Combo tipi fornitore (PF \ PG).
			List<DecodificaDTO> lista = decodificheDAOImpl.ottieniTipologieFornitore();
			ArrayList<CodiceDescrizioneDTO> tipiFornitori = new ArrayList<CodiceDescrizioneDTO>();
			for (DecodificaDTO dto : lista) {
				CodiceDescrizioneDTO cd = new CodiceDescrizioneDTO();
				cd.setCodice(dto.getId().toString());
				cd.setDescrizione(dto.getDescrizione());
				tipiFornitori.add(cd);
			}
			result.setComboTipiFornitore(tipiFornitori);

			// Esistenza proposta certificazione.
			Boolean esistePropCert = rettificaBusinessImpl.checkPropostaCertificazione(idUtente, idIride, idProgetto);
			if (esistePropCert != null && esistePropCert)
				result.setEsistePropostaCertificazione(true);
			else
				result.setEsistePropostaCertificazione(false);

			// Indice se mostrare o no il bottone "Richiedi Integrazione".
			boolean b = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, "OPEREN039");
			result.setRichiestaIntegrazioneAbilitata(b);

			LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la inizializzazione della spesa validata: ", e);
			throw new DaoException("Errore durante la inizializzazione della spesa validata.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public InizializzaModificaSpesaValidataDTO inizializzaModificaSpesaValidata(Long idDocumentoDiSpesa,
			String numDichiarazione, Long idProgetto, String codiceRuolo, Long idBandoLinea, Long idSoggetto,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::inizializzaModificaSpesaValidata] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idDocumentoDiSpesa = " + idDocumentoDiSpesa
				+ "; numDichiarazione = " + numDichiarazione + "; codiceRuolo = " + codiceRuolo + "; idBandoLinea = "
				+ idBandoLinea + "; idUtente = " + idSoggetto + "; idSoggetto = " + idUtente);

		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (StringUtils.isBlank(numDichiarazione)) {
			throw new InvalidParameterException("numDichiarazione non valorizzato.");
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

		InizializzaModificaSpesaValidataDTO result = new InizializzaModificaSpesaValidataDTO();
		try {

			DatiProgettoInizializzazioneDTO datiProgetto = inizializzazioneManager.completaDatiProgetto(idProgetto);
			if (datiProgetto != null) {
				result.setCodiceVisualizzatoProgetto(datiProgetto.getCodiceVisualizzato());
			}

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.EsitoDettaglioDocumento esito;
			esito = validazioneRendicontazioneBusinessImpl.findDettaglioDocumentoDiSpesaRettifica(idUtente, idIride,
					idDocumentoDiSpesa, idProgetto);
			result.setDocumentoDiSpesa(validazioneRendicontazioneDAOImpl
					.rimappaDettaglioDocumentoDiSpesaPerValidazione(esito.getDocumentoDiSpesa()));

			Long idVoceDiSpesa = null;
			result.setPagamentiAssociati(this.findPagamentiAssociati(idUtente, idIride, idProgetto, idVoceDiSpesa,
					idDocumentoDiSpesa, numDichiarazione));

			result.setNoteDiCredito(this.getNoteDiCredito(idUtente, idIride, idDocumentoDiSpesa, idProgetto));

			// Esistenza proposta certificazione.
			Boolean esistePropCert = rettificaBusinessImpl.checkPropostaCertificazione(idUtente, idIride, idProgetto);
			if (esistePropCert != null && esistePropCert)
				result.setEsistePropostaCertificazione(true);
			else
				result.setEsistePropostaCertificazione(false);

			result.setBottoneSalvaVisibile(profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo, "OPEREN022"));

			// Se l'invio non è cartaceo, cerco gli allegati.
			if (!"C".equalsIgnoreCase(result.getDocumentoDiSpesa().getTipoInvio())) {

				ArrayList<DocumentoAllegatoDTO> allegatiDoc = validazioneRendicontazioneDAOImpl
						.getAllegatiDocumento(idDocumentoDiSpesa, idProgetto, idUtente, idIride);
				result.setAllegatiDocumento(allegatiDoc);

				ArrayList<DocumentoAllegatoPagamentoDTO> allegatiPag = validazioneRendicontazioneDAOImpl
						.getAllegatiPagamento(idDocumentoDiSpesa, idProgetto, idBandoLinea, idUtente, idIride);
				result.setAllegatiPagamento(allegatiPag);

				DocumentoAllegatoDTO[] listaAllForn = validazioneRendicontazioneDAOImpl
						.getAllegatiFornitore(result.getDocumentoDiSpesa(), idUtente, idIride);
				if (listaAllForn != null)
					result.setAllegatiFornitore(new ArrayList<DocumentoAllegatoDTO>(Arrays.asList(listaAllForn)));
				else
					result.setAllegatiFornitore(new ArrayList<DocumentoAllegatoDTO>());

				ArrayList<DocumentoAllegatoDTO> allegatiNoteCredito = new ArrayList<DocumentoAllegatoDTO>();
				for (RigaNotaDiCreditoItemDTO rigaNotaDiCreditoItem : result.getNoteDiCredito()) {
					if (rigaNotaDiCreditoItem.getRigaNotaDiCredito()) {
						ArrayList<DocumentoAllegatoDTO> documentiAllegatiNota = validazioneRendicontazioneDAOImpl
								.getAllegatiDocumento(rigaNotaDiCreditoItem.getId(), idProgetto, idUtente, idIride);
						if (documentiAllegatiNota != null)
							allegatiNoteCredito.addAll(documentiAllegatiNota);
					}
				}
				result.setAllegatiNoteDiCredito(allegatiNoteCredito);

			}

			LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la inizializzazione della modifica della spesa validata: ", e);
			throw new DaoException("Errore durante la inizializzazione della modifica della spesa validata.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	// Ex RettificaBusinessImpl.estraiVociDiSpesa()
	private void estraiVociDiSpesa(it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoDTO c,
			ArrayList<CodiceDescrizioneDTO> result, String descVocePadre) {

		if (c.getFigli() != null && c.getFigli().length > 0) {
			for (it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoDTO dto : c.getFigli()) {
				estraiVociDiSpesa(dto, result, c.getLabel());
			}
		} else {
			if (c.getLabel() != null && NumberUtil.compare(c.getImportoSpesaValidataTotale(), 0D) > 0) {
				CodiceDescrizioneDTO voce = new CodiceDescrizioneDTO();
				voce.setCodice(NumberUtil.getStringValue(c.getIdVoce()));
				if (descVocePadre == null || descVocePadre.trim().length() == 0)
					descVocePadre = "";
				else
					descVocePadre = descVocePadre + " : : ";
				voce.setDescrizione(descVocePadre + c.getLabel());
				result.add(voce);
			}
		}

	}

	private List<CodiceDescrizioneDTO> comboTipiDocumentoSpesa() throws Exception {
		String prf = "[SpesaValidataDAOImpl::comboTipiDocumentoSpesa] ";
		List<CodiceDescrizioneDTO> result = new ArrayList<CodiceDescrizioneDTO>();
		try {

			String sql = "select ID_TIPO_DOCUMENTO_SPESA as CODICE, DESC_TIPO_DOCUMENTO_SPESA as DESCRIZIONE from PBANDI_D_TIPO_DOCUMENTO_SPESA ";
			sql += "where (trunc(sysdate) >= nvl(trunc(DT_INIZIO_VALIDITA), trunc(sysdate) -1) ";
			sql += " and trunc(sysdate) < nvl(trunc(DT_FINE_VALIDITA), trunc(sysdate) +1)) ORDER BY DESC_BREVE_TIPO_DOC_SPESA, DESC_TIPO_DOCUMENTO_SPESA ASC";
			LOG.info("\n" + sql);

			result = getJdbcTemplate().query(sql, new BeanRowMapper(CodiceDescrizioneDTO.class));
			LOG.info(prf + "Record trovati = " + result.size());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca in PBANDI_D_TIPO_DOCUMENTO_SPESA: ", e);
			throw new Exception("Errore durante la ricerca dei tipi documento spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex pbandiweb RettificaBusinessImpl.findDocumentiDiSpesa()
	public ArrayList<DocumentoDiSpesa> cercaDocumentiSpesaValidata(
			CercaDocumentiSpesaValidataRequest cercaDocumentiSpesaValidataRequest, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::cercaDocumentiSpesaValidata] ";
		LOG.info(prf + " BEGIN");

		if (cercaDocumentiSpesaValidataRequest == null) {
			throw new InvalidParameterException("cercaDocumentiSpesaValidataRequest non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);
		LOG.info(prf + "CercaDocumentiSpesaValidataRequest = " + cercaDocumentiSpesaValidataRequest.toString());

		ArrayList<DocumentoDiSpesa> result = new ArrayList<DocumentoDiSpesa>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DocumentoDiSpesaDTO filtroDTO = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DocumentoDiSpesaDTO();
			filtroDTO.setIdProgetto(cercaDocumentiSpesaValidataRequest.getIdProgetto());
			filtroDTO.setIdDichiarazioneSpesa(cercaDocumentiSpesaValidataRequest.getIdDichiarazioneDiSpesa());
			filtroDTO.setIdVoceSpesa(cercaDocumentiSpesaValidataRequest.getIdVoceDiSpesa());
			filtroDTO.setIdTipoDocumentoSpesa(cercaDocumentiSpesaValidataRequest.getIdTipoDocumentoDiSpesa());
			filtroDTO.setIdTipoFornitore(cercaDocumentiSpesaValidataRequest.getIdTipoFornitore());
			filtroDTO.setCodiceFiscaleFornitore(cercaDocumentiSpesaValidataRequest.getCodiceFiscaleFornitore());
			filtroDTO.setPartitaIvaFornitore(cercaDocumentiSpesaValidataRequest.getPartitaIvaFornitore());
			filtroDTO.setDenominazioneFornitore(cercaDocumentiSpesaValidataRequest.getDenominazioneFornitore());
			filtroDTO.setCognomeFornitore(cercaDocumentiSpesaValidataRequest.getCognomeFornitore());
			filtroDTO.setNomeFornitore(cercaDocumentiSpesaValidataRequest.getNomeFornitore());
			filtroDTO.setDtEmissioneDocumento(cercaDocumentiSpesaValidataRequest.getDataDocumentoDiSpesa());
			filtroDTO.setNumeroDocumento(cercaDocumentiSpesaValidataRequest.getNumeroDocumentoDiSpesa());
			filtroDTO.setIsRettificato(cercaDocumentiSpesaValidataRequest.getRettificato());
			if (cercaDocumentiSpesaValidataRequest.getTask() != null)
				filtroDTO.setTask(cercaDocumentiSpesaValidataRequest.getTask().trim());

			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DocumentoDiSpesaDTO[] documenti;
			documenti = rettificaBusinessImpl.findDocumentiDiSpesaPerRettifica(idUtente, idIride, filtroDTO);

			Set<Long> docs = new LinkedHashSet<Long>();
			for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DocumentoDiSpesaDTO dto : documenti) {
				DocumentoDiSpesa doc = new DocumentoDiSpesa();
				doc.setId(dto.getIdDocumentoDiSpesa());
				doc.setDescrizioneTipologiaDocumento(dto.getDescTipoDocumentoSpesa());
				doc.setNumeroDocumento(dto.getNumeroDocumento());
				doc.setDataDocumento(DateUtil.formatDate(dto.getDtEmissioneDocumento()));
				doc.setTask(dto.getTask());
				doc.setDenominazioneFornitore(dto.getDenominazioneFornitore() + " " + dto.getCognomeFornitore() + " "
						+ dto.getNomeFornitore());
				doc.setImportoTotaleDocumento(dto.getImportoTotaleDocumento());
				doc.setDescrizioneStato(dto.getDescStatoDocumentoSpesa());
				doc.setTipoInvio(dto.getTipoInvio());
				doc.setRilievoContabile(dto.getRilievoContabile());
				doc.setDtRilievoContabile(dto.getDtRilievoContabile());
				if (dto.getTotaleRettificaDoc() == null)
					doc.setIsRettificato(Boolean.FALSE);
				else
					doc.setIsRettificato(Boolean.TRUE);

				ArrayList<Double> importiValidati = null;
				ArrayList<String> numDichiarazioni = null;
				ArrayList<Boolean> rettificatos = null;
				if (docs.contains(dto.getIdDocumentoDiSpesa())) {
					for (DocumentoDiSpesa documento : result) {
						if (documento.getId().longValue() == dto.getIdDocumentoDiSpesa()) {
							doc = documento;
							break;
						}
					}
					importiValidati = doc.getImportiValidati();
					numDichiarazioni = doc.getNumDichiarazioni();
					rettificatos = doc.getRettificatos();
					importiValidati.add(dto.getValidatoPerDichiarazione());
					numDichiarazioni.add(dto.getIdDichiarazioneSpesa().toString());
					if (dto.getTotaleRettificaDoc() == null) {
						rettificatos.add(Boolean.FALSE);
					} else {
						rettificatos.add(Boolean.TRUE);
					}
					doc.setImportiValidati(importiValidati);
					doc.setNumDichiarazioni(numDichiarazioni);
					doc.setRettificatos(rettificatos);
				} else {
					importiValidati = new ArrayList<Double>();
					numDichiarazioni = new ArrayList<String>();
					rettificatos = new ArrayList<Boolean>();
					docs.add(dto.getIdDocumentoDiSpesa());
					importiValidati.add(dto.getValidatoPerDichiarazione());
					numDichiarazioni.add(dto.getIdDichiarazioneSpesa().toString());
					if (dto.getTotaleRettificaDoc() == null) {
						rettificatos.add(Boolean.FALSE);
					} else {
						rettificatos.add(Boolean.TRUE);
					}
					doc.setImportiValidati(importiValidati);
					doc.setNumDichiarazioni(numDichiarazioni);
					doc.setRettificatos(rettificatos);
					result.add(doc);
				}

			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca dei documenti per spesa validata: ", e);
			throw new DaoException("Errore durante la ricerca dei documenti per spesa validata.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public List<MensilitaProgettoDTO> recuperaMensilitaProgetto(Long idProgetto, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::recuperaMensilitaProgetto] ";
		LOG.info(prf + " BEGIN");

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);
		LOG.info(prf + "idProgetto = " + idProgetto);

		ArrayList<MensilitaProgettoDTO> result = new ArrayList<MensilitaProgettoDTO>();
		List<MensilitaProgettoDTO> listaRaggruppata = new ArrayList<MensilitaProgettoDTO>();
		try {

			String query = "SELECT A.ANNO,\n" + "       A.MESE,\n" + "       A.ID_PROGETTO,\n" + "       A.ESITO,\n"
					+ "       A.FLAG_SABBATICO,\n" + "       A.ID_DICHIARAZIONE_SPESA,\n"
					+ "       B.NOTE_EROGAZIONE,\n" + "       A.NOTE\n" + "FROM PBANDI_T_DICH_MENS_WS A\n"
					+ "LEFT JOIN PBANDI_T_EROGAZIONE B ON B.ID_PROGETTO = A.ID_PROGETTO\n"
					+ "LEFT JOIN PBANDI_T_EROGAZIONE B ON TO_CHAR(A.ID_DICHIARAZIONE_SPESA) = B.NOTE_EROGAZIONE\n"
					+ "JOIN PBANDI_T_DICHIARAZIONE_SPESA C ON C.ID_DICHIARAZIONE_SPESA = A.ID_DICHIARAZIONE_SPESA\n"
					+ "WHERE A.ID_PROGETTO= ? \n" + "  AND A.DT_FINE_VALIDITA IS NULL\n"
					+ "  AND C.DT_CHIUSURA_VALIDAZIONE IS NOT NULL\n" + "ORDER BY A.ANNO,\n" + "         A.MESE ASC";

			LOG.info(prf + "\nQuery mensilità: \n\n" + query + "\n\n");

			result = getJdbcTemplate().query(query, rs -> {
				ArrayList<MensilitaProgettoDTO> lista = new ArrayList<MensilitaProgettoDTO>();

				while (rs.next()) {
					MensilitaProgettoDTO mensilita = new MensilitaProgettoDTO();
					mensilita.setAnno(rs.getLong("ANNO"));
					mensilita.setMese(rs.getString("MESE"));
					mensilita.setIdPrg(rs.getLong("ID_PROGETTO"));
					mensilita.setEsito(rs.getString("ESITO"));
					mensilita.setSabbatico(rs.getString("FLAG_SABBATICO"));
					mensilita.setIdDichSpesa(rs.getLong("ID_DICHIARAZIONE_SPESA"));
					String idDichSpesa = String.valueOf(mensilita.getIdDichSpesa());
					mensilita.setErogato(idDichSpesa.equals(rs.getString("NOTE_EROGAZIONE")));
					Clob clob = rs.getClob("NOTE");
					if (clob != null) {
						mensilita.setNote(clob.getSubString(1, (int) clob.length()));
					}
					lista.add(mensilita);
				}

				return lista;
			}, idProgetto);

			// Controllo se ci sono più occorrenze per la stessa mensilità
			if (!result.isEmpty()) {
				Map<Long, List<MensilitaProgettoDTO>> anniGrouped = result.stream()
						.collect(Collectors.groupingBy(MensilitaProgettoDTO::getAnno));
				for (Map.Entry<Long, List<MensilitaProgettoDTO>> anno : anniGrouped.entrySet()) {
					Map<String, List<MensilitaProgettoDTO>> mensilitaGrouped = anno.getValue().stream()
							.collect(Collectors.groupingBy(MensilitaProgettoDTO::getMese));
					for (int i = 1; i <= 12; i++) {
						String prefisso = i < 10 ? "0" : "";
						String mese = prefisso + String.valueOf(i);
						// Caso di mese singolo
						if (mensilitaGrouped.containsKey(mese) && mensilitaGrouped.get(mese).size() == 1) {
							listaRaggruppata.addAll(mensilitaGrouped.get(mese));
						} else if (mensilitaGrouped.containsKey(mese) && mensilitaGrouped.get(mese).size() > 1) {
							// Caso di più occorrenze per lo stesso mese
							boolean condVerificata = false;
							// 1° criterio di raggruppamento
							for (MensilitaProgettoDTO mensilita : mensilitaGrouped.get(mese)) {
								if ("OK".equals(mensilita.getEsito())) {
									listaRaggruppata.add(mensilita);
									condVerificata = true;
									break;
								}
							}
							// 2° criterio di raggruppamento (se non si è verificato il primo)
							if (!condVerificata) {
								for (MensilitaProgettoDTO mensilita : mensilitaGrouped.get(mese)) {
									if ("true".equals(mensilita.getSabbatico())) {
										listaRaggruppata.add(mensilita);
										condVerificata = true;
										break;
									}
								}
							}
							// 3° criterio di raggruppamento (se non si è verificato il secondo)
							if (!condVerificata) {
								for (MensilitaProgettoDTO mensilita : mensilitaGrouped.get(mese)) {
									if ("NV".equals(mensilita.getEsito())) {
										listaRaggruppata.add(mensilita);
										condVerificata = true;
										break;
									}
								}
							}
							// 4° criterio di raggruppamento (se non si è verificato il terzo)
							if (!condVerificata) {
								for (MensilitaProgettoDTO mensilita : mensilitaGrouped.get(mese)) {
									if ("KO".equals(mensilita.getEsito())) {
										listaRaggruppata.add(mensilita);
										condVerificata = true;
										break;
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca delle mensilità per il progetto: ", e);
			throw new DaoException("Errore durante la ricerca delle mensilità per il progetto.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaRaggruppata;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EsitoSalvaSpesaValidataDTO validaMensilitaProgetto(List<MensilitaProgettoDTO> listaMensilitaProgetto,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::validaMensilitaProgetto] ";
		LOG.info(prf + " BEGIN");

		if (listaMensilitaProgetto.isEmpty()) {
			throw new InvalidParameterException("listaMensilitaProgetto vuota.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "idUtente = " + idUtente);

		EsitoSalvaSpesaValidataDTO esito = new EsitoSalvaSpesaValidataDTO();

		/**
		 * Buono Domiciliarità/Residenzialità - Chiamata al servizio di Validazione
		 * integrazione
		 * 
		 */
		Long idDichiarazioneDiSpesa = listaMensilitaProgetto.get(0).getIdDichSpesa();
		HashMap<String, String> result = validazioneRendicontazioneDAOImpl
				.getTitoloBandoENumeroDomandaValidazione(idDichiarazioneDiSpesa, "BR69");
		String response = null;
		if (result != null && !result.isEmpty()) {
			String numeroDomanda = result.get("numeroDomanda");
			String titoloBando = result.get("titoloBando");
			LOG.info("numeroDomanda: " + numeroDomanda + ", titoloBando: " + titoloBando);
			List<MensilitaDTO> meseAnnoEsitoNote = validazioneRendicontazioneDAOImpl
					.getMeseAnnoEsitoNote(idDichiarazioneDiSpesa);
			String host = "";
			String user = "";
			String pass = "";
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
			headers.add("Authorization", validazioneRendicontazioneDAOImpl.getBasicAuthenticationHeader(user, pass));
			headers.add("X-Request-Id", uuid4.toString());
			ObjectMapper requestMapper = new ObjectMapper();
			requestMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			requestMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_FORMAT_REQUEST));
			String jsonRequest = "{\"validazione_dichiarazione\": {" + "\"id_dichiarazione_spesa_bandi\": "
					+ requestMapper.writeValueAsString(idDichiarazioneDiSpesa.toString()) + ",\"mensilita\": "
					+ requestMapper.writeValueAsString(meseAnnoEsitoNote) + "}}";
			LOG.info("Request: " + jsonRequest);
			HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);
			response = template.postForObject(host + numeroDomanda + "/dichiarazione/validazione", entity,
					String.class);
		}

		try {

			if (response == null || (response != null && "OK".equals(response))) {

				// Aggiornamento record
				for (MensilitaProgettoDTO mensilita : listaMensilitaProgetto) {
					String queryUpdate = "UPDATE PBANDI_T_DICH_MENS_WS\n" + "SET ESITO= ? ,\n" + "ID_UTENTE_AGG= ? ,\n"
							+ "DT_AGGIORNAMENTO = SYSDATE ,\n" + "NOTE = ? \n" + "WHERE ANNO= ? \n" + "  AND MESE= ? \n"
							+ "  AND ID_DICHIARAZIONE_SPESA= ?";
					getJdbcTemplate().update(queryUpdate, mensilita.getEsito(), idUtente, mensilita.getNote(),
							mensilita.getAnno(), mensilita.getMese(), mensilita.getIdDichSpesa());

					//
					List<Integer> elencoDichSpesa = new ArrayList<Integer>();
					String queryElencoDichSpesa = "SELECT ID_DICHIARAZIONE_SPESA \n"
							+ "FROM PBANDI_T_DICHIARAZIONE_SPESA ptds \n" + "WHERE ID_PROGETTO = ? \n"
							+ "  AND DT_CHIUSURA_VALIDAZIONE IS NOT NULL";

					LOG.info(prf + "\nQuery ElencoDichSpesa: \n\n" + queryElencoDichSpesa + "\n\n");

					elencoDichSpesa = getJdbcTemplate().query(queryElencoDichSpesa, rs -> {
						List<Integer> lista = new ArrayList<Integer>();

						while (rs.next()) {
							Integer idDicharazioneSpesa = rs.getInt("ID_DICHIARAZIONE_SPESA");
							lista.add(idDicharazioneSpesa);
						}

						return lista;
					}, mensilita.getIdPrg());

					for (Integer dichSpesa : elencoDichSpesa) {
						ArrayList<DocumentoDTO> documenti = new ArrayList<>();
						String queryDocumenti = "SELECT A.ID_PAGAMENTO, \n" + "       B.ID_DOCUMENTO_DI_SPESA, \n"
								+ "       C.ID_STATO_DOCUMENTO_SPESA_VALID \n"
								+ "FROM PBANDI_R_PAGAMENTO_DICH_SPESA A \n"
								+ "JOIN PBANDI_R_PAGAMENTO_DOC_SPESA B ON A.ID_PAGAMENTO = B.ID_PAGAMENTO \n"
								+ "JOIN PBANDI_R_DOC_SPESA_PROGETTO C ON B.ID_DOCUMENTO_DI_SPESA = C.ID_DOCUMENTO_DI_SPESA \n"
								+ "JOIN PBANDI_T_QUOTA_PARTE_DOC_SPESA D ON D.ID_DOCUMENTO_DI_SPESA = C.ID_DOCUMENTO_DI_SPESA \n"
								+ "JOIN PBANDI_R_PAG_QUOT_PARTE_DOC_SP E ON E.ID_QUOTA_PARTE_DOC_SPESA = D.ID_QUOTA_PARTE_DOC_SPESA \n"
								+ "AND E.ID_PAGAMENTO = A.ID_PAGAMENTO \n" + "WHERE A.ID_DICHIARAZIONE_SPESA = ? \n"
								+ "ORDER BY B.ID_DOCUMENTO_DI_SPESA, \n" + "         A.ID_PAGAMENTO";
						LOG.info(prf + "\nQuery documenti: \n\n" + queryDocumenti + "\n\n");

						documenti = getJdbcTemplate().query(queryDocumenti, rs -> {
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
						}, dichSpesa);

						// Pagamenti
						int cntPagamenti = 0;
						for (DocumentoDTO doc : documenti) {
							if ("Approvato".equals(doc.getStatoDocumento())) {
								cntPagamenti++;
							}
						}
						int cntMensApprov = 0;
						for (MensilitaProgettoDTO mens : listaMensilitaProgetto) {
							Integer idDichSpesaMens = mens.getIdDichSpesa().intValue();
							if ("OK".equals(mens.getEsito()) && idDichSpesaMens.equals(dichSpesa)) {
								cntMensApprov++;
							}
						}

						BigDecimal importoValidatoTotale = BigDecimal.valueOf(600)
								.multiply(BigDecimal.valueOf(cntMensApprov));
						for (DocumentoDTO doc : documenti) {
							BigDecimal importoValidatoPagamento = null;
							if ("Approvato".equals(doc.getStatoDocumento()) && cntPagamenti > 0) {
								importoValidatoPagamento = importoValidatoTotale
										.divide(BigDecimal.valueOf(cntPagamenti), 2, RoundingMode.HALF_UP);
							} else {
								importoValidatoPagamento = BigDecimal.valueOf(0);
							}
							String queryUpdatePagamento = "UPDATE PBANDI_R_PAG_QUOT_PARTE_DOC_SP "
									+ "SET IMPORTO_VALIDATO = ?, " + "IMPORTO_QUIETANZATO = ? "
									+ "WHERE ID_PAGAMENTO = ?";
							getJdbcTemplate().update(queryUpdatePagamento, importoValidatoPagamento,
									importoValidatoPagamento, doc.getIdPagamento());
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
						BigDecimal importoRendTotale = BigDecimal.valueOf(600)
								.multiply(BigDecimal.valueOf(cntMensApprov));
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
							getJdbcTemplate().update(queryUpdateDocumenti, importoRendDocumento,
									doc.getIdDocumentoSpesa());

							String queryUpdateQuotaParte = "UPDATE PBANDI_T_QUOTA_PARTE_DOC_SPESA \n"
									+ "SET IMPORTO_QUOTA_PARTE_DOC_SPESA = ? \n" + "WHERE ID_DOCUMENTO_DI_SPESA = ?";
							getJdbcTemplate().update(queryUpdateQuotaParte, importoRendDocumento,
									doc.getIdDocumentoSpesa());
						}
					}
				}

				esito.setEsito(true);
			} else {
				LOG.error(
						"L’esito non è stato comunicato al sistema per la rendicontazione del beneficiario. Response servizio: "
								+ response);
				esito.setEsito(false);
				ArrayList<String> messaggi = new ArrayList<String>();
				messaggi.add("L’esito non è stato comunicato al sistema per la rendicontazione del beneficiario");
				esito.setMessaggi(messaggi);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la validazione delle mensilità per progetto: ", e);
			throw new DaoException("Errore durante la validazione delle mensilità per progetto.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;

	}

	@Override
	// Ex ReportExcelAction.generaReportDocumentiDiSpesa().
	public EsitoLeggiFile reportDettaglioDocumentoDiSpesa(Long idProgetto, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::reportDettaglioDocumentoDiSpesa] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoReportDettaglioDocumentiDiSpesaDTO esitoSrv;
			esitoSrv = rettificaBusinessImpl.generaReportDettaglioDocumentoDiSpesa(idUtente, idIride, idProgetto);

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
	// Ex: rettificaAction.findDichiarazioniDocumento()
	public ArrayList<DichiarazioneSpesaValidataDTO> dichiarazioniDocumentoDiSpesa(Long idDocumentoDiSpesa,
			Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::dichiarazioniDocumentoDiSpesa] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = "
				+ idUtente);

		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		ArrayList<DichiarazioneSpesaValidataDTO> result = new ArrayList<DichiarazioneSpesaValidataDTO>();
		try {

			Map<Long, DichiarazioneSpesaValidataDTO> mapDich = new LinkedHashMap<Long, DichiarazioneSpesaValidataDTO>();

			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DichiarazioneDiSpesaRettificaDTO[] dichiarazioniSrv;
			dichiarazioniSrv = rettificaBusinessImpl.findDichiarazioniDocumento(idUtente, idIride, idDocumentoDiSpesa,
					idProgetto);
			LOG.info(prf + "Dichiarazioni trovate da rettificaBusinessImpl.findDichiarazioniDocumento(): "
					+ (dichiarazioniSrv != null ? dichiarazioniSrv.length : 0));

			for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DichiarazioneDiSpesaRettificaDTO dto : dichiarazioniSrv) {

				DichiarazioneSpesaValidataDTO model = new DichiarazioneSpesaValidataDTO();
				model.setDescTipoDichiarazioneSpesa(dto.getDescTipoDichiarazioneSpesa());
				model.setDtChiusuraValidazione(DateUtil.formatDate(dto.getDtDichiarazione()));
				model.setIdDichiarazioneSpesa(dto.getIdDichiarazioneSpesa());
				model.setIdProgetto(dto.getIdProgetto());
				model.setNoteChiusuraValidazione(dto.getNoteChiusuraValidazione());
				model.setPeriodoAl(DateUtil.formatDate(dto.getPeriodoAl()));
				model.setPeriodoDal(DateUtil.formatDate(dto.getPeriodoDal()));
				model.setPeriodo("dal " + DateUtil.formatDate(dto.getPeriodoDal()) + " al "
						+ DateUtil.formatDate(dto.getPeriodoAl()));
				model.setIdDocIndexDichiarazioneDiSpesa(dto.getIdDocIndexDichSpesa());
				model.setIdDocIndexReportDettaglio(dto.getIdDocIndexReportDettaglio());
				model.setIdUtenteIns(dto.getIdUtenteIns());

				mapDich.put(dto.getIdDichiarazioneSpesa(), model);
			}

			result = new ArrayList<DichiarazioneSpesaValidataDTO>(mapDich.values());
			LOG.info(prf + "Dichiarazioni restituite: " + (result != null ? result.size() : 0));

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca delle dichiarazioni: ", e);
			throw new DaoException("Errore durante la ricerca delle dichiarazioni.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	// Ex: RettificaBusinessImpl.findPagamentiAssociati()
	private ArrayList<RigaValidazioneItemDTO> findPagamentiAssociati(Long idUtente, String idIride, Long idProgetto,
			Long idVoceSpesa, Long idDocumentoSpesa, String numDichiarazione) throws Exception {

		it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DocumentoDiSpesaDTO filtro = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.DocumentoDiSpesaDTO();
		filtro.setIdDocumentoDiSpesa(idDocumentoSpesa);
		filtro.setIdProgetto(idProgetto);
		filtro.setIdVoceSpesa(idVoceSpesa);
		filtro.setIdDichiarazioneSpesa(Long.valueOf(numDichiarazione));

		it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO[] pagamenti;
		pagamenti = rettificaBusinessImpl.findPagamentiAssociatiDocumentoVoceSpesa(idUtente, idIride, filtro);

		Map<String, RigaValidazioneItemDTO> voci = new TreeMap<String, RigaValidazioneItemDTO>();
		for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO dto : pagamenti) {
			// modifica richiesta da G.Bruno 12 /09/14: accorpamento vds

			if (dto.getVociDiSpesa() != null) {
				for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO voce : dto.getVociDiSpesa()) {
					RigaValidazioneItemDTO itemVoce = new RigaValidazioneItemDTO();
					if (voci.containsKey(voce.getDescVoceDiSpesa())) {
						itemVoce = voci.get(voce.getDescVoceDiSpesa());
					}
					itemVoce.setIdPagamento(dto.getIdPagamento());
					itemVoce.setRigaPagamento(false);
					itemVoce.setDescrizioneVoceDiSpesa(voce.getDescVoceDiSpesa());

					if (itemVoce.getImportoAssociatoVoceDiSpesa() == null)
						itemVoce.setImportoAssociatoVoceDiSpesa(0d);
					if (itemVoce.getImportoValidatoPrecedenteVoceDiSpesa() == null)
						itemVoce.setImportoValidatoPrecedenteVoceDiSpesa(0d);
					if (itemVoce.getImportoValidatoVoceDiSpesa() == null)
						itemVoce.setImportoValidatoVoceDiSpesa(0d);
					if (itemVoce.getImportoTotaleRettifica() == null)
						itemVoce.setImportoTotaleRettifica(0d);
					if (voce.getImportoQuietanzato() == null)
						voce.setImportoQuietanzato(0d);
					if (voce.getImportoValidatoLordo() == null)
						voce.setImportoValidatoLordo(0d);
					if (voce.getImportoValidato() == null)
						voce.setImportoValidato(0d);
					if (voce.getImportoTotaleRettifica() == null)
						voce.setImportoTotaleRettifica(0d);

					itemVoce.setImportoAssociatoVoceDiSpesa(
							NumberUtil.sum(itemVoce.getImportoAssociatoVoceDiSpesa(), voce.getImportoQuietanzato()));
					itemVoce.setImportoValidatoPrecedenteVoceDiSpesa(NumberUtil
							.sum(itemVoce.getImportoValidatoPrecedenteVoceDiSpesa(), voce.getImportoValidatoLordo()));
					itemVoce.setImportoValidatoVoceDiSpesa(
							NumberUtil.sum(itemVoce.getImportoValidatoVoceDiSpesa(), voce.getImportoValidato()));
					itemVoce.setImportoTotaleRettifica(
							NumberUtil.sum(itemVoce.getImportoTotaleRettifica(), voce.getImportoTotaleRettifica()));
					itemVoce.setIdQuotaParte(voce.getIdQuotaParteDocSpesa());
					itemVoce.setRigaModificabile(true);
					itemVoce.setProgrPagQuotParteDocSp(voce.getProgrPagQuotParteDocSp());

					ArrayList<RettificaVoceItem> rettifiche = findRettificheVoce(idUtente, idIride,
							voce.getProgrPagQuotParteDocSp());
					if (rettifiche != null && !rettifiche.isEmpty()) {
						itemVoce.setLinkRettifiche("OK");
					}

					voci.put(itemVoce.getDescrizioneVoceDiSpesa(), itemVoce);
				}
			}
		}
		ArrayList<RigaValidazioneItemDTO> result = new ArrayList<RigaValidazioneItemDTO>(voci.values());

		// Jira PNANDI-2898: su richiesta di Pacilio si ignorano le righe con Importo
		// Associato uguale a zero.
		ArrayList<RigaValidazioneItemDTO> resultRidotto = new ArrayList<RigaValidazioneItemDTO>();
		for (RigaValidazioneItemDTO item : result) {
			if (item.getImportoAssociatoVoceDiSpesa() != null && item.getImportoAssociatoVoceDiSpesa().intValue() > 0) {
				resultRidotto.add(item);
			}
		}

		return resultRidotto;
	}

	// Jira PBANDI-2898: eseguo findRettifiche() solo se progQuotaParte è
	// valorizzato.
	// Ex: RettificaBusinessImpl.findRettificheVoce()
	private ArrayList<RettificaVoceItem> findRettificheVoce(Long idUtente, String idIride, Long progQuotaParte)
			throws Exception {
		ArrayList<RettificaVoceItem> rettifiche = new ArrayList<RettificaVoceItem>();
		if (progQuotaParte != null) {
			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO filtro = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO();
			filtro.setProgrPagQuotParteDocSp(progQuotaParte);
			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO[] result;
			result = rettificaBusinessImpl.findRettifiche(idUtente, idIride, filtro);
			if (result != null) {
				Long progrQuota = -1L;
				for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO dto : result) {
					if (NumberUtil.compare(dto.getProgrPagQuotParteDocSp(), progrQuota) != 0) {
						progrQuota = dto.getProgrPagQuotParteDocSp();
						RettificaVoceItem rigaVoce = new RettificaVoceItem();
						rigaVoce.setDescVoceDiSpesa(dto.getDescVoceDiSpesa());
						rigaVoce.setRigaVoce(Boolean.TRUE);
						rettifiche.add(rigaVoce);
					}
					RettificaVoceItem rigaRettifica = new RettificaVoceItem();
					rigaRettifica.setCodiceFiscaleSoggetto(dto.getCodiceFiscaleSoggetto());
					rigaRettifica.setDtRettifica(DateUtil.formatDate(dto.getDtRettifica()));
					rigaRettifica.setImportoRettifica(dto.getImportoRettifica());
					rigaRettifica.setRiferimento(dto.getRiferimento());
					rigaRettifica.setRigaVoce(Boolean.FALSE);
					rettifiche.add(rigaRettifica);
				}
			}
		}
		return rettifiche;
	}

	// Ex: RettificaBusinessImpl.getNoteDiCredito()
	public ArrayList<RigaNotaDiCreditoItemDTO> getNoteDiCredito(Long idUtente, String identitaIride,
			Long idDocumentoDiSpesa, Long idProgetto) throws Exception {

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
	public VoceDiSpesaDTO voceDiSpesa(Long idQuotaParte, Long idDocumentoDiSpesa, Long idProgetto, Long idUtente,
			String idIride) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::vociDiSpesaAssociateValidazione] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto
				+ "; idQuotaParte = " + idQuotaParte + "; idUtente = " + idUtente);

		if (idQuotaParte == null) {
			throw new InvalidParameterException("idQuotaParte non valorizzato");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		VoceDiSpesaDTO result = new VoceDiSpesaDTO();
		try {

			// Legge le voci di spesa associate tramite il servizio di pandisrv.
			it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO[] vociSpesaPbandisrv = null;
			vociSpesaPbandisrv = gestioneVociDiSpesaBusinessImpl.findVociDiSpesaAssociateSemplificazione(idUtente,
					idIride, idDocumentoDiSpesa, idProgetto);

			// Da oggetto pbandisrv a oggetto pbweb.
			List<VoceDiSpesaDTO> listaVdS = beanUtil.transformToArrayList(vociSpesaPbandisrv,
					it.csi.pbandi.pbweb.dto.documentoDiSpesa.VoceDiSpesaDTO.class);

			for (VoceDiSpesaDTO dto : listaVdS) {
				if (dto.getIdQuotaParteDocSpesa().intValue() == idQuotaParte.intValue())
					result = dto;
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca delle voci di spesa: ", e);
			throw new DaoException(" ERRORE nella ricerca delle voci di spesa.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex CPBERettificaDocumentoDiSpesa.salvaDocumentiDaRettificare()
	// Click su bottone "SALVA" di "Gestione Spesa Validata": possibili esiti (da
	// quel che ho capito):
	// - esito = true e chiedereConfermaPerProseguire = false: tutto bene, le
	// modifiche sono state salvate..
	// - esito = true e chiedereConfermaPerProseguire = true: ci sono dei warning,
	// si chiede conferma all'utente.
	// - esito = false: tutto male, non si procede con il salvataggio.
	public EsitoSalvaSpesaValidataDTO salvaSpesaValidata(SalvaSpesaValidataRequest salvaSpesaValidataRequest,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::salvaSpesaValidata] ";
		LOG.info(prf + "BEGIN");

		if (salvaSpesaValidataRequest == null) {
			throw new InvalidParameterException("salvaSpesaValidataRequest non valorizzato");
		}

		ArrayList<RigaValidazioneItemDTO> pagamentiAssociati = salvaSpesaValidataRequest.getPagamentiAssociati();
		Long idDichiarazioneDiSpesa = salvaSpesaValidataRequest.getIdDichiarazioneDiSpesa();
		Long idDocumentoDiSpesa = salvaSpesaValidataRequest.getIdDocumentoDiSpesa();
		Long idProgetto = salvaSpesaValidataRequest.getIdProgetto();

		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (pagamentiAssociati == null) {
			throw new InvalidParameterException("pagamentiAssociati non valorizzato");
		}
		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoSalvaSpesaValidataDTO esito = new EsitoSalvaSpesaValidataDTO();
		esito.setChiedereConfermaPerProseguire(false);
		try {

			// Controllo dei pagamenti

			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti esitoPagam;
			esitoPagam = this.controlloPagamenti(idUtente, idIride, idDichiarazioneDiSpesa, idDocumentoDiSpesa,
					idProgetto, pagamentiAssociati);

			if (esitoPagam == null) {
				esito.setEsito(false);
				esito.getMessaggi().add(ErrorMessages.ERRORE_SERVER);
				return esito;
			}
			if (!esitoPagam.getEsito()) {
				esito.setEsito(false);
				if (esitoPagam.getMsgs() != null) {
					for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.MessaggioDTO dto : esitoPagam.getMsgs()) {
						esito.getMessaggi().add(TraduttoreMessaggiPbandisrv.traduci(dto.getMsgKey(), dto.getParams()));
					}
				}
				return esito;
			}

			// Verifico che non ci siano dei warning

			esitoPagam = this.checkRettificaPagamenti(idUtente, idIride, idProgetto, pagamentiAssociati);
			if (esitoPagam != null && esitoPagam.getEsito()) {

				// Eseguo l'update dei pagamenti su db.

				esitoPagam = this.rettificaPagamenti(idUtente, idIride, idDichiarazioneDiSpesa, idDocumentoDiSpesa,
						idProgetto, pagamentiAssociati);
				if (esitoPagam != null && esitoPagam.getEsito()) {

					// 26/08/2014: in gestione spesa validata le note sono in sola lettura
					// validazioneDichiarazioneDiSpesaBusiness.saveNoteValidazioneDoc(userinfo,
					// theModel.getAppDataDettaglioDocumentoDiSpesa().getId(),
					// idProgetto,
					// theModel.getAppDataDettaglioDocumentoDiSpesa().getNoteValidazione());

					esito.setEsito(true);
					esito.setChiedereConfermaPerProseguire(false);
					for (MessaggioDTO msg : esitoPagam.getMsgs()) {
						if ("W.N059".equalsIgnoreCase(msg.getMsgKey())) {
							esito.getMessaggi().add("Nessun importo modificato!");
							;
						} else {
							for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.MessaggioDTO dto : esitoPagam.getMsgs()) {
								esito.getMessaggi()
										.add(TraduttoreMessaggiPbandisrv.traduci(dto.getMsgKey(), dto.getParams()));
							}
						}
					}

				} else if (esito != null && !esito.getEsito()) {
					esito.setEsito(false);
					if (esitoPagam.getMsgs() != null) {
						for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.MessaggioDTO dto : esitoPagam.getMsgs()) {
							esito.getMessaggi()
									.add(TraduttoreMessaggiPbandisrv.traduci(dto.getMsgKey(), dto.getParams()));
						}
					}
				}

			} else if (esitoPagam != null && !esitoPagam.getEsito()) {

				// Chiedo la conferma per salvare.

				esito.setEsito(true);
				esito.setChiedereConfermaPerProseguire(true);
				if (esitoPagam.getMsgs() != null) {
					for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.MessaggioDTO dto : esitoPagam.getMsgs()) {
						esito.getMessaggi().add(TraduttoreMessaggiPbandisrv.traduci(dto.getMsgKey(), dto.getParams()));
					}
				}
			}

			if (esito.getEsito() && esito.getMessaggi().size() == 0) {
				esito.getMessaggi().add(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel salvataggio della spesa validata: ", e);
			throw new DaoException(" ERRORE nel salvataggio della spesa validata.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	// Ex RettificaBusinessImpl.checkRettificaPagamenti()
	private it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti checkRettificaPagamenti(Long idUtente,
			String idIride, Long idProgetto, ArrayList<RigaValidazioneItemDTO> pagamenti) throws Exception {
		List<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO> listPagamenti = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO>();
		Long idPagamento = -1L;
		it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO pagamento = null;
		List<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO> voci = null;
		for (RigaValidazioneItemDTO item : pagamenti) {
			if (NumberUtil.compare(idPagamento, item.getIdPagamento()) != 0) {
				if (voci != null) {
					pagamento.setVociDiSpesa(beanUtil.transform(voci,
							it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO.class));
				}
				idPagamento = item.getIdPagamento();
				pagamento = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO();
				pagamento.setIdPagamento(item.getIdPagamento());
				pagamento.setImportoPagamento(item.getImportoTotalePagamento());
				listPagamenti.add(pagamento);
				voci = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO>();

			}
			if (!item.getRigaPagamento()) {
				it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO voce = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO();
				voce.setIdPagamento(item.getIdPagamento());
				voce.setIdQuotaParteDocSpesa(item.getIdQuotaParte());
				voce.setProgrPagQuotParteDocSp(item.getProgrPagQuotParteDocSp());
				voce.setImportoValidato(item.getImportoValidatoVoceDiSpesa());
				voce.setImportoQuietanzato(item.getImportoAssociatoVoceDiSpesa());
				voce.setImportoValidatoLordo(item.getImportoValidatoPrecedenteVoceDiSpesa());
				voce.setImportoTotaleRettifica(item.getImportoTotaleRettifica());
				voce.setRiferimento(item.getRiferimentoRettifica());
				voci.add(voce);
			}
		}
		if (voci != null) {
			pagamento.setVociDiSpesa(beanUtil.transform(voci,
					it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO.class));
		}

		return rettificaBusinessImpl.checkRettificaPagamenti(idUtente, idIride, idProgetto, beanUtil
				.transform(listPagamenti, it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO.class));
	}

	// Ex RettificaBusinessImpl.rettificaPagamenti()
	public it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti rettificaPagamenti(Long idUtente,
			String idIride, Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			ArrayList<RigaValidazioneItemDTO> pagamenti) throws Exception {

		List<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO> listPagamenti = this
				.popolaPagamentiPerServizio(pagamenti);

		it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti esito = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti();
		try {

			esito = rettificaBusinessImpl.rettificaPagamenti(idUtente, idIride, idDichiarazioneDiSpesa,
					idDocumentoDiSpesa, idProgetto, beanUtil.transform(listPagamenti,
							it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO.class));

		} catch (RettificaException e) {
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(e.getMessage());
			esito.setEsito(Boolean.FALSE);
			esito.setMsgs(new MessaggioDTO[] { msg });
		}
		return esito;
	}

	// Ex RettificaBusinessImpl.controlloPagamenti()
	private it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti controlloPagamenti(Long idUtente,
			String idIride, Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto,
			ArrayList<RigaValidazioneItemDTO> pagamenti) throws Exception {

		List<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO> listPagamenti = popolaPagamentiPerServizio(
				pagamenti);

		it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti esito = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.contoeconomico.ContoEconomicoDTO c;
			c = contoeconomicoBusinessImpl.findDatiContoEconomico(idUtente, idIride, idProgetto);

			esito = rettificaBusinessImpl.controlloPagamenti(idUtente, idIride, idDichiarazioneDiSpesa,
					idDocumentoDiSpesa, idProgetto,
					beanUtil.transform(listPagamenti,
							it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO.class),
					c.getImportoSpesaValidataTotale());

		} catch (RettificaException e) {
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(e.getMessage());
			esito.setEsito(Boolean.FALSE);
			esito.setMsgs(new MessaggioDTO[] { msg });
		}
		return esito;
	}

	// Ex RettificaBusinessImpl.rettificaPagamentoRettificaDTO()
	private List<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO> popolaPagamentiPerServizio(
			ArrayList<RigaValidazioneItemDTO> pagamenti) {
		List<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO> listPagamenti = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO>();
		Long idPagamento = -1L;
		it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO pagamento = null;
		List<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO> voci = null;
		for (RigaValidazioneItemDTO item : pagamenti) {
			if (NumberUtil.compare(idPagamento, item.getIdPagamento()) != 0) {
				if (voci != null) {
					pagamento.setVociDiSpesa(beanUtil.transform(voci,
							it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO.class));
				}
				idPagamento = item.getIdPagamento();
				pagamento = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.PagamentoRettificaDTO();
				pagamento.setIdPagamento(item.getIdPagamento());
				pagamento.setImportoPagamento(item.getImportoTotalePagamento());
				listPagamenti.add(pagamento);
				voci = new ArrayList<it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO>();
			}
			if (!item.getRigaPagamento()) {
				it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO voce = new it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO();
				voce.setIdPagamento(item.getIdPagamento());
				voce.setIdQuotaParteDocSpesa(item.getIdQuotaParte());
				voce.setProgrPagQuotParteDocSp(item.getProgrPagQuotParteDocSp());
				voce.setImportoValidato(item.getImportoValidatoVoceDiSpesa());
				voce.setImportoQuietanzato(item.getImportoAssociatoVoceDiSpesa());
				voce.setImportoValidatoLordo(item.getImportoValidatoPrecedenteVoceDiSpesa());

				// VN: Fix PBandi-1407: Il totale della rettifica viene calcolato come
				// differenza tra il nuovo ed il vecchio
				BigDecimal importoTotaleRettifica = NumberUtil.subtract(
						BeanUtil.transformToBigDecimal(item.getImportoValidatoVoceDiSpesa()),
						BeanUtil.transformToBigDecimal(item.getImportoValidatoPrecedenteVoceDiSpesa()));
				voce.setImportoTotaleRettifica(NumberUtil.toDouble(importoTotaleRettifica));
				voce.setRiferimento(item.getRiferimentoRettifica());
				voci.add(voce);
			}
		}
		if (voci != null) {
			pagamento.setVociDiSpesa(beanUtil.transform(voci,
					it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.VoceDiSpesaRettificaDTO.class));
		}

		return listPagamenti;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex CPBERettificaDocumentoDiSpesa.salvaDocumentiDaRettificare()
	// Click su bottone "CONFERMA" di "Gestione Spesa Validata" a seguito del
	// salvataggio.
	public EsitoSalvaSpesaValidataDTO confermaSalvaSpesaValidata(
			ConfermaSalvaSpesaValidataRequest confermaSalvaSpesaValidataRequest, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::confermaSalvaSpesaValidata] ";
		LOG.info(prf + "BEGIN");

		if (confermaSalvaSpesaValidataRequest == null) {
			throw new InvalidParameterException("confermaSalvaSpesaValidataRequest non valorizzato");
		}

		Long idDichiarazioneDiSpesa = confermaSalvaSpesaValidataRequest.getIdDichiarazioneDiSpesa();
		Long idDocumentoDiSpesa = confermaSalvaSpesaValidataRequest.getIdDocumentoDiSpesa();
		Long idProgetto = confermaSalvaSpesaValidataRequest.getIdProgetto();
		ArrayList<RigaValidazioneItemDTO> pagamentiAssociati = confermaSalvaSpesaValidataRequest
				.getPagamentiAssociati();
		String noteValidazione = confermaSalvaSpesaValidataRequest.getNoteValidazione();

		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idDocumentoDiSpesa = "
				+ idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);

		if (pagamentiAssociati == null) {
			throw new InvalidParameterException("pagamentiAssociati non valorizzato");
		}
		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato");
		}
		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}

		EsitoSalvaSpesaValidataDTO esito = new EsitoSalvaSpesaValidataDTO();
		esito.setEsito(true);
		esito.setChiedereConfermaPerProseguire(false);
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.EsitoRettificaPagamenti esitoPagam;
			esitoPagam = this.rettificaPagamenti(idUtente, idIride, idDichiarazioneDiSpesa, idDocumentoDiSpesa,
					idProgetto, pagamentiAssociati);

			if (esitoPagam != null && esitoPagam.getEsito()) {

				// validazioneRendicontazioneBusinessImpl.saveNoteValidazioneDoc(idUtente,idIride,idDocumentoDiSpesa,idProgetto,noteValidazione);

				for (MessaggioDTO msg : esitoPagam.getMsgs()) {
					if ("W.N059".equalsIgnoreCase(msg.getMsgKey())) {
						esito.getMessaggi().add("Nessun importo modificato!");
						;
					} else {
						for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.MessaggioDTO dto : esitoPagam.getMsgs()) {
							esito.getMessaggi()
									.add(TraduttoreMessaggiPbandisrv.traduci(dto.getMsgKey(), dto.getParams()));
						}
					}
				}

			} else if (esitoPagam != null && !esitoPagam.getEsito()) {

				for (MessaggioDTO msg : esitoPagam.getMsgs()) {
					if ("W.N059".equalsIgnoreCase(msg.getMsgKey())) {
						esito.getMessaggi().add("Nessun importo modificato!");
						;
					} else {
						for (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.MessaggioDTO dto : esitoPagam.getMsgs()) {
							esito.getMessaggi()
									.add(TraduttoreMessaggiPbandisrv.traduci(dto.getMsgKey(), dto.getParams()));
						}
					}
				}

			}

			// aggiorno note di validazione (modificabili solo se presente la BR63 e il
			// pulsante SOSPENDI)
			PbandiRDocSpesaProgettoVO pbandiRDocSpesaProgettoVO = new PbandiRDocSpesaProgettoVO();
			pbandiRDocSpesaProgettoVO.setIdDocumentoDiSpesa(new BigDecimal(idDocumentoDiSpesa.longValue()));
			pbandiRDocSpesaProgettoVO.setIdProgetto(new BigDecimal(idProgetto.longValue()));

			pbandiRDocSpesaProgettoVO = genericDAO.findSingleWhere(pbandiRDocSpesaProgettoVO);
			pbandiRDocSpesaProgettoVO.setNoteValidazione(noteValidazione);
			genericDAO.updateNullables(pbandiRDocSpesaProgettoVO);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella conferma del salvataggio della spesa validata: ", e);
			throw new DaoException(" ERRORE nella conferma del salvataggio della spesa validata.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	// Ex RettificaAction.findRettificheVoce()
	public ArrayList<RettificaVoceItem> dettaglioRettifiche(Long progQuotaParte, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::dettaglioRettifiche] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "progQuotaParte = " + progQuotaParte + "; idUtente = " + idUtente);

		if (progQuotaParte == null) {
			throw new InvalidParameterException("progQuotaParte non valorizzato");
		}

		ArrayList<RettificaVoceItem> result = new ArrayList<RettificaVoceItem>();
		try {

			result = this.findRettificheVoce(idUtente, idIride, progQuotaParte);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca delle rettifiche: ", e);
			throw new DaoException(" ERRORE nella ricerca delle rettifiche.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public EsitoDTO salvaRilievoDS(Long idDichiarazioneDiSpesa, String note, Long idUtente, String idIride,
			Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::salvaRilievoDS] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; note = " + note + "; idIride = "
				+ idIride + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = "
				+ codiceRuolo);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (StringUtils.isBlank(note)) {
			throw new InvalidParameterException("note non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(Boolean.FALSE);
		try {
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			pbandiTDichiarazioneSpesaVO.setRilievoContabile(note);
			pbandiTDichiarazioneSpesaVO.setDtRilievoContabile(new java.sql.Date((new java.util.Date()).getTime()));
			genericDAO.update(pbandiTDichiarazioneSpesaVO);

			esito.setEsito(Boolean.TRUE);
			esito.setMessaggio("Rilievo contabile salvato con successo.");
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante il salvataggio del rilievo della DS: ", e);
			throw new Exception("Errore durante il salvataggio del rilievo della DS.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public EsitoDTO setNullaDaRilevare(Long idDichiarazioneDiSpesa, Long idProgetto, List<Long> idDocumentiConRilievo,
			Long idUtente, String idIride, Long idSoggetto, String codiceRuolo)
			throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::setNullaDaRilevare] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idProgetto = " + idProgetto
				+ "; idDocumentiConRilievo = " + idDocumentiConRilievo.toString() + "; idIride = " + idIride
				+ "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(Boolean.FALSE);
		try {
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			pbandiTDichiarazioneSpesaVO.setRilievoContabile(null);
			pbandiTDichiarazioneSpesaVO.setDtRilievoContabile(null);
			pbandiTDichiarazioneSpesaVO.setDtChiusuraRilievo(new java.sql.Date((new java.util.Date()).getTime()));
			genericDAO.updateNullables(pbandiTDichiarazioneSpesaVO);

			// cancello rilievi associati ai documenti
			for (Long idDocSpesa : idDocumentiConRilievo) {
				PbandiRDocSpesaProgettoVO pbandiRDocSpesaProgettoVO = new PbandiRDocSpesaProgettoVO();
				pbandiRDocSpesaProgettoVO.setIdProgetto(new BigDecimal(idProgetto.longValue()));
				pbandiRDocSpesaProgettoVO.setIdDocumentoDiSpesa(new BigDecimal(idDocSpesa.longValue()));
				pbandiRDocSpesaProgettoVO = genericDAO.findSingleWhere(pbandiRDocSpesaProgettoVO);

				pbandiRDocSpesaProgettoVO.setRilievoContabile(null);
				pbandiRDocSpesaProgettoVO.setDtRilievoContabile(null);
				genericDAO.updateNullables(pbandiRDocSpesaProgettoVO);
			}

			// invio mail all'istruttore / agli istruttori

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dtDichiarazione = dateFormat.format(pbandiTDichiarazioneSpesaVO.getDtDichiarazione());
			String dtOggi = dateFormat.format(new java.util.Date());

			String subject = "Chiusura dei rilievi della Dichiarazione di Spesa " + idDichiarazioneDiSpesa + " del "
					+ dtDichiarazione;
			String content = "Si notifica che in data " + dtOggi
					+ " l'istruttore competente ha chiuso i rilievi della dichiarazione di spesa numero "
					+ idDichiarazioneDiSpesa + " del " + dtDichiarazione;

			List<String> descBreviTipoAnag = new ArrayList<String>();
			descBreviTipoAnag.add(Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_ADG_ISTRUTTORE);
			descBreviTipoAnag.add(Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_OI_ISTRUTTORE);
			List<String> mailIstruttori = leggiIndirizzoMailIstruttori(idProgetto, descBreviTipoAnag);
			invioNotifica(subject, content, mailIstruttori);

			esito.setEsito(Boolean.TRUE);
			esito.setMessaggio("Operazione confermata con successo.");
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante setNullaDaRilevare della DS: ", e);
			throw new Exception("Errore durante setNullaDaRilevare della DS.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public EsitoDTO chiudiRilievi(Long idDichiarazioneDiSpesa, Long idUtente, String idIride, Long idSoggetto,
			String codiceRuolo) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::chiudiRilievi] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idIride = " + idIride + "; idUtente = "
				+ idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(Boolean.FALSE);
		try {
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			pbandiTDichiarazioneSpesaVO.setDtChiusuraRilievo(new java.sql.Date((new java.util.Date()).getTime()));
			genericDAO.update(pbandiTDichiarazioneSpesaVO);

			// invio mail all'istruttore / agli istruttori

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dtDichiarazione = dateFormat.format(pbandiTDichiarazioneSpesaVO.getDtDichiarazione());
			String dtOggi = dateFormat.format(new java.util.Date());

			String subject = "Chiusura dei rilievi della Dichiarazione di Spesa " + idDichiarazioneDiSpesa + " del "
					+ dtDichiarazione;
			String content = "Si notifica che in data " + dtOggi
					+ " l'istruttore competente ha chiuso i rilievi della dichiarazione di spesa numero "
					+ idDichiarazioneDiSpesa + " del " + dtDichiarazione;

			List<String> descBreviTipoAnag = new ArrayList<String>();
			descBreviTipoAnag.add(Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_ADG_ISTRUTTORE);
			descBreviTipoAnag.add(Constants.DESCRIZIONE_BREVE_TIPO_ANAGRAFICA_OI_ISTRUTTORE);
			List<String> mailIstruttori = leggiIndirizzoMailIstruttori(
					new Long(pbandiTDichiarazioneSpesaVO.getIdProgetto().longValue()), descBreviTipoAnag);
			invioNotifica(subject, content, mailIstruttori);

			esito.setEsito(Boolean.TRUE);
			esito.setMessaggio("Rilievi chiusi con successo.");
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante il chiudi rilievi della DS: ", e);
			throw new Exception("Errore durante il chiudi rilievi della DS.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	private void invioNotifica(String subject, String content, List<String> mails) throws Exception {
		LOG.info("\n\n\n INVIO NOTIFICA \n\n\n");

		String[] valueToCheck = { "idProgetto", "subject", "content", "descBreviTipoAnag" };
		ValidatorInput.verifyNullValue(valueToCheck);

		try {
			MailVO mailVO = new MailVO();

			mailVO.setSubject(subject);
			mailVO.setFromAddress(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.MAIL_ADDRESS_NO_REPLY_CSI_IT);

			List<String> mailIstruttori = mails;
			mailVO.setToAddresses(mailIstruttori);

			mailVO.setContent(content);

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
	}

	private List<String> leggiIndirizzoMailIstruttori(Long idProgetto, List<String> descBreviTipoAnag)
			throws Exception {
		String prf = "[SpesaValidataDAOImpl::leggiIndirizzoMailIstruttori] ";
		LOG.info(prf + " BEGIN");
		if (idProgetto == null)
			throw new Exception("idProgetto non valorizzato");
		if (descBreviTipoAnag == null || descBreviTipoAnag.size() == 0) {
			throw new Exception("descBreviTipoAnag non valorizzato");
		}

		List<String> mails = new ArrayList<String>();

		// cerco le mail degli istruttori associati al progetto
		String query = "SELECT DISTINCT(r.EMAIL)\r\n" + "FROM PBANDI_R_SOGGETTO_PROGETTO sp\r\n"
				+ "JOIN PBANDI_D_TIPO_ANAGRAFICA ta ON ta.ID_TIPO_ANAGRAFICA = sp.ID_TIPO_ANAGRAFICA \r\n"
				+ "JOIN PBANDI_R_PERSONA_FISICA_RECAP pfr ON pfr.ID_PERSONA_FISICA = sp.ID_PERSONA_FISICA\r\n"
				+ "JOIN PBANDI_T_RECAPITI r ON r.ID_RECAPITI = pfr.ID_RECAPITI \r\n"
				+ "WHERE sp.ID_PROGETTO = :idProgetto AND ta.DESC_BREVE_TIPO_ANAGRAFICA IN (";
		for (String s : descBreviTipoAnag) {
			query += "?,";
		}
		query = query.substring(0, query.length() - 1);
		query += ")";

		Object[] param = new Object[descBreviTipoAnag.size() + 1];
		param[0] = idProgetto;
		int j = 0;
		for (int i = 1; i < descBreviTipoAnag.size(); i++) {
			param[i] = descBreviTipoAnag.get(j);
			j++;
		}

		LOG.info(prf + " <idProgetto>: " + idProgetto + ", <descBreviTipoAnag>: " + descBreviTipoAnag.toString());
		LOG.info(query);
		List<String> emailIstrProg = getJdbcTemplate().queryForList(query, param, String.class);

		if (emailIstrProg != null && emailIstrProg.size() > 0) {
			LOG.info(prf + " mail istruttori associati al progetto trovate: " + String.join(",", emailIstrProg));
		} else {
			LOG.info(prf + " nessuna mail per gli istruttori associati al progetto trovata.");
		}
		// cerco le mail degli istruttori associati al bando
		query = "SELECT DISTINCT(r.EMAIL)\r\n" + "FROM PBANDI_R_SOGG_BANDO_LIN_INT sbli\r\n"
				+ "JOIN PBANDI_T_DOMANDA d ON d.PROGR_BANDO_LINEA_INTERVENTO = sbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
				+ "JOIN PBANDI_T_PROGETTO p ON p.ID_DOMANDA = d.ID_DOMANDA\r\n"
				+ "JOIN PBANDI_D_TIPO_ANAGRAFICA ta ON ta.ID_TIPO_ANAGRAFICA = sbli.ID_TIPO_ANAGRAFICA \r\n"
				+ "JOIN PBANDI_T_PERSONA_FISICA pf ON pf.ID_SOGGETTO = sbli.ID_SOGGETTO\r\n"
				+ "JOIN PBANDI_R_PERSONA_FISICA_RECAP pfr ON pfr.ID_PERSONA_FISICA = pf.ID_PERSONA_FISICA\r\n"
				+ "JOIN PBANDI_T_RECAPITI r ON r.ID_RECAPITI = pfr.ID_RECAPITI \r\n"
				+ "WHERE p.ID_PROGETTO = ? AND ta.DESC_BREVE_TIPO_ANAGRAFICA IN (";
		for (String s : descBreviTipoAnag) {
			query += "?,";
		}
		query = query.substring(0, query.length() - 1);
		query += ")";
		LOG.info(prf + " <idProgetto>: " + idProgetto + ", <descBreviTipoAnag>: " + descBreviTipoAnag.toString());
		LOG.info(query);
		List<String> emailIstrBando = getJdbcTemplate().queryForList(query, param, String.class);

		if (emailIstrBando != null && emailIstrBando.size() > 0) {
			LOG.info(prf + " mail istruttori associati al bando trovate: " + String.join(",", emailIstrBando));
		} else {
			LOG.info(prf + " nessuna mail per gli istruttori associati al bando trovata.");
		}
		if ((emailIstrProg == null || emailIstrProg.size() == 0)
				&& (emailIstrBando == null || emailIstrBando.size() == 0)) {
			LOG.error("Nessuna mail trovata per gli istruttori associati al bando e/o al progetto.");
		}

		for (String s : emailIstrProg) {
			if (!mails.contains(s)) {
				mails.add(s);
			}
		}
		for (String s : emailIstrBando) {
			if (!mails.contains(s)) {
				mails.add(s);
			}
		}

		LOG.info(prf + " END");
		return mails;
	}

	@Override
	public RilievoDocSpesaDTO getRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, Long idDichiarazioneDiSpesa,
			Long idUtente, String idIride, Long idSoggetto, String codiceRuolo)
			throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::getRilievoDocSpesa] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto
				+ "; idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idUtente = " + idUtente
				+ "; idSoggetto = " + idSoggetto + "; idIride = " + idIride + "; codiceRuolo = " + codiceRuolo);

		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		RilievoDocSpesaDTO rilievoDocSpesaDTO = new RilievoDocSpesaDTO();
		try {
			PbandiRDocSpesaProgettoVO pbandiRDocSpesaProgettoVO = new PbandiRDocSpesaProgettoVO();
			pbandiRDocSpesaProgettoVO.setIdProgetto(new BigDecimal(idProgetto.longValue()));
			pbandiRDocSpesaProgettoVO.setIdDocumentoDiSpesa(new BigDecimal(idDocumentoDiSpesa.longValue()));
			pbandiRDocSpesaProgettoVO = genericDAO.findSingleWhere(pbandiRDocSpesaProgettoVO);

			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			rilievoDocSpesaDTO.setRilievoContabile(pbandiRDocSpesaProgettoVO.getRilievoContabile());
			rilievoDocSpesaDTO.setDtRilievoContabile(pbandiRDocSpesaProgettoVO.getDtRilievoContabile());
			rilievoDocSpesaDTO.setDtChiusuraRilievo(pbandiTDichiarazioneSpesaVO.getDtChiusuraRilievo());
			rilievoDocSpesaDTO.setDtConfermaValidita(pbandiTDichiarazioneSpesaVO.getDtConfermaValidita());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante il caricamento del rilievo del documento di spesa: ", e);
			throw new Exception("Errore durante il caricamento del rilievo del documento di spesa.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return rilievoDocSpesaDTO;
	}

	@Override
	public EsitoDTO salvaRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, String note, Long idUtente,
			String idIride, Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::salvaRilievoDocSpesa] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; note = "
				+ note + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto + "; idIride = " + idIride
				+ "; codiceRuolo = " + codiceRuolo);

		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (StringUtils.isBlank(note)) {
			throw new InvalidParameterException("note non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(Boolean.FALSE);
		try {
			PbandiRDocSpesaProgettoVO pbandiRDocSpesaProgettoVO = new PbandiRDocSpesaProgettoVO();
			pbandiRDocSpesaProgettoVO.setIdProgetto(new BigDecimal(idProgetto.longValue()));
			pbandiRDocSpesaProgettoVO.setIdDocumentoDiSpesa(new BigDecimal(idDocumentoDiSpesa.longValue()));
			pbandiRDocSpesaProgettoVO = genericDAO.findSingleWhere(pbandiRDocSpesaProgettoVO);

			pbandiRDocSpesaProgettoVO.setRilievoContabile(note);
			pbandiRDocSpesaProgettoVO.setDtRilievoContabile(new java.sql.Date((new java.util.Date()).getTime()));
			genericDAO.update(pbandiRDocSpesaProgettoVO);

			esito.setEsito(Boolean.TRUE);
			esito.setMessaggio("Rilievo salvato con successo.");
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante il salvataggio del rilievo del documento di spesa: ", e);
			throw new Exception("Errore durante il salvataggio del rilievo del documento di spesa.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public EsitoDTO deleteRilievoDS(Long idDichiarazioneDiSpesa, Long idUtente, String idIride, Long idSoggetto,
			String codiceRuolo) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::deleteRilievoDS] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idIride = " + idIride + "; idUtente = "
				+ idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(Boolean.FALSE);
		try {
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			pbandiTDichiarazioneSpesaVO.setRilievoContabile(null);
			pbandiTDichiarazioneSpesaVO.setDtRilievoContabile(null);
			genericDAO.updateNullables(pbandiTDichiarazioneSpesaVO);

			esito.setEsito(Boolean.TRUE);
			esito.setMessaggio("Rilievo cancellato con successo.");
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante l'eliminazione del rilievo del documento di spesa: ", e);
			throw new Exception("Errore durante l'eliminazione del rilievo del documento di spesa.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public EsitoDTO deleteRilievoDocSpesa(Long idDocumentoDiSpesa, Long idProgetto, Long idUtente, String idIride,
			Long idSoggetto, String codiceRuolo) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::deleteRilievoDocSpesa] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto + "; idUtente = "
				+ idUtente + "; idSoggetto = " + idSoggetto + "; idIride = " + idIride + "; codiceRuolo = "
				+ codiceRuolo);

		if (idDocumentoDiSpesa == null) {
			throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(Boolean.FALSE);
		esito.setMessaggio("Errore Errore durante l'eliminazione del rilievo.");

		try {
			PbandiRDocSpesaProgettoVO pbandiRDocSpesaProgettoVO = new PbandiRDocSpesaProgettoVO();
			pbandiRDocSpesaProgettoVO.setIdProgetto(new BigDecimal(idProgetto.longValue()));
			pbandiRDocSpesaProgettoVO.setIdDocumentoDiSpesa(new BigDecimal(idDocumentoDiSpesa.longValue()));
			pbandiRDocSpesaProgettoVO = genericDAO.findSingleWhere(pbandiRDocSpesaProgettoVO);

			pbandiRDocSpesaProgettoVO.setRilievoContabile(null);
			pbandiRDocSpesaProgettoVO.setDtRilievoContabile(null);
			genericDAO.updateNullables(pbandiRDocSpesaProgettoVO);

			esito.setEsito(Boolean.TRUE);
			esito.setMessaggio("Rilievo eliminato con successo.");
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante l'eliminazione del rilievo del documento di spesa: ", e);
			throw new Exception("Errore durante l'eliminazione del rilievo del documento di spesa.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public EsitoDTO confermaValiditaRilievi(Long idDichiarazioneDiSpesa, Long idUtente, String idIride, Long idSoggetto,
			String codiceRuolo) throws InvalidParameterException, Exception {
		String prf = "[SpesaValidataDAOImpl::confermaValiditaRilievi] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idIride = " + idIride + "; idUtente = "
				+ idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		EsitoDTO esito = new EsitoDTO();
		esito.setEsito(Boolean.FALSE);
		try {
			PbandiTDichiarazioneSpesaVO pbandiTDichiarazioneSpesaVO = new PbandiTDichiarazioneSpesaVO();
			pbandiTDichiarazioneSpesaVO.setIdDichiarazioneSpesa(new BigDecimal(idDichiarazioneDiSpesa.longValue()));
			pbandiTDichiarazioneSpesaVO = genericDAO.findSingleWhere(pbandiTDichiarazioneSpesaVO);

			pbandiTDichiarazioneSpesaVO.setDtConfermaValidita(new java.sql.Date((new java.util.Date()).getTime()));
			genericDAO.update(pbandiTDichiarazioneSpesaVO);

			// invio mail alla ragioneria delegata

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String dtDichiarazione = dateFormat.format(pbandiTDichiarazioneSpesaVO.getDtDichiarazione());
			String dtOggi = dateFormat.format(new java.util.Date());

			String subject = "Conferma validità della Dichiarazione di Spesa " + idDichiarazioneDiSpesa + " del "
					+ dtDichiarazione;
			String content = "Si avvisa che il beneficiario ha completato le integrazioni e in data " + dtOggi
					+ ", a seguito di un ulteriore controllo, si conferma la validità della rettifica della spesa validata della dichiarazione di spesa numero "
					+ idDichiarazioneDiSpesa + " del " + dtDichiarazione;
			List<String> mailRagioneriaDelegata = null;
			try {
				mailRagioneriaDelegata = validazioneRendicontazioneDAO.leggiIndirizzoMailRuoloEnteProgetto(
						new Long(pbandiTDichiarazioneSpesaVO.getIdProgetto().longValue()),
						Constants.DESC_BREVE_RUOLO_ENTE_COMP_RAGIONERIA_DELEGATA);

			} catch (Exception e) {
				LOG.error("ERRORE nel recuperare l'indirizzo email della ragioneria delegata", e);
				throw e;
			}
			if (mailRagioneriaDelegata != null) {
				invioNotifica(subject, content, mailRagioneriaDelegata);
			}
			esito.setEsito(Boolean.TRUE);
			esito.setMessaggio("Validità dei rilievo confermata con successo.");
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la conferma di validità dei rilievi: ", e);
			throw new Exception("Errore durante la conferma di validità dei rilievi.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	/*
	 * // Ex RettificaBusinessImpl.findRettifiche() private
	 * ArrayList<RettificaVoceItem> findRettifiche(Long idUtente, String idIride,
	 * Long idDocumentoDiSpesa, Long idProgetto, Long idBandoLinea)throws Exception
	 * { String prf = "[SpesaValidataDAOImpl::findRettifiche] "; LOG.info(prf +
	 * "idDocumentoDiSpesa = "+idDocumentoDiSpesa+"; idProgetto = "
	 * +idBandoLinea+"; idProgetto = "+idBandoLinea+"; idUtente = "+idUtente);
	 * 
	 * it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO filtro = new
	 * it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO();
	 * filtro.setIdDocumentoDiSpesa(idDocumentoDiSpesa);
	 * filtro.setIdProgetto(idProgetto);
	 * it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO[] result; result =
	 * rettificaBusinessImpl.findRettifiche(idUtente, idIride, filtro);
	 * ArrayList<RettificaVoceItem> rettifiche = new ArrayList<RettificaVoceItem>();
	 * 
	 * // BR04 - Regola data valuta pagamento boolean isBR04 = false; //if
	 * (profilazioneSrv.isRegolaApplicabileForBandoLinea(user.getIdUtente(),
	 * user.getIdIride(), idBando,RegoleConstants.BR04_VISUALIZZA_DATA_VALUTA )){ if
	 * (profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
	 * idBandoLinea, RegoleConstants.BR04_VISUALIZZA_DATA_VALUTA)) { isBR04 = true;
	 * }
	 * 
	 * // BR05 - Regola data pagamento boolean isBR05 = false; //if
	 * (profilazioneSrv.isRegolaApplicabileForBandoLinea(user.getIdUtente(),
	 * user.getIdIride(), idBando,RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA )){
	 * if (profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente,
	 * idIride, idBandoLinea, RegoleConstants.BR05_VISUALIZZA_DATA_EFFETTIVA)) {
	 * isBR05 = true; }
	 * 
	 * if (result != null) { Long progrQuota = -1L; for
	 * (it.csi.pbandi.pbweb.pbandisrv.dto.rettifica.RettificaDTO dto : result) {
	 * 
	 * if (NumberUtil.compare(dto.getProgrPagQuotParteDocSp(), progrQuota) != 0) {
	 * progrQuota = dto.getProgrPagQuotParteDocSp(); RettificaVoceItem rigaVoce =
	 * new RettificaVoceItem();
	 * rigaVoce.setDescVoceDiSpesa(dto.getDescVoceDiSpesa());
	 * rigaVoce.setRigaVoce(Boolean.TRUE);
	 * rigaVoce.setProgrPagQuotParteDocSp(dto.getProgrPagQuotParteDocSp());
	 * rigaVoce.setImportoPagamento(dto.getImportoPagamento());
	 * rigaVoce.setModalitaPagamento(dto.getDescModalitaPagamento());
	 * 
	 * rigaVoce.setDtValutaVisible(isBR04); if (isBR04) {
	 * rigaVoce.setDtValuta(DateUtil.formatDate(dto.getDtValuta())); }
	 * 
	 * rigaVoce.setDtPagamentoVisible(isBR05); if (isBR05) {
	 * rigaVoce.setDtPagamento(DateUtil.formatDate(dto.getDtPagamento())); }
	 * rettifiche.add(rigaVoce); } RettificaVoceItem rigaRettifica = new
	 * RettificaVoceItem();
	 * rigaRettifica.setCodiceFiscaleSoggetto(dto.getCodiceFiscaleSoggetto());
	 * rigaRettifica.setDtRettifica(DateUtil.formatDate(dto.getDtRettifica()));
	 * rigaRettifica.setImportoRettifica(dto.getImportoRettifica());
	 * rigaRettifica.setRiferimento(dto.getRiferimento());
	 * rigaRettifica.setProgrPagQuotParteDocSp(dto.getProgrPagQuotParteDocSp());
	 * rigaRettifica.setRigaVoce(Boolean.FALSE);
	 * rigaRettifica.setDtValutaVisible(isBR04);
	 * rigaRettifica.setDtPagamentoVisible(isBR05); rettifiche.add(rigaRettifica); }
	 * } return rettifiche; }
	 */
}
