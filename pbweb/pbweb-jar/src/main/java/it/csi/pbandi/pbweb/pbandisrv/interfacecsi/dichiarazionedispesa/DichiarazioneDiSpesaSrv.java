/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.interfacecsi.dichiarazionedispesa;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.DeclaratoriaDTO;
import it.csi.pbandi.pbweb.pbandisrv.exception.gestionedatigenerali.GestioneDatiGeneraliException;

import java.util.ArrayList;

public interface DichiarazioneDiSpesaSrv {
  String CODICE_RUOLO_ENTE_DESTINATARIO = "DESTINATARIO";
  String CODICE_RUOLO_ENTE_PROGRAMMATORE = "PROGRAMMATORE";
  String CODICE_RUOLO_ENTE_ATTUATORE = "ATTUATORE";
  String CODICE_RUOLO_ENTE_DEST_FINANZIAMENTO = "DEST. FINANZIAMENTO";
  String CODICE_RUOLO_ENTE_REALIZZATORE = "REALIZZATORE";
  String CODICE_RUOLO_ENTE_RESPONSABILE = "RESPONSABILE";

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaReportDTO findDatiDichiarazioneForReport(Long idUtente, String identitaDigitale, Long idProgetto, java.util.Date dal, java.util.Date al) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DocumentoContabileDTO[] findDocumentiContabili(Long idUtente, String identitaDigitale, Long idProgetto, java.util.Date dal, java.util.Date al) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.VoceDiCostoDTO[] findVociDiCosto(Long idUtente, String identitaDigitale, Long idProgetto, java.util.Date dal, java.util.Date al) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneInviaDichiarazione inviaDichiarazioneDiSpesa(Long idUtente, String identitaDigitale, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO dichiarazioneDiSpesa, String codUtente, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.IstanzaAttivitaDTO istanzaAttivita, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO rappresentanteLegale, Long idDelegato, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.RelazioneTecnicaDTO relazioneTecnica, it.csi.pbandi.pbweb.pbandisrv.dto.fineprogetto.ComunicazioneFineProgettoDTO cfpDto, DeclaratoriaDTO declaratoriaDTO) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneScaricaDichiarazioneSpesa scaricaDichiarazioneDiSpesa(Long idUtente, String identitaDigitale, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO dichiarazioneDiSpesa) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa(Long idUtente, String identitaDigitale, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO dichiarazioneDiSpesaDTO) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneAnteprimaDichiarazioneSpesa anteprimaDichiarazioneDiSpesa(Long idUtente, String identitaDigitale, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO dichiarazioneDiSpesa, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO rappresentanteLegale, Long idDelegato, it.csi.pbandi.pbweb.pbandisrv.dto.fineprogetto.ComunicazioneFineProgettoDTO cfpDto) throws it.csi.csi.wrapper.CSIException;

	it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneAnteprimaDichiarazioneSpesa anteprimaDichiarazioneDiSpesaCultura(Long idUtente, String identitaDigitale, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO dichiarazioneDiSpesa, it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO rappresentanteLegale, Long idDelegato, it.csi.pbandi.pbweb.pbandisrv.dto.fineprogetto.ComunicazioneFineProgettoDTO cfpDto, DeclaratoriaDTO allegatiCultura) throws it.csi.csi.wrapper.CSIException;

	it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.EsitoOperazioneAnteprimaDichiarazioneSpesa ricreaPdf(Long idUtente, String identitaDigitale, Long idDichiarazioneDiSpesa, Long idSoggetto) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente, String identitaDigitale, Long idProgetto, Long idSoggettoRappresentante) throws it.csi.csi.wrapper.CSIException;

  String findNoteChiusuraValidazione(Long idUtente, String identitaDigitale, Long idDichiarazione) throws it.csi.csi.wrapper.CSIException;

  boolean hasDichiarazioneFinale(Long idUtente, String identitaDigitale, Long idProgetto) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.InfoDichiarazioneSpesaDTO getInfoDichiarazioneDiSpesa(Long idUtente, String identitaDigitale, Long idDichiarazioneDiSpesa, Long idProgetto) throws it.csi.csi.wrapper.CSIException;

  boolean hasDichiarazioneFinaleOFinaleComunicazione(Long idUtente, String identitaDigitale, Long idProgetto) throws it.csi.csi.wrapper.CSIException;

  Long findIdDocIndexReport(Long idUtente, String identitaDigitale, Long idDichiarazioneDiSpesa) throws it.csi.csi.wrapper.CSIException;

  it.csi.pbandi.pbweb.pbandisrv.dto.dichiarazionedispesa.DichiarazioneDiSpesaDTO findDichiarazioneFinale(Long idUtente, String identitaDigitale, Long idProgetto) throws it.csi.csi.wrapper.CSIException;

  EsitoDTO salvaTipoAllegati(Long idUtente, String identitaIride, it.csi.pbandi.pbweb.pbandisrv.dto.gestionedatigenerali.TipoAllegatoDTO[] tipoAllegatiDTO) throws CSIException;
}