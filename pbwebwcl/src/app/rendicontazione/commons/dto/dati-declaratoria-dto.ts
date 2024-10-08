/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export interface DatiDeclaratoriaDTO {
    strutture: Array<Struttura>;
    stataleComunitaria: Array<StataleComunitaria>;
    ritenutaIres: string;
    iva: string;
    collegiali: string;
    documentoUnico: string;
    contributiStataliComunitarie: string;
    contributiStrutture: string;
    contributiSuccessivi: string;

}

interface Struttura{
	direzione: string;
	normativa:string;
	settore:string;
}

interface StataleComunitaria{
	programma: string;
	struttura: string;
}
