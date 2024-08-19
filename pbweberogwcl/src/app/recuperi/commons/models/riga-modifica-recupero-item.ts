/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
export interface RigaModificaRecuperoItem {
  data?: string;
  hasCausaliErogazione?: boolean;
  idAnnoContabile?: number;
  idModalitaAgevolazione?: string;
  idRecupero?: string;
  importoAgevolato?: number;
  importoErogato?: number;
  importoRecuperato?: number;
  importoRevocato?: number;
  isRigaCausale?: boolean;
  isRigaModalita?: boolean;
  isRigaRecupero?: boolean;
  isRigaRevoca?: boolean;
  isRigaTotale?: boolean;
  label?: string;
  riferimento?: string;
}
