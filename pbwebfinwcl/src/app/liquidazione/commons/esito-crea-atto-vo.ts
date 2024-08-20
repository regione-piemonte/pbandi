/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class EsitoCreaAttoDTO { 
    constructor(
        public esito: boolean,
        public message: string,
        public params: Array<string>,
        public esitoOperazione: string,
        public numeroAtto: string,
        public descEsitoOperazione: string,
        public annoAtto: string,
        public dataAtto: Date,
        public idOperazioneAsincrona: string,
        public numeroDocSpesa: string,

    ) { }
}