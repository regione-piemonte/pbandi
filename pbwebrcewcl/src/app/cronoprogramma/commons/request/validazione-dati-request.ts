/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import {CronoprogrammaItem} from "../dto/cronoprogramma-item";
import {DatiCronoprogramma} from "../dto/dati-cronoprogramma";

export class ValidazioneDatiRequest{
  constructor(
    public idProgetto: number,
    public datiCrono: DatiCronoprogramma,
    public fasi: Array<CronoprogrammaItem>
  ) { }
}
