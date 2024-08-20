/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ModalitaAgevolazione {
    constructor(
        public descModalita: string,
        public importoMassimoAgevolato: number,
        public percentualeMassimoAgevolato: number,
        public ultimoImportoRichiesto: number,
        public importoRichiesto: number,
        public percentualeImportoRichiesto: number,
        public importoErogato: number,
        public ultimoImportoAgevolato: number,
        public percentualeUltimoImportoAgevolato: number,
        public modificabile: boolean,
        public importoAgevolato: number,
        public percentualeImportoAgevolato: number,
        public hasImportoRichiestoError: boolean,
        public errorImportoRichiestoMsg: string,
        public hasPercImportoRichiestoError: boolean,
        public errorPercImportoRichiestoMsg: string,
        public hasImportoAgevolatoError: boolean,
        public errorImportoAgevolatoMsg: string,
        public hasPercImportoAgevolatoError: boolean,
        public errorPercImportoAgevolatoMsg: string,
        public idModalitaAgevolazione: number,
        public isModalitaTotale: boolean,
        public importoAgevolatoFormatted?: string,                      //solo frontend per concludi rimodulazione
        public percentualeImportoAgevolatoFormatted?: string,           //solo frontend per concludi rimodulazione
    ) { }
}