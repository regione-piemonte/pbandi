/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface Beneficiario {
  codiceFiscale?: string;
  cognome?: string;
  descrizione?: string;
  idBeneficiario?: number;
  idCombo?: number;
  idDimensioneImpresa?: number;
  idFormaGiuridica?: number;
  idSoggetto?: number;
  isCapofila?: boolean;
  nome?: string;
  pivaSedeIntervento?: string;
  pivaSedeLegale?: string;
  sede?: string;
  sedeLegale?: string;
}
