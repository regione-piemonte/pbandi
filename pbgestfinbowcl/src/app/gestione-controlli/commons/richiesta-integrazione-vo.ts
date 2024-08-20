/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class RichiestaIntegrazioneVo {

    public idRichiestaIntegr: number;
    public motivazione: string;
    public dataRichiesta: Date;
    public dataNotifica: Date;
    public idUtente: number;
    public idTarget:number; // corrrisponde a idControllo
    public numGiorniScadenza:number;
    public descStatoRichiesta: string; 
    public idProgetto: number;
    public idEntita: number;
    public idStatoRichiesta:number;
    public dataScadenza: Date;
}
