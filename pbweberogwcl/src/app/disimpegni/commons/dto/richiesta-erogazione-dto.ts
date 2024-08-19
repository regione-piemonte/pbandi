/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { EstremiBancariDTO } from '../dto/estremi-bancari-dto';
import { FideiussioneDTO } from '../dto/fideiussione-dto';
import { FideiussioneTipoTrattamentoDTO } from '../dto/fideiussione-tipo-trattamento-dto';
import { SpesaProgettoDTO } from '../dto/spesa-progetto-dto';
import { TipoAllegatoDTO } from '../dto/tipo-allegato-dto';
export interface RichiestaErogazioneDTO {
  descBreveCausaleErogazione?: string;
  descrizioneCausaleErogazione?: string;
  direttoreLavori?: string;
  dtInizioLavori?: string;
  dtStipulazioneContratti?: string;
  estremiBancari?: EstremiBancariDTO;
  fideiussioni?: Array<FideiussioneDTO>;
  fideiussioniTipoTrattamento?: Array<FideiussioneTipoTrattamentoDTO>;
  id?: number;
  importoRichiesto?: number;
  percentualeErogazione?: number;
  percentualeLimite?: number;
  residenzaDirettoreLavori?: string;
  spesaProgetto?: SpesaProgettoDTO;
  tipoAllegati?: Array<TipoAllegatoDTO>;
}
