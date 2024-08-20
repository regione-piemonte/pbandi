/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

//import { SchedaClienteDettaglioErogato } from "./scheda-cliente-dettaglio-erogato";
//import { SchedaClienteHistory } from "./scheda-cliente-history";
//import { SuggestRatingVO } from "./suggest-rating-VO";

import { GestioneAllegatiVO } from "src/app/gestione-crediti/commons/dto/gestione-allegati-VO";

export class modificaEscussioneRiassicurazioniDTO {


    //public idSoggetto: string;
    public idProgetto: string;
    //public progrSoggProg: number;

    public idEscussioneCorrente :number;
	public idStatoEscussione :number;
	public idTipoEscussione :number;
	public tipoEscussione: string;
	public statoEscussione: string;
	public dataRicevimentoRichEscuss: Date;
	public dataStatoEscussione: Date;
	public dataNotificaEscussione: Date;
	public numeroProtocolloRichiesta: string;
	public numeroProtocolloNotifica: string;
	public importoRichiesto: number;
	public importoApprovato: number;
	public causaleBonifico: string;
	public ibanBanca: string;
	public denomBanca: string;
	public note: string;

    public allegatiInseriti: Array<GestioneAllegatiVO>;

    // Escussione
    //public idEscussioneCorrente: number;
    //public idStatoEscussione: number;
	//public idTipoEscussione: number;
	

    /*public dataRicevimentoRichEscuss: Date;
    public numeroProtocolloRichiesta: string;
	public numeroProtocolloNotifica: string;
	public tipoEscussione: string;
	public statoEscussione: string;
	public dataStatoEscussione: Date;
	public dataNotificaEscussione: Date;
	public importoRichiesto: number;
	public importoApprovato: number;
	public causaleBonifico: string;
	public ibanBanca: string;
	public denomBanca: string;
	public note: string;*/


    //dtInizioValidita: new FormControl([Validators.required]), == public dataStatoEscussione: Date; ??
    //idEscussione :this.infoEscussione.idEscussione,
    /*progrSoggettoProgetto: this.progrSoggettoProgetto,
    idBanca: this.idBanca,
    idSoggProgBancaBen: this.idSoggProgBancaBen,*/
	
}
