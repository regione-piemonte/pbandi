/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PropostaCertificazioneDTO {
    public dataPagamenti: Date;
    public dataValidazioni: Date;
    public dataFideiussioni: Date;
    public dataErogazioni: Date;
    public descProposta: string;
    public idPropostaCertificaz: number;
    public dtOraCreazione: Date;
    public numeroProposta: number;
    public idProgetto: number;
    public codiceVisualizzato: string;
    public titoloProgetto: string;
    public attivita: string;
    public descBreveCompletaAttivita: string;
    public percContributoPubblico: number;
    public percCofinFesr: number;
    public importoErogazioni: number;
    public importoPagamentiValidati: number;
    public importoEccendenzeValidazione: number;
    public importoFideiussioni: number;
    public spesaCertificabileLorda: number;
    public descStatoPropostaCertif: string;
    public idDettPropostaCertif: number;
    public descBreveStatoPropostaCert: string;
    public isBozza: boolean;
    public costoAmmesso: number;
    public beneficiario: string;
    public idLineaDiIntervento: number;
    public isApprovataeNuovaProgrammazione: boolean;
    public idDocumentoIndex: boolean;
    public nomeDocumento: string;
    public esistePropostaApertaSuccessiva: boolean;

    constructor() { }
}