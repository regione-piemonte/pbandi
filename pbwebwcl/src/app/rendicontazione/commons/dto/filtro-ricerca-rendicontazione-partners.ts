import { DecodificaDTO } from "./decodifica-dto";

export class FiltroRicercaRendicontazionePartners {
    constructor(
        public visibile: boolean,
        public opzioni: Array<DecodificaDTO>,
        public partners: Array<DecodificaDTO>
    ) { }
}