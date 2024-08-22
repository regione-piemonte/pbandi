/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.exception.RecordNotFoundException;
import it.csi.pbandi.pbservwelfare.integration.dao.AbilitazioneRendicontoDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class AbilitazioneRendicontoDAOImpl extends BaseDAO implements AbilitazioneRendicontoDAO {

	private Logger LOG;

	public AbilitazioneRendicontoDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	/**
	 *
	 */
	@Override
	public Boolean isCodiceFiscalePresentePerDomiciliarita(String codiceFiscale, String numeroDomanda) {

		String prf = "[AbilitazioneRendicontoDAOImpl::isCodiceFiscalePresentePerDomiciliarita]";
		LOG.info(prf + " BEGIN");
		Boolean risposta = null;
		LOG.info(prf + " codiceFiscale=" + codiceFiscale + ", numeroDomanda=" + numeroDomanda);
		try {

			String sql = " SELECT  *  FROM                                                   \n"
					+ "   PBANDI_T_SOGGETTO PTS,                                             \n"
					+ "   PBANDI_T_DOMANDA PTD,                                              \n"
					+ "   PBANDI_R_SOGGETTO_DOMANDA PRSD                                     \n"
					+ "  WHERE                                                               \n"
					+ "  PTD.id_domanda= PRSD.id_domanda                                     \n"
					+ "  AND PTS.id_soggetto= PRSD.id_soggetto                               \n"
					+ "   AND PTS.codice_fiscale_soggetto= :codiceFiscale                    \n"
					+ "   AND PTD.numero_domanda= :numeroDomanda                             \n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			risposta = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Boolean>() {
				@Override
				public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {

					Boolean response = false;

					if (rs.next()) {
						response = true;
					} else {
						response = false;
					}
					return response;
				}
			});
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AbilitazioneRendiconto", e);
			throw new ErroreGestitoException("DaoException while trying to read AbilitazioneRendiconto", e);
		} finally {
			LOG.info(prf + " END");
		}
		return risposta;
	}

	/**
	 *
	 */
	@Override
	public Boolean isCodiceFiscalePresentePerResidenzialita(String codiceFiscale) {
		String prf = "[AbilitazioneRendicontoDAOImpl::isCodiceFiscalePresentePerResidenzialita]";
		LOG.info(prf + " BEGIN");
		Boolean risposta = null;
		LOG.info(prf + " codiceFiscale=" + codiceFiscale);
		try {

			String sql = " SELECT  *  FROM                                                   \n"
					+ "   PBANDI_T_FORNITORE ptf                                             \n"
					+ "  WHERE                                                               \n"
					+ "  PTF.CODICE_FISCALE_FORNITORE= :codiceFiscale                        \n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);

			risposta = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Boolean>() {
				@Override
				public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {

					Boolean response = false;

					if (rs.next()) {
						response = true;
					} else {
						response = false;
					}
					return response;
				}
			});
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AbilitazioneRendiconto", e);
			throw new ErroreGestitoException("DaoException while trying to read AbilitazioneRendiconto", e);
		} finally {
			LOG.info(prf + " END");
		}
		return risposta;
	}

	/**
	 *
	 */
	@Override
	public HashMap<String, String> getSoggettoBeneficiario(String numeroDomanda) {
		String prf = "[AbilitazioneRendicontoDAOImpl::getSoggettoBeneficiario]";
		LOG.info(prf + " BEGIN");
		HashMap<String, String> map = new HashMap<>();
		LOG.info(prf + ", numeroDomanda=" + numeroDomanda);
		try {
			String sql = "SELECT PTP.CODICE_VISUALIZZATO,														\n"
					+ "       PTP.ID_PROGETTO,																	\n"		
					+ "       PRSP.ID_SOGGETTO,																	\n"
					+ "       PTS.CODICE_FISCALE_SOGGETTO														\n"
					+ "FROM PBANDI_T_DOMANDA PTD																\n"
					+ "JOIN PBANDI_T_PROGETTO PTP ON PTP.ID_DOMANDA = PTD.ID_DOMANDA							\n"
					+ "JOIN PBANDI_R_SOGGETTO_PROGETTO PRSP ON PRSP.ID_PROGETTO = PTP.ID_PROGETTO				\n"
					+ "JOIN PBANDI_T_SOGGETTO PTS ON PTS.ID_SOGGETTO = PRSP.ID_SOGGETTO							\n"
					+ "WHERE PRSP.ID_TIPO_ANAGRAFICA = 1														\n"
					+ "  AND PRSP.ID_TIPO_BENEFICIARIO <> 4														\n"
					+ "  AND PTD.NUMERO_DOMANDA= :numeroDomanda													\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			map = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<HashMap<String, String>>() {
				@Override
				public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					HashMap<String, String> soggBenef = new HashMap<String, String>();
					while (rs.next()) {
						soggBenef.put("codFiscaleSoggetto", rs.getString("CODICE_FISCALE_SOGGETTO"));
						soggBenef.put("codiceVisualizzato", rs.getString("CODICE_VISUALIZZATO"));
						soggBenef.put("idSoggetto", String.valueOf(rs.getInt("ID_SOGGETTO")));
						soggBenef.put("idProgetto", String.valueOf(rs.getInt("ID_PROGETTO")));
					}
					return soggBenef;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AbilitazioneRendiconto", e);
			throw new ErroreGestitoException("DaoException while trying to read AbilitazioneRendiconto", e);
		} finally {
			LOG.info(prf + " END");
		}

		return map;
	}

	/**
	 *
	 */
	@Override
	public Boolean verificaSoggettoPerDomiciliarita(String idProgetto, String codiceFiscaleUtente) {
		String prf = "[AbilitazioneRendicontoDAOImpl::verificaSoggettoPerDomiciliarita]";
		LOG.info(prf + " BEGIN");
		Boolean isSoggettoAbilitato = false;
		LOG.info(prf + ", idProgetto=" + idProgetto);
		try {

			String sql = "SELECT 																									\n" + 
					"	prsp.PROGR_SOGGETTO_PROGETTO AS PROGR_SOGG_PROG_BENEFICIARIO,												\n" + 
					"	prsp.ID_SOGGETTO AS ID_SOGGETTO_BENEFICIARIO,																\n" + 
					"	pts2.CODICE_FISCALE_SOGGETTO  AS CODICE_FISCALE_BENEFICIARIO,												\n" + 
					"	prsc.PROGR_SOGGETTI_CORRELATI,																				\n" + 
					"	prsc.ID_SOGGETTO AS ID_SOGGETTO_CORRELATO,																	\n" + 
					"	prspsc.PROGR_SOGGETTO_PROGETTO AS PROGR_SOGG_PROG_CORRELATO,												\n" + 
					"	pts.CODICE_FISCALE_SOGGETTO AS COD_FISC_SOGG_CORRELATO														\n" + 
					"FROM PBANDI_R_SOGGETTO_PROGETTO prsp																			\n" + 
					"JOIN PBANDI_T_SOGGETTO pts2 																					\n" + 
					"ON pts2.ID_SOGGETTO = prsp.ID_SOGGETTO 																		\n" + 
					"JOIN PBANDI_R_SOGGETTI_CORRELATI prsc 																			\n" + 
					"ON prsp.ID_SOGGETTO = prsc.ID_SOGGETTO_ENTE_GIURIDICO															\n" + 
					"JOIN PBANDI_T_SOGGETTO pts 																					\n" + 
					"ON pts.ID_SOGGETTO = prsc.ID_SOGGETTO																			\n" + 
					"JOIN PBANDI_R_SOGG_PROG_SOGG_CORREL prspsc 																	\n" + 
					"ON prspsc.PROGR_SOGGETTI_CORRELATI = prsc.PROGR_SOGGETTI_CORRELATI 											\n" + 
					"JOIN PBANDI_R_SOGGETTO_PROGETTO prsp2 																			\n" + 
					"ON prspsc.PROGR_SOGGETTO_PROGETTO = prsp2.PROGR_SOGGETTO_PROGETTO AND prsp2.ID_PROGETTO= :idProgetto			\n" + 
					"WHERE prsp.ID_TIPO_ANAGRAFICA = 1 AND prsp.ID_TIPO_BENEFICIARIO <> 4 AND prsp.ID_PROGETTO= :idProgetto			\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idProgetto", idProgetto, Types.INTEGER);

			isSoggettoAbilitato = this.namedParameterJdbcTemplate.query(sql, param,
					new ResultSetExtractor<Boolean>() {
						@Override
						public Boolean extractData(ResultSet rs)
								throws SQLException, DataAccessException {

							Boolean isAbilitato = false;
							while (rs.next()) {
								String codFiscSoggCorrelato = rs.getString("COD_FISC_SOGG_CORRELATO");
								if(codiceFiscaleUtente.equals(codFiscSoggCorrelato)) {
									isAbilitato = true;
								}
							}
							return isAbilitato;
						}
					});
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AbilitazioneRendiconto", e);
			throw new ErroreGestitoException("DaoException while trying to read AbilitazioneRendiconto", e);
		} finally {
			LOG.info(prf + " END");
		}
		return isSoggettoAbilitato;
	}

	/**
	 *
	 */
	@Override
	public ArrayList<String> getListaCodiceFiscaleFornitore(int idSoggettoBeneficiario) {
		String prf = "[AbilitazioneRendicontoDAOImpl::getListaCodiceFiscaleFornitore]";
		LOG.info(prf + " BEGIN");
		ArrayList<String> listaCodiceFiscaleFornitore = new ArrayList<>();
		LOG.info(prf + ", idSoggettoBeneficiario=" + idSoggettoBeneficiario);
		try {

			String sql = "SELECT CODICE_FISCALE_FORNITORE							\n" 
					+ "FROM PBANDI_T_FORNITORE PTF									\n"
					+ "WHERE PTF.ID_SOGGETTO_FORNITORE= :idSoggettoBeneficiario		\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggettoBeneficiario", idSoggettoBeneficiario, Types.INTEGER);

			listaCodiceFiscaleFornitore = this.namedParameterJdbcTemplate.query(sql, param,
					new ResultSetExtractor<ArrayList<String>>() {
						@Override
						public ArrayList<String> extractData(ResultSet rs) throws SQLException, DataAccessException {

							ArrayList<String> list = new ArrayList<>();
							while (rs.next()) {
								String codiceFiscale = rs.getString("CODICE_FISCALE_FORNITORE");
								list.add(codiceFiscale);
							}
							return list;
						}
					});
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AbilitazioneRendiconto", e);
			throw new ErroreGestitoException("DaoException while trying to read AbilitazioneRendiconto", e);
		} finally {
			LOG.info(prf + " END");
		}
		return listaCodiceFiscaleFornitore;
	}

	@Override
	public String verificaAbilitazioneARendicontare(String idProgetto) {
		String prf = "[AbilitazioneRendicontoDAOImpl::verificaAbilitazioneARendicontare]";
		LOG.info(prf + " BEGIN");
		String descBreveStatoProgetto = "";
		LOG.info(prf + ", idProgetto=" + idProgetto);
		try {

			String sql = "SELECT DESC_BREVE_STATO_PROGETTO						\n"
					+ "FROM PBANDI_T_PROGETTO PTP,								\n"
					+ "     PBANDI_D_STATO_PROGETTO PSDP						\n"
					+ "WHERE PTP.ID_STATO_PROGETTO = PSDP.ID_STATO_PROGETTO		\n" 
					+ "  AND ID_PROGETTO= :idProgetto							\n";

			LOG.debug(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idProgetto", idProgetto, Types.VARCHAR);

			descBreveStatoProgetto = this.namedParameterJdbcTemplate.query(sql, param,
					new ResultSetExtractor<String>() {
						@Override
						public String extractData(ResultSet rs) throws SQLException, DataAccessException {
							String result = "";
							while (rs.next()) {
								result = rs.getString("DESC_BREVE_STATO_PROGETTO");
							}
							return result;
						}
					});
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read AbilitazioneRendiconto", e);
			throw new ErroreGestitoException("DaoException while trying to read AbilitazioneRendiconto", e);
		} finally {
			LOG.info(prf + " END");
		}
		return descBreveStatoProgetto;
	}

}
