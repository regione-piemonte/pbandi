/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.ammvoservrest.dto.ImportiRevoche;
import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.business.service.AmministrativoContabileService;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.ProvvedimentoRevocaDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.DocumentoRevocaVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.ProvvedimentoRevocaMiniVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.ProvvedimentoRevocaVORowMapper;
import it.csi.pbandi.pbgestfinbo.integration.vo.revoche.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroRevocaVO;
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
import java.sql.Types;
import java.util.*;

@Service
public class ProvvedimentoRevocaDAOImpl extends JdbcDaoSupport implements ProvvedimentoRevocaDAO {
    @Autowired
    AmministrativoContabileService amministrativoContabileService;

    @Autowired
    DocumentoManager documentoManager;

    @Autowired
    IterAutorizzativiDAO iterAutorizzativiDAO;


    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    private final Long ID_UTENTE_MIGRAZIONE = -14L;

    @Autowired
    public ProvvedimentoRevocaDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    //RICERCA
    @Override
    public List<ProvvedimentoRevocaMiniVO> getProvvedimentoRevoca(FiltroRevocaVO filtroRevocaVO) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<ProvvedimentoRevocaMiniVO> lista;
        try{

            StringBuilder query = new StringBuilder();
            query.append("SELECT \n" +
                    "gestioneRevoca.id_gestione_revoca AS idProvvedimentoRevoca, \n" +
                    "gestioneRevoca.numero_revoca AS numeroProvvedimentoRevoca, \n" +
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
                    "gestioneRevoca.dt_gestione AS dataProvvedimentoRevoca, \n" +
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
                    "gestioneRevoca.id_tipologia_revoca = 3\n" +
                    "AND \n" +
                    "gestioneRevoca.dt_fine_validita IS NULL \n" +
                    "AND \n" +
                    "soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND \n" +
                    "soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                    "AND \n" +
                    "bandoLineaEnte.id_ente_competenza = 2 \n" +
                    "AND \n" +
                    "bandoLineaEnte.id_ruolo_ente_competenza = 1 "); //SELECT ALL

            ArrayList<Object> args = new ArrayList<>();
            if(filtroRevocaVO.getIdSoggetto() != null){
                args.add(filtroRevocaVO.getIdSoggetto());
                query.append("AND \n" +
                        "soggetto.id_soggetto = ? \n");
            }
            if(filtroRevocaVO.getIdProgetto() != null){
                args.add(filtroRevocaVO.getIdProgetto());
                query.append("AND \n" +
                        "progetto.id_progetto = ? \n");
            }
            if(filtroRevocaVO.getNumeroRevoca() != null){
                args.add(filtroRevocaVO.getNumeroRevoca());
                query.append("AND \n" +
                        "gestioneRevoca.numero_revoca = ? \n");
            }
            if(filtroRevocaVO.getProgrBandoLineaIntervent() != null){
                args.add(filtroRevocaVO.getProgrBandoLineaIntervent());
                query.append("AND \n" +
                        "bandoLinea.progr_bando_linea_intervento = ? \n");
            }
            if(filtroRevocaVO.getIdCausaRevoca() != null){
                args.add(filtroRevocaVO.getIdCausaRevoca());
                query.append("AND \n" +
                        "blocco.id_causale_blocco = ? \n");
            }
            if(filtroRevocaVO.getIdStatoRevoca() != null){
                args.add(filtroRevocaVO.getIdStatoRevoca());
                query.append("AND \n" +
                        "statoRevoca.id_stato_revoca = ? \n");
            }
            if(filtroRevocaVO.getIdAttivitaRevoca() != null){
                args.add(filtroRevocaVO.getIdAttivitaRevoca());
                query.append("AND \n" +
                        "attivita.id_attivita_revoca = ? \n");
            }
            if(filtroRevocaVO.getDataRevocaFrom() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaFrom().getTime()));
                query.append("AND \n" +
                        "gestioneRevoca.dt_gestione >= ? ");
            }
            if(filtroRevocaVO.getDataRevocaTo() != null){
                args.add(new java.sql.Date(filtroRevocaVO.getDataRevocaTo().getTime()));
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
                    new ProvvedimentoRevocaMiniVORowMapper()
            );

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read ProvvedimentoRevocaVO", e);
            throw new ErroreGestitoException("DaoException while trying to read ProvvedimentoRevocaVO", e);
        } finally {
            LOG.info(prf + " END");
        }
        return lista;
    }

    //DETTAGLIO
    @Override
    public ProvvedimentoRevocaVO getDettaglioProvvedimentoRevoca(Long numeroGestioneRevoca, Long idUtente) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + " BEGIN");

        String query =
                "SELECT DISTINCT \n" +
                        "gestioneRevoca.id_gestione_revoca AS idProvvedimentoRevoca,\n" +
                        "gestioneRevoca.numero_revoca AS numeroProvvedimentoRevoca,\n" +
                        "soggetto.id_soggetto AS idSoggetto,  \n" +
                        "soggetto.ndg AS ndg,\n" +
                        "soggetto.codice_fiscale_soggetto AS codiceFiscaleSoggetto,\n" +
                        "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.id_persona_fisica) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.id_ente_giuridico END) END) AS idBeneficiario, \n" +
                        "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazioneBeneficiario,\n" +
                        "domanda.id_domanda AS idDomanda, \n" +
                        "domanda.numero_domanda AS numeroDomanda,\n" +
                        "bandoLinea.id_bando AS idBando,\n" +
                        "bandoLinea.progr_bando_linea_intervento AS progrBandoLineaIntervento, \n" +
                        "bandoLinea.nome_bando_linea AS nomeBandoLinea, \n" +
                        "progetto.id_progetto AS idProgetto, \n" +
                        "progetto.titolo_progetto AS titoloProgetto, \n" +
                        "progetto.codice_visualizzato AS codiceVisualizzatoProgetto,\n" +
                        "gestioneRevoca.num_protocollo AS numeroProtocollo,\n" +
                        "gestioneRevoca.dt_gestione AS dataAvvioProvvedimentoRevoca,\n" +
                        "gestioneRevoca.flag_determina AS flagDetermina,\n" +
                        "gestioneRevoca.dt_determina AS dtDetermina,\n" +
                        "gestioneRevoca.estremi AS estremi,\n" +
                        "gestioneRevoca.flag_ordine_recupero AS flagOrdineRecupero,\n" +
                        "mancatoRecupero.id_mancato_recupero AS idMancatoRecupero,\n" +
                        "mancatoRecupero.desc_mancato_recupero AS descMancatoRecupero,\n" +
                        "motivoRevoca.id_motivo_Revoca AS idMotivoRevoca,\n" +
                        "motivoRevoca.desc_motivo_revoca AS descMotivoRevoca,\n" +
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
                        "gestioneRevoca.flag_contrib_revoca AS flagContribRevoca,\n" +
                        "gestioneRevoca.flag_contrib_minor_spese AS flagContribMinorSpese,\n" +
                        "gestioneRevoca.flag_contrib_decurtaz AS flagContribDecurtaz,\n" +
                        "gestioneRevoca.flag_finanz_revoca AS flagFinanzRevoca,\n" +
                        "gestioneRevoca.flag_finanz_minor_spese AS flagFinanzMinorSpese,\n" +
                        "gestioneRevoca.flag_finanz_decurtaz AS flagFinanzDecurtaz,\n" +
                        "gestioneRevoca.flag_garanzia_revoca AS flagGaranziaRevoca,\n" +
                        "gestioneRevoca.flag_garanzia_minor_spese AS flagGaranziaMinorSpese,\n" +
                        "gestioneRevoca.flag_garanzia_decurtaz AS flagGaranziaDecurtaz,\n" +
                        "importoConcessoContributo.modAgevDesc AS modAgevContrib,\n" +
                        "importoConcessoContributo.idModAgev AS idModAgevContrib,\n" +
                        "importoConcessoContributo.idModAgevRif AS idModAgevContribRif,\n" +
                        "impAmmessoContributo.importo_ammesso_iniziale AS importoAmmessoContributo,\n" +
                        "importoConcessoContributo.importo_concesso_contributo AS importoConcessoContributo,\n" +
                        "importoConcessoFinanziamento.modAgevDesc AS modAgevFinanz,\n" +
                        "importoConcessoFinanziamento.idModAgev AS idModAgevFinanz,\n" +
                        "importoConcessoFinanziamento.idModAgevRif AS idModAgevFinanzRif,\n" +
                        "impAmmessoFinanziamento.importo_ammesso_iniziale AS importoAmmessoFinanziamento,\n" +
                        "importoConcessoFinanziamento.importo_concesso_finanziamento AS importoConcessoFinanziamento,\n" +
                        "importoConcessoGaranzia.modAgevDesc AS modAgevGaranz,\n" +
                        "importoConcessoGaranzia.idModAgev AS idModAgevGaranz,\n" +
                        "importoConcessoGaranzia.idModAgevRif AS idModAgevGaranzRif,\n" +
                        "impAmmessoGaranzia.importo_ammesso_iniziale AS importoAmmessoGaranzia,\n" +
                        "importoConcessoGaranzia.importo_concesso_garanzia AS importoConcessoGaranzia,\n" +
                        "importoRevocatoContributo.importo_revocato_contributo AS importoRevocatoContributo,\n" +
                        "importoRevocatoFinanziamento.importo_revocato_finanziamento AS importoRevocatoFinanziamento,\n" +
                        "importoRevocatoGaranzia.importo_revocato_garanzia AS importoRevocatoGaranzia,\n" +
                        "gestioneRevoca.imp_contrib_revoca_no_recu AS impContribRevocaNoRecu,\n" +
                        "gestioneRevoca.imp_contrib_revoca_recu AS impContribRevocaRecu,\n" +
                        "gestioneRevoca.imp_contrib_interessi AS impContribInteressi,\n" +
                        "gestioneRevoca.imp_finanz_revoca_no_recu AS impFinanzRevocaNoRecu,\n" +
                        "gestioneRevoca.imp_finanz_revoca_recu AS impFinanzRevocaRecu,\n" +
                        "gestioneRevoca.imp_finanz_interessi AS impFinanzInteressi,\n" +
                        "gestioneRevoca.imp_finanz_prerecupero AS impFinanzPreRecu,\n" +
                        "gestioneRevoca.imp_garanzia_revoca_no_recu AS impGaranziaRevocaNoRecu,\n" +
                        "gestioneRevoca.imp_garanzia_revoca_recupero AS impGaranziaRevocaRecu,\n" +
                        "gestioneRevoca.imp_garanzia_interessi AS impGaranziaInteressi,\n" +
                        "gestioneRevoca.imp_garanzia_prerecupero AS impGaranziaPreRecu,\n" +
                        "gestioneRevoca.note AS note, gestioneRevoca.covar,\n" +
                        "istruttore.id_soggetto AS idSoggettoIstruttore,\n" +
                        "istruttore.nome || ' ' || istruttore.cognome AS denominazioneIstruttore\n" +
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
                        "LEFT JOIN PBANDI_D_MANCATO_RECUPERO mancatoRecupero ON mancatoRecupero.id_mancato_recupero = gestioneRevoca.id_mancato_recupero\n" +
                        "LEFT JOIN PBANDI_D_MOTIVO_REVOCA motivoRevoca ON motivoRevoca.id_motivo_revoca = gestioneRevoca.id_motivo_revoca\n" +
                        "LEFT JOIN PBANDI_D_CATEG_ANAGRAFICA autoritaControllante ON autoritaControllante.id_categ_anagrafica = gestioneRevoca.id_categ_anagrafica \n" +
                        "LEFT JOIN PBANDI_D_CAUSALE_BLOCCO blocco ON blocco.id_causale_blocco = gestioneRevoca.id_causale_blocco \n" +
                        "LEFT JOIN PBANDI_D_STATO_REVOCA statoRevoca ON statoRevoca.id_stato_revoca = gestioneRevoca.id_stato_revoca \n" +
                        "LEFT JOIN PBANDI_D_ATTIVITA_REVOCA attivita ON attivita.id_attivita_revoca = gestioneRevoca.id_attivita_revoca \n" +
                        "LEFT JOIN ( /* IMPORTO AMMESSO INIZIALE CONTRIBUTO */\n" +
                        "\tSELECT ptce.ID_DOMANDA, prcema.IMPORTO_AMMESSO_FINPIS AS importo_ammesso_iniziale\n" +
                        "\tFROM PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema \n" +
                        "\tJOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO \n" +
                        "\tJOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma2 ON pdma2.id_modalita_agevolazione_rif = 1\n" +
                        "\tWHERE ptce.DT_FINE_VALIDITA IS NULL AND pdma2.id_modalita_agevolazione = prcema.id_modalita_agevolazione\n" +
                        ") impAmmessoContributo ON impAmmessoContributo.id_domanda = domanda.id_domanda\n" +
                        "LEFT JOIN (SELECT contoEconomicoModAgev.quota_importo_agevolato AS importo_concesso_contributo,\n" +
                        "\tcontoEconomico.id_domanda AS id_domanda,\n" +
                        "\tmodAgev.desc_modalita_agevolazione AS modAgevDesc,\n" +
                        "\tmodAgev.id_modalita_agevolazione AS idModAgev,\n" +
                        "\tmodAgev.id_modalita_agevolazione_rif AS idModAgevRif\n" +
                        "\tFROM PBANDI_T_CONTO_ECONOMICO contoEconomico\n" +
                        "\tJOIN (\n" +
                        "\tSELECT MIN(ptce.DT_INIZIO_VALIDITA) AS DT_INIZIO_VALIDITA, ptce.ID_DOMANDA \n" +
                        "\tFROM PBANDI_T_CONTO_ECONOMICO ptce \n" +
                        "\tGROUP BY ptce.ID_DOMANDA \n" +
                        "\t) ptceOriginale ON ptceOriginale.ID_DOMANDA = contoEconomico.ID_DOMANDA \n" +
                        "\t\tAND ptceOriginale.DT_INIZIO_VALIDITA = contoEconomico.DT_INIZIO_VALIDITA \n" +
                        "\tJOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV contoEconomicoModAgev ON contoEconomicoModAgev.id_conto_economico = contoEconomico.id_conto_economico\n" +
                        "\tJOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.id_modalita_agevolazione = contoEconomicoModAgev.id_modalita_agevolazione\n" +
                        "\tWHERE modAgev.id_modalita_agevolazione_rif = 1) importoConcessoContributo ON importoConcessoContributo.id_domanda = domanda.id_domanda\n" +
                        "LEFT JOIN ( /* IMPORTO AMMESSO INIZIALE FINANZIAMENTO */\n" +
                        "\tSELECT ptce.ID_DOMANDA, prcema.IMPORTO_AMMESSO_FINPIS AS importo_ammesso_iniziale\n" +
                        "\tFROM PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema \n" +
                        "\tJOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO \n" +
                        "\tJOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma2 ON pdma2.id_modalita_agevolazione_rif = 5\n" +
                        "\tWHERE ptce.DT_FINE_VALIDITA IS NULL AND pdma2.id_modalita_agevolazione = prcema.id_modalita_agevolazione\n" +
                        ") impAmmessoFinanziamento ON impAmmessoFinanziamento.id_domanda = domanda.id_domanda\n" +
                        "LEFT JOIN (SELECT contoEconomicoModAgev.quota_importo_agevolato AS importo_concesso_finanziamento,\n" +
                        "\tcontoEconomico.id_domanda AS id_domanda,\n" +
                        "\tmodAgev.desc_modalita_agevolazione AS modAgevDesc,\n" +
                        "\tmodAgev.id_modalita_agevolazione AS idModAgev,\n" +
                        "\tmodAgev.id_modalita_agevolazione_rif AS idModAgevRif\n" +
                        "\tFROM PBANDI_T_CONTO_ECONOMICO contoEconomico\n" +
                        "\tJOIN (\n" +
                        "\tSELECT MIN(ptce.DT_INIZIO_VALIDITA) AS DT_INIZIO_VALIDITA, ptce.ID_DOMANDA \n" +
                        "\tFROM PBANDI_T_CONTO_ECONOMICO ptce \n" +
                        "\tGROUP BY ptce.ID_DOMANDA \n" +
                        "\t) ptceOriginale ON ptceOriginale.ID_DOMANDA = contoEconomico.ID_DOMANDA \n" +
                        "\t\tAND ptceOriginale.DT_INIZIO_VALIDITA = contoEconomico.DT_INIZIO_VALIDITA \n" +
                        "\tJOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV contoEconomicoModAgev ON contoEconomicoModAgev.id_conto_economico = contoEconomico.id_conto_economico\n" +
                        "\tJOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.id_modalita_agevolazione = contoEconomicoModAgev.id_modalita_agevolazione\n" +
                        "\tWHERE modAgev.id_modalita_agevolazione_rif = 5) importoConcessoFinanziamento ON importoConcessoFinanziamento.id_domanda = domanda.id_domanda\n" +
                        "LEFT JOIN ( /* IMPORTO AMMESSO INIZIALE GARANZIA */\n" +
                        "\tSELECT ptce.ID_DOMANDA, prcema.IMPORTO_AMMESSO_FINPIS AS importo_ammesso_iniziale\n" +
                        "\tFROM PBANDI_R_CONTO_ECONOM_MOD_AGEV prcema \n" +
                        "\tJOIN PBANDI_T_CONTO_ECONOMICO ptce ON ptce.ID_CONTO_ECONOMICO = prcema.ID_CONTO_ECONOMICO \n" +
                        "\tJOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma2 ON pdma2.id_modalita_agevolazione_rif = 10\n" +
                        "\tWHERE ptce.DT_FINE_VALIDITA IS NULL AND pdma2.id_modalita_agevolazione = prcema.id_modalita_agevolazione\n" +
                        ") impAmmessoGaranzia ON impAmmessoGaranzia.id_domanda = domanda.id_domanda\n" +
                        "LEFT JOIN (SELECT contoEconomicoModAgev.quota_importo_agevolato AS importo_concesso_garanzia,\n" +
                        "\tcontoEconomico.id_domanda AS id_domanda,\n" +
                        "\tmodAgev.desc_modalita_agevolazione AS modAgevDesc,\n" +
                        "\tmodAgev.id_modalita_agevolazione AS idModAgev,\n" +
                        "\tmodAgev.id_modalita_agevolazione_rif AS idModAgevRif\n" +
                        "\tFROM PBANDI_T_CONTO_ECONOMICO contoEconomico\n" +
                        "\tJOIN (\n" +
                        "\tSELECT MIN(ptce.DT_INIZIO_VALIDITA) AS DT_INIZIO_VALIDITA, ptce.ID_DOMANDA \n" +
                        "\tFROM PBANDI_T_CONTO_ECONOMICO ptce \n" +
                        "\tGROUP BY ptce.ID_DOMANDA \n" +
                        "\t) ptceOriginale ON ptceOriginale.ID_DOMANDA = contoEconomico.ID_DOMANDA \n" +
                        "\t\tAND ptceOriginale.DT_INIZIO_VALIDITA = contoEconomico.DT_INIZIO_VALIDITA \n" +
                        "\tJOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV contoEconomicoModAgev ON contoEconomicoModAgev.id_conto_economico = contoEconomico.id_conto_economico\n" +
                        "\tJOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.id_modalita_agevolazione = contoEconomicoModAgev.id_modalita_agevolazione\n" +
                        "\tWHERE modAgev.id_modalita_agevolazione_rif = 10) importoConcessoGaranzia ON importoConcessoGaranzia.id_domanda = domanda.id_domanda\n" +
                        "LEFT JOIN (SELECT sum(ptr.importo) AS importo_revocato_contributo, \n" +
                        "ptgr.id_gestione_revoca\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                        "JOIN PBANDI_T_REVOCA ptr ON ptr.id_progetto = ptgr.id_progetto\n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.id_modalita_agevolazione = ptr.id_modalita_agevolazione\n" +
                        "WHERE pdma.id_modalita_agevolazione_rif = 1\n" +
                        "AND (ptgr.DT_GESTIONE IS NULL OR ptr.DT_REVOCA + 1 < ptgr.DT_GESTIONE) \n" +
                        "GROUP BY ptgr.ID_GESTIONE_REVOCA) importoRevocatoContributo ON importoRevocatoContributo.id_gestione_revoca = gestioneRevoca.id_gestione_revoca\n" +
                        "LEFT JOIN (SELECT sum(ptr.importo) AS importo_revocato_finanziamento, \n" +
                        "ptgr.id_gestione_revoca\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                        "JOIN PBANDI_T_REVOCA ptr ON ptr.id_progetto = ptgr.id_progetto\n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.id_modalita_agevolazione = ptr.id_modalita_agevolazione\n" +
                        "WHERE pdma.id_modalita_agevolazione_rif = 5\n" +
                        "AND (ptgr.DT_GESTIONE IS NULL OR ptr.DT_REVOCA + 1 < ptgr.DT_GESTIONE) \n" +
                        "GROUP BY ptgr.ID_GESTIONE_REVOCA) importoRevocatoFinanziamento ON importoRevocatoFinanziamento.id_gestione_revoca = gestioneRevoca.id_gestione_revoca\n" +
                        "LEFT JOIN (SELECT sum(ptr.importo) AS importo_revocato_garanzia, \n" +
                        "ptgr.id_gestione_revoca\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                        "JOIN PBANDI_T_REVOCA ptr ON ptr.id_progetto = ptgr.id_progetto\n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.id_modalita_agevolazione = ptr.id_modalita_agevolazione\n" +
                        "WHERE pdma.id_modalita_agevolazione_rif = 10\n" +
                        "AND (ptgr.DT_GESTIONE IS NULL OR ptr.DT_REVOCA + 1 < ptgr.DT_GESTIONE) \n" +
                        "GROUP BY ptgr.ID_GESTIONE_REVOCA) importoRevocatoGaranzia ON importoRevocatoGaranzia.id_gestione_revoca = gestioneRevoca.id_gestione_revoca\t\n" +
                        "JOIN (SELECT gestioneRevoca.numero_revoca AS numero_revoca,\n" +
                        "\tmin(gestioneRevoca.id_gestione_revoca) AS id_gestione_revoca\n" +
                        "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "\tWHERE gestioneRevoca.id_tipologia_revoca = 3\n" +
                        "\tGROUP BY gestioneRevoca.numero_revoca) primaRevoca ON primaRevoca.numero_revoca = gestioneRevoca.numero_revoca\n" +
                        "JOIN PBANDI_T_GESTIONE_REVOCA nuovaRevoca ON nuovaRevoca.id_gestione_revoca = primaRevoca.id_gestione_revoca\n" +
                        "LEFT JOIN PBANDI_T_UTENTE utente ON utente.id_utente = nuovaRevoca.id_utente_ins \n" +
                        "LEFT JOIN (SELECT DISTINCT \n" +
                        "\tsoggetto.id_soggetto,\n" +
                        "\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                        "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                        "\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
                        "\tGROUP BY soggetto.id_soggetto) istruttoreUnivoco ON istruttoreUnivoco.id_soggetto = utente.id_soggetto \n" +
                        "LEFT JOIN PBANDI_T_PERSONA_FISICA istruttore ON istruttore.id_persona_fisica = istruttoreUnivoco.id_persona_fisica \n" +
                        "WHERE gestioneRevoca.numero_revoca = ?\n" +
                        "AND gestioneRevoca.dt_fine_validita IS NULL \n" +
                        "AND gestioneRevoca.id_tipologia_revoca = 3\n" +
                        "AND soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                        "AND soggettoProgetto.id_tipo_beneficiario <> 4 \n" +
                        "AND bandoLineaEnte.id_ente_competenza = 2 \n" +
                        "AND bandoLineaEnte.id_ruolo_ente_competenza = 1";
        LOG.info(prf + "Query: \n\n" + query + "\n");

        ProvvedimentoRevocaVO data;
        try{
            data = getJdbcTemplate().queryForObject(
                    query,
                    new Object[] {numeroGestioneRevoca},
                    new ProvvedimentoRevocaVORowMapper()
            );
        }catch (EmptyResultDataAccessException e){
            data = null;
        }

        if(data != null) {

            query =
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
                            "WHERE ptgr.numero_revoca = ?\n" +
                            "AND ptgr.dt_fine_validita IS NULL \n" +
                            "AND ptgr.id_tipologia_revoca = 3";

            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            List <ParamAmmContabileVO> params = getJdbcTemplate().query(
                    query,
                    ps -> ps.setLong(1, numeroGestioneRevoca),
                    new ParamAmmContabileVORowMapper()
            );
            for(ParamAmmContabileVO param : params){
                try {
                    if(
                            (param.getIdModalitaAgevolazioneRif() == 1 && data.getModAgevContrib() != null) ||
                                    (param.getIdModalitaAgevolazioneRif() == 5 && data.getModAgevFinanz() != null) ||
                                    (param.getIdModalitaAgevolazioneRif() == 10 && data.getModAgevGaranz() != null)
                    ) {
                        if(param.getIdStato() == 9 || param.getIdStato() == 10){
                            query =
                                "SELECT \n" +
                                        "ptgr.dt_stato_revoca\n" +
                                        "FROM PBANDI_T_GESTIONE_REVOCA ptgr\n" +
                                        "JOIN PBANDI_T_PROGETTO ptp ON ptgr.id_progetto = ptp.id_progetto \n" +
                                        "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.id_progetto = ptp.id_progetto\n" +
                                        "LEFT JOIN PBANDI_T_ESTREMI_BANCARI pteb ON pteb.id_estremi_bancari = prsp.id_estremi_bancari\n" +
                                        "JOIN PBANDI_T_DOMANDA ptd ON ptp.id_domanda = ptd.id_domanda\n" +
                                        "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON ptd.progr_bando_linea_intervento = prbli.progr_bando_linea_intervento \n" +
                                        "JOIN PBANDI_R_BANDO_MODALITA_AGEVOL prbma ON prbli.id_bando = prbma.id_bando \n" +
                                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON prbma.id_modalita_agevolazione = pdma.id_modalita_agevolazione\n" +
                                        "WHERE ptgr.numero_revoca = ?\n" +
                                        "AND pdma.id_modalita_agevolazione = ?\n" +
                                        "AND ptgr.id_stato_revoca = 8\n" +
                                        "AND ptgr.id_tipologia_revoca = 3\n" +
                                        "AND prsp.id_tipo_anagrafica = 1\n" +
                                        "AND prsp.id_tipo_beneficiario <> 4";
                            param.setDtStato(getJdbcTemplate().queryForObject(query, new Object[]{numeroGestioneRevoca, param.getIdModalitaAgevolazione()}, Date.class));
                        }
                        ImportiRevoche[] importiRevoche = amministrativoContabileService.callToImportiRevocheImporti(
                                param.getIdProgetto(),
                                param.getIdBando(),
                                (param.getIdStato() == 5 ? new Date() : param.getDtStato()),
                                param.getIdModalitaAgevolazione(),
                                param.getIdModalitaAgevolazioneRif(),
                                data.getIdProvvedimentoRevoca(),
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
                    data.setImpConcAlNettoRevocatoContributo();
                    data.setImpConcAlNettoRevocatoFinanziamento();
                    data.setImpConcAlNettoRevocatoGaranzia();
                    data.setImpErogAlNettoRecuERimbContributo();
                    data.setImpErogAlNettoRecuERimbFinanziamento();
                    data.setImpErogAlNettoRecuERimbGaranzia();
                }
            }
        }

        LOG.info(prf + "END");

        return data;
    }

    @Override
    public List<DocumentoRevocaVO> getDocumentiProvvedimentoRevoca(Long numeroRevoca) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        List<DocumentoRevocaVO> data = new ArrayList<>();

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );
        Long idEntitaContestaz = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_CONTESTAZ'",
                Long.class
        );
        Long idEntitaChecklist = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_CHECKLIST'",
                Long.class
        );

        String query = "SELECT UNIQUE\n" +
                "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                "gestioneRevoca.dt_gestione AS dataDocumento,\n" +
                "documenti.id_documento_index AS idDocumento,\n" +
                "documenti.nome_file AS nomeFile\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_target = gestioneRevoca.id_gestione_revoca\n" +
                "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index = ?\n" +
                "WHERE gestioneRevoca.numero_revoca = ?\n" +
                "AND gestioneRevoca.id_tipologia_revoca = 3\n" +
                "ORDER BY gestioneRevoca.dt_gestione ";

        String queryLastRevoca = "SELECT UNIQUE\n" +
                "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                "lastGestioneRevoca.dt_gestione AS dataDocumento,\n" +
                "documenti.id_documento_index AS idDocumento,\n" +
                "documenti.nome_file AS nomeFile\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                "JOIN PBANDI_T_GESTIONE_REVOCA lastGestioneRevoca ON lastGestioneRevoca.numero_revoca = gestioneRevoca.numero_revoca\n" +
                "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_entita = ? AND documenti.id_target = gestioneRevoca.id_gestione_revoca\n" +
                "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index = ?\n" +
                "WHERE gestioneRevoca.numero_revoca = ?\n" +
                "AND gestioneRevoca.id_tipologia_revoca = 3\n" +
                "AND lastGestioneRevoca.id_tipologia_revoca = 3\n" +
                "AND lastGestioneRevoca.dt_fine_validita IS NULL \n" +
                "ORDER BY lastGestioneRevoca.dt_gestione ";
        //Checklist excel da DS idTipoDocumentoIndex = 33
        //Report validazione idTipoDocumentoIndex = 63
        String queryDocChecklist =
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
                        "AND gestioneRevoca.id_tipologia_revoca in (1, 2, 3) \n" +
                        "ORDER BY documenti.dt_inserimento_index";
        {
            //Lettera accompagnatoria avvio provvedimento di revoca
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(queryLastRevoca, new Object[]{idEntitaGestioneRevoca, 47, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Eventuali altri allegati all’avvio provvedimento di revoca
            try {
                data.addAll(getJdbcTemplate().query(queryLastRevoca, new Object[]{idEntitaGestioneRevoca, 53, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Allegati alla checklist dalla ds
            try {
                data.addAll(getJdbcTemplate().query(queryDocChecklist, new Object[]{idEntitaChecklist, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Lettera accompagnatoria ritiro in autotutela provvedimento di revoca
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, 48, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Eventuali altri allegati al ritiro in autotutela del provvedimento di revoca
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, 54, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Lettera accompagnatoria conferma provvedimento di revoca
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, 49, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Eventuali altri allegati alla conferma del provvedimento di revoca
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, 55, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
            //Allegati alla contestazione
            query =
                    "SELECT UNIQUE\n" +
                            "gestioneRevoca.numero_revoca AS numeroRevoca,\n" +
                            "tipoDocumento.id_tipo_documento_index AS idTipoDocumento, \n" +
                            "gestioneRevoca.dt_gestione AS dataDocumento,\n" +
                            "documenti.id_documento_index AS idDocumento,\n" +
                            "documenti.nome_file AS nomeFile\n" +
                            "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                            "JOIN PBANDI_T_CONTESTAZ contestaz ON contestaz.id_entita = ? AND contestaz.id_target = gestioneRevoca.id_gestione_revoca\n" +
                            "JOIN PBANDI_T_FILE_ENTITA fileEntita ON fileEntita.id_Entita = ? AND fileEntita.id_target = contestaz.id_contestaz\n" +
                            "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file \n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX documenti ON documenti.id_documento_index = files.id_documento_index\n" +
                            "JOIN PBANDI_C_TIPO_DOCUMENTO_INDEX tipoDocumento ON tipoDocumento.id_tipo_documento_index = documenti.id_tipo_documento_index AND documenti.id_tipo_documento_index = 23\n" +
                            "WHERE gestioneRevoca.numero_revoca = ?\n" +
                            "AND gestioneRevoca.id_tipologia_revoca = 3\n" +
                            "AND contestaz.id_stato_contestaz = 2\n" +
                            "ORDER BY gestioneRevoca.dt_gestione ";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            try {
                data.addAll(getJdbcTemplate().query(query, new Object[]{idEntitaGestioneRevoca, idEntitaContestaz, numeroRevoca}, new DocumentoRevocaVORowMapper()));
            } catch (EmptyResultDataAccessException ignored) {}
        }
        LOG.info(prf + "END");
        return data;
    }

    //MODIFICA
    @Override
    public void modificaProvvedimentoRevoca(Long numeroRevoca, ProvvedimentoRevocaVO provvedimentoRevocaVO, HttpServletRequest req) {
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

        Long idEntitaDistinta = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_DISTINTA'",
                Long.class
        );

        String getStatoRevoca ="SELECT gestioneRevoca.id_stato_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.NUMERO_REVOCA = ? \n" +
                "AND gestioneRevoca.DT_FINE_VALIDITA IS NULL \n" +
                "AND gestioneRevoca.ID_TIPOLOGIA_REVOCA = 3";
        LOG.info(prf + "\nQuery: \n\n" + getStatoRevoca + "\n\n");
        Long idStatoRevoca = getJdbcTemplate().queryForObject(getStatoRevoca, new Object[]{numeroRevoca}, Long.class);

        if(provvedimentoRevocaVO.getNote() != null) {
            String query =
                    "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                    "SET NOTE = ?, ID_UTENTE_AGG = ?, DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                    "WHERE numero_revoca = ? \n" +
                    "AND ID_TIPOLOGIA_REVOCA = 3 \n" +
                    "AND DT_FINE_VALIDITA IS NULL";
            LOG.info(prf + " Query: \n\n" + query + "\n");
            getJdbcTemplate().update(query, ps -> {
                ps.setString(1, provvedimentoRevocaVO.getNote());
                ps.setLong(2, userInfoSec.getIdUtente());
                ps.setLong(3, numeroRevoca);
            });
        }

        String query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                        "SET ID_MOTIVO_REVOCA = ?, ID_MANCATO_RECUPERO = ?, ID_UTENTE_AGG = ?, DT_AGGIORNAMENTO = CURRENT_DATE \n" +
                        "WHERE numero_revoca = ? \n" +
                        "AND ID_TIPOLOGIA_REVOCA = 3 \n" +
                        "AND DT_FINE_VALIDITA IS NULL";
        LOG.info(prf + " Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, ps -> {
            if(provvedimentoRevocaVO.getIdMotivoRevoca() != null) {
                ps.setLong(1, provvedimentoRevocaVO.getIdMotivoRevoca());
            }else{
                ps.setNull(1, Types.BIGINT);
            }
            if(provvedimentoRevocaVO.getIdMancatoRecupero() != null) {
                ps.setLong(2, provvedimentoRevocaVO.getIdMancatoRecupero());
            }else{
                ps.setNull(2, Types.BIGINT);
            }
            ps.setLong(3, userInfoSec.getIdUtente());
            ps.setLong(4, numeroRevoca);
        });

        //BOZZA
        if(idStatoRevoca == 5) {
            //Controlli
            /* controllo flag recupero e importi
            if(provvedimentoRevocaVO.getFlagOrdineRecupero()){
                if(provvedimentoRevocaVO.getImpContribRevocaNoRecu() != null ||
                provvedimentoRevocaVO.getImpFinanzRevocaNoRecu() != null ||
                provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu() != null){
                    throw new ErroreGestitoException("Importo revoca senza recupero non deve essere valorizzato o la revoca deve essere Senza recupero");
                }
            }else{
                if(provvedimentoRevocaVO.getImpContribRevocaRecu() != null ||
                provvedimentoRevocaVO.getImpFinanzRevocaRecu() != null ||
                provvedimentoRevocaVO.getImpGaranziaRevocaRecu() != null){
                    throw new ErroreGestitoException("Importo revoca con recupero non deve essere valorizzato o la revoca deve essere Con recupero");
                }
            }
             */
            if (provvedimentoRevocaVO.getFlagOrdineRecupero()) {
                if (provvedimentoRevocaVO.getImpContribRevocaRecu() == null &&
                        provvedimentoRevocaVO.getImpFinanzRevocaRecu() == null &&
                        provvedimentoRevocaVO.getImpGaranziaRevocaRecu() == null) {
                    throw new ErroreGestitoException("In caso di revoca 'con recupero' è neccessario indicare almeno un importo revoca 'con recupero'");
                }
            } else if (provvedimentoRevocaVO.getFlagOrdineRecupero() != null) {
                if (provvedimentoRevocaVO.getImpContribRevocaNoRecu() == null &&
                        provvedimentoRevocaVO.getImpFinanzRevocaNoRecu() == null &&
                        provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu() == null) {
                    throw new ErroreGestitoException("In caso di revoca 'senza recupero' è neccessario indicare almeno un importo revoca 'senza recupero'");
                }
            }

            //Update
            List<Object> args = new ArrayList<>();
            StringJoiner params = new StringJoiner(", \n");
            if(provvedimentoRevocaVO.getFlagDetermina() != null){
                params.add("FLAG_DETERMINA = ?");
                args.add(provvedimentoRevocaVO.getFlagDetermina() ? "S" : null);
            }
            if(provvedimentoRevocaVO.getDtDetermina() != null){
                params.add("DT_DETERMINA = ?");
                args.add(new java.sql.Date(provvedimentoRevocaVO.getDtDetermina().getTime()));
            }
            if (provvedimentoRevocaVO.getEstremi() != null) {
                params.add("estremi = ?");
                args.add(provvedimentoRevocaVO.getEstremi());
            }
            if (provvedimentoRevocaVO.getFlagOrdineRecupero() != null) {
                params.add("flag_ordine_recupero = ?");
                args.add(provvedimentoRevocaVO.getFlagOrdineRecupero() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getIdMancatoRecupero() != null) {
                params.add("id_mancato_recupero = ?");
                args.add(provvedimentoRevocaVO.getIdMancatoRecupero());
            }

            //devo permettere di resettare il motivo per cui accetto anche null
            params.add("id_motivo_revoca = ?");
            args.add(provvedimentoRevocaVO.getIdMotivoRevoca());

            if (provvedimentoRevocaVO.getFlagContribRevoca() != null) {
                params.add("flag_contrib_revoca = ?");
                args.add(provvedimentoRevocaVO.getFlagContribRevoca() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getFlagContribMinorSpese() != null) {
                params.add("flag_contrib_minor_spese = ?");
                args.add(provvedimentoRevocaVO.getFlagContribMinorSpese() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getFlagContribDecurtaz() != null) {
                params.add("flag_contrib_decurtaz = ?");
                args.add(provvedimentoRevocaVO.getFlagContribDecurtaz() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getFlagFinanzRevoca() != null) {
                params.add("flag_finanz_revoca = ?");
                args.add(provvedimentoRevocaVO.getFlagFinanzRevoca() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getFlagFinanzMinorSpese() != null) {
                params.add("flag_finanz_minor_spese = ?");
                args.add(provvedimentoRevocaVO.getFlagFinanzMinorSpese() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getFlagFinanzDecurtaz() != null) {
                params.add("flag_finanz_decurtaz = ?");
                args.add(provvedimentoRevocaVO.getFlagFinanzDecurtaz() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getFlagGaranziaRevoca() != null) {
                params.add("flag_garanzia_revoca = ?");
                args.add(provvedimentoRevocaVO.getFlagGaranziaRevoca() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getFlagGaranziaMinorSpese() != null) {
                params.add("flag_garanzia_minor_spese = ?");
                args.add(provvedimentoRevocaVO.getFlagGaranziaMinorSpese() ? "S" : null);
            }
            if (provvedimentoRevocaVO.getFlagGaranziaDecurtaz() != null) {
                params.add("flag_garanzia_decurtaz = ?");
                args.add(provvedimentoRevocaVO.getFlagGaranziaDecurtaz() ? "S" : null);
            }

            params.add("imp_contrib_revoca_no_recu = ?");
            args.add(provvedimentoRevocaVO.getImpContribRevocaNoRecu());
            params.add("imp_finanz_revoca_no_recu = ?");
            args.add(provvedimentoRevocaVO.getImpFinanzRevocaNoRecu());
            params.add("imp_garanzia_revoca_no_recu = ?");
            args.add(provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu());
            params.add("imp_contrib_revoca_recu = ?");
            args.add(provvedimentoRevocaVO.getImpContribRevocaRecu());
            params.add("imp_finanz_revoca_recu = ?");
            args.add(provvedimentoRevocaVO.getImpFinanzRevocaRecu());
            params.add("imp_garanzia_revoca_recupero = ?");
            args.add(provvedimentoRevocaVO.getImpGaranziaRevocaRecu());
            params.add("imp_finanz_prerecupero = ?");
            args.add(provvedimentoRevocaVO.getImpFinanzPreRecu());
            params.add("imp_garanzia_prerecupero = ?");
            args.add(provvedimentoRevocaVO.getImpGaranzPreRecu());

            if (provvedimentoRevocaVO.getImpContribInteressi() != null) {
                params.add("imp_contrib_interessi = ?");
                args.add(provvedimentoRevocaVO.getImpContribInteressi());
            }
            if (provvedimentoRevocaVO.getImpFinanzInteressi() != null) {
                params.add("imp_finanz_interessi = ?");
                args.add(provvedimentoRevocaVO.getImpFinanzInteressi());
            }
            if (provvedimentoRevocaVO.getImpGaranziaInteressi() != null) {
                params.add("imp_garanzia_interessi = ?");
                args.add(provvedimentoRevocaVO.getImpGaranziaInteressi());
            }
            if(provvedimentoRevocaVO.getNumeroCovar()!=null && provvedimentoRevocaVO.getNumeroCovar().intValue()>0) {
            	 params.add("covar = ?");
            	 args.add(provvedimentoRevocaVO.getNumeroCovar());
            }

            params.add("ID_UTENTE_AGG = ?");
            args.add(userInfoSec.getIdUtente());
            params.add("DT_AGGIORNAMENTO = CURRENT_DATE");

            query = "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                    "SET \n" +
                    params + " \n" +
                    "WHERE numero_revoca = ? \n" +
                    "AND ID_TIPOLOGIA_REVOCA = 3 \n" +
                    "AND DT_FINE_VALIDITA IS NULL";
            LOG.info(prf + " Query: \n\n" + query + "\n");
            getJdbcTemplate().update(query, ps -> {
                int k = 1;
                for (Object object : args) {
                    ps.setObject(k++, object);
                }
                ps.setLong(k, numeroRevoca);
            });
        }else if(idStatoRevoca == 8){//emmesso provv revoca
            LOG.info(prf + " Stato: Emmesso provvedimetno di revoca\n");
            if (provvedimentoRevocaVO.getDataNotifica() != null) {
                LOG.info(prf + " Modifica data notifica\n");

                //controllo data notifica
                String getDtGestione =
                        "SELECT gestioneRevoca.dt_gestione\n" +
                        "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "WHERE gestioneRevoca.NUMERO_REVOCA = ?\n" +
                        "AND gestioneRevoca.DT_FINE_VALIDITA IS NULL \n" +
                        "AND gestioneRevoca.ID_TIPOLOGIA_REVOCA = 3";
                LOG.info(prf + "\nQuery: \n\n" + getDtGestione + "\n\n");
                Date dtGestione = getJdbcTemplate().queryForObject(getDtGestione, new Object[]{numeroRevoca}, Date.class);
                if(dtGestione == null){
                    throw new ErroreGestitoException("La data emissione provvedimento non può essere nulla");
                }
                if(DateUtils.truncatedCompareTo(provvedimentoRevocaVO.getDataNotifica(), dtGestione, Calendar.DATE) < 0){
                    throw new ErroreGestitoException("Data non corretta. La data di notifica non può essere antecedente alla data di emissione del provvedimento");
                }

                //aggiornamento data notifica
                query =
                        "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                        "SET \n" +
                        "dt_notifica = ?, \n" +
                        "dt_aggiornamento = CURRENT_DATE, \n" +
                        "id_utente_agg = ? \n" +
                        "WHERE numero_revoca = ? \n" +
                        "AND id_tipologia_revoca = 3 \n" +
                        "AND dt_fine_validita IS NULL";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                getJdbcTemplate().update(query, ps -> {
                    ps.setDate(1, new java.sql.Date(provvedimentoRevocaVO.getDataNotifica().getTime()));
                    ps.setLong(2, userInfoSec.getIdUtente());
                    ps.setLong(3, numeroRevoca);
                });
            }

            Long idUtenteIns = getJdbcTemplate().queryForObject(
                    "SELECT ptgr.ID_UTENTE_INS \n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                    "WHERE ptgr.ID_GESTIONE_REVOCA = ?",
                    Long.class,
                    provvedimentoRevocaVO.getIdProvvedimentoRevoca()
            );

            //provvedimentoRevocaVO = getDettaglioProvvedimentoRevoca(provvedimentoRevocaVO.getNumeroProvvedimentoRevoca(), userInfoSec.getIdUtente());
            //alg12
            if(provvedimentoRevocaVO.getIdStatoRevoca() == 8 && provvedimentoRevocaVO.getIdAttivitaRevoca() == null && !Objects.equals(idUtenteIns, ID_UTENTE_MIGRAZIONE)){
                LOG.info(prf + " Stato revoca = 8 && idAttivita = null \n");
                //alg9
                if(provvedimentoRevocaVO.getImpContribRevocaRecu() != null || provvedimentoRevocaVO.getImpFinanzRevocaRecu() != null || provvedimentoRevocaVO.getImpGaranziaRevocaRecu() != null){
                    LOG.info(prf + " Importi con recupero \n");
                    String queryAlg10 =
                            "SELECT COUNT(1) \n" +
                            "FROM PBANDI_T_MON_SERV monServAmmvoContab\n" +
                            "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = monServAmmvoContab.id_target\n" +
                            "JOIN PBANDI_T_DISTINTA_DETT dettDistinta ON dettDistinta.id_distinta = distinta.id_distinta \n" +
                            "JOIN PBANDI_T_GESTIONE_REVOCA gestioneRevoca ON gestioneRevoca.id_gestione_revoca = dettDistinta.id_target\n" +
                            "WHERE monServAmmvoContab.id_servizio ='10'\n" +
                            "AND monServAmmvoContab.id_entita = ?\n" +
                            "AND distinta.id_entita = ?\n" +
                            "AND gestioneRevoca.id_tipologia_revoca = 3\n" +
                            "AND gestioneRevoca.dt_fine_validita IS NULL \n" +
                            "AND distinta.dt_fine_validita IS NULL \n" +
                            "AND gestioneRevoca.id_gestione_revoca = ?\n" +
                            "AND distinta.id_modalita_agevolazione = ?\n" +
                            "AND monServAmmvoContab.esito = 'OK'";
                    String getIdDistinta =
                            "select SEQ_PBANDI_T_DISTINTA.nextval from dual";
                    String queryDistinta =
                            "INSERT INTO PBANDI_T_DISTINTA \n" +
                            "(ID_DISTINTA, ID_ENTITA, ID_TIPO_DISTINTA, ID_STATO_DISTINTA, ID_MODALITA_AGEVOLAZIONE, ID_UTENTE_INS, DT_INSERIMENTO, DT_INIZIO_VALIDITA)\n" +
                            "VALUES \n" +
                            "(?, ?, 2, 1, ?, ?, CURRENT_DATE, CURRENT_DATE)";
                    String getIdDistintaDett =
                            "select SEQ_PBANDI_T_DISTINTA_DETT.nextval from dual";
                    String getRigaDistinta =
                            "SELECT COUNT(1) + 1 FROM PBANDI_T_DISTINTA_DETT ptdd WHERE ptdd.ID_DISTINTA = ?";
                    String queryDistintaDett =
                            "INSERT INTO PBANDI_T_DISTINTA_DETT \n" +
                            "(ID_DISTINTA_DETT, RIGA_DISTINTA, ID_DISTINTA, ID_TARGET, ID_UTENTE_INS, DT_INSERIMENTO, DT_INIZIO_VALIDITA)\n" +
                            "VALUES \n" +
                            "(?, ?, ?, ?, ?, CURRENT_DATE, CURRENT_DATE)";
                    String queryAlg14 =
                            "SELECT monServAmmvoContab.esito\n" +
                            "FROM PBANDI_T_MON_SERV monServAmmvoContab\n" +
                            "WHERE monServAmmvoContab.id_servizio = 10\n" +
                            "AND monServAmmvoContab.id_entita = ?\n" +
                            "AND monServAmmvoContab.id_target = ?";
                    String queryAlg15 =
                            "SELECT COUNT(1)\n" +
                            "FROM PBANDI_T_REVOCA revoca \n" +
                            "WHERE revoca.id_gestione_revoca = ?\n" +
                            "AND revoca.ID_MODALITA_AGEVOLAZIONE = ?";
                    String getIdRevoca =
                            "select SEQ_PBANDI_T_REVOCA.nextval from dual";
                    String queryAlg16 =
                            "INSERT INTO PBANDI_T_REVOCA \n" +
                            "(ID_REVOCA, IMPORTO, DT_REVOCA, ID_PROGETTO, ID_UTENTE_INS, ESTREMI, ID_MOTIVO_REVOCA, NOTE, ID_MODALITA_AGEVOLAZIONE, DT_INSERIMENTO, INTERESSI_REVOCA, ID_CAUSALE_DISIMPEGNO, FLAG_ORDINE_RECUPERO, ID_MANCATO_RECUPERO, ID_GESTIONE_REVOCA)\n" +
                            "VALUES\n" +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE, ?, ?, ?, ?, ?)";


                    if(provvedimentoRevocaVO.getImportoConcessoContributo() != null &&
                        provvedimentoRevocaVO.getImpContribRevocaRecu() != null &&
                        !provvedimentoRevocaVO.getImpContribRevocaRecu().equals(BigDecimal.ZERO)
                    ){
                        LOG.info(prf + " Importo concesso per il contributo != null \n");

                        LOG.info(prf + " QUERY: \n" + queryAlg10 + "\n\n");
                        Long esitiOK = getJdbcTemplate().queryForObject(queryAlg10, new Object[]{idEntitaDistinta, idEntitaGestioneRevoca, provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 1}, Long.class);
                        if(esitiOK == 0L){
                            LOG.info(prf + " Non esiste ancora una chiamata ad amministrativo contabile con esito postiivo per questo provvedimento di revoca.\n");

                            //alg11
                            int tipologiaRevocaContributo = (
                                    ((provvedimentoRevocaVO.getImpContribRevocaRecu() != null ? provvedimentoRevocaVO.getImpContribRevocaRecu().longValue() : 0L) +
                                    (provvedimentoRevocaVO.getImpContribRevocaNoRecu() != null ? provvedimentoRevocaVO.getImpContribRevocaNoRecu().longValue() : 0L)) ==
                                    (provvedimentoRevocaVO.getImportoConcessoContributo() != null ? provvedimentoRevocaVO.getImportoConcessoContributo().longValue() : 0L)
                            ) ? 1 : 2;

                            if (tipologiaRevocaContributo == 1) {
                                LOG.info(prf + " Revoca totale\n");
                            } else {
                                LOG.info(prf + " Revoca parziale\n");
                            }

                            //alg13
                            LOG.info(prf + " Query: \n" + getIdDistinta + "\n\n");
                            Long idDistinta = getJdbcTemplate().queryForObject(getIdDistinta, Long.class);
                            getJdbcTemplate().update(queryDistinta, ps -> {
                                ps.setLong(1, idDistinta);
                                ps.setLong(2, idEntitaGestioneRevoca);
                                ps.setLong(3, 1);
                                ps.setLong(4, userInfoSec.getIdUtente());
                            });
                            LOG.info(prf + " idDistinta: " + idDistinta + "\n\n");
                            LOG.info(prf + " Query: \n" + getIdDistintaDett + "\n\n");
                            Long idDistintaDett = getJdbcTemplate().queryForObject(getIdDistintaDett, Long.class);
                            LOG.info(prf + " idDistintaDett: " + idDistintaDett + "\n\n");
                            Long rigaDistinta = getJdbcTemplate().queryForObject(getRigaDistinta, Long.class, idDistinta);
                            LOG.info(prf + " rigaDistinta: " + rigaDistinta + "\n\n");
                            getJdbcTemplate().update(queryDistintaDett, ps -> {
                                ps.setLong(1, idDistintaDett);
                                ps.setLong(2, rigaDistinta);
                                ps.setLong(3, idDistinta);
                                ps.setLong(4, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                                ps.setLong(5, userInfoSec.getIdUtente());
                            });
                            LOG.info(prf + " Chiamata ad amm.cont DistintaErogazione:Revoca \n\n");
                            amministrativoContabileService.callToDistintaErogazioneRevoca(
                                    idDistinta.intValue(), //distinta
                                    rigaDistinta.intValue(), //riga distinta
                                    provvedimentoRevocaVO.getDataNotifica(), //dataErogazione
                                    (provvedimentoRevocaVO.getImpContribInteressi() != null ? provvedimentoRevocaVO.getImpContribInteressi().doubleValue() : 0), //oneri
                                    (provvedimentoRevocaVO.getImpContribRevocaRecu() != null ? provvedimentoRevocaVO.getImpContribRevocaRecu().doubleValue() : 0), //importiRevocaCapitale
                                    provvedimentoRevocaVO.getNumeroProvvedimentoRevoca().intValue(), //numeroRevoca
                                    tipologiaRevocaContributo, //tipoRevoca
                                    provvedimentoRevocaVO.getIdProgetto().intValue(),
                                    provvedimentoRevocaVO.getIdModAgevContrib().intValue(),
                                    provvedimentoRevocaVO.getIdModAgevContribRif().intValue(), //idAgevolazioneRif
                                    userInfoSec.getIdUtente()
                            );
                            //alg14
                            LOG.info(prf + " Query: " + queryAlg14 + "\n\n");
                            String esito = getJdbcTemplate().queryForObject(queryAlg14, new Object[]{idEntitaDistinta, idDistinta}, String.class);
                            if(esito == null || esito.equals("KO")){
                                throw new ErroreGestitoException("Errore nella trasmissione ad Amministrativo contabile per la modalità di agevolazione contributo");
                            }
                            //alg15
                            LOG.info(prf + " Query: " + queryAlg15 + "\n\n");
                            if(getJdbcTemplate().queryForObject(queryAlg15, new Object[]{provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 1}, Long.class) == 0){
                                LOG.info(prf + " Query: " + getIdRevoca + "\n\n");
                                Long idRevoca = getJdbcTemplate().queryForObject(getIdRevoca, Long.class);
                                //alg16
                                LOG.info(prf + " Query: " + queryAlg16 + "\n\n");
                                getJdbcTemplate().update(queryAlg16, ps -> {
                                    ps.setLong(1, idRevoca);
                                    BigDecimal importo = BigDecimal.ZERO;
                                    if(provvedimentoRevocaVO.getImpContribRevocaRecu() != null){
                                        importo = importo.add(provvedimentoRevocaVO.getImpContribRevocaRecu());
                                    }
                                    if(provvedimentoRevocaVO.getImpContribRevocaNoRecu() != null){
                                        importo = importo.add(provvedimentoRevocaVO.getImpContribRevocaNoRecu());
                                    }
                                    ps.setBigDecimal(2, importo);
                                    ps.setDate(3, new java.sql.Date(provvedimentoRevocaVO.getDataNotifica().getTime()));
                                    ps.setLong(4, provvedimentoRevocaVO.getIdProgetto());
                                    ps.setLong(5, userInfoSec.getIdUtente());
                                    ps.setString(6, provvedimentoRevocaVO.getEstremi());
                                    ps.setObject(7, provvedimentoRevocaVO.getIdMotivoRevoca());
                                    ps.setString(8, provvedimentoRevocaVO.getNote());
                                    ps.setLong(9, provvedimentoRevocaVO.getIdModAgevContrib()); //id_modalita_agevolazione
                                    ps.setBigDecimal(10, provvedimentoRevocaVO.getImpContribInteressi());
                                    if(provvedimentoRevocaVO.getFlagContribRevoca()){
                                        if(provvedimentoRevocaVO.getFlagContribDecurtaz()){
                                            ps.setLong(11, 5);
                                        }else if(provvedimentoRevocaVO.getFlagContribMinorSpese()){
                                            ps.setLong(11, 4);
                                        }else{
                                            ps.setLong(11, 1);
                                        }
                                    }else{
                                        if(provvedimentoRevocaVO.getFlagContribDecurtaz()){
                                            ps.setLong(11, 3);
                                        }else if(provvedimentoRevocaVO.getFlagContribMinorSpese()){
                                            ps.setLong(11, 2);
                                        }else{
                                            ps.setLong(11, 1);
                                        }
                                    }
                                    ps.setString(12, provvedimentoRevocaVO.getFlagOrdineRecupero() ? "S" : "N");
                                    ps.setObject(13, provvedimentoRevocaVO.getIdMancatoRecupero());
                                    ps.setLong(14, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                                });
                            }
                        }
                    }
                    if(provvedimentoRevocaVO.getImportoConcessoFinanziamento() != null &&
                        provvedimentoRevocaVO.getImpFinanzRevocaRecu() != null &&
                        !provvedimentoRevocaVO.getImpFinanzRevocaRecu().equals(BigDecimal.ZERO)
                    ){
                        LOG.info(prf + " Importo concesso per il finanziamento != null \n");

                        LOG.info(prf + " QUERY: \n" + queryAlg10 + "\n\n");
                        Long esitiOK = getJdbcTemplate().queryForObject(queryAlg10, new Object[]{idEntitaDistinta, idEntitaGestioneRevoca, provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 5}, Long.class);
                        if(esitiOK == 0L){
                            LOG.info(prf + " Non esiste ancora una chiamata ad amministrativo contabile con esito postiivo per questo provvedimento di revoca.\n");

                            //alg11
                            int tipologiaRevocaFinanziamento = (
                                    ((provvedimentoRevocaVO.getImpFinanzPreRecu() != null ? provvedimentoRevocaVO.getImpFinanzPreRecu().longValue() : 0L) +
                                    (provvedimentoRevocaVO.getImpFinanzRevocaRecu() != null ? provvedimentoRevocaVO.getImpFinanzRevocaRecu().longValue() : 0L) +
                                    (provvedimentoRevocaVO.getImpFinanzRevocaNoRecu() != null ? provvedimentoRevocaVO.getImpFinanzRevocaNoRecu().longValue() : 0L)) ==
                                    (provvedimentoRevocaVO.getImportoConcessoFinanziamento() != null ? provvedimentoRevocaVO.getImportoConcessoFinanziamento().longValue() : 0L)
                            ) ? 1 : 2;

                            if (tipologiaRevocaFinanziamento == 1) {
                                LOG.info(prf + " Revoca totale\n");
                            } else {
                                LOG.info(prf + " Revoca parziale\n");
                            }

                            //alg13
                            LOG.info(prf + " Query: \n" + getIdDistinta + "\n\n");
                            Long idDistinta = getJdbcTemplate().queryForObject(getIdDistinta, Long.class);
                            getJdbcTemplate().update(queryDistinta, ps -> {
                                ps.setLong(1, idDistinta);
                                ps.setLong(2, idEntitaGestioneRevoca);
                                ps.setLong(3, 5);
                                ps.setLong(4, userInfoSec.getIdUtente());
                            });
                            LOG.info(prf + " idDistinta: " + idDistinta + "\n\n");
                            LOG.info(prf + " Query: \n" + getIdDistintaDett + "\n\n");
                            Long idDistintaDett = getJdbcTemplate().queryForObject(getIdDistintaDett, Long.class);
                            LOG.info(prf + " idDistintaDett: " + idDistintaDett + "\n\n");
                            Long rigaDistinta = getJdbcTemplate().queryForObject(getRigaDistinta, Long.class, idDistinta);
                            LOG.info(prf + " rigaDistinta: " + rigaDistinta + "\n\n");
                            getJdbcTemplate().update(queryDistintaDett, ps -> {
                                ps.setLong(1, idDistintaDett);
                                ps.setLong(2, rigaDistinta);
                                ps.setLong(3, idDistinta);
                                ps.setLong(4, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                                ps.setLong(5, userInfoSec.getIdUtente());
                            });
                            LOG.info(prf + " Chiamata ad amm.cont DistintaErogazione:Revoca \n\n");
                            amministrativoContabileService.callToDistintaErogazioneRevoca(
                                    idDistinta.intValue(), //distinta
                                    rigaDistinta.intValue(), //riga distinta
                                    provvedimentoRevocaVO.getDataNotifica(), //dataErogazione
                                    provvedimentoRevocaVO.getImpFinanzInteressi().doubleValue(), //oneri
                                    provvedimentoRevocaVO.getImpFinanzRevocaRecu().doubleValue(), //importiRevocaCapitale
                                    provvedimentoRevocaVO.getNumeroProvvedimentoRevoca().intValue(), //numeroRevoca
                                    tipologiaRevocaFinanziamento, //tipoRevoca
                                    provvedimentoRevocaVO.getIdProgetto().intValue(),
                                    provvedimentoRevocaVO.getIdModAgevFinanz().intValue(),
                                    provvedimentoRevocaVO.getIdModAgevFinanzRif().intValue(),  //idAgevolazioneRif
                                    userInfoSec.getIdUtente()
                            );
                            //alg14
                            LOG.info(prf + " Query: " + queryAlg14 + "\n\n");
                            String esito = getJdbcTemplate().queryForObject(queryAlg14, new Object[]{idEntitaDistinta, idDistinta}, String.class);
                            if(esito == null || esito.equals("KO")){
                                throw new ErroreGestitoException("Errore nella trasmissione ad Amministrativo contabile per la modalità di agevolazione finanziamento");
                            }
                            //alg15
                            LOG.info(prf + " Query: " + queryAlg15 + "\n\n");
                            if(getJdbcTemplate().queryForObject(queryAlg15, new Object[]{provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 5}, Long.class) == 0){
                                LOG.info(prf + " Query: " + getIdRevoca + "\n\n");
                                Long idRevoca = getJdbcTemplate().queryForObject(getIdRevoca, Long.class);
                                //alg16
                                LOG.info(prf + " Query: " + queryAlg16 + "\n\n");
                                getJdbcTemplate().update(queryAlg16, ps -> {
                                    ps.setLong(1, idRevoca);
                                    BigDecimal importo = BigDecimal.ZERO;
                                    if(provvedimentoRevocaVO.getImpFinanzRevocaRecu() != null){
                                        importo = importo.add(provvedimentoRevocaVO.getImpFinanzRevocaRecu());
                                    }
                                    if(provvedimentoRevocaVO.getImpFinanzRevocaNoRecu() != null){
                                        importo = importo.add(provvedimentoRevocaVO.getImpFinanzRevocaNoRecu());
                                    }
                                    if(provvedimentoRevocaVO.getImpFinanzPreRecu() != null){
                                        importo = importo.add(provvedimentoRevocaVO.getImpFinanzPreRecu());
                                    }
                                    ps.setBigDecimal(2, importo);
                                    ps.setDate(3, new java.sql.Date(provvedimentoRevocaVO.getDataNotifica().getTime()));
                                    ps.setLong(4, provvedimentoRevocaVO.getIdProgetto());
                                    ps.setLong(5, userInfoSec.getIdUtente());
                                    ps.setString(6, provvedimentoRevocaVO.getEstremi());
                                    ps.setObject(7, provvedimentoRevocaVO.getIdMotivoRevoca());
                                    ps.setString(8, provvedimentoRevocaVO.getNote());
                                    ps.setLong(9, provvedimentoRevocaVO.getIdModAgevFinanz()); //id_modalita_agevolazione
                                    ps.setBigDecimal(10, provvedimentoRevocaVO.getImpFinanzInteressi());
                                    if(provvedimentoRevocaVO.getFlagFinanzRevoca()){
                                        if(provvedimentoRevocaVO.getFlagFinanzDecurtaz()){
                                            ps.setLong(11, 5);
                                        }else if(provvedimentoRevocaVO.getFlagFinanzMinorSpese()){
                                            ps.setLong(11, 4);
                                        }else{
                                            ps.setLong(11, 1);
                                        }
                                    }else{
                                        if(provvedimentoRevocaVO.getFlagFinanzDecurtaz()){
                                            ps.setLong(11, 3);
                                        }else if(provvedimentoRevocaVO.getFlagFinanzMinorSpese()){
                                            ps.setLong(11, 2);
                                        }else{
                                            ps.setLong(11, 1);
                                        }
                                    }
                                    ps.setString(12, provvedimentoRevocaVO.getFlagOrdineRecupero() ? "S" : "N");
                                    ps.setObject(13, provvedimentoRevocaVO.getIdMancatoRecupero());
                                    ps.setLong(14, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                                });
                            }
                        }
                    }
                    if(provvedimentoRevocaVO.getImportoConcessoGaranzia() != null &&
                        provvedimentoRevocaVO.getImpGaranziaRevocaRecu() != null &&
                        !provvedimentoRevocaVO.getImpGaranziaRevocaRecu().equals(BigDecimal.ZERO)
                    ){
                        LOG.info(prf + " Importo concesso per il garanzia != null \n");

                        LOG.info(prf + " QUERY: \n" + queryAlg10 + "\n\n");
                        Long esitiOK = getJdbcTemplate().queryForObject(queryAlg10, new Object[]{idEntitaDistinta, idEntitaGestioneRevoca, provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 10}, Long.class);
                        if(esitiOK == 0L){
                            LOG.info(prf + " Non esiste ancora una chiamata ad amministrativo contabile con esito postiivo per questo provvedimento di revoca.\n");

                            //alg11
                            int tipologiaRevocaGaranzia = (
                                    ((provvedimentoRevocaVO.getImpGaranzPreRecu() != null ? provvedimentoRevocaVO.getImpGaranzPreRecu().longValue() : 0L) +
                                    (provvedimentoRevocaVO.getImpGaranziaRevocaRecu() != null ? provvedimentoRevocaVO.getImpGaranziaRevocaRecu().longValue() : 0L) +
                                    (provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu() != null ? provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu().longValue() : 0L)) ==
                                    (provvedimentoRevocaVO.getImportoConcessoGaranzia() != null ? provvedimentoRevocaVO.getImportoConcessoGaranzia().longValue() : 0L)
                            ) ? 1 : 2;

                            if (tipologiaRevocaGaranzia == 1) {
                                LOG.info(prf + " Revoca totale\n");
                            } else {
                                LOG.info(prf + " Revoca parziale\n");
                            }

                            //alg13
                            LOG.info(prf + " Query: \n" + getIdDistinta + "\n\n");
                            Long idDistinta = getJdbcTemplate().queryForObject(getIdDistinta, Long.class);
                            getJdbcTemplate().update(queryDistinta, ps -> {
                                ps.setLong(1, idDistinta);
                                ps.setLong(2, idEntitaGestioneRevoca);
                                ps.setLong(3, 10);
                                ps.setLong(4, userInfoSec.getIdUtente());
                            });
                            LOG.info(prf + " idDistinta: " + idDistinta + "\n\n");
                            LOG.info(prf + " Query: \n" + getIdDistintaDett + "\n\n");
                            Long idDistintaDett = getJdbcTemplate().queryForObject(getIdDistintaDett, Long.class);
                            LOG.info(prf + " idDistintaDett: " + idDistintaDett + "\n\n");
                            Long rigaDistinta = getJdbcTemplate().queryForObject(getRigaDistinta, Long.class, idDistinta);
                            LOG.info(prf + " rigaDistinta: " + rigaDistinta + "\n\n");
                            getJdbcTemplate().update(queryDistintaDett, ps -> {
                                ps.setLong(1, idDistintaDett);
                                ps.setLong(2, rigaDistinta);
                                ps.setLong(3, idDistinta);
                                ps.setLong(4, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                                ps.setLong(5, userInfoSec.getIdUtente());
                            });
                            LOG.info(prf + " Chiamata ad amm.cont DistintaErogazione:Revoca \n\n");
                            amministrativoContabileService.callToDistintaErogazioneRevoca(
                                    idDistinta.intValue(), //distinta
                                    rigaDistinta.intValue(), //riga distinta
                                    provvedimentoRevocaVO.getDataNotifica(), //dataErogazione
                                    provvedimentoRevocaVO.getImpGaranziaInteressi().doubleValue(), //oneri
                                    provvedimentoRevocaVO.getImpGaranziaRevocaRecu().doubleValue(), //importiRevocaCapitale
                                    provvedimentoRevocaVO.getNumeroProvvedimentoRevoca().intValue(), //numeroRevoca
                                    tipologiaRevocaGaranzia, //tipoRevoca
                                    provvedimentoRevocaVO.getIdProgetto().intValue(),
                                    provvedimentoRevocaVO.getIdModAgevGaranz().intValue(),
                                    provvedimentoRevocaVO.getIdModAgevGaranzRif().intValue(),  //idAgevolazioneRif
                                    userInfoSec.getIdUtente()
                            );
                            //alg14
                            LOG.info(prf + " Query: " + queryAlg14 + "\n\n");
                            String esito = getJdbcTemplate().queryForObject(queryAlg14, new Object[]{idEntitaDistinta, idDistinta}, String.class);
                            if(esito == null || esito.equals("KO")){
                                throw new ErroreGestitoException("Errore nella trasmissione ad Amministrativo contabile per la modalità di agevolazione garanzia");
                            }
                            //alg15
                            LOG.info(prf + " Query: " + queryAlg15 + "\n\n");
                            if(getJdbcTemplate().queryForObject(queryAlg15, new Object[]{provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 10}, Long.class) == 0){
                                LOG.info(prf + " Query: " + getIdRevoca + "\n\n");
                                Long idRevoca = getJdbcTemplate().queryForObject(getIdRevoca, Long.class);
                                //alg16
                                LOG.info(prf + " Query: " + queryAlg16 + "\n\n");
                                getJdbcTemplate().update(queryAlg16, ps -> {
                                    ps.setLong(1, idRevoca);
                                    BigDecimal importo = BigDecimal.ZERO;
                                    if(provvedimentoRevocaVO.getImpGaranziaRevocaRecu() != null){
                                        importo = importo.add(provvedimentoRevocaVO.getImpGaranziaRevocaRecu());
                                    }
                                    if(provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu() != null){
                                        importo = importo.add(provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu());
                                    }
                                    if(provvedimentoRevocaVO.getImpGaranzPreRecu() != null){
                                        importo = importo.add(provvedimentoRevocaVO.getImpGaranzPreRecu());
                                    }
                                    ps.setBigDecimal(2, importo);
                                    ps.setDate(3, new java.sql.Date(provvedimentoRevocaVO.getDataNotifica().getTime()));
                                    ps.setLong(4, provvedimentoRevocaVO.getIdProgetto());
                                    ps.setLong(5, userInfoSec.getIdUtente());
                                    ps.setString(6, provvedimentoRevocaVO.getEstremi());
                                    ps.setObject(7, provvedimentoRevocaVO.getIdMotivoRevoca());
                                    ps.setString(8, provvedimentoRevocaVO.getNote());
                                    ps.setLong(9, provvedimentoRevocaVO.getIdModAgevGaranz()); //id_modalita_agevolazione
                                    ps.setBigDecimal(10, provvedimentoRevocaVO.getImpGaranziaInteressi());
                                    if(provvedimentoRevocaVO.getFlagGaranziaRevoca()){
                                        if(provvedimentoRevocaVO.getFlagGaranziaDecurtaz()){
                                            ps.setLong(11, 5);
                                        }else if(provvedimentoRevocaVO.getFlagGaranziaMinorSpese()){
                                            ps.setLong(11, 4);
                                        }else{
                                            ps.setLong(11, 1);
                                        }
                                    }else{
                                        if(provvedimentoRevocaVO.getFlagGaranziaDecurtaz()){
                                            ps.setLong(11, 3);
                                        }else if(provvedimentoRevocaVO.getFlagGaranziaMinorSpese()){
                                            ps.setLong(11, 2);
                                        }else{
                                            ps.setLong(11, 1);
                                        }
                                    }
                                    ps.setString(12, provvedimentoRevocaVO.getFlagOrdineRecupero() ? "S" : "N");
                                    ps.setObject(13, provvedimentoRevocaVO.getIdMancatoRecupero());
                                    ps.setLong(14, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                                });
                            }
                        }
                    }
                }else{
                    LOG.info(prf + " Importi senza recupero \n");
                    String queryAlg15 =
                            "SELECT COUNT(1)\n" +
                            "FROM PBANDI_T_REVOCA revoca \n" +
                            "WHERE revoca.id_gestione_revoca = ?\n" +
                            "AND revoca.id_modalita_agevolazione = ?";
                    String getIdRevoca =
                            "select SEQ_PBANDI_T_REVOCA.nextval from dual";
                    String queryAlg16 =
                            "INSERT INTO PBANDI_T_REVOCA \n" +
                            "(ID_REVOCA, IMPORTO, DT_REVOCA, ID_PROGETTO, ID_UTENTE_INS, ESTREMI, ID_MOTIVO_REVOCA, NOTE, ID_MODALITA_AGEVOLAZIONE, DT_INSERIMENTO, INTERESSI_REVOCA, ID_CAUSALE_DISIMPEGNO, FLAG_ORDINE_RECUPERO, ID_MANCATO_RECUPERO, ID_GESTIONE_REVOCA)\n" +
                            "VALUES\n" +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE, ?, ?, ?, ?, ?)";
                    if(provvedimentoRevocaVO.getImportoConcessoContributo() != null &&
                        provvedimentoRevocaVO.getImpContribRevocaNoRecu() != null &&
                        !provvedimentoRevocaVO.getImpContribRevocaNoRecu().equals(BigDecimal.ZERO) &&
                        (provvedimentoRevocaVO.getImpContribRevocaRecu() == null || provvedimentoRevocaVO.getImpContribRevocaRecu().equals(BigDecimal.ZERO))
                    ) {
                        LOG.info(prf + " Importo concesso per il contributo != null \n");
                        //alg15
                        LOG.info(prf + " Query: " + queryAlg15 + "\n\n");
                        if(getJdbcTemplate().queryForObject(queryAlg15, new Object[]{provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 1}, Long.class) == 0){
                            LOG.info(prf + " Query: " + getIdRevoca + "\n\n");
                            Long idRevoca = getJdbcTemplate().queryForObject(getIdRevoca, Long.class);
                            //alg16
                            LOG.info(prf + " Query: " + queryAlg16 + "\n\n");
                            getJdbcTemplate().update(queryAlg16, ps -> {
                                ps.setLong(1, idRevoca);
                                BigDecimal importo = BigDecimal.ZERO;
                                if(provvedimentoRevocaVO.getImpContribRevocaRecu() != null){
                                    importo = importo.add(provvedimentoRevocaVO.getImpContribRevocaRecu());
                                }
                                if(provvedimentoRevocaVO.getImpContribRevocaNoRecu() != null){
                                    importo = importo.add(provvedimentoRevocaVO.getImpContribRevocaNoRecu());
                                }
                                ps.setBigDecimal(2, importo);
                                ps.setDate(3, new java.sql.Date(provvedimentoRevocaVO.getDataNotifica().getTime()));
                                ps.setLong(4, provvedimentoRevocaVO.getIdProgetto());
                                ps.setLong(5, userInfoSec.getIdUtente());
                                ps.setString(6, provvedimentoRevocaVO.getEstremi());
                                ps.setObject(7, provvedimentoRevocaVO.getIdMotivoRevoca());
                                ps.setString(8, provvedimentoRevocaVO.getNote());
                                ps.setLong(9, provvedimentoRevocaVO.getIdModAgevContrib()); //id_modalita_agevolazione
                                ps.setBigDecimal(10, provvedimentoRevocaVO.getImpContribInteressi());
                                if(provvedimentoRevocaVO.getFlagContribRevoca()){
                                    if(provvedimentoRevocaVO.getFlagContribDecurtaz()){
                                        ps.setLong(11, 5);
                                    }else if(provvedimentoRevocaVO.getFlagContribMinorSpese()){
                                        ps.setLong(11, 4);
                                    }else{
                                        ps.setLong(11, 1);
                                    }
                                }else{
                                    if(provvedimentoRevocaVO.getFlagContribDecurtaz()){
                                        ps.setLong(11, 3);
                                    }else if(provvedimentoRevocaVO.getFlagContribMinorSpese()){
                                        ps.setLong(11, 2);
                                    }else{
                                        ps.setLong(11, 1);
                                    }
                                }
                                ps.setString(12, provvedimentoRevocaVO.getFlagOrdineRecupero() ? "S" : "N");
                                ps.setObject(13, provvedimentoRevocaVO.getIdMancatoRecupero());
                                ps.setLong(14, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                            });
                        }
                    }
                    if(provvedimentoRevocaVO.getImportoConcessoFinanziamento() != null &&
                        provvedimentoRevocaVO.getImpFinanzRevocaNoRecu() != null &&
                        !provvedimentoRevocaVO.getImpFinanzRevocaNoRecu().equals(BigDecimal.ZERO) &&
                        (provvedimentoRevocaVO.getImpFinanzRevocaRecu() == null || provvedimentoRevocaVO.getImpFinanzRevocaRecu().equals(BigDecimal.ZERO))
                    ) {
                        LOG.info(prf + " Importo concesso per il finanziamento != null \n");
                        //alg15
                        LOG.info(prf + " Query: " + queryAlg15 + "\n\n");
                        if(getJdbcTemplate().queryForObject(queryAlg15, new Object[]{provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 5}, Long.class) == 0){
                            LOG.info(prf + " Query: " + getIdRevoca + "\n\n");
                            Long idRevoca = getJdbcTemplate().queryForObject(getIdRevoca, Long.class);
                            //alg16
                            LOG.info(prf + " Query: " + queryAlg16 + "\n\n");
                            getJdbcTemplate().update(queryAlg16, ps -> {
                                ps.setLong(1, idRevoca);
                                BigDecimal importo = BigDecimal.ZERO;
                                if(provvedimentoRevocaVO.getImpFinanzRevocaRecu() != null){
                                    importo = importo.add(provvedimentoRevocaVO.getImpFinanzRevocaRecu());
                                }
                                if(provvedimentoRevocaVO.getImpFinanzRevocaNoRecu() != null){
                                    importo = importo.add(provvedimentoRevocaVO.getImpFinanzRevocaNoRecu());
                                }
                                if(provvedimentoRevocaVO.getImpFinanzPreRecu() != null){
                                    importo = importo.add(provvedimentoRevocaVO.getImpFinanzPreRecu());
                                }
                                ps.setBigDecimal(2, importo);
                                ps.setDate(3, new java.sql.Date(provvedimentoRevocaVO.getDataNotifica().getTime()));
                                ps.setLong(4, provvedimentoRevocaVO.getIdProgetto());
                                ps.setLong(5, userInfoSec.getIdUtente());
                                ps.setString(6, provvedimentoRevocaVO.getEstremi());
                                ps.setObject(7, provvedimentoRevocaVO.getIdMotivoRevoca());
                                ps.setString(8, provvedimentoRevocaVO.getNote());
                                ps.setLong(9, provvedimentoRevocaVO.getIdModAgevFinanz()); //id_modalita_agevolazione
                                ps.setBigDecimal(10, provvedimentoRevocaVO.getImpFinanzInteressi());
                                if(provvedimentoRevocaVO.getFlagFinanzRevoca()){
                                    if(provvedimentoRevocaVO.getFlagFinanzDecurtaz()){
                                        ps.setLong(11, 5);
                                    }else if(provvedimentoRevocaVO.getFlagFinanzMinorSpese()){
                                        ps.setLong(11, 4);
                                    }else{
                                        ps.setLong(11, 1);
                                    }
                                }else{
                                    if(provvedimentoRevocaVO.getFlagFinanzDecurtaz()){
                                        ps.setLong(11, 3);
                                    }else if(provvedimentoRevocaVO.getFlagFinanzMinorSpese()){
                                        ps.setLong(11, 2);
                                    }else{
                                        ps.setLong(11, 1);
                                    }
                                }
                                ps.setString(12, provvedimentoRevocaVO.getFlagOrdineRecupero() ? "S" : "N");
                                ps.setObject(13, provvedimentoRevocaVO.getIdMancatoRecupero());
                                ps.setLong(14, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                            });
                        }
                    }
                    if(provvedimentoRevocaVO.getImportoConcessoGaranzia() != null &&
                        provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu() != null &&
                        !provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu().equals(BigDecimal.ZERO) &&
                        (provvedimentoRevocaVO.getImpGaranziaRevocaRecu() == null || provvedimentoRevocaVO.getImpGaranziaRevocaRecu().equals(BigDecimal.ZERO))
                    ) {
                        LOG.info(prf + " Importo concesso per il garanzia != null \n");
                        //alg15
                        LOG.info(prf + " Query: " + queryAlg15 + "\n\n");
                        if(getJdbcTemplate().queryForObject(queryAlg15, new Object[]{provvedimentoRevocaVO.getIdProvvedimentoRevoca(), 10}, Long.class) == 0){
                            LOG.info(prf + " Query: " + getIdRevoca + "\n\n");
                            Long idRevoca = getJdbcTemplate().queryForObject(getIdRevoca, Long.class);
                            //alg16
                            LOG.info(prf + " Query: " + queryAlg16 + "\n\n");
                            getJdbcTemplate().update(queryAlg16, ps -> {
                                ps.setLong(1, idRevoca);
                                BigDecimal importo = BigDecimal.ZERO;
                                if(provvedimentoRevocaVO.getImpGaranziaRevocaRecu() != null){
                                    importo = importo.add(provvedimentoRevocaVO.getImpGaranziaRevocaRecu());
                                }
                                if(provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu() != null){
                                    importo = importo.add(provvedimentoRevocaVO.getImpGaranziaRevocaNoRecu());
                                }
                                if(provvedimentoRevocaVO.getImpGaranzPreRecu() != null){
                                    importo = importo.add(provvedimentoRevocaVO.getImpGaranzPreRecu());
                                }
                                ps.setBigDecimal(2, importo);
                                ps.setDate(3, new java.sql.Date(provvedimentoRevocaVO.getDataNotifica().getTime()));
                                ps.setLong(4, provvedimentoRevocaVO.getIdProgetto());
                                ps.setLong(5, userInfoSec.getIdUtente());
                                ps.setString(6, provvedimentoRevocaVO.getEstremi());
                                ps.setObject(7, provvedimentoRevocaVO.getIdMotivoRevoca());
                                ps.setString(8, provvedimentoRevocaVO.getNote());
                                ps.setLong(9, provvedimentoRevocaVO.getIdModAgevGaranz()); //id_modalita_agevolazione
                                ps.setBigDecimal(10, provvedimentoRevocaVO.getImpGaranziaInteressi());
                                if(provvedimentoRevocaVO.getFlagGaranziaRevoca()){
                                    if(provvedimentoRevocaVO.getFlagGaranziaDecurtaz()){
                                        ps.setLong(11, 5);
                                    }else if(provvedimentoRevocaVO.getFlagGaranziaMinorSpese()){
                                        ps.setLong(11, 4);
                                    }else{
                                        ps.setLong(11, 1);
                                    }
                                }else{
                                    if(provvedimentoRevocaVO.getFlagGaranziaDecurtaz()){
                                        ps.setLong(11, 3);
                                    }else if(provvedimentoRevocaVO.getFlagGaranziaMinorSpese()){
                                        ps.setLong(11, 2);
                                    }else{
                                        ps.setLong(11, 1);
                                    }
                                }
                                ps.setString(12, provvedimentoRevocaVO.getFlagOrdineRecupero() ? "S" : "N");
                                ps.setObject(13, provvedimentoRevocaVO.getIdMancatoRecupero());
                                ps.setLong(14, provvedimentoRevocaVO.getIdProvvedimentoRevoca());
                            });
                        }
                    }
                }
            }
        }


        LOG.info(prf + "END");
    }

    @Override
    public void eliminaProvvedimentoRevoca(Long numeroRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        //CONTROLLI
        String query = "SELECT id_stato_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.numero_revoca = ?\n" +
                "AND gestioneRevoca.id_tipologia_revoca = 3\n" +
                "AND gestioneRevoca.dt_fine_validita IS NULL";
        Long statoRevoca;
        try {
            statoRevoca = getJdbcTemplate().queryForObject(query, new Object[]{numeroRevoca}, Long.class);
        }catch (EmptyResultDataAccessException e) {
            throw new ErroreGestitoException("Nessun provvedimento di revoca attivo con il numero revoca passato");
        }
        if(statoRevoca != 5) {
            throw new ErroreGestitoException("Per eliminare un provvedimento di revoca questo deve trovarsi in stato 'bozza'");
        }

        //DELETE
        query =     //CANCELLO IL PROVVEDIMENTO DI REVOCA IN BOZZA
                "DELETE FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                        "WHERE gestioneRevoca.id_tipologia_revoca = 3\n" +
                        "AND gestioneRevoca.id_stato_revoca = 5\n" +
                        "AND numero_revoca = ?\n";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, numeroRevoca);

        query = //CANCELLO LA PROPOSTA DI REVOCA IN STATO CREATA BOZZA PROVVEDIMENTO DI REVOCA
                "DELETE FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                        "WHERE gestioneRevoca.id_tipologia_revoca = 1\n" +
                        "AND gestioneRevoca.id_stato_revoca = 3\n" +
                        "AND numero_revoca = ?\n";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        if(getJdbcTemplate().update(query, numeroRevoca) != 0) {

            query = //RI ABILITO LA PROPOSTA DI REVOCA IN STATO CREATA
                    "UPDATE PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                            "SET gestioneRevoca.dt_fine_validita = NULL, \n" +
                            "gestioneRevoca.dt_aggiornamento = NULL, \n" +
                            "gestioneRevoca.id_utente_agg = NULL\n" +
                            "WHERE gestioneRevoca.id_tipologia_revoca = 1\n" +
                            "AND gestioneRevoca.id_stato_revoca = 1\n" +
                            "AND numero_revoca = ?\n";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            getJdbcTemplate().update(query, numeroRevoca);
        }else {
            query = //CANCELLO IL PROCEDIMENTO DI REVOCA IN STATO CREATA BOZZA PROVVEDIMENTO DI REVOCA
                    "DELETE FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                            "WHERE gestioneRevoca.id_tipologia_revoca = 2\n" +
                            "AND gestioneRevoca.id_stato_revoca = 3\n" +
                            "AND numero_revoca = ?\n";
            LOG.info(prf + "Query: \n\n" + query + "\n");
            if(getJdbcTemplate().update(query, numeroRevoca) != 0) {
                query = //RI ABILITO IL PROCEDIMENTO DI REVOCA PRECEDENTE
                        "UPDATE PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                                "SET gestioneRevoca.dt_fine_validita = NULL, \n" +
                                "gestioneRevoca.dt_aggiornamento = NULL, \n" +
                                "gestioneRevoca.id_utente_agg = NULL\n" +
                                "WHERE gestioneRevoca.id_tipologia_revoca = 2\n" +
                                "AND gestioneRevoca.id_stato_revoca = 7\n" +
                                "AND numero_revoca = ?\n";
                LOG.info(prf + "Query: \n\n" + query + "\n");
                //SE NON HO TROVATO STATO 7 ALLORA DEVO RIABILITARE STATO 6
                if(getJdbcTemplate().update(query, numeroRevoca) == 0) {
                    query = //RI ABILITO IL PROCEDIMENTO DI REVOCA PRECEDENTE
                            "UPDATE PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                                    "SET gestioneRevoca.dt_fine_validita = NULL, \n" +
                                    "gestioneRevoca.dt_aggiornamento = NULL, \n" +
                                    "gestioneRevoca.id_utente_agg = NULL\n" +
                                    "WHERE gestioneRevoca.id_tipologia_revoca = 2\n" +
                                    "AND gestioneRevoca.id_stato_revoca = 6\n" +
                                    "AND numero_revoca = ?\n";
                    LOG.info(prf + "Query: \n\n" + query + "\n");
                    getJdbcTemplate().update(query, numeroRevoca);
                }
            }
        }

        LOG.info(prf + "END");
    }

    @Override
    public void verificaImportiTotaliRevoca(Long numeroRevoca, ProvvedimentoRevocaVO provvedimentoRevocaVO) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try{
            List<String> errore;
            String query = "SELECT \n" +
                    "CASE WHEN modAgev.id_modalita_agevolazione_rif = 1 THEN nvl(gestioneRevoca.IMP_CONTRIB_REVOCA_RECU + gestioneRevoca.IMP_CONTRIB_REVOCA_NO_RECU, 0)\n" +
                    "ELSE (\n" +
                    "\tCASE WHEN modAgev.id_modalita_agevolazione_rif = 5 THEN nvl(gestioneRevoca.IMP_FINANZ_REVOCA_RECU + gestioneRevoca.IMP_FINANZ_REVOCA_NO_RECU + gestioneRevoca.IMP_FINANZ_PRERECUPERO, 0)\n" +
                    "\tELSE (\n" +
                    "\t\tCASE WHEN modAgev.id_modalita_agevolazione_rif = 10 THEN nvl(gestioneRevoca.IMP_GARANZIA_REVOCA_RECUPERO + gestioneRevoca.IMP_GARANZIA_REVOCA_NO_RECU + gestioneRevoca.IMP_GARANZIA_PRERECUPERO, 0)\n" +
                    "\t\tELSE (0) END \n" +
                    "\t) END \n" +
                    ") END AS daRevocare,\n" +
                    "concesso.quota_importo_agevolato - NVL(importoRevocato.importo, 0) AS concessoResiduo,\n" +
                    "modAgev.desc_modalita_agevolazione AS modAgev\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = gestioneRevoca.id_progetto\n" +
                    "JOIN PBANDI_T_CONTO_ECONOMICO contoEconomico ON contoEconomico.id_domanda = progetto.id_domanda\n" +
                    "LEFT JOIN PBANDI_R_CONTO_ECONOM_MOD_AGEV concesso ON concesso.id_conto_economico = contoEconomico.id_conto_economico \n" +
                    "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.id_modalita_agevolazione = concesso.id_modalita_agevolazione\n" +
                    "LEFT JOIN (\n" +
                    "SELECT sum(ptr.importo) AS importo, \n" +
                    "ptgr.id_gestione_revoca,\n" +
                    "pdma.id_modalita_agevolazione_rif\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA ptgr \n" +
                    "JOIN PBANDI_T_REVOCA ptr ON ptr.id_progetto = ptgr.id_progetto\n" +
                    "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.id_modalita_agevolazione = ptr.id_modalita_agevolazione\n" +
                    "WHERE (ptr.ID_GESTIONE_REVOCA IS NULL AND (ptgr.DT_GESTIONE IS NULL OR ptr.DT_REVOCA+1 < ptgr.DT_GESTIONE)) OR \n" +
                    "(ptr.ID_GESTIONE_REVOCA IS NOT NULL AND ptgr.ID_GESTIONE_REVOCA > ptr.ID_GESTIONE_REVOCA)\n \n" +
                    "GROUP BY ptgr.ID_GESTIONE_REVOCA, pdma.id_modalita_agevolazione_rif\n" +
                    ") importoRevocato ON importoRevocato.id_gestione_revoca = gestioneRevoca.id_gestione_revoca\n" +
                    "\tAND importoRevocato.id_modalita_agevolazione_rif = modAgev.id_modalita_agevolazione_rif\n" +
                    "WHERE gestioneRevoca.numero_revoca = ?\n" +
                    "AND gestioneRevoca.id_tipologia_revoca = 3 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL \n" +
                    "AND contoEconomico.dt_fine_validita IS NULL";

            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");

            errore = getJdbcTemplate().query(
                    query,
                    new Object[]{numeroRevoca},
                    (rs, rowNum) -> {
                        BigDecimal importoDaRevocare = rs.getBigDecimal("daRevocare");
                        BigDecimal importoConcessoResiduo = rs.getBigDecimal("concessoResiduo");
                        if(importoDaRevocare != null && importoDaRevocare.compareTo(importoConcessoResiduo) > 0){
                            return "Importo totale della revoca maggiore del valore concesso per la modalita di agevolazione " + rs.getString("modAgev");
                        }
                        return null;
                    }
            );

            errore.removeAll(Collections.singleton(null));

            if(!errore.isEmpty()){
                throw new ErroreGestitoException(String.join("\n", errore));
            }
        } catch (ErroreGestitoException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to verificaImportiTotaliRevoca", e);
            throw new ErroreGestitoException("DaoException while trying to verificaImportiTotaliRevoca", e);
        } finally {
            LOG.info(prf + " END");
        }
    }

    @Override
    public void abilitaEmettiProvvedimento(Long numeroRevoca) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        try{
            String query;

            //ALG2 Non è stato inserito alcun valore
            query = "SELECT \n" +
                    "NVL(gestioneRevoca.IMP_CONTRIB_REVOCA_RECU,0) + NVL(gestioneRevoca.IMP_CONTRIB_REVOCA_NO_RECU,0) AS importoDaRevocareContrib,\n" +
                    "NVL(gestioneRevoca.IMP_FINANZ_REVOCA_RECU,0) + NVL(gestioneRevoca.IMP_FINANZ_REVOCA_NO_RECU,0) AS importoDaRevocareFinanz,\n" +
                    "NVL(gestioneRevoca.IMP_GARANZIA_REVOCA_RECUPERO,0) + NVL(gestioneRevoca.IMP_GARANZIA_REVOCA_NO_RECU,0) AS importoDaRevocareGaranzia\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "WHERE gestioneRevoca.numero_revoca = ?\n" +
                    "AND gestioneRevoca.id_tipologia_revoca = 3 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            if(getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{numeroRevoca},
                    (rs, rowNum) -> Objects.equals(rs.getBigDecimal("importoDaRevocareContrib"), BigDecimal.ZERO) && Objects.equals(rs.getBigDecimal("importoDaRevocareFinanz"), BigDecimal.ZERO) && Objects.equals(rs.getBigDecimal("importoDaRevocareGaranzia"), BigDecimal.ZERO)
            )){
                throw new ErroreGestitoException("Non è stato inserito alcun valore");
            }
            //ALG3 & ALG4 Incoerenza tra importo inserito e causale disimpegno
            query = "SELECT \n" +
                    "FLAG_CONTRIB_REVOCA AS flagContribRevoca,\n" +
                    "NVL(IMP_CONTRIB_REVOCA_RECU,0) AS impContribRevocaRecu,\n" +
                    "FLAG_FINANZ_REVOCA AS flagFinanzRevoca, \n" +
                    "NVL(IMP_FINANZ_REVOCA_RECU,0) AS impFinanzRevocaRecu,\n" +
                    "FLAG_GARANZIA_REVOCA AS flagGaranziaRevoca,\n" +
                    "NVL(IMP_GARANZIA_REVOCA_RECUPERO,0) AS impGaranziaRevocaRecu,\n" +
                    "FLAG_CONTRIB_MINOR_SPESE AS flagContribMinorSpese,\n" +
                    "FLAG_CONTRIB_DECURTAZ AS flagContribDecurtaz,\n" +
                    "NVL(IMP_CONTRIB_REVOCA_NO_RECU,0) AS impContribRevocaNoRecu,\n" +
                    "FLAG_FINANZ_MINOR_SPESE AS flagFinanzMinorSpese,\n" +
                    "FLAG_FINANZ_DECURTAZ AS flagFinanzDecurtaz,\n" +
                    "NVL(IMP_FINANZ_REVOCA_NO_RECU,0) AS impFinanzRevocaNoRecu,\n" +
                    "FLAG_GARANZIA_MINOR_SPESE as flagGaranziaMinorSpese,\n" +
                    "FLAG_GARANZIA_DECURTAZ AS flagGaranziaDecurtaz,\n" +
                    "NVL(IMP_GARANZIA_REVOCA_NO_RECU,0) AS impGaranziaRevocaNoRecu\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "WHERE gestioneRevoca.numero_revoca = ?\n" +
                    "AND gestioneRevoca.id_tipologia_revoca = 3 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            if(getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{numeroRevoca},
                    (rs, rowNum) -> {
                        String flag;
                        String flag2;

                        //ALG3
                        flag = rs.getString("flagContribRevoca");
                        if(flag != null && flag.equals("S") && rs.getLong("impContribRevocaRecu")==0){
                            return true;
                        }
                        flag = rs.getString("flagFinanzRevoca");
                        if(flag != null && flag.equals("S") && rs.getLong("impFinanzRevocaRecu")==0){
                            return true;
                        }
                        flag = rs.getString("flagGaranziaRevoca");
                        if(flag != null && flag.equals("S") && rs.getLong("impGaranziaRevocaRecu")==0){
                            return true;
                        }
                        //ALG4
                        flag = rs.getString("flagContribMinorSpese");
                        flag2 = rs.getString("flagContribDecurtaz");
                        if(((flag != null && flag.equals("S")) ||
                                (flag2 != null && flag2.equals("S"))) && rs.getLong("impContribRevocaNoRecu")==0){
                            return true;
                        }
                        flag = rs.getString("flagFinanzMinorSpese");
                        flag2 = rs.getString("flagFinanzDecurtaz");
                        if(((flag != null && flag.equals("S")) ||
                                (flag2 != null && flag2.equals("S"))) && rs.getLong("impFinanzRevocaNoRecu")==0){
                            return true;
                        }
                        flag = rs.getString("flagGaranziaMinorSpese");
                        flag2 = rs.getString("flagGaranziaDecurtaz");
                        return ((flag != null && flag.equals("S")) ||
                                (flag2 != null && flag2.equals("S"))) && rs.getLong("impGaranziaRevocaNoRecu") == 0;
                    }
            )){
                throw new ErroreGestitoException("Incoerenza tra importo inserito e causale disimpegno");
            }
            //ALG5 Campi relativi alla determina non compilati
            query = "SELECT \n" +
                    "gestioneRevoca.FLAG_DETERMINA AS flagDetermina,\n" +
                    "gestioneRevoca.DT_DETERMINA AS dtDetermina,\n" +
                    "gestioneRevoca.estremi AS estremi\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "WHERE gestioneRevoca.numero_revoca = ?\n" +
                    "AND gestioneRevoca.id_tipologia_revoca = 3 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL ";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            if(getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{numeroRevoca},
                    (rs, rowNum) -> {
                        String flag = rs.getString("flagDetermina");
                        return flag != null && flag.equals("S") && (rs.getDate("dtDetermina") == null || rs.getString("estremi") == null);
                    }
            )){
                throw new ErroreGestitoException("Campi relativi alla determina non compilati");
            }
            //ALG6 Importi non inseriti per la revoca con recupero
            query = "SELECT \n" +
                    "NVL(gestioneRevoca.FLAG_ORDINE_RECUPERO, 0) AS flagOrdineRecupero,\n" +
                    "NVL(gestioneRevoca.IMP_CONTRIB_REVOCA_RECU, 0) AS impContribRevocaRecu,\n" +
                    "NVL(gestioneRevoca.IMP_FINANZ_REVOCA_RECU, 0) AS impFinanzRevocaRecu,\n" +
                    "NVL(gestioneRevoca.IMP_GARANZIA_REVOCA_RECUPERO, 0) AS impGaranziaRevocaRecu\n" +
                    "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                    "WHERE gestioneRevoca.numero_revoca = ?\n" +
                    "AND gestioneRevoca.id_tipologia_revoca = 3 \n" +
                    "AND gestioneRevoca.dt_fine_validita IS NULL";
            LOG.info(prf + "\nQuery: \n\n" + query + "\n\n");
            if(getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{numeroRevoca},
                    (rs, rowNum) -> {
                        String flag = rs.getString("flagOrdineRecupero");
                        return flag != null && flag.equals("S") && (rs.getLong("impContribRevocaRecu") == 0 && rs.getLong("impFinanzRevocaRecu") == 0 && rs.getLong("impGaranziaRevocaRecu") == 0);
                    }
            )){
                throw new ErroreGestitoException("Importi non inseriti per la revoca con recupero");
            }
        } catch (ErroreGestitoException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to abilitaEmettiProvvedimento", e);
            throw new ErroreGestitoException("DaoException while trying to abilitaEmettiProvvedimento", e);
        } finally {
            LOG.info(prf + " END");
        }
    }

    @Transactional
    @Override
    public void emettiProvvedimentoRevoca(Long numeroGestioneRevoca, Long giorniScadenza, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        String getIdGestioneRevoca =
                "SELECT gestioneRevoca.id_gestione_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.NUMERO_REVOCA = ?" +
                "AND gestioneRevoca.DT_FINE_VALIDITA IS NULL \n" +
                "AND gestioneRevoca.ID_TIPOLOGIA_REVOCA = 3";
        LOG.info(prf + "\nQuery: \n\n" + getIdGestioneRevoca + "\n\n");
        Long idGestioneRevoca = getJdbcTemplate().queryForObject(getIdGestioneRevoca, new Object[]{numeroGestioneRevoca}, Long.class);

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );

        //AVVIO ITER AUTORIZZATIVO
        String getIdProgetto =
                "SELECT gestioneRevoca.id_progetto\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.ID_GESTIONE_REVOCA = ?";
        LOG.info(prf + "\nQuery: \n\n" + getIdProgetto + "\n\n");
        Long idProgetto = getJdbcTemplate().queryForObject(getIdProgetto, new Object[]{idGestioneRevoca}, Long.class);

        String erroreIter = iterAutorizzativiDAO.avviaIterAutorizzativo(11L, idEntitaGestioneRevoca, idGestioneRevoca, idProgetto, userInfoSec.getIdUtente());
        if(!Objects.equals(erroreIter, "")){
            throw new ErroreGestitoException(erroreIter);
        }

        //MODIFICO PROVVEDIMENTO DI REVOCA
        String query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA  \n" +
                "SET \n" +
                "DT_NOTIFICA = NULL,\n" +
                "GG_RISPOSTA = ?,\n" +
                "ID_ATTIVITA_REVOCA = 4, \n" +
                "DT_ATTIVITA = CURRENT_DATE, \n" +
                "ID_UTENTE_AGG = ?, \n" +
                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                "WHERE ID_GESTIONE_REVOCA = ?";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, giorniScadenza, userInfoSec.getIdUtente(), idGestioneRevoca);

        LOG.info(prf + "END");
    }

    @Transactional
    @Override
    public void confermaProvvedimentoRevoca(Long numeroGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        String getIdGestioneRevoca =
                "SELECT gestioneRevoca.id_gestione_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.NUMERO_REVOCA = ?" +
                "AND gestioneRevoca.DT_FINE_VALIDITA IS NULL \n" +
                "AND gestioneRevoca.ID_TIPOLOGIA_REVOCA = 3";
        LOG.info(prf + "\nQuery: \n\n" + getIdGestioneRevoca + "\n\n");
        Long idGestioneRevoca = getJdbcTemplate().queryForObject(getIdGestioneRevoca, new Object[]{numeroGestioneRevoca}, Long.class);

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                    "FROM PBANDI_C_ENTITA entita \n" +
                    "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );

        //AVVIO ITER AUTORIZZATIVO
        String getIdProgetto =
                "SELECT gestioneRevoca.id_progetto\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.ID_GESTIONE_REVOCA = ?";
        LOG.info(prf + "\nQuery: \n\n" + getIdProgetto + "\n\n");
        Long idProgetto = getJdbcTemplate().queryForObject(getIdProgetto, new Object[]{idGestioneRevoca} ,Long.class);

        String erroreIter = iterAutorizzativiDAO.avviaIterAutorizzativo(17L, idEntitaGestioneRevoca, idGestioneRevoca, idProgetto, userInfoSec.getIdUtente());
        if(!Objects.equals(erroreIter, "")){
            throw new ErroreGestitoException(erroreIter);
        }

        //MODIFICO PROVVEDIMENTO DI REVOCA
        String query =
                "UPDATE PBANDI_T_GESTIONE_REVOCA \n" +
                "SET \n" +
                "ID_ATTIVITA_REVOCA = 13, \n" +
                "DT_ATTIVITA = CURRENT_DATE, \n" +
                "ID_UTENTE_AGG = ?, \n" +
                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                "WHERE ID_GESTIONE_REVOCA = ?";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, userInfoSec.getIdUtente(), idGestioneRevoca);

        LOG.info(prf + "END");
    }

    @Transactional
    @Override
    public void ritiroInAutotutelaRevoca(Long numeroGestioneRevoca, HttpServletRequest req) {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        String getIdGestioneRevoca ="SELECT gestioneRevoca.id_gestione_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.NUMERO_REVOCA = ?" +
                "AND gestioneRevoca.DT_FINE_VALIDITA IS NULL \n" +
                "AND gestioneRevoca.ID_TIPOLOGIA_REVOCA = 3";
        LOG.info(prf + "\nQuery: \n\n" + getIdGestioneRevoca + "\n\n");
        Long idGestioneRevoca = getJdbcTemplate().queryForObject(getIdGestioneRevoca, new Object[]{numeroGestioneRevoca}, Long.class);

        Long idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                Long.class
        );

        //AVVIO ITER AUTORIZZATIVO
        String getIdProgetto ="SELECT gestioneRevoca.id_progetto\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.ID_GESTIONE_REVOCA = ?";
        LOG.info(prf + "\nQuery: \n\n" + getIdProgetto + "\n\n");
        Long idProgetto = getJdbcTemplate().queryForObject(getIdProgetto, new Object[]{idGestioneRevoca} ,Long.class);

        String erroreIter = iterAutorizzativiDAO.avviaIterAutorizzativo(12L, idEntitaGestioneRevoca, idGestioneRevoca, idProgetto, userInfoSec.getIdUtente());
        if(!Objects.equals(erroreIter, "")){
            throw new ErroreGestitoException(erroreIter);
        }

        //MODIFICO PROVVEDIMENTO DI REVOCA
        String query = "UPDATE PBANDI_T_GESTIONE_REVOCA  \n" +
                "SET \n" +
                "ID_ATTIVITA_REVOCA = 5, \n" +
                "DT_ATTIVITA = CURRENT_DATE, \n" +
                "ID_UTENTE_AGG = ?, \n" +
                "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                "WHERE ID_GESTIONE_REVOCA = ?";
        LOG.info(prf + "Query: \n\n" + query + "\n");
        getJdbcTemplate().update(query, userInfoSec.getIdUtente(), idGestioneRevoca);

        LOG.info(prf + "END");
    }

    //UTILS
    @Override
    public void aggiungiAllegato(Long numeroRevoca, Boolean letteraAccompagnatoria, int ambitoAllegato, byte[] allegato, String nomeAllegato, HttpServletRequest req) {
        //  ambitoAllegato -> 0 = emissione, 1 = ritiro in autotutela, 2 = conferma provvedimento
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String prf = "[" + className + "::" + methodName + "]";
        LOG.info(prf + "BEGIN");

        String getIdGestioneRevoca ="SELECT gestioneRevoca.id_gestione_revoca\n" +
                "FROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca\n" +
                "WHERE gestioneRevoca.NUMERO_REVOCA = ?" +
                "AND gestioneRevoca.DT_FINE_VALIDITA IS NULL \n" +
                "AND gestioneRevoca.ID_TIPOLOGIA_REVOCA = 3";
        LOG.info(prf + "\nQuery: \n\n" + getIdGestioneRevoca + "\n\n");
        Long idGestioneRevoca = getJdbcTemplate().queryForObject(getIdGestioneRevoca, new Object[]{numeroRevoca}, Long.class);

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        BigDecimal idEntitaGestioneRevoca = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_GESTIONE_REVOCA'",
                BigDecimal.class
        );

        DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
        if(ambitoAllegato < 0 || ambitoAllegato > 2){
            throw new ErroreGestitoException("ambitoAllegato valorizzato erroneamente!");
        }
        documentoIndexVO.setIdTipoDocumentoIndex(new BigDecimal(47 + ambitoAllegato + (letteraAccompagnatoria ? 0 : 6)));
        documentoIndexVO.setIdEntita(idEntitaGestioneRevoca);
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

        //se lettera accompagnatoria già presente
        if(letteraAccompagnatoria){
            String query =
                            "SELECT COUNT(1)\n" +
                            "FROM PBANDI_T_DOCUMENTO_INDEX doc \n" +
                            "WHERE doc.ID_TIPO_DOCUMENTO_INDEX = ?\n" +
                            "AND doc.ID_ENTITA = ? \n" +
                            "AND doc.ID_TARGET IN (\n" +
                            "\tSELECT gestioneRevoca.ID_GESTIONE_REVOCA \n" +
                            "\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                            "\tWHERE gestioneRevoca.NUMERO_REVOCA = (\n" +
                            "\t\tSELECT gestioneRevoca.NUMERO_REVOCA \n" +
                            "\t\tFROM PBANDI_T_GESTIONE_REVOCA gestioneRevoca \n" +
                            "\t\tWHERE gestioneRevoca.ID_GESTIONE_REVOCA = ?\n" +
                            "\t) AND gestioneRevoca.ID_TIPOLOGIA_REVOCA = 3\n" +
                            ")";

            boolean letteraPresente = getJdbcTemplate().queryForObject(
                    query,
                    new Object[]{documentoIndexVO.getIdTipoDocumentoIndex(), idEntitaGestioneRevoca, idGestioneRevoca},
                    Long.class
            ) != 0;

            if(letteraPresente){
                throw new ErroreGestitoException("Lettera accompagnatoria già presente");
            }
        }

        //salva allegato
        if(!documentoManager.salvaFileConVisibilita(allegato, documentoIndexVO, null)) {
            throw new ErroreGestitoException("Errore durante il salvataggio dell'allegato");
        }

        LOG.info(prf + "END");
    }
}
