/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.ContestazioniDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.AllegatiContestazioniVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.ContestazioniVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ContestazioniDAOImpl extends JdbcDaoSupport implements ContestazioniDAO {
    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    public ContestazioniDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Autowired
    DocumentoManager documentoManager;

    @Override
    public List<AllegatiContestazioniVO> getAllegati(Long idContestazione) {
        String prf = "[ContestazioniDAOImpl::getAllegati]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + "idControdeduz=" + idContestazione);

        List<AllegatiContestazioniVO> dati;

        try {
            String sql = "SELECT\n" +
                    "fileEntita.ID_FILE_ENTITA, \n" +
                    "fileEntita.ID_FILE, \n" +
                    "fileEntita.FLAG_ENTITA, \n" +
                    "docIndex.ID_DOCUMENTO_INDEX, \n" +
                    "docIndex.NOME_FILE, \n" +
                    "tipoDocIndex.DESC_BREVE_TIPO_DOC_INDEX\n" +
                    "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                    "JOIN PBANDI_T_FILE files ON files.ID_FILE = fileEntita.ID_FILE\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX docIndex ON docIndex.ID_DOCUMENTO_INDEX = files.ID_DOCUMENTO_INDEX\n" +
                    "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocIndex ON tipoDocIndex.ID_TIPO_DOCUMENTO_INDEX = docIndex.ID_TIPO_DOCUMENTO_INDEX\n" +
                    "WHERE fileEntita.ID_ENTITA = 610 AND fileEntita.ID_TARGET = :idContestazione";

            LOG.debug(sql);
            Object[] args = new Object[]{idContestazione};
            int[] types = new int[]{Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                    (rs, rownum) -> {
                        AllegatiContestazioniVO item = new AllegatiContestazioniVO();

                        item.setNomeFile(rs.getString("NOME_FILE"));
                        item.setIdDocumentoIndex(rs.getInt("ID_DOCUMENTO_INDEX"));
                        item.setFlagEntita(rs.getString("FLAG_ENTITA"));
                        item.setIdFileEntita(rs.getInt("ID_FILE_ENTITA"));
                        item.setCodTipoDoc(rs.getString("DESC_BREVE_TIPO_DOC_INDEX"));

                        return item;
                    });

        } catch (Exception e) {
            LOG.info("Error while trying to getAllegati: " + e);
            dati = new ArrayList<>();
        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }

    @Override
    public Boolean deleteAllegato(Long idFileEntita) {
        String prf = "[ContestazioniDAOImpl::deleteAllegato]";
        LOG.info(prf + " BEGIN");

        boolean result;

        String query = "DELETE FROM PBANDI_T_FILE_ENTITA ptfe \n" +
                "WHERE ID_ENTITA = 610\n" +
                "AND ID_FILE_ENTITA = :idFileEntita";

        LOG.debug(query);

        Object[] args = new Object[]{idFileEntita};
        int[] types = new int[]{Types.INTEGER};

        try {
            result = getJdbcTemplate().update(query, args, types) > 0;

            if (!result) {
                LOG.info("Record con idFileEntita = " + idFileEntita + " non trovato.");
            }
        } catch (Exception e) {
            LOG.info("Error while trying to deleteAllegato: " + e);
            result = false;
        }

        LOG.info(prf + " end");
        return result;
    }

    @Override
    public void inserisciLetteraAllegato(List<AllegatiContestazioniVO> allegati, Long idContestazione) {
        String prf = "[ContestazioniDAOImpl::insertFileEntita]";
        LOG.info(prf + " BEGIN");

        try {
            String query = "INSERT INTO PBANDI_T_FILE_ENTITA ptfe (\n" +
                    "ID_FILE_ENTITA,\n" +
                    "ID_FILE,\n" +
                    "ID_ENTITA,\n" +
                    "ID_TARGET,\n" +
                    "FLAG_ENTITA\n" +
                    ") VALUES (\n" +
                    "SEQ_PBANDI_T_FILE_ENTITA.nextval,\n" +
                    "(\n" +
                    "\tSELECT ID_FILE FROM PBANDI_T_FILE ptf \n" +
                    "\tWHERE ID_DOCUMENTO_INDEX = :idDocumentoIndex\n" +
                    "),\n" +
                    "610,\n" +
                    ":idContestazione,\n" +
                    ":flagEntita\n" +
                    ")";

            LOG.debug(query);


            for (AllegatiContestazioniVO allegatiContestazioniVO : allegati) {
                Object[] args = new Object[]{allegatiContestazioniVO.getIdDocumentoIndex(), idContestazione, allegatiContestazioniVO.getFlagEntita()};
                int[] types = new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR};
                getJdbcTemplate().update(query, args, types);
            }
        } catch (Exception e) {
            LOG.info("Error while trying to inserisciLetteraAllegato: " + e);
        } finally {
            LOG.info(prf + " END");
        }
    }

    @Override
    public List<String> getCodiceProgetto(Long idProgetto) {
        String prf = "[ContestazioniDAOImpl::getCodiceProgetto]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + "idProgetto=" + idProgetto);

        List<String> dati;
        try {
            String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO ptp WHERE ID_PROGETTO = :idProgetto \n";

            LOG.debug(sql);
            Object[] args = new Object[]{idProgetto};
            int[] types = new int[]{Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                    (rs, rownum) -> rs.getString("CODICE_VISUALIZZATO")
            );

        } catch (RecordNotFoundException e) {
            LOG.info("Error while trying to getCodiceProgetto: " + e);
            dati = new ArrayList<>();
        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }

    @Override
    public List<ContestazioniVO> getContestazioni(Long idProgetto) {
        String prf = "[ContestazioniDAOImpl::getContestazioni]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + "idProgetto=" + idProgetto);

        List<ContestazioniVO> dati;
        try {
            String sql =
                    "SELECT \n" +
                            "gestioneRevoca.id_gestione_revoca AS idGestioneRevoca,\n" +
                            "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                            "gestioneRevoca.dt_notifica AS dataNotifica,\n" +
                            "gestioneRevoca.num_protocollo AS numeroProtocollo,\n" +
                            "causaleBlocco.desc_causale_blocco AS causaRevoca,\n" +
                            "gestioneRevoca.id_stato_revoca AS idStatoRevoca,\n" +
                            "gestioneRevoca.id_attivita_revoca AS idAttivitaRevoca,\n" +
                            "NULL AS idContestazione,\n" +
                            "NULL AS numeroContestazione,\n" +
                            "NULL AS idStatoContestazione,\n" +
                            "NULL AS descStatoContestazione,\n" +
                            "NULL AS dtStatoContestazione,\n" +
                            "(CASE WHEN gestioneRevoca.dt_notifica IS NOT NULL THEN (gestioneRevoca.dt_notifica + gestioneRevoca.gg_risposta) ELSE (NULL) END) AS dataScadenzaContestazione,\n" +
                            "0 AS letteraAccompagnatoria\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                            "JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = gestioneRevoca.id_causale_blocco\n" +
                            "LEFT JOIN PBANDI_C_ENTITA entita ON entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                            "LEFT JOIN PBANDI_T_CONTESTAZ contestaz ON contestaz.id_target = gestioneRevoca.id_gestione_revoca AND contestaz.id_entita = entita.id_entita\n" +
                            "WHERE gestioneRevoca.id_tipologia_revoca = 3\n" +
                            "AND gestioneRevoca.id_stato_revoca = 8\n" +
                            "AND (gestioneRevoca.id_attivita_revoca IN (11, 14) OR gestioneRevoca.id_attivita_revoca IS NULL)\n" +
                            "AND gestioneRevoca.id_progetto = :idProgetto\n" +
                            "AND gestioneRevoca.dt_notifica IS NOT NULL\n" +
                            "AND contestaz.id_contestaz IS NULL\n" +
                            "UNION \n" +
                            "SELECT\n" +
                            "gestioneRevoca.id_gestione_revoca,\n" +
                            "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                            "gestioneRevoca.dt_notifica AS dataNotifica,\n" +
                            "gestioneRevoca.num_protocollo AS numeroProtocollo,\n" +
                            "causaleBlocco.desc_causale_blocco AS causaRevoca,\n" +
                            "gestioneRevoca.id_stato_revoca AS idStatoRevoca,\n" +
                            "gestioneRevoca.id_attivita_revoca AS idAttivitaRevoca,\n" +
                            "contestaz.id_contestaz AS idContestazione,\n" +
                            "contestaz.numero_contestaz AS numeroContestazione,\n" +
                            "statoContestaz.id_stato_contestaz AS idStatoContestazione,\n" +
                            "statoContestaz.desc_stato_contestaz AS descStatoContestazione,\n" +
                            "contestaz.dt_stato_contestaz AS dtStatoContestazione,\n" +
                            "(CASE WHEN gestioneRevoca.dt_notifica IS NOT NULL THEN (gestioneRevoca.dt_notifica + gestioneRevoca.gg_risposta) ELSE (NULL) END) AS dataScadenzaContestazione,\n" +
                            "(CASE WHEN letteraAccompagnatoria.id_file_entita IS NOT NULL THEN 1 ELSE 0 END) AS letteraAccompagnatoria\n" +
                            "FROM PBANDI_T_CONTESTAZ contestaz\n" +
                            "LEFT JOIN PBANDI_D_STATO_CONTESTAZ statoContestaz ON statoContestaz.id_stato_contestaz = contestaz.id_stato_contestaz\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = contestaz.id_entita AND entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                            "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON gestioneRevoca.id_gestione_revoca = contestaz.id_target\n" +
                            "JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = gestioneRevoca.id_causale_blocco\n" +
                            "LEFT JOIN PBANDI_T_FILE_ENTITA letteraAccompagnatoria ON \n" +
                            "\tletteraAccompagnatoria.id_entita = 610 AND\n" +
                            "\tletteraAccompagnatoria.id_target = contestaz.id_contestaz AND\n" +
                            "\tletteraAccompagnatoria.flag_entita = 'I'\n" +
                            "WHERE gestioneRevoca.id_progetto = :idProgetto\n" +
                            "AND gestioneRevoca.dt_notifica IS NOT NULL";

            LOG.debug(sql);
            Object[] args = new Object[]{idProgetto, idProgetto};
            int[] types = new int[]{Types.INTEGER, Types.INTEGER};

            dati = getJdbcTemplate().query(
                    sql, args, types,
                    (rs, rownum) -> {
                        ContestazioniVO item = new ContestazioniVO();

                        item.setIdGestioneRevoca(rs.getLong("idGestioneRevoca"));
                        item.setNumeroRevoca(rs.getLong("numeroRevoca"));
                        item.setDataNotifica(rs.getDate("dataNotifica"));
                        item.setNumeroProtocollo(rs.getString("numeroProtocollo"));
                        item.setCausaRevoca(rs.getString("causaRevoca"));

                        item.setIdContestazione(rs.getLong("idContestazione"));
                        if (rs.wasNull()) {
                            item.setIdContestazione(null);
                        }
                        item.setNumeroContestazione(rs.getLong("numeroContestazione"));
                        if (rs.wasNull()) {
                            item.setNumeroContestazione(null);
                        }
                        item.setIdStatoContestazione(rs.getLong("idStatoContestazione"));
                        item.setDescStatoContestazione(rs.getString("descStatoContestazione"));
                        item.setDtStatoContestazione(rs.getDate("dtStatoContestazione"));
                        item.setDataScadenzaContestazione(rs.getDate("dataScadenzaContestazione"));

                        long idStatoRevoca = rs.getLong("idStatoRevoca");
                        long idAttivitaRevoca = rs.getLong("idAttivitaRevoca");
                        item.setAbilitatoContestaz(item.getIdContestazione() == null && idStatoRevoca == 8L && (
                                idAttivitaRevoca == 0L || idAttivitaRevoca == 11L
                        ));
                        item.setAbilitatoIntegra(item.getIdStatoContestazione() != null && item.getIdStatoContestazione() == 1);
                        item.setAbilitatoInvia(item.getIdStatoContestazione() != null && item.getIdStatoContestazione() == 1 && rs.getLong("letteraAccompagnatoria") == 1);
                        item.setAbilitatoElimina(item.getIdStatoContestazione() != null && item.getIdStatoContestazione() == 1);

                        return item;
                    }
            );

        } catch (Exception e) {
            LOG.info("Error while trying to getContestazioni: " + e);
            dati = new ArrayList<>();
        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }

    @Override
    public Boolean inserisciContestazione(Long idGestioneRevoca, HttpServletRequest req) {
        String prf = "[ContestazioniDAOImpl::inserisciContestazione]";
        LOG.info(prf + " BEGIN");

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        boolean result;

        try {

            String query = "INSERT INTO PBANDI_T_CONTESTAZ ptc (\n" +
                    "ID_CONTESTAZ,\n" +
                    "NUMERO_CONTESTAZ,\n" +
                    "ID_ENTITA,\n" +
                    "ID_TARGET,\n" +
                    "ID_STATO_CONTESTAZ,\n" +
                    "DT_STATO_CONTESTAZ,\n" +
                    "DT_INIZIO_VALIDITA,\n" +
                    "ID_UTENTE_INS,\n" +
                    "DT_INSERIMENTO\n" +
                    ") VALUES (\n" +
                    "SEQ_PBANDI_T_CONTESTAZ.nextval,\n" +
                    "SEQ_PBANDI_T_CONTESTAZ.nextval,\n" +
                    "516,\n" +
                    ":idGestioneRevoca,\n" +
                    "1,\n" +
                    "CURRENT_DATE,\n" +
                    "CURRENT_DATE,\n" +
                    ":idUtente,\n" +
                    "CURRENT_DATE\n" +
                    ")";

            LOG.debug(query);

            Object[] args = new Object[]{idGestioneRevoca, userInfoSec.getIdUtente()};
            int[] types = new int[]{Types.INTEGER, Types.INTEGER};

            result = getJdbcTemplate().update(query, args, types) > 0;

            if (!result) {
                LOG.info("Errore durante l'inserimento del record per idGestioneRevoca: " + idGestioneRevoca);
            }

        } catch (Exception e) {
            LOG.info("Error while trying to inserisciContestazione: ", e);
            result = false;
        }

        LOG.info(prf + " END");
        return result;
    }

    @Override
    public Boolean inviaContestazione(Long idContestazione, HttpServletRequest req) {
        String prf = "[ContestazioniDAOImpl::aggiornaContestazione]";
        LOG.info(prf + " BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        boolean result;

        try {
            //AGGIORNO CONTESTAZ
            String query1 = "UPDATE PBANDI_T_CONTESTAZ SET \n" +
                    "ID_STATO_CONTESTAZ = 2,\n" +
                    "DT_STATO_CONTESTAZ = CURRENT_DATE, \n" +
                    "ID_UTENTE_AGG = :idUtente,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                    "WHERE ID_CONTESTAZ = :idContestazione";
            LOG.debug(query1);

            Object[] args1 = new Object[]{userInfoSec.getIdUtente(), idContestazione};
            int[] types1 = new int[]{Types.INTEGER, Types.INTEGER};

            //AGGIORNO GESTIONE REVOCA
            String query2 = "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                    "ID_ATTIVITA_REVOCA = 12,\n" +
                    "DT_ATTIVITA = CURRENT_DATE, \n" +
                    "ID_UTENTE_AGG = :idUtente,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                    "WHERE ID_GESTIONE_REVOCA IN (" +
                    "\tSELECT ID_TARGET FROM PBANDI_T_CONTESTAZ ptc \n" +
                    "\tWHERE ID_CONTESTAZ = :idContestazione" +
                    ")";
            LOG.debug(query2);

            Object[] args2 = new Object[]{userInfoSec.getIdUtente(), idContestazione};
            int[] types2 = new int[]{Types.INTEGER, Types.INTEGER};


            result = getJdbcTemplate().update(query1, args1, types1) > 0 && getJdbcTemplate().update(query2, args2, types2) > 0;
        } catch (Exception e) {
            LOG.info("Error while trying to inviaContestazione: " + e);
            result = false;
        } finally {
            LOG.info(prf + " END");
        }

        return result;
    }

    @Override
    public Boolean eliminaContestazione(Long idContestazione, HttpServletRequest req) {
        String prf = "[ContestazioniDAOImpl::eliminaContestazione]";
        LOG.info(prf + " BEGIN");

        boolean result;

        String query = "DELETE FROM PBANDI_T_CONTESTAZ WHERE ID_CONTESTAZ = :idContestazione\n";

        LOG.debug(query);

        Object[] args = new Object[]{idContestazione};
        int[] types = new int[]{Types.INTEGER};

        try {
            result = getJdbcTemplate().update(query, args, types) > 0;

            if (!result) {
                LOG.info("Nessuna contestazione trovata con id_contestaz = " + idContestazione);
            }

        } catch (Exception e) {
            LOG.info("Error while trying to eliminaContestazione:" + e);
            result = false;
        } finally {
            LOG.info(prf + " End");
        }

        return result;
    }
}
