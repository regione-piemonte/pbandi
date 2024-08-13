/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.interfacecsi.gestionepagamenti;

import it.csi.csi.wrapper.*;
import it.csi.pbandi.pbweb.pbandisrv.dto.gestionepagamenti.*;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionepagamenti.GestionePagamentoException;

public interface GestionePagamentiSrv {
  String STATO_VALIDAZIONE_DA_RESPINGERE = "STATO_VALIDAZIONE_DA_RESPINGERE";
  String STATO_VALIDAZIONE_DICHIARATO = "STATO_VALIDAZIONE_DICHIARATO";
  String STATO_VALIDAZIONE_INSERITO = "STATO_VALIDAZIONE_INSERITO";
  String STATO_VALIDAZIONE_NON_VALIDATO = "STATO_VALIDAZIONE_NON_VALIDATO";
  String STATO_VALIDAZIONE_RESPINTO = "STATO_VALIDAZIONE_RESPINTO";
  String STATO_VALIDAZIONE_SOSPESO = "STATO_VALIDAZIONE_SOSPESO";
  String STATO_VALIDAZIONE_PARZIALMENTE_VALIDATO = "STATO_VALIDAZIONE_PARZIALMENTE_VALIDATO";
  String STATO_VALIDAZIONE_VALIDATO = "STATO_VALIDAZIONE_VALIDATO";
  String MODALITA_PAGAMENTO_VARIE = "AUT";
  String MODALITA_PAGAMENTO_COSTI_STANDARD = "COST";

  public EsitoOperazioneCancellazionePagamento eliminaPagamento(Long idUtente, String identitaDigitale, PagamentoDTO pagamento, Long idBando)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public PagamentoVoceDiSpesaDTO[] findPagamentiVociDiSpesa(Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa, Long idPagamento, Long idProgetto)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public PagamentoDTO[] findPagamentiAssociati(Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa, Long idBando, Long idProgetto)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public PagamentoDTO[] findPagamentiAssociatiPerValidazione(Long idUtente, String identitaDigitale, Long idDocumentoDiSpesa, Long idBando, Long idProgetto)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public EsitoOperazioneInserimentoPagamento inserisciPagamento(Long idUtente, String identitaDigitale, PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa, Long idProgetto)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public EsitoOperazioneModificaPagamento modificaPagamento(Long idUtente, String identitaDigitale, PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa, Long idProgetto)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

	public EsitoOperazioneModificaPagamento modificaPagamentoBR62(Long idUtente, String identitaDigitale, PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa, Long idProgetto)
			throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

	public PagamentoVoceDiSpesaDTO[] ripartisciPagamentoOnVociDiSpesa(Long idUtente, String identitaDigitale, Double importoTotalePagamento, PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, Long idBando)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public PagamentoVoceDiSpesaDTO[] ripartisciPagamentoOnVociDiSpesaPerValidazione(Long idUtente, String identitaDigitale, Double importoTotalePagamento, PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, Long idBando)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public EsitoOperazioneInserimentoPagamento forzaInserisciPagamento(Long idUtente, String identitaDigitale, PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa, Long idProgetto)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public EsitoOperazioneModificaPagamento forzaModificaPagamento(Long idUtente, String identitaDigitale, PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa, Long idProgetto)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public ModalitaPagamentoDTO[] findModalitaPagamento(Long idUtente, String identitaDigitale, Long idProgetto, Long idTipoDocumentoSpesa)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public EsitoOperazioneModificaPagamento modificaPagamentoPerValidazione(Long idUtente, String identitaDigitale, PagamentoVoceDiSpesaDTO[] arrayPagamentiVociDiSpesa, PagamentoDTO pagamento, Long idBando, Long idDocumentoDiSpesa, Long idProgetto)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;

  public CausalePagamentoDTO[] getCausaliPagamento(Long idUtente, String identitaDigitale)
      throws CSIException, SystemException, UnrecoverableException, GestionePagamentoException;
}