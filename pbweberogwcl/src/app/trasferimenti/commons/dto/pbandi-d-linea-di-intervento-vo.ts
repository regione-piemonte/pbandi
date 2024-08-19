/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PbandiDLineaDiInterventoVO {
    constructor(
        public codIgrueT13T14: string,
        public idProcesso: number,
        public dtInizioValidita: Date,
        public idTipoStrumentoProgr: number,
        public numDelibera: number,
        public idStrumentoAttuativo: number,
        public idLineaDiInterventoPadre: number,
        public annoDelibera: number,
        public dtFineValidita: Date,
        public idTipoLineaIntervento: number,
        public idLineaDiIntervento: number,
        public codLivGerarchico: string,
        public descBreveLinea: string,
        public descLinea: string,
        public flagCampionRilev: string,
        public flagTrasferimenti: string
    ) { }
}