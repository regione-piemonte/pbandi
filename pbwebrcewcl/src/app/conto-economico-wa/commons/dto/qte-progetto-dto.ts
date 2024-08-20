/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatiQteDTO } from "./dati-qte-dto";

export interface QteProgettoDTO {
    idQtesHtmlProgetto: number;
    htmlQtesProgetto: string;
    idProgetto: number;
    datiQte: Array<DatiQteDTO>;
}