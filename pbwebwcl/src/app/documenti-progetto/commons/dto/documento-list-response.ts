import { ApiMessage } from "./api-message";
import { Documento } from "./documento";

export class DocumentoListResponse {
    constructor(
        public esitoPositivo: boolean,
        public apiMessages: Array<ApiMessage>,
        public documentiList: Array<Documento>
    ) { }
}