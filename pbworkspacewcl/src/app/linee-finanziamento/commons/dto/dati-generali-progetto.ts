/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FormControl } from "@angular/forms";
import { CodiceDescrizione } from "./codice-descrizione";

export class DatiGeneraliProgetto {
    cup: string;
    titoloProgetto: string;
    codProgettoNumDomanda: string;
    dataPresentazioneDomanda: FormControl;
    dataComitato: FormControl;
    dataConcessione: FormControl;
    dataDecorrenza: string;                         //da restituire al salvataggio come la ricevo in inizializzazione
    dataGenerazione: string;                        //da restituire al salvataggio come la ricevo in inizializzazione
    annoConcessioneOld: string;                     //da modificare ?? oppure da restituire al salvataggio come la ricevo in inizializzazione
    settoreAttivita: CodiceDescrizione;
    attivitaAteco: CodiceDescrizione;
    tipoOperazione: CodiceDescrizione;
    prioritaQsn: CodiceDescrizione;
    obiettivoGenQsn: CodiceDescrizione;
    obiettivoSpecQsn: CodiceDescrizione;
    strumento: CodiceDescrizione;
    settoreCpt: CodiceDescrizione;
    temaPrioritario: CodiceDescrizione;
    indicatoreRisProg: CodiceDescrizione;
    indicatoreQsn: CodiceDescrizione;
    tipoAiuto: CodiceDescrizione;
    tipoStrumentoProg: CodiceDescrizione;
    dimTerritoriale: CodiceDescrizione;
    progettoComplesso: CodiceDescrizione;
    obiettivoTematico: CodiceDescrizione;
    classificazioneRA: CodiceDescrizione;
    settoreCipe: CodiceDescrizione;
    sottoSettoreCipe: CodiceDescrizione;
    categoriaCipe: CodiceDescrizione;
    grandeProgetto: CodiceDescrizione;
    naturaCipe: CodiceDescrizione;
    tipologiaCipe: CodiceDescrizione;
    flagCardine: string;                             //da restituire al salvataggio come la ricevo in inizializzazione
    flagGeneratoreEntrate: string;                   //da restituire al salvataggio come la ricevo in inizializzazione
    flagLeggeObiettivo: string;                      //da restituire al salvataggio come la ricevo in inizializzazione
    flagAltroFondo: string;                          //da restituire al salvataggio come la ricevo in inizializzazione
    flagStatoProgettoProgramma: string;              //da restituire al salvataggio come la ricevo in inizializzazione
    flagDettaglioCup: string;                        //da restituire al salvataggio come la ricevo in inizializzazione
    flagAggiungiCup: string;                         //da restituire al salvataggio come la ricevo in inizializzazione
    flagBeneficiarioCup: string;                     //da restituire al salvataggio come la ricevo in inizializzazione
    flagProgettoDaInviareAlMonitoraggio: string;
    flagRichiestaAutomaticaDelCup: string;
    ppp: string;
    flagImportanzaStrategica: string;

    constructor() {
        this.dataPresentazioneDomanda = new FormControl();
        this.dataComitato = new FormControl();
        this.dataConcessione = new FormControl();
    }
}