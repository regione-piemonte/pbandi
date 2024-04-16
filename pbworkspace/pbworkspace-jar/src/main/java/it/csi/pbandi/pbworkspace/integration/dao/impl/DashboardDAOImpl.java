/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao.impl;

import java.security.InvalidParameterException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.csi.pbandi.pbworkspace.dto.BandoWidgetDTO;
import it.csi.pbandi.pbworkspace.dto.EsitoOperazioneDTO;
import it.csi.pbandi.pbworkspace.dto.WidgetDTO;
import it.csi.pbandi.pbworkspace.exception.DaoException;
import it.csi.pbandi.pbworkspace.exception.ErroreGestitoException;
import it.csi.pbandi.pbworkspace.exception.RecordNotFoundException;
import it.csi.pbandi.pbworkspace.integration.dao.DashboardDAO;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.BandoWidgetDTORowMapper;
import it.csi.pbandi.pbworkspace.integration.dao.impl.rowmapper.WidgetDTORowMapper;
import it.csi.pbandi.pbworkspace.util.Constants;

@Service
public class DashboardDAOImpl extends JdbcDaoSupport implements DashboardDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public DashboardDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public DashboardDAOImpl() {
	}

	@Override
	public Boolean isDashboardVisible(String codiceRuolo, Long idSoggetto, Long idUtente, String idIride)
			throws InvalidParameterException, DaoException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente
				+ ", idIride=" + idIride);

		if (idUtente == null) {
			throw new InvalidParameterException("variabile idUtente non valorizzata");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("variabile idSoggetto non valorizzata");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("variabile idIride non valorizzata");
		}
		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			throw new InvalidParameterException("variabile codiceRuolo non valorizzata");
		}

		Boolean result = Boolean.FALSE;
		try {

			String sql = "SELECT COUNT(*)\r\n" + "FROM PBANDI_R_PERMESSO_TIPO_ANAGRAF pta\r\n"
					+ "JOIN PBANDI_D_PERMESSO p ON p.ID_PERMESSO = pta.ID_PERMESSO \r\n"
					+ "JOIN PBANDI_D_TIPO_ANAGRAFICA ta ON ta.ID_TIPO_ANAGRAFICA = pta.ID_TIPO_ANAGRAFICA\r\n"
					+ "WHERE p.DESC_BREVE_PERMESSO = ? AND ta.DESC_BREVE_TIPO_ANAGRAFICA = ?";

			LOG.info(sql);
			LOG.info(prf + "<descBrevePermesso>: " + Constants.DESC_BREVE_PERMESSO_DASHBOARD
					+ ", <descBreveTipoAnagrafica>: " + codiceRuolo);

			int count = getJdbcTemplate().queryForObject(sql,
					new Object[] { Constants.DESC_BREVE_PERMESSO_DASHBOARD, codiceRuolo }, Integer.class);

			LOG.info(prf + " count = " + count);
			if (count > 0) {
				result = Boolean.TRUE;
			}

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception", e);
			throw new ErroreGestitoException("DaoException", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public Boolean areWidgetsConfigured(String codiceRuolo, Long idSoggetto, Long idUtente, String idIride)
			throws InvalidParameterException, DaoException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente
				+ ", idIride=" + idIride);

		if (idUtente == null) {
			throw new InvalidParameterException("variabile idUtente non valorizzata");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("variabile idSoggetto non valorizzata");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("variabile idIride non valorizzata");
		}
		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			throw new InvalidParameterException("variabile codiceRuolo non valorizzata");
		}

		Boolean result = Boolean.FALSE;
		try {

			String sql = "SELECT COUNT(*)\r\n" + "FROM PBANDI_R_SOGGETTO_WIDGET sw\r\n" + "WHERE ID_SOGGETTO = ?";

			LOG.info(sql);
			LOG.info(prf + "<idSoggetto>: " + idSoggetto);

			int count = getJdbcTemplate().queryForObject(sql, new Object[] { idSoggetto }, Integer.class);

			LOG.info(prf + " count = " + count);
			if (count > 0) {
				result = Boolean.TRUE;
			}

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception", e);
			throw new ErroreGestitoException("DaoException", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public List<WidgetDTO> getWidgets(String codiceRuolo, Long idSoggetto, Long idUtente, String idIride) {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente
				+ ", idIride=" + idIride);

		if (idUtente == null) {
			throw new InvalidParameterException("variabile idUtente non valorizzata");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("variabile idSoggetto non valorizzata");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("variabile idIride non valorizzata");
		}
		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			throw new InvalidParameterException("variabile codiceRuolo non valorizzata");
		}

		List<WidgetDTO> result = null;
		try {

			String sql = "SELECT w.ID_WIDGET, w.DESC_BREVE_WIDGET, w.DESC_WIDGET, w.TITOLO_WIDGET, w.FLAG_MODIFICA, sw.FLAG_WIDGET_ATTIVO\r\n"
					+ "FROM PBANDI_D_WIDGET w \r\n"
					+ "LEFT JOIN PBANDI_R_SOGGETTO_WIDGET sw ON sw.ID_WIDGET = w.ID_WIDGET AND sw.ID_SOGGETTO = ?\r\n"
					+ "WHERE TRUNC(w.DT_INIZIO_VALIDITA) <= TRUNC(SYSDATE) \r\n"
					+ "	AND (TRUNC(w.DT_FINE_VALIDITA) IS NULL OR TRUNC(w.DT_FINE_VALIDITA) >=TRUNC(SYSDATE))";

			LOG.info(sql);
			LOG.info(prf + "<idSoggetto>: " + idSoggetto);

			result = getJdbcTemplate().query(sql, new Object[] { idSoggetto }, new WidgetDTORowMapper());

			LOG.info(prf + " risultati trovati = " + (result == null ? 0 : result.size()));

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception", e);
			throw new ErroreGestitoException("DaoException", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public List<BandoWidgetDTO> getBandiDaAssociare(String codiceRuolo, Long idSoggetto, Long idUtente, String idIride)
			throws InvalidParameterException, DaoException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente
				+ ", idIride=" + idIride);

		if (idUtente == null) {
			throw new InvalidParameterException("variabile idUtente non valorizzata");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("variabile idSoggetto non valorizzata");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("variabile idIride non valorizzata");
		}
		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			throw new InvalidParameterException("variabile codiceRuolo non valorizzata");
		}

		List<BandoWidgetDTO> result = null;
		try {

			String sql = "SELECT distinct NOMEBANDOLINEA, PROGRBANDOLINEAINTERVENTO\r\n" + "FROM( \r\n"
					+ "	SELECT DISTINCT PBANDI_R_BANDO_LINEA_INTERVENT.NOME_BANDO_LINEA nomeBandoLinea, \r\n"
					+ "	PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO progrBandoLineaIntervento\r\n"
					+ "	FROM PBANDI_R_BANDO_LINEA_INTERVENT \r\n"
					+ "	JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP ON (PBANDI_R_BANDO_LINEA_INTERVENT.PROGR_BANDO_LINEA_INTERVENTO=PBANDI_R_BANDO_LINEA_ENTE_COMP.PROGR_BANDO_LINEA_INTERVENTO) \r\n"
					+ "	JOIN PBANDI_R_ENTE_COMPETENZA_SOGG ON (PBANDI_R_BANDO_LINEA_ENTE_COMP.ID_ENTE_COMPETENZA=PBANDI_R_ENTE_COMPETENZA_SOGG.ID_ENTE_COMPETENZA \r\n"
					+ "											AND PBANDI_R_BANDO_LINEA_ENTE_COMP.DT_FINE_VALIDITA IS NULL \r\n"
					+ "											AND PBANDI_R_ENTE_COMPETENZA_SOGG.DT_FINE_VALIDITA IS NULL) \r\n"
					+ "	JOIN PBANDI_T_DOMANDA ON (PBANDI_R_BANDO_LINEA_ENTE_COMP.PROGR_BANDO_LINEA_INTERVENTO=PBANDI_T_DOMANDA.PROGR_BANDO_LINEA_INTERVENTO) \r\n"
					+ "	WHERE ID_SOGGETTO = ?  \r\n" + "	) \r\n" + "ORDER BY NOMEBANDOLINEA";

			LOG.info(sql);
			LOG.info(prf + "<idSoggetto>: " + idSoggetto);

			result = getJdbcTemplate().query(sql, new Object[] { idSoggetto }, new BandoWidgetDTORowMapper());

			LOG.info(prf + " risultati trovati = " + (result == null ? 0 : result.size()));

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception", e);
			throw new ErroreGestitoException("DaoException", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public List<BandoWidgetDTO> getBandiAssociati(Long idWidget, String codiceRuolo, Long idSoggetto, Long idUtente,
			String idIride) throws InvalidParameterException, DaoException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente
				+ ", idIride=" + idIride);

		if (idWidget == null) {
			throw new InvalidParameterException("variabile idWidget non valorizzata");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("variabile idUtente non valorizzata");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("variabile idSoggetto non valorizzata");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("variabile idIride non valorizzata");
		}
		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			throw new InvalidParameterException("variabile codiceRuolo non valorizzata");
		}

		List<BandoWidgetDTO> result = null;
		try {

			String sql = "SELECT blsw.ID_BANDO_LIN_SOGG_WIDGET AS IDBANDOLINSOGGWIDGET, bli.PROGR_BANDO_LINEA_INTERVENTO AS PROGRBANDOLINEAINTERVENTO , bli.NOME_BANDO_LINEA AS NOMEBANDOLINEA\r\n"
					+ "FROM PBANDI_R_BANDO_LINEA_INTERVENT bli\r\n"
					+ "JOIN PBANDI_R_BANDO_LIN_SOGG_WIDGET blsw ON bli.PROGR_BANDO_LINEA_INTERVENTO = blsw.PROGR_BANDO_LINEA_INTERVENTO \r\n"
					+ "JOIN PBANDI_R_SOGGETTO_WIDGET sw ON sw.ID_SOGGETTO_WIDGET = blsw.ID_SOGGETTO_WIDGET \r\n"
					+ "WHERE sw.ID_SOGGETTO = ? AND sw.ID_WIDGET = ?";

			LOG.info(sql);
			LOG.info(prf + "<idSoggetto>: " + idSoggetto + ", <idWidget>: " + idWidget);

			result = getJdbcTemplate().query(sql, new Object[] { idSoggetto, idWidget }, new BandoWidgetDTORowMapper());

			LOG.info(prf + " risultati trovati = " + (result == null ? 0 : result.size()));

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception", e);
			throw new ErroreGestitoException("DaoException", e);
		} finally {
			LOG.info(prf + " END");
		}
		return result;
	}

	@Override
	public EsitoOperazioneDTO changeWidgetAttivo(Long idWidget, Boolean flagWidgetAttivo, String codiceRuolo,
			Long idSoggetto, Long idUtente, String idIride) throws InvalidParameterException, DaoException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente
				+ ", idIride=" + idIride);

		if (idWidget == null) {
			throw new InvalidParameterException("variabile idWidget non valorizzata");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("variabile idUtente non valorizzata");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("variabile idSoggetto non valorizzata");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("variabile idIride non valorizzata");
		}
		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			throw new InvalidParameterException("variabile codiceRuolo non valorizzata");
		}

		EsitoOperazioneDTO esito = new EsitoOperazioneDTO();
		esito.setEsito(Boolean.FALSE);

		try {

			String sql = "SELECT ID_SOGGETTO_WIDGET\r\n" + "FROM PBANDI_R_SOGGETTO_WIDGET \r\n"
					+ "WHERE ID_SOGGETTO = ? AND ID_WIDGET = ?";

			LOG.info(sql);
			LOG.info(prf + "<idSoggetto>: " + idSoggetto + ", <idWidget>: " + idWidget);

			List<Long> idSoggettoWidgets = getJdbcTemplate().queryForList(sql, new Object[] { idSoggetto, idWidget },
					Long.class);

			if (idSoggettoWidgets != null && idSoggettoWidgets.size() > 0) {
				Long idSoggettoWidget = idSoggettoWidgets.get(0);
				LOG.info(prf + "<idSoggettoWidget>: " + idSoggettoWidget);
				// UPDATE
				sql = "UPDATE PBANDI.PBANDI_R_SOGGETTO_WIDGET\r\n"
						+ "SET FLAG_WIDGET_ATTIVO = ?, ID_UTENTE_AGG = ?, DT_AGGIORNAMENTO = SYSDATE\r\n"
						+ "WHERE ID_SOGGETTO_WIDGET = ?";

				LOG.info(sql);
				LOG.info(prf + "<flagWidgetAttivo>: " + (flagWidgetAttivo.equals(Boolean.TRUE) ? "S" : "N")
						+ ", <idUtenteAgg>: " + idUtente + ", <idSoggettoWidget>: " + idSoggettoWidget);

				int rows = getJdbcTemplate().update(sql, new Object[] {
						(flagWidgetAttivo.equals(Boolean.TRUE) ? "S" : "N"), idUtente, idSoggettoWidget });
				if (rows > 0) {
					esito.setEsito(Boolean.TRUE);
				}
			} else {
				LOG.info(prf + " idSoggettoWidget non trovato");
				// INSERT
				sql = "INSERT INTO PBANDI.PBANDI_R_SOGGETTO_WIDGET\r\n"
						+ "(ID_SOGGETTO_WIDGET, ID_SOGGETTO, ID_WIDGET, FLAG_WIDGET_ATTIVO, ID_UTENTE_INS, DT_INSERIMENTO, ID_UTENTE_AGG, DT_AGGIORNAMENTO)\r\n"
						+ "VALUES(SEQ_PBANDI_R_SOGGETTO_WIDGET.nextval, ?, ?, ?, ?, SYSDATE, NULL, NULL)";

				LOG.info(sql);
				LOG.info(prf + "<idSoggetto>: " + idSoggetto + ", <idWidget>: " + idWidget + ", <flagWidgetAttivo>: "
						+ (flagWidgetAttivo.equals(Boolean.TRUE) ? "S" : "N") + ", <idUtenteIns>: " + idUtente);

				int rows = getJdbcTemplate().update(sql, new Object[] { idSoggetto, idWidget,
						(flagWidgetAttivo.equals(Boolean.TRUE) ? "S" : "N"), idUtente });
				if (rows > 0) {
					esito.setEsito(Boolean.TRUE);
				}
			}

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception", e);
			throw new ErroreGestitoException("DaoException", e);
		} finally {
			LOG.info(prf + " END");
		}
		return esito;
	}

	@Override
	public EsitoOperazioneDTO associaBandoAWidget(Long idWidget, Long progrBandoLineaIntervento, String codiceRuolo,
			Long idSoggetto, Long idUtente, String idIride) throws InvalidParameterException, DaoException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente
				+ ", idIride=" + idIride);

		if (idWidget == null) {
			throw new InvalidParameterException("variabile idWidget non valorizzata");
		}
		if (progrBandoLineaIntervento == null) {
			throw new InvalidParameterException("variabile progrBandoLineaIntervento non valorizzata");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("variabile idUtente non valorizzata");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("variabile idSoggetto non valorizzata");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("variabile idIride non valorizzata");
		}
		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			throw new InvalidParameterException("variabile codiceRuolo non valorizzata");
		}

		EsitoOperazioneDTO esito = new EsitoOperazioneDTO();
		esito.setEsito(Boolean.FALSE);

		try {

			String sql = "SELECT ID_SOGGETTO_WIDGET\r\n" + "FROM PBANDI_R_SOGGETTO_WIDGET \r\n"
					+ "WHERE ID_SOGGETTO = ? AND ID_WIDGET = ?";

			LOG.info(sql);
			LOG.info(prf + "<idSoggetto>: " + idSoggetto + ", <idWidget>: " + idWidget);

			List<Long> idSoggettoWidgets = getJdbcTemplate().queryForList(sql, new Object[] { idSoggetto, idWidget },
					Long.class);

			if (idSoggettoWidgets != null && idSoggettoWidgets.size() > 0) {
				Long idSoggettoWidget = idSoggettoWidgets.get(0);
				LOG.info(prf + "<idSoggettoWidget>: " + idSoggettoWidget);
				// INSERT
				sql = "INSERT INTO PBANDI_R_BANDO_LIN_SOGG_WIDGET\r\n"
						+ "(ID_BANDO_LIN_SOGG_WIDGET, ID_SOGGETTO_WIDGET, PROGR_BANDO_LINEA_INTERVENTO, ID_UTENTE_INS, DT_INSERIMENTO)\r\n"
						+ "VALUES(SEQ_PBANDI_R_BAN_LIN_SOGG_WID.nextval, ?, ?, ?, SYSDATE)";

				LOG.info(sql);
				LOG.info(prf + "<idSoggettoWidget>: " + idSoggettoWidget + ", <progrBandoLineaIntervento>: "
						+ progrBandoLineaIntervento + ", <idUtenteIns>: " + idUtente);

				int rows = getJdbcTemplate().update(sql,
						new Object[] { idSoggettoWidget, progrBandoLineaIntervento, idUtente });
				if (rows > 0) {
					esito.setEsito(Boolean.TRUE);
				}
			} else {
				LOG.error(prf + " idSoggettoWidget non trovato.");
				throw new Exception("idSoggettoWidget non trovato.");
			}

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception", e);
			throw new ErroreGestitoException("DaoException", e);
		} finally {
			LOG.info(prf + " END");
		}
		return esito;
	}

	@Override
	public EsitoOperazioneDTO disassociaBandoAWidget(Long idBandoLinSoggWidget, String codiceRuolo, Long idSoggetto,
			Long idUtente, String idIride) throws InvalidParameterException, DaoException, Exception {
		String className = Thread.currentThread().getStackTrace()[1].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String prf = "[" + className + "::" + methodName + "]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + " codiceRuolo=" + codiceRuolo + ", idSoggetto=" + idSoggetto + ", idUtente=" + idUtente
				+ ", idIride=" + idIride);

		if (idBandoLinSoggWidget == null) {
			throw new InvalidParameterException("variabile idBandoLinSoggWidget non valorizzata");
		}
		if (idUtente == null) {
			throw new InvalidParameterException("variabile idUtente non valorizzata");
		}
		if (idSoggetto == null) {
			throw new InvalidParameterException("variabile idSoggetto non valorizzata");
		}
		if (idIride == null || idIride.isEmpty()) {
			throw new InvalidParameterException("variabile idIride non valorizzata");
		}
		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			throw new InvalidParameterException("variabile codiceRuolo non valorizzata");
		}

		EsitoOperazioneDTO esito = new EsitoOperazioneDTO();
		esito.setEsito(Boolean.FALSE);
		try {

			String sql = "DELETE FROM PBANDI_R_BANDO_LIN_SOGG_WIDGET\r\n" + "WHERE ID_BANDO_LIN_SOGG_WIDGET = ?";

			LOG.info(sql);
			LOG.info(prf + "<idBandoLinSoggWidget>: " + idBandoLinSoggWidget);

			int rows = getJdbcTemplate().update(sql, new Object[] { idBandoLinSoggWidget });

			if (rows > 0) {
				esito.setEsito(Boolean.TRUE);
			}

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception", e);
			throw new ErroreGestitoException("DaoException", e);
		} finally {
			LOG.info(prf + " END");
		}
		return esito;
	}

}
