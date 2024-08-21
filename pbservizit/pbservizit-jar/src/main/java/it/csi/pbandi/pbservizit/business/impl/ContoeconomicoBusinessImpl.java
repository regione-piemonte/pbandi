/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.business.impl;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.integration.vo.ContoEconomicoMaxDataFineVO;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.*;
import it.csi.pbandi.pbservizit.pbandisrv.dto.contoeconomico.*;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RigoContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ImportoTotaleDisimpegniVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NotCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.NullCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEconomieVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRigoContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTSpesaPreventivataVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static it.csi.pbandi.pbservizit.util.DateUtil.utilToSqlDate;

@Service
public class ContoeconomicoBusinessImpl extends BusinessImpl implements ContoeconomicoSrv {

  private static final String PERIODO_UNICO = "9999";

  static final private Map<String, String> mapVisualizzazioneMaster = new HashMap<String, String>();
  static final private Map<String, String> mapMain = new HashMap<String, String>();
  static final private Map<String, String> mapMaster = new HashMap<String, String>();
  static final private Map<String, String> mapCopyBen = new HashMap<String, String>();
  static final private Map<String, String> mapCopyIst = new HashMap<String, String>();
  static final private Map<String, String> mapCommon = new HashMap<String, String>();

  private static final BidiMap MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE = new TreeBidiMap();
  private static final BidiMap MAP_FROM_STEPAGGIUDICAZIONE_TO_PBANDIRITERPROCAGGVO;
  static final private Map<String, Map<String, String>> mappaturaPerTipoContoEconomico = new HashMap<String, Map<String, String>>();

  static {
    MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("dtEffettiva", "dtEffettiva");
    MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("dtPrevista", "dtPrevista");
    MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("idMotivoScostamento", "idMotivoScostamento");
    MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("idStepAggiudicazione", "idStepAggiudicazione");
    MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("importoStep", "importo");
    MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE.put("descStep", "descStepAggiudicazione");

    MAP_FROM_STEPAGGIUDICAZIONE_TO_PBANDIRITERPROCAGGVO = MAP_FROM_ITERPROCAGGVO_TO_STEPAGGIUDICAZIONE
        .inverseBidiMap();
  }

  @Autowired
  private ContoEconomicoManager contoEconomicoManager;
  @Autowired
  private it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO genericDAO;
//  @Autowired
//  private ProgettoManager progettoManager;
//  @Autowired
//  private PopolaTemplateManager popolaTemplateManager;
//  // NOTA: suoi bean senza autowired
//  @Autowired
//  private PbandiDichiarazioneDiSpesaDAOImpl pbandiDichiarazioneDiSpesaDAO;
//  @Autowired
//  private RappresentanteLegaleManager rappresentanteLegaleManager;
//  @Autowired
//  private SedeManager sedeManager;
//  @Autowired
//  private PbandiArchivioFileDAOImpl pbandiArchivioFileDAOImpl;
//  @Autowired
//  private it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl neofluxBusinessImpl;
//  @Autowired
//  private SoggettoManager soggettoManager;
//  @Autowired
//  private it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager decodificheManager;
//  @Autowired
//  private DocumentoManager documentoManager;
//  @Autowired
//  private it.csi.pbandi.pbservizit.business.manager.DocumentoManager documentoManagerNuovaVersione;

  {
    mapVisualizzazioneMaster.put("importoAmmesso", "importoSpesaAmmessa");
    mapVisualizzazioneMaster.put("importoQuietanzato", "importoSpesaQuietanziata");
    mapVisualizzazioneMaster.put("importoRendicontato", "importoSpesaRendicontata");
    mapVisualizzazioneMaster.put("importoValidato", "importoSpesaValidataTotale");
    mapVisualizzazioneMaster.put("descVoceDiSpesa", "label");
    mapVisualizzazioneMaster.put("idVoceDiSpesa", "idVoce");
    mapVisualizzazioneMaster.put("idContoEconomico", "idContoEconomico");
    mapVisualizzazioneMaster.put("idVoceDiSpesaPadre", "idVocePadre");
    // Cultura
    mapVisualizzazioneMaster.put("idTipologiaVoceDiSpesa", "idTipologiaVoceDiSpesa");
    mapVisualizzazioneMaster.put("importoSpesaPreventivata", "importoSpesaPreventivata");
    mapVisualizzazioneMaster.put("percSpGenFunz", "percSpGenFunz");

    mapMain.put("importoRichiesto", "importoRichiestoInDomanda");
    mapMain.put("importoAmmesso", "importoSpesaAmmessaInDetermina");

    mapMaster.put("importoRichiesto", "importoRichiestoUltimaProposta");
    mapMaster.put("importoAmmesso", "importoSpesaAmmessaUltima");
    mapMaster.put("importoRendicontato", "importoSpesaRendicontata");
    mapMaster.put("importoQuietanzato", "importoSpesaQuietanziata");
    mapMaster.put("importoValidato", "importoSpesaValidataTotale");

    //Architetture Rurali
    mapMaster.put("delta", "delta");

    mapCopyBen.put("importoRichiesto", "importoRichiestoNuovaProposta");

    mapCopyIst.put("importoAmmesso", "importoSpesaAmmessaRimodulazione");

    mapCommon.put("descVoceDiSpesa", "label");
    mapCommon.put("massimoImportoAmmissibile", "importoAmmessoDaBando");
    mapCommon.put("idVoceDiSpesa", "idVoce");
    mapCommon.put("idRigoContoEconomico", "idRigoContoEconomico");
    mapCommon.put("idContoEconomico", "idContoEconomico");
    mapCommon.put("voceAssociataARigo", "voceAssociataARigo");
    mapCommon.put("idTipologiaVoceDiSpesa", "idTipologiaVoceDiSpesa");
    mapCommon.put("descTipologiaVoceDiSpesa", "descTipologiaVoceDiSpesa");
    mapCommon.put("percQuotaContributo", "percQuotaContributo");
    mapCommon.put("percSpGenFunz", "percSpGenFunz");
    mapCommon.put("idVoceDiEntrata", "idVoceDiEntrata");
    mapCommon.put("completamento", "completamento");
    mapCommon.put("descVoceDiEntrata", "descVoceDiEntrata");
    mapCommon.put("flagEdit", "flagEdit");
    mapCommon.put("importoSpesaPreventivata", "importoSpesaPreventivata");

    mappaturaPerTipoContoEconomico.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_MAIN, mapMain);
    mappaturaPerTipoContoEconomico.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER, mapMaster);
    mappaturaPerTipoContoEconomico.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_BEN, mapCopyBen);
    mappaturaPerTipoContoEconomico.put(Constants.TIPOLOGIA_CONTO_ECONOMICO_COPY_IST, mapCopyIst);
  }


  public it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO getGenericDAO() {
    return genericDAO;
  }

  public void setGenericDAO(it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO genericDAO) {
    this.genericDAO = genericDAO;
  }

  public ContoEconomicoManager getContoEconomicoManager() {
    return contoEconomicoManager;
  }

  public void setContoEconomicoManager(ContoEconomicoManager contoEconomicoManager) {
    this.contoEconomicoManager = contoEconomicoManager;
  }

  // Jira PBANDI-2853; Alex: copiato da CertificazioneBusinessImpl su pbandisrv.
  public Double findImportoTotaleDisimpegni(Long idProgetto)
      throws CSIException {

    ImportoTotaleDisimpegniVO vo = new ImportoTotaleDisimpegniVO();
    vo.setIdProgetto(new BigDecimal(idProgetto));
    vo = genericDAO.findSingleOrNoneWhere(vo);

    if (vo == null || vo.getImportoTotaleDisimpegni() == null)
      return new Double(0);
    else
      return vo.getImportoTotaleDisimpegni();
  }

  public Boolean isProgettoRicevente(Long idUtente, String identitaDigitale, Long idProgetto)
      throws CSIException {
    PbandiTEconomieVO vo = new PbandiTEconomieVO();
    vo.setIdProgettoRicevente(new BigDecimal(idProgetto));
    List<PbandiTEconomieVO> lista = genericDAO.findListWhere(vo);
    boolean esito = (lista.size() > 0);
    return esito;
  }

  public Double findSommaEconomieUtilizzate(Long idUtente, String identitaDigitale, Long idProgetto)
      throws CSIException {

    PbandiTEconomieVO vo = new PbandiTEconomieVO();
    vo.setIdProgettoRicevente(new BigDecimal(idProgetto));
    FilterCondition<PbandiTEconomieVO> filter = new FilterCondition<PbandiTEconomieVO>(vo);
    NotCondition<PbandiTEconomieVO> dtUtilizzoValorizzata = new NotCondition<PbandiTEconomieVO>(
        new NullCondition<PbandiTEconomieVO>(PbandiTEconomieVO.class, "dataUtilizzo"));
    AndCondition<PbandiTEconomieVO> andCond = new AndCondition<>(filter, dtUtilizzoValorizzata);
    List<PbandiTEconomieVO> lista = genericDAO.findListWhere(andCond);

    double somma = 0.0;
    for (PbandiTEconomieVO e : lista) {
      somma = somma + e.getImportoCeduto();
    }
    return somma;
  }

  public EsitoFindContoEconomicoDTO findContoEconomicoPerRimodulazione(Long idUtente, Integer idProcesso, String identitaDigitale,
                                                                                    ProgettoDTO progetto)
      throws CSIException {

    String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
    ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto, progetto.getIdProgetto());

    ContoEconomicoRimodulazioneDTO contoEconomicoCulturaDTO = null;


    try {
      // la rimodulazione prevede che si lavori su 3 possibili tipologie di conto
      // economico
      // - MAIN - MASTER - COPY_IST
      List<String> listaTipi = new ArrayList<String>();
      Map<String, ContoEconomicoDTO> mappaConti = new HashMap<String, ContoEconomicoDTO>();
      String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_IST;
      Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

      // TNT modificato per istruttoria con master non presente

      caricaContiEconomici(new BigDecimal(progetto.getIdProgetto()), listaTipi, mappaConti, tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);

      // TNT modificato per istruttoria con master non presente
      ContoEconomicoDTO contoMaster = mappaConti.get(TIPOLOGIA_CONTO_ECONOMICO_MASTER);

      // TNT modificare per istruttoria ,eliminato riferimento a master nei nomi
      // variabili.

      contoEconomicoCulturaDTO = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoMaster);

      EsitoFindContoEconomicoDTO esitoFindContoEconomicoDTO = new EsitoFindContoEconomicoDTO();
      esitoFindContoEconomicoDTO.setContoEconomico(contoEconomicoCulturaDTO);
      esitoFindContoEconomicoDTO.setLocked(isLockedByBeneficiario(contoMaster));
      esitoFindContoEconomicoDTO.setCopiaModificataPresente(mappaConti.get(tipologiaContoEconomicoCopy) != null);
      esitoFindContoEconomicoDTO.setModificabile(regolaManager.isRegolaApplicabileForProgetto(
          progetto.getIdProgetto(), RegoleConstants.BR12_RIMODULAZIONE_DISPONIBILE));

      ContoEconomicoDTO contoMain = mappaConti.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);
      if (contoMain != null) {
        if (Constants.STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
          esitoFindContoEconomicoDTO.setInIstruttoria(true);
      }

      ContoEconomicoMaxDataFineVO datiUltimaProposta = getDatiUltimaProposta(progetto);
      Date dataUltimaProposta = null;

      if (datiUltimaProposta != null) {
        dataUltimaProposta = datiUltimaProposta.getDtFineValidita();
        // VN: Ribasso d' asta. Resituisco il flag ribasso d' asta dell' ultima proposta
        if (datiUltimaProposta.getPercRibassoAsta() != null) {
          esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(Constants.FLAG_TRUE);
          esitoFindContoEconomicoDTO.setImportoUltimoRibassoAstaInProposta(NumberUtil.toDouble(datiUltimaProposta.getImportoRibassoAsta()));
          esitoFindContoEconomicoDTO.setPercUltimoRibassoAstaInProposta(NumberUtil.toDouble(datiUltimaProposta.getPercRibassoAsta()));
        } else {
          esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
        }
      }

      ContoEconomicoMaxDataFineVO datiUltimaRimodulazione = getDatiUltimaRimodulazione(progetto);
      Date dataUltimaRimodulazione = null;
      if (datiUltimaRimodulazione != null) {
        dataUltimaRimodulazione = datiUltimaRimodulazione.getDtFineValidita();
        // VN: Ribasso d' asta. Resituisco il flag ribasso d' asta dell' ultima
        // rimodulazione
        if (datiUltimaRimodulazione.getPercRibassoAsta() != null) {
          esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(
              it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
        } else {
          esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(
              it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
        }
      }

      esitoFindContoEconomicoDTO.setDataUltimaProposta(dataUltimaProposta);

      esitoFindContoEconomicoDTO.setDataUltimaRimodulazione(dataUltimaRimodulazione);

      esitoFindContoEconomicoDTO
          .setDataFineIstruttoria(mappaConti.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN).getDtFineValidita());

      esitoFindContoEconomicoDTO
          .setDataPresentazioneDomanda(mapDataPresentazioneDomanda.get(DATA_PRESENTAZIONE_DOMANDA));

      return esitoFindContoEconomicoDTO;

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UnrecoverableException(e.getMessage(), e);
    }
  }


  public EsitoFindContoEconomicoCulturaDTO findContoEconomicoPerRimodulazioneCultura(Long idUtente, Integer idProcesso, String identitaDigitale,
                                                                       ProgettoDTO progetto)
      throws CSIException {

    String[] nameParameter = {"idUtente", "identitaDigitale", "progetto", "progetto.idProgetto"};
    ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, progetto, progetto.getIdProgetto());

    ContoEconomicoRimodulazioneCulturaDTO contoEconomicoCulturaDTO;


    try {
      // la rimodulazione prevede che si lavori su 3 possibili tipologie di conto
      // economico
      // - MAIN - MASTER - COPY_IST
      List<String> listaTipi = new ArrayList<String>();
      Map<String, ContoEconomicoDTO> mappaConti = new HashMap<String, ContoEconomicoDTO>();
      String tipologiaContoEconomicoCopy = TIPOLOGIA_CONTO_ECONOMICO_COPY_IST;
      Map<String, Date> mapDataPresentazioneDomanda = new HashMap<String, Date>();

      // TNT modificato per istruttoria con master non presente
      if(idProcesso != null && idProcesso == 3) {
        logger.info("findContoEconomicoPerRimodulazioneCultura: caricamento conti economici");
        caricaContiEconomiciVociDiEntrata(new BigDecimal(progetto.getIdProgetto()), listaTipi, mappaConti, tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);
      }
      else {
        caricaContiEconomici(new BigDecimal(progetto.getIdProgetto()), listaTipi, mappaConti, tipologiaContoEconomicoCopy, mapDataPresentazioneDomanda);
      }
      // TNT modificato per istruttoria con master non presente
      ContoEconomicoDTO contoMaster = mappaConti.get(TIPOLOGIA_CONTO_ECONOMICO_MASTER);

      // TNT modificare per istruttoria ,eliminato riferimento a master nei nomi
      // variabili.
      if(idProcesso != null && idProcesso == 3){
        contoEconomicoCulturaDTO = convertiContoEconomicoPerRimodulazioneCultura(listaTipi, mappaConti, contoMaster);
      }
      else {
        ContoEconomicoRimodulazioneDTO temp = convertiContoEconomicoPerRimodulazione(listaTipi, mappaConti, contoMaster);
        contoEconomicoCulturaDTO = new ContoEconomicoRimodulazioneCulturaDTO(temp);

      }


      EsitoFindContoEconomicoCulturaDTO esitoFindContoEconomicoDTO = new EsitoFindContoEconomicoCulturaDTO();
      esitoFindContoEconomicoDTO.setContoEconomico(contoEconomicoCulturaDTO);
      esitoFindContoEconomicoDTO.setLocked(isLockedByBeneficiario(contoMaster));
      esitoFindContoEconomicoDTO.setCopiaModificataPresente(mappaConti.get(tipologiaContoEconomicoCopy) != null);
      esitoFindContoEconomicoDTO.setModificabile(regolaManager.isRegolaApplicabileForProgetto(
          progetto.getIdProgetto(), RegoleConstants.BR12_RIMODULAZIONE_DISPONIBILE));

      ContoEconomicoDTO contoMain = mappaConti.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);
      if (contoMain != null) {
        if (Constants.STATO_CONTO_ECONOMICO_IN_ISTRUTTORIA.equalsIgnoreCase(contoMain.getDescBreveStatoContoEconom()))
          esitoFindContoEconomicoDTO.setInIstruttoria(true);
      }

      ContoEconomicoMaxDataFineVO datiUltimaProposta = getDatiUltimaProposta(progetto);
      Date dataUltimaProposta = null;

      if (datiUltimaProposta != null) {
        dataUltimaProposta = datiUltimaProposta.getDtFineValidita();
        // VN: Ribasso d' asta. Resituisco il flag ribasso d' asta dell' ultima proposta
        if (datiUltimaProposta.getPercRibassoAsta() != null) {
          esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(Constants.FLAG_TRUE);
          esitoFindContoEconomicoDTO.setImportoUltimoRibassoAstaInProposta(NumberUtil.toDouble(datiUltimaProposta.getImportoRibassoAsta()));
          esitoFindContoEconomicoDTO.setPercUltimoRibassoAstaInProposta(NumberUtil.toDouble(datiUltimaProposta.getPercRibassoAsta()));
        } else {
          esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInProposta(it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
        }
      }

      ContoEconomicoMaxDataFineVO datiUltimaRimodulazione = getDatiUltimaRimodulazione(progetto);
      Date dataUltimaRimodulazione = null;
      if (datiUltimaRimodulazione != null) {
        dataUltimaRimodulazione = datiUltimaRimodulazione.getDtFineValidita();
        // VN: Ribasso d' asta. Resituisco il flag ribasso d' asta dell' ultima
        // rimodulazione
        if (datiUltimaRimodulazione.getPercRibassoAsta() != null) {
          esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(
              it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_TRUE);
        } else {
          esitoFindContoEconomicoDTO.setFlagUltimoRibassoAstaInRimodulazione(
              it.csi.pbandi.pbservizit.pbandiutil.common.Constants.FLAG_FALSE);
        }
      }

      esitoFindContoEconomicoDTO.setDataUltimaProposta(dataUltimaProposta);

      esitoFindContoEconomicoDTO.setDataUltimaRimodulazione(dataUltimaRimodulazione);

      esitoFindContoEconomicoDTO
          .setDataFineIstruttoria(mappaConti.get(TIPOLOGIA_CONTO_ECONOMICO_MAIN).getDtFineValidita());

      esitoFindContoEconomicoDTO
          .setDataPresentazioneDomanda(mapDataPresentazioneDomanda.get(DATA_PRESENTAZIONE_DOMANDA));

      return esitoFindContoEconomicoDTO;

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new UnrecoverableException(e.getMessage(), e);
    }
  }


  private void caricaContiEconomici(BigDecimal idProgetto, List<String> listaTipi,
                                    Map<String, ContoEconomicoDTO> mappaConti,
                                    String tipologiaContoEconomicoCopy, Map<String, Date> mapDataPresentazioneDomanda) throws Exception {

    ContoEconomicoDTO contoEconomico;
    Map<String, ContoEconomicoDTO> contiEconomiciPerRimodulazione = contoEconomicoManager
        .findContiEconomici(idProgetto, tipologiaContoEconomicoCopy);
    ContoEconomicoDTO contoMain = contiEconomiciPerRimodulazione
        .get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);

    if (contoMain != null) {
      mapDataPresentazioneDomanda.put(DATA_PRESENTAZIONE_DOMANDA, contoMain.getDataPresentazioneDomanda());
      listaTipi.add(TIPOLOGIA_CONTO_ECONOMICO_MAIN);
      mappaConti.put(TIPOLOGIA_CONTO_ECONOMICO_MAIN, contoMain);
    }

    contoEconomico = contiEconomiciPerRimodulazione.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
    if (contoEconomico != null) {
      listaTipi.add(TIPOLOGIA_CONTO_ECONOMICO_MASTER);
      mappaConti.put(TIPOLOGIA_CONTO_ECONOMICO_MASTER, contoEconomico);
    }
    ContoEconomicoDTO contoCopy = contiEconomiciPerRimodulazione
        .get(tipologiaContoEconomicoCopy);
    if (contoCopy != null) {
      listaTipi.add(tipologiaContoEconomicoCopy);
      mappaConti.put(tipologiaContoEconomicoCopy, contoCopy);
    }
  }


  private void caricaContiEconomiciVociDiEntrata(BigDecimal idProgetto, List<String> listaTipi,
                                    Map<String, ContoEconomicoDTO> mappaConti,
                                    String tipologiaContoEconomicoCopy, Map<String, Date> mapDataPresentazioneDomanda) throws Exception {

    ContoEconomicoDTO contoEconomico;
    Map<String, ContoEconomicoDTO> contiEconomiciPerRimodulazione = contoEconomicoManager
        .findContiEconomiciVociDiEntrata(idProgetto, tipologiaContoEconomicoCopy);
    ContoEconomicoDTO contoMain = contiEconomiciPerRimodulazione
        .get(TIPOLOGIA_CONTO_ECONOMICO_MAIN);

    if (contoMain != null) {
      mapDataPresentazioneDomanda.put(DATA_PRESENTAZIONE_DOMANDA, contoMain.getDataPresentazioneDomanda());
      listaTipi.add(TIPOLOGIA_CONTO_ECONOMICO_MAIN);
      mappaConti.put(TIPOLOGIA_CONTO_ECONOMICO_MAIN, contoMain);
    }

    contoEconomico = contiEconomiciPerRimodulazione.get(Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
    if (contoEconomico != null) {
      listaTipi.add(TIPOLOGIA_CONTO_ECONOMICO_MASTER);
      mappaConti.put(TIPOLOGIA_CONTO_ECONOMICO_MASTER, contoEconomico);
    }
    ContoEconomicoDTO contoCopy = contiEconomiciPerRimodulazione
        .get(tipologiaContoEconomicoCopy);
    if (contoCopy != null) {
      listaTipi.add(tipologiaContoEconomicoCopy);
      mappaConti.put(tipologiaContoEconomicoCopy, contoCopy);
    }
  }

  private ContoEconomicoRimodulazioneDTO convertiContoEconomicoPerRimodulazione(List<String> tipiDiConti,
                                                                                Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici,
                                                                                RigoContoEconomicoDTO rigoConto) throws Exception {

    Map<String, RigoContoEconomicoDTO> righiContiEconomici = new HashMap<String, RigoContoEconomicoDTO>(
        contiEconomici.size());
    for (String key : contiEconomici.keySet()) {
      righiContiEconomici.put(key, contiEconomici.get(key));
    }
    ContoEconomicoRimodulazioneDTO contoEconomicoConvertito = convertiFigliContoEconomicoPerRimodulazione(
        tipiDiConti, righiContiEconomici, rigoConto);

    for (String tipoDiConto : tipiDiConti) {
      it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoCaricato = contiEconomici
          .get(tipoDiConto);
      contoEconomicoConvertito.setFlagRibassoAsta(contoEconomicoCaricato.getFlagRibassoAsta());
    }

    return contoEconomicoConvertito;
  }

  private ContoEconomicoRimodulazioneCulturaDTO convertiContoEconomicoPerRimodulazioneCultura(List<String> tipiDiConti,
                                                                          Map<String, it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO> contiEconomici,
                                                                          RigoContoEconomicoDTO rigoConto) throws Exception {

    Map<String, RigoContoEconomicoDTO> righiContiEconomici = new HashMap<String, RigoContoEconomicoDTO>(
        contiEconomici.size());
    for (String key : contiEconomici.keySet()) {
      righiContiEconomici.put(key, contiEconomici.get(key));
    }
    ContoEconomicoRimodulazioneCulturaDTO contoEconomicoConvertito = convertiFigliContoEconomicoPerRimodulazioneCultura(
        tipiDiConti, righiContiEconomici, rigoConto);

    for (String tipoDiConto : tipiDiConti) {
      it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoEconomicoCaricato = contiEconomici
          .get(tipoDiConto);
      contoEconomicoConvertito.setFlagRibassoAsta(contoEconomicoCaricato.getFlagRibassoAsta());
    }

    return contoEconomicoConvertito;
  }


  private ContoEconomicoRimodulazioneDTO convertiFigliContoEconomicoPerRimodulazione(List<String> tipiDiConti,
                                                                                     Map<String, RigoContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
    ContoEconomicoRimodulazioneDTO contoEconomicoDTO = mappaContoRimodulazione(tipiDiConti, contiEconomici,
        rigoConto);

    List<RigoContoEconomicoDTO> figli = contiEconomici.get(tipiDiConti.get(0)).getFigli();

    ContoEconomicoRimodulazioneDTO[] figliTrasformati = null;
    if (figli != null && figli.size() > 0) {
      List<ContoEconomicoRimodulazioneDTO> listaTrasformati = new ArrayList<ContoEconomicoRimodulazioneDTO>();
      for (int i = 0; i < figli.size(); i++) {
        Map<String, RigoContoEconomicoDTO> mappaFigliCorrenti = new HashMap<String, RigoContoEconomicoDTO>();
        for (String tipoDiConto : tipiDiConti) {
          mappaFigliCorrenti.put(tipoDiConto, contiEconomici.get(tipoDiConto).getFigli().get(i));
        }

        RigoContoEconomicoDTO rigoContoFiglio = rigoConto == null ? null : rigoConto.getFigli().get(i);

        ContoEconomicoRimodulazioneDTO figlioTrasformato = convertiFigliContoEconomicoPerRimodulazione(
            tipiDiConti, mappaFigliCorrenti, rigoContoFiglio);

        listaTrasformati.add(figlioTrasformato);
      }
      figliTrasformati = listaTrasformati.toArray(new ContoEconomicoRimodulazioneDTO[listaTrasformati.size()]);
    }
    contoEconomicoDTO.setFigli(figliTrasformati);

    return contoEconomicoDTO;
  }

  private ContoEconomicoRimodulazioneCulturaDTO convertiFigliContoEconomicoPerRimodulazioneCultura(List<String> tipiDiConti,
                                                                               Map<String, RigoContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
    ContoEconomicoRimodulazioneCulturaDTO contoEconomicoDTO = mappaContoRimodulazioneCultura(tipiDiConti, contiEconomici,
        rigoConto);

    List<RigoContoEconomicoDTO> figli = contiEconomici.get(tipiDiConti.get(0)).getFigli();

    ContoEconomicoRimodulazioneCulturaDTO[] figliTrasformati = null;
    if (figli != null && figli.size() > 0) {
      List<ContoEconomicoRimodulazioneCulturaDTO> listaTrasformati = new ArrayList<ContoEconomicoRimodulazioneCulturaDTO>();
      for (int i = 0; i < figli.size(); i++) {
        Map<String, RigoContoEconomicoDTO> mappaFigliCorrenti = new HashMap<String, RigoContoEconomicoDTO>();
        for (String tipoDiConto : tipiDiConti) {
          mappaFigliCorrenti.put(tipoDiConto, contiEconomici.get(tipoDiConto).getFigli().get(i));
        }

        RigoContoEconomicoDTO rigoContoFiglio = rigoConto == null ? null : rigoConto.getFigli().get(i);

        ContoEconomicoRimodulazioneCulturaDTO figlioTrasformato = convertiFigliContoEconomicoPerRimodulazioneCultura(
            tipiDiConti, mappaFigliCorrenti, rigoContoFiglio);

        listaTrasformati.add(figlioTrasformato);
      }
      figliTrasformati = listaTrasformati.toArray(new ContoEconomicoRimodulazioneCulturaDTO[listaTrasformati.size()]);
    }
    contoEconomicoDTO.setFigli(figliTrasformati);

    return contoEconomicoDTO;
  }

  private ContoEconomicoRimodulazioneDTO mappaContoRimodulazione(List<String> tipiDiConti,
                                                                 Map<String, RigoContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
    ContoEconomicoRimodulazioneDTO contoEconomicoDTO = new ContoEconomicoRimodulazioneDTO();

    for (String tipoDiConto : tipiDiConti) {
      RigoContoEconomicoDTO rigo = contiEconomici.get(tipoDiConto);
      getBeanUtil().copyNotNullValues(rigo, contoEconomicoDTO, mappaturaPerTipoContoEconomico.get(tipoDiConto));
      getBeanUtil().copyNotNullValues(rigo, contoEconomicoDTO, mapCommon);
    }

    if (rigoConto != null) {
      contoEconomicoDTO.setPercSpesaQuietanziataSuAmmessa(NumberUtil
          .toDouble(NumberUtil.percentage(rigoConto.getImportoQuietanzato(), rigoConto.getImportoAmmesso())));
      contoEconomicoDTO.setPercSpesaValidataSuAmmessa(NumberUtil
          .toDouble(NumberUtil.percentage(rigoConto.getImportoValidato(), rigoConto.getImportoAmmesso())));
    }
    return contoEconomicoDTO;
  }

  private ContoEconomicoRimodulazioneCulturaDTO mappaContoRimodulazioneCultura(List<String> tipiDiConti,
                                                           Map<String, RigoContoEconomicoDTO> contiEconomici, RigoContoEconomicoDTO rigoConto) throws Exception {
    ContoEconomicoRimodulazioneCulturaDTO contoEconomicoDTO = new ContoEconomicoRimodulazioneCulturaDTO();

    for (String tipoDiConto : tipiDiConti) {
      RigoContoEconomicoDTO rigo = contiEconomici.get(tipoDiConto);
      getBeanUtil().copyNotNullValues(rigo, contoEconomicoDTO, mappaturaPerTipoContoEconomico.get(tipoDiConto));
      getBeanUtil().copyNotNullValues(rigo, contoEconomicoDTO, mapCommon);
    }

    if (rigoConto != null) {
      contoEconomicoDTO.setPercSpesaQuietanziataSuAmmessa(NumberUtil
          .toDouble(NumberUtil.percentage(rigoConto.getImportoQuietanzato(), rigoConto.getImportoAmmesso())));
      contoEconomicoDTO.setPercSpesaValidataSuAmmessa(NumberUtil
          .toDouble(NumberUtil.percentage(rigoConto.getImportoValidato(), rigoConto.getImportoAmmesso())));
    }
    return contoEconomicoDTO;
  }

  private boolean isLockedByBeneficiario(
      it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO contoMaster) {
    return contoMaster != null && contoMaster.getDescBreveStatoContoEconom()
        .equals(Constants.STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA);
  }

  public ContoEconomicoMaxDataFineVO getDatiUltimaProposta(ProgettoDTO progetto) {
    return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_NUOVA_PROPOSTA_INVIATA);
  }

  public ContoEconomicoMaxDataFineVO getDatiContoEconomicoByStato(ProgettoDTO progetto, String stato) {
    ContoEconomicoMaxDataFineVO contoEconomicoMaxDataFineVO = new ContoEconomicoMaxDataFineVO();
    contoEconomicoMaxDataFineVO.setIdProgetto(NumberUtil.createScaledBigDecimal(progetto.getIdProgetto()));
    contoEconomicoMaxDataFineVO.setDescBreveStatoContoEconom(stato);
    ContoEconomicoMaxDataFineVO[] voz = getGenericDAO().findWhere(contoEconomicoMaxDataFineVO);
    if (!isEmpty(voz))
      return voz[0];
    else
      return null;
  }

  public ContoEconomicoMaxDataFineVO getDatiUltimaRimodulazione(ProgettoDTO progetto) {
    return getDatiContoEconomicoByStato(progetto, Constants.STATO_CONTO_ECONOMICO_NUOVA_RIMODULAZIONE_CONCLUSA);
  }

  public boolean updateRigoContoEconomico(PbandiTRigoContoEconomicoVO voceDiEntrata) {
    try{
      genericDAO.update(voceDiEntrata);
    }
    catch(Exception e){
      logger.error("Errore durante l'aggiornamento del rigo del conto economico", e);
      return false;
    }
    return true;
  }

  public boolean updateOrAddSpesaPreventivata(PbandiTSpesaPreventivataVO spesa, Long idUtente) {
    try{
      //controllo se esiste un rigo a db

      PbandiTSpesaPreventivataVO old = new PbandiTSpesaPreventivataVO();
      old.setIdRigoContoEconomico(spesa.getIdRigoContoEconomico());
      old = genericDAO.findSingleOrNoneWhere(old);

      if(old != null) {
        logger.info(old.toString());
        spesa.setIdSpesaPreventivata(old.getIdSpesaPreventivata());
      }

      if(spesa.getIdSpesaPreventivata() != null){
        //se esiste aggiorno
        spesa.setIdUtenteAgg(BigDecimal.valueOf(idUtente));
        spesa.setDtModifica(new java.sql.Date(new Date().getTime()));
        genericDAO.update(spesa);
      }
      else{
        //se non esiste inserisco
        spesa.setIdUtenteIns(BigDecimal.valueOf(idUtente));
        spesa.setDtInserimento(new java.sql.Date(new Date().getTime()));
        genericDAO.insert(spesa);
      }
    }
    catch(Exception e){
      logger.error("Errore durante l'operazione di aggiunta/modifica spesa preventivata: " + spesa, e);
      return false;
    }
    return true;
  }
}
