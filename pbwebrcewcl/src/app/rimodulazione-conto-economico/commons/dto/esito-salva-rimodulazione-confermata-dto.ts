/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoSalvaRimodulazioneConfermataDTO {
    constructor(
        public nuovoIdContoEconomico: number,
        public esito: boolean,
        public messaggi: Array<string>
    ) { }
}