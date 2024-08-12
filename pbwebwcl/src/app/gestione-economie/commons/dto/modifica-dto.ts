import { BandoLinea } from "./bando-linea";
import { DatiGeneraliDTO } from "./dati-generali-dto";
import { EconomiaDTO } from "./economia-dto";
import { ProgettoDTO } from "./progetto-dto";
import { QuotaDTO } from "./quota-dto";
import { ResponseCodeMessage } from "./response-code-message";

export class ModificaDTO {
    constructor(
        public quoteProgettoCedente: Array<QuotaDTO>,
        public quoteEconomiaCedente: Array<QuotaDTO>,
        public quoteProgettoRicevente: Array<QuotaDTO>,
        public quoteEconomiaRicevente: Array<QuotaDTO>,
        public bandoLinea: Array<BandoLinea>,
        public economiaDTO: Array<EconomiaDTO>,
        public progettiBandoLinea: Array<ProgettoDTO>,
        public datiGeneraliDTO: DatiGeneraliDTO,
        public economieGiaCedute: number,
        public importoMaxCedibile: number,
        public responseCodeMessage: ResponseCodeMessage
    ) { }
}