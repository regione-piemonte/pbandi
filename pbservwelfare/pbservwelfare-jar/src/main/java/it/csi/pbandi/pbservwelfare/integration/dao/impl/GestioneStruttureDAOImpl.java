/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;

import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.GestioneStruttureDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;

public class GestioneStruttureDAOImpl extends BaseDAO implements GestioneStruttureDAO {
	
	private Logger LOG;
	
	public GestioneStruttureDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public int aggiornaMailStruttura(String codStruttura, String email) {
		String prf = "[GestioneStruttureDAOImpl::aggiornaMailStruttura]";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "codStruttura=" + codStruttura + ", email=" + email);
		int result = -1;
		
		try {
	        String sql = "UPDATE PBANDI_T_FORNITORE_STRUTTURA 	\n" + 
	        		"SET EMAIL = ?,								\n" +
	        		"DT_AGGIORNAMENTO = SYSDATE					\n" + 
	        		"WHERE COD_STRUTTURA = ?";
	        LOG.info("Query: " + sql);
	        
	        result = this.namedParameterJdbcTemplate.getJdbcOperations().update(
					sql,
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, email);
							ps.setString(2, codStruttura);
						}
					});
			LOG.info("aggiornaMailStruttura record modified: " + result);

		} catch (Exception e) {
			throw new ErroreGestitoException("Errore nell'aggiornare il record", e);
		} finally {
			LOG.info(prf + " END");
		}
		
		return result;
	}

}
