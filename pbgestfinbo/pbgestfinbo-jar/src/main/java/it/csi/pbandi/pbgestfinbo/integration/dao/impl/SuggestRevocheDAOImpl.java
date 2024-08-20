/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.SuggestRevocheDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.suggestion.SuggestIdDescVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.SuggestIdDescVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestRevocheDAOImpl extends JdbcDaoSupport implements SuggestRevocheDAO {
    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    public SuggestRevocheDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public Long newNumeroRevoca() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        String query = "select SEQ_PBANDI_T_GEST_REVOC_NUMERO.nextval from dual";

        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

        Long id = getJdbcTemplate().queryForObject(query, Long.class);

        LOG.info(prf + "END");
        return id;
    }
    @Override
    public List<SuggestIdDescVO> suggestNumeroRevoche(FiltroRevocaVO filtroRevocaVO) throws DaoException {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIdDescVO> lista;

        try{
            StringBuilder query = new StringBuilder();

            query.append(
                    "SELECT DISTINCT \n" +
                    "gestioneRevoca.id_gestione_revoca AS id, \n" +
                    "gestioneRevoca.numero_revoca AS descrizione\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = gestioneRevoca.id_progetto \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = soggettoProgetto.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnte ON bandoLineaEnte.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                    "LEFT JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = gestioneRevoca.id_causale_blocco\n" +
                    "LEFT JOIN PBANDI_D_STATO_REVOCA statoRevoca ON statoRevoca.id_stato_revoca = gestioneRevoca.id_stato_revoca\n" +
                    "LEFT JOIN PBANDI_D_ATTIVITA_REVOCA attivitaRevoca ON attivitaRevoca.id_attivita_revoca = gestioneRevoca.id_attivita_revoca \n" +
                    "WHERE soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                    "AND bandoLineaEnte.id_ente_competenza = 2 \n" +
                    "AND bandoLineaEnte.id_ruolo_ente_competenza = 1 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL \n" +
                    "AND gestioneRevoca.id_tipologia_revoca = ? \n"
            );

            ArrayList<Object> args = new ArrayList<>();
            args.add(filtroRevocaVO.getIdTipologiaRevoca());

            if(filtroRevocaVO.getValue() != null){
                args.add(filtroRevocaVO.getValue());
                query.append("AND \n" +
                        "UPPER(gestioneRevoca.numero_revoca) LIKE CONCAT('%', CONCAT(?,'%')) \n");
            }
            if(filtroRevocaVO.getIdSoggetto() != null){
                args.add(filtroRevocaVO.getIdSoggetto());
                query.append("AND \n" +
                        "soggettoProgetto.id_soggetto = ? \n");
            }
            if(filtroRevocaVO.getIdProgetto() != null){
                args.add(filtroRevocaVO.getIdProgetto());
                query.append("AND \n" +
                        "soggettoProgetto.id_progetto = ? \n");
            }
            if(filtroRevocaVO.getProgrBandoLineaIntervent() != null){
                args.add(filtroRevocaVO.getProgrBandoLineaIntervent());
                query.append("AND \n" +
                        "bandoLinea.progr_bando_linea_intervento = ? \n");
            }
            if(filtroRevocaVO.getIdCausaRevoca() != null){
                args.add(filtroRevocaVO.getIdCausaRevoca());
                query.append("AND \n" +
                        "causaleBlocco.id_causale_blocco = ? \n");
            }
            if(filtroRevocaVO.getIdStatoRevoca() != null){
                args.add(filtroRevocaVO.getIdStatoRevoca());
                query.append("AND \n" +
                        "statoRevoca.id_stato_revoca = ? \n");
            }
            if(filtroRevocaVO.getIdAttivitaRevoca() != null){
                args.add(filtroRevocaVO.getIdAttivitaRevoca());
                query.append("AND \n" +
                        "attivitaRevoca.id_attivita_revoca = ? \n");
            }
            if(filtroRevocaVO.getDataRevocaFrom() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaFrom().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione >= ? \n");
            }
            if(filtroRevocaVO.getDataRevocaTo() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaTo().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione <= ? \n");
            }
            query.append("ORDER BY descrizione ");

            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

            lista = getJdbcTemplate().query(
                    query.toString(),
                    ps -> {
                        for(int i = 0; i < args.size(); i++){
                            ps.setObject(i+1, args.get(i));
                        }
                    },
                    new SuggestIdDescVORowMapper()
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to suggestNumeroRevoche", e);
            throw new DaoException("DaoException while trying to suggestNumeroRevoche", e);
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to suggestNumeroRevoche", e);
            throw new DaoException("DaoException while trying to suggestNumeroRevoche", e);
        } finally {
            LOG.info(prf + " END");
        }

        return lista;
    }
    @Override
    public List<SuggestIdDescVO> suggestBeneficiario(String nomeBeneficiario, Long progrBandoLineaIntervent) throws DaoException {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIdDescVO> lista;

        try{
            String sql = //379
                    "WITH lastSoggProg AS (\n" +
                    "\tSELECT max(soggettoProgetto.progr_soggetto_progetto) AS maxProgrSoggProg\n" +
                    "\tFROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto\n" +
                    "\tGROUP BY soggettoProgetto.id_soggetto\n" +
                    ")\n" +
                    "SELECT DISTINCT \n" +
                    "soggetto.id_soggetto AS id, \n" +
                    "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS descrizione, \n" +
                    "soggetto.codice_fiscale_soggetto AS altro \n" +
                    "FROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto \n" +
                    "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto\n" +
                    "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
                    "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = soggettoProgetto.id_persona_fisica\n" +
                    "JOIN lastSoggProg ON lastSoggProg.maxProgrSoggProg = soggettoProgetto.progr_soggetto_progetto\n" +
                    "WHERE UPPER(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) LIKE CONCAT('%', CONCAT(?,'%'))\n" +
                    "AND soggettoProgetto.id_tipo_anagrafica = 1 AND soggettoProgetto.id_tipo_beneficiario <> 4";
            if(progrBandoLineaIntervent != -1){
                sql =
                    "WITH lastSoggProg AS (\n" +
                    "\tSELECT max(soggettoProgetto.progr_soggetto_progetto) AS maxProgrSoggProg\n" +
                    "\tFROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto\n" +
                    "\tGROUP BY soggettoProgetto.id_soggetto\n" +
                    ")\n" +
                    "SELECT DISTINCT \n" +
                    "soggetto.id_soggetto AS id, \n" +
                    "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS descrizione, \n" +
                    "soggetto.codice_fiscale_soggetto AS altro \n" +
                    "FROM PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = soggettoProgetto.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervent ON bandoLineaIntervent.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento\n" +
                    "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP enteComp ON enteComp.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento\n" +
                    "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto\n" +
                    "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
                    "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = soggettoProgetto.id_persona_fisica\n" +
                    "JOIN lastSoggProg ON lastSoggProg.maxProgrSoggProg = soggettoProgetto.progr_soggetto_progetto\n" +
                    "WHERE UPPER(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) LIKE CONCAT('%', CONCAT(?,'%'))\n" +
                    "AND soggettoProgetto.id_tipo_anagrafica = 1 AND soggettoProgetto.id_tipo_beneficiario <> 4\n" +
                    "AND enteComp.ID_ENTE_COMPETENZA = '2' \n" +
                    "AND enteComp.ID_RUOLO_ENTE_COMPETENZA = '1' \n" +
                    "AND bandoLineaIntervent.progr_bando_linea_intervento = ?";
            }

            LOG.info(sql);
            lista = getJdbcTemplate().query(
                    sql,
                    ps -> {
                        ps.setString(1, nomeBeneficiario);
                        if(progrBandoLineaIntervent != -1){
                            ps.setLong(2, progrBandoLineaIntervent);
                        }
                    },
                    new SuggestIdDescVORowMapper()
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to suggestBeneficiario", e);
            throw new DaoException("DaoException while trying to suggestBeneficiario", e);
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to suggestBeneficiario", e);
            throw new DaoException("DaoException while trying to suggestBeneficiario", e);
        }
        finally {
            LOG.info(prf + " END");
        }

        return lista;
    }
    @Override
    public List<SuggestIdDescVO> suggestBandoLineaIntervent(String nomeBandoLinea, Long idSoggetto) throws DaoException {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIdDescVO> suggestIdDescVOList;

        try{
            String sql =
                    "SELECT DISTINCT \n" +
                    "bandoLineaIntervent.PROGR_BANDO_LINEA_INTERVENTO AS id,\n" +
                    "bandoLineaIntervent.NOME_BANDO_LINEA AS descrizione\n" +
                    "FROM PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervent\n" +
                    "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP enteComp ON enteComp.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.progr_bando_linea_intervento = bandoLineaIntervent.progr_bando_linea_intervento\n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = domanda.id_domanda \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggetto ON soggetto.id_progetto = progetto.id_progetto \n" +
                    "WHERE " + (idSoggetto!=-1 ? "soggetto.id_soggetto = ? AND soggetto.id_tipo_anagrafica = 1 AND soggetto.id_tipo_beneficiario <> 4 AND \n" : "\n") +
                    "enteComp.ID_ENTE_COMPETENZA = '2' \n" +
                    "AND enteComp.ID_RUOLO_ENTE_COMPETENZA = '1' \n" +
                    "AND UPPER(bandoLineaIntervent.NOME_BANDO_LINEA) LIKE CONCAT('%', CONCAT(?,'%')) \n" +
                    "ORDER BY bandoLineaIntervent.NOME_BANDO_LINEA";

            LOG.info(sql);

            suggestIdDescVOList = getJdbcTemplate().query(
                    sql,
                    ps -> {
                        if(idSoggetto != -1){
                            ps.setLong(1, idSoggetto);
                            ps.setString(2, nomeBandoLinea);
                        }else{
                            ps.setString(1, nomeBandoLinea);
                        }
                    },
                    new SuggestIdDescVORowMapper()
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to suggestBandoLineaIntervent", e);
            throw new DaoException("DaoException while trying to suggestBandoLineaIntervent", e);
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to suggestBandoLineaIntervent", e);
            throw new DaoException("DaoException while trying to suggestBandoLineaIntervent", e);
        }
        finally {
            LOG.info(prf + " END");
        }

        return suggestIdDescVOList;
    }
    @Override
    public List<SuggestIdDescVO> suggestProgetto(String codiceVisualizzatoProgetto, Long progrBandoLineaIntervent, Long idSoggetto) throws DaoException {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIdDescVO> lista;

        try{
            String sql =
                    "SELECT DISTINCT \n" +
                    "progetto.id_progetto AS id, \n" +
                    "progetto.codice_visualizzato AS descrizione, \n" +
                    "progetto.titolo_progetto as altro \n" +
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
                    "upper(progetto.codice_visualizzato) LIKE CONCAT('%', CONCAT(upper(?),'%'))";

            LOG.info(sql);

            lista = getJdbcTemplate().query(
                    sql,
                    ps -> {
                        ps.setLong(1, progrBandoLineaIntervent);
                        ps.setLong(2, idSoggetto);
                        ps.setString(3, codiceVisualizzatoProgetto);
                    },
                    new SuggestIdDescVORowMapper()
            );
        } catch (IncorrectResultSizeDataAccessException e) {
            LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to suggestProgetto", e);
            throw new DaoException("DaoException while trying to suggestProgetto", e);
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to suggestProgetto", e);
            throw new DaoException("DaoException while trying to suggestProgetto", e);
        }
        finally {
            LOG.info(prf + " END");
        }

        return lista;
    }
    @Override
    public List<SuggestIdDescVO> suggestCausaRevoche(FiltroRevocaVO filtroRevocaVO){
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<SuggestIdDescVO> lista;
        try{
            StringBuilder query = new StringBuilder();
            query.append(
                    "SELECT DISTINCT \n" +
                    "causaleBlocco.id_causale_blocco AS id, \n" +
                    "causaleBlocco.desc_causale_blocco AS descrizione\n" +
                    "FROM PBANDI_D_CAUSALE_BLOCCO causaleBlocco \n" +
                    "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON gestioneRevoca.id_causale_blocco = causaleBlocco.id_causale_blocco \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = gestioneRevoca.id_progetto \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = soggettoProgetto.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnte ON bandoLineaEnte.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                    "LEFT JOIN PBANDI_D_STATO_REVOCA statoRevoca ON statoRevoca.id_stato_revoca = gestioneRevoca.id_stato_revoca\n" +
                    "LEFT JOIN PBANDI_D_ATTIVITA_REVOCA attivitaRevoca ON attivitaRevoca.id_attivita_revoca = gestioneRevoca.id_attivita_revoca \n" +
                    "WHERE causaleBlocco.flag_revoca = 'S'\n" +
                    "AND soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                    "AND bandoLineaEnte.id_ente_competenza = 2 \n" +
                    "AND bandoLineaEnte.id_ruolo_ente_competenza = 1 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL \n" +
                    "AND gestioneRevoca.id_tipologia_revoca = ? \n"); //SELECT ALL

            ArrayList<Object> args = new ArrayList<>();
            args.add(filtroRevocaVO.getIdTipologiaRevoca());

            if(filtroRevocaVO.getNumeroRevoca() != null){
                args.add(filtroRevocaVO.getNumeroRevoca());
                query.append("AND \n" +
                        "gestioneRevoca.numero_revoca = ? \n");
            }
            if(filtroRevocaVO.getIdSoggetto() != null){
                args.add(filtroRevocaVO.getIdSoggetto());
                query.append("AND \n" +
                        "soggettoProgetto.id_soggetto = ? \n");
            }
            if(filtroRevocaVO.getIdProgetto() != null){
                args.add(filtroRevocaVO.getIdProgetto());
                query.append("AND \n" +
                        "soggettoProgetto.id_progetto = ? \n");
            }
            if(filtroRevocaVO.getProgrBandoLineaIntervent() != null){
                args.add(filtroRevocaVO.getProgrBandoLineaIntervent());
                query.append("AND \n" +
                        "bandoLinea.progr_bando_linea_intervento = ? \n");
            }
            if(filtroRevocaVO.getIdStatoRevoca() != null){
                args.add(filtroRevocaVO.getIdStatoRevoca());
                query.append("AND \n" +
                        "statoRevoca.id_stato_revoca = ? \n");
            }
            if(filtroRevocaVO.getIdAttivitaRevoca() != null){
                args.add(filtroRevocaVO.getIdAttivitaRevoca());
                query.append("AND \n" +
                        "attivitaRevoca.id_attivita_revoca = ? \n");
            }
            if(filtroRevocaVO.getDataRevocaFrom() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaFrom().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione >= ? \n");
            }
            if(filtroRevocaVO.getDataRevocaTo() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaTo().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione <= ? \n");
            }
            query.append("ORDER BY causaleBlocco.desc_causale_blocco ");

            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

            lista = getJdbcTemplate().query(
                    query.toString(),
                    ps -> {
                        for(int i = 0; i < args.size(); i++){
                            ps.setObject(i+1, args.get(i));
                        }
                    },
                    new SuggestIdDescVORowMapper()
            );

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to suggestCausaRevoche", e);
            throw new ErroreGestitoException("DaoException while trying to suggestCausaRevoche", e);
        } finally {
            LOG.info(prf + " END");
        }
        return lista;
    }
    @Override
    public List<SuggestIdDescVO> suggestStatoRevoche(FiltroRevocaVO filtroRevocaVO) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<SuggestIdDescVO> lista;
        try{

            StringBuilder query = new StringBuilder();
            query.append(
                    "SELECT DISTINCT \n" +
                    "statoRevoca.id_stato_revoca AS id, \n" +
                    "statoRevoca.desc_stato_revoca AS descrizione \n" +
                    "FROM PBANDI_D_STATO_REVOCA statoRevoca \n" +
                    "JOIN PBANDI_R_STATO_TIPO_REVOCA statoTipoRevoca ON statoTipoRevoca.id_stato_revoca = statoRevoca.id_stato_revoca \n" +
                    "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON gestioneRevoca.id_stato_revoca = statoRevoca.id_stato_revoca \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = gestioneRevoca.id_progetto \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = soggettoProgetto.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnte ON bandoLineaEnte.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                    "LEFT JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = gestioneRevoca.id_causale_blocco \n" +
                    "LEFT JOIN PBANDI_D_ATTIVITA_REVOCA attivitaRevoca ON attivitaRevoca.id_attivita_revoca = gestioneRevoca.id_attivita_revoca \n" +
                    "WHERE soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                    "AND bandoLineaEnte.id_ente_competenza = 2 \n" +
                    "AND bandoLineaEnte.id_ruolo_ente_competenza = 1 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL \n" +
                    "AND gestioneRevoca.id_tipologia_revoca = ? \n"); //SELECT ALL

            ArrayList<Object> args = new ArrayList<>();
            args.add(filtroRevocaVO.getIdTipologiaRevoca());

            if(filtroRevocaVO.getNumeroRevoca() != null){
                args.add(filtroRevocaVO.getNumeroRevoca());
                query.append("AND \n" +
                        "gestioneRevoca.numero_revoca = ? \n");
            }
            if(filtroRevocaVO.getIdSoggetto() != null){
                args.add(filtroRevocaVO.getIdSoggetto());
                query.append("AND \n" +
                        "soggettoProgetto.id_soggetto = ? \n");
            }
            if(filtroRevocaVO.getIdProgetto() != null){
                args.add(filtroRevocaVO.getIdProgetto());
                query.append("AND \n" +
                        "soggettoProgetto.id_progetto = ? \n");
            }
            if(filtroRevocaVO.getProgrBandoLineaIntervent() != null){
                args.add(filtroRevocaVO.getProgrBandoLineaIntervent());
                query.append("AND \n" +
                        "bandoLinea.progr_bando_linea_intervento = ? \n");
            }
            if(filtroRevocaVO.getIdCausaRevoca() != null){
                args.add(filtroRevocaVO.getIdCausaRevoca());
                query.append("AND \n" +
                        "causaleBlocco.id_causale_blocco = ? \n");
            }
            if(filtroRevocaVO.getIdAttivitaRevoca() != null){
                args.add(filtroRevocaVO.getIdAttivitaRevoca());
                query.append("AND \n" +
                        "attivitaRevoca.id_attivita_revoca = ? \n");
            }
            if(filtroRevocaVO.getDataRevocaFrom() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaFrom().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione >= ? \n");
            }
            if(filtroRevocaVO.getDataRevocaTo() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaTo().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione <= ? \n");
            }
            query.append("ORDER BY statoRevoca.desc_stato_revoca \n");

            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

            lista = getJdbcTemplate().query(
                    query.toString(),
                    ps -> {
                        for(int i = 0; i < args.size(); i++){
                            ps.setObject(i+1, args.get(i));
                        }
                    },
                    new SuggestIdDescVORowMapper()
            );

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to suggestStatoRevoche", e);
            throw new ErroreGestitoException("DaoException while trying to suggestStatoRevoche", e);
        } finally {
            LOG.info(prf + " END");
        }
        return lista;
    }
    @Override
    public List<SuggestIdDescVO> suggestAttivitaRevoche(FiltroRevocaVO filtroRevocaVO) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<SuggestIdDescVO> lista;
        try{

            StringBuilder query = new StringBuilder();
            query.append(
                    "SELECT DISTINCT \n" +
                    "attivitaRevoca.id_attivita_revoca AS id,\n" +
                    "attivitaRevoca.desc_attivita_revoca AS descrizione \n" +
                    "FROM PBANDI_D_ATTIVITA_REVOCA attivitaRevoca \n" +
                    "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON gestioneRevoca.id_attivita_revoca = attivitaRevoca.id_attivita_revoca \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = gestioneRevoca.id_progetto \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = soggettoProgetto.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnte ON bandoLineaEnte.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                    "LEFT JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = gestioneRevoca.id_causale_blocco \n" +
                    "LEFT JOIN PBANDI_D_STATO_REVOCA statoRevoca ON statoRevoca.id_stato_revoca = gestioneRevoca.id_stato_revoca \n" +
                    "WHERE soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                    "AND bandoLineaEnte.id_ente_competenza = 2 \n" +
                    "AND bandoLineaEnte.id_ruolo_ente_competenza = 1 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL \n" +
                    "AND gestioneRevoca.id_tipologia_revoca = ?"); //SELECT ALL

            ArrayList<Object> args = new ArrayList<>();
            args.add(filtroRevocaVO.getIdTipologiaRevoca());

            if(filtroRevocaVO.getNumeroRevoca() != null){
                args.add(filtroRevocaVO.getNumeroRevoca());
                query.append("AND \n" +
                        "gestioneRevoca.numero_revoca = ? \n");
            }
            if(filtroRevocaVO.getIdSoggetto() != null){
                args.add(filtroRevocaVO.getIdSoggetto());
                query.append("AND \n" +
                        "soggettoProgetto.id_soggetto = ? \n");
            }
            if(filtroRevocaVO.getIdProgetto() != null){
                args.add(filtroRevocaVO.getIdProgetto());
                query.append("AND \n" +
                        "soggettoProgetto.id_progetto = ? \n");
            }
            if(filtroRevocaVO.getProgrBandoLineaIntervent() != null){
                args.add(filtroRevocaVO.getProgrBandoLineaIntervent());
                query.append("AND \n" +
                        "bandoLinea.progr_bando_linea_intervento = ? \n");
            }
            if(filtroRevocaVO.getIdCausaRevoca() != null){
                args.add(filtroRevocaVO.getIdCausaRevoca());
                query.append("AND \n" +
                        "causaleBlocco.id_causale_blocco = ? \n");
            }
            if(filtroRevocaVO.getIdStatoRevoca() != null){
                args.add(filtroRevocaVO.getIdStatoRevoca());
                query.append("AND \n" +
                        "statoRevoca.id_stato_revoca = ? \n");
            }
            if(filtroRevocaVO.getDataRevocaFrom() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaFrom().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione >= ? \n");
            }
            if(filtroRevocaVO.getDataRevocaTo() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaTo().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione <= ? \n");
            }
            query.append("ORDER BY attivitaRevoca.desc_attivita_revoca \n");

            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

            lista = getJdbcTemplate().query(
                    query.toString(),
                    ps -> {
                        for(int i = 0; i < args.size(); i++){
                            ps.setObject(i+1, args.get(i));
                        }
                    },
                    new SuggestIdDescVORowMapper()
            );

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to suggestAttivitaRevoche", e);
            throw new ErroreGestitoException("DaoException while trying to suggestAttivitaRevoche", e);
        } finally {
            LOG.info(prf + " END");
        }
        return lista;
    }
    @Override
    public List<SuggestIdDescVO> suggestAllCausaRevoca() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIdDescVO> lista;

        StringBuilder query = new StringBuilder();
        query.append(
                "SELECT DISTINCT \n" +
                "causaleBlocco.id_causale_blocco AS id, \n" +
                "causaleBlocco.desc_causale_blocco AS descrizione \n" +
                "FROM PBANDI_D_CAUSALE_BLOCCO causaleBlocco \n" +
                "WHERE causaleBlocco.flag_revoca = 'S' \n" +
                "AND causaleBlocco.DESC_BREVE_CAUSALE_BLOCCO != 'Esito dich con erog' \n" +
                "ORDER BY causaleBlocco.desc_causale_blocco"
        );

        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

        lista = getJdbcTemplate().query(
                query.toString(),
                new SuggestIdDescVORowMapper()
        );

        LOG.info(prf + " END");

        return lista;
    }
    @Override
    public List<SuggestIdDescVO> suggestAllAutoritaControllante() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIdDescVO> lista;

        StringBuilder query = new StringBuilder();
        query.append(
                "SELECT DISTINCT \n" +
                "categAnagrafica.id_categ_anagrafica AS id, \n" +
                "categAnagrafica.desc_categ_anagrafica AS descrizione\n" +
                "FROM PBANDI_D_CATEG_ANAGRAFICA categAnagrafica\n" +
                "WHERE categAnagrafica.flag_revoca = 'S'\n" +
                "ORDER BY categAnagrafica.desc_categ_anagrafica"
        );

        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

        lista = getJdbcTemplate().query(
                query.toString(),
                new SuggestIdDescVORowMapper()
        );

        LOG.info(prf + " END");

        return lista;
    }

    @Override
    public List<SuggestIdDescVO> getModalitaRecupero() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIdDescVO> lista;

        StringBuilder query = new StringBuilder();
        query.append(
                "SELECT DISTINCT \n" +
                "mancatoRecupero.id_mancato_recupero AS id, \n" +
                "mancatoRecupero.desc_mancato_recupero AS descrizione\n" +
                "FROM PBANDI_D_MANCATO_RECUPERO mancatoRecupero\n" +
                "WHERE mancatoRecupero.dt_fine_validita IS NULL"
        );

        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

        lista = getJdbcTemplate().query(
                query.toString(),
                new SuggestIdDescVORowMapper()
        );

        LOG.info(prf + " END");

        return lista;
    }

    @Override
    public List<SuggestIdDescVO> getMotivoRevoca() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        List<SuggestIdDescVO> lista;

        StringBuilder query = new StringBuilder();
        query.append(
                "SELECT DISTINCT \n" +
                "motivoRevoca.id_motivo_revoca AS id, \n" +
                "motivoRevoca.desc_motivo_revoca AS descrizione\n" +
                "FROM PBANDI_D_MOTIVO_REVOCA motivoRevoca\n" +
                "WHERE motivoRevoca.dt_fine_validita IS NULL"
        );

        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

        lista = getJdbcTemplate().query(
                query.toString(),
                new SuggestIdDescVORowMapper()
        );

        LOG.info(prf + " END");

        return lista;
    }

}
