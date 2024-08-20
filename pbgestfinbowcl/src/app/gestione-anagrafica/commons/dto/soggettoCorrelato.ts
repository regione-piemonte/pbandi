/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface SoggettoCorrelato {
	codiceFiscale: string;
	cognome: string;
	idDomanda: string;
	idSoggettoCorellato: number;
	nag: string;
	nome:string;
	quotaPartecipazione?: any;
	ruolo: string;
	tipologia: string;
}

