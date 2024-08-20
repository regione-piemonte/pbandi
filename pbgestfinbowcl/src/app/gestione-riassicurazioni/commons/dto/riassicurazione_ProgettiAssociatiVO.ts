/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Riassicurazione_SoggettiCorrelatiVO } from "./riassicurazione_SoggettiCorrelatiVO";

export class Riassicurazione_ProgettiAssociatiVO {
    
    public idProgetto: number;
    public idSoggetto: number;
    public idBando: number;
    public progrSoggettoProgetto: number;
    public idEscussione: number;

    public codiceVisualizzato: string;
    public idModalitaAgevolazioneOrig: number;
    public descBreveModalitaAgevolazioneOrig: string;
    public descModalitaAgevolazioneOrig: string;
    public idModalitaAgevolazioneRif: number;
    public descBreveModalitaAgevolazioneRif: string;
    public descModalitaAgevolazioneRif: string;
    public totaleBanca: number;
    public totaleAmmesso: number;
    
    public soggettiCorrelati: Array<Riassicurazione_SoggettiCorrelatiVO>;
}