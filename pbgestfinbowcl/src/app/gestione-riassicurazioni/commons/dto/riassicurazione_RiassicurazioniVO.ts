/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class Riassicurazione_RiassicurazioniVO {
    
    public idProgetto: number;
    public idRiassicurazione: number;

    public lineaInterventoSogg: string;
    public ragioneSociale: string;
    public formaGiuridica: string;
    public descFormaGiuridica: string;
    public codiceFiscale: string;
    public indirizzoSedeDestinatario: string;
    public localitaSedeDestinatario: string;
    public provinciaSedeDestinatario: string;
    public ateco: string;
    public descAteco: string;
    public importoFinanziato: number;
    public importoGaranzia: number;
    public importoAmmesso: number;
    public importoEscusso: number;
    public percentualeGaranzia: number;
    public percentualeRiassicurato: number;
    public dataErogazioneMutuo: Date;
    public dataDeliberaMutuo: Date;
    public dataEmissioneGaranzia: Date;
    public dataScadenzaMutuo: Date;
    public dataScarico: Date;
    public dataRevoca: Date;
    public dataEscussione: Date;

    public dataInizioValidita: Date;
    public dataFineValidita: Date;
    public utenteIserito: string;
}