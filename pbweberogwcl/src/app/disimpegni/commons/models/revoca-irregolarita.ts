/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DettaglioRevocaIrregolarita } from './dettaglio-revoca-irregolarita';
export class RevocaIrregolarita {
  descPeriodoVisualizzata: string;
  dettagliRevocaIrregolarita: Array<DettaglioRevocaIrregolarita>;
  dtFineValidita: string;
  idIrregolarita: number;
  idRevoca: number;
  importoAgevolazioneIrreg: number;
  importoIrregolarita: number;
  motivoRevocaIrregolarita: string;
  notePraticaUsata: string;
  quotaIrreg: number;
  tipoIrregolarita: string;
  checked?: boolean;
}
