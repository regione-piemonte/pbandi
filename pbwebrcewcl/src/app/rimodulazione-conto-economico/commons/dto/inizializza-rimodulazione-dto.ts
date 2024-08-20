/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { InizializzaPropostaRimodulazioneDTO } from "./inizializza-proposta-rimodulazione-dto";

export class InizializzaRimodulazioneDTO extends InizializzaPropostaRimodulazioneDTO {
    public progettoRicevente: boolean;
    public sommaEconomieUtilizzate: number;
}