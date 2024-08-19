/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface RequestInserisciIrreg {
  idU: string, 
  fileOlaf: File
  fileDatiAggiuntivi: File,
  tipoControlli: string,
  idProgetto: string,
  idCategAnagrafica: string,
  idPeriodo: string,
  importoIrregolarita: string,
  importoAgevolazioneIrreg: string,
  quotaImpIrregCertificato: string,
  dataInizioControlli: string,
  dataFineControlli: string,
  modificaDatiAggiuntivi: string,
  datacampione: string,
  soggettoResponsabile: string,
  note: string,
  idMotivoRevoca: string,
  notePraticaUsata: string,
  flagProvvedimento: string,
  idIrregolaritaProvv?: string
}
