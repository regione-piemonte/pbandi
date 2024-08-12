import { QualificaFormDTO } from "../dto/qualifica-form-dto";

export class SalvaQualificaRequest {
    constructor(
        public qualificaFormDTO: QualificaFormDTO,
        public idUtente: number
    ) { }
}