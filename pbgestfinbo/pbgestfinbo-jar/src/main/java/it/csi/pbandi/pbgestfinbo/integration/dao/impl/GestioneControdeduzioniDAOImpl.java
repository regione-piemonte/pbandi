/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.integration.dao.GestioneControdeduzioniDAO;
import it.csi.pbandi.pbgestfinbo.integration.vo.controdeduzioni.*;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GestioneControdeduzioniDAOImpl extends JdbcDaoSupport implements GestioneControdeduzioniDAO {
    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    public GestioneControdeduzioniDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public GestioneControdeduzioniDAOImpl() {

    }

    @Autowired
    DocumentoManager documentoManager;

    @Autowired
    NeofluxBusinessImpl neofluxBusinessImpl;

    @Override
    public List<AllegatiControdeduzioniVO> getAllegati(Long idControdeduz) {
        String prf = "[GestioneControdeduzioniDAOImpl::getAllegati]";
        LOG.info(prf + " BEGIN");

        List<AllegatiControdeduzioniVO> dati;

        LOG.info(prf + "idControdeduz = " + idControdeduz);

        try {
            String sql =
                    "SELECT \n" +
                            "fileEntita.ID_FILE, \n" +
                            "fileEntita.FLAG_ENTITA as flagEntita, \n" +
                            "fileEntita.ID_FILE_ENTITA as idFileEntita, \n" +
                            "docIndex.ID_DOCUMENTO_INDEX as idDocumentoIndex, \n" +
                            "docIndex.NOME_FILE as nomeFile, \n" +
                            "tipoDocIndex.DESC_BREVE_TIPO_DOC_INDEX as descBreveTipoDocIndex\n" +
                            "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                            "JOIN PBANDI_T_FILE files ON files.ID_FILE = fileEntita.ID_FILE\n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX docIndex ON docIndex.ID_DOCUMENTO_INDEX = files.ID_DOCUMENTO_INDEX\n" +
                            "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocIndex ON tipoDocIndex.ID_TIPO_DOCUMENTO_INDEX = docIndex.ID_TIPO_DOCUMENTO_INDEX\n" +
                            "WHERE fileEntita.ID_ENTITA = 609 AND\n" +
                            "fileEntita.ID_TARGET = :idControdeduz";
            LOG.debug(sql);

            Object[] args = new Object[]{idControdeduz};
            int[] types = new int[]{Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                    (rs, rownum) -> {
                        AllegatiControdeduzioniVO dto = new AllegatiControdeduzioniVO();

                        dto.setNomeFile(rs.getString("nomeFile"));
                        dto.setFlagEntita(rs.getString("flagEntita"));
                        dto.setIdDocumentoIndex(rs.getInt("idDocumentoIndex"));
                        dto.setIdFileEntita(rs.getInt("idFileEntita"));
                        dto.setCodTipoDoc(rs.getString("descBreveTipoDocIndex"));

                        return dto;
                    });

        } catch (Exception e) {
            LOG.info("Error while trying to getAllegati: " + e);
            dati = new ArrayList<>();
        }

        LOG.info(prf + " END");

        return dati;
    }

    @Override
    public Boolean inserisciAllegati(Long idControdeduz, List<AllegatiControdeduzioniVO> allegati) {
        String prf = "[GestioneControdeduzioniDAOImpl::inserisciAllegati]";
        LOG.info(prf + " BEGIN");

        int rowsUpdated = 0;

        try {
            String query =
                    "INSERT INTO PBANDI_T_FILE_ENTITA (\n" +
                            "ID_FILE_ENTITA,\n" +
                            "ID_FILE,\n" +
                            "ID_ENTITA,\n" +
                            "ID_TARGET,\n" +
                            "FLAG_ENTITA\n" +
                            ") VALUES (\n" +
                            "SEQ_PBANDI_T_FILE_ENTITA.NEXTVAL,\n" +
                            "(\n" +
                            "\tSELECT ID_FILE FROM PBANDI_T_FILE\n" +
                            "\tWHERE ID_DOCUMENTO_INDEX = :idDocumentoIndex\n" +
                            "),\n" +
                            "609,\n" +
                            ":idControdeduz,\n" +
                            ":flagEntita\n" +
                            ")";

            LOG.debug(query);

            for (AllegatiControdeduzioniVO allegato : allegati) {
                Object[] args = new Object[]{allegato.getIdDocumentoIndex(), idControdeduz, allegato.getFlagEntita()};
                int[] types = new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR};
                rowsUpdated += getJdbcTemplate().update(query, args, types);
            }
        } catch (Exception e) {
            LOG.info("Error while trying to inserisciAllegati: " + e);
            rowsUpdated = 0;
        }

        return rowsUpdated > 0;
    }

    @Override
    public Boolean deleteAllegato(Long idFileEntita) {
        String prf = "[GestioneControdeduzioniDAOImpl::deleteAllegato]";
        LOG.info(prf + " BEGIN");

        int rowsUpdated = 0;

        String query =
                "DELETE FROM PBANDI_T_FILE_ENTITA\n" +
                        "WHERE ID_ENTITA = 609\n" +
                        "AND ID_FILE_ENTITA = :idFileEntita";

        LOG.debug(query);

        Object[] args = new Object[]{idFileEntita};
        int[] types = new int[]{Types.INTEGER};

        try {
            rowsUpdated = getJdbcTemplate().update(query, args, types);
        } catch (Exception e) {
            LOG.info("Error while trying to deleteAllegato: ", e);
        }

        LOG.info("N. record aggiornati:\n" + rowsUpdated);
        LOG.info(prf + " end");
        return rowsUpdated > 0;
    }

    @Override
    public List<ProgettoVO> getIntestazioneControdeduzioni(Long idProgetto) {
        String prf = "[GestioneControdeduzioniDAOImpl::getIntestazioneControdeduzioni]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + "idProgetto=" + idProgetto);

        List<ProgettoVO> dati;

        try {
            String sql =
                    "SELECT\n" +
                            "ID_PROGETTO as idProgetto,\n" +
                            "CODICE_VISUALIZZATO as codiceVisualizzatoProgetto,\n" +
                            "TITOLO_PROGETTO as titoloProgetto\n" +
                            "FROM PBANDI_T_PROGETTO\n" +
                            "WHERE ID_PROGETTO = :idProgetto";
            LOG.debug(sql);
            Object[] args = new Object[]{idProgetto};
            int[] types = new int[]{Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                    (rs, rownum) -> {
                        ProgettoVO dto = new ProgettoVO();

                        dto.setIdProgetto(rs.getLong("idProgetto"));
                        dto.setCodiceVisualizzatoProgetto(rs.getString("codiceVisualizzatoProgetto"));
                        dto.setTitoloProgetto(rs.getString("titoloProgetto"));

                        return dto;
                    });

        } catch (Exception e) {
            LOG.info("Error while trying to getIntestazioneControdeduzioni: " + e);
            dati = new ArrayList<>();
        }

        LOG.info(prf + " END");

        return dati;
    }

    @Override
    public List<ControdeduzioneVO> getControdeduzioni(Long idProgetto) {
        String prf = "[GestioneControdeduzioniDAOImpl::getControdeduzioni]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + "idProgetto=" + idProgetto);

        List<ControdeduzioneVO> dati;

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
                            "NULL AS idControdeduzione,\n" +
                            "NULL AS numeroControdeduzione,\n" +
                            "NULL AS idStatoControdeduzione,\n" +
                            "NULL AS descStatoControdeduzione,\n" +
                            "NULL AS dtStatoControdeduz,\n" +
                            "NULL AS idAttivitaControdeduzione,\n" +
                            "NULL AS descAttivitaControdeduzione,\n" +
                            "NULL AS dtAttivitaControdeduz, \n" +
                            "(CASE WHEN gestioneRevoca.dt_notifica IS NOT NULL THEN (gestioneRevoca.dt_notifica + gestioneRevoca.gg_risposta) ELSE (NULL) END) AS dataScadenzaControdeduzione,\n" +
                            "0 AS letteraAccompagnatoria\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                            "JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = gestioneRevoca.id_causale_blocco\n" +
                            "LEFT JOIN PBANDI_C_ENTITA entita ON entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                            "LEFT JOIN PBANDI_T_CONTRODEDUZ controdeduzione ON controdeduzione.id_target = gestioneRevoca.id_gestione_revoca AND controdeduzione.id_entita = entita.id_entita\n" +
                            "WHERE gestioneRevoca.id_progetto = :idProgetto\n" +
                            "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                            "AND gestioneRevoca.dt_fine_validita IS NULL\n" +
                            "AND gestioneRevoca.id_stato_revoca = 6\n" +
                            "AND (gestioneRevoca.id_attivita_revoca IN (8, 9, 10, 11) OR gestioneRevoca.id_attivita_revoca IS NULL)\n" +
                            "AND gestioneRevoca.dt_notifica IS NOT NULL\n" +
                            "AND controdeduzione.ID_CONTRODEDUZ IS NULL\n" +
                            "UNION \n" +
                            "SELECT \n" +
                            "gestioneRevoca.id_gestione_revoca AS idGestioneRevoca,\n" +
                            "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                            "gestioneRevoca.dt_notifica AS dataNotifica,\n" +
                            "gestioneRevoca.num_protocollo AS numeroProtocollo,\n" +
                            "causaleBlocco.desc_causale_blocco AS causaRevoca,\n" +
                            "gestioneRevoca.id_stato_revoca AS idStatoRevoca,\n" +
                            "gestioneRevoca.id_attivita_revoca AS idAttivitaRevoca,\n" +
                            "controdeduzione.id_controdeduz AS idControdeduzione,\n" +
                            "controdeduzione.numero_controdeduz AS numeroControdeduzione,\n" +
                            "statoControdeduz.id_stato_controdeduz AS idStatoControdeduzione,\n" +
                            "statoControdeduz.desc_stato_controdeduz AS descStatoControdeduzione,\n" +
                            "controdeduzione.dt_stato_controdeduz AS dtStatoControdeduz,\n" +
                            "attivitaControdeduz.id_attiv_controdeduz AS idAttivitaControdeduzione,\n" +
                            "attivitaControdeduz.desc_attiv_controdeduz AS descAttivitaControdeduzione,\n" +
                            "controdeduzione.dt_attiv_controdeduz AS dtAttivitaControdeduz, \n" +
                            "(CASE WHEN gestioneRevoca.dt_notifica IS NOT NULL THEN (gestioneRevoca.dt_notifica + gestioneRevoca.gg_risposta) ELSE (NULL) END) AS dataScadenzaControdeduzione,\n" +
                            "(CASE WHEN letteraAccompagnatoria.id_file_entita IS NOT NULL THEN 1 ELSE 0 END) AS letteraAccompagnatoria\n" +
                            "FROM PBANDI_T_CONTRODEDUZ controdeduzione\n" +
                            "LEFT JOIN PBANDI_D_STATO_CONTRODEDUZ statoControdeduz ON statoControdeduz.id_stato_controdeduz = controdeduzione.id_stato_controdeduz\n" +
                            "LEFT JOIN PBANDI_D_ATTIV_CONTRODEDUZ attivitaControdeduz ON attivitaControdeduz.id_attiv_controdeduz = controdeduzione.id_attiv_controdeduz\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = controdeduzione.id_entita AND entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                            "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON gestioneRevoca.id_gestione_revoca = controdeduzione.id_target\n" +
                            "JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = gestioneRevoca.id_causale_blocco\n" +
                            "LEFT JOIN PBANDI_T_FILE_ENTITA letteraAccompagnatoria ON \n" +
                            "\tletteraAccompagnatoria.id_entita = 609 AND\n" +
                            "\tletteraAccompagnatoria.id_target = controdeduzione.id_controdeduz AND\n" +
                            "\tletteraAccompagnatoria.flag_entita = 'I'\n" +
                            "WHERE gestioneRevoca.id_progetto = :idProgetto\n" +
                            "AND gestioneRevoca.dt_notifica IS NOT NULL";
            LOG.debug(sql);
            Object[] args = new Object[]{idProgetto, idProgetto};
            int[] types = new int[]{Types.INTEGER, Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                    (rs, rownum) -> {
                        ControdeduzioneVO dto = new ControdeduzioneVO();

                        dto.setIdGestioneRevoca(rs.getLong("idGestioneRevoca"));
                        dto.setNumeroRevoca(rs.getLong("numeroRevoca"));
                        dto.setDataNotifica(rs.getDate("dataNotifica"));
                        dto.setNumeroProtocollo(rs.getString("numeroProtocollo"));
                        dto.setCausaRevoca(rs.getString("causaRevoca"));

                        dto.setIdControdeduzione(rs.getLong("idControdeduzione"));
                        if (rs.wasNull()) {
                            dto.setIdControdeduzione(null);
                        }
                        dto.setNumeroControdeduzione(rs.getLong("numeroControdeduzione"));
                        if (rs.wasNull()) {
                            dto.setNumeroControdeduzione(null);
                        }
                        dto.setIdStatoControdeduzione(rs.getLong("idStatoControdeduzione"));
                        if (rs.wasNull()) {
                            dto.setIdStatoControdeduzione(null);
                        }
                        dto.setDescStatoControdeduzione(rs.getString("descStatoControdeduzione"));
                        dto.setDtStatoControdeduzione(rs.getDate("dtStatoControdeduz"));
                        dto.setIdAttivitaControdeduzione(rs.getLong("idAttivitaControdeduzione"));
                        if (rs.wasNull()) {
                            dto.setIdAttivitaControdeduzione(null);
                        }
                        dto.setDescAttivitaControdeduzione(rs.getString("descAttivitaControdeduzione"));
                        dto.setDtAttivitaControdeduzione(rs.getDate("dtAttivitaControdeduz"));
                        dto.setDtScadenzaControdeduzione(rs.getDate("dataScadenzaControdeduzione"));

                        //set stato proroga
                        if (dto.getIdControdeduzione() != null) {
                            getJdbcTemplate().query(
                                    "SELECT * FROM (\n" +
                                        "\tSELECT\n" +
                                        "\tstatoProroga.id_stato_proroga as idStatoProroga, \n" +
                                        "\tstatoProroga.desc_stato_proroga AS statoProroga\n" +
                                        "\tFROM PBANDI_T_PROROGA proroga\n" +
                                        "\tJOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                                        "\tWHERE proroga.ID_ENTITA = 609\n" +
                                        "\tAND proroga.id_target = ?\n" +
                                        "\tORDER BY proroga.DT_INSERIMENTO DESC \n" +
                                        ") WHERE rownum <= 1",
                                    resultSet -> {
                                        dto.setIdStatoProroga(resultSet.getLong("idStatoProroga"));
                                        dto.setDescStatoProroga(resultSet.getString("statoProroga"));
                                    },
                                    dto.getIdControdeduzione()
                            );
                        }

                        //set abilitazioni
                        long idStatoRevoca = rs.getLong("idStatoRevoca");
                        long idAttivitaRevoca = rs.getLong("idAttivitaRevoca");
                        dto.setAbilitatoControdeduz(dto.getIdControdeduzione() == null && idStatoRevoca == 6L && (
                                idAttivitaRevoca == 0L || idAttivitaRevoca == 8L || idAttivitaRevoca == 9L || idAttivitaRevoca == 10L || idAttivitaRevoca == 11L
                        ));
                        dto.setAbilitatoIntegra(dto.getIdStatoControdeduzione() != null && dto.getIdStatoControdeduzione() == 1L);
                        dto.setAbilitatoInvia(dto.getIdStatoControdeduzione() != null && dto.getIdStatoControdeduzione() == 1L && rs.getLong("letteraAccompagnatoria") == 1L);
                        dto.setAbilitatoElimina(dto.getIdStatoControdeduzione() != null && dto.getIdStatoControdeduzione() == 1L && dto.getIdAttivitaControdeduzione() == null);
                        dto.setAbilitatoAtti(
                                dto.getIdControdeduzione() != null &&
                                (dto.getIdAttivitaControdeduzione() == null || dto.getIdAttivitaControdeduzione() != 4L) &&
                                (
                                        (idStatoRevoca == 6 && (Arrays.asList(new Long[]{0L, 6L, 9L, 10L, 11L}).contains(idAttivitaRevoca))) ||
                                        (idStatoRevoca == 4 && idAttivitaRevoca == 0L) ||
                                        (idStatoRevoca == 3 && idAttivitaRevoca == 0L)
                                )
                        );

                        return dto;
                    }
            );

        } catch (Exception e) {
            LOG.info("Error while trying to getControdeduzioni " + e);
            dati = new ArrayList<>();
        } finally {
            LOG.info(prf + " END");
        }

        return dati;
    }

    @Override
    public Boolean insertControdeduz(Long idGestioneRevoca, HttpServletRequest req) {
        String prf = "[GestioneControdeduzioniDAOImpl::insertControdeduz]";
        LOG.info(prf + " BEGIN");

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
        int rowsUpdated;

        String query =
                "INSERT INTO PBANDI_T_CONTRODEDUZ (\n" +
                        "ID_CONTRODEDUZ,\n" +
                        "NUMERO_CONTRODEDUZ, \n" +
                        "ID_ENTITA,\n" +
                        "ID_TARGET,\n" +
                        "ID_STATO_CONTRODEDUZ,\n" +
                        "DT_STATO_CONTRODEDUZ,\n" +
                        "DT_INIZIO_VALIDITA,\n" +
                        "ID_UTENTE_INS,\n" +
                        "DT_INSERIMENTO\n" +
                        ") VALUES (\n" +
                        "SEQ_PBANDI_T_CONTRODEDUZ.nextval,\n" +
                        "SEQ_PBANDI_T_CONTRODEDUZ.nextval,\n" +
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

        try {
            rowsUpdated = getJdbcTemplate().update(query, args, types);
        } catch (Exception e) {
            LOG.info("Error while trying to insertControdeduz: ", e);
            rowsUpdated = 0;
        }

        LOG.info(prf + " END");

        return rowsUpdated > 0;
    }

    @Override
    public Boolean richiediAccessoAtti(Long idControdeduz, HttpServletRequest req) {
        String prf = "[GestioneControdeduzioniDAOImpl::richiediAccessoAtti]";
        LOG.info(prf + " BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        int rowsUpdated;

        String query =
                "UPDATE PBANDI_T_CONTRODEDUZ SET\n" +
                        "ID_ATTIV_CONTRODEDUZ = 4,\n" +
                        "DT_ATTIV_CONTRODEDUZ = CURRENT_DATE,\n" +
                        "ID_UTENTE_AGG = :idUtente,\n" +
                        "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                        "WHERE ID_CONTRODEDUZ = :idControdeduz";
        LOG.debug(query);

        String query2 =
                "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                        "ID_ATTIVITA_REVOCA = 14,\n" +
                        "DT_ATTIVITA = CURRENT_DATE,\n" +
                        "ID_UTENTE_AGG = :idUtente,\n" +
                        "DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE ID_GESTIONE_REVOCA IN (\n" +
                        "\tSELECT gestioneRevoca.id_gestione_revoca\n" +
                        "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "\tJOIN PBANDI_T_CONTRODEDUZ controdeduz ON controdeduz.id_target = gestioneRevoca.id_gestione_revoca AND controdeduz.id_entita = 516\n" +
                        "\tWHERE controdeduz.id_controdeduz = :idControdeduz\n" +
                        ")";
        LOG.debug(query2);

        Object[] args = new Object[]{userInfoSec.getIdUtente(), idControdeduz};
        int[] types = new int[]{Types.INTEGER, Types.INTEGER};

        try {
            rowsUpdated = getJdbcTemplate().update(query, args, types);
            if (rowsUpdated > 0) {
                rowsUpdated = getJdbcTemplate().update(query2, args, types);
            }
        } catch (Exception e) {
            LOG.info("Error while trying to richiediAccessoAtti: ", e);
            rowsUpdated = 0;
        }

        LOG.info(prf + " end");
        return rowsUpdated > 0;
    }

    @Override
    public Boolean inviaControdeduz(Long idControdeduz, HttpServletRequest req) {
        String prf = "[GestioneControdeduzioniDAOImpl::inviaControdeduz]";
        LOG.info(prf + " BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        int rowsUpdated;

        String query =
                "UPDATE PBANDI_T_CONTRODEDUZ SET\n" +
                        "ID_STATO_CONTRODEDUZ = 2,\n" +
                        "DT_STATO_CONTRODEDUZ = CURRENT_DATE,\n" +
                        "ID_UTENTE_AGG = :idUtente,\n" +
                        "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                        "WHERE ID_CONTRODEDUZ = :idControdeduz";
        LOG.debug(query);

        String query2 =
                "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                        "ID_ATTIVITA_REVOCA = 6,\n" +
                        "DT_ATTIVITA = CURRENT_DATE,\n" +
                        "ID_UTENTE_AGG = :idUtente,\n" +
                        "DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE ID_GESTIONE_REVOCA IN (\n" +
                        "\tSELECT gestioneRevoca.id_gestione_revoca\n" +
                        "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "\tJOIN PBANDI_T_CONTRODEDUZ controdeduz ON controdeduz.id_target = gestioneRevoca.id_gestione_revoca AND controdeduz.id_entita = 516\n" +
                        "\tWHERE controdeduz.id_controdeduz = :idControdeduz\n" +
                        ")";
        LOG.debug(query2);

        Object[] args = new Object[]{userInfoSec.getIdUtente(), idControdeduz};
        int[] types = new int[]{Types.INTEGER, Types.INTEGER};

        try {
            rowsUpdated = getJdbcTemplate().update(query, args, types);
            if (rowsUpdated > 0) {
                rowsUpdated = getJdbcTemplate().update(query2, args, types);
            }
        } catch (Exception e) {
            LOG.info("Error while trying to inviaControdeduz: ", e);
            rowsUpdated = 0;
        }

        LOG.info("N. record aggiornati:\n" + rowsUpdated);
        LOG.info(prf + " END");

        return rowsUpdated > 0;
    }

    @Override
    public Boolean deleteControdeduz(Long idControdeduz, HttpServletRequest req) {
        String prf = "[GestioneControdeduzioniDAOImpl::deleteControdeduz]";
        LOG.info(prf + " BEGIN");

        int rowsUpdated;

        String query =
                "DELETE FROM PBANDI_T_CONTRODEDUZ\n" +
                        "WHERE ID_CONTRODEDUZ = :idControdeduz";

        LOG.debug(query);

        Object[] args = new Object[]{idControdeduz};
        int[] types = new int[]{Types.INTEGER};

        try {
            rowsUpdated = getJdbcTemplate().update(query, args, types);
        } catch (Exception e) {
            LOG.info("Error while trying to deleteControdeduz: ", e);
            rowsUpdated = 0;
        }

        LOG.info("N. record aggiornati:\n" + rowsUpdated);
        LOG.info(prf + " end");

        return rowsUpdated > 0;
    }

    @Override
    public List<RichiestaProrogaVO> getRichiestaProroga(Long idControdeduz) {
        String prf = "[GestioneControdeduzioniDAOImpl::getRichiestaProroga]";
        LOG.info(prf + " BEGIN");

        LOG.info(prf + "idControdeduz = " + idControdeduz);

        List<RichiestaProrogaVO> dati;

        try {
            String sql =
                    "SELECT \n" +
                            "proroga.id_richiesta_proroga AS idRichiestaProroga,\n" +
                            "proroga.dt_richiesta AS dataRichiestaProroga,\n" +
                            "proroga.motivazione AS motivazioneProroga,\n" +
                            "proroga.num_giorni_rich AS numGiorniRichiesti,\n" +
                            "proroga.num_giorni_approv AS numGiorniApprovati,\n" +
                            "statoProroga.id_stato_proroga AS idStatoProroga,\n" +
                            "statoProroga.desc_stato_proroga AS descStatoProroga\n" +
                            "FROM PBANDI_T_PROROGA proroga\n" +
                            "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                            "WHERE proroga.id_entita = 609\n" +
                            "AND proroga.id_target = :idControdeduz";
            LOG.debug(sql);
            Object[] args = new Object[]{idControdeduz};
            int[] types = new int[]{Types.INTEGER};

            dati = getJdbcTemplate().query(sql, args, types,
                    (rs, rownum) -> {
                        RichiestaProrogaVO dto = new RichiestaProrogaVO();

                        dto.setIdRichiestaProroga(rs.getLong("idRichiestaProroga"));
                        dto.setDataRichiestaProroga(rs.getDate("dataRichiestaProroga"));
                        dto.setMotivazioneProroga(rs.getString("motivazioneProroga"));
                        dto.setNumGiorniRichiesti(rs.getLong("numGiorniRichiesti"));
                        dto.setNumGiorniApprovati(rs.getLong("numGiorniApprovati"));
                        dto.setIdStatoProroga(rs.getLong("idStatoProroga"));
                        dto.setDescStatoProroga(rs.getString("descStatoProroga"));

                        return dto;
                    });

        } catch (Exception e) {
            LOG.info("Error while trying to getRichiestaProroga: " + e);
            dati = new ArrayList<>();
        }

        LOG.info(prf + " END");

        return dati;
    }

    @Override
    public Boolean richiediProroga(Long idControdeduz, RichiestaProrogaVO richiestaProrogaVO, HttpServletRequest req) {
        String prf = "[GestioneControdeduzioniDAOImpl::richiediProroga]";
        LOG.info(prf + " BEGIN");

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        boolean esito = true;

        String query =
                "INSERT INTO PBANDI_T_PROROGA (\n" +
                        "ID_RICHIESTA_PROROGA,\n" +
                        "ID_ENTITA,\n" +
                        "ID_TARGET,\n" +
                        "NUM_GIORNI_RICH,\n" +
                        "MOTIVAZIONE,\n" +
                        "ID_STATO_PROROGA,\n" +
                        "ID_UTENTE_INS,\n" +
                        "DT_RICHIESTA,\n" +
                        "DT_INSERIMENTO\n" +
                        ") VALUES (\n" +
                        "SEQ_PBANDI_T_PROROGA.nextval,\n" +
                        "609,\n" +
                        ":idControdeduz,\n" +
                        ":giorniRichiesti,\n" +
                        ":motivazione,\n" +
                        "1,\n" +
                        ":idUtente,\n" +
                        "CURRENT_DATE,\n" +
                        "CURRENT_DATE\n" +
                        ")";
        LOG.debug(query);

        Object[] args = new Object[]{idControdeduz, richiestaProrogaVO.getNumGiorniRichiesti(), richiestaProrogaVO.getMotivazioneProroga(), userInfoSec.getIdUtente()};
        int[] types = new int[]{Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER};

        String query2 =
                "UPDATE PBANDI_T_CONTRODEDUZ SET\n" +
                        "ID_ATTIV_CONTRODEDUZ = 1,\n" +
                        "DT_ATTIV_CONTRODEDUZ = CURRENT_DATE,\n" +
                        "ID_UTENTE_AGG = :idUtente,\n" +
                        "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                        "WHERE ID_CONTRODEDUZ = :idControdeduz";

        String query3 =
                "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                        "ID_ATTIVITA_REVOCA = 8,\n" +
                        "DT_ATTIVITA = CURRENT_DATE,\n" +
                        "ID_UTENTE_AGG = :idUtente,\n" +
                        "DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE ID_GESTIONE_REVOCA IN (\n" +
                        "\tSELECT gestioneRevoca.id_gestione_revoca\n" +
                        "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "\tJOIN PBANDI_T_CONTRODEDUZ controdeduz ON controdeduz.id_target = gestioneRevoca.id_gestione_revoca AND controdeduz.id_entita = 516\n" +
                        "\tWHERE controdeduz.id_controdeduz = :idControdeduz\n" +
                        ")";
        Object[] args2 = new Object[]{userInfoSec.getIdUtente(), idControdeduz};
        int[] types2 = new int[]{Types.INTEGER, Types.INTEGER};

        try {
            //Inserimento richiesta di proroga
            getJdbcTemplate().update(query, args, types);
            //Aggiornamento controdeduzione
            getJdbcTemplate().update(query2, args2, types2);
            //Aggiornamento gestione revoca
            getJdbcTemplate().update(query3, args2, types2);
        } catch (Exception e) {
            LOG.info("Error while trying to richiediProroga: ", e);
            esito = false;
        }

        LOG.info(prf + " END");
        return esito;
    }

}
