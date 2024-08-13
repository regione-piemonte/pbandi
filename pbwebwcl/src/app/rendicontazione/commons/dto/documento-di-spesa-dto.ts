/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DocumentoDiSpesaDTO {
    constructor(
        public codiceFiscaleFornitore: string,
        public codiceProgetto: string,
        public cognomeFornitore: string,
        public dataDocumentoDiSpesa: Date,
        public dataDocumentoDiSpesaDiRiferimento: Date,
        public descrizioneDocumentoDiSpesa: string,
        public destinazioneTrasferta: string,
        public denominazioneFornitore: string,
        public descStatoDocumentoSpesa: string,
        public descTipologiaDocumentoDiSpesa: string,
        public descBreveTipoDocumentoDiSpesa: string,
        public descTipologiaFornitore: string,
        public durataTrasferta: number,
        public idBeneficiario: number,
        public idDocRiferimento: number,
        public idDocumentoDiSpesa: number,
        public idFornitore: number,
        public idProgetto: number,
        public idSoggetto: number,
        public idSoggettoPartner: number,
        public idTipoDocumentoDiSpesa: number,
        public idTipoFornitore: number,
        public idTipoOggettoAttivita: number,
        public imponibile: number,
        public importoIva: number,
        public importoIvaACosto: number,
        public importoRendicontabile: number,
        public importoTotaleDocumentoIvato: number,
        public importoRitenutaDAcconto: number,
        public isGestitiNelProgetto: boolean,
        public isRicercaPerCapofila: boolean,
        public isRicercaPerTutti: boolean,
        public isRicercaPerPartner: boolean,
        public nomeFornitore: string,
        public numeroDocumento: string,
        public numeroDocumentoDiSpesa: string,
        public numeroDocumentoDiSpesaDiRiferimento: string,
        public partitaIvaFornitore: string,
        public partner: string,
        public costoOrario: number,
        public task: string,
        public importoTotaleValidato: number,
        public importoTotaleRendicontato: number,
        public progrFornitoreQualifica: number,
        public importoTotaleQuietanzato: number,
        public idStatoDocumentoSpesa: number,
        public rendicontabileQuietanzato: number,
        public noteValidazione: string,
        public descBreveStatoDocumentoSpesa: string,
        public tipoInvio: string,
        public flagElettronico: boolean,
        public idAppalto: number,
        public descrizioneAppalto: string,
        public flagElettXml: string,
        public idParametroCompenso: number,
        public ggLavorabiliMese: number,
        public sospBrevi: number,
        public sospLungheGgTot: number,
        public sospLungheGgLav: number,
        public oreMeseLavorate: number,
        public mese: number,
        public anno: number
    ) { }
}