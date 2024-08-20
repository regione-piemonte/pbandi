/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class VisualizzaStoricoEscussioneVO {
    
    /*public idSoggetto: string;
	public idProgetto: string;
	public idUtente: string;
    public idEscussione: string;
    public idCurrentRecord: number;
    public descStatoEscussione: string;
    public dtInizioValidita: string;
    public dtNotifica: string;
    public note: string;*/

    public dtRichEscussione: Date;
	public numProtocolloRich: string;
	public descTipoEscussione: string;
	public descStatoEscussione: string;
	public dtInizioValidita: Date;
	public dtNotifica: Date;
	public numProtocolloNotif: string;
	public impApprovato: number;
	public impRichiesto: number;
	public causaleBonifico: string;
	public ibanBonifico: string;
	public descBanca: string;
	public note: string;
	public nome: string;
	public cognome: string;
	//solo per insert
	public idProgetto: number;
	public idUtente: number;
	public idEscussione: number;
	public idSoggProgBancaBen: number;
	public idBanca: number;
	public ProgrSoggettoProgetto: number;
	public listaAllegatiPresenti: Array<number>;
	//per gli errori
	public Messaggio: string;
}