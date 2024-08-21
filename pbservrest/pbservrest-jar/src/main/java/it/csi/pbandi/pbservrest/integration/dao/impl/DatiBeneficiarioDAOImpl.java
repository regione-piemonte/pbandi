/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservrest.dto.DatiBeneficiario;
import it.csi.pbandi.pbservrest.dto.Esito;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.InternalUnexpectedException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.BaseDAO;
import it.csi.pbandi.pbservrest.integration.dao.DatiBeneficiarioDAO;
import it.csi.pbandi.pbservrest.model.SoggettoEProgressivoTmp;
import it.csi.pbandi.pbservrest.util.Constants;

public class DatiBeneficiarioDAOImpl extends BaseDAO implements DatiBeneficiarioDAO {

	private Logger LOG;
	
	SimpleDateFormat oracleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private Esito esito;
	private List<Esito> esitoList = null;

	public DatiBeneficiarioDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}
	
	// q0: test connessione al database
	@Override
	public boolean testConnection() {

		String sql = "SELECT 1 FROM DUAL";
        MapSqlParameterSource params = new MapSqlParameterSource();
        try {
            List<Integer> result = namedParameterJdbcTemplate.query(sql, params, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getInt(1);
                }
            });
            
            boolean isConnected = result != null && !result.isEmpty();
            LOG.info("Stato connessione data base: " + (isConnected ? "Connesso" : "Disconnesso"));
            return true;
        } catch (DataAccessException ex) {
        	LOG.error("Errore 003 connessione al database: " + ex.getMessage());
        }
        
		return false;
	}

	// q1: verifico cf presente a db ed associato a numero domanda
	@Override
	public Boolean isCorrect(String codiceFiscale, String numeroDomanda) {

		String prf = "[DatiBeneficiarioDAOImpl::isCorrect]";
		LOG.info(prf + " BEGIN");

		Boolean risposta = null;

		LOG.info(prf + " codiceFiscale=" + codiceFiscale + ", numeroDomanda=" + numeroDomanda);

		try {

			String sql = " SELECT * \n" 
					+ "FROM PBANDI_T_SOGGETTO PTS, PBANDI_T_DOMANDA PTD, \n"
					+ "PBANDI_R_SOGGETTO_DOMANDA PRSD \n" 
					+ "WHERE PTD.id_domanda= PRSD.id_domanda \n"
					+ "AND PTS.id_soggetto= PRSD.id_soggetto \n"
					+ "AND PTS.codice_fiscale_soggetto= :codiceFiscale \n"
					+ "AND PTD.numero_domanda= :numeroDomanda \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			risposta = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Boolean>() {

				@Override
				public Boolean extractData(ResultSet rs1) throws SQLException, DataAccessException, DataAccessException {

					Boolean response = false;

					if (rs1.next()) {
						response = true;
					} else {
						response = false;
					}
					return response;
				}
			});

			LOG.info(prf + " risposta: " + risposta);

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiario", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}
		return risposta;
	}

	
	// q13:
	@Override
	public boolean idSoggettoExist(int idSoggetto) {
		
		String prf = "[DatiBeneficiarioDAOImpl::idSoggettoExist]";
		LOG.info(prf + " BEGIN");
		
		Boolean risposta = null;
		LOG.info(prf + " idSoggetto=" + idSoggetto);
		try {

			String sql = "SELECT * \n"
					+ "FROM PBANDI_T_SOGGETTO_RATING \n"
					+ "WHERE id_soggetto= :idSoggetto \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

			risposta = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Boolean>() {
				
				@Override
				public Boolean extractData(ResultSet rs2) throws SQLException, DataAccessException {

					Boolean response = false;

					if (rs2.next()) {
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
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}
		return risposta;

	}

	
	// q2: recupero soggetto e progressivo by (numeroDomanda)
	@Override
	public List<SoggettoEProgressivoTmp> getSoggettoEProg(String numeroDomanda) {

		String prf = "[DatiBeneficiarioDAOImpl::getSoggettoEProg]";
		LOG.info(prf + " BEGIN");

		List<SoggettoEProgressivoTmp> dati = null;

		LOG.info(prf + "numeroDomanda=" + numeroDomanda);
		try {

			String sql = "SELECT PTS.ID_TIPO_SOGGETTO, PRSD.PROGR_SOGGETTO_DOMANDA \n" 
					+ "FROM PBANDI_T_SOGGETTO PTS \n"
					+ "JOIN PBANDI_R_SOGGETTO_DOMANDA PRSD ON PRSD.ID_SOGGETTO = PTS.ID_SOGGETTO \n"
					+ "JOIN PBANDI_T_DOMANDA PTD ON PTD.id_domanda= PRSD.id_domanda \n"
					+ "AND PRSD.id_tipo_anagrafica=1 \n" 
					+ "AND PRSD.id_tipo_beneficiario<>4 \n"
					+ "WHERE PTD.NUMERO_DOMANDA= :numeroDomanda \n"
					+ "AND PRSD.dt_fine_validita IS NULL\n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			dati = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<SoggettoEProgressivoTmp>>() {

						@Override
						public List<SoggettoEProgressivoTmp> extractData(ResultSet rs3)
								throws SQLException, DataAccessException {

							List<SoggettoEProgressivoTmp> elencoDati = null;

							while (rs3.next()) {
								elencoDati = new ArrayList<SoggettoEProgressivoTmp>();

								SoggettoEProgressivoTmp soggProg = new SoggettoEProgressivoTmp();
								soggProg.setIdTipoSoggetto(rs3.getInt("ID_TIPO_SOGGETTO"));
								soggProg.setProgrSoggettoDomanda(rs3.getInt("PROGR_SOGGETTO_DOMANDA"));

								elencoDati.add(soggProg);
							}

							LOG.info(prf + " elencoDati: " + elencoDati.size());
							return elencoDati;
						}
					});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}
	

	// q3: recupero (cod_dimensione_impresa e dt_valutazione_esito)
	@Override
	public List<DatiBeneficiario> getDimensioneEValutazione(String codiceFiscale, String numeroDomanda) {

		String prf = "[DatiBeneficiarioDAOImpl::getDimensioneEValutazione]";
		LOG.info(prf + " BEGIN");
		
		List<DatiBeneficiario> dati = null;
		LOG.info(prf + " codiceFiscale=" + codiceFiscale + ", numeroDomanda=" + numeroDomanda);
		
		String idBeneficiario = codiceFiscale + "-" + numeroDomanda;
		
		LOG.info(" idBeneficiario=" + idBeneficiario);
		
		String sql = "SELECT DISTINCT \n"
				+ "    PDDI.cod_dimensione_impresa, \n"
				+ "    PTEG.dt_valutazione_esito, \n"
				+ "    PTR.PEC \n"
				+ "FROM PBANDI_D_DIMENSIONE_IMPRESA PDDI \n"
				+ "JOIN PBANDI_T_ENTE_GIURIDICO PTEG ON PDDI.id_dimensione_impresa= PTEG.id_dimensione_impresa \n"
				+ "JOIN PBANDI_R_SOGGETTO_DOMANDA PRSD ON PRSD.id_ente_giuridico= PTEG.id_ente_giuridico \n"
				+ "AND PRSD.ID_SOGGETTO = PTEG.ID_SOGGETTO \n"
				+ "JOIN PBANDI_T_DOMANDA PTD ON PTD.id_domanda= PRSD.id_domanda \n"
				+ "AND PRSD.id_tipo_anagrafica=1 \n"
				+ "AND PRSD.id_tipo_beneficiario<>4 \n"
				+ "JOIN PBANDI_R_SOGGETTO_DOMANDA_SEDE PRSDS ON PRSDS.PROGR_SOGGETTO_DOMANDA = PRSD.PROGR_SOGGETTO_DOMANDA \n"
				+ "LEFT JOIN PBANDI_T_RECAPITI PTR ON PTR.ID_RECAPITI = PRSDS.ID_RECAPITI \n"
				+ "WHERE PTD.NUMERO_DOMANDA= :numeroDomanda \n"
				+ "AND pteg.dt_fine_validita IS NULL \n"
				+ "AND prsd.dt_fine_validita IS NULL \n";
		
		LOG.info(sql);
		
		try {

			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("numeroDomanda", numeroDomanda);
			
			dati = namedParameterJdbcTemplate.query(sql, parameters, new ResultSetExtractor<List<DatiBeneficiario>>() {
				
				@Override
				public List<DatiBeneficiario> extractData(ResultSet rs4) throws SQLException, DataAccessException {

					List<DatiBeneficiario> elencoDati = new ArrayList<DatiBeneficiario>();
					
					while (rs4.next()) {
						DatiBeneficiario item = new DatiBeneficiario();
						item.setIdDataBeneficiario(idBeneficiario);
						item.setCodiceDimensioneImpresa(rs4.getString("cod_dimensione_impresa"));
						item.setDtValutazioneEsito(rs4.getDate("dt_valutazione_esito"));
						item.setPec(rs4.getString("pec"));
						elencoDati.add(item);
					}
					
					return elencoDati;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiario", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}
		return dati;
	}
	

	// q4:
	@Override
	public List<DatiBeneficiario> getDscrizioni(int prog) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getDscrizioni]";
		LOG.info(prf + " BEGIN");
		
		List<DatiBeneficiario> dati4 = null;
		
		LOG.info(prf +"prog=" + prog);
		
		String sql =" SELECT                                                             \n"
				+ "   PTI.desc_indirizzo, PTI.cap,                                       \n"
				+ "   PDN.desc_nazione,                                                  \n"
				+ "   PDP.desc_provincia,                                                \n"
				+ "   PDC.desc_comune,                                                   \n"
				+ "   PDCE.desc_comune_estero,                                           \n"
				+ "   PTR.PEC                                                            \n"
				+ " FROM                                                                 \n"
				+ "   PBANDI_D_NAZIONE PDN,                                              \n"
				+ "   PBANDI_D_PROVINCIA PDP,                                            \n"
				+ "   PBANDI_D_COMUNE PDC,                                               \n"
				+ "   PBANDI_D_COMUNE_ESTERO PDCE,                                       \n"
				+ "   PBANDI_T_INDIRIZZO PTI,                                            \n"
				+ "   PBANDI_R_SOGGETTO_DOMANDA_SEDE PRSDS,                              \n"
				+ "   PBANDI_T_RECAPITI PTR                                              \n"
				+ " WHERE                                                                \n"
				+ "  PTI.id_nazione= PDN.id_nazione                                      \n"
				+ "  AND PTI.id_comune= PDC.id_comune                                    \n"
				+ "  AND PTI.id_comune_estero= PDCE.id_comune_estero                     \n"
				+ "  AND PTI.id_provincia= PDP.id_provincia                              \n"
				+ "  AND PRSDS.id_indirizzo= PTI.id_indirizzo                            \n"
				+ "  AND PTR.ID_RECAPITI= PRSDS.ID_RECAPITI                              \n"
				+ "  AND PRSDS.id_tipo_sede=1                                            \n"
				+ "  AND PROGR_SOGGETTO_DOMANDA= :prog                                   \n";
		
		try {
	
			LOG.info(sql);
	
			MapSqlParameterSource param4 = new MapSqlParameterSource(); 
			param4.addValue("prog", prog, Types.VARCHAR);   
	
			dati4 = this.namedParameterJdbcTemplate.query(sql, param4, new ResultSetExtractor<List<DatiBeneficiario>>() {
				
				@Override
				public List<DatiBeneficiario> extractData(ResultSet rs5) throws SQLException, DataAccessException  {
					List<DatiBeneficiario> elencoDati = new ArrayList<>();
				
					while(rs5.next())
					{
						DatiBeneficiario item = new DatiBeneficiario();
						item.setCap(rs5.getString("cap"));
						item.setPec(rs5.getString("PEC"));
						item.setDescIndirizzo(rs5.getString("desc_indirizzo"));
						item.setDescComune(rs5.getString("desc_comune"));
						item.setDescComuneEstero(rs5.getString("desc_comune_estero"));
						item.setDescNazione(rs5.getString("desc_nazione"));
						item.setDescProvincia(rs5.getString("desc_provincia"));
						elencoDati.add(item);
					}
					return elencoDati;
				}
		   });
			
		}catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}
		return dati4;
	}

	
	
	// q5: recupera descProvider e codiceRating
	@Override
	public List<DatiBeneficiario> getProviderERating(String idDataBen, String numeroDomanda) {

		String prf = "[DatiBeneficiarioDAOImpl::getProviderERating]";
		LOG.info(prf + " BEGIN");

		List<DatiBeneficiario> dati = null;
		
		LOG.info(prf + "q5: idDataBen=" + idDataBen + ", numeroDomanda=" + numeroDomanda);
		
		String sql = "SELECT * \n"
				+ "FROM ( \n"
				+ "SELECT pdp.desc_provider, pdr.codice_rating, ptsr.dt_classificazione \n"
				+ "FROM PBANDI_D_PROVIDER pdp \n"
				+ "JOIN PBANDI_D_RATING pdr ON pdr.id_provider = pdp.id_provider \n"
				+ "JOIN PBANDI_T_SOGGETTO_RATING ptsr ON ptsr.id_rating = pdr.id_rating \n"
				+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.ID_SOGGETTO = ptsr.ID_SOGGETTO \n"
				+ "JOIN PBANDI_T_DOMANDA ptd ON ptd.id_domanda= prsd.id_domanda \n"
				+ "WHERE ptd.NUMERO_DOMANDA= :numeroDomanda \n"
				+ "AND ptsr.dt_fine_validita IS NULL \n"
				+ "AND prsd.id_tipo_anagrafica=1 \n"
				+ "AND prsd.id_tipo_beneficiario<>4 \n"
				+ "AND prsd.dt_fine_validita IS NULL \n"
				+ "ORDER BY ID_SOGGETTO_RATING DESC) a \n"
				+ "WHERE ROWNUM = 1 \n";

		try {

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);
			
			dati = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<DatiBeneficiario>>() {
			
				@Override
				public List<DatiBeneficiario> extractData(ResultSet rs6) throws SQLException  {
				
					List<DatiBeneficiario> elencoDati = new ArrayList<>();
					
					boolean recordsFound = false;
					
					while(rs6.next()) {
						recordsFound = true;
						
						DatiBeneficiario item = new DatiBeneficiario();
						
						if (rs6.getString("codice_rating") != null) {
							item.setIdDataBeneficiario(idDataBen);
							item.setDescProvider(rs6.getString("desc_provider"));
							item.setCodiceRating(rs6.getString("codice_rating"));
							item.setDtClassificazione(rs6.getDate("dt_classificazione"));
						} 
						else {
							LOG.info("Il codice rating non è presente nella base dati.");
						}
						elencoDati.add(item);
					}

					if(!recordsFound) {
						// eseguo blocco di codice else
						LOG.info("[descrizione provider] , [codice rating] e [data classificazione] - Nessun record trovato per il seguente numero di domanda: ["+numeroDomanda+"]");
					}
					
					return elencoDati;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiario", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}
		return dati;
	}
	

	
	// q6:
	@Override
	public List<DatiBeneficiario> getDescrClasseRischio(String idDataBen, String numeroDomanda) {

		String prf = "[DatiBeneficiarioDAOImpl::getDescrClasseRischio]";
		LOG.info(prf + " BEGIN");

		List<DatiBeneficiario> dati6 = null;

		LOG.info(prf + " idDataBen=" + idDataBen + ", numeroDomanda=" + numeroDomanda);
		
		String sql = "SELECT * \n"
				+ "FROM ( \n"
				+ "	SELECT PDCR.desc_breve_classe_rischio \n"
				+ "	 FROM PBANDI_D_CLASSE_RISCHIO PDCR \n"
				+ "	 JOIN PBANDI_T_SOGGETTO_CLA_RISCHIO PTSCR ON PTSCR.id_classe_rischio= PDCR.id_classe_rischio \n"
				+ "	 JOIN PBANDI_R_SOGGETTO_DOMANDA PRSD ON PRSD.ID_SOGGETTO = PTSCR.ID_SOGGETTO \n"
				+ "	 AND PTSCR.dt_fine_validita IS NULL \n"
				+ "	 JOIN PBANDI_T_DOMANDA PTD ON PTD.id_domanda= PRSD.id_domanda \n"
				+ "	 AND PRSD.id_tipo_anagrafica=1 \n"
				+ "	 AND PRSD.id_tipo_beneficiario<>4 \n"
				+ "	 WHERE PTD.NUMERO_DOMANDA= :numeroDomanda \n"
				+ "	ORDER BY PTSCR.ID_SOGGETTO_CLA_RISCHIO DESC) B \n"
				+ "WHERE ROWNUM=1 \n";

		try {

			LOG.info(sql);

			MapSqlParameterSource param6 = new MapSqlParameterSource();
			param6.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);

			dati6 = this.namedParameterJdbcTemplate.query(sql, param6, new ResultSetExtractor<List<DatiBeneficiario>>() {

				@Override
				public List<DatiBeneficiario> extractData(ResultSet rs7) throws SQLException, DataAccessException  {
					List<DatiBeneficiario> elencoDati = new ArrayList<>();
					
					boolean recordsFound = false;
					
					while (rs7.next()) {
						recordsFound = true;
						DatiBeneficiario item = new DatiBeneficiario();
						item.setIdDataBeneficiario(idDataBen);
						item.setDesBreveClasseRischio(rs7.getString("desc_breve_classe_rischio"));
						elencoDati.add(item);
					}
					
					if(!recordsFound) {
						// eseguo blocco di codice else
						LOG.info("[identificativo classe di rischio] non risulta presente nella base dati per il numero di domanda: ["+numeroDomanda+"]");
					}
					
					return elencoDati;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiario", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}

		return dati6;
	}

	
	
	// q7:
	@Override
	public List<DatiBeneficiario> getDescrizioniSoggettoFisico(int prog, String idDataBen) {

		String prf = "[DatiBeneficiarioDAOImpl::getDescrizioniSoggettoFisico]";
		LOG.info(prf + " BEGIN");

		List<DatiBeneficiario> dati = null;

		LOG.info(prf + " prog= " + prog);
		LOG.info(prf + " idDataBen= " + idDataBen);

		String sql = "SELECT \n"
				+ "    b.cap, \n"
				+ "    b.desc_indirizzo, \n"
				+ "    c.desc_comune, \n"
				+ "    d.desc_provincia, \n"
				+ "    e.desc_nazione, \n"
				+ "    g.pec \n"
				+ "FROM PBANDI_R_SOGGETTO_DOMANDA a \n"
				+ "LEFT join PBANDI_T_INDIRIZZO b ON a.id_indirizzo_persona_fisica = b.id_indirizzo \n"
				+ "AND b.dt_fine_validita IS NULL \n"
				+ "LEFT JOIN PBANDI_D_COMUNE c ON b.id_comune = c.id_comune \n"
				+ "LEFT JOIN PBANDI_D_PROVINCIA d ON b.id_provincia = d.id_provincia \n"
				+ "LEFT JOIN PBANDI_D_NAZIONE e ON b.id_nazione = e.id_nazione \n"
				+ "LEFT JOIN PBANDI_T_RECAPITI g ON a.id_recapiti_persona_fisica = g.id_recapiti \n"
				+ "AND g.dt_fine_validita IS NULL \n"
				+ "WHERE a.progr_soggetto_domanda = :prog \n"
				+ "AND ROWNUM = 1 \n";

		try {

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("prog", prog, Types.VARCHAR);

			dati = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<DatiBeneficiario>>() {

				@Override
				public List<DatiBeneficiario> extractData(ResultSet rs8) throws SQLException, DataAccessException {
					List<DatiBeneficiario> elencoDati = new ArrayList<>();

					while (rs8.next()) {
						DatiBeneficiario item = new DatiBeneficiario();
						item.setIdDataBeneficiario(idDataBen);
						item.setCap(rs8.getString("cap"));
						item.setPec(rs8.getString("PEC"));
						item.setDescIndirizzo(rs8.getString("desc_indirizzo"));
						item.setDescComune(rs8.getString("desc_comune"));
						item.setDescNazione(rs8.getString("desc_nazione"));
						item.setDescProvincia(rs8.getString("desc_provincia"));

						elencoDati.add(item);
					}
					return elencoDati;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiario", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}
		return dati;
	}

	
	
	// q8: insert into PBANDI_T_ENTE_GIURIDICO
	@Override
	public void insertDimensioneEValutazione(int idSoggetto, int idDimImpresa, Date dtValutazioneEsito) {
		
		String prf = "[DatiBeneficiarioDAOImpl::insertDimensioneEValutazione]";
		LOG.info(prf + " BEGIN");

		String query = " INSERT INTO PBANDI_T_ENTE_GIURIDICO \n"
				+ "(ID_SOGGETTO, DENOMINAZIONE_ENTE_GIURIDICO, DT_COSTITUZIONE, \n"
				+ "CAPITALE_SOCIALE, ID_ATTIVITA_UIC, ID_FORMA_GIURIDICA, \n"
				+ "ID_CLASSIFICAZIONE_ENTE, ID_DIMENSIONE_IMPRESA,ID_ENTE_GIURIDICO, \n"
				+ "DT_INIZIO_VALIDITA, DT_FINE_VALIDITA, DT_ULTIMO_ESERCIZIO_CHIUSO, \n"
				+ "ID_UTENTE_INS, ID_UTENTE_AGG, FLAG_PUBBLICO_PRIVATO, \n"
				+ "COD_UNI_IPA, ID_STATO_ATTIVITA, DT_VALUTAZIONE_ESITO, PERIODO_SCADENZA_ESERCIZIO, \n"
				+ "DT_INIZIO_ATTIVITA_ESITO, FLAG_RATING_LEGALITA)  \n"
				+ "SELECT \n"
				+ ":idSoggetto ID_SOGGETTO, DENOMINAZIONE_ENTE_GIURIDICO, DT_COSTITUZIONE, \n"
				+ "CAPITALE_SOCIALE, ID_ATTIVITA_UIC, ID_FORMA_GIURIDICA, \n"
				+ "ID_CLASSIFICAZIONE_ENTE, :idDimImpresa ID_DIMENSIONE_IMPRESA, SEQ_PBANDI_T_ENTE_GIURIDICO.NEXTVAL, \n"
				+ "CURRENT_DATE AS DT_INIZIO_VALIDITA, DT_FINE_VALIDITA, DT_ULTIMO_ESERCIZIO_CHIUSO, \n"
				+ "1 AS ID_UTENTE_INS, null AS ID_UTENTE_AGG, FLAG_PUBBLICO_PRIVATO, \n"
				+ "COD_UNI_IPA, ID_STATO_ATTIVITA, :dtValutazioneEsito DT_VALUTAZIONE_ESITO, PERIODO_SCADENZA_ESERCIZIO, \n"
				+ "DT_INIZIO_ATTIVITA_ESITO, FLAG_RATING_LEGALITA \n"
				+ "FROM PBANDI_T_ENTE_GIURIDICO \n"
				+ "WHERE ID_SOGGETTO = :idSoggetto \n"
				+ "AND ID_ENTE_GIURIDICO = (SELECT max(ID_ENTE_GIURIDICO) \n"
				+ "FROM PBANDI_T_ENTE_GIURIDICO \n"
				+ "WHERE ID_SOGGETTO = :idSoggetto) \n";

		LOG.info(query);
		LOG.info("Dati:" + idDimImpresa + ", " + dtValutazioneEsito);

		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
		param.addValue("idDimImpresa", idDimImpresa, Types.INTEGER);
		param.addValue("dtValutazioneEsito", dtValutazioneEsito, Types.DATE);

		try {
			
			int rowsInserted = this.update(query, param);
            
			if (rowsInserted > 0) {
				LOG.info("L'inserimento è avvenuto con successo.");
			} else {
				LOG.info("L'inserimento non è riuscito.");
			}
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(" END");
		}
	}

	
	// q:9
	@Override
	public int getIdSoggetto(String codiceFiscale) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getIdSoggetto]";
		LOG.info(prf + " BEGIN");
		
		int idSoggetto;
		try {
			String query = "SELECT id_soggetto \n"
					+ "FROM PBANDI_T_SOGGETTO \n"
					+ "WHERE codice_fiscale_soggetto = :codiceFiscale \n";

			LOG.info(query);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("codiceFiscale", codiceFiscale, Types.VARCHAR);

			idSoggetto = this.namedParameterJdbcTemplate.query(query, param, new ResultSetExtractor<Integer>() {
				
				@Override
				public Integer extractData(ResultSet rs9) throws SQLException, DataAccessException {

					int idSog = 0;
					while (rs9.next()) {
						idSog = rs9.getInt("id_soggetto");
					}
					return idSog;
				}
				
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(" END");
		}
		return idSoggetto;
	}

	
	// q10:
	@Override
	public void updateEnteGiuridico(int idEnteGiuridico, int idSoggetto) {
		
		String prf = "[DatiBeneficiarioDAOImpl::updateEnteGiuridico]";
		LOG.info(prf + " BEGIN");
		
		String query = "UPDATE PBANDI_R_SOGGETTO_DOMANDA \n"
				+ "SET id_ente_giuridico = :idEnteGiuridico \n"
				+ "WHERE id_soggetto = :idSoggetto \n";

		LOG.info(query);
		LOG.info("Query:\n" + query);

		MapSqlParameterSource param = new MapSqlParameterSource();

		param.addValue("idEnteGiuridico", idEnteGiuridico, Types.INTEGER);
		param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

		int rowsUpdated = 0;
		try {
			
			rowsUpdated = this.update(query, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		}

		LOG.info("N. record aggiornati:\n" + rowsUpdated);
	}

	
	
	// q14:
	@Override
	public int getIdDimImpresa(String codDimImpresa) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getIdDimImpresa]";
		LOG.info(prf + " BEGIN");
		int idDimImpresa = 0;
		try {

			String sql = "SELECT id_dimensione_impresa \n"
					+ "FROM PBANDI_D_DIMENSIONE_IMPRESA \n"
					+ "WHERE cod_dimensione_impresa= :codDimImpresa \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("codDimImpresa", codDimImpresa, Types.VARCHAR);

			idDimImpresa = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
				@Override
				public Integer extractData(ResultSet rs10) throws SQLException, DataAccessException {
					int idImp = 0;
					while (rs10.next()) {
						idImp = rs10.getInt("id_dimensione_impresa");
					}
					return idImp;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idDimImpresa;
	}

	
	
	// q11:
	@Override
	public int getIdRating(String codRating, int idProvider) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getIdRating]";
		LOG.info(prf + " BEGIN");
		
		int idRating = 0;
		try {

			String sql = "SELECT id_rating \n"
					+ "FROM PBANDI_D_RATING \n"
					+ "WHERE codice_rating= :codRating \n"
					+ "AND id_provider= :idProvider \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("codRating", codRating, Types.VARCHAR);
			param.addValue("idProvider", idProvider, Types.INTEGER);

			idRating = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
				
				@Override
				public Integer extractData(ResultSet rs11) throws SQLException, DataAccessException {
					int idRat = 0;
					while (rs11.next()) {
						idRat = rs11.getInt("id_rating");
					}
					return idRat;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idRating;
	}

	
	// q16:
	@Override
	public void insertRatingESogg(int idRating, int idSoggetto, Date dtClassificazione) {
		
		String prf = "[DatiBeneficiarioDAOImpl::insertRatingESogg]";
		LOG.info(prf + " BEGIN");
		
		LOG.info("idRating: " + idRating );
		LOG.info("idSoggetto: " + idSoggetto );
		LOG.info("dtClassificazione: " + dtClassificazione );

		String query = "INSERT INTO PBANDI_T_SOGGETTO_RATING \n"
				+ "(id_soggetto_rating, id_soggetto, id_rating, dt_classificazione, dt_inizio_validita, dt_fine_validita, \n"
				+ "id_utente_ins, id_utente_agg, dt_inserimento, dt_aggiornamento ) \n"
				+ "VALUES (COALESCE((SELECT MAX(id_soggetto_rating)+1 FROM PBANDI_T_SOGGETTO_RATING),1), :idSoggetto, :idRating, :dtClassificazione, \n"
				+ "CURRENT_DATE, null, 1, null, CURRENT_DATE, null) \n";

		LOG.info(query);
		LOG.info("Dati:" + idRating + ", " + idSoggetto);

		MapSqlParameterSource param = new MapSqlParameterSource();

		param.addValue("idRating", idRating, Types.INTEGER);
		param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
		param.addValue("dtClassificazione", dtClassificazione, Types.DATE);

		try {
			
			int rowsInserted = this.update(query, param);
            
			if (rowsInserted > 0) {
				LOG.info("L'inserimento è avvenuto con successo.");
            } else {
            	LOG.info("L'inserimento non è riuscito.");
            }
            
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		}
	}

	
	// q12:
	@Override
	public int getVecchioIdRating(int idSoggetto) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getVecchioIdRating]";
		LOG.info(prf + " BEGIN");
		
		int idRating = 0;
		try {

			String sql = "SELECT id_rating \n"
					+ "FROM PBANDI_T_SOGGETTO_RATING \n"
					+ "WHERE id_soggetto= :idSoggetto \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

			idRating = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
				
				@Override
				public Integer extractData(ResultSet rs12) throws SQLException, DataAccessException {
					int idRat = 0;
					while (rs12.next()) {
						idRat = rs12.getInt("id_rating");
					}
					return idRat;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idRating;
	}

	
	// q15:
	@Override
	public void updateDtFineValidita(int idSoggetto) {
		
		String prf = "[DatiBeneficiarioDAOImpl::updateDtFineValidita]";
		LOG.info(prf + " BEGIN");
		
		String query = "UPDATE PBANDI_T_SOGGETTO_RATING \n"
				+ "SET dt_fine_validita = CURRENT_DATE \n"
				+ "WHERE id_soggetto = :idSoggetto \n"
				+ "AND dt_fine_validita IS NULL \n";

		LOG.info(query);
		LOG.info("Query:\n" + query);

		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

		int rowsUpdated = 0;
		
		try {
			rowsUpdated = this.update(query, param);
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		}

		LOG.info("N. record aggiornati:\n" + rowsUpdated);
	}

	
	
	// q17:
	@Override
	public int getIdClasseRischio(String descClasseRischio) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getIdClasseRischio]";
		LOG.info(prf + " BEGIN");
		
		int idClasseRischio = 0;
		try {

			String sql = "SELECT id_classe_rischio \n"
					+ "FROM PBANDI_D_CLASSE_RISCHIO \n"
					+ "WHERE desc_breve_classe_rischio = :desClasseRischio \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("desClasseRischio", descClasseRischio, Types.VARCHAR);

			idClasseRischio = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
				
				@Override
				public Integer extractData(ResultSet rs13) throws SQLException, DataAccessException {
					int idCla = 0;
					while (rs13.next()) {
						idCla = rs13.getInt("id_classe_rischio");
					}
					return idCla;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idClasseRischio;
	}

	
	// q18:
	@Override
	public int getVecchioIdClasseRischio(int idSoggetto) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getVecchioIdClasseRischio]";
		LOG.info(prf + " BEGIN");
		
		int vecchioIdClasseRischio = 0;
		try {

			String sql = "SELECT id_classe_rischio \n"
					+ "FROM PBANDI_T_SOGGETTO_CLA_RISCHIO \n"
					+ "WHERE id_soggetto= :idSoggetto \n"
					+ "AND DT_FINE_VALIDITA IS NULL \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

			vecchioIdClasseRischio = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
						
				@Override
				public Integer extractData(ResultSet rs14) throws SQLException, DataAccessException {
					int idCla = 0;
					while (rs14.next()) {
						idCla = rs14.getInt("id_classe_rischio");
					}
					return idCla;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return vecchioIdClasseRischio;
	}

	
	// q19:
	@Override
	public void insertClasseESogg(int idSoggetto, int idClasseRischio) {
		
		String prf = "[DatiBeneficiarioDAOImpl::insertClasseESogg]";
		LOG.info(prf + " BEGIN");

		LOG.info("Parametri passati: " );
		LOG.info("idSoggetto: " + idSoggetto);
		LOG.info("idClasseRischio: " + idClasseRischio);
		
		String query = "INSERT INTO PBANDI_T_SOGGETTO_CLA_RISCHIO ( id_soggetto_cla_rischio, id_classe_rischio, id_soggetto, dt_inizio_validita, dt_fine_validita, id_utente_ins, id_utente_agg, dt_inserimento, dt_aggiornamento) \n"
				+ "VALUES (COALESCE((SELECT MAX(id_soggetto_cla_rischio)+1 FROM PBANDI_T_SOGGETTO_CLA_RISCHIO),1), :idClasseRischio, :idSoggetto, CURRENT_DATE, null, 1, null, CURRENT_DATE, null ) \n";

		LOG.info(query);

		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idClasseRischio", idClasseRischio, Types.INTEGER);
		param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

		try {
			
			int rowsInserted = this.update(query, param);
            
			if (rowsInserted > 0) {
				LOG.info("L'inserimento è avvenuto con successo.");
			} else {
				LOG.info("L'inserimento non è riuscito.");
			}
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		}
	}

	
	// q20:
	@Override
	public void updateDtFineValiditaClasse(int idSoggetto) {
		
		String prf = "[DatiBeneficiarioDAOImpl::updateDtFineValiditaClasse]";
		LOG.info(prf + " BEGIN");

		LOG.info("Parametri passati: " );
		LOG.info("idSoggetto: " + idSoggetto);
		
		String query = "UPDATE PBANDI_T_SOGGETTO_CLA_RISCHIO \n"
				+ "SET dt_fine_validita = CURRENT_DATE \n"
				+ "WHERE id_soggetto = :idSoggetto \n"
				+ "AND DT_FINE_VALIDITA IS NULL \n";

		LOG.info(query);
		LOG.info("Query:\n" + query);

		MapSqlParameterSource param = new MapSqlParameterSource();

		param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

		int rowsUpdated = 0;
		try {
			
			rowsUpdated = this.update(query, param);
			
		} catch (InternalUnexpectedException e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		}

		LOG.info("N. record aggiornati:\n" + rowsUpdated);
	}
	
	
	
	// q21:
	@Override
	public int getIdEnteGiuridico(int idSoggetto) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getIdEnteGiuridico]";
		LOG.info(prf + " BEGIN");
		
		int idEnteGiuridico = 0;
		try {

			String sql = "SELECT max(ID_ENTE_GIURIDICO) \n"
					+ "FROM PBANDI_T_ENTE_GIURIDICO \n"
					+ "WHERE id_soggetto= :idSoggetto \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

			idEnteGiuridico = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
				@Override
				public Integer extractData(ResultSet rs15) throws SQLException, DataAccessException {
					int idEnte = 0;
					while (rs15.next()) {
						idEnte = rs15.getInt("max(id_ente_giuridico)");
					}
					return idEnte;
				}
			});
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idEnteGiuridico;
	}

	
	// q22:
	@Override
	public int getIdProvider(String descrizioneProvider) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getIdProvider]";
		LOG.info(prf + " BEGIN");
		
		int idProvider = 0;
		try {

			String sql = "SELECT id_provider \n"
					+ "FROM PBANDI_D_PROVIDER \n"
					+ "WHERE desc_provider= :descrizioneProvider \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("descrizioneProvider", descrizioneProvider, Types.VARCHAR);

			idProvider = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
				@Override
				public Integer extractData(ResultSet rs16) throws SQLException, DataAccessException {
					int idProv = 0;
					while (rs16.next()) {
						idProv = rs16.getInt("id_provider");
					}
					return idProv;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idProvider;
	}

	
	// q23:
	@Override
	public boolean trovatoRecord(int idDimImpresa, Date dtValutazioneEsito) {
		
		String prf = "[DatiBeneficiarioDAOImpl::trovatoRecord]";
		LOG.info(prf + " BEGIN");
		
		boolean trovato = false;
		
		LOG.info(prf + "Parametro passato: " + dtValutazioneEsito);
		
        String dtValutazioneEsitoFormatted = oracleDateFormat.format(dtValutazioneEsito);
        LOG.info(prf + "Formattazione: " + dtValutazioneEsitoFormatted);

		try {

			String sql = "SELECT * \n"
					+ "FROM PBANDI_T_ENTE_GIURIDICO \n"
					+ "WHERE id_dimensione_impresa= :idDimImpresa \n"
					+ "AND dt_valutazione_esito= :dtValutazioneEsito "
					+ "AND TRUNC(dt_valutazione_esito) = TO_DATE(:dtValutazioneEsito, 'YYYY-MM-DD') \n"
					+ "AND ROWNUM = 1 \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idDimImpresa", idDimImpresa, Types.INTEGER);
			param.addValue("dtValutazioneEsito", dtValutazioneEsitoFormatted, Types.DATE);

			trovato = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Boolean>() {
				@Override
				public Boolean extractData(ResultSet rs17) throws SQLException, DataAccessException {
					Boolean response = false;

					if (rs17.next()) {
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
			LOG.error(prf + "Exception while trying to read DatiBeneficiario", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiario", e);
		} finally {
			LOG.info(prf + " END");
		}
		return trovato;
	}
	
	
	// q24: recupero idIndirizzo
	@Override
	public int getIdIndirizzo(int progr) {
		
		String prf = "[DatiBeneficiarioDAOImpl::getIdIndirizzo]";
		LOG.info(prf + " BEGIN");
		
		int idIndirizzo = 0;
		try {

			String sql = "SELECT PRSDS.id_indirizzo \n"
					+ "FROM PBANDI_R_SOGGETTO_DOMANDA_SEDE PRSDS \n"
					+ "LEFT JOIN pbandi_t_sede pbts ON prsds.id_sede = pbts.id_sede \n"
					+ "WHERE PRSDS.PROGR_SOGGETTO_DOMANDA= :progr \n"
					+ "AND pbts.dt_fine_validita IS NULL \n"
					+ "AND PRSDS.id_tipo_sede=1";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("progr", progr, Types.INTEGER);

			idIndirizzo = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
				
				@Override
				public Integer extractData(ResultSet rs18) throws SQLException, DataAccessException {
					int idInd = 0;
					while (rs18.next()) {
						idInd = rs18.getInt("id_indirizzo");
					}
					LOG.info("idInd risulta: " + idInd);
					return idInd;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idIndirizzo;
	}
	
	
	@Override
	public List<DatiBeneficiario> getIndirizzoCompleto(int idIndirizzo, String idDataBen) {

		String prf = "[DatiBeneficiarioDAOImpl::getIndirizzoCompleto]";
		LOG.info(prf + " BEGIN");

		List<DatiBeneficiario> dati = null;

		LOG.info(prf + "idIndirizzo= " + idIndirizzo);
		LOG.info(prf + "idDataBen= " + idDataBen);
		
		try {

			String sql = "SELECT \n"
					+ "    pti.desc_indirizzo, \n"
					+ "    pti.cap, \n"
					+ "    pti.id_nazione, \n"
					+ "    pti.id_comune, \n"
					+ "    pti.ID_PROVINCIA, \n"
					+ "    pti.id_comune_estero, \n"
					+ "    pdc.desc_comune, \n"
					+ "    pdp.desc_provincia, \n"
					+ "    pdn.desc_nazione, \n"
					+ "    pdce.desc_comune_estero \n" 
					+ "FROM PBANDI_T_INDIRIZZO pti \n"
					+ "LEFT JOIN PBANDI_D_COMUNE PDC ON pti.ID_COMUNE = pdc.ID_COMUNE \n"
					+ "LEFT JOIN PBANDI_D_PROVINCIA pdp ON  pdp.ID_PROVINCIA = pti.ID_PROVINCIA \n"
					+ "LEFT JOIN PBANDI_D_NAZIONE pdn ON pti.ID_NAZIONE = pdn.ID_NAZIONE \n"
					+ "LEFT JOIN PBANDI_D_COMUNE_ESTERO pdce ON pti.ID_COMUNE_ESTERO = pdce.ID_COMUNE_ESTERO \n"
					+ "WHERE pti.id_indirizzo = :idIndirizzo \n"
					+ "AND pti.dt_fine_validita IS NULL \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idIndirizzo", idIndirizzo, Types.INTEGER);

			dati = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<DatiBeneficiario>>() {

				public List<DatiBeneficiario> extractData(ResultSet rs19) throws SQLException, DataAccessException {

					List<DatiBeneficiario> elencoDati = null;

					while (rs19.next()) {
						elencoDati = new ArrayList<DatiBeneficiario>();

						DatiBeneficiario dataBen = new DatiBeneficiario();
						dataBen.setIdDataBeneficiario(idDataBen);
						dataBen.setDescIndirizzo(rs19.getString("desc_indirizzo"));
						dataBen.setCap(rs19.getString("cap"));
						dataBen.setDescComune(rs19.getString("desc_comune"));
						dataBen.setDescProvincia(rs19.getString("desc_provincia"));
						dataBen.setDescNazione(rs19.getString("desc_nazione"));
						dataBen.setDescComuneEstero(rs19.getString("desc_comune_estero"));
						elencoDati.add(dataBen);
					}

					LOG.info(prf + " elencoDati: " + elencoDati.size());
					return elencoDati;
				}
			});

		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}

		return dati;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

	public List<Esito> getEsitoList() {
		return esitoList;
	}

	public void setEsitoList(List<Esito> esitoList) {
		this.esitoList = esitoList;
	}
}
