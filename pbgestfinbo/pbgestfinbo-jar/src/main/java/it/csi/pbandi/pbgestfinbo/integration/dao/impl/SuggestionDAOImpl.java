/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestionDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.*;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



@Service
public class SuggestionDAOImpl extends JdbcDaoSupport implements SuggestionDAO {
	private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	public SuggestionDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<ProgettoSuggestVO> suggestProgetto(String codiceProgetto, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestProgetto] ";
		LOG.info(prf + " BEGIN");

		List<ProgettoSuggestVO> progettoSuggestVOList = new ArrayList<>();

		try{
			String sql =
					"SELECT DISTINCT \n" +
					"progetto.id_progetto AS idProgetto, \n" +
					"progetto.codice_visualizzato AS codiceVisualizzato \n" +
					"FROM PBANDI_T_PROGETTO progetto \n" +
					"WHERE \n" +
					"UPPER(progetto.codice_visualizzato) LIKE CONCAT(CONCAT('%',upper(?)),'%') " +
					"AND rownum < 100 "
					;

			LOG.debug(sql);

			progettoSuggestVOList = getJdbcTemplate().query(
					sql,
					ps -> ps.setString(1, codiceProgetto.toUpperCase()),
					new ProgettoSuggestVORowMapper()
			);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read ProgettoSuggestVO", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read ProgettoSuggestVO", e);
			throw new DaoException("DaoException while trying to read ProgettoSuggestVO", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return progettoSuggestVOList;
	}

	@Override
	public List<DenominazioneSuggestVO> suggestDenominazione(String denominazione, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestDenominazione] ";
		LOG.info(prf + " BEGIN");

		List<DenominazioneSuggestVO> denominazioneSuggestVOList = new ArrayList<>();

		try{
			String sql =
					"WITH lastSoggProg AS (\n" +
							"\tSELECT max(soggettoProgetto.progr_soggetto_progetto) AS maxProgrSoggProg\n" +
							"\tFROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto\n" +
							"\tGROUP BY soggettoProgetto.id_soggetto\n" +
							")\n" +
							"SELECT DISTINCT \n" +
							"soggetto.id_soggetto AS idSoggetto, \n" +
							"(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazione,\n" +
							"soggetto.codice_fiscale_soggetto AS codiceFiscaleSoggetto \n" +
							"FROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto \n" +
							"JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto\n" +
							"LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
							"LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = soggettoProgetto.id_persona_fisica\n" +
							"JOIN lastSoggProg ON lastSoggProg.maxProgrSoggProg = soggettoProgetto.progr_soggetto_progetto\n" +
							"WHERE UPPER(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) LIKE CONCAT('%', CONCAT(?,'%'))\n" +
							"AND soggettoProgetto.id_tipo_anagrafica = 1 AND soggettoProgetto.id_tipo_beneficiario <> 4"
					;

			LOG.debug(sql);

			denominazioneSuggestVOList = getJdbcTemplate().query(
					sql,
					ps -> ps.setString(1, denominazione.toUpperCase()),
					new DenominazioneSuggestVORowMapper()
			);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read DenominazioneSuggestVO", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DenominazioneSuggestVO", e);
			throw new DaoException("DaoException while trying to read DenominazioneSuggestVO", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return denominazioneSuggestVOList;
	}

	@Override
	public List<ProgettoSuggestVO> suggestProgetto(String codiceProgetto, Long idBando, HttpServletRequest req) throws DaoException {
			String prf = "[SuggestionDAOImpl::suggestProgetto] ";
			LOG.info(prf + " BEGIN");

			List<ProgettoSuggestVO> progettoSuggestVOList = new ArrayList<>();

			try{

				String sql =
						"SELECT DISTINCT \n" +
								"progetto.id_progetto AS idProgetto, \n" +
								"progetto.codice_visualizzato AS codiceVisualizzato \n" +
								"FROM PBANDI_T_PROGETTO progetto \n" +
								"WHERE id_domanda IN \n" +
								"( \n" +
								"\tSELECT domanda.id_domanda \n" +
								"\tFROM PBANDI_T_DOMANDA domanda \n" +
								"\tWHERE domanda.progr_bando_linea_intervento IN \n" +
								"\t( \n" +
								"\t\tSELECT bandoLineaIntervento.progr_bando_linea_intervento \n" +
								"\t\tFROM PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento WHERE id_bando = ? \n" +
								"\t) \n" +
								") \n" +
								"AND \n" +
								"progetto.codice_visualizzato LIKE CONCAT(CONCAT('%',?),'%') " +
								"AND rownum < 100 "
						;

				LOG.debug(sql);

				progettoSuggestVOList = getJdbcTemplate().query(
						sql,
						ps -> {
							ps.setLong(1, idBando);
							ps.setString(2, codiceProgetto);
						},
						new ProgettoSuggestVORowMapper()
				);
			} catch (IncorrectResultSizeDataAccessException e) {
				LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read ProgettoSuggestVO", e);

			} catch (Exception e) {
				LOG.error(prf + "Exception while trying to read ProgettoSuggestVO", e);
				throw new DaoException("DaoException while trying to read ProgettoSuggestVO", e);
			}
			finally {
				LOG.info(prf + " END");
			}

			return progettoSuggestVOList;
	}

	@Override
	public List<BandoSuggestVO> suggestionBando(String titoloBando, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionBando] ";
		LOG.info(prf + " BEGIN");

		List<BandoSuggestVO> bandoSuggestVOList = new ArrayList<>();

		try{
			String sql =
					"SELECT DISTINCT \n" +
							"bandoLineaIntervent.ID_BANDO AS idBando,\n" +
							"bandoLineaIntervent.NOME_BANDO_LINEA AS titoloBando\n" +
							"FROM PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervent\n" +
							"JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP enteComp ON enteComp.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento \n" +
							"JOIN PBANDI_T_DOMANDA domanda ON domanda.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento\n" +
							"JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = domanda.id_domanda \n" +
							"JOIN PBANDI_R_SOGGETTO_PROGETTO soggetto ON soggetto.id_progetto = progetto.id_progetto \n" +
							"WHERE soggetto.id_tipo_anagrafica = 1 AND soggetto.id_tipo_beneficiario <> 4 AND \n" +
							"enteComp.ID_ENTE_COMPETENZA = '2' \n" +
							"AND enteComp.ID_RUOLO_ENTE_COMPETENZA = '1' \n" +
							"AND UPPER(bandoLineaIntervent.NOME_BANDO_LINEA) LIKE CONCAT('%', CONCAT(TRIM(?),'%')) \n" +
							"ORDER BY bandoLineaIntervent.NOME_BANDO_LINEA";

			LOG.debug(sql);

			bandoSuggestVOList = getJdbcTemplate().query(
					sql,
					ps -> {
						ps.setString(1, titoloBando);
					},
					new BandoSuggestVORowMapper()
			);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read BandoVO", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BandoVO", e);
			throw new DaoException("DaoException while trying to read BandoVO", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return bandoSuggestVOList;
	}

	@Override
	public List<AgevolazioneSuggestVO> suggestionAgevolazione(String descrizioneAgevolazione, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionAgevolazione] ";
		LOG.info(prf + " BEGIN");

		List<AgevolazioneSuggestVO> agevolazioneSuggestVOList = new ArrayList<>();

		try{
			String sql =
					"SELECT \n" +
					"modalitaAgevolazione.id_modalita_agevolazione AS idModalitaAgevolazione, \n" +
					"modalitaAgevolazione.desc_modalita_agevolazione AS descModalitaAgevolazione, \n" +
					"modalitaAgevolazione.desc_breve_modalita_agevolaz AS descBreveModalitaAgevolazione \n" +
					"FROM PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione \n" +
					"WHERE \n" +
					"UPPER(modalitaAgevolazione.desc_modalita_agevolazione) like CONCAT(CONCAT('%',?),'%') \n" +
					"AND modalitaAgevolazione.id_modalita_agevolazione in (1, 5, 10) \n";

			LOG.debug(sql);

			agevolazioneSuggestVOList = getJdbcTemplate().query(
					sql,
					ps -> {
						ps.setString(1, descrizioneAgevolazione);
					},
					new AgevolazioneSuggestVORowMapper()
			);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read AgevolazioneVO", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AgevolazioneVO", e);
			throw new DaoException("DaoException while trying to read AgevolazioneVO", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return agevolazioneSuggestVOList;
	}

	@Override
	public List<IdDistintaVO> suggestionDistinta(String idDistinta, Boolean respinta, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionDistinta] ";
		LOG.info(prf + " BEGIN");

		List<IdDistintaVO> idDistintaVOList = new ArrayList<>();

		try{
			String sql =
					"SELECT \n" +
					"distinta.id_distinta AS idDistinta, \n" +
					"distinta.descrizione AS descrizioneDistinta, \n" +
					"modalitaAgevolazione.desc_modalita_agevolazione AS descrModalitaAgevolaz \n" +
					"FROM PBANDI_T_DISTINTA distinta \n" +
					"JOIN PBANDI_D_STATO_DISTINTA statoDistinta ON statoDistinta.id_stato_distinta = distinta.id_stato_distinta \n" +
					"JOIN PBANDI_D_TIPO_DISTINTA tipoDistinta ON tipoDistinta.id_tipo_distinta = distinta.id_tipo_distinta \n" +
					"JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = distinta.id_modalita_agevolazione \n" +
					"WHERE UPPER(distinta.id_distinta) LIKE CONCAT(CONCAT('%',?),'%') \n" +
					(respinta ? "AND statoDistinta.desc_stato_distinta LIKE 'RESPINTA%' \n" : "") +
					"AND tipoDistinta.desc_breve_tipo_distinta IN ('ER', 'ES')" +
					"AND rownum < 100 ";

			LOG.debug(sql);

			idDistintaVOList = getJdbcTemplate().query(
					sql,
					ps -> ps.setString(1, idDistinta),
					new IdDistintaRowMapper()
			);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read DistintaVO", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DistintaVO", e);
			throw new DaoException("DaoException while trying to read DistintaVO", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return idDistintaVOList;
	}

	@Override
	public List<IdSoggettoVO> suggestionIdSoggetto(String idSoggetto, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionIdSoggetto] ";
		LOG.info(prf + " BEGIN");

		List<IdSoggettoVO> idSoggettoList = new ArrayList<> ();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT\r\n"
					+ "    distinct pts.ID_SOGGETTO,\r\n"
					+ "    pdts.DESC_BREVE_TIPO_SOGGETTO,\r\n"
					+ "    pdts.ID_TIPO_SOGGETTO,\r\n"
					+ "    pts.ndg\r\n"
					+ "FROM\r\n"
					+ "	PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
					+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = PRSD .ID_SOGGETTO \r\n"
					+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
					+ "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA  = prsd.PROGR_SOGGETTO_DOMANDA \r\n"
					+ "WHERE\r\n"
					+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    --AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
					+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    --AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    and upper(pts.ndg) like upper('%"+idSoggetto+"%')\r\n"
					+ "    AND rownum <100\r\n"
					+ "    ORDER BY pts.NDG DESC");
					//+ "AND pts.ID_SOGGETTO = " + idSoggetto);

			LOG.debug(prf + sql);

			idSoggettoList  = getJdbcTemplate().query(sql.toString(),new IdSoggettoRowMapper());


		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
			throw new DaoException("DaoException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		} finally {
			LOG.info(prf + " END");
		}

		return idSoggettoList;
	}

	@Override
	public List<SuggestIdDescVO> suggestCodiceFondoFinpis(String codiceFondoFinpis, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestCodiceFondoFinpis] ";
		LOG.info(prf + " BEGIN");

		List<SuggestIdDescVO> codiceFondoFinpisList = new ArrayList<>();

		try {
			String sql =
					"SELECT DISTINCT prbli.PROGR_BANDO_LINEA_INTERVENTO AS id,\n" +
							"prbmaeb.CODICE_FONDO_FINPIS AS sugg\n" +
							"FROM PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb \n" +
							"JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.ID_BANDO = prbmaeb.ID_BANDO \n" +
							"WHERE prbmaeb.CODICE_FONDO_FINPIS LIKE CONCAT('%', CONCAT(?,'%'))";

			LOG.debug(sql);

			codiceFondoFinpisList = getJdbcTemplate().query(
					sql,
					ps -> {
						ps.setString(1, codiceFondoFinpis);
					},
					(rs, rowNum) -> {
						SuggestIdDescVO suggestIdDescVO = new SuggestIdDescVO();

						suggestIdDescVO.setId(rs.getLong("id"));
						suggestIdDescVO.setDesc(rs.getString("sugg"));

						return suggestIdDescVO;
					}
			);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to suggestCodiceFondoFinpis", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to suggestCodiceFondoFinpis", e);
			throw new DaoException("DaoException while trying to suggestCodiceFondoFinpis", e);
		} finally {
			LOG.info(prf + " END");
		}

		return codiceFondoFinpisList;
	}

	@Override
	public List<CodiceFiscaleVO> suggestionCodiceFiscale(String codiceFiscale, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionCodiceFiscale] ";
		LOG.info(prf + " BEGIN");

		List<CodiceFiscaleVO> cfList = new ArrayList<>();

		try {

			StringBuilder sql = new StringBuilder();

			if(idSoggetto != null && idSoggetto != 0) {

				sql.append("SELECT distinct pts.ID_SOGGETTO, pts.CODICE_FISCALE_SOGGETTO, \r\n"
						+ "pdts.DESC_BREVE_TIPO_SOGGETTO, pdts.ID_TIPO_SOGGETTO \r\n"
						+ "FROM PBANDI_T_SOGGETTO pts \r\n"
						+ "JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
						+ "ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
						+ "ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
						+ "ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "WHERE prsd.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_BENEFICIARIO <> 4 \r\n"
						+ "AND pts.CODICE_FISCALE_SOGGETTO LIKE '" + codiceFiscale.toUpperCase() + "%' "
						+ "AND pts.ID_SOGGETTO = " + idSoggetto +"\r\n"
						+ "   AND  rownum < 50\r\n"
						+ "  ORDER BY pts.CODICE_FISCALE_SOGGETTO ");

			}else {

				sql.append("SELECT\r\n"
						+ "    distinct prsd.ID_SOGGETTO,\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "    pdts.DESC_BREVE_TIPO_SOGGETTO,\r\n"
						+ "    pdts.ID_TIPO_SOGGETTO\r\n"
						+ "FROM\r\n"
						+ "	PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "	LEFT JOIN PBANDI_T_SOGGETTO pts ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND upper (pts.CODICE_FISCALE_SOGGETTO ) LIKE '%"+codiceFiscale.toUpperCase()+"%'\r\n"
						+ "    AND rownum < 50\r\n"
						+ "ORDER BY\r\n"
						+ "    pts.CODICE_FISCALE_SOGGETTO");

			}

			LOG.debug(prf + sql);

			cfList = getJdbcTemplate().query(sql.toString(),new CfRowMapper());


		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
			throw new DaoException("DaoException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
		}
		finally {
			LOG.info(prf + " END");
		}

		return cfList;
	}

	@Override
	public List<PartitaIvaVO> suggestionPartitaIva(String partitaIva, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionPartitaIva]";
		LOG.info(prf + " BEGIN");

		List<PartitaIvaVO> pIvaList = new ArrayList<> ();

		try {

			StringBuilder sql = new StringBuilder();

			if(idSoggetto != null && idSoggetto != 0) {

				sql.append("SELECT\r\n"
						+ "    DISTINCT ptse.PARTITA_IVA, \r\n"
						+ "    pts.ID_SOGGETTO,\r\n"
						+ "    pdts.DESC_BREVE_TIPO_SOGGETTO,\r\n"
						+ "    pdts.ID_TIPO_SOGGETTO   \r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "	   LEFT JOIN PBANDI_T_SOGGETTO pts  ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsds.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsds.ID_TIPO_SEDE = 1\r\n"
						+ "    AND ptse.PARTITA_IVA LIKE '%"+partitaIva.toUpperCase()+"%'\r\n"
						+ "    AND rownum < 100");

			}else {
				sql.append("SELECT\r\n"
						+ "    DISTINCT ptse.PARTITA_IVA, \r\n"
						+ "    pts.ID_SOGGETTO,\r\n"
						+ "    pdts.DESC_BREVE_TIPO_SOGGETTO,\r\n"
						+ "    pdts.ID_TIPO_SOGGETTO   \r\n"
						+ "FROM\r\n"
						+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "	   LEFT JOIN PBANDI_T_SOGGETTO pts  ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
						+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsds.ID_SEDE\r\n"
						+ "    LEFT JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsd.ID_ENTE_GIURIDICO\r\n"
						+ "WHERE\r\n"
						+ "    prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    AND prsds.ID_TIPO_SEDE = 1\r\n"
						+ "    AND ptse.PARTITA_IVA LIKE '%"+partitaIva.toUpperCase()+"%'\r\n"
						+ "    AND rownum < 100");

			}


			LOG.debug(prf + sql);

			pIvaList  = getJdbcTemplate().query(sql.toString(),new PIvaRowMapper());


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return pIvaList;
	}

	@Override
	public List<DenominazioneVO> suggestionDenominazione(String denominazione, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionDenominazione] ";
		LOG.info(prf + " BEGIN");

		List<DenominazioneVO> denominazioniList = new ArrayList<> ();

		try {
			StringBuilder sql = new StringBuilder();

			if(idSoggetto != null && idSoggetto != 0) {

				sql.append("with selezione as(SELECT distinct pteg.ID_ENTE_GIURIDICO, pts.ID_SOGGETTO, pdts.DESC_BREVE_TIPO_SOGGETTO, \r\n"
						+ "pteg.DENOMINAZIONE_ENTE_GIURIDICO, pdts.ID_TIPO_SOGGETTO\r\n"
						+ "FROM PBANDI_T_SOGGETTO pts \r\n"
						+ "JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
						+ "ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
						+ "ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
						+ "ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_T_ENTE_GIURIDICO pteg \r\n"
						+ "ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO  \r\n"
						+ "WHERE prsd.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_BENEFICIARIO <> 4 \r\n"
						+ "AND pteg.DENOMINAZIONE_ENTE_GIURIDICO LIKE '%" + denominazione.toUpperCase() + "%' "
						+ "AND pts.ID_SOGGETTO = " + idSoggetto + " "
						+ "ORDER BY DENOMINAZIONE_ENTE_GIURIDICO) select * from selezione where rownum < 50");

			}else {

				sql.append("with selezione as(\r\n"
						+ "    SELECT\r\n"
						+ "        distinct \r\n"
						+ "        pts.ID_SOGGETTO,\r\n"
						+ "        pdts.DESC_BREVE_TIPO_SOGGETTO,\r\n"
						+ "        pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "        pdts.ID_TIPO_SOGGETTO\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_T_SOGGETTO pts\r\n"
						+ "        JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "        JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "        JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "        JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
						+ "    WHERE\r\n"
						+ "        prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "        AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "        AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "        AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "        AND pteg.DENOMINAZIONE_ENTE_GIURIDICO LIKE '%" + denominazione.toUpperCase() + "%'\r\n"
						+ "    GROUP BY\r\n"
						+ "        pts.id_soggetto,\r\n"
						+ "        pdts.DESC_BREVE_TIPO_SOGGETTO,\r\n"
						+ "        pteg.DENOMINAZIONE_ENTE_GIURIDICO,\r\n"
						+ "        pdts.ID_TIPO_SOGGETTO\r\n"
						+ "ORDER BY DENOMINAZIONE_ENTE_GIURIDICO) select * from selezione where rownum < 50");

			}



			LOG.debug(prf + sql);

			denominazioniList = getJdbcTemplate().query(sql.toString(),new DenominazioneRowMapper());


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return denominazioniList;
	}

	@Override
	public List<IdDomandaVO> suggestionIdDomanda(String numeroDomanda, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionIdDomanda] ";
		LOG.info(prf + " BEGIN");

		List<IdDomandaVO> idDomandaList = new ArrayList<> ();

		try {

			StringBuilder sql = new StringBuilder();

			if(idSoggetto != null && idSoggetto != 0) {
				sql.append("with selezione as (\r\n"
						+ "    SELECT\r\n"
						+ "        DISTINCT ptd.NUMERO_DOMANDA,\r\n"
						+ "        prsd.ID_DOMANDA,\r\n"
						+ "        prsd.ID_SOGGETTO,\r\n"
						+ "        prsd.DT_INIZIO_VALIDITA,\r\n"
						+ "        pts.ID_TIPO_SOGGETTO ,\r\n"
						+ "        pdts.DESC_TIPO_SOGGETTO , pdts.DESC_BREVE_TIPO_SOGGETTO\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        INNER JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
						+ "        LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO \r\n"
						+ "        LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pts.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO  \r\n"
						+ "    WHERE\r\n"
						+ "        ptd.NUMERO_DOMANDA LIKE '%"+numeroDomanda+"%'\r\n"
						+ " 	   and prsd.id_soggetto="+ idSoggetto+ "\r\n"
						+ "        AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "		   AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    ORDER BY\r\n"
						+ "        prsd.DT_INIZIO_VALIDITA desc\r\n"
						+ ")\r\n"
						+ "select\r\n"
						+ "    *\r\n"
						+ "from\r\n"
						+ "    selezione\r\n"
						+ "where\r\n"
						+ "    rownum < 50");
			}else {
				sql.append("with selezione as (\r\n"
						+ "    SELECT\r\n"
						+ "        DISTINCT ptd.NUMERO_DOMANDA,\r\n"
						+ "        prsd.ID_DOMANDA,\r\n"
						+ "        prsd.ID_SOGGETTO,\r\n"
						+ "        prsd.DT_INIZIO_VALIDITA,\r\n"
						+ "        pts.ID_TIPO_SOGGETTO ,\r\n"
						+ "        pdts.DESC_TIPO_SOGGETTO, pdts.DESC_BREVE_TIPO_SOGGETTO \r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        INNER JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.ID_DOMANDA\r\n"
						+ "        LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO \r\n"
						+ "        LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pts.ID_TIPO_SOGGETTO = pdts.ID_TIPO_SOGGETTO  \r\n"
						+ "    WHERE\r\n"
						+ "        upper (ptd.NUMERO_DOMANDA) LIKE upper ('%"+numeroDomanda+"%')\r\n"
						+ "        AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "		   AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    ORDER BY\r\n"
						+ "        prsd.DT_INIZIO_VALIDITA desc\r\n"
						+ ")\r\n"
						+ "select\r\n"
						+ "    *\r\n"
						+ "from\r\n"
						+ "    selezione\r\n"
						+ "where\r\n"
						+ "    rownum < 50");
			}


			LOG.debug(prf + sql);

			idDomandaList = getJdbcTemplate().query(sql.toString(),new IdDomandaRowMapper());


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		LOG.info(prf +"PRINTO DOMANDA LIST: " + idDomandaList);
		return idDomandaList;
	}

	@Override
	public List<IdProgettoVO> suggestionIdProgetto(String codProgetto, Long idSoggetto, HttpServletRequest req)
			throws DaoException {
		String prf = "[SuggestionDAOImpl::suggestionIdProgetto] ";
		LOG.info(prf + " BEGIN");

		List<IdProgettoVO> idProgettoList = new ArrayList<> ();

		try {
			StringBuilder sql = new StringBuilder();

			if(idSoggetto != null && idSoggetto != 0) {
				sql.append("with selezione as (\r\n"
						+ "    SELECT\r\n"
						+ "        DISTINCT ptp.ID_PROGETTO,\r\n"
						+ "        prsp.ID_PROGETTO, ptp.CODICE_VISUALIZZATO\r\n"
						+ "        prsp.ID_SOGGETTO,\r\n"
						+ "        prsp.DT_INIZIO_VALIDITA, \r\n"
						+ "        pdts.DESC_TIPO_SOGGETTO, pdts.DESC_BREVE_TIPO_SOGGETTO \r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "        INNER JOIN PBANDI_T_PROGETTO ptp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
						+ "        LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "        LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO \r\n"
						+ "    WHERE\r\n"
						+ "        upper(ptp.CODICE_VISUALIZZATO) LIKE upper('%"+codProgetto+"%')\r\n"
						+ "        AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "		   AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "        AND prsp.ID_SOGGETTO = "+idSoggetto+"\r\n"
						+ "    ORDER BY\r\n"
						+ "        prsp.DT_INIZIO_VALIDITA desc\r\n"
						+ ")\r\n"
						+ "select\r\n"
						+ "    *\r\n"
						+ "from\r\n"
						+ "    selezione\r\n"
						+ "where\r\n"
						+ "    rownum < 50");
			}else {
				sql.append("with selezione as (\r\n"
						+ "    SELECT\r\n"
						+ "        DISTINCT ptp.ID_PROGETTO,\r\n"
						+ "     	ptp.CODICE_VISUALIZZATO,\r\n"
						+ "        prsp.ID_SOGGETTO,\r\n"
						+ "        prsp.DT_INIZIO_VALIDITA, \r\n"
						+ "        pdts.DESC_TIPO_SOGGETTO, pdts.DESC_BREVE_TIPO_SOGGETTO \r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
						+ "        INNER JOIN PBANDI_T_PROGETTO ptp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO\r\n"
						+ "        LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "        LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO \r\n"
						+ "    WHERE\r\n"
						+ "       	   prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "		   AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    ORDER BY\r\n"
						+ "        prsp.DT_INIZIO_VALIDITA desc\r\n"
						+ ")\r\n"
						+ "select\r\n"
						+ "    *\r\n"
						+ "from\r\n"
						+ "    selezione\r\n"
						+ "WHERE\r\n"
						+ " 	upper(selezione.CODICE_VISUALIZZATO) LIKE upper('%"+codProgetto.toUpperCase()+"%')\r\n"
						+ "    AND rownum < 100");
			}



			LOG.debug(prf + sql);

			idProgettoList = getJdbcTemplate().query(sql.toString(),new IdProgettoRowMapper());


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return idProgettoList;
	}

	@Override
	public List<NomeVO> getNome(String nome, Long idSoggetto, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::getNome] ";
		LOG.info(prf + " BEGIN");

		List<NomeVO> nomiList = new ArrayList<> ();

		try {
			StringBuilder sql = new StringBuilder();

			if(idSoggetto != null && idSoggetto != 0) {

				sql.append("SELECT distinct  pts.ID_SOGGETTO, pdts.DESC_BREVE_TIPO_SOGGETTO, \r\n"
						+ "ptpf.NOME, pdts.ID_TIPO_SOGGETTO\r\n"
						+ "FROM PBANDI_T_SOGGETTO pts \r\n"
						+ "JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
						+ "ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
						+ "ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
						+ "ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_T_PERSONA_FISICA ptpf \r\n"
						+ "ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA OR ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA\r\n"
						+ "WHERE prsd.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_BENEFICIARIO <> 4 \r\n"
						+ "AND ptpf.NOME LIKE '" + nome.toUpperCase() + "%' AND pts.ID_SOGGETTO = " + idSoggetto +"\r\n"
						+ "   AND  rownum < 50\r\n");

			}else {

				sql.append("SELECT distinct pts.ID_SOGGETTO, pdts.DESC_BREVE_TIPO_SOGGETTO, \r\n"
						+ "ptpf.NOME, pdts.ID_TIPO_SOGGETTO\r\n"
						+ "FROM PBANDI_T_SOGGETTO pts \r\n"
						+ "JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
						+ "ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
						+ "ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
						+ "ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_T_PERSONA_FISICA ptpf \r\n"
						+ "ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA OR ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA\r\n"
						+ "WHERE prsd.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_BENEFICIARIO <> 4 \r\n"
						+ "AND upper (ptpf.NOME) LIKE '" + nome.toUpperCase() + "%'"
						+ "   AND  rownum < 50\r\n");

			}

			LOG.debug(prf + sql);

			ResultSetExtractor<List<NomeVO>> rse = rs -> {

				List<NomeVO> list = new ArrayList<> ();

				while (rs.next())
				{

					NomeVO vo = new NomeVO();

					vo.setNome(rs.getString("NOME"));
					vo.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
					vo.setDescrTipoSogg(rs.getNString("DESC_BREVE_TIPO_SOGGETTO"));
					vo.setIdTipoSogg(rs.getInt("ID_TIPO_SOGGETTO"));

					list.add(vo);

				}

				return list;
			};

			nomiList = getJdbcTemplate().query(sql.toString(), rse);


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return nomiList;
	}

	@Override
	public List<CognomeVO> getCognome(String cognome, Long idSoggetto, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::getNome] ";
		LOG.info(prf + " BEGIN");

		List<CognomeVO> cognomiList = new ArrayList<> ();

		try {
			StringBuilder sql = new StringBuilder();

			if(idSoggetto != null && idSoggetto != 0) {

				sql.append("SELECT distinct pts.ID_SOGGETTO, pdts.DESC_BREVE_TIPO_SOGGETTO, \r\n"
						+ "ptpf.COGNOME, pdts.ID_TIPO_SOGGETTO\r\n"
						+ "FROM PBANDI_T_SOGGETTO pts \r\n"
						+ "JOIN PBANDI_D_TIPO_SOGGETTO pdts \r\n"
						+ "ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
						+ "ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
						+ "ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO \r\n"
						+ "JOIN PBANDI_T_PERSONA_FISICA ptpf \r\n"
						+ "ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA OR ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA\r\n"
						+ "WHERE prsd.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_TIPO_BENEFICIARIO <> 4 \r\n"
						+ "AND ptpf.COGNOME LIKE '" + cognome.toUpperCase() + "%' AND pts.ID_SOGGETTO = " + idSoggetto +"\r\n"
						+ "   AND  rownum < 50\r\n");

			} else {

				sql.append("WITH dati AS (\r\n"
						+ "    SELECT\r\n"
						+ "        prsd.ID_SOGGETTO,\r\n"
						+ "        prsd.ID_PERSONA_FISICA,\r\n"
						+ "        pdts.DESC_BREVE_TIPO_SOGGETTO, pts.ID_TIPO_SOGGETTO ,\r\n"
						+ "        pts.CODICE_FISCALE_SOGGETTO,\r\n"
						+ "        CONCAT(ptpf.COGNOME, CONCAT(' ', ptpf.NOME)) AS denom\r\n"
						+ "    FROM\r\n"
						+ "        PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
						+ "        LEFT JOIN PBANDI_T_SOGGETTO pts ON prsd.ID_SOGGETTO = pts.ID_SOGGETTO\r\n"
						+ "        LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
						+ "        LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsd.ID_PERSONA_FISICA\r\n"
						+ "    WHERE\r\n"
						+ "        prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
						+ "        AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
						+ "    ORDER BY\r\n"
						+ "        denom\r\n"
						+ ")\r\n"
						+ "SELECT\r\n"
						+ "    *\r\n"
						+ "FROM\r\n"
						+ "    dati\r\n"
						+ "WHERE\r\n"
						+ "    UPPER(dati.denom) LIKE '%"+cognome.toUpperCase()+"%'\r\n"
						+ "    AND rownum < 100");

			}

			LOG.debug(prf + sql);

			ResultSetExtractor<List<CognomeVO>> rse = rs -> {

				List<CognomeVO> list = new ArrayList<> ();

				while (rs.next())
				{

					CognomeVO vo = new CognomeVO();

					vo.setCognome(rs.getString("denom"));
					vo.setIdSoggetto(rs.getLong("ID_SOGGETTO"));
					vo.setDescrTipoSogg(rs.getNString("DESC_BREVE_TIPO_SOGGETTO"));
					vo.setIdTipoSogg(rs.getInt("ID_TIPO_SOGGETTO"));
					list.add(vo);

				}

				return list;
			};

			cognomiList = getJdbcTemplate().query(sql.toString(), rse);


		}catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException ", e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read ", e);
			throw new DaoException("DaoException while trying to read ", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return cognomiList;
	}

	@Override
	public AutofillVO getAutofill(Long idSoggetto, String tipoSoggetto, Long idDomanda, Long idProgetto, HttpServletRequest req) throws DaoException {
		String prf = "[SuggestionDAOImpl::getNome] ";
		LOG.info(prf + " BEGIN");

		AutofillVO autofill = new AutofillVO ();


		try {
			
			
			// se fra i parametri il numero domanda passato non è null,  allora faccio l'autofill con essi dati, 
			// stessa cosa per i 
			//  Per recuperare l'autofill devo seguire i seguenti passi
			// controllo se l'ultima domanda del soggetto eneficiario ha un progetto oppure no 
			
			
			
			// cerco l'ultima domanda 
			String getUltimaDomanda =" SELECT MAX(prsd.PROGR_SOGGETTO_DOMANDA)  AS PROGR_SOGGETTO_DOMANDA \r\n"
					+ " FROM PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
					+ " WHERE ID_SOGGETTO ="+idSoggetto+"\r\n"; 
					if(idDomanda>0) {
						getUltimaDomanda = getUltimaDomanda + " AND prsd.id_domanda ="+idDomanda+"\r\n"; 
					}
					getUltimaDomanda = getUltimaDomanda + " 	AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "	AND prsd.ID_TIPO_ANAGRAFICA =1\r\n"
					+ " GROUP BY ID_SOGGETTO";
			Long progSoggettoDomanda = getJdbcTemplate().queryForObject(getUltimaDomanda, Long.class);
			
			if(idProgetto==0) {			
				idProgetto = getIdProgetto(progSoggettoDomanda);
			}
		
			// se il progetto è nul allora recupero i dati con la tabella 
			if(idProgetto!=null) {
				String getAutofillProgetto= null;
				// si tratta di una personna giurdica
				if(tipoSoggetto.equals("EG")) {
					getAutofillProgetto	="WITH selezione AS (\r\n"
							+ "    SELECT\r\n"
							+ "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS desc_1,\r\n"
							+ "        '' AS desc_2,\r\n"
							+ "        ENTEGIU.ID_SOGGETTO, entegiu.ID_ENTE_GIURIDICO\r\n"
							+ "    FROM\r\n"
							+ "        PBANDI_T_ENTE_GIURIDICO entegiu\r\n"
							+ ")\r\n"
							+ "SELECT\r\n"
							+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
							+ "    pts.ndg,\r\n"
							+ "    pts.ID_SOGGETTO,\r\n"
							+ "    se.desc_1,\r\n"
							+ "    se.desc_2,\r\n"
							+ "    ptp.CODICE_VISUALIZZATO,\r\n"
							+ "    ptd.NUMERO_DOMANDA,\r\n"
							+ "    prsd.ID_DOMANDA,\r\n"
							+ "    pdsp.DESC_STATO_PROGETTO,\r\n"
							+ "    ptse.PARTITA_IVA, pdts.DESC_BREVE_TIPO_SOGGETTO ,\r\n"
							+ "    ptp.CODICE_VISUALIZZATO, ptp.CODICE_PROGETTO, prsp.id_progetto \r\n"
							+ "FROM\r\n"
							+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
							+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
							+ "    LEFT JOIN selezione se ON prsp.ID_ENTE_GIURIDICO = se.ID_ENTE_GIURIDICO\r\n"
							+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.id_domanda\r\n"
							+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON pdsp.ID_STATO_PROGETTO = ptp.ID_STATO_PROGETTO\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
							+ "    LEFT JOIN PBANDI_T_SEDE ptse ON prsps.ID_SEDE = ptse.ID_SEDE\r\n"
							+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
							+ "WHERE\r\n"
							+ "    prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    AND prsp.PROGR_SOGGETTO_PROGETTO = "+idProgetto+"\r\n"
							+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
							+ "    AND rownum = 1"; 
				
				} else {
					
					getAutofillProgetto="WITH selezione AS (\r\n"
							+ "    SELECT\r\n"
							+ "        persfis.COGNOME AS desc_1,\r\n"
							+ "        persfis.NOME AS desc_2,\r\n"
							+ "        PERSFIS.ID_SOGGETTO,\r\n"
							+ "        persfis.ID_PERSONA_FISICA\r\n"
							+ "    FROM\r\n"
							+ "        PBANDI_T_PERSONA_FISICA persfis\r\n"
							+ ")\r\n"
							+ "SELECT\r\n"
							+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
							+ "    pts.ndg,\r\n"
							+ "    pts.ID_SOGGETTO,\r\n"
							+ "    se.desc_1,\r\n"
							+ "    se.desc_2,\r\n"
							+ "    ptp.CODICE_VISUALIZZATO,\r\n"
							+ "    ptd.NUMERO_DOMANDA,\r\n"
							+ "    prsd.ID_DOMANDA,\r\n"
							+ "    pdsp.DESC_STATO_PROGETTO,\r\n"
							+ "    ptse.PARTITA_IVA,\r\n"
							+ "    pdts.DESC_BREVE_TIPO_SOGGETTO,\r\n"
							+ "    ptp.CODICE_VISUALIZZATO,\r\n"
							+ "    ptp.CODICE_PROGETTO,\r\n"
							+ "    prsd.ID_PERSONA_FISICA as idEntePersFis, prsp.id_progetto\r\n"
							+ "FROM\r\n"
							+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
							+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
							+ "    LEFT JOIN selezione se ON prsp.ID_PERSONA_FISICA = se.ID_PERSONA_FISICA\r\n"
							+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.id_domanda\r\n"
							+ "    LEFT JOIN PBANDI_D_STATO_PROGETTO pdsp ON pdsp.ID_STATO_PROGETTO = ptp.ID_STATO_PROGETTO\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps ON prsps.PROGR_SOGGETTO_PROGETTO = prsp.PROGR_SOGGETTO_PROGETTO\r\n"
							+ "    LEFT JOIN PBANDI_T_SEDE ptse ON prsps.ID_SEDE = ptse.ID_SEDE\r\n"
							+ "    LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
							+ "WHERE\r\n"
							+ "    prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "    AND prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    AND prsp.PROGR_SOGGETTO_PROGETTO ="+idProgetto+"\r\n"
							+ "    AND prsd.ID_SOGGETTO ="+idSoggetto+"\r\n"
							+ "    AND rownum = 1";
					
				}
						
				autofill = getJdbcTemplate().query(getAutofillProgetto, rs -> {
					AutofillVO result = new AutofillVO();

					while (rs.next())
					{
						String desc2=null;
						//result.setDesc
						result.setCf(rs.getString("CODICE_FISCALE_SOGGETTO"));
						result.setIdSoggetto(rs.getInt("ID_SOGGETTO"));
						result.setpIva(rs.getString("PARTITA_IVA"));
						result.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
						result.setCodVisualizzato(rs.getString("CODICE_PROGETTO"));
						result.setDescStatoDomanda(rs.getString("DESC_STATO_PROGETTO"));
						result.setNdg(rs.getString("ndg"));
						result.setIdDomanda(rs.getLong("ID_DOMANDA"));
						result.setIdProgetto(rs.getLong("id_progetto"));
						 desc2= rs.getString("DESC_2");
						if(desc2!=null) {							
							result.setNome(desc2);
							result.setCognome(rs.getString("desc_1")+ " "+ result.getNome());
						} else {
							result.setDenominazione(rs.getString("desc_1"));
						}
					}
					return result;
				});
				
			} else {
				
				String getAutoFill = null; 
				if(tipoSoggetto.equals("EG")) {
					getAutoFill ="WITH selezione AS (\r\n"
							+ "    SELECT\r\n"
							+ "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS desc_1,\r\n"
							+ "        '' AS desc_2,\r\n"
							+ "        ENTEGIU.ID_SOGGETTO, entegiu.ID_ENTE_GIURIDICO\r\n"
							+ "    FROM\r\n"
							+ "        PBANDI_T_ENTE_GIURIDICO entegiu\r\n"
							+ ")\r\n"
							+ "SELECT\r\n"
							+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
							+ "    pts.ndg,\r\n"
							+ "    pts.ID_SOGGETTO,\r\n"
							+ "    se.desc_1,\r\n"
							+ "    se.desc_2,\r\n"
							+ "    ptd.NUMERO_DOMANDA,\r\n"
							+ "    prsd.ID_DOMANDA,\r\n"
							+ "    pdsd.DESC_STATO_DOMANDA, pdts.DESC_BREVE_TIPO_SOGGETTO ,\r\n"
							+ "    ptse.PARTITA_IVA\r\n"
							+ "FROM\r\n"
							+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
							+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n"
							+ "    --LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "    --LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
							+ "    LEFT JOIN selezione se ON prsd.ID_ENTE_GIURIDICO = se.ID_ENTE_GIURIDICO\r\n"
							+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.id_domanda\r\n"
							+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsds.ID_SEDE\r\n"
							+ "  LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
							+ "WHERE\r\n"
							+ "    --prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    --AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "     prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    AND prsd.PROGR_SOGGETTO_DOMANDA ="+progSoggettoDomanda+"\r\n"
							+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
							+ "    AND rownum = 1";
					
				} else {
					
					getAutoFill ="WITH selezione AS (\r\n"
							+ "    SELECT\r\n"
							+ "        persfis.COGNOME AS desc_1,\r\n"
							+ "        persfis.NOME AS desc_2,\r\n"
							+ "        PERSFIS.ID_SOGGETTO, persfis.ID_PERSONA_FISICA\r\n"
							+ "    FROM\r\n"
							+ "        PBANDI_T_PERSONA_FISICA persfis\r\n"
							+ ")\r\n"
							+ "SELECT\r\n"
							+ "    pts.CODICE_FISCALE_SOGGETTO,\r\n"
							+ "    pts.ndg,\r\n"
							+ "    pts.ID_SOGGETTO,\r\n"
							+ "    se.desc_1,\r\n"
							+ "    se.desc_2,\r\n"
							+ "    ptd.NUMERO_DOMANDA,\r\n"
							+ "    prsd.ID_DOMANDA,\r\n"
							+ "    pdsd.DESC_STATO_DOMANDA, pdts.DESC_BREVE_TIPO_SOGGETTO ,\r\n"
							+ "    ptse.PARTITA_IVA\r\n"
							+ "FROM\r\n"
							+ "    PBANDI_R_SOGGETTO_DOMANDA prsd\r\n"
							+ "    LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO\r\n"
							+ "    --LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "    --LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
							+ "    LEFT JOIN selezione se ON prsd.ID_PERSONA_FISICA = se.ID_PERSONA_FISICA\r\n"
							+ "    LEFT JOIN PBANDI_T_DOMANDA ptd ON prsd.ID_DOMANDA = ptd.id_domanda\r\n"
							+ "    LEFT JOIN PBANDI_D_STATO_DOMANDA pdsd ON pdsd.ID_STATO_DOMANDA = ptd.ID_STATO_DOMANDA\r\n"
							+ "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds ON prsds.PROGR_SOGGETTO_DOMANDA = prsd.PROGR_SOGGETTO_DOMANDA\r\n"
							+ "    LEFT JOIN PBANDI_T_SEDE ptse ON ptse.ID_SEDE = prsds.ID_SEDE\r\n"
							+ "  LEFT JOIN PBANDI_D_TIPO_SOGGETTO pdts ON pdts.ID_TIPO_SOGGETTO = pts.ID_TIPO_SOGGETTO\r\n"
							+ "WHERE\r\n"
							+ "    --prsp.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    --AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "     prsd.ID_TIPO_BENEFICIARIO <> 4\r\n"
							+ "    AND prsd.ID_TIPO_ANAGRAFICA = 1\r\n"
							+ "    AND prsd.PROGR_SOGGETTO_DOMANDA ="+progSoggettoDomanda+"\r\n"
							+ "    AND prsd.ID_SOGGETTO = "+idSoggetto+"\r\n"
							+ "    AND rownum = 1";
					
				}
				
				
				
				autofill = getJdbcTemplate().query(getAutoFill, rs -> {
					AutofillVO result = new AutofillVO();

					while (rs.next())
					{
						String desc2=null;
						result.setCf(rs.getString("CODICE_FISCALE_SOGGETTO"));
						result.setIdSoggetto(rs.getInt("ID_SOGGETTO"));
						result.setpIva(rs.getString("PARTITA_IVA"));
						result.setNumeroDomanda(rs.getString("NUMERO_DOMANDA"));
						//result.setCodVisualizzato(rs.getString("CODICE_PROGETTO"));
						result.setDescStatoDomanda(rs.getString("DESC_STATO_DOMANDA"));
						result.setNdg(rs.getString("ndg"));
						result.setIdDomanda(rs.getLong("ID_DOMANDA"));
						 desc2= rs.getString("DESC_2");
						if(desc2!=null) {							
							result.setNome(desc2);
							result.setCognome(rs.getString("desc_1")+ " "+ result.getNome());
						} else {
							result.setDenominazione(rs.getString("desc_1"));
						}
					}
					return result;
				});
				
			}
		
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read ", e);
			throw new DaoException("DaoException while trying to read ", e);
		}
		finally {
			LOG.info(prf + " END");
		}
		return autofill;
	}

	private Long getIdProgetto(Long progSoggettoDomanda) throws IncorrectResultSizeDataAccessException {
		
		Long idProgetto = null ; 
		
		try {
			String getIdProgetto ="SELECT\r\n"
					+ "    prsp.PROGR_SOGGETTO_PROGETTO \r\n"
					+ "FROM\r\n"
					+ "    PBANDI_R_SOGGETTO_PROGETTO prsp\r\n"
					+ "WHERE\r\n"
					+ "    PROGR_SOGGETTO_DOMANDA ="+progSoggettoDomanda+"\r\n"
					+ "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
					+ "    AND prsp.ID_TIPO_ANAGRAFICA = 1"; 
		
			 //idProgetto=getJdbcTemplate().queryForObject(getIdProgetto, BigDecimal.class);
			 idProgetto = getJdbcTemplate().query(getIdProgetto, new ResultSetExtractor<Long>() {

				@Override
				public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
					Long id = null; 
					while (rs.next()) {
						id = rs.getLong("PROGR_SOGGETTO_PROGETTO"); 
					}
						return id;
				}
				 
			 });
			
		} catch(IncorrectResultSizeDataAccessException e) {
			LOG.error("IncorrectResultSizeDataAccessException while trying to read PBANDI_R_SOGGETTO_PROGETTO", e);
		}
		catch (Exception e) {
			LOG.error(e);
			return null; 
		}
 
		return idProgetto;
	}



	//METODI SUGGESTION DI BACKUP PRE REFACTOR

	//	@Override
	//	public List<Long> suggestionIdSoggetto(Long idSoggetto, HttpServletRequest req) throws DaoException {
	//
	//		String prf = "[SuggestionDAOImpl::suggestionIdSoggetto] ";
	//		LOG.info(prf + " BEGIN");
	//
	//		List<Long> idSoggettoList = new ArrayList<Long> ();
	//
	//		try {
	//
	//			StringBuilder sql = new StringBuilder();
	//			sql.append("with selezione as (SELECT pts.ID_SOGGETTO FROM PBANDI_T_SOGGETTO pts LEFT OUTER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO "
	//					+ "WHERE to_char(pts.ID_SOGGETTO) LIKE '"+idSoggetto+"%' AND prsd.ID_TIPO_ANAGRAFICA = 1) "
	//					+ "select * from selezione where rownum < 50");
	//
	//			LOG.debug(prf + sql);
	//
	//			List<SuggestionVO> lista  = getJdbcTemplate().query(sql.toString(),new SuggestionRowMapper());
	//
	//			Set<SuggestionVO> hashSet = (HashSet<SuggestionVO>) lista.stream().collect(Collectors.toSet());
	//
	//			hashSet.forEach(idList -> idSoggettoList.add(idList.getIdSoggetto()));
	//
	//
	//		} catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//			throw new DaoException("DaoException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//
	//		return idSoggettoList;
	//	}
	//
	//
	//
	//
	//	@Override
	//	public List<CodiceFiscaleVO> suggestionCodiceFiscale(String codiceFiscale, HttpServletRequest req) throws DaoException {
	//
	//		String prf = "[SuggestionDAOImpl::suggestionCodiceFiscale] ";
	//		LOG.info(prf + " BEGIN");
	//
	//		List<CodiceFiscaleVO> cfList = new ArrayList<CodiceFiscaleVO> ();
	//
	//		try {
	//
	//			StringBuilder sql = new StringBuilder();
	//			sql.append("with selezione as (SELECT pts.CODICE_FISCALE_SOGGETTO, pts.ID_SOGGETTO ")
	//			.append("FROM PBANDI_T_SOGGETTO pts ")
	//			.append("LEFT OUTER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ")
	//			.append("ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO ")
	//			.append("LEFT OUTER JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ")
	//			.append("ON pts.ID_SOGGETTO = prsp.ID_SOGGETTO ")
	//			.append("WHERE pts.CODICE_FISCALE_SOGGETTO LIKE '" + codiceFiscale.toUpperCase() + "%' AND prsd.ID_TIPO_ANAGRAFICA = 1 AND prsd.ID_TIPO_BENEFICIARIO <> 4) select * from selezione where rownum < 50");
	//
	//			LOG.debug(prf + sql);
	//
	//			cfList = getJdbcTemplate().query(sql.toString(),new CfRowMapper());
	//
	//
	//		} catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//			throw new DaoException("DaoException while trying to read PBANDI_R_SOGGETTO_DOMANDA", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//
	//		return cfList;
	//	}
	//
	//
	//
	//
	//	@Override
	//	public List<PartitaIvaVO> suggestionPartitaIva(String partitaIva, HttpServletRequest req) throws DaoException {
	//		String prf = "[SuggestionDAOImpl::suggestionPartitaIva]";
	//		LOG.info(prf + " BEGIN");
	//
	//		List<PartitaIvaVO> pIvaList = new ArrayList<PartitaIvaVO> ();
	//
	//		try {
	//
	//			StringBuilder sql = new StringBuilder();
	//			sql.append("with selezione as (SELECT pts.PARTITA_IVA \r\n"
	//					+ "FROM PBANDI_T_SEDE pts \r\n"
	//					+ "LEFT OUTER JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE prsds \r\n"
	//					+ "ON pts.ID_SEDE = prsds.ID_SEDE \r\n"
	//					+ "RIGHT OUTER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd \r\n"
	//					+ "ON prsd.PROGR_SOGGETTO_DOMANDA = prsds.PROGR_SOGGETTO_DOMANDA \r\n"
	//					+ "LEFT OUTER JOIN PBANDI_R_SOGG_PROGETTO_SEDE prsps \r\n"
	//					+ "ON pts.ID_SEDE = prsps.ID_SEDE \r\n"
	//					+ "RIGHT OUTER JOIN  PBANDI_R_SOGGETTO_PROGETTO prsp \r\n"
	//					+ "ON prsp.PROGR_SOGGETTO_PROGETTO = prsps.PROGR_SOGGETTO_PROGETTO "
	//					+ "WHERE prsds.ID_TIPO_SEDE = 1 AND TO_CHAR(pts.PARTITA_IVA) LIKE '" + partitaIva + "%' AND prsd.ID_TIPO_ANAGRAFICA = 1 AND prsd.ID_TIPO_BENEFICIARIO <> 4)"
	//					+ "select * from selezione where rownum < 50");
	//
	//			LOG.debug(prf + sql);
	//
	//			pIvaList  = getJdbcTemplate().query(sql.toString(),new PIvaRowMapper());
	//
	//
	//		}catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
	//			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//		return pIvaList;
	//	}
	//
	//
	//
	//	@Override
	//	public List<DenominazioneVO> suggestionDenominazione(String denominazione, HttpServletRequest req) throws DaoException {
	//		String prf = "[SuggestionDAOImpl::suggestionDenominazione] ";
	//		LOG.info(prf + " BEGIN");
	//
	//		List<DenominazioneVO> denominazioniList = new ArrayList<DenominazioneVO> ();
	//
	//		try {
	//			StringBuilder sql = new StringBuilder();
	//
	//			sql.append("with selezione as(SELECT pteg.DENOMINAZIONE_ENTE_GIURIDICO ")
	//			.append("FROM PBANDI_T_ENTE_GIURIDICO pteg ")
	//			.append("LEFT OUTER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ")
	//			.append("ON pteg.ID_SOGGETTO = prsd.ID_SOGGETTO ")
	//			.append("WHERE pteg.DENOMINAZIONE_ENTE_GIURIDICO LIKE '" + denominazione.toUpperCase() + "%' ")
	//			.append("AND PBANDI_R_SOGGETTO_DOMANDA.ID_TIPO_ANAGRAFICA = 1 ORDER BY DENOMINAZIONE_ENTE_GIURIDICO) select * from selezione where rownum < 50");
	//
	//
	//			LOG.debug(prf + sql);
	//
	//			denominazioniList = getJdbcTemplate().query(sql.toString(),new DenominazioneRowMapper());
	//
	//
	//		}catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
	//			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//		return denominazioniList;
	//	}
	//
	//	@Override
	//	public List<IdDomandaVO> suggestionIdDomanda(String numeroDomanda, HttpServletRequest req) throws DaoException {
	//		String prf = "[SuggestionDAOImpl::suggestionIdDomanda] ";
	//		LOG.info(prf + " BEGIN");
	//
	//		List<IdDomandaVO> idDomandaList = new ArrayList<IdDomandaVO> ();
	//
	//		try {
	//
	//			StringBuilder sql = new StringBuilder();
	//			sql.append("with selezione as (SELECT DISTINCT ptd.NUMERO_DOMANDA, prsd.ID_DOMANDA, prsd.ID_SOGGETTO, prsd.DT_INIZIO_VALIDITA "
	//					+ "FROM PBANDI_R_SOGGETTO_DOMANDA prsd "
	//					+ "INNER JOIN PBANDI_T_DOMANDA ptd "
	//					+ "ON prsd.ID_DOMANDA = ptd.ID_DOMANDA "
	//					+ "WHERE ptd.NUMERO_DOMANDA LIKE '" + numeroDomanda + "%' AND prsd.ID_TIPO_ANAGRAFICA = 1 ORDER BY prsd.DT_INIZIO_VALIDITA desc) "
	//					+ "select * from selezione where rownum < 50");
	//
	//			LOG.debug(prf + sql);
	//
	//			idDomandaList = getJdbcTemplate().query(sql.toString(),new IdDomandaRowMapper());
	//
	//
	//		}catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
	//			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//		System.out.println("PRINTO DOMANDA LIST: " + idDomandaList);
	//		return idDomandaList;
	//	}
	//
	//
	//
	//
	//	@Override
	//	public List<IdProgettoVO> suggestionIdProgetto(String codProgetto, HttpServletRequest req) throws DaoException {
	//		String prf = "[SuggestionDAOImpl::suggestionIdProgetto] ";
	//		LOG.info(prf + " BEGIN");
	//
	//		List<IdProgettoVO> idProgettoList = new ArrayList<IdProgettoVO> ();
	//
	//		try {
	//			StringBuilder sql = new StringBuilder();
	//			sql.append("with selezione as (SELECT DISTINCT ptp.CODICE_PROGETTO, prsp.ID_PROGETTO, prsp.ID_SOGGETTO, prsp.DT_INIZIO_VALIDITA \r\n"
	//					+ "FROM PBANDI_R_SOGGETTO_PROGETTO prsp "
	//					+ "INNER JOIN PBANDI_T_PROGETTO ptp "
	//					+ "ON prsp.ID_PROGETTO = ptp.ID_PROGETTO "
	//					+ "WHERE ptp.CODICE_PROGETTO LIKE '" + codProgetto + "%' AND prsp.ID_TIPO_ANAGRAFICA = 1 ORDER BY prsp.DT_INIZIO_VALIDITA desc) "
	//					+ "select * from selezione where rownum < 50");
	//
	//
	//			LOG.debug(prf + sql);
	//
	//			idProgettoList = getJdbcTemplate().query(sql.toString(),new IdProgettoRowMapper());
	//
	//
	//		}catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
	//			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//		return idProgettoList;
	//	}
	//
	//	@Override
	//	public List<NomeVO> getNome(String nome, HttpServletRequest req) throws DaoException {
	//		String prf = "[SuggestionDAOImpl::getNome] ";
	//		LOG.info(prf + " BEGIN");
	//
	//		List<NomeVO> nomiList = new ArrayList<NomeVO> ();
	//
	//		try {
	//			StringBuilder sql = new StringBuilder();
	//			sql.append("with selezione as (SELECT ptpf.NOME "
	//					+ "FROM PBANDI_T_SOGGETTO pts "
	//					+ "LEFT OUTER JOIN PBANDI_T_PERSONA_FISICA ptpf "
	//					+ "ON pts.ID_SOGGETTO = ptpf.ID_SOGGETTO "
	//					+ "LEFT OUTER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd "
	//					+ "ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO "
	//					+ "WHERE ptpf.NOME LIKE '"+nome.toUpperCase()+"%' AND prsd.ID_TIPO_ANAGRAFICA = 1) "
	//					+ "select * from selezione where rownum < 50");
	//
	//
	//			LOG.debug(prf + sql);
	//
	//			ResultSetExtractor<List<NomeVO>> rse = new ResultSetExtractor<List<NomeVO>>() {
	//
	//				@Override
	//				public List<NomeVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
	//					
	//					List<NomeVO> list = new ArrayList<NomeVO> ();
	//					
	//					 while (rs.next())
	//			          {
	//						 NomeVO vo = new NomeVO();
	//						 vo.setNome(rs.getString("NOME"));
	//			      		list.add(vo);
	//			          }
	//
	//					return list;
	//				}
	//			};
	//
	//			nomiList = getJdbcTemplate().query(sql.toString(), rse);
	//
	//
	//		}catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read PBANDI_T_SEDE", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read PBANDI_T_SEDE", e);
	//			throw new DaoException("DaoException while trying to read PBANDI_T_SEDE", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//		return nomiList;
	//	}
	//
	//	@Override
	//	public List<CognomeVO> getCognome(String cognome, HttpServletRequest req) throws DaoException {
	//		String prf = "[SuggestionDAOImpl::getNome] ";
	//		LOG.info(prf + " BEGIN");
	//
	//		List<CognomeVO> cognomiList = new ArrayList<CognomeVO> ();
	//
	//		try {
	//			StringBuilder sql = new StringBuilder();
	//			sql.append("with selezione as (SELECT ptpf.COGNOME "
	//					+ "FROM PBANDI_T_SOGGETTO pts "
	//					+ "LEFT OUTER JOIN PBANDI_T_PERSONA_FISICA ptpf "
	//					+ "ON pts.ID_SOGGETTO = ptpf.ID_SOGGETTO "
	//					+ "LEFT OUTER JOIN PBANDI_R_SOGGETTO_DOMANDA prsd "
	//					+ "ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO "
	//					+ "WHERE ptpf.NOME LIKE '"+cognome.toUpperCase()+"%' AND prsd.ID_TIPO_ANAGRAFICA = 1) "
	//					+ "select * from selezione where rownum < 50");
	//
	//
	//			LOG.debug(prf + sql);
	//
	//			ResultSetExtractor<List<CognomeVO>> rse = new ResultSetExtractor<List<CognomeVO>>() {
	//
	//				@Override
	//				public List<CognomeVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
	//					
	//					List<CognomeVO> list = new ArrayList<CognomeVO> ();
	//					
	//					 while (rs.next())
	//			          {
	//						 CognomeVO vo = new CognomeVO();
	//						 vo.setCognome(rs.getString("COGNOME"));
	//			      		list.add(vo);
	//			          }
	//
	//					return list;
	//				}
	//			};
	//
	//			cognomiList = getJdbcTemplate().query(sql.toString(), rse);
	//
	//
	//		}catch (IncorrectResultSizeDataAccessException e) {
	//			LOG.error(prf + "IncorrectResultSizeDataAccessException ", e);
	//
	//		} catch (Exception e) {
	//			LOG.error(prf + "Exception while trying to read ", e);
	//			throw new DaoException("DaoException while trying to read ", e);
	//		}
	//		finally {
	//			LOG.info(prf + " END");
	//		}
	//		return cognomiList;
	//	}




}
