/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;

import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbweb.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.business.manager.InizializzazioneManager;
import it.csi.pbandi.pbweb.dto.CodiceDescrizioneDTO;
import it.csi.pbandi.pbweb.dto.DatiIntegrazioneDsDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.LongValue;
import it.csi.pbandi.pbweb.dto.ProgettoBeneficiarioDTO;
import it.csi.pbandi.pbweb.dto.StringValue;
import it.csi.pbandi.pbweb.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.AllegatiDichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.DocumentoIndexDTO;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.FiltroRicercaDocumentiDTO;
import it.csi.pbandi.pbweb.dto.documentiDiProgetto.InizializzaDocumentiDiProgettoDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.DocumentiDiProgettoDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.vo.EnteProgettoDomandaVO;
import it.csi.pbandi.pbweb.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.FileUtil;
import it.csi.pbandi.pbweb.util.NumberUtil;

@Service
public class DocumentiDiProgettoDAOImpl extends JdbcDaoSupport implements DocumentiDiProgettoDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public DocumentiDiProgettoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	BeanUtil beanUtil;

	@Autowired
	ProfilazioneDAO profilazioneDao;

	@Autowired
	DecodificheDAOImpl decodificheDAOImpl;

	@Autowired
	DocumentoManager documentoManager;

	@Autowired
	InizializzazioneManager inizializzazioneManager;

	// da togliere
	@Autowired
	ValidazioneRendicontazioneDAOImpl validazioneRendicontazioneDAO;

	@Autowired
	private ProfilazioneBusinessImpl profilazioneSrv;

	@Autowired
	private it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl profilazioneBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentazione.GestioneDocumentazioneBusinessImpl gestioneDocumentazioneBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione.ValidazioneRendicontazioneBusinessImpl validazioneRendicontazioneBusinessImpl;

	@Autowired
	protected it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;

	@Autowired
	it.csi.pbandi.pbweb.pbandisrv.business.checklist.CheckListBusinessImpl checkListBusinessImpl;

	@Override
	public InizializzaDocumentiDiProgettoDTO inizializzaDocumentiDiProgetto(String codiceRuolo, Long idSoggetto,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::inizializzaDocumentiDiProgetto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "codiceRuolo = " + codiceRuolo + "; idSoggetto = " + idSoggetto + "; idUtente = " + idUtente);

		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		InizializzaDocumentiDiProgettoDTO result = new InizializzaDocumentiDiProgettoDTO();
		try {

			// Combo beneficiari.
			// creato un metodo apposito
			// result.setComboBeneficiari(this.comboBeneficiari(codiceRuolo, idSoggetto,
			// idUtente, idIride));

			// Combo tipi documenti di spesa.
			result.setComboTipiDocumentoIndex(this.comboTipiDocumentoIndex(codiceRuolo, idSoggetto, idUtente, idIride));

			// Compbo tipi documenti index nella popup Upload.
			result.setComboTipiDocumentoIndexUploadable(this.comboTipiDocumentoIndexUploadable(idUtente, idIride));

			// Lista di check Visibilita nella popup Upload.
			result.setCategorieAnagrafica(this.elencoVisibilita(idUtente, idIride));

			result.setDimMaxSingoloFile(
					new Long(decodificheDAOImpl.costante(Constants.COSTANTE_ARCHIVIO_FILE_SIZE_LIMIT, true)));

			String archivioAllowedFileExtensions = decodificheDAOImpl
					.costante(Constants.COSTANTE_ARCHIVIO_ALLOWED_FILE_EXT, true);
			StringTokenizer strtkz = new StringTokenizer(archivioAllowedFileExtensions, ",");
			ArrayList<String> allowedExtensions = new ArrayList<String>();
			while (strtkz.hasMoreElements()) {
				allowedExtensions.add(strtkz.nextToken());
			}
			result.setEstensioniConsentite(allowedExtensions);

//            LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la inizializzazione di documenti di progetto: ", e);
			throw new DaoException("Errore durante la inizializzazione di documenti di progetto.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public List<CodiceDescrizioneDTO> getBeneficiariDocProgettoByDenomOrIdBen(String denominazione, Long idBeneficiario,
			String codiceRuolo, Long idSoggetto, Long idUtente, String idIride) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::getBeneficiariDocProgettoByDenom] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "denominazione = " + denominazione + "codiceRuolo = " + codiceRuolo + "; idSoggetto = "
				+ idSoggetto + "; idUtente = " + idUtente);

		if (denominazione == null) {
			throw new InvalidParameterException("denominazione non valorizzata.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		List<CodiceDescrizioneDTO> result = new ArrayList<>();
		try {
			result = this.comboBeneficiari(codiceRuolo, idSoggetto, idUtente, idIride, denominazione, idBeneficiario);
		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante il caricamento dei beneficiari di documenti di progetto: ", e);
			throw new DaoException("Errore durante il caricamento dei beneficiari di documenti di progetto.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex: GestioneDocumentazioneBusinessImpl.findProgetti()
	public ArrayList<ProgettoBeneficiarioDTO> progettiBeneficiario(String codiceRuolo, Long idSoggettoBeneficiario,
			Long idSoggetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::progettiBeneficiario] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "codiceRuolo = " + codiceRuolo + "; idSoggettoBeneficiario = " + idSoggettoBeneficiario
				+ "; idSoggetto = " + idSoggetto + "; idUtente = " + idUtente);

		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idSoggettoBeneficiario == null) {
			throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		ArrayList<ProgettoBeneficiarioDTO> result = new ArrayList<ProgettoBeneficiarioDTO>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro = new it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.FiltroBeneficiarioProgettoSoggettoRuoloDTO();
			filtro.setIdSoggetto(idSoggetto);
			filtro.setCodiceRuolo(codiceRuolo);
			filtro.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
			// filtro.setIdBando(NumberUtil.toNullableLong(model.getAppDatabanSelezionato()));

			it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.ProgettoDTO[] progetti;
			progetti = profilazioneBusinessImpl.findProgettiByBeneficiarioSoggettoRuolo(idUtente, idIride, filtro);

			for (it.csi.pbandi.pbweb.pbandisrv.dto.profilazione.ProgettoDTO dto : progetti) {
				ProgettoBeneficiarioDTO progetto = new ProgettoBeneficiarioDTO();
				progetto.setCodice(NumberUtil.getStringValue(dto.getIdProgetto()));
				progetto.setDescrizione(dto.getCodiceVisualizzatoProgetto());
				progetto.setNumeroDomanda(dto.getNumeroDomanda());
				result.add(progetto);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca dei progetti: ", e);
			throw new DaoException("Errore durante la ricerca dei progetti.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	// Ex: GestioneDocumentazioneBusinessImpl.findBeneficiari()
	private ArrayList<CodiceDescrizioneDTO> comboBeneficiari(String codiceRuolo, Long idSoggetto, Long idUtente,
			String idIride, String denominazione, Long idBeneficiario) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::comboBeneficiari] ";
		LOG.info(prf + " BEGIN");

		ArrayList<CodiceDescrizioneDTO> result = new ArrayList<CodiceDescrizioneDTO>();
		try {

			it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.FiltroBeneficiarioProgettoSoggettoRuoloDTO filtro = new it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.FiltroBeneficiarioProgettoSoggettoRuoloDTO();
			filtro.setIdSoggetto(idSoggetto);
			filtro.setCodiceRuolo(codiceRuolo);
			filtro.setIdSoggettoBeneficiario(idBeneficiario);

			it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.Beneficiario[] beneficiari;
			beneficiari = profilazioneSrv.findBeneficiariByProgettoSoggettoRuoloDenominazioneOrIdBen(idUtente, idIride,
					filtro, denominazione);

			for (it.csi.pbandi.pbservizit.pbandisrv.dto.profilazione.Beneficiario dto : beneficiari) {
				CodiceDescrizioneDTO beneficiario = new CodiceDescrizioneDTO();
				beneficiario.setCodice(NumberUtil.getStringValue(dto.getId_soggetto()));
				beneficiario.setDescrizione(dto.getDescrizione() + " - " + dto.getCodiceFiscale());
				result.add(beneficiario);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca dei beneficiari: ", e);
			throw new Exception("Errore durante la ricerca dei dei beneficiari.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	private ArrayList<CodiceDescrizioneDTO> comboTipiDocumentoIndexUploadable(Long idUtente, String idIride)
			throws Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::comboTipiDocumentoIndexUploadable] ";
		LOG.info(prf + " BEGIN");
		ArrayList<CodiceDescrizioneDTO> result = new ArrayList<CodiceDescrizioneDTO>();
		try {

			List<DecodificaDTO> tipiDocumentoIndex = decodificheDAOImpl.tipiDocumentoIndexUploadable();

			for (DecodificaDTO dto : tipiDocumentoIndex) {
				CodiceDescrizioneDTO tipoDocumentoIndex = new CodiceDescrizioneDTO();
				tipoDocumentoIndex.setCodice(dto.getId().toString());
				tipoDocumentoIndex.setDescrizione(dto.getDescrizione());
				result.add(tipoDocumentoIndex);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca dei tipi documento index uploadable: ", e);
			throw new Exception("Errore durante la ricerca dei dei tipi documento index uploadable.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	private ArrayList<CodiceDescrizioneDTO> elencoVisibilita(Long idUtente, String idIride) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::elencoVisibilita] ";
		LOG.info(prf + " BEGIN");
		ArrayList<CodiceDescrizioneDTO> result = new ArrayList<CodiceDescrizioneDTO>();
		try {

			List<DecodificaDTO> tipiDocumentoIndex = decodificheDAOImpl.categorieAnagrafica();

			for (DecodificaDTO dto : tipiDocumentoIndex) {
				CodiceDescrizioneDTO tipoDocumentoIndex = new CodiceDescrizioneDTO();
				tipoDocumentoIndex.setCodice(dto.getId().toString());
				tipoDocumentoIndex.setDescrizione(dto.getDescrizione());
				result.add(tipoDocumentoIndex);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca delle visibilita: ", e);
			throw new Exception("Errore durante la ricerca dei delle visibilita.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	// Ex: GestioneDocumentazioneBusinessImpl.findTipiDocumentoIndex()
	private ArrayList<CodiceDescrizioneDTO> comboTipiDocumentoIndex(String codiceRuolo, Long idSoggetto, Long idUtente,
			String idIride) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::comboTipiDocumentoIndex] ";
		LOG.info(prf + " BEGIN");
		ArrayList<CodiceDescrizioneDTO> result = new ArrayList<CodiceDescrizioneDTO>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.FiltroTipoDocumentoIndexDTO filtro = new it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.FiltroTipoDocumentoIndexDTO();
			filtro.setIdSoggetto(idSoggetto);
			filtro.setCodiceRuolo(codiceRuolo);
			// filtro.setIdProgetto(NumberUtil.toNullableLong(model.getAppDataprogSelezionato()));

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.TipoDocumentoIndexDTO[] tipiDocumentoIndex;
			tipiDocumentoIndex = gestioneDocumentazioneBusinessImpl.findTipiDocumentoIndex(idUtente, idIride, filtro);

			for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.TipoDocumentoIndexDTO dto : tipiDocumentoIndex) {
				CodiceDescrizioneDTO tipoDocumentoIndex = new CodiceDescrizioneDTO();
				tipoDocumentoIndex.setCodice(NumberUtil.getStringValue(dto.getIdTipoDocumentoIndex()));
				tipoDocumentoIndex.setDescrizione(dto.getDescTipoDocIndex());
				result.add(tipoDocumentoIndex);
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca dei tipi documento index: ", e);
			throw new Exception("Errore durante la ricerca dei dei tipi documento index.");
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex: GestioneDocumentazioneBusinessImpl.findDocumenti()
	public ArrayList<DocumentoIndexDTO> cercaDocumenti(FiltroRicercaDocumentiDTO filtroRicercaDocumentiDTO,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::cercaDocumenti] ";
		LOG.info(prf + " BEGIN");
		// LOG.info(prf + "codiceRuolo = "+codiceRuolo+"; idSoggettoBeneficiario =
		// "+idSoggettoBeneficiario+"; idSoggetto = "+idSoggetto+"; idUtente =
		// "+idUtente);

		if (filtroRicercaDocumentiDTO == null) {
			throw new InvalidParameterException("filtroRicercaDocumentiDTO non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		LOG.info(prf + "filtro= " + filtroRicercaDocumentiDTO.toString());

		ArrayList<DocumentoIndexDTO> result = new ArrayList<DocumentoIndexDTO>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.RicercaDocumentazioneDTO filtro;
			filtro = beanUtil.transform(filtroRicercaDocumentiDTO,
					it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.RicercaDocumentazioneDTO.class);

			if (filtroRicercaDocumentiDTO.getDocInFirma())
				filtro.setDocInFirma("true");
			else
				filtro.setDocInFirma("false");

			if (filtroRicercaDocumentiDTO.getDocInviati())
				filtro.setDocInviati("true");
			else
				filtro.setDocInviati("false");

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO[] documentiDTO;
			documentiDTO = gestioneDocumentazioneBusinessImpl.ricercaDocumenti(idUtente, idIride, filtro);

//			int i=0;
			if (documentiDTO != null) {
				LOG.info(prf + "Documenti found: " + documentiDTO.length);
				for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO dto : documentiDTO) {

					LOG.info(prf + "DocumentoDTO = " + dto);

					DocumentoIndexDTO doc = new DocumentoIndexDTO();
					doc.setBeneficiario(dto.getBeneficiario());
					doc.setCodStatoDoc(dto.getCodStatoDoc());
					doc.setCodTipoDoc(dto.getCodTipoDoc());
					doc.setCodiceProgettoVisualizzato(dto.getCodiceVisualizzato());
					doc.setDescTipoDocumento(dto.getDescTipoDocIndex());
					doc.setDescErrore(dto.getDescErrore());
					doc.setDtCreazione(DateUtil.getDate(dto.getDtCreazione()));
					doc.setDtSign(DateUtil.getDate(dto.getDtSign()));
					doc.setDtTimestamp(DateUtil.getDate(dto.getDtTimestamp()));
					doc.setDocumento(dto.getNomeFile());
					doc.setIdDocumentoIndex(String.valueOf(dto.getIdDocumentoIndex()));
					doc.setIdProgetto(String.valueOf(dto.getIdProgetto()));
					doc.setIdBandoLinea(findIdBandoLineaByIdProgetto(doc.getIdProgetto()));
					if (doc.getIdBandoLinea() != null)
						doc.setIsBR50(profilazioneSrv.isRegolaApplicabileForBandoLinea(idUtente, idIride,
								Long.decode(doc.getIdBandoLinea()), "BR50"));
					doc.setNote(dto.getNoteDocumentoIndex());
					if (dto.getFlagFirmaCartacea() != null && dto.getFlagFirmaCartacea().equals("S")) {
						doc.setFlagFirmaCartacea(true);
					}
					if (dto.getFlagTrasmDest() != null && dto.getFlagTrasmDest().equals("S")) {
						doc.setFlagTrasmDest(true);
					}
					if (dto.getSignable() != null && dto.getSignable()) {
						doc.setProtocollo(dto.getProtocollo());
					} else {
						doc.setProtocollo("n.a.");
					}
					doc.setSignable(dto.getSignable());
					doc.setSigned(dto.getSigned());
					doc.setSignValid(dto.getSignValid());
					doc.setTimeStamped(dto.getTimeStamped());

					if (dto.getIdCategAnagraficaMitt() != null) {
						doc.setIdCategAnagraficaMitt(dto.getIdCategAnagraficaMitt().toString());
						doc.setDescCategAnagraficaMitt(dto.getDescCategAnagraficaMitt());
					}

					// Campi aggiunti a DocumentoDTO per visualizzare gli allegati alle
					// integrazioniDS
					// in Gestione Documenti.
					if (dto.getIdEntita() != null) {
						doc.setIdEntita(NumberUtil.getStringValue(dto.getIdEntita()));
					}
					if (dto.getIdTarget() != null) {
						doc.setIdTarget(NumberUtil.getStringValue(dto.getIdTarget()));
					}
					doc.setTipoInvioDs(dto.getTipoInvioDs());

					doc.setDimMaxFileFirmato(
							new Long(decodificheDAOImpl.costante(Constants.COSTANTE_DIGITAL_SIGN_SIZE_LIMIT, true)));

					String flagFirmaAutografa = findFlagFirmaAutografa(dto.getIdDocumentoIndex().toString());
					doc.setFlagFirmaAutografa(flagFirmaAutografa != null && flagFirmaAutografa.equals("S"));

					// PK : individuo i doc linkati da ACTA e per quelli valorizzo il codice
					// protocollo
					if (!StringUtils.isBlank(dto.getDescTipoDocIndex())
							&& dto.getDescTipoDocIndex().contains("DOCUMENTO PRESENTE SU ACTA")
							&& "ACTA".equals(dto.getCodTipoDoc()) && !StringUtils.isBlank(dto.getProtocollo())) {

						doc.setProtocollo(dto.getProtocollo());
						LOG.info(prf + " settata doc.setProtocollo");
					}
					result.add(doc);
				}
			}

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca dei documenti: ", e);
			throw new DaoException("Errore durante la ricerca dei documenti.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	private String findIdBandoByIdProgetto(String idProgetto) throws DaoException {

		String prf = "[DocumentiDiProgettoDAOImpl::findIdBandoByIdProgetto] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + ";");

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}

		Long idBando = null;

		try {

			String sql = "select prbli.id_bando as value 												\r\n"
					+ "from pbandi_t_progetto ptp													\r\n"
					+ "join pbandi_t_domanda ptd													\r\n"
					+ "on ptp.id_domanda = ptd.id_domanda											\r\n"
					+ "join pbandi_r_bando_linea_intervent prbli									\r\n"
					+ "on ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento		\r\n"
					+ "where id_progetto = ?										";

			Object[] par = { idProgetto };

			// getJdbcTemplate().queryForObject(sql, param, new
			// BeanRowMapper(Integer.class));

			logger.info(prf + "\n" + sql);
			idBando = ((LongValue) getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(LongValue.class)))
					.getValue();

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca del bando: ", e);
			// throw new DaoException(" ERRORE nella ricerca dei dati beneficirio:");
			return null;
		} finally {
			LOG.info(prf + " END");
		}

		return idBando.toString();
	}

	private String findIdBandoLineaByIdProgetto(String idProgetto) throws DaoException {

		String prf = "[DocumentiDiProgettoDAOImpl::findIdBandoLineaByIdProgetto] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + ";");

		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}

		Long idBando = null;

		try {

			String sql = "select prbli.progr_bando_linea_intervento as value							\r\n"
					+ "from pbandi_t_progetto ptp													\r\n"
					+ "join pbandi_t_domanda ptd													\r\n"
					+ "on ptp.id_domanda = ptd.id_domanda											\r\n"
					+ "join pbandi_r_bando_linea_intervent prbli									\r\n"
					+ "on ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento		\r\n"
					+ "where id_progetto = ?													";

			Object[] par = { idProgetto };

			// getJdbcTemplate().queryForObject(sql, param, new
			// BeanRowMapper(Integer.class));

			logger.info(prf + "\n" + sql);
			idBando = ((LongValue) getJdbcTemplate().queryForObject(sql, par, new BeanRowMapper(LongValue.class)))
					.getValue();

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca del bando linea intervento : ", e);
			// throw new DaoException(" ERRORE nella ricerca dei dati beneficirio:");
			return null;
		} finally {
			LOG.info(prf + " END");
		}

		return idBando.toString();
	}

	private String findFlagFirmaAutografa(String idDocumentoIndex) throws DaoException {

		String prf = "[DocumentiDiProgettoDAOImpl::findFlagFirmaAutografa] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + ";");

		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}

		String flagFirmaAutografa = null;

		try {

			String sql = "SELECT flag_firma_autografa as value				\r\n"
					+ "FROM PBANDI_T_DOCUMENTO_INDEX					\r\n"
					+ "WHERE id_documento_index = ?							";

			Object[] par = { idDocumentoIndex };

			// getJdbcTemplate().queryForObject(sql, param, new
			// BeanRowMapper(Integer.class));

			logger.info(prf + "\n" + sql);
			StringValue response = (StringValue) getJdbcTemplate().queryForObject(sql, par,
					new BeanRowMapper(StringValue.class));
			flagFirmaAutografa = response != null ? response.getValue() : null;

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella ricerca della firma autografa : ", e);
			// throw new DaoException(" ERRORE nella ricerca dei dati beneficirio:");
			return null;
		} finally {
			LOG.info(prf + " END");
		}

		return flagFirmaAutografa;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex UploadFileAction.upload()
	public Boolean salvaUpload(MultipartFormDataInput multipartFormData, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::salvaUpload] ";
		LOG.info(prf + "BEGIN");

		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato");
		}

		// String nomeFile = multipartFormData.getFormDataPart("nomeFile", String.class,
		// null);
		String codiceRuolo = multipartFormData.getFormDataPart("codiceRuolo", String.class, null); // in
																									// uploadFileIstruttori.js
																									// era
																									// tipoAnagraficaMittente,
		Long idTipoDocIndex = multipartFormData.getFormDataPart("idTipoDocIndex", Long.class, null);
		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		String listaIdCategAnag = multipartFormData.getFormDataPart("listaIdCategAnag", String.class, null); // numeri
																												// separati
																												// da
																												// virgole

		// LOG.info(prf+"input nomeFile = "+nomeFile);
		LOG.info(prf + "input codiceRuolo      = " + codiceRuolo);
		LOG.info(prf + "input idTipoDocIndex   = " + idTipoDocIndex);
		LOG.info(prf + "input idProgetto       = " + idProgetto);
		LOG.info(prf + "input listaIdCategAnag = " + listaIdCategAnag);

		// if (StringUtils.isBlank(nomeFile)) {
		// throw new InvalidParameterException("nomeFile non valorizzato");
		// }
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		if (idTipoDocIndex == null) {
			throw new InvalidParameterException("idTipoDocIndex non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (StringUtils.isBlank(listaIdCategAnag)) {
			throw new InvalidParameterException("listaIdCategAnag non valorizzato");
		}

		Boolean esito = true;
		try {

			// Legge il file firmato dal multipart.
			Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
			List<InputPart> listInputPart = map.get("file");

			if (listInputPart == null) {
				LOG.info("listInputPart NULLO");
			} else {
				LOG.info("listInputPart SIZE = " + listInputPart.size());
			}
			for (InputPart i : listInputPart) {
				MultivaluedMap<String, String> m = i.getHeaders();
				Set<String> s = m.keySet();
				for (String x : s) {
					LOG.info("SET = " + x);
				}
			}

			FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
			if (file == null) {
				throw new InvalidParameterException("File non valorizzato");
			}

			LOG.info(prf + "input nomeFile = " + file.getNomeFile());
			LOG.info(prf + "input bytes.length = " + file.getBytes().length);

			// Copio listaIdCategAnag (stringa con id divisi da virgola) in
			// elencoIdCategAnag (vettore di Long).
			ArrayList<Long> elencoIdCategAnag = new ArrayList<Long>();
			if (listaIdCategAnag != null && listaIdCategAnag.length() > 0) {
				String[] listaTemp = listaIdCategAnag.split(",");
				for (int i = 0; i < listaTemp.length; i++) {
					elencoIdCategAnag.add(new Long(listaTemp[i]));
				}
			}

			String nomeFile = this.addTimestampToFileName(file.getNomeFile());

			BigDecimal idCategAnagrafica = decodificheDAOImpl.idDaDescrizione("PBANDI_D_TIPO_ANAGRAFICA",
					"ID_CATEG_ANAGRAFICA", "DESC_BREVE_TIPO_ANAGRAFICA", codiceRuolo);
			if (idCategAnagrafica == null)
				throw new Exception("Categoria anagrafica non trovata.");

			String descBreveTipoDocIndex = decodificheDAOImpl.descrizioneDaId("PBANDI_C_TIPO_DOCUMENTO_INDEX",
					"ID_TIPO_DOCUMENTO_INDEX", "DESC_BREVE_TIPO_DOC_INDEX", idTipoDocIndex);
			if (descBreveTipoDocIndex == null || descBreveTipoDocIndex.length() == 0)
				throw new Exception("Tipo documento index non trovato.");

			Date currentDate = new Date(System.currentTimeMillis());

			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
			documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(idTipoDocIndex));
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdProgetto(new BigDecimal(idProgetto));
			documentoIndexVO.setIdCategAnagraficaMitt(idCategAnagrafica);
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO.setIdEntita(new BigDecimal(53)); // PBANDI_T_PROGETTO
			documentoIndexVO.setIdTarget(new BigDecimal(idProgetto)); // id della PBANDI_T_PROGETTO
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setUuidNodo("UUID");

			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
			documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, elencoIdCategAnag);
			LOG.info(prf + "idDocumentoIndex inserito = " + documentoIndexVO.getIdDocumentoIndex());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nel salvataggio del file con visibilita: ", e);
			throw new DaoException(" ERRORE nel salvataggio del file con visibilita.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	// Ex UploadFileAction.upload()
	public Boolean cancellaFileConVisibilita(Long idDocumentoIndex, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::cancellaFileConVisibilita] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idUtente = " + idUtente);

		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}

		Boolean esito = true;
		try {

			esito = documentoManager.cancellaFileConVisibilita(new BigDecimal(idDocumentoIndex));

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella cancellazione del file con visibilita: ", e);
			throw new DaoException(" ERRORE nella cancellazione del file con visibilita.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	@Override
	public Boolean cancellaRecordDocumentoIndex(Long idDocumentoIndex, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {

		String prf = "[DocumentiDiProgettoDAOImpl::cancellaRecordDocumentoIndex] ";
		LOG.info(prf + "BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idUtente = " + idUtente);

		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato");
		}

		Boolean esito = true;
		try {

			esito = documentoManager.cancellaRecordDocumentoIndex(new BigDecimal(idDocumentoIndex));

		} catch (Exception e) {
			LOG.error(prf + " ERRORE nella cancellazione del file sulla tabella PBANDI_R_CATEG_ANAG_DOC_INDEX: ", e);
			throw new DaoException(" ERRORE nella cancellazione del file sulla tabella PBANDI_R_CATEG_ANAG_DOC_INDEX.");
		} finally {
			LOG.info(prf + " END");
		}

		return esito;
	}

	private String addTimestampToFileName(String originalFileName) {
		String time = DateUtil.getTime(new java.util.Date(), Constants.DATEHOUR_FORMAT_PER_NOME_FILE);
		String tipoFile = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		String nomeFile = originalFileName.substring(0, originalFileName.lastIndexOf("."));
		return nomeFile + "_" + time + tipoFile;
	}

	@Override
	// Ex RicercaDocumentazioneAction.getFilesAssociated()
	public List<DocumentoAllegatoDTO> allegati(String codTipoDocIndex, Long idDocumentoIndex, Long idUtente,
			String idIride) throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::allegati] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "codTipoDocIndex = " + codTipoDocIndex + "; idDocumentoIndex = " + idDocumentoIndex
				+ "; idUtente = " + idUtente);

		if (StringUtils.isBlank(codTipoDocIndex)) {
			throw new InvalidParameterException("codTipoDocIndex non valorizzato.");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		List<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO[] filesAssociated;
			filesAssociated = gestioneDocumentazioneBusinessImpl.getFilesAssociated(idUtente, idIride, codTipoDocIndex,
					idDocumentoIndex);

			if (filesAssociated == null)
				return result;

			for (it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentazione.DocumentoDTO dtoSrv : filesAssociated) {
				DocumentoAllegatoDTO dto = new DocumentoAllegatoDTO();
				dto.setNome(dtoSrv.getNomeFile());
				dto.setSizeFile(dtoSrv.getSizeFile());
				dto.setId(dtoSrv.getIdDocumentoIndex());
				result.add(dto);
			}

			// Aggiungo anche gli allegati alla checklist CR183
			String sql = "SELECT ptdi.ID_DOCUMENTO_INDEX, ptdi.NOME_FILE, ptdi.ID_PROGETTO\n"
					+ "FROM PBANDI_T_DOCUMENTO_INDEX ptdi \n"
					+ "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptdi.ID_ENTITA \n"
					+ "\tAND pce.NOME_ENTITA = 'PBANDI_T_CHECKLIST'\n"
					+ "JOIN PBANDI_T_CHECKLIST ptc ON ptc.ID_CHECKLIST = ptdi.ID_TARGET \n"
					+ "JOIN PBANDI_T_DOCUMENTO_INDEX ptdi2 ON ptdi2.ID_TARGET = ptc.ID_DICHIARAZIONE_SPESA\n"
					+ "JOIN PBANDI_C_ENTITA pce2 ON pce2.ID_ENTITA = ptdi2.ID_ENTITA \n"
					+ "\tAND pce2.NOME_ENTITA = 'PBANDI_T_DICHIARAZIONE_SPESA'\n"
					+ "WHERE ptdi2.ID_DOCUMENTO_INDEX = ?\n" + "AND ptdi.FLAG_VISIBILE_BEN IS NULL";
			result.addAll(getJdbcTemplate().query(sql, (rs, rowNum) -> {
				DocumentoAllegatoDTO documentoDTO = new DocumentoAllegatoDTO();
				documentoDTO.setId(rs.getLong("ID_DOCUMENTO_INDEX"));
				documentoDTO.setIdProgetto(rs.getLong("ID_PROGETTO"));
				documentoDTO.setNome(rs.getString("NOME_FILE"));
				return documentoDTO;
			}, idDocumentoIndex));

			LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca degli allegati: ", e);
			throw new DaoException("Errore durante la la ricerca degli allegati.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex RicercaDocumentazioneAction.getFilesAssociatedIntegrazioniDS()
	public List<DatiIntegrazioneDsDTO> allegatiIntegrazioniDS(Long idDichiarazioneDiSpesa, String codTipoDocIndex,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::allegatiIntegrazioniDS] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idUtente = " + idUtente);

		if (idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}
		if (codTipoDocIndex == null) {
			throw new InvalidParameterException("codTipoDocIndex non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		List<DatiIntegrazioneDsDTO> result = new ArrayList<DatiIntegrazioneDsDTO>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO[] integrazioni;
			if (codTipoDocIndex.equals("CFP")) {
				integrazioni = validazioneRendicontazioneBusinessImpl.findIntegrazioniSpesaCFP(idUtente, idIride,
						idDichiarazioneDiSpesa);
			} else {
				integrazioni = validazioneRendicontazioneBusinessImpl.findIntegrazioniSpesa(idUtente, idIride,
						idDichiarazioneDiSpesa);
			}

			if (integrazioni == null)
				return result;

			for (it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO dto : integrazioni) {
				DatiIntegrazioneDsDTO dati = new DatiIntegrazioneDsDTO();
				dati.setIdIntegrazioneSpesa(dto.getIdIntegrazioneSpesa().longValue());
				dati.setDataRichiesta(DateUtil.getDate(dto.getDataRichiesta()));
				dati.setDataInvio(DateUtil.getDate(dto.getDataInvio()));
				dati.setDescrizione(dto.getDescrizione());
				if (dto.getDataInvio() != null) {

					it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] allegatiSrv;
					allegatiSrv = archivioBusinessImpl.getFilesAssociatedIntegrazioneDS(idUtente, idIride,
							dto.getIdIntegrazioneSpesa());

					ArrayList<DocumentoAllegatoDTO> allegati = new ArrayList<DocumentoAllegatoDTO>();
					for (it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO fileDto : allegatiSrv) {
						DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
						allegato.setNome(fileDto.getNomeFile());
						allegato.setSizeFile(fileDto.getSizeFile());
						allegato.setId(fileDto.getIdDocumentoIndex());
						allegati.add(allegato);
					}
					dati.setAllegati(allegati);

				}
				result.add(dati);
			}

			LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca degli allegatiallegati Integrazioni DS: ", e);
			throw new DaoException("Errore durante la la ricerca degli allegati allegatiIntegrazioni DS.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex RicercaDocumentazioneAction.getFilesAssociatedIntegrazioniDS()
	// Usato per popolare la popup degli allegati per le checklist in loco in
	// Documenti di Progetto.
	public List<DocumentoAllegatoDTO> allegatiVerbaleChecklist(String codTipoDocIndex, Long idChecklist,
			Long idDocIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::allegatiVerbaleChecklist] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "codTipoDocIndex = " + codTipoDocIndex + "; idChecklist = " + idChecklist + "; idUtente = "
				+ idUtente);

		if (StringUtils.isBlank(codTipoDocIndex)) {
			throw new InvalidParameterException("codTipoDocIndex non valorizzato.");
		}
		if (idChecklist == null) {
			throw new InvalidParameterException("idChecklist non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		ArrayList<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FileDTO[] filesAssociated = null;
			filesAssociated = checkListBusinessImpl.getFilesAssociatedVerbaleChecklist(idUtente, idIride,
					codTipoDocIndex, idChecklist, idDocIndex);

			if (filesAssociated == null)
				return result;

			for (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FileDTO fileDto : filesAssociated) {
				DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
				allegato.setNome(fileDto.getNomeFile());
				allegato.setSizeFile(fileDto.getSizeFile());
				allegato.setId(fileDto.getIdDocumentoIndex());
				result.add(allegato);
			}

			LOG.info(prf + result.toString());

			// String estensioniAllegatiStr =
			// validazioneRendicontazioneDAO.getCostantiAllegati(idUtente,
			// idIride).getAttributo();
			// List <String> estensioniAllegati = Arrays.asList(
			// estensioniAllegatiStr.split(",") );

			// LOG.info(prf + " " + estensioniAllegati);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca del verbale checklist: ", e);
			throw new DaoException("Errore durante la la ricerca del verbale checklist.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public List<DocumentoAllegatoDTO> getAllegatiChecklist(Long idChecklist, Long idUtente) throws Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::getAllegatiChecklist] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idChecklist = " + idChecklist + "; idUtente = " + idUtente);

		List<DocumentoAllegatoDTO> result = new ArrayList<>();

		try {
			List<Long> tipoDocumentoIndex = new ArrayList<>();
			tipoDocumentoIndex.add(60L);
			tipoDocumentoIndex.add(63L);
			if (getJdbcTemplate().queryForObject("SELECT count(*)\n" + "FROM PBANDI_T_UTENTE ptu  \n"
					+ "JOIN PBANDI_R_SOGG_TIPO_ANAGRAFICA prsta ON prsta.id_soggetto = ptu.id_soggetto\n"
					+ "JOIN PBANDI_R_DOC_INDEX_TIPO_ANAG prdita ON prdita.id_tipo_anagrafica = prsta.id_tipo_anagrafica\n"
					+ "WHERE prdita.ID_TIPO_DOCUMENTO_INDEX = 33\n" + "AND ptu.id_utente = ?", Long.class,
					idUtente) > 0) {
				tipoDocumentoIndex.add(33L);
			}

			StringBuilder query = new StringBuilder("SELECT \n"
					+ "documentoIndex.id_documento_index AS idDocumentoIndex,\n"
					+ "documentoIndex.nome_file AS nomeFile\n" + "FROM PBANDI_T_DOCUMENTO_INDEX documentoIndex\n"
					+ "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = documentoIndex.id_entita AND entita.NOME_ENTITA = 'PBANDI_T_CHECKLIST'\n"
					+ "JOIN PBANDI_T_CHECKLIST checklist ON checklist.id_checklist = documentoIndex.id_target\n"
					+ "WHERE checklist.ID_CHECKLIST = ?\n" + "AND documentoIndex.ID_TIPO_DOCUMENTO_INDEX IN (");
			for (int i = tipoDocumentoIndex.size(); i > 0; --i) {
				if (i != 1) {
					query.append(" ?,");
				} else {
					query.append(" ?)");
				}
			}

			LOG.info(prf + query);
			result.addAll(getJdbcTemplate().query(query.toString(), ps -> {
				int k = 1;
				ps.setLong(k++, idChecklist);
				for (Long tipoDoc : tipoDocumentoIndex) {
					ps.setLong(k++, tipoDoc);
				}
			}, (rs, rowNum) -> {
				DocumentoAllegatoDTO dto = new DocumentoAllegatoDTO();

				dto.setId(rs.getLong("idDocumentoIndex"));
				dto.setNome(rs.getString("nomeFile"));

				return dto;
			}));
		} catch (Exception e) {
			LOG.error(prf + "Error while trying to getAllegatiChecklist: ", e);
			throw new DaoException("Wrror while trying to getAllegatiChecklist.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	/*
	 * @Override public List<DocumentoAllegatoDTO> allegatiVerbaleChecklist(Long
	 * idDocIndex, Long idUtente, String idIride) throws InvalidParameterException,
	 * Exception { String prf =
	 * "[DocumentiDiProgettoDAOImpl::allegatiVerbaleChecklist] "; LOG.info(prf +
	 * " BEGIN"); LOG.info(prf + "idDocIndex = " + idDocIndex + "; idUtente = " +
	 * idUtente);
	 * 
	 * 
	 * if (idDocIndex == null) { throw new
	 * InvalidParameterException("idChecklist non valorizzato."); } if (idUtente ==
	 * null) { throw new InvalidParameterException("idUtente non valorizzato."); }
	 * 
	 * ArrayList<DocumentoAllegatoDTO> result = new
	 * ArrayList<DocumentoAllegatoDTO>(); try {
	 * 
	 * it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FileDTO[] filesAssociated = null;
	 * filesAssociated =
	 * checkListBusinessImpl.getFilesAssociatedVerbaleChecklist(idUtente,
	 * idIride,idDocIndex);
	 * 
	 * if (filesAssociated == null) return result;
	 * 
	 * for (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FileDTO fileDto :
	 * filesAssociated) { DocumentoAllegatoDTO allegato = new
	 * DocumentoAllegatoDTO(); allegato.setNome(fileDto.getNomeFile());
	 * allegato.setSizeFile(fileDto.getSizeFile());
	 * allegato.setId(fileDto.getIdDocumentoIndex()); result.add(allegato); }
	 * 
	 * LOG.info(prf + result.toString());
	 * 
	 * //String estensioniAllegatiStr =
	 * validazioneRendicontazioneDAO.getCostantiAllegati(idUtente,
	 * idIride).getAttributo(); //List <String> estensioniAllegati = Arrays.asList(
	 * estensioniAllegatiStr.split(",") );
	 * 
	 * //LOG.info(prf + " " + estensioniAllegati);
	 * 
	 * 
	 * } catch (Exception e) { LOG.error(prf +
	 * " ERRORE durante la ricerca del verbale checklist: ", e); throw new
	 * DaoException("Errore durante la la ricerca del verbale checklist.", e); }
	 * finally { LOG.info(prf + " END"); }
	 * 
	 * return result; }
	 */

	@Override
	// Ex RicercaDocumentazioneAction.getFilesAssociatedIntegrazioniDS()
	// Usato per popolare la popup degli allegati per le checklist in loco
	// affidamento in Documenti di Progetto.
	public List<DocumentoAllegatoDTO> allegatiVerbaleChecklistAffidamento(String codTipoDocIndex, Long idDocumentoIndex,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::allegatiVerbaleChecklistAffidamento] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "codTipoDocIndex = " + codTipoDocIndex + "; idDocumentoIndex = " + idDocumentoIndex
				+ "; idUtente = " + idUtente);

		if (StringUtils.isBlank(codTipoDocIndex)) {
			throw new InvalidParameterException("codTipoDocIndex non valorizzato.");
		}
		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		ArrayList<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
		try {

			it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FileDTO[] filesAssociated = null;
			filesAssociated = checkListBusinessImpl.getFilesAssociatedVerbaleChecklistAffidamento(idUtente, idIride,
					codTipoDocIndex, idDocumentoIndex);

			if (filesAssociated == null)
				return result;

			for (it.csi.pbandi.pbweb.pbandisrv.dto.checklist.FileDTO fileDto : filesAssociated) {
				DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
				allegato.setNome(fileDto.getNomeFile());
				allegato.setSizeFile(fileDto.getSizeFile());
				allegato.setId(fileDto.getIdDocumentoIndex());
				result.add(allegato);
			}

			LOG.info(prf + result.toString());

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca del verbale checklist affidamento: ", e);
			throw new DaoException("Errore durante la ricerca del verbale checklist affidamento.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	// Ex RicercaDocumentazioneAction.getCodStatoDocument()
	public String codiceStatoDocumentoIndex(Long idDocumentoIndex, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[DocumentiDiProgettoDAOImpl::codiceStatoDocumentoIndex] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idUtente = " + idUtente);

		if (idDocumentoIndex == null) {
			throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		String result = "";
		try {

			result = gestioneDocumentazioneBusinessImpl.getCodStatoDoc(idUtente, idIride, idDocumentoIndex);

			LOG.info(prf + "Codice stato = " + result);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca del codice stato: ", e);
			throw new DaoException("Errore durante la ricerca del codice stato.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public int salvaDocumentoIndex(PbandiTDocumentoIndexVO documentoIndexVO)
			throws InvalidParameterException, Exception {

		String prf = "[DocumentiDiProgettoDAOImpl::salvaDocumentoIndex] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "Parametro in input -> documentoIndexVO: " + documentoIndexVO.toString());

		BigDecimal idDocumentoIndex = documentoManager.nuovoIdTDocumentoIndex();

		final String INSERT_PBANDI_T_DOCUMENTO_INDEX = ""
				+ "INSERT INTO PBANDI_T_DOCUMENTO_INDEX(ID_DOCUMENTO_INDEX, ID_TARGET, "
				+ "UUID_NODO, REPOSITORY, DT_INSERIMENTO_INDEX, " + "ID_TIPO_DOCUMENTO_INDEX, ID_ENTITA, "
				+ "NOME_FILE, ACTA_INDICE_CLASSIFICAZ_ESTESO, " + "ID_UTENTE_INS, ID_PROGETTO, "
				+ "ID_STATO_DOCUMENTO ) VALUES(" + "?, ?, ?, ?, SYSDATE, " + "?, ?, " + "?, ?, " + "?, ?," + "? ) ";

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			jdbcTemplate.update(INSERT_PBANDI_T_DOCUMENTO_INDEX,
					new Object[] { idDocumentoIndex, idDocumentoIndex, documentoIndexVO.getUuidNodo(),
							documentoIndexVO.getRepository(), documentoIndexVO.getIdTipoDocumentoIndex(),
							documentoIndexVO.getIdEntita(), documentoIndexVO.getNomeFile(),
							documentoIndexVO.getActaIndiceClassificazioneEsteso(), documentoIndexVO.getIdUtenteIns(),
							documentoIndexVO.getIdProgetto(), documentoIndexVO.getIdStatoDocumento() });

		} catch (Exception e) {
			LOG.error(prf + " Errore: " + e);
			throw e;
		}

		LOG.info(prf + " END");
		return idDocumentoIndex != null ? idDocumentoIndex.intValue() : null;
	}

	private class EnteProgettoDomandaVORowMapper implements RowMapper<EnteProgettoDomandaVO> {

		public EnteProgettoDomandaVO mapRow(ResultSet rs, int rowNum) throws SQLException {

			EnteProgettoDomandaVO enteProgettoDomanda = new EnteProgettoDomandaVO();

			enteProgettoDomanda.setSettore(rs.getString("SETTORE"));
			enteProgettoDomanda.setProgBandoLineaEnteComp(rs.getLong("PROGR_BANDO_LINEA_ENTE_COMP"));
			enteProgettoDomanda.setIdRuoloEnteCompetenza(rs.getLong("ID_RUOLO_ENTE_COMPETENZA"));
			enteProgettoDomanda.setDescBreveRuoloEnte(rs.getString("DESC_BREVE_RUOLO_ENTE"));
			enteProgettoDomanda.setIdEnteCompetenza(rs.getLong("ID_ENTE_COMPETENZA"));
			enteProgettoDomanda.setDescBreveTipoEnteCompetenz(rs.getString("DESC_ENTE_COMPETENZA"));
			enteProgettoDomanda.setDescBreveEnte(rs.getString("DESC_BREVE_ENTE"));
			enteProgettoDomanda.setDescBreveTipoEnteCompetenz(rs.getString("DESC_BREVE_TIPO_ENTE_COMPETENZ"));
			enteProgettoDomanda.setProgBandoLineaIntervento(rs.getLong("PROGR_BANDO_LINEA_INTERVENTO"));
			enteProgettoDomanda.setIdSettoreEnte(rs.getLong("ID_SETTORE_ENTE"));
			enteProgettoDomanda.setDescBreveSettore(rs.getString("DESC_BREVE_SETTORE"));
			enteProgettoDomanda.setDescSettore(rs.getString("DESC_SETTORE"));
			enteProgettoDomanda.setDescEnteSettore(rs.getString("DESC_ENTE_SETTORE"));
			enteProgettoDomanda.setIdIndirizzo(rs.getLong("ID_INDIRIZZO"));
			enteProgettoDomanda.setDtFineValidita(rs.getDate("DT_FINE_VALIDITA"));
			enteProgettoDomanda.setParolaChiave(rs.getString("PAROLA_CHIAVE"));
			enteProgettoDomanda.setFeedbackActa(rs.getString("FEEDBACK_ACTA"));
			enteProgettoDomanda.setConservazioneCorrente(rs.getLong("CONSERVAZIONE_CORRENTE"));
			enteProgettoDomanda.setConservazioneGenerale(rs.getLong("CONSERVAZIONE_GENERALE"));

			return enteProgettoDomanda;

		}
	}

	@Override
	public EnteProgettoDomandaVO getEnteProgettoDomandaVO(Long idProgetto) {
		String prf = "[DocumentiDiProgettoDAOImpl::getEnteProgettoDomandaVo] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "Parametro in input -> idProgetto: " + idProgetto);

		final String BANDO_LINEA_ENTE_COMP_PROGETTO_DOMANDA = ""
				+ "select blec.DESC_BREVE_ENTE || NVL (blec.DESC_SETTORE, NVL(blec.DESC_BREVE_SETTORE,'000'))SETTORE, blec.* "
				+ "from pbandi.PBANDI_V_BANDO_LINEA_ENTE_COMP blec, PBANDI_T_PROGETTO prog, PBANDI_T_DOMANDA do "
				+ "where blec.DESC_BREVE_RUOLO_ENTE ='DESTINATARIO' "
				+ "AND blec.PROGR_BANDO_LINEA_INTERVENTO = do.PROGR_BANDO_LINEA_INTERVENTO "
				+ "AND do.id_domanda = prog.id_domanda " + "AND prog.ID_PROGETTO = ? ";

		EnteProgettoDomandaVO enteProgettoDomandaVO;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug(prf + " : query " + BANDO_LINEA_ENTE_COMP_PROGETTO_DOMANDA);
		Object[] params = { idProgetto };
		try {

			enteProgettoDomandaVO = jdbcTemplate.queryForObject(BANDO_LINEA_ENTE_COMP_PROGETTO_DOMANDA,
					new EnteProgettoDomandaVORowMapper(), params);

		} catch (Exception e) {
			LOG.error(e);
			enteProgettoDomandaVO = null;
		}

		LOG.info(prf + " END");

		return enteProgettoDomandaVO;
	}

	@Override
	public Long getDocumentoIndex(PbandiTDocumentoIndexVO doc) {
		String prf = "[DocumentiDiProgettoDAOImpl::getDocumentoIndex] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "Parametro in input -> doc: " + doc);

		final String QUERY = "SELECT ID_DOCUMENTO_INDEX FROM PBANDI_T_DOCUMENTO_INDEX i " + "where i.UUID_NODO= ? "
				+ "and i.REPOSITORY= ? " + "and i.ID_TIPO_DOCUMENTO_INDEX= ? " + "and i.ID_ENTITA= ? "
				+ "and i.ID_UTENTE_INS=? " + "and i.id_progetto=?";

		Long idDocIndex;
		PbandiTDocumentoIndexVO docu;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug(prf + " : query = " + QUERY);
		Object[] params = { doc.getUuidNodo(), doc.getRepository(), doc.getIdTipoDocumentoIndex(), doc.getIdEntita(),
				doc.getIdUtenteIns(), doc.getIdProgetto() };
		try {

			idDocIndex = jdbcTemplate.queryForObject(QUERY, params, Long.class);
			LOG.info(prf + " idDocIndex=" + idDocIndex);

		} catch (Exception e) {
			LOG.error(e);
			idDocIndex = null;
		}

		LOG.info(prf + " END");
		return idDocIndex;
	}

	@Override
	public Boolean numeroProtocolloExists(String numProtocollo) {
		String prf = "[DocumentiDiProgettoDAOImpl::actaIndiceClassificazEstesoExists] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "Parametro in input -> numProtocollo: " + numProtocollo);

		final String QUERY = "SELECT ID_DOCUMENTO_INDEX FROM PBANDI_T_DOC_PROTOCOLLO "
				+ "where NUM_PROTOCOLLO = ? AND ID_SISTEMA_PROT = 4";

		Boolean found = Boolean.FALSE;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug(prf + " : query = " + QUERY);
		Object[] params = { numProtocollo };
		try {

			List<Long> ids = jdbcTemplate.queryForList(QUERY, params, Long.class);
			LOG.info(prf + " ids=" + ids);
			if (ids != null && ids.size() > 0) {
				found = Boolean.TRUE;
				LOG.info(prf + " documento protocollato già associato.");
			}
		} catch (Exception e) {
			LOG.error(e);
			found = null;
		}

		LOG.info(prf + " END");
		return found;
	}

	@Override
	public Long getIdChecklist(Long idDichiarazioneSpesa, Long idDocumentoIndex) {
		String prf = "[DocumentiDiProgettoDAOImpl::getIdChecklist]";
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
	public AllegatiDichiarazioneDiSpesaDTO getAllegatiByTipoDoc(Long idTarget, Long idProgetto, String descBreveTipoDoc,
			Long idUtente, String idIride) throws InvalidParameterException, Exception {
		String prf = "[DichiarazioneDiSpesaDAOImpl::allegatiDichiarazioneDiSpesaIntegrazioni] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idTarget = " + idTarget + "; descBreveTipoDoc = "
				+ descBreveTipoDoc + "; idUtente = " + idUtente);

		if (idTarget == null) {
			throw new InvalidParameterException("idTarget non valorizzato.");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (StringUtils.isEmpty(descBreveTipoDoc)) {
			throw new InvalidParameterException("descBreveTipoDoc non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}

		AllegatiDichiarazioneDiSpesaDTO result = new AllegatiDichiarazioneDiSpesaDTO();
		try {
			String nomeEntita = null;
			switch (descBreveTipoDoc) {
				case Constants.DESC_BREVE_TIPO_DOC_INDEX_RA:
				case Constants.DESC_BREVE_TIPO_DOC_INDEX_RE:
				case Constants.DESC_BREVE_TIPO_DOC_INDEX_R2A:
				case Constants.DESC_BREVE_TIPO_DOC_INDEX_SLD:
				case Constants.DESC_BREVE_TIPO_DOC_INDEX_RUA:
					nomeEntita = Constants.ENTITA_T_RICHIESTA_EROGAZIONE;
					break;
				case Constants.DESC_BREVE_TIPO_DOC_INDEX_PR:
					nomeEntita = Constants.ENTITA_T_CONTO_ECONOMICO;
					break;
				default:
					LOG.error(prf
							+ " La visualizzazione degli allegati non e' gestita per il documento con descBreveTipoDoc "
							+ descBreveTipoDoc);
					throw new Exception(
							"La visualizzazione degli allegati non e' gestita per il documento con descBreveTipoDoc "
									+ descBreveTipoDoc);
			}

			if (nomeEntita != null) {
				LOG.info(prf + "Cerco gli allegati");
				it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] allegati;
				allegati = archivioBusinessImpl.getFilesAssociatedByIdTargetAndEntita(idUtente, prf, idTarget,
						nomeEntita, idProgetto);
				result.setAllegati(this.popolaListaDocumentoAllegatoDTODaFileDTO(allegati));
			}

			LOG.info(prf + result);

		} catch (Exception e) {
			LOG.error(prf + " ERRORE durante la ricerca degli allegati: ", e);
			throw new DaoException("Errore durante la ricerca degli allegati.", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	// Trasforma un array da FileDTO in DocumentoAllegatoDTO.
	private ArrayList<DocumentoAllegatoDTO> popolaListaDocumentoAllegatoDTODaFileDTO(
			it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] files) {
		ArrayList<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
		if (!ObjectUtil.isEmpty(files)) {
			for (it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO file : files) {
				DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
				allegato.setId(file.getIdDocumentoIndex());
				allegato.setNome(file.getNomeFile());
				allegato.setDisassociabile(true);
				allegato.setSizeFile(file.getSizeFile());
				result.add(allegato);
			}
		}
		return result;
	}
}
