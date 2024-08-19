/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { CausaleErogazioneDTO } from '../dto/causale-erogazione-dto';
export interface ModalitaAgevolazioneErogazioneDTO {
  causaliErogazione?: Array<CausaleErogazioneDTO>;
  descBreveModalitaAgevolaz?: string;
  descModalitaAgevolazione?: string;
  idModalitaAgevolazione?: number;
  importoResiduoDaErogare?: number;
  importoTotaleErogazioni?: number;
  importoTotaleRecupero?: number;
  ultimoImportoAgevolato?: number;
}
