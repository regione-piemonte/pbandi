/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class InizializzaLineeDiFinanziamentoDTO {
    constructor(
        public inserimentoModificaProgettoAbilitati: boolean,                // Se true, i bottoni di creazione e modifica progetto sono visibili.
        public esistonoProgettiSifAvviati: boolean,                          // Se true, il bottone di creazione progetto va nascosto.
        public nomeBandoLinea: string
    ) { }
}