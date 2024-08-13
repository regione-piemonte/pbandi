/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DecodificaSupport } from './decodifica-support';
import { DescBreveDescEstesaDTOSupport } from './descbreve-descestesa-dto-support';

export class EntiDTOSupport {
    constructor(

        public seralVersionUID: number,

        public entiAssociabiliFiltrati: Array<DecodificaSupport>,
        public entiAssociatiList: Array<DescBreveDescEstesaDTOSupport>,


    ) { }



}