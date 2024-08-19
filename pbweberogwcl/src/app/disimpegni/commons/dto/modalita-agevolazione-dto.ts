/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { CausaleErogazioneDTO } from '../dto/causale-erogazione-dto';
import { RecuperoDTO } from '../dto/recupero-dto';
import { RevocaDTO } from '../dto/revoca-dto';
export interface ModalitaAgevolazioneDTO {
  causaliErogazioni?: Array<CausaleErogazioneDTO>;
  descBreveModalitaAgevolaz?: string;
  descModalitaAgevolazione?: string;
  idModalitaAgevolazione?: number;
  idProgetto?: number;
  importoErogazioni?: number;
  quotaImportoAgevolato?: number;
  recuperi?: Array<RecuperoDTO>;
  revoche?: Array<RevocaDTO>;
  totaleImportoRecuperato?: number;
  totaleImportoRevocato?: number;
}
