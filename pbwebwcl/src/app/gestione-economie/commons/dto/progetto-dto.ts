export class ProgettoDTO {
    constructor(
        public idProgetto: number,
        public idSoggettoBeneficiario: number,
        public idBando: number,
        public numeroIstruttoriAssociati: number,
        public titoloBando: string,
        public codiceVisualizzato: string,
        public beneficiario: string,
        public progrSoggettoProgetto: number,
        public cup: string
    ) { }
}