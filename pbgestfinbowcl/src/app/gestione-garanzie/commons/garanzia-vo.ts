/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DettaglioGaranziaVO } from "./dettaglio-garanzia-vo"

export class GaranziaVO {
    public codiceFiscale: string;
    public codiceProgetto: string;
    public codiceVisualizzato: string;
    public dataRicevimento: Date;
    public dataNotifica: Date;
    public dataRicevimentoEscussione: Date;
    public dataStato: Date;
    public denominazioneBanca: string;
    public denominazioneCognomeNome: string;
    public descrizioneBando: string;
    public idProgetto: any;
    public idModalitaAgevolazione: number;
    public idModalitaAgevolazioneRif: number;
    public idTipoEscussione: number;
    public codBanca: string;
    public idStatoEscussione: number;
    public idSoggetto: number;
    public importoApprovato: number;
    public importoRichiesto: number;
    public nag: any;
    public partitaIva: string;
    public progrSoggettoProgetto: string;
    public statoCredito: string;
    public statoEscussione: string;
    public tipoEscussione: string;
    public idGaranzia: number; 
    public idEscussione: number; 
    public ndg : string;
    public numProtoRichiesta: number;
    public numProtoNotifica: number;
    public causaleBonifico : string;
    public ibanBancaBenef: string;
    public note : string;
    public descBanca: string;
    public idBanca: number;
    public idSoggProgBancaBen: number;
    public isFondoMisto: boolean


    public listaDettagli: Array<DettaglioGaranziaVO>;


}