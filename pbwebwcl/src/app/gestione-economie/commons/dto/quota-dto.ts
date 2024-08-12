export class QuotaDTO {
    constructor(
        public descSoggFinanziatore: string,
        public percQuotaSoggFinanziatore: number,
        public importo: number,
        public impQuotaEconSoggFinanziat: number,
        public idEconomia: number,
        public idSoggettoFinanziatore: string,
        public idProgetto: string,
        public impQuotaEconSoggFinanziatFormatted?: string
    ) { }
}