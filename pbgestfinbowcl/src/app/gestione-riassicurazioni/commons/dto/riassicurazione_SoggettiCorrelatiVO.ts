/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class Riassicurazione_SoggettiCorrelatiVO {
    
    public idProgetto: number;
    public idSoggetto: number;
    public ndg: string;
    public idEnte: number
    public progrSoggettiCorrelati: number;
    public progrSoggettoDomanda: number;
    public idRiassicurazione: number;

    public nome1: string;
    public nome2: string;
    public codiceFiscale: string;
    public statoProgetto: string;
    public dataEscussione: Date;
    public dataScarico: Date;
    public importoRichiesto: number;
    public importoAmmesso: number;
    
}