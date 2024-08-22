/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.DichiarazioneSpesaDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class DichiarazioneSpesaDAOImpl extends BaseDAO implements DichiarazioneSpesaDAO {

	private Logger LOG;

	public DichiarazioneSpesaDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public HashMap<String, String> caricaFile(InputStream file, String nomeFile, String numeroDomanda, String dati) {
		String prf = "[DichiarazioneSpesaDAOImpl::caricaFile]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "nomeFile=" + nomeFile + ", numeroDomanda=" + numeroDomanda + ", dati=" + dati);
		HashMap<String, String> result = new HashMap<String, String>();

		try {
			// Creo il record sulla PBANDI_T_FOLDER
			String sqlId = "SELECT max(ID_CARICAMENTO_FILE) from PBANDI_W_CARICAMENTO_FILE";
			Long idCaricamento = this.queryForLong(sqlId, null) + 1;
			LOG.debug(prf + "idCaricamento=" + idCaricamento);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	        String sql = "INSERT INTO PBANDI_W_CARICAMENTO_FILE (ID_CARICAMENTO_FILE, ID_CARICAMENTO_XML, FILEB, NOME_FILE, NUMERO_DOMANDA, FILE_JSON, FLAG_CARICATO, DT_INSERIMENTO)		\n" + 
	        		"VALUES(:idCaricamento,																																					\n" + 
	        		"       NULL,																																							\n" + 
	        		"       NULL,																																							\n" + 
	        		"       :nomeFile,																																						\n" + 
	        		"       :numeroDomanda,																																					\n" + 
	        		"       NULL,																																							\n" + 
	        		"       NULL,																																							\n" + 
	        		"       :timestamp)";
	        
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idCaricamento", idCaricamento);
			param.addValue("nomeFile", nomeFile);
			param.addValue("numeroDomanda", numeroDomanda);
			param.addValue("timestamp", timestamp);

			int ris = this.namedParameterJdbcTemplate.update(sql, param);
			LOG.debug(prf + "insert ris=" + ris);

			// Aggiorno il record col blob del file allegato ed il clob del JSON
			int righeInserite = updateBlob(file, dati, idCaricamento);
			LOG.debug(prf + "PBANDI_W_CARICAMENTO_FILE updated row=" + righeInserite);
			
			result.put("idCaricamento", String.valueOf(idCaricamento));
			result.put("righeInserite", String.valueOf(righeInserite));
			result.put("timestamp", String.valueOf(timestamp));
		} catch (Exception e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}
	
	/**
	 * @param file
	 * @param dati
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int updateBlob(final InputStream file, final String dati, final Long id) throws Exception {
		int update = -1;
		LOG.info("updateChecklistCompiled for idChecklist " + id);

		update = this.namedParameterJdbcTemplate.getJdbcOperations().update(
				"update PBANDI_W_CARICAMENTO_FILE set FILEB=?, FILE_JSON=? where ID_CARICAMENTO_FILE=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setBinaryStream(1, file);
						getLobHandler().getLobCreator().setClobAsString(ps, 2, dati);
						ps.setLong(3, id);
					}
				});
		LOG.info("updateChecklistCompiled record modified: " + update);

		return update;
	}

}
