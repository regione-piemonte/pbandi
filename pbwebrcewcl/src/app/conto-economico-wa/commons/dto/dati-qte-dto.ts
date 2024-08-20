/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CheckDescBreve, NumberDescBreve } from "../../components/conto-economico-wa/conto-economico-wa.component";

export interface DatiQteDTO {
    idColonnaQtes: number;
    importiFonti: Array<NumberDescBreve>;
    tipiIntervento: Array<CheckDescBreve>;
    altriValori: Array<NumberDescBreve>;
    estremiAttoApprovazione: string;
    estremiAttoApprovazioneCert: string;

}