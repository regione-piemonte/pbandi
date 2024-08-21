/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservrest.dto.EsitoDurcAntimafia;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.InternalUnexpectedException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.BaseDAO;
import it.csi.pbandi.pbservrest.integration.dao.EsitoDurcAntimafiaDAO;
import it.csi.pbandi.pbservrest.model.EsitoDurcAntimafiaInRest;
import it.csi.pbandi.pbservrest.model.IstruttoreAsfTmp;
import it.csi.pbandi.pbservrest.model.SoggettoDomandaTmp;
import it.csi.pbandi.pbservrest.model.SoggettoRichiestaTmp;
import it.csi.pbandi.pbservrest.util.Constants;

public class EsitoDurcAntimafiaDAOImpl extends BaseDAO implements EsitoDurcAntimafiaDAO {
	
	private Logger LOG;

	public EsitoDurcAntimafiaDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	
	// test connessione al database
	@Override
	public boolean testConnection() {

		String sql = "SELECT 1 FROM DUAL";
        MapSqlParameterSource params = new MapSqlParameterSource();
        try {
            List<Integer> result = namedParameterJdbcTemplate.query(sql, params, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs1, int rowNum) throws SQLException {
                    return rs1.getInt(1);
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
	
	
	/* q1 */
	@Override
	public List<SoggettoDomandaTmp> getEsitoDurcAntimafia(String codFiscaleSoggetto, String numeroDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getEsitoDurcAntimafia]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " codFiscaleBeneficiario: " + codFiscaleSoggetto);
		LOG.info(prf + " numeroDomanda: " + numeroDomanda);

		List<SoggettoDomandaTmp> idSoggettoDomandaList = new ArrayList<SoggettoDomandaTmp>();
		
		try {
			
			String sql1 = "SELECT pts.id_soggetto, ptd.id_domanda \n"
					+ "FROM PBANDI_T_DOMANDA ptd \n"
					+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON ptd.id_domanda = prsd.id_domanda \n"
					+ "AND prsd.DT_FINE_VALIDITA IS NULL \n"
					+ "JOIN PBANDI_T_SOGGETTO pts ON pts.id_soggetto = prsd.id_soggetto \n"
					+ "WHERE prsd.id_tipo_anagrafica = 1 \n"
					+ "AND prsd.id_tipo_beneficiario <> 4 \n"
					+ "AND pts.codice_fiscale_soggetto = :codFiscaleSoggetto \n"
					+ "AND ptd.numero_domanda = :numeroDomanda";
			
			LOG.info(sql1);
			MapSqlParameterSource param1 = new MapSqlParameterSource(); 
			param1.addValue("codFiscaleSoggetto", codFiscaleSoggetto, Types.VARCHAR);
			param1.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);
			
			idSoggettoDomandaList = this.namedParameterJdbcTemplate.query(sql1, param1, new ResultSetExtractor<List<SoggettoDomandaTmp>>() {
				
				public List<SoggettoDomandaTmp> extractData(ResultSet rs2) throws SQLException, DataAccessException {
					
					List<SoggettoDomandaTmp> elencoIdSoggDomanda = new ArrayList<>();
					
					boolean recordsFound = false;
					
					while(rs2.next()) {
						recordsFound = true;
						
						SoggettoDomandaTmp ris = new SoggettoDomandaTmp();
						ris.setIdSoggetto(rs2.getInt("id_soggetto"));
						ris.setIdDomanda(rs2.getInt("id_domanda"));
						elencoIdSoggDomanda.add(ris);
					}

					if(!recordsFound) {
						LOG.info("[id_soggetto] e [id_domanda] - Nessun record trovato per il seguente numero di domanda: ["+numeroDomanda+"]");
					}
					
					return elencoIdSoggDomanda;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoDurcAntimafia", e);
			throw new ErroreGestitoException("DaoException while trying to read EsitoDurcAntimafia", e);
		} finally {
			LOG.info(prf + " END");
		}
		return idSoggettoDomandaList;
	}
	

	
	// q2-Durc
	@Override
	public int getIdSoggettoDurc(Integer idSoggetto) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getIdSoggettoDurc]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro: ");
		LOG.info(prf + " idSoggetto: " + idSoggetto);
		
		int idSoggettoDurc = 0;
		
		try {

			String sql = "SELECT ptsd.id_soggetto_durc \n"
					+ "FROM PBANDI_T_SOGGETTO_DURC ptsd \n"
					+ "WHERE ptsd.id_soggetto  = :idSoggetto \n"
					+ "AND ptsd.dt_scadenza > SYSDATE \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);

			idSoggettoDurc = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
						
				@Override
				public Integer extractData(ResultSet rs3) throws SQLException, DataAccessException {
					int idSogg = 0;
					while (rs3.next()) {
						idSogg = rs3.getInt("ID_SOGGETTO_DURC");
					}
					return idSogg;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idSoggettoDurc;
	}
	
	
	
	/** recupero id soggetto antimafia */
	@Override
	public int getIdSoggettoAntimafia(Integer idDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getIdSoggettoAntimafia]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro: ");
		LOG.info(prf + " idDomanda: " + idDomanda);
		
		int idSoggettoAntimafia = 0;
		
		try {

			String sql = "SELECT ptsa.ID_SOGGETTO_ANTIMAFIA \n"
					+ "FROM PBANDI_T_SOGGETTO_ANTIMAFIA ptsa \n"
					+ "WHERE ptsa.ID_DOMANDA = :idDomanda \n"
					+ "AND ptsa.DT_SCADENZA_ANTIMAFIA > SYSDATE \n";
			
			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idDomanda", idDomanda, Types.INTEGER);

			idSoggettoAntimafia = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
						
				@Override
				public Integer extractData(ResultSet rs4) throws SQLException, DataAccessException {
					int idSogg = 0;
					while (rs4.next()) {
						idSogg = rs4.getInt("ID_SOGGETTO_ANTIMAFIA");
					}
					return idSogg;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idSoggettoAntimafia;
	}
	
	
	@Override
	public int getIdSoggettoAntimafiaBDNA(Integer idDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getIdSoggettoAntimafiaBDNA]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro: ");
		LOG.info(prf + " idDomanda: " + idDomanda);
		
		int idSoggettoAntimafia = 0;
		
		try {

			String sql = "SELECT ptsa.ID_SOGGETTO_ANTIMAFIA \n"
					+ "FROM PBANDI_T_SOGGETTO_ANTIMAFIA ptsa \n"
					+ "WHERE ptsa.ID_DOMANDA = :idDomanda \n"
					+ "AND ptsa.DT_RICEZIONE_BDNA IS NOT NULL \n"
					+ "AND ptsa.DT_SCADENZA_ANTIMAFIA IS NULL \n";
			
			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idDomanda", idDomanda, Types.INTEGER);

			idSoggettoAntimafia = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
						
				@Override
				public Integer extractData(ResultSet rs4) throws SQLException, DataAccessException {
					int idSogg = 0;
					while (rs4.next()) {
						idSogg = rs4.getInt("ID_SOGGETTO_ANTIMAFIA");
					}
					return idSogg;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idSoggettoAntimafia;
	}
	
	
	// q2b-Dsan
	@Override
	public int getIdSoggettoDsan(Integer idDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getIdSoggettoDsan]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro: ");
		LOG.info(prf + " idDomanda: " + idDomanda);
		
		int idSoggettoDsan = 0;
		
		try {

			String sql = "SELECT ptsd.id_soggetto_dsan \n"
					+ "FROM PBANDI_T_SOGGETTO_DSAN ptsd \n"
					+ "WHERE ptsd.id_domanda  = :idDomanda \n"
					+ "AND ptsd.dt_emissione_dsan IS NOT NULL \n"
					+ "AND ptsd.dt_scadenza > SYSDATE \n";

			LOG.info(sql);

			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idDomanda", idDomanda, Types.INTEGER);

			idSoggettoDsan = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<Integer>() {
						
				@Override
				public Integer extractData(ResultSet rs5) throws SQLException, DataAccessException {
					int idSogg = 0;
					while (rs5.next()) {
						idSogg = rs5.getInt("ID_SOGGETTO_DSAN");
					}
					return idSogg;
				}
			});
			
		} catch (RecordNotFoundException e) {
			throw e;
		} finally {
			LOG.info(prf + " END");
		}
		return idSoggettoDsan;
	}
	

	
	// q3: Aggiungo in response numeroDomanda in data: 8 giu 2023 rif.: A.Romano
	@Override
	public List<EsitoDurcAntimafia> getDatiDurc(Integer idSoggettoDurc, Integer idDomanda, String numeroDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getUtenti]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro");
		LOG.info(prf + " idSoggettoDurc: " + idSoggettoDurc);
		LOG.info(prf + " idDomanda: " + idDomanda);
		LOG.info(prf + " numeroDomanda: " + numeroDomanda);
		
		List<EsitoDurcAntimafia> esitoUtenti = new ArrayList<EsitoDurcAntimafia>();
		try {
			String sql = "SELECT pdter.desc_esito_richieste, \n"
					+ "	ptsd.DT_EMISSIONE_DURC, \n"
					+ "	ptsd.DT_SCADENZA \n"
					+ "FROM PBANDI_T_SOGGETTO_DURC ptsd \n"
					+ "JOIN PBANDI_D_TIPO_ESITO_RICHIESTE pdter ON ptsd.ID_TIPO_ESITO_DURC = pdter.ID_TIPO_ESITO_RICHIESTE \n"
					+ "WHERE ID_SOGGETTO_DURC = :idSoggettoDurc";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idSoggettoDurc", idSoggettoDurc, Types.INTEGER);
			
			esitoUtenti = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<EsitoDurcAntimafia>>() {
				
				public List<EsitoDurcAntimafia> extractData(ResultSet rs6) throws SQLException, DataAccessException {
					
					List<EsitoDurcAntimafia> elencoEsitoUtenti= new ArrayList<>();
					
					/**
					 *	DESC_ESITO_RICHIESTE | DT_EMISSIONE_DURC | DT_SCADENZA
					 */
					while(rs6.next()) {
						EsitoDurcAntimafia esito = new EsitoDurcAntimafia();
						esito.setIdDomanda(String.valueOf(idDomanda));
						esito.setNumeroDomanda(numeroDomanda);
						esito.setEsito(rs6.getString("DESC_ESITO_RICHIESTE"));
						esito.setDtRicezioneDocumentazione(rs6.getDate("DT_EMISSIONE_DURC"));
						esito.setDtScadenza(rs6.getDate("DT_SCADENZA"));
						elencoEsitoUtenti.add(esito);
					}
					
					return elencoEsitoUtenti;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiarioVO", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiarioVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return esitoUtenti;
	}
	
	
	// q3b
	@Override
	public List<EsitoDurcAntimafia> getDatiDsan(Integer idSoggettoDsan, Integer idDomanda, String numeroDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDatiDsan]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro");
		LOG.info(prf + " idSoggettoDsan: " + idSoggettoDsan);
		LOG.info(prf + " idDomanda: " + idDomanda);
		LOG.info(prf + " numeroDomanda: " + numeroDomanda);
		
		/*
			dt_emissione_dsan
			dt_scadenza
			dt_chiusura_richiesta
		*/
		
		List<EsitoDurcAntimafia> esitoDatiList = new ArrayList<EsitoDurcAntimafia>();
		try {
			String sql = "SELECT \n"
					+ "CASE WHEN ptsd.dt_emissione_dsan IS NOT NULL \n"
					+ "AND ptsd.dt_scadenza > SYSDATE THEN 'REGOLARE' \n"
					+ "ELSE 'NON REGOLARE' \n"
					+ "END AS desc_esito_richieste, \n"
					+ "ptsd.dt_emissione_dsan, \n"
					+ "ptsd.dt_scadenza \n"
					+ "FROM PBANDI_T_SOGGETTO_DSAN ptsd \n"
					+ "WHERE ptsd.id_soggetto_dsan = :idSoggettoDsan \n";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idSoggettoDsan", idSoggettoDsan, Types.INTEGER);
			
			esitoDatiList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<EsitoDurcAntimafia>>() {
				
				public List<EsitoDurcAntimafia> extractData(ResultSet rs7) throws SQLException, DataAccessException {
					
					List<EsitoDurcAntimafia> esitoList = new ArrayList<>();
					
					/**
					 *	DESC_ESITO_RICHIESTE | DT_EMISSIONE_DURC | DT_SCADENZA
					 */
					while(rs7.next()) {
						EsitoDurcAntimafia esito = new EsitoDurcAntimafia();
						esito.setIdDomanda(String.valueOf(idDomanda));
						esito.setNumeroDomanda(numeroDomanda);
						esito.setEsito(rs7.getString("DESC_ESITO_RICHIESTE"));
						esito.setDtRicezioneDocumentazione(rs7.getDate("DT_EMISSIONE_DSAN"));
						// esito.setDtEmissione(rs7.getDate("DT_EMISSIONE_DSAN"));
						esito.setDtScadenza(rs7.getDate("DT_SCADENZA"));
						esitoList.add(esito);
					}
					
					return esitoList;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiarioVO", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiarioVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return esitoDatiList;
	}
	
	
	
	@Override
	public List<EsitoDurcAntimafia> getDatiAntimafia(Integer idSoggettoAntimafia, Integer idDomanda, String numeroDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDatiAntimafia]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro");
		LOG.info(prf + " idSoggettoAntimafia: " + idSoggettoAntimafia);
		LOG.info(prf + " idDomanda: " + idDomanda);
		LOG.info(prf + " numeroDomanda: " + numeroDomanda);
		
		List<EsitoDurcAntimafia> esitoDatiList = new ArrayList<EsitoDurcAntimafia>();
		try {
//			String sql = "SELECT \n"
//					+ " CASE WHEN ptsa.dt_emissione IS NOT NULL \n"
//					+ " AND ptsa.dt_scadenza_antimafia > SYSDATE THEN 'REGOLARE' \n"
//					+ " ELSE 'NON REGOLARE' \n"
//					+ " END AS desc_esito_richieste, \n"
//					+ " ptsa.dt_emissione, \n"
//					+ " ptsa.dt_scadenza_antimafia \n"
//					+ " FROM pbandi_t_soggetto_antimafia ptsa \n"
//					+ " WHERE ptsa.id_soggetto_antimafia = :idSoggettoAntimafia \n";
			
			String sql = "SELECT\n"
					+ " pdter.DESC_ESITO_RICHIESTE , \n"
					+ " ptsa.dt_emissione, \n"
					+ " ptsa.dt_scadenza_antimafia \n"
					+ " FROM pbandi_t_soggetto_antimafia ptsa \n"
					+ " LEFT JOIN PBANDI_D_TIPO_ESITO_RICHIESTE pdter ON ptsa.ID_TIPO_ESITO_ANTIMAFIA = pdter.ID_TIPO_ESITO_RICHIESTE \n"
					+ " WHERE ptsa.id_soggetto_antimafia = :idSoggettoAntimafia \n";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idSoggettoAntimafia", idSoggettoAntimafia, Types.INTEGER);
			
			esitoDatiList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<EsitoDurcAntimafia>>() {
				
				public List<EsitoDurcAntimafia> extractData(ResultSet rs8) throws SQLException, DataAccessException {
					
					List<EsitoDurcAntimafia> esitoList = new ArrayList<>();
					
					/**
					 *	desc_esito_richieste | dt_emissione | dt_scadenza_antimafia
					 */
					while(rs8.next()) {
						EsitoDurcAntimafia esito = new EsitoDurcAntimafia();
						esito.setIdDomanda(String.valueOf(idDomanda));
						esito.setNumeroDomanda(numeroDomanda);
						esito.setEsito(rs8.getString("desc_esito_richieste"));
						esito.setDtEmissione(rs8.getDate("dt_emissione"));
						esito.setDtScadenza(rs8.getDate("dt_scadenza_antimafia"));
						esitoList.add(esito);
					}
					
					return esitoList;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiarioVO", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiarioVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return esitoDatiList;
	}
	
	
	
	/* getDatiAntimafia_BDNA */
	@Override
	public List<EsitoDurcAntimafia> getDatiAntimafia_BDNA(Integer idSoggettoAntimafia, Integer idDomanda, String numeroDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDatiAntimafia_BDNA]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro");
		LOG.info(prf + " idSoggettoAntimafia: " + idSoggettoAntimafia);
		LOG.info(prf + " idDomanda: " + idDomanda);
		LOG.info(prf + " numeroDomanda: " + numeroDomanda);
		
		List<EsitoDurcAntimafia> esitoDatiList = new ArrayList<EsitoDurcAntimafia>();
		try {
			String sql = "SELECT \n"
					+ " ptsa.dt_ricezione_bdna, \n"
					+ " ptsa.dt_scadenza_antimafia \n"
					+ " FROM pbandi_t_soggetto_antimafia ptsa \n"
					+ " WHERE ptsa.id_soggetto_antimafia = :idSoggettoAntimafia \n";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idSoggettoAntimafia", idSoggettoAntimafia, Types.INTEGER);
			
			esitoDatiList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<EsitoDurcAntimafia>>() {
				
				public List<EsitoDurcAntimafia> extractData(ResultSet rs8) throws SQLException, DataAccessException {
					
					List<EsitoDurcAntimafia> esitoList = new ArrayList<>();
					
					/**
					 *	desc_esito_richieste | dt_emissione | dt_scadenza_antimafia | dt_ricezione_bdna
					 */
					while(rs8.next()) {
						EsitoDurcAntimafia esito = new EsitoDurcAntimafia();
						esito.setIdDomanda(String.valueOf(idDomanda));
						esito.setNumeroDomanda(numeroDomanda);
						esito.setDtScadenza(rs8.getDate("dt_scadenza_antimafia"));
						esito.setDtRicezioneDocumentazione(rs8.getDate("dt_ricezione_bdna"));
						esitoList.add(esito);
					}
					
					return esitoList;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiarioVO", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiarioVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return esitoDatiList;
	}
	
	
	
	// q4
	@Override
	public List<EsitoDurcAntimafia> getDatiRichiestiDurc(Integer idDomanda, String numeroDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDatiRichiesta]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro");
		LOG.info(prf + " idDomanda: " + idDomanda);
		
		List<EsitoDurcAntimafia> datiRichiestiList = new ArrayList<EsitoDurcAntimafia>();
		try {
			String sql = "SELECT a.DT_CHIUSURA_RICHIESTA, a.DESC_TIPO_RICHIESTA, \n"
					+ " a.DESC_STATO_RICHIESTA  \n"
					+ " FROM ( \n"
					+ " SELECT ptr.DT_CHIUSURA_RICHIESTA, pdtr.DESC_TIPO_RICHIESTA, \n"
					+ " pdsr.DESC_STATO_RICHIESTA, PTR.ID_RICHIESTA \n"
					+ " FROM PBANDI_T_RICHIESTA ptr \n"
					+ " JOIN PBANDI_D_TIPO_RICHIESTA pdtr ON pdtr.ID_TIPO_RICHIESTA = ptr.ID_TIPO_RICHIESTA \n"
					+ " JOIN PBANDI_D_STATO_RICHIESTA pdsr ON pdsr.ID_STATO_RICHIESTA = ptr.ID_STATO_RICHIESTA \n"
					+ " WHERE PTR.ID_DOMANDA = :idDomanda \n"
					+ " AND pdtr.desc_breve_tipo_richiesta = 'DURC' \n"
					+ " ORDER BY ptr.id_richiesta DESC) a \n"
					+ " WHERE ROWNUM = 1 \n";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idDomanda", idDomanda, Types.INTEGER);
			
			datiRichiestiList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<EsitoDurcAntimafia>>() {
				
				public List<EsitoDurcAntimafia> extractData(ResultSet rs9) throws SQLException, DataAccessException {
					
					List<EsitoDurcAntimafia> elencoDati= new ArrayList<>();
					
					/**
					 *  DT_CHIUSURA_RICHIESTA| DESC_TIPO_RICHIESTA| DESC_STATO_RICHIESTA 
					 */
					while(rs9.next()) {
						EsitoDurcAntimafia ris = new EsitoDurcAntimafia();
						ris.setIdDomanda(String.valueOf(idDomanda));
						ris.setNumeroDomanda(numeroDomanda);
						ris.setDtChiusuraRichiesta(rs9.getDate("DT_CHIUSURA_RICHIESTA"));
						ris.setDescTipoRichiesta(rs9.getString("DESC_TIPO_RICHIESTA"));
						ris.setDescStatoRichiesta(rs9.getString("DESC_STATO_RICHIESTA"));
						elencoDati.add(ris);
					}
					
					return elencoDati;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiarioVO", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiarioVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return datiRichiestiList;
	}
	
	
	// q4b
	@Override
	public List<EsitoDurcAntimafia> getDatiRichiestiDsan(Integer idDomanda, String numeroDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDatiRichiestiDsan]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro");
		LOG.info(prf + " idDomanda: " + idDomanda);
		LOG.info(prf + " numeroDomanda: " + numeroDomanda);
		
		List<EsitoDurcAntimafia> datiRichiestiList = new ArrayList<EsitoDurcAntimafia>();
		try {
			String sql = "SELECT a.DT_CHIUSURA_RICHIESTA, a.DESC_BREVE_TIPO_RICHIESTA, \n"
					+ " a.DESC_STATO_RICHIESTA \n"
					+ " FROM ( \n"
					+ " SELECT ptr.DT_CHIUSURA_RICHIESTA, pdtr.DESC_BREVE_TIPO_RICHIESTA, \n"
					+ " pdsr.DESC_STATO_RICHIESTA, PTR.ID_RICHIESTA \n"
					+ " FROM PBANDI_T_RICHIESTA ptr \n"
					+ " JOIN PBANDI_D_TIPO_RICHIESTA pdtr ON pdtr.ID_TIPO_RICHIESTA = ptr.ID_TIPO_RICHIESTA \n"
					+ " JOIN PBANDI_D_STATO_RICHIESTA pdsr ON pdsr.ID_STATO_RICHIESTA = ptr.ID_STATO_RICHIESTA \n"
					+ " WHERE PTR.ID_DOMANDA = :idDomanda \n"
					+ " AND pdtr.desc_breve_tipo_richiesta = 'DSAN' \n"
					+ " ORDER BY ptr.id_richiesta DESC) a \n"
					+ " WHERE ROWNUM = 1 ";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idDomanda", idDomanda, Types.INTEGER);
			
			datiRichiestiList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<EsitoDurcAntimafia>>() {
				
				public List<EsitoDurcAntimafia> extractData(ResultSet rs10) throws SQLException, DataAccessException {
					
					List<EsitoDurcAntimafia> elencoDati= new ArrayList<>();
					
					/**
					 *	DT_CHIUSURA_RICHIESTA | DESC_BREVE_TIPO_RICHIESTA | DESC_STATO_RICHIESTA
					 */
					while(rs10.next()) {
						EsitoDurcAntimafia ris = new EsitoDurcAntimafia();
						ris.setIdDomanda(String.valueOf(idDomanda));
						ris.setNumeroDomanda(numeroDomanda);
						ris.setDescStatoRichiesta(rs10.getString("DESC_STATO_RICHIESTA"));
						ris.setDtChiusuraRichiesta(rs10.getDate("DT_CHIUSURA_RICHIESTA"));
						ris.setDescTipoRichiesta(rs10.getString("DESC_BREVE_TIPO_RICHIESTA"));
						elencoDati.add(ris);
					}
					
					return elencoDati;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiarioVO", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiarioVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return datiRichiestiList;
	}
	
	
	
	@Override
	public List<EsitoDurcAntimafia> getDatiRichiestiAntimafia(Integer idDomanda, String numeroDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDatiRichiestiAntimafia]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametro");
		LOG.info(prf + " idDomanda: " + idDomanda);
		LOG.info(prf + " numeroDomanda: " + numeroDomanda);
		
		List<EsitoDurcAntimafia> datiRichiestiList = new ArrayList<EsitoDurcAntimafia>();
		try {
			String sql = "SELECT a.DT_CHIUSURA_RICHIESTA, a.DESC_TIPO_RICHIESTA, a.DESC_STATO_RICHIESTA \n"
					+ "FROM ( \n"
					+ "SELECT ptr.DT_CHIUSURA_RICHIESTA, pdtr.DESC_TIPO_RICHIESTA, \n"
					+ "pdsr.DESC_STATO_RICHIESTA, PTR.ID_RICHIESTA \n"
					+ "FROM PBANDI_T_RICHIESTA ptr \n"
					+ "JOIN PBANDI_D_TIPO_RICHIESTA pdtr ON pdtr.ID_TIPO_RICHIESTA = ptr.ID_TIPO_RICHIESTA \n"
					+ "JOIN PBANDI_D_STATO_RICHIESTA pdsr ON pdsr.ID_STATO_RICHIESTA = ptr.ID_STATO_RICHIESTA \n"
					+ "WHERE PTR.ID_DOMANDA = :idDomanda \n"
					+ "AND pdtr.desc_breve_tipo_richiesta = 'ANTIMAFIA' \n"
					+ "ORDER BY ptr.id_richiesta DESC) a \n"
					+ "WHERE ROWNUM = 1 \n";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idDomanda", idDomanda, Types.INTEGER);
			
			datiRichiestiList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<EsitoDurcAntimafia>>() {
				
				public List<EsitoDurcAntimafia> extractData(ResultSet rs11) throws SQLException, DataAccessException {
					
					List<EsitoDurcAntimafia> elencoDati= new ArrayList<>();
					
					/** dt_chiusura_richiesta, desc_tipo_richiesta, desc_stato_richiesta */
					while(rs11.next()) {
						EsitoDurcAntimafia ris = new EsitoDurcAntimafia();
						ris.setIdDomanda(String.valueOf(idDomanda));
						ris.setNumeroDomanda(numeroDomanda);
						ris.setDtChiusuraRichiesta(rs11.getDate("dt_chiusura_richiesta"));
						ris.setDescTipoRichiesta(rs11.getString("desc_tipo_richiesta"));
						ris.setDescStatoRichiesta(rs11.getString("desc_stato_richiesta"));
						elencoDati.add(ris);
					}
					
					return elencoDati;
				}
			});
			
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read DatiBeneficiarioVO", e);
			throw new ErroreGestitoException("DaoException while trying to read DatiBeneficiarioVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		return datiRichiestiList;
	}
	
	
	
	/* verifico se esiste una richiesta attiva per DURC */
	@Override
	public List<SoggettoRichiestaTmp> getDomandaRichiesta(Integer idSoggetto){
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDomandaRichiesta]";
		
		LOG.info(prf + " BEGIN");
		
		List<SoggettoRichiestaTmp> soggBeneficiariList = new ArrayList<SoggettoRichiestaTmp>();
		LOG.info(prf + " idSoggetto: " + idSoggetto);
		
		try {
			String sql = "SELECT e.id_soggetto, c.desc_tipo_richiesta \n"
					+ "FROM PBANDI_T_RICHIESTA a \n"
					+ "JOIN PBANDI_D_STATO_RICHIESTA b ON a.id_stato_richiesta = b.id_stato_richiesta \n"
					+ "JOIN PBANDI_D_TIPO_RICHIESTA c ON a.id_tipo_richiesta = c.id_tipo_richiesta \n"
					+ "AND c.desc_tipo_richiesta = 'DURC' \n"
					+ "JOIN PBANDI_T_DOMANDA d ON d.id_domanda = a.id_domanda \n"
					+ "JOIN PBANDI_R_SOGGETTO_DOMANDA e ON e.id_domanda = d.id_domanda \n"
					+ "AND e.id_tipo_anagrafica = 1 \n"
					+ "AND e.id_tipo_beneficiario <> 4 \n"
					+ "where b.desc_breve_stato_richiesta <> 'Annullata' \n"
					+ "AND b.desc_breve_stato_richiesta <> 'Completata' \n"
					+ "AND e.id_soggetto = :idSoggetto";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			
			soggBeneficiariList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<SoggettoRichiestaTmp>>() {
				
				public List<SoggettoRichiestaTmp> extractData(ResultSet rs12) throws SQLException, DataAccessException {
					
					List<SoggettoRichiestaTmp> datiList = new ArrayList<>();
					
					while(rs12.next()) {
						SoggettoRichiestaTmp esito = new SoggettoRichiestaTmp();
						esito.setIdSoggetto(rs12.getInt("id_soggetto"));
						esito.setDescTipoRichiesta(rs12.getString("desc_tipo_richiesta"));
						datiList.add(esito);
					}
					
					return datiList;
				}
			});
		
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoDurcAntimafia", e);
			throw new ErroreGestitoException("DaoException while trying to read EsitoDurcAntimafia", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return soggBeneficiariList;
	}
	
	
	
	
	@Override
	public List<SoggettoRichiestaTmp> getDomandaRichiestaDsan(Integer id_domanda){
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDomandaRichiestaDsan]";
		
		LOG.info(prf + " BEGIN");
		
		List<SoggettoRichiestaTmp> soggBeneficiariList = new ArrayList<SoggettoRichiestaTmp>();
		LOG.info(prf + " id_domanda: " + id_domanda);
		
		try {
			String sql = "SELECT e.id_soggetto, c.desc_tipo_richiesta \n"
					+ "FROM PBANDI_T_RICHIESTA a \n"
					+ "JOIN PBANDI_D_STATO_RICHIESTA b ON a.id_stato_richiesta = b.id_stato_richiesta \n"
					+ "JOIN PBANDI_D_TIPO_RICHIESTA c ON a.id_tipo_richiesta = c.id_tipo_richiesta \n"
					+ "AND c.desc_tipo_richiesta = 'DSAN in assenza di DURC' \n"
					+ "JOIN PBANDI_T_DOMANDA d ON d.id_domanda = a.id_domanda \n"
					+ "JOIN PBANDI_R_SOGGETTO_DOMANDA e ON e.id_domanda = d.id_domanda \n"
					+ "AND e.id_tipo_anagrafica = 1 \n"
					+ "AND e.id_tipo_beneficiario <> 4 \n"
					+ "WHERE b.desc_breve_stato_richiesta <> 'Annullata' \n"
					+ "AND b.desc_breve_stato_richiesta <> 'Completata' \n"
					+ "AND e.id_domanda = :id_domanda";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("id_domanda", id_domanda, Types.INTEGER);
			
			soggBeneficiariList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<SoggettoRichiestaTmp>>() {
				
				public List<SoggettoRichiestaTmp> extractData(ResultSet rs13) throws SQLException, DataAccessException {
					
					List<SoggettoRichiestaTmp> datiList = new ArrayList<>();
					
					while(rs13.next()) {
						SoggettoRichiestaTmp esito = new SoggettoRichiestaTmp();
						esito.setIdSoggetto(rs13.getInt("id_soggetto"));
						esito.setDescTipoRichiesta(rs13.getString("desc_tipo_richiesta"));
						datiList.add(esito);
					}
					
					return datiList;
				}
			});
		
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoDurcAntimafia", e);
			throw new ErroreGestitoException("DaoException while trying to read EsitoDurcAntimafia", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return soggBeneficiariList;
	}
	
	
	@Override
	public List<IstruttoreAsfTmp> getDatiIstruttoreASF(String cfIstruttore){
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDatiIstruttoreASF]";
		
		LOG.info(prf + " BEGIN");
		
		List<IstruttoreAsfTmp> istruttoreList = new ArrayList<IstruttoreAsfTmp>();
		LOG.info(prf + " cfIstruttore: " + cfIstruttore);
		
		try {
			String sql = "SELECT \n"
					+ "	a.ID_SOGGETTO, \n"
					+ "	a.CODICE_FISCALE_SOGGETTO, \n"
					+ "	b.NOME, \n"
					+ "	b.COGNOME \n"
					+ "FROM PBANDI_T_SOGGETTO a \n"
					+ "JOIN PBANDI_T_PERSONA_FISICA b ON b.ID_SOGGETTO = a.ID_SOGGETTO \n"
					+ "WHERE a.codice_fiscale_soggetto = :cfIstruttore \n";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("cfIstruttore", cfIstruttore, Types.VARCHAR);
			
			istruttoreList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<IstruttoreAsfTmp>>() {
				
				public List<IstruttoreAsfTmp> extractData(ResultSet rs14) throws SQLException, DataAccessException {
					
					List<IstruttoreAsfTmp> datiList = new ArrayList<>();
					
					while(rs14.next()) {
						IstruttoreAsfTmp dato = new IstruttoreAsfTmp();
						dato.setIdIstruttore(rs14.getInt("id_soggetto"));
						dato.setNomeIstrASF(rs14.getString("nome"));
						dato.setCognomeIstrASF(rs14.getString("cognome"));
						dato.setCodicefiscaleIstrASF(rs14.getString("codice_fiscale_soggetto"));
						
						datiList.add(dato);
					}
					
					return datiList;
				}
			});
		
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoDurcAntimafia", e);
			throw new ErroreGestitoException("DaoException while trying to read EsitoDurcAntimafia", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return istruttoreList;
	}
	
	
	@Override
	public boolean recordExists(Integer idSoggetto) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::recordExists]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametri: ");
		LOG.info(prf + " idSoggetto: " + idSoggetto);
		
        String query = "SELECT count(*) \n"
        		+ "FROM PBANDI_T_UTENTE a \n"
        		+ "JOIN PBANDI_T_SOGGETTO b ON b.ID_SOGGETTO = a.ID_SOGGETTO \n"
        		+ "WHERE a.id_soggetto = :idSoggetto \n";
        
        MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
		
        Integer count = namedParameterJdbcTemplate.query(query, param, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs15) throws SQLException, DataAccessException {
                if (rs15.next()) {
                    return rs15.getInt(1);
                }
                return 0;
            }
        });
        return count > 0;
    }
	
	
	
	
	/**
	 * Inserimento soggetto a sistema
	 * parametro utile: codFiscaleSoggetto
	 */
	@Override
	public Integer insertSoggetto(EsitoDurcAntimafiaInRest datiInputRest) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::insertSoggetto]";
		LOG.info(prf + " BEGIN");
		
		int risultato1 = 0;
		int risultato2 = 0;		
		
		String codFiscaleSoggetto = "";
		if(datiInputRest != null) {
			if(datiInputRest.getCodFiscaleIstruttore() != null) {
				codFiscaleSoggetto = datiInputRest.getCodFiscaleIstruttore();
			}
		}
		LOG.info("codFiscaleSoggetto:" + codFiscaleSoggetto);
		
		/* 1ma insert */
		String insertSoggettoQuery = "INSERT INTO PBANDI_T_SOGGETTO ( \n"
				+ "ID_SOGGETTO, \n"
				+ "CODICE_FISCALE_SOGGETTO, \n"
				+ "ID_UTENTE_INS, \n"
				+ "ID_TIPO_SOGGETTO, \n"
				+ "DT_INSERIMENTO, \n"
				+ "DT_AGGIORNAMENTO, \n"
				+ "RICEVENTE_TRASF ) \n"
				+ "VALUES ( \n"
				+ "SEQ_PBANDI_T_SOGGETTO.nextval, \n"
				+ ":codFiscaleSoggetto, \n"
				+ "1, \n"
				+ "1, \n"
				+ "SYSDATE, \n"
				+ "NULL, \n"
				+ "NULL \n"
				+ ")";
		
		LOG.info(insertSoggettoQuery);
		
		/* 2da insert */
		String insertPersonaFisica = "INSERT INTO PBANDI_T_PERSONA_FISICA ptpf \n"
				+ "( \n"
				+ " ptpf.ID_SOGGETTO, \n"
				+ "	ptpf.NOME, \n"
				+ "	ptpf.COGNOME, \n"
				+ "	ptpf.DT_NASCITA, \n"
				+ "	ptpf.SESSO, \n"
				+ "	ptpf.ID_PERSONA_FISICA, \n"
				+ "	ptpf.DT_INIZIO_VALIDITA, \n"
				+ "	ptpf.DT_FINE_VALIDITA, \n"
				+ "	ptpf.ID_COMUNE_ITALIANO_NASCITA, \n"
				+ "	ptpf.ID_COMUNE_ESTERO_NASCITA, \n"
				+ "	ptpf.ID_NAZIONE_NASCITA, \n"
				+ "	ptpf.ID_UTENTE_INS, \n"
				+ "	ptpf.ID_UTENTE_AGG, \n"
				+ "	ptpf.ID_CITTADINANZA, \n"
				+ "	ptpf.ID_TITOLO_STUDIO, \n"
				+ "	ptpf.ID_OCCUPAZIONE \n"
				+ ") \n"
				+ "VALUES \n"
				+ "( \n"
				+ "	:idSoggetto, \n"
				+ "	:cognomeIstruttore, \n"
				+ "	:nomeIstruttore, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	SEQ_PBANDI_T_PERSONA_FISICA.nextval, \n"
				+ "	SYSDATE, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	1, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	NULL \n"
				+ ")";
	   
	    
	    // Creo un oggetto MapSqlParameterSource per la prima insert
	    MapSqlParameterSource soggettoParams = new MapSqlParameterSource(); 
	    soggettoParams.addValue("codFiscaleSoggetto", codFiscaleSoggetto, Types.VARCHAR);
		
	    Integer idSoggetto = namedParameterJdbcTemplate.queryForObject("SELECT MAX(id_soggetto) FROM PBANDI_T_SOGGETTO", 
	    		new MapSqlParameterSource(), Integer.class);
		
		MapSqlParameterSource personaFisicaParams = new MapSqlParameterSource();
		personaFisicaParams.addValue("cognomeIstruttore", datiInputRest.getCognomeIstruttore(), Types.VARCHAR);
		personaFisicaParams.addValue("nomeIstruttore", datiInputRest.getNomeIstruttore(), Types.VARCHAR);
		personaFisicaParams.addValue("idSoggetto", new SqlParameterValue(Types.NUMERIC, idSoggetto));
		
		LOG.info(insertPersonaFisica);
		
		try {
			risultato1 = this.update(insertSoggettoQuery, soggettoParams);
			risultato2 = this.update(insertPersonaFisica, personaFisicaParams);
			
			if (risultato1 > 0 && risultato2 > 0) {
				LOG.info("Entrambe le query sono state eseguite con successo");
	        } else {
	            // Almeno una delle query è fallita
	            throw new Exception("Errore nell'esecuzione delle query [insertSoggetto] ");
	        }
			
		} catch (InternalUnexpectedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
		return idSoggetto;
	}
	
	
	
	/* E. inserimento richiesta a sistema 
	 * TODO: clonare inserimento richiesta per DSAN 
	 **/
	@Override
	public boolean insertRichiesta(EsitoDurcAntimafiaInRest datiInputRest, Integer idDomanda, Integer idIstruttore) {
		String prf = "[EsitoDurcAntimafiaDAOImpl::addRichiesta]";
		LOG.info(prf + " BEGIN");
		
		boolean isInsertSuccessful = false;
		int risultato = 0;
		
		String sql = "INSERT \n"
				+ "INTO \n"
				+ "PBANDI_T_RICHIESTA ( \n"
				+ "	ID_RICHIESTA, \n"
				+ "	ID_TIPO_RICHIESTA, \n"
				+ "	ID_STATO_RICHIESTA, \n"
				+ "	ID_UTENTE_RICHIEDENTE, \n"
				+ "	ID_UTENTE_INS, \n"
				+ "	ID_DOMANDA, \n"
				+ "	DT_INVIO_RICHIESTA, \n"
				+ "	DT_INIZIO_VALIDITA, \n"
				+ "	FLAG_URGENZA \n"
				+ ") \n"
				+ "VALUES ( \n"
				+ "	SEQ_PBANDI_T_RICHIESTA.nextval, \n"
				+ "	( \n"
				+ "		SELECT DISTINCT pdtr.ID_TIPO_RICHIESTA \n"
				+ "		FROM PBANDI_D_TIPO_RICHIESTA pdtr \n"
				+ "		WHERE pdtr.DESC_TIPO_RICHIESTA = :tipoRichiesta \n"
				+ "	), \n"
				+ "	1, \n"
				+ "	( \n"
				+ "		SELECT ID_UTENTE \n"
				+ "		FROM PBANDI_T_UTENTE ptu \n"
				+ "     WHERE ptu.ID_SOGGETTO = :idIstruttore \n"
				+ "		AND ROWNUM =1 \n"
				
				+ "	), \n"
				+ "	( \n"
				+ "		SELECT ID_UTENTE \n"
				+ "		FROM PBANDI_T_UTENTE ptu \n"
				+ "		WHERE ptu.ID_SOGGETTO = :idIstruttore \n"
				+ "		AND ROWNUM =1 \n"
				+ "	), \n"
				+ "	:idDomanda, \n"
				+ "	SYSDATE, \n"
				+ "	SYSDATE, \n"
				+ "	:modalitaRichiesta \n"
				+ ")";
		
		LOG.info(sql);
		LOG.info("Dati:" + datiInputRest.getTipoRichiesta() + ", " + datiInputRest.getNumeroDomanda() + ", " + datiInputRest.getModalitaRichiesta() + ", " + idDomanda + ", " + idIstruttore);
		
		MapSqlParameterSource param = new MapSqlParameterSource(); 
		param.addValue("tipoRichiesta", datiInputRest.getTipoRichiesta(), Types.VARCHAR);
		param.addValue("numeroDomanda", datiInputRest.getNumeroDomanda(), Types.VARCHAR);
		param.addValue("modalitaRichiesta", datiInputRest.getModalitaRichiesta(), Types.VARCHAR);
		param.addValue("codFiscaleIstruttore", datiInputRest.getCodFiscaleIstruttore(), Types.VARCHAR);
		param.addValue("idDomanda", idDomanda, Types.INTEGER);
		param.addValue("idIstruttore", idIstruttore, Types.INTEGER);
		
		
		try {
			risultato = this.update(sql, param);
			
			if(risultato > 0) {
				isInsertSuccessful = true;
			}
			
		} catch (InternalUnexpectedException e) {
			e.printStackTrace();
		}
		
		return isInsertSuccessful;
	}
	
	
	/* D. inserimento utente a sistema */
	@Override
	public boolean insertUtente(EsitoDurcAntimafiaInRest datiInputRest, Integer idIstruttore) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::insertUtente]";
		LOG.info(prf + " BEGIN");
		
		boolean isInsertSuccessful = false;
		int risultato = 0;
		
		String sql = "INSERT \n"
				+ "	INTO \n"
				+ "	PBANDI_T_UTENTE ( \n"
				+ "	ID_UTENTE, \n"
				+ "	LOGIN, \n"
				+ "	PASSWORD, \n"
				+ "	CODICE_UTENTE, \n"
				+ "	ID_TIPO_ACCESSO, \n"
				+ "	ID_SOGGETTO, \n"
				+ "	DT_INSERIMENTO, \n"
				+ "	DT_AGGIORNAMENTO, \n"
				+ "	FLAG_CONSENSO_MAIL, \n"
				+ "	EMAIL_CONSENSO, \n"
				+ "	FLAG_AMMIN \n"
				+ ") \n"
				+ "VALUES ( \n"
				+ "	SEQ_PBANDI_T_UTENTE.nextval, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	:codFiscaleIstruttore, \n"
				+ "	5, \n"
				+ "	:idIstruttore, \n"
				+ "	SYSDATE, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	NULL, \n"
				+ "	NULL \n"
				+ ")";
		
		LOG.info(sql);
		LOG.info("Dati:" + datiInputRest.getCodFiscaleSoggetto());
		
		MapSqlParameterSource param = new MapSqlParameterSource(); 
		param.addValue("codFiscaleIstruttore", datiInputRest.getCodFiscaleIstruttore(), Types.VARCHAR);
		param.addValue("codFiscaleSoggetto", datiInputRest.getCodFiscaleSoggetto(), Types.VARCHAR);
		param.addValue("idIstruttore", idIstruttore, Types.INTEGER);
		
		try {
			
			risultato = this.update(sql, param);

			if(risultato > 0) {
				isInsertSuccessful = true;
			}
			
		} catch (InternalUnexpectedException e) {
			e.printStackTrace();
		}
		
		LOG.info(prf + " END");
		
		return isInsertSuccessful;
	}
	
	
	
	/* verifico se esiste record con dt_ricezione_bdna valorizzato e dt_scadenza_antimafia non valorizzato */
	@Override
	public boolean recordBdnaExist(Integer idDomanda) {
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::recordBdnaExist]";
		LOG.info(prf + " BEGIN");
		
		LOG.info(prf + " Parametri: ");
		
        String query = "SELECT count(*) \n"
        		+ "FROM PBANDI_T_SOGGETTO_ANTIMAFIA a \n"
        		+ "WHERE a.dt_ricezione_bdna IS NOT NULL \n"
        		+ "AND a.dt_scadenza_antimafia IS NULL \n"
        		+ "AND a.id_domanda = :idDomanda ";
        
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("idDomanda", idDomanda, Types.INTEGER);
        
        
        Integer count = namedParameterJdbcTemplate.query(query, param, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs16) throws SQLException, DataAccessException {
                if (rs16.next()) {
                    return rs16.getInt(1);
                }
                return 0;
            }
        });
        return count > 0;
    }
	
	
	
	/** q da correggere ?!? */
	@Override
	public List<EsitoDurcAntimafia> getEsitoDurcAntimafiaDsan(String codFiscaleSoggetto, String numeroDomanda, String tipoRichiesta, String modalitaRichiesta, int codBando) {
		String prf = "[EsitoDurcAntimafiaDAOImpl::getEsitoDurcAntimafiaDsan]";
		LOG.info(prf + " BEGIN");
		List<EsitoDurcAntimafia> esitiDurcAntimafia = new ArrayList<EsitoDurcAntimafia>();
		LOG.info(prf 
				+ " codFiscaleBeneficiario: " + codFiscaleSoggetto 
				+ ", numeroDomanda: " + numeroDomanda 
				+ ", tipoRichiesta: " + tipoRichiesta 
				+ ", modalitaRichiesta: " + modalitaRichiesta 
				+ ", codBando: " + codBando);
		try {
			String sql = "SELECT \n"
					+ "ptsd.DT_EMISSIONE_DSAN, \n"
					+ "ptsd.DT_SCADENZA, \n"
					+ "pdter.DESC_ESITO_RICHIESTE, \n"
					+ "ptr.DT_CHIUSURA_RICHIESTA, \n"
					+ "pdtr.DESC_TIPO_RICHIESTA, \n"
					+ "pdsr.DESC_STATO_RICHIESTA \n"
					+ "FROM PBANDI_T_SOGGETTO pts \n"
					+ "JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON pts.ID_SOGGETTO = prsd.ID_SOGGETTO \n"
					+ "JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \n"
					+ "JOIN PBANDI_T_SOGGETTO_DSAN ptsd ON ptd.ID_DOMANDA = ptsd.ID_DOMANDA \n"
					+ "JOIN PBANDI_D_TIPO_ESITO_RICHIESTE pdter ON pdter.ID_TIPO_ESITO_RICHIESTE = ptsd.ID_ESITO_DSAN \n"
					+ "JOIN PBANDI_T_RICHIESTA ptr ON ptd.ID_DOMANDA = ptr.ID_DOMANDA \n"
					+ "JOIN PBANDI_D_TIPO_RICHIESTA pdtr ON pdtr.ID_TIPO_RICHIESTA = ptr.ID_TIPO_RICHIESTA \n"
					+ "JOIN PBANDI_D_STATO_RICHIESTA pdsr ON pdsr.ID_STATO_RICHIESTA = ptr.ID_STATO_RICHIESTA \n"
					+ "JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pts.ID_SOGGETTO = pteg.ID_SOGGETTO \n"
					+ "WHERE prsd.ID_TIPO_ANAGRAFICA = 1 \n"
					+ "AND prsd.ID_TIPO_BENEFICIARIO <> 4 \n"
					+ "AND pts.CODICE_FISCALE_SOGGETTO = :codFiscaleSoggetto \n"
					+ "AND ptd.NUMERO_DOMANDA = :numeroDomanda \n"
					+ "AND pdtr.DESC_TIPO_RICHIESTA = :tipoRichiesta \n"
					+ "AND ptr.FLAG_URGENZA = :modalitaRichiesta \n"
					+ "AND ptd.PROGR_BANDO_LINEA_INTERVENTO = :codBando \n"
					+ "AND ptsd.DT_SCADENZA > SYSDATE";
			
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("codFiscaleSoggetto", codFiscaleSoggetto, Types.VARCHAR);
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);
			param.addValue("tipoRichiesta", tipoRichiesta, Types.VARCHAR);
			param.addValue("modalitaRichiesta", modalitaRichiesta, Types.VARCHAR);
			param.addValue("codBando", codBando, Types.NUMERIC);
			
			LOG.info(sql);

			esitiDurcAntimafia = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<EsitoDurcAntimafia>>() {
				
				public List<EsitoDurcAntimafia> extractData(ResultSet rs17) throws SQLException, DataAccessException {
					List<EsitoDurcAntimafia> elencoEsitiDurcAntimafia = new ArrayList<>();
					
					boolean recordsFound = false;
					
					while(rs17.next()) {
						recordsFound = true;
						
						EsitoDurcAntimafia esito = new EsitoDurcAntimafia();
						esito.setNumeroDomanda(numeroDomanda);
						esito.setDtRicezioneDocumentazione(rs17.getDate("dt_emissione_dsan"));
						esito.setDtScadenza(rs17.getDate("dt_scadenza"));
						esito.setDtChiusuraRichiesta(rs17.getDate("dt_chiusura_richiesta"));
						esito.setDescTipoRichiesta(rs17.getString("desc_tipo_richiesta"));
						esito.setDescStatoRichiesta(rs17.getString("desc_stato_richiesta"));
						elencoEsitiDurcAntimafia.add(esito);
					}
					
					if(!recordsFound) {
						LOG.info("[id_soggetto] e [id_domanda] - Nessun record trovato per il seguente numero di domanda: ["+numeroDomanda+"]");
					}
					
					return elencoEsitiDurcAntimafia;
				}
			});
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoDurcAntimafia", e);
			throw new ErroreGestitoException("DaoException while trying to read EsitoDurcAntimafia", e);
		} finally {
			LOG.info(prf + " END");
		}
		return esitiDurcAntimafia;
	}
	
	
	
	// verifico se esiste una richiesta attiva per Antimafia */
	@Override
	public List<SoggettoRichiestaTmp> getDomandaRichiestaAntimafia(Integer idomanda){
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getDomandaRichiestaAntimafia]";
		
		LOG.info(prf + " BEGIN");
		
		List<SoggettoRichiestaTmp> soggBeneficiariList = new ArrayList<SoggettoRichiestaTmp>();
		LOG.info(prf + " idomanda: " + idomanda);
		
		try {
			String sql = "SELECT e.id_soggetto, c.desc_tipo_richiesta \n"
					+ "FROM PBANDI_T_RICHIESTA a \n"
					+ "JOIN PBANDI_D_STATO_RICHIESTA b ON a.id_stato_richiesta = b.id_stato_richiesta \n"
					+ "JOIN PBANDI_D_TIPO_RICHIESTA c ON a.id_tipo_richiesta = c.id_tipo_richiesta \n"
					+ "AND c.desc_tipo_richiesta = 'ANTIMAFIA' \n"
					+ "JOIN PBANDI_T_DOMANDA d ON d.id_domanda = a.id_domanda \n"
					+ "JOIN PBANDI_R_SOGGETTO_DOMANDA e ON e.id_domanda = d.id_domanda \n"
					+ "AND e.id_tipo_anagrafica = 1 \n"
					+ "AND e.id_tipo_beneficiario <> 4 \n"
					+ "where b.desc_breve_stato_richiesta <> 'Annullata' \n"
					+ "AND b.desc_breve_stato_richiesta <> 'Completata' \n"
					+ "AND e.id_domanda = :idomanda";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idomanda", idomanda, Types.INTEGER);
			
			soggBeneficiariList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<SoggettoRichiestaTmp>>() {
				
				public List<SoggettoRichiestaTmp> extractData(ResultSet rs18) throws SQLException, DataAccessException {
					
					List<SoggettoRichiestaTmp> datiList = new ArrayList<>();
					
					while(rs18.next()) {
						SoggettoRichiestaTmp esito = new SoggettoRichiestaTmp();
						esito.setIdSoggetto(rs18.getInt("id_soggetto"));
						esito.setDescTipoRichiesta(rs18.getString("desc_tipo_richiesta"));
						datiList.add(esito);
					}
					
					return datiList;
				}
			});
		
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoDurcAntimafia", e);
			throw new ErroreGestitoException("DaoException while trying to read EsitoDurcAntimafia", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return soggBeneficiariList;
	}
	
	
	@Override
	public List<SoggettoRichiestaTmp> getRichiestaDurc(Integer idSoggetto){
		
		String prf = "[EsitoDurcAntimafiaDAOImpl::getRichiestaDurc]";
		
		LOG.info(prf + " BEGIN");
		
		List<SoggettoRichiestaTmp> soggBeneficiariList = new ArrayList<SoggettoRichiestaTmp>();
		LOG.info(prf + " idSoggetto: " + idSoggetto);
		
		try {
			String sql =  "SELECT e.id_soggetto, c.desc_tipo_richiesta \n"
			+ "FROM PBANDI_T_RICHIESTA a \n"
			+ "JOIN PBANDI_D_STATO_RICHIESTA b ON a.id_stato_richiesta = b.id_stato_richiesta \n"
			+ "JOIN PBANDI_D_TIPO_RICHIESTA c ON a.id_tipo_richiesta = c.id_tipo_richiesta \n"
			+ "AND c.desc_tipo_richiesta = 'DURC' \n"
			+ "JOIN PBANDI_T_DOMANDA d ON d.id_domanda = a.id_domanda \n"
			+ "JOIN PBANDI_R_SOGGETTO_DOMANDA e ON e.id_domanda = d.id_domanda \n"
			+ "AND e.id_tipo_anagrafica = 1 \n"
			+ "AND e.id_tipo_beneficiario <> 4 \n"
			+ "WHERE b.desc_breve_stato_richiesta <> 'A' \n"
			+ "AND b.desc_breve_stato_richiesta <> 'C' \n"
			+ "AND e.id_soggetto = :idSoggetto";
			
			LOG.info(sql);
			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("idSoggetto", idSoggetto, Types.INTEGER);
			
			soggBeneficiariList = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<SoggettoRichiestaTmp>>() {
				
				public List<SoggettoRichiestaTmp> extractData(ResultSet rs13) throws SQLException, DataAccessException {
					
					List<SoggettoRichiestaTmp> datiList = new ArrayList<>();
					
					while(rs13.next()) {
						SoggettoRichiestaTmp esito = new SoggettoRichiestaTmp();
						esito.setIdSoggetto(rs13.getInt("id_soggetto"));
						esito.setDescTipoRichiesta(rs13.getString("desc_tipo_richiesta"));
						datiList.add(esito);
					}
					
					return datiList;
				}
			});
		
		} catch(RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read EsitoDurcAntimafia", e);
			throw new ErroreGestitoException("DaoException while trying to read EsitoDurcAntimafia", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return soggBeneficiariList;
	}

}