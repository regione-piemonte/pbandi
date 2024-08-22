/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservwelfare.integration.dao.impl;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.pbservwelfare.dto.FileInfo;
import it.csi.pbandi.pbservwelfare.dto.Fornitori;
import it.csi.pbandi.pbservwelfare.exception.ErroreGestitoException;
import it.csi.pbandi.pbservwelfare.integration.dao.BaseDAO;
import it.csi.pbandi.pbservwelfare.integration.dao.GestioneProrogheDAO;
import it.csi.pbandi.pbservwelfare.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;

public class GestioneProrogheDAOImpl extends BaseDAO implements GestioneProrogheDAO {

    private Logger LOG;

    public GestioneProrogheDAOImpl() {
        LOG = Logger.getLogger(Constants.COMPONENT_NAME);
    }


    @Override
    public BigDecimal getIdControdeduzione(BigDecimal numeroRevoca) {
        String prf = "[GestioneProrogheDAOImpl::getIdControdeduzione]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", numeroRevoca=" + numeroRevoca);

        BigDecimal idControdeduzione;

        try {
            String sql = "SELECT ptc.ID_CONTRODEDUZ \n" +
                    "FROM PBANDI_T_CONTRODEDUZ ptc \n" +
                    "JOIN PBANDI_C_ENTITA pce ON pce.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                    "JOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptgr.ID_GESTIONE_REVOCA = ptc.ID_TARGET \n" +
                    "WHERE ptc.ID_ENTITA = pce.ID_ENTITA\n" +
                    "AND ptgr.NUMERO_REVOCA = :numeroRevoca";

            LOG.debug(sql);

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("numeroRevoca", numeroRevoca, Types.NUMERIC);

            idControdeduzione = this.namedParameterJdbcTemplate.queryForObject(sql, param, BigDecimal.class);
        } catch (DataAccessException ex) {
            LOG.error(prf + "DataAccessException while trying to getIdControdeduzione", ex);
            idControdeduzione = null;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to getIdControdeduzione", e);
            idControdeduzione = null;
        } finally {
            LOG.info(prf + " END");
        }

        return idControdeduzione;
    }

    @Override
    public void insertProrogaControdeduzione(BigDecimal idControdeduzione, Integer numeroGiorni, String motivazione) {
        String prf = "[GestioneProrogheDAOImpl::insertProrogaControdeduzione]";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + ", idControdeduzione=" + idControdeduzione);
        LOG.info(prf + ", numeroGiorni=" + numeroGiorni);
        LOG.info(prf + ", motivazione=" + motivazione);

        String sql;
        MapSqlParameterSource param= new MapSqlParameterSource();
        try {
            //getIdEntitaControdeduzione
            sql = "SELECT pce.ID_ENTITA FROM PBANDI_C_ENTITA pce WHERE pce.NOME_ENTITA = 'PBANDI_T_CONTRODEDUZ'\n";
            BigDecimal idEntitaControdeduz = this.namedParameterJdbcTemplate.queryForObject(sql, param, BigDecimal.class);
            //getNewIdProroga
            sql = "SELECT PBANDI.SEQ_PBANDI_T_PROROGA.nextval FROM dual";
            BigDecimal idRichiestaProroga = this.namedParameterJdbcTemplate.queryForObject(sql, param, BigDecimal.class);
            //Insert proroga
            sql = "INSERT INTO PBANDI_T_PROROGA ptp \n" +
                    "(ID_RICHIESTA_PROROGA, ID_ENTITA, ID_TARGET, DT_RICHIESTA, NUM_GIORNI_RICH, MOTIVAZIONE, ID_STATO_PROROGA, DT_INSERIMENTO, ID_UTENTE_INS)\n" +
                    "VALUES (:idRichiestaProroga, :idEntitaControdeduz, :idControdeduzione, CURRENT_DATE, :numeroGiorni, :motivazione, 1, CURRENT_DATE, -20)";
            LOG.debug(sql);
            param = new MapSqlParameterSource();
            param.addValue("idRichiestaProroga", idRichiestaProroga, Types.NUMERIC);
            param.addValue("idEntitaControdeduz", idEntitaControdeduz, Types.NUMERIC);
            param.addValue("idControdeduzione", idControdeduzione, Types.NUMERIC);
            param.addValue("numeroGiorni", numeroGiorni, Types.INTEGER);
            param.addValue("numeroRevoca", motivazione, Types.VARCHAR);

            if(this.namedParameterJdbcTemplate.update(sql, param) == 0){
                throw new Exception("Errore durante l'inserimento della richiesta di proroga");
            }
            //Update controdeduzione
            sql = "UPDATE PBANDI_T_CONTRODEDUZ SET \n" +
                    "ID_ATTIV_CONTRODEDUZ = 1,\n" +
                    "DT_ATTIV_CONTRODEDUZ = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = -20,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                    "WHERE ID_CONTRODEDUZ = :idControdeduzione";

            LOG.debug(sql);

            param = new MapSqlParameterSource();
            param.addValue("idControdeduzione", idControdeduzione, Types.NUMERIC);

            if(this.namedParameterJdbcTemplate.update(sql, param) == 0){
                throw new Exception("Errore durante l'update della controdeduzione");
            }
            //Update gestioneRevoca
            sql = "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                    "ID_ATTIVITA_REVOCA = 8,\n" +
                    "DT_ATTIVITA = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = -20,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                    "WHERE ID_GESTIONE_REVOCA IN (\n" +
                    "\tSELECT ptc.ID_TARGET FROM PBANDI_T_CONTRODEDUZ ptc WHERE ptc.ID_CONTRODEDUZ = :idControdeduzione\n" +
                    ")";

            LOG.debug(sql);

            param = new MapSqlParameterSource();
            param.addValue("idControdeduzione", idControdeduzione, Types.NUMERIC);

            if(this.namedParameterJdbcTemplate.update(sql, param) == 0){
                throw new Exception("Errore durante l'update della gestione revoca");
            }        } catch (DataAccessException ex) {
            LOG.error(prf + "DataAccessException while trying to insertProrogaControdeduzione", ex);
            throw ex;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to insertProrogaControdeduzione", e);
            throw new ErroreGestitoException("DaoException while trying to insertProrogaControdeduzione", e);
        } finally {
            LOG.info(prf + " END");
        }
    }
}
