export class OperazioneVO {
    constructor(
        public id: number,
        public descrizione: string,
        public selected?: boolean,
        public icona?: string,
        public isExpanded?: boolean
    ) { }
}