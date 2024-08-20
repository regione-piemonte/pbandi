/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ContoEconomicoRimodulazioneDTO } from "./conto-economico-rimodulazione-dto";

export class EsitoFindContoEconomicoDTO {
    constructor(
        public locked: boolean,
        public modificabile: boolean,
        public copiaModificataPresente: boolean,
        public contoEconomico: ContoEconomicoRimodulazioneDTO,
        public rimodulazionePresente: boolean,
        public propostaPresente: boolean,
        public dataUltimaProposta: Date,
        public dataUltimaRimodulazione: Date,
        public dataPresentazioneDomanda: Date,
        public dataFineIstruttoria: Date,
        public inIstruttoria: boolean,
        public inStatoRichiesto: boolean,
        public isContoMainNew: boolean,
        public flagUltimoRibassoAstaInProposta: string,
        public flagUltimoRibassoAstaInRimodulazione: string,
        public importoUltimoRibassoAstaInProposta: number,
        public percUltimoRibassoAstaInProposta: number
    ) { }
}