/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { QuadroPrevisionaleDTO } from './quadro-previsionale-dto';
import { QuadroPrevisionaleComplessivoDTO } from './quadro-previsionale-complessivo-dto';
export interface EsitoFindQuadroPrevisionaleDTO {
  controlloNuovoImportoBloccante?: boolean;
  dataUltimaSpesaAmmessa?: string;
  dataUltimoPreventivo?: string;
  quadroPrevisionale?: QuadroPrevisionaleDTO;
  quadroPrevisionaleComplessivo?: Array<QuadroPrevisionaleComplessivoDTO>;
  vociVisibili?: boolean;
}
