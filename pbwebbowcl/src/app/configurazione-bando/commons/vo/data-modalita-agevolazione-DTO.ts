/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DataModalitaAgevolazioneDTO { 
    constructor(

        public idBando : number,
        public idModalitaAgevolazione : number,
        public descModalitaAgevolazione : string,
        public percentualeImportoAgevolato : number,
        public minimoImportoAgevolato : number,
        public massimoImportoAgevolato : number,
        public periodoStabilita : number,
        public importoDaErogare : number,
        public flagLiquidazione : string,

    ) { }
}