/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface RequestModificaIrreg {
  idU: number,
  idIride: string,
  modificaDatiAggiuntivi: string,
  idProgetto: number,
  idMotivoRevoca: number,
  importoIrregolarita: number,
  importoAgevolazioneIrreg: number,
  quotaImpIrregCertificato: number,
  idPeriodo: number,
  idCategAnagrafica: number,
  tipoControlli: string,
  idIrregolarita: number,
  note: string,
  dataCampione: string,
  dataInizioControlli: string,
  dataFineControlli: string,
  flagProvvedimento: string,
  notePraticaUsata: string,
  soggettoResponsabile: string,
  idIrregolaritaProvv: number,
  fileDatiAggiuntivi: File,
  fileOlaf: File,
  idDocumentoIndexOlaf?: string,
  idDocumentoIndexDatiAggiuntivi?: string
}
