/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ControlloLocoVo {

	progrBandoLinea: number;
	idStatoControllo: number;
	descBando: string;
	codVisualizzato: string;
	denominazione: string;
	descStatoControllo: string;
	descTipoControllo: string;
	descAttivita: string;
	istruttoreVisita: string;
	descTipoVisita: string;
	flagSif: string;
	importoAgevIrreg: number;
	importoIrregolarita: number;
	importoDaControllare: number;
	idControllo: number;
	idGiuPersFis: number;
	isPersonaGiuridica: boolean;
	dataAvvioControlli: Date;
	dataInizioControlli: Date;
	dataFineControlli: Date;
	dataVisitaControllo: Date;
	idProgetto: number;
	idAttivitaContrLoco: number;
	idAutoritaControllante: number;
	tipoControllo: string;
	idSoggettoBenef: number;
	numProtocollo: string; 
    descAutoritaControllante: string;
	descStatoChecklist:  string; 
	idStatoChecklist: number; 


}
