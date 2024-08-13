/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { EsitoOperazioneDTO } from "src/app/checklist/commons/dto/esito-operazione-dto";

export class InizializzaRendicontazioneDTO {

    constructor(
        public taskVisibile: boolean,
        public codiceVisualizzatoProgetto: string,
        public allegatiAmmessi: boolean,
        public causalePagamentoVisible: boolean,
        public idTipoOperazioneProgetto: number,
        public idProcessoBandoLinea: number,
        public utenteAbilitatoAdAssociarePagamenti: boolean,
        public utenteAbilitatoAdAssociareVociDiSpesa: boolean,
        public solaVisualizzazione: EsitoOperazioneDTO,
        public allegatiAmmessiDocumentoSpesa: boolean,
        public allegatiAmmessiQuietanze: boolean,
        public allegatiAmmessiGenerici: boolean,
        public isBR58: boolean,
        public hasDocumentoDichiarabile: boolean,
    ) { }
}