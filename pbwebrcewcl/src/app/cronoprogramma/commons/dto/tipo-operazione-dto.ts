/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class TipoOperazioneDTO {
    constructor(
        public idTipoOperazione: number,
        public descBreveTipoOperazione: string,
        public descTipoOperazione: string,
        public codIgrueT0: string,
        public dtInizioValidita: Date,
        public dtFineValidita: Date
    ) { }
}