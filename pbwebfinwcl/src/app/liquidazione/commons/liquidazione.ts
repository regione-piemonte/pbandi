/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizioneDTO } from "src/app/gestione-disimpegni/commons/codice-descrizione-vo";
import { DatiGeneraliDTO } from "./dati-generali-vo";
import { EsitoDatiProgettoDTO } from "./esito-dati-progetto-vo";
import { EsitoLeggiAttoLiquidazioneDTO } from "./esito-leggi-atto-liquidazione-vo";
import { ModalitaAgevolazioneLiquidazioneDTO } from "./modalita-agevolazione-liquidazione-vo";

export class Liquidazione {
    constructor(
        public datiGeneraliDTO: DatiGeneraliDTO,
        public modalitaAgevolazioneLiquidazioneDTO: Array<ModalitaAgevolazioneLiquidazioneDTO>,
        public esitoDatiProgettoDTO: EsitoDatiProgettoDTO,
        public esitoLeggiAttoLiquidazioneDTO: EsitoLeggiAttoLiquidazioneDTO,
        public codiceDescrizioneErogazioneDTO: Array<CodiceDescrizioneDTO>,
        public codiceDescrizioneDTO: Array<CodiceDescrizioneDTO>
    ) { }
}