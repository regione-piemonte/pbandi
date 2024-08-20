/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ControdeduzioneVO {
    public idSoggetto: string;
    public idProgetto: string;
    public idUtente: number;
    public codiceUtente: string;
    public idControdeduz: number;
    public idStatoProroga: number;
    public progrSoggettoProgetto: string;
    public descrizioneBando: string;
    public codiceProgetto: string;
    public codiceVisualizzato: string;
    public numeroControdeduz: number;
	public numeroRevoca: number;
	public numeroProtocollo: number;
	public idStatoControdeduz: number;
	public dtStatoControdeduz: Date;
	public idAttivitaControdeduz: number;
	public dtAttivitaControdeduz: Date;
	public nonTrovato: boolean;
	public messaggio: string;
    public motivazione: string;
    public giorniRich: number;
    public giorniApp: number;
    public thereIsProroga: boolean;
    public isApproved: number;
    public statoProroga: string;
    public dtRichiesta: Date;
    public idGestioneRevoca: number;
    public idAttivitaRevoca: number;
    public idCausaleBlocco: number;
}