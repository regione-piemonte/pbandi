/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.integration.dao.AcquisizioneDomandeDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class AcquisizioneDomandeDAOImpl extends BaseDAO implements AcquisizioneDomandeDAO {

	private Logger LOG;

	public AcquisizioneDomandeDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public HashMap<String, String> insertXml(String file, String nomeFile, String numeroDomanda) {
		String prf = "[AcquisizioneDomandeDAOImpl::insertXml]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "file=" + file + ", nomeFile=" + nomeFile + ", numeroDomanda=" + numeroDomanda);
		HashMap<String, String> result = new HashMap<String, String>();
		int idCaricamento = 0;
		int righeInserite = 0;

		try {
			// Creo il record sulla PBANDI_T_FOLDER
			String sqlId = "SELECT max(ID_CARICAMENTO ) from PBANDI_W_CARICAMENTO_XML";
			idCaricamento = this.queryForInteger(sqlId, null) + 1;
			LOG.debug(prf + "idCaricamento=" + idCaricamento);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	        String sql = "INSERT INTO PBANDI_W_CARICAMENTO_XML(ID_CARICAMENTO, FILE_XML, NOME_FILE, FLAG_VALIDO, FLAG_CARICATO, NUMERO_DOMANDA, FONTE, DT_INSERIMENTO)	\n" + 
	        		"VALUES(:idCaricamento,																																\n" + 
	        		"       XMLTYPE(:file),																																\n" + 
	        		"       :nomeFile,																																	\n" + 
	        		"       NULL,																																		\n" + 
	        		"       NULL,																																		\n" + 
	        		"       :numeroDomanda,																																\n" + 
	        		"       'WS',																																	\n" + 
	        		"       :timestamp)";
	        
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("idCaricamento", idCaricamento);
			param.addValue("file", "<xml/>");
			param.addValue("nomeFile", nomeFile);
			param.addValue("numeroDomanda", numeroDomanda);
			param.addValue("timestamp", timestamp);

			int ris = this.namedParameterJdbcTemplate.update(sql, param);
			LOG.debug(prf + "insert ris=" + ris);

			righeInserite = updateXML(file, idCaricamento);
			LOG.debug(prf + "PBANDI_W_CARICAMENTO_XML updated row=" + righeInserite);
			
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
	 * @param str
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int updateXML(final String str, final int id) throws Exception {
		int update = -1;
		LOG.info("updateChecklistCompiled for idChecklist " + id);

		update = this.namedParameterJdbcTemplate.getJdbcOperations().update(
				"update PBANDI_W_CARICAMENTO_XML set FILE_XML=(XMLType(?)) where ID_CARICAMENTO=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						getLobHandler().getLobCreator().setClobAsString(ps, 1, str);
						ps.setInt(2, id);
					}
				});
		LOG.info("updateChecklistCompiled record modified: " + update);
		
		return update;
	}

}
