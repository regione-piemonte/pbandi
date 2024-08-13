/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import {CronoprogrammaItem} from "../dto/cronoprogramma-item";

export class SalvaFasiMonitoraggioGestioneRequest {
  constructor(
    public idProgetto: number,
    public idIter: number,
    public fasi: Array<CronoprogrammaItem>,
    public idTipoOperazione?: number
  ) { }
}
