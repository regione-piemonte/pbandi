/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { FileDTO } from 'src/app/dati-progetto/commons/dto/file-dto';
import { EstremiBancariDTO } from '../dto/estremi-bancari-dto';
import { FideiussioneDTO } from '../dto/fideiussione-dto';
import { FideiussioneTipoTrattamentoDTO } from '../dto/fideiussione-tipo-trattamento-dto';
import { SpesaProgettoDTO } from '../dto/spesa-progetto-dto';
import { TipoAllegatoDTO, TipoAllegatoRichiestaErogDTO } from '../dto/tipo-allegato-dto';
export interface RichiestaErogazioneDTO {
  descBreveCausaleErogazione?: string;
  descrizioneCausaleErogazione?: string;
  direttoreLavori?: string;
  dtInizioLavori?: Date;
  dtStipulazioneContratti?: Date;
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
  tipoAllegatiCompleti?: Array<TipoAllegatoRichiestaErogDTO>;
  allegati?: Array<FileDTO>;
}
