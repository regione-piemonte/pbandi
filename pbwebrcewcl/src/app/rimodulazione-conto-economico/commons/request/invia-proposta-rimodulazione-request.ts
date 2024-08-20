/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ModalitaAgevolazione } from "../dto/modalita-agevolazione";
import { TipoAllegatoDTO } from "../dto/tipo-allegato-dto";

export class InviaPropostaRimodulazioneRequest {
    constructor(
        public idProgetto: number,
        public idContoEconomico: number,
        public idSoggettoBeneficiario: number,
        public listaModalitaAgevolazione: Array<ModalitaAgevolazione>,
        public note: string,
        public idRapprensentanteLegale: number,
        public idDelegato: number,
        public importoFinanziamentoRichiesto: number,
        public tipiAllegato: Array<TipoAllegatoDTO>
    ) { }
}