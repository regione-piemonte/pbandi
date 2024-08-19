/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { RigaModificaRevocaItem } from "./riga-modifica-revoca-item";
import { RigaRevocaItem } from "./riga-revoca-item";

export class RequestSalvaRevoche {
  constructor(
    public idProgetto: number,
    public note: string,
    public estremi: string,
    public dtRevoca: string,
    public idMotivoRevoca: number,
    public ordineRecupero: string,
    public idModalitaRecupero: number,
    public codCausaleDisimpegno: string,
    public idAnnoContabile: number,
    public revoche: Array<RigaRevocaItem>,
    public disimpegni: Array<RigaModificaRevocaItem>
  ) { }
}
