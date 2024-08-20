/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbservizit.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbwebrce.dto.CheckDescBreve;
import it.csi.pbandi.pbwebrce.dto.ComuneProvinciaSedeDTO;
import it.csi.pbandi.pbwebrce.dto.CupProgettoNumeroDomandaDTO;
import it.csi.pbandi.pbwebrce.dto.DatiQteDTO;
import it.csi.pbandi.pbwebrce.dto.DtFineEffEstremiAttoDTO;
import it.csi.pbandi.pbwebrce.dto.EsitoDTO;
import it.csi.pbandi.pbwebrce.dto.NumberDescBreve;
import it.csi.pbandi.pbwebrce.dto.QteFaseDTO;
import it.csi.pbandi.pbwebrce.dto.QteHtmlDTO;
import it.csi.pbandi.pbwebrce.dto.TitoloBandoNomeBandoLineaDTO;
import it.csi.pbandi.pbwebrce.exception.DaoException;
import it.csi.pbandi.pbwebrce.integration.dao.ContoEconomicoWaDAO;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.ComuneProvinciaSedeRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.CupProgettoNumeroDomandaRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.DtFineEffEstremiAttoRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.QteFaseRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.QteHtmlRowMapper;
import it.csi.pbandi.pbwebrce.integration.dao.impl.rowmapper.TitoloBandoNomeBandoLineaRowMapper;
import it.csi.pbandi.pbwebrce.util.Constants;
import it.csi.pbandi.pbwebrce.util.FileUtil;

@Component
public class ContoEconomicoWaDAOImpl extends JdbcDaoSupport implements ContoEconomicoWaDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	DecodificheDAOImpl decodificheDAOImpl;

	@Autowired
	DocumentoManager documentoManager;

	@Autowired
	public ContoEconomicoWaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public QteHtmlDTO getModelloQte(Long progrBandoLineaIntervento, Long idSoggetto, String codiceRuolo, Long idUtente,
			String idIride, Long idBeneficiario) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getModelloQte] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "progrBandoLineaIntervento = " + progrBandoLineaIntervento + "; idUtente = " + idUtente
				+ "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (progrBandoLineaIntervento == 0) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		QteHtmlDTO result = null;
		try {
			String sql;
			sql = "SELECT pcmq.ID_MODELLO_QTES AS ID, pcmq.HTML_QTES\r\n" + "FROM PBANDI_C_MODELLO_QTES pcmq\r\n"
					+ "JOIN PBANDI_R_MOD_QTES_BAN_LI_INT prmqbli ON prmqbli.ID_MODELLO_QTES = pcmq.ID_MODELLO_QTES \r\n"
					+ "WHERE prmqbli.PROGR_BANDO_LINEA_INTERVENTO = ?";

			Object[] args = new Object[] { progrBandoLineaIntervento };
			LOG.info("<progrBandoLineaIntervento>: " + progrBandoLineaIntervento);
			LOG.info(prf + "\n" + sql + "\n");

			List<QteHtmlDTO> dtos = getJdbcTemplate().query(sql, args, new QteHtmlRowMapper());
			if (dtos != null && dtos.size() > 0) {
				result = dtos.get(0);
				result.setIdQtesHtmlProgetto(null);
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getModelloQte: ", e);
			throw new Exception(" ERRORE in getModelloQte.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public QteHtmlDTO getQteProgetto(Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente,
			String idIride, Long idBeneficiario) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getQteProgetto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		QteHtmlDTO result = null;
		try {
			String sql;
			sql = "SELECT ID_QTES_HTML_PROGETTO AS ID , HTML_QTES_PROGETTO AS HTML_QTES\r\n"
					+ "FROM PBANDI_T_QTES_HTML_PROGETTO ptqhp\r\n" + "WHERE ID_PROGETTO = ?";

			Object[] args = new Object[] { idProgetto };
			LOG.info("<idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<QteHtmlDTO> dtos = getJdbcTemplate().query(sql, args, new QteHtmlRowMapper());
			if (dtos != null && dtos.size() > 0) {
				result = dtos.get(0);
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getQteProgetto: ", e);
			throw new Exception(" ERRORE in getQteProgetto.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = { InvalidParameterException.class, Exception.class })
	public EsitoDTO salvaQteHtmlProgetto(Long idProgetto, Long idQtesHtmlProgetto, String htmlQtesProgetto,
			Long idSoggetto, String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::salvaQteHtmlProgetto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idQtesHtmlProgetto = " + idQtesHtmlProgetto + "; idUtente = "
				+ idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (htmlQtesProgetto == null || htmlQtesProgetto.length() == 0) {
			throw new InvalidParameterException("htmlQtesProgetto non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		EsitoDTO result = new EsitoDTO();
		result.setEsito(Boolean.FALSE);
		try {
			String sql = "";
			Object[] args = null;
			if (idQtesHtmlProgetto == null) { // inserimento
				sql = "INSERT INTO PBANDI_T_QTES_HTML_PROGETTO\r\n"
						+ "(ID_QTES_HTML_PROGETTO, ID_PROGETTO, HTML_QTES_PROGETTO, DT_INSERIMENTO, ID_UTENTE_INS)\r\n"
						+ "VALUES(SEQ_PBANDI_T_QTES_HTML_PROG.NEXTVAL, ?, ?, SYSDATE, ?)";
				args = new Object[] { idProgetto, htmlQtesProgetto, idUtente };
				LOG.info("<idProgetto>: " + idProgetto + ", <idUtente>: " + idUtente);
			} else { // modifica
				sql = "UPDATE PBANDI.PBANDI_T_QTES_HTML_PROGETTO\r\n"
						+ "SET ID_PROGETTO=?, HTML_QTES_PROGETTO=?, DT_AGGIORNAMENTO=SYSDATE, ID_UTENTE_AGG=?\r\n"
						+ "WHERE ID_QTES_HTML_PROGETTO = ?";
				args = new Object[] { idProgetto, htmlQtesProgetto, idUtente, idQtesHtmlProgetto };
				LOG.info("<idProgetto>: " + idProgetto + ", <idUtente>: " + idUtente + ", <idQtesHtmlProgetto>: "
						+ idQtesHtmlProgetto);
			}

			LOG.info(prf + "\n" + sql + "\n");

			int rows = getJdbcTemplate().update(sql, args);
			if (rows > 0) {
				result.setEsito(Boolean.TRUE);
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in salvaQteHtmlProgetto: ", e);
			throw new Exception(" ERRORE in salvaQteHtmlProgetto.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = { InvalidParameterException.class, Exception.class })
	public EsitoDTO salvaQteDatiProgetto(Long idProgetto, List<DatiQteDTO> datiQte, Long idSoggetto, String codiceRuolo,
			Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::salvaQteHtmlProgetto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (datiQte == null || datiQte.size() == 0) {
			throw new InvalidParameterException("datiQte non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		EsitoDTO result = new EsitoDTO();
		result.setEsito(Boolean.FALSE);
		try {
			for (DatiQteDTO datoQte : datiQte) {
				String sql = "";
				Object[] args = null;

				// cerco se esiste gia un record in PBANDI_T_DATI_PROGETTO_QTES
				sql = "SELECT ID_DATI_QTES\r\n" + "FROM PBANDI_T_DATI_PROGETTO_QTES ptdqp \r\n"
						+ "WHERE ID_PROGETTO = ? AND ID_COLONNA_QTES = ?";

				args = new Object[] { idProgetto, datoQte.getIdColonnaQtes() };
				LOG.info(prf + " <idProgetto>: " + idProgetto + ", <idColonnaQtes>: " + datoQte.getIdColonnaQtes());
				LOG.info(prf + "\n" + sql + "\n");

				List<Integer> idDatiQtesList = getJdbcTemplate().queryForList(sql, args, Integer.class);
				Integer idDatiQtes = null;
				if (idDatiQtesList != null && idDatiQtesList.size() > 0) {
					idDatiQtes = idDatiQtesList.get(0);
				}

				LOG.info(prf + " <idDatiQtes>: " + idDatiQtes);
				if (idDatiQtes == null) { // inserimento
					LOG.info(prf + " INSERT in PBANDI_T_DATI_PROGETTO_QTES");

					sql = "SELECT SEQ_PBANDI_T_DATI_QTES_PROG.nextval FROM dual";
					idDatiQtes = getJdbcTemplate().queryForObject(sql, Integer.class);
					LOG.info(prf + "\n" + sql + "\n");
					LOG.info(prf + " <idDatiQtes>: " + idDatiQtes);

					sql = "INSERT INTO PBANDI_T_DATI_PROGETTO_QTES\r\n"
							+ "(ID_DATI_QTES, ID_PROGETTO, ID_COLONNA_QTES, ESTREMI_ATTO_APPROVAZIONE, DT_INSERIMENTO, ID_UTENTE_INS, ESTREMI_ATTO_APPROVAZIONE_CCC)\r\n"
							+ "VALUES(?, ?, ?, ?, SYSDATE, ?, ?)";
					args = new Object[] { idDatiQtes, idProgetto, datoQte.getIdColonnaQtes(),
							datoQte.getEstremiAttoApprovazione(), idUtente, datoQte.getEstremiAttoApprovazioneCert() };
					LOG.info(prf + " <idProgetto>: " + idProgetto + ", <idColonnaQtes>: " + datoQte.getIdColonnaQtes()
							+ ", <estremiAttoApprovazione>: " + datoQte.getEstremiAttoApprovazione() + ", <idUtente>: "
							+ idUtente + ", <estremiAttoApprovazioneCert>: "
							+ datoQte.getEstremiAttoApprovazioneCert());
					LOG.info(prf + "\n" + sql + "\n");

					int rows = getJdbcTemplate().update(sql, args);
					if (rows == 0) {
						throw new Exception(prf + "Errore durante il salvataggio dei dati del QTE");
					}
				} else { // modifica
					LOG.info(prf + " UPDATE in PBANDI_T_DATI_PROGETTO_QTES");
					sql = "UPDATE PBANDI_T_DATI_PROGETTO_QTES\r\n"
							+ "SET ESTREMI_ATTO_APPROVAZIONE=?, DT_AGGIORNAMENTO=SYSDATE, ID_UTENTE_AGG=?, ESTREMI_ATTO_APPROVAZIONE_CCC=? \r\n"
							+ "WHERE ID_DATI_QTES = ?";
					args = new Object[] { datoQte.getEstremiAttoApprovazione(), idUtente,
							datoQte.getEstremiAttoApprovazioneCert(), idDatiQtes };
					LOG.info(prf + " <idDatiQtes>: " + idDatiQtes + ", <estremiAttoApprovazione>: "
							+ datoQte.getEstremiAttoApprovazione() + ", <idUtente>: " + idUtente
							+ ", <estremiAttoApprovazioneCert>: " + datoQte.getEstremiAttoApprovazioneCert());
					LOG.info(prf + "\n" + sql + "\n");

					int rows = getJdbcTemplate().update(sql, args);
					if (rows == 0) {
						throw new Exception(prf + "Errore durante il salvataggio dei dati del QTE");
					}
				}
				if (datoQte.getImportiFonti() != null && datoQte.getImportiFonti().size() > 0) {
					aggiornaFontiQte(datoQte, idDatiQtes, idUtente);
				}
				if (datoQte.getTipiIntervento() != null && datoQte.getTipiIntervento().size() > 0) {
					aggiornaTipiInterventoQte(datoQte, idDatiQtes, idUtente);
				}
				if (datoQte.getAltriValori() != null && datoQte.getAltriValori().size() > 0) {
					aggiornaAltriValoriQte(datoQte, idDatiQtes, idUtente);
				}
			}
			result.setEsito(Boolean.TRUE);
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in salvaQteHtmlProgetto: ", e);
			throw new Exception(" ERRORE in salvaQteHtmlProgetto.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	private void aggiornaFontiQte(DatiQteDTO datoQte, Integer idDatiQtes, Long idUtente) throws Exception {
		String prf = "[ContoEconomicoWaDAOImpl::aggiornaFontiQte] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " Aggiorno le fonti sulla PBANDI_T_IMPORTO_FONTI_QTES");
		try {
			for (NumberDescBreve fonte : datoQte.getImportiFonti()) {

				String sql = "SELECT ID_SOGGETTO_FINANZIATORE\r\n" + "FROM PBANDI_D_SOGGETTO_FINANZIATORE pdsf \r\n"
						+ "WHERE DESC_BREVE_SOGG_FINANZIATORE = ?";
				Object[] args = new Object[] { fonte.getDescBreve() };
				List<Integer> idSoggettoFinanziatoreList = getJdbcTemplate().queryForList(sql, args, Integer.class);
				Integer idSoggettoFinanziatore = null;
				if (idSoggettoFinanziatoreList != null && idSoggettoFinanziatoreList.size() > 0) {
					idSoggettoFinanziatore = idSoggettoFinanziatoreList.get(0);
				}
				LOG.info(prf + " <descBreveSoggettoFinanziatore>: " + fonte.getDescBreve());
				LOG.info(prf + "\n" + sql + "\n");
				LOG.info(prf + " <idSoggettoFinanziatore>: " + idSoggettoFinanziatore);
				if (idSoggettoFinanziatore == null) {
					throw new InvalidParameterException(
							"descBreveSoggettoFinanziatore '" + fonte.getDescBreve() + "' non trovata.");
				}

				sql = "SELECT COUNT(*) \r\n" + "FROM PBANDI_T_IMPORTO_FONTI_QTES \r\n"
						+ "WHERE ID_DATI_QTES = ? AND ID_SOGGETTO_FINANZIATORE = ?";
				args = new Object[] { idDatiQtes, idSoggettoFinanziatore };
				int count = getJdbcTemplate().queryForObject(sql, args, Integer.class);
				LOG.info(prf + "\n" + sql + "\n");
				LOG.info(prf + " <count>: " + count);

				if (count == 0) {
					if (fonte.getNumber() != null) {
						sql = "INSERT INTO PBANDI_T_IMPORTO_FONTI_QTES\r\n"
								+ "(ID_DATI_QTES, ID_SOGGETTO_FINANZIATORE, IMPORTO, DT_INSERIMENTO, ID_UTENTE_INS)\r\n"
								+ "VALUES(?, ?, ?, SYSDATE, ?)";
						args = new Object[] { idDatiQtes, idSoggettoFinanziatore, fonte.getNumber(), idUtente };
						LOG.info(prf + " <idDatiQtes>: " + idDatiQtes + ", <idSoggettoFinanziatore>: "
								+ idSoggettoFinanziatore + ", <importo>: " + fonte.getNumber() + ", <idUtente>: "
								+ idUtente);
						LOG.info(prf + "\n" + sql + "\n");

						int rows = getJdbcTemplate().update(sql, args);
						if (rows == 0) {
							throw new Exception(prf + "Errore durante la insert degli importi delle fonti del QTE");
						}
					} else {
						LOG.info(prf + " Importo della fonte con descBreveSoggettoFinanziatore " + fonte.getDescBreve()
								+ " nullo. Non inserisco il record");
					}
				} else {
					sql = "UPDATE PBANDI_T_IMPORTO_FONTI_QTES\r\n"
							+ "SET IMPORTO = ?, DT_MODIFICA = SYSDATE, ID_UTENTE_AGG = ? \r\n"
							+ "WHERE ID_DATI_QTES = ? AND ID_SOGGETTO_FINANZIATORE = ?";
					args = new Object[] { fonte.getNumber(), idUtente, idDatiQtes, idSoggettoFinanziatore };
					LOG.info(prf + " <idDatiQtes>: " + idDatiQtes + ", <idSoggettoFinanziatore>: "
							+ idSoggettoFinanziatore + ", <importo>: " + fonte.getNumber() + ", <idUtente>: "
							+ idUtente);
					LOG.info(prf + "\n" + sql + "\n");

					int rows = getJdbcTemplate().update(sql, args);
					if (rows == 0) {
						throw new Exception(prf + "Errore durante l'update degli importi delle fonti del QTE");
					}
				}
			}
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in aggiornaFontiQte: ", e);
			throw new Exception(" ERRORE in aggiornaFontiQte.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	private void aggiornaTipiInterventoQte(DatiQteDTO datoQte, Integer idDatiQtes, Long idUtente) throws Exception {
		String prf = "[ContoEconomicoWaDAOImpl::aggiornaTipiInterventoQte] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " Aggiorno le tipologie intervento sulla PBANDI_T_TIPO_INTERV_QTES");
		try {
			for (CheckDescBreve tipo : datoQte.getTipiIntervento()) {

				String sql = "SELECT ID_TIPO_INTERVENTO \r\n" + "FROM PBANDI_D_TIPO_INTERV_QTES pdtiq  \r\n"
						+ "WHERE DESC_BREVE_INTERVENTO = ?";
				Object[] args = new Object[] { tipo.getDescBreve() };
				List<Integer> idTipoInterventoList = getJdbcTemplate().queryForList(sql, args, Integer.class);
				Integer idTipoIntervento = null;
				if (idTipoInterventoList != null && idTipoInterventoList.size() > 0) {
					idTipoIntervento = idTipoInterventoList.get(0);
				}
				LOG.info(prf + " <descBreveInervento>: " + tipo.getDescBreve());
				LOG.info(prf + "\n" + sql + "\n");
				LOG.info(prf + " <idTipoIntervento>: " + idTipoIntervento);
				if (idTipoIntervento == null) {
					throw new InvalidParameterException(
							"descBreveInervento '" + tipo.getDescBreve() + "' non trovata.");
				}

				sql = "SELECT COUNT(*) \r\n" + "FROM PBANDI_T_TIPO_INTERV_QTES \r\n"
						+ "WHERE ID_DATI_QTES = ? AND ID_TIPO_INTERVENTO = ?";
				args = new Object[] { idDatiQtes, idTipoIntervento };
				int count = getJdbcTemplate().queryForObject(sql, args, Integer.class);
				LOG.info(prf + "\n" + sql + "\n");
				LOG.info(prf + " <count>: " + count);

				String check = tipo.getCheck() != null && tipo.getCheck().equals(Boolean.TRUE) ? "S" : "N";

				if (count == 0) {
					sql = "INSERT INTO PBANDI_T_TIPO_INTERV_QTES\r\n"
							+ "(ID_DATI_QTES, ID_TIPO_INTERVENTO, FLAG_SN, DT_INSERIMENTO, ID_UTENTE_INS)\r\n"
							+ "VALUES(?, ?, ?, SYSDATE, ?)";

					args = new Object[] { idDatiQtes, idTipoIntervento, check, idUtente };
					LOG.info(prf + " <idDatiQtes>: " + idDatiQtes + ", <idTipoIntervento>: " + idTipoIntervento
							+ ", <flagSN>: " + check + ", <idUtente>: " + idUtente);
					LOG.info(prf + "\n" + sql + "\n");

					int rows = getJdbcTemplate().update(sql, args);
					if (rows == 0) {
						throw new Exception(prf + "Errore durante la insert delle tipologie itnervento del QTE");
					}
				} else {
					sql = "UPDATE PBANDI_T_TIPO_INTERV_QTES\r\n"
							+ "SET FLAG_SN = ?, DT_MODIFICA = SYSDATE, ID_UTENTE_AGG = ? \r\n"
							+ "WHERE ID_DATI_QTES = ? AND ID_TIPO_INTERVENTO = ?";
					args = new Object[] { check, idUtente, idDatiQtes, idTipoIntervento };
					LOG.info(prf + " <idDatiQtes>: " + idDatiQtes + ", <idTipoIntervento>: " + idTipoIntervento
							+ ", <flagSN>: " + check + ", <idUtente>: " + idUtente);
					LOG.info(prf + "\n" + sql + "\n");

					int rows = getJdbcTemplate().update(sql, args);
					if (rows == 0) {
						throw new Exception(prf + "Errore durante l'update delle tipologie itnervento del QTE");
					}
				}
			}
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in aggiornaTipiInterventoQte: ", e);
			throw new Exception(" ERRORE in aggiornaTipiInterventoQte.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	private void aggiornaAltriValoriQte(DatiQteDTO datoQte, Integer idDatiQtes, Long idUtente) throws Exception {
		String prf = "[ContoEconomicoWaDAOImpl::aggiornaAltriValoriQte] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " Aggiorno gli altri valori sulla PBANDI_T_ALTRI_VALORI_QTES");
		try {
			for (NumberDescBreve value : datoQte.getAltriValori()) {

				String sql = "SELECT ID_TIPO_VALORE  \r\n" + "FROM PBANDI_D_ALTRI_VALORI_QTES pdavq \r\n"
						+ "WHERE DESC_BREVE_TIPO_VALORE = ?";
				Object[] args = new Object[] { value.getDescBreve() };
				List<Integer> idTipoValoreList = getJdbcTemplate().queryForList(sql, args, Integer.class);
				Integer idTipoValore = null;
				if (idTipoValoreList != null && idTipoValoreList.size() > 0) {
					idTipoValore = idTipoValoreList.get(0);
				}
				LOG.info(prf + " <descBreveSoggettoFinanziatore>: " + value.getDescBreve());
				LOG.info(prf + "\n" + sql + "\n");
				LOG.info(prf + " <idTipoValore>: " + idTipoValore);
				if (idTipoValore == null) {
					throw new InvalidParameterException(
							"descBreveTipoValore '" + value.getDescBreve() + "' non trovata.");
				}

				sql = "SELECT COUNT(*) \r\n" + "FROM PBANDI_T_ALTRI_VALORI_QTES \r\n"
						+ "WHERE ID_DATI_QTES = ? AND ID_TIPO_VALORE = ?";
				args = new Object[] { idDatiQtes, idTipoValore };
				int count = getJdbcTemplate().queryForObject(sql, args, Integer.class);
				LOG.info(prf + "\n" + sql + "\n");
				LOG.info(prf + " <count>: " + count);

				if (count == 0) {
					if (value.getNumber() != null) {
						sql = "INSERT INTO PBANDI_T_ALTRI_VALORI_QTES\r\n"
								+ "(ID_DATI_QTES, ID_TIPO_VALORE, VALORE, DT_INSERIMENTO, ID_UTENTE_INS)\r\n"
								+ "VALUES(?, ?, ?, SYSDATE, ?)";
						args = new Object[] { idDatiQtes, idTipoValore, value.getNumber(), idUtente };
						LOG.info(prf + " <idDatiQtes>: " + idDatiQtes + ", <idTipoValore>: " + idTipoValore
								+ ", <valore>: " + value.getNumber() + ", <idUtente>: " + idUtente);
						LOG.info(prf + "\n" + sql + "\n");

						int rows = getJdbcTemplate().update(sql, args);
						if (rows == 0) {
							throw new Exception(prf + "Errore durante la insert degli altri valori del QTE");
						}
					} else {
						LOG.info(prf + " Valore degli altri valori con descBreveTipoValore " + value.getDescBreve()
								+ " nullo. Non inserisco il record");
					}
				} else {
					sql = "UPDATE PBANDI_T_ALTRI_VALORI_QTES\r\n"
							+ "SET VALORE = ?, DT_MODIFICA = SYSDATE, ID_UTENTE_AGG = ? \r\n"
							+ "WHERE ID_DATI_QTES = ? AND ID_TIPO_VALORE = ?";
					args = new Object[] { value.getNumber(), idUtente, idDatiQtes, idTipoValore };
					LOG.info(prf + " <idDatiQtes>: " + idDatiQtes + ", <idSoggettoFinanziatore>: " + idTipoValore
							+ ", <valore>: " + value.getNumber() + ", <idUtente>: " + idUtente);
					LOG.info(prf + "\n" + sql + "\n");

					int rows = getJdbcTemplate().update(sql, args);
					if (rows == 0) {
						throw new Exception(prf + "Errore durante l'update degli altri valori del QTE");
					}
				}
			}
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in aggiornaAltriValoriQte: ", e);
			throw new Exception(" ERRORE in aggiornaAltriValoriQte.");
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public List<QteFaseDTO> getColonneModelloQte(Long progrBandoLineaIntervento, Long idSoggetto, String codiceRuolo,
			Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getColonneModelloQte] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "progrBandoLineaIntervento = " + progrBandoLineaIntervento + "; idUtente = " + idUtente
				+ "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (progrBandoLineaIntervento == 0) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		List<QteFaseDTO> result = null;
		try {
			String sql;
			sql = "SELECT pdfq.ID_COLONNA_QTES, pdfq.DESC_BREVE_COLONNA_QTES, pdfq.DESC_COLONNA_QTES\r\n"
					+ "FROM PBANDI_D_FASI_QTES pdfq\r\n"
					+ "JOIN PBANDI_R_MODELLO_FASI_QTES prmfq ON prmfq.ID_COLONNA_QTES = pdfq.ID_COLONNA_QTES\r\n"
					+ "JOIN PBANDI_R_MOD_QTES_BAN_LI_INT prmqbli ON prmqbli.ID_MODELLO_QTES = prmfq.ID_MODELLO_QTES \r\n"
					+ "WHERE prmqbli.PROGR_BANDO_LINEA_INTERVENTO = ?\r\n" + "ORDER BY prmfq.ORDINAMENTO ";

			Object[] args = new Object[] { progrBandoLineaIntervento };
			LOG.info("<progrBandoLineaIntervento>: " + progrBandoLineaIntervento);
			LOG.info(prf + "\n" + sql + "\n");

			result = getJdbcTemplate().query(sql, args, new QteFaseRowMapper());
			if (result == null || result.size() == 0) {
				throw new Exception("colonne qte associate al modello non trovate");
			}

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getColonneModelloQte: ", e);
			throw new Exception(" ERRORE in getColonneModelloQte.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public String getDescEnteCompetenza(Long progrBandoLineaIntervento, String descBreveRuoloEnteCompetenza,
			Long idSoggetto, String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getDescEnteCompetenza] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "progrBandoLineaIntervento = " + progrBandoLineaIntervento + "; descBreveRuoloEnteCompetenza = "
				+ descBreveRuoloEnteCompetenza + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);

		if (progrBandoLineaIntervento == 0) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato.");
		}
		if (StringUtils.isBlank(descBreveRuoloEnteCompetenza)) {
			throw new InvalidParameterException("descBreveRuoloEnteCompetenza non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		String result = null;
		try {
			String sql;
			sql = "SELECT  b.DESC_ENTE \r\n"
					+ "FROM PBANDI_R_BANDO_LINEA_ENTE_COMP a, PBANDI_T_ENTE_COMPETENZA b, PBANDI_D_RUOLO_ENTE_COMPETENZA c \r\n"
					+ "WHERE a.PROGR_BANDO_LINEA_INTERVENTO = ? \r\n"
					+ "AND a.ID_ENTE_COMPETENZA = b.ID_ENTE_COMPETENZA \r\n"
					+ "AND c.ID_RUOLO_ENTE_COMPETENZA = a.ID_RUOLO_ENTE_COMPETENZA \r\n"
					+ "AND c.DESC_BREVE_RUOLO_ENTE = ? ";

			Object[] args = new Object[] { progrBandoLineaIntervento, descBreveRuoloEnteCompetenza };
			LOG.info("<progrBandoLineaIntervento>: " + progrBandoLineaIntervento + ", <descBreveRuoloEnteCompetenza>: "
					+ descBreveRuoloEnteCompetenza);
			LOG.info(prf + "\n" + sql + "\n");

			result = getJdbcTemplate().queryForObject(sql, args, String.class);

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getDescEnteCompetenza: ", e);
			throw new Exception(" ERRORE in getDescEnteCompetenza.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public String getDescSettoreEnte(Long progrBandoLineaIntervento, String descBreveRuoloEnteCompetenza,
			Long idSoggetto, String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getDescSettoreEnte] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "progrBandoLineaIntervento = " + progrBandoLineaIntervento + "; descBreveRuoloEnteCompetenza = "
				+ descBreveRuoloEnteCompetenza + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);

		if (progrBandoLineaIntervento == 0) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato.");
		}
		if (StringUtils.isBlank(descBreveRuoloEnteCompetenza)) {
			throw new InvalidParameterException("descBreveRuoloEnteCompetenza non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		String result = null;
		try {
			String sql;
			sql = "SELECT  b.DESC_SETTORE\r\n"
					+ "FROM PBANDI_R_BANDO_LINEA_SETTORE a, PBANDI_D_SETTORE_ENTE b, PBANDI_D_RUOLO_ENTE_COMPETENZA c \r\n"
					+ "WHERE a.PROGR_BANDO_LINEA_INTERVENTO = ? \r\n" + "AND a.ID_SETTORE_ENTE = b.ID_SETTORE_ENTE \r\n"
					+ "AND c.ID_RUOLO_ENTE_COMPETENZA = a.ID_RUOLO_ENTE_COMPETENZA \r\n"
					+ "AND c.DESC_BREVE_RUOLO_ENTE = ? ";

			Object[] args = new Object[] { progrBandoLineaIntervento, descBreveRuoloEnteCompetenza };
			LOG.info("<progrBandoLineaIntervento>: " + progrBandoLineaIntervento + ", <descBreveRuoloEnteCompetenza>: "
					+ descBreveRuoloEnteCompetenza);
			LOG.info(prf + "\n" + sql + "\n");

			result = getJdbcTemplate().queryForObject(sql, args, String.class);

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getDescSettoreEnte: ", e);
			throw new Exception(" ERRORE in getDescSettoreEnte.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public TitoloBandoNomeBandoLineaDTO getTitoloBandoNomeBandoLinea(Long progrBandoLineaIntervento, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getTitoloBandoNomeBandoLinea] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "progrBandoLineaIntervento = " + progrBandoLineaIntervento + "; idUtente = " + idUtente
				+ "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (progrBandoLineaIntervento == 0) {
			throw new InvalidParameterException("progrBandoLineaIntervento non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		TitoloBandoNomeBandoLineaDTO result = null;
		try {
			String sql;
			sql = "SELECT b.TITOLO_BANDO, bli.NOME_BANDO_LINEA\r\n" + "FROM PBANDI_T_BANDO b\r\n"
					+ "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bli ON bli.ID_BANDO = b.ID_BANDO \r\n"
					+ "WHERE bli.PROGR_BANDO_LINEA_INTERVENTO  = ? ";

			Object[] args = new Object[] { progrBandoLineaIntervento };
			LOG.info("<progrBandoLineaIntervento>: " + progrBandoLineaIntervento);
			LOG.info(prf + "\n" + sql + "\n");

			result = getJdbcTemplate().queryForObject(sql, args, new TitoloBandoNomeBandoLineaRowMapper());

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getTitoloBandoNomeBandoLinea: ", e);
			throw new Exception(" ERRORE in getTitoloBandoNomeBandoLinea.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public CupProgettoNumeroDomandaDTO getCupProgettoNumeroDomanda(Long idProgetto, Long idSoggetto, String codiceRuolo,
			Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getCupProgettoNumeroDomanda] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idSoggetto = " + idSoggetto
				+ "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = " + idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		CupProgettoNumeroDomandaDTO result = null;
		try {
			String sql;
			sql = "SELECT p.CUP, d.NUMERO_DOMANDA\r\n" + "FROM PBANDI_T_PROGETTO p\r\n"
					+ "JOIN PBANDI_T_DOMANDA d ON d.ID_DOMANDA = p.ID_DOMANDA \r\n" + "WHERE ID_PROGETTO = ? ";

			Object[] args = new Object[] { idProgetto };
			LOG.info("<idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			result = getJdbcTemplate().queryForObject(sql, args, new CupProgettoNumeroDomandaRowMapper());

		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getCupProgettoNumeroDomanda: ", e);
			throw new Exception(" ERRORE in getCupProgettoNumeroDomanda.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public ComuneProvinciaSedeDTO getComuneProvinciaSede(Long idProgetto, String descBreveTipoSede, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getComuneProvinciaSede] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; descBreveTipoSede = " + descBreveTipoSede + "; idUtente = "
				+ idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (StringUtils.isBlank(descBreveTipoSede)) {
			throw new InvalidParameterException("descBreveTipoSede non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		ComuneProvinciaSedeDTO result = null;
		try {
			String sql;
			sql = "select distinct DESC_PROVINCIA as descProvincia, DESC_COMUNE as descComune\r\n" + "from (\r\n"
					+ "select distinct \r\n" + "      rsp.id_progetto\r\n" + "/* tipo sede */      \r\n"
					+ "      ,ts.desc_breve_tipo_sede\r\n" + "/* provincia */\r\n"
					+ "        ,nvl2( i.id_provincia, \r\n" + "              ( select desc_provincia\r\n"
					+ "                 from pbandi_d_provincia\r\n"
					+ "                where id_provincia = i.id_provincia \r\n"
					+ "                  and  nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)),\r\n"
					+ "              nvl2( i.id_comune_estero,\r\n" + "                     null,\r\n"
					+ "                     (  select b.desc_provincia\r\n"
					+ "                        from pbandi_d_comune a, pbandi_d_provincia b\r\n"
					+ "                        where a.id_comune = i.id_comune\r\n"
					+ "                          and b.id_provincia = a.id_provincia\r\n"
					+ "                          AND NVL(TRUNC(a.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)\r\n"
					+ "                          AND NVL(TRUNC(b.dt_fine_validita), TRUNC(sysdate +1)) > TRUNC(sysdate)\r\n"
					+ "                     )\r\n" + "                )\r\n" + "        ) as desc_provincia\r\n"
					+ "/* comune */ \r\n" + "        ,nvl2( i.id_comune, \r\n"
					+ "              ( select desc_comune\r\n" + "                 from pbandi_d_comune\r\n"
					+ "                where id_comune = i.id_comune \r\n"
					+ "                  and  nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)),\r\n"
					+ "              nvl2( i.id_comune_estero,\r\n"
					+ "                    ( select desc_comune_estero\r\n"
					+ "                        from pbandi_d_comune_estero\r\n"
					+ "                       where id_comune_estero = i.id_comune_estero \r\n"
					+ "                         and  nvl(trunc(dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)),\r\n"
					+ "                   null\r\n" + "              )\r\n" + "        ) as desc_comune\r\n"
					+ "from pbandi_r_sogg_progetto_sede rsps --non nella select\r\n"
					+ "     ,pbandi_r_soggetto_progetto rsp\r\n" + "     , pbandi_t_indirizzo i\r\n"
					+ "     , pbandi_t_recapiti r\r\n" + "     , pbandi_t_sede s\r\n"
					+ "     , pbandi_d_tipo_sede ts\r\n"
					+ "where rsps.progr_soggetto_progetto = rsp.progr_soggetto_progetto \r\n"
					+ "  and rsps.id_tipo_sede = ts.id_tipo_sede\r\n"
					+ "  and rsp.ID_TIPO_ANAGRAFICA = (select dta.id_tipo_anagrafica from pbandi_d_tipo_anagrafica dta where dta.desc_breve_tipo_anagrafica = 'BENEFICIARIO')\r\n"
					+ "  and nvl(rsp.id_tipo_beneficiario, '-1') <> (select dtb.id_tipo_beneficiario from pbandi_d_tipo_beneficiario dtb where dtb.desc_breve_tipo_beneficiario = 'BEN-ASSOCIATO')\r\n"
					+ "  and rsps.id_indirizzo = i.id_indirizzo (+)\r\n"
					+ "  and rsps.id_recapiti = r.id_recapiti (+)\r\n" + "  and rsps.id_sede = s.id_sede\r\n"
					+ "  /* vengono considerate valide le sedi con dt_fine_validita non valorizzata */\r\n"
					+ "  and s.dt_fine_validita is null\r\n"
					+ "  and nvl(trunc(rsp.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)\r\n"
					+ "  and nvl(trunc(ts.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)\r\n"
					+ "  and nvl(trunc(r.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)\r\n"
					+ "  and nvl(trunc(i.dt_fine_validita), trunc(sysdate +1)) > trunc(sysdate)\r\n"
					+ ") where DESC_BREVE_TIPO_SEDE = ? and ID_PROGETTO = ?";

			Object[] args = new Object[] { descBreveTipoSede, idProgetto };
			LOG.info("<descBreveTipoSede>: " + descBreveTipoSede + ", <idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<ComuneProvinciaSedeDTO> dtos = getJdbcTemplate().query(sql, args, new ComuneProvinciaSedeRowMapper());
			if (dtos != null && dtos.size() > 0) {
				result = dtos.get(0);
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getComuneProvinciaSede: ", e);
			throw new Exception(" ERRORE in getCupProgettoNumeroDomanda.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public DtFineEffEstremiAttoDTO getDtFineEffEstremiAtto(Long idProgetto, String codIgrueFaseMonit, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getDtFineEffEstremiAtto] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto + "; codIgrueFaseMonit = " + codIgrueFaseMonit + "; idUtente = "
				+ idUtente + "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (StringUtils.isBlank(codIgrueFaseMonit)) {
			throw new InvalidParameterException("codIgrueFaseMonit non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		DtFineEffEstremiAttoDTO result = null;
		try {
			String sql;
			sql = "SELECT t.DT_FINE_EFFETTIVA, t.ESTREMI_ATTO_AMMINISTRATIVO\r\n"
					+ "FROM PBANDI_R_PROGETTO_FASE_MONIT t, PBANDI_D_FASE_MONIT fm\r\n" + "WHERE t.ID_PROGETTO = ? \r\n"
					+ "AND t.ID_FASE_MONIT = fm.ID_FASE_MONIT\r\n" + "AND  fm.COD_IGRUE_T35 = ?";

			Object[] args = new Object[] { idProgetto, codIgrueFaseMonit };
			LOG.info("<codIgrueFaseMonit>: " + codIgrueFaseMonit + ", <idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<DtFineEffEstremiAttoDTO> dtos = getJdbcTemplate().query(sql, args,
					new DtFineEffEstremiAttoRowMapper());
			if (dtos != null && dtos.size() > 0) {
				result = dtos.get(0);
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getDtFineEffEstremiAtto: ", e);
			throw new Exception(" ERRORE in getDtFineEffEstremiAtto.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public Boolean salvaCCCDefinitivo(MultipartFormDataInput multipartFormData, Long idUtente, String codiceRuolo,
			String idIride) throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::salvaCCCDefinitivo] ";
		LOG.info(prf + "BEGIN");

		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato");
		}
		if (multipartFormData == null) {
			throw new InvalidParameterException("multipartFormData non valorizzato");
		}

		Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
		Long idQtesHtmlProgetto = multipartFormData.getFormDataPart("idQtesHtmlProgetto", Long.class, null);

		LOG.info(prf + "input idProgetto       = " + idProgetto);

		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato");
		}
		if (idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato");
		}
		if (idQtesHtmlProgetto == null) {
			throw new InvalidParameterException("idQtesHtmlProgetto non valorizzato");
		}

		Boolean esito = true;
		try {

			// Legge il file dal multipart.
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

			it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart,
					null);
			if (file == null) {
				throw new InvalidParameterException("File non valorizzato");
			}

			LOG.info(prf + "input nomeFile = " + file.getNomeFile());
			LOG.info(prf + "input bytes.length = " + file.getBytes().length);

			String descBreveTipoDocIndex = Constants.DESC_BREVE_TIPO_DOC_INDEX_CCC;

			BigDecimal idTipoDocIndex = decodificheDAOImpl.idDaDescrizione("PBANDI_C_TIPO_DOCUMENTO_INDEX",
					"ID_TIPO_DOCUMENTO_INDEX", "DESC_BREVE_TIPO_DOC_INDEX", descBreveTipoDocIndex);
			if (idTipoDocIndex == null)
				throw new Exception("Tipo documento index non trovato.");

			String descBreveStatoDocIndexGenerato = Constants.DESC_BREVE_STATO_DOCUMENTO_INDEX_GENERATO;

			BigDecimal idStatoDocIndexGenerato = decodificheDAOImpl.idDaDescrizione("PBANDI_D_STATO_DOCUMENTO_INDEX",
					"ID_STATO_DOCUMENTO", "DESC_BREVE", descBreveStatoDocIndexGenerato);
			if (idStatoDocIndexGenerato == null)
				throw new Exception("Stato documento index non trovato.");

			Date currentDate = new Date(System.currentTimeMillis());

			PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocIndex);
			documentoIndexVO.setNomeFile(file.getNomeFile());
			documentoIndexVO.setIdProgetto(new BigDecimal(idProgetto));
			documentoIndexVO.setDtInserimentoIndex(new java.sql.Date(currentDate.getTime()));
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO.setIdEntita(new BigDecimal(Constants.ID_ENTITA_QTES_HTML_PROGETTO)); // PBANDI_T_QTES_HTML_PROGETTO
			documentoIndexVO.setIdTarget(new BigDecimal(idQtesHtmlProgetto)); // id della PBANDI_T_QTES_HTML_PROGETTO
			documentoIndexVO.setRepository(descBreveTipoDocIndex);
			documentoIndexVO.setIdStatoDocumento(idStatoDocIndexGenerato);
			documentoIndexVO.setUuidNodo("UUID");

			// Salvo indexVO su db e il file su filesystem; aggiorno le visivilita'.
			documentoManager.salvaFile(file.getBytes(), documentoIndexVO);
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
	public Long getIdDocumentoIndexCCC(Long idProgetto, Long idQtesHtmlProgetto, String descBreveTipoDocIndex,
			Long idSoggetto, String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception {
		String prf = "[ContoEconomicoWaDAOImpl::getIdDocumentoIndexCCC] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idQtesHtmlProgetto = " + idQtesHtmlProgetto + "; descBreveTipoDocIndex = "
				+ descBreveTipoDocIndex + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente
				+ "; idSoggetto = " + idSoggetto + "; codiceRuolo = " + codiceRuolo + "; idBeneficiario = "
				+ idBeneficiario);

		if (idQtesHtmlProgetto == 0) {
			throw new InvalidParameterException("idQtesHtmlProgetto non valorizzato.");
		}
		if (idProgetto == 0) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		if (StringUtils.isBlank(descBreveTipoDocIndex)) {
			throw new InvalidParameterException("descBreveTipoDocIndex non valorizzato.");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("idSoggetto non valorizzato.");
		}
		if (StringUtils.isBlank(codiceRuolo)) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (StringUtils.isBlank(idIride)) {
			throw new InvalidParameterException("idIride non valorizzato.");
		}
		if (idBeneficiario == null) {
			throw new InvalidParameterException("idBeneficiario non valorizzato.");
		}

		Long result = null;
		try {
			String sql;
			sql = "SELECT di.ID_DOCUMENTO_INDEX\r\n" + "FROM PBANDI_T_DOCUMENTO_INDEX di\r\n"
					+ "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tdi ON tdi.ID_TIPO_DOCUMENTO_INDEX = di.ID_TIPO_DOCUMENTO_INDEX \r\n"
					+ "WHERE di.ID_TARGET = ? AND tdi.DESC_BREVE_TIPO_DOC_INDEX = ? AND di.ID_PROGETTO = ?";

			Object[] args = new Object[] { idQtesHtmlProgetto, descBreveTipoDocIndex, idProgetto };
			LOG.info("<idQtesHtmlProgetto>: " + idQtesHtmlProgetto + ", <descBreveTipoDocIndex>: "
					+ descBreveTipoDocIndex + ", <idProgetto>: " + idProgetto);
			LOG.info(prf + "\n" + sql + "\n");

			List<Long> dtos = getJdbcTemplate().queryForList(sql, args, Long.class);
			if (dtos != null && dtos.size() > 0) {
				result = dtos.get(0);
			}
		} catch (InvalidParameterException e) {
			LOG.error(prf + e.getMessage());
			throw e;
		} catch (Exception e) {
			LOG.error(prf + " ERRORE in getIdDocumentoIndexCCC: ", e);
			throw new Exception(" ERRORE in getIdDocumentoIndexCCC.");
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

}
