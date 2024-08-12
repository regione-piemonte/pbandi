export class AttivitaAtecoNodoDTO {
    constructor(
        public idAttivitaAteco: number,
        public codAteco: string,
        public codDescAteco: string,
        public figli: Array<AttivitaAtecoNodoDTO>
    ) { }
}