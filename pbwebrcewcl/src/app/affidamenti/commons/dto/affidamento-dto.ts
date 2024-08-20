/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FornitoreAffidamentoDTO } from "./fornitore-affidamento-dto";
import { ProceduraAggiudicazioneDTO } from "./procedura-aggiudicazione-dto";
import { VarianteAffidamentoDTO } from "./variante-affidamento-dto";

export class AffidamentoDTO {
    constructor(
        public idProgetto: number,
        public idAppalto: number,
        public oggettoAppalto: string,
        public idProceduraAggiudicaz: number,
        public idTipologiaAppalto: number,
        public descTipologiaAppalto: string,
        public idTipoAffidamento: number,
        public idTipoPercettore: number,
        public idStatoAffidamento: number,
        public descStatoAffidamento: string,
        public idNorma: number,
        public bilancioPreventivo: number,
        public importoContratto: number,
        public impRendicontabile: number,
        public impRibassoAsta: number,
        public percRibassoAsta: number,
        public dtGuue: Date,
        public dtGuri: Date,
        public dtQuotNazionali: Date,
        public dtWebStazAppaltante: Date,
        public dtWebOsservatorio: Date,
        public dtInizioPrevista: Date,
        public dtConsegnaLavori: Date,
        public dtFirmaContratto: Date,
        public interventoPisu: string,
        public impresaAppaltatrice: string,
        public sopraSoglia: string,
        public descProcAgg: string,
        public dtInserimento: Date,
        public dtAggiornamento: Date,
        public idUtenteIns: number,
        public idUtenteAgg: number,
        public cigProcAgg: string,
        public codProcAgg: string,
        public proceduraAggiudicazioneDTO: ProceduraAggiudicazioneDTO,
        public varianti: Array<VarianteAffidamentoDTO>,
        public fornitori: Array<FornitoreAffidamentoDTO>,
        public numFornitoriAssociati: number,
        public numAllegatiAssociati: number,
        public respingibile: boolean,
        public fase1Esito: string,
        public fase1Rettifica: string,
        public fase2Esito: string,
        public fase2Rettifica: string,
        public esisteAllegatoNonInviato: boolean
    ) { }
}
