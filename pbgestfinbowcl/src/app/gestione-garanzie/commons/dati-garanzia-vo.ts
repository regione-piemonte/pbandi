/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatiDettaglioGaranziaVO } from "./dati-dettaglio-garanzia-vo";

export class DatiGaranziaVO {

    public idProgetto: number;
    public idSoggetto: number;
    public progrSoggettoProgetto: number;
    public idBando: number;
    public idEscussione: number;

    public titoloBando: string;
	public codiceProgetto: string;
	public ndg: string;
	public denomBeneficiario: string;
	public denomBanca: string;
	public dataRichiestaEscussione: Date;
	public idTipoEscussione: number;
	public descTipoEscussione: string;
	public idStatoEscussione: number;
	public descStatoEscussione: string;
	public dataStato: Date; // Corrisponde a DT_INIZIO_VALIDITA
	public importoRichiesto: number;
	public importoApprovato: number;
	public idModalitaAgevolazione: number;
	public idModalitaAgevolazioneRif: number;
	
	public codiceFiscale: string;
	public partitaIva: string;
	public isFondoMisto: boolean;
	//public esitoInviato: boolean;
	
    public listaDettagli: Array<DatiDettaglioGaranziaVO>;


}