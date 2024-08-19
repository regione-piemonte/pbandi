/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.EstremiBancariDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.FideiussioneTipoTrattamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.*;

import java.util.List;

public class DatiVerificaErogazioneDTO implements java.io.Serializable {

  private static final long serialVersionUID = 3L;
  private PbandiTRichiestaErogazioneVO richiestaErogazione;
  private PbandiDCausaleErogazioneVO causaleErogazione;
  private List<FideiussioneTipoTrattamentoDTO> fideiussioni;
  private List<FileDTO> allegati;
  private List<PbandiTDocumentoIndexVO> allegatiRichiestaErogazione;
  private List<PbandiTAffidServtecArVO> affidamenti;
  private List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentanteLegale;
  private EstremiBancariDTO estremiBancari;

  public PbandiTRichiestaErogazioneVO getRichiestaErogazione() {
    return richiestaErogazione;
  }

  public void setRichiestaErogazione(PbandiTRichiestaErogazioneVO richiestaErogazione) {
    this.richiestaErogazione = richiestaErogazione;
  }

  public PbandiDCausaleErogazioneVO getCausaleErogazione() {
    return causaleErogazione;
  }

  public void setCausaleErogazione(PbandiDCausaleErogazioneVO causaleErogazione) {
    this.causaleErogazione = causaleErogazione;
  }

  public List<FileDTO> getAllegati() {
    return allegati;
  }

  public void setAllegati(List<FileDTO> allegati) {
    this.allegati = allegati;
  }

  public List<PbandiTAffidServtecArVO> getAffidamenti() {
    return affidamenti;
  }

  public void setAffidamenti(List<PbandiTAffidServtecArVO> affidamenti) {
    this.affidamenti = affidamenti;
  }

  public List<FideiussioneTipoTrattamentoDTO> getFideiussioni() {
    return fideiussioni;
  }

  public void setFideiussioni(List<FideiussioneTipoTrattamentoDTO> fideiussioni) {
    this.fideiussioni = fideiussioni;
  }

  public List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> getRappresentanteLegale() {
    return rappresentanteLegale;
  }

  public void setRappresentanteLegale(List<it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO> rappresentanteLegale) {
    this.rappresentanteLegale = rappresentanteLegale;
  }

  public List<PbandiTDocumentoIndexVO> getAllegatiRichiestaErogazione() {
    return allegatiRichiestaErogazione;
  }

  public void setAllegatiRichiestaErogazione(List<PbandiTDocumentoIndexVO> allegatiRichiestaErogazione) {
    this.allegatiRichiestaErogazione = allegatiRichiestaErogazione;
  }


  public EstremiBancariDTO getEstremiBancari() {
    return estremiBancari;
  }

  public void setEstremiBancari(EstremiBancariDTO estremiBancari) {
    this.estremiBancari = estremiBancari;
  }

  @Override
  public String toString() {
    return "DatiVerificaErogazioneDTO{" +
        "richiestaErogazione=" + richiestaErogazione +
        ", causaleErogazione=" + causaleErogazione +
        ", fideiussioni=" + fideiussioni +
        ", allegati=" + allegati +
        ", allegatiRichiestaErogazione=" + allegatiRichiestaErogazione +
        ", affidamenti=" + affidamenti +
        ", rappresentanteLegale=" + rappresentanteLegale +
        ", estremiBancari=" + estremiBancari +
        '}';
  }
}
