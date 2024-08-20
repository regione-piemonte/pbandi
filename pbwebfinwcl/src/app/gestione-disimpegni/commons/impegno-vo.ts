/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BeneficiarioImpegnoDTO } from "./beneficiario-impegno-vo";
import { CapitoloDTO } from "./capitolo-vo";
import { ProvvedimentoDTO } from "./provvedimento-vo";
import { StatoImpegnoDTO } from "./stato-impegno-vo";
import { TrasferimentoDTO } from "./trasferimento-vo";

export class ImpegnoDTO { 
    constructor(
        public idImpegno: number,
        public annoImpegno: number,
        public numeroImpegno: number,
        public annoEsercizio: number,
        public importoInizialeImpegno: number,
        public importoAttualeImpegno: number,
        public totaleLiquidatoImpegno: number,
        public disponibilitaLiquidare: number,
        public totaleQuietanzatoImpegno: number,
        public dtInserimento: Date,
        public dtAggiornamento: Date,
        public cupImpegno: string,
        public cigImpegno: string,
        public numeroCapitoloOrigine: number,
        public statoImpegno: StatoImpegnoDTO,
        public provvedimento: ProvvedimentoDTO,
        public capitolo: CapitoloDTO,
        public annoPerente: number,
        public numeroPerente: number,
        public descImpegno: string,
        public trasferimento: TrasferimentoDTO,
        public numeroTotaleMandati: number,
        public beneficiario: BeneficiarioImpegnoDTO,
        public flagNoProvvedimento: string
    ) { }
}