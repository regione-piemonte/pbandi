/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Decodifica } from 'src/app/shared/commons/dto/decodifica';
import { DescBreveDescEstesaDTO } from './descbreve-descestesa-dto';

export class EntiDTO {
    constructor(

        public seralVersionUID: number,

        private entiAssociabiliFiltrati: Array<Decodifica>,
        private entiAssociatiList: Array<DescBreveDescEstesaDTO>,


    ) { }




}