/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.business.service.impl;

import it.csi.pbandi.pbservizit.dto.EsitoDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatididominio.GestioneDatiDiDominioBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.gestionedatigenerali.GestioneDatiGeneraliBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.SoggettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatididominio.Decodifica;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DatiGeneraliDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.DelegatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestionedatigenerali.ImportoAgevolatoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoReportRichiestaErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.EsitoRichiestaErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RichiestaErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAffidServtecArVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.profilazione.ProfilazioneSrv;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.UserInfoHelper;
import it.csi.pbandi.pbservizit.pbandiutil.commonweb.business.impl.EsecuzioneAttivitaBusinessImpl;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbweberog.business.service.ErogazioneService;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.erogazione.*;
import it.csi.pbandi.pbweberog.integration.dao.ErogazioneDAO;
import it.csi.pbandi.pbweberog.integration.request.*;
import it.csi.pbandi.pbweberog.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ErogazioneServiceImpl implements ErogazioneService {

  @Autowired
  protected BeanUtil beanUtil;

  @Autowired
  protected RegolaManager regolaManager;

  @Autowired
  ErogazioneDAO erogazioneDAO;
  @Autowired
  GestioneDatiGeneraliBusinessImpl datiGeneraliBusinessImpl;
  @Autowired
  GestioneDatiDiDominioBusinessImpl datiDiDominioBusinessImpl;
  @Autowired
  UserInfoHelper userInfoHelper;
  @Autowired
  ProfilazioneSrv profilazioneSrv;
  @Autowired
  EsecuzioneAttivitaBusinessImpl esecuzioneAttivitaBusinessImpl;
  @Autowired
  private SoggettoManager soggettoManager;
  private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

  @Override
  public Response getErogazione(Long idU, Long idSoggettoBeneficiario, Long idProgetto, HttpServletRequest req) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getVerificaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();

      DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idU, idIride, idProgetto, idSoggettoBeneficiario);
      Long idBandoLinea = datiGenerali.getIdBandoLinea();
      Long idFormaGiuridica = datiGenerali.getIdFormaGiuridica();
      Long idDimensioneImpresa = datiGenerali.getIdDimensioneImpresa();

      LOG.info(prf + " idBandoLinea: " + idBandoLinea + " idFormaGiuridica: " + idFormaGiuridica + " idDimensioneImpresa: " + idDimensioneImpresa);

      EsitoErogazioneDTO esito = erogazioneDAO.findErogazione(idU, idIride, idProgetto, idBandoLinea, idFormaGiuridica, idDimensioneImpresa);
      if (!esito.getIsRegolaAttiva()) {
        esito.setMessages(new String[]{MessageConstants.ATTIVITA_EROGAZIONE_NON_UTILIZZABILE});
      }
      LOG.debug(prf + " END");
      return Response.ok().entity(esito).build();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public Response getCausaliErogazioni(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getCausaliErogazioni]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();

      DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
      Long idBandoLinea = datiGenerali.getIdBandoLinea();
      Long idFormaGiuridica = datiGenerali.getIdFormaGiuridica();
      Long idDimensioneImpresa = datiGenerali.getIdDimensioneImpresa();
      LOG.info(prf + " idBandoLinea: " + idBandoLinea + " idFormaGiuridica: " + idFormaGiuridica + " idDimensioneImpresa: " + idDimensioneImpresa);
      CodiceDescrizioneCausaleDTO[] causali = erogazioneDAO.findCausaliErogazione(idUtente, idIride, idProgetto, idBandoLinea, idFormaGiuridica, idDimensioneImpresa);

      ArrayList<CodiceDescrizioneCausale> list = new ArrayList<CodiceDescrizioneCausale>();
      for (int i = 0; i < causali.length; i++) {
        CodiceDescrizioneCausale cd = new CodiceDescrizioneCausale();
        cd.setCodice(causali[i].getCodice());
        cd.setDescrizione(causali[i].getDescrizione());
        cd.setPercErogazione(causali[i].getPercErogazione());
        cd.setPercLimite(causali[i].getPercLimite());
        ;
        list.add(cd);
      }
      //METTO IN CODA QUELLI FISSI
      BigDecimal zero = new BigDecimal(0);
      CodiceDescrizioneCausale cdPrimoAcconto = new CodiceDescrizioneCausale();
//			cdPrimoAcconto.setCodice(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO+"@"+"0"+"@"+"0");
      cdPrimoAcconto.setCodice(Constants.COD_CAUSALE_EROGAZIONE_PRIMO_ACCONTO);
      cdPrimoAcconto.setDescrizione(Constants.DESC_CAUSALE_EROGAZIONE_PRIMO_ACCONTO);
      cdPrimoAcconto.setPercErogazione(zero.doubleValue());
      cdPrimoAcconto.setPercLimite(zero.doubleValue());
      list.add(cdPrimoAcconto);
      CodiceDescrizioneCausale cdUlterioreAcconto = new CodiceDescrizioneCausale();
//			cdUlterioreAcconto.setCodice(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO+"@"+"0"+"@"+"0");
      cdUlterioreAcconto.setCodice(Constants.COD_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO);
      cdUlterioreAcconto.setDescrizione(Constants.DESC_CAUSALE_EROGAZIONE_ULTERIORE_ACCONTO);
      cdUlterioreAcconto.setPercErogazione(zero.doubleValue());
      cdUlterioreAcconto.setPercLimite(zero.doubleValue());
      list.add(cdUlterioreAcconto);
      CodiceDescrizioneCausale cdSaldo = new CodiceDescrizioneCausale();
//			cdSaldo.setCodice(Constants.COD_CAUSALE_EROGAZIONE_SALDO+"@"+"0"+"@"+"0");
      cdSaldo.setCodice(Constants.COD_CAUSALE_EROGAZIONE_SALDO);
      cdSaldo.setDescrizione(Constants.DESC_CAUSALE_EROGAZIONE_SALDO);
      cdSaldo.setPercErogazione(zero.doubleValue());
      cdSaldo.setPercLimite(zero.doubleValue());
      list.add(cdSaldo);

      LOG.debug(prf + " END");
      return Response.ok().entity(list).build();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public Response getAllModalitaAgevolazioneContoEconomico(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getAllModalitaAgevolazioneContoEconomico]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();
      CodiceDescrizioneDTO[] listSrv = erogazioneDAO.findModalitaAgevolazionePerContoEconomico(idUtente, idIride, idProgetto);
      ArrayList<CodiceDescrizione> listModalita = new ArrayList<CodiceDescrizione>();
      if (listSrv != null) {
        for (CodiceDescrizioneDTO cdSrv : listSrv) {
          CodiceDescrizione cd = new CodiceDescrizione();
          cd.setCodice(cdSrv.getCodice());
          cd.setDescrizione(cdSrv.getDescrizione());
          listModalita.add(cd);
        }
      }
//			if (listModalita.isEmpty()) {
//				//ErrorConstants.ERRORE_EROGAZIONE_MODALITA_DI_AGEVOLAZIONI_NON_PRESENTI)
//				return 
//			}

      LOG.debug(prf + " END");
      return Response.ok().entity(listModalita).build();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public Response getAllModalitaErogazione(HttpServletRequest req) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getAllModalitaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();
      Decodifica[] list = datiDiDominioBusinessImpl.findDecodifiche(idUtente, idIride, Constants.MODALITA_EROGAZIONE);
      ArrayList<CodiceDescrizione> listModalitaErog = getListCodiciDescrizione(list);

      LOG.debug(prf + " END");
      return Response.ok().entity(listModalitaErog).build();
    } catch (Exception e) {
      throw e;
    }
  }


  private ArrayList<CodiceDescrizione> getListCodiciDescrizione(Decodifica[] list) {
    ArrayList<CodiceDescrizione> listCD = new ArrayList<CodiceDescrizione>();
    String prf = "[ErogazioneServiceImpl::getListCodiciDescrizione]";
    LOG.debug(prf + " START");
    for (int i = 0; i < list.length; i++) {
      Decodifica d = list[i];
      CodiceDescrizione cd = new CodiceDescrizione();
      cd.setCodice(d.getDescrizioneBreve());
      cd.setDescrizione(d.getDescrizione());
      listCD.add(cd);
    }
    LOG.debug(prf + " END");
    return listCD;
  }


  @Override
  public Response getAllCodiciDirezione(HttpServletRequest req) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getAllCodiciDirezione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();
      Decodifica[] list = datiDiDominioBusinessImpl.findEntiDiCompetenzaPerTipo(idUtente, idIride, Constants.RUOLO_ADG_IST_MASTER);
      ArrayList<CodiceDescrizione> listCodiciDirezione = getListCodiciDescrizione(list);

      LOG.debug(prf + " END");
      return Response.ok().entity(listCodiciDirezione).build();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public Response inserisciErogazione(HttpServletRequest req, SalvaErogazioneRequest salvaRequest) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::inserisciErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();
      Long idProgetto = salvaRequest.getIdProgetto();
      Erogazione erogazione = salvaRequest.getDettaglioErogazione();
      DatiCalcolati datiCalcolati = salvaRequest.getDatiCalcolati();

      if (idProgetto == null)
        return inviaErroreBadRequest("parametro mancato nel body: ?{SalvaErogazioneRequest.idProgetto}");
      if (erogazione == null) {
        return inviaErroreBadRequest("parametro mancato nel body: ?{SalvaErogazioneRequest.erogazione}");
      }
      if (datiCalcolati == null) {
        return inviaErroreBadRequest("parametro mancato nel body: ?{SalvaErogazioneRequest.datiCalcolati}");
      }
      // Importo erogazione effettiva > Importo residuo spettante ?
      if (NumberUtil.compare(erogazione.getImportoErogazioneEffettiva(), datiCalcolati.getImportoResiduoSpettante()) > 0) {
        return inviaErroreBadRequest(ErrorMessages.IMPORTO_DA_EROGARE_SUPERIORE_A_RESIDUO_E049);
      }
      //validate oggetto Erogazione
      if (erogazione.getCodCausaleErogazione() == null)
        return inviaErroreBadRequest("parametro mancato nel body: ?{erogazione.codCausaleErogazione}");
      if (erogazione.getImportoErogazioneEffettiva() == null)

        return inviaErroreBadRequest("parametro mancato nel body: ?{erogazione.importoErogazioneEffettiva}");
      if (erogazione.getImportoErogazioneEffettiva() < 0d)
        return inviaErroreBadRequest("L'importo erogazione effettiva deve essere maggiore di 0.");

      if (erogazione.getPercentualeErogazioneEffettiva() == null)
        return inviaErroreBadRequest("parametro mancato nel body: ?{erogazione.percentualeErogazioneEffettiva}");
      if (erogazione.getPercentualeErogazioneEffettiva() < 0d)
        return inviaErroreBadRequest("La percentuale erogazione effettiva deve essere maggiore di 0.");
      if (erogazione.getCodModalitaAgevolazione() == null || erogazione.getCodModalitaAgevolazione() == "")
        return inviaErroreBadRequest("parametro mancato nel body: ?{erogazione.codModalitaAgevolazione}");
      if (erogazione.getCodModalitaErogazione() == null || erogazione.getCodModalitaErogazione() == "")
        return inviaErroreBadRequest("parametro mancato nel body: ?{erogazione.codModalitaErogazione}");
      if (erogazione.getCodTipoDirezione() == null || erogazione.getCodTipoDirezione() == "")
        return inviaErroreBadRequest("parametro mancato nel body: ?{erogazione.codTipoDirezione}");
      if (erogazione.getDataContabile() == null || erogazione.getCodTipoDirezione() == "")
        return inviaErroreBadRequest("parametro mancato nel body: ?{erogazione.dataContabile}");
      if (DateUtil.getDate(erogazione.getDataContabile()).after(new Date(System.currentTimeMillis())))
        return inviaErroreBadRequest("La data contabile non può essere maggiore della data odierna");
      if (erogazione.getNumero() == null || erogazione.getNumero() == "") {
        return inviaErroreBadRequest("parametro mancato nel body: ?{erogazione.numero}");
      } else {
        if (erogazione.getNumero().trim().length() > 20) {
          return inviaErroreBadRequest("Il numero riferimento erogazione non deve superare i 20 caratteri.");
        }
      }

      ErogazioneDTO erogazioneDTO = new ErogazioneDTO();
      erogazioneDTO.setCodCausaleErogazione(erogazione.getCodCausaleErogazione());
      erogazioneDTO.setCodDirezione(erogazione.getCodTipoDirezione());
      erogazioneDTO.setCodModalitaAgevolazione(erogazione.getCodModalitaAgevolazione());
      erogazioneDTO.setCodModalitaErogazione(erogazione.getCodModalitaErogazione());
      erogazioneDTO.setDataContabile(DateUtil.getDate(erogazione.getDataContabile()));
      erogazioneDTO.setNumeroRiferimento(erogazione.getNumero());
      erogazioneDTO.setNoteErogazione(erogazione.getNote());
      erogazioneDTO.setImportoErogazioneEffettivo(erogazione.getImportoErogazioneEffettiva());
      erogazioneDTO.setPercentualeErogazioneEffettiva(erogazione.getPercentualeErogazioneEffettiva());

      DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
      Long idBandoLinea = datiGenerali.getIdBandoLinea();
      String codUtenteFlux = userInfoHelper.getCodUtenteFlux(userInfo);

      EsitoErogazioneDTO esito = erogazioneDAO.inserisciErogazione(idUtente, idIride, idProgetto, erogazioneDTO, codUtenteFlux);
      if (esito.getEsito() != null && esito.getEsito()) {
        if (isRegolaAssociaQuietanzeDiMandato(userInfo, idBandoLinea)) {
          esito.getMessages()[0] = MessageConstants.EROGAZIONE_DA_ASSOCIARE_SALVATA;
        }
      }

      LOG.debug(prf + " END");
      return Response.ok().entity(esito).build();
    } catch (Exception e) {
      throw e;
    }
  }


  private boolean isRegolaAssociaQuietanzeDiMandato(UserInfoSec userInfo, Long idBandoLinea) throws Exception {
    String prf = "[ErogazioneServiceImpl::isRegolaAssociaQuietanzeDiMandato]";
    LOG.debug(prf + " START");
    try {
      LOG.debug(prf + " END");
      return profilazioneSrv.isRegolaApplicabileForBandoLinea(userInfo.getIdUtente(), userInfo.getIdIride(), idBandoLinea, RegoleConstants.BR09_ASSOCIAZIONE_QUIETANZE_ATTO_LIQUIDAZIONE);
    } catch (Exception e) {
      LOG.error(prf + " " + e.getMessage());
      throw e;
    }

  }


  @Override
  public Response getDatiCalcolati(HttpServletRequest req, GetDatiCalcolatiRequest getDatiCalcolatiRequest) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getDatiCalcolati]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();
      Long idProgetto = getDatiCalcolatiRequest.getIdProgetto();
      ErogazioneDTO erogazione = getDatiCalcolatiRequest.getErogazioneDTO();
      if (idProgetto == null)
        return inviaErroreBadRequest("parametro mancato nel body: ?{GetDatiCalcolatiRequest.idProgetto}");
      if (erogazione == null) {
        return inviaErroreBadRequest("parametro mancato nel body: ?{GetDatiCalcolatiRequest.erogazione}");
      }
      DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
      Double importoTotaleAgevolato = 0d;
      for (ImportoAgevolatoDTO d : datiGenerali.getImportiAgevolati()) {
        if (d.getImporto() != null) {
          importoTotaleAgevolato += d.getImporto();
        }
      }
      DatiCalcolati dati = findDatiCalcolati(idProgetto, erogazione, importoTotaleAgevolato);
      LOG.debug(prf + " END");
      return Response.ok().entity(dati).build();
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Response controllaDatiOnSelectCausaleErogazione(HttpServletRequest req, ControllaDatiRequest controllaDatiRequest) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::inserisciErogazione]";
    LOG.debug(prf + " BEGIN");
    Erogazione erogazione = new Erogazione();
    EsitoControllaDati esito = new EsitoControllaDati();

    Double importoCalcolato = controllaDatiRequest.getImportoCalcolato();
    Double importoResiduoSpettante = controllaDatiRequest.getImportoResiduoSpettante();
    Double importoErogazioneEffettiva = controllaDatiRequest.getImportoErogazioneEffettiva();

    Double percErogazione = controllaDatiRequest.getPercErogazione();
    Double percLimite = controllaDatiRequest.getPercLimite();

    if (percErogazione > 0) {
      // CASO A
      erogazione.setPercentualeErogazioneIterFinanziario(percErogazione);
      Double importoErogazioneCalcolato = NumberUtil.toRoundDouble(importoCalcolato * percErogazione / 100);
      erogazione.setImportoErogazioneDaIterFinanziario(importoErogazioneCalcolato);
      erogazione.setPercentualeErogazioneEffettiva(percErogazione);

      if (NumberUtil.compare(importoErogazioneEffettiva, importoResiduoSpettante) > 0) {
        // MESSAGE
        esito.setEsito(false);
        esito.setMessage(ErrorMessages.IMPORTO_DA_EROGARE_SUPERIORE_A_RESIDUO_E048);

      } else {
        esito.setEsito(true);
      }

    } else if (percErogazione == 0 && percLimite > 0) {
      // CASO B
      erogazione.setPercentualeErogazioneIterFinanziario(percLimite);
      Double importoErogazioneDaIterFinanziario = NumberUtil.toRoundDouble((importoCalcolato * percLimite / 100));
      erogazione.setImportoErogazioneDaIterFinanziario(importoErogazioneDaIterFinanziario);
      if (importoErogazioneDaIterFinanziario > importoResiduoSpettante) {
        erogazione.setPercentualeErogazioneEffettiva(NumberUtil.toRoundDouble(importoResiduoSpettante / importoCalcolato * 100));
        erogazione.setImportoErogazioneEffettiva(importoResiduoSpettante);
      } else {
        erogazione.setPercentualeErogazioneEffettiva(percLimite);
        erogazione.setImportoErogazioneEffettiva(importoErogazioneDaIterFinanziario);
      }
      esito.setEsito(true);

    } else if (percErogazione == 0 && percLimite == 0) {
      // CASO C
      erogazione.setPercentualeErogazioneIterFinanziario(0d);
      erogazione.setImportoErogazioneDaIterFinanziario(0d);
      erogazione.setPercentualeErogazioneEffettiva(0d);
      erogazione.setImportoErogazioneEffettiva(0d);
      esito.setEsito(true);
    }
    esito.setErogazione(erogazione);
    return Response.ok().entity(esito).build();
  }

  private DatiCalcolati findDatiCalcolati(Long idProgetto, ErogazioneDTO erogazione, Double importoTotaleAgevolato) {
    String prf = "[ErogazioneServiceImpl::findDatiCalcolati]";
    LOG.debug(prf + " START");
    DatiCalcolati datiCalcolati = new DatiCalcolati();
    datiCalcolati.setImportoResiduoSpettante(erogazione.getImportoTotaleResiduo());
    datiCalcolati.setImportoTotaleErogato(erogazione.getImportoTotaleErogato());
    datiCalcolati.setImportoTotaleRichiesto(erogazione.getImportoTotaleRichiesto());
    datiCalcolati.setImportoTotaleAgevolato(NumberUtil.toRoundDouble(importoTotaleAgevolato));
    LOG.debug(prf + " END");
    return datiCalcolati;
  }


  @Override
  public Response getDettaglioErogazione(HttpServletRequest req, Long idProgetto, Long idErogazione) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::eliminaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();
      DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
      Long idBandoLinea = datiGenerali.getIdBandoLinea();
      Long idFormaGiuridica = datiGenerali.getIdFormaGiuridica();
      Long idDimensioneImpresa = datiGenerali.getIdDimensioneImpresa();
      LOG.info(prf + " idBandoLinea: " + idBandoLinea + " idFormaGiuridica: " + idFormaGiuridica + " idDimensioneImpresa: " + idDimensioneImpresa);

      EsitoErogazioneDTO esito = erogazioneDAO.findDettaglioErogazione(idUtente, idIride, idErogazione, idFormaGiuridica, idDimensioneImpresa, idBandoLinea, idProgetto);

      ErogazioneDTO erogazioneSrv = esito.getErogazione();
      Erogazione erogazione = new Erogazione();
      erogazione.setIdErogazione(erogazioneSrv.getIdErogazione());

      erogazione.setCodCausaleErogazione(erogazioneSrv.getCodCausaleErogazione());
      erogazione.setCodModalitaAgevolazione(erogazioneSrv.getCodModalitaAgevolazione());
      erogazione.setCodModalitaErogazione(erogazioneSrv.getCodModalitaErogazione());

      erogazione.setCodTipoDirezione(erogazioneSrv.getCodDirezione());
      erogazione.setDataContabile(DateUtil.getDate(erogazioneSrv.getDataContabile()));
      erogazione.setNumero(erogazioneSrv.getNumeroRiferimento());
      erogazione.setNote(erogazioneSrv.getNoteErogazione());

      erogazione.setDescCausaleErogazione(erogazioneSrv.getDescrizioneCausaleErogazione());
      erogazione.setDescModalitaAgevolazione(erogazioneSrv.getDescModalitaAgevolazione());
      erogazione.setDescModalitaErogazione(erogazioneSrv.getDescModalitaErogazione());
      erogazione.setDescTipoDirezione(erogazioneSrv.getDescTipoDirezione());


      erogazione.setPercentualeErogazioneEffettiva(erogazioneSrv.getPercentualeErogazioneEffettiva());
      erogazione.setPercentualeErogazioneIterFinanziario(erogazioneSrv.getPercentualeErogazioneFinanziaria());
      erogazione.setImportoErogazioneDaIterFinanziario(erogazioneSrv.getImportoErogazioneFinanziario());
      erogazione.setImportoErogazioneEffettiva(erogazioneSrv.getImportoErogazioneEffettivo());

      erogazione.setPercErogazione(erogazioneSrv.getPercErogazione());
      erogazione.setPercLimite(erogazioneSrv.getPercLimite());

      //erogazione.setImportoCalcolato(erogazioneSrv.);
      LOG.debug(prf + " END");
      erogazione.setImportoCalcolato(null);
      return Response.ok().entity(erogazione).build();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public Response eliminaErogazione(HttpServletRequest req, Long idErogazione) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::eliminaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();


      EsitoOperazioni esito = erogazioneDAO.eliminaErogazione(idUtente, idIride, idErogazione);
      LOG.debug(prf + " END");
      return Response.ok().entity(esito).build();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public Response modificaErogazione(HttpServletRequest req, ModificaErogazioneRequest moRequest) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::modificaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();

      Long idProgetto = moRequest.getIdProgetto();
      Erogazione erogazione = moRequest.getErogazione();
      DatiCalcolati datiCalcolati = moRequest.getDatiCalcolati();

      ErogazioneDTO erogazioneDTO = new ErogazioneDTO();
      erogazioneDTO.setIdErogazione(erogazione.getIdErogazione());
      erogazioneDTO.setCodCausaleErogazione(erogazione.getCodCausaleErogazione());
      erogazioneDTO.setCodDirezione(erogazione.getCodTipoDirezione());
      erogazioneDTO.setCodModalitaAgevolazione(erogazione.getCodModalitaAgevolazione());
      erogazioneDTO.setCodModalitaErogazione(erogazione.getCodModalitaErogazione());
      erogazioneDTO.setDataContabile(DateUtil.getDate(erogazione.getDataContabile()));
      erogazioneDTO.setNumeroRiferimento(erogazione.getNumero());
      erogazioneDTO.setNoteErogazione(erogazione.getNote());
      erogazioneDTO.setImportoErogazioneEffettivo(erogazione.getImportoErogazioneEffettiva());
      erogazioneDTO.setPercentualeErogazioneEffettiva(erogazione.getPercentualeErogazioneEffettiva());
      //SERVE PER IL CONTROLLO SULL'IMPORTO AGEVOLATO
      SpesaProgettoDTO spesa = new SpesaProgettoDTO();
      spesa.setImportoAmmessoContributo(datiCalcolati.getImportoTotaleAgevolato());
      erogazioneDTO.setSpesaProgetto(spesa);

      EsitoOperazioni esito = erogazioneDAO.modificaErogazione(idUtente, idIride, idProgetto, erogazioneDTO);
      LOG.debug(prf + " END");
      return Response.ok().entity(esito).build();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public Response getDatiRiepilogoRichiestaErogazione(HttpServletRequest req, Long idProgetto, String codCausale) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getDatiRiepilogoRichiestaErogazione]";
    LOG.info(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();

      DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, userInfo.getBeneficiarioSelezionato().getIdBeneficiario());
      Long idBandoLinea = datiGenerali.getIdBandoLinea();
      Long idDimensioneImpresa = datiGenerali.getIdDimensioneImpresa();
      Long idFormaGiuridica = datiGenerali.getIdFormaGiuridica();
      Long idSoggetto = userInfo.getBeneficiarioSelezionato().getIdBeneficiario();
//			IstanzaAttivitaCorrenteDTO istanzaAttivita = esecuzioneAttivitaBusinessImpl.getCurrentActivity( req.getParameterMap());
//			LOG.info(prf + " codCausale = "+ istanzaAttivita.getCodCausaleErogazione());				
      EsitoRichiestaErogazioneDTO esito = erogazioneDAO.findDatiRiepilogoRichiestaErogazione(idUtente, idIride, idProgetto, codCausale, idDimensioneImpresa, idFormaGiuridica, idBandoLinea, idSoggetto);

      LOG.info(prf + " END");
      return Response.ok().entity(esito).build();
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Response getRappresentantiLegali(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getRappresentantiLegali]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();

      RappresentanteLegaleDTO[] rappresentantiDTO = erogazioneDAO.findRappresentantiLegali(idUtente, idIride, idProgetto, null);
//			ArrayList<CodiceDescrizione> rappresentanti = new ArrayList<CodiceDescrizione>();
//			if (rappresentantiDTO != null) {
//				for (RappresentanteLegaleDTO rapp : rappresentantiDTO){
//					CodiceDescrizione rappresentante = new CodiceDescrizione();
//					rappresentante.setCodice(String.valueOf(rapp.getIdSoggetto()));
//					rappresentante.setDescrizione(rapp.getCognome()+" "+rapp.getNome()+" - "+rapp.getCodiceFiscaleSoggetto());
//					rappresentanti.add(rappresentante);
//				}
//			}
      LOG.debug(prf + " END");
      return Response.ok().entity(rappresentantiDTO).build();
    } catch (Exception e) {
      throw e;
    }

  }

  @Override
  public Response creaRichiestaErogazione(HttpServletRequest req, CreaRichiestaErogazioneRequest creaRequest) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::creaRichiestaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();
      Long idProgetto = creaRequest.getIdProgetto();
      RichiestaErogazioneDTO richiestaErogazione = creaRequest.getRichiesta();
      RappresentanteLegaleDTO rappresentanteLegale = creaRequest.getRappresentante();
      List<AffidServtecArDTO> affidamentiServiziLavoriDTOs = creaRequest.getAffidamentiServiziLavori();
      List<PbandiTAffidServtecArVO> affidamentiServiziLavoriVOs = null;

      if (affidamentiServiziLavoriDTOs != null) {
        affidamentiServiziLavoriVOs = new ArrayList<PbandiTAffidServtecArVO>();
        for (AffidServtecArDTO dto : affidamentiServiziLavoriDTOs) {
          PbandiTAffidServtecArVO vo = new PbandiTAffidServtecArVO();
          vo.setFornitore(dto.getFornitore());
          vo.setServizioAffidato(dto.getServizioAffidato());
          vo.setDocumento(dto.getDocumento());
          vo.setNomeFile(dto.getNomeFile());
          vo.setFlagAffidServtec(dto.getFlagAffidServtec());
          affidamentiServiziLavoriVOs.add(vo);
        }
      }
      Long idDelegato = creaRequest.getIdDelegato();
      Long idSoggetto = userInfo.getIdSoggetto();
      EsitoReportRichiestaErogazioneDTO esito = erogazioneDAO.creaReportRichiestaErogazione(idUtente, idIride, idProgetto, richiestaErogazione, rappresentanteLegale, idDelegato, idSoggetto, affidamentiServiziLavoriVOs);
      LOG.debug(prf + " END");
      return Response.ok().entity(esito).build();

    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Response getDelegati(HttpServletRequest req, Long idProgetto) throws UtenteException, Exception {
    String prf = "[ErogazioneServiceImpl::getDelegati]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();
      DelegatoDTO[] delegatiDTO = datiGeneraliBusinessImpl.findDelegati(idUtente, idIride, idProgetto);
      ArrayList<CodiceDescrizione> delegati = new ArrayList<CodiceDescrizione>();
      if (delegatiDTO != null) {
        for (DelegatoDTO del : delegatiDTO) {
          CodiceDescrizione delegato = new CodiceDescrizione();
          delegato.setCodice(String.valueOf(del.getIdSoggetto()));
          delegato.setDescrizione(del.getCognome() + " " + del.getNome() + " - " + del.getCodiceFiscaleSoggetto());
          delegati.add(delegato);
        }
      }
      LOG.debug(prf + " END");
      return Response.ok().entity(delegati).build();

    } catch (Exception e) {
      throw e;
    }

  }


  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////// METODI DI RESPONSE HTTP /////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private Response inviaErroreBadRequest(String msg) {
    EsitoOperazioni esito = new EsitoOperazioni();
    esito.setEsito(false);
    esito.setMsg(msg);
    return Response.status(Response.Status.BAD_REQUEST).entity(esito).type(MediaType.APPLICATION_JSON).build();
  }


  @Override
  public Response associaAllegatiARichiestaErogazione(HttpServletRequest req, AssociaFilesRequest associaFilesRequest) throws Exception {
    String prf = "[ErogazioneServiceImpl::associaAllegatiARichiestaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();

      EsitoAssociaFilesDTO esito = erogazioneDAO.associaAllegatiARichiestaErogazione(associaFilesRequest, idUtente, idIride);
      LOG.debug(prf + " END");
      return Response.ok().entity(esito).build();
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Response getFilesAssociatedRichiestaErogazioneByIdProgetto(HttpServletRequest req, Long idProgetto) throws Exception {
    String prf = "[ErogazioneServiceImpl::getFilesAssociatedRichiestaErogazioneByIdProgetto]";
    LOG.debug(prf + " BEGIN");
    try {
      FileDTO[] files = erogazioneDAO.getFilesAssociatedRichiestaErogazioneByIdProgetto(idProgetto);
      LOG.debug(prf + " END");
      return Response.ok().entity(files).build();
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Response disassociaAllegatiARichiestaErogazione(HttpServletRequest req, Long idDocumentoIndex, Long idErogazione, Long idProgetto) throws Exception {
    String prf = "[ErogazioneServiceImpl::disassociaAllegatiARichiestaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {
      HttpSession session = req.getSession();
      UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
      Long idUtente = userInfo.getIdUtente();
      String idIride = userInfo.getIdIride();

      EsitoOperazioni esito = erogazioneDAO.disassociaAllegatiARichiestaErogazione(idDocumentoIndex, idErogazione, idProgetto, idUtente, idIride);
      LOG.debug(prf + " END");
      return Response.ok().entity(esito).build();
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Response inizializzaErogazione(HttpServletRequest req, Long idProgetto) throws Exception {
    String prf = "[ErogazioneServiceImpl::inizializzaErogazione]";
    LOG.debug(prf + " BEGIN");
    try {

      InizializzaErogazioneDTO dto = erogazioneDAO.inizializzaErogazione(idProgetto);

      LOG.debug(prf + " END");
      return Response.ok().entity(dto).build();
    } catch (Exception e) {
      throw e;
    }
  }


  @Override
  public Response getModalitaAgevolazioneDaVisualizzare(HttpServletRequest req, Long idModalitaAgev) throws UtenteException, Exception {

    EsitoDTO esito = new EsitoDTO();

    esito.setMessaggio(erogazioneDAO.getModalitaAgevolazioneDaVisualizzare(idModalitaAgev).toString());
    esito.setEsito(true);
    return Response.ok(esito).build();
  }

  public Response getVerificaErogazione(HttpServletRequest req, Long idErogazione, Long idProgetto) throws Exception {
    String prf = "[ErogazioneServiceImpl::getVerificaErogazione]";
    LOG.info(prf + " BEGIN");
    EsitoDatiVerificaErogazioneDTO esito = new EsitoDatiVerificaErogazioneDTO();
    esito.setEsito(false);
    if (idErogazione == null || idProgetto == null) {
      esito.setMessages(new String[]{"Parametri non validi!"});
      return Response.status(Response.Status.BAD_REQUEST).entity(esito).build();
    }

    HttpSession session = req.getSession();
    UserInfoSec userInfo = (UserInfoSec) session.getAttribute(Constants.USERINFO_SESSIONATTR);
    Long idUtente = userInfo.getIdUtente();
    String idIride = userInfo.getIdIride();

    DatiVerificaErogazioneDTO datiVerificaErogazioneDTO = new DatiVerificaErogazioneDTO();

    LOG.info(prf + " idErogazione: " + idErogazione + " idProgetto: " + idProgetto);
    try {

      DatiGeneraliDTO datiGenerali = datiGeneraliBusinessImpl.caricaDatiGenerali(idUtente, idIride, idProgetto, null);
      Long idBandoLinea = datiGenerali.getIdBandoLinea();
      Long idFormaGiuridica = datiGenerali.getIdFormaGiuridica();
      Long idDimensioneImpresa = datiGenerali.getIdDimensioneImpresa();
      LOG.info(prf + " idBandoLinea: " + idBandoLinea + " idFormaGiuridica: " + idFormaGiuridica + " idDimensioneImpresa: " + idDimensioneImpresa);

      // Dati Erogazione
      datiVerificaErogazioneDTO.setRichiestaErogazione(erogazioneDAO.findRichiestaErogazione(idErogazione));

      // Dati Causale Erogazione
      datiVerificaErogazioneDTO.setCausaleErogazione(erogazioneDAO.findCausaleErogazione(datiVerificaErogazioneDTO.getRichiestaErogazione().getIdCausaleErogazione().longValue()));

      // Fideiussioni
      datiVerificaErogazioneDTO.setFideiussioni(erogazioneDAO.findFideiussioniRichiestaErogazione(idProgetto));

      // Allegati
      FileDTO[] fileAllegati = erogazioneDAO.getFilesAssociatedRichiestaErogazioneByIdErogazione(idErogazione);
      datiVerificaErogazioneDTO.setAllegati(Arrays.asList(fileAllegati));

      // Allegati Richiesta Erogazione
      datiVerificaErogazioneDTO.setAllegatiRichiestaErogazione(erogazioneDAO.findAllegatiRichiestaErogazione(idProgetto, idErogazione));

      // Dati affidamenti
      datiVerificaErogazioneDTO.setAffidamenti(erogazioneDAO.findAffidamentiRichiestaErogazione(idErogazione));

      // Rappresentante Legale
      Long idSoggettoRappresentante;
      if (!datiVerificaErogazioneDTO.getAllegatiRichiestaErogazione().isEmpty()) {
        BigDecimal idDelegato = datiVerificaErogazioneDTO.getAllegatiRichiestaErogazione().get(0).getIdSoggDelegato();
        if (idDelegato != null && idDelegato.longValue() > 0) {
          idSoggettoRappresentante = idDelegato.longValue();
        } else {
          idSoggettoRappresentante = datiVerificaErogazioneDTO.getAllegatiRichiestaErogazione().get(0).getIdSoggRapprLegale().longValue();
        }
      } else {
        idSoggettoRappresentante = soggettoManager.findSoggettoRappresentanteByProgetto(idProgetto);
      }
      LOG.info(prf + " idSoggettoRappresentante: " + idSoggettoRappresentante);
      if (idSoggettoRappresentante != null) {
        datiVerificaErogazioneDTO.setRappresentanteLegale(soggettoManager.findRappresentantiLegali(idProgetto, idSoggettoRappresentante));
      } else {
        LOG.error(prf + " idSoggettoRappresentante non trovato!");
        esito.setMessages(new String[]{"idSoggettoRappresentante non trovato!"});
      }

      // Estremi Bancari
      datiVerificaErogazioneDTO.setEstremiBancari(erogazioneDAO.findEstremiBancariVerificaErogazione(idProgetto));
      esito.setEsito(true);
      esito.setErogazione(datiVerificaErogazioneDTO);

    } catch (RuntimeException e) {
      esito.setMessages(new String[]{"Errore sulle query durante la raccolta dei dati per verifica dell'erogazione!"});
      LOG.error(e.toString());
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(esito).build();
    } catch (Exception e) {
      LOG.error(prf + " " + e.getMessage());
      throw e;
    } finally {
      LOG.info(prf + " END");
    }

    return Response.ok().entity(esito).build();
  }

  @Override
  public Response verificaErogazione(HttpServletRequest req, Map<String, Object> requestData) throws Exception {
    String prf = "[ErogazioneServiceImpl::verificaErogazione]";
    LOG.debug(prf + " BEGIN");
    if (requestData == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity(new EsitoOperazioni(false, "Parametri non validi!")).build();
    }
    long idErogazione;
    Boolean verificato;
    try {
      if (requestData.get("idErogazione") == null) {
        throw new Exception("Parametri non validi!");
      }
      idErogazione = Long.parseLong(String.valueOf(requestData.get("idErogazione")));
      verificato = (Boolean) requestData.get("verificato");
      if (verificato == null) {
        throw new Exception("Parametri non validi!");
      }
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(new EsitoOperazioni(false, "Parametri non validi!")).build();
    }

    Long idUtente = ((UserInfoSec) req.getSession().getAttribute(Constants.USERINFO_SESSIONATTR)).getIdUtente();
    EsitoOperazioni esitoOperazioni = erogazioneDAO.verificaErogazione(idErogazione, idUtente, verificato);

    LOG.debug(prf + " END");

    if (!esitoOperazioni.getEsito())
      return Response.status(Response.Status.NOT_MODIFIED).entity(esitoOperazioni).build();

    return Response.ok().entity(esitoOperazioni).build();
  }

}

