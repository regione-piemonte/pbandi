/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizioneDTO } from "./codice-descrizione-dto"

export class AvvisoDTO {
    public idNews: number;
    public titolo: string;
    public descrizione: string;
    public tipoNews: string;
    public urlPagina: string;
    public dtInizio: Date;
    public dtFine: Date;
    public utenteIns: number;
    public tipiAnagrafica: Array<CodiceDescrizioneDTO>;
}