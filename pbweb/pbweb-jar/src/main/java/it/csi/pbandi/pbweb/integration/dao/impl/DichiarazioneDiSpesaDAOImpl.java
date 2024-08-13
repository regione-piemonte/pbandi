/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.integration.dao.impl.ArchivioFileDAOImpl;
import it.csi.pbandi.pbservizit.integration.dao.impl.ContoEconomicoDAOImpl;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.CoordinateBancarieDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiPiuGreenDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DelegatoDTO;
import it.csi.pbandi.pbweb.business.manager.DocumentoManager;
import it.csi.pbandi.pbweb.dto.AllegatoIntegrazioneDs;
import it.csi.pbandi.pbweb.dto.DatiIntegrazioneDsDTO;
import it.csi.pbandi.pbweb.dto.DatiProgettoInizializzazioneDTO;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.IntegerValue;
import it.csi.pbandi.pbweb.dto.RigaTabRichiesteIntegrazioniDs;
import it.csi.pbandi.pbweb.dto.archivioFile.FileDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.AllegatiDichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.DeclaratoriaDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.EsitoOperazioneAnteprimaDichiarazioneSpesa;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.EsitoOperazioneInviaDichiarazioneDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.EsitoOperazioneVerificaDichiarazioneSpesa;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.InizializzaIntegrazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.InizializzaInvioDichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.DocumentoDiSpesa;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.DichiarazioneDiSpesaDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.dao.request.AnteprimaDichiarazioneDiSpesaCulturaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.AnteprimaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.InviaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.integration.vo.ProgettoBandoLineaVO;
import it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.dichiarazionedispesa.DichiarazioneDiSpesaBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.digitalsign.DigitalSignBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione.ValidazioneRendicontazioneBusinessImpl;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.Esito;
import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneInviaDichiarazione;
import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.IndirizzoPrivatoCittadino;
import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.IstanzaAttivitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.RelazioneTecnicaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.digitalsign.SignedFileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.fineprogetto.ComunicazioneFineProgettoDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.AllegatoIntegrazioneSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.util.BeanUtil;
import it.csi.pbandi.pbweb.util.Constants;
import it.csi.pbandi.pbweb.util.DateUtil;
import it.csi.pbandi.pbweb.util.ErrorMessages;
import it.csi.pbandi.pbweb.util.FileSqlUtil;
import it.csi.pbandi.pbweb.util.FileUtil;
import it.csi.pbandi.pbweb.util.TraduttoreMessaggiPbandisrv;

@Service
public class DichiarazioneDiSpesaDAOImpl extends JdbcDaoSupport implements DichiarazioneDiSpesaDAO {

    private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

    @Autowired
    protected DecodificheDAOImpl decodificheDAOImpl;

    @Autowired
    protected ArchivioFileDAOImpl archivioFileDAOImpl;

    @Autowired
    protected DocumentoManager documentoManager;

    @Autowired
    protected DichiarazioneDiSpesaBusinessImpl dichiarazioneDiSpesaBusinessImpl;

    @Autowired
    protected ProfilazioneBusinessImpl profilazioneBusinessImpl;

    @Autowired
    protected ArchivioBusinessImpl archivioBusinessImpl;

    @Autowired
    protected GestioneDatiGeneraliBusinessImpl gestioneDatiGeneraliBusinessImpl;

    @Autowired
    protected SoggettoManager soggettoManager;

    @Autowired
    DigitalSignBusinessImpl digitalSignBusinessImpl;

    @Autowired
    ValidazioneRendicontazioneBusinessImpl validazioneRendicontazioneBusinessImpl;

    @Autowired
    public DichiarazioneDiSpesaDAOImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Autowired
    protected BeanUtil beanUtil;

    @Autowired
    protected FileSqlUtil fileSqlUtil;

    @Autowired
    protected ContoEconomicoDAOImpl contoEconomicoDAOImpl;

    @Override
    // Restituisce i dati con cui popolare Invio Dichiarazione di Spesa
    // all'apertura:
    public InizializzaInvioDichiarazioneDiSpesaDTO inizializzaInvioDichiarazioneDiSpesa(Long idProgetto,
            Long idBandoLinea, Long idUtente, String idIride) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::InizializzaInvioDichiarazioneDiSpesaDTO] ";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente + "; idBandoLinea = " + idBandoLinea);

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato.");
        }
        if (idBandoLinea == null) {
            throw new InvalidParameterException("idBandoLinea non valorizzato.");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato.");
        }
        if (StringUtils.isBlank(idIride)) {
            throw new InvalidParameterException("idIride non valorizzato.");
        }

        InizializzaInvioDichiarazioneDiSpesaDTO result = new InizializzaInvioDichiarazioneDiSpesaDTO();
        try {

            // Usata per controlli su cronoprogramma.
            Boolean regola30attiva = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR30_DATA_AMMISSIBILITA_PER_PROGETTTI);
            result.setRegola30attiva(regola30attiva);

            // Se true, gli indicatori sono obbligatori: devono essere compilati prima di
            // poter generare la CFP.
            Boolean indicatoriObbligatori = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR35_CONTROLLO_ATTIVITA_INDICATORI_VALIDA);
            result.setIndicatoriObbligatori(indicatoriObbligatori);

            // Se true, il cronoprogramma è obbligatorio: deve essere compilato prima di
            // poter generare la CFP.
            Boolean cronoprogrammaObbligatorio = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente,
                    idIride, idBandoLinea, RegoleConstants.BR34_CONTROLLO_ATTIVITA_CRONOPROGRAMMA_VALIDA);
            result.setCronoprogrammaObbligatorio(cronoprogrammaObbligatorio);

            // La compo del task la visualizzo solo se e' abilitata la regola BR16.
            Boolean taskVisibile = profilazioneBusinessImpl.isRegolaApplicabileForProgetto(idUtente, idIride,
                    idProgetto, RegoleConstants.BR16_GESTIONE_CAMPO_TASK);
            result.setTaskVisibile(taskVisibile);

            // Se true, viene visualizzato il tasto per l'upload della Relazione Tecnica.
            Boolean uploadRelazioneTecnicaAmmesso = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente,
                    idIride, idBandoLinea, RegoleConstants.BR33_UPLOAD_FILE_RELAZIONE_TECNICA);

            result.setUploadRelazioneTecnicaAmmesso(uploadRelazioneTecnicaAmmesso);

            // Gli allegati alla dichiarazione sono ammessi solo se e' abilitata la regola
            // BR42.
            Boolean allegatiAmmessi = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
            result.setAllegatiAmmessi(allegatiAmmessi);

            // la dichiarazione Finale e' ammessa solo se e' abilitata la regola BR54.
            Boolean dichiarazioneFinale = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR54_ATTIVA_DICHIARAZIONE_FINALE);
            result.setRegolaBR54attiva(dichiarazioneFinale);

            // Gli allegati generici (facoltativi) sono ammessi solo se e' abilitata la
            // regola BR53.
            Boolean allegatiGenerici = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR53_UPLOAD_ALLEGATI_GENERICI);
            result.setRegolaBR53attiva(allegatiGenerici);

            // Gli Allegati obbligatori alle quietanze sono ammessi solo se e' abilitata la
            // regola BR52.
            Boolean allegatiQuietanze = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR52_UPLOAD_ALLEGATI_QUIETANZA);
            result.setRegolaBR52attiva(allegatiQuietanze);

            // Gli Allegati di spesa sono ammessa solo se e' abilitata la regola BR51.
            Boolean allegatiSpesa = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR51_UPLOAD_ALLEGATI_SPESA);
            result.setRegolaBR51attiva(allegatiSpesa);

            // L'importo della richiesta di erogazione saldo è ammesso solo se e' abilitata
            // la regola BR15.
            Boolean importoRichiestoErogazioneSaldoAmmesso = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(
                    idUtente, idIride, idBandoLinea, RegoleConstants.BR15_ATTIVITA_RICHIESTA_EROGAZIONE_DISPONIBILE);
            result.setImportoRichiestoErogazioneSaldoAmmesso(importoRichiestoErogazioneSaldoAmmesso);

            Boolean isRegolaBR60attiva = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(
                idUtente, idIride, idBandoLinea, RegoleConstants.BR60_ATTIVA_DICHIARAZIONE_FINALE);
            result.setRegolaBR60attiva(isRegolaBR60attiva);

            Boolean isRegolaBR61attiva = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(
                idUtente, idIride, idBandoLinea, RegoleConstants.BR61_ATTIVA_DICHIARAZIONE_FINALE);
            result.setRegolaBR61attiva(isRegolaBR61attiva);

            Boolean isRegolaBR62attiva = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(
                idUtente, idIride, idBandoLinea, RegoleConstants.BR62_DICHIARAZIONE_CON_QUIETANZA_ZERO);
            result.setRegolaBR62attiva(isRegolaBR62attiva);

            DatiPiuGreenDTO datiPiuGreenDto;
            datiPiuGreenDto = gestioneDatiGeneraliBusinessImpl.findDatiPiuGreen(idUtente, idIride, idProgetto);
            if (datiPiuGreenDto != null) {
                result.setProgettoPiuGreen(datiPiuGreenDto.getRegolaBR44Attiva());
                result.setIdProgettoContributo(datiPiuGreenDto.getIdProgettoContributo());
            }

            String sql = fileSqlUtil.getQuery("ProgettoBandoLineaVO.sql");
            LOG.info(prf + "\n" + sql + " con id_progetto = " + idProgetto);
            ProgettoBandoLineaVO progettoBandoLineaVO = (ProgettoBandoLineaVO) getJdbcTemplate().queryForObject(
                    sql.toString(), new Object[] { idProgetto }, new BeanRowMapper(ProgettoBandoLineaVO.class));
            if (progettoBandoLineaVO != null) {
                result.setCodiceVisualizzatoProgetto(progettoBandoLineaVO.getCodiceVisualizzato());
            }

            result.setDimMaxFileFirmato(
                    new Long(decodificheDAOImpl.costante(Constants.COSTANTE_DIGITAL_SIGN_SIZE_LIMIT, true)));

            LOG.info(prf + result.toString());

        } catch (Exception e) {
            LOG.error(prf + " ERRORE durante la inizializzazione della dichiarazione di spesa: ", e);
            throw new DaoException("Errore durante la inizializzazione della dichiarazione di spesa.", e);
        } finally {
            LOG.info(prf + " END");
        }

        return result;
    }

    @Override
    public InizializzaIntegrazioneDiSpesaDTO inizializzaIntegrazioneDiSpesa(Long idProgetto, Long idUtente,
            String idIride) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::InizializzaInvioDichiarazioneDiSpesaDTO] ";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato.");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato.");
        }
        if (StringUtils.isBlank(idIride)) {
            throw new InvalidParameterException("idIride non valorizzato.");
        }

        InizializzaIntegrazioneDiSpesaDTO result = new InizializzaIntegrazioneDiSpesaDTO();
        try {

            DatiProgettoInizializzazioneDTO datiProgetto = completaDatiProgetto(idProgetto);
            LOG.info("\n\nIdProcesso = " + datiProgetto.getIdProcesso());
            if (datiProgetto != null) {
                result.setCodiceVisualizzatoProgetto(datiProgetto.getCodiceVisualizzato());
                /*
                 * result.setIdProcessoBandoLinea(datiProgetto.getIdProcesso()); if
                 * (datiProgetto.getIdTipoOperazione() != null)
                 * result.setIdTipoOperazioneProgetto(datiProgetto.getIdTipoOperazione().
                 * longValue());
                 */
            }

            LOG.info(prf + result.toString());

        } catch (Exception e) {
            LOG.error(prf + " ERRORE durante la inizializzazione della integrazione di spesa: ", e);
            throw new DaoException("Errore durante la inizializzazione della integrazione di spesa.", e);
        } finally {
            LOG.info(prf + " END");
        }

        return result;
    }

    private DatiProgettoInizializzazioneDTO completaDatiProgetto(Long idPrj) {
        String prf = "[DichiarazioneDiSpesaDAOImpl::completaDatiProgetto] ";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + " idPrj=" + idPrj);

        DatiProgettoInizializzazioneDTO dto = null;
        try {
            if (idPrj != null && idPrj.intValue() != 0) {
                String sql = fileSqlUtil.getQuery("ProgettoBandoLineaVO.sql");
                LOG.info(prf + "\n" + sql + " con id_progetto = " + idPrj);
                ProgettoBandoLineaVO progettoBandoLineaVO = (ProgettoBandoLineaVO) getJdbcTemplate().queryForObject(
                        sql.toString(), new Object[] { idPrj }, new BeanRowMapper(ProgettoBandoLineaVO.class));
                if (progettoBandoLineaVO != null) {
                    dto = new DatiProgettoInizializzazioneDTO();
                    dto.setCodiceVisualizzato(progettoBandoLineaVO.getCodiceVisualizzato());
                    dto.setIdProcesso(progettoBandoLineaVO.getIdProcesso());
                    if (progettoBandoLineaVO.getIdTipoOperazione() != null)
                        dto.setIdTipoOperazione(progettoBandoLineaVO.getIdTipoOperazione().longValue());
                }
            }
        } catch (Exception e) {
            LOG.error(prf + " Exception " + e.getMessage());
        }

        LOG.info(prf + " END");
        return dto;
    }

    @Override
    // Ex DichiarazioneDiSpesaBusinessImpl.findRappresentantiLegali()
    public Boolean getIsBeneficiarioPrivatoCittadino(Long idProgetto, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::getIsBeneficiarioPrivatoCittadino] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        // BeneficiarioVO beneficiario = new BeneficiarioVO();
        Boolean isPersinaFisica = null;

        try {

            Long idTipoAnagrafica = 1L;
            Long idTipoBeneficiario = 4L;

            String sql = "select count(id_soggetto) as value					\r\n"
                    + "from pbandi_r_soggetto_progetto 						\r\n"
                    + "where id_progetto = ?								\r\n"
                    + "and id_tipo_anagrafica = ?							\r\n"
                    + "and id_tipo_beneficiario != ?						\r\n"
                    + "and id_persona_fisica is not null 	 					";

            /*
             * MapSqlParameterSource param = new MapSqlParameterSource();
             * param.addValue("id_progetto", idProgetto, Types.NUMERIC);
             * param.addValue("id_tipo_anagrafica", idTipoAnagrafica, Types.NUMERIC);
             * param.addValue("id_tipo_beneficiario", idTipoBeneficiario, Types.NUMERIC);
             */

            Object[] par = { idProgetto, idTipoAnagrafica, idTipoBeneficiario };

            // getJdbcTemplate().queryForObject(sql, param, new
            // BeanRowMapper(Integer.class));

            logger.info(prf + "\n" + sql);
            Integer num = ((IntegerValue) getJdbcTemplate().queryForObject(sql, par,
                    new BeanRowMapper(IntegerValue.class))).getValue();

            if (num == null)
                isPersinaFisica = false;
            else if (num > 0)
                isPersinaFisica = true;
            // beneficiario.setIsPersonaFisica(true);
            else
                isPersinaFisica = false;
            // beneficiario.setIsPersonaFisica(false);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dei dati beneficirio: ", e);
            throw new DaoException(" ERRORE nella ricerca dei dati beneficirio:");
        } finally {
            LOG.info(prf + " END");
        }

        return isPersinaFisica;
        // return beneficiario;
    }

    public Boolean getIsBeneficiarioPrivatoCittadino(Long idProgetto) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::getIsBeneficiarioPrivatoCittadino] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + ";");

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }

        // BeneficiarioVO beneficiario = new BeneficiarioVO();
        Boolean isPersinaFisica = null;

        try {

            Long idTipoAnagrafica = 1L;
            Long idTipoBeneficiario = 4L;

            String sql = "select count(id_soggetto) as value					\r\n"
                    + "from pbandi_r_soggetto_progetto 						\r\n"
                    + "where id_progetto = ?								\r\n"
                    + "and id_tipo_anagrafica = ?							\r\n"
                    + "and id_tipo_beneficiario != ?						\r\n"
                    + "and id_persona_fisica is not null 	 					";

            /*
             * MapSqlParameterSource param = new MapSqlParameterSource();
             * param.addValue("id_progetto", idProgetto, Types.NUMERIC);
             * param.addValue("id_tipo_anagrafica", idTipoAnagrafica, Types.NUMERIC);
             * param.addValue("id_tipo_beneficiario", idTipoBeneficiario, Types.NUMERIC);
             */

            Object[] par = { idProgetto, idTipoAnagrafica, idTipoBeneficiario };

            // getJdbcTemplate().queryForObject(sql, param, new
            // BeanRowMapper(Integer.class));

            logger.info(prf + "\n" + sql);
            Integer num = ((IntegerValue) getJdbcTemplate().queryForObject(sql, par,
                    new BeanRowMapper(IntegerValue.class))).getValue();

            if (num == null)
                isPersinaFisica = false;
            else if (num > 0)
                isPersinaFisica = true;
            // beneficiario.setIsPersonaFisica(true);
            else
                isPersinaFisica = false;
            // beneficiario.setIsPersonaFisica(false);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dei dati beneficirio: ", e);
            throw new DaoException(" ERRORE nella ricerca dei dati beneficirio:");
        } finally {
            LOG.info(prf + " END");
        }

        return isPersinaFisica;
        // return beneficiario;
    }
    
    public Boolean isBottoneConsuntivoVisibile(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::isBottoneConsuntivoVisibile] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + ";");

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }

        Boolean isBottoneVisibile = null;

        try {
            String sql = 
            		
            	   "   SELECT COUNT (T.ID_DICHIARAZIONE_SPESA) \n"
            	 + "   FROM pbandi_t_dichiarazione_spesa t     \n"
            	 + "   WHERE t.id_progetto = ?";

             
            logger.info(prf + "\n" + sql);
            Integer num = getJdbcTemplate().queryForObject(sql, new Object[]{idProgetto}, Integer.class);
            isBottoneVisibile = num != null && num > 0;
            
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dei dati beneficirio: ", e);
            throw new DaoException(" ERRORE nella ricerca dei dati beneficirio:");
        } finally {
            LOG.info(prf + " END");
        }

        return isBottoneVisibile;
    }

    @Override
    // Ex DichiarazioneDiSpesaBusinessImpl.findRappresentantiLegali()
    public ArrayList<DecodificaDTO> rappresentantiLegali(Long idProgetto, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::rappresentantiLegali] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        ArrayList<DecodificaDTO> rappresentanti = new ArrayList<DecodificaDTO>();
        try {

            Long idSoggettoRappresentante = null;
            RappresentanteLegaleDTO[] rappresentantiDTO;
            rappresentantiDTO = dichiarazioneDiSpesaBusinessImpl.findRappresentantiLegali(idUtente, idIride, idProgetto,
                    idSoggettoRappresentante);

            if (rappresentantiDTO != null) {
                for (RappresentanteLegaleDTO rapp : rappresentantiDTO) {
                    DecodificaDTO rappresentante = new DecodificaDTO();
                    rappresentante.setId(rapp.getIdSoggetto());
                    rappresentante.setDescrizione(
                            rapp.getCognome() + " " + rapp.getNome() + " [" + rapp.getCodiceFiscaleSoggetto() + "]");
                    rappresentanti.add(rappresentante);
                }
            }

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dei rappresentanti legali: ", e);
            throw new DaoException(" ERRORE nella ricerca dei rappresentanti legali.");
        } finally {
            LOG.info(prf + " END");
        }

        return rappresentanti;
    }

    @Override
    // Ex DatiGeneraliAction.findDelegati()
    public ArrayList<DecodificaDTO> delegatiCombo(Long idProgetto, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::delegati] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        ArrayList<DecodificaDTO> result = new ArrayList<DecodificaDTO>();
        try {

            DelegatoDTO[] delegati;
            delegati = gestioneDatiGeneraliBusinessImpl.findDelegati(idUtente, idIride, idProgetto);

            for (DelegatoDTO d : delegati) {
                DecodificaDTO dec = new DecodificaDTO();
                dec.setId(d.getIdSoggetto());
                dec.setDescrizione(d.getCognome() + " " + d.getNome() + " - " + d.getCodiceFiscaleSoggetto());
                result.add(dec);
            }

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dei delegati: ", e);
            throw new DaoException(" ERRORE nella ricerca dei delegati.");
        } finally {
            LOG.info(prf + " END");
        }

        return result;
    }

    @Override
    // Ex DichiarazioneDiSpesaBusinessImpl.verificaDichiarazioneDiSpesa()
    // codiceTipoDichiarazioneDiSpesa : I (intermedia), F (finale), IN
    // (integrativa).
    public EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa(VerificaDichiarazioneDiSpesaRequest verificaDichiarazioneDiSpesaRequest, Long idUtente, String idIride) throws Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::verificaDichiarazioneDiSpesa] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idUtente = " + idUtente);

        if (verificaDichiarazioneDiSpesaRequest == null) {
            throw new InvalidParameterException("verificaDichiarazioneDiSpesaRequest non valorizzato");
        }
        LOG.info(prf + verificaDichiarazioneDiSpesaRequest.toString());

        Long idProgetto = verificaDichiarazioneDiSpesaRequest.getIdProgetto();
        Long idBandoLinea = verificaDichiarazioneDiSpesaRequest.getIdBandoLinea();
        Date dataLimiteDocumentiRendicontabili = verificaDichiarazioneDiSpesaRequest
                .getDataLimiteDocumentiRendicontabili();
        Long idSoggettoBeneficiario = verificaDichiarazioneDiSpesaRequest.getIdSoggettoBeneficiario();
        String codiceTipoDichiarazioneDiSpesa = verificaDichiarazioneDiSpesaRequest.getCodiceTipoDichiarazioneDiSpesa();

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idBandoLinea == null) {
            throw new InvalidParameterException("idBandoLinea non valorizzato");
        }
        if (idSoggettoBeneficiario == null) {
            throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        EsitoOperazioneVerificaDichiarazioneSpesa esito = new EsitoOperazioneVerificaDichiarazioneSpesa();

        // Alex: non ho capito a cosa serve nel vecchio, per ora lo commento.
        // boolean isRegolaCampoTaskAttivo =
        // profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
        // idBandoLinea, RegoleConstants.BR16_GESTIONE_CAMPO_TASK);

        DichiarazioneDiSpesaDTO dtoSrv = new DichiarazioneDiSpesaDTO();
        dtoSrv.setIdBandoLinea(idBandoLinea);
        dtoSrv.setIdProgetto(idProgetto);
        dtoSrv.setIdSoggetto(idSoggettoBeneficiario);
        dtoSrv.setDataFineRendicontazione(dataLimiteDocumentiRendicontabili);
        dtoSrv.setTipoDichiarazione(codiceTipoDichiarazioneDiSpesa);

        it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneVerificaDichiarazioneSpesa esitoPbandisrv = null;

        try {
            esitoPbandisrv = dichiarazioneDiSpesaBusinessImpl.verificaDichiarazioneDiSpesa(idUtente, idIride, dtoSrv);
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella verifica della dichiarazione di spesa: ", e);

        }
        ArrayList<String> messaggi = new ArrayList<String>();

        if (esitoPbandisrv != null) {
            esito.setEsito(esitoPbandisrv.getEsito());
            if (esitoPbandisrv.getMessaggi() != null) {
                messaggi = TraduttoreMessaggiPbandisrv.traduci(esitoPbandisrv.getMessaggi());
                messaggi.add(TraduttoreMessaggiPbandisrv.traduci(esitoPbandisrv.getMessage()));
                if (esito.getEsito() && messaggi.isEmpty()) {
                    messaggi.add(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
                }
            esito.setMessaggi(messaggi);
            }
            ArrayList<DocumentoDiSpesa> result = new ArrayList<DocumentoDiSpesa>();
            result = getElencoDocumentiDispesa(esitoPbandisrv.getDocumentiDiSpesa());


            for (DocumentoDiSpesa d : result)
                LOG.info(prf + "ID = " + d.getId() + "; TipologiaDocumento = " + d.getDescrizioneTipologiaDocumento() + "; NumeroDocumento = " + d.getNumeroDocumento() + "; Motivazione = " + d.getMotivazione());


            // Alex: 26/04/2022: committato per evitare errori in prod durante la verifica
            // di una DS.
            /*
             * Collections.sort(result, new Comparator<DocumentoDiSpesa>() { public int
             * compare(DocumentoDiSpesa o1, DocumentoDiSpesa o2) { if (o1.getMotivazione()
             * == null) return 1; if (o2.getMotivazione() == null) return -1; int i =
             * (o2.getMotivazione() .compareTo(o1 .getMotivazione())); return i; } });
             */

            esito.setDocumentiDiSpesa(result);
        }
        else{
            esito.setEsito(false);
        }

        LOG.info(prf + " END");
        return esito;
    }

    private ArrayList<DocumentoDiSpesa> getElencoDocumentiDispesa(DocumentoDiSpesaDTO[] listDocumentiSrv) {

        ArrayList<DocumentoDiSpesa> elenco = new ArrayList<DocumentoDiSpesa>();
        if (listDocumentiSrv != null) {

          for (DocumentoDiSpesaDTO objSrv : listDocumentiSrv) {
            DocumentoDiSpesa doc = new DocumentoDiSpesa();
            doc.setId(objSrv.getIdDocumentoDiSpesa());
            doc.setDescrizioneTipologiaDocumento(objSrv.getDescTipoDocumentoDiSpesa());
            doc.setDataDocumento(DateUtil.getDate(objSrv.getDataDocumentoDiSpesa()));
            doc.setNumeroDocumento(objSrv.getNumeroDocumentoDiSpesa());

            doc.setTask(objSrv.getTask());
            doc.setCodiceFiscaleFornitore(objSrv.getNomeFornitore());
            doc.setMotivazione(TraduttoreMessaggiPbandisrv.traduci(objSrv.getMotivazione()));
            doc.setTipoInvio(objSrv.getTipoInvio());
            doc.setFlagElettronico(objSrv.getFlagElettronico());
            doc.setIsAllegatiPresenti(objSrv.getAllegatiPresenti());
            elenco.add(doc);
          }
        }
        return elenco;
    }

    @Override
    // Ex DatiGeneraliAction.findDelegati()
    public String iban(Long idProgetto, Long idSoggettoBeneficiario, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::iban] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idSoggettoBeneficiario = " + idSoggettoBeneficiario
                + "; idUtente = " + idUtente);

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idSoggettoBeneficiario == null) {
            throw new InvalidParameterException("idSoggettoBeneficiario non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        String iban = "";
        try {

            CoordinateBancarieDTO cb;
            cb = gestioneDatiGeneraliBusinessImpl.getCoordinateBancarie(idUtente, idIride, idProgetto,
                    idSoggettoBeneficiario);

            iban = cb.getIban();

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dell'iban: ", e);
            throw new DaoException(" ERRORE nella ricerca dell'iban.");
        } finally {
            LOG.info(prf + " END");
        }

        return iban;
    }

    @Override
    // Ex DatiGeneraliAction.findTipoAllegati()
    // codiceTipoDichiarazioneDiSpesa = I (intermedia), IN (integrativa), F
    // (finale).
    // idDichiarazione può essere nullo.
    public ArrayList<TipoAllegatoDTO> tipoAllegati(Long idBandoLinea, String codiceTipoDichiarazioneDiSpesa,
            Long idDichiarazione, Long idProgetto, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::tipoAllegati] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idBandoLinea = " + idBandoLinea + "; codiceTipoDichiarazioneDiSpesa = "
                + codiceTipoDichiarazioneDiSpesa + "; idDichiarazione = " + idDichiarazione + "; idProgetto = "
                + idProgetto + "; idUtente = " + idUtente);

        if (idBandoLinea == null) {
            throw new InvalidParameterException("idBandoLinea non valorizzato");
        }
        if (StringUtil.isBlank(codiceTipoDichiarazioneDiSpesa)) {
            throw new InvalidParameterException("codiceTipoDichiarazioneDiSpesa non valorizzato");
        }
        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        ArrayList<TipoAllegatoDTO> result = new ArrayList<TipoAllegatoDTO>();
        try {

            String codTipoDocIndex;
            if (codiceTipoDichiarazioneDiSpesa.equalsIgnoreCase(Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE)) {
                codTipoDocIndex = "CFP";
            } else {
                codTipoDocIndex = "DS";
            }

            it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO[] tipoAllegati;
            tipoAllegati = gestioneDatiGeneraliBusinessImpl.findTipoAllegati(idUtente, idIride, idBandoLinea,
                    codTipoDocIndex, idDichiarazione, idProgetto);

            // Da dto di pbandisrv a dto di pbweb.
            for (it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO dtoPbandisrv : tipoAllegati) {
                result.add(beanUtil.transform(dtoPbandisrv, TipoAllegatoDTO.class));
            }

            for (TipoAllegatoDTO dto : result) {
                dto.setIdProgetto(idProgetto);
            }

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dei tipo allegati: ", e);
            throw new DaoException(" ERRORE nella ricerca dei tipo allegati.");
        } finally {
            LOG.info(prf + " END");
        }

        return result;
    }

    @Override
    // Servizio non più usato: il salvataggio dei tipoAllegato è stato incluso
    // nell'anteprima e nell'invio della DS.
    public EsitoDTO salvaTipoAllegati(ArrayList<TipoAllegatoDTO> listaTipoAllegati,
            String codiceTipoDichiarazioneDiSpesa, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::salvaTipoAllegati] ";
        LOG.info(prf + "BEGIN");

        if (StringUtil.isBlank(codiceTipoDichiarazioneDiSpesa)) {
            throw new InvalidParameterException("codiceTipoDichiarazioneDiSpesa non valorizzato");
        }

        EsitoDTO esito = new EsitoDTO();
        try {

            esito = eseguiSalvaTipoAllegati(listaTipoAllegati, codiceTipoDichiarazioneDiSpesa, idUtente, idIride);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dei tipo allegati: ", e);
            throw new DaoException(" ERRORE nella ricerca dei tipo allegati.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    // Ex DatiGeneraliAction.salvaListaAllegati()
    // codiceTipoDichiarazioneDiSpesa = I (intermedia), IN (integrativa), F
    // (finale).
    // Metodo chiamato sia nell'anteprima sia nell'invio di una DS.
    private EsitoDTO eseguiSalvaTipoAllegati(ArrayList<TipoAllegatoDTO> listaTipoAllegati,
            String codiceTipoDichiarazioneDiSpesa, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::eseguiSalvaTipoAllegati] ";
        LOG.info(prf + "BEGIN");

        EsitoDTO esito = new EsitoDTO();
        try {

            if (listaTipoAllegati == null || listaTipoAllegati.size() == 0) {
                LOG.info(prf + "listaTipoAllegati vuota: non faccio nulla.");
                esito.setEsito(true);
                return esito;
            }

            /*
             * String codTipoDocIndex = ""; if
             * (codiceTipoDichiarazioneDiSpesa.equalsIgnoreCase(Constants.
             * CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE)) codTipoDocIndex = "CFP"; else
             * codTipoDocIndex = "DS";
             */

            // Da dto di pbweb a dto di pbandisrv.
            it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO[] listaTipoAllegatiSrv;
            listaTipoAllegatiSrv = beanUtil.transform(listaTipoAllegati,
                    it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO.class);

            esito = dichiarazioneDiSpesaBusinessImpl.salvaTipoAllegati(idUtente, idIride, listaTipoAllegatiSrv);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dei tipo allegati: ", e);
            throw new DaoException(" ERRORE nella ricerca dei tipo allegati.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    // Trasforma un array da FileDTO in DocumentoAllegatoDTO.
    private ArrayList<DocumentoAllegatoDTO> popolaListaDocumentoAllegatoDTODaFileDTO(
            it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] files) {
        ArrayList<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
        if (!ObjectUtil.isEmpty(files)) {
            for (it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO file : files) {
                DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
                allegato.setId(file.getIdDocumentoIndex());
                allegato.setNome(file.getNomeFile());
                allegato.setDisassociabile(true);
                allegato.setSizeFile(file.getSizeFile());
                result.add(allegato);
            }
        }
        return result;
    }

    @Override
    // Ex ArchivioFileAction.getAllegatiDichiarazioneByIdProgetto()
    public ArrayList<DocumentoAllegatoDTO> allegatiDichiarazioneDiSpesaPerIdProgetto(
            String codiceTipoDichiarazioneDiSpesa, Long idProgetto, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::allegatiDichiarazioneDiSpesaPerIdProgetto] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "codiceTipoDichiarazioneDiSpesa = " + codiceTipoDichiarazioneDiSpesa + "; idProgetto = "
                + idProgetto + "; idUtente = " + idUtente);

        if (StringUtils.isBlank(codiceTipoDichiarazioneDiSpesa)) {
            throw new InvalidParameterException("codiceTipoDichiarazioneDiSpesa non valorizzato");
        }
        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        ArrayList<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
        try {

            it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] files;
//            ALLEGATI NON PIU ASSOCIATI ALLA CFP MA SOLO ALLA DS
//            if (Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE.equalsIgnoreCase(codiceTipoDichiarazioneDiSpesa)) {
//                files = archivioBusinessImpl.getFilesAssociatedComunicazioneFineProgByIdProgetto(idUtente, idIride,
//                        idProgetto);
//            } else {
                files = archivioBusinessImpl.getFilesAssociatedDichiarazioneByIdProgetto(idUtente, idIride, idProgetto);
//            }

            result = this.popolaListaDocumentoAllegatoDTODaFileDTO(files);
            /*
             * if (!ObjectUtil.isEmpty(files)) { for
             * (it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO file : files) {
             * DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
             * allegato.setId(file.getIdDocumentoIndex());
             * allegato.setNome(file.getNomeFile()); allegato.setDisassociabile(true);
             * allegato.setSizeFile(file.getSizeFile()); result.add(allegato); } }
             */

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca degli allegati della dichiarazione: ", e);
            throw new DaoException(" ERRORE nella ricerca degli allegati della dichiarazione.");
        } finally {
            LOG.info(prf + " END");
        }

        return result;
    }

    @Override
    // Data una DS, restituisce gli allegati della DS o della CFP (a seconda che la
    // DS sia non finale o finale)
    // e gli allegati delle eventuali integrazioni alla DS.
    // NOTA: forse va bene anche per progetti +GREEN, ignorando la parte delle
    // integrazioni: da verificare su un caso concreto.
    public AllegatiDichiarazioneDiSpesaDTO allegatiDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa, Long idProgetto,
            Long idUtente, String idIride) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::allegatiDichiarazioneDiSpesa] ";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa
                + "; idUtente = " + idUtente);

        if (idDichiarazioneDiSpesa == null) {
            throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
        }
        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato.");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato.");
        }

        AllegatiDichiarazioneDiSpesaDTO result = new AllegatiDichiarazioneDiSpesaDTO();
        try {
            // NON cerco piu gli allegati della CFP ma solo della DS

//            if (decodificheDAOImpl.dichiarazioneSpesaFinaleConCFP(idDichiarazioneDiSpesa)) {
//
//                LOG.info(prf + "Cerco l'id della CFP");
//                Long idCFP = decodificheDAOImpl.idComunicazioneFineProgetto(idProgetto);
//
//                LOG.info(prf + "Cerco gli allegati della CFP");
//                it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] allegati;
//                allegati = archivioBusinessImpl.getFilesAssociatedComunicazioneFineProg(idUtente, prf, idCFP,
//                        idProgetto);
//                result.setAllegati(this.popolaListaDocumentoAllegatoDTODaFileDTO(allegati));
//
//            } else {

                LOG.info(prf + "Cerco gli allegati della DS");
                it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] allegati;
                allegati = archivioBusinessImpl.getFilesAssociatedDichiarazione(idUtente, prf, idDichiarazioneDiSpesa,
                        idProgetto);
                result.setAllegati(this.popolaListaDocumentoAllegatoDTODaFileDTO(allegati));

//            }

            // Ex: ArchivioFileAction.getAllegatiIntegrazioniDS()
            // Restituisce le integrazioni con relativi allegati della DS in input.
            LOG.info(prf + "Cerco le integrazioni della DS");
            IntegrazioneSpesaDTO[] integrazioni;
            integrazioni = validazioneRendicontazioneBusinessImpl.findIntegrazioniSpesa(idUtente, idIride,
                    idDichiarazioneDiSpesa);

            ArrayList<DatiIntegrazioneDsDTO> datiIntegrazioneDs = new ArrayList<DatiIntegrazioneDsDTO>();
            for (IntegrazioneSpesaDTO dto : integrazioni) {
                DatiIntegrazioneDsDTO dati = new DatiIntegrazioneDsDTO();
                dati.setIdIntegrazioneSpesa(dto.getIdIntegrazioneSpesa().longValue());
                dati.setDataRichiesta(DateUtil.getDate(dto.getDataRichiesta()));
                dati.setDataInvio(DateUtil.getDate(dto.getDataInvio()));
                dati.setDescrizione(dto.getDescrizione());
                if (dto.getDataInvio() != null) {
                    LOG.info(prf + "Cerco gli allegati dell'integrazione " + dto.getIdIntegrazioneSpesa() + " della DS "
                            + dto.getIdDichiarazioneSpesa());
                    // DocumentoAllegato[] allegati =
                    // gestioneArchivioFileBusinessImpl.getAllegatiIntegrazioneDS(user,
                    // dto.getIdIntegrazioneSpesa());
                    it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] allegatiIntegrazioneSrv = archivioBusinessImpl
                            .getFilesAssociatedIntegrazioneDS(idUtente, idIride, dto.getIdIntegrazioneSpesa());
                    ArrayList<DocumentoAllegatoDTO> allegatiIntegrazione = this
                            .popolaListaDocumentoAllegatoDTODaFileDTO(allegatiIntegrazioneSrv);

                    dati.setAllegati(allegatiIntegrazione);
                }
                datiIntegrazioneDs.add(dati);
            }
            result.setIntegrazioni(datiIntegrazioneDs);

            LOG.info(prf + result.toString());

        } catch (Exception e) {
            LOG.error(prf + " ERRORE durante la ricerca degli allegati: ", e);
            throw new DaoException("Errore durante la ricerca degli allegati.", e);
        } finally {
            LOG.info(prf + " END");
        }

        return result;
    }
    @Override
    public AllegatiDichiarazioneDiSpesaDTO allegatiDichiarazioneDiSpesaIntegrazioni(Long idDichiarazioneDiSpesa, Long idProgetto, Long idUtente, String idIride) throws Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::allegatiDichiarazioneDiSpesaIntegrazioni] ";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa + "; idUtente = " + idUtente);

        if (idDichiarazioneDiSpesa == null) {
            throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
        }
        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato.");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato.");
        }

        AllegatiDichiarazioneDiSpesaDTO result = new AllegatiDichiarazioneDiSpesaDTO();
        try {
            // Se è una DS finale non cerco piu gli allegati della CFP ma sempre della DS           
//            if (decodificheDAOImpl.dichiarazioneSpesaFinaleConCFP(idDichiarazioneDiSpesa)) {
//                LOG.info(prf + "Cerco l'id della CFP");
//                Long idCFP = decodificheDAOImpl.idComunicazioneFineProgetto(idProgetto);
//
//                LOG.info(prf + "Cerco gli allegati della CFP");
//                it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] allegati;
//                allegati = archivioBusinessImpl.getFilesAssociatedComunicazioneFineProgNoIntegr(idUtente, prf, idCFP, idProgetto);
//                result.setAllegati(this.popolaListaDocumentoAllegatoDTODaFileDTO(allegati));
//            } else {
        	 // Se è una DS non finale: bisogna restituire gli allegati della DS
                LOG.info(prf + "Cerco gli allegati della DS");
                it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] allegati;
                allegati = archivioBusinessImpl.getFilesAssociatedDichiarazioneNoIntegr(idUtente, prf, idDichiarazioneDiSpesa, idProgetto);
                result.setAllegati(this.popolaListaDocumentoAllegatoDTODaFileDTO(allegati));
//            }

            //INTEGRAZIONI
            LOG.info(prf + "Cerco le integrazioni della DS");
            it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO[] integrazioni;
            integrazioni = validazioneRendicontazioneBusinessImpl.findIntegrazioniSpesa(idUtente, idIride, idDichiarazioneDiSpesa);

            //ALLEGATI INTEGRAZIONE
            ArrayList<DatiIntegrazioneDsDTO> datiIntegrazioneDs = new ArrayList<DatiIntegrazioneDsDTO>();
            for (it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IntegrazioneSpesaDTO dto : integrazioni) {
                DatiIntegrazioneDsDTO dati = new DatiIntegrazioneDsDTO();
                dati.setIdIntegrazioneSpesa(dto.getIdIntegrazioneSpesa());
                dati.setDataRichiesta(DateUtil.getDate(dto.getDataRichiesta()));
                dati.setDataInvio(DateUtil.getDate(dto.getDataInvio()));
                dati.setDescrizione(dto.getDescrizione());
                if (dto.getDataInvio() != null) {
                    LOG.info(prf + "Cerco gli allegati dell'integrazione " + dto.getIdIntegrazioneSpesa() + " della DS "
                            + dto.getIdDichiarazioneSpesa());
                    
                    String sql =  "SELECT \n" +
                            "documentoIndex.id_documento_index as idDocIndex, \n" +
                            "integrazione.id_integrazione_spesa AS idIntegrazioneSpesa,\n" +
                            "integrazione.data_invio AS dtInvioIntegrazione,\n" +
                            "documentoIndex.nome_file AS nomeFile,\n" +
                            "files.size_file AS sizeFile\n" +
                            "FROM PBANDI_T_FILE_ENTITA fileEntita\n" +
                            "JOIN PBANDI_T_DICHIARAZIONE_SPESA dichSpesa ON dichSpesa.id_dichiarazione_spesa = fileEntita.id_target\n" +
                            "JOIN PBANDI_T_INTEGRAZIONE_SPESA integrazione ON integrazione.id_integrazione_spesa = fileEntita.id_integrazione_spesa\n" +
                            "JOIN PBANDI_T_FILE files ON files.id_file = fileEntita.id_file\n" +
                            "JOIN PBANDI_T_DOCUMENTO_INDEX documentoIndex ON documentoIndex.id_documento_index = files.id_documento_index\n" +
                            "WHERE fileEntita.id_entita = 63 \n" +
                            "AND integrazione.id_integrazione_spesa = :idIntegrazione";
                    LOG.info(sql);

                    List<DocumentoAllegatoDTO> allegatiIntegrazione = getJdbcTemplate().query(
                           sql,
                            ps -> ps.setLong(1, dto.getIdIntegrazioneSpesa()),
                            (rs, rownum) -> {
                                DocumentoAllegatoDTO documentoAllegatoDTO = new DocumentoAllegatoDTO();

                                documentoAllegatoDTO.setId(rs.getLong("idDocIndex"));
                                documentoAllegatoDTO.setIdIntegrazioneSpesa(rs.getString("idIntegrazioneSpesa"));
                                documentoAllegatoDTO.setDtInvioIntegrazione(rs.getString("dtInvioIntegrazione"));
                                documentoAllegatoDTO.setNome(rs.getString("nomeFile"));
                                documentoAllegatoDTO.setSizeFile(rs.getLong("sizeFile"));

                                return documentoAllegatoDTO;
                            }
                    );
                    if(allegatiIntegrazione == null || allegatiIntegrazione.size() == 0) {
                    	it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO[] allegatiIntegrazioneSrv = archivioBusinessImpl
                                .getFilesAssociatedIntegrazioneDS(idUtente, idIride, dto.getIdIntegrazioneSpesa());
                        allegatiIntegrazione = this.popolaListaDocumentoAllegatoDTODaFileDTO(allegatiIntegrazioneSrv);
                    }

                    dati.setAllegati((ArrayList<DocumentoAllegatoDTO>) allegatiIntegrazione);
                }
                datiIntegrazioneDs.add(dati);
            }
            result.setIntegrazioni(datiIntegrazioneDs);

            LOG.info(prf + result);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE durante la ricerca degli allegati: ", e);
            throw new DaoException("Errore durante la ricerca degli allegati.", e);
        } finally {
            LOG.info(prf + " END");
        }

        return result;
    }

    @Override
    // Ex pbandiweb ArchivioFileAction.dissociate()
    // NOTA: se la dichiarazione di spesa non è stata ancora creata, passare
    // idDichiarazioneDiSpesa = null
    // al suo posto verrà usato l'idProgetto.
    public EsitoDTO disassociaAllegatoDichiarazioneDiSpesa(Long idDocumentoIndex, Long idDichiarazioneDiSpesa,
            Long idProgetto, String tipoDichiarazione, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::disassociaAllegatoDocumentoDiSpesa] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idDichiarazioneDiSpesa = " + idDichiarazioneDiSpesa
                + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);

        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato");
        }
        // if (idDichiarazioneDiSpesa == null) {
        // throw new InvalidParameterException("idDichiarazioneDiSpesa non
        // valorizzato");
        // }
        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        EsitoDTO esito = new EsitoDTO();
        try {

            String entita = "";
//            ALLEGATI NON PIU ASSOCIATI ALLA CFP; MA SEMPRE ALLA DS
//            if (tipoDichiarazione.equals("F")) {
//                entita = Constants.ENTITA_T_COMUNICAZ_FINE_PROG;
//            } else {
                entita = Constants.ENTITA_T_DICH_SPESA;
//            }
            BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
                    entita);
            if (idEntita == null)
                throw new DaoException("Id entita non trovato.");

            if (idDichiarazioneDiSpesa == null)
                idDichiarazioneDiSpesa = idProgetto;

            Esito esitoPbandisrv = archivioBusinessImpl.disassociateFile(idUtente, idIride, idDocumentoIndex,
                    idEntita.longValue(), idDichiarazioneDiSpesa, idProgetto);
            esito.setEsito(esitoPbandisrv.getEsito());
            esito.setMessaggio(esitoPbandisrv.getMessage());

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel disassociare gli allegati della dichiarazione di spesa: ", e);
            throw new DaoException(" ERRORE nel disassociare gli allegati della dichiarazione di spesa.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Ex pbandiweb ArchivioFileAction.dissociate()
    public EsitoDTO disassociaAllegatoIntegrazioneDiSpesa(Long idDocumentoIndex, Long idIntegrazioneDiSpesa,
            Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::disassociaAllegatoIntegrazioneDiSpesa] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idIntegrazioneDiSpesa = " + idIntegrazioneDiSpesa
                + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);

        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato");
        }
        if (idIntegrazioneDiSpesa == null) {
            throw new InvalidParameterException("idIntegrazioneDiSpesa non valorizzato");
        }
        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        EsitoDTO esito = new EsitoDTO();
        try {

            BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
                    Constants.ENTITA_T_INTEGRAZIONE_SPESA);
            if (idEntita == null)
                throw new DaoException("Id entita non trovato.");

            Esito esitoPbandisrv = archivioBusinessImpl.disassociateFile(idUtente, idIride, idDocumentoIndex,
                    idEntita.longValue(), idIntegrazioneDiSpesa, idProgetto);
            esito.setEsito(esitoPbandisrv.getEsito());
            esito.setMessaggio(esitoPbandisrv.getMessage());

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel disassociare gli allegati della integrazione di spesa: ", e);
            throw new DaoException(" ERRORE nel disassociare gli allegati della integrazione di spesa.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Associa gli allegati ad una dichiarazione di spesa.
    // NOTA: se la dichiarazione di spesa non è stata ancora creata, passare
    // idDichiarazioneDiSpesa = null
    // al suo posto verrà usato l'idProgetto.
    public EsitoAssociaFilesDTO associaAllegatiADichiarazioneDiSpesa(AssociaFilesRequest associaFilesRequest,
            Long idUtente) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::associaAllegatiADichiarazioneDiSpesa] ";
        LOG.info(prf + "BEGIN");

        EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
        try {

            BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
                    Constants.ENTITA_T_DICH_SPESA);

            if (associaFilesRequest.getIdTarget() == null)
                associaFilesRequest.setIdTarget(associaFilesRequest.getIdProgetto());

            esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita.longValue(), idUtente);
            LOG.info(prf + esito.toString());

        } catch (InvalidParameterException e) {
            LOG.error(prf + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel associare allegati alla dichiarazione di spesa: ", e);
            throw new DaoException(" ERRORE nel associare alla dichiarazione di spesa.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Associa gli allegati ad una dichiarazione di spesa FINALE: l'entità sarà
    // PBANDI_T_COMUNICAZ_FINE_PROG.
    // NOTA: se la dichiarazione di spesa non è stata ancora creata, passare
    // idDichiarazioneDiSpesa = null
    // al suo posto verrà usato l'idProgetto.
    public EsitoAssociaFilesDTO associaAllegatiADichiarazioneDiSpesaFinale(AssociaFilesRequest associaFilesRequest,
            Long idUtente) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::associaAllegatiADichiarazioneDiSpesa] ";
        LOG.info(prf + "BEGIN");

        EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
        try {

            BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
                    Constants.ENTITA_T_DICH_SPESA);

            if (associaFilesRequest.getIdTarget() == null)
                associaFilesRequest.setIdTarget(associaFilesRequest.getIdProgetto());

            esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita.longValue(), idUtente);
            LOG.info(prf + esito.toString());

        } catch (InvalidParameterException e) {
            LOG.error(prf + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel associare allegati alla dichiarazione finale di spesa: ", e);
            throw new DaoException(" ERRORE nel associare alla dichiarazione finale di spesa.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Associa gli allegati ad una integrazione di spesa.
    public EsitoAssociaFilesDTO associaAllegatiAIntegrazioneDiSpesa(AssociaFilesRequest associaFilesRequest,
            Long idUtente) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::associaAllegatiADichiarazioneDiSpesa] ";
        LOG.info(prf + "BEGIN");

        EsitoAssociaFilesDTO esito = new EsitoAssociaFilesDTO();
        try {

            BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
                    Constants.ENTITA_T_INTEGRAZIONE_SPESA);

            if (associaFilesRequest.getIdTarget() == null)
                associaFilesRequest.setIdTarget(associaFilesRequest.getIdProgetto());

            esito = archivioFileDAOImpl.associaFiles(associaFilesRequest, idEntita.longValue(), idUtente);
            LOG.info(prf + esito.toString());

        } catch (InvalidParameterException e) {
            LOG.error(prf + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel associare allegati alla integrazione di spesa: ", e);
            throw new DaoException(" ERRORE nel associare alla integrazione di spesa.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Ex ReportPdfAction.anteprima()
    @Transactional(rollbackFor = { Exception.class })
    public EsitoOperazioneAnteprimaDichiarazioneSpesa anteprimaDichiarazioneDiSpesa(
            AnteprimaDichiarazioneDiSpesaRequest request, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::anteprimaDichiarazioneDiSpesa] ";
        LOG.info(prf + "BEGIN");

        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }
        if (request == null) {
            throw new InvalidParameterException("request non valorizzato");
        }
        LOG.info(prf + request.toString());

        if (request.getIdBandoLinea() == null) {
            throw new InvalidParameterException("IdBandoLinea non valorizzato");
        }
        if (request.getIdProgetto() == null) {
            throw new InvalidParameterException("IdProgetto non valorizzato");
        }
        if (request.getIdSoggetto() == null) {
            throw new InvalidParameterException("IdSoggetto non valorizzato");
        }
        if (request.getIdSoggettoBeneficiario() == null) {
            throw new InvalidParameterException("IdSoggettoBeneficiario non valorizzato");
        }
        if (request.getDataLimiteDocumentiRendicontabili() == null) {
            throw new InvalidParameterException("DataLimiteDocumentiRendicontabili non valorizzato");
        }
        if (StringUtil.isBlank(request.getCodiceTipoDichiarazioneDiSpesa())) {
            throw new InvalidParameterException("CodiceTipoDichiarazioneDiSpesa non valorizzato");
        }
        if (request.getIdRappresentanteLegale() == null) {
            throw new InvalidParameterException("IdRappresentanteLegale non valorizzato");
        }

        EsitoOperazioneAnteprimaDichiarazioneSpesa esito = new EsitoOperazioneAnteprimaDichiarazioneSpesa();
        try {

            // Salva l'elenco dei tipoAllegato prima di procedere con l'enteprima.
            EsitoDTO esitoTipoAllegati = eseguiSalvaTipoAllegati(request.getListaTipoAllegati(),
                    request.getCodiceTipoDichiarazioneDiSpesa(), idUtente, idIride);
            if (esitoTipoAllegati == null || !esitoTipoAllegati.getEsito()) {
                throw new Exception("Errore nel salvataggio dei tipo allegati.");
            }

            Long idRappresentanteLegale = request.getIdRappresentanteLegale();
            Long idProgetto = request.getIdProgetto();

            boolean isPrivatoCittadino = this.getIsBeneficiarioPrivatoCittadino(idProgetto, idUtente, idIride);
            RappresentanteLegaleDTO rappresentante = null;

            if (isPrivatoCittadino) {

                Long idSoggettoBeneficiario = idRappresentanteLegale;

                // Recupera i dati del privato cittadino e lo mette nella variabile
                // rappresenatnti legali
                rappresentante = getPrivatoCittadinoForRappresentanteLegale(idSoggettoBeneficiario);
                IndirizzoPrivatoCittadino indirizzoPrivatoCittadino = getIndirizzoPrivatoCittadino(
                        idSoggettoBeneficiario, idProgetto);
                if (indirizzoPrivatoCittadino != null) {
                    rappresentante.setIndirizzoResidenza(indirizzoPrivatoCittadino.getDescIndirizzo());
                    rappresentante.setCapResidenza(indirizzoPrivatoCittadino.getCap());
                    rappresentante.setIdProvinciaResidenza(indirizzoPrivatoCittadino.getIdProvincia());
                    rappresentante.setIdComuneResidenza(indirizzoPrivatoCittadino.getIdComune());
                }

            }

            else {

                // Recupera i dati del Rappresentante Legale (se il servizio ne restituisce
                // molti, prende il primo).
                RappresentanteLegaleDTO[] lista = null;
                lista = dichiarazioneDiSpesaBusinessImpl.findRappresentantiLegali(idUtente, idIride, idProgetto,
                        idRappresentanteLegale);
                if (lista != null && lista.length > 0) {
                    rappresentante = lista[0];
                }
            }

            /*
             * it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.
             * RappresentanteLegaleDTO[] lista = null; lista =
             * dichiarazioneDiSpesaBusinessImpl.findRappresentantiLegali(idUtente, idIride,
             * request.getIdProgetto(), request.getIdRappresentanteLegale());
             * 
             * it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.
             * RappresentanteLegaleDTO rappresentante = null; if (lista != null &&
             * lista.length > 0) { rappresentante = lista[0]; }
             */

            DichiarazioneDiSpesaDTO dichSpesaDto;
            dichSpesaDto = popolaDichiarazioneDiSpesaDTO(request);

            boolean isFinale = (Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE
                    .equalsIgnoreCase(request.getCodiceTipoDichiarazioneDiSpesa()));

            ComunicazioneFineProgettoDTO cfpDto = null;
            if (isFinale) {
                cfpDto = popolaComunicazioneFineProgettoDTO(request);
            }

            it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneAnteprimaDichiarazioneSpesa esitoSrv;
            esitoSrv = dichiarazioneDiSpesaBusinessImpl.anteprimaDichiarazioneDiSpesa(idUtente, idIride, dichSpesaDto,
                    rappresentante, request.getIdDelegato(), cfpDto);

            esito.setEsito(esitoSrv.getEsito());
            if (esito.getEsito()) {
                esito.setMsg(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
                esito.setNomeFile(esitoSrv.getNomeFile());
                esito.setPdfBytes(esitoSrv.getPdfBytes());
            } else {
                esito.setMsg(TraduttoreMessaggiPbandisrv.traduci(esitoSrv.getMsg()));
            }
            LOG.info("Esito = " + esito.getEsito() + "; nome file = " + esito.getNomeFile() + "; pdfBytes = "
                    + ((esito.getPdfBytes() == null) ? "NULL" : esito.getPdfBytes().length));

            // Angular gestisce solo l'array di byte, quindi
            // EsitoOperazioneAnteprimaDichiarazioneSpesa è inutile.
            if (esitoSrv.getPdfBytes() == null || esitoSrv.getPdfBytes().length == 0) {
                throw new Exception("Errore nella creazione del pdf.");
            }

        } catch (InvalidParameterException e) {
            LOG.error(prf + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella generazione dell'anteprima: ", e);
            throw new DaoException(" ERRORE nella generazione dell'anteprima.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Ex ReportPdfAction.anteprima()
    @Transactional(rollbackFor = { Exception.class })
    public EsitoOperazioneAnteprimaDichiarazioneSpesa anteprimaDichiarazioneDiSpesaCultura(
        AnteprimaDichiarazioneDiSpesaCulturaRequest request, Long idUtente, String idIride)
        throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::anteprimaDichiarazioneDiSpesaCultura] ";
        LOG.info(prf + "BEGIN");

        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }
        if (request == null) {
            throw new InvalidParameterException("request non valorizzato");
        }
        LOG.info(prf + request.toString());

        if (request.getIdBandoLinea() == null) {
            throw new InvalidParameterException("IdBandoLinea non valorizzato");
        }
        if (request.getIdProgetto() == null) {
            throw new InvalidParameterException("IdProgetto non valorizzato");
        }
        if (request.getIdSoggetto() == null) {
            throw new InvalidParameterException("IdSoggetto non valorizzato");
        }
        if (request.getIdSoggettoBeneficiario() == null) {
            throw new InvalidParameterException("IdSoggettoBeneficiario non valorizzato");
        }
        if (request.getDataLimiteDocumentiRendicontabili() == null) {
            throw new InvalidParameterException("DataLimiteDocumentiRendicontabili non valorizzato");
        }
        if (StringUtil.isBlank(request.getCodiceTipoDichiarazioneDiSpesa())) {
            throw new InvalidParameterException("CodiceTipoDichiarazioneDiSpesa non valorizzato");
        }
        if (request.getIdRappresentanteLegale() == null) {
            throw new InvalidParameterException("IdRappresentanteLegale non valorizzato");
        }
        if (request.getAllegatiCultura() == null) {
            throw new InvalidParameterException("AllegatiCultura non valorizzato");
        }

        EsitoOperazioneAnteprimaDichiarazioneSpesa esito = new EsitoOperazioneAnteprimaDichiarazioneSpesa();
        try {

            // Salva l'elenco dei tipoAllegato prima di procedere con l'enteprima.
            EsitoDTO esitoTipoAllegati = eseguiSalvaTipoAllegati(request.getListaTipoAllegati(),
                request.getCodiceTipoDichiarazioneDiSpesa(), idUtente, idIride);
            if (esitoTipoAllegati == null || !esitoTipoAllegati.getEsito()) {
                throw new Exception("Errore nel salvataggio dei tipo allegati.");
            }

            Long idRappresentanteLegale = request.getIdRappresentanteLegale();
            Long idProgetto = request.getIdProgetto();

            boolean isPrivatoCittadino = this.getIsBeneficiarioPrivatoCittadino(idProgetto, idUtente, idIride);
            RappresentanteLegaleDTO rappresentante = null;

            if (isPrivatoCittadino) {

                // Recupera i dati del privato cittadino e lo mette nella variabile
                // rappresenatnti legali
                rappresentante = getPrivatoCittadinoForRappresentanteLegale(idRappresentanteLegale);
                IndirizzoPrivatoCittadino indirizzoPrivatoCittadino = getIndirizzoPrivatoCittadino(
                    idRappresentanteLegale, idProgetto);
                if (indirizzoPrivatoCittadino != null) {
                    rappresentante.setIndirizzoResidenza(indirizzoPrivatoCittadino.getDescIndirizzo());
                    rappresentante.setCapResidenza(indirizzoPrivatoCittadino.getCap());
                    rappresentante.setIdProvinciaResidenza(indirizzoPrivatoCittadino.getIdProvincia());
                    rappresentante.setIdComuneResidenza(indirizzoPrivatoCittadino.getIdComune());
                }

            }

            else {

                // Recupera i dati del Rappresentante Legale (se il servizio ne restituisce
                // molti, prende il primo).
                RappresentanteLegaleDTO[] lista = null;
                lista = dichiarazioneDiSpesaBusinessImpl.findRappresentantiLegali(idUtente, idIride, idProgetto,
                    idRappresentanteLegale);
                if (lista != null && lista.length > 0) {
                    rappresentante = lista[0];
                }
            }

            /*
             * it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.
             * RappresentanteLegaleDTO[] lista = null; lista =
             * dichiarazioneDiSpesaBusinessImpl.findRappresentantiLegali(idUtente, idIride,
             * request.getIdProgetto(), request.getIdRappresentanteLegale());
             *
             * it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.
             * RappresentanteLegaleDTO rappresentante = null; if (lista != null &&
             * lista.length > 0) { rappresentante = lista[0]; }
             */

            DichiarazioneDiSpesaDTO dichSpesaDto;
            dichSpesaDto = popolaDichiarazioneDiSpesaDTO(request);

            boolean isFinale = (Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE
                .equalsIgnoreCase(request.getCodiceTipoDichiarazioneDiSpesa()));

            ComunicazioneFineProgettoDTO cfpDto = null;
            if (isFinale) {
                cfpDto = popolaComunicazioneFineProgettoDTO(request);
            }

            it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneAnteprimaDichiarazioneSpesa esitoSrv;
            esitoSrv = dichiarazioneDiSpesaBusinessImpl.anteprimaDichiarazioneDiSpesaCultura(idUtente, idIride, dichSpesaDto,
                rappresentante, request.getIdDelegato(), cfpDto, request.getAllegatiCultura());

            esito.setEsito(esitoSrv.getEsito());
            if (esito.getEsito()) {
                esito.setMsg(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
                esito.setNomeFile(esitoSrv.getNomeFile());
                esito.setPdfBytes(esitoSrv.getPdfBytes());
            } else {
                esito.setMsg(TraduttoreMessaggiPbandisrv.traduci(esitoSrv.getMsg()));
            }
            LOG.info("Esito = " + esito.getEsito() + "; nome file = " + esito.getNomeFile() + "; pdfBytes = "
                + ((esito.getPdfBytes() == null) ? "NULL" : esito.getPdfBytes().length));

            // Angular gestisce solo l'array di byte, quindi
            // EsitoOperazioneAnteprimaDichiarazioneSpesa è inutile.
            if (esitoSrv.getPdfBytes() == null || esitoSrv.getPdfBytes().length == 0) {
                throw new Exception("Errore nella creazione del pdf.");
            }

        } catch (InvalidParameterException e) {
            LOG.error(prf + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella generazione dell'anteprima: ", e);
            throw new DaoException(" ERRORE nella generazione dell'anteprima.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Crea e salva il pdf di una nuova Dichiarazione di Spesa.
    // Ex CPBEPDichiarazioneDiSpesa.goToCreaDichiarazioneSpesa()
    @Transactional(rollbackFor = {Exception.class})
    public EsitoOperazioneInviaDichiarazioneDTO inviaDichiarazioneDiSpesa(MultipartFormDataInput multipartFormData,
                                                                          Long idUtente, String idIride) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::inviaDichiarazioneDiSpesa] ";
        LOG.info(prf + "BEGIN");
        System.gc();

        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }
        if (multipartFormData == null) {
            throw new InvalidParameterException("multipartFormData non valorizzato");
        }

        // Legge i parametri dal multipart, eccetto il file della relazione tecnica.
        Long idBandoLinea = multipartFormData.getFormDataPart("idBandoLinea", Long.class, null);
        Long idProgetto = multipartFormData.getFormDataPart("idProgetto", Long.class, null);
        Long idProgettoContributoPiuGreen = multipartFormData.getFormDataPart("idProgettoContributoPiuGreen",
            Long.class, null); // non obbligatorio.
        Long idSoggetto = multipartFormData.getFormDataPart("idSoggetto", Long.class, null);
        Long idSoggettoBeneficiario = multipartFormData.getFormDataPart("idSoggettoBeneficiario", Long.class, null);
        Long idRappresentanteLegale = multipartFormData.getFormDataPart("idRappresentanteLegale", Long.class, null);
        Long idDelegato = multipartFormData.getFormDataPart("idDelegato", Long.class, null); // non obbligatorio.
        String codiceTipoDichiarazioneDiSpesa = multipartFormData.getFormDataPart("codiceTipoDichiarazioneDiSpesa",
            String.class, null);
        String note = multipartFormData.getFormDataPart("note", String.class, null);
        Double importoRichiestaSaldo = multipartFormData.getFormDataPart("importoRichiestaSaldo", Double.class, null);
        Boolean isBR58 = multipartFormData.getFormDataPart("isBR58", Boolean.class, null);
        DeclaratoriaDTO declaratoriaDTO = new DeclaratoriaDTO();
        String stringaDeclaratoria = multipartFormData.getFormDataPart("allegatiCultura", String.class, null);
        if (!StringUtil.isBlank(stringaDeclaratoria)) {
            ObjectMapper mapper = new ObjectMapper();
            declaratoriaDTO = mapper.readValue(stringaDeclaratoria, new TypeReference<DeclaratoriaDTO>() {
            });
        }
        boolean isBandoCultura = isBandoCultura(idBandoLinea);
        // La data arriva come stringa "dd/MM/yyyy".
        String stringDataLimiteDocumentiRendicontabili = multipartFormData
            .getFormDataPart("dataLimiteDocumentiRendicontabili", String.class, null);
        stringDataLimiteDocumentiRendicontabili = DateUtil.getData();
        Date dataLimiteDocumentiRendicontabili = DateUtil.getDate(stringDataLimiteDocumentiRendicontabili);

        // Legge la relazione tecnica dal multipart; non obbligatoria.
        String nomeFileRelazioneTecnica = null;
        byte[] bytesRelazioneTecnica = null;
        Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
        List<InputPart> listInputPart = map.get("fileRelazioneTecnica");
        if (listInputPart != null && listInputPart.size() > 0) {
            ArrayList<FileDTO> listFileDTO = FileUtil.leggiFilesDaMultipart(listInputPart, null);
            if (listFileDTO != null && listFileDTO.size() > 0) {
                nomeFileRelazioneTecnica = listFileDTO.get(0).getNomeFile();
                bytesRelazioneTecnica = listFileDTO.get(0).getBytes();
            }
        }

        // Legge la lista dei tipo allegati dal multipart; non obbligatoria.
        ArrayList<TipoAllegatoDTO> listaTipoAllegati = new ArrayList<TipoAllegatoDTO>();
        String stringa = multipartFormData.getFormDataPart("listaTipoAllegati", String.class, null);
        if (!StringUtil.isBlank(stringa)) {
            ObjectMapper mapper = new ObjectMapper();
            List<TipoAllegatoDTO> lista = mapper.readValue(stringa, new TypeReference<List<TipoAllegatoDTO>>() {
            });
            listaTipoAllegati = (ArrayList<TipoAllegatoDTO>) lista;
        }

        // Un po' di log.
        LOG.info(prf + "input idBandoLinea = " + idBandoLinea);
        LOG.info(prf + "input idProgetto = " + idProgetto);
        LOG.info(prf + "input idProgettoContributoPiuGreen = " + idProgettoContributoPiuGreen);
        LOG.info(prf + "input idSoggetto = " + idSoggetto);
        LOG.info(prf + "input idRappresentanteLegale = " + idRappresentanteLegale);
        LOG.info(prf + "input idDelegato = " + idDelegato);
        LOG.info(prf + "input dataLimiteDocumentiRendicontabili stringa = " + stringDataLimiteDocumentiRendicontabili);
        LOG.info(prf + "input dataLimiteDocumentiRendicontabili date = " + dataLimiteDocumentiRendicontabili);
        LOG.info(prf + "input codiceTipoDichiarazioneDiSpesa = " + codiceTipoDichiarazioneDiSpesa);
        LOG.info(prf + "input nomeFileRelazioneTecnica = " + nomeFileRelazioneTecnica);
        LOG.info(prf + "input importoRichiestaSaldo = " + importoRichiestaSaldo);
        LOG.info(prf + "input note = " + note);
        if (bytesRelazioneTecnica == null)
            LOG.info(prf + "input bytesRelazioneTecnica = NULL");
        else
            LOG.info(prf + "input bytesRelazioneTecnica.length = " + bytesRelazioneTecnica.length);
        if (listaTipoAllegati == null) {
            LOG.info(prf + "input listaTipoAllegati = NULL");
        } else {
            LOG.info(prf + "input listaTipoAllegati.size = " + listaTipoAllegati.size());
            for (TipoAllegatoDTO dto : listaTipoAllegati)
                LOG.info(prf + "   input TipoAllegato: " + dto.getFlagAllegato() + " - " + dto.getDescTipoAllegato());
        }

        // Verifica campi obbligatori.
        if (idBandoLinea == null) {
            throw new InvalidParameterException("IdBandoLinea non valorizzato");
        }
        if (idProgetto == null) {
            throw new InvalidParameterException("IdProgetto non valorizzato");
        }
        if (idSoggetto == null) {
            throw new InvalidParameterException("IdSoggetto non valorizzato");
        }
        if (idRappresentanteLegale == null) {
            throw new InvalidParameterException("idRappresentanteLegale non valorizzato");
        }
        if (dataLimiteDocumentiRendicontabili == null) {
            throw new InvalidParameterException("dataLimiteDocumentiRendicontabili non valorizzato");
        }
        if (StringUtil.isBlank(codiceTipoDichiarazioneDiSpesa)) {
            throw new InvalidParameterException("CodiceTipoDichiarazioneDiSpesa non valorizzato");
        }
        if (!StringUtil.isBlank(nomeFileRelazioneTecnica)
            && (bytesRelazioneTecnica == null || bytesRelazioneTecnica.length == 0)) {
            throw new InvalidParameterException("BytesRelazioneTecnica non valorizzato");
        }
        if (bytesRelazioneTecnica != null && bytesRelazioneTecnica.length > 0
            && StringUtil.isBlank(nomeFileRelazioneTecnica)) {
            throw new InvalidParameterException("NomeFileRelazioneTecnica non valorizzato");
        }

        EsitoOperazioneInviaDichiarazioneDTO esito = new EsitoOperazioneInviaDichiarazioneDTO();
        esito.setEsito(true);
        try {

            // Salva l'elenco dei tipoAllegato prima di procedere con l'enteprima.
            EsitoDTO esitoTipoAllegati = eseguiSalvaTipoAllegati(listaTipoAllegati, codiceTipoDichiarazioneDiSpesa,
                idUtente, idIride);
            if (esitoTipoAllegati == null || !esitoTipoAllegati.getEsito()) {
                throw new Exception("Errore nel salvataggio dei tipo allegati.");
            }

            // controllo che sia privato cittadino e cerco nella tabella adeguata

            boolean isPrivatoCittadino = this.getIsBeneficiarioPrivatoCittadino(idProgetto, idUtente, idIride);
            RappresentanteLegaleDTO rappresentante = null;

            if (isPrivatoCittadino) {
                // Recupera i dati del privato cittadino e lo mette nella variabile
                // rappresenatnti legali
                rappresentante = getPrivatoCittadinoForRappresentanteLegale(idSoggettoBeneficiario);
                IndirizzoPrivatoCittadino indirizzoPrivatoCittadino = getIndirizzoPrivatoCittadino(
                    idSoggettoBeneficiario, idProgetto);
                if (indirizzoPrivatoCittadino != null) {
                    rappresentante.setIndirizzoResidenza(indirizzoPrivatoCittadino.getDescIndirizzo());
                    rappresentante.setCapResidenza(indirizzoPrivatoCittadino.getCap());
                    rappresentante.setIdProvinciaResidenza(indirizzoPrivatoCittadino.getIdProvincia());
                    rappresentante.setIdComuneResidenza(indirizzoPrivatoCittadino.getIdComune());
                }

            } else {
                // Recupera i dati del Rappresentante Legale (se il servizio ne restituisce
                // molti, prende il primo).
                RappresentanteLegaleDTO[] lista = null;
                lista = dichiarazioneDiSpesaBusinessImpl.findRappresentantiLegali(idUtente, idIride, idProgetto,
                    idRappresentanteLegale);
                if (lista != null && lista.length > 0) {
                    rappresentante = lista[0];
                }
            }

            DichiarazioneDiSpesaDTO dichSpesaDto;
            dichSpesaDto = new DichiarazioneDiSpesaDTO();
            dichSpesaDto.setIdBandoLinea(idBandoLinea);
            dichSpesaDto.setIdProgetto(idProgetto);
            dichSpesaDto.setIdSoggetto(idSoggettoBeneficiario);
            dichSpesaDto.setDataFineRendicontazione(dataLimiteDocumentiRendicontabili);
            dichSpesaDto.setTipoDichiarazione(codiceTipoDichiarazioneDiSpesa);
            dichSpesaDto.setIdProgettoContributoPiuGreen(idProgettoContributoPiuGreen);

            RelazioneTecnicaDTO relazTecnica = null;
            if (!StringUtil.isBlank(nomeFileRelazioneTecnica)) {
                relazTecnica = new RelazioneTecnicaDTO();
                relazTecnica.setByteAllegato(bytesRelazioneTecnica);
                relazTecnica.setNomeFile(nomeFileRelazioneTecnica);
            }

            boolean isFinale = (Constants.CODICE_TIPO_DICHIARAZIONE_DI_SPESA_FINALE
                .equalsIgnoreCase(codiceTipoDichiarazioneDiSpesa));

            ComunicazioneFineProgettoDTO cfpDto = null;
            if (isFinale) {
                // cfpDto = popolaComunicazioneFineProgettoDTO(request);
                cfpDto = new ComunicazioneFineProgettoDTO();
                cfpDto.setIdDelegato(idDelegato);
                cfpDto.setIdProgetto(idProgetto);
                cfpDto.setIdProgettoContributoPiuGreen(idProgettoContributoPiuGreen);
                cfpDto.setIdRappresentanteLegale(idRappresentanteLegale);
                cfpDto.setIdSoggettoBeneficiario(idSoggettoBeneficiario);
                cfpDto.setImportoRichiestaSaldo(importoRichiestaSaldo);
                cfpDto.setNote(note);
                // Campi che non servono.
                cfpDto.setCfBeneficiario(null); // FORSE NON SERVE, PER IL MOMENTO LO LASCIO A NULL.
                cfpDto.setCodiceProgetto(null);
            }

            // Nota: String codUtente e IstanzaAttivitaDTO istanzaAttivita non sono usati.
            String codUtente = null;
            IstanzaAttivitaDTO istanza = null;

            EsitoOperazioneInviaDichiarazione esitoSrv;
            esitoSrv = dichiarazioneDiSpesaBusinessImpl.inviaDichiarazioneDiSpesa(idUtente, idIride, dichSpesaDto,
                codUtente, istanza, rappresentante, idDelegato, relazTecnica, cfpDto, declaratoriaDTO);
            System.gc();

            esito.setEsito(esitoSrv.getEsito());
            if (esito.getEsito()) {

                //UPDATE R PROGETTO ITER CON ID DICHIARAZIONE
                if (isBR58 != null && isBR58.equals(Boolean.TRUE)) {
                    if (aggiornaProgettoIter(idProgetto, esitoSrv.getDichiarazioneDTO().getIdDichiarazioneSpesa()).equals(Boolean.TRUE)) {
                        esito.setMsg(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
                    } else {
                        esito.setEsito(Boolean.FALSE);
                        esito.setMsg("Errore in fase di aggiornamento della fase del cronoprogramma.");
                    }
                } else {
                    esito.setMsg(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
                }
            } else {
                esito.setMsg(TraduttoreMessaggiPbandisrv.traduci(esitoSrv.getMsg()));
            }
            LOG.info(prf + "Esito = " + esito.getEsito() + "; msg = " + esito.getMsg());

            // NOTA: nel caso di DS Finale, esitoSrv.IdDichiarazioneSpesa contiene l'id
            // della CFP.
            // IdDichiarazioneSpesa serve ad Angular per ricostruire il nome del pdf
            // che viene scritto nella pagina di upload del file firmato.

            if (esitoSrv.getDichiarazioneDTO() != null) {
                esito.setNomeFileDichiarazioneSpesa(esitoSrv.getDichiarazioneDTO().getNomeFile());
                esito.setIdDocumentoIndex(esitoSrv.getDichiarazioneDTO().getIdDocIndex());
                esito.setIdDichiarazioneSpesa(esitoSrv.getDichiarazioneDTO().getIdDichiarazioneSpesa());
                LOG.info(prf + "IdDocIndex = " + esito.getIdDocumentoIndex() + "; NoneFile = "
                    + esito.getNomeFileDichiarazioneSpesa() + "; IdDichiarazioneSpesa = "
                    + esito.getIdDichiarazioneSpesa());
            }

            if (esitoSrv.getDichiarazionePiuGreenDTO() != null) {
                esito.setNomeFileDichiarazioneSpesaPiuGreen(esitoSrv.getDichiarazionePiuGreenDTO().getNomeFile());
                esito.setIdDocumentoIndexPiuGreen(esitoSrv.getDichiarazionePiuGreenDTO().getIdDocIndex());
                esito.setIdDichiarazioneSpesaPiuGreen(esitoSrv.getDichiarazionePiuGreenDTO().getIdDichiarazioneSpesa());
                LOG.info(prf + "IdDocIndexPiuGreen = " + esito.getIdDocumentoIndexPiuGreen() + "; NoneFilePiuGreen = "
                    + esito.getNomeFileDichiarazioneSpesaPiuGreen() + "; IdDichiarazioneSpesaPiuGreen = "
                    + esito.getIdDichiarazioneSpesaPiuGreen());
            }

            if (isBandoCultura && isFinale) {
                try {
                    contoEconomicoDAOImpl.inviaPropostaDiRimodulazioneAutomatizzata(idUtente, idIride, prf, idProgetto, idBandoLinea, idSoggettoBeneficiario, idRappresentanteLegale, idDelegato);
                } catch (Exception e) {
                    LOG.error(prf + " ERRORE nell'invio della Proposta di Rimodulazione Automatizzata: ", e);
                    throw new DaoException(" ERRORE nell'invio della Proposta di Rimodulazione Automatizzata.");
                }
            }
            System.gc();
        } catch (InvalidParameterException e) {
            LOG.error(prf + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error(prf + " ERRORE nell'invio della Dichiarazione di Spesa: ", e);
            throw new DaoException(" ERRORE nell'invio della Dichiarazione di Spesa.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;

    }

    private boolean isBandoCultura(Long idBandoLinea) {
        String sql = "SELECT * FROM PBANDI_R_BANDO_LINEA_INTERVENT WHERE ID_PROCESSO = 3 AND PROGR_BANDO_LINEA_INTERVENTO = ?";
      return !getJdbcTemplate().queryForList(sql, idBandoLinea).isEmpty();
    }


    private Boolean aggiornaProgettoIter(Long idProgetto, Long idDichiarazioneDiSpesa) throws Exception {
    	
    	String prf = "[DichiarazioneDiSpesaDAOImpl::aggiornaProgettoIter] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idProgetto = " + idProgetto);

		if (idProgetto == 0 || idProgetto == null) {
			throw new InvalidParameterException("idProgetto non valorizzato.");
		}
		
		if (idDichiarazioneDiSpesa == 0 || idDichiarazioneDiSpesa == null) {
			throw new InvalidParameterException("idDichiarazioneDiSpesa non valorizzato.");
		}

		try {
			String sql;
			sql = "UPDATE PBANDI_R_PROGETTO_ITER SET ID_DICHIARAZIONE_SPESA = ? \r\n"
					+ "WHERE id_progetto = ? AND FLAG_FASE_CHIUSA = 1 AND ID_DICHIARAZIONE_SPESA  IS NULL";

			Object[] args = new Object[] { idDichiarazioneDiSpesa, idProgetto };
			logger.info("<idProgetto>: " + idProgetto + ", <idDichiarazioneDiSpesa>: " + idDichiarazioneDiSpesa);
			logger.info(prf + "\n" + sql + "\n");

			int rows = getJdbcTemplate().update(sql, args);
			if (rows > 0) {
				return Boolean.TRUE;
			}
		}  catch (Exception e) {
			LOG.error(prf + " ERRORE in aggiornaProgettoIter: ", e);
			throw new Exception(" ERRORE in aggiornaProgettoIter.");
		} finally {
			LOG.info(prf + " END");
		}
    	
    	return Boolean.FALSE;
    }

    private RappresentanteLegaleDTO getPrivatoCittadinoForRappresentanteLegale(Long idSoggetto) throws DaoException {

        String prf = "[DichiarazioneDiSpesaDAOImpl::getPrivatoCittadinoForRappresentanteLegale] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idSoggetto = " + idSoggetto + " ; ");

        if (idSoggetto == null) {
            throw new InvalidParameterException("idSoggetto non valorizzato");
        }

        RappresentanteLegaleDTO rappresentanteLegaleDTO = null;

        try {

            /*
             * String sql =
             * "select 														\r\n" +
             * "ptpf.id_soggetto,											\r\n" +
             * "ptpf.id_persona_fisica,										\r\n" +
             * "ptpf.cognome,												\r\n" +
             * "ptpf.nome,													\r\n" +
             * "ptpf.dt_nascita as data_nascita,								\r\n" +
             * "ptpf.id_comune_italiano_nascita,								\r\n" +
             * "ptpf.id_comune_estero_nascita,								\r\n" +
             * "pts.codice_fiscale_soggetto 									\r\n" +
             * "from pbandi_t_persona_fisica ptpf							\r\n" +
             * "join pbandi_t_soggetto pts									\r\n" +
             * "on ptpf.id_soggetto = pts.id_soggetto						\r\n" +
             * "where ptpf.id_soggetto = ?		 							\r\n" +
             * "and ROWNUM <=1												";
             */

            String sql = "select 														\r\n"
                    + "ptpf.id_soggetto,											\r\n"
                    + "ptpf.id_persona_fisica,										\r\n"
                    + "ptpf.cognome,												\r\n"
                    + "ptpf.nome,													\r\n"
                    + "ptpf.dt_nascita as data_nascita,								\r\n"
                    + "ptpf.id_comune_italiano_nascita,								\r\n"
                    + "ptpf.id_comune_estero_nascita,								\r\n"
                    + "pts.codice_fiscale_soggetto,									\r\n"
                    + "case						 									\r\n"
                    + "	when ptpf.id_comune_italiano_nascita is null				\r\n"
                    + "		then pdce.desc_comune_estero							\r\n"
                    + "	else pdc.desc_comune										\r\n"
                    + "	end as luogo_nascita										\r\n"
                    + "from pbandi_t_persona_fisica ptpf							\r\n"
                    + "join pbandi_t_soggetto pts									\r\n"
                    + "	on ptpf.id_soggetto = pts.id_soggetto						\r\n"
                    + "left join pbandi_d_comune pdc								\r\n"
                    + "	on ptpf.id_comune_italiano_nascita = pdc.id_comune			\r\n"
                    + "left join pbandi_d_comune_estero pdce						\r\n"
                    + "	on ptpf.id_comune_estero_nascita = pdce.id_comune_estero	\r\n"
                    + "where ptpf.id_soggetto = ? 									\r\n"
                    + "and ROWNUM <=1													";

            Object[] par = { idSoggetto.toString() };

            logger.info(prf + "\n" + sql);
            rappresentanteLegaleDTO = (RappresentanteLegaleDTO) getJdbcTemplate().queryForObject(sql, par,
                    new BeanRowMapper(RappresentanteLegaleDTO.class));

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca del rappresentante legale nel caso del privato cittadino: ", e);
            throw new DaoException(" ERRORE nella ricerca del rappresentante legale nel caso del privato cittadino: ");
        } finally {
            LOG.info(prf + " END");
        }

        return rappresentanteLegaleDTO;

    }

    private IndirizzoPrivatoCittadino getIndirizzoPrivatoCittadino(Long idSoggetto, Long idProgetto)
            throws DaoException {

        String prf = "[DichiarazioneDiSpesaDAOImpl::getIndirizzoPrivatoCittadino] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idSoggetto = " + idSoggetto + ", " + "idProgetto = " + idProgetto + " ; ");

        if (idSoggetto == null) {
            throw new InvalidParameterException("idSoggetto non valorizzato");
        }
        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }

        IndirizzoPrivatoCittadino indirizzoPrivatoCittadino = null;

        try {

            String sql = "SELECT                             \r\n"
            		+ "	pti.desc_indirizzo,                       \r\n"
            		+ "	pti.cap,                            \r\n"
            		+ "	pti.id_provincia,                      \r\n"
            		+ "	pti.id_comune                        \r\n"
            		+ "FROM pbandi_r_soggetto_progetto prsp              \r\n"
            		+ "JOIN pbandi_t_indirizzo pti ON prsp.id_indirizzo_persona_fisica = pti.id_indirizzo    \r\n"
            		+ "JOIN pbandi_d_tipo_anagrafica ta ON ta.id_tipo_anagrafica = prsp.id_tipo_anagrafica\r\n"
            		+ "WHERE prsp.id_progetto = ?   \r\n"
            		+ "	AND ta.desc_breve_tipo_anagrafica = ?              \r\n"
            		+ "	AND prsp.id_soggetto = ?";

            Object[] par = { idProgetto.toString(),Constants.DESC_BREVE_TIPO_ANAGRAFICA_PERSONA_FISICA, idSoggetto.toString() };

            logger.info(prf + "\n" + sql);
            indirizzoPrivatoCittadino = (IndirizzoPrivatoCittadino) getJdbcTemplate().queryForObject(sql, par,
                    new BeanRowMapper(IndirizzoPrivatoCittadino.class));

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca dell' indirizzo privato cittadino: ", e);
            throw new DaoException(" ERRORE nella ricerca dell' indirizzo privato cittadino: ");
        } finally {
            LOG.info(prf + " END");
        }

        return indirizzoPrivatoCittadino;

    }

    private DichiarazioneDiSpesaDTO popolaDichiarazioneDiSpesaDTO(
            InviaDichiarazioneDiSpesaRequest req) {
        if (req == null)
            return null;
        DichiarazioneDiSpesaDTO dto = new DichiarazioneDiSpesaDTO();
        dto.setIdBandoLinea(req.getIdBandoLinea());
        dto.setIdProgetto(req.getIdProgetto());
        dto.setIdSoggetto(req.getIdSoggetto());
        dto.setDataFineRendicontazione(req.getDataLimiteDocumentiRendicontabili());
        dto.setTipoDichiarazione(req.getCodiceTipoDichiarazioneDiSpesa());
        dto.setIdProgettoContributoPiuGreen(req.getIdProgettoContributoPiuGreen());
        return dto;
    }

    private DichiarazioneDiSpesaDTO popolaDichiarazioneDiSpesaDTO(
            AnteprimaDichiarazioneDiSpesaRequest req) {
        if (req == null)
            return null;
        DichiarazioneDiSpesaDTO dto = new DichiarazioneDiSpesaDTO();
        dto.setIdBandoLinea(req.getIdBandoLinea());
        dto.setIdProgetto(req.getIdProgetto());
        dto.setIdSoggetto(req.getIdSoggettoBeneficiario()); // Alex: anche se si chiama IdSoggetto, in realtà deve
                                                            // contenere IdSoggettoBeneficiario.
        dto.setDataFineRendicontazione(req.getDataLimiteDocumentiRendicontabili());
        dto.setTipoDichiarazione(req.getCodiceTipoDichiarazioneDiSpesa());
        dto.setIdProgettoContributoPiuGreen(req.getIdProgettoContributoPiuGreen());
        return dto;
    }

    private ComunicazioneFineProgettoDTO popolaComunicazioneFineProgettoDTO(
            AnteprimaDichiarazioneDiSpesaRequest req) {
        if (req == null)
            return null;
        ComunicazioneFineProgettoDTO dto = new ComunicazioneFineProgettoDTO();
        dto.setIdDelegato(req.getIdDelegato());
        dto.setIdProgetto(req.getIdProgetto());
        dto.setIdProgettoContributoPiuGreen(req.getIdProgettoContributoPiuGreen());
        dto.setIdRappresentanteLegale(req.getIdRappresentanteLegale());
        dto.setIdSoggettoBeneficiario(req.getIdSoggettoBeneficiario());
        dto.setImportoRichiestaSaldo(req.getImportoRichiestaSaldo());
        dto.setNote(req.getNote());

        // Campi che non servono.
        dto.setCfBeneficiario(null); // FORSE NON SERVE, PER IL MOMENTO LO LASCIO A NULL.
        dto.setCodiceProgetto(null);

        return dto;
    }

    @Override
    // Setta il campo PBANDI_T_DOCUMENTO_INDEX.FLAG_FIRMA_CARTACEA a "S".
    // Ex DigitalSignBusinessImpl.setFlagCartaceo()
    public Boolean salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::salvaInvioCartaceo] ";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + "invioCartaceo = " + invioCartaceo + "; idDocumentoIndex = " + idDocumentoIndex + "; idUtente = "
                + idUtente);

        if (invioCartaceo == null) {
            throw new InvalidParameterException("invioCartaceo non valorizzato.");
        }
        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato.");
        }

        Boolean esito = true;
        try {

            String flagFirmaCartacea = (invioCartaceo != null && invioCartaceo) ? "S" : "N";
            this.valorizzaFlagFirmaCartacea(idDocumentoIndex, flagFirmaCartacea, idUtente);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE durante la valorizzazione del Flag Firma Cartacea: ", e);
            esito = false;
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    // Setta il campo PBANDI_T_DOCUMENTO_INDEX.FLAG_FIRMA_CARTACEA con il valore in
    // input.
    private Boolean valorizzaFlagFirmaCartacea(Long idDocumentoIndex, String flag, Long idUtente)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::valorizzaFlagFirmaCartacea] ";
        LOG.info(prf + " BEGIN");
        LOG.info(prf + "+idDocumentoIndex = " + idDocumentoIndex + "; flag = " + flag + "; idUtente = " + idUtente);

        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato.");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato.");
        }

        Boolean esito = true;
        try {

            String sql = "UPDATE PBANDI_T_DOCUMENTO_INDEX SET FLAG_FIRMA_CARTACEA = '" + flag
                    + "', DT_AGGIORNAMENTO_INDEX = SYSDATE, ID_UTENTE_AGG = " + idUtente
                    + " where ID_DOCUMENTO_INDEX = " + idDocumentoIndex;
            logger.info(prf + "\n" + sql);
            getJdbcTemplate().update(sql);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE durante la valorizzazione del Flag Firma Cartacea: ", e);
            esito = false;
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Salva il pdf firmato della dichiarazione di spesa.
    @Transactional(rollbackFor = { Exception.class })
    public Boolean salvaFileFirmato(MultipartFormDataInput multipartFormData, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::salvaFileFirmato] ";
        LOG.info(prf + "BEGIN");

        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }
        if (multipartFormData == null) {
            throw new InvalidParameterException("multipartFormData non valorizzato");
        }

        Long idDocumentoIndex = multipartFormData.getFormDataPart("idDocumentoIndex", Long.class, null);
        LOG.info(prf + "input idDocumentoIndex = " + idDocumentoIndex);
        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato");
        }

        // Legge il file firmato dal multipart.
        Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
        List<InputPart> listInputPart = map.get("fileFirmato");

        if (listInputPart == null) {
            LOG.info("listInputPart NULLO");
        } else {
            LOG.info("listInputPart SIZE = " + listInputPart.size());
        }
        for (InputPart i : listInputPart) {
            MultivaluedMap<String, String> m = i.getHeaders();
            Set<String> s = m.keySet();
            for (String x : s) {
                LOG.info("SET = " + x);
            }
        }

        FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
        if (file == null) {
            throw new InvalidParameterException("File non valorizzato");
        }

        LOG.info(prf + "input idDocumentoIndex = " + idDocumentoIndex);
        LOG.info(prf + "input nomeFile = " + file.getNomeFile());
        LOG.info(prf + "input bytes.length = " + file.getBytes().length);

        Boolean esito = true;
        try {

            esito = documentoManager.salvaFileFirmato(file.getBytes(), file.getNomeFile(), idDocumentoIndex, idUtente);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel salvataggio del file firmato: ", e);
            throw new DaoException(" ERRORE nel salvataggio del file firmato.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Salva il pdf firmato con firma Autografa della dichiarazione di spesa.
    @Transactional(rollbackFor = { Exception.class })
    public Boolean salvaFileFirmaAutografa(MultipartFormDataInput multipartFormData, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::salvaFileFirmaAutografa]";
        LOG.info(prf + "BEGIN");

        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }
        if (multipartFormData == null) {
            throw new InvalidParameterException("multipartFormData non valorizzato");
        }

        Long idDocumentoIndex = multipartFormData.getFormDataPart("idDocumentoIndex", Long.class, null);
        LOG.info(prf + "input idDocumentoIndex = " + idDocumentoIndex);
        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato");
        }

        // Legge il file firmato dal multipart.
        Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
        List<InputPart> listInputPart = map.get("fileFirmaAutografa");

        if (listInputPart == null) {
            LOG.info("listInputPart NULLO");
        } else {
            LOG.info("listInputPart SIZE = " + listInputPart.size());
        }
        for (InputPart i : listInputPart) {
            MultivaluedMap<String, String> m = i.getHeaders();
            Set<String> s = m.keySet();
            for (String x : s) {
                LOG.info("SET = " + x);
            }
        }

        FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
        if (file == null) {
            throw new InvalidParameterException("File non valorizzato");
        }

        LOG.info(prf + "input idDocumentoIndex = " + idDocumentoIndex);
        LOG.info(prf + "input nomeFile = " + file.getNomeFile());
        LOG.info(prf + "input bytes.length = " + file.getBytes().length);

        Boolean esito = true;
        try {

            esito = documentoManager.salvaFileFirmaAutografa(file.getBytes(), file.getNomeFile(), idDocumentoIndex,
                    idUtente);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel salvataggio del file firmato: ", e);
            throw new DaoException(" ERRORE nel salvataggio del file firmato.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    // Salva il pdf firmato con firma Autografa della dichiarazione di spesa.
    @Transactional(rollbackFor = { Exception.class })
    public Boolean salvaFileFirmaAutografaOld(MultipartFormDataInput multipartFormData, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::salvaFileFirmaAutografa]";
        LOG.info(prf + "BEGIN");

        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }
        if (multipartFormData == null) {
            throw new InvalidParameterException("multipartFormData non valorizzato");
        }

        Long idDocumentoIndex = multipartFormData.getFormDataPart("idDocumentoIndex", Long.class, null);
        LOG.info(prf + "input idDocumentoIndex = " + idDocumentoIndex);
        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato");
        }

        // Legge il file firmato dal multipart.
        Map<String, List<InputPart>> map = multipartFormData.getFormDataMap();
        List<InputPart> listInputPart = map.get("fileFirmaAutografa");

        if (listInputPart == null) {
            LOG.info("listInputPart NULLO");
        } else {
            LOG.info("listInputPart SIZE = " + listInputPart.size());

            for (InputPart i : listInputPart) {
                MultivaluedMap<String, String> m = i.getHeaders();
                Set<String> s = m.keySet();
                for (String x : s) {
                    LOG.info("SET = " + x);
                }
            }

        }

        FileDTO file = FileUtil.leggiFileDaMultipart(listInputPart, null);
        if (file == null) {
            throw new InvalidParameterException("File non valorizzato");
        }

        LOG.info(prf + "input idDocumentoIndex = " + idDocumentoIndex);
        LOG.info(prf + "input nomeFile = " + file.getNomeFile());
        LOG.info(prf + "input bytes.length = " + file.getBytes().length);

        Boolean esito = true;
        try {

            esito = documentoManager.salvaFileFirmato(file.getBytes(), file.getNomeFile(), idDocumentoIndex, idUtente);

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel salvataggio del file firmato: ", e);
            throw new DaoException(" ERRORE nel salvataggio del file firmato.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Verifica la firma digitale del file in input e lo marca temporalmente.
    // NOTA: questo metodo viene chiamato in modo asincrono da Angular.
    // Ex DigitalSignAction.upload()
    public Boolean verificaFirmaDigitale(Long idDocumentoIndex, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::verificaFirmaDigitale] ";
        LOG.info(prf + "BEGIN");

        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }
        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato");
        }

        LOG.info(prf + "input idDocumentoIndex = " + idDocumentoIndex);
        Boolean esito = true;
        try {

            FileDTO fileFirmato = documentoManager.leggiFileFirmato(idDocumentoIndex);
            if (fileFirmato == null) {
                LOG.error(prf + " Lettura del file firmato fallita.");
                throw new Exception("Lettura del file firmato fallita.");
            }

            SignedFileDTO signedFileDTO = new SignedFileDTO();
            signedFileDTO.setBytes(fileFirmato.getBytes());
            signedFileDTO.setFileName(fileFirmato.getNomeFile());
            signedFileDTO.setIdDocIndex(idDocumentoIndex);

            LOG.info(prf + "Chiamo digitalSignSrv.signFile");
            digitalSignBusinessImpl.signFile(idUtente, idIride, signedFileDTO);
            LOG.info(prf + "Terminato digitalSignSrv.signFile");

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella verifica del file firmato: ", e);
            throw new DaoException(" ERRORE nella verifica del file firmato.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    // Ex
    // ValidazioneDichiarazioneDiSpesaBusinessImpl.popolaTableRichiesteIntegrazioneDs()
    // contenente le integrazioni del progetto in input e relativi allegati.
    public ArrayList<RigaTabRichiesteIntegrazioniDs> integrazioniSpesaByIdProgetto(Long idProgetto, Long idUtente,
            String idIride) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::integrazioniSpesaByIdProgetto] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        ArrayList<RigaTabRichiesteIntegrazioniDs> elenco = new ArrayList<RigaTabRichiesteIntegrazioniDs>();
        try {

            IntegrazioneSpesaDTO[] integrazioni;
            integrazioni = validazioneRendicontazioneBusinessImpl.findIntegrazioniSpesaByIdProgetto(idUtente, idIride,
                    idProgetto);
            // Crea l'elenco da visualizzare a video.
            for (IntegrazioneSpesaDTO i : integrazioni) {
                RigaTabRichiesteIntegrazioniDs riga = new RigaTabRichiesteIntegrazioniDs();
                riga.setIdIntegrazioneSpesa(i.getIdIntegrazioneSpesa());
                riga.setIdDichiarazioneSpesa(i.getIdDichiarazioneSpesa().toString());
                riga.setDataRichiesta(DateUtil.getDate(i.getDataRichiesta()));
                riga.setDataInvio(DateUtil.getDate(i.getDataInvio()));
                riga.setDescrizione(i.getDescrizione());
                riga.setDataDichiarazione(DateUtil.getDate(i.getDataDichiarazione()));
                if (i.getAllegati() != null) {
                    ArrayList<AllegatoIntegrazioneDs> allegati = new ArrayList<AllegatoIntegrazioneDs>();
                    for (AllegatoIntegrazioneSpesaDTO allegatoDTO : i
                            .getAllegati()) {
                        AllegatoIntegrazioneDs allegato = new AllegatoIntegrazioneDs();
                        allegato.setIdDocumentoIndex(allegatoDTO.getIdDocumentoIndex().toString());
                        allegato.setNomeFile(allegatoDTO.getNomeFile());
                        allegato.setFlagEntita(allegatoDTO.getFlagEntita()); // Jira PBANDI-2815.
                        allegati.add(allegato);
                    }
                    riga.setAllegati(allegati);
                }
                elenco.add(riga);
            }

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nella ricerca delle integrazioni alla spesa: ", e);
            throw new DaoException(" ERRORE nella ricerca delle integrazioni alla spesa.");
        } finally {
            LOG.info(prf + " END");
        }

        return elenco;
    }

    @Override
    // Marca l'allegato in input come Dichiarazione di Integrazione (Jira
    // PBANDI-2815),
    // settando a "I" il campo PBANDI_T_FILE_ENTITA.FLAG_ENTITA.
    public EsitoDTO marcaComeDichiarazioneDiIntegrazione(Long idDocumentoIndex, Long idIntegrazioneDiSpesa,
            Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::marcaComeDichiarazioneDiIntegrazione] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idDocumentoIndex = " + idDocumentoIndex + "; idIntegrazioneDiSpesa = " + idIntegrazioneDiSpesa
                + "; idProgetto = " + idProgetto + "; idUtente = " + idUtente);

        if (idDocumentoIndex == null) {
            throw new InvalidParameterException("idDocumentoIndex non valorizzato");
        }
        if (idIntegrazioneDiSpesa == null) {
            throw new InvalidParameterException("idIntegrazioneDiSpesa non valorizzato");
        }
        if (idProgetto == null) {
            throw new InvalidParameterException("idProgetto non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        EsitoDTO esito = new EsitoDTO();
        try {

            BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
                    Constants.ENTITA_T_INTEGRAZIONE_SPESA);
            if (idEntita == null)
                throw new DaoException("Id entita non trovato.");

            String flagEntita = "I";

            Esito esitoPbandisrv = archivioBusinessImpl.marcaFlagEntitaFile(idUtente, idIride, idDocumentoIndex,
                    idEntita.longValue(), idIntegrazioneDiSpesa, idProgetto, flagEntita);
            esito.setEsito(esitoPbandisrv.getEsito());
            esito.setMessaggio(esitoPbandisrv.getMessage());

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nel marcare come dichiarazione di integrazione ", e);
            throw new DaoException(" ERRORE nel marcare come dichiarazione di integrazione.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public EsitoDTO inviaIntegrazioneDiSpesaAIstruttore(Long idIntegrazioneDiSpesa, Long idUtente, String idIride)
            throws InvalidParameterException, Exception {
        String prf = "[DichiarazioneDiSpesaDAOImpl::inviaIntegrazioneDiSpesaAIstruttore] ";
        LOG.info(prf + "BEGIN");
        LOG.info(prf + "idIntegrazioneDiSpesa = " + idIntegrazioneDiSpesa + "; idUtente = " + idUtente);

        if (idIntegrazioneDiSpesa == null) {
            throw new InvalidParameterException("idIntegrazioneDiSpesa non valorizzato");
        }
        if (idUtente == null) {
            throw new InvalidParameterException("idUtente non valorizzato");
        }

        EsitoDTO esito = new EsitoDTO();
        try {
            java.sql.Date oggi = DateUtil.getSysdate();

            IntegrazioneSpesaDTO dto = new IntegrazioneSpesaDTO();
            dto.setIdIntegrazioneSpesa(idIntegrazioneDiSpesa);
            dto.setDataInvio(oggi);
            dto.setIdUtenteInvio(idUtente);

            validazioneRendicontazioneBusinessImpl.salvaIntegrazioneSpesa(idUtente, idIride, dto);

            esito.setEsito(true);
            esito.setMessaggio("Integrazione inviata con successo.");

        } catch (Exception e) {
            LOG.error(prf + " ERRORE nell'invio della integrazione all'istruttore ", e);
            throw new DaoException(" ERRORE nell'invio della integrazione all'istruttore.");
        } finally {
            LOG.info(prf + " END");
        }

        return esito;
    }

    @Override
    public Boolean isBandoCultura(Long idBandoLinea, Long idUtente, String idIride) throws Exception {
        return isBandoCultura(idBandoLinea);
    }

}
