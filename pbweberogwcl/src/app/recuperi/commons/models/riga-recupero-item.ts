/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RigaRecuperoItem {
  dtRevoca?: string;
  estremiRevoca?: string;
  hasRevoche?: boolean;
  idAnnoContabile?: number;
  idModalitaAgevolazione?: number;
  idRecupero?: number;
  importoAgevolato?: number;
  importoErogato?: number;
  importoRecupero?: number;
  importoTotaleRecuperato?: number;
  importoTotaleRevoche?: number;
  isRigaModalita?: boolean;
  isRigaTotale?: boolean;
  label?: string;
  importoNuovoRecupero?: number;           //solo frontend
  importoNuovoRecuperoFormatted?: string;  //solo frontend
}
