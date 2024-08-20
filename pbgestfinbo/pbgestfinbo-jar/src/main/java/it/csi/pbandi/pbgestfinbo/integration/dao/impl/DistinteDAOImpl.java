/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao.impl;

import it.csi.pbandi.pbgestfinbo.business.manager.DocumentoManager;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.exception.ErroreGestitoException;
import it.csi.pbandi.pbgestfinbo.exception.RecordNotFoundException;
import it.csi.pbandi.pbgestfinbo.integration.dao.DistinteDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.IterAutorizzativiDAO;
import it.csi.pbandi.pbgestfinbo.integration.dao.impl.rowmapper.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.DatiDistintaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroProposteErogazioneDistVO;
import it.csi.pbandi.pbgestfinbo.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DistinteDAOImpl extends JdbcDaoSupport implements DistinteDAO {
    private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    DocumentoManager documentoManager;

    @Autowired
    IterAutorizzativiDAO iterAutorizzativiDAO;

    @Autowired
    public DistinteDAOImpl(DataSource dataSource){ setDataSource(dataSource); }

    public DistinteDAOImpl() {

    }

    @Autowired
    NeofluxBusinessImpl neofluxBusinessImpl;

    @Override
    public Boolean nuovaDistinta(Long idBando, Long idModalitaAgevolazione, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::nuovaDistinta]";
        LOG.info(prf + " BEGIN");
        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        try{

            String query = "SELECT DISTINCT \n" +
                    "soggettoProgetto.id_soggetto AS idSoggetto, \n" +
                    "progetto.id_progetto AS idProgetto, \n" +
                    "progetto.codice_visualizzato AS codiceVisualizzato, \n" +
                    "propostaErogazione.id_proposta AS idPropostaErogazione\n" +
                    "FROM PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione \n" +
                    "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.ID_MODALITA_AGEVOLAZIONE = propostaErogazione.ID_MODALITA_AGEVOLAZIONE \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT lineaIntervento ON lineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                    "JOIN PBANDI_T_BANDO bando ON bando.id_bando = lineaIntervento.id_bando \n" +
                    "JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.id_bando = bando.id_bando \n" +
                    "JOIN PBANDI_T_ESTREMI_BANCARI pteb ON pteb.id_estremi_bancari = prbmaeb.id_estremi_bancari\n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
                    "WHERE soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND soggettoProgetto.id_tipo_beneficiario != 4 \n" +
                    "AND modAgev.id_modalita_agevolazione_rif = ? \n" +
                    "AND bando.id_bando = ?";
            for(DistintaPropostaErogazioneVO distintaPropostaErogazioneVO : verificaPosizione(getJdbcTemplate().query(
                    query,
                    ps -> {
                        ps.setLong(1, idModalitaAgevolazione);
                        ps.setLong(2, idBando);
                    },
                    (rs, rowNum) -> {
                        DistintaPropostaErogazioneVO distintaPropostaErogazioneVO = new DistintaPropostaErogazioneVO();
                        distintaPropostaErogazioneVO.setIdSoggetto(rs.getLong("idSoggetto"));
                        distintaPropostaErogazioneVO.setIdProgetto(rs.getLong("idProgetto"));
                        distintaPropostaErogazioneVO.setCodiceVisualizzato(rs.getString("codiceVisualizzato"));
                        distintaPropostaErogazioneVO.setIdPropostaErogazione(rs.getLong("idPropostaErogazione"));
                        distintaPropostaErogazioneVO.setAbilitata(true);
                        return distintaPropostaErogazioneVO;
                    }), userInfoSec.getIdUtente())) {
                if(distintaPropostaErogazioneVO.getVerificaPosizione()) {
                    return true;
                }
            }

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read distintaPropostaErogazioneVO", e);
            throw new ErroreGestitoException("DaoException while trying to read distintaPropostaErogazioneVO", e);
        } finally {
            LOG.info(prf + " END");
        }
		return false;
    }

    @Override
    public Boolean existsDistinta(Long idDistinta) {
        String prf = "[DistinteDAOImpl::existsDistinta]";
        LOG.info(prf + " BEGIN");
        boolean status;
        try {
            String sql = "SELECT \n" +
                    "COUNT(*) as results \n" +
                    "FROM \n" +
                    "PBANDI_T_DISTINTA distinta \n" +
                    "WHERE \n" +
                    "(\n" +
                    "\tSELECT \n" +
                    "\tentita.nome_entita \n" +
                    "\tFROM PBANDI_C_ENTITA entita  \n" +
                    "\tWHERE \n" +
                    "\tentita.id_entita = distinta.id_entita\n" +
                    ") = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                    "AND distinta.id_distinta = ?";
            status = getJdbcTemplate().query(
                    sql,
                    ps -> ps.setLong(1, idDistinta),
                    (rs, rowNum) -> rs.getLong("results")).get(0)==1;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to count PBANDI_T_DISTINTA", e);
            throw new ErroreGestitoException("DaoException while trying to count PBANDI_T_DISTINTA", e);
        } finally {
            LOG.info(prf + " END");
        }

        return status;
    }

    @Override
    public DettaglioDistintaVO copiaDistinta(Long idDistinta, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::copiaDistinta]";
        LOG.info(prf + " BEGIN");

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        DettaglioDistintaVO dettaglioDistintaVO = null;
        try {
            String table = getJdbcTemplate().queryForObject(
                    "SELECT entita.nome_entita AS nomeEntita " +
                        "FROM PBANDI_C_ENTITA entita " +
                        "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_entita = entita.id_entita " +
                        "WHERE " +
                        "distinta.id_distinta = ?",
                    new Object[]{idDistinta},
                    String.class
            );

            if (Objects.equals(table, "PBANDI_T_PROPOSTA_EROGAZIONE")){
                //Dettaglio distinta
                String query = "SELECT \n" +
                        "bandoLineaIntervento.id_bando AS idBando,\n" +
                        "bandoLineaIntervento.nome_bando_linea AS titoloBando,\n" +
                        "modalitaAgevolazione.id_modalita_agevolazione AS idModalitaAgevolazione, \n" +
                        "modalitaAgevolazione.id_modalita_agevolazione_rif AS idModalitaAgevolazioneRif, \n" +
                        "modalitaAgevolazione.desc_modalita_agevolazione AS descrModalitaAgevolaz, \n" +
                        "distinta.descrizione AS descrizioneDistinta,\n" +
                        "distinta.id_stato_distinta AS idStatoDistinta\n" +
                        "FROM PBANDI_T_DISTINTA distinta \n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = distinta.id_modalita_agevolazione \n" +
                        "JOIN PBANDI_T_DISTINTA_DETT dettaglioDistinta ON dettaglioDistinta.id_distinta = distinta.id_distinta \n" +
                        "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = dettaglioDistinta.id_target \n" +
                        "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                        "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                        "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento ON bandoLineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                        "WHERE \n" +
                        "distinta.id_distinta = ?\n" +
                        "AND \n" +
                        "ROWNUM = 1";
                dettaglioDistintaVO = getJdbcTemplate().queryForObject(
                        query,
                        new Object[]{idDistinta},
                        new DettaglioDistintaVORowMapper()
                );

                //Vecchi estremi bancari pre selezionati
                query = "SELECT DISTINCT\n" +
                        "estremiBancari.id_estremi_bancari AS idEstremiBancari, \n" +
                        "estremiBancari.iban AS iban,\n" +
                        "NULL AS codiceFondoFinpis \n" +
                        "FROM PBANDI_T_DISTINTA distinta\n" +
                        "JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.ID_ESTREMI_BANCARI = distinta.ID_ESTREMI_BANCARI\n" +
                        "WHERE distinta.id_distinta = ?";
                dettaglioDistintaVO.setEstremiBancariVO(getJdbcTemplate().queryForObject(query, new Object[]{idDistinta}, new EstremiBancariVORowMapper()));

                /*Carica estremi bancari per distinta*/
                query = "SELECT \n" +
                        "estremiBancari.id_estremi_bancari AS idEstremiBancari, \n" +
                        "estremiBancari.iban AS iban,\n" +
                        "prbmaeb.codice_fondo_finpis AS codiceFondoFinpis \n" +
                        "FROM \n" +
                        "PBANDI_T_ESTREMI_BANCARI estremiBancari \n" +
                        "JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.id_estremi_bancari = estremiBancari.id_estremi_bancari \n" +
                        "JOIN PBANDI_T_BANDO bando ON bando.id_bando = prbmaeb.id_bando \n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = prbmaeb.id_modalita_agevolazione\n" +
                        "AND \n" +
                        "bando.id_bando = ? \n" +
                        "AND \n" +
                        "modalitaAgevolazione.id_modalita_agevolazione_rif = ?";
                DettaglioDistintaVO finalDettaglioDistintaVO = dettaglioDistintaVO;
                dettaglioDistintaVO.setEstremiBancariList(getJdbcTemplate().query(
                        query,
                        ps ->
                        {
                            ps.setLong(1, finalDettaglioDistintaVO.getIdBando());
                            ps.setLong(2, finalDettaglioDistintaVO.getIdModalitaAgevolazioneRif());
                        },
                        new EstremiBancariVORowMapper()
                ));

                //Proposte di erogazione prese
                List<Long> idPropostePrese;
                idPropostePrese = getJdbcTemplate().query(
                        "SELECT DISTINCT ptdd.ID_TARGET AS idPropostaErogazione\n" +
                                "FROM PBANDI_T_DISTINTA ptd\n" +
                                "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptd.ID_ENTITA \n" +
                                "JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA = ptd.ID_DISTINTA \n" +
                                "JOIN PBANDI_D_STATO_DISTINTA pdsd ON pdsd.ID_STATO_DISTINTA = ptd.ID_STATO_DISTINTA \n" +
                                "WHERE pdsd.DESC_STATO_DISTINTA NOT LIKE 'RESPINTA%'\n" +
                                "AND pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'",
                        (rs, rowNum) -> rs.getLong("idPropostaErogazione")
                );

                /*Carica progetti per distinta*/
                query = "SELECT DISTINCT \n" +
                        "propostaErogazione.id_proposta AS idProposta, \n" +
                        "propostaErogazione.imp_lordo AS importoLordo, \n" +
                        "propostaErogazione.imp_ires AS importoIres, \n" +
                        "propostaErogazione.imp_da_erogare AS importoNetto, \n" +
                        "propostaErogazione.dt_validazione_spesa AS dataEsitoDS, \n" +
                        "progetto.id_progetto AS idProgetto, \n" +
                        "progetto.codice_visualizzato AS codiceVisualizzato, \n" +
                        "progetto.codice_fondo_finpis AS codiceFondoFinpis, \n" +
                        "progetto.dt_concessione AS dataConcessione, \n" +
                        "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazione, \n" +
                        "soggetto.id_soggetto AS idSoggetto, \n" +
                        "soggetto.codice_fiscale_soggetto AS codiceFiscaleSoggetto, \n" +
                        "soggettoProgetto.progr_soggetto_progetto AS progrSoggettoProgetto, \n" +
                        //"estremiBancari.id_estremi_bancari AS idEstremiBancari, \n" +
                        //"estremiBancari.iban AS iban, \n" +
                        "sede.id_sede AS idSede, \n" +
                        "sede.partita_iva AS partitaIva," +
                        "propostaErogazione.flag_finistr as flagFinistr \n" +
                        "FROM PBANDI_T_DISTINTA distinta \n" +
                        "JOIN PBANDI_T_DISTINTA_DETT dettaglioDistinta ON dettaglioDistinta.id_distinta = distinta.id_distinta \n" +
                        "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = dettaglioDistinta.id_target \n" +
                        "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                        "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
                        "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
                        "JOIN PBANDI_R_SOGG_PROGETTO_SEDE soggettoProgettoSede ON soggettoProgettoSede.progr_soggetto_progetto = soggettoProgetto.progr_soggetto_progetto \n" +
                        "JOIN PBANDI_T_SEDE sede ON sede.id_sede = soggettoProgettoSede.id_sede \n" +
                        //"JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.id_estremi_bancari = soggettoProgetto.id_estremi_bancari \n" +
                        "JOIN (SELECT DISTINCT \n" +
                        "\tsoggetto.id_soggetto,\n" +
                        "\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
                        "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                        "\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
                        "\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
                        "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
                        "JOIN (SELECT DISTINCT \n" +
                        "\tsoggetto.id_soggetto,\n" +
                        "\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                        "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                        "\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
                        "\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
                        "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
                        "WHERE \n" +
                        "soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                        "AND \n" +
                        "soggettoProgetto.id_tipo_beneficiario != 4 \n" +
                        "AND \n" +
                        "(\n" +
                        "\tSELECT \n" +
                        "\tentita.nome_entita \n" +
                        "\tFROM PBANDI_C_ENTITA entita  \n" +
                        "\tWHERE \n" +
                        "\tentita.id_entita = distinta.id_entita \n" +
                        ") = 'PBANDI_T_PROPOSTA_EROGAZIONE' \n" +
                        "AND distinta.id_distinta = ? " +
                        "ORDER BY progetto.codice_fondo_finpis, progetto.id_progetto, soggetto.codice_fiscale_soggetto ";
                dettaglioDistintaVO.setDistintaPropostaErogazioneList(getJdbcTemplate().query(
                        query,
                        ps -> ps.setLong(1, idDistinta),
                        (rs, rownum) -> {
                            DistintaPropostaErogazioneVO distintaPropostaErogazioneVO = new DistintaPropostaErogazioneVO();

                            distintaPropostaErogazioneVO.setIdPropostaErogazione(rs.getLong("idProposta"));
                            distintaPropostaErogazioneVO.setImportoLordo(rs.getLong("importoLordo"));
                            distintaPropostaErogazioneVO.setImportoIres(rs.getLong("importoIres"));
                            distintaPropostaErogazioneVO.setImportoNetto(rs.getLong("importoNetto"));
                            distintaPropostaErogazioneVO.setDataEsitoDS(rs.getDate("dataEsitoDS"));

                            distintaPropostaErogazioneVO.setIdProgetto(rs.getLong("idProgetto"));
                            distintaPropostaErogazioneVO.setCodiceVisualizzato(rs.getString("codiceVisualizzato"));
                            distintaPropostaErogazioneVO.setCodiceFondoFinpisProgetto(rs.getString("codiceFondoFinpis"));
                            distintaPropostaErogazioneVO.setDataConcessione(rs.getDate("dataConcessione"));

                            distintaPropostaErogazioneVO.setDenominazione(rs.getString("denominazione"));

                            distintaPropostaErogazioneVO.setIdSoggetto(rs.getLong("idSoggetto"));
                            distintaPropostaErogazioneVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));

                            distintaPropostaErogazioneVO.setProgSoggettoProgetto(rs.getLong("progrSoggettoProgetto"));
                            /*
                            distintaPropostaErogazioneVO.setIdEstremiBancari(rs.getLong("idEstremiBancari"));

                            distintaPropostaErogazioneVO.setIban(rs.getString("iban"));
                             */

                            distintaPropostaErogazioneVO.setIdSede(rs.getLong("idSede"));
                            distintaPropostaErogazioneVO.setPartitaIva(rs.getLong("partitaIva"));

                            distintaPropostaErogazioneVO.setFlagFinistr(rs.getString("flagFinistr"));
                            if(rs.wasNull()){
                                distintaPropostaErogazioneVO.setFlagFinistr("NO");
                            }else {
                                if(distintaPropostaErogazioneVO.getFlagFinistr().equals("S")){
                                    distintaPropostaErogazioneVO.setFlagFinistr("SI");
                                }else{
                                    distintaPropostaErogazioneVO.setFlagFinistr("NO");
                                }
                            }

                            distintaPropostaErogazioneVO.setAbilitata(!idPropostePrese.contains(distintaPropostaErogazioneVO.getIdPropostaErogazione()));

                            return distintaPropostaErogazioneVO;
                        }
                ));
                //VALORIZZA VERIFICA POSIZIONE
                dettaglioDistintaVO.setDistintaPropostaErogazioneList(verificaPosizione(dettaglioDistintaVO.getDistintaPropostaErogazioneList(), userInfoSec.getIdUtente()));

                String sqlGetDocs = "SELECT DISTINCT ptp.CODICE_VISUALIZZATO, ptp.ID_PROGETTO, ptpe.FLAG_FINISTR\n" +
                        "FROM PBANDI_T_DISTINTA ptd \n" +
                        "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptd.ID_ENTITA\n" +
                        "JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA = ptd.ID_DISTINTA \n" +
                        "JOIN PBANDI_T_PROPOSTA_EROGAZIONE ptpe ON ptpe.ID_PROPOSTA = ptdd.ID_TARGET \n" +
                        "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptpe.ID_PROGETTO \n" +
                        "WHERE pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                        "AND ptd.ID_DISTINTA = ?";

                dettaglioDistintaVO.setProgettoAllegatoVOList(getJdbcTemplate().query(sqlGetDocs, (rs, rowNum) -> {
                    ProgettoAllegatoVO progettoAllegatoVO = new ProgettoAllegatoVO();

                    progettoAllegatoVO.setCodiceVisualizzatoProgetto(rs.getString("CODICE_VISUALIZZATO"));
                    progettoAllegatoVO.setIdProgetto(rs.getLong("ID_PROGETTO"));
                    progettoAllegatoVO.setFlagFinistr(rs.getString("FLAG_FINISTR"));
                    if(rs.wasNull()){
                        progettoAllegatoVO.setFlagFinistr("NO");
                    }else{
                        if(progettoAllegatoVO.getFlagFinistr().equals("S")){
                            progettoAllegatoVO.setFlagFinistr("SI");
                        }else {
                            progettoAllegatoVO.setFlagFinistr("NO");
                        }
                    }

                    return progettoAllegatoVO;
                }, idDistinta));
            }
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read DettaglioDistintaVO", e);
            throw new ErroreGestitoException("DaoException while trying to read DettaglioDistintaVO", e);
        } finally {
            LOG.info(prf + " END");
        }

        return dettaglioDistintaVO;
    }

    @Override
    public Boolean modificaDistinta(Long idDistinta, DatiDistintaVO datiDistintaVO, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::modificaDistinta]";
        LOG.info(prf + " BEGIN");
        boolean success;

        try{
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            String sql =
                    "UPDATE PBANDI_T_DISTINTA SET\n" +
                    "ID_ESTREMI_BANCARI = ?,\n" +
                    "DESCRIZIONE = ?,\n" +
                    "ID_UTENTE_AGG = ?, \n" +
                    "DT_AGGIORNAMENTO = CURRENT_DATE\n" +
                    "WHERE ID_DISTINTA = ? ";

            success = getJdbcTemplate().update(
                    sql,
                    datiDistintaVO.getIdEstremiBancari(),
                    datiDistintaVO.getDescrizione(),
                    userInfoSec.getIdUtente(),
                    idDistinta
                    ) != 0;
        }catch (Exception e){
            LOG.error(prf + "Exception while trying to modificaDistinta", e);
            throw new ErroreGestitoException("DaoException while trying to modificaDistinta", e);
        }finally {
            LOG.info(prf + " END");
        }
        return success;
    }

    @Override
    public DettaglioDistintaVO getNuovaDistinta(Long idBando, Long idModalitaAgevolazione) {
        String prf = "[DistinteDAOImpl::getNuovaDistinta]";
        LOG.info(prf + " BEGIN");
        DettaglioDistintaVO dettaglioDistintaVO = new DettaglioDistintaVO();
        try{
            String query;
            dettaglioDistintaVO.setIdBando(idBando);
            dettaglioDistintaVO.setTitoloBando(getJdbcTemplate().query(
                    "SELECT bando.titolo_bando as titoloBando \n" +
                            "FROM \n" +
                            "PBANDI_T_BANDO bando \n" +
                            "WHERE bando.id_bando = ?",
                    ps -> ps.setLong(1, idBando),
                    rs -> {
                        rs.next();
                        return rs.getString("titoloBando");
                    }
            ));
            dettaglioDistintaVO.setIdModalitaAgevolazione(getJdbcTemplate().query("SELECT pdma.ID_MODALITA_AGEVOLAZIONE \n" +
                    "FROM PBANDI_R_BANDO_MODALITA_AGEVOL prbma \n" +
                    "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE pdma ON pdma.ID_MODALITA_AGEVOLAZIONE = prbma.ID_MODALITA_AGEVOLAZIONE \n" +
                    "WHERE prbma.ID_BANDO = ?\n" +
                    "AND pdma.ID_MODALITA_AGEVOLAZIONE_RIF = ?",
                    ps -> {
                        ps.setLong(1, idBando);
                        ps.setLong(2, idModalitaAgevolazione);
                    },
                    rs -> {
                        rs.next();
                        return rs.getLong("ID_MODALITA_AGEVOLAZIONE");
                    }));
            dettaglioDistintaVO.setIdModalitaAgevolazioneRif(idModalitaAgevolazione);
            dettaglioDistintaVO.setDescModalitaAgevolazione(getJdbcTemplate().query(
                    "SELECT modalitaAgevolazione.desc_modalita_agevolazione as descModalitaAgevolazione \n" +
                            "FROM \n" +
                            "PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione \n" +
                            "WHERE modalitaAgevolazione.id_modalita_agevolazione = ?",
                    ps -> ps.setLong(1, idModalitaAgevolazione),
                    rs -> {
                        rs.next();
                        return rs.getString("descModalitaAgevolazione");
                    }
            ));

            /*Estremi bancari*/
            query = "SELECT \n" +
                    "estremiBancari.id_estremi_bancari AS idEstremiBancari, \n" +
                    "estremiBancari.iban AS iban,\n" +
                    "prbmaeb.codice_fondo_finpis AS codiceFondoFinpis \n" +
                    "FROM PBANDI_T_ESTREMI_BANCARI estremiBancari \n" +
                    "JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.id_estremi_bancari = estremiBancari.id_estremi_bancari \n" +
                    "JOIN PBANDI_T_BANDO bando ON bando.id_bando = prbmaeb.id_bando \n" +
                    "WHERE bando.id_bando = ? \n" +
                    "AND prbmaeb.id_modalita_agevolazione = ?";
            dettaglioDistintaVO.setEstremiBancariList(getJdbcTemplate().query(
                    query,
                    ps ->
                    {
                        ps.setLong(1, idBando);
                        ps.setLong(2, dettaglioDistintaVO.getIdModalitaAgevolazione());
                    },
                    new EstremiBancariVORowMapper()
            ));
        } catch (RecordNotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read DettaglioDistintaVO", e);
            throw new ErroreGestitoException("DaoException while trying to read DettaglioDistintaVO", e);
        } finally {
            LOG.info(prf + " END");
        }

        return dettaglioDistintaVO;
    }

    @Override
    public List<DistintaPropostaErogazioneVO> getProposteErogazione(FiltroProposteErogazioneDistVO filtroProposteErogazioneDistVO) {
        String prf = "[DistinteDAOImpl::getProposteErogazione]";
        LOG.info(prf + " BEGIN");
        List<DistintaPropostaErogazioneVO> distintaPropostaErogazioneVOList;
        try {
            //Proposte di erogazione prese
            List<Long> idPropostePrese;
            idPropostePrese = getJdbcTemplate().query(
                    "SELECT DISTINCT ptdd.ID_TARGET AS idPropostaErogazione\n" +
                            "FROM PBANDI_T_DISTINTA ptd\n" +
                            "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptd.ID_ENTITA \n" +
                            "JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA = ptd.ID_DISTINTA \n" +
                            "JOIN PBANDI_D_STATO_DISTINTA pdsd ON pdsd.ID_STATO_DISTINTA = ptd.ID_STATO_DISTINTA \n" +
                            "WHERE pdsd.DESC_STATO_DISTINTA NOT LIKE 'RESPINTA%'\n" +
                            "AND pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'",
                    (rs, rowNum) -> rs.getLong("idPropostaErogazione")
            );

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT DISTINCT \n" +
                    "propostaErogazione.id_proposta AS idProposta, \n" +
                    "propostaErogazione.flag_ctrl_pre_erogazione AS verificaPosizione, \n" +
                    "propostaErogazione.imp_lordo AS importoLordo, \n" +
                    "propostaErogazione.imp_ires AS importoIres, \n" +
                    "propostaErogazione.imp_da_erogare AS importoNetto, \n" +
                    "propostaErogazione.dt_validazione_spesa AS dataEsitoDS, \n" +
                    "progetto.id_progetto AS idProgetto, \n" +
                    "progetto.codice_visualizzato AS codiceVisualizzato, \n" +
                    "prbmaeb.codice_fondo_finpis AS codiceFondoFinpis, \n" +
                    "progetto.dt_concessione AS dataConcessione, \n" +
                    "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazione, \n" +
                    "soggetto.id_soggetto AS idSoggetto, \n" +
                    "soggetto.codice_fiscale_soggetto AS codiceFiscaleSoggetto, \n" +
                    "soggettoProgetto.progr_soggetto_progetto AS progrSoggettoProgetto, \n" +
                    "sede.id_sede AS idSede, \n" +
                    "sede.partita_iva AS partitaIva, \n" +
                    "propostaErogazione.flag_finistr as flagFinistr \n" +
                    "FROM PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione\n" +
                    "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgev ON modAgev.ID_MODALITA_AGEVOLAZIONE = propostaErogazione.ID_MODALITA_AGEVOLAZIONE \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT lineaIntervento ON lineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                    "JOIN PBANDI_T_BANDO bando ON bando.id_bando = lineaIntervento.id_bando \n" +
                    "JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.ID_BANDO = bando.ID_BANDO \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto  \n" +
                    "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
                    "JOIN PBANDI_R_SOGG_PROGETTO_SEDE soggettoProgettoSede ON soggettoProgettoSede.progr_soggetto_progetto = soggettoProgetto.progr_soggetto_progetto \n" +
                    "JOIN PBANDI_T_SEDE sede ON sede.id_sede = soggettoProgettoSede.id_sede \n" +
                    "JOIN (SELECT DISTINCT \n" +
                    "\tsoggetto.id_soggetto,\n" +
                    "\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
                    "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                    "\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
                    "\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
                    "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
                    "JOIN (SELECT DISTINCT \n" +
                    "\tsoggetto.id_soggetto,\n" +
                    "\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                    "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                    "\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
                    "\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
                    "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
                    "WHERE soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                    "AND soggettoProgetto.id_tipo_beneficiario != 4 \n" +
                    "AND bando.id_bando = ?\n" +
                    "AND modAgev.id_modalita_agevolazione_rif = ?\n");

            if(filtroProposteErogazioneDistVO.getDtValidazioneSpesaFrom() != null &&
                    filtroProposteErogazioneDistVO.getDtValidazioneSpesaTo() != null){
                sql.append("AND propostaErogazione.dt_validazione_spesa >= ? AND propostaErogazione.dt_validazione_spesa <= ? + 1 \n");
            }
            if(filtroProposteErogazioneDistVO.getDtConcessioneFrom() != null &&
                    filtroProposteErogazioneDistVO.getDtConcessioneTo() != null){
                sql.append("AND progetto.dt_concessione >= ? AND progetto.dt_concessione <= ? + 1 \n");
            }
            if(filtroProposteErogazioneDistVO.getDenominazione()!=null){
                sql.append("AND (CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) = ? \n");
            }
            if(filtroProposteErogazioneDistVO.getCodiceProgetto()!=null){
                sql.append("AND progetto.codice_visualizzato = ? \n");
            }
            if(filtroProposteErogazioneDistVO.getCodiceFondoFinpis()!=null){
                sql.append("AND ltrim(prbmaeb.codice_fondo_finpis,'0') = ? \n");
            } 
            if(filtroProposteErogazioneDistVO.getIdModalitaAgevolazione() == 10 && filtroProposteErogazioneDistVO.getFlagFinistr()!= null) {
                if(filtroProposteErogazioneDistVO.getFlagFinistr().trim().equals("SI")) {
                    sql.append("AND propostaErogazione.FLAG_FINISTR IS NOT NULL\n");
                }else {
                    sql.append("AND propostaErogazione.FLAG_FINISTR IS NULL\n");
                }
            }

            sql.append("ORDER BY prbmaeb.codice_fondo_finpis, progetto.id_progetto, soggetto.codice_fiscale_soggetto");

            LOG.debug(prf + " Query: " + sql);

            distintaPropostaErogazioneVOList = getJdbcTemplate().query(
                    sql.toString(),
                    ps -> {
                        int i = 1;
                        ps.setLong(i, filtroProposteErogazioneDistVO.getIdBando());
                        ps.setLong(++i, filtroProposteErogazioneDistVO.getIdModalitaAgevolazione());
                        if(filtroProposteErogazioneDistVO.getDtValidazioneSpesaFrom() != null &&
                        filtroProposteErogazioneDistVO.getDtValidazioneSpesaTo() != null){
                            ps.setDate(++i, filtroProposteErogazioneDistVO.getDtValidazioneSpesaFrom());
                            ps.setDate(++i, filtroProposteErogazioneDistVO.getDtValidazioneSpesaTo());
                        }
                        if(filtroProposteErogazioneDistVO.getDtConcessioneFrom() != null &&
                        filtroProposteErogazioneDistVO.getDtConcessioneTo() != null){
                            ps.setDate(++i, filtroProposteErogazioneDistVO.getDtConcessioneFrom());
                            ps.setDate(++i, filtroProposteErogazioneDistVO.getDtConcessioneTo());
                        }
                        if(filtroProposteErogazioneDistVO.getDenominazione()!=null){
                            ps.setString(++i, filtroProposteErogazioneDistVO.getDenominazione());
                        }
                        if(filtroProposteErogazioneDistVO.getCodiceProgetto()!=null){
                            ps.setString(++i, filtroProposteErogazioneDistVO.getCodiceProgetto());
                        }
                        if(filtroProposteErogazioneDistVO.getCodiceFondoFinpis()!=null){
                            ps.setString(++i, filtroProposteErogazioneDistVO.getCodiceFondoFinpis());
                        }
                    },
                    (rs, rowNum) -> {
                        DistintaPropostaErogazioneVO distintaPropostaErogazioneVO = new DistintaPropostaErogazioneVO();

                        distintaPropostaErogazioneVO.setIdPropostaErogazione(rs.getLong("idProposta"));

                        distintaPropostaErogazioneVO.setVerificaPosizione(rs.getString("verificaPosizione").equals("S"));

                        distintaPropostaErogazioneVO.setImportoLordo(rs.getLong("importoLordo"));
                        distintaPropostaErogazioneVO.setImportoIres(rs.getLong("importoIres"));
                        distintaPropostaErogazioneVO.setImportoNetto(rs.getLong("importoNetto"));
                        distintaPropostaErogazioneVO.setDataEsitoDS(rs.getDate("dataEsitoDS"));

                        distintaPropostaErogazioneVO.setIdProgetto(rs.getLong("idProgetto"));
                        distintaPropostaErogazioneVO.setCodiceVisualizzato(rs.getString("codiceVisualizzato"));
                        distintaPropostaErogazioneVO.setCodiceFondoFinpisProgetto(rs.getString("codiceFondoFinpis"));
                        distintaPropostaErogazioneVO.setDataConcessione(rs.getDate("dataConcessione"));

                        distintaPropostaErogazioneVO.setDenominazione(rs.getString("denominazione"));

                        distintaPropostaErogazioneVO.setIdSoggetto(rs.getLong("idSoggetto"));
                        distintaPropostaErogazioneVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));

                        distintaPropostaErogazioneVO.setProgSoggettoProgetto(rs.getLong("progrSoggettoProgetto"));
                        /*
                        distintaPropostaErogazioneVO.setIdEstremiBancari(rs.getLong("idEstremiBancari"));

                        distintaPropostaErogazioneVO.setIban(rs.getString("iban"));
                         */

                        distintaPropostaErogazioneVO.setIdSede(rs.getLong("idSede"));
                        distintaPropostaErogazioneVO.setPartitaIva(rs.getLong("partitaIva"));

                        distintaPropostaErogazioneVO.setFlagFinistr(rs.getString("flagFinistr"));
                        if(rs.wasNull()){
                            distintaPropostaErogazioneVO.setFlagFinistr("NO");
                        }else {
                            if(distintaPropostaErogazioneVO.getFlagFinistr().equals("S")){
                                distintaPropostaErogazioneVO.setFlagFinistr("SI");
                            }else {
                                distintaPropostaErogazioneVO.setFlagFinistr("NO");
                            }
                        }

                        distintaPropostaErogazioneVO.setAbilitata(!idPropostePrese.contains(distintaPropostaErogazioneVO.getIdPropostaErogazione()));

                        return distintaPropostaErogazioneVO;
                    }
            );
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read DistintaPropostaErogazioneVO", e);
            throw new ErroreGestitoException("DaoException while trying to read DistintaPropostaErogazioneVO", e);
        } finally {
            LOG.info(prf + " END");
        }

        return distintaPropostaErogazioneVOList;
    }

    @Override
    public DettaglioDistintaVO getDettaglioDistinta(Long idDistinta, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::getDettaglioDistinta]";
        LOG.info(prf + " BEGIN");

        UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

        DettaglioDistintaVO dettaglioDistintaVO = null;

        try {
            String table = getJdbcTemplate().queryForObject(
                    "SELECT entita.nome_entita AS nomeEntita " +
                        "FROM PBANDI_C_ENTITA entita " +
                        "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_entita = entita.id_entita " +
                        "WHERE " +
                        "distinta.id_distinta = ?",
                    new Object[]{idDistinta},
                    String.class
            );

            //Dettaglio distinta
            if (Objects.equals(table, "PBANDI_T_PROPOSTA_EROGAZIONE")){
                String query = "SELECT \n" +
                        "bandoLineaIntervento.id_bando AS idBando,\n" +
                        "bandoLineaIntervento.nome_bando_linea AS titoloBando,\n" +
                        "modalitaAgevolazione.id_modalita_agevolazione AS idModalitaAgevolazione, \n" +
                        "modalitaAgevolazione.id_modalita_agevolazione_rif AS idModalitaAgevolazioneRif, \n" +
                        "modAgevVisual.desc_modalita_agevolazione AS descrModalitaAgevolaz, \n" +
                        "distinta.descrizione AS descrizioneDistinta,\n" +
                        "distinta.id_stato_distinta AS idStatoDistinta\n" +
                        "FROM PBANDI_T_DISTINTA distinta \n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = distinta.id_modalita_agevolazione\n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modAgevVisual ON modAgevVisual.id_modalita_agevolazione = modalitaAgevolazione.id_modalita_agevolazione_rif\n" +
                        "JOIN PBANDI_T_DISTINTA_DETT dettaglioDistinta ON dettaglioDistinta.id_distinta = distinta.id_distinta \n" +
                        "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = dettaglioDistinta.id_target \n" +
                        "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                        "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                        "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento ON bandoLineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                        "WHERE distinta.id_distinta = ?\n" +
                        "AND ROWNUM = 1";
                dettaglioDistintaVO = getJdbcTemplate().queryForObject(
                        query,
                        new Object[]{idDistinta},
                        new DettaglioDistintaVORowMapper()
                );
                dettaglioDistintaVO.setIdDistinta(idDistinta);

                //Vecchi estremi bancari pre selezionati
                query = "SELECT DISTINCT\n" +
                        "estremiBancari.id_estremi_bancari AS idEstremiBancari, \n" +
                        "estremiBancari.iban AS iban,\n" +
                        "NULL AS codiceFondoFinpis \n" +
                        "FROM PBANDI_T_DISTINTA distinta\n" +
                        "JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.ID_ESTREMI_BANCARI = distinta.ID_ESTREMI_BANCARI\n" +
                        "WHERE distinta.id_distinta = ?";
                try {
                    dettaglioDistintaVO.setEstremiBancariVO(getJdbcTemplate().queryForObject(query, new Object[]{idDistinta}, new EstremiBancariVORowMapper()));
                }catch (IncorrectResultSizeDataAccessException ignored){}

                /*Carica estremi bancari per distinta*/
                query = "SELECT \n" +
                        "estremiBancari.id_estremi_bancari AS idEstremiBancari, \n" +
                        "estremiBancari.iban AS iban,\n" +
                        "prbmaeb.codice_fondo_finpis AS codiceFondoFinpis \n" +
                        "FROM \n" +
                        "PBANDI_T_ESTREMI_BANCARI estremiBancari \n" +
                        "JOIN PBANDI_R_BANDO_MOD_AG_ESTR_BAN prbmaeb ON prbmaeb.id_estremi_bancari = estremiBancari.id_estremi_bancari \n" +
                        "JOIN PBANDI_T_BANDO bando ON bando.id_bando = prbmaeb.id_bando \n" +
                        "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = prbmaeb.id_modalita_agevolazione\n" +
                        "AND \n" +
                        "bando.id_bando = ? \n" +
                        "AND \n" +
                        "modalitaAgevolazione.id_modalita_agevolazione_rif = ?";
                DettaglioDistintaVO finalDettaglioDistintaVO = dettaglioDistintaVO;
                dettaglioDistintaVO.setEstremiBancariList(getJdbcTemplate().query(
                        query,
                        ps ->
                        {
                            ps.setLong(1, finalDettaglioDistintaVO.getIdBando());
                            ps.setLong(2, finalDettaglioDistintaVO.getIdModalitaAgevolazioneRif());
                        },
                        new EstremiBancariVORowMapper()
                ));

                //Proposte di erogazione prese
                List<Long> idPropostePrese;
                idPropostePrese = getJdbcTemplate().query(
                        "SELECT DISTINCT ptdd.ID_TARGET AS idPropostaErogazione\n" +
                                "FROM PBANDI_T_DISTINTA ptd\n" +
                                "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptd.ID_ENTITA \n" +
                                "JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA = ptd.ID_DISTINTA \n" +
                                "JOIN PBANDI_D_STATO_DISTINTA pdsd ON pdsd.ID_STATO_DISTINTA = ptd.ID_STATO_DISTINTA \n" +
                                "WHERE pdsd.DESC_STATO_DISTINTA NOT LIKE 'RESPINTA%'\n" +
                                "AND pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'",
                        (rs, rowNum) -> rs.getLong("idPropostaErogazione")
                );

                /*Carica progetti per distinta*/
                query = "SELECT DISTINCT \n" +
                        "propostaErogazione.id_proposta AS idProposta, \n" +
                        "propostaErogazione.imp_lordo AS importoLordo, \n" +
                        "propostaErogazione.imp_ires AS importoIres, \n" +
                        "propostaErogazione.imp_da_erogare AS importoNetto, \n" +
                        "propostaErogazione.dt_validazione_spesa AS dataEsitoDS, \n" +
                        "progetto.id_progetto AS idProgetto, \n" +
                        "progetto.codice_visualizzato AS codiceVisualizzato, \n" +
                        "progetto.codice_fondo_finpis AS codiceFondoFinpis, \n" +
                        "progetto.dt_concessione AS dataConcessione, \n" +
                        "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazione, \n" +
                        "soggetto.id_soggetto AS idSoggetto, \n" +
                        "soggetto.codice_fiscale_soggetto AS codiceFiscaleSoggetto, \n" +
                        "soggettoProgetto.progr_soggetto_progetto AS progrSoggettoProgetto, \n" +
                        //"estremiBancari.id_estremi_bancari AS idEstremiBancari, \n" +
                        //"estremiBancari.iban AS iban, \n" +
                        "sede.id_sede AS idSede, \n" +
                        "sede.partita_iva AS partitaIva, \n" +
                        "propostaErogazione.flag_finistr as flagFinistr \n" +
                        "FROM PBANDI_T_DISTINTA distinta \n" +
                        "JOIN PBANDI_T_DISTINTA_DETT dettaglioDistinta ON dettaglioDistinta.id_distinta = distinta.id_distinta \n" +
                        "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = dettaglioDistinta.id_target \n" +
                        "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                        "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
                        "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
                        "JOIN PBANDI_R_SOGG_PROGETTO_SEDE soggettoProgettoSede ON soggettoProgettoSede.progr_soggetto_progetto = soggettoProgetto.progr_soggetto_progetto \n" +
                        "JOIN PBANDI_T_SEDE sede ON sede.id_sede = soggettoProgettoSede.id_sede \n" +
                        //"JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.id_estremi_bancari = soggettoProgetto.id_estremi_bancari \n" +
                        "JOIN (SELECT DISTINCT \n" +
                        "\tsoggetto.id_soggetto,\n" +
                        "\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
                        "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                        "\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
                        "\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
                        "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
                        "JOIN (SELECT DISTINCT \n" +
                        "\tsoggetto.id_soggetto,\n" +
                        "\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                        "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                        "\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
                        "\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
                        "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
                        "WHERE \n" +
                        "soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                        "AND \n" +
                        "soggettoProgetto.id_tipo_beneficiario != 4 \n" +
                        "AND \n" +
                        "(\n" +
                        "\tSELECT \n" +
                        "\tentita.nome_entita \n" +
                        "\tFROM PBANDI_C_ENTITA entita  \n" +
                        "\tWHERE \n" +
                        "\tentita.id_entita = distinta.id_entita \n" +
                        ") = 'PBANDI_T_PROPOSTA_EROGAZIONE' \n" +
                        "AND distinta.id_distinta = ? " +
                        "ORDER BY progetto.codice_fondo_finpis, progetto.id_progetto, soggetto.codice_fiscale_soggetto ";
                dettaglioDistintaVO.setDistintaPropostaErogazioneList(getJdbcTemplate().query(
                        query,
                        ps -> ps.setLong(1, idDistinta),
                        (rs, rownum) -> {
                            DistintaPropostaErogazioneVO distintaPropostaErogazioneVO = new DistintaPropostaErogazioneVO();

                            distintaPropostaErogazioneVO.setIdPropostaErogazione(rs.getLong("idProposta"));
                            distintaPropostaErogazioneVO.setImportoLordo(rs.getLong("importoLordo"));
                            distintaPropostaErogazioneVO.setImportoIres(rs.getLong("importoIres"));
                            distintaPropostaErogazioneVO.setImportoNetto(rs.getLong("importoNetto"));
                            distintaPropostaErogazioneVO.setDataEsitoDS(rs.getDate("dataEsitoDS"));

                            distintaPropostaErogazioneVO.setIdProgetto(rs.getLong("idProgetto"));
                            distintaPropostaErogazioneVO.setCodiceVisualizzato(rs.getString("codiceVisualizzato"));
                            distintaPropostaErogazioneVO.setCodiceFondoFinpisProgetto(rs.getString("codiceFondoFinpis"));
                            distintaPropostaErogazioneVO.setDataConcessione(rs.getDate("dataConcessione"));

                            distintaPropostaErogazioneVO.setDenominazione(rs.getString("denominazione"));

                            distintaPropostaErogazioneVO.setIdSoggetto(rs.getLong("idSoggetto"));
                            distintaPropostaErogazioneVO.setCodiceFiscaleSoggetto(rs.getString("codiceFiscaleSoggetto"));

                            distintaPropostaErogazioneVO.setProgSoggettoProgetto(rs.getLong("progrSoggettoProgetto"));
                            /*
                            distintaPropostaErogazioneVO.setIdEstremiBancari(rs.getLong("idEstremiBancari"));

                            distintaPropostaErogazioneVO.setIban(rs.getString("iban"));
                             */

                            distintaPropostaErogazioneVO.setIdSede(rs.getLong("idSede"));
                            distintaPropostaErogazioneVO.setPartitaIva(rs.getLong("partitaIva"));

                            distintaPropostaErogazioneVO.setFlagFinistr(rs.getString("flagFinistr"));
                            if(rs.wasNull()){
                                distintaPropostaErogazioneVO.setFlagFinistr("NO");
                            }else{
                                if(distintaPropostaErogazioneVO.getFlagFinistr().equals("S")){
                                    distintaPropostaErogazioneVO.setFlagFinistr("SI");
                                }else{
                                    distintaPropostaErogazioneVO.setFlagFinistr("NO");
                                }
                            }

                            distintaPropostaErogazioneVO.setAbilitata(!idPropostePrese.contains(distintaPropostaErogazioneVO.getIdPropostaErogazione()));

                            return distintaPropostaErogazioneVO;
                        }
                ));
                //VALORIZZA VERIFICA POSIZIONE
                dettaglioDistintaVO.setDistintaPropostaErogazioneList(verificaPosizione(dettaglioDistintaVO.getDistintaPropostaErogazioneList(), userInfoSec.getIdUtente()));

                String sqlGetDocs = "SELECT DISTINCT ptp.CODICE_VISUALIZZATO, ptp.ID_PROGETTO, ptpe.FLAG_FINISTR, \n" +
                        "lettera.ID_DOCUMENTO_INDEX idDocIndexLettera,\n" +
                        "lettera.NOME_FILE nomeFileLettera,\n" +
                        "lettera.FLAG_VISIBILE_BEN visibilitaLettera,\n" +
                        "checklist.ID_DOCUMENTO_INDEX idDocIndexChecklist,\n" +
                        "checklist.NOME_FILE nomeFileChecklist,\n" +
                        "checklist.FLAG_VISIBILE_BEN visibilitaChecklist,\n" +
                        "report.ID_DOCUMENTO_INDEX idDocIndexReport,\n" +
                        "report.NOME_FILE nomeFileReport,\n" +
                        "report.FLAG_VISIBILE_BEN visibilitaReport\n" +
                        "FROM PBANDI_T_DISTINTA ptd \n" +
                        "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptd.ID_ENTITA\n" +
                        "JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA = ptd.ID_DISTINTA \n" +
                        "JOIN PBANDI_T_PROPOSTA_EROGAZIONE ptpe ON ptpe.ID_PROPOSTA = ptdd.ID_TARGET \n" +
                        "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptpe.ID_PROGETTO \n" +
                        "JOIN PBANDI_C_ENTITA pce2 ON pce2.NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
                        "LEFT JOIN PBANDI_T_DOCUMENTO_INDEX lettera ON lettera.ID_TARGET = ptd.ID_DISTINTA\n" +
                        "\tAND lettera.ID_ENTITA = pce2.ID_ENTITA AND lettera.ID_PROGETTO = ptp.ID_PROGETTO AND lettera.ID_TIPO_DOCUMENTO_INDEX = 42\n" +
                        "LEFT JOIN PBANDI_T_DOCUMENTO_INDEX report ON report.ID_TARGET = ptd.ID_DISTINTA\n" +
                        "\tAND report.ID_ENTITA = pce2.ID_ENTITA AND report.ID_PROGETTO = ptp.ID_PROGETTO AND report.ID_TIPO_DOCUMENTO_INDEX = 63\n" +
                        "LEFT JOIN PBANDI_T_DOCUMENTO_INDEX checklist ON checklist.ID_TARGET = ptd.ID_DISTINTA\n" +
                        "\tAND checklist.ID_ENTITA = pce2.ID_ENTITA AND checklist.ID_PROGETTO = ptp.ID_PROGETTO AND checklist.ID_TIPO_DOCUMENTO_INDEX = 33\n" +
                        "WHERE pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                        "AND ptd.ID_DISTINTA = ?";

                dettaglioDistintaVO.setProgettoAllegatoVOList(getJdbcTemplate().query(sqlGetDocs, (rs, rowNum) -> {
                    ProgettoAllegatoVO progettoAllegatoVO = new ProgettoAllegatoVO();

                    progettoAllegatoVO.setCodiceVisualizzatoProgetto(rs.getString("CODICE_VISUALIZZATO"));
                    progettoAllegatoVO.setIdProgetto(rs.getLong("ID_PROGETTO"));
                    progettoAllegatoVO.setFlagFinistr(rs.getString("FLAG_FINISTR"));
                    if(rs.wasNull()){
                        progettoAllegatoVO.setFlagFinistr("NO");
                    }else{
                        if(progettoAllegatoVO.getFlagFinistr().equals("S")){
                            progettoAllegatoVO.setFlagFinistr("SI");
                        }else{
                            progettoAllegatoVO.setFlagFinistr("NO");
                        }
                    }

                    progettoAllegatoVO.setVisibilitaLettera(Objects.equals(rs.getString("visibilitaLettera"), "S"));
                    progettoAllegatoVO.setVisibilitaChecklist(Objects.equals(rs.getString("visibilitaChecklist"), "S"));
                    progettoAllegatoVO.setVisibilitaReport(Objects.equals(rs.getString("visibilitaReport"), "S"));

                    //lettera
                    rs.getLong("idDocIndexLettera");
                    if(!rs.wasNull()){
                        AllegatoVO allegatoVO = new AllegatoVO();
                        allegatoVO.setIdDocumentoIndex(rs.getLong("idDocIndexLettera"));
                        allegatoVO.setName(rs.getString("nomeFileLettera"));
                        progettoAllegatoVO.setLetteraAccompagnatoria(allegatoVO);
                    }
                    //checklist
                    rs.getLong("idDocIndexChecklist");
                    if(!rs.wasNull()){
                        AllegatoVO allegatoVO = new AllegatoVO();
                        allegatoVO.setIdDocumentoIndex(rs.getLong("idDocIndexChecklist"));
                        allegatoVO.setName(rs.getString("nomeFileChecklist"));
                        progettoAllegatoVO.setChecklistInterna(allegatoVO);
                    }
                    //report
                    rs.getLong("idDocIndexReport");
                    if(!rs.wasNull()){
                        AllegatoVO allegatoVO = new AllegatoVO();
                        allegatoVO.setIdDocumentoIndex(rs.getLong("idDocIndexReport"));
                        allegatoVO.setName(rs.getString("nomeFileReport"));
                        progettoAllegatoVO.setReportValidazione(allegatoVO);
                    }

                    return progettoAllegatoVO;
                }, idDistinta));
            }
        } catch (RecordNotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read DettaglioDistintaVO", e);
            throw new ErroreGestitoException("DaoException while trying to read DettaglioDistintaVO", e);
        } finally {
            LOG.info(prf + " END");
        }

        return dettaglioDistintaVO;
    }

    @Override
    public Long salvaInBozza(DatiDistintaVO datiDistintaVO, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::salvaInBozza]";
        LOG.info(prf + " BEGIN");
        boolean success;
        Long idDistinta;

        try{
            if(datiDistintaVO.getListaIdProposteErogazione().isEmpty()){
                throw new ErroreGestitoException("Non puoi salvare una distinta se non selezioni almeno una proposta di erogazione!");
            }
            if(datiDistintaVO.getDescrizione() == null || datiDistintaVO.getDescrizione().trim().equals("")){
                throw new ErroreGestitoException("Non puoi salvare una distinta senza la descrizione!");
            }

            String getIdDistinta ="select SEQ_PBANDI_T_DISTINTA.nextval from dual";
            idDistinta = getJdbcTemplate().queryForObject(getIdDistinta, Long.class);
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            String sql = "INSERT INTO PBANDI_T_DISTINTA \n" +
                    "(ID_DISTINTA, ID_ENTITA, ID_TIPO_DISTINTA, ID_MODALITA_AGEVOLAZIONE, DESCRIZIONE, ID_ESTREMI_BANCARI, ID_STATO_DISTINTA, DT_INIZIO_VALIDITA, ID_UTENTE_INS, DT_INSERIMENTO)\n" +
                    "VALUES\n" +
                    "(?, (\n" +
                    "\tSELECT id_entita\n" +
                    "\tFROM PBANDI_C_ENTITA\n" +
                    "\tWHERE nome_entita ='PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                    "),?,?,?,?,7,CURRENT_DATE,?, CURRENT_DATE)";


            success = getJdbcTemplate().execute(
                    sql,
                    (PreparedStatementCallback<Boolean>) ps -> {
                        ps.setLong(1, idDistinta);
                        ps.setLong(2, datiDistintaVO.getIdTipoDistinta());
                        ps.setLong(3, datiDistintaVO.getIdModalitaAgevolazione());
                        ps.setString(4, datiDistintaVO.getDescrizione());
                        ps.setLong(5, datiDistintaVO.getIdEstremiBancari());
                        ps.setLong(6, userInfoSec.getIdUtente());
                        return ps.execute();
                    }) != null;

            /*
            IL DOCUMENTO NON SERVE PIU
            sql = "UPDATE PBANDI_T_DOCUMENTO_INDEX \n" +
                    "SET ID_TARGET = ?\n" +
                    "WHERE ID_DOCUMENTO_INDEX = ?";
            result = result && getJdbcTemplate().execute(
                    sql,
                    (PreparedStatementCallback<Boolean>) ps -> {
                        ps.setBigDecimal(1, idDistinta);
                        ps.setLong(2, idAllegato);
                        return ps.execute();
                    });
             */

            for(Long idPropostaErogazione : datiDistintaVO.getListaIdProposteErogazione()){
                String getIdDistintaDett ="select SEQ_PBANDI_T_DISTINTA_DETT.nextval from dual";
                String getRigaDistinta = "SELECT COUNT(1) + 1 FROM PBANDI_T_DISTINTA_DETT ptdd WHERE ptdd.ID_DISTINTA = ?";
                BigDecimal idDistintaDett = getJdbcTemplate().queryForObject(getIdDistintaDett, BigDecimal.class);
                Long rigaDistinta = getJdbcTemplate().queryForObject(getRigaDistinta, Long.class, idDistinta);
                sql = "INSERT INTO PBANDI_T_DISTINTA_DETT\n" +
                        "(ID_DISTINTA_DETT, ID_DISTINTA, RIGA_DISTINTA, ID_TARGET, DT_INIZIO_VALIDITA, ID_UTENTE_INS, DT_INSERIMENTO)\n" +
                        "VALUES\n" +
                        "(?,?,?,?,CURRENT_DATE,?, CURRENT_DATE)";
                success = success && getJdbcTemplate().execute(
                        sql,
                        (PreparedStatementCallback<Boolean>) ps -> {
                            ps.setBigDecimal(1, idDistintaDett);
                            ps.setLong(2, idDistinta);
                            ps.setLong(3, rigaDistinta);
                            ps.setLong(4, idPropostaErogazione);
                            ps.setLong(5, userInfoSec.getIdUtente());
                            return ps.execute();
                        }) != null;
            }

        }catch (Exception e){
            LOG.error(prf + "Exception while trying to save Distinta and DistintaDett", e);
            throw new ErroreGestitoException("DaoException while trying to save Distinta and DistintaDett", e);
        }finally {
            LOG.info(prf + " END");
        }
        return (success ? idDistinta : null);
    }

    @Override
    public Boolean isAbilitatoAvviaIter(HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::isAbilitatoAvviaIter]";
        LOG.info(prf + " BEGIN");
        boolean result;

        try{
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            String query = "SELECT COUNT(1)\n" +
                    "FROM PBANDI_R_TIPO_STATO_ITER tipoStatoIter\n" +
                    "JOIN PBANDI_D_INCARICO incarico ON incarico.id_incarico = tipoStatoIter.id_incarico \n" +
                    "JOIN PBANDI_R_INCARICO_SOGGETTO soggetto ON soggetto.id_incarico = incarico.id_incarico \n" +
                    "WHERE tipoStatoIter.id_tipo_iter = 13\n" +
                    "AND tipoStatoIter.ordine = 1\n" +
                    "AND soggetto.id_soggetto = ?";
            result = getJdbcTemplate().queryForObject(query, new Object[]{userInfoSec.getIdSoggetto()}, Long.class) > 0;

        }catch (Exception e){
            LOG.error(prf + "Exception in isAbilitatoAvviaIter", e);
            throw new ErroreGestitoException("Exception in isAbilitatoAvviaIter", e);
        }finally {
            LOG.info(prf + " END");
        }
        return result;
    }

    private List<DistintaPropostaErogazioneVO> verificaPosizione(List<DistintaPropostaErogazioneVO> distintaPropostaErogazioneVOList, Long idUtente) {
        String prf = "[DistinteDAOImpl::verificaPosizione]";
        LOG.info(prf + " BEGIN");

        for (DistintaPropostaErogazioneVO distintaPropostaErogazioneVO : distintaPropostaErogazioneVOList) {
            String sql =
                    "SELECT IMP_LORDO \n" +
                            "FROM PBANDI_T_PROPOSTA_EROGAZIONE ptpe \n" +
                            "WHERE ptpe.ID_PROPOSTA = ?";
            BigDecimal importoAgevolato = getJdbcTemplate().queryForObject(sql, BigDecimal.class, distintaPropostaErogazioneVO.getIdPropostaErogazione());

            if(distintaPropostaErogazioneVO.getAbilitata()) {
                LOG.info(prf + " Verifico il progetto " + distintaPropostaErogazioneVO.getCodiceVisualizzato());
                //1.	Blocco anagrafico
                if (
                        getJdbcTemplate().query(
                                "SELECT COUNT(*) as results\n" +
                                        "FROM PBANDI_T_SOGG_DOMANDA_BLOCCO soggettoDomandaBlocco \n" +
                                        "JOIN PBANDI_R_SOGGETTO_DOMANDA soggettoDomanda ON soggettoDomanda.progr_soggetto_domanda = soggettoDomandaBlocco.progr_soggetto_domanda \n" +
                                        "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = soggettoDomanda.id_domanda \n" +
                                        "JOIN PBANDI_D_CAUSALE_BLOCCO causaleBlocco ON causaleBlocco.id_causale_blocco = soggettoDomandaBlocco.id_causale_blocco \n" +
                                        "WHERE causaleBlocco.flag_erogazione = 'S'\n" +
                                        "AND soggettoDomanda.id_soggetto = ? \n" +
                                        "AND progetto.id_progetto = ? \n" +
                                        "AND soggettoDomandaBlocco.dt_inserimento_sblocco IS NULL",
                                ps -> {
                                    ps.setLong(1, distintaPropostaErogazioneVO.getIdSoggetto());
                                    ps.setLong(2, distintaPropostaErogazioneVO.getIdProgetto());
                                },
                                rs -> {
                                    rs.next();
                                    return rs.getLong("results");
                                }
                        ) > 0) {
                    //ho trovato almeno un blocco per questa domanda/soggetto senza data sblocco
                    LOG.info("La proposta con codice: " + distintaPropostaErogazioneVO.getCodiceVisualizzato() + "presenta un blocco soggetto/domanda senza data di sblocco!");
                    distintaPropostaErogazioneVO.setVerificaPosizione(false);
                    continue;
                }
                //2.	Forzatura controllo non applicabile per Antimafia
                if (
                        getJdbcTemplate().query(
                                "SELECT COUNT(*) as results \n" +
                                        "FROM \n" +
                                        "PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" +
                                        "WHERE \n" +
                                        "controlloNonApplicabile.id_tipo_richiesta = 3 \n" +
                                        "AND \n" +
                                        "controlloNonApplicabile.id_proposta = ? " +
                                        "AND \n" +
                                        "controlloNonApplicabile.dt_fine_validita IS NULL",
                                ps -> ps.setLong(1, distintaPropostaErogazioneVO.getIdPropostaErogazione()),
                                rs -> {
                                    rs.next();
                                    return rs.getLong("results");
                                }
                        ) < 1 //ANTIMAFIA SOLO SE IMPORTO < 150.000 (condizione rimossa)
                        /*importoAgevolato.compareTo(BigDecimal.valueOf(150000)) >= 0*/) {
                    //non ho trovato alcun controllo non applicabile di tipo antimafia per questa proposta di erogazione
                    //3.	Scaduto un controllo antimafia
                    if (
                            getJdbcTemplate().query(
                                    "SELECT COUNT(*) as results \n" +
                                            "FROM PBANDI_T_SOGGETTO_ANTIMAFIA antimafia \n" +
                                            "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = antimafia.id_domanda \n" +
                                            "AND progetto.id_progetto = ?\n" +
                                            "AND ( \n" +
                                            "\tantimafia.dt_scadenza_antimafia > CURRENT_DATE \n" +
                                            "\tOR ( \n" +
                                            "\t\tantimafia.dt_scadenza_antimafia IS NULL\n" +
                                            "\t\tAND antimafia.DT_RICEZIONE_BDNA IS NOT NULL\n" +
                                            "\t)\n" +
                                            ")",
                                    ps -> ps.setLong(1, distintaPropostaErogazioneVO.getIdProgetto()),
                                    rs -> {
                                        rs.next();
                                        return rs.getLong("results");
                                    }
                            ) < 1) {
                        //non ho trovato alcun controllo antimafia valido per questa proposta
                        LOG.info("La proposta con codice: " + distintaPropostaErogazioneVO.getCodiceVisualizzato() + " non presenta controlli antimafia validi!");
                        distintaPropostaErogazioneVO.setVerificaPosizione(false);
                        continue;
                    }
                }
                //4.	Forzatura controllo non applicabile per DURC
                if (
                        getJdbcTemplate().query(
                                "SELECT COUNT(*) as results \n" +
                                        "FROM \n" +
                                        "PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" +
                                        "WHERE \n" +
                                        "controlloNonApplicabile.id_tipo_richiesta = 1 \n" +
                                        "AND \n" +
                                        "controlloNonApplicabile.id_proposta = ? " +
                                        "AND \n" +
                                        "controlloNonApplicabile.dt_fine_validita IS NULL",
                                ps -> ps.setLong(1, distintaPropostaErogazioneVO.getIdPropostaErogazione()),
                                rs -> {
                                    rs.next();
                                    return rs.getLong("results");
                                }
                        ) < 1) {
                    //non ho trovato alcun controllo non applicabile di tipo DURC per questa proposta di erogazione
                    //5.	Scaduto un controllo DURC
                    if (
                            getJdbcTemplate().query(
                                    "SELECT COUNT(*) AS results\n" +
                                            "FROM PBANDI_T_SOGGETTO_DURC durc\n" +
                                            "WHERE durc.id_soggetto = ?\n" +
                                            "AND durc.dt_scadenza > CURRENT_DATE",
                                    ps -> ps.setLong(1, distintaPropostaErogazioneVO.getIdSoggetto()),
                                    rs -> {
                                        rs.next();
                                        return rs.getLong("results");
                                    }
                            ) < 1) {
                        if (
                                getJdbcTemplate().query(
                                        "SELECT COUNT(*) AS results\n" +
                                                "FROM PBANDI_T_SOGGETTO_DSAN dsan\n" +
                                                "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_domanda = dsan.id_domanda\n" +
                                                "WHERE progetto.id_progetto = ?\n" +
                                                "AND dsan.dt_scadenza > CURRENT_DATE",
                                        ps -> ps.setLong(1, distintaPropostaErogazioneVO.getIdProgetto()),
                                        rs -> {
                                            rs.next();
                                            return rs.getLong("results");
                                        }
                                ) < 1) {
                            //non ho trovato alcun controllo DURC valido per questa proposta
                            LOG.info("La proposta con codice: " + distintaPropostaErogazioneVO.getCodiceVisualizzato() + " non presenta controlli ne DURC ne DSAN validi!");
                            distintaPropostaErogazioneVO.setVerificaPosizione(false);
                            continue;
                        }
                    }
                }
                Long privato = getJdbcTemplate().query(
                        "SELECT CASE WHEN regolaBR57.id_regola IS NOT NULL \n" +
                                "\tTHEN \n" +
                                "\t\t(CASE WHEN enteGiuridico.flag_pubblico_privato IS NOT NULL \n" +
                                "\t\t\tTHEN \n" +
                                "\t\t\t\tenteGiuridico.flag_pubblico_privato \n" +
                                "\t\t\tELSE 2 \n" +
                                "\t\tEND)\n" +
                                "\tELSE \n" +
                                "\t\t2\n" +
                                "END AS flagPubblicoPrivato\n" +
                                "FROM PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione \n" +
                                "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                                "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
                                "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
                                "JOIN (SELECT DISTINCT \n" +
                                "\tsoggetto.id_soggetto,\n" +
                                "\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
                                "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                                "\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
                                "\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
                                "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
                                "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda \n" +
                                "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bandoLineaIntervento ON bandoLineaIntervento.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento \n" +
                                "LEFT JOIN (\n" +
                                "\tSELECT DISTINCT prbli.PROGR_BANDO_LINEA_INTERVENTO, pcr.ID_REGOLA \n" +
                                "\tFROM PBANDI_R_BANDO_LINEA_INTERVENT prbli \n" +
                                "\tJOIN PBANDI_R_REGOLA_BANDO_LINEA prrbl ON  prrbl.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \n" +
                                "\tJOIN PBANDI_C_REGOLA pcr ON pcr.ID_REGOLA = prrbl.ID_REGOLA AND pcr.DESC_BREVE_REGOLA = 'BR57'\n" +
                                ") regolaBR57 ON regolaBR57.PROGR_BANDO_LINEA_INTERVENTO = bandoLineaIntervento.PROGR_BANDO_LINEA_INTERVENTO\n" +
                                "JOIN PBANDI_T_BANDO bando ON bandoLineaIntervento.id_bando = bando.id_bando \n" +
                                "WHERE \n" +
                                "soggettoProgetto.id_tipo_anagrafica = 1 \n" +
                                "AND soggettoProgetto.id_tipo_beneficiario != 4 \n" +
                                "AND propostaErogazione.id_proposta = ?",
                        ps -> ps.setLong(1, distintaPropostaErogazioneVO.getIdPropostaErogazione()),
                        rs -> {
                            rs.next();
                            return rs.getLong("flagPubblicoPrivato");
                        });
                if (privato == 1) {
                    //6.	Forzatura controllo non applicabile per Deggendorf
                    if (
                            getJdbcTemplate().query(
                                    "SELECT COUNT(*) as results \n" +
                                            "FROM \n" +
                                            "PBANDI_R_CTRL_NA_EROGAZIONE controlloNonApplicabile \n" +
                                            "WHERE \n" +
                                            "controlloNonApplicabile.id_tipo_richiesta = 4 \n" +
                                            "AND \n" +
                                            "controlloNonApplicabile.id_proposta = ? ",
                                    ps -> ps.setLong(1, distintaPropostaErogazioneVO.getIdPropostaErogazione()),
                                    rs -> {
                                        rs.next();
                                        return rs.getLong("results");
                                    }
                            ) < 1) {
                        //non ho trovato alcun controllo non applicabile di tipo Deggendorf per questa proposta di erogazione
                        //7.	Scaduto un controllo Deggendorf (con esito 1)
                        if (
                                getJdbcTemplate().query(
                                        "SELECT COUNT(*) AS results \n" +
                                                "FROM \n" +
                                                "PBANDI_T_PROP_EROG_CTRL_RNA deggendorf \n" +
                                                "WHERE \n" +
                                                "deggendorf.id_soggetto = ? \n" +
                                                "AND \n" +
                                                "deggendorf.dt_scadenza > CURRENT_DATE \n" +
                                                "AND \n" +
                                                "deggendorf.esito = 1 ",
                                        ps -> ps.setLong(1, distintaPropostaErogazioneVO.getIdSoggetto()),
                                        rs -> {
                                            rs.next();
                                            return rs.getLong("results");
                                        }
                                ) < 1) {
                            //non ho trovato alcun controllo DURC valido per questa proposta
                            LOG.info("La proposta con codice: " + distintaPropostaErogazioneVO.getCodiceVisualizzato() + " non presenta controlli Deggendrof validi!");
                            distintaPropostaErogazioneVO.setVerificaPosizione(false);
                            continue;
                        }
                    }
                }
                distintaPropostaErogazioneVO.setVerificaPosizione(true);
            }
        }

        for(DistintaPropostaErogazioneVO distintaPropostaErogazioneVO : distintaPropostaErogazioneVOList) {
            if(distintaPropostaErogazioneVO.getAbilitata()) {
                String query = "UPDATE PBANDI_T_PROPOSTA_EROGAZIONE set " +
                        "FLAG_CTRL_PRE_EROGAZIONE = ?, " +
                        "dt_controlli = CURRENT_DATE, " +
                        "id_utente_agg = ?, " +
                        "dt_aggiornamento = CURRENT_DATE " +
                        "WHERE id_proposta = ?";
                LOG.debug(prf + " query: " + query + "\n" + prf + " args: " + idUtente + ", " + distintaPropostaErogazioneVO.getIdPropostaErogazione());

                getJdbcTemplate().update(query, distintaPropostaErogazioneVO.getVerificaPosizione() ? "S" : "N", idUtente, distintaPropostaErogazioneVO.getIdPropostaErogazione());
            }
        }

        LOG.info(prf + " END");
        return distintaPropostaErogazioneVOList;
    }

    @Override
    public Boolean checkAllegatiDistinta(Long idDistinta) {
        String prf = "[DistinteDAOImpl::checkAllegatiDistinta]";
        LOG.info(prf + " BEGIN");
        boolean esito;

        try{
            String query = "SELECT count(1)\n" +
                    "FROM (SELECT DISTINCT ptp.CODICE_VISUALIZZATO\n" +
                    "FROM PBANDI_T_DISTINTA ptd \n" +
                    "JOIN PBANDI_C_ENTITA pce ON pce.ID_ENTITA = ptd.ID_ENTITA\n" +
                    "JOIN PBANDI_T_DISTINTA_DETT ptdd ON ptdd.ID_DISTINTA = ptd.ID_DISTINTA \n" +
                    "JOIN PBANDI_T_PROPOSTA_EROGAZIONE ptpe ON ptpe.ID_PROPOSTA = ptdd.ID_TARGET \n" +
                    "JOIN PBANDI_T_PROGETTO ptp ON ptp.ID_PROGETTO = ptpe.ID_PROGETTO \n" +
                    "JOIN PBANDI_C_ENTITA pce2 ON pce2.NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
                    "LEFT JOIN PBANDI_T_DOCUMENTO_INDEX lettera ON lettera.ID_TARGET = ptd.ID_DISTINTA\n" +
                    "\tAND lettera.ID_ENTITA = pce2.ID_ENTITA AND lettera.ID_PROGETTO = ptp.ID_PROGETTO AND lettera.ID_TIPO_DOCUMENTO_INDEX = 42\n" +
                    "LEFT JOIN PBANDI_T_DOCUMENTO_INDEX checklist ON checklist.ID_TARGET = ptd.ID_DISTINTA\n" +
                    "\tAND checklist.ID_ENTITA = pce2.ID_ENTITA AND checklist.ID_PROGETTO = ptp.ID_PROGETTO AND checklist.ID_TIPO_DOCUMENTO_INDEX = 33\n" +
                    "WHERE pce.NOME_ENTITA = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                    "AND (\n" +
                    "\tNOT (lettera.ID_DOCUMENTO_INDEX IS NULL AND ptpe.flag_finistr IS NULL)\n" +
                    "\tAND NOT (checklist.ID_DOCUMENTO_INDEX IS NULL AND ptpe.flag_finistr IS NULL)\n" +
                    ") AND ptd.ID_DISTINTA = ?)";
            esito = getJdbcTemplate().queryForObject(query, Long.class, idDistinta) > 0;
        }catch (Exception e){
            LOG.error(prf + "Exception while trying to checkAllegatiDistinta", e);
            throw new ErroreGestitoException("DaoException while trying to checkAllegatiDistinta", e);
        }finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Transactional
    @Override
    public void avviaIterAutorizzativo(Long idDistinta, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::avviaIterAutorizzativo]";
        LOG.info(prf + " BEGIN");

        Long idEntitaDistinta = getJdbcTemplate().queryForObject(
                "SELECT entita.id_entita AS idEntita \n" +
                        "FROM PBANDI_C_ENTITA entita \n" +
                        "WHERE entita.nome_entita = 'PBANDI_T_DISTINTA'",
                Long.class
        );
        try{
            if(isAbilitatoAvviaIter(req)) {
                UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

                String query =
                        "UPDATE PBANDI_T_DISTINTA \n" +
                        "SET ID_STATO_DISTINTA = 1\n" +
                        "WHERE ID_DISTINTA = ?";
                getJdbcTemplate().update(query, idDistinta);

                String erroreIter = iterAutorizzativiDAO.avviaIterAutorizzativo(13L, idEntitaDistinta, idDistinta, null, userInfoSec.getIdUtente());
                if(!Objects.equals(erroreIter, "")){
                    throw new ErroreGestitoException(erroreIter);
                }
            }
        }catch (Exception e){
            LOG.error(prf + "Exception while trying to avviaIterAutorizzativo", e);
            throw new ErroreGestitoException("DaoException while trying to avviaIterAutorizzativo", e);
        }finally {
            LOG.info(prf + " END");
        }
    }

    @Override
    public void salvaAllegato(byte[] file, String visibilita, String nomeFile, Long idTipoDocumentoIndex, Long idDistinta, Long idProgetto, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::salvaAllegato]";
        LOG.info(prf + " BEGIN");

        try{
            UserInfoSec userInfoSec = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

            //Salvo l'allegato
            DocumentoIndexVO documentoIndexVO = new DocumentoIndexVO();
            documentoIndexVO.setIdTipoDocumentoIndex(BigDecimal.valueOf(idTipoDocumentoIndex));
            documentoIndexVO.setIdEntita(getJdbcTemplate().queryForObject(
                    "SELECT pce.ID_ENTITA FROM PBANDI_C_ENTITA pce WHERE pce.NOME_ENTITA = 'PBANDI_T_DISTINTA'",
                    BigDecimal.class
            ));
            documentoIndexVO.setIdTarget(BigDecimal.valueOf(idDistinta));
            documentoIndexVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
            documentoIndexVO.setDtInserimentoIndex(new java.sql.Date((new Date().getTime())));
            documentoIndexVO.setIdUtenteIns(BigDecimal.valueOf(userInfoSec.getIdUtente()));
            documentoIndexVO.setNomeFile(nomeFile);
            documentoIndexVO.setFlagVisibileBen(visibilita);
            documentoIndexVO.setIdStatoDocumento(new BigDecimal(2));
            documentoIndexVO.setRepository(getJdbcTemplate().queryForObject(
                    "SELECT DESC_BREVE_TIPO_DOC_INDEX FROM PBANDI_C_TIPO_DOCUMENTO_INDEX WHERE ID_TIPO_DOCUMENTO_INDEX = ?",
                    new Object[]{documentoIndexVO.getIdTipoDocumentoIndex()},
                    String.class
            ));
            /*Dati Obbligatori*/
            documentoIndexVO.setUuidNodo("UUID");

            //salva allegato
            if(!documentoManager.salvaFileConVisibilita(file, documentoIndexVO, null)) {
                throw new ErroreGestitoException("Errore durante il salvataggio dell'allegato");
            }
        }catch (Exception e){
            LOG.error(prf + "Exception while trying to salvaNuovoFile", e);
            throw new ErroreGestitoException("DaoException while trying to salvaNuovoFile", e);
        }finally {
            LOG.info(prf + " END");
        }
    }

    @Override
    public boolean eliminaAllegato(Long idDocumentoIndex) {
        String prf = "[DistinteDAOImpl::eliminaAllegato]";
        LOG.info(prf + " BEGIN");
        boolean esito;

        try{
                String query = "DELETE FROM PBANDI_T_DOCUMENTO_INDEX ptdi WHERE ptdi.ID_DOCUMENTO_INDEX = ?";
                getJdbcTemplate().update(query, idDocumentoIndex);
                esito = documentoManager.cancellaFileConVisibilita(BigDecimal.valueOf(idDocumentoIndex));
        }catch (Exception e){
            LOG.error(prf + "Exception while trying to eliminaAllegato", e);
            throw new ErroreGestitoException("DaoException while trying to eliminaAllegato", e);
        }finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    public boolean updateVisibilita(Long idDocumentoIndex, String visibilita) {
        String prf = "[DistinteDAOImpl::updateVisibilita]";
        LOG.info(prf + " BEGIN");

        try{
            String query = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET FLAG_VISIBILE_BEN = ? WHERE ID_DOCUMENTO_INDEX = ? ";
            getJdbcTemplate().update(query, visibilita, idDocumentoIndex);
        }catch (Exception e){
            LOG.error(prf + "Exception while trying to updateVisibilita", e);
            return false;
        }finally {
            LOG.info(prf + " END");
        }

        return true;
    }

    @Override
    public List<RicercaDistintaPropErogVO> filterDistinte(Date dataCreazioneFrom, Date dataCreazioneTo, Long idBando, Long idAgevolazione, Long idDistinta, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::filterDistinte(generic)]";
        LOG.info(prf + " BEGIN");
        List<RicercaDistintaPropErogVO> ricercaDistintaPropErogVOList;
        try{
            StringBuilder query = new StringBuilder();
            query.append("SELECT DISTINCT\n" +
                    "distinta.id_distinta AS idDistinta, \n" +
                    "distinta.descrizione AS descrizioneDistinta, \n" +
                    "tipoDistinta.id_tipo_distinta AS idTipoDistinta, \n" +
                    "tipoDistinta.desc_tipo_distinta AS descTipoDistinta, \n" +
                    "tipoDistinta.desc_breve_tipo_distinta AS descBreveTipoDistinta, \n" +
                    "personaFisica.cognome || ' ' || personaFisica.nome AS utenteCreazione, \n" +
                    "distinta.dt_inserimento as dataCreazioneDistinta, \n" +
                    "statoDistinta.id_stato_distinta AS idStatoDistinta,\n" +
                    "statoDistinta.desc_stato_distinta AS descStatoDistinta\n" +
                    "FROM \n" +
                    "PBANDI_T_DISTINTA distinta\n" +
                    "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = distinta.id_modalita_agevolazione\n" +
                    "JOIN PBANDI_D_TIPO_DISTINTA tipoDistinta ON tipoDistinta.id_tipo_distinta = distinta.id_tipo_distinta \n" +
                    "JOIN PBANDI_D_STATO_DISTINTA statoDistinta ON statoDistinta.id_stato_distinta = distinta.id_stato_distinta \n" +
                    "JOIN PBANDI_T_UTENTE utente ON utente.id_utente = distinta.id_utente_ins \n" +
                    "JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = utente.id_soggetto\n" +
                    "JOIN (\n" +
                    "\tSELECT max(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                    "\tFROM PBANDI_T_PERSONA_FISICA personaFisica\n" +
                    "\tGROUP BY personaFisica.id_soggetto\n" +
                    ") maxPersonaFisica ON maxPersonaFisica.id_persona_fisica = personaFisica.id_persona_fisica\n" +
                    "JOIN PBANDI_T_DISTINTA_DETT distintaDettaglio ON distintaDettaglio.id_distinta = distinta.id_distinta \n" +
                    "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = distintaDettaglio.id_target \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda\n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bando ON bando.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento\n" +
                    "WHERE \n" +
                    "tipoDistinta.desc_breve_tipo_distinta IN ('ER', 'ES')\n" +
                    "AND \n" +
                    "distinta.id_entita = (\n" +
                    "\tSELECT id_entita\n" +
                    "\tFROM PBANDI_C_ENTITA\n" +
                    "\tWHERE PBANDI_C_ENTITA.nome_entita = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                    ")\n");
            if(dataCreazioneFrom != null){
                query.append("AND distinta.dt_inserimento >= ? \n");
            }
            if(dataCreazioneTo != null){
                query.append("AND distinta.dt_inserimento <= ? + 1 \n");
            }
            if(idBando != null){
                query.append(" \n" +
                        "AND bando.id_bando = ? \n");
            }
            if(idAgevolazione != null){
                query.append(" \n" +
                        "AND modalitaAgevolazione.id_modalita_agevolazione_rif = ? \n");
            }
            if(idDistinta != null) {
                query.append(" \n" +
                        "AND distinta.id_distinta = ? \n");
            }
            ricercaDistintaPropErogVOList = getJdbcTemplate().query(
                    query.toString(),
                    ps ->
                    {
                        int i = 0;
                        if(dataCreazioneFrom != null){
                            ps.setDate(++i, new java.sql.Date(dataCreazioneFrom.getTime()));
                        }
                        if(dataCreazioneTo != null){
                            ps.setDate(++i, new java.sql.Date(dataCreazioneTo.getTime()));
                        }
                        if(idBando != null){
                            ps.setLong(++i, idBando);
                        }
                        if(idAgevolazione != null){
                            ps.setLong(++i, idAgevolazione);
                        }
                        if(idDistinta != null){
                            ps.setLong(++i, idDistinta);
                        }
                    },
                    new RicercaDistintaPropErogVORowMapper()
            );
            for(RicercaDistintaPropErogVO distinta : ricercaDistintaPropErogVOList){
                //set stato iter
                if(getJdbcTemplate().queryForObject(
                        "SELECT COUNT(1)\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workflowEntita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workflowEntita.id_target\n" +
                                "JOIN PBANDI_T_WORK_FLOW workflow ON workflow.id_work_flow = workflowEntita.id_work_flow\n" +
                                "JOIN PBANDI_T_DETT_WORK_FLOW workflowDett ON workflowDett.id_work_flow = workflow.id_work_flow\n" +
                                "WHERE workflowEntita.id_entita = (\n" +
                                "\tSELECT ID_ENTITA \n" +
                                "\tFROM PBANDI_C_ENTITA \n" +
                                "\tWHERE NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
                                ") AND workflowDett.id_stato_iter = 5 \n" +
                                "AND distinta.id_distinta = ?",
                        new Object[]{distinta.getIdDistinta()},
                        Long.class) != 0){
                    distinta.setStatoIterAutorizzativo("Approvato");
                }else if(getJdbcTemplate().queryForObject(
                        "SELECT COUNT(1)\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workflowEntita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workflowEntita.id_target\n" +
                                "JOIN PBANDI_T_WORK_FLOW workflow ON workflow.id_work_flow = workflowEntita.id_work_flow\n" +
                                "JOIN PBANDI_T_DETT_WORK_FLOW workflowDett ON workflowDett.id_work_flow = workflow.id_work_flow\n" +
                                "WHERE workflowEntita.id_entita = (\n" +
                                "\tSELECT ID_ENTITA \n" +
                                "\tFROM PBANDI_C_ENTITA \n" +
                                "\tWHERE NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
                                ") AND workflowDett.dt_annullamento IS NOT NULL\n" +
                                "AND distinta.id_distinta = ?",
                        new Object[]{distinta.getIdDistinta()},
                        Long.class) != 0){
                    distinta.setStatoIterAutorizzativo("Rifiutato");
                }else if(getJdbcTemplate().queryForObject(
                        "SELECT COUNT(1)\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workflowEntita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workflowEntita.id_target\n" +
                                "JOIN PBANDI_T_WORK_FLOW workflow ON workflow.id_work_flow = workflowEntita.id_work_flow\n" +
                                "JOIN PBANDI_T_DETT_WORK_FLOW workflowDett ON workflowDett.id_work_flow = workflow.id_work_flow\n" +
                                "WHERE workflowEntita.id_entita = (\n" +
                                "\tSELECT ID_ENTITA \n" +
                                "\tFROM PBANDI_C_ENTITA \n" +
                                "\tWHERE NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
                                ") AND workflowDett.dt_approvazione IS NULL AND workflowDett.dt_annullamento IS NULL\n" +
                                "AND distinta.id_distinta = ?",
                        new Object[]{distinta.getIdDistinta()},
                        Long.class) != 0){
                    distinta.setStatoIterAutorizzativo("In corso");
                }
            }
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read RicercaDistintaPropErogVO", e);
            throw new ErroreGestitoException("DaoException while trying to read RicercaDistintaPropErogVO", e);
        } finally {
            LOG.info(prf + " END");
        }
        return ricercaDistintaPropErogVOList;
    }

    @Override
    public List<RicercaDistintaPropErogPlusVO> filterDistinte(Date dataCreazioneFrom, Date dataCreazioneTo, Long idBeneficiario,
                                                              Long idBando, Long idAgevolazione, Long idProgetto, Long idDistinta, HttpServletRequest req) {
        String prf = "[DistinteDAOImpl::filterDistinte(idProgetto/idBeneficiario/idBando/idAgevolazione)]";
        LOG.info(prf + " BEGIN");
        List<RicercaDistintaPropErogPlusVO> ricercaDistintaPropErogPlusVOList;
        try{
            StringBuilder query = new StringBuilder();
            query.append(
                    "SELECT DISTINCT \n" +
                    "distinta.id_distinta AS idDistinta, \n" +
                    "distinta.descrizione AS descrizioneDistinta, \n" +
                    "tipoDistinta.id_tipo_distinta AS idTipoDistinta, \n" +
                    "tipoDistinta.desc_tipo_distinta AS descTipoDistinta, \n" +
                    "tipoDistinta.desc_breve_tipo_distinta AS descBreveTipoDistinta, \n" +
                    "personaFisica.cognome || ' ' || personaFisica.nome AS utenteCreazione, \n" +
                    "distinta.dt_inserimento as dataCreazioneDistinta, \n" +
                    "statoDistinta.desc_stato_distinta AS descStatoDistinta, \n" +
                    "propostaErogazione.id_proposta AS idPropostaErogazione, \n" +
                    "progetto.id_progetto AS idProgetto, \n" +
                    "progetto.codice_visualizzato AS codiceVisualizzato, \n" +
                    "progetto.codice_fondo_finpis AS codiceFondoFinpis, \n" +
                    "soggetto.id_soggetto AS idSoggetto, \n" +
                    "(CASE WHEN personaFisicaBenef.id_soggetto IS NOT NULL THEN (personaFisicaBenef.cognome || ' ' || personaFisicaBenef.nome) ELSE (CASE WHEN enteGiuridicoBenef.id_ente_giuridico IS NOT NULL THEN enteGiuridicoBenef.denominazione_ente_giuridico END) END) AS denominazione, \n" +
                    "personaFisica.id_soggetto,\n" +
                    "soggetto.codice_fiscale_soggetto AS codiceFiscaleSoggetto, \n" +
                    "sede.id_sede AS idSede, \n" +
                    "sede.partita_iva AS partitaIva, \n" +
                    "soggettoProgetto.progr_soggetto_progetto AS progrSoggettoProgetto, \n" +
                    "estremiBancari.id_estremi_bancari AS idEstremiBancari,\n" +
                    "estremiBancari.iban AS iban,\n" +
                    "propostaErogazione.imp_da_erogare AS importoNetto, \n" +
                    "propostaErogazione.imp_lordo AS importoLordo, \n" +
                    "erogazione.dt_contabile AS dataContabileErogazione, \n" +
                    "erogazione.importo_erogazione AS importoErogato \n" +
                    "FROM \n" +
                    "PBANDI_T_DISTINTA distinta \n" +
                    "JOIN PBANDI_D_MODALITA_AGEVOLAZIONE modalitaAgevolazione ON modalitaAgevolazione.id_modalita_agevolazione = distinta.id_modalita_agevolazione\n" +
                    "JOIN PBANDI_D_TIPO_DISTINTA tipoDistinta ON tipoDistinta.id_tipo_distinta = distinta.id_tipo_distinta \n" +
                    "JOIN PBANDI_D_STATO_DISTINTA statoDistinta ON statoDistinta.id_stato_distinta = distinta.id_stato_distinta \n" +
                    "JOIN PBANDI_T_UTENTE utente ON utente.id_utente = distinta.id_utente_ins \n" +
                    "JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = utente.id_soggetto\n" +
                    "JOIN (\n" +
                    "\tSELECT max(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                    "\tFROM PBANDI_T_PERSONA_FISICA personaFisica\n" +
                    "\tGROUP BY personaFisica.id_soggetto\n" +
                    ") maxPersonaFisica ON maxPersonaFisica.id_persona_fisica = personaFisica.id_persona_fisica\n" +
                    "JOIN PBANDI_T_DISTINTA_DETT distintaDettaglio ON distintaDettaglio.id_distinta = distinta.id_distinta \n" +
                    "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = distintaDettaglio.id_target \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                    "JOIN PBANDI_T_DOMANDA domanda ON domanda.id_domanda = progetto.id_domanda\n" +
                    "JOIN PBANDI_R_BANDO_LINEA_INTERVENT bando ON bando.progr_bando_linea_intervento = domanda.progr_bando_linea_intervento\n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
                    "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
                    "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridicoBenef ON enteGiuridicoBenef.id_ente_giuridico = soggettoProgetto.id_ente_giuridico\n" +
                    "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisicaBenef ON personaFisicaBenef.id_persona_fisica = soggettoProgetto.id_persona_fisica\n" +
                    "JOIN PBANDI_R_SOGG_PROGETTO_SEDE soggettoProgettoSede ON soggettoProgettoSede.progr_soggetto_progetto = soggettoProgetto.progr_soggetto_progetto \n" +
                    "JOIN PBANDI_T_SEDE sede ON sede.id_sede = soggettoProgettoSede.id_sede \n" +
                    "LEFT JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.id_estremi_bancari = soggettoProgetto.id_estremi_bancari\n" +
                    "LEFT JOIN PBANDI_T_EROGAZIONE erogazione ON erogazione.id_distinta = distinta.id_distinta \n" +
                    "WHERE tipoDistinta.desc_breve_tipo_distinta IN ('ER', 'ES')\n" +
                    "AND \n" +
                    "distinta.id_entita = (\n" +
                    "\tSELECT id_entita\n" +
                    "\tFROM PBANDI_C_ENTITA\n" +
                    "\tWHERE PBANDI_C_ENTITA.nome_entita = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                    ")\n" +
                    "AND \n" +
                    "id_tipo_anagrafica = 1\n" +
                    "AND \n" +
                    "id_tipo_beneficiario != 4 \n");
            if(dataCreazioneFrom != null){
                query.append("AND distinta.dt_inserimento >= ? \n");
            }
            if(dataCreazioneTo != null){
                query.append("AND distinta.dt_inserimento <= ? \n");
            }
            if(idBeneficiario != null){
                query.append(" \n" +
                        "AND soggetto.id_soggetto = ? \n");
            }
            if(idBando != null){
                query.append(" \n" +
                        "AND bando.id_bando = ? \n");
            }
            if(idAgevolazione != null){
                query.append(" \n" +
                        "AND modalitaAgevolazione.id_modalita_agevolazione_rif = ? \n");
            }
            if(idProgetto != null){
                query.append(" \n" +
                        "AND progetto.id_progetto = ? \n");
            }
            if(idDistinta != null) {
                query.append(" \n" +
                        "AND distinta.id_distinta = ? \n");
            }
            ricercaDistintaPropErogPlusVOList = getJdbcTemplate().query(
                    query.toString(),
                    ps ->
                    {
                        int i = 0;
                        if(dataCreazioneFrom != null){
                            ps.setDate(++i, new java.sql.Date(dataCreazioneFrom.getTime()));
                        }
                        if(dataCreazioneTo != null){
                            ps.setDate(++i, new java.sql.Date(dataCreazioneTo.getTime()));
                        }
                        if(idBeneficiario != null){
                            ps.setLong(++i, idBeneficiario);
                        }
                        if(idBando != null){
                            ps.setLong(++i, idBando);
                        }
                        if(idAgevolazione != null){
                            ps.setLong(++i, idAgevolazione);
                        }
                        if(idProgetto != null){
                            ps.setLong(++i, idProgetto);
                        }
                        if(idDistinta != null) {
                            ps.setLong(++i, idDistinta);
                        }
                    },
                    new RicercaDistintaPropErogPlusVORowMapper()
            );
            for(RicercaDistintaPropErogPlusVO distinta : ricercaDistintaPropErogPlusVOList){
                if(getJdbcTemplate().queryForObject(
                        "SELECT COUNT(1)\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workflowEntita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workflowEntita.id_target\n" +
                                "JOIN PBANDI_T_WORK_FLOW workflow ON workflow.id_work_flow = workflowEntita.id_work_flow\n" +
                                "JOIN PBANDI_T_DETT_WORK_FLOW workflowDett ON workflowDett.id_work_flow = workflow.id_work_flow\n" +
                                "WHERE workflowEntita.id_entita = (\n" +
                                "\tSELECT ID_ENTITA \n" +
                                "\tFROM PBANDI_C_ENTITA \n" +
                                "\tWHERE NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
                                ") AND workflowDett.id_stato_iter = 5 \n" +
                                "AND distinta.id_distinta = ?",
                        new Object[]{distinta.getIdDistinta()},
                        Long.class) != 0){
                    distinta.setStatoIterAutorizzativo("Approvato");
                }else if(getJdbcTemplate().queryForObject(
                        "SELECT COUNT(1)\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workflowEntita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workflowEntita.id_target\n" +
                                "JOIN PBANDI_T_WORK_FLOW workflow ON workflow.id_work_flow = workflowEntita.id_work_flow\n" +
                                "JOIN PBANDI_T_DETT_WORK_FLOW workflowDett ON workflowDett.id_work_flow = workflow.id_work_flow\n" +
                                "WHERE workflowEntita.id_entita = (\n" +
                                "\tSELECT ID_ENTITA \n" +
                                "\tFROM PBANDI_C_ENTITA \n" +
                                "\tWHERE NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
                                ") AND workflowDett.dt_annullamento IS NOT NULL\n" +
                                "AND distinta.id_distinta = ?",
                        new Object[]{distinta.getIdDistinta()},
                        Long.class) != 0){
                    distinta.setStatoIterAutorizzativo("Rifiutato");
                }else if(getJdbcTemplate().queryForObject(
                        "SELECT COUNT(1)\n" +
                                "FROM PBANDI_T_WORK_FLOW_ENTITA workflowEntita\n" +
                                "JOIN PBANDI_T_DISTINTA distinta ON distinta.id_distinta = workflowEntita.id_target\n" +
                                "JOIN PBANDI_T_WORK_FLOW workflow ON workflow.id_work_flow = workflowEntita.id_work_flow\n" +
                                "JOIN PBANDI_T_DETT_WORK_FLOW workflowDett ON workflowDett.id_work_flow = workflow.id_work_flow\n" +
                                "WHERE workflowEntita.id_entita = (\n" +
                                "\tSELECT ID_ENTITA \n" +
                                "\tFROM PBANDI_C_ENTITA \n" +
                                "\tWHERE NOME_ENTITA = 'PBANDI_T_DISTINTA'\n" +
                                ") AND workflowDett.dt_approvazione IS NULL AND workflowDett.dt_annullamento IS NULL\n" +
                                "AND distinta.id_distinta = ?",
                        new Object[]{distinta.getIdDistinta()},
                        Long.class) != 0){
                    distinta.setStatoIterAutorizzativo("In corso");
                }
            }
        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read RicercaDistintaPropErogVO", e);
            throw new ErroreGestitoException("DaoException while trying to read RicercaDistintaPropErogVO", e);
        } finally {
            LOG.info(prf + " END");
        }
        return ricercaDistintaPropErogPlusVOList;
    }

    @Override
    public XSSFWorkbook esportaDettaglioDistinta(Long idDistinta) {
        String prf = "[DistinteDAOImpl::filterDistinte(idProgetto/idBeneficiario)]";
        LOG.info(prf + " BEGIN");
        XSSFWorkbook workbook = new XSSFWorkbook();
        try{
            //Dati per stampa excel
            String query = "SELECT DISTINCT \n" +
                    "propostaErogazione.id_proposta, \n" +
                    "progetto.titolo_progetto AS titoloProgetto, \n" +
                    "progetto.codice_visualizzato AS codiceVisualizzatoProgetto, \n" +
                    "(CASE WHEN personaFisica.id_soggetto IS NOT NULL THEN (personaFisica.cognome || ' ' || personaFisica.nome) ELSE (CASE WHEN enteGiuridico.id_soggetto IS NOT NULL THEN enteGiuridico.denominazione_ente_giuridico END) END) AS denominazioneBeneficiario,\n" +
                    "soggetto.codice_fiscale_soggetto AS codiceFiscale, \n" +
                    "sede.partita_iva AS partitaIva, \n" +
                    "(CASE WHEN destinatarioIntervento.iban IS NOT NULL THEN destinatarioIntervento.iban ELSE estremiBancari.iban END) AS iban,\n" +
                    "propostaErogazione.imp_lordo AS importoLordo,\n" +
                    "propostaErogazione.imp_ires AS importoIres,\n" +
                    "propostaErogazione.imp_da_erogare AS importoNetto\n" +
                    "FROM PBANDI_T_DISTINTA distinta \n" +
                    "JOIN PBANDI_T_DISTINTA_DETT distintaDettaglio ON distintaDettaglio.id_distinta = distinta.id_distinta \n" +
                    "JOIN PBANDI_T_PROPOSTA_EROGAZIONE propostaErogazione ON propostaErogazione.id_proposta = distintaDettaglio.id_target \n" +
                    "JOIN PBANDI_T_PROGETTO progetto ON progetto.id_progetto = propostaErogazione.id_progetto \n" +
                    "JOIN PBANDI_R_SOGGETTO_PROGETTO soggettoProgetto ON soggettoProgetto.id_progetto = progetto.id_progetto \n" +
                    "JOIN PBANDI_T_SOGGETTO soggetto ON soggetto.id_soggetto = soggettoProgetto.id_soggetto \n" +
                    "JOIN (SELECT DISTINCT \n" +
                    "\tsoggetto.id_soggetto,\n" +
                    "\tmax(enteGiuridico.id_ente_giuridico) AS id_ente_giuridico\n" +
                    "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                    "\tLEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_soggetto = soggetto.id_soggetto \n" +
                    "\tGROUP BY soggetto.id_soggetto) enteUnivoco ON enteUnivoco.id_soggetto = soggetto.id_soggetto \n" +
                    "LEFT JOIN PBANDI_T_ENTE_GIURIDICO enteGiuridico ON enteGiuridico.id_ente_giuridico = enteUnivoco.id_ente_giuridico\n" +
                    "JOIN (SELECT DISTINCT \n" +
                    "\tsoggetto.id_soggetto,\n" +
                    "\tmax(personaFisica.id_persona_fisica) AS id_persona_fisica\n" +
                    "\tFROM PBANDI_T_SOGGETTO soggetto  \n" +
                    "\tLEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_soggetto = soggetto.id_soggetto \n" +
                    "\tGROUP BY soggetto.id_soggetto) personaUnivoca ON personaUnivoca.id_soggetto = soggetto.id_soggetto \n" +
                    "LEFT JOIN PBANDI_T_PERSONA_FISICA personaFisica ON personaFisica.id_persona_fisica = personaUnivoca.id_persona_fisica \n" +
                    "JOIN PBANDI_R_SOGG_PROGETTO_SEDE soggettoProgettoSede ON soggettoProgettoSede.progr_soggetto_progetto = soggettoProgetto.progr_soggetto_progetto \n" +
                    "JOIN PBANDI_T_SEDE sede ON sede.id_sede = soggettoProgettoSede.id_sede \n" +
                    "LEFT JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.id_estremi_bancari = soggettoProgetto.id_estremi_bancari \n" +
                    "LEFT JOIN PBANDI_D_DEST_INTERV_SOSTITUT destinatarioIntervento ON destinatarioIntervento.id_destinatario_intervento = propostaErogazione.id_destinatario_intervento\n" +
                    "LEFT JOIN PBANDI_T_EROGAZIONE erogazione ON erogazione.id_distinta = distinta.id_distinta \n" +
                    "WHERE \n" +
                    "distinta.id_entita = (\n" +
                    "\tSELECT id_entita\n" +
                    "\tFROM PBANDI_C_ENTITA\n" +
                    "\tWHERE PBANDI_C_ENTITA.nome_entita = 'PBANDI_T_PROPOSTA_EROGAZIONE'\n" +
                    ")\n" +
                    "AND id_tipo_anagrafica=1\n" +
                    "AND id_tipo_beneficiario != 4 \n" +
                    "AND distinta.id_distinta = ?";
            List<PropostaErogazioneExcelVO> propostaErogazioneExcelVOS = getJdbcTemplate().query(
                query,
                ps -> ps.setLong(1, idDistinta),
                new PropostaErogazioneExcelVORowMapper());

            query = "SELECT DISTINCT\n" +
                    "estremiBancari.iban AS iban\n" +
                    "FROM PBANDI_T_DISTINTA distinta\n" +
                    "JOIN PBANDI_T_ESTREMI_BANCARI estremiBancari ON estremiBancari.ID_ESTREMI_BANCARI = distinta.ID_ESTREMI_BANCARI\n" +
                    "WHERE distinta.id_distinta = ?";
            String iban = getJdbcTemplate().queryForObject(query, new Object[]{idDistinta}, String.class);


            //Creazione excel
            {
                // create a new Excel sheet
                XSSFSheet sheet = workbook.createSheet("Proposte erogazione distinta");

                // create style for header cells
                XSSFCellStyle style = workbook.createCellStyle();
                XSSFFont font = workbook.createFont();
                //font.setFontName("Arial");
                font.setBold(true);
                style.setVerticalAlignment(VerticalAlignment.TOP);
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setBorderBottom(BorderStyle.MEDIUM);
                style.setBorderTop(BorderStyle.MEDIUM);
                style.setBorderLeft(BorderStyle.MEDIUM);
                style.setBorderRight(BorderStyle.MEDIUM);
                style.setWrapText(true);
                style.setFont(font);

                // CELL STYLE DI DEFAULT
                XSSFCellStyle styleValDef = workbook.createCellStyle();
                font = workbook.createFont();
                //font.setFontName("Arial");
                styleValDef.setWrapText(true);
                styleValDef.setVerticalAlignment(VerticalAlignment.CENTER);
                styleValDef.setAlignment(HorizontalAlignment.LEFT);
                styleValDef.setBorderBottom(BorderStyle.THIN);
                styleValDef.setBorderTop(BorderStyle.THIN);
                styleValDef.setBorderLeft(BorderStyle.THIN);
                styleValDef.setBorderRight(BorderStyle.THIN);
                styleValDef.setFont(font);

                // CELL STYLE PER GLI IMPORTI
                XSSFCellStyle styleImporto = workbook.createCellStyle();
                styleImporto.cloneStyleFrom(styleValDef);
                DataFormat format = workbook.createDataFormat();
                styleImporto.setDataFormat(format.getFormat("#,##0.00"));
                styleImporto.setAlignment(HorizontalAlignment.RIGHT);

                // create header row
                XSSFRow header = sheet.createRow(0);

                String[] titoliCol = new String[]{
                        "Titolo Progetto", "Codice Visualizzato Progetto", "Denominazione Beneficiario",
                        "Codice Fiscale", "Partita Iva", "Iban",
                        "Importo Lordo", "Importo Ires", "Importo Netto"};
                for (int countCol = 0; countCol < titoliCol.length; countCol++) {
                    header.createCell(countCol).setCellValue(titoliCol[countCol]);
                    header.getCell(countCol).setCellStyle(style);
                    sheet.setDefaultColumnStyle(countCol, styleValDef);
                }

                // create data rows
                int rowCount = 1;
                for(PropostaErogazioneExcelVO propostaErogazioneExcelVO : propostaErogazioneExcelVOS) {
                    XSSFRow row = sheet.createRow(rowCount++);
                    row.setHeight((short) -1);

                    row.createCell(0);
                    if(propostaErogazioneExcelVO.getTitoloProgetto() != null){
                        row.getCell(0).setCellValue(propostaErogazioneExcelVO.getTitoloProgetto());
                    }
                    row.getCell(0).setCellStyle(styleValDef);

                    row.createCell(1);
                    if(propostaErogazioneExcelVO.getCodiceVisualizzatoProgetto() != null){
                        row.getCell(1).setCellValue(propostaErogazioneExcelVO.getCodiceVisualizzatoProgetto());
                    }
                    row.getCell(1).setCellStyle(styleValDef);

                    row.createCell(2);
                    if(propostaErogazioneExcelVO.getDenominazioneBeneficiario() != null){
                        row.getCell(2).setCellValue(propostaErogazioneExcelVO.getDenominazioneBeneficiario());
                    }
                    row.getCell(2).setCellStyle(styleValDef);

                    row.createCell(3);
                    if(propostaErogazioneExcelVO.getCodiceFiscale() != null){
                        row.getCell(3).setCellValue(propostaErogazioneExcelVO.getCodiceFiscale());
                    }
                    row.getCell(3).setCellStyle(styleValDef);

                    row.createCell(4);
                    if(propostaErogazioneExcelVO.getPartitaIva() != null){
                        row.getCell(4).setCellValue(propostaErogazioneExcelVO.getPartitaIva());
                    }
                    row.getCell(4).setCellStyle(styleValDef);

                    row.createCell(5);
                    if(propostaErogazioneExcelVO.getIban() != null){
                        row.getCell(5).setCellValue(propostaErogazioneExcelVO.getIban());
                    }else {
                        row.getCell(5).setCellValue(iban);
                    }
                    row.getCell(5).setCellStyle(styleValDef);

                    row.createCell(6);
                    if(propostaErogazioneExcelVO.getImportoLordo() != null){
                        row.getCell(6).setCellValue(propostaErogazioneExcelVO.getImportoLordo().doubleValue());
                    }
                    row.getCell(6).setCellStyle(styleImporto);

                    row.createCell(7);
                    if(propostaErogazioneExcelVO.getImportoIres() != null){
                        row.getCell(7).setCellValue(propostaErogazioneExcelVO.getImportoIres().doubleValue());
                    }
                    row.getCell(7).setCellStyle(styleImporto);

                    row.createCell(8);
                    if(propostaErogazioneExcelVO.getImportoNetto() != null){
                        row.getCell(8).setCellValue(propostaErogazioneExcelVO.getImportoNetto().doubleValue());
                    }
                    row.getCell(8).setCellStyle(styleImporto);
                }

                //adjust column width to fit the content
                for (int countCol = 0; countCol < 9; countCol++) {
                    sheet.autoSizeColumn(countCol);
                }
            }

        } catch (Exception e) {
            LOG.error(prf + "Exception while trying to read PropostaErogazioneExcelVO", e);
            throw new ErroreGestitoException("DaoException while trying to read PropostaErogazioneExcelVO", e);
        } finally {
            LOG.info(prf + " Excel prova: " + workbook);
            LOG.info(prf + " END");
        }
        return workbook;
    }
}
