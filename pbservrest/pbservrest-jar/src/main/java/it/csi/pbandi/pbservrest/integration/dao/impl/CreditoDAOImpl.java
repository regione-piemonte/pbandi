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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbservrest.dto.StatoCredito;
import it.csi.pbandi.pbservrest.exception.ErroreGestitoException;
import it.csi.pbandi.pbservrest.exception.RecordNotFoundException;
import it.csi.pbandi.pbservrest.integration.dao.BaseDAO;
import it.csi.pbandi.pbservrest.integration.dao.CreditoDAO;

import it.csi.pbandi.pbservrest.util.Constants;


public class CreditoDAOImpl extends BaseDAO implements CreditoDAO {

	private Logger LOG;

	public CreditoDAOImpl() {
		LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	}

	@Override
	public List<StatoCredito> getStatoCredito(String numeroDomanda) {
	
		LOG.info("[CreditoDAOImpl::getStatoCredito] BEGIN");
		List<StatoCredito> dati;
		LOG.info( "[CreditoDAOImpl::getStatoCredito] numeroDomanda=" + numeroDomanda);
		try {

			String sql =" SELECT                                                             \n"
					+ "   PTP.CODICE_VISUALIZZATO,                                           \n"
					+ "   PRSPSCF.DT_INIZIO_VALIDITA,                                        \n"
					+ "   PDSCF.desc_stato_credito_fp                                        \n"
					+ " FROM                                                                 \n"
					+ "   PBANDI_D_STATO_CREDITO_FP PDSCF                                    \n"
					+ " JOIN                                                                 \n"
					+ "   PBANDI_R_SOGG_PROG_STA_CRED_FP PRSPSCF                             \n"
					+ "  ON PDSCF.Id_stato_credito_fp=2                                      \n"
					+ "  OR PDSCF.id_stato_credito_fp=3 OR PDSCF.Id_stato_credito_fp=4       \n"
					+ "  JOIN                                                                \n"
					+ "   PBANDI_T_PROGETTO PTP                                              \n"
					+ "  ON PRSPSCF.Dt_fine_validita is null                                 \n"
					+ "  JOIN                                                                \n"
					+ "  PBANDI_R_SOGGETTO_PROGETTO PRSP                                     \n"
					+ "  ON PRSP.Id_progetto= PTP.Id_progetto                                \n"
					+ "  AND PRSP.PROGR_SOGGETTO_PROGETTO = PRSPSCF.PROGR_SOGGETTO_PROGETTO  \n"
					+ "  JOIN                                                                \n"
					+ "   PBANDI_R_SOGGETTO_DOMANDA PRSD                                     \n"
					+ "  ON PRSD.Id_soggetto= PRSP.ID_SOGGETTO                               \n"
					+ "  AND PRSP.id_tipo_anagrafica=1                                       \n"
					+ "  AND PRSP.id_tipo_beneficiario<>4                                    \n"
					+ "  AND PRSP.Dt_fine_validita IS null                                   \n"
					+ "  JOIN                                                                \n"
					+ "   PBANDI_T_DOMANDA PTD                                               \n"
					+ "  ON PTD.id_domanda= PRSD.id_domanda                                  \n"
					+ "  AND PRSD.id_tipo_anagrafica=1 AND PRSD.id_tipo_beneficiario<>4      \n"
					+ "  WHERE PTD.NUMERO_DOMANDA= :numeroDomanda                            \n";
			 

			LOG.debug("[CreditoDAOImpl::getStatoCredito] sql="+sql);

			MapSqlParameterSource param = new MapSqlParameterSource(); 
			param.addValue("numeroDomanda", numeroDomanda, Types.VARCHAR);   

			dati = this.namedParameterJdbcTemplate.query(sql, param, new ResultSetExtractor<List<StatoCredito>>() {
				@Override
				public List<StatoCredito> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<StatoCredito> elencoDati = new ArrayList<>();
					while(rs.next())
					{
						StatoCredito stato = new StatoCredito();
						
						stato.setCodiceProgetto(rs.getString("CODICE_VISUALIZZATO"));
						stato.setStatoCredito(rs.getString("desc_stato_credito_fp"));
						stato.setDtInzioValidita(rs.getDate("DT_INIZIO_VALIDITA"));
						
						elencoDati.add(stato);

					}
					return elencoDati;
				}
			  });
		}catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			LOG.error( "[CreditoDAOImpl::getStatoCredito] Exception while trying to read StatoCredito", e);
			throw new ErroreGestitoException("DaoException while trying to read StatoCredito", e);
		} finally {
			LOG.info( "[CreditoDAOImpl::getStatoCredito] END");
		}
		return dati;
	}

}