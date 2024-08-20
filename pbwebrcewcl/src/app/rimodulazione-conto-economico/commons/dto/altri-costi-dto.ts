/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AltriCostiDTO {
    constructor(
        public idDCeAltriCosti: number,
        public descBreveCeAltriCosti: string,
        public descCeAltriCosti: string,
        public idTCeAltriCosti: number,
        public impCeApprovato: number,
        public impCePropmod: number,
        public impCeApprovatoFormatted?: string, //solo frontend
        public impCePropmodFormatted?: string  //solo frontend
    ) { }
}