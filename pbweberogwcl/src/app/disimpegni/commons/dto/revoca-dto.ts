/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { RevocaIrregolaritaDTO } from '../dto/revoca-irregolarita-dto';
export interface RevocaDTO {
  causaleDisimpegno?: string;
  codCausaleDisimpegno?: string;
  codiceVisualizzato?: string;
  descBreveModalitaAgevolaz?: string;
  descMancatoRecupero?: string;
  descModalitaAgevolazione?: string;
  descMotivoRevoca?: string;
  dtRevoca?: string;
  estremi?: string;
  flagOrdineRecupero?: string;
  idMancatoREcupero?: number;
  idModalitaAgevolazione?: number;
  idMotivoRevoca?: number;
  idPeriodo?: number;
  idProgetto?: number;
  idRevoca?: number;
  importo?: number;
  note?: string;
  revocaIrregolarita?: Array<RevocaIrregolaritaDTO>;
}
