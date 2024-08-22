/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.dto.IdentificativoBeneficiario;
import it.csi.pbandi.pbservwelfare.dto.IdentificativoSoggettoCorrelato;
import it.csi.pbandi.pbservwelfare.dto.SoggettiCorrelati;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.ElencoSoggettiCorrelatiDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class ElencoSoggettiCorrelatiDAOImpl extends BaseDAO implements ElencoSoggettiCorrelatiDAO {
	
	private Logger LOG;
	
	public ElencoSoggettiCorrelatiDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public List<IdentificativoBeneficiario> getIdentificativoBeneficiario(String numeroDomanda) {
		String prf = "[ElencoSoggettiCorrelatiDAOImpl::getIdentificativoBeneficiario]";
		LOG.info(prf + " BEGIN");
		ArrayList<IdentificativoBeneficiario> listaBeneficiari = new ArrayList<>();
		LOG.info(prf + ", numeroDomanda=" + numeroDomanda);
		try {
			String sql = "SELECT PTD.ID_DOMANDA,													\n" + 
					"       PTP.ID_PROGETTO,														\n" + 
					"       PRSP.PROGR_SOGGETTO_PROGETTO,											\n" + 
					"       PRSP.ID_SOGGETTO,														\n" + 
					"       PRSP.ID_PERSONA_FISICA,													\n" + 
					"		PTS.CODICE_FISCALE_SOGGETTO,											\n" +
					"		PRSP.ID_RECAPITI_PERSONA_FISICA, 										\n" +
					"		PRSP.ID_INDIRIZZO_PERSONA_FISICA										\n" +
					"FROM PBANDI_T_DOMANDA PTD														\n" + 
					"JOIN PBANDI_T_PROGETTO PTP ON PTP.ID_DOMANDA = PTD.ID_DOMANDA					\n" + 
					"JOIN PBANDI_R_SOGGETTO_PROGETTO PRSP ON PRSP.ID_PROGETTO = PTP.ID_PROGETTO		\n" + 
					"JOIN PBANDI_T_SOGGETTO PTS ON PTS.ID_SOGGETTO = PRSP.ID_SOGGETTO 				\n" +			
					"WHERE PTD.NUMERO_DOMANDA= :numeroDomanda										\n" + 
					"  AND PRSP.ID_TIPO_ANAGRAFICA = 1												\n" + 
					"  AND PRSP.ID_TIPO_BENEFICIARIO <> 4											\n" + 
					"  AND PRSP.DT_FINE_VALIDITA IS NULL											\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			listaBeneficiari = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<ArrayList<IdentificativoBeneficiario>>() {
				@Override
				public ArrayList<IdentificativoBeneficiario> extractData(ResultSet rs) throws SQLException, DataAccessException {
					ArrayList<IdentificativoBeneficiario> list = new ArrayList<>();
					while (rs.next()) {
						IdentificativoBeneficiario benef = new IdentificativoBeneficiario();
						benef.setProgrSoggBeneficiario(rs.getInt("PROGR_SOGGETTO_PROGETTO"));
						benef.setIdSoggBeneficiario(rs.getInt("ID_SOGGETTO"));
						benef.setIdPersonaFisicaBeneficiario(rs.getInt("ID_PERSONA_FISICA"));
						benef.setIdProgetto(rs.getInt("ID_PROGETTO"));
						benef.setCodiceFiscaleSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
						benef.setIdRecapitiPersonaFisica(rs.getInt("ID_RECAPITI_PERSONA_FISICA"));
						benef.setIdIndirizzoPersonaFisica(rs.getInt("ID_INDIRIZZO_PERSONA_FISICA"));
						list.add(benef);
					}
					return list;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read IdentificativoBeneficiario", e);
			throw new ErroreGestitoException("DaoException while trying to read IdentificativoBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaBeneficiari;
	}

	@Override
	public List<IdentificativoSoggettoCorrelato> getIdentificativoSoggettoCorrelato(Integer idSoggettoBeneficiario) {
		String prf = "[ElencoSoggettiCorrelatiDAOImpl::getIdentificativoSoggettoCorrelato]";
		LOG.info(prf + " BEGIN");
		ArrayList<IdentificativoSoggettoCorrelato> listaSoggettiCorrelati = new ArrayList<>();
		LOG.info(prf + ", idSoggettoBeneficiario=" + idSoggettoBeneficiario);
		try {
			String sql = "SELECT ID_SOGGETTO,											\n" + 
					"       PROGR_SOGGETTI_CORRELATI									\n" + 
					"FROM PBANDI_R_SOGGETTI_CORRELATI PRSC								\n" + 
					"WHERE (ID_TIPO_SOGGETTO_CORRELATO = 1								\n" + 
					"       OR ID_TIPO_SOGGETTO_CORRELATO = 22)							\n" + 
					"  AND ID_SOGGETTO_ENTE_GIURIDICO= :idSoggettoBeneficiario			\n" + 
					"  AND DT_FINE_VALIDITA IS NULL										\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario, Types.INTEGER);

			listaSoggettiCorrelati = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<ArrayList<IdentificativoSoggettoCorrelato>>() {
				@Override
				public ArrayList<IdentificativoSoggettoCorrelato> extractData(ResultSet rs) throws SQLException, DataAccessException {
					ArrayList<IdentificativoSoggettoCorrelato> list = new ArrayList<>();
					while (rs.next()) {
						IdentificativoSoggettoCorrelato soggetto = new IdentificativoSoggettoCorrelato();
						soggetto.setIdSoggettoCorrelato(rs.getInt("ID_SOGGETTO"));
						soggetto.setProgrSoggettiCorrelati(rs.getInt("PROGR_SOGGETTI_CORRELATI"));
						list.add(soggetto);
					}
					return list;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read IdentificativoSoggettoCorrelato", e);
			throw new ErroreGestitoException("DaoException while trying to read IdentificativoSoggettoCorrelato", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaSoggettiCorrelati;
	}

	@Override
	public List<SoggettiCorrelati> getSoggettiCorrelati(Integer progrSoggettiCorrelati, Integer idProgetto) {
		String prf = "[ElencoSoggettiCorrelatiDAOImpl::getSoggettiCorrelati]";
		LOG.info(prf + " BEGIN");
		ArrayList<SoggettiCorrelati> listaSoggetti = new ArrayList<>();
		LOG.info(prf + ", progrSoggettiCorrelati=" + progrSoggettiCorrelati + ", idProgetto=" + idProgetto);
		try {
			String sql = "SELECT PRSP.PROGR_SOGGETTO_PROGETTO,																		\n" + 
					"       PRSP.ID_PERSONA_FISICA,																					\n" + 
					"       PTS.CODICE_FISCALE_SOGGETTO,																			\n" + 
					"       PDTSC.DESC_TIPO_SOGGETTO_CORRELATO,																		\n" + 
					"       PRSP.ID_RECAPITI_PERSONA_FISICA,																		\n" + 
					"       PRSP.ID_INDIRIZZO_PERSONA_FISICA																		\n" + 
					"FROM PBANDI_R_SOGG_PROG_SOGG_CORREL PRSPSC																		\n" + 
					"JOIN PBANDI_R_SOGGETTI_CORRELATI prsc ON prsc.PROGR_SOGGETTI_CORRELATI = prspsc.PROGR_SOGGETTI_CORRELATI 		\n" + 
					"JOIN PBANDI_R_SOGGETTO_PROGETTO PRSP ON PRSP.PROGR_SOGGETTO_PROGETTO = PRSPSC.PROGR_SOGGETTO_PROGETTO 			\n" + 
					"JOIN PBANDI_T_SOGGETTO PTS ON PTS.ID_SOGGETTO = PRSP.ID_SOGGETTO												\n" + 
					"JOIN PBANDI_D_TIPO_SOGG_CORRELATO PDTSC ON PDTSC.ID_TIPO_SOGGETTO_CORRELATO = prsc.ID_TIPO_SOGGETTO_CORRELATO 	\n" + 
					"WHERE PRSPSC.PROGR_SOGGETTI_CORRELATI= :progrSoggettiCorrelati													\n" + 
					"  AND PRSP.DT_FINE_VALIDITA IS NULL																			\n" + 
					"  AND PRSP.ID_PROGETTO= :idProgetto																			\n" + 
					"  AND PTS.ID_TIPO_SOGGETTO = 1																					\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("progrSoggettiCorrelati", progrSoggettiCorrelati, Types.INTEGER);
			param.addValue("idProgetto", idProgetto, Types.INTEGER);

			listaSoggetti = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<ArrayList<SoggettiCorrelati>>() {
				@Override
				public ArrayList<SoggettiCorrelati> extractData(ResultSet rs) throws SQLException, DataAccessException {
					ArrayList<SoggettiCorrelati> list = new ArrayList<>();
					while (rs.next()) {
						SoggettiCorrelati soggetto = new SoggettiCorrelati();
						soggetto.setProgrSoggettoProgettoCorrelato(rs.getInt("PROGR_SOGGETTO_PROGETTO"));
						soggetto.setIdPersonaFisica(rs.getInt("ID_PERSONA_FISICA"));
						soggetto.setCodFiscaleSoggetto(rs.getString("CODICE_FISCALE_SOGGETTO"));
						soggetto.setDescTipoSoggettoCorrelato(rs.getString("DESC_TIPO_SOGGETTO_CORRELATO"));
						soggetto.setIdRecapitiPersonaFisica(rs.getInt("ID_RECAPITI_PERSONA_FISICA"));
						soggetto.setIdIndirizzoPersonaFisica(rs.getInt("ID_INDIRIZZO_PERSONA_FISICA"));
						list.add(soggetto);
					}
					return list;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettiCorrelati", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettiCorrelati", e);
		} finally {
			LOG.info(prf + " END");
		}

		return listaSoggetti;
	}

	@Override
	public HashMap<String, String> getInfoAnagrafiche(Integer idPersonaFisica) {
		String prf = "[ElencoSoggettiCorrelatiDAOImpl::getInfoAnagrafiche]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> infoAnagrafiche = new HashMap<>();
		LOG.info(prf + ", idPersonaFisica=" + idPersonaFisica);
		try {
			String sql = "SELECT NOME,								\n" + 
					"       COGNOME,								\n" + 
					"       DT_NASCITA,								\n" + 
					"       ID_COMUNE_ITALIANO_NASCITA,				\n" + 
					"       ID_COMUNE_ESTERO_NASCITA				\n" + 
					"FROM PBANDI_T_PERSONA_FISICA PTPF				\n" + 
					"WHERE ID_PERSONA_FISICA= :idPersonaFisica		\n";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idPersonaFisica", idPersonaFisica, Types.INTEGER);
			infoAnagrafiche = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<HashMap<String, String>>() {
				@Override
				public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					HashMap<String, String> map = new HashMap();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					while (rs.next()) {
						map.put("nome", rs.getString("NOME"));
						map.put("cognome", rs.getString("COGNOME"));
						if(rs.getDate("DT_NASCITA") != null) {
							map.put("dataNascita", sdf.format(rs.getDate("DT_NASCITA")));
						}
						map.put("idComuneItalianoNascita", String.valueOf(rs.getInt("ID_COMUNE_ITALIANO_NASCITA")));
						map.put("idComuneEsteroNascita", String.valueOf(rs.getInt("ID_COMUNE_ESTERO_NASCITA")));
					}
					return map;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoCorrelato", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettoCorrelato", e);
		} finally {
			LOG.info(prf + " END");
		}

		return infoAnagrafiche;
	}
	
	@Override
	public HashMap<String, String> getInfoComune(String idComuneItalianoNascita) {
		String prf = "[ElencoSoggettiCorrelatiDAOImpl::getInfoComune]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> infoComune = new HashMap<>();
		LOG.info(prf + ", idComuneItalianoNascita=" + idComuneItalianoNascita);
		try {
			String sql = "SELECT DESC_COMUNE,					\n" + 
					"       COD_ISTAT_COMUNE					\n" + 
					"FROM PBANDI_D_COMUNE pdc					\n" + 
					"WHERE ID_COMUNE= :idComuneItalianoNascita	\n";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idComuneItalianoNascita", idComuneItalianoNascita, Types.INTEGER);
			infoComune = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<HashMap<String, String>>() {
				@Override
				public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					HashMap<String, String> map = new HashMap();
					while (rs.next()) {
						map.put("codiceComuneNascita", rs.getString("COD_ISTAT_COMUNE"));
						map.put("descrizioneComuneNascita", rs.getString("DESC_COMUNE"));
					}
					return map;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoCorrelato", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettoCorrelato", e);
		} finally {
			LOG.info(prf + " END");
		}

		return infoComune;
	}
	
	@Override
	public HashMap<String, String> getInfoComuneEstero(String idComuneEsteroNascita) {
		String prf = "[ElencoSoggettiCorrelatiDAOImpl::getInfoComuneEstero]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> infoComune = new HashMap<>();
		LOG.info(prf + ", idComuneEsteroNascita=" + idComuneEsteroNascita);
		try {
			String sql = "SELECT DESC_COMUNE_ESTERO						\n" + 
					"FROM PBANDI_D_COMUNE_ESTERO pdce					\n" + 
					"WHERE ID_COMUNE_ESTERO= :idComuneEsteroNascita 	\n";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idComuneEsteroNascita", idComuneEsteroNascita, Types.INTEGER);
			infoComune = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<HashMap<String, String>>() {
				@Override
				public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					HashMap<String, String> map = new HashMap();
					while (rs.next()) {
						map.put("descrizioneComuneEsteroNascita", rs.getString("DESC_COMUNE_ESTERO"));
					}
					return map;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoCorrelato", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettoCorrelato", e);
		} finally {
			LOG.info(prf + " END");
		}

		return infoComune;
	}
	
	@Override
	public HashMap<String, String> getInfoRecapiti(Integer idRecapitiPersonaFisica) {
		String prf = "[ElencoSoggettiCorrelatiDAOImpl::getInfoRecapiti]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> infoRecapiti = new HashMap<>();
		LOG.info(prf + ", idRecapitiPersonaFisica=" + idRecapitiPersonaFisica);
		try {
			String sql = "SELECT EMAIL,								\n" + 
					"       TELEFONO								\n" + 
					"FROM PBANDI_T_RECAPITI ptr						\n" + 
					"WHERE ID_RECAPITI= :idRecapitiPersonaFisica 	\n";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idRecapitiPersonaFisica", idRecapitiPersonaFisica, Types.INTEGER);
			infoRecapiti = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<HashMap<String, String>>() {
				@Override
				public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					HashMap<String, String> map = new HashMap();
					while (rs.next()) {
						map.put("mail", rs.getString("EMAIL"));
						map.put("telefono", rs.getString("TELEFONO"));
					}
					return map;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoCorrelato", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettoCorrelato", e);
		} finally {
			LOG.info(prf + " END");
		}

		return infoRecapiti;
	}

	@Override
	public HashMap<String, String> getInfoIndirizzo(Integer idIndirizzoPersonaFisica) {
		String prf = "[ElencoSoggettiCorrelatiDAOImpl::getInfoIndirizzo]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> infoIndirizzo = new HashMap<>();
		LOG.info(prf + ", idIndirizzoPersonaFisica=" + idIndirizzoPersonaFisica);
		try {
			String sql = "SELECT DESC_INDIRIZZO,					\n" + 
					"       CIVICO,									\n" + 
					"       CAP										\n" + 
					"FROM PBANDI_T_INDIRIZZO pti					\n" + 
					"WHERE ID_INDIRIZZO= :idIndirizzoPersonaFisica	\n";

			LOG.debug(sql);
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idIndirizzoPersonaFisica", idIndirizzoPersonaFisica, Types.INTEGER);
			infoIndirizzo = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<HashMap<String, String>>() {
				@Override
				public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					HashMap<String, String> map = new HashMap();
					while (rs.next()) {
						map.put("indirizzo", rs.getString("DESC_INDIRIZZO"));
						map.put("civico", rs.getString("CIVICO"));
						map.put("cap", rs.getString("CAP"));
					}
					return map;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoCorrelato", e);
			throw new ErroreGestitoException("DaoException while trying to read SoggettoCorrelato", e);
		} finally {
			LOG.info(prf + " END");
		}

		return infoIndirizzo;
	}

}
