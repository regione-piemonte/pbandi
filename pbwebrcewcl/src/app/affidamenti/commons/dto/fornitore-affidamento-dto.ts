/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SubcontrattoAffidDTO } from "./subcontratto-affid-dto";

export class FornitoreAffidamentoDTO {
    constructor(
        public idFornitore: number,
        public idAppalto: number,
        public idTipoPercettore: number,
        public descTipoPercettore: string,
        public dtInvioVerificaAffidamento: Date,
        public flgInvioVerificaAffidamento: string,
        public idTipoSoggetto: number,
        public codiceFiscaleFornitore: string,
        public nomeFornitore: string,
        public cognomeFornitore: string,
        public partitaIvaFornitore: string,
        public denominazioneFornitore: string,
        public subcontrattiAffid: Array<SubcontrattoAffidDTO>
    ) {
        this.subcontrattiAffid = [];
    }
}