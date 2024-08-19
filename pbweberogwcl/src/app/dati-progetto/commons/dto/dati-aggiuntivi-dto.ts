/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DatiAggiuntiviDTO {
    constructor(
        public numeroDomanda: string,
        public nomeReferenteTecnico: string,
        public cognomeReferenteTecnico: string,
        public cfReferenteTecnico: string,
        public telefonoReferenteTecnico: string,
        public emailReferenteTecnico: string,
        public pecReferenteTecnico: string,
        public dataUltimaTrasmissione: Date,
        public descrizioneIntervento: string,
        public livelloProgettuale: string,
        public dichTipologiaProgetto: string,
        public individuazioneAmbito: string,
        public numeroBeniOggIntervento: number,
        public descrizioneOggettiIntervento: string,
        public tipologiaBene: string,
        public titoloDisponibilitaBene: string,
        public tipologiaVincoloIstruttoria: string,
        public decretoSoprintendenza: string,
        public cofinanziamento: number,
        public tipologiaIntervento: string,
        public ivaDetraibileIndetraibile: string,
        public anno2023: number,
        public impegno2023N: string,
        public anno2024: number,
        public impegno2024N: string,
        public anno2025: number,
        public impegno2025N: string,
        public modErogSostFin: string,
        public provincia: string,
        public comuneCatasto: string,
        public catastoCodiceComune: string,
        public foglioCatasto: string,
        public particelleCatasto: string,
        public subParticellaCatasto: string,
        public rea: string,
        public codiceAteco: string,
        public contabiliaCodBen: number,
        public codiceCor: number,
        public modAiutoStatoCon: string,
        public prescrizioniDeMinimis: string,
        public ddAssegnazioneNDel: string,
        public attoObbligoDataProtocollo: Date,
        public attoObbligoNProtocollo: number
    ) { }
}