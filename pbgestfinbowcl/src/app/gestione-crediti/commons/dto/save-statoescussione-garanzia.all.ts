/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SaveStatoEscussioneGaranzia {
    public idSoggetto: string;
	public idProgetto: string;
	public idUtente: string;
    public idEscussione: string;
    public idCurrentRecord: number;
    public descStatoEscussione: string;
    public dtInizioValidita: string;
    public dtNotifica: string;
    public note: string;

    public listaAllegatiPresenti: Array<number>;
}