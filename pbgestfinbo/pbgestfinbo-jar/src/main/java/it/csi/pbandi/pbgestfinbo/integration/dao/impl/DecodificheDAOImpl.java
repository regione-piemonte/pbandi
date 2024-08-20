/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.DecodificheDAO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbgestfinbo.util.StringUtil;

public class DecodificheDAOImpl extends JdbcDaoSupport implements DecodificheDAO{

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	
	public DecodificheDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	// Se non la trova e obbligatoria = true, innalza una eccezione.
	public String costante(String attributo, boolean obbligatoria) throws DaoException {
		String prf = "[DecodificheDAOImpl::costante()] ";
		LOG.info(prf + "BEGIN");
		String valore = null;
		try {
			
			String sql = "SELECT VALORE FROM PBANDI_C_COSTANTI WHERE ATTRIBUTO = '"+attributo+"'";
			LOG.debug("\n"+sql.toString());
			
			valore = (String) getJdbcTemplate().queryForObject(sql.toString(), String.class);
			
			if (obbligatoria && StringUtil.isEmpty(valore)) {
				throw new DaoException("Costante "+attributo+" non trovata.");
			}
			
		} catch (Exception e) {
			LOG.error(prf+"ERRORE nella ricerca di una COSTANTE: ", e);
			throw new DaoException("ERRORE nella ricerca della costante "+attributo+".", e);
		}
		finally {
			LOG.info(prf+"END");
		}
		
		return valore;
	}
}
