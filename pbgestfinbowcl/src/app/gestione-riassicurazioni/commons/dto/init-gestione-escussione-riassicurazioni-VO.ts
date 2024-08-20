/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

//import { SchedaClienteDettaglioErogato } from "./scheda-cliente-dettaglio-erogato";
//import { SchedaClienteHistory } from "./scheda-cliente-history";
//import { SuggestRatingVO } from "./suggest-rating-VO";

import { GestioneAllegatiVO } from "src/app/gestione-crediti/commons/dto/gestione-allegati-VO";

export class initGestioneEscussioneRiassicurazioniVO {

    /* Oggetto usato per popolare e visualizzare la pagina principale di modifica escussione di Riassicurazione. */

    public idSoggetto: string;
    public idProgetto: string;
    public progrSoggProg: number;

    // Testata
    public testata_bando: string;
    public testata_codProgetto: string;

    // Dati anagrafici
    public datiAnagrafici_beneficiario: string;
    public datiAnagrafici_codFiscale: string;
    public datiAnagrafici_partitaIva: string;
    public datiAnagrafici_formaGiuridica: string;
    public datiAnagrafici_tipoAnagrafica: string;
    public datiAnagrafici_denomBanca: string;

    // Escussioni passate
    public escussioniPassate: Array<initGestioneEscussioneRiassicurazioniVO>;

    // Escussione corrente
    public escussione_idEscussioneCorrente: number;
    public escussione_idStatoEscussione: number;
	public escussione_idTipoEscussione: number;
	public escussione_tipoEscussione: string;
	public escussione_statoEscussione: string;
	public escussione_dataRicevimentoRichEscuss: Date;
	public escussione_dataStatoEscussione: Date;
	public escussione_dataNotificaEscussione: Date;
	public escussione_numeroProtocolloRichiesta: string;
	public escussione_numeroProtocolloNotifica: string;
	public escussione_importoRichiesto: number;
	public escussione_importoApprovato: number;
	public escussione_causaleBonifico: string;
	public escussione_ibanBanca: string;
	public escussione_denomBanca: string;
	public escussione_note: string;

    // Escussione nuova
    public statiNuovaEscussione: Array<string>;

    // Controllo stato iter Escussione
    public esitoInviato: boolean;
    public integrazioneInviata: boolean;
    public tipiEscussione: Array<string>;
	public statiEscussione: Array<string>;
    public statoPulsanteEscussione: number; // 1 = Nuova // 2 = Modifica // 3 = Disabilitato
  
    public canSendEsito: boolean;
	public canSendIntegrazione: boolean;
    public sommaImportiApprovatiInseriti: number;

    public allegatiInseriti: Array<GestioneAllegatiVO>
}
