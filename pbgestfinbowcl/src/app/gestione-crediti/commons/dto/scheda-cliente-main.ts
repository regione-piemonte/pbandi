/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SchedaClienteDettaglioErogato } from "./scheda-cliente-dettaglio-erogato";
import { SchedaClienteHistory } from "./scheda-cliente-history";
import { SuggestRatingVO } from "./suggest-rating-VO";

export class SchedaClienteMain {

    // Tenete aggiornato l'oggetto tra BE e FE, per favore

    public idSoggetto: string;
    public idProgetto: string;
    public progrSoggProg: number;
    public staAz_currentId: number;
    public staAz_idStatoAzienda: number;
    public staAz_dtInizioValidita: string;
    public staAz_dtfineValidita: string;
    public stAz_statiAzienda: Array<string>;
    public staCre_currentId: number;
    public staCre_idStatoCredito: number;
    public staCre_statiCredito: Array<string>;
    public rating_currentId: number;
    //public rating_providers: Array<string>;
    public rating_ratings: Array<SuggestRatingVO>;
    public banBen_currentId: number;
    public banBen_idBanca: number;
    public banBen_banche: Array<string>;
    public banBen_motivazioni: Array<string>;
    public tipoAnagrafica: string;
    public bando: string;
    public progetto: string;
    public denominazEnteGiu: string;
    public denominazPerFis: string;
    public codiceFiscale: string;
    public partitaIva: string;
    public formaGiuridica: string;
    public tipoSoggetto: string;
    public statoAzienda: string;
    public statoCredito: string;
    public rating: string;
    public provider: string;
    public dataClassRating: string;
    public classeRischio: string;
    public dataClassRischio: string;
    public banca: string;
    public denominazione: string;
    public denominazioneBanca: string;
    public codBanca: string;
    public codUtente: string;
    public descStato: string;
    public idEscussione: string;
    public idTarget: number;
    public dtRichEscussione: string;
    public numProtocolloRich: string;
    public numProtocolloNotif: string;
    public descTipoEscussione: string;
    public descStatoEscussione: string;
    public statoEscussione: string;
    public dtInizioValidita: any;
    public dtNotifica: any;
    public impRichiesto: string;
    public impApprovato: string;
    public causaleBonifico: string;
    public ibanBonifico: string;
    public descBanca: string;
    public note: string;
    public deliberatoBanca: string;
    public confidi: string;
    public altreGaranzie: string;

    public dtModifica: any;

    public motivazione: string;

    public dettaglioErogato: Array<SchedaClienteDettaglioErogato>;

    public storicoStatoAzienda: Array<SchedaClienteHistory>;
    public storicoStatoCreditoFin: Array<SchedaClienteHistory>;
    public storicoRating: Array<SchedaClienteHistory>;
    public storicoBancaBeneficiario: Array<SchedaClienteHistory>;
    public storicoEscussione: Array<SchedaClienteMain>;
    public storicoStatocredito: Array<SchedaClienteMain>;

    public codiceUtente: string;
    public progrSoggettoProgetto: string;
    public descrizioneBando: string;
    public codiceProgetto: string;
    public codiceVisualizzato: string;

}
