/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatiContestazioniVO } from "./allegatiContestazione";

export class ContestazioniVO {

        constructor(
            public idGestioneRevoca: number,
            public numeroRevoca: number,
            public dataNotifica: Date,
            public numeroProtocollo: number,
            public causaRevoca: string,
            public numeroContestazione: number,
            public idStatoContestazione: number,
            public descStatoContestazione: string,
            public dataScadenzaContestazione: Date,
            
            public abilitatoElimina: boolean,
            public abilitatoContestaz: boolean,
            public abilitatoIntegra: boolean,
            public abilitatoInvia: boolean,
            public allegati: Array<AllegatiContestazioniVO>
        ) { }
    }