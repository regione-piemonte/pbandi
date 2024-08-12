package it.csi.pbandi.pbweb.integration.dao.impl;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import it.csi.pbandi.pbweb.integration.dao.DocumentoDiSpesaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.util.UseCaseConstants;
import it.csi.pbandi.pbweb.business.manager.FatturaElettronicaManager;
import it.csi.pbandi.pbweb.dto.*;
import it.csi.pbandi.pbweb.dto.documentoDiSpesa.*;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.exception.DaoException;
import it.csi.pbandi.pbweb.integration.dao.DocumentoDiSpesaCulturaDAO;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbweb.integration.dao.impl.rowmapper.TipoDocumentiSpesaRowMapper;
import it.csi.pbandi.pbweb.integration.dao.request.PagamentiAssociatiRequest;
import it.csi.pbandi.pbweb.integration.vo.AffidamentoVO;
import it.csi.pbandi.pbweb.integration.vo.FornitoreAffidamentoVO;
import it.csi.pbandi.pbweb.integration.vo.TipoDocumentiSpesaVO;
import it.csi.pbandi.pbweb.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.DocumentoDiSpesaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.EsitoOperazioneDocumentoDiSpesa;
import it.csi.pbandi.pbweb.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweb.util.*;

@Service
public class DocumentoDiSpesaCulturaDAOImpl extends JdbcDaoSupport implements DocumentoDiSpesaCulturaDAO {

  @Autowired
  protected FileSqlUtil fileSqlUtil;
  @Autowired
  protected BeanUtil beanUtil;
  @Autowired
  protected InizializzazioneDAOImpl inizializzazioneDAOImpl;
  @Autowired
  protected DecodificheDAOImpl decodificheDAOImpl;
  @Autowired
  // protected ArchivioFileDAOImpl archivioFileDAOImpl;
  protected it.csi.pbandi.pbservizit.integration.dao.impl.ArchivioFileDAOImpl archivioFileDAOImpl;
  @Autowired
  protected ProfilazioneDAO profilazioneDao;
  @Autowired
  protected it.csi.pbandi.pbweb.pbandisrv.business.archivio.ArchivioBusinessImpl archivioBusinessImpl;
  @Autowired
  protected it.csi.pbandi.pbweb.pbandisrv.business.profilazione.ProfilazioneBusinessImpl profilazioneBusinessImpl;
  @Autowired
  protected it.csi.pbandi.pbweb.pbandisrv.business.gestionedocumentidispesa.GestioneDocumentiDiSpesaBusinessImpl gestioneDocumentiDiSpesaBusinessImp;
  @Autowired
  protected it.csi.pbandi.pbweb.pbandisrv.business.gestionevocidispesa.GestioneVociDiSpesaBusinessImpl gestioneVociDiSpesaBusinessImpl;
  @Autowired
  protected it.csi.pbandi.pbweb.pbandisrv.business.gestionepagamenti.GestionePagamentiBusinessImpl gestionePagamentiBusinessImpl;
  @Autowired
  protected DocumentoDiSpesaDAO documentoDiSpesaDAO;
  @Autowired
  protected it.csi.pbandi.pbweb.pbandisrv.business.manager.ProgettoManager progettoManager;
  // Selettori di ricerca documenti di spesa
  String SELETTORE_RICERCA_DOCUMENTI_GESTITI_NEL_PROGETTO = "GESTITI";
  String SELETTORE_RICERCA_DOCUMENTI_TUTTI = "TUTTI";
  String SELETTORE_RICERCA_RENDICONTAZIONE_CAPOFILA = "CAPOFILA";
  String SELETTORE_RICERCA_RENDICONTAZIONE_TUTTI_I_PARTNERS = "PARTNERS";
  String SEPARATORE_SOGGETTO_PROGETTO_PARTNER = ";";
  @Autowired
  it.csi.pbandi.pbweb.pbandisrv.business.validazionerendicontazione.ValidazioneRendicontazioneBusinessImpl validazioneRendicontazioneBusinessImpl;
  @Autowired
  FatturaElettronicaManager fatturaElettronicaManager;
  private final Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

  @Autowired
  public DocumentoDiSpesaCulturaDAOImpl(DataSource dataSource) {
    setDataSource(dataSource);
  }

  @Override
  public List<TipoDocumentiSpesaVO> ottieniTipologieDocumentiDiSpesaByBandoLinea(Long idBandoLinea, Long idProgetto) {
    String prf = "[DocumentoDiSpesaCulturaDAOImpl::ottieniTipologieDocumentiDiSpesaByBandoLinea] ";

    LOG.info(prf + "ottieniTipologieDocumentiDiSpesaByBandoLinea: idBandoLinea = " + idBandoLinea);
    List<TipoDocumentiSpesaVO> listTipoDocumentiSpesa = new ArrayList<TipoDocumentiSpesaVO>();
    String sql;
    try {
      sql = fileSqlUtil.getQuery("OttieniTipologieDocumentiDiSpesa.sql");
      listTipoDocumentiSpesa = getJdbcTemplate().query(sql, new Object[] { idBandoLinea },
          new TipoDocumentiSpesaRowMapper());
      LOG.info(prf + "ottieniTipologieDocumentiDiSpesaByBandoLinea: listTipoDocumentiSpesa.size = "
          + listTipoDocumentiSpesa.size());
    } catch (Exception e) {
      System.out.println("Eccezione EXc" + e.getMessage());
    }

    //aggiungo filtro atto di liquidazione con beneficiario pubblico
    BigDecimal tipoBeneficiario;
    try {
      tipoBeneficiario = documentoDiSpesaDAO.getTipoBeneficiario(idProgetto);
    } catch (Exception e) {
      LOG.error(prf + " ERRORE nel recuperare il tipo beneficiario del progetto: ", e);
      throw new RuntimeException(e);
    }
    if(tipoBeneficiario != null && tipoBeneficiario.intValue() == 2)
      return listTipoDocumentiSpesa.stream()
          .filter(tipoDoc -> tipoDoc.getDescBreveTipoDocSpesa().equals(Constants.COD_TIPO_DOC_DI_SPESA_ATTO_DI_LIQUIDAZIONE))
          .collect(java.util.stream.Collectors.toList());

    return listTipoDocumentiSpesa.stream()
        .filter(tipoDoc -> !tipoDoc.getDescBreveTipoDocSpesa().equals(Constants.COD_TIPO_DOC_DI_SPESA_ATTO_DI_LIQUIDAZIONE))
        .collect(java.util.stream.Collectors.toList());
  }


  private void selezionaAffidamentiPerIstruttore(List<AffidamentoVO> lista) {
    if (lista != null) {
      Iterator<AffidamentoVO> iterator = lista.iterator();
      while (iterator.hasNext()) {
        AffidamentoVO item = iterator.next();
        int idStato = item.getIdStatoAffidamento().intValue();
        if (idStato == Constants.ID_STATO_AFFIDAMENTO_DAINVIARE) {
          iterator.remove();
        }
      }
    }
  }

  private List<FornitoreAffidamentoVO> fornitoriAffidamento(Long idAppalto) throws DaoException {
    String prf = "[DocumentoDiSpesaCulturaDAOImpl::fornitoriAffidamento] ";
    LOG.info(prf + "BEGIN");
    List<FornitoreAffidamentoVO> result = null;
    try {
      LOG.info(prf + "idAppalto = " + idAppalto);

      if (idAppalto == null) {
        return new ArrayList<FornitoreAffidamentoVO>();
      }

      String sql = fileSqlUtil.getQuery("FornitoreAffidamentoVO.sql");
      LOG.info(prf + "\n" + sql + "\n con parametri: idAppalto = " + idAppalto);
      Object[] param = new Object[] { idAppalto };
      result = getJdbcTemplate().query(sql, param, new BeanRowMapper(FornitoreAffidamentoVO.class));
      LOG.info(prf + "Record trovati = " + result.size());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca dei fornitori di un affidamento: ", e);
      throw new DaoException(" ERRORE nella ricerca dei fornitori di un affidamento.", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  public List<DocumentoAllegatoDTO> allegatiFornitore(long idFornitore, long idProgetto, long idUtente,
      String idIride) throws Exception {
    String prf = "[DocumentoDiSpesaCulturaDAOImpl::allegatiFornitore] ";
    LOG.info(prf + "BEGIN");
    LOG.info(prf + "idFornitore = " + idFornitore + "idProgetto = " + idProgetto + "; idUtente = " + idUtente);

    if (idFornitore == 0) {
      throw new InvalidParameterException("idFornitore non valorizzato");
    }
    if (idProgetto == 0) {
      throw new InvalidParameterException("idProgetto non valorizzato");
    }
    if (idUtente == 0) {
      throw new InvalidParameterException("idUtente non valorizzato");
    }

    List<DocumentoAllegatoDTO> result = new ArrayList<DocumentoAllegatoDTO>();
    try {

      BigDecimal idEntita = decodificheDAOImpl.idDaDescrizione("PBANDI_C_ENTITA", "ID_ENTITA", "NOME_ENTITA",
          Constants.ENTITA_T_FORNITORE);
      if (idEntita == null)
        throw new DaoException("Id entita non trovato.");

      FileDTO[] files = archivioBusinessImpl.getFilesAssociatedFornitoriOrQualifiche(idUtente, idIride,
          idFornitore, idEntita.longValue(), idProgetto);

      for (FileDTO fileDTO : files) {
        DocumentoAllegatoDTO temp = new DocumentoAllegatoDTO();
        temp.setId(fileDTO.getIdDocumentoIndex());
        temp.setNome(fileDTO.getNomeFile());
        temp.setIdProgetto(fileDTO.getIdProgetto());
        temp.setDisassociabile(false);
        temp.setCodiceVisualizzatoProgetto(fileDTO.getCodiceVisualizzato());
        temp.setDtAssociazione(fileDTO.getDtAssociazione()); // Jira PBANDI-2890.

        result.add(temp);
      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella ricerca degli allegati del fornitore: ", e);
      throw new DaoException(" ERRORE nella ricerca degli allegati del fornitore.");
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }


  @Override
  // Ex pbandiweb
  // GestioneDocumentiDiSpesaBusinessImpl.salvaDocumentoDiSpesaSemplificazione()
  @Transactional(rollbackFor = {Exception.class})
  public EsitoDTO salvaDocumentoDiSpesa(DocumentoDiSpesaDTO documentoDiSpesaDTO, long idUtente, long progrBandoLinea, long tipoBeneficiario,
                                        HttpServletRequest req) throws Exception {
	String prf = "[DocumentoDiSpesaCulturaDAOImpl::salvaDocumentoDiSpesaCultura] ";
    LOG.info(prf + "BEGIN");
    LOG.info(prf + "idUtente = " + idUtente);

    if (documentoDiSpesaDTO == null) {
      throw new InvalidParameterException("documentoDiSpesaDTO non valorizzato");
    }
    if (idUtente == 0) {
      throw new InvalidParameterException("idUtente non valorizzato");
    }

    LOG.info(documentoDiSpesaDTO.toString());

    EsitoDTO esito = new EsitoDTO();
    try {

      String idIride = RequestUtil.idIrideInSessione(req);

      String descBreveTipoDocumentoDiSpesa = documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa();
      LOG.info(prf + "descBreveTipoDocumentoDiSpesa = " + descBreveTipoDocumentoDiSpesa);

      // Calcolo il totale del documento come:
      // CEDOLINO COSTI STANDARD:
      // - CEDOLINO o CEDOLINO COCOPRO : uguale all' imponibile
      // - SPESE FORFETTARIE: e' inserito dall' utente
      // - tutti gli altri casi e' dato dalla somma tra imponibil ed iva
      if (isCedolinoCostiStandard(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
          || isDocumentoGenerico(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
          || isAutocertificazioneSpese(descBreveTipoDocumentoDiSpesa)
          || isSALSenzaQuietanza(descBreveTipoDocumentoDiSpesa)
          || isSALConQuietanza(descBreveTipoDocumentoDiSpesa)) {
        LOG.info(prf + documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa()
            + ": setto importo totale e imponibile col rendicontabile: "
            + documentoDiSpesaDTO.getImportoRendicontabile());
        documentoDiSpesaDTO.setImportoTotaleDocumentoIvato(documentoDiSpesaDTO.getImportoRendicontabile());
        documentoDiSpesaDTO.setImponibile(documentoDiSpesaDTO.getImportoRendicontabile());
      } else if (isCedolino(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
          || isCedolinoCOCOPRO(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())) {
        documentoDiSpesaDTO.setImportoTotaleDocumentoIvato(documentoDiSpesaDTO.getImponibile());
      } else if (!isSpeseForfettarie(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
          && !isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
          && !isDocumentoGenerico(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
          && !isSpesaExtraAffidamento(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())) {
        Double importoIva = NumberUtil.toRoundDouble(documentoDiSpesaDTO.getImportoIva());
        Double imponibile = documentoDiSpesaDTO.getImponibile();

        documentoDiSpesaDTO.setImportoTotaleDocumentoIvato(NumberUtil.sum(imponibile, importoIva));
      }

      // Se il tipo di documento e' SPESE FORFETTARIE allora
      // l'importo rendicondabile e l'imponibile sono uguali all'importo totale del
      // documento.
      if (isSpeseForfettarie(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
          || isSpeseGeneraliForfettarieCostiStandard(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())
          || isSpesaExtraAffidamento(documentoDiSpesaDTO.getDescBreveTipoDocumentoDiSpesa())) {
        documentoDiSpesaDTO.setImportoRendicontabile(documentoDiSpesaDTO.getImportoTotaleDocumentoIvato());
        documentoDiSpesaDTO.setImponibile(documentoDiSpesaDTO.getImportoTotaleDocumentoIvato());
      }

      // modifica per bug clona/nuovo documento
      documentoDiSpesaDTO.setCodiceFiscaleFornitore(null);

      logger.info(
          "SALVA DOCUMENTO DI SPESA =====> FLAG ELETT XML ====> " + documentoDiSpesaDTO.getFlagElettXml());

      EsitoOperazioneDocumentoDiSpesa esitoPbandisrv = gestioneDocumentiDiSpesaBusinessImp
          .salvaDocumentoDiSpesa(idUtente, idIride, documentoDiSpesaDTO, progrBandoLinea);
      esito.setEsito(esitoPbandisrv.getEsito());
      esito.setId(esitoPbandisrv.getIdDocumentoDiSpesa());
      if (esitoPbandisrv.getMessaggi() != null && esitoPbandisrv.getMessaggi()[0] != null)
        esito.setMessaggio(esitoPbandisrv.getMessaggi()[0].getMsgKey());
      if (esito.getEsito() && StringUtil.isEmpty(esito.getMessaggio()))
        esito.setMessaggio(ErrorMessages.OPERAZIONE_ESEGUITA_CON_SUCCESSO);
      LOG.info(esito.toString());

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nel salvare il documento di spesa: ", e);
      throw new DaoException(" ERRORE nel salvare il documento di spesa.");
    } finally {
      LOG.info(prf + " END");
    }

    return esito;
  }

  private it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO trasforma(
      DocumentoDiSpesaDTO dto) {

    it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO newDto = new it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.DocumentoDiSpesaDTO();

    newDto.setCodiceFiscaleFornitore(dto.getCodiceFiscaleFornitore());
    newDto.setCodiceProgetto(dto.getCodiceProgetto());
    newDto.setCognomeFornitore(dto.getCognomeFornitore());
    newDto.setDataDocumentoDiSpesa(dto.getDataDocumentoDiSpesa());
    newDto.setDataDocumentoDiSpesaDiRiferimento(dto.getDataDocumentoDiSpesaDiRiferimento());
    newDto.setDescrizioneDocumentoDiSpesa(dto.getDescrizioneDocumentoDiSpesa());
    newDto.setDestinazioneTrasferta(dto.getDestinazioneTrasferta());
    newDto.setDenominazioneFornitore(dto.getDenominazioneFornitore());
    newDto.setDescStatoDocumentoSpesa(dto.getDescStatoDocumentoSpesa());
    newDto.setDescTipoDocumentoDiSpesa(dto.getDescTipologiaDocumentoDiSpesa());
    newDto.setDescTipologiaFornitore(dto.getDescTipologiaFornitore());
    newDto.setDurataTrasferta(dto.getDurataTrasferta());
    newDto.setIdBeneficiario(dto.getIdBeneficiario());
    newDto.setIdDocRiferimento(dto.getIdDocRiferimento());
    newDto.setIdDocumentoDiSpesa(dto.getIdDocumentoDiSpesa());
    newDto.setIdFornitore(dto.getIdFornitore());
    newDto.setIdProgetto(dto.getIdProgetto());
    newDto.setIdSoggetto(dto.getIdSoggetto());
    newDto.setIdSoggettoPartner(dto.getIdSoggettoPartner());
    newDto.setIdTipoDocumentoDiSpesa(dto.getIdTipoDocumentoDiSpesa());
    newDto.setIdTipoFornitore(dto.getIdTipoFornitore());
    newDto.setIdTipoOggettoAttivita(dto.getIdTipoOggettoAttivita());
    newDto.setImponibile(dto.getImponibile());
    newDto.setImportoIva(dto.getImportoIva());
    newDto.setImportoIvaACosto(dto.getImportoIvaACosto());
    newDto.setImportoRendicontabile(dto.getImportoRendicontabile());
    newDto.setImportoTotaleDocumentoIvato(dto.getImportoTotaleDocumentoIvato());
    newDto.setImportoRitenutaDAcconto(dto.getImportoRitenutaDAcconto());
    newDto.setIsGestitiNelProgetto(dto.getIsGestitiNelProgetto());
    newDto.setIsRicercaPerCapofila(dto.getIsRicercaPerCapofila());
    newDto.setIsRicercaPerTutti(dto.getIsRicercaPerTutti());
    newDto.setIsRicercaPerPartner(dto.getIsRicercaPerPartner());
    newDto.setNomeFornitore(dto.getNomeFornitore());
    newDto.setNumeroDocumento(dto.getNumeroDocumento());
    newDto.setNumeroDocumentoDiSpesa(dto.getNumeroDocumentoDiSpesa());
    newDto.setNumeroDocumentoDiSpesaDiRiferimento(dto.getNumeroDocumentoDiSpesaDiRiferimento());
    newDto.setPartitaIvaFornitore(dto.getPartitaIvaFornitore());
    newDto.setPartner(dto.getPartner());
    newDto.setCostoOrario(dto.getCostoOrario());
    newDto.setStatiDocumento(null);
    newDto.setTask(dto.getTask());
    newDto.setImportoTotaleValidato(dto.getImportoTotaleValidato());
    newDto.setDescBreveTipoDocumentoDiSpesa(dto.getDescBreveTipoDocumentoDiSpesa());
    newDto.setImportoTotaleRendicontato(dto.getImportoTotaleRendicontato());
    newDto.setProgrFornitoreQualifica(dto.getProgrFornitoreQualifica());
    newDto.setImportoTotaleQuietanzato(dto.getImportoTotaleQuietanzato());
    newDto.setIdStatoDocumentoSpesa(dto.getIdStatoDocumentoSpesa());
    newDto.setRendicontabileQuietanzato(dto.getRendicontabileQuietanzato());
    newDto.setIsValidabile(null);
    newDto.setMsgNonValidabile(null);
    newDto.setTipoInvio(dto.getTipoInvio());
    newDto.setIdAppalto(dto.getIdAppalto());
    newDto.setNoteValidazione(dto.getNoteValidazione());

    LOG.info("\n\n IdTipoDocumentoDiSpesa() = " + newDto.getIdTipoDocumentoDiSpesa() + "  --  "
        + newDto.getIdTipoDocumentoDiSpesa());
    LOG.info("\n\n CodiceFiscaleFornitore() = " + newDto.getCodiceFiscaleFornitore() + "  --  "
        + newDto.getCodiceFiscaleFornitore());

    return newDto;
  }

  private boolean isSpesaExtraAffidamento(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null
        && descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SPESE_EXTRA_AFFIDAMENTO);
    return result;
  }

  private boolean isSpeseGeneraliForfettarieCostiStandard(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null && descBreveTipoDocumento
        .equals(Constants.COD_TIPO_DOC_DI_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_STANDARD);
    return result;
  }

  private boolean isCedolinoCOCOPRO(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null
        && descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_CEDOLINO_COCOPRO);
    return result;
  }

  private boolean isCedolino(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null
        && descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_CEDOLINO);
    return result;
  }

  private boolean isSpeseForfettarie(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null
        && descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SPESE_FORFETTARIE);
    return result;
  }

  private boolean isDocumentoGenerico(String descBreveTipoDocumento) {
    return descBreveTipoDocumento != null
        && (descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_DOCUMENTO_GENERICO));
  }

  private boolean isCedolinoCostiStandard(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null
        && descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_CEDOLINO_COSTI_STANDARD);
    return result;
  }

  private boolean isAutocertificazioneSpese(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null
        && descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_AUTOCERTIFICAZIONE_SPESE);
    return result;
  }

  private boolean isSALSenzaQuietanza(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null
        && descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SAL_SENZA_QUIETANZA);
    return result;
  }

  private boolean isSALConQuietanza(String descBreveTipoDocumento) {
    boolean result = descBreveTipoDocumento != null
        && descBreveTipoDocumento.equals(Constants.COD_TIPO_DOC_DI_SPESA_SAL_CON_QUIETANZA);
    return result;
  }

  @Override
  // Ex pbandiweb
  // GestioneDocumentiDiSpesaBusinessImpl.salvaDocumentoDiSpesaSemplificazione()
  @Transactional(rollbackFor = { Exception.class })
  public EsitoDTO associaVoceDiSpesa(VoceDiSpesaDTO voceDiSpesaDTO, long idUtente, HttpServletRequest req)
      throws Exception {
    String prf = "[DocumentoDiSpesaCulturaDAOImpl::associaVoceDiSpesa] ";
    LOG.info(prf + "BEGIN");
    LOG.info(prf + "idUtente = " + idUtente);

    if (voceDiSpesaDTO == null) {
      throw new InvalidParameterException("voceDiSpesaDTO non valorizzato");
    }
    if (idUtente == 0) {
      throw new InvalidParameterException("idUtente non valorizzato");
    }

    EsitoDTO esito = new EsitoDTO();
    try {
      LOG.info(prf + "voceDiSpesa.IdQuotaParteDocSpesa = " + voceDiSpesaDTO.getIdQuotaParteDocSpesa());

      String idIride = RequestUtil.idIrideInSessione(req);

      Long idUtenteLong = idUtente;
      Long idDocSpesa = voceDiSpesaDTO.getIdDocSpesa();
      Long idProgetto = voceDiSpesaDTO.getIdProgetto();
      Boolean forzaSalvataggio = true;

      // Da oggetto pbweb a oggetto pbandisrv.
      it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO dtoPbandisrv = beanUtil.transform(
          voceDiSpesaDTO, it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.VoceDiSpesaDTO.class);

      it.csi.pbandi.pbweb.pbandisrv.dto.gestionevocidispesa.EsitoOperazioneVociDiSpesa esitpPbandisrv = null;
      if (voceDiSpesaDTO.getIdQuotaParteDocSpesa() == null
          || voceDiSpesaDTO.getIdQuotaParteDocSpesa().intValue() == 0) {
        LOG.info(prf + "Eseguo gestioneVociDiSpesaBusinessImpl.associaVoceDiSpesaDocumentoSemplificazione");
        esitpPbandisrv = gestioneVociDiSpesaBusinessImpl.associaVoceDiSpesaDocumentoSemplificazione(
            idUtenteLong, idIride, idProgetto, idDocSpesa, dtoPbandisrv, forzaSalvataggio);
      } else {
        LOG.info(prf + "Eseguo gestioneVociDiSpesaBusinessImpl.modificaVoceDiSpesaDocumentoSemplificazione");
        esitpPbandisrv = gestioneVociDiSpesaBusinessImpl.modificaVoceDiSpesaDocumentoSemplificazione(
            idUtenteLong, idIride, idProgetto, idDocSpesa, dtoPbandisrv, forzaSalvataggio);
      }

      esito.setEsito(esitpPbandisrv.getEsito());
      esito.setMessaggio(TraduttoreMessaggiPbandisrv.traduci(esitpPbandisrv.getMessage()));
      if (esitpPbandisrv.getVoceDiSpesa() != null) {
        esito.setId(esitpPbandisrv.getVoceDiSpesa().getIdQuotaParteDocSpesa());
      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nel salvare il documento di spesa: ", e);
      throw new DaoException(" ERRORE nel salvare il documento di spesa.");
    } finally {
      LOG.info(prf + " END");
    }

    return esito;
  }

  @Override
  public List<VoceDiSpesaPadre> vociDiSpesaRicerca(long idProgetto, HttpServletRequest req)
      throws Exception {
    String prf = "[DecodificheDAOImpl::vociDiSpesaRicerca] ";
    LOG.info(prf + " BEGIN");
    LOG.info(prf + " input: idProgetto = " + idProgetto);

    if (idProgetto == 0) {
      throw new InvalidParameterException("idProgetto non valorizzato.");
    }

    List<VoceDiSpesaPadre> result = new ArrayList<VoceDiSpesaPadre>();
    try {

      UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

      it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.VoceDiSpesaPadreDTO[] vdsDto = null;
      vdsDto = gestioneDocumentiDiSpesaBusinessImp.findAllVociDiSpesaPadreConFigliCultura(userInfo.getIdUtente(),
          userInfo.getIdIride(), idProgetto);

      result = new ArrayList<VoceDiSpesaPadre>(vdsDto.length);
      Map<String, String> figlieMap = new HashMap<String, String>() {
        {
          put("codice", "idVoceDiSpesa");
          put("descrizione", "descVoceDiSpesa");
        }
      };
      for (int i = 0; i < vdsDto.length; i++) {
        VoceDiSpesaPadre temp = beanUtil.transform(vdsDto[i], VoceDiSpesaPadre.class);
        temp.setVociDiSpesaFiglie(beanUtil.transformToArrayList(vdsDto[i].getVociDiSpesaFiglie(),
            VoceDiSpesaFiglia.class, figlieMap));
        result.add(temp);
      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE: ", e);
      throw new DaoException(" ERRORE nella ricerca delle voci di spesa: ", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }

  @Override
  // Popola la combo "Rendicontazione" nella finestra di ricerca dei Documenti Di
  // Spesa (Rendicontazione).
  // Contiene i partners di un beneficiario capofila.
  // Ex
  // GestioneDocumentiDiSpesaBusinessImpl.findFiltroRicercaRendicontazionePartners()
  public FiltroRicercaRendicontazionePartners partners(long idProgetto, long idBandoLinea, HttpServletRequest req)
      throws Exception {
    String prf = "[DecodificheDAOImpl::partners] ";
    LOG.info(prf + " BEGIN");
    LOG.info(prf + " input: idProgetto = " + idProgetto + "; idBandoLinea = " + idBandoLinea);

    if (idProgetto == 0) {
      throw new InvalidParameterException("idProgetto non valorizzato.");
    }
    if (idBandoLinea == 0) {
      throw new InvalidParameterException("idBandoLinea non valorizzato.");
    }

    FiltroRicercaRendicontazionePartners result = new FiltroRicercaRendicontazionePartners();
    try {

      UserInfoSec userInfo = (UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

      it.csi.pbandi.pbweb.pbandisrv.dto.gestionedocumentidispesa.PartnerDTO[] partnersrv = null;
      partnersrv = gestioneDocumentiDiSpesaBusinessImp.findPartners(userInfo.getIdUtente(), userInfo.getIdIride(),
          idProgetto);

      ArrayList<DecodificaDTO> elenco = new ArrayList<DecodificaDTO>();
      for (int i = 0; i < partnersrv.length; i++) {
        DecodificaDTO obj = new DecodificaDTO();
        obj.setDescrizioneBreve(
            partnersrv[i].getIdSoggetto() + Constants.SEPARATORE_SOGGETTO_PROGETTO_PARTNER
                + partnersrv[i].getIdProgettoPartner());
        obj.setDescrizione(partnersrv[i].getDescrizione());
        elenco.add(obj);
      }

      ArrayList<DecodificaDTO> opzioni = new ArrayList<DecodificaDTO>();
      DecodificaDTO capofila = new DecodificaDTO();
      capofila.setDescrizioneBreve(Constants.VALUE_RADIO_RENDICONTAZIONE_CAPOFILA);
      capofila.setDescrizione("Capofila");
      opzioni.add(capofila);

      if (elenco != null && elenco.size() != 0) {
        DecodificaDTO tuttiPartners = new DecodificaDTO();
        tuttiPartners.setDescrizioneBreve(Constants.VALUE_RADIO_RENDICONTAZIONE_PARTNERS);
        tuttiPartners.setDescrizione("Tutti i Partners");
        opzioni.add(tuttiPartners);
      }

      boolean regolaAttiva = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(userInfo.getIdUtente(),
          userInfo.getIdIride(), idBandoLinea, RegoleConstants.BR08_FILTRI_RICERCA_BENEFICIARIO_CAPOFILA);

      boolean beneficiarioCapofila = false;
      if (regolaAttiva) {
        beneficiarioCapofila = progettoManager.isCapofila(new BigDecimal(idProgetto));
      }
      LOG.info(prf + "regolaAttiva = " + regolaAttiva + "; beneficiarioCapofila = " + beneficiarioCapofila);

      result.setOpzioni(opzioni);
      result.setPartners(elenco);
      result.setVisibile(beneficiarioCapofila);

    } catch (Exception e) {
      LOG.error(prf + " ERRORE: ", e);
      throw new DaoException(" ERRORE nella ricerca dei partners: ", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }
  @Override
  // Dato l'id recupera il dettaglio di un documento di spesa.
  // Ex
  // GestioneDocumentiDiSpesaBusinessImpl.findDettaglioDocumentoDiSpesaSemplificazione()
  public DocumentoDiSpesaDTO documentoDiSpesa(long idDocumentoDiSpesa, long idProgetto, long idUtente, String idIride)
      throws Exception {
    String prf = "[DocumentoDiSpesaCulturaDAOImpl::documentoDiSpesa] ";
    LOG.info(prf + "BEGIN");
    LOG.info(prf + "input: idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; idProgetto = " + idProgetto
        + "; idUtente = " + idUtente);

    DocumentoDiSpesaDTO result = null;
    try {

      result = gestioneDocumentiDiSpesaBusinessImp.findDettaglioDocumentoDiSpesaSemplificazione(idUtente, idIride,
          idDocumentoDiSpesa, idProgetto);
      LOG.info(result.toString());

    } catch (Exception e) {
      LOG.error(prf + "ERRORE nella ricerca del dettaglio del documento di spesa. ", e);
      throw new DaoException("ERRORE nella ricerca del dettaglio del documento di spesa ", e);
    } finally {
      LOG.info(prf + " END");
    }

    return result;
  }
  @Override
  // Ex pbandiweb
  // GestioneDocumentiDiPagamentoBusinessImpl.findPagamentiAssociatiDocumentoDiSpesaSemplificazione()
  // validazione: true se si arrviva da Validazione o da ValidazioneFinale.
  public List<DocumentoDiPagamentoDTO> pagamentiAssociati(PagamentiAssociatiRequest pagamentiAssociatiRequest,
      long idUtente, String idIride, long idSoggetto) throws Exception {
    String prf = "[DocumentoDiSpesaCulturaDAOImpl::pagamentiAssociati] ";
    LOG.info(prf + "BEGIN");

    if (pagamentiAssociatiRequest == null) {
      throw new InvalidParameterException("pagamentiAssociatiRequest non valorizzato");
    }

    Long idDocumentoDiSpesa = pagamentiAssociatiRequest.getIdDocumentoDiSpesa();
    String tipoInvioDocumentoDiSpesa = pagamentiAssociatiRequest.getTipoInvioDocumentoDiSpesa();
    String descBreveStatoDocSpesa = pagamentiAssociatiRequest.getDescBreveStatoDocSpesa();
    String tipoOperazioneDocSpesa = pagamentiAssociatiRequest.getTipoOperazioneDocSpesa();
    Long idProgetto = pagamentiAssociatiRequest.getIdProgetto();
    Long idBandoLinea = pagamentiAssociatiRequest.getIdBandoLinea();
    String codiceRuolo = pagamentiAssociatiRequest.getCodiceRuolo();
    Boolean validazione = pagamentiAssociatiRequest.getValidazione();

    if (idDocumentoDiSpesa == 0) {
      throw new InvalidParameterException("idDocumentoDiSpesa non valorizzato");
    }
    if (StringUtil.isEmpty(tipoInvioDocumentoDiSpesa)) {
      throw new InvalidParameterException("tipoInvioDocumentoDiSpesa non valorizzato");
    }
    if (StringUtil.isEmpty(descBreveStatoDocSpesa)) {
      throw new InvalidParameterException("descBreveStatoDocSpesa non valorizzato");
    }
    if (idProgetto == 0) {
      throw new InvalidParameterException("idProgetto non valorizzato");
    }
    if (idBandoLinea == 0) {
      throw new InvalidParameterException("idBandoLinea non valorizzato");
    }
    if (StringUtil.isEmpty(codiceRuolo)) {
      throw new InvalidParameterException("codiceRuolo non valorizzato");
    }
    LOG.info(prf + "idDocumentoDiSpesa = " + idDocumentoDiSpesa + "; tipoInvioDocumentoDiSpesa = "
        + tipoInvioDocumentoDiSpesa + "; descBreveStatoDocSpesa = " + descBreveStatoDocSpesa
        + "; tipoOperazioneDocSpesa = " + tipoOperazioneDocSpesa + "; idProgetto = " + idProgetto
        + "; idBandoLinea = " + idBandoLinea + "; codiceRuolo = " + codiceRuolo + "; validazione = "
        + validazione + "; idSoggetto = " + idSoggetto + "; idUtente = " + idUtente);

    List<DocumentoDiPagamentoDTO> list = new ArrayList<DocumentoDiPagamentoDTO>();
    try {

      it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO[] listPagamentiAssociati = null;
      if (validazione) {
        listPagamentiAssociati = gestionePagamentiBusinessImpl.findPagamentiAssociatiPerValidazione(idUtente,
            idIride, idDocumentoDiSpesa, idBandoLinea, idProgetto);
      } else {
        listPagamentiAssociati = gestionePagamentiBusinessImpl.findPagamentiAssociati(idUtente, idIride,
            idDocumentoDiSpesa, idBandoLinea, idProgetto);
      }

      boolean isAbilitatoModificaPagamento = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
          UseCaseConstants.UC_OPEREN016); // OPEREN016 Modifica
      boolean isAbilitatoEliminaPagamento = profilazioneDao.hasPermesso(idSoggetto, idUtente, codiceRuolo,
          UseCaseConstants.UC_OPEREN019); // OPEREN019 Elimina

      boolean isDocumentoDichiarabileAllProgetti = gestioneDocumentiDiSpesaBusinessImp
          .isDocumentoDichiarabileInAllProgetti(idUtente, idIride, idDocumentoDiSpesa, idProgetto);

      if (listPagamentiAssociati != null && listPagamentiAssociati.length > 0) {

        for (int i = 0; i < listPagamentiAssociati.length; i++) {
          // 26 giugno 2015
          boolean isPagamentoModificabile = isAbilitatoModificaPagamento
              && isDocumentoDichiarabileAllProgetti;

          boolean isPagamentoEliminabile = isAbilitatoEliminaPagamento && isDocumentoDichiarabileAllProgetti;
          DocumentoDiPagamentoDTO dto = rimappaObjPagamentoClient(listPagamentiAssociati[i]);

          // Se sono in validazione l' utente puo' solamente modificare i
          // pagamenti che sono in stato diverso da INSERITO e RESPINTO se
          // e' abilitato al caso d'uso

          // FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
          // String statoPagamento = dto.getDescStatoValidazioneSpesa();

          // }L{ tolto dal blocco non-validazione: e' buono per entrambi
          if (Constants.TIPO_INVIO_DIGITALE.equalsIgnoreCase(tipoInvioDocumentoDiSpesa)) {

            // Ricerco i documenti allegati al pagamento

            LOG.info(prf + "cerco gli allegati al pagamento" + dto.getId()
                + " tramite archivioBusinessImpl.getFilesAssociatedPagamento");
            FileDTO[] files = archivioBusinessImpl.getFilesAssociatedPagamento(idUtente, idIride,
                dto.getId());

            // E' possibile allegare file ai pagamenti se e solo se il
            // tipo di invio del documento di spesa e' D e per il
            // bandolinea e' abilitata la regola BR42
            boolean regolaFileAttiva = profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente,
                idIride, idBandoLinea, RegoleConstants.BR42_ABILITAZIONE_ALLEGA_FILE);
            regolaFileAttiva = regolaFileAttiva
                || profilazioneBusinessImpl.isRegolaApplicabileForBandoLinea(idUtente, idIride,
                    idBandoLinea, RegoleConstants.BR52_UPLOAD_ALLEGATI_QUIETANZA);
            LOG.info(prf + "regolaFileAttiva = " + regolaFileAttiva);

            if (regolaFileAttiva) {

              // }L{ l'istruttore non allega files
              dto.setLinkAllegaFileVisible(!validazione);

              if (!ObjectUtil.isEmpty(files)) {
                ArrayList<DocumentoAllegatoDTO> allegati = new ArrayList<DocumentoAllegatoDTO>();
                for (FileDTO file : files) {
                  DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
                  allegato.setId(file.getIdDocumentoIndex());
                  allegato.setNome(file.getNomeFile());
                  allegato.setIdParent(file.getIdFolder());

                  // Un allegato e' dissocibile se il documento e' in stato a DICHIARABILE o
                  // RESPINTO per tutti i progetti
                  // e se l'attivita' corrente e' quella della dichiarazione.
                  if (!validazione) {
                    // boolean isDocumentoDichiarabileAllProgetti =
                    // gestioneDocumentiDiSpesaBusinessImpl
                    // .isDocumentoDichiarabileInAllProgetti(user, idProgetto, documento.getId());
                    allegato.setDisassociabile(isDocumentoDichiarabileAllProgetti);
                  } else {
                    allegato.setDisassociabile(false);
                  }
                  allegati.add(allegato);
                }
                dto.setDocumentiAllegati(allegati);
              }

            } else {
              // }L{ 2410: deve comunque essere possibile vedere i
              // file allegati
              logger.info("tipo invio non digitale: disabilito LinkAllegaFile ");
              dto.setLinkAllegaFileVisible(false);

              if (!ObjectUtil.isEmpty(files)) {
                ArrayList<DocumentoAllegatoDTO> allegati = new ArrayList<DocumentoAllegatoDTO>();
                for (FileDTO file : files) {
                  DocumentoAllegatoDTO allegato = new DocumentoAllegatoDTO();
                  allegato.setId(file.getIdDocumentoIndex());
                  allegato.setNome(file.getNomeFile());
                  allegato.setIdParent(file.getIdFolder());
                  // In questo caso non posso fare nulla all'allegato
                  allegato.setDisassociabile(false);
                  allegati.add(allegato);
                }
                dto.setDocumentiAllegati(allegati);
              }
            }

          } // fine if tipo invio = D

          boolean isPagamentoQuietanzato = listPagamentiAssociati[i].getIsQuietanzato();
          boolean isPagamentoUsedDichiarazioni = listPagamentiAssociati[i].getIsUsedDichiarazioni();
          if (validazione) {
            isPagamentoEliminabile = false;
            if (isPagamentoModificabile &&
            // FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
            // (statoPagamento.equalsIgnoreCase("INSERITO") ||
            // statoPagamento.equalsIgnoreCase("RESPINTO"))
                isPagamentoQuietanzato)
              isPagamentoModificabile = false;
          } else {

            // Sono modificabili i pagamenti che:
            // - utente abilitato al caso d'uso
            // - tipo di operazione non e' DETTAGLIO
            // - il documento e' in stato DICHIARABILE o DA COMPLETARE
            // - se il documento e' in stato DICHIARABILE allora il pagamento non deve
            // essere stato
            // ripartito tra le voci di spesa
            // - se il documento e' in stato DA COMPLETARE allora il pagamento non deve
            // essere stato
            // ripartito tra le voci di spesa e non deve essere stato utilizzato nelle
            // validazioni.
            // - il doc ï¿½ isDocumentoDichiarabileInAltriprogetti

            if (!Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(descBreveStatoDocSpesa)
                && !Constants.CODICE_STATO_DOCUMENTO_DA_COMPLETARE
                    .equalsIgnoreCase(descBreveStatoDocSpesa)) {
              isPagamentoModificabile = false;
            }

            if (isPagamentoModificabile
            // FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
            // (!statoPagamento.equalsIgnoreCase("INSERITO") &&
            // !statoPagamento.equalsIgnoreCase("RESPINTO"))
            ) {

              if (Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE.equalsIgnoreCase(descBreveStatoDocSpesa)
                  && isPagamentoQuietanzato)
                isPagamentoModificabile = false;
              else if (Constants.CODICE_STATO_DOCUMENTO_DA_COMPLETARE
                  .equalsIgnoreCase(descBreveStatoDocSpesa) && isPagamentoUsedDichiarazioni)
                isPagamentoModificabile = false;
            }

            if (isPagamentoModificabile && tipoOperazioneDocSpesa != null && tipoOperazioneDocSpesa
                .equals(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO)) {
              isPagamentoModificabile = false;
            }

            // Sono modificabili i pagamenti che:
            // - utente abilitato al caso d'uso
            // - tipo di operazione non e' DETTAGLIO
            // - il documento e' in stato DICHIARABILE o DA COMPLETARE
            // - se il documento e' in stato DICHIARABILE allora il pagamento non deve
            // essere stato
            // ripartito tra le voci di spesa o non deve essere stato utilizzato nelle
            // validazioni.
            // - se il documento e' in stato DA COMPLETARE allora il pagamento non deve
            // essere stato
            // ripartito tra le voci di spesa e non deve essere stato utilizzato nelle
            // validazioni.

            if (!descBreveStatoDocSpesa.equalsIgnoreCase(Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE)
                && !descBreveStatoDocSpesa
                    .equalsIgnoreCase(Constants.CODICE_STATO_DOCUMENTO_DA_COMPLETARE)) {
              isPagamentoEliminabile = false;
            }

            if (isPagamentoEliminabile
            // FIX PBANDI-2314: Non esiste piu' lo stato del pagamento
            // (!statoPagamento.equalsIgnoreCase("INSERITO") &&
            // !statoPagamento.equalsIgnoreCase("RESPINTO"))) {
            ) {
              if (descBreveStatoDocSpesa.equalsIgnoreCase(Constants.CODICE_STATO_DOCUMENTO_DICHIARABILE)
                  && (isPagamentoQuietanzato || isPagamentoUsedDichiarazioni))
                isPagamentoEliminabile = false;
              else if (descBreveStatoDocSpesa.equalsIgnoreCase(
                  Constants.CODICE_STATO_DOCUMENTO_DA_COMPLETARE) && isPagamentoUsedDichiarazioni)
                isPagamentoEliminabile = false;
            }

            if (isPagamentoEliminabile && tipoOperazioneDocSpesa != null && tipoOperazioneDocSpesa
                .equals(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO)) {
              isPagamentoEliminabile = false;
            }

          }
          dto.setPagamentoModificabile(isPagamentoModificabile);
          dto.setPagamentoEliminabile(isPagamentoEliminabile);

          // Alex: in visualizzazione non posso associare o disassociare gli allegati al
          // pagamento.
          if (tipoOperazioneDocSpesa != null
              && tipoOperazioneDocSpesa.equals(Constants.TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO)) {
            dto.setLinkAllegaFileVisible(false);
            if (dto.getDocumentiAllegati() != null) {
              for (DocumentoAllegatoDTO d : dto.getDocumentiAllegati()) {
                d.setDisassociabile(false);
              }
            }
          }

          list.add(dto);

        }
      }

    } catch (Exception e) {
      LOG.error(prf + " ERRORE nella lettura dei pagamenti del documento di spesa: ", e);
      throw new DaoException(" ERRORE nella lettura dei pagamenti del documento di spesa.");
    } finally {
      LOG.info(prf + " END");
    }

    return list;
  }

  private DocumentoDiPagamentoDTO rimappaObjPagamentoClient(
      it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.PagamentoDTO pagamentoDTO) {
    DocumentoDiPagamentoDTO documentoPagamento = new DocumentoDiPagamentoDTO();
    if (pagamentoDTO.getIdPagamento() != null)
      documentoPagamento.setId(pagamentoDTO.getIdPagamento());
    documentoPagamento.setDtPagamento(DateUtil.getDate(pagamentoDTO.getDtPagamento()));
    documentoPagamento.setCausalePagamento(pagamentoDTO.getCausalePagamento());
    documentoPagamento.setDescBreveModalitaPagamento(pagamentoDTO.getDescBreveModalitaPagamento());
    documentoPagamento.setDescrizioneModalitaPagamento(pagamentoDTO.getDescrizioneModalitaPagamento());
    if (pagamentoDTO.getImportoQuietanzato() == null || pagamentoDTO.getImportoQuietanzato().doubleValue() == 0d) {
      documentoPagamento.setImportoResiduoUtilizzabileVuoto(Boolean.TRUE);
    } else {
      documentoPagamento.setImportoResiduoUtilizzabileVuoto(Boolean.FALSE);
      documentoPagamento.setImportoResiduoUtilizzabile(pagamentoDTO.getImportoResiduoUtilizzabile());
    }

    documentoPagamento.setDestinatarioPagamento(pagamentoDTO.getDestinatarioPagamento());
    documentoPagamento.setEstremiPagamento(pagamentoDTO.getEstremiPagamento());
    if (pagamentoDTO.getIdModalitaPagamento() != null)
      documentoPagamento.setIdModalitaPagamento(pagamentoDTO.getIdModalitaPagamento());
    if (pagamentoDTO.getIdSoggetto() != null)
      documentoPagamento.setIdSoggetto(pagamentoDTO.getIdSoggetto());
    if (pagamentoDTO.getImportoPagamento() != null)
      documentoPagamento.setImportoPagamento(pagamentoDTO.getImportoPagamento());
    if (pagamentoDTO.getImportoRendicontabilePagato() != null)
      documentoPagamento.setImportoRendicontabilePagato(pagamentoDTO.getImportoRendicontabilePagato());
    documentoPagamento.setIdCausalePagamento(pagamentoDTO.getIdCausalePagamento());
    documentoPagamento.setRifPagamento(pagamentoDTO.getRifPagamento());
    return documentoPagamento;
  }

}
