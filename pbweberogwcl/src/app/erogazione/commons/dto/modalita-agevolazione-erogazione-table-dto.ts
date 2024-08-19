/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ModalitaAgevolazioneErogazioneTableDTO {
  constructor(
    public descrizione: string,
    public numRiferimento: string,
    public dtContabile: Date,
    public ultimoImportoAgevolato: number,
    public importoErogato: number,
    public importoRecuperato: number,
    public importoResiduoDaErogare: number,
    public isAgevolazione: boolean,
    public isTotale: boolean,
    public idErogazione: number,
    public importoRevocato: number,
    public idModalitaAgevolazione: number
  ) { }
}
