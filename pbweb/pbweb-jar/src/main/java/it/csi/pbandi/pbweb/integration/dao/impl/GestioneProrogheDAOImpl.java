/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.ProrogaIntegrazioneDTO;
import it.csi.pbandi.pbweb.integration.dao.GestioneProrogheDAO;
import it.csi.pbandi.pbweb.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GestioneProrogheDAOImpl extends JdbcDaoSupport implements GestioneProrogheDAO {
    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    public GestioneProrogheDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public List<ProrogaIntegrazioneDTO> getProrogaIntegrazione(Long idDichiarazioneSpesa, HttpServletRequest req) {
        String prf = "[GestioneProrogheDAOImpl::getProrogheDS]";
        LOG.info(prf + " BEGIN");
        List<ProrogaIntegrazioneDTO> prorogaIntegrazioneDTO;

        LOG.info(prf + "idDichiarazioneSpesa=" + idDichiarazioneSpesa);

        try {
            String sql =
                    "SELECT \n" +
                    "proroga.id_richiesta_proroga AS idRichiestaProroga,\n" +
                    "proroga.dt_richiesta AS dtRichiestaProroga,\n" +
                    "proroga.num_giorni_rich AS numGiorniRichProroga,\n" +
                    "proroga.motivazione AS motivazioneProroga,\n" +
                    "proroga.num_giorni_approv AS numGiorniApprovProroga,\n" +
                    "statoProroga.id_stato_proroga AS idStatoProroga,\n" +
                    "statoProroga.desc_stato_proroga AS descStatoProroga,\n" +
                    "integrazione.id_integrazione_spesa AS idRichiestaIntegrazione,\n" +
                    "integrazione.data_richiesta AS dtRichiestaIntegrazione,\n" +
                    "statoIntegrazione.id_stato_richiesta AS idStatoRichiestaIntegrazione,\n" +
                    "statoIntegrazione.desc_stato_richiesta AS descStatoRichiestaIntegrazione\n" +
                    "FROM PBANDI_T_DICHIARAZIONE_SPESA dichiarazioneSpesa\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_dichiarazione_spesa = dichiarazioneSpesa.id_dichiarazione_spesa\n" +
                    "LEFT JOIN PBANDI_D_STATO_RICH_INTEGRAZ statoIntegrazione ON statoIntegrazione.id_stato_richiesta = integrazione.id_stato_richiesta\n" +
                    "JOIN PBANDI_T_PROROGA proroga ON proroga.id_target = integrazione.id_integrazione_spesa\n" +
                    "LEFT JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                    "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n" +
                    "WHERE dichiarazioneSpesa.id_dichiarazione_spesa = ?\n" +
                    "AND entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n" +
                    "AND proroga.id_stato_proroga = 1\n" +
                    "AND integrazione.id_stato_richiesta IN (1, 4)\n" +
                    "ORDER BY integrazione.DATA_RICHIESTA, proroga.dt_richiesta";

            LOG.debug(sql);
            prorogaIntegrazioneDTO = getJdbcTemplate().query(
                    sql,
                    new Object[]{idDichiarazioneSpesa},
                    (rs, rowNum) ->  {
                        ProrogaIntegrazioneDTO dto = new ProrogaIntegrazioneDTO();

                        dto.setIdRichiestaProroga(rs.getLong("idRichiestaProroga"));
                        dto.setDtRichiestaProroga(rs.getDate("dtRichiestaProroga"));
                        dto.setNumGiorniRichProroga(rs.getLong("numGiorniRichProroga"));
                        dto.setMotivazioneProroga(rs.getString("motivazioneProroga"));
                        dto.setNumGiorniApprovProroga(rs.getLong("numGiorniApprovProroga"));
                        dto.setIdStatoProroga(rs.getLong("idStatoProroga"));
                        dto.setDescStatoProroga(rs.getString("descStatoProroga"));
                        dto.setIdRichiestaIntegrazione(rs.getLong("idRichiestaIntegrazione"));
                        dto.setDtRichiestaIntegrazione(rs.getDate("dtRichiestaIntegrazione"));
                        dto.setIdStatoRichiestaIntegrazione(rs.getLong("idStatoRichiestaIntegrazione"));
                        dto.setDescStatoRichiestaIntegrazione(rs.getString("descStatoRichiestaIntegrazione"));

                        return dto;
            });
        } catch (Exception e) {
            prorogaIntegrazioneDTO = new ArrayList<>();
            LOG.info("Exception while trying to getProrogaDS: " + e);
        } finally {
            LOG.info(prf + " END");
        }

        return prorogaIntegrazioneDTO;
    }
    @Override
    public List<ProrogaIntegrazioneDTO> getStoricoProrogheDS(Long idDichiarazioneSpesa, HttpServletRequest req) {
        String prf = "[GestioneProrogheDAOImpl::getStoricoProrogheDS]";
        LOG.info(prf + " BEGIN");
        List<ProrogaIntegrazioneDTO> prorogaIntegrazioneDTO;

        LOG.info(prf + "idDichiarazioneSpesa=" + idDichiarazioneSpesa);

        try {
            String sql =
                    "SELECT \n" +
                    "proroga.id_richiesta_proroga AS idRichiestaProroga,\n" +
                    "proroga.dt_richiesta AS dtRichiestaProroga,\n" +
                    "proroga.num_giorni_rich AS numGiorniRichProroga,\n" +
                    "proroga.motivazione AS motivazioneProroga,\n" +
                    "proroga.num_giorni_approv AS numGiorniApprovProroga,\n" +
                    "statoProroga.id_stato_proroga AS idStatoProroga,\n" +
                    "statoProroga.desc_stato_proroga AS descStatoProroga,\n" +
                    "integrazione.id_integrazione_spesa AS idRichiestaIntegrazione,\n" +
                    "integrazione.data_richiesta AS dtRichiestaIntegrazione,\n" +
                    "statoIntegrazione.id_stato_richiesta AS idStatoRichiestaIntegrazione,\n" +
                    "statoIntegrazione.desc_stato_richiesta AS descStatoRichiestaIntegrazione\n" +
                    "FROM PBANDI_T_DICHIARAZIONE_SPESA dichiarazioneSpesa\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_dichiarazione_spesa = dichiarazioneSpesa.id_dichiarazione_spesa\n" +
                    "LEFT JOIN PBANDI_D_STATO_RICH_INTEGRAZ statoIntegrazione ON statoIntegrazione.id_stato_richiesta = integrazione.id_stato_richiesta\n" +
                    "JOIN PBANDI_T_PROROGA proroga ON proroga.id_target = integrazione.id_integrazione_spesa\n" +
                    "LEFT JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                    "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n" +
                    "WHERE dichiarazioneSpesa.id_dichiarazione_spesa = ?\n" +
                    "AND entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n" +
                    "AND proroga.id_stato_proroga != 1\n" +
                    "ORDER BY integrazione.DATA_RICHIESTA, proroga.dt_richiesta";

            LOG.debug(sql);
            prorogaIntegrazioneDTO = getJdbcTemplate().query(
                    sql,
                    new Object[]{idDichiarazioneSpesa},
                    (rs, rowNum) ->  {
                        ProrogaIntegrazioneDTO dto = new ProrogaIntegrazioneDTO();

                        dto.setIdRichiestaProroga(rs.getLong("idRichiestaProroga"));
                        dto.setDtRichiestaProroga(rs.getDate("dtRichiestaProroga"));
                        dto.setNumGiorniRichProroga(rs.getLong("numGiorniRichProroga"));
                        dto.setMotivazioneProroga(rs.getString("motivazioneProroga"));
                        dto.setNumGiorniApprovProroga(rs.getLong("numGiorniApprovProroga"));
                        dto.setIdStatoProroga(rs.getLong("idStatoProroga"));
                        dto.setDescStatoProroga(rs.getString("descStatoProroga"));
                        dto.setIdRichiestaIntegrazione(rs.getLong("idRichiestaIntegrazione"));
                        dto.setDtRichiestaIntegrazione(rs.getDate("dtRichiestaIntegrazione"));
                        dto.setIdStatoRichiestaIntegrazione(rs.getLong("idStatoRichiestaIntegrazione"));
                        dto.setDescStatoRichiestaIntegrazione(rs.getString("descStatoRichiestaIntegrazione"));

                        return dto;
                    });
        } catch (Exception e) {
            prorogaIntegrazioneDTO = new ArrayList<>();
            LOG.info("Exception while trying to getProrogaDS: " + e);
        } finally {
            LOG.info(prf + " END");
        }

        return prorogaIntegrazioneDTO;
    }
    @Override
    public void approvaProroga(Long idProroga, Long numGiorni, HttpServletRequest req) {
        String prf = "[GestioneProrogheDAOImpl::approvaProroga]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + "idProroga=" + idProroga);
        LOG.info(prf + "numGiorni=" + numGiorni);

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            String sql =
                    "UPDATE PBANDI_T_PROROGA SET \n" +
                    "NUM_GIORNI_APPROV = ?,\n" +
                    "ID_STATO_PROROGA = 2,\n" +
                    "DT_ESITO_RICHIESTA = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = ?,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                    "WHERE ID_RICHIESTA_PROROGA = ?";
            LOG.debug(sql);

            getJdbcTemplate().update(
                    sql,
                    numGiorni, userInfoSec.getIdUtente(), idProroga
            );

        } catch (Exception e) {
            LOG.info("Exception while trying to approvaProroga: " + e);
        } finally {
            LOG.info(prf + " END");
        }
    }
    @Override
    public void respingiProroga(Long idProroga, HttpServletRequest req) {
        String prf = "[GestioneProrogheDAOImpl::approvaProroga]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + "idProroga=" + idProroga);

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            String sql =
                    "UPDATE PBANDI_T_PROROGA SET \n" +
                    "ID_STATO_PROROGA = 3,\n" +
                    "DT_ESITO_RICHIESTA = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = ?,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                    "WHERE ID_RICHIESTA_PROROGA = ?";
            LOG.debug(sql);

            getJdbcTemplate().update(
                    sql,
                    userInfoSec.getIdUtente(), idProroga
            );

        } catch (Exception e) {
            LOG.info("Exception while trying to approvaProroga: " + e);
        } finally {
            LOG.info(prf + " END");
        }
    }
    @Override
    public Boolean gestioneRichiestaProroga(Long idDichiarazioneSpesa, HttpServletRequest req) {
        String prf = "[GestioneProrogheDAOImpl::gestioneRichiestaProroga]";
        LOG.info(prf + " BEGIN");
        Boolean gestioneRichiestaProroga;

        LOG.info(prf + "idDichiarazioneSpesa=" + idDichiarazioneSpesa);

        try {
            String sql =
                    "SELECT \n" +
                    "COUNT(1)" +
                    "FROM PBANDI_T_DICHIARAZIONE_SPESA dichiarazioneSpesa\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_dichiarazione_spesa = dichiarazioneSpesa.id_dichiarazione_spesa\n" +
                    "JOIN PBANDI_T_PROROGA proroga ON proroga.id_target = integrazione.id_integrazione_spesa\n" +
                    "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n" +
                    "WHERE dichiarazioneSpesa.id_dichiarazione_spesa = ?\n" +
                    "AND entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n" +
                    "AND proroga.id_stato_proroga = 1\n" +
                    "AND integrazione.data_invio IS NULL";

            LOG.debug(sql);
            gestioneRichiestaProroga = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{idDichiarazioneSpesa},
                    Boolean.class);
        }catch (IncorrectResultSizeDataAccessException ignored){
            gestioneRichiestaProroga = false;
        }catch (Exception e) {
            gestioneRichiestaProroga = false;
            LOG.info("Exception while trying to gestioneRichiestaProroga: " + e);
        } finally {
            LOG.info(prf + " END");
        }

        return gestioneRichiestaProroga;
    }
    @Override
    public Boolean storicoRichiestaProroga(Long idDichiarazioneSpesa, HttpServletRequest req) {
        String prf = "[GestioneProrogheDAOImpl::storicoRichiestaProroga]";
        LOG.info(prf + " BEGIN");
        Boolean storicoRichiestaProroga;

        LOG.info(prf + "idDichiarazioneSpesa=" + idDichiarazioneSpesa);

        try {
            String sql =
                    "SELECT \n" +
                    "COUNT(1)" +
                    "FROM PBANDI_T_DICHIARAZIONE_SPESA dichiarazioneSpesa\n" +
                    "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_dichiarazione_spesa = dichiarazioneSpesa.id_dichiarazione_spesa\n" +
                    "JOIN PBANDI_T_PROROGA proroga ON proroga.id_target = integrazione.id_integrazione_spesa\n" +
                    "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = proroga.id_entita\n" +
                    "WHERE dichiarazioneSpesa.id_dichiarazione_spesa = ?\n" +
                    "AND entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n" +
                    "AND proroga.id_stato_proroga != 1\n";

            LOG.debug(sql);
            storicoRichiestaProroga = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{idDichiarazioneSpesa},
                    Boolean.class);
        }catch (IncorrectResultSizeDataAccessException ignored){
            storicoRichiestaProroga = false;
        }catch (Exception e) {
            storicoRichiestaProroga = false;
            LOG.info("Exception while trying to storicoRichiestaProroga: " + e);
        } finally {
            LOG.info(prf + " END");
        }

        return storicoRichiestaProroga;
    }
}
