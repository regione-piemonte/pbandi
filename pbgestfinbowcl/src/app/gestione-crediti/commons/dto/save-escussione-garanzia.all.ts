/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SaveEscussioneGaranzia {
    public idSoggetto: string;
	public idProgetto: string;
	public codUtente: string;
	public idUtente: string;    
    public idCurrentRecord: number;
    public dtRichEscussione: string;
	public numProtocolloRich: string;
	public descTipoEscussione: string;
	public descStatoEscussione: string;
	public dtInizioValidita: string;
	public dtNotifica: string;
	public impApprovato: string;
	public impRichiesto: string;
	public causaleBonifico: string;
	public numProtocolloNotif: string;
	public ibanBonifico: string;
	public descBanca: string;
	public note: string;
}