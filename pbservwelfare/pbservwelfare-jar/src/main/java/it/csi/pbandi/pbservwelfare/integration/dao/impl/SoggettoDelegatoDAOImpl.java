/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.InternalUnexpectedException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.SoggettoDelegatoDAO;
import it.csi.pbandi.pbservwelfare.util.CommonUtil;
import it.csi.pbandi.pbservwelfare.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.Types;
import java.util.HashMap;

public class SoggettoDelegatoDAOImpl extends BaseDAO implements SoggettoDelegatoDAO {

	private final Logger LOG;

	public SoggettoDelegatoDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	/**
	 *
	 */
	@Override
	public HashMap<String, String> getSoggettoBeneficiario(String numeroDomanda) {
		String prf = "[SoggettoDelegatoDAOImpl::getSoggettoBeneficiario]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> map;
		LOG.info(prf + ", numeroDomanda=" + numeroDomanda);
		try {
			String sql = "SELECT PRSP.PROGR_SOGGETTO_PROGETTO,												\n" 
					+ "       PRSP.ID_SOGGETTO,																\n"
					+ "		  PTP.ID_PROGETTO																\n"	
					+ "FROM PBANDI_T_DOMANDA PTD															\n" 
					+ "JOIN PBANDI_T_PROGETTO PTP ON PTD.ID_DOMANDA = PTP.ID_DOMANDA						\n"
					+ "JOIN PBANDI_R_SOGGETTO_PROGETTO PRSP ON PTP.ID_PROGETTO = PRSP.ID_PROGETTO			\n"
					+ "WHERE PTD.NUMERO_DOMANDA= :numeroDomanda												\n" 
					+ "  AND PRSP.ID_TIPO_ANAGRAFICA = 1													\n"
					+ "  AND PRSP.ID_TIPO_BENEFICIARIO <> 4 												\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			map = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, String> soggBenef = new HashMap<>();
				while (rs.next()) {
					soggBenef.put("idSoggetto", String.valueOf(rs.getInt("ID_SOGGETTO")));
					soggBenef.put("progrSoggettoProgetto", String.valueOf(rs.getInt("PROGR_SOGGETTO_PROGETTO")));
					soggBenef.put("idProgetto", String.valueOf(rs.getInt("ID_PROGETTO")));
				}
				return soggBenef;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoDelegato", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettoDelegato", e);
		} finally {
			LOG.info(prf + " END");
		}

		return map;
	}

	@Override
	public HashMap<String, String> isSoggettoPresente(String idSoggetto, String idProgetto, String codiceFiscale) {
		String prf = "[SoggettoDelegatoDAOImpl::isSoggettoPresente]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> result;
		LOG.info(prf + ", idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto + ", codiceFiscale=" + codiceFiscale);
		try {
			String sql = "SELECT PRSC.PROGR_SOGGETTI_CORRELATI,																				\n" + 
					"       PRSC.ID_SOGGETTO,																								\n" + 
					"       PRSPSC.PROGR_SOGGETTO_PROGETTO,																					\n" + 
					"       PRSP.ID_SOGGETTO,																								\n" + 
					"       PTS.CODICE_FISCALE_SOGGETTO																						\n" + 
					"FROM PBANDI_R_SOGGETTI_CORRELATI PRSC																					\n" + 
					"JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL PRSPSC ON PRSPSC.PROGR_SOGGETTI_CORRELATI = PRSC.PROGR_SOGGETTI_CORRELATI			\n" + 
					"JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON PRSP.PROGR_SOGGETTO_PROGETTO = PRSPSC.PROGR_SOGGETTO_PROGETTO					\n" + 
					"JOIN PBANDI_T_SOGGETTO pts ON PTS.ID_SOGGETTO = PRSP.ID_SOGGETTO														\n" + 
					"WHERE ID_SOGGETTO_ENTE_GIURIDICO= :idSoggetto 																			\n" + 
					"  AND ID_TIPO_SOGGETTO_CORRELATO = 22																					\n" + 
					"  AND PRSP.ID_PROGETTO= :idProgetto 																					\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);

			result = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, String> map = new HashMap<>();
				map.put("isSoggettoPresente", "false");
				while (rs.next()) {
					String codFiscaleSoggetto = rs.getString("CODICE_FISCALE_SOGGETTO");
					if(codiceFiscale.equals(codFiscaleSoggetto)) {
						map.put("isSoggettoPresente", "true");
						map.put("progrSoggProgettoDelegato", String.valueOf(rs.getInt("PROGR_SOGGETTO_PROGETTO")));
					}
				}
				return map;
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoDelegato", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettoDelegato", e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public Integer verificaEsistenzaIdentificativoSoggetto(String codiceFiscale) {
		String prf = "[SoggettoDelegatoDAOImpl::verificaEsistenzaIdentificativoSoggetto]";
		LOG.info(prf + " BEGIN");
		Integer idSoggetto;
		LOG.info(prf + ", codiceFiscale=" + codiceFiscale);
		try {
			String sql = "SELECT ID_SOGGETTO									\n" + 
					"FROM PBANDI_T_SOGGETTO PTS									\n" + 
					"WHERE CODICE_FISCALE_SOGGETTO= :codiceFiscale				\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);

			idSoggetto = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				Integer idSoggettoDelegato = null;
				while (rs.next()) {
					idSoggettoDelegato = rs.getInt("ID_SOGGETTO");
				}
				return idSoggettoDelegato;
			});

		} finally {
			LOG.info(prf + " END");
		}

		return idSoggetto;
	}

	@Override
	public void insertSoggetto(String codiceFiscale) {
		String prf = "[SoggettoDelegatoDAOImpl::insertSoggetto]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", codiceFiscale=" + codiceFiscale);
		
		try {
			String sql = "INSERT INTO PBANDI_T_SOGGETTO (ID_SOGGETTO, ID_TIPO_SOGGETTO, CODICE_FISCALE_SOGGETTO, ID_UTENTE_INS, DT_INSERIMENTO)		\n" + 
					"VALUES(SEQ_PBANDI_T_SOGGETTO.NEXTVAL,																							\n" + 
					"       1,																														\n" + 
					"       :codiceFiscale,																											\n" + 
					"       1,																														\n" + 
					"       SYSDATE)																												\n";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
		
	}

	@Override
	public Integer verificaEsistenzaIdentificativoPersonaFisica(Integer idSoggetto) {
		String prf = "[SoggettoDelegatoDAOImpl::verificaEsistenzaIdentificativoPersonaFisica]";
		LOG.info(prf + " BEGIN");
		Integer idPersonaFisica;
		LOG.info(prf + ", idSoggetto=" + idSoggetto);
		try {
			String sql = "SELECT ID_PERSONA_FISICA					\n" + 
					"FROM PBANDI_T_PERSONA_FISICA PTPF				\n" + 
					"WHERE ID_SOGGETTO= :idSoggetto					\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

			idPersonaFisica = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				Integer id = null;
				while (rs.next()) {
					id = rs.getInt("ID_PERSONA_FISICA");
				}
				return id;
			});

		} finally {
			LOG.info(prf + " END");
		}

		return idPersonaFisica;
	}

	@Override
	public void insertPersonaFisica(Integer idSoggettoDelegato, String cognome, String nome, String dataNascita,
			String codiceComuneNascita, String descrizioneComuneEsteroNascita) {
		String prf = "[SoggettoDelegatoDAOImpl::insertPersonaFisica]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggettoDelegato=" + idSoggettoDelegato + ", cognome=" + cognome + ", nome=" + nome
				+ ", dataNascita=" + dataNascita + ", codiceComuneNascita=" + codiceComuneNascita
				+ ", descrizioneComuneEsteroNascita=" + descrizioneComuneEsteroNascita);
		
		try {
			// Recupero valore per ID_COMUNE_ITALIANO_NASCITA
			Integer idComune = getIdComune(codiceComuneNascita);
			// Recupero valore per ID_COMUNE_ESTERO_NASCITA
			String comuneEsteroNascitaSql = "SELECT ID_COMUNE_ESTERO FROM PBANDI_D_COMUNE_ESTERO PDCE WHERE DESC_COMUNE_ESTERO= :descrizioneComuneEsteroNascita";
			LOG.debug(descrizioneComuneEsteroNascita);
			MapSqlParameterSource paramComuneEstero = new MapSqlParameterSource();
			paramComuneEstero.addValue("descrizioneComuneEsteroNascita", descrizioneComuneEsteroNascita, Types.VARCHAR);
			Integer idComuneEstero = this.queryForInteger(comuneEsteroNascitaSql, paramComuneEstero);
			// Recupero valore per ID_NAZIONE_NASCITA
			Integer idNazione = getIdNazione(descrizioneComuneEsteroNascita, idComune);
			
			String sql = "INSERT INTO PBANDI_T_PERSONA_FISICA (ID_PERSONA_FISICA, ID_SOGGETTO, COGNOME, NOME, DT_NASCITA, DT_INIZIO_VALIDITA, ID_COMUNE_ITALIANO_NASCITA, ID_COMUNE_ESTERO_NASCITA, ID_NAZIONE_NASCITA, ID_UTENTE_INS)	\n" + 
					"VALUES(SEQ_PBANDI_T_PERSONA_FISICA.NEXTVAL,																																										\n" + 
					"       :idSoggettoDelegato,																																														\n" + 
					"       :cognome,																																																	\n" + 
					"       :nome,																																																		\n" + 
					"       :dataNascita,																																										\n" + 
					"       SYSDATE,																																																	\n" + 
					"       :idComune,																																																	\n" + 
					"       :idComuneEstero,																																															\n" + 
					"       :idNazione,																																																	\n" + 
					"       1)																																																			\n";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggettoDelegato", idSoggettoDelegato, Types.INTEGER);
			param.addValue("cognome", cognome, Types.VARCHAR);
			param.addValue("nome", nome, Types.VARCHAR);
			if(!CommonUtil.isEmpty(dataNascita)) {
				param.addValue("dataNascita", dataNascita, Types.DATE);
			} else {
				param.addValue("dataNascita", null, Types.VARCHAR);
			}
			param.addValue("idComune", idComune, Types.INTEGER);
			param.addValue("idComuneEstero", idComuneEstero, Types.INTEGER);
			param.addValue("idNazione", idNazione, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
		
	}

	@Override
	public void insertSoggettiCorrelati(Integer idSoggettoDelegato, String idSoggetto) {
		String prf = "[SoggettoDelegatoDAOImpl::insertSoggettiCorrelati]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggettoDelegato=" + idSoggettoDelegato + ", idSoggetto=" + idSoggetto);
		
		try {
			String sql = "INSERT INTO PBANDI_R_SOGGETTI_CORRELATI (ID_SOGGETTO, ID_TIPO_SOGGETTO_CORRELATO, ID_SOGGETTO_ENTE_GIURIDICO, DT_INIZIO_VALIDITA, DT_FINE_VALIDITA, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS)	\n" + 
					"VALUES(:idSoggettoDelegato,																																										\n" + 
					"       22,																																															\n" + 
					"       :idSoggetto,																																												\n" + 
					"       SYSDATE,																																													\n" + 
					"       NULL,																																														\n" + 
					"       SEQ_PBANDI_R_SOGG_CORRELATI.NEXTVAL,																																						\n" + 
					"       1)																																															\n";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggettoDelegato", idSoggettoDelegato, Types.INTEGER);
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
		
	}

	@Override
	public Integer getProgrSoggettiCorrelati(Integer idSoggettoDelegato, String idSoggetto) {
		String prf = "[SoggettoDelegatoDAOImpl::getProgrSoggettiCorrelati]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggettoDelegato=" + idSoggettoDelegato + ", idSoggetto=" + idSoggetto);
		Integer progrSoggettiCorrelati;
		try {
			String sql = "SELECT PROGR_SOGGETTI_CORRELATI					\n" + 
					"FROM PBANDI_R_SOGGETTI_CORRELATI						\n" + 
					"WHERE ID_SOGGETTO = :idSoggettoDelegato				\n" + 
					"  AND ID_SOGGETTO_ENTE_GIURIDICO = :idSoggetto			\n";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggettoDelegato", idSoggettoDelegato, Types.INTEGER);
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			progrSoggettiCorrelati = this.queryForInteger(sql, param);
		} finally {
			LOG.info(prf + " END");
		}
		return progrSoggettiCorrelati;
	}

	@Override
	public void insertSoggettoProgetto(Integer idSoggettoDelegato, String idProgetto, Integer idPersonaFisica) {
		String prf = "[SoggettoDelegatoDAOImpl::insertSoggettoProgetto]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggettoDelegato=" + idSoggettoDelegato + ", idProgetto=" + idProgetto + ", idPersonaFisica=" + idPersonaFisica);
		
		try {
			String sql = "INSERT INTO PBANDI_R_SOGGETTO_PROGETTO (ID_SOGGETTO, ID_PROGETTO, PROGR_SOGGETTO_PROGETTO, DT_INIZIO_VALIDITA, ID_TIPO_ANAGRAFICA, ID_PERSONA_FISICA, ID_UTENTE_INS, DT_INSERIMENTO)\r\n" +
					"VALUES(:idSoggettoDelegato,																				\n" + 
					"       :idProgetto,																						\n" + 
					"       SEQ_PBANDI_R_SOGGETTO_PROGETTO.NEXTVAL,																\n" + 
					"       SYSDATE,																							\n" + 
					"       16,																									\n" + 
					"       :idPersonaFisica,																					\n" +
					"       1,																									\n" + 
					"       SYSDATE)																							\n";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggettoDelegato", idSoggettoDelegato, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			param.addValue("idPersonaFisica", idPersonaFisica, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	@Override
	public Integer getProgrSoggettoProgetto(Integer idSoggettoDelegato, String idProgetto, Integer idPersonaFisica) {
		String prf = "[SoggettoDelegatoDAOImpl::getProgrSoggettiCorrelati]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggettoDelegato=" + idSoggettoDelegato + ", idProgetto=" + idProgetto + ", idPersonaFisica=" + idPersonaFisica);
		Integer progrSoggettoProgetto;
		try {
			String sql = "SELECT PROGR_SOGGETTO_PROGETTO FROM PBANDI_R_SOGGETTO_PROGETTO prsp WHERE ID_SOGGETTO= :idSoggettoDelegato AND ID_PROGETTO= :idProgetto AND ID_PERSONA_FISICA= :idPersonaFisica";
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggettoDelegato", idSoggettoDelegato, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);
			param.addValue("idPersonaFisica", idPersonaFisica, Types.INTEGER);
			progrSoggettoProgetto = this.queryForInteger(sql, param);
		} finally {
			LOG.info(prf + " END");
		}
		return progrSoggettoProgetto;
	}
	
	@Override
	public void insertSoggProgSoggCorrel(Integer progrSoggettoProgettoDelegato, Integer progrSoggettiCorrelati) {
		String prf = "[SoggettoDelegatoDAOImpl::insertSoggProgSoggCorrel]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", progrSoggettoProgettoDelegato=" + progrSoggettoProgettoDelegato + ", progrSoggettiCorrelati=" + progrSoggettiCorrelati);
		
		try {
			String sql = "INSERT INTO PBANDI_R_SOGG_PROG_SOGG_CORREL (PROGR_SOGGETTO_PROGETTO, PROGR_SOGGETTI_CORRELATI, ID_UTENTE_INS, DT_INSERIMENTO, DT_FINE_VALIDITA)		\n" + 
					"VALUES(:progrSoggettoProgettoDelegato,																														\n" + 
					"       :progrSoggettiCorrelati,																															\n" +
					"		1,																																					\n" +		
					"       SYSDATE,																																			\n" + 
					"       NULL)																																				\n";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("progrSoggettoProgettoDelegato", progrSoggettoProgettoDelegato, Types.INTEGER);
			param.addValue("progrSoggettiCorrelati", progrSoggettiCorrelati, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	@Override
	public void updatePersonaFisica(Integer idSoggettoDelegato, String cognome, String nome, String dataNascita,
			String codiceComuneNascita, String descrizioneComuneEsteroNascita) {
		String prf = "[SoggettoDelegatoDAOImpl::updatePersonaFisica]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", idSoggettoDelegato=" + idSoggettoDelegato + ", cognome=" + cognome + ", nome=" + nome
				+ ", dataNascita=" + dataNascita + ", codiceComuneNascita=" + codiceComuneNascita
				+ ", descrizioneComuneEsteroNascita=" + descrizioneComuneEsteroNascita);
		
		try {
			// Recupero valore per ID_COMUNE_ITALIANO_NASCITA
			Integer idComune = getIdComune(codiceComuneNascita);
			// Recupero valore per ID_COMUNE_ESTERO_NASCITA
			String comuneEsteroNascitaSql = "SELECT ID_COMUNE_ESTERO FROM PBANDI_D_COMUNE_ESTERO PDCE WHERE DESC_COMUNE_ESTERO= :descrizioneComuneEsteroNascita";
			LOG.debug(descrizioneComuneEsteroNascita);
			MapSqlParameterSource paramComuneEstero = new MapSqlParameterSource();
			paramComuneEstero.addValue("descrizioneComuneEsteroNascita", descrizioneComuneEsteroNascita, Types.VARCHAR);
			Integer idComuneEstero = this.queryForInteger(comuneEsteroNascitaSql, paramComuneEstero);
			// Recupero valore per ID_NAZIONE_NASCITA
			Integer idNazione = getIdNazione(descrizioneComuneEsteroNascita, idComune);
			
			String sql = "UPDATE PBANDI_T_PERSONA_FISICA						\n" + 
					"SET COGNOME= :cognome,										\n" + 
					"NOME= :nome,												\n" + 
					"DT_NASCITA= :dataNascita,									\n" + 
					"ID_COMUNE_ITALIANO_NASCITA= :idComune,						\n" + 
					"ID_COMUNE_ESTERO_NASCITA= :idComuneEstero,					\n" + 
					"ID_NAZIONE_NASCITA= :idNazione								\n" + 
					"WHERE ID_SOGGETTO= :idSoggettoDelegato						\n";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("cognome", cognome, Types.VARCHAR);
			param.addValue("nome", nome, Types.VARCHAR);
			if(!CommonUtil.isEmpty(dataNascita)) {
				param.addValue("dataNascita", dataNascita, Types.DATE);
			} else {
				param.addValue("dataNascita", null, Types.VARCHAR);
			}
			param.addValue("idComune", idComune, Types.INTEGER);
			param.addValue("idComuneEstero", idComuneEstero, Types.INTEGER);
			param.addValue("idNazione", idNazione, Types.INTEGER);
			param.addValue("idSoggettoDelegato", idSoggettoDelegato, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	
	@Override
	public HashMap<String, Integer> getIdIndirizzoERecapiti(String progrSoggProgetto) {
		String prf = "[SoggettoDelegatoDAOImpl::getIdIndirizzoERecapiti]";
		LOG.info(prf + " BEGIN");
		HashMap<String, Integer> map;
		LOG.info(prf + ", progrSoggProgetto=" + progrSoggProgetto);
		try {
			String sql = "SELECT ID_INDIRIZZO_PERSONA_FISICA, ID_RECAPITI_PERSONA_FISICA FROM PBANDI_R_SOGGETTO_PROGETTO prsp WHERE PROGR_SOGGETTO_PROGETTO= :progrSoggProgetto";
			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("progrSoggProgetto", progrSoggProgetto, Types.INTEGER);

			map = this.namedParameterJdbcTemplate.query(sql, param, rs -> {
				HashMap<String, Integer> id = new HashMap<>();
				while (rs.next()) {
					id.put("idIndirizzo", rs.getInt("ID_INDIRIZZO_PERSONA_FISICA"));
					if(rs.wasNull()){
						id.put("idIndirizzo", null);
					}
					id.put("idRecapiti", rs.getInt("ID_RECAPITI_PERSONA_FISICA"));
					if(rs.wasNull()){
						id.put("idRecapiti", null);
					}
				}
				return id;
			});

		} finally {
			LOG.info(prf + " END");
		}

		return map;
	}
	
	@Override
	public void updateIndirizzo(Integer idIndirizzo, String indirizzo, String cap, String codiceComuneNascita,
			String descrizioneComuneEsteroNascita) {
		String prf = "[SoggettoDelegatoDAOImpl::updateIndirizzo]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", indirizzo=" + indirizzo + ", cap=" + cap + ", codiceComuneNascita=" + codiceComuneNascita
				+ ", descrizioneComuneEsteroNascita=" + descrizioneComuneEsteroNascita);
		
		try {
			Integer idComune = getIdComune(codiceComuneNascita);
			Integer idNazione = getIdNazione(descrizioneComuneEsteroNascita, idComune);
			// Recupero valoer per ID_ PROVINCIA
			String provinciaSql = "SELECT ID_PROVINCIA FROM PBANDI_D_COMUNE PDC WHERE COD_ISTAT_COMUNE= :codiceComuneNascita";
			LOG.debug(provinciaSql);
			MapSqlParameterSource paramProvincia = new MapSqlParameterSource();
			paramProvincia.addValue("codiceComuneNascita", codiceComuneNascita, Types.VARCHAR);
			Integer idProvincia = this.queryForInteger(provinciaSql, paramProvincia);
			
			String sql = "UPDATE PBANDI_T_INDIRIZZO					\n" + 
					"SET DESC_INDIRIZZO= :indirizzo, 				\n" + 
					"CAP= :cap,										\n" + 
					"ID_NAZIONE= :idNazione,						\n" + 
					"ID_COMUNE= :idComune,							\n" + 
					"ID_PROVINCIA= :idProvincia,					\n" + 
					"DT_INIZIO_VALIDITA = SYSDATE					\n" + 
					"WHERE ID_INDIRIZZO= :idIndirizzo				\n";
			
			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("indirizzo", indirizzo, Types.VARCHAR);
			param.addValue("cap", cap, Types.VARCHAR);
			param.addValue("idNazione", idNazione, Types.INTEGER);
			param.addValue("idComune", idComune, Types.INTEGER);
			param.addValue("idProvincia", idProvincia, Types.INTEGER);
			param.addValue("idIndirizzo", idIndirizzo, Types.INTEGER);
			this.update(sql, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
		
	}

	@Override
	public int createIndirizzo(String indirizzo, String cap, String codiceComuneNascita, String descrizioneComuneEsteroNascita, String progrSoggProgDelegato){
		String prf = "[SoggettoDelegatoDAOImpl::createIndirizzo]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", indirizzo=" + indirizzo + ", cap=" + cap + ", codiceComuneNascita=" + codiceComuneNascita
				+ ", descrizioneComuneEsteroNascita=" + descrizioneComuneEsteroNascita);

		Integer idIndirizzo;

		try {
			Integer idComune = getIdComune(codiceComuneNascita);
			Integer idNazione = getIdNazione(descrizioneComuneEsteroNascita, idComune);
			// Recupero valoer per ID_ PROVINCIA
			String provinciaSql = "SELECT ID_PROVINCIA FROM PBANDI_D_COMUNE PDC WHERE COD_ISTAT_COMUNE= :codiceComuneNascita";
			LOG.debug(provinciaSql);
			MapSqlParameterSource paramProvincia = new MapSqlParameterSource();
			paramProvincia.addValue("codiceComuneNascita", codiceComuneNascita, Types.VARCHAR);
			Integer idProvincia = this.queryForInteger(provinciaSql, paramProvincia);

			String getNewIdIndirizzo = "select PBANDI.SEQ_PBANDI_T_INDIRIZZO.nextval from dual";
			idIndirizzo = this.queryForInteger(getNewIdIndirizzo, new MapSqlParameterSource());

			String sql = "INSERT INTO PBANDI_T_INDIRIZZO \n" +
					"(ID_INDIRIZZO, DESC_INDIRIZZO, CAP, ID_NAZIONE, ID_COMUNE, ID_PROVINCIA, DT_INIZIO_VALIDITA, ID_UTENTE_INS)\n" +
					"VALUES (:idIndirizzo, :indirizzo, :cap, :idNazione, :idComune, :idProvincia, SYSDATE, 1)";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idIndirizzo", idIndirizzo, Types.INTEGER);
			param.addValue("indirizzo", indirizzo, Types.VARCHAR);
			param.addValue("cap", cap, Types.VARCHAR);
			param.addValue("idNazione", idNazione, Types.INTEGER);
			param.addValue("idComune", idComune, Types.INTEGER);
			param.addValue("idProvincia", idProvincia, Types.INTEGER);
			this.update(sql, param);

			updateIdIndirizzo(progrSoggProgDelegato, idIndirizzo);
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'inserire il record", e);
		} finally {
			LOG.info(prf + " END");
		}

		return idIndirizzo;
	}

	@Override
	public void updateIdIndirizzo(String progrSoggettoProgetto, Integer idIndirizzo) {
		String prf = "[SoggettoDelegatoDAOImpl::updateIdIndirizzo]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", progrSoggettoProgetto=" + progrSoggettoProgetto + ", idIndirizzo=" + idIndirizzo);

		try {
			String sql = "UPDATE PBANDI_R_SOGGETTO_PROGETTO SET \n" +
					"ID_INDIRIZZO_PERSONA_FISICA = :idIndirizzo\n" +
					"WHERE PROGR_SOGGETTO_PROGETTO = :progrSoggettoProgetto";
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idIndirizzo", idIndirizzo, Types.INTEGER);
			param.addValue("progrSoggettoProgetto", progrSoggettoProgetto, Types.VARCHAR);
			this.update(sql, param);
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public void updateRecapiti(Integer idRecapiti, String mail, String telefono) {
		String prf = "[SoggettoDelegatoDAOImpl::updateRecapiti]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", mail=" + mail + ", telefono=" + telefono);

		try {
			String sql = "UPDATE PBANDI_T_RECAPITI SET \n" +
					"EMAIL = :mail,\n" +
					"TELEFONO = :telefono,\n" +
					"ID_UTENTE_AGG = 1,\n" +
					"DT_INIZIO_VALIDITA = SYSDATE\n" +
					"WHERE ID_RECAPITI = :idRecapiti";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("mail", mail, Types.VARCHAR);
			param.addValue("telefono", telefono, Types.VARCHAR);
			param.addValue("idRecapiti", idRecapiti, Types.INTEGER);
			this.update(sql, param);
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}

	@Override
	public int createRecapiti(String mail, String telefono, String progrSoggProgDelegato) {
		String prf = "[SoggettoDelegatoDAOImpl::createRecapiti]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", mail=" + mail + ", telefono=" + telefono);

		Integer idRecapiti;

		try {
			String getNewIdRecapiti = "select PBANDI.SEQ_PBANDI_T_RECAPITI.nextval from dual";
			idRecapiti = this.queryForInteger(getNewIdRecapiti, new MapSqlParameterSource());

			String sql = "INSERT INTO PBANDI_T_RECAPITI  \n" +
					"(ID_RECAPITI, EMAIL, TELEFONO, ID_UTENTE_INS, DT_INIZIO_VALIDITA)\n" +
					"VALUES (:idRecapiti, :mail, :telefono, 1, SYSDATE)";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idRecapiti", idRecapiti, Types.INTEGER);
			param.addValue("mail", mail, Types.VARCHAR);
			param.addValue("telefono", telefono, Types.VARCHAR);
			this.update(sql, param);

			updateIdRecapiti(progrSoggProgDelegato, idRecapiti);
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}

		return idRecapiti;
	}

	@Override
	public void updateIdRecapiti(String progrSoggettoProgetto, Integer idRecapiti) {
		String prf = "[SoggettoDelegatoDAOImpl::updateIdRecapiti]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + ", progrSoggettoProgetto=" + progrSoggettoProgetto + ", idRecapiti=" + idRecapiti);

		try {
			String sql = "UPDATE PBANDI_R_SOGGETTO_PROGETTO SET \n" +
					"ID_RECAPITI_PERSONA_FISICA = :idRecapiti\n" +
					"WHERE PROGR_SOGGETTO_PROGETTO = :progrSoggettoProgetto";
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idRecapiti", idRecapiti, Types.INTEGER);
			param.addValue("progrSoggettoProgetto", progrSoggettoProgetto, Types.VARCHAR);
			this.update(sql, param);
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
	}
	

	private Integer getIdComune(String codiceComuneNascita) {
		String comuneItalianoNascitaSql = "SELECT ID_COMUNE FROM PBANDI_D_COMUNE PDC WHERE COD_ISTAT_COMUNE= :codiceComuneNascita";
		LOG.debug(comuneItalianoNascitaSql);
		MapSqlParameterSource paramComune = new MapSqlParameterSource();
		paramComune.addValue("codiceComuneNascita", codiceComuneNascita, Types.VARCHAR);
		return this.queryForInteger(comuneItalianoNascitaSql, paramComune);
	}
	

	private Integer getIdNazione(String descrizioneComuneEsteroNascita, Integer idComune) {
		Integer idNazione;
		if(idComune != null) {
			String idNazioneSql = "SELECT ID_NAZIONE FROM PBANDI_D_NAZIONE PDN WHERE COD_ISTAT_NAZIONE = '000'";
			LOG.debug(idNazioneSql);
			idNazione = this.queryForInteger(idNazioneSql, null);
		} else {
			String idNazioneSql = "SELECT ID_NAZIONE FROM PBANDI_D_COMUNE_ESTERO PDCE WHERE DESC_COMUNE_ESTERO= :descrizioneComuneEsteroNascita";
			LOG.debug(idNazioneSql);
			MapSqlParameterSource paramNazione = new MapSqlParameterSource();
			paramNazione.addValue("descrizioneComuneEsteroNascita", descrizioneComuneEsteroNascita, Types.VARCHAR);
			idNazione = this.queryForInteger(idNazioneSql, paramNazione);
		}
		return idNazione;
	}

}
