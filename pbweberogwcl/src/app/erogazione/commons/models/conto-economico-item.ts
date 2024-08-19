/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface ContoEconomicoItem {
  haFigli?: boolean;
  id?: string;
  idPadre?: string;
  idTipologia?: string;
  importoSpesaAmmessa?: number;
  importoSpesaQuietanziata?: number;
  importoSpesaRendicontata?: number;
  importoSpesaValidataTotale?: number;
  label?: string;
  level?: number;
  percentualeSpesaQuietanziataSuAmmessa?: number;
  percentualeSpesaValidataSuAmmessa?: number;
  type?: string;
}
