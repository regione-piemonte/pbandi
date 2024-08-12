import { Beneficiario } from "./beneficiario";
import { QuotaDTO } from "./quota-dto";

export class BeneficiarioQuoteDTO {
    constructor(
        public beneficiario: Array<Beneficiario>,
        public quotaDTO: Array<QuotaDTO>,
        public quotaEconomiaDTO: Array<QuotaDTO>,
        public importoCeduto: number,
        public importoMaxCedibile: number,
        public economieGiaCedute: number
    ) { }
}