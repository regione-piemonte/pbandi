/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface TrasferimentiItem {
  cfBeneficiario?: string;
  codiceTrasferimento?: string;
  denominazioneBeneficiario?: string;
  descCausaleTrasferimento?: string;
  descPubblicoPrivato?: string;
  dtTrasferimento?: string;
  flagPubblicoPrivato?: string;
  idCausaleTrasferimento?: number;
  idSoggettoBeneficiario?: number;
  idTrasferimento?: number;
  importoTrasferimento?: number;
  isEliminabile?: boolean;
  isModificabile?: boolean;
  lnkDettaglio?: string;
  lnkElimina?: string;
  lnkModifica?: string;
}
