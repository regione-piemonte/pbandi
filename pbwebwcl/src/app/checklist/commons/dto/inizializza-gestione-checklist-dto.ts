export class InizializzaGestioneChecklistDTO {
    constructor(
        public codiceVisualizzatoProgetto: string,
        public modificaChecklistAmmessa: boolean,
        public eliminazioneChecklistAmmessa: boolean
    ) { }
}