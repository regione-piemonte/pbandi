/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.integration.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservrest.integration.dao.BaseDAO;
import it.csi.pbandi.pbservrest.integration.dao.CupDAO;
import it.csi.pbandi.pbservrest.util.Constants;

public class CupDAOImpl extends BaseDAO implements CupDAO{

	private Logger LOG;

	public CupDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public boolean testConnection() {
		String prf = "[CupDAOImpl::testConnection] ";
		LOG.info( prf + "BEGIN");
		boolean isConnected = false;

		String sql = "SELECT 1 as num FROM DUAL";
		MapSqlParameterSource params = new MapSqlParameterSource();

		try {
			Integer num = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
			LOG.info(prf + "num="+num);

			if(num!=null) {
				isConnected = true;
			}

		} catch (DataAccessException ex ) {
			LOG.error(prf + "Errore connessione al database: " + ex.getMessage());

		}finally {
			LOG.info(prf + "END Stato connessione data base: " + (isConnected ? "Connesso" : "Disconnesso"));
		}

		return isConnected;
	}

	@Override
	public boolean domandaExist(String numeroDomanda) {
		String prf = "[CupDAOImpl::domandaExist] ";
		LOG.info( prf + "BEGIN");
		boolean exist = false;

		String sql = "SELECT ID_DOMANDA from PBANDI_T_DOMANDA WHERE NUMERO_DOMANDA = :numeroDomanda";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("numeroDomanda", numeroDomanda);

		LOG.info(prf + "numeroDomanda="+numeroDomanda);
		LOG.info(prf + "sql="+sql);

		try {
			Integer num = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
			LOG.info(prf + "id_domanda="+num);

			if(num!=null) {
				exist = true;
			}

		} catch (Exception ex ) {
			LOG.error(prf + "Errore : " + ex.getMessage());

		}finally {
			LOG.info(prf + "END");
		}

		return exist;
	}

	@Override
	public boolean aggiornaCodiceCUP(String codiceCup, String numeroDomanda) {
		String prf = "[CupDAOImpl::aggiornaCodiceCUP] ";
		LOG.info( prf + "BEGIN");
		boolean cupAggiornato = false;

		String sql = "UPDATE PBANDI_T_PROGETTO  "
				+ "			SET CUP = :codiceCup "
				+ "			WHERE ID_PROGETTO IN ( "
				+ "			select ID_PROGETTO  "
				+ "			from PBANDI_T_PROGETTO p  "
				+ "			join PBANDI_T_DOMANDA d on p.id_domanda = d.id_domanda "
				+ "			WHERE NUMERO_DOMANDA = :numeroDomanda)";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("codiceCup", codiceCup);
		params.addValue("numeroDomanda", numeroDomanda);

		LOG.info(prf + "codiceCup="+codiceCup);
		LOG.info(prf + "numeroDomanda="+numeroDomanda);
		LOG.info(prf + "sql="+sql);


		int rowsUpdated = 0;
		try {

			rowsUpdated = this.update(sql, params);

			if(rowsUpdated>0) {
				cupAggiornato = true;
				LOG.info(prf + "Aggiornate " + rowsUpdated + " righe sul DB");
			}

		} catch (Exception e) {
			LOG.error( prf + "Errore nell'aggiornare il record, e=" + e.getMessage());

		}finally {
			LOG.info(prf + "END");
		}

		return cupAggiornato;
	}

}
