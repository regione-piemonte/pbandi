/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class InizializzaInvioDichiarazioneDiSpesaDTO {
    constructor(
        public uploadRelazioneTecnicaAmmesso: boolean,
        public allegatiAmmessi: boolean,
        public allegatoGenerico: boolean,
        public progettoPiuGreen: boolean,
        public idProgettoContributo: number,
        public codiceVisualizzatoProgetto: string,
        public taskVisibile: boolean,
        public importoRichiestoErogazioneSaldoAmmesso: boolean,
        public dimMaxFileFirmato: number,
        public indicatoriObbligatori: boolean,
        public cronoprogrammaObbligatorio: boolean,
        public regola30attiva: boolean,
        public regolaBR51attiva: boolean,
        public regolaBR52attiva: boolean,
        public regolaBR53attiva: boolean,
        public regolaBR54attiva: boolean,
        public regolaBR60attiva: boolean,
        public regolaBR61attiva: boolean
    ) { }
}