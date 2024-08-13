/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DescBreveDescEstesaDTO } from "./descbreve-descestesa-dto";

export class DescBreveDescEstesaDTOSupport {

    public descBreveDescEstesaDTO: DescBreveDescEstesaDTO
    public checkboxselected: boolean = false
    public checkboxselectedChanged: boolean = false

    constructor(

        public seralVersionUID: number,

        public id: number,
        public descBreve: string,
        public descEstesa: string,

    ) {

        this.descBreveDescEstesaDTO = new DescBreveDescEstesaDTO(this.seralVersionUID, this.id, this.descBreve, this.descEstesa)

    }
}