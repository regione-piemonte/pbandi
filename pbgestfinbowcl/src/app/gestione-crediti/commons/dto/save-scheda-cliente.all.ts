/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/


export class SaveSchedaCliente
{
    public wtfImSaving: string;

    public idSoggetto: string;
    public idUtente: string;

    // SALVA STATO AZIENDA (1)
    public staAz_idCurrentRecord: number;
    public staAz_statoAzienda: string;
    public staAz_dataInizio: any;
    public staAz_dataFine: any;

    // SALVA STATO CREDITO FINPIEMONTE (2)
    public staCre_idCurrentRecord: number;
    public staCre_stato: string;
    public staCre_dataMod: any;
    public staCre_PROGR_SOGGETTO_PROGETTO: number
    
    // SALVA RATING (3)
    public rat_idCurrentRecord: number;
    //public rat_provider: string;
    public rat_idRating: number;
    public rat_dataClassificazione: any;

    // SALVA BANCA BENEFICIARIO (4)
    public banBen_idCurrentRecord: number;
    public banBen_banca: string;
    public banBen_dataModifica: string;
    public benBen_motivazione: string;
    public banBen_soggettoTerzo: string;
    public banBen_PROGR_SOGGETTO_PROGETTO: number;

    
}
