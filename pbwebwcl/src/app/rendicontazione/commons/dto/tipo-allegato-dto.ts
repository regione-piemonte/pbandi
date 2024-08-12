export class TipoAllegatoDTO {
    constructor(
        public descTipoAllegato: string,
        public idTipoDocumentoIndex: number,
        public progrBandoLineaIntervento: number,
        public idDichiarazioneSpesa: number,
        public idMicroSezioneModulo: number,
        public flagAllegato: string,
        public numOrdinamentoMicroSezione: number,
        public idProgetto: number,
        public idTipoAllegato?: number, //solo frontend
        public isChecked?: boolean, //solo frontend
        public isDisabled?: boolean, //solo frontend
        public hasCheckbox?: boolean //solo frontend
    ) { }
}