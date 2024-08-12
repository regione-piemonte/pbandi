import { FormControl } from "@angular/forms";
import { CodiceDescrizioneDTO } from "./codice-descrizione-dto";

export class FiltroRicercaSpesaValidata {
    constructor(
        public taskSelected: string,
        public dichiarazioneDiSpesaSelected: CodiceDescrizioneDTO,
        public voceDiSpesaSelected: CodiceDescrizioneDTO,
        public tipologiaDocSelected: CodiceDescrizioneDTO,
        public tipologiaFornitoreSelected: CodiceDescrizioneDTO,
        public numDocSpesa: string,
        public dataDoc: FormControl,
        public cfFornitore: string,
        public pIvaFornitore: string,
        public denomFornitore: string,
        public cognomeFornitore: string,
        public nomeFornitore: string,
        public documenti: string
    ) { }
}