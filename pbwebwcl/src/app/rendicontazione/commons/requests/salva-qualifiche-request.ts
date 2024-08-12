import { QualificaFormDTO } from "../dto/qualifica-form-dto";

export class SalvaQualificheRequest {
    constructor(
        public listaQualificaFormDTO: Array<QualificaFormDTO>
    ) { }
}