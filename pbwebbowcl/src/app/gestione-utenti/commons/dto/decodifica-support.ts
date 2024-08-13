/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Decodifica } from "src/app/shared/commons/dto/decodifica"


export class DecodificaSupport {

    public decodifica: Decodifica
    public checkboxselected: boolean = false
    public checkboxselectedChanged: boolean = false

    constructor(

        public seralVersionUID: number,

        public id: number,
        public descrizione: string,
        public descrizioneBreve: string,
        public dataInizioValidita: Date,
        public dataFineValidita: Date,

    ) {

        this.decodifica = new Decodifica(this.id, this.descrizione, this.descrizioneBreve, this.dataInizioValidita, this.dataFineValidita)

    }


}