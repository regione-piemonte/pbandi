export class PagamentiAssociatiRequest {
    constructor(
        public idDocumentoDiSpesa: number,
        public tipoInvioDocumentoDiSpesa: string,	// D (digitale):string, C (cartaceo)
        public descBreveStatoDocSpesa: string,
        public tipoOperazioneDocSpesa: string,		// "inserisci", "modifica", etc
        public idProgetto: number,
        public idBandoLinea: number,
        public codiceRuolo: string,
        public validazione: boolean				// true se si è in Validazione o in ValidazioneFinale.
    ) { }
}