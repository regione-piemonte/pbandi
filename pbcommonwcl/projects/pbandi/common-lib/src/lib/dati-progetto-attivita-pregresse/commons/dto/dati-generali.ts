/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Bando } from "./bando"
import { Beneficiario } from "./beneficiario"
import { DatiCumuloDeMinimis } from "./dati-cumulo-de-minimis"
import { EconomiaPerDatiGenerali } from "./economia-per-dati-generali"
import { ImportoAgevolato } from "./importo-agevolato"
import { ImportoDescrizione } from "./importo-descrizione"
import { Progetto } from "./progetto"

export class DatiGenerali {
    constructor(
        public progetto: Progetto,
        public bando: Bando,
        public beneficiario: Beneficiario,
        public descrizioneBreve: string,
        public codiceDichiarazione: string,
        public dataPresentazione: Date,
        public importiAgevolati: Array<ImportoAgevolato>,
        public importoCertificatoNettoUltimaPropAppr: number,
        public importoValidatoNettoRevoche: number,
        public importoSoppressioni: number,
        public flagImportoCertificatoNettoVisibile: string,
        public erogazioni: Array<ImportoDescrizione>,
        public preRecuperi: Array<ImportoDescrizione>,
        public recuperi: Array<ImportoDescrizione>,
        public revoche: Array<ImportoDescrizione>,
        public importoRendicontato: number,
        public importoQuietanzato: number,
        public importoCofinanziamentoPrivato: number,
        public importoCofinanziamentoPubblico: number,
        public importoAmmesso: number,
        public datiCumuloDeMinimis: DatiCumuloDeMinimis,
        public importoImpegno: number,
        public importoResiduoAmmesso: string,
        public isLegatoBilancio: boolean,
        public economie: Array<EconomiaPerDatiGenerali>,
    ) { }
}