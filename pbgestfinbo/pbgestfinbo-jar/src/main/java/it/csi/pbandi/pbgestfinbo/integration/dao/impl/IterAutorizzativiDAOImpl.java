/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.csi.wrapper.CSIException;
import it.csi.pbandi.ammvoservrest.dto.StatoDistinte;
import it.csi.pbandi.pbgestfinbo.business.manager.ActaManager;
import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.business.service.AmministrativoContabileService;
import it.csi.pbandi.pbgestfinbo.dto.DatiXActaDTO;
import it.csi.pbandi.pbgestfinbo.dto.RicercaControlliDTO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.DistinteDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.DatiXActaRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.DocumentoIterVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion.BandoLineaInterventVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion.DenominazioneSuggestVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion.ProgettoSuggestVOplusRowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.BandoLineaInterventVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.DenominazioneSuggestVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.ProgettoSuggestVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.pbandisrv.dto.rettifica.EsitoReportDettaglioDocumentiDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.validazionerendicontazione.ValidazioneRendicontazioneException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter.ExcelDataWriter;
import it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter.TableWriter;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IterAutorizzativiDAOImpl extends JdbcDaoSupport implements IterAutorizzativiDAO {
    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    AmministrativoContabileService amministrativoContabileService;

    @Autowired
    private GenericDAO genericDAO;
    @Autowired
    DistinteDAO distinteDao;
    @Autowired
    DocumentoManager documentoManager;
    @Autowired
    ActaManager actaManager;

    private static String QUERY_ESITO_CHIAMATA_SERV = "SELECT monServAmmvoContab.esito\n" +
            "FROM PBANDI_T_MON_SERV monServAmmvoContab\n" +
            "WHERE monServAmmvoContab.id_monit = ?";

    @Autowired
    public IterAutorizzativiDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    //GET ITER
    @Override
    public List<IterAutorizzativiMiniVO> getIterAutorizzativi(RicercaControlliDTO searchVO, HttpServletRequest req) {
        String prf = "[IterAutorizzativiDAOImpl :: getIterAutorizzativi]";
        logger.info(prf + "BEGIN");
        List<IterAutorizzativiMiniVO> response = new ArrayList<>();
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        try {
            StringBuilder sql = new StringBuilder();
            StringBuilder sql2 = new StringBuilder();

            sql.append(
                    "WITH denom AS (\n" +
                            "    SELECT\n" +
                            "    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione_fis,\n" +
                            "    PERSFIS.ID_SOGGETTO,\n" +
                            "    persfis.ID_PERSONA_FISICA\n" +
                            "    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
                            "),\n" +
                            "denom2 AS (\n" +
                            "    SELECT\n" +
                            "    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione_ente,\n" +
                            "    ENTEGIU.ID_SOGGETTO,\n" +
                            "    entegiu.ID_ENTE_GIURIDICO\n" +
                            "    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
                            "),\n" +
                            "lastStep AS (\n" +
                            "\tSELECT MAX(ptdwf.ID_DETT_WORK_FLOW) AS ID_DETT_WORK_FLOW\n" +
                            "\tFROM PBANDI_T_DETT_WORK_FLOW ptdwf \n" +
                            "\tGROUP BY ptdwf.ID_WORK_FLOW \n" +
                            ")" +
                            "SELECT DISTINCT\n" +
                            "ptdwf.ID_WORK_FLOW,\n" +
                            "ptwfe.ID_TARGET,\n" +
                            "ptdwf.ID_STATO_ITER,\n" +
                            "pdsi.DESC_STATO_ITER,\n" +
                            "pdti.ID_TIPO_ITER,\n" +
                            "pdti.DESC_TIPO_ITER,\n" +
                            "NULL AS ID_PROGETTO,\n" +
                            "NULL AS CODICE_VISUALIZZATO,\n" +
                            "prbli.PROGR_BANDO_LINEA_INTERVENTO,\n" +
                            "prbli.NOME_BANDO_LINEA,\n" +
                            "NULL AS ID_SOGGETTO,\n" +
                            "NULL AS denominazione_fis,\n" +
                            "NULL AS ID_PERSONA_FISICA,\n" +
                            "NULL AS denominazione_ente,\n" +
                            "NULL AS ID_ENTE_GIURIDICO\n" +
                            "FROM PBANDI_T_DETT_WORK_FLOW ptdwf\n" +
                            "JOIN lastStep ON lastStep.id_dett_work_flow = ptdwf.id_dett_work_flow\n" +
                            "JOIN PBANDI_D_STATO_ITER pdsi ON \n" +
                            "\t(ptdwf.DT_ANNULLAMENTO IS NULL AND pdsi.ID_STATO_ITER = ptdwf.ID_STATO_ITER) OR \n" +
                            "\t(ptdwf.DT_ANNULLAMENTO IS NOT NULL AND pdsi.DESC_BREVE_STATO_ITER = 'RESPINTO')\n" +
                            "JOIN PBANDI_T_WORK_FLOW ptwf ON ptwf.ID_WORK_FLOW = ptdwf.ID_WORK_FLOW\n" +
                            "JOIN PBANDI_T_WORK_FLOW_ENTITA ptwfe ON ptwfe.ID_WORK_FLOW = ptwf.ID_WORK_FLOW\n" +
                            "JOIN PBANDI_D_TIPO_ITER pdti ON ptwf.ID_TIPO_ITER = pdti.ID_TIPO_ITER\n" +
                            "LEFT JOIN PBANDI_R_TIPO_STATO_ITER prtsi ON \n" +
                            "\tprtsi.id_tipo_iter = ptwf.id_tipo_iter AND \n" +
                            "\tprtsi.id_stato_iter = ptdwf.id_stato_iter\n" +
                            "LEFT JOIN PBANDI_R_INCARICO_SOGGETTO pris ON pris.id_incarico = prtsi.id_incarico\n" +
                            "LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.id_soggetto = pris.id_soggetto\n" +
                            "JOIN PBANDI_C_ENTITA pce ON pce.id_entita = ptwfe.id_entita\n" +
                            "JOIN PBANDI_T_DISTINTA ptd ON ptd.id_distinta = ptwfe.id_target\n" +
                            "JOIN PBANDI_C_ENTITA pce2 ON pce2.id_entita = ptd.id_entita\n" +
                            "JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.id_distinta = ptd.id_distinta\n" +
                            "JOIN PBANDI_T_PROPOSTA_EROGAZIONE ptpe ON ptpe.id_proposta = ptdd.id_target\n" +
                            "LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptpe.ID_PROGETTO\n" +
                            "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
                            "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
                            "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO\n" +
                            "WHERE pce.nome_entita = 'PBANDI_T_DISTINTA'\n" +
                            "AND pce2.nome_entita = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                            "AND prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                            "AND prsp.ID_TIPO_BENEFICIARIO <> 4\n"
            );

            sql2.append(
                    "SELECT DISTINCT\n" +
                            "ptdwf.ID_WORK_FLOW,\n" +
                            "ptwfe.ID_TARGET,\n" +
                            "ptdwf.ID_STATO_ITER,\n" +
                            "pdsi.DESC_STATO_ITER,\n" +
                            "pdti.ID_TIPO_ITER,\n" +
                            "pdti.DESC_TIPO_ITER,\n" +
                            "ptwfe.ID_PROGETTO,\n" +
                            "ptp.CODICE_VISUALIZZATO,\n" +
                            "prbli.PROGR_BANDO_LINEA_INTERVENTO,\n" +
                            "prbli.NOME_BANDO_LINEA,\n" +
                            "prsp.ID_SOGGETTO,\n" +
                            "denom.denominazione_fis,\n" +
                            "denom.ID_PERSONA_FISICA,\n" +
                            "denom2.denominazione_ente,\n" +
                            "denom2.ID_ENTE_GIURIDICO\n" +
                            "FROM PBANDI_T_DETT_WORK_FLOW ptdwf\n" +
                            "JOIN lastStep ON lastStep.id_dett_work_flow = ptdwf.id_dett_work_flow\n" +
                            "JOIN PBANDI_D_STATO_ITER pdsi ON \n" +
                            "\t(ptdwf.DT_ANNULLAMENTO IS NULL AND pdsi.ID_STATO_ITER = ptdwf.ID_STATO_ITER) OR \n" +
                            "\t(ptdwf.DT_ANNULLAMENTO IS NOT NULL AND pdsi.DESC_BREVE_STATO_ITER = 'RESPINTO')\n" +
                            "JOIN PBANDI_T_WORK_FLOW ptwf ON ptwf.ID_WORK_FLOW = ptdwf.ID_WORK_FLOW\n" +
                            "JOIN PBANDI_T_WORK_FLOW_ENTITA ptwfe ON ptwfe.ID_WORK_FLOW = ptwf.ID_WORK_FLOW\n" +
                            "JOIN PBANDI_D_TIPO_ITER pdti ON ptwf.ID_TIPO_ITER = pdti.ID_TIPO_ITER\n" +
                            "LEFT JOIN PBANDI_R_TIPO_STATO_ITER prtsi ON \n" +
                            "\tprtsi.id_tipo_iter = ptwf.id_tipo_iter AND \n" +
                            "\tprtsi.id_stato_iter = ptdwf.id_stato_iter\n" +
                            "LEFT JOIN PBANDI_R_INCARICO_SOGGETTO pris ON pris.id_incarico = prtsi.id_incarico\n" +
                            "LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.id_soggetto = pris.id_soggetto\n" +
                            "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptwfe.ID_PROGETTO\n" +
                            "JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
                            "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
                            "JOIN PBANDI_C_ENTITA pce ON ptwfe.ID_ENTITA = pce.ID_ENTITA\n" +
                            "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\n" +
                            "LEFT JOIN denom ON prsp.ID_SOGGETTO = denom.id_soggetto AND denom.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\n" +
                            "LEFT JOIN denom2 ON prsp.ID_SOGGETTO = denom2.id_soggetto AND denom2.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\n" +
                            "WHERE ((prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                            "AND prsp.ID_TIPO_BENEFICIARIO <> 4) OR prsp.ID_SOGGETTO IS NULL)\n"
            );

            boolean filtro = false;
            if (searchVO.getIdProgetto() != null) {
                sql.append("AND ptp.ID_PROGETTO = ?\n");
                sql2.append("AND ptp.ID_PROGETTO = ?\n");
                filtro = true;
            }
            if (searchVO.getIdSoggetto() != null) {
                sql.append("AND prsp.ID_SOGGETTO = ?\n");
                sql2.append("AND prsp.ID_SOGGETTO = ?\n");
                filtro = true;
            }
            if (searchVO.getProgrBandoLinea() != null) {
                sql.append("AND prbli.PROGR_BANDO_LINEA_INTERVENTO = ?\n");
                sql2.append("AND prbli.PROGR_BANDO_LINEA_INTERVENTO = ?\n");
                filtro = true;
            }
            if (searchVO.getIdStatoIter() != 0) {
                if (searchVO.getIdStatoIter() == 6) {
                    sql.append("AND ptdwf.DT_ANNULLAMENTO IS NOT NULL\n");
                    sql2.append("AND ptdwf.DT_ANNULLAMENTO IS NOT NULL\n");
                } else {
                    sql.append("AND ptdwf.ID_STATO_ITER = ?\n" +
                            "AND ptdwf.DT_ANNULLAMENTO IS NULL\n");
                    sql2.append("AND ptdwf.ID_STATO_ITER = ?\n" +
                            "AND ptdwf.DT_ANNULLAMENTO IS NULL\n");
                }
                filtro = true;
            }
            if (searchVO.getDataInizio() != null) {
                sql.append("AND ptwf.dt_inserimento >= ?\n");
                sql2.append("AND ptwf.dt_inserimento >= ?\n");
                filtro = true;
            }
            if (searchVO.getDataFine() != null) {
                sql.append("AND ptwf.dt_inserimento-1 <= ?\n");
                sql2.append("AND ptwf.dt_inserimento-1 <= ?\n");
                filtro = true;
            }
            if (!filtro) {
                sql.append("AND ptu.id_utente = ?\n" +
                        "AND ptdwf.DT_APPROVAZIONE IS NULL\n" +
                        "AND ptdwf.DT_ANNULLAMENTO IS NULL\n");
                sql2.append("AND ptu.id_utente = ?\n" +
                        "AND ptdwf.DT_APPROVAZIONE IS NULL\n" +
                        "AND ptdwf.DT_ANNULLAMENTO IS NULL\n");
            }
            sql.append("UNION\n");
            sql.append(sql2);
            sql.append("ORDER BY DESC_TIPO_ITER\n");

            boolean finalFiltro = filtro;
            response = getJdbcTemplate().query(
                    sql.toString(),
                    ps -> {
                        int k = 1;
                        for (int i = 0; i < 2; i++) {
                            if (searchVO.getIdProgetto() != null) {
                                ps.setBigDecimal(k++, searchVO.getIdProgetto());
                            }
                            if (searchVO.getIdSoggetto() != null) {
                                ps.setBigDecimal(k++, searchVO.getIdSoggetto());
                            }
                            if (searchVO.getProgrBandoLinea() != null) {
                                ps.setBigDecimal(k++, searchVO.getProgrBandoLinea());
                            }
                            if (searchVO.getIdStatoIter() != 0 && searchVO.getIdStatoIter() != 6) {
                                ps.setInt(k++, searchVO.getIdStatoIter());
                            }
                            if (searchVO.getDataInizio() != null) {
                                ps.setDate(k++, new Date(searchVO.getDataInizio().getTime()));
                            }
                            if (searchVO.getDataFine() != null) {
                                ps.setDate(k++, new Date(searchVO.getDataFine().getTime()));
                            }
                            if (!finalFiltro) {
                                ps.setLong(k++, userInfoSec.getIdUtente());
                            }
                        }
                    },
                    (rs, rowNum) -> {
                        IterAutorizzativiMiniVO iter = new IterAutorizzativiMiniVO();
                        iter.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));
                        iter.setDescrizioneBando(rs.getString("NOME_BANDO_LINEA"));
                        iter.setDescrizioneIter(rs.getString("DESC_TIPO_ITER"));
                        iter.setDescrizioneStato(rs.getString("DESC_STATO_ITER"));
                        iter.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
                        iter.setDescrizioneProgetto(rs.getString("CODICE_VISUALIZZATO"));
                        iter.setIdBando(rs.getBigDecimal("PROGR_BANDO_LINEA_INTERVENTO"));
                        iter.setIdWorkFlow(rs.getBigDecimal("ID_WORK_FLOW"));
                        iter.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
                        iter.setIdStatoIter(rs.getInt("ID_STATO_ITER"));
                        iter.setIdTipoIter(rs.getInt("ID_TIPO_ITER"));
                        String denom = rs.getString("denominazione_ente");
                        if (denom != null) {
                            iter.setIdBeneficiario(rs.getBigDecimal("ID_ENTE_GIURIDICO"));
                            iter.setBeneficiario(denom);
                            iter.setPersonaGiuridica(true);
                        } else {
                            iter.setBeneficiario(rs.getString("denominazione_fis"));
                            iter.setIdBeneficiario(rs.getBigDecimal("ID_PERSONA_FISICA"));
                            iter.setPersonaGiuridica(false);
                        }
                        iter.setIdTarget(rs.getBigDecimal("ID_TARGET"));
                        //converto idTarget per revoche
                        try {
                            if (iter.getIdTipoIter() == 9 || iter.getIdTipoIter() == 10 || iter.getIdTipoIter() == 11 || iter.getIdTipoIter() == 12 || iter.getIdTipoIter() == 17) {
                                iter.setIdTarget(getJdbcTemplate().queryForObject(
                                        "SELECT DISTINCT numero_revoca\n" +
                                                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                                                "WHERE gestioneRevoca.id_gestione_revoca = ?",
                                        new Object[]{iter.getIdTarget()},
                                        BigDecimal.class
                                ));
                            }
                        } catch (Exception e) {
                            logger.error("Un'iter autorizzativo per revoche non corrisponde ad alcuna revoca: id_gestione_revoca = " + iter.getIdTarget());
                        }
                        //converto idTarget per DS
                        try {
                            if (iter.getIdTipoIter() == 1) {
                                iter.setIdTarget(getJdbcTemplate().queryForObject(
                                        "SELECT DISTINCT ID_DICHIARAZIONE_SPESA  \n" +
                                                "FROM PBANDI_T_INTEGRAZIONE_SPESA\n" +
                                                "WHERE ID_INTEGRAZIONE_SPESA = ?",
                                        new Object[]{iter.getIdTarget()},
                                        BigDecimal.class
                                ));
                            }
                        } catch (Exception e) {
                            logger.error("Un'iter autorizzativo di 'Rendicontazione - Richiesta integrazione' non corrisponde ad alcuna DS: id_integrazione = " + iter.getIdTarget());
                        }//converto idTarget per DS
                        try {
                            if (iter.getIdTipoIter() == 2) {
                                iter.setIdTarget(getJdbcTemplate().queryForObject(
                                        "SELECT DISTINCT ID_DICHIARAZIONE_SPESA \n" +
                                                "FROM PBANDI_T_CHECKLIST\n" +
                                                "WHERE ID_CHECKLIST = ?",
                                        new Object[]{iter.getIdTarget()},
                                        BigDecimal.class
                                ));
                            }
                        } catch (Exception e) {
                            logger.error("Un'iter autorizzativo di 'Rendicontazione - Chiusura validazione' non corrisponde ad alcuna DS: id_checklist = " + iter.getIdTarget());
                        }

                        iter.setAbilitaGestioneIter(checkPermessiIter(iter.getIdWorkFlow().longValue(), req));

                        return iter;
                    });


        } catch (Exception e2) {
            logger.error(e2);
        }
        return response;
    }

    //GET DETTAGLIO ITER
    @Override
    public List<DettaglioIterAutorizzativiVO> getDettaglioIter(BigDecimal idWorkflow) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<DettaglioIterAutorizzativiVO> list = new ArrayList<>();
        try {

            String sql =
                    "SELECT\n" +
                            "ptdwf.ID_WORK_FLOW,\n" +
                            "ptdwf.ID_DETT_WORK_FLOW,\n" +
                            "ptpf.NOME,\n" +
                            "ptpf.COGNOME,\n" +
                            "ptdwf.DT_APPROVAZIONE,\n" +
                            "ptdwf.DT_ANNULLAMENTO,\n" +
                            "ptdwf.MOTIVAZIONE,\n" +
                            "pdi.DESC_INCARICO\n" +
                            "FROM PBANDI_T_DETT_WORK_FLOW ptdwf\n" +
                            "JOIN PBANDI_T_WORK_FLOW ptwf ON ptwf.ID_WORK_FLOW = ptdwf.ID_WORK_FLOW\n" +
                            "JOIN PBANDI_R_TIPO_STATO_ITER prtsi ON prtsi.ID_STATO_ITER = ptdwf.ID_STATO_ITER AND ptwf.ID_TIPO_ITER = prtsi.ID_TIPO_ITER\n" +
                            "JOIN PBANDI_D_INCARICO pdi ON pdi.ID_INCARICO = prtsi.ID_INCARICO\n" +
                            "LEFT JOIN PBANDI_T_UTENTE ptu ON ptdwf.ID_UTENTE_AGG = ptu.ID_UTENTE\n" +
                            "LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = ptu.ID_SOGGETTO\n" +
                            "LEFT JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_SOGGETTO = pts.ID_SOGGETTO\n" +
                            "WHERE (ptu.ID_UTENTE IS NULL OR PTPF.ID_PERSONA_FISICA IN (\n" +
                            "    SELECT max(ptpf.ID_PERSONA_FISICA)\n" +
                            "    FROM PBANDI_T_DETT_WORK_FLOW ptdwf\n" +
                            "    JOIN PBANDI_T_UTENTE ptu ON ptdwf.ID_UTENTE_AGG = ptu.ID_UTENTE\n" +
                            "    JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = ptu.ID_SOGGETTO\n" +
                            "    JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_SOGGETTO = pts.ID_SOGGETTO\n" +
                            "    GROUP BY pts.ID_SOGGETTO \n" +
                            "))\n" +
                            "AND ptdwf.ID_WORK_FLOW = ?\n" +
                            "ORDER BY prtsi.ORDINE";

            logger.info(sql);

            list = getJdbcTemplate().query(sql,
                    (rs, rowNum) -> {
                        DettaglioIterAutorizzativiVO det = new DettaglioIterAutorizzativiVO();
                        det.setCognomeUtente(rs.getString("COGNOME"));
                        det.setNomeUtente(rs.getString("NOME"));
                        det.setDataApprovazione(rs.getDate("DT_APPROVAZIONE"));
                        det.setDataRespingimeneto(rs.getDate("DT_ANNULLAMENTO"));
                        det.setDescIncarico(rs.getString("DESC_INCARICO"));
                        det.setMotivazione(rs.getString("MOTIVAZIONE"));
                        det.setIdDettWorkflow(rs.getBigDecimal("ID_DETT_WORK_FLOW"));
                        det.setIdWorkFlow(rs.getBigDecimal("ID_WORK_FLOW"));
                        return det;
                    }, idWorkflow);


        } catch (Exception e) {
            logger.error(prf + " errore durante la lettura della tabella " + e);
        }

        LOG.info(prf + "END");
        return list;
    }

    //GET ALLEGATI ITER
    @Override
    public List<DocumentoIterVO> getAllegati(BigDecimal idWorkflow) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<DocumentoIterVO> data = new ArrayList<>();

        Integer idTipoDocumentoIndexLettAcc = null;
        List<Integer> idTipoDocumentoIndexAltriAllegati = new ArrayList<>();

        DocumentoIterVO documentoIterVO;
        String query;
        //in base all'id tipo iter devo gestire dei documenti piuttosto che degli altri
        switch (getJdbcTemplate().queryForObject(
                "SELECT workFlow.id_tipo_iter FROM PBANDI_T_WORK_FLOW workFlow WHERE workFlow.id_work_flow = ?",
                new Object[]{idWorkflow},
                Integer.class
        )) {
            case 1:
                idTipoDocumentoIndexLettAcc = 35;

                query =
                        "SELECT\n" +
                                "documentoIndex.id_documento_index,\n" +
                                "documentoIndex.nome_file,\n" +
                                "documentoIndex.id_tipo_documento_index\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_entita = workFlowEntita.id_entita AND documentoIndex.id_target = workFlowEntita.id_target\n" +
                                "WHERE workFlowEntita.id_work_flow = ?\n" +
                                "AND documentoIndex.id_tipo_documento_index in (33, 63)";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                //CHECKLIST E REPORT DI VALIDAZIONE INTERNA
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }

				/* DOC B SU CDU
				//REPORT EXCEL
				try {
					documentoIterVO = new DocumentoIterVO();
					documentoIterVO.setNomeFile("dichiarazione_spesa.xls");
					documentoIterVO.setFlagExcel(true);
					documentoIterVO.setTipoExcel(1);
					documentoIterVO.setIdTargetExcel(getJdbcTemplate().queryForObject(
							"SELECT\n" +
								"workFlowEntita.id_target\n" +
								"FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
								"JOIN PBANDI_C_ENTITA entita ON workFlowEntita.id_entita = entita.id_entita\n" +
								"WHERE workFlowEntita.id_work_flow = ?\n" +
								"AND entita.nome_entita = ?",
							new Object[]{idWorkflow, "PBANDI_T_DICHIARAZIONE_SPESA"},
							Long.class));
					data.add(documentoIterVO);
				}catch (IncorrectResultSizeDataAccessException ignored){
				}
				 */
                break;
            case 2:
            case 18:
                idTipoDocumentoIndexLettAcc = 60;

                query =
                        "SELECT\n" +
                                "documentoIndex.id_documento_index,\n" +
                                "documentoIndex.nome_file,\n" +
                                "documentoIndex.id_tipo_documento_index\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_entita = workFlowEntita.id_entita AND documentoIndex.id_target = workFlowEntita.id_target\n" +
                                "WHERE workFlowEntita.id_work_flow = ?\n" +
                                "AND documentoIndex.id_tipo_documento_index in (33, 63)";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                //CHECKLIST DI VALIDAZIONE INTERNA && REPORT DI VALIDAZIONE
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }

				/*
				query =
						"SELECT\n" +
						"documentoIndex.id_documento_index,\n" +
						"documentoIndex.nome_file,\n" +
						"documentoIndex.id_tipo_documento_index\n" +
						"FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
						"JOIN PBANDI_C_ENTITA entitaDich ON entitaDich.id_entita = workFlowEntita.id_entita\n" +
						"JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_entita = workFlowEntita.id_entita AND documentoIndex.id_target = workFlowEntita.id_target\n" +
						"WHERE entitaDich.nome_entita = 'PBANDI_T_DICHIARAZIONE_SPESA'\n" +
						"AND workFlowEntita.id_work_flow = ?\n" +
						"AND documentoIndex.id_tipo_documento_index = ?";
				LOG.info(prf + "Query: \n\n" + query + "\n");
				try {
					data.addAll(getJdbcTemplate().query(query, new Object[]{idWorkflow, 24}, new DocumentoIterVORowMapper()));
				} catch (EmptyResultDataAccessException ignored) {}
				 */
                break;
            case 5:
                idTipoDocumentoIndexLettAcc = 62;
                break;
            case 6:
                idTipoDocumentoIndexLettAcc = 56;
                break;
            case 7:
                idTipoDocumentoIndexLettAcc = 57;
                idTipoDocumentoIndexAltriAllegati.add(7);
                idTipoDocumentoIndexAltriAllegati.add(58);
                break;
            case 8:
                query =
                        "SELECT UNIQUE\n" +
                                "documenti.id_documento_index,\n" +
                                "documenti.nome_file,\n" +
                                "documenti.id_tipo_documento_index\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe \n" +
                                "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptwfe.ID_ENTITA \n" +
                                "JOIN PBANDI_T_RICHIESTA_INTEGRAZ ptri ON ptri.ID_RICHIESTA_INTEGRAZ = ptwfe.ID_TARGET \n" +
                                "JOIN PBANDI_C_ENTITA pce2 ON pce2.ID_ENTITA = ptri.ID_ENTITA \n" +
                                "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = 516 AND documenti.id_target = ptri.ID_TARGET \n" +
                                "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index = ?\n" +
                                "WHERE ptwfe.ID_WORK_FLOW = ?";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                //Lettera Accompagnatoria
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{45, idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }
                //Altri Allegati
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{51, idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }
                idTipoDocumentoIndexLettAcc = 45;
                idTipoDocumentoIndexAltriAllegati.add(51);
                break;
            case 9:
                //Checklist excel da DS idTipoDocumentoIndex = 33
                //Report validazione idTipoDocumentoIndex = 63
                query =
                        "SELECT UNIQUE\n" +
                                "documenti.id_documento_index,\n" +
                                "documenti.nome_file,\n" +
                                "documenti.id_tipo_documento_index\n" +
                                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                                "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevocaValida ON gestioneRevocaValida.numero_revoca = gestioneRevoca.numero_revoca AND gestioneRevocaValida.id_tipologia_revoca = 2 AND gestioneRevocaValida.dt_fine_validita IS NULL\n" +
                                "JOIN PBANDI_T_CHECKLIST checklist ON checklist.id_dichiarazione_spesa = gestioneRevocaValida.id_dichiarazione_spesa\n" +
                                "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_target = checklist.id_checklist\n" +
                                "JOIN PBANDI_C_ENTITA pceChecklist ON pceChecklist.id_entita = documenti.id_entita\n" +
                                "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index IN (33, 63)\n" +
                                "WHERE gestioneRevoca.numero_revoca = (\n" +
                                "\tSELECT ptgr.NUMERO_REVOCA \n" +
                                "\tFROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "\tJOIN PBANDI_C_ENTITA pce ON pce.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                                "\tJOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptgr.ID_GESTIONE_REVOCA = workFlowEntita.ID_TARGET \n" +
                                "\tWHERE workFlowEntita.id_work_flow = ?\n" +
                                "\tAND workFlowEntita.ID_ENTITA = pce.ID_ENTITA \n" +
                                ")\n" +
                                "AND gestioneRevoca.id_tipologia_revoca in (1, 2) \n" +
                                "AND pceChecklist.NOME_ENTITA = 'PBANDI_T_CHECKLIST'\n";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {}
                query =
                        "SELECT UNIQUE\n" +
                                "documenti.id_documento_index,\n" +
                                "documenti.nome_file,\n" +
                                "documenti.id_tipo_documento_index\n" +
                                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                                "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevocaValida ON gestioneRevocaValida.numero_revoca = gestioneRevoca.numero_revoca AND gestioneRevocaValida.id_tipologia_revoca = 2 AND gestioneRevocaValida.dt_fine_validita IS NULL\n" +
                                "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = 516 AND documenti.id_target = gestioneRevoca.id_gestione_revoca\n" +
                                "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index = ?\n" +
                                "WHERE gestioneRevoca.numero_revoca = (\n" +
                                "\tSELECT ptgr.NUMERO_REVOCA \n" +
                                "\tFROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "\tJOIN PBANDI_C_ENTITA pce ON pce.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                                "\tJOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptgr.ID_GESTIONE_REVOCA = workFlowEntita.ID_TARGET \n" +
                                "\tWHERE workFlowEntita.id_work_flow = ?\n" +
                                "\tAND workFlowEntita.ID_ENTITA = pce.ID_ENTITA \n" +
                                ")\n" +
                                "AND gestioneRevoca.id_tipologia_revoca in (1, 2) \n";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                //Lettera Accompagnatoria
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{44, idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }
                //Altri Allegati
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{50, idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }
                //Verbale di controlli in loco
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{7, idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }
                //Checklist di controlli in loco
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{58, idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }
                break;
            case 10:
                idTipoDocumentoIndexLettAcc = 46;
                idTipoDocumentoIndexAltriAllegati.add(52);
                break;
            case 11:
                //Checklist excel da DS idTipoDocumentoIndex = 33
                //Report validazione idTipoDocumentoIndex = 63
                query =
                        "SELECT UNIQUE\n" +
                                "documenti.id_documento_index,\n" +
                                "documenti.nome_file,\n" +
                                "documenti.id_tipo_documento_index\n" +
                                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                                "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevocaValida ON gestioneRevocaValida.numero_revoca = gestioneRevoca.numero_revoca AND gestioneRevocaValida.id_tipologia_revoca = 2 AND gestioneRevocaValida.dt_fine_validita IS NULL\n" +
                                "JOIN PBANDI_T_CHECKLIST checklist ON checklist.id_dichiarazione_spesa = gestioneRevocaValida.id_dichiarazione_spesa\n" +
                                "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_target = checklist.id_checklist\n" +
                                "JOIN PBANDI_C_ENTITA pceChecklist ON pceChecklist.id_entita = documenti.id_entita\n" +
                                "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index IN (33, 63)\n" +
                                "WHERE gestioneRevoca.numero_revoca = (\n" +
                                "\tSELECT ptgr.NUMERO_REVOCA \n" +
                                "\tFROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "\tJOIN PBANDI_C_ENTITA pce ON pce.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                                "\tJOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptgr.ID_GESTIONE_REVOCA = workFlowEntita.ID_TARGET \n" +
                                "\tWHERE workFlowEntita.id_work_flow = ?\n" +
                                "\tAND workFlowEntita.ID_ENTITA = pce.ID_ENTITA \n" +
                                ")\n" +
                                "AND gestioneRevoca.id_tipologia_revoca in (1, 2, 3) \n" +
                                "AND pceChecklist.NOME_ENTITA = 'PBANDI_T_CHECKLIST'\n";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {}
                idTipoDocumentoIndexLettAcc = 47;
                idTipoDocumentoIndexAltriAllegati.add(53);
                break;
            case 12:
                idTipoDocumentoIndexLettAcc = 48;
                idTipoDocumentoIndexAltriAllegati.add(54);
                break;
            case 13:
                //idTipoDocumentoIndexLettAcc = 42;
                //REPORT VALIDAZIONE E CHECKLIST INTERNA
                query =
                        "SELECT\n" +
                                "documentoIndex.id_documento_index,\n" +
                                "documentoIndex.nome_file,\n" +
                                "documentoIndex.id_tipo_documento_index,\n" +
                                "progetto.id_progetto,\n" +
                                "progetto.codice_visualizzato\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_entita = workFlowEntita.id_entita AND documentoIndex.id_target = workFlowEntita.id_target\n" +
                                "JOIN PBANDI_T_PROGETTO progetto ON progetto.ID_PROGETTO = documentoIndex.ID_PROGETTO \n" +
                                "WHERE workFlowEntita.id_work_flow = ?\n" +
                                "AND documentoIndex.id_tipo_documento_index in (42, 33, 63)";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                //CHECKLIST DI VALIDAZIONE INTERNA && REPORT DI VALIDAZIONE
                try {
                    data.addAll(getJdbcTemplate().query(query, new Object[]{idWorkflow}, new DocumentoIterVORowMapper()));
                } catch (EmptyResultDataAccessException ignored) {
                }
                //REPORT EXCEL
                try {
                    documentoIterVO = new DocumentoIterVO();
                    documentoIterVO.setNomeFile("proposte_erogazione.xls");
                    documentoIterVO.setFlagExcel(true);
                    documentoIterVO.setTipoExcel(2);
                    documentoIterVO.setIdTargetExcel(getJdbcTemplate().queryForObject(
                            "SELECT\n" +
                                    "workFlowEntita.id_target\n" +
                                    "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                    "JOIN PBANDI_C_ENTITA entita ON workFlowEntita.id_entita = entita.id_entita\n" +
                                    "WHERE workFlowEntita.id_work_flow = ?\n" +
                                    "AND entita.nome_entita = ?",
                            new Object[]{idWorkflow, "PBANDI_T_DISTINTA"},
                            Long.class));
                    data.add(documentoIterVO);
                } catch (IncorrectResultSizeDataAccessException ignored) {
                }
                break;
            case 14:
                idTipoDocumentoIndexLettAcc = 37;
                break;
            case 15:
                idTipoDocumentoIndexLettAcc = 38;
                break;
            case 16:
                idTipoDocumentoIndexLettAcc = 38;
                break;
            case 17:
                idTipoDocumentoIndexLettAcc = 49;
                idTipoDocumentoIndexAltriAllegati.add(55);
                break;
            //CHECKLIST E REPORT DI VALIDAZIONE INTERNA
            case 19:
                idTipoDocumentoIndexLettAcc = 84;
                break;
        }

        //Lettera Accompagnatoria
        if (idTipoDocumentoIndexLettAcc != null) {
            query =
                    "SELECT\n" +
                            "documentoIndex.id_documento_index,\n" +
                            "documentoIndex.nome_file,\n" +
                            "documentoIndex.id_tipo_documento_index\n" +
                            "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_entita = workFlowEntita.id_Entita AND documentoIndex.id_target = workFlowEntita.id_target\n" +
                            "WHERE workFlowEntita.id_work_flow = ?\n" +
                            "AND documentoIndex.id_tipo_documento_index = ?";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idWorkflow, idTipoDocumentoIndexLettAcc}, new DocumentoIterVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {
            }
        }
        //Altri Allegati
        for (int idTipoDocumento : idTipoDocumentoIndexAltriAllegati) {
            query =
                    "SELECT\n" +
                            "documentoIndex.id_documento_index,\n" +
                            "documentoIndex.nome_file,\n" +
                            "documentoIndex.id_tipo_documento_index\n" +
                            "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_entita = workFlowEntita.id_Entita AND documentoIndex.id_target = workFlowEntita.id_target\n" +
                            "WHERE workFlowEntita.id_work_flow = ?\n" +
                            "AND documentoIndex.id_tipo_documento_index = ?";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idWorkflow, idTipoDocumento}, new DocumentoIterVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {
            }
        }

        LOG.info(prf + "END");
        return data;
    }

    //GET ALLEGATO REPORT DOCUMENTO DI SPESA COPIA DA PBWEB
    @Override
    // Ex ReportExcelAction.generaReportDettaglioDocumentoDiSpesa().
    public EsitoLeggiFile reportDettaglioDocumentoDiSpesa(Long idDichiarazioneDiSpesa, Long idUtente, String idIride)
            throws Exception {
        String prf = "[ValidazioneRendicontazioneDAOImpl::reportDettaglioDocumentoDiSpesa] ";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + "idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idUtente = " + idUtente);

        if (idDichiarazioneDiSpesa == null) {
            throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato.");
        }

        try {

            EsitoReportDettaglioDocumentiDiSpesaDTO esitoSrv;
            esitoSrv = generaReportDettaglioDocumentoDiSpesa(idUtente, idIride, idDichiarazioneDiSpesa);

            if (esitoSrv == null || esitoSrv.getExcelBytes() == null || esitoSrv.getExcelBytes().length == 0) {
                throw new Exception("Report non generato.");
            }

            EsitoLeggiFile esito = new EsitoLeggiFile();
            esito.setNomeFile(esitoSrv.getNomeFile());
            esito.setBytes(esitoSrv.getExcelBytes());

            return esito;

            //return esito.getExcelBytes();
            //String nomeFile = esito.getNomeFile();

        } catch (Exception e) {
            LOG.error(prf + " ERRORE durante la generazione del report: ", e);
            throw new DaoException("Errore durante la generazione del report.", e);
        } finally {
            LOG.info(prf + " END");
        }

    }

    private EsitoReportDettaglioDocumentiDiSpesaDTO generaReportDettaglioDocumentoDiSpesa(
            Long idUtente, String identitaDigitale, Long idDichiarazioneSpesa)
            throws CSIException {

        logger.info("generaReportDettaglioDocumentoDiSpesa");

        try {

            String[] nameParameter = {"idUtente", "identitaDigitale", "idDichiarazioneSpesa"};
            ValidatorInput.verifyNullValue(nameParameter, idUtente,
                    identitaDigitale, idDichiarazioneSpesa);

            ReportDettaglioDocumentiSpesaVO reportVO = new ReportDettaglioDocumentiSpesaVO();
            reportVO.setIdDichiarazioneSpesa(idDichiarazioneSpesa);
            logger.info("\n\nseek ReportDettaglioDocumentiSpesaVO per idDichiarazioneSpesa : " + idDichiarazioneSpesa);
            List<ReportDettaglioDocumentiSpesaVO> elementiReport = genericDAO.findListWhere(reportVO);
            logger.info("\nfound: " + elementiReport.size() + "\n\n");
            EsitoReportDettaglioDocumentiDiSpesaDTO esito = null;
            if (!elementiReport.isEmpty()) {
                byte[] reportDettaglioDocumentiSpesaFileData = TableWriter
                        .writeTableToByteArray(
                                "reportDettaglioDocumentiSpesa",
                                new ExcelDataWriter(idDichiarazioneSpesa
                                        .toString()),
                                elementiReport);

                // String nomeFile = "reportValidazione"
                String nomeFile = "reportValidazione"
                        + idDichiarazioneSpesa + ".xls";

                esito = new EsitoReportDettaglioDocumentiDiSpesaDTO();
                esito.setExcelBytes(reportDettaglioDocumentiSpesaFileData);
                esito.setNomeFile(nomeFile);

            }

            return esito;

        } catch (Exception e) {
            logger.error(
                    "Errore nella creazione del report dettaglio documenti di spesa per id dichiarazione "
                            + idDichiarazioneSpesa,
                    e);
            throw new ValidazioneRendicontazioneException(
                    "Errore nella creazione del report dettaglio documenti di spesa per id dichiarazione "
                            + idDichiarazioneSpesa);
        }
    }

    //GESTIONE ITER
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void respingiIter(Long idWorkFlow, String motivazione, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        //CONTROLLA PERMESSI
        if (!checkPermessiIter(idWorkFlow, req)) {
            throw new Exception("L'utente non è abilitato per procedere al respingimento dell'iter!");
        }

        //RESPINGI ITER
        try {
            String query =
                    "UPDATE PBANDI_T_DETT_WORK_FLOW SET \n" +
                            "DT_ANNULLAMENTO = CURRENT_DATE,\n" +
                            "MOTIVAZIONE = ?,\n" +
                            "DT_AGGIORNAMENTO = CURRENT_DATE,\n" +
                            "ID_UTENTE_AGG = ?\n" +
                            "WHERE ID_DETT_WORK_FLOW IN (\n" +
                            "\tSELECT MAX(ptdwf.ID_DETT_WORK_FLOW) AS ID_DETT_WORK_FLOW\n" +
                            "\tFROM PBANDI_T_DETT_WORK_FLOW ptdwf \n" +
                            "\tGROUP BY ptdwf.ID_WORK_FLOW \n" +
                            ") AND ID_WORK_FLOW = ?";
            getJdbcTemplate().update(query, motivazione, userInfoSec.getIdUtente(), idWorkFlow);
        } catch (Exception e) {
            throw new Exception("Errore durante il respingimento dell'iter!");
        }

        //SCENARI ALTERNATIVI
        try {
            String getIdTipoIter = "SELECT workFlow.id_tipo_iter\n" +
                    "FROM PBANDI_T_WORK_FLOW workFlow\n" +
                    "WHERE workFlow.id_work_flow = ?";
            Integer idTipoIter = getJdbcTemplate().queryForObject(getIdTipoIter, new Object[]{idWorkFlow}, Integer.class);
            switch (idTipoIter) {
                case 1:
                    gestioneRendicontazioneDSSospesa(idWorkFlow, 35, Arrays.asList(63, 33), false, req);
                    break;
                case 5:
                    gestioneCNTRichiestaIntegrazione(idWorkFlow, 62, false, req);
                    break;
                case 6:
                    gestioneCNTAvvioControllo(idWorkFlow, 56, false, req);
                    break;
                case 7:
                    gestioneCNTEsitoPositivo(idWorkFlow, 57, 7, false, req);
                    break;
                case 8:
                    gestioneIntegrazioneProcRev(idWorkFlow, 45, false, req);
                    break;
                case 9:
                    gestioneRevoche(idWorkFlow, 44, 7, false, false, true, 6, false, req);
                    break;
                case 10:
                    gestioneRevoche(idWorkFlow, 46, null, false, false, false, 4, true, req);
                    break;
                case 11:
                    gestioneRevoche(idWorkFlow, 47, null, false, true, true, 8, false, req);
                    break;
                case 12:
                    gestioneRevoche(idWorkFlow, 48, null, false, true, true, 9, true, req);
                    break;
                case 13:
                    gestioneErogazione(idWorkFlow, 42, 63, false, req);
                    break;
                case 17:
                    gestioneRevoche(idWorkFlow, 49, null, false, true, true, 10, true, req);
                    break;
                case 14:
                    gestioneGaranzieRichiestaIntegrazione(idWorkFlow, 37, false, req);
                    break;
                case 15:
                    gestioneGaranzieInvEsitoNegativo(idWorkFlow, 38, false, req);
                    break;
                case 16:
                    gestioneGaranzieInvEsitoPositivo(idWorkFlow, 38, false, req);
                    break;
            }
        } catch (Exception e) {
            throw new Exception("Errore durante il processo post respingimento iter!");
        }

        LOG.info(prf + "END");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void autorizzaIter(Long idWorkFlow, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        //CONTROLLA PERMESSI
        if (!checkPermessiIter(idWorkFlow, req)) {
            throw new Exception("L'utente non è abilitato per procedere all'autorizzazione dell'iter!");
        }

        boolean lastStep;
        //APPROVA ITER
        try {
            //AUTORIZZA STEP CORRENTE
            String query =
                    "UPDATE PBANDI_T_DETT_WORK_FLOW SET \n" +
                            "DT_APPROVAZIONE = CURRENT_DATE,\n" +
                            "DT_AGGIORNAMENTO = CURRENT_DATE,\n" +
                            "ID_UTENTE_AGG = ?\n" +
                            "WHERE ID_DETT_WORK_FLOW IN (\n" +
                            "\tSELECT MAX(ptdwf.ID_DETT_WORK_FLOW) AS ID_DETT_WORK_FLOW\n" +
                            "\tFROM PBANDI_T_DETT_WORK_FLOW ptdwf \n" +
                            "\tGROUP BY ptdwf.ID_WORK_FLOW \n" +
                            ") AND ID_WORK_FLOW = ?";
            getJdbcTemplate().update(query, userInfoSec.getIdUtente(), idWorkFlow);

            query =
                    "SELECT COUNT(1)\n" +
                            "FROM PBANDI_T_DETT_WORK_FLOW dettWorkFlow\n" +
                            "JOIN PBANDI_T_WORK_FLOW workFlow ON workFlow.id_work_flow = dettWorkFlow.id_work_flow\n" +
                            "JOIN PBANDI_R_TIPO_STATO_ITER tipoStatoIter ON\n" +
                            "\ttipoStatoIter.id_tipo_iter = workFlow.id_tipo_iter AND \n" +
                            "\ttipoStatoIter.id_stato_iter = dettWorkFlow.id_stato_iter\n" +
                            "JOIN (SELECT max(tipoStatoIter.ordine) AS ordine, id_tipo_iter\n" +
                            "FROM PBANDI_R_TIPO_STATO_ITER tipoStatoIter\n" +
                            "GROUP BY tipoStatoIter.id_tipo_iter) maxOrdine ON \n" +
                            "\tmaxOrdine.id_tipo_iter = tipoStatoIter.id_tipo_iter AND \n" +
                            "\tmaxOrdine.ordine = tipoStatoIter.ordine\n" +
                            "WHERE dettWorkFlow.ID_DETT_WORK_FLOW IN (\n" +
                            "\tSELECT MAX(ptdwf.ID_DETT_WORK_FLOW) AS ID_DETT_WORK_FLOW\n" +
                            "\tFROM PBANDI_T_DETT_WORK_FLOW ptdwf \n" +
                            "\tGROUP BY ptdwf.ID_WORK_FLOW \n" +
                            ") AND dettWorkFlow.ID_WORK_FLOW = ?";

            String getIdDettWorkFlow = "SELECT SEQ_PBANDI_T_DETT_WORK_FLOW.nextval FROM dual";
            Long idDettWorkFlow = getJdbcTemplate().queryForObject(getIdDettWorkFlow, Long.class);
            //SE HO APPROVATO L'ULTIMO STEP
            lastStep = getJdbcTemplate().queryForObject(query, new Object[]{idWorkFlow}, Long.class) != 0;
            if (lastStep) {
                //GENERATO LO STEP RELATIVO ALL'APPROVAZIONE ITER
                query =
                        "INSERT INTO PBANDI_T_DETT_WORK_FLOW (\n" +
                                "\tID_DETT_WORK_FLOW,\n" +
                                "\tID_WORK_FLOW,\n" +
                                "\tID_STATO_ITER,\n" +
                                "\tDT_APPROVAZIONE,\n" +
                                "\tID_UTENTE_INS,\n" +
                                "\tDT_INSERIMENTO,\n" +
                                "\tID_UTENTE_AGG,\n" +
                                "\tDT_AGGIORNAMENTO\n" +
                                ")VALUES (\n" +
                                "\t?,\n" +
                                "\t?,\n" +
                                "\t5,\n" +
                                "\tCURRENT_DATE,\n" +
                                "\t?,\n" +
                                "\tCURRENT_DATE,\n" +
                                "\t?,\n" +
                                "\tCURRENT_DATE\n" +
                                ")";
                getJdbcTemplate().update(query,
                        idDettWorkFlow,
                        idWorkFlow,
                        userInfoSec.getIdUtente(),
                        userInfoSec.getIdUtente());
            } else {
                //TROVA STATO DELLO STEP SUCCESSIVO
                query =
                        "SELECT nextTipoStatoIter.id_stato_iter\n" +
                                "FROM PBANDI_T_WORK_FLOW workFlow\n" +
                                "JOIN PBANDI_T_DETT_WORK_FLOW dettWorkFlow ON dettWorkFlow.id_work_flow = workFlow.id_work_flow\n" +
                                "JOIN PBANDI_R_TIPO_STATO_ITER tipoStatoIter ON \n" +
                                "\ttipoStatoIter.id_tipo_iter = workFlow.id_tipo_iter AND \n" +
                                "\ttipoStatoIter.id_stato_iter = dettWorkFlow.id_stato_iter\n" +
                                "JOIN PBANDI_R_TIPO_STATO_ITER nextTipoStatoIter ON \n" +
                                "\tnextTipoStatoIter.id_tipo_iter = workFlow.id_tipo_iter AND \n" +
                                "\tnextTipoStatoIter.ordine = tipoStatoIter.ordine+1\n" +
                                "WHERE dettWorkFlow.ID_DETT_WORK_FLOW IN (\n" +
                                "\tSELECT MAX(ptdwf.ID_DETT_WORK_FLOW) AS ID_DETT_WORK_FLOW\n" +
                                "\tFROM PBANDI_T_DETT_WORK_FLOW ptdwf \n" +
                                "\tGROUP BY ptdwf.ID_WORK_FLOW \n" +
                                ") AND workFlow.id_work_flow = ?";
                Long idStatoIter = getJdbcTemplate().queryForObject(query, new Object[]{idWorkFlow}, Long.class);

                //CREA STEP SUCCESSIVO
                query =
                        "INSERT INTO PBANDI_T_DETT_WORK_FLOW (\n" +
                                "\tID_DETT_WORK_FLOW,\n" +
                                "\tID_WORK_FLOW,\n" +
                                "\tID_STATO_ITER,\n" +
                                "\tID_UTENTE_INS,\n" +
                                "\tDT_INSERIMENTO\n" +
                                ")VALUES (\n" +
                                "\t?,\n" +
                                "\t?,\n" +
                                "\t?,\n" +
                                "\t?,\n" +
                                "\tCURRENT_DATE\n" +
                                ")";
                getJdbcTemplate().update(query,
                        idDettWorkFlow,
                        idWorkFlow,
                        idStatoIter,
                        userInfoSec.getIdUtente());
            }
        } catch (Exception e) {
            throw new Exception("Errore durante l'approvazione dell'iter!");
        }

        //SCENARI ALTERNATIVI
        if (lastStep) {
            try {
                String getIdTipoIter = "SELECT workFlow.id_tipo_iter\n" +
                        "FROM PBANDI_T_WORK_FLOW workFlow\n" +
                        "WHERE workFlow.id_work_flow = ?";
                Integer idTipoIter = getJdbcTemplate().queryForObject(getIdTipoIter, new Object[]{idWorkFlow}, Integer.class);
                LOG.info(prf + " idTipoIter: " + idTipoIter);
                
                switch (idTipoIter) {
                    case 1: //tipoIter = 1 -- CdU 3 --  // prendere solo tipo "LETTERA ACCOMPAGNATORIA"
                        gestioneRendicontazioneDSSospesa(idWorkFlow, 35, Arrays.asList(63, 33), true, req);
                        break;
                    case 2:
                        autorizzaRendicontazioneChiusuraValidazione(idWorkFlow, 60, Arrays.asList(63, 33), req);
                        break;
                    case 5:
                        gestioneCNTRichiestaIntegrazione(idWorkFlow, 62, true, req);
                        break;
                    case 6:
                        gestioneCNTAvvioControllo(idWorkFlow, 56, true, req);
                        break;
                    case 7:
                        gestioneCNTEsitoPositivo(idWorkFlow, 57, 7, true, req);
                        break;
                    case 8:
                        gestioneIntegrazioneProcRev(idWorkFlow, 45, true, req);
                        break;
                    case 9:
                        gestioneRevoche(idWorkFlow, 44, 7, true, false, true, 6, false, req);
                        break;
                    case 10:
                        gestioneRevoche(idWorkFlow, 46, null, true, false, false, 4, true, req);
                        break;
                    case 11:
                        gestioneRevoche(idWorkFlow, 47, null, true, true, true, 8, false, req);
                        break;
                    case 12:
                        gestioneRevoche(idWorkFlow, 48, null, true, true, false, 9, true, req);
                        break;
                    case 13:
                        gestioneErogazione(idWorkFlow, 42, 63, true, req);
                        break;
                    case 14:
                        gestioneGaranzieRichiestaIntegrazione(idWorkFlow, 37, true, req);
                        break;
                    case 15:
                        gestioneGaranzieInvEsitoNegativo(idWorkFlow, 38, true, req);
                        break;
                    case 16:
                        gestioneGaranzieInvEsitoPositivo(idWorkFlow, 38, true, req);
                        break;
                    case 17:
                        gestioneRevoche(idWorkFlow, 49, null, true, true, false, 10, true, req);
                        break;
                    case 18:
                        gestioneEsitoValidazioneErogazioneSospesa(idWorkFlow, 60, 63, true, req);
                        break;
                    case 19:
                        gestioneComunicazioneInterventoSostitutivo(idWorkFlow, 84, true, req);
                        break;
                }
            } catch (Exception e) {
                throw new Exception("Errore durante il processo post approvazione iter!");
            }
        }
        LOG.info(prf + "END");
    }

    @Override
    public boolean checkPermessiIter(Long idWorkFlow, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        boolean esito;

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            String query =
                    "WITH lastStep AS (\n" +
                            "\tSELECT MAX(ptdwf.ID_DETT_WORK_FLOW) AS ID_DETT_WORK_FLOW\n" +
                            "\tFROM PBANDI_T_DETT_WORK_FLOW ptdwf \n" +
                            "\tGROUP BY ptdwf.ID_WORK_FLOW \n" +
                            ")\n" +
                            "SELECT COUNT(1)\n" +
                            "FROM PBANDI_T_DETT_WORK_FLOW dettWorkFlow\n" +
                            "JOIN lastStep ON lastStep.id_dett_work_flow = dettWorkFlow.id_dett_work_flow\n" +
                            "JOIN PBANDI_T_WORK_FLOW workFlow ON workFlow.id_work_flow = dettWorkFlow.id_work_flow\n" +
                            "JOIN PBANDI_R_TIPO_STATO_ITER tipoStatoIter ON \n" +
                            "\ttipoStatoIter.id_tipo_iter = workFlow.id_tipo_iter AND \n" +
                            "\ttipoStatoIter.id_stato_iter = dettWorkFlow.id_stato_iter\n" +
                            "JOIN PBANDI_R_INCARICO_SOGGETTO incaricoSoggetto ON incaricoSoggetto.id_incarico = tipoStatoIter.id_incarico\n" +
                            "WHERE dettWorkFlow.id_work_flow = ?\n" +
                            "AND dettWorkFlow.dt_approvazione IS NULL \n" +
                            "AND dettWorkFlow.dt_annullamento IS NULL\n" +
                            "AND dettWorkFlow.id_stato_iter <> 5\n" +
                            "AND incaricoSoggetto.id_soggetto = ?";
            esito = getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{idWorkFlow, userInfoSec.getIdSoggetto()},
                    Long.class
            ) != 0;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to checkPermessiIter", e);
            esito = false;
        } finally {
            LOG.info(prf + "END");
        }

        return esito;
    }

    //AVVIA ITER
    @Transactional
    @Override
    public String avviaIterAutorizzativo(Long idTipoIter, Long idEntita, Long idTarget, Long idProgetto, Long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        //check permessi
        boolean isCheckUtente = false;
        try {
            //recupero l'incarico dell'idUtente passato e confronto con l'incarico di livello 1 per l'idTipoIter passato
            String getIncaricoIter =
                    "SELECT ID_INCARICO \n" +
                            "FROM PBANDI_R_TIPO_STATO_ITER prtsi \n" +
                            "WHERE prtsi.ID_TIPO_ITER  = ?\n" +
                            "AND ORDINE = 1";
            int incaricoIter = getJdbcTemplate().queryForObject(getIncaricoIter, new Object[]{idTipoIter}, Integer.class);

            String getIncaricoUtente =
                    "SELECT pris.ID_INCARICO \n" +
                            "FROM PBANDI_R_INCARICO_SOGGETTO pris \n" +
                            "LEFT JOIN PBANDI_T_SOGGETTO pts ON pts.ID_SOGGETTO = pris.ID_SOGGETTO \n" +
                            "LEFT JOIN PBANDI_T_UTENTE ptu ON ptu.ID_SOGGETTO = pts.ID_SOGGETTO \n" +
                            "WHERE ptu.ID_UTENTE = ?\n" +
                            "AND id_incarico = ?\n" +
                            "AND rownum = 1";
            int incaricoUtente = getJdbcTemplate().queryForObject(getIncaricoUtente, new Object[]{idUtente, incaricoIter}, Integer.class);

            if (incaricoIter == incaricoUtente) {
                isCheckUtente = true;
            }
        } catch (IncorrectResultSizeDataAccessException ignored) {
        } catch (Exception e) {
            logger.error(e);
        }
        if (!isCheckUtente) {
            return "Utente non abilitato all'avvio iter!";
        }
        //primo passo : creazione del workflow
        BigDecimal idWorkFlow;
        try {
            idWorkFlow = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_WORK_FLOW.nextval FROM dual ", BigDecimal.class);

            String insert =
                    "INSERT INTO PBANDI_T_WORK_FLOW (\n" +
                            "ID_WORK_FLOW,\n" +
                            "ID_TIPO_ITER,\n" +
                            "ID_UTENTE_INS,\n" +
                            "DT_INSERIMENTO\n" +
                            ") VALUES (?,?,?, CURRENT_DATE)";

            getJdbcTemplate().update(
                    insert,
                    idWorkFlow,
                    idTipoIter,
                    idUtente
            );
        } catch (Exception e) {
            logger.info(e);
            idWorkFlow = null;
        }
        if (idWorkFlow == null) {
            return "Errore nell'inserimento del workflow!";
        }
        //secondo passo inserimento tabella PBANDI_T_DETT_WORK_FLOW
        BigDecimal idDettWorkFlow;
        try {
            idDettWorkFlow = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_DETT_WORK_FLOW.nextval FROM dual ", BigDecimal.class);

            // recupero lo stato iter per il primo passo,
            Long idStatoIter = getJdbcTemplate().queryForObject(
                    "SELECT ID_STATO_ITER \n" +
                            "FROM PBANDI_R_TIPO_STATO_ITER prtsi \n" +
                            "WHERE prtsi.ID_TIPO_ITER = ? \n" +
                            "AND prtsi.ORDINE = 1",
                    new Object[]{idTipoIter},
                    Long.class
            );

            //inserimento per il passo 1
            String insert =
                    "INSERT INTO PBANDI_T_DETT_WORK_FLOW (\n" +
                            "ID_DETT_WORK_FLOW,\n" +
                            "ID_WORK_FLOW,\n" +
                            "ID_STATO_ITER,\n" +
                            "DT_APPROVAZIONE,\n" +
                            "ID_UTENTE_INS,\n" +
                            "ID_UTENTE_AGG,\n" +
                            "DT_INSERIMENTO,\n" +
                            "DT_AGGIORNAMENTO\n" +
                            ") VALUES (?,?,?,CURRENT_DATE,?,?,CURRENT_DATE,CURRENT_DATE)";
            getJdbcTemplate().update(insert,
                    idDettWorkFlow,
                    idWorkFlow,
                    idStatoIter,
                    idUtente,
                    idUtente);

            // recupero lo stato iter per il secondo passo,
            idStatoIter = getJdbcTemplate().queryForObject(
                    "SELECT ID_STATO_ITER \n" +
                            "FROM PBANDI_R_TIPO_STATO_ITER prtsi \n" +
                            "WHERE prtsi.ID_TIPO_ITER = ? \n" +
                            "AND prtsi.ORDINE = 2",
                    new Object[]{idTipoIter},
                    Long.class);

            idDettWorkFlow = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_DETT_WORK_FLOW.nextval FROM dual ", BigDecimal.class);

            String insertSecondoPasso =
                    "INSERT INTO PBANDI_T_DETT_WORK_FLOW (\n" +
                            "ID_DETT_WORK_FLOW,\n" +
                            "ID_WORK_FLOW,\n" +
                            "ID_STATO_ITER,\n" +
                            "ID_UTENTE_INS,\n" +
                            "DT_INSERIMENTO\n" +
                            ") VALUES (?,?,?,?,CURRENT_DATE)";
            getJdbcTemplate().update(insertSecondoPasso,
                    idDettWorkFlow,
                    idWorkFlow,
                    idStatoIter,
                    idUtente);

        } catch (Exception e) {
            logger.error(e);
            idDettWorkFlow = null;
        }
        if (idDettWorkFlow == null) {
            return "Errore nell'inserimento del dettaglio workflow!";
        }
        //creazone della relazione iter-entit-target
        BigDecimal idWorkFlowEntita;
        try {
            idWorkFlowEntita = getJdbcTemplate().queryForObject("SELECT SEQ_PBANDI_T_WORK_FLOW_ENTITA.nextval FROM dual ", BigDecimal.class);

            String insert =
                    "INSERT INTO PBANDI_T_WORK_FLOW_ENTITA (\n" +
                            "ID_WORK_FLOW_ENTITA,\n" +
                            "ID_WORK_FLOW,\n" +
                            "ID_ENTITA,\n" +
                            "ID_TARGET,\n" +
                            "ID_PROGETTO,\n" +
                            "ID_UTENTE_INS,\n" +
                            "DT_INSERIMENTO\n" +
                            ") VALUES(?,?,?,?,?,?, CURRENT_DATE)";
            getJdbcTemplate().update(insert,
                    idWorkFlowEntita,
                    idWorkFlow,
                    idEntita,
                    idTarget,
                    idProgetto,
                    idUtente);

        } catch (Exception e) {
            logger.info(e);
            idWorkFlowEntita = null;
        }
        if (idWorkFlowEntita == null) {
            return "Errore nell'inserimento dell'entita workflow!";
        }

        LOG.info(prf + "END");
        return "";
    }


    //SUGGEST
    @Override
    public List<DenominazioneSuggestVO> suggestBeneficiario(String denominazione, HttpServletRequest req) throws DaoException {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<DenominazioneSuggestVO> denominazioneSuggestVOList = new ArrayList<>();

        try {
            String sql =
                    "SELECT DISTINCT \n" +
                            "soggetto.id_soggetto AS idSoggetto, \n" +
                            "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazione \n" +
                            "FROM \n" +
                            "PBANDI_R_SOGGETTO_PROGETTO soggetto \n" +
                            "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
                            "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
                            "WHERE UPPER(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) LIKE CONCAT('%' , CONCAT(?,'%'))\n" +
                            "AND soggetto.ID_TIPO_ANAGRAFICA = 1\n" +
                            "AND soggetto.ID_TIPO_BENEFICIARIO <> 4\n";

            LOG.info(sql);

            denominazioneSuggestVOList = getJdbcTemplate().query(
                    sql,
                    ps -> ps.setString(1, denominazione),
                    new DenominazioneSuggestVORowMapper()
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read DenominazioneSuggestVO", e);

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read DenominazioneSuggestVO", e);
            throw new DaoException("DaoException while trying to read DenominazioneSuggestVO", e);
        } finally {
            LOG.info(prf + " END");
        }

        return denominazioneSuggestVOList;
    }

    @Override
    public List<BandoLineaInterventVO> suggestBando(String nomeBando, Long idSoggetto) throws DaoException {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<BandoLineaInterventVO> bandoLineaInterventVOList = new ArrayList<>();

        try {
            String sql =
                    "SELECT DISTINCT \n" +
                            "bandoLineaIntervent.PROGR_BANDO_LINEA_INTERVENTO AS idBandoLineaIntervent,\n" +
                            "bandoLineaIntervent.NOME_BANDO_LINEA AS nomeBandoLineaIntervent\n" +
                            "FROM PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervent\n" +
                            "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP enteComp ON enteComp.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento \n" +
                            "JOIN PBANDI_T_DOMANDA domanda ON domanda.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento\n" +
                            "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = domanda.id_domanda \n" +
                            "JOIN PBANDI_R_SOGGETTO_PROGETTO soggetto ON soggetto.id_progetto = progetto.id_progetto \n" +
                            "WHERE soggetto.id_soggetto = ? \n" +
                            "AND enteComp.ID_ENTE_COMPETENZA = '2' \n" +
                            "AND enteComp.ID_RUOLO_ENTE_COMPETENZA = '1' \n" +
                            "AND UPPER(bandoLineaIntervent.NOME_BANDO_LINEA) LIKE CONCAT('%' , CONCAT(?,'%')) \n" +
                            "ORDER BY bandoLineaIntervent.NOME_BANDO_LINEA";

            LOG.info(sql);

            bandoLineaInterventVOList = getJdbcTemplate().query(
                    sql,
                    ps -> {
                        ps.setLong(1, idSoggetto);
                        ps.setString(2, nomeBando);
                    },
                    new BandoLineaInterventVORowMapper()
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read BandoLineaInterventVO", e);

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read BandoLineaInterventVO", e);
            throw new DaoException("DaoException while trying to read BandoLineaInterventVO", e);
        } finally {
            LOG.info(prf + " END");
        }

        return bandoLineaInterventVOList;
    }

    @Override
    public List<ProgettoSuggestVO> suggestProgetto(String titoloProgetto, Long idBando, Long idSoggetto, HttpServletRequest req) throws DaoException {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<ProgettoSuggestVO> progettoSuggestVOList = new ArrayList<>();

        try {
            String sql = "SELECT DISTINCT \n" +
                    "progetto.id_progetto AS idProgetto, \n" +
                    "progetto.titolo_progetto as titoloProgetto, \n" +
                    "progetto.codice_visualizzato AS codiceVisualizzato \n" +
                    "FROM PBANDI_T_PROGETTO progetto \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
                    "WHERE progetto.id_domanda IN \n" +
                    "( \n" +
                    "\tSELECT domanda.id_domanda \n" +
                    "\tFROM PBANDI_T_DOMANDA domanda \n" +
                    "\tWHERE domanda.progr_bando_linea_intervento IN \n" +
                    "\t( \n" +
                    "\t\tSELECT bandoLineaIntervento.progr_bando_linea_intervento \n" +
                    "\t\tFROM PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento \n" +
                    "\t\tJOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnteComp ON bandoLineaEnteComp.progr_bando_linea_intervento = bandoLineaIntervento.progr_bando_linea_intervento \n" +
                    "\t\tWHERE bandoLineaIntervento.progr_bando_linea_intervento = ?\n" +
                    "\t\tAND bandoLineaEnteComp.id_ente_competenza = 2 \n" +
                    "\t\tAND bandoLineaEnteComp.id_ruolo_ente_competenza = 1\n" +
                    "\t) \n" +
                    ") \n" +
                    "AND \n" +
                    "soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND \n" +
                    "soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                    "AND \n" +
                    "soggettoProgetto.id_soggetto = ?\n" +
                    "AND \n" +
                    "upper(progetto.codice_visualizzato) LIKE CONCAT('%',CONCAT(upper(?),'%'))";

            LOG.info(sql);

            progettoSuggestVOList = getJdbcTemplate().query(
                    sql,
                    ps -> {
                        ps.setLong(1, idBando);
                        ps.setLong(2, idSoggetto);
                        ps.setString(3, titoloProgetto);
                    },
                    new ProgettoSuggestVOplusRowMapper()
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read ProgettoSuggestVO", e);

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read ProgettoSuggestVO", e);
            throw new DaoException("DaoException while trying to read ProgettoSuggestVO", e);
        } finally {
            LOG.info(prf + " END");
        }

        return progettoSuggestVOList;
    }

    @Override
    public Object getTendinaBando() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<SuggestIterVO> list = new ArrayList<>();

        try {
            String sql = "SELECT \r\n"
                    + "	prbli.PROGR_BANDO_LINEA_INTERVENTO , \r\n"
                    + "	prbli.ID_BANDO ,\r\n"
                    + "	prbli.NOME_BANDO_LINEA \r\n"
                    + "	FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe \r\n"
                    + "	LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO \r\n"
                    + "	LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd  ON prsd.PROGR_SOGGETTO_DOMANDA =prsp.PROGR_SOGGETTO_DOMANDA \r\n"
                    + "	LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA \r\n"
                    + "	LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO \r\n"
                    + "	LEFT JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec ON prblec.PROGR_BANDO_LINEA_INTERVENTO  = prbli.PROGR_BANDO_LINEA_INTERVENTO \r\n"
                    + "	WHERE \r\n"
                    + "	prblec.ID_RUOLO_ENTE_COMPETENZA =1\r\n"
                    + "	AND prblec.ID_ENTE_COMPETENZA =2\r\n"
                    + "	AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
                    + "	AND prsp.ID_TIPO_ANAGRAFICA =1";

            RowMapper<SuggestIterVO> lista = (rs, rowNum) -> {
                SuggestIterVO sug = new SuggestIterVO();

//				sug.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
//				sug.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
                sug.setNomeBando(rs.getString("NOME_BANDO_LINEA"));
//				sug.setDenominazione(rs.getString("denominazione"));
                sug.setProgBandoLinea(rs.getBigDecimal("PROGR_BANDO_LINEA_INTERVENTO"));
//				sug.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));

                return sug;
            };

            list = getJdbcTemplate().query(sql, lista);
        } catch (Exception e) {
            logger.error(e);
        }

        return list;
    }

    @Override
    public Object getTendinaProgetto() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<SuggestIterVO> list = new ArrayList<>();

        try {
            String sql = "SELECT\r\n"
                    + "    DISTINCT prsp.ID_PROGETTO,\r\n"
                    + "    ptp.CODICE_VISUALIZZATO,\r\n"
                    + "    prbli.PROGR_BANDO_LINEA_INTERVENTO,\r\n"
                    + "    prbli.ID_BANDO,\r\n"
                    + "    prbli.NOME_BANDO_LINEA\r\n"
                    + "FROM\r\n"
                    + "    PBANDI_T_WORK_FLOW_ENTITA ptwfe\r\n"
                    + "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\r\n"
                    + "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
                    + "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
                    + "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
                    + "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
                    + "    LEFT JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec ON prblec.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
                    + "WHERE\r\n"
                    + "    prblec.ID_RUOLO_ENTE_COMPETENZA = 1\r\n"
                    + "    AND prblec.ID_ENTE_COMPETENZA = 2\r\n"
                    + "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
                    + "    AND prsp.ID_TIPO_ANAGRAFICA = 1";


            RowMapper<SuggestIterVO> lista = (rs, rowNum) -> {
                SuggestIterVO sug = new SuggestIterVO();

//				sug.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
                sug.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
//				sug.setNomeBando(rs.getString("NOME_BANDO_LINEA"));
//				sug.setDenominazione(rs.getString("denominazione"));
//				sug.setProgBandoLinea(rs.getBigDecimal("PROGR_BANDO_LINEA_INTERVENTO"));
                sug.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));

                return sug;
            };

            list = getJdbcTemplate().query(sql, lista);
        } catch (Exception e) {
            logger.error(e);
        }

        return list;
    }

    @Override
    public Object getTendinaBeneficiario() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<SuggestIterVO> list = new ArrayList<>();

        try {
            List<SuggestIterVO> listEG;
            List<SuggestIterVO> listPF;
            String sqlEG = "SELECT\r\n"
                    + "   DISTINCT prsp.ID_SOGGETTO,\r\n"
                    + "	pteg.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione\r\n"
                    + "FROM\r\n"
                    + "    PBANDI_T_WORK_FLOW_ENTITA ptwfe\r\n"
                    + "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\r\n"
                    + "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
                    + "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
                    + "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
                    + "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
                    + "    LEFT JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec ON prblec.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
                    + "    JOIN PBANDI_T_ENTE_GIURIDICO pteg ON pteg.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\r\n"
                    + "WHERE\r\n"
                    + "    prblec.ID_RUOLO_ENTE_COMPETENZA = 1\r\n"
                    + "    AND prblec.ID_ENTE_COMPETENZA = 2\r\n"
                    + "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
                    + "    AND prsp.ID_TIPO_ANAGRAFICA = 1";

            RowMapper<SuggestIterVO> lista = (rs, rowNum) -> {
                SuggestIterVO sug = new SuggestIterVO();

                sug.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
//				sug.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
//				sug.setNomeBando(rs.getString("NOME_BANDO_LINEA"));
                sug.setDenominazione(rs.getString("denominazione"));
//				sug.setProgBandoLinea(rs.getBigDecimal("PROGR_BANDO_LINEA_INTERVENTO"));
//				sug.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));

                return sug;
            };

            String sqlPerfis = "SELECT\r\n"
                    + "    DISTINCT prsp.ID_SOGGETTO,\r\n"
                    + "    concat(ptpf.NOME, CONCAT (' ', ptpf.COGNOME)) as denominazione\r\n"
                    + "FROM\r\n"
                    + "    PBANDI_T_WORK_FLOW_ENTITA ptwfe\r\n"
                    + "    LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\r\n"
                    + "    LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\r\n"
                    + "    LEFT JOIN PBANDI_R_SOGGETTO_DOMANDA prsd ON prsd.PROGR_SOGGETTO_DOMANDA = prsp.PROGR_SOGGETTO_DOMANDA\r\n"
                    + "    LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = prsd.ID_DOMANDA\r\n"
                    + "    LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\r\n"
                    + "    LEFT JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec ON prblec.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\r\n"
                    + "    JOIN PBANDI_T_PERSONA_FISICA ptpf ON ptpf.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\r\n"
                    + "WHERE\r\n"
                    + "    prblec.ID_RUOLO_ENTE_COMPETENZA = 1\r\n"
                    + "    AND prblec.ID_ENTE_COMPETENZA = 2\r\n"
                    + "    AND prsp.ID_TIPO_BENEFICIARIO <> 4\r\n"
                    + "    AND prsp.ID_TIPO_ANAGRAFICA = 1";

            listEG = getJdbcTemplate().query(sqlEG, lista);
            listPF = getJdbcTemplate().query(sqlPerfis, lista);

            list.addAll(listPF);
            list.addAll(listEG);

        } catch (Exception e) {
            logger.error(e);
        }

        return list;
    }

    @Override
    public List<StatoIterVO> getStatoIter(HttpServletRequest req) {
        String prf = "[IterAutorizzativiDAOImpl :: getStatoIter]";
        logger.info(prf + "BEGIN");
        List<StatoIterVO> response = new ArrayList<>();

        try {

            String sql = "SELECT\n" +
                    "statoIter.ID_STATO_ITER ,\n" +
                    "statoIter.DESC_STATO_ITER\n" +
                    "FROM PBANDI_D_STATO_ITER statoIter\n" +
                    "WHERE statoIter.id_stato_iter IN (2, 3, 4, 5, 6)\n" +
                    "ORDER BY ID_STATO_ITER";

            RowMapper<StatoIterVO> lista = (rs, rowNum) -> {
                StatoIterVO s = new StatoIterVO();

                s.setIdStatoIter(rs.getInt("ID_STATO_ITER"));
                s.setDescStatoIter(rs.getString("DESC_STATO_ITER"));

                return s;
            };

            response = getJdbcTemplate().query(sql, lista);

        } catch (Exception e) {
            logger.error(e);
        }

        return response;
    }

    @Override
    public Object suggestIter(SuggestIterVO sugVO) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIterVO> lista = new ArrayList<>();
        try {

//			1 per bando
//			2 benef
//			3 progetti

            switch (sugVO.getId()) {
                case 1:
                    lista = getBandi(sugVO);
                    break;
                case 2:
                    lista = getBenef(sugVO);
                    break;
                case 3:
                    lista = getProgetti(sugVO);
                    break;

                default:
                    break;
            }


        } catch (Exception e) {
            logger.error(e);
        }

        return lista;
    }

    private List<SuggestIterVO> getBandi(SuggestIterVO sugVO) {

        List<SuggestIterVO> list = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder();

            sql.append("WITH denom AS (\n" +
                    "    SELECT DISTINCT \n" +
                    "    PERSFIS.ID_SOGGETTO,\n" +
                    "    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione,\n" +
                    "    MAX(persfis.ID_PERSONA_FISICA) AS id\n" +
                    "    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
                    "    WHERE\n" +
                    "    persfis.ID_PERSONA_FISICA IN (\n" +
                    "        SELECT MAX(prsp2.ID_PERSONA_FISICA)\n" +
                    "        FROM PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
                    "        GROUP BY prsp2.ID_SOGGETTO\n" +
                    "    )\n" +
                    "    GROUP BY\n" +
                    "        persfis.ID_SOGGETTO,\n" +
                    "        persfis.NOME,\n" +
                    "        persfis.COGNOME\n" +
                    "    UNION\n" +
                    "    SELECT\n" +
                    "        DISTINCT ENTEGIU.ID_SOGGETTO,\n" +
                    "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione,\n" +
                    "        MAX(entegiu.ID_ENTE_GIURIDICO) AS id\n" +
                    "    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
                    "    WHERE entegiu.ID_ENTE_GIURIDICO IN (\n" +
                    "            SELECT MAX(prsp2.ID_ENTE_GIURIDICO)\n" +
                    "            FROM PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
                    "            GROUP BY prsp2.ID_SOGGETTO\n" +
                    "    )\n" +
                    "    GROUP BY\n" +
                    "        ID_SOGGETTO,\n" +
                    "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO\n" +
                    ")\n" +
                    "SELECT DISTINCT \n" +
                    "prbli.PROGR_BANDO_LINEA_INTERVENTO,\n" +
                    "prbli.NOME_BANDO_LINEA\n" +
                    "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n" +
                    "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO= ptwfe.ID_PROGETTO\n" +
                    "LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\n" +
                    "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
                    "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
                    "LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\n" +
                    "LEFT JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec ON prblec.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\n" +
                    "LEFT JOIN denom ON denom.id_soggetto = pts.ID_SOGGETTO\n" +
                    "WHERE prblec.ID_RUOLO_ENTE_COMPETENZA = 1\n" +
                    "AND prblec.ID_ENTE_COMPETENZA = 2\n" +
                    "AND prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
                    "AND prsp.ID_TIPO_ANAGRAFICA = 1\n");

            if (sugVO.getIdSoggetto() != null) {
                sql.append("AND prsp.id_soggetto= ?\r\n");
            }
            if (sugVO.getIdProgetto() != null) {
                sql.append("AND prsp.id_progetto= ?\r\n");
            }
            if (sugVO.getValue().trim().length() > 0) {
                sql.append("AND UPPER(prbli.NOME_BANDO_LINEA) LIKE CONCAT('%', CONCAT(UPPER(?), '%'))\r\n");
            }
            sql.append(" AND ROWNUM<=100\r\n" +
                    "ORDER BY prbli.NOME_BANDO_LINEA \n");


            list = getJdbcTemplate().query(
                    sql.toString(),
                    ps -> {
                        int k = 1;
                        if (sugVO.getIdSoggetto() != null) {
                            ps.setBigDecimal(k++, sugVO.getIdSoggetto());
                        }
                        if (sugVO.getIdProgetto() != null) {
                            ps.setBigDecimal(k++, sugVO.getIdProgetto());
                        }
                        if (sugVO.getValue().trim().length() > 0) {
                            ps.setString(k, sugVO.getValue());
                        }
                    },
                    (rs, rowNum) -> {
                        SuggestIterVO sug = new SuggestIterVO();

                        sug.setNomeBando(rs.getString("NOME_BANDO_LINEA"));
                        sug.setProgBandoLinea(rs.getBigDecimal("PROGR_BANDO_LINEA_INTERVENTO"));

                        return sug;
                    });
        } catch (Exception e) {
            logger.error(e);
        }
        return list;
    }

    private List<SuggestIterVO> getBenef(SuggestIterVO sugVO) {
        List<SuggestIterVO> list = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder();

            sql.append("WITH denom AS (\n" +
                    "    SELECT\n" +
                    "        DISTINCT PERSFIS.ID_SOGGETTO,\n" +
                    "        concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione,\n" +
                    "        MAX(persfis.ID_PERSONA_FISICA) AS id\n" +
                    "    FROM\n" +
                    "        PBANDI_T_PERSONA_FISICA persfis\n" +
                    "    WHERE\n" +
                    "        persfis.ID_PERSONA_FISICA IN (\n" +
                    "            SELECT\n" +
                    "                MAX(prsp2.ID_PERSONA_FISICA)\n" +
                    "            FROM\n" +
                    "                PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
                    "            GROUP BY\n" +
                    "                prsp2.ID_SOGGETTO\n" +
                    "        )\n" +
                    "    GROUP BY\n" +
                    "        persfis.ID_SOGGETTO,\n" +
                    "        persfis.NOME,\n" +
                    "        persfis.COGNOME\n" +
                    "    UNION\n" +
                    "    SELECT\n" +
                    "        DISTINCT ENTEGIU.ID_SOGGETTO,\n" +
                    "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione,\n" +
                    "        MAX(entegiu.ID_ENTE_GIURIDICO) AS id\n" +
                    "    FROM\n" +
                    "        PBANDI_T_ENTE_GIURIDICO entegiu\n" +
                    "    WHERE\n" +
                    "        entegiu.ID_ENTE_GIURIDICO IN (\n" +
                    "            SELECT\n" +
                    "                MAX(prsp2.ID_ENTE_GIURIDICO)\n" +
                    "            FROM\n" +
                    "                PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
                    "            GROUP BY\n" +
                    "                prsp2.ID_SOGGETTO\n" +
                    "        )\n" +
                    "    GROUP BY\n" +
                    "        ID_SOGGETTO,\n" +
                    "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO\n" +
                    ")\n" +
                    "SELECT DISTINCT \n" +
                    "prsp.ID_SOGGETTO,\n" +
                    "denom.denominazione \n" +
                    "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n" +
                    "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\n" +
                    "LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\n" +
                    "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
                    "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
                    "LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\n" +
                    "LEFT JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec ON prblec.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\n" +
                    "LEFT JOIN denom ON denom.id_soggetto = pts.ID_SOGGETTO\n" +
                    "WHERE prblec.ID_RUOLO_ENTE_COMPETENZA = 1\n" +
                    "AND prblec.ID_ENTE_COMPETENZA = 2\n" +
                    "AND prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
                    "AND prsp.ID_TIPO_ANAGRAFICA = 1\n");

            if (sugVO.getProgBandoLinea() != null) {
                sql.append("AND prbli.PROGR_BANDO_LINEA_INTERVENTO = ?\r\n");
            }
            if (sugVO.getIdProgetto() != null) {
                sql.append("AND prsp.id_progetto= ?\r\n");
            }
            if (sugVO.getValue().trim().length() > 0) {
                sql.append("AND  UPPER(denom.denominazione) LIKE CONCAT('%', CONCAT(UPPER(?), '%'))\r\n");
            }
            sql.append(" AND ROWNUM<=100\r\n" +
                    "ORDER BY denom.denominazione \n");

            list = getJdbcTemplate().query(sql.toString(),
                    ps -> {
                        int k = 1;
                        if (sugVO.getProgBandoLinea() != null) {
                            ps.setBigDecimal(k++, sugVO.getProgBandoLinea());
                        }
                        if (sugVO.getIdProgetto() != null) {
                            ps.setBigDecimal(k++, sugVO.getIdProgetto());
                        }
                        if (sugVO.getValue().trim().length() > 0) {
                            ps.setString(k, sugVO.getValue());
                        }
                    },
                    (rs, rowNum) -> {
                        SuggestIterVO sug = new SuggestIterVO();

                        sug.setIdSoggetto(rs.getBigDecimal("ID_SOGGETTO"));
                        sug.setDenominazione(rs.getString("denominazione"));

                        return sug;
                    });
        } catch (Exception e) {
            logger.error(e);
        }
        return list;

    }

    private List<SuggestIterVO> getProgetti(SuggestIterVO sugVO) {
        List<SuggestIterVO> list = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder();

            sql.append("WITH denom AS (\n" +
                    "    SELECT\n" +
                    "        DISTINCT PERSFIS.ID_SOGGETTO,\n" +
                    "        concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione,\n" +
                    "        MAX(persfis.ID_PERSONA_FISICA) AS id\n" +
                    "    FROM\n" +
                    "        PBANDI_T_PERSONA_FISICA persfis\n" +
                    "    WHERE\n" +
                    "        persfis.ID_PERSONA_FISICA IN (\n" +
                    "            SELECT\n" +
                    "                MAX(prsp2.ID_PERSONA_FISICA)\n" +
                    "            FROM\n" +
                    "                PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
                    "            GROUP BY\n" +
                    "                prsp2.ID_SOGGETTO\n" +
                    "        )\n" +
                    "    GROUP BY\n" +
                    "        persfis.ID_SOGGETTO,\n" +
                    "        persfis.NOME,\n" +
                    "        persfis.COGNOME\n" +
                    "    UNION\n" +
                    "    SELECT\n" +
                    "        DISTINCT ENTEGIU.ID_SOGGETTO,\n" +
                    "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione,\n" +
                    "        MAX(entegiu.ID_ENTE_GIURIDICO) AS id\n" +
                    "    FROM\n" +
                    "        PBANDI_T_ENTE_GIURIDICO entegiu\n" +
                    "    WHERE\n" +
                    "        entegiu.ID_ENTE_GIURIDICO IN (\n" +
                    "            SELECT\n" +
                    "                MAX(prsp2.ID_ENTE_GIURIDICO)\n" +
                    "            FROM\n" +
                    "                PBANDI_R_SOGGETTO_PROGETTO prsp2\n" +
                    "            GROUP BY\n" +
                    "                prsp2.ID_SOGGETTO\n" +
                    "        )\n" +
                    "    GROUP BY\n" +
                    "        ID_SOGGETTO,\n" +
                    "        entegiu.DENOMINAZIONE_ENTE_GIURIDICO\n" +
                    ")\n" +
                    "SELECT DISTINCT \n" +
                    "prsp.ID_PROGETTO,\n" +
                    "ptp.CODICE_VISUALIZZATO\n" +
                    "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n" +
                    "LEFT JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\n" +
                    "LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = prsp.ID_PROGETTO\n" +
                    "LEFT JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA\n" +
                    "LEFT JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO\n" +
                    "LEFT JOIN PBANDI_T_SOGGETTO pts ON prsp.ID_SOGGETTO = pts.ID_SOGGETTO\n" +
                    "LEFT JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP prblec ON prblec.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO\n" +
                    "LEFT JOIN denom ON denom.id_soggetto = pts.ID_SOGGETTO\n" +
                    "WHERE prblec.ID_RUOLO_ENTE_COMPETENZA = 1\n" +
                    "AND prblec.ID_ENTE_COMPETENZA = 2\n" +
                    "AND prsp.ID_TIPO_BENEFICIARIO <> 4\n" +
                    "AND prsp.ID_TIPO_ANAGRAFICA = 1\n");

            if (sugVO.getProgBandoLinea() != null) {
                sql.append("AND prbli.PROGR_BANDO_LINEA_INTERVENTO = ?\r\n");
            }
            if (sugVO.getIdSoggetto() != null) {
                sql.append("AND prsp.id_soggetto= ?\r\n");
            }
            if (sugVO.getValue().trim().length() > 0) {
                sql.append("AND UPPER(prbli.NOME_BANDO_LINEA) LIKE CONCAT('%', CONCAT(UPPER(?), '%'))\r\n");
            }
            sql.append(" AND ROWNUM<=100\r\n" +
                    "ORDER BY ptp.CODICE_VISUALIZZATO \n");

            logger.info(sql.toString());

            list = getJdbcTemplate().query(sql.toString(),
                    ps -> {
                        int k = 1;
                        if (sugVO.getProgBandoLinea() != null) {
                            ps.setBigDecimal(k++, sugVO.getProgBandoLinea());
                        }
                        if (sugVO.getIdSoggetto() != null) {
                            ps.setBigDecimal(k++, sugVO.getIdSoggetto());
                        }
                        if (sugVO.getValue().trim().length() > 0) {
                            ps.setString(k, sugVO.getValue());
                        }
                    },
                    (rs, rowNum) -> {
                        SuggestIterVO sug = new SuggestIterVO();

                        sug.setIdProgetto(rs.getBigDecimal("ID_PROGETTO"));
                        sug.setCodiceVisualizzato(rs.getString("CODICE_VISUALIZZATO"));

                        return sug;
                    });
        } catch (Exception e) {
            logger.error(e);
        }
        return list;


    }

    //SCENARI ALTERNATIVI GESTIONE ITER
    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneRendicontazioneDSSospesa(Long idWorkFlow, Integer idTipoDocIndex, List<Integer> idDocSecondari, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idWorkFlow=" + idWorkFlow + ", idTipoDocIndex=" + idTipoDocIndex + ", esito=" + esito);

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            String query =
                    "SELECT workFlowEntita.id_target\n" +
                            "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = workFlowEntita.id_entita\n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_INTEGRAZIONE_SPESA'\n" +
                            "AND workFlowEntita.id_work_flow = ?";
            Long idIntegrazioneSpesa = getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{idWorkFlow},
                    Long.class
            );
            LOG.info(prf + "idIntegrazioneSpesa=" + idIntegrazioneSpesa);

            if (esito) {
                //LETTERA PROTOCOLLATA IN DOQUI
                DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);
                String codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);
//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());

                //REPORT VALIDAZIONE e CHECKLIST PROTOCOLLAZIONE
                for (Integer idDocumentoIndex : idDocSecondari) {
                    datiActa = popolaDatiXActa(idWorkFlow, idDocumentoIndex, userInfoSec);
                    LOG.info(prf + "datiActa idDocIndex " + idDocumentoIndex + " = " + datiActa);
                    if (datiActa != null) {
                        codClassificazione = actaManager.classificaDocumento(datiActa);
                        LOG.info(prf + "codClassificazione idDocIndex " + idDocumentoIndex + " = " + codClassificazione);
//					if(codClassificazione!=null) {
//						datiActa.setCodiceClassificazione(codClassificazione);
//						codProtocollo = actaManager.protocollaDocumento(datiActa);
//						LOG.info(prf + "codProtocollo report validazione= " + codProtocollo);
//					}else {
//						LOG.warn(prf + "classificazione report validazione non riuscita,  codClassificazione="+codClassificazione);
//						throw new Exception ("classificazione report validazione non riuscita");
//					}
//
//					// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
					updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
                    }
                }
            }

            query = "UPDATE PBANDI_T_INTEGRAZIONE_SPESA SET ID_STATO_RICHIESTA = ? WHERE ID_INTEGRAZIONE_SPESA = ?";
            LOG.info(prf + "query = " + query);
            getJdbcTemplate().update(query, esito ? 1 : 5, idIntegrazioneSpesa);

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneRendicontazioneDSSospesa", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void autorizzaRendicontazioneChiusuraValidazione(Long idWorkFlow, Integer idTipoDocIndex, List<Integer> idDocSecondari, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            //LETTERA PROTOCOLLATA IN DOQUI
            DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
            LOG.info(prf + "datiActa= " + datiActa);
            String codClassificazione = actaManager.classificaDocumento(datiActa);
            LOG.info(prf + "codClassificazione= " + codClassificazione);
//			String codProtocollo;
//			if(codClassificazione!=null) {
//				datiActa.setCodiceClassificazione(codClassificazione);
//				codProtocollo = actaManager.protocollaDocumento(datiActa);
//				LOG.info(prf + "codProtocollo= " + codProtocollo);
//			}else {
//				LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//				throw new Exception ("classificazione non riuscita");
//			}
//			// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
			updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());

            //REPORT VALIDAZIONE E CHECKLIST VALIDAZIONE PROTOCOLLAZIONE
			for(Integer idDocumentoIndex : idDocSecondari) {
				datiActa = popolaDatiXActa(idWorkFlow, idDocumentoIndex, userInfoSec);
				LOG.info(prf + "datiActa idDocIndex " + idDocumentoIndex + " = " + datiActa);
				if (datiActa != null) {
					codClassificazione = actaManager.classificaDocumento(datiActa);
					LOG.info(prf + "codClassificazione report validazione= " + codClassificazione);
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo report validazione= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione report validazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione report validazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
				}
			}
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to autorizzaRendicontazioneChiusuraValidazione", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneCNTRichiestaIntegrazione(Long idWorkFlow, Integer idTipoDocIndex, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            if (esito) {

                LOG.info(prf + "userInfoSec= " + userInfoSec);

                //LETTERA PROTOCOLLATA IN DOQUI
                DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);

                String codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);

//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
            }

            //AGGIORNAMENTO PBANDI_T_RICHIESTA_INTEGRAZ
            String getIdTargetIntegraz =
                    "SELECT workFlowEntita.id_target\n" +
                            "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = workFlowEntita.id_entita\n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_RICHIESTA_INTEGRAZ'\n" +
                            "AND workFlowEntita.id_work_flow = ?";
            String getIdTarget =
                    "SELECT richiestaIntegraz.id_target\n" +
                            "FROM PBANDI_T_RICHIESTA_INTEGRAZ richiestaIntegraz\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = richiestaIntegraz.id_entita\n" +
                            "WHERE entita.nome_entita = ?\n" +
                            "AND richiestaIntegraz.id_richiesta_integraz = ?";
            Long idRichiestaIntegrazione = getJdbcTemplate().queryForObject(
                    getIdTargetIntegraz,
                    new Object[]{idWorkFlow},
                    Long.class
            );
            String query =
                    "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ SET \n" +
                            "ID_STATO_RICHIESTA = ?,\n" +
                            "ID_UTENTE_AGG = ?,\n" +
                            "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                            "WHERE ID_RICHIESTA_INTEGRAZ = ?";
            getJdbcTemplate().update(query,
                    esito ? 1 : 5,
                    userInfoSec.getIdUtente(),
                    idRichiestaIntegrazione);

            //CONTROLLO LOCO
            try {
                Long idControlloLoco = getJdbcTemplate().queryForObject(
                        getIdTarget,
                        new Object[]{"PBANDI_T_CONTROLLO_LOCO", idRichiestaIntegrazione},
                        Long.class
                );
                query =
                        "UPDATE PBANDI_T_CONTROLLO_LOCO SET \n" +
                                "ID_ATTIV_CONTR_LOCO = ?\n" +
                                "WHERE ID_CONTROLLO_LOCO = ?\n";
                getJdbcTemplate().update(
                        query,
                        esito ? 5 : 6,
                        idControlloLoco);
            } catch (EmptyResultDataAccessException ignored) {
            }
            //CONTROLLO LOCO ALTRI
            try {
                Long idControlloLocoAltri = getJdbcTemplate().queryForObject(
                        getIdTarget,
                        new Object[]{"PBANDI_T_CONTROLLO_LOCO_ALTRI", idRichiestaIntegrazione},
                        Long.class
                );
                query =
                        "UPDATE PBANDI_T_CONTROLLO_LOCO_ALTRI SET \n" +
                                "ID_ATTIV_CONTR_LOCO = ?\n" +
                                "WHERE ID_CONTROLLO = ?";
                getJdbcTemplate().update(query,
                        esito ? 5 : 6,
                        idControlloLocoAltri);
            } catch (EmptyResultDataAccessException ignored) {
            }

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneCNTRichiestaIntegrazione", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneCNTAvvioControllo(Long idWorkFlow, Integer idTipoDocIndex, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            if (esito) {
                LOG.info(prf + "userInfoSec= " + userInfoSec);

                //LETTERA PROTOCOLLATA IN DOQUI
                DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);

                String codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);

//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
            }

            //recupero idControlloLoco
            String getIdControlloLoco =
                    "SELECT ptwfe.ID_TARGET\n"
                            + "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n"
                            + "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptwfe.ID_ENTITA\n"
                            + "WHERE pce.NOME_ENTITA = ?\n"
                            + "AND ptwfe.id_work_flow = ?";


            try {

                Long idControlloLoco = getJdbcTemplate().queryForObject(
                        getIdControlloLoco,
                        new Object[]{"PBANDI_T_CONTROLLO_LOCO", idWorkFlow},
                        Long.class
                );

                //update
                String query =
                        "UPDATE PBANDI_T_CONTROLLO_LOCO controlloLoco SET\n"
                                + "ID_ATTIV_CONTR_LOCO = ?\n"
                                + "WHERE ID_CONTROLLO_LOCO = ?";

                //update
                getJdbcTemplate().update(query,
                        esito ? 2 : 3,
                        idControlloLoco);

            } catch (EmptyResultDataAccessException ignored) {
            }


            try {
                Long idControlloLoco = getJdbcTemplate().queryForObject(
                        getIdControlloLoco,
                        new Object[]{"PBANDI_T_CONTROLLO_LOCO_ALTRI", idWorkFlow},
                        Long.class
                );

                //update
                String query =
                        "UPDATE PBANDI_T_CONTROLLO_LOCO_ALTRI controlloLoco SET\n"
                                + "ID_ATTIV_CONTR_LOCO = ?\n"
                                + "WHERE ID_CONTROLLO = ?";


                getJdbcTemplate().update(query,
                        esito ? 2 : 3,
                        idControlloLoco);
            } catch (EmptyResultDataAccessException ignored) {
            }

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneCNTRichiestaIntegrazione", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneCNTEsitoPositivo(Long idWorkFlow, Integer idTipoDocLettera, Integer idTipoDocVerbale, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            if (esito) {
                LOG.info(prf + "userInfoSec= " + userInfoSec);

                //LETTERA PROTOCOLLATA IN DOQUI
                DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocLettera, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);

                String codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);

//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());

                //VERBALE DI CONTROLLO IN LOCO PER DOQUI
                datiActa = popolaDatiXActa(idWorkFlow, idTipoDocVerbale, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);

                codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);

//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
            }

            Long idControlloLoco = null;
            String query = null;

            //recupero idControlloLoco
            String getIdControlloLoco =
                    "SELECT ptwfe.ID_TARGET\n"
                            + "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n"
                            + "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptwfe.ID_ENTITA\n"
                            + "WHERE pce.NOME_ENTITA = ?\n"
                            + "AND ptwfe.id_work_flow = ?";
            try {
                idControlloLoco = getJdbcTemplate().queryForObject(
                        getIdControlloLoco,
                        new Object[]{"PBANDI_T_CONTROLLO_LOCO", idWorkFlow},
                        Long.class
                );
                //update
                query =
                        "UPDATE PBANDI_T_CONTROLLO_LOCO controlloLoco SET\n"
                                + "ID_ATTIV_CONTR_LOCO = ?\n"
                                + "WHERE ID_CONTROLLO_LOCO = ?";

            } catch (EmptyResultDataAccessException ignored) {
            }
            try {
                idControlloLoco = getJdbcTemplate().queryForObject(
                        getIdControlloLoco,
                        new Object[]{"PBANDI_T_CONTROLLO_LOCO_ALTRI", idWorkFlow},
                        Long.class
                );
                //update
                query =
                        "UPDATE PBANDI_T_CONTROLLO_LOCO_ALTRI controlloLoco SET\n"
                                + "ID_ATTIV_CONTR_LOCO = ?\n"
                                + "WHERE ID_CONTROLLO = ?";
            } catch (EmptyResultDataAccessException ignored) {
            }

            //update
            getJdbcTemplate().update(query, esito ? 8 : 9, idControlloLoco);
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneCNTEsitoPositivo", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneIntegrazioneProcRev(Long idWorkFlow, Integer idTipoDocIndex, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            String query;

            IdEntitaRichIntegrazVO idEntitaRichiestaIntegraz;
            query = "SELECT \n" +
                    "pce2.ID_ENTITA as ID_ENTITA_GESTIONE_REVOCA,\n" +
                    "ptgr.ID_GESTIONE_REVOCA,\n" +
                    "pce.ID_ENTITA as ID_ENTITA_RICHIESTA_INTEGRAZ,\n" +
                    "ptri.ID_RICHIESTA_INTEGRAZ \n" +
                    "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe  \n" +
                    "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptwfe.ID_ENTITA\n" +
                    "JOIN PBANDI_T_RICHIESTA_INTEGRAZ ptri ON ptri.ID_RICHIESTA_INTEGRAZ = ptwfe.ID_TARGET \n" +
                    "JOIN PBANDI_C_ENTITA pce2 ON pce2.ID_ENTITA = ptri.ID_ENTITA\n" +
                    "JOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptgr.ID_GESTIONE_REVOCA = ptri.ID_TARGET \n" +
                    "WHERE ptwfe.ID_WORK_FLOW = ?\n" +
                    "AND pce.NOME_ENTITA = 'PBANDI_T_RICHIESTA_INTEGRAZ'\n" +
                    "AND pce2.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'";
            idEntitaRichiestaIntegraz = getJdbcTemplate().queryForObject(query, (rs, rownum) -> {
                IdEntitaRichIntegrazVO dto = new IdEntitaRichIntegrazVO();

                dto.setIdEntitaGestioneRevoca(rs.getLong("ID_ENTITA_GESTIONE_REVOCA"));
                dto.setIdGestioneRevoca(rs.getLong("ID_GESTIONE_REVOCA"));
                dto.setIdEntitaRichiestaIntegraz(rs.getLong("ID_ENTITA_RICHIESTA_INTEGRAZ"));
                dto.setIdRichiestaIntegraz(rs.getLong("ID_RICHIESTA_INTEGRAZ"));

                return dto;
            }, idWorkFlow);

            if (esito) {
                LOG.info(prf + "userInfoSec= " + userInfoSec);

                //SPOSTO GLI ALLEGATI DALLA GESTIONE REVOCA ALLA RICHIESTA INTEGRAZIONE
                query = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET \n" +
                        "ID_ENTITA = ?,\n" +
                        "ID_TARGET = ?\n" +
                        "WHERE ID_DOCUMENTO_INDEX IN (\n" +
                        "\tSELECT UNIQUE documenti.id_documento_index AS idDocumento\n" +
                        "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "\tJOIN PBANDI_T_GESTIONE_REVOCA gestioneRevocaValida ON gestioneRevocaValida.numero_revoca = gestioneRevoca.numero_revoca AND gestioneRevocaValida.id_tipologia_revoca = 2 AND gestioneRevocaValida.dt_fine_validita IS NULL\n" +
                        "\tJOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_target = gestioneRevoca.id_gestione_revoca AND documenti.id_tipo_documento_index IN (45, 51)\n" +
                        "\tJOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index\n" +
                        "\tWHERE gestioneRevoca.id_gestione_revoca = ?\n" +
                        "\tAND gestioneRevoca.id_tipologia_revoca = 2 \n" +
                        ")";
                getJdbcTemplate().update(query, idEntitaRichiestaIntegraz.getIdEntitaRichiestaIntegraz(), idEntitaRichiestaIntegraz.getIdRichiestaIntegraz(), idEntitaRichiestaIntegraz.getIdEntitaGestioneRevoca(), idEntitaRichiestaIntegraz.getIdGestioneRevoca());


                //LETTERA PROTOCOLLATA IN DOQUI
                DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);

                String codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);

//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
            }

            //AGGIORNAMENTO PBANDI_T_GESTIONE_REVOCA
            if (esito) {
                //AGGIORNO T_GESTIONE_REVOCA
                query =
                        "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                                "DT_FINE_VALIDITA = CURRENT_DATE,\n" +
                                "ID_UTENTE_AGG = ?,\n" +
                                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                                "WHERE ID_GESTIONE_REVOCA = ?";
                getJdbcTemplate().update(query,
                        userInfoSec.getIdUtente(),
                        idEntitaRichiestaIntegraz.getIdGestioneRevoca());
                //NUOVO RECORD SU T_GESTIONE_REVOCA
                String getNewIdGestioneRevoca = "select SEQ_PBANDI_T_GESTIONE_REVOCA.nextval from dual";
                Long newIdGestioneRevoca = getJdbcTemplate().queryForObject(getNewIdGestioneRevoca, Long.class);

                String getNumGiorniScadenza = "SELECT ptri.NUM_GIORNI_SCADENZA FROM PBANDI_T_RICHIESTA_INTEGRAZ ptri \n" +
                        "JOIN PBANDI_C_ENTITA pce ON pce.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                        "WHERE ptri.ID_ENTITA = pce.ID_ENTITA AND ptri.ID_TARGET = ? AND ptri.ID_STATO_RICHIESTA = 4";
                Long numGiorniScadenza = getJdbcTemplate().queryForObject(getNumGiorniScadenza, Long.class, idEntitaRichiestaIntegraz.getIdGestioneRevoca());

                query =
                        "INSERT INTO PBANDI_T_GESTIONE_REVOCA (\n" +
                                "ID_GESTIONE_REVOCA,\n" +
                                "NUMERO_REVOCA,\n" +
                                "ID_TIPOLOGIA_REVOCA,\n" +
                                "ID_PROGETTO,\n" +
                                "ID_CAUSALE_BLOCCO,\n" +
                                "ID_CATEG_ANAGRAFICA,\n" +
                                "DT_GESTIONE,\n" +
                                "NUM_PROTOCOLLO,\n" +
                                "FLAG_ORDINE_RECUPERO,\n" +
                                "ID_MANCATO_RECUPERO,\n" +
                                "ID_STATO_REVOCA,\n" +
                                "DT_STATO_REVOCA,\n" +
                                "DT_NOTIFICA,\n" +
                                "GG_RISPOSTA,\n" +
                                "FLAG_PROROGA,\n" +
                                "IMP_DA_REVOCARE_CONTRIB,\n" +
                                "IMP_DA_REVOCARE_FINANZ,\n" +
                                "IMP_DA_REVOCARE_GARANZIA,\n" +
                                "FLAG_DETERMINA,\n" +
                                "ESTREMI,\n" +
                                "DT_DETERMINA,\n" +
                                "NOTE,\n" +
                                "ID_ATTIVITA_REVOCA,\n" +
                                "DT_ATTIVITA,\n" +
                                "ID_MOTIVO_REVOCA,\n" +
                                "FLAG_CONTRIB_REVOCA,\n" +
                                "FLAG_CONTRIB_MINOR_SPESE,\n" +
                                "FLAG_CONTRIB_DECURTAZ,\n" +
                                "FLAG_FINANZ_REVOCA,\n" +
                                "FLAG_FINANZ_MINOR_SPESE,\n" +
                                "FLAG_FINANZ_DECURTAZ,\n" +
                                "FLAG_GARANZIA_REVOCA,\n" +
                                "FLAG_GARANZIA_MINOR_SPESE,\n" +
                                "FLAG_GARANZIA_DECURTAZ,\n" +
                                "IMP_CONTRIB_REVOCA_NO_RECU,\n" +
                                "IMP_CONTRIB_REVOCA_RECU,\n" +
                                "IMP_CONTRIB_INTERESSI,\n" +
                                "IMP_FINANZ_REVOCA_NO_RECU,\n" +
                                "IMP_FINANZ_REVOCA_RECU,\n" +
                                "IMP_FINANZ_INTERESSI,\n" +
                                "IMP_GARANZIA_REVOCA_NO_RECU,\n" +
                                "IMP_GARANZIA_REVOCA_RECUPERO,\n" +
                                "IMP_GARANZIA_INTERESSI,\n" +
                                "COVAR,\n" +
                                "DT_INIZIO_VALIDITA,\n" +
                                "DT_FINE_VALIDITA,\n" +
                                "ID_UTENTE_INS,\n" +
                                "ID_UTENTE_AGG,\n" +
                                "DT_INSERIMENTO,\n" +
                                "DT_AGGIORNAMENTO\n" +
                                ") \n" +
                                "SELECT \n" +
                                "?,\n" +
                                "gestioneRevoca.NUMERO_REVOCA,\n" +
                                "gestioneRevoca.ID_TIPOLOGIA_REVOCA,\n" +
                                "gestioneRevoca.ID_PROGETTO,\n" +
                                "gestioneRevoca.ID_CAUSALE_BLOCCO,\n" +
                                "gestioneRevoca.ID_CATEG_ANAGRAFICA,\n" +
                                "gestioneRevoca.DT_GESTIONE, \n" +
                                "gestioneRevoca.NUM_PROTOCOLLO, \n" +
                                "gestioneRevoca.FLAG_ORDINE_RECUPERO,\n" +
                                "gestioneRevoca.ID_MANCATO_RECUPERO,\n" +
                                "?,\n" +
                                "CURRENT_DATE,\n" +
                                "NULL, \n" +
                                "?,\n" +
                                "gestioneRevoca.FLAG_PROROGA,\n" +
                                "gestioneRevoca.IMP_DA_REVOCARE_CONTRIB,\n" +
                                "gestioneRevoca.IMP_DA_REVOCARE_FINANZ,\n" +
                                "gestioneRevoca.IMP_DA_REVOCARE_GARANZIA,\n" +
                                "gestioneRevoca.FLAG_DETERMINA,\n" +
                                "gestioneRevoca.ESTREMI,\n" +
                                "gestioneRevoca.DT_DETERMINA,\n" +
                                "gestioneRevoca.NOTE,\n" +
                                "NULL,\n" +
                                "NULL,\n" +
                                "gestioneRevoca.ID_MOTIVO_REVOCA,\n" +
                                "gestioneRevoca.FLAG_CONTRIB_REVOCA,\n" +
                                "gestioneRevoca.FLAG_CONTRIB_MINOR_SPESE,\n" +
                                "gestioneRevoca.FLAG_CONTRIB_DECURTAZ,\n" +
                                "gestioneRevoca.FLAG_FINANZ_REVOCA,\n" +
                                "gestioneRevoca.FLAG_FINANZ_MINOR_SPESE,\n" +
                                "gestioneRevoca.FLAG_FINANZ_DECURTAZ,\n" +
                                "gestioneRevoca.FLAG_GARANZIA_REVOCA,\n" +
                                "gestioneRevoca.FLAG_GARANZIA_MINOR_SPESE,\n" +
                                "gestioneRevoca.FLAG_GARANZIA_DECURTAZ,\n" +
                                "gestioneRevoca.IMP_CONTRIB_REVOCA_NO_RECU,\n" +
                                "gestioneRevoca.IMP_CONTRIB_REVOCA_RECU,\n" +
                                "gestioneRevoca.IMP_CONTRIB_INTERESSI,\n" +
                                "gestioneRevoca.IMP_FINANZ_REVOCA_NO_RECU,\n" +
                                "gestioneRevoca.IMP_FINANZ_REVOCA_RECU,\n" +
                                "gestioneRevoca.IMP_FINANZ_INTERESSI,\n" +
                                "gestioneRevoca.IMP_GARANZIA_REVOCA_NO_RECU,\n" +
                                "gestioneRevoca.IMP_GARANZIA_REVOCA_RECUPERO,\n" +
                                "gestioneRevoca.IMP_GARANZIA_INTERESSI,\n" +
                                "gestioneRevoca.COVAR,\n" +
                                "CURRENT_DATE, \n" +
                                "NULL,\n" +
                                "?,\n" +
                                "NULL,\n" +
                                "CURRENT_DATE,\n" +
                                "NULL\n" +
                                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                                "WHERE gestioneRevoca.id_gestione_revoca = ?";

                getJdbcTemplate().update(query,
                        newIdGestioneRevoca,
                        7, numGiorniScadenza, userInfoSec.getIdUtente(), idEntitaRichiestaIntegraz.getIdGestioneRevoca());
                //AGGIORNO T_RICHIESTA_INTEGRAZ
                query =
                        "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ SET \n" +
                                "ID_TARGET = ?,\n" +
                                "ID_STATO_RICHIESTA = 1,\n" +
                                "ID_UTENTE_AGG = ?,\n" +
                                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                                "WHERE ID_RICHIESTA_INTEGRAZ = ?";
                getJdbcTemplate().update(query,
                        newIdGestioneRevoca,
                        userInfoSec.getIdUtente(),
                        idEntitaRichiestaIntegraz.getIdRichiestaIntegraz());
            } else {
                //AGGIORNO T_GESTIONE_REVOCA
                query =
                        "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                                "ID_ATTIVITA_REVOCA = 17,\n" +
                                "DT_ATTIVITA = CURRENT_DATE,\n" +
                                "ID_UTENTE_AGG = ?,\n" +
                                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                                "WHERE ID_GESTIONE_REVOCA = ?";
                getJdbcTemplate().update(query,
                        userInfoSec.getIdUtente(),
                        idEntitaRichiestaIntegraz.getIdGestioneRevoca());

                //Aggiorno monitoraggio
                query =
                        "UPDATE PBANDI_T_MONIT_TEMPI SET \n" +
                                "DT_FINE = NULL\n" +
                                "WHERE ID_MONIT_TEMPI IN (\n" +
                                "\tSELECT * FROM (\n" +
                                "\t\tSELECT ptmt.id_monit_tempi\n" +
                                "\t\tFROM PBANDI_T_MONIT_TEMPI ptmt \n" +
                                "\t\tJOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptmt.ID_ENTITA \n" +
                                "\t\tWHERE pce.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                                "\t\tAND ptmt.DT_FINE IS NULL\n" +
                                "\t\tAND ptmt.ID_TARGET = ?\n" +
                                "\t\tORDER BY ID_MONIT_TEMPI DESC\n" +
                                "\t) WHERE rownum = 1\n" +
                                ")";
                getJdbcTemplate().update(query,
                        idEntitaRichiestaIntegraz.getIdGestioneRevoca());

                //AGGIORNO T_RICHIESTA_INTEGRAZ
                query =
                        "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ SET \n" +
                                "ID_STATO_RICHIESTA = 5,\n" +
                                "ID_UTENTE_AGG = ?,\n" +
                                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                                "WHERE ID_RICHIESTA_INTEGRAZ = ?";
                getJdbcTemplate().update(query,
                        userInfoSec.getIdUtente(),
                        idEntitaRichiestaIntegraz.getIdRichiestaIntegraz());
            }
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneIntegrazioneProcRev", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneRevoche(Long idWorkFlow, Integer idTipoDocIndex, Integer idTipoDocIndexSecondario, Boolean esito, Boolean provv, Boolean doqui, Integer idStato, Boolean monitoraggio, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        try {
            DatiXActaDTO datiActa = null;
            Long idDocumentoIndexLettera = null;

            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            if (esito) {
                LOG.info(prf + "userInfoSec= " + userInfoSec);

                //LETTERA PROTOCOLLATA IN DOQUI
                datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
                LOG.info(prf + " datiActa da popolaDatiXActa: " + datiActa);

                String codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);

//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception("classificazione non riuscita");
//				}
                
				//aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
                idDocumentoIndexLettera = datiActa.getIdDocIndex();

                //ALLEGATO SECONDARIO PROTOCOLLATO IN DOQUI
                if (idTipoDocIndexSecondario != null) {
                	LOG.info(prf + " Aggiungo allegato secondario protocollato");
                    datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndexSecondario, userInfoSec);
                    LOG.info(prf + "datiActa= " + datiActa);

                    if (datiActa != null) {
                        codClassificazione = actaManager.classificaDocumento(datiActa);
                        LOG.info(prf + "codClassificazione= " + codClassificazione);

//						if (codClassificazione != null) {
//							datiActa.setCodiceClassificazione(codClassificazione);
//							codProtocollo = actaManager.protocollaDocumento(datiActa);
//							LOG.info(prf + "codProtocollo= " + codProtocollo);
//						} else {
//							LOG.warn(prf + "classificazione non riuscita,  codClassificazione=" + codClassificazione);
//							throw new Exception("classificazione non riuscita");
//						}
//						//aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
						updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
                    }
                } else {
                	LOG.info(prf + " NON aggiungo nessun allegato secondario");
				}
            }

            //AGGIORNAMENTO PBANDI_T_GESTIONE_REVOCA
            String getIdTarget =
                    "SELECT workFlowEntita.id_target\n" +
                            "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = workFlowEntita.id_entita\n" +
                            "WHERE entita.nome_entita = ?\n" +
                            "AND workFlowEntita.id_work_flow = ?";
            Long idGestioneRevoca = getJdbcTemplate().queryForObject(
                    getIdTarget,
                    new Object[]{"PBANDI_T_GESTIONE_REVOCA", idWorkFlow},
                    Long.class
            );

            if (esito) {
                //AGGIORNO T_GESTIONE_REVOCA
                String query =
                        "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                                "DT_FINE_VALIDITA = CURRENT_DATE,\n" +
                                "ID_UTENTE_AGG = ?,\n" +
                                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                                "WHERE ID_GESTIONE_REVOCA = ?";
                getJdbcTemplate().update(query,
                        userInfoSec.getIdUtente(),
                        idGestioneRevoca);
                //NUOVO RECORD SU T_GESTIONE_REVOCA
                Object[] object = new Object[2]; //1 - numProtocollo, 2 - dtProtocollo
                if (doqui) {
                    query =
                            "SELECT \n" +
                                    "docProtocollo.num_protocollo, \n" +
                                    "docProtocollo.dt_protocollo\n" +
                                    "FROM PBANDI_T_DOC_PROTOCOLLO docProtocollo\n" +
                                    "WHERE docProtocollo.id_documento_index = ?";
                    try {
                        if (idDocumentoIndexLettera != null) {
                            object = getJdbcTemplate().queryForObject(query,
                                    (rs, rowNum) -> {
                                        Object[] objects = new Object[2];

                                        objects[0] = rs.getDate("dt_protocollo");
                                        objects[1] = rs.getString("num_protocollo");

                                        return objects;
                                    }, idDocumentoIndexLettera);
                        }
                    } catch (EmptyResultDataAccessException ignored) {
                    }
                }

                String getNewIdGestioneRevoca = "select SEQ_PBANDI_T_GESTIONE_REVOCA.nextval from dual";
                Long newIdGestioneRevoca = getJdbcTemplate().queryForObject(getNewIdGestioneRevoca, Long.class);

                query =
                        "INSERT INTO PBANDI_T_GESTIONE_REVOCA (\n" +
                                "ID_GESTIONE_REVOCA,\n" +
                                "NUMERO_REVOCA,\n" +
                                "ID_TIPOLOGIA_REVOCA,\n" +
                                "ID_PROGETTO,\n" +
                                "ID_CAUSALE_BLOCCO,\n" +
                                "ID_CATEG_ANAGRAFICA,\n" +
                                "DT_GESTIONE,\n" +
                                "NUM_PROTOCOLLO,\n" +
                                "FLAG_ORDINE_RECUPERO,\n" +
                                "ID_MANCATO_RECUPERO,\n" +
                                "ID_STATO_REVOCA,\n" +
                                "DT_STATO_REVOCA,\n" +
                                "DT_NOTIFICA, \n" +
                                "GG_RISPOSTA,\n" +
                                "FLAG_PROROGA,\n" +
                                "IMP_DA_REVOCARE_CONTRIB,\n" +
                                "IMP_DA_REVOCARE_FINANZ,\n" +
                                "IMP_DA_REVOCARE_GARANZIA,\n" +
                                "FLAG_DETERMINA,\n" +
                                "ESTREMI,\n" +
                                "DT_DETERMINA,\n" +
                                "NOTE,\n" +
                                "ID_ATTIVITA_REVOCA,\n" +
                                "DT_ATTIVITA,\n" +
                                "ID_MOTIVO_REVOCA,\n" +
                                "FLAG_CONTRIB_REVOCA,\n" +
                                "FLAG_CONTRIB_MINOR_SPESE,\n" +
                                "FLAG_CONTRIB_DECURTAZ,\n" +
                                "FLAG_FINANZ_REVOCA,\n" +
                                "FLAG_FINANZ_MINOR_SPESE,\n" +
                                "FLAG_FINANZ_DECURTAZ,\n" +
                                "FLAG_GARANZIA_REVOCA,\n" +
                                "FLAG_GARANZIA_MINOR_SPESE,\n" +
                                "FLAG_GARANZIA_DECURTAZ,\n" +
                                "IMP_CONTRIB_REVOCA_NO_RECU,\n" +
                                "IMP_CONTRIB_REVOCA_RECU,\n" +
                                "IMP_CONTRIB_INTERESSI,\n" +
                                "IMP_FINANZ_REVOCA_NO_RECU,\n" +
                                "IMP_FINANZ_REVOCA_RECU,\n" +
                                "IMP_FINANZ_INTERESSI,\n" +
                                "IMP_GARANZIA_REVOCA_NO_RECU,\n" +
                                "IMP_GARANZIA_REVOCA_RECUPERO,\n" +
                                "IMP_GARANZIA_INTERESSI,\n" +
                                "COVAR,\n" +
                                "DT_INIZIO_VALIDITA,\n" +
                                "DT_FINE_VALIDITA,\n" +
                                "ID_UTENTE_INS,\n" +
                                "ID_UTENTE_AGG,\n" +
                                "DT_INSERIMENTO,\n" +
                                "DT_AGGIORNAMENTO\n" +
                                ") \n" +
                                "SELECT \n" +
                                "?,\n" +
                                "gestioneRevoca.NUMERO_REVOCA,\n" +
                                "gestioneRevoca.ID_TIPOLOGIA_REVOCA,\n" +
                                "gestioneRevoca.ID_PROGETTO,\n" +
                                "gestioneRevoca.ID_CAUSALE_BLOCCO,\n" +
                                "gestioneRevoca.ID_CATEG_ANAGRAFICA,\n" +
                                (doqui ? "?,\n?,\n" : "gestioneRevoca.DT_GESTIONE, \ngestioneRevoca.NUM_PROTOCOLLO, \n") +
                                "gestioneRevoca.FLAG_ORDINE_RECUPERO,\n" +
                                "gestioneRevoca.ID_MANCATO_RECUPERO,\n" +
                                "?,\n" +
                                "CURRENT_DATE,\n" +
                                (idStato == 4 || idStato == 9 || idStato == 10 ? "gestioneRevoca.DT_NOTIFICA,\n" : "NULL,\n") +
                                "gestioneRevoca.GG_RISPOSTA,\n" +
                                "gestioneRevoca.FLAG_PROROGA,\n" +
                                "gestioneRevoca.IMP_DA_REVOCARE_CONTRIB,\n" +
                                "gestioneRevoca.IMP_DA_REVOCARE_FINANZ,\n" +
                                "gestioneRevoca.IMP_DA_REVOCARE_GARANZIA,\n" +
                                "gestioneRevoca.FLAG_DETERMINA,\n" +
                                "gestioneRevoca.ESTREMI,\n" +
                                "gestioneRevoca.DT_DETERMINA,\n" +
                                "gestioneRevoca.NOTE,\n" +
                                "NULL,\n" +
                                "NULL,\n" +
                                "gestioneRevoca.ID_MOTIVO_REVOCA,\n" +
                                "gestioneRevoca.FLAG_CONTRIB_REVOCA,\n" +
                                "gestioneRevoca.FLAG_CONTRIB_MINOR_SPESE,\n" +
                                "gestioneRevoca.FLAG_CONTRIB_DECURTAZ,\n" +
                                "gestioneRevoca.FLAG_FINANZ_REVOCA,\n" +
                                "gestioneRevoca.FLAG_FINANZ_MINOR_SPESE,\n" +
                                "gestioneRevoca.FLAG_FINANZ_DECURTAZ,\n" +
                                "gestioneRevoca.FLAG_GARANZIA_REVOCA,\n" +
                                "gestioneRevoca.FLAG_GARANZIA_MINOR_SPESE,\n" +
                                "gestioneRevoca.FLAG_GARANZIA_DECURTAZ,\n" +
                                "gestioneRevoca.IMP_CONTRIB_REVOCA_NO_RECU,\n" +
                                "gestioneRevoca.IMP_CONTRIB_REVOCA_RECU,\n" +
                                "gestioneRevoca.IMP_CONTRIB_INTERESSI,\n" +
                                "gestioneRevoca.IMP_FINANZ_REVOCA_NO_RECU,\n" +
                                "gestioneRevoca.IMP_FINANZ_REVOCA_RECU,\n" +
                                "gestioneRevoca.IMP_FINANZ_INTERESSI,\n" +
                                "gestioneRevoca.IMP_GARANZIA_REVOCA_NO_RECU,\n" +
                                "gestioneRevoca.IMP_GARANZIA_REVOCA_RECUPERO,\n" +
                                "gestioneRevoca.IMP_GARANZIA_INTERESSI,\n" +
                                "gestioneRevoca.COVAR,\n" +
                                "CURRENT_DATE, \n" +
                                "NULL,\n" +
                                "?,\n" +
                                "NULL,\n" +
                                "CURRENT_DATE,\n" +
                                "NULL\n" +
                                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                                "WHERE gestioneRevoca.id_gestione_revoca = ?";

                if (doqui) {
                    getJdbcTemplate().update(query,
                            newIdGestioneRevoca, object[0], object[1],
                            idStato, userInfoSec.getIdUtente(), idGestioneRevoca);
                } else {
                    getJdbcTemplate().update(query,
                            newIdGestioneRevoca,
                            idStato, userInfoSec.getIdUtente(), idGestioneRevoca);
                }
            } else {
                //AGGIORNO T_GESTIONE_REVOCA
                String query =
                        "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                                "ID_ATTIVITA_REVOCA = ?,\n" +
                                "DT_ATTIVITA = CURRENT_DATE,\n" +
                                "ID_UTENTE_AGG = ?,\n" +
                                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                                "WHERE ID_GESTIONE_REVOCA = ?";
                getJdbcTemplate().update(query,
                        provv ? 18 : 17,
                        userInfoSec.getIdUtente(),
                        idGestioneRevoca);
                //se neccessario riattivo il monitoraggio
                if (monitoraggio) {
                    query =
                            "UPDATE PBANDI_T_MONIT_TEMPI SET \n" +
                                    "DT_FINE = NULL\n" +
                                    "WHERE ID_TARGET = ?\n" +
                                    "AND ID_ENTITA IN (\n" +
                                    "\tSELECT pce.ID_ENTITA \n" +
                                    "\tFROM PBANDI_C_ENTITA pce \n" +
                                    "\tWHERE pce.NOME_ENTITA = 'PBANDI_T_GESTIONE_REVOCA'\n" +
                                    ")";
                    getJdbcTemplate().update(query,
                            idGestioneRevoca);
                }
            }

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneRevoche", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneErogazione(Long idWorkFlow, Integer idTipoDocIndex, Integer idTipoDocIndexSecondario, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            if (esito) {
                String query =
                        "SELECT tipoDistinta.id_tipo_distinta\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = workFlowEntita.id_entita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workFlowEntita.id_target\n" +
                                "JOIN PBANDI_D_TIPO_DISTINTA tipoDistinta ON tipoDistinta.id_tipo_distinta = distinta.id_tipo_distinta\n" +
                                "WHERE entita.nome_entita = 'PBANDI_T_DISTINTA'\n" +
                                "AND workFlowEntita.id_work_flow = ?";

                int tipoDistinta = getJdbcTemplate().queryForObject(query, new Object[]{idWorkFlow}, Integer.class);

                List<DistintaErogazioneVO> distintaErogazioneVOList;
                Long idMonitAmmvoCont;
                boolean esitoAmm;
                StatoDistinte statoDistinte;
                Integer idDistinta = null;
                switch (tipoDistinta) {
                    case 1:
                        query = "SELECT\n" +
                                "escussione.*,\n" +
                                "distinta.id_distinta AS idDistinta,\n" +
                                "distintaDett.id_distinta_dett AS idDistintaDett,\n" +
                                "distintaDett.riga_distinta AS rigaDistinta,\n" +
                                "progetto.id_progetto AS idProgetto,\n" +
                                "bandoLinea.id_bando AS idBando,\n" +
                                "modAgev.id_modalita_agevolazione AS idModalitaAgevolazione,\n" +
                                "modAgev.id_modalita_agevolazione_rif AS idModalitaAgevolazioneRif,\n" +
                                "escussione.DT_INIZIO_VALIDITA  AS dtInizioValidita,\n" +
                                "escussione.IMP_APPROVATO  AS importoApprovato,\n" +
                                "escussione.ID_TIPO_ESCUSSIONE AS idTipoEscussione, \n" +
                                "escussione.IBAN_BONIFICO AS ibanBeneficiario ,\n" +
                                "escussione.CAUSALE_BONIFICO AS causaleBonifico\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_C_ENTITA entitaDistinta ON entitaDistinta.id_entita = workFlowEntita.id_entita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workFlowEntita.id_target\n" +
                                "JOIN PBANDI_T_DISTINTA_DETT distintaDett ON distintaDett.id_distinta = distinta.id_distinta\n" +
                                "JOIN PBANDI_C_ENTITA entitaPropostaErogazione ON entitaPropostaErogazione.id_entita = distinta.id_entita\n" +
                                "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = distintaDett.id_target\n" +
                                "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev on modAgev.id_modalita_agevolazione = propostaErogazione.id_modalita_agevolazione\n" +
                                "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto\n" +
                                "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnteComp ON bandoLineaEnteComp.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento\n" +
                                "JOIN PBANDI_T_ESCUSSIONE escussione ON escussione.id_proposta_erogazione = propostaErogazione.id_proposta\n" +
                                "WHERE\n" +
                                "entitaDistinta.nome_entita = 'PBANDI_T_DISTINTA'\n" +
                                "AND entitaPropostaErogazione.nome_entita = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                                "AND bandoLineaEnteComp.id_ente_competenza = 2\n" +
                                "AND bandoLineaEnteComp.id_ruolo_ente_competenza = 1\n" +
                                "AND modAgev.id_modalita_agevolazione_rif = 10\n" +
                                "AND workFlowEntita.id_work_flow = ?";


                        RowMapper<DistintaErogazioneVO> rowMapperEscussione = (rs, rowNum) -> {

                            DistintaErogazioneVO distinta = new DistintaErogazioneVO();

                            distinta.setDataErogazione(rs.getDate("dtInizioValidita"));
                            distinta.setIdBando(rs.getInt("idBando"));
                            distinta.setIdProgetto(rs.getInt("idProgetto"));
                            distinta.setIdDistinta(rs.getInt("idDistinta"));
                            distinta.setIdDistintaDett(rs.getInt("idDistintaDett"));
                            distinta.setRigaDistinta(rs.getInt("rigaDistinta"));
                            distinta.setImporto(rs.getDouble("importoApprovato"));
                            distinta.setIdTipoEscussione(rs.getInt("idTipoEscussione"));
                            distinta.setIdModalitaAgevolazione(rs.getInt("idModalitaAgevolazione"));
                            distinta.setIdModalitaAgevolazioneRif(rs.getInt("idModalitaAgevolazioneRif"));
                            distinta.setIbanBonifico(rs.getString("ibanBeneficiario"));
                            distinta.setCausaleBonifico(rs.getString("causaleBonifico"));

                            return distinta;
                        };

                        distintaErogazioneVOList = getJdbcTemplate().query(
                                query,
                                new Object[]{idWorkFlow},
                                rowMapperEscussione
                        );

                        for (DistintaErogazioneVO distintaErogazioneVO : distintaErogazioneVOList) {
                            idDistinta = distintaErogazioneVO.getIdDistinta();
                            idMonitAmmvoCont = amministrativoContabileService.callToDistintaErogazioneEscussione(
                                    distintaErogazioneVO.getIdDistinta(),
                                    distintaErogazioneVO.getRigaDistinta(),
                                    distintaErogazioneVO.getIdProgetto(),
                                    distintaErogazioneVO.getIdBando(),
                                    distintaErogazioneVO.getIbanBonifico(),
                                    distintaErogazioneVO.getDataErogazione(),
                                    distintaErogazioneVO.getImporto(),
                                    distintaErogazioneVO.getIdTipoEscussione(),
                                    distintaErogazioneVO.getCausaleBonifico(),
                                    distintaErogazioneVO.getIdModalitaAgevolazione(),
                                    distintaErogazioneVO.getIdModalitaAgevolazioneRif(),
                                    userInfoSec.getIdUtente()
                            );
                            //CONTROLLO L'ESITO DELLA CHIAMATA
                            try {

                                esitoAmm = "OK".equals(getJdbcTemplate().queryForObject(
                                		QUERY_ESITO_CHIAMATA_SERV,
                                        new Object[]{idMonitAmmvoCont},
                                        String.class
                                ));
                            } catch (IncorrectResultSizeDataAccessException e) {
                                esitoAmm = false;
                            }
                            //SE ESITO OK ALLORA FACCIO LA CHIAMATA ALLO STATO DISTINTE E AGGIORNO LO STATO
                            if (esitoAmm) {
                                statoDistinte = amministrativoContabileService.calltoStatoDistinte(
                                        distintaErogazioneVO.getIdDistinta(),
                                        distintaErogazioneVO.getRigaDistinta(),
                                        userInfoSec.getIdUtente());
                                if (statoDistinte != null) {
                                    query = "UPDATE PBANDI_T_DISTINTA SET \n" +
                                            "ID_STATO_DISTINTA = (\n" +
                                            "\tSELECT pdsd.ID_STATO_DISTINTA \n" +
                                            "\tFROM PBANDI_D_STATO_DISTINTA pdsd \n" +
                                            "\tWHERE pdsd.ID_STATO_DISTINTA_AMVVO = ?\n" +
                                            "),\n" +
                                            "DT_AGGIORNAMENTO = ?,\n" +
                                            "ID_UTENTE_AGG = ?\n" +
                                            "WHERE ID_DISTINTA = ?";
                                    getJdbcTemplate().update(query, statoDistinte.getStato(), statoDistinte.getDataStato(), userInfoSec.getIdUtente(), statoDistinte.getIdDistinta());
                                } else {
                                    //cosa faccio se stato distinta è null?
                                    LOG.info("Stato distinta null!");
                                }

                                idDistinta = distintaErogazioneVO.getIdDistinta();
                            }
                        }
                        break;
                    case 6:
                        query = "SELECT \n" +
                                "distinta.id_distinta AS idDistinta,\n" +
                                "distintaDett.id_distinta_dett AS idDistintaDett,\n" +
                                "distintaDett.riga_distinta AS rigaDistinta,\n" +
                                "distinta.dt_inserimento AS dataErogazione,\n" +
                                "propostaErogazione.imp_lordo AS importo,\n" +
                                "propostaErogazione.imp_ires AS importoIres,\n" +
                                "propostaErogazione.ID_CAUSALE_EROGAZIONE AS idCausaleErogazione,\n" + // Aggiunto come nuova specifica di amm.vo cont.
                                "destIntervent.iban AS iban,\n" +
                                "progetto.id_progetto AS idProgetto,\n" +
                                "bandoLinea.id_bando AS idBando,\n" +
                                "modAgev.id_modalita_agevolazione AS idModalitaAgevolazione,\n" +
                                "modAgev.id_modalita_agevolazione_rif AS idModalitaAgevolazioneRif\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_C_ENTITA entitaDistinta ON entitaDistinta.id_entita = workFlowEntita.id_entita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workFlowEntita.id_target\n" +
                                "JOIN PBANDI_T_DISTINTA_DETT distintaDett ON distintaDett.id_distinta = distinta.id_distinta\n" +
                                "JOIN PBANDI_C_ENTITA entitaPropostaErogazione ON entitaPropostaErogazione.id_entita = distinta.id_entita\n" +
                                "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = distintaDett.id_target\n" +
                                "LEFT JOIN PBANDI_D_DEST_INTERV_SOSTITUT destIntervent ON destIntervent.id_destinatario_intervento = propostaErogazione.id_destinatario_intervento\n" +
                                "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev on modAgev.ID_MODALITA_AGEVOLAZIONE = propostaErogazione.ID_MODALITA_AGEVOLAZIONE\n" +
                                "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto\n" +
                                "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnteComp ON bandoLineaEnteComp.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                                "WHERE entitaDistinta.nome_entita = 'PBANDI_T_DISTINTA'\n" +
                                "AND entitaPropostaErogazione.nome_entita = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                                "AND bandoLineaEnteComp.id_ente_competenza = 2\n" +
                                "AND bandoLineaEnteComp.id_ruolo_ente_competenza = 1 \n" +
                                "AND modAgev.id_modalita_agevolazione_rif = 1\n" +
                                "AND workFlowEntita.id_work_flow = ? ";
                        distintaErogazioneVOList = getJdbcTemplate().query(
                                query,
                                new Object[]{idWorkFlow},
                                new DistintaErogazioneVORowMapper()
                        );
                        for (DistintaErogazioneVO distintaErogazioneVO : distintaErogazioneVOList) {
                            idDistinta = distintaErogazioneVO.getIdDistinta();
                            idMonitAmmvoCont = amministrativoContabileService.callToDistintaErogazioneContributo(
                                    distintaErogazioneVO.getIdDistinta(),
                                    distintaErogazioneVO.getRigaDistinta(),
                                    distintaErogazioneVO.getIdDistintaDett(),
                                    distintaErogazioneVO.getIdProgetto(),
                                    distintaErogazioneVO.getIdBando(),
                                    distintaErogazioneVO.getDataErogazione(),
                                    distintaErogazioneVO.getImporto(),
                                    distintaErogazioneVO.getImportoIres(),
                                    distintaErogazioneVO.getIdCausaleErogazione(), // Nuova specifica di amm.vo cont.
                                    distintaErogazioneVO.getIbanBonifico(),
                                    distintaErogazioneVO.getIdModalitaAgevolazione(),
                                    distintaErogazioneVO.getIdModalitaAgevolazioneRif(),
                                    userInfoSec.getIdUtente()
                            );
                            //CONTROLLO L'ESITO DELLA CHIAMATA
                            try {
                                esitoAmm = "OK".equals(getJdbcTemplate().queryForObject(
                                		QUERY_ESITO_CHIAMATA_SERV,
                                        new Object[]{idMonitAmmvoCont},
                                        String.class
                                ));
                            } catch (IncorrectResultSizeDataAccessException e) {
                                esitoAmm = false;
                            }
                            //SE ESITO OK ALLORA FACCIO LA CHIAMATA ALLO STATO DISTINTE E AGGIORNO LO STATO
                            if (esitoAmm) {
                                statoDistinte = amministrativoContabileService.calltoStatoDistinte(
                                        distintaErogazioneVO.getIdDistinta(),
                                        distintaErogazioneVO.getRigaDistinta(),
                                        userInfoSec.getIdUtente());
                                if (statoDistinte != null) {
                                    query = "UPDATE PBANDI_T_DISTINTA SET \n" +
                                            "ID_STATO_DISTINTA = (\n" +
                                            "\tSELECT pdsd.ID_STATO_DISTINTA \n" +
                                            "\tFROM PBANDI_D_STATO_DISTINTA pdsd \n" +
                                            "\tWHERE pdsd.ID_STATO_DISTINTA_AMVVO = ?\n" +
                                            "),\n" +
                                            "DT_AGGIORNAMENTO = ?,\n" +
                                            "ID_UTENTE_AGG = ?\n" +
                                            "WHERE ID_DISTINTA = ?";
                                    getJdbcTemplate().update(query, statoDistinte.getStato(), statoDistinte.getDataStato(), userInfoSec.getIdUtente(), statoDistinte.getIdDistinta());
                                } else {
                                    //cosa faccio se stato distinta è null?
                                    LOG.info("Stato distinta null!");
                                }

                                idDistinta = distintaErogazioneVO.getIdDistinta();
                            }
                        }
                        break;
                    case 7:
                        query = "SELECT \n" +
                                "distinta.id_distinta AS idDistinta,\n" +
                                "distintaDett.id_distinta_dett AS idDistintaDett,\n" +
                                "distintaDett.riga_distinta AS rigaDistinta,\n" +
                                "distinta.dt_inserimento AS dataErogazione,\n" +
                                "propostaErogazione.imp_lordo AS importo,\n" +
                                "ammortamento.mm_ammortamento AS numeroRate,\n" +
                                "frequenza.descr_breve_frequenza_ammort AS frequenzaRate,\n" +
                                "tipoPeriodo.descr_breve_tipo_period_amm AS tipoPeriodo, \n" +
                                "ammortamento.perc_interessi AS percentualeInteressi,\n" +
                                "propostaErogazione.imp_ires AS importoIres,\n" +
                                "propostaErogazione.ID_CAUSALE_EROGAZIONE AS idCausaleErogazione,\n" + // Aggiunto per nuova specifica di amm.vo cont.
                                "ammortamento.mm_preammortamento AS preammortamento,\n" +
                                "progetto.id_progetto AS idProgetto,\n" +
                                "bandoLinea.id_bando AS idBando,\n" +
                                "modAgev.id_modalita_agevolazione AS idModalitaAgevolazione,\n" +
                                "modAgev.id_modalita_agevolazione_rif AS idModalitaAgevolazioneRif\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_C_ENTITA entitaDistinta ON entitaDistinta.id_entita = workFlowEntita.id_entita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workFlowEntita.id_target\n" +
                                "JOIN PBANDI_T_DISTINTA_DETT distintaDett ON distintaDett.id_distinta = distinta.id_distinta\n" +
                                "JOIN PBANDI_C_ENTITA entitaPropostaErogazione ON entitaPropostaErogazione.id_entita = distinta.id_entita\n" +
                                "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = distintaDett.id_target\n" +
                                "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev on modAgev.ID_MODALITA_AGEVOLAZIONE = propostaErogazione.ID_MODALITA_AGEVOLAZIONE\n" +
                                "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto\n" +
                                "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnteComp ON bandoLineaEnteComp.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                                "JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.id_estremi_bancari = distinta.id_estremi_bancari\n" +
                                "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto\n" +
                                "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto\n" +
                                "JOIN PBANDI_T_ESTREMI_BANCARI estremiBancariBeneficiario ON estremiBancariBeneficiario.id_estremi_bancari = soggettoProgetto.id_estremi_bancari\n" +
                                "JOIN PBANDI_T_DELIBERA_PROGETTI deliberaProgetti ON deliberaProgetti.id_progetto = progetto.id_progetto\n" +
                                "JOIN PBANDI_T_AMMORTAMENTO_PROGETTI ammortamento ON ammortamento.id_delibera = deliberaProgetti.id_delibera AND ammortamento.dt_fine_validita IS NULL\n" +
                                "JOIN PBANDI_D_TIPO_PERIODO_AMMORT tipoPeriodo ON tipoPeriodo.ID_TIPO_PERIODO_AMMORTAMENTO = ammortamento.ID_TIPO_PERIODO_AMMORTAMENTO\n" +
                                "JOIN PBANDI_D_FREQUENZA_AMMORT frequenza ON frequenza.ID_FREQUENZA_AMMORTAMENTO = ammortamento.ID_FREQUENZA_AMMORTAMENTO\n" +
                                "WHERE entitaDistinta.nome_entita = 'PBANDI_T_DISTINTA'\n" +
                                "AND entitaPropostaErogazione.nome_entita = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                                "AND bandoLineaEnteComp.id_ente_competenza = 2\n" +
                                "AND bandoLineaEnteComp.id_ruolo_ente_competenza = 1 \n" +
                                "AND soggettoProgetto.id_tipo_anagrafica = 1\n" +
                                "AND soggettoProgetto.id_tipo_beneficiario <> 4\n" +
                                "AND modAgev.id_modalita_agevolazione_rif = 5\n" +
                                "AND workFlowEntita.id_work_flow = ? ";
                        distintaErogazioneVOList = getJdbcTemplate().query(
                                query,
                                new Object[]{idWorkFlow},
                                new DistintaErogazioneVORowMapper()
                        );
                        for (DistintaErogazioneVO distintaErogazioneVO : distintaErogazioneVOList) {
                            idDistinta = distintaErogazioneVO.getIdDistinta();
                            idMonitAmmvoCont = amministrativoContabileService.callToDistintaErogazioneFinanziamento(
                                    distintaErogazioneVO.getIdDistinta(),
                                    distintaErogazioneVO.getRigaDistinta(),
                                    distintaErogazioneVO.getIdProgetto(),
                                    distintaErogazioneVO.getIdBando(),
                                    distintaErogazioneVO.getDataErogazione(),
                                    distintaErogazioneVO.getImporto(),
                                    distintaErogazioneVO.getNumRate(),
                                    distintaErogazioneVO.getFreqRate(),
                                    distintaErogazioneVO.getTipoPeriodo(),
                                    distintaErogazioneVO.getPercentualeInteressi(),
                                    distintaErogazioneVO.getImportoIres(),
                                    distintaErogazioneVO.getIdCausaleErogazione(), // Nuova specifica di amm.vo cont.
                                    distintaErogazioneVO.getPreammortamento(),
                                    distintaErogazioneVO.getIdModalitaAgevolazione(),
                                    distintaErogazioneVO.getIdModalitaAgevolazioneRif(),
                                    userInfoSec.getIdUtente()
                            );
                            //CONTROLLO L'ESITO DELLA CHIAMATA
                            try {

                                esitoAmm = "OK".equals(getJdbcTemplate().queryForObject(
                                		QUERY_ESITO_CHIAMATA_SERV,
                                        new Object[]{idMonitAmmvoCont},
                                        String.class
                                ));
                            } catch (IncorrectResultSizeDataAccessException e) {
                                esitoAmm = false;
                            }
                            //SE ESITO OK ALLORA FACCIO LA CHIAMATA ALLO STATO DISTINTE E AGGIORNO LO STATO
                            if (esitoAmm) {
                                statoDistinte = amministrativoContabileService.calltoStatoDistinte(
                                        distintaErogazioneVO.getIdDistinta(),
                                        distintaErogazioneVO.getRigaDistinta(),
                                        userInfoSec.getIdUtente());
                                if (statoDistinte != null) {
                                    query = "UPDATE PBANDI_T_DISTINTA SET \n" +
                                            "ID_STATO_DISTINTA = (\n" +
                                            "\tSELECT pdsd.ID_STATO_DISTINTA \n" +
                                            "\tFROM PBANDI_D_STATO_DISTINTA pdsd \n" +
                                            "\tWHERE pdsd.ID_STATO_DISTINTA_AMVVO = ?\n" +
                                            "),\n" +
                                            "DT_AGGIORNAMENTO = ?,\n" +
                                            "ID_UTENTE_AGG = ?\n" +
                                            "WHERE ID_DISTINTA = ?";
                                    getJdbcTemplate().update(query, statoDistinte.getStato(), statoDistinte.getDataStato(), userInfoSec.getIdUtente(), statoDistinte.getIdDistinta());
                                } else {
                                    //cosa faccio se stato distinta è null?
                                    LOG.info("Stato distinta null!");
                                }

                                idDistinta = distintaErogazioneVO.getIdDistinta();
                            }
                        }
                        break;
                    case 8:
                        query = "SELECT \n" +
                                "distinta.id_distinta AS idDistinta,\n" +
                                "distintaDett.id_distinta_dett AS idDistintaDett,\n" +
                                "distintaDett.riga_distinta AS rigaDistinta,\n" +
                                "distinta.dt_inserimento AS dataErogazione,\n" +
                                "propostaErogazione.imp_da_erogare AS importo,\n" +
                                "ammortamento.mm_ammortamento AS numeroRate,\n" +
                                "frequenza.descr_breve_frequenza_ammort AS frequenzaRate,\n" +
                                "tipoPeriodo.descr_breve_tipo_period_amm as tipoPeriodo, \n" +
                                "ammortamento.perc_interessi AS percentualeInteressi,\n" +
                                "propostaErogazione.imp_ires AS importoIres,\n" +
                                "propostaErogazione.ID_CAUSALE_EROGAZIONE AS idCausaleErogazione,\n" +  // Aggiunto per nuova specifica di amm.vo cont.
                                "ammortamento.mm_preammortamento AS preammortamento,\n" +
                                "progetto.id_progetto AS idProgetto,\n" +
                                "bandoLinea.id_bando AS idBando,\n" +
                                "modAgev.id_modalita_agevolazione AS idModalitaAgevolazione,\n" +
                                "modAgev.id_modalita_agevolazione_rif AS idModalitaAgevolazioneRif\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_C_ENTITA entitaDistinta ON entitaDistinta.id_entita = workFlowEntita.id_entita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workFlowEntita.id_target\n" +
                                "JOIN PBANDI_T_DISTINTA_DETT distintaDett ON distintaDett.id_distinta = distinta.id_distinta\n" +
                                "JOIN PBANDI_C_ENTITA entitaPropostaErogazione ON entitaPropostaErogazione.id_entita = distinta.id_entita\n" +
                                "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = distintaDett.id_target\n" +
                                "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev on modAgev.ID_MODALITA_AGEVOLAZIONE = propostaErogazione.ID_MODALITA_AGEVOLAZIONE\n" +
                                "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto\n" +
                                "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnteComp ON bandoLineaEnteComp.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                                "JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.id_estremi_bancari = distinta.id_estremi_bancari\n" +
                                "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto\n" +
                                "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto\n" +
                                "JOIN PBANDI_T_ESTREMI_BANCARI estremiBancariBeneficiario ON estremiBancariBeneficiario.id_estremi_bancari = soggettoProgetto.id_estremi_bancari\n" +
                                "JOIN PBANDI_T_DELIBERA_PROGETTI deliberaProgetti ON deliberaProgetti.id_progetto = progetto.id_progetto\n" +
                                "JOIN PBANDI_T_AMMORTAMENTO_PROGETTI ammortamento ON ammortamento.id_delibera = deliberaProgetti.id_delibera AND ammortamento.dt_fine_validita IS NULL\n" +
                                "JOIN PBANDI_D_TIPO_PERIODO_AMMORT tipoPeriodo ON tipoPeriodo.ID_TIPO_PERIODO_AMMORTAMENTO = ammortamento.ID_TIPO_PERIODO_AMMORTAMENTO \n" +
                                "JOIN PBANDI_D_FREQUENZA_AMMORT frequenza ON frequenza.ID_FREQUENZA_AMMORTAMENTO = ammortamento.ID_FREQUENZA_AMMORTAMENTO \n" +
                                "WHERE entitaDistinta.nome_entita = 'PBANDI_T_DISTINTA'\n" +
                                "AND entitaPropostaErogazione.nome_entita = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                                "AND bandoLineaEnteComp.id_ente_competenza = 2\n" +
                                "AND bandoLineaEnteComp.id_ruolo_ente_competenza = 1 \n" +
                                "AND soggettoProgetto.id_tipo_anagrafica = 1\n" +
                                "AND soggettoProgetto.id_tipo_beneficiario <> 4\n" +
                                "AND modAgev.id_modalita_agevolazione_rif = 10\n" +
                                "AND workFlowEntita.id_work_flow = ? ";
                        distintaErogazioneVOList = getJdbcTemplate().query(
                                query,
                                new Object[]{idWorkFlow},
                                new DistintaErogazioneVORowMapper()
                        );
                        for (DistintaErogazioneVO distintaErogazioneVO : distintaErogazioneVOList) {
                            idMonitAmmvoCont = amministrativoContabileService.callToDistintaErogazioneGaranzia(
                                    distintaErogazioneVO.getIdDistinta(),
                                    distintaErogazioneVO.getRigaDistinta(),
                                    distintaErogazioneVO.getIdProgetto(),
                                    distintaErogazioneVO.getIdBando(),
                                    distintaErogazioneVO.getDataErogazione(),
                                    distintaErogazioneVO.getImporto(),
                                    distintaErogazioneVO.getIdCausaleErogazione(), // Nuova specifica di amm.vo cont.
                                    distintaErogazioneVO.getNumRate(),
                                    distintaErogazioneVO.getFreqRate(),
                                    distintaErogazioneVO.getTipoPeriodo(),
                                    distintaErogazioneVO.getPercentualeInteressi(),
                                    distintaErogazioneVO.getPreammortamento(),
                                    distintaErogazioneVO.getIdModalitaAgevolazione(),
                                    distintaErogazioneVO.getIdModalitaAgevolazioneRif(),
                                    userInfoSec.getIdUtente()
                            );
                            //CONTROLLO L'ESITO DELLA CHIAMATA
                            try {

                                esitoAmm = "OK".equals(getJdbcTemplate().queryForObject(
                                		QUERY_ESITO_CHIAMATA_SERV,
                                        new Object[]{idMonitAmmvoCont},
                                        String.class
                                ));
                            } catch (IncorrectResultSizeDataAccessException e) {
                                esitoAmm = false;
                            }
                            //SE ESITO OK ALLORA FACCIO LA CHIAMATA ALLO STATO DISTINTE E AGGIORNO LO STATO
                            if (esitoAmm) {
                                statoDistinte = amministrativoContabileService.calltoStatoDistinte(
                                        distintaErogazioneVO.getIdDistinta(),
                                        distintaErogazioneVO.getRigaDistinta(),
                                        userInfoSec.getIdUtente());
                                if (statoDistinte != null) {
                                    query = "UPDATE PBANDI_T_DISTINTA SET \n" +
                                            "ID_STATO_DISTINTA = (\n" +
                                            "\tSELECT pdsd.ID_STATO_DISTINTA \n" +
                                            "\tFROM PBANDI_D_STATO_DISTINTA pdsd \n" +
                                            "\tWHERE pdsd.ID_STATO_DISTINTA_AMVVO = ?\n" +
                                            "),\n" +
                                            "DT_AGGIORNAMENTO = ?,\n" +
                                            "ID_UTENTE_AGG = ?\n" +
                                            "WHERE ID_DISTINTA = ?";
                                    getJdbcTemplate().update(query, statoDistinte.getStato(), statoDistinte.getDataStato(), userInfoSec.getIdUtente(), statoDistinte.getIdDistinta());
                                } else {
                                    //cosa faccio se stato distinta è null?
                                    LOG.info("Stato distinta null!");
                                }

                                idDistinta = distintaErogazioneVO.getIdDistinta();
                            }
                        }
                        break;
                    default:
                        throw new ErroreGestitoException("Tipo distinta non valido!");
                }

                if (idDistinta != null) {
                    avviaProcedimentoDaDistinta(idDistinta, req);
                }

                LOG.info(prf + "userInfoSec= " + userInfoSec);

                //LETTERA PROTOCOLLATA IN DOQUI
                List<DatiXActaDTO> datiActaList = popolaDatiXActaList(idWorkFlow, idTipoDocIndex, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActaList);

                for (DatiXActaDTO datiActa : datiActaList) {
                    String codClassificazione = actaManager.classificaDocumento(datiActa);
                    LOG.info(prf + "codClassificazione= " + codClassificazione);

//					String codProtocollo;
//					if (codClassificazione != null) {
//						datiActa.setCodiceClassificazione(codClassificazione);
//						codProtocollo = actaManager.protocollaDocumento(datiActa);
//						LOG.info(prf + "codProtocollo= " + codProtocollo);
//					} else {
//						LOG.warn(prf + "classificazione non riuscita,  codClassificazione=" + codClassificazione);
//						throw new Exception("classificazione non riuscita");
//					}
//					// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
					updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
                }
                //REPORT VALIDAZIONE IN DOQUI
                datiActaList = popolaDatiXActaList(idWorkFlow, idTipoDocIndexSecondario, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActaList);
                for (DatiXActaDTO datiActa : datiActaList) {
                    String codClassificazione = actaManager.classificaDocumento(datiActa);
                    LOG.info(prf + "codClassificazione= " + codClassificazione);

//					String codProtocollo;
//					if(codClassificazione!=null) {
//						datiActa.setCodiceClassificazione(codClassificazione);
//						codProtocollo = actaManager.protocollaDocumento(datiActa);
//						LOG.info(prf + "codProtocollo= " + codProtocollo);
//					}else {
//						LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//						throw new Exception ("classificazione non riuscita");
//					}
//					// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
					updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
                }

            } else {
                //AGGIORNARE STATO PBANDI_T_DISTINTA
                String query =
                        "SELECT workFlowEntita.id_target\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = workFlowEntita.id_entita\n" +
                                "WHERE entita.nome_entita = 'PBANDI_T_DISTINTA'\n" +
                                "AND workFlowEntita.id_work_flow = ?";
                Long idEscussione = getJdbcTemplate().queryForObject(
                        query,
                        new Object[]{idWorkFlow},
                        Long.class
                );
                query =
                        "SELECT ID_STATO_ITER \n" +
                                "FROM PBANDI_T_DETT_WORK_FLOW ptdwf\n" +
                                "WHERE ptdwf.ID_DETT_WORK_FLOW IN (\n" +
                                "\tSELECT MAX(ptdwf.ID_DETT_WORK_FLOW) AS ID_DETT_WORK_FLOW\n" +
                                "\tFROM PBANDI_T_DETT_WORK_FLOW ptdwf \n" +
                                "\tGROUP BY ptdwf.ID_WORK_FLOW \n" +
                                ") AND ID_WORK_FLOW = ?";
                Integer statoDistinta = getJdbcTemplate().queryForObject(
                        query,
                        new Object[]{idWorkFlow},
                        Integer.class
                );
                switch (statoDistinta) {
                    case 2:
                        statoDistinta = 2;
                        break;
                    case 3:
                        statoDistinta = 3;
                        break;
                    case 4:
                        statoDistinta = 9;
                        break;
                    default:
                        statoDistinta = null;
                }
                if (statoDistinta != null) {
                    query =
                            "UPDATE PBANDI_T_DISTINTA SET \n" +
                                    "ID_STATO_DISTINTA = ?,\n" +
                                    "DT_AGGIORNAMENTO = CURRENT_DATE,\n" +
                                    "ID_UTENTE_AGG = ?\n" +
                                    "WHERE ID_DISTINTA = ?";
                    getJdbcTemplate().update(query, statoDistinta, userInfoSec.getIdUtente(), idEscussione);
                }
            }

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneErogazione", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneGaranzieRichiestaIntegrazione(Long idWorkFlow, Integer idTipoDocIndex, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            LOG.info(prf + "userInfoSec= " + userInfoSec);

            //LETTERA PROTOCOLLATA IN DOQUI
            DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
            LOG.info(prf + "datiActa= " + datiActa);

            String codClassificazione = actaManager.classificaDocumento(datiActa);
            LOG.info(prf + "codClassificazione= " + codClassificazione);

//			String codProtocollo;
//			if(codClassificazione!=null) {
//				datiActa.setCodiceClassificazione(codClassificazione);
//				codProtocollo = actaManager.protocollaDocumento(datiActa);
//				LOG.info(prf + "codProtocollo= " + codProtocollo);
//			}else {
//				LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//				throw new Exception ("classificazione non riuscita");
//			}
//			// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
			updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());

            //AGGIORNARE STATO PBANDI_T_RICHIESTA_INTEGRAZ
            String query =
                    "SELECT workFlowEntita.id_target\n" +
                            "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = workFlowEntita.id_entita\n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_ESCUSSIONE'\n" +
                            "AND workFlowEntita.id_work_flow = ?";
            Long idEscussione = getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{idWorkFlow},
                    Long.class
            );
            query =
                    "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ SET \n" +
                            "ID_STATO_RICHIESTA = ?,\n" +
                            "DT_AGGIORNAMENTO = CURRENT_DATE,\n" +
                            "ID_UTENTE_AGG = ?\n" +
                            "WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_ESCUSSIONE')\n" +
                            "AND ID_TARGET = ?\n" +
                            "AND DT_FINE_VALIDITA IS NULL";
            getJdbcTemplate().update(query, esito ? 1 : 5, userInfoSec.getIdUtente(), idEscussione);

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneGaranzieRichiestaIntegrazione", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneGaranzieInvEsitoNegativo(Long idWorkFlow, Integer idTipoDocIndex, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            LOG.info(prf + "userInfoSec= " + userInfoSec);

            //LETTERA PROTOCOLLATA IN DOQUI
            DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
            LOG.info(prf + "datiActa= " + datiActa);

            String codClassificazione = actaManager.classificaDocumento(datiActa);
            LOG.info(prf + "codClassificazione= " + codClassificazione);

//			String codProtocollo;
//			if(codClassificazione!=null) {
//				datiActa.setCodiceClassificazione(codClassificazione);
//				codProtocollo = actaManager.protocollaDocumento(datiActa);
//				LOG.info(prf + "codProtocollo= " + codProtocollo);
//			}else {
//				LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//				throw new Exception ("classificazione non riuscita");
//			}
//			// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
			updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());

            //AGGIORNARE STATO PBANDI_T_RICHIESTA_INTEGRAZ
            String query =
                    "SELECT workFlowEntita.id_target\n" +
                            "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = workFlowEntita.id_entita\n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_ESCUSSIONE'\n" +
                            "AND workFlowEntita.id_work_flow = ?";
            Long idEscussione = getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{idWorkFlow},
                    Long.class
            );
            query =
                    "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ SET \n" +
                            "ID_STATO_RICHIESTA = ?,\n" +
                            "DT_AGGIORNAMENTO = CURRENT_DATE,\n" +
                            "ID_UTENTE_AGG = ?\n" +
                            "WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_ESCUSSIONE')\n" +
                            "AND ID_TARGET = ?\n" +
                            "AND DT_FINE_VALIDITA IS NULL";
            getJdbcTemplate().update(query, esito ? 1 : 5, userInfoSec.getIdUtente(), idEscussione);

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneGaranzieInvEsitoNegativo", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneGaranzieInvEsitoPositivo(Long idWorkFlow, Integer idTipoDocIndex, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            if (esito) {
                //PASSAGGIO DISTINTA AD AMM.CONT
                String query =
                        "SELECT \n" +
                                "progetto.id_progetto,\n" +
                                "bandoLinea.id_bando,\n" +
                                "distinta.id_distinta,\n" +
                                "distintaDett.id_distinta_dett,\n" +
                                "distintaDett.riga_distinta,\n" +
                                "escussione.iban_bonifico,\n" +
                                "escussione.dt_inizio_validita,\n" +
                                "escussione.imp_approvato,\n" +
                                "tipoEscussione.id_tipo_escussione,\n" +
                                "escussione.causale_bonifico,\n" +
                                "modAgev.id_modalita_agevolazione,\n" +
                                "modAgev.id_modalita_agevolazione_rif\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                                "JOIN PBANDI_C_ENTITA entitaDistinta ON entitaDistinta.id_entita = workFlowEntita.id_entita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workFlowEntita.id_target\n" +
                                "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev on modAgev.ID_MODALITA_AGEVOLAZIONE = distinta.ID_MODALITA_AGEVOLAZIONE\n" +
                                "JOIN PBANDI_T_DISTINTA_DETT distintaDett ON distintaDett.id_distinta = distinta.id_distinta\n" +
                                "JOIN PBANDI_C_ENTITA entitaEscussione ON entitaEscussione.id_entita = distinta.id_entita\n" +
                                "JOIN PBANDI_T_ESCUSSIONE escussione ON escussione.id_escussione = distintaDett.id_target\n" +
                                "JOIN PBANDI_D_TIPO_ESCUSSIONE tipoEscussione ON tipoEscussione.id_tipo_escussione = escussione.id_tipo_escussione\n" +
                                "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = escussione.id_progetto\n" +
                                "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda\n" +
                                "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento\n" +
                                "WHERE entitaEscussione.nome_entita = 'PBANDI_T_ESCUSSIONE'\n" +
                                "AND entitaDistinta.nome_entita = 'PBANDI_T_DISTINTA'\n" +
                                "AND workFlowEntita.id_work_flow = ? \n" +
                                "AND ROWNUM <= 1";
                DistintaEscussioniVO distintaEscussioniVO = getJdbcTemplate().queryForObject(
                        query,
                        new Object[]{idWorkFlow},
                        new DistintaEscussioniVORowMapper()
                );
                Long idMonitAmmvoCont = amministrativoContabileService.callToDistintaErogazioneEscussione(
                        distintaEscussioniVO.getIdDistinta(),
                        distintaEscussioniVO.getRigaDistinta(),
                        distintaEscussioniVO.getIdProgetto(),
                        distintaEscussioniVO.getIdBando(),
                        distintaEscussioniVO.getIbanBonifico(),
                        distintaEscussioniVO.getDtInizioValidita(),
                        distintaEscussioniVO.getImportoApprovato(),
                        distintaEscussioniVO.getIdTipoEscussione(),
                        distintaEscussioniVO.getCausaleBonifico(),
                        distintaEscussioniVO.getIdModalitaAgevolazione(),
                        distintaEscussioniVO.getIdModalitaAgevolazioneRif(),
                        userInfoSec.getIdUtente()
                );
                boolean esitoAmm;
                try {

                    esitoAmm = "OK".equals(getJdbcTemplate().queryForObject(
                    		QUERY_ESITO_CHIAMATA_SERV,
                            new Object[]{idMonitAmmvoCont},
                            String.class
                    ));
                } catch (IncorrectResultSizeDataAccessException e) {
                    esitoAmm = false;
                }
                //AGGIORNARE STATO DISTINTA
                query = "UPDATE PBANDI_T_DISTINTA \n" +
                        "SET ID_STATO_DISTINTA = 8 \n" +
                        "WHERE ID_DISTINTA = ? ";
                getJdbcTemplate().update(query, distintaEscussioniVO.getIdDistinta());

                //SE ESITO OK ALLORA FACCIO LA CHIAMATA ALLO STATO DISTINTE E AGGIORNO LO STATO
                if (esitoAmm) {
                    StatoDistinte statoDistinte = amministrativoContabileService.calltoStatoDistinte(distintaEscussioniVO.getIdDistinta(), distintaEscussioniVO.getRigaDistinta(), userInfoSec.getIdUtente());
                    query = "UPDATE PBANDI_T_DISTINTA SET \n" +
                            "ID_STATO_DISTINTA = (\n" +
                            "\tSELECT pdsd.ID_STATO_DISTINTA \n" +
                            "\tFROM PBANDI_D_STATO_DISTINTA pdsd \n" +
                            "\tWHERE pdsd.ID_STATO_DISTINTA_AMVVO = ?\n" +
                            "),\n" +
                            "DT_AGGIORNAMENTO = ?,\n" +
                            "ID_UTENTE_AGG = ?\n" +
                            "WHERE ID_DISTINTA = ?";
                    getJdbcTemplate().update(query, statoDistinte.getStato(), statoDistinte.getDataStato(), userInfoSec.getIdUtente(), statoDistinte.getIdDistinta());

                    LOG.info(prf + "userInfoSec= " + userInfoSec);

                    //LETTERA PROTOCOLLATA IN DOQUI
                    DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocIndex, userInfoSec);
                    LOG.info(prf + "datiActa= " + datiActa);

                    String codClassificazione = actaManager.classificaDocumento(datiActa);
                    LOG.info(prf + "codClassificazione= " + codClassificazione);

//					String codProtocollo;
//					if(codClassificazione!=null) {
//						datiActa.setCodiceClassificazione(codClassificazione);
//						codProtocollo = actaManager.protocollaDocumento(datiActa);
//						LOG.info(prf + "codProtocollo= " + codProtocollo);
//					}else {
//						LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//						throw new Exception ("classificazione non riuscita");
//					}
//					// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
					updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());

                }
            }

            //AGGIORNARE STATO PBANDI_T_RICHIESTA_INTEGRAZ
            String query =
                    "SELECT workFlowEntita.id_target\n" +
                            "FROM PBANDI_T_WORK_FLOW_ENTITA workFlowEntita\n" +
                            "JOIN PBANDI_C_ENTITA entita ON entita.id_entita = workFlowEntita.id_entita\n" +
                            "WHERE entita.nome_entita = 'PBANDI_T_ESCUSSIONE'\n" +
                            "AND workFlowEntita.id_work_flow = ?";
            Long idEscussione = getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{idWorkFlow},
                    Long.class
            );
            query =
                    "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ SET \n" +
                            "ID_STATO_RICHIESTA = ?,\n" +
                            "DT_AGGIORNAMENTO = CURRENT_DATE,\n" +
                            "ID_UTENTE_AGG = ?\n" +
                            "WHERE ID_ENTITA = (SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE NOME_ENTITA = 'PBANDI_T_ESCUSSIONE')\n" +
                            "AND ID_TARGET = ?\n" +
                            "AND DT_FINE_VALIDITA IS NULL";
            getJdbcTemplate().update(query, esito ? 1 : 5, userInfoSec.getIdUtente(), idEscussione);

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneGaranzieInvEsitoPositivo", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneEsitoValidazioneErogazioneSospesa(Long idWorkFlow, Integer idTipoDocumentoIndex, Integer idTipoDocumentoIndexSecondario, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            LOG.info(prf + "userInfoSec= " + userInfoSec);

            if (esito) {
                //LETTERA PROTOCOLLATA IN DOQUI
                DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocumentoIndex, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);

                String codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);

//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());

                datiActa = popolaDatiXActa(idWorkFlow, idTipoDocumentoIndexSecondario, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);
                if (datiActa != null) {
                    codClassificazione = actaManager.classificaDocumento(datiActa);
                    LOG.info(prf + "codClassificazione= " + codClassificazione);

//					if(codClassificazione!=null) {
//						datiActa.setCodiceClassificazione(codClassificazione);
//						codProtocollo = actaManager.protocollaDocumento(datiActa);
//						LOG.info(prf + "codProtocollo= " + codProtocollo);
//					}else {
//						LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//						throw new Exception ("classificazione non riuscita");
//					}
//					// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
					updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
                }
            }

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneEsitoValidazioneErogazioneSospesa", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void gestioneComunicazioneInterventoSostitutivo(Long idWorkFlow, Integer idTipoDocumentoIndex, Boolean esito, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN" + "params: idWorklow: "+ idWorkFlow!= null?idWorkFlow: "null" + "idTipoDocIndex: "+ idTipoDocumentoIndex!= null?idTipoDocumentoIndex: "null");


        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            LOG.info(prf + "userInfoSec= " + userInfoSec);

            if (esito) {
                //LETTERA PROTOCOLLATA IN DOQUI
                DatiXActaDTO datiActa = popolaDatiXActa(idWorkFlow, idTipoDocumentoIndex, userInfoSec);
                LOG.info(prf + "datiActa= " + datiActa);

                String codClassificazione = actaManager.classificaDocumento(datiActa);
                LOG.info(prf + "codClassificazione= " + codClassificazione);

//				String codProtocollo;
//				if(codClassificazione!=null) {
//					datiActa.setCodiceClassificazione(codClassificazione);
//					codProtocollo = actaManager.protocollaDocumento(datiActa);
//					LOG.info(prf + "codProtocollo= " + codProtocollo);
//				}else {
//					LOG.warn(prf + "classificazione non riuscita,  codClassificazione="+codClassificazione);
//					throw new Exception ("classificazione non riuscita");
//				}
//				// aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
				updateTProtocollo(userInfoSec.getIdUtente(), codClassificazione, datiActa.getIdDocIndex());
            }

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to gestioneComunicazioneInterventoSostitutivo", e);
            throw e;
        } finally {
            LOG.info(prf + "END");
        }
    }


    // Se ho mandato la distinta ad amministrativo contabile devo, per ogni proposta di erogazione,
    // se provviene dalle revoche, avviare il procedimento di revoca
    private void avviaProcedimentoDaDistinta(int idDistinta, HttpServletRequest req) {
        String prf = "[IterAutorizzativiDAOImpl::avviaProcedimentoDaDistinta]";
        LOG.info(prf + "BEGIN");
        
        LOG.info(prf + " idDistinta: " + idDistinta);

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        try {
            //Prendo i record sulla gestioneRevoca
            String query = "SELECT ptgr.ID_GESTIONE_REVOCA \n" +
                    "FROM PBANDI_T_DISTINTA ptd \n" +
                    "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptd.ID_ENTITA \n" +
                    "\tAND pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                    "JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA = ptd.ID_DISTINTA \n" +
                    "JOIN PBANDI_T_PROPOSTA_EROGAZIONE ptpe ON ptpe.ID_PROPOSTA = ptdd.ID_TARGET \n" +
                    "JOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptgr.ID_GESTIONE_REVOCA = ptpe.ID_GESTIONE_REVOCA \n" +
                    "WHERE ptd.ID_DISTINTA = ?";
            LOG.info(prf + " Query: " + query);
            List<Long> idGestioniRevoca = getJdbcTemplate().queryForList(query, Long.class, idDistinta);
            LOG.info(prf + " idGestioniRevoca: " + idGestioniRevoca);

            String queryChiudiRecord = "UPDATE PBANDI_T_GESTIONE_REVOCA SET \n" +
                    "DT_FINE_VALIDITA = CURRENT_DATE,\n" +
                    "ID_UTENTE_AGG = ?,\n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                    "WHERE ID_GESTIONE_REVOCA = ?";

            String queryNewIdGestioneRevoca = "select SEQ_PBANDI_T_GESTIONE_REVOCA.nextval from dual";

            String queryNewRecordGestioneRevoca = "INSERT INTO PBANDI_T_GESTIONE_REVOCA (\n" +
                    "ID_GESTIONE_REVOCA,\n" + "NUMERO_REVOCA,\n" + "ID_TIPOLOGIA_REVOCA,\n" + "ID_PROGETTO,\n" + "ID_CAUSALE_BLOCCO,\n" +
                    "ID_CATEG_ANAGRAFICA,\n" + "DT_GESTIONE,\n" + "NUM_PROTOCOLLO,\n" + "FLAG_ORDINE_RECUPERO,\n" + "ID_MANCATO_RECUPERO,\n" +
                    "ID_STATO_REVOCA,\n" + "DT_STATO_REVOCA,\n" + "DT_NOTIFICA, \n" + "GG_RISPOSTA,\n" + "FLAG_PROROGA,\n" + "IMP_DA_REVOCARE_CONTRIB,\n" +
                    "IMP_DA_REVOCARE_FINANZ,\n" + "IMP_DA_REVOCARE_GARANZIA,\n" + "FLAG_DETERMINA,\n" + "ESTREMI,\n" + "DT_DETERMINA,\n" + "NOTE,\n" +
                    "ID_ATTIVITA_REVOCA,\n" + "DT_ATTIVITA,\n" + "ID_MOTIVO_REVOCA,\n" + "FLAG_CONTRIB_REVOCA,\n" + "FLAG_CONTRIB_MINOR_SPESE,\n" +
                    "FLAG_CONTRIB_DECURTAZ,\n" + "FLAG_FINANZ_REVOCA,\n" + "FLAG_FINANZ_MINOR_SPESE,\n" + "FLAG_FINANZ_DECURTAZ,\n" + "FLAG_GARANZIA_REVOCA,\n" +
                    "FLAG_GARANZIA_MINOR_SPESE,\n" + "FLAG_GARANZIA_DECURTAZ,\n" + "IMP_CONTRIB_REVOCA_NO_RECU,\n" + "IMP_CONTRIB_REVOCA_RECU,\n" +
                    "IMP_CONTRIB_INTERESSI,\n" + "IMP_FINANZ_REVOCA_NO_RECU,\n" + "IMP_FINANZ_REVOCA_RECU,\n" + "IMP_FINANZ_INTERESSI,\n" +
                    "IMP_GARANZIA_REVOCA_NO_RECU,\n" + "IMP_GARANZIA_REVOCA_RECUPERO,\n" + "IMP_GARANZIA_INTERESSI,\n" + "COVAR,\n" + "DT_INIZIO_VALIDITA,\n" +
                    "DT_FINE_VALIDITA,\n" + "ID_UTENTE_INS,\n" + "ID_UTENTE_AGG,\n" + "DT_INSERIMENTO,\n" + "DT_AGGIORNAMENTO\n" +
                    ") \n" +
                    "SELECT \n" +
                    "?,\n" +    //ID_GESTIONE_REVOCA_NEW
                    "gestioneRevoca.NUMERO_REVOCA,\n" +
                    "gestioneRevoca.ID_TIPOLOGIA_REVOCA,\n" +
                    "gestioneRevoca.ID_PROGETTO,\n" +
                    "gestioneRevoca.ID_CAUSALE_BLOCCO,\n" +
                    "gestioneRevoca.ID_CATEG_ANAGRAFICA,\n" +
                    "NULL,\n" +
                    "NULL,\n" +
                    "gestioneRevoca.FLAG_ORDINE_RECUPERO,\n" +
                    "gestioneRevoca.ID_MANCATO_RECUPERO,\n" +
                    "6,\n" +    //ID_STATO_REVOCA
                    "CURRENT_DATE,\n" +
                    "NULL,\n" +
                    "gestioneRevoca.GG_RISPOSTA,\n" +
                    "gestioneRevoca.FLAG_PROROGA,\n" +
                    "gestioneRevoca.IMP_DA_REVOCARE_CONTRIB,\n" +
                    "gestioneRevoca.IMP_DA_REVOCARE_FINANZ,\n" +
                    "gestioneRevoca.IMP_DA_REVOCARE_GARANZIA,\n" +
                    "gestioneRevoca.FLAG_DETERMINA,\n" +
                    "gestioneRevoca.ESTREMI,\n" +
                    "gestioneRevoca.DT_DETERMINA,\n" +
                    "gestioneRevoca.NOTE,\n" +
                    "NULL,\n" +
                    "NULL,\n" +
                    "gestioneRevoca.ID_MOTIVO_REVOCA,\n" +
                    "gestioneRevoca.FLAG_CONTRIB_REVOCA,\n" +
                    "gestioneRevoca.FLAG_CONTRIB_MINOR_SPESE,\n" +
                    "gestioneRevoca.FLAG_CONTRIB_DECURTAZ,\n" +
                    "gestioneRevoca.FLAG_FINANZ_REVOCA,\n" +
                    "gestioneRevoca.FLAG_FINANZ_MINOR_SPESE,\n" +
                    "gestioneRevoca.FLAG_FINANZ_DECURTAZ,\n" +
                    "gestioneRevoca.FLAG_GARANZIA_REVOCA,\n" +
                    "gestioneRevoca.FLAG_GARANZIA_MINOR_SPESE,\n" +
                    "gestioneRevoca.FLAG_GARANZIA_DECURTAZ,\n" +
                    "gestioneRevoca.IMP_CONTRIB_REVOCA_NO_RECU,\n" +
                    "gestioneRevoca.IMP_CONTRIB_REVOCA_RECU,\n" +
                    "gestioneRevoca.IMP_CONTRIB_INTERESSI,\n" +
                    "gestioneRevoca.IMP_FINANZ_REVOCA_NO_RECU,\n" +
                    "gestioneRevoca.IMP_FINANZ_REVOCA_RECU,\n" +
                    "gestioneRevoca.IMP_FINANZ_INTERESSI,\n" +
                    "gestioneRevoca.IMP_GARANZIA_REVOCA_NO_RECU,\n" +
                    "gestioneRevoca.IMP_GARANZIA_REVOCA_RECUPERO,\n" +
                    "gestioneRevoca.IMP_GARANZIA_INTERESSI,\n" +
                    "gestioneRevoca.COVAR,\n" +
                    "CURRENT_DATE, \n" +
                    "NULL,\n" +
                    "?,\n" +    //ID_UTENTE_INS
                    "NULL,\n" +
                    "CURRENT_DATE,\n" +
                    "NULL\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "WHERE gestioneRevoca.id_gestione_revoca = ?";    //ID_GESTIONE_REVOCA

            for (Long idGestioneRevoca : idGestioniRevoca) {
                //Chiudo i record precedente
                getJdbcTemplate().update(queryChiudiRecord, userInfoSec.getIdUtente(), idGestioneRevoca);
                //Ricavo nuovo id
                Long idGestioneRevocaNew = getJdbcTemplate().queryForObject(queryNewIdGestioneRevoca, Long.class);
                //Inserisco nuovo record
                getJdbcTemplate().update(queryNewRecordGestioneRevoca, idGestioneRevocaNew, userInfoSec.getIdUtente(), idGestioneRevoca);
            }
        } catch (Exception e) {
            LOG.error("Error while trying to avviaProcedimentoDaDistinta", e);
            e.printStackTrace();
        } finally {
            LOG.info(prf + "END");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateTProtocollo(Long idUtente, String codClassificazione, Long idDocIndex) {
        // aggiorno PBANDI_T_DOC_PROTOCOLLO con codClassificazione e codProtocollo
        String q = "UPDATE PBANDI_T_DOC_PROTOCOLLO SET DT_CLASSIFICAZIONE = sysdate, CLASSIFICAZIONE_ACTA = ?, "
                + " DT_AGGIORNAMENTO = sysdate, ID_UTENTE_AGG  = ? "
                + " WHERE ID_DOCUMENTO_INDEX = ? ";
        LOG.debug("[IterAutorizzativiDAOImpl::updateTProtocollo] query = " + q);
        if (getJdbcTemplate().update(q, codClassificazione, idUtente, idDocIndex) == 0) {
            // se non trovo record da aggiornare ne inserisco uno nuovo
            q = "INSERT INTO PBANDI_T_DOC_PROTOCOLLO \n" +
                    "(ID_DOCUMENTO_INDEX, ID_SISTEMA_PROT, DT_CLASSIFICAZIONE, CLASSIFICAZIONE_ACTA, ID_UTENTE_INS, DT_INSERIMENTO) \n" +
                    "VALUES (?, 2, sysdate, ?, ?, sysdate)";
            getJdbcTemplate().update(q, idDocIndex, codClassificazione, idUtente);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public DatiXActaDTO popolaDatiXActa(Long idWorkFlow, Integer idTipoDocIndex, UserInfoSec userInfoSec) throws Exception {
        String prf = "[IterAutorizzativiDAOImpl::popolaDatiXActa]";
        //LOG.info(prf + "BEGIN" + "params: idWorklow: "+ idWorkFlow!= null?idWorkFlow: "null" + "idTipoDocIndex: "+ idTipoDocIndex!= null?idTipoDocIndex: "null");
        LOG.info(prf + " BEGIN");
        
        LOG.info(prf + " idWorklow: " + idWorkFlow);
        LOG.info(prf + " idTipoDocIndex: " + idTipoDocIndex);
        
        DatiXActaDTO dati;

       
        String query = "SELECT \n" +
                "ptwfe.ID_WORK_FLOW, \n" +
                "ptwfe.ID_ENTITA, \n" +
                "ptwfe.ID_TARGET, \n" +
                "pr.ID_PROGETTO, \n" +
                "do.ID_DOMANDA, \n" +
                "do.NUMERO_DOMANDA,\n" +
                "ct.DESC_BREVE_TIPO_DOC_INDEX, \n" +
                "ct.DESC_TIPO_DOC_INDEX,\n" +
                "fa.DESC_FASCICOLO, \n" +
                "di.FLAG_FIRMA_AUTOGRAFA, \n" +
                "di.NOME_DOCUMENTO, \n" +
                "di.NOME_FILE, \n" +
                "di.ID_DOCUMENTO_INDEX,\n" +
                "di.ID_TIPO_DOCUMENTO_INDEX     \n" +
                "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n" +
                "JOIN PBANDI_T_WORK_FLOW ptwf on ptwf.ID_WORK_FLOW = ptwfe.ID_WORK_FLOW \n" +
                "JOIN PBANDI_T_DOCUMENTO_INDEX di ON di.ID_TARGET = ptwfe.ID_TARGET\n" +
                "\t AND di.ID_ENTITA = ptwfe.ID_ENTITA\n" +
                "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX ct ON ct.ID_TIPO_DOCUMENTO_INDEX = di.ID_TIPO_DOCUMENTO_INDEX\n" +
                "JOIN PBANDI_R_FASC_ACTA_TIP_DOC_IDX rfa ON rfa.ID_TIPO_DOCUMENTO_INDEX = di.ID_TIPO_DOCUMENTO_INDEX \n" +
                "JOIN PBANDI_D_FASCICOLO_ACTA fa ON fa.ID_FASCICOLO_ACTA = rfa.ID_FASCICOLO_ACTA \n" +
                "JOIN PBANDI_T_PROGETTO pr ON pr.ID_PROGETTO = ptwfe.ID_PROGETTO\n" +
                "JOIN PBANDI_T_DOMANDA do ON do.ID_DOMANDA = pr.ID_DOMANDA \n" +
                "WHERE PTWFE.ID_WORK_FLOW = ? AND di.ID_TIPO_DOCUMENTO_INDEX = ? ";

        List<DatiXActaDTO> datiList = getJdbcTemplate().query(query, new Object[]{idWorkFlow, idTipoDocIndex}, new DatiXActaRowMapper());

        if (datiList != null && datiList.size() > 1) {
            LOG.warn(prf + "trovati troppo risultati =" + datiList.size());
            throw new Exception("Trovati troppo risultati");
        } else if (datiList == null || datiList.isEmpty()) {
            dati = null;
        } else {
            dati = datiList.get(0);
        }

        LOG.info(prf + " datiObj: " + dati);
        if (dati != null) {

            if (dati.getNumeroDomanda() != null) {
                dati.setParolaChiave(dati.getNumeroDomanda());
                // bisogna capire se la domanda arriva da FINDOM o da PBANDI/scheda progetto
            } else {
                LOG.warn(prf + "Numero domanda non valorizzato");
                throw new Exception("Numero domanda non valorizzato");
            }
            if (dati.getIdDocIndex() != null) {
                FileDTO fil = documentoManager.leggiFile(dati.getIdDocIndex());
                if (fil != null) {
                    LOG.info(prf + "caricato file nomefile = " + fil.getNomeFile());
                    LOG.info(prf + "con file size = " + fil.getSizeFile());
                    dati.setTsdFile(fil.getBytes());
                }
            } else {
                throw new Exception("File non trovato");
            }

            dati.setUtenteCollegatoCF(userInfoSec.getCodFisc());
            dati.setUtenteCollegatoNome(userInfoSec.getNome());
            dati.setUtenteCollegatoCognome(userInfoSec.getCognome());
            dati.setUtenteCollegatoId(userInfoSec.getIdUtente());

            try {
                String sql = "WITH denom AS (\n" +
                        "    SELECT\n" +
                        "    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione_fis,\n" +
                        "    PERSFIS.ID_SOGGETTO,\n" +
                        "    persfis.ID_PERSONA_FISICA\n" +
                        "    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
                        "),\n" +
                        "denom2 AS (\n" +
                        "    SELECT\n" +
                        "    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione_ente,\n" +
                        "    ENTEGIU.ID_SOGGETTO,\n" +
                        "    entegiu.ID_ENTE_GIURIDICO\n" +
                        "    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
                        ")\n" +
                        "SELECT DISTINCT denom.denominazione_fis || denom2.denominazione_ente AS denominazione\n" +
                        "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n" +
                        "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\n" +
                        "LEFT JOIN denom ON prsp.ID_SOGGETTO = denom.id_soggetto AND denom.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\n" +
                        "LEFT JOIN denom2 ON prsp.ID_SOGGETTO = denom2.id_soggetto AND denom2.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\n" +
                        "WHERE ((prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                        "AND prsp.ID_TIPO_BENEFICIARIO <> 4) OR prsp.ID_SOGGETTO IS NULL)\n" +
                        "AND ptwfe.ID_WORK_FLOW = ?";
                dati.setDenominazioneBeneficiario(getJdbcTemplate().queryForObject(sql, String.class, idWorkFlow));
            } catch (IncorrectResultSizeDataAccessException ignored) {
            }

        }
        LOG.info(prf + " END");
        return dati;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<DatiXActaDTO> popolaDatiXActaList(Long idWorkFlow, Integer idTipoDocIndex, UserInfoSec userInfoSec) throws Exception {
        String prf = "[IterAutorizzativiDAOImpl::popolaDatiXActaList]";
        //LOG.info(prf + "BEGIN" + "params: idWorklow: "+ idWorkFlow!= null?idWorkFlow: "null" + "idTipoDocIndex: "+ idTipoDocIndex!= null?idTipoDocIndex: "null");
        LOG.info(prf + " BEGIN");
        
        LOG.info(prf + " idWorklow: " + idWorkFlow);
        LOG.info(prf + " idTipoDocIndex: " + idTipoDocIndex);

        String query = "SELECT PTWFE.ID_WORK_FLOW, PTWFE.ID_ENTITA, PTWFE.ID_TARGET, \r\n"
        		+ "CASE \r\n"
        		+ "	WHEN pr.ID_PROGETTO IS NOT NULL\r\n"
        		+ "	THEN pr.ID_PROGETTO\r\n"
        		+ "	ELSE ptp.ID_PROGETTO\r\n"
        		+ "END AS ID_PROGETTO,\r\n"
        		+ "CASE \r\n"
        		+ "	WHEN pr.ID_DOMANDA IS NOT NULL\r\n"
        		+ "	THEN pr.ID_DOMANDA\r\n"
        		+ "	ELSE ptp.ID_DOMANDA\r\n"
        		+ "END AS ID_DOMANDA,\r\n"
        		+ "CASE \r\n"
        		+ "	WHEN pr.ID_DOMANDA IS NOT NULL\r\n"
        		+ "	THEN do1.NUMERO_DOMANDA\r\n"
        		+ "	ELSE do2.NUMERO_DOMANDA\r\n"
        		+ "END AS NUMERO_DOMANDA,\r\n"
        		+ "ct.DESC_BREVE_TIPO_DOC_INDEX, ct.DESC_TIPO_DOC_INDEX, \r\n"
        		+ "fa.DESC_FASCICOLO, di.FLAG_FIRMA_AUTOGRAFA,  \r\n"
        		+ "di.NOME_DOCUMENTO, di.NOME_FILE, \r\n"
        		+ "di.ID_DOCUMENTO_INDEX, di.ID_TIPO_DOCUMENTO_INDEX\r\n"
        		+ "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe \r\n"
        		+ "JOIN PBANDI_T_DOCUMENTO_INDEX di ON di.ID_TARGET = ptwfe.ID_TARGET  \r\n"
        		+ "	AND di.ID_ENTITA = ptwfe.ID_ENTITA \r\n"
        		+ "LEFT JOIN PBANDI_T_PROGETTO pr ON pr.ID_PROGETTO = ptwfe.ID_PROGETTO\r\n"
        		+ "LEFT JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = di.ID_PROGETTO\r\n"
        		+ "LEFT JOIN PBANDI_T_DOMANDA do1 ON do1.ID_DOMANDA = PR.ID_DOMANDA \r\n"
        		+ "LEFT JOIN PBANDI_T_DOMANDA do2 ON do2.ID_DOMANDA = PTP.ID_DOMANDA \r\n"
        		+ "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX ct ON ct.ID_TIPO_DOCUMENTO_INDEX = di.ID_TIPO_DOCUMENTO_INDEX\r\n"
        		+ "JOIN PBANDI_R_FASC_ACTA_TIP_DOC_IDX rfa ON rfa.ID_TIPO_DOCUMENTO_INDEX = di.ID_TIPO_DOCUMENTO_INDEX \r\n"
        		+ "JOIN PBANDI_D_FASCICOLO_ACTA fa ON fa.ID_FASCICOLO_ACTA = rfa.ID_FASCICOLO_ACTA\n" +
                "WHERE PTWFE.ID_WORK_FLOW = ? AND di.ID_TIPO_DOCUMENTO_INDEX = ? \n" +
                "AND NVL(di.FLAG_VISIBILE_BEN, 'S') != 'N'"; // Logica modificata per accomodare i flag a NULL
        LOG.info(prf + query);

        List<DatiXActaDTO> datiList = getJdbcTemplate().query(query, new Object[]{idWorkFlow, idTipoDocIndex}, new DatiXActaRowMapper());
        LOG.info(prf + " datiList: " + datiList);

        for (DatiXActaDTO dati : datiList) {
            if (dati.getIdDomanda() != null) {
                dati.setParolaChiave(dati.getNumeroDomanda());
                // bisogna capire se la domanda arriva da FINDOM o da PBANDI/scheda progetto
            } else {
                LOG.warn(prf + "Numero domanda non valorizzato");
                throw new Exception("Numero domanda non valorizzato");
            }
            if (dati.getIdDocIndex() != null) {
                FileDTO fil = documentoManager.leggiFile(dati.getIdDocIndex());
                if (fil != null) {
                    LOG.info(prf + "caricato file nomefile =" + fil.getNomeFile());
                    LOG.info(prf + "caricato file size =" + fil.getSizeFile());
                    dati.setTsdFile(fil.getBytes());
                }
            } else {
                throw new Exception("File non trovato");
            }

            dati.setUtenteCollegatoCF(userInfoSec.getCodFisc());
            dati.setUtenteCollegatoNome(userInfoSec.getNome());
            dati.setUtenteCollegatoCognome(userInfoSec.getCognome());
            dati.setUtenteCollegatoId(userInfoSec.getIdUtente());

            try {
                String sql = "WITH denom AS (\n" +
                        "    SELECT\n" +
                        "    concat(persfis.NOME, CONCAT (' ', persfis.COGNOME)) as denominazione_fis,\n" +
                        "    PERSFIS.ID_SOGGETTO,\n" +
                        "    persfis.ID_PERSONA_FISICA\n" +
                        "    FROM PBANDI_T_PERSONA_FISICA persfis\n" +
                        "),\n" +
                        "denom2 AS (\n" +
                        "    SELECT\n" +
                        "    entegiu.DENOMINAZIONE_ENTE_GIURIDICO AS denominazione_ente,\n" +
                        "    ENTEGIU.ID_SOGGETTO,\n" +
                        "    entegiu.ID_ENTE_GIURIDICO\n" +
                        "    FROM PBANDI_T_ENTE_GIURIDICO entegiu\n" +
                        ")\n" +
                        "SELECT DISTINCT denom.denominazione_fis || denom2.denominazione_ente AS denominazione\n" +
                        "FROM PBANDI_T_WORK_FLOW_ENTITA ptwfe\n" +
                        "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptwfe.ID_PROGETTO\n" +
                        "LEFT JOIN denom ON prsp.ID_SOGGETTO = denom.id_soggetto AND denom.ID_PERSONA_FISICA = prsp.ID_PERSONA_FISICA\n" +
                        "LEFT JOIN denom2 ON prsp.ID_SOGGETTO = denom2.id_soggetto AND denom2.ID_ENTE_GIURIDICO = prsp.ID_ENTE_GIURIDICO\n" +
                        "WHERE ((prsp.ID_TIPO_ANAGRAFICA = 1\n" +
                        "AND prsp.ID_TIPO_BENEFICIARIO <> 4) OR prsp.ID_SOGGETTO IS NULL)\n" +
                        "AND ptwfe.ID_WORK_FLOW = ?";
                dati.setDenominazioneBeneficiario(getJdbcTemplate().queryForObject(sql, String.class, idWorkFlow));
            } catch (IncorrectResultSizeDataAccessException ignored) {
            }

        }

        LOG.info(prf + "END");
        return datiList;
    }
}
