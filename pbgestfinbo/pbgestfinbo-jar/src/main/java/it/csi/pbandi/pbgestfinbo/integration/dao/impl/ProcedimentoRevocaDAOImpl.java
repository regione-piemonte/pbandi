/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.ammvoservrest.dto.ImportiRevoche;
import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.business.service.AmministrativoContabileService;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.utils.FileDTO;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.ControlloPreErogazioneDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.ProcedimentoRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.DocumentoRevocaVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.ProcedimentoRevocaMiniVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.ProcedimentoRevocaVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroProcedimentoRevocaVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProcedimentoRevocaDAOImpl extends JdbcDaoSupport implements ProcedimentoRevocaDAO {
    @Autowired
    AmministrativoContabileService amministrativoContabileService;

    @Autowired
    DocumentoManager documentoManager;

    @Autowired
    IterAutorizzativiDAO iterAutorizzativiDAO;

    @Autowired
    ControlloPreErogazioneDAO controlloPreErogazioneDAO;

    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    public ProcedimentoRevocaDAOImpl(DataSource dataSource){ setDataSource(dataSource); }

    @Override
    public List<ProcedimentoRevocaMiniVO> getProcedimentoRevoca(FiltroProcedimentoRevocaVO filtroProcedimentoRevocaVO) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<ProcedimentoRevocaMiniVO> lista;
        try{

            StringBuilder query = new StringBuilder();
            query.append("SELECT \n" +
                    "gestioneRevoca.id_gestione_revoca AS idProcedimentoRevoca, \n" +
                    "gestioneRevoca.numero_revoca AS numeroProcedimentoRevoca, \n" +
                    "soggetto.id_soggetto AS idSoggetto, \n" +
                    "soggetto.codice_fiscale_soggetto AS codiceFiscale, \n" +
                    "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.id_persona_fisica) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.id_ente_giuridico END) END) AS idBeneficiario, \n" +
                    "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazioneBeneficiario, \n" +
                    "domanda.id_domanda AS idDomanda, \n" +
                    "bandoLinea.progr_bando_linea_intervento AS progrBandoLineaIntervento, \n" +
                    "bandoLinea.nome_bando_linea AS nomeBandoLinea, \n" +
                    "progetto.id_progetto AS idProgetto, \n" +
                    "progetto.titolo_progetto AS titoloProgetto, \n" +
                    "progetto.codice_visualizzato AS codiceVisualizzatoProgetto, \n" +
                    "blocco.id_causale_blocco AS idCausaleBlocco, \n" +
                    "blocco.desc_causale_blocco AS descCausaleBlocco, \n" +
                    "gestioneRevoca.dt_gestione AS dataProcedimentoRevoca, \n" +
                    "gestioneRevoca.dt_notifica AS dataNotifica, \n" +
                    "statoRevoca.id_stato_revoca AS idStatoRevoca, \n" +
                    "statoRevoca.desc_stato_revoca AS descStatoRevoca, \n" +
                    "gestioneRevoca.dt_stato_revoca AS dataStatoRevoca, \n" +
                    "attivita.id_attivita_revoca AS idAttivitaRevoca, \n" +
                    "attivita.desc_attivita_revoca AS descAttivitaRevoca, \n" +
                    "gestioneRevoca.dt_attivita AS dataAttivitaRevoca \n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = gestioneRevoca.id_progetto \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = soggettoProgetto.id_progetto \n" +
                    "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
                    "JOIN (SELECT DISTINCT \n" +
                    "\tsoggetto.id_soggetto,\n" +
                    "\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
                    "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                    "\tJOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_soggetto = soggetto.id_soggetto\n" +
                    "\t\tAND soggettoProgetto.ID_TIPO_ANAGRAFICA = 1 AND soggettoProgetto.ID_TIPO_BENEFICIARIO != 4\n" +
                    "\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
                    "\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
                    "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
                    "JOIN (SELECT DISTINCT \n" +
                    "\tsoggetto.id_soggetto,\n" +
                    "\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                    "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                    "\tJOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_soggetto = soggetto.id_soggetto\n" +
                    "\t\tAND soggettoProgetto.ID_TIPO_ANAGRAFICA = 1 AND soggettoProgetto.ID_TIPO_BENEFICIARIO != 4\n" +
                    "\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = soggettoProgetto.id_persona_fisica \n" +
                    "\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
                    "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnte ON bandoLineaEnte.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                    "LEFT JOIN PBANDI_D_CAUSALE_BLOCCO blocco ON blocco.id_causale_blocco = gestioneRevoca.id_causale_blocco \n" +
                    "LEFT JOIN PBANDI_D_STATO_REVOCA statoRevoca ON statoRevoca.id_stato_revoca = gestioneRevoca.id_stato_revoca \n" +
                    "LEFT JOIN PBANDI_D_ATTIVITA_REVOCA attivita ON attivita.id_attivita_revoca = gestioneRevoca.id_attivita_revoca \n" +
                    "WHERE \n" +
                    "gestioneRevoca.id_tipologia_revoca = 2 \n" +
                    "AND \n" +
                    "gestioneRevoca.dt_fine_validita IS NULL \n" +
                    "AND \n" +
                    "soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND \n" +
                    "soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                    "AND \n" +
                    "bandoLineaEnte.id_ente_competenza = 2 \n" +
                    "AND \n" +
                    "bandoLineaEnte.id_ruolo_ente_competenza = 1 \n"); //SELECT ALL

            ArrayList<Object> args = new ArrayList<>();
            if(filtroProcedimentoRevocaVO.getNumeroProcedimentoRevoca() != null){
                args.add(filtroProcedimentoRevocaVO.getNumeroProcedimentoRevoca());
                query.append("AND \n" +
                        "gestioneRevoca.numero_revoca = ? \n");
            }
            if(filtroProcedimentoRevocaVO.getIdSoggetto() != null){
                args.add(filtroProcedimentoRevocaVO.getIdSoggetto());
                query.append("AND \n" +
                        "soggetto.id_soggetto = ? \n");
            }
            if(filtroProcedimentoRevocaVO.getIdProgetto() != null){
                args.add(filtroProcedimentoRevocaVO.getIdProgetto());
                query.append("AND \n" +
                        "progetto.id_progetto = ? \n");
            }
            if(filtroProcedimentoRevocaVO.getProgrBandoLineaIntervent() != null){
                args.add(filtroProcedimentoRevocaVO.getProgrBandoLineaIntervent());
                query.append("AND \n" +
                        "bandoLinea.progr_bando_linea_intervento = ? \n");
            }
            if(filtroProcedimentoRevocaVO.getIdCausaleBlocco() != null){
                args.add(filtroProcedimentoRevocaVO.getIdCausaleBlocco());
                query.append("AND \n" +
                        "blocco.id_causale_blocco = ? \n");
            }
            if(filtroProcedimentoRevocaVO.getIdStatoRevoca() != null){
                args.add(filtroProcedimentoRevocaVO.getIdStatoRevoca());
                query.append("AND \n" +
                        "statoRevoca.id_stato_revoca = ? \n");
            }
            if(filtroProcedimentoRevocaVO.getIdAttivitaRevoca() != null){
                args.add(filtroProcedimentoRevocaVO.getIdAttivitaRevoca());
                query.append("AND \n" +
                        "attivita.id_attivita_revoca = ? \n");
            }
            if(filtroProcedimentoRevocaVO.getDataProcedimentoRevocaFrom() != null){
                args.add(new java.sql.Date(filtroProcedimentoRevocaVO.getDataProcedimentoRevocaFrom().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione >= ? ");
            }
            if(filtroProcedimentoRevocaVO.getDataProcedimentoRevocaTo() != null){
                args.add(new java.sql.Date(filtroProcedimentoRevocaVO.getDataProcedimentoRevocaTo().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione <= ? ");
            }

            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

            lista = getJdbcTemplate().query(
                    query.toString(),
                    ps -> {
                        for(int i = 0; i < args.size(); i++){
                            ps.setObject(i+1, args.get(i));
                        }
                    },
                    new ProcedimentoRevocaMiniVORowMapper()
            );

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read ProcedimentoRevocaVO", e);
            throw new ErroreGestitoException("DaoException while trying to read ProcedimentoRevocaVO", e);
        } finally {
            LOG.info(prf + " END");
        }
        return lista;
    }

    @Override
    public ProcedimentoRevocaVO getDettaglioProcedimentoRevoca(Long idProcedimentoRevoca, Long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        String query1 =
                "SELECT  \n" +
                "gestioneRevoca.id_gestione_revoca AS idProcedimentoRevoca,\n" +
                "gestioneRevoca.numero_revoca AS numeroProcedimentoRevoca, \n" +
                "soggetto.id_soggetto AS idSoggetto,  \n" +
                "soggetto.codice_fiscale_soggetto AS codiceFiscaleSoggetto,\n" +
                "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.id_persona_fisica) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.id_ente_giuridico END) END) AS idBeneficiario, \n" +
                "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazioneBeneficiario,\n" +
                "domanda.id_domanda AS idDomanda, \n" +
                "bandoLinea.progr_bando_linea_intervento AS progrBandoLineaIntervento, \n" +
                "bandoLinea.nome_bando_linea AS nomeBandoLinea, \n" +
                "progetto.id_progetto AS idProgetto, \n" +
                "progetto.titolo_progetto AS titoloProgetto, \n" +
                "progetto.codice_visualizzato AS codiceVisualizzatoProgetto, \n" +
                "blocco.id_causale_blocco AS idCausaleBlocco, \n" +
                "blocco.desc_causale_blocco AS descCausaleBlocco, \n" +
                "autoritaControllante.id_categ_anagrafica AS idAutoritaControllante,\n" +
                "autoritaControllante.desc_categ_anagrafica AS descAutoritaControllante,\n" +
                "statoRevoca.id_stato_revoca AS idStatoRevoca, \n" +
                "statoRevoca.desc_stato_revoca AS descStatoRevoca, \n" +
                "gestioneRevoca.dt_stato_revoca AS dataStatoRevoca, \n" +
                "attivita.id_attivita_revoca AS idAttivitaRevoca, \n" +
                "attivita.desc_attivita_revoca AS descAttivitaRevoca, \n" +
                "gestioneRevoca.dt_attivita AS dataAttivitaRevoca, \n" +
                "gestioneRevoca.dt_notifica AS dataNotifica,\n" +
                "gestioneRevoca.gg_risposta AS giorniScadenza,\n" +
                "(CASE WHEN gestioneRevoca.dt_notifica IS NOT NULL THEN (gestioneRevoca.dt_notifica + gestioneRevoca.gg_risposta) ELSE (NULL) END) AS dataScadenza,\n" +
                "gestioneRevoca.flag_proroga AS proroga,\n" +
                "gestioneRevoca.note AS note,\n" +
                "istruttore.id_soggetto AS idSoggettoIstruttore,\n" +
                "istruttore.nome || ' ' || istruttore.cognome AS denominazioneIstruttore,\n" +
                "gestioneRevoca.num_protocollo AS numeroProtocollo,\n" +
                "gestioneRevoca.dt_gestione AS dataAvvioProcedimentoRevoca,\n" +
                "gestioneRevoca.imp_da_revocare_contrib AS importoDaRevocareContributo, \n" +
                "gestioneRevoca.imp_da_revocare_finanz AS importoDaRevocareFinanziamento, \n" +
                "gestioneRevoca.imp_da_revocare_garanzia AS importoDaRevocareGaranzia,\n" +
                //valori per invia incarico ad erogazione
                "gestioneRevoca.imp_ires AS impIres,\n" +
                "gestioneRevoca.IMP_DA_EROGARE_CONTRIBUTO AS impDaErogareContributo,\n" +
                "causaleErogazioneContr.DESC_CAUSALE AS causaleErogazioneContributo,\n" +
                "gestioneRevoca.IMP_DA_EROGARE_FINANZIAMENTO AS impDaErogareFinanziamento,\n" +
                "causaleErogazioneFinanz.DESC_CAUSALE AS causaleErogazioneFinanziamento,\n" +
                "gestioneRevoca.id_dichiarazione_spesa AS idDichiarazioneSpesa\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "LEFT JOIN PBANDI_D_CAUSALE_EROGAZIONE causaleErogazioneContr ON causaleErogazioneContr.id_causale_erogazione = gestioneRevoca.id_causale_erog_contr \n" +
                "LEFT JOIN PBANDI_D_CAUSALE_EROGAZIONE causaleErogazioneFinanz ON causaleErogazioneFinanz.id_causale_erogazione = gestioneRevoca.id_causale_erog_fin \n" +
                "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = gestioneRevoca.id_progetto \n" +
                "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = soggettoProgetto.id_progetto \n" +
                "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
                "JOIN (SELECT DISTINCT \n" +
                "\tsoggetto.id_soggetto,\n" +
                "\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
                "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                "\tJOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_soggetto = soggetto.id_soggetto\n" +
                "\t\tAND soggettoProgetto.ID_TIPO_ANAGRAFICA = 1 AND soggettoProgetto.ID_TIPO_BENEFICIARIO != 4\n" +
                "\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
                "\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
                "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
                "JOIN (SELECT DISTINCT \n" +
                "\tsoggetto.id_soggetto,\n" +
                "\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                "\tJOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_soggetto = soggetto.id_soggetto\n" +
                "\t\tAND soggettoProgetto.ID_TIPO_ANAGRAFICA = 1 AND soggettoProgetto.ID_TIPO_BENEFICIARIO != 4\n" +
                "\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = soggettoProgetto.id_persona_fisica \n" +
                "\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
                "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
                "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLinea ON bandoLinea.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                "JOIN PBANDI_R_BANDO_LINEA_ENTE_COMP bandoLineaEnte ON bandoLineaEnte.progr_bando_linea_intervento = bandoLinea.progr_bando_linea_intervento \n" +
                "LEFT JOIN PBANDI_D_CATEG_ANAGRAFICA autoritaControllante ON autoritaControllante.id_categ_anagrafica = gestioneRevoca.id_categ_anagrafica \n" +
                "LEFT JOIN PBANDI_D_CAUSALE_BLOCCO blocco ON blocco.id_causale_blocco = gestioneRevoca.id_causale_blocco \n" +
                "LEFT JOIN PBANDI_D_STATO_REVOCA statoRevoca ON statoRevoca.id_stato_revoca = gestioneRevoca.id_stato_revoca \n" +
                "LEFT JOIN PBANDI_D_ATTIVITA_REVOCA attivita ON attivita.id_attivita_revoca = gestioneRevoca.id_attivita_revoca \n" +
                "JOIN (SELECT gestioneRevoca.numero_revoca AS numero_revoca,\n" +
                "\tmin(gestioneRevoca.id_gestione_revoca) AS id_gestione_revoca\n" +
                "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "\tWHERE gestioneRevoca.id_tipologia_revoca = 2\n" +
                "\tGROUP BY gestioneRevoca.numero_revoca) primaRevoca ON primaRevoca.numero_revoca = gestioneRevoca.numero_revoca\n" +
                "LEFT JOIN PBANDI_T_GESTIONE_REVOCA nuovaRevoca ON nuovaRevoca.id_gestione_revoca = primaRevoca.id_gestione_revoca\n" +
                "LEFT JOIN PBANDI_T_UTENTE utente ON utente.id_utente = nuovaRevoca.id_utente_ins \n" +
                "LEFT JOIN (SELECT DISTINCT \n" +
                "\tsoggetto.id_soggetto,\n" +
                "\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                "\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
                "\tGROUP BY soggetto.id_soggetto) istruttoreUnivoco ON istruttoreUnivoco.id_soggetto = utente.id_soggetto \n" +
                "LEFT JOIN PBANDI_T_PERSONA_FISICA istruttore ON istruttore.id_persona_fisica = istruttoreUnivoco.id_persona_fisica \n" +
                "WHERE gestioneRevoca.id_gestione_revoca = ?\n" +
                "AND \n" +
                "gestioneRevoca.id_tipologia_revoca = 2 \n" +
                "AND \n" +
                "gestioneRevoca.dt_fine_validita IS NULL \n" +
                "AND \n" +
                "soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                "AND \n" +
                "soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                "AND \n" +
                "bandoLineaEnte.id_ente_competenza = 2 \n" +
                "AND \n" +
                "bandoLineaEnte.id_ruolo_ente_competenza = 1";
        LOG.info(prf + "Query: \n\n" + query1 + "\n");

        ProcedimentoRevocaVO data;
        try{
            data = getJdbcTemplate().queryForObject(
                    query1,
                    new Object[] {idProcedimentoRevoca},
                    new ProcedimentoRevocaVORowMapper()
            );
        }catch (EmptyResultDataAccessException e) {
            data = null;
        }

        if(data != null) {
            //Importi concessi
            query1 =
                    "SELECT \n" +
                            "impAmmesso.importo_ammesso_iniziale AS importoAmmessoIniziale,\n" +
                            "prcema.quota_importo_agevolato AS importoConcesso,\n" +
                            "pdma.desc_modalita_agevolazione AS descModalitaAgevolazione,\n" +
                            "pdma.id_modalita_agevolazione_rif AS idModalitaAgevolazioneRif\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                            "JOIN PBANDI_T_PROGETTO ptp ON ptp.id_progetto = ptgr.id_progetto \n" +
                            "JOIN PBANDI_T_DOMANDA ptd ON ptd.id_domanda = ptp.id_domanda\n" +
                            "JOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.id_domanda = ptd.id_domanda\n" +
                            "JOIN (\n" +
                            "SELECT MIN(ptce.DT_INIZIO_VALIDITA) AS DT_INIZIO_VALIDITA, ptce.ID_DOMANDA \n" +
                            "FROM PBANDI_T_CONTO_ECONOMICO ptce \n" +
                            "GROUP BY ptce.ID_DOMANDA \n" +
                            ") ptceOriginale ON ptceOriginale.ID_DOMANDA = ptce.ID_DOMANDA \n" +
                            "\tAND ptceOriginale.DT_INIZIO_VALIDITA = ptce.DT_INIZIO_VALIDITA \n" +
                            "JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema ON prcema.id_conto_economico = ptce.id_conto_economico\n" +
                            "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = prcema.ID_MODALITA_AGEVOLAZIONE \n" +
                            "LEFT JOIN ( /* IMPORTO AMMESSO INIZIALE */\n" +
                            "\tSELECT ptce.ID_DOMANDA, prcema.ID_MODALITA_AGEVOLAZIONE, prcema.IMPORTO_AMMESSO_FINPIS AS importo_ammesso_iniziale\n" +
                            "\tFROM PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema \n" +
                            "\tJOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO \n" +
                            "\tWHERE ptce.DT_FINE_VALIDITA IS NULL\n" +
                            ") impAmmesso ON impAmmesso.id_modalita_agevolazione = pdma.id_modalita_agevolazione\n" +
                            "\tAND impAmmesso.id_domanda = ptd.id_domanda\n" +
                            "WHERE ptgr.id_gestione_revoca = ?";
            try{
                ProcedimentoRevocaVO finalData = data;
                getJdbcTemplate().query(
                    query1,
                    rs -> {
                        switch (rs.getInt("idModalitaAgevolazioneRif")){
                            case 1:
                                finalData.setImportoAmmessoContributo(rs.getBigDecimal("importoAmmessoIniziale"));
                                finalData.setModalitaAgevolazioneContributo(rs.getString("descModalitaAgevolazione"));
                                finalData.setImportoConcessoContributo(rs.getBigDecimal("importoConcesso"));
                                if(finalData.getImportoConcessoContributo() == null){
                                    finalData.setImportoConcessoContributo(BigDecimal.ZERO);
                                }
                                break;
                            case 5:
                                finalData.setImportoAmmessoFinanziamento(rs.getBigDecimal("importoAmmessoIniziale"));
                                finalData.setModalitaAgevolazioneFinanziamento(rs.getString("descModalitaAgevolazione"));
                                finalData.setImportoConcessoFinanziamento(rs.getBigDecimal("importoConcesso"));
                                if(finalData.getImportoConcessoFinanziamento() == null){
                                    finalData.setImportoConcessoFinanziamento(BigDecimal.ZERO);
                                }
                                break;
                            case 10:
                                finalData.setImportoAmmessoGaranzia(rs.getBigDecimal("importoAmmessoIniziale"));
                                finalData.setModalitaAgevolazioneGaranzia(rs.getString("descModalitaAgevolazione"));
                                finalData.setImportoConcessoGaranzia(rs.getBigDecimal("importoConcesso"));
                                if(finalData.getImportoConcessoGaranzia() == null){
                                    finalData.setImportoConcessoGaranzia(BigDecimal.ZERO);
                                }
                                break;
                            default:
                                break;
                        }
                    },
                    data.getIdProcedimentoRevoca()
                );
                data = finalData;
            } catch (EmptyResultDataAccessException ignored){}
            //Importi revocati
            query1 =
                    "SELECT sum(ptr.importo) AS importoRevocato,\n" +
                    "pdma.ID_MODALITA_AGEVOLAZIONE_RIF AS idModalitaAgevolazioneRif\n" +
                    "FROM PBANDI_T_REVOCA ptr\n" +
                    "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.id_modalita_agevolazione = ptr.id_modalita_agevolazione\n" +
                    "JOIN PBANDI_T_GESTIONE_REVOCA ptgr ON ptgr.ID_GESTIONE_REVOCA = ?\n" +
                    "WHERE ptr.id_progetto = ? AND (\n" +
                        "(ptr.ID_GESTIONE_REVOCA IS NULL AND (ptgr.DT_GESTIONE IS NULL OR ptr.DT_REVOCA+1 < ptgr.DT_GESTIONE)) OR \n" +
                        "(ptr.ID_GESTIONE_REVOCA IS NOT NULL AND ptgr.ID_GESTIONE_REVOCA > ptr.ID_GESTIONE_REVOCA)\n" +
                    ") \n" +
                    "GROUP BY pdma.ID_MODALITA_AGEVOLAZIONE_RIF";
            try{
                ProcedimentoRevocaVO finalData = data;
                getJdbcTemplate().query(
                    query1,
                    rs -> {
                        switch (rs.getInt("idModalitaAgevolazioneRif")){
                            case 1:
                                finalData.setImportoRevocatoContributo(rs.getBigDecimal("importoRevocato"));
                                break;
                            case 5:
                                finalData.setImportoRevocatoFinanziamento(rs.getBigDecimal("importoRevocato"));
                                break;
                            case 10:
                                finalData.setImportoRevocatoGaranzia(rs.getBigDecimal("importoRevocato"));
                                break;
                            default:
                                break;
                        }
                    },
                    data.getIdProcedimentoRevoca(),
                    data.getIdProgetto()
                );
                data = finalData;
            } catch (EmptyResultDataAccessException ignored){}

            query1 =
                    "SELECT \n" +
                            "ptp.id_progetto,\n" +
                            "prbli.id_bando,\n" +
                            "ptgr.id_stato_revoca,\n" +
                            "ptgr.dt_stato_revoca,\n" +
                            "pdma.id_modalita_agevolazione,\n" +
                            "pdma.id_modalita_agevolazione_rif\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA ptgr\n" +
                            "JOIN PBANDI_T_PROGETTO ptp ON ptgr.id_progetto = ptp.id_progetto \n" +
                            "JOIN PBANDI_T_DOMANDA ptd ON ptp.id_domanda = ptd.id_domanda\n" +
                            "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento \n" +
                            "JOIN PBANDI_R_BANDO_MODALITA_AGEVOL prbma ON prbli.id_bando = prbma.id_bando \n" +
                            "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON prbma.id_modalita_agevolazione = pdma.id_modalita_agevolazione\n" +
                            "WHERE ptgr.id_gestione_revoca = ?\n" +
                            "AND ptgr.id_tipologia_revoca = 2";

            LOG.info(prf + "\nQuery: \n\n" + query1 + "\n\n");
            List <ParamAmmContabileVO> params = getJdbcTemplate().query(
                    query1,
                    ps -> ps.setLong(1, idProcedimentoRevoca),
                    new ParamAmmContabileVORowMapper()
            );
            for(ParamAmmContabileVO param : params){
                try {
                    if(
                            (param.getIdModalitaAgevolazioneRif() == 1 && data.getModalitaAgevolazioneContributo() != null) ||
                            (param.getIdModalitaAgevolazioneRif() == 5 && data.getModalitaAgevolazioneFinanziamento() != null) ||
                            (param.getIdModalitaAgevolazioneRif() == 10 && data.getModalitaAgevolazioneGaranzia() != null)
                    ) {
                        ImportiRevoche[] importiRevoche = amministrativoContabileService.callToImportiRevocheImporti(
                                param.getIdProgetto(),
                                param.getIdBando(),
                                (param.getIdStato() == 3 || param.getIdStato() == 4 ? param.getDtStato() : new Date()),
                                param.getIdModalitaAgevolazione(),
                                param.getIdModalitaAgevolazioneRif(),
                                idProcedimentoRevoca,
                                idUtente
                        );

                        for (ImportiRevoche importo : importiRevoche) {
                            switch (importo.getIdAgevolazione()) {
                                case 1:
                                    switch (importo.getCausale()) {
                                        case "Erogazioni":
                                            if (data.getImportoErogatoContributo() != null) {
                                                data.setImportoErogatoContributo(data.getImportoErogatoContributo().add(BigDecimal.valueOf(importo.getImporto())));
                                            } else {
                                                data.setImportoErogatoContributo(BigDecimal.valueOf(importo.getImporto()));
                                            }
                                            break;
                                        case "Recuperi":
                                            if (data.getImportoRecuperatoContributo() != null) {
                                                data.setImportoRecuperatoContributo(data.getImportoRecuperatoContributo().add(BigDecimal.valueOf(importo.getImporto())));
                                            } else {
                                                data.setImportoRecuperatoContributo(BigDecimal.valueOf(importo.getImporto()));
                                            }
                                            break;
                                    }
                                    break;
                                case 5:
                                    switch (importo.getCausale()) {
                                        case "Erogazioni":
                                            if (data.getImportoErogatoFinanziamento() != null) {
                                                data.setImportoErogatoFinanziamento(data.getImportoErogatoFinanziamento().add(BigDecimal.valueOf(importo.getImporto())));
                                            } else {
                                                data.setImportoErogatoFinanziamento(BigDecimal.valueOf(importo.getImporto()));
                                            }
                                            break;
                                        case "Rimborsi":
                                            if (data.getImportoRimborsatoFinanziamento() != null) {
                                                data.setImportoRimborsatoFinanziamento(data.getImportoRimborsatoFinanziamento().add(BigDecimal.valueOf(importo.getImporto())));
                                            } else {
                                                data.setImportoRimborsatoFinanziamento(BigDecimal.valueOf(importo.getImporto()));
                                            }
                                            break;
                                        case "Recuperi":
                                            if (data.getImportoRecuperatoFinanziamento() != null) {
                                                data.setImportoRecuperatoFinanziamento(data.getImportoRecuperatoFinanziamento().add(BigDecimal.valueOf(importo.getImporto())));
                                            } else {
                                                data.setImportoRecuperatoFinanziamento(BigDecimal.valueOf(importo.getImporto()));
                                            }
                                            break;
                                    }
                                    break;
                                case 10:
                                    switch (importo.getCausale()) {
                                        case "Erogazioni":
                                            if (data.getImportoErogatoGaranzia() != null) {
                                                data.setImportoErogatoGaranzia(data.getImportoErogatoGaranzia().add(BigDecimal.valueOf(importo.getImporto())));
                                            } else {
                                                data.setImportoErogatoGaranzia(BigDecimal.valueOf(importo.getImporto()));
                                            }
                                            break;
                                        case "Recuperi":
                                            if (data.getImportoRecuperatoGaranzia() != null) {
                                                data.setImportoRecuperatoGaranzia(data.getImportoRecuperatoGaranzia().add(BigDecimal.valueOf(importo.getImporto())));
                                            } else {
                                                data.setImportoRecuperatoGaranzia(BigDecimal.valueOf(importo.getImporto()));
                                            }
                                            break;
                                    }
                                    break;
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.info(prf+"Errore durante la chiamata ad amministrativo contabile!");
                } finally {
                    data.setImportoConcessoNeRevocatoContributo();
                    data.setImportoConcessoNeRevocatoFinanziamento();
                    data.setImportoConcessoNeRevocatoGaranzia();
                    data.setImportoErogatoNeRecuperatoRimborsatoContributo();
                    data.setImportoErogatoNeRecuperatoRimborsatoFinanziamento();
                    data.setImportoErogatoNeRecuperatoRimborsatoGaranzia();
                }
            }
        }

        LOG.info(prf + "END");

        return data;
    }

    @Override
    public List<DocumentoRevocaVO> getDocumentiProcedimentoRevoca(Long numeroRevoca) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<DocumentoRevocaVO> data = new ArrayList<>();
        String query;

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );
        Long idEntitaControdeduz = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_CONTRODEDUZ'",
                Long.class
        );
        Long idEntitaFile = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_FILE'",
                Long.class
        );
        Long idEntitaRichiestaIntegraz = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_RICHIESTA_INTEGRAZ'",
                Long.class
        );
        Long idEntitaChecklist = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_CHECKLIST'",
                Long.class
        );

        {
            //Lettera accompagnatoria avvio procedimento di revoca idTipoDocumentoIndex = 44
            //Eventuali altri allegati all’avvio procedimento di revoca idTipoDocumentoIndex = 50, 7, 58
            //Lettera accompagnatoria archiviazione procedimento di revoca idTipoDocumentoIndex = 46
            //Altri allegati all'archiviazione procedimento di revoca idTipoDocumentoIndex = 52
            query =
                    "SELECT UNIQUE\n" +
                            "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                            "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                            "gestioneRevocaValida.dt_gestione AS dataDocumento,\n" +
                            "documenti.id_documento_index AS idDocumento,\n" +
                            "documenti.nome_file AS nomeFile\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                            "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevocaValida ON gestioneRevocaValida.numero_revoca = gestioneRevoca.numero_revoca AND gestioneRevocaValida.id_tipologia_revoca = 2 AND gestioneRevocaValida.dt_fine_validita IS NULL\n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_target = gestioneRevoca.id_gestione_revoca\n" +
                            "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index IN (44, 50, 7, 58, 46, 52)\n" +
                            "WHERE gestioneRevoca.numero_revoca = ? \n" +
                            "AND gestioneRevoca.id_tipologia_revoca in (1, 2) \n" +
                            "ORDER BY gestioneRevocaValida.dt_gestione ";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Checklist excel da DS idTipoDocumentoIndex = 33
            //Report validazione idTipoDocumentoIndex = 63
            query =
                    "SELECT UNIQUE\n" +
                            "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                            "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                            "documenti.dt_inserimento_index AS dataDocumento,\n" +
                            "documenti.id_documento_index AS idDocumento,\n" +
                            "documenti.nome_file AS nomeFile\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                            "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevocaValida ON gestioneRevocaValida.numero_revoca = gestioneRevoca.numero_revoca AND gestioneRevocaValida.id_tipologia_revoca = 2 AND gestioneRevocaValida.dt_fine_validita IS NULL\n" +
                            "JOIN PBANDI_T_CHECKLIST checklist ON checklist.id_dichiarazione_spesa = gestioneRevocaValida.id_dichiarazione_spesa\n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_target = checklist.id_checklist\n" +
                            "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index IN (33, 63)\n" +
                            "WHERE gestioneRevoca.numero_revoca = ?\n" +
                            "AND gestioneRevoca.id_tipologia_revoca in (1, 2) \n" +
                            "ORDER BY documenti.dt_inserimento_index";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaChecklist, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Allegati alla controdeduzione idTipoDocumentoIndex = 23
            query =
                    "SELECT UNIQUE\n" +
                    "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                    "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                    "controdeduz.dt_stato_controdeduz AS dataDocumento,\n" +
                    "documenti.id_documento_index AS idDocumento,\n" +
                    "documenti.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "JOIN PBANDI_T_CONTRODEDUZ controdeduz ON controdeduz.id_entita = ? AND controdeduz.id_target = gestioneRevoca.id_gestione_revoca AND controdeduz.id_stato_controdeduz = 2\n" +
                    "JOIN PBANDI_T_FILE_ENTITA fileEntita ON fileEntita.id_entita = ? AND fileEntita.id_target = controdeduz.id_controdeduz \n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_documento_index = files.id_documento_index AND documenti.id_tipo_documento_index = 23 \n" +
                    "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index\n" +
                    "WHERE gestioneRevoca.numero_revoca = ? \n" +
                    "AND gestioneRevoca.id_tipologia_revoca = 2 \n" +
                    "ORDER BY controdeduz.dt_stato_controdeduz ";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, idEntitaControdeduz, idEntitaFile, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Lettera accompagnatoria richiesta integrazione procedimento di revoca idTipoDocumentoIndex = 45
            //Eventuali altri allegati alla richiesta integrazione procedimento di revoca idTipoDocumentoIndex = 51
            query =
                    "SELECT UNIQUE\n" +
                            "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                            "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                            "integraz.dt_richiesta AS dataDocumento,\n" +
                            "documenti.id_documento_index AS idDocumento,\n" +
                            "documenti.nome_file AS nomeFile\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                            "JOIN PBANDI_T_RICHIESTA_INTEGRAZ integraz ON integraz.id_entita = ? AND integraz.id_target = gestioneRevoca.id_gestione_revoca \n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_target = integraz.id_richiesta_integraz AND documenti.id_tipo_documento_index IN (45, 51)\n" +
                            "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index\n" +
                            "WHERE gestioneRevoca.numero_revoca = ? \n" +
                            "AND gestioneRevoca.id_tipologia_revoca = 2 \n" +
                            "ORDER BY integraz.dt_richiesta ";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            query =
                    "SELECT UNIQUE\n" +
                            "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                            "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                            "null AS dataDocumento,\n" +
                            "documenti.id_documento_index AS idDocumento,\n" +
                            "documenti.nome_file AS nomeFile\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                            "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevocaValida ON gestioneRevocaValida.numero_revoca = gestioneRevoca.numero_revoca AND gestioneRevocaValida.id_tipologia_revoca = 2 AND gestioneRevocaValida.dt_fine_validita IS NULL\n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_target = gestioneRevoca.id_gestione_revoca AND documenti.id_tipo_documento_index IN (45, 51)\n" +
                            "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index\n" +
                            "WHERE gestioneRevoca.numero_revoca = ? \n" +
                            "AND gestioneRevoca.id_tipologia_revoca = 2 \n";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                List<DocumentoRevocaVO> docsBozza = getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, numeroRevoca}, new DocumentoRevocaVORowMapper());
                docsBozza.forEach(element -> element.setBozza(true));
                data.addAll(docsBozza);
            } catch (EmptyResultDataAccessException ignored) {}
            //Allegati alla richiesta di integrazione da parte del beneficiario idTipoDocumentoIndex = 23
            query =
                    "SELECT UNIQUE\n" +
                    "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                    "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                    "integraz.dt_invio AS dataDocumento,\n" +
                    "documenti.id_documento_index AS idDocumento,\n" +
                    "documenti.nome_file AS nomeFile\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "JOIN PBANDI_T_RICHIESTA_INTEGRAZ integraz ON integraz.id_entita = ? AND integraz.id_target = gestioneRevoca.id_gestione_revoca \n" +
                    "JOIN PBANDI_T_FILE_ENTITA fileEntita ON fileEntita.id_entita = ? AND fileEntita.id_target = integraz.id_richiesta_integraz \n" +
                    "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file \n" +
                    "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_documento_index = files.id_documento_index AND documenti.id_tipo_documento_index = 23\n" +
                    "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index\n" +
                    "WHERE gestioneRevoca.numero_revoca = ? \n" +
                    "AND gestioneRevoca.id_tipologia_revoca = 2 \n" +
                    "ORDER BY integraz.dt_invio ";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, idEntitaFile, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
        }
        LOG.info(prf + "END");
        return data;
    }

    public Boolean eliminaAllegato(Long idDocumentoIndex) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        boolean result;

        //DELETE
        String query = "DELETE FROM PBANDI_T_DOCUMENTO_INDEX ptdi WHERE ptdi.ID_DOCUMENTO_INDEX = ?";
        try {
            result = getJdbcTemplate().update(query, idDocumentoIndex) > 0;
        } catch (Exception e){
            throw new ErroreGestitoException("Errore durante l'eliminazione dell'allegato");
        }finally {
            LOG.info(prf + "END");
        }

        return result;
    }

    @Override
    public void updateProcedimentoRevoca(ProcedimentoRevocaVO procedimentoRevocaVO, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        BigDecimal idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                BigDecimal.class
        );
        //Data Notifica CONTROLLI
        if(procedimentoRevocaVO.getDataNotifica() != null) {
            //Verifica data notifica per avvio del procedimento
            Date dtGestione;
            String query =
                    "SELECT a.DT_GESTIONE \n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA a \n" +
                    "WHERE a.ID_GESTIONE_REVOCA = ? \n";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                dtGestione = getJdbcTemplate().queryForObject(query, new Object[]{procedimentoRevocaVO.getIdProcedimentoRevoca()}, Date.class);
            } catch (EmptyResultDataAccessException e) {
                dtGestione = null;
            }

            if(dtGestione != null && DateUtils.truncatedCompareTo(procedimentoRevocaVO.getDataNotifica(), dtGestione, Calendar.DATE) < 0){
                throw new ErroreGestitoException("Data di notifica non valida. Inserire data uguale o successiva alla data di avvio del procedimento di revoca");
            }
            //Verifica data notifica per richiesta integrazioni
            query =
                    "SELECT a.DT_RICHIESTA\n" +
                    "FROM PBANDI_T_RICHIESTA_INTEGRAZ a \n" +
                    "WHERE a.ID_ENTITA = ? \n" +
                    "AND ID_STATO_RICHIESTA='1'\n" +
                    "AND a.ID_TARGET = ? \n";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            try {
                dtGestione = getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, procedimentoRevocaVO.getIdProcedimentoRevoca()}, Date.class);
            } catch (EmptyResultDataAccessException e) {
                dtGestione = null;
            }
            if(dtGestione != null && DateUtils.truncatedCompareTo(procedimentoRevocaVO.getDataNotifica(), dtGestione, Calendar.DATE) < 0){
                throw new ErroreGestitoException("Data di notifica non valida. Inserire data uguale o successiva alla data di richiesta di integrazioni");
            }
        }
        //Importi CONTROLLI
        if(procedimentoRevocaVO.getImportoDaRevocareContributo() != null || procedimentoRevocaVO.getImportoDaRevocareFinanziamento() != null || procedimentoRevocaVO.getImportoDaRevocareGaranzia() != null){
            BigDecimal importo;
            String query =
                    "SELECT QUOTA_IMPORTO_AGEVOLATO - NVL(importoRevocato.importo, 0) AS concessoResiduo \n" +
                            "FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV a\n" +
                            "JOIN PBANDI_T_CONTO_ECONOMICO b ON a.ID_CONTO_ECONOMICO = b.ID_CONTO_ECONOMICO \n" +
                            "JOIN PBANDI_T_PROGETTO c ON b.ID_DOMANDA = c.ID_DOMANDA \n" +
                            "JOIN PBANDI_T_GESTIONE_REVOCA d ON c.ID_PROGETTO = d.ID_PROGETTO\n" +
                            "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.ID_MODALITA_AGEVOLAZIONE = a.ID_MODALITA_AGEVOLAZIONE \n" +
                            "LEFT JOIN (\n" +
                            "SELECT sum(ptr.importo) AS importo, \n" +
                            "ptgr.id_gestione_revoca,\n" +
                            "pdma.id_modalita_agevolazione_rif\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                            "JOIN PBANDI_T_REVOCA ptr ON ptr.id_progetto = ptgr.id_progetto\n" +
                            "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.id_modalita_agevolazione = ptr.id_modalita_agevolazione\n" +
                            "WHERE (\n" +
                            "(ptr.ID_GESTIONE_REVOCA IS NULL AND (ptgr.DT_GESTIONE IS NULL OR ptr.DT_REVOCA+1 < ptgr.DT_GESTIONE)) OR \n" +
                            "(ptr.ID_GESTIONE_REVOCA IS NOT NULL AND ptgr.ID_GESTIONE_REVOCA > ptr.ID_GESTIONE_REVOCA)\n" +
                            ") GROUP BY ptgr.ID_GESTIONE_REVOCA, pdma.id_modalita_agevolazione_rif\n" +
                            ") importoRevocato ON importoRevocato.id_gestione_revoca = d.id_gestione_revoca\n" +
                            "\tAND importoRevocato.id_modalita_agevolazione_rif = modAgev.id_modalita_agevolazione_rif\n" +
                            "WHERE modAgev.ID_MODALITA_AGEVOLAZIONE_RIF = ?\n" +
                            "AND b.DT_FINE_VALIDITA IS NULL \n" +
                            "AND d.ID_GESTIONE_REVOCA = ?";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            //Contributo
            if(procedimentoRevocaVO.getImportoDaRevocareContributo() != null) {
                try {
                    importo = getJdbcTemplate().queryForObject(
                            query,
                            new Object[]{1, procedimentoRevocaVO.getIdProcedimentoRevoca()},
                            BigDecimal.class
                    );
                } catch (EmptyResultDataAccessException e) {
                    importo = null;
                }
                if(importo == null || importo.compareTo(procedimentoRevocaVO.getImportoDaRevocareContributo()) < 0){
                    throw new ErroreGestitoException("Il valore inserito da revocare è maggiore dell’importo concesso");
                }
            }
            //Finanziamento
            if(procedimentoRevocaVO.getImportoDaRevocareFinanziamento() != null){
                try{
                    importo = getJdbcTemplate().queryForObject(
                            query,
                            new Object[] {5, procedimentoRevocaVO.getIdProcedimentoRevoca()},
                            BigDecimal.class
                    );
                }catch (EmptyResultDataAccessException e){
                    importo = null;
                }
                if(importo == null || importo.compareTo(procedimentoRevocaVO.getImportoDaRevocareFinanziamento()) < 0){
                    throw new ErroreGestitoException("Il valore inserito da revocare è maggiore dell’importo concesso");
                }
            }
            //Garanzia
            if(procedimentoRevocaVO.getImportoDaRevocareGaranzia() != null){
                try{
                    importo = getJdbcTemplate().queryForObject(
                            query,
                            new Object[] {10, procedimentoRevocaVO.getIdProcedimentoRevoca()},
                            BigDecimal.class
                    );
                }catch (EmptyResultDataAccessException e){
                    importo = null;
                }
                if(importo == null || importo.compareTo(procedimentoRevocaVO.getImportoDaRevocareGaranzia()) < 0){
                    throw new ErroreGestitoException("Il valore inserito da revocare è maggiore dell’importo concesso");
                }
            }
        }
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        //Note UPDATE
        if(procedimentoRevocaVO.getNote() != null){
            String query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA \r\n"
                + "SET note = ? \r\n"
                + ", ID_UTENTE_AGG = ? \r\n"
                + ", DT_AGGIORNAMENTO = CURRENT_DATE \r\n"
                + "WHERE ID_GESTIONE_REVOCA = ? ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            getJdbcTemplate().update(query, procedimentoRevocaVO.getNote(), userInfoSec.getIdUtente(), procedimentoRevocaVO.getIdProcedimentoRevoca());
        }
        //Data Notifica UPDATE
        if(procedimentoRevocaVO.getDataNotifica() != null) {
            //UPDATE
            String query =
                    "UPDATE PBANDI_T_GESTIONE_REVOCA \r\n"
                    + "SET DT_NOTIFICA = ? \r\n"
                    + ", ID_UTENTE_AGG = ? \r\n"
                    + ", DT_AGGIORNAMENTO = CURRENT_DATE \r\n"
                    + "WHERE ID_GESTIONE_REVOCA = ? ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            getJdbcTemplate().update(query, procedimentoRevocaVO.getDataNotifica(), userInfoSec.getIdUtente(), procedimentoRevocaVO.getIdProcedimentoRevoca());

            //SE DEVO AGGIORNARE RICHIESTA INTEGRAZIONE
            query = "SELECT COUNT(1) \n" +
                    "FROM PBANDI_T_RICHIESTA_INTEGRAZ \n" +
                    "WHERE ID_ENTITA = ? AND ID_TARGET = ? AND DT_FINE_VALIDITA IS NULL ";
            boolean richiestaIntegrazionePresente = getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, procedimentoRevocaVO.getIdProcedimentoRevoca()}, Long.class) != 0;
            if(richiestaIntegrazionePresente){
                //UPDATE RICHIESTA INTEGRAZIONE
                Calendar dataScadenza = Calendar.getInstance();
                dataScadenza.setTime(procedimentoRevocaVO.getDataNotifica());
                dataScadenza.add(Calendar.DATE, procedimentoRevocaVO.getGiorniScadenza().intValue());
                query =
                        "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ  \n" +
                        "SET DT_NOTIFICA = ? \n" +
                        ", DT_SCADENZA = ?\n" +
                        ", ID_UTENTE_AGG = ? \n" +
                        ", DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE ID_ENTITA = ? AND ID_TARGET = ? AND DT_FINE_VALIDITA IS NULL";
                LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
                getJdbcTemplate().update(query, procedimentoRevocaVO.getDataNotifica(), dataScadenza.getTime(), userInfoSec.getIdUtente(), idEntitaGestioneRevoca, procedimentoRevocaVO.getIdProcedimentoRevoca());
            }
        }
        //Importi UPDATE
        if(procedimentoRevocaVO.getImportoDaRevocareContributo() != null) {
            String query =
                    "UPDATE PBANDI_T_GESTIONE_REVOCA \r\n"
                    + "SET IMP_DA_REVOCARE_CONTRIB = ? \r\n"
                    + ", ID_UTENTE_AGG = ? \r\n"
                    + ", DT_AGGIORNAMENTO = CURRENT_DATE \r\n"
                    + "WHERE ID_GESTIONE_REVOCA = ? ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            getJdbcTemplate().update(query, procedimentoRevocaVO.getImportoDaRevocareContributo(), userInfoSec.getIdUtente(), procedimentoRevocaVO.getIdProcedimentoRevoca());
        }
        if(procedimentoRevocaVO.getImportoDaRevocareFinanziamento() != null){
            String query =
                    "UPDATE PBANDI_T_GESTIONE_REVOCA \r\n"
                    + "SET IMP_DA_REVOCARE_FINANZ = ? \r\n"
                    + ", ID_UTENTE_AGG = ? \r\n"
                    + ", DT_AGGIORNAMENTO = CURRENT_DATE \r\n"
                    + "WHERE ID_GESTIONE_REVOCA = ? ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            getJdbcTemplate().update(query, procedimentoRevocaVO.getImportoDaRevocareFinanziamento(), userInfoSec.getIdUtente(), procedimentoRevocaVO.getIdProcedimentoRevoca());
        }
        if(procedimentoRevocaVO.getImportoDaRevocareGaranzia() != null){
            String query =
                    "UPDATE PBANDI_T_GESTIONE_REVOCA \r\n"
                    + "SET IMP_DA_REVOCARE_GARANZIA = ? \r\n"
                    + ", ID_UTENTE_AGG = ? \r\n"
                    + ", DT_AGGIORNAMENTO = CURRENT_DATE \r\n"
                    + "WHERE ID_GESTIONE_REVOCA = ? ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            getJdbcTemplate().update(query, procedimentoRevocaVO.getImportoDaRevocareGaranzia(), userInfoSec.getIdUtente(), procedimentoRevocaVO.getIdProcedimentoRevoca());
        }

        LOG.info(prf + "END");
    }

    @Override
    public void eliminaProcedimentoRevoca(Long numeroRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        //CONTROLLI
        String query = "SELECT id_stato_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.numero_revoca = ?\n" +
                "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                "AND gestioneRevoca.dt_fine_validita IS NULL";
        Long statoRevoca;
        try {
            statoRevoca = getJdbcTemplate().queryForObject(query, new Object[]{numeroRevoca}, Long.class);
        }catch (EmptyResultDataAccessException e) {
            throw new ErroreGestitoException("Nessun procedimento di revoca attivo con il numero revoca passato");
        }
        if(statoRevoca != 5) {
            throw new ErroreGestitoException("Per eliminare un procedimento di revoca questo deve trovarsi in stato 'bozza'");
        }

        //DELETE
        query =     //CANCELLO IL PROCEDIMENTO DI REVOCA IN BOZZA
                "DELETE FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "WHERE gestioneRevoca.id_tipologia_revoca = 2\n" +
                        "AND gestioneRevoca.id_stato_revoca = 5\n" +
                        "AND numero_revoca = ?\n";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, numeroRevoca);

        query =     //CANCELLO LA PROPOSTA DI REVOCA IN STATO CREATA BOZZA PROCEDIMENTO DI REVOCA
                "DELETE FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "WHERE gestioneRevoca.id_tipologia_revoca = 1\n" +
                        "AND gestioneRevoca.id_stato_revoca = 2\n" +
                        "AND numero_revoca = ?\n";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, numeroRevoca);

        query =     //RI ABILITO LA PROPOSTA DI REVOCA IN STATO CREATA
                "UPDATE PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "SET gestioneRevoca.dt_fine_validita = NULL, \n" +
                        "gestioneRevoca.dt_aggiornamento = NULL, \n" +
                        "gestioneRevoca.id_utente_agg = NULL\n" +
                        "WHERE gestioneRevoca.id_tipologia_revoca = 1\n" +
                        "AND gestioneRevoca.id_stato_revoca = 1\n" +
                        "AND numero_revoca = ?\n";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, numeroRevoca);

        LOG.info(prf + "END");
    }

    @Override
    public void verificaImporti(Long idGestioneRevoca) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        //Importi da revocare
        String query = "SELECT gestioneRevoca.imp_da_revocare_contrib AS contrib, \n" +
                "gestioneRevoca.imp_da_revocare_finanz AS finanz,\n" +
                "gestioneRevoca.imp_da_revocare_garanzia AS garanzia\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "WHERE gestioneRevoca.id_gestione_revoca = ?";
        BigDecimal[] importi = getJdbcTemplate().queryForObject(query, new Object[]{idGestioneRevoca}, (rs, rowNum) -> {
            BigDecimal[] imp = new BigDecimal[3];
            imp[0] = rs.getBigDecimal("contrib");
            imp[1] = rs.getBigDecimal("finanz");
            imp[2] = rs.getBigDecimal("garanzia");
            return imp;
        });

        BigDecimal importoContributo;
        BigDecimal importoFinanzimento;
        BigDecimal importoGaranzia;
        query =
                "SELECT QUOTA_IMPORTO_AGEVOLATO \n" +
                        "FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV a\n" +
                        "JOIN PBANDI_T_CONTO_ECONOMICO b ON a.ID_CONTO_ECONOMICO = b.ID_CONTO_ECONOMICO \n" +
                        "JOIN PBANDI_T_PROGETTO c ON b.ID_DOMANDA = c.ID_DOMANDA \n" +
                        "JOIN PBANDI_T_GESTIONE_REVOCA d ON c.ID_PROGETTO = d.ID_PROGETTO\n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.ID_MODALITA_AGEVOLAZIONE = a.ID_MODALITA_AGEVOLAZIONE \n" +
                        "WHERE modAgev.ID_MODALITA_AGEVOLAZIONE_RIF = ? \n" +
                        "AND b.DT_FINE_VALIDITA IS NULL \n" +
                        "AND d.ID_GESTIONE_REVOCA = ? ";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        //Contributo
        try {
            importoContributo = getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{1, idGestioneRevoca},
                    BigDecimal.class
            );
        } catch (EmptyResultDataAccessException e) {
            importoContributo = null;
        }
        //Finanziamento
        try{
            importoFinanzimento = getJdbcTemplate().queryForObject(
                    query,
                    new Object[] {5, idGestioneRevoca},
                    BigDecimal.class
            );
        }catch (EmptyResultDataAccessException e){
            importoFinanzimento = null;
        }
        //Garanzia
        try{
            importoGaranzia = getJdbcTemplate().queryForObject(
                    query,
                    new Object[] {10, idGestioneRevoca},
                    BigDecimal.class
            );
        }catch (EmptyResultDataAccessException e){
            importoGaranzia = null;
        }

        //GestioneErrori
        if(
            (importoContributo == null || importi[0] == null) &&
            (importoFinanzimento == null || importi[1] == null) &&
            (importoGaranzia == null || importi[2] == null)
        ){
            throw new ErroreGestitoException("Importi non inseriti correttamente");
        }
        if(
            (importoContributo != null && importi[0] != null && importoContributo.compareTo(importi[0]) < 0) ||
            (importoFinanzimento != null && importi[1] != null && importoFinanzimento.compareTo(importi[1]) < 0) ||
            (importoGaranzia != null && importi[2] != null && importoGaranzia.compareTo(importi[2]) < 0)
        ){
            throw new ErroreGestitoException("Il valore inserito da revocare è maggiore dell’importo concesso");
        }

        LOG.info(prf + "END");
    }

    @Transactional
    @Override
    public void avviaProcedimentoRevoca(Long numeroProcedimentoRevoca, Long giorniScadenza, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        BigDecimal idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                BigDecimal.class
        );

        String query;
        //CONTROLLO CHE ESISTA IL PROCEDIMENTO DI REVOCA IN STATO BOZZA E CON ATTIVITA NULL O 17 e CAUSALE BLOCCO != 24
        query =
                "SELECT count(1) \n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "WHERE NUMERO_REVOCA = ?\n" +
                        "AND DT_FINE_VALIDITA IS NULL \n" +
                        "AND ID_TIPOLOGIA_REVOCA = 2 \n" +
                        "AND ID_STATO_REVOCA = 5\n" +
                        "AND ID_CAUSALE_BLOCCO <> 24 \n" +
                        "AND (ID_ATTIVITA_REVOCA IS NULL OR ID_ATTIVITA_REVOCA = 17)";
        if(getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, Long.class) == 0){
            throw new ErroreGestitoException("Il procedimento di revoca non può essere avviato!");
        }
        //CONTROLLO CHE LA LETTERA ACCOMPAGNATORIA SIA STATA CARICATA
        query =
                "SELECT COUNT(1)\n" +
                        "FROM PBANDI_T_DOCUMENTO_INDEX doc \n" +
                        "WHERE doc.ID_TIPO_DOCUMENTO_INDEX = 44\n" +
                        "AND doc.ID_ENTITA = ? \n" +
                        "AND doc.ID_TARGET IN (\n" +
                        "\tSELECT gestioneRevoca.ID_GESTIONE_REVOCA \n" +
                        "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "\tWHERE gestioneRevoca.NUMERO_REVOCA = ? AND gestioneRevoca.ID_TIPOLOGIA_REVOCA IN (1, 2)\n" +
                        ")";
        if(getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, numeroProcedimentoRevoca}, Long.class) == 0){
            throw new ErroreGestitoException("Prima di avviare il procedimento di revoca devi allegare la lettera accompagnatoria");
        }

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        //Chiamata all'iter autorizzativo
        query =
                "SELECT id_gestione_revoca, id_progetto\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "WHERE gestioneRevoca.numero_revoca = ?\n" +
                        "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                        "AND gestioneRevoca.dt_fine_validita IS NULL";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        Long[] params = getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, (rs, rowNum) -> {
            Long[] result = new Long[2];
            result[0] = rs.getLong("id_gestione_revoca");
            result[1] = rs.getLong("id_progetto");
            return result;
        });
        String erroreIter = iterAutorizzativiDAO.avviaIterAutorizzativo(9L, idEntitaGestioneRevoca.longValue(), params[0], params[1], userInfoSec.getIdUtente());
        if(!Objects.equals(erroreIter, "")){
            throw new ErroreGestitoException(erroreIter);
        }

        //AVVIO PROCEDIMENTO REVOCA
        query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA \r\n"
                        + "SET GG_RISPOSTA = ? \r\n"
                        + ", ID_ATTIVITA_REVOCA = 1 \r\n"
                        + ", DT_ATTIVITA = CURRENT_DATE \r\n"
                        + ", ID_UTENTE_AGG = ? \r\n"
                        + ", DT_AGGIORNAMENTO = CURRENT_DATE \r\n"
                        + "WHERE NUMERO_REVOCA = ? \n"
                        + "AND id_tipologia_revoca = 2 \n"
                        + "AND dt_fine_validita IS NULL ";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        getJdbcTemplate().update(query, giorniScadenza, userInfoSec.getIdUtente(), numeroProcedimentoRevoca);

        LOG.info(prf + "END");
    }

    @Override
    public void inviaIncaricoAErogazione(Long numeroProcedimentoRevoca, Long numeroDichiarazioneSpesa, BigDecimal importoDaErogareContributo, BigDecimal importoDaErogareFinanziamento, BigDecimal importoIres, Long giorniScadenza, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        String query;
        //CONTROLLO CHE ESISTA IL PROCEDIMENTO DI REVOCA IN STATO BOZZA E CON ATTIVITA NULL O 17 e CAUSALE BLOCCO == 24
        query =
                "SELECT count(1) \n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "WHERE NUMERO_REVOCA = ?\n" +
                "AND ID_STATO_REVOCA = 5\n" +
                "AND ID_CAUSALE_BLOCCO = 24 \n" +
                "AND (ID_ATTIVITA_REVOCA IS NULL OR ID_ATTIVITA_REVOCA = 17)";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        if(getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, Long.class) == 0){
            throw new ErroreGestitoException("Il procedimento di revoca non può essere inviato ad erogazione per avvio procedimento!");
        }

        //CR173
//        //CONTROLLO CHE LA LETTERA ACCOMPAGNATORIA SIA STATA CARICATA
//        query =
//                "SELECT doc.ID_DOCUMENTO_INDEX\n" +
//                "FROM PBANDI_T_DOCUMENTO_INDEX doc \n" +
//                "WHERE doc.ID_TIPO_DOCUMENTO_INDEX = 44\n" +
//                "AND doc.ID_ENTITA = ? \n" +
//                "AND doc.ID_TARGET IN (\n" +
//                "\tSELECT gestioneRevoca.ID_GESTIONE_REVOCA \n" +
//                "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
//                "\tWHERE gestioneRevoca.NUMERO_REVOCA = ? AND gestioneRevoca.ID_TIPOLOGIA_REVOCA IN (1, 2)\n" +
//                ")";
//        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
//        Long idDocumentoIndex;
//        try {
//             idDocumentoIndex = getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, numeroProcedimentoRevoca}, Long.class);
//        }catch (IncorrectResultSizeDataAccessException e){
//            idDocumentoIndex = null;
//        }
//        if(idDocumentoIndex == null){
//            throw new ErroreGestitoException("Prima di avviare il procedimento di revoca devi allegare la lettera accompagnatoria");
//        }

        //INVIO INFORMAZIONE A EROGAZIONE
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        //getImportiDaRevocare
        query = "SELECT gestioneRevoca.imp_da_revocare_contrib AS contrib, \n" +
                "gestioneRevoca.imp_da_revocare_finanz AS finanz,\n" +
                "gestioneRevoca.imp_da_revocare_garanzia AS garanzia\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "WHERE gestioneRevoca.numero_revoca = ? \n" +
                "AND gestioneRevoca.id_tipologia_revoca = 2 \n" +
                "and gestionerevoca.dt_fine_validita IS NULL";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        BigDecimal[] importi = getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, (rs, rowNum) -> {
            BigDecimal[] imp = new BigDecimal[3];
            imp[0] = rs.getBigDecimal("contrib");
            imp[1] = rs.getBigDecimal("finanz");
            imp[2] = rs.getBigDecimal("garanzia");
            return imp;
        });
        //getProgetto
        query = "SELECT gestioneRevoca.id_progetto\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.numero_revoca = ?\n" +
                "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                "AND gestioneRevoca.dt_fine_validita IS NULL ";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        Long idProgetto = getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, Long.class);

        //getProgetto
        query = "SELECT gestioneRevoca.id_gestione_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.numero_revoca = ?\n" +
                "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                "AND gestioneRevoca.dt_fine_validita IS NULL ";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        Long idGestioneRevoca = getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, Long.class);

        //aggiungo la proposta di erogazione
        query =
                "INSERT INTO PBANDI_T_PROPOSTA_EROGAZIONE \n" +
                "(\n" +
                "\tID_GESTIONE_REVOCA, \n" +
                "\tID_PROPOSTA, \n" +
                "\tID_PROGETTO, \n" +
                "\tIMP_LORDO, \n" +
                "\tIMP_IRES, \n" +
                "\tID_MODALITA_AGEVOLAZIONE, \n" +
                "\tID_CAUSALE_EROGAZIONE, \n" +
                "\tDT_VALIDAZIONE_SPESA, \n" +
                "\tDT_INIZIO_VALIDITA,\n" +
                "\tID_UTENTE_INS,\n" +
                "\tDT_INSERIMENTO\n" +
                ")\n" +
                "VALUES \n" +
                "(\n" +
                "\t?, \n" +
                "\t?, \n" +
                "\t?,\n" +
                "\t?,\n" +
//                "(SELECT (MEDIAN(quotaAgev.QUOTA_IMPORTO_AGEVOLATO)/SUM(a.QUOTA_IMPORTO_AGEVOLATO))*?\n" +
//                "FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV a\n" +
//                "JOIN PBANDI_T_CONTO_ECONOMICO b ON b.id_conto_economico = a.id_conto_economico \n" +
//                "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.ID_MODALITA_AGEVOLAZIONE = a.ID_MODALITA_AGEVOLAZIONE \n" +
//                "JOIN PBANDI_T_PROGETTO c ON c.id_domanda = b.id_domanda\n" +
//                "JOIN (SELECT a.ID_CONTO_ECONOMICO, QUOTA_IMPORTO_AGEVOLATO \n" +
//                "FROM PBANDI_R_CONTO_ECONOM_MOD_AGEV a\n" +
//                "JOIN PBANDI_T_CONTO_ECONOMICO b ON a.ID_CONTO_ECONOMICO = b.ID_CONTO_ECONOMICO AND b.DT_FINE_VALIDITA IS NULL\n" +
//                "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.ID_MODALITA_AGEVOLAZIONE = a.ID_MODALITA_AGEVOLAZIONE \n" +
//                "WHERE modAgev.ID_MODALITA_AGEVOLAZIONE_RIF = ?) quotaAgev ON quotaAgev.id_conto_economico = b.id_conto_economico\n" +
//                "WHERE modAgev.ID_MODALITA_AGEVOLAZIONE_RIF IN ('1', '5', '10')\n" +
//                "AND b.dt_fine_validita IS NULL \n" +
//                "AND c.id_progetto = ?)," +
                "\t?,\n" +
                "\t?,\n" +
                "\t?,\n" +
                "\t(SELECT dichiarazioneSpesa.DT_CHIUSURA_VALIDAZIONE \n" +
                "\tFROM PBANDI_T_DICHIARAZIONE_SPESA dichiarazioneSpesa\n" +
                "\tWHERE dichiarazioneSpesa.ID_DICHIARAZIONE_SPESA = ?),\n" +
                "\tCURRENT_DATE,\n" +
                "\t?,\n" +
                "\tCURRENT_DATE\n" +
                ")";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

        String queryUpdateImporto = "UPDATE PBANDI_T_PROPOSTA_EROGAZIONE SET \n" +
                "IMP_DA_EROGARE = IMP_LORDO - IMP_IRES \n" +
                "WHERE ID_PROPOSTA = ?";

        String getIdPropostaErogazione =
                "select SEQ_PBANDI_T_PROP_EROGAZIONE.nextval from dual";

        //GET ID_CAUSALE_EROGAZIONE
        String getIdCausaleErogazioneContributo = "SELECT ptgr.id_causale_erog_contr\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                "WHERE ptgr.ID_GESTIONE_REVOCA = :idGestioneRevoca";
        String getIdCausaleErogazioneFinanziamento = "SELECT ptgr.id_causale_erog_fin\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                "WHERE ptgr.ID_GESTIONE_REVOCA = :idGestioneRevoca";

        //GET ID_MODALITA_AGEV CORRETTO
        String getIdModalitaAgevolazione = "SELECT prbma.ID_MODALITA_AGEVOLAZIONE \n" +
                "FROM PBANDI_R_BANDO_MODALITA_AGEVOL prbma \n" +
                "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = prbma.ID_MODALITA_AGEVOLAZIONE \n" +
                "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.ID_BANDO = prbma.ID_BANDO \n" +
                "JOIN PBANDI_T_DOMANDA ptd ON ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \n" +
                "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_DOMANDA = ptd.ID_DOMANDA \n" +
                "WHERE ptp.ID_PROGETTO = ? AND pdma.ID_MODALITA_AGEVOLAZIONE_RIF = ?";

        //CONTRIBUTO
        if(importoDaErogareContributo != null && importoDaErogareContributo.compareTo(BigDecimal.ZERO) > 0){
            Integer idModalitaAgevolazione = getJdbcTemplate().queryForObject(getIdModalitaAgevolazione, Integer.class, idProgetto, 1);
            Integer idCausaleErogazione = getJdbcTemplate().queryForObject(getIdCausaleErogazioneContributo, Integer.class, idGestioneRevoca);
            BigDecimal idPropostaErogazione = getJdbcTemplate().queryForObject(getIdPropostaErogazione, BigDecimal.class);
            getJdbcTemplate().update(query, idGestioneRevoca, idPropostaErogazione, idProgetto, importoDaErogareContributo, /*1, idProgetto,*/ importoIres, idModalitaAgevolazione, idCausaleErogazione, numeroDichiarazioneSpesa, userInfoSec.getIdUtente());
            controlloPreErogazioneDAO.checkControlliPreErogazione(idPropostaErogazione.longValue(), req);
            getJdbcTemplate().update(queryUpdateImporto, idPropostaErogazione);
            //CR173
//            //aggiungo la lettera accompagnatoria
//            FileDTO file;
//            try{
//                file = documentoManager.leggiFile(idDocumentoIndex);
//            }catch (Exception e){
//                throw new Exception("Lettera accompagnatoria non trovata su file system");
//            }
//            DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
//            documentoIndexVO.setIdTipoDocumentoIndex(BigDecimal.valueOf(42));
//            documentoIndexVO.setIdEntita(idEntitaPropostaErogazione);
//            documentoIndexVO.setIdTarget(idPropostaErogazione);
//            documentoIndexVO.setDtInserimentoIndex(new java.sql.Date((new Date().getTime())));
//            documentoIndexVO.setIdUtenteIns(BigDecimal.valueOf(userInfoSec.getIdUtente()));
//            documentoIndexVO.setNomeFile(file.getNomeFile());
//            documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
//            documentoIndexVO.setRepository(getJdbcTemplate().queryForObject(
//                    "SELECT DESC_BREVE_TIPO_DOC_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE ID_TIPO_DOCUMENTO_INDEX = ?",
//                    new Object[]{documentoIndexVO.getIdTipoDocumentoIndex()},
//                    String.class
//            ));
//            /*Dati Obbligatori*/
//            documentoIndexVO.setUuidNodo("UUID");
//            //salva allegato
//            documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
        }
        //FINANZIAMENTO
        if(importoDaErogareFinanziamento != null && importoDaErogareFinanziamento.compareTo(BigDecimal.ZERO) > 0){
            Integer idModalitaAgevolazione = getJdbcTemplate().queryForObject(getIdModalitaAgevolazione, Integer.class, idProgetto, 5);
            Integer idCausaleErogazione = getJdbcTemplate().queryForObject(getIdCausaleErogazioneFinanziamento, Integer.class, idGestioneRevoca);
            BigDecimal idPropostaErogazione = getJdbcTemplate().queryForObject(getIdPropostaErogazione, BigDecimal.class);
            getJdbcTemplate().update(query, idGestioneRevoca, idPropostaErogazione, idProgetto, importoDaErogareFinanziamento, /*5, idProgetto,*/ 0, idModalitaAgevolazione, idCausaleErogazione, numeroDichiarazioneSpesa, userInfoSec.getIdUtente());
            controlloPreErogazioneDAO.checkControlliPreErogazione(idPropostaErogazione.longValue(), req);
            getJdbcTemplate().update(queryUpdateImporto, idPropostaErogazione);
            //CR173
//            //aggiungo la lettera accompagnatoria
//            FileDTO file;
//            try{
//                file = documentoManager.leggiFile(idDocumentoIndex);
//            }catch (Exception e){
//                throw new Exception("Lettera accompagnatoria non trovata su file system");
//            }
//            DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
//            documentoIndexVO.setIdTipoDocumentoIndex(BigDecimal.valueOf(42));
//            documentoIndexVO.setIdEntita(idEntitaPropostaErogazione);
//            documentoIndexVO.setIdTarget(idPropostaErogazione);
//            documentoIndexVO.setDtInserimentoIndex(new java.sql.Date((new Date().getTime())));
//            documentoIndexVO.setIdUtenteIns(BigDecimal.valueOf(userInfoSec.getIdUtente()));
//            documentoIndexVO.setNomeFile(file.getNomeFile());
//            documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
//            documentoIndexVO.setRepository(getJdbcTemplate().queryForObject(
//                    "SELECT DESC_BREVE_TIPO_DOC_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE ID_TIPO_DOCUMENTO_INDEX = ?",
//                    new Object[]{documentoIndexVO.getIdTipoDocumentoIndex()},
//                    String.class
//            ));
//            /*Dati Obbligatori*/
//            documentoIndexVO.setUuidNodo("UUID");
//            //salva allegato
//            documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
        }

        //Sembra che non serva creare la proposta di erogazione per garanzia
//        //GARANZIA
//        if(importi[2] != null && importi[2].compareTo(BigDecimal.ZERO) != 0){
//            BigDecimal idPropostaErogazione = getJdbcTemplate().queryForObject(getIdPropostaErogazione, BigDecimal.class);
//            getJdbcTemplate().update(query, idGestioneRevoca, idPropostaErogazione, idProgetto, importoDaErogare, /*10, idProgetto,*/ importoIres, 10, numeroDichiarazioneSpesa, userInfoSec.getIdUtente());
//            controlloPreErogazioneDAO.checkControlliPreErogazione(idPropostaErogazione.longValue(), req);
//            getJdbcTemplate().update(queryUpdateImporto, idPropostaErogazione);
//            //CR173
////            //aggiungo la lettera accompagnatoria
////            FileDTO file;
////            try{
////                file = documentoManager.leggiFile(idDocumentoIndex);
////            }catch (Exception e){
////                throw new Exception("Lettera accompagnatoria non trovata su file system");
////            }
////            DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
////            documentoIndexVO.setIdTipoDocumentoIndex(BigDecimal.valueOf(42));
////            documentoIndexVO.setIdEntita(idEntitaPropostaErogazione);
////            documentoIndexVO.setIdTarget(idPropostaErogazione);
////            documentoIndexVO.setDtInserimentoIndex(new java.sql.Date((new Date().getTime())));
////            documentoIndexVO.setIdUtenteIns(BigDecimal.valueOf(userInfoSec.getIdUtente()));
////            documentoIndexVO.setNomeFile(file.getNomeFile());
////            documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
////            documentoIndexVO.setRepository(getJdbcTemplate().queryForObject(
////                    "SELECT DESC_BREVE_TIPO_DOC_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE ID_TIPO_DOCUMENTO_INDEX = ?",
////                    new Object[]{documentoIndexVO.getIdTipoDocumentoIndex()},
////                    String.class
////            ));
////            /*Dati Obbligatori*/
////            documentoIndexVO.setUuidNodo("UUID");
////            //salva allegato
////            documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null);
//        }

        //UPDATE GESTIONE REVOCA

        query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                "SET GG_RISPOSTA = ?,\n" +
                "ID_ATTIVITA_REVOCA = 16,\n" +
                "DT_ATTIVITA = CURRENT_DATE \n" +
                "WHERE NUMERO_REVOCA = ?\n" +
                "AND ID_TIPOLOGIA_REVOCA = 2\n" +
                "AND DT_FINE_VALIDITA IS NULL ";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        getJdbcTemplate().update(query, giorniScadenza, numeroProcedimentoRevoca);

        LOG.info(prf + "END");
    }

    @Override
    public void aggiungiAllegato(Long idGestioneRevoca, byte[] allegato, String nomeAllegato, Boolean letteraAccompagnatoria, Boolean allegatoIntegrazione, Boolean allegatoArchiviazione, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
        FileDTO file = new FileDTO();
        file.setBytes(allegato);
        DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
        documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(allegatoIntegrazione ? 51 : allegatoArchiviazione ? 52 : 50));
        documentoIndexVO.setIdEntita(BigDecimal.valueOf(idEntitaGestioneRevoca));
        documentoIndexVO.setIdTarget(BigDecimal.valueOf(idGestioneRevoca));
        documentoIndexVO.setDtInserimentoIndex(new java.sql.Date((new Date().getTime())));
        documentoIndexVO.setIdUtenteIns(BigDecimal.valueOf(userInfoSec.getIdUtente()));
        documentoIndexVO.setNomeFile(nomeAllegato);
        documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
        documentoIndexVO.setRepository(getJdbcTemplate().queryForObject(
                "SELECT DESC_BREVE_TIPO_DOC_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE ID_TIPO_DOCUMENTO_INDEX = ?",
                new Object[]{documentoIndexVO.getIdTipoDocumentoIndex()},
                String.class
        ));
        /*Dati Obbligatori*/
        documentoIndexVO.setUuidNodo("UUID");

        //se lettera accompagnatoria
        if(letteraAccompagnatoria){
            documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(allegatoIntegrazione ? 45 : allegatoArchiviazione ? 46 : 44));
            documentoIndexVO.setRepository(getJdbcTemplate().queryForObject(
                    "SELECT DESC_BREVE_TIPO_DOC_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE ID_TIPO_DOCUMENTO_INDEX = ?",
                    new Object[]{documentoIndexVO.getIdTipoDocumentoIndex()},
                    String.class
            ));

            String query =
                    allegatoIntegrazione ?
                    "SELECT COUNT(1)\n" +
                    "FROM PBANDI_T_DOCUMENTO_INDEX doc \n" +
                    "WHERE doc.ID_TIPO_DOCUMENTO_INDEX = 45\n" +
                    "AND doc.ID_ENTITA = ? \n" +
                    "AND doc.ID_TARGET = ?"
                    : (
                        allegatoArchiviazione ?
                        "SELECT COUNT(1)\n" +
                        "FROM PBANDI_T_DOCUMENTO_INDEX doc \n" +
                        "WHERE doc.ID_TIPO_DOCUMENTO_INDEX = 46\n" +
                        "AND doc.ID_ENTITA = ? \n" +
                        "AND doc.ID_TARGET IN (\n" +
                        "\tSELECT gestioneRevoca.ID_GESTIONE_REVOCA \n" +
                        "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "\tWHERE gestioneRevoca.NUMERO_REVOCA = (\n" +
                        "\t\tSELECT gestioneRevoca.NUMERO_REVOCA \n" +
                        "\t\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "\t\tWHERE gestioneRevoca.ID_GESTIONE_REVOCA = ?\n" +
                        "\t) AND gestioneRevoca.ID_TIPOLOGIA_REVOCA IN (1, 2)\n" +
                        ")"
                        :
                        "SELECT COUNT(1)\n" +
                        "FROM PBANDI_T_DOCUMENTO_INDEX doc \n" +
                        "WHERE doc.ID_TIPO_DOCUMENTO_INDEX = 44\n" +
                        "AND doc.ID_ENTITA = ? \n" +
                        "AND doc.ID_TARGET IN (\n" +
                        "\tSELECT gestioneRevoca.ID_GESTIONE_REVOCA \n" +
                        "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "\tWHERE gestioneRevoca.NUMERO_REVOCA = (\n" +
                        "\t\tSELECT gestioneRevoca.NUMERO_REVOCA \n" +
                        "\t\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "\t\tWHERE gestioneRevoca.ID_GESTIONE_REVOCA = ?\n" +
                        "\t) AND gestioneRevoca.ID_TIPOLOGIA_REVOCA IN (1, 2)\n" +
                        ")"
                    );

            boolean letteraPresente = getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{idEntitaGestioneRevoca, idGestioneRevoca},
                    Long.class
            ) != 0;

            if(letteraPresente){
                throw new ErroreGestitoException("Lettera accompagnatoria già presente");
            }
        }

        //salva allegato
        if(!documentoManager.salvaFileConVisibilita(file.getBytes(), documentoIndexVO, null)) {
            throw new ErroreGestitoException("Errore durante il salvataggio dell'allegato");
        }

        LOG.info(prf + "END");
    }

    @Transactional
    @Override
    public void archiviaProcedimentoRevoca(Long numeroProcedimentoRevoca, String note, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );

        String query;
        //CONTROLLO CHE ESISTA IL PROCEDIMENTO DI REVOCA IN STATO E CON ATTIVITA (6 6, 6 11, 7 7, 7 17, 7 11, 7 15)
        query =
                "SELECT count(1) \n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "WHERE NUMERO_REVOCA = ?\n" +
                "AND DT_FINE_VALIDITA IS NULL \n" +
                "AND ID_TIPOLOGIA_REVOCA = 2 \n" +
                "AND \n" +
                "(ID_STATO_REVOCA = 6 AND ID_ATTIVITA_REVOCA IN (6, 11))\n" +
                "OR \n" +
                "(ID_STATO_REVOCA = 7 AND ID_ATTIVITA_REVOCA IN (7, 11, 15, 17))";
        if(getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, Long.class) == 0){
            throw new ErroreGestitoException("Il procedimento di revoca non può essere archiviato!");
        }
        //CONTROLLO CHE LA LETTERA ACCOMPAGNATORIA SIA STATA CARICATA
        query =
                "SELECT COUNT(1)\n" +
                "FROM PBANDI_T_DOCUMENTO_INDEX doc \n" +
                "WHERE doc.ID_TIPO_DOCUMENTO_INDEX = 46\n" +
                "AND doc.ID_ENTITA = ? \n" +
                "AND doc.ID_TARGET IN (\n" +
                "\tSELECT gestioneRevoca.ID_GESTIONE_REVOCA \n" +
                "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "\tWHERE gestioneRevoca.NUMERO_REVOCA = ? AND gestioneRevoca.ID_TIPOLOGIA_REVOCA IN (1, 2)\n" +
                ")";
        if(getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, numeroProcedimentoRevoca}, Long.class) == 0){
            throw new ErroreGestitoException("Prima di archiviare il procedimento di revoca devi allegare la lettera accompagnatoria");
        }

        //ARCHIVIA PROCEDIMENTO REVOCA
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
        query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA \r\n"
                + "SET NOTE = ? \r\n"
                + ", ID_ATTIVITA_REVOCA = 3 \r\n"
                + ", DT_ATTIVITA = CURRENT_DATE \r\n"
                + ", ID_UTENTE_AGG = ? \r\n"
                + ", DT_AGGIORNAMENTO = CURRENT_DATE \r\n"
                + "WHERE NUMERO_REVOCA = ? \n"
                + "AND id_tipologia_revoca = 2 \n"
                + "AND dt_fine_validita IS NULL ";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        getJdbcTemplate().update(query, note, userInfoSec.getIdUtente(), numeroProcedimentoRevoca);

        query =
                "SELECT id_gestione_revoca, id_progetto\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.numero_revoca = ?\n" +
                "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                "AND gestioneRevoca.dt_fine_validita IS NULL";
        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
        Long[] params = getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, (rs, rowNum) -> {
            Long[] result = new Long[2];
            result[0] = rs.getLong("id_gestione_revoca");
            result[1] = rs.getLong("id_progetto");
            return result;
        });

        //Iter autorizzativo
        String erroreIter = iterAutorizzativiDAO.avviaIterAutorizzativo(10L, idEntitaGestioneRevoca, params[0], params[1], userInfoSec.getIdUtente());
        if(!Objects.equals(erroreIter, "")){
            throw new ErroreGestitoException(erroreIter);
        }

        LOG.info(prf + "END");
    }


    @Transactional
    @Override
    public void salvaNoteArchiviazione(Long idGestioneRevoca, String note, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try {
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
            String query =
                    "UPDATE PBANDI_T_GESTIONE_REVOCA \r\n"
                            + "SET NOTE = ? \r\n"
                            + ", ID_UTENTE_AGG = ? \r\n"
                            + ", DT_AGGIORNAMENTO = CURRENT_DATE \r\n"
                            + "WHERE ID_GESTIONE_REVOCA = ? ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            getJdbcTemplate().update(query, note, userInfoSec.getIdUtente(), idGestioneRevoca);
        }catch (Exception e) {
            LOG.error("Exception while trying to saveNoteArchiviazone: ", e);
            throw e;
        }

        LOG.info(prf + "END");
    }

    /*PROROGHE*/

    @Override
    public GestioneProrogaVO getRichiestaProroga(Long numeroProcedimentoRevoca) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        GestioneProrogaVO data = null;

        try{
            Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
            );

            String query = "SELECT \n" +
                    "gestioneRevoca.id_stato_revoca\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "WHERE gestioneRevoca.numero_revoca = ?\n" +
                    "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                    "AND gestioneRevoca.id_attivita_revoca = 8\n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL ";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            Long idStatoRevoca = getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, Long.class);

            if(idStatoRevoca == 6){
                Long idEntitaControdeduz = getJdbcTemplate().queryForObject(
                        "SELECT entita.id_entita AS idEntita \n" +
                                "FROM PBANDI_C_ENTITA entita \n" +
                                "WHERE entita.nome_entita = 'PBANDI_T_CONTRODEDUZ'",
                        Long.class
                );
                query = "SELECT \n" +
                        "proroga.id_richiesta_proroga AS idProroga,\n" +
                        "statoProroga.id_stato_proroga AS idStatoProroga,\n" +
                        "statoProroga.desc_stato_proroga AS descStatoProroga,\n" +
                        "proroga.dt_richiesta AS dtRichiestaProroga,\n" +
                        "proroga.dt_esito_richiesta AS dtEsitoRichiestaProroga,\n" +
                        "proroga.num_giorni_rich AS numGiorniRichiestiProroga,\n" +
                        "proroga.num_giorni_approv AS numGiorniApprovatiProroga,\n" +
                        "proroga.motivazione AS motivazioneProroga,\n" +
                        "proroga.dt_inserimento AS dtInserimentoProroga\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "JOIN PBANDI_T_CONTRODEDUZ controdeduz ON controdeduz.id_entita = ? AND controdeduz.id_target = gestioneRevoca.id_gestione_revoca\n" +
                        "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = controdeduz.id_controdeduz AND proroga.id_stato_proroga = 1 \n" +
                        "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                        "WHERE gestioneRevoca.numero_revoca = ?\n" +
                        "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                        "AND gestioneRevoca.id_attivita_revoca = 8 \n" +
                        "AND gestioneRevoca.id_stato_revoca = 6\n" +
                        "AND gestioneRevoca.dt_fine_validita IS NULL";
                try{
                    data = getJdbcTemplate().queryForObject(
                            query,
                            new Object[] {idEntitaGestioneRevoca, idEntitaControdeduz, numeroProcedimentoRevoca},
                            new GestioneProrogaVORowMapper()
                    );
                }catch (EmptyResultDataAccessException ignored){}
            }else if(idStatoRevoca == 7){
                Long idEntitaRichiestaIntegraz = getJdbcTemplate().queryForObject(
                        "SELECT entita.id_entita AS idEntita \n" +
                                "FROM PBANDI_C_ENTITA entita \n" +
                                "WHERE entita.nome_entita = 'PBANDI_T_RICHIESTA_INTEGRAZ'",
                        Long.class
                );
                query = "SELECT \n" +
                        "proroga.id_richiesta_proroga AS idProroga,\n" +
                        "statoProroga.id_stato_proroga AS idStatoProroga,\n" +
                        "statoProroga.desc_stato_proroga AS descStatoProroga,\n" +
                        "proroga.dt_richiesta AS dtRichiestaProroga,\n" +
                        "proroga.dt_esito_richiesta AS dtEsitoRichiestaProroga,\n" +
                        "proroga.num_giorni_rich AS numGiorniRichiestiProroga,\n" +
                        "proroga.num_giorni_approv AS numGiorniApprovatiProroga,\n" +
                        "proroga.motivazione AS motivazioneProroga,\n" +
                        "proroga.dt_inserimento AS dtInserimentoProroga\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "JOIN PBANDI_T_RICHIESTA_INTEGRAZ integrazione ON integrazione.id_entita = ? AND integrazione.id_target = gestioneRevoca.id_gestione_revoca \n" +
                        "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = integrazione.id_richiesta_integraz AND proroga.id_stato_proroga = 1 \n" +
                        "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                        "WHERE gestioneRevoca.numero_revoca = ?\n" +
                        "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                        "AND gestioneRevoca.id_attivita_revoca = 8 \n" +
                        "AND gestioneRevoca.id_stato_revoca = 7\n" +
                        "AND gestioneRevoca.dt_fine_validita IS NULL";
                try{
                    data = getJdbcTemplate().queryForObject(
                            query,
                            new Object[] {idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, numeroProcedimentoRevoca},
                            new GestioneProrogaVORowMapper()
                    );
                }catch (EmptyResultDataAccessException ignored){}
            }
        }catch (EmptyResultDataAccessException e){
            return null;
        }finally {
            LOG.info(prf + "END");
        }

        return data;
    }

    @Override
    public void updateRichiestaProroga(Long numeroProcedimentoRevoca, Boolean esitoRichiestaProroga, Long giorniApprovati, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );
        Long idEntitaControdeduz = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_CONTRODEDUZ'",
                Long.class
        );
        Long idEntitaRichiestaIntegraz = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_RICHIESTA_INTEGRAZ'",
                Long.class
        );

        //Controlli
        String query = "SELECT \n" +
                "gestioneRevoca.id_stato_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.numero_revoca = ?\n" +
                "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                "AND gestioneRevoca.id_attivita_revoca = 8\n" +
                "AND gestioneRevoca.dt_fine_validita IS NULL ";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        try{
            Long idStatoRevoca = getJdbcTemplate().queryForObject(query, new Object[]{numeroProcedimentoRevoca}, Long.class);
            if(idStatoRevoca == 6){
                if(esitoRichiestaProroga && giorniApprovati == null){
                    query =
                            "SELECT proroga.num_giorni_rich\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                            "JOIN PBANDI_T_CONTRODEDUZ controdeduz ON controdeduz.id_entita = ? AND controdeduz.id_target = gestioneRevoca.id_gestione_revoca\n" +
                            "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = controdeduz.id_controdeduz AND proroga.id_stato_proroga = 1 \n" +
                            "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                            "WHERE gestioneRevoca.numero_revoca = ?\n" +
                            "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                            "AND gestioneRevoca.id_attivita_revoca = 8 \n" +
                            "AND gestioneRevoca.id_stato_revoca = 6\n" +
                            "AND gestioneRevoca.dt_fine_validita IS NULL";
                    giorniApprovati = getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, idEntitaControdeduz, numeroProcedimentoRevoca}, Long.class);
                }

                query = "UPDATE PBANDI_T_PROROGA \n" +
                        "SET " + (esitoRichiestaProroga ? "NUM_GIORNI_APPROV = ?,\n" : "") +
                        "\tID_STATO_PROROGA = ?,\n" +
                        "\tID_UTENTE_AGG = ?,\n" +
                        "\tDT_ESITO_RICHIESTA = CURRENT_DATE,\n" +
                        "\tDT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE id_richiesta_proroga = (SELECT proroga.id_richiesta_proroga\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "JOIN PBANDI_T_CONTRODEDUZ controdeduz ON controdeduz.id_entita = ? AND controdeduz.id_target = gestioneRevoca.id_gestione_revoca\n" +
                        "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = controdeduz.id_controdeduz AND proroga.id_stato_proroga = 1 \n" +
                        "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                        "WHERE gestioneRevoca.numero_revoca = ?\n" +
                        "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                        "AND gestioneRevoca.id_attivita_revoca = 8 \n" +
                        "AND gestioneRevoca.id_stato_revoca = 6\n" +
                        "AND gestioneRevoca.dt_fine_validita IS NULL)";

                //UPDATE
                LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
                if(esitoRichiestaProroga)
                    getJdbcTemplate().update(query, giorniApprovati, 2, userInfoSec.getIdUtente(), idEntitaGestioneRevoca, idEntitaControdeduz, numeroProcedimentoRevoca);
                else
                    getJdbcTemplate().update(query,  3, userInfoSec.getIdUtente(), idEntitaGestioneRevoca, idEntitaControdeduz, numeroProcedimentoRevoca);

                //update controdeduz
                query = "UPDATE PBANDI_T_CONTRODEDUZ  \n" +
                        "SET ID_ATTIV_CONTRODEDUZ = ?,\n" +
                        "\tDT_ATTIV_CONTRODEDUZ = CURRENT_DATE,\n" +
                        "\tID_UTENTE_AGG = ?,\n" +
                        "\tDT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE id_controdeduz = (SELECT DISTINCT controdeduz.id_controdeduz\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "JOIN PBANDI_T_CONTRODEDUZ controdeduz ON controdeduz.id_entita = ? AND controdeduz.id_target = gestioneRevoca.id_gestione_revoca\n" +
                        "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = controdeduz.id_controdeduz AND proroga.id_stato_proroga IN (2, 3)\n" +
                        "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                        "WHERE gestioneRevoca.numero_revoca = ?\n" +
                        "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                        "AND gestioneRevoca.id_attivita_revoca = 8\n" +
                        "AND gestioneRevoca.id_stato_revoca = 6\n" +
                        "AND gestioneRevoca.dt_fine_validita IS NULL)";

                //UPDATE
                LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
                getJdbcTemplate().update(query, esitoRichiestaProroga ? 2 : 3, userInfoSec.getIdUtente(),
                        idEntitaGestioneRevoca, idEntitaControdeduz, numeroProcedimentoRevoca);
            }else if(idStatoRevoca == 7){
                if(esitoRichiestaProroga && giorniApprovati == null){
                    query =
                            "SELECT proroga.num_giorni_rich\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                            "JOIN PBANDI_T_RICHIESTA_INTEGRAZ integrazione ON integrazione.id_entita = ? AND integrazione.id_target = gestioneRevoca.id_gestione_revoca \n" +
                            "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = integrazione.id_richiesta_integraz AND proroga.id_stato_proroga = 1 \n" +
                            "WHERE gestioneRevoca.numero_revoca = ?\n" +
                            "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                            "AND gestioneRevoca.id_attivita_revoca = 8 \n" +
                            "AND gestioneRevoca.id_stato_revoca = 7\n" +
                            "AND gestioneRevoca.dt_fine_validita IS NULL";
                    giorniApprovati = getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, numeroProcedimentoRevoca}, Long.class);
                }

                query = "UPDATE PBANDI_T_PROROGA \n" +
                        "SET " + (esitoRichiestaProroga ? "NUM_GIORNI_APPROV = ?,\n" : "") +
                        "\tID_STATO_PROROGA = ?,\n" +
                        "\tID_UTENTE_AGG = ?,\n" +
                        "\tDT_ESITO_RICHIESTA = CURRENT_DATE,\n" +
                        "\tDT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE id_richiesta_proroga = (SELECT proroga.id_richiesta_proroga\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "JOIN PBANDI_T_RICHIESTA_INTEGRAZ integrazione ON integrazione.id_entita = ? AND integrazione.id_target = gestioneRevoca.id_gestione_revoca \n" +
                        "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = integrazione.id_richiesta_integraz AND proroga.id_stato_proroga = 1 \n" +
                        "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                        "WHERE gestioneRevoca.numero_revoca = ?\n" +
                        "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                        "AND gestioneRevoca.id_attivita_revoca = 8 \n" +
                        "AND gestioneRevoca.id_stato_revoca = 7\n" +
                        "AND gestioneRevoca.dt_fine_validita IS NULL)";

                //UPDATE
                LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
                if(esitoRichiestaProroga)
                    getJdbcTemplate().update(query, giorniApprovati, 2, userInfoSec.getIdUtente(), idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, numeroProcedimentoRevoca);
                else
                    getJdbcTemplate().update(query, 3, userInfoSec.getIdUtente(), idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, numeroProcedimentoRevoca);

                //update integraz
                query = "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ \n" +
                        "SET " +
                        (esitoRichiestaProroga
                                ? "DT_SCADENZA = DT_SCADENZA + ?,\n NUM_GIORNI_SCADENZA = NUM_GIORNI_SCADENZA + ?,\n"
                                : "") +
                        "\tID_STATO_RICHIESTA = ?,\n" +
                        "\tID_UTENTE_AGG = ?,\n" +
                        "\tDT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE id_richiesta_integraz = (SELECT integraz.id_richiesta_integraz\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "JOIN PBANDI_T_RICHIESTA_INTEGRAZ integraz ON integraz.id_entita = ? AND integraz.id_target = gestioneRevoca.id_gestione_revoca\n" +
                        "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = integraz.id_richiesta_integraz AND proroga.id_stato_proroga IN (2, 3)\n" +
                        "JOIN PBANDI_D_STATO_PROROGA statoProroga ON statoProroga.id_stato_proroga = proroga.id_stato_proroga\n" +
                        "WHERE gestioneRevoca.numero_revoca = ?\n" +
                        "AND gestioneRevoca.id_tipologia_revoca = 2\n" +
                        "AND gestioneRevoca.id_attivita_revoca = 8\n" +
                        "AND gestioneRevoca.id_stato_revoca = 6\n" +
                        "AND gestioneRevoca.dt_fine_validita IS NULL)";

                //UPDATE
                LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
                if(esitoRichiestaProroga)
                    getJdbcTemplate().update(query, giorniApprovati, giorniApprovati, 1, userInfoSec.getIdUtente(), idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, numeroProcedimentoRevoca);
                else
                    getJdbcTemplate().update(query, 5, userInfoSec.getIdUtente(), idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, numeroProcedimentoRevoca);
            }
        }catch (EmptyResultDataAccessException e){
            throw new ErroreGestitoException("Il procedimento di revoca non dispone di alcuna proroga aggiornabile");
        }

        query = "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                 (esitoRichiestaProroga ?
                         "SET ID_ATTIVITA_REVOCA = 9,\n" +
                         "GG_RISPOSTA = GG_RISPOSTA + ?,\n" +
                         "FLAG_PROROGA = 'S',\n"
                         : "SET ID_ATTIVITA_REVOCA = 10,") +
                "DT_ATTIVITA = CURRENT_DATE,\n" +
                "ID_UTENTE_AGG = ?,\n" +
                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                "WHERE NUMERO_REVOCA = ?\n" +
                "AND ID_TIPOLOGIA_REVOCA = 2\n" +
                "AND ID_ATTIVITA_REVOCA = 8\n" +
                "AND DT_FINE_VALIDITA IS NULL ";

        LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

        Long finalGiorniApprovati = giorniApprovati;
        getJdbcTemplate().update(query, ps -> {
            int k = 1;
            if(esitoRichiestaProroga){
                ps.setLong(k++, finalGiorniApprovati);
            }
            ps.setLong(k++, userInfoSec.getIdUtente());
            ps.setLong(k, numeroProcedimentoRevoca);
        });

        LOG.info(prf + "END");
    }

    /*INTEGRAZIONI*/

    @Override
    public Boolean abilitaRichiediIntegrazione(Long idGestioneRevoca) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        boolean result;

//        devo farlo?
//        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
//                "SELECT entita.id_entita AS idEntita \n" +
//                "FROM PBANDI_C_ENTITA entita \n" +
//                "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
//                Long.class
//        );
//        String query =
//                "SELECT count(1)\n" +
//                "FROM PBANDI_T_RICHIESTA_INTEGRAZ integrazione\n" +
//                "WHERE integrazione.ID_STATO_RICHIESTA IN (1, 4)\n" +
//                "AND integrazione.ID_ENTITA = ? \n" +
//                "AND integrazione.ID_TARGET = ?";
//        LOG.info(prf + "Query: \n\n" + query + "\n");
//        result = getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, idGestioneRevoca}, Long.class) != 0;

        String query =
                "SELECT count(1)\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "WHERE ((gestioneRevoca.ID_STATO_REVOCA = 6 AND \n" +
                "gestioneRevoca.ID_ATTIVITA_REVOCA IN (6, 17, 11)) OR\n" +
                "(gestioneRevoca.ID_STATO_REVOCA = 7 AND \n" +
                "gestioneRevoca.ID_ATTIVITA_REVOCA IN (7, 17, 11, 15))) AND \n" +
                "gestioneRevoca.id_gestione_revoca = ?";
        result = getJdbcTemplate().queryForObject(query, new Object[]{idGestioneRevoca}, Long.class) != 0;

        LOG.info(prf + "END");
        return result;
    }
    @Override
    public Boolean abilitaAvviaIterIntegrazione(Long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        boolean result;
        String query =
                "SELECT count(1)\n" +
                "FROM PBANDI_R_TIPO_STATO_ITER statoIter \n" +
                "JOIN PBANDI_D_INCARICO incarico ON incarico.id_incarico = statoIter.id_incarico\n" +
                "JOIN PBANDI_R_INCARICO_SOGGETTO soggetto ON soggetto.id_incarico = incarico.id_incarico\n" +
                "JOIN PBANDI_T_UTENTE utente ON utente.id_soggetto = soggetto.id_soggetto\n" +
                "WHERE utente.id_utente = ? \n" +
                "AND statoIter.ID_TIPO_ITER = 8\n" +
                "AND statoIter.ordine = 1";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        result = getJdbcTemplate().queryForObject(query, new Object[]{idUtente}, Long.class) != 0;

        LOG.info(prf + "END");
        return result;
    }

    @Transactional
    @Override
    public Long creaRichiestaIntegrazione(Long idGestioneRevoca, Long numGiorniScadenza, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );

        //MODIFICO PROCEDIMENTO DI REVOCA
        String query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA  \n" +
                "SET \n" +
                "ID_ATTIVITA_REVOCA = 2, \n" +
                "DT_ATTIVITA = CURRENT_DATE, \n" +
                "ID_UTENTE_AGG = ?, \n" +
                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                "WHERE ID_GESTIONE_REVOCA = ?";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, userInfoSec.getIdUtente(), idGestioneRevoca);


        //CREO RICHIESTA INTEGRAZIONE
        String getIdRichiestaIntegraz ="SELECT SEQ_PBANDI_T_RICH_INTEGRAZ.nextval FROM dual";
        LOG.info(prf + "\nQuery: \n\n" + getIdRichiestaIntegraz + "\n\n");
        Long newIdRichiestaIntegraz = getJdbcTemplate().queryForObject(getIdRichiestaIntegraz, Long.class);

        query =
                "INSERT INTO PBANDI_T_RICHIESTA_INTEGRAZ \n" +
                        "(\n" +
                        "\tID_RICHIESTA_INTEGRAZ, \n" +
                        "\tID_ENTITA, \n" +
                        "\tID_TARGET,\n" +
                        "\tDT_RICHIESTA,\n" +
                        "\tID_UTENTE_RICHIESTA,\n" +
                        "\tNUM_GIORNI_SCADENZA,\n" +
                        "\tID_STATO_RICHIESTA,\n" +
                        "\tDT_INIZIO_VALIDITA,\n" +
                        "\tID_UTENTE_INS,\n" +
                        "\tDT_INSERIMENTO\n" +
                        ")\n" +
                        "VALUES\n" +
                        "(\n" +
                        "\t?,\n" +
                        "\t?,\n" +
                        "\t?,\n" +
                        "\tCURRENT_DATE,\n" +
                        "\t?,\n" +
                        "\t?,\n" +
                        "\t4,\n" +
                        "\tCURRENT_DATE,\n" +
                        "\t?,\n" +
                        "\tCURRENT_DATE\n" +
                        ")";

        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, newIdRichiestaIntegraz, idEntitaGestioneRevoca, idGestioneRevoca, userInfoSec.getIdUtente(), numGiorniScadenza, userInfoSec.getIdUtente());

        Long idEntitaRichiestaIntegraz = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_RICHIESTA_INTEGRAZ'",
                Long.class
        );

        //AVVIO ITER AUTORIZZATIVO
        String getIdProgetto ="SELECT id_progetto\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.id_gestione_revoca = ?";
        LOG.info(prf + "\nQuery: \n\n" + getIdProgetto + "\n\n");
        Long idProgetto = getJdbcTemplate().queryForObject(getIdProgetto, Long.class, idGestioneRevoca);

        String erroreIter = iterAutorizzativiDAO.avviaIterAutorizzativo(8L, idEntitaRichiestaIntegraz, newIdRichiestaIntegraz, idProgetto, userInfoSec.getIdUtente());
        if(!Objects.equals(erroreIter, "")){
            throw new ErroreGestitoException(erroreIter);
        }

        LOG.info(prf + "END");

        return newIdRichiestaIntegraz;
    }

    @Override
    public Boolean abilitaChiudiIntegrazione(Long idGestioneRevoca) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );
        Long idEntitaRichiestaIntegraz = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_RICHIESTA_INTEGRAZ'",
                Long.class
        );

        boolean result;
        String query = "SELECT COUNT(1)\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "JOIN PBANDI_T_RICHIESTA_INTEGRAZ richiestaIntegrazione ON richiestaIntegrazione.id_entita = ? AND richiestaIntegrazione.id_target = gestioneRevoca.id_gestione_revoca\n" +
                "WHERE gestioneRevoca.id_gestione_revoca = ?\n" +
                "AND richiestaIntegrazione.id_stato_richiesta = 1";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        result = getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, idGestioneRevoca}, Long.class) != 0;

        query = "SELECT COUNT(1)\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "JOIN PBANDI_T_RICHIESTA_INTEGRAZ richiestaIntegrazione ON richiestaIntegrazione.id_entita = ? AND richiestaIntegrazione.id_target = gestioneRevoca.id_gestione_revoca\n" +
                "JOIN PBANDI_T_PROROGA proroga ON proroga.id_entita = ? AND proroga.id_target = richiestaIntegrazione.id_richiesta_integraz AND proroga.id_stato_proroga = 1 \n" +
                "WHERE gestioneRevoca.id_gestione_revoca = ?\n" +
                "AND richiestaIntegrazione.id_stato_richiesta = 1";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        result = result && getJdbcTemplate().queryForObject(query, new Object[]{idEntitaGestioneRevoca, idEntitaRichiestaIntegraz, idGestioneRevoca}, Long.class) == 0;

        LOG.info(prf + "END");
        return result;
    }

    @Override
    public void chiudiRichiestaIntegrazione(Long idGestioneRevoca, HttpServletRequest req) throws Exception {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );

        String query = "UPDATE PBANDI_T_RICHIESTA_INTEGRAZ \n" +
                "SET \n" +
                "ID_STATO_RICHIESTA = 3, \n" +
                "DT_CHIUSURA_UFFICIO = CURRENT_DATE, \n" +
                "ID_UTENTE_AGG = ?, \n" +
                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                "WHERE ID_ENTITA = ? AND ID_TARGET = ?";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        if(0==getJdbcTemplate().update(query, userInfoSec.getIdUtente(), idEntitaGestioneRevoca, idGestioneRevoca)){
            throw new Exception("Errore durante la chiusura della richiesta di integrazione!");
        }

        query = "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                "SET ID_ATTIVITA_REVOCA = 15, " +
                "DT_ATTIVITA = CURRENT_DATE, " +
                "ID_UTENTE_AGG = ?, " +
                "DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                "WHERE ID_GESTIONE_REVOCA = ?";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        if(0==getJdbcTemplate().update(query, userInfoSec.getIdUtente(), idGestioneRevoca)){
            throw new Exception("Errore durante la chiusura della richiesta di integrazione!");
        }

        LOG.info(prf + "END");
    }


}
