/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DocumentoAllegatoDTO } from "./documento-allegato-dto"

export class Fornitore {
    constructor(
        public codiceFiscaleFornitore: string,
        public cognomeFornitore: string,
        public costoOrario: number,
        public costoRisorsa: number,
        public descQualifica: string,
        public descTipoSoggetto: string,
        public denominazioneFornitore: string,
        public documentiAllegati: Array<DocumentoAllegatoDTO>,
        public idFornitore: number,
        public idQualifica: number,
        public idTipoSoggetto: number,
        public idSoggettoFornitore: number,
        public isLinkAllegaFileVisible: boolean,
        public isInvalid: boolean,
        public monteOre: number,
        public nomeFornitore: string,
        public partitaIvaFornitore: string,
        public idAttivitaAteco: number,
        public idFormaGiuridica: number,
        public idNazione: number,
        public altroCodice: string,
        public codUniIpa: string,
        public flagPubblicoPrivato: number,
        public isCFInvalid?: boolean, //solo frontend
        public isAltroCodInvalid?: boolean //solo frontend
    ) { }
}