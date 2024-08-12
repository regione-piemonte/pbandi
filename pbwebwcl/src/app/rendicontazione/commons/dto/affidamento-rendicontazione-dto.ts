export class AffidamentoRendicontazioneDTO {
    constructor(
        public idAppalto: number,
        public cigProcAgg: string,
        public codProcAgg: string,
        public oggettoAppalto: string,
        public selezionabile: boolean,
        public fornitori: Array<string>,
    ) { }
}