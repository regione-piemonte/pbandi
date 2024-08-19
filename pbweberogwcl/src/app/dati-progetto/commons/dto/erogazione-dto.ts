/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { FideiussioneDTO } from '../dto/fideiussione-dto';
import { ModalitaAgevolazioneErogazioneDTO } from '../dto/modalita-agevolazione-erogazione-dto';
import { RichiestaErogazione } from '../models/richiesta-erogazione';
import { SpesaProgettoDTO } from '../dto/spesa-progetto-dto';
export interface ErogazioneDTO {
  codCausaleErogazione?: string;
  codDirezione?: string;
  codModalitaAgevolazione?: string;
  codModalitaErogazione?: string;
  dataContabile?: string;
  descBreveCausaleErogazione?: string;
  descBreveModalitaAgezolazione?: string;
  descBreveModalitaErogazione?: string;
  descModalitaAgevolazione?: string;
  descModalitaErogazione?: string;
  descTipoDirezione?: string;
  descrizioneCausaleErogazione?: string;
  erogazioni?: Array<ErogazioneDTO>;
  fideiussioni?: Array<FideiussioneDTO>;
  idErogazione?: number;
  importoErogazioneEffettivo?: number;
  importoErogazioneFinanziario?: number;
  importoTotaleErogato?: number;
  importoTotaleRecuperi?: number;
  importoTotaleResiduo?: number;
  importoTotaleRichiesto?: number;
  modalitaAgevolazioni?: Array<ModalitaAgevolazioneErogazioneDTO>;
  noteErogazione?: string;
  numeroRiferimento?: string;
  percErogazione?: number;
  percLimite?: number;
  percentualeErogazioneEffettiva?: number;
  percentualeErogazioneFinanziaria?: number;
  richiesteErogazioni?: Array<RichiestaErogazione>;
  spesaProgetto?: SpesaProgettoDTO;
}
