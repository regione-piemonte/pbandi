/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AttivitaDTO } from "src/app/gestione-crediti/commons/dto/attivita-dto";

export class DichiarazioneSpesaCampionamentoVO{

    public tipoDichiarazione: AttivitaDTO[]; 
    public flagValidita: boolean = true; 
    public imporRendicontatoInizio: number;
    public imporRendicontatoFine: number;
    public importoValidatoInizio: number;
    public importoValidatoFine: number;
    public dataRicezioneInizio: Date;
    public dataRicezioneFine: Date;
    public dataUltimoEsitoInizio: Date;
    public dataUltimoEsitoFine: Date;
}